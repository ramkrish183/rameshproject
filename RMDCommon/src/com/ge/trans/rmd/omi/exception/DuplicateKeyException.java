/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   DuplicateKeyException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   DuplicateKeyException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class DuplicateKeyException extends AppException {

    private static final long serialVersionUID = 6757168370184017199L;

    public DuplicateKeyException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public DuplicateKeyException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public DuplicateKeyException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public DuplicateKeyException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public DuplicateKeyException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public DuplicateKeyException(Throwable aRootCause) {
        super(aRootCause);
    }

    public DuplicateKeyException(String aMessageKey) {
        super(aMessageKey);
    }
}