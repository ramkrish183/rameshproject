package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public interface RuleTracerBOIntf {

    FaultDataDetailsServiceVO getFaults(TracerServiceVO objTracerServiceVO) throws RMDBOException;

    /**
     * @param tracerVO
     * @Description: This methos is used to get the Tracer Id
     */
    String getTraceID(TracerServiceVO tracerServiceVO) throws RMDBOException;

}
