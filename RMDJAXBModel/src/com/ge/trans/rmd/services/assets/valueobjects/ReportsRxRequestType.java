package com.ge.trans.rmd.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author 212600432
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reportsRxRequestType", propOrder = { "mineId", "truckId", "rxType", "fromDate", "toDate", "isAvgCalc", "isGeLevelReport", "urgency" })

@XmlRootElement
public class ReportsRxRequestType {
	protected String mineId;
	protected String truckId;
	protected String rxType;
	protected String urgency;
	protected String fromDate;
	protected String toDate;
	protected boolean isAvgCalc;
	protected boolean isGeLevelReport;
	

	
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public boolean isGeLevelReport() {
		return isGeLevelReport;
	}
	public void setGeLevelReport(boolean isGeLevelReport) {
		this.isGeLevelReport = isGeLevelReport;
	}
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
	public String getRxType() {
		return rxType;
	}
	public void setRxType(String rxType) {
		this.rxType = rxType;
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
	
	public boolean isAvgCalc() {
		return isAvgCalc;
	}
	public void setAvgCalc(boolean isAvgCalc) {
		this.isAvgCalc = isAvgCalc;
	}
	public ReportsRxRequestType(){}
	
	public ReportsRxRequestType(String mineId,
			String truckId, String rxType,String urgency, String fromDate, String toDate) {
		super();
		this.mineId = mineId;
		this.truckId = truckId;
		this.rxType = rxType;
		this.urgency = urgency;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	public ReportsRxRequestType(String mineId,
			String truckId, String rxType, String fromDate, String toDate , boolean isAvgCalc) {
			super();
			this.mineId = mineId;
			this.truckId = truckId;
			this.rxType = rxType;
			this.fromDate = fromDate;
			this.toDate = toDate;
			this.isAvgCalc = isAvgCalc;
	}
	
	public ReportsRxRequestType(String mineId, boolean isGeLevelReport, String truckId, String rxType, String fromDate, String toDate) {
		super();
		this.mineId = mineId;
		this.isGeLevelReport = isGeLevelReport;
		this.truckId = truckId;
		this.rxType = rxType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		
	}
	
}
