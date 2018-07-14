/**
 * ============================================================
 * File : RxSearchResultServiceVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.cases.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 20, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RxSearchResultServiceVO extends BaseVO {

    static final long serialVersionUID = 1299447734L;
    private String strRxId;
    private String strTitle;
    private String strStatus;
    private String strStatusValue;
    private String strType;
    private String strUrgRepair;
    private String strEstRepairTime;
    private String strUrgRepairValue;
    private String strEstRepairTimeValue;
    private String strActiveStatus;
    private String strVersion;
    private String strCaseRx;
    private String lastUpdatedBy;
    private String strAuthor;
    private String notes;
    private String strLocoImpact;
    private String strModel;
    private Date lastUpdateDate;
    private String lastEdited;
    private long noOfActiveWeeks;
    private String strDeliveries;
    private String strRank;
    private String strPrecision;
    private String strAccuracy;
    private String strReIssuePercent;
    private String strNTFPercent;
    private String createdBy;
    
    public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(final String lastEdited) {
        this.lastEdited = lastEdited;
    }

    /**
     * @return the strCaseRx
     */
    public String getStrCaseRx() {
        return strCaseRx;
    }

    /**
     * @param strCaseRx
     *            the strCaseRx to set
     */
    public void setStrCaseRx(final String strCaseRx) {
        this.strCaseRx = strCaseRx;
    }

    /**
     * @return the strRxId
     */
    public String getStrRxId() {
        return strRxId;
    }

    /**
     * @param strRxId
     *            the strRxId to set
     */
    public void setStrRxId(final String strRxId) {
        this.strRxId = strRxId;
    }

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
     * @return the strStatus
     */
    public String getStrStatus() {
        return strStatus;
    }

    /**
     * @param strStatus
     *            the strStatus to set
     */
    public void setStrStatus(final String strStatus) {
        this.strStatus = strStatus;
    }

    /**
     * @return the strType
     */
    public String getStrType() {
        return strType;
    }

    /**
     * @param strType
     *            the strType to set
     */
    public void setStrType(final String strType) {
        this.strType = strType;
    }

    /**
     * @return the strUrgRepair
     */
    public String getStrUrgRepair() {
        return strUrgRepair;
    }

    /**
     * @param strUrgRepair
     *            the strUrgRepair to set
     */
    public void setStrUrgRepair(final String strUrgRepair) {
        this.strUrgRepair = strUrgRepair;
    }

    /**
     * @return the strEstRepairTime
     */
    public String getStrEstRepairTime() {
        return strEstRepairTime;
    }

    /**
     * @param strEstRepairTime
     *            the strEstRepairTime to set
     */
    public void setStrEstRepairTime(final String strEstRepairTime) {
        this.strEstRepairTime = strEstRepairTime;
    }

    /**
     * @return the strActiveStatus
     */
    public String getStrActiveStatus() {
        return strActiveStatus;
    }

    /**
     * @param strActiveStatus
     *            the strActiveStatus to set
     */
    public void setStrActiveStatus(final String strActiveStatus) {
        this.strActiveStatus = strActiveStatus;
    }

    /**
     * @return the strUrgRepairValue
     */
    public String getStrUrgRepairValue() {
        return strUrgRepairValue;
    }

    /**
     * @param strUrgRepairValue
     *            the strUrgRepairValue to set
     */
    public void setStrUrgRepairValue(final String strUrgRepairValue) {
        this.strUrgRepairValue = strUrgRepairValue;
    }

    /**
     * @return the strEstRepairTimeValue
     */
    public String getStrEstRepairTimeValue() {
        return strEstRepairTimeValue;
    }

    /**
     * @param strEstRepairTimeValue
     *            the strEstRepairTimeValue to set
     */
    public void setStrEstRepairTimeValue(final String strEstRepairTimeValue) {
        this.strEstRepairTimeValue = strEstRepairTimeValue;
    }

    /**
     * @return the strVersion
     */
    public String getStrVersion() {
        return strVersion;
    }

    /**
     * @param strVersion
     *            the strVersion to set
     */
    public void setStrVersion(final String strVersion) {
        this.strVersion = strVersion;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxId", strRxId)
                .append("strTitle", strTitle).append("strStatus", strStatus).append("strType", strType)
                .append("strUrgRepair", strUrgRepair).append("strEstRepairTime", strEstRepairTime)
                .append("strActiveStatus", strActiveStatus).append("strUrgRepairValue", strUrgRepairValue)
                .append("strEstRepairTimeValue", strEstRepairTimeValue).append("strVersion", strVersion)
                .append("strCaseRx", strCaseRx).toString();
    }

    /**
     * @return the strStatusValue
     */
    public String getStrStatusValue() {
        return strStatusValue;
    }

    /**
     * @param strStatusValue
     *            the strStatusValue to set
     */
    public void setStrStatusValue(final String strStatusValue) {
        this.strStatusValue = strStatusValue;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(final String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getStrAuthor() {
        return strAuthor;
    }

    public void setStrAuthor(final String strAuthor) {
        this.strAuthor = strAuthor;
    }

    public String getStrLocoImpact() {
        return strLocoImpact;
    }

    public void setStrLocoImpact(final String strLocoImpact) {
        this.strLocoImpact = strLocoImpact;
    }

    public String getStrModel() {
        return strModel;
    }

    public void setStrModel(final String strModel) {
        this.strModel = strModel;
    }

    public long getNoOfActiveWeeks() {
        return noOfActiveWeeks;
    }

    public void setNoOfActiveWeeks(final long noOfActiveWeeks) {
        this.noOfActiveWeeks = noOfActiveWeeks;
    }

    public String getStrDeliveries() {
        return strDeliveries;
    }

    public void setStrDeliveries(String strDeliveries) {
        this.strDeliveries = strDeliveries;
    }

    public String getStrRank() {
        return strRank;
    }

    public void setStrRank(String strRank) {
        this.strRank = strRank;
    }

    public String getStrPrecision() {
        return strPrecision;
    }

    public void setStrPrecision(String strPrecision) {
        this.strPrecision = strPrecision;
    }

    public String getStrAccuracy() {
        return strAccuracy;
    }

    public void setStrAccuracy(String strAccuracy) {
        this.strAccuracy = strAccuracy;
    }

    public String getStrReIssuePercent() {
        return strReIssuePercent;
    }

    public void setStrReIssuePercent(String strReIssuePercent) {
        this.strReIssuePercent = strReIssuePercent;
    }

    public String getStrNTFPercent() {
        return strNTFPercent;
    }

    public void setStrNTFPercent(String strNTFPercent) {
        this.strNTFPercent = strNTFPercent;
    }

}