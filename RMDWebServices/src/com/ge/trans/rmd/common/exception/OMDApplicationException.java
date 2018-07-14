/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDApplicationException.java
 * Description :
 * Package : com.ge.trans.rmd.services.exception
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.exception;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class OMDApplicationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String errorMessage;
    private String errorType;

    /**
     * @param errorCode
     * @param errorMessage
     */
    public OMDApplicationException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public OMDApplicationException(String errorCode, String errorMessage, String errorType) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public OMDApplicationException(String errorCode) {
        this.errorCode = errorCode;

    }

    public OMDApplicationException(String errorCode, String errorMessage, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * Default constructor
     */
    public OMDApplicationException() {
        super();
    }

    /**
     * @return String
     */
    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    /**
     * @param errorCode
     */
    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return String
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     */
    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
