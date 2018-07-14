package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activePreservationDataResponseType", propOrder = {
        "assetOwnerId", "roadInitial", "roadNumber", "device",
        "recordingStatus", "activePreservationRequests",
        "preservationQueueUtilizationDt", "sectionTitle", "recordingDate",
        "queueUtilization", "activePreservationTemplates" })
@XmlRootElement
public class ActivePreservationDataResponseType {

    private String assetOwnerId;
    private String roadInitial;
    private String roadNumber;
    private String device;
    private String recordingStatus;
    private String activePreservationRequests;
    private String preservationQueueUtilizationDt;
    private String sectionTitle;
    private String recordingDate;
    private String queueUtilization;
    private List<LDVRPreservationRequestTemplate> activePreservationTemplates;

    public String getPreservationQueueUtilizationDt() {
        return preservationQueueUtilizationDt;
    }

    public void setPreservationQueueUtilizationDt(
            String preservationQueueUtilizationDt) {
        this.preservationQueueUtilizationDt = preservationQueueUtilizationDt;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(String recordingDate) {
        this.recordingDate = recordingDate;
    }

    public String getQueueUtilization() {
        return queueUtilization;
    }

    public void setQueueUtilization(String queueUtilization) {
        this.queueUtilization = queueUtilization;
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

    public void setRoadInitial(String roadInitial) {
        this.roadInitial = roadInitial;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getRecordingStatus() {
        return recordingStatus;
    }

    public void setRecordingStatus(String recordingStatus) {
        this.recordingStatus = recordingStatus;
    }

    public String getActivePreservationRequests() {
        return activePreservationRequests;
    }

    public void setActivePreservationRequests(String activePreservationRequests) {
        this.activePreservationRequests = activePreservationRequests;
    }

    public List<LDVRPreservationRequestTemplate> getActivePreservationTemplates() {
        return activePreservationTemplates;
    }

    public void setActivePreservationTemplates(
            List<LDVRPreservationRequestTemplate> activePreservationTemplates) {
        this.activePreservationTemplates = activePreservationTemplates;
    }

}
