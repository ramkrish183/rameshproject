package com.ge.trans.rmd.services.kpi.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for kpiInfoType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
* &lt;complexType name="kpiInfoType">
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element name="parameterName" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="parameterCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kpiInfoType", propOrder = { "parameterName", "parameterCount" })
@XmlRootElement
public class KpiInfoType {

    @XmlElement(required = true)
    protected String parameterName;
    @XmlElement(required = true)
    protected String parameterCount;

    /**
     * Gets the value of the parameterName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * Sets the value of the parameterName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setParameterName(String value) {
        this.parameterName = value;
    }

    /**
     * Gets the value of the parameterCount property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getParameterCount() {
        return parameterCount;
    }

    /**
     * Sets the value of the parameterCount property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setParameterCount(String value) {
        this.parameterCount = value;
    }

}
