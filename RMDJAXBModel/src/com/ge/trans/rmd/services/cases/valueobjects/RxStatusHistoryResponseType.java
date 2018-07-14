package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rxStatusHistoryResponseType", propOrder = { "rxStatusDate", "status", "comments" })
@XmlRootElement
public class RxStatusHistoryResponseType {
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rxStatusDate;
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String comments;

    public XMLGregorianCalendar getRxStatusDate() {
        return rxStatusDate;
    }

    public void setRxStatusDate(XMLGregorianCalendar rxStatusDate) {
        this.rxStatusDate = rxStatusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
