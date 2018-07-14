package com.ge.trans.pp.services.idlereport.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class IdleReportDetailsResponseVO extends BaseVO {

    private String region;
    private String subRegion;
    private String customerId;
    private String roadInitial;
    private String roadNumber;
    private String ntMovingDuration;
    private String engOnDuration;
    private String currEngState;
    private String dwellDuration;
    private String distance;
    private String location;
    private String state;
    private String idleSince;
    private String lastMsgTime;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
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

    public String getNtMovingDuration() {
        return ntMovingDuration;
    }

    public void setNtMovingDuration(String ntMovingDuration) {
        this.ntMovingDuration = ntMovingDuration;
    }

    public String getEngOnDuration() {
        return engOnDuration;
    }

    public void setEngOnDuration(String engOnDuration) {
        this.engOnDuration = engOnDuration;
    }

    public String getCurrEngState() {
        return currEngState;
    }

    public void setCurrEngState(String currEngState) {
        this.currEngState = currEngState;
    }

    public String getDwellDuration() {
        return dwellDuration;
    }

    public void setDwellDuration(String dwellDuration) {
        this.dwellDuration = dwellDuration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getIdleSince() {
        return idleSince;
    }

    public void setIdleSince(String idleSince) {
        this.idleSince = idleSince;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
