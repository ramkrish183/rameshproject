package com.ge.trans.rmd.common.valueobjects;

public class ReportsTimeSeriesVO {
	private String timestamp;
	private double value;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
