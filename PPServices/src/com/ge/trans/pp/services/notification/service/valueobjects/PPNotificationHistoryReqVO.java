package com.ge.trans.pp.services.notification.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPNotificationHistoryReqVO extends BaseVO {

    private String customerId;
    private String roadInitial;
    private String roadNo;
    private String fromDate;
    private String toDate;
    private String notificationTypes;
    private boolean inactiveNotification;
    private String region;
    private String timeZone;
    private List<String> products;

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

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

    public String getRoadNo() {
        return roadNo;
    }

    public void setRoadNo(String roadNo) {
        this.roadNo = roadNo;
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

    public boolean isInactiveNotification() {
        return inactiveNotification;
    }

    public void setInactiveNotification(boolean inactiveNotification) {
        this.inactiveNotification = inactiveNotification;
    }

    public String getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(String notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
