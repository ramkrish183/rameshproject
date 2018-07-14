package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class ParameterRequestServiceVO extends BaseVO{
	
	private String strLanguage;
	private String family;
	private String uom;
	private String isGERuleAUthor;
	private String customer;
	private String ruleType;
	public String getStrLanguage() {
		return strLanguage;
	}
	public void setStrLanguage(String strLanguage) {
		this.strLanguage = strLanguage;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getIsGERuleAUthor() {
		return isGERuleAUthor;
	}
	public void setIsGERuleAUthor(String isGERuleAUthor) {
		this.isGERuleAUthor = isGERuleAUthor;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	

	
}
