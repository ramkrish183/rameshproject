package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Template Response VO to list the templates
 */
@XmlRootElement
public class LDVRTemplateDetailsType {

	private List<Integer> cameraList = null;
	private String requestedEndTime = null;
	private Long importance = null;
	private Long deletionType = null;
	private LDVRTimeFrameType timeframe = null;
	private LDVRTemplateGeofenceType geoZone = null;
	private LDVREventType event = null;
	public List<Integer> getCameraList() {
		return cameraList;
	}
	public void setCameraList(List<Integer> cameraList) {
		this.cameraList = cameraList;
	}
	public String getRequestedEndTime() {
		return requestedEndTime;
	}
	public void setRequestedEndTime(String requestedEndTime) {
		this.requestedEndTime = requestedEndTime;
	}
	public Long getImportance() {
		return importance;
	}
	public void setImportance(Long importance) {
		this.importance = importance;
	}
	public Long getDeletionType() {
		return deletionType;
	}
	public void setDeletionType(Long deletionType) {
		this.deletionType = deletionType;
	}
	public LDVRTimeFrameType getTimeframe() {
		return timeframe;
	}
	public void setTimeframe(LDVRTimeFrameType timeframe) {
		this.timeframe = timeframe;
	}
	
	public LDVRTemplateGeofenceType getGeoZone() {
		return geoZone;
	}
	public void setGeoZone(LDVRTemplateGeofenceType geoZone) {
		this.geoZone = geoZone;
	}
	public LDVREventType getEvent() {
		return event;
	}
	public void setEvent(LDVREventType event) {
		this.event = event;
	}
	
}
