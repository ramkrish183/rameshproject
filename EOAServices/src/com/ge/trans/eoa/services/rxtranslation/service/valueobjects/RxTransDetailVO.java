package com.ge.trans.eoa.services.rxtranslation.service.valueobjects;

import java.util.ArrayList;
import java.util.List;

public class RxTransDetailVO {

	private String rxStatus;
	private String transStatus;
	private String lastModifiedBy;
	private String lastModifiedOn;
	private String approvedBy;
	private String approvedOn;
	private String rxTitle;
	private String rxTransTitle;
	private String language;
	private String rxObjid;
	private List<RxTransHistVO> arlRxTransHistVO=new ArrayList<RxTransHistVO>();
	private List<RxTransTaskDetailVO> arlRxTransTaskDetailVO=new ArrayList<RxTransTaskDetailVO>();
	private String revisionNote;
	private String transNewStatus;
	private String strUrgRepair;
	private String strEstmTimeRepair;
	private String strSelAssetImp;
	private String rxDescription;
	private String transDescription;
	
	public List<RxTransTaskDetailVO> getArlRxTransTaskDetailVO() {
		return arlRxTransTaskDetailVO;
	}
	public void setArlRxTransTaskDetailVO(
			List<RxTransTaskDetailVO> arlRxTransTaskDetailVO) {
		this.arlRxTransTaskDetailVO = arlRxTransTaskDetailVO;
	}
	public List<RxTransHistVO> getArlRxTransHistVO() {
		return arlRxTransHistVO;
	}
	public void setArlRxTransHistVO(List<RxTransHistVO> arlRxTransHistVO) {
		this.arlRxTransHistVO = arlRxTransHistVO;
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
	public String getTransNewStatus() {
		return transNewStatus;
	}
	public void setTransNewStatus(String transNewStatus) {
		this.transNewStatus = transNewStatus;
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
