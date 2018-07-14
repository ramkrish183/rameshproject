package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cfgAssetSearchRequestType", propOrder = {
		"customer",
		"fleet",
		"model",
		"assetGrpName",
		"onboardSWVersion",
		"fromVersion",
		"toVersion",
		"assetNumbers",
		"searchType"
})

@XmlRootElement
public class CfgAssetSearchRequestType {
	
	@XmlElement
	String customer;
	@XmlElement
	String fleet;
	@XmlElement
	String model;
	@XmlElement
	String assetGrpName;
	@XmlElement
	String onboardSWVersion;
	@XmlElement
	String fromVersion;
	@XmlElement
	String toVersion;
	@XmlElement
	String searchType;
	
	@XmlElement
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
