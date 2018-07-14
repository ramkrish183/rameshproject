package com.ge.trans.rmd.services.solutions.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "solutionParameterRequest", propOrder = {"solutionModels"})
@XmlRootElement
public class SolutionParameterRequest {

    @XmlElement(required = true)
    protected List<String> solutionModels;

	public List<String> getSolutionModels() {
		return solutionModels;
	}

	public void setSolutionModels(List<String> solutionModels) {
		this.solutionModels = solutionModels;
	}
}
