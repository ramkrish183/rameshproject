/**
 * ============================================================
 * Classification: GE Confidential
 * File : UnitShipperServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.intf;
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

package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetNumberVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.UnitShippersVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ShipUnitVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.UnitShipDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;

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
public interface UnitShipperServiceIntf {

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */

    public List<UnitShippersVO> getUnitsToBeShipped(
            UnitShipDetailsVO objDetailsVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :List<UnitShippersVO>
     * @param :UnitShipDetailsVO objDetailsVO
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units which are already
     *               Shipped.
     * 
     */

    public List<UnitShippersVO> getLastShippedUnits(
            UnitShipDetailsVO objDetailsVO) throws RMDServiceException;

    /**
     * @Author :
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDServiceException
     * @Description: This Method Fetches the list of Units to be shipped.
     * 
     */

    public String updateUnitsToBeShipped(List<UnitShippersVO> arlShipperUnits)
            throws RMDServiceException;

    /**
     * @Author :Mohamed
     * @return :String
     * @param :List<UnitShippersVO> arlShipperUnits
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of ASSET NUMBER which yrt to be shipped.
     * 
     */
    public List<AssetNumberVO> getAssetNumbersForShipUnits(AssetServiceVO objAssetServiceVO)
            throws RMDServiceException;
    /**
     * @Author :Arun
     * @return :List<ShipUnitVO>
     * @param :
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of ASSET NUMBER shipped for past 24 hours.
     * 
     */
    public List<ShipUnitVO> getShipUnitDetails()
            throws RMDServiceException;
    /**
     * @Author :Arun
     * @return :List<ShipUnitVO>
     * @param :
     * @throws :RMDWebException
     * @Description: This Method Fetches the list of Infancy failure units for past 180 days.
     * 
     */
    public List<ShipUnitVO> getInfancyFailureDetails() throws RMDServiceException;
    
}
