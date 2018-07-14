/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FaultCommStatusMessage.java
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

import com.ge.trans.rmd.omi.beans.msg.CommonMessageAttributes;

public class FaultCommStatusMessage extends CommonMessageAttributes {

    private static final long serialVersionUID = 3846178109532657055L;
    private Long locoCustRN = null;
    private Integer locoCustomerID = null;
    private Integer locoRoadNumber = null;
    private Integer primeFaultSource = null;
    private Integer faultCount = null;
    private Integer resetCount = null;
    private Integer skipCount = null;

    private String commDefinition = null;
    private Boolean isCommDefCAX = null;
    private Integer commDefCAX = null;
    private Boolean isCommDefCAB = null;
    private Integer commDefCAB = null;
    private Boolean isCommDefEXC = null;
    private Integer commDefEXC = null;
    private Boolean isCommDefAUX = null;
    private Integer commDefAUX = null;
    private Boolean isCommDefEFI = null;
    private Integer commDefEFI = null;
    private Boolean isCommDefPSC = null;
    private Integer commDefPSC = null;
    private Boolean isCommDefAC6000 = null;
    private Integer commDefAC6000 = null;
    private Boolean isCommDefCCA = null;
    private Integer commDefCCA = null;

    private String commState = null;
    private Boolean isCAXCommLoss = null;
    private Integer caxCommLoss = null;
    private Boolean isCABCommLoss = null;
    private Integer cabCommLoss = null;
    private Boolean isEXCCommLoss = null;
    private Integer excCommLoss = null;
    private Boolean isAUXCommLoss = null;
    private Integer auxCommLoss = null;
    private Boolean isEFICommLoss = null;
    private Integer efiCommLoss = null;
    private Boolean isPSCCommLoss = null;
    private Integer pscCommLoss = null;
    private Boolean isAC6000CommLoss = null;
    private Integer ac6000CommLoss = null;
    private Boolean isCCACommLoss = null;
    private Integer ccaCommLoss = null;

    private Long gpsLatitude = null;
    private Long gpsLongitude = null;
    private Integer gpsVelocity = null;

    /**
     * @return the locoCustRN
     */
    public Long getLocoCustRN() {
        return locoCustRN;
    }

    /**
     * @param pLocoCustRN
     *            the locoCustRN to set
     */
    public void setLocoCustRN(String pLocoCustRN) {
        locoCustRN = getLong(pLocoCustRN);
    }

    /**
     * @return the locoCustomerID
     */
    public Integer getLocoCustomerID() {
        return locoCustomerID;
    }

    /**
     * @param pLocoCustomerID
     *            the locoCustomerID to set
     */
    public void setLocoCustomerID(String pLocoCustomerID) {
        locoCustomerID = getInteger(pLocoCustomerID);
    }

    /**
     * @return the locoRoadNumber
     */
    public Integer getLocoRoadNumber() {
        return locoRoadNumber;
    }

    /**
     * @param pLocoRoadNumber
     *            the locoRoadNumber to set
     */
    public void setLocoRoadNumber(String pLocoRoadNumber) {
        locoRoadNumber = getInteger(pLocoRoadNumber);
    }

    /**
     * @return the primeFaultSource
     */
    public Integer getPrimeFaultSource() {
        return primeFaultSource;
    }

    /**
     * @param pPrimeFaultSource
     *            the primeFaultSource to set
     */
    public void setPrimeFaultSource(String pPrimeFaultSource) {
        primeFaultSource = getInteger(pPrimeFaultSource);
    }

    /**
     * @return the faultCount
     */
    public Integer getFaultCount() {
        return faultCount;
    }

    /**
     * @param pFaultCount
     *            the faultCount to set
     */
    public void setFaultCount(String pFaultCount) {
        faultCount = getInteger(pFaultCount);
    }

    /**
     * @return the resetCount
     */
    public Integer getResetCount() {
        return resetCount;
    }

    /**
     * @param pResetCount
     *            the resetCount to set
     */
    public void setResetCount(String pResetCount) {
        resetCount = getInteger(pResetCount);
    }

    /**
     * @return the skipCount
     */
    public Integer getSkipCount() {
        return skipCount;
    }

