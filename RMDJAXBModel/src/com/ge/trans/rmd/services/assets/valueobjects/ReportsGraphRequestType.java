package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsGraphRequestType", propOrder = { "mineId", "truckId", "fromDate", "toDate", "period"})

@XmlRootElement
public class ReportsGraphRequestType {
	protected String mineId;
	protected String truckId;
	protected String fromDate;
	protected String toDate;
	protected int period;
	public String getMineId() {
		return mineId;
	}
	public void setMineId(String mineId) {
		this.mineId = mineId;
	}
	public String getTruckId() {
		return truckId;
	}
	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public ReportsGraphRequestType(){}
	
	public ReportsGraphRequestType(String mineId, String truckId,
			String fromDate, String toDate, int period) {
		super();
		this.mineId = mineId;
		this.truckId = truckId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.period = period;
	}
	
	public ReportsGraphRequestType(String mineId, String truckId, int period) {
		super();
		this.mineId = mineId;
		this.truckId = truckId;
		this.period = period;
	}

}
