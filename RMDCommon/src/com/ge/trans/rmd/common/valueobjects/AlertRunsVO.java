package com.ge.trans.rmd.common.valueobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * VO for Alert Run Search Screen.
 * 
 * @author 212556286
 * 
 */
public class AlertRunsVO extends BaseVO {

    private static final long serialVersionUID = 3613004194591565031L;
    private String runObjId;
    private String runDate;
    private String run2Vehicle;
    private String vehHdrCust;
    private String serialNo;
    private String family;
    private String ruleId;
    private String ruleTitle;
    private String controllerSrcId;
    private String eoaMinLookBack;
    private String eoaMaxLookBack;
    private String atsMinLookBack;
    private String atsMaxLookBack;
    private String ruleFiredDate;
    private String firedDataRecord;
    private String dataRecordType;
    private boolean isAlertSent;
    private String firingId;
    private String strCustomer;
    private List<AlertRuleParmVO> ruleParmData = new ArrayList<AlertRuleParmVO>();
    
    public String getStrCustomer() {
		return strCustomer;
	}

	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}

	public String getRunObjId() {
        return runObjId;
    }

    public void setRunObjId(String runObjId) {
        this.runObjId = runObjId;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getRun2Vehicle() {
        return run2Vehicle;
    }

    public void setRun2Vehicle(String run2Vehicle) {
        this.run2Vehicle = run2Vehicle;
    }

    public String getVehHdrCust() {
        return vehHdrCust;
    }

    public void setVehHdrCust(String vehHdrCust) {
        this.vehHdrCust = vehHdrCust;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getControllerSrcId() {
        return controllerSrcId;
    }

    public void setControllerSrcId(String controllerSrcId) {
        this.controllerSrcId = controllerSrcId;
    }

    public String getEoaMinLookBack() {
        return eoaMinLookBack;
    }

    public void setEoaMinLookBack(String eoaMinLookBack) {
        this.eoaMinLookBack = eoaMinLookBack;
    }

    public String getEoaMaxLookBack() {
        return eoaMaxLookBack;
    }

    public void setEoaMaxLookBack(String eoaMaxLookBack) {
        this.eoaMaxLookBack = eoaMaxLookBack;
    }

    public String getAtsMinLookBack() {
        return atsMinLookBack;
    }

    public void setAtsMinLookBack(String atsMinLookBack) {
        this.atsMinLookBack = atsMinLookBack;
    }

    public String getAtsMaxLookBack() {
        return atsMaxLookBack;
    }

    public void setAtsMaxLookBack(String atsMaxLookBack) {
        this.atsMaxLookBack = atsMaxLookBack;
    }

    public String getRuleFiredDate() {
        return ruleFiredDate;
    }

    public void setRuleFiredDate(String ruleFiredDate) {
        this.ruleFiredDate = ruleFiredDate;
    }

    public String getFiredDataRecord() {
        return firedDataRecord;
    }

    public void setFiredDataRecord(String firedDataRecord) {
        this.firedDataRecord = firedDataRecord;
    }

    public String getDataRecordType() {
        return dataRecordType;
    }

    public void setDataRecordType(String dataRecordType) {
        this.dataRecordType = dataRecordType;
    }

    public boolean isAlertSent() {
        return isAlertSent;
    }

    public void setAlertSent(boolean isAlertSent) {
        this.isAlertSent = isAlertSent;
    }

    public List<AlertRuleParmVO> getRuleParmData() {
        return ruleParmData;
    }

    public void setRuleParmData(List<AlertRuleParmVO> ruleParmData) {
        this.ruleParmData = ruleParmData;
    }

    public String getFiringId() {
        return firingId;
    }

    public void setFiringId(String firingId) {
        this.firingId = firingId;
    }

}
