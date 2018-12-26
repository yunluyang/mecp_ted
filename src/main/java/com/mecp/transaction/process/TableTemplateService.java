package com.mecp.transaction.process;

import java.util.List;

import com.demo.common.model.TableTemplate;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
@Before(Tx.class)
public class TableTemplateService {
	private TableTemplate dao = new TableTemplate().dao();
	
	public TableTemplate getById(int id){
		return dao.findById(id);
	}
	
	public List<TableTemplate> getAll(){
		return dao.find("select * from table_template order by id asc");
	}
	
	public List<TableTemplate> getInfoByType(String type){
		return dao.find("select * from table_template where type = ? order by id asc",type);
	}
	
	public Page<TableTemplate> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from table_template order by id asc");
	}
}
