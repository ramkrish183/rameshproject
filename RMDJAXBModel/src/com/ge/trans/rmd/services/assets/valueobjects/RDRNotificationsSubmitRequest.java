package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for RDRNotificationsSubmitRequestVO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RDRNotificationsSubmitRequestVO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="assetNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="assetGrpName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="customerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RDRNotificationsSubmitRequestVO", propOrder = { "userID", "assetNumber", "assetGrpName", "customerID",
        "fromDate", "toDate", "status"})
@XmlRootElement
public class RDRNotificationsSubmitRequest {
	
	@XmlElement(required = true)
	protected String userID;
	@XmlElement(required = true)
	protected String assetNumber;
	@XmlElement(required = true)
	protected String assetGrpName;
	@XmlElement(required = true)
	protected String customerID;
	@XmlElement(required = true)
	protected String fromDate;
	@XmlElement(required = true)
	protected String toDate;
	@XmlElement(required = true)
	protected String status;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getAssetNumber() {
		return assetNumber;
	}
	public void setAssetNumber(String assetNumber) {
		this.assetNumber = assetNumber;
	}
	public String getAssetGrpName() {
		return assetGrpName;
	}
	public void setAssetGrpName(String assetGrpName) {
		this.assetGrpName = assetGrpName;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
