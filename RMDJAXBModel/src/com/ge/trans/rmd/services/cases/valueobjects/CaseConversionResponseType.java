package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseConversionResponseType", propOrder = {"year", "month",
		"convCount","rxTitle","rxCount"
})
@XmlRootElement
public class CaseConversionResponseType {
	@XmlElement(required = true)
	protected String year;
	@XmlElement(required = true)
	protected String month;
	@XmlElement(required = true)
	protected String convCount;
	@XmlElement(required = true)
	protected String rxTitle;
	@XmlElement(required = true)
	protected String rxCount;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getConvCount() {
		return convCount;
	}
	public void setConvCount(String convCount) {
		this.convCount = convCount;
	}
	public String getRxTitle() {
		return rxTitle;
	}
	public void setRxTitle(String rxTitle) {
		this.rxTitle = rxTitle;
	}
	public String getRxCount() {
		return rxCount;
	}
	public void setRxCount(String rxCount) {
		this.rxCount = rxCount;
	}

	

}
