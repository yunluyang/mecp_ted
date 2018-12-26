package com.mecp.service.scheme;

import com.jfinal.core.Controller;

public class ServiceSchemeController extends Controller {
	
	public void index(){
		redirect("/views/servicescheme/servicescheme.html");
	}
	
}
