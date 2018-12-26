package com.mecp.upload;

import java.util.List;

import com.demo.common.model.ServeDetail;
import com.demo.common.model.UploadRecord;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class UploadRecordService {
	private UploadRecord dao = new UploadRecord().dao();
	
	public UploadRecord getById(int id){
		return dao.findById(id);
	}
	
	public List<UploadRecord> getAll(){
		return dao.find("select * from upload_record order by id asc");
	}
	
	public List<UploadRecord> getAll(int type,int id){
		return dao.find("select * from upload_record where type = ? and infoid = ? order by id desc",type,id);
	}
	
	public List<UploadRecord> getInfoByType(String type){
		return dao.find("select * from upload_record where type = ? order by id asc",type);
	}
	
	public Page<UploadRecord> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from upload_record order by id asc");
	}
}
