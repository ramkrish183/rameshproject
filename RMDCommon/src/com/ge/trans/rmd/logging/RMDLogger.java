/**
 * ============================================================
 * File : RMDLogger.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.logging;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.logging;

import java.io.Serializable;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public final class RMDLogger implements Serializable {

    private static final long serialVersionUID = 133L;
    private Logger log;

    private RMDLogger() {
        this.log = null;
    }

    /**
     * 
     */
    public static RMDLogger getLogger(final Class className) {
        RMDLogger logger = new RMDLogger();
        logger.log = Logger.getLogger(className);
        return logger;
    }

    /**
     * void
     * 
     * @param obj
     */
    public void debug(Object obj) {
        log.debug(obj);
    }

    /**
     * void
     * 
     * @param obj
     * @param t
     */
    public void debug(Object obj, Throwable throwable) {
        log.debug(obj, throwable);
    }

    /**
     * void
     * 
     * @param obj
     */
    public void error(Object obj) {
        log.error(obj);
    }

    /**
     * void
     * 
     * @param obj
     */
    public void error(Object obj, Throwable throwable) {
        log.error(obj, throwable);
    }

    /**
     * void
     * 
     * @param obj
     */
    public void fatal(Object obj) {
        log.fatal(obj);
    }

    /**
     * void
     * 
     * @param obj
     * @param throwable
     */
    public void fatal(Object obj, Throwable throwable) {
        log.fatal(obj, throwable);
    }

    /**
     * void
     * 
     * @param obj
     */
    public void warn(Object obj) {
        log.warn(obj);
    }

    /**
     * void
     * 
     * @param obj
     * @param t
     */
    public void warn(Object obj, Throwable throwable) {
        log.warn(obj, throwable);
    }

    /**
     * void
     * 
     * @param obj
     * @param t
     */
    public void info(Object obj, Throwable throwable) {
        log.warn(obj, throwable);
    }

    /**
     * void
     * 
     * @param obj
     */
    public void info(Object obj) {
        log.info(obj);
    }

    /**
     * @param level
     * @return boolean
     */
    public boolean isEnabledFor(Level level) {
        return log.isEnabledFor(level);

    }

}
