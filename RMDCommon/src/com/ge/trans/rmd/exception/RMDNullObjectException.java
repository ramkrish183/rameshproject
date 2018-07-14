/**
 * ============================================================
 * File : NullObjectException.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.exception
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 2, 2009
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
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 2, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDNullObjectException extends RMDException {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for RMDNullObjectException
     */
    public RMDNullObjectException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.NULLOBJECTEXCEPTION), RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorParams
     */
    public RMDNullObjectException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.NULLOBJECTEXCEPTION), errorParams);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDNullObjectException(String errorCode, String[] errorParams) {
        super(errorCode, errorParams);
    }

    /**
     * @param errorCode
     */
    public RMDNullObjectException(String errorCode) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDNullObjectException(Class className, String methodName, String[] params, String errorCode) {
        super(className, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDNullObjectException(Class className, String methodName, String errorCode) {
        super(className, methodName, errorCode);
    }

    /**
     * @param className
     * @param params
     * @param errorCode
     */
    public RMDNullObjectException(Class className, String[] params, String errorCode) {
        super(className, null, params, errorCode);
    }

    /**
     * @param params
     * @param errorCode
     * @param methodName
     */
    public RMDNullObjectException(String[] params, String errorCode, String methodName) {
        super(null, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDNullObjectException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.NULLOBJECTEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDNullObjectException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.NULLOBJECTEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDNullObjectException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.NULLOBJECTEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDNullObjectException(ErrorDetail errorDetail) {
        super(errorDetail);
    }
}
