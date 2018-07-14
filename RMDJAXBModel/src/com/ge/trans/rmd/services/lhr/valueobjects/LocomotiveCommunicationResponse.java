package com.ge.trans.rmd.services.lhr.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "locomotiveCommunicationResponse", propOrder = {
    "locomotiveId",
    "roadNumber",
    "vehicleHeader",
    "nextMaintenanceType",
    "nextMaintenanceDate",
    "lastFaultMsgDate",
    "lastATSMsgDate",
    "lastTOStatusMsgDate"
})
@XmlRootElement
public class LocomotiveCommunicationResponse {
	
	@XmlElement(required = true)
	protected long locomotiveId;
	@XmlElement(required = true)
	protected String roadNumber;
	@XmlElement(required = true)
	protected String vehicleHeader;
	@XmlElement(required = true)
	protected long nextMaintenanceType;
	protected String nextMaintenanceDate;
	protected String lastFaultMsgDate;
	protected String lastATSMsgDate;
	protected String lastTOStatusMsgDate;
	
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
