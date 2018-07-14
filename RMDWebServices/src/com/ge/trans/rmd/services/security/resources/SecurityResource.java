/**
 * ============================================================
 * Classification: GE Confidential
 * File : SecurityResource.java
 * Description : 
 * Package : com.ge.trans.omd.services.security.resources
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Aug 3 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.security.resources;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.service.intf.RolesServiceIntf;
import com.ge.trans.eoa.services.common.valueobjects.GetCmContactInfoVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.intf.AuthenticatorServiceIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleCommonServiceIntf;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.exception.OMDInValidInputException;
import com.ge.trans.rmd.common.exception.OMDNoResultFoundException;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.resources.BaseResource;
import com.ge.trans.rmd.common.util.BeanUtility;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.admin.valueobjects.UserRequestType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolePrivilegesType;
import com.ge.trans.rmd.services.security.valueobjects.LoginResponse;
import com.ge.trans.rmd.services.security.valueobjects.PersonalDetailType;
import com.ge.trans.rmd.services.security.valueobjects.PreferencesType;
import com.ge.trans.rmd.services.security.valueobjects.RolesType;
import com.ge.trans.rmd.services.security.valueobjects.UserDetailType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.tools.keys.util.AppConstants;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Aug 3 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as SecurityResource Webservices and provide the login related
 *              funtionalities
 * @History :
 ******************************************************************************/
@Path(OMDConstants.SECURITYSERVICE)
@Component
public class SecurityResource extends BaseResource {
    public static final RMDLogger securityResourceLOG = RMDLoggerHelper.getLogger(SecurityResource.class);

    @Autowired
    AuthenticatorServiceIntf objAuthenticatorServiceIntf;
    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    RolesServiceIntf objRolesServiceIntf;
    @Autowired
    RuleCommonServiceIntf objRuleCommonServiceIntf;

