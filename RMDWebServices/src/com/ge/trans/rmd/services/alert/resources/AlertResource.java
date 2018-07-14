package com.ge.trans.rmd.services.alert.resources;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.ge.trans.eoa.services.security.service.valueobjects.UpdatePhoneVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.UserRequestType;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.alert.valueobjects.AlertActivateDeleteAlertRequestType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertAssetRequestType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertAssetResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertHistoryRequestType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertHistoryResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertHstDetailsResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertMultiUsersResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertSubResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertSubscriberRequestType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertSubscriberResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertSubscriptionRequestType;
import com.ge.trans.rmd.services.alert.valueobjects.AlertSubscriptionResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.ModelsResponseType;
import com.ge.trans.rmd.services.alert.valueobjects.RestrictedAlertShopRespType;
import com.ge.trans.rmd.services.alert.valueobjects.ShopsResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.Customer;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.ALERT_SERVICE)
@Component
public class AlertResource extends BaseResource {
	public static final RMDLogger RMDLOGGER = RMDLoggerHelper
			.getLogger(AlertResource.class);

	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;
	@Autowired
	private AlertServiceIntf objAlertServiceIntf;

	/**
	 * This method is used for Activation or Deactivation Of Alerts for the
	 * parameters passed
	 * 
	 * @return Status of Updation in Table
	 * @throws RMDServiceException
	 * @Description To update Records in for Activation or Deactivation Of
	 *              Alerts
	 */

