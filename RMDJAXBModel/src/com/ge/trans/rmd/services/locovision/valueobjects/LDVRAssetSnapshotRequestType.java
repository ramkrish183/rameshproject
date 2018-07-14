package com.ge.trans.rmd.services.locovision.valueobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LDVRAssetSnapshotRequestType{
      
    protected String assetOwnerId;
    
    protected String roadInitial;
    
    protected String roadNumber;  
    
    protected String device;

    protected String requestType;
    
    
    public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
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
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Override
	public String toString() {
		String val = 	"assetOwnerId: "+assetOwnerId
						+"\nroadInitial: "+roadInitial
						+"\nroadNumber: "+roadNumber
						+"\ndevice: "+device
						+"\nrequestType: "+requestType;
						
		return val;
	}
	
}

