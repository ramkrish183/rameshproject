/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   XMLMessageRetryVO.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This is the bean Class for the XML Message Retry for QTR OMI.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.xml;

import org.apache.commons.lang.StringUtils;

/**
 * This is the bean Class for the XML Message Retry for QTR OMI. Along with the
 * getters and setter , this class define a toXML() The toXML method returns the
 * XML Retry required for every message.
 */
public class XMLMessageRetryVO extends XMLFormattedObject {

    private static final long serialVersionUID = 1L;

    public static final String XML_MSG_RETRY = "msg-retry";
    public static final String XML_MSG_RETRY_ID = "id";
    public static final String XML_MSG_RETRY_MAX_ATTEMPTS = "max-attempts";
    public static final String XML_MSG_RETRY_COUNT = "count";

    /* Message Retry elements */
    private String strMessageRetryID = null;
    private String strMaxAttempts = null;
    private String strCount = null;

    /**
     * @return the strMessageRetryID
     */
    public String getStrMessageRetryID() {
        return strMessageRetryID;
    }

    /**
     * @param strMessageRetryID
     *            the strMessageRetryID to set
     */
    public void setStrMessageRetryID(String strMessageRetryID) {
        this.strMessageRetryID = strMessageRetryID;
    }

    /**
     * @return the strMaxAttempts
     */
    public String getStrMaxAttempts() {
        return strMaxAttempts;
    }

    /**
     * @param strMaxAttempts
     *            the strMaxAttempts to set
     */
    public void setStrMaxAttempts(String strMaxAttempts) {
        this.strMaxAttempts = strMaxAttempts;
    }

    /**
     * @return the strCount
     */
    public String getStrCount() {
        return strCount;
    }

    /**
     * @param strCount
     *            the strCount to set
     */
    public void setStrCount(String strCount) {
        this.strCount = strCount;
    }

    @Override
    public String toXML() {
        StringBuilder strBuffer = new StringBuilder("");
        if (checkMessageRetryTag()) {
            strBuffer.append(XML_TAG_START).append(XML_MSG_RETRY);
        }
        if (StringUtils.isNotBlank(getStrMessageRetryID()))
            strBuffer.append(getAttribute(XML_MSG_RETRY_ID, getStrMessageRetryID()));
        if (StringUtils.isNotBlank(getStrMaxAttempts()))
            strBuffer.append(getAttribute(XML_MSG_RETRY_MAX_ATTEMPTS, getStrMaxAttempts()));
        if (StringUtils.isNotBlank(getStrCount()))
            strBuffer.append(getAttribute(XML_MSG_RETRY_COUNT, getStrCount()));
        if (checkMessageRetryTag()) {
            strBuffer.append(XML_TAG_SLASH);
            strBuffer.append(XML_TAG_END);
        }
        return strBuffer.toString();
    }

    private boolean checkMessageRetryTag() {
        if (StringUtils.isNotBlank(getStrMessageRetryID()) || StringUtils.isNotBlank(getStrMaxAttempts())
                || StringUtils.isNotBlank(getStrCount())) {
            return true;
        } else
            return false;
    }

}
