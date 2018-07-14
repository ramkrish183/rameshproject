/**
 * ============================================================
 * File : RMDCommonUtility.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.utilities
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 3, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.tools.keys.validator.AssetKeyValidator;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 3, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public final class RMDCommonUtility {
    public static final RMDLogger LOG = RMDLogger.getLogger(RMDCommonUtility.class);
    public static final Properties rmdProperties = new Properties();

    private RMDCommonUtility() {
    }

    /**
     * @Author:
     * @param cal
     * @return
     * @Description: converts the given calendar to GMT or US/Eastern based on
     *               the value returnd from property file
     */

    public static void setGMTTime(Calendar cal) {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        gmtCal.setTimeInMillis(cal.getTimeInMillis());
        gmtCal.setTimeInMillis(gmtCal.getTimeInMillis() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));
        cal.setTimeInMillis(gmtCal.getTimeInMillis());
    }

    /**
     * @Author:
     * @param cal
     * @return
     * @Description: converts the given calendar to GMT or US/Eastern based on
     *               the value returnd from property file
     */
    public static void setZoneOffsetTime(Calendar cal, String timeZone) {

        Calendar newCal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        newCal.setTimeInMillis(cal.getTimeInMillis());
        if (newCal.get(Calendar.ZONE_OFFSET) < 0 || newCal.get(Calendar.ZONE_OFFSET) > 0) {
            newCal.setTimeInMillis(
                    newCal.getTimeInMillis() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)
                            + (-1 * newCal.get(Calendar.ZONE_OFFSET) + -1 * newCal.get(Calendar.DST_OFFSET)));
        } else if (newCal.get(Calendar.ZONE_OFFSET) == 0) {
            newCal.setTimeInMillis(
                    newCal.getTimeInMillis() + cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET));
        }

        cal.setTimeInMillis(newCal.getTimeInMillis());
    }

    /**
     * @Author:
     * @param input
     * @param pattern
     * @return
     * @Description:
     */
    public static boolean checkPattern(final String input, final String pattern) throws Exception {
        boolean isSame = false;
        if (input != null && !RMDCommonConstants.EMPTY_STRING.equals(input.trim()) && pattern != null
                && !RMDCommonConstants.EMPTY_STRING.equals(pattern.trim())) {
            isSame = Pattern.compile(pattern).matcher(input).matches();
        }
        return isSame;
    }

    /**
     * @Author:
     * @param input
     * @param pattern
     * @return
     * @Description: This method will do the left Padding to the String
     */
    public static String leftPad(String str, int size, char padChar) {
        StringBuilder padded = new StringBuilder(RMDCommonConstants.EMPTY_STRING);
        while (padded.length() < (size - str.length())) {
            padded.append(padChar);
        }
        padded.append(str);
        return padded.toString();
    }

    /**
     * @Author:
     * @param input
     * @param pattern
     * @return
     * @Description: This method will do the right Padding to the String
     */
    public String rightPad(String str, int size, char padChar) {
        StringBuilder padded = new StringBuilder(str);
        while (padded.length() < size) {
            padded.append(padChar);
        }
        return padded.toString();
    }

    /**
     * @Author:
     * @param :none
     * @return timeZone
     * @Description: this Method will return the TimeZone(Short form as EST or
     *               EDT etc) considering DST as well
     */
    public static String getServerTimeZone() {
        String timeZone = null;
        final TimeZone timeZonal = TimeZone.getDefault();
        final Date today = new Date();
        timeZone = timeZonal.getDisplayName(timeZonal.inDaylightTime(today), 0);
        return timeZone;
    }

    /**
     * @Author:
     * @param strValue
     * @return
     * @Description:
     */
    public static boolean isNullOrEmpty(final String strValue) {
        boolean returnString = false;
        if (strValue == null) {
            returnString = true;
        } else {
            final String strValues = strValue.trim();
            if (strValues.length() == 0) {
                returnString = true;
            } else if (strValues.equalsIgnoreCase("null")) {
                returnString = true;
            } else {
                returnString = false;
            }
        }
        return returnString;
    }

    /**
     * @Author:
     * @param strnumber
     * @return
     * @Description:
     */
    public static float getFloatValue(final String strnumber) {
        float number = -1.0F;
        try {
            final String strNumber = strnumber.trim();
            if (null != strNumber && strNumber.length() > 0) {
                number = Float.parseFloat(strNumber);
            }
        } catch (NumberFormatException e) {
            number = -1.0F;
        }
        return number;
    }

    /**
     * @Author:
     * @param strData
     * @return
     * @Description: This method will check whether the passed string is null or
     *               not
     */
    public static boolean isNull(final String strData) {
        boolean returnString = true;
        String strDatas = RMDCommonConstants.EMPTY_STRING;
        if (strData != null) {
            strDatas = strData.trim();
        }
        if ((strDatas != null) && (strDatas.length() > 0)
                && (!strDatas.trim().equalsIgnoreCase(RMDCommonConstants.NULL_STRING))) {
            returnString = false;
        }
        return returnString;
    }

    /**
     * @Author:
     * @param list
     * @return
     * @Description: This method will check whether the passed collection is not
     *               null & not empty
     */
    public static boolean isCollectionNotEmpty(final Collection list) {
        return (list != null && !list.isEmpty());
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if it is valid number Returns false if it is an
     *         invalid number
     * @Description: This method will check whether passed value is a valid
     *               number or not
     */
    public static boolean isNumeric(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            inputData = inputData.replaceAll(RMDCommonConstants.BLANK_SPACE, RMDCommonConstants.EMPTY_STRING);
            final Pattern pattern = Pattern.compile(RMDCommonConstants.NUMERIC_PATTERN, Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(inputData);
            if (matcher.find()) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * This method will check the whether passed value is a valid date or not.
     * 
     * @param strDate
     * @return Returns true if it is a valid date Returns false if invalid date
     */
    public static boolean isDate(String strDate) {
        boolean returnValue = true;
        String returnString = strDate;
        if (strDate == null) {
            returnValue = false;
        }
        if (returnString != null) {
            returnString = returnString.trim();
        }
        if (returnString != null && returnString.length() > 10) {
            returnValue = false;
        }
        try {
            final DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
            if (strDate != null) {
                formatter.parse(returnString);
            }
        } catch (ParseException e) {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * This method converts a java.util.Date to java.sql.Date.
     * 
     * @param dateValue
     *            - The java.util.Date object.
     * @return - java.sql.Date object
     */
    public static java.sql.Date convertToSqlDate(final java.util.Date dateValue) {
        return new java.sql.Date(dateValue.getTime());
    }

    /**
     * Converts a String to a Date using the specified pattern.
     * 
     * @param dateString
     *            the <code>String</code> to convert.
     * @param dateFormatPattern
     *            the format pattern.
     * @return the corresponding <code>Date</code>.
     * @throws:None
     * @see java.text.SimpleDateFormat date format pattern syntax
     */
    public static Date stringToGMTDate(final String dateString, final String dateFormatPattern) throws Exception {
        Date date = null;
        final SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(dateFormatPattern);
        // Specify strict parsing (i.e. inputs must match object's format)
        formatter.setLenient(false);
        formatter.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        date = formatter.parse(dateString);
        return date;
    }

    public static GregorianCalendar getGMTGregorianCalendar(Date date) throws Exception {
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        gcal.setTimeInMillis(date.getTime());
        return gcal;
    }

    public static Date stringToDate(final String dateString, final String dateFormatPattern) throws Exception {
        Date date = null;
        final SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(dateFormatPattern);
        // Specify strict parsing (i.e. inputs must match object's format)
        formatter.setLenient(false);
        date = formatter.parse(dateString);
        return date;
    }

    /**
     * @param data
     * @return
     */
    public static String checkData(final String data) {
        String inputData = data;
        if (inputData == null || RMDCommonConstants.EMPTY_STRING.equals(inputData)
                || RMDCommonConstants.EMPTY_STRING.equals(inputData.trim())) {
            inputData = "&nbsp;";
        } else {
            inputData = inputData.trim();
        }
        return inputData;
    }

    /**
     * To find difference between two dates.
     * 
     * @param calDateVal1
     *            the <code>Calendar</code> to convert.
     * @param calDateVal2
     *            the <code>Calendar</code> to convert.
     * @return the corresponding <code>Double</code>. public static long
     *         DateDiff( Calendar calDateVal1, Calendar calDateVal2) { long
     *         diffVal = calDateVal1.getTimeInMillis() -
     *         calDateVal2.getTimeInMillis(); return diffVal; }
     */
    public static int dateTimeDiff(final String sdate1, final String sdate2, final String fmt, final TimeZone timeZone)
            throws Exception {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(fmt, Locale.getDefault());
        Date date1 = null;
        Date date2 = null;
        int minDiff = 0;
        date1 = dateFormat.parse(sdate1);
        date2 = dateFormat.parse(sdate2);
        Calendar cal1 = null;
        Calendar cal2 = null;
        if (timeZone == null) {
            cal1 = Calendar.getInstance();
            cal2 = Calendar.getInstance();
        } else {
            cal1 = Calendar.getInstance(timeZone);
            cal2 = Calendar.getInstance(timeZone);
        }
        // different date might have different offset
        cal1.setTime(date1);
        final long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
        cal2.setTime(date2);
        final long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
        final int min1 = (int) (ldate1 / 60000);
        final int min2 = (int) (ldate2 / 60000);
        minDiff = min2 - min1;
        return minDiff;
    }

    /**
     * @param dtCreateDate
     * @return @ Description: To calculate Age
     */
    public static long dayDiffInMin(Date dtCreateDate) throws Exception {
        long diff = 0;
        long diffMins = 0;
        SimpleDateFormat sdfDestination = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        diff = RMDCommonUtility.dateDiff(
                sdfDestination.format(RMDCommonUtility.getGMTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa))
                        .toString(),
                sdfDestination.format(dtCreateDate).toString(), RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        diffMins = (diff / (1000 * 60));
        return diffMins;
    }

    /**
     * @param sdate1
     * @param sdate2
     * @param fmt
     * @return The method to check whether the first date is greater than or
     *         equal to the second date passed to it
     */
    public static long dateDiff(final String sdate1, final String sdate2, final String fmt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt, Locale.getDefault());
        Date date1 = null;
        Date date2 = null;
        long dateDiff;
        try {
            if (isNullOrEmpty(sdate1) || isNullOrEmpty(sdate2)) {
                dateDiff = -1;
            } else {
                date1 = simpleDateFormat.parse(sdate1);
                date2 = simpleDateFormat.parse(sdate2);
                dateDiff = date1.getTime() - date2.getTime();
            }
        } catch (ParseException e) {
            dateDiff = -1;
        }
        return dateDiff;
    }

    public static long dayDiff(final String sdate1, final String sdate2, final String fmt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fmt, Locale.getDefault());
        Date date1 = null;
        Date date2 = null;
        long dateDiff;
        long date1time = 0;
        long date2time = 0;
        try {
            if (!isNullOrEmpty(sdate1)) {
                date1 = simpleDateFormat.parse(sdate1);
                date1time = date1.getTime();
            }
            if (!isNullOrEmpty(sdate2)) {
                date2 = simpleDateFormat.parse(sdate2);
                date2time = date2.getTime();
            }
            dateDiff = date1time - date2time;
            dateDiff = dateDiff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            dateDiff = -1;
        }
        return dateDiff;
    }

    /**
     * @Author:
     * @param date1
     * @param date2
     * @param fmt
     * @return
     * @Description:
     */
    public static long dayDiff(final Date date1, final Date date2) {
        long dateDiff;
        long date1time = 0;
        long date2time = 0;
        try {
            if (!checkNull(date1)) {
                date1time = date1.getTime();
            }
            if (!checkNull(date2)) {
                date2time = date2.getTime();
            }
            dateDiff = date1time - date2time;
            dateDiff = dateDiff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            dateDiff = -1;
            LOG.debug(e.getMessage(), e);
        }
        return dateDiff;
    }

    /**
     * @Author:
     * @param inputDate
     *            - input should be in Date
     * @param timeZone
     *            - Time Zone
     * @param component
     *            - which value have to add/substract. i.e Calendar.MONTH,
     *            Calendar.YEAR etc
     * @param value
     *            - the actaul value have to add /substract
     * @return Date
     * @Throws:None
     * @Description: This method add/substract a particular year or month or day
     *               or time from a date
     */
    public static Date substractOrAddFromDate(final Date inputDate, final TimeZone timeZone, final int component,
            final int value) {
        Calendar date = null;
        Date result = null;
        if (timeZone == null) {
            date = Calendar.getInstance();
        } else {
            date = Calendar.getInstance(timeZone);
        }
        if (inputDate != null) {
            date.setTime(inputDate);
            date.add(component, value);
            result = date.getTime();
        }
        return result;
    }

    /**
     * @Author:
     * @param dateString
     *            the <code>String</code> to convert.
     * @param dateFormatPattern
     * @return the corresponding <code>Date</code>.
     * @Throws:None
     * @Description: Converts a String to a Date using the specified pattern.
     */
    public static Date stringToDate(final String dateString, final String dateFormatPattern, final TimeZone timeZone)
            throws Exception {
        Date date = null;
        try {
            final SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.applyPattern(dateFormatPattern);
            // Specify strict parsing (i.e. inputs must match object's format)
            formatter.setLenient(false);
            date = formatter.parse(dateString);
            if (timeZone != null) {
                Calendar cal = Calendar.getInstance(timeZone);
                cal.setTime(date);
                date = cal.getTime();
            }
        } catch (Exception e) {
            LOG.error("Exception while parsing date >>> ", e);
        }
        return date;
    }

    /**
     * @Author:
     * @param input
     * @param formatString
     * @return String
     * @Throws:None
     * @Description: This method is to Format the Date.
     */
    public static String formatStringDate(final String input, final String formatString) {
        String inputData = input;
        DateFormat localFormat = new SimpleDateFormat(formatString, Locale.getDefault());
        Date newDate = null;
        String dateStr = RMDCommonConstants.EMPTY_STRING;
        try {
            if ((inputData != null) && !RMDCommonConstants.EMPTY_STRING.equals(inputData)) {
                if (RMDCommonConstants.DateConstants.yyMMddhhmmss.equals(formatString)) {
                    if (inputData.length() == 11) {
                        inputData = RMDCommonConstants.ZERO_STRING + inputData;
                    }
                    newDate = localFormat.parse(inputData);
                    localFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss,
                            Locale.getDefault());
                } else if (RMDCommonConstants.DateConstants.yyMMdd.equals(formatString)) {
                    if (inputData.length() == 5) {
                        inputData = RMDCommonConstants.ZERO_STRING + inputData;
                    }
                    newDate = localFormat.parse(inputData);
                    localFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyy, Locale.getDefault());
                } else if (RMDCommonConstants.DateConstants.EMMMddHHmmsszyyyy.equals(formatString)) {
                    newDate = localFormat.parse(inputData);
                    localFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss,
                            Locale.getDefault());
                } else if (RMDCommonConstants.DateConstants.MMddyyHHmm.equals(formatString)) {
                    newDate = localFormat.parse(inputData);
                    localFormat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss,
                            Locale.getDefault());
                } else {
                    newDate = new Date();
                }
                dateStr = localFormat.format(newDate);
            }
        } catch (ParseException e) {
            dateStr = RMDCommonConstants.EMPTY_STRING;
        } catch (Exception e) {
            dateStr = RMDCommonConstants.EMPTY_STRING;
            LOG.debug(e.getMessage(), e);
        }
        return dateStr;
    }

    /**
     * @Author:
     * @param s
     * @return String
     * @Throws:None
     * @Description:This method will remove the Unwanted space from the String.
     */
    public static String removeSpaces(final String input) {
        StringBuilder result = new StringBuilder(RMDCommonConstants.EMPTY_STRING);
        StringTokenizer strToken = new StringTokenizer(input, RMDCommonConstants.BLANK_SPACE, false);
        while (strToken.hasMoreElements()) {
            result.append(strToken.nextElement()).append(RMDCommonConstants.BLANK_SPACE);
        }
        strToken = new StringTokenizer(result.toString().trim(), RMDCommonConstants.SINGLE_QTE);
        result = new StringBuilder(RMDCommonConstants.EMPTY_STRING);
        final int totalCount = strToken.countTokens();
        int position = 1;
        while (strToken.hasMoreElements()) {
            if (totalCount == position) {
                result.append(strToken.nextElement());
            } else {
                result.append(strToken.nextElement()).append("''");
                position++;
            }
        }
        return result.toString().trim();
    }

    /**
     * @param input
     * @param str
     * @param str2
     * @return
     * @Description:This method will replace all the occurance of the str witn
     *                   str
     */
    public static String replaceAll(final String input, String str, String str2) {
        String output = RMDCommonConstants.EMPTY_STRING;
        if (!checkNull(input)) {
            output = input.replaceAll(str, str2);
        }
        return output.trim();
    }

    /**
     * @Author:
     * @return
     * @Description: Returns the current date (Example: 01/02/2009 13:29)
     */
    public static String currentDate() {
        int days = 0;
        int months = 0;
        int years = 0;
        int hour = 0;
        int min = 0;
        Calendar cal = Calendar.getInstance();
        days = cal.get(Calendar.DAY_OF_MONTH);
        months = cal.get(Calendar.MONTH);
        years = cal.get(Calendar.YEAR);
        months = months + 1;
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        String date = months + RMDCommonConstants.FOWARD_SLASH + days + RMDCommonConstants.FOWARD_SLASH + years
                + RMDCommonConstants.BLANK_SPACE + hour + RMDCommonConstants.FULL_COLON + min;
        return date;
    }

    /**
     * @Author:
     * @param StringValue
     * @return
     * @Description: Function to convert String value into int value.
     */
    public static int getIntValue(final String stringValue) {
        int intValue = 0;
        try {
            intValue = Integer.parseInt(stringValue);
        } catch (NumberFormatException exception) {
            intValue = 0;
        }
        return intValue;
    }

    /**
     * @Author:
     * @param StringValue
     * @return
     * @Description: Function to convert String value into long value.
     */
    public static long getLongValue(final String stringValue) {
        long longValue = 0L;
        try {
            longValue = Long.parseLong(stringValue);
        } catch (NumberFormatException exception) {
            longValue = 0L;
        }
        return longValue;
    }

    /**
     * @Author:
     * @param stringValue
     * @return
     * @Description: Function to convert String value into double value.
     */
    public static double getDoubleValue(final String stringValue) {
        double doubleValue = 0.0D;
        try {
            doubleValue = Double.parseDouble(stringValue);
        } catch (Exception e) {
            doubleValue = 0.0D;
            LOG.debug(e.getMessage(), e);
        }
        return doubleValue;
    }

    /**
     * @Author:
     * @return
     * @Description: This method gets the current Date in "dd MMMM yyyy" format.
     */
    public static String getCuttentDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMMM_DD_YYYY,
                Locale.getDefault());
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * @Author:
     * @param currentDate
     * @param timeZoneId
     * @return
     * @Description:
     */
    public static Date getDateInTimeZone(Date currentDate, String timeZoneId) {
        Calendar mbCal = new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
        mbCal.setTimeInMillis(currentDate.getTime());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
        return cal.getTime();
    }

    /**
     * @Author:
     * @return
     * @Description: This method gets the current Date in "dd MMMM yyyy" format.
     */
    public static Date getCuttentDate(Locale locale) throws Exception {
        Date resultDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        Date currentTime = new Date();
        DateFormat dfout = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss, locale);
        resultDate = dfout.parse(formatter.format(currentTime));
        return resultDate;
    }

    /**
     * @Author:
     * @param dateString
     *            a <code>String</code>.
     * @return <code>true</code> or <code>false</code>.
     * @Description: Determines if a <code>String</code> is has a valid 4-digit
     *               year as it's suffix.
     */
    public static boolean hasFourDigitYear(final String dateString) {
        return ((dateString.length() - dateString.lastIndexOf('/')) == 5);
    }

    /**
     * @Author:
     * @param dateValue
     *            - java.sql.Date object
     * @return The java.util.Date object.
     * @Description: This method converts a java.sql.Date to java.util.Date.
     */
    public static java.util.Date convertToUtilDate(java.sql.Date dateValue) {
        return new java.util.Date(dateValue.getTime());
    }

    /**
     * @Author:
     * @param input
     * @param delimitter
     * @return array
     * @Throws:None
     * @Description:This method splits the input String according to the
     *                   delimiter.
     */
    public static String[] splitString(final String input, final String delimitter) {
        String result[] = null;
        int index = 0;
        if (input != null && !RMDCommonConstants.EMPTY_STRING.equals(input.trim())) {
            StringTokenizer stringToken = new StringTokenizer(input, delimitter);
            int count = stringToken.countTokens();
            result = new String[count];
            while (stringToken.hasMoreTokens()) {
                result[index] = stringToken.nextToken().trim();
                index++;
            }
        }
        return result;
    }

    /**
     * @Author:
     * @param obj
     * @return boolean
     * @Throws:None
     * @Description:This method is to Check whether the object is null.
     */
    public static boolean checkNull(Object obj) {
        return (obj == null);
    }

    /**
     * @Author:
     * @param date
     * @return
     * @Description:
     */
    public static int getWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @Author:
     * @param date
     * @return
     * @Description:
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public static int currentYear() {
        int years = 0;
        Calendar cal = Calendar.getInstance();
        years = cal.get(Calendar.YEAR);
        return years;
    }

    /**
     * @Author:
     * @param amount
     * @return
     * @Description:
     */
    public static String formatCurrency(String amount) throws Exception {
        String returnValue = null;
        int noOfDecimal = 0;
        if (amount != null) {
            noOfDecimal = amount.length();
        }
        if (noOfDecimal > 0) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(noOfDecimal);
            numberFormat.setGroupingUsed(true);
            returnValue = "$ " + numberFormat.format(getDoubleValue(amount));
        } else {
            returnValue = "$ 0.00";
        }
        return returnValue;
    }

    /**
     * @param dateValue
     * @return
     */
    public static String formatDateConvertion(String dateValue) throws Exception {
        SimpleDateFormat input = new SimpleDateFormat(RMDCommonConstants.DateConstants.EMMMddHHmmsszyyyy);
        SimpleDateFormat output = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyy);
        String outputValue = RMDCommonConstants.EMPTY_STRING;
        if (dateValue != null && !RMDCommonConstants.EMPTY_STRING.equals(dateValue)) {
            outputValue = output.format((Date) input.parseObject(dateValue));
        }
        return outputValue;
    }

    /**
     * @Author:
     * @param strInputValue
     * @return
     * @Description: This methods checks the availability of Special Characters
     *               > < ' / \ in the input
     */
    public static boolean isSpecialCharactersFound(String strInputValue) {
        boolean isSpecialCharFound = false;
        final Pattern specialCharacterPattern = Pattern.compile(RMDCommonConstants.RESTRICTED_SPECIAL_CHAR_PATTERN);
        if (null != strInputValue) {

            if (specialCharacterPattern.matcher(strInputValue).find()) {
                isSpecialCharFound = true;
            }
        }
        return isSpecialCharFound;
    }

    /**
     * @Author:omd
     * @param strInputValue
     * @return
     * @Description: This methods checks the availability of only Special Characters
     *               in the input
     */
    
    public static boolean isOnlySpecialCharactersFound(String strInputValue) {
        boolean isSpecialCharFound = false;
        String specialCharacters = "[" + "-/@#!*$%^&.'_+={}()?<>|,\\]\\[:"+ "]+" ;
        
        if ( strInputValue.matches(specialCharacters)) {
        	isSpecialCharFound = true;
        } else {
        	isSpecialCharFound = false;
        }
        return isSpecialCharFound;
    }
    
    

    /**
     * @Author:
     * @param strInputValue
     * @return
     * @Description: This methods checks the availability of Special Characters
     *               in the input
     */
    public static boolean isSingleQuoteFound(String strInputValue) {
        boolean foundAny = true;
        if (strInputValue != null) {
            if (StringUtils.containsNone(strInputValue, "'")) {
                foundAny = false;
            }
        }
        return foundAny;
    }

    /**
     * @Author:
     * @param strInputValue
     * @return
     * @Description: This methods validates the user name string
     */
    public static boolean isInvalidUserName(String strInputValue) {
        boolean foundAny = true;
        if (strInputValue != null) {
            if (StringUtils.containsNone(strInputValue, "~!#$&%^*()[]{}`-+=|></\\:;,\"?")) {
                foundAny = false;
            }
        }
        return foundAny;
    }

    /**
     * @param strInputValue
     * @return
     */
    public static boolean isInValidText(String strInputValue) {
        boolean foundAny = true;
        if (strInputValue != null) {
            if (StringUtils.containsNone(strInputValue, ">\\<")) {
                foundAny = false;
            }
        }
        return foundAny;
    }

    /**
     * @Author:
     * @param path
     * @return
     * @Description:
     */
    public static synchronized boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
        return path.delete();
    }

    /**
     * @Author:
     * @param path
     * @return
     * @Description:
     */
    public static synchronized void makeDirectory(String path) {
        File reportDir = new File(path);
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }
    }

    /**
     * @Author:
     * @param dateValue
     *            - The java.util.Date object.
     * @return dateString in GMT
     * @Throws:
     * @Description: This method converts Date into GMT Format
     */
    public static Date convertToGMTDate(String dateValue) throws Exception {
        DateFormat dfout = new SimpleDateFormat(RMDCommonConstants.DateConstants.DEFAULT_DATE_FORMAT);
        return dfout.parse(convertToGMTString(dateValue, RMDCommonConstants.DateConstants.DEFAULT_DATE_FORMAT));
    }

    /**
     * @Author:
     * @param dateValue
     *            - The java.util.Date object.
     * @return dateString in GMT
     * @Throws:
     * @Description: This method converts Date into GMT Format
     */
    public static Date convertToGMTDate(String dateValue, String inputFormat) throws Exception {
        DateFormat dfout = new SimpleDateFormat(inputFormat);
        return dfout.parse(convertToGMTString(dateValue, inputFormat));
    }

    /**
     * @Author:
     * @param dateValue
     *            - The java.util.Date object.
     * @return dateString in GMT
     * @Throws:
     * @Description: This method converts Date into GMT Format
     */
    public static String convertToGMTString(String dateValue, String inputFormat) throws Exception {
        String returnGMTString = RMDCommonConstants.EMPTY_STRING;
        DateFormat dfin = null;
        DateFormat dfout = null;
        Date inDate = null;
        dfin = new SimpleDateFormat(inputFormat);
        dfout = new SimpleDateFormat(inputFormat);
        dfin.setTimeZone(TimeZone.getDefault());
        dfout.setTimeZone(TimeZone.getTimeZone("GMT"));
        inDate = dfin.parse(dateValue);
        returnGMTString = dfout.format(inDate);
        return returnGMTString;
    }

    /**
     * @Author:
     * @param dateValue
     *            - The java.util.Date object.
     * @return dateString in GMT
     * @Throws:
     * @Description: This method converts current Date into GMT Format
     */
    public static Date getGMTDateTime(String inputFormat) throws Exception {
        String format = inputFormat;
        if (null == inputFormat) {
            format = RMDCommonConstants.DateConstants.MMddyyyyHHmmssa;
        }
        DateFormat outDfm = new SimpleDateFormat(format);
        DateFormat outDfmTemp = new SimpleDateFormat(format);
        outDfm.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        String strd = outDfm.format(Calendar.getInstance().getTime());
        return outDfmTemp.parse(strd);
    }

    /**
     * @Author:
     * @param dateValue
     *            - The java.util.Date object.
     * @return dateString in EST
     * @Throws:
     * @Description: This method converts current Date into GMT Format
     */
    public static Date getESTDateTime(String inputFormat) throws Exception {
        String format = inputFormat;
        if (null == inputFormat) {
            format = RMDCommonConstants.DateConstants.MMddyyyyHHmmssa;
        }
        DateFormat outDfm = new SimpleDateFormat(format);
        DateFormat outDfmTemp = new SimpleDateFormat(format);
        outDfm.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.EST_US));
        String strd = outDfm.format(Calendar.getInstance().getTime());
        return outDfmTemp.parse(strd);
    }

    /**
     * @Author:
     * @param intGMToffset
     * @return
     * @Description:
     */
    public static String getTimeZone(int intGMToffset) {
        String toTimeZone = RMDCommonConstants.EMPTY_STRING;
        String[] zoneId = TimeZone.getAvailableIDs(intGMToffset * 1000);
        if (zoneId.length > 0) {
            toTimeZone = zoneId[0];
        } else {
            toTimeZone = "GMT";
        }
        return toTimeZone;
    }

    /**
     * @param condate
     * @param fromTimeZone
     * @param isDSTuDate
     * @param inputFormat
     * @param outputFormat
     * @return
     * @throws ParseException
     */
    public static String getConvertedDate(String condate, String fromTimeZone, String toTimeZone, String inputFormat,
            String outputFormat, boolean isDST) throws ParseException {
        String output = RMDCommonConstants.EMPTY_STRING;
        boolean isDSTcDate = false;
        SimpleTimeZone stzobj = null;
        Date cDate = null;
        DateFormat df = new SimpleDateFormat(inputFormat);
        df.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
        try {
            cDate = df.parse(condate);
        } catch (ParseException e) {
            throw e;
        }
        TimeZone ctz = TimeZone.getTimeZone(fromTimeZone);
        isDSTcDate = ctz.inDaylightTime(cDate);
        df = new SimpleDateFormat(outputFormat);
        if ((isDSTcDate && isDST) || (!isDSTcDate && isDST)) {
            /* if user is in DST then added an hour with the offset */
            stzobj = new SimpleTimeZone(TimeZone.getTimeZone(toTimeZone).getRawOffset() + 3600000, toTimeZone);
        } else {
            stzobj = new SimpleTimeZone(TimeZone.getTimeZone(toTimeZone).getRawOffset(), toTimeZone);
        }
        df.setTimeZone(stzobj);
        output = df.format(cDate);
        return output;
    }

    /**
     * will take the date to be converted and will convert the same to the
     * timezone specified by the user
     * 
     * @param condate
     * @param fromTimeZone
     * @param toTimeZone
     * @param inputFormat
     * @param outputFormat
     * @return
     * @throws ParseException
     */
    public static String getConvertedDate(String condate, String fromTimeZone, String toTimeZone, String inputFormat,
            String outputFormat) throws ParseException {
        String output = RMDCommonConstants.EMPTY_STRING;
        SimpleTimeZone stzobj = null;
        DateFormat dfin = new SimpleDateFormat(inputFormat);
        DateFormat dfout = new SimpleDateFormat(outputFormat);
        dfin.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
        Date cDate = null, uDate = null;
        boolean isDSTcDate = false, isDSTuDate = false;
        /* get the current date and convert that to the users time zone */
        Date today = new Date();
        DateFormat df = new SimpleDateFormat(inputFormat);
        df.setTimeZone(TimeZone.getTimeZone(toTimeZone));
        String userdate = df.format(today);
        /* parse both the user and tobe converted date */
        try {
            cDate = dfin.parse(condate);
            uDate = dfin.parse(userdate);
        } catch (ParseException e) {
            throw e;
        }
        /*
         * use the timezone object to check whether the both the date's are in
         * DST or not
         */
        TimeZone ctz = TimeZone.getTimeZone(fromTimeZone);
        TimeZone utz = TimeZone.getTimeZone(toTimeZone);
        isDSTcDate = ctz.inDaylightTime(cDate);
        isDSTuDate = utz.inDaylightTime(uDate);
        if ((isDSTcDate && isDSTuDate) || (!isDSTcDate && isDSTuDate)) {
            /* if user is in DST then added an hour with the offset */
            stzobj = new SimpleTimeZone(TimeZone.getTimeZone(toTimeZone).getRawOffset() + 3600000, toTimeZone);
        } else {
            stzobj = new SimpleTimeZone(TimeZone.getTimeZone(toTimeZone).getRawOffset(), toTimeZone);
        }
        dfout.setTimeZone(stzobj);
        output = dfout.format(cDate);
        /* format the date and return that to user */
        return output;
    }

    /**
     * @Author:
     * @param condate
     * @param fromTimeZone
     * @param toTimeZone
     * @param inputFormat
     * @param outputFormat
     * @return
     * @Description:
     */
    public static Date getConvertedDateFormat(String condate, String fromTimeZone, String toTimeZone,
            String inputFormat, String outputFormat) throws Exception {
        String strConvertedDate = getConvertedDate(condate, fromTimeZone, toTimeZone, inputFormat, outputFormat);
        DateFormat dtOut = new SimpleDateFormat(inputFormat);
        return dtOut.parse(strConvertedDate);
    }

    /* This method helps to get time zone with respect to dat light saving */
    /**
     * @Author:
     * @param condate
     * @param toTimeZone
     * @param inputFormat
     * @return
     * @throws Exception
     * @Description:
     */
    public static String getConvertedTimeZone(String condate, String toTimeZone, String inputFormat) throws Exception {

        boolean isDSTcDate = false;
        Date cDate = null;
        String output = RMDCommonConstants.EMPTY_STRING;
        DateFormat dfin = new SimpleDateFormat(inputFormat);
        try {
            cDate = dfin.parse(condate);
        } catch (ParseException e) {
            throw e;
        }
        TimeZone utz = TimeZone.getTimeZone(toTimeZone);
        isDSTcDate = utz.inDaylightTime(cDate);
        output = TimeZone.getTimeZone(toTimeZone).getDisplayName(isDSTcDate, 0);
        return output;
    }

    /**
     * @Author:
     * @param dtIn
     * @return
     * @throws Exception
     * @Description:
     */
    public static Date updateAsGMT(Date dtIn) throws Exception {
        Calendar sUTCCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sf = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        SimpleDateFormat sf1 = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);

        sf.setTimeZone(sUTCCalendar.getTimeZone());
        Date dtOut = null;
        dtOut = sf1.parse(sf.format(dtIn));
        return dtOut;
    }

    /**
     * @Author:
     * @param dtIn
     * @return
     * @Description:
     */
    public static String updateAsGMTString(Date dtIn) {
        Calendar sUTCCalendar = Calendar.getInstance(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        SimpleDateFormat sf = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);

        sf.setTimeZone(sUTCCalendar.getTimeZone());
        String strOut = null;
        strOut = sf.format(dtIn);
        return strOut;
    }

    /**
     * @Author:
     * @param language
     * @return
     * @Description:
     */
    public static Locale getLocale(String language) {
        Locale local = Locale.ENGLISH;
        if (RMDCommonConstants.CHINESE_LANGUAGE.equals(language)) {
            local = Locale.SIMPLIFIED_CHINESE;
        }
        return local;
    }

    /**
     * @Author:
     * @param dtDate
     * @param strOutputFormat
     * @param strTimeFlag
     * @param val
     * @param strUserTZ
     * @param strToTimeZone
     * @return String
     * @Description: This method is used to convert the user time zone to GMT
     *               time zone
     */
    public static String getDateTimeWithLagInGMT(Date dtDate, String strOutputFormat, String strTimeFlag, int val,
            String strUserTZ, String strToTimeZone) throws Exception {
        String strDtOut = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(dtDate);
        if ("S".equalsIgnoreCase(strTimeFlag)) {
            cal.add(Calendar.SECOND, -val);
        }
        if ("M".equalsIgnoreCase(strTimeFlag)) {
            cal.add(Calendar.MINUTE, -val);
        }
        if ("H".equalsIgnoreCase(strTimeFlag)) {
            cal.add(Calendar.HOUR, -val);
        }
        if ("D".equalsIgnoreCase(strTimeFlag)) {
            cal.add(Calendar.DATE, -val);
        }
        Date dtUpdated = cal.getTime();
        SimpleDateFormat sdfDestination = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        /* Convert from User time zone to GMT starts */
        strDtOut = RMDCommonUtility.getConvertedDate(sdfDestination.format(dtUpdated), strUserTZ, strToTimeZone,
                RMDCommonConstants.DateConstants.MMddyyyyHHmmssa, RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        return strDtOut;
    }

    /**
     * @Author:
     * @param dateValue
     * @param locale
     * @return
     * @Description:
     */
    public static String getDateAsString(Date dateValue, String outputFormat, String locale) throws Exception {
        if (dateValue == null) {
            return RMDCommonConstants.EMPTY_STRING;
        }
        String formattedDate = null;
        if (RMDCommonConstants.CHINESE_LANGUAGE.equals(locale)) {
            DateFormat formatter = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.CHINESE);
            formattedDate = formatter.format(dateValue);
            formatter = new SimpleDateFormat("HH:mm:ss");
            formattedDate += RMDCommonConstants.BLANK_SPACE + formatter.format(dateValue);
        } else {
            DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
            formattedDate = formatter.format(dateValue);
        }
        return formattedDate;
    }

    /**
     * @Author:
     * @param key
     * @param paramArray
     * @param language
     * @return
     * @Description:
     */
    public static String getMessage(String key, Object[] paramArray, String language) {
        String name = RMDCommonConstants.EMPTY_STRING;
        try {
            ApplicationContext context = RMDApplicationContext.getApplicationContext();
            name = context.getMessage(key, paramArray, RMDCommonUtility.getLocale(language));
        } catch (NoSuchMessageException e) {
            name = RMDCommonConstants.EMPTY_STRING;
            LOG.debug(e.getMessage(), e);
        }
        return name;
    }

    /**
     * @Author:
     * @param key
     * @param paramArray
     * @param language
     * @return
     * @Description:
     */
    public static String getErrorCode(String key) {
        String name = RMDCommonConstants.EMPTY_STRING;
        name = RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME).getProperty(key);
        return name;
    }

    /**
     * @Author:
     * @param strFileName
     * @return
     * @throws IOException
     * @Description:
     */
    public static byte[] getBytesFromFile(String strFileName) throws IOException {
        File inputFile = new File(strFileName);
        InputStream is = new FileInputStream(inputFile);
        long length = inputFile.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length) {
            numRead = is.read(bytes, offset, bytes.length - offset);
            if (numRead >= 0) {
                offset += numRead;
            }
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + inputFile.getName());
        }
        is.close();
        return bytes;
    }

    /**
     * @param n
     * @return
     */
    public static String pad(long n) {
        if (n == 0) {
            return RMDCommonConstants.ZERO_STRING + RMDCommonConstants.ZERO_STRING;
        } else if (n <= 9 && n >= 1) {
            return RMDCommonConstants.ZERO_STRING + n;
        } else {
            return String.valueOf(n);
        }
    }

    /**
     * @Author: iGATE
     * @param date
     * @return
     * @Description: This method will give date as String
     */
    public static String convertToString(Date date, String strFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
        return formatter.format(date);
    }

    /**
     * @Author: Igate Global Solutions
     * @param milliSeconds
     * @return hours
     * @Description: This method returns number of hours, calculated by the
     *               number of milliseconds passed.
     */
    public static double getHours(long milliSeconds) {
        double hours = -1;
        double seconds = 0;
        double minutes = 0;
        try {
            seconds = milliSeconds / 1000d;
            minutes = seconds / 60d;
            hours = minutes / 60d;
        } catch (Exception e) {
            hours = -1;
            LOG.debug(e.getMessage(), e);
        }
        return hours;
    }

    /**
     * @Author: iGATE
     * @param list
     * @return
     * @Description: Method used to get the comma separated values
     */
    public static String getCommaSep(List<String> list) {
        String strReturn = RMDCommonConstants.EMPTY_STRING;
        if (null != list && !list.isEmpty()) {
            strReturn = list.toString();
            strReturn = strReturn.substring(1, strReturn.length() - 1);
        }
        return strReturn;
    }

    /**
     * @Author: Igate Global Solutions
     * @param date
     * @return Date
     * @Description: This method mainly to convert the any time into the GMT
     */
    public static Date convertToGMT(Date date) {
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(date.getTime() - tz.getRawOffset());
        // checking the GMT date,
        if (tz.inDaylightTime(ret)) {
            Date dstDate = new Date(ret.getTime() - tz.getDSTSavings());
            if (tz.inDaylightTime(dstDate)) {
                ret = dstDate;
            }
        }
        return ret;
    }

    /**
     * @Author: iGATE
     * @param date
     * @param inputFormat
     * @return
     * @throws Exception
     * @Description: This method used to convert java Data to GMT
     */
    public static Date convertToGMTDate(java.util.Date date, String inputFormat) throws Exception {
        String strDateValue = convertToString(date, inputFormat);
        DateFormat dfout = new SimpleDateFormat(inputFormat);
        return dfout.parse(convertToGMTString(strDateValue, inputFormat));
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if it is valid number Returns false if it is an
     *         invalid number
     * @Description: This method will check whether passed value is a valid
     *               number/negative number or not
     */
    public static boolean isNumericWithNegative(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.NUMERIC_PATTERN_NEGATIVE,
                    Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(inputData);
            if (matcher.find()) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if it is valid number Returns false if it is an
     *         invalid number
     * @Description: This method will check whether passed value is a valid
     *               number only[without dot(.)] or not
     */
    public static boolean isNumbersOnly(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            inputData = inputData.replaceAll(RMDCommonConstants.BLANK_SPACE, RMDCommonConstants.EMPTY_STRING);
            final Pattern pattern = Pattern.compile(RMDCommonConstants.NUM_PATRN_WITHOUT_DOT, Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(inputData);
            if (matcher.find()) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if the value is alpha numeric
     */
    public static boolean isAlphaNumeric(final String strData) {
        boolean isValid = false;
        if (strData != null) {
            if (StringUtils.containsNone(strData, "~!$@#&-_+%.^*[]{}`=|></\\:;\"'?")) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if the value is alpha numeric
     */
    public static boolean isAlphabet(final String strData) {
        boolean isValid = false;
        if (strData != null) {
            if (StringUtils.containsNone(strData, "~!$@#&-_+%^*[]{}`=|></\\:;\"'?0123456789")) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if the value is alpha numeric
     */
    public static boolean isAlphaNumericWithHyphen(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            if (inputData.indexOf('-') != -1) {
                inputData = inputData.replaceAll("-", "");
            }
            isValid = isAlphaNumeric(inputData);
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if it is valid number Returns false if it is an
     *         invalid number
     * @Description: This method will check whether passed value is a valid
     *               number only[without dot(.)] or not
     */
    public static boolean isValidPhoneNumber(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            if (inputData.indexOf('-') != -1) {
                inputData = inputData.replaceAll("-", "");
            }

            isValid = isNumbersOnly(inputData);
        }
        return isValid;
    }

    /**
     * @Author:
     * @param strPassword
     * @return Returns true if it is valid password else returns false
     * @Description: This method will check whether password complies to GE
     *               Password policy 1. Should not begin and with a number; 2.
     *               Must be a minimum of 8 & maximum of 15 characters. 3. Must
     *               contain atleast 2 embedded numbers;
     */
    public static boolean isValidPassword(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            final Pattern pattern1 = Pattern.compile(RMDCommonConstants.PASSWORD_PATTERN1);
            final Matcher matcher1 = pattern1.matcher(inputData);
            final Pattern pattern2 = Pattern.compile(RMDCommonConstants.PASSWORD_PATTERN2);
            final Matcher matcher2 = pattern2.matcher(inputData);
            if (matcher1.find() && matcher2.find()) {
                isValid = true;
            }
        }
        return isValid;
    }

    /**
     * will take the date to be converted to timezone and formated according to
     * the locale of specified by the user
     * 
     * @param condate
     * @param fromTimeZone
     * @param toTimeZone
     * @param dateFormat
     * @param locale
     * @return
     * @throws ParseException
     */
    public static String getConvertedFormatedDate(Date date, String fromTimeZone, String toTimeZone, String dateFormat,
            String locale) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String condate = formatter.format(date);
        String output = RMDCommonConstants.EMPTY_STRING;
        SimpleTimeZone stzobj = null;
        DateFormat dfin = new SimpleDateFormat(dateFormat);
        dfin.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
        Date cDate = null;
        /* parse both the user and to be converted date */
        try {
            cDate = dfin.parse(condate);
        } catch (ParseException e) {
            throw e;
        }
        stzobj = new SimpleTimeZone(TimeZone.getTimeZone(toTimeZone).getRawOffset(), toTimeZone);
        if (RMDCommonConstants.CHINESE_LANGUAGE.equals(locale)) {
            DateFormat dfout = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, Locale.CHINESE);
            dfout.setTimeZone(stzobj);
            output = dfout.format(cDate);
            dfout = new SimpleDateFormat(RMDCommonConstants.DateConstants.HHmmss);
            dfout.setTimeZone(stzobj);
            output += RMDCommonConstants.BLANK_SPACE + dfout.format(cDate);
        } else {
            String pattern = null;
            if (pattern == null) {
                pattern = dateFormat;
            }
            DateFormat dfout = new SimpleDateFormat(pattern);
            dfout.setTimeZone(stzobj);
            output = dfout.format(cDate);
        }
        /* format the date and return that to user */
        return output;
    }

    /**
     * @Author:
     * @param dateInput
     * @return
     * @throws ParseException
     * @Description:
     */
    public static Date getDateAsGMT(Date dateInput) throws ParseException {
        if (dateInput == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        SimpleDateFormat sf1 = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
        sf.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT));
        Date dtOut = null;
        try {
            dtOut = sf1.parse(sf.format(dateInput));
        } catch (ParseException e) {
            throw e;
        }
        return dtOut;
    }

    /**
     * @Author:
     * @param date
     * @param format
     * @return
     * @Description:
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * @Author:
     * @param datetoCompare
     * @param startDate
     * @param endDate
     * @param excludeStartEndDate
     * @return
     * @Description:
     */
    public static boolean isDateWithinGivenRange(Date datetoCompare, Date startDate, Date endDate,
            boolean excludeStartEndDate) {
        boolean result = false;
        if (isDate1AfterDate2(datetoCompare, startDate) && isDate1BeforeDate2(datetoCompare, endDate)) {
            result = true;
        }
        if (excludeStartEndDate && (datetoCompare.equals(startDate) || datetoCompare.equals(endDate))) {
            result = true;
        }
        return result;
    }

    /**
     * @Author:
     * @param date1
     * @param date2
     * @return
     * @Description:
     */
    public static boolean isDate1AfterDate2(Date date1, Date date2) {
        if (compareDate(date1, date2) > 0) {
            return true;
        }
        return false;
    }

    /**
     * @Author:
     * @param date1
     * @param date2
     * @return
     * @Description:
     */
    public static boolean isDate1BeforeDate2(Date date1, Date date2) {
        if (compareDate(date1, date2) < 0) {
            return true;
        }
        return false;
    }

    /**
     * @Author:
     * @param date1
     * @param date2
     * @return
     * @Description:
     */
    private static int compareDate(Date date1, Date date2) {
        GregorianCalendar calendarDate1 = new GregorianCalendar();
        calendarDate1.setTime(date1);
        GregorianCalendar calendarDate2 = new GregorianCalendar();
        calendarDate2.setTime(date2);
       /* // Make sure that the timezones are the same
        if (!calendarDate1.getTimeZone().getID().equals(calendarDate2.getTimeZone().getID())) {
            calendarDate1.setTimeZone(calendarDate2.getTimeZone());
        }
        // Compare the dates
        if (calendarDate1.get(GregorianCalendar.YEAR) < calendarDate2.get(GregorianCalendar.YEAR)) {
            return -1;
        } else if (calendarDate1.get(GregorianCalendar.YEAR) > calendarDate2.get(GregorianCalendar.YEAR)) {
            return 1;
        } else if (calendarDate1.get(GregorianCalendar.DAY_OF_YEAR) < calendarDate2
                .get(GregorianCalendar.DAY_OF_YEAR)) {
            return -1;
        } else if (calendarDate1.get(GregorianCalendar.DAY_OF_YEAR) > calendarDate2
                .get(GregorianCalendar.DAY_OF_YEAR)) {
            return 1;
        } else if (calendarDate1.get(GregorianCalendar.HOUR_OF_DAY) < calendarDate2
                .get(GregorianCalendar.HOUR_OF_DAY)) {
            return -1;
        } else if (calendarDate1.get(GregorianCalendar.HOUR_OF_DAY) > calendarDate2
                .get(GregorianCalendar.HOUR_OF_DAY)) {
            return 1;
        } else if (calendarDate1.get(GregorianCalendar.MINUTE) < calendarDate2.get(GregorianCalendar.MINUTE)) {
            return -1;
        } else if (calendarDate1.get(GregorianCalendar.MINUTE) > calendarDate2.get(GregorianCalendar.MINUTE)) {
            return 1;
        } else if (calendarDate1.get(GregorianCalendar.SECOND) < calendarDate2.get(GregorianCalendar.MINUTE)) {
            return -1;
        } else if (calendarDate1.get(GregorianCalendar.SECOND) > calendarDate2.get(GregorianCalendar.MINUTE)) {
            return 1;
        } else {
            return 0;
        }*/

        if (date1.compareTo(date2) > 0) {
            return 1;
        } else if (date1.compareTo(date2) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * @Author:
     * @param roadNumber
     * @param roadnumberGroup
     * @return
     * @Description: This method is to validate the roadNumber against the Key
     *               Codes
     */
    public static boolean validateRoadNumber(int roadNumber, int roadnumberGroup) {
        AssetKeyValidator validator = null;
        boolean bResult = false;
        try {
            validator = AssetKeyValidator.getInstance();
            bResult = validator.validate(roadNumber, roadnumberGroup);
        } catch (Exception e) {
            throw new RMDRunTimeException(e);
        }
        return bResult;
    }

    /**
     * @Author:
     * @param roadNumber
     * @param roadnumberGroup
     * @return
     * @Description: This method is to validate the roadNumber against the Key
     *               Codes
     */
    public static String getApplicationBaseDirectoryPath() {
        String path = RMDCommonConstants.EMPTY_STRING;
        try {
            path = System.getenv().get(RMDCommonConstants.RMD_HOME) + File.separator;
        } catch (Exception e) {
            throw new RMDRunTimeException(e);
        }
        return path;
    }

    /**
     * @Author:
     * @param roadNumber
     * @param roadnumberGroup
     * @return
     * @Description: This method is to validate the roadNumber against the Key
     *               Codes
     */
    public static String getApplicationConfigEntriesFromPropertyFile(String key) {
        String value = RMDCommonConstants.EMPTY_STRING;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(RMDCommonUtility.getApplicationBaseDirectoryPath()
                    + RMDCommonConstants.RMD_BASE_PROPERTY_FILE_NAME);
            Properties props = new Properties();
            props.load(inputStream);
            value = props.getProperty(key);
        } catch (Exception e) {
            throw new RMDRunTimeException(e);
        } finally {
            try {
                inputStream.close();
            } catch (Exception exception) {
                throw new RMDRunTimeException(exception);
            }

        }
        return value;
    }

    /**
     * @Author:
     * @param d
     * @param decimalPlace
     * @return
     * @Description: see the Javadoc about why we use a String in the
     *               constructor
     *               http://java.sun.com/j2se/1.5.0/docs/api/java/math
     *               /BigDecimal.html#BigDecimal(double)
     */
    public static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @param strDate
     * @param strDateFormat
     * @return Date object
     * @throws ParseException
     */
    public static java.util.Date getDateValue(String strDate, String strDateFormat) throws ParseException {
        DateFormat formatter;
        Date date = null;
        formatter = new SimpleDateFormat(strDateFormat);
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    /**
     * @Author:
     * @param s
     * @return String
     * @Throws:None
     * @Description:This method will remove the Unwanted space from the String.
     */
    public static String removeALLSpaces(final String input) {

        return input.replace(" ", "");
    }

    public static String getRoundedString(final String input) throws Exception {
        Double doubleValue = 0.0D;
        String strRoundedString = null;
        try {
            doubleValue = getDoubleValue(input);
            doubleValue = round(doubleValue, 2);
            strRoundedString = doubleValue.toString();
        } catch (Exception e) {
            throw e;
        }
        return strRoundedString;
    }

    /**
     * @param strInput
     * @return
     * @throws Exception
     */
    public static String replaceComma(String strInput) throws Exception {
        String localInput = strInput;
        try {
            if (localInput != null) {
                localInput = localInput.replace(RMDCommonConstants.COMMMA_SEPARATOR,
                        RMDCommonConstants.COMMMA_SEPARATOR + RMDCommonConstants.BLANK_SPACE);
            }
        } catch (Exception e) {
            throw e;
        }
        return localInput;
    }

    /**
     * @param str
     * @return
     */
    public static String chop(String str) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        if (strLen < 2) {
            return "";
        }
        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);
        if (last == RMDCommonConstants.LF) {
            if (ret.charAt(lastIdx - 1) == RMDCommonConstants.CR) {
                return ret.substring(0, lastIdx - 1);
            }
        }
        return ret;
    }

    // this Method used in KM code--Start
    /**
     * @throws Exception
     *             This is the common method used for converting the xml date to
     *             string
     * @param strTrackingID
     *            @return list of SummaryVO @throws
     */
    public static String convertXMLDateToString(XMLGregorianCalendar gregorianCalendar) throws Exception {
        SimpleDateFormat objDateformat = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
        Date convertdate = null;
        String strDate = null;
        try {
            if (gregorianCalendar != null) {
                convertdate = new Date(gregorianCalendar.toGregorianCalendar().getTimeInMillis());
                strDate = objDateformat.format(convertdate);
            }
        } catch (Exception e) {
            throw e;
        }
        return strDate;
    }

    // this Method used in KM code--Start 24 hh format
    /**
     * @throws Exception
     *             This is the common method used for converting the xml date to
     *             string
     * @param strTrackingID
     *            @return list of SummaryVO @throws
     */
    public static String convertXMLDateToStringFormat(XMLGregorianCalendar gregorianCalendar) throws Exception {
        SimpleDateFormat objDateformat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date convertdate = null;
        String strDate = null;
        try {
            if (gregorianCalendar != null) {
                convertdate = new Date(gregorianCalendar.toGregorianCalendar().getTimeInMillis());
                strDate = objDateformat.format(convertdate);
            }
        } catch (Exception e) {
            throw e;
        }
        return strDate;
    }

    /**
     * @Author:
     * @param s
     * @return String
     * @throws Exception
     * @Throws:None
     * @Description:This method will remove the Duplicate records in the list
     *                   and return the list back
     */
    public static List<String> removeDuplicateValues(List<String> lstInput) throws Exception {
        SortedSet<String> objSortedSet = null;
        try {
            objSortedSet = new TreeSet<String>();
            if (RMDCommonUtility.isCollectionNotEmpty(lstInput)) {
                objSortedSet.addAll(lstInput);
                lstInput.clear();
                lstInput.addAll(objSortedSet);
            }

        } catch (Exception e) {
            throw e;
        }
        return lstInput;
    }

    // this Method used in KM code--End

    /**
     * @Author:
     * @param startDate
     * @param endDate
     * @return
     * @Description: Calculate difference between create date and current date
     */
    public static String calculateAge(GregorianCalendar startDate, GregorianCalendar endDate, String timeZone) {
        String age = RMDCommonConstants.EMPTY_STRING;
        RMDCommonUtility.setZoneOffsetTime(startDate, timeZone);
        RMDCommonUtility.setZoneOffsetTime(endDate, timeZone);

        long startTime = startDate.getTimeInMillis();
        long endTime = endDate.getTimeInMillis();

        // Calculate difference in milliseconds
        long diff = endTime - startTime;

        // Difference in minutes
        long diffMin = diff / (60 * 1000);

        // Difference in hours
        long diffHours = diff / (60 * 60 * 1000);

        // Difference in days
        long diffDays = diff / (24 * 60 * 60 * 1000);
        age = RMDCommonConstants.OPEN_BRACKET + diffDays + RMDCommonConstants.DateConstants.d
                + RMDCommonConstants.BLANK_SPACE + (diffHours - (diffDays * 24)) + RMDCommonConstants.DateConstants.hrs
                + RMDCommonConstants.BLANK_SPACE + (diffMin - (diffHours * 60)) + RMDCommonConstants.DateConstants.min
                + RMDCommonConstants.CLOSE_BRACKET;

        return age;
    }

    /*
     * @param Object
     * @return String
     * @Description: converts the given object to a string
     */
    public static String convertObjectToString(Object object) {
        if (object == null) {
            return null;
        } else {
            return object.toString();
        }
    }

    /*
     * @param Object object to convert
     * @param String defaultvalue to return if object is null
     * @return String
     * @Description: converts the given object to a string and returns it. if
     * the object is null, then returns the default value
     */
    public static String convertObjectToString(Object object, String defaultValue) {
        String objectToString = defaultValue;
        if (object != null) {
            objectToString = object.toString();
        }
        return objectToString;
    }

    /*
     * @param Object
     * @return Long
     * @Description: converts the given object to a long type
     */
    public static Long convertObjectToLong(Object object) {
        if (object == null) {
            return 0L;
        } else {
            return getLongValue(object.toString());
        }
    }

    /*
     * @param Object object to convert
     * @param Long defaultvalue to return if object is null
     * @return Long
     * @Description: converts the given object to a long value and returns it.
     * if the object is null, then returns the default value
     */
    public static Long convertObjectToLong(Object object, Long defaultValue) {
        Long objectToLong = defaultValue;
        if (object != null) {
            objectToLong = getLongValue(object.toString());
        }
        return objectToLong;
    }

    /*
     * @param Object object to convert
     * @param Long to return if object is null
     * @return Long
     * @Description: converts the given object to a long value and returns it.
     */
    public static Long cnvrtBigDecimalObjectToLong(Object object) {
        if (object == null) {
            return 0L;
        } else {
            return ((BigDecimal) object).longValue();
        }
    }

    public static BigDecimal convertObjtoBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
                        + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static Double cnvrtStringObjectToDouble(Object object) {
        if (object == null) {
            return 0.0;
        } else {
            return ((BigDecimal) object).doubleValue();
        }
    }

    public static int convertObjectToInt(Object object) {
        if (object == null) {
            return 0;
        } else {
            return Integer.parseInt(object.toString());
        }

    }

    public static float convertObjectToFloat(Object object) {
        if (object == null) {
            return 0;
        } else {
            return Float.parseFloat(object.toString());
        }

    }

    public static int convertStringToInt(String str) {
        if (str == null) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }

    }

    public static String formatDateToString(String formatStr, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(date);
    }

    public static String cnvrtBigDecimalObjectToString(Object object) {
        if (object == null) {
            return null;
        } else {
            return ((BigDecimal) object).toString();
        }
    }

    public static int cnvrtBigDecimalObjectToInt(Object object) {
        if (object == null) {
            return 0;
        } else {
            return ((BigDecimal) object).intValue();
        }
    }

    public static String cnvrtListToString(String object) {
        StringBuilder sb = new StringBuilder();
        if (object == null) {
            return null;
        } else {
            String convrt = object.replace(",", "','");
            sb.append("'");
            sb.append(convrt);
            sb.append("'");
            return sb.toString();
        }
    }

    public static String cnvrtDoubleObjectToString(Object object) {
        if (object == null) {
            return null;
        } else {
            return ((Double) object).toString();
        }
    }

    // Method converts comma separated string to list
    public static List<String> cnvrtStringToList(Object object) {
        List<String> itemsList = new ArrayList<String>();
        if (null == object) {
            return null;
        } else {
            String delimeter = ",";
            StringTokenizer strToken = new StringTokenizer(object.toString(), delimeter);
            while (strToken.hasMoreTokens()) {
                String token = strToken.nextToken();
                if (!token.equalsIgnoreCase("null") && !token.isEmpty()) {
                    itemsList.add(token);
                }
            }
            return itemsList;
        }
    }

    /*
     * added to fix eoa timezone issue
     */
    public static String getTimezoneId(String timezone) {
        String timezoneId = null;
        String[] ts = TimeZone.getAvailableIDs();
        for (int i = 0; i < ts.length; i++) {
            if (timezone.equalsIgnoreCase(TimeZone.getTimeZone(ts[i]).getDisplayName(false, TimeZone.SHORT))) {
                timezoneId = TimeZone.getTimeZone(ts[i]).getID();
                break;
            }
        }
        return timezoneId;
    }

    public static String getFavFilterColumnTypeCode(String columnType) {
        String columnTypeCD = null;

        if (columnType.equals(RMDCommonConstants.TEXT)) {
            columnTypeCD = RMDCommonConstants.FAV_FILTER_TEXT_CODE;
        } else if (columnType.equals(RMDCommonConstants.NUMBER)) {
            columnTypeCD = RMDCommonConstants.FAV_FILTER_NUMBER_CODE;
        } else if (columnType.equals(RMDCommonConstants.DATE_TIME)) {
            columnTypeCD = RMDCommonConstants.FAV_FILTER_DATE_CODE;
        } else if (columnType.equals(RMDCommonConstants.DECIMAL)) {
            columnTypeCD = RMDCommonConstants.FAV_FILTER_DECIMAL_CODE;
        }

        return columnTypeCD;
    }

    public static String getFavFilterColumnTypeValue(String columnType) {
        String columnTypeCD = null;

        if (columnType.equals(RMDCommonConstants.FAV_FILTER_TEXT_CODE)) {
            columnTypeCD = RMDCommonConstants.TEXT;
        } else if (columnType.equals(RMDCommonConstants.FAV_FILTER_NUMBER_CODE)) {
            columnTypeCD = RMDCommonConstants.NUMBER;
        } else if (columnType.equals(RMDCommonConstants.FAV_FILTER_DATE_CODE)) {
            columnTypeCD = RMDCommonConstants.DATE_TIME;
        } else if (columnType.equals(RMDCommonConstants.FAV_FILTER_DECIMAL_CODE)) {
            columnTypeCD = RMDCommonConstants.DECIMAL;
        }

        return columnTypeCD;
    }

    public static List<String> getAssetNoList(List<List<String>> assetNoLst) {
        List<String> assetNo = new ArrayList<String>();
        assetNo = new ArrayList<String>();
        if (null != assetNoLst) {
            for (int i = 0; i < assetNoLst.size(); i++) {
                for (int j = 0; j < assetNoLst.get(i).size(); j++) {
                    assetNo.add(assetNoLst.get(i).get(j));
                }
            }
        }
        return assetNo;
    }

    public static List<Long> getAssetNoListLong(List<String> assetNoLst) {
        List<Long> assetNo = new ArrayList<Long>();
        assetNo = new ArrayList<Long>();
        if (null != assetNoLst) {
            for (int i = 0; i < assetNoLst.size(); i++) {
                assetNo.add(Long.valueOf(assetNoLst.get(i)));
            }
        }
        return assetNo;
    }

    /**
     * To get the no of days in between two date
     * 
     * @param from
     *            and to date
     * @return
     */
    public static int findNoOfDaysBetween(Date fromdate, Date todate) {
        Long msFromDate = fromdate.getTime(), msTodate = todate.getTime();
        long day = 1000 * 60 * 60 * 24; // milliseconds in a day
        return (int) ((msTodate - msFromDate) / day);

    }

    /**
     * @Author:
     * @param startDate
     * @param endDate
     * @return
     * @Description: Calculate difference between fromDate and toDate
     */
    public static String calculateAge(Date dateStart, Date dateEnd) throws Exception {
        String age = RMDCommonConstants.EMPTY_STRING;
        try {

            // Calculate difference in milliseconds
            long diff = dateEnd.getTime() - dateStart.getTime();

            // Difference in minutes
            long diffMin = diff / (60 * 1000);

            // Difference in hours
            long diffHours = diff / (60 * 60 * 1000);

            // Difference in days
            long diffDays = diff / (24 * 60 * 60 * 1000);
            age = RMDCommonConstants.OPEN_BRACKET + diffDays + RMDCommonConstants.DateConstants.d
                    + RMDCommonConstants.BLANK_SPACE + (diffHours - (diffDays * 24))
                    + RMDCommonConstants.DateConstants.hrs + RMDCommonConstants.BLANK_SPACE
                    + (diffMin - (diffHours * 60)) + RMDCommonConstants.DateConstants.min
                    + RMDCommonConstants.CLOSE_BRACKET;

        } catch (Exception e) {
            throw e;
        }

        return age;
    }

    /**
     * @Author:
     * @param strData
     * @return Returns true if it is valid number, Returns false if it is an
     *         invalid number
     * @Description: This method will check whether passed value is a valid
     *               number/negative number with up to 4 Decimals or not
     */
    public static boolean isNumericWithNegativeAndSixDecimals(final String strData) {
        String inputData = strData;
        boolean isValid = false;
        if (inputData != null) {
            inputData = inputData.trim();
        }
        if ((inputData != null) && (inputData.length() > 0)) {
            final Pattern pattern = Pattern.compile(RMDCommonConstants.NUMERIC_PATTERN_NEGATIVE_SIX_DECIMALS,
                    Pattern.CASE_INSENSITIVE);
            final Matcher matcher = pattern.matcher(inputData);
            if (matcher.find()) {
                isValid = true;
            } else {
                isValid = false;
            }
        }
        return isValid;

    }

    /**
     * @Author:
     * @param aStart,aEnd,aRandom
     * @return int
     * @Description: This method generates a random number
     */
    public static int generateRandomInteger(int aStart, int aEnd, Random aRandom) {
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        long range = (long) aEnd - (long) aStart + 1;
        long fraction = (long) (range * aRandom.nextDouble());
        int randomNumber = (int) (fraction + aStart);
        return randomNumber;
    }

    /**
     * @Author: iGATE
     * @param list
     * @return
     * @Description: Method used to get the comma separated values with no space
     *               after comma
     */
    public static String getCommaSeperatedString(List<String> list) {
        String strReturn = RMDCommonConstants.EMPTY_STRING;
        if (null != list) {
            for (int j = 0; j < list.size(); j++) {
                strReturn += list.get(j).trim();
                if (j < list.size() - 1) {
                    strReturn += ",";
                }
            }
        }
        return strReturn;
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalender(Date date, String timeZone)
            throws DatatypeConfigurationException {
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        GregorianCalendar objGregorianCalendar;
        XMLGregorianCalendar objXmlGregorianCalendar;
        objGregorianCalendar = new GregorianCalendar();
        objGregorianCalendar.setTime(date);
        RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, timeZone);
        objXmlGregorianCalendar = dtf.newXMLGregorianCalendar(objGregorianCalendar);
        return objXmlGregorianCalendar;
    }

    /**
     * @Author: iGATE
     * @param Date
     *            ,timeZone
     * @return
     * @Description: This Method is used to convert Date to XMLGregorianCalendar
     *               to String.
     */

    public static String convertXMLGregorianCalenderToString(XMLGregorianCalendar objXmlGregorianCalendar,
            String timeZone) {
        final DateFormat zoneFormater = new SimpleDateFormat(RMDCommonConstants.DATE_FORMAT_24HRS);
        final TimeZone objTimeZone = TimeZone.getTimeZone(timeZone);
        zoneFormater.setTimeZone(objTimeZone);
        String strDate = zoneFormater.format(objXmlGregorianCalendar.toGregorianCalendar().getTime());
        return strDate;
    }

    /**
     * @Author: Capgemini
     * @param String
     * @return
     * @Description: This Method is used to make http hyper links from a given
     *               String
     */

    public static String makeHttpHyperlinks(String s) {
        int i = 0;
        int j = 0;
        int k = 0;
        String hyperLinkStr = "";
        String s2 = s;
        String s3 = s.toLowerCase();
        while (i != -1 && k != -1) {
            int l = s3.indexOf("http://", j);
            int j1 = s3.indexOf("https://", j);
            i = -1;
            if (l != -1)
                i = l;
            if (j1 != -1 && (j1 < i || i == -1))
                i = j1;
            if (i != -1) {
                int k1 = s2.indexOf(" ", i);
                k = s2.indexOf("\n", i);
                if (k != -1 && (k1 > k || k1 == -1)) {
                    hyperLinkStr = hyperLinkStr + s2.substring(j, i)
                            + "<a style=\"word-wrap:break-word\" href=\"javascript:void(0)\" data-url="
                            + s2.substring(i, k) + " >" + s2.substring(i, k) + "</a>";
                    j = k;
                } else {
                    k = s2.indexOf(" ", i);
                    if (k != -1)
                        hyperLinkStr = hyperLinkStr + s2.substring(j, i)
                                + "<a style=\"word-wrap:break-word\"  href=\"javascript:void(0)\"  data-url="
                                + s2.substring(i, k) + " >" + s2.substring(i, k) + "</a>";
                    else
                        hyperLinkStr = hyperLinkStr + s2.substring(j, i)
                                + "<a style=\"word-wrap:break-word\"  href=\"javascript:void(0)\"  data-url="
                                + s2.substring(i) + " >" + s2.substring(i) + "</a>";
                    j = k;
                }
            } else {
                hyperLinkStr = hyperLinkStr + s2.substring(j);
            }
        }
        return hyperLinkStr;
    }

    public static Date stringToUSESTDate(final String dateString, final String dateFormatPattern) throws Exception {
        Date date = null;
        final SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern(dateFormatPattern);
        // Specify strict parsing (i.e. inputs must match object's format)
        formatter.setLenient(false);
        formatter.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.EST_US));
        try{
        	date = formatter.parse(dateString);
        }catch(ParseException parseException){
        	formatter.setTimeZone(TimeZone.getTimeZone(RMDCommonConstants.DateConstants.EST));
        	date = formatter.parse(dateString);
        }
        return date;
    }

     
    public static String alphaFirstUppercase(Object obj)
    {
    	String str ="";
    	if (obj != null) 
    	{
    		str = obj.toString();
    		str= str.replace("_", " ").toLowerCase();
   	      	StringBuffer stringbf = new StringBuffer();
   	      	Matcher m = Pattern.compile("([a-z])([a-z]*)",Pattern.CASE_INSENSITIVE).matcher(str);
   	      	while (m.find()) 
   	      	{
   	      		m.appendReplacement(stringbf, 
   	      		m.group(1).toUpperCase() + m.group(2).toLowerCase());
   	      	}
   	      	str = stringbf.toString();
        }
    	
        return str;
    }
    public static String convertDateFormatTimezone(final String dateString,
			final String dateFormatFromPattern,
			final String dateFormatToPattern, String timeZone) throws Exception {

		final SimpleDateFormat dateFormatFrom = new SimpleDateFormat(
				dateFormatFromPattern);
		SimpleDateFormat toDateFormat = new SimpleDateFormat(
				dateFormatToPattern);
		dateFormatFrom.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
		Date statusDateTimeD = dateFormatFrom.parse(dateString);
		toDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		return toDateFormat.format(statusDateTimeD.getTime());

	}
	public static String getConvertedDate(String date, String fromTimeZone,
			String toTimeZone)
			throws ParseException {
		String output=null;
		final DateFormat fromzoneFormater = new SimpleDateFormat(
				RMDCommonConstants.DATE_FORMAT_24HRS);
		final DateFormat toZoneFormater = new SimpleDateFormat(
				RMDCommonConstants.DATE_FORMAT_24HRS);
		fromzoneFormater.setTimeZone(TimeZone.getTimeZone(fromTimeZone));
		toZoneFormater.setTimeZone(TimeZone.getTimeZone(toTimeZone));
		try {
			if(null!=date){
			Calendar dtCal = Calendar.getInstance();
			dtCal.setTime(fromzoneFormater.parse(date));
			output=toZoneFormater.format(dtCal.getTime());		
			}
		} catch (ParseException e) {
			throw e;
		}	
		return output;
	}
	
	
	public static String getConvertedDate(String strDate, String applicationTimeZone)			
			throws Exception {
		String output=null;
		Date dateDt=null;
				
		try {
			if(!strDate.isEmpty()){
			final DateFormat zoneFormater = new SimpleDateFormat(
					RMDCommonConstants.DATE_FORMAT_24HRS);		
			zoneFormater.setTimeZone(TimeZone.getTimeZone(applicationTimeZone));
			if (null!=applicationTimeZone && (RMDCommonConstants.EST_TIMEZONE).equalsIgnoreCase(applicationTimeZone)) {
				return strDate;
			} else {
				dateDt = RMDCommonUtility.stringToUSESTDate(
						strDate,
						RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
				output=zoneFormater
				.format(dateDt);
			}	
			}
		} catch (ParseException e) {
			throw e;
		}	
		return output;
	} 	
	
	/**
	 * This method will convert Date from source time zone to target time zone as well as from source date format to target date format
	 * @param dateString
	 * @param dateFormatFromPattern
	 * @param dateFormatToPattern
	 * @param sourceTimezone
	 * @param targetTimezone
	 * @return
	 * @throws Exception
	 */
	public static String convertDateFormatAndTimezone(final String dateString, final String dateFormatFromPattern,
			final String dateFormatToPattern, String sourceTimezone, String targetTimezone) throws Exception {
		final SimpleDateFormat dateFormatFrom = new SimpleDateFormat(dateFormatFromPattern);
		SimpleDateFormat toDateFormat = new SimpleDateFormat(dateFormatToPattern);
		dateFormatFrom.setTimeZone(TimeZone.getTimeZone(sourceTimezone));
		Date statusDateTimeD = dateFormatFrom.parse(dateString);
		toDateFormat.setTimeZone(TimeZone.getTimeZone(targetTimezone));
		return toDateFormat.format(statusDateTimeD.getTime());
	}
		public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
        	date=null;
        }
        return date != null;
    }
	/**
	 * This method will send email notification
	 * @Param email Subject
	 * @Param emailContent
	 * @Param smtpHost
	 * @Param smtpServerName
	 * @Param receipientList
	 * @Param fromDL
	 *  
	 */
    public static void SendEmail(String smtpHost, String smtpServerName, String emailSubject, String emailContent,
            String fromDL, String receipientList, File whitePaperPdffile) {
        try {
            boolean custflag = false;
            
            if(emailContent.contains(RMDCommonConstants.RX_CHANGE_MAIL_SEND_IN_BCC)){
                System.out.println("Sending email to customer");
                custflag = true;
                emailContent = emailContent.split(Pattern.quote("-"))[1];
            }
            
            Properties props = new Properties();
            props.setProperty(smtpHost, smtpServerName);
            Session session = Session.getDefaultInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromDL));
            if(custflag){
                msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(receipientList));
            }else{
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(receipientList));
            }
            msg.setSubject(emailSubject);           
            msg.setSentDate(new Date());
            

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailContent, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (whitePaperPdffile != null) {
                MimeBodyPart attachPart = new MimeBodyPart();
                try {
                    attachPart.attachFile(whitePaperPdffile);
                } catch (IOException ex) {
                    System.out.println("Exception in SendEmail method " + ex.toString());
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
            }
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (SendFailedException e) {
            e.printStackTrace();
            System.out.println("Message Exception in SendEmail method SendFailedException " + e.toString());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Message Exception in SendEmail method " + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in SendEmail method " + e.toString());
        }
    }
	
	/*private static String getBody(String exceptionSummary) throws IOException  {
        String body = null;
            try{
            
                VelocityEngine ve = new VelocityEngine();
                ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
                ve.init();
            Template template = ve.getTemplate("MailTemplate.vm");
            VelocityContext vmcontext = new VelocityContext();
            vmcontext.put("timeStamp",
                    new Timestamp(System.currentTimeMillis()));
            vmcontext.put("exceptionSummary", exceptionSummary);
            Writer writer = new StringWriter();

            if (template != null) {
                template.merge(vmcontext, writer);
                body = writer.toString();
            }
            System.out.println("-->Email body has been created.\n");
            }catch(Exception e){
                System.out.println(e);
            }
        
        return body;
    }*/
}
