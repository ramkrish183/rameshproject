package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FRDResponseType", propOrder = { "configId", "template",
		"version", "title", "status", "faultSource", "faultCodeFrom",
		"faultCodeTo", "subIdFrom", "subIdTo", "edpTemplate",
		"preValue","postValue","biasValue","configValue","objId",
		"faultStart","faultEnd","controllerName"

})

@XmlRootElement
public class FRDResponseType {

	@XmlElement(required = true)
	protected String configId;
	@XmlElement(required = true)
	protected String template;
	@XmlElement(required = true)
	protected String version;
	@XmlElement(required = true)
	protected String title;
	@XmlElement(required = true)
	protected String status;
	@XmlElement(required = true)
	protected String faultSource;
	@XmlElement(required = true)
	protected String faultCodeFrom;
	@XmlElement(required = true)
	protected String faultCodeTo;
	@XmlElement(required = true)
	protected String subIdFrom;
	@XmlElement(required = true)
	protected String subIdTo;
	@XmlElement(required = true)
	protected String edpTemplate;
	@XmlElement(required = true)
	protected String postValue;
	@XmlElement(required = true)
	protected String preValue;
	@XmlElement(required = true)
	protected String biasValue;
	@XmlElement(required = true)
	protected String configValue;
	@XmlElement(required = true)
	protected String objId;
	@XmlElement(required = true)
	protected String faultStart;
	@XmlElement(required = true)
	protected String faultEnd;
	@XmlElement(required = true)
	protected String controllerName;
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFaultSource() {
		return faultSource;
	}
	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}
	public String getFaultCodeFrom() {
		return faultCodeFrom;
	}
	public void setFaultCodeFrom(String faultCodeFrom) {
		this.faultCodeFrom = faultCodeFrom;
	}
	public String getFaultCodeTo() {
		return faultCodeTo;
	}
	public void setFaultCodeTo(String faultCodeTo) {
		this.faultCodeTo = faultCodeTo;
	}
	public String getSubIdFrom() {
		return subIdFrom;
	}
	public void setSubIdFrom(String subIdFrom) {
		this.subIdFrom = subIdFrom;
	}
	public String getSubIdTo() {
		return subIdTo;
	}
	public void setSubIdTo(String subIdTo) {
		this.subIdTo = subIdTo;
	}
	public String getEdpTemplate() {
		return edpTemplate;
	}
	public void setEdpTemplate(String edpTemplate) {
		this.edpTemplate = edpTemplate;
	}
	public String getPostValue() {
		return postValue;
	}
	public void setPostValue(String postValue) {
		this.postValue = postValue;
	}
	public String getPreValue() {
		return preValue;
	}
	public void setPreValue(String preValue) {
		this.preValue = preValue;
	}
	public String getBiasValue() {
		return biasValue;
	}
	public void setBiasValue(String biasValue) {
		this.biasValue = biasValue;
	}
	public String getConfigValue() {
		return configValue;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	public String getFaultStart() {
		return faultStart;
	}
	public void setFaultStart(String faultStart) {
		this.faultStart = faultStart;
	}
	public String getFaultEnd() {
		return faultEnd;
	}
	public void setFaultEnd(String faultEnd) {
		this.faultEnd = faultEnd;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
}