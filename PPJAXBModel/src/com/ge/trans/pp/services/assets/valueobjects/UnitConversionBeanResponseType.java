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
@XmlType(name = "unitConversionBeanResponseType", propOrder = { "paramId", "convertedValue", "unitName"})
@XmlRootElement
public class UnitConversionBeanResponseType {
@XmlElement(required = true)	
private String unitName;
@XmlElement(required = true)
private String paramId;
@XmlElement(required = true)
private String convertedValue;
public String getUnitName() {
	return unitName;
}
public void setUnitName(String unitName) {
	this.unitName = unitName;
}
public String getParamId() {
	return paramId;
}
public void setParamId(String paramId) {
	this.paramId = paramId;
}
public String getConvertedValue() {
	return convertedValue;
}
public void setConvertedValue(String convertedValue) {
	this.convertedValue = convertedValue;
}

}
