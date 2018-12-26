package com.mecp.user;

import javax.servlet.http.HttpSession;

import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class UserInterceptor implements Interceptor{

	@Inject
	UserService userSrv;
	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		Controller controller = inv.getController();
		 
		System.out.println("Before method invoking");
		System.out.println("URL:"+controller.getRequest().getLocalAddr());
		
		HttpSession session = inv.getController().getSession(); 
		 if(session == null){
			 inv.getController().redirect("/views/user/login.html");
	        }  
	        else{  
	    		String nickname = (String) session.getAttribute("loginUser");  
	            if(nickname != null) {  
	                //System.out.println("hello");  
	        		inv.invoke();//对目标方法的调用,在这一行代码的前后插入切面代码可以很方便地实现AOP
	        		controller.setAttr("ContextPath", controller.getRequest().getContextPath() );
	        		System.out.println("After method invoking");
	        		System.out.println("ContextPath:"+controller.getRequest().getRequestURI());
	            }  
	            else {  
	            	inv.getController().redirect("/views/user/login.html");  
	            } 
	            User user = userSrv.findUserByName(nickname);
	            if(user.getStates()==0){
	            	inv.getController().renderJson("账号异常，请联系管理员");
	            }
	        }  
	}

}
