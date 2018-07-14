package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

/**
 * This pojo contains all the possible values to the corresponding templates
 * event based
 */

public class LDVREventType {

	private String preTriggerAndPostTrigger;
	private List<LDVRMPType> mps;

	public String getPreTriggerAndPostTrigger() {
		return preTriggerAndPostTrigger;
	}

	public void setPreTriggerAndPostTrigger(String preTriggerAndPostTrigger) {
		this.preTriggerAndPostTrigger = preTriggerAndPostTrigger;
	}

	public List<LDVRMPType> getMps() {
		return mps;
	}

	public void setMps(List<LDVRMPType> mps) {
		this.mps = mps;
	}

}
