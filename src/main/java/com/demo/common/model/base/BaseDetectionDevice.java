package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseDetectionDevice<M extends BaseDetectionDevice<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setDeviceName(java.lang.String deviceName) {
		set("device_name", deviceName);
	}
	
	public java.lang.String getDeviceName() {
		return getStr("device_name");
	}

	public void setVender(java.lang.String vender) {
		set("vender", vender);
	}
	
	public java.lang.String getVender() {
		return getStr("vender");
	}

	public void setModel(java.lang.String model) {
		set("model", model);
	}
	
	public java.lang.String getModel() {
		return getStr("model");
	}

	public void setParameter(java.lang.String parameter) {
		set("parameter", parameter);
	}
	
	public java.lang.String getParameter() {
		return getStr("parameter");
	}

	public void setDeviceNumber(java.lang.String deviceNumber) {
		set("device_number", deviceNumber);
	}
	
	public java.lang.String getDeviceNumber() {
		return getStr("device_number");
	}

	public void setCalibrationReportId(java.lang.Integer calibrationReportId) {
		set("calibration_report_id", calibrationReportId);
	}
	
	public java.lang.Integer getCalibrationReportId() {
		return getInt("calibration_report_id");
	}

	public void setFileName(java.lang.String fileName) {
		set("file_name", fileName);
	}
	
	public java.lang.String getFileName() {
		return getStr("file_name");
	}

}
