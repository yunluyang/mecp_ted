package com.mecp.common.info;


import java.util.List;

import com.demo.common.model.BasicInfomation;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class BasicInfomationService {
	private BasicInfomation dao = new BasicInfomation().dao();
	
	public BasicInfomation getModelById(int id){
		return dao.findById(id);
	}
	
	public List<BasicInfomation> getModelByType(String type){
		return dao.find("select id,type,vender,model from basic_infomation where type = ?  order by id asc",type);
	}
	
	public Page<BasicInfomation> paginate(int pageNum,int page,String type) {
		return dao.paginate(pageNum, page, "select id,type,vender,model", "from basic_infomation where type = ?  order by id asc",type);
	}
}
