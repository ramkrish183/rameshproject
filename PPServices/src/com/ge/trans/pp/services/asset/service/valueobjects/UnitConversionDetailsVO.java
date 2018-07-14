package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

public class UnitConversionDetailsVO {
	
	private String measurement;
	private String userId;
	private List<UnitConversionVO> lstUnitConversionVO;
	public String getMeasurement() {
		return measurement;
	}
	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<UnitConversionVO> getLstUnitConversionVO() {
		return lstUnitConversionVO;
	}
	public void setLstUnitConversionVO(List<UnitConversionVO> lstUnitConversionVO) {
		this.lstUnitConversionVO = lstUnitConversionVO;
	}


}
