package com.ge.trans.rmd.common.valueobjects;

import java.util.List;

public class TruckGraphVO {
	private List<ReportsTimeSeriesVO> loads;
	private List<ReportsTimeSeriesVO> engineOpHrs;
	private List<ReportsTimeSeriesVO> engineIdleTime;
	private List<ReportsTimeSeriesVO> overloads;
	private List<ReportsTimeSeriesVO> averageHP;
	
	public List<ReportsTimeSeriesVO> getLoads() {
		return loads;
	}
	public void setLoads(List<ReportsTimeSeriesVO> loads) {
		this.loads = loads;
	}
	public List<ReportsTimeSeriesVO> getEngineOpHrs() {
		return engineOpHrs;
	}
	public void setEngineOpHrs(List<ReportsTimeSeriesVO> engineOpHrs) {
		this.engineOpHrs = engineOpHrs;
	}
	public List<ReportsTimeSeriesVO> getEngineIdleTime() {
		return engineIdleTime;
	}
	public void setEngineIdleTime(List<ReportsTimeSeriesVO> engineIdleTime) {
		this.engineIdleTime = engineIdleTime;
	}
	public List<ReportsTimeSeriesVO> getOverloads() {
		return overloads;
	}
	public void setOverloads(List<ReportsTimeSeriesVO> overloads) {
		this.overloads = overloads;
	}
	public List<ReportsTimeSeriesVO> getAverageHP() {
		return averageHP;
	}
	public void setAverageHP(List<ReportsTimeSeriesVO> averageHP) {
		this.averageHP = averageHP;
	}
}
