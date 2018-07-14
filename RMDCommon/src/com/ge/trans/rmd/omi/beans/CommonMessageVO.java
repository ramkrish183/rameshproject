/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   CommonMessageVO.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This class contains setter and getter methods of common Message properties.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ge.trans.rmd.omi.beans.BaseVO;

/**
 * This class contains setter and getter methods of common Message properties.
 */

public class CommonMessageVO extends BaseVO {
    private static final long serialVersionUID = 8381519836239090718L;

    private String strAppNum = null;

    private String strMessageId = null;

    private String strMessageFRV = null;

    private String strMessageValidity = null;

    private String strMessageByteFollows = null;

    private String strMessageLR = null;

    private String strCustID = null;

    private String strRoadNumber = null;

    private String strRRRRRCCC = null;

    private String strMessageTime = null;

    private String strOffBoardSeqNum = null;

    private String strNumOfZipBytes = null;

    private String strTemplateNumber = null;

    private String strVersionNumber = null;

    private boolean blnCrcFlag;

    private String strCRC = "00";// default value set to "00"
    // because encoder replacing last 2 bytes with actual CRC

    private String strMesageUserName = null;

    private String strMessageLocation = null;

    private String strMsgScheduledTime = null;

    private String strMsgReTriggerTime = null;

    private String strNotMovingSpeed = null;

    private String strNotMovingDistance = null;

    private String strNotMovingCheckTime = null;

    private String strMinForTriggerMsg = null;

    private String strFuelLevelDecVal = null;

    private String strFuelLevelIncVal = null;

    private String strFuelLevelDecTime = null;

    private String strFuelLevelIncTime = null;

    private String strFuelLevelTransmitRate = null;

    private String strFuelLevelValue = null;

    private String strMNRNetwork = null;

    private String strWLANetwork = null;

    private String strBWSNetwork = null;

    private String strFuelLevelLow = null;

    private String strFuelEvent = null;

    private String strAPUFailsToStart = null;

    private String strERMPanelPowerUp = null;

    private String strEngineStartFault = null;

    private String strLocoMoving = null;

    private String strGeoZoneEvent = null;

    private String strSpare = null;

    private String strBreadCrumb = null;

    private String strSpareBit = null;

    private String strDNSLength = null;

    // Extra Data Encoding
    private String strExtraData = null;

    // Compression field
    private String strCompression = null;

    /**
     * Size of destination directory name
     */
    private String strNumWlanDestBytes = null;

    /**
     * WLAN Server destination directory/symbolic link
     */
    private String strWlanDest = null;

    /**
     * @return the strDNSLength
     * @param void
     */
    public String getStrDNSLength() {
        return strDNSLength;
    }

    /**
     * @return StringstrDNSLength
     * @param strDNSLength
     *            the strDNSLength to set
     */
    public void setStrDNSLength(String strDNSLength) {
        this.strDNSLength = strDNSLength;
    }

    /**
     * @return the strMesageUserName
     * @param void
     */
    public String getStrMesageUserName() {
        return strMesageUserName;
    }

    /**
     * @return StringstrMesageUserName
     * @param strMesageUserName
     *            the strMesageUserName to set
     */
    public void setStrMesageUserName(String strMesageUserName) {
        this.strMesageUserName = strMesageUserName;
    }

    /**
     * @return the strMessageLocation
     * @param void
     */
    public String getStrMessageLocation() {
        return strMessageLocation;
    }

    /**
     * @return StringstrMessageLocation
     * @param strMessageLocation
     *            the strMessageLocation to set
     */
    public void setStrMessageLocation(String strMessageLocation) {
        this.strMessageLocation = strMessageLocation;
    }

    /**
     * @return the strTemplateNumber
     * @param void
     */
    public String getStrTemplateNumber() {
        return strTemplateNumber;
    }

    /**
     * @return StringstrTemplateNumber
     * @param strTemplateNumber
     *            the strTemplateNumber to set
     */
    public void setStrTemplateNumber(String strTemplateNumber) {
        this.strTemplateNumber = strTemplateNumber;
    }

