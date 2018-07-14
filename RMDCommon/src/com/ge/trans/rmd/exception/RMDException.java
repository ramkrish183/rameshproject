/**
 * ============================================================
 * File : RMDException.java
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
 * @Description : Base class for all Exceptions
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public abstract class RMDException extends Exception {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;
    /**
     * Declare throwable
     */
    private Throwable cause;
    /**
     * Declare ErrorDetail
     */
    private ErrorDetail errorDetail;

    /**
     * @Author:
     * @param errorDetail
     * @Description: set errordetail object
     */
    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    /**
     * @Author:
     * @return ErrorDetail
     * @Description: get errordetail object
     */
    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    /**
     * Constructor of RMDException
     */
    public RMDException() {
        super();
    }

    /**
     * @param msg
     */
    public RMDException(String msg) {
        super(msg);
    }

    /**
     * @param cause
     */
    public RMDException(Throwable cause) {
        this(cause, null);
    }

    /**
     * @param cause
     * @param msg
     */
    public RMDException(Throwable cause, String msg) {
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
    public void printStackTrace(PrintStream p) {
        super.printStackTrace(p);
        if (this.cause != null) {
            p.println("Caused by:");
            this.cause.printStackTrace(p);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
     */
    @Override
    public void printStackTrace(PrintWriter p) {
        super.printStackTrace(p);
        if (this.cause == null) {
            return;
        }
        p.println("Caused by:");
        this.cause.printStackTrace(p);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDException(Class className, String methodName, String[] params, String errorCode) {
        super("Error Code is ::- " + errorCode);
        Logger logger = Logger.getLogger(className);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, params);
        logger.error("RMD Exception Occured ::- " + ExceptionHelper.parseClassName(className.getName(), methodName)
                + errorDetailVO.toString());
        this.errorDetail = errorDetailVO;
    }

    /**
     * @param className
     * @param methodName
     * @param errorCode
     */
    public RMDException(Class className, String methodName, String errorCode) {
        super("Error Code is ::- " + errorCode);
        Logger logger = Logger.getLogger(className);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, RMDCommonConstants.EMPTY_STRING);
        logger.error("RMD Exception Occured ::- " + ExceptionHelper.parseClassName(className.getName(), methodName)
                + errorDetailVO.toString());
        this.errorDetail = errorDetailVO;
    }

    /**
     * @param errorCode
     * @param params
     */
    public RMDException(String errorCode, String[] params) {
        super("Error Code is ::- " + errorCode);
        ErrorDetail errorDetailVO = new ErrorDetail(errorCode, params);
        this.errorDetail = errorDetailVO;
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDException(String errorCode, String errorMessage) {
        super("Error Code is ::- " + errorCode);
        this.errorDetail = new ErrorDetail(errorCode, errorMessage);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDException(String errorCode, String[] errorParams, String strErrMsg, Exception objErrMsg,
            String errorType) {
        super("Error Code is ::- " + errorCode);
        this.errorDetail = new ErrorDetail(errorCode, errorParams, strErrMsg, objErrMsg, errorType);
    }

    /**
     * @param className
     * @param methodName
     * @param params
     * @param errorCode
     */
    public RMDException(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    public RMDException(ErrorDetail errorDetail, Exception objErrMsg) {
        this.errorDetail = errorDetail;
        this.errorDetail.setObjErrMsg(objErrMsg);
    }

    public RMDException(String errorCode, String errorMessage, Exception objErrMsg) {
        super("Error Code is ::- " + errorCode);
        this.errorDetail = new ErrorDetail(errorCode, errorMessage, objErrMsg);
    }
}
