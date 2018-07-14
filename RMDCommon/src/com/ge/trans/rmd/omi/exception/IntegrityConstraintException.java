/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   IntegrityConstraintException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   IntegrityConstraintException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class IntegrityConstraintException extends AppException {

    private static final long serialVersionUID = -8581334845558735661L;

    public IntegrityConstraintException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public IntegrityConstraintException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public IntegrityConstraintException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public IntegrityConstraintException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public IntegrityConstraintException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public IntegrityConstraintException(Throwable aRootCause) {
        super(aRootCause);
    }

    public IntegrityConstraintException(String aMessageKey) {
        super(aMessageKey);
    }
}