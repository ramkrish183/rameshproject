/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   MessageDetailsVO.java
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

import com.ge.trans.rmd.omi.beans.xml.XMLMessageHeaderVO;

public class MessageDetailsVO extends BaseVO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5265111795030709923L;

    private XMLMessageHeaderVO messageHeaderVO = null;

    private String messageID = null;

    private String desc = null;

    private String inOrOut = null;

    private String destinationAddress = null;

    private String destinationType = null;

    private boolean requiresAck = false;

    private String priority = null;

    private String appRetryAttempts = null;

    private String appRetryInterval = null;

    private String apiRetryAttempts = null;

    private String apiRetryInterval = null;

    private boolean isRequest = false;

    /**
     * @return the messageHeaderVO
     */
    public XMLMessageHeaderVO getMessageHeaderVO() {
        return messageHeaderVO;
    }

    /**
     * @param messageHeaderVO
     *            the messageHeaderVO to set
     */
    public void setMessageHeaderVO(XMLMessageHeaderVO messageHeaderVO) {
        this.messageHeaderVO = messageHeaderVO;
    }

    /**
     * @return the messageID
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * @param messageID
     *            the messageID to set
     */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc
     *            the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the inOrOut
     */
    public String getInOrOut() {
        return inOrOut;
    }

    /**
     * @param inOrOut
     *            the inOrOut to set
     */
    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    /**
     * @return the destinationAddress
     */
    public String getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * @param destinationAddress
     *            the destinationAddress to set
     */
    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * @return the destinationType
     */
    public String getDestinationType() {
        return destinationType;
    }

    /**
     * @param destinationType
     *            the destinationType to set
     */
    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    /**
     * @return the requiresAck
     */
    public boolean isRequiresAck() {
        return requiresAck;
    }

    /**
     * @param requiresAck
     *            the requiresAck to set
     */
    public void setRequiresAck(boolean requiresAck) {
        this.requiresAck = requiresAck;
    }

    /**
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * @return the appRetryAttempts
     */
    public String getAppRetryAttempts() {
        return appRetryAttempts;
    }

    /**
     * @param appRetryAttempts
     *            the appRetryAttempts to set
     */
    public void setAppRetryAttempts(String appRetryAttempts) {
        this.appRetryAttempts = appRetryAttempts;
    }

    /**
     * @return the appRetryInterval
     */
    public String getAppRetryInterval() {
        return appRetryInterval;
    }

    /**
     * @param appRetryInterval
     *            the appRetryInterval to set
     */
    public void setAppRetryInterval(String appRetryInterval) {
        this.appRetryInterval = appRetryInterval;
    }

    /**
     * @return the apiRetryAttempts
     */
    public String getApiRetryAttempts() {
        return apiRetryAttempts;
    }

    /**
     * @param apiRetryAttempts
     *            the apiRetryAttempts to set
     */
    public void setApiRetryAttempts(String apiRetryAttempts) {
        this.apiRetryAttempts = apiRetryAttempts;
    }

    /**
     * @return the apiRetryInterval
     */
    public String getApiRetryInterval() {
        return apiRetryInterval;
    }

    /**
     * @param apiRetryInterval
     *            the apiRetryInterval to set
     */
    public void setApiRetryInterval(String apiRetryInterval) {
        this.apiRetryInterval = apiRetryInterval;
    }

    /**
     * @return the isRequest
     */
    public boolean isRequest() {
        return isRequest;
    }

    /**
     * @param isRequest
     *            the isRequest to set
     */
    public void setRequest(boolean isRequest) {
        this.isRequest = isRequest;
    }

}
