package com.mecp.transaction.process;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.Message;
import com.demo.common.model.ServeDetail;
import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.demo.common.model.TableTemplate;
import com.demo.common.model.Team;
import com.demo.common.model.UploadRecord;
import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.common.info.BasicInfomationService;
import com.mecp.common.info.CustomerService;
import com.mecp.common.info.DetectionDeviceService;
import com.mecp.common.info.TeamService;
import com.mecp.common.info.TestStBasisService;
import com.mecp.constant.Constant;
import com.mecp.service.info.ServeInfoService;
import com.mecp.service.info.ServeUserService;
import com.mecp.upload.UploadRecordService;
import com.mecp.user.UserInterceptor;
import com.mecp.user.UserService;
import com.mecp.utils.DateUtil;
@Before(UserInterceptor.class)
public class TransactionProcessController extends Controller{

	Logger logger  =  Logger.getLogger(TransactionProcessController.class );
	@Inject
	CustomerService cusSrv ;
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
	ServeDetailService serDetailSrv;
	@Inject
	TableTemplateService tabTemplateSrv;
	@Inject
	UploadRecordService uploadRecordSrv;
	public void index(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("user", user);
		render("/views/tprocess/service_content_config.html");
	}
	
	//显示个人账户名下历史       服务配置
	public void serviceContent(){
			User user = userSrv.findByUserName(getSessionAttr("loginUser"));
			Page<ServeUser> page = serUserSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
			System.out.println("list:"+page.getList());
			setAttr("data", page.getList());
			setAttr("code",0);
			setAttr("count", page.getTotalRow());
			renderJson();
		}
	
	public void personLiable(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId= getParaToInt("id");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		if(serveUser.getConciseReport()==0){
			TableTemplate tableTemplate = tabTemplateSrv.getById(1);
			setAttr("table", tableTemplate.getDetail());
		}else{
			ServeDetail serveDetail = serDetailSrv.getById(serveUser.getConciseReport());
			List<UploadRecord> list = uploadRecordSrv.getAll(Constant.UPLOAD_TYPE_REPORT, serUserId);
			setAttr("list", list);
			setAttr("table", serveDetail.getDetail());
		}
		if(serveUser.getContractId()==0||serveUser.getContractId()==null){
			//尚未上传合同
			
		}else{
			//已上传合同且最新id 查询历史上传记录
			List<UploadRecord> list = uploadRecordSrv.getAll(Constant.UPLOAD_TYPE_CONTRACT, serUserId);
			setAttr("contract_list", list);
		}
		if(serveUser.getSettlementMaterialsId()==0||serveUser.getSettlementMaterialsId()==null){
			//尚未上传合同
			
		}else{
			//已上传合同且最新id 查询历史上传记录
			List<UploadRecord> list = uploadRecordSrv.getAll(Constant.UPLOAD_TYPE_SM, serUserId);
			setAttr("sm_list", list);
		}
		setAttr("code",0);
		setAttr("user", user);
		setAttr("serUserId", serUserId);
		setAttr("serUser", serveUser);
		setAttr("data", JSONArray.parse(serUserSrv.getById(serUserId).getInfo()));
		setAttr("team", teamSrv.getNameAllTeam());
		render("/views/tprocess/person_liable.html");
	}
	
	//编辑检测项目责任人
	public void editPinC(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		String s = serUserSrv.getById(getParaToInt("serUserId")).getInfo();
		 ServeUser serveUser = serUserSrv.getById(getParaToInt("serUserId"));
		JSONArray jsonArray = JSONArray.parseArray(s);
		JSONArray resultArray = new JSONArray();
		   for(int i=0;i<jsonArray.size();i++){
               JSONObject job = jsonArray.getJSONObject(i);//把每一个对象转成json对象
               job.put("pinc", Integer.parseInt(getPara(job.getInteger("id")+"")));
               
               Team team = teamSrv.getTeamById(Integer.parseInt(getPara(job.getInteger("id")+"")));
               Message message = new Message();
			   message.setContent("检测项目("+serveUser.getName()+"),分配您检测,请及时处理");
			   message.setCreatTime(new Date());
			   message.setIsRead(false);
			   message.setSender("系统");
			   message.setReceiver(team.getUsername());
			   message.setTitle("检测项目责任分配");
			   message.save();
               
               
               if(job.get("states").equals(Constant.DG_SERVICE_USER_STATUS_WPZ)){
                   job.put("states", Constant.DG_SERVICE_USER_STATUS_DJC);
               }
               resultArray.add(job);
		   }
		 serveUser.setInfo(resultArray.toJSONString());
		 serveUser.setStates(Constant.SERVICE_USER_STATUS_DJ);
		 serveUser.setProgress(Constant.PROGESS20);
		 serveUser.setStartTime(new Date());
		 serveUser.setUpdateTime(new Date());
		 serveUser.setEditer(user.getUsername());
		 serveUser.update();
		setAttr("code",0);
		renderJson();
	}
	
	
	
