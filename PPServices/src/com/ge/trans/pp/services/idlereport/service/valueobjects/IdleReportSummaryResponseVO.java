package com.ge.trans.pp.services.idlereport.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class IdleReportSummaryResponseVO extends BaseVO {

    private String region;
    private String totalReported;
    private String ntMovingCnt;
    private String ntMovingEngOn;
    private String ntMovingEngOff;
    private String dwellCnt;
    private String engOnCnt;
    private String orderBy;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTotalReported() {
        return totalReported;
    }

    public void setTotalReported(String totalReported) {
        this.totalReported = totalReported;
    }

    public String getNtMovingCnt() {
        return ntMovingCnt;
    }

    public void setNtMovingCnt(String ntMovingCnt) {
        this.ntMovingCnt = ntMovingCnt;
    }

    public String getNtMovingEngOn() {
        return ntMovingEngOn;
    }

    public void setNtMovingEngOn(String ntMovingEngOn) {
        this.ntMovingEngOn = ntMovingEngOn;
    }

    public String getNtMovingEngOff() {
        return ntMovingEngOff;
    }

    public void setNtMovingEngOff(String ntMovingEngOff) {
        this.ntMovingEngOff = ntMovingEngOff;
    }

    public String getDwellCnt() {
        return dwellCnt;
    }

    public void setDwellCnt(String dwellCnt) {
        this.dwellCnt = dwellCnt;
    }

    public String getEngOnCnt() {
        return engOnCnt;
    }

    public void setEngOnCnt(String engOnCnt) {
        this.engOnCnt = engOnCnt;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
