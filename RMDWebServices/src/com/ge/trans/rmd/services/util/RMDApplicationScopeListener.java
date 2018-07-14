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
package com.ge.trans.rmd.services.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
public class RMDApplicationScopeListener implements ServletContextListener {

    final private RMDLogger LOG = RMDLoggerHelper.getLogger(RMDApplicationScopeListener.class);

    // @Value("${"+RMDCommonConstants.RMD_DATASOURCE_JNDI_NAME+"}") String
    // dataSourceJNDIName;
    public RMDApplicationScopeListener() {
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

    }
}
