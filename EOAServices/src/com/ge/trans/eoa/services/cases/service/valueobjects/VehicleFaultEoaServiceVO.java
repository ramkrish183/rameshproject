/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleFaultServiceVO.java
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

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
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
public class VehicleFaultEoaServiceVO extends BaseVO {
    static final long serialVersionUID = 33325626;
    private String strFromDate = null;
    private String strToDate = null;
    private String strRoadNo = null;
    private String strHC = null;
    private String strDaysSelected = null;
    private String strSortOption = null;
    private String strMinutes = null;
    private String dataSets = null;
    // Added for timebased filter for datascreen
    private String strCaseFrom = null;
    private String caseId = null;
    // Added for timebased filter for datascreen

    private String strJDPADRadio = null;
    private String strRuleDefId = null;
    private String strGraphParms = null;
    private String strControllerCfg = null;
    private String IsDateDiffSelected = null;
    private boolean isLimitedParam = false;
    private boolean isHideL3Faults = false;
    private boolean isInitLoad = false;
    private boolean isNotch8 = false;
    private boolean isMobileRequest = false;
    private String roleName;
    private String userCustomer;
    private String userTimeZone;
	private String userTimeZoneCode;
	private boolean enableCustomColumns;
    
    public boolean isMobileRequest() {
		return isMobileRequest;
	}

	public void setMobileRequest(boolean isMobileRequest) {
		this.isMobileRequest = isMobileRequest;
	}

    public boolean isNotch8() {
        return isNotch8;
    }

    public void setNotch8(boolean isNotch8) {
        this.isNotch8 = isNotch8;
    }

    public boolean isInitLoad() {
        return isInitLoad;
    }

    public void setInitLoad(boolean isInitLoad) {
        this.isInitLoad = isInitLoad;
    }

    public boolean isHideL3Faults() {
        return isHideL3Faults;
    }

    public void setHideL3Faults(boolean isHideL3Faults) {
        this.isHideL3Faults = isHideL3Faults;
    }

    public boolean isLimitedParam() {
        return isLimitedParam;
    }

    public void setLimitedParam(boolean isLimitedParam) {
        this.isLimitedParam = isLimitedParam;
    }

    public String getIsDateDiffSelected() {
        return IsDateDiffSelected;
    }

    public void setIsDateDiffSelected(String isDateDiffSelected) {
        IsDateDiffSelected = isDateDiffSelected;
    }

    public String getStrControllerCfg() {
        return strControllerCfg;
    }

    public void setStrControllerCfg(String strControllerCfg) {
        this.strControllerCfg = strControllerCfg;
    }

    public String getStrGraphParms() {
        return strGraphParms;
    }

    public void setStrGraphParms(String strGraphParms) {
        this.strGraphParms = strGraphParms;
    }

    public String getStrRuleDefId() {
        return strRuleDefId;
    }

    public void setStrRuleDefId(String strRuleDefId) {
        this.strRuleDefId = strRuleDefId;
    }

    public String getStrJDPADRadio() {
        return strJDPADRadio;
    }

