package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "unitRenumberingRequestType", propOrder = { "customerId","vehicleHeader","oldUnitNumber", "newUnitNumber"})

@XmlRootElement
public class UnitRenumberingRequestType {
	@XmlElement(required = true)
	protected String customerId;
	@XmlElement(required = true)
	protected String vehicleHeader;
	@XmlElement(required = true)
	protected String oldUnitNumber;
	@XmlElement(required = true)
	protected String newUnitNumber;
	
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
     * Gets the value of the oldUnitNumber property.
     * 
     * @return possible object is {@link oldUnitNumber }
     * 
     */
	public String getOldUnitNumber() {
		return oldUnitNumber;
	}
	
	/**
     * Sets the value of the oldUnitNumber property.
     * 
     * @param value
     *            allowed object is {@link oldUnitNumber }
     * 
     */
	public void setOldUnitNumber(String oldUnitNumber) {
		this.oldUnitNumber = oldUnitNumber;
	}
	
	/**
     * Gets the value of the newUnitNumber property.
     * 
     * @return possible object is {@link newUnitNumber }
     * 
     */
	public String getNewUnitNumber() {
		return newUnitNumber;
	}
	
	/**
     * Sets the value of the newUnitNumber property.
     * 
     * @param value
     *            allowed object is {@link newUnitNumber }
     * 
     */
	public void setNewUnitNumber(String newUnitNumber) {
		this.newUnitNumber = newUnitNumber;
	}

	/**
     * Gets the value of the vehicleHeader property.
     * 
     * @return possible object is {@link vehicleHeader }
     * 
     */
	public String getVehicleHeader() {
		return vehicleHeader;
	}

	/**
     * Sets the value of the vehicleHeader property.
     * 
     * @param value
     *            allowed object is {@link vehicleHeader }
     * 
     */
	public void setVehicleHeader(String vehicleHeader) {
		this.vehicleHeader = vehicleHeader;
	}
	
	
}
