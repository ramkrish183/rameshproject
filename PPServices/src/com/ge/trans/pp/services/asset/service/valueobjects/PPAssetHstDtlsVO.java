package com.ge.trans.pp.services.asset.service.valueobjects;

public class PPAssetHstDtlsVO {

    private String rnhId;
    private String rnId;
    private String customerName;
    private String days;
    private String fromDate;
    private String toDate;
    private String sortBy;
    private String roleId;
    public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getRnhId() {
        return rnhId;
    }

    public void setRnhId(String rnhId) {
        this.rnhId = rnhId;
    }

    public String getRnId() {
        return rnId;
    }

    public void setRnId(String rnId) {
        this.rnId = rnId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
    
    
    

}
