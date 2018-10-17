package com.mecp.service.info;

import java.util.List;

import org.apache.log4j.Logger;

import com.demo.common.model.Customer;
import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.common.info.BasicInfomationService;
import com.mecp.common.info.CommonInfoController;
import com.mecp.common.info.CustomerService;
import com.mecp.common.info.DetectionDeviceService;
import com.mecp.common.info.TeamService;
import com.mecp.common.info.TestStBasisService;
import com.mecp.user.UserService;

public class ServeInfoController extends Controller {
	
	
	Logger logger  =  Logger.getLogger(ServeInfoController.class );
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
	public void index(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("user", user);
		render("../views/serveinfo/serveinfo.html");
	}
	
	public void serviceContent(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		Page<ServeUser> page = serUserSrv.paginate(getParaToInt("page"), getParaToInt("limit"),user.getUsername());
		setAttr("data", page.getList());
		setAttr("code",0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	
	public void addService(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("code",0);
		renderJson();
	}

}
