package com.ge.trans.eoa.services.alert.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.alert.bo.intf.AlertBOIntf;
import com.ge.trans.eoa.services.alert.dao.intf.AlertDAOIntf;
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
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class AlertBOImpl implements AlertBOIntf
{
	private AlertDAOIntf objAlertDAOIntf;

    public AlertBOImpl(final AlertDAOIntf objAlertDAOIntf) {
        this.objAlertDAOIntf = objAlertDAOIntf;
    }
    
    @Override
    public String activateOrDeactivateAlert(List<AlertSubscriptionDetailsVO> rowList) throws RMDBOException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertDAOIntf.activateOrDeactivateAlert(rowList);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return pass;
    }

    @Override
    public String editAlert(AlertSubscriptionDetailsVO objEdit) throws RMDBOException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertDAOIntf.editAlert(objEdit);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return pass;
    }

    @Override
    public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList) throws RMDBOException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertDAOIntf.deleteAlert(rowList);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return pass;
    }

    @Override
    public String addAlertSubscriptionData(AlertSubscriptionDetailsVO ppalertSubscriptionDetails)
            throws RMDBOException {
        String returnstr = null;
        try {
            returnstr = objAlertDAOIntf.addAlertSubscriptionData(ppalertSubscriptionDetails);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return returnstr;

    }

    @Override
    public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
            AlertSubscriptionDetailsVO ppalertSubscriptionDetails) throws RMDBOException {
        List<AlertSubscriptionDetailsVO> ppAlertSubscriptionDetailsVO = new ArrayList<AlertSubscriptionDetailsVO>();
        try {
            ppAlertSubscriptionDetailsVO = objAlertDAOIntf.getAlertSubscriptionData(ppalertSubscriptionDetails);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return ppAlertSubscriptionDetailsVO;
    }
    
    @Override
    public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO) throws RMDBOException {
        List<AlertAssetDetailsVO> assetList = null;
        try {
            assetList = objAlertDAOIntf.getAssetForCustomer(assetVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return assetList;
    }
    
    @Override
    public List<ShopsVO> getAllShops() throws RMDBOException {
        List<ShopsVO> shopList = null;
        try {
            shopList = objAlertDAOIntf.getAllShops();
        } catch (RMDDAOException ex) {
            throw ex;
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
    public List<ModelVO> getAllModels() throws RMDBOException {
        List<ModelVO> modelList = null;
        try {
            modelList = objAlertDAOIntf.getAllModels();
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return modelList;
    }
    
    @Override
    public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO) throws RMDBOException {
        AlertDetailsVO alertList = null;
        try {
            alertList = objAlertDAOIntf.getAlertForCustomer(assetVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return alertList;
    }
    @Override
   	public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType)
   			throws RMDBOException {
   		List<AlertMultiUsersVO> objPPAssetStatusHistoryVO = null;
   		try {

   			objPPAssetStatusHistoryVO = objAlertDAOIntf.getMultiUsers(
   					customerId, userType);

   		} catch (RMDDAOException ex) {
   			throw new RMDBOException(ex.getErrorDetail(), ex);
   		} catch (Exception ex) {
   			String errorCode = RMDCommonUtility
   					.getErrorCode(RMDCommonConstants.BOEXCEPTION);
   			throw new RMDBOException(errorCode, new String[] {},
   					ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
   		}
   		return objPPAssetStatusHistoryVO;
   	}
    @Override
	public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId,
			String modelVal) throws RMDBOException {
		List<AlertDetailsVO> objPPAlertDetailsVO = null;
		try {

			objPPAlertDetailsVO = objAlertDAOIntf.getConfigAlertForCustomer(
					customerId, modelVal);

		} catch (RMDDAOException ex) {
			throw new RMDBOException(ex.getErrorDetail(), ex);
		} catch (Exception ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDCommonConstants.BOEXCEPTION);
			throw new RMDBOException(errorCode, new String[] {},
					ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
		}
		return objPPAlertDetailsVO;
	}
    
	@Override
    public String getUserEmailId(String userId) throws RMDBOException {
        String userEmailId = null;
        try {
        	userEmailId = objAlertDAOIntf.getUserEmailId(userId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return userEmailId;
    }
	
	@Override
    public List<String> getATSEnabledCustomers() throws RMDBOException {
        List<String> atsCustomer = null;
        try {
            atsCustomer = objAlertDAOIntf.getATSEnabledCustomers();
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return atsCustomer;
    }

    @Override
    public String getAlertSubAssetCount(String userId) throws RMDBOException {
        String returnstr = "0";
        try {
            returnstr = objAlertDAOIntf.getAlertSubAssetCount(userId);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return returnstr;

    }

    @Override
    public List<RestrictedAlertShopVO> getRestrictedAlertShop() throws RMDBOException {
        List<RestrictedAlertShopVO> shopList = null;
        try {
            shopList = objAlertDAOIntf.getRestrictedAlertShop();
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return shopList;
    }
	
	@Override
	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO) throws RMDBOException
	{
		AlertHstDetailsVO objAlertHistoryDtsVO =new AlertHstDetailsVO();
		try
		{
			objAlertHistoryDtsVO = objAlertDAOIntf.getAlertHistoryDetails(objAlertHistoryDetailsVO);
		} 
		catch (RMDDAOException ex) 
		{		
			throw ex;
		}
		return objAlertHistoryDtsVO;
		
	}
	
	@Override
    public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(AlertAssetDetailsVO assetVO) throws RMDBOException {
        List<AlertAssetDetailsVO> assetList = null;
        try {
            assetList = objAlertDAOIntf.getAlertHisotryPopulateData(assetVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return assetList;
    }

	@Override
	public String getSubMenuList(String userId) throws RMDBOException 
	{
		String subMenuString = RMDCommonConstants.EMPTY_STRING;
		try 
		{
			subMenuString = objAlertDAOIntf.getSubMenuList(userId);
		} 
		catch (RMDDAOException ex)
		{
			throw ex;
		}
		return subMenuString;
	}
	
	// Added by Sriram.B(212601214) for SMS feature
	@Override
    public String updatephone(List<UpdatePhoneVO> updatePhoneVO) throws RMDBOException {
        String pass = RMDServiceConstants.FAILURE;
        try {
            pass = objAlertDAOIntf.updatephone(updatePhoneVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return pass;
    }

	// Added by Sriram.B(212601214) for SMS feature
		@Override
	    public String getOTPParameters() throws RMDBOException {
	        String otp_parameters = null;
	        try {
	        	otp_parameters = objAlertDAOIntf.getOTPParameters();
	        } catch (RMDDAOException ex) {
	            throw new RMDBOException(ex.getErrorDetail(), ex);
	        } catch (Exception ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
	            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
	        }
	        return otp_parameters;
	    }
	
	// Added by Sriram.B(212601214) for SMS feature
	@Override
    public String getUserPhoneNo(String userId) throws RMDBOException {
        String userPhoneNo = null;
        try {
        	userPhoneNo = objAlertDAOIntf.getUserPhoneNo(userId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return userPhoneNo;
    }
	
	// Added by Sriram.B(212601214) for SMS feature
	@Override
    public String getUserPhoneCountryCode(String userId) throws RMDBOException {
        String userPhoneCountryCode = null;
        try {
        	userPhoneCountryCode = objAlertDAOIntf.getUserPhoneCountryCode(userId);
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.BOEXCEPTION);
            throw new RMDBOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.MINOR_ERROR);
        }
        return userPhoneCountryCode;
    }
	
	@Override
    public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO) throws RMDBOException {
        List<AlertSubscriberDetailsVO> subscriberList = null;
        try {
        	
        	subscriberList = objAlertDAOIntf.getSubscribersList(subscriberVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return subscriberList;
    }
}
