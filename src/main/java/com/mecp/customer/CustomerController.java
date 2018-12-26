package com.mecp.customer;


import java.util.List;

import org.apache.log4j.Logger;

import com.demo.common.model.Company;
import com.demo.common.model.Customer;
import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.common.info.BasicInfomationService;
import com.mecp.common.info.CustomerService;
import com.mecp.common.info.DetectionDeviceService;
import com.mecp.common.info.TeamService;
import com.mecp.common.info.TestStBasisService;
import com.mecp.service.info.ServeInfoService;
import com.mecp.service.info.ServeUserService;
import com.mecp.user.UserService;

public class CustomerController extends Controller{
	
	Logger logger  =  Logger.getLogger(CustomerController.class );
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
	CompanyService  companyService;
	
	public void index(){
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		setAttr("user", user);
		setAttr("company",companyService.getAll());
		render("/views/commoninfo/customer2.html");
	}
	
	
	public void getCompanyProvince(){
		List<Company> list = companyService.getProvice(getPara("company"));
		if(list.size()==0){
			setAttr("province",null);
		}
		setAttr("province",list);
		renderJson();
	}
	
	
	public void getCustomer(){
		List<Customer> page;
		if(getPara("company")==null||getPara("company").equals("")){
			page = cusSrv.getAllCustomer();
		}else{
			page = cusSrv.getCustomer(getPara("company"), getPara("province"));
		}
		setAttr("data",page);
		renderJson();
	}
	
	
	public void gotoHtmlCustomer(){
		render("/views/commoninfo/customer_edit.html");
	}
	
	public void getCustomerByPage(){
		Page<Customer> page = cusSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("data", page.getList());
		setAttr("code", 0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	
	public void addCustomer(){
		String companyName = getPara("customer.company");
		Customer customer = getBean(Customer.class);
		customer.setCustomerAddress(getPara("customer_address"));
		customer.setCustomerName(getPara("customer_name"));
		customer.setCustomerContactName(getPara("customer_contact_name"));
		customer.setCustomerContact(getPara("customer_contact"));
		customer.setProvince(getPara("province"));
		customer.setCompany(getPara("customer.company"));
		customer.setLat(Double.parseDouble(getPara("customer.lat")));
		customer.setLong(Double.parseDouble(getPara("customer.long")));
		customer.save();
		Company company = companyService.getByCompanyName(companyName);
		if(company==null||company.equals("")){
			Company c = new Company();
			c.setName(companyName);
			c.save();
		}
		setAttr("code", 0);
		renderJson();
	}
	
	public void delCustomer(){
		Customer customer = cusSrv.findById(getParaToInt("id"));
		if(customer.delete()){
			setAttr("code", 0);
			renderJson();
		}else{
			setAttr("code", 1);
			renderJson();
		}
	}
	
	public void editCustomer(){
		Customer customer = cusSrv.findById(getParaToInt("id"));
		customer.setCustomerAddress(getPara("customer_address"));
		customer.setCustomerName(getPara("customer_name"));
		customer.setCustomerContactName(getPara("customer_contact_name"));
		customer.setProvince(getPara("province"));
		customer.setCompany(getPara("company"));
		customer.setLat(Double.parseDouble(getPara("lat")));
		customer.setLong(Double.parseDouble(getPara("long")));
		customer.setCustomerContact(getPara("customer_contact"));
		customer.update();
		setAttr("code", 0);
		renderJson();
	}
	
	/**
	 * param id=客户id
	 */
	public void updateCustomer(){
		Customer customer = cusSrv.findById(getParaToInt("id"));
		customer.setLat(Double.parseDouble(getPara("lat")));
		customer.setLong(Double.parseDouble(getPara("lng")));
		customer.update();
		setAttr("code", 0);
		renderJson();
	}
	

}
