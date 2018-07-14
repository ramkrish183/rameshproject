package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geofenceType", propOrder = { "geozoneObjid","geoZoneId","geoZoneName","assetOwnerId","latitude1","longitude1","latitude3","longitude3","active"})
*/
@XmlRootElement
public class LDVRTemplateGeofenceType {
	private String geoZoneIds;
	
	private List<LDVRTemplateGeofences> geoZones;

	public String getGeoZoneIds() {
		return geoZoneIds;
	}

	public void setGeoZoneIds(String geoZoneIds) {
		this.geoZoneIds = geoZoneIds;
	}

	public List<LDVRTemplateGeofences> getGeoZones() {
		return geoZones;
	}

	public void setGeoZones(List<LDVRTemplateGeofences> geoZones) {
		this.geoZones = geoZones;
	}	
	
}


