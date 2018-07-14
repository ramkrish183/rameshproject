package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsTruckEventsResponseType", propOrder = { "eventNumber","subId","description", "occurTime", "resetTime", "numberOfOccurance", "trucksPerDay" })

@XmlRootElement
public class ReportsTruckEventsResponseType {
	String eventNumber;
	String subId;
	String description;
	String occurTime;
	String resetTime;
	String numberOfOccurance;
	String trucksPerDay;
	
	public String getEventNumber() {
		return eventNumber;
	}
	public void setEventNumber(String eventNumber) {
		this.eventNumber = eventNumber;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOccurTime() {
		return occurTime;
	}
	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}
	public String getResetTime() {
		return resetTime;
	}
	public void setResetTime(String resetTime) {
		this.resetTime = resetTime;
	}
	public String getNumberOfOccurance() {
		return numberOfOccurance;
	}
	public void setNumberOfOccurance(String numberOfOccurance) {
		this.numberOfOccurance = numberOfOccurance;
	}
	public String getTrucksPerDay() {
		return trucksPerDay;
	}
	public void setTrucksPerDay(String trucksPerDay) {
		this.trucksPerDay = trucksPerDay;
	}

}