    /**
     * @return the strVersionNumber
     * @param void
     */
    public String getStrVersionNumber() {
        return strVersionNumber;
    }

    /**
     * @return StringstrVersionNumber
     * @param strVersionNumber
     *            the strVersionNumber to set
     */
    public void setStrVersionNumber(String strVersionNumber) {
        this.strVersionNumber = strVersionNumber;
    }

    /**
     * @return the strNumOfZipBytes
     * @param void
     */
    public String getStrNumOfZipBytes() {
        return strNumOfZipBytes;
    }

    /**
     * @return StringstrNumOfZipBytes
     * @param strNumOfZipBytes
     *            the strNumOfZipBytes to set
     */
    public void setStrNumOfZipBytes(String strNumOfZipBytes) {
        this.strNumOfZipBytes = strNumOfZipBytes;
    }

    /**
     * @return the strOffBoardSeqNum
     * @param void
     */
    public String getStrOffBoardSeqNum() {
        return strOffBoardSeqNum;
    }

    /**
     * @return StringstrOffBoardSeqNum
     * @param strOffBoardSeqNum
     *            the strOffBoardSeqNum to set
     */
    public void setStrOffBoardSeqNum(String strOffBoardSeqNum) {
        this.strOffBoardSeqNum = strOffBoardSeqNum;
    }

    /**
     * @return the strMessageTime
     * @param void
     */
    public String getStrMessageTime() {
        return strMessageTime;
    }

    /**
     * @return StringstrMessageTime
     * @param strMessageTime
     *            the strMessageTime to set
     */
    public void setStrMessageTime(String strMessageTime) {
        this.strMessageTime = strMessageTime;
    }

    /**
     * @return the strCRC
     * @param void
     */
    public String getStrCRC() {
        return strCRC;
    }

    /**
     * @return void
     * @param arrMessageData
     *            the arrMessageData to set
     */
    public void setStrCRC(String strCRC) {
        this.strCRC = strCRC;
    }

    /**
     * @return the strAppNum
     * @param void
     */
    public String getStrAppNum() {
        return strAppNum;
    }

    /**
     * @return void
     * @param String
     *            strAppNum
     */
    public void setStrAppNum(String strAppNum) {
        this.strAppNum = strAppNum;
    }

    /**
     * @return the strMessageByteFollows
     * @param void
     */
    public String getStrMessageByteFollows() {
        return strMessageByteFollows;
    }

    /**
     * @return void
     * @param String
     *            strMessageByteFollows
     */
    public void setStrMessageByteFollows(String strMessageByteFollows) {
        this.strMessageByteFollows = strMessageByteFollows;
    }

    /**
     * @return the strMessageFRV
     * @param void
     */
    public String getStrMessageFRV() {
        return strMessageFRV;
    }

    /**
     * @return void
     * @param String
     *            strMessageFRV
     */
    public void setStrMessageFRV(String strMessageFRV) {
        this.strMessageFRV = strMessageFRV;
    }

    /**
     * @return the strMessageId
     * @param void
     */
    public String getStrMessageId() {
        return strMessageId;
    }

    /**
     * @return void
     * @param String
     *            strMessageId
     */
    public void setStrMessageId(String strMessageId) {
        this.strMessageId = strMessageId;
    }

    /**
     * @return the strMessageLR
     * @param void
     */
    public String getStrMessageLR() {
        return strMessageLR;
    }

    /**
     * @return void
     * @param String
     *            strMessageValidity
     */
    public void setStrMessageLR(String strMessageLR) {
        this.strMessageLR = strMessageLR;
    }

    /**
     * @return the strMessageValidity
     * @param void
     */
    public String getStrMessageValidity() {
        return strMessageValidity;
    }

    /**
     * @return void
     * @param String
     *            strMessageValidity
     */
    public void setStrMessageValidity(String strMessageValidity) {
        this.strMessageValidity = strMessageValidity;
    }

    /**
     * @return the blnCrcFlag
     * @param void
     */
    public boolean getblnCrcFlag() {
        return this.blnCrcFlag;
    }

    /**
     * @return void
     * @param String
     *            crcFlag
     */
    public void setblnCrcFlag(boolean crcFlag) {
        this.blnCrcFlag = crcFlag;
    }

    /**
     * @return the blnCrcFlag
     * @param void
     */
    public boolean isBlnCrcFlag() {
        return blnCrcFlag;
    }

    /**
     * @return booleanblnCrcFlag
     * @param blnCrcFlag
     *            the blnCrcFlag to set
     */
    public void setBlnCrcFlag(boolean blnCrcFlag) {
        this.blnCrcFlag = blnCrcFlag;
    }

    /**
     * @return the strCustID
     * @param void
     */
    public String getStrCustID() {
        return strCustID;
    }

    /**
     * @return StringstrCustID
     * @param strCustID
     *            the strCustID to set
     */
    public void setStrCustID(String strCustID) {
        this.strCustID = strCustID;
    }

    /**
     * @return the strRoadNumber
     * @param void
     */
    public String getStrRoadNumber() {
        return strRoadNumber;
    }

    /**
     * @return StringstrRoadNumber
     * @param strRoadNumber
     *            the strRoadNumber to set
     */
    public void setStrRoadNumber(String strRoadNumber) {
        this.strRoadNumber = strRoadNumber;
    }

    /**
     * @return the strRRRRRCCC
     * @param void
     */
    public String getStrRRRRRCCC() {
        return strRRRRRCCC;
    }

    /**
     * @return StringstrRRRRRCCC
     * @param strRRRRRCCC
     *            the strRRRRRCCC to set
     */
    public void setStrRRRRRCCC(String strRRRRRCCC) {
        this.strRRRRRCCC = strRRRRRCCC;
    }

    /**
     * @return the strAPUFailsToStart
     * @param void
     */
    public String getStrAPUFailsToStart() {
        return strAPUFailsToStart;
    }

    /**
     * @return StringstrAPUFailsToStart
     * @param strAPUFailsToStart
     *            the strAPUFailsToStart to set
     */
    public void setStrAPUFailsToStart(String strAPUFailsToStart) {
        this.strAPUFailsToStart = strAPUFailsToStart;
    }

    /**
     * @return the strBreadCrumb
     * @param void
     */
    public String getStrBreadCrumb() {
        return strBreadCrumb;
    }

    /**
     * @return StringstrBreadCrumb
     * @param strBreadCrumb
     *            the strBreadCrumb to set
     */
    public void setStrBreadCrumb(String strBreadCrumb) {
        this.strBreadCrumb = strBreadCrumb;
    }

    /**
     * @return the strBWSNetwork
     * @param void
     */
    public String getStrBWSNetwork() {
        return strBWSNetwork;
    }

    /**
     * @return StringstrBWSNetwork
     * @param strBWSNetwork
     *            the strBWSNetwork to set
     */
    public void setStrBWSNetwork(String strBWSNetwork) {
        this.strBWSNetwork = strBWSNetwork;
    }

    /**
     * @return the strEngineStartFault
     * @param void
     */
    public String getStrEngineStartFault() {
        return strEngineStartFault;
    }

    /**
     * @return StringstrEngineStartFault
     * @param strEngineStartFault
     *            the strEngineStartFault to set
     */
    public void setStrEngineStartFault(String strEngineStartFault) {
        this.strEngineStartFault = strEngineStartFault;
    }

    /**
     * @return the strERMPanelPowerUp
     * @param void
     */
    public String getStrERMPanelPowerUp() {
        return strERMPanelPowerUp;
    }

    /**
     * @return StringstrERMPanelPowerUp
     * @param strERMPanelPowerUp
     *            the strERMPanelPowerUp to set
     */
    public void setStrERMPanelPowerUp(String strERMPanelPowerUp) {
        this.strERMPanelPowerUp = strERMPanelPowerUp;
    }

    /**
     * @return the strFuelEvent
     * @param void
     */
    public String getStrFuelEvent() {
        return strFuelEvent;
    }

