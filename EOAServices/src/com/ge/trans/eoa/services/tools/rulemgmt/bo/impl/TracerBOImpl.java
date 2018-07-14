package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.TracerBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.TracerDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.impl.RuleTracerServiceImpl;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class TracerBOImpl implements TracerBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTracerServiceImpl.class);

    private TracerDAOIntf objLoadFaultsDAOIntf;

    public TracerBOImpl(final TracerDAOIntf objLoadFaultsDAOIntf) {
        super();
        this.objLoadFaultsDAOIntf = objLoadFaultsDAOIntf;
    }

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    @Override
    public FaultDataDetailsServiceVO loadFaults(final TracerServiceVO faultServiceVO) {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO;
        objFaultDataDetailsServiceVO = objLoadFaultsDAOIntf.loadFaults(faultServiceVO);
        return objFaultDataDetailsServiceVO;
    }

}
