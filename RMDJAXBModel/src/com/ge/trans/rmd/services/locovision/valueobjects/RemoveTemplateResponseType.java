package com.ge.trans.rmd.services.locovision.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "removeTemplateResponseType", propOrder = { "status","details"})

@XmlRootElement
public class RemoveTemplateResponseType {
	private String status;

	private List<RemoveTemplateStatus> details;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RemoveTemplateStatus> getDetails() {
		return details;
	}

	public void setDetails(List<RemoveTemplateStatus> details) {
		this.details = details;
	}
}
