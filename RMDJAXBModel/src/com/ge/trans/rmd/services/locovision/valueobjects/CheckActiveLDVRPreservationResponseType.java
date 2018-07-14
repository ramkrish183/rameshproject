package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkActiveLDVRPreserResType", propOrder = { "assetOwnerId",
		"roadInitial","roadNumber","device","activeLDVRLimitExceeded", "activeLDVRMaxLimit" })

@XmlRootElement
public class CheckActiveLDVRPreservationResponseType {

	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String activeLDVRLimitExceeded;
	private String activeLDVRMaxLimit;
	
	public String getAssetOwnerId() {
		return assetOwnerId;
	}
	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}
	public String getRoadInitial() {
		return roadInitial;
	}
	public void setRoadInitial(String roadInitial) {
		this.roadInitial = roadInitial;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
	
	public String getActiveLDVRLimitExceeded() {
		return activeLDVRLimitExceeded;
	}
	public void setActiveLDVRLimitExceeded(String activeLDVRLimitExceeded) {
		this.activeLDVRLimitExceeded = activeLDVRLimitExceeded;
	}
	
	public String getActiveLDVRMaxLimit() {
		return activeLDVRMaxLimit;
	}
	public void setActiveLDVRMaxLimit(String activeLDVRMaxLimit) {
		this.activeLDVRMaxLimit = activeLDVRMaxLimit;
	}

}
