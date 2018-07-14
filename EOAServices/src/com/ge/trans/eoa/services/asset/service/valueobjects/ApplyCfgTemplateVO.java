package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;

public class ApplyCfgTemplateVO {

	private List<CfgAssetSearchVO> arlCfgAssetSearchVOs;
	private List<VerifyCfgTemplateVO> cfgTemplateList;
	private String userName;
	private String ctrlCfgObjId;
	private String searchType;
	private String ctrlCfgName;
	private boolean isDeleteConfig;

	public boolean isDeleteConfig() {
		return isDeleteConfig;
	}

	public void setDeleteConfig(boolean isDeleteConfig) {
		this.isDeleteConfig = isDeleteConfig;
	}

	public String getCtrlCfgName() {
		return ctrlCfgName;
	}

	public void setCtrlCfgName(String ctrlCfgName) {
		this.ctrlCfgName = ctrlCfgName;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getCtrlCfgObjId() {
		return ctrlCfgObjId;
	}

	public void setCtrlCfgObjId(String ctrlCfgObjId) {
		this.ctrlCfgObjId = ctrlCfgObjId;
	}

	public List<CfgAssetSearchVO> getArlCfgAssetSearchVOs() {
		return arlCfgAssetSearchVOs;
	}

	public void setArlCfgAssetSearchVOs(
			List<CfgAssetSearchVO> arlCfgAssetSearchVOs) {
		this.arlCfgAssetSearchVOs = arlCfgAssetSearchVOs;
	}

	public List<VerifyCfgTemplateVO> getCfgTemplateList() {
		return cfgTemplateList;
	}

	public void setCfgTemplateList(List<VerifyCfgTemplateVO> cfgTemplateList) {
		this.cfgTemplateList = cfgTemplateList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
