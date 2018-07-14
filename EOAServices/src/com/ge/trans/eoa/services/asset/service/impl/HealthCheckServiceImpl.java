/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.impl;
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
package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.asset.bo.intf.HealthCheckBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.HealthCheckServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HlthChckRqstServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class HealthCheckServiceImpl implements HealthCheckServiceIntf {

    private HealthCheckBOIntf objHealthCheckBOIntf;

    /**
     * @param objHealthCheckRequestBOIntf
     */
    public HealthCheckServiceImpl(HealthCheckBOIntf objHealthCheckBOIntf) {
        this.objHealthCheckBOIntf = objHealthCheckBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.asset.service.intf.
     * HealthCheckRequestServiceIntf
     * #sendHealthRequest(com.ge.trans.rmd.services.asset.service.valueobjects.
     * HlthChckRqstServiceVO)
     */
    /*
     * This Method is used for call the sendHealthRequest method in
     * HealthCheckRequestBOImpl
     */
    @Override
    public String sendHealthRequest(HlthChckRqstServiceVO objHlthChckRqstServiceVO) throws RMDServiceException {
        String strRequestID = RMDCommonConstants.EMPTY_STRING;
        try {
            strRequestID = objHealthCheckBOIntf.sendHealthRequest(objHlthChckRqstServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return strRequestID;
    }

    /**
     * @return the objHealthCheckRequestBOIntf
     */
    public HealthCheckBOIntf getObjHealthCheckBOIntf() {
        return objHealthCheckBOIntf;
    }
  
    /**
     * @param objHealthCheckRequestBOIntf
     *            the objHealthCheckRequestBOIntf to set
     */
    public void setObjHealthCheckRequestBOIntf(HealthCheckBOIntf objHealthCheckBOIntf) {
        this.objHealthCheckBOIntf = objHealthCheckBOIntf;
    }

    /**
     * @param String
     *            strCustomer, String strAssetNumber, String strAssetGroupName,
     *            String strLanguage
     * @return HealthCheckAvailableVO
     * @throws RMDServiceException
     * @Description:This method checks if healthcheck Available.
     */
    @Override
    public HealthCheckAvailableVO IsHCAvailable(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException {
        HealthCheckAvailableVO result = null;
        try {
            result = objHealthCheckBOIntf.IsHCAvailable(strCustomer, strAssetNumber, strAssetGroupName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;

    }
    
    // Raj.S (212687474)
    
    @Override
    public List<RDRNotificationsDataVO> getRDRNotifications(String userId) throws RMDServiceException {
    	List<RDRNotificationsDataVO> notifications = null;
    	try {
            notifications = objHealthCheckBOIntf.getRDRNotifications(userId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    	return notifications;
    }
    
    @Override
    public boolean updateRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDServiceException {
    	boolean status = false;
    	try {
            status = objHealthCheckBOIntf.updateRDRNotification(notification);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    	return status;
    }
    
    @Override
    public boolean insertRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDServiceException {
    	boolean status = false;
    	try {
            status = objHealthCheckBOIntf.insertRDRNotification(notification);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    	return status;
    }

    @Override
    public List<ElementVO> getAssetHCMPGroups(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage, String requestType, String assetType, String deviceName) throws RMDServiceException {
        List<ElementVO> result = null;
        try {
            result = objHealthCheckBOIntf.getAssetHCMPGroups(strCustomer, strAssetNumber, strAssetGroupName,
                    strLanguage, requestType, assetType, deviceName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }
    @Override
    public String getHCAssetType(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException {
        String result = null;
        try {
            result = objHealthCheckBOIntf.getHCAssetType(strCustomer, strAssetNumber, strAssetGroupName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    @Override
    public HealthCheckAttributeVO getSendMessageAttributes(String strInput, String strAssetType, String strLanguage,
            String typeOfUser, String vehObjId, String device) throws RMDServiceException {
        HealthCheckAttributeVO hcAttrVO = null;
        try {
            hcAttrVO = objHealthCheckBOIntf.getSendMessageAttributes(strInput, strAssetType, strLanguage, typeOfUser,
                    vehObjId, device);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return hcAttrVO;
    }

    @Override
    public Map<String, String> getCustrnhDetails(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDServiceException {
        Map<String, String> mapCustrnh = null;
        try {
            mapCustrnh = objHealthCheckBOIntf.getCustrnhDetails(strCustomer, strAssetNumber, strAssetGroupName,
                    strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return mapCustrnh;
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method submits a healthCheck request
     */
    @Override
    public String submitHealthCheckRequest(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO) throws RMDServiceException {
        String msgRequestId = null;
        try {
            msgRequestId = objHealthCheckBOIntf.submitHealthCheckRequest(healthCheckSubmitEoaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return msgRequestId;
    }

    /**
     * @param viewReqHistoryEoaVo
     * @return List<ViewRespHistoryEoaVo>
     * @throws RMDServiceException
     * @Description:This method returns the list of healthCheck request
     */
    @Override
    public List<ViewRespHistoryEoaVo> viewRequestHistory(
            ViewReqHistoryEoaVo viewReqHistoryEoaVo) throws RMDServiceException {
        List<ViewRespHistoryEoaVo> viewHistorylist = null;
        try {
            viewHistorylist = objHealthCheckBOIntf
                    .viewRequestHistory(viewReqHistoryEoaVo);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex,
                    RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return viewHistorylist;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method fetches the device information.
     */
    @Override
    public String getDeviceInfo(HealthCheckVO objHealthCheckVO) throws RMDServiceException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckBOIntf.getDeviceInfo(objHealthCheckVO);
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method saves the health check details.
     */
    @Override
    public String saveHCDetails(HealthCheckVO objHealthCheckVO) throws RMDServiceException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckBOIntf.saveHCDetails(objHealthCheckVO);
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates EGA HC.
     */
    @Override
    public String validateEGAHC(HealthCheckVO objHealthCheckVO) throws RMDServiceException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckBOIntf.validateEGAHC(objHealthCheckVO);
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDServiceException
     * @Description:This method validates NT HC.
     */
    @Override
    public String validateNTHC(HealthCheckVO objHealthCheckVO) throws RMDServiceException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckBOIntf.validateNTHC(objHealthCheckVO);
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    /**
     * @Author:
     * @param String,String
     * @return String
     * @throws RMDServiceException
     * @Description:This method filters records based on type of user logged in
     */
    @Override
    public List<String> filterRecords(String messageIdList, String filterCondition) throws RMDServiceException {
        List<String> result = null;
        try {
            if (null != messageIdList && null != filterCondition) {
                result = objHealthCheckBOIntf.filterRecords(messageIdList, filterCondition);
            }
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }
}
