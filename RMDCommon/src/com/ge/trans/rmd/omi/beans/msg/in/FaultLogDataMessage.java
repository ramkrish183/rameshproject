/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FaultLogDataMessage.java
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

package com.ge.trans.rmd.omi.beans.msg.in;

import com.ge.trans.rmd.omi.beans.Date;
import com.ge.trans.rmd.omi.beans.msg.CommonMessageAttributes;

public class FaultLogDataMessage extends CommonMessageAttributes {
    private static final long serialVersionUID = -5999625226087846150L;

    private Long cmuEpochTime = null;
    private Date cmuTime = null;
    private Integer onboardSeqNo = null;
    private Boolean isEDPError = null;
    private Integer edpError = null;
    private Boolean isSDPError = null;
    private Integer sdpError = null;
    private Integer errorProcessing = null;
    private Integer faultSource = null;
    private Boolean isAllSubIDApplicable = null;
    private Integer allSubIDApplicable = null;
    private Boolean isResetTimePresent = null;
    private Integer resetTimePresent = null;
    private Integer faultSourceValue = null;
    private Integer faultIndex = null;
    private String faultSubSystem = null;
    private String faultNumber = null;
    private Long faultOccurEpochTime = null;
    private Date faultOccurTime = null;
    private Long faultResetEpochTime = null;
    private Date faultResetTime = null;
    private Boolean isSameSdpMPData = null;
    private Integer sameSdpMPData = null;
    private Boolean isSameEdpMPData = null;
    private Integer sameEdpMPData = null;
    private Integer sameMPData = null;
    private Integer sdpTemplateNo = null;
    private Integer sdpVersionNo = null;
    private StandardDatapackParameters sdpParams = null;
    private String sdpValidity = null;
    private Integer cmuSkipCount = null;
    private Long gpsLatitude = null;
    private Long gpsLongitude = null;

    /**
     * @return the cMUTime
     */
    public Date getCMUTime() {
        return cmuTime;
    }

    /**
     * @return the onboardSeqNo
     */
    public Integer getOnboardSeqNo() {
        return onboardSeqNo;
    }

    /**
     * @param pOnboardSeqNo
     *            the onboardSeqNo to set
     */
    public void setOnboardSeqNo(String pOnboardSeqNo) {
        onboardSeqNo = getInteger(pOnboardSeqNo);
    }

    /**
     * @return the isEDPError
     */
    public Boolean getIsEDPError() {
        return isEDPError;
    }

    /**
     * @param pIsEDPError
     *            the isEDPError to set
     */
    public void setIsEDPError(String pIsEDPError) {
        String localpIsEDPError = pIsEDPError;
        localpIsEDPError = trimToNull(localpIsEDPError);
        this.edpError = getInteger(localpIsEDPError);

        this.isEDPError = ((localpIsEDPError != null) ? ((edpError > 0) ? true : false) : null);
    }

    /**
     * @return the edpError
     */
    public Integer getEdpError() {
        return edpError;
    }

    /**
     * @return the isSDPError
     */
    public Boolean getIsSDPError() {
        return isSDPError;
    }

    /**
     * @param pIsSDPError
     *            the isSDPError to set
     */
    public void setIsSDPError(String pIsSDPError) {
        String localpIsSDPError = pIsSDPError;
        localpIsSDPError = trimToNull(localpIsSDPError);
        this.sdpError = getInteger(localpIsSDPError);

        this.isSDPError = ((localpIsSDPError != null) ? ((sdpError > 0) ? true : false) : null);
    }

    /**
     * @return the sdpError
     */
    public Integer getSdpError() {
        return sdpError;
    }

    /**
     * @return the errorProcessing
     */
    public Integer getErrorProcessing() {
        return errorProcessing;
    }

    /**
     * @param pErrorProcessing
     *            the errorProcessing to set
     */
    public void setErrorProcessing(String pErrorProcessing) {
        errorProcessing = getInteger(pErrorProcessing);
    }

    /**
     * @return the faultSource
     */
    public Integer getFaultSource() {
        return faultSource;
    }

    /**
     * @param pFaultSource
     *            the faultSource to set
     */
    public void setFaultSource(String pFaultSource) {
        faultSource = getInteger(pFaultSource);
    }

    /**
     * @return the isAllSubIDApplicable
     */
    public Boolean getIsAllSubIDApplicable() {
        return isAllSubIDApplicable;
    }

