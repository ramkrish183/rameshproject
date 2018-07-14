package com.ge.trans.rmd.services.locovision.valueobjects;
/**
 * This pojo contains all the possible values to the corresponding templates
 * Mp based
 */

public class LDVRMPType {
	
	private LDVRMPParamType mpOper = null;
	private LDVRMPParamType mpParam = null;
	private Long mpParams = null;
	private Long mpOperator = null;
	private String mpValue = null;

	public Long getMpParams() {
		return mpParams;
	}

	public void setMpParams(Long mpParams) {
		this.mpParams = mpParams;
	}

	public Long getMpOperator() {
		return mpOperator;
	}

	public void setMpOperator(Long mpOperator) {
		this.mpOperator = mpOperator;
	}

	public String getMpValue() {
		return mpValue;
	}

	public void setMpValue(String mpValue) {
		this.mpValue = mpValue;
	}

	public LDVRMPParamType getMpOper() {
		return mpOper;
	}

	public void setMpOper(LDVRMPParamType mpOper) {
		this.mpOper = mpOper;
	}

	/**
	 * @return the mpParam
	 */
	public LDVRMPParamType getMpParam() {
		return mpParam;
	}

	/**
	 * @param mpParam the mpParam to set
	 */
	public void setMpParam(LDVRMPParamType mpParam) {
		this.mpParam = mpParam;
	}

	

}
