package com.ge.trans.rmd.services.solutions.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solutionLiteResponseType", propOrder = { "solutionId", "solutionTitle" })

@XmlRootElement
public class SolutionLiteResponseType {

    @XmlElement(required = true)
    protected String solutionId;
    @XmlElement(required = true)
    protected String solutionTitle;

    public String getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public String getSolutionTitle() {
        return solutionTitle;
    }

    public void setSolutionTitle(String solutionTitle) {
        this.solutionTitle = solutionTitle;
    }

}
