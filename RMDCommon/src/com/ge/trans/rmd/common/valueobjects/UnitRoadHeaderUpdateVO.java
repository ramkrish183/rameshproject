package com.ge.trans.rmd.common.valueobjects;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UnitRoadHeaderUpdateVO implements Serializable {
	private String customer;
	private String currentRoadHeader;
	private String newRoadHeader;
	List<String> vehicleNumbers;
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCurrentRoadHeader() {
		return currentRoadHeader;
	}
	public void setCurrentRoadHeader(String currendRoadHeader) {
		this.currentRoadHeader = currendRoadHeader;
	}
	public String getNewRoadHeader() {
		return newRoadHeader;
	}
	public void setNewRoadHeader(String newRoadHeader) {
		this.newRoadHeader = newRoadHeader;
	}
	public List<String> getVehicleNumbers() {
		return vehicleNumbers;
	}
	public void setVehicleNumbers(List<String> vehicleNumbers) {
		this.vehicleNumbers = vehicleNumbers;
	}
}
