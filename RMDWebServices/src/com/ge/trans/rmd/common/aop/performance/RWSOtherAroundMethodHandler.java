package com.ge.trans.rmd.common.aop.performance;

import org.aspectj.lang.ProceedingJoinPoint;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RWSOtherAroundMethodHandler {
    private static RMDLogger log = RMDLoggerHelper.getLogger(RWSOtherAroundMethodHandler.class);

    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("***RWS::Other::Begin:: " + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + "()" + " at " + startTime);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("###RWS::Other::End:: " + joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "()" + " took (" + (endTime - startTime)
                    + " ms) which started at " + startTime);
        }
        return proceed;
    }
}