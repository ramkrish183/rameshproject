/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusServiceIntf.java
 * Description : Service Interface for Vehicle comm status
 *
 * Package : com.ge.trans.rmd.services.asset.service.intf
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
package com.ge.trans.eoa.services.asset.service.intf;

import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Interface for Vehicle comm status
 * @History :
 ******************************************************************************/
public interface VehicleCommStatusEoaServiceIntf {

    /**
     * @param objVehicleCommStatusInputVO
     * @return VehicleCommServiceResultVO
     * @throws RMDServiceException
     */
    VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            VehicleComStatusInputEoaServiceVO objVehicleCommStatusInputVO) throws RMDServiceException;
}
