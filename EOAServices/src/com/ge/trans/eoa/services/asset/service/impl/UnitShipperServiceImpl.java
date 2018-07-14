/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipperServiceImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.service.impl;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.UnitShipperBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.UnitShipperServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.UnitShippersVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ShipUnitVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class UnitShipperServiceImpl implements UnitShipperServiceIntf {

    private UnitShipperBOIntf objUnitShipperBOIntf;

    /**
     * @param objHealthCheckRequestBOIntf
     */
    public UnitShipperServiceImpl(UnitShipperBOIntf objUnitShipperBOIntf) {
        this.objUnitShipperBOIntf = objUnitShipperBOIntf;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO
     *            objDetailsVO
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units to be shipped.
     */

    @Override
    public List<UnitShippersVO> getUnitsToBeShipped(UnitShipDetailsVO objDetailsVO) throws RMDServiceException {
        List<UnitShippersVO> arlUnitsTobeShipped = new ArrayList<UnitShippersVO>();
        try {
            arlUnitsTobeShipped = objUnitShipperBOIntf.getUnitsToBeShipped(objDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlUnitsTobeShipped;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO
     *            objDetailsVO
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units which are already
     *               Shipped.
     */
    @Override
    public List<UnitShippersVO> getLastShippedUnits(UnitShipDetailsVO objDetailsVO) throws RMDServiceException {
        List<UnitShippersVO> arlLastShippedUnits = new ArrayList<UnitShippersVO>();
        try {
            arlLastShippedUnits = objUnitShipperBOIntf.getLastShippedUnits(objDetailsVO);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return arlLastShippedUnits;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<UnitShippersVO>
     *            arlShipperUnits
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units to be shipped.
     */
    @Override
    public String updateUnitsToBeShipped(List<UnitShippersVO> arlShipperUnits) throws RMDServiceException {
        String unitShipppedStatus = null;
        try {
            unitShipppedStatus = objUnitShipperBOIntf.updateUnitsToBeShipped(arlShipperUnits);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return unitShipppedStatus;
    }
    /**
     * @Author :Mohamed
     * @return :String
     * @param :List<UnitShippersVO>
     *            arlShipperUnits
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of ASSET NUMBER which yrt to
     *               be shipped.
     * 
     */
    @Override
    public List<AssetNumberVO> getAssetNumbersForShipUnits(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException {
        List<AssetNumberVO> arlAsset = null;
        try {
            arlAsset = objUnitShipperBOIntf.getAssetNumbersForShipUnits(objAssetServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objAssetServiceVO.getStrLanguage());
        }
        return arlAsset;
    }

	@Override
	public List<ShipUnitVO> getShipUnitDetails() throws RMDServiceException {
		List<ShipUnitVO> arlAsset = null;
        try {
            arlAsset = objUnitShipperBOIntf.getShipUnitDetails();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
        return arlAsset;
	}

	@Override
	public List<ShipUnitVO> getInfancyFailureDetails()
			throws RMDServiceException {
		List<ShipUnitVO> arlAsset = null;
        try {
            arlAsset = objUnitShipperBOIntf.getInfancyFailureDetails();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "en");
        }
        return arlAsset;
	}
}
