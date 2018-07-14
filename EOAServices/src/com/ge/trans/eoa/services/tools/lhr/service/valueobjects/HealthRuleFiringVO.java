package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.Date;

public class HealthRuleFiringVO {  

	private long lmsLocoId;
	private long ruleId;
	private boolean hasFired = false;
	private Date lastRunDate = null;

	public HealthRuleFiringVO(long ruleId, boolean fired, Date runDate) {
		this.ruleId = ruleId;
		this.hasFired = fired;
		this.lastRunDate = runDate;
	}

	public HealthRuleFiringVO(long locoId, long ruleId, boolean fired,
			Date runDate) {
		this.lmsLocoId = locoId;
		this.ruleId = ruleId;
		this.hasFired = fired;
		this.lastRunDate = runDate;
	}

	public long getLmsLocoId() {
		return lmsLocoId;
	}

	public void setLmsLocoId(long lmsLocoId) {
		this.lmsLocoId = lmsLocoId;
	}

	public long getRuleId() {
		return ruleId;
	}

	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}

	public boolean isHasFired() {
		return hasFired;
	}

	public void setHasFired(boolean hasFired) {
		this.hasFired = hasFired;
	}

	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
}
