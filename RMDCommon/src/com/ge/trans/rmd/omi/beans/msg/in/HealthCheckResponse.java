/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   HealthCheckResponse.java
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

public class HealthCheckResponse extends CommonMessageAttributes {

    private static final long serialVersionUID = -4215116800976691695L;

    private Integer sdpTemplateNo = null;
    private Integer sdpVersionNo = null;
    private Long cmuEpochTime = null;
    private Date cmuTime = null;
    private Boolean isError = null;
    private Integer errorLevel = null;
    private Long gpsLatitude = null;
    private Long gpsLongitude = null;
    private Integer gpsVelocity = null;
    private Long gpsHeading = null;
    private Integer gpsNumSatTrack = null;
    private Long controllerEpochTime = null;
    private Date controllerTime = null;
    private StandardDatapackParameters sdpParams = null;
    private String sdpValidity = null;

    /**
     * Gets the sdp template no.
     * 
     * @return the sdp template no
     */
    public Integer getSdpTemplateNo() {
        return sdpTemplateNo;
    }

    /**
     * Sets the sdp template no.
     * 
     * @param pSdpTemplateNo
     *            the new sdp template no
     */
    public void setSdpTemplateNo(String pSdpTemplateNo) {
        sdpTemplateNo = getInteger(pSdpTemplateNo);
    }

    /**
     * Gets the sdp version no.
     * 
     * @return the sdp version no
     */
    public Integer getSdpVersionNo() {
        return sdpVersionNo;
    }

    /**
     * Sets the sdp version no.
     * 
     * @param pSdpVersionNo
     *            the new sdp version no
     */
    public void setSdpVersionNo(String pSdpVersionNo) {
        sdpVersionNo = getInteger(pSdpVersionNo);
    }

    /**
     * Gets the cMU time.
     * 
     * @return the cMU time
     */
    public Date getCMUTime() {
        return cmuTime;
    }

    /**
     * Gets the checks if is error.
     * 
     * @return the checks if is error
     */
    public Boolean getIsError() {
        return isError;
    }

    /**
     * Sets the checks if is error.
     * 
     * @param pIsError
     *            the new checks if is error
     */
    public void setIsError(String pIsError) {
        String localpIsError = pIsError;
        localpIsError = trimToNull(localpIsError);
        this.errorLevel = getInteger(localpIsError);

        this.isError = ((localpIsError != null) ? ((errorLevel > 0) ? true : false) : null);
    }

    /**
     * Gets the error level.
     * 
     * @return the error level
     */
    public Integer getErrorLevel() {
        return errorLevel;
    }

    /**
     * Gets the gps latitude.
     * 
     * @return the gps latitude
     */
    public Long getGpsLatitude() {
        return gpsLatitude;
    }

    /**
     * Sets the gps latitude.
     * 
     * @param pGpsLatitude
     *            the new gps latitude
     */
    public void setGpsLatitude(String pGpsLatitude) {
        gpsLatitude = getLong(pGpsLatitude);
    }

    /**
     * Gets the gps longitude.
     * 
     * @return the gps longitude
     */
    public Long getGpsLongitude() {
        return gpsLongitude;
    }

    /**
     * Sets the gps longitude.
     * 
     * @param pGpsLongitude
     *            the new gps longitude
     */
    public void setGpsLongitude(String pGpsLongitude) {
        gpsLongitude = getLong(pGpsLongitude);
    }

    /**
     * Gets the gps velocity.
     * 
     * @return the gps velocity
     */
    public Integer getGpsVelocity() {
        return gpsVelocity;
    }

    /**
     * Sets the gps velocity.
     * 
     * @param pGpsVelocity
     *            the new gps velocity
     */
    public void setGpsVelocity(String pGpsVelocity) {
        gpsVelocity = getInteger(pGpsVelocity);
    }

    /**
     * Gets the gps heading.
     * 
     * @return the gps heading
     */
    public Long getGpsHeading() {
        return gpsHeading;
    }

    /**
     * Sets the gps heading.
     * 
     * @param pGpsHeading
     *            the new gps heading
     */
    public void setGpsHeading(String pGpsHeading) {
        gpsHeading = getLong(pGpsHeading);
    }

    /**
     * Gets the gps num sat track.
     * 
     * @return the gps num sat track
     */
    public Integer getGpsNumSatTrack() {
        return gpsNumSatTrack;
    }

    /**
     * Sets the gps num sat track.
     * 
     * @param pGpsNumSatTrack
     *            the new gps num sat track
     */
    public void setGpsNumSatTrack(String pGpsNumSatTrack) {
        gpsNumSatTrack = getInteger(pGpsNumSatTrack);
    }

    /**
     * Gets the controller time.
     * 
     * @return the controller time
     */
    public Date getControllerTime() {
        return controllerTime;
    }

    /**
     * Gets the sdp params.
     * 
     * @return the sdp params
     */
    public StandardDatapackParameters getSdpParams() {
        return sdpParams;
    }

    /**
     * Sets the sdp params.
     * 
     * @param pSdpParams
     *            the new sdp params
     */
    public void setSdpParams(StandardDatapackParameters pSdpParams) {
        sdpParams = pSdpParams;
    }

    /**
     * Gets the sdp validity.
     * 
     * @return the sdp validity
     */
    public String getSdpValidity() {
        return sdpValidity;
    }

    /**
     * Sets the sdp validity.
     * 
     * @param pSdpValidity
     *            the new sdp validity
     */
    public void setSdpValidity(String pSdpValidity) {
        sdpValidity = getString(pSdpValidity);
    }

    /**
     * Gets the cmu epoch time.
     * 
     * @return the cMUEpochTime
     */
    public Long getCMUEpochTime() {
        return cmuEpochTime;
    }

    /**
     * Sets the cmu epoch time.
     * 
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
     * Gets the controller epoch time.
     * 
     * @return the controllerEpochTime
     */
    public Long getControllerEpochTime() {
        return controllerEpochTime;
    }

    /**
     * Sets the controller epoch time.
     * 
     * @param pControllerEpochTime
     *            the controllerEpochTime to set
     */
    public void setControllerEpochTime(String pControllerEpochTime) {
        controllerEpochTime = getLong(pControllerEpochTime);

        if (controllerEpochTime != null && controllerEpochTime > 0) {
            controllerTime = getDate(controllerEpochTime);
        }
    }
}