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

package com.ge.trans.rmd.omi.beans.msg.out;

import com.ge.trans.rmd.omi.beans.msg.BaseMessage;

public class HealthCheckRequest extends BaseMessage {
    private static final long serialVersionUID = -2958831370119759615L;

    private String messageID = null;
    private String offboardSeqNo = null;
    private String numBytesToFollow = null;
    private String dataToCollectSDP = null;
    private String dataToCollectEDP = null;
    private String dataToCollect = null;
    private String sdpTemplateNo = null;
    private String edpTemplateNo = null;
    private String triggerNumber = null;
    private String reTriggerCount = null;
    private String cRC = null;

    /**
     * @return the messageID
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * @param pMessageID
     *            the messageID to set
     */
    public void setMessageID(String pMessageID) {
        messageID = pMessageID;
    }

    /**
     * @return the offboardSeqNo
     */
    public String getOffboardSeqNo() {
        return offboardSeqNo;
    }

    /**
     * @param pOffboardSeqNo
     *            the offboardSeqNo to set
     */
    public void setOffboardSeqNo(String pOffboardSeqNo) {
        offboardSeqNo = pOffboardSeqNo;
    }

    /**
     * @return the numBytesToFollow
     */
    public String getNumBytesToFollow() {
        return numBytesToFollow;
    }

    /**
     * @param pNumBytesToFollow
     *            the numBytesToFollow to set
     */
    public void setNumBytesToFollow(String pNumBytesToFollow) {
        numBytesToFollow = pNumBytesToFollow;
    }

    /**
     * @return the dataToCollectSDP
     */
    public String getDataToCollectSDP() {
        return dataToCollectSDP;
    }

    /**
     * @param pDataToCollectSDP
     *            the dataToCollectSDP to set
     */
    public void setDataToCollectSDP(String pDataToCollectSDP) {
        dataToCollectSDP = pDataToCollectSDP;
    }

    /**
     * @return the dataToCollectEDP
     */
    public String getDataToCollectEDP() {
        return dataToCollectEDP;
    }

    /**
     * @param pDataToCollectEDP
     *            the dataToCollectEDP to set
     */
    public void setDataToCollectEDP(String pDataToCollectEDP) {
        dataToCollectEDP = pDataToCollectEDP;
    }

    /**
     * @return the dataToCollect
     */
    public String getDataToCollect() {
        return dataToCollect;
    }

    /**
     * @param pDataToCollect
     *            the dataToCollect to set
     */
    public void setDataToCollect(String pDataToCollect) {
        dataToCollect = pDataToCollect;
    }

    /**
     * @return the sdpTemplateNo
     */
    public String getSdpTemplateNo() {
        return sdpTemplateNo;
    }

    /**
     * @param pSdpTemplateNo
     *            the sdpTemplateNo to set
     */
    public void setSdpTemplateNo(String pSdpTemplateNo) {
        sdpTemplateNo = pSdpTemplateNo;
    }

    /**
     * @return the edpTemplateNo
     */
    public String getEdpTemplateNo() {
        return edpTemplateNo;
    }

    /**
     * @param pEdpTemplateNo
     *            the edpTemplateNo to set
     */
    public void setEdpTemplateNo(String pEdpTemplateNo) {
        edpTemplateNo = pEdpTemplateNo;
    }

    /**
     * @return the triggerNumber
     */
    public String getTriggerNumber() {
        return triggerNumber;
    }

    /**
     * @param pTriggerNumber
     *            the triggerNumber to set
     */
    public void setTriggerNumber(String pTriggerNumber) {
        triggerNumber = pTriggerNumber;
    }

    /**
     * @return the reTriggerCount
     */
    public String getReTriggerCount() {
        return reTriggerCount;
    }

    /**
     * @param pReTriggerCount
     *            the reTriggerCount to set
     */
    public void setReTriggerCount(String pReTriggerCount) {
        reTriggerCount = pReTriggerCount;
    }

    /**
     * @return the cRC
     */
    public String getcRC() {
        return cRC;
    }

    /**
     * @param pCRC
     *            the cRC to set
     */
    public void setcRC(String pCRC) {
        cRC = pCRC;
    }
}