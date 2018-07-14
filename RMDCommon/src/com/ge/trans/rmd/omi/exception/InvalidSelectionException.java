/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   InvalidSelectionException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   InvalidSelectionException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class InvalidSelectionException extends AppException {

    private static final long serialVersionUID = -691658964590615425L;

    public InvalidSelectionException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public InvalidSelectionException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public InvalidSelectionException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public InvalidSelectionException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public InvalidSelectionException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public InvalidSelectionException(Throwable aRootCause) {
        super(aRootCause);
    }

    public InvalidSelectionException(String aMessageKey) {
        super(aMessageKey);
    }
}