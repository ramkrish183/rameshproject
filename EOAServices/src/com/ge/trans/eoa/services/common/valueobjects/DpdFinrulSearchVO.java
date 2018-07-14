/**
 * ============================================================
 * Classification: GE Confidential
 * File : DpdFinrulSearchHVO.java
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
public class DpdFinrulSearchVO extends BaseVO {

    /**
     *
     */
    static final long serialVersionUID = 5365255361L;
    private Long kmDpdFinrulSeqId;
    private DpdCmprulSearchVO dpdCmprulHVO;
    private DpdSimrulSearchVO dpdSimrulHVO;
    private String family;
    private String subsystem;
    private Long lookback;
    private String ruleDescription;
    private String ruleTitle;
    private String lastUpdatedBy;
	private String lastUpdatedDate;
    private String createdBy;
	private String creationDate;
    private Long rowVersion;

    /**
     * @return the kmDpdFinrulSeqId
     */
    public Long getKmDpdFinrulSeqId() {
        return kmDpdFinrulSeqId;
    }

    /**
     * @param kmDpdFinrulSeqId
     *            the kmDpdFinrulSeqId to set
     */
    public void setKmDpdFinrulSeqId(final Long kmDpdFinrulSeqId) {
        this.kmDpdFinrulSeqId = kmDpdFinrulSeqId;
    }

    /**
     * @return the dpdSimrulHVO
     */
    public DpdSimrulSearchVO getDpdSimrulHVO() {
        return dpdSimrulHVO;
    }

    /**
     * @param dpdSimrulHVO
     *            the dpdSimrulHVO to set
     */
    public void setDpdSimrulHVO(final DpdSimrulSearchVO dpdSimrulHVO) {
        this.dpdSimrulHVO = dpdSimrulHVO;
    }

    /**
     * @return the family
     */
    public String getFamily() {
        return family;
    }

    /**
     * @param family
     *            the family to set
     */
    public void setFamily(final String family) {
        this.family = family;
    }

    /**
     * @return the subsystem
     */
    public String getSubsystem() {
        return subsystem;
    }

    /**
     * @param subsystem
     *            the subsystem to set
     */
    public void setSubsystem(final String subsystem) {
        this.subsystem = subsystem;
    }

    /**
     * @return the lookback
     */
    public Long getLookback() {
        return lookback;
    }

    /**
     * @param lookback
     *            the lookback to set
     */
    public void setLookback(final Long lookback) {
        this.lookback = lookback;
    }

    /**
     * @return the ruleDescription
     */
    public String getRuleDescription() {
        return ruleDescription;
    }

    /**
     * @param ruleDescription
     *            the ruleDescription to set
     */
    public void setRuleDescription(final String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    /**
     * @return the ruleTitle
     */
    public String getRuleTitle() {
        return ruleTitle;
    }

    /**
     * @param ruleTitle
     *            the ruleTitle to set
     */
    public void setRuleTitle(final String ruleTitle) {
        this.ruleTitle = ruleTitle;
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

	

	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return the dpdCmprulHVO
     */
    public DpdCmprulSearchVO getDpdCmprulHVO() {
        return dpdCmprulHVO;
    }

    /**
     * @param dpdCmprulHVO
     *            the dpdCmprulHVO to set
     */
    public void setDpdCmprulHVO(final DpdCmprulSearchVO dpdCmprulHVO) {
        this.dpdCmprulHVO = dpdCmprulHVO;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("kmDpdFinrulSeqId", kmDpdFinrulSeqId)
                .append("dpdCmprulHVO", dpdCmprulHVO).append("dpdSimrulHVO", dpdSimrulHVO).append("family", family)
                .append("subsystem", subsystem).append("lookback", lookback).append("ruleDescription", ruleDescription)
                .append("ruleTitle", ruleTitle).append("lastUpdatedBy", lastUpdatedBy)
                .append("lastUpdatedDate", lastUpdatedDate).append("createdBy", createdBy)
                .append("creationDate", creationDate).append("rowVersion", rowVersion).toString();
    }

}
