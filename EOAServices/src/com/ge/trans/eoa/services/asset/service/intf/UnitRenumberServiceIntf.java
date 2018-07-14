package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface UnitRenumberServiceIntf {
	/**
	 * This method is used to update unit number in OLTP tables
	 * @param oldValue
	 * @param newValue
	 * @param vehicleHeader
	 * @param customerId
	 * @return boolean - update status 
	 */
	boolean updateUnitNumber(String oldValue, String newValue, String vehicleHeader, String customerId);
	
	/**
	 * This method is used to update unit number in DWH tables
	 * @param oldValue
	 * @param newValue
	 * @param vehicleHeader
	 * @param customerId
	 * @return
	 */
	boolean updateUnitNumberDW(String oldValue, String newValue, String vehicleHeader, String customerId);
	
	/**
	 * This method is used to update unit header in OLTP tables
	 * @param objUnitRoadHeaderUpdateVO
	 * @return
	 */
	boolean updateUnitHeader(UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO);
	
	/**
	 * This method is used to update unit header in DW tables
	 * @param objUnitRoadHeaderUpdateVO
	 * @return
	 */
	boolean updateUnitHeaderDW(UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO);

	/**
	 * 
	 * @param objAssetServiceVO
	 * @return
	 * @throws RMDServiceException
	 */
	List<AssetServiceVO> getAssetsForUnitRenum(AssetServiceVO objAssetServiceVO)
			throws RMDServiceException;
}
