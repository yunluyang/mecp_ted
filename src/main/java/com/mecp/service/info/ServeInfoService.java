package com.mecp.service.info;

import java.util.List;

import com.demo.common.model.ServeInfo;
import com.demo.common.model.ServeUser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class ServeInfoService {
	private ServeInfo dao = new ServeInfo().dao();
	
	public ServeInfo getById(int id){
		return dao.findById(id);
	}
	
	public List<ServeInfo> getAll(){
		return dao.find("select * from serve_info order by id asc");
	}
	
	public List<ServeInfo> getInfoByType(String type){
		return dao.find("select * from serve_info where type = ? order by id asc",type);
	}
	
	public Page<ServeInfo> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from serve_info order by id desc");
	}
}
