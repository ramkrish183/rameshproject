package com.ge.trans.rmd.services.locovision.valueobjects;

public class LDVRSaveGeofenceRequest {

	private String geozoneObjId;
	private String geozoneId;
	private String geozoneName;
	private String assetOwnerId;
	private String lattitude1;
	private String longitude1;
	private String lattitude3;
	private String longitude3;
	private String userName;
	private String activeFlag;
	
	public String getGeozoneObjId() {
		return geozoneObjId;
	}
	public void setGeozoneObjId(String geozoneObjId) {
		this.geozoneObjId = geozoneObjId;
	}
	public String getGeozoneId() {
		return geozoneId;
	}
	public void setGeozoneId(String geozoneId) {
		this.geozoneId = geozoneId;
	}
	public String getGeozoneName() {
		return geozoneName;
	}
	public void setGeozoneName(String geozoneName) {
		this.geozoneName = geozoneName;
	}
	public String getAssetOwnerId() {
		return assetOwnerId;
	}
	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

	public String getLattitude1() {
		return lattitude1;
	}
	public void setLattitude1(String lattitude1) {
		this.lattitude1 = lattitude1;
	}
	public String getLongitude1() {
		return longitude1;
	}
	public void setLongitude1(String longitude1) {
		this.longitude1 = longitude1;
	}
	public String getLattitude3() {
		return lattitude3;
	}
	public void setLattitude3(String lattitude3) {
		this.lattitude3 = lattitude3;
	}
	public String getLongitude3() {
		return longitude3;
	}
	public void setLongitude3(String longitude3) {
		this.longitude3 = longitude3;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}
