package com.ge.trans.rmd.common.valueobjects;

public class ExtendedModelVO extends BaseVO {

    public ExtendedModelVO() {
        super();
    }

    private static final long serialVersionUID = 7945239794822514671L;

    private Long primaryKey;

    private Integer startRow;
    private Integer endRow;

    private String strSortColumn;
    private String strSortOrder;

    public Long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(final Long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(final Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getEndRow() {
        return endRow;
    }

    public void setEndRow(final Integer endRow) {
        this.endRow = endRow;
    }

    public String getStrSortColumn() {
        return strSortColumn;
    }

    public void setStrSortColumn(final String strSortColumn) {
        this.strSortColumn = strSortColumn;
    }

    public String getStrSortOrder() {
        return strSortOrder;
    }

    public void setStrSortOrder(final String strSortOrder) {
        this.strSortOrder = strSortOrder;
    }
}