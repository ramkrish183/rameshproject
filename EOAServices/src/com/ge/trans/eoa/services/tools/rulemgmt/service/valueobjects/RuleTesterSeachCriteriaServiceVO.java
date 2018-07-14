/**
 * ============================================================
 * File : RuleTesterSeachCriteriaServiceVO.java
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
public class RuleTesterSeachCriteriaServiceVO extends BaseVO {

    static final long serialVersionUID = 39824139L;
    private String strTrackingID = RMDCommonConstants.EMPTY_STRING;
    private String strRuleID = RMDCommonConstants.EMPTY_STRING;
    private String strUserName = RMDCommonConstants.EMPTY_STRING;
    private String strSelTrackingID = RMDCommonConstants.EMPTY_STRING;
    private String strSelRuleID = RMDCommonConstants.EMPTY_STRING;
    private String strSelUserName = RMDCommonConstants.EMPTY_STRING;
    private String strLookBackday = RMDCommonConstants.EMPTY_STRING;
    private String strLookBackWeeks = RMDCommonConstants.EMPTY_STRING;
    private String strFaultCount = RMDCommonConstants.EMPTY_STRING;
    private String strLookBack = RMDCommonConstants.EMPTY_STRING;
    private String strRuleTitle = RMDCommonConstants.EMPTY_STRING;
    private String strFaultRuleID = RMDCommonConstants.EMPTY_STRING;
    private ArrayList alTrackingID = new ArrayList();
    private ArrayList alRuleID = new ArrayList();
    private ArrayList alUserName = new ArrayList();
    private ArrayList alLookBackdays = new ArrayList();
    private ArrayList alLookBackWeeks = new ArrayList();
    private ArrayList alTestViewResultVO = new ArrayList();
    private ArrayList alTrackingResult = new ArrayList();
    private int intStartPos = 0;
    private int intNoOfRecs = 0;
    private String strSelectedRuleId = RMDCommonConstants.EMPTY_STRING;
    private String strSelectedRuleType = RMDCommonConstants.EMPTY_STRING;
    private String strSelTracingID = RMDCommonConstants.EMPTY_STRING;
    private String strCaseID = RMDCommonConstants.EMPTY_STRING;

    /**
     * @return the strTrackingID
     */
    public String getStrTrackingID() {
        return strTrackingID;
    }

    /**
     * @param strTrackingID
     *            the strTrackingID to set
     */
    public void setStrTrackingID(String strTrackingID) {
        this.strTrackingID = strTrackingID;
    }

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
     * @return the strLookBackday
     */
    public String getStrLookBackday() {
        return strLookBackday;
    }

    /**
     * @param strLookBackday
     *            the strLookBackday to set
     */
    public void setStrLookBackday(String strLookBackday) {
        this.strLookBackday = strLookBackday;
    }

    /**
     * @return the alTrackingID
     */
    public ArrayList getAlTrackingID() {
        return alTrackingID;
    }

    /**
     * @param alTrackingID
     *            the alTrackingID to set
     */
    public void setAlTrackingID(ArrayList alTrackingID) {
        this.alTrackingID = alTrackingID;
    }

    /**
     * @return the alRuleID
     */
    public ArrayList getAlRuleID() {
        return alRuleID;
    }

    /**
     * @param alRuleID
     *            the alRuleID to set
     */
    public void setAlRuleID(ArrayList alRuleID) {
        this.alRuleID = alRuleID;
    }

    /**
     * @return the alUserName
     */
    public ArrayList getAlUserName() {
        return alUserName;
    }

    /**
     * @param alUserName
     *            the alUserName to set
     */
    public void setAlUserName(ArrayList alUserName) {
        this.alUserName = alUserName;
    }

    /**
     * @return the alLookBackdays
     */
    public ArrayList getAlLookBackdays() {
        return alLookBackdays;
    }

    /**
     * @param alLookBackdays
     *            the alLookBackdays to set
     */
    public void setAlLookBackdays(ArrayList alLookBackdays) {
        this.alLookBackdays = alLookBackdays;
    }

    /**
     * @return the alLookBackWeeks
     */
    public ArrayList getAlLookBackWeeks() {
        return alLookBackWeeks;
    }

    /**
     * @param alLookBackWeeks
     *            the alLookBackWeeks to set
     */
    public void setAlLookBackWeeks(ArrayList alLookBackWeeks) {
        this.alLookBackWeeks = alLookBackWeeks;
    }

    /**
     * @return the alTestViewResultVO
     */
    public ArrayList getAlTestViewResultVO() {
        return alTestViewResultVO;
    }

    /**
     * @param alTestViewResultVO
     *            the alTestViewResultVO to set
     */
    public void setAlTestViewResultVO(ArrayList alTestViewResultVO) {
        this.alTestViewResultVO = alTestViewResultVO;
    }

    /**
     * @return the strFaultCount
     */
    public String getStrFaultCount() {
        return strFaultCount;
    }

    /**
     * @param strFaultCount
     *            the strFaultCount to set
     */
    public void setStrFaultCount(String strFaultCount) {
        this.strFaultCount = strFaultCount;
    }

    /**
     * @return the strRuleTitle
     */
    public String getStrRuleTitle() {
        return strRuleTitle;
    }

    /**
     * @param strRuleTitle
     *            the strRuleTitle to set
     */
    public void setStrRuleTitle(String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
    }

    /**
     * @return the strFaultRuleID
     */
    public String getStrFaultRuleID() {
        return strFaultRuleID;
    }

    /**
     * @param strFaultRuleID
     *            the strFaultRuleID to set
     */
    public void setStrFaultRuleID(String strFaultRuleID) {
        this.strFaultRuleID = strFaultRuleID;
    }

    /**
     * @return the alTrackingResult
     */
    public ArrayList getAlTrackingResult() {
        return alTrackingResult;
    }

    /**
     * @param alTrackingResult
     *            the alTrackingResult to set
     */
    public void setAlTrackingResult(ArrayList alTrackingResult) {
        this.alTrackingResult = alTrackingResult;
    }

    /**
     * @return the strSelTrackingID
     */
    public String getStrSelTrackingID() {
        return strSelTrackingID;
    }

    /**
     * @param strSelTrackingID
     *            the strSelTrackingID to set
     */
    public void setStrSelTrackingID(String strSelTrackingID) {
        this.strSelTrackingID = strSelTrackingID;
    }

    /**
     * @return the strSelRuleID
     */
    public String getStrSelRuleID() {
        return strSelRuleID;
    }

    /**
     * @param strSelRuleID
     *            the strSelRuleID to set
     */
    public void setStrSelRuleID(String strSelRuleID) {
        this.strSelRuleID = strSelRuleID;
    }

    /**
     * @return the strSelUserName
     */
    public String getStrSelUserName() {
        return strSelUserName;
    }

    /**
     * @param strSelUserName
     *            the strSelUserName to set
     */
    public void setStrSelUserName(String strSelUserName) {
        this.strSelUserName = strSelUserName;
    }

    /**
     * @return the strLookBack
     */
    public String getStrLookBack() {
        return strLookBack;
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
    public void setIntStartPos(int intStartPos) {
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
    public void setIntNoOfRecs(int intNoOfRecs) {
        this.intNoOfRecs = intNoOfRecs;
    }

    /**
     * @param strLookBack
     *            the strLookBack to set
     */
    public void setStrLookBack(String strLookBack) {
        this.strLookBack = strLookBack;
    }

    /**
     * @return the strLookBackWeeks
     */
    public String getStrLookBackWeeks() {
        return strLookBackWeeks;
    }

    /**
     * @param strLookBackWeeks
     *            the strLookBackWeeks to set
     */
    public void setStrLookBackWeeks(String strLookBackWeeks) {
        this.strLookBackWeeks = strLookBackWeeks;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strTrackingID", strTrackingID)
                .append("strRuleID", strRuleID).append("strUserName", strUserName)
                .append("strLookBackday", strLookBackday).append("strFaultCount", strFaultCount)
                .append("strRuleTitle", strRuleTitle).append("strFaultRuleID", strFaultRuleID)
                .append("alTrackingID", alTrackingID).append("alRuleID", alRuleID).append("alUserName", alUserName)
                .append("alLookBackdays", alLookBackdays).append("alTestViewResultVO", alTestViewResultVO).toString();
    }

    public String getStrSelectedRuleId() {
        return strSelectedRuleId;
    }

    public void setStrSelectedRuleId(String strSelectedRuleId) {
        this.strSelectedRuleId = strSelectedRuleId;
    }

    public String getStrSelectedRuleType() {
        return strSelectedRuleType;
    }

    public void setStrSelectedRuleType(String strSelectedRuleType) {
        this.strSelectedRuleType = strSelectedRuleType;
    }

    public String getStrSelTracingID() {
        return strSelTracingID;
    }

    public void setStrSelTracingID(String strSelTracingID) {
        this.strSelTracingID = strSelTracingID;
    }

    public String getStrCaseID() {
        return strCaseID;
    }

    public void setStrCaseID(String strCaseID) {
        this.strCaseID = strCaseID;
    }

}
