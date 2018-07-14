package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.Date;

public class PerformanceHealthDataVO {
    private long objid;
    private int perfCheckId;
    private RxVO mostCriticalRx = null;
    private float healthScore = 0;
    private double confidence = 1.0;
    private Date calculationDate = null;
    private String performanceCheckName;
    private String performanceCheckCode;

    public PerformanceHealthDataVO() {

    }

    public PerformanceHealthDataVO(int perfCheckId, VehicleVO vehicle, RxVO mostCriticalRx, float healthFactor,
            Date calculationDate) {
        this.perfCheckId = perfCheckId;
        this.mostCriticalRx = mostCriticalRx;
        this.healthScore = healthFactor;
        this.calculationDate = calculationDate;
    }

    public long getObjid() {
        return objid;
    }

    public void setObjid(long objid) {
        this.objid = objid;
    }

    public int getPerfCheckId() {
        return perfCheckId;
    }

    public void setPerfCheckId(int perfCheckId) {
        this.perfCheckId = perfCheckId;
    }

    public RxVO getMostCriticalRx() {
        return mostCriticalRx;
    }

    public void setMostCriticalRx(RxVO mostCriticalRx) {
        this.mostCriticalRx = mostCriticalRx;
    }

    public float getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(float healthScore) {
        this.healthScore = healthScore;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public String getPerformanceCheckName() {
        return performanceCheckName;
    }

    public void setPerformanceCheckName(String performanceCheckName) {
        this.performanceCheckName = performanceCheckName;
    }

    public String getPerformanceCheckCode() {
        return performanceCheckCode;
    }

    public void setPerformanceCheckCode(String performanceCheckCode) {
        this.performanceCheckCode = performanceCheckCode;
    }
}
