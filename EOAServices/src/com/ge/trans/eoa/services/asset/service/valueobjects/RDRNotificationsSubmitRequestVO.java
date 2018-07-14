package com.ge.trans.eoa.services.asset.service.valueobjects;

import javax.xml.bind.annotation.XmlElement;

public class RDRNotificationsSubmitRequestVO {
	
	private String userID;
	private String assetNumber;
	private String assetGrpName;
	private String customerID;
	private String fromDate;
	private String toDate;
	private String status;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getAssetGrpName() {
		return assetGrpName;
	}
	public void setAssetGrpName(String assetGrpName) {
		this.assetGrpName = assetGrpName;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

