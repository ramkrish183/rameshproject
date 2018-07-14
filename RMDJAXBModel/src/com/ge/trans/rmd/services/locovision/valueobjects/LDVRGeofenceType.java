package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geofenceType", propOrder = { "geozoneObjid","geoZoneId","geoZoneName","assetOwnerId","latitude1","longitude1","latitude3","longitude3","active","obsolete"})

@XmlRootElement
public class LDVRGeofenceType {
	
	private String geozoneObjid;
	private String geoZoneId;
	private String geoZoneName;
	private String assetOwnerId;
	private double latitude1;
	private double longitude1;
	private double latitude3;
	private double longitude3;
    private String active; // "Y/N"
	private String obsolete;
	public String getGeozoneObjid() {
		return geozoneObjid;
	}
	public void setGeozoneObjid(String geozoneObjid) {
		this.geozoneObjid = geozoneObjid;
	}
	public String getGeoZoneId() {
		return geoZoneId;
	}
	public void setGeoZoneId(String geoZoneId) {
		this.geoZoneId = geoZoneId;
	}
	public String getGeoZoneName() {
		return geoZoneName;
	}
	public void setGeoZoneName(String geoZoneName) {
		this.geoZoneName = geoZoneName;
	}
	public String getAssetOwnerId() {
		return assetOwnerId;
	}
	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}


	public double getLatitude1() {
		return latitude1;
	}
	public void setLatitude1(double latitude1) {
		this.latitude1 = latitude1;
	}
	public double getLongitude1() {
		return longitude1;
	}
	public void setLongitude1(double longitude1) {
		this.longitude1 = longitude1;
	}
	public double getLatitude3() {
		return latitude3;
	}
	public void setLatitude3(double latitude3) {
		this.latitude3 = latitude3;
	}
	public double getLongitude3() {
		return longitude3;
	}
	public void setLongitude3(double longitude3) {
		this.longitude3 = longitude3;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getObsolete() {
		return obsolete;
	}
	public void setObsolete(String obsolete) {
		this.obsolete = obsolete;
	}
    
}


