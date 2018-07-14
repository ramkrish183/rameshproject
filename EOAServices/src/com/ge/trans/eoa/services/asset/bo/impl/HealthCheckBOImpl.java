/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthChechRequestBOImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.asset.bo.impl
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.locator.RMDServiceLocator;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAttributeVO;
import com.ge.trans.rmd.common.valueobjects.HealthCheckAvailableVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceLocatorException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.omi.beans.msg.request.HealthCheckRequest;
import com.ge.trans.rmd.omi.beans.msg.request.Request;
import com.ge.trans.rmd.omi.ejb.request.stateless.RequestProcessorSLSBHome;
import com.ge.trans.rmd.omi.ejb.request.stateless.RequestProcessorSLSBRemote;
import com.ge.trans.eoa.services.asset.bo.intf.HealthCheckBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.HealthCheckDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckSubmitEoaVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HealthCheckVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.HlthChckRqstServiceVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RDRNotificationsSubmitRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewReqHistoryEoaVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.ViewRespHistoryEoaVo;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class HealthCheckBOImpl implements HealthCheckBOIntf {

    private HealthCheckDAOIntf objHealthCheckDAOIntf;

    /**
     * @param objHealthCheckRequestDAOIntf
     */
    public HealthCheckBOImpl(final HealthCheckDAOIntf objHealthCheckDAOIntf) {
        super();
        this.objHealthCheckDAOIntf = objHealthCheckDAOIntf;
    }

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(HealthCheckBOImpl.class);

    /*
     * This Method is used for call the sendHealthRequest method in
     * HealthCheckRequestDAOImpl
     */
    @Override
    public String sendHealthRequest(final HlthChckRqstServiceVO objHlthChckRqstServiceVO) throws RMDBOException {
        String strRequestID = RMDCommonConstants.EMPTY_STRING;
        HealthCheckVO objHealthCheckVO = null;
        try {
            objHealthCheckVO = getRoadNumberDetails(objHlthChckRqstServiceVO.getStrAssetNumber(),
                    objHlthChckRqstServiceVO.getStrLanguage());
            if (objHealthCheckVO != null) {
                final HealthCheckRequest healthCheckRequest = new HealthCheckRequest(objHealthCheckVO.getCustomerID(),
                        objHealthCheckVO.getAssetGroupID(),
                        Integer.valueOf(objHealthCheckVO.getAssetGroupNumber().intValue()),
                        objHealthCheckVO.getAssetNumber(), Boolean.TRUE, 0, 0,
                        objHlthChckRqstServiceVO.getStrUserName());
                final RMDServiceLocator serviceLocator = RMDServiceLocator.getInstance();
                final RequestProcessorSLSBHome home = (RequestProcessorSLSBHome) serviceLocator
                        .getHome(RMDServiceConstants.HEALTH_CHECK_JNDI_NAME, RequestProcessorSLSBHome.class);
                final RequestProcessorSLSBRemote obj = home.create();
                final Long existingMessageID = obj.isRequestPending(healthCheckRequest);
                if (existingMessageID == null) {
                    Request request = obj.generateRequest(healthCheckRequest);
                    if (request.getRequestID() != null) {
                        strRequestID = request.getRequestID().toString();
                        objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.SUCCESS);
                    } else {
                        objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.FAILURE);
                    }
                } else {
                    strRequestID = existingMessageID.toString();
                    objHlthChckRqstServiceVO.setErrorMessage(RMDServiceConstants.HC_REQUEST_EXIST);
                }
            } else {
                objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.INVALID_ROAD_NUMBER_ERROR);
            }
        } catch (RemoteException e) {
            objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.FAILURE);
            LOG.debug("" + e);
        } catch (RMDServiceLocatorException e) {
            objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.FAILURE);
            LOG.debug("" + e);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            objHlthChckRqstServiceVO.setErrorMessage(RMDCommonConstants.FAILURE);
            LOG.debug("" + e);
        }
        return strRequestID;
    }

    /*
     * This Method is used for call the getRoadNumberDet method in AssetDAOImpl
     */
    @Override
    public HealthCheckVO getRoadNumberDetails(final String strAssetNumber, final String strLanguage)
            throws RMDBOException {
        HealthCheckVO objHealthCheckVO;
        try {
            objHealthCheckVO = objHealthCheckDAOIntf.getRoadNumberDetails(strAssetNumber, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objHealthCheckVO;
    }

    /**
     * @return the objHealthCheckRequestDAOIntf
     */
    public HealthCheckDAOIntf getObjHealthCheckDAOIntf() {
        return objHealthCheckDAOIntf;
    }

    /**
     * @param objHealthCheckRequestDAOIntf
     *            the objHealthCheckRequestDAOIntf to set
     */
    public void setObjHealthCheckDAOIntf(final HealthCheckDAOIntf objHealthCheckDAOIntf) {
        this.objHealthCheckDAOIntf = objHealthCheckDAOIntf;
    }

    @Override
    public List<ElementVO> getAssetHCMPGroups(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage, String requestType, String assetType, String deviceName) throws RMDBOException {
        List<ElementVO> arlHCMPGroups = null;
        try {
            arlHCMPGroups = objHealthCheckDAOIntf.getAssetHCMPGroups(strCustomer, strAssetNumber, strAssetGroupName,
                    strLanguage, requestType, assetType, deviceName);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlHCMPGroups;
    }

    @Override
    public HealthCheckAvailableVO IsHCAvailable(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException {
        HealthCheckAvailableVO result = null;
        try {
            result = objHealthCheckDAOIntf.IsHCAvailable(strCustomer, strAssetNumber, strAssetGroupName, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }
    
    // Raj.S (212687474)
    
    @Override
    public List<RDRNotificationsDataVO> getRDRNotifications(String userId) throws RMDBOException {
    	List<RDRNotificationsDataVO> notifications;
    	try {
    		notifications = objHealthCheckDAOIntf.getRDRNotifications(userId);
    	} catch (RMDDAOException e) {
            throw e;
        }
    	return notifications;
    }
    
    @Override
	public boolean updateRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDBOException {
    	boolean status;
    	try {
    		status = objHealthCheckDAOIntf.updateRDRNotification(notification);
    	} catch (RMDDAOException e) {
            throw e;
        }
    	return status;
    }
    
    @Override
	public boolean insertRDRNotification(RDRNotificationsSubmitRequestVO notification) throws RMDBOException {
    	boolean status;
    	try {
    		status = objHealthCheckDAOIntf.insertRDRNotification(notification);
    	} catch (RMDDAOException e) {
            throw e;
        }
    	return status;
    }

    @Override
    public String getHCAssetType(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException {
        String result = null;
        try {
            result = objHealthCheckDAOIntf.getHCAssetType(strCustomer, strAssetNumber, strAssetGroupName, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return result;
    }

    @Override
    public HealthCheckAttributeVO getSendMessageAttributes(String strInput, String strAssetType, String strLanguage,
            String typeOfUser, String vehObjId, String device) throws RMDBOException {
        HealthCheckAttributeVO hcAttributeVO = null;
        try {
            hcAttributeVO = objHealthCheckDAOIntf.getSendMessageAttributes(strInput, strAssetType, strLanguage,
                    typeOfUser, vehObjId, device);
        } catch (RMDDAOException e) {
            throw e;
        }
        return hcAttributeVO;
    }

    @Override
    public Map<String, String> getCustrnhDetails(String strCustomer, String strAssetNumber, String strAssetGroupName,
            String strLanguage) throws RMDBOException {
        Map<String, String> mapCustrnh = null;
        try {
            mapCustrnh = objHealthCheckDAOIntf.getCustrnhDetails(strCustomer, strAssetNumber, strAssetGroupName,
                    strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return mapCustrnh;
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return String
     * @throws RMDBOException
     * @Description:This method submits a healthCheck request
     */

    @Override
    public String submitHealthCheckRequest(HealthCheckSubmitEoaVO healthCheckSubmitEoaVO) throws RMDBOException {

        try {
            objHealthCheckDAOIntf.submitHealthCheckRequest(healthCheckSubmitEoaVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl submitHealthCheckRequest()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return healthCheckSubmitEoaVO.getRequestNum();
    }

    /**
     * @param healthCheckSubmitEoaVO
     * @return List<ViewRespHistoryEoaVo>
     * @throws RMDBOException
     * @Description:This method returns the list of Request History
     */
    @Override
    public List<ViewRespHistoryEoaVo> viewRequestHistory(ViewReqHistoryEoaVo viewReqHistoryEoaVo)
            throws RMDBOException {
        try {

            List<ViewRespHistoryEoaVo> viewHistorylist = objHealthCheckDAOIntf.viewRequestHistory(viewReqHistoryEoaVo);

            return viewHistorylist;
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl submitHealthCheckRequest()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        } catch (Exception e) {
            LOG.error("CommonUtil : Exception in generateXML :", e);
            throw new RMDBOException(e.getMessage());
        }
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method fetches the device information.
     */
    @Override
    public String getDeviceInfo(HealthCheckVO objHealthCheckVO) throws RMDBOException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckDAOIntf.getDeviceInfo(objHealthCheckVO);
            }
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl getDeviceInfo()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method saves the health check details.
     */
    @Override
    public String saveHCDetails(HealthCheckVO objHealthCheckVO) throws RMDBOException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckDAOIntf.saveHCDetails(objHealthCheckVO);
            }
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl saveHCDetails()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates EGA HC
     */
    @Override
    public String validateEGAHC(HealthCheckVO objHealthCheckVO) throws RMDBOException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckDAOIntf.validateEGAHC(objHealthCheckVO);
            }
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl validateEGAHC()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param HealthCheckVO
     * @return String
     * @throws RMDBOException
     * @Description:This method validates NT HC
     */
    @Override
    public String validateNTHC(HealthCheckVO objHealthCheckVO) throws RMDBOException {
        String result = null;
        try {
            if (null != objHealthCheckVO) {
                result = objHealthCheckDAOIntf.validateNTHC(objHealthCheckVO);
            }
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl validateNTHC()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }

    /**
     * @Author:
     * @param String,String
     * @return String
     * @throws RMDBOException
     * @Description:This method filters records based on type of user logged in
     */
    @Override
    public List<String> filterRecords(String messageIdList, String filterCondition) throws RMDBOException {
        List<String> result = null;
        try {
            if (null != messageIdList && null != filterCondition) {
                result = objHealthCheckDAOIntf.filterRecords(messageIdList, filterCondition);
            }
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in HealthCheckBOImpl filterRecords()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return result;
    }
}
