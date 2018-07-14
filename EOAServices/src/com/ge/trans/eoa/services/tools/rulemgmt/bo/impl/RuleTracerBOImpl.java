package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.impl.RunRecreatorServiceImpl;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public class RuleTracerBOImpl implements RuleTracerBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RunRecreatorServiceImpl.class);

    private RuleTracerDAOIntf objTracerDAOIntf;

    public RuleTracerBOImpl(final RuleTracerDAOIntf objTracerDAOIntf) {
        super();
        this.objTracerDAOIntf = objTracerDAOIntf;
    }

    @Override
    public FaultDataDetailsServiceVO getFaults(final TracerServiceVO objTracerServiceVO) throws RMDBOException {
        LOG.debug("Begin TracerBOImpl searchResult method");
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = objTracerDAOIntf.getFaults(objTracerServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End TracerBOImpl searchResult method");
        return objFaultDataDetailsServiceVO;
    }

    /**
     * Refer the Corresponding method in the Intf
     */
    @Override
    public String getTraceID(final TracerServiceVO tracerServiceVO) throws RMDBOException {
        String traceID = null;
        try {
            if ("Manual_Run".equalsIgnoreCase(tracerServiceVO.getMode())) {
                traceID = objTracerDAOIntf.getTraceIDForMJ(tracerServiceVO);
            }
            if ("Rule_Tester".equalsIgnoreCase(tracerServiceVO.getMode())) {
                traceID = objTracerDAOIntf.getTraceIDForTester(tracerServiceVO);
            }
            if ("Run_Recreator".equalsIgnoreCase(tracerServiceVO.getMode())) {
                traceID = objTracerDAOIntf.getTraceIDForRunRecreator(tracerServiceVO);
            }
            if ("On_the_fly_DS".equalsIgnoreCase(tracerServiceVO.getMode())) {
                traceID = objTracerDAOIntf.getTraceIDForOnTheFlyTrace(tracerServiceVO);
            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return traceID;

    }

}