    /**
     * @return StringstrFuelEvent
     * @param strFuelEvent
     *            the strFuelEvent to set
     */
    public void setStrFuelEvent(String strFuelEvent) {
        this.strFuelEvent = strFuelEvent;
    }

    /**
     * @return the strFuelLevelDecTime
     * @param void
     */
    public String getStrFuelLevelDecTime() {
        return strFuelLevelDecTime;
    }

    /**
     * @return StringstrFuelLevelDecTime
     * @param strFuelLevelDecTime
     *            the strFuelLevelDecTime to set
     */
    public void setStrFuelLevelDecTime(String strFuelLevelDecTime) {
        this.strFuelLevelDecTime = strFuelLevelDecTime;
    }

    /**
     * @return the strFuelLevelDecVal
     * @param void
     */
    public String getStrFuelLevelDecVal() {
        return strFuelLevelDecVal;
    }

    /**
     * @return StringstrFuelLevelDecVal
     * @param strFuelLevelDecVal
     *            the strFuelLevelDecVal to set
     */
    public void setStrFuelLevelDecVal(String strFuelLevelDecVal) {
        this.strFuelLevelDecVal = strFuelLevelDecVal;
    }

    /**
     * @return the strFuelLevelIncTime
     * @param void
     */
    public String getStrFuelLevelIncTime() {
        return strFuelLevelIncTime;
    }

    /**
     * @return StringstrFuelLevelIncTime
     * @param strFuelLevelIncTime
     *            the strFuelLevelIncTime to set
     */
    public void setStrFuelLevelIncTime(String strFuelLevelIncTime) {
        this.strFuelLevelIncTime = strFuelLevelIncTime;
    }

    /**
     * @return the strFuelLevelIncVal
     * @param void
     */
    public String getStrFuelLevelIncVal() {
        return strFuelLevelIncVal;
    }

    /**
     * @return StringstrFuelLevelIncVal
     * @param strFuelLevelIncVal
     *            the strFuelLevelIncVal to set
     */
    public void setStrFuelLevelIncVal(String strFuelLevelIncVal) {
        this.strFuelLevelIncVal = strFuelLevelIncVal;
    }

    /**
     * @return the strFuelLevelLow
     * @param void
     */
    public String getStrFuelLevelLow() {
        return strFuelLevelLow;
    }

    /**
     * @return StringstrFuelLevelLow
     * @param strFuelLevelLow
     *            the strFuelLevelLow to set
     */
    public void setStrFuelLevelLow(String strFuelLevelLow) {
        this.strFuelLevelLow = strFuelLevelLow;
    }

    /**
     * @return the strFuelLevelTransmitRate
     * @param void
     */
    public String getStrFuelLevelTransmitRate() {
        return strFuelLevelTransmitRate;
    }

    /**
     * @return StringstrFuelLevelTransmitRate
     * @param strFuelLevelTransmitRate
     *            the strFuelLevelTransmitRate to set
     */
    public void setStrFuelLevelTransmitRate(String strFuelLevelTransmitRate) {
        this.strFuelLevelTransmitRate = strFuelLevelTransmitRate;
    }

    /**
     * @return the strFuelLevelValue
     * @param void
     */
    public String getStrFuelLevelValue() {
        return strFuelLevelValue;
    }

    /**
     * @return StringstrFuelLevelValue
     * @param strFuelLevelValue
     *            the strFuelLevelValue to set
     */
    public void setStrFuelLevelValue(String strFuelLevelValue) {
        this.strFuelLevelValue = strFuelLevelValue;
    }

    /**
     * @return the strGeoZoneEvent
     * @param void
     */
    public String getStrGeoZoneEvent() {
        return strGeoZoneEvent;
    }

    /**
     * @return StringstrGeoZoneEvent
     * @param strGeoZoneEvent
     *            the strGeoZoneEvent to set
     */
    public void setStrGeoZoneEvent(String strGeoZoneEvent) {
        this.strGeoZoneEvent = strGeoZoneEvent;
    }

    /**
     * @return the strLocoMoving
     * @param void
     */
    public String getStrLocoMoving() {
        return strLocoMoving;
    }

