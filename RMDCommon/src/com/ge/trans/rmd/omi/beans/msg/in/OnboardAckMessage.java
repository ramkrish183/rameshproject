/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   OnboardAckMessage.java
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

public class OnboardAckMessage extends CommonMessageAttributes {
    private static final long serialVersionUID = 2321661500689688511L;

    private Long locoCustRN = null;
    private Integer locoCustomerID = null;
    private Integer locoRoadNumber = null;
    private Integer reasonCode = null;

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
     * @return the reasonCode
     */
    public Integer getReasonCode() {
        return reasonCode;
    }

    /**
     * @param pReasonCode
     *            the reasonCode to set
     */
    public void setReasonCode(String pReasonCode) {
        reasonCode = getInteger(pReasonCode);
    }
}