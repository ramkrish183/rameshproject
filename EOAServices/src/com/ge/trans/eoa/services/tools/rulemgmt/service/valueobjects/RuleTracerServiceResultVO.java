package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

public class RuleTracerServiceResultVO {

    private String strTrackingSeqId = RMDCommonConstants.EMPTY_STRING;
    private String strFaultRuleId = RMDCommonConstants.EMPTY_STRING;
    private String strCaseID = RMDCommonConstants.EMPTY_STRING;
    private String strCaseTitle = RMDCommonConstants.EMPTY_STRING;
    private String strCreatedBy = RMDCommonConstants.EMPTY_STRING;
    private Date strCreationDate;
    private Date strLastUpdatedDate;
    private String strStatus = RMDCommonConstants.EMPTY_STRING;
    private boolean bTrackingLink = RMDCommonConstants.FALSE;
    private boolean bTrackingText = RMDCommonConstants.FALSE;

    /**
     * @return the strTrackingSeqId
     */
    public String getStrTrackingSeqId() {
        return strTrackingSeqId;
    }

    /**
     * @param strTrackingSeqId
     *            the strTrackingSeqId to set
     */
    public void setStrTrackingSeqId(String strTrackingSeqId) {
        this.strTrackingSeqId = strTrackingSeqId;
    }

    /**
     * @return the strFaultRuleId
     */
    public String getStrFaultRuleId() {
        return strFaultRuleId;
    }

    /**
     * @param strFaultRuleId
     *            the strFaultRuleId to set
     */
    public void setStrFaultRuleId(String strFaultRuleId) {
        this.strFaultRuleId = strFaultRuleId;
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
     * @return the strCreationDate
     */
    public Date getStrCreationDate() {
        return strCreationDate;
    }

    /**
     * @param strCreationDate
     *            the strCreationDate to set
     */
    public void setStrCreationDate(Date strCreationDate) {
        this.strCreationDate = strCreationDate;
    }

    /**
     * @return the strLastUpdatedDate
     */
    public Date getStrLastUpdatedDate() {
        return strLastUpdatedDate;
    }

    /**
     * @param strLastUpdatedDate
     *            the strLastUpdatedDate to set
     */
    public void setStrLastUpdatedDate(Date strLastUpdatedDate) {
        this.strLastUpdatedDate = strLastUpdatedDate;
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
     * @return the bTrackingLink
     */
    public boolean isBTrackingLink() {
        return bTrackingLink;
    }

    /**
     * @param trackingLink
     *            the bTrackingLink to set
     */
    public void setBTrackingLink(boolean trackingLink) {
        bTrackingLink = trackingLink;
    }

    /**
     * @return the bTrackingText
     */
    public boolean isBTrackingText() {
        return bTrackingText;
    }

    /**
     * @param trackingText
     *            the bTrackingText to set
     */
    public void setBTrackingText(boolean trackingText) {
        bTrackingText = trackingText;
    }

    public String getStrCaseID() {
        return strCaseID;
    }

    public void setStrCaseID(String strCaseID) {
        this.strCaseID = strCaseID;
    }

    public String getStrCaseTitle() {
        return strCaseTitle;
    }

    public void setStrCaseTitle(String strCaseTitle) {
        this.strCaseTitle = strCaseTitle;
    }

}
