/**
 * =================================================================
 * Classification: GE Confidential
 * File : RoleBOImpl.java
 * Description : BO Impl for Role Management
 *
 * Package : com.ge.trans.rmd.services.admin.bo.impl
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
package com.ge.trans.eoa.services.admin.bo.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.UPDATE_SUCCESS;

import java.util.List;

import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.admin.bo.intf.RolesBOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.RolesDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : BO0 Impl for Role Management
 * @History :
 ******************************************************************************/
public class RolesBOImpl implements RolesBOIntf {

    /** rolesDAO of Type RolesDAOIntf **/
    private RolesDAOIntf rolesDAO;

    /**
     * @param rolesDAO
     */
    public RolesBOImpl(RolesDAOIntf rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getAllRoles(java.lang
     * .String)
     */
    @Override
    public List<RoleServiceVO> getAllRoles(final String language) throws RMDBOException {
        try {
            return rolesDAO.getAllRoles(language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }
    	
    
    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getRoleInfo(java.lang
     * .Long, java.lang.String)
     */
    @Override
    public RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDBOException {
        try {
            return rolesDAO.getRoleInfo(roleId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getRolePrivileges(
     * java.lang.Long, java.lang.String)
     */
    @Override
    public List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDBOException {
        try {
            return rolesDAO.getRolePrivileges(roleId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#
     * getUsersPermittedResources(java.lang.Long, java.lang.String)
     */
    @Override
    public List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDBOException {
        try {
            return rolesDAO.getUsersPermittedResources(userId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#saveRoleInfo(com.ge.
     * trans.rmd.services.admin.service.valueobjects.RoleServiceVO,
     * java.util.List)
     */
    @Override
    public int saveRoleInfo(final RoleServiceVO objRoleServiceVO, final List<RolePrivilegeServiceVO> rolePrivileges)
            throws RMDBOException {
        try {
            RoleServiceVO objRoleServiceVOPersisited = (RoleServiceVO) rolesDAO.saveRoleInfo(objRoleServiceVO);
            objRoleServiceVO.setGetUsrRolesSeqId(objRoleServiceVOPersisited.getGetUsrRolesSeqId());
            rolesDAO.deleteAllRolePrivileges(objRoleServiceVO);
            if (!RMDCommonUtility.isCollectionNotEmpty(rolePrivileges)) {
                return UPDATE_SUCCESS;
            }
            rolesDAO.saveRolePrivileges(objRoleServiceVO, rolePrivileges);
            return UPDATE_SUCCESS;
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_ROLE_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRoleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getMatchingRole(com.
     * ge.trans.rmd.services.admin.service.valueobjects.RoleServiceVO)
     */
    @Override
    public RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDBOException {
        try {
            return rolesDAO.getMatchingRole(roleService);
        } catch (RMDDAOException e) {
            throw e;
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getRolesHomePages(
     * java.lang.Long)
     */
    @Override
    public List<String> getRolesHomePages(final Long roleId, final String language) throws RMDBOException {
        try {
            return rolesDAO.getRolesHomePages(roleId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getRolePrivileges(
     * java.lang.Long, java.lang.String)
     */
    @Override
    public List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language) throws RMDBOException {
        try {
            return rolesDAO.getRoleSecNavPrivileges(roleId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<PrivilegesVO> getAllPrivileges(String roleid) throws RMDBOException {
        try {
            return rolesDAO.getAllPrivileges(roleid);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDBOException {
        try {
            rolesDAO.addOrCopyRole(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public void editRole(RoleManagementVO objRoleManagementVO) throws RMDBOException {
        try {
            rolesDAO.editRole(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

	@Override
	public void deleteRole(RoleManagementVO objRoleManagementVO)
			throws RMDBOException {
		try {
            rolesDAO.deleteRole(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
		
	}
	
	@Override
	public List<DeleteRolesResponseVO> getDeleteRoleList(RoleManagementVO objRoleManagementVO)
			throws RMDBOException {
		try {
            
			return rolesDAO.getDeleteRoleList(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
		
	}
	
	@Override
	public String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDBOException{
		try {
            return rolesDAO.deleteRoleUpdateList(arlDeleteRolesUpdateVO, userId);
        } catch (RMDDAOException e) {
            throw e;
        }
	}

    
}