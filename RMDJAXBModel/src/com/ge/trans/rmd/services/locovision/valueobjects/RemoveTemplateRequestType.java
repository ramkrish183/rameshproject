package com.ge.trans.rmd.services.locovision.valueobjects;


public class RemoveTemplateRequestType {
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String templateIds;
	private String userName;
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

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTemplateIds() {
		return templateIds;
	}
	public void setTemplateIds(String templateIds) {
		this.templateIds = templateIds;
	}
	
	
	
}
