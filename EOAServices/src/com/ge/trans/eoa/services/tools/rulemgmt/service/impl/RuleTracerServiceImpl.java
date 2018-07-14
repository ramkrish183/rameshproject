package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleTracerServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

public class RuleTracerServiceImpl implements RuleTracerServiceIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RunRecreatorServiceImpl.class);

    private RuleTracerBOIntf objTracerBOIntf;

    public RuleTracerServiceImpl(final RuleTracerBOIntf objTracerBOIntf) {
        this.objTracerBOIntf = objTracerBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf
     * #searchResult(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTracerSeachCriteriaServiceVO) This Method is used to
     * call the searchResult method in RuleTracerBOImpl
     */
    @Override
    public FaultDataDetailsServiceVO getFaults(final TracerServiceVO objTracerServiceVO) throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = objTracerBOIntf.getFaults(objTracerServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objFaultDataDetailsServiceVO;
    }

    /**
     * Refer the Corresponding method in the Intf
     */
    @Override
    public void getTraceID(final TracerServiceVO tracerServiceVO) throws RMDServiceException {
        String traceID = null;
        try {
            traceID = objTracerBOIntf.getTraceID(tracerServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, tracerServiceVO.getStrLanguage());
        }
        tracerServiceVO.setStrTraceID(traceID);
        // return tracerServiceVO;
    }
}
