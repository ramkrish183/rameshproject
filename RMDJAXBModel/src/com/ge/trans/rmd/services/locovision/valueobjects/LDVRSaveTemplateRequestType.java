package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.NONE)
public class LDVRSaveTemplateRequestType {

	private String templateObjId;
	
	private String messageId;
	
    private String applicationId;
	
    private int templateNumber;
	
    private int templateVersion;
	
    private String description;
	
    private String templateStatus;
	
    private String customerId;
    
    private String userName;   
    
	private String createdDate;
	
	private String lastUpdatedDate;
    
    private LDVRSaveTemplateDetailsType templateDetails;
      
	public String getMessageId() {
		return messageId;
	}


	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public String getApplicationId() {
		return applicationId;
	}


	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	public int getTemplateNumber() {
		return templateNumber;
	}


	public void setTemplateNumber(int templateNumber) {
		this.templateNumber = templateNumber;
	}


	public int getTemplateVersion() {
		return templateVersion;
	}


	public void setTemplateVersion(int templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getTemplateStatus() {
		return templateStatus;
	}


	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getTemplateObjId() {
		return templateObjId;
	}


	public void setTemplateObjId(String templateObjId) {
		this.templateObjId = templateObjId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}


	public LDVRSaveTemplateDetailsType getTemplateDetails() {
		return templateDetails;
	}


	public void setTemplateDetails(LDVRSaveTemplateDetailsType templateDetails) {
		this.templateDetails = templateDetails;
	}	
	
	/*	@Override
	public String toString() {
		String val = "applicationId: "+applicationId
						+"\nmessageId: "+messageId
						+"\ntemplateNumber: "+templateNumber
						+"\ntemplateVersion: "+templateVersion
						+"\ntemplateStatus: "+templateStatus
						+"\ndescription: "+description
						+"\ncustomerId: "+customerId;						
		return val;
	}*/
	
}

