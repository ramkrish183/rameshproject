/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthorizationResource.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.authorization.resources
 * Author : iGATE-Patni Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Aug 3 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.services.authorization.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.trans.eoa.services.admin.service.intf.RolesEoaServiceIntf;
import com.ge.trans.eoa.services.admin.service.intf.RolesServiceIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.rmd.common.constants.OMDConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
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
import com.ge.trans.rmd.services.authorization.valueobjects.DeleteRoleListType;
import com.ge.trans.rmd.services.authorization.valueobjects.DeleteRolesDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.DeleteRoleType;
import com.ge.trans.rmd.services.authorization.valueobjects.PrivilegeDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.RoleManagementRequestType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolePrivilegesType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesDetailsType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesRequestType;
import com.ge.trans.rmd.services.authorization.valueobjects.RolesResponseType;
import com.ge.trans.rmd.services.util.CMBeanUtility;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.MultivaluedMapImpl;
/***********************************************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Aug 3 2011
 * @Date Modified :Aug 26 2011
 * @Modified By :
 * @Contact :
 * @Description : This Class act as a webservice and helps to get information
 *              for the Roles related functions related funtionalities
 * @History :
 ****************************************************************************************************************/

@Path(OMDConstants.AUTHORIZATION_SERVICE)
@Component
public class AuthorizationResource extends BaseResource {
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(AuthorizationResource.class);

    @Autowired
    RolesServiceIntf objRolesServiceIntf;
    @Autowired
    OMDResourceMessagesIntf omdResourceMessagesIntf;
    @Autowired
    RolesEoaServiceIntf objRolesEoaServiceIntf;

    /**
     * This method is used for retrieving Role details based on the inputs
     *
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ROLESDETAILS)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RolesResponseType> getRoleDetails() throws RMDServiceException {
        Iterator<RoleEoaServiceVO> iterRoles = null;
        List<RoleEoaServiceVO> arlRoles;
        RoleEoaServiceVO objRoleServiceVO;
        String strLanguage = null;
        RolesResponseType objRolesResponseType;
        final List<RolesResponseType> arlRoleType = new ArrayList<RolesResponseType>();

        try {
            strLanguage = getRequestHeader(OMDConstants.LANGUAGE);
            arlRoles = objRolesEoaServiceIntf.getAllRoles(strLanguage);
            if (RMDCommonUtility.isCollectionNotEmpty(arlRoles)) {
                if (RMDCommonUtility.isCollectionNotEmpty(arlRoles)) {
                    iterRoles = arlRoles.iterator();
                    while (iterRoles.hasNext()) {
                        objRolesResponseType = new RolesResponseType();
                        objRoleServiceVO = iterRoles.next();
                        BeanUtility.copyBeanProperty(objRoleServiceVO, objRolesResponseType);
                        objRolesResponseType.setLastUpdatedBy(objRoleServiceVO.getLastUpdatedBy());
                        objRolesResponseType.setLastUpdatedTime(objRoleServiceVO.getLastUpdatedTime());
                        arlRoleType.add(objRolesResponseType);

                    }
                }
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
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

        return arlRoleType;

    }

    /**
     * This method is used for retrieving role details based on the role name
     * passed
     * 
     * @param roleNameValue
     * @return RolesResponseType
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ROLESDETAILS_ROLENAME)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public RolesResponseType getRoleDetails(@PathParam(OMDConstants.ROLENAMEVALUE) String roleNameValue)
            throws RMDServiceException {
        RoleServiceVO objRoleServiceVO = null;
        RolesResponseType objRolesResponseType = null;
        try {
            if (RMDCommonUtility.isNull(roleNameValue)) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                objRoleServiceVO = new RoleServiceVO();
                objRoleServiceVO.setRoleName(roleNameValue);
                objRoleServiceVO = objRolesServiceIntf.getMatchingRole(objRoleServiceVO);
                objRolesResponseType = new RolesResponseType();
                if (null == objRoleServiceVO) {
                    throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                            omdResourceMessagesIntf.getMessage(
                                    BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
                                    BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
                } else {
                    BeanUtility.copyBeanProperty(objRoleServiceVO, objRolesResponseType);
                }
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

        return objRolesResponseType;
    }

    /**
     * This method is used for saving the Role from the inputs passed
     * 
     * @param objRolesRequestType
     * @return
     * @throws RMDServiceException
     */
    @POST
    @Path(OMDConstants.SAVEROLESDETAILS)
    @Consumes(MediaType.APPLICATION_XML)
    public void saveRoleDetails(final RolesRequestType objRolesRequestType) throws RMDServiceException {
        final RoleServiceVO objRoleServiceVO = new RoleServiceVO();
        final RolePrivilegeServiceVO objRolePrivilegeServiceVO = new RolePrivilegeServiceVO();
        final List<RolePrivilegeServiceVO> arlPrevileges = new ArrayList<RolePrivilegeServiceVO>();
        RolesDetailsType objRolesDetailsType;
        RolePrivilegesType objRolePrivilegesType;
        try {
            if (null == objRolesRequestType) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.GETTING_NULL_REQUEST_OBJECT), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                objRolesDetailsType = objRolesRequestType.getRolesDetails();
                objRolePrivilegesType = objRolesRequestType.getRolePrivileges();
                BeanUtility.copyBeanProperty(objRolesDetailsType, objRoleServiceVO);
                BeanUtility.copyBeanProperty(objRolePrivilegesType, objRolePrivilegeServiceVO);
                arlPrevileges.add(objRolePrivilegeServiceVO);
                objRoleServiceVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                objRoleServiceVO.setStrLanguage(getRequestHeader(OMDConstants.LANGUAGE));
                objRolesServiceIntf.saveRoleInfo(objRoleServiceVO, arlPrevileges);
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
    }

