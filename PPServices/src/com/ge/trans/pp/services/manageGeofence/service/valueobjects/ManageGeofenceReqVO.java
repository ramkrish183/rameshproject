package com.ge.trans.pp.services.manageGeofence.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class ManageGeofenceReqVO extends BaseVO {
    private String customerId;
    private String geofenceName;
    private String proximityEvent;
    private String upLeftLat;
    private String upLeftLong;
    private String lowerRightLat;
    private String lowerRightLong;
    private String geofenceNotes;
    private String actionType;
    private List<String> customerList;
    private String geofenceSeqId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGeofenceName() {
        return geofenceName;
    }

    public void setGeofenceName(String geofenceName) {
        this.geofenceName = geofenceName;
    }

    public String getProximityEvent() {
        return proximityEvent;
    }

    public void setProximityEvent(String proximityEvent) {
        this.proximityEvent = proximityEvent;
    }

    public String getUpLeftLat() {
        return upLeftLat;
    }

    public void setUpLeftLat(String upLeftLat) {
        this.upLeftLat = upLeftLat;
    }

    public String getUpLeftLong() {
        return upLeftLong;
    }

    public void setUpLeftLong(String upLeftLong) {
        this.upLeftLong = upLeftLong;
    }

    public String getLowerRightLat() {
        return lowerRightLat;
    }

    public void setLowerRightLat(String lowerRightLat) {
        this.lowerRightLat = lowerRightLat;
    }

    public String getLowerRightLong() {
        return lowerRightLong;
    }

    public void setLowerRightLong(String lowerRightLong) {
        this.lowerRightLong = lowerRightLong;
    }

    public String getGeofenceNotes() {
        return geofenceNotes;
    }

    public void setGeofenceNotes(String geofenceNotes) {
        this.geofenceNotes = geofenceNotes;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public List<String> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<String> customerList) {
        this.customerList = customerList;
    }

    public String getGeofenceSeqId() {
        return geofenceSeqId;
    }

    public void setGeofenceSeqId(String geofenceSeqId) {
        this.geofenceSeqId = geofenceSeqId;
    }

}
