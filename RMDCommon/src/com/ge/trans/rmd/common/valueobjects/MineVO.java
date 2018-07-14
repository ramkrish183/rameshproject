package com.ge.trans.rmd.common.valueobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineVO {
	private String mine;
	private String header;
	private String mineStatus;
	private String commStatus;
	private String messageReceived;
	private String healthReport;
	private String fromDate;
	private String toDate;
	
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

	List<TruckVO> trucks = new ArrayList<TruckVO>();
	Map<String, List<String>> mineParam = new HashMap<String, List<String>>();
	
	public String getMine() {
		return mine;
	}

	public void setMine(String mine) {
		this.mine = mine;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getMineStatus() {
		return mineStatus;
	}

	public void setMineStatus(String mineStatus) {
		this.mineStatus = mineStatus;
	}

	public String getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(String commStatus) {
		this.commStatus = commStatus;
	}

	public String getMessageReceived() {
		return messageReceived;
	}

	public void setMessageReceived(String messageReceived) {
		this.messageReceived = messageReceived;
	}

	public String getHealthReport() {
		return healthReport;
	}

	public void setHealthReport(String healthReport) {
		this.healthReport = healthReport;
	}

	public List<TruckVO> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<TruckVO> trucks) {
		this.trucks = trucks;
	}

	public Map<String, List<String>> getMineParam() {
		return mineParam;
	}

	public void setMineParam(Map<String, List<String>> mineParam) {
		this.mineParam = mineParam;
	}	
}
