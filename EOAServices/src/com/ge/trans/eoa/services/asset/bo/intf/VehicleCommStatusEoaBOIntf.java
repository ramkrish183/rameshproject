/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusBOIntf.java
 * Description : BO Interface for Vehicle comm status
 *
 * Package : com.ge.trans.rmd.services.asset.bo.intf
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
package com.ge.trans.eoa.services.asset.bo.intf;

import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Interface for Vehicle comm status
 * @History :
 ******************************************************************************/
public interface VehicleCommStatusEoaBOIntf {

    /**
     * @Author:
     * @param objVehicleCommStatusInputVO
     * @return VehicleCommServiceResultVO
     * @throws RMDServiceException
     */
    VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            VehicleComStatusInputEoaServiceVO objVehicleCommStatusInputVO) throws RMDBOException;
}
