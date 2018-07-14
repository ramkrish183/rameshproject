package com.ge.trans.eoa.services.alert.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
import com.ge.trans.eoa.services.common.constants.AlertConstants;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.security.service.valueobjects.UpdatePhoneVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class AlertDAOImpl extends RMDCommonDAO implements AlertDAOIntf 
{
	private static final long serialVersionUID = 1L;
	public static final RMDLogger logger = RMDLoggerHelper.getLogger(AlertDAOImpl.class);
	
	@Override
	public String activateOrDeactivateAlert(
			List<AlertSubscriptionDetailsVO> rowList) throws RMDDAOException {
		Session objHibernateSession = null;
		Transaction tx = null;
		int executedRow = 0;
		String pass = RMDServiceConstants.FAILURE;
		String alertType = null;
		try {
			objHibernateSession = getHibernateSession();
			tx = objHibernateSession.beginTransaction();
			String customerId = null;
			String regionId = null;
			String fleetId = null;
			String assetId = null;
			String flag = null;
			String shopId = null;
			Query updateQuery = null; 
			String modelId = null;

			for (AlertSubscriptionDetailsVO ppRowList : rowList) {
				if (ppRowList.getRegionid() != null
						&& !ppRowList.getRegionid().isEmpty()) {
					regionId = ppRowList.getRegionid();
					alertType = AlertConstants.ALERT_TYPE_REGION;
					if (ppRowList.getSubDivisionid() != null
							&& !ppRowList.getSubDivisionid().isEmpty()) {
						alertType = AlertConstants.ALERT_TYPE_SUB_DIVISION;
					}
				} else if (ppRowList.getFleetid() != null
						&& !ppRowList.getFleetid().isEmpty()) {
					fleetId = ppRowList.getFleetid();
					alertType = AlertConstants.ALERT_TYPE_FLEET;
				} else if (ppRowList.getVehicleObjId() != null
						&& !ppRowList.getVehicleObjId().isEmpty()) {
					assetId = ppRowList.getVehicleObjId();
					alertType = AlertConstants.ALERT_TYPE_ASSET;
				} else if (ppRowList.getShopid() != null
						&& !ppRowList.getShopid().isEmpty()) {
					shopId = ppRowList.getShopid();
					alertType = AlertConstants.ALERT_TYPE_SHOP;
				} else if (null != ppRowList.getModelId()
						&& !ppRowList.getModelId().isEmpty()) {
					modelId = ppRowList.getModelId();
					alertType = AlertConstants.ALERT_TYPE_MODEL;
				} else if (ppRowList.getCustomerAlertObjId() != null
						&& !ppRowList.getCustomerAlertObjId().isEmpty()) {
					customerId = ppRowList.getCustomerAlertObjId();
					alertType = AlertConstants.ALERT_TYPE_CUSTOMER;
				}

				flag = ppRowList.getStatus();

				if (alertType
						.equalsIgnoreCase(AlertConstants.ALERT_TYPE_CUSTOMER)) {
					if (objHibernateSession != null) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE GET_USR.GET_USR_CUSTOMER_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :customerAlertObjId");
						if (null != customerId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(customerId)) {
							updateQuery.setParameter(
									AlertConstants.CUSTOMER_ALERT_OBJ_ID,
									customerId);
						}
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						executedRow = updateQuery.executeUpdate();
					}
				} else if (alertType
						.equalsIgnoreCase(AlertConstants.ALERT_TYPE_REGION)) {
					if (objHibernateSession != null) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE  GET_USR.GET_USR_REGION_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :regionId");
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != regionId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(regionId)) {
							updateQuery.setParameter(
									AlertConstants.REGIONID, regionId);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						executedRow = updateQuery.executeUpdate();
					}

				} else if (alertType
						.equalsIgnoreCase(AlertConstants.ALERT_TYPE_FLEET)) {
					if (objHibernateSession != null) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE GET_USR.GET_USR_FLEET_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :fleetId");
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != fleetId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(fleetId)) {
							updateQuery.setParameter(
									AlertConstants.FLEETID, fleetId);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						executedRow = updateQuery.executeUpdate();
					}

				} else if (alertType
						.equalsIgnoreCase(AlertConstants.ALERT_TYPE_ASSET)) {
					if (objHibernateSession != null) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE GET_USR.GET_USR_VEHICLE_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :assetId");
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != assetId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(assetId)) {
							updateQuery.setParameter(
									AlertConstants.ASSETID, assetId);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						executedRow = updateQuery.executeUpdate();
					}

				} else if (alertType
						.equals(AlertConstants.ALERT_TYPE_SHOP)) {

					if (objHibernateSession != null) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE GET_USR.GET_USR_SHOP_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :shopId");
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						if (null != shopId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(shopId)) {
							updateQuery.setParameter(AlertConstants.SHOPID,
									shopId);
						}
						executedRow = updateQuery.executeUpdate();
					}
				} else if (alertType
						.equals(AlertConstants.ALERT_TYPE_MODEL) && null != objHibernateSession) {
						updateQuery = objHibernateSession
								.createSQLQuery("UPDATE GET_USR.GET_USR_MODEL_ALERT SET ACTIVE_FLAG = :statusFlag, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE WHERE OBJID = :modelId");
						if (null != flag
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(flag)) {
							updateQuery.setParameter(
									AlertConstants.STATUS_FLAG, flag);
						}
						if (null != ppRowList.getUserid()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(ppRowList.getUserid())) {
							updateQuery.setParameter(RMDCommonConstants.USERID,
									ppRowList.getUserid());
						}
						if (null != modelId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(modelId)) {
							updateQuery.setParameter(
									RMDCommonConstants.MODELID, modelId);
						}
						executedRow = updateQuery.executeUpdate();
					}			
			}
			if (executedRow > 0) {
				pass = RMDServiceConstants.SUCCESS;
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return pass;
	}

	@Override
	public String editAlert(AlertSubscriptionDetailsVO objEdit)
			throws RMDDAOException {
		Session objHibernateSession = null;

		Transaction tx = null;
		int executedRow = 0;
		String pass = RMDServiceConstants.FAILURE;
		String alertType = null;
		String assetId = null;
		String regionId = null;
		String fleetId = null;
		String customerId = null;
		String emailFrmt = null;
		String shopId = null;
		String modelId = null;
		String notificationType = null;

		try {
			objHibernateSession = getHibernateSession();
			tx = objHibernateSession.beginTransaction();
			if (objEdit.getCustomerAlertObjId() != null
					&& !objEdit.getCustomerAlertObjId().isEmpty()) {
				customerId = objEdit.getCustomerAlertObjId();
				alertType = AlertConstants.ALERT_TYPE_CUSTOMER;
			}
			if (objEdit.getRegionid() != null
					&& !objEdit.getRegionid().isEmpty()) {
				regionId = objEdit.getRegionid();
				alertType = AlertConstants.ALERT_TYPE_REGION;
				if (objEdit.getSubDivisionid() != null
						&& !objEdit.getSubDivisionid().isEmpty()) {
					alertType = AlertConstants.ALERT_TYPE_SUB_DIVISION;
				}
			}
			if (objEdit.getFleetid() != null && !objEdit.getFleetid().isEmpty()) {
				fleetId = objEdit.getFleetid();
				alertType = AlertConstants.ALERT_TYPE_FLEET;
			}
			if (objEdit.getVehicleObjId() != null
					&& !objEdit.getVehicleObjId().isEmpty()) {
				assetId = objEdit.getVehicleObjId();
				alertType = AlertConstants.ALERT_TYPE_ASSET;
			}
			if (objEdit.getShopid() != null && !objEdit.getShopid().isEmpty()) {
				shopId = objEdit.getShopid();
				alertType = AlertConstants.ALERT_TYPE_SHOP;
			}
			if (objEdit.getEmailFormat() != null
					&& !objEdit.getEmailFormat().isEmpty()) {
				emailFrmt = objEdit.getEmailFormat();
			}
			if (null != objEdit.getModelId() && !objEdit.getModelId().isEmpty()) {
				modelId = objEdit.getModelId();
				alertType = AlertConstants.ALERT_TYPE_MODEL;
			}
			if (null != objEdit.getNotificationType() && !objEdit.getNotificationType().isEmpty()) {
				notificationType = objEdit.getNotificationType();
			}
			Query customerQuer = null;
			Query regionQuer = null;
			Query fleetQuer = null;
			Query assetQuer = null;

			if (alertType.equals(AlertConstants.ALERT_TYPE_CUSTOMER) && objHibernateSession != null) {
					customerQuer = objHibernateSession
							.createSQLQuery("Update GET_USR.GET_USR_CUSTOMER_ALERT set EMAIL_ATTACHMENT_FORMAT_CD= :emailFormat, NOTIFICATION_TYPE=:notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :customerAlertObjId");
					if (null != customerId
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(customerId)) {
						customerQuer.setParameter(
								AlertConstants.CUSTOMER_ALERT_OBJ_ID,
								customerId);
					}
					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						customerQuer.setParameter(
								AlertConstants.EMAIL_FORMAT, emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						customerQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						customerQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = customerQuer.executeUpdate();

				}

			if (alertType.equals(AlertConstants.ALERT_TYPE_REGION) && objHibernateSession != null) {
					regionQuer = objHibernateSession
							.createSQLQuery("Update  GET_USR.GET_USR_REGION_ALERT set EMAIL_ATTACHMENT_FORMAT_CD = :emailFormat, NOTIFICATION_TYPE = :notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :regionId");
					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						regionQuer.setParameter(
								AlertConstants.EMAIL_FORMAT, emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						regionQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != regionId
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(regionId)) {
						regionQuer.setParameter(AlertConstants.REGIONID,
								regionId);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						regionQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = regionQuer.executeUpdate();
				}
			
			if (alertType.equals(AlertConstants.ALERT_TYPE_FLEET) && objHibernateSession != null) {
					fleetQuer = objHibernateSession
							.createSQLQuery("Update GET_USR.GET_USR_FLEET_ALERT set EMAIL_ATTACHMENT_FORMAT_CD = :emailFormat, NOTIFICATION_TYPE = :notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :fleetId");

					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						fleetQuer.setParameter(AlertConstants.EMAIL_FORMAT,
								emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						fleetQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != fleetId
							&& !RMDCommonConstants.EMPTY_STRING.equals(fleetId)) {
						fleetQuer.setParameter(AlertConstants.FLEETID,
								fleetId);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						fleetQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = fleetQuer.executeUpdate();
				}
		
			if (alertType.equals(AlertConstants.ALERT_TYPE_ASSET) && objHibernateSession != null) {
					assetQuer = objHibernateSession
							.createSQLQuery("Update GET_USR.GET_USR_VEHICLE_ALERT set EMAIL_ATTACHMENT_FORMAT_CD = :emailFormat, NOTIFICATION_TYPE = :notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :assetId ");
					if (null != assetId
							&& !RMDCommonConstants.EMPTY_STRING.equals(assetId)) {
						assetQuer.setParameter(AlertConstants.ASSETID,
								assetId);
					}
					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						assetQuer.setParameter(AlertConstants.EMAIL_FORMAT,
								emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						assetQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						assetQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = assetQuer.executeUpdate();
				}
		
			if (alertType.equals(AlertConstants.ALERT_TYPE_SHOP) && objHibernateSession != null) {
					assetQuer = objHibernateSession
							.createSQLQuery("Update GET_USR.GET_USR_SHOP_ALERT set EMAIL_ATTACHMENT_FORMAT_CD = :emailFormat, NOTIFICATION_TYPE = :notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :shopId ");
					if (null != shopId
							&& !RMDCommonConstants.EMPTY_STRING.equals(shopId)) {
						assetQuer.setParameter(AlertConstants.SHOPID,
								shopId);
					}
					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						assetQuer.setParameter(AlertConstants.EMAIL_FORMAT,
								emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						assetQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						assetQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = assetQuer.executeUpdate();
				}

			if (alertType.equals(AlertConstants.ALERT_TYPE_MODEL) && null != objHibernateSession) {
					assetQuer = objHibernateSession
							.createSQLQuery("Update GET_USR.GET_USR_MODEL_ALERT set EMAIL_ATTACHMENT_FORMAT_CD = :emailFormat, NOTIFICATION_TYPE = :notificationType, LAST_UPDATED_BY=:userId, LAST_UPDATED_DATE=SYSDATE where OBJID = :modelId ");
					if (null != modelId
							&& !RMDCommonConstants.EMPTY_STRING.equals(modelId)) {
						assetQuer.setParameter(RMDCommonConstants.MODELID,
								modelId);
					}
					if (null != emailFrmt
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(emailFrmt)) {
						assetQuer.setParameter(AlertConstants.EMAIL_FORMAT,
								emailFrmt);
					}
					if (null != notificationType
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(notificationType)) {
						assetQuer.setParameter(
								AlertConstants.NOTIFICATION_TYPE, notificationType);
					}
					if (null != objEdit.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(objEdit
									.getUserid())) {
						assetQuer.setParameter(RMDCommonConstants.USERID,
								objEdit.getUserid());
					}
					executedRow = assetQuer.executeUpdate();
				}

			if (executedRow > 0) {
				pass = RMDServiceConstants.SUCCESS;
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}

		return pass;
	}

	@Override
	public String deleteAlert(List<AlertSubscriptionDetailsVO> rowList)
			throws RMDDAOException {
		Session objHibernateSession = null;

		int executedRow = 0;
		String pass = RMDServiceConstants.FAILURE;
		String alertType = null;
		String assetId = null;
		String regionId = null;
		String fleetId = null;
		String customerId = null;
		String shopId = null;
		String modelId = null;
		Transaction tx = null;
		try {
			Query customerQuer = null;
			Query regionQuer = null;
			Query fleetQuer = null;
			Query assetQuer = null;

			objHibernateSession = getHibernateSession();
			tx = objHibernateSession.beginTransaction();
			for (AlertSubscriptionDetailsVO list : rowList) {
				if (list.getRegionid() != null && !list.getRegionid().isEmpty()) {
					regionId = list.getRegionid();
					alertType = AlertConstants.ALERT_TYPE_REGION;
					if (list.getSubDivisionid() != null
							&& !list.getSubDivisionid().isEmpty()) {
						alertType = AlertConstants.ALERT_TYPE_SUB_DIVISION;
					}
				} else if (list.getFleetid() != null
						&& !list.getFleetid().isEmpty()) {
					fleetId = list.getFleetid();
					alertType = AlertConstants.ALERT_TYPE_FLEET;
				} else if (list.getVehicleObjId() != null
						&& !list.getVehicleObjId().isEmpty()) {
					assetId = list.getVehicleObjId();
					alertType = AlertConstants.ALERT_TYPE_ASSET;
				} else if (list.getShopid() != null
						&& !list.getShopid().isEmpty()) {
					shopId = list.getShopid();
					alertType = AlertConstants.ALERT_TYPE_SHOP;
				} else if (null != list.getModelId()
						&& !list.getModelId().isEmpty()) {
					modelId = list.getModelId();
					alertType = AlertConstants.ALERT_TYPE_MODEL;
				} else if (list.getCustomerAlertObjId() != null
						&& !list.getCustomerAlertObjId().isEmpty()) {
					customerId = list.getCustomerAlertObjId();
					alertType = AlertConstants.ALERT_TYPE_CUSTOMER;
				}

				if (alertType.equals(AlertConstants.ALERT_TYPE_CUSTOMER) && objHibernateSession != null) {
						customerQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_CUSTOMER_ALERT where OBJID = :customerAlertObjId");
						if (null != customerId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(customerId)) {
							customerQuer.setParameter(
									AlertConstants.CUSTOMER_ALERT_OBJ_ID,
									customerId);
						}
						executedRow = customerQuer.executeUpdate();
					}				

				if (alertType.equals(AlertConstants.ALERT_TYPE_REGION) && objHibernateSession != null) {
						regionQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_REGION_ALERT where OBJID = :regionId");
						if (null != regionId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(regionId)) {
							regionQuer.setParameter(
									AlertConstants.REGIONID, regionId);
						}
						executedRow = regionQuer.executeUpdate();
					}
				
				if (alertType.equals(AlertConstants.ALERT_TYPE_FLEET) && objHibernateSession != null) {
						fleetQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_FLEET_ALERT where OBJID = :fleetId");
						if (null != fleetId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(fleetId)) {
							fleetQuer.setParameter(AlertConstants.FLEETID,
									fleetId);
						}
						executedRow = fleetQuer.executeUpdate();
					}
				if (alertType.equals(AlertConstants.ALERT_TYPE_ASSET) && objHibernateSession != null) {
						assetQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_VEHICLE_ALERT where OBJID = :assetId");
						if (null != assetId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(assetId)) {
							assetQuer.setParameter(AlertConstants.ASSETID,
									assetId);
						}

						executedRow = assetQuer.executeUpdate();
					}
				if (alertType.equals(AlertConstants.ALERT_TYPE_SHOP) && objHibernateSession != null) {
						fleetQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_SHOP_ALERT where OBJID = :shopId");
						if (null != shopId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(shopId)) {
							fleetQuer.setParameter(AlertConstants.SHOPID,
									shopId);
						}
						executedRow = fleetQuer.executeUpdate();
					}
				if (alertType.equals(AlertConstants.ALERT_TYPE_MODEL) && null != objHibernateSession) {
						fleetQuer = objHibernateSession
								.createSQLQuery("Delete from GET_USR.GET_USR_MODEL_ALERT where OBJID = :modelId");
						if (null != modelId
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(modelId)) {
							fleetQuer.setParameter(RMDCommonConstants.MODELID,
									modelId);
						}
						executedRow = fleetQuer.executeUpdate();
				}
			}
			if (executedRow > 0) {
				pass = RMDServiceConstants.SUCCESS;
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}

		return pass;
	}

	@Override
	public String updatephone(List<UpdatePhoneVO> updatePhoneVO)
			throws RMDDAOException {		
		Session objHibernateSession = null;
		String pass = RMDServiceConstants.FAILURE;
		/*String userId = updatePhoneVO.getUserId();
		String userPhoneNo = updatePhoneVO.getUserPhoneNo();
		String userPhoneCountryCode = updatePhoneVO.getUserPhoneCountryCode();*/
		Query updateQuery = null;
		int executedRow = 0;
		String queryString = "";
		int i;
		
		if(updatePhoneVO.size()<=0)
		{
			pass = RMDServiceConstants.FAILURE;
			return pass;
		}
		try {
			objHibernateSession = getHibernateSession();
			queryString+="UPDATE GET_USR_USERS SET USER_PHONE_NUMBER = CASE ";
			for(i=0;i<updatePhoneVO.size();i++)
			{
				queryString+="WHEN USER_ID = ";
				queryString+=":userId";
				queryString+=Integer.toString(i);
				queryString+=" THEN ";
				queryString+=":userPhoneNo";
				queryString+=Integer.toString(i);
				queryString+=" ";
			}
			queryString+="END , USER_COUNTRY_CODE = CASE ";
			for(i=0;i<updatePhoneVO.size();i++)
			{
				queryString+="WHEN USER_ID = ";
				queryString+=":userId";
				queryString+=Integer.toString(i);
				queryString+=" THEN ";
				queryString+=":userPhoneCountryCode";
				queryString+=Integer.toString(i);
				queryString+=" ";
			}
			queryString+="END WHERE USER_ID IN (";
			for(i=0;i<updatePhoneVO.size()-1;i++)
			{
				queryString+=":userId";
				queryString+=Integer.toString(i);
				queryString+=",";
			}
			queryString+=":userId";
			queryString+=Integer.toString(updatePhoneVO.size()-1);
			queryString+=")";
			updateQuery = objHibernateSession
					.createSQLQuery(queryString);
			i=0;
			String temp="";
			for(UpdatePhoneVO up : updatePhoneVO)
			{
				if(up.getUserPhoneNo()==null)
					up.setUserPhoneNo("");
				if(up.getUserPhoneCountryCode()==null)
					up.setUserPhoneCountryCode("");
				temp = AlertConstants.USER_ID+Integer.toString(i);
				updateQuery.setParameter(temp,up.getUserId());
				temp = AlertConstants.USER_PHONE_NO+Integer.toString(i);
				updateQuery.setParameter(temp,up.getUserPhoneNo());
				temp = AlertConstants.USER_PHONE_COUNTRY_CODE+Integer.toString(i);
				updateQuery.setParameter(temp,up.getUserPhoneCountryCode());
				i++;
			}
			executedRow = updateQuery.executeUpdate();
			/*if (executedRow > 0) 
			{
				pass = RMDServiceConstants.SUCCESS;
			}*/
			pass = RMDServiceConstants.SUCCESS;
		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return pass;
	}
	
	@Override
	public String addAlertSubscriptionData(
			AlertSubscriptionDetailsVO ppalertSubscriptionDetails)
			throws RMDDAOException {
		Session objHibernateSession = null;
		String returnStr = RMDServiceConstants.FAILURE;
		objHibernateSession = getHibernateSession();
		Transaction tx = objHibernateSession.beginTransaction();
		StringBuilder insertQueryStr = null;
		String multiUsers = null;
		StringBuilder strMultiUsers = null;
		StringBuilder strcurrentUser = null;
		Map<String, String> duplicateUser = null;
		String[] userDetailsArr = null;
		String[] userDetailsArrStr = null;
		List<String> alertConfObjId = null;
		try {
			// For getting SA.TABLE_BUS_ORG table OBJID
			List<Object[]> customerList = null;
			String customerObjId = null;
			if (null != objHibernateSession) {
				Query customerQuery = objHibernateSession
						.createSQLQuery("SELECT OBJID, ORG_ID FROM SA.TABLE_BUS_ORG WHERE ORG_ID = :customerID");
				if (null != ppalertSubscriptionDetails.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(ppalertSubscriptionDetails
										.getCustomerId())) {
					customerQuery.setParameter(RMDCommonConstants.CUSTOMERID,
							ppalertSubscriptionDetails.getCustomerId());
				}
				customerList = (ArrayList) customerQuery.list();
				customerQuery = null;
			}
			if (RMDCommonUtility.isCollectionNotEmpty(customerList)) {
				for (Object[] obj : customerList) {
					if (RMDCommonUtility.convertObjectToString(obj[0]) != null
							&& !RMDCommonUtility.convertObjectToString(obj[0])
									.trim().equals(RMDCommonConstants.EMPTY_STRING)) {
						customerObjId = RMDCommonUtility
								.convertObjectToString(obj[0]);
					}
				}
			}
			List<String> srcAlertList = null;
			List<String> rxTypeList = null;
			if (null != ppalertSubscriptionDetails.getService()
					&& ppalertSubscriptionDetails.getService().equals(
							AlertConstants.EOA_SERVICE)) {
				if (ppalertSubscriptionDetails.getValue() != null
						&& !ppalertSubscriptionDetails.getValue().equals(
								RMDServiceConstants.EMPTY_TEXT)) {
					String[] alertArray = ppalertSubscriptionDetails.getValue()
							.split(RMDCommonConstants.COMMMA_SEPARATOR);
					srcAlertList = Arrays.asList(alertArray);

				}
				String[] rxTypeArray = ppalertSubscriptionDetails.getRxType()
						.split(RMDCommonConstants.COMMMA_SEPARATOR);
				rxTypeList = Arrays.asList(rxTypeArray);

			} else {
				if (ppalertSubscriptionDetails.getAlertSubscribed() != null
						&& !ppalertSubscriptionDetails.getAlertSubscribed()
								.equals(RMDServiceConstants.EMPTY_TEXT)) {
					String[] alertArray = ppalertSubscriptionDetails
							.getAlertSubscribed().split(
									RMDCommonConstants.COMMMA_SEPARATOR);
					srcAlertList = Arrays.asList(alertArray);

				}
			}

			if (ppalertSubscriptionDetails.isMultiSubscriptionPrivilege()) {
				multiUsers = ppalertSubscriptionDetails.getMultiUsers();
			}

			strcurrentUser = new StringBuilder();

			strcurrentUser = strcurrentUser.append(ppalertSubscriptionDetails
					.getUserid());
			strcurrentUser = strcurrentUser.append(RMDCommonConstants.TILDA);
			strcurrentUser = strcurrentUser.append(ppalertSubscriptionDetails
					.getUserSeqId());

			String[] currentUserDetails = { strcurrentUser.toString() };

			if (multiUsers == null || multiUsers.trim().length() == 0) {
				userDetailsArr = currentUserDetails;
			} else {
				strMultiUsers = new StringBuilder();
				strMultiUsers = strMultiUsers.append(multiUsers);
				multiUsers = strMultiUsers.toString();
				userDetailsArrStr = multiUsers.split(RMDCommonConstants.COMMMA_SEPARATOR);
				userDetailsArr = userDetailsArrStr;
			}

			duplicateUser = new HashMap<String, String>(userDetailsArr.length);
			for (String multiUser : userDetailsArr) {
				String[] strMultiUser = multiUser
						.split(RMDCommonConstants.TILDA);
				ppalertSubscriptionDetails.setUserid(strMultiUser[0]);
				ppalertSubscriptionDetails.setUserSeqId(strMultiUser[1]);
				if (!duplicateUser.containsKey(ppalertSubscriptionDetails
						.getUserid())) {

					for (String alertSubscribed : srcAlertList) {
						List<Object[]> alertList = null;
						boolean hasDuplicateAlertConf = false;
						String alertConfSeqId = null;
						alertConfObjId = new ArrayList<String>();
						if (null != ppalertSubscriptionDetails.getService()
								&& ppalertSubscriptionDetails.getService()
										.equals(AlertConstants.EOA_SERVICE)) {
							for (String rxTypeSubscribed : rxTypeList) {

							if (null != objHibernateSession) {
								StringBuilder alertbuff = null;
								alertbuff = new StringBuilder();
								alertbuff
										.append("SELECT ALERTCONF.OBJID, RXALERT.OBJID ");
								alertbuff
										.append("FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_RX_ALERT RXALERT ");
								alertbuff
										.append("WHERE ALERTCONF.NOTIFY_ALERT_CONF2RX_ALERT = RXALERT.OBJID ");
								alertbuff
										.append("AND UPPER(ALERTCONF.NOTIFY_EVENT_TYPE) = :eventType ");
								alertbuff
										.append(AlertConstants.QUERY_ALERTCONF_SERVICE);
								alertbuff
										.append("AND ALERTCONF.NOTIFY_ALERT_CONF2USERS_SEQID =:userSeqId AND RXALERT.SUBSCRIBED_ALERTS = :rxsubscribedAlert ");
								if (null != ppalertSubscriptionDetails
										.getRxSubscribedAlerts()
										&& ppalertSubscriptionDetails
												.getRxSubscribedAlerts()
												.equalsIgnoreCase(
														AlertConstants.TITLES)) {
									alertbuff
											.append(AlertConstants.QUERY_RXALERT_VALUE2SD_RECOM);
								} else {
									alertbuff
											.append(AlertConstants.QUERY_RXALERT_VALUE2RMD_LOOKUP);
								}
								if (null != rxTypeSubscribed
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(rxTypeSubscribed)) {
									alertbuff
										.append("AND RXALERT.RX_TYPE = :rxTypeSubscribed ");
								}
								Query alertQuery = objHibernateSession
										.createSQLQuery(alertbuff.toString());
								if (null != ppalertSubscriptionDetails
										.getAlertSubscribed()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getAlertSubscribed())) {
									alertQuery.setParameter(
											AlertConstants.EVENT_TYPE,
											ppalertSubscriptionDetails
													.getAlertSubscribed());
								}
								if (null != ppalertSubscriptionDetails
										.getUserSeqId()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getUserSeqId())) {
 									alertQuery.setParameter(
											RMDCommonConstants.USER_SEQ_ID,
											ppalertSubscriptionDetails
													.getUserSeqId());
								}
 								if (null != ppalertSubscriptionDetails
										.getService()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getService())) {
									alertQuery.setParameter(
											AlertConstants.SERVICE,
											ppalertSubscriptionDetails
													.getService());
								}
								if (null != ppalertSubscriptionDetails
										.getRxSubscribedAlerts()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getRxSubscribedAlerts())) {
									alertQuery
											.setParameter(
													AlertConstants.RXSUBSCRIBED_ALERT,
													ppalertSubscriptionDetails
															.getRxSubscribedAlerts());
								}
								if (null != ppalertSubscriptionDetails
										.getRxSubscribedAlerts()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getRxSubscribedAlerts())) {
									alertQuery
											.setParameter(
													AlertConstants.RXSUBSCRIBED_ALERT,
													ppalertSubscriptionDetails
															.getRxSubscribedAlerts());
								}
								if (null != alertSubscribed
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertSubscribed)) {
									alertQuery.setParameter(
											AlertConstants.RX_ALERT_VALUE,
											alertSubscribed);
								}
								if (null != rxTypeSubscribed
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(rxTypeSubscribed)) {
									alertQuery.setParameter(
											AlertConstants.RX_TYPE_VALUE,
											rxTypeSubscribed);
								}
								alertList = (ArrayList) alertQuery.list();
							}
							if (alertList != null && !alertList.isEmpty()) {
								hasDuplicateAlertConf = true;
								for (Object[] obj : alertList) {
									if (RMDCommonUtility
											.convertObjectToString(obj[0]) != null
											&& !RMDCommonUtility
													.convertObjectToString(
															obj[0]).trim()
													.equals(RMDCommonConstants.EMPTY_STRING)) {
										alertConfSeqId = RMDCommonUtility
												.convertObjectToString(obj[0]);
									}
								}
							}
							if (!hasDuplicateAlertConf) {
								insertQueryStr = new StringBuilder();
								// Adding entry to GET_USR_RX_ALERT
								if (null != objHibernateSession) {
									insertQueryStr
											.append("insert into GET_USR.GET_USR_RX_ALERT(OBJID, CREATED_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE, CREATED_BY, SUBSCRIBED_ALERTS,");
									if (null != rxTypeSubscribed
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(rxTypeSubscribed)) {
										insertQueryStr.append("RX_TYPE,");
									}
									if (null != ppalertSubscriptionDetails
											.getRxSubscribedAlerts()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())
											&& (AlertConstants.TITLES)
													.equalsIgnoreCase(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())) {
										insertQueryStr
												.append("VALUE2SD_RECOM) values ");
									} else {
										insertQueryStr
												.append("VALUE2RMD_LOOKUP) values ");
									}
									insertQueryStr
											.append(" (GET_USR.GET_USR_RX_ALERT_SEQ.nextval,SYSDATE,");
									insertQueryStr
											.append(" :userId,SYSDATE,:userId,:rxsubscribedAlert,");
									if (null != rxTypeSubscribed
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(rxTypeSubscribed)) {
										insertQueryStr.append(":rxTypeSubscribed, ");
									}
									if (null != ppalertSubscriptionDetails
											.getRxSubscribedAlerts()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())
											&& (AlertConstants.TITLES)
													.equalsIgnoreCase(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())) {
										insertQueryStr.append(":sdRecomeval)");
									} else {
										insertQueryStr.append(":lookupVal)");
									}
									Query rxAlertQuery = objHibernateSession
											.createSQLQuery(insertQueryStr
													.toString());

									if (null != ppalertSubscriptionDetails
											.getUserid()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserid())) {
										rxAlertQuery.setParameter(
												RMDCommonConstants.USERID,
												ppalertSubscriptionDetails
														.getUserid());
									}
									if (null != ppalertSubscriptionDetails
											.getRxSubscribedAlerts()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())
											&& (AlertConstants.TITLES)
													.equalsIgnoreCase(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())) {
										if (null != ppalertSubscriptionDetails
												.getService()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getService())) {
											rxAlertQuery
													.setParameter(
															AlertConstants.SD_RECOME_VAL,
															alertSubscribed);
										}
									} else {
										if (null != ppalertSubscriptionDetails
												.getService()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getService())) {
											rxAlertQuery
													.setParameter(
															AlertConstants.LOOKUP_VAL,
															alertSubscribed);
										}

									}
									if (null != ppalertSubscriptionDetails
											.getRxSubscribedAlerts()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getRxSubscribedAlerts())) {
										rxAlertQuery
												.setParameter(
														AlertConstants.RXSUBSCRIBED_ALERT,
														ppalertSubscriptionDetails
																.getRxSubscribedAlerts());
									}
									if (null != rxTypeSubscribed
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(rxTypeSubscribed)) {
										rxAlertQuery
												.setParameter(
														AlertConstants.RX_TYPE_VALUE,
														rxTypeSubscribed);
									}
									rxAlertQuery.executeUpdate();
								}

								insertQueryStr = new StringBuilder();
								// Adding entry to GET_USR_NOTIFY_ALERT_CONF
								if (null != objHibernateSession) {
									insertQueryStr
											.append(AlertConstants.INSERT_GET_USR_NOTIFY_ALERT_CONF);
									insertQueryStr
											.append(AlertConstants.QUERY_INSERT_GET_USR_NOTIFY_ALERT_CONF_EOA);
									insertQueryStr
											.append(" :eventType,:userSeqId,SYSDATE,:userId,SYSDATE,:userId, ");
									insertQueryStr
											.append(" GET_USR.GET_USR_RX_ALERT_SEQ.currval,null ) ");

									Query customerQuery = objHibernateSession
											.createSQLQuery(insertQueryStr
													.toString());

									if (null != ppalertSubscriptionDetails
											.getService()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getService())) {
										customerQuery.setParameter(
												AlertConstants.SERVICE,
												ppalertSubscriptionDetails
														.getService());
									}
									if (null != ppalertSubscriptionDetails
											.getAlertSubscribed()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getAlertSubscribed())) {
										customerQuery.setParameter(
												AlertConstants.EVENT_TYPE,
												ppalertSubscriptionDetails
														.getAlertSubscribed());
									}
									if (null != ppalertSubscriptionDetails
											.getUserSeqId()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserSeqId())) {
										customerQuery.setParameter(
												RMDCommonConstants.USER_SEQ_ID,
												ppalertSubscriptionDetails
														.getUserSeqId());
									}
									if (null != ppalertSubscriptionDetails
											.getUserid()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserid())) {
										customerQuery.setParameter(
												RMDCommonConstants.USERID,
												ppalertSubscriptionDetails
														.getUserid());
									}
									
									StringBuilder recordQuery = new StringBuilder();
									String objid = null;
										recordQuery.append("select GET_USR.GET_USR_NOTIFY_ALERT_CONF_SEQ.nextVal from GET_USR.GET_USR_NOTIFY_ALERT_CONF");
										Query recordQueryHiber = objHibernateSession
												.createSQLQuery(recordQuery
														.toString());
										objid = recordQueryHiber.list().get(0).toString();
										if (null != objid
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(objid)) {
											customerQuery.setParameter(
													RMDCommonConstants.OBJ_ID,
													objid);
										}
										alertConfObjId.add(objid);
										customerQuery.executeUpdate();
								}
							}
							}
						}
						 else if (null != ppalertSubscriptionDetails
								.getService()
								&& ppalertSubscriptionDetails
										.getService()
										.equals(AlertConstants.CONFIG_ALERT_SERVICE)) {
							if (null != objHibernateSession) {
								StringBuilder alertbuff = null;
								alertbuff = new StringBuilder();
								alertbuff
										.append("SELECT ALERTCONF.OBJID, ALERTCONF.CREATED_DATE ");
								alertbuff
										.append("FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF ");
								alertbuff
										.append("WHERE ALERTCONF.NOTIFY_EVENT_TYPE = 'Rule Title' ");
								alertbuff
										.append(AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID);
								alertbuff
										.append(AlertConstants.QUERY_ALERTCONF_SERVICE);
								alertbuff
										.append("AND NTFY_ALRT_CNF2RULEHIS_ORGNL_ID = :eventType ");
								Query alertQuery = objHibernateSession
										.createSQLQuery(alertbuff.toString());
								if (null != alertSubscribed
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertSubscribed)) {
									alertQuery.setParameter(
											AlertConstants.EVENT_TYPE,
											alertSubscribed);
								}
								if (null != ppalertSubscriptionDetails
										.getUserSeqId()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getUserSeqId())) {
									alertQuery.setParameter(
											RMDCommonConstants.USER_SEQ_ID,
											ppalertSubscriptionDetails
													.getUserSeqId());
								}
								if (null != ppalertSubscriptionDetails
										.getService()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getService())) {
									alertQuery.setParameter(
											AlertConstants.SERVICE,
											ppalertSubscriptionDetails
													.getService());
								}
								alertList = (ArrayList) alertQuery.list();
							}
							if (alertList != null && !alertList.isEmpty()) {
								hasDuplicateAlertConf = true;
								for (Object[] obj : alertList) {
									if (RMDCommonUtility
											.convertObjectToString(obj[0]) != null
											&& !RMDCommonUtility
													.convertObjectToString(
															obj[0]).trim()
													.equals(RMDCommonConstants.EMPTY_STRING)) {
										alertConfSeqId = RMDCommonUtility
												.convertObjectToString(obj[0]);
									}
								}
							}

							insertQueryStr = new StringBuilder();
							if (!hasDuplicateAlertConf && null != objHibernateSession) {
								// Adding entry to GET_USR_NOTIFY_ALERT_CONF
									insertQueryStr
											.append(AlertConstants.INSERT_GET_USR_NOTIFY_ALERT_CONF);
									insertQueryStr
											.append(AlertConstants.QUERY_INSERT_GET_USR_NOTIFY_ALERT_CONF);
									insertQueryStr
											.append(" 'Rule Title',:userSeqId,SYSDATE,:userId,SYSDATE,:userId,null,:eventType) ");

									Query customerQuery = objHibernateSession
											.createSQLQuery(insertQueryStr
													.toString());

									if (null != ppalertSubscriptionDetails
											.getService()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getService())) {
										customerQuery.setParameter(
												AlertConstants.SERVICE,
												ppalertSubscriptionDetails
														.getService());
									}
									if (null != alertSubscribed
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(alertSubscribed)) {
										customerQuery.setParameter(
												AlertConstants.EVENT_TYPE,
												alertSubscribed);
									}
									if (null != ppalertSubscriptionDetails
											.getUserSeqId()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserSeqId())) {
										customerQuery.setParameter(
												RMDCommonConstants.USER_SEQ_ID,
												ppalertSubscriptionDetails
														.getUserSeqId());
									}
									if (null != ppalertSubscriptionDetails
											.getUserid()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserid())) {
										customerQuery.setParameter(
												RMDCommonConstants.USERID,
												ppalertSubscriptionDetails
														.getUserid());
									}
									customerQuery.executeUpdate();
								}
						} else {
							if (null != objHibernateSession) {
								StringBuilder alertbuff = null;
								alertbuff = new StringBuilder();
								alertbuff
										.append("SELECT ALERTCONF.OBJID, ALERTCONF.CREATED_DATE ");
								alertbuff
										.append("FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF ");
								alertbuff
										.append("WHERE UPPER(ALERTCONF.NOTIFY_EVENT_TYPE) = :eventType ");
								alertbuff
										.append(AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID);
								alertbuff
										.append(AlertConstants.QUERY_ALERTCONF_SERVICE);
								Query alertQuery = objHibernateSession
										.createSQLQuery(alertbuff.toString());
								if (null != alertSubscribed
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertSubscribed)) {
									alertQuery.setParameter(
											AlertConstants.EVENT_TYPE,
											alertSubscribed);
								}
								if (null != ppalertSubscriptionDetails
										.getUserSeqId()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getUserSeqId())) {
									alertQuery.setParameter(
											RMDCommonConstants.USER_SEQ_ID,
											ppalertSubscriptionDetails
													.getUserSeqId());
								}
								if (null != ppalertSubscriptionDetails
										.getService()
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(ppalertSubscriptionDetails
														.getService())) {
									alertQuery.setParameter(
											AlertConstants.SERVICE,
											ppalertSubscriptionDetails
													.getService());
								}
								alertList = (ArrayList) alertQuery.list();
							}
							if (alertList != null && !alertList.isEmpty()) {
								hasDuplicateAlertConf = true;
								for (Object[] obj : alertList) {
									if (RMDCommonUtility
											.convertObjectToString(obj[0]) != null
											&& !RMDCommonUtility
													.convertObjectToString(
															obj[0]).trim()
													.equals(RMDCommonConstants.EMPTY_STRING)) {
										alertConfSeqId = RMDCommonUtility
												.convertObjectToString(obj[0]);
									}
								}
							}

							insertQueryStr = new StringBuilder();
							if (!hasDuplicateAlertConf && null != objHibernateSession) {

								// Adding entry to GET_USR_NOTIFY_ALERT_CONF
									insertQueryStr
											.append(AlertConstants.INSERT_GET_USR_NOTIFY_ALERT_CONF);
									insertQueryStr
											.append(AlertConstants.QUERY_INSERT_GET_USR_NOTIFY_ALERT_CONF);
									insertQueryStr
											.append(" :eventType,:userSeqId,SYSDATE,:userId,SYSDATE,:userId,null,null) ");

									Query customerQuery = objHibernateSession
											.createSQLQuery(insertQueryStr
													.toString());

									if (null != ppalertSubscriptionDetails
											.getService()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getService())) {
										customerQuery.setParameter(
												AlertConstants.SERVICE,
												ppalertSubscriptionDetails
														.getService());
									}
									if (null != alertSubscribed
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(alertSubscribed)) {
										customerQuery.setParameter(
												AlertConstants.EVENT_TYPE,
												alertSubscribed);
									}
									if (null != ppalertSubscriptionDetails
											.getUserSeqId()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserSeqId())) {
										customerQuery.setParameter(
												RMDCommonConstants.USER_SEQ_ID,
												ppalertSubscriptionDetails
														.getUserSeqId());
									}
									if (null != ppalertSubscriptionDetails
											.getUserid()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserid())) {
										customerQuery.setParameter(
												RMDCommonConstants.USERID,
												ppalertSubscriptionDetails
														.getUserid());
									}

									customerQuery.executeUpdate();
								}
						}
						String alertType = ppalertSubscriptionDetails
								.getAlertType();
						if (ppalertSubscriptionDetails.getAlertType() != null
								&& !ppalertSubscriptionDetails.getAlertType()
										.equals(RMDServiceConstants.EMPTY_TEXT)) {
							boolean hasCustomerAlertEntry = false;
							if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_CUSTOMER)) {
								if (null != objHibernateSession) {
									Query alertQuery = null;
									StringBuilder alertbuffer = new StringBuilder();
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										alertbuffer
												.append(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER);
										alertbuffer
												.append("FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT,GET_USR.GET_USR_RX_ALERT RXALERT , ");
										alertbuffer
												.append(AlertConstants.QUERY_NOTIFY_EVENT_TYPE);
										alertbuffer
												.append(AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID);
										alertbuffer
												.append(AlertConstants.QUERY_CUST_OBJID);
										alertbuffer
												.append(AlertConstants.QUERY_CUSTALERT_EMAIL_ATTACHMENT_FORMAT_CD);
										alertbuffer
												.append("AND ALERTCONF.NOTIFY_ALERT_CONF2RX_ALERT = RXALERT.OBJID ");
										alertbuffer
												.append(AlertConstants.QUERY_CUSTALERT_CONF_ID2NOTIFY_ALERT_CONF);
										alertbuffer
												.append(AlertConstants.QUERY_CUSTALERT_CUST_ID2BUS_ORG);
										alertbuffer
												.append("AND RXALERT.SUBSCRIBED_ALERTS=:rxsubscribedAlert ");
										if (null != ppalertSubscriptionDetails
												.getRxSubscribedAlerts()
												&& ppalertSubscriptionDetails
														.getRxSubscribedAlerts()
														.equals(AlertConstants.TITLES)) {
											alertbuffer
													.append(AlertConstants.QUERY_RXALERT_VALUE2SD_RECOM);
										} else {
											alertbuffer
													.append(AlertConstants.QUERY_RXALERT_VALUE2RMD_LOOKUP);
										}
										if (null != alertbuffer) {
											alertQuery = objHibernateSession
													.createSQLQuery(alertbuffer
															.toString());
										}
									}

									else if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.CONFIG_ALERT_SERVICE)) {
										alertQuery = objHibernateSession
												.createSQLQuery(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER
														+ "FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT, "
														+ "SA.TABLE_BUS_ORG CUST WHERE NOTIFY_EVENT_TYPE = 'Rule Title' "
														+ AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID
														+ AlertConstants.QUERY_CUST_OBJID
														+ AlertConstants.QUERY_CUSTALERT_EMAIL_ATTACHMENT_FORMAT_CD
														+ AlertConstants.QUERY_CUSTALERT_CONF_ID2NOTIFY_ALERT_CONF
														+ AlertConstants.QUERY_CUSTALERT_CUST_ID2BUS_ORG
														+ "AND ALERTCONF.NTFY_ALRT_CNF2RULEHIS_ORGNL_ID = :eventType ");
									} else {
										alertQuery = objHibernateSession
												.createSQLQuery(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER
														+ "FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT, "
														+ AlertConstants.QUERY_NOTIFY_EVENT_TYPE
														+ AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID
														+ AlertConstants.QUERY_CUST_OBJID
														+ AlertConstants.QUERY_CUSTALERT_EMAIL_ATTACHMENT_FORMAT_CD
														+ AlertConstants.QUERY_CUSTALERT_CONF_ID2NOTIFY_ALERT_CONF
														+ AlertConstants.QUERY_CUSTALERT_CUST_ID2BUS_ORG);
									}
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										if (null != ppalertSubscriptionDetails
												.getAlertSubscribed()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getAlertSubscribed())) {
											alertQuery
													.setParameter(
															AlertConstants.EVENT_TYPE,
															ppalertSubscriptionDetails
																	.getAlertSubscribed());
										}
									} else {
										if (null != alertSubscribed
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(alertSubscribed)) {
											alertQuery
													.setParameter(
															AlertConstants.EVENT_TYPE,
															alertSubscribed);
										}
									}
									if (null != ppalertSubscriptionDetails
											.getUserSeqId()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserSeqId())) {
										alertQuery.setParameter(
												RMDCommonConstants.USER_SEQ_ID,
												ppalertSubscriptionDetails
														.getUserSeqId());
									}
									if (null != customerObjId
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(customerObjId)) {
										alertQuery
												.setParameter(
														AlertConstants.CUSTOMER_OBJ_ID,
														customerObjId);
									}
									if (null != ppalertSubscriptionDetails
											.getEmailFormat()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getEmailFormat())) {
										alertQuery
												.setParameter(
														AlertConstants.EMAIL_FORMAT,
														ppalertSubscriptionDetails
																.getEmailFormat());
									}
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										if (null != ppalertSubscriptionDetails
												.getRxSubscribedAlerts()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getRxSubscribedAlerts())) {
											alertQuery
													.setParameter(
															AlertConstants.RXSUBSCRIBED_ALERT,
															ppalertSubscriptionDetails
																	.getRxSubscribedAlerts());
										}
										if (null != alertSubscribed
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(alertSubscribed)) {
											alertQuery
													.setParameter(
															AlertConstants.RX_ALERT_VALUE,
															alertSubscribed);
										}
									}
									alertList = (ArrayList) alertQuery.list();
								}
								if (alertList != null && !alertList.isEmpty()) {
									hasCustomerAlertEntry = true;
								}
								if(null != ppalertSubscriptionDetails
										.getService()
										&& ppalertSubscriptionDetails
												.getService()
												.equals(AlertConstants.EOA_SERVICE)){
									for(String objId : alertConfObjId){
										if (!hasCustomerAlertEntry && null != objHibernateSession) {
											
											insertQueryStr = new StringBuilder();
											insertQueryStr
												.append("insert into GET_USR.GET_USR_CUSTOMER_ALERT(OBJID, CUST_ID2BUS_ORG, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values  ");
											insertQueryStr
												.append(" (GET_USR.GET_USR_CUSTOMER_ALERT_SEQ.nextval,:customerObjId,:activeFlag,:emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType,");
												insertQueryStr
												.append(AlertConstants.OBJID);
								
											Query customerQuery = objHibernateSession
													.createSQLQuery(insertQueryStr
															.toString());

											if (null != customerObjId
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(customerObjId)) {
												customerQuery
														.setParameter(
																AlertConstants.CUSTOMER_OBJ_ID,
																customerObjId);
											}
											customerQuery
													.setParameter(
															RMDCommonConstants.ACTIVEFLAG,
															RMDCommonConstants.Y_LETTER_UPPER);

											if (null != ppalertSubscriptionDetails
													.getEmailFormat()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getEmailFormat())) {
												customerQuery
														.setParameter(
																AlertConstants.EMAIL_FORMAT,
																ppalertSubscriptionDetails
																		.getEmailFormat());
											}
											if (null != ppalertSubscriptionDetails
													.getNotificationType()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getNotificationType())) {
												customerQuery
														.setParameter(
																AlertConstants.NOTIFICATION_TYPE,
																ppalertSubscriptionDetails
																		.getNotificationType());
											}
											customerQuery
													.setParameter(
															AlertConstants.EMAIL_FLAG,
															RMDCommonConstants.Y_LETTER_UPPER);
											if (null != ppalertSubscriptionDetails
													.getSubscriptionModel()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getSubscriptionModel())) {
												customerQuery
														.setParameter(
																AlertConstants.SUBSCRIPTION_MODEL,
																ppalertSubscriptionDetails
																		.getSubscriptionModel());
											}
											if (null != ppalertSubscriptionDetails
													.getUserid()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getUserid())) {
												customerQuery.setParameter(
														RMDCommonConstants.USERID,
														ppalertSubscriptionDetails
																.getUserid());
											}
					
											if (null != objId
													&& !RMDCommonConstants.EMPTY_STRING
													.equals(objId)) {
													customerQuery
															.setParameter(
																	AlertConstants.OBJ_ID,
																	objId);
											}
											customerQuery.executeUpdate();
									}
									}
								}
								else {
								if (!hasCustomerAlertEntry && null != objHibernateSession) {
									
										insertQueryStr = new StringBuilder();
										insertQueryStr
											.append("insert into GET_USR.GET_USR_CUSTOMER_ALERT(OBJID, CUST_ID2BUS_ORG, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values  ");
										insertQueryStr
											.append(" (GET_USR.GET_USR_CUSTOMER_ALERT_SEQ.nextval,:customerObjId,:activeFlag,:emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType,");
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query customerQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != customerObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(customerObjId)) {
											customerQuery
													.setParameter(
															AlertConstants.CUSTOMER_OBJ_ID,
															customerObjId);
										}
										customerQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);

										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											customerQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											customerQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										customerQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											customerQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											customerQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
				
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												customerQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
										}
										customerQuery.executeUpdate();
								}
							}
							} else {
								if (null != objHibernateSession) {
									Query alertQuery = null;
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										StringBuilder alertbuffer = new StringBuilder();
										alertbuffer
												.append(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER);
										alertbuffer
												.append("FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT,GET_USR.GET_USR_RX_ALERT RXALERT, ");
										alertbuffer
												.append(AlertConstants.QUERY_NOTIFY_EVENT_TYPE);
										alertbuffer
												.append(AlertConstants.QUERY_ALERTCONF_NOTIFY_ALERT_CONF2USERS_SEQID);
										alertbuffer
												.append(AlertConstants.QUERY_CUST_OBJID);
										alertbuffer
												.append("AND CUSTALERT.ACTIVE_FLAG is NULL ");
										alertbuffer
												.append("AND ALERTCONF.NOTIFY_ALERT_CONF2RX_ALERT = RXALERT.OBJID ");
										alertbuffer
												.append(AlertConstants.QUERY_CUSTALERT_CONF_ID2NOTIFY_ALERT_CONF);
										alertbuffer
												.append(AlertConstants.QUERY_CUSTALERT_CUST_ID2BUS_ORG);
										alertbuffer
												.append("AND RXALERT.SUBSCRIBED_ALERTS=:rxsubscribedAlert ");
										if (null != ppalertSubscriptionDetails
												.getRxSubscribedAlerts()
												&& ppalertSubscriptionDetails
														.getRxSubscribedAlerts()
														.equals(AlertConstants.TITLES)) {
											alertbuffer
													.append(AlertConstants.QUERY_RXALERT_VALUE2SD_RECOM);
										} else {
											alertbuffer
													.append(AlertConstants.QUERY_RXALERT_VALUE2RMD_LOOKUP);
										}

										if (null != alertbuffer) {
											alertQuery = objHibernateSession
													.createSQLQuery(alertbuffer
															.toString());
										}
									} else if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.CONFIG_ALERT_SERVICE)) {
										alertQuery = objHibernateSession
												.createSQLQuery(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER
														+ " FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT, "
														+ " SA.TABLE_BUS_ORG CUST WHERE NOTIFY_EVENT_TYPE = 'Rule Title' "
														+ " AND ALERTCONF.NOTIFY_ALERT_CONF2USERS_SEQID =:userSeqId "
														+ AlertConstants.QUERY_CUST_OBJID
														+ " AND CUSTALERT.ACTIVE_FLAG is NULL "
														+ " AND CUSTALERT.CONF_ID2NOTIFY_ALERT_CONF = ALERTCONF.OBJID "
														+ " AND CUSTALERT.CUST_ID2BUS_ORG = CUST.OBJID"
														+ " AND ALERTCONF.NTFY_ALRT_CNF2RULEHIS_ORGNL_ID = :eventType ");
									} else {
										alertQuery = objHibernateSession
												.createSQLQuery(AlertConstants.QUERY_ALERT_TYPE_CUSTOMER
														+ " FROM GET_USR.GET_USR_NOTIFY_ALERT_CONF ALERTCONF, GET_USR.GET_USR_CUSTOMER_ALERT CUSTALERT, "
														+ " SA.TABLE_BUS_ORG CUST WHERE UPPER(NOTIFY_EVENT_TYPE) = :eventType "
														+ " AND ALERTCONF.NOTIFY_ALERT_CONF2USERS_SEQID =:userSeqId "
														+ AlertConstants.QUERY_CUST_OBJID
														+ " AND CUSTALERT.ACTIVE_FLAG is NULL "
														+ " AND CUSTALERT.CONF_ID2NOTIFY_ALERT_CONF = ALERTCONF.OBJID "
														+ " AND CUSTALERT.CUST_ID2BUS_ORG = CUST.OBJID");
									}
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										if (null != ppalertSubscriptionDetails
												.getAlertSubscribed()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getAlertSubscribed())) {
											alertQuery
													.setParameter(
															AlertConstants.EVENT_TYPE,
															ppalertSubscriptionDetails
																	.getAlertSubscribed());
										}
									} else {
										if (null != alertSubscribed
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(alertSubscribed)) {
											alertQuery
													.setParameter(
															AlertConstants.EVENT_TYPE,
															alertSubscribed);
										}
									}

									if (null != ppalertSubscriptionDetails
											.getUserSeqId()
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(ppalertSubscriptionDetails
															.getUserSeqId())) {
										alertQuery.setParameter(
												RMDCommonConstants.USER_SEQ_ID,
												ppalertSubscriptionDetails
														.getUserSeqId());
									}
									if (null != customerObjId
											&& !RMDCommonConstants.EMPTY_STRING
													.equals(customerObjId)) {
										alertQuery
												.setParameter(
														AlertConstants.CUSTOMER_OBJ_ID,
														customerObjId);
									}
									if (null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)) {
										if (null != ppalertSubscriptionDetails
												.getRxSubscribedAlerts()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getRxSubscribedAlerts())) {
											alertQuery
													.setParameter(
															AlertConstants.RXSUBSCRIBED_ALERT,
															ppalertSubscriptionDetails
																	.getRxSubscribedAlerts());
										}
										if (null != alertSubscribed
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(alertSubscribed)) {
											alertQuery
													.setParameter(
															AlertConstants.RX_ALERT_VALUE,
															alertSubscribed);
										}
									}
									alertList = (ArrayList) alertQuery.list();
								}
								if (alertList != null && !alertList.isEmpty()) {
									hasCustomerAlertEntry = true;
								}
								if(null != ppalertSubscriptionDetails
										.getService()
										&& ppalertSubscriptionDetails
												.getService()
												.equals(AlertConstants.EOA_SERVICE)){
									for(String objId : alertConfObjId){
										if (!hasCustomerAlertEntry && null != objHibernateSession) {
											insertQueryStr = new StringBuilder();
											insertQueryStr
												.append("insert into GET_USR.GET_USR_CUSTOMER_ALERT(OBJID, CUST_ID2BUS_ORG, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values  ");
											insertQueryStr
												.append(" (GET_USR.GET_USR_CUSTOMER_ALERT_SEQ.nextval,:customerObjId,:activeFlag,:emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType, ");
												insertQueryStr
														.append(AlertConstants.OBJID);
											Query customerQuery = objHibernateSession
													.createSQLQuery(insertQueryStr
															.toString());

											if (null != customerObjId
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(customerObjId)) {
												customerQuery
														.setParameter(
																AlertConstants.CUSTOMER_OBJ_ID,
																customerObjId);
											}
											customerQuery.setParameter(
													RMDCommonConstants.ACTIVEFLAG,
													null);
											customerQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															null);
											customerQuery.setParameter(
													AlertConstants.EMAIL_FLAG,
													null);
											customerQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															null);
											if (null != ppalertSubscriptionDetails
													.getUserid()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getUserid())) {
												customerQuery.setParameter(
														RMDCommonConstants.USERID,
														ppalertSubscriptionDetails
																.getUserid());
											}
											if (null != ppalertSubscriptionDetails
													.getNotificationType()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getNotificationType())) {
												customerQuery
														.setParameter(
																AlertConstants.NOTIFICATION_TYPE,
																ppalertSubscriptionDetails
																		.getNotificationType());
											}
											if (null != objId
													&& !RMDCommonConstants.EMPTY_STRING
													.equals(objId)) {
													customerQuery
															.setParameter(
																	AlertConstants.OBJ_ID,
																	objId);
											}
											customerQuery.executeUpdate();
										}
									}
								}
								else{
								if (!hasCustomerAlertEntry && null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
											.append("insert into GET_USR.GET_USR_CUSTOMER_ALERT(OBJID, CUST_ID2BUS_ORG, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values  ");
										insertQueryStr
											.append(" (GET_USR.GET_USR_CUSTOMER_ALERT_SEQ.nextval,:customerObjId,:activeFlag,:emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType, ");
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query customerQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != customerObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(customerObjId)) {
											customerQuery
													.setParameter(
															AlertConstants.CUSTOMER_OBJ_ID,
															customerObjId);
										}
										customerQuery.setParameter(
												RMDCommonConstants.ACTIVEFLAG,
												null);
										customerQuery
												.setParameter(
														AlertConstants.EMAIL_FORMAT,
														null);
										customerQuery.setParameter(
												AlertConstants.EMAIL_FLAG,
												null);
										customerQuery
												.setParameter(
														AlertConstants.SUBSCRIPTION_MODEL,
														null);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											customerQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											customerQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												customerQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
										}
										customerQuery.executeUpdate();
									}
							}
							}
							if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_REGION)) {
								Object rsEmailFlag = null;
								String rxFlag = null;
								StringBuilder lookupbuffer = new StringBuilder();
								lookupbuffer
										.append("SELECT look_value FROM GETS_RMD.GETS_RMD_LOOKUP WHERE list_name='RX_SUBSCRIPTION_DELIVERY_EMAIL_FLAG'");
								Query lookupQuery = objHibernateSession
										.createSQLQuery(lookupbuffer.toString());

								rsEmailFlag = lookupQuery.uniqueResult();
								if (RMDCommonUtility
										.convertObjectToString(rsEmailFlag) != null
										&& !RMDCommonUtility
												.convertObjectToString(
														rsEmailFlag).trim()
												.equals(RMDCommonConstants.EMPTY_STRING)) {
									rxFlag = RMDCommonUtility
											.convertObjectToString(rsEmailFlag);
								}

								// sub division based alert

								List<String> srcSubDivisionList = null;
								if (null != ppalertSubscriptionDetails
										.getSubDivision()
										&& !ppalertSubscriptionDetails
												.getSubDivision()
												.equals(RMDServiceConstants.EMPTY_TEXT)
										&& null != rxFlag
										&& rxFlag
												.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
									String[] subDivisionArray = ppalertSubscriptionDetails
											.getSubDivision().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcSubDivisionList = Arrays
											.asList(subDivisionArray);
								} else if (null != ppalertSubscriptionDetails
										.getRegion()
										&& !ppalertSubscriptionDetails
												.getRegion()
												.equals(RMDServiceConstants.EMPTY_TEXT)) {
									String[] subDivisionArray = ppalertSubscriptionDetails
											.getRegion().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcSubDivisionList = Arrays
											.asList(subDivisionArray);
								}

								for (String subRegion : srcSubDivisionList) {
									if(null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)){
										for(String objId : alertConfObjId){
											if (null != objHibernateSession) {
												insertQueryStr = new StringBuilder();
												insertQueryStr
													.append("insert into GET_USR.GET_USR_REGION_ALERT(OBJID, REGION_ID2RMD_REGION, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, NOTIFICATION_TYPE, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, ");
												if (null != ppalertSubscriptionDetails
														.getSubDivision()
														&& !ppalertSubscriptionDetails
																.getSubDivision()
																.equals(RMDServiceConstants.EMPTY_TEXT)
														&& null != rxFlag
														&& rxFlag
																.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
													insertQueryStr
															.append("REGION_ID2PP_SUB_REGION, ");
												}
												insertQueryStr
														.append("CONF_ID2NOTIFY_ALERT_CONF ) values ");
												insertQueryStr
														.append(" (GET_USR.GET_USR_REGION_ALERT_SEQ.nextval,:regionObjId,:activeFlag, ");
												insertQueryStr
													.append(" :emailFormat,:emailFlag,:notificationType,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, ");
												if (null != ppalertSubscriptionDetails
														.getSubDivision()
														&& !ppalertSubscriptionDetails
																.getSubDivision()
																.equals(RMDServiceConstants.EMPTY_TEXT)
														&& null != rxFlag
														&& rxFlag
																.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
													insertQueryStr
															.append(":subRegionObjId, ");
												}
													insertQueryStr
															.append(AlertConstants.OBJID);

												Query regionQuery = objHibernateSession
														.createSQLQuery(insertQueryStr
																.toString());
												if (null != rxFlag
														&& rxFlag
																.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
													if (null != ppalertSubscriptionDetails
															.getRegion()
															&& !RMDCommonConstants.EMPTY_STRING
																	.equals(ppalertSubscriptionDetails
																			.getRegion())) {
														regionQuery
																.setParameter(
																		AlertConstants.REGION_OBJID,
																		ppalertSubscriptionDetails
																				.getRegion());
													}
												} else {
													if (null != subRegion
															&& !RMDCommonConstants.EMPTY_STRING
																	.equals(subRegion)) {
														regionQuery
																.setParameter(
																		AlertConstants.REGION_OBJID,
																		subRegion);
													}
												}
												regionQuery
														.setParameter(
																RMDCommonConstants.ACTIVEFLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getEmailFormat()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getEmailFormat())) {
													regionQuery
															.setParameter(
																	AlertConstants.EMAIL_FORMAT,
																	ppalertSubscriptionDetails
																			.getEmailFormat());
												}
												regionQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getNotificationType()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getNotificationType())) {
													regionQuery
															.setParameter(
																	AlertConstants.NOTIFICATION_TYPE,
																	ppalertSubscriptionDetails
																			.getNotificationType());
												}
												if (null != ppalertSubscriptionDetails
														.getSubscriptionModel()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getSubscriptionModel())) {
													regionQuery
															.setParameter(
																	AlertConstants.SUBSCRIPTION_MODEL,
																	ppalertSubscriptionDetails
																			.getSubscriptionModel());
												}
												regionQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getUserid()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getUserid())) {
													regionQuery.setParameter(
															RMDCommonConstants.USERID,
															ppalertSubscriptionDetails
																	.getUserid());
												}
												if (null != ppalertSubscriptionDetails
														.getSubDivision()
														&& !ppalertSubscriptionDetails
																.getSubDivision()
																.equals(RMDServiceConstants.EMPTY_TEXT)
														&& null != rxFlag
														&& rxFlag
																.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
													if (null != subRegion
															&& !RMDCommonConstants.EMPTY_STRING
																	.equals(subRegion)) {
														regionQuery
																.setParameter(
																		AlertConstants.SUBREGION_OBJID,
																		subRegion);
													}
												}
												if (null != objId
														&& !RMDCommonConstants.EMPTY_STRING
														.equals(objId)) {
														regionQuery
																.setParameter(
																		AlertConstants.OBJ_ID,
																		objId);
													}
												regionQuery.executeUpdate();
											}
										}
									}else{
									if (null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
											.append("insert into GET_USR.GET_USR_REGION_ALERT(OBJID, REGION_ID2RMD_REGION, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, NOTIFICATION_TYPE, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, ");
										if (null != ppalertSubscriptionDetails
												.getSubDivision()
												&& !ppalertSubscriptionDetails
														.getSubDivision()
														.equals(RMDServiceConstants.EMPTY_TEXT)
												&& null != rxFlag
												&& rxFlag
														.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
											insertQueryStr
													.append("REGION_ID2PP_SUB_REGION, ");
										}
										insertQueryStr
												.append("CONF_ID2NOTIFY_ALERT_CONF ) values ");
										insertQueryStr
												.append(" (GET_USR.GET_USR_REGION_ALERT_SEQ.nextval,:regionObjId,:activeFlag, ");
										insertQueryStr
											.append(" :emailFormat,:emailFlag,:notificationType,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, ");
										if (null != ppalertSubscriptionDetails
												.getSubDivision()
												&& !ppalertSubscriptionDetails
														.getSubDivision()
														.equals(RMDServiceConstants.EMPTY_TEXT)
												&& null != rxFlag
												&& rxFlag
														.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
											insertQueryStr
													.append(":subRegionObjId, ");
										}

										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query regionQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());
										if (null != rxFlag
												&& rxFlag
														.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
											if (null != ppalertSubscriptionDetails
													.getRegion()
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(ppalertSubscriptionDetails
																	.getRegion())) {
												regionQuery
														.setParameter(
																AlertConstants.REGION_OBJID,
																ppalertSubscriptionDetails
																		.getRegion());
											}
										} else {
											if (null != subRegion
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(subRegion)) {
												regionQuery
														.setParameter(
																AlertConstants.REGION_OBJID,
																subRegion);
											}
										}
										regionQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											regionQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										regionQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											regionQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											regionQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										regionQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											regionQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
										if (null != ppalertSubscriptionDetails
												.getSubDivision()
												&& !ppalertSubscriptionDetails
														.getSubDivision()
														.equals(RMDServiceConstants.EMPTY_TEXT)
												&& null != rxFlag
												&& rxFlag
														.equalsIgnoreCase(RMDCommonConstants.Y_LETTER)) {
											if (null != subRegion
													&& !RMDCommonConstants.EMPTY_STRING
															.equals(subRegion)) {
												regionQuery
														.setParameter(
																AlertConstants.SUBREGION_OBJID,
																subRegion);
											}
										}
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												regionQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
											}
										regionQuery.executeUpdate();
									}
								}
								}
							} else if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_FLEET)) {

								List<String> srcFleetList = null;
								if (ppalertSubscriptionDetails.getFleet() != null
										&& !ppalertSubscriptionDetails
												.getFleet()
												.equals(RMDServiceConstants.EMPTY_TEXT)) {
									String[] fleetArray = ppalertSubscriptionDetails
											.getFleet().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcFleetList = Arrays.asList(fleetArray);
								}

								for (String fleet : srcFleetList) {
									if(null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)){
										for(String objId : alertConfObjId){
											if (null != objHibernateSession) {
												insertQueryStr = new StringBuilder();
												insertQueryStr
														.append("insert into GET_USR.GET_USR_FLEET_ALERT(OBJID, FLEET_NBR2RMD_FLEET, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) ");
												insertQueryStr
														.append("values (GET_USR.GET_USR_FLEET_ALERT_SEQ.nextval,:fleetObjId,:activeFlag, ");
												insertQueryStr
														.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT_FOR_FLEET);
												insertQueryStr
														.append(":notificationType,");
													insertQueryStr
															.append(AlertConstants.OBJID);
												Query fleetQuery = objHibernateSession
														.createSQLQuery(insertQueryStr
																.toString());

												if (null != fleet
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(fleet)) {
													fleetQuery
															.setParameter(
																	AlertConstants.FLEET_OBJID,
																	fleet);
												}
												fleetQuery
														.setParameter(
																RMDCommonConstants.ACTIVEFLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getEmailFormat()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getEmailFormat())) {
													fleetQuery
															.setParameter(
																	AlertConstants.EMAIL_FORMAT,
																	ppalertSubscriptionDetails
																			.getEmailFormat());
												}
												if (null != ppalertSubscriptionDetails
														.getNotificationType()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getNotificationType())) {
													fleetQuery
															.setParameter(
																	AlertConstants.NOTIFICATION_TYPE,
																	ppalertSubscriptionDetails
																			.getNotificationType());
												}
												fleetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getSubscriptionModel()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getSubscriptionModel())) {
													fleetQuery
															.setParameter(
																	AlertConstants.SUBSCRIPTION_MODEL,
																	ppalertSubscriptionDetails
																			.getSubscriptionModel());
												}
												fleetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getUserid()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getUserid())) {
													fleetQuery.setParameter(
															RMDCommonConstants.USERID,
															ppalertSubscriptionDetails
																	.getUserid());
												}
												if (null != objId
														&& !RMDCommonConstants.EMPTY_STRING
														.equals(objId)) {
														fleetQuery
																.setParameter(
																		AlertConstants.OBJ_ID,
																		objId);
												}
												fleetQuery.executeUpdate();
											}
										}
									}else{
									if (null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
												.append("insert into GET_USR.GET_USR_FLEET_ALERT(OBJID, FLEET_NBR2RMD_FLEET, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) ");
										insertQueryStr
												.append("values (GET_USR.GET_USR_FLEET_ALERT_SEQ.nextval,:fleetObjId,:activeFlag, ");
										insertQueryStr
												.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT_FOR_FLEET);
										insertQueryStr
												.append(":notificationType,");
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query fleetQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != fleet
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(fleet)) {
											fleetQuery
													.setParameter(
															AlertConstants.FLEET_OBJID,
															fleet);
										}
										fleetQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											fleetQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											fleetQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										fleetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											fleetQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										fleetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											fleetQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												fleetQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
										}
										fleetQuery.executeUpdate();
									}
								}
								}
							} else if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_ASSET)) {
								List<String> srcassetList = null;
								if (ppalertSubscriptionDetails.getAsset() != null
										&& !ppalertSubscriptionDetails
												.getAsset()
												.equals(RMDServiceConstants.EMPTY_TEXT)) {
									String[] assetArray = ppalertSubscriptionDetails
											.getAsset().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcassetList = Arrays.asList(assetArray);
								}

								for (String vehicleObjId : srcassetList) {
									if(null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)){
										for(String objId : alertConfObjId){
											if (null != objHibernateSession) {
												insertQueryStr = new StringBuilder();
												insertQueryStr
														.append("insert into GET_USR.GET_USR_VEHICLE_ALERT(OBJID, VEHICLE_OBJID2RMD_VEHICLE, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, NOTIFICATION_TYPE, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, CONF_ID2NOTIFY_ALERT_CONF) values ");
												insertQueryStr
														.append(" (GET_USR.GET_USR_VEHICLE_ALERT_SEQ.nextval,:vehicleObjid,:activeFlag, ");
												insertQueryStr
														.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT);
													insertQueryStr
															.append(AlertConstants.OBJID);
												Query assetQuery = objHibernateSession
														.createSQLQuery(insertQueryStr
																.toString());

												if (null != vehicleObjId
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(vehicleObjId)) {
													assetQuery
															.setParameter(
																	RMDCommonConstants.VEHICLE_OBJID,
																	vehicleObjId);
												}
												assetQuery
														.setParameter(
																RMDCommonConstants.ACTIVEFLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getEmailFormat()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getEmailFormat())) {
													assetQuery
															.setParameter(
																	AlertConstants.EMAIL_FORMAT,
																	ppalertSubscriptionDetails
																			.getEmailFormat());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getNotificationType()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getNotificationType())) {
													assetQuery
															.setParameter(
																	AlertConstants.NOTIFICATION_TYPE,
																	ppalertSubscriptionDetails
																			.getNotificationType());
												}
												if (null != ppalertSubscriptionDetails
														.getSubscriptionModel()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getSubscriptionModel())) {
													assetQuery
															.setParameter(
																	AlertConstants.SUBSCRIPTION_MODEL,
																	ppalertSubscriptionDetails
																			.getSubscriptionModel());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getUserid()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getUserid())) {
													assetQuery.setParameter(
															RMDCommonConstants.USERID,
															ppalertSubscriptionDetails
																	.getUserid());
												}
												if (null != objId
														&& !RMDCommonConstants.EMPTY_STRING
														.equals(objId)) {
														assetQuery
																.setParameter(
																		AlertConstants.OBJ_ID,
																		objId);
												}
												assetQuery.executeUpdate();
											}
										}
									}else{
									if (null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
												.append("insert into GET_USR.GET_USR_VEHICLE_ALERT(OBJID, VEHICLE_OBJID2RMD_VEHICLE, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, NOTIFICATION_TYPE, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, CONF_ID2NOTIFY_ALERT_CONF) values ");
										insertQueryStr
												.append(" (GET_USR.GET_USR_VEHICLE_ALERT_SEQ.nextval,:vehicleObjid,:activeFlag, ");
										insertQueryStr
												.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT);
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query assetQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != vehicleObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(vehicleObjId)) {
											assetQuery
													.setParameter(
															RMDCommonConstants.VEHICLE_OBJID,
															vehicleObjId);
										}
										assetQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											assetQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											assetQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											assetQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											assetQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												assetQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
										}
										assetQuery.executeUpdate();
									}
								}
								}
							} else if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_SHOP)) {
								// shop based alert
								List<String> srcshopList = null;
								if (ppalertSubscriptionDetails.getShop() != null
										&& !ppalertSubscriptionDetails
												.getShop()
												.equals(RMDServiceConstants.EMPTY_TEXT)) {
									String[] shopArray = ppalertSubscriptionDetails
											.getShop().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcshopList = Arrays.asList(shopArray);
								}

								for (String shopObjId : srcshopList) {
									if(null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)){
										for(String objId : alertConfObjId){
											if (null != objHibernateSession) {
												insertQueryStr = new StringBuilder();
												insertQueryStr
														.append("insert into GET_USR.GET_USR_SHOP_ALERT(OBJID, SHOP_OBJID2PROXIMITY_DEF, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values ");
												insertQueryStr
														.append(" (GET_USR.GET_USR_SHOP_ALERT_SEQ.nextval,:shopObjid,:activeFlag, ");
												insertQueryStr
														.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT_FOR_FLEET);
												insertQueryStr
														.append(":notificationType,");
													insertQueryStr
															.append(AlertConstants.OBJID);
												Query assetQuery = objHibernateSession
														.createSQLQuery(insertQueryStr
																.toString());

												if (null != shopObjId
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(shopObjId)) {
													assetQuery
															.setParameter(
																	RMDCommonConstants.SHOP_OBJID,
																	shopObjId);
												}
												assetQuery
														.setParameter(
																RMDCommonConstants.ACTIVEFLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getEmailFormat()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getEmailFormat())) {
													assetQuery
															.setParameter(
																	AlertConstants.EMAIL_FORMAT,
																	ppalertSubscriptionDetails
																			.getEmailFormat());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getNotificationType()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getNotificationType())) {
													assetQuery
															.setParameter(
																	AlertConstants.NOTIFICATION_TYPE,
																	ppalertSubscriptionDetails
																			.getNotificationType());
												}
												if (null != ppalertSubscriptionDetails
														.getSubscriptionModel()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getSubscriptionModel())) {
													assetQuery
															.setParameter(
																	AlertConstants.SUBSCRIPTION_MODEL,
																	ppalertSubscriptionDetails
																			.getSubscriptionModel());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getUserid()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getUserid())) {
													assetQuery.setParameter(
															RMDCommonConstants.USERID,
															ppalertSubscriptionDetails
																	.getUserid());
												}
													if (!RMDCommonUtility.isNullOrEmpty(objId)) {
														assetQuery
																.setParameter(
																		AlertConstants.OBJ_ID,
																		objId);
													}									
												assetQuery.executeUpdate();
											}
										}
									} else {
									if (null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
												.append("insert into GET_USR.GET_USR_SHOP_ALERT(OBJID, SHOP_OBJID2PROXIMITY_DEF, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, CONF_ID2NOTIFY_ALERT_CONF) values ");
										insertQueryStr
												.append(" (GET_USR.GET_USR_SHOP_ALERT_SEQ.nextval,:shopObjid,:activeFlag, ");
										insertQueryStr
												.append(AlertConstants.QUERY_GET_USR_GET_USR_VEHICLE_ALERT_FOR_FLEET);
										insertQueryStr
												.append(":notificationType,");
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query assetQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != shopObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(shopObjId)) {
											assetQuery
													.setParameter(
															RMDCommonConstants.SHOP_OBJID,
															shopObjId);
										}
										assetQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											assetQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											assetQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											assetQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											assetQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
											if (hasDuplicateAlertConf && !RMDCommonUtility.isNullOrEmpty(alertConfSeqId)) {
												assetQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
											}									
										assetQuery.executeUpdate();
									}
								}
								}
							} else if (alertType
									.equalsIgnoreCase(AlertConstants.ALERT_TYPE_MODEL)) {
								// Model based alert
								List<String> srcModelList = null;
								if (null != ppalertSubscriptionDetails
										.getModel()
										&& !ppalertSubscriptionDetails
												.getModel().isEmpty()) {
									String[] modelArray = ppalertSubscriptionDetails
											.getModel().split(
													RMDCommonConstants.COMMMA_SEPARATOR);
									srcModelList = Arrays.asList(modelArray);
								}
								for (String modelObjId : srcModelList) {
									if(null != ppalertSubscriptionDetails
											.getService()
											&& ppalertSubscriptionDetails
													.getService()
													.equals(AlertConstants.EOA_SERVICE)){
										for(String objId : alertConfObjId){
											if (null != objHibernateSession) {
												insertQueryStr = new StringBuilder();
												insertQueryStr
														.append("insert into GET_USR.GET_USR_MODEL_ALERT (OBJID, MODEL_OBJID2RMD_MODEL, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, MODEL_OBJID2BUS_ORG, CONF_ID2NOTIFY_ALERT_CONF) values ");
												insertQueryStr
														.append(" (GET_USR.GET_USR_MODEL_ALERT_SEQ.nextval,:modelObjId,:activeFlag, ");
												insertQueryStr
														.append(" :emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType, :customerObjId,");
													insertQueryStr
															.append(AlertConstants.OBJID);
												Query assetQuery = objHibernateSession
														.createSQLQuery(insertQueryStr
																.toString());

												if (null != modelObjId
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(modelObjId)) {
													assetQuery
															.setParameter(
																	RMDCommonConstants.MODEL_OBJID,
																	modelObjId);
												}
												assetQuery
														.setParameter(
																RMDCommonConstants.ACTIVEFLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getEmailFormat()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getEmailFormat())) {
													assetQuery
															.setParameter(
																	AlertConstants.EMAIL_FORMAT,
																	ppalertSubscriptionDetails
																			.getEmailFormat());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getNotificationType()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getNotificationType())) {
													assetQuery
															.setParameter(
																	AlertConstants.NOTIFICATION_TYPE,
																	ppalertSubscriptionDetails
																			.getNotificationType());
												}
												if (null != ppalertSubscriptionDetails
														.getSubscriptionModel()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getSubscriptionModel())) {
													assetQuery
															.setParameter(
																	AlertConstants.SUBSCRIPTION_MODEL,
																	ppalertSubscriptionDetails
																			.getSubscriptionModel());
												}
												assetQuery
														.setParameter(
																AlertConstants.EMAIL_FLAG,
																RMDCommonConstants.Y_LETTER_UPPER);
												if (null != ppalertSubscriptionDetails
														.getUserid()
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(ppalertSubscriptionDetails
																		.getUserid())) {
													assetQuery.setParameter(
															RMDCommonConstants.USERID,
															ppalertSubscriptionDetails
																	.getUserid());
												}
												if (null != customerObjId
														&& !RMDCommonConstants.EMPTY_STRING
																.equals(customerObjId)) {
													assetQuery
															.setParameter(
																	AlertConstants.CUSTOMER_OBJ_ID,
																	customerObjId);
												}
												if (null != objId
														&& !RMDCommonConstants.EMPTY_STRING
														.equals(objId)) {
														assetQuery
																.setParameter(
																		AlertConstants.OBJ_ID,
																		objId);
													}
												assetQuery.executeUpdate();
											}
										}
									} else {
									if (null != objHibernateSession) {
										insertQueryStr = new StringBuilder();
										insertQueryStr
												.append("insert into GET_USR.GET_USR_MODEL_ALERT (OBJID, MODEL_OBJID2RMD_MODEL, ACTIVE_FLAG, EMAIL_ATTACHMENT_FORMAT_CD, EMAIL_FLAG, SUBSCRIPTION_FREQUENCY_CD, CREATED_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY, NOTIFICATION_TYPE, MODEL_OBJID2BUS_ORG, CONF_ID2NOTIFY_ALERT_CONF) values ");
										insertQueryStr
												.append(" (GET_USR.GET_USR_MODEL_ALERT_SEQ.nextval,:modelObjId,:activeFlag, ");
										insertQueryStr
												.append(" :emailFormat,:emailFlag,:subscriptionModel, SYSDATE, :userId, SYSDATE, :userId, :notificationType, :customerObjId,");
										if (!hasDuplicateAlertConf) {
											insertQueryStr
													.append(AlertConstants.GET_USR_NOTIFY_ALERT_CONF_SEQ);
										} else {
											insertQueryStr
													.append(AlertConstants.ALERT_CONF_SEQID);
										}
										Query assetQuery = objHibernateSession
												.createSQLQuery(insertQueryStr
														.toString());

										if (null != modelObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(modelObjId)) {
											assetQuery
													.setParameter(
															RMDCommonConstants.MODEL_OBJID,
															modelObjId);
										}
										assetQuery
												.setParameter(
														RMDCommonConstants.ACTIVEFLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getEmailFormat()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getEmailFormat())) {
											assetQuery
													.setParameter(
															AlertConstants.EMAIL_FORMAT,
															ppalertSubscriptionDetails
																	.getEmailFormat());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getNotificationType()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getNotificationType())) {
											assetQuery
													.setParameter(
															AlertConstants.NOTIFICATION_TYPE,
															ppalertSubscriptionDetails
																	.getNotificationType());
										}
										if (null != ppalertSubscriptionDetails
												.getSubscriptionModel()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getSubscriptionModel())) {
											assetQuery
													.setParameter(
															AlertConstants.SUBSCRIPTION_MODEL,
															ppalertSubscriptionDetails
																	.getSubscriptionModel());
										}
										assetQuery
												.setParameter(
														AlertConstants.EMAIL_FLAG,
														RMDCommonConstants.Y_LETTER_UPPER);
										if (null != ppalertSubscriptionDetails
												.getUserid()
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(ppalertSubscriptionDetails
																.getUserid())) {
											assetQuery.setParameter(
													RMDCommonConstants.USERID,
													ppalertSubscriptionDetails
															.getUserid());
										}
										if (null != customerObjId
												&& !RMDCommonConstants.EMPTY_STRING
														.equals(customerObjId)) {
											assetQuery
													.setParameter(
															AlertConstants.CUSTOMER_OBJ_ID,
															customerObjId);
										}
										if (hasDuplicateAlertConf && (null != alertConfSeqId
												&& !RMDCommonConstants.EMPTY_STRING
												.equals(alertConfSeqId))) {
												assetQuery
														.setParameter(
																AlertConstants.ALERT_CONF_SEQ_ID,
																alertConfSeqId);
											}
										assetQuery.executeUpdate();
									}
								}
								}
							}
						}
					
						}
					duplicateUser.put(ppalertSubscriptionDetails.getUserid(),
							ppalertSubscriptionDetails.getUserSeqId());
				}
					}
			
			tx.commit();
			returnStr = RMDServiceConstants.SUCCESS;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
			duplicateUser = null;
			userDetailsArr = null;
			userDetailsArrStr = null;
		}
		return returnStr;
	}

	@Override
	public List<AlertSubscriptionDetailsVO> getAlertSubscriptionData(
			AlertSubscriptionDetailsVO alertVo) throws RMDDAOException {
		Session hibernateSession = null;
		final StringBuilder alertQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> alertSubList = null;
		List<AlertSubscriptionDetailsVO> ppAlertSubscriptionDetailsVO = new ArrayList<AlertSubscriptionDetailsVO>();
		AlertSubscriptionDetailsVO returnAlertSubscriptionVo = null;
		String urgency = null;
		String urgencyValue = null;
		String userType = alertVo.getUserType();
		String[] urgencyInfo;
		try {
			alertQry.append("SELECT USER_ID, NOTIFY_OBJID, CUSTOMER_OBJID, REGION_OBJID, FLEET_OBJID, ");
			alertQry.append("VEHICLE_OBJID,  LAST_UPDATED_DATE, SERVICE, ORG_ID, NOTIFY_EVENT_TYPE, REGION, FLEET_NUMBER, ");
			alertQry.append("SERIAL_NO, EMAIL_ATTACHMENT_FORMAT_CD, SUBSCRIPTION_FREQUENCY_CD, ");
			alertQry.append("ACTIVE_FLAG, SHOP_OBJID, PROXIMITY_DESC ,SUBSCRIBED_ALERTS ,RXALERT_VALUE ,SUB_REGION_OBJID ,SUB_REGION,RXALERT_OBJID, ");
			alertQry.append("MODEL_OBJID,MODEL_NAME ");

			if (alertVo.isConfigAlertPrivilege()) {
				alertQry.append(",RULE_TITLE,RULE_STATUS,ORIGINAL_ID,RULE_FAMILY ");
			}
			alertQry.append(",NOTIFICATION_TYPE");
			if (alertVo.isMultiSubscriptionPrivilege()) {
				alertQry.append(",FIRST_NAME,LAST_NAME,USER_EMAIL");
			}
			if (alertVo.isMultiSubscriptionPrivilege()) {
				alertQry.append(",USER_PHONE_NUMBER ");
			}
			alertQry.append(",RX_TYPE ");
			alertQry.append(" FROM GETS_RMD.GETS_RMD_SUBSCRIPTION_ALERT_V WHERE ");

			if (alertVo.isMultiSubscriptionPrivilege()) {

				if (null != userType
						&& userType.equals(RMDCommonConstants.CUSTOMERSTR)) {
					alertQry.append("USER_TYPE='Customer' ");
				} else {
					alertQry.append(" USER_TYPE IS NOT NULL ");
					alertQry.append("AND NOT USER_TYPE='Ex Employee' ");
				}
				if (!alertVo.isAllCustomer()) {
					alertQry.append("AND ORG_ID IN ( :customerId )");
				}

				alertQry.append("AND USER_STATUS='1' ");
				alertQry.append("AND USER_EMAIL IS NOT NULL ");
			} else {
				alertQry.append("USER_ID = :userId ");
			}

			if (!alertVo.getFlag().equalsIgnoreCase(
					RMDCommonConstants.ALL_SUBSCRIPTION)
					&& !alertVo.isModelPrivilege()) {
				alertQry.append("AND MODEL_OBJID IS NULL AND MODEL_NAME IS NULL ");
			}
			if (!alertVo.isConfigAlertPrivilege()) {
				alertQry.append("AND NOT SERVICE  ='Config Alerts' ");
			}
			alertQry.append("ORDER BY LAST_UPDATED_DATE DESC ");

			if (null != hibernateSession) {
				Query alertHqry = hibernateSession.createSQLQuery(alertQry
						.toString());
				if (alertVo.isMultiSubscriptionPrivilege()) {
					if (null != alertVo.getCustomerId()
							&& !RMDCommonConstants.EMPTY_STRING.equals(alertVo
									.getCustomerId())) {
						List<String> customerList = Arrays.asList(alertVo
								.getCustomerId().split(
										RMDCommonConstants.COMMMA_SEPARATOR));
						alertHqry.setParameterList(
								RMDCommonConstants.CUSTOMER_ID, customerList);
					}

				} else {
					if (null != alertVo.getUserid()
							&& !RMDCommonConstants.EMPTY_STRING.equals(alertVo
									.getUserid())) {
						alertHqry.setParameter(RMDCommonConstants.USERID,
								alertVo.getUserid());
					}
				}
				alertHqry.setFetchSize(2000);
				alertSubList = (ArrayList) alertHqry.list();

				int i=0;
				if (RMDCommonUtility.isCollectionNotEmpty(alertSubList)) {
					for (Object[] obj : alertSubList) {
						i++;
						returnAlertSubscriptionVo = new AlertSubscriptionDetailsVO();
						// NOTIFY_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[1]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[1]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setAlertConfSeqId(RMDCommonUtility
											.convertObjectToString(obj[1]));
						}
						if (RMDCommonUtility.convertObjectToString(obj[2]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[2]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setCustomerAlertObjId(RMDCommonUtility
											.convertObjectToString(obj[2]));
						}
						// REGION_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[3]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[3]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setRegionid(RMDCommonUtility
											.convertObjectToString(obj[3]));
						}
						// FLEET_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[4]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[4]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setFleetid(RMDCommonUtility
											.convertObjectToString(obj[4]));
						}
						// VEHICLE_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[5]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[5]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setVehicleObjId(RMDCommonUtility
											.convertObjectToString(obj[5]));
						}
						// SERVICE
						if (RMDCommonUtility.convertObjectToString(obj[7]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[7]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setService(RMDCommonUtility
											.convertObjectToString(obj[7]));
						}
						// ORG_ID
						if (RMDCommonUtility.convertObjectToString(obj[8]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[8]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setCustomerId(RMDCommonUtility
											.convertObjectToString(obj[8]));
						}
						// NOTIFY_EVENT_TYPE
						if (RMDCommonUtility.convertObjectToString(obj[9]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[9]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							if (AlertConstants.ATS_SERVICE
									.equals(returnAlertSubscriptionVo
											.getService())) {
								String alertSubscribed = RMDCommonUtility.alphaFirstUppercase(obj[9]);
								returnAlertSubscriptionVo
								.setAlertSubscribed(alertSubscribed);
							} else {
								returnAlertSubscriptionVo
								.setAlertSubscribed(RMDCommonUtility
										.convertObjectToString(obj[9]));
							}
						}
						// REGION
						if (RMDCommonUtility.convertObjectToString(obj[10]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[10]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setRegion(RMDCommonUtility
											.convertObjectToString(obj[10]));
						}
						// FLEET_NUMBER
						if (RMDCommonUtility.convertObjectToString(obj[11]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[11]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo.setFleet(RMDCommonUtility
									.convertObjectToString(obj[11]));
						}
						// SERIAL_NO
						if (RMDCommonUtility.convertObjectToString(obj[12]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[12]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo.setAsset(RMDCommonUtility
									.convertObjectToString(obj[12]));
						}
						// EMAIL_ATTACHMENT_FORMAT_CD
						if (RMDCommonUtility.convertObjectToString(obj[13]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[13]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setEmailFormat(RMDCommonUtility
											.convertObjectToString(obj[13]));
						}
						// SUBSCRIPTION_FREQUENCY_CD
						if (RMDCommonUtility.convertObjectToString(obj[14]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[14]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setSubscriptionModel(RMDCommonUtility
											.convertObjectToString(obj[14]));
						}
						// ACTIVE_FLAG
						if (RMDCommonUtility.convertObjectToString(obj[15]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[15]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							String activeFlag = null;
							if (RMDCommonUtility.convertObjectToString(obj[15])
									.equalsIgnoreCase(
											RMDCommonConstants.Y_LETTER_UPPER)) {
								activeFlag = AlertConstants.ACTIVE_FLAG;
							} else {
								activeFlag = AlertConstants.INACTIVE_FLAG;
							}
							returnAlertSubscriptionVo.setStatus(activeFlag);
						}
						// Shop ID
						if (RMDCommonUtility.convertObjectToString(obj[16]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[16]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setShopid(RMDCommonUtility
											.convertObjectToString(obj[16]));
						}
						// Shop Name
						if (RMDCommonUtility.convertObjectToString(obj[17]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[17]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo.setShop(RMDCommonUtility
									.convertObjectToString(obj[17]));
						}
						// SUBSCRIBED_ALERTS
						if (RMDCommonUtility.convertObjectToString(obj[18]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[18]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setRxSubscribedAlerts(RMDCommonUtility
											.convertObjectToString(obj[18]));
						}
						// RXALERT_VALUE
						if (RMDCommonUtility.convertObjectToString(obj[19]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[19]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							urgency = RMDCommonUtility
									.convertObjectToString(obj[19]);
							if (urgency.indexOf('~') != -1) {
								urgencyInfo = urgency
										.split(RMDCommonConstants.TILDA);
								urgencyValue = urgencyInfo[0] + ":"
										+ urgencyInfo[1];
								returnAlertSubscriptionVo
										.setValue(urgencyValue);

							} else {
								returnAlertSubscriptionVo.setValue(urgency);
							}

						}
						// SUB_REGION_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[20]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[20]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setSubDivisionid(RMDCommonUtility
											.convertObjectToString(obj[20]));
						}
						// SUB_REGION
						if (RMDCommonUtility.convertObjectToString(obj[21]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[21]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setSubDivision(RMDCommonUtility
											.convertObjectToString(obj[21]));
						}
						// RXALERT_OBJID
						if (RMDCommonUtility.convertObjectToString(obj[22]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[22]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							returnAlertSubscriptionVo
									.setRxAlertId(RMDCommonUtility
											.convertObjectToString(obj[22]));
						}
						// Model ID
						if (null != RMDCommonUtility
								.convertObjectToString(obj[23])
								&& !RMDCommonUtility
										.convertObjectToString(obj[23]).trim()
										.isEmpty()) {
							returnAlertSubscriptionVo
									.setModelId(RMDCommonUtility
											.convertObjectToString(obj[23]));
						}
						// Model Name
						if (null != RMDCommonUtility
								.convertObjectToString(obj[24])
								&& !RMDCommonUtility
										.convertObjectToString(obj[24]).trim()
										.isEmpty()) {
							returnAlertSubscriptionVo.setModel(RMDCommonUtility
									.convertObjectToString(obj[24]));
						}

						if (alertVo.isConfigAlertPrivilege()) {
							// RULE_TITLE
							if (null != RMDCommonUtility
									.convertObjectToString(obj[25])
									&& !RMDCommonUtility
											.convertObjectToString(obj[25])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setRuleTitle(RMDCommonUtility
												.convertObjectToString(obj[25]));
							}
							// ACTIVE
							if (null != RMDCommonUtility
									.convertObjectToString(obj[26])
									&& !RMDCommonUtility
											.convertObjectToString(obj[26])
											.trim().isEmpty()) {
								String activeFlag = null;
								if (RMDCommonUtility.convertObjectToString(
										obj[26]).equalsIgnoreCase(
										RMDCommonConstants.ONE_STRING)) {
									activeFlag = AlertConstants.ACTIVE_FLAG;
								} else {
									activeFlag = AlertConstants.INACTIVE_FLAG;
								}
								returnAlertSubscriptionVo.setActive(activeFlag);
							}
							// ORIGINAL_ID
							if (null != RMDCommonUtility
									.convertObjectToString(obj[27])
									&& !RMDCommonUtility
											.convertObjectToString(obj[27])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setOriginalId(RMDCommonUtility
												.convertObjectToString(obj[27]));
							}
							// RULE_MODEL
							if (null != RMDCommonUtility
									.convertObjectToString(obj[28])
									&& !RMDCommonUtility
											.convertObjectToString(obj[28])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setRuleModel(RMDCommonUtility
												.convertObjectToString(obj[28]));
							}
						}
						//NOTIFICATION TYPE
						if ((alertVo.isConfigAlertPrivilege()) && null != RMDCommonUtility
								.convertObjectToString(obj[29])
								&& !RMDCommonUtility
										.convertObjectToString(obj[29])
										.trim().isEmpty()) {
							returnAlertSubscriptionVo
									.setNotificationType(RMDCommonUtility
											.convertObjectToString(obj[29])
											.trim());
						}
						else
						{
							returnAlertSubscriptionVo
							.setNotificationType(RMDCommonUtility
									.convertObjectToString(obj[25])
									.trim());
						}
						if (alertVo.isMultiSubscriptionPrivilege()) {
							// FIRST_NAME
							if ((alertVo.isConfigAlertPrivilege()) && null != RMDCommonUtility
									.convertObjectToString(obj[30])
									&& !RMDCommonUtility
											.convertObjectToString(obj[30])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setFirstName(RMDCommonUtility
												.convertObjectToString(obj[30])
												.trim());
							}
							else
							{
								returnAlertSubscriptionVo
								.setFirstName(RMDCommonUtility
										.convertObjectToString(obj[26])
										.trim());
							}
							// LAST_NAME
							if ((alertVo.isConfigAlertPrivilege()) && null != RMDCommonUtility
									.convertObjectToString(obj[31])
									&& !RMDCommonUtility
											.convertObjectToString(obj[31])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setLastName(RMDCommonUtility
												.convertObjectToString(obj[31])
												.trim());
							}
							else
							{
								returnAlertSubscriptionVo
								.setLastName(RMDCommonUtility
										.convertObjectToString(obj[27])
										.trim());
							}
							// USER_EMAIL
							if ((alertVo.isConfigAlertPrivilege()) && null != RMDCommonUtility
									.convertObjectToString(obj[32])
									&& !RMDCommonUtility
											.convertObjectToString(obj[32])
											.trim().isEmpty()) {
								returnAlertSubscriptionVo
										.setUserEmail(RMDCommonUtility
												.convertObjectToString(obj[32])
												.trim());
							}
							else
							{
								returnAlertSubscriptionVo
								.setUserEmail(RMDCommonUtility
										.convertObjectToString(obj[28])
										.trim());
							}
						}
						if (alertVo.isMultiSubscriptionPrivilege()) {
							//USER PHONE NUMBER
							if (alertVo.isConfigAlertPrivilege())
							{
								if(null != RMDCommonUtility
										.convertObjectToString(obj[33])
										&& !RMDCommonUtility
												.convertObjectToString(obj[33])
												.trim().isEmpty())
									returnAlertSubscriptionVo
									.setPhoneNumber(RMDCommonUtility
											.convertObjectToString(obj[33])
											.trim());
								else
									returnAlertSubscriptionVo
									.setPhoneNumber(RMDCommonUtility
											.convertObjectToString(obj[33]));
									
							}
							else
							{
								if(obj[29]==null)
									returnAlertSubscriptionVo
									.setPhoneNumber(RMDCommonUtility
											.convertObjectToString(obj[29]));
								else
									returnAlertSubscriptionVo
									.setPhoneNumber(RMDCommonUtility
											.convertObjectToString(obj[29])
											.trim());
							}
						}
						if (alertVo.isMultiSubscriptionPrivilege() == true && alertVo.isConfigAlertPrivilege() == true) {
							
								if(null != RMDCommonUtility
										.convertObjectToString(obj[34])
										&& !RMDCommonUtility
												.convertObjectToString(obj[34])
												.trim().isEmpty())
									returnAlertSubscriptionVo
									.setRxType(RMDCommonUtility
											.convertObjectToString(obj[34])
											.trim());
								else
									returnAlertSubscriptionVo
									.setRxType(RMDCommonConstants.N_A);
						}	
						else if(alertVo.isConfigAlertPrivilege() == true || alertVo.isMultiSubscriptionPrivilege() == true)
							{
								if(obj[30]==null)
									returnAlertSubscriptionVo
									.setRxType(RMDCommonConstants.N_A);
								else
									returnAlertSubscriptionVo
									.setRxType(RMDCommonUtility
											.convertObjectToString(obj[30])
											.trim());
							}
						else
						{
							if(obj[26]==null)
								returnAlertSubscriptionVo
								.setRxType(RMDCommonConstants.N_A);
							else
								returnAlertSubscriptionVo
								.setRxType(RMDCommonUtility
										.convertObjectToString(obj[26])
										.trim());
						}
						ppAlertSubscriptionDetailsVO
								.add(returnAlertSubscriptionVo);
					}
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA);
			throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA);
			throw new RMDDAOException(errorCode, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
			alertSubList = null;
		}
		return ppAlertSubscriptionDetailsVO;
	}
	@Override
	public List<AlertAssetDetailsVO> getAssetForCustomer(AlertAssetDetailsVO assetVO)
			throws RMDDAOException {
		List<AlertAssetDetailsVO> assetList = new ArrayList<AlertAssetDetailsVO>();
		Session hibernateSession = null;
		final StringBuilder assetQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> arlAssetNo = null;
		AlertAssetDetailsVO assetResponseVO = null;
		try {

			assetQry.append("SELECT V.VEHICLE_NO, V.VEHICLE_OBJID, V.VEHICLE_HDR ");
			assetQry.append("FROM GETS_MCS.MCS_ASSET A  ,GETS_MCS.MCS_LOOKUP_VALUE ST, ");
			assetQry.append("GETS_MCS.MCS_APPLICATION SERVICE, GETS_MCS.MCS_ASSET_APPLICATION APP ,GETS_RMD_CUST_RNH_RN_V V ");
			assetQry.append("WHERE STATUS = ST.OBJID ");
			assetQry.append("AND APP.APPLICATION_OBJID = SERVICE.OBJID ");
			assetQry.append("AND APP.ASSET_OBJID = A.OBJID  AND V.VEHICLE2ASSET_OBJID = A.OBJID ");
			assetQry.append("AND APPLICATION_NAME = 'ATS' ");
			assetQry.append("AND V.ORG_ID = :customerId ");
			if (null != assetVO.getFleet()	&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO.getFleet())) 
            {
				assetQry.append("AND V.FLEET_NUMBER_V IN(:strFleet) ");
			}
			if (null != hibernateSession) {
				Query assetHqry = hibernateSession.createSQLQuery(assetQry
						.toString());

				if (null != assetVO.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
								.getCustomerId())) {
					assetHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,
							assetVO.getCustomerId());
				}
				if (null != assetVO.getFleet()	&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO.getFleet()))
                {
                	String[] tmpStrFleet = assetVO.getFleet().split(RMDCommonConstants.COMMMA_SEPARATOR);
                	assetHqry.setParameterList(RMDCommonConstants.STRFLEET, tmpStrFleet);
				}
				assetHqry.setFetchSize(2000);
				arlAssetNo = (ArrayList) assetHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
					for (Object[] obj : arlAssetNo) {
						assetResponseVO = new AlertAssetDetailsVO();
						assetResponseVO.setAssetNumber(RMDCommonUtility
								.convertObjectToString(obj[0]));
						assetResponseVO.setVehicleObjId(RMDCommonUtility
								.convertObjectToString(obj[1]));

						assetList.add(assetResponseVO);
					}
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PINPOINT_GET_ASSET);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return assetList;
	}

	@Override
	public AlertDetailsVO getAlertForCustomer(AlertAssetDetailsVO assetVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		final StringBuilder alertQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> arlAssetNo = null;
		AlertDetailsVO alertDetailsVO = null;
		try {

			alertQry.append("SELECT CUSTLKP.CUSTOMER_NAME, CUSTLKP.PP_EMAIL_NOT_MOVING_NOTIFY, ");
			alertQry.append("CUSTLKP.PP_EMAIL_LOW_FUEL_NOTIFY, CUSTLKP.PP_EMAIL_FUELING_NOTIFY, ");
			alertQry.append("CUSTLKP.PP_EMAIL_DWELL_NOTIFY, CUSTLKP.PP_EMAIL_ENG_STATE_NOTIFY, ");
			alertQry.append("CUSTLKP.PP_EMAIL_ENG_ON_ISOLATE_NOTIFY, CUSTLKP.PP_EMAIL_PROXIMITY_NOTIFY, ");
			alertQry.append("CUSTLKP.PP_EMAIL_INTERCHANGE_NOTIFY, CUSTLKP.PP_EMAIL_CONSIST_NOTIFY, ");
			alertQry.append("CUSTLKP.OPERATOR_PWR_BRAKE_NOTIFY_FLAG, CUSTLKP.EMER_AIR_BRK_APPLY_NOTIFY_FLAG, ");
			alertQry.append("CUSTLKP.AESS_SHUTDOWN_NOTIFY_FLAG, CUSTLKP.ALERT_TMOUT_PNLTY_NOTIFY_FLAG, ");
			alertQry.append("CUSTLKP.TO_NOT_USED_NOTIFY_FLAG  ");
			alertQry.append("FROM GETS_SD.GETS_SD_CUST_LKP CUSTLKP, ");
			alertQry.append("SA.TABLE_BUS_ORG TBLBUS  ");
			alertQry.append("WHERE TBLBUS.ORG_ID =:customerId  ");
			alertQry.append("AND TBLBUS.NAME = CUSTLKP.CUSTOMER_NAME  ");

			if (null != hibernateSession) {
				Query alertHqry = hibernateSession.createSQLQuery(alertQry
						.toString());

				if (null != assetVO.getCustomerId()
						&& !RMDCommonConstants.EMPTY_STRING.equals(assetVO
								.getCustomerId())) {
					alertHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,
							assetVO.getCustomerId());
				}

				alertHqry.setFetchSize(2000);
				arlAssetNo = (ArrayList) alertHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) {
					ArrayList<String> alertArrList = null;
					for (Object[] obj : arlAssetNo) {
						alertDetailsVO = new AlertDetailsVO();
						alertArrList = new ArrayList<String>();

						// PP_EMAIL_NOT_MOVING_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[1])) && RMDCommonUtility.convertObjectToString(obj[1])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_NOT_MOVING_NOTIFY);
							}					

						// PP_EMAIL_LOW_FUEL_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[2])) && RMDCommonUtility.convertObjectToString(obj[2])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_LOW_FUEL_NOTIFY);
						}

						// PP_EMAIL_FUELING_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[3])) && RMDCommonUtility.convertObjectToString(obj[3])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_FUELING_NOTIFY);
						}

						// PP_EMAIL_DWELL_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[4])) && RMDCommonUtility.convertObjectToString(obj[4])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_DWELL_NOTIFY);
						}

						// PP_EMAIL_ENG_STATE_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[5])) && RMDCommonUtility.convertObjectToString(obj[5])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_ENG_STATE_NOTIFY);
						}

						// PP_EMAIL_ENG_ON_ISOLATE_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[6])) && RMDCommonUtility.convertObjectToString(obj[6])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_ENG_ON_ISOLATE_NOTIFY);
						}

						// PP_EMAIL_PROXIMITY_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[7])) && RMDCommonUtility.convertObjectToString(obj[7])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_PROXIMITY_NOTIFY);
							}

						// PP_EMAIL_INTERCHANGE_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[8])) && RMDCommonUtility.convertObjectToString(obj[8])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_INTERCHANGE_NOTIFY);
							}

						// PP_EMAIL_CONSIST_NOTIFY
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[9])) && RMDCommonUtility.convertObjectToString(obj[9])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.PP_EMAIL_CONSIST_NOTIFY);
							}

						// OPERATOR_PWR_BRAKE_NOTIFY_FLAG
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[10])) && RMDCommonUtility.convertObjectToString(obj[10])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.OPERATOR_PWR_BRAKE_NOTIFY_FLAG);
						}

						// EMER_AIR_BRK_APPLY_NOTIFY_FLAG
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[11])) && RMDCommonUtility.convertObjectToString(obj[11])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.EMER_AIR_BRK_APPLY_NOTIFY_FLAG);
							}

						// AESS_SHUTDOWN_NOTIFY_FLAG
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[12])) && RMDCommonUtility.convertObjectToString(obj[12])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.AESS_SHUTDOWN_NOTIFY_FLAG);
							}

						// ALERT_TMOUT_PNLTY_NOTIFY_FLAG
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[13])) && RMDCommonUtility.convertObjectToString(obj[13])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.ALERT_TMOUT_PNLTY_NOTIFY_FLAG);
						}

						// TO_NOT_USED_NOTIFY_FLAG
						if (!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(obj[14])) && RMDCommonUtility.convertObjectToString(obj[14])
								.equals(RMDCommonConstants.LETTER_Y)) {
								alertArrList
										.add(AlertConstants.TO_NOT_USED_NOTIFY_FLAG);
							}

						alertDetailsVO.setAlertNotifyTypeList(alertArrList);
					}
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return alertDetailsVO;
	}
	
	@Override
	public List<ShopsVO> getAllShops() throws RMDDAOException {

		List<Object[]> shopList = null;
		List<ShopsVO> shopStrList = null;
		Session objHibernateSession = null;
		ShopsVO objShopsVO = null;
		final StringBuilder shopBuffer = new StringBuilder();
		try {

			objHibernateSession = getHibernateSession();
			if (objHibernateSession != null) {
				shopBuffer
						.append("SELECT shop.OBJID,shop.PROXIMITY_DESC,cust.ORG_ID FROM ");
				shopBuffer
						.append("GETS_RMD_EOA_PROXIMITY_DEF shop, TABLE_BUS_ORG cust WHERE shop.PROXIMITY2BUS_ORG=cust.OBJID AND shop.ACTIVE_FLAG = 'Y' ");
				shopBuffer
						.append("AND shop.PROXIMITY_LABEL = 'G' ORDER BY cust.ORG_ID");

				Query shopQry = objHibernateSession.createSQLQuery(shopBuffer
						.toString());
				shopList = (ArrayList<Object[]>) shopQry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(shopList)) {
					shopStrList = new ArrayList<ShopsVO>();
					for (Object[] obj : shopList) {
						objShopsVO = new ShopsVO();

						// OBJID
						if (RMDCommonUtility.convertObjectToString(obj[0]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[0]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setShopId(RMDCommonUtility
									.convertObjectToString(obj[0]));
						}
						// PROXIMITY_DESC
						if (RMDCommonUtility.convertObjectToString(obj[1]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[1]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setShopName(RMDCommonUtility
									.convertObjectToString(obj[1]));
						}
						// cust.ORG_ID
						if (RMDCommonUtility.convertObjectToString(obj[2]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[2]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setCustomerId(RMDCommonUtility
									.convertObjectToString(obj[2]));
						}
						shopStrList.add(objShopsVO);
					}
				}
			}

		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}

		return shopStrList;
	}
	
	/**
	 * @Author:
	 * @param:
	 * @return:List<ModelVO>
	 * @throws:RMDWebException,Exception
	 * @Description: This method is used for fetching the All Model values.
	 */
	@Override
	public List<ModelVO> getAllModels() throws RMDDAOException {
		List<Object[]> resultList = null;
		List<ModelVO> modelList = null;
		Session objHibernateSession = null;
		ModelVO objModelVO = null;
		final StringBuilder caseQry = new StringBuilder();
		try {
			objHibernateSession = getHibernateSession();
			if (null != objHibernateSession) {
				caseQry.append("SELECT  DISTINCT MODEL.MODEL_NAME,MODEL.OBJID,CUSTOMER.ORG_ID,MODEL.FAMILY, MODEL.MODEL_GENERAL ");
				caseQry.append("FROM GETS_RMD_MODEL MODEL,GETS_RMD_VEHICLE ASSET,GETS_RMD_FLEET FLEET,TABLE_BUS_ORG CUSTOMER ");
				caseQry.append("WHERE ASSET.VEHICLE2MODEL = MODEL.OBJID ");
				caseQry.append("AND ASSET.VEHICLE2FLEET = FLEET.OBJID ");
				caseQry.append("AND CUSTOMER.OBJID = FLEET.FLEET2BUS_ORG ");
				caseQry.append("AND MODEL.FAMILY NOT LIKE '%EMD%' ");
				Query caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());
				resultList = (ArrayList<Object[]>) caseHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					modelList = new ArrayList<ModelVO>(resultList.size());
					for (Object[] obj : resultList) {
						objModelVO = new ModelVO();
						objModelVO.setModelObjId(RMDCommonUtility
								.convertObjectToString(obj[0]));
						objModelVO.setModelName(RMDCommonUtility
								.convertObjectToString(obj[1]));
						objModelVO.setCustomerId(RMDCommonUtility
								.convertObjectToString(obj[2]));
						String modelFamily = RMDCommonUtility
								.convertObjectToString(obj[3]);
						if(modelFamily.equalsIgnoreCase(RMDCommonConstants.ACCCA) || modelFamily.equalsIgnoreCase(RMDCommonConstants.DCCCA))
						{
							objModelVO.setModelFamily(RMDCommonConstants.CCA);
						}
						else
						{
							objModelVO.setModelFamily(modelFamily);
						}	
						
						objModelVO.setModelGeneral(RMDCommonUtility
                                .convertObjectToString(obj[4]));
						modelList.add(objModelVO);
					}
				}
			}
		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_DETAILS);
		} finally {
			releaseSession(objHibernateSession);
			resultList = null;
		}
		return modelList;
	}
	
	/**
	 * @Author:
	 * @param:
	 * @return:List<PPMultiUsersVO>
	 * @throws:RMDWebException,Exception
	 * @Description: This method is used for fetching the Multi Users.
	 */
	@Override
	public List<AlertMultiUsersVO> getMultiUsers(String customerId, String userType)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		List<AlertMultiUsersVO> userList = null;
		Session objHibernateSession = null;
		AlertMultiUsersVO objAlertMultiUsersVO = null;

		Query caseHqry = null;
		StringBuilder caseQry = null;

		try {
			objHibernateSession = getHibernateSession();
			if (null != objHibernateSession) {
				caseQry = new StringBuilder();
				caseQry.append("SELECT DISTINCT GET_USR_USERS_SEQ_ID,USER_ID, FIRST_NAME, LAST_NAME,EMAIL_ID,USER_PHONE_NUMBER,USER_COUNTRY_CODE ");
				caseQry.append("FROM GET_USR.GET_USR_USERS USR,GET_USR.GET_USR_USER_CUSTOMERS  CUST,TABLE_BUS_ORG ORG ");
				caseQry.append("WHERE USR.STATUS = 1 ");
				caseQry.append("AND ((CUST.LINK_CUSTOMERS = ORG.OBJID AND USR.GET_USR_USERS_SEQ_ID = CUST.LINK_USERS) ");
				caseQry.append("OR USR.GET_USR_USERS_SEQ_ID NOT IN (SELECT LINK_USERS FROM GET_USR.GET_USR_USER_CUSTOMERS)) ");
				caseQry.append("AND ORG.ORG_ID =:customerId ");
				if (null != userType
						&& userType.equals(RMDCommonConstants.CUSTOMERSTR)) {
					caseQry.append("AND USR.USER_TYPE='Customer' ");
				} else {
					caseQry.append(" AND USR.USER_TYPE IS NOT NULL ");
					caseQry.append(" AND NOT USR.USER_TYPE='Ex Employee' ");
				}
				caseQry.append("AND EMAIL_ID IS NOT NULL ");
				caseQry.append("ORDER BY FIRST_NAME ASC ");
				caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());
				caseHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,
						customerId);
				resultList = (ArrayList<Object[]>) caseHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					userList = new ArrayList<AlertMultiUsersVO>(resultList.size());
					for (Object[] obj : resultList) {
						objAlertMultiUsersVO = new AlertMultiUsersVO();
						objAlertMultiUsersVO.setUserId(RMDCommonUtility
								.convertObjectToString(obj[1]).trim()
								+ RMDCommonConstants.TILDA
								+ RMDCommonUtility
										.convertObjectToString(obj[0]).trim());
						objAlertMultiUsersVO.setFirstName(RMDCommonUtility
								.convertObjectToString(obj[2]));
						objAlertMultiUsersVO.setLastName(RMDCommonUtility
								.convertObjectToString(obj[3]));
						objAlertMultiUsersVO.setUserEmailId(RMDCommonUtility
								.convertObjectToString(obj[4]));
						objAlertMultiUsersVO.setUserPhoneNumber(RMDCommonUtility
								.convertObjectToString(obj[5]));
						objAlertMultiUsersVO.setUserCountryCode(RMDCommonUtility
								.convertObjectToString(obj[6]));
						userList.add(objAlertMultiUsersVO);
						objAlertMultiUsersVO = null;
					}
				}
			}
		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_MULTI_USERS);
		} finally {
			releaseSession(objHibernateSession);
			resultList = null;
			caseHqry = null;
			caseQry = null;
		}
		return userList;
	}
	
	@Override
	public List<AlertDetailsVO> getConfigAlertForCustomer(String customerId,
			String modelFamilyVal) throws RMDDAOException {
		List<Object[]> resultList = null;
		List<AlertDetailsVO> ruleList = null;
		Session objHibernateSession = null;
		String modelFmlyVal = null;
		AlertDetailsVO objPPAlertDetailsVO = null;
		final StringBuilder caseQry = new StringBuilder();
		if(modelFamilyVal.equalsIgnoreCase(RMDCommonConstants.ACCCA) || modelFamilyVal.equalsIgnoreCase(RMDCommonConstants.DCCCA))
		{
			modelFmlyVal = RMDCommonConstants.CCA;
		}
		else
		{
		modelFmlyVal = modelFamilyVal;
		}
		try {
			objHibernateSession = getHibernateSession();
			if (null != objHibernateSession) {
				caseQry.append("SELECT distinct FIN.RULE_TITLE,HIS.ACTIVE,HIS.ORIGINAL_ID ");
				caseQry.append("FROM GETS_TOOL_DPD_FINRUL FIN,GETS_TOOL_DPD_RULHIS HIS,GETS_TOOL_DPD_RULEDEF DEF, ");
				caseQry.append("GETS_TOOL_DPD_CUST C,SA.TABLE_BUS_ORG B,gets_rmd_model mod ");
				caseQry.append("WHERE HIS.RULHIS2FINRUL =FIN.OBJID AND DEF.RULEDEF2FINRUL = FIN.OBJID AND DEF.RULE_TYPE = 'Alert' ");
				caseQry.append("AND HIS.ACTIVE = 1 AND HIS.COMPLETED = 1 AND C.CUST2RULEDEF = DEF.OBJID ");
				caseQry.append("AND C.EXCLUDE = 'N' AND C.CUST2BUSORG = B.OBJID AND B.ORG_ID  =:customerId ");
				if(null != modelFmlyVal && !modelFmlyVal.equalsIgnoreCase(AlertConstants.NOMODEL))
				{
					caseQry.append("AND FIN.FAMILY =:modelFamily ");
				}				
				Query caseHqry = objHibernateSession.createSQLQuery(caseQry
						.toString());
				caseHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,
						customerId);
				if(null != modelFmlyVal && !modelFmlyVal.equalsIgnoreCase(AlertConstants.NOMODEL))
				{
					caseHqry.setParameter(AlertConstants.MODELFAMILY, modelFmlyVal);
				}				
				resultList = (ArrayList<Object[]>) caseHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					ruleList = new ArrayList<AlertDetailsVO>(
							resultList.size());
					for (Object[] obj : resultList) {
						objPPAlertDetailsVO = new AlertDetailsVO();
						objPPAlertDetailsVO.setRuleTitle(RMDCommonUtility
								.convertObjectToString(obj[0]));
						objPPAlertDetailsVO.setOriginalId(RMDCommonUtility
								.convertObjectToString(obj[2]));
						ruleList.add(objPPAlertDetailsVO);
					}
				}
			}
		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_CONFIG_ALERTS);
		} finally {
			releaseSession(objHibernateSession);
			resultList = null;
		}
		return ruleList;
	}
	
	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user email of the user
	 */
	@Override
	public String getUserEmailId(String userId) throws RMDDAOException {
		Session session = null;
		String userEmailId = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT EMAIL_ID FROM GET_USR.GET_USR_USERS WHERE USER_ID =:userId ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.USERID, userId);
			if (null != caseHqry.uniqueResult()) {
				userEmailId = caseHqry.uniqueResult().toString();
			} else {
				userEmailId = RMDCommonConstants.STRING_NULL;
			}
		} catch (Exception e) {
			userEmailId = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_EMAIL_ID);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);

		} finally {
			releaseSession(session);
		}
		return userEmailId;
	}
	
	/**
	 * @param userId
	 * @return String
	 * @Description: method to get OTP parameters
	 */
	@Override
	public String getOTPParameters() throws RMDDAOException {
		Session session = null;
		String otp_parameter =  "";
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT VALUE FROM GETS_RMD_SYSPARM WHERE TITLE IN (:otp_param1,:otp_param2,:otp_param3)");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.OTP_PARAM_1,RMDCommonConstants.AUTO_RETRY_ATTEMPTS);
			caseHqry.setParameter(RMDCommonConstants.OTP_PARAM_2,RMDCommonConstants.ATTEMPTS_LEFT);
			caseHqry.setParameter(RMDCommonConstants.OTP_PARAM_3,RMDCommonConstants.TIME_REMAINING);
			if (null != caseHqry.list()) {
				List<String> temp = caseHqry.list();
				for(String i:temp)
				{
					otp_parameter += i+'~';
				}
			} else {
				otp_parameter = RMDCommonConstants.STRING_NULL;
			}
		} catch (Exception e) {
			otp_parameter = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_PHONE_NO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return otp_parameter;
	}
	
	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user Phone no. of the user
	 */
	@Override
	public String getUserPhoneNo(String userId) throws RMDDAOException {
		Session session = null;
		String userPhoneNo = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT USER_PHONE_NUMBER FROM GET_USR.GET_USR_USERS WHERE USER_ID =:userId ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.USERID, userId);
			
			if (null != caseHqry.uniqueResult()) {
				userPhoneNo = caseHqry.uniqueResult().toString();
			} else {
				userPhoneNo = RMDCommonConstants.STRING_NULL;
			}
		} catch (Exception e) {
			userPhoneNo = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_PHONE_NO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);

		} finally {
			releaseSession(session);
		}
		return userPhoneNo;
	}
	
	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user Phone country code of the user
	 */
	@Override
	public String getUserPhoneCountryCode(String userId) throws RMDDAOException {
		Session session = null;
		String userPhoneCountryCode = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT USER_COUNTRY_CODE FROM GET_USR.GET_USR_USERS WHERE USER_ID =:userId ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.USERID, userId);
			
			if (null != caseHqry.uniqueResult()) {
				userPhoneCountryCode = caseHqry.uniqueResult().toString();
			} else {
				userPhoneCountryCode = RMDCommonConstants.STRING_NULL;
			}
		} catch (Exception e) {
			userPhoneCountryCode = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_PHONE_COUNTRY_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);

		} finally {
			releaseSession(session);
		}
		return userPhoneCountryCode;
	}
	
	@Override
	public List<String> getATSEnabledCustomers() throws RMDDAOException {
		List<String> atsCustomers = null;
		List<String> atsCustomerresult = null;
		Session objHibernateSession = null;

		try {
			atsCustomers = new ArrayList();
			objHibernateSession = getHibernateSession();
			Query customerQuer = objHibernateSession
					.createSQLQuery("SELECT DISTINCT ( org_id ) "
							+ "FROM   gets_rmd_cust_rnh_rn_v "
							+ "WHERE  vehicle_objid IN (SELECT veh.objid "
							+ "                         FROM   table_site_part tsp "
							+ "                                JOIN gets_rmd_vehicle veh "
							+ "                                  ON tsp.objid = veh.vehicle2site_part "
							+ "                                JOIN gets_rmd_vehcfg cfg "
							+ "                                  ON veh.objid = cfg.veh_cfg2vehicle "
							+ "                                JOIN gets_rmd_master_bom bom "
							+ "                                  ON cfg.vehcfg2master_bom = bom.objid "
							+ "                         WHERE  ( ( bom.config_item = 'CMU' "
							+ "                                    AND cfg.current_version = '2' ) "
							+ "                                   OR ( bom.config_item = 'HPEAP' "
							+ "                                        AND cfg.current_version = '1' ) ))");

			atsCustomerresult = (List<String>) customerQuer.list();

			if (atsCustomerresult != null && !atsCustomerresult.isEmpty()) {
				for (String custId : atsCustomerresult) {
					atsCustomers.add(custId);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							"Error_in-getATSEnabledCustomers"), ex,
					RMDCommonConstants.FATAL_ERROR);// Need to check the STring
													// Later
		}

		catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return atsCustomers;
	}

	@Override
	public String getAlertSubAssetCount(String userId) throws RMDDAOException {
		Session objHibernateSession = null;
		final StringBuilder assetQry = new StringBuilder();
		List<Object> assetSubList = null;
		String assetCount = "0";
		try {

			objHibernateSession = getHibernateSession();
			if (objHibernateSession != null) {
				assetQry.append("SELECT COUNT(1) AS ASSETCOUNT FROM GET_USR.GET_USR_VEHICLE_ALERT ASSETTBL, GET_USR.GET_USR_NOTIFY_ALERT_CONF NOTIFYTBL, GET_USR.GET_USR_USERS USRSTBL ");
				assetQry.append("WHERE USRSTBL.USER_ID = :userId ");
				assetQry.append("AND NOTIFYTBL.NOTIFY_ALERT_CONF2USERS_SEQID = USRSTBL.GET_USR_USERS_SEQ_ID ");
				assetQry.append("AND ASSETTBL.CONF_ID2NOTIFY_ALERT_CONF = NOTIFYTBL.OBJID ");

				Query assetHqry = objHibernateSession.createSQLQuery(assetQry
						.toString());

				if (null != userId
						&& !RMDCommonConstants.EMPTY_STRING.equals(userId)) {
					assetHqry.setParameter(RMDCommonConstants.USERID, userId);
				}
				assetSubList = (ArrayList) assetHqry.list();

				if (RMDCommonUtility.isCollectionNotEmpty(assetSubList)) {

					for (Object obj : assetSubList) {
						if (RMDCommonUtility.convertObjectToString(obj) != null
								&& !RMDCommonUtility.convertObjectToString(obj)
										.trim().equals(RMDCommonConstants.EMPTY_STRING)) {
							assetCount = RMDCommonUtility
									.convertObjectToString(obj);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}

		return EsapiUtil.stripXSSCharacters(assetCount);
	}

	@Override
	public List<RestrictedAlertShopVO> getRestrictedAlertShop()
			throws RMDDAOException {

		List<Object[]> shopList = null;
		List<RestrictedAlertShopVO> shopStrList = null;
		Session objHibernateSession = null;
		RestrictedAlertShopVO objShopsVO = null;
		final StringBuilder shopChkBuffer = new StringBuilder();
		try {

			objHibernateSession = getHibernateSession();
			if (objHibernateSession != null) {
				shopChkBuffer
						.append("SELECT RL_CNFGN.FLTR_RL_CNFGN2ALERT_TYPE,ALERT_TYPE.ALERT_TYPE,RL_CNFGN.PROXIMITY_CHK_FLG,RL_CNFGN.FLTR_RL_CNFGN2BUS_ORG,TBO.ORG_ID ");
				shopChkBuffer
						.append("FROM GETS_RMD.GETS_RMD_FLTR_RL_CNFGN RL_CNFGN LEFT OUTER JOIN GETS_RMD.GETS_RMD_FLTR_ALERT_TYPE ALERT_TYPE ");
				shopChkBuffer
						.append("ON ALERT_TYPE.OBJID=RL_CNFGN.FLTR_RL_CNFGN2ALERT_TYPE LEFT OUTER JOIN SA.TABLE_BUS_ORG TBO ON TBO.OBJID=RL_CNFGN.FLTR_RL_CNFGN2BUS_ORG WHERE RL_CNFGN.PROXIMITY_CHK_FLG='Y'");

				Query shopChkQry = objHibernateSession
						.createSQLQuery(shopChkBuffer.toString());
				shopList = (ArrayList<Object[]>) shopChkQry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(shopList)) {
					shopStrList = new ArrayList<RestrictedAlertShopVO>();
					for (Object[] obj : shopList) {
						objShopsVO = new RestrictedAlertShopVO();

						// RL_CNFGN.FLTR_RL_CNFGN2ALERT_TYPE
						if (RMDCommonUtility.convertObjectToString(obj[0]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[0]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setAlertId(RMDCommonUtility
									.convertObjectToString(obj[0]));
						}

						// ALERT_TYPE.ALERT_TYPE
						if (RMDCommonUtility.convertObjectToString(obj[1]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[1]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setAlertName(RMDCommonUtility
									.convertObjectToString(obj[1]));
						}
						// RL_CNFGN.PROXIMITY_CHK_FLG
						if (RMDCommonUtility.convertObjectToString(obj[2]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[2]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setProximityCheckFlag(RMDCommonUtility
									.convertObjectToString(obj[2]));
						}
						// TBO.ORG_ID
						if (RMDCommonUtility.convertObjectToString(obj[4]) != null
								&& !RMDCommonUtility
										.convertObjectToString(obj[4]).trim()
										.equals(RMDCommonConstants.EMPTY_STRING)) {
							objShopsVO.setCustomerId(RMDCommonUtility
									.convertObjectToString(obj[4]));
						}
						shopStrList.add(objShopsVO);
					}
				}
			}

		} catch (Exception e) {
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}

		return shopStrList;
	}
	
	@Override
	public AlertHstDetailsVO getAlertHistoryDetails(AlertHistoryDetailsVO objAlertHistoryDetailsVO) throws RMDDAOException
	{
		List<AlertHistoryDetailsVO> alertHistoryHdrlst = new ArrayList<AlertHistoryDetailsVO>();
		AlertHstDetailsVO objAlertHstDetailsVO = new AlertHstDetailsVO();
		Session hibernateSession = null;
		final StringBuilder alertQry = new StringBuilder();
		final StringBuilder alertCountQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> alertSubList = null;
		AlertHistoryDetailsVO alertHistoryDetailsVO = null;	
		DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.ALERT_DATE_FORMAT);
		int count = 0;
		String strCount = null;
		
		if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrType()) && objAlertHistoryDetailsVO.getStrType().equalsIgnoreCase(AlertConstants.SELECT))
		{
			objAlertHistoryDetailsVO.setStrType(RMDCommonConstants.EMPTY_STRING);
		}
		if (!objAlertHistoryDetailsVO.isMultiSubscriptionPrivilege())
		{
			objAlertHistoryDetailsVO.setStrMultiUsers(objAlertHistoryDetailsVO.getUserId());
		}
		String notificationType=RMDCommonConstants.EMPTY_STRING;
		try
		{
			alertCountQry.append("select count(*) from( ");
			alertQry.append("SELECT VI.VEHICLE_NO,B2B.ALERT_TYPE,B2BDET.NOTIFICATION_DATE,B2B.GPS_LAT, B2B.GPS_LONG, ");
			alertQry.append("R.REGION, RR.SUB_REGION,VI.MODEL_NAME_V, VI.FLEET_NUMBER_V,B2B.ALERT_TITLE, ");
			alertQry.append("B2B.EMAIL_HIST2FINRUL, B2B.PDF_PATH, S.PROXIMITY_DESC,B2BDET.EMAIL_TEXT,B2B.EMAIL_HIST2FIRING,TO_CHAR(B2B.FIRING_DATE,'DD-MON-YY hh24:mi:ss'), ");
			if (objAlertHistoryDetailsVO.isMultiSubscriptionPrivilege())
			{
				alertQry.append(" B2BDET.SEND_TO_EMAIL_ADDRESS, ");
			}
			alertQry.append("case when B2BDET.IS_EMAIL_SENT='Y' and B2BDET.IS_SMS_SENT='Y' then 'EMAIL,SMS' when B2BDET.IS_EMAIL_SENT='Y' and B2BDET.IS_SMS_SENT='N' then 'EMAIL' ");
			alertQry.append("when B2BDET.IS_EMAIL_SENT='N' and B2BDET.IS_SMS_SENT='Y' then 'SMS' end as NOTIFICATION, B2BDET.SEND_TO_PHONE_NUMBER, B2BDET.IS_EMAIL_SENT, B2BDET.IS_SMS_SENT,B2BDET.SMS_TEXT ");
			alertQry.append("FROM GETS_RMD_CUST_RNH_RN_V VI,GETS_B2B_EMAIL_HISTORY B2B,GETS_B2B_EMAIL_DETAIL B2BDET,  GET_USR.GET_USR_USERS USR, ");
			alertQry.append("GETS_RMD_REGION R, GETS_RMD_PP_SUB_REGION RR,GETS_RMD_EOA_PROXIMITY_DEF S ");		
			alertQry.append("WHERE B2B.OBJID = B2BDET.EMAIL_DETAIL2HISTORY AND B2B.EMAIL_HIST2VEHICLE = VI.VEHICLE_OBJID (+) ");			
			alertQry.append("AND B2B.EMAIL_HIST2PROXIMITY_DEF = S.OBJID (+) AND B2B.EMAIL_HIST2REGION = R.OBJID(+)  ");
			alertQry.append("AND B2B.EMAIL_HIST2SUB_REGION = RR.OBJID(+) AND B2BDET.USER_ID = USR.USER_ID ");
						
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrNumberOfDays()))
			{
				alertQry.append("AND B2BDET.NOTIFICATION_DATE >= (SYSDATE - :strNumberOfDays) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrFromDate()))
			{
				alertQry.append("AND B2BDET.NOTIFICATION_DATE BETWEEN TO_DATE(:strFromDate,'MM/DD/YYYY HH24:MI:SS')  AND TO_DATE(:strToDate,'MM/DD/YYYY HH24:MI:SS') ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrCustomer()))
			{
				alertQry.append("AND VI.ORG_ID = :strCustomer ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRegion()))
			{
				alertQry.append("AND B2B.EMAIL_HIST2REGION = :strRegion ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrSubDivision()))
			{
				alertQry.append("AND B2B.EMAIL_HIST2SUB_REGION IN(:strSubDivision) ");
			}	
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrModel()))
			{
				alertQry.append("AND VI.MODEL_NAME_V IN(:strModel) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrFleet()))
			{
				alertQry.append("AND VI.FLEET_NUMBER_V IN(:strFleet) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrAsset()))
			{
				alertQry.append("AND VI.VEHICLE_NO IN(:strAsset) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrShop()))
			{
				alertQry.append("AND S.OBJID IN(:strShop) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrType()))
			{
				alertQry.append("AND B2B.ALERT_TYPE = :strType ");
			}
			if ((!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRule()))
			  ||(!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrAlert()))
			  ||(!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRxFilter())))
			{
				alertQry.append("AND B2B.ALERT_TITLE IN(:strAlertTitle) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrSearchValue()))
			{
				alertQry.append("AND UPPER(B2B.ALERT_TITLE) LIKE UPPER(:strAlertTitle) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrMultiUsers()))
			{
				alertQry.append("AND B2BDET.SEND_TO_EMAIL_ADDRESS IN(:strMultiUsers) ");
			}				
			if (null != objAlertHistoryDetailsVO.getUserType() && objAlertHistoryDetailsVO.getUserType().equals(RMDCommonConstants.CUSTOMERSTR)) 
			{
				alertQry.append("AND USR.USER_TYPE IN ('Customer') ");
			} 
			else
			{
				alertQry.append("AND USR.USER_TYPE IN ('Employee','Customer','Contractor','Functional') ");
			}
			alertQry.append("AND USR.STATUS =1 ");
			alertQry.append("AND (B2BDET.IS_EMAIL_SENT = 'Y' OR B2BDET.IS_SMS_SENT= 'Y') ");
			alertQry.append("ORDER BY B2BDET.NOTIFICATION_DATE desc");
			alertCountQry.append(alertQry);
			alertCountQry.append(" )");
			if (null != hibernateSession) 
			{
				Query alertHqry = hibernateSession.createSQLQuery(alertQry.toString());
				Query alertHqry1 = hibernateSession.createSQLQuery(alertCountQry.toString());
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrNumberOfDays()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_NUMBER_OF_DAYS,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrNumberOfDays()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_NUMBER_OF_DAYS,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrNumberOfDays()));
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrFromDate()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_FROM_DATE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrFromDate()));
					alertHqry.setParameter(AlertConstants.STR_ALERT_TO_DATE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrToDate()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_FROM_DATE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrFromDate()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_TO_DATE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrToDate()));
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrCustomer()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_CUSTOMER,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrCustomer()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_CUSTOMER,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrCustomer()));
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRegion()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_REGION,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrRegion()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_REGION,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrRegion()));
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrSubDivision()))
				{
					String[] strSubDivision = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrSubDivision()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_SUB_DIVISION, strSubDivision);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_SUB_DIVISION, strSubDivision);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrModel()))
				{
					String[] strModel =EsapiUtil.stripXSSCharacters( objAlertHistoryDetailsVO.getStrModel()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_MODEL, strModel);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_MODEL, strModel);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrFleet()))
				{
					String[] strFleet = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrFleet()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_FLEET, strFleet);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_FLEET, strFleet);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrAsset()))
				{
					String[] strAsset = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrAsset()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_ASSET, strAsset);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_ASSET, strAsset);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrShop()))
				{
					String[] strShop = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrShop()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_SHOP, strShop);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_SHOP, strShop);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrType()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_TYPE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrType()));
					alertHqry1.setParameter(AlertConstants.STR_ALERT_TYPE,	EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrType()));
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRule()))
				{
					String[] strAlertTitle = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrRule()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrAlert()))
				{
					String[] strAlertTitle = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrAlert()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrRxFilter()))
				{
					String[] strAlertTitle = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrRxFilter()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_TITLE, strAlertTitle);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrSearchValue()))
				{
					alertHqry.setParameter(AlertConstants.STR_ALERT_TITLE,	RMDServiceConstants.PERCENTAGE+EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrSearchValue())+RMDServiceConstants.PERCENTAGE);
					alertHqry1.setParameter(AlertConstants.STR_ALERT_TITLE,	RMDServiceConstants.PERCENTAGE+EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrSearchValue())+RMDServiceConstants.PERCENTAGE);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objAlertHistoryDetailsVO.getStrMultiUsers()))
				{
					String[] strMultiUsers = EsapiUtil.stripXSSCharacters(objAlertHistoryDetailsVO.getStrMultiUsers()).split(RMDCommonConstants.COMMMA_SEPARATOR);
					alertHqry.setParameterList(AlertConstants.STR_ALERT_MULTIUSERS, strMultiUsers);
					alertHqry1.setParameterList(AlertConstants.STR_ALERT_MULTIUSERS, strMultiUsers);
				}								
				
				BigDecimal count1 = (BigDecimal) alertHqry1.uniqueResult();
				count = count1.intValue();
				strCount = String.valueOf(count);

				if (objAlertHistoryDetailsVO.getStrMethodFlag().equalsIgnoreCase("Export"))
				{
					int exportRecordsPerPage = Integer.valueOf(strCount);
					objAlertHistoryDetailsVO.setRecordsPerPage(exportRecordsPerPage);
				}
				
				if (objAlertHistoryDetailsVO.getRecordsPerPage() >= 0) {
				alertHqry.setFirstResult(objAlertHistoryDetailsVO.getStartRow());
				alertHqry.setMaxResults(objAlertHistoryDetailsVO.getRecordsPerPage());}
				alertSubList = (ArrayList) alertHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(alertSubList)) 
				{
					for (Object[] obj : alertSubList) 
					{
						alertHistoryDetailsVO = new AlertHistoryDetailsVO();																		
						alertHistoryDetailsVO.setStrAsset(RMDCommonUtility.convertObjectToString(obj[0]));																
						alertHistoryDetailsVO.setStrGPSLat(RMDCommonUtility.convertObjectToString(obj[3]));
						alertHistoryDetailsVO.setStrGPSLog(RMDCommonUtility.convertObjectToString(obj[4]));
						alertHistoryDetailsVO.setStrRegion(RMDCommonUtility.convertObjectToString(obj[5]));
						alertHistoryDetailsVO.setStrSubDivision(RMDCommonUtility.convertObjectToString(obj[6]));
						alertHistoryDetailsVO.setStrFleet(RMDCommonUtility.convertObjectToString(obj[8]));				
						alertHistoryDetailsVO.setStrModel(RMDCommonUtility.convertObjectToString(obj[7]));
						alertHistoryDetailsVO.setStrShop(RMDCommonUtility.convertObjectToString(obj[12]));			
						alertHistoryDetailsVO.setStrAlertTitle(RMDCommonUtility.convertObjectToString(obj[9]));				
						alertHistoryDetailsVO.setStrRuleLogic(RMDCommonUtility.convertObjectToString(obj[10]));										
						alertHistoryDetailsVO.setStrPdfUrl(RMDCommonUtility.convertObjectToString(obj[11]));	
						alertHistoryDetailsVO.setEmailText(RMDCommonUtility.convertObjectToString(obj[13]));
						alertHistoryDetailsVO.setFiringId(RMDCommonUtility.convertObjectToString(obj[14]));
						alertHistoryDetailsVO.setFiredOn(RMDCommonUtility.convertObjectToString(obj[15]));					
						alertHistoryDetailsVO.setStrType(RMDCommonUtility.convertObjectToString(obj[1]));						
						if (objAlertHistoryDetailsVO.isMultiSubscriptionPrivilege())
						{
							alertHistoryDetailsVO.setStrMultiUsers(RMDCommonUtility.convertObjectToString(obj[16]));
							alertHistoryDetailsVO.setNotification(RMDCommonUtility.convertObjectToString(obj[17]));
							alertHistoryDetailsVO.setMobileNo(RMDCommonUtility.convertObjectToString(obj[18]));
							alertHistoryDetailsVO.setIsEmailFlag(RMDCommonUtility.convertObjectToString(obj[19]));
							alertHistoryDetailsVO.setIsSmsFlag(RMDCommonUtility.convertObjectToString(obj[20]));
							alertHistoryDetailsVO.setSmsText(RMDCommonUtility.convertObjectToString(obj[21]));
						}
						else
						{
							alertHistoryDetailsVO.setNotification(RMDCommonUtility.convertObjectToString(obj[16]));
							alertHistoryDetailsVO.setMobileNo(RMDCommonUtility.convertObjectToString(obj[17]));
							alertHistoryDetailsVO.setIsEmailFlag(RMDCommonUtility.convertObjectToString(obj[18]));
							alertHistoryDetailsVO.setIsSmsFlag(RMDCommonUtility.convertObjectToString(obj[19]));
							alertHistoryDetailsVO.setSmsText(RMDCommonUtility.convertObjectToString(obj[20]));
						}
						Date strAlertDate = formatter.parse(RMDCommonUtility.convertObjectToString(obj[2]));						
						if (null != strAlertDate)
						{	                       
	                        alertHistoryDetailsVO.setStrAlertDate(strAlertDate);
	                    }
						
						alertHistoryHdrlst.add(alertHistoryDetailsVO);
					}
				}
			}
			objAlertHstDetailsVO.setAlertHistoryHdrlst(alertHistoryHdrlst);
			objAlertHstDetailsVO.setRecordsTotal(strCount);
		} 
		catch (RMDDAOConnectionException ex)
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_HISTORY_DATA);
			throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR);			
		} 
		catch (Exception e) 
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ALERT_HISTORY_DATA);
			throw new RMDDAOException(errorCode, RMDCommonConstants.MAJOR_ERROR);
		} 
		finally 
		{
			releaseSession(hibernateSession);
			alertSubList = null;
		}
		return objAlertHstDetailsVO;
	}

	@Override
	public List<AlertAssetDetailsVO> getAlertHisotryPopulateData(AlertAssetDetailsVO assetVO)throws RMDDAOException 
	{
		List<AlertAssetDetailsVO> assetList = new ArrayList<AlertAssetDetailsVO>();
		Session hibernateSession = null;
		final StringBuilder assetQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> arlAssetNo = null;
		AlertAssetDetailsVO assetResponseVO = null;
		try 
		{
			assetQry.append("SELECT GRV.OBJID,TBO.ORG_ID,GRVH.VEH_HDR,TSP.SERIAL_NO, ");
			assetQry.append("GRM.MODEL_NAME,GRF.FLEET_NUMBER,TBO.NAME,GRM.OBJID MODELID,GRF.OBJID FLEETID ");
			assetQry.append("FROM GETS_RMD_VEH_HDR GRVH,TABLE_BUS_ORG TBO,GETS_RMD_VEHICLE GRV, ");
			assetQry.append("TABLE_SITE_PART TSP,GETS_RMD_MODEL GRM,GETS_RMD_FLEET GRF ");
			assetQry.append("WHERE GRVH.VEH_HDR2BUSORG = TBO.OBJID AND GRVH.OBJID = GRV.VEHICLE2VEH_HDR ");
			assetQry.append("AND GRV.VEHICLE2SITE_PART = TSP.OBJID AND GRF.OBJID = GRV.VEHICLE2FLEET ");
			assetQry.append("AND GRM.OBJID = GRV.VEHICLE2MODEL AND TSP.S_SERIAL_NO NOT LIKE '%BAD%' ");
			assetQry.append("AND TBO.ORG_ID = :customerId ");
			if (!RMDCommonUtility.isNullOrEmpty(assetVO.getFleet())) 
            {
				assetQry.append("AND GRF.FLEET_NUMBER =:strFleet ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(assetVO.getModel())) 
            {
				assetQry.append("AND GRM.MODEL_NAME =:strModel ");
			}
			if (null != hibernateSession)
			{
				Query assetHqry = hibernateSession.createSQLQuery(assetQry.toString());

				if (!RMDCommonUtility.isNullOrEmpty(assetVO.getCustomerId())) 
				{
					assetHqry.setParameter(RMDCommonConstants.CUSTOMER_ID,assetVO.getCustomerId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(assetVO.getFleet()))
                {
                	assetHqry.setParameter(RMDCommonConstants.STRFLEET, assetVO.getFleet());
				}
				if (!RMDCommonUtility.isNullOrEmpty(assetVO.getModel()))
                {
					assetHqry.setParameter(RMDCommonConstants.STRMODEL, assetVO.getModel());
				}
				assetHqry.setFetchSize(2000);
				arlAssetNo = (ArrayList) assetHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetNo)) 
				{
					for (Object[] obj : arlAssetNo)
					{
						assetResponseVO = new AlertAssetDetailsVO();
						assetResponseVO.setModel(RMDCommonUtility.convertObjectToString(obj[4]));
						assetResponseVO.setFleet(RMDCommonUtility.convertObjectToString(obj[5]));
						assetResponseVO.setAssetNumber(RMDCommonUtility.convertObjectToString(obj[3]));
						assetResponseVO.setCustomerId(RMDCommonUtility.convertObjectToString(obj[1]));
						assetList.add(assetResponseVO);
					}
				}
			}
		} 
		catch (RMDDAOConnectionException ex) 
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ALERT_HISTORY_POPULATE_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) 
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ALERT_HISTORY_POPULATE_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							assetVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		finally 
		{
			releaseSession(hibernateSession);
		}
		return assetList;
	}
	
	@Override
	public String getSubMenuList(String userId) throws RMDDAOException 
	{
		Session session = null;
		StringBuilder countryStateQuery = null;
		String subMenuDetails = RMDCommonConstants.EMPTY_STRING;
		try{
			countryStateQuery= new StringBuilder();
			session = getHibernateSession();
			countryStateQuery.append(" SELECT B.PRIVILEGE_NAME FROM ( ");
			countryStateQuery.append(" SELECT * FROM GET_USR.GET_USR_USER_ROLES USRROL LEFT OUTER JOIN GET_USR.GET_USR_USERS USR ");
			countryStateQuery.append(" ON USRROL.LINK_USERS=USR.GET_USR_USERS_SEQ_ID LEFT OUTER JOIN GET_USR.GET_USR_ROLES ROL ");
			countryStateQuery.append(" ON USRROL.LINK_ROLES=ROL.GET_USR_ROLES_SEQ_ID LEFT OUTER JOIN GET_USR_USER_PREFERENCE PREF ");
			countryStateQuery.append(" ON PREF.LINK_USERS=USR.GET_USR_USERS_SEQ_ID WHERE USR.user_id =:userId AND PREF.USER_PREFERENCE_TYPE='Role' AND PREF.USER_PREFERENCE_VALUE = ROL.GET_USR_ROLES_SEQ_ID) A, ");
			countryStateQuery.append(" (SELECT * FROM GET_USR.GET_USR_ROLE_PRIVILEGES ROLPRIV LEFT OUTER JOIN GET_USR.GET_USR_ROLES ROL ON ROLPRIV.LINK_ROLES=ROL.GET_USR_ROLES_SEQ_ID ");
			countryStateQuery.append(" LEFT OUTER JOIN GET_USR.GET_USR_PRIVILEGE PRIV ON ROLPRIV.LINK_PRIVILEGES=PRIV.GET_USR_PRIVILEGE_SEQ_ID WHERE PRIV.RESOURCE_TYPE='submenu') B ");
			countryStateQuery.append(" WHERE A.GET_USR_ROLES_SEQ_ID=B.GET_USR_ROLES_SEQ_ID  ");
			
			final Query stateHQuery = session.createSQLQuery(countryStateQuery.toString());
			stateHQuery.setParameter(RMDCommonConstants.USERID, userId);
			stateHQuery.setFetchSize(500);
			List<String> componentList = stateHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(componentList)) 
			{
				Iterator<String> it = componentList.iterator();
				subMenuDetails=StringUtils.join(it,RMDCommonConstants.COMMMA_SEPARATOR);
			}
		}
		catch (RMDDAOConnectionException ex)
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBMENU_LIST);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		}
		catch (Exception e)
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBMENU_LIST);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		finally 
		{
			releaseSession(session);
			countryStateQuery = null;
		}
		return subMenuDetails;
	}
	@Override
	public List<AlertSubscriberDetailsVO> getSubscribersList(AlertSubscriberDetailsVO subscriberVO)throws RMDDAOException 
	{
		
		List<AlertSubscriberDetailsVO> subscriberList = new ArrayList<AlertSubscriberDetailsVO>();
		Session hibernateSession = null;
		final StringBuilder rxQry = new StringBuilder();
		final StringBuilder vehcileDetailsQry = new StringBuilder();
		final StringBuilder subscriberQryUnion = new StringBuilder();
		final StringBuilder proximityQry = new StringBuilder();
		hibernateSession = getHibernateSession();
		List<Object[]> arlSubscriberNo = null;
		List<Object[]> arlVehicleNo = null;
		List<Object[]> arlSubscriberUnionNo = null;
		AlertSubscriberDetailsVO subscriberResponseVO = null;
		String locoImpact=null;
		String rxTitle= null;
		String subsystem= null;
		String vehcileObjid= null;
		String modelName= null;
		String fleetNumber= null;
		String orgId= null;
		String proximityDesc= null;
		String originalId= null;
		String urgency= null;
	
		try 
		{
			 
		   if((!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType())) && 
				    subscriberVO.getAlertType().equalsIgnoreCase("RX")){ 
		    rxQry.append("  select RECOM.LOCO_IMPACT, RECOM.TITLE, LKP.LOOK_VALUE,RECOM.URGENCY ");
		    rxQry.append("  FROM GETS_SD_RECOM RECOM,GETS_SD_RECOM_SUBSYS SUBSYS, GETS_RMD_LOOKUP LKP ");
		    rxQry.append("  WHERE RECOM.OBJID=SUBSYS.RECOM_SUBSYS2RECOM ");
		    rxQry.append("  AND SUBSYS.SUBSYSTEM=LKP.OBJID(+) ");
			rxQry.append("  AND LKP.LIST_NAME(+)='SUBSYSTEM' ");
			rxQry.append( " AND RECOM.OBJID=:alertId and rownum<=1");
			
			
			if (null != hibernateSession)
			{
				Query subscriberHqry = hibernateSession.createSQLQuery(rxQry.toString());
				
				if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertId())) 
				{
					subscriberHqry.setParameter(RMDCommonConstants.ALERT_ID,subscriberVO.getAlertId());
				}
				
				
				arlSubscriberNo = (ArrayList) subscriberHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(arlSubscriberNo)) 
				{
					for (Object[] obj : arlSubscriberNo)
					{
						locoImpact= RMDCommonUtility.convertObjectToString(obj[0]);
						rxTitle= RMDCommonUtility.convertObjectToString(obj[1]);
						subsystem= RMDCommonUtility.convertObjectToString(obj[2]);
						urgency= RMDCommonUtility.convertObjectToString(obj[3]);
						
					}
				}
			}
		}
		 
		//Getting the vehicle details and org_id for the vehcileObjid
		   
		   


		  
		    vehcileDetailsQry.append("SELECT V.VEHICLE_OBJID, V.ORG_ID, V.MODEL_NAME_V, V.FLEET_NUMBER_V , ");
		    vehcileDetailsQry.append("  (select PD.PROXIMITY_DESC from GETS_RMD_EOA_PROXIMITY_DEF PD where V.BUS_ORG_OBJID=PD.PROXIMITY2BUS_ORG(+)");
		    vehcileDetailsQry.append("  and PD.PROXIMITY_LABEL(+) = 'G' ");
		    vehcileDetailsQry.append("  AND (:latitude BETWEEN LEAST(PD.UPPER_LEFT_LAT,PD.LOWER_RIGHT_LAT) AND GREATEST(PD.UPPER_LEFT_LAT,PD.LOWER_RIGHT_LAT))");
		    vehcileDetailsQry.append("  AND (:longitude between LEAST(PD.UPPER_LEFT_LON,PD.LOWER_RIGHT_LON) AND GREATEST(PD.UPPER_LEFT_LON,PD.LOWER_RIGHT_LON))");
		    vehcileDetailsQry.append("    and rownum <= 1 ) PROXIMITY_DESC");
		    vehcileDetailsQry.append(" 	FROM GETS_RMD_CUST_RNH_RN_V V ");
	
		    vehcileDetailsQry.append("  WHERE V.VEHICLE_HDR = :vehicleHeader");
		    vehcileDetailsQry.append("  AND V.VEHICLE_NO= :vehicleNo");
		    vehcileDetailsQry.append("  AND rownum<=1");
         
			
         
			if (null != hibernateSession)
			{ 
				Query vehcileDetailsHqry = hibernateSession.createSQLQuery(vehcileDetailsQry.toString());
              
				if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getVehicleHeader())) 
				{
					vehcileDetailsHqry.setParameter(RMDCommonConstants.VEHICLE_HEADER,subscriberVO.getVehicleHeader());
				}
				if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getVehicleNumber())) 
				{
					vehcileDetailsHqry.setParameter(RMDCommonConstants.VEHICLE_NO,subscriberVO.getVehicleNumber());
				}
				
				if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getLatitude())) 
				{
					vehcileDetailsHqry.setParameter(RMDCommonConstants.LATTITUDE,subscriberVO.getLatitude());
					
				}
			 
				else
					vehcileDetailsHqry.setParameter(RMDCommonConstants.LATTITUDE,null,Hibernate.INTEGER);
				
				if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getLongitude())) 
				{
					vehcileDetailsHqry.setParameter(RMDCommonConstants.LONGITUDE,subscriberVO.getLongitude());
					
				} 
				else
					vehcileDetailsHqry.setParameter(RMDCommonConstants.LONGITUDE,null,Hibernate.INTEGER);
				
				
				arlVehicleNo = (ArrayList) vehcileDetailsHqry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(arlVehicleNo)) 
				{
					for (Object[] obj : arlVehicleNo)
					{ 
						vehcileObjid= RMDCommonUtility.convertObjectToString(obj[0]);
						orgId= RMDCommonUtility.convertObjectToString(obj[1]);
						modelName= RMDCommonUtility.convertObjectToString(obj[2]);
						fleetNumber= RMDCommonUtility.convertObjectToString(obj[3]);
						proximityDesc =  RMDCommonUtility.convertObjectToString(obj[4]);
						
					}
				} 
				  //Query to get subscriber info
				
				 if((!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType())) && 
						    subscriberVO.getAlertType().equalsIgnoreCase(RMDCommonConstants.RX))
				{ 
						subscriberQryUnion.append("with view_data AS ( ");
						subscriberQryUnion.append("  select user_id,user_email,active_flag,email_attachment_format_cd,");
						subscriberQryUnion.append("  org_id, model_name,model_objid,serial_no,");
						subscriberQryUnion.append("  fleet_number, region,sub_region,proximity_desc,subscribed_alerts,rxalert_value, ");
						subscriberQryUnion.append("  user_phone_number, notification_type, timezone_cd, measurement_system");
						subscriberQryUnion.append("  FROM gets_rmd.GETS_RMD_SUB_ALERT_V_CPY");
						subscriberQryUnion.append("  WHERE active_flag = 'Y' AND notify_event_type ='Rx' ) ");
						subscriberQryUnion.append("  SELECT user_id,user_email,active_flag,email_attachment_format_cd,");
						subscriberQryUnion.append("  serial_no,NULL region, NULL sub_region,NULL fleet_number,NULL model_name,");
						subscriberQryUnion.append("  NULL proximity_desc,  NULL org_id, user_phone_number,notification_type,timezone_cd, measurement_system");
						subscriberQryUnion.append("  FROM view_data WHERE serial_no = :vehicleNo AND org_id = :orgId");
						
						//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem' AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE= :rxTitle) )");
				
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("  select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,");
						subscriberQryUnion.append(" NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA  WHERE REGION = :region and SUB_REGION = :subRegion and ORG_ID = :orgId");
						
						//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem' AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE = :rxTitle) )");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE REGION = :region and SUB_REGION is null and ORG_ID  = :orgId ");
						
						//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem' AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE = :rxTitle) )");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,FLEET_NUMBER,NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE FLEET_NUMBER = :fleetNumber and ORG_ID = :orgId ");
						
						//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem' AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE = :rxTitle) )");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("	select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append("USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE PROXIMITY_DESC = :proximityDesc and ORG_ID = :orgId");
						
						//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem' AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE = :rxTitle) )");
						
						subscriberQryUnion.append("  UNION");
		                subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,NULL MODEL_NAME,NULL PROXIMITY_DESC,ORG_ID,");
		                subscriberQryUnion.append("  USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE ORG_ID = :orgId and  SERIAL_NO is null and REGION is null and SUB_REGION is null and FLEET_NUMBER is null and PROXIMITY_DESC is null");
		 
		            	//filter
						subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
						subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem'  AND RXALERT_VALUE = :subsystem)");
						subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE = :rxTitle) )");
						
						subscriberQryUnion.append("  UNION");
		                subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,");
		                subscriberQryUnion.append("  MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE MODEL_NAME = :modelName  and ORG_ID = :orgId");
		 	 
		               	//filter
		        		subscriberQryUnion.append(" AND ((SUBSCRIBED_ALERTS='Urgency' AND RXALERT_VALUE = :urgency)  ");
		        		subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Loco Impact' AND RXALERT_VALUE = :locoImpact)");
		        		subscriberQryUnion.append(" OR  (SUBSCRIBED_ALERTS='Subsystem'  AND RXALERT_VALUE = :subsystem)");
		        		subscriberQryUnion.append(" OR (SUBSCRIBED_ALERTS='Title' AND RXALERT_VALUE  = :rxTitle) )");
				
				 }
				 else if((!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType())) && 
						    subscriberVO.getAlertType().equalsIgnoreCase("ATS"))
				 {
					    subscriberQryUnion.append("with view_data AS ( ");
						subscriberQryUnion.append("  select user_id,user_email,active_flag,email_attachment_format_cd,");
						subscriberQryUnion.append("  org_id, model_name,model_objid,serial_no,");
						subscriberQryUnion.append("  fleet_number, region,sub_region,proximity_desc,subscribed_alerts,rxalert_value, ");
						subscriberQryUnion.append("  user_phone_number, notification_type, timezone_cd, measurement_system");
						subscriberQryUnion.append("  FROM gets_rmd.GETS_RMD_SUB_ALERT_V_CPY");
						subscriberQryUnion.append("  WHERE active_flag = 'Y' AND notify_event_type = :notificationId ) ");
						subscriberQryUnion.append("  SELECT user_id,user_email,active_flag,email_attachment_format_cd,");
						subscriberQryUnion.append("  serial_no,NULL region, NULL sub_region,NULL fleet_number,NULL model_name,");
						subscriberQryUnion.append("  NULL proximity_desc,  NULL org_id, user_phone_number,notification_type,timezone_cd, measurement_system");
						subscriberQryUnion.append("  FROM view_data WHERE serial_no = :vehicleNo AND org_id = :orgId");
				
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("  select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,");
						subscriberQryUnion.append(" NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA  WHERE REGION = :region and SUB_REGION = :subRegion and ORG_ID = :orgId");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE REGION = :region and SUB_REGION is null and ORG_ID  = :orgId ");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,FLEET_NUMBER,NULL MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE FLEET_NUMBER = :fleetNumber and ORG_ID = :orgId ");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("	select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER, NULL MODEL_NAME,PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append("USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE PROXIMITY_DESC = :proximityDesc and ORG_ID = :orgId");
				
						subscriberQryUnion.append("  UNION");
		                subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,NULL MODEL_NAME,NULL PROXIMITY_DESC,ORG_ID,");
		                subscriberQryUnion.append("  USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE ORG_ID = :orgId and  SERIAL_NO is null and REGION is null and SUB_REGION is null and FLEET_NUMBER is null and PROXIMITY_DESC is null");
		 
						subscriberQryUnion.append("  UNION");
		                subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,");
		                subscriberQryUnion.append("  MODEL_NAME,NULL PROXIMITY_DESC,NULL ORG_ID,USER_PHONE_NUMBER,NOTIFICATION_TYPE,timezone_cd, measurement_system from VIEW_DATA WHERE MODEL_NAME = :modelName  and ORG_ID = :orgId");
		 	 
				 }
				 else if((!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType())) && 
						    subscriberVO.getAlertType().equalsIgnoreCase("CONFIGURABLE"))
				 {

						subscriberQryUnion.append("with view_data AS ( ");
						subscriberQryUnion.append("  SELECT USER_ID,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,ORG_ID, MODEL_OBJID,SERIAL_NO, FLEET_NUMBER, REGION, ");
						subscriberQryUnion.append("  SUB_REGION,PROXIMITY_DESC,SUBSCRIBED_ALERTS,RXALERT_VALUE,USER_PHONE_NUMBER,NOTIFICATION_TYPE ");
						subscriberQryUnion.append("  FROM GETS_RMD.GETS_RMD_SUBSCRIPTION_ALERT_V where ACTIVE_FLAG = 'Y' AND ORIGINAL_ID :originalId)");
						subscriberQryUnion.append("  select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,SERIAL_NO,NULL REGION");
						subscriberQryUnion.append("  ,NULL SUB_REGION,NULL FLEET_NUMBER,NULL PROXIMITY_DESC,NULL ORG_ID,USER_PHONE_NUMBER,NOTIFICATION_TYPE");
						subscriberQryUnion.append("  from VIEW_DATA WHERE SERIAL_NO :vehicleNo and ORG_ID :orgId   ");
					
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("  select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,");
						subscriberQryUnion.append(" NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE from VIEW_DATA  WHERE REGION :region and SUB_REGION :subRegion and ORG_ID :orgId");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,REGION,SUB_REGION,NULL FLEET_NUMBER,NULL PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE from VIEW_DATA WHERE REGION :region and SUB_REGION is null and ORG_ID :orgId ");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,FLEET_NUMBER,NULL PROXIMITY_DESC,NULL ORG_ID,");
						subscriberQryUnion.append(" USER_PHONE_NUMBER,NOTIFICATION_TYPE from VIEW_DATA WHERE FLEET_NUMBER :fleetNumber and ORG_ID :orgId ");
						
						subscriberQryUnion.append("  UNION");
						subscriberQryUnion.append("	select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,PROXIMITY_DESC,NULL ORG_ID,"); 
						subscriberQryUnion.append("USER_PHONE_NUMBER,NOTIFICATION_TYPE from VIEW_DATA WHERE PROXIMITY_DESC :proximityDesc and ORG_ID :orgId ");
						
						subscriberQryUnion.append("  UNION");
		                subscriberQryUnion.append(" select user_id,user_email,ACTIVE_FLAG,EMAIL_ATTACHMENT_FORMAT_CD,NULL SERIAL_NO,NULL REGION,NULL SUB_REGION,NULL FLEET_NUMBER,NULL PROXIMITY_DESC,ORG_ID,");
		                subscriberQryUnion.append("  USER_PHONE_NUMBER,NOTIFICATION_TYPE from VIEW_DATA WHERE ORG_ID :orgId and  SERIAL_NO is null and REGION is null and SUB_REGION is null and FLEET_NUMBER is null and PROXIMITY_DESC is null");
		 	 
				 
				 }
				
				 Query subscriberQryUnionHqry = hibernateSession.createSQLQuery(subscriberQryUnion.toString());
				   
					if (!RMDCommonUtility.isNullOrEmpty(orgId)) 
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.ORG_ID,orgId);
					}
					
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.PROXIMITYDESC,proximityDesc);
					
					if (!RMDCommonUtility.isNullOrEmpty(originalId) && (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType()) && 
				    subscriberVO.getAlertType().equalsIgnoreCase(RMDCommonConstants.CONFIGURABLE))) 
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.ORIGINAL_ID,originalId);
					}
					if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertId()) && (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getAlertType()) && 
						    subscriberVO.getAlertType().equalsIgnoreCase(RMDCommonConstants.ATS))) 
				    {
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.ALERT_ID,subscriberVO.getAlertId());
					}
					if (!RMDCommonUtility.isNullOrEmpty(fleetNumber)) 
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.FLEETNUMBER,fleetNumber);
					}
					if (!RMDCommonUtility.isNullOrEmpty(modelName)) 
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.MODEL_NAME,modelName);
					}
					if (!RMDCommonUtility.isNullOrEmpty(subscriberVO.getVehicleNumber())) 
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.VEHICLE_NO,subscriberVO.getVehicleNumber());
					}
					 
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.REGION,subscriberVO.getRegion());
					    subscriberQryUnionHqry.setParameter(RMDCommonConstants.SUB_REGION,subscriberVO.getSubRegion());
					
					if (subscriberVO.getAlertType().equalsIgnoreCase(RMDCommonConstants.RX) && !RMDCommonUtility.isNullOrEmpty(rxTitle))
					{ 
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.RX_TITLE,rxTitle);
					}
					
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.SUB_SYSTEM,subsystem);
					    subscriberQryUnionHqry.setParameter(RMDCommonConstants.LOCOMOTIVE,locoImpact);
					
				 
					if (!RMDCommonUtility.isNullOrEmpty(urgency))
					{
						subscriberQryUnionHqry.setParameter(RMDCommonConstants.URGENCY,urgency);
					} 
				
					arlSubscriberUnionNo = (ArrayList) subscriberQryUnionHqry.list();
				
					if (RMDCommonUtility.isCollectionNotEmpty(arlSubscriberUnionNo)) 
					{
						for (Object[] obj : arlSubscriberUnionNo)
						{ 
							subscriberResponseVO = new AlertSubscriberDetailsVO();
						
						 	subscriberResponseVO.setSso(RMDCommonUtility.convertObjectToString(obj[0]));
							subscriberResponseVO.setEmailId(RMDCommonUtility.convertObjectToString(obj[1]));
							subscriberResponseVO.setNotificationType(RMDCommonUtility.convertObjectToString(obj[12]));
							subscriberResponseVO.setPhoneNumber(RMDCommonUtility.convertObjectToString(obj[11]));
							subscriberResponseVO.setMeasurementSystem(RMDCommonUtility.convertObjectToString(obj[14]));
							subscriberResponseVO.setUserTimezone(RMDCommonUtility.convertObjectToString(obj[13]));
							subscriberList.add(subscriberResponseVO);
							
						}
					}
					 
				 }	 
	}
		catch (RMDDAOConnectionException ex) 
		{
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ALERT_HISTORY_POPULATE_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							subscriberVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) 
		{
			e.printStackTrace();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ALERT_HISTORY_POPULATE_DATA);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							subscriberVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		finally 
		{
			arlVehicleNo= null;
			arlSubscriberNo= null;
			releaseSession(hibernateSession);
		}
		return subscriberList;
	}

}
