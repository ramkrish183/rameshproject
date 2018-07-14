package com.ge.trans.rmd.services.lhr.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthRuleFiringRequestType", propOrder = {
    "locomotiveId",
    "healthRules"
})
@XmlRootElement
public class HealthRuleFiringRequestType {
	@XmlElement(required = true)
	protected long locomotiveId;
	@XmlElement(required = true)
	protected List<HealthRuleRequest> healthRules;

	public long getLocomotiveId() {
		return locomotiveId;
	}

	public void setLocomotiveId(long locomotiveId) {
		this.locomotiveId = locomotiveId;
	}
	public List<HealthRuleRequest> getHealthRules() {
		return healthRules;
	}

	public void setHealthRules(List<HealthRuleRequest> healthRules) {
		this.healthRules = healthRules;
	}
}