    /**
     * This method is used for retrieving Role Privileges
     * 
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETROLEPRIVILEGE_ROLEID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RolePrivilegesType> getRolePrivileges(@PathParam(OMDConstants.ROLE_ID_VALUE) Long roleIdValue)
            throws RMDServiceException {
        String strLanguage = null;
        List<ElementVO> lstElement;
        List<RolePrivilegesType> lstRolePrivileges = null;
        RolePrivilegesType objRolePrivilegesType = null;
        try {
            MultivaluedMap<String, String> map = new MultivaluedMapImpl();
            map.add(OMDConstants.ROLE_ID_VALUE, String.valueOf(roleIdValue));
            int[] methodConstants = { RMDCommonConstants.NUMERIC };
            if (null == roleIdValue || roleIdValue < 1
                    || !AppSecUtil.validateWebServiceInput(map, null, methodConstants, OMDConstants.ROLE_ID_VALUE)) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA), new String[] {},
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
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
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

    /**
     * This method is used for retrieving Role Privileges
     * 
     * @param uriParam
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GETROLEsECNAVIGATION)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<RolePrivilegesType> getRoleSecNavPrivileges(@PathParam(OMDConstants.ROLE_ID_VALUE) Long roleIdValue)
            throws RMDServiceException {
        String strLanguage = null;
        List<ElementVO> lstElement;
        List<RolePrivilegesType> lstRolePrivileges = null;
        RolePrivilegesType objRolePrivilegesType = null;
        try {
            if (null == roleIdValue || roleIdValue < 1 || !AppSecUtil.checkIntNumber(String.valueOf(roleIdValue))) {
                throw new OMDInValidInputException(BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.INVALID_SEARCH_CRITERIA), new String[] {},
                                BeanUtility.getLocale(OMDConstants.DEFAULT_LANGUAGE)));
            } else {
                strLanguage = getRequestHeader(OMDConstants.LANGUAGE);

                lstElement = objRolesServiceIntf.getRoleSecNavPrivileges(roleIdValue, strLanguage);
            }

            if (RMDCommonUtility.isCollectionNotEmpty(lstElement)) {
                lstRolePrivileges = new ArrayList<RolePrivilegesType>();
                for (ElementVO elementVO : lstElement) {
                    objRolePrivilegesType = new RolePrivilegesType();
                    CMBeanUtility.copyElementVOToRolePrivType(elementVO, objRolePrivilegesType);
                    objRolePrivilegesType.setName(elementVO.getName());
                    objRolePrivilegesType.setAccessLevel(elementVO.getAccesslevel());
                    objRolePrivilegesType.setSecNavigationURL(elementVO.getSecNavigationURL());
                    objRolePrivilegesType.setImages(elementVO.getImages());
                    objRolePrivilegesType.setNewFlag(elementVO.getNewFlag());
                    lstRolePrivileges.add(objRolePrivilegesType);
                }
            } /*else {
                throw new OMDNoResultFoundException(BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION),
                        omdResourceMessagesIntf.getMessage(
                                BeanUtility.getErrorCode(OMDConstants.NORECORDFOUNDEXCEPTION), new String[] {},
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

    /**
     * This method is used for updating role for the inputs given
     * 
     * @param objRoleManagementRequestType
     * @return
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.EDIT_ROLE)
    @Consumes(MediaType.APPLICATION_XML)
    public void editRole(final RoleManagementRequestType objRoleManagementRequestType) throws RMDServiceException {

        RoleManagementVO objRoleManagementVO = new RoleManagementVO();

        try {
            if (null == objRoleManagementRequestType) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            } else if (validateRoleManagementRequestType(objRoleManagementRequestType)) {
                objRoleManagementVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                CMBeanUtility.copyRoleManagementRequestTypeToVO(objRoleManagementVO, objRoleManagementRequestType);
                objRolesServiceIntf.editRole(objRoleManagementVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
    }

    
    
    @PUT
    @Path(OMDConstants.DELETE_ROLE)
    @Consumes(MediaType.APPLICATION_XML)
    public void deleteRole(final RoleManagementRequestType objRoleManagementRequestType) throws RMDServiceException {

        RoleManagementVO objRoleManagementVO = new RoleManagementVO();

        try {
            if (null == objRoleManagementRequestType) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            } else if (validateRoleManagementRequestType(objRoleManagementRequestType)) {
                objRoleManagementVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                CMBeanUtility.copyRoleManagementRequestTypeToVO(objRoleManagementVO, objRoleManagementRequestType);
                objRolesServiceIntf.deleteRole(objRoleManagementVO);
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
    }
    /**
     * This method is used for adding role for the inputs given
     * 
     * @param objRoleManagementRequestType
     * @return
     * @throws RMDServiceException
     */
    @PUT
    @Path(OMDConstants.ADD_ROLE)
    @Consumes(MediaType.APPLICATION_XML)
    public void addOrCopyRole(final RoleManagementRequestType objRoleManagementRequestType) throws RMDServiceException {
        RoleManagementVO objRoleManagementVO = new RoleManagementVO();
        try {
            if (null == objRoleManagementRequestType) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            } else if (null == objRoleManagementRequestType.getRoleName()
                    || objRoleManagementRequestType.getRoleName().isEmpty()) {
                throw new OMDInValidInputException(OMDConstants.ROLE_NAME_REQUIRED);

            } else if (validateRoleManagementRequestType(objRoleManagementRequestType)) {
                objRoleManagementVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                CMBeanUtility.copyRoleManagementRequestTypeToVO(objRoleManagementVO, objRoleManagementRequestType);
                objRolesServiceIntf.addOrCopyRole(objRoleManagementVO);

            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
            }
        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }

    }

    /**
     * This method is used for getting all the privileges
     * 
     * @param role
     *            id (optional)
     * @return
     * @throws RMDServiceException
     */
    @GET
    @Path(OMDConstants.GET_ALL_PRIVILEGES)
    @Consumes(MediaType.APPLICATION_XML)
    public List<PrivilegeDetailsType> getAllPrivileges(@QueryParam(OMDConstants.ROLE_ID) final String roleId)
            throws RMDServiceException {
        List<PrivilegesVO> lstPrivilegesVO = new ArrayList<PrivilegesVO>();
        List<PrivilegeDetailsType> allPrivilegesList = new ArrayList<PrivilegeDetailsType>();
        PrivilegeDetailsType privilegeDetails = null;
        try {
            lstPrivilegesVO = objRolesServiceIntf.getAllPrivileges(EsapiUtil.stripXSSCharacters(roleId));

            for (PrivilegesVO detailsVO : lstPrivilegesVO) {
               privilegeDetails = new PrivilegeDetailsType();
                privilegeDetails.setPrivilegeId(detailsVO.getPrivilegeID());
                privilegeDetails.setPrivilegeName(EsapiUtil.stripXSSCharacters(detailsVO.getPrivilegeName()));
                privilegeDetails.setDescription(EsapiUtil.stripXSSCharacters(detailsVO.getDescription()));
                privilegeDetails.setDisplayName(EsapiUtil.stripXSSCharacters(detailsVO.getDisplayName()));
                privilegeDetails.setEnabled(detailsVO.isEnabled());
                privilegeDetails.setLevel(EsapiUtil.stripXSSCharacters(detailsVO.getLevel()));
                privilegeDetails.setParentId(detailsVO.getParentId());
                privilegeDetails.setResourceType(EsapiUtil.stripXSSCharacters(detailsVO.getResourceType()));
                privilegeDetails.setSortOrder(EsapiUtil.stripXSSCharacters(detailsVO.getSortOrder()));
                allPrivilegesList.add(privilegeDetails);
            }

        } catch (Exception ex) {
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return allPrivilegesList;
    }

    /**
     * This method is used to validate the data used for saving the user input
     * RoleManagementRequestType
     * 
     * @return boolean
     */
    public static boolean validateRoleManagementRequestType(RoleManagementRequestType objRoleRequestType) {

        if (null != objRoleRequestType.getRoleName() && !objRoleRequestType.getRoleName().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objRoleRequestType.getRoleName())) {
                return false;
            }
        }

        if (null != objRoleRequestType.getRoleId() && !objRoleRequestType.getRoleId().isEmpty()) {
            if (!AppSecUtil.checkIntNumber(objRoleRequestType.getRoleId())) {
                return false;
            }
        }

        if (null != objRoleRequestType.getRoleDescription() && !objRoleRequestType.getRoleDescription().isEmpty()) {
            if (!AppSecUtil.checkAlphaNumericUnderscore(objRoleRequestType.getRoleDescription())) {
                return false;
            }
        }

        if (null == objRoleRequestType.getPrivileges()) {
            return false;
        }
        return true;
    }

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDServiceException
     * @Description:This method is used for fetching the EOA UserId by calling
     *                   the getEoaUserId() method of Service Layer.
     */

    @POST
    @Path(OMDConstants.GET_EOA_USERID)
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String getEoaUserName(UserRequestType userRequestType) throws RMDServiceException {
        String eoaUserName = null;

        try {

            String userId = userRequestType.getUserId();
            if (userId != null && !userId.trim().equals("")) {

                eoaUserName = objRolesEoaServiceIntf.getEoaUserName(userId);
                if (null == eoaUserName) {
                    eoaUserName = RMDCommonConstants.NO_EOA_USER;
                }
            } else {

                throw new OMDInValidInputException(OMDConstants.INVALID_USERID);
            }
        } catch (Exception e) {
            RMDWebServiceErrorHandler.handleException(e, omdResourceMessagesIntf);
        }
        return eoaUserName;
    }
    
    @GET
    @Path(OMDConstants.DELETE_ROLE_LIST)
    @Consumes(MediaType.APPLICATION_XML)
    public List<DeleteRolesDetailsType> getDeleteRoleList(@QueryParam(OMDConstants.ROLE_ID) final String roleId)
            throws RMDServiceException {
    	List<DeleteRolesResponseVO> deleteRolesResponseVO = new ArrayList<DeleteRolesResponseVO>();
       
        List<DeleteRolesDetailsType> responseList = new ArrayList<DeleteRolesDetailsType>();
        
        RoleManagementVO objRoleManagementVO = new RoleManagementVO();
        RoleManagementRequestType objRoleManagementRequestType = new RoleManagementRequestType();
        objRoleManagementRequestType.setRoleId(EsapiUtil.stripXSSCharacters(roleId));
        try {
            if (null == EsapiUtil.stripXSSCharacters(roleId)) {
                throw new OMDInValidInputException(OMDConstants.GETTING_NULL_REQUEST_OBJECT);
            } else if (validateRoleManagementRequestType(objRoleManagementRequestType)) {
                objRoleManagementVO.setStrUserName(getRequestHeader(OMDConstants.USERID));
                CMBeanUtility.copyRoleManagementRequestTypeToVO(objRoleManagementVO, objRoleManagementRequestType);
                deleteRolesResponseVO = objRolesServiceIntf.getDeleteRoleList(objRoleManagementVO);
                
                if(deleteRolesResponseVO != null)
                {
                	for(DeleteRolesResponseVO deleteRolesResponseVO2 : deleteRolesResponseVO) {
                		 DeleteRolesDetailsType deleteRolesDetailsType = new DeleteRolesDetailsType();
                		deleteRolesDetailsType.setFirstName(deleteRolesResponseVO2.getFirstName());
                		deleteRolesDetailsType.setLastName(deleteRolesResponseVO2.getLastName());
                		deleteRolesDetailsType.setUserId(deleteRolesResponseVO2.getUserId());
                		deleteRolesDetailsType.setUserSeqId(deleteRolesResponseVO2.getUserSeqId());
                		deleteRolesDetailsType.getUserCustomers().addAll(deleteRolesResponseVO2.getUserCustomers());
                		
                		responseList.add(deleteRolesDetailsType);
					}
               }
      
          
            } else {
                throw new OMDInValidInputException(OMDConstants.INVALID_VALUE);
                
        } 
        }catch (Exception ex) {
        	ex.printStackTrace();
            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
        }
        return responseList;
    }
    
    @POST
    @Path(OMDConstants.DELETE_ROLE_UPDATE_LIST)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public String deleteRoleUpdateList(DeleteRoleListType delRoleListType)
            throws RMDServiceException {
    	String result = null;
    	List<DeleteRolesUpdateVO> arlDelRolUpdList = null;
    	List<DeleteRoleType> arlDeleteRolesUpdateDetailsType = delRoleListType.getDeleteRoleTypeList();
    	arlDelRolUpdList = new ArrayList<DeleteRolesUpdateVO>(arlDeleteRolesUpdateDetailsType.size());
    	try{
	    	for (DeleteRoleType objItem : arlDeleteRolesUpdateDetailsType) {
	    		DeleteRolesUpdateVO objDeleteRolesUpdateVO = new DeleteRolesUpdateVO();
	    		if(!RMDCommonUtility.isNullOrEmpty(objItem.getUserId())){
	    			objDeleteRolesUpdateVO.setUserId(objItem.getUserId());
	    			}else{
	    				throw new OMDInValidInputException(
	                            OMDConstants.USERID_NOT_PROVIDED);
	    			}
	    		if(!RMDCommonUtility.isNullOrEmpty(objItem.getCurrentRole())){
	    			objDeleteRolesUpdateVO.setCurrentRole(objItem.getCurrentRole());
	    			}else{
	    				throw new OMDInValidInputException(
	                            OMDConstants.CURRENT_ROLE_NOT_PROVIDED);
	    			}
	    		if(!RMDCommonUtility.isNullOrEmpty(objItem.getChangedRoleId())){
	    			objDeleteRolesUpdateVO.setChangedRoleId(objItem.getChangedRoleId());
	    			}else{
	    				throw new OMDInValidInputException(
	                            OMDConstants.USERID_NOT_PROVIDED);
	    			}
	    		if(!RMDCommonUtility.isNullOrEmpty(objItem.getUserSeqId())){
	    			objDeleteRolesUpdateVO.setUserSeqId(objItem.getUserSeqId());
	    			}else{
	    				throw new OMDInValidInputException("user sequence id not provided");
	    			}
	    		arlDelRolUpdList.add(objDeleteRolesUpdateVO);
	    		}
    		
    		result =	objRolesServiceIntf.deleteRoleUpdateList(arlDelRolUpdList, delRoleListType.getUserId());
			} catch (Exception ex) {
			
				ex.printStackTrace();
	            RMDWebServiceErrorHandler.handleException(ex, omdResourceMessagesIntf);
			}
    	return result;
    	
		}
    	
}
