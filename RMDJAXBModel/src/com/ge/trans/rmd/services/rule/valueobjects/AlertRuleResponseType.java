package com.ge.trans.rmd.services.rule.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Jav class for AlertRule ResponseType
 * 
 * @author 212556286
 * 
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertRuleResponseType", propOrder = { "fireId", "ruleId",
        "title", "family", "version", "vehObjId", "vehHdr", "vehSerialNo",
        "createdOn", "diagService", "alertStatus","strCustomer" })
@XmlRootElement
public class AlertRuleResponseType {

    @XmlElement(required = true, name = "ruleId")
    protected String ruleId;

    @XmlElement(required = true, name = "title")
    protected String title;

    @XmlElement(required = true, name = "family")
    protected String family;

    @XmlElement(required = true, name = "version")
    protected String version;

    @XmlElement(required = true, name = "vehObjId")
    protected String vehObjId;

    @XmlElement(required = true, name = "vehHdr")
    protected String vehHdr;

    @XmlElement(required = true, name = "vehSerialNo")
    protected String vehSerialNo;

    @XmlElement(required = true, name = "createdOn")
    protected String createdOn;

    @XmlElement(required = true, name = "diagService")
    protected String diagService;

    @XmlElement(required = true, name = "alertStatus")
    protected String alertStatus;

    @XmlElement(required = true, name = "fireId")
    protected String fireId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVehObjId() {
        return vehObjId;
    }

    public void setVehObjId(String vehObjId) {
        this.vehObjId = vehObjId;
    }

    public String getVehHdr() {
        return vehHdr;
    }

    public void setVehHdr(String vehHdr) {
        this.vehHdr = vehHdr;
    }

    public String getVehSerialNo() {
        return vehSerialNo;
    }

    public void setVehSerialNo(String vehSerialNo) {
        this.vehSerialNo = vehSerialNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDiagService() {
        return diagService;
    }

    public void setDiagService(String diagService) {
        this.diagService = diagService;
    }

    public String getAlertStatus() {
        return alertStatus;
    }

    public void setAlertStatus(String alertStatus) {
        this.alertStatus = alertStatus;
    }

    public String getFireId() {
        return fireId;
    }

    public void setFireId(String fireId) {
        this.fireId = fireId;
    }
}