	//检测数据录入 跳转
	public void jumpToTable(){
		int serUserId= getParaToInt("serUserId");
		int serInfoId = getParaToInt("serInfoId");
		ServeInfo serveInfo = serInfoSrv.getById(serInfoId);
		ServeUser serveUser = serUserSrv.getById(serUserId);
		JSONArray jsonArray = JSONArray.parseArray(serUserSrv.getById(serUserId).getInfo());
		 for(int i=0;i<jsonArray.size();i++){
             JSONObject job = jsonArray.getJSONObject(i);//把每一个对象转成json对象
             int serve_detail_id = job.getInteger("serve_detail_id");
             if(job.getInteger("id")==serInfoId)
             {
            	 if(serve_detail_id!=0){
              		ServeDetail serveDetail = serDetailSrv.getById(serve_detail_id);
                	setAttr("table", serveDetail.getDetail());
                	UploadRecord uploadRecord = uploadRecordSrv.getById(serveDetail.getUploadRecordId());
                	setAttr("path", uploadRecord.getPath());
                	
            	 }else if(serve_detail_id==0&&serveInfo.getTemplateId()!=0){
                 	ServeDetail serveDetail = serDetailSrv.getById(serveInfo.getTemplateId());
                 	setAttr("table", serveDetail.getDetail());
                	UploadRecord uploadRecord = uploadRecordSrv.getById(serveDetail.getUploadRecordId());
                	setAttr("path", uploadRecord.getPath());
                 }
             }
		   }
		setAttr("serUser", serveUser);
		setAttr("serveInfo", serveInfo);
		setAttr("code",0);
		render("/views/tprocess/table.html");
	}
	
	
	
	//保存简洁报告
	public void saveConciseReport(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId= getParaToInt("serUserId");
		String table = getPara("table");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		ServeDetail serveDetail;
		if(serveUser.getConciseReport()!=0||serveUser.getConciseReport()==null){
			serveDetail = serDetailSrv.getById(serveUser.getConciseReport());
			serveDetail.setDetail(table);
			serveDetail.setServeUserId(serUserId);
			serveDetail.setInspectionItems(Constant.CONCISE_REPORT);
			serveDetail.setEditor(user.getUsername());	
			serveDetail.setTime(new Date());
			serveDetail.update();
		}else{
			serveDetail = new ServeDetail();
			serveDetail.setDetail(table);
			serveDetail.setServeUserId(serUserId);
			serveDetail.setInspectionItems(Constant.CONCISE_REPORT);
			serveDetail.setEditor(user.getUsername());	
			serveDetail.setTime(new Date());
			serveDetail.save();
		}
		serveUser.setConciseReport(serveDetail.getId()); 
		 serveUser.setProgress(Constant.PROGESS60);
		 serveUser.setUpdateTime(new Date());
		 serveUser.setEditer(user.getUsername());
		serveUser.update();
		setAttr("code",0);
		renderJson();
	}
	
	
	
	public void saveTable(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		int serUserId= getParaToInt("serUserId");
		int serInfoId = getParaToInt("serInfoId");
		String table = getPara("table");
		ServeUser serveUser = serUserSrv.getById(serUserId);
		ServeInfo serveInfo = serInfoSrv.getById(serInfoId);
		JSONArray jsonArray = JSONArray.parseArray(serUserSrv.getById(serUserId).getInfo());
		JSONArray resultArray = new JSONArray();
		ServeDetail serveDetail = null;
		 for(int i=0;i<jsonArray.size();i++){
             JSONObject job = jsonArray.getJSONObject(i);//把每一个对象转成json对象
             int serve_detail_id = job.getInteger("serve_detail_id");
             if(job.getInteger("id")==serInfoId)
             {
         		if(serve_detail_id!=0){
         			//更新表格
         			serveDetail = serDetailSrv.getById(serve_detail_id);
         			serveDetail.setDetail(table);
         			serveDetail.setTime(new Date());
         			serveDetail.setEditor(user.getUsername());
         			serveDetail.update();
         		}else{
         			//新建表格
         			serveDetail = new ServeDetail();
         			serveDetail.setInfoId(serInfoId);
         			serveDetail.setServeUserId(serUserId);
         			serveDetail.setInspectionItems(serveInfo.getInspectionItems());
         			serveDetail.setDetail(table);
         			serveDetail.setEditor(user.getUsername());
         			serveDetail.setTime(new Date());
         			serveDetail.save();
             		job.put("serve_detail_id", serveDetail.getId());
             		job.put("states", Constant.DG_SERVICE_USER_STATUS_YJC);
         		}
             }
             resultArray.add(job);
		   }
		 serveUser.setInfo(resultArray.toJSONString());
		 serveUser.setStates(Constant.SERVICE_USER_STATUS_ZJ);
		 serveUser.setProgress(Constant.PROGESS40);
		 serveUser.setUpdateTime(new Date());
		 serveUser.setEditer(user.getUsername());
		 serveUser.update();
		setAttr("code",0);
		renderJson();
	}
	
	public void lookTable(){
		int serDetailId= getParaToInt("serDetailId");
		ServeDetail serveDetail = serDetailSrv.getById(serDetailId);
		setAttr("code",0);
		setAttr("time", DateUtil.dateToStr(serveDetail.getTime()));
		setAttr("table", serveDetail.getDetail());
		setAttr("serveDetail",serveDetail);
		render("/views/tprocess/table_look.html");
	}
}
