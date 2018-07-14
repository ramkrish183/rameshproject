/**
 *  Project     :   RAIL - QTR OMI
 *  Program     :   AppLogger.java
 *  Author      :   Patni Team
 *  Date        :   10-June-2010
 *  Security    :   Classified/Unclassified
 *  Restrictions:   GE PROPRIETARY INFORMATION, FOR GE USE ONLY
 *
 *  ****************************************************
 *  *  Copyright(2010) with all rights reserved        *
 *  *          General Electric Company                *
 *  ****************************************************
 *  Description:   Application Logger .
 *  
 *  Revision Log  (mm/dd/yy initials description)
 *  --------------------------------------------------------
 *  Patni Team    June 10, 2010  Created
 */
package com.ge.trans.rmd.tools.keys.util;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public final class AppLogger {
    private Logger log = null;
    private Class<?> objClass = null;

    private static boolean SYSOUT_ENABLED;
    private static boolean SYSOUT_STACKTRACE_ENABLED;
    public static final boolean ERROR_ONLY_LOGGING = false;

    public static final String ENV_PARAM_SYSOUT = "sysout.enabled";
    public static final String ENV_PARAM_SYSOUT_STACKTRACE = "sysout.stack.enabled";
    public static final String SYSOUT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    static {
        String param = null;

        param = CommonUtils.trimToNull(System.getProperty(ENV_PARAM_SYSOUT));
        if (param != null) {
            SYSOUT_ENABLED = Boolean.valueOf(param);

            if (SYSOUT_ENABLED) {
                param = CommonUtils.trimToNull(System.getProperty(ENV_PARAM_SYSOUT_STACKTRACE));

                if (param != null) {
                    SYSOUT_STACKTRACE_ENABLED = Boolean.valueOf(param);
                } else {
                    SYSOUT_STACKTRACE_ENABLED = AppConstants.DefaultProperties.LOG_SYSOUT_STACKTRACE_ENABLED;
                }
            }
        } else {
            SYSOUT_ENABLED = AppConstants.DefaultProperties.LOG_SYSOUT_ENABLED;
            SYSOUT_STACKTRACE_ENABLED = AppConstants.DefaultProperties.LOG_SYSOUT_STACKTRACE_ENABLED;
        }
    }

    private boolean traceEnabledD = true;
    private boolean debugEnabled = true;
    private boolean infoEnabled = true;
    private boolean warnEnabled = true;
    private boolean errorEnabled = true;
    private boolean fatalEnabled = true;

    public AppLogger(Class<?> pClass) {
        objClass = pClass;
        log = Logger.getLogger(objClass);

        traceEnabledD = log.isEnabledFor(Level.TRACE);
        debugEnabled = log.isEnabledFor(Level.DEBUG);
        infoEnabled = log.isEnabledFor(Level.INFO);
        warnEnabled = log.isEnabledFor(Level.WARN);
        errorEnabled = log.isEnabledFor(Level.ERROR);
        fatalEnabled = log.isEnabledFor(Level.FATAL);
    }

    private String getLogPrefix(String pLevel) {
        StringBuilder sBuffer = new StringBuilder();
        sBuffer.append(CommonUtils.getFormattedDate(new Date(), SYSOUT_TIMESTAMP_FORMAT));
        sBuffer.append(" ");
        sBuffer.append(CommonUtils.rightPad(pLevel, 5, " "));
        sBuffer.append(" [");
        sBuffer.append(Thread.currentThread().getName());
        sBuffer.append("] [");
        sBuffer.append(objClass.getName());
        sBuffer.append("] ");

        return sBuffer.toString();
    }

    public void debug(Object pMessage, Throwable pThrowable) {
        if (!ERROR_ONLY_LOGGING) {
            if (debugEnabled)
                log.debug(pMessage, pThrowable);

            if (SYSOUT_ENABLED) {

                if (SYSOUT_STACKTRACE_ENABLED)
                    pThrowable.printStackTrace();
                else
                    log.debug(getLogPrefix(AppConstants.LoggerLevel.DEBUG) + pThrowable);
            }
        }
    }

    public void debug(Object pMessage) {
        if (!ERROR_ONLY_LOGGING) {
            if (debugEnabled)
                log.debug(pMessage);

            if (SYSOUT_ENABLED)
                log.debug(getLogPrefix(AppConstants.LoggerLevel.DEBUG) + pMessage);
        }
    }

    public void error(Object pMessage, Throwable pThrowable) {
        if (errorEnabled)
            log.error(pMessage, pThrowable);

        if (SYSOUT_ENABLED) {
            log.error(getLogPrefix(AppConstants.LoggerLevel.ERROR) + pMessage);

            if (SYSOUT_STACKTRACE_ENABLED)
                pThrowable.printStackTrace();
            else
                log.error(getLogPrefix(AppConstants.LoggerLevel.ERROR) + pThrowable);
        }
    }

    public void error(Object pMessage) {
        if (errorEnabled)
            log.error(pMessage);

        if (SYSOUT_ENABLED)
            log.error(getLogPrefix(AppConstants.LoggerLevel.ERROR) + pMessage);
    }

    public void fatal(Object pMessage, Throwable pThrowable) {
        if (fatalEnabled)
            log.fatal(pMessage, pThrowable);

        if (SYSOUT_ENABLED) {
            log.error(getLogPrefix(AppConstants.LoggerLevel.FATAL) + pMessage);

            if (SYSOUT_STACKTRACE_ENABLED)
                pThrowable.printStackTrace();
            else
                log.error(getLogPrefix(AppConstants.LoggerLevel.FATAL) + pThrowable);
        }
    }

    public void fatal(Object pMessage) {
        if (fatalEnabled)
            log.fatal(pMessage);

        if (SYSOUT_ENABLED)
            log.error(getLogPrefix(AppConstants.LoggerLevel.FATAL) + pMessage);
    }

    public void info(Object pMessage, Throwable pThrowable) {
        if (!ERROR_ONLY_LOGGING) {
            if (infoEnabled)
                log.debug(pMessage, pThrowable);

            if (SYSOUT_ENABLED) {
                log.debug(getLogPrefix(AppConstants.LoggerLevel.INFO) + pMessage);

                if (SYSOUT_STACKTRACE_ENABLED)
                    pThrowable.printStackTrace();
                else
                    log.debug(getLogPrefix(AppConstants.LoggerLevel.INFO) + pThrowable);
            }
        }
    }

    public void info(Object pMessage) {
        if (!ERROR_ONLY_LOGGING) {
            if (infoEnabled)
                log.debug(pMessage);

            if (SYSOUT_ENABLED)
                log.debug(getLogPrefix(AppConstants.LoggerLevel.INFO) + pMessage);
        }
    }

    public void trace(Object pMessage, Throwable pThrowable) {
        if (!ERROR_ONLY_LOGGING) {
            if (traceEnabledD)
                log.trace(pMessage, pThrowable);

            if (SYSOUT_ENABLED) {
                log.debug(getLogPrefix(AppConstants.LoggerLevel.TRACE) + pMessage);

                if (SYSOUT_STACKTRACE_ENABLED)
                    pThrowable.printStackTrace();
                else
                    log.debug(getLogPrefix(AppConstants.LoggerLevel.TRACE) + pThrowable);
            }
        }
    }

    public void trace(Object pMessage) {
        if (!ERROR_ONLY_LOGGING) {
            if (traceEnabledD)
                log.trace(pMessage);

            if (SYSOUT_ENABLED)
                log.debug(getLogPrefix(AppConstants.LoggerLevel.TRACE) + pMessage);
        }
    }

    public void warn(Object pMessage, Throwable pThrowable) {
        if (!ERROR_ONLY_LOGGING) {
            if (warnEnabled)
                log.warn(pMessage, pThrowable);

            if (SYSOUT_ENABLED) {
                log.debug(getLogPrefix(AppConstants.LoggerLevel.WARN) + pMessage);

                if (SYSOUT_STACKTRACE_ENABLED)
                    pThrowable.printStackTrace();
                else
                    log.debug(getLogPrefix(AppConstants.LoggerLevel.WARN) + pThrowable);
            }
        }
    }

    public void warn(Object pMessage) {
        if (!ERROR_ONLY_LOGGING) {
            if (warnEnabled)
                log.warn(pMessage);

            if (SYSOUT_ENABLED)
                log.debug(getLogPrefix(AppConstants.LoggerLevel.WARN) + pMessage);
        }
    }
}