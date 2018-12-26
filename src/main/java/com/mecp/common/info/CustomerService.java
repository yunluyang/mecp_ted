package com.mecp.common.info;

import java.util.List;

import com.demo.common.model.Customer;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class CustomerService {
	private Customer dao = new Customer().dao();
	
	public Customer findById(int id){
		return dao.findById(id);
	}
	
	public List<Customer> getAllCustomer(){
		return dao.find("select * from customer order by id asc");
	}
	
	public Page<Customer> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from customer order by id desc");
	}
	
	public List<Customer> getCustomer(String company,String province){
		return dao.find("select * from customer where company like ?  and  province like ? order by id asc",company,province);
	}
}
