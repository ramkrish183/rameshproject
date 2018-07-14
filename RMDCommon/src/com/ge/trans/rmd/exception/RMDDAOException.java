/**
 * ============================================================
 * File : RMDDAOException.java
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
 * @Description : Class for throwing DAOExceptions
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDDAOException extends RMDRunTimeException {

    /**
     * Default serialVersionID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to throw DAOException
     */
    public RMDDAOException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOEXCEPTION), RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorParams
     */
    public RMDDAOException(final String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOEXCEPTION), errorParams);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDDAOException(final String errorCode, final String[] errorParams) {
        super(errorCode, errorParams);
    }

    /**
     * @param errorCode
     * @param errorParams
     */
    public RMDDAOException(final String errorCode, final String[] errorParams, final String strErrMsg,
            final Exception objErrMsg, final String strErrorType) {
        super(errorCode, errorParams, strErrMsg, objErrMsg, strErrorType);
    }

    /**
     * @param errorCode
     */
    public RMDDAOException(final String errorCode) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorCode
     */
    public RMDDAOException(final String errorCode, final String errorMessage) {
        super(errorCode, errorMessage);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDDAOException(final Class className, final String methodName, final String[] params,
            final String errorCode) {
        super(className, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDDAOException(Class className, final String methodName, final String errorCode) {
        super(className, methodName, errorCode);
    }

    /**
     * @param className
     * @param params
     * @param errorCode
     */
    public RMDDAOException(Class className, String[] params, String errorCode) {
        super(className, null, params, errorCode);
    }

    /**
     * @param params
     * @param errorCode
     * @param methodName
     */
    public RMDDAOException(String[] params, String errorCode, String methodName) {
        super(null, methodName, params, errorCode);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDDAOException(Class className, String methodName, String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDDAOException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDDAOException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDDAOException(ErrorDetail errorDetail) {
        super(errorDetail);
    }

    /**
     * @param errorDetail
     */
    public RMDDAOException(String errorCode, Exception objErrMsg) {
        super(errorCode, RMDCommonConstants.EMPTY_STRING, objErrMsg);
    }

    /**
     * @param errorCode
     */
    public RMDDAOException(final String errorCode, final String errorMessage, Exception objErrMsg) {
        super(errorCode, errorMessage, objErrMsg);
    }

}