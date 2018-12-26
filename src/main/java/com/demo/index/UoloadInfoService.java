package com.demo.index;

import java.util.List;

import com.demo.common.model.Uploadinfo;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class UoloadInfoService {
	
	private static final Uploadinfo dao = new Uploadinfo().dao();
	

	public List<Uploadinfo> findAllUser(){
		return dao.find("select * from uploadinfo");
	}
	
}
