package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public interface RuleTracerServiceIntf {

    FaultDataDetailsServiceVO getFaults(TracerServiceVO objServiceVO) throws RMDServiceException;

    /**
     * @param tracerVO
     * @throws RMDServiceException
     * @Description: This method is used to get the Tracer Id
     */
    void getTraceID(TracerServiceVO objTracerServiceVO) throws RMDServiceException;;

}
