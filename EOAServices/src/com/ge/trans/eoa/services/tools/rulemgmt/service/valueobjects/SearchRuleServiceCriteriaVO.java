/**
 * ============================================================
 * File : SearchRuleServiceCriteriaVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class SearchRuleServiceCriteriaVO extends BaseVO {

    static final long serialVersionUID = 911777484L;
    private String strFault;
    private String strFamily;
    private String strSubSystem;
    private String strRuleType;
    private String strCreatedBy;
    private String strCreatedSince;
    private String strRecommendation;
    private String strRxTitle;
    private String strActivateBy;
    private String strActivateSince;
    private String strIsActive;
    private String strIsComplete;
    private String strRuleTitle;
    private String strRuleID;
    private String strVersion;
    private String strApplicationType;

    // Added for Rule Search Criteria
    private String strLastUpdatedFromDate;
    private String strLastUpdatedToDate;
    private String strCreatedByFromDate;
    private String strCreatedByToDate;
    private String strStatus;
    private String strLastUpdatedBy;
    private boolean blnDefaultLoad;
    private String strCustomer;

    public String getStrCustomer() {
        return strCustomer;
    }

    public void setStrCustomer(String strCustomer) {
        this.strCustomer = strCustomer;
    }

    /**
     * @return the strFault
     */
    public String getStrFault() {
        return strFault;
    }

    /**
     * @param strFault
     *            the strFault to set
     */
    public void setStrFault(String strFault) {
        this.strFault = strFault;
    }

    /**
     * @return the strSubSystem
     */
    public String getStrSubSystem() {
        return strSubSystem;
    }

    /**
     * @param strSubSystem
     *            the strSubSystem to set
     */
    public void setStrSubSystem(String strSubSystem) {
        this.strSubSystem = strSubSystem;
    }

    /**
     * @return the strRuleType
     */
    public String getStrRuleType() {
        return strRuleType;
    }

    /**
     * @param strRuleType
     *            the strRuleType to set
     */
    public void setStrRuleType(String strRuletype) {
        this.strRuleType = strRuletype;
    }

    /**
     * @return the strCreatedBy
     */
    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    /**
     * @param strCreatedBy
     *            the strCreatedBy to set
     */
    public void setStrCreatedBy(String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    /**
     * @return the strCreatedSince
     */
    public String getStrCreatedSince() {
        return strCreatedSince;
    }

    /**
     * @param strCreatedSince
     *            the strCreatedSince to set
     */
    public void setStrCreatedSince(String strCreatedSince) {
        this.strCreatedSince = strCreatedSince;
    }

    /**
     * @return the strRecommendation
     */
    public String getStrRecommendation() {
        return strRecommendation;
    }

    /**
     * @param strRecommendation
     *            the strRecommendation to set
     */
    public void setStrRecommendation(String strRecommendation) {
        this.strRecommendation = strRecommendation;
    }

    /**
     * @return the strActivateBy
     */
    public String getStrActivateBy() {
        return strActivateBy;
    }

    /**
     * @param strActivateBy
     *            the strActivateBy to set
     */
    public void setStrActivateBy(String strActivateBy) {
        this.strActivateBy = strActivateBy;
    }

    /**
     * @return the strActivateSince
     */
    public String getStrActivateSince() {
        return strActivateSince;
    }

    /**
     * @param strActivateSince
     *            the strActivateSince to set
     */
    public void setStrActivateSince(String strActivateSince) {
        this.strActivateSince = strActivateSince;
    }

    /**
     * @return the strIsActive
     */
    public String getStrIsActive() {
        return strIsActive;
    }

    /**
     * @param strIsActive
     *            the strIsActive to set
     */
    public void setStrIsActive(String strIsActive) {
        this.strIsActive = strIsActive;
    }

    /**
     * @return the strIsComplete
     */
    public String getStrIsComplete() {
        return strIsComplete;
    }

    /**
     * @param strIsComplete
     *            the strIsComplete to set
     */
    public void setStrIsComplete(String strIsComplete) {
        this.strIsComplete = strIsComplete;
    }

    /**
     * @return the strRuleTitle
     */
    public String getStrRuleTitle() {
        return strRuleTitle;
    }

    /**
     * @param strRuleTitle
     *            the strRuleTitle to set
     */
    public void setStrRuleTitle(String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
    }

    /**
     * @return the strRuleID
     */
    public String getStrRuleID() {
        return strRuleID;
    }

    /**
     * @param strRuleID
     *            the strRuleID to set
     */
    public void setStrRuleID(String strRuleID) {
        this.strRuleID = strRuleID;
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
    public void setStrVersion(String strVersion) {
        this.strVersion = strVersion;
    }

    /**
     * @return the strApplicationType
     */
    public String getStrApplicationType() {
        return strApplicationType;
    }

    /**
     * @param strApplicationType
     *            the strApplicationType to set
     */
    public void setStrApplicationType(String strApplicationType) {
        this.strApplicationType = strApplicationType;
    }

    /**
     * @return the strFamily
     */
    public String getStrFamily() {
        return strFamily;
    }

    /**
     * @param strFamily
     *            the strFamily to set
     */
    public void setStrFamily(String strFamily) {
        this.strFamily = strFamily;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFault", strFault)
                .append("strSubSystem", strSubSystem).append("strRuleType", strRuleType)
                .append("strCreatedBy", strCreatedBy).append("strCreatedSince", strCreatedSince)
                .append("strRecommendation", strRecommendation).append("strActivateBy", strActivateBy)
                .append("strActivateSince", strActivateSince).append("strIsActive", strIsActive)
                .append("strIsComplete", strIsComplete).append("strRuleTitle", strRuleTitle)
                .append("strFamily", strFamily).append("strRuleID", strRuleID).append("strVersion", strVersion)
                .append("strApplicationType", strApplicationType).append("strRxTitle", strRxTitle).toString();
    }

    /**
     * @return the strLastUpdatedFromDate
     */
    public String getStrLastUpdatedFromDate() {
        return strLastUpdatedFromDate;
    }

    /**
     * @param strLastUpdatedFromDate
     *            the strLastUpdatedFromDate to set
     */
    public void setStrLastUpdatedFromDate(String strLastUpdatedFromDate) {
        this.strLastUpdatedFromDate = strLastUpdatedFromDate;
    }

    /**
     * @return the strLastUpdatedToDate
     */
    public String getStrLastUpdatedToDate() {
        return strLastUpdatedToDate;
    }

    /**
     * @param strLastUpdatedToDate
     *            the strLastUpdatedToDate to set
     */
    public void setStrLastUpdatedToDate(String strLastUpdatedToDate) {
        this.strLastUpdatedToDate = strLastUpdatedToDate;
    }

    /**
     * @return the strCreatedByFromDate
     */
    public String getStrCreatedByFromDate() {
        return strCreatedByFromDate;
    }

    /**
     * @param strCreatedByFromDate
     *            the strCreatedByFromDate to set
     */
    public void setStrCreatedByFromDate(String strCreatedByFromDate) {
        this.strCreatedByFromDate = strCreatedByFromDate;
    }

    /**
     * @return the strCreatedByToDate
     */
    public String getStrCreatedByToDate() {
        return strCreatedByToDate;
    }

    /**
     * @param strCreatedByToDate
     *            the strCreatedByToDate to set
     */
    public void setStrCreatedByToDate(String strCreatedByToDate) {
        this.strCreatedByToDate = strCreatedByToDate;
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
    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    /**
     * @return the strLastUpdatedBy
     */
    public String getStrLastUpdatedBy() {
        return strLastUpdatedBy;
    }

    /**
     * @param strLastUpdatedBy
     *            the strLastUpdatedBy to set
     */
    public void setStrLastUpdatedBy(String strLastUpdatedBy) {
        this.strLastUpdatedBy = strLastUpdatedBy;
    }

    public boolean isBlnDefaultLoad() {
        return blnDefaultLoad;
    }

    public void setBlnDefaultLoad(boolean blnDefaultLoad) {
        this.blnDefaultLoad = blnDefaultLoad;
    }

    public String getStrRxTitle() {
        return strRxTitle;
    }

    public void setStrRxTitle(String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }

}
