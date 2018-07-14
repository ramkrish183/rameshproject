package com.ge.trans.eoa.services.cases.service.valueobjects;

public class CustomerFdbkVO {
    private String custFdbkObjId;
    private String serviceReqId;
    private String isRxPresent;
    private String rxCloseDate;
    private String caseSuccess;
    private String rxFdbk;
    private String rxCaseId;

    public String getCustFdbkObjId() {
        return custFdbkObjId;
    }

    public void setCustFdbkObjId(String custFdbkObjId) {
        this.custFdbkObjId = custFdbkObjId;
    }

    public String getRxCloseDate() {
        return rxCloseDate;
    }

    public void setRxCloseDate(String rxCloseDate) {
        this.rxCloseDate = rxCloseDate;
    }

    public String getCaseSuccess() {
        return caseSuccess;
    }

    public void setCaseSuccess(String caseSuccess) {
        this.caseSuccess = caseSuccess;
    }

    public String getRxFdbk() {
        return rxFdbk;
    }

    public void setRxFdbk(String rxFdbk) {
        this.rxFdbk = rxFdbk;
    }

    public String getServiceReqId() {
        return serviceReqId;
    }

    public void setServiceReqId(String serviceReqId) {
        this.serviceReqId = serviceReqId;
    }

    public String getIsRxPresent() {
        return isRxPresent;
    }

    public void setIsRxPresent(String isRxPresent) {
        this.isRxPresent = isRxPresent;
    }

    public String getRxCaseId() {
        return rxCaseId;
    }

    public void setRxCaseId(String rxCaseId) {
        this.rxCaseId = rxCaseId;
    }

}
