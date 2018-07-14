package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.rmd.common.valueobjects.UnitRoadHeaderUpdateVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface UnitRenumberDAOIntf {
	/**
	 * This method is used to update Unit number in OLTP tables
	 * @param oldValue
	 * @param newValue
	 * @param vehicleHeader
	 * @param customerId
	 * @return
	 */
	public boolean updateUnitNumber(String oldValue, String newValue, String vehicleHeader, String customerId);
	
	/**
	 * This method is used to update Unit number in DWH tables
	 * @param oldValue
	 * @param newValue
	 * @param vehicleHeader
	 * @param customerId
	 * @return
	 */
	public boolean updateUnitNumberDW(String oldValue, String newValue, String vehicleHeader, String customerId);
	
	/**
	 * This method is used to update Unit header in OLTP tables
	 * @param objUnitRoadHeaderUpdateVO
	 * @return
	 */
	public boolean updateUnitHeader(UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO);
	
	/**
	 * This method is used to update Unit header in DWH tables
	 * @param objUnitRoadHeaderUpdateVO
	 * @return
	 */
	public boolean updateUnitHeaderDW(UnitRoadHeaderUpdateVO objUnitRoadHeaderUpdateVO);

	/**
	 * This method returns all the vehicle number for the customer including 'BAD' vehicles
	 * @param objAssetServiceVO
	 * @return
	 * @throws RMDDAOException
	 */
	List<AssetServiceVO> getAssetsForUnitRenum(AssetServiceVO objAssetServiceVO)
			throws RMDDAOException;
}
