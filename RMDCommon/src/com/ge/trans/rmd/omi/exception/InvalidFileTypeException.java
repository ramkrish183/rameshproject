/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   InvalidFileTypeException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   InvalidFileTypeException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class InvalidFileTypeException extends AppException {

    private static final long serialVersionUID = 565645999462315290L;

    public InvalidFileTypeException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public InvalidFileTypeException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public InvalidFileTypeException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public InvalidFileTypeException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public InvalidFileTypeException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public InvalidFileTypeException(Throwable aRootCause) {
        super(aRootCause);
    }

    public InvalidFileTypeException(String aMessageKey) {
        super(aMessageKey);
    }
}