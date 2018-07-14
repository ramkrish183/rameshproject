/**
 * 
 */
package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ge.trans.rmd.services.locovision.valueobjects.MessageDetailsType;

/**
 * @author MSHIRAJUDDIN
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activeLcvLDVRCamDetailsType", propOrder = { "cameraNumber",
        "ldvrStatusTime", "location", "zeroSpeedStopped",
        "extDriveRemotelyStopped", "gpsSourceLoss", "speedSourceLoss",
        "recordingStatus", "extDriveLatestFileTime", "intDriveKBFree",
        "extDriveKBFree", "messageDetails" })
@XmlRootElement
public class ActiveLcvLDVRCamDetailsType {

    private String cameraNumber;
    private String ldvrStatusTime;
    private String location;
    private String zeroSpeedStopped;
    private String extDriveRemotelyStopped;
    private String gpsSourceLoss;
    private String speedSourceLoss;
    private String recordingStatus;
    private String extDriveLatestFileTime;
    private String intDriveKBFree;
    private String extDriveKBFree;
    private MessageDetailsType messageDetails;

    /**
     * @return the cameraNumber
     */
    public String getCameraNumber() {
        return cameraNumber;
    }

    /**
     * @param cameraNumber
     *            the cameraNumber to set
     */
    public void setCameraNumber(String cameraNumber) {
        this.cameraNumber = cameraNumber;
    }

    /**
     * @return the ldvrStatusTime
     */
    public String getLdvrStatusTime() {
        return ldvrStatusTime;
    }

    /**
     * @param ldvrStatusTime
     *            the ldvrStatusTime to set
     */
    public void setLdvrStatusTime(String ldvrStatusTime) {
        this.ldvrStatusTime = ldvrStatusTime;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the zeroSpeedStopped
     */
    public String getZeroSpeedStopped() {
        return zeroSpeedStopped;
    }

    /**
     * @param zeroSpeedStopped
     *            the zeroSpeedStopped to set
     */
    public void setZeroSpeedStopped(String zeroSpeedStopped) {
        this.zeroSpeedStopped = zeroSpeedStopped;
    }

    /**
     * @return the extDriveRemotelyStopped
     */
    public String getExtDriveRemotelyStopped() {
        return extDriveRemotelyStopped;
    }

    /**
     * @param extDriveRemotelyStopped
     *            the extDriveRemotelyStopped to set
     */
    public void setExtDriveRemotelyStopped(String extDriveRemotelyStopped) {
        this.extDriveRemotelyStopped = extDriveRemotelyStopped;
    }

    /**
     * @return the gpsSourceLoss
     */
    public String getGpsSourceLoss() {
        return gpsSourceLoss;
    }

    /**
     * @param gpsSourceLoss
     *            the gpsSourceLoss to set
     */
    public void setGpsSourceLoss(String gpsSourceLoss) {
        this.gpsSourceLoss = gpsSourceLoss;
    }

    /**
     * @return the speedSourceLoss
     */
    public String getSpeedSourceLoss() {
        return speedSourceLoss;
    }

    /**
     * @param speedSourceLoss
     *            the speedSourceLoss to set
     */
    public void setSpeedSourceLoss(String speedSourceLoss) {
        this.speedSourceLoss = speedSourceLoss;
    }

    /**
     * @return the recordingStatus
     */
    public String getRecordingStatus() {
        return recordingStatus;
    }

    /**
     * @param recordingStatus
     *            the recordingStatus to set
     */
    public void setRecordingStatus(String recordingStatus) {
        this.recordingStatus = recordingStatus;
    }

    /**
     * @return the extDriveLatestFileTime
     */
    public String getExtDriveLatestFileTime() {
        return extDriveLatestFileTime;
    }

    /**
     * @param extDriveLatestFileTime
     *            the extDriveLatestFileTime to set
     */
    public void setExtDriveLatestFileTime(String extDriveLatestFileTime) {
        this.extDriveLatestFileTime = extDriveLatestFileTime;
    }

    /**
     * @return the intDriveKBFree
     */
    public String getIntDriveKBFree() {
        return intDriveKBFree;
    }

    /**
     * @param intDriveKBFree
     *            the intDriveKBFree to set
     */
    public void setIntDriveKBFree(String intDriveKBFree) {
        this.intDriveKBFree = intDriveKBFree;
    }

    /**
     * @return the extDriveKBFree
     */
    public String getExtDriveKBFree() {
        return extDriveKBFree;
    }

    /**
     * @param extDriveKBFree
     *            the extDriveKBFree to set
     */
    public void setExtDriveKBFree(String extDriveKBFree) {
        this.extDriveKBFree = extDriveKBFree;
    }

    /**
     * @return the messageDetails
     */
    public MessageDetailsType getMessageDetails() {
        return messageDetails;
    }

    /**
     * @param messageDetails
     *            the messageDetails to set
     */
    public void setMessageDetails(MessageDetailsType messageDetails) {
        this.messageDetails = messageDetails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ActiveLcvLDVRCamDetails [cameraNumber=" + cameraNumber
                + ", ldvrStatusTime=" + ldvrStatusTime + ", location="
                + location + ", zeroSpeedStopped=" + zeroSpeedStopped
                + ", extDriveRemotelyStopped=" + extDriveRemotelyStopped
                + ", gpsSourceLoss=" + gpsSourceLoss + ", speedSourceLoss="
                + speedSourceLoss + ", recordingStatus=" + recordingStatus
                + ", extDriveLatestFileTime=" + extDriveLatestFileTime
                + ", intDriveKBFree=" + intDriveKBFree + ", extDriveKBFree="
                + extDriveKBFree + ", messageDetails=" + messageDetails + "]";
    }

}
