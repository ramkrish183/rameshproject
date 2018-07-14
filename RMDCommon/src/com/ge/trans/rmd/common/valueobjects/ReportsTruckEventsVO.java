package com.ge.trans.rmd.common.valueobjects;

import java.util.Date;

public class ReportsTruckEventsVO {
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
