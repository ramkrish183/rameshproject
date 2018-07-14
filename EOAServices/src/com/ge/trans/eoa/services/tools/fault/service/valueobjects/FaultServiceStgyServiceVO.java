/**
 * ============================================================
 * File : FaultServiceStgyServiceVO.java
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
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.fault.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 9, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class FaultServiceStgyServiceVO extends BaseVO {

    static final long serialVersionUID = 1642566326L;
    private Long lngFltCodesSeqId;
    private String strFaultOrgin = RMDCommonConstants.EMPTY_STRING;
    private String strFaultCode = RMDCommonConstants.EMPTY_STRING;
    private String strDescription = RMDCommonConstants.EMPTY_STRING;
    private String strfaultClassSelected = RMDCommonConstants.EMPTY_STRING;
    private String strToolUrg = RMDCommonConstants.EMPTY_STRING;
    private String strNotes;
    private String strLastModifiedBy = RMDCommonConstants.EMPTY_STRING;
    private String family = RMDCommonConstants.EMPTY_STRING;
    private String modelName = RMDCommonConstants.EMPTY_STRING;
	private boolean isExternalRuleAUthor;

    private List<ElementVO> alFaultClassification = new ArrayList<ElementVO>();
    private List<ElementVO> alFaultData = new ArrayList<ElementVO>();
    private String subIds;
    
    public String getSubIds() {
		return subIds;
	}

	public void setSubIds(String subIds) {
		this.subIds = subIds;
	}

	private Date dtLastModifiedDate;

    /**
     * @return the strFaultOrgin
     */
    public String getStrFaultOrgin() {
        return strFaultOrgin;
    }

    /**
     * @param strFaultOrgin
     *            the strFaultOrgin to set
     */
    public void setStrFaultOrgin(final String strFaultOrgin) {
        this.strFaultOrgin = strFaultOrgin;
    }

    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }

    /**
     * @param strFaultCode
     *            the strFaultCode to set
     */
    public void setStrFaultCode(final String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /**
     * @return the strDescription
     */
    public String getStrDescription() {
        return strDescription;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(final String family) {
        this.family = family;
    }

    /**
     * @param strDescription
     *            the strDescription to set
     */
    public void setStrDescription(final String strDescription) {
        this.strDescription = strDescription;
    }

    /**
     * @return the strfaultClassSelected
     */
    public String getStrfaultClassSelected() {
        return strfaultClassSelected;
    }

    /**
     * @param strfaultClassSelected
     *            the strfaultClassSelected to set
     */
    public void setStrfaultClassSelected(final String strfaultClassSelected) {
        this.strfaultClassSelected = strfaultClassSelected;
    }

    /**
     * @return the strLastModifiedBy
     */
    public String getStrLastModifiedBy() {
        return strLastModifiedBy;
    }

    /**
     * @param strLastModifiedBy
     *            the strLastModifiedBy to set
     */
    public void setStrLastModifiedBy(final String strLastModifiedBy) {
        this.strLastModifiedBy = strLastModifiedBy;
    }

    /**
     * @return the strNotes
     */
    public String getStrNotes() {
        return strNotes;
    }

    /**
     * @param strNotes
     *            the strNotes to set
     */
    public void setStrNotes(final String strNotes) {
        this.strNotes = strNotes;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFaultOrgin", strFaultOrgin)
                .append("strFaultCode", strFaultCode).append("strDescription", strDescription)
                .append("strfaultClassSelected", strfaultClassSelected).append("strNotes", strNotes)
                .append("alFaultClassification", alFaultClassification).append("alFaultData", alFaultData)
                .append("dtLastModifiedDate", dtLastModifiedDate).append("strLastModifiedBy", strLastModifiedBy)
                .append("lngFltCodesSeqId", lngFltCodesSeqId).toString();
    }

    /**
     * @return the dtLastModifiedDate
     */
    public Date getDtLastModifiedDate() {
        return dtLastModifiedDate;
    }

    /**
     * @param dtLastModifiedDate
     *            the dtLastModifiedDate to set
     */
    public void setDtLastModifiedDate(final Date dtLastModifiedDate) {
        this.dtLastModifiedDate = dtLastModifiedDate;
    }

    /**
     * @return the strToolUrg
     */
    public String getStrToolUrg() {
        return strToolUrg;
    }

    /**
     * @param strToolUrg
     *            the strToolUrg to set
     */
    public void setStrToolUrg(final String strToolUrg) {
        this.strToolUrg = strToolUrg;
    }

    public List<ElementVO> getAlFaultClassification() {
        return alFaultClassification;
    }

    public void setAlFaultClassification(final List<ElementVO> alFaultClassification) {
        this.alFaultClassification = alFaultClassification;
    }

    public List<ElementVO> getAlFaultData() {
        return alFaultData;
    }

    public void setAlFaultData(final List<ElementVO> alFaultData) {
        this.alFaultData = alFaultData;
    }

    public Long getLngFltCodesSeqId() {
        return lngFltCodesSeqId;
    }

    public void setLngFltCodesSeqId(final Long lngFltCodesSeqId) {
        this.lngFltCodesSeqId = lngFltCodesSeqId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

	public boolean isExternalRuleAUthor() {
		return isExternalRuleAUthor;
	}

	public void setExternalRuleAUthor(boolean isExternalRuleAUthor) {
		this.isExternalRuleAUthor = isExternalRuleAUthor;
	}
}
