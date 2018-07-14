/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDException.java
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

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Repository;

import com.ge.trans.rmd.exception.RMDServiceException;

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
@Repository
@XmlRootElement(name = "error")
public class OMDException {
    private String code;
    private String message;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @param rmdServiceException
     */
    public OMDException(final RMDServiceException rmdServiceException) {
        this.code = rmdServiceException.getErrorDetail().getErrorCode();
        this.message = rmdServiceException.getErrorDetail().getErrorMessage();
        this.type = rmdServiceException.getErrorDetail().getErrorType();
    }

    /**
     * @param omdApplicationException
     */
    public OMDException(final OMDApplicationException omdApplicationException) {
        this.code = omdApplicationException.getErrorCode();
        this.message = omdApplicationException.getErrorMessage();
        this.type = omdApplicationException.getErrorType();
    }

    /**
     * default constructor asking while starting the server
     */
    public OMDException() {
    }
}
