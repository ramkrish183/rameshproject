package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addEditEDPRequestType", propOrder = { "tempObjId",
		"ctrlCfgObjId", "ctrlCfgName", "cfgFileName", "paramObjId",
		"addedParamObjId", "removedParamObjId", "templateNo", "versionNo",
		"status", "title", "whatNew", "userName" })
@XmlRootElement
public class AddEditEDPRequestType {

	@XmlElement(required = true)
	protected String tempObjId;

	@XmlElement(required = true)
	protected String ctrlCfgObjId;

	@XmlElement(required = true)
	protected String ctrlCfgName;

	@XmlElement(required = true)
	protected String cfgFileName;

	@XmlElement(required = true)
	protected String paramObjId;

	@XmlElement(required = true)
	protected String addedParamObjId;

	@XmlElement(required = true)
	protected String removedParamObjId;

	@XmlElement(required = true)
	protected String templateNo;

	@XmlElement(required = true)
	protected String versionNo;

	@XmlElement(required = true)
	protected String status;

	@XmlElement(required = true)
	protected String title;

	@XmlElement(required = true)
	protected String whatNew;

	@XmlElement(required = true)
	protected String userName;

	public String getTempObjId() {
		return this.tempObjId;
	}

	public void setTempObjId(String tempObjId) {
		this.tempObjId = tempObjId;
	}

	public String getCtrlCfgObjId() {
		return this.ctrlCfgObjId;
	}

	public void setCtrlCfgObjId(String ctrlCfgObjId) {
		this.ctrlCfgObjId = ctrlCfgObjId;
	}

	public String getCtrlCfgName() {
		return this.ctrlCfgName;
	}

	public void setCtrlCfgName(String ctrlCfgName) {
		this.ctrlCfgName = ctrlCfgName;
	}

	public String getCfgFileName() {
		return this.cfgFileName;
	}

	public void setCfgFileName(String cfgFileName) {
		this.cfgFileName = cfgFileName;
	}

	public String getParamObjId() {
		return this.paramObjId;
	}

	public void setParamObjId(String paramObjId) {
		this.paramObjId = paramObjId;
	}

	public String getAddedParamObjId() {
		return addedParamObjId;
	}

	public void setAddedParamObjId(String addedParamObjId) {
		this.addedParamObjId = addedParamObjId;
	}

	public String getRemovedParamObjId() {
		return removedParamObjId;
	}

	public void setRemovedParamObjId(String removedParamObjId) {
		this.removedParamObjId = removedParamObjId;
	}

	public String getTemplateNo() {
		return this.templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWhatNew() {
		return this.whatNew;
	}

	public void setWhatNew(String whatNew) {
		this.whatNew = whatNew;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}