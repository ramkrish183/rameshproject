package com.ge.trans.eoa.services.asset.service.valueobjects;

	/*******************************************************************************
	 * 
	 * @Author : 
	 * @Version : 1.0
	 * @Date Created: July 20, 2015
	 * @Date Modified :July 20, 2015
	 * @Modified By :
	 * @Contact :
	 * @Description :
	 * @History :
	 * 
	 ******************************************************************************/
	public class AssetSearchVO {

		
		private String customerId;
		private String assetGroupName;
		private String fleet;
		private String assetFrom;
		private String assetTo;
		private String assetNumber;
		private String ctrlCfgObjId;
		
		
		
		
		public String getCtrlCfgObjId() {
			return ctrlCfgObjId;
		}
		public void setCtrlCfgObjId(String ctrlCfgObjId) {
			this.ctrlCfgObjId = ctrlCfgObjId;
		}
		public String getAssetNumber() {
			return assetNumber;
		}
		public void setAssetNumber(String assetNumber) {
			this.assetNumber = assetNumber;
		}
		public String getFleet() {
			return fleet;
		}
		public void setFleet(String fleet) {
			this.fleet = fleet;
		}
		
		
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		
		public String getAssetGroupName() {
			return assetGroupName;
		}
		public void setAssetGroupName(String assetGroupName) {
			this.assetGroupName = assetGroupName;
		}
		public String getAssetFrom() {
			return assetFrom;
		}
		public void setAssetFrom(String assetFrom) {
			this.assetFrom = assetFrom;
		}
		public String getAssetTo() {
			return assetTo;
		}
		public void setAssetTo(String assetTo) {
			this.assetTo = assetTo;
		}
		
		
	}
