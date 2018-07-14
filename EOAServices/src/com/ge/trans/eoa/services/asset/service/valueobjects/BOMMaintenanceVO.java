package com.ge.trans.eoa.services.asset.service.valueobjects;

public class BOMMaintenanceVO {

	private String objid;
	private String configList;
	private String configItem;
	private String value;
	private String parameter;
	private String status;
	private String bomObjid;
	private String parameterObjid;
	private String parameterNumber;
	private String parameterDesc;
	private String action;
	private String configId;
	private String userName;
	private String notificationFlag;
	

	public String getNotificationFlag() {
		return notificationFlag;
	}

	public void setNotificationFlag(String notificationFlag) {
		this.notificationFlag = notificationFlag;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
