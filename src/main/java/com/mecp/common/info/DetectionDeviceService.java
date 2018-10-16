package com.mecp.common.info;

import java.util.List;

import com.demo.common.model.DetectionDevice;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class DetectionDeviceService {
	private DetectionDevice dao = new DetectionDevice().dao();
	
	public List<DetectionDevice> getAllTeam(){
		return dao.find("select * from detection_device order by id asc");
	}
	
	public Page<DetectionDevice> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from detection_device order by id asc");
	}
}
