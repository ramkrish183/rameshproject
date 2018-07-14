package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveGeofenceStatusType", propOrder = { "assetOwnerId", "roadInitial", "roadNumber", "device", "itemObjid", "statusCode", "statusMessage"})

@XmlRootElement
public class LDVRSaveGeofenceStatusType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String itemObjid; //(can be template Objid, geozone Objid, geoZones Name)
	private String statusCode; //(success/fault code)
	private String statusMessage;
	
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
	public String getItemObjid() {
		return itemObjid;
	}
	public void setItemObjid(String itemObjid) {
		this.itemObjid = itemObjid;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	
}
