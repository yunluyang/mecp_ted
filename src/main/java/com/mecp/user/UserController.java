package com.mecp.user;


import java.util.Date;

import org.apache.log4j.Logger;

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
import com.mecp.transaction.process.ServeDetailService;
import com.mecp.transaction.process.TableTemplateService;
import com.mecp.upload.UploadRecordService;
import com.mecp.user.UserService;
import com.mecp.utils.MD5Utils;


@Before(UserInterceptor.class)
public class UserController extends Controller{

	Logger logger  =  Logger.getLogger(UserController.class );
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
	
	
	public void getAllUser(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		Page<User> page = userSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("code", 0);
		setAttr("data", page.getList());
		setAttr("count", page.getTotalRow());
		renderJson();
    }
	
	public void addUserByAdmin(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		User changeUser = getModel(User.class);
		if (userSrv.findByUserName(changeUser.getUsername()) != null) {
			setAttr("code", 201);
			setAttr("msg", "注册失败,用户名已注册！");
			renderJson();
		} else {
			changeUser.setRid(0);
			changeUser.setPassword(MD5Utils.getMD5String("123456"));
			changeUser.setCreatTime(new Date());
			changeUser.save();
			setAttr("code", 200);
			setAttr("msg", "注册成功！");
			renderJson();
		}
	}
	
	public void delUser(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		User changeUser = userSrv.findById(getParaToInt("id"));
		if(user.getRid()!=Constant.USER_NORMAL){
			changeUser.delete();
			setAttr("code", 0);
			renderJson();
		}else {
			setAttr("msg", "无权限");
			setAttr("code", 1);
			renderJson();
		}
	}
	
	public void changePassWord(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		User changeUser = userSrv.findById(getParaToInt("id"));
		if(user.getRid()==Constant.USER_ADMIN||user.getRid()==Constant.USER_ADMIN_SUPER){
			changeUser.setPassword(MD5Utils.getMD5String("123456"));
			if(changeUser.update()){
				setAttr("code", 0);
				renderJson();
			}else{
				setAttr("code", 1);
				renderJson();
			}
		}else{
			setAttr("code", 1);
			setAttr("msg", "无权限");
			renderJson();
		}
		
	}
	
	public void updateUser(){
		User changeUser = userSrv.findById(getParaToInt("id"));
		changeUser.setCompany(getPara("company"));
		changeUser.setContactName(getPara("contact_name"));
		changeUser.setElectricity(getPara("electricity"));
		changeUser.setPhone(getPara("phone"));
		changeUser.setPowerPlantName(getPara("power_plant_name"));
		changeUser.setProvince(getPara("province"));
		changeUser.setElectricity(getPara("electricity"));
		if(changeUser.update()){
			setAttr("code", 0);
			renderJson();
		}else{
			setAttr("code", 1);
			renderJson();
		}
	}
	
	public void changeStatus(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		User changeUser = userSrv.findById(getParaToInt("id"));
		if(changeUser.getRid()==Constant.USER_ADMIN_SUPER||changeUser.getUsername().equals(user.getUsername())){
			setAttr("code", 1);
			setAttr("msg", "无权限");
			renderJson();
		}else{
			if(changeUser.getStates()==0){
				changeUser.setStates(1);
			}else{
				changeUser.setStates(0);
			}
			changeUser.update();
			setAttr("states", changeUser.getStates());
			setAttr("code", 0);
			renderJson();
		}
	}
	
	public void changeRid(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		User changeUser = userSrv.findById(getParaToInt("id"));
		if(user.getRid()==Constant.USER_ADMIN_SUPER&&!(changeUser.getUsername().equals(user.getUsername()))){
			if(changeUser.getRid()==Constant.USER_NORMAL){
				changeUser.setRid(Constant.USER_ADMIN);
			}else if(changeUser.getRid()==Constant.USER_ADMIN){
				changeUser.setRid(Constant.USER_ADMIN_SUPER);
			}else {
				changeUser.setRid(Constant.USER_NORMAL);
			}
			changeUser.update();
			setAttr("rid", changeUser.getRid());
			setAttr("code", 0);
			renderJson();
		}else {
			setAttr("msg", "无权限");
			setAttr("code", 1);
			renderJson();
		}
	}
	
	
	public void userChangePassWord(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		String oldPassword = MD5Utils.getMD5String(getPara("oldPassword"));
		String password = getPara("password");
		String repassword = getPara("repassword");
		if(user.getPassword().endsWith(oldPassword)&&password.equals(repassword)){
			user.setPassword(oldPassword);
			user.update();
			setAttr("code", 0);
			renderJson();
		}else{
			setAttr("code", 1);
			renderJson();
		}
	}
	
	
}
