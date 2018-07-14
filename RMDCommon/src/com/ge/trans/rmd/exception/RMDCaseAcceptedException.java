/**
 * ============================================================
 * File : RMDCaseAcceptedException.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.exception
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
package com.ge.trans.rmd.exception;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.utilities.RMDPropertiesLoader;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDCaseAcceptedException extends RMDServiceException {

    /**
     * Default serialVersionID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor to throw DAO Connection Exception
     */
    public RMDCaseAcceptedException() {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION));
    }

    /**
     * @param errorCode
     */
    public RMDCaseAcceptedException(final String errorCode) {
        super(errorCode);
    }

    /**
     * @param errorParams
     */
    public RMDCaseAcceptedException(final String[] errorParams) {
        super(RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION), errorParams);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     */
    public RMDCaseAcceptedException(final Class className, final String methodName, final String[] params) {
        super(className, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION));
    }

    /**
     * @param className
     * @param methodName
     */
    public RMDCaseAcceptedException(Class className, String methodName) {
        super(className, methodName, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION));
    }

    /**
     * @param params
     * @param className
     */
    public RMDCaseAcceptedException(String[] params, Class className) {
        super(className, null, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION));
    }

    /**
     * @param params
     * @param methodName
     */
    public RMDCaseAcceptedException(String[] params, String methodName) {
        super(null, methodName, params, RMDPropertiesLoader.loadProperties(RMDCommonConstants.PROPERTIES_FILE_NAME)
                .getProperty(RMDCommonConstants.CASEACCEPTEDEXCEPTION));
    }

    /**
     * @param errorDetail
     */
    public RMDCaseAcceptedException(ErrorDetail errorDetail) {
        super(errorDetail);
    }
}
