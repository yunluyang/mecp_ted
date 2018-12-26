package com.mecp.service.info;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Customer;
import com.demo.common.model.DetectionDevice;
import com.demo.common.model.ServeDetail;
import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.demo.common.model.Team;
import com.demo.common.model.TestStBasis;
import com.demo.common.model.UploadRecord;
import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.common.info.BasicInfomationService;
import com.mecp.common.info.CommonInfoController;
import com.mecp.common.info.CustomerService;
import com.mecp.common.info.DetectionDeviceService;
import com.mecp.common.info.TeamService;
import com.mecp.common.info.TestStBasisService;
import com.mecp.constant.Constant;
import com.mecp.transaction.process.ServeDetailService;
import com.mecp.upload.UploadRecordService;
import com.mecp.user.UserInterceptor;
import com.mecp.user.UserService;

@Before(UserInterceptor.class)
public class ServeInfoController extends Controller {

	Logger logger = Logger.getLogger(ServeInfoController.class);
	@Inject
	CustomerService cusSrv;
	@Inject
	TeamService teamSrv;
	@Inject
	DetectionDeviceService detectionDeviceSrv;
	@Inject
	TestStBasisService testStBasisSrv;
	@Inject
	BasicInfomationService basicInfomationSrv;
	@Inject
	UserService userSrv;
	@Inject
	ServeUserService serUserSrv;
	@Inject
	ServeInfoService serInfoSrv;
	@Inject
	UploadRecordService uploadRecordSrv;
	@Inject
	TestStBasisService codSrv;
	@Inject
	ServeDetailService serveDetailService;

