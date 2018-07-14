package com.ge.trans.rmd.services.rule.valueobjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "parmData", propOrder = { "faultObjId", "recordType",
        "cmuTime", "incidentTime", "faultCode", "parmNames",
        "parmDisplayNames", "parmSet" })
public class AlertRuleParmResponseType {

    protected String faultObjId;
    protected String recordType;
    protected String cmuTime;
    protected String incidentTime;
    protected String faultCode;
    protected List<String> parmNames = new ArrayList<String>();
    protected List<String> parmDisplayNames = new ArrayList<String>();
    protected List<RuleParmData> parmSet = new ArrayList<RuleParmData>();

    @XmlElement(name = "RecordType")
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    @XmlElement(name = "CmuTime")
    public String getCmuTime() {
        return cmuTime;
    }

    public void setCmuTime(String cMUTime) {
        cmuTime = cMUTime;
    }

    @XmlElement(name = "IncidentTime")
    public String getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(String incidentTime) {
        this.incidentTime = incidentTime;
    }

    @XmlElement(required = true, name = "ParmNames")
    public List<String> getParmNames() {
        return parmNames;
    }

    public void setParmNames(List<String> parmNames) {
        this.parmNames = parmNames;
    }

    @XmlElementWrapper(name = "ruleParmData")
    @XmlElement(name = "parmValues")
    public List<RuleParmData> getParmSet() {
        return parmSet;
    }

    public void setParmSet(List<RuleParmData> parmSet) {
        this.parmSet = parmSet;
    }

    @XmlElement(name = "FaultCode")
    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    @XmlElement(required = true, name = "FaultObjId")
    public String getFaultObjId() {
        return faultObjId;
    }

    public void setFaultObjId(String faultObjId) {
        this.faultObjId = faultObjId;
    }

    @XmlElement(required = true, name = "ParmDisplayNames")
    public List<String> getParmDisplayNames() {
        return parmDisplayNames;
    }

    public void setParmDisplayNames(List<String> parmDisplayNames) {
        this.parmDisplayNames = parmDisplayNames;
    }
}
