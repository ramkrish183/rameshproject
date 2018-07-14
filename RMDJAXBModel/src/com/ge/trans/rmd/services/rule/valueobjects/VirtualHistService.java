package com.ge.trans.rmd.services.rule.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "virtualHistService", propOrder = { "strDateCreated", "createdBy", "versionNumber", "note"})
@XmlRootElement
public class VirtualHistService {
	@XmlElement(required = true)
	protected String strDateCreated;
	@XmlElement(required = true)
	protected String createdBy;
	@XmlElement(required = true)
	protected String versionNumber;
	@XmlElement(required = true)
	protected String note;
	public String getStrDateCreated() {
		return strDateCreated;
	}
	public void setStrDateCreated(String strDateCreated) {
		this.strDateCreated = strDateCreated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
