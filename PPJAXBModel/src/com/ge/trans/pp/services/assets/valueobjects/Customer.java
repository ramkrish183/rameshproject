package com.ge.trans.pp.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for customer complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="customer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerSeqId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isAllCustomerdefault" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isAllCustomer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customer", propOrder = { "customerID", "customerName", "customerSeqId", "isDefault",
        "isAllCustomerdefault", "isAllCustomer" })
@XmlRootElement
public class Customer {

    @XmlElement(required = true)
    protected String customerID;
    @XmlElement(required = true)
    protected String customerName;
    @XmlElement(required = true)
    protected String customerSeqId;
    protected boolean isDefault;
    protected boolean isAllCustomerdefault;
    protected boolean isAllCustomer;

    /**
     * Gets the value of the customerID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Sets the value of the customerID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCustomerID(String value) {
        this.customerID = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerSeqId property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCustomerSeqId() {
        return customerSeqId;
    }

    /**
     * Sets the value of the customerSeqId property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCustomerSeqId(String value) {
        this.customerSeqId = value;
    }

    /**
     * Gets the value of the isDefault property.
     * 
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     */
    public void setIsDefault(boolean value) {
        this.isDefault = value;
    }

    /**
     * Gets the value of the isAllCustomerdefault property.
     * 
     */
    public boolean isIsAllCustomerdefault() {
        return isAllCustomerdefault;
    }

    /**
     * Sets the value of the isAllCustomerdefault property.
     * 
     */
    public void setIsAllCustomerdefault(boolean value) {
        this.isAllCustomerdefault = value;
    }

    /**
     * Gets the value of the isAllCustomerdefault property.
     * 
     */
    public boolean isIsAllCustomer() {
        return isAllCustomer;
    }

    /**
     * Sets the value of the isAllCustomerdefault property.
     * 
     */
    public void setIsAllCustomer(boolean value) {
        this.isAllCustomer = value;
    }
}
