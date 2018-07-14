/**
 * 
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/**
 * @author krishbal
 */
public class DpdRulMetricsSearchVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = -7649127713193771928L;
    private Long getKmDpdRuleMetricsSeqId;
    private Long linkFinalRule;
    private String firedRules;
    private String successRules;
    private String accuratePercent;
    private String missRules;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;

    /**
     * @return the getKmDpdRuleMetricsSeqId
     */
    public Long getGetKmDpdRuleMetricsSeqId() {
        return getKmDpdRuleMetricsSeqId;
    }

    /**
     * @param getKmDpdRuleMetricsSeqId
     *            the getKmDpdRuleMetricsSeqId to set
     */
    public void setGetKmDpdRuleMetricsSeqId(final Long getKmDpdRuleMetricsSeqId) {
        this.getKmDpdRuleMetricsSeqId = getKmDpdRuleMetricsSeqId;
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
     * @return the firedRules
     */
    public String getFiredRules() {
        return firedRules;
    }

    /**
     * @param firedRules
     *            the firedRules to set
     */
    public void setFiredRules(final String firedRules) {
        this.firedRules = firedRules;
    }

    /**
     * @return the successRules
     */
    public String getSuccessRules() {
        return successRules;
    }

    /**
     * @param successRules
     *            the successRules to set
     */
    public void setSuccessRules(final String successRules) {
        this.successRules = successRules;
    }

    /**
     * @return the accuratePercent
     */
    public String getAccuratePercent() {
        return accuratePercent;
    }

    /**
     * @param accuratePercent
     *            the accuratePercent to set
     */
    public void setAccuratePercent(final String accuratePercent) {
        this.accuratePercent = accuratePercent;
    }

    /**
     * @return the missRules
     */
    public String getMissRules() {
        return missRules;
    }

    /**
     * @param missRules
     *            the missRules to set
     */
    public void setMissRules(final String missRules) {
        this.missRules = missRules;
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

}