	@POST
	@Path(OMDConstants.ACT_DEACT_ALERT)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String activateOrDeactivateAlert(
			final AlertActivateDeleteAlertRequestType objAlertRowList)
			throws RMDServiceException {
		String pass = OMDConstants.FAIL;
		try {
			List<AlertSubscriptionRequestType> objAlertRowReqType;
			AlertSubscriptionDetailsVO ppAlertSubscriptionDetailsVO;
			List<AlertSubscriptionDetailsVO> rowList = new ArrayList<AlertSubscriptionDetailsVO>();
			if (objAlertRowList.getListAlertSubscriptionDetails() != null
					&& !objAlertRowList.getListAlertSubscriptionDetails()
							.isEmpty()) {
				objAlertRowReqType = objAlertRowList
						.getListAlertSubscriptionDetails();
				for (AlertSubscriptionRequestType rows : objAlertRowReqType) {
					ppAlertSubscriptionDetailsVO = new AlertSubscriptionDetailsVO();
					ppAlertSubscriptionDetailsVO
							.setCustomerAlertObjId(BeanUtility
									.stripXSSCharacters(rows
											.getCustomerAlertObjId()));
					if (rows.getFleetid() != null
							&& !rows.getFleetid().isEmpty()) {
						ppAlertSubscriptionDetailsVO.setFleetid(BeanUtility
								.stripXSSCharacters(rows.getFleetid()));
					} else if (rows.getRegionid() != null
							&& !rows.getRegionid().isEmpty()) {
						ppAlertSubscriptionDetailsVO.setRegionid(BeanUtility
								.stripXSSCharacters(rows.getRegionid()));
					} else if (rows.getVehicleObjId() != null
							&& !rows.getVehicleObjId().isEmpty()) {
						ppAlertSubscriptionDetailsVO
								.setVehicleObjId(BeanUtility
										.stripXSSCharacters(rows
												.getVehicleObjId()));
					} else if (rows.getShopid() != null
							&& !rows.getShopid().isEmpty()) {
						ppAlertSubscriptionDetailsVO
								.setShopid(rows.getShopid());
					} else if (null != rows.getModelId()
							&& !rows.getModelId().isEmpty()) {
						ppAlertSubscriptionDetailsVO.setModelId(rows
								.getModelId());
					}
					ppAlertSubscriptionDetailsVO.setStatus(BeanUtility
							.stripXSSCharacters(rows.getStatus()));
					ppAlertSubscriptionDetailsVO.setUserid(BeanUtility
							.stripXSSCharacters(rows.getUserid()));
					rowList.add(ppAlertSubscriptionDetailsVO);
				}
			}
			pass = objAlertServiceIntf.activateOrDeactivateAlert(rowList);

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return pass;
	}

	/**
	 * This method is used for updation of Records for Delivery Format Of Alerts
	 * for the parameters passed
	 * 
	 * @return Status of Updation in Table
	 * @throws RMDServiceException
	 * @Description To update Records for Delivery Format Of Alerts
	 */

	@POST
	@Path(OMDConstants.EDIT_DLVRY_FRMT)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String editAlert(final AlertSubscriptionRequestType objEdit)
			throws RMDServiceException {
		String pass = OMDConstants.FAIL;
		try {
			AlertSubscriptionDetailsVO editVal = new AlertSubscriptionDetailsVO();
			if (objEdit.getFleetid() != null && !objEdit.getFleetid().isEmpty()) {
				editVal.setFleetid(BeanUtility.stripXSSCharacters(objEdit
						.getFleetid()));
			}
			if (objEdit.getRegionid() != null
					&& !objEdit.getRegionid().isEmpty()) {
				editVal.setRegionid(BeanUtility.stripXSSCharacters(objEdit
						.getRegionid()));
			}
			if (objEdit.getVehicleObjId() != null
					&& !objEdit.getVehicleObjId().isEmpty()) {
				editVal.setVehicleObjId(BeanUtility.stripXSSCharacters(objEdit
						.getVehicleObjId()));
			}
			if (objEdit.getShopid() != null && !objEdit.getShopid().isEmpty()) {
				editVal.setShopid(objEdit.getShopid());
			}
			if (objEdit.getCustomerAlertObjId() != null
					&& !objEdit.getCustomerAlertObjId().isEmpty()) {
				editVal.setCustomerAlertObjId(BeanUtility
						.stripXSSCharacters(objEdit.getCustomerAlertObjId()));
			}
			if (objEdit.getEmailFormat() != null
					&& !objEdit.getEmailFormat().isEmpty()) {
				editVal.setEmailFormat(BeanUtility.stripXSSCharacters(objEdit
						.getEmailFormat()));
			}
			if (null != objEdit.getModelId() && !objEdit.getModelId().isEmpty()) {
				editVal.setModelId(objEdit.getModelId());
			}
			if (null != objEdit.getNotificationType()
					&& !objEdit.getNotificationType().isEmpty()) {
				editVal.setNotificationType(objEdit.getNotificationType());
			}
			if (null != objEdit.getRxType()
					&& !objEdit.getRxType().isEmpty()) {
				editVal.setRxType(objEdit.getRxType());
			}
			editVal.setUserid(BeanUtility.stripXSSCharacters(objEdit
					.getUserid()));
			pass = objAlertServiceIntf.editAlert(editVal);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return pass;
	}

	/**
	 * This method is used for deletion of Records for Alert subscribed for the
	 * parameters passed
	 * 
	 * @return Status of Updation in Table
	 * @throws RMDServiceException
	 * @Description To Delete Records for Alert subscribed
	 */

	@POST
	@Path(OMDConstants.DELETE_ALERT)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String deleteAlert(
			final AlertActivateDeleteAlertRequestType objAlertRowList)
			throws RMDServiceException {
		String pass = OMDConstants.FAIL;

		try {
			List<AlertSubscriptionRequestType> objAlertRowReqType = objAlertRowList
					.getListAlertSubscriptionDetails();
			AlertSubscriptionDetailsVO ppAlertSubscriptionDetailsVO;
			List<AlertSubscriptionDetailsVO> rowList = new ArrayList<AlertSubscriptionDetailsVO>();

			for (AlertSubscriptionRequestType rows : objAlertRowReqType) {
				ppAlertSubscriptionDetailsVO = new AlertSubscriptionDetailsVO();
				ppAlertSubscriptionDetailsVO.setAlertConfSeqId(BeanUtility
						.stripXSSCharacters(rows.getAlertConfSeqId()));
				ppAlertSubscriptionDetailsVO.setCustomerAlertObjId(BeanUtility
						.stripXSSCharacters(rows.getCustomerAlertObjId()));
				if (rows.getFleetid() != null && !rows.getFleetid().isEmpty()) {
					ppAlertSubscriptionDetailsVO.setFleetid(BeanUtility
							.stripXSSCharacters(rows.getFleetid()));
				} else if (rows.getRegionid() != null
						&& !rows.getRegionid().isEmpty()) {
					ppAlertSubscriptionDetailsVO.setRegionid(BeanUtility
							.stripXSSCharacters(rows.getRegionid()));
				} else if (rows.getVehicleObjId() != null
						&& !rows.getVehicleObjId().isEmpty()) {
					ppAlertSubscriptionDetailsVO.setVehicleObjId(BeanUtility
							.stripXSSCharacters(rows.getVehicleObjId()));
				} else if (rows.getShopid() != null
						&& !rows.getShopid().isEmpty()) {
					ppAlertSubscriptionDetailsVO.setShopid(rows.getShopid());
				} else if (null != rows.getModelId()
						&& !rows.getModelId().isEmpty()) {
					ppAlertSubscriptionDetailsVO.setModelId(rows.getModelId());
				}
				ppAlertSubscriptionDetailsVO.setUserid(BeanUtility
						.stripXSSCharacters(rows.getUserid()));
				rowList.add(ppAlertSubscriptionDetailsVO);
			}

			pass = objAlertServiceIntf.deleteAlert(rowList);
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return pass;
	}

	@POST
	@Path(OMDConstants.UPDATE_PHONE)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updatephone(final UserRequestType userrequesttype)
			throws RMDServiceException {
		String pass = OMDConstants.FAIL;
		List<UpdatePhoneVO> updatePhoneVO = null;
		List<UserRequestType> userRequestType = userrequesttype
				.getarrUserRequestType();
		updatePhoneVO = new ArrayList<UpdatePhoneVO>(userRequestType.size());
		try {
			for (UserRequestType objItem : userRequestType) {
				UpdatePhoneVO objDeleteRolesUpdateVO = new UpdatePhoneVO();
				if (!RMDCommonUtility.isNullOrEmpty(objItem.getUserId())) {
					objDeleteRolesUpdateVO.setUserId(objItem.getUserId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objItem.getUserPhoneNo())) {
					objDeleteRolesUpdateVO.setUserPhoneNo(objItem
							.getUserPhoneNo());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objItem
						.getUserPhoneCountryCode())) {
					objDeleteRolesUpdateVO.setUserPhoneCountryCode(objItem
							.getUserPhoneCountryCode());
				}
				updatePhoneVO.add(objDeleteRolesUpdateVO);
			}

			pass = objAlertServiceIntf.updatephone(updatePhoneVO);
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return pass;
	}

	@POST
	@Path(OMDConstants.ADD_ALERT_SUBSCRIPTION_DATA)
	@Consumes(MediaType.APPLICATION_XML)
	public String addAlertSubscriptionData(
			AlertSubscriptionRequestType alertSubscriptionDtls)
			throws RMDServiceException {
		String returnStr = null;
		AlertSubscriptionDetailsVO ppalertSubscriptionDetails = null;
		try {
			ppalertSubscriptionDetails = new AlertSubscriptionDetailsVO();

			if (alertSubscriptionDtls.getAlertSubscribed() != null
					&& !alertSubscriptionDtls.getAlertSubscribed().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setAlertSubscribed(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getAlertSubscribed()));
			}
			if (alertSubscriptionDtls.getService() != null
					&& !alertSubscriptionDtls.getService().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails
						.setService(BeanUtility
								.stripXSSCharacters(alertSubscriptionDtls
										.getService()));
			}
			if (alertSubscriptionDtls.getCustomerId() != null
					&& !alertSubscriptionDtls.getCustomerId().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setCustomerId(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getCustomerId()));
			}
			if (alertSubscriptionDtls.getAlertType() != null
					&& !alertSubscriptionDtls.getAlertType().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setAlertType(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getAlertType()));
			}
			if (alertSubscriptionDtls.getRegion() != null
					&& !alertSubscriptionDtls.getRegion().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setRegion(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls.getRegion()));
			}
			if (alertSubscriptionDtls.getFleet() != null
					&& !alertSubscriptionDtls.getFleet().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setFleet(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls.getFleet()));
			}
			if (alertSubscriptionDtls.getAsset() != null
					&& !alertSubscriptionDtls.getAsset().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setAsset(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls.getAsset()));
			}
			if (alertSubscriptionDtls.getEmailFormat() != null
					&& !alertSubscriptionDtls.getEmailFormat().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setEmailFormat(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getEmailFormat()));
			}
			if (alertSubscriptionDtls.getSubscriptionModel() != null
					&& !alertSubscriptionDtls.getSubscriptionModel().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setSubscriptionModel(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getSubscriptionModel()));
			}
			if (alertSubscriptionDtls.getStatus() != null
					&& !alertSubscriptionDtls.getStatus().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setStatus(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls.getStatus()));
			}
			if (alertSubscriptionDtls.getUserid() != null
					&& !alertSubscriptionDtls.getUserid().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setUserid(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls.getUserid()));
			}
			if (alertSubscriptionDtls.getUserSeqId() != null
					&& !alertSubscriptionDtls.getUserSeqId().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setUserSeqId(BeanUtility
						.stripXSSCharacters(alertSubscriptionDtls
								.getUserSeqId()));
			}
			if (alertSubscriptionDtls.getSubDivision() != null
					&& !alertSubscriptionDtls.getSubDivision().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setSubDivision(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getSubDivision())));
			}
			if (alertSubscriptionDtls.getShop() != null
					&& !alertSubscriptionDtls.getShop().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setShop(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getShop())));
			}
			if (alertSubscriptionDtls.getRxSubscribedAlerts() != null
					&& !alertSubscriptionDtls.getRxSubscribedAlerts().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setRxSubscribedAlerts(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getRxSubscribedAlerts())));
			}
			if (alertSubscriptionDtls.getRxAlertValue() != null
					&& !alertSubscriptionDtls.getRxAlertValue().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setValue(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getRxAlertValue())));
			}
			if (null != alertSubscriptionDtls.getModel()
					&& !alertSubscriptionDtls.getModel().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setModel(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getModel())));
			}
			if (null != alertSubscriptionDtls.getMultiUsers()
					&& !alertSubscriptionDtls.getMultiUsers().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setMultiUsers(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getMultiUsers())));
			}
			if (null != alertSubscriptionDtls.getConfigAlertModelVal()
					&& !alertSubscriptionDtls.getConfigAlertModelVal().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setConfigAlertModelVal(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getConfigAlertModelVal())));
			}
			if (null != alertSubscriptionDtls.getNotificationType()
					&& !alertSubscriptionDtls.getNotificationType().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setNotificationType(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getNotificationType())));
			}
			if (null != alertSubscriptionDtls.getPhoneNumber()
					&& !alertSubscriptionDtls.getPhoneNumber().equals(
							OMDConstants.EMPTY_STRING)) {
				ppalertSubscriptionDetails.setPhoneNumber(EsapiUtil
						.stripXSSCharacters((String) (alertSubscriptionDtls
								.getPhoneNumber())));
			}
			ppalertSubscriptionDetails.setRxType(EsapiUtil
					.stripXSSCharacters((String) (alertSubscriptionDtls
							.getRxType())));
			ppalertSubscriptionDetails
					.setMultiSubscriptionPrivilege(alertSubscriptionDtls
							.isMultiSubscriptionPrivilege());
			returnStr = objAlertServiceIntf
					.addAlertSubscriptionData(ppalertSubscriptionDetails);

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		} finally {
			ppalertSubscriptionDetails = null;
		}
		return returnStr;
	}

	@POST
	@Path(OMDConstants.GET_ALERT_SUBSCRIPTION_DATA)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AlertSubscriptionResponseType> getAlertSubscriptionData(
			final AlertSubscriptionRequestType objAlertReqType)
			throws RMDServiceException {

		List<AlertSubscriptionDetailsVO> alertDetailsVO = new ArrayList<AlertSubscriptionDetailsVO>();
		AlertSubscriptionResponseType alertVO = null;
		AlertSubscriptionDetailsVO alertReqVO = new AlertSubscriptionDetailsVO();
		List<AlertSubscriptionResponseType> alertResponse = new ArrayList<AlertSubscriptionResponseType>();
		try {
			if (null != objAlertReqType.getUserid()
					&& !objAlertReqType.getUserid().isEmpty()) {
				alertReqVO.setUserid(BeanUtility
						.stripXSSCharacters(objAlertReqType.getUserid()));
			}
			if (null != objAlertReqType.getFlag()
					&& !objAlertReqType.getFlag().isEmpty()) {
				alertReqVO.setFlag(BeanUtility
						.stripXSSCharacters(objAlertReqType.getFlag()));
			}
			alertReqVO.setModelPrivilege(objAlertReqType.isModelPrivilege());
			alertReqVO.setConfigAlertPrivilege(objAlertReqType
					.isConfigAlertPrivilege());
			alertReqVO.setMultiSubscriptionPrivilege(objAlertReqType
					.isMultiSubscriptionPrivilege());
			alertReqVO.setAllCustomer(objAlertReqType.isAllCustomer());
			alertReqVO.setUserType(objAlertReqType.getUserType());
			alertReqVO.setCustomerList(objAlertReqType.getCustomerList());
			alertReqVO.setCustomerId(objAlertReqType.getCustomerId());

			alertDetailsVO = objAlertServiceIntf
					.getAlertSubscriptionData(alertReqVO);

			if (RMDCommonUtility.isCollectionNotEmpty(alertDetailsVO)) {
				for (AlertSubscriptionDetailsVO respObj : alertDetailsVO) {
					alertVO = new AlertSubscriptionResponseType();
					if (respObj.getAlertSubscribed() != null
							&& !respObj.getAlertSubscribed().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setAlertSubscribed(respObj.getAlertSubscribed());
					}
					if (respObj.getService() != null
							&& !respObj.getService().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setService(respObj.getService());
					}
					if (respObj.getCustomerId() != null
							&& !respObj.getCustomerId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setCustomerId(respObj.getCustomerId());
					}
					if (respObj.getAlertType() != null
							&& !respObj.getAlertType().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setAlertType(respObj.getAlertType());
					}
					if (respObj.getRegion() != null
							&& !respObj.getRegion().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRegion(respObj.getRegion());
					}
					if (respObj.getFleet() != null
							&& !respObj.getFleet().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setFleet(respObj.getFleet());
					}
					if (respObj.getAsset() != null
							&& !respObj.getAsset().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setAsset(respObj.getAsset());
					}
					if (respObj.getEmailFormat() != null
							&& !respObj.getEmailFormat().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setEmailFormat(respObj.getEmailFormat());
					}
					if (respObj.getSubscriptionModel() != null
							&& !respObj.getSubscriptionModel().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setSubscriptionModel(respObj
								.getSubscriptionModel());
					}
					if (respObj.getStatus() != null
							&& !respObj.getStatus().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setStatus(respObj.getStatus());
					}
					if (respObj.getUserid() != null
							&& !respObj.getUserid().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setUserid(respObj.getUserid());
					}
					if (respObj.getUserSeqId() != null
							&& !respObj.getUserSeqId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setUserSeqId(respObj.getUserSeqId());
					}
					if (respObj.getCustomerAlertObjId() != null
							&& !respObj.getCustomerAlertObjId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setCustomerAlertObjId(respObj
								.getCustomerAlertObjId());
					}
					if (respObj.getAlertConfSeqId() != null
							&& !respObj.getAlertConfSeqId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setAlertConfSeqId(respObj.getAlertConfSeqId());
					}
					if (respObj.getFleetid() != null
							&& !respObj.getFleetid().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setFleetid(respObj.getFleetid());
					}
					if (respObj.getRegionid() != null
							&& !respObj.getRegionid().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRegionid(respObj.getRegionid());
					}
					if (respObj.getVehicleObjId() != null
							&& !respObj.getVehicleObjId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setVehicleObjId(respObj.getVehicleObjId());
					}
					//
					if (respObj.getShopid() != null
							&& !respObj.getShopid().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setShopid(respObj.getShopid());
					}
					if (respObj.getShop() != null
							&& !respObj.getShop().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setShop(respObj.getShop());
					}
					if (respObj.getRxSubscribedAlerts() != null
							&& !respObj.getRxSubscribedAlerts().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRxSubscribedAlerts(respObj
								.getRxSubscribedAlerts());
					}
					if (respObj.getValue() != null
							&& !respObj.getValue().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setValue(respObj.getValue());
					}
					if (respObj.getSubDivision() != null
							&& !respObj.getSubDivision().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setSubDivision(respObj.getSubDivision());
					}
					if (respObj.getSubDivisionid() != null
							&& !respObj.getSubDivisionid().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setSubDivisionid(respObj.getSubDivisionid());
					}
					if (respObj.getRxAlertId() != null
							&& !respObj.getRxAlertId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRxAlertId(respObj.getRxAlertId());
					}
					// Model
					if (null != respObj.getModelId()
							&& !respObj.getModelId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setModelId(respObj.getModelId());
					}
					if (null != respObj.getModel()
							&& !respObj.getModel().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setModel(respObj.getModel());
					}
					//
					if (null != respObj.getRuleTitle()
							&& !respObj.getRuleTitle().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRuleTitle(respObj.getRuleTitle());
					}
					if (null != respObj.getActive()
							&& !respObj.getActive().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setActive(respObj.getActive());
					}
					if (null != respObj.getOriginalId()
							&& !respObj.getOriginalId().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setOriginalId(respObj.getOriginalId());
					}
					if (null != respObj.getRuleModel()
							&& !respObj.getRuleModel().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRuleModel(respObj.getRuleModel());
					}
					if (null != respObj.getFirstName()
							&& !respObj.getFirstName().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setFirstName(respObj.getFirstName());
					}
					if (null != respObj.getLastName()
							&& !respObj.getLastName().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setLastName(respObj.getLastName());
					}
					if (null != respObj.getUserEmail()
							&& !respObj.getUserEmail().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setUserEmail(respObj.getUserEmail());
					}
					if (null != respObj.getNotificationType()
							&& !respObj.getNotificationType().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setNotificationType(respObj
								.getNotificationType());
					}
					if (null != respObj.getPhoneNumber()
							&& !respObj.getPhoneNumber().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setPhoneNumber(respObj.getPhoneNumber());
					}
					if (null != respObj.getRxType()
							&& !respObj.getRxType().equals(
									OMDConstants.EMPTY_STRING)) {
						alertVO.setRxType(respObj.getRxType());
					}
					alertResponse.add(alertVO);
				}

			}

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return alertResponse;
	}

	@GET
	@Path(OMDConstants.GET_ALL_SHOPS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ShopsResponseType> getAllShops(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ShopsResponseType> shopResponseList = new ArrayList<ShopsResponseType>();
		ShopsResponseType respShop = null;
		List<ShopsVO> shopList = null;
		try {
			shopList = objAlertServiceIntf.getAllShops();
			if (null != shopList) {
				if (RMDCommonUtility.isCollectionNotEmpty(shopList)) {

					for (ShopsVO shopVo : shopList) {
						respShop = new ShopsResponseType();
						respShop.setShopId(shopVo.getShopId());
						respShop.setShopName(shopVo.getShopName());
						respShop.setCustomerId(shopVo.getCustomerId());
						shopResponseList.add(respShop);
					}
				}/*
				 * else { throw new
				 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
				 * ); }
				 */
			}/*
			 * else { throw new
			 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return shopResponseList;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:Map<String, ArrayList<ModelVO>>
	 * @throws:RMDWebException,Exception
	 * @Description: This method is used for fetching the All Model values.
	 */
	@GET
	@Path(OMDConstants.GET_ALL_MODELS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ModelsResponseType> getAllModels(@Context final UriInfo ui)
			throws RMDServiceException {
		List<ModelsResponseType> modelsResponseTypeList = new ArrayList<ModelsResponseType>();
		ModelsResponseType objModelsResponseType = null;
		List<ModelVO> modelList = null;
		try {
			modelList = objAlertServiceIntf.getAllModels();
			if (null != modelList) {
				if (RMDCommonUtility.isCollectionNotEmpty(modelList)) {
					for (ModelVO objModelVO : modelList) {
						objModelsResponseType = new ModelsResponseType();
						objModelsResponseType.setModelObjId(objModelVO
								.getModelObjId());
						objModelsResponseType.setModelName(objModelVO
								.getModelName());
						objModelsResponseType.setCustomerId(objModelVO
								.getCustomerId());
						objModelsResponseType.setModelFamily(objModelVO
								.getModelFamily());
						objModelsResponseType.setModelGeneral(objModelVO
                                .getModelGeneral());
						modelsResponseTypeList.add(objModelsResponseType);
					}
				} /*
				 * else { throw new OMDNoResultFoundException(
				 * OMDConstants.NORECORDFOUNDEXCEPTION); }
				 */
			} /*
			 * else { throw new OMDNoResultFoundException(
			 * OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return modelsResponseTypeList;
	}

	/*
	 * This method is used for retrieving Asset details for the pinpoint passed
	 * in UriInfo
	 * 
	 * @param ui
	 * 
	 * @return List of AssetResponseType
	 * 
	 * @throws RMDServiceException
	 */
	@POST
	@Path(OMDConstants.GET_ASSET_FOR_CUSTOMER)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AlertAssetResponseType> getAssetForCustomer(
			final AlertAssetRequestType objAssetReqType)
			throws RMDServiceException {

		List<AlertAssetResponseType> arlAssetResponseType = new ArrayList<AlertAssetResponseType>();
		List<AlertAssetDetailsVO> arlAssetResponseVO = null;
		AlertAssetDetailsVO assetVO = new AlertAssetDetailsVO();
		AlertAssetResponseType assetResponse = null;
		try {

			if (validatePPAssetStatus(objAssetReqType)) {
				if (null != objAssetReqType.getAssetGroupName()
						&& !objAssetReqType.getAssetGroupName().isEmpty()) {
					assetVO.setAssetGrpName(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetGroupName()));
				} else {
					assetVO.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {
					assetVO.setCustomerId(BeanUtility
							.stripXSSCharacters(objAssetReqType.getCustomerId()));
				} else {
					assetVO.setCustomerId(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getAssetNumber()
						&& !objAssetReqType.getAssetNumber().isEmpty()) {
					assetVO.setAssetNumber(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetNumber()));
				} else {
					assetVO.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getStrFleet()
						&& !objAssetReqType.getStrFleet().isEmpty()) {
					assetVO.setFleet(BeanUtility
							.stripXSSCharacters(objAssetReqType.getStrFleet()));
				} else {
					assetVO.setFleet(RMDCommonConstants.EMPTY_STRING);
				}
				arlAssetResponseVO = objAlertServiceIntf
						.getAssetForCustomer(assetVO);
				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetResponseVO)) {
					for (AlertAssetDetailsVO tempVO : arlAssetResponseVO) {
						assetResponse = new AlertAssetResponseType();
						assetResponse.setAssetNumber(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getAssetNumber())));
						assetResponse.setVehicleObjId(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getVehicleObjId())));
						arlAssetResponseType.add(assetResponse);
					}
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlAssetResponseType;
	}

	@POST
	@Path(OMDConstants.GET_ALERT_FOR_CUSTOMER)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AlertSubResponseType getAlertForCustomer(
			final AlertAssetRequestType objAssetReqType)
			throws RMDServiceException {

		AlertDetailsVO alertDetailsVO = null;
		AlertAssetDetailsVO assetVO = new AlertAssetDetailsVO();
		AlertSubResponseType alertResponse = new AlertSubResponseType();
		try {

			if (validatePPAssetStatus(objAssetReqType)) {
				if (null != objAssetReqType.getAssetGroupName()
						&& !objAssetReqType.getAssetGroupName().isEmpty()) {
					assetVO.setAssetGrpName(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetGroupName()));
				} else {
					assetVO.setAssetGrpName(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {
					assetVO.setCustomerId(BeanUtility
							.stripXSSCharacters(objAssetReqType.getCustomerId()));
				} else {
					assetVO.setCustomerId(RMDCommonConstants.EMPTY_STRING);
				}
				if (null != objAssetReqType.getAssetNumber()
						&& !objAssetReqType.getAssetNumber().isEmpty()) {
					assetVO.setAssetNumber(BeanUtility
							.stripXSSCharacters(objAssetReqType
									.getAssetNumber()));
				} else {
					assetVO.setAssetNumber(RMDCommonConstants.EMPTY_STRING);
				}

				alertDetailsVO = objAlertServiceIntf
						.getAlertForCustomer(assetVO);
				if (RMDCommonUtility.isCollectionNotEmpty(alertDetailsVO
						.getAlertNotifyTypeList())) {
					alertResponse.setAlertNotifyType(alertDetailsVO
							.getAlertNotifyTypeList());
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return alertResponse;
	}

	public static boolean validatePPAssetStatus(
			final AlertAssetRequestType objAssetReqType) {

		if (!RMDCommonUtility.isNullOrEmpty(objAssetReqType.getAssetNumber())
				&& !AppSecUtil.checkAlphaNumericUnderscore(objAssetReqType
						.getAssetNumber())) {
			return false;
		}

		if (!RMDCommonUtility.isNullOrEmpty(objAssetReqType.getCustomerId())
				&& !AppSecUtil.checkAlphaNumericComma(BeanUtility
						.stripXSSCharacters(objAssetReqType.getCustomerId()))) {
			return false;
		}

		if (!RMDCommonUtility
				.isNullOrEmpty(objAssetReqType.getAssetGroupName())
				&& !AppSecUtil
						.checkAlphabets(BeanUtility
								.stripXSSCharacters(objAssetReqType
										.getAssetGroupName()))) {
			return false;
		}
		return true;
	}

	@GET
	@Path(OMDConstants.GET_MULTI_USERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AlertMultiUsersResponseType> getMultiUsers(
			@Context final UriInfo ui) throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		List<AlertMultiUsersVO> objPPAssetStatusHistoryVO = null;
		AlertMultiUsersResponseType objPpAssetStatusHstryResponse = null;
		List<AlertMultiUsersResponseType> aurizon = null;
		String customerId = null;
		String userType = null;
		try {
			queryParams = ui.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				}
				if (queryParams.containsKey(OMDConstants.USERTYPE)) {
					userType = queryParams.getFirst(OMDConstants.USERTYPE);
				}
			}
			objPPAssetStatusHistoryVO = objAlertServiceIntf.getMultiUsers(
					customerId, userType);

			if (null != objPPAssetStatusHistoryVO) {
				aurizon = new ArrayList<AlertMultiUsersResponseType>(
						objPPAssetStatusHistoryVO.size());
				for (AlertMultiUsersVO pPAssetStatusHistoryVO : objPPAssetStatusHistoryVO) {
					objPpAssetStatusHstryResponse = new AlertMultiUsersResponseType();
					objPpAssetStatusHstryResponse
							.setUserId(pPAssetStatusHistoryVO.getUserId());
					objPpAssetStatusHstryResponse
							.setFirstName(pPAssetStatusHistoryVO.getFirstName());
					objPpAssetStatusHstryResponse
							.setLastName(pPAssetStatusHistoryVO.getLastName());
					objPpAssetStatusHstryResponse
							.setUserEmailId(pPAssetStatusHistoryVO
									.getUserEmailId());
					objPpAssetStatusHstryResponse
							.setUserPhoneNumber(pPAssetStatusHistoryVO
									.getUserPhoneNumber());
					objPpAssetStatusHstryResponse
							.setUserCountryCode(pPAssetStatusHistoryVO
									.getUserCountryCode());
					aurizon.add(objPpAssetStatusHstryResponse);
					objPpAssetStatusHstryResponse = null;
				}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		} finally {
			objPPAssetStatusHistoryVO = null;
			queryParams = null;
		}
		return aurizon;
	}

	@GET
	@Path(OMDConstants.GET_ALERT_RULES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AlertSubResponseType> getConfigAlertForCustomer(
			@Context final UriInfo ui) throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		List<AlertDetailsVO> objAlertDetailsVO = null;
		AlertSubResponseType objPpAlertResponseType = null;
		List<AlertSubResponseType> configAlert = null;
		String customerId = null;
		String modelVal = null;
		try {
			queryParams = ui.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				}
				if (queryParams.containsKey(OMDConstants.MODEL_VAL)) {
					modelVal = queryParams.getFirst(OMDConstants.MODEL_VAL);
				}
			}
			objAlertDetailsVO = objAlertServiceIntf.getConfigAlertForCustomer(
					customerId, modelVal);

			if (null != objAlertDetailsVO) {
				configAlert = new ArrayList<AlertSubResponseType>(
						objAlertDetailsVO.size());
				for (AlertDetailsVO alertDetailsVO : objAlertDetailsVO) {
					objPpAlertResponseType = new AlertSubResponseType();
					objPpAlertResponseType.setRuleTitle(alertDetailsVO
							.getRuleTitle());
					objPpAlertResponseType.setOriginalId(alertDetailsVO
							.getOriginalId());
					configAlert.add(objPpAlertResponseType);
				}
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		} finally {
			objPpAlertResponseType = null;
			queryParams = null;
			objAlertDetailsVO = null;
		}
		return configAlert;
	}

	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user email of the user
	 */
	@GET
	@Path(OMDConstants.GET_USER_EMAIL_ID)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getUserEmailId(@Context final UriInfo uriParam)
			throws RMDServiceException {
		String userId = OMDConstants.USER_ID;
		MultivaluedMap<String, String> queryParams = null;
		String userEmailId = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.USER_ID)) {
					userId = queryParams.getFirst(OMDConstants.USER_ID);
				}
				userEmailId = objAlertServiceIntf.getUserEmailId(userId);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return userEmailId;
	}

	/**
	 * @return String
	 * @Description: method to get OTP parameters
	 */
	@GET
	@Path(OMDConstants.GET_OTP_PARAMETERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getUserPhoneNo() throws RMDServiceException {
		String otp_parameters = "";
		try {
			otp_parameters = objAlertServiceIntf.getOTPParameters();
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return otp_parameters;
	}

	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user Phone No. of the user
	 */
	@GET
	@Path(OMDConstants.GET_USER_PHONE_NO)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getUserPhoneNo(@Context final UriInfo uriParam)
			throws RMDServiceException {
		String userId = OMDConstants.USER_ID;
		MultivaluedMap<String, String> queryParams = null;
		String userPhoneNo = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.USER_ID)) {
					userId = queryParams.getFirst(OMDConstants.USER_ID);
				}
				userPhoneNo = objAlertServiceIntf.getUserPhoneNo(userId);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return userPhoneNo;
	}

	/**
	 * @param userId
	 * @return String
	 * @Description: method to get user Phone No. country code of the user
	 */
	@GET
	@Path(OMDConstants.GET_USER_PHONE_COUNTRY_CODE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getUserPhoneCountryCode(@Context final UriInfo uriParam)
			throws RMDServiceException {
		String userId = OMDConstants.USER_ID;
		MultivaluedMap<String, String> queryParams = null;
		String userPhoneCountryCode = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.USER_ID)) {
					userId = queryParams.getFirst(OMDConstants.USER_ID);
				}
				userPhoneCountryCode = objAlertServiceIntf
						.getUserPhoneCountryCode(userId);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.QUERY_PARAMETERS_NOT_PASSED);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return userPhoneCountryCode;
	}

	/**
	 * This method is used for retrieving ATS customer details for the
	 * parameters passed in UriInfo
	 * 
	 * @return List of Customer object
	 * @throws RMDServiceException
	 * @Description To get list of all the ATS customers for a user
	 */

	@GET
	@Path(OMDConstants.GET_ATS_CUSTOMERS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Customer> getATSCustomers() throws RMDServiceException {
		List<Customer> resCustomerList = null;
		List<String> atsCust = null;
		Customer customer = null;
		try {
			resCustomerList = new ArrayList<Customer>();

			atsCust = objAlertServiceIntf.getATSEnabledCustomers();
			if (RMDCommonUtility.isCollectionNotEmpty(atsCust)) {

				for (String custId : atsCust) {
					customer = new Customer();
					customer.setCustomerID(custId);
					resCustomerList.add(customer);
				}
			} /*
			 * else { throw new OMDNoResultFoundException(
			 * OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return resCustomerList;
	}

	@POST
	@Path(OMDConstants.GET_ALERT_SUB_ASSET_COUNT)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getAlertSubAssetCount(String userId)
			throws RMDServiceException {
		String count = "0";
		try {
			count = objAlertServiceIntf.getAlertSubAssetCount(EsapiUtil
					.stripXSSCharacters((String) (userId)));
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return count;
	}

	@GET
	@Path(OMDConstants.GET_ALL_SHOP_CHECK)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<RestrictedAlertShopRespType> getRestrictedAlertShop(
			@Context final UriInfo ui) throws RMDServiceException {
		List<RestrictedAlertShopRespType> shopResponseList = new ArrayList<RestrictedAlertShopRespType>();
		RestrictedAlertShopRespType respShop = null;
		List<RestrictedAlertShopVO> shopList = null;
		try {
			shopList = objAlertServiceIntf.getRestrictedAlertShop();
			if (null != shopList) {
				if (RMDCommonUtility.isCollectionNotEmpty(shopList)) {

					for (RestrictedAlertShopVO alertShopVo : shopList) {
						respShop = new RestrictedAlertShopRespType();
						respShop.setProximityCheckFlag(alertShopVo
								.getProximityCheckFlag());
						respShop.setAlertId(alertShopVo.getAlertId());
						respShop.setAlertName(alertShopVo.getAlertName());
						respShop.setCustomerId(alertShopVo.getCustomerId());
						shopResponseList.add(respShop);
					}
				}/*
				 * else { throw new
				 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
				 * ); }
				 */
			}/*
			 * else { throw new
			 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return shopResponseList;
	}

	@POST
	@Path(OMDConstants.ALERTHISTORYDETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AlertHstDetailsResponseType getAlertHistoryDetails(
			AlertHistoryRequestType objAlertHistoryRequestType)
			throws RMDServiceException {
		List<AlertHistoryResponseType> objAlertHistoryResponseTypelst = new ArrayList<AlertHistoryResponseType>();
		AlertHistoryDetailsVO objAlertHistoryDetailsVO = new AlertHistoryDetailsVO();
		AlertHstDetailsResponseType objAlertHstDetailsResponseType = new AlertHstDetailsResponseType();
		AlertHstDetailsVO objAlertHstDetailsVOlst = new AlertHstDetailsVO();
		AlertHistoryResponseType objAlertHistoryResponseType;
		GregorianCalendar objGregorianCalendar;
		String timeZone = getDefaultTimeZone();
		XMLGregorianCalendar strAlertDate;
		try {
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			if (null != objAlertHistoryRequestType.getStrAlert()
					&& !objAlertHistoryRequestType.getStrAlert().isEmpty()) {
				objAlertHistoryDetailsVO.setStrAlert(objAlertHistoryRequestType
						.getStrAlert());
			}
			if (null != objAlertHistoryRequestType.getStrAsset()
					&& !objAlertHistoryRequestType.getStrAsset().isEmpty()) {
				objAlertHistoryDetailsVO.setStrAsset(objAlertHistoryRequestType
						.getStrAsset());
			}
			if (null != objAlertHistoryRequestType.getStrCustomer()
					&& !objAlertHistoryRequestType.getStrCustomer().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrCustomer(objAlertHistoryRequestType
								.getStrCustomer());
			}
			if (null != objAlertHistoryRequestType.getStrFleet()
					&& !objAlertHistoryRequestType.getStrFleet().isEmpty()) {
				objAlertHistoryDetailsVO.setStrFleet(objAlertHistoryRequestType
						.getStrFleet());
			}
			if (null != objAlertHistoryRequestType.getStrFromDate()
					&& !objAlertHistoryRequestType.getStrFromDate().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrFromDate(objAlertHistoryRequestType
								.getStrFromDate());
			}
			if (null != objAlertHistoryRequestType.getStrModel()
					&& !objAlertHistoryRequestType.getStrModel().isEmpty()) {
				objAlertHistoryDetailsVO.setStrModel(objAlertHistoryRequestType
						.getStrModel());
			}
			if (null != objAlertHistoryRequestType.getStrMultiUsers()
					&& !objAlertHistoryRequestType.getStrMultiUsers().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrMultiUsers(objAlertHistoryRequestType
								.getStrMultiUsers());
			}
			if (null != objAlertHistoryRequestType.getStrNumberOfDays()
					&& !objAlertHistoryRequestType.getStrNumberOfDays()
							.isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrNumberOfDays(objAlertHistoryRequestType
								.getStrNumberOfDays());
			}
			if (null != objAlertHistoryRequestType.getStrToDate()
					&& !objAlertHistoryRequestType.getStrToDate().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrToDate(objAlertHistoryRequestType.getStrToDate());
			}
			if (null != objAlertHistoryRequestType.getStrType()
					&& !objAlertHistoryRequestType.getStrType().isEmpty()) {
				objAlertHistoryDetailsVO.setStrType(objAlertHistoryRequestType
						.getStrType());
			}
			if (null != objAlertHistoryRequestType.getStrRegion()
					&& !objAlertHistoryRequestType.getStrRegion().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrRegion(objAlertHistoryRequestType.getStrRegion());
			}
			if (null != objAlertHistoryRequestType.getStrRule()
					&& !objAlertHistoryRequestType.getStrRule().isEmpty()) {
				objAlertHistoryDetailsVO.setStrRule(objAlertHistoryRequestType
						.getStrRule());
			}
			if (null != objAlertHistoryRequestType.getStrRxFilter()
					&& !objAlertHistoryRequestType.getStrRxFilter().isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrRxFilter(objAlertHistoryRequestType
								.getStrRxFilter());
			}
			if (null != objAlertHistoryRequestType.getStrSearchValue()
					&& !objAlertHistoryRequestType.getStrSearchValue()
							.isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrSearchValue(objAlertHistoryRequestType
								.getStrSearchValue());
			}
			if (null != objAlertHistoryRequestType.getStrShop()
					&& !objAlertHistoryRequestType.getStrShop().isEmpty()) {
				objAlertHistoryDetailsVO.setStrShop(objAlertHistoryRequestType
						.getStrShop());
			}
			if (null != objAlertHistoryRequestType.getStrSubDivision()
					&& !objAlertHistoryRequestType.getStrSubDivision()
							.isEmpty()) {
				objAlertHistoryDetailsVO
						.setStrSubDivision(objAlertHistoryRequestType
								.getStrSubDivision());
			}
			if (null != objAlertHistoryRequestType.getStartRow()
					&& !objAlertHistoryRequestType.getStartRow().isEmpty()) {
				objAlertHistoryDetailsVO.setStartRow(Integer
						.valueOf(objAlertHistoryRequestType.getStartRow()));
			}
			if (null != objAlertHistoryRequestType.getPageNo()
					&& !objAlertHistoryRequestType.getPageNo().isEmpty()) {
				objAlertHistoryDetailsVO.setPageNo(Integer
						.valueOf(objAlertHistoryRequestType.getPageNo()));
			}
			if (null != objAlertHistoryRequestType.getRecordsPerPage()
					&& !objAlertHistoryRequestType.getRecordsPerPage()
							.isEmpty()) {
				objAlertHistoryDetailsVO
						.setRecordsPerPage(Integer
								.valueOf(objAlertHistoryRequestType
										.getRecordsPerPage()));
			}
			objAlertHistoryDetailsVO
					.setStrMethodFlag(objAlertHistoryRequestType
							.getStrMethodFlag());
			objAlertHistoryDetailsVO
					.setMultiSubscriptionPrivilege(objAlertHistoryRequestType
							.isMultiSubscriptionPrivilege());
			objAlertHistoryDetailsVO.setUserType(objAlertHistoryRequestType
					.getUserType());
			objAlertHistoryDetailsVO.setUserId(objAlertHistoryRequestType
					.getUserId());
			objAlertHstDetailsVOlst = objAlertServiceIntf
					.getAlertHistoryDetails(objAlertHistoryDetailsVO);
			if (!RMDCommonUtility.checkNull(objAlertHstDetailsVOlst)) {
				for (AlertHistoryDetailsVO objAltHstDetailsVO : objAlertHstDetailsVOlst
						.getAlertHistoryHdrlst()) {
					objAlertHistoryResponseType = new AlertHistoryResponseType();
					objAlertHistoryResponseType
							.setStrMultiUsers(objAltHstDetailsVO
									.getStrMultiUsers());
					objAlertHistoryResponseType.setStrAsset(objAltHstDetailsVO
							.getStrAsset());
					objAlertHistoryResponseType.setStrType(objAltHstDetailsVO
							.getStrType());
					objAlertHistoryResponseType.setStrGPSLat(objAltHstDetailsVO
							.getStrGPSLat());
					objAlertHistoryResponseType.setStrGPSLog(objAltHstDetailsVO
							.getStrGPSLog());
					objAlertHistoryResponseType.setStrRegion(objAltHstDetailsVO
							.getStrRegion());
					objAlertHistoryResponseType
							.setStrSubDivision(objAltHstDetailsVO
									.getStrSubDivision());
					objAlertHistoryResponseType.setStrFleet(objAltHstDetailsVO
							.getStrFleet());
					objAlertHistoryResponseType.setStrModel(objAltHstDetailsVO
							.getStrModel());
					objAlertHistoryResponseType.setStrShop(objAltHstDetailsVO
							.getStrShop());
					objAlertHistoryResponseType
							.setStrAlertTitle(objAltHstDetailsVO
									.getStrAlertTitle());
					objAlertHistoryResponseType.setStrPdfUrl(objAltHstDetailsVO
							.getStrPdfUrl());
					objAlertHistoryResponseType.setEmailText(objAltHstDetailsVO
							.getEmailText());
					objAlertHistoryResponseType
							.setStrRuleLogic(objAltHstDetailsVO
									.getStrRuleLogic());
					objAlertHistoryResponseType.setFiringId(objAltHstDetailsVO
							.getFiringId());
					objAlertHistoryResponseType.setFiredOn(objAltHstDetailsVO
							.getFiredOn());
					if (null != objAltHstDetailsVO.getStrAlertDate()) {
						objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(objAltHstDetailsVO
								.getStrAlertDate());
						RMDCommonUtility.setZoneOffsetTime(
								objGregorianCalendar, timeZone);
						strAlertDate = dtf
								.newXMLGregorianCalendar(objGregorianCalendar);
						objAlertHistoryResponseType
								.setStrAlertDate(strAlertDate);
					}
					objAlertHistoryResponseType
							.setNotification(objAltHstDetailsVO
									.getNotification());
					objAlertHistoryResponseType.setMobileNo(objAltHstDetailsVO
							.getMobileNo());
					objAlertHistoryResponseType
							.setIsEmailFlag(objAltHstDetailsVO.getIsEmailFlag());
					objAlertHistoryResponseType.setIsSmsFlag(objAltHstDetailsVO
							.getIsSmsFlag());
					objAlertHistoryResponseType.setSmsText(objAltHstDetailsVO
							.getSmsText());
					objAlertHistoryResponseTypelst
							.add(objAlertHistoryResponseType);
				}
			}
			objAlertHstDetailsResponseType
					.setAlertHdrResponseTypelst(objAlertHistoryResponseTypelst);
			objAlertHstDetailsResponseType.setCount(objAlertHstDetailsVOlst
					.getRecordsTotal());
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objAlertHstDetailsResponseType;
	}

	/*
	 * This method is used for retrieving Asset details for the pinpoint passed
	 * in UriInfo
	 * 
	 * @param ui
	 * 
	 * @return List of AssetResponseType
	 * 
	 * @throws RMDServiceException
	 */
	@POST
	@Path(OMDConstants.GET_ALERT_HST_POPULATE_DATA)
	@Consumes(MediaType.APPLICATION_XML)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<AlertAssetResponseType> getAlertHisotryPopulateData(
			final AlertAssetRequestType objAssetReqType)
			throws RMDServiceException {
		List<AlertAssetResponseType> arlAssetResponseType = new ArrayList<AlertAssetResponseType>();
		List<AlertAssetDetailsVO> arlAssetResponseVO = null;
		AlertAssetDetailsVO assetVO = new AlertAssetDetailsVO();
		AlertAssetResponseType assetResponse = null;
		try {

			if (validatePPAssetStatus(objAssetReqType)) {
				if (null != objAssetReqType.getCustomerId()
						&& !objAssetReqType.getCustomerId().isEmpty()) {
					assetVO.setCustomerId(BeanUtility
							.stripXSSCharacters(objAssetReqType.getCustomerId()));
				}
				if (null != objAssetReqType.getStrModel()
						&& !objAssetReqType.getStrModel().isEmpty()) {
					assetVO.setModel(BeanUtility
							.stripXSSCharacters(objAssetReqType.getStrModel()));
				}
				if (null != objAssetReqType.getStrFleet()
						&& !objAssetReqType.getStrFleet().isEmpty()) {
					assetVO.setFleet(BeanUtility
							.stripXSSCharacters(objAssetReqType.getStrFleet()));
				}
				arlAssetResponseVO = objAlertServiceIntf
						.getAlertHisotryPopulateData(assetVO);
				if (RMDCommonUtility.isCollectionNotEmpty(arlAssetResponseVO)) {
					for (AlertAssetDetailsVO tempVO : arlAssetResponseVO) {
						assetResponse = new AlertAssetResponseType();
						assetResponse
								.setModel(EsapiUtil
										.stripXSSCharacters((String) (tempVO
												.getModel())));
						assetResponse
								.setFleet(EsapiUtil
										.stripXSSCharacters((String) (tempVO
												.getFleet())));
						assetResponse.setAssetNumber(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getAssetNumber())));
						assetResponse.setCustomerId(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getCustomerId())));
						arlAssetResponseType.add(assetResponse);
					}
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlAssetResponseType;
	}

	/**
	 * 
	 * @param UriInfo
	 *            ui
	 * @return String
	 * @throws RMDServiceException
	 * @Description This method is used to get the states for the selected
	 *              Country
	 * 
	 */
	@GET
	@Path(OMDConstants.GET_SUBMENU_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getSubMenuList(@Context UriInfo ui)
			throws RMDServiceException {
		String subMenuString = RMDCommonConstants.EMPTY_STRING;
		MultivaluedMap<String, String> queryParams = null;
		String userId = OMDConstants.USERId;
		try {
			queryParams = ui.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.USERId)) {
				userId = queryParams.getFirst(OMDConstants.USERId);
			}
			subMenuString = objAlertServiceIntf.getSubMenuList(userId);
		} catch (Exception e) {
			LOG.error("Exception occuered in getSubMenuList() method of AlertResource"
					+ e);
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
		return subMenuString;
	}
	 
	@POST
	@Path(OMDConstants.GET_SUBSCRIBERS)
	@Produces({MediaType.APPLICATION_JSON })
	public List<AlertSubscriberResponseType> getSubscribers(
			final AlertSubscriberRequestType objSubscriberReqType)
			throws RMDServiceException {
		List<AlertSubscriberResponseType> arlSubscriberResponseType = new ArrayList<AlertSubscriberResponseType>();
		List<AlertSubscriberDetailsVO> arlSubscriberDetailsVO = null;
		AlertSubscriberDetailsVO subscriberVO = new AlertSubscriberDetailsVO();
		AlertSubscriberResponseType subscriberResponse = null;
		try { 

	
				if (null != objSubscriberReqType.getVehicleHeader()
						&& !objSubscriberReqType.getVehicleHeader().isEmpty()) {
					subscriberVO.setVehicleHeader(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getVehicleHeader()));
				}
				if (null != objSubscriberReqType.getVehicleNumber()
						&& !objSubscriberReqType.getVehicleNumber().isEmpty()) {
					subscriberVO.setVehicleNumber(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getVehicleNumber()));
				}
				if (null != objSubscriberReqType.getLatitude()
						&& !objSubscriberReqType.getLatitude().isEmpty()) {
					subscriberVO.setLatitude(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getLatitude()));
				}
				if (null != objSubscriberReqType.getLongitude()
						&& !objSubscriberReqType.getLongitude().isEmpty()) {
					subscriberVO.setLongitude(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getLongitude()));
				}
				if (null != objSubscriberReqType.getAlertId()
						&& !objSubscriberReqType.getAlertId().isEmpty()) {
					subscriberVO.setAlertId(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getAlertId()));
				}
				if (null != objSubscriberReqType.getAlertType()
						&& !objSubscriberReqType.getAlertType().isEmpty()) {
					subscriberVO.setAlertType(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getAlertType()));
				}
				if (null != objSubscriberReqType.getRegion()
						&& !objSubscriberReqType.getRegion().isEmpty()) {
					subscriberVO.setRegion(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getRegion()));
				}
				if (null != objSubscriberReqType.getSubRegion()
						&& !objSubscriberReqType.getSubRegion().isEmpty()) {
					subscriberVO.setSubRegion(BeanUtility
							.stripXSSCharacters(objSubscriberReqType.getSubRegion()));
				}
				arlSubscriberDetailsVO = objAlertServiceIntf
						.getSubscribersList(subscriberVO);
			
			
				if (RMDCommonUtility.isCollectionNotEmpty(arlSubscriberDetailsVO)) {
					for (AlertSubscriberDetailsVO tempVO : arlSubscriberDetailsVO) {
						subscriberResponse = new AlertSubscriberResponseType();
						subscriberResponse
								.setSso(EsapiUtil
										.stripXSSCharacters((String) (tempVO
												.getSso())));
						subscriberResponse
								.setEmailId(EsapiUtil
										.stripXSSCharacters((String) (tempVO
												.getEmailId())));
						subscriberResponse.setMeasurementSystem(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getMeasurementSystem())));
						subscriberResponse.setUserTimezone(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getUserTimezone())));
						subscriberResponse.setNotificationType(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getNotificationType())));
						subscriberResponse.setPhoneNumber(EsapiUtil
								.stripXSSCharacters((String) (tempVO
										.getPhoneNumber())));
						arlSubscriberResponseType.add(subscriberResponse);
					}
				}
			
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlSubscriberResponseType;
}
	}