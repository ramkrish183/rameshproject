package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

public class RxHistoryVO {
    private String rxCaseId;
    private String rxFeedback;
    private Date rxCloseDate;
    private String serviceRequestId;
    private String rxSuccess;
    private String missCode;
    private String feedToTools;
    private String goodFeedback;
    private String custFdbkObjId;

    public String getRxCaseId() {
        return rxCaseId;
    }

    public void setRxCaseId(String rxCaseId) {
        this.rxCaseId = rxCaseId;
    }

    public String getRxFeedback() {
        return rxFeedback;
    }

    public void setRxFeedback(String rxFeedback) {
        this.rxFeedback = rxFeedback;
    }

    public Date getRxCloseDate() {
        return rxCloseDate;
    }

    public void setRxCloseDate(Date rxCloseDate) {
        this.rxCloseDate = rxCloseDate;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    public String getRxSuccess() {
        return rxSuccess;
    }

    public void setRxSuccess(String rxSuccess) {
        this.rxSuccess = rxSuccess;
    }

    public String getMissCode() {
        return missCode;
    }

    public void setMissCode(String missCode) {
        this.missCode = missCode;
    }

    public String getFeedToTools() {
        return feedToTools;
    }

    public void setFeedToTools(String feedToTools) {
        this.feedToTools = feedToTools;
    }

    public String getGoodFeedback() {
        return goodFeedback;
    }

    public void setGoodFeedback(String goodFeedback) {
        this.goodFeedback = goodFeedback;
    }

    public String getCustFdbkObjId() {
        return custFdbkObjId;
    }

    public void setCustFdbkObjId(String custFdbkObjId) {
        this.custFdbkObjId = custFdbkObjId;
    }

}
