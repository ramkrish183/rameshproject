package com.ge.trans.eoa.services.alert.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.alert.service.valueobjects.AlertHistoryDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertHstDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertSubscriptionDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertAssetDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertMultiUsersVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.AlertSubscriberDetailsVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.RestrictedAlertShopVO;
import com.ge.trans.eoa.services.alert.service.valueobjects.ShopsVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UpdatePhoneVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface AlertDAOIntf {

	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO) throws RMDDAOException;

	public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId,String modelVal) throws RMDDAOException;

	public String getUserEmailId(String userId) throws RMDDAOException;
	
	public String getOTPParameters() throws RMDDAOException;
	
	public String getUserPhoneNo(String userId) throws RMDDAOException;
	
	public String getUserPhoneCountryCode(String userId) throws RMDDAOException;

	public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType)throws RMDDAOException;

	public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO)throws RMDDAOException;

	public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO)throws RMDDAOException;

	public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails) throws RMDDAOException;

	public String addAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails) throws RMDDAOException;

	public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList) throws RMDDAOException;
	
	public String updatephone(List<UpdatePhoneVO> updatePhoneVO) throws RMDDAOException;

	public String editAlert(AlertSubscriptionDetailsVO objEdit) throws RMDDAOException;

	public String activateOrDeactivateAlert(
			List<AlertSubscriptionDetailsVO> rowList) throws RMDDAOException;

	public List<ShopsVO> getAllShops() throws RMDDAOException;

	public List<ModelVO> getAllModels() throws RMDDAOException;

	public List<RestrictedAlertShopVO> getRestrictedAlertShop() throws RMDDAOException;

	public String getAlertSubAssetCount(String userId) throws RMDDAOException;

	public List<String> getATSEnabledCustomers() throws RMDDAOException;

	public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(
			AlertAssetDetailsVO assetVO) throws RMDDAOException;

	public String getSubMenuList(String userId) throws RMDDAOException;

	public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO) throws RMDDAOException;

}
