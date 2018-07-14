package com.ge.trans.eoa.services.kpi.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class KpiAssetCountEoaServiceVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String assetNumber;
    private String assetGroupName;
    private String customerId;
    private Long numDays;
    private String kpiName;

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(final String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getAssetGroupName() {
        return assetGroupName;
    }

    public void setAssetGroupName(final String assetGroupName) {
        this.assetGroupName = assetGroupName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public Long getNumDays() {
        return numDays;
    }

    public void setNumDays(final Long numDays) {
        this.numDays = numDays;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(final String kpiName) {
        this.kpiName = kpiName;
    }

}
