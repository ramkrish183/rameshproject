package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BOMRequestType", propOrder = { "objid", "configId",
		"configItem", "value", "status", "bomObjid", "parameterObjid",
		"parameterNumber", "parameterDesc", "action", "arlBOMRequestType",
		"userName","notificationFlag"

})
@XmlRootElement
public class BOMRequestType {

	@XmlElement(required = true)
	protected String configId;
	@XmlElement(required = true)
	protected String objid;
	@XmlElement(required = true)
	protected String configItem;
	@XmlElement(required = true)
	protected String value;
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
	@XmlElement(required = true)
	protected String action;
	@XmlElement(required = true)
	protected String userName;
	@XmlElement(required = true)
	protected List<BOMRequestType> arlBOMRequestType;
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

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<BOMRequestType> getArlBOMRequestType() {
		return arlBOMRequestType;
	}

	public void setArlBOMRequestType(List<BOMRequestType> arlBOMRequestType) {
		this.arlBOMRequestType = arlBOMRequestType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
