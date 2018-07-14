package com.ge.trans.pp.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitConversionBeanType", propOrder = { "paramId", "convertValue"})
@XmlRootElement
public class UnitConversionBeanType {
	@XmlElement(required = true)
	private String paramId;
	@XmlElement(required = true)
	private String convertValue;
	public String getParamId() {
		return paramId;
	}
	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
	public String getConvertValue() {
		return convertValue;
	}
	public void setConvertValue(String convertValue) {
		this.convertValue = convertValue;
	}
}
