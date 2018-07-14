/**
 * ============================================================
 * Classification: GE Confidential
 * File : RecomDelvServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.valueobjects
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
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 3, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RecomDelvServiceVO extends BaseVO {

    private static final long serialVersionUID = 162487539L;
    private String strRxObjid;
    private String strRxTitle;
    private String strUrgRepair;
    private String strEstmRepTime;
    private String strRxRevNo;
    private String strRxStatus;
    private Date dtRxDelvDate;
    private String strRxCaseId;
    private String locomotiveImpact;
    private Date dtRxClosedDate;
    private String solutionNotes;
    private Date reissuedRxDelvDate;

    /**
     * @return the solutionNotes
     */
    public String getSolutionNotes() {
        return solutionNotes;
    }

    /**
     * @param solutionNotes
     *            the solutionNotes to set
     */
    public void setSolutionNotes(final String solutionNotes) {
        this.solutionNotes = solutionNotes;
    }

    /**
     * @return the strRxObjid
     */
    public String getStrRxTitle() {
        return strRxTitle;
    }

    /**
     * @param strRxTitle
     *            the strRxTitle to set
     */
    public void setStrRxTitle(final String strRxTitle) {
        this.strRxTitle = strRxTitle;
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
     * @return the strEstmRepTime
     */
    public String getStrEstmRepTime() {
        return strEstmRepTime;
    }

    /**
     * @param strEstmRepTime
     *            the strEstmRepTime to set
     */
    public void setStrEstmRepTime(final String strEstmRepTime) {
        this.strEstmRepTime = strEstmRepTime;
    }

    /**
     * @return the strRxRevNo
     */
    public String getStrRxRevNo() {
        return strRxRevNo;
    }

    /**
     * @param strRxRevNo
     *            the strRxRevNo to set
     */
    public void setStrRxRevNo(final String strRxRevNo) {
        this.strRxRevNo = strRxRevNo;
    }

    /**
     * @return the strRxStatus
     */
    public String getStrRxStatus() {
        return strRxStatus;
    }

    /**
     * @param strRxStatus
     *            the strRxStatus to set
     */
    public void setStrRxStatus(final String strRxStatus) {
        this.strRxStatus = strRxStatus;
    }

    /**
     * @return the strRxObjid
     */
    public String getStrRxObjid() {
        return strRxObjid;
    }

    /**
     * @param strRxObjid
     *            the strRxObjid to set
     */
    public void setStrRxObjid(final String strRxObjid) {
        this.strRxObjid = strRxObjid;
    }

    /**
     * @return the dtRxDelvDate
     */
    public Date getDtRxDelvDate() {
        return dtRxDelvDate;
    }

    /**
     * @param dtRxDelvDate
     *            the dtRxDelvDate to set
     */
    public void setDtRxDelvDate(final Date dtRxDelvDate) {
        this.dtRxDelvDate = dtRxDelvDate;
    }

    public String getStrRxCaseId() {
        return strRxCaseId;
    }

    public void setStrRxCaseId(final String strRxCaseId) {
        this.strRxCaseId = strRxCaseId;
    }

    public String getLocomotiveImpact() {
        return locomotiveImpact;
    }

    public void setLocomotiveImpact(final String locomotiveImpact) {
        this.locomotiveImpact = locomotiveImpact;
    }

    public Date getDtRxClosedDate() {
        return dtRxClosedDate;
    }

    public void setDtRxClosedDate(final Date dtRxClosedDate) {
        this.dtRxClosedDate = dtRxClosedDate;
    }

   

	public Date getReissuedRxDelvDate() {
		return reissuedRxDelvDate;
	}

	public void setReissuedRxDelvDate(Date reissuedRxDelvDate) {
		this.reissuedRxDelvDate = reissuedRxDelvDate;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxObjid", strRxObjid)
                .append("strRxTitle", strRxTitle).append("strUrgRepair", strUrgRepair)
                .append("strEstmRepTime", strEstmRepTime).append("strRxRevNo", strRxRevNo)
                .append("strRxStatus", strRxStatus).append("dtRxDelvDate", dtRxDelvDate).toString();
    }
}
