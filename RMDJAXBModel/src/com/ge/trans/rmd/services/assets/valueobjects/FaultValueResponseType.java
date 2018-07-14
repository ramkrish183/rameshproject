package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faultValueResponseType", propOrder = { "preValue", "postValue",
		"biasValue","parameterTitle","edpTemplate","faultSource","objId","controllerName","cfgVersion","cfgDesc","cfgDetailDesc","status"
})
@XmlRootElement
public class FaultValueResponseType {

	@XmlElement(required = true)
	protected String preValue;
	@XmlElement(required = true)
	protected String postValue;
	@XmlElement(required = true)
	protected String parameterTitle;
	@XmlElement(required = true)
	protected String biasValue;
	@XmlElement(required = true)
	protected String edpTemplate;
	@XmlElement(required = true)
	protected String faultSource;
	@XmlElement(required = true)
	protected String objId;
	@XmlElement(required = true)
	protected String controllerName;
	@XmlElement(required = true)
	protected String cfgVersion;
	@XmlElement(required = true)
	protected String cfgDesc;
	@XmlElement(required = true)
	protected String cfgDetailDesc;
	@XmlElement(required = true)
	protected String status;
	public String getPreValue() {
		return preValue;
	}
	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}
	public String getPostValue() {
		return postValue;
	}
	public void setPostValue(String postValue) {
		this.postValue = postValue;
	}
	public String getBiasValue() {
		return biasValue;
	}
	public void setBiasValue(String biasValue) {
		this.biasValue = biasValue;
	}
	public String getEdpTemplate() {
		return edpTemplate;
	}
	public void setEdpTemplate(String edpTemplate) {
		this.edpTemplate = edpTemplate;
	}
	public String getParameterTitle() {
		return parameterTitle;
	}
	public void setParameterTitle(String parameterTitle) {
		this.parameterTitle = parameterTitle;
	}
	public String getFaultSource() {
		return faultSource;
	}
	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public String getCfgVersion() {
		return cfgVersion;
	}
	public void setCfgVersion(String cfgVersion) {
		this.cfgVersion = cfgVersion;
	}
	public String getCfgDesc() {
		return cfgDesc;
	}
	public void setCfgDesc(String cfgDesc) {
		this.cfgDesc = cfgDesc;
	}
	public String getCfgDetailDesc() {
		return cfgDetailDesc;
	}
	public void setCfgDetailDesc(String cfgDetailDesc) {
		this.cfgDetailDesc = cfgDetailDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}