package com.mcep.message;

import java.util.List;

import com.demo.common.model.Message;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

@Before(Tx.class)
public class MessageService {

	private Message dao = new Message().dao();
	
	public Message getById(int id){
		return dao.findById(id);
	}
	
	public  List<Message> getByName(String username){
		return dao.find("select * from message where receiver = ?",username);
	}
	
	public Page<Message> paginate(int pageNum,int page,String username) {
		return dao.paginate(pageNum, page, "select *", "from message where receiver = ? order by id desc",username);
	}
	
	public List<Message> getAll(){
		return dao.find("select * from message order by id asc");
	}
	
}
