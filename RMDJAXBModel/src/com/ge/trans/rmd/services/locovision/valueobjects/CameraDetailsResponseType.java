package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cameraDetailsResponseType", propOrder = { "assetOwnerId",
		"roadInitial", "roadNumber", "device", "numOfCameras" })

@XmlRootElement
public class CameraDetailsResponseType {
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private int numOfCameras;	
	/**
	 * @return the assetOwnerId
	 */
	public String getAssetOwnerId() {
		return assetOwnerId;
	}
	/**
	 * @param assetOwnerId the assetOwnerId to set
	 */
	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}
	/**
	 * @return the roadInitial
	 */
	public String getRoadInitial() {
		return roadInitial;
	}
	/**
	 * @param roadInitial the roadInitial to set
	 */
	public void setRoadInitial(String roadInitial) {
		this.roadInitial = roadInitial;
	}
	/**
	 * @return the roadNumber
	 */
	public String getRoadNumber() {
		return roadNumber;
	}
	/**
	 * @param roadNumber the roadNumber to set
	 */
	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}
	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}
	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}
	/**
	 * @return the numOfCameras
	 */
	public int getNumOfCameras() {
		return numOfCameras;
	}
	/**
	 * @param numOfCameras the numOfCameras to set
	 */
	public void setNumOfCameras(int numOfCameras) {
		this.numOfCameras = numOfCameras;
	}	
	
	@Override
	public String toString() {
		String val = "assetOwnerId: "+assetOwnerId
						+"\nroadInitial: "+roadInitial
						+"\nroadNumber: "+roadNumber
						+"\ndevice: "+device
						+"\nnumOfCameras: "+numOfCameras;						
		return val;
	}
	
}
