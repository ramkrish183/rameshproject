package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

public class PPAssetHistoryVO {
    private List<PPAssetHistoryParmDetailsVO> ppAssetHistoryVOlst;
    private String recordCount;

    public List<PPAssetHistoryParmDetailsVO> getPpAssetHistoryVOlst() {
        return ppAssetHistoryVOlst;
    }

    public void setPpAssetHistoryVOlst(List<PPAssetHistoryParmDetailsVO> ppAssetHistoryVOlst) {
        this.ppAssetHistoryVOlst = ppAssetHistoryVOlst;
    }

    public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	@Override
    public String toString() {
        return "PPAssetHistoryVO [ppAssetHistoryVOlst=" + ppAssetHistoryVOlst + "]";
    }

    /*
     * public List<String> getPpAssetHistoryVOlst() { return
     * ppAssetHistoryVOlst; } public void setPpAssetHistoryVOlst(List<String>
     * ppAssetHistoryVOlst) { this.ppAssetHistoryVOlst = ppAssetHistoryVOlst; }
     */

    /*
     * @Override public String toString() { return
     * "PPAssetHistoryVO [ppAssetHistoryVOlst=" + ppAssetHistoryVOlst + "]"; }
     */

}
