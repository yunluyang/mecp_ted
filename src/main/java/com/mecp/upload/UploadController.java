package com.mecp.upload;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.ServeDetail;
import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.demo.common.model.UploadRecord;
import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.mecp.common.info.BasicInfomationService;
import com.mecp.common.info.CustomerService;
import com.mecp.common.info.DetectionDeviceService;
import com.mecp.common.info.TeamService;
import com.mecp.common.info.TestStBasisService;
import com.mecp.constant.Constant;
import com.mecp.service.info.ServeInfoService;
import com.mecp.service.info.ServeUserService;
import com.mecp.transaction.process.ServeDetailService;
import com.mecp.transaction.process.TableTemplateService;
import com.mecp.user.UserService;
import com.mecp.utils.DateUtil;
import com.mecp.utils.FileService;
import com.mecp.utils.POIReadExcelToHtml;

public class UploadController extends Controller {

	Logger logger = Logger.getLogger(UploadController.class);
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
	ServeDetailService serDetailSrv;
	@Inject
	TableTemplateService tabTemplateSrv;
	@Inject
	UploadRecordService uploadRecordSrv;

	public void index() {
		renderJson();
	}

	public void upload() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		try {
			UploadFile file = getFile();
			UploadRecord uploadRecord = new UploadRecord();
			String filePath = file.getUploadPath() + "\\";
			if (getParaToInt("type") == Constant.UPLOAD_TYPE_REPORT) {
				filePath = filePath + "report" + "\\";
			} else if (getParaToInt("type") == Constant.UPLOAD_TYPE_CONTRACT) {
				filePath = filePath + "contract" + "\\";
			}else if (getParaToInt("type") == Constant.UPLOAD_TYPE_SM) {
				filePath = filePath + "settmaterial" + "\\";
			}
			String dateString = new Date().getTime() + "";
			System.out.println(filePath + dateString + file.getFileName());
			File movefile = new File(filePath + dateString + file.getFileName());
			FileService.copyFileUsingFileChannels(file.getFile(), movefile);
			file.getFile().delete();
			setAttr("filePath", movefile.getPath());
			setAttr("fileSize", movefile.length() / 1024 + "");
			uploadRecord.setEditor(user.getUsername());
			uploadRecord.setPath(movefile.getPath());
			uploadRecord.setInfoid(getParaToInt("id"));
			uploadRecord.setTime(new Date());
			uploadRecord.setFileName(movefile.getName());
			uploadRecord.setType(getParaToInt("type"));
			uploadRecord.save();
			ServeUser serveUser = serUserSrv.getById(getParaToInt("id"));
			if (getParaToInt("type") == Constant.UPLOAD_TYPE_REPORT) {  //上传完整报告
				serveUser.setReportId(uploadRecord.getId());
				 serveUser.setProgress(Constant.PROGESS80);
				serveUser.setStates(Constant.SERVICE_USER_STATUS_YJ);
			} else if (getParaToInt("type") == Constant.UPLOAD_TYPE_CONTRACT) { //上传合同
				serveUser.setContractId(uploadRecord.getId());
				setAttr("fileName", file.getFileName());
			}else if (getParaToInt("type") == Constant.UPLOAD_TYPE_SM) { //上传结算材料
				serveUser.setSettlementMaterialsId(uploadRecord.getId());
				setAttr("fileName", file.getFileName());
				serveUser.setProgress(Constant.PROGESS100);
				serveUser.setStates(Constant.SERVICE_USER_STATUS_WJ);
			}
			 serveUser.setUpdateTime(new Date());
			 serveUser.setEditer(user.getUsername());
			serveUser.update();
			setAttr("code", 0);
			renderJson();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("文件上传失败");
			setAttr("code", 1);
			renderJson();
		}
	}
	
	//公用方法
	public UploadRecord uploadByType(User user,UploadFile file,String type,int uploadType) throws IOException{
		UploadRecord uploadRecord = new UploadRecord();
		String filePath = file.getUploadPath() + "\\";
		filePath = filePath + type + "\\";
		String dateString = new Date().getTime() + "";
		File movefile = new File(filePath + dateString + file.getFileName());
		FileService.copyFileUsingFileChannels(file.getFile(), movefile);
		file.getFile().delete();
		setAttr("filePath", movefile.getPath());
		setAttr("fileSize", movefile.length() / 1024 + "");
		uploadRecord.setEditor(user.getUsername());
		uploadRecord.setPath(movefile.getPath());
		uploadRecord.setTime(new Date());
		uploadRecord.setFileName(movefile.getName());
		uploadRecord.setType(uploadType);
		uploadRecord.save();
		return uploadRecord;
	}
	
	//上传检测设备报告
	public void uploadDetecDevice() throws IOException{
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		UploadFile file = getFile();
		UploadRecord uploadRecord = uploadByType(user,file,"detecdevice",Constant.UPLOAD_TYPE_JCSB_REPORT);
		setAttr("code", 0);
		setAttr("urid", uploadRecord.getId());
		renderJson();
	}
	
	
	
	//上传检测标准及依据
		public void uploadCod() throws IOException{
			User user = userSrv.findByUserName(getSessionAttr("loginUser"));
			UploadFile file = getFile();
			UploadRecord uploadRecord = uploadByType(user,file,"cod",Constant.UPLOAD_TYPE_JCBZ_YJ);
			setAttr("code", 0);
			setAttr("urid", uploadRecord.getId());
			renderJson();
		}
		
		public void teamAvatar() throws IOException{
			User user = userSrv.findByUserName(getSessionAttr("loginUser"));
			UploadFile file = getFile();
			UploadRecord uploadRecord = uploadByType(user,file,"team",Constant.UPLOAD_TYPE_TEAM_AVATAR);
			setAttr("code", 0);
			setAttr("urid", uploadRecord.getId());
			renderJson();
		}
		
		public void uploadExcel() throws IOException{
			User user = userSrv.findByUserName(getSessionAttr("loginUser"));
			UploadFile file = getFile();
			UploadRecord uploadRecord = uploadByType(user,file,"excel",Constant.UPLOAD_TYPE_SERVER_DETAIL);
			//String path = uploadRecord.getPath().replaceAll("\\\\", "/");
		//	System.out.println(path);
			int serUserId= getParaToInt("serUserId");
			int serInfoId = getParaToInt("serInfoId");
			String table = POIReadExcelToHtml.readExcelToHtml(uploadRecord.getPath(), true);
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
	         			serveDetail.setUploadRecordId(uploadRecord.getId());
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
	         			serveDetail.setUploadRecordId(uploadRecord.getId());
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
			setAttr("code", 0);
			setAttr("urid", uploadRecord.getId());
			setAttr("result", table);
			renderJson();
		}
}
