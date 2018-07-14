package com.ge.trans.pp.common.aop.performance;

import org.aspectj.lang.ProceedingJoinPoint;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class PPWSOtherAroundMethodHandler {
    private static RMDLogger log = RMDLoggerHelper.getLogger(PPWSOtherAroundMethodHandler.class);

    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("***PPWS::Other::Begin:: " + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + "()" + " at " + startTime);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("###PPWS::Other::End:: " + joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "()" + " took (" + (endTime - startTime)
                    + " ms) which started at " + startTime);
        }
        return proceed;
    }
}