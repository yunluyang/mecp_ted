package com.mecp.transaction.process;

import java.util.List;

import com.demo.common.model.ServeDetail;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class ServeDetailService {
	private ServeDetail dao = new ServeDetail().dao();
	
	public ServeDetail getById(int id){
		return dao.findById(id);
	}
	
	public List<ServeDetail> getAll(){
		return dao.find("select * from serve_info order by id asc");
	}
	
	public List<ServeDetail> getInfoByType(String type){
		return dao.find("select * from serve_info where type = ? order by id asc",type);
	}
	
	public Page<ServeDetail> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from serve_info order by id asc");
	}
}
