package com.ge.trans.rmd.services.lhr.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthRulesResponse", propOrder = {
    "ruleId",
    "ruleTitle",
    "ruleType",
    "family"
})
@XmlRootElement
public class HealthRulesResponse {
	@XmlElement(required = true)
	protected long ruleId;
	@XmlElement(required = true)
	protected String ruleTitle;
	@XmlElement(required = true)
	protected String ruleType;
	@XmlElement(required = true)
	protected String family;
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
