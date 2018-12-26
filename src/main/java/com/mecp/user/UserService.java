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
	
	//查找所有管理员和超级管理员
	public List<User> findAllAdmin(){
		return dao.find("select * from user where rid = 1 or  rid = 2");
	}
	
	public User findUserByName(String username){
		return dao.findFirst("select * from user where username = ?",username);
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
		String sql = "select * from user where userName = ? order by id desc";
		return dao.findFirst(sql, userName);
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}
}
