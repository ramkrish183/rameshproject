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

import com.ge.trans.eoa.services.admin.bo.intf.RolesEoaBOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.RolesEoaDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
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
public class RolesEoaBOImpl implements RolesEoaBOIntf {

    /** rolesDAO of Type RolesDAOIntf **/
    private RolesEoaDAOIntf rolesEoaDAOIntf;

    /**
     * @param rolesDAO
     */
    public RolesEoaBOImpl(RolesEoaDAOIntf rolesDAO) {
        this.rolesEoaDAOIntf = rolesDAO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.RolesBOIntf#getAllRoles(java.lang
     * .String)
     */
    @Override
    public List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDBOException {
        try {
            return rolesEoaDAOIntf.getAllRoles(language);
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
    public RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDBOException {
        try {
            return rolesEoaDAOIntf.getRoleInfo(roleId, language);
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
            return rolesEoaDAOIntf.getRolePrivileges(roleId, language);
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
    public List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDBOException {
        try {
            return rolesEoaDAOIntf.getUsersPermittedResources(userId, language);
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
    public int saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO,
            final List<RolePrivilegeEoaServiceVO> rolePrivileges) throws RMDBOException {
        try {
            RoleEoaServiceVO objRoleServiceVOPersisited = (RoleEoaServiceVO) rolesEoaDAOIntf
                    .saveRoleInfo(objRoleServiceVO);
            objRoleServiceVO.setGetUsrRolesSeqId(objRoleServiceVOPersisited.getGetUsrRolesSeqId());
            rolesEoaDAOIntf.deleteAllRolePrivileges(objRoleServiceVO);
            if (!RMDCommonUtility.isCollectionNotEmpty(rolePrivileges)) {
                return UPDATE_SUCCESS;
            }
            rolesEoaDAOIntf.saveRolePrivileges(objRoleServiceVO, rolePrivileges);
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
    public RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDBOException {
        try {
            return rolesEoaDAOIntf.getMatchingRole(roleService);
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
            return rolesEoaDAOIntf.getRolesHomePages(roleId, language);
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
            return rolesEoaDAOIntf.getRoleSecNavPrivileges(roleId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public List<PrivilegesEOAVO> getAllPrivileges(String roleid) throws RMDBOException {
        try {
            return rolesEoaDAOIntf.getAllPrivileges(roleid);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDBOException {
        try {
            rolesEoaDAOIntf.addOrCopyRole(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    @Override
    public void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDBOException {
        try {
            rolesEoaDAOIntf.editRole(objRoleManagementVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method returns the list of roles which have Case
     *                   Management Privilege by calling the getCaseMgmtRoles()
     *                   method of DAO Layer.
     */

    @Override
    public List<RolesVO> getCaseMgmtRoles() throws RMDBOException {

        List<RolesVO> rolesList = null;
        try {
            rolesList = rolesEoaDAOIntf.getCaseMgmtRoles();

        } catch (RMDDAOException e) {
            throw e;
        }
        return rolesList;
    }

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDBOException
     * @Description:This method is used for fetching the EOA UserId by calling
     *                   the getEoaUserId() method of DAO Layer.
     */

    @Override
    public String getEoaUserName(String userId) throws RMDBOException {
        String eoaUserName = null;
        try {
            eoaUserName = rolesEoaDAOIntf.getEoaUserName(userId);
        } catch (RMDDAOException e) {

            throw e;
        }
        return eoaUserName;
    }
    
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method returns the list of roles which have EOA Onsite
     *                   Privilege enabled roles by calling the getEoaOnsiteRoles()
     *                   method of DAO Layer.
     */

    @Override
    public List<RolesVO> getEoaOnsiteRoles() throws RMDBOException {

        List<RolesVO> rolesList = null;
        try {
            rolesList = rolesEoaDAOIntf.getEoaOnsiteRoles();

        } catch (RMDDAOException e) {
            throw e;
        }
        return rolesList;
    }

}