/**
 * ============================================================
 * Classification: GE Confidential
 * File : PPAssetBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.pp.services.asset.bo.impl
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
package com.ge.trans.pp.services.asset.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf;
import com.ge.trans.pp.services.asset.dao.intf.PPAssetDAOIntf;
import com.ge.trans.pp.services.asset.service.valueobjects.PPMetricsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHstDtlsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetHistoryDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusHistoryVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesResponseVO;
import com.ge.trans.pp.services.asset.service.valueobjects.PPStatesVO;
import com.ge.trans.pp.services.asset.service.valueobjects.UnitConversionDetailsVO;
import com.ge.trans.pp.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class PPAssetBOImpl implements PPAssetBOIntf {

    private PPAssetDAOIntf objPPAssetDAOIntf;

    public PPAssetBOImpl(final PPAssetDAOIntf objPPAssetDAOIntf) {
        this.objPPAssetDAOIntf = objPPAssetDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf#getCurrentPPStatus
     * (com.ge.trans.pp.services.asset.service.valueobjects.PPAssetStatusVO)
     * This method is used to call the getCurrentPPStatus() method in
     * PPAssetDAOImpl
     */
    @Override
    public List getCurrentPPStatus(final PPAssetStatusVO objPPAssetStatusVO) throws RMDBOException {
        List ppAssetStatus = null;
        try {

            ppAssetStatus = objPPAssetDAOIntf.getCurrentPPStatus(objPPAssetStatusVO);

        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATUS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objPPAssetStatusVO.getStrLanguage()), exc,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return ppAssetStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf#getPPStatusHistory(
     * com.ge.trans.pp.services.asset.service.valueobjects.
     * PPAssetStatusHistoryVO) This method is used to call the
     * getPPStatusHistory() method in PPAssetDAOImpl
     */
    @Override
    public List getPPStatusHistory(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDBOException {
        List ppAssetStatusHstry = null;
        try {
            ppAssetStatusHstry = objPPAssetDAOIntf.getPPStatusHistory(objPPAssetStatusHistoryVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_HISTORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objPPAssetStatusHistoryVO.getStrLanguage()),
                    exc, RMDCommonConstants.MINOR_ERROR);
        }
        return ppAssetStatusHstry;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf#getPPStates(com.ge.
     * trans.pp.services.asset.service.valueobjects.PPStatesVO) This method is
     * used to call the getPPStates() method in PPAssetDAOImpl
     */
    @Override
    public List<PPStatesResponseVO> getPPStates(final PPStatesVO objPPStatesVO) throws RMDBOException {
        List ppStatesResp = null;
        try {
            ppStatesResp = objPPAssetDAOIntf.getPPStates(objPPStatesVO);
        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_STATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objPPStatesVO.getStrLanguage()), exc,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return ppStatesResp;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.pp.services.asset.bo.intf.PPAssetBOIntf#getPPAssets(com.ge.
     * trans.pp.services.asset.service.valueobjects.PPAssetDetailsVO)
     */
    @Override
    public List<PPAssetDetailsVO> getPPAssets(PPAssetDetailsVO assetVO) throws RMDBOException {
        List<PPAssetDetailsVO> assetList = null;
        try {
            assetList = objPPAssetDAOIntf.getPPAssets(assetVO);
        } catch (RMDDAOException ex) {
            throw ex;
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
    public String getStatusValue(String rnhId, String rnId) throws RMDBOException {
        String statusValue = null;
        try {
            statusValue = objPPAssetDAOIntf.getStatusValue(rnhId, rnId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return statusValue;
    }

    /**
     * @param
     * @return String
     * @Description * This method is used to fetch the Status value for
     *              particular Road Number.
     */
    @Override
    public String getPPAssetModel(String rnhId, String rnId) throws RMDBOException {
        String modelValue = null;
        try {
            modelValue = objPPAssetDAOIntf.getPPAssetModel(rnhId, rnId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
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
    public void changeStatus(String status, String rnhId, String rnId) throws RMDBOException {
        try {
            objPPAssetDAOIntf.changeStatus(status, rnhId, rnId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
    }

    /**
     * @param
     * @return List<PPAssetHistoryDetailsVO>
     * @Description * This method is used to get the PP Asset History data for
     *              Customer.
     */
    @Override
    public List<PPAssetHistoryDetailsVO> getAssetHistory(PPAssetHstDtlsVO objPPAssetHstDtlsVO) throws RMDBOException {
        List<PPAssetHistoryDetailsVO> ppAssetHistoryDtlsVOLst = new ArrayList<PPAssetHistoryDetailsVO>();
        try {
            ppAssetHistoryDtlsVOLst = objPPAssetDAOIntf.getAssetHistory(objPPAssetHstDtlsVO);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return ppAssetHistoryDtlsVOLst;
    }

    /**
     * @Author:
     * @param:
     * @return:List<PPAssetDetailsVO>
     * @throws:RMDDAOException,Exception
     * @Description: This method is used for fetching the the pin point enabled
     *               assets.
     */
    @Override
    public List<PPAssetDetailsVO> getPPSearchAssets(PPAssetStatusVO ppAssetStatusVO) throws RMDBOException {
        List ppAssetSearch = null;
        try {

            ppAssetSearch = objPPAssetDAOIntf.getPPSearchAssets(ppAssetStatusVO);

        } catch (RMDDAOException ex) {
            throw ex;
        } catch (Exception exc) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ppAssetStatusVO.getLanguage()), exc,
                    RMDCommonConstants.MINOR_ERROR);
        }
        return ppAssetSearch;
    }

    @Override
    public List<PPMetricsVO> getMetricsConversion() throws RMDBOException {
        List<PPMetricsVO> objPPAssetStatusHistoryVO = null;
        try {

            objPPAssetStatusHistoryVO = objPPAssetDAOIntf.getMetricsConversion();

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return objPPAssetStatusHistoryVO;
    }

	@Override
	public String getPPAssetHistoryHeaders(final PPAssetStatusHistoryVO objPPAssetStatusHistoryVO) throws RMDBOException {
		  String response = null;
	        try {

	        	response = objPPAssetDAOIntf.getPPAssetHistoryHeaders(objPPAssetStatusHistoryVO);

	        } catch (RMDDAOException ex) {
	            throw new RMDBOException(ex.getErrorDetail(), ex);
	        } catch (Exception ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
	            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
	        }
	        return response;
	}

	@Override
	public List<PPMetricsVO> getUnitConversionData(
			List<UnitConversionDetailsVO> objUnitConversionDetailsVO)
			throws RMDBOException {
		List<PPMetricsVO> response = null;
        try {

        	response = objPPAssetDAOIntf.getUnitConversionData(objUnitConversionDetailsVO);

        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return response;
	}	
    
   

	
}
