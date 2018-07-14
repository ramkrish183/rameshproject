/**
 * ============================================================
 * Classification: GE Confidential
 * File : RxHistoryServiceVO.java
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
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE Global Solutions Ltd.
 * @Version : 1.0
 * @Date Created : Oct 31, 2009
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RxHistoryServiceVO extends BaseVO {

    static final long serialVersionUID = 2865934L;
    private String strRxCaseId;
    private String strRxFeedbck;
    private String strRxSaveMiss;
    private String strRxStatus;
    private String strRxExecStatus;
    private String strRecommendationTitle;
    private Date dtRxCloseDate;
    private String strLatestRx;
    private String strRxDelvId;

    /**
     * @return the strRxDelvId
     */
    public String getStrRxDelvId() {
        return strRxDelvId;
    }

    /**
     * @param strRxDelvId
     *            the strRxDelvId to set
     */
    public void setStrRxDelvId(final String strRxDelvId) {
        this.strRxDelvId = strRxDelvId;
    }

    /**
     * @return the strLatestRx
     */
    public String getStrLatestRx() {
        return strLatestRx;
    }

    /**
     * @param strLatestRx
     *            the strLatestRx to set
     */
    public void setStrLatestRx(final String strLatestRx) {
        this.strLatestRx = strLatestRx;
    }

    public RxHistoryServiceVO() {
        super();
    }

    /**
     * @return the strRxCaseId
     */
    public String getStrRxCaseId() {
        return strRxCaseId;
    }

    /**
     * @param strRxCaseId
     *            the strRxCaseId to set
     */
    public void setStrRxCaseId(final String strRxCaseId) {
        this.strRxCaseId = strRxCaseId;
    }

    /**
     * @return the strRxFeedbck
     */
    public String getStrRxFeedbck() {
        return strRxFeedbck;
    }

    /**
     * @param strRxFeedbck
     *            the strRxFeedbck to set
     */
    public void setStrRxFeedbck(final String strRxFeedbck) {
        this.strRxFeedbck = strRxFeedbck;
    }

    /**
     * @return the strRxSaveMiss
     */
    public String getStrRxSaveMiss() {
        return strRxSaveMiss;
    }

    /**
     * @param strRxSaveMiss
     *            the strRxSaveMiss to set
     */
    public void setStrRxSaveMiss(final String strRxSaveMiss) {
        this.strRxSaveMiss = strRxSaveMiss;
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
     * @return the strRecommendationTitle
     */
    public String getStrRecommendationTitle() {
        return strRecommendationTitle;
    }

    /**
     * @param strRecommendationTitle
     *            the strRecommendationTitle to set
     */
    public void setStrRecommendationTitle(final String strRecommendationTitle) {
        this.strRecommendationTitle = strRecommendationTitle;
    }

    /**
     * @return the dtRxCloseDate
     */
    public Date getDtRxCloseDate() {
        return dtRxCloseDate;
    }

    /**
     * @param dtRxCloseDate
     *            the dtRxCloseDate to set
     */
    public void setDtRxCloseDate(final Date dtRxCloseDate) {
        this.dtRxCloseDate = dtRxCloseDate;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRxCaseId", strRxCaseId)
                .append("strRxFeedbck", strRxFeedbck).append("strRxSaveMiss", strRxSaveMiss)
                .append("strRxStatus", strRxStatus).append("strRxExecStatus", strRxExecStatus)
                .append("strRecommendationTitle", strRecommendationTitle).append("dtRxCloseDate", dtRxCloseDate)
                .append("strLatestRx", strLatestRx).toString();
    }

    /**
     * @return the strRxExecStatus
     */
    public String getStrRxExecStatus() {
        return strRxExecStatus;
    }

    /**
     * @param strRxExecStatus
     *            the strRxExecStatus to set
     */
    public void setStrRxExecStatus(final String strRxExecStatus) {
        this.strRxExecStatus = strRxExecStatus;
    }
}