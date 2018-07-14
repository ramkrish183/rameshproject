package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unitHeaderUpdateRequestType", propOrder = { "customerId","currentVehicleHeader","newVehicleHeader", "vehicleNumber"})

@XmlRootElement
public class UnitHeaderUpdateRequestType {
	@XmlElement(required = true)
	protected String customerId;
	@XmlElement(required = true)
	protected String currentVehicleHeader;
	@XmlElement(required = true)
	protected String newVehicleHeader;
	@XmlElement(required = true)
	protected List<String> vehicleNumber;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCurrentVehicleHeader() {
		return currentVehicleHeader;
	}
	public void setCurrentVehicleHeader(String currentVehicleHeader) {
		this.currentVehicleHeader = currentVehicleHeader;
	}
	public String getNewVehicleHeader() {
		return newVehicleHeader;
	}
	public void setNewVehicleHeader(String newVehicleHeader) {
		this.newVehicleHeader = newVehicleHeader;
	}
	public List<String> getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(List<String> vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	
	
}
