/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDServiceErrorHandler.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.common.utils;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2008 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.pp.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDApplicationException;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.exception.OMDNoResultFoundException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.util.BeanUtility;

import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDServiceException;

public class PPRMDWebServiceErrorHandler {

    /**
     * @throws OMDApplicationException
     *             This method is added for handling the all exceptions
     *             occurring in RMDWebServices.
     * @param Exception,OMDResourceMessagesIntf
     * @return void
     */
    public static void handleException(Exception e, OMDResourceMessagesIntf omdResourceMessagesIntf)
            throws OMDApplicationException {
        String errorCode = null;

        if (e instanceof RMDServiceException) {
            RMDServiceException rms = (RMDServiceException) e;
            ErrorDetail errDetails = rms.getErrorDetail();

            throw new OMDApplicationException(errDetails.getErrorCode(), errDetails.getErrorMessage(),
                    errDetails.getErrorType());

        } else if (e instanceof OMDInValidInputException) {
            OMDInValidInputException inv = (OMDInValidInputException) e;
            errorCode = inv.getErrorCode();
            throw new OMDApplicationException(BeanUtility.getErrorCode(errorCode),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(errorCode), new String[] {},
                            BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    OMDConstants.MINOR_ERROR);

        } else if (e instanceof OMDNoResultFoundException) {
            OMDNoResultFoundException nrs = (OMDNoResultFoundException) e;
            errorCode = nrs.getErrorCode();

            throw new OMDApplicationException(BeanUtility.getErrorCode(errorCode),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(errorCode), new String[] {},
                            BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    OMDConstants.MINOR_ERROR);

        } else if (e instanceof FileNotFoundException || e instanceof IllegalAccessException
                || e instanceof InvocationTargetException || e instanceof NoSuchMethodException
                || e instanceof IOException || e instanceof ClassNotFoundException) {

            throw new OMDApplicationException(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    OMDConstants.MAJOR_ERROR);

        }
    }

}
