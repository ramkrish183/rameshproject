package com.ge.trans.rmd.services.rule.valueobjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "parmNameValue", propOrder = { "parmName", "parmValue" })
public class RuleParmData {

    private String parmName;
    private String parmValue;

    @XmlElement(name = "ParmName")
    public String getParmName() {
        return parmName;
    }

    public void setParmName(String parmName) {
        this.parmName = parmName;
    }

    @XmlElement(name = "ParmValue")
    public String getParmValue() {
        return parmValue;
    }

    public void setParmValue(String parmValue) {
        this.parmValue = parmValue;
    }

}
