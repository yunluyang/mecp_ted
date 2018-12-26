package com.mcep.message;

import java.util.Date;

import com.demo.common.model.Message;
import com.demo.common.model.User;
import com.demo.index.UoloadInfoService;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.user.UserService;

/**
 * IndexController
 */
public class MessageController extends Controller {

	@Inject
	UserService userSrv;

	@Inject
	UoloadInfoService upSrv;
	@Inject
	MessageService msgSrv;
	
	public void index() {
		render("");
	}
	
	public void myMessage(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		Page<Message> page = msgSrv.paginate(getParaToInt("page"), getParaToInt("limit"), user.getUsername());
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	
	//跳转至消息详细html界面
	public void gotodetailHtml(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		Message  msg = msgSrv.getById(getParaToInt("id"));
		if(user.getUsername().equals(msg.getReceiver())){
			msg.setIsRead(true);
			if(msg.getReadTime()==null||msg.getReadTime().equals("")){
				msg.setReadTime(new Date());
			}
			msg.update();
			setAttr("code", 0);
			setAttr("msg", msg);
			render("/views/app/message/detail.html");
		}else{
			setAttr("code", 1);
			render("/views/template/tips/404.html");
		}
		
	}

	
}
