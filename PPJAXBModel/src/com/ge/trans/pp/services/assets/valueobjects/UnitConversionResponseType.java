package com.ge.trans.pp.services.assets.valueobjects;
import java.util.List;

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
@XmlType(name = "unitConversionResponseType", propOrder = {"userId","lstUnitConversionBeanResponseType"})
@XmlRootElement
public class UnitConversionResponseType {
@XmlElement(required = true)
private String userId;
@XmlElement(required = true)
protected List<UnitConversionBeanResponseType> lstUnitConversionBeanResponseType;
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public List<UnitConversionBeanResponseType> getLstUnitConversionBeanResponseType() {
	return lstUnitConversionBeanResponseType;
}
public void setLstUnitConversionBeanResponseType(
		List<UnitConversionBeanResponseType> lstUnitConversionBeanResponseType) {
	this.lstUnitConversionBeanResponseType = lstUnitConversionBeanResponseType;
}
}
