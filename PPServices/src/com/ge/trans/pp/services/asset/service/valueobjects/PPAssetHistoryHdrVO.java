package com.ge.trans.pp.services.asset.service.valueobjects;

public class PPAssetHistoryHdrVO {

    private String displayName;
    private String columnAliasName;
    private String tableHdType;
    private String AsetSrchType;
    private String toolTipInfo;
    private String colColp;
    private String sortOrder;
    private String exportColumnName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getColumnAliasName() {
        return columnAliasName;
    }

    public void setColumnAliasName(String columnAliasName) {
        this.columnAliasName = columnAliasName;
    }

    public String getTableHdType() {
        return tableHdType;
    }

    public void setTableHdType(String tableHdType) {
        this.tableHdType = tableHdType;
    }

    public String getAsetSrchType() {
        return AsetSrchType;
    }

    public void setAsetSrchType(String asetSrchType) {
        AsetSrchType = asetSrchType;
    }

    public String getToolTipInfo() {
        return toolTipInfo;
    }

    public void setToolTipInfo(String toolTipInfo) {
        this.toolTipInfo = toolTipInfo;
    }

    public String getColColp() {
        return colColp;
    }

    public void setColColp(String colColp) {
        this.colColp = colColp;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

	public String getExportColumnName() {
		return exportColumnName;
	}

	public void setExportColumnName(String exportColumnName) {
		this.exportColumnName = exportColumnName;
	}

	@Override
	public String toString() {
		return "PPAssetHistoryHdrVO [displayName=" + displayName
				+ ", columnAliasName=" + columnAliasName + ", tableHdType="
				+ tableHdType + ", AsetSrchType=" + AsetSrchType
				+ ", toolTipInfo=" + toolTipInfo + ", colColp=" + colColp
				+ ", sortOrder=" + sortOrder + ", exportColumnName="
				+ exportColumnName + "]";
	}

}
