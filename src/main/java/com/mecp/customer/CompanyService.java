package com.mecp.customer;

import java.util.List;

import com.demo.common.model.Company;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

@Before(Tx.class)
public class CompanyService {

	private Company dao = new Company().dao();
	
	public Company getById(int id){
		return dao.findById(id);
	}
	
	public Company getByCompanyName(String companyName){
		return dao.findFirst("select * from company where name = ?",companyName);
	}
	
	public List<Company> getAll(){
		return dao.find("select * from company order by id asc");
	}
	
	public List<Company>  getProvice(String companyName){
		return dao.find("select province from customer where company = ? group by province having count(province) >= 1",companyName);
	}
	
}