    /**
     * @param pSkipCount
     *            the skipCount to set
     */
    public void setSkipCount(String pSkipCount) {
        skipCount = getInteger(pSkipCount);
    }

    /**
     * @return the commDefinition
     */
    public String getCommDefinition() {
        return commDefinition;
    }

    /**
     * @param pCommDefinition
     *            the commDefinition to set
     */
    public void setCommDefinition(String pCommDefinition) {
        commDefinition = pCommDefinition;
    }

    /**
     * @return the isCommDefCAX
     */
    public Boolean getIsCommDefCAX() {
        return isCommDefCAX;
    }

    /**
     * @param pIsCommDefCAX
     *            the isCommDefCAX to set
     */
    public void setIsCommDefCAX(String pIsCommDefCAX) {
        String localpIsCommDefCAX = pIsCommDefCAX;
        localpIsCommDefCAX = trimToNull(localpIsCommDefCAX);
        this.commDefCAX = getInteger(localpIsCommDefCAX);

        this.isCommDefCAX = ((localpIsCommDefCAX != null) ? ((commDefCAX == 1) ? true : false) : null);
    }

    /**
     * @return the commDefCAX
     */
    public Integer getCommDefCAX() {
        return commDefCAX;
    }

    /**
     * @return the isCommDefCAB
     */
    public Boolean getIsCommDefCAB() {
        return isCommDefCAB;
    }

    /**
     * @param pIsCommDefCAB
     *            the isCommDefCAB to set
     */
    public void setIsCommDefCAB(String pIsCommDefCAB) {
        String localpIsCommDefCAB = pIsCommDefCAB;
        localpIsCommDefCAB = trimToNull(localpIsCommDefCAB);
        this.commDefCAB = getInteger(localpIsCommDefCAB);

        this.isCommDefCAB = ((localpIsCommDefCAB != null) ? ((commDefCAB == 1) ? true : false) : null);
    }

    /**
     * @return the commDefCAB
     */
    public Integer getCommDefCAB() {
        return commDefCAB;
    }

    /**
     * @return the isCommDefEXC
     */
    public Boolean getIsCommDefEXC() {
        return isCommDefEXC;
    }

    /**
     * @param pIsCommDefEXC
     *            the isCommDefEXC to set
     */
    public void setIsCommDefEXC(String pIsCommDefEXC) {
        String localpIsCommDefEXC = pIsCommDefEXC;
        localpIsCommDefEXC = trimToNull(localpIsCommDefEXC);
        this.commDefEXC = getInteger(localpIsCommDefEXC);

        this.isCommDefEXC = ((localpIsCommDefEXC != null) ? ((commDefEXC == 1) ? true : false) : null);
    }

    /**
     * @return the commDefEXC
     */
    public Integer getCommDefEXC() {
        return commDefEXC;
    }

    /**
     * @return the isCommDefAUX
     */
    public Boolean getIsCommDefAUX() {
        return isCommDefAUX;
    }

    /**
     * @param pIsCommDefAUX
     *            the isCommDefAUX to set
     */
    public void setIsCommDefAUX(String pIsCommDefAUX) {
        String localpIsCommDefAUX = pIsCommDefAUX;
        localpIsCommDefAUX = trimToNull(localpIsCommDefAUX);
        this.commDefAUX = getInteger(localpIsCommDefAUX);

        this.isCommDefAUX = ((localpIsCommDefAUX != null) ? ((commDefAUX == 1) ? true : false) : null);
    }

    /**
     * @return the commDefAUX
     */
    public Integer getCommDefAUX() {
        return commDefAUX;
    }

    /**
     * @return the isCommDefEFI
     */
    public Boolean getIsCommDefEFI() {
        return isCommDefEFI;
    }

    /**
     * @param pIsCommDefEFI
     *            the isCommDefEFI to set
     */
    public void setIsCommDefEFI(String pIsCommDefEFI) {
        String localpIsCommDefEFI = pIsCommDefEFI;
        localpIsCommDefEFI = trimToNull(localpIsCommDefEFI);
        this.commDefEFI = getInteger(localpIsCommDefEFI);

        this.isCommDefEFI = ((localpIsCommDefEFI != null) ? ((commDefEFI == 1) ? true : false) : null);
    }

