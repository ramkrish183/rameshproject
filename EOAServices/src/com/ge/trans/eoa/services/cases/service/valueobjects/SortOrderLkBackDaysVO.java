package com.ge.trans.eoa.services.cases.service.valueobjects;

public class SortOrderLkBackDaysVO {

    private static final long serialVersionUID = 1L;

    private String defaultSortOrder;
    private String defaultLookBack;

    public SortOrderLkBackDaysVO(String defaultSortOrder, String defaultLookBack) {
        super();

        this.defaultSortOrder = defaultSortOrder;
        this.defaultLookBack = defaultLookBack;
    }

    public String getDefaultSortOrder() {
        return defaultSortOrder;
    }

    public void setDefaultSortOrder(String defaultSortOrder) {
        this.defaultSortOrder = defaultSortOrder;
    }

    public String getDefaultLookBack() {
        return defaultLookBack;
    }

    public void setDefaultLookBack(String defaultLookBack) {
        this.defaultLookBack = defaultLookBack;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
