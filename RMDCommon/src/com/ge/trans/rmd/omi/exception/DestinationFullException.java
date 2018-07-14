/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   DestinationFullException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   DataException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.exception;

public class DestinationFullException extends AppException {
    /**
     * 
     */
    private static final long serialVersionUID = -1741302012564893281L;

    public DestinationFullException(String aMessageKey, Object[] aMessageArgs, Throwable aRootCause) {
        super(aMessageKey, aMessageArgs, aRootCause);
    }

    public DestinationFullException(int anErrorType, String aMessageKey) {
        super(anErrorType, aMessageKey);
    }

    public DestinationFullException(int anErrorType, int anErrorCode, String aMessageKey) {
        super(anErrorType, anErrorCode, aMessageKey);
    }

    public DestinationFullException(int anErrorType, int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorType, anErrorCode, aMessageKey, aRootCause);
    }

    public DestinationFullException(int anErrorCode, String aMessageKey, Throwable aRootCause) {
        super(anErrorCode, aMessageKey, aRootCause);
    }

    public DestinationFullException(Throwable aRootCause) {
        super(aRootCause);
    }

    public DestinationFullException(String aMessageKey) {
        super(aMessageKey);
    }

}
