/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   StatusMessageVO.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   StatusMessageVO
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */

package com.ge.trans.rmd.omi.beans.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ge.trans.mcs.codec.exception.CodecException;
import com.ge.trans.rmd.omi.exception.MessagingException;

public class StatusMessageVO extends XMLFormattedObject {

    private static final long serialVersionUID = 17645764587L;

    private String strStatusType;
    private String strTimeStamp;
    private List<ComponentStatusVO> lstComponentTag;
    private String strHeaderID;
    private String strHdrMMMM;
    private String strHdrFRV;
    private String strHdrSource;
    private String strStackTrace;
    private String strSource;
    private String strHeaderMsgSize;
    private String strHdrInOutType;
    private String strHdrTimeStamp;

    /** XML constants for Status message declaration starts */
    public static final String STATUS_HDR = "status";
    public static final String STATUS_HDR_ATTRIBUTE_TYPE = "type";
    public static final String STATUS_COMPONENT_TAG = "component";
    public static final String STATUS_COMPONENT_ATTRIBUTE_NAME = "name";
    public static final String STATUS_COMPONENT_ATTRIBUTE_STATUS = "status";
    public static final String STATUS_MSG_HDR_ATTRIBUTE_ID = "id";
    public static final String STATUS_MSG_HDR_ATTRIBUTE_MMMM = "MMMM";
    public static final String STATUS_MSG_HDR_ATTRIBUTE_FRV = "FRV";
    public static final String MESSAGE_HDR_TAG = "message-header";
    public static final String ATTRIBUTE_TIMESTAMP = "timestamp";
    public static final String ATTRIBUTE_SIZE = "size";
    public static final String ATTRIBUTE_INOUT = "in-out";
    public static final String ATTRIBUTE_SOURCE = "source";
    public static final String XML_DATE_FORMAT = "yyyyMMddHHmmssSSS";

    /** XML constants for Status message declaration ends */

    @Override
    public String toXML() {

        StringBuilder sbXML = null;

        try {
            sbXML = new StringBuilder();
            sbXML.append(XML_TAG_START);
            sbXML.append(STATUS_HDR);
            sbXML.append(this.getAttribute(STATUS_HDR_ATTRIBUTE_TYPE,
                    (null == this.strStatusType) ? "" : this.strStatusType));
            sbXML.append(this.getAttribute(ATTRIBUTE_SOURCE, (null == this.strSource) ? "" : this.strSource));
            sbXML.append(this.getAttribute(ATTRIBUTE_TIMESTAMP, getCurrentTimeStamp(XML_DATE_FORMAT)));
            sbXML.append(XML_TAG_END);

            sbXML.append(XML_TAG_START);
            sbXML.append(STATUS_COMPONENT_TAG);

            ComponentStatusVO componentStatusVO = lstComponentTag.get(0);

            sbXML.append(this.getAttribute(STATUS_COMPONENT_ATTRIBUTE_NAME,
                    (null == componentStatusVO.getStrName()) ? "" : componentStatusVO.getStrName()));
            sbXML.append(this.getAttribute(STATUS_HDR,
                    (null == componentStatusVO.getStrStatus()) ? "" : componentStatusVO.getStrStatus()));
            sbXML.append(this.getAttribute(ATTRIBUTE_TIMESTAMP,
                    (null == componentStatusVO.getStrTimeStamp()) ? "" : componentStatusVO.getStrTimeStamp()));
            sbXML.append(XML_TAG_SLASH);
            sbXML.append(XML_TAG_END);

            sbXML.append(XML_TAG_START);
            sbXML.append(MESSAGE_HDR_TAG);
            sbXML.append(
                    this.getAttribute(STATUS_MSG_HDR_ATTRIBUTE_ID, (null == this.strHeaderID) ? "" : this.strHeaderID));
            sbXML.append(
                    this.getAttribute(ATTRIBUTE_TIMESTAMP, (null == this.strHdrTimeStamp) ? "" : this.strHdrTimeStamp));
            sbXML.append(
                    this.getAttribute(ATTRIBUTE_SIZE, (null == this.strHeaderMsgSize) ? "" : this.strHeaderMsgSize));
            sbXML.append(
                    this.getAttribute(STATUS_MSG_HDR_ATTRIBUTE_MMMM, (null == this.strHdrMMMM) ? "" : this.strHdrMMMM));
            sbXML.append(
                    this.getAttribute(STATUS_MSG_HDR_ATTRIBUTE_FRV, (null == this.strHdrFRV) ? "" : this.strHdrFRV));
            sbXML.append(
                    this.getAttribute(ATTRIBUTE_INOUT, (null == this.strHdrInOutType) ? "" : this.strHdrInOutType));
            sbXML.append(this.getAttribute(ATTRIBUTE_SOURCE, (null == this.strHdrSource) ? "" : this.strHdrSource));
            sbXML.append(XML_TAG_SLASH);
            sbXML.append(XML_TAG_END);

            sbXML.append(XML_TAG_START);
            sbXML.append(XML_TAG_SLASH);
            sbXML.append(STATUS_HDR);
            sbXML.append(XML_TAG_END);

        } catch (Exception objExp) {
            
        }

        return sbXML.toString();
    }

