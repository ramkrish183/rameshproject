package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lDVRPreservationRetriveTemplateInfo", propOrder = { "templateTypeName","templateNumber","templateVersion","title","templateStatus","lastUpdatedBy",
		"lastUpdatedDate","requestedStartTime","requestedEndTime","scanExpirationCode","numFilesInPq","latestFileScannedDate","statusDate","deletion","offloadPriority","complete"})


@XmlRootElement
public class LDVRPreservationRetriveTemplateInfo {
	
	private String templateTypeName;
	private String templateNumber;
	private String templateVersion;
	private String title;
	private String templateStatus;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private String requestedStartTime;
	private String requestedEndTime;
	private String scanExpirationCode;
	private String numFilesInPq;
	private String latestFileScannedDate;
	private String statusDate;
	private String deletion;
	private String offloadPriority;
	private String complete;
	
	public String getTemplateTypeName() {
		return templateTypeName;
	}
	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}
	public String getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}
	public String getTemplateVersion() {
		return templateVersion;
	}
	public void setTemplateVersion(String templateVersion) {
		this.templateVersion = templateVersion;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTemplateStatus() {
		return templateStatus;
	}
	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public String getRequestedStartTime() {
		return requestedStartTime;
	}
	public void setRequestedStartTime(String requestedStartTime) {
		this.requestedStartTime = requestedStartTime;
	}
	public String getRequestedEndTime() {
		return requestedEndTime;
	}
	public void setRequestedEndTime(String requestedEndTime) {
		this.requestedEndTime = requestedEndTime;
	}
	public String getScanExpirationCode() {
		return scanExpirationCode;
	}
	public void setScanExpirationCode(String scanExpirationCode) {
		this.scanExpirationCode = scanExpirationCode;
	}
	public String getNumFilesInPq() {
		return numFilesInPq;
	}
	public void setNumFilesInPq(String numFilesInPq) {
		this.numFilesInPq = numFilesInPq;
	}
	public String getLatestFileScannedDate() {
		return latestFileScannedDate;
	}
	public void setLatestFileScannedDate(String latestFileScannedDate) {
		this.latestFileScannedDate = latestFileScannedDate;
	}
	public String getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	public String getDeletion() {
		return deletion;
	}
	public void setDeletion(String deletion) {
		this.deletion = deletion;
	}
	public String getOffloadPriority() {
		return offloadPriority;
	}
	public void setOffloadPriority(String offloadPriority) {
		this.offloadPriority = offloadPriority;
	}
	public String getComplete() {
		return complete;
	}
	public void setComplete(String complete) {
		this.complete = complete;
	}
	
	
}
