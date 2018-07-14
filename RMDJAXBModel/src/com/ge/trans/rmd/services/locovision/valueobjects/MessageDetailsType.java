/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author MSHIRAJUDDIN
 *
 */
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageDetailsType", propOrder = { "statusDate","device","gpsLocation","gpsSourceStatus",
		"gpsSourceApplicationDataLoss","speedSourceStatus",
		"speedSourceApplicationDataLoss", "stoppedDueToZeroSpeed", "applicationsReportingDataLoss",
		"activePreservationRequests","assetLDVRData","assetOwnerId","roadInitial",
		"intDriveSizeKB","intFreeSpaceKB",
		"intRemoteStopActive","intOldestFileName",
		"newestFileTime", "exttDriveSizeKB", 
		"extFreeSpaceKB",
		"exttRemoteStopActive","preservationFreeSpaceKB",
		"oldDirectoryName", "newestVamFile", 
		"extOldestFileName"})*/

@XmlRootElement
public class MessageDetailsType {
	
	private String statusDate;
	private String device;
	private String gpsLocation;
	private String gpsSourceStatus;
	private String gpsSourceApplicationDataLoss;
	private String speedSourceStatus;
	
	private String speedSourceApplicationDataLoss;
	private String stoppedDueToZeroSpeed;
	private String applicationsReportingDataLoss;
	private String intDriveSizeKB;
	private String intFreeSpaceKB;

	private String intRemoteStopActive;
	private String intOldestFileTime;
	private String newestFileTime;
	private String exttDriveSizeKB;
	private String extFreeSpaceKB;
	
