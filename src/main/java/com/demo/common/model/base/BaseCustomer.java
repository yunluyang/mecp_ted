package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCustomer<M extends BaseCustomer<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setCustomerName(java.lang.String customerName) {
		set("customer_name", customerName);
	}
	
	public java.lang.String getCustomerName() {
		return getStr("customer_name");
	}

	public void setCustomerAddress(java.lang.String customerAddress) {
		set("customer_address", customerAddress);
	}
	
	public java.lang.String getCustomerAddress() {
		return getStr("customer_address");
	}

	public void setCustomerContact(java.lang.String customerContact) {
		set("customer_contact", customerContact);
	}
	
	public java.lang.String getCustomerContact() {
		return getStr("customer_contact");
	}

	public void setCustomerContactName(java.lang.String customerContactName) {
		set("customer_contact_name", customerContactName);
	}
	
	public java.lang.String getCustomerContactName() {
		return getStr("customer_contact_name");
	}

	public void setLat(java.lang.Double lat) {
		set("lat", lat);
	}
	
	public java.lang.Double getLat() {
		return getDouble("lat");
	}

	public void setLong(java.lang.Double _long) {
		set("long", _long);
	}
	
	public java.lang.Double getLong() {
		return getDouble("long");
	}

}