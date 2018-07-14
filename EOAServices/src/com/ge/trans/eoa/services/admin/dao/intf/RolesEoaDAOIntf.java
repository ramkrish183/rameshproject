/**
 * =================================================================
 * Classification: GE Confidential
 * File : RolesDAOIntf.java
 * Description : DAO Interface for Role Management
 *
 * Package : com.ge.trans.rmd.services.admin.dao.intf
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
package com.ge.trans.eoa.services.admin.dao.intf;

import java.io.Serializable;
import java.util.List;

import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Interface for Role Management
 * @History :
 ******************************************************************************/
public interface RolesEoaDAOIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int deleteAllRolePrivileges(final RoleEoaServiceVO objRoleServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolesPrivileges
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveRolePrivileges(final RoleEoaServiceVO objRoleServiceVO,
            final List<RolePrivilegeEoaServiceVO> rolesPrivileges) throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Serializable saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<String> getRolesHomePages(final Long roleId, final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */

    List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<PrivilegesEOAVO> getAllPrivileges(String roleid) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDDAOException;

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method is returns the list of roles which have Case
     *                   Management Privilege
     */
    List<RolesVO> getCaseMgmtRoles() throws RMDDAOException;

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDDAOException
     * @Description:This method is used for fetching the EOA UserId.
     */
    String getEoaUserName(final String userId) throws RMDDAOException;
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method is returns the list of roles which have EOA Onsite 
     *                   Privilege enabled roles
     */
    List<RolesVO> getEoaOnsiteRoles() throws RMDDAOException;

}