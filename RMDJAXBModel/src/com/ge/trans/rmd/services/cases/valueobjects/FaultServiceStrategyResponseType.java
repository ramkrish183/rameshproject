package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faultServiceStrategyResponseType", propOrder = { "lstDiagnosticWeight", "lstSubSysWeight",
        "lstModeRestriction", "lstFaultClassification", "fsObjId", "faultOrigin", "faultDesc", "faultLabel",
        "faultNotes", "lastUpdatedBy", "lastUpdatedDate", "faultSubId", "faultCode", "faultCriticalFlag",
        "faultLagTime", "faultRule", "faultRuleDesc", "fltStart2FltCode", "userId", "cmAliasName" })
@XmlRootElement
public class FaultServiceStrategyResponseType {
    @XmlElement(required = true)
    protected String lstDiagnosticWeight;
    @XmlElement(required = true)
    protected String lstSubSysWeight;
    @XmlElement(required = true)
    protected String lstModeRestriction;
    @XmlElement(required = true)
    protected String lstFaultClassification;
    @XmlElement(required = true)
    protected String fsObjId;
    @XmlElement(required = true)
    protected String faultOrigin;
    @XmlElement(required = true)
    protected String faultDesc;
    @XmlElement(required = true)
    protected String faultLabel;
    @XmlElement(required = true)
    protected String faultNotes;
    @XmlElement(required = true)
    protected String lastUpdatedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastUpdatedDate;
    @XmlElement(required = true)
    protected String faultSubId;
    @XmlElement(required = true)
    protected String faultCode;
    @XmlElement(required = true)
    protected String faultCriticalFlag;
    @XmlElement(required = true)
    protected String faultLagTime;
    @XmlElement(required = true)
    protected String faultRule;
    @XmlElement(required = true)
    protected String faultRuleDesc;
    @XmlElement(required = true)
    protected String fltStart2FltCode;
    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String cmAliasName;

    public String getLstDiagnosticWeight() {
        return lstDiagnosticWeight;
    }

    public void setLstDiagnosticWeight(String lstDiagnosticWeight) {
        this.lstDiagnosticWeight = lstDiagnosticWeight;
    }

    public String getLstSubSysWeight() {
        return lstSubSysWeight;
    }

    public void setLstSubSysWeight(String lstSubSysWeight) {
        this.lstSubSysWeight = lstSubSysWeight;
    }

    public String getLstModeRestriction() {
        return lstModeRestriction;
    }

    public void setLstModeRestriction(String lstModeRestriction) {
        this.lstModeRestriction = lstModeRestriction;
    }

    public String getLstFaultClassification() {
        return lstFaultClassification;
    }

    public void setLstFaultClassification(String lstFaultClassification) {
        this.lstFaultClassification = lstFaultClassification;
    }

    public String getFsObjId() {
        return fsObjId;
    }

    public void setFsObjId(String fsObjId) {
        this.fsObjId = fsObjId;
    }

    public String getFaultOrigin() {
        return faultOrigin;
    }

    public void setFaultOrigin(String faultOrigin) {
        this.faultOrigin = faultOrigin;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultLabel() {
        return faultLabel;
    }

    public void setFaultLabel(String faultLabel) {
        this.faultLabel = faultLabel;
    }

    public String getFaultNotes() {
        return faultNotes;
    }

    public void setFaultNotes(String faultNotes) {
        this.faultNotes = faultNotes;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public XMLGregorianCalendar getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(XMLGregorianCalendar lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getFaultSubId() {
        return faultSubId;
    }

    public void setFaultSubId(String faultSubId) {
        this.faultSubId = faultSubId;
    }

    public String getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultCriticalFlag() {
        return faultCriticalFlag;
    }

    public void setFaultCriticalFlag(String faultCriticalFlag) {
        this.faultCriticalFlag = faultCriticalFlag;
    }

    public String getFaultLagTime() {
        return faultLagTime;
    }

    public void setFaultLagTime(String faultLagTime) {
        this.faultLagTime = faultLagTime;
    }

    public String getFaultRule() {
        return faultRule;
    }

    public void setFaultRule(String faultRule) {
        this.faultRule = faultRule;
    }

    public String getFaultRuleDesc() {
        return faultRuleDesc;
    }

    public void setFaultRuleDesc(String faultRuleDesc) {
        this.faultRuleDesc = faultRuleDesc;
    }

    public String getFltStart2FltCode() {
        return fltStart2FltCode;
    }

    public void setFltStart2FltCode(String fltStart2FltCode) {
        this.fltStart2FltCode = fltStart2FltCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCmAliasName() {
        return cmAliasName;
    }

    public void setCmAliasName(String cmAliasName) {
        this.cmAliasName = cmAliasName;
    }
}
