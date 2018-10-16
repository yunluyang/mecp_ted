package com.mecp.user;

import java.util.List;

import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class UserService {
	
	private static final User dao = new User().dao();
	

	public List<User> findAllUser(){
		return dao.find("select * from user");
	}
	
	public int countUser(){
		return dao.find("select * from user").size();
	}
	
	public Page<User> paginate(int pageNumber, int pageSize) {
		return dao.paginate(pageNumber, pageSize, "select *", "from user order by id asc");
	}
	
	
	public User findByOpenId(String openid) {
		return dao.findFirst("select * from user where openid = ?",openid);
	}
	
	public User findByPhone(java.math.BigDecimal phone) {
		return dao.findFirst("select * from user where cellPhone = ?",phone);
	}
	
	
	public User findById(int id) {
		return dao.findById(id);
	}
	
	
	public User findByUserName(String userName) {
		String sql = "select * from user where userName = ?";
		return dao.findFirst(sql, userName);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