	public void index() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("user", user);
		render("/views/serveinfo/serveinfo.html");
	}

	// 显示个人账户名下历史 服务配置
	public void serviceContent() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		Page<ServeUser> page = serUserSrv.paginate(getParaToInt("page"), getParaToInt("limit"), user.getUsername());
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}

	// 增加 服务配置
	public void addService() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		ServeUser sUser = getBean(ServeUser.class);
		sUser.setName(getPara("name"));
		sUser.setUsername(user.getUsername());
		sUser.setCreatTime(new Date());
		sUser.setPowerPlantName(getPara("plant_name"));
		sUser.setStates(Constant.SERVICE_USER_STATUS_DJ);
		sUser.setProgress(Constant.PROGESS0);
		sUser.setUpdateTime(new Date());
		sUser.setEditer(user.getUsername());
		sUser.save();
		setAttr("code", 0);
		renderJson();
	}

	// 增加 服务配置 详细
	public void addServiceDetail() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		ServeUser sUser = serUserSrv.getById(getParaToInt("id"));
		String json = getPara("json").replace("[", "");
		json = json.replace("]", "");
		String[] array = json.split(",");
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < array.length; i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", Integer.parseInt(array[i]));
			jsonObject.put("name", serInfoSrv.getById(Integer.parseInt(array[i])).getInspectionItems());
			jsonObject.put("states", Constant.DG_SERVICE_USER_STATUS_WPZ);
			jsonObject.put("pinc", 1);
			jsonObject.put("serve_detail_id", 0);
			jsonArray.add(jsonObject);
		}
		sUser.setInfo(jsonArray.toJSONString());
		sUser.setProgress(Constant.PROGESS10); // 服务已配置
		sUser.setUpdateTime(new Date());
		sUser.setEditer(user.getUsername());
		sUser.update();
		setAttr("code", 0);
		setAttr("data", jsonArray);
		renderJson();
	}

	// 查看 服务配置 明细
	public void configService() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId = getParaToInt("id");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		setAttr("user", user);
		setAttr("serUser", serveUser);
		setAttr("serinfo", serInfoSrv.getAll());
		render("/views/serveinfo/config_service.html");
	}

	// 查看 服务配置 明细
	public void serviceDetail() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId = getParaToInt("id");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		setAttr("report_states", "未上传");
		setAttr("js_states", "未结算");
		if (serveUser.getContractId() != 0) {
			setAttr("contrack", "已签署");
			setAttr("step", 1);
			UploadRecord uploadRecord = uploadRecordSrv.getById(serveUser.getContractId());
			setAttr("contrackPath", uploadRecord.getPath());
		} else {
			setAttr("contrack", "未签署");
			setAttr("step", 0);
		}
		JSONArray jsonArray = JSONArray.parseArray(serveUser.getInfo());
		if (jsonArray == null || jsonArray.equals("")) {
			setAttr("count", 0);
			setAttr("total", 0);
		} else {
			int count = 0;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if (jsonObject.get("states").equals(Constant.DG_SERVICE_USER_STATUS_YJC)) {
					count++;
					setAttr("step", 2);
				}
			}
			setAttr("count", count);
			setAttr("total", jsonArray.size());
		}
		
		if (serveUser.getConciseReport()!= 0) {
			setAttr("step", 3);
			setAttr("report_states", "简洁报告已上传");
			ServeDetail serveDetail = serveDetailService.getById(serveUser.getConciseReport());
			setAttr("c_reportPath", serveDetail.getId());
		}
		
		if (serveUser.getReportId()!= 0) {
			setAttr("step", 3);
			setAttr("report_states", "完整报告已上传");
			UploadRecord uploadRecord = uploadRecordSrv.getById(serveUser.getReportId());
			setAttr("reportPath", uploadRecord.getPath());
		}

		if (serveUser.getSettlementMaterialsId() != 0) {
			setAttr("step", 4);
			setAttr("js_states", "已结算");
			UploadRecord uploadRecord = uploadRecordSrv.getById(serveUser.getSettlementMaterialsId());
			setAttr("setMaterPath", uploadRecord.getPath());
		}

		setAttr("user", user);
		setAttr("serUser", serveUser);
		setAttr("serinfo", serInfoSrv.getAll());
		render("/views/serveinfo/serverdetail.html");
	}

	// 查看 服务配置 明细
	public void quseryServiceDetail() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId = getParaToInt("id");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		JSONArray jsonArray = JSONArray.parseArray(serveUser.getInfo());
		JSONArray resultArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			jsonObject.put("p", teamSrv.getTeamById(jsonObject.getIntValue("pinc")).getName());
			resultArray.add(jsonObject);
		}
		setAttr("code", 0);
		setAttr("user", user);
		setAttr("team", teamSrv.getAllTeam());
		setAttr("data", resultArray);
		renderJson();
	}

	// 查看不同type对应不同的服务项
	public void serviceInfoByType() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("code", 0);
		setAttr("user", user);
		setAttr("data", serInfoSrv.getInfoByType(getPara("type")));
		renderJson();
	}

	
	
	public void getServiceInfo(){
		Page<ServeInfo> page = serInfoSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}

	public void serviceInfoEdit(){
		render("/views/serveinfo/serveredit.html");
	}
	
	//添加服务项目
	public void addServiceInfo(){
		ServeInfo serveInfo = new ServeInfo();
		serveInfo.setAcceptanceStandard(getPara("acceptance_standard"));
		serveInfo.setInspectionItems(getPara("inspection_items"));
		serveInfo.setType(getPara("type"));
		serveInfo.save();
		setAttr("code", 0);
		renderJson();
	}
	
	public void delServiceInfo(){
		ServeInfo serveInfo = serInfoSrv.getById(getParaToInt("id"));
		serveInfo.delete();
		setAttr("code", 0);
		renderJson();
	}
	
	public void editServiceInfo(){
		ServeInfo serveInfo = serInfoSrv.getById(getParaToInt("id"));
		serveInfo.setAcceptanceStandard(getPara("acceptance_standard"));
		serveInfo.setInspectionItems(getPara("inspection_items"));
		serveInfo.setType(getPara("type"));
		serveInfo.update();
		setAttr("code", 0);
		renderJson();
	}
	
	public void gotoTestDetecDeviceHtml(){
		render("/views/serveinfo/detecdevice.html");
	}
	
	public void getDetecDevice(){
		Page<DetectionDevice> page = detectionDeviceSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	
	//跳转至检测标准及依据编辑页面
	public void gotoCodHtml(){
		render("/views/serveinfo/cod.html");
	}
	
	//查询检测标准及依据
	public void getCod(){
		Page<TestStBasis> page = codSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	//添加检测标准及依据
	public void addCod(){
		TestStBasis tsBasis = new TestStBasis();
		tsBasis.setStandard(getPara("standard"));
		tsBasis.setStandardName(getPara("standard_name"));
		tsBasis.setStandardNumber(getPara("standard_number"));
		tsBasis.setFileId(getParaToLong("file_id"));
		UploadRecord uploadRecord = uploadRecordSrv.getById(getParaToInt("file_id"));
		tsBasis.setStandardFile(uploadRecord.getFileName());
		tsBasis.save();
		setAttr("code", 0);
		renderJson();
	}
	
	//删除检测标准及依据
	public void deletCod(){
		TestStBasis testStBasis = codSrv.queryById(getParaToInt("id"));
		testStBasis.delete();
		setAttr("code", 0);
		renderJson();
	}
	
	//编辑检测标准及依据
	public void editCod(){
		TestStBasis tsBasis = testStBasisSrv.queryById(getParaToInt("id"));
		tsBasis.setStandard(getPara("standard"));
		tsBasis.setStandardName(getPara("standard_name"));
		tsBasis.setStandardNumber(getPara("standard_number"));
		tsBasis.setFileId(getParaToLong("file_id"));
		tsBasis.update();
		setAttr("code", 0);
		renderJson();
	}
	
	
	
		//跳转服务团队编辑页面
		public void gotoTeamHtml(){
			render("/views/serveinfo/team.html");
		}
		
		//删除团队成员
		public void deletTeam(){
			Team team = teamSrv.getTeamById(getParaToInt("id"));
			team.delete();
			setAttr("code", 0);
			renderJson();
		}

		public void getTeam(){
			Page<Team> list =  teamSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
			setAttr("data", list.getList());
			setAttr("code", 0);
			setAttr("count", list.getTotalRow());
			renderJson();
		}
		
		public void editTeam(){
			Team team = teamSrv.getTeamById(getParaToInt("id"));
			team.setBirth(getParaToDate("birth"));
			team.setEmail(getPara("email"));
			team.setInfo(getPara("info"));
			team.setName(getPara("name"));
			team.setPhone(getPara("phone"));
			team.setPosition(getPara("position"));
			team.update();
			setAttr("code", 0);
			renderJson();
		}
		
		public void addTeam(){
			Team team = new Team();
			team.setBirth(getParaToDate("birth"));
			team.setEmail(getPara("email"));
			team.setInfo(getPara("info"));
			team.setName(getPara("name"));
			team.setPhone(getPara("phone"));
			team.setPosition(getPara("position"));
			UploadRecord uploadRecord = uploadRecordSrv.getById(getParaToInt("file_id"));
			team.setAvatar(uploadRecord.getFileName());
			team.save();
			setAttr("code", 0);
			renderJson();
		}
}
