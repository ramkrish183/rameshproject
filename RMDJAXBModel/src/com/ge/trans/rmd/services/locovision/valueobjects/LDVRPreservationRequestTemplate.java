package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activePreservationTemplates", propOrder = { "templateObjId","title","numVer","complete","filesPreserved","lastUpdatedBy",
		"requestedStartTime","requestedEndTime","comment","offBoardStatus","lDVRPreservationRetriveTemplateInfo"})


@XmlRootElement
public class LDVRPreservationRequestTemplate {

	
	private String templateObjId;
	private String title;
	private String numVer;
	private String complete;
	private String filesPreserved;
	private String lastUpdatedBy;
	private String requestedStartTime;
	private String requestedEndTime;
	private String comment;
	private String offBoardStatus;

	private LDVRPreservationRetriveTemplateInfo lDVRPreservationRetriveTemplateInfo;

	public String getTemplateObjId() {
		return templateObjId;
	}

	public void setTemplateObjId(String templateObjId) {
		this.templateObjId = templateObjId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNumVer() {
		return numVer;
	}

	public void setNumVer(String numVer) {
		this.numVer = numVer;
	}

	public String getComplete() {
		return complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public String getFilesPreserved() {
		return filesPreserved;
	}

	public void setFilesPreserved(String filesPreserved) {
		this.filesPreserved = filesPreserved;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOffBoardStatus() {
		return offBoardStatus;
	}

	public void setOffBoardStatus(String offBoardStatus) {
		this.offBoardStatus = offBoardStatus;
	}

	public LDVRPreservationRetriveTemplateInfo getlDVRPreservationRetriveTemplateInfo() {
		return lDVRPreservationRetriveTemplateInfo;
	}

	public void setlDVRPreservationRetriveTemplateInfo(
			LDVRPreservationRetriveTemplateInfo lDVRPreservationRetriveTemplateInfo) {
		this.lDVRPreservationRetriveTemplateInfo = lDVRPreservationRetriveTemplateInfo;
	}
	
	
	
}