    /**
     * @return the commDefEFI
     */
    public Integer getCommDefEFI() {
        return commDefEFI;
    }

    /**
     * @return the isCommDefPSC
     */
    public Boolean getIsCommDefPSC() {
        return isCommDefPSC;
    }

    /**
     * @param pIsCommDefPSC
     *            the isCommDefPSC to set
     */
    public void setIsCommDefPSC(String pIsCommDefPSC) {
        String localpIsCommDefPSC = pIsCommDefPSC;
        localpIsCommDefPSC = trimToNull(localpIsCommDefPSC);
        this.commDefPSC = getInteger(localpIsCommDefPSC);

        this.isCommDefPSC = ((localpIsCommDefPSC != null) ? ((commDefPSC == 1) ? true : false) : null);
    }

    /**
     * @return the commDefPSC
     */
    public Integer getCommDefPSC() {
        return commDefPSC;
    }

    /**
     * @return the isCommDefAC6000
     */
    public Boolean getIsCommDefAC6000() {
        return isCommDefAC6000;
    }

    /**
     * @param pIsCommDefAC6000
     *            the isCommDefAC6000 to set
     */
    public void setIsCommDefAC6000(String pIsCommDefAC6000) {
        String localpIsCommDefAC6000 = pIsCommDefAC6000;
        localpIsCommDefAC6000 = trimToNull(localpIsCommDefAC6000);
        this.commDefAC6000 = getInteger(localpIsCommDefAC6000);

        this.isCommDefAC6000 = ((localpIsCommDefAC6000 != null) ? ((commDefAC6000 == 1) ? true : false) : null);
    }

    /**
     * @return the commDefAC6000
     */
    public Integer getCommDefAC6000() {
        return commDefAC6000;
    }

    /**
     * @return the isCommDefCCA
     */
    public Boolean getIsCommDefCCA() {
        return isCommDefCCA;
    }

    /**
     * @param pIsCommDefCCA
     *            the isCommDefCCA to set
     */
    public void setIsCommDefCCA(String pIsCommDefCCA) {
        pIsCommDefCCA = trimToNull(pIsCommDefCCA);
        this.commDefCCA = getInteger(pIsCommDefCCA);

        this.isCommDefCCA = ((pIsCommDefCCA != null) ? ((commDefCCA == 1) ? true : false) : null);
    }

    /**
     * @return the commDefCCA
     */
    public Integer getCommDefCCA() {
        return commDefCCA;
    }

    /**
     * @return the commState
     */
    public String getCommState() {
        return commState;
    }

    /**
     * @param pCommState
     *            the commState to set
     */
    public void setCommState(String pCommState) {
        commState = pCommState;
    }

    /**
     * @return the isCAXCommLoss
     */
    public Boolean getIsCAXCommLoss() {
        return isCAXCommLoss;
    }

