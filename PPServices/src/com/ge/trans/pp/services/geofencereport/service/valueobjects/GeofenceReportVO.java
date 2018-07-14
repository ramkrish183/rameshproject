package com.ge.trans.pp.services.geofencereport.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class GeofenceReportVO extends BaseVO {

    private String customerId;
    private String roadInitial;
    private String roadNumber;
    private String geofenceName;
    private Date entryTime;
    private String totalTime;
    private String entryFuelLevel;
    private String totalFuel;
    private String fleet;

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

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getGeofenceName() {
        return geofenceName;
    }

    public void setGeofenceName(String geofenceName) {
        this.geofenceName = geofenceName;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getEntryFuelLevel() {
        return entryFuelLevel;
    }

    public void setEntryFuelLevel(String entryFuelLevel) {
        this.entryFuelLevel = entryFuelLevel;
    }

    public String getTotalFuel() {
        return totalFuel;
    }

    public void setTotalFuel(String totalFuel) {
        this.totalFuel = totalFuel;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

}
