package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "templateResponseType", propOrder = { "ldvrTemplates"})*/

@XmlRootElement
public class LDVRTemplateResponseType {
	
	private String templateCategoryName;
	private String templateTypeName;
	private String templateObjId;
	private String applicationId;
	private String messageId;
	private Integer templateNumber;
	private Integer templateVersion;
	private String description;
	private String templateStatus;
	private String createdBy;
	private String createdDate;
	private String lastUpdatedBy;
	private String lastUpdatedDate;
	private LDVRTemplateDetailsType templateDetails;
	private List<String> devices;

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getTemplateObjId() {
		return templateObjId;
	}

	public void setTemplateObjId(String templateObjId) {
		this.templateObjId = templateObjId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(Integer templateNumber) {
		this.templateNumber = templateNumber;
	}

	public Integer getTemplateVersion() {
		return templateVersion;
	}

	public void setTemplateVersion(Integer templateVersion) {
		this.templateVersion = templateVersion;
	}

	public String getTemplateStatus() {
		return templateStatus;
	}

	public void setTemplateStatus(String templateStatus) {
		this.templateStatus = templateStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public LDVRTemplateDetailsType getLdvrTemplateDetType() {
		return templateDetails;
	}

	public void setLdvrTemplateDetType(LDVRTemplateDetailsType ldvrTemplateDetType) {
		this.templateDetails = ldvrTemplateDetType;
	}

	public String getTemplateCategoryName() {
		return templateCategoryName;
	}

	public void setTemplateCategoryName(String templateCategoryName) {
		this.templateCategoryName = templateCategoryName;
	}

	public LDVRTemplateDetailsType getTemplateDetails() {
		return templateDetails;
	}

	public void setTemplateDetails(LDVRTemplateDetailsType templateDetails) {
		this.templateDetails = templateDetails;
	}

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }
	
	

	
}
