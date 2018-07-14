package com.ge.trans.pp.common.aop.performance;

import org.aspectj.lang.ProceedingJoinPoint;

import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class PPWSAroundMethodHandler {
    private static RMDLogger log = RMDLoggerHelper.getLogger(PPWSAroundMethodHandler.class);

    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // System.out.println("*** RWS :: Before ::"+ joinPoint.getStaticPart()
        // + " at " + startTime);
        log.info("*** PPWS :: Begin ::" + joinPoint.getTarget().getClass().getName() + "."
                + joinPoint.getSignature().getName() + "()" + " at " + startTime);
        // log.debug("*** RWS :: Before ::"+ joinPoint.getStaticPart() + " at "
        // + startTime);
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            // System.out.println("### RWS :: After "+
            // joinPoint.getStaticPart()+ " took ("+(endTime-startTime)+"ms)
            // which started at " + startTime);
            // log.debug("### RWS :: After "+ joinPoint.getStaticPart()+ " took
            // ("+(endTime-startTime)+"ms) which started at " + startTime);
            log.info("### PPWS :: After " + joinPoint.getTarget().getClass().getName() + "."
                    + joinPoint.getSignature().getName() + "() took (" + (endTime - startTime)
                    + " ms) which started at " + startTime);
        }
        return proceed;
    }
}