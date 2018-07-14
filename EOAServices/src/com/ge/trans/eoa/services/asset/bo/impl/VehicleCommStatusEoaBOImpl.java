/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCommStatusBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.bo.impl
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
package com.ge.trans.eoa.services.asset.bo.impl;

import com.ge.trans.eoa.services.asset.bo.intf.VehicleCommStatusEoaBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VehicleCommStatusEoaDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleComStatusInputEoaServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCommServiceResultEoaVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 4, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class VehicleCommStatusEoaBOImpl implements VehicleCommStatusEoaBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(VehicleCommStatusEoaBOImpl.class);
    private VehicleCommStatusEoaDAOIntf objVehCommStatusDAOIntf;

    public VehicleCommStatusEoaBOImpl(final VehicleCommStatusEoaDAOIntf objVehCommStatusDAOIntf) {
        this.objVehCommStatusDAOIntf = objVehCommStatusDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.asset.bo.intf.AssetBOIntf#
     * getVehicleCommStatusDetails(com.ge.trans.rmd.services.cases.service.
     * valueobjects.VehicleComStatusInputServiceVO)
     *//*
       * This Method is used for call the getViewCommStatusDetails method in
       * VehicleCommStatusDAOImpl
       */
    @Override
    public VehicleCommServiceResultEoaVO[] getVehicleCommStatusDetails(
            final VehicleComStatusInputEoaServiceVO objComStatusInputServiceVO) throws RMDBOException {
        VehicleCommServiceResultEoaVO[] objVehicleCommServiceResultVO = null;
        try {
            objVehicleCommServiceResultVO = objVehCommStatusDAOIntf
                    .getVehicleCommStatusDetails(objComStatusInputServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in VehicleCommStatusBOImpl getViewCommStatusDetails", e);
            throw e;
        }
        return objVehicleCommServiceResultVO;
    }
}
