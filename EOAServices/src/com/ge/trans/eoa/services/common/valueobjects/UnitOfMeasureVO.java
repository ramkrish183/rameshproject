package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class UnitOfMeasureVO extends BaseVO{
	
	private String sourceUom;
	private String targetUom;
	private String sourceAbbr;
	private String targetAbbr;
	private String conversionExp;
	private String isConversionRequired;
	
	public String getIsConversionRequired() {
		return isConversionRequired;
	}
	public void setIsConversionRequired(String isConversionRequired) {
		this.isConversionRequired = isConversionRequired;
	}
	public String getSourceUom() {
		return sourceUom;
	}
	public void setSourceUom(String sourceUom) {
		this.sourceUom = sourceUom;
	}
	public String getTargetUom() {
		return targetUom;
	}
	public void setTargetUom(String targetUom) {
		this.targetUom = targetUom;
	}
	public String getSourceAbbr() {
		return sourceAbbr;
	}
	public void setSourceAbbr(String sourceAbbr) {
		this.sourceAbbr = sourceAbbr;
	}
	public String getTargetAbbr() {
		return targetAbbr;
	}
	public void setTargetAbbr(String targetAbbr) {
		this.targetAbbr = targetAbbr;
	}
	public String getConversionExp() {
		return conversionExp;
	}
	public void setConversionExp(String conversionExp) {
		this.conversionExp = conversionExp;
	}
	
	

}
