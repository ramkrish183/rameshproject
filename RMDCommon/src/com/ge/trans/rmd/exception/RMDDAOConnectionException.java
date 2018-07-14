/**
 * ============================================================
 * File : RMDDAOConnectionException.java
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
 * @Description : Exception to be thrown when connection failure occurs
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDDAOConnectionException extends RMDDAOException {

    /**
     * Default serialVersionID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to throw DAO Connection Exception
     */
    public RMDDAOConnectionException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION));
    }

    /**
     * @param errorCode
     */
    public RMDDAOConnectionException(String errorCode) {
        super(errorCode);
    }

    /**
     * @param errorParams
     */
    public RMDDAOConnectionException(String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION), errorParams);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDDAOConnectionException(final Class className, final String methodName, final String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION));
    }

    /**
     * @param className
     * @param methodName
     */
    public RMDDAOConnectionException(final Class className, final String methodName) {
        super(className, methodName, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDDAOConnectionException(String[] params, final Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDDAOConnectionException(final String[] params, final String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.DAOCONNECTIONEXCEPTION));
    }

    /**
     * @param errorCode
     */
    public RMDDAOConnectionException(final String errorCode, final String[] errorParams, final String strErrMsg,
            final Exception objErrMsg, final String strErrType) {
        super(errorCode, errorParams, strErrMsg, objErrMsg, strErrType);
    }

    /**
     * @param errorCode
     */
    public RMDDAOConnectionException(String errorCode, Exception objErrMsg) {
        super(errorCode, objErrMsg);
    }
}
