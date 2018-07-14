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
import java.sql.Timestamp;
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
import com.ge.trans.eoa.services.admin.dao.intf.RolesDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.ErrorDetail;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
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
public class RolesDAOImpl extends RMDCommonDAO implements RolesDAOIntf {
	private static final RMDLogger LOG = RMDLogger.getLogger(RolesDAOImpl.class);
    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -5259728318291027569L;

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.dao.intf.RolesDAOIntf#
     * deleteAllRolePrivileges
     * (com.ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO)
     */
    @Autowired
    PopupListAdminBOIntf popupListAdminBOIntf;

    @Override
    public int deleteAllRolePrivileges(final RoleServiceVO objRoleServiceVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "DELETE FROM GetUsrRolePrivilegesHVO RP WHERE RP.getUsrRoles.getUsrRolesSeqId = :roleId"; // and
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID, objRoleServiceVO.getGetUsrRolesSeqId());
            return query.executeUpdate();
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("deleteAllRolePrivileges", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("deleteAllRolePrivileges", e);
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
    public List<RoleServiceVO> getAllRoles(final String language) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "SELECT GET_USR_ROLES_SEQ_ID,ROLE_NAME,ROLE_DESC,STATUS,ROW_VERSION,LAST_UPDATED_BY,to_char(LAST_UPDATED_DATE) as LAST_UPDATED_TIME,CREATED_BY,CREATION_DATE,SORT_ORDER FROM GET_USR.GET_USR_ROLES ORDER BY sortOrder"; // where
            Query query = session.createQuery(queryString);
            List<GetUsrRolesHVO> arlUserRolesHVO = query.list();
            List<RoleServiceVO> arlUserRolesVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(arlUserRolesHVO)) {
                arlUserRolesVO = new ArrayList<RoleServiceVO>();
                RoleServiceVO objUserRoleVO = null;
                for (GetUsrRolesHVO objUserRoleHVO : arlUserRolesHVO) {
                    objUserRoleVO = new RoleServiceVO();
                    objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                    objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                    objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                    objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
                    objUserRoleVO.setLastUpdatedBy(objUserRoleHVO.getLastUpdatedBy());
                   // objUserRoleVO.setLastUpdatedTime(objUserRoleHVO.getLastUpdatedDate());
                    
                    arlUserRolesVO.add(objUserRoleVO);
                }
            }
            return arlUserRolesVO;
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("getAllRoles", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getAllRoles", e);
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
    public RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GetUsrRolesHVO WHERE roleName = :roleName"; // and
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_NAME, roleService.getRoleName());
            List<GetUsrRolesHVO> objUserRolesHVO = query.list();
            RoleServiceVO objUserRoleVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(objUserRolesHVO)) {
                objUserRoleVO = new RoleServiceVO();
                GetUsrRolesHVO objUserRoleHVO = objUserRolesHVO.get(0);
                objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
            }
            return objUserRoleVO;
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("getMatchingRole", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, roleService.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getMatchingRole", e);
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
    public RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            String queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId = :roleId"; // and
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID, roleId);
            List<GetUsrRolesHVO> objUserRolesHVO = query.list();
            RoleServiceVO objUserRoleVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(objUserRolesHVO)) {
                objUserRoleVO = new RoleServiceVO();
                GetUsrRolesHVO objUserRoleHVO = objUserRolesHVO.get(0);
                objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleDesc());
                objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
            }
            return objUserRoleVO;
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("getRoleInfo", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getRoleInfo", e);
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
    public List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDDAOException {
        Session session = null;
        try {
            List<ElementVO> rolePriviliges = null;
            ElementVO elementVO = null;
            session = getHibernateSession();
            // added sort_order for Permanent Tab Sort Order Implementation
            String queryString = "SELECT TBLMASTERPRIVILEGE.PRIVILEGE_NAME AS ROLEPRIVILEGE,TBLMASTERPRIVILEGE.RESOURCE_TYPE AS ACCESSLEVEL,TBLROLEPRIVILEGE.SORT_ORDER AS SORTORDER FROM GET_USR.GET_USR_ROLE_PRIVILEGES TBLROLEPRIVILEGE,GET_USR.GET_USR_PRIVILEGE TBLMASTERPRIVILEGE WHERE  TBLROLEPRIVILEGE.LINK_ROLES=:roleId AND TBLROLEPRIVILEGE.link_privileges=TBLMASTERPRIVILEGE.GET_USR_PRIVILEGE_SEQ_ID AND   TBLMASTERPRIVILEGE.STATUS ='1' ORDER BY TBLROLEPRIVILEGE.SORT_ORDER ASC";

            Query query = session.createSQLQuery(queryString).setParameter(RMDCommonConstants.ROLE_ID, roleId);
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
        	LOG.error("getRolePrivileges", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getRolePrivileges", e);
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
    public List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userSeqId, final String language)
            throws RMDDAOException {
        Session session = null;
        try {
            List<RolePrivilegeServiceVO> userPriviliges = null;
            session = getHibernateSession();

            String queryString = "SELECT rolePrivileges, accessLevel FROM GetUsrRolePrivilegesHVO WHERE getUsrRoles.getUsrRolesSeqId "
                    + "IN (SELECT getUsrRoles.getUsrRolesSeqId FROM GetUsrUserRolesHVO WHERE getUsrUsers.getUsrUsersSeqId  = :userSeqId "
                    + "AND getUsrRoles.getUsrRolesSeqId IN (SELECT getUsrRolesSeqId FROM GetUsrRolesHVO WHERE status = 1 ))";

            // Removing the language check for roles
            Query query = session.createQuery(queryString).setParameter(RMDCommonConstants.USER_SEQ_ID, userSeqId);
            List usrRolePrivileges = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(usrRolePrivileges)) {
                userPriviliges = new ArrayList<RolePrivilegeServiceVO>();
                RolePrivilegeServiceVO rolePrivilege = null;
                for (Object userRolePrivilege : usrRolePrivileges) {
                    rolePrivilege = new RolePrivilegeServiceVO();
                    Object[] resultColumns = (Object[]) userRolePrivilege;
                    rolePrivilege.setRolePrivilege((String) resultColumns[0]);
                    rolePrivilege.setAccessLevel((String) resultColumns[1]);
                    userPriviliges.add(rolePrivilege);
                }
            }
            return userPriviliges;
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("getUsersPermittedResources", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getUsersPermittedResources", e);
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
     * (com.ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO,
     * java.util.List)
     */
    @Override
    public int saveRolePrivileges(final RoleServiceVO objRoleServiceVO,
            final List<RolePrivilegeServiceVO> rolesPrivileges) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession(objRoleServiceVO.getStrUserName());
            GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
            for (RolePrivilegeServiceVO objRolePrivilegeServiceVO : rolesPrivileges) {
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
        	LOG.error("saveRolePrivileges", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("saveRolePrivileges", e);
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
    public Serializable saveRoleInfo(final RoleServiceVO objRoleServiceVO) throws RMDDAOException {
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
            RoleServiceVO roleServiceVOPersisted = new RoleServiceVO();
            BeanUtils.copyProperties(roleServiceVOPersisted, rolesHVO);
            return roleServiceVOPersisted;
        } catch (RMDDAOConnectionException ex) {
        	LOG.error("saveRoleInfo", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("saveRoleInfo", e);
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
            // queryString.append("and rp.getUsrRoles.status = 1");
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
        	LOG.error("getRolesHomePages", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getRolesHomePages", e);
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
        	LOG.error("getRoleSecNavPrivileges", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error("getRoleSecNavPrivileges", e);
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
    public List<PrivilegesVO> getAllPrivileges(String roleId) throws RMDDAOException {
        Session session = null;
        int currentLevel = 1;
        StringBuilder queryString = new StringBuilder();
        boolean islevelOneFlag = true;
        List<PrivilegesVO> resultListPriviliges = new ArrayList<PrivilegesVO>();
        Map<Long, List<PrivilegesVO>> privilegesMap = new HashMap<Long, List<PrivilegesVO>>();
        PrivilegesVO privilegesVO = null;
        List<Long> currentLevelPriviliges = new ArrayList<Long>();
        List<PrivilegesVO> listPrivilegesToSort = new ArrayList<PrivilegesVO>();
        List<PrivilegesVO> newChildList = null;
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
                resultListPriviliges = new ArrayList<PrivilegesVO>();
                Object[] resultColumns = null;
                // for each privilege iterate through the loop
                for (Object currentPrivilege : allPrivileges) {
                    resultColumns = (Object[]) currentPrivilege;
                    privilegesVO = new PrivilegesVO();
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
                                List<PrivilegesVO> sortedList = sortList(listPrivilegesToSort);
                                for (PrivilegesVO childPrivilege : sortedList) {
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
                                    List<PrivilegesVO> tempChildList = privilegesMap.get(privilegesVO.getParentId());
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
                                List<PrivilegesVO> tempChildList = privilegesMap.get(privilegesVO.getParentId());
                                tempChildList.add(privilegesVO);
                                privilegesMap.put(privilegesVO.getParentId(), tempChildList);
                            }

                        } else {
                            // once the new parent come setting sort order older
                            // parents child which role privileges sort order is
                            // null
                            if (null != listPrivilegesToSort && !listPrivilegesToSort.isEmpty()) {
                                Long currentParentId = listPrivilegesToSort.get(0).getParentId();
                                List<PrivilegesVO> tempChildList = privilegesMap.get(currentParentId);
                                List<PrivilegesVO> sortedList = sortList(listPrivilegesToSort);
                                for (PrivilegesVO childPrivilege : sortedList) {
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
                            newChildList = new ArrayList<PrivilegesVO>();
                            newChildList.add(privilegesVO);
                            privilegesMap.put(privilegesVO.getParentId(), newChildList);

                        }
                    }
                }
                // if any privilege is left to sort in list after for loop
                // adding that also to map
                if (null != listPrivilegesToSort && !listPrivilegesToSort.isEmpty()) {
                    Long currentParentId = listPrivilegesToSort.get(0).getParentId();
                    List<PrivilegesVO> tempChildList = privilegesMap.get(currentParentId);
                    List<PrivilegesVO> sortedList = sortList(listPrivilegesToSort);
                    for (PrivilegesVO childPrivilege : sortedList) {
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
                            List<PrivilegesVO> ChildList = privilegesMap.get(parentId);
                            privilegesMap.remove(parentId);
                            // for each child set the level and add to result
                            // and save its id as next level parent
                            for (PrivilegesVO childPrivilege : ChildList) {
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
        	LOG.error("getAllPrivileges", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
        	LOG.error("getAllPrivileges", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, e);
        } finally {
            releaseSession(session);
        }

    }

    // method to sort the list
    public List<PrivilegesVO> sortList(List<PrivilegesVO> listToSort) {
        Collections.sort(listToSort, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) Integer.parseInt(((PrivilegesVO) (o1)).getSortOrder()))
                        .compareTo(Integer.parseInt(((PrivilegesVO) (o2)).getSortOrder()));
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
    public void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDDAOException {

        Session session = null;
        String queryString = null;
        Long roleId = 0l;
        GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
        try {
            session = getHibernateSession(objRoleManagementVO.getStrUserName());
            if ((null != objRoleManagementVO.getRoleDescription()
                    && !objRoleManagementVO.getRoleDescription().isEmpty())
                    && (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty())) {
                queryString = "FROM GetUsrRolesHVO WHERE roleName=:roleName";
                Query query = session.createQuery(queryString).setParameter(RMDCommonConstants.ROLE_NAME,
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
                    query = session.createQuery(queryString).setParameter(RMDCommonConstants.PRODUCT_NAME,
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
                for (PrivilegesVO privilege : objRoleManagementVO.getPrivileges()) {

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
        	LOG.error("addOrCopyRole", ex);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
        	LOG.error("addOrCopyRole", e);
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
    public void editRole(RoleManagementVO objRoleManagementVO) throws RMDDAOException {
        GetUsrRolePrivilegesHVO objRolePrivilegeHVO = null;
        Session session = null;
        String queryString = null;
        Query query = null;
        try {
            session = getHibernateSession(objRoleManagementVO.getStrUserName());
            if ((null != objRoleManagementVO.getRoleDescription()
                    && !objRoleManagementVO.getRoleDescription().isEmpty())
                    || (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty())) {
                // validation needed or not
                if (null != objRoleManagementVO.getRoleName() && !objRoleManagementVO.getRoleName().isEmpty()) {
                    queryString = "FROM GetUsrRolesHVO WHERE roleName=:roleName";
                    query = session.createQuery(queryString).setParameter(RMDCommonConstants.ROLE_NAME,
                            objRoleManagementVO.getRoleName());
                    List<GetUsrRolesHVO> arlUserRolesHVO = query.list();
                    if (arlUserRolesHVO.isEmpty()) {
                        queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId=:roleId";
                        query = session.createQuery(queryString).setParameter(RMDCommonConstants.ROLE_ID,
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
                    query = session.createQuery(queryString).setParameter(RMDCommonConstants.ROLE_ID,
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
            for (PrivilegesVO privilege : objRoleManagementVO.getPrivileges()) {

                objRolePrivilegeHVO = new GetUsrRolePrivilegesHVO();
                objRolePrivilegeHVO.setLinkRole(Long.valueOf(objRoleManagementVO.getRoleId()));
                objRolePrivilegeHVO.setLinkPrivileges(privilege.getPrivilegeID());
                objRolePrivilegeHVO.setRowVersion(0);
                objRolePrivilegeHVO.setSortOrder(Long.parseLong(privilege.getSortOrder()));
                session.save(objRolePrivilegeHVO);
                session.flush();

            }
           
            long start = System.currentTimeMillis();
            Timestamp timestamp  = new Timestamp(start) ;
            Date calculationDate = new java.util.Date(timestamp.getTime());
               
            
            queryString = "FROM GetUsrRolesHVO WHERE getUsrRolesSeqId=:roleId";
            query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.ROLE_ID,
                    Long.valueOf(objRoleManagementVO.getRoleId()));
            GetUsrRolesHVO editedRole = (GetUsrRolesHVO) query.list().get(0);
            	editedRole.setLastUpdatedBy(objRoleManagementVO.getStrUserName());
                editedRole.setLastUpdatedDate(calculationDate);
            session.update(editedRole);
            session.flush();
            
            

        } catch (RMDDAOConnectionException ex) {
        	LOG.error("editRole", ex);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
        	LOG.error("editRole", e);
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

	@Override
	public void deleteRole(RoleManagementVO objRoleManagementVO)
			throws RMDDAOException {
		
		Session session = null;
        String queryString = null;
        Query query = null;
        
        try {
            session = getHibernateSession(objRoleManagementVO.getStrUserName());
            if (!objRoleManagementVO.getRoleId().isEmpty()){
            	 queryString = "DELETE FROM GetUsrRolesHVO WHERE getUsrRolesSeqId=:roleId";
                 query = session.createQuery(queryString);
                 query.setParameter(RMDCommonConstants.ROLE_ID, Long.valueOf(objRoleManagementVO.getRoleId()));
                 query.executeUpdate();
                 session.flush();
            	}
            }
        catch (RMDDAOConnectionException ex) {
        	LOG.error("deleteRole", ex);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, ex);
        } catch (Exception e) {
        	LOG.error("deleteRole", e);
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
	
	@Override
	public List<DeleteRolesResponseVO> getDeleteRoleList(
			RoleManagementVO objRoleManagementVO) {

		Session session = null;
		Query deleteRolesHqry = null;
		List<Object[]> resultList = null;
		List<DeleteRolesResponseVO> objVOlist = null;

		session = getHibernateSession();

		StringBuilder deleteRolesQry = new StringBuilder();
		try {
			deleteRolesQry
					.append("select user_id,first_name,last_name,get_usr_users_seq_id from get_usr_users where get_usr_users_seq_id in (Select link_users from get_usr_user_roles where link_users in (select link_users  from  get_usr_user_roles ");
			deleteRolesQry
					.append("where link_users in (select link_users from get_usr_user_preference where user_preference_type = 'Role' AND user_preference_value =:roleId) ");
			deleteRolesQry
					.append("group by link_users  having count(link_users) = 1))");

			deleteRolesHqry = session.createSQLQuery(deleteRolesQry.toString());
			deleteRolesHqry.setParameter(RMDCommonConstants.ROLE_ID,
					Long.valueOf(objRoleManagementVO.getRoleId()));
			deleteRolesHqry.setFetchSize(1000);
			resultList = deleteRolesHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				objVOlist = new ArrayList<DeleteRolesResponseVO>();
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					DeleteRolesResponseVO drVO = new DeleteRolesResponseVO();
					final Object[] DeleteRolesResponseVO = obj.next();
					drVO.setUserId(RMDCommonUtility
							.convertObjectToString(DeleteRolesResponseVO[0]));
					drVO.setFirstName(RMDCommonUtility
							.convertObjectToString(DeleteRolesResponseVO[1]));
					drVO.setLastName(RMDCommonUtility
							.convertObjectToString(DeleteRolesResponseVO[2]));
					drVO.setUserSeqId(RMDCommonUtility
							.convertObjectToString(DeleteRolesResponseVO[3]));
					//Session sessionCustomers = null;
					try {

						//sessionCustomers = getHibernateSession();
						Query customersFetchQuery = null;
						List<String> customersResultList = null;
						StringBuilder varname1 = new StringBuilder();
						varname1.append("select s_name from TABLE_BUS_ORG where objid in (select link_customers from get_usr_user_customers where link_users = (select get_usr_users_seq_id  from get_usr_users where user_id =:userId))");
						customersFetchQuery = session
								.createSQLQuery(varname1.toString());
						customersFetchQuery
								.setParameter(
										RMDCommonConstants.DELETE_ROLE_USER_ID,
										RMDCommonUtility
												.convertObjectToString(DeleteRolesResponseVO[0]));
						customersFetchQuery.setFetchSize(100);
						customersResultList = customersFetchQuery.list();
						ArrayList<String> customersArray = new ArrayList<String>();
						if (RMDCommonUtility
								.isCollectionNotEmpty(customersResultList)) {
							for (final Iterator<String> objcust = customersResultList
									.iterator(); objcust.hasNext();) {
								String customersVO = objcust.next();
								customersArray.add(RMDCommonUtility
										.convertObjectToString(customersVO));
							}
						} else {
							customersArray.add("ALL");
						}
						drVO.setUserCustomers(customersArray);
					} catch (Exception ex) {
						LOG.error("getDeleteRoleList", ex);
						ex.printStackTrace();
					} 

					objVOlist.add(drVO);

				}

			}

		} catch (Exception e) {
			LOG.error("getDeleteRoleList", e);
			if (e instanceof RMDDAOException) {
				RMDDAOException rmd = (RMDDAOException) e;
				ErrorDetail errDetails = rmd.getErrorDetail();
				throw new RMDDAOException(errDetails.getErrorCode(),
						new String[] {}, null, null,
						RMDCommonConstants.MINOR_ERROR);
			} else {
				final String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
				throw new RMDDAOException(errorCode);
			}
		} finally {
			releaseSession(session);
		}

		return objVOlist;

	}

	@Override
	public String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDDAOException {

		String response = RMDCommonConstants.EMPTY_STRING;
		Session session = null;
		Query deleteRolesUpHqry = null;
		String roleId = arlDeleteRolesUpdateVO.get(0).getCurrentRole();
		Long roleIdLong = Long.valueOf(roleId);
		
		try {
			session = getHibernateSession();
			if (!arlDeleteRolesUpdateVO.get(0).getUserId().equals(RMDCommonConstants.DELETE_NO_VAL)) {
				try {
					for (DeleteRolesUpdateVO deleteRolesResponseVO : arlDeleteRolesUpdateVO) {
						Query updateUserQry = null;
						updateUserQry = session.createSQLQuery("UPDATE get_usr_user_roles SET link_roles =:changedRoleId  WHERE link_users =:userSeqId and link_roles =:roleId");
						updateUserQry.setParameter("userSeqId", deleteRolesResponseVO.getUserSeqId());
						updateUserQry.setParameter(RMDCommonConstants.ROLE_ID, Long.valueOf(deleteRolesResponseVO.getCurrentRole()));
						updateUserQry.setParameter(RMDCommonConstants.DELETE_ROLE_CHANGED_ROLE_ID, Long.valueOf(deleteRolesResponseVO.getChangedRoleId()));
						updateUserQry.executeUpdate();
						
						updateUserQry = session.createSQLQuery("UPDATE get_usr_user_preference SET user_preference_value =:changedRoleId WHERE link_users =:userSeqId and user_preference_type ='Role'");
						updateUserQry.setParameter("userSeqId", deleteRolesResponseVO.getUserSeqId());
						updateUserQry.setParameter(RMDCommonConstants.DELETE_ROLE_CHANGED_ROLE_ID, Long.valueOf(deleteRolesResponseVO.getChangedRoleId()));
						updateUserQry.executeUpdate();
						
						updateUserQry = session.createSQLQuery("UPDATE get_usr_users SET last_updated_by =:userId, last_updated_date = sysdate WHERE get_usr_users_seq_id =:userSeqId");
						updateUserQry.setParameter("userSeqId", deleteRolesResponseVO.getUserSeqId());
						updateUserQry.setParameter("userId", userId);
						updateUserQry.executeUpdate();
					}

				} catch (Exception e) {
					LOG.error("deleteRoleUpdateList", e);
					throw new RMDDAOException("deleteRoleUpdateList", e);
				} 
			}

			try {
				List<Object> resultList = null;
				Query getUsrPreferenceQry = session.createSQLQuery("select link_users from get_usr_user_preference where user_preference_type = 'Role' and user_preference_value =:roleId");
				getUsrPreferenceQry.setParameter(RMDCommonConstants.ROLE_ID, roleIdLong);
				getUsrPreferenceQry.setFetchSize(20);
				resultList = getUsrPreferenceQry.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					for (final Object linkUserId : resultList) {
					
						StringBuilder StringDel = new StringBuilder();
						StringDel
								.append("UPDATE get_usr_user_preference SET user_preference_value = (select link_roles from (select link_roles from get_usr_user_roles "
										+ "where link_users =:userId order by link_roles) where rownum = 1) where link_users =:userId and user_preference_type = 'Role' ");
						Query updateUserPreferenceQry = session.createSQLQuery(StringDel.toString());
						updateUserPreferenceQry.setParameter(
								RMDCommonConstants.DELETE_ROLE_USER_ID,
								RMDCommonUtility.convertObjectToString(linkUserId));
						updateUserPreferenceQry.executeUpdate();

					}
				}

			} catch (Exception e) {
				LOG.error("deleteRoleUpdateList", e);
				throw new RMDDAOException("deleteRoleUpdateList", e);
			} 

			session = getHibernateSession();
			deleteRolesUpHqry = session.createSQLQuery("DELETE FROM get_usr_user_roles WHERE link_roles =:roleId");
			deleteRolesUpHqry.setParameter(RMDCommonConstants.ROLE_ID, roleIdLong);
			deleteRolesUpHqry.executeUpdate();
			
			deleteRolesUpHqry = session.createSQLQuery("delete from get_usr_roles  where get_usr_roles_seq_id =:roleId ");
			deleteRolesUpHqry.setParameter(RMDCommonConstants.ROLE_ID, roleIdLong);
			deleteRolesUpHqry.executeUpdate();

			
		} catch (Exception e) {
			LOG.error("deleteRoleUpdateList", e);
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ROLE_MANAGEMENT);
			throw new RMDDAOException(errorCode);
		} finally {
			releaseSession(session);
		}
		return response;
	}

    

}
