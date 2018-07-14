package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface UnitRenumberBOIntf {
	/**
	 * This method is used to update unit number in OLTP tables
	 * @param oldValue
	 * @param newValue
	 * @param vehicleHeader
	 * @param customerId
	 * @return
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
	 * This method is used to update unit header in DWH tables
	 * @param objUnitRoadHeaderUpdateVO
	 * @return
	 */
	boolean updateUnitHeaderDW(UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO);
	List<AssetServiceVO> getAssetsForUnitRenum(AssetServiceVO objAssetServiceVO)
			throws RMDBOException;
}
