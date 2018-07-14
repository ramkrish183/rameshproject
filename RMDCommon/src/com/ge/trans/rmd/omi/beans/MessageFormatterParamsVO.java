/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   MessageFormatterParamsVO.java
 *  Author      :   Patni Team
 *  Date        :   25-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   This class is used to store all the constant variables in the entire application.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 25, 2010  Created
 */
package com.ge.trans.rmd.omi.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The Class MessageFormatterParamsVO.
 */
public class MessageFormatterParamsVO extends BaseVO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3094815144027147914L;
    // B2B Message Forwarding
    /** The obj message vo. */
    private MessageVO objMessageVO = null;
    // B2B Asset Config
    /** The lng asset obj id. */
    private Long lngAssetObjId = null;

    /** The msg trigger type. */
    private int msgTriggerType = -1;

    /** The str request no. */
    private String strRequestNo = null;

    /** The obj common message vo. */
    private CommonMessageVO objCommonMessageVO = null;

    /**
     * Gets the obj message vo.
     * 
     * @return the obj message vo
     */
    public MessageVO getObjMessageVO() {
        return objMessageVO;
    }

    /**
     * Sets the obj message vo.
     * 
     * @param objMessageVO
     *            the new obj message vo
     */
    public void setObjMessageVO(MessageVO objMessageVO) {
        this.objMessageVO = objMessageVO;
    }

    /**
     * Gets the msg trigger type.
     * 
     * @return the msg trigger type
     */
    public int getMsgTriggerType() {
        return msgTriggerType;
    }

    /**
     * Sets the msg trigger type.
     * 
     * @param msgTriggerType
     *            the new msg trigger type
     */
    public void setMsgTriggerType(int msgTriggerType) {
        this.msgTriggerType = msgTriggerType;
    }

    /**
     * Gets the obj common message vo.
     * 
     * @return the obj common message vo
     */
    public CommonMessageVO getObjCommonMessageVO() {
        return objCommonMessageVO;
    }

    /**
     * Sets the obj common message vo.
     * 
     * @param objCommonMessageVO
     *            the new obj common message vo
     */
    public void setObjCommonMessageVO(CommonMessageVO objCommonMessageVO) {
        this.objCommonMessageVO = objCommonMessageVO;
    }

    /**
     * Gets the str request no.
     * 
     * @return the str request no
     */
    public String getStrRequestNo() {
        return strRequestNo;
    }

    /**
     * Sets the str request no.
     * 
     * @param strRequestNo
     *            the new str request no
     */
    public void setStrRequestNo(String strRequestNo) {
        this.strRequestNo = strRequestNo;
    }

    /**
     * Gets the lng asset obj id.
     * 
     * @return the lng asset obj id
     */
    public Long getLngAssetObjId() {
        return lngAssetObjId;
    }

    /**
     * Sets the lng asset obj id.
     * 
     * @param lngAssetObjId
     *            the new lng asset obj id
     */
    public void setLngAssetObjId(Long lngAssetObjId) {
        this.lngAssetObjId = lngAssetObjId;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, getToStringStyleObject()).append("objMessageVO", objMessageVO)
                .append("msgTriggerType", msgTriggerType).append("strRequestNo", strRequestNo)
                .append("objCommonMessageVO", objCommonMessageVO).append("lngAssetObjId", lngAssetObjId).toString();
    }
}