    /**
     * @return the strStatusType
     */
    public String getStrStatusType() {
        return strStatusType;
    }

    /**
     * @param strStatusType
     *            the strStatusType to set
     */
    public void setStrStatusType(String strStatusType) {
        this.strStatusType = strStatusType;
    }

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
     * @return the lstComponentTag
     */
    public List<ComponentStatusVO> getLstComponentTag() {
        return lstComponentTag;
    }

    /**
     * @param lstComponentTag
     *            the lstComponentTag to set
     */
    public void setLstComponentTag(List<ComponentStatusVO> lstComponentTag) {
        this.lstComponentTag = lstComponentTag;
    }

    /**
     * @return the strHeaderID
     */
    public String getStrHeaderID() {
        return strHeaderID;
    }

    /**
     * @param strHeaderID
     *            the strHeaderID to set
     */
    public void setStrHeaderID(String strHeaderID) {
        this.strHeaderID = strHeaderID;
    }

    /**
     * @return the strHdrMMMM
     */
    public String getStrHdrMMMM() {
        return strHdrMMMM;
    }

    /**
     * @param strHdrMMMM
     *            the strHdrMMMM to set
     */
    public void setStrHdrMMMM(String strHdrMMMM) {
        this.strHdrMMMM = strHdrMMMM;
    }

    /**
     * @return the strHdrFRV
     */
    public String getStrHdrFRV() {
        return strHdrFRV;
    }

    /**
     * @param strHdrFRV
     *            the strHdrFRV to set
     */
    public void setStrHdrFRV(String strHdrFRV) {
        this.strHdrFRV = strHdrFRV;
    }

    /**
     * @return the strHdrSource
     */
    public String getStrHdrSource() {
        return strHdrSource;
    }

    /**
     * @param strHdrSource
     *            the strHdrSource to set
     */
    public void setStrHdrSource(String strHdrSource) {
        this.strHdrSource = strHdrSource;
    }

    /**
     * @return the strStackTraec
     */
    public String getStrStackTrace() {
        return strStackTrace;
    }

    /**
     * @param strStackTraec
     *            the strStackTraec to set
     */
    public void setStrStackTrace(String strStackTraec) {
        this.strStackTrace = strStackTraec;
    }

    /**
     * @return the strSource
     */
    public String getStrSource() {
        return strSource;
    }

    /**
     * @param strSource
     *            the strSource to set
     */
    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }

    /**
     * @return the strHeaderMsgSize
     */
    public String getStrHeaderMsgSize() {
        return strHeaderMsgSize;
    }

    /**
     * @param strHeaderMsgSize
     *            the strHeaderMsgSize to set
     */
    public void setStrHeaderMsgSize(String strHeaderMsgSize) {
        this.strHeaderMsgSize = strHeaderMsgSize;
    }

    /**
     * @return the strHdrInOutType
     */
    public String getStrHdrInOutType() {
        return strHdrInOutType;
    }

    /**
     * @param strHdrInOutType
     *            the strHdrInOutType to set
     */
    public void setStrHdrInOutType(String strHdrInOutType) {
        this.strHdrInOutType = strHdrInOutType;
    }

    /**
     * @return the strHdrTimeStamp
     */
    public String getStrHdrTimeStamp() {
        return strHdrTimeStamp;
    }

    /**
     * @param strHdrTimeStamp
     *            the strHdrTimeStamp to set
     */
    public void setStrHdrTimeStamp(String strHdrTimeStamp) {
        this.strHdrTimeStamp = strHdrTimeStamp;
    }

    /**
     * This method will get current timestamp.
     * 
     * @param String
     *            formaterPatten
     * @return String
     * @throws MessagingException
     * @throws CodecException
     */

    public static String getCurrentTimeStamp(String formaterPatten) throws MessagingException {
        String strTimeStamp = null;
        try {
            SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
            df.applyPattern(formaterPatten);
            strTimeStamp = df.format(new Date());

        } catch (Exception e) {
            throw new MessagingException("Exception Occured in StatusMessageVO : getCurrentTimeStamp() method", null,
                    e);
        }
        return strTimeStamp;
    }

}
