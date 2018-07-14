package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RxRepairResponseType", propOrder = { "rxCaseID", "customerId","vehicleNo","caseSuccess","repairCode","missCode","goodFdbk","rxTitle","rxUrgency",
		"caseType","rxOpenDate","rxCloseDate","rxFeedback","repairCodeDesc"})
@XmlRootElement
public class RxRepairResponseType {
	protected String rxCaseID;
	protected String customerId;
	protected String vehicleNo;
	protected String caseSuccess;
	protected String repairCode;
	protected String missCode;
	protected String goodFdbk;
	protected String rxTitle;
	protected String rxUrgency;
	protected String caseType;
	protected String rxOpenDate;
	protected String rxCloseDate;
	protected String rxFeedback;
	protected String repairCodeDesc;
	public String getRxCaseID() {
		return rxCaseID;
	}
	public void setRxCaseID(String rxCaseID) {
		this.rxCaseID = rxCaseID;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getCaseSuccess() {
		return caseSuccess;
	}
	public void setCaseSuccess(String caseSuccess) {
		this.caseSuccess = caseSuccess;
	}
	public String getRepairCode() {
		return repairCode;
	}
	public void setRepairCode(String repairCode) {
		this.repairCode = repairCode;
	}
	public String getMissCode() {
		return missCode;
	}
	public void setMissCode(String missCode) {
		this.missCode = missCode;
	}
	public String getGoodFdbk() {
		return goodFdbk;
	}
	public void setGoodFdbk(String goodFdbk) {
		this.goodFdbk = goodFdbk;
	}
	public String getRxTitle() {
		return rxTitle;
	}
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	public String getRxUrgency() {
		return rxUrgency;
	}
	public void setRxUrgency(String rxUrgency) {
		this.rxUrgency = rxUrgency;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getRxOpenDate() {
		return rxOpenDate;
	}
	public void setRxOpenDate(String rxOpenDate) {
		this.rxOpenDate = rxOpenDate;
	}
	public String getRxCloseDate() {
		return rxCloseDate;
	}
	public void setRxCloseDate(String rxCloseDate) {
		this.rxCloseDate = rxCloseDate;
	}
	public String getRxFeedback() {
		return rxFeedback;
	}
	public void setRxFeedback(String rxFeedback) {
		this.rxFeedback = rxFeedback;
	}
	public String getRepairCodeDesc() {
		return repairCodeDesc;
	}
	public void setRepairCodeDesc(String repairCodeDesc) {
		this.repairCodeDesc = repairCodeDesc;
	}
	}
	
	


