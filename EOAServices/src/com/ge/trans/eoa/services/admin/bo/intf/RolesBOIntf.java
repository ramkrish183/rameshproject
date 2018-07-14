/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserDAOImpl.java
 * Description : BO Impl for Role Management
 *
 * Package : com.ge.trans.rmd.services.admin.bo.intf
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
package com.ge.trans.eoa.services.admin.bo.intf;

import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesResponseVO;
import com.ge.trans.eoa.services.security.service.valueobjects.DeleteRolesUpdateVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementVO;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDException;
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
 * @Description : BO Impl for Role Management
 * @History :
 ******************************************************************************/
public interface RolesBOIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RolePrivilegeServiceVO> getUsersPermittedResources(final Long userId, final String language)
            throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<ElementVO> getRolePrivileges(final Long roleId, final String language) throws RMDBOException;

    /**
     * @Author:
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RoleServiceVO> getAllRoles(final String language) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    RoleServiceVO getRoleInfo(final Long roleId, final String language) throws RMDBOException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDBOException
     * @Description:
     */
    RoleServiceVO getMatchingRole(final RoleServiceVO roleService) throws RMDBOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolePrivileges
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int saveRoleInfo(final RoleServiceVO objRoleServiceVO, final List<RolePrivilegeServiceVO> rolePrivileges)
            throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<String> getRolesHomePages(final Long roleId, final String language) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<ElementVO> getRoleSecNavPrivileges(final Long roleId, final String language) throws RMDException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<PrivilegesVO> getAllPrivileges(String roleid) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementVO objRoleManagementVO) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void editRole(RoleManagementVO objRoleManagementVO) throws RMDBOException;
    
    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void deleteRole(RoleManagementVO objRoleManagementVO) throws RMDBOException;
    

    List<DeleteRolesResponseVO> getDeleteRoleList(RoleManagementVO objRoleManagementVO) throws RMDBOException;
     
     String deleteRoleUpdateList(List<DeleteRolesUpdateVO> arlDeleteRolesUpdateVO, String userId) throws RMDBOException;


}