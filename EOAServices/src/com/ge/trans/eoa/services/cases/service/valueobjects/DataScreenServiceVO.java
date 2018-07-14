package com.ge.trans.eoa.services.cases.service.valueobjects;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class DataScreenServiceVO extends BaseVO {

    static final long serialVersionUID = 546875134L;
    private String strCaseID = null;
    private String strNoOfDays = null;
    private String strRadioSelection = null;
    private String strExport = null;
    private String strRoadNo = null;
    private String strDaysRadio = null;
    private String strCriticalRadio = null;
    private String strHC = null;
    private String strEGU = null;
    private String strAssetNo = null;
    private boolean blnHC = false;
    private String strRuleDefId;
    private String strSortOption = RMDCommonConstants.EMPTY_STRING;
    private String strCaseSelection = RMDCommonConstants.EMPTY_STRING;
    private String strSortSelection = RMDCommonConstants.EMPTY_STRING;
    private String strDaySelection = RMDCommonConstants.EMPTY_STRING;
    private String strJDPADRadio = RMDCommonConstants.EMPTY_STRING;
    private String fromDate;
    private String toDate;
    private int intStartPos = 0;
    private int intNoOfRecs = 0;
    private int intTotalCount = 0;
    // ADDING FOR MULTIPLE CUSTOMERS:Rajesh:START
    private String customerId;
    private String assetGrpName;
    // ADDING FOR MULTIPLE CUSTOMERS:Rajesh:END

    // Added for timebased filter for datascreen
    private String strCaseFrom = null;
    private String dataSets = null;
    private boolean isLimitedParam = false;

    public boolean isLimitedParam() {
        return isLimitedParam;
    }

    public void setLimitedParam(boolean isLimitedParam) {
        this.isLimitedParam = isLimitedParam;
    }

    public String getDataSets() {
        return dataSets;
    }

    public void setDataSets(String dataSets) {
        this.dataSets = dataSets;
    }

    public String getStrCaseFrom() {
        return strCaseFrom;
    }

    public void setStrCaseFrom(String strCaseFrom) {
        this.strCaseFrom = strCaseFrom;
    }

    public String getStrCaseID() {
        return strCaseID;
    }

    public void setStrCaseID(final String strCaseID) {
        this.strCaseID = strCaseID;
    }

    public String getStrSortOption() {
        return strSortOption;
    }

    public void setStrSortOption(final String strSortOption) {
        this.strSortOption = strSortOption;
    }

    public String getStrNoOfDays() {
        return strNoOfDays;
    }

    public void setStrNoOfDays(final String strNoOfDays) {
        this.strNoOfDays = strNoOfDays;
    }

    public String getStrCaseSelection() {
        return strCaseSelection;
    }

    public void setStrCaseSelection(final String strCaseSelection) {
        this.strCaseSelection = strCaseSelection;
    }

    public String getStrRadioSelection() {
        return strRadioSelection;
    }

    public void setStrRadioSelection(String strRadioSelection) {
        this.strRadioSelection = strRadioSelection;
    }

    public String getStrExport() {
        return strExport;
    }

    public void setStrExport(final String strExport) {
        this.strExport = strExport;
    }

    public String getStrRoadNo() {
        return strRoadNo;
    }

    public void setStrRoadNo(String strRoadNo) {
        this.strRoadNo = strRoadNo;
    }

    public String getStrDaysRadio() {
        return strDaysRadio;
    }

    public void setStrDaysRadio(final String strDaysRadio) {
        this.strDaysRadio = strDaysRadio;
    }

    public String getStrJDPADRadio() {
        return strJDPADRadio;
    }

    public void setStrJDPADRadio(final String strJDPADRadio) {
        this.strJDPADRadio = strJDPADRadio;
    }

    public String getStrCriticalRadio() {
        return strCriticalRadio;
    }

    public void setStrCriticalRadio(final String strCriticalRadio) {
        this.strCriticalRadio = strCriticalRadio;
    }

    public String getStrHC() {
        return strHC;
    }

    public void setStrHC(final String strHC) {
        this.strHC = strHC;
    }

    public String getStrEGU() {
        return strEGU;
    }

    public void setStrEGU(final String strEGU) {
        this.strEGU = strEGU;
    }

    public String getStrAssetNo() {
        return strAssetNo;
    }

    public void setStrAssetNo(final String strAssetNo) {
        this.strAssetNo = strAssetNo;
    }

    /**
     * @return the blnHC
     */
    public boolean isBlnHC() {
        return blnHC;
    }

    /**
     * @param blnHC
     *            the blnHC to set
     */
    public void setBlnHC(final boolean blnHC) {
        this.blnHC = blnHC;
    }

    /**
     * @return the strRuleDefId
     */
    public String getStrRuleDefId() {
        return strRuleDefId;
    }

    /**
     * @param strRuleDefId
     *            the strRuleDefId to set
     */
    public void setStrRuleDefId(final String strRuleDefId) {
        this.strRuleDefId = strRuleDefId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strCaseID", strCaseID)
                .append("strNoOfDays", strNoOfDays).append("strRadioSelection", strRadioSelection)
                .append("strExport", strExport).append("strRoadNo", strRoadNo).append("strDaysRadio", strDaysRadio)
                .append("strCriticalRadio", strCriticalRadio).append("strHC", strHC).append("strEGU", strEGU)
                .append("strAssetNo", strAssetNo).append("blnHC", blnHC).append("strRuleDefId", strRuleDefId)
                .append("strSortOption", strSortOption).append("strCaseSelection", strCaseSelection)
                .append("strJDPADRadio", strJDPADRadio).toString();
        /*
         * .append("strFilter", strFilter) .append("strTime", strTime) .append(
         * "strTimeFlag", strTimeFlag).toString();
         */
    }

    public int getIntStartPos() {
        return intStartPos;
    }

    public void setIntStartPos(final int intStartPos) {
        this.intStartPos = intStartPos;
    }

    public int getIntNoOfRecs() {
        return intNoOfRecs;
    }

    public void setIntNoOfRecs(final int intNoOfRecs) {
        this.intNoOfRecs = intNoOfRecs;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the strSortSelection
     */
    public String getStrSortSelection() {
        return strSortSelection;
    }

    /**
     * @param strSortSelection
     *            the strSortSelection to set
     */
    public void setStrSortSelection(final String strSortSelection) {
        this.strSortSelection = strSortSelection;
    }

    /**
     * @return the strDaySelection
     */
    public String getStrDaySelection() {
        return strDaySelection;
    }

    /**
     * @param strDaySelection
     *            the strDaySelection to set
     */
    public void setStrDaySelection(final String strDaySelection) {
        this.strDaySelection = strDaySelection;
    }

    /**
     * @return the intTotalCount
     */
    public int getIntTotalCount() {
        return intTotalCount;
    }

    /**
     * @param intTotalCount
     *            the intTotalCount to set
     */
    public void setIntTotalCount(final int intTotalCount) {
        this.intTotalCount = intTotalCount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(final String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

}
