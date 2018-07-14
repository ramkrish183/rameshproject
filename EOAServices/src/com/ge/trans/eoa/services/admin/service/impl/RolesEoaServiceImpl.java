/**
 * ============================================================
 * Classification: GE Confidential
 * File : RolesServiceImpl.java
 * Description : Service Impl for Role Management
 *
 * Package : com.ge.trans.rmd.services.admin.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.admin.bo.intf.RolesEoaBOIntf;
import com.ge.trans.eoa.services.admin.service.intf.RolesEoaServiceIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : Service Impl for Role Management
 * @History :
 ******************************************************************************/
public class RolesEoaServiceImpl implements RolesEoaServiceIntf {

    /** rolesBO of Type RolesBOIntf **/
    private RolesEoaBOIntf rolesEoaBOIntf;

    /**
     * @param rolesBO
     */
    public RolesEoaServiceImpl(RolesEoaBOIntf rolesBO) {
        this.rolesEoaBOIntf = rolesBO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#getAllRoles
     * (java.lang.String)
     */
    @Override
    public List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDServiceException {
        List<RoleEoaServiceVO> arlRoles = null;
        try {
            arlRoles = rolesEoaBOIntf.getAllRoles(language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return arlRoles;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#getRoleInfo
     * (java.lang.Long, java.lang.String)
     */
    @Override
    public RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDServiceException {
        RoleEoaServiceVO roleInfo = null;
        try {
            roleInfo = rolesEoaBOIntf.getRoleInfo(roleId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return roleInfo;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * getMatchingRole(com.ge.trans.rmd.services.admin.service.valueobjects.
     * RoleServiceVO)
     */
    @Override
    public RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDServiceException {
        RoleEoaServiceVO matchingRole = null;
        try {
            return rolesEoaBOIntf.getMatchingRole(roleService);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, roleService.getStrLanguage());
        }
        return matchingRole;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * getRolePrivileges(java.lang.Long, java.lang.String)
     */
    @Override
    public List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDServiceException {
        List<ElementVO> rolePrivileges = null;
        try {
            return rolesEoaBOIntf.getRolePrivileges(roleId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return rolePrivileges;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * getUsersPermittedResources(java.lang.Long, java.lang.String)
     */
    @Override
    public List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDServiceException {
        List<RolePrivilegeEoaServiceVO> rolePrivileges = null;
        try {
            rolePrivileges = rolesEoaBOIntf.getUsersPermittedResources(userId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return rolePrivileges;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * saveRoleInfo(com.ge.trans.rmd.services.admin.service.valueobjects.
     * RoleServiceVO, java.util.List)
     */
    @Override
    public int saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO,
            final List<RolePrivilegeEoaServiceVO> rolePrivileges) throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = rolesEoaBOIntf.saveRoleInfo(objRoleServiceVO, rolePrivileges);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objRoleServiceVO.getStrLanguage());
        }
        return rowsUpdated;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * getRolesHomePages(java.lang.Long)
     */
    @Override
    public List<String> getRolesHomePages(final Long roleId, final String language) throws RMDServiceException {
        List<String> homePages = null;
        try {
            homePages = rolesEoaBOIntf.getRolesHomePages(roleId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return homePages;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#
     * getRolePrivileges(java.lang.Long, java.lang.String)
     */
    @Override
    public List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language)
            throws RMDServiceException {
        List<ElementVO> rolePrivileges = null;
        try {
            return rolesEoaBOIntf.getRoleSecNavPrivileges(roleId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return rolePrivileges;
    }

    @Override
    public List<PrivilegesEOAVO> getAllPrivileges(String roleid) throws RMDServiceException {
        try {
            return rolesEoaBOIntf.getAllPrivileges(roleid);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }

    @Override
    public void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDServiceException {
        try {
            rolesEoaBOIntf.addOrCopyRole(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }

    @Override
    public void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDServiceException {
        try {
            rolesEoaBOIntf.editRole(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method returns the list of roles which have Case
     *                   Management Privilege by calling the getCaseMgmtRoles()
     *                   method of BO Layer.
     */
    @Override
    public List<RolesVO> getCaseMgmtRoles() throws RMDServiceException {
        List<RolesVO> rolesList = null;
        try {
            rolesList = rolesEoaBOIntf.getCaseMgmtRoles();

        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return rolesList;

    }

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDServiceException
     * @Description:This method is used for fetching the EOA UserId by calling
     *                   the getEoaUserId() method of BO Layer.
     */

    @Override
    public String getEoaUserName(String userId) throws RMDServiceException {
        String eoaUserName = null;
        try {
            eoaUserName = rolesEoaBOIntf.getEoaUserName(userId);
        } catch (RMDBOException e) {

            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return eoaUserName;
    }
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method returns the list of roles which have EOA Onsite
     *                   Privilege enabled roles by calling the getEoaOnsiteRoles()
     *                   method of BO Layer.
     */
    @Override
    public List<RolesVO> getEoaOnsiteRoles() throws RMDServiceException {
        List<RolesVO> rolesList = null;
        try {
            rolesList = rolesEoaBOIntf.getEoaOnsiteRoles();

        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return rolesList;

    }

}