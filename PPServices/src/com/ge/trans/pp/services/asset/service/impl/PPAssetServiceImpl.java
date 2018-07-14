/**
 * ============================================================
 * Classification: GE Confidential
 * File : PPAssetServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.trans.pp.services.asset.service.impl
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
package com.ge.trans.pp.services.asset.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.pp.common.util.RMDServiceErrorHandler;
import com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf;
import com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf;
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
import com.ge.trans.rmd.exception.RMDServiceException;

public class PPAssetServiceImpl implements PPAssetServiceIntf {

    private PPAssetBOIntf objPPAssetBOIntf;

    public PPAssetServiceImpl(final PPAssetBOIntf objPPAssetBOIntf) {
        this.objPPAssetBOIntf = objPPAssetBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf#
     * getCurrentPPStatus(com.ge.trans.pp.services.asset.service.valueobjects.
     * PPAssetStatusVO) This method is used to call getCurrentPPStatus() of
     * PPAssetBOImpl
     */
    @Override
    public List getCurrentPPStatus(final PPAssetStatusVO objPPAssetStatusVO) throws RMDServiceException {
        List ppAssetStatus = null;
        try {

            ppAssetStatus = objPPAssetBOIntf.getCurrentPPStatus(objPPAssetStatusVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objPPAssetStatusVO.getStrLanguage());
        }
        return ppAssetStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf#
     * getPPStatusHistory(com.ge.trans.pp.services.asset.service.valueobjects.
     * PPAssetStatusHistoryVO) This method is used to call getPPStatusHistory()
     * of PPAssetBOImpl
     */
    @Override
    public List<PPAssetHistoryDetailsVO> getPPStatusHistory(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO)
            throws RMDServiceException {
        List<PPAssetHistoryDetailsVO> ppAssetStatusHstry = null;
        try {
            ppAssetStatusHstry = objPPAssetBOIntf.getPPStatusHistory(objPPAssetStatusHistoryVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objPPAssetStatusHistoryVO.getStrLanguage());
        }
        return ppAssetStatusHstry;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf#
     * getPPStates(com.ge.trans.pp.services.asset.service.valueobjects.
     * PPStatesVO) This method is used to call getPPStates() of PPAssetBOImpl
     */
    @Override
    public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO) throws RMDServiceException {
        List ppStateResp = null;
        try {
            ppStateResp = objPPAssetBOIntf.getPPStates(objPPStatesVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objPPStatesVO.getStrLanguage());
        }
        return ppStateResp;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.pp.services.asset.service.intf.PPAssetServiceIntf#
     * getPPAssets(com.ge.trans.pp.services.asset.service.valueobjects.
     * PPAssetDetailsVO)
     */
    @Override
    public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO) throws RMDServiceException {
        List<PPAssetDetailsVO> assetList = null;
        try {
            assetList = objPPAssetBOIntf.getPPAssets(assetVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetVO.getStrLanguage());
        }
        return assetList;
    }

    /**
     * @param
     * @return String
     * @Description * This method is used to fetch the Status value for
     *              particular Road Number.
     */
    @Override
    public String getStatusValue(String rnhId, String rnId) throws RMDServiceException {
        String statusValue = null;
        try {
            statusValue = objPPAssetBOIntf.getStatusValue(rnhId, rnId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return statusValue;
    }

    /**
     * @param
     * @return String
     * @Description * This method is used to fetch the Model value for
     *              particular Road Number.
     */
    @Override
    public String getPPAssetModel(String rnhId, String rnId) throws RMDServiceException {
        String modelValue = null;
        try {
            modelValue = objPPAssetBOIntf.getPPAssetModel(rnhId, rnId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return modelValue;
    }

    /**
     * @param
     * @return String
     * @Description * This method is used to update the Status value for
     *              particular Road Number.
     */
    @Override
    public void changeStatus(String status, String rnhId, String rnId) throws RMDServiceException {
        try {
            objPPAssetBOIntf.changeStatus(status, rnhId, rnId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }

    /**
     * @param
     * @return List<PPAssetHistoryDetailsVO>
     * @Description * This method is used to get the PP Asset History data for
     *              Customer.
     */
    @Override
    public List<PPAssetHistoryDetailsVO> getAssetHistory(PPAssetHstDtlsVO objPPAssetHstDtlsVO)
            throws RMDServiceException {
        List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
        try {
            ppAssetHistoryDtlsVOLst = objPPAssetBOIntf.getAssetHistory(objPPAssetHstDtlsVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "Exception occured");
        }
        return ppAssetHistoryDtlsVOLst;
    }

   
    /**
     * @Author:
     * @param:
     * @return:List<PPAssetDetailsVO>
     * @throws:RMDServiceException,Exception
     * @Description: This method is used for fetching the pin point enabled
     *               assets.
     */
    @Override
    public List<PPAssetDetailsVO> getPPSearchAssets(PPAssetStatusVO ppAssetStatusVO) throws RMDServiceException {

        List ppAssetStatus = null;
        try {

            ppAssetStatus = objPPAssetBOIntf.getPPSearchAssets(ppAssetStatusVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, ppAssetStatusVO.getLanguage());
        }
        return ppAssetStatus;
    }

    @Override
    public List<PPMetricsVO> getMetricsConversion() throws RMDServiceException {
        List<PPMetricsVO> objPPAssetStatusHistoryVO = null;
        try {

            objPPAssetStatusHistoryVO = objPPAssetBOIntf.getMetricsConversion();

        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "Exception occured");
        }
        return objPPAssetStatusHistoryVO;
    }

	@Override
	public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDServiceException {
		String headerResponse = null;
		try
		{
			headerResponse = objPPAssetBOIntf.getPPAssetHistoryHeaders(objPPAssetStatusHistoryVO);
		}
		catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "Exception occured");
        }
        return headerResponse;
	}

	@Override
	public List<PPMetricsVO> getUnitConversionData(
			List<UnitConversionDetailsVO> objUnitConversionDetailsVO)
			throws RMDServiceException {
		List<PPMetricsVO> metricResponse = null;
		try
		{
			metricResponse = objPPAssetBOIntf.getUnitConversionData(objUnitConversionDetailsVO);
		}
		catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "Exception occured");
        }
        return metricResponse;
	}
}