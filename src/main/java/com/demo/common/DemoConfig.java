package com.demo.common;

import com.demo.common.model._MappingKit;
import com.demo.index.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Const;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.mcep.message.MessageController;
import com.mecp.common.info.CommonInfoController;
import com.mecp.customer.CustomerController;
import com.mecp.service.info.ServeInfoController;
import com.mecp.service.scheme.ServiceSchemeController;
import com.mecp.transaction.process.TransactionProcessController;
import com.mecp.upload.DownloadController;
import com.mecp.upload.UploadController;
import com.mecp.user.UserController;


/**
 * 
 * API引导式配置
 */
public class DemoConfig extends JFinalConfig {
	
	/**
	 * 启动入口
	 */
	public static void main(String[] args) {
		//JFinal.start("src/main/webapp", 80, "/", 5);
		UndertowServer.start(DemoConfig.class, 80, true);
	}
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用PropKit.get(...)获取值
		PropKit.use("a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		// 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
		me.setInjectDependency(true);
		me.setMaxPostSize(100*Const.DEFAULT_MAX_POST_SIZE);
		me.setError404View("/views/template/tips/404.html");
		me.setError500View("/views/template/tips/error.html");
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",IndexController.class);
		me.add("/customer", CustomerController.class);		
		me.add("/common", CommonInfoController.class);		
		me.add("/servicescheme",ServiceSchemeController.class);
		me.add("/serveinfo",ServeInfoController.class);	
		me.add("/tprocess",TransactionProcessController.class);	
		me.add("/upload",UploadController.class);
		me.add("/download",DownloadController.class);
		me.add("/user",UserController.class);
		me.add("/message",MessageController.class);
	}
	
	public void configEngine(Engine me) {
		me.addSharedFunction("/common/_layout.html");
		me.addSharedFunction("/common/_paginate.html");
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
		
		
		/*QuartzPlugin quartzPlugin = new QuartzPlugin();
		quartzPlugin.setJobs("system-quartz.properties");
		me.add(quartzPlugin);*/
	}
	
	public static DruidPlugin createDruidPlugin() {
		return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}

	@Override
	public void afterJFinalStart() {
		// TODO Auto-generated method stub
		super.afterJFinalStart();
	}

	@Override
	public void beforeJFinalStop() {
		// TODO Auto-generated method stub
		super.beforeJFinalStop();
	}
	
	
	
}
