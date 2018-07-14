/**
 * ============================================================
 * File : RuleTesterServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects;
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

package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

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
@SuppressWarnings("unchecked")
public class RuleTesterServiceVO extends BaseVO {

    static final long serialVersionUID = 97879371L;
    private String strRuleID = RMDCommonConstants.EMPTY_STRING;
    private String strDay = RMDCommonConstants.EMPTY_STRING;
    private String strUserName = RMDCommonConstants.EMPTY_STRING;
    private String strTestSeqId = RMDCommonConstants.EMPTY_STRING;
    private String strAssetHeader = RMDCommonConstants.EMPTY_STRING;
    private String[] strAssetNumber;
    private ArrayList alDayList = new ArrayList();
    private ArrayList alUserNameList = new ArrayList();
    private ArrayList alRuleIdList = new ArrayList();
    private ArrayList alAssetHeaderList = new ArrayList();
    private ArrayList alAssetNumberList = new ArrayList();

    /**
     * @return the strRuleID
     */
    public String getStrRuleID() {
        return strRuleID;
    }

    /**
     * @param strRuleID
     *            the strRuleID to set
     */
    public void setStrRuleID(String strRuleID) {
        this.strRuleID = strRuleID;
    }

    /**
     * @return the strUserName
     */
    @Override
    public String getStrUserName() {
        return strUserName;
    }

    /**
     * @param strUserName
     *            the strUserName to set
     */
    @Override
    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    /**
     * @return the alUserNameList
     */
    public ArrayList getAlUserNameList() {
        return alUserNameList;
    }

    /**
     * @param alUserNameList
     *            the alUserNameList to set
     */
    public void setAlUserNameList(ArrayList alUserNameList) {
        this.alUserNameList = alUserNameList;
    }

    /**
     * @return the strDay
     */
    public String getStrDay() {
        return strDay;
    }

    /**
     * @param strDay
     *            the strDay to set
     */
    public void setStrDay(String strDay) {
        this.strDay = strDay;
    }

    /**
     * @return the strTestSeqId
     */
    public String getStrTestSeqId() {
        return strTestSeqId;
    }

    /**
     * @param strTestSeqId
     *            the strTestSeqId to set
     */
    public void setStrTestSeqId(String strTestSeqId) {
        this.strTestSeqId = strTestSeqId;
    }

    /**
     * @return the alDayList
     */
    public ArrayList getAlDayList() {
        return alDayList;
    }

    /**
     * @param alDayList
     *            the alDayList to set
     */
    public void setAlDayList(ArrayList alDayList) {
        this.alDayList = alDayList;
    }

    /**
     * @return the alRuleIdList
     */
    public ArrayList getAlRuleIdList() {
        return alRuleIdList;
    }

    /**
     * @param alRuleIdList
     *            the alRuleIdList to set
     */
    public void setAlRuleIdList(ArrayList alRuleIdList) {
        this.alRuleIdList = alRuleIdList;
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
    public void setStrAssetHeader(String strAssetHeader) {
        this.strAssetHeader = strAssetHeader;
    }

    /**
     * @return the strAssetNumber
     */
    public String[] getStrAssetNumber() {
        return strAssetNumber;
    }

    /**
     * @param strAssetNumber
     *            the strAssetNumber to set
     */
    public void setStrAssetNumber(String[] strAssetNumber) {
        this.strAssetNumber = strAssetNumber;
    }

    /**
     * @return the alAssetHeaderList
     */
    public ArrayList getAlAssetHeaderList() {
        return alAssetHeaderList;
    }

    /**
     * @param alAssetHeaderList
     *            the alAssetHeaderList to set
     */
    public void setAlAssetHeaderList(ArrayList alAssetHeaderList) {
        this.alAssetHeaderList = alAssetHeaderList;
    }

    /**
     * @return the alAssetNumberList
     */
    public ArrayList getAlAssetNumberList() {
        return alAssetNumberList;
    }

    /**
     * @param alAssetNumberList
     *            the alAssetNumberList to set
     */
    public void setAlAssetNumberList(ArrayList alAssetNumberList) {
        this.alAssetNumberList = alAssetNumberList;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strRuleID", strRuleID)
                .append("strDay", strDay).append("strUserName", strUserName).append("strTestSeqId", strTestSeqId)
                .append("strAssetHeader", strAssetHeader).append("strAssetNumber", strAssetNumber)
                .append("alDayList", alDayList).append("alUserNameList", alUserNameList)
                .append("alRuleIdList", alRuleIdList).append("alAssetHeaderList", alAssetHeaderList)
                .append("alAssetNumberList", alAssetNumberList).toString();
    }
}
