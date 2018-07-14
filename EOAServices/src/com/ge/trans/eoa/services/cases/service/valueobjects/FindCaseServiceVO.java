/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseVO.java
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
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FindCaseServiceVO extends BaseVO {
    static final long serialVersionUID = 32544474L;
    private String strCaseLike;
    private String strStartDate;
    private String strEndDate;
    private String strAssetNumber;
    private String strAssetHeader;
    private String strQueue;
    private String strOrderBy;
    private String strSearchWhat;
    private String strSearchHow;
    private String strRxTitle;
    private String strRxStatus;
    private String strCaseId;
    private String strRxCaseId;
    private Date selectedFromDate;
    private Date selectedToDate;
    private String strPage;
    private String strUserId;
    private String strCaseStatus;
    private String strCaseTitle;
    private String strRxFdbk;
    private String strRxId;
    private String strRuleDefId;
    private String strCustomerId;
    private String strAssetGrpName;
    private String[] urgency;
    private String estRepairTime;
    private String[] rxIds;
    private String[] caseType;
    private String[] modelId;
    private String locoId;
    private String[] fleetId;
    private List<String> products;
    private int closedRxLookBackDays;
    private int lookBackDays;
    private String searchTypeFlag;
    private String caseID;
    private String caseTypes;
    private String noOfDays;
    private String strCaseType;
    private String appendFlag;
    private String screenName;

    public String getLocoId() {
        return locoId;
    }

    public void setLocoId(String locoId) {
        this.locoId = locoId;
    }

    public String getAppendFlag() {
        return appendFlag;
    }

    public void setAppendFlag(String appendFlag) {
        this.appendFlag = appendFlag;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getStrCaseType() {
        return strCaseType;
    }

    public void setStrCaseType(String strCaseType) {
        this.strCaseType = strCaseType;
    }

    /**
     * @return the caseType.
     */
    public String getCaseTypes() {
        return caseTypes;
    }

    /**
     * @param caseTypes
     *            the caseTypes to set
     */

    public void setCaseTypes(String caseTypes) {
        this.caseTypes = caseTypes;
    }

    /**
     * @return the caseID.
     */

    public String getCaseID() {
        return caseID;
    }

    /**
     * @param caseID
     *            the caseID to set
     */
    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getStrRxCaseId() {
        return strRxCaseId;
    }

    public void setStrRxCaseId(String strRxCaseId) {
        this.strRxCaseId = strRxCaseId;
    }

    /**
     * @return the closedRxLookBackDays
     */
    public int getClosedRxLookBackDays() {
        return closedRxLookBackDays;
    }

    /**
     * @param closedRxLookBackDays
     *            the closedRxLookBackDays to set
     */
    public void setClosedRxLookBackDays(int closedRxLookBackDays) {
        this.closedRxLookBackDays = closedRxLookBackDays;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String[] getModelId() {
        return modelId;
    }

    public void setModelId(String[] modelId) {
        this.modelId = modelId;
    }

    public String[] getFleetId() {
        return fleetId;
    }

    public void setFleetId(String[] fleetId) {
        this.fleetId = fleetId;
    }

    public String[] getCaseType() {
        return caseType;
    }

    public void setCaseType(String[] caseType) {
        this.caseType = caseType;
    }

    public String[] getRxIds() {
        return rxIds;
    }

    public void setRxIds(String[] rxIds) {
        this.rxIds = rxIds;
    }

    public String[] getUrgency() {
        return urgency;
    }

    public void setUrgency(String[] urgency) {
        this.urgency = urgency;
    }

    public String getEstRepairTime() {
        return estRepairTime;
    }

    public void setEstRepairTime(String estRepairTime) {
        this.estRepairTime = estRepairTime;
    }

    /**
     * @return the strCaseLike
     */
    public String getStrCaseLike() {
        return strCaseLike;
    }

    /**
     * @param strCaseLike
     *            the strCaseLike to set
     */
    public void setStrCaseLike(final String strCaseLike) {
        this.strCaseLike = strCaseLike;
    }

    /**
     * @return the strStartDate
     */
    public String getStrStartDate() {
        return strStartDate;
    }

    /**
     * @param strStartDate
     *            the strStartDate to set
     */
    public void setStrStartDate(final String strStartDate) {
        this.strStartDate = strStartDate;
    }

    /**
     * @return the strEndDate
     */
    public String getStrEndDate() {
        return strEndDate;
    }

    /**
     * @param strEndDate
     *            the strEndDate to set
     */
    public void setStrEndDate(final String strEndDate) {
        this.strEndDate = strEndDate;
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
     * @return the strOrderBy
     */
    public String getStrOrderBy() {
        return strOrderBy;
    }

    /**
     * @param strOrderBy
     *            the strOrderBy to set
     */
    public void setStrOrderBy(final String strOrderBy) {
        this.strOrderBy = strOrderBy;
    }

    /**
     * @return the strSearchWhat
     */
    public String getStrSearchWhat() {
        return strSearchWhat;
    }

    /**
     * @param strSearchWhat
     *            the strSearchWhat to set
     */
    public void setStrSearchWhat(final String strSearchWhat) {
        this.strSearchWhat = strSearchWhat;
    }

    /**
     * @return the strSearchHow
     */
    public String getStrSearchHow() {
        return strSearchHow;
    }

    /**
     * @param strSearchHow
     *            the strSearchHow to set
     */
    public void setStrSearchHow(final String strSearchHow) {
        this.strSearchHow = strSearchHow;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject())
                .append("strCaseLike", strCaseLike)
                .append("strStartDate", strStartDate)
                .append("strEndDate", strEndDate)
                .append("strAssetNumber", strAssetNumber)
                .append("strAssetHeader", strAssetHeader)
                .append("strQueue", strQueue).append("strOrderBy", strOrderBy)
                .append("strSearchWhat", strSearchWhat)
                .append("strSearchHow", strSearchHow)
                .append("selectedFromDate", selectedFromDate)
                .append("selectedToDate", selectedToDate)
                .append("strCaseId", strCaseId).toString();
    }

    public String getStrRxTitle() {
        return strRxTitle;
    }

    public void setStrRxTitle(final String strRxTitle) {
        this.strRxTitle = strRxTitle;
    }

    public String getStrRxStatus() {
        return strRxStatus;
    }

    public void setStrRxStatus(final String strRxStatus) {
        this.strRxStatus = strRxStatus;
    }

    public String getStrCaseId() {
        return strCaseId;
    }

    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrPage() {
        return strPage;
    }

    public void setStrPage(final String strPage) {
        this.strPage = strPage;
    }

    public String getStrUserId() {
        return strUserId;
    }

    public void setStrUserId(final String strUserId) {
        this.strUserId = strUserId;
    }

    public String getStrCaseStatus() {
        return strCaseStatus;
    }

    public void setStrCaseStatus(final String strCaseStatus) {
        this.strCaseStatus = strCaseStatus;
    }

    public String getStrCaseTitle() {
        return strCaseTitle;
    }

    public void setStrCaseTitle(final String strCaseTitle) {
        this.strCaseTitle = strCaseTitle;
    }

    public String getStrRxFdbk() {
        return strRxFdbk;
    }

    public void setStrRxFdbk(final String strRxFdbk) {
        this.strRxFdbk = strRxFdbk;
    }

    public String getStrRxId() {
        return strRxId;
    }

    public void setStrRxId(final String strRxId) {
        this.strRxId = strRxId;
    }

    public String getStrRuleDefId() {
        return strRuleDefId;
    }

    public void setStrRuleDefId(final String strRuleDefId) {
        this.strRuleDefId = strRuleDefId;
    }

    public String getStrCustomerId() {
        return strCustomerId;
    }

    public void setStrCustomerId(final String strCustomerId) {
        this.strCustomerId = strCustomerId;
    }

    public String getStrAssetGrpName() {
        return strAssetGrpName;
    }

    public void setStrAssetGrpName(final String strAssetGrpName) {
        this.strAssetGrpName = strAssetGrpName;
    }

    public int getLookBackDays() {
        return lookBackDays;
    }

    public void setLookBackDays(int lookBackDays) {
        this.lookBackDays = lookBackDays;
    }

    public String getSearchTypeFlag() {
        return searchTypeFlag;
    }

    public void setSearchTypeFlag(String searchTypeFlag) {
        this.searchTypeFlag = searchTypeFlag;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    
    

}
