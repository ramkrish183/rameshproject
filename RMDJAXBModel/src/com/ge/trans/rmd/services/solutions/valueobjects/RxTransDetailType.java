package com.ge.trans.rmd.services.solutions.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxTransDetailType", propOrder = { "rxStatus", "transStatus", "lastModifiedBy", "lastModifiedOn", "approvedBy", "approvedOn", "rxTitle",
        "rxTransTitle", "language", "rxObjid", "rxTransHist","rxTransTask","revisionNote","newTransStatus", "strUrgRepair", "strEstmTimeRepair","strSelAssetImp","rxDescription","transDescription" })
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RxTransDetailType {
	@XmlElement(required = true)
	protected String rxStatus;
	@XmlElement(required = true)
	protected String transStatus;
	@XmlElement(required = true)
	protected String lastModifiedBy;
	@XmlElement(required = true)
	protected String lastModifiedOn;
	@XmlElement(required = true)
	protected String approvedBy;
	@XmlElement(required = true)
	protected String approvedOn;
	@XmlElement(required = true)
	protected String rxTitle;
	@XmlElement(required = true)
	protected String rxTransTitle;
	@XmlElement(required = true)
	protected String language;
	@XmlElement(required = true)
	protected String rxObjid;
	@XmlElement(required = true)
	protected List<RxTransHistRequestType> rxTransHist=new ArrayList<RxTransHistRequestType>();
	@XmlElement(required = true)
	protected List<RxTransTaskDetailType> rxTransTask=new ArrayList<RxTransTaskDetailType>();	
	@XmlElement(required = true)
	protected String revisionNote;
	@XmlElement(required = true)
	protected String newTransStatus;
	@XmlElement(required = true)
	protected String strUrgRepair;
	@XmlElement(required = true)
	protected String strEstmTimeRepair;
	@XmlElement(required = true)
	protected String strSelAssetImp;
	@XmlElement(required = true)
	protected String rxDescription;
	@XmlElement(required = true)
	protected String transDescription;
	
	public List<RxTransHistRequestType> getRxTransHist() {
		return rxTransHist;
	}
	public void setRxTransHist(List<RxTransHistRequestType> rxTransHist) {
		this.rxTransHist = rxTransHist;
	}
	public List<RxTransTaskDetailType> getRxTransTask() {
		return rxTransTask;
	}
	public void setRxTransTask(List<RxTransTaskDetailType> rxTransTask) {
		this.rxTransTask = rxTransTask;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRxObjid() {
		return rxObjid;
	}
	public void setRxObjid(String rxObjid) {
		this.rxObjid = rxObjid;
	}
	
	public String getRxStatus() {
		return rxStatus;
	}
	public void setRxStatus(String rxStatus) {
		this.rxStatus = rxStatus;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}
	public String getRxTitle() {
		return rxTitle;
	}
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	public String getRxTransTitle() {
		return rxTransTitle;
	}
	public void setRxTransTitle(String rxTransTitle) {
		this.rxTransTitle = rxTransTitle;
	}
	public String getRevisionNote() {
		return revisionNote;
	}
	public void setRevisionNote(String revisionNote) {
		this.revisionNote = revisionNote;
	}
	public String getNewTransStatus() {
		return newTransStatus;
	}
	public void setNewTransStatus(String newTransStatus) {
		this.newTransStatus = newTransStatus;
	}
	public String getStrUrgRepair() {
		return strUrgRepair;
	}
	public void setStrUrgRepair(String strUrgRepair) {
		this.strUrgRepair = strUrgRepair;
	}
	public String getStrEstmTimeRepair() {
		return strEstmTimeRepair;
	}
	public void setStrEstmTimeRepair(String strEstmTimeRepair) {
		this.strEstmTimeRepair = strEstmTimeRepair;
	}
	public String getStrSelAssetImp() {
		return strSelAssetImp;
	}
	public void setStrSelAssetImp(String strSelAssetImp) {
		this.strSelAssetImp = strSelAssetImp;
	}
	public String getRxDescription() {
		return rxDescription;
	}
	public void setRxDescription(String rxDescription) {
		this.rxDescription = rxDescription;
	}
	public String getTransDescription() {
		return transDescription;
	}
	public void setTransDescription(String transDescription) {
		this.transDescription = transDescription;
	}
}
