package com.ge.trans.eoa.services.asset.service.valueobjects;

public class ApplyEFICfgVO {

	private String customer;
	private String assetGrpName;
	private String fromAssetNumber;
	private String toAssetNumber;
	private String fromVersion;
	private String toVersion;
	private String userName;
	private boolean isChangeVersion;
	private VerifyCfgTemplateVO objVerifyCfgTemplateVO;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public VerifyCfgTemplateVO getObjVerifyCfgTemplateVO() {
		return objVerifyCfgTemplateVO;
	}
	public void setObjVerifyCfgTemplateVO(VerifyCfgTemplateVO objVerifyCfgTemplateVO) {
		this.objVerifyCfgTemplateVO = objVerifyCfgTemplateVO;
	}
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

}
