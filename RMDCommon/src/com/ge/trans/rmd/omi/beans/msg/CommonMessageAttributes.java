/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   CommonMessageAttributes.java
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

package com.ge.trans.rmd.omi.beans.msg;

public abstract class CommonMessageAttributes extends BaseMessage {

    private static final long serialVersionUID = -4431457480037369138L;
    private String messageID = null;
    private Long rrrrrccc = null;
    private Integer customerID = null;
    private Integer roadNumber = null;
    private String messageFRV = null;
    private Integer nameValidity = null;
    private Boolean isAckRequired = null;
    private Integer ackRequired = null;
    private Boolean isRNCustMismatch = null;
    private Integer rnCustMismatch = null;
    private Boolean isSDPMissing = null;
    private Integer sdpMissing = null;
    private Boolean isEDPMissing = null;
    private Integer edpMissing = null;
    private Boolean isTriggerRequested = null;
    private Integer triggerRequested = null;
    private Boolean isOBSeqPresent = null;
    private Integer obSeqPresent = null;
    private Long offboardSeqNo = null;
    private Boolean isZipped = null;
    private Integer zippedValue = null;
    private Integer unzipBytesNo = null;
    private Integer onbAckNumber = null;
    private Integer cRC = null;

    /**
     * Gets the message id.
     * 
     * @return the message id
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the message id.
     * 
     * @param pMessageID
     *            the new message id
     */
    public void setMessageID(String pMessageID) {
        messageID = getString(pMessageID);
    }

    /**
     * Gets the rRRRRCCC.
     * 
     * @return the rRRRRCCC
     */
    public Long getRRRRRCCC() {
        return rrrrrccc;
    }

    /**
     * Sets the rRRRRCCC.
     * 
     * @param pRRRRRCCC
     *            the new rRRRRCCC
     */
    public void setRRRRRCCC(String pRRRRRCCC) {
        rrrrrccc = getLong(pRRRRRCCC);
    }

    /**
     * Gets the customer id.
     * 
     * @return the customer id
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customer id.
     * 
     * @param pCustomerID
     *            the new customer id
     */
    public void setCustomerID(String pCustomerID) {
        customerID = getInteger(pCustomerID);
    }

    /**
     * Gets the road number.
     * 
     * @return the road number
     */
    public Integer getRoadNumber() {
        return roadNumber;
    }

    /**
     * Sets the road number.
     * 
     * @param pRoadNumber
     *            the new road number
     */
    public void setRoadNumber(String pRoadNumber) {
        roadNumber = getInteger(pRoadNumber);
    }

    /**
     * Gets the message frv.
     * 
     * @return the message frv
     */
    public String getMessageFRV() {
        return messageFRV;
    }

    /**
     * Sets the message frv.
     * 
     * @param pMessageFRV
     *            the new message frv
     */
    public void setMessageFRV(String pMessageFRV) {
        messageFRV = getString(pMessageFRV);
    }

    /**
     * Gets the name validity.
     * 
     * @return the name validity
     */
    public Integer getNameValidity() {
        return nameValidity;
    }

    /**
     * Sets the name validity.
     * 
     * @param pNameValidity
     *            the new name validity
     */
    public void setNameValidity(String pNameValidity) {
        nameValidity = getInteger(pNameValidity);
    }

    /**
     * Gets the offboard seq no.
     * 
     * @return the offboard seq no
     */
    public Long getOffboardSeqNo() {
        return offboardSeqNo;
    }

    /**
     * Sets the offboard seq no.
     * 
     * @param pOffboardSeqNo
     *            the new offboard seq no
     */
    public void setOffboardSeqNo(String pOffboardSeqNo) {
        offboardSeqNo = getLong(pOffboardSeqNo);
    }

    /**
     * Checks if is zipped.
     * 
     * @return true, if is zipped
     */
    public boolean isZipped() {
        return isZipped;
    }

    /**
     * Sets the checks if is zipped.
     * 
     * @param pIsZipped
     *            the new checks if is zipped
     */
    public void setIsZipped(String pIsZipped) {
        String localpIsZipped = pIsZipped;
        localpIsZipped = trimToNull(localpIsZipped);
        zippedValue = getInteger(localpIsZipped);

        isZipped = ((zippedValue != null) ? ((zippedValue == 1) ? true : false) : null);
    }

    /**
     * Gets the zipped value.
     * 
     * @return the zipped value
     */
    public Integer getZippedValue() {
        return zippedValue;
    }

    /**
     * Gets the unzip bytes no.
     * 
     * @return the unzip bytes no
     */
    public Integer getUnzipBytesNo() {
        return unzipBytesNo;
    }

    /**
     * Sets the unzip bytes no.
     * 
     * @param pUnzipBytesNo
     *            the new unzip bytes no
     */
    public void setUnzipBytesNo(String pUnzipBytesNo) {
        unzipBytesNo = getInteger(pUnzipBytesNo);
    }

    /**
     * Gets the onb ack number.
     * 
     * @return the onb ack number
     */
    public Integer getOnbAckNumber() {
        return onbAckNumber;
    }

    /**
     * Sets the onb ack number.
     * 
     * @param pOnbAckNumber
     *            the new onb ack number
     */
    public void setOnbAckNumber(String pOnbAckNumber) {
        onbAckNumber = getInteger(pOnbAckNumber);
    }

    /**
     * Gets the checks if is ack required.
     * 
     * @return the checks if is ack required
     */
    public Boolean getIsAckRequired() {
        return isAckRequired;
    }

