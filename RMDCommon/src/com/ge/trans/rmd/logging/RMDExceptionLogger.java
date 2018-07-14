/**
 * ============================================================
 * File : RMDExceptionLogger.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.logging
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
package com.ge.trans.rmd.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDException;
import com.ge.trans.rmd.exception.RMDRunTimeException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDExceptionLogger implements ThrowsAdvice {

    public RMDExceptionLogger() {
        super();
    }

    private static RMDLogger log = null;

    /**
     * @Author:
     * @param method
     * @param object
     * @param target
     * @param exception
     * @throws Throwable
     * @Description:
     */
    public void afterThrowing(final Method method, final Object[] object, final Object target,
            final Throwable exception) throws Throwable {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Exception occured in the method ");
        strBuilder.append(method.getName());
        final String newLine = System.getProperty("line.separator");
        strBuilder.append(newLine);
        strBuilder.append("Exception is :: ");
        strBuilder.append(exception.getMessage());
        log = RMDLoggerHelper.getLogger(target.getClass());
        log.error(String.valueOf(strBuilder));
        log.error("Exception occured in class :: " + exception.getStackTrace()[0].getClassName());
        log.error("Exception occured in file  :: " + exception.getStackTrace()[0].getFileName());
        log.error("Exception occured in method:: " + exception.getStackTrace()[0].getMethodName());
        log.error("Exception occured in Line  :: " + exception.getStackTrace()[0].getLineNumber());
        final StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        if (exception != null) {
            exception.printStackTrace(pWriter);
        }
        ErrorDetail errorDetail = null;
        Throwable exceptionDetails = null;
        if (exception instanceof RMDException) {
            RMDException exp = (RMDException) exception;
            errorDetail = exp.getErrorDetail();
            exceptionDetails = errorDetail.getObjErrMsg();
        } else if (exception instanceof RMDRunTimeException) {
            RMDRunTimeException ee = (RMDRunTimeException) exception;
            errorDetail = ee.getErrorDetail();
            exceptionDetails = errorDetail.getObjErrMsg();
        } else {
            exceptionDetails = exception;
        }
        if (exceptionDetails != null) {
            exceptionDetails.printStackTrace(pWriter);
        }
        log.error("Exception Details :: " + sWriter.getBuffer().toString());
        pWriter.close();
        pWriter = null;
    }
}
