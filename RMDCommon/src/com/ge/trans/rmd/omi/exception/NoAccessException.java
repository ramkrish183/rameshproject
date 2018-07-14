/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   NoAccessException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   NoAccessException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class NoAccessException extends AppException {

    private static final long serialVersionUID = 8137685809916756650L;

    public NoAccessException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public NoAccessException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public NoAccessException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public NoAccessException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public NoAccessException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public NoAccessException(Throwable aRootCause) {
        super(aRootCause);
    }

    public NoAccessException(String aMessageKey) {
        super(aMessageKey);
    }
}