    /**
     * @param pIsCAXCommLoss
     *            the isCAXCommLoss to set
     */
    public void setIsCAXCommLoss(String pIsCAXCommLoss) {
        pIsCAXCommLoss = trimToNull(pIsCAXCommLoss);
        this.caxCommLoss = getInteger(pIsCAXCommLoss);

        this.isCAXCommLoss = ((pIsCAXCommLoss != null) ? ((caxCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the caxCommLoss
     */
    public Integer getCaxCommLoss() {
        return caxCommLoss;
    }

    /**
     * @return the isCABCommLoss
     */
    public Boolean getIsCABCommLoss() {
        return isCABCommLoss;
    }

    /**
     * @param pIsCABCommLoss
     *            the isCABCommLoss to set
     */
    public void setIsCABCommLoss(String pIsCABCommLoss) {
        pIsCABCommLoss = trimToNull(pIsCABCommLoss);
        this.cabCommLoss = getInteger(pIsCABCommLoss);

        this.isCABCommLoss = ((pIsCABCommLoss != null) ? ((cabCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the cabCommLoss
     */
    public Integer getCabCommLoss() {
        return cabCommLoss;
    }

    /**
     * @return the isEXCCommLoss
     */
    public Boolean getIsEXCCommLoss() {
        return isEXCCommLoss;
    }

    /**
     * @param pIsEXCCommLoss
     *            the isEXCCommLoss to set
     */
    public void setIsEXCCommLoss(String pIsEXCCommLoss) {
        pIsEXCCommLoss = trimToNull(pIsEXCCommLoss);
        this.excCommLoss = getInteger(pIsEXCCommLoss);

        this.isEXCCommLoss = ((pIsEXCCommLoss != null) ? ((excCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the excCommLoss
     */
    public Integer getExcCommLoss() {
        return excCommLoss;
    }

    /**
     * @return the isAUXCommLoss
     */
    public Boolean getIsAUXCommLoss() {
        return isAUXCommLoss;
    }

    /**
     * @param pIsAUXCommLoss
     *            the isAUXCommLoss to set
     */
    public void setIsAUXCommLoss(String pIsAUXCommLoss) {
        pIsAUXCommLoss = trimToNull(pIsAUXCommLoss);
        this.auxCommLoss = getInteger(pIsAUXCommLoss);

        this.isAUXCommLoss = ((pIsAUXCommLoss != null) ? ((auxCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the auxCommLoss
     */
    public Integer getAuxCommLoss() {
        return auxCommLoss;
    }

    /**
     * @return the isEFICommLoss
     */
    public Boolean getIsEFICommLoss() {
        return isEFICommLoss;
    }

    /**
     * @param pIsEFICommLoss
     *            the isEFICommLoss to set
     */
    public void setIsEFICommLoss(String pIsEFICommLoss) {
        pIsEFICommLoss = trimToNull(pIsEFICommLoss);
        this.efiCommLoss = getInteger(pIsEFICommLoss);

        this.isEFICommLoss = ((pIsEFICommLoss != null) ? ((efiCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the efiCommLoss
     */
    public Integer getEfiCommLoss() {
        return efiCommLoss;
    }

    /**
     * @return the isPSCCommLoss
     */
    public Boolean getIsPSCCommLoss() {
        return isPSCCommLoss;
    }

    /**
     * @param pIsPSCCommLoss
     *            the isPSCCommLoss to set
     */
    public void setIsPSCCommLoss(String pIsPSCCommLoss) {
        pIsPSCCommLoss = trimToNull(pIsPSCCommLoss);
        this.pscCommLoss = getInteger(pIsPSCCommLoss);

        this.isPSCCommLoss = ((pIsPSCCommLoss != null) ? ((pscCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the pscCommLoss
     */
    public Integer getPscCommLoss() {
        return pscCommLoss;
    }

    /**
     * @return the isAC6000CommLoss
     */
    public Boolean getIsAC6000CommLoss() {
        return isAC6000CommLoss;
    }

    /**
     * @param pIsAC6000CommLoss
     *            the isAC6000CommLoss to set
     */
    public void setIsAC6000CommLoss(String pIsAC6000CommLoss) {
        pIsAC6000CommLoss = trimToNull(pIsAC6000CommLoss);
        this.ac6000CommLoss = getInteger(pIsAC6000CommLoss);

        this.isAC6000CommLoss = ((pIsAC6000CommLoss != null) ? ((ac6000CommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the ac6000CommLoss
     */
    public Integer getAc6000CommLoss() {
        return ac6000CommLoss;
    }

    /**
     * @return the isCCACommLoss
     */
    public Boolean getIsCCACommLoss() {
        return isCCACommLoss;
    }

    /**
     * @param pIsCCACommLoss
     *            the isCCACommLoss to set
     */
    public void setIsCCACommLoss(String pIsCCACommLoss) {
        pIsCCACommLoss = trimToNull(pIsCCACommLoss);
        this.ccaCommLoss = getInteger(pIsCCACommLoss);

        this.isCCACommLoss = ((pIsCCACommLoss != null) ? ((ccaCommLoss == 0) ? true : false) : null);
    }

    /**
     * @return the ccaCommLoss
     */
    public Integer getCcaCommLoss() {
        return ccaCommLoss;
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
}