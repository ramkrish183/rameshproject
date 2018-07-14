/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   GeoZoneTemplateParseException.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   GeoZoneTemplateParseException
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.exception;

public class GeoZoneTemplateParseException extends Exception {

    private static final long serialVersionUID = 1L;

    public GeoZoneTemplateParseException() {
        super();
    }

    /**
     * @param message
     */
    public GeoZoneTemplateParseException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public GeoZoneTemplateParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public GeoZoneTemplateParseException(Throwable cause) {
        super(cause);
    }
}
