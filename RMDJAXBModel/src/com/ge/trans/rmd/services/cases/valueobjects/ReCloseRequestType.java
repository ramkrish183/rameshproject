package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reCloseRequestType", propOrder = { "userId", "caseId", "reCloseAction", "appendCaseId"

})
@XmlRootElement
public class ReCloseRequestType {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String caseId;
    @XmlElement(required = true)
    protected String reCloseAction;
    @XmlElement(required = true)
    protected String appendCaseId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getReCloseAction() {
        return reCloseAction;
    }

    public void setReCloseAction(String reCloseAction) {
        this.reCloseAction = reCloseAction;
    }

    public String getAppendCaseId() {
        return appendCaseId;
    }

    public void setAppendCaseId(String appendCaseId) {
        this.appendCaseId = appendCaseId;
    }

}
