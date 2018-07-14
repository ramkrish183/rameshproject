package com.ge.trans.rmd.services.locovision.valueobjects;
public class LDVRAssetCriteriaType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumberFrom;
	private String roadNumberTo;
	private String device;
	private String roadNumber;
	//private String requestType;
	//private String templateIds;
	//private String userName;

	/*public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}*/

/*	public String getTemplateIds() {
		return templateIds;
	}

	public void setTemplateIds(String templateIds) {
		this.templateIds = templateIds;
	}
*/

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
/*
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
*/
	public String getAssetOwnerId() {
		return assetOwnerId;
	}

	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

	public String getRoadInitial() {
		return roadInitial;
	}

	public void setRoadInitial(String value) {
		this.roadInitial = value;
	}

	public String getRoadNumberFrom() {
		return roadNumberFrom;
	}

	public void setRoadNumberFrom(String value) {
		this.roadNumberFrom = value;
	}

	public String getRoadNumberTo() {
		return roadNumberTo;
	}

	public void setRoadNumberTo(String value) {
		this.roadNumberTo = value;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String value) {
		this.device = value;
	}
	@Override
	public String toString() {
		String val = "assetOwnerId: "+assetOwnerId						
						+"\nroadInitial: "+roadInitial
						+"\nroadNumber: "+roadNumber
						+"\nroadNumberFrom: "+roadNumberFrom						
						+"\nroadNumberTo: "+roadNumberTo
						+"\ndevice: "+device;									
		return val;	
	}
}
