package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;


public class LDVRTemplateRequestType {

	private String messageId;
	
    private String applicationId;
	
    private Long templateNumber;
	
    private Long templateVersion;
	
    private String description;
	
    private String templateStatus;
	
    private List<String> customerId;
    
    private String device;
    
    private String controllerConfiguration;
    
    private String category;
    
   // private LDVRTemplateDetailsType ldvrTemplateDetType;
      
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


	public Long getTemplateNumber() {
		return templateNumber;
	}


	public void setTemplateNumber(Long templateNumber) {
		this.templateNumber = templateNumber;
	}


	public Long getTemplateVersion() {
		return templateVersion;
	}


	public void setTemplateVersion(Long templateVersion) {
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


	public List<String> getCustomerId() {
		return customerId;
	}


	public void setCustomerId(List<String> customerId) {
		this.customerId = customerId;
	}
	
	
	/*public LDVRTemplateDetailsType getLdvrTemplateDetType() {
		return ldvrTemplateDetType;
	}


	public void setLdvrTemplateDetType(LDVRTemplateDetailsType ldvrTemplateDetType) {
		this.ldvrTemplateDetType = ldvrTemplateDetType;
	}
*/

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


    public String getControllerConfiguration() {
        return controllerConfiguration;
    }


    public void setControllerConfiguration(String controllerConfiguration) {
        this.controllerConfiguration = controllerConfiguration;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    @Override
	public String toString() {
		String val = "applicationId: "+applicationId
						+"\nmessageId: "+messageId
						+"\ntemplateNumber: "+templateNumber
						+"\ntemplateVersion: "+templateVersion
						+"\ntemplateStatus: "+templateStatus
						+"\ndescription: "+description
						+"\ncustomerId: "+customerId;						
		return val;
	}
	
}

