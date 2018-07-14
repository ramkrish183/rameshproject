package com.ge.trans.eoa.services.asset.service.valueobjects;

import java.util.List;


public class AssetsLocationFromShopVO {

	
	
	private String EServicesOrgId;
	private List<AssetVO> assets;
	private String distanceToLocate;
	

	public String getEServicesOrgId() {
		return EServicesOrgId;
	}
	public void setEServicesOrgId(String eServicesOrgId) {
		EServicesOrgId = eServicesOrgId;
	}
	public List<AssetVO> getAssets() {
		return assets;
	}
	public void setAssets(List<AssetVO> assets) {
		this.assets = assets;
	}
	public String getDistanceToLocate() {
		return distanceToLocate;
	}
	public void setDistanceToLocate(String distanceToLocate) {
		this.distanceToLocate = distanceToLocate;
	}
	
	
	
	

	
}
