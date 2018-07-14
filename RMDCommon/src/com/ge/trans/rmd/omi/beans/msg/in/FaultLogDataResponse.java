/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FaultLogDataResponse.java
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

public class FaultLogDataResponse extends CommonMessageAttributes {

    private static final long serialVersionUID = -5999625226087846150L;
    private String strErrorProcessingAAAA = null;
    private String strErrorProcessingBBBB = null;
    private String strOnboardSeqNo = null;
    // private FaultLogTimeParameters fltParams = null;
    private String strSameMPDataA = null;
    private String strSameMPDataB = null;
    private String strSDPVVVV = null;
    private String strFLTSource = null;
    private String strFLTSourceC = null;
    private String strIndex = null;
    private String strSubSystem = null;
    private String strNumber = null;
    private Date occurTime = null;
    private Date resetTime = null;
    private Date cmuTime = null;
    private StandardDatapackParameters sdpParams = null;
    private Boolean isError = null;
    private Integer errorLevel = null;
    private String strSameMPData = null;
    private String strSdpValidity = null;
    private String strCMUSkipCount = null;
    private Long gpsLatitude = null;
    private Long gpsLongitude = null;

    /**
     * @return the gpsLatitude
     */
    public Long getGpsLatitude() {
        return gpsLatitude;
    }

    /**
     * @param gpsLatitude
     *            the gpsLatitude to set
     */
    public void setGpsLatitude(String gpsLatitude) {
        this.gpsLatitude = getLong(gpsLatitude);
    }

    /**
     * @return the gpsLongitude
     */
    public Long getGpsLongitude() {
        return gpsLongitude;
    }

    /**
     * @param gpsLongitude
     *            the gpsLongitude to set
     */
    public void setGpsLongitude(String gpsLongitude) {
        this.gpsLongitude = getLong(gpsLongitude);
    }

    /**
     * @return the strCMUSkipCount
     */
    public String getStrCMUSkipCount() {
        return strCMUSkipCount;
    }

    /**
     * @param strCMUSkipCount
     *            the strCMUSkipCount to set
     */
    public void setStrCMUSkipCount(String strCMUSkipCount) {
        this.strCMUSkipCount = strCMUSkipCount;
    }

    /**
     * @return the strSdpValidity
     */
    public String getStrSdpValidity() {
        return strSdpValidity;
    }

    /**
     * @param strSdpValidity
     *            the strSdpValidity to set
     */
    public void setStrSdpValidity(String strSdpValidity) {
        this.strSdpValidity = strSdpValidity;
    }

    /**
     * @return the strSameMPData
     */
    public String getStrSameMPData() {
        return strSameMPData;
    }

    /**
     * @param strSameMPData
     *            the strSameMPData to set
     */
    public void setStrSameMPData(String strSameMPData) {
        this.strSameMPData = strSameMPData;
    }

    /**
     * @return the strFLTSource
     */
    public String getStrFLTSource() {
        return strFLTSource;
    }

    /**
     * @param strFLTSource
     *            the strFLTSource to set
     */
    public void setStrFLTSource(String strFLTSource) {
        this.strFLTSource = strFLTSource;
    }

    /**
     * @return the strFLTSourceC
     */
    public String getStrFLTSourceC() {
        return strFLTSourceC;
    }

    /**
     * @param strFLTSourceC
     *            the strFLTSourceC to set
     */
    public void setStrFLTSourceC(String strFLTSourceC) {
        this.strFLTSourceC = strFLTSourceC;
    }

    /**
     * @return the strIndex
     */
    public String getStrIndex() {
        return strIndex;
    }

    /**
     * @param strIndex
     *            the strIndex to set
     */
    public void setStrIndex(String strIndex) {
        this.strIndex = strIndex;
    }

    /**
     * @return the strSubSystem
     */
    public String getStrSubSystem() {
        return strSubSystem;
    }

    /**
     * @param strSubSystem
     *            the strSubSystem to set
     */
    public void setStrSubSystem(String strSubSystem) {
        this.strSubSystem = strSubSystem;
    }

    /**
     * @return the strNumber
     */
    public String getStrNumber() {
        return strNumber;
    }

