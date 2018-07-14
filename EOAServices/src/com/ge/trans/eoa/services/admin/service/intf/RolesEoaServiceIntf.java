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

import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDServiceException;

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
public interface RolesEoaServiceIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userId, final String language)
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
    List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDServiceException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDServiceException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolePrivileges
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO, final List<RolePrivilegeEoaServiceVO> rolePrivileges)
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
    List<PrivilegesEOAVO> getAllPrivileges(String roleid) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementEOAVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDServiceException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementEOAVO
     * @Description:
     */
    void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDServiceException;

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method is returns the list of roles which have Case
     *                   Management Privilege
     */
    List<RolesVO> getCaseMgmtRoles() throws RMDServiceException;

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDServiceException
     * @Description:This method is used for fetching the EOA UserId.
     */
    String getEoaUserName(final String userId) throws RMDServiceException;
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method is returns the list of roles which have EOA Onsite
     *                    Privilege enabled
     */
    List<RolesVO> getEoaOnsiteRoles() throws RMDServiceException;
    

}