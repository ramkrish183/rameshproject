package com.ge.trans.eoa.services.tools.lhr.service.valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleHealthDataVO {
    private long objid;
    private VehicleVO vehicle = null;
    private float healthFactor = 100;
    private String defectLevelCode;
    private double confidence = 1.0;
    private Date calculationDate = null;
    private List<PerformanceHealthDataVO> componentHealthList;

    public VehicleHealthDataVO(VehicleVO vehicle) {
        this.setVehicle(vehicle);
    }

    public VehicleHealthDataVO(VehicleVO vehicle, float healthFactor, String defaultDefectLevel) {
        this.setVehicle(vehicle);
        this.healthFactor = healthFactor;
        this.defectLevelCode = defaultDefectLevel;
    }

    public VehicleHealthDataVO(VehicleVO vehicle, float healthFactor, double confidenceValue,
            List<PerformanceHealthDataVO> perfDataList, String suitabilityCode) {
        this.setVehicle(vehicle);
        this.healthFactor = healthFactor;
        this.confidence = confidenceValue;
        this.componentHealthList = perfDataList;
        this.defectLevelCode = suitabilityCode;
        this.calculationDate = new Date();
    }

    public VehicleHealthDataVO(long id, VehicleVO vehicle, float healthFactor, double confidenceValue,
            List<PerformanceHealthDataVO> perfDataList, String suitabilityCode, Date calculatedOn) {
        this.objid = id;
        this.setVehicle(vehicle);
        this.healthFactor = healthFactor;
        this.confidence = confidenceValue;
        this.componentHealthList = perfDataList;
        this.defectLevelCode = suitabilityCode;
        this.calculationDate = calculatedOn;
    }

    public long getObjid() {
        return objid;
    }

    public void setObjid(long objid) {
        this.objid = objid;
    }

    public float getHealthFactor() {
        return healthFactor;
    }

    public void setHealthFactor(float healthFactor) {
        this.healthFactor = healthFactor;
    }

    public String getDefectLevelCode() {
        return defectLevelCode;
    }

    public void setDefectLevelCode(String code) {
        this.defectLevelCode = code;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidenceValue) {
        this.confidence = confidenceValue;
    }

    public Date getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(Date calculationDate) {
        this.calculationDate = calculationDate;
    }

    public List<PerformanceHealthDataVO> getComponentHealthList() {
        return componentHealthList;
    }

    public void setComponentHealthList(List<PerformanceHealthDataVO> componentHealth) {
        this.componentHealthList = componentHealth;
    }

    public void addComponentHealth(PerformanceHealthDataVO healthData) {
        if (healthData != null) {
            if (componentHealthList == null) {
                componentHealthList = new ArrayList<PerformanceHealthDataVO>();
            }
            componentHealthList.add(healthData);
        }
    }

    public VehicleVO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleVO vehicle) {
        this.vehicle = vehicle;
    }

}