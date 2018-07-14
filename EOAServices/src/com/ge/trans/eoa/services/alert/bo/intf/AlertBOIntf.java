package com.ge.trans.eoa.services.alert.bo.intf;

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
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.security.service.valueobjects.UpdatePhoneVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface AlertBOIntf {

	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO) throws RMDBOException;

	public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId,String modelVal) throws RMDBOException;

	public String getUserEmailId(String userId) throws RMDBOException;

	public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType)throws RMDBOException;

	public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO)throws RMDBOException;

	public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO)throws RMDBOException;

	public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails)throws RMDBOException;

	public String addAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails)throws RMDBOException;

	public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList)throws RMDBOException;

	public String editAlert(AlertSubscriptionDetailsVO objEdit)throws RMDBOException;

	public String activateOrDeactivateAlert(
			List<AlertSubscriptionDetailsVO> rowList)throws RMDBOException;

	public List<ModelVO> getAllModels()throws RMDBOException;

	public List<ShopsVO> getAllShops()throws RMDBOException;

	public List<RestrictedAlertShopVO> getRestrictedAlertShop()throws RMDBOException;

	public String getAlertSubAssetCount(String userId)throws RMDBOException;

	public List<String> getATSEnabledCustomers()throws RMDBOException;

	public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(
			AlertAssetDetailsVO assetVO) throws RMDBOException;

	public String getSubMenuList(String userId)throws RMDBOException;
	
	public String getOTPParameters() throws RMDBOException;
	
	public String getUserPhoneNo(String userId) throws RMDBOException;
	
	public String getUserPhoneCountryCode(String userId) throws RMDBOException;
	
	public String updatephone(List<UpdatePhoneVO> updatePhoneVO)throws RMDBOException;

	public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO) throws RMDBOException;

}
