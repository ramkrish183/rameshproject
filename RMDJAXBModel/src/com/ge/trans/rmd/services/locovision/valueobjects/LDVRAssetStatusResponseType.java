/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.locovision.valueobjects.AssetLDVRDataType;

/**
 * @author MSHIRAJUDDIN
 *
 */
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ldvrAssetStatusResponseType", propOrder = { "assetOwnerId","roadInitial","roadNumber","device",
		"sectionTitle","recordingStatus",
		"recordingDate", "queueUtilization", "preservationQueueUtilizationDt",
		"activePreservationRequests","assetLDVRData"})*/

@XmlRootElement
public class LDVRAssetStatusResponseType {
	private String assetOwnerId;
	private String roadInitial;
	private String roadNumber;
	private String device;
	private String sectionTitle;
	private String recordingStatus;
	private String recordingDate;
	private String queueUtilization;
	private String preservationQueueUtilizationDt;
	private String activePreservationRequests;
	private AssetLDVRDataType assetLDVRData;
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
	 * @return the sectionTitle
	 */
	public String getSectionTitle() {
		return sectionTitle;
	}
	/**
	 * @param sectionTitle the sectionTitle to set
	 */
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	/**
	 * @return the recordingStatus
	 */
	public String getRecordingStatus() {
		return recordingStatus;
	}
	/**
	 * @param recordingStatus the recordingStatus to set
	 */
	public void setRecordingStatus(String recordingStatus) {
		this.recordingStatus = recordingStatus;
	}
	/**
	 * @return the recordingDate
	 */
	public String getRecordingDate() {
		return recordingDate;
	}
	/**
	 * @param recordingDate the recordingDate to set
	 */
	public void setRecordingDate(String recordingDate) {
		this.recordingDate = recordingDate;
	}
	/**
	 * @return the queueUtilization
	 */
	public String getQueueUtilization() {
		return queueUtilization;
	}
	/**
	 * @param queueUtilization the queueUtilization to set
	 */
	public void setQueueUtilization(String queueUtilization) {
		this.queueUtilization = queueUtilization;
	}
	/**
	 * @return the preservationQueueUtilizationDt
	 */
	public String getPreservationQueueUtilizationDt() {
		return preservationQueueUtilizationDt;
	}
	/**
	 * @param preservationQueueUtilizationDt the preservationQueueUtilizationDt to set
	 */
	public void setPreservationQueueUtilizationDt(
			String preservationQueueUtilizationDt) {
		this.preservationQueueUtilizationDt = preservationQueueUtilizationDt;
	}
	/**
	 * @return the activePreservationRequests
	 */
	public String getActivePreservationRequests() {
		return activePreservationRequests;
	}
	/**
	 * @param activePreservationRequests the activePreservationRequests to set
	 */
	public void setActivePreservationRequests(String activePreservationRequests) {
		this.activePreservationRequests = activePreservationRequests;
	}
	/**
	 * @return the assetLDVRData
	 */
	public AssetLDVRDataType getAssetLDVRData() {
		return assetLDVRData;
	}
	/**
	 * @param assetLDVRData the assetLDVRData to set
	 */
	public void setAssetLDVRData(AssetLDVRDataType assetLDVRData) {
		this.assetLDVRData = assetLDVRData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LDVRAssetStatusResponseVO [assetOwnerId=" + assetOwnerId
				+ ", roadInitial=" + roadInitial + ", roadNumber=" + roadNumber
				+ ", device=" + device + ", sectionTitle=" + sectionTitle
				+ ", recordingStatus=" + recordingStatus + ", recordingDate="
				+ recordingDate + ", queueUtilization=" + queueUtilization
				+ ", preservationQueueUtilizationDt="
				+ preservationQueueUtilizationDt
				+ ", activePreservationRequests=" + activePreservationRequests
				+ "]";
	}

}
