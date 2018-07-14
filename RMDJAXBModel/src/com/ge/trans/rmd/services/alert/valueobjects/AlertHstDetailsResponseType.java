package com.ge.trans.rmd.services.alert.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "alertHstDetailsResponseType", propOrder = {
        "alertHdrResponseTypelst", "count" })
public class AlertHstDetailsResponseType {

    @XmlElement(required = true)
    protected List<AlertHistoryResponseType> alertHdrResponseTypelst;
    @XmlElement(required = true)
    protected String count;

    public List<AlertHistoryResponseType> getAlertHdrResponseTypelst() {
        if (alertHdrResponseTypelst == null) {
            alertHdrResponseTypelst = new ArrayList<AlertHistoryResponseType>();
        }
        return alertHdrResponseTypelst;
    }

    public void setAlertHdrResponseTypelst(
            List<AlertHistoryResponseType> alertHdrResponseTypelst) {
        this.alertHdrResponseTypelst = alertHdrResponseTypelst;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
