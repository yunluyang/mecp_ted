package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseServeUser<M extends BaseServeUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setUsername(java.lang.Integer username) {
		set("username", username);
	}
	
	public java.lang.Integer getUsername() {
		return getInt("username");
	}

	public void setInfo(java.lang.String info) {
		set("info", info);
	}
	
	public java.lang.String getInfo() {
		return getStr("info");
	}

	public void setStates(java.lang.String states) {
		set("states", states);
	}
	
	public java.lang.String getStates() {
		return getStr("states");
	}

	public void setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
	}
	
	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

	public void setEndTime(java.util.Date endTime) {
		set("end_time", endTime);
	}
	
	public java.util.Date getEndTime() {
		return get("end_time");
	}

	public void setStartTime(java.util.Date startTime) {
		set("start_time", startTime);
	}
	
	public java.util.Date getStartTime() {
		return get("start_time");
	}

}
