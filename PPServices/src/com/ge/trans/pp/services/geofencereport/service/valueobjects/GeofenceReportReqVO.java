package com.ge.trans.pp.services.geofencereport.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class GeofenceReportReqVO extends BaseVO {

    private String customerId;
    private String roadInitial;
    private String fromRoadNo;
    private String toRoadNo;
    private String fromDate;
    private String toDate;
    private List<String> fleets;
    private List<String> geofences;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRoadInitial() {
        return roadInitial;
    }

    public void setRoadInitial(String roadInitial) {
        this.roadInitial = roadInitial;
    }

    public String getFromRoadNo() {
        return fromRoadNo;
    }

    public void setFromRoadNo(String fromRoadNo) {
        this.fromRoadNo = fromRoadNo;
    }

    public String getToRoadNo() {
        return toRoadNo;
    }

    public void setToRoadNo(String toRoadNo) {
        this.toRoadNo = toRoadNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<String> getFleets() {
        return fleets;
    }

    public void setFleets(List<String> fleets) {
        this.fleets = fleets;
    }

    public List<String> getGeofences() {
        return geofences;
    }

    public void setGeofences(List<String> geofences) {
        this.geofences = geofences;
    }

}
