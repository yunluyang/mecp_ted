package com.mecp.constant;

public class Constant {
		public static String PROGESS0 = "0%";  //合同未签署
		public static String PROGESS10 = "10%";  // 服务已配置
		public static String PROGESS20 = "20%";  // 责任人分配
		public static String PROGESS40 = "40%";  // 检测报告上传
		public static String PROGESS60 = "60%";   // 简洁报告录入
		public static String PROGESS80 = "80%";  //报告录入
		public static String PROGESS100 = "100%";  //结算材料录入
		
		//检测大项目状态
		public static String SERVICE_USER_STATUS_ZJ = "在检";
		public static String SERVICE_USER_STATUS_YJ= "已检";
		public static String SERVICE_USER_STATUS_DJ= "待检";
		public static String SERVICE_USER_STATUS_WJ= "完结";
		
		//单个检测状态
		public static String DG_SERVICE_USER_STATUS_WPZ = "未配置";
		public static String DG_SERVICE_USER_STATUS_DJC = "待检测";
		public static String DG_SERVICE_USER_STATUS_YJC = "已检测";
		
		
		public static String CONCISE_REPORT = "简洁报告";
		
		//上传类型
		public static int UPLOAD_TYPE_REPORT=1; //完整报告
		public static int UPLOAD_TYPE_CONTRACT=2;//合同
		public static int UPLOAD_TYPE_SM=3;//结算材料
		public static int UPLOAD_TYPE_SERVER_DETAIL=4;//检测小项数据
		public static int UPLOAD_TYPE_JCSB_REPORT=5;//上传检测设备报告
		public static int UPLOAD_TYPE_JCBZ_YJ=6;//上传检测标准及依据
		public static int UPLOAD_TYPE_TEAM_AVATAR=7;//团队头像
		
		public static int USER_NORMAL = 0;
		public static int USER_ADMIN = 1;
		public static int USER_ADMIN_SUPER = 2;
}
