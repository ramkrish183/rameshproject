/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AppException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AppException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

/**
 * A generic Checked Exception class which will be used by both DAO layer and
 * Business Layer. Runtime exceptions will not be handled by Application.
 * web.xml will be configured to forward to error jsp in case of Runtime
 * exception
 */
public class AppException extends Exception {

    private static final long serialVersionUID = 8935423541994771926L;
    protected int errorCode;
    protected int errorType;
    protected String messageKey = null;
    protected String strErrorCode = null;

    /**
     * List of arguments that can be passed to the message
     */
    protected Object messageArgs[] = null;
    protected Throwable rootCause = null;

    /**
     * Constructor using Message keys, message args and root cause. Message args
     * can be used to send an object along with the exception. e.g. When
     * supplier match is done through D&B while creating a new supplier, if
     * match is found exception has to be raised along with sending the list of
     * matching suppliers found.
     * 
     * @param aMessageKey
     * @param aMessageArgs
     * @param aRootCause
     */
    public AppException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aRootCause);
        messageKey = aMessageKey;
        messageArgs = aMessageArgs;
        rootCause = aRootCause;
    }

    /**
     * Constructor using Error type and message key
     * 
     * @param anErrorType
     * @param aMessageKey
     */
    public AppException(int anErrorType, String aMessageKey) {
        super(aMessageKey);
        errorType = anErrorType;
        messageKey = aMessageKey;

    }

    /**
     * Constructor using errorType and error code
     * 
     * @param anErrorType
     * @param anErrorCode
     * @param aMessageKey
     */
    public AppException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(aMessageKey);
        errorType = anErrorType;
        errorCode = anErrorCode;
        messageKey = aMessageKey;
    }

    /**
     * Constructor using error type, error code and root cause
     * 
     * @param anErrorType
     * @param anErrorCode
     * @param aMessageKey
     * @param aRootCause
     */
    public AppException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(aMessageKey, aRootCause);
        errorType = anErrorType;
        errorCode = anErrorCode;
        messageKey = aMessageKey;
        rootCause = aRootCause;
    }

    /**
     * Constructor using Error code, message key and root cause exception
     * 
     * @param anErrorCode
     * @param aMessageKey
     * @param aRootCause
     */
    public AppException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(aMessageKey, aRootCause);
        errorCode = anErrorCode;
        messageKey = aMessageKey;
        rootCause = aRootCause;

    }

    /**
     * Constructor to store exception information wrapped by the base Exception
     * class
     * 
     * @param aRootCause
     */
    public AppException(Throwable aRootCause) {
        super(aRootCause);
        rootCause = aRootCause;
    }

    /**
     * Constructor takes the key of the message to be displayed from the
     * ApplicationResources file as parameter
     * 
     * @param aMessageKey
     */
    public AppException(String aMessageKey) {
        super(aMessageKey);
        messageKey = aMessageKey;
    }

    /**
     * Method to get Error Code
     * 
     * @return java.lang.String
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Method to set Error code
     * 
     * @param errorCode
     */
    public void setErrorCode(int anErrorCode) {
        errorCode = anErrorCode;

    }

    /**
     * Method to get type of Error
     * 
     * @return int
     */
    public int getErrorType() {
        return errorType;
    }

    /**
     * Method to set type of error
     * 
     * @param errorType
     */
    public void setErrorType(int anErrorType) {
        errorType = anErrorType;
    }

    /**
     * Method to get message key.
     * 
     * @return java.lang.String
     */
    public String getMessageKey() {
        return messageKey;
    }

    /**
     * Setter method for message key
     * 
     * @param messageKey
     */
    public void setMessageKey(String aMessageKey) {
        messageKey = aMessageKey;
    }

    /**
     * Method to get the root cause exception
     * 
     * @return java.lang.Throwable
     */
    public Throwable getRootCause() {
        return rootCause;
    }

    /**
     * Method to set root cause exception
     * 
     * @param rootCause
     */
    public void setRootCause(Throwable anRootCause) {
        rootCause = anRootCause;
    }

    /**
     * Method to get message object array
     * 
     * @return java.lang.Object[]
     */
    public Object[] getMessageArgs() {
        return messageArgs;
    }

    /**
     * Method to set message object array
     * 
     * @param messageArgs
     */
    public void setMessageArgs(Object[] aMessageArgs) {
        messageArgs = aMessageArgs;
    }

    public AppException(String anstrErrorType, String aMessageKey) {
        super(aMessageKey);
        this.strErrorCode = anstrErrorType;
        messageKey = aMessageKey;

    }

    public AppException(String aStrErrorType, String aStrMessage, Object[] aMessageArgs, Throwable aRootCause) {
        super(aStrMessage, aRootCause);
        messageKey = aStrMessage;
        messageArgs = aMessageArgs;
        rootCause = aRootCause;
        this.strErrorCode = aStrErrorType;

    }

    /**
     * @return the strErrorCode
     */
    public String getStrErrorCode() {
        return strErrorCode;
    }

    /**
     * @param strErrorCode
     *            the strErrorCode to set
     */
    public void setStrErrorCode(String strErrorCode) {
        this.strErrorCode = strErrorCode;
    }
}
