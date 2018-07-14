package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxHistoryResponseType", propOrder = { "rxCaseId", "rxFeedback", "rxCloseDate", "serviceRequestId",
        "rxSuccess", "missCode", "feedToTools", "goodFeedback", "custFdbkObjId" })
@XmlRootElement
public class RxHistoryResponseType {
    @XmlElement(required = true)
    protected String rxCaseId;
    @XmlElement(required = true)
    protected String rxFeedback;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rxCloseDate;
    @XmlElement(required = true)
    protected String serviceRequestId;
    @XmlElement(required = true)
    protected String rxSuccess;
    @XmlElement(required = true)
    protected String missCode;
    @XmlElement(required = true)
    protected String feedToTools;
    @XmlElement(required = true)
    protected String goodFeedback;
    @XmlElement(required = true)
    protected String custFdbkObjId;

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

    public XMLGregorianCalendar getRxCloseDate() {
        return rxCloseDate;
    }

    public void setRxCloseDate(XMLGregorianCalendar rxCloseDate) {
        this.rxCloseDate = rxCloseDate;
    }

    public String getCustFdbkObjId() {
        return custFdbkObjId;
    }

    public void setCustFdbkObjId(String custFdbkObjId) {
        this.custFdbkObjId = custFdbkObjId;
    }
}
