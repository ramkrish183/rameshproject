/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckVO.java
 * Description :
 * Package : com.ge.trans.rmd.services.asset.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Apr 7, 2010
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class HealthCheckVO {

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getNoOfPostSamples() {
        return noOfPostSamples;
    }

    public void setNoOfPostSamples(String noOfPostSamples) {
        this.noOfPostSamples = noOfPostSamples;
    }

    private static final long serialVersionUID = 1L;
    private Long customerID;
    private Long assetGroupID;
    private Integer assetNumber;
    private Long assetGroupNumber;
    private Long assetID;
    private String assetType;
    private String isHCAvailable;
    private String roadNumber;
    private String customerId;
    private String assetGrpName;
    private String typeOfUser;
    private String messageId;
    private String noOfPostSamples;
    private String userId;
    private boolean fromMcs;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getAssetGrpName() {
        return assetGrpName;
    }

    public void setAssetGrpName(String assetGrpName) {
        this.assetGrpName = assetGrpName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRoadNumber() {
        return roadNumber;
    }

    public void setRoadNumber(String roadNumber) {
        this.roadNumber = roadNumber;
    }

    /**
     * @return the customerID
     */
    public Long getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID
     *            the customerID to set
     */
    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the assetGroupNumber
     */
    public Long getAssetGroupNumber() {
        return assetGroupNumber;
    }

    /**
     * @param assetGroupNumber
     *            the assetGroupNumber to set
     */
    public void setAssetGroupNumber(Long assetGroupNumber) {
        this.assetGroupNumber = assetGroupNumber;
    }

    /**
     * @return the assetGroupID
     */
    public Long getAssetGroupID() {
        return assetGroupID;
    }

    /**
     * @param assetGroupID
     *            the assetGroupID to set
     */
    public void setAssetGroupID(Long assetGroupID) {
        this.assetGroupID = assetGroupID;
    }

    /**
     * @return the assetNumber
     */
    public Integer getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param assetNumber
     *            the assetNumber to set
     */
    public void setAssetNumber(Integer assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * @return the assetID
     */
    public Long getAssetID() {
        return assetID;
    }

    /**
     * @param assetID
     *            the assetID to set
     */
    public void setAssetID(Long assetID) {
        this.assetID = assetID;
    }

	public boolean isFromMcs() {
		return fromMcs;
	}

	public void setFromMcs(boolean fromMcs) {
		this.fromMcs = fromMcs;
	}
}
