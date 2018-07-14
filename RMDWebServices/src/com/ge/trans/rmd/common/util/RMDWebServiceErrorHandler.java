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
package com.ge.trans.rmd.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDWebServiceErrorHandler {

    /**
     * @Author:
     * @param e
     * @param language
     * @Description:
     */
    public static void handleException(Exception e, OMDResourceMessagesIntf omdResourceMessagesIntf)
            throws OMDApplicationException {
        String errorCode = null;
        String errorMessage=null;

        if (e instanceof RMDServiceException) {
            RMDServiceException rms = (RMDServiceException) e;
            ErrorDetail errDetails = rms.getErrorDetail();
            if(errDetails != null){
	            throw new OMDApplicationException(errDetails.getErrorCode(), errDetails.getErrorMessage(),
	                    errDetails.getErrorType());
            }

        } else if (e instanceof OMDInValidInputException) {
            OMDInValidInputException inv = (OMDInValidInputException) e;
            errorCode = inv.getErrorCode();
            errorMessage=inv.getErrorMessage();
            if(errorCode.equalsIgnoreCase(OMDConstants.NON_ROR_RULES_ERROR)){
            	throw new OMDApplicationException(BeanUtility.getErrorCode(errorCode),
                        errorMessage,
                        OMDConstants.MINOR_ERROR);
            }else{
            throw new OMDApplicationException(BeanUtility.getErrorCode(errorCode),
                    omdResourceMessagesIntf.getMessage(BeanUtility.getErrorCode(errorCode), new String[] {},
                            BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
                    OMDConstants.MINOR_ERROR);
            }

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