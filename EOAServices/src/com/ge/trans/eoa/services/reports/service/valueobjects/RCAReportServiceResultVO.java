/**
 * ============================================================
 * File : RCAReportServiceResultVO.java
 * Description : This service VO holds all the attribute of root cause analysis report
 * Package : com.ge.trans.rmd.services.reports.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.reports.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Mar 11, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This service VO holds all the attribute of root cause analysis
 *              report
 * @History :
 ******************************************************************************/
public class RCAReportServiceResultVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long lngAssetNumber;
    private String strAssetNumber;
    private String strCaseId;
    private String strRxCaseId;
    private String strRxTitle;
    private Date dtDeliverDate;
    private Date dtRxCloserDate;
    private String strFeedBackInfo;
    private String strExecutedBy;
    private String strRxFeedBack;
    private String strRxScoring;

    /**
     * @return the lngAssetNumber
     */
    public Long getLngAssetNumber() {
        return lngAssetNumber;
    }

    /**
     * @param lngAssetNumber
     *            the lngAssetNumber to set
     */
    public void setLngAssetNumber(final Long lngAssetNumber) {
        this.lngAssetNumber = lngAssetNumber;
    }

    /**
     * @return the strAssetNumber
     */
    public String getStrAssetNumber() {
        return strAssetNumber;
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public void setStrAssetNumber(final String strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    /**
     * @return the strCaseId
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @param strCaseId
     *            the strCaseId to set
     */
    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
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
     * @return the strRxTitle
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
     * @return the dtDeliverDate
     */
    public Date getDtDeliverDate() {
        return dtDeliverDate;
    }

    /**
     * @param dtDeliverDate
     *            the dtDeliverDate to set
     */
    public void setDtDeliverDate(final Date dtDeliverDate) {
        this.dtDeliverDate = dtDeliverDate;
    }

    /**
     * @return the dtRxCloserDate
     */
    public Date getDtRxCloserDate() {
        return dtRxCloserDate;
    }

    /**
     * @param dtRxCloserDate
     *            the dtRxCloserDate to set
     */
    public void setDtRxCloserDate(final Date dtRxCloserDate) {
        this.dtRxCloserDate = dtRxCloserDate;
    }

    /**
     * @return the strFeedBackInfo
     */
    public String getStrFeedBackInfo() {
        return strFeedBackInfo;
    }

    /**
     * @param strFeedBackInfo
     *            the strFeedBackInfo to set
     */
    public void setStrFeedBackInfo(final String strFeedBackInfo) {
        this.strFeedBackInfo = strFeedBackInfo;
    }

    /**
     * @return the strExecutedBy
     */
    public String getStrExecutedBy() {
        return strExecutedBy;
    }

    /**
     * @param strExecutedBy
     *            the strExecutedBy to set
     */
    public void setStrExecutedBy(final String strExecutedBy) {
        this.strExecutedBy = strExecutedBy;
    }

    /**
     * @return the strRxFeedBack
     */
    public String getStrRxFeedBack() {
        return strRxFeedBack;
    }

    /**
     * @param strRxFeedBack
     *            the strRxFeedBack to set
     */
    public void setStrRxFeedBack(final String strRxFeedBack) {
        this.strRxFeedBack = strRxFeedBack;
    }

    /**
     * @return the strRxScoring
     */
    public String getStrRxScoring() {
        return strRxScoring;
    }

    /**
     * @param strRxScoring
     *            the strRxScoring to set
     */
    public void setStrRxScoring(final String strRxScoring) {
        this.strRxScoring = strRxScoring;
    }
}
