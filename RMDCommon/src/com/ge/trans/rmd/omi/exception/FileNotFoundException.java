/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FileNotFoundException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   FileNotFoundException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class FileNotFoundException extends AppException {

    private static final long serialVersionUID = 981600322248103227L;

    public FileNotFoundException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public FileNotFoundException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public FileNotFoundException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public FileNotFoundException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public FileNotFoundException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public FileNotFoundException(Throwable aRootCause) {
        super(aRootCause);
    }

    public FileNotFoundException(String aMessageKey) {
        super(aMessageKey);
    }
}