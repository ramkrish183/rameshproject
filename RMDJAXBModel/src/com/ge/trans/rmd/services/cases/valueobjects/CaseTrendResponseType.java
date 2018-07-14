package com.ge.trans.rmd.services.cases.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CaseTrendResponseType", propOrder = {"creationRXDate", "day",
		"rxCount","problemDesc","customer"
})
@XmlRootElement
public class CaseTrendResponseType {
	@XmlElement(required = true)
	protected String creationRXDate;
	@XmlElement(required = true)
	protected String day;
	@XmlElement(required = true)
	protected String rxCount;
	@XmlElement(required = true)
	protected String problemDesc;
	@XmlElement(required = true)
	protected String customer;
	public String getCreationRXDate() {
		return creationRXDate;
	}
	public void setCreationRXDate(String creationRXDate) {
		this.creationRXDate = creationRXDate;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getRxCount() {
		return rxCount;
	}
	public void setRxCount(String rxCount) {
		this.rxCount = rxCount;
	}
	public String getProblemDesc() {
		return problemDesc;
	}
	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	

}