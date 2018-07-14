package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsComStatusResponseType", propOrder = { "eoaEquip","messageReceived","healthReports" })

@XmlRootElement
public class ReportsComStatusResponseType {
	private String eoaEquip;
	private String messageReceived;
	private String healthReports;
	public String getEoaEquip() {
		return eoaEquip;
	}
	public void setEoaEquip(String eoaEquip) {
		this.eoaEquip = eoaEquip;
	}
	public String getMessageReceived() {
		return messageReceived;
	}
	public void setMessageReceived(String messageReceived) {
		this.messageReceived = messageReceived;
	}
	public String getHealthReports() {
		return healthReports;
	}
	public void setHealthReports(String healthReports) {
		this.healthReports = healthReports;
	}

}
