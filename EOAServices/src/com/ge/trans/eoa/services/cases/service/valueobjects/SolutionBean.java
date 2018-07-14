/**
 * ============================================================
 * Classification	: GE Confidential
 * File 			: CaseDetailsVO.java
 * Description 		:
 *
 * Package			: com.ge.trans.eoa.services.cases.service.valueobjects
 * Author 			: iGATE Global Solutions Ltd.
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		:
 * History
 * Modified By 		: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created : Mar 16 , 2015
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :This is a plain POJO class which carries details about case.
 * @History :
 ******************************************************************************/

public class SolutionBean {
    private String caseId;
    private String caseTitle;
    private String caseType;
    private Long caseObjid;
    private String roadNumber;
    private String model;
    private String caseNumber;
    private String casePriority;
    private String recPendingAlert;
    private String heading;
    private String onBoardRNH;
    private String fleet;
    private String pendingFaults;
    private String services;
    private String customerRNH;
    private String location;
    private String appendedDate;
    private String nextScheduledRun;
    private String badActor;
    private String title;
    private String repairTime;
    private Date deliveryDate;
    private String revisionNo;
    private Long messageObjId;
    private String reIssue;
    private String urgency;
    private Long rxObjId;
    private Date creationDate;
    private String rxType;
    
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Long getCaseObjid() {
        return caseObjid;
    }

    public void setCaseObjid(Long caseObjid) {
        this.caseObjid = caseObjid;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCasePriority() {
        return casePriority;
    }

    public void setCasePriority(String casePriority) {
        this.casePriority = casePriority;
    }

    public String getRecPendingAlert() {
        return recPendingAlert;
    }

    public void setRecPendingAlert(String recPendingAlert) {
        this.recPendingAlert = recPendingAlert;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getOnBoardRNH() {
        return onBoardRNH;
    }

    public void setOnBoardRNH(String onBoardRNH) {
        this.onBoardRNH = onBoardRNH;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    public String getPendingFaults() {
        return pendingFaults;
    }

    public void setPendingFaults(String pendingFaults) {
        this.pendingFaults = pendingFaults;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getCustomerRNH() {
        return customerRNH;
    }

    public void setCustomerRNH(String customerRNH) {
        this.customerRNH = customerRNH;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppendedDate() {
        return appendedDate;
    }

    public void setAppendedDate(String appendedDate) {
        this.appendedDate = appendedDate;
    }

    public String getNextScheduledRun() {
        return nextScheduledRun;
    }

    public void setNextScheduledRun(String nextScheduledRun) {
        this.nextScheduledRun = nextScheduledRun;
    }

    public String getBadActor() {
        return badActor;
    }

    public void setBadActor(String badActor) {
        this.badActor = badActor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRevisionNo() {
        return revisionNo;
    }

    public void setRevisionNo(String revisionNo) {
        this.revisionNo = revisionNo;
    }

    public Long getMessageObjId() {
        return messageObjId;
    }

    public void setMessageObjId(Long messageObjId) {
        this.messageObjId = messageObjId;
    }

    public Long getRxObjId() {
        return rxObjId;
    }

    public void setRxObjId(Long rxObjId) {
        this.rxObjId = rxObjId;
    }

    public String getReIssue() {
        return reIssue;
    }

    public void setReIssue(String reIssue) {
        this.reIssue = reIssue;
    }

	public String getRxType() {
		return rxType;
	}

	public void setRxType(String rxType) {
		this.rxType = rxType;
	}
    
}
