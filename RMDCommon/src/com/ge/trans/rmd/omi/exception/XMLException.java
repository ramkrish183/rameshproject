/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   XMLException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   XMLException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class XMLException extends AppException {

    private static final long serialVersionUID = -1223903867759252369L;

    public XMLException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public XMLException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public XMLException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public XMLException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public XMLException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public XMLException(Throwable aRootCause) {
        super(aRootCause);
    }

    public XMLException(String aMessageKey) {
        super(aMessageKey);
    }
}