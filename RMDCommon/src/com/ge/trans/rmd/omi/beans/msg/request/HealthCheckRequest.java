/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   HealthCheckRequest.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.msg.request;

public final class HealthCheckRequest extends Request {

    private static final long serialVersionUID = -1347904761872177863L;
    private Long customerID;
    private Long assetGroupID;
    private Integer assetGroupNumber;
    private Integer assetNumber;
    private Long assetID;
    private Boolean includeSDP;
    private Integer triggerNumber;
    private Integer reTriggerNumber;
    private String requestorName;

    public HealthCheckRequest(Long pAssetID, Boolean pIncludeSDP, Integer pTriggerNumber, Integer pReTriggerNumber,
            String pRequestorName) {
        assetID = pAssetID;
        includeSDP = pIncludeSDP;
        triggerNumber = pTriggerNumber;
        reTriggerNumber = pReTriggerNumber;
        requestorName = pRequestorName;
    }

    public HealthCheckRequest(Long pCustomerID, Long pAssetGroupID, Integer pAssetGroupNumber, Integer pAssetNumber,
            Boolean pIncludeSDP, Integer pTriggerNumber, Integer pReTriggerNumber, String pRequestorName) {
        customerID = pCustomerID;
        assetGroupID = pAssetGroupID;
        assetGroupNumber = pAssetGroupNumber;

        assetNumber = pAssetNumber;
        includeSDP = pIncludeSDP;
        triggerNumber = pTriggerNumber;
        reTriggerNumber = pReTriggerNumber;
        requestorName = pRequestorName;
    }

    /**
     * @return the assetGroupNumber
     */
    public Integer getAssetGroupNumber() {
        return assetGroupNumber;

    }

    /**
     * @param assetGroupNumber
     *            the assetGroupNumber to set
     */
    public void setAssetGroupNumber(Integer assetGroupNumber) {
        this.assetGroupNumber = assetGroupNumber;

    }

    /**
     * @return the customerID
     */
    public Long getCustomerID() {
        return customerID;
    }

    /**
     * @param pCustomerID
     *            the customerID to set
     */
    public void setCustomerID(Long pCustomerID) {
        customerID = pCustomerID;
    }

    /**
     * @return the assetGroupID
     */
    public Long getAssetGroupID() {
        return assetGroupID;
    }

    /**
     * @param pAssestGroupID
     *            the assetGroupID to set
     */
    public void setAssetGroupID(Long pAssetGroupID) {
        assetGroupID = pAssetGroupID;
    }

    /**
     * @return the assetNumber
     */
    public Integer getAssetNumber() {
        return assetNumber;
    }

    /**
     * @param pAssetNumber
     *            the assetNumber to set
     */
    public void setAssetNumber(Integer pAssetNumber) {
        assetNumber = pAssetNumber;
    }

    /**
     * @return the assetID
     */
    public Long getAssetID() {
        return assetID;
    }

    /**
     * @param pAssetID
     *            the assetID to set
     */
    public void setAssetID(Long pAssetID) {
        assetID = pAssetID;
    }

    /**
     * @return the includeSDP
     */
    public Boolean getIncludeSDP() {
        return includeSDP;
    }

    /**
     * @param pIncludeSDP
     *            the includeSDP to set
     */
    public void setIncludeSDP(Boolean pIncludeSDP) {
        includeSDP = pIncludeSDP;
    }

    /**
     * @return the triggerNumber
     */
    public Integer getTriggerNumber() {
        return triggerNumber;
    }

    /**
     * @param pTriggerNumber
     *            the triggerNumber to set
     */
    public void setTriggerNumber(Integer pTriggerNumber) {
        triggerNumber = pTriggerNumber;
    }

    /**
     * @return the reTriggerNumber
     */
    public Integer getReTriggerNumber() {
        return reTriggerNumber;
    }

    /**
     * @param pReTriggerNumber
     *            the reTriggerNumber to set
     */
    public void setReTriggerNumber(Integer pReTriggerNumber) {
        reTriggerNumber = pReTriggerNumber;
    }

    /**
     * @return the requestorName
     */
    public String getRequestorName() {
        return requestorName;
    }

    /**
     * @param pRequestorName
     *            the requestorName to set
     */
    public void setRequestorName(String pRequestorName) {
        requestorName = pRequestorName;
    }
}