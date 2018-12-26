package com.demo.common.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("basic_infomation", "id", BasicInfomation.class);
		arp.addMapping("company", "id", Company.class);
		arp.addMapping("customer", "id", Customer.class);
		arp.addMapping("detection_device", "id", DetectionDevice.class);
		arp.addMapping("message", "id", Message.class);
		arp.addMapping("serve_detail", "id", ServeDetail.class);
		arp.addMapping("serve_info", "id", ServeInfo.class);
		arp.addMapping("serve_user", "id", ServeUser.class);
		arp.addMapping("table_template", "id", TableTemplate.class);
		arp.addMapping("team", "id", Team.class);
		arp.addMapping("test_st_basis", "id", TestStBasis.class);
		arp.addMapping("upload_record", "id", UploadRecord.class);
		arp.addMapping("uploadinfo", "id", Uploadinfo.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("yuye", "id", Yuye.class);
	}
}

