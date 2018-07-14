package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

public class HealthRulesVO { 
	
	private long ruleId;
	private String ruleTitle;
	private String ruleType;
	private String family;
	
	public HealthRulesVO(long ruleId, String ruleTitle, String ruleType,
			String family) { 
		this.ruleId = ruleId;
		this.ruleTitle = ruleTitle;
		this.ruleType = ruleType;
		this.family = family;
	}
	
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleTitle() {
		return ruleTitle;
	}
	public void setRuleTitle(String ruleTitle) {
		this.ruleTitle = ruleTitle;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
}
