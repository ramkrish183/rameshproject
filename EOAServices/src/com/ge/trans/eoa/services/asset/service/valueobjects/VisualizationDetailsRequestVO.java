package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class VisualizationDetailsRequestVO {

    private String customerId;
    private String assetGroupName;
    private String assetNumber;
    private String source;
    private String sourceType;
    private String fromDate;
    private String toDate;
    private List<String> mpNumbers = new ArrayList<String>();
    private String userLanguage;
    private String controllerCfg;
    private String controllerSrc;
    private String dateFormat;
    private String faultCode;
    private String notchOperator;
    private String notchValue;
    private String ambientTempOperator;
    private String ambientTempValue1;
    private String ambientTempValue2;
    private String engineGHPOperator;
    private String engineGHPValue1;
    private String engineGHPValue2;
    private String engineSpeedOperator;
    private String engineSpeedValue1;
    private String engineSpeedValue2;
    private String locoState;
    private String sourceTypeCd;
    private List<String> virtualParameters = new ArrayList<String>();
    private String family;
    private String assetObjid;
    private List<String> anomParameters = new ArrayList<String>();
    private String oilInletOp;
    private String oilInletValue1;
    private String oilInletValue2;
    private String hpAvailableOp;
    private String hpAvailableValue1;
    private String hpAvailableValue2;
    private String barometricPressOp;
    private String barometricPressValue1;
    private String barometricPressValue2;
    private String rxObjid;
    private String noOfDays;
    
    public String getRxObjid() {
		return rxObjid;
	}

	public void setRxObjid(String rxObjid) {
		this.rxObjid = rxObjid;
	}

	public String getOilInletOp() {
        return oilInletOp;
    }

    public void setOilInletOp(String oilInletOp) {
        this.oilInletOp = oilInletOp;
    }

    public String getOilInletValue1() {
        return oilInletValue1;
    }

    public void setOilInletValue1(String oilInletValue1) {
        this.oilInletValue1 = oilInletValue1;
    }

    public String getOilInletValue2() {
        return oilInletValue2;
    }

    public void setOilInletValue2(String oilInletValue2) {
        this.oilInletValue2 = oilInletValue2;
    }

    public String getHpAvailableOp() {
        return hpAvailableOp;
    }

    public void setHpAvailableOp(String hpAvailableOp) {
        this.hpAvailableOp = hpAvailableOp;
    }

    public String getHpAvailableValue1() {
        return hpAvailableValue1;
    }

    public void setHpAvailableValue1(String hpAvailableValue1) {
        this.hpAvailableValue1 = hpAvailableValue1;
    }

    public String getHpAvailableValue2() {
        return hpAvailableValue2;
    }

    public void setHpAvailableValue2(String hpAvailableValue2) {
        this.hpAvailableValue2 = hpAvailableValue2;
    }

    public String getBarometricPressOp() {
        return barometricPressOp;
    }

    public void setBarometricPressOp(String barometricPressOp) {
        this.barometricPressOp = barometricPressOp;
    }

    public String getBarometricPressValue1() {
        return barometricPressValue1;
    }

    public void setBarometricPressValue1(String barometricPressValue1) {
        this.barometricPressValue1 = barometricPressValue1;
    }

    public String getBarometricPressValue2() {
        return barometricPressValue2;
    }

    public void setBarometricPressValue2(String barometricPressValue2) {
        this.barometricPressValue2 = barometricPressValue2;
    }

    public List<String> getAnomParameters() {
        return anomParameters;
    }

    public void setAnomParameters(List<String> anomParameters) {
        this.anomParameters = anomParameters;
    }

    public String getAssetObjid() {
        return assetObjid;
    }

    public void setAssetObjid(String assetObjid) {
        this.assetObjid = assetObjid;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public List<String> getVirtualParameters() {
        return virtualParameters;
    }

    public void setVirtualParameters(List<String> virtualParameters) {
        this.virtualParameters = virtualParameters;
    }

    public String getSourceTypeCd() {
        return sourceTypeCd;
    }

    public void setSourceTypeCd(String sourceTypeCd) {
        this.sourceTypeCd = sourceTypeCd;
    }

    public String getControllerSrc() {
        return controllerSrc;
    }

    public void setControllerSrc(String controllerSrc) {
        this.controllerSrc = controllerSrc;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getControllerCfg() {
        return controllerCfg;
    }

    public void setControllerCfg(String controllerCfg) {
        this.controllerCfg = controllerCfg;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<String> getMpNumbers() {
        return mpNumbers;
    }

    public void setMpNumbers(List<String> mpNumbers) {
        this.mpNumbers = mpNumbers;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getNotchOperator() {
        return notchOperator;
    }

    public void setNotchOperator(String notchOperator) {
        this.notchOperator = notchOperator;
    }

    public String getNotchValue() {
        return notchValue;
    }

    public void setNotchValue(String notchValue) {
        this.notchValue = notchValue;
    }

    public String getAmbientTempOperator() {
        return ambientTempOperator;
    }

    public void setAmbientTempOperator(String ambientTempOperator) {
        this.ambientTempOperator = ambientTempOperator;
    }

    public String getAmbientTempValue1() {
        return ambientTempValue1;
    }

    public void setAmbientTempValue1(String ambientTempValue1) {
        this.ambientTempValue1 = ambientTempValue1;
    }

    public String getAmbientTempValue2() {
        return ambientTempValue2;
    }

    public void setAmbientTempValue2(String ambientTempValue2) {
        this.ambientTempValue2 = ambientTempValue2;
    }

    public String getEngineGHPOperator() {
        return engineGHPOperator;
    }

    public void setEngineGHPOperator(String engineGHPOperator) {
        this.engineGHPOperator = engineGHPOperator;
    }

    public String getEngineGHPValue1() {
        return engineGHPValue1;
    }

    public void setEngineGHPValue1(String engineGHPValue1) {
        this.engineGHPValue1 = engineGHPValue1;
    }

    public String getEngineGHPValue2() {
        return engineGHPValue2;
    }

    public void setEngineGHPValue2(String engineGHPValue2) {
        this.engineGHPValue2 = engineGHPValue2;
    }

    public String getEngineSpeedOperator() {
        return engineSpeedOperator;
    }

    public void setEngineSpeedOperator(String engineSpeedOperator) {
        this.engineSpeedOperator = engineSpeedOperator;
    }

    public String getEngineSpeedValue1() {
        return engineSpeedValue1;
    }

    public void setEngineSpeedValue1(String engineSpeedValue1) {
        this.engineSpeedValue1 = engineSpeedValue1;
    }

    public String getEngineSpeedValue2() {
        return engineSpeedValue2;
    }

    public void setEngineSpeedValue2(String engineSpeedValue2) {
        this.engineSpeedValue2 = engineSpeedValue2;
    }

    public String getLocoState() {
        return locoState;
    }

    public void setLocoState(String locoState) {
        this.locoState = locoState;
    }

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

}
