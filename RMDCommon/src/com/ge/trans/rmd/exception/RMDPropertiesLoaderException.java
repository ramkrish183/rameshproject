/**
 * ============================================================
 * File : RMDPropertiesLoaderException.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.exception
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
package com.ge.trans.rmd.exception;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.utilities.RMDPropertiesLoader;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Exception thrown when loading properties
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDPropertiesLoaderException extends RMDRunTimeException {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructor for propertiesloaderexception
     */
    public RMDPropertiesLoaderException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION));
    }

    public RMDPropertiesLoaderException(Exception cause) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION), "", cause);
    }

    /**
     * @param errorCode
     */
    public RMDPropertiesLoaderException(String errorCode) {
        super(errorCode);
    }

    /**
     * @param errorParams
     */
    public RMDPropertiesLoaderException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION), errorParams);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDPropertiesLoaderException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION));
    }

    /**
     * @param className
     * @param methodName
     */
    public RMDPropertiesLoaderException(Class className, String methodName) {
        super(className, methodName, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDPropertiesLoaderException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDPropertiesLoaderException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BEANFACTORYEXCEPTION));
    }
}
