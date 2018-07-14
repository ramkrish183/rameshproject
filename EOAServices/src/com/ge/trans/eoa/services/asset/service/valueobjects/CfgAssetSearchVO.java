package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;

public class CfgAssetSearchVO {
	String customer;
	String fleet;
	String model;
	String assetGrpName;
	String onboardSWVersion;
	String fromVersion;
	String toVersion;
	String searchType;
	
	List<String> assetNumbers;
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getFleet() {
		return fleet;
	}
	public void setFleet(String fleet) {
		this.fleet = fleet;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAssetGrpName() {
		return assetGrpName;
	}
	public void setAssetGrpName(String assetGrpName) {
		this.assetGrpName = assetGrpName;
	}
	public String getOnboardSWVersion() {
		return onboardSWVersion;
	}
	public void setOnboardSWVersion(String onboardSWVersion) {
		this.onboardSWVersion = onboardSWVersion;
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
	public List<String> getAssetNumbers() {
		return assetNumbers;
	}
	public void setAssetNumbers(List<String> assetNumbers) {
		this.assetNumbers = assetNumbers;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}
