package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caseScoreRepairCodesResponseType", propOrder = {"repairCode", "description",
        "repairCodeId" })
@XmlRootElement
public class CaseScoreRepairCodesResponseType {
    protected String repairCode;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String repairCodeId;

    public String getRepairCode() {
        return repairCode;
    }

    public void setRepairCode(String repairCode) {
        this.repairCode = repairCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepairCodeId() {
        return repairCodeId;
    }

    public void setRepairCodeId(String repairCodeId) {
        this.repairCodeId = repairCodeId;
    }

}
