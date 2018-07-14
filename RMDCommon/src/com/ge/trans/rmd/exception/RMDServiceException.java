/**
 * ============================================================
 * File : RMDServiceException.java
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
public class RMDServiceException extends RMDException {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of Service Exception
     */
    public RMDServiceException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION), RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorParams
     */
    public RMDServiceException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION), errorParams);
    }

    /**
     * @param errorCode
     */
    public RMDServiceException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * @param errorCode
     */
    public RMDServiceException(String errorCode, String[] errorParams, String strErrMsg, Exception objErrMsg,
            String strErrType) {
        super(errorCode, errorParams, strErrMsg, objErrMsg, strErrType);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDServiceException(String errorCode, String[] errorParams) {
        super(errorCode, errorParams);
    }

    /**
     * @param errorCode
     */
    public RMDServiceException(String errorCode) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDServiceException(Class className, String methodName, String[] params, String errorCode) {
        super(className, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDServiceException(Class className, String methodName, String errorCode) {
        super(className, methodName, errorCode);
    }

    /**
     * @param className
     * @param params
     * @param errorCode
     */
    public RMDServiceException(Class className, String[] params, String errorCode) {
        super(className, null, params, errorCode);
    }

    /**
     * @param params
     * @param errorCode
     * @param methodName
     */
    public RMDServiceException(String[] params, String errorCode, String methodName) {
        super(null, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDServiceException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDServiceException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDServiceException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.SERVICEEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDServiceException(ErrorDetail errorDetail) {
        super(errorDetail);
    }

    public RMDServiceException(ErrorDetail errorDetail, Exception objErrMsg) {
        super(errorDetail, objErrMsg);
    }

    public RMDServiceException(Exception objErrMsg) {
        super(objErrMsg);
    }
}
