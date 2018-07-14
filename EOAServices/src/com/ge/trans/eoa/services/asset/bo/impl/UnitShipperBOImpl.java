/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipperBOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.bo.impl;
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.UnitShipperBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.UnitShipperDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.UnitShippersVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ShipUnitVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class UnitShipperBOImpl implements UnitShipperBOIntf {

    private UnitShipperDAOIntf objUnitShipperDAOIntf;

    /**
     * @param objHealthCheckRequestBOIntf
     */
    public UnitShipperBOImpl(UnitShipperDAOIntf objUnitShipperDAOIntf) {
        this.objUnitShipperDAOIntf = objUnitShipperDAOIntf;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */
    @Override
    public List<UnitShippersVO> getUnitsToBeShipped(
            UnitShipDetailsVO objDetailsVO) throws RMDBOException {
        List<UnitShippersVO> arlUnitsTobeShipped = new ArrayList<UnitShippersVO>();
        try {
            arlUnitsTobeShipped = objUnitShipperDAOIntf
                    .getUnitsToBeShipped(objDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlUnitsTobeShipped;
    }

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of Units which are already
     *               Shipped.
     * 
     */
    @Override
    public List<UnitShippersVO> getLastShippedUnits(
            UnitShipDetailsVO objDetailsVO) throws RMDBOException {
        List<UnitShippersVO> arlLastShippedUnits = new ArrayList<UnitShippersVO>();
        try {
            arlLastShippedUnits = objUnitShipperDAOIntf
                    .getLastShippedUnits(objDetailsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return arlLastShippedUnits;
    }

    /**
     * @Author :
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDBOException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */
    @Override
    public String updateUnitsToBeShipped(List<UnitShippersVO> arlShipperUnits)
            throws RMDBOException {
        String unitShipppedStatus = null;
        try {
            unitShipppedStatus = objUnitShipperDAOIntf
                    .updateUnitsToBeShipped(arlShipperUnits);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return unitShipppedStatus;
    }

    /**
     * @Author :Mohamed
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of ASSET NUMBER which yrt to be shipped.
     * 
     */
    @Override
    public List<AssetNumberVO> getAssetNumbersForShipUnits(
            final AssetServiceVO objAssetServiceVO) throws RMDBOException {
        List<AssetNumberVO> arlAsset = null;

        try {
            /* SETTING PRODUCTlST AND CUSTOMERlIST AND PASSING TO DAO */

            /* Enters if block if product asset config is available */
            arlAsset = objUnitShipperDAOIntf
                    .getAssetNumbersForShipUnits(objAssetServiceVO);

        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility
            .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS);
            throw new RMDDAOException(errorCode, new String[] {},
            RMDCommonUtility.getMessage(errorCode, new String[] {},
            objAssetServiceVO.getStrLanguage()), exc,
            RMDCommonConstants.MINOR_ERROR);
        }

        return arlAsset;
    }

	@Override
	public List<ShipUnitVO> getShipUnitDetails() throws RMDBOException {
		List<ShipUnitVO> arlAsset = null;

        try {
            arlAsset = objUnitShipperDAOIntf
                    .getShipUnitDetails();

        } catch (RMDDAOException ex) {
        	 throw new RMDBOException(ex.getErrorDetail(), ex);
        } 
        return arlAsset;
	}

	@Override
	public List<ShipUnitVO> getInfancyFailureDetails() throws RMDBOException {
		List<ShipUnitVO> arlAsset = null;

        try {
            arlAsset = objUnitShipperDAOIntf
                    .getInfancyFailureDetails();

        } catch (RMDDAOException ex) {
        	 throw new RMDBOException(ex.getErrorDetail(), ex);
        } 
        return arlAsset;
	}
}
