package com.mecp.common.info;

import java.util.List;

import com.demo.common.model.Team;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;


@Before(Tx.class)
public class TeamService {
	private Team dao = new Team().dao();
	
	public List<Team> getAllTeam(){
		return dao.find("select * from team order by id asc");
	}
	
	public Page<Team> paginate(int pageNum,int page) {
		return dao.paginate(pageNum, page, "select *", "from team order by id asc");
	}
}
