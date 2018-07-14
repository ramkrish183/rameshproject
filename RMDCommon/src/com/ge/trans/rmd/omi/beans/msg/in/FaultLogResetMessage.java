/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FaultLogResetMessage.java
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

public class FaultLogResetMessage extends CommonMessageAttributes {

    private static final long serialVersionUID = 7746551525743929097L;

    private Long cmuEpochTime = null;
    private Date cmuTime = null;
    private Integer onboardSeqNo = null;
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