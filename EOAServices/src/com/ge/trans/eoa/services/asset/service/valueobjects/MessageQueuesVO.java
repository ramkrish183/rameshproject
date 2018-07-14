package com.ge.trans.eoa.services.asset.service.valueobjects;


public class MessageQueuesVO {

	 int vehicleObjId;
	 int messageId;
	 String services;
	 String vehicleHdrNo;
	 String roadNumber;
	 String messagePriority;
	 String roadNumberHeader;
	 String sql_type;

	public int getVehicleObjId() {
		return vehicleObjId;
	}
	public void setVehicleObjId(int vehicleObjId) {
		this.vehicleObjId = vehicleObjId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getVehicleHdrNo() {
		return vehicleHdrNo;
	}
	public void setVehicleHdrNo(String vehicleHdrNo) {
		this.vehicleHdrNo = vehicleHdrNo;
	}
	
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getMessagePriority() {
		return messagePriority;
	}
	public void setMessagePriority(String messagePriority) {
		this.messagePriority = messagePriority;
	}
	public String getRoadNumberHeader() {
		return roadNumberHeader;
	}
	public void setRoadNumberHeader(String roadNumberHeader) {
		this.roadNumberHeader = roadNumberHeader;
	}
	
}
