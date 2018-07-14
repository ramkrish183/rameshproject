/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDNoResultFoundException.java
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
public class OMDNoResultFoundException extends OMDApplicationException {

    private static final long serialVersionUID = 1L;

    /**
     * @param errorCode
     * @param strErrMsg
     */
    public OMDNoResultFoundException(final String errorCode, final String strErrMsg) {
        super(errorCode, strErrMsg);
    }

    public OMDNoResultFoundException(String errorCode) {
        super(errorCode);
    }

}
