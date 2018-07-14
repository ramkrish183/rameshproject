package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eDPHeaderResponseType", propOrder = { "tempObjId",
		"ctrlCfgObjId", "cfgFileName", "templateNo", "vesrionNo","title","ctrlCfgName", "status" })
@XmlRootElement
public class EDPHeaderResponseType {

	@XmlElement(required = true)
	protected String tempObjId;
	@XmlElement(required = true)
	protected String ctrlCfgObjId;
	@XmlElement(required = true)
	protected String cfgFileName;
	@XmlElement(required = true)
	protected String templateNo;
	@XmlElement(required = true)
	protected String vesrionNo;
	@XmlElement(required = true)
	protected String title;
	@XmlElement(required = true)
	protected String ctrlCfgName;
	@XmlElement(required = true)
	protected String status;
	
	public String getTempObjId() {
		return tempObjId;
	}
	public void setTempObjId(String tempObjId) {
		this.tempObjId = tempObjId;
	}
	public String getCtrlCfgObjId() {
		return ctrlCfgObjId;
	}
	public void setCtrlCfgObjId(String ctrlCfgObjId) {
		this.ctrlCfgObjId = ctrlCfgObjId;
	}
	public String getCfgFileName() {
		return cfgFileName;
	}
	public void setCfgFileName(String cfgFileName) {
		this.cfgFileName = cfgFileName;
	}
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getVesrionNo() {
		return vesrionNo;
	}
	public void setVesrionNo(String vesrionNo) {
		this.vesrionNo = vesrionNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtrlCfgName() {
		return ctrlCfgName;
	}
	public void setCtrlCfgName(String ctrlCfgName) {
		this.ctrlCfgName = ctrlCfgName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
