package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.List;

public class LocomotivesRequestVO {
	 
	protected long locomotiveId;
	protected List<Long> healthRules;
	
	public long getLocomotiveId() {
		return locomotiveId;
	}
	public void setLocomotiveId(long locomotiveId) {
		this.locomotiveId = locomotiveId;
	}
	public List<Long> getHealthRules() {
		return healthRules;
	}
	public void setHealthRules(List<Long> healthRules) {
		this.healthRules = healthRules;
	}
	
}
