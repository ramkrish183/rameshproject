package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class LDVRSaveTemplateGeofenceType {
	private List<String> geoZoneIds;

	public List<String> getGeoZoneIds() {
		return geoZoneIds;
	}

	public void setGeoZoneIds(List<String> geoZoneIds) {
		this.geoZoneIds = geoZoneIds;
	}	
	
}


