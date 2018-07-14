package com.ge.trans.pp.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/**
 * @author Igate
 */
public class PPAssetDetailsVO extends BaseVO {

    private String assetNumber;
    private String assetGrpName;
    private String customerId;
    private String vehicleObjId;
    private long assetGroupNumber;
    private String model;
    private String fleet;

    /**
     * @return the assetNumber
     */
    public String getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param assetNumber
     *            the assetNumber to set
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * @return the assetGrpName
     */
    public String getAssetGrpName() {
        return assetGrpName;
    }

    /**
     * @param assetGrpName
     *            the assetGrpName to set
     */
    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVehicleObjId() {
        return vehicleObjId;
    }

    public void setVehicleObjId(String vehicleObjId) {
        this.vehicleObjId = vehicleObjId;
    }

    public long getAssetGroupNumber() {
        return assetGroupNumber;
    }

    public void setAssetGroupNumber(long assetGroupNumber) {
        this.assetGroupNumber = assetGroupNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFleet() {
        return fleet;
    }

    public void setFleet(String fleet) {
        this.fleet = fleet;
    }

}
