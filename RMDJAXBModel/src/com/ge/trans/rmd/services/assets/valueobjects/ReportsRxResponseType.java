package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsRxResponseType", propOrder = { "rxObjid","rxTitle","urgency", "rxDeliverDate", "rxOpenTime", "rxClosedDate", "timezone", "truckId", "estmRepairTime", "locoImpact", "caseId"})

@XmlRootElement
public class ReportsRxResponseType {
	private String rxObjid;
    private String rxTitle;
    private String urgency;
    private XMLGregorianCalendar rxDeliverDate;
    private String rxOpenTime;
    private XMLGregorianCalendar rxClosedDate;
    private String timezone;
    private String truckId;
    private String estmRepairTime;
    private String locoImpact;
    private String caseId;
	public String getRxObjid() {
		return rxObjid;
	}
	public void setRxObjid(String rxObjid) {
		this.rxObjid = rxObjid;
	}
	public String getRxTitle() {
		return rxTitle;
	}
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public XMLGregorianCalendar getRxDeliverDate() {
		return rxDeliverDate;
	}
	public void setRxDeliverDate(XMLGregorianCalendar rxDeliverDate) {
		this.rxDeliverDate = rxDeliverDate;
	}
	public String getRxOpenTime() {
		return rxOpenTime;
	}
	public void setRxOpenTime(String rxOpenTime) {
		this.rxOpenTime = rxOpenTime;
	}
	public XMLGregorianCalendar getRxClosedDate() {
		return rxClosedDate;
	}
	public void setRxClosedDate(XMLGregorianCalendar rxClosedDate) {
		this.rxClosedDate = rxClosedDate;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getTruckId() {
		return truckId;
	}
	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
	public String getEstmRepairTime() {
		return estmRepairTime;
	}
	public void setEstmRepairTime(String estmRepairTime) {
		this.estmRepairTime = estmRepairTime;
	}
	public String getLocoImpact() {
		return locoImpact;
	}
	public void setLocoImpact(String locoImpact) {
		this.locoImpact = locoImpact;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}	
}
