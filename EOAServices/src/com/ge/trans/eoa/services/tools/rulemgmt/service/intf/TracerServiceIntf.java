package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface TracerServiceIntf {

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    FaultDataDetailsServiceVO loadFaults(TracerServiceVO faultServiceVO) throws RMDServiceException;

}
