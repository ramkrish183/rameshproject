package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for headerInfoType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
* &lt;complexType name="headerInfoType">
*   &lt;complexContent>
*     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
*       &lt;sequence>
*         &lt;element name="graphName" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="graphMPNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="graphMPDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
*         &lt;element name="sortOrder" type="{http://www.w3.org/2001/XMLSchema}string"/>
*       &lt;/sequence>
*     &lt;/restriction>
*   &lt;/complexContent>
* &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visualizationEventDataResponseType", namespace = "http://omd/omdservices/assetservice", propOrder = {
        "eventOccurDate", "eventEndDate", "eventType", "eventSummary", "eventDetails", "strEventOccurDt",
        "strEventEndDt"

})
@XmlRootElement
public class VisualizationEventDataResponseType {

    @XmlElement(required = true)
    protected Long eventOccurDate;
    @XmlElement(required = true)
    protected Long eventEndDate;
    @XmlElement(required = true)
    protected String eventType;
    @XmlElement(required = true)
    protected String eventSummary;
    @XmlElement(required = true)
    protected String eventDetails;
    @XmlElement(required = true)
    protected String strEventOccurDt;
    @XmlElement(required = true)
    protected String strEventEndDt;

    public String getStrEventOccurDt() {
        return strEventOccurDt;
    }

    public void setStrEventOccurDt(String strEventOccurDt) {
        this.strEventOccurDt = strEventOccurDt;
    }

    public String getStrEventEndDt() {
        return strEventEndDt;
    }

    public void setStrEventEndDt(String strEventEndDt) {
        this.strEventEndDt = strEventEndDt;
    }

    public Long getEventOccurDate() {
        return eventOccurDate;
    }

    public void setEventOccurDate(Long eventOccurDate) {
        this.eventOccurDate = eventOccurDate;
    }

    public Long getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(Long eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

}
