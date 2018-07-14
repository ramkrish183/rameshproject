/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   ResourceNotFoundException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   ResourceNotFoundException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class ResourceNotFoundException extends AppException {

    private static final long serialVersionUID = -914905311458230209L;

    public ResourceNotFoundException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public ResourceNotFoundException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public ResourceNotFoundException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public ResourceNotFoundException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public ResourceNotFoundException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public ResourceNotFoundException(Throwable aRootCause) {
        super(aRootCause);
    }

    public ResourceNotFoundException(String aMessageKey) {
        super(aMessageKey);
    }
}