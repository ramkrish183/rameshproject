/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AppConstants.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   AppConstants .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.util;

public class AppConstants {



    public static final String DEFAULT_EXTN_SEPARATOR = ".";
    public static final String DEFAULT_KEY_FILE_EXTN = DEFAULT_EXTN_SEPARATOR + "bin";
    public static final String DEFAULT_KEY_FILE_NAME = "sys-util" + DEFAULT_KEY_FILE_EXTN;
    public static final String DEFAULT_KEY_BAK_FILE_SUFFIX = "-bak-";
    public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String XML_KEY_ASSET_KEY_FILE = "ASSET_KEY_FILE";
    public static final String HASH_SYMBOL = "#";
    public static final String EMPTY_STRING = "";
    public static final String HYPHEN = "-";
    public static final String SPACE = " ";
    public static final String THREE_DOT = "...";
    public static final String TWO_DOT = "..";
    public static final String SINGLE_QUOTES = ",";
    public static final String DOUBLE_QUOTES = "\"";
    public static final String LAST_LOGIN_FROM = "Web";
    public static final String NEW_LINE = "\n";
    public final class LoggerLevel {
        public final static String TRACE = "TRACE";
        public final static String DEBUG = "DEBUG";
        public final static String INFO = "INFO";
        public final static String WARN = "WARN";
        public final static String ERROR = "ERROR";
        public final static String FATAL = "FATAL";
    }

    public final class DefaultProperties {
        public final static boolean LOG_SYSOUT_ENABLED = false;
        public final static boolean LOG_SYSOUT_STACKTRACE_ENABLED = false;
    }
    
    private AppConstants() {
    }
}
