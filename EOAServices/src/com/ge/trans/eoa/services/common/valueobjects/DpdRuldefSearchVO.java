/**
 * 
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/**
 * @author krishbal
 */
public class DpdRuldefSearchVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 3791240416098049030L;
    private Long kmDpdRuledefSeqId;
    private String message;
    private Long linkFinalRule;
    private String ruleType;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;
    private Long linkOriginalRecom;

    /**
     * @return the kmDpdRuledefSeqId
     */
    public Long getKmDpdRuledefSeqId() {
        return kmDpdRuledefSeqId;
    }

    /**
     * @param kmDpdRuledefSeqId
     *            the kmDpdRuledefSeqId to set
     */
    public void setKmDpdRuledefSeqId(final Long kmDpdRuledefSeqId) {
        this.kmDpdRuledefSeqId = kmDpdRuledefSeqId;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the linkFinalRule
     */
    public Long getLinkFinalRule() {
        return linkFinalRule;
    }

    /**
     * @param linkFinalRule
     *            the linkFinalRule to set
     */
    public void setLinkFinalRule(final Long linkFinalRule) {
        this.linkFinalRule = linkFinalRule;
    }

    /**
     * @return the ruleType
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * @param ruleType
     *            the ruleType to set
     */
    public void setRuleType(final String ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy
     *            the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(final String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the lastUpdatedDate
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * @param lastUpdatedDate
     *            the lastUpdatedDate to set
     */
    public void setLastUpdatedDate(final Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the linkOriginalRecom
     */
    public Long getLinkOriginalRecom() {
        return linkOriginalRecom;
    }

    /**
     * @param linkOriginalRecom
     *            the linkOriginalRecom to set
     */
    public void setLinkOriginalRecom(final Long linkOriginalRecom) {
        this.linkOriginalRecom = linkOriginalRecom;
    }

}
