package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geofenceResponseType", propOrder = { "ldvrGeoZones"})

@XmlRootElement
public class LDVRGeofenceResponseType {
	
	private List<LDVRGeofenceType> ldvrGeoZones;

	public List<LDVRGeofenceType> getLdvrGeoZones() {
		return ldvrGeoZones;
	}

	public void setLdvrGeoZones(List<LDVRGeofenceType> ldvrGeoZones) {
		this.ldvrGeoZones = ldvrGeoZones;
	}

}
