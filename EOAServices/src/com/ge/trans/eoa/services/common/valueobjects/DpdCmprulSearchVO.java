/**
 * ============================================================
 * Classification: GE Confidential
 * File : DpdCmprulSearchHVO.java
 * Description :
 *
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
package com.ge.trans.eoa.services.common.valueobjects;

import java.util.Date;

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
public class DpdCmprulSearchVO extends BaseVO {
    static final long serialVersionUID = 222333355L;
    private Long getKmDpdCmprulSeqId;
    private Long timeWindow;
    private Long frequency;
    private String complexFunction;
    private Integer rowVersion;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private String createdBy;
    private Date creationDate;

    public Long getGetKmDpdCmprulSeqId() {
        return this.getKmDpdCmprulSeqId;
    }

    public void setGetKmDpdCmprulSeqId(final Long getKmDpdCmprulSeqId) {
        this.getKmDpdCmprulSeqId = getKmDpdCmprulSeqId;
    }

    public Long getTimeWindow() {
        return this.timeWindow;
    }

    public void setTimeWindow(final Long timeWindow) {
        this.timeWindow = timeWindow;
    }

    public Long getFrequency() {
        return this.frequency;
    }

    public void setFrequency(final Long frequency) {
        this.frequency = frequency;
    }

    public String getComplexFunction() {
        return this.complexFunction;
    }

    public void setComplexFunction(final String complexFunction) {
        this.complexFunction = complexFunction;
    }

    @Override
    public Integer getRowVersion() {
        return this.rowVersion;
    }

    @Override
    public void setRowVersion(final Integer rowVersion) {
        this.rowVersion = rowVersion;
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
        return new ToStringBuilder(this, getToStringStyleObject()).append("getKmDpdCmprulSeqId", getKmDpdCmprulSeqId)
                .append("timeWindow", timeWindow).append("frequency", frequency)
                .append("complexFunction", complexFunction).append("rowVersion", rowVersion)
                .append("lastUpdatedBy", lastUpdatedBy).append("lastUpdatedDate", lastUpdatedDate)
                .append("createdBy", createdBy).append("creationDate", creationDate).toString();
    }
}
