/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FaultLogClearMessage.java
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

public class FaultLogClearMessage extends CommonMessageAttributes {

    private static final long serialVersionUID = -2193986865148996400L;

    private Long cmuEpochTime = null;
    private Date cmuTime = null;
    private Integer onboardSeqNo = null;
    private Integer faultSource = null;
    private Boolean isAllSubIDApplicable = null;
    private Integer allSubIDApplicable = null;
    private Boolean isResetTimePresent = null;
    private Integer resetTimePresent = null;
    private Integer faultSourceValue = null;
    private Long gpsLatitude = null;
    private Long gpsLongitude = null;
    private Integer gpsVelocity = null;
    private Long gpsHeading = null;
    private Integer gpsNumSatTrack = null;
    private Long controllerEpochTime = null;
    private Date controllerTime = null;

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
        String localpIsAllSubIDApplicable = pIsAllSubIDApplicable;
        localpIsAllSubIDApplicable = trimToNull(localpIsAllSubIDApplicable);
        this.allSubIDApplicable = getInteger(localpIsAllSubIDApplicable);

        this.isAllSubIDApplicable = ((localpIsAllSubIDApplicable != null) ? ((allSubIDApplicable == 0) ? true : false)
                : null);
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
     * @return the gpsVelocity
     */
    public Integer getGpsVelocity() {
        return gpsVelocity;
    }

    /**
     * @param pGpsVelocity
     *            the gpsVelocity to set
     */
    public void setGpsVelocity(String pGpsVelocity) {
        gpsVelocity = getInteger(pGpsVelocity);
    }

    /**
     * @return the gpsHeading
     */
    public Long getGpsHeading() {
        return gpsHeading;
    }

    /**
     * @param pGpsHeading
     *            the gpsHeading to set
     */
    public void setGpsHeading(String pGpsHeading) {
        gpsHeading = getLong(pGpsHeading);
    }

    /**
     * @return the gpsNumSatTrack
     */
    public Integer getGpsNumSatTrack() {
        return gpsNumSatTrack;
    }

    /**
     * @param pGpsNumSatTrack
     *            the gpsNumSatTrack to set
     */
    public void setGpsNumSatTrack(String pGpsNumSatTrack) {
        gpsNumSatTrack = getInteger(pGpsNumSatTrack);
    }

    /**
     * @return the controllerTime
     */
    public Date getControllerTime() {
        return controllerTime;
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
     * @return the controllerEpochTime
     */
    public Long getControllerEpochTime() {
        return controllerEpochTime;
    }

    /**
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