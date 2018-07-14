package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customerFeedbackResponseType", propOrder = { "custFdbkObjId", "serviceReqId", "isRxPresent",
        "rxCloseDate", "caseSuccess", "rxFdbk", "rxCaseId" })
@XmlRootElement
public class CustomerFeedbackResponseType {
    @XmlElement(required = true)
    protected String custFdbkObjId;
    @XmlElement(required = true)
    protected String serviceReqId;
    @XmlElement(required = true)
    protected String isRxPresent;
    @XmlElement(required = true)
    protected String rxCloseDate;
    @XmlElement(required = true)
    protected String caseSuccess;
    @XmlElement(required = true)
    protected String rxFdbk;
    @XmlElement(required = true)
    protected String rxCaseId;

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
