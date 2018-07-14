package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPAssetStatusHistoryVO extends BaseVO{
    
    static final long serialVersionUID = 13376664L;
    private String assetNumber;
    private String assetGroupName;
    private String customerId;
    private String vehObjId;
    private String fromDate;
    private String toDate;
    private String noOfDays;
    private String timezone;
    
    private Date lastMsgDate;
    private String latitude;
    private String longitude;
    private String velocity;
    private String heading;
    private String direction;
    private String distance;
    private String location;
    private String state;
    private String milePost;
    private String region;
    private String subRegion;
    private String fuelLevel;
    private String fuelAdded;
    private String engine;
    private String notMoving;
    private String engineOn;
    private String engineCtrl;
    private String locoOrientation;
    private String notificationType;
    private String throttlePosition;
    private String atsMessageReason;
    private String flagMPData;
    private String conversionPreference;
    private String pageNo;
	private String recordsPerPage;
	private String startRow;
    
    
    public String getAtsMessageReason() {
        return atsMessageReason;
    }
    public void setAtsMessageReason(String atsMessageReason) {
        this.atsMessageReason = atsMessageReason;
    }
    public String getThrottlePosition() {
        return throttlePosition;
    }
    public void setThrottlePosition(String throttlePosition) {
        this.throttlePosition = throttlePosition;
    }
    public String getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    
    public String getAssetNumber() {
        return assetNumber;
    }
    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }
    public String getAssetGroupName() {
        return assetGroupName;
    }
    public void setAssetGroupName(final String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }
    public String getVehObjId() {
        return vehObjId;
    }
    public void setVehObjId(final String vehObjId) {
        this.vehObjId = vehObjId;
    }
    
    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }
    public String getToDate() {
        return toDate;
    }
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }
    
    public String getNoOfDays() {
        return noOfDays;
    }
    public void setNoOfDays(final String noOfDays) {
        this.noOfDays = noOfDays;
    }
    public String getTimezone() {
        return timezone;
    }
    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }
    

    public Date getLastMsgDate() {
        return lastMsgDate;
    }
    public void setLastMsgDate(final Date lastMsgDate) {
        this.lastMsgDate = lastMsgDate;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
    public String getVelocity() {
        return velocity;
    }
    public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getRecordsPerPage() {
		return recordsPerPage;
	}
	public void setRecordsPerPage(String recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	public String getStartRow() {
		return startRow;
	}
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}
	public void setVelocity(final String velocity) {
        this.velocity = velocity;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(final String heading) {
        this.heading = heading;
    }
    
    public String getDirection() {
        return direction;
    }
    public void setDirection(final String direction) {
        this.direction = direction;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(final String distance) {
        this.distance = distance;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(final String location) {
        this.location = location;
    }
    public String getState() {
        return state;
    }
    public void setState(final String state) {
        this.state = state;
    }
    public String getMilePost() {
        return milePost;
    }
    public void setMilePost(final String milePost) {
        this.milePost = milePost;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(final String region) {
        this.region = region;
    }
    public String getSubRegion() {
        return subRegion;
    }
    public void setSubRegion(final String subRegion) {
        this.subRegion = subRegion;
    }
    public String getFuelLevel() {
        return fuelLevel;
    }
    public void setFuelLevel(final String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    public String getFuelAdded() {
        return fuelAdded;
    }
    public void setFuelAdded(final String fuelAdded) {
        this.fuelAdded = fuelAdded;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(final String engine) {
        this.engine = engine;
    }
    public String getNotMoving() {
        return notMoving;
    }
    public void setNotMoving(final String notMoving) {
        this.notMoving = notMoving;
    }
    public String getEngineOn() {
        return engineOn;
    }
    public void setEngineOn(final String engineOn) {
        this.engineOn = engineOn;
    }
    public String getEngineCtrl() {
        return engineCtrl;
    }
    public void setEngineCtrl(final String engineCtrl) {
        this.engineCtrl = engineCtrl;
    }
    public String getLocoOrientation() {
        return locoOrientation;
    }
    public void setLocoOrientation(final String locoOrientation) {
        this.locoOrientation = locoOrientation;
    }
	public String getFlagMPData() {
		return flagMPData;
	}
	public void setFlagMPData(String flagMPData) {
		this.flagMPData = flagMPData;
	}
	public String getConversionPreference() {
		return conversionPreference;
	}
	public void setConversionPreference(String conversionPreference) {
		this.conversionPreference = conversionPreference;
	}
    
    
    
}
