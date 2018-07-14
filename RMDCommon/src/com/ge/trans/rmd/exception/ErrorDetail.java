/**
 * ============================================================
 * File : ErrorDetail.java
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

import java.io.Serializable;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Error detail VO which has error code and error params
 * @History :
 ******************************************************************************/
public class ErrorDetail implements Serializable {

    /**
     * Default serialversionid
     */
    private static final long serialVersionUID = 1L;
    /**
     * Declare an error code
     */
    private String errorCode;

    /**
     * Declare an error Message
     */
    private String errorMessage;

    /**
     * Declare error parameters
     */
    private String[] errorParams;

    /**
     * Declare errorType
     */
    private String errorType;

    /**
     * Declare errorObject
     */
    private Exception objErrMsg;

    /**
     * Constructor for this class
     */
    public ErrorDetail() {
    }

    /**
     * @param errorCode
     */
    public ErrorDetail(String errorCode) {
        this(errorCode, RMDCommonConstants.EMPTY_STRING);
    }

    /**
     * @param errorCode
     * @param params
     */
    public ErrorDetail(String errorCode, String[] params) {
        this.errorCode = errorCode;
        this.errorParams = params;
    }

    /**
     * @param errorCode
     * @param params
     */
    public ErrorDetail(String errorCode, String[] params, String strErrMsg, Exception objErrMsg, String errorType) {
        this.errorCode = errorCode;
        this.errorParams = params;
        this.errorMessage = strErrMsg;
        this.objErrMsg = objErrMsg;
        this.errorType = errorType;
    }

    /**
     * @param errorCode
     * @param params
     */
    public ErrorDetail(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * @param errorCode
     * @param params
     */
    public ErrorDetail(String errorCode, String errorMessage, Exception objErrMsg) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.objErrMsg = objErrMsg;
    }

    /**
     * @Author:
     * @return String
     * @Description: return errorcode
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * @Author:
     * @return String[]
     * @Description: return error parameters
     */
    public String[] getErrorParams() {
        return this.errorParams;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString() Method used for logging the error code
     * and error parameters
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("errorCode = ");
        sb.append(this.errorCode);
        sb.append(", errorParams = ");
        if (this.errorParams == null) {
            sb.append("null");
        } else {
            for (int j = 0; j < this.errorParams.length; ++j) {
                if (j != 0) {
                    sb.append(RMDCommonConstants.COMMMA_SEPARATOR);
                }
                sb.append(this.errorParams[j]);
            }
        }
        return sb.toString();
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Exception getObjErrMsg() {
        return objErrMsg;
    }

    public void setObjErrMsg(Exception objErrMsg) {
        this.objErrMsg = objErrMsg;
    }
}