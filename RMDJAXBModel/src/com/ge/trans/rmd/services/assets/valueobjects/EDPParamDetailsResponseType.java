package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eDPParamDetailsResponseType", propOrder = { "paramObjId",
		"paramNo", "ctrlName", "paramDesc", "uom", "usageRate", "destType" })
@XmlRootElement
public class EDPParamDetailsResponseType {

	@XmlElement(required = true)
	protected String paramObjId;

	@XmlElement(required = true)
	protected String paramNo;

	@XmlElement(required = true)
	protected String ctrlName;

	@XmlElement(required = true)
	protected String paramDesc;

	@XmlElement(required = true)
	protected String uom;

	@XmlElement(required = true)
	protected String usageRate;

	@XmlElement(required = true)
	protected String destType;

	public String getParamObjId() {
		return this.paramObjId;
	}

	public void setParamObjId(String paramObjId) {
		this.paramObjId = paramObjId;
	}

	public String getParamNo() {
		return this.paramNo;
	}

	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
	}

	public String getCtrlName() {
		return this.ctrlName;
	}

	public void setCtrlName(String ctrlName) {
		this.ctrlName = ctrlName;
	}

	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getUom() {
		return this.uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getUsageRate() {
		return this.usageRate;
	}

	public void setUsageRate(String usageRate) {
		this.usageRate = usageRate;
	}

	public String getDestType() {
		return this.destType;
	}

	public void setDestType(String destType) {
		this.destType = destType;
	}
}