/**
 * =================================================================
 * Classification: GE Confidential
 * File : RolesDAOImpl.java
 * Description : DAO Impl for Role Management
 * 
 * Package : com.ge.trans.rmd.services.admin.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.admin.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;
import static com.ge.trans.rmd.common.constants.RMDCommonConstants.UPDATE_SUCCESS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.admin.bo.intf.PopupListAdminBOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.RolesEoaDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrRolePrivilegesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrRolesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserProductHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserProductRoleHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Impl for Role Management
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RolesEoaDAOImpl extends BaseDAO implements RolesEoaDAOIntf {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -5259728318291027569L;

    private PopupListAdminDAOIntf popupListAdminDAO;

    public void setPopupListAdminDAO(PopupListAdminDAOIntf popupListAdminDAO) {
        this.popupListAdminDAO = popupListAdminDAO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * deleteAllRolePrivileges
     * (com.ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO)
     */
    @Autowired
    PopupListAdminBOIntf popupListAdminBOIntf;

    @Override
    public int deleteAllRolePrivileges(final RoleEoaServiceVO objRoleServiceVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "DELETE FROM GetUsrRolePrivilegesHVO RP WHERE RP.getUsrRoles.getUsrRolesSeqId = :roleId"; // and
            // rp
            // .
            // strLanguage
            // =
            // :
            // language
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID, objRoleServiceVO.getGetUsrRolesSeqId());
            return query.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#getAllRoles(java
     * .lang.String)
     */
    @Override
    public List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GetUsrRolesHVO ORDER BY roleName"; // where
            // language
            // =
            // :
            // language
            Query query = session.createQuery(queryString.toString());
            List<GetUsrRolesHVO> arlUserRolesHVO = query.list();
            List<RoleEoaServiceVO> arlUserRolesVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(arlUserRolesHVO)) {
                arlUserRolesVO = new ArrayList<RoleEoaServiceVO>();
                RoleEoaServiceVO objUserRoleVO = null;
                for (GetUsrRolesHVO objUserRoleHVO : arlUserRolesHVO) {
                    objUserRoleVO = new RoleEoaServiceVO();
                    objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                    objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                    objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                    objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
                    objUserRoleVO.setLastUpdatedBy(objUserRoleHVO.getLastUpdatedBy());
                    objUserRoleVO.setLastUpdatedTime(objUserRoleHVO.getLastUpdatedDate());
                    arlUserRolesVO.add(objUserRoleVO);
                }
            }
            return arlUserRolesVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * getMatchingRole
     * (com.ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO)
     */
    @Override
    public RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GetUsrRolesHVO WHERE roleName = :roleName"; // and
            // language
            // =
            // :
            // language
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_NAME, roleService.getRoleName());
            List<GetUsrRolesHVO> objUserRolesHVO = query.list();
            RoleEoaServiceVO objUserRoleVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(objUserRolesHVO)) {
                objUserRoleVO = new RoleEoaServiceVO();
                GetUsrRolesHVO objUserRoleHVO = objUserRolesHVO.get(0);
                objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
            }
            return objUserRoleVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, roleService.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, roleService.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * getRoleInfo(java.lang.Long, java.lang.String)
     */
    @Override
    public RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId = :roleId"; // and
            // language
            // =
            // :
            // language
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID, roleId);
            List<GetUsrRolesHVO> objUserRolesHVO = query.list();
            RoleEoaServiceVO objUserRoleVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(objUserRolesHVO)) {
                objUserRoleVO = new RoleEoaServiceVO();
                GetUsrRolesHVO objUserRoleHVO = objUserRolesHVO.get(0);
                objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
            }
            return objUserRoleVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * getRolePrivileges(java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDDAOException {
        Session session = null;
        try {
            List<ElementVO> rolePriviliges = null;
            ElementVO elementVO = null;
            session = getHibernateSession();
            // added sort_order for Permanent Tab Sort Order Implementation
            String queryString = "SELECT TBLMASTERPRIVILEGE.PRIVILEGE_NAME AS ROLEPRIVILEGE,TBLMASTERPRIVILEGE.RESOURCE_TYPE AS ACCESSLEVEL,TBLROLEPRIVILEGE.SORT_ORDER AS SORTORDER FROM GET_USR.GET_USR_ROLE_PRIVILEGES TBLROLEPRIVILEGE,GET_USR.GET_USR_PRIVILEGE TBLMASTERPRIVILEGE WHERE  TBLROLEPRIVILEGE.LINK_ROLES=:roleId AND TBLROLEPRIVILEGE.link_privileges=TBLMASTERPRIVILEGE.GET_USR_PRIVILEGE_SEQ_ID AND   TBLMASTERPRIVILEGE.STATUS ='1' ORDER BY TBLROLEPRIVILEGE.SORT_ORDER ASC";

            Query query = session.createSQLQuery(queryString).setParameter(RMDCommonConstants.ROLE_ID,
                    Long.valueOf(roleId));
            List usrRolePrivileges = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(usrRolePrivileges)) {
                rolePriviliges = new ArrayList<ElementVO>();
                for (Object rolePrivilege : usrRolePrivileges) {
                    Object[] resultColumns = (Object[]) rolePrivilege;
                    elementVO = new ElementVO((String) resultColumns[0], RMDServiceConstants.SHOW,
                            (String) resultColumns[1], RMDCommonUtility.convertObjectToLong(resultColumns[2]));
                    rolePriviliges.add(elementVO);
                }
            }
            return rolePriviliges;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * getUsersPermittedResources(java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userSeqId, final String language)
            throws RMDDAOException {
        Session session = null;
        try {
            List<RolePrivilegeEoaServiceVO> userPriviliges = null;
            session = getHibernateSession();

            String queryString = "SELECT rolePrivileges, accessLevel FROM GetUsrRolePrivilegesHVO WHERE getUsrRoles.getUsrRolesSeqId "
                    + "IN (SELECT getUsrRoles.getUsrRolesSeqId FROM GetUsrUserRolesHVO WHERE getUsrUsers.getUsrUsersSeqId  = :userSeqId "
                    + "AND getUsrRoles.getUsrRolesSeqId IN (SELECT getUsrRolesSeqId FROM GetUsrRolesHVO WHERE status = 1 ))";

            // Removing the language check for roles
            Query query = session.createQuery(queryString).setParameter(RMDCommonConstants.USER_SEQ_ID, userSeqId);
            List usrRolePrivileges = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(usrRolePrivileges)) {
                userPriviliges = new ArrayList<RolePrivilegeEoaServiceVO>();
                RolePrivilegeEoaServiceVO rolePrivilege = null;
                for (Object userRolePrivilege : usrRolePrivileges) {
                    rolePrivilege = new RolePrivilegeEoaServiceVO();
                    Object[] resultColumns = (Object[]) userRolePrivilege;
                    rolePrivilege.setRolePrivilege((String) resultColumns[0]);
                    rolePrivilege.setAccessLevel((String) resultColumns[1]);
                    userPriviliges.add(rolePrivilege);
                }
            }
            return userPriviliges;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * saveRolePrivileges
     * (com.ge.trans.rmd.services.admin.service.valueobjects.RoleEoaServiceVO,
     * java.util.List)
     */
    @Override
    public int saveRolePrivileges(final RoleEoaServiceVO objRoleServiceVO,
            final List<RolePrivilegeEoaServiceVO> rolesPrivileges) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession(objRoleServiceVO.getStrUserName());
            GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
            for (RolePrivilegeEoaServiceVO objRolePrivilegeServiceVO : rolesPrivileges) {
                objRolePrivilegeHVO = new GetUsrRolePrivilegesHVO();
                GetUsrRolesHVO userRolesHVO = (GetUsrRolesHVO) session.get(GetUsrRolesHVO.class,
                        objRoleServiceVO.getGetUsrRolesSeqId());
                objRolePrivilegeHVO.setStrLanguage(objRoleServiceVO.getStrLanguage());
                objRolePrivilegeHVO.setGetUsrRoles(userRolesHVO);
                objRolePrivilegeHVO.setRolePrivileges(objRolePrivilegeServiceVO.getRolePrivilege());
                objRolePrivilegeHVO.setResourceType(objRolePrivilegeServiceVO.getResourceType());
                objRolePrivilegeHVO.setAccessLevel(objRolePrivilegeServiceVO.getAccessLevel());
                session.save(objRolePrivilegeHVO);
                session.flush();
            }
            return UPDATE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * saveRoleInfo(com
     * .ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO)
     */
    @Override
    public Serializable saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession(objRoleServiceVO.getStrUserName());
            GetUsrRolesHVO rolesHVO = new GetUsrRolesHVO();
            if (objRoleServiceVO.getGetUsrRolesSeqId() != null && objRoleServiceVO.getGetUsrRolesSeqId() != 0) {
                rolesHVO = (GetUsrRolesHVO) session.get(GetUsrRolesHVO.class, objRoleServiceVO.getGetUsrRolesSeqId());
            }
            rolesHVO.setRoleName(objRoleServiceVO.getRoleName());
            rolesHVO.setRoleDesc(objRoleServiceVO.getRoleDesc());
            rolesHVO.setStatus(objRoleServiceVO.getStatus());
            rolesHVO.setStrLanguage(objRoleServiceVO.getStrLanguage());
            session.saveOrUpdate(rolesHVO);
            session.flush();
            RoleEoaServiceVO roleServiceVOPersisted = new RoleEoaServiceVO();
            BeanUtils.copyProperties(roleServiceVOPersisted, rolesHVO);
            return roleServiceVOPersisted;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#getRolesHomePages
     * (java.lang.Long)
     */
    @Override
    public List<String> getRolesHomePages(final Long roleId, final String language) {
        Session session = null;
        try {
            session = getHibernateSession(EMPTY_STRING);
            StringBuilder queryString = new StringBuilder();
            queryString.append("FROM GetUsrRolePrivilegesHVO RP WHERE RP.getUsrRoles.getUsrRolesSeqId = :roleId ");

            Query query = session.createQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.ROLE_ID, roleId);
            List<GetUsrRolePrivilegesHVO> arlRolePrivileges = query.list();
            List<String> arlHomePages = null;
            if (RMDCommonUtility.isCollectionNotEmpty(arlRolePrivileges)) {
                arlHomePages = new ArrayList<String>();
                for (GetUsrRolePrivilegesHVO rolePrivileges : arlRolePrivileges) {
                    arlHomePages.add(rolePrivileges.getRolePrivileges());
                }
            }
            return arlHomePages;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * This method is to get the secondary navigation links (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * getRoleSecNavPrivileges(java.lang.Long, java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language) throws RMDDAOException {
        Session session = null;
        try {
            List<ElementVO> rolePriviliges = null;
            ElementVO elementVO = null;
            session = getHibernateSession();

            String queryString = "SELECT TBLMASTERPRIVILEGE.PRIVILEGE_NAME AS ROLEPRIVILEGE,TBLMASTERPRIVILEGE.RESOURCE_TYPE FROM GET_USR.GET_USR_ROLE_PRIVILEGES TBLROLEPRIVILEGE, GET_USR.GET_USR_PRIVILEGE TBLMASTERPRIVILEGE WHERE  TBLROLEPRIVILEGE.LINK_ROLES=:roleId AND   TBLROLEPRIVILEGE.link_privileges=TBLMASTERPRIVILEGE.GET_USR_PRIVILEGE_SEQ_ID AND  TBLMASTERPRIVILEGE.RESOURCE_TYPE=:resourceType AND TBLMASTERPRIVILEGE.STATUS ='1' ORDER BY TBLROLEPRIVILEGE.SORT_ORDER ASC";

            Query query = session.createSQLQuery(queryString).setParameter(RMDCommonConstants.ROLE_ID,
                    Long.valueOf(roleId));
            query.setParameter(RMDCommonConstants.RESOURCE_TYPE, RMDCommonConstants.SECNAV);
            List usrRolePrivileges = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(usrRolePrivileges)) {
                rolePriviliges = new ArrayList<ElementVO>();
                List<GetSysLookupVO> lstSysLookupVO = null;
                GetSysLookupVO sysLookupVO = null;
                GetSysLookupVO getSysLookupVO = null;
                String lookUPURLValue = null;
                String imageValue = null;
                String newFlag = null;
                String id = null;
                String resColumn = null;
                Object[] resultColumns = null;
                for (Object rolePrivilege : usrRolePrivileges) {
                    resultColumns = (Object[]) rolePrivilege;
                    sysLookupVO = new GetSysLookupVO();
                    id = RMDCommonUtility.convertObjectToString(resultColumns[0]);
                    resColumn = RMDCommonConstants.SHOW;
                    sysLookupVO.setListName(id);
                    lstSysLookupVO = popupListAdminBOIntf.getPopupListValues(sysLookupVO);
                    if (RMDCommonUtility.isCollectionNotEmpty(lstSysLookupVO)) {

                        getSysLookupVO = lstSysLookupVO.get(0);
                        lookUPURLValue = getSysLookupVO.getLookValue();

                        /**
                         * As of now image label and flag are hard coded. In
                         * future we can bring it from Database
                         */
                        if (id != null && id.startsWith(RMDCommonConstants.HELP)) {
                            imageValue = RMDCommonConstants.DUMMY_IMAGE;
                            newFlag = RMDCommonConstants.NEW_WINDOW_FLAG;
                        } else if (id != null && id.startsWith(RMDCommonConstants.CWC)) {
                            imageValue = RMDCommonConstants.DUMMY2_IMAGE;
                            newFlag = RMDCommonConstants.SUBTAB_FLAG;
                        } else if (id != null && id.startsWith(RMDCommonConstants.ESERVICE)) {
                            imageValue = RMDCommonConstants.DUMMY3_IMAGE;
                            newFlag = RMDCommonConstants.NEW_WINDOW_FLAG;
                        }
                        elementVO = new ElementVO(id, resColumn, lookUPURLValue, imageValue, newFlag);
                        rolePriviliges.add(elementVO);
                    }
                }
            }
            return rolePriviliges;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * This method is used for getting all the privileges
     * 
     * @param role
     *            id (optional)
     * @return
     * @throws RMDDAOException
     */
    @Override
    public List<PrivilegesEOAVO> getAllPrivileges(String roleId) throws RMDDAOException {
        Session session = null;
        int currentLevel = 1;
        StringBuilder queryString = new StringBuilder();
        boolean islevelOneFlag = true;
        List<PrivilegesEOAVO> resultListPriviliges = new ArrayList<PrivilegesEOAVO>();
        Map<Long, List<PrivilegesEOAVO>> privilegesMap = new HashMap<Long, List<PrivilegesEOAVO>>();
        PrivilegesEOAVO privilegesVO = null;
        List<Long> currentLevelPriviliges = new ArrayList<Long>();
        List<PrivilegesEOAVO> listPrivilegesToSort = new ArrayList<PrivilegesEOAVO>();
        List<PrivilegesEOAVO> newChildList = null;
        int levelSortOrder = 1;
        Query query = null;
        try {
            session = getHibernateSession();
            queryString
                    .append("SELECT GET_USR_PRIVILEGE_SEQ_ID,PRIVILEGE_NAME,DISPLAY_NAME,PRIVILEGE_DESCRIPTION_TXT,");
            queryString.append(" RESOURCE_TYPE,PARENT_ID,LINK_ROLES , MASTERSORTORDER, ROLEPRIVSORTORDER FROM (");
            queryString.append(
                    "SELECT TBLMASTERPRIVILEGE.GET_USR_PRIVILEGE_SEQ_ID,TBLMASTERPRIVILEGE.PRIVILEGE_NAME,TBLMASTERPRIVILEGE.DISPLAY_NAME,TBLMASTERPRIVILEGE.PRIVILEGE_DESCRIPTION_TXT,");
            queryString.append(
                    " TBLMASTERPRIVILEGE.RESOURCE_TYPE,TBLMASTERPRIVILEGE.PARENT_ID,TBLROLEPRIVILEGE.LINK_ROLES ,TBLMASTERPRIVILEGE.SORT_ORDER MASTERSORTORDER,");
            queryString.append(
                    "TBLROLEPRIVILEGE.SORT_ORDER ROLEPRIVSORTORDER,TBLMASTERPRIVILEGE.STATUS FROM GET_USR.GET_USR_ROLE_PRIVILEGES TBLROLEPRIVILEGE RIGHT JOIN GET_USR.GET_USR_PRIVILEGE TBLMASTERPRIVILEGE ");

            queryString.append(
                    "ON TBLROLEPRIVILEGE.LINK_PRIVILEGES = TBLMASTERPRIVILEGE.GET_USR_PRIVILEGE_SEQ_ID AND TBLROLEPRIVILEGE.LINK_ROLES=:roleId ORDER BY TBLMASTERPRIVILEGE.PARENT_ID,ROLEPRIVSORTORDER");
            queryString.append(") WHERE  STATUS ='1'");
            query = session.createSQLQuery(queryString.toString()).setParameter(RMDCommonConstants.ROLE_ID, roleId);
            query.setFetchSize(100);
            List allPrivileges = query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(allPrivileges)) {
                resultListPriviliges = new ArrayList<PrivilegesEOAVO>();
                Object[] resultColumns = null;
                // for each privilege iterate through the loop
                for (Object currentPrivilege : allPrivileges) {
                    resultColumns = (Object[]) currentPrivilege;
                    privilegesVO = new PrivilegesEOAVO();
                    privilegesVO.setPrivilegeID(RMDCommonUtility.convertObjectToLong(resultColumns[0]));
                    privilegesVO.setPrivilegeName(RMDCommonUtility.convertObjectToString(resultColumns[1]));
                    privilegesVO.setDisplayName(RMDCommonUtility.convertObjectToString(resultColumns[2]));
                    privilegesVO.setDescription(RMDCommonUtility.convertObjectToString(resultColumns[3]));
                    privilegesVO.setResourceType(RMDCommonUtility.convertObjectToString(resultColumns[4]));
                    privilegesVO.setParentId(RMDCommonUtility.convertObjectToLong(resultColumns[5]));
                    if (null != resultColumns[6]) {
                        privilegesVO.setEnabled(true);
                    } else {
                        privilegesVO.setEnabled(false);
                    }
                    if (islevelOneFlag) {
                        if (null != roleId && !roleId.isEmpty()) {
                            // if role privilege sort order is not null
                            if (null != resultColumns[8] && privilegesVO.getParentId() == 0) {
                                privilegesVO.setSortOrder(String.valueOf(levelSortOrder++));
                                privilegesVO.setLevel(Integer.toString(currentLevel));
                                resultListPriviliges.add(privilegesVO);
                                currentLevelPriviliges.add(privilegesVO.getPrivilegeID());
                            } // if role privilege sort order is null taking
                              // master table sort order
                            else if (privilegesVO.getParentId() == 0) {
                                privilegesVO.setSortOrder(RMDCommonUtility.convertObjectToString(resultColumns[7]));
                                listPrivilegesToSort.add(privilegesVO);
                                currentLevelPriviliges.add(privilegesVO.getPrivilegeID());
                                continue;
                            } // once main level is over setting sort order
                              // where role privilege sort order is null
                            else if (privilegesVO.getParentId() != 0) {
                                List<PrivilegesEOAVO> sortedList = sortList(listPrivilegesToSort);
                                for (PrivilegesEOAVO childPrivilege : sortedList) {
                                    childPrivilege.setSortOrder(String.valueOf(levelSortOrder++));
                                    childPrivilege.setLevel(String.valueOf(currentLevel));
                                    resultListPriviliges.add(childPrivilege);
                                }
                                listPrivilegesToSort.clear();
                                levelSortOrder = 1;
                                islevelOneFlag = false;
                            }

                        } // if role id is not the taking values from master
                          // table
                        else {
                            if (privilegesVO.getParentId() == 0) {
                                privilegesVO.setLevel(Integer.toString(currentLevel));
                                privilegesVO.setSortOrder(RMDCommonUtility.convertObjectToString(resultColumns[7]));
                                resultListPriviliges.add(privilegesVO);
                                currentLevelPriviliges.add(privilegesVO.getPrivilegeID());
                            } else {
                                islevelOneFlag = false;

                            }
                        }

                    }

                    if (privilegesVO.getParentId() != 0) {

                        if (privilegesMap.containsKey(privilegesVO.getParentId())) {

                            if (null != roleId && !roleId.isEmpty()) {
                                // if role privilege sort order is not null
                                if (null != resultColumns[8]) {
                                    privilegesVO.setSortOrder(String.valueOf(levelSortOrder++));
                                    List<PrivilegesEOAVO> tempChildList = privilegesMap.get(privilegesVO.getParentId());
                                    tempChildList.add(privilegesVO);
                                    privilegesMap.put(privilegesVO.getParentId(), tempChildList);

                                } else {
                                    privilegesVO.setSortOrder(RMDCommonUtility.convertObjectToString(resultColumns[7]));
                                    listPrivilegesToSort.add(privilegesVO);
                                }
                            } // if role id is not the taking values from master
                              // table
                            else {
                                privilegesVO.setSortOrder(RMDCommonUtility.convertObjectToString(resultColumns[7]));
                                List<PrivilegesEOAVO> tempChildList = privilegesMap.get(privilegesVO.getParentId());
                                tempChildList.add(privilegesVO);
                                privilegesMap.put(privilegesVO.getParentId(), tempChildList);
                            }

                        } else {
                            // once the new parent come setting sort order older
                            // parents child which role privileges sort order is
                            // null
                            if (null != listPrivilegesToSort && !listPrivilegesToSort.isEmpty()) {
                                Long currentParentId = listPrivilegesToSort.get(0).getParentId();
                                List<PrivilegesEOAVO> tempChildList = privilegesMap.get(currentParentId);
                                List<PrivilegesEOAVO> sortedList = sortList(listPrivilegesToSort);
                                for (PrivilegesEOAVO childPrivilege : sortedList) {
                                    childPrivilege.setSortOrder(String.valueOf(levelSortOrder++));
                                    tempChildList.add(childPrivilege);
                                }
                                privilegesMap.put(currentParentId, tempChildList);
                                listPrivilegesToSort.clear();
                                levelSortOrder = 1;
                            }
                            if (null != roleId && !roleId.isEmpty()) {
                                privilegesVO.setSortOrder(String.valueOf(levelSortOrder++));
                            } else {
                                privilegesVO.setSortOrder(RMDCommonUtility.convertObjectToString(resultColumns[7]));
                            }
                            newChildList = new ArrayList<PrivilegesEOAVO>();
                            newChildList.add(privilegesVO);
                            privilegesMap.put(privilegesVO.getParentId(), newChildList);

                        }
                    }
                }
                // if any privilege is left to sort in list after for loop
                // adding that also to map
                if (null != listPrivilegesToSort && !listPrivilegesToSort.isEmpty()) {
                    Long currentParentId = listPrivilegesToSort.get(0).getParentId();
                    List<PrivilegesEOAVO> tempChildList = privilegesMap.get(currentParentId);
                    List<PrivilegesEOAVO> sortedList = sortList(listPrivilegesToSort);
                    for (PrivilegesEOAVO childPrivilege : sortedList) {
                        childPrivilege.setSortOrder(String.valueOf(levelSortOrder++));
                        tempChildList.add(childPrivilege);
                    }
                    privilegesMap.put(currentParentId, tempChildList);
                    listPrivilegesToSort.clear();
                    levelSortOrder = 1;
                }

                // increment the level to next level after level 1
                currentLevel++;
                while (!privilegesMap.isEmpty()) {
                    List<Long> nextLevelPriviliges = new ArrayList<Long>();
                    // for each parent in the current level take the childs set
                    // the level and remove from map
                    for (Long parentId : currentLevelPriviliges) {
                        if (privilegesMap.containsKey(parentId)) {
                            List<PrivilegesEOAVO> ChildList = privilegesMap.get(parentId);
                            privilegesMap.remove(parentId);
                            // for each child set the level and add to result
                            // and save its id as next level parent
                            for (PrivilegesEOAVO childPrivilege : ChildList) {
                                childPrivilege.setLevel(Integer.toString(currentLevel));
                                resultListPriviliges.add(childPrivilege);
                                nextLevelPriviliges.add(childPrivilege.getPrivilegeID());

                            }
                        }
                    }
                    currentLevelPriviliges = nextLevelPriviliges;
                    if (nextLevelPriviliges.isEmpty()) {
                        break;
                    }
                    currentLevel++;
                }
            }
            return resultListPriviliges;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, e);
        } finally {
            releaseSession(session);
        }

    }

    // method to sort the list
    public List<PrivilegesEOAVO> sortList(List<PrivilegesEOAVO> listToSort) {
        Collections.sort(listToSort, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) Integer.parseInt(((PrivilegesEOAVO) (o1)).getSortOrder()))
                        .compareTo(Integer.parseInt(((PrivilegesEOAVO) (o2)).getSortOrder()));
            }
        });
        return listToSort;
    }

    /**
     * This method is used for adding role for the inputs given
     * 
     * @param objRoleManagementVO
     * @return
     * @throws RMDServiceException
     */
    @Override
    public void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDDAOException {

        Session session = null;
        String queryString = null;
        Long roleId = 0l;
        GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
        Date sysdate = new Date();
        try {
            session = getHibernateSession(objRoleManagementVO.getStrUserName());
            if ((null != objRoleManagementVO.getRoleDescription()
                    && !objRoleManagementVO.getRoleDescription().isEmpty())
                    && (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty())) {
                queryString = "FROM GetUsrRolesHVO WHERE roleName=:roleName";
                Query query = session.createQuery(queryString.toString()).setParameter(RMDCommonConstants.ROLE_NAME,
                        objRoleManagementVO.getRoleName());
                List<GetUsrRolesHVO> arlUserRolesHVO = query.list();
                if (arlUserRolesHVO.isEmpty()) {
                    GetUsrRolesHVO rolesHVO = new GetUsrRolesHVO();
                    rolesHVO.setRoleName(objRoleManagementVO.getRoleName());
                    rolesHVO.setRoleDesc(objRoleManagementVO.getRoleDescription());
                    rolesHVO.setRowVersion(0);
                    rolesHVO.setStatus(1L);
                    rolesHVO.setStrLanguage(RMDCommonConstants.ENGLISH_LANGUAGE);
                    session.saveOrUpdate(rolesHVO);
                    session.flush();
                    roleId = rolesHVO.getGetUsrRolesSeqId();

                    queryString = "FROM GetUsrUserProductHVO WHERE productCode=:productName";
                    query = session.createQuery(queryString.toString()).setParameter(RMDCommonConstants.PRODUCT_NAME,
                            RMDCommonConstants.DEFAULT_STRING);
                    List<GetUsrUserProductHVO> arlUserProductHVO = query.list();
                    if (null != arlUserProductHVO && !arlUserProductHVO.isEmpty()) {
                        GetUsrUserProductRoleHVO defaultProduct = new GetUsrUserProductRoleHVO();
                        defaultProduct.setGetUsrProduct(arlUserProductHVO.get(0));
                        defaultProduct.setGetUsrRoles(rolesHVO);
                        session.save(defaultProduct);
                        session.flush();
                    }

                } else {
                    // roll back needed or not
                    final String errorCode = RMDCommonUtility
                            .getErrorCode(RMDServiceConstants.ROLE_NAME_ALREADY_PRESENT);
                    throw new RMDDAOException(errorCode);
                }
                for (PrivilegesEOAVO privilege : objRoleManagementVO.getPrivileges()) {

                    objRolePrivilegeHVO = new GetUsrRolePrivilegesHVO();
                    objRolePrivilegeHVO.setLinkRole(roleId);
                    objRolePrivilegeHVO.setLinkPrivileges(privilege.getPrivilegeID());
                    objRolePrivilegeHVO.setRowVersion(0);
                    objRolePrivilegeHVO.setSortOrder(Long.parseLong(privilege.getSortOrder()));
                    session.save(objRolePrivilegeHVO);
                    session.flush();
                }

            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);

            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
                throw new RMDDAOException(errorCode);
            }
        } finally {

            releaseSession(session);
        }

    }

    /**
     * This method is used for updating role for the inputs given
     * 
     * @param objRoleManagementRequestType
     * @return
     * @throws RMDDAOException
     */
    @Override
    public void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDDAOException {
        GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
        Session session = null;
        String queryString = null;
        Query query = null;
        Date sysdate = new Date();
        try {
            session = getHibernateSession(objRoleManagementVO.getStrUserName());
            if ((null != objRoleManagementVO.getRoleDescription()
                    && !objRoleManagementVO.getRoleDescription().isEmpty())
                    || (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty())) {
                // validation needed or not
                if (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty()) {
                    queryString = "FROM GetUsrRolesHVO WHERE roleName=:roleName";
                    query = session.createQuery(queryString.toString()).setParameter(RMDCommonConstants.ROLE_NAME,
                            objRoleManagementVO.getRoleName());
                    List<GetUsrRolesHVO> arlUserRolesHVO = query.list();
                    if (arlUserRolesHVO.isEmpty()) {
                        queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId=:roleId";
                        query = session.createQuery(queryString.toString()).setParameter(RMDCommonConstants.ROLE_ID,
                                Long.valueOf(objRoleManagementVO.getRoleId()));
                        GetUsrRolesHVO editedRole = (GetUsrRolesHVO) query.list().get(0);
                        editedRole.setRoleName(objRoleManagementVO.getRoleName());
                        if (null != objRoleManagementVO.getRoleDescription()
                                && !objRoleManagementVO.getRoleDescription().isEmpty()) {
                            editedRole.setRoleDesc(objRoleManagementVO.getRoleDescription());
                        }
                        session.update(editedRole);
                        session.flush();
                    } else {
                        throw new RMDDAOException(RMDServiceConstants.ROLE_NAME_ALREADY_PRESENT);
                    }
                } else if (null != objRoleManagementVO.getRoleDescription()
                        && !objRoleManagementVO.getRoleDescription().isEmpty()) {
                    queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId=:roleId";
                    query = session.createQuery(queryString.toString()).setParameter(RMDCommonConstants.ROLE_ID,
                            Long.valueOf(objRoleManagementVO.getRoleId()));
                    GetUsrRolesHVO editedRole = (GetUsrRolesHVO) query.list().get(0);
                    if (null != objRoleManagementVO.getRoleDescription()) {
                        editedRole.setRoleDesc(objRoleManagementVO.getRoleDescription());
                    }
                    session.update(editedRole);
                    session.flush();
                }

            }
            queryString = "DELETE FROM GetUsrRolePrivilegesHVO RP WHERE RP.getUsrRoles.getUsrRolesSeqId = :roleId";
            query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID, Long.valueOf(objRoleManagementVO.getRoleId()));
            query.executeUpdate();
            for (PrivilegesEOAVO privilege : objRoleManagementVO.getPrivileges()) {

                objRolePrivilegeHVO = new GetUsrRolePrivilegesHVO();
                objRolePrivilegeHVO.setLinkRole(Long.valueOf(objRoleManagementVO.getRoleId()));
                objRolePrivilegeHVO.setLinkPrivileges(privilege.getPrivilegeID());
                objRolePrivilegeHVO.setRowVersion(0);
                objRolePrivilegeHVO.setSortOrder(Long.parseLong(privilege.getSortOrder()));
                session.save(objRolePrivilegeHVO);
                session.flush();

            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);
            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
                throw new RMDDAOException(errorCode);
            }
        } finally {
            releaseSession(session);
        }

    }

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method returns the list of roles which have Case
     *                   Management Privilege by querying the database.
     */
    @Override
    public List<RolesVO> getCaseMgmtRoles() throws RMDDAOException {
        Session objSession = null;
        List<RolesVO> cmPreviligeRolesList = new ArrayList<RolesVO>();
        try {

            objSession = getHibernateSession();
            StringBuilder getCMPreviligeRolesQry = new StringBuilder();
            getCMPreviligeRolesQry.append(
                    " SELECT GET_USR_ROLES_SEQ_ID,ROLE_NAME FROM GET_USR_ROLES , GET_USR_ROLE_PRIVILEGES , GET_USR_PRIVILEGE ");
            getCMPreviligeRolesQry.append(
                    "WHERE  LINK_ROLES=GET_USR_ROLES_SEQ_ID AND LINK_PRIVILEGES=GET_USR_PRIVILEGE_SEQ_ID AND PRIVILEGE_NAME=:previlige");
            Query cmPreviligeCheckHqry = objSession.createSQLQuery(getCMPreviligeRolesQry.toString());
            cmPreviligeCheckHqry.setParameter(RMDCommonConstants.PREVILIGE,
                    getLookUpValue(RMDCommonConstants.CASE_MANAGEMENT_PRIVILEGE));
            List<Object[]> rolesList = cmPreviligeCheckHqry.list();

            for (Object[] currentRole : rolesList) {
                RolesVO role = new RolesVO();
                role.setGetUsrRolesSeqId(RMDCommonUtility.convertObjectToLong(currentRole[0]));
                role.setRoleName(RMDCommonUtility.convertObjectToString(currentRole[1]));
                cmPreviligeRolesList.add(role);
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);
            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES);
                throw new RMDDAOException(errorCode);
            }
        } finally {
            releaseSession(objSession);
        }

        return cmPreviligeRolesList;
    }

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method is used for fetching the EOA UserId by querying
     *                   the database.
     */

    @Override
    public String getEoaUserName(String userId) throws RMDDAOException {

        Session objSession = null;
        String eoaUserName = null;
        try {
            objSession = getHibernateSession();

            eoaUserName = getEoaUserName(objSession, userId);

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);
            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES);
                throw new RMDDAOException(errorCode);
            }
        } finally {
            releaseSession(objSession);
        }
        return eoaUserName;
    }

    public String getLookUpValue(String listName) {

        List<GetSysLookupVO> arlSysLookUp = new ArrayList<GetSysLookupVO>();
        String lookupValue = RMDCommonConstants.EMPTY_STRING;
        try {

            arlSysLookUp = popupListAdminDAO.getPopupListValues(listName);
            if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {

                for (Iterator iterator = arlSysLookUp.iterator(); iterator.hasNext();) {
                    GetSysLookupVO objSysLookupVO = (GetSysLookupVO) iterator.next();
                    lookupValue = objSysLookupVO.getLookValue();

                }

            }

        } catch (RMDDAOException e) {
            throw e;
        }
        return lookupValue;
    }
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method returns the list of roles which have EOA Onsite 
     *                   Privilege enabled roles by querying the database.
     */
    @Override
    public List<RolesVO> getEoaOnsiteRoles() throws RMDDAOException {
        Session objSession = null;
        List<RolesVO> cmPreviligeRolesList = new ArrayList<RolesVO>();
        try {

            objSession = getHibernateSession();
            StringBuilder getCMPreviligeRolesQry = new StringBuilder();
            getCMPreviligeRolesQry.append(
                    " SELECT GET_USR_ROLES_SEQ_ID,ROLE_NAME FROM GET_USR_ROLES , GET_USR_ROLE_PRIVILEGES , GET_USR_PRIVILEGE ");
            getCMPreviligeRolesQry.append(
                    "WHERE  LINK_ROLES=GET_USR_ROLES_SEQ_ID AND LINK_PRIVILEGES=GET_USR_PRIVILEGE_SEQ_ID AND PRIVILEGE_NAME=:previlige");
            Query cmPreviligeCheckHqry = objSession.createSQLQuery(getCMPreviligeRolesQry.toString());
            cmPreviligeCheckHqry.setParameter(RMDCommonConstants.PREVILIGE,RMDCommonConstants.SUBMENU_ADMINISTRATION_EOAONSITE);
            List<Object[]> rolesList = cmPreviligeCheckHqry.list();

            for (Object[] currentRole : rolesList) {
                RolesVO role = new RolesVO();
                role.setGetUsrRolesSeqId(RMDCommonUtility.convertObjectToLong(currentRole[0]));
                role.setRoleName(RMDCommonUtility.convertObjectToString(currentRole[1]));
                cmPreviligeRolesList.add(role);
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EOA_ONSITE_PREVILIGE_ROLES);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
            if (e instanceof RMDDAOException) {
                RMDDAOException rmd = (RMDDAOException) e;
                ErrorDetail errDetails = rmd.getErrorDetail();
                throw new RMDDAOException(errDetails.getErrorCode(), new String[] {}, null, null,
                        RMDCommonConstants.MINOR_ERROR);
            } else {
                final String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EOA_ONSITE_PREVILIGE_ROLES);
                throw new RMDDAOException(errorCode);
            }
        } finally {
            releaseSession(objSession);
        }

        return cmPreviligeRolesList;
    }

}