    /**
     * @return StringstrLocoMoving
     * @param strLocoMoving
     *            the strLocoMoving to set
     */
    public void setStrLocoMoving(String strLocoMoving) {
        this.strLocoMoving = strLocoMoving;
    }

    /**
     * @return the strMinForTriggerMsg
     * @param void
     */
    public String getStrMinForTriggerMsg() {
        return strMinForTriggerMsg;
    }

    /**
     * @return StringstrMinForTriggerMsg
     * @param strMinForTriggerMsg
     *            the strMinForTriggerMsg to set
     */
    public void setStrMinForTriggerMsg(String strMinForTriggerMsg) {
        this.strMinForTriggerMsg = strMinForTriggerMsg;
    }

    /**
     * @return the strMNRNetwork
     * @param void
     */
    public String getStrMNRNetwork() {
        return strMNRNetwork;
    }

    /**
     * @return StringstrMNRNetwork
     * @param strMNRNetwork
     *            the strMNRNetwork to set
     */
    public void setStrMNRNetwork(String strMNRNetwork) {
        this.strMNRNetwork = strMNRNetwork;
    }

    /**
     * @return the strMsgReTriggerTime
     * @param void
     */
    public String getStrMsgReTriggerTime() {
        return strMsgReTriggerTime;
    }

    /**
     * @return StringstrMsgReTriggerTime
     * @param strMsgReTriggerTime
     *            the strMsgReTriggerTime to set
     */
    public void setStrMsgReTriggerTime(String strMsgReTriggerTime) {
        this.strMsgReTriggerTime = strMsgReTriggerTime;
    }

    /**
     * @return the strMsgScheduledTime
     * @param void
     */
    public String getStrMsgScheduledTime() {
        return strMsgScheduledTime;
    }

    /**
     * @return StringstrMsgScheduledTime
     * @param strMsgScheduledTime
     *            the strMsgScheduledTime to set
     */
    public void setStrMsgScheduledTime(String strMsgScheduledTime) {
        this.strMsgScheduledTime = strMsgScheduledTime;
    }

    /**
     * @return the strNotMovingCheckTime
     * @param void
     */
    public String getStrNotMovingCheckTime() {
        return strNotMovingCheckTime;
    }

    /**
     * @return StringstrNotMovingCheckTime
     * @param strNotMovingCheckTime
     *            the strNotMovingCheckTime to set
     */
    public void setStrNotMovingCheckTime(String strNotMovingCheckTime) {
        this.strNotMovingCheckTime = strNotMovingCheckTime;
    }

    /**
     * @return the strNotMovingDistance
     * @param void
     */
    public String getStrNotMovingDistance() {
        return strNotMovingDistance;
    }

    /**
     * @return StringstrNotMovingDistance
     * @param strNotMovingDistance
     *            the strNotMovingDistance to set
     */
    public void setStrNotMovingDistance(String strNotMovingDistance) {
        this.strNotMovingDistance = strNotMovingDistance;
    }

    /**
     * @return the strNotMovingSpeed
     * @param void
     */
    public String getStrNotMovingSpeed() {
        return strNotMovingSpeed;
    }

    /**
     * @return StringstrNotMovingSpeed
     * @param strNotMovingSpeed
     *            the strNotMovingSpeed to set
     */
    public void setStrNotMovingSpeed(String strNotMovingSpeed) {
        this.strNotMovingSpeed = strNotMovingSpeed;
    }

    /**
     * @return the strSpare
     * @param void
     */
    public String getStrSpare() {
        return strSpare;
    }

    /**
     * @return StringstrSpare
     * @param strSpare
     *            the strSpare to set
     */
    public void setStrSpare(String strSpare) {
        this.strSpare = strSpare;
    }

    /**
     * @return the strSpareBit
     * @param void
     */
    public String getStrSpareBit() {
        return strSpareBit;
    }

    /**
     * @return StringstrSpareBit
     * @param strSpareBit
     *            the strSpareBit to set
     */
    public void setStrSpareBit(String strSpareBit) {
        this.strSpareBit = strSpareBit;
    }

