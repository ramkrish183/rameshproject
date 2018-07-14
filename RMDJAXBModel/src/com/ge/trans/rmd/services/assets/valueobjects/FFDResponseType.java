package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FFDResponseType", propOrder = { "configId", "template",
		"version", "title", "status", "parameterTitle", "operatorFrom",
		"opertorTo", "valueFrom", "valueTo", "conjunction",
		"configValue","parameterObjId","objId"

})
@XmlRootElement
public class FFDResponseType {

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
	protected String parameterTitle;
	@XmlElement(required = true)
	protected String operatorFrom;
	@XmlElement(required = true)
	protected String opertorTo;
	@XmlElement(required = true)
	protected String valueFrom;
	@XmlElement(required = true)
	protected String valueTo;
	@XmlElement(required = true)
	protected String conjunction;
	@XmlElement(required = true)
	protected String configValue;
	@XmlElement(required = true)
	protected String parameterObjId;
	@XmlElement(required = true)
	protected String objId;
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
	public String getParameterTitle() {
		return parameterTitle;
	}
	public void setParameterTitle(String parameterTitle) {
		this.parameterTitle = parameterTitle;
	}
	public String getOperatorFrom() {
		return operatorFrom;
	}
	public void setOperatorFrom(String operatorFrom) {
		this.operatorFrom = operatorFrom;
	}
	public String getOpertorTo() {
		return opertorTo;
	}
	public void setOpertorTo(String opertorTo) {
		this.opertorTo = opertorTo;
	}
	public String getValueFrom() {
		return valueFrom;
	}
	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}
	public String getValueTo() {
		return valueTo;
	}
	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}
	public String getConjunction() {
		return conjunction;
	}
	public void setConjunction(String conjunction) {
		this.conjunction = conjunction;
	}
	public String getConfigValue() {
		return configValue;
	}
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	public String getParameterObjId() {
		return parameterObjId;
	}
	public void setParameterObjId(String parameterObjId) {
		this.parameterObjId = parameterObjId;
	}
	public String getObjId() {
		return objId;
	}
	public void setObjId(String objId) {
		this.objId = objId;
	}
	
	
}