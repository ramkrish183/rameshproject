package com.ge.trans.rmd.services.rule.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertFiringResponseType", propOrder = { "ruleId", "ruleTitle",
        "groupName", "assetNo", "vehicle", "processedOn",
        "firing2LastDataRecord", "isAlertSent", "ruleParmData","strCustomer" })
@XmlRootElement
public class AlertFiringDetailsResponseType {

    @XmlElement(required = true, name = "RuleId")
    protected String ruleId;

    @XmlElement(required = true, name = "RuleTitle")
    protected String ruleTitle;

    @XmlElement(required = true, name = "Vehicle")
    protected String vehicle;

    @XmlElement(required = true, name = "ProcessedOn")
    protected String processedOn;

    @XmlElement(required = true, name = "IsAlertSent")
    protected boolean isAlertSent;

    @XmlElement(required = true, name = "firing2LastDataRecord")
    protected String firing2LastDataRecord;

    @XmlElement(required = true, name = "GroupName")
    protected String groupName;

    @XmlElement(required = true, name = "AssetNo")
    protected String assetNo;

    @XmlElementWrapper(name = "ruleParmData")
    @XmlElement(name = "Parm")
    protected List<AlertRuleParmResponseType> ruleParmData = new ArrayList<AlertRuleParmResponseType>();
    @XmlElement(required = true, name = "strCustomer")
    protected String strCustomer;
    
    public String getStrCustomer() {
		return strCustomer;
	}

	public void setStrCustomer(String strCustomer) {
		this.strCustomer = strCustomer;
	}

	public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(String processedOn) {
        this.processedOn = processedOn;
    }

    public boolean isAlertSent() {
        return isAlertSent;
    }

    public void setAlertSent(boolean isAlertSent) {
        this.isAlertSent = isAlertSent;
    }

    public List<AlertRuleParmResponseType> getRuleParmData() {
        return ruleParmData;
    }

    public void setRuleParmData(List<AlertRuleParmResponseType> ruleParmData) {
        this.ruleParmData = ruleParmData;
    }

    public String getFiring2LastDataRecord() {
        return firing2LastDataRecord;
    }

    public void setFiring2LastDataRecord(String firing2LastDataRecord) {
        this.firing2LastDataRecord = firing2LastDataRecord;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

}
