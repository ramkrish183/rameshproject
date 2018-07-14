package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

public class PPAssetHistoryDetailsVO {

    private List<PPAssetHistoryHdrVO> ppAssetHistoryHdrlst;
    private List<PPAssetHistoryVO> ppAssetHistorylst;
    private List<JSONObject> ppAssetHistoryValue;
    private int totalRecord;

    public List<PPAssetHistoryHdrVO> getPpAssetHistoryHdrlst() {
        return ppAssetHistoryHdrlst;
    }

    public void setPpAssetHistoryHdrlst(List<PPAssetHistoryHdrVO> ppAssetHistoryHdrlst) {
        this.ppAssetHistoryHdrlst = ppAssetHistoryHdrlst;
    }

    public List<PPAssetHistoryVO> getPpAssetHistorylst() {
        return ppAssetHistorylst;
    }

    public void setPpAssetHistorylst(List<PPAssetHistoryVO> ppAssetHistorylst) {
        this.ppAssetHistorylst = ppAssetHistorylst;
    }

    public List<JSONObject> getPpAssetHistoryValue() {
		return ppAssetHistoryValue;
	}

	public void setPpAssetHistoryValue(List<JSONObject> ppAssetHistoryValue) {
		this.ppAssetHistoryValue = ppAssetHistoryValue;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	@Override
    public String toString() {
        return "PPAssetHistoryDetailsVO [ppAssetHistoryHdrlst=" + ppAssetHistoryHdrlst + ", ppAssetHistorylst="
                + ppAssetHistorylst + "]";
    }

}
