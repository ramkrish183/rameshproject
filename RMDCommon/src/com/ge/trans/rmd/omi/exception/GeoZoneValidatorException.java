/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   GeoZoneValidatorException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   GeoZoneValidatorException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

public class GeoZoneValidatorException extends Exception {

    private static final long serialVersionUID = 1L;

    public GeoZoneValidatorException() {
        super();
    }

    /**
     * @param message
     */
    public GeoZoneValidatorException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GeoZoneValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public GeoZoneValidatorException(Throwable cause) {
        super(cause);
    }
}
