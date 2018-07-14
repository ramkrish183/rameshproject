package com.ge.trans.pp.services.asset.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PPMetricsVO extends BaseVO {

    private String toUnit;
    private String convReq;
    private String fromUnit;
    private String customerName;
    private String screenName;
    private String columnName;
    private String userId;
private List<MetricsVO> lstMetricsVO;
    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public String getConvReq() {
        return convReq;
    }

    public void setConvReq(String convReq) {
        this.convReq = convReq;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<MetricsVO> getLstMetricsVO() {
		return lstMetricsVO;
	}

	public void setLstMetricsVO(List<MetricsVO> lstMetricsVO) {
		this.lstMetricsVO = lstMetricsVO;
	}

}
