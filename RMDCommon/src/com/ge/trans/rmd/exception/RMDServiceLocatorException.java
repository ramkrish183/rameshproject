/**
 * ============================================================
 * File : RMDServiceLocatorException.java
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
 * @Description : Base class for all service exceptions
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDServiceLocatorException extends RMDException {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of Service Exception
     */
    public RMDServiceLocatorException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION), RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorParams
     */
    public RMDServiceLocatorException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION), errorParams);
    }

    /**
     * @param errorCode
     */
    public RMDServiceLocatorException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * @param errorCode
     */
    public RMDServiceLocatorException(String errorCode, String[] errorParams, String strErrMsg, Exception objErrMsg,
            String strErrType) {
        super(errorCode, errorParams, strErrMsg, objErrMsg, strErrType);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDServiceLocatorException(String errorCode, String[] errorParams) {
        super(errorCode, errorParams);
    }

    /**
     * @param errorCode
     */
    public RMDServiceLocatorException(String errorCode) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDServiceLocatorException(Class className, String methodName, String[] params, String errorCode) {
        super(className, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDServiceLocatorException(Class className, String methodName, String errorCode) {
        super(className, methodName, errorCode);
    }

    /**
     * @param className
     * @param params
     * @param errorCode
     */
    public RMDServiceLocatorException(Class className, String[] params, String errorCode) {
        super(className, null, params, errorCode);
    }

    /**
     * @param params
     * @param errorCode
     * @param methodName
     */
    public RMDServiceLocatorException(String[] params, String errorCode, String methodName) {
        super(null, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDServiceLocatorException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDServiceLocatorException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDServiceLocatorException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDServiceLocatorException(ErrorDetail errorDetail) {
        super(errorDetail);
    }

    /**
     * @param errorCode
     */
    public RMDServiceLocatorException(String errorCode, Exception objErrMsg) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING, objErrMsg);
    }
}
