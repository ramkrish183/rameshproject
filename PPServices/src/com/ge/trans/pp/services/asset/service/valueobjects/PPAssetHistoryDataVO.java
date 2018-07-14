package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;
import java.util.Map;
public class PPAssetHistoryDataVO {
	
	private List<Map<String,String>> ppAssetHistorylst;
	private int recordsTotal;
	public List<Map<String,String>> getPpAssetHistorylst() {
		return ppAssetHistorylst;
	}
	public void setPpAssetHistorylst(List<Map<String,String>> ppAssetHistorylst) {
		this.ppAssetHistorylst = ppAssetHistorylst;
	}
	public int getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	} 

}
