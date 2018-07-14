package com.ge.trans.eoa.services.kpi.service.valueobjects;

import java.util.List;

public class KpiAssetCountResponseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String assetNumber;
    private String assetGroupName;
    private String customerId;
    private Long numDays;
    private String kpiName;
    private Long totalCount;
    private List<RxUrgencyInfoEoaVO> urgInfoVO;

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

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(final Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<RxUrgencyInfoEoaVO> getUrgInfoVO() {
        return urgInfoVO;
    }

    public void setUrgInfoVO(final List<RxUrgencyInfoEoaVO> urgInfoVO) {
        this.urgInfoVO = urgInfoVO;
    }

}
