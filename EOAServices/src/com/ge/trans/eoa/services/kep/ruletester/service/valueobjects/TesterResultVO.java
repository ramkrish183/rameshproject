/**
 * ============================================================
 * File : TesterResultVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.tester.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */

package com.ge.trans.eoa.services.kep.ruletester.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This Class consists of the value objects for TesterResult
 * @History :
 ******************************************************************************/
public class TesterResultVO {
    private int noOfFired;
    private String customer;
    private String assetNo;
    private String segmentId;
    private String classification;
    private String hitOrMiss;
    private String assetHeader;
    private String vehicleNo;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getAssetHeader() {
        return assetHeader;
    }

    public void setAssetHeader(String assetHeader) {
        this.assetHeader = assetHeader;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(final String customer) {
        this.customer = customer;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(final String assetNo) {
        this.assetNo = assetNo;
    }

    public int getNoOfFired() {
        return noOfFired;
    }

    public void setNoOfFired(final int noOfFired) {
        this.noOfFired = noOfFired;
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(final String segmentId) {
        this.segmentId = segmentId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(final String classification) {
        this.classification = classification;
    }

    public String getHitOrMiss() {
        return hitOrMiss;
    }

    public void setHitOrMiss(final String hitOrMiss) {
        this.hitOrMiss = hitOrMiss;
    }

}
