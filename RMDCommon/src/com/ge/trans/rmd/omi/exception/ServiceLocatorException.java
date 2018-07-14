/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   ServiceLocatorException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   ServiceLocatorException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

/**
 * A generic Checked Exception class which will be used by both DAO layer and
 * Business Layer. Runtime exceptions will not be handled by Application.
 * web.xml will be configured to forward to error jsp in case of Runtime
 * exception
 */
public class ServiceLocatorException extends AppException {

    private static final long serialVersionUID = 6513724124058318903L;

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
    public ServiceLocatorException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    /**
     * Constructor using Error type and message key
     * 
     * @param anErrorType
     * @param aMessageKey
     */
    public ServiceLocatorException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    /**
     * Constructor using errorType and error code
     * 
     * @param anErrorType
     * @param anErrorCode
     * @param aMessageKey
     */
    public ServiceLocatorException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    /**
     * Constructor using error type, error code and root cause
     * 
     * @param anErrorType
     * @param anErrorCode
     * @param aMessageKey
     * @param aRootCause
     */
    public ServiceLocatorException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    /**
     * Constructor using Error code, message key and root cause exception
     * 
     * @param anErrorCode
     * @param aMessageKey
     * @param aRootCause
     */
    public ServiceLocatorException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    /**
     * Constructor to store exception information wrapped by the base Exception
     * class
     * 
     * @param aRootCause
     */
    public ServiceLocatorException(Throwable aRootCause) {
        super(aRootCause);
    }

    /**
     * Constructor takes the key of the message to be displayed from the
     * ApplicationResources file as parameter
     * 
     * @param aMessageKey
     */
    public ServiceLocatorException(String aMessageKey) {
        super(aMessageKey);
    }
}