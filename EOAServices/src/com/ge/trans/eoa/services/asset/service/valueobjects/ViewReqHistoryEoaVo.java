package com.ge.trans.eoa.services.asset.service.valueobjects;

public class ViewReqHistoryEoaVo {
    private String customerID;
    private String roadIntial;
    private Long roadNumber;
    private String messageID;
    private String toDate;
    private String fromDate;
    private String device;
    private String messageIdCustomer;
    private String messageIdAuto;
    private String isInternal;

    public String getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(String isInternal) {
        this.isInternal = isInternal;
    }

    public String getMessageIdAuto() {
        return messageIdAuto;
    }

    public void setMessageIdAuto(String messageIdAuto) {
        this.messageIdAuto = messageIdAuto;
    }

    public String getMessageIdCustomer() {
        return messageIdCustomer;
    }

    public void setMessageIdCustomer(String messageIdCustomer) {
        this.messageIdCustomer = messageIdCustomer;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getRoadIntial() {
        return roadIntial;
    }

    public void setRoadIntial(String roadIntial) {
        this.roadIntial = roadIntial;
    }

    public Long getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(Long roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
