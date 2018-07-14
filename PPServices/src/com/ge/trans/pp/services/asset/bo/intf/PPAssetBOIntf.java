package com.ge.trans.pp.services.asset.bo.intf;

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
import com.ge.trans.rmd.exception.RMDServiceException;

public interface PPAssetBOIntf {

	List getCurrentPPStatus(PPAssetStatusVO objPPAssetStatusVO)
	throws RMDBOException;
	List<PPAssetHistoryDetailsVO> getPPStatusHistory(PPAssetStatusHistoryVO objPPAssetStatusHistoryVO)
	throws RMDBOException;
	public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO)
			throws RMDBOException;
	public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO)
			throws RMDBOException;
	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Status value for particular Road Number.
	 * 
	 */
	public String getStatusValue(String rnhId, String rnId)throws RMDBOException;
	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to fetch the Model value for particular Road Number.
	 * 
	 */
	public String getPPAssetModel(String rnhId, String rnId)throws RMDBOException;
	/**
	 * 
	 * @param
	 * @return String
	 * @Description * This method is used to update the Status value for particular Road Number.
	 * 
	 */
	public void changeStatus(String status, String rnhId, String rnId) throws RMDBOException;
	/**
	 * 
	 * @param
	 * @return List<PPAssetHistoryDetailsVO>
	 * @Description * This method is used to get the PP Asset History data for Customer.
	 * 
	 */
	public List<PPAssetHistoryDetailsVO> getAssetHistory(PPAssetHstDtlsVO objPPAssetHstDtlsVO) throws RMDBOException;
    /**
     * @Author:
     * @param: PPAssetStatusVO
     * @return:List<PPAssetDetailsVO>
     * @throws:RMDBOException,Exception
     * @Description: This method is used for fetching the the pin point enabled
     *               assets.
     */
    public List<PPAssetDetailsVO> getPPSearchAssets(
            PPAssetStatusVO ppAssetStatusVO) throws RMDBOException;
    
    
    public List<PPMetricsVO> getMetricsConversion()throws RMDBOException;
    public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO)throws RMDBOException;
    public List<PPMetricsVO> getUnitConversionData(final List<UnitConversionDetailsVO> objUnitConversionDetailsVO)throws RMDBOException;
}
