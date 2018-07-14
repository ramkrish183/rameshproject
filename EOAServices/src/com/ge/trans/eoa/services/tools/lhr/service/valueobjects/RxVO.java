package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.Date;

public class RxVO {

    private long objid;
    private String urgency;
    private String title;
    private String type;
    private String status;
    private String estimatedRepairTime;
    private String impactDescription;
    private Date openedOn;
    private Date deliveredOn;
    private Date closedOn;

    public RxVO(long objid, String urgency, String title, String type, String status, String estimatedRepairTime,
            String impactDescription, Date rxOpenDate, Date rxCloseDate, Date rxDeliveryDate) {
        this.objid = objid;
        this.urgency = urgency;
        this.title = title;
        this.type = type;
        this.status = status;
        this.estimatedRepairTime = estimatedRepairTime;
        this.impactDescription = impactDescription;
        this.openedOn = rxOpenDate;
        this.deliveredOn = rxDeliveryDate;
        this.closedOn = rxCloseDate;
    }

    public long getObjid() {
        return objid;
    }

    public void setObjid(long objid) {
        this.objid = objid;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimatedRepairTime() {
        return estimatedRepairTime;
    }

    public void setEstimatedRepairTime(String estimatedRepairTime) {
        this.estimatedRepairTime = estimatedRepairTime;
    }

    public String getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }

    public Date getOpenedOn() {
        return openedOn;
    }

    public void setOpenedOn(Date rxOpenDate) {
        this.openedOn = rxOpenDate;
    }

    public Date getDeliveredOn() {
        return deliveredOn;
    }

    public void setDeliveredOn(Date deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public Date getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(Date closedOn) {
        this.closedOn = closedOn;
    }
}
