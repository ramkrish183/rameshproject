/**
 * =================================================================
 * Classification: GE Confidential
 * File : RolesServiceIntf.java
 * Description : Service Interface for Role Management
 *
 * Package : com.ge.trans.rmd.services.admin.service.intf
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
package com.ge.trans.eoa.services.admin.service.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : Service Interface for Role Management
 * @History :
 ******************************************************************************/
public interface RolesServiceIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<RoleServiceVO> getAllRoles(final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDServiceException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolePrivileges
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveRoleInfo(final RoleServiceVO objRoleServiceVO, final List<RolePrivilegeServiceVO> rolePrivileges)
            throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<String> getRolesHomePages(final Long roleId, final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<PrivilegesVO> getAllPrivileges(String roleid) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void editRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException;
    
    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void deleteRole(RoleManagementVO objRoleManagementVO) throws RMDServiceException;
    
    
List<DeleteRolesResponseVO> getDeleteRoleList(RoleManagementVO objRoleManagementVO) throws RMDServiceException;
    
    String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDServiceException;


    
    

}