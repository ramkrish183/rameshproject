package com.ge.trans.pp.services.manageGeofence.resources;

import java.util.ArrayList;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.pp.common.constants.OMDConstants;
import com.ge.trans.pp.common.exception.OMDApplicationException;
import com.ge.trans.pp.common.exception.OMDInValidInputException;
import com.ge.trans.pp.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.pp.common.util.BeanUtility;
import com.ge.trans.pp.common.util.PPRMDWebServiceErrorHandler;
import com.ge.trans.pp.services.manageGeofence.service.intf.ConfigGeofenceServiceIntf;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.AddNotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.CreateRegionVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.MilePostDetailsVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.NotifyConfigVO;
import com.ge.trans.pp.services.manageGeofence.service.valueobjects.ProximityDetailsVO;
import com.ge.trans.pp.services.manageGeofence.valueobjects.AddNotifyConfigRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.AddRegionRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.MilePostDetailsRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.MilePostDetailsResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.NotifyConfigRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.NotifyConfigResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.NotifyFlagsResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ProximityDetailsRequestType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ProximityDetailsResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ProximityParentResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.StateProvinceResponseType;
import com.ge.trans.pp.services.manageGeofence.valueobjects.ThresholdResponseType;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@Path(OMDConstants.REQ_URI_CONFIG_GEOFENCE_SERVICE)
@Component
public class ConfigGeofenceResource {

	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;

	@Autowired
	private ConfigGeofenceServiceIntf objConfigGeofenceServiceIntf;

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(ConfigGeofenceResource.class);

