package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsTruckGraphResponseType", propOrder = { "loads","engineOpHrs","engineIdleTime","overloads","averageHP" })

@XmlRootElement
public class ReportsTruckGraphResponseType {
	private List<ReportsTimeSeriesResponseType> loads;
	private List<ReportsTimeSeriesResponseType> engineOpHrs;
	private List<ReportsTimeSeriesResponseType> engineIdleTime;
	private List<ReportsTimeSeriesResponseType> overloads;
	private List<ReportsTimeSeriesResponseType> averageHP;
	
	public List<ReportsTimeSeriesResponseType> getLoads() {
		return loads;
	}
	public void setLoads(List<ReportsTimeSeriesResponseType> loads) {
		this.loads = loads;
	}
	public List<ReportsTimeSeriesResponseType> getEngineOpHrs() {
		return engineOpHrs;
	}
	public void setEngineOpHrs(List<ReportsTimeSeriesResponseType> engineOpHrs) {
		this.engineOpHrs = engineOpHrs;
	}
	public List<ReportsTimeSeriesResponseType> getEngineIdleTime() {
		return engineIdleTime;
	}
	public void setEngineIdleTime(List<ReportsTimeSeriesResponseType> engineIdleTime) {
		this.engineIdleTime = engineIdleTime;
	}
	public List<ReportsTimeSeriesResponseType> getOverloads() {
		return overloads;
	}
	public void setOverloads(List<ReportsTimeSeriesResponseType> overloads) {
		this.overloads = overloads;
	}
	public List<ReportsTimeSeriesResponseType> getAverageHP() {
		return averageHP;
	}
	public void setAverageHP(List<ReportsTimeSeriesResponseType> averageHP) {
		this.averageHP = averageHP;
	}
}
