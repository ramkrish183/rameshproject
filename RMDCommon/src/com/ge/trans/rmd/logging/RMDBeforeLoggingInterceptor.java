/**
 * ============================================================
 * File : RMDBeforeLoggingInterceptor.java
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

import org.springframework.aop.MethodBeforeAdvice;

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
public class RMDBeforeLoggingInterceptor implements MethodBeforeAdvice {

    private static RMDLogger log = null;

    /*
     * (non-Javadoc)
     * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.
     * Method, java.lang.Object[], java.lang.Object)
     */
    @Override
    public void before(final Method method, final Object[] object, final Object target) throws Throwable {
        log = RMDLoggerHelper.getLogger(method.getDeclaringClass());
        log.debug("Entering method " + method.getName());
    }
}
