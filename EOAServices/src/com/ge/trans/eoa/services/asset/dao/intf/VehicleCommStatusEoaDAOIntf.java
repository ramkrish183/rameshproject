/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusDAOIntf.java
 * Description : DAO Interface for Vehicle Comm Status
 *
 * Package : com.ge.trans.rmd.services.asset.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 4, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.dao.intf;

import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : DAO Interface for Vehicle Comm Status
 * @History :
 ******************************************************************************/
public interface VehicleCommStatusEoaDAOIntf {

    /**
     * @Author:
     * @param objVehicleCommStatusInputVO
     * @return VehicleCommServiceResultVO
     * @throws RMDServiceException
     */
    VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            VehicleComStatusInputEoaServiceVO objVehicleCommStatusInputVO) throws RMDDAOException;
}
