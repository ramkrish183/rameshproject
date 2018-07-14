package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.List;

public class LocomotivesResponseVO {
	 
	protected long locomotiveId;
	protected List<HealthRulesFiringVO> healthRules;
	
	public long getLocomotiveId() {
		return locomotiveId;
	}
	public void setLocomotiveId(long locomotiveId) {
		this.locomotiveId = locomotiveId;
	}
	public List<HealthRulesFiringVO> getHealthRules() {
		return healthRules;
	}
	public void setHealthRules(List<HealthRulesFiringVO> healthRules) {
		this.healthRules = healthRules;
	}
}
