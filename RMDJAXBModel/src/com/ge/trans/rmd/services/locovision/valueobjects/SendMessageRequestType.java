package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



/*@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendMesageRequestType", propOrder = {
    "messageId",
    "applicationId",
    "assetOwnerId",
    "roadInitial",
    "roadNumber",
    "requestorName",
    "device",
    "comments",
    "selectedCameras",
    "internalMemory",
    "externalMemory",
    "requestTitle",
    "startTime",
    "endTime",
    "importance",
    "deletionType"
})*/
//@XmlRootElement
public class SendMessageRequestType {

    
    protected String messageId;
    
    protected String applicationId;
    
    protected String assetOwnerId;
    
    protected String roadInitial;
    
    protected String roadNumber;
    
    protected String requestorName;
    
    protected String device;
    
    protected String comments;
    
    private String selectedCameras;    
    
	private String internalMemory;
    
    private String externalMemory;
    
	private String requestTitle;
    
    private String startTime;
    
	private String endTime;
    
	private String importance;
    
	private String deletionType;
   
    
    
    public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	/**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the applicationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the value of the applicationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationId(String value) {
        this.applicationId = value;
    }

    /**
     * Gets the value of the assetOwnerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetOwnerId() {
        return assetOwnerId;
    }

    /**
     * Sets the value of the assetOwnerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetOwnerId(String value) {
        this.assetOwnerId = value;
    }

    /**
     * Gets the value of the roadInitial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoadInitial() {
        return roadInitial;
    }

    /**
     * Sets the value of the roadInitial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoadInitial(String value) {
        this.roadInitial = value;
    }

    /**
     * Gets the value of the roadNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoadNumber() {
        return roadNumber;
    }

    /**
     * Sets the value of the roadNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoadNumber(String value) {
        this.roadNumber = value;
    }

    /**
     * Gets the value of the requestorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * Sets the value of the requestorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestorName(String value) {
        this.requestorName = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

	public String getSelectedCameras() {
		return selectedCameras;
	}

	public void setSelectedCameras(String selectedCameras) {
		this.selectedCameras = selectedCameras;
	}

	public String getInternalMemory() {
		return internalMemory;
	}

	public void setInternalMemory(String internalMemory) {
		this.internalMemory = internalMemory;
	}

	public String getExternalMemory() {
		return externalMemory;
	}

	public void setExternalMemory(String externalMemory) {
		this.externalMemory = externalMemory;
	}

	public String getRequestTitle() {
		return requestTitle;
	}

	public void setRequestTitle(String requestTitle) {
		this.requestTitle = requestTitle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getDeletionType() {
		return deletionType;
	}

	public void setDeletionType(String deletionType) {
		this.deletionType = deletionType;
	}
	
	@Override
	public String toString() {
		String val = "applicationId: "+applicationId
						+"\nmessageId: "+messageId
						+"\nassetOwnerId: "+assetOwnerId
						+"\nroadInitial: "+roadInitial
						+"\nroadNumber: "+roadNumber
						+"\nrequestorName: "+requestorName
						+"\ndevice: "+device
						+"\ncomments: "+comments
						+"\ninternalMemory: "+internalMemory
						+"\nnetworkMemory: "+externalMemory
						+"\nselectedCameras: "+selectedCameras
						+"\nrequestTitle: "+requestTitle
						+"\nrequestStartTime: "+startTime
						+"\nrequestEndTime: "+endTime
						+"\nimportance: "+importance
						+"\nDeletionType: "+deletionType;
		return val;
	}
	
}