    public void setStrJDPADRadio(String strJDPADRadio) {
        this.strJDPADRadio = strJDPADRadio;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getStrCaseFrom() {
        return strCaseFrom;
    }

    public void setStrCaseFrom(String strCaseFrom) {
        this.strCaseFrom = strCaseFrom;
    }

    public String getDataSets() {
        return dataSets;
    }

    public void setDataSets(String dataSets) {
        this.dataSets = dataSets;
    }

    private String strDays = RMDCommonConstants.EMPTY_STRING;
    private String strCalendar = RMDCommonConstants.EMPTY_STRING;
    private String strAssetHdr = RMDCommonConstants.EMPTY_STRING;
    private String strMins = RMDCommonConstants.EMPTY_STRING;
    private String strExport = "N";
    private Date fromDate;
    private Date toDate;
    private int intStartPos = 0;
    private int intNoOfRecs = 0;
    private long minFaultSeqId = 0;
    private long maxFaultSeqId = 0;
    private long totalCount = 0;
    private boolean blnHealtCheck;
    // ADDING FOR MULTIPLE CUSTOMERS:VG:START
    private String customerId;
    private String assetGrpName;
    private String strScreen;
    private String strCaseType;
    private String strAllRecords;

    public String getStrAllRecords() {
        return strAllRecords;
    }

    public void setStrAllRecords(String strAllRecords) {
        this.strAllRecords = strAllRecords;
    }

    public String getStrCaseType() {
        return strCaseType;
    }

    public void setStrCaseType(String strCaseType) {
        this.strCaseType = strCaseType;
    }

    public String getStrScreen() {
        return strScreen;
    }

    public void setStrScreen(String strScreen) {
        this.strScreen = strScreen;
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

    // ADDING FOR MULTIPLE CUSTOMERS:VG:END
    public String getStrToDate() {
        return strToDate;
    }

    public void setStrToDate(final String strToDate) {
        this.strToDate = strToDate;
    }

    public String getStrRoadNo() {
        return strRoadNo;
    }

    public void setStrRoadNo(final String strRoadNo) {
        this.strRoadNo = strRoadNo;
    }

    public String getStrHC() {
        return strHC;
    }

    public void setStrHC(final String strHC) {
        this.strHC = strHC;
    }

    public String getStrDaysSelected() {
        return strDaysSelected;
    }

    public void setStrDaysSelected(final String strDaysSelected) {
        this.strDaysSelected = strDaysSelected;
    }

    public String getStrSortOption() {
        return strSortOption;
    }

    public void setStrSortOption(final String strSortOption) {
        this.strSortOption = strSortOption;
    }

    public String getStrFromDate() {
        return strFromDate;
    }

    public void setStrFromDate(final String strFromDate) {
        this.strFromDate = strFromDate;
    }

    public String getStrMinutes() {
        return strMinutes;
    }

    public void setStrMinutes(final String strMinutes) {
        this.strMinutes = strMinutes;
    }

    public String getStrDays() {
        return strDays;
    }

    public void setStrDays(final String strDays) {
        this.strDays = strDays;
    }

    public String getStrCalendar() {
        return strCalendar;
    }

    public void setStrCalendar(final String strCalendar) {
        this.strCalendar = strCalendar;
    }

    public String getStrExport() {
        return strExport;
    }

    public void setStrExport(final String strExport) {
        this.strExport = strExport;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate
     *            the fromDate to set
     */
    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate
     *            the toDate to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the intStartPos
     */
    public int getIntStartPos() {
        return intStartPos;
    }

    /**
     * @param intStartPos
     *            the intStartPos to set
     */
    public void setIntStartPos(final int intStartPos) {
        this.intStartPos = intStartPos;
    }

    /**
     * @return the intNoOfRecs
     */
    public int getIntNoOfRecs() {
        return intNoOfRecs;
    }

    /**
     * @param intNoOfRecs
     *            the intNoOfRecs to set
     */
    public void setIntNoOfRecs(final int intNoOfRecs) {
        this.intNoOfRecs = intNoOfRecs;
    }

    /**
     * @return the strMins
     */
    public String getStrMins() {
        return strMins;
    }

    /**
     * @param strMins
     *            the strMins to set
     */
    public void setStrMins(final String strMins) {
        this.strMins = strMins;
    }

    /**
     * @return the strAssetHdr
     */
    public String getStrAssetHdr() {
        return strAssetHdr;
    }

    /**
     * @param strAssetHdr
     *            the strAssetHdr to set
     */
    public void setStrAssetHdr(final String strAssetHdr) {
        this.strAssetHdr = strAssetHdr;
    }

    /**
     * @return the minFaultSeqId
     */
    public long getMinFaultSeqId() {
        return minFaultSeqId;
    }

    /**
     * @param minFaultSeqId
     *            the minFaultSeqId to set
     */
    public void setMinFaultSeqId(final long minFaultSeqId) {
        this.minFaultSeqId = minFaultSeqId;
    }

    /**
     * @return the maxFaultSeqId
     */
    public long getMaxFaultSeqId() {
        return maxFaultSeqId;
    }

    /**
     * @param maxFaultSeqId
     *            the maxFaultSeqId to set
     */
    public void setMaxFaultSeqId(final long maxFaultSeqId) {
        this.maxFaultSeqId = maxFaultSeqId;
    }

    /**
     * @return the totalCount
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the blnHealtCheck
     */
    public boolean isBlnHealtCheck() {
        return blnHealtCheck;
    }

    /**
     * @param blnHealtCheck
     *            the blnHealtCheck to set
     */
    public void setBlnHealtCheck(final boolean blnHealtCheck) {
        this.blnHealtCheck = blnHealtCheck;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strFromDate", strFromDate)
                .append("strToDate", strToDate).append("strRoadNo", strRoadNo).append("strHC", strHC)
                .append("strDaysSelected", strDaysSelected).append("strSortOption", strSortOption)
                .append("strMinutes", strMinutes).append("strDays", strDays).append("strCalendar", strCalendar)
                .append("strExport", strExport).append("fromDate", fromDate).append("toDate", toDate).toString();
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(String userCustomer) {
		this.userCustomer = userCustomer;
	}

	public String getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public String getUserTimeZoneCode() {
		return userTimeZoneCode;
	}

	public void setUserTimeZoneCode(String userTimeZoneCode) {
		this.userTimeZoneCode = userTimeZoneCode;
	}

	public boolean isEnableCustomColumns() {
		return enableCustomColumns;
	}

	public void setEnableCustomColumns(boolean enableCustomColumns) {
		this.enableCustomColumns = enableCustomColumns;
	}
	
}
