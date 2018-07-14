package com.ge.trans.rmd.services.lhr.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "locomotiveCommunicationRequest", propOrder = {
    "locomotiveIds"
})
@XmlRootElement
public class LocomotiveCommunicationRequest {
	@XmlElement(required = true)
	protected List<Long> locomotives;

	public List<Long> getLocomotives() {
		return locomotives;
	}

	public void setLocomotives(List<Long> locomotives) {
		this.locomotives = locomotives;
	}
}
