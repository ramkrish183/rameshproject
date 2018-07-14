package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findCasesDetailsResponseType", propOrder = {
        "caseID",
        "title",
        "condition",
        "status",
        "contact",
        "creationTime",
        "caseType",
        "custRNH",
        "rn" ,
        "queue",
        "customerId"
})
@XmlRootElement
public class FindCasesDetailsResponseType 
{
    @XmlElement(required = true)
    protected String caseID;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected String condition;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String contact;
    @XmlElement(required = true)
    protected String creationTime;
    @XmlElement(required = true)
    protected String caseType;
    @XmlElement(required = true)
    protected String custRNH;
    @XmlElement(required = true)
    protected String rn;
    @XmlElement(required = true)
    protected String queue;
    @XmlElement(required = true)
    protected String customerId;
    
    public String getCaseID() {
        return caseID;
    }
    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
    public String getCaseType() {
        return caseType;
    }
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
    public String getCustRNH() {
        return custRNH;
    }
    public void setCustRNH(String custRNH) {
        this.custRNH = custRNH;
    }
    public String getRn() {
        return rn;
    }
    public void setRn(String rn) {
        this.rn = rn;
    }
    public String getQueue() {
        return queue;
    }
    public void setQueue(String queue) {
        this.queue = queue;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    } 
    
}
