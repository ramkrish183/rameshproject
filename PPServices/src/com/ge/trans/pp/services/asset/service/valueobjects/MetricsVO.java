package com.ge.trans.pp.services.asset.service.valueobjects;

public class MetricsVO {
	private String unitName;
    private String convertedValue;
    private String paramId;
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getConvertedValue() {
		return convertedValue;
	}
	public void setConvertedValue(String convertedValue) {
		this.convertedValue = convertedValue;
	}
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
}
