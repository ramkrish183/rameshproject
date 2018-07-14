package com.ge.trans.eoa.services.asset.service.valueobjects;

public class VehicleDetailsVO {

	
	
	private String vehicleObjid;
	private String sitePartObjid;
	private String vehicleNo;
	private String vehicleHdr;
	private String orgId;
	private String custName; 	
	private String partStatus;
	private boolean isServicesEnabled;
	private String serviceCell;
	private String services;
	private int controllerId;
	private String family;
	private long measurementSystemId;
	
	public int getControllerId() {
		return controllerId;
	}
	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public long getMeasurementSystemId() {
		return measurementSystemId;
	}
	public void setMeasurementSystemId(long measurementSystemId) {
		this.measurementSystemId = measurementSystemId;
	}
	public String getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	public String getVehicleObjid() {
		return vehicleObjid;
	}
	public void setVehicleObjid(String vehicleObjid) {
		this.vehicleObjid = vehicleObjid;
	}
	public String getSitePartObjid() {
		return sitePartObjid;
	}
	public void setSitePartObjid(String sitePartObjid) {
		this.sitePartObjid = sitePartObjid;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleHdr() {
		return vehicleHdr;
	}
	public void setVehicleHdr(String vehicleHdr) {
		this.vehicleHdr = vehicleHdr;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public boolean isServicesEnabled() {
		return isServicesEnabled;
	}
	public void setServicesEnabled(boolean isServicesEnabled) {
		this.isServicesEnabled = isServicesEnabled;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getServiceCell() {
		return serviceCell;
	}
	public void setServiceCell(String serviceCell) {
		this.serviceCell = serviceCell;
	}
}
