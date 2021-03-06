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

	public void setUsername(java.lang.String username) {
		set("username", username);
	}
	
	public java.lang.String getUsername() {
		return getStr("username");
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

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setPowerPlantName(java.lang.String powerPlantName) {
		set("power_plant_name", powerPlantName);
	}
	
	public java.lang.String getPowerPlantName() {
		return getStr("power_plant_name");
	}

	public void setProgress(java.lang.String progress) {
		set("progress", progress);
	}
	
	public java.lang.String getProgress() {
		return getStr("progress");
	}

	public void setConciseReport(java.lang.Integer conciseReport) {
		set("concise_report", conciseReport);
	}
	
	public java.lang.Integer getConciseReport() {
		return getInt("concise_report");
	}

	public void setIsUpload(java.lang.Long isUpload) {
		set("isUpload", isUpload);
	}
	
	public java.lang.Long getIsUpload() {
		return getLong("isUpload");
	}

	public void setReportId(java.lang.Integer reportId) {
		set("report_id", reportId);
	}
	
	public java.lang.Integer getReportId() {
		return getInt("report_id");
	}

	public void setContractId(java.lang.Integer contractId) {
		set("contract_id", contractId);
	}
	
	public java.lang.Integer getContractId() {
		return getInt("contract_id");
	}

	public void setSettlementMaterialsId(java.lang.Integer settlementMaterialsId) {
		set("settlement_materials_id", settlementMaterialsId);
	}
	
	public java.lang.Integer getSettlementMaterialsId() {
		return getInt("settlement_materials_id");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
	}
	
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

	public void setEditer(java.lang.String editer) {
		set("editer", editer);
	}
	
	public java.lang.String getEditer() {
		return getStr("editer");
	}

}
