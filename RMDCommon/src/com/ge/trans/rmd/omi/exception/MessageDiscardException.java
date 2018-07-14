/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   MessageDiscardException.java
 *  Author      :   Patni Team
 *  Date        :   10-July-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   Exception Class for message discard.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    July 10, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

import com.ge.trans.rmd.omi.exception.AppException;

public class MessageDiscardException extends AppException {

    private static final long serialVersionUID = -76726236392203356L;

    public MessageDiscardException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public MessageDiscardException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public MessageDiscardException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public MessageDiscardException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public MessageDiscardException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public MessageDiscardException(Throwable aRootCause) {
        super(aRootCause);
    }

    public MessageDiscardException(String aMessageKey) {
        super(aMessageKey);
    }

    public MessageDiscardException(String aStrErrorType, String aStrMessage, Object[] aMessageArgs,
            Throwable aRootCause) {
        super(aStrErrorType, aStrMessage, aMessageArgs, aRootCause);
    }

    public MessageDiscardException(String anstrErrorType, String aMessageKey) {
        super(anstrErrorType, aMessageKey);

    }
}