/**
 * ============================================================
 * File : RMDApplicationContext.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.utilities
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 4, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.common.resources;

import org.springframework.context.ApplicationContext;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public final class RMDApplicationContext {

    private static ApplicationContext ctx;

    /**
     * private constructor
     */
    private RMDApplicationContext() {
    }

    /**
     * @Author:
     * @param applicationContext
     * @Description: Injected from the class "ApplicationContextProvider" which
     *               is automatically. loaded during Spring-Initialization.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * @Author:
     * @return
     * @Description:Get access to the Spring ApplicationContext from everywhere
     *                  in your Application.
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}