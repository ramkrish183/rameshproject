package com.ge.trans.pp.services.geofencereport.service.valueobjects;

public class GeofenceMasterProximityVO {
    private String customerId;
    private String proxDesc;
    private String proxEvent;
    private float upLeftLatitude;
    private float upLeftLongitude;
    private float lowRightLatitude;
    private float lowRightLongitude;
    private String notes;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProxDesc() {
        return proxDesc;
    }

    public void setProxDesc(String proxDesc) {
        this.proxDesc = proxDesc;
    }

    public String getProxEvent() {
        return proxEvent;
    }

    public void setProxEvent(String proxEvent) {
        this.proxEvent = proxEvent;
    }

    public float getUpLeftLatitude() {
        return upLeftLatitude;
    }

    public void setUpLeftLatitude(float upLeftLatitude) {
        this.upLeftLatitude = upLeftLatitude;
    }

    public float getUpLeftLongitude() {
        return upLeftLongitude;
    }

    public void setUpLeftLongitude(float upLeftLongitude) {
        this.upLeftLongitude = upLeftLongitude;
    }

    public float getLowRightLatitude() {
        return lowRightLatitude;
    }

    public void setLowRightLatitude(float lowRightLatitude) {
        this.lowRightLatitude = lowRightLatitude;
    }

    public float getLowRightLongitude() {
        return lowRightLongitude;
    }

    public void setLowRightLongitude(float lowRightLongitude) {
        this.lowRightLongitude = lowRightLongitude;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