	/**
	 * @Author :
	 * @return :List<NotifyConfigResponseType>
	 * @param :@Context final UriInfo ui
	 * @throws :RMDServiceException
	 * @Description: This method fetches Notify Configuration details.
	 */
	@GET
	@Path(OMDConstants.GET_NOTIFY_CONFIGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<NotifyConfigResponseType> getNotifyConfigs(
			@Context final UriInfo ui) throws RMDServiceException {
		String customer = null;
		List<NotifyConfigResponseType> arlNotifyConfigResponseTypes = new ArrayList<NotifyConfigResponseType>();

		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			if (queryParams.containsKey(OMDConstants.CUSTOMER)) {
				customer = queryParams.getFirst(OMDConstants.CUSTOMER);
			}
			List<NotifyConfigVO> arlConfigVOs = objConfigGeofenceServiceIntf
					.getNotifyConfigs(customer);
			for (NotifyConfigVO objConfigVO : arlConfigVOs) {
				NotifyConfigResponseType objResponseType = new NotifyConfigResponseType();
				objResponseType.setCfgobjId(objConfigVO.getCfgobjId());
				objResponseType.setCfgParamName(objConfigVO.getCfgParamName());
				objResponseType.setCfgValue(objConfigVO.getCfgValue());
				objResponseType.setCustomer(objConfigVO.getCustomer());
				objResponseType.setModel(objConfigVO.getModel());
				objResponseType.setModelObjId(objConfigVO.getModelObjId());
				objResponseType.setOrgObjId(objConfigVO.getOrgObjId());
				arlNotifyConfigResponseTypes.add(objResponseType);
			}

		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return arlNotifyConfigResponseTypes;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :AddNotifyConfigRequestType objAddNotifyConfigRequestType
	 * @throws :RMDServiceException
	 * @Description: This method is used for Adding a Notify Configuration.
	 */
	@POST
	@Path(OMDConstants.ADD_NOTIFY_CONFIG)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addNotifyConfig(
			AddNotifyConfigRequestType objAddNotifyConfigRequestType)
			throws RMDServiceException {
		String addStatus = RMDCommonConstants.FAILURE;
		try {
			AddNotifyConfigVO objAddNotifyConfigVO = new AddNotifyConfigVO();
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getModelObjId())) {
				objAddNotifyConfigVO
						.setModelObjId(objAddNotifyConfigRequestType
								.getModelObjId());
			} else {
				throw new OMDInValidInputException(
						OMDConstants.MODEL_OBJID_NOT_PROVIDED);
			}

			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getCustomer())) {
				objAddNotifyConfigVO.setCustomer(objAddNotifyConfigRequestType
						.getCustomer());
			} else {
				throw new OMDInValidInputException(
						OMDConstants.ORG_OBJID_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getUserName())) {
				objAddNotifyConfigVO.setUserName(objAddNotifyConfigRequestType
						.getUserName());
			} else {
				throw new OMDInValidInputException(
						OMDConstants.USERNAME_VALUE_NOT_PROVIDED);
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getModel())) {
				objAddNotifyConfigVO.setModel(objAddNotifyConfigRequestType
						.getModel());
			} else {
				throw new OMDInValidInputException(
						OMDConstants.MODEL_NOT_PROVIDED);

			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getNotfyFlagValue())) {
				objAddNotifyConfigVO
						.setNotifyFlagValue(objAddNotifyConfigRequestType
								.getNotfyFlagValue());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getNotifyFlagName())) {
				objAddNotifyConfigVO
						.setNotifyFlagName(objAddNotifyConfigRequestType
								.getNotifyFlagName());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getThresholdName())) {
				objAddNotifyConfigVO
						.setThresholdName(objAddNotifyConfigRequestType
								.getThresholdName());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objAddNotifyConfigRequestType
					.getThresholdValue())) {
				objAddNotifyConfigVO
						.setThresholdValue(objAddNotifyConfigRequestType
								.getThresholdValue());
			}

			addStatus = objConfigGeofenceServiceIntf
					.addNotifyConfig(objAddNotifyConfigVO);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return addStatus;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :NotifyConfigRequestType objConfigRequestType
	 * @throws :RMDServiceException
	 * @Description: This method is used for Updating a Notify Configuration.
	 */
	@POST
	@Path(OMDConstants.UPDATE_NOTIFY_CONFIG)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updateNotifyConfig(NotifyConfigRequestType objRequestType)
			throws RMDServiceException {
		String updateStatus = RMDCommonConstants.FAILURE;
		try {
			List<NotifyConfigVO> arlConfigVOs = new ArrayList<NotifyConfigVO>();
			List<NotifyConfigRequestType> arlConfigRequestTypes = objRequestType
					.getArlNotifyConfigRequestType();
			for (NotifyConfigRequestType objConfigRequestType : arlConfigRequestTypes) {
				NotifyConfigVO objConfigVO = new NotifyConfigVO();
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getCfgobjId())) {
					objConfigVO.setCfgobjId(objConfigRequestType.getCfgobjId());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.CFGOBJID_NOT_PROVIDED);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getCfgParamName())) {
					objConfigVO.setCfgParamName(objConfigRequestType
							.getCfgParamName());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.PARAM_NAME_NOT_PROVIDED);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getCfgValue())) {
					objConfigVO.setCfgValue(objConfigRequestType.getCfgValue());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.PARAM_VALUE_NOT_PROVIDED);
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getCustomer())) {
					objConfigVO.setCustomer(objConfigRequestType.getCustomer());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getModel())) {
					objConfigVO.setModel(objConfigRequestType.getModel());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getModelObjId())) {
					objConfigVO.setModelObjId(objConfigRequestType
							.getModelObjId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getOrgObjId())) {
					objConfigVO.setOrgObjId(objConfigRequestType.getOrgObjId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objConfigRequestType
						.getUserName())) {
					objConfigVO.setUserName(objConfigRequestType.getUserName());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.USERNAME_VALUE_NOT_PROVIDED);
				}
				arlConfigVOs.add(objConfigVO);
			}
			updateStatus = objConfigGeofenceServiceIntf
					.updateNotifyConfig(arlConfigVOs);

		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return updateStatus;
	}

	/**
	 * @Author :
	 * @return :NotifyFlagsResponseType
	 * @param :
	 * @throws :RMDServiceException
	 * @Description: This method is used for fetching notify flags.
	 */
	@GET
	@Path(OMDConstants.GET_NOTIFY_FLAGS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public NotifyFlagsResponseType getNotifyFlags() throws RMDServiceException {
		NotifyFlagsResponseType objFlagsResponseType = new NotifyFlagsResponseType();
		try {
			List<String> arlnotifyFlags = objConfigGeofenceServiceIntf
					.getNotifyFlags();
			objFlagsResponseType.setNotifyFlagsList(arlnotifyFlags);

		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objFlagsResponseType;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :AddNotifyConfigVO objAddNotifyConfigVO
	 * @throws :RMDServiceException
	 * @Description:This method is used for fetching Threshold values.
	 */
	@GET
	@Path(OMDConstants.GET_THRESHOLDS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ThresholdResponseType getThreshold() throws RMDServiceException {

		ThresholdResponseType objThresholdResponseType = new ThresholdResponseType();
		try {
			List<String> thresHoldList = objConfigGeofenceServiceIntf
					.getThreshold();
			objThresholdResponseType.setThresholdList(thresHoldList);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objThresholdResponseType;
	}

	// Murali Changes

	/**
	 * @Author :
	 * @return :StateProvinceResponseType
	 * @param :
	 * @throws :RMDServiceException
	 * @Description: This Method Fetches the list of stateprovince.
	 */
	@GET
	@Path(OMDConstants.GET_STATE_PROVINCE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StateProvinceResponseType getStateProvince()
			throws RMDServiceException {

		final StateProvinceResponseType objStateProvinceResponseType = new StateProvinceResponseType();
		List<String> arlStateProvinceList = null;
		try {

			arlStateProvinceList = objConfigGeofenceServiceIntf
					.getStateProvince();
			LOG.debug("StateProvince List retrieved from PP Web Service ");
			if (null != arlStateProvinceList) {
				objStateProvinceResponseType
						.setStateProvinceList(arlStateProvinceList);
			} /*
			 * else { throw new
			 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION); }
			 */

			LOG.debug("Arraylist of getStateProvince Response "
					+ objStateProvinceResponseType);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objStateProvinceResponseType;
	}

	/**
	 * @Author:
	 * @param :UriInfo uriParam
	 * @return:ProximityParentResponseType
	 * @throws:RMDServiceException
	 * @Description: This method is used to get proximity parent for the
	 *               selected customer
	 */
	@GET
	@Path(OMDConstants.GET_PROXIMITY_PARENT)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public ProximityParentResponseType getProximityParent(
			@Context final UriInfo uriParam) throws RMDServiceException {

		final ProximityParentResponseType objProximityParentResponseType = new ProximityParentResponseType();
		MultivaluedMap<String, String> queryParams = null;
		List<String> proxParentList = null;
		String customerId = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				} else {
					throw new OMDInValidInputException(
							OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
				}
				proxParentList = objConfigGeofenceServiceIntf
						.getProximityParent(customerId);

				if (null != proxParentList && !proxParentList.isEmpty()) {
					objProximityParentResponseType
							.setProximityParentList(proxParentList);
				} /*
				 * else { throw new OMDNoResultFoundException(
				 * OMDConstants.NORECORDFOUNDEXCEPTION); }
				 */

			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objProximityParentResponseType;
	}

	/**
	 * @Author:
	 * @param :UriInfo uriParam
	 * @return:StateProvinceResponseType
	 * @throws:RMDServiceException
	 * @Description: This method is used to get region for the selected customer
	 */
	@GET
	@Path(OMDConstants.GET_REGION)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StateProvinceResponseType getRegion(@Context final UriInfo uriParam)
			throws RMDServiceException {

		final StateProvinceResponseType objStateProvinceResponseType = new StateProvinceResponseType();
		MultivaluedMap<String, String> queryParams = null;
		List<String> regionList = null;
		String customerId = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					customerId = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				} else {
					throw new OMDInValidInputException(
							OMDConstants.CUSTOMER_ID_NOT_PROVIDED);
				}
				regionList = objConfigGeofenceServiceIntf.getRegion(customerId);

				if (null != regionList && !regionList.isEmpty()) {
					objStateProvinceResponseType.setRegionList(regionList);
				} /*
				 * else { throw new OMDNoResultFoundException(
				 * OMDConstants.NORECORDFOUNDEXCEPTION); }
				 */
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objStateProvinceResponseType;
	}

	/**
	 * @Author:
	 * @param :UriInfo uriParam
	 * @return:StateProvinceResponseType
	 * @throws:RMDServiceException
	 * @Description: This method is used to get sub region for the selected
	 *               region
	 */
	@GET
	@Path(OMDConstants.GET_SUB_REGION)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public StateProvinceResponseType getSubRegion(
			@Context final UriInfo uriParam) throws RMDServiceException {

		final StateProvinceResponseType objStateProvinceResponseType = new StateProvinceResponseType();
		MultivaluedMap<String, String> queryParams = null;
		List<String> subRegionList = null;
		String region = null;
		try {
			queryParams = uriParam.getQueryParameters();
			if (!queryParams.isEmpty()) {
				if (queryParams.containsKey(OMDConstants.REGION)) {
					region = queryParams.getFirst(OMDConstants.REGION);
				} else {
					throw new OMDInValidInputException(
							OMDConstants.REGION_NOT_PROVIDED);
				}
				subRegionList = objConfigGeofenceServiceIntf
						.getSubRegion(region);

				if (null != subRegionList && !subRegionList.isEmpty()) {
					objStateProvinceResponseType
							.setSubRegionList(subRegionList);
				}
			}
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return objStateProvinceResponseType;
	}

	/**
	 * @Author :
	 * @return :List<ProximityDetailsResponseType>
	 * @param :@Context UriInfo ui
	 * @throws :RMDServiceException
	 * @Description: This method is used to get the proximity details for the
	 *               selected customer.
	 */
	@GET
	@Path(OMDConstants.GET_GEO_PROXIMITY_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ProximityDetailsResponseType> getProximityDetails(
			@Context UriInfo ui) throws RMDServiceException {
		String customerID = null;
		String proxStatus = null;
		List<ProximityDetailsResponseType> proxDetailsList = new ArrayList<ProximityDetailsResponseType>();
		List<ProximityDetailsVO> proximityList = new ArrayList<ProximityDetailsVO>();
		ProximityDetailsResponseType objProximityDetailsResponseType = null;
		try {

			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			try {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {

					customerID = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				}
				if (queryParams.containsKey(OMDConstants.PROXMITY_STATUS)) {

					proxStatus = queryParams
							.getFirst(OMDConstants.PROXMITY_STATUS);
				}
				proximityList = objConfigGeofenceServiceIntf
						.getProximityDetails(customerID, proxStatus);
				for (ProximityDetailsVO objProximityDetailsVO : proximityList) {
					objProximityDetailsResponseType = new ProximityDetailsResponseType();
					objProximityDetailsResponseType
							.setProxObjId(objProximityDetailsVO.getProxObjId());
					objProximityDetailsResponseType
							.setActiveFlag(objProximityDetailsVO
									.getActiveFlag());
					objProximityDetailsResponseType
							.setCustProxId(objProximityDetailsVO
									.getCustProxId());
					objProximityDetailsResponseType
							.setProxDesc(objProximityDetailsVO.getProxDesc());
					objProximityDetailsResponseType
							.setProxType(objProximityDetailsVO.getProxType());
					objProximityDetailsResponseType
							.setProxParent(objProximityDetailsVO
									.getProxParent());
					objProximityDetailsResponseType
							.setUpperLeftLat(objProximityDetailsVO
									.getUpperLeftLat());
					objProximityDetailsResponseType
							.setUpperLeftLon(objProximityDetailsVO
									.getUpperLeftLon());
					objProximityDetailsResponseType
							.setLowerRightLat(objProximityDetailsVO
									.getLowerRightLat());
					objProximityDetailsResponseType
							.setLowerRightLon(objProximityDetailsVO
									.getLowerRightLon());
					objProximityDetailsResponseType
							.setArrivalFlag(objProximityDetailsVO
									.getArrivalFlag());
					objProximityDetailsResponseType
							.setDepartureFlag(objProximityDetailsVO
									.getDepartureFlag());
					objProximityDetailsResponseType
							.setDwelFlag(objProximityDetailsVO.getDwelFlag());
					objProximityDetailsResponseType
							.setInterchangeFlag(objProximityDetailsVO
									.getInterchangeFlag());
					objProximityDetailsResponseType
							.setConsistFlag(objProximityDetailsVO
									.getConsistFlag());
					proxDetailsList.add(objProximityDetailsResponseType);

				}

			} catch (Exception e) {
				throw new OMDApplicationException(
						BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.GENERALEXCEPTION),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
						e);
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage(), e);
		}

		return proxDetailsList;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : ProximityDetailsRequestType
	 * @throws :RMDServiceException
	 * @Description: This method is used to update selected proximity details
	 */
	@POST
	@Path(OMDConstants.UPDATE_PROXIMITY_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updateProximity(
			ProximityDetailsRequestType objProxDeatailsRequestType)
			throws RMDServiceException {
		String status = OMDConstants.FAILURE;
		List<ProximityDetailsVO> proxDetailsList = new ArrayList<ProximityDetailsVO>();
		List<ProximityDetailsRequestType> arlProxDetailsRequestTypes = objProxDeatailsRequestType
				.getArlProxDeatislRequestType();
		try {
			for (ProximityDetailsRequestType proxDetailsRequestType : arlProxDetailsRequestTypes) {
				ProximityDetailsVO objProximityDetailsVO = new ProximityDetailsVO();
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getProxObjId())) {
					objProximityDetailsVO.setProxObjId(proxDetailsRequestType
							.getProxObjId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getActiveFlag())) {
					objProximityDetailsVO.setActiveFlag(proxDetailsRequestType
							.getActiveFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getCustProxId())) {
					objProximityDetailsVO.setCustProxId(proxDetailsRequestType
							.getCustProxId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getSmppProxId())) {
					objProximityDetailsVO.setSmppProxId(proxDetailsRequestType
							.getSmppProxId());
				}

				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getProxDesc())) {
					objProximityDetailsVO.setProxDesc(proxDetailsRequestType
							.getProxDesc());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getProxType())) {
					objProximityDetailsVO.setProxType(proxDetailsRequestType
							.getProxType());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getProxParent())) {
					objProximityDetailsVO.setProxParent(proxDetailsRequestType
							.getProxParent());
				}
				if (!RMDCommonUtility.isNullOrEmpty(String
						.valueOf(proxDetailsRequestType.getUpperLeftLat()))) {
					objProximityDetailsVO
							.setUpperLeftLat(proxDetailsRequestType
									.getUpperLeftLat());
				}

				if (!RMDCommonUtility.isNullOrEmpty(String
						.valueOf(proxDetailsRequestType.getUpperLeftLon()))) {
					objProximityDetailsVO
							.setUpperLeftLon(proxDetailsRequestType
									.getUpperLeftLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(String
						.valueOf(proxDetailsRequestType.getLowerRightLat()))) {
					objProximityDetailsVO
							.setLowerRightLat(proxDetailsRequestType
									.getLowerRightLat());
				}
				if (!RMDCommonUtility.isNullOrEmpty(String
						.valueOf(proxDetailsRequestType.getLowerRightLon()))) {
					objProximityDetailsVO
							.setLowerRightLon(proxDetailsRequestType
									.getLowerRightLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getArrivalFlag())) {
					objProximityDetailsVO.setArrivalFlag(proxDetailsRequestType
							.getArrivalFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getDepartureFlag())) {
					objProximityDetailsVO
							.setDepartureFlag(proxDetailsRequestType
									.getDepartureFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getDwelFlag())) {
					objProximityDetailsVO.setDwelFlag(proxDetailsRequestType
							.getDwelFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getInterchangeFlag())) {
					objProximityDetailsVO
							.setInterchangeFlag(proxDetailsRequestType
									.getInterchangeFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getConsistFlag())) {
					objProximityDetailsVO.setConsistFlag(proxDetailsRequestType
							.getConsistFlag());
				}

				if (!RMDCommonUtility.isNullOrEmpty(proxDetailsRequestType
						.getUserId())) {
					objProximityDetailsVO.setUserId(proxDetailsRequestType
							.getUserId());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.USERID_NOT_PROVIDED);
				}

				proxDetailsList.add(objProximityDetailsVO);
			}

			status = objConfigGeofenceServiceIntf
					.updateProximity(proxDetailsList);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :ProximityDetailsRequestType
	 * @throws :RMDServiceException
	 * @Description: This method adds new proximity..
	 */
	@POST
	@Path(OMDConstants.ADD_NEW_PROXIMITY)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addNewProximity(
			final ProximityDetailsRequestType objProxRequestType)
			throws RMDServiceException {
		String status = null;
		ProximityDetailsVO objProxDetails = null;

		try {
			if (null != objProxRequestType) {
				objProxDetails = new ProximityDetailsVO();
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getCustomerName())) {
					objProxDetails.setCustomerName(objProxRequestType
							.getCustomerName());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getActiveFlag())) {
					objProxDetails.setActiveFlag(objProxRequestType
							.getActiveFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getCustProxId())) {
					objProxDetails.setCustProxId(objProxRequestType
							.getCustProxId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getProxDesc())) {
					objProxDetails
							.setProxDesc(objProxRequestType.getProxDesc());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getProxType())) {
					objProxDetails
							.setProxType(objProxRequestType.getProxType());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getUpperLeftLat())) {
					objProxDetails.setUpperLeftLat(objProxRequestType
							.getUpperLeftLat());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getUpperLeftLon())) {
					objProxDetails.setUpperLeftLon(objProxRequestType
							.getUpperLeftLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getLowerRightLat())) {
					objProxDetails.setLowerRightLat(objProxRequestType
							.getLowerRightLat());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getLowerRightLon())) {
					objProxDetails.setLowerRightLon(objProxRequestType
							.getLowerRightLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getArrivalFlag())) {
					objProxDetails.setArrivalFlag(objProxRequestType
							.getArrivalFlag());
				}

				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getDepartureFlag())) {
					objProxDetails.setDepartureFlag(objProxRequestType
							.getDepartureFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getDwelFlag())) {
					objProxDetails
							.setDwelFlag(objProxRequestType.getDwelFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getInterchangeFlag())) {
					objProxDetails.setInterchangeFlag(objProxRequestType
							.getInterchangeFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getConsistFlag())) {
					objProxDetails.setConsistFlag(objProxRequestType
							.getConsistFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getUserId())) {
					objProxDetails.setUserId(objProxRequestType.getUserId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objProxRequestType
						.getProxParent())) {
					objProxDetails.setProxParent(objProxRequestType
							.getProxParent());
				}

			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			status = objConfigGeofenceServiceIntf
					.addNewProximity(objProxDetails);

		} catch (Exception e) {
			LOG.error("Error in addNewProximity method of  ConfigGeofenceResource:"
					+ e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}
		return status;

	}

	/**
	 * @Author:
	 * @param :ProximityDetailsRequestType
	 * @throws:RMDServiceException
	 * @Description: This method is used to delete the proximities
	 */
	@POST
	@Path(OMDConstants.DELETE_PROXIMITY)
	@Consumes(MediaType.APPLICATION_XML)
	public void deleteProximity(
			final ProximityDetailsRequestType proxRequestType)
			throws RMDServiceException {
		ProximityDetailsVO objProximityDetailsVO = null;
		try {
			if (proxRequestType != null
					&& proxRequestType.getProxObjId() != null
					&& !proxRequestType.getProxObjId().isEmpty()) {
				objProximityDetailsVO = new ProximityDetailsVO();
				objProximityDetailsVO.setProxObjId(proxRequestType
						.getProxObjId());
				objConfigGeofenceServiceIntf
						.deleteProximity(objProximityDetailsVO);
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}

		} catch (Exception ex) {
			LOG.error("Error in deleteProximity method of  ConfigGeofenceResource:"
					+ ex);
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

	}

	/**
	 * @Author :
	 * @return :List<MilePostDetailsResponseType>
	 * @param :@Context UriInfo ui
	 * @throws :RMDServiceException
	 * @Description: This method is used to get the milepost details for the
	 *               selected customer.
	 */
	@GET
	@Path(OMDConstants.GET_GEO_MILEPOST_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<MilePostDetailsResponseType> getMilePostDetails(
			@Context UriInfo ui) throws RMDServiceException {
		String customerID = null;
		String milePostStatus = null;
		List<MilePostDetailsResponseType> milePostDetailsList = new ArrayList<MilePostDetailsResponseType>();
		List<MilePostDetailsVO> milePostList = new ArrayList<MilePostDetailsVO>();
		MilePostDetailsResponseType objMilePostDetailsResponseType = null;
		try {
			final MultivaluedMap<String, String> queryParams = ui
					.getQueryParameters();
			try {
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {

					customerID = queryParams.getFirst(OMDConstants.CUSTOMER_ID);
				}
				if (queryParams.containsKey(OMDConstants.MILEPOST_STATUS)) {

					milePostStatus = queryParams
							.getFirst(OMDConstants.MILEPOST_STATUS);
				}
				milePostList = objConfigGeofenceServiceIntf.getMilePostDetails(
						customerID, milePostStatus);
				for (MilePostDetailsVO objMilePostDetailsVO : milePostList) {
					objMilePostDetailsResponseType = new MilePostDetailsResponseType();
					objMilePostDetailsResponseType
							.setMilePostObjId(objMilePostDetailsVO
									.getMilePostObjId());
					objMilePostDetailsResponseType
							.setActiveFlag(objMilePostDetailsVO.getActiveFlag());
					objMilePostDetailsResponseType
							.setMilePostId(objMilePostDetailsVO.getMilePostId());
					objMilePostDetailsResponseType
							.setMilePostDesc(objMilePostDetailsVO
									.getMilePostDesc());
					objMilePostDetailsResponseType
							.setStateProvince(objMilePostDetailsVO
									.getStateProvince());
					objMilePostDetailsResponseType
							.setRegion(objMilePostDetailsVO.getRegion());
					objMilePostDetailsResponseType
							.setSubRegion(objMilePostDetailsVO.getSubRegion());
					objMilePostDetailsResponseType
							.setGpsLat(objMilePostDetailsVO.getGpsLat());
					objMilePostDetailsResponseType
							.setGpsLon(objMilePostDetailsVO.getGpsLon());
					objMilePostDetailsResponseType
							.setCustomerName(objMilePostDetailsVO
									.getCustomerName());

					milePostDetailsList.add(objMilePostDetailsResponseType);

				}

			} catch (Exception e) {
				throw new OMDApplicationException(
						BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.GENERALEXCEPTION),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)),
						e);
			}

		} catch (Exception e) {
			LOG.debug(e.getMessage(), e);
		}

		return milePostDetailsList;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : MilePostDetailsRequestType
	 * @throws :RMDServiceException
	 * @Description: This method is used to update selected milepost details
	 */
	@POST
	@Path(OMDConstants.UPDATE_MILEPOST_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updateMilePost(
			MilePostDetailsRequestType objMilePostDeatailsRequestType)
			throws RMDServiceException {
		String status = OMDConstants.FAILURE;
		List<MilePostDetailsVO> mpDetailsList = new ArrayList<MilePostDetailsVO>();
		List<MilePostDetailsRequestType> arlMilePostDetailsRequestTypes = objMilePostDeatailsRequestType
				.getarlMPDetailslRequestType();
		try {
			for (MilePostDetailsRequestType mpDetailsRequestType : arlMilePostDetailsRequestTypes) {
				MilePostDetailsVO objMilePostDetailsVO = new MilePostDetailsVO();
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getMilePostObjId())) {
					objMilePostDetailsVO.setMilePostObjId(mpDetailsRequestType
							.getMilePostObjId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getActiveFlag())) {
					objMilePostDetailsVO.setActiveFlag(mpDetailsRequestType
							.getActiveFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getMilePostId())) {
					objMilePostDetailsVO.setMilePostId(mpDetailsRequestType
							.getMilePostId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getMilePostDesc())) {
					objMilePostDetailsVO.setMilePostDesc(mpDetailsRequestType
							.getMilePostDesc());
				}

				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getStateProvince())) {
					objMilePostDetailsVO.setStateProvince(mpDetailsRequestType
							.getStateProvince());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getRegion())) {
					objMilePostDetailsVO.setRegion(mpDetailsRequestType
							.getRegion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getSubRegion())) {
					objMilePostDetailsVO.setSubRegion(mpDetailsRequestType
							.getSubRegion());
				}

				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getGpsLat())) {
					objMilePostDetailsVO.setGpsLat(mpDetailsRequestType
							.getGpsLat());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getGpsLon())) {
					objMilePostDetailsVO.setGpsLon(mpDetailsRequestType
							.getGpsLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getCustomerName())) {
					objMilePostDetailsVO.setCustomerName(mpDetailsRequestType
							.getCustomerName());
				}

				if (!RMDCommonUtility.isNullOrEmpty(mpDetailsRequestType
						.getUserId())) {
					objMilePostDetailsVO.setUserId(mpDetailsRequestType
							.getUserId());
				} else {
					throw new OMDInValidInputException(
							OMDConstants.USERID_NOT_PROVIDED);
				}

				mpDetailsList.add(objMilePostDetailsVO);
			}

			status = objConfigGeofenceServiceIntf.updateMilePost(mpDetailsList);
		} catch (Exception ex) {
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :MilePostDetailsRequestType
	 * @throws :RMDServiceException
	 * @Description: This method adds new milepost.
	 */
	@POST
	@Path(OMDConstants.ADD_NEW_MILEPOST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addNewMilePost(
			final MilePostDetailsRequestType objMilePostRequestType)
			throws RMDServiceException {
		String status = null;
		MilePostDetailsVO objMilePostDetails = null;

		try {
			if (null != objMilePostRequestType) {
				objMilePostDetails = new MilePostDetailsVO();
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getCustomerName())) {
					objMilePostDetails.setCustomerName(objMilePostRequestType
							.getCustomerName());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getActiveFlag())) {
					objMilePostDetails.setActiveFlag(objMilePostRequestType
							.getActiveFlag());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getMilePostId())) {
					objMilePostDetails.setMilePostId(objMilePostRequestType
							.getMilePostId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getMilePostDesc())) {
					objMilePostDetails.setMilePostDesc(objMilePostRequestType
							.getMilePostDesc());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getStateProvince())) {
					objMilePostDetails.setStateProvince(objMilePostRequestType
							.getStateProvince());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getGpsLat())) {
					objMilePostDetails.setGpsLat(objMilePostRequestType
							.getGpsLat());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getGpsLon())) {
					objMilePostDetails.setGpsLon(objMilePostRequestType
							.getGpsLon());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getRegion())) {
					objMilePostDetails.setRegion(objMilePostRequestType
							.getRegion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getSubRegion())) {
					objMilePostDetails.setSubRegion(objMilePostRequestType
							.getSubRegion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objMilePostRequestType
						.getUserId())) {
					objMilePostDetails.setUserId(objMilePostRequestType
							.getUserId());
				}
			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			status = objConfigGeofenceServiceIntf
					.addNewMilePost(objMilePostDetails);

		} catch (Exception e) {
			LOG.error("Error in addNewMilePost method of  ConfigGeofenceResource:"
					+ e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}
		return status;

	}

	/**
	 * @Author:
	 * @param :MilePostDetailsRequestType
	 * @throws:RMDServiceException
	 * @Description: This method is used to delete the milepost
	 */
	@POST
	@Path(OMDConstants.DELETE_MILEPOST)
	@Consumes(MediaType.APPLICATION_XML)
	public void deleteMilePost(final MilePostDetailsRequestType mpRequestType)
			throws RMDServiceException {
		MilePostDetailsVO objMilePostDetailsVO = null;
		try {
			if (mpRequestType != null
					&& mpRequestType.getMilePostObjId() != null
					&& !mpRequestType.getMilePostObjId().isEmpty()) {
				objMilePostDetailsVO = new MilePostDetailsVO();
				objMilePostDetailsVO.setMilePostObjId(mpRequestType
						.getMilePostObjId());
				objConfigGeofenceServiceIntf
						.deleteMilePost(objMilePostDetailsVO);
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}

		} catch (Exception ex) {
			LOG.error("Error in deleteMilePost method of  ConfigGeofenceResource:"
					+ ex);
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

	}

	/**
	 * @Author:
	 * @param :UriInfo uriParam
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used to add New Region/sub region
	 */
	@POST
	@Path(OMDConstants.ADD_REGION_SUB_REGION)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String addRegionSubRegion(
			final AddRegionRequestType objRegionRequestType)
			throws RMDServiceException {

		String status = null;
		CreateRegionVO regionVO = null;
		try {
			if (null != objRegionRequestType) {
				regionVO = new CreateRegionVO();
				if (!RMDCommonUtility.isNullOrEmpty(objRegionRequestType
						.getCustomerId())) {
					regionVO.setCustomerId(objRegionRequestType.getCustomerId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objRegionRequestType
						.getRegion())) {
					regionVO.setRegion(objRegionRequestType.getRegion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objRegionRequestType
						.getSubRegion())) {
					regionVO.setSubRegion(objRegionRequestType.getSubRegion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objRegionRequestType
						.getUserId())) {
					regionVO.setUserId(objRegionRequestType.getUserId());
				}
				status = objConfigGeofenceServiceIntf
						.addRegionSubRegion(regionVO);

			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}

		} catch (Exception ex) {
			LOG.error("Error in addRegionSubRegion method of  ConfigGeofenceResource:"
					+ ex);
			PPRMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return status;
	}

}
