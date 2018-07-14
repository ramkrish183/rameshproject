/**
 * ============================================================
 * Classification: GE Confidential
 * File : SelectCaseHomeHVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================*/
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class SelectCaseHomeVO extends BaseVO {

    static final long serialVersionUID = 1211566523L;
    private Long lngCmCaseSeqId;
    private Date dtCreationDate;
    private Date dtHistDate;
    private String strTitle;
    private String strCaseId;
    private String strAge;
    private String strcustomerName;
    private String strAssetHeader;
    private String strAssetNumber;
    private String strPriority;
    private String strStatus;
    private String strCreator;
    private String strOwner;
    private String strQueue;
    private String strReason;
    private String strSeverity;
    private Long longAssetNumber;
    private String strUrgency;
    private Date dtLastUpdatedDate;
    private String strGrpName;
    private List<RecomDelvServiceVO> arlRecomDelv;
    private String modelName;
    private String fleetName;
    private Float caseReasonSort;
    private Integer customerNameSort;
    private String caseRxStatus;
    private String caseCurrentRXObjid;
    private String strOwnerName;
    private String strCustomerId;
    private String condition;
    private String caseType;
    private Date closeDate;
    private String isAppend;
    private Long caseObjid;
    private String rxType;

    public String getRxType() {
		return rxType;
	}

	public void setRxType(String rxType) {
		this.rxType = rxType;
	}

	public Long getCaseObjid() {
        return caseObjid;
    }

    public void setCaseObjid(Long caseObjid) {
        this.caseObjid = caseObjid;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(String isAppend) {
        this.isAppend = isAppend;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCaseRxStatus() {
        return caseRxStatus;
    }

    public void setCaseRxStatus(String caseRxStatus) {
        this.caseRxStatus = caseRxStatus;
    }

    public String getCaseCurrentRXObjid() {
        return caseCurrentRXObjid;
    }

    public void setCaseCurrentRXObjid(String caseCurrentRXObjid) {
        this.caseCurrentRXObjid = caseCurrentRXObjid;
    }

    public String getStrOwnerName() {
        return strOwnerName;
    }

    public void setStrOwnerName(String strOwnerName) {
        this.strOwnerName = strOwnerName;
    }

    public String getStrCustomerId() {
        return strCustomerId;
    }

    public void setStrCustomerId(String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }

    public Float getCaseReasonSort() {
        return caseReasonSort;
    }

    public void setCaseReasonSort(Float caseReasonSort) {
        this.caseReasonSort = caseReasonSort;
    }

    public Integer getCustomerNameSort() {
        return customerNameSort;
    }

    public void setCustomerNameSort(Integer customerNameSort) {
        this.customerNameSort = customerNameSort;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(String fleetName) {
        this.fleetName = fleetName;
    }

    /* added for Sprint17_EOAAssetCases start */
    private String strCaseType;

    public String getStrCaseType() {
        return strCaseType;
    }

    public void setStrCaseType(final String strCaseType) {
        this.strCaseType = strCaseType;
    }

    /* added for Sprint17_EOAAssetCases end */
    /**
     * @return the longAssetNumber
     */
    public Long getLongAssetNumber() {
        return longAssetNumber;
    }

    /**
     * @param longAssetNumber
     *            the longAssetNumber to set
     */
    public void setLongAssetNumber(final Long longAssetNumber) {
        this.longAssetNumber = longAssetNumber;
    }

    /**
     * @return the lngCmCaseSeqId
     */
    public Long getLngCmCaseSeqId() {
        return lngCmCaseSeqId;
    }

    /**
     * @param lngCmCaseSeqId
     *            the lngCmCaseSeqId to set
     */
    public void setLngCmCaseSeqId(final Long lngCmCaseSeqId) {
        this.lngCmCaseSeqId = lngCmCaseSeqId;
    }

    /**
     * @return the strTitle
     */
    public String getStrTitle() {
        return strTitle;
    }

    /**
     * @param strTitle
     *            the strTitle to set
     */
    public void setStrTitle(final String strTitle) {
        this.strTitle = strTitle;
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
     * @return the dtCreationDate
     */
    public Date getDtCreationDate() {
        return dtCreationDate;
    }

    /**
     * @param dtCreationDate
     *            the dtCreationDate to set
     */
    public void setDtCreationDate(final Date dtCreationDate) {
        this.dtCreationDate = dtCreationDate;
    }

    /**
     * @return the strAge
     */
    public String getStrAge() {
        return strAge;
    }

    /**
     * @param strAge
     *            the strAge to set
     */
    public void setStrAge(final String strAge) {
        this.strAge = strAge;
    }

    /**
     * @return the strcustomerName
     */
    public String getStrcustomerName() {
        return strcustomerName;
    }

    /**
     * @param strcustomerName
     *            the strcustomerName to set
     */
    public void setStrcustomerName(final String strcustomerName) {
        this.strcustomerName = strcustomerName;
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
     * @return the strPriority
     */
    public String getStrPriority() {
        return strPriority;
    }

    /**
     * @param strPriority
     *            the strPriority to set
     */
    public void setStrPriority(final String strPriority) {
        this.strPriority = strPriority;
    }

    /**
     * @return the strStatus
     */
    public String getStrStatus() {
        return strStatus;
    }

    /**
     * @param strStatus
     *            the strStatus to set
     */
    public void setStrStatus(final String strStatus) {
        this.strStatus = strStatus;
    }

    /**
     * @return the strCreator
     */
    public String getStrCreator() {
        return strCreator;
    }

    /**
     * @param strCreator
     *            the strCreator to set
     */
    public void setStrCreator(final String strCreator) {
        this.strCreator = strCreator;
    }

    /**
     * @return the strOwner
     */
    public String getStrOwner() {
        return strOwner;
    }

    /**
     * @param strOwner
     *            the strOwner to set
     */
    public void setStrOwner(final String strOwner) {
        this.strOwner = strOwner;
    }

    /**
     * @return the strQueue
     */
    public String getStrQueue() {
        return strQueue;
    }

    /**
     * @param strQueue
     *            the strQueue to set
     */
    public void setStrQueue(final String strQueue) {
        this.strQueue = strQueue;
    }

    /**
     * @return the strReason
     */
    public String getStrReason() {
        return strReason;
    }

    /**
     * @param strReason
     *            the strReason to set
     */
    public void setStrReason(final String strReason) {
        this.strReason = strReason;
    }

    /**
     * @return the dtHistDate
     */
    public Date getDtHistDate() {
        return dtHistDate;
    }

    /**
     * @param dtHistDate
     *            the dtHistDate to set
     */
    public void setDtHistDate(final Date dtHistDate) {
        this.dtHistDate = dtHistDate;
    }

    public String getStrSeverity() {
        return strSeverity;
    }

    public void setStrSeverity(final String strSeverity) {
        this.strSeverity = strSeverity;
    }

    public String getStrUrgency() {
        return strUrgency;
    }

    public void setStrUrgency(String strUrgency) {
        this.strUrgency = strUrgency;
    }

    public Date getDtLastUpdatedDate() {
        return dtLastUpdatedDate;
    }

    public void setDtLastUpdatedDate(final Date dtLastUpdatedDate) {
        this.dtLastUpdatedDate = dtLastUpdatedDate;
    }

    public String getStrGrpName() {
        return strGrpName;
    }

    public void setStrGrpName(final String strGrpName) {
        this.strGrpName = strGrpName;
    }

    public List<RecomDelvServiceVO> getArlRecomDelv() {
        return arlRecomDelv;
    }

    public void setArlRecomDelv(final List<RecomDelvServiceVO> arlRecomDelv) {
        this.arlRecomDelv = arlRecomDelv;
    }

}
