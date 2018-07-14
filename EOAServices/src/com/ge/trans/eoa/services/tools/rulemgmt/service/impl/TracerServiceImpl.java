package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.TracerBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.TracerServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class TracerServiceImpl implements TracerServiceIntf {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTracerServiceImpl.class);

    private TracerBOIntf objLoadFaultsBOIntf;

    public TracerServiceImpl(final TracerBOIntf objLoadFaultsBOIntf) {
        this.objLoadFaultsBOIntf = objLoadFaultsBOIntf;
    }

    @Override
    public FaultDataDetailsServiceVO loadFaults(final TracerServiceVO faultServiceVO) throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        objFaultDataDetailsServiceVO = objLoadFaultsBOIntf.loadFaults(faultServiceVO);
        return objFaultDataDetailsServiceVO;
    }

}
