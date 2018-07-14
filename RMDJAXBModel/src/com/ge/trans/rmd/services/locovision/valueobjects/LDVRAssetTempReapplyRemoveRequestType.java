package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class LDVRAssetTempReapplyRemoveRequestType {
	
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String lstReApplyTemplateIDs;
	private String lstRemoveTemplateIDs;
	private String userName;




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
	 * @return the lstReApplyTemplateIDs
	 */
	public String getLstReApplyTemplateIDs() {
		return lstReApplyTemplateIDs;
	}

	/**
	 * @param lstReApplyTemplateIDs the lstReApplyTemplateIDs to set
	 */
	public void setLstReApplyTemplateIDs(String lstReApplyTemplateIDs) {
		this.lstReApplyTemplateIDs = lstReApplyTemplateIDs;
	}

	/**
	 * @return the lstRemoveTemplateIDs
	 */
	public String getLstRemoveTemplateIDs() {
		return lstRemoveTemplateIDs;
	}

	/**
	 * @param lstRemoveTemplateIDs the lstRemoveTemplateIDs to set
	 */
	public void setLstRemoveTemplateIDs(String lstRemoveTemplateIDs) {
		this.lstRemoveTemplateIDs = lstRemoveTemplateIDs;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoadNumber() {
		return roadNumber;
	}

	public void setRoadNumber(String roadNumber) {
		this.roadNumber = roadNumber;
	}


	public String getAssetOwnerId() {
		return assetOwnerId;
	}

	public void setAssetOwnerId(String assetOwnerId) {
		this.assetOwnerId = assetOwnerId;
	}

	public String getRoadInitial() {
		return roadInitial;
	}

	public void setRoadInitial(String value) {
		this.roadInitial = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LDVRAssetTempReapplyRemoveRequestVO [assetOwnerId="
				+ assetOwnerId + ", roadInitial=" + roadInitial
				+ ", roadNumber=" + roadNumber + ", device=" + device
				+ ", lstReApplyTemplateIDs=" + lstReApplyTemplateIDs
				+ ", lstRemoveTemplateIDs=" + lstRemoveTemplateIDs
				+ ", userName=" + userName + "]";
	}

}
