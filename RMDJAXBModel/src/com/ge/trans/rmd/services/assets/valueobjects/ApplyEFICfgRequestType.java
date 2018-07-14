package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.assets.valueobjects.VerifyCfgTemplatesRequestType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "applyEFICfgRequestType", propOrder = {
		"customer",
		"assetGrpName",
		"fromAssetNumber",
		"toAssetNumber",
		"fromVersion",
		"toVersion",
		"isChangeVersion",
		"userName",
		"objVerifyCfgTemplatesRequestType"
})
@XmlRootElement
public class ApplyEFICfgRequestType {

	@XmlElement(required = true)
	protected String customer;
	@XmlElement(required = true)
	protected String assetGrpName;
	@XmlElement(required = true)
	protected String fromAssetNumber;
	@XmlElement(required = true)
	protected String toAssetNumber;
	
	@XmlElement(required = true)
	protected String fromVersion;
	@XmlElement(required = true)
	protected String toVersion;
	@XmlElement(required = true)
	protected boolean isChangeVersion;
	@XmlElement(required = true)
	protected String userName;
	@XmlElement(required = true)
	protected VerifyCfgTemplatesRequestType objVerifyCfgTemplatesRequestType;

	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAssetGrpName() {
		return assetGrpName;
	}
	public void setAssetGrpName(String assetGrpName) {
		this.assetGrpName = assetGrpName;
	}
	public String getFromAssetNumber() {
		return fromAssetNumber;
	}
	public void setFromAssetNumber(String fromAssetNumber) {
		this.fromAssetNumber = fromAssetNumber;
	}
	public String getToAssetNumber() {
		return toAssetNumber;
	}
	public void setToAssetNumber(String toAssetNumber) {
		this.toAssetNumber = toAssetNumber;
	}
	public String getFromVersion() {
		return fromVersion;
	}
	public void setFromVersion(String fromVersion) {
		this.fromVersion = fromVersion;
	}
	public String getToVersion() {
		return toVersion;
	}
	public void setToVersion(String toVersion) {
		this.toVersion = toVersion;
	}
	public boolean isChangeVersion() {
		return isChangeVersion;
	}
	public void setChangeVersion(boolean isChangeVersion) {
		this.isChangeVersion = isChangeVersion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public VerifyCfgTemplatesRequestType getObjVerifyCfgTemplatesRequestType() {
		return objVerifyCfgTemplatesRequestType;
	}
	public void setObjVerifyCfgTemplatesRequestType(
			VerifyCfgTemplatesRequestType objVerifyCfgTemplatesRequestType) {
		this.objVerifyCfgTemplatesRequestType = objVerifyCfgTemplatesRequestType;
	}
	
}
