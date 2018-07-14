package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

public class LocomotivesCommResponseVO {
	 
	protected long locomotiveId;
	protected String roadNumber;
	protected String vehicleHeader;
	protected long nextMaintenanceType;
	protected String nextMaintenanceDate;
	protected String lastFaultMsgDate;
	protected String lastATSMsgDate;
	protected String lastTOStatusMsgDate;
	
	public LocomotivesCommResponseVO(){}
	
	public LocomotivesCommResponseVO(long locomotiveId, String roadNumber,
			String vehicleHeader, long nextMaintenanceType,
			String nextMaintenanceDate, String lastFaultMsgDate,
			String lastATSMsgDate, String lastTOStatusMsgDate) {
		
		this.locomotiveId = locomotiveId;
		this.roadNumber = roadNumber;
		this.vehicleHeader = vehicleHeader;
		this.nextMaintenanceType = nextMaintenanceType;
		this.nextMaintenanceDate = nextMaintenanceDate;
		this.lastFaultMsgDate = lastFaultMsgDate;
		this.lastATSMsgDate = lastATSMsgDate;
		this.lastTOStatusMsgDate = lastTOStatusMsgDate;
	}
	
	public long getLocomotiveId() {
		return locomotiveId;
	}
	public void setLocomotiveId(long locomotiveId) {
		this.locomotiveId = locomotiveId;
	}
	public String getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	public String getVehicleHeader() {
		return vehicleHeader;
	}
	public void setVehicleHeader(String vehicleHeader) {
		this.vehicleHeader = vehicleHeader;
	}
	public long getNextMaintenanceType() {
		return nextMaintenanceType;
	}
	public void setNextMaintenanceType(long nextMaintenanceType) {
		this.nextMaintenanceType = nextMaintenanceType;
	}
	public String getNextMaintenanceDate() {
		return nextMaintenanceDate;
	}
	public void setNextMaintenanceDate(String nextMaintenanceDate) {
		this.nextMaintenanceDate = nextMaintenanceDate;
	}
	public String getLastFaultMsgDate() {
		return lastFaultMsgDate;
	}
	public void setLastFaultMsgDate(String lastFaultMsgDate) {
		this.lastFaultMsgDate = lastFaultMsgDate;
	}
	public String getLastATSMsgDate() {
		return lastATSMsgDate;
	}
	public void setLastATSMsgDate(String lastATSMsgDate) {
		this.lastATSMsgDate = lastATSMsgDate;
	}
	public String getLastTOStatusMsgDate() {
		return lastTOStatusMsgDate;
	}
	public void setLastTOStatusMsgDate(String lastTOStatusMsgDate) {
		this.lastTOStatusMsgDate = lastTOStatusMsgDate;
	}

}
