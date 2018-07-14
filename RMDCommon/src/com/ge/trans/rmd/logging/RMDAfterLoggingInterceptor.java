/**
 * ============================================================
 * File : RMDAfterLoggingInterceptor.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.logging
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.logging;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDAfterLoggingInterceptor implements AfterReturningAdvice {

    private static RMDLogger log = null;

    /**
     * 
     */
    public RMDAfterLoggingInterceptor() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.
     * Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
     */
    @Override
    public void afterReturning(final Object source, final Method method, final Object[] object, final Object target)
            throws Throwable {
        log = RMDLoggerHelper.getLogger(method.getDeclaringClass());
        log.debug("Ending method " + method.getName());
    }
}
