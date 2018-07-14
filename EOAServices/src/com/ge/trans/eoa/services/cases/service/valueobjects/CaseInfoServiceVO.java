/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseInfoServiceVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 21, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CaseInfoServiceVO extends BaseVO {

    private static final long serialVersionUID = 13406664L;
    private String strCaseSeqId;

    private String strTitle;

    private String strCaseId;

    private String strReason;

    private String strCondition;

    private String strActualCondition;

    private Date dtCreateDate;

    private String strOwner;

    private String strQueue;

    private Date dtStkyCreatOn;

    private String strStkyCreatBy;

    private String strStkyNotes;

    private String strStickyExist;

    private String strStickyType;

    private String strFileCount;

    private String strAssetNo;

    /* added for Sprint17_EOAAssetCases start */
    private String strCaseType;

    private String strQueueType;

    private Date dtDelvDate;

    private Date dtRxCloseDate;

    private String strRxCaseId;

    private String strCaseSuccess;

    private String caseSolutionStatus;

    private String customerName;
    private String roadNumber;
    private String model;
    private String caseNumber;
    private String casePriority;
    private String recPendingAlert;
    private String gpsHeading;
    private String onBoardRNH;

    private String fleet;
    private String pendingFaults;
    private String services;
    private String customerRNH;
    private Date appendedDate;
    private Date nextScheduledRun;
    private String badActor;
    private String caseObjId;
    private String caseTitle;
    private String caseType;
    private String gpsLatitude;
    private String gpslongitude;
    private String vehicleObjId;
    private String controllerConfig;
    private String userName;
    private String caseStatus;

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(String controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getVehicleObjId() {
        return vehicleObjId;
    }

    public void setVehicleObjId(String vehicleObjId) {
        this.vehicleObjId = vehicleObjId;
    }

    public String getGpsHeading() {
        return gpsHeading;
    }

    public void setGpsHeading(String gpsHeading) {
        this.gpsHeading = gpsHeading;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    private String creationDate;

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the roadNumber
     */
    public String getRoadNumber() {
        return roadNumber;
    }

    /**
     * @param roadNumber
     *            the roadNumber to set
     */
    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model
     *            the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the caseNumber
     */
    public String getCaseNumber() {
        return caseNumber;
    }

    /**
     * @param caseNumber
     *            the caseNumber to set
     */
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    /**
     * @return the casePriority
     */
    public String getCasePriority() {
        return casePriority;
    }

    /**
     * @param casePriority
     *            the casePriority to set
     */
    public void setCasePriority(String casePriority) {
        this.casePriority = casePriority;
    }

    /**
     * @return the recPendingAlert
     */
    public String getRecPendingAlert() {
        return recPendingAlert;
    }

    /**
     * @param recPendingAlert
     *            the recPendingAlert to set
     */
    public void setRecPendingAlert(String recPendingAlert) {
        this.recPendingAlert = recPendingAlert;
    }

    public String getCaseObjId() {
        return caseObjId;
    }

    public void setCaseObjId(String caseObjId) {
        this.caseObjId = caseObjId;
    }

    /**
     * @return the onBoardRNH
     */
    public String getOnBoardRNH() {
        return onBoardRNH;
    }

    /**
     * @param onBoardRNH
     *            sets the onBoardRNH
     */
    public void setOnBoardRNH(String onBoardRNH) {
        this.onBoardRNH = onBoardRNH;
    }

    /**
     * @return the fleet
     */

    public String getFleet() {
        return fleet;
    }

    /**
     * @param sets
     *            the fleet
     */
    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

    /**
     * @return the pendingFaults
     */
    public String getPendingFaults() {
        return pendingFaults;
    }

    /**
     * @param sets
     *            the pendingFaults
     */
    public void setPendingFaults(String pendingFaults) {
        this.pendingFaults = pendingFaults;
    }

    /**
     * @return the services
     */
    public String getServices() {
        return services;
    }

    /**
     * @param sets
     *            the services
     */
    public void setServices(String services) {
        this.services = services;
    }

    /**
     * @return the customerRNH
     */
    public String getCustomerRNH() {
        return customerRNH;
    }

    /**
     * @param sets
     *            the customerRNH
     */
    public void setCustomerRNH(String customerRNH) {
        this.customerRNH = customerRNH;
    }

    public String getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public String getGpslongitude() {
        return gpslongitude;
    }

    public void setGpslongitude(String gpslongitude) {
        this.gpslongitude = gpslongitude;
    }

    /**
     * @return the appendedDate
     */
    public Date getAppendedDate() {
        return appendedDate;
    }

    /**
     * @param sets
     *            the appendedDate
     */
    public void setAppendedDate(Date appendedDate) {
        this.appendedDate = appendedDate;
    }

    /**
     * @return the nextScheduledRun
     */
    public Date getNextScheduledRun() {
        return nextScheduledRun;
    }

    /**
     * @param sets
     *            the nextScheduledRun
     */
    public void setNextScheduledRun(Date nextScheduledRun) {
        this.nextScheduledRun = nextScheduledRun;
    }

    /**
     * @return the badActor
     */
    public String getBadActor() {
        return badActor;
    }

    /**
     * @param sets
     *            the badActor
     */
    public void setBadActor(String badActor) {
        this.badActor = badActor;
    }

    /**
     * @return the caseSolutionStatus
     */
    public String getCaseSolutionStatus() {
        return caseSolutionStatus;
    }

    public void setCaseSolutionStatus(String caseSolutionStatus) {
        this.caseSolutionStatus = caseSolutionStatus;
    }

    public String getStrRxCaseId() {
        return strRxCaseId;
    }

    public void setStrRxCaseId(String strRxCaseId) {
        this.strRxCaseId = strRxCaseId;
    }

    public String getStrCaseSuccess() {
        return strCaseSuccess;
    }

    public void setStrCaseSuccess(String strCaseSuccess) {
        this.strCaseSuccess = strCaseSuccess;
    }

    public Date getDtDelvDate() {
        return dtDelvDate;
    }

    public void setDtDelvDate(Date dtDelvDate) {
        this.dtDelvDate = dtDelvDate;
    }

    public Date getDtRxCloseDate() {
        return dtRxCloseDate;
    }

    public void setDtRxCloseDate(Date dtRxCloseDate) {
        this.dtRxCloseDate = dtRxCloseDate;
    }

    public String getStrQueueType() {
        return strQueueType;
    }

    public void setStrQueueType(String strQueueType) {
        this.strQueueType = strQueueType;
    }

    public String getStrCaseType() {
        return strCaseType;
    }

    public void setStrCaseType(final String strCaseType) {
        this.strCaseType = strCaseType;
    }
    /* added for Sprint17_EOAAssetCases end */

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle
     *            the strTitle to set
     */
    public void setStrTitle(final String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    /**
     * @return the strReason
     */
    public String getStrReason() {
        return strReason;
    }

    /**
     * @param strReason
     *            the strReason to set
     */
    public void setStrReason(final String strReason) {
        this.strReason = strReason;
    }

    /**
     * @return the strCondition
     */
    public String getStrCondition() {
        return strCondition;
    }

    /**
     * @param strCondition
     *            the strCondition to set
     */
    public void setStrCondition(final String strCondition) {
        this.strCondition = strCondition;
    }

    /**
     * @return the strOwner
     */
    public String getStrOwner() {
        return strOwner;
    }

    /**
     * @param strOwner
     *            the strOwner to set
     */
    public void setStrOwner(final String strOwner) {
        this.strOwner = strOwner;
    }

    /**
     * @return the strQueue
     */
    public String getStrQueue() {
        return strQueue;
    }

    /**
     * @param strQueue
     *            the strQueue to set
     */
    public void setStrQueue(final String strQueue) {
        this.strQueue = strQueue;
    }

    /**
     * @return the strStkyCreatBy
     */
    public String getStrStkyCreatBy() {
        return strStkyCreatBy;
    }

    /**
     * @param strStkyCreatBy
     *            the strStkyCreatBy to set
     */
    public void setStrStkyCreatBy(final String strStkyCreatBy) {
        this.strStkyCreatBy = strStkyCreatBy;
    }

    /**
     * @return the strStkyNotes
     */
    public String getStrStkyNotes() {
        return strStkyNotes;
    }

    /**
     * @param strStkyNotes
     *            the strStkyNotes to set
     */
    public void setStrStkyNotes(final String strStkyNotes) {
        this.strStkyNotes = strStkyNotes;
    }

    /**
     * @return the strStickyExist
     */
    public String getStrStickyExist() {
        return strStickyExist;
    }

    /**
     * @param strStickyExist
     *            the strStickyExist to set
     */
    public void setStrStickyExist(final String strStickyExist) {
        this.strStickyExist = strStickyExist;
    }

    /**
     * @return the strStickyType
     */
    public String getStrStickyType() {
        return strStickyType;
    }

    /**
     * @param strStickyType
     *            the strStickyType to set
     */
    public void setStrStickyType(final String strStickyType) {
        this.strStickyType = strStickyType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strTitle", strTitle)
                .append("strCaseId", strCaseId).append("strReason", strReason).append("strCondition", strCondition)
                .append("dtCreateDate", dtCreateDate).append("strOwner", strOwner).append("strQueue", strQueue)
                .append("dtStkyCreatOn", dtStkyCreatOn).append("strStkyCreatBy", strStkyCreatBy)
                .append("strStkyNotes", strStkyNotes).append("strStickyExist", strStickyExist)
                .append("strStickyType", strStickyType).toString();
    }

    /**
     * @return the dtCreateDate
     */
    public Date getDtCreateDate() {
        return dtCreateDate;
    }

    /**
     * @param dtCreateDate
     *            the dtCreateDate to set
     */
    public void setDtCreateDate(final Date dtCreateDate) {
        this.dtCreateDate = dtCreateDate;
    }

    /**
     * @return the dtStkyCreatOn
     */
    public Date getDtStkyCreatOn() {
        return dtStkyCreatOn;
    }

    /**
     * @param dtStkyCreatOn
     *            the dtStkyCreatOn to set
     */
    public void setDtStkyCreatOn(final Date dtStkyCreatOn) {
        this.dtStkyCreatOn = dtStkyCreatOn;
    }

    public String getStrActualCondition() {
        return strActualCondition;
    }

    public void setStrActualCondition(final String strActualCondition) {
        this.strActualCondition = strActualCondition;
    }

    /**
     * @return the strFileCount
     */
    public String getStrFileCount() {
        return strFileCount;
    }

    /**
     * @param strFileCount
     *            the strFileCount to set
     */
    public void setStrFileCount(final String strFileCount) {
        this.strFileCount = strFileCount;
    }

    /**
     * @return the strAssetNo
     */
    public String getStrAssetNo() {
        return strAssetNo;
    }

    /**
     * @param strAssetNo
     *            the strAssetNo to set
     */
    public void setStrAssetNo(final String strAssetNo) {
        this.strAssetNo = strAssetNo;
    }

    public String getStrCaseSeqId() {
        return strCaseSeqId;
    }

    public void setStrCaseSeqId(final String strCaseSeqId) {
        this.strCaseSeqId = strCaseSeqId;
    }

}