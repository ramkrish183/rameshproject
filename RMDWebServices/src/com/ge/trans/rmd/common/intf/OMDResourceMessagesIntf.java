/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseAPIResourceMessagesIntf.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.caseapi.services.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Jul 15, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.intf;

import java.util.Locale;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 15, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface OMDResourceMessagesIntf {

    /**
     * @Author:
     * @param errorCode
     * @param params
     * @param locale
     * @return
     * @Description:
     */
    String getMessage(String errorCode, Object[] params, Locale locale);
}