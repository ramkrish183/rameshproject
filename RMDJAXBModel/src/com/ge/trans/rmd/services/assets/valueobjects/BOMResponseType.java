package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BOMResponseType", propOrder = { "objid", "configList",
		"configItem", "value", "parameter", "status", "bomObjid",
		"parameterObjid", "parameterNumber", "parameterDesc",
		"notificationFlag"

})
@XmlRootElement
public class BOMResponseType {

	@XmlElement(required = true)
	protected String configList;
	@XmlElement(required = true)
	protected String objid;
	@XmlElement(required = true)
	protected String configItem;
	@XmlElement(required = true)
	protected String value;
	@XmlElement(required = true)
	protected String parameter;
	@XmlElement(required = true)
	protected String status;
	@XmlElement(required = true)
	protected String bomObjid;
	@XmlElement(required = true)
	protected String parameterObjid;
	@XmlElement(required = true)
	protected String parameterNumber;
	@XmlElement(required = true)
	protected String parameterDesc;
	@XmlElement
	protected String notificationFlag;

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getConfigList() {
		return configList;
	}

	public void setConfigList(String configList) {
		this.configList = configList;
	}

	public String getConfigItem() {
		return configItem;
	}

	public void setConfigItem(String configItem) {
		this.configItem = configItem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBomObjid() {
		return bomObjid;
	}

	public void setBomObjid(String bomObjid) {
		this.bomObjid = bomObjid;
	}

	public String getParameterObjid() {
		return parameterObjid;
	}

	public void setParameterObjid(String parameterObjid) {
		this.parameterObjid = parameterObjid;
	}

	public String getParameterNumber() {
		return parameterNumber;
	}

	public void setParameterNumber(String parameterNumber) {
		this.parameterNumber = parameterNumber;
	}

	public String getParameterDesc() {
		return parameterDesc;
	}

	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}

}
