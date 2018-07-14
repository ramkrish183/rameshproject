package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.TracerServiceVO;

public interface RuleTracerDAOIntf {

    public FaultDataDetailsServiceVO getFaults(TracerServiceVO faultServiceVO);

    /**
     * @param tracerVO
     * @Description: This methos is used to get the Tracer Id for the Manual
     *               JDPAD ID
     */
    String getTraceIDForMJ(TracerServiceVO tracerServiceVO) throws RMDDAOException;

    /**
     * @param tracerVO
     * @Description: This methos is used to get the Tracer Id for the Tester ID
     */
    String getTraceIDForTester(TracerServiceVO tracerServiceVO) throws RMDDAOException;

    /**
     * @param tracerVO
     * @Description: This methos is used to get the Tracer Id for the Run
     *               Recreator ID
     */
    String getTraceIDForRunRecreator(TracerServiceVO tracerServiceVO) throws RMDDAOException;

    /**
     * @param tracerVO
     * @Description: This methos is used to get the Tracer Id for the Case ID
     */
    String getTraceIDForOnTheFlyTrace(TracerServiceVO tracerServiceVO) throws RMDDAOException;

}
