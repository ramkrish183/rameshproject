package com.ge.trans.eoa.services.alert.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
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
import com.ge.trans.rmd.exception.RMDServiceException;

public interface AlertServiceIntf {

	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO) throws RMDServiceException;

	public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId,String modelVal) throws RMDServiceException;

	public String getUserEmailId(String userId) throws RMDServiceException;
	
	public String getOTPParameters() throws RMDServiceException;
	
	public String getUserPhoneNo(String userId) throws RMDServiceException;
	
	public String getUserPhoneCountryCode(String userId) throws RMDServiceException;

	public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType) throws RMDServiceException;

	public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO) throws RMDServiceException;

	public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO) throws RMDServiceException;

	public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
			AlertSubscriptionDetailsVO alertReqVO) throws RMDServiceException;

	public String addAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails) throws RMDServiceException;

	public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList) throws RMDServiceException;
	
	public String updatephone(List<UpdatePhoneVO> updatePhoneVO) throws RMDServiceException;

	public String editAlert(AlertSubscriptionDetailsVO editVal) throws RMDServiceException;

	public String activateOrDeactivateAlert(
			List<AlertSubscriptionDetailsVO> rowList) throws RMDServiceException;

	public List<ShopsVO> getAllShops() throws RMDServiceException;

	public List<ModelVO> getAllModels() throws RMDServiceException;

	public List<RestrictedAlertShopVO> getRestrictedAlertShop() throws RMDServiceException;

	public String getAlertSubAssetCount(String stripXSSCharacters) throws RMDServiceException;

	public List<String> getATSEnabledCustomers() throws RMDServiceException;

	public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(
			AlertAssetDetailsVO assetVO)throws RMDServiceException;

	public String getSubMenuList(String userId)throws RMDServiceException;

	public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO) throws RMDServiceException;

	

}
