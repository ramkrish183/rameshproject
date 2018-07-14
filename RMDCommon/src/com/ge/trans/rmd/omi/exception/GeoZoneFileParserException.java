/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   GeoZoneFileParserException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   GeoZoneFileParserException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */
package com.ge.trans.rmd.omi.exception;

public class GeoZoneFileParserException extends Exception {

    private static final long serialVersionUID = 1L;

    public GeoZoneFileParserException() {
        super();
    }

    /**
     * @param message
     */
    public GeoZoneFileParserException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GeoZoneFileParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public GeoZoneFileParserException(Throwable cause) {
        super(cause);
    }
}
