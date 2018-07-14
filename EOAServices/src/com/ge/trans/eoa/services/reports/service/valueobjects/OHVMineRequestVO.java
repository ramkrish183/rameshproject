package com.ge.trans.eoa.services.reports.service.valueobjects;

public class OHVMineRequestVO {
	private String mineId;
	private String fromDate;
	private String toDate;
	private boolean isGeLevelReport;
	
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
}
