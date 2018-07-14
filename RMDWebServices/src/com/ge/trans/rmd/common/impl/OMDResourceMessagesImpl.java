/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDResourceMessagesImpl.java
 * Description : 
 * Package : com.ge.trans.omd.services.common.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : aug 18 2011
 * History
 * Modified By : Initial Release
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@Service
public class OMDResourceMessagesImpl implements OMDResourceMessagesIntf {
    @Autowired
    private transient MessageSource messageSourceImpl;

    /**
     * @param messageSource
     */
    public void setMessageSource(final MessageSource messageSource) {
        this.messageSourceImpl = messageSource;
    }

    /**
     * @param errorCode
     * @param params
     * @param locale
     * @return String
     */
    public String getMessage(final String errorCode, final Object[] params, final Locale locale) {
        return messageSourceImpl.getMessage(errorCode, params, locale);
    }
}
