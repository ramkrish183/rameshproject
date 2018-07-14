/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   Date.java
 *  Author      :   Patni Team
 *  Date        :   26-April-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(Year) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   Date.
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    April 26, 2010  Created
 */

package com.ge.trans.rmd.omi.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Date {

    private java.util.Date date = null;
    private Long millisecs = null;
    private Long epochSecs = null;
    public static final String DATE_FORMAT = "MM/dd/yy HH:mm:ss";

    /**
     * Instantiates a new date.
     * 
     * @param pDate
     *            the date
     */
    public Date(java.util.Date pDate) {
        date = pDate;

        if (date != null) {
            millisecs = date.getTime();
            epochSecs = millisecs / 1000;
        }
    }

    /**
     * Gets the formatted date.
     * 
     * @param pTimeZone
     *            the time zone
     * @return the formatted date
     */
    public String getFormattedDate(String pTimeZone) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(pTimeZone));
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        format.setCalendar(calendar);

        return format.format(date);
    }

    /**
     * Gets the gMT date.
     * 
     * @return the gMT date
     */
    public String getGMTDate() {
        return getFormattedDate("GMT");
    }

    /**
     * Gets the local date.
     * 
     * @return the local date
     */
    public String getLocalDate() {
        return getFormattedDate(TimeZone.getDefault().getID());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getGMTDate();
    }

    /**
     * Gets the date.
     * 
     * @return the date
     */
    public java.util.Date getDate() {
        return date;
    }

    /**
     * Gets the milli seconds.
     * 
     * @return the millisecs
     */
    public Long getMilliSeconds() {
        return millisecs;
    }

    /**
     * Gets the epoch seconds.
     * 
     * @return the epochSecs
     */
    public Long getEpochSeconds() {
        return epochSecs;
    }
}