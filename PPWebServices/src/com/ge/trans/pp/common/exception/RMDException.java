/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDException.java
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
package com.ge.trans.pp.common.exception;

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
public class RMDException {

    private String code;
    private String message;

    /**
     * This method is used to set proper Error Code
     */
    public RMDException() {
        this.setCode("100");
        this.setMessage("test");
    }

    /**
     * This method is used to set proper Error Code
     * 
     * @param Exception
     */
    public RMDException(final Exception exception) {
        this.setCode("103");
        this.setMessage("test");
    }

    /**
     * This method is used to set proper Error Code
     * 
     * @param RMDServiceException
     */
    public RMDException(final RMDServiceException ex) {

        this.setCode(ex.getErrorDetail().getErrorCode());
        this.setMessage(ex.getErrorDetail().getErrorMessage());
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
    public String getCode() {
        return code;
    }

    /**
     * @param message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
