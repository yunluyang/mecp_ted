package com.mecp.common.info;

import java.util.List;

import com.demo.common.model.TestStBasis;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TestStBasisService {
	private TestStBasis dao = new TestStBasis().dao();
	
	public TestStBasis queryById(int idValue){
		return dao.findById(idValue);
	}
	
	public List<TestStBasis> getAllTeam(){
		return dao.find("select * from test_st_basis order by id asc");
	}
	
	public Page<TestStBasis> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from test_st_basis order by id asc");
	}
}
