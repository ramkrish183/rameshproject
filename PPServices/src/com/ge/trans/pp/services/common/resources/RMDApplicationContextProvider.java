/**
 * ============================================================
 * File : ApplicationContextProvider.java
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
package com.ge.trans.pp.services.common.resources;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;

import org.springframework.context.ApplicationContextAware;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class provides an application-wide access to the Spring
 *              ApplicationContext. The ApplicationContext is injected in a
 *              static method of the class "AppContext". Use
 *              AppContext.getApplicationContext() to get access to all Spring
 *              Beans.
 * @History :
 ******************************************************************************/
public class RMDApplicationContextProvider implements ApplicationContextAware {

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.context.ApplicationContextAware#setApplicationContext
     * (org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(final ApplicationContext ctx) throws BeansException {
        // Wiring the ApplicationContext into a static method
        RMDApplicationContext.setApplicationContext(ctx);
    }
}
