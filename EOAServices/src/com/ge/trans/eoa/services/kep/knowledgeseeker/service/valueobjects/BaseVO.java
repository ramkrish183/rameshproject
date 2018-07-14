package com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects;

import java.util.Date;

/*******************************************************************************
 * @Author : Igate Patni Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 24, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class BaseVO {

    private String createdBy;
    private Date dtStartDate;
    private Date dtEndDate;
    private String status;
    private String customer;
    private String assetID;
    private String segmentID;
    private Date dtCreateDate;
    private String trackID;
    private int customerID;
    private int fleetID;
    private String fleetName;
    private String modelName;
    private int modelID;
    private int configID;
    private String configName;
    private String assetName;
    private String patternHitorMiss;
    private String assetHeader;

    public String getAssetHeader() {
        return assetHeader;
    }

    public void setAssetHeader(String assetHeader) {
        this.assetHeader = assetHeader;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDtStartDate() {
        return dtStartDate;
    }

    public void setDtStartDate(final Date dtStartDate) {
        this.dtStartDate = dtStartDate;
    }

    public Date getDtEndDate() {
        return dtEndDate;
    }

    public void setDtEndDate(final Date dtEndDate) {
        this.dtEndDate = dtEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(final String customer) {
        this.customer = customer;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(final String assetID) {
        this.assetID = assetID;
    }

    public String getSegmentID() {
        return segmentID;
    }

    public void setSegmentID(final String segmentID) {
        this.segmentID = segmentID;
    }

    public Date getDtCreateDate() {
        return dtCreateDate;
    }

    public void setDtCreateDate(final Date dtCreateDate) {
        this.dtCreateDate = dtCreateDate;
    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(final String trackID) {
        this.trackID = trackID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(final int customerID) {
        this.customerID = customerID;
    }

    public int getFleetID() {
        return fleetID;
    }

    public void setFleetID(final int fleetID) {
        this.fleetID = fleetID;
    }

    public String getFleetName() {
        return fleetName;
    }

    public void setFleetName(final String fleetName) {
        this.fleetName = fleetName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(final int modelID) {
        this.modelID = modelID;
    }

    public int getConfigID() {
        return configID;
    }

    public void setConfigID(final int configID) {
        this.configID = configID;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(final String configName) {
        this.configName = configName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(final String assetName) {
        this.assetName = assetName;
    }

    public String getPatternHitorMiss() {
        return patternHitorMiss;
    }

    public void setPatternHitorMiss(final String patternHitorMiss) {
        this.patternHitorMiss = patternHitorMiss;
    }
}
