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

import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;

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
public interface RolesDAOIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userId, final String language)
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
    List<RoleServiceVO> getAllRoles(final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDDAOException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int deleteAllRolePrivileges(final RoleServiceVO objRoleServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolesPrivileges
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveRolePrivileges(final RoleServiceVO objRoleServiceVO, final List<RolePrivilegeServiceVO> rolesPrivileges)
            throws RMDDAOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Serializable saveRoleInfo(final RoleServiceVO objRoleServiceVO) throws RMDDAOException;

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
    List<PrivilegesVO> getAllPrivileges(String roleid) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDDAOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void editRole(RoleManagementVO objRoleManagementVO) throws RMDDAOException;
    
    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void deleteRole(RoleManagementVO objRoleManagementVO) throws RMDDAOException;
    

	 List<DeleteRolesResponseVO> getDeleteRoleList(RoleManagementVO objRoleManagementVO) throws RMDDAOException;
   
   String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDDAOException;


}