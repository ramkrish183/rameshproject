/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   HealthCheckRequestParams.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   HealthCheckRequestParams.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans;

/**
 * The Class HealthCheckRequestInputsVO.
 */
public class HealthCheckRequestParams extends BaseVO {

    private static final long serialVersionUID = 1L;
    private String strMessageID = null;
    private String strMMMM = null;// How do we get it from user
    private String strFRV = null;// How do we get it from user
    private String strMessageSize = null;
    private String user = null;
    private String strAssetID = null; // How do we get it from user
    private String strRoadInitial = null;
    private String strRoadInitialID = null;
    private String strRoadNumber = null;
    private String eDP = null;
    private String sDP = null;
    private String triggerCount = null;
    private String offboardSeqNo = null;
    private String numBytesToFollow = null;
    private String dataToCollectSDP = null;
    private String dataToCollectEDP = null;
    private String dataToCollect = null;
    private String sdpTemplateNo = null;
    private String edpTemplateNo = null;
    private String triggerNumber = null;
    private String reTriggerCount = null;

    public static final String MESSAGE_ID_SEPARATOR = ".";

    /**
     * Gets the eDP.
     * 
     * @return the eDP
     */
    public String getEDP() {
        return eDP;
    }

    /**
     * Sets the eDP.
     * 
     * @param edp
     *            the new eDP
     */
    public void setEDP(String edp) {
        eDP = edp;
    }

    /**
     * Gets the sDP.
     * 
     * @return the sDP
     */
    public String getSDP() {
        return sDP;
    }

    /**
     * Sets the sDP.
     * 
     * @param sdp
     *            the new sDP
     */
    public void setSDP(String sdp) {
        sDP = sdp;
    }

    /**
     * Gets the trigger count.
     * 
     * @return the trigger count
     */
    public String getTriggerCount() {
        return triggerCount;
    }

    /**
     * Sets the trigger count.
     * 
     * @param triggerCount
     *            the new trigger count
     */
    public void setTriggerCount(String triggerCount) {
        this.triggerCount = triggerCount;
    }

    /**
     * Gets the serial version uid.
     * 
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets the str message id.
     * 
     * @return the str message id
     */
    public String getStrMessageID() {
        return strMessageID;
    }

    /**
     * Sets the str message id.
     * 
     * @param strMessageID
     *            the new str message id
     */
    public void setStrMessageID(String strMessageID) {
        this.strMessageID = strMessageID;
    }

    /**
     * Gets the str mmmm.
     * 
     * @return the str mmmm
     */
    public String getStrMMMM() {
        return strMMMM;
    }

    /**
     * Sets the str mmmm.
     * 
     * @param strMMMM
     *            the new str mmmm
     */
    public void setStrMMMM(String strMMMM) {
        this.strMMMM = strMMMM;
    }

    /**
     * Gets the str frv.
     * 
     * @return the str frv
     */
    public String getStrFRV() {
        return strFRV;
    }

    /**
     * Sets the str frv.
     * 
     * @param strFRV
     *            the new str frv
     */
    public void setStrFRV(String strFRV) {
        this.strFRV = strFRV;
    }

    /**
     * Gets the str asset id.
     * 
     * @return the str asset id
     */
    public String getStrAssetID() {
        return strAssetID;
    }

    /**
     * Sets the str asset id.
     * 
     * @param strAssetID
     *            the new str asset id
     */
    public void setStrAssetID(String strAssetID) {
        this.strAssetID = strAssetID;
    }

    /**
     * Gets the str road initial.
     * 
     * @return the str road initial
     */
    public String getStrRoadInitial() {
        return strRoadInitial;
    }

    /**
     * Sets the str road initial.
     * 
     * @param strRoadInitial
     *            the new str road initial
     */
    public void setStrRoadInitial(String strRoadInitial) {
        this.strRoadInitial = strRoadInitial;
    }

    /**
     * Gets the str road initial id.
     * 
     * @return the str road initial id
     */
    public String getStrRoadInitialID() {
        return strRoadInitialID;
    }

    /**
     * Sets the str road initial id.
     * 
     * @param strRoadInitialID
     *            the new str road initial id
     */
    public void setStrRoadInitialID(String strRoadInitialID) {
        this.strRoadInitialID = strRoadInitialID;
    }

