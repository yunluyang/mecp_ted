package com.mecp.upload;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.ServeDetail;
import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.demo.common.model.TableTemplate;
import com.demo.common.model.UploadRecord;
import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
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

public class DownloadController extends Controller{

	Logger logger  =  Logger.getLogger(DownloadController.class );
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
		renderJson();
	}
	
	public void downloadFile(){
		renderFile(new File(getPara("path")));
    }
	
	//查看设备信息报告
	public void findDetecDevicePdf(){
		UploadRecord uploadRecord = uploadRecordSrv.getById(getParaToInt("id"));
		renderFile(new File(uploadRecord.getPath()));
	}
	
	
    // 判断文件是否存在
    public static boolean judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
            return true;
        } else {
            System.out.println("file not exists, create it ...");
            return false;
        }

    }
}