    /**
     * @param pIsAllSubIDApplicable
     *            the isAllSubIDApplicable to set
     */
    public void setIsAllSubIDApplicable(String pIsAllSubIDApplicable) {
        String localApplicable = pIsAllSubIDApplicable;
        localApplicable = trimToNull(localApplicable);
        this.allSubIDApplicable = getInteger(localApplicable);

        this.isAllSubIDApplicable = ((localApplicable != null) ? ((allSubIDApplicable == 0) ? true : false) : null);
    }

    /**
     * @return the allSubIDApplicable
     */
    public Integer getAllSubIDApplicable() {
        return allSubIDApplicable;
    }

    /**
     * @return the isResetTimePresent
     */
    public Boolean getIsResetTimePresent() {
        return isResetTimePresent;
    }

    /**
     * @param pIsResetTimePresent
     *            the isResetTimePresent to set
     */
    public void setIsResetTimePresent(String pIsResetTimePresent) {
        String localpIsResetTimePresent = pIsResetTimePresent;
        localpIsResetTimePresent = trimToNull(localpIsResetTimePresent);
        this.resetTimePresent = getInteger(localpIsResetTimePresent);

        this.isResetTimePresent = ((localpIsResetTimePresent != null) ? ((resetTimePresent == 0) ? true : false)
                : null);
    }

    /**
     * @return the resetTimePresent
     */
    public Integer getResetTimePresent() {
        return resetTimePresent;
    }

    /**
     * @return the faultSourceValue
     */
    public Integer getFaultSourceValue() {
        return faultSourceValue;
    }

    /**
     * @param pFaultSourceValue
     *            the faultSourceValue to set
     */
    public void setFaultSourceValue(String pFaultSourceValue) {
        faultSourceValue = getInteger(pFaultSourceValue);
    }

    /**
     * @return the faultIndex
     */
    public Integer getFaultIndex() {
        return faultIndex;
    }

    /**
     * @param pFaultIndex
     *            the faultIndex to set
     */
    public void setFaultIndex(String pFaultIndex) {
        faultIndex = getInteger(pFaultIndex);
    }

    /**
     * @return the faultSubSystem
     */
    public String getFaultSubSystem() {
        return faultSubSystem;
    }

    /**
     * @param pFaultSubSystem
     *            the faultSubSystem to set
     */
    public void setFaultSubSystem(String pFaultSubSystem) {
        faultSubSystem = pFaultSubSystem;
    }

    /**
     * @return the faultNumber
     */
    public String getFaultNumber() {
        return faultNumber;
    }

    /**
     * @param pFaultNumber
     *            the faultNumber to set
     */
    public void setFaultNumber(String pFaultNumber) {
        faultNumber = pFaultNumber;
    }

    /**
     * @return the faultOccurTime
     */
    public Date getFaultOccurTime() {
        return faultOccurTime;
    }

    /**
     * @return the faultResetTime
     */
    public Date getFaultResetTime() {
        return faultResetTime;
    }

    /**
     * @return the isSameSdpMPData
     */
    public Boolean getIsSameSdpMPData() {
        return isSameSdpMPData;
    }

    /**
     * @param pIsSameSdpMPData
     *            the isSameSdpMPData to set
     */
    public void setIsSameSdpMPData(String pIsSameSdpMPData) {
        String localpIsSameSdpMPData = pIsSameSdpMPData;
        localpIsSameSdpMPData = trimToNull(localpIsSameSdpMPData);
        this.sameSdpMPData = getInteger(localpIsSameSdpMPData);

        this.isSameSdpMPData = ((localpIsSameSdpMPData != null) ? ((sameSdpMPData == 0) ? true : false) : null);
    }

    /**
     * @return the sameSdpMPData
     */
    public Integer getSameSdpMPData() {
        return sameSdpMPData;
    }

    /**
     * @return the isSameEdpMPData
     */
    public Boolean getIsSameEdpMPData() {
        return isSameEdpMPData;
    }

    /**
     * @param pIsSameEdpMPData
     *            the isSameEdpMPData to set
     */
    public void setIsSameEdpMPData(String pIsSameEdpMPData) {
        String localpIsSameEdpMPData = pIsSameEdpMPData;
        localpIsSameEdpMPData = trimToNull(localpIsSameEdpMPData);
        this.sameEdpMPData = getInteger(localpIsSameEdpMPData);

        this.isSameEdpMPData = ((localpIsSameEdpMPData != null) ? ((sameEdpMPData == 0) ? true : false) : null);
    }

