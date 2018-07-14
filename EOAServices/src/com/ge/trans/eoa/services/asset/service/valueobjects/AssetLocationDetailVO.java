package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class AssetLocationDetailVO extends BaseVO {

	   private String rnh;
	   private String roadNumber;
	   private String latitude;
	   private String longitude;
	   private String latlongTime;
	   private String gpsSystem;
	   private String distanceToShop;
	   private String directionFromShop;
	   
	public String getRnh() {
		return rnh;
	}
	public void setRnh(String rnh) {
		this.rnh = rnh;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatlongTime() {
		return latlongTime;
	}
	public void setLatlongTime(String latlongTime) {
		this.latlongTime = latlongTime;
	}
	public String getGpsSystem() {
		return gpsSystem;
	}
	public void setGpsSystem(String gpsSystem) {
		this.gpsSystem = gpsSystem;
	}
	public String getDistanceToShop() {
		return distanceToShop;
	}
	public void setDistanceToShop(String distanceToShop) {
		this.distanceToShop = distanceToShop;
	}
	public String getDirectionFromShop() {
		return directionFromShop;
	}
	public void setDirectionFromShop(String directionFromShop) {
		this.directionFromShop = directionFromShop;
	}

   
}
