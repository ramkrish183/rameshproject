package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class VisualizationEventDataVO extends BaseVO {

    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    private Long eventOccurDate;
    private Long eventEndDate;
    private String eventType;
    private String eventSummary;
    private String eventDetails;
    private String customerId;
    private String assetGroupName;
    private String assetNumber;
    private String fromDate;
    private String toDate;
    private String controllerCfg;
    private String strEvntOccurDt;
    private String strEvntEndDt;
    private String noOfDays;

    public String getStrEvntOccurDt() {
        return strEvntOccurDt;
    }

    public void setStrEvntOccurDt(String strEvntOccurDt) {
        this.strEvntOccurDt = strEvntOccurDt;
    }

    public String getStrEvntEndDt() {
        return strEvntEndDt;
    }

    public void setStrEvntEndDt(String strEvntEndDt) {
        this.strEvntEndDt = strEvntEndDt;
    }

    public String getControllerCfg() {
        return controllerCfg;
    }

    public void setControllerCfg(String controllerCfg) {
        this.controllerCfg = controllerCfg;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
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

    public Long getEventOccurDate() {
        return eventOccurDate;
    }

    public void setEventOccurDate(Long eventOccurDate) {
        this.eventOccurDate = eventOccurDate;
    }

    public Long getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Long eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

}
