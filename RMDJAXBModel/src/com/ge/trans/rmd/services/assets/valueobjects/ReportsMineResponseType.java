package com.ge.trans.rmd.services.assets.valueobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportsMineResponseType", propOrder = { "mine","header","mineStatus","commStatus","messageReceived","healthReport","trucks","mineParams" })

@XmlRootElement
public class ReportsMineResponseType {
	private String mine;
	private String header;
	private String mineStatus;
	private String commStatus;
	private String messageReceived;
	private String healthReport;
	
	List<ReportsTruckInfoResponseType> trucks = new ArrayList<ReportsTruckInfoResponseType>();
	Map<String, ReportsListWrapper> mineParams = new LinkedHashMap<String, ReportsListWrapper>();
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

	public List<ReportsTruckInfoResponseType> getTrucks() {
		return trucks;
	}

	public void setTrucks(List<ReportsTruckInfoResponseType> trucks) {
		this.trucks = trucks;
	}

	public Map<String, ReportsListWrapper> getMineParams() {
		return mineParams;
	}

	public void setMineParams(Map<String, ReportsListWrapper> mineParams) {
		this.mineParams = mineParams;
	}
	
}
