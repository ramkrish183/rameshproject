/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   LoginFailedException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   LoginFailedException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class LoginFailedException extends AppException {

    private static final long serialVersionUID = -8031385102708644434L;

    public LoginFailedException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public LoginFailedException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public LoginFailedException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public LoginFailedException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public LoginFailedException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public LoginFailedException(Throwable aRootCause) {
        super(aRootCause);
    }

    public LoginFailedException(String aMessageKey) {
        super(aMessageKey);
    }
}