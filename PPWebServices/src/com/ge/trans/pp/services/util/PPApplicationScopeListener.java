/**
 * ============================================================
 * File : RMDApplicationScopeListener.java
 * Description :
 * Package : com.ge.trans.rmd.services.util
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 18, 2010
 * History
 * Modified By : Initial Release
 * Copyright (C) 2010 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.pp.services.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

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
public class PPApplicationScopeListener implements ServletContextListener {

    final private RMDLogger LOG = RMDLoggerHelper.getLogger(PPApplicationScopeListener.class);

    public PPApplicationScopeListener() {
        super();
    }

    /**
     * @Author:
     * @param event
     */
    public void contextDestroyed(final ServletContextEvent event) {
    }

    /**
     * @Author:
     * @param event
     */
    public void contextInitialized(final ServletContextEvent event) {
        try {
            final ServletContext applicationContext = event.getServletContext();
            final String dataSourceJNDIName = applicationContext
                    .getInitParameter(RMDCommonConstants.PP_DATASOURCE_JNDI_NAME);

            System.setProperty(RMDCommonConstants.PP_DATASOURCE_JNDI_NAME, dataSourceJNDIName);

            LOG.debug("Success!!!!! PP Web Application Deployed Successfully");
            LOG.debug("============================================");
        } catch (Exception excep) {
            LOG.error("Error occured in PPApplicationScopeListener - contextInitialized method" + excep);
        }
    }
}
