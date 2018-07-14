package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DetailsType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String itemObjId;
	private String statusCode;
	private String statusMessage;
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
	 * @return the itemObjId
	 */
	public String getItemObjId() {
		return itemObjId;
	}
	/**
	 * @param itemObjId the itemObjId to set
	 */
	public void setItemObjId(String itemObjId) {
		this.itemObjId = itemObjId;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DetailsVO [assetOwnerId=" + assetOwnerId + ", roadInitial="
				+ roadInitial + ", roadNumber=" + roadNumber + ", device="
				+ device + ", itemObjId=" + itemObjId + ", statusCode="
				+ statusCode + ", statusMessage=" + statusMessage + "]";
	}


}
