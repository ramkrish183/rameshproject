/**
 * ============================================================
 * Classification: GE Confidential
 * File : RolePrivilegeServceVO.java
 * Description : RolePrivilegeServiceVO for RoleManagement
 *
 * Package : com.ge.trans.rmd.services.admin.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 16, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jul 18, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RolePrivilegeEoaServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -2679667661088036010L;

    /** getUsrRolePrivilegeSeqId of Type Long **/
    private Long getUsrRolePrivilegeSeqId;
    /** getUsrRoles of Type RoleServiceVO **/
    private RoleEoaServiceVO getUsrRoles;
    /** rolePrivilege of Type String **/
    private String rolePrivilege;
    /** resourceType of Type String **/
    private String resourceType;
    /** accessLevel of Type String **/
    private String accessLevel;
    /** rowVersion of Type Integer **/
    private Integer rowVersion;

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Long getGetUsrRolePrivilegeSeqId() {
        return getUsrRolePrivilegeSeqId;
    }

    /**
     * @Author:
     * @param getUsrRolePrivilegeSeqId
     * @Description:
     */
    public void setGetUsrRolePrivilegeSeqId(Long getUsrRolePrivilegeSeqId) {
        this.getUsrRolePrivilegeSeqId = getUsrRolePrivilegeSeqId;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public RoleEoaServiceVO getGetUsrRoles() {
        return getUsrRoles;
    }

    /**
     * @Author:
     * @param getUsrRoles
     * @Description:
     */
    public void setGetUsrRoles(RoleEoaServiceVO getUsrRoles) {
        this.getUsrRoles = getUsrRoles;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getRolePrivilege() {
        return rolePrivilege;
    }

    /**
     * @Author:
     * @param rolePrivilege
     * @Description:
     */
    public void setRolePrivilege(String rolePrivilege) {
        this.rolePrivilege = rolePrivilege;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * @Author:
     * @param accessLevel
     * @Description:
     */
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    @Override
    public Integer getRowVersion() {
        return rowVersion;
    }

    /**
     * @Author:
     * @param rowVersion
     * @Description:
     */
    @Override
    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @Author:
     * @param resourceType
     * @Description:
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

}