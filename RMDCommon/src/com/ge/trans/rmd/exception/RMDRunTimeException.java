/**
 * ============================================================
 * File : RMDRunTimeException.java
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

import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Base class for all RMD exceptions
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RMDRunTimeException extends RuntimeException {

    /**
     * serialversionid of this class
     */
    private static final long serialVersionUID = 1L;
    /**
     * Cause of exception
     */
    private Throwable cause;
    /**
     * Declare error details for adding error code and error params
     */
    private ErrorDetail errorDetail;

    /**
     * Constructor of RMDRunTimeException
     */
    public RMDRunTimeException() {
        super();
    }

    /**
     * @param msg
     */
    public RMDRunTimeException(String msg) {
        super(msg);
    }

    /**
     * @param cause
     */
    public RMDRunTimeException(Throwable cause) {
        this(cause, null);
    }

    /**
     * @param cause
     * @param msg
     */
    public RMDRunTimeException(Throwable cause, String msg) {
        super(msg);
        this.cause = cause;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#printStackTrace()
     */
    @Override
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
     */
    @Override
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        if (this.cause != null) {
            printStream.println("Caused by:");
            this.cause.printStackTrace(printStream);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
     */
    @Override
    public void printStackTrace(PrintWriter printStream) {
        super.printStackTrace(printStream);
        if (this.cause != null) {
            printStream.println("Caused by:");
            this.cause.printStackTrace(printStream);
        }
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDRunTimeException(String errorCode, String errorMessage) {
        super("Error Code is ::- " + errorCode);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, errorMessage);
        this.setErrorDetail(errorDetailVO);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDRunTimeException(Class className, String methodName, String[] params, String errorCode) {
        super("Error Code is ::- " + errorCode);
        Logger logger = Logger.getLogger(className);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, params);
        logger.error(ExceptionHelper.parseClassName(className.getName(), methodName) + errorDetailVO.toString());
        this.setErrorDetail(errorDetailVO);
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDRunTimeException(Class className, String methodName, String errorCode) {
        super("Error Code is ::- " + errorCode);
        Logger logger = Logger.getLogger(className);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, RMDCommonConstants.EMPTY_STRING);
        logger.error("RMDRunTime Exception Occured ::- "
                + ExceptionHelper.parseClassName(className.getName(), methodName) + errorDetailVO.toString());
        this.setErrorDetail(errorDetailVO);
    }

    /**
     * @param errorCode
     * @param params
     */
    public RMDRunTimeException(String errorCode, String[] params) {
        super("Error Code is ::- " + errorCode);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, params);
        this.setErrorDetail(errorDetailVO);
    }

    /**
     * @param errorCode
     * @param params
     */
    public RMDRunTimeException(String errorCode, String[] params, String strErrMsg, Exception objErrMsg,
            String errorType) {
        super("Error Code is ::- " + errorCode);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, params, strErrMsg, objErrMsg, errorType);
        this.setErrorDetail(errorDetailVO);
    }

    /**
     * @Author:
     * @param errorDetail
     * @Description: Set errordetail object
     */
    public final void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    /**
     * @Author:
     * @return ErrorDetail
     * @Description: Get errordetail object
     */
    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    /**
     * @param errorDetail
     */
    public RMDRunTimeException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDRunTimeException(String errorCode, String errorMessage, Exception objErrMsg) {
        super("Error Code is ::- " + errorCode);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, errorMessage);
        errorDetailVO.setObjErrMsg(objErrMsg);
        this.setErrorDetail(errorDetailVO);

    }
}