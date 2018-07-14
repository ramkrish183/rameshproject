/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusServiceImpl.java
 * Description : Service Implementation for Vehicle comm status
 *
 * Package : com.ge.trans.rmd.services.asset.service.impl
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
package com.ge.trans.eoa.services.asset.service.impl;

import java.io.Serializable;

import com.ge.trans.eoa.services.asset.bo.intf.VehicleCommStatusEoaBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.VehicleCommStatusEoaServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Implementation for Vehicle comm status
 * @History :
 ******************************************************************************/
public class VehicleCommStatusEoaServiceImpl implements Serializable, VehicleCommStatusEoaServiceIntf {

    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(VehicleCommStatusEoaServiceImpl.class);
    private VehicleCommStatusEoaBOIntf objVehCommStatusBOIntf;

    /**
     * @param objVehCommStatusServiceIntf
     */
    public VehicleCommStatusEoaServiceImpl(VehicleCommStatusEoaBOIntf objVehCommStatusBOIntf) {
        this.objVehCommStatusBOIntf = objVehCommStatusBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.asset.service.intf.AssetServiceIntf#
     * getVehicleCommStatusDetails(com.ge.trans.rmd.services.cases.service.
     * valueobjects.VehicleComStatusInputServiceVO)
     *//*
       * This Method is used for call the getVehicleCommStatusDetails method in
       * VehicleCommStatusBOImpl
       */
    @Override
    public VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            VehicleComStatusInputEoaServiceVO objComStatusInputServiceVO) throws RMDServiceException {
        VehicleCommServiceResultEoaVO[] objVehicleCommServiceResultVO = null;
        try {
            objVehicleCommServiceResultVO = objVehCommStatusBOIntf
                    .getVehicleCommStatusDetails(objComStatusInputServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexpected Error occured in VehicleCommStatusServiceImpl getVehicleCommStatusDetails", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objComStatusInputServiceVO.getStrLanguage());
        }
        return objVehicleCommServiceResultVO;
    }
}
