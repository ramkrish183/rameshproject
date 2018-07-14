package com.ge.trans.rmd.services.locovision.valueobjects;

public class CheckActiveLDVRPreservationRequestType {

	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	
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
	
	@Override
	public String toString() {
		return "[assetOwnerId: "+assetOwnerId+", roadInitial: "+roadInitial+", roadNumber: "+roadNumber+", device: "+device+"]";	
	}


}
