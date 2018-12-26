package com.demo.index;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import com.demo.common.model.Message;
import com.demo.common.model.Uploadinfo;
import com.demo.common.model.User;
import com.demo.common.model.Yuye;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.mecp.user.UserInterceptor;
import com.mecp.user.UserService;
import com.mecp.utils.MD5Utils;

/**
 * IndexController
 */
@Before(UserInterceptor.class)
public class IndexController extends Controller {

	@Inject
	UserService userSrv;

	@Inject
	UoloadInfoService upSrv;
	
	
	
	@Clear()
	public void index() {
		render("/views/user/login.html");
	}

	@Clear()
	public void login(){
		User user = userSrv.findByUserName(getPara("username"));
		if(user!=null){
			if(MD5Utils.getMD5String(getPara("password")).equals(user.getPassword())){
				//cookies需要设置编码格式
				setCookie("username", user.getUsername(), 1000*60*60*24);
				setCookie("password", user.getPassword(), 1000*60*60*24);
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
		if (userSrv.findByUserName(user.getUsername()) != null) {
			setAttr("code", 201);
			setAttr("msg", "注册失败,用户名已注册！");
			renderJson();
		} else {
			if(getPara("repass").equals(getPara("password"))){
			user.setPowerPlantName(getPara("power_plant_name"));
			user.setProvince(getPara("province"));
			user.setCompany(getPara("company"));
			user.setElectricity(getPara("electricity"));
			user.setContactName(getPara("contact_name"));
			user.setPhone(getPara("phone"));
			user.setRid(0);
			user.setPassword(MD5Utils.getMD5String(getPara("password")));
			user.setCreatTime(new Date());
			user.save();
			for(User admin : userSrv.findAllAdmin()){
				Message message = new Message();
				message.setContent("用户注册:"+user.getUsername()+",请及时审核并解冻账号！");
				message.setCreatTime(new Date());
				message.setIsRead(false);
				message.setSender("系统");
				message.setReceiver(admin.getUsername());
				message.setTitle("用户注册");
				message.save();
				admin.setUnread(true);
				admin.update();
			}
			setAttr("code", 200);
			setAttr("msg", "注册成功！");
			renderJson();
			}else{
				setAttr("code", 201);
				setAttr("msg", "密码不一致");
				renderJson();
			}}
	}
	
	public void loginOut(){
		getSession().removeAttribute("loginUser");
		redirect("/views/user/login.html");
	}
	
	public void fuck(){
		setAttr("code", 200);
		renderJson();
		List<Uploadinfo> list = upSrv.findAllUser();
		for(int i=0;i<list.size();i++){
			System.out.println(
					HttpClient.doPost(
				"http://xawxfw.xacg.gov.cn/Urban/rest/saveComplainInfo?"
					,"openid=" +list.get(i).getOpenid()
						+ "&telephone=" +list.get(i).getTelephone()
							+ "&name=" +list.get(i).getName()
								+ "&content=" +list.get(i).getContent()
									+ "&dataTime=" +list.get(i).getDataTime()
										+ "&picturepath1=" +list.get(i).getPicturepath1()
									+ "&picturepath2=" +list.get(i).getPicturepath2()
								+ "&jingdu= "+list.get(i).getLon()
							+ "&weidu= " +list.get(i).getLat()
						+ "&address="+list.get(i).getAddress()
					+ "&wentidizhi="+list.get(i).getWentidizhi()
						)
					);
			  if(i==(list.size()-1)){
				  i=0;
			  }
			try {
				System.out.println(i);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Clear()
	public void yuyeReg(){
		Yuye yuye = new Yuye();
		yuye.setAccount(getPara("account"));
		yuye.setPhone(getPara("phone"));
		yuye.setPassword(getPara("password"));
		yuye.setCreatTime(new Date());
		yuye.save();
		setAttr("code", 200);
		renderJson();
	}

}
