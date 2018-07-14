package com.ge.trans.pp.services.asset.service.intf;

import java.util.List;

import com.ge.trans.pp.services.asset.service.valueobjects.PPMetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHstDtlsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionDetailsVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface PPAssetServiceIntf {

    List getCurrentPPStatus(PPAssetStatusVO objPPAssetStatusVO)
            throws RMDServiceException;

	List<PPAssetHistoryDetailsVO> getPPStatusHistory(
			PPAssetStatusHistoryVO objPPAssetStatusHistoryVO)
			throws RMDServiceException;

    public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO)
            throws RMDServiceException;

    public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO)
            throws RMDServiceException;
        /**
     * 
     * @param
     * @return String
     * @Description * This method is used to fetch the Status value for particular Road Number.
     * 
     */
    public String getStatusValue(String rnhId, String rnId)throws RMDServiceException;
    /**
     * 
     * @param
     * @return String
     * @Description * This method is used to fetch the Model value for particular Road Number.
     * 
     */
    public String getPPAssetModel(String rnhId, String rnId)throws RMDServiceException;
    /**
     * 
     * @param
     * @return String
     * @Description * This method is used to update the Status value for particular Road Number.
     * 
     */
    public void changeStatus(String status, String rnhId, String rnId) throws RMDServiceException;
    /**
     * 
     * @param
     * @return List<PPAssetHistoryDetailsVO>
     * @Description * This method is used to get the PP Asset History data for Customer.
     * 
     */
    public List<PPAssetHistoryDetailsVO> getAssetHistory(PPAssetHstDtlsVO objPPAssetHstDtlsVO) throws RMDServiceException;
    /**
     * @Author:
     * @param: PPAssetStatusVO
     * @return:List<PPAssetDetailsVO>
     * @throws:RMDServiceException,Exception
     * @Description: This method is used for fetching the the pin point enabled
     *               assets.
     */
    public List<PPAssetDetailsVO> getPPSearchAssets(
            PPAssetStatusVO ppAssetStatusVO) throws RMDServiceException;
    
    public List<PPMetricsVO> getMetricsConversion()throws RMDServiceException;
    public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO)throws RMDServiceException;
    public List<PPMetricsVO> getUnitConversionData(final List<UnitConversionDetailsVO> objUnitConversionDetailsVO)throws RMDServiceException;
}
