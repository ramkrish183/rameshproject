package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsTruckInfoResponseType", propOrder = { "mine","truck","fleet","header","manufacturer","customerModel","geModel","controllerConfig","software","loads","openRx","activeTime","urgency" })

@XmlRootElement
public class ReportsTruckInfoResponseType {
	private String mine;
	private String truck;
	private String fleet;
	private String header;
	private String manufacturer;
	private String customerModel;
	private String geModel;
	private String controllerConfig;
	private Map<String,String> software;
	private String loads;
	private String openRx;
	private String activeTime;
	private String urgency;
	
	public String getMine() {
		return mine;
	}
	public void setMine(String mine) {
		this.mine = mine;
	}
	public String getTruck() {
		return truck;
	}
	public void setTruck(String truck) {
		this.truck = truck;
	}
	public String getFleet() {
		return fleet;
	}
	public void setFleet(String fleet) {
		this.fleet = fleet;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getCustomerModel() {
		return customerModel;
	}
	public void setCustomerModel(String customerModel) {
		this.customerModel = customerModel;
	}
	public String getGeModel() {
		return geModel;
	}
	public void setGeModel(String geModel) {
		this.geModel = geModel;
	}
	public String getControllerConfig() {
		return controllerConfig;
	}
	public void setControllerConfig(String controllerConfig) {
		this.controllerConfig = controllerConfig;
	}
	public String getLoads() {
		return loads;
	}
	public void setLoads(String loads) {
		this.loads = loads;
	}
	public String getOpenRx() {
		return openRx;
	}
	public void setOpenRx(String openRx) {
		this.openRx = openRx;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	
	public Map<String, String> getSoftware() {
		return software;
	}
	public void setSoftware(Map<String, String> software) {
		this.software = software;
	}
	
}
