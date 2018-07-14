package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LDVRAssetSnapshotResponseType {

	protected String device;
	protected List<LDVRActiveServices> activeServices;
	
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public List<LDVRActiveServices> getActiveServices() {
		return activeServices;
	}
	public void setActiveServices(List<LDVRActiveServices> activeServices) {
		this.activeServices = activeServices;
	}
	

}
