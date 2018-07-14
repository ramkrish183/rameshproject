package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class TracerServiceVO extends BaseVO {

    /**
     * 
     */
    private static final long serialVersionUID = -6695027144102073621L;
    private String strFinalRuleID = RMDCommonConstants.EMPTY_STRING;
    private String strSelRuleID = RMDCommonConstants.EMPTY_STRING;
    // Tracking id for MJ or Tester or Run Re-creator or DS Tool Output
    private String strTrackingID = RMDCommonConstants.EMPTY_STRING;
    // Tracer ID for the corresponding Tracking ID;
    private String strTraceID = RMDCommonConstants.EMPTY_STRING;
    private String strMode = RMDCommonConstants.EMPTY_STRING;
    private String strSelectedRuleType = RMDCommonConstants.EMPTY_STRING;
    private String strUser = RMDCommonConstants.EMPTY_STRING;
    private String strLang = RMDCommonConstants.EMPTY_STRING;
    private String strCaseId = RMDCommonConstants.EMPTY_STRING;
    private String strRuleTitle = RMDCommonConstants.EMPTY_STRING;
    private String strLookBack = RMDCommonConstants.EMPTY_STRING;
    private String strLookBackday = RMDCommonConstants.EMPTY_STRING;
    private String strLookBackWeeks = RMDCommonConstants.EMPTY_STRING;
    private String strFaultCount = RMDCommonConstants.EMPTY_STRING;
    private String strFaultRuleID = RMDCommonConstants.EMPTY_STRING;
    private int intStartPos = 0;
    private int intNoOfRecs = 0;
    private int intTotalCoun = 0;
    private List alTrackingID = new ArrayList();
    private List alRuleID = new ArrayList();
    private List alUserName = new ArrayList();
    private List alLookBackdays = new ArrayList();
    private List alLookBackWeeks = new ArrayList();
    private List alTestViewResultVO = new ArrayList();
    private List alTrackingResult = new ArrayList();
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStrFinalRuleID() {
        return strFinalRuleID;
    }

    public void setStrFinalRuleID(String strFinalRuleID) {
        this.strFinalRuleID = strFinalRuleID;
    }

    public String getStrSelRuleID() {
        return strSelRuleID;
    }

    public void setStrSelRuleID(String strSelRuleID) {
        this.strSelRuleID = strSelRuleID;
    }

    public String getStrTrackingID() {
        return strTrackingID;
    }

    public void setStrTrackingID(String strTrackingID) {
        this.strTrackingID = strTrackingID;
    }

    public String getStrMode() {
        return strMode;
    }

    public void setStrMode(String strMode) {
        this.strMode = strMode;
    }

    public String getStrSelectedRuleType() {
        return strSelectedRuleType;
    }

    public void setStrSelectedRuleType(String strSelectedRuleType) {
        this.strSelectedRuleType = strSelectedRuleType;
    }

    public String getStrUser() {
        return strUser;
    }

    public void setStrUser(String strUser) {
        this.strUser = strUser;
    }

    public String getStrLang() {
        return strLang;
    }

    public void setStrLang(String strLang) {
        this.strLang = strLang;
    }

    public String getStrCaseId() {
        return strCaseId;
    }

    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrRuleTitle() {
        return strRuleTitle;
    }

    public void setStrRuleTitle(String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
    }

    public String getStrLookBack() {
        return strLookBack;
    }

    public void setStrLookBack(String strLookBack) {
        this.strLookBack = strLookBack;
    }

    public String getStrLookBackday() {
        return strLookBackday;
    }

    public void setStrLookBackday(String strLookBackday) {
        this.strLookBackday = strLookBackday;
    }

    public String getStrLookBackWeeks() {
        return strLookBackWeeks;
    }

    public void setStrLookBackWeeks(String strLookBackWeeks) {
        this.strLookBackWeeks = strLookBackWeeks;
    }

    public String getStrFaultCount() {
        return strFaultCount;
    }

    public void setStrFaultCount(String strFaultCount) {
        this.strFaultCount = strFaultCount;
    }

    public String getStrFaultRuleID() {
        return strFaultRuleID;
    }

    public void setStrFaultRuleID(String strFaultRuleID) {
        this.strFaultRuleID = strFaultRuleID;
    }

    public int getIntStartPos() {
        return intStartPos;
    }

    public void setIntStartPos(int intStartPos) {
        this.intStartPos = intStartPos;
    }

    public int getIntNoOfRecs() {
        return intNoOfRecs;
    }

    public void setIntNoOfRecs(int intNoOfRecs) {
        this.intNoOfRecs = intNoOfRecs;
    }

    public int getIntTotalCoun() {
        return intTotalCoun;
    }

    public void setIntTotalCoun(int intTotalCoun) {
        this.intTotalCoun = intTotalCoun;
    }

    public List getAlTrackingID() {
        return alTrackingID;
    }

    public void setAlTrackingID(List alTrackingID) {
        this.alTrackingID = alTrackingID;
    }

    public List getAlRuleID() {
        return alRuleID;
    }

    public void setAlRuleID(List alRuleID) {
        this.alRuleID = alRuleID;
    }

    public List getAlUserName() {
        return alUserName;
    }

    public void setAlUserName(List alUserName) {
        this.alUserName = alUserName;
    }

    public List getAlLookBackdays() {
        return alLookBackdays;
    }

    public void setAlLookBackdays(List alLookBackdays) {
        this.alLookBackdays = alLookBackdays;
    }

    public List getAlLookBackWeeks() {
        return alLookBackWeeks;
    }

    public void setAlLookBackWeeks(List alLookBackWeeks) {
        this.alLookBackWeeks = alLookBackWeeks;
    }

    public List getAlTestViewResultVO() {
        return alTestViewResultVO;
    }

    public void setAlTestViewResultVO(List alTestViewResultVO) {
        this.alTestViewResultVO = alTestViewResultVO;
    }

    public List getAlTrackingResult() {
        return alTrackingResult;
    }

    public void setAlTrackingResult(List alTrackingResult) {
        this.alTrackingResult = alTrackingResult;
    }

    public String getStrTraceID() {
        return strTraceID;
    }

    public void setStrTraceID(String strTraceID) {
        this.strTraceID = strTraceID;
    }

}
