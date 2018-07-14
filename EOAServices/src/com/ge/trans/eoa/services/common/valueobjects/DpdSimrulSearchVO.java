/**
 * ============================================================
 * Classification: GE Confidential
 * File : DpdSimrulSearchHVO.java
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
public class DpdSimrulSearchVO extends BaseVO {

    static final long serialVersionUID = 9876987L;
    private Long kmDpdSimrulSeqId;
    private String fault;
    private String subId;
    private Long rowVersion;
    private Long linkFinrul;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;

    /**
     * @return the kmDpdSimrulSeqId
     */
    public Long getKmDpdSimrulSeqId() {
        return kmDpdSimrulSeqId;
    }

    /**
     * @param kmDpdSimrulSeqId
     *            the kmDpdSimrulSeqId to set
     */
    public void setKmDpdSimrulSeqId(final Long kmDpdSimrulSeqId) {
        this.kmDpdSimrulSeqId = kmDpdSimrulSeqId;
    }

    /**
     * @return the fault
     */
    public String getFault() {
        return fault;
    }

    /**
     * @param fault
     *            the fault to set
     */
    public void setFault(final String fault) {
        this.fault = fault;
    }

    /**
     * @return the subId
     */
    public String getSubId() {
        return subId;
    }

    /**
     * @param subId
     *            the subId to set
     */
    public void setSubId(final String subId) {
        this.subId = subId;
    }

    /**
     * @return the linkFinrul
     */
    public Long getLinkFinrul() {
        return linkFinrul;
    }

    /**
     * @param linkFinrul
     *            the linkFinrul to set
     */
    public void setLinkFinrul(final Long linkFinrul) {
        this.linkFinrul = linkFinrul;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("kmDpdSimrulSeqId", kmDpdSimrulSeqId)
                .append("fault", fault).append("subId", subId).append("rowVersion", rowVersion)
                .append("linkFinrul", linkFinrul).append("lastUpdatedBy", lastUpdatedBy)
                .append("lastUpdatedDate", lastUpdatedDate).append("createdBy", createdBy)
                .append("creationDate", creationDate).toString();
    }
}
