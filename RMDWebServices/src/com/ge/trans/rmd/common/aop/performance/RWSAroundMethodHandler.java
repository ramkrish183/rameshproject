package com.ge.trans.rmd.common.aop.performance;

import org.aspectj.lang.ProceedingJoinPoint;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class RWSAroundMethodHandler {
    private static RMDLogger log = RMDLoggerHelper.getLogger(RWSAroundMethodHandler.class);

    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // System.out.println("*** RWS :: Before ::"+
        // joinPoint.getTarget().getClass().getName() + " at " + startTime);
        log.info("*** RWS :: Begin ::" + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + "()" + " at " + startTime);
        // log.debug("*** RWS :: Before ::"+
        // joinPoint.getTarget().getClass().getName() + " at " + startTime);
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            // System.out.println("### RWS :: After "+
            // joinPoint.getTarget().getClass().getName()+ " took
            // ("+(endTime-startTime)+"ms) which started at " + startTime);
            // log.debug("### RWS :: After "+
            // joinPoint.getTarget().getClass().getName()+ " took
            // ("+(endTime-startTime)+"ms) which started at " + startTime);
            log.info("### RWS :: After " + joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "() took (" + (endTime - startTime)
                    + " ms) which started at " + startTime);
        }
        return proceed;
    }
}