package com.ge.trans.rmd.services.lhr.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "healthRulesRequest", propOrder = {
    "locomotives"
})
@XmlRootElement
public class HealthRulesRequest {
	
	@XmlElement(required = true)
	protected List<Locomotives> locomotives;

	public List<Locomotives> getLocomotives() {
		return locomotives;
	}

	public void setLocomotives(List<Locomotives> locomotives) {
		this.locomotives = locomotives;
	}
}