    /**
     * Gets the str road number.
     * 
     * @return the str road number
     */
    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    /**
     * Sets the str road number.
     * 
     * @param strRoadNumber
     *            the new str road number
     */
    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }

    /**
     * Gets the offboard seq no.
     * 
     * @return the offboard seq no
     */
    public String getOffboardSeqNo() {
        return offboardSeqNo;
    }

    /**
     * Sets the offboard seq no.
     * 
     * @param offboardSeqNo
     *            the new offboard seq no
     */
    public void setOffboardSeqNo(String offboardSeqNo) {
        this.offboardSeqNo = offboardSeqNo;
    }

    /**
     * Gets the num bytes to follow.
     * 
     * @return the num bytes to follow
     */
    public String getNumBytesToFollow() {
        return numBytesToFollow;
    }

    /**
     * Sets the num bytes to follow.
     * 
     * @param numBytesToFollow
     *            the new num bytes to follow
     */
    public void setNumBytesToFollow(String numBytesToFollow) {
        this.numBytesToFollow = numBytesToFollow;
    }

    /**
     * Gets the data to collect sdp.
     * 
     * @return the data to collect sdp
     */
    public String getDataToCollectSDP() {
        return dataToCollectSDP;
    }

    /**
     * Sets the data to collect sdp.
     * 
     * @param dataToCollectSDP
     *            the new data to collect sdp
     */
    public void setDataToCollectSDP(String dataToCollectSDP) {
        this.dataToCollectSDP = dataToCollectSDP;
    }

    /**
     * Gets the data to collect edp.
     * 
     * @return the data to collect edp
     */
    public String getDataToCollectEDP() {
        return dataToCollectEDP;
    }

    /**
     * Sets the data to collect edp.
     * 
     * @param dataToCollectEDP
     *            the dataToCollectEDP to set
     */
    public void setDataToCollectEDP(String dataToCollectEDP) {
        this.dataToCollectEDP = dataToCollectEDP;
    }

    /**
     * Gets the data to collect.
     * 
     * @return the data to collect
     */
    public String getDataToCollect() {
        return dataToCollect;
    }

    /**
     * Sets the data to collect.
     * 
     * @param dataToCollect
     *            the dataToCollect to set
     */
    public void setDataToCollect(String dataToCollect) {
        this.dataToCollect = dataToCollect;
    }

    /**
     * Gets the sdp template no.
     * 
     * @return the sdp template no
     */
    public String getSdpTemplateNo() {
        return sdpTemplateNo;
    }

    /**
     * Sets the sdp template no.
     * 
     * @param sdpTemplateNo
     *            the sdpTemplateNo to set
     */
    public void setSdpTemplateNo(String sdpTemplateNo) {
        this.sdpTemplateNo = sdpTemplateNo;
    }

    /**
     * Gets the edp template no.
     * 
     * @return the edp template no
     */
    public String getEdpTemplateNo() {
        return edpTemplateNo;
    }

    /**
     * Sets the edp template no.
     * 
     * @param edpTemplateNo
     *            the edpTemplateNo to set
     */
    public void setEdpTemplateNo(String edpTemplateNo) {
        this.edpTemplateNo = edpTemplateNo;
    }

    /**
     * Gets the trigger number.
     * 
     * @return the trigger number
     */
    public String getTriggerNumber() {
        return triggerNumber;
    }

    /**
     * Sets the trigger number.
     * 
     * @param triggerNumber
     *            the triggerNumber to set
     */
    public void setTriggerNumber(String triggerNumber) {
        this.triggerNumber = triggerNumber;
    }

    /**
     * Gets the re trigger count.
     * 
     * @return the reTriggerCount
     */
    public String getReTriggerCount() {
        return reTriggerCount;
    }

    /**
     * Sets the re trigger count.
     * 
     * @param reTriggerCount
     *            the reTriggerCount to set
     */
    public void setReTriggerCount(String reTriggerCount) {
        this.reTriggerCount = reTriggerCount;
    }

    /**
     * Gets the str message size.
     * 
     * @return the strMessageSize
     */
    public String getStrMessageSize() {
        return strMessageSize;
    }

    /**
     * Sets the str message size.
     * 
     * @param strMessageSize
     *            the strMessageSize to set
     */
    public void setStrMessageSize(String strMessageSize) {
        this.strMessageSize = strMessageSize;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the message id.
     * 
     * @return String
     */
    public String getMessageID() {
        return (getStrMMMM() + MESSAGE_ID_SEPARATOR + getStrFRV());
    }
}
