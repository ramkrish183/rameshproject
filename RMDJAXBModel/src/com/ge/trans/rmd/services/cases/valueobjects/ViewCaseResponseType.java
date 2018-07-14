package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "viewCaseResponseType", propOrder = {
    "caseObjid",
    "strCaseId",
    "condition",
    "strPriority",
    "strTitle",
    "strReason",
    "dtCreationDate",
    "caseType",
    "isAppend",
    "strQueue",
    "closeDate",    
    "strOwner"    
})

@XmlRootElement
public class ViewCaseResponseType {
    protected Long caseObjid;
    protected String strCaseId;
    protected String condition;
    protected String strPriority;
    protected String strTitle;
    protected String strReason;
    protected String dtCreationDate;
    protected String caseType;
    protected String isAppend;
    protected String strQueue;
    protected String closeDate; 
    protected String strOwner;
    
    public Long getCaseObjid() {
        return caseObjid;
    }
    public void setCaseObjid(Long caseObjid) {
        this.caseObjid = caseObjid;
    }
    public String getStrCaseId() {
        return strCaseId;
    }
    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }
    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
    public String getStrPriority() {
        return strPriority;
    }
    public void setStrPriority(String strPriority) {
        this.strPriority = strPriority;
    }
    public String getStrTitle() {
        return strTitle;
    }
    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }
    public String getStrReason() {
        return strReason;
    }
    public void setStrReason(String strReason) {
        this.strReason = strReason;
    }
    public String getDtCreationDate() {
        return dtCreationDate;
    }
    public void setDtCreationDate(String dtCreationDate) {
        this.dtCreationDate = dtCreationDate;
    }
    public String getCaseType() {
        return caseType;
    }
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
    public String getIsAppend() {
        return isAppend;
    }
    public void setIsAppend(String isAppend) {
        this.isAppend = isAppend;
    }
    public String getStrQueue() {
        return strQueue;
    }
    public void setStrQueue(String strQueue) {
        this.strQueue = strQueue;
    }
    public String getCloseDate() {
        return closeDate;
    }
    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }
    public String getStrOwner() {
        return strOwner;
    }
    public void setStrOwner(String strOwner) {
        this.strOwner = strOwner;
    }
}