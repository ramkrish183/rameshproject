package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

public class RxStatusHistoryVO {
    private Date rxStatusDate;
    private String status;
    private String comments;

    public Date getRxStatusDate() {
        return rxStatusDate;
    }

    public void setRxStatusDate(Date rxStatusDate) {
        this.rxStatusDate = rxStatusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