    /**
     * @return the sameEdpMPData
     */
    public Integer getSameEdpMPData() {
        return sameEdpMPData;
    }

    /**
     * @return the sameMPData
     */
    public Integer getSameMPData() {
        return sameMPData;
    }

    /**
     * @param pSameMPData
     *            the sameMPData to set
     */
    public void setSameMPData(String pSameMPData) {
        sameMPData = getInteger(pSameMPData);
    }

    /**
     * @return the sdpTemplateNo
     */
    public Integer getSdpTemplateNo() {
        return sdpTemplateNo;
    }

    /**
     * @param pSdpTemplateNo
     *            the sdpTemplateNo to set
     */
    public void setSdpTemplateNo(String pSdpTemplateNo) {
        sdpTemplateNo = getInteger(pSdpTemplateNo);
    }

    /**
     * @return the sdpVersionNo
     */
    public Integer getSdpVersionNo() {
        return sdpVersionNo;
    }

    /**
     * @param pSdpVersionNo
     *            the sdpVersionNo to set
     */
    public void setSdpVersionNo(String pSdpVersionNo) {
        sdpVersionNo = getInteger(pSdpVersionNo);
    }

    /**
     * @return the sdpParams
     */
    public StandardDatapackParameters getSdpParams() {
        return sdpParams;
    }

    /**
     * @param pSdpParams
     *            the sdpParams to set
     */
    public void setSdpParams(StandardDatapackParameters pSdpParams) {
        sdpParams = pSdpParams;
    }

    /**
     * @return the sdpValidity
     */
    public String getSdpValidity() {
        return sdpValidity;
    }

    /**
     * @param pSdpValidity
     *            the sdpValidity to set
     */
    public void setSdpValidity(String pSdpValidity) {
        sdpValidity = pSdpValidity;
    }

    /**
     * @return the cMUSkipCount
     */
    public Integer getCMUSkipCount() {
        return cmuSkipCount;
    }

    /**
     * @param pCMUSkipCount
     *            the cMUSkipCount to set
     */
    public void setCMUSkipCount(String pCMUSkipCount) {
        cmuSkipCount = getInteger(pCMUSkipCount);
    }

    /**
     * @return the gpsLatitude
     */
    public Long getGpsLatitude() {
        return gpsLatitude;
    }

    /**
     * @param pGpsLatitude
     *            the gpsLatitude to set
     */
    public void setGpsLatitude(String pGpsLatitude) {
        gpsLatitude = getLong(pGpsLatitude);
    }

    /**
     * @return the gpsLongitude
     */
    public Long getGpsLongitude() {
        return gpsLongitude;
    }

    /**
     * @param pGpsLongitude
     *            the gpsLongitude to set
     */
    public void setGpsLongitude(String pGpsLongitude) {
        gpsLongitude = getLong(pGpsLongitude);
    }

    /**
     * @return the cMUEpochTime
     */
    public Long getCMUEpochTime() {
        return cmuEpochTime;
    }

    /**
     * @param pCMUEpochTime
     *            the cMUEpochTime to set
     */
    public void setCMUEpochTime(String pCMUEpochTime) {
        cmuEpochTime = getLong(pCMUEpochTime);

        if (cmuEpochTime != null && cmuEpochTime > 0) {
            cmuTime = getDate(cmuEpochTime);
        }
    }

    /**
     * @return the faultOccurEpochTime
     */
    public Long getFaultOccurEpochTime() {
        return faultOccurEpochTime;
    }

    /**
     * @param pFaultOccurEpochTime
     *            the faultOccurEpochTime to set
     */
    public void setFaultOccurEpochTime(String pFaultOccurEpochTime) {
        faultOccurEpochTime = getLong(pFaultOccurEpochTime);

        if (faultOccurEpochTime != null && faultOccurEpochTime > 0) {
            faultOccurTime = getDate(faultOccurEpochTime);
        }
    }

    /**
     * @return the faultResetEpochTime
     */
    public Long getFaultResetEpochTime() {
        return faultResetEpochTime;
    }

    /**
     * @param pFaultResetEpochTime
     *            the faultResetEpochTime to set
     */
    public void setFaultResetEpochTime(String pFaultResetEpochTime) {
        faultResetEpochTime = getLong(pFaultResetEpochTime);

        if (faultResetEpochTime != null && faultResetEpochTime > 0) {
            faultResetTime = getDate(faultResetEpochTime);
        }
    }
}