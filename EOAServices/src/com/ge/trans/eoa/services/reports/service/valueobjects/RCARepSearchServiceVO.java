/**
 * ============================================================
 * File : RCARepSearchServiceVO.java
 * Description : This service VO holds the search criteria attribute
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
 * @Description : This service VO holds the search criteria attribute
 * @History :
 ******************************************************************************/
public class RCARepSearchServiceVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    private String strAssetNumber;
    private String strAssetHeader;
    private String strSearchWhatFeedBack;
    private String strSearchWhatTitle;
    private String strSearchCaseFeedBack;
    private String strSearchCaseTitle;
    private Date selectedFromDate;
    private Date selectedToDate;
    private String strRXStatus;

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
     * @return the strAssetHeader
     */
    public String getStrAssetHeader() {
        return strAssetHeader;
    }

    /**
     * @param strAssetHeader
     *            the strAssetHeader to set
     */
    public void setStrAssetHeader(final String strAssetHeader) {
        this.strAssetHeader = strAssetHeader;
    }

    /**
     * @return the strSearchWhatFeedBack
     */
    public String getStrSearchWhatFeedBack() {
        return strSearchWhatFeedBack;
    }

    /**
     * @param strSearchWhatFeedBack
     *            the strSearchWhatFeedBack to set
     */
    public void setStrSearchWhatFeedBack(final String strSearchWhatFeedBack) {
        this.strSearchWhatFeedBack = strSearchWhatFeedBack;
    }

    /**
     * @return the strSearchWhatTitle
     */
    public String getStrSearchWhatTitle() {
        return strSearchWhatTitle;
    }

    /**
     * @param strSearchWhatTitle
     *            the strSearchWhatTitle to set
     */
    public void setStrSearchWhatTitle(final String strSearchWhatTitle) {
        this.strSearchWhatTitle = strSearchWhatTitle;
    }

    /**
     * @return the strSearchCaseFeedBack
     */
    public String getStrSearchCaseFeedBack() {
        return strSearchCaseFeedBack;
    }

    public void setStrSearchCaseFeedBack(final String strSearchCaseFeedBack) {
        this.strSearchCaseFeedBack = strSearchCaseFeedBack;
    }

    public String getStrSearchCaseTitle() {
        return strSearchCaseTitle;
    }

    public void setStrSearchCaseTitle(final String strSearchCaseTitle) {
        this.strSearchCaseTitle = strSearchCaseTitle;
    }

    /**
     * @return the selectedFromDate
     */
    public Date getSelectedFromDate() {
        return selectedFromDate;
    }

    /**
     * @param selectedFromDate
     *            the selectedFromDate to set
     */
    public void setSelectedFromDate(final Date selectedFromDate) {
        this.selectedFromDate = selectedFromDate;
    }

    /**
     * @return the selectedToDate
     */
    public Date getSelectedToDate() {
        return selectedToDate;
    }

    /**
     * @param selectedToDate
     *            the selectedToDate to set
     */
    public void setSelectedToDate(final Date selectedToDate) {
        this.selectedToDate = selectedToDate;
    }

    /**
     * @return the strRXStatus
     */
    public String getStrRXStatus() {
        return strRXStatus;
    }

    /**
     * @param strRXStatus
     *            the strRXStatus to set
     */
    public void setStrRXStatus(final String strRXStatus) {
        this.strRXStatus = strRXStatus;
    }
}