    /**
     * @return the strWLANetwork
     * @param void
     */
    public String getStrWLANetwork() {
        return strWLANetwork;
    }

    /**
     * @return StringstrWLANetwork
     * @param strWLANetwork
     *            the strWLANetwork to set
     */
    public void setStrWLANetwork(String strWLANetwork) {
        this.strWLANetwork = strWLANetwork;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("strAppNum", strAppNum)
                .append("strMessageId", strMessageId).append("strMessageFRV", strMessageFRV)
                .append("strMessageValidity", strMessageValidity).append("strMessageByteFollows", strMessageByteFollows)
                .append("strMessageLR", strMessageLR).append("strCustID", strCustID)
                .append("strRoadNumber", strRoadNumber).append("strRRRRRCCC", strRRRRRCCC)
                .append("strMessageTime", strMessageTime).append("strOffBoardSeqNum", strOffBoardSeqNum)
                .append("strNumOfZipBytes", strNumOfZipBytes).append("strTemplateNumber", strTemplateNumber)
                .append("strVersionNumber", strVersionNumber).append("blnCrcFlag", blnCrcFlag).append("strCRC", strCRC)
                .append("strMesageUserName", strMesageUserName).append("strMessageLocation", strMessageLocation)
                .append("strMsgScheduledTime", strMsgScheduledTime).append("strMsgReTriggerTime", strMsgReTriggerTime)
                .append("strNotMovingSpeed", strNotMovingSpeed).append("strNotMovingDistance", strNotMovingDistance)
                .append("strNotMovingCheckTime", strNotMovingCheckTime)
                .append("strMinForTriggerMsg", strMinForTriggerMsg).append("strFuelLevelDecVal", strFuelLevelDecVal)
                .append("strFuelLevelIncVal", strFuelLevelIncVal).append("strFuelLevelDecTime", strFuelLevelDecTime)
                .append("strFuelLevelIncTime", strFuelLevelIncTime)
                .append("strFuelLevelTransmitRate", strFuelLevelTransmitRate)
                .append("strFuelLevelValue", strFuelLevelValue).append("strMNRNetwork", strMNRNetwork)
                .append("strWLANetwork", strWLANetwork).append("strBWSNetwork", strBWSNetwork)
                .append("strFuelLevelLow", strFuelLevelLow).append("strFuelEvent", strFuelEvent)
                .append("strAPUFailsToStart", strAPUFailsToStart).append("strERMPanelPowerUp", strERMPanelPowerUp)
                .append("strEngineStartFault", strEngineStartFault).append("strLocoMoving", strLocoMoving)
                .append("strGeoZoneEvent", strGeoZoneEvent).append("strSpare", strSpare)
                .append("strBreadCrumb", strBreadCrumb).append("strSpareBit", strSpareBit)
                .append("strDNSLength", strDNSLength).append("strExtraData", strExtraData).toString();
    }

    /**
     * @return strExtraData
     */
    public String getStrExtraData() {
        return strExtraData;
    }

    /**
     * @param strExtraData
     */
    public void setStrExtraData(String strExtraData) {
        this.strExtraData = strExtraData;
    }

    /**
     * @return strCompression
     */
    public String getStrCompression() {
        return strCompression;
    }

    /**
     * @param strCompression
     */
    public void setStrCompression(String strCompression) {
        this.strCompression = strCompression;
    }

    /**
     * @return String
     */
    public String getStrNumWlanDestBytes() {
        return strNumWlanDestBytes;
    }

    /**
     * @param strNumWlanDestBytes
     */
    public void setStrNumWlanDestBytes(String strNumWlanDestBytes) {
        this.strNumWlanDestBytes = strNumWlanDestBytes;
    }

    /**
     * @return String
     */
    public String getStrWlanDest() {
        return strWlanDest;
    }

    /**
     * @param strWlanDest
     */
    public void setStrWlanDest(String strWlanDest) {
        this.strWlanDest = strWlanDest;
    }

}