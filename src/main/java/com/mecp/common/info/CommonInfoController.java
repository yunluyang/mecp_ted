package com.mecp.common.info;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.BasicInfomation;
import com.demo.common.model.Customer;
import com.demo.common.model.DetectionDevice;
import com.demo.common.model.TestStBasis;
import com.demo.common.model.User;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.mecp.user.UserService;

/**
 * CommonInfoController
 */
public class CommonInfoController extends Controller {
	
	
	Logger logger  =  Logger.getLogger(CommonInfoController.class );
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
	
	public void index() {
		User user = userSrv.findByUserName(getSessionAttr("loginUser"));
		System.out.println("user:"+getSessionAttr("loginUser"));
		user.setPassword("");
		setAttr("user", user);
		render("/views/index.html");
		}
	
	
	public void getCustomers(){
		Page<Customer> page = cusSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("data", page.getList());
		setAttr("code",0);
		setAttr("count", page.getTotalRow());
		renderJson();
	}
	
	/**
	 * 获取所有用户信息
	 */
	public void getListCustomers(){
		List<Customer> page = cusSrv.getAllCustomer();
		setAttr("data", page);
		renderJson();
	}
	
	public void teamlistHtml(){
		setAttr("teamlist", teamSrv.getAllTeam());
		render("../views/commoninfo/teamlist.html");
	}
	
	public void getDetectionDevice(){
		Page<DetectionDevice> page = detectionDeviceSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("code",0);
		setAttr("count", page.getTotalRow());
		setAttr("data", page.getList());
		renderJson();
	}
	
	public void detectionDeviceHtml(){
		render("../views/commoninfo/detectiondevice.html");
	}
	
	public void getTestStBasisDevice(){
		Page<TestStBasis> page = testStBasisSrv.paginate(getParaToInt("page"), getParaToInt("limit"));
		setAttr("code",0);
		setAttr("count", page.getTotalRow());
		setAttr("data", page.getList());
		renderJson();
	}
	
	
	/**
	 * 获取检测与标准页面
	 */
	public void testStBasisHtml(){
		render("../views/commoninfo/standard.html");
	}
	
	/**
	 * 获取指定光伏组件数据
	 * 
	 */
	public void getBasicInfomation(){
		Page<BasicInfomation> page = basicInfomationSrv.paginate(getParaToInt("page"), getParaToInt("limit"),getPara("type"));
		setAttr("code",0);
		setAttr("count", page.getTotalRow());
		setAttr("data", page.getList());
		renderJson();
	}
	
	/**
	 * 获取光伏组件信息
	 */
	public void getPvm(){
		List<BasicInfomation> page = basicInfomationSrv.getModelByType("光伏组件");
		BasicInfomation model = basicInfomationSrv.getModelById(getParaToInt("id"));
		JSONObject jsonObject = JSON.parseObject(model.getInfo());
		setAttr("model",jsonObject);
		setAttr("id",getParaToInt("id"));
		setAttr("list", page);
		render("../views/commoninfo/pvmodule_edit.html");
	}
	
	/**
	 * 获取汇流箱信息
	 */
	public void getHeaderBox(){
		List<BasicInfomation> page = basicInfomationSrv.getModelByType("汇流箱");
		BasicInfomation model = basicInfomationSrv.getModelById(getParaToInt("id"));
		JSONObject jsonObject = JSON.parseObject(model.getInfo());
		setAttr("model",jsonObject);
		setAttr("id",getParaToInt("id"));
		setAttr("list", page);
		render("../views/commoninfo/headerbox_edit.html");
	}
	
	/**
	 * 获取逆变器信息
	 */
	public void getInverter(){
		List<BasicInfomation> page = basicInfomationSrv.getModelByType("逆变器");
		BasicInfomation model = basicInfomationSrv.getModelById(getParaToInt("id"));
		JSONObject jsonObject = JSON.parseObject(model.getInfo());
		setAttr("model",jsonObject);
		setAttr("id",getParaToInt("id"));
		setAttr("list", page);
		render("../views/commoninfo/inverter_edit.html");
	}
	
	/**
	 * 获取SVG信息
	 */
	public void getSvg(){
		List<BasicInfomation> page = basicInfomationSrv.getModelByType("静止无功发生器");
		BasicInfomation model = basicInfomationSrv.getModelById(getParaToInt("id"));
		JSONObject jsonObject = JSON.parseObject(model.getInfo());
		setAttr("model",jsonObject);
		setAttr("id",getParaToInt("id"));
		setAttr("list", page);
		render("../views/commoninfo/svg_edit.html");
	}
	
	
	
}