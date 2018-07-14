/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AutoSchedulerVO.java
 *  Author      :   Patni Team
 *  Date        :   01-July-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AutoSchedulerVO.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    July 01, 2010  Created
 */
package com.ge.trans.rmd.omi.beans.xml;

import org.apache.commons.lang.StringUtils;

public class AutoSchedulerVO extends XMLFormattedObject {

    /**
     * 
     */
    private static final long serialVersionUID = -6189435198897280931L;

    public static final String XML_JOB = "job";
    public static final String XML_JOB_TIMESTAMP = "timestamp";
    public static final String XML_JOB_ASSET_ID = "asset-id";
    public static final String XML_JOB_FAULT_ID = "fault-id";
    public static final String XML_JOB_FAULT_CODE = "fault-code";
    public static final String XML_JOB_MSG_TYPE_ID = "msg-type-id";
    public static final String XML_JOB_CONTROLLER = "controller-src-id";

    private String strTimeStamp;
    private String strAssetId;
    private String strFaultCode;
    private String strFaultId;
    private String strMsgType;
    private String strControllerSourceId;

    /**
     * @return the strTimeStamp
     */
    public String getStrTimeStamp() {
        return strTimeStamp;
    }

    /**
     * @param strTimeStamp
     *            the strTimeStamp to set
     */
    public void setStrTimeStamp(String strTimeStamp) {
        this.strTimeStamp = strTimeStamp;
    }

    /**
     * @return the strAssetId
     */
    public String getStrAssetId() {
        return strAssetId;
    }

    /**
     * @return the strFaultCode
     */
    public String getStrFaultCode() {
        return strFaultCode;
    }

    /**
     * @return the strFaultId
     */
    public String getStrFaultId() {
        return strFaultId;
    }

    /**
     * @return the strMsgType
     */
    public String getStrMsgType() {
        return strMsgType;
    }

    /**
     * @return the strControllerSourceId
     */
    public String getStrControllerSourceId() {
        return strControllerSourceId;
    }

    /**
     * @param strAssetId
     *            the strAssetId to set
     */
    public void setStrAssetId(String strAssetId) {
        this.strAssetId = strAssetId;
    }

    /**
     * @param strFaultCode
     *            the strFaultCode to set
     */
    public void setStrFaultCode(String strFaultCode) {
        this.strFaultCode = strFaultCode;
    }

    /**
     * @param strFaultId
     *            the strFaultId to set
     */
    public void setStrFaultId(String strFaultId) {
        this.strFaultId = strFaultId;
    }

    /**
     * @param strMsgType
     *            the strMsgType to set
     */
    public void setStrMsgType(String strMsgType) {
        this.strMsgType = strMsgType;
    }

    /**
     * @param strControllerSourceId
     *            the strControllerSourceId to set
     */
    public void setStrControllerSourceId(String strControllerSourceId) {
        this.strControllerSourceId = strControllerSourceId;
    }

    @Override
    public String toXML() {
        StringBuilder sbXML = new StringBuilder();
        sbXML.append(XML_TAG_START);
        sbXML.append(XML_JOB);
        if (StringUtils.isNotBlank(this.strTimeStamp))
            sbXML.append(getAttribute(XML_JOB_TIMESTAMP, this.strTimeStamp));
        if (StringUtils.isNotBlank(this.strAssetId))
            sbXML.append(getAttribute(XML_JOB_ASSET_ID, this.strAssetId));
        if (StringUtils.isNotBlank(this.strFaultId))
            sbXML.append(getAttribute(XML_JOB_FAULT_ID, this.strFaultId));
        if (StringUtils.isNotBlank(this.strFaultCode))
            sbXML.append(getAttribute(XML_JOB_FAULT_CODE, this.strFaultCode));
        if (StringUtils.isNotBlank(this.strMsgType))
            sbXML.append(getAttribute(XML_JOB_MSG_TYPE_ID, this.strMsgType));
        if (StringUtils.isNotBlank(this.strControllerSourceId))
            sbXML.append(getAttribute(XML_JOB_CONTROLLER, this.strControllerSourceId));
        sbXML.append(XML_TAG_SLASH);
        sbXML.append(XML_TAG_END);
        return sbXML.toString();
    }
}
