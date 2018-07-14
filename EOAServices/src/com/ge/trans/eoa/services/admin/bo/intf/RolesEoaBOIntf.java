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

import com.ge.trans.eoa.services.admin.service.valueobjects.RoleEoaServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.RolePrivilegeEoaServiceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.PrivilegesEOAVO;
import com.ge.trans.eoa.services.security.service.valueobjects.RoleManagementEOAVO;
import java.util.List;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDException;
import com.ge.trans.rmd.exception.RMDServiceException;

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
public interface RolesEoaBOIntf {

    /**
     * @Author:
     * @param userId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RolePrivilegeEoaServiceVO> getUsersPermittedResources(final Long userId, final String language)
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
    List<RoleEoaServiceVO> getAllRoles(final String language) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RMDBOException
     * @Description:
     */
    RoleEoaServiceVO getRoleInfo(final Long roleId, final String language) throws RMDBOException;

    /**
     * @Author:
     * @param roleService
     * @return
     * @throws RMDBOException
     * @Description:
     */
    RoleEoaServiceVO getMatchingRole(final RoleEoaServiceVO roleService) throws RMDBOException;

    /**
     * @Author:
     * @param objRoleServiceVO
     * @param rolePrivileges
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int saveRoleInfo(final RoleEoaServiceVO objRoleServiceVO, final List<RolePrivilegeEoaServiceVO> rolePrivileges)
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
    List<PrivilegesEOAVO> getAllPrivileges(String roleid) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void addOrCopyRole(RoleManagementEOAVO objRoleManagementVO) throws RMDBOException;

    /**
     * @Author:
     * @param roleId
     * @param language
     * @return
     * @throws RoleManagementVO
     * @Description:
     */
    void editRole(RoleManagementEOAVO objRoleManagementVO) throws RMDBOException;

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method is returns the list of roles which have Case
     *                   Management Privilege
     */
    List<RolesVO> getCaseMgmtRoles() throws RMDBOException;

    /**
     * @Author:
     * @param :
     * @param :userId
     * @return:String
     * @throws:RMDBOException
     * @Description:This method is used for fetching the EOA UserId.
     */
    String getEoaUserName(final String userId) throws RMDBOException;
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method is returns the list of roles which have EOA Onsite
     *               Privilege enabled roles
     */
    List<RolesVO> getEoaOnsiteRoles() throws RMDBOException;
}