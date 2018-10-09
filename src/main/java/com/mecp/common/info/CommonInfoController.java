package com.mecp.common.info;

import org.apache.log4j.Logger;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;

/**
 * IndexController
 */
public class CommonInfoController extends Controller {
	Logger logger  =  Logger.getLogger(CommonInfoController.class );
	@Inject
	CustomerService cusSrv ;
	public void index() {
			redirect("customer.html");
		}
	
	
	public void getCustomers(){
		setAttr("data", cusSrv.getAllCustomer());
		setAttr("code",0);
		setAttr("count", 1);
		renderJson();
	}
}