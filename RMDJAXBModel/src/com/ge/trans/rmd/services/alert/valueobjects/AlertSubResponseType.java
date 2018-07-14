package com.ge.trans.rmd.services.alert.valueobjects;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertSubResponseType", propOrder = {
    "alertNotifyType",
    "customerId",
    "enabledFlag",
    "ruleTitle",
    "originalId"
})
public class AlertSubResponseType {
	 protected ArrayList<String> alertNotifyType;
	 protected String customerId;
	 protected boolean enabledFlag;
	 protected String ruleTitle;
	 protected String originalId;
	 
	public ArrayList<String> getAlertNotifyType() {
		return alertNotifyType;
	}
	public void setAlertNotifyType(ArrayList<String> alertNotifyType) {
		this.alertNotifyType = alertNotifyType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public boolean isEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(boolean enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getRuleTitle() {
		return ruleTitle;
	}
	public void setRuleTitle(String ruleTitle) {
		this.ruleTitle = ruleTitle;
	}
	public String getOriginalId() {
		return originalId;
	}
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}
}
