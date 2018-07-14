/**
 * ============================================================
 * Classification: GE Confidential
 * File : AdminResource.java
 * Description : 
 * Package : com.ge.trans.rmd.services.admin.resources
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.admin.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.bo.intf.PopupListAdminBOIntf;
import com.ge.trans.eoa.services.admin.service.intf.PopupListAdminServiceIntf;
import com.ge.trans.eoa.services.admin.service.intf.UserServiceIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.GetSysParameterVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
import com.ge.trans.eoa.services.cases.service.intf.LocationServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationResultServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindLocationServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.intf.AuthenticatorServiceIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterDetailServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FetchFavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FilterDetailVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDApplicationException;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.AddressDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.ApplicationParametersRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.ApplicationParametersResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.FavFilterDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.FetchFavFilterResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.FilterDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.LocationResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.PersonalDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.PreferencesRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.PreferencesType;
import com.ge.trans.rmd.services.admin.valueobjects.RolesType;
import com.ge.trans.rmd.services.admin.valueobjects.SaveFavFilterType;
import com.ge.trans.rmd.services.admin.valueobjects.SystemParamResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.SystemTimeZoneResponseType;
import com.ge.trans.rmd.services.admin.valueobjects.UserDeleteRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.UserDetailType;
import com.ge.trans.rmd.services.admin.valueobjects.UserRequestType;
import com.ge.trans.rmd.services.admin.valueobjects.UsersResponseType;
import com.ge.trans.rmd.services.assets.valueobjects.UserEOADetailsResponseType;
import com.ge.trans.rmd.services.authorization.valueobjects.PrivilegeDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: July 21, 2011
 * @Date Modified : July 25, 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as admin Webservices and provide the Admin
 *              related funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.ADMIN_SERVICE)
@Component
@SuppressWarnings("unchecked")
public class AdminResource extends BaseResource {
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(AdminResource.class);

	@Autowired
	private AuthenticatorServiceIntf objAuthenticatorServiceIntf;
	@Autowired
	private PopupListAdminServiceIntf objPopAdminServiceIntf;
	@Autowired
	private UserServiceIntf objUserServiceIntf;
	@Autowired
	private OMDResourceMessagesIntf omdResourceMessagesIntf;
	@Autowired
	private PopupListAdminBOIntf popupListAdminBOIntf;
	@Autowired
	private LocationServiceIntf objLocationServiceIntf;
	@Autowired
	private com.ge.trans.eoa.services.admin.service.intf.PopupListAdminServiceIntf objpopupListAdminServiceIntf;

	/**
	 * This method is used for fetching system time zone
	 * 
	 * @param ui
	 * @return List of SystemTimeZoneResponseType
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_SYSTEM_TIMEZONE)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<SystemTimeZoneResponseType> getSystemTimeZones()
			throws RMDServiceException {
		String strLanguage = null;
		List<GetCmTimeZoneVO> lstAvailableTimeZone = new ArrayList<GetCmTimeZoneVO>();
		List<SystemTimeZoneResponseType> lstTimeZoneResponse = null;
		SystemTimeZoneResponseType objSystemTimeZoneResponseType = null;
		GetCmTimeZoneVO getCmTimeZoneVO;
		try {

			strLanguage = OMDConstants.CHINESE_LANGUAGE;

			lstAvailableTimeZone = objAuthenticatorServiceIntf
					.getTimeZones(strLanguage);

			if (RMDCommonUtility.isCollectionNotEmpty(lstAvailableTimeZone)) {
				lstTimeZoneResponse = new ArrayList<SystemTimeZoneResponseType>();
				for (final Iterator<GetCmTimeZoneVO> iterator = lstAvailableTimeZone
						.iterator(); iterator.hasNext();) {
					getCmTimeZoneVO = iterator.next();

					objSystemTimeZoneResponseType = new SystemTimeZoneResponseType();
					CMBeanUtility.copyGetTmeZonVOToSysTmeZonResType(
							getCmTimeZoneVO, objSystemTimeZoneResponseType);
					objSystemTimeZoneResponseType
							.setDisplayName(getCmTimeZoneVO.getDisplayName());
					lstTimeZoneResponse.add(objSystemTimeZoneResponseType);
				}
			} /*
			 * else { throw new
			 * OMDNoResultFoundException(BeanUtility.getErrorCode
			 * (OMDConstants.NORECORDFOUNDEXCEPTION),
			 * omdResourceMessagesIntf.getMessage(
			 * BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
			 * new String[] {},
			 * BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE))); }
			 */
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return lstTimeZoneResponse;
	}

	/**
	 * This method is used for fetching user details
	 * 
	 * @param ui
	 * @return List<UsersResponseType>
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_USERS_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<UsersResponseType> getUsersDetails(
			@Context final UriInfo uriParam) throws RMDServiceException {

		UserServiceVO userServiceVO = null;
		List<UserServiceVO> listUsers = null;
		UsersResponseType responseType = null;
		final List<UsersResponseType> liResponseTypes = new ArrayList<UsersResponseType>();
		try {

			final MultivaluedMap<String, String> queryParams = uriParam
					.getQueryParameters();

			int[] methodConstants = { RMDCommonConstants.AlPHA_NUMERIC };
			/* Added in phase2 Sprint 1 */
			if (AppSecUtil.validateWebServiceInput(queryParams, null,
					methodConstants, OMDConstants.CUSTOMER_ID)) {
				userServiceVO = new UserServiceVO();
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					if (null != queryParams.getFirst(OMDConstants.CUSTOMER_ID)
							&& !queryParams.getFirst(OMDConstants.CUSTOMER_ID)
									.isEmpty())
						if(queryParams.getFirst(OMDConstants.CUSTOMER_ID).contains(RMDCommonConstants.COMMMA_SEPARATOR))
						{
							userServiceVO.setListCustomer(Arrays.asList(queryParams
									.getFirst(OMDConstants.CUSTOMER_ID).split(
											OMDConstants.COMMA)));
						}
						else
						{
							userServiceVO.setCustomerId(queryParams
									.getFirst(OMDConstants.CUSTOMER_ID));
						}
				}
				if (queryParams.containsKey(OMDConstants.DEFAULT_CUSTOMER)) {
					if (null != queryParams
							.getFirst(OMDConstants.DEFAULT_CUSTOMER)
							&& !queryParams.getFirst(
									OMDConstants.DEFAULT_CUSTOMER).isEmpty()) {
						userServiceVO.setDefaultCustomer(queryParams
								.getFirst(OMDConstants.DEFAULT_CUSTOMER));
					}

				}
				if (queryParams.containsKey(OMDConstants.USERTYPE)) {
					if (null != queryParams
							.getFirst(OMDConstants.USERTYPE)
							&& !queryParams.getFirst(
									OMDConstants.USERTYPE).isEmpty()) {
						userServiceVO.setUserType(queryParams
								.getFirst(OMDConstants.USERTYPE));
					}

				}
				/* Added in phase2 Sprint 1 */

				listUsers = objUserServiceIntf.getUsers(userServiceVO);
				if (RMDCommonUtility.isCollectionNotEmpty(listUsers)) {
					for (UserServiceVO userServiceVO2 : listUsers) {
						responseType = new UsersResponseType();
						CMBeanUtility.copyUserServiceVOToUsersResponseType(
								userServiceVO2, responseType);
						responseType.setUom(userServiceVO2.getUom());
						liResponseTypes.add(responseType);
					}
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return liResponseTypes;
	}

	/**
	 * This method is used for resetting the Password based on the Parameter
	 * 
	 * @param objUserRequestType
	 * @return
	 * @throws RMDServiceException
	 */
	@PUT
	@Path(OMDConstants.RESET_PASSWORD)
	@Consumes(MediaType.APPLICATION_XML)
	public void resetPassword(UserRequestType objUserRequestType)
			throws RMDServiceException {
		LOG.debug("Inside - resetPassword");
		UserDetailType objUserDetailType;
		PersonalDetailType objPersonalDetailType;
		Date convertedDate;
		final UserServiceVO objUserServiceVO = new UserServiceVO();
		try {
			if (null == objUserRequestType) {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			} else {
				objUserDetailType = objUserRequestType.getUserDetail();
				objPersonalDetailType = objUserRequestType.getPersonalDetail();

				if (null != objUserRequestType.getLastLoginDate()) {
					convertedDate = null;
					convertedDate = objUserRequestType.getLastLoginDate()
							.toGregorianCalendar().getTime();
					objUserServiceVO.setLastLoginDate(convertedDate);
				}
				if (null != objUserRequestType.getPasswordChangeDate()) {
					convertedDate = null;
					convertedDate = objUserRequestType.getPasswordChangeDate()
							.toGregorianCalendar().getTime();
					objUserServiceVO.setPasswordChgDate(convertedDate);
				}
				objUserServiceVO.setGetUsrUsersSeqId(objUserDetailType
						.getUserSeqId());
				objUserServiceVO.setHomePage(objUserDetailType.getHomePage());
				objUserServiceVO.setPassword(objUserDetailType.getPassword());
				objUserServiceVO.setGetUsrContactSedId(objPersonalDetailType
						.getUserContactSeqId());
				objUserServiceVO.setStatus(Long.parseLong(objUserDetailType
						.getStatus()));
				objUserServiceVO.setStrEmail(objPersonalDetailType.getEmail());
				objUserServiceVO.setStrFirstName(objPersonalDetailType
						.getFirstName());
				objUserServiceVO.setStrLastName(objPersonalDetailType
						.getLastName());
				objUserServiceVO.setStrRole(objUserRequestType.getRole());
				objUserServiceVO.setStrTimeZone(objUserRequestType
						.getTimeZone());
				objUserServiceVO.setStrUserLanguage(objUserRequestType
						.getLanguage());
				objUserServiceVO
						.setStrUserName(objUserDetailType.getUserName());
				objUserServiceVO.setUserId(objUserDetailType.getUserId());
				objUserServiceVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

				objUserServiceIntf.resetPassword(objUserServiceVO);
				LOG.debug("password successfully reset for a User : "
						+ objUserDetailType.getUserId());
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	}

	/**
	 * This method is used for creating/updating user for the inputs given
	 * 
	 * @param objUserRequestType
	 * @return
	 * @throws RMDServiceException
	 */
	@PUT
	@Path(OMDConstants.SAVE_USER)
	@Consumes(MediaType.APPLICATION_XML)
	public void saveUser(final UserRequestType objUserRequestType)
			throws RMDServiceException {

		PersonalDetailType objPersonalDetailType = new PersonalDetailType();
		UserDetailType objUserDetailType = new UserDetailType();
		final UserServiceVO objUserServiceVO = new UserServiceVO();
		Long lnUserSeqId;
		List arlUserRoles = null;
		try {
			if (null == objUserRequestType) {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			} else if (validateSaveUser(objUserRequestType)) {
				if (RMDCommonUtility.isCollectionNotEmpty(objUserRequestType
						.getRolesDetail())) {
					arlUserRoles = new ArrayList<Long>();
					for (RolesType role : objUserRequestType.getRolesDetail()) {
						arlUserRoles.add(role.getUserRolesSeqId());
						if (null != objUserRequestType.getDefaultRole()
								&& !RMDCommonConstants.EMPTY_STRING
										.equals(objUserRequestType
												.getDefaultRole())) {
							objUserServiceVO.setUsrRoleId(role
									.getUserRolesSeqId());
						}
					}
					objUserServiceVO.setRoleList(arlUserRoles);
				}
				if (null != objUserRequestType.getRolesChangeFlag()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objUserRequestType.getRolesChangeFlag())) {
					objUserServiceVO.setRolesChangeFlag(objUserRequestType
							.getRolesChangeFlag());
				}

				objPersonalDetailType = objUserRequestType.getPersonalDetail();
				objUserDetailType = objUserRequestType.getUserDetail();
				objUserServiceVO.setNewUserIdEntered(objUserDetailType
						.getNewUserIdEntered());

				BeanUtility.copyBeanProperty(objPersonalDetailType,
						objUserServiceVO);
				BeanUtility.copyBeanProperty(objUserDetailType,
						objUserServiceVO);
				BeanUtility.copyBeanProperty(objUserRequestType,
						objUserServiceVO);
				// Added by Murali Medicherla for Rally Id : US226051
				objUserServiceVO.setMobileAccess(objUserDetailType
						.getMobileAccess());
				objUserServiceVO.setEndUserScoring(objUserDetailType
						.getEndUserScoring());
				objUserServiceVO.setUsrRoleId(Long.valueOf(objUserRequestType
						.getRole()));
				objUserServiceVO.setUom(objUserRequestType.getUom());
				if (!OMDConstants.ALL_CUSTOMER.equals(objUserRequestType
						.getUserDetail().getCustomerId())) {
					objUserServiceVO.setCustomerId(objUserRequestType
							.getUserDetail().getCustomerId());
				}
				objUserServiceVO.setListCustomer(objUserRequestType
						.getUserDetail().getListCustomerIds());
				objUserServiceVO.setUserType(objUserRequestType.getUserDetail()
						.getUserType());
				objUserServiceVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
				objUserServiceVO
						.setStrUserName(getRequestHeader(OMDConstants.USERID));
				objUserServiceVO
						.setOldUserId(objUserRequestType.getOldUserId());
				// Added for emailer Changes
				objUserServiceVO.setStrEmail(ESAPI.encoder().decodeForHTML(
						objPersonalDetailType.getEmail()));
				/* Added for MDSC Admin changes */
				if (RMDCommonConstants.Y_LETTER_UPPER
						.equalsIgnoreCase(objUserRequestType.getUpdateEOAUser())
						&& (RMDCommonConstants.Y_LETTER_UPPER
								.equalsIgnoreCase(objUserRequestType
										.getOmdCMFlag())
								|| RMDCommonConstants.Y_LETTER_UPPER
										.equalsIgnoreCase(objUserRequestType
												.getOmdMLFlag())
								|| RMDCommonConstants.Y_LETTER_UPPER
										.equalsIgnoreCase(objUserRequestType
												.getOmdCmMlPrevRemoved()) || RMDCommonConstants.Y_LETTER_UPPER
									.equalsIgnoreCase(objUserRequestType
											.getOmdEmetricsFlag()))) {
					objUserServiceVO.setUpdateEOAUser(objUserRequestType
							.getUpdateEOAUser());
					objUserServiceVO.setEoaCMFlag(objUserRequestType
							.getEoaCMFlag());
					objUserServiceVO.setEoaMLFlag(objUserRequestType
							.getEoaMLFlag());
					objUserServiceVO.setEoaAlias(objUserRequestType
							.getEoaAlias());
					objUserServiceVO.setEoaMLVal(objUserRequestType
							.getEoaMLVal());
					objUserServiceVO.setOmdCMFlag(objUserRequestType
							.getOmdCMFlag());
					objUserServiceVO.setOmdMLFlag(objUserRequestType
							.getOmdMLFlag());
					objUserServiceVO.setOmdCmMlPrevRemoved(objUserRequestType
							.getOmdCmMlPrevRemoved());
					objUserServiceVO.setOmdMlAloneRemoved(objUserRequestType
							.getOmdMlAloneRemoved());
					objUserServiceVO.setEoaEmetricsFlag(objUserRequestType
							.getEoaEmetricsFlag());
					objUserServiceVO.setEoaEmetricsVal(objUserRequestType
							.getEoaEmetricsVal());
					objUserServiceVO.setOmdEmetricsFlag(objUserRequestType
							.getOmdEmetricsFlag());
					objUserServiceVO
							.setOmdEmetricsAloneRemoved(objUserRequestType
									.getOmdEmetricsAloneRemoved());
				}
				objUserServiceVO
				.setEoaOnsiteUser(objUserRequestType
						.getEoaOnsiteUser());
				/* End of MDSC Admin */
				lnUserSeqId = objUserServiceIntf.saveUserInfo(objUserServiceVO);
				LOG.debug("User created : " + lnUserSeqId);
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	}

	/**
	 * This method is used to delete the application parameter
	 * 
	 * @param objUserRequestType
	 * @return
	 * @throws RMDServiceException
	 */
	@DELETE
	@Path(OMDConstants.DELETE_APPL_PARAM_LOOKNAME)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void deleteApplicationParameter(
			@PathParam(OMDConstants.LOOKUP_NAME) String lookupName)
			throws RMDServiceException {
		GetSysLookupVO objGetSysLookupVO;
		try {
			MultivaluedMap<String, String> map = new MultivaluedMapImpl();
			map.add(OMDConstants.LOOKUP_NAME, lookupName);
			int[] methodConstants = { RMDCommonConstants.ALPHABETS };
			if (RMDCommonUtility.isNull(lookupName)
					&& !AppSecUtil.validateWebServiceInput(map, null,
							methodConstants, OMDConstants.LOOKUP_NAME)) {
				throw new OMDInValidInputException(
						BeanUtility
								.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			} else {
				objGetSysLookupVO = new GetSysLookupVO();
				objGetSysLookupVO.setListName(lookupName);
				objGetSysLookupVO
						.setStrUserName(getRequestHeader(OMDConstants.USERID));
				objGetSysLookupVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

				objPopAdminServiceIntf.deletePopupList(objGetSysLookupVO);
			}
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);
		}
	}

	/**
	 * This method is used to update userPreferences
	 * 
	 * @param objUserRequestType
	 * @return
	 * @throws RMDServiceException
	 */
	@POST
	@Path(OMDConstants.SAVE_USER_PREFERENCE)
	@Consumes(MediaType.APPLICATION_XML)
	public void saveUserPreferences(
			PreferencesRequestType preferencesRequestType)
			throws RMDServiceException {
		final List<GetUsrUserPreferenceVO> userPreferenceList = new ArrayList<GetUsrUserPreferenceVO>();
		List<PreferencesType> preferenceTypeLst = null;
		GetUsrUserPreferenceVO userPreferenceVO = null;
		final GetUsrUserPreferenceVO userPreferenceVOobj = new GetUsrUserPreferenceVO();
		preferenceTypeLst = preferencesRequestType.getPreferencesType();
		try {

			userPreferenceVOobj
					.setStrUserName(getRequestHeader(OMDConstants.USERID));

			userPreferenceVOobj.setCustomerChange(preferencesRequestType
					.getCustomerChangeFlag());
			userPreferenceVOobj.setCustomerId(preferencesRequestType
					.getCustomerId());
			userPreferenceVOobj
					.setUserId(preferencesRequestType.getUserSeqId());
			for (PreferencesType preferenceType : preferenceTypeLst) {

				userPreferenceVO = new GetUsrUserPreferenceVO();
				userPreferenceVO.setGetUsrUserPreferenceSeqId(preferenceType
						.getUserPreferenceSeqId());
				userPreferenceVO.setUserPreferernceType(preferenceType
						.getUserPreferernceType());
				userPreferenceVO.setUserPreferenceValue(preferenceType
						.getUserPreferenceValue());
				userPreferenceVO.setCustomerId(preferenceType.getCustomerId());
				userPreferenceVO.setUserId(preferenceType.getUserId());
				userPreferenceList.add(userPreferenceVO);
			}
			objUserServiceIntf.saveUserPreferences(userPreferenceList,
					userPreferenceVOobj);

		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	}

	@GET
	@Path(OMDConstants.GET_LOOKUP_VALUES_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getAllLookupValues()
			throws RMDBOException {

		List<GetSysLookupVO> lstSysLookupVO = null;
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		GetSysLookupVO getSysLookupVO = null;
		ApplicationParametersResponseType appParamResType = null;
		final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
		try {
			lstSysLookupVO = popupListAdminBOIntf.getAllPopupListValues();
			if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

				for (int i = 0; i < lstSysLookupVO.size(); i++) {
					appParamResType = new ApplicationParametersResponseType();
					getSysLookupVO = lstSysLookupVO.get(i);
					appParamResType.setSysLookupSeqId(getSysLookupVO
							.getGetSysLookupSeqId());
					appParamResType.setListName(getSysLookupVO.getListName());
					appParamResType.setListDescription(getSysLookupVO
							.getListDescription());
					appParamResType.setLookupValue(getSysLookupVO
							.getLookValue());
					appParamResType.setLookupState(getSysLookupVO
							.getLookState());
					appParamResType.setDisplayName(getSysLookupVO
							.getDisplayName());
					appParamResType.setSortOrder(getSysLookupVO.getSortOrder()
							.toString());
					appParamResType.setIsEditable(getSysLookupVO
							.getIsEditable());

					appParamResTypeLst.add(appParamResType);
				}
			}
			return appParamResTypeLst;
		} catch (RMDDAOException e) {
			throw e;
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							sysLookupVO.getStrLanguage()), e,
					RMDCommonConstants.MINOR_ERROR);
		}
	}

	@GET
	@Path(OMDConstants.GET_LOOKUP_VALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getLookupValues(
			@PathParam(OMDConstants.LOOKUP_NAME) final String lookupName)
			throws RMDBOException {

		List<GetSysLookupVO> lstSysLookupVO = null;
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		GetSysLookupVO getSysLookupVO = null;
		ApplicationParametersResponseType appParamResType = null;
		final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
		sysLookupVO.setListName(lookupName);
		try {
			MultivaluedMap<String, String> map = new MultivaluedMapImpl();
			map.add(OMDConstants.LOOKUP_NAME, lookupName);
			int[] methodConstants = { RMDCommonConstants.ALPHA_UNDERSCORE };
			if (AppSecUtil.validateWebServiceInput(map, null, methodConstants,
					OMDConstants.LOOKUP_NAME)) {
				lstSysLookupVO = popupListAdminBOIntf
						.getPopupListValues(sysLookupVO);
				if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

					for (int i = 0; i < lstSysLookupVO.size(); i++) {
						appParamResType = new ApplicationParametersResponseType();
						getSysLookupVO = lstSysLookupVO.get(i);
						appParamResType.setSysLookupSeqId(getSysLookupVO
								.getGetSysLookupSeqId());
						appParamResType.setListName(getSysLookupVO
								.getListName());
						appParamResType.setListDescription(getSysLookupVO
								.getListDescription());
						appParamResType.setLookupValue(getSysLookupVO
								.getLookValue());
						appParamResType.setLookupState(getSysLookupVO
								.getLookState());
						appParamResType.setDisplayName(getSysLookupVO
								.getDisplayName());
						appParamResType.setSortOrder(getSysLookupVO
								.getSortOrder().toString());
						appParamResType.setIsEditable(getSysLookupVO
								.getIsEditable());

						appParamResTypeLst.add(appParamResType);
					}
				}
				return appParamResTypeLst;
			} else {
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (RMDDAOException e) {
			throw e;
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							sysLookupVO.getStrLanguage()), e,
					RMDCommonConstants.MINOR_ERROR);
		}
	}

	/**
	 * This method is used for creating/updating inputs for favourite filter
	 * story
	 * 
	 * @param objSaveFavFilter
	 * @return
	 * @throws RMDServiceException
	 */
	@PUT
	@Path(OMDConstants.ADD_FILTER)
	@Consumes(MediaType.APPLICATION_XML)
	public void addFilter(final SaveFavFilterType objSaveFavFilter)
			throws RMDServiceException {

		final FavFilterServiceVO objFavFilterVO = new FavFilterServiceVO();
		int lnUserSeqId;
		try {
			if (null == objSaveFavFilter) {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			} else if (null == objSaveFavFilter.getLinkUsrRoleSeqId()
					|| objSaveFavFilter.getLinkUsrRoleSeqId().isEmpty()) {
				throw new OMDInValidInputException(
						OMDConstants.USR_ROLE_ID_NOT_FOUND);
			}

			else if (validateFavFilter(objSaveFavFilter)) {

				if (null != objSaveFavFilter.getFilterId()
						&& !objSaveFavFilter.getFilterId().isEmpty()) {

					objFavFilterVO.setFilterId(Long.valueOf(objSaveFavFilter
							.getFilterId()));
				}

				if (null != objSaveFavFilter.getLinkUsrRoleSeqId()
						&& !objSaveFavFilter.getLinkUsrRoleSeqId().isEmpty()) {

					objFavFilterVO.setLinkUsrRoleSeqId(Long
							.valueOf(objSaveFavFilter.getLinkUsrRoleSeqId()));
				}

				if (null != objSaveFavFilter.getScreenName()
						&& !objSaveFavFilter.getScreenName().isEmpty()) {

					objFavFilterVO.setScreenName(objSaveFavFilter
							.getScreenName().toUpperCase());
					objFavFilterVO.setScreenNameFilter(objSaveFavFilter
							.getLinkUsrRoleSeqId()
							+ RMDCommonConstants.UNDERSCORE
							+ objSaveFavFilter.getScreenName().toUpperCase()
							+ RMDCommonConstants.UNDERSCORE
							+ RMDCommonConstants.FAVOURITE_FILTER);
				}

				if (null != objSaveFavFilter.getColumnInfo()
						&& objSaveFavFilter.getColumnInfo().getColumnType()
								.size() > 0) {
					List<String> columnTypeLst = new ArrayList<String>();
					for (int i = 0; i < objSaveFavFilter.getColumnInfo()
							.getColumnName().size(); i++) {
						columnTypeLst.add(objSaveFavFilter.getColumnInfo()
								.getColumnType().get(i));
					}

					objFavFilterVO.setColumnType(columnTypeLst);
				}

				if (null != objSaveFavFilter.getColumnInfo()
						&& objSaveFavFilter.getColumnInfo().getColumnName()
								.size() > 0) {
					List<String> columnNameLst = new ArrayList<String>();
					for (int i = 0; i < objSaveFavFilter.getColumnInfo()
							.getColumnName().size(); i++) {
						columnNameLst.add(objSaveFavFilter.getColumnInfo()
								.getColumnName().get(i));
					}
					objFavFilterVO.setColumnName(columnNameLst);
				}

				if (null != objSaveFavFilter.getDataInfo()
						&& objSaveFavFilter.getDataInfo().getDataValue().size() > 0) {
					List<String> dataValueLst = new ArrayList<String>();
					for (int i = 0; i < objSaveFavFilter.getDataInfo()
							.getDataValue().size(); i++) {
						dataValueLst.add(objSaveFavFilter.getDataInfo()
								.getDataValue().get(i));
					}
					objFavFilterVO.setFilterData(dataValueLst);
				}

				objFavFilterVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
				objFavFilterVO
						.setStrUserName(getRequestHeader(OMDConstants.USERID));

				lnUserSeqId = objUserServiceIntf.saveFilterData(objFavFilterVO);
				LOG.debug("User created : " + lnUserSeqId);
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
	}

	/**
	 * This method is used to validate the data used for saving the user input
	 * UserRequestType
	 * 
	 * @return boolean
	 */
	public static boolean validateSaveUser(UserRequestType obj) {

		if (null != obj.getPersonalDetail().getFirstName()
				&& !obj.getPersonalDetail().getFirstName().isEmpty()) {
			if (RMDCommonUtility.isSpecialCharactersFound(obj
					.getPersonalDetail().getFirstName())) {
				return false;
			}
		}

		if (null != obj.getPersonalDetail().getLastName()
				&& !obj.getPersonalDetail().getLastName().isEmpty()) {
			if (RMDCommonUtility.isSpecialCharactersFound(obj
					.getPersonalDetail().getLastName())) {
				return false;
			}
		}

		if (null != obj.getRole() && !obj.getRole().isEmpty()) {
			if (RMDCommonUtility.isSpecialCharactersFound(obj.getRole()))
				return false;
			if (!AppSecUtil.checkIntNumber(obj.getRole())) {
				return false;
			}
		}

		if (null != obj.getUserDetail().getStatus()
				&& !obj.getUserDetail().getStatus().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(obj.getUserDetail().getStatus())) {
				return false;
			}
		}

		if (null != obj.getCustomerId() && !obj.getCustomerId().isEmpty()) {
			if (RMDCommonUtility.isSpecialCharactersFound(obj.getCustomerId())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method is used to delete favorite filter from database
	 * 
	 * @param ui
	 * @throws RMDServiceException
	 */
	@DELETE
	@Path(OMDConstants.DELETE_FAVORITE_FILTER)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void deleteFavoriteFilter(@Context final UriInfo ui)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		FavFilterServiceVO objFavFilterVO = null;
		String roleSeqId = null;
		try {
			queryParams = ui.getQueryParameters();
			objFavFilterVO = new FavFilterServiceVO();
			MultivaluedMap<String, String> map = new MultivaluedMapImpl();

			if (queryParams.containsKey(OMDConstants.FILTER_ID)) {
				if (null != queryParams.getFirst(OMDConstants.FILTER_ID)
						&& !queryParams.getFirst(OMDConstants.FILTER_ID)
								.isEmpty()) {
					objFavFilterVO.setFilterId(RMDCommonUtility
							.convertObjectToLong(queryParams
									.getFirst(OMDConstants.FILTER_ID)));
				} else {
					throw new OMDInValidInputException(
							OMDConstants.FILTERID_NOT_PROVIDED);
				}

			}
			if (queryParams.containsKey("usrRoleSeqId")) {
				if (null != queryParams.getFirst("usrRoleSeqId")
						&& !queryParams.getFirst("usrRoleSeqId").isEmpty()) {
					roleSeqId = queryParams.getFirst("usrRoleSeqId");
				}
			}
			if (queryParams.containsKey("filterName")) {
				if (null != queryParams.getFirst("filterName")
						&& !queryParams.getFirst("filterName").isEmpty()) {
					String filterName = queryParams.getFirst("filterName");
					objFavFilterVO.setScreenNameFilter(roleSeqId
							+ RMDCommonConstants.UNDERSCORE
							+ filterName.toUpperCase()
							+ RMDCommonConstants.UNDERSCORE
							+ RMDCommonConstants.FAVOURITE_FILTER);
				}
			}
			int[] methodConstants = { RMDCommonConstants.NUMERIC };
			if (null != objFavFilterVO.getFilterId()
					&& objFavFilterVO.getFilterId() != 0
					&& AppSecUtil.validateWebServiceInput(map, null,
							methodConstants, OMDConstants.FILTER_ID)) {

				objFavFilterVO
						.setStrUserName(getRequestHeader(OMDConstants.USERID));
				objFavFilterVO
						.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

				objUserServiceIntf.deleteFavoriteFilter(objFavFilterVO);
			} else {
				throw new OMDInValidInputException(
						BeanUtility
								.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}

		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {
			throw rmdServiceException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
	}

	/**
	 * This method is used to validate the data used for saving Favourite Filter
	 * SaveFavFilterType
	 * 
	 * @return boolean
	 */
	public static boolean validateFavFilter(SaveFavFilterType obj) {

		if (null != obj.getFilterId() && !obj.getFilterId().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(obj.getFilterId())) {
				return false;
			}
		}

		if (null != obj.getLinkUsrRoleSeqId()
				&& !obj.getLinkUsrRoleSeqId().isEmpty()) {
			if (!AppSecUtil.checkIntNumber(obj.getLinkUsrRoleSeqId())) {
				return false;
			}
		}

		if (null != obj.getScreenName() && !obj.getScreenName().isEmpty()) {
			if (!AppSecUtil.checkAlphaNumericUnderscore(obj.getScreenName())) {
				return false;
			}
		}

		if (null != obj.getColumnInfo()
				&& !obj.getColumnInfo().getColumnName().isEmpty()) {
			for (int i = 0; i < obj.getColumnInfo().getColumnName().size(); i++) {
				if (!AppSecUtil.checkAlphaUnderScore(obj.getColumnInfo()
						.getColumnName().get(i))) {
					return false;
				}
			}
		}

		if (null != obj.getColumnInfo()
				&& !obj.getColumnInfo().getColumnType().isEmpty()) {
			for (int i = 0; i < obj.getColumnInfo().getColumnType().size(); i++) {
				if (!AppSecUtil.checkAlphaUnderScore(obj.getColumnInfo()
						.getColumnType().get(i))) {
					return false;
				}
			}
		}

		if (null != obj.getDataInfo()
				&& !obj.getDataInfo().getDataValue().isEmpty()) {

			Object strDataArray[];
			List<Object[]> dataLst = new ArrayList<Object[]>();
			// Converting the comma String to String Array
			for (int i = 0; i < obj.getDataInfo().getDataValue().size(); i++) {
				strDataArray = RMDCommonUtility.splitString(obj.getDataInfo()
						.getDataValue().get(i),
						RMDCommonConstants.COMMMA_SEPARATOR);
				dataLst.add(strDataArray);

			}

			// code added to handle the RoadRange Filters(String appended with
			// hypen) for GeofenceRport
			if (RMDCommonConstants.GEOFENCE.equals(obj.getScreenName())) {
				if (null != dataLst) {
					for (int i = 0; i < dataLst.size(); i++) {
						for (int j = 0; j < dataLst.get(i).length; j++) {
							if (obj.getColumnInfo().getColumnType().get(i)
									.equalsIgnoreCase(RMDCommonConstants.TEXT)) {
								if (!AppSecUtil
										.checkAlphaNumericHypen(RMDCommonUtility
												.convertObjectToString(dataLst
														.get(i)[j]))) {
									return false;
								}
							}

							if (obj.getColumnInfo()
									.getColumnType()
									.get(i)
									.equalsIgnoreCase(
											RMDCommonConstants.DATE_TIME)) {
								if (!AppSecUtil
										.validateDate(
												RMDCommonUtility
														.convertObjectToString(dataLst
																.get(i)[j]),
												RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT)) {
									return false;
								}
							}

						}
					}

				}
			} else {
				if (null != dataLst) {
					for (int i = 0; i < dataLst.size(); i++) {
						for (int j = 0; j < dataLst.get(i).length; j++) {
							if (obj.getColumnInfo().getColumnType().get(i)
									.equalsIgnoreCase(RMDCommonConstants.TEXT)) {
								
								if (!AppSecUtil
										.checkAlphaNumericUnderscore(RMDCommonUtility
												.convertObjectToString(dataLst
														.get(i)[j]))) {
									return false;
								}
							}

							if (obj.getColumnInfo()
									.getColumnType()
									.get(i)
									.equalsIgnoreCase(RMDCommonConstants.NUMBER)) {
								if (!AppSecUtil
										.checkIntNumber(RMDCommonUtility
												.convertObjectToString(dataLst
														.get(i)[j]))) {
									return false;
								}
							}

							if (obj.getColumnInfo()
									.getColumnType()
									.get(i)
									.equalsIgnoreCase(
											RMDCommonConstants.DATE_TIME)) {
								if (!AppSecUtil
										.validateDate(
												RMDCommonUtility
														.convertObjectToString(dataLst
																.get(i)[j]),
												RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT)) {
									return false;
								}
							}

							if (obj.getColumnInfo()
									.getColumnType()
									.get(i)
									.equalsIgnoreCase(
											RMDCommonConstants.DECIMAL)) {
								if (!AppSecUtil
										.checkNumbersDot(RMDCommonUtility
												.convertObjectToString(dataLst
														.get(i)[j]))) {
									return false;
								}
							}
						}
					}

				}
			}
		}
		return true;
	}

	/**
	 * this service is used to fetch favorite filter data from database based on
	 * either filterId or userRoleSeqId(as query parameters)
	 * 
	 * @param ui
	 * @return
	 * @throws RMDBOException
	 */
	@GET
	@Path(OMDConstants.FETCH_FAVORITE_FILTER)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public FetchFavFilterResponseType fetchFavoriteFilter(
			@Context final UriInfo ui) throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		final FetchFavFilterResponseType fetchFilterRespType = new FetchFavFilterResponseType();
		final FavFilterServiceVO objFavFilterVO = new FavFilterServiceVO();
		GregorianCalendar objGregorianCalendar = null;
		XMLGregorianCalendar dtLastUpdatedDate = null;

		try {
			String strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
			objFavFilterVO.setStrLanguage(strLanguage);
			queryParams = ui.getQueryParameters();
			int[] methodConstants = { RMDCommonConstants.NUMERIC };

			if (false == queryParams.containsKey(OMDConstants.FILTER_ID)
					&& false == queryParams
							.containsKey(OMDConstants.USR_ROLE_SEQ_ID)) {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}

			if (queryParams.containsKey(OMDConstants.FILTER_ID)) {
				if (AppSecUtil.validateWebServiceInput(queryParams, null,
						methodConstants, OMDConstants.FILTER_ID)) {

					if (null != queryParams.getFirst(OMDConstants.FILTER_ID)
							&& !queryParams.getFirst(OMDConstants.FILTER_ID)
									.isEmpty()) {
						objFavFilterVO.setFilterId(RMDCommonUtility
								.convertObjectToLong(queryParams
										.getFirst(OMDConstants.FILTER_ID)));
					} else {
						throw new OMDInValidInputException(
								OMDConstants.FILTERID_NOT_PROVIDED);
					}
				} else {
					throw new OMDInValidInputException(
							OMDConstants.INVALID_VALUE);
				}
			}
			if (queryParams.containsKey(OMDConstants.USR_ROLE_SEQ_ID)) {
				if (AppSecUtil.validateWebServiceInput(queryParams, null,
						methodConstants, OMDConstants.USR_ROLE_SEQ_ID)) {
					if (null != queryParams
							.getFirst(OMDConstants.USR_ROLE_SEQ_ID)
							&& !queryParams.getFirst(
									OMDConstants.USR_ROLE_SEQ_ID).isEmpty()) {
						objFavFilterVO
								.setLinkUsrRoleSeqId(RMDCommonUtility.convertObjectToLong(queryParams
										.getFirst(OMDConstants.USR_ROLE_SEQ_ID)));
					} else {
						throw new OMDInValidInputException(
								OMDConstants.USR_ROLE_SEQID_NOT_PROVIDED);
					}
				} else {
					throw new OMDInValidInputException(
							OMDConstants.INVALID_VALUE);
				}
			}

			FetchFavFilterServiceVO fetchFilterVO = objUserServiceIntf
					.fetchFavoriteFilter(objFavFilterVO);
			if (null != fetchFilterVO) {

				List<FavFilterDetailServiceVO> favFilterDetailServiceVO = fetchFilterVO
						.getFilterDetail();

				FavFilterDetailType eachFavFilterDetailType = null;
				List<FilterDetailType> detailTypeList = null;
				FilterDetailType eachDetailType = null;
				List<FavFilterDetailType> filterDetailTypeList = new ArrayList<FavFilterDetailType>();
				List<FilterDetailVO> filterDetailVOList = null;

				if (RMDCommonUtility
						.isCollectionNotEmpty(favFilterDetailServiceVO)) {
					for (FavFilterDetailServiceVO eachDetailServiceVO : favFilterDetailServiceVO) {

						eachFavFilterDetailType = new FavFilterDetailType();
						eachFavFilterDetailType.setFilterId(eachDetailServiceVO
								.getFilterId());
						eachFavFilterDetailType
								.setFilterName(eachDetailServiceVO
										.getFilterName());

						filterDetailVOList = eachDetailServiceVO
								.getFilterDetail();
						detailTypeList = new ArrayList<FilterDetailType>();

						if (RMDCommonUtility
								.isCollectionNotEmpty(filterDetailVOList)) {

							for (FilterDetailVO detailVO : filterDetailVOList) {
								eachDetailType = new FilterDetailType();

								eachDetailType.setColumnName(detailVO
										.getColumnName());
								eachDetailType.setColumnType(detailVO
										.getColumnType());
								eachDetailType.setUsrFilterId(detailVO
										.getUsrFilterId());
								eachDetailType.setUsrFilterDetailId(detailVO
										.getUsrFilterDetailId());
								eachDetailType.setFromFilterValue(detailVO
										.getFromFilterValue());
								eachDetailType.setToFilterValue(detailVO
										.getToFilterValue());
								eachDetailType.setCreatedBy(detailVO
										.getCreatedBy());
								eachDetailType.setResourceId(detailVO
										.getResourceId());
								eachDetailType.setResourceName(detailVO
										.getResourceName());

								objGregorianCalendar = new GregorianCalendar();

								if (detailVO.getCreatedDate() != null) {
									objGregorianCalendar.setTime(detailVO
											.getCreatedDate());
									dtLastUpdatedDate = DatatypeFactory
											.newInstance()
											.newXMLGregorianCalendar(
													objGregorianCalendar);
									eachDetailType
											.setCreatedDate(dtLastUpdatedDate);
								}

								if (detailVO.getLastUpdatedDate() != null) {
									objGregorianCalendar.setTime(detailVO
											.getLastUpdatedDate());
									dtLastUpdatedDate = DatatypeFactory
											.newInstance()
											.newXMLGregorianCalendar(
													objGregorianCalendar);
									eachDetailType
											.setLastUpdatedDate(dtLastUpdatedDate);
								}

								eachDetailType.setLastUpdatedBy(detailVO
										.getLastUpdatedBy());
								detailTypeList.add(eachDetailType);
							}
						}
						eachFavFilterDetailType
								.setFilterDetails(detailTypeList);
						filterDetailTypeList.add(eachFavFilterDetailType);
					}

				}

				fetchFilterRespType.setUsrRoleSeqId(fetchFilterVO
						.getUsrRoleSeqId());
				fetchFilterRespType.setFilterDetail(filterDetailTypeList);
			}
		} catch (OMDInValidInputException objOMDInValidInputException) {
			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return fetchFilterRespType;
	}

	/**
	 * @Author:
	 * @param sysLookupVO
	 * @return
	 * @throws RMDServiceException
	 * @Description:This method is used for fetching the legends list for Map
	 */
	@GET
	@Path(OMDConstants.GET_MAP_LEGENDS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getMapLegends(
			@Context final UriInfo ui) throws RMDBOException {
		MultivaluedMap<String, String> queryParams = null;
		List<GetSysLookupVO> lstSysLookupVO = null;
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		GetSysLookupVO getSysLookupVO = null;
		ApplicationParametersResponseType appParamResType = null;
		final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
		String lookupName = RMDCommonConstants.EMPTY_STRING;
		try {
			queryParams = ui.getQueryParameters();
			String timeZone = getDefaultTimeZone();

			int[] methodConstants = { RMDCommonConstants.ALPHA_UNDERSCORE };

			if (AppSecUtil.validateWebServiceInput(queryParams, null,
					methodConstants, OMDConstants.LIST_NAME)) {

				lookupName = queryParams.getFirst(OMDConstants.LIST_NAME);
				sysLookupVO.setListName(lookupName);

				lstSysLookupVO = objPopAdminServiceIntf
						.getMapLegends(sysLookupVO);
				if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

					DatatypeFactory df = DatatypeFactory.newInstance();

					for (int i = 0; i < lstSysLookupVO.size(); i++) {
						appParamResType = new ApplicationParametersResponseType();
						getSysLookupVO = lstSysLookupVO.get(i);
						appParamResType.setSysLookupSeqId(getSysLookupVO
								.getGetSysLookupSeqId());
						appParamResType.setListName(getSysLookupVO
								.getListName());
						appParamResType.setListDescription(getSysLookupVO
								.getListDescription());
						appParamResType.setLookupValue(getSysLookupVO
								.getLookValue());
						appParamResType.setLookupState(getSysLookupVO
								.getLookState());
						appParamResType.setDisplayName(getSysLookupVO
								.getDisplayName());
						appParamResType.setSortOrder(getSysLookupVO
								.getSortOrder().toString());
						appParamResType.setIsEditable(getSysLookupVO
								.getIsEditable());
						/*
						 * added for map last refresh time phase 2 sprint 6 MISC
						 * change - Start
						 */
						GregorianCalendar gc = new GregorianCalendar();

						gc.setTimeInMillis(getSysLookupVO.getLastUpdatedDate()
								.getTime());
						RMDCommonUtility.setZoneOffsetTime(gc, timeZone);
						XMLGregorianCalendar lastUpdatedDate = df
								.newXMLGregorianCalendar(gc);
						appParamResType.setLastUpdatedDate(lastUpdatedDate);
						/*
						 * added for map last refresh time phase 2 sprint 6 MISC
						 * change - End
						 */
						appParamResTypeLst.add(appParamResType);
					}
				}
				return appParamResTypeLst;
			} else {
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (RMDDAOException e) {
			throw e;
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							sysLookupVO.getStrLanguage()), e,
					RMDCommonConstants.MINOR_ERROR);
		}
	}

	/**
	 * @Author:
	 * @param
	 * @return
	 * @throws RMDServiceException
	 * @Description:This method is used for fetching the System parameter values
	 *                   title and value
	 */

	@GET
	@Path(OMDConstants.GET_SYSTEM_PARAM_VALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<SystemParamResponseType> getAllSystemParamValues()
			throws RMDServiceException {

		List<GetSysParameterVO> objGetSysParameterVO = null;
		List<SystemParamResponseType> appParamResTypeLst = new ArrayList<SystemParamResponseType>();
		GetSysParameterVO getParameterVO = null;
		SystemParamResponseType appParamResType = null;
		try {

			objGetSysParameterVO = objpopupListAdminServiceIntf
					.getAllSystemParamValues();
			// populate values to response type
			if (RMDCommonUtility.isCollectionNotEmpty(objGetSysParameterVO)) {

				for (int i = 0; i < objGetSysParameterVO.size(); i++) {
					appParamResType = new SystemParamResponseType();
					getParameterVO = objGetSysParameterVO.get(i);
					appParamResType.setTitle(getParameterVO.getTitle());
					appParamResType.setValue(getParameterVO.getValue());
					appParamResTypeLst.add(appParamResType);

				}
			}

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return appParamResTypeLst;

	}

	/**
	 * This method is used for fetching Locations
	 * 
	 * @param ui
	 * @return List<LocationResponseType>
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_LOCATIONS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<LocationResponseType> getLocations(
			@Context final UriInfo uriParam) throws RMDServiceException {
		Iterator<FindLocationResultServiceVO> arlIterator;
		FindLocationServiceVO objFindLocationServiceVO;
		List<FindLocationResultServiceVO> arlLocation = null;
		final List<LocationResponseType> arlLocationDetails = new ArrayList<LocationResponseType>();
		FindLocationResultServiceVO objFindLocationResultServiceVO;
		LocationResponseType objLocationResponseType;
		AddressDetailType objAddressDetailType;
		try {

			final MultivaluedMap<String, String> queryParams = uriParam
					.getQueryParameters();

			objFindLocationServiceVO = new FindLocationServiceVO();
			objFindLocationServiceVO
					.setStrUserLanguage(getRequestHeader(OMDConstants.USERLANGUAGE));
			objFindLocationServiceVO
					.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));

			if (null != queryParams.getFirst(OMDConstants.LOCATION_ID)) {
				objFindLocationServiceVO.setStrLocationId(queryParams
						.getFirst(OMDConstants.LOCATION_ID));
			}
			if (null != queryParams.getFirst(OMDConstants.LOCATION_TYPE)) {
				objFindLocationServiceVO.setStrLocationType(queryParams
						.getFirst(OMDConstants.LOCATION_TYPE));
			}
			if (null != queryParams.getFirst(OMDConstants.LOCATION_NAME)) {
				objFindLocationServiceVO.setStrLocationName(queryParams
						.getFirst(OMDConstants.LOCATION_NAME));
			}
			if (null != queryParams.getFirst(OMDConstants.CUSTOMER_ID)) {
				objFindLocationServiceVO.setCustomerId(queryParams
						.getFirst(OMDConstants.CUSTOMER_ID));
			}
			if (null != queryParams.getFirst(OMDConstants.SITE_ADDRESS1)) {
				objFindLocationServiceVO.setStrSiteAddress1(queryParams
						.getFirst(OMDConstants.SITE_ADDRESS1));
			}
			if (null != queryParams.getFirst(OMDConstants.SITE_ADDRESS2)) {
				objFindLocationServiceVO.setStrSiteAddress2(queryParams
						.getFirst(OMDConstants.SITE_ADDRESS2));
			}
			if (null != queryParams.getFirst(OMDConstants.CITY)) {
				objFindLocationServiceVO.setStrCity(queryParams
						.getFirst(OMDConstants.CITY));
			}
			if (null != queryParams.getFirst(OMDConstants.STATE)) {
				objFindLocationServiceVO.setStrState(queryParams
						.getFirst(OMDConstants.STATE));
			}
			if (null != queryParams.getFirst(OMDConstants.COUNTRY)) {
				objFindLocationServiceVO.setStrCountry(queryParams
						.getFirst(OMDConstants.COUNTRY));
			}
			if (null != queryParams.getFirst(OMDConstants.ZIPCODE)) {
				objFindLocationServiceVO.setStrZipCode(queryParams
						.getFirst(OMDConstants.ZIPCODE));
			}
			if (null != queryParams.getFirst(OMDConstants.TIMEZONE)) {
				objFindLocationServiceVO.setStrTimeZone(queryParams
						.getFirst(OMDConstants.TIMEZONE));
			}
			if (null != queryParams.getFirst(OMDConstants.INCLUDE_INACTIVE)) {
				final boolean blnIncludeInactive = Boolean
						.parseBoolean(queryParams
								.getFirst(OMDConstants.INCLUDE_INACTIVE));
				objFindLocationServiceVO
						.setBlnIncludeInactive(blnIncludeInactive);
			}
			arlLocation = objLocationServiceIntf
					.findLocation(objFindLocationServiceVO);

			if (RMDCommonUtility.isCollectionNotEmpty(arlLocation)) {

				arlIterator = arlLocation.iterator();

				while (arlIterator.hasNext()) {

					objLocationResponseType = new LocationResponseType();
					objAddressDetailType = new AddressDetailType();
					objFindLocationResultServiceVO = arlIterator.next();
					CMBeanUtility.copyFndLocResltVOToAdresDetType(
							objFindLocationResultServiceVO,
							objAddressDetailType);
					CMBeanUtility.copyFndLocResltVOToLocResType(
							objFindLocationResultServiceVO,
							objLocationResponseType);
					objLocationResponseType
							.setAddressDetail(objAddressDetailType);
					arlLocationDetails.add(objLocationResponseType);
				}
			} /*
			 * else { LOG.debug("No Results found"); throw new
			 * OMDNoResultFoundException
			 * (BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
			 * omdResourceMessagesIntf.getMessage(
			 * BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
			 * new String[] {},
			 * BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE))); }
			 */
		} catch (OMDInValidInputException objOMDInValidInputException) {

			throw objOMDInValidInputException;
		} catch (OMDNoResultFoundException objOMDNoResultFoundException) {
			throw objOMDNoResultFoundException;
		} catch (RMDServiceException rmdServiceException) {

			throw rmdServiceException;
		} catch (Exception e) {
			RMDWebServiceErrorHandler.handleException(e,
					omdResourceMessagesIntf);

		}
		return arlLocationDetails;
	}

	@GET
	@Path(OMDConstants.GET_EOA_LOOKUP_VALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getEoaLookupValues(
			@PathParam(OMDConstants.LOOKUP_NAME) final String lookupName)
			throws RMDBOException {

		List<GetSysLookupVO> lstSysLookupVO = null;
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		GetSysLookupVO getSysLookupVO = null;
		ApplicationParametersResponseType appParamResType = null;
		final GetSysLookupVO sysLookupVO = new GetSysLookupVO();
		sysLookupVO.setListName(lookupName);
		try {
			MultivaluedMap<String, String> map = new MultivaluedMapImpl();
			map.add(OMDConstants.LOOKUP_NAME, lookupName);
			int[] methodConstants = { RMDCommonConstants.ALPHA_UNDERSCORE };
			if (AppSecUtil.validateWebServiceInput(map, null, methodConstants,
					OMDConstants.LOOKUP_NAME)) {
				lstSysLookupVO = objpopupListAdminServiceIntf
						.getPopupListValues(sysLookupVO);
				if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

					for (int i = 0; i < lstSysLookupVO.size(); i++) {
						appParamResType = new ApplicationParametersResponseType();
						getSysLookupVO = lstSysLookupVO.get(i);
						appParamResType.setSysLookupSeqId(getSysLookupVO
								.getGetSysLookupSeqId());
						appParamResType.setListName(getSysLookupVO
								.getListName());
						appParamResType.setListDescription(getSysLookupVO
								.getListDescription());
						appParamResType.setLookupValue(getSysLookupVO
								.getLookValue());
						appParamResType.setLookupState(getSysLookupVO
								.getLookState());
						appParamResType.setDisplayName(getSysLookupVO
								.getDisplayName());
						appParamResType.setSortOrder(getSysLookupVO
								.getSortOrder().toString());
						appParamResType.setIsEditable(getSysLookupVO
								.getIsEditable());

						appParamResTypeLst.add(appParamResType);
					}
				}
				return appParamResTypeLst;
			} else {
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (RMDDAOException e) {
			throw e;
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							sysLookupVO.getStrLanguage()), e,
					RMDCommonConstants.MINOR_ERROR);
		}
	}

	/**
	 * This method is used for fetching enabled user details with alert
	 * Subscription Privilage
	 * 
	 * @param ui
	 * @return List<UsersResponseType>
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_ALERT_SUB_USERS_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<UsersResponseType> getAlertUsersDetails(
			@Context final UriInfo uriParam) throws RMDServiceException {

		UserServiceVO userServiceVO = null;
		List<UserServiceVO> listUsers = null;
		UsersResponseType responseType = null;
		final List<UsersResponseType> liResponseTypes = new ArrayList<UsersResponseType>();
		try {

			final MultivaluedMap<String, String> queryParams = uriParam
					.getQueryParameters();

			int[] methodConstants = { RMDCommonConstants.ALPHABETS };
			/* Added in phase2 Sprint 1 */
			if (AppSecUtil.validateWebServiceInput(queryParams, null,
					methodConstants, OMDConstants.CUSTOMER_ID)) {
				userServiceVO = new UserServiceVO();
				if (queryParams.containsKey(OMDConstants.CUSTOMER_ID)) {
					if (null != queryParams.getFirst(OMDConstants.CUSTOMER_ID)
							&& !queryParams.getFirst(OMDConstants.CUSTOMER_ID)
									.isEmpty())
						userServiceVO.setListCustomer(Arrays.asList(queryParams
								.getFirst(OMDConstants.CUSTOMER_ID).split(
										OMDConstants.COMMA)));
					userServiceVO.setCustomerId(queryParams
							.getFirst(OMDConstants.CUSTOMER_ID));
				}
				if (queryParams.containsKey(OMDConstants.DEFAULT_CUSTOMER)) {
					if (null != queryParams
							.getFirst(OMDConstants.DEFAULT_CUSTOMER)
							&& !queryParams.getFirst(
									OMDConstants.DEFAULT_CUSTOMER).isEmpty()) {
						userServiceVO.setDefaultCustomer(queryParams
								.getFirst(OMDConstants.DEFAULT_CUSTOMER));
					}

				}
				/* Added in phase2 Sprint 1 */

				listUsers = objUserServiceIntf
						.getAlertUsersDetails(userServiceVO);
				if (RMDCommonUtility.isCollectionNotEmpty(listUsers)) {
					for (UserServiceVO userServiceVO2 : listUsers) {
						responseType = new UsersResponseType();
						CMBeanUtility.copyUserServiceVOToUsersResponseType(
								userServiceVO2, responseType);
						liResponseTypes.add(responseType);
					}
				}
			} else {
				throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return liResponseTypes;
	}

	/**
	 * This method is used for fetching user Privilage
	 * 
	 * @param ui
	 * @return List<PrivilegeDetailsType>
	 * @throws RMDServiceException
	 */
	@POST
	@Path(OMDConstants.GET_USER_COMPONENT_LIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<PrivilegeDetailsType> getUserComponentList(String userId)
			throws RMDServiceException {
		List<String> userComponentList = null;
		List<PrivilegeDetailsType> ComponentListResponse = new ArrayList<PrivilegeDetailsType>();
		PrivilegeDetailsType privilegeDetailsType = null;
		try {
			userComponentList = objUserServiceIntf.getUserComponentList(userId);
			for (String componentName : userComponentList) {
				privilegeDetailsType = new PrivilegeDetailsType();
				privilegeDetailsType.setDisplayName(componentName);
				ComponentListResponse.add(privilegeDetailsType);
			}
		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return ComponentListResponse;
	}

	/**
	 * This method is used for fetching customer specified urgency
	 * 
	 * @param ui
	 * @return List<ApplicationParametersResponseType>
	 * @throws RMDServiceException
	 */
	@GET
	@Path(OMDConstants.GET_CUSTOMER_BASED_URGENCY)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getCustomerBasedLookupValues(
			@Context final UriInfo uriParam) throws RMDBOException {

		List<GetSysLookupVO> lstUrgencyLookupVO = null;
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		GetSysLookupVO urgencyVO = null;
		ApplicationParametersResponseType appParamResType = null;
		MultivaluedMap<String, String> queryParams = null;
		try {
			queryParams = uriParam.getQueryParameters();
			String strCustomerId = queryParams
					.getFirst(OMDConstants.CUSTOMER_ID);
			int[] methodConstants = { RMDCommonConstants.ALPHA_NUMERIC_UNDERSCORE };
			if (AppSecUtil.validateWebServiceInput(queryParams, null,
					methodConstants, OMDConstants.CUSTOMER_ID)) {
				lstUrgencyLookupVO = popupListAdminBOIntf
						.getCustomerPopupListValues(strCustomerId);
				if (RMDCommonUtility.isCollectionNotEmpty(lstUrgencyLookupVO)) {

					for (int i = 0; i < lstUrgencyLookupVO.size(); i++) {
						appParamResType = new ApplicationParametersResponseType();
						urgencyVO = lstUrgencyLookupVO.get(i);
						appParamResType.setUrgency(urgencyVO.getUrgency());
						appParamResTypeLst.add(appParamResType);
					}
				}
				return appParamResTypeLst;
			} else {
				throw new OMDInValidInputException(
						BeanUtility.getErrorCode(OMDConstants.INVALID_VALUE),
						omdResourceMessagesIntf.getMessage(
								BeanUtility
										.getErrorCode(OMDConstants.INVALID_VALUE),
								new String[] {},
								BeanUtility
										.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
			}
		} catch (RMDDAOException e) {
			throw e;
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_CUSTOMER_POPUP_ADMIN);
			throw new RMDDAOException(
					errorCode,
					new String[] {},
					RMDCommonUtility
							.getMessage(
									errorCode,
									new String[] {},
									RMDServiceConstants.BO_EXCEPTION_CUSTOMER_POPUP_ADMIN),
					e, RMDCommonConstants.MINOR_ERROR);
		}
	}

	@GET
	@Path(OMDConstants.GET_SHOW_ALL)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getLookupValuesShowAll(
			@Context final UriInfo uriParam) throws RMDBOException {

		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		ApplicationParametersResponseType restype = null;
		try {

			final MultivaluedMap<String, String> queryParams = uriParam
					.getQueryParameters();
			if (queryParams != null) {
				GetSysLookupVO getSysLookupVO = new GetSysLookupVO();
				getSysLookupVO.setFilterSearchBy(queryParams
						.getFirst(OMDConstants.POUP_SEARCHBY));
				getSysLookupVO.setFilterOption(queryParams
						.getFirst(OMDConstants.POUP_CONDITION));
				getSysLookupVO.setFieldValue(queryParams
						.getFirst(OMDConstants.POUP_VALUE));

				List<GetSysLookupVO> lstSysLookupVO = objPopAdminServiceIntf
						.getLookupValuesShowAll(getSysLookupVO);
				if (null != lstSysLookupVO) {
					if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {
						for (GetSysLookupVO sysvo : lstSysLookupVO) {
							restype = new ApplicationParametersResponseType();
							restype.setListName(sysvo.getListName());
							restype.setListDescription(sysvo
									.getListDescription());
							// restype.setSysLookupSeqId(sysvo.getGetSysLookupSeqId());
							appParamResTypeLst.add(restype);
						}
					} /*
					 * else { throw new
					 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
					 * ); }
					 */
				} /*
				 * else { throw new
				 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
				 * ); }
				 */
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return appParamResTypeLst;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method updates the popup listName,description.
	 */
	@PUT
	@Path(OMDConstants.UPDATE_POPUP_LIST)
	@Consumes(MediaType.APPLICATION_XML)
	public void updatePopupList(final ApplicationParametersRequestType reqobj)
			throws RMDServiceException {
		GetSysLookupVO sysvo = null;

		try {
			if (null != reqobj) {
				sysvo = new GetSysLookupVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getUserName())) {
					sysvo.setStrUserName(reqobj.getUserName());
				}
				/*
				 * if (!RMDCommonUtility.isNullOrEmpty(Long.toString(reqobj.
				 * getSysLookupSeqId()))){
				 * sysvo.setGetSysLookupSeqId(reqobj.getSysLookupSeqId()); }
				 */

				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getListName())) {
					sysvo.setListName(reqobj.getListName());
				}

				if (!RMDCommonUtility
						.isNullOrEmpty(reqobj.getListDescription())) {
					sysvo.setListDescription(reqobj.getListDescription());
				}

				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getOldListName())) {
					sysvo.setOldListName(reqobj.getOldListName());
				}
			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			objPopAdminServiceIntf.updatePopupListNew(sysvo);

		} catch (Exception e) {
			LOG.error("error in updatePopupList IN AdminResource :" + e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method adds the popup listName,description.
	 */

	@POST
	@Path(OMDConstants.ADD_POPUP_LIST_VALUES)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String savePopupList(final ApplicationParametersRequestType reqobj)
			throws RMDServiceException {
		String uniquerecord = null;
		GetSysLookupVO sysvo = null;

		try {
			if (null != reqobj) {

				sysvo = new GetSysLookupVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getUserName())) {
					sysvo.setStrUserName(reqobj.getUserName());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getListName())) {
					sysvo.setListName(reqobj.getListName());
				}

				if (!RMDCommonUtility
						.isNullOrEmpty(reqobj.getListDescription())) {
					sysvo.setListDescription(reqobj.getListDescription());
				}
			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			uniquerecord = objPopAdminServiceIntf.savePopupListNew(sysvo);

		} catch (Exception ex) {
			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}

		return uniquerecord;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method delete the popup listName,description.
	 */
	@PUT
	@Path(OMDConstants.DELETE_POPUP_LIST)
	@Consumes(MediaType.APPLICATION_XML)
	public void deletePopUpList(final ApplicationParametersRequestType reqobj)
			throws RMDServiceException {
		GetSysLookupVO sysvo = null;

		try {
			if (null != reqobj) {
				sysvo = new GetSysLookupVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getUserName())) {
					sysvo.setStrUserName(reqobj.getUserName());
				}
				/*
				 * if (!RMDCommonUtility.isNullOrEmpty(Long.toString(reqobj.
				 * getSysLookupSeqId()))){
				 * sysvo.setGetSysLookupSeqId(reqobj.getSysLookupSeqId()); }
				 */
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getListName())) {
					sysvo.setListName(reqobj.getListName());
				}
			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			objPopAdminServiceIntf.deletePopupListNew(sysvo);

		} catch (Exception e) {
			LOG.error("error in deletePopUpList IN AdminResource :" + e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method updates the popup listName,description.
	 */
	@POST
	@Path(OMDConstants.UPDATE_POPUP_LIST_VALUE)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String updatePopupListValues(
			final ApplicationParametersRequestType objRowList)
			throws RMDServiceException {

		String count = OMDConstants.FAIL;
		try {
			GetSysLookupVO sysvo = null;
			List<GetSysLookupVO> sysLookVOLst = new ArrayList<GetSysLookupVO>();
			List<ApplicationParametersRequestType> requesTypeList = objRowList
					.getListpopupdetails();
			if (requesTypeList != null) {
				for (ApplicationParametersRequestType reqType : requesTypeList) {
					sysvo = new GetSysLookupVO();
					sysvo.setGetSysLookupSeqId(reqType.getSysLookupSeqId());
					sysvo.setLookValue(reqType.getLookupValue());
					sysvo.setLookState(reqType.getLookupState());
					sysvo.setSortOrder(reqType.getSortOrder());
					sysvo.setStrUserName(reqType.getUserName());
					sysvo.setLookValueDesc(reqType.getLookValueDesc());
					sysLookVOLst.add(sysvo);
				}

			}
			count = objPopAdminServiceIntf.updatePopupListValues(sysLookVOLst);
		}

		catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return count;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method adds the popup listName,description.
	 */
	@POST
	@Path(OMDConstants.ADD_POPUP_LOOK_VALUES)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String savePopupListlookvalue(
			final ApplicationParametersRequestType reqobj)
			throws RMDServiceException {
		String uniquerecord = null;
		GetSysLookupVO sysvo = null;

		try {
			if (null != reqobj) {
				sysvo = new GetSysLookupVO();
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getUserName())) {
					sysvo.setStrUserName(reqobj.getUserName());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getLookupValue())) {
					sysvo.setLookValue(reqobj.getLookupValue());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getListName())) {
					sysvo.setListName(reqobj.getListName());
				}

				if (!RMDCommonUtility
						.isNullOrEmpty(reqobj.getListDescription())) {
					sysvo.setListDescription(reqobj.getListDescription());
				}
				if (!RMDCommonUtility
						.isNullOrEmpty(reqobj.getLookValueDesc())) {
					sysvo.setLookValueDesc(reqobj.getLookValueDesc());
				}

			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			uniquerecord = objPopAdminServiceIntf.savePopupListlookvalue(sysvo);

		} catch (Exception e) {
			LOG.error("error in savePopupList IN AdminResource :" + e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}
		return uniquerecord;

	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method updates the popup listName,description.
	 */
	@PUT
	@Path(OMDConstants.REMOVE_POPUP_LOOK_VALUES)
	@Consumes(MediaType.APPLICATION_XML)
	public void removePopupListlookvalue(
			final ApplicationParametersRequestType reqobj)
			throws RMDServiceException {
		GetSysLookupVO sysvo = null;

		try {
			if (null != reqobj) {
				sysvo = new GetSysLookupVO();
				if (!RMDCommonUtility.isNullOrEmpty(Long.toString(reqobj
						.getSysLookupSeqId()))) {
					sysvo.setGetSysLookupSeqId(reqobj.getSysLookupSeqId());
				}
				if (!RMDCommonUtility.isNullOrEmpty(reqobj.getListName())) {
					sysvo.setListName(reqobj.getListName());
				}
				if (reqobj.getSortOrder()!=null) {
					sysvo.setSortOrder(reqobj.getSortOrder());
				}
			} else {
				throw new OMDInValidInputException(
						OMDConstants.GETTING_NULL_REQUEST_OBJECT);
			}
			objPopAdminServiceIntf.removePopupListlookvalue(sysvo);

		} catch (Exception e) {
			LOG.error("error in updatePopupList IN AdminResource :" + e);
			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		}

	}

	/**
	 * @Author :
	 * @return :String
	 * @param :GetSysLookupVO
	 * @throws :RMDWebException
	 * @Description: This method adds the popup listName,description.
	 */
	@GET
	@Path(OMDConstants.GET_POPUP_LIST_VALUES)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<ApplicationParametersResponseType> getPopupListValues(
			@Context final UriInfo uriParam) throws RMDBOException {
		List<ApplicationParametersResponseType> appParamResTypeLst = new ArrayList<ApplicationParametersResponseType>();
		ApplicationParametersResponseType restype = null;
		try {

			final MultivaluedMap<String, String> queryParams = uriParam
					.getQueryParameters();
			if (queryParams != null) {
				GetSysLookupVO getSysLookupVO = new GetSysLookupVO();
				getSysLookupVO.setListName(queryParams
						.getFirst(OMDConstants.POUP_LIST_NAME));

				List<GetSysLookupVO> lstSysLookupVO = objPopAdminServiceIntf
						.getPopupListValues(getSysLookupVO);
				if (null != lstSysLookupVO) {
					if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {
						for (GetSysLookupVO sysvo : lstSysLookupVO) {
							restype = new ApplicationParametersResponseType();
							restype.setSysLookupSeqId(sysvo
									.getGetSysLookupSeqId());
							restype.setListName(sysvo.getListName());
							restype.setLookupValue(sysvo.getLookValue());
							restype.setListDescription(sysvo
									.getListDescription());
							restype.setLookupState(sysvo.getLookState());
							Long sortOrderstring = sysvo.getSortOrder();
							String sortOrder = Long.toString(sortOrderstring);
							restype.setSortOrder(sortOrder);
							restype.setLookValueDesc(sysvo.getLookValueDesc());
							appParamResTypeLst.add(restype);
						}
					}/*
					 * else { throw new
					 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
					 * ); }
					 */
				} /*
				 * else { throw new
				 * OMDNoResultFoundException(OMDConstants.NORECORDFOUNDEXCEPTION
				 * ); }
				 */
			}
		} catch (Exception ex) {

			RMDWebServiceErrorHandler.handleException(ex,
					omdResourceMessagesIntf);
		}
		return appParamResTypeLst;
	}

	/**
	 * @Author:
	 * @param : UriInfo ui
	 * @return:UserEOADetailsResponseType
	 * @throws:RMDServiceException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	@GET
	@Path(OMDConstants.GET_CM_ML_DETAILS)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public UserEOADetailsResponseType getCMorMLDetails(@Context UriInfo ui)
			throws RMDServiceException {
		MultivaluedMap<String, String> queryParams = null;
		UserEOADetailsResponseType objUserEOADetailsResponseType = null;
		UserEOADetailsVO objUserEOADetailsVO;
		try {
			queryParams = ui.getQueryParameters();
			String roleId = OMDConstants.EMPTY_STRING;
			String userId = OMDConstants.EMPTY_STRING;
			String removedRoleId = OMDConstants.EMPTY_STRING;
			if (queryParams.containsKey(OMDConstants.ROLE_ID)) {
				roleId = queryParams.getFirst(OMDConstants.ROLE_ID);
			}
			if (queryParams.containsKey(OMDConstants.USER_ID)) {
				userId = queryParams.getFirst(OMDConstants.USER_ID);
			}
			if (queryParams.containsKey(OMDConstants.REMOVED_ROLE_ID)) {
				removedRoleId = queryParams
						.getFirst(OMDConstants.REMOVED_ROLE_ID);
			}
			if (RMDCommonUtility.isNullOrEmpty(roleId)
					&& RMDCommonUtility.isNullOrEmpty(removedRoleId)) {
				throw new OMDInValidInputException(
						OMDConstants.ROLE_ID_NOT_PROVIDED);
			}
			if (RMDCommonUtility.isNullOrEmpty(userId)) {
				throw new OMDInValidInputException(
						OMDConstants.USER_ID_NOT_PROVIDED);
			}
			objUserEOADetailsVO = objUserServiceIntf.getCMorMLDetails(roleId,
					userId, removedRoleId);
			if (null != objUserEOADetailsVO) {
				objUserEOADetailsResponseType = new UserEOADetailsResponseType();
				objUserEOADetailsResponseType.setUserId(objUserEOADetailsVO
						.getUserId());
				objUserEOADetailsResponseType.setFirstName(objUserEOADetailsVO
						.getFirstName());
				objUserEOADetailsResponseType.setLastName(objUserEOADetailsVO
						.getLastName());
				objUserEOADetailsResponseType.setEmailId(objUserEOADetailsVO
						.getEmailId());
				objUserEOADetailsResponseType.setAliasName(objUserEOADetailsVO
						.getAliasName());
				objUserEOADetailsResponseType.setEoaMLVal(objUserEOADetailsVO
						.getEoaMLVal());
				objUserEOADetailsResponseType
						.setOmdCMPrevFlag(objUserEOADetailsVO.isOmdCMPrevFlag());
				objUserEOADetailsResponseType
						.setOmdMLPrevFlag(objUserEOADetailsVO.isOmdMLPrevFlag());
				objUserEOADetailsResponseType
						.setEoaCMPrevFlag(objUserEOADetailsVO.isEoaCMPrevFlag());
				objUserEOADetailsResponseType
						.setEoaMLPrevFlag(objUserEOADetailsVO.isEoaMLPrevFlag());
				objUserEOADetailsResponseType
						.setBlnOMDCmMlPreRemoved(objUserEOADetailsVO
								.isBlnOMDCmMlPreRemoved());
				objUserEOADetailsResponseType
						.setBlnOMDMlAloneRemoved(objUserEOADetailsVO
								.isBlnOMDMlAloneRemoved());
				objUserEOADetailsResponseType
						.setEoaEmetricsPrevFlag(objUserEOADetailsVO
								.isEoaEmetricsPrevFlag());
				objUserEOADetailsResponseType
						.setEoaEmetricsVal(objUserEOADetailsVO
								.getEoaEmetricsVal());
				objUserEOADetailsResponseType
						.setBlnOMDEmetricsAloneRemoved(objUserEOADetailsVO
								.isBlnOMDEmetricsAloneRemoved());
				objUserEOADetailsResponseType
						.setOmdEmetricsPrevFlag(objUserEOADetailsVO
								.isOmdEmetricsPrevFlag());
				objUserEOADetailsResponseType
						.setBlnOMDCmMlEmetricsPreRemoved(objUserEOADetailsVO
								.isBlnOMDCmMlEmetricsPreRemoved());
				objUserEOADetailsResponseType.setErrorMsg(objUserEOADetailsVO
						.getErrorMsg());
				objUserEOADetailsResponseType
						.setEndUserScoring(objUserEOADetailsVO
								.getEndUserScoring());
			}

		} catch (Exception e) {
			LOG.error("Exception occuered in getCMorMLDetails() method of AdminResource"
					+ e);

			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		} finally {
			queryParams = null;
			objUserEOADetailsVO = null;
		}

		return objUserEOADetailsResponseType;
	}

	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for checking whether the entered
	 *                   aliasName is exist or not
	 */
	@GET
	@Path(OMDConstants.CHECK_EOA_ALIAS_EXIST)
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String checkEOAAliasExist(@Context UriInfo ui)
			throws RMDServiceException {
		String result = null;
		MultivaluedMap<String, String> queryParams = null;
		String aliasName = OMDConstants.EMPTY_STRING;
		try {
			queryParams = ui.getQueryParameters();

			if (queryParams.containsKey(OMDConstants.EOA_ALIAS)) {
				aliasName = queryParams.getFirst(OMDConstants.EOA_ALIAS);
			} else {
				throw new OMDInValidInputException(
						OMDConstants.EOA_ALIAS_VALUE_NOT_PROVIDED);
			}
			result = objUserServiceIntf.checkEOAAliasExist(aliasName);
		} catch (Exception e) {
			LOG.error("Exception occuered in checkEOAAliasExist() method of AdminResource"
					+ e);

			throw new OMDApplicationException(
					BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
					omdResourceMessagesIntf.getMessage(BeanUtility
							.getErrorCode(OMDConstants.GENERALEXCEPTION),
							new String[] {}, BeanUtility
									.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
		} finally {
			queryParams = null;
		}
		return result;
	}
	
	@GET
	@Path(OMDConstants.GET_NO_CM_ROLES)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<RolesResponseType> getNoCMRoles(){
	    List<RolesResponseType> arlRolesResponseType=null;
	    List<RolesVO> arRolesVO=null;
	    RolesResponseType objRolesResponseType=null;
	    try{
	        arRolesVO= objUserServiceIntf.getNoCMRoles();
	        if(RMDCommonUtility.isCollectionNotEmpty(arRolesVO)){
	            arlRolesResponseType=new ArrayList<RolesResponseType>(arRolesVO.size());
	            for(RolesVO rolesVO:arRolesVO){
	                objRolesResponseType=new RolesResponseType();
	                objRolesResponseType.setRoleSeqId(rolesVO.getGetUsrRolesSeqId());
	                objRolesResponseType.setRoleName(rolesVO.getRoleName());
	                arlRolesResponseType.add(objRolesResponseType);
	            }
	        }
	    }catch (Exception e) {
            LOG.error("Exception occuered in getNoCMRoles() method of AdminResource"
                    + e);

            throw new OMDApplicationException(
                    BeanUtility.getErrorCode(OMDConstants.GENERALEXCEPTION),
                    omdResourceMessagesIntf.getMessage(BeanUtility
                            .getErrorCode(OMDConstants.GENERALEXCEPTION),
                            new String[] {}, BeanUtility
                                    .getLocale(OMDConstants.DEFAULT_LANGUAGE)));
        }
        return arlRolesResponseType;
	    
	}
	
	
	@POST
	@Path(OMDConstants.ADMIN_DELETE_USER_DETAILS)
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public String deleteUsers(UserDeleteRequestType userDeleteRequestType){
		List<String> userdetails =userDeleteRequestType.getUserIds();
		String str = null;
	    try{
	        str= objUserServiceIntf.deleteUsers(userdetails);
	       
	    }catch (Exception e) {
            LOG.error("Exception occuered in getNoCMRoles() method of AdminResource"
                    + e);

           
        }
        return str;
	    
	}
}
