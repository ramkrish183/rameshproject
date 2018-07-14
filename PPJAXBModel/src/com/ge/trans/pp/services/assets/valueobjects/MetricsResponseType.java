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
 *         &lt;element name="columnName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="screenName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fromUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="toUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="convReq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricsResponseType", propOrder = { "customerID", "columnName", "screenName", "fromUnit", "toUnit",
        "convReq" })
@XmlRootElement
public class MetricsResponseType {
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public String getConvReq() {
        return convReq;
    }

    public void setConvReq(String convReq) {
        this.convReq = convReq;
    }

    @XmlElement(required = true)
    protected String customerID;
    @XmlElement(required = true)
    protected String columnName;
    @XmlElement(required = true)
    protected String screenName;
    @XmlElement(required = true)
    protected String fromUnit;
    @XmlElement(required = true)
    protected String toUnit;
    @XmlElement(required = true)
    protected String convReq;
}