    /**
     * @param strNumber
     *            the strNumber to set
     */
    public void setStrNumber(String strNumber) {
        this.strNumber = strNumber;
    }

    /**
     * @return the occurTime
     */
    public Date getOccurTime() {
        return occurTime;
    }

    /**
     * @param occurTime
     *            the occurTime to set
     */
    public void setOccurTime(String occurTime) {
        this.occurTime = getDate(Long.parseLong(occurTime));
    }

    /**
     * @return the resetTime
     */
    public Date getResetTime() {
        return resetTime;
    }

    /**
     * @param resetTime
     *            the resetTime to set
     */
    public void setResetTime(String resetTime) {
        this.resetTime = getDate(Long.parseLong(resetTime));
    }

    /**
     * @return the strErrorProcessingAAAA
     */
    public String getStrErrorProcessingAAAA() {
        return strErrorProcessingAAAA;
    }

    /**
     * @param strErrorProcessingAAAA
     *            the strErrorProcessingAAAA to set
     */
    public void setStrErrorProcessingAAAA(String strErrorProcessingAAAA) {
        this.strErrorProcessingAAAA = strErrorProcessingAAAA;
    }

    /**
     * @return the strErrorProcessingBBBB
     */
    public String getStrErrorProcessingBBBB() {
        return strErrorProcessingBBBB;
    }

    /**
     * @param strErrorProcessingBBBB
     *            the strErrorProcessingBBBB to set
     */
    public void setStrErrorProcessingBBBB(String strErrorProcessingBBBB) {
        this.strErrorProcessingBBBB = strErrorProcessingBBBB;
    }

    /**
     * @return the strSameMPDataA
     */
    public String getStrSameMPDataA() {
        return strSameMPDataA;
    }

    /**
     * @param strSameMPDataA
     *            the strSameMPDataA to set
     */
    public void setStrSameMPDataA(String strSameMPDataA) {
        this.strSameMPDataA = strSameMPDataA;
    }

    /**
     * @return the strSameMPDataB
     */
    public String getStrSameMPDataB() {
        return strSameMPDataB;
    }

    /**
     * @param strSameMPDataB
     *            the strSameMPDataB to set
     */
    public void setStrSameMPDataB(String strSameMPDataB) {
        this.strSameMPDataB = strSameMPDataB;
    }

    /**
     * @return the strSDPVVVV
     */
    public String getStrSDPVVVV() {
        return strSDPVVVV;
    }

    /**
     * @param strSDPVVVV
     *            the strSDPVVVV to set
     */
    public void setStrSDPVVVV(String strSDPVVVV) {
        this.strSDPVVVV = strSDPVVVV;
    }

    /**
     * @return the strOnboardSeqNo
     */
    public String getStrOnboardSeqNo() {
        return strOnboardSeqNo;
    }

    /**
     * @param strOnboardSeqNo
     *            the strOnboardSeqNo to set
     */
    public void setStrOnboardSeqNo(String strOnboardSeqNo) {
        this.strOnboardSeqNo = strOnboardSeqNo;
    }

    /**
     * @return the sdpParams
     */
    public StandardDatapackParameters getSdpParams() {
        return sdpParams;
    }

    /**
     * @param sdpParams
     *            the sdpParams to set
     */
    public void setSdpParams(StandardDatapackParameters sdpParams) {
        this.sdpParams = sdpParams;
    }

    /**
     * @return the cMUTime
     */
    public Date getCMUTime() {
        return cmuTime;
    }

    /**
     * @param time
     *            the cMUTime to set
     */
    public void setCMUTime(String time) {
        cmuTime = getDate(Long.parseLong(time));
    }

    /**
     * @return the isError
     */
    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(String pIsError) {
        String localpIsError = pIsError;
        localpIsError = trimToNull(localpIsError);
        this.errorLevel = getInteger(localpIsError);

        this.isError = ((localpIsError != null) ? ((errorLevel == 1) ? true : false) : null);
    }

    /**
     * @param isError
     *            the isError to set
     */
    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public Integer getErrorLevel() {
        return errorLevel;
    }

}
