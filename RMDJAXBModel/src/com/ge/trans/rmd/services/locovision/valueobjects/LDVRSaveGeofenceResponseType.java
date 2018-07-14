package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveGeofenceResponseType", propOrder = { "status", "ldvrSaveGeofenceStatus"})

@XmlRootElement
public class LDVRSaveGeofenceResponseType {
	private String status;
	private List<LDVRSaveGeofenceStatusType> ldvrSaveGeofenceStatus;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<LDVRSaveGeofenceStatusType> getLdvrSaveGeofenceStatus() {
		return ldvrSaveGeofenceStatus;
	}
	public void setLdvrSaveGeofenceStatus(
			List<LDVRSaveGeofenceStatusType> ldvrSaveGeofenceStatus) {
		this.ldvrSaveGeofenceStatus = ldvrSaveGeofenceStatus;
	}

	
}
