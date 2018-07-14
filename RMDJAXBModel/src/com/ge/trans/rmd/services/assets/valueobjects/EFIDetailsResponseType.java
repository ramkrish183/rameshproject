package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eFIDetailsResponseType", propOrder = { "tempObjId",
		"templateNo", "versionNo", "isActive" })
@XmlRootElement
public class EFIDetailsResponseType {

	@XmlElement(required = true)
	protected String tempObjId;

	@XmlElement(required = true)
	protected String templateNo;

	@XmlElement(required = true)
	protected String versionNo;

	@XmlElement(required = true)
	protected String isActive;

	public String getTempObjId() {
		return this.tempObjId;
	}

	public void setTempObjId(String tempObjId) {
		this.tempObjId = tempObjId;
	}

	public String getTemplateNo() {
		return this.templateNo;
	}

	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}

	public String getVersionNo() {
		return this.versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}