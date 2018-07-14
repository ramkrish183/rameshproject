/**
 * ============================================================
 * Classification: GE Confidential
 * File : DpdRulhisSearchHVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 18, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 18, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class DpdRulhisSearchVO extends BaseVO {

    static final long serialVersionUID = 1542136L;
    private Long kmDpdRulhisSeqId;
    private DpdFinrulSearchVO dpdFinrulHVO;
    private Long versionNo;
    private Long active;
    private Long linkFinalRule;
    private Long maxRowVersion;
    private String activatedBy;
    private Date activationDate;
    private Long previousVersionId;
    private Long completed;
    private Long originalId;
    private String revHistory;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;
    private DpdRuldefSearchVO dpdRuldefSearchVO;
    private DpdRulMetricsSearchVO dpdRulMetricsSearchVO;

    /**
     * @return the kmDpdRulhisSeqId
     */
    public Long getKmDpdRulhisSeqId() {
        return kmDpdRulhisSeqId;
    }

    /**
     * @param kmDpdRulhisSeqId
     *            the kmDpdRulhisSeqId to set
     */
    public void setKmDpdRulhisSeqId(final Long kmDpdRulhisSeqId) {
        this.kmDpdRulhisSeqId = kmDpdRulhisSeqId;
    }

    /**
     * @return the versionNo
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * @param versionNo
     *            the versionNo to set
     */
    public void setVersionNo(final Long versionNo) {
        this.versionNo = versionNo;
    }

    /**
     * @return the active
     */
    public Long getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(final Long active) {
        this.active = active;
    }

    /**
     * @return the activatedBy
     */
    public String getActivatedBy() {
        return activatedBy;
    }

    /**
     * @param activatedBy
     *            the activatedBy to set
     */
    public void setActivatedBy(final String activatedBy) {
        this.activatedBy = activatedBy;
    }

    /**
     * @return the activationDate
     */
    public Date getActivationDate() {
        return activationDate;
    }

    /**
     * @param activationDate
     *            the activationDate to set
     */
    public void setActivationDate(final Date activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * @return the previousVersionId
     */
    public Long getPreviousVersionId() {
        return previousVersionId;
    }

    /**
     * @param previousVersionId
     *            the previousVersionId to set
     */
    public void setPreviousVersionId(final Long previousVersionId) {
        this.previousVersionId = previousVersionId;
    }

    /**
     * @return the completed
     */
    public Long getCompleted() {
        return completed;
    }

    /**
     * @param completed
     *            the completed to set
     */
    public void setCompleted(final Long completed) {
        this.completed = completed;
    }

    /**
     * @return the originalId
     */
    public Long getOriginalId() {
        return originalId;
    }

    /**
     * @param originalId
     *            the originalId to set
     */
    public void setOriginalId(final Long originalId) {
        this.originalId = originalId;
    }

    /**
     * @return the revHistory
     */
    public String getRevHistory() {
        return revHistory;
    }

    /**
     * @param revHistory
     *            the revHistory to set
     */
    public void setRevHistory(final String revHistory) {
        this.revHistory = revHistory;
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
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return the dpdFinrulHVO
     */
    public DpdFinrulSearchVO getDpdFinrulHVO() {
        return dpdFinrulHVO;
    }

    /**
     * @param dpdFinrulHVO
     *            the dpdFinrulHVO to set
     */
    public void setDpdFinrulHVO(final DpdFinrulSearchVO dpdFinrulHVO) {
        this.dpdFinrulHVO = dpdFinrulHVO;
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
     * @return the maxRowVersion
     */
    public Long getMaxRowVersion() {
        return maxRowVersion;
    }

    /**
     * @param maxRowVersion
     *            the maxRowVersion to set
     */
    public void setMaxRowVersion(final Long maxRowVersion) {
        this.maxRowVersion = maxRowVersion;
    }

    /**
     * @return the dpdRuldefSearchVO
     */
    public DpdRuldefSearchVO getDpdRuldefSearchVO() {
        return dpdRuldefSearchVO;
    }

    /**
     * @param dpdRuldefSearchVO
     *            the dpdRuldefSearchVO to set
     */
    public void setDpdRuldefSearchVO(final DpdRuldefSearchVO dpdRuldefSearchVO) {
        this.dpdRuldefSearchVO = dpdRuldefSearchVO;
    }

    /**
     * @return the dpdRulMetricsSearchVO
     */
    public DpdRulMetricsSearchVO getDpdRulMetricsSearchVO() {
        return dpdRulMetricsSearchVO;
    }

    /**
     * @param dpdRulMetricsSearchVO
     *            the dpdRulMetricsSearchVO to set
     */
    public void setDpdRulMetricsSearchVO(final DpdRulMetricsSearchVO dpdRulMetricsSearchVO) {
        this.dpdRulMetricsSearchVO = dpdRulMetricsSearchVO;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("kmDpdRulhisSeqId", kmDpdRulhisSeqId)
                .append("dpdFinrulHVO", dpdFinrulHVO).append("versionNo", versionNo).append("active", active)
                .append("linkFinalRule", linkFinalRule).append("rowVersion", getRowVersion())
                .append("maxRowVersion", maxRowVersion).append("activatedBy", activatedBy)
                .append("activationDate", activationDate).append("previousVersionId", previousVersionId)
                .append("completed", completed).append("originalId", originalId).append("revHistory", revHistory)
                .append("lastUpdatedBy", lastUpdatedBy).append("lastUpdatedDate", lastUpdatedDate)
                .append("createdBy", createdBy).append("creationDate", creationDate).toString();
    }
}
