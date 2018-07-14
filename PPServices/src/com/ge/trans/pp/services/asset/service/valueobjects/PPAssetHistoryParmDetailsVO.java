package com.ge.trans.pp.services.asset.service.valueobjects;

public class PPAssetHistoryParmDetailsVO {

    private String columnName;
    private String columnValue;
    private String recordCount;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public String getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}

	@Override
    public String toString() {
        return "PPAssetHistoryParmDetailsVO [columnName=" + columnName + ", columnValue=" + columnValue + "]";
    }

}
