package com.ge.trans.eoa.services.reports.service.valueobjects;

public class OHVReportsRxRequestVO {
	private String mineId;
	private String truckId;
	private String rxType;
	private String urgency;
	private String fromDate;
	private String toDate;
	private boolean isAvgCalc;
	

	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
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
	
}