    /**
     * Sets the checks if is ack required.
     * 
     * @param pIsAckRequired
     *            the new checks if is ack required
     */
    public void setIsAckRequired(String pIsAckRequired) {
        String localpIsAckRequired = pIsAckRequired;
        localpIsAckRequired = trimToNull(localpIsAckRequired);
        ackRequired = getInteger(localpIsAckRequired);

        isAckRequired = ((ackRequired != null) ? ((ackRequired == 1) ? true : false) : null);
    }

    /**
     * Gets the checks if is rn cust mismatch.
     * 
     * @return the checks if is rn cust mismatch
     */
    public Boolean getIsRNCustMismatch() {
        return isRNCustMismatch;
    }

    /**
     * Sets the checks if is rn cust mismatch.
     * 
     * @param pIsRNCustMismatch
     *            the new checks if is rn cust mismatch
     */
    public void setIsRNCustMismatch(String pIsRNCustMismatch) {
        String localpIsRNCustMismatch = pIsRNCustMismatch;
        localpIsRNCustMismatch = trimToNull(localpIsRNCustMismatch);
        rnCustMismatch = getInteger(localpIsRNCustMismatch);

        isRNCustMismatch = ((rnCustMismatch != null) ? ((rnCustMismatch == 0) ? true : false) : null);
    }

    /**
     * Gets the checks if is sdp missing.
     * 
     * @return the checks if is sdp missing
     */
    public Boolean getIsSDPMissing() {
        return isSDPMissing;
    }

    /**
     * Sets the checks if is sdp missing.
     * 
     * @param pIsSDPMissing
     *            the new checks if is sdp missing
     */
    public void setIsSDPMissing(String pIsSDPMissing) {
        String localpIsSDPMissing = pIsSDPMissing;
        localpIsSDPMissing = trimToNull(localpIsSDPMissing);
        sdpMissing = getInteger(localpIsSDPMissing);

        isSDPMissing = ((sdpMissing != null) ? ((sdpMissing == 0) ? true : false) : null);
    }

    /**
     * Gets the checks if is edp missing.
     * 
     * @return the checks if is edp missing
     */
    public Boolean getIsEDPMissing() {
        return isEDPMissing;
    }

    /**
     * Sets the checks if is edp missing.
     * 
     * @param pIsEDPMissing
     *            the new checks if is edp missing
     */
    public void setIsEDPMissing(String pIsEDPMissing) {
        String localpIsEDPMissing = pIsEDPMissing;
        localpIsEDPMissing = trimToNull(localpIsEDPMissing);
        edpMissing = getInteger(localpIsEDPMissing);

        isEDPMissing = ((edpMissing != null) ? ((edpMissing == 0) ? true : false) : null);
    }

    /**
     * Gets the checks if is trigger requested.
     * 
     * @return the checks if is trigger requested
     */
    public Boolean getIsTriggerRequested() {
        return isTriggerRequested;
    }

    /**
     * Sets the checks if is trigger requested.
     * 
     * @param pIsTriggerRequested
     *            the new checks if is trigger requested
     */
    public void setIsTriggerRequested(String pIsTriggerRequested) {
        String localpIsTriggerRequested = pIsTriggerRequested;
        localpIsTriggerRequested = trimToNull(localpIsTriggerRequested);
        triggerRequested = getInteger(localpIsTriggerRequested);

        isTriggerRequested = ((triggerRequested != null) ? ((triggerRequested == 1) ? true : false) : null);
    }

    /**
     * Gets the checks if is ob seq present.
     * 
     * @return the checks if is ob seq present
     */
    public Boolean getIsOBSeqPresent() {
        return isOBSeqPresent;
    }

    /**
     * Sets the checks if is ob seq present.
     * 
     * @param pIsOBSeqPresent
     *            the new checks if is ob seq present
     */
    public void setIsOBSeqPresent(String pIsOBSeqPresent) {
        String localpIsOBSeqPresent = pIsOBSeqPresent;
        localpIsOBSeqPresent = trimToNull(localpIsOBSeqPresent);
        obSeqPresent = getInteger(localpIsOBSeqPresent);

        isOBSeqPresent = ((obSeqPresent != null) ? ((obSeqPresent == 1) ? true : false) : null);
    }

    /**
     * Gets the cRC.
     * 
     * @return the cRC
     */
    public Integer getCRC() {
        return cRC;
    }

    /**
     * Sets the cRC.
     * 
     * @param pCrc
     *            the new cRC
     */
    public void setCRC(String pCrc) {
        cRC = getInteger(pCrc);
    }

    /**
     * Gets the ack required.
     * 
     * @return the ackRequired
     */
    public Integer getAckRequired() {
        return ackRequired;
    }

    /**
     * Gets the rn cust mismatch.
     * 
     * @return the rnCustMismatch
     */
    public Integer getRnCustMismatch() {
        return rnCustMismatch;
    }

    /**
     * Gets the sdp missing.
     * 
     * @return the sdpMissing
     */
    public Integer getSdpMissing() {
        return sdpMissing;
    }

    /**
     * Gets the edp missing.
     * 
     * @return the edpMissing
     */
    public Integer getEdpMissing() {
        return edpMissing;
    }

    /**
     * Gets the trigger requested.
     * 
     * @return the triggerRequested
     */
    public Integer getTriggerRequested() {
        return triggerRequested;
    }

    /**
     * Gets the ob seq present.
     * 
     * @return the obSeqPresent
     */
    public Integer getObSeqPresent() {
        return obSeqPresent;
    }
}