    /**
     * This method is used for authenticating the user based on the credential passed in path
     * parameter
     * 
     * @param userIdValue
     * @param password
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.AUTHENTICATEUSER_USERID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public LoginResponse authenticateUser(@PathParam(OMDConstants.USERIDVALUE) final String userIdValue)
            throws RMDServiceException {
        LoginResponse objLoginResponse = new LoginResponse();
        try {
            UserDetailType objUserDetailType = new UserDetailType();
            LoginRequestServiceVO objLoginRequestServiceVO = new LoginRequestServiceVO();
            if (userIdValue != null) {
                objUserDetailType.setUserId(userIdValue);
                objLoginRequestServiceVO.setStrUserName(userIdValue);
                GetUsrUsersVO objGetUsrUsersVO = objAuthenticatorServiceIntf.authenticateUser(objLoginRequestServiceVO);
                objUserDetailType = new UserDetailType();
                PersonalDetailType objPersonalDetailType = new PersonalDetailType();
                if (null == objGetUsrUsersVO) {
                    LOG.info("No Record found for the given search criteria in authenticate method");
                } else {
                    GetCmContactInfoVO getCmContactInfo = objGetUsrUsersVO.getGetCmContactInfo();
                    // Customer Filter story - sets the customer associated
                    // with the user and added CustomerName for
                    // Miscellaneous changes across the screens story
                    objUserDetailType.setCustomerId(objGetUsrUsersVO.getCustomerId());
                    objUserDetailType.setCustomerName(objGetUsrUsersVO.getCustomerName());
                    objUserDetailType.setUserId(objGetUsrUsersVO.getUserId());
                    objUserDetailType.setStatus(String.valueOf(objGetUsrUsersVO.getStatus()));
                    objUserDetailType.setUserSeqId(objGetUsrUsersVO.getGetUsrUsersSeqId());
                    objUserDetailType.setUserType(objGetUsrUsersVO.getUserType());
                    objUserDetailType.setLastLoginDateTime(objGetUsrUsersVO.getLastLoginDateTime());
                    objUserDetailType.setLastLoginFrom(objGetUsrUsersVO.getLastLoginFrom());
                    //Added by Murali Medicherla for Rally Id : US226051
                    objUserDetailType.setMobileAccess(objGetUsrUsersVO.getMobileAccess() == null?"N":objGetUsrUsersVO.getMobileAccess());
                    objUserDetailType.setEndUserScoring(objGetUsrUsersVO.getEndUserScoring());
                    objPersonalDetailType.setUserContactSeqId(RMDCommonUtility.convertObjectToLong(getCmContactInfo.getGetCmContactInfoSeqId()));
                    objPersonalDetailType.setFirstName(getCmContactInfo.getFirstName());
                    objPersonalDetailType.setLastName(getCmContactInfo.getLastName());
                    objPersonalDetailType.setEmail(getCmContactInfo.getEMail());
                    objLoginResponse.setUserdetail(objUserDetailType);
                    objLoginResponse.setPersonalDetailType(objPersonalDetailType);
                    GregorianCalendar objGregorianCalendar = new GregorianCalendar();
                    if (objGetUsrUsersVO.getLastLoginDate() != null) {
                        objGregorianCalendar.setTime(objGetUsrUsersVO.getLastLoginDate());
                        objLoginResponse.setLastLoginDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar));
                    }
                    if (objGetUsrUsersVO.getPasswordChgDate() != null) {
                        objGregorianCalendar.setTime(objGetUsrUsersVO.getPasswordChgDate());
                        objLoginResponse.setPasswordChangeDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(objGregorianCalendar));
                    }
                    // Below code added for RMD changes - START
                    if (objGetUsrUsersVO.getGetUsrUserRoles() != null) {
                        List<RolesVO> rolesVOList = objGetUsrUsersVO.getGetUsrUserRoles();
                        List<RolesType> rolesTypeLst = null;
                        if (null != rolesVOList && !rolesVOList.isEmpty()) {
                            rolesTypeLst = new ArrayList<RolesType>();
                            for (RolesVO rolesVO : rolesVOList) {
                                RolesType rolesType = new RolesType();
                                rolesType.setUserRolesSeqId(RMDCommonUtility.convertObjectToLong(rolesVO.getGetUsrRolesSeqId()));
                                rolesType.setLinkUsrRoleSeqId(RMDCommonUtility.convertObjectToLong(rolesVO.getLinkUsrRoleSeqId()));
                                rolesType.setRoleName(rolesVO.getRoleName());
                                rolesType.setRoleDesc(rolesVO.getRoleDesc());
                                rolesTypeLst.add(rolesType);
                            }
                        }
                        objLoginResponse.setRolesType(rolesTypeLst);
                    }
                    List<PreferencesType> preferencesTypeLst = getUserPreference(objLoginRequestServiceVO);
                    if (preferencesTypeLst != null)
                        objLoginResponse.setPreferencesType(preferencesTypeLst);
                }

            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.USERID_PASSWORD_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.USERID_PASSWORD_NOT_PROVIDED), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
             RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return objLoginResponse;
    }
    
    /**
     * This method is used for authenticating the user based on the credential passed in path
     * parameter
     * 
     * @param userIdValue
     * @param password
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.AUTHENTICATEUSER_USERID_MOB)
    @Produces({MediaType.APPLICATION_JSON})
    public LoginResponse authenticateUserMob(@PathParam(OMDConstants.USERIDVALUE) final String userIdValue,
            @PathParam(OMDConstants.LASTLOGINFROM) final String lastLoginFrom) throws RMDServiceException {
        LoginResponse objLoginResponse = new LoginResponse();
        String lastLoginFromLoc = OMDConstants.EMPTY_STRING;
        String userSeqId =null;

        try {
            UserDetailType objUserDetailType = new UserDetailType();
            LoginRequestServiceVO objLoginRequestServiceVO = new LoginRequestServiceVO();
            if (userIdValue != null) {
                objUserDetailType.setUserId(userIdValue);
                objLoginRequestServiceVO.setStrUserName(userIdValue);
                GetUsrUsersVO objGetUsrUsersVO = objAuthenticatorServiceIntf.authenticateUser(objLoginRequestServiceVO);
                objUserDetailType = new UserDetailType();
                PersonalDetailType objPersonalDetailType = new PersonalDetailType();
                if (null == objGetUsrUsersVO) {
                    securityResourceLOG.info("No Record found for the given search criteria in authenticate method");
                } else {
                    userSeqId = objGetUsrUsersVO.getGetUsrUsersSeqId().toString();
                    GetCmContactInfoVO getCmContactInfo = objGetUsrUsersVO.getGetCmContactInfo();
                    // Customer Filter story - sets the customer associated
                    // with the user and added CustomerName for
                    // Miscellaneous changes across the screens story
                    objUserDetailType.setCustomerId(objGetUsrUsersVO.getCustomerId());
                    objUserDetailType.setCustomerName(objGetUsrUsersVO.getCustomerName());
                    objUserDetailType.setUserId(objGetUsrUsersVO.getUserId());
                    objUserDetailType.setStatus(String.valueOf(objGetUsrUsersVO.getStatus()));
                    objUserDetailType.setUserSeqId(objGetUsrUsersVO.getGetUsrUsersSeqId());
                    objUserDetailType.setUserType(objGetUsrUsersVO.getUserType());
                    objUserDetailType.setLastLoginDateTime(objGetUsrUsersVO.getLastLoginDateTime());
                    objUserDetailType.setLastLoginFrom(objGetUsrUsersVO.getLastLoginFrom());
                    // Added by Murali Medicherla for Rally Id : US226051
                    objUserDetailType.setMobileAccess(objGetUsrUsersVO.getMobileAccess() == null
                            ? "N"
                            : objGetUsrUsersVO.getMobileAccess());
                    objPersonalDetailType.setUserContactSeqId(RMDCommonUtility.convertObjectToLong(getCmContactInfo
                            .getGetCmContactInfoSeqId()));
                    objPersonalDetailType.setFirstName(getCmContactInfo.getFirstName());
                    objPersonalDetailType.setLastName(getCmContactInfo.getLastName());
                    objPersonalDetailType.setEmail(getCmContactInfo.getEMail());
                    objLoginResponse.setUserdetail(objUserDetailType);
                    objLoginResponse.setPersonalDetailType(objPersonalDetailType);
                    GregorianCalendar objGregorianCalendar = new GregorianCalendar();
                    if (objGetUsrUsersVO.getLastLoginDate() != null) {
                        objGregorianCalendar.setTime(objGetUsrUsersVO.getLastLoginDate());
                        objLoginResponse.setLastLoginDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                                objGregorianCalendar));
                    }
                    if (objGetUsrUsersVO.getPasswordChgDate() != null) {
                        objGregorianCalendar.setTime(objGetUsrUsersVO.getPasswordChgDate());
                        objLoginResponse.setPasswordChangeDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(
                                objGregorianCalendar));
                    }
                    // Below code added for RMD changes - START
                    if (objGetUsrUsersVO.getGetUsrUserRoles() != null) {
                        List<RolesVO> rolesVOList = objGetUsrUsersVO.getGetUsrUserRoles();
                        List<RolesType> rolesTypeLst = null;
                        if (null != rolesVOList && !rolesVOList.isEmpty()) {
                            rolesTypeLst = new ArrayList<RolesType>();
                            for (RolesVO rolesVO : rolesVOList) {
                                RolesType rolesType = new RolesType();
                                rolesType.setUserRolesSeqId(RMDCommonUtility.convertObjectToLong(rolesVO
                                        .getGetUsrRolesSeqId()));
                                rolesType.setLinkUsrRoleSeqId(RMDCommonUtility.convertObjectToLong(rolesVO
                                        .getLinkUsrRoleSeqId()));
                                rolesType.setRoleName(rolesVO.getRoleName());
                                rolesType.setRoleDesc(rolesVO.getRoleDesc());
                                rolesTypeLst.add(rolesType);
                            }
                        }
                        objLoginResponse.setRolesType(rolesTypeLst);
                    }

                    if (lastLoginFrom != null && !lastLoginFrom.trim().equals(OMDConstants.EMPTY_STRING)) {
                        lastLoginFromLoc = lastLoginFrom;
                    } else {
                        lastLoginFromLoc = AppConstants.LAST_LOGIN_FROM;
                    }
                    
                    
                    objAuthenticatorServiceIntf.updateLastLoginDetails(userSeqId, lastLoginFromLoc);
                    List<PreferencesType> preferencesTypeLst = getUserPreference(objLoginRequestServiceVO);
                    
                    if (!preferencesTypeLst.isEmpty()) {
                        objLoginResponse.setPreferencesType(preferencesTypeLst);
                        
                        if (OMDConstants.LASTLOGINFROMMOB.equals(lastLoginFromLoc)) {
                            List<ElementVO> lookupList = objRuleCommonServiceIntf.getLookupValues(OMDConstants.MOBILE_APP_CONFIGURATION);
                            
                            if (!lookupList.isEmpty()) {
                                for (ElementVO ev : lookupList) {
                                    objLoginResponse.setMobOperatingStatus(ev.getName());
                                }
                            }
                            
                            for (PreferencesType preferenceType : preferencesTypeLst) {
                                if (OMDConstants.ROLE.equals(preferenceType.getUserPreferernceType())) {
                                    objLoginResponse.setLstRolePrivileges(getRolePrivileges(Long.valueOf(preferenceType
                                            .getUserPreferenceValue())));
                                }
                            }
                        }
                    }
                }
            } else {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.USERID_PASSWORD_NOT_PROVIDED),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.USERID_PASSWORD_NOT_PROVIDED), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return objLoginResponse;
    }

    /**
     * This method is used for getting the preference type on login
     * 
     * @return
     * @throws RMDServiceException
     */
    public List<PreferencesType> getUserPreference(LoginRequestServiceVO objLogin) throws RMDServiceException {
        List<GetUsrUserPreferenceVO> objUserPreferenceList = null;
        List<PreferencesType> preferencesTypeLst = new ArrayList<PreferencesType>();
        PreferencesType preferencesType = null;
        try {
            objUserPreferenceList = objAuthenticatorServiceIntf.getUserPreferences(objLogin);
            for (GetUsrUserPreferenceVO objUserPreference : objUserPreferenceList) {
                preferencesType = new PreferencesType();
                GetUsrUsersVO objUsrUsers = objUserPreference.getGetUsrUsersVO();
                preferencesType.setUserId(objUsrUsers.getGetUsrUsersSeqId());
                preferencesType.setUserPreferenceSeqId(objUserPreference.getGetUsrUserPreferenceSeqId());
                preferencesType.setUserPreferernceType(objUserPreference.getUserPreferernceType());

                preferencesType.setUserPreferenceValue(objUserPreference.getUserPreferenceValue());

                preferencesTypeLst.add(preferencesType);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return preferencesTypeLst;
    }
    /**
     * @param @Context UriInfo ui
     * @return List<ConfigTemplateDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the Templates for the selected Controller Config and
     *              Config File.
     */

    @POST
    @Path(OMDConstants.UPDATE_LAST_LOGIN_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String updateLastLoginDetails(UserRequestType userRequestType) throws RMDServiceException {

        String userId = OMDConstants.EMPTY_STRING;
        String lastLoginFrom = OMDConstants.EMPTY_STRING;

        String status = null;
        try {
            userId = userRequestType.getUserId();
            if (userRequestType.getLastLoginFrom() != null
                    && !userRequestType.getLastLoginFrom().equals(OMDConstants.EMPTY_STRING)) {
                lastLoginFrom = userRequestType.getLastLoginFrom();
            } else {
                lastLoginFrom = AppConstants.LAST_LOGIN_FROM;
            }

            if (userId != null && !userId.trim().equals(OMDConstants.EMPTY_STRING)) {
                status = objAuthenticatorServiceIntf.updateLastLoginDetails(userId, lastLoginFrom);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_USERID);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return status;
    }

    /**
     * @param @Context UriInfo ui
     * @return List<ConfigTemplateDetailsVO>
     * @throws RMDServiceException
     * @Description This method is used to get the Templates for the selected Controller Config and
     *              Config File.
     */

    @GET
    @Path(OMDConstants.UPDATE_LAST_LOGIN_AND_FROM_DETAILS)
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String updateLastLoginAndFromDetails(@PathParam(OMDConstants.USERIDVALUE) final String userId,
            @PathParam(OMDConstants.USER_SEQ_ID_VALUE) final String userSeqIdValue,
            @PathParam(OMDConstants.LASTLOGINFROM) final String lastLoginFrom) throws RMDServiceException {

        String lastLoginFromLoc = OMDConstants.EMPTY_STRING;
        String status = null;
        try {

            if (lastLoginFrom != null && !lastLoginFrom.trim().equals(OMDConstants.EMPTY_STRING)) {
                lastLoginFromLoc = lastLoginFrom;
            } else {
                lastLoginFromLoc = AppConstants.LAST_LOGIN_FROM;
            }

            if (userSeqIdValue != null && !userSeqIdValue.trim().equals(OMDConstants.EMPTY_STRING)) {
                status = objAuthenticatorServiceIntf.updateLastLoginDetails(userSeqIdValue, lastLoginFromLoc);
            } else {

                throw new OMDInValidInputException(OMDConstants.INVALID_USERID);
            }
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }

        return status;
    }

    public List<RolePrivilegesType> getRolePrivileges(Long roleIdValue) throws RMDServiceException {
        String strLanguage = null;
        List<ElementVO> lstElement;
        List<RolePrivilegesType> lstRolePrivileges = null;
        RolePrivilegesType objRolePrivilegesType = null;
        try {
            MultivaluedMap<String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.ROLE_ID_VALUE, String.valueOf(roleIdValue));
            int[] methodConstants = {RMDCommonConstants.NUMERIC};
            if (null == roleIdValue || roleIdValue < 1
                    || !AppSecUtil.validateWebServiceInput(map, null, methodConstants, OMDConstants.ROLE_ID_VALUE)) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
                lstElement = objRolesServiceIntf.getRolePrivileges(roleIdValue, strLanguage);
            }

            if (RMDCommonUtility.isCollectionNotEmpty(lstElement)) {
                lstRolePrivileges = new ArrayList<RolePrivilegesType>();
                for (ElementVO elementVO : lstElement) {
                    objRolePrivilegesType = new RolePrivilegesType();
                    CMBeanUtility.copyElementVOToRolePrivType(elementVO, objRolePrivilegesType);
                    lstRolePrivileges.add(objRolePrivilegesType);
                }
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[]{},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            }*/
        } catch (OMDInValidInputException objOMDInValidInputException) {
            throw objOMDInValidInputException;
        } catch (OMDNoResultFoundException objOMDNoResultFoundException) {
            throw objOMDNoResultFoundException;
        } catch (RMDServiceException rmdServiceException) {
            throw rmdServiceException;
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);

        }
        return lstRolePrivileges;
    }

}