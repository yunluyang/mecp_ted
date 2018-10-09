package com.mecp.common.info;

import java.util.List;

import com.demo.common.model.Customer;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class CustomerService {
	private Customer dao = new Customer().dao();
	public List<Customer> getAllCustomer(){
		return dao.find("select * from customer order by id asc");
	}
}
