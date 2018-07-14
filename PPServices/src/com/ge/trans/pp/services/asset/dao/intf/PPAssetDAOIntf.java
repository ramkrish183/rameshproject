package com.ge.trans.pp.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHstDtlsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPMetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionDetailsVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface PPAssetDAOIntf {

	List getCurrentPPStatus(PPAssetStatusVO objPPAssetStatusVO) throws RMDDAOException;
	List<PPAssetHistoryDetailsVO> getPPStatusHistory(PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDDAOException;
	public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO) throws RMDDAOException;
	public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO)
			throws RMDDAOException;
		/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Status value for particular Road Number.
	 * 
	 */
	public String getStatusValue(String rnhId, String rnId)throws RMDDAOException;
	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Model value for particular Road Number.
	 * 
	 */
	public String getPPAssetModel(String rnhId, String rnId)throws RMDDAOException;
	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to update the Status value for particular Road Number.
	 * 
	 */
	public void changeStatus(String status, String rnhId, String rnId) throws RMDDAOException;
	/**
	 * 
	 * @param
	 * @return List<PPAssetHistoryDetailsVO>
	 * @Description * This method is used to get the PP Asset History data for Customer.
	 * 
	 */
	public List<PPAssetHistoryDetailsVO> getAssetHistory(PPAssetHstDtlsVO objPPAssetHstDtlsVO) throws RMDDAOException;
	/**
	 * @Author:
	 * @param: PPAssetStatusVO
	 * @return:List<PPAssetDetailsVO>
	 * @throws:RMDDAOException,Exception
	 * @Description: This method is used for fetching the the pin point enabled
	 *               assets.
	 */
	public List<PPAssetDetailsVO> getPPSearchAssets(
			PPAssetStatusVO ppAssetStatusVO) throws RMDDAOException;
	
	public List<PPMetricsVO> getMetricsConversion() throws RMDDAOException;	
	public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDDAOException;	
	public List<PPMetricsVO> getUnitConversionData(final List<UnitConversionDetailsVO> objUnitConversionDetailsVO)throws RMDDAOException;
}
