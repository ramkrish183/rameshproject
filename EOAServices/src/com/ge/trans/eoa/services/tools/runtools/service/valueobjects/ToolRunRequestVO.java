package com.ge.trans.eoa.services.tools.runtools.service.valueobjects;


public class ToolRunRequestVO {
	
	private String scheduleRunDate;
	private String diagnosticService;
	private String lookbackRange;
	private String vehicleObjid;	
	private boolean isCurrentDateRun;
	private String customerId;
	private String assetNumber;
	private String assetHeader;
	private String userName;
	private boolean includeShopData;
	private boolean isRunOnPastData;
	
	public boolean isRunOnPastData() {
		return isRunOnPastData;
	}
	public void setRunOnPastData(boolean isRunOnPastData) {
		this.isRunOnPastData = isRunOnPastData;
	}
	public boolean isIncludeShopData() {
		return includeShopData;
	}
	public void setIncludeShopData(boolean includeShopData) {
		this.includeShopData = includeShopData;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getAssetHeader() {
		return assetHeader;
	}
	public void setAssetHeader(String assetHeader) {
		this.assetHeader = assetHeader;
	}
	public boolean isCurrentDateRun() {
		return isCurrentDateRun;
	}
	public void setCurrentDateRun(boolean isCurrentDateRun) {
		this.isCurrentDateRun = isCurrentDateRun;
	}
	public String getVehicleObjid() {
		return vehicleObjid;
	}
	public void setVehicleObjid(String vehicleObjid) {
		this.vehicleObjid = vehicleObjid;
	}
	public String getScheduleRunDate() {
		return scheduleRunDate;
	}
	public void setScheduleRunDate(String scheduleRunDate) {
		this.scheduleRunDate = scheduleRunDate;
	}
	public String getDiagnosticService() {
		return diagnosticService;
	}
	public void setDiagnosticService(String diagnosticService) {
		this.diagnosticService = diagnosticService;
	}
	public String getLookbackRange() {
		return lookbackRange;
	}
	public void setLookbackRange(String lookbackRange) {
		this.lookbackRange = lookbackRange;
	}
	
	

}
