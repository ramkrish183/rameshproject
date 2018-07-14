package com.ge.trans.eoa.services.alert.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.alert.bo.intf.AlertBOIntf;
import com.ge.trans.eoa.services.alert.service.intf.AlertServiceIntf;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertAssetDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertHistoryDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertHstDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertMultiUsersVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertSubscriberDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertSubscriptionDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.RestrictedAlertShopVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.ShopsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.security.service.valueobjects.UpdatePhoneVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class AlertServiceImpl implements AlertServiceIntf
{
	private AlertBOIntf objAlertBOIntf;

    public AlertServiceImpl(final AlertBOIntf objAlertBOIntf) {
        this.objAlertBOIntf = objAlertBOIntf;
    }
    
    @Override
    public String activateOrDeactivateAlert(
            List<AlertSubscriptionDetailsVO> rowList)
            throws RMDServiceException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertBOIntf.activateOrDeactivateAlert(rowList);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE); // need to check for correct string
        }
        return pass;
    }
    @Override
    public String editAlert(AlertSubscriptionDetailsVO objEdit) throws RMDServiceException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertBOIntf.editAlert(objEdit);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE); // need to check for correct  string
        }
        return pass;
    }

    @Override
    public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList) throws RMDServiceException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertBOIntf.deleteAlert(rowList);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE); // need to check for correct  string
        }
        return pass;
    }
    
    public String updatephone(List<UpdatePhoneVO> updatePhoneVO) throws RMDServiceException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertBOIntf.updatephone(updatePhoneVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return pass;
    }

    @Override
    public String addAlertSubscriptionData(AlertSubscriptionDetailsVO ppalertSubscriptionDetails)
            throws RMDServiceException {
        String returnStr = null;
        try {
            returnStr = objAlertBOIntf.addAlertSubscriptionData(ppalertSubscriptionDetails);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return returnStr;
    }

    @Override
    public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
            AlertSubscriptionDetailsVO ppalertSubscriptionDetails) throws RMDServiceException {
        List<AlertSubscriptionDetailsVO> ppAlertSubscriptionDetailsVO = new ArrayList<AlertSubscriptionDetailsVO>();
        try {
            ppAlertSubscriptionDetailsVO = objAlertBOIntf.getAlertSubscriptionData(ppalertSubscriptionDetails);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return ppAlertSubscriptionDetailsVO;

    }
    @Override
    public List<ShopsVO> getAllShops() throws RMDServiceException {
        List<ShopsVO> shopList = null;
        try {
            shopList = objAlertBOIntf.getAllShops();
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return shopList;
    }

    /**
     * @Author:
     * @param:
     * @return:List<ModelVO>
     * @throws:RMDWebException,Exception
     * @Description: This method is used for fetching the All Model values.
     */
    @Override
    public List<ModelVO> getAllModels() throws RMDServiceException {
        List<ModelVO> modelList = null;
        try {
            modelList = objAlertBOIntf.getAllModels();
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return modelList;
    }

    @Override
    public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO) throws RMDServiceException {
        List<AlertAssetDetailsVO> assetList = null;
        try {
            assetList = objAlertBOIntf.getAssetForCustomer(assetVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetVO.getStrLanguage());
        }
        return assetList;
    }

    @Override
    public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO) throws RMDServiceException {
        AlertDetailsVO alertList = null;
        try {
            alertList = objAlertBOIntf.getAlertForCustomer(assetVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetVO.getStrLanguage());
        }
        return alertList;
    }
    @Override
    public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType) throws RMDServiceException {
        List<AlertMultiUsersVO> objPPAssetStatusHistoryVO = null;
        try {

            objPPAssetStatusHistoryVO = objAlertBOIntf.getMultiUsers(customerId, userType);

        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objPPAssetStatusHistoryVO;
    }

    @Override
    public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId, String modelVal)
            throws RMDServiceException {
        List<AlertDetailsVO> objPPAlertDetailsVO = null;
        try {

            objPPAlertDetailsVO = objAlertBOIntf.getConfigAlertForCustomer(customerId, modelVal);

        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return objPPAlertDetailsVO;
    }
    
    @Override
    public String getUserEmailId(String userId) throws RMDServiceException {
        String userEmailId = null;
        try {
        	userEmailId = objAlertBOIntf.getUserEmailId(userId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return userEmailId;
    }
    
    @Override
    public String getOTPParameters() throws RMDServiceException {
        String otp_parameters = null;
        try {
        	otp_parameters = objAlertBOIntf.getOTPParameters();
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return otp_parameters;
    }
    
    @Override
    public String getUserPhoneNo(String userId) throws RMDServiceException {
        String userPhoneNo = null;
        try {
        	userPhoneNo = objAlertBOIntf.getUserPhoneNo(userId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return userPhoneNo;
    }
    
    @Override
    public String getUserPhoneCountryCode(String userId) throws RMDServiceException {
        String userPhoneCountryCode = null;
        try {
        	userPhoneCountryCode = objAlertBOIntf.getUserPhoneCountryCode(userId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return userPhoneCountryCode;
    }
    
    @Override
    public List<String> getATSEnabledCustomers() throws RMDServiceException {
        List<String> atsCustomer = null;
        try {
            atsCustomer = objAlertBOIntf.getATSEnabledCustomers();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE); // need to check for correct  string
        }
        return atsCustomer;
    }
    
    @Override
    public String getAlertSubAssetCount(String userId) throws RMDServiceException {
        String count = "0";
        try {
            count = objAlertBOIntf.getAlertSubAssetCount(userId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return count;
    }
   
    @Override
    public List<RestrictedAlertShopVO> getRestrictedAlertShop() throws RMDServiceException {
        List<RestrictedAlertShopVO> shopList = null;
        try {
            shopList = objAlertBOIntf.getRestrictedAlertShop();
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return shopList;
    }
    
	@Override
	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO)  throws RMDServiceException
	{
		AlertHstDetailsVO objAlertHistoryDetailsVOlst =new AlertHstDetailsVO();
		try 
		{
			objAlertHistoryDetailsVOlst = objAlertBOIntf.getAlertHistoryDetails(objAlertHistoryDetailsVO);
		} 
		catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
		return objAlertHistoryDetailsVOlst;
	}

	@Override
    public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(AlertAssetDetailsVO assetVO)throws RMDServiceException {
        List<AlertAssetDetailsVO> assetList = null;
        try {
            assetList = objAlertBOIntf.getAlertHisotryPopulateData(assetVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, assetVO.getStrLanguage());
        }
        return assetList;
    }

	@Override
	public String getSubMenuList(String userId) throws RMDServiceException
	{
		String subMenuString = RMDCommonConstants.EMPTY_STRING;
		try 
		{
			subMenuString = objAlertBOIntf.getSubMenuList(userId);
		} 
		catch (RMDBOException ex)
		{
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return subMenuString;
	}
	@Override
    public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO)throws RMDServiceException {
        List<AlertSubscriberDetailsVO> subscriberList = null;
        try {
        	
        	subscriberList = objAlertBOIntf.getSubscribersList(subscriberVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, subscriberVO.getStrLanguage());
        }
        return subscriberList;
    }
}
