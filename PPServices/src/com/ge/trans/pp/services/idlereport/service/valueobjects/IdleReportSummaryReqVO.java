package com.ge.trans.pp.services.idlereport.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class IdleReportSummaryReqVO extends BaseVO {

    private String customerId;
    private String elapsedTime;
    private String region;
    private String idleType;
    private List<String> products;

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIdleType() {
        return idleType;
    }

    public void setIdleType(String idleType) {
        this.idleType = idleType;
    }

}
