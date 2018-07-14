/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   FileAlreadyExistsException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:  FileAlreadyExistsException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class FileAlreadyExistsException extends AppException {

    private static final long serialVersionUID = -320186644607417002L;

    public FileAlreadyExistsException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public FileAlreadyExistsException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public FileAlreadyExistsException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public FileAlreadyExistsException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public FileAlreadyExistsException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public FileAlreadyExistsException(Throwable aRootCause) {
        super(aRootCause);
    }

    public FileAlreadyExistsException(String aMessageKey) {
        super(aMessageKey);
    }
}