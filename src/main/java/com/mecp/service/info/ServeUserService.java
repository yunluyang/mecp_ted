package com.mecp.service.info;

import java.util.List;

import com.demo.common.model.ServeUser;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class ServeUserService {
	private ServeUser dao = new ServeUser().dao();
	
	public List<ServeUser> getAll(){
		return dao.find("select * from serve_user order by id asc");
	}
	
	public List<ServeUser> getAllByUserName(String username){
		return dao.find("select * from serve_user where username = ? order by id asc",username);
	}
	
	public Page<ServeUser> paginate(int pageNum,int page,String username) {
		return dao.paginate(pageNum, page, "select *", "from serve_user where username = ? order by id asc",username);
	}
}
