/**
 * =================================================================
 * Classification: GE Confidential
 * File : RolesDAOIntf.java
 * Description : DAO Interface for Vehicle Faults
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleFaultEoaServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Interface for Vehicle Faults
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface VehicleFaultEoaDAOIntf {

    /**
     * @Author:
     * @param strFaultCode
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getFaultCode(String strFaultCode, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    void getTotalCount(VehicleFaultEoaServiceVO objVehicleFaultServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getHeaderGroup(String strLanguage) throws RMDDAOException;

    /**
     * @param strCustomer
     * @param strModelId
     * @return
     */
    public ArrayList<String> getCustLevelDetails(String strCustomer, String strModelId);

    public ArrayList<String> getCustLevelDPEABDetails(String strCustomer, String strModelId);

    public FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception;
}