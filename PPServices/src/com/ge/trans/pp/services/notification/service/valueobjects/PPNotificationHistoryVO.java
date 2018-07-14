package com.ge.trans.pp.services.notification.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPNotificationHistoryVO extends BaseVO {

    private String assetNumber;
    private String assetGrpName;
    private String dateTime;
    private String comments;
    private String notificationType;
    private String email;
    private String status;
    private String location;
    private String state;
    private String milepost;
    private String currRegion;
    private String subRegion;
    private String fuelLevel;
    private String fuelAdded;
    private String gpsLatDisplay;
    private String gpsLonDisplay;

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMilepost() {
        return milepost;
    }

    public void setMilepost(String milepost) {
        this.milepost = milepost;
    }

    public String getCurrRegion() {
        return currRegion;
    }

    public void setCurrRegion(String currRegion) {
        this.currRegion = currRegion;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getFuelAdded() {
        return fuelAdded;
    }

    public void setFuelAdded(String fuelAdded) {
        this.fuelAdded = fuelAdded;
    }

	public String getGpsLatDisplay() {
		return gpsLatDisplay;
	}

	public void setGpsLatDisplay(String gpsLatDisplay) {
		this.gpsLatDisplay = gpsLatDisplay;
	}

	public String getGpsLonDisplay() {
		return gpsLonDisplay;
	}

	public void setGpsLonDisplay(String gpsLonDisplay) {
		this.gpsLonDisplay = gpsLonDisplay;
	}

}
