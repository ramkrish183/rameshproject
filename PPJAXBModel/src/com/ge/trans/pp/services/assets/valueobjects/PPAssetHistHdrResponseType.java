package com.ge.trans.pp.services.assets.valueobjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pPAssetHistHdrResponseType", propOrder = { "displayName", "columnAliasName", "tableHdType",
        "AsetSrchType", "toolTipInfo", "colColp", "sortOrder","exportColumnName" })
@XmlRootElement
public class PPAssetHistHdrResponseType {

    @XmlElement(required = true)
    protected String displayName;
    @XmlElement(required = true)
    protected String columnAliasName;
    @XmlElement(required = true)
    protected String tableHdType;
    @XmlElement(required = true)
    protected String AsetSrchType;
    @XmlElement(required = true)
    protected String toolTipInfo;
    @XmlElement(required = true)
    protected String colColp;
    @XmlElement(required = true)
    protected String sortOrder;
    @XmlElement(required = true)
    protected String exportColumnName;
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
		return "PPAssetHistHdrResponseType [displayName=" + displayName
				+ ", columnAliasName=" + columnAliasName + ", tableHdType="
				+ tableHdType + ", AsetSrchType=" + AsetSrchType
				+ ", toolTipInfo=" + toolTipInfo + ", colColp=" + colColp
				+ ", sortOrder=" + sortOrder + ", exportColumnName="
				+ exportColumnName + "]";
	}

}
