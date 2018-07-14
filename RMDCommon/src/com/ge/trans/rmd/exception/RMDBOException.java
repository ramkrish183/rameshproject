/**
 * ============================================================
 * File : RMDBOException.java
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
 * @Description : Base class for all BOExceptions
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDBOException extends RMDException {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for RMDBOException
     */
    public RMDBOException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BOEXCEPTION), RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorParams
     */
    public RMDBOException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BOEXCEPTION), errorParams);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDBOException(final String errorCode, final String[] errorParams) {
        super(errorCode, errorParams);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDBOException(final String errorCode, final String[] errorParams, String strErrMsg, Exception objErrMsg,
            String strErrType) {
        super(errorCode, errorParams, strErrMsg, objErrMsg, strErrType);
    }

    /**
     * @param errorCode
     */
    public RMDBOException(String errorCode) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorCode
     * @param errorMessage
     */
    public RMDBOException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDBOException(Class className, String methodName, String[] params, String errorCode) {
        super(className, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDBOException(Class className, String methodName, String errorCode) {
        super(className, methodName, errorCode);
    }

    /**
     * @param className
     * @param params
     * @param errorCode
     */
    public RMDBOException(Class className, String[] params, String errorCode) {
        super(className, null, params, errorCode);
    }

    /**
     * @param params
     * @param errorCode
     * @param methodName
     */
    public RMDBOException(String[] params, String errorCode, String methodName) {
        super(null, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDBOException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BOEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDBOException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BOEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDBOException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.BOEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDBOException(ErrorDetail errorDetail) {
        super(errorDetail);
    }

    public RMDBOException(ErrorDetail errorDetail, Exception objErrMsg) {
        super(errorDetail, objErrMsg);
    }
}