	private String exttRemoteStopActive;
	private String preservationFreeSpaceKB;
	private String oldDirectoryName;
	private String newestVamFile;
	private String extOldestFileTime;
	/**
	 * @return the statusDate
	 */
	public String getStatusDate() {
		return statusDate;
	}
	/**
	 * @param statusDate the statusDate to set
	 */
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
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
	 * @return the gpsLocation
	 */
	public String getGpsLocation() {
		return gpsLocation;
	}
	/**
	 * @param gpsLocation the gpsLocation to set
	 */
	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}
	/**
	 * @return the gpsSourceApplicationDataLoss
	 */
	public String getGpsSourceApplicationDataLoss() {
		return gpsSourceApplicationDataLoss;
	}
	/**
	 * @param gpsSourceApplicationDataLoss the gpsSourceApplicationDataLoss to set
	 */
	public void setGpsSourceApplicationDataLoss(String gpsSourceApplicationDataLoss) {
		this.gpsSourceApplicationDataLoss = gpsSourceApplicationDataLoss;
	}
	/**
	 * @return the speedSourceStatus
	 */
	public String getSpeedSourceStatus() {
		return speedSourceStatus;
	}
	/**
	 * @param speedSourceStatus the speedSourceStatus to set
	 */
	public void setSpeedSourceStatus(String speedSourceStatus) {
		this.speedSourceStatus = speedSourceStatus;
	}
	/**
	 * @return the speedSourceApplicationDataLoss
	 */
	public String getSpeedSourceApplicationDataLoss() {
		return speedSourceApplicationDataLoss;
	}
	/**
	 * @param speedSourceApplicationDataLoss the speedSourceApplicationDataLoss to set
	 */
	public void setSpeedSourceApplicationDataLoss(
			String speedSourceApplicationDataLoss) {
		this.speedSourceApplicationDataLoss = speedSourceApplicationDataLoss;
	}
	/**
	 * @return the stoppedDueToZeroSpeed
	 */
	public String getStoppedDueToZeroSpeed() {
		return stoppedDueToZeroSpeed;
	}
	/**
	 * @param stoppedDueToZeroSpeed the stoppedDueToZeroSpeed to set
	 */
	public void setStoppedDueToZeroSpeed(String stoppedDueToZeroSpeed) {
		this.stoppedDueToZeroSpeed = stoppedDueToZeroSpeed;
	}
	/**
	 * @return the applicationsReportingDataLoss
	 */
	public String getApplicationsReportingDataLoss() {
		return applicationsReportingDataLoss;
	}
	/**
	 * @param applicationsReportingDataLoss the applicationsReportingDataLoss to set
	 */
	public void setApplicationsReportingDataLoss(
			String applicationsReportingDataLoss) {
		this.applicationsReportingDataLoss = applicationsReportingDataLoss;
	}
	/**
	 * @return the intDriveSizeKB
	 */
	public String getIntDriveSizeKB() {
		return intDriveSizeKB;
	}
	/**
	 * @param intDriveSizeKB the intDriveSizeKB to set
	 */
	public void setIntDriveSizeKB(String intDriveSizeKB) {
		this.intDriveSizeKB = intDriveSizeKB;
	}
	/**
	 * @return the intFreeSpaceKB
	 */
	public String getIntFreeSpaceKB() {
		return intFreeSpaceKB;
	}
	/**
	 * @param intFreeSpaceKB the intFreeSpaceKB to set
	 */
	public void setIntFreeSpaceKB(String intFreeSpaceKB) {
		this.intFreeSpaceKB = intFreeSpaceKB;
	}
	/**
	 * @return the intRemoteStopActive
	 */
	public String getIntRemoteStopActive() {
		return intRemoteStopActive;
	}
	/**
	 * @param intRemoteStopActive the intRemoteStopActive to set
	 */
	public void setIntRemoteStopActive(String intRemoteStopActive) {
		this.intRemoteStopActive = intRemoteStopActive;
	}
	
	/**
	 * @return the newestFileTime
	 */
	public String getNewestFileTime() {
		return newestFileTime;
	}
	/**
	 * @param newestFileTime the newestFileTime to set
	 */
	public void setNewestFileTime(String newestFileTime) {
		this.newestFileTime = newestFileTime;
	}
	/**
	 * @return the exttDriveSizeKB
	 */
	public String getExttDriveSizeKB() {
		return exttDriveSizeKB;
	}
	/**
	 * @param exttDriveSizeKB the exttDriveSizeKB to set
	 */
	public void setExttDriveSizeKB(String exttDriveSizeKB) {
		this.exttDriveSizeKB = exttDriveSizeKB;
	}
	/**
	 * @return the extFreeSpaceKB
	 */
	public String getExtFreeSpaceKB() {
		return extFreeSpaceKB;
	}
	/**
	 * @param extFreeSpaceKB the extFreeSpaceKB to set
	 */
	public void setExtFreeSpaceKB(String extFreeSpaceKB) {
		this.extFreeSpaceKB = extFreeSpaceKB;
	}
	/**
	 * @return the exttRemoteStopActive
	 */
	public String getExttRemoteStopActive() {
		return exttRemoteStopActive;
	}
	/**
	 * @param exttRemoteStopActive the exttRemoteStopActive to set
	 */
	public void setExttRemoteStopActive(String exttRemoteStopActive) {
		this.exttRemoteStopActive = exttRemoteStopActive;
	}
	/**
	 * @return the preservationFreeSpaceKB
	 */
	public String getPreservationFreeSpaceKB() {
		return preservationFreeSpaceKB;
	}
	/**
	 * @param preservationFreeSpaceKB the preservationFreeSpaceKB to set
	 */
	public void setPreservationFreeSpaceKB(String preservationFreeSpaceKB) {
		this.preservationFreeSpaceKB = preservationFreeSpaceKB;
	}
	/**
	 * @return the oldDirectoryName
	 */
	public String getOldDirectoryName() {
		return oldDirectoryName;
	}
	/**
	 * @param oldDirectoryName the oldDirectoryName to set
	 */
	public void setOldDirectoryName(String oldDirectoryName) {
		this.oldDirectoryName = oldDirectoryName;
	}
	/**
	 * @return the newestVamFile
	 */
	public String getNewestVamFile() {
		return newestVamFile;
	}
	/**
	 * @param newestVamFile the newestVamFile to set
	 */
	public void setNewestVamFile(String newestVamFile) {
		this.newestVamFile = newestVamFile;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageDetails [statusDate=" + statusDate + ", device="
				+ device + ", gpsLocation=" + gpsLocation
				+ ", gpsSourceApplicationDataLoss="
				+ gpsSourceApplicationDataLoss + ", speedSourceStatus="
				+ speedSourceStatus + ", speedSourceApplicationDataLoss="
				+ speedSourceApplicationDataLoss + ", stoppedDueToZeroSpeed="
				+ stoppedDueToZeroSpeed + ", applicationsReportingDataLoss="
				+ applicationsReportingDataLoss + ", intDriveSizeKB="
				+ intDriveSizeKB + ", intFreeSpaceKB=" + intFreeSpaceKB
				+ ", intRemoteStopActive=" + intRemoteStopActive
				+ ", intOldestFileName=" + intOldestFileTime
				+ ", newestFileTime=" + newestFileTime + ", exttDriveSizeKB="
				+ exttDriveSizeKB + ", extFreeSpaceKB=" + extFreeSpaceKB
				+ ", exttRemoteStopActive=" + exttRemoteStopActive
				+ ", preservationFreeSpaceKB=" + preservationFreeSpaceKB
				+ ", oldDirectoryName=" + oldDirectoryName + ", newestVamFile="
				+ newestVamFile + ", extOldestFileName=" + extOldestFileTime
				+ "]";
	}
	public String getIntOldestFileTime() {
		return intOldestFileTime;
	}
	public void setIntOldestFileTime(String intOldestFileTime) {
		this.intOldestFileTime = intOldestFileTime;
	}
	public String getExtOldestFileTime() {
		return extOldestFileTime;
	}
	public void setExtOldestFileTime(String extOldestFileTime) {
		this.extOldestFileTime = extOldestFileTime;
	}
	public String getGpsSourceStatus() {
		return gpsSourceStatus;
	}
	public void setGpsSourceStatus(String gpsSourceStatus) {
		this.gpsSourceStatus = gpsSourceStatus;
	}


}
