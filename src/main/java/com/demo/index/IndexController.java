package com.demo.index;

import com.demo.common.model.User;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.mecp.user.UserService;
import com.mecp.utils.MD5Utils;

/**
 * IndexController
 */
public class IndexController extends Controller {
	
	@Inject
	UserService userSrv;
	
	public void index() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		user.setPassword("");
		setAttr("user", user);
		render("index.html");
	}
	
	@Clear()
	public void login(){
		User user = userSrv.findByUserName(getPara("username"));
		if(user!=null){
			if(MD5Utils.getMD5String(getPara("password")).equals(user.getPassword())){
				getSession().setMaxInactiveInterval(-1);
				setSessionAttr("loginUser", user.getUsername());
				setAttr("user", user);
				renderJson();
			}else{
				setAttr("code", 201);
				setAttr("msg","用户名或密码错误");
				renderJson();
			}
		}else{
			setAttr("code", 201);
			setAttr("msg","用户名或密码错误");
			renderJson();
		}
	}
	
	@Clear()
	public void register() {
		User user = new User();
		user.setUsername(getPara("username"));
		if(userSrv.findByUserName(user.getUsername())!=null){
			setAttr("code", 201);
			setAttr("msg","注册失败,用户名已注册！");
			renderJson();
		}else{
			user.setPassword(MD5Utils.getMD5String(getPara("password")));
			user.save();
			setAttr("code", 200);
			setAttr("msg","注册成功！");
			renderJson(); 
		}
	}
	
}



