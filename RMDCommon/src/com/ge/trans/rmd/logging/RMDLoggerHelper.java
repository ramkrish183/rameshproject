/**
 * ============================================================
 * File : RMDLoggerHelper.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.cases.utils
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 30, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.logging;

import org.apache.log4j.MDC;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Helper class for loading log4j.xml and mapping the class for
 *              logging
 * @History :
 ******************************************************************************/
public final class RMDLoggerHelper {

    /**
     * Private Constructor
     */
    private RMDLoggerHelper() {
    }

    /**
     * @Author:
     * @param className
     * @return Logger
     * @Description: Used for getting logger for the required class
     */
    public static RMDLogger getLogger(final Class className) {
        return RMDLogger.getLogger(className);
    }

    /**
     * @param propertyName
     * @param propertyValue
     */
    public static void setMDCProperty(final String propertyName, final String propertyValue) {
        MDC.put(propertyName, propertyValue);
    }

    /**
     * @param propertyName
     */
    public static void removeMDCProperty(final String propertyName) {
        MDC.remove(propertyName);
    }
}