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

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.bo.intf.RolesBOIntf;
import com.ge.trans.eoa.services.admin.service.intf.RolesServiceIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
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
public class RolesServiceImpl implements RolesServiceIntf {

    /** rolesBO of Type RolesBOIntf **/
    private RolesBOIntf rolesBO;

    /**
     * @param rolesBO
     */
    public RolesServiceImpl(RolesBOIntf rolesBO) {
        this.rolesBO = rolesBO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.RolesServiceIntf#getAllRoles
     * (java.lang.String)
     */
    @Override
    public List<RoleServiceVO> getAllRoles(final String language) throws RMDServiceException {
        List<RoleServiceVO> arlRoles = null;
        try {
            arlRoles = rolesBO.getAllRoles(language);
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
    public RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDServiceException {
        RoleServiceVO roleInfo = null;
        try {
            roleInfo = rolesBO.getRoleInfo(roleId, language);
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
    public RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDServiceException {
        RoleServiceVO matchingRole = null;
        try {
            return rolesBO.getMatchingRole(roleService);
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
            return rolesBO.getRolePrivileges(roleId, language);
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
    public List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDServiceException {
        List<RolePrivilegeServiceVO> rolePrivileges = null;
        try {
            rolePrivileges = rolesBO.getUsersPermittedResources(userId, language);
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
    public int saveRoleInfo(final RoleServiceVO objRoleServiceVO, final List<RolePrivilegeServiceVO> rolePrivileges)
            throws RMDServiceException {
        int rowsUpdated = 0;
        try {
            rowsUpdated = rolesBO.saveRoleInfo(objRoleServiceVO, rolePrivileges);
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
            homePages = rolesBO.getRolesHomePages(roleId, language);
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
            return rolesBO.getRoleSecNavPrivileges(roleId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return rolePrivileges;
    }

    @Override
    public List<PrivilegesVO> getAllPrivileges(String roleid) throws RMDServiceException {
        try {
            return rolesBO.getAllPrivileges(roleid);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }

    @Override
    public void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException {
        try {
            rolesBO.addOrCopyRole(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }

    @Override
    public void editRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException {
        try {
            rolesBO.editRole(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }
    
    @Override
    public void deleteRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException {
        try {
            rolesBO.deleteRole(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }
    
    @Override
    public List<DeleteRolesResponseVO> getDeleteRoleList(RoleManagementVO objRoleManagementVO) throws RMDServiceException {
        try {
            return rolesBO.getDeleteRoleList(objRoleManagementVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    }
@Override
    public String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDServiceException{
    	try {
           return rolesBO.deleteRoleUpdateList(arlDeleteRolesUpdateVO, userId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            throw new RMDServiceException(ex);
        }
    	
    }


}