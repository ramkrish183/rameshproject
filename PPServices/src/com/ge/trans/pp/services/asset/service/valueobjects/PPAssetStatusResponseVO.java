package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.Date;

public class PPAssetStatusResponseVO{

    private String assetNumber;
    private String assetGroupName;
    private String customerId;
    private String customerName;
    
    private String model;
    private String latitude;
    private String fuelLevel;
    private String longitude;
    private String engine;
    private String velocity;
    private String notMoving;
    private String heading;
    private String engineOn;
    private String distance;
    private String engControl;
    private String location;
    private String locoOrientation;
    private String state;
    private String actvNotifications;
    private String milePost;
    private String region;
    private String subRegion;
    private Date dtLstFuelReadingRcvd;
    private Date dtMessageTime;
    private String notificationType;
    private String fleetNumber;
    private String roadHdrNumber;
    private String direction;
    private String snapLatitude;
    private String snapLongitude;
    private String histLatitude;
    private String histLongitude;
    private Date snapShotTime;
    private Date histTime;
    private String throttlePosition;
    private String atsMessageReason;
    
    public String getThrottlePosition() {
        return throttlePosition;
    }
    public void setThrottlePosition(String throttlePosition) {
        this.throttlePosition = throttlePosition;
    }
    public Date getSnapShotTime() {
        return snapShotTime;
    }
    public void setSnapShotTime(Date snapShotTime) {
        this.snapShotTime = snapShotTime;
    }
    public Date getHistTime() {
        return histTime;
    }
    public void setHistTime(Date histTime) {
        this.histTime = histTime;
    }
    public String getSnapLatitude() {
        return snapLatitude;
    }
    public void setSnapLatitude(String snapLatitude) {
        this.snapLatitude = snapLatitude;
    }
    public String getSnapLongitude() {
        return snapLongitude;
    }
    public void setSnapLongitude(String snapLongitude) {
        this.snapLongitude = snapLongitude;
    }
    public String getHistLatitude() {
        return histLatitude;
    }
    public void setHistLatitude(String histLatitude) {
        this.histLatitude = histLatitude;
    }
    public String getHistLongitude() {
        return histLongitude;
    }
    public void setHistLongitude(String histLongitude) {
        this.histLongitude = histLongitude;
    }
    public String getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
    public String getModel() {
        return model;
    }
    public void setModel(final String model) {
        this.model = model;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(final String latitude) {
        this.latitude = latitude;
    }
    public String getFuelLevel() {
        return fuelLevel;
    }
    public void setFuelLevel(final String fuelLevel) {
        this.fuelLevel = fuelLevel;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(final String longitude) {
        this.longitude = longitude;
    }
    public String getEngine() {
        return engine;
    }
    public void setEngine(final String engine) {
        this.engine = engine;
    }
    public String getVelocity() {
        return velocity;
    }
    public void setVelocity(final String velocity) {
        this.velocity = velocity;
    }
    public String getNotMoving() {
        return notMoving;
    }
    public void setNotMoving(final String notMoving) {
        this.notMoving = notMoving;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(final String heading) {
        this.heading = heading;
    }
    public String getEngineOn() {
        return engineOn;
    }
    public void setEngineOn(final String engineOn) {
        this.engineOn = engineOn;
    }
    public String getDistance() {
        return distance;
    }
    public void setDistance(final String distance) {
        this.distance = distance;
    }
    public String getEngControl() {
        return engControl;
    }
    public void setEngControl(final String engControl) {
        this.engControl = engControl;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(final String location) {
        this.location = location;
    }
    public String getLocoOrientation() {
        return locoOrientation;
    }
    public void setLocoOrientation(final String locoOrientation) {
        this.locoOrientation = locoOrientation;
    }
    public String getState() {
        return state;
    }
    public void setState(final String state) {
        this.state = state;
    }
    public String getActvNotifications() {
        return actvNotifications;
    }
    public void setActvNotifications(final String actvNotifications) {
        this.actvNotifications = actvNotifications;
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
    public Date getDtLstFuelReadingRcvd() {
        return dtLstFuelReadingRcvd;
    }
    public void setDtLstFuelReadingRcvd(final Date dtLstFuelReadingRcvd) {
        this.dtLstFuelReadingRcvd = dtLstFuelReadingRcvd;
    }
    public Date getDtMessageTime() {
        return dtMessageTime;
    }
    public void setDtMessageTime(final Date dtMessageTime) {
        this.dtMessageTime = dtMessageTime;
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
    
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getRoadHdrNumber() {
        return roadHdrNumber;
    }
    public void setRoadHdrNumber(String roadHdrNumber) {
        this.roadHdrNumber = roadHdrNumber;
    }
    public String getFleetNumber() {
        return fleetNumber;
    }
    public void setFleetNumber(String fleet) {
        this.fleetNumber = fleet;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
    public String getAtsMessageReason() {
        return atsMessageReason;
    }
    public void setAtsMessageReason(String atsMessageReason) {
        this.atsMessageReason = atsMessageReason;
    }
    
}
