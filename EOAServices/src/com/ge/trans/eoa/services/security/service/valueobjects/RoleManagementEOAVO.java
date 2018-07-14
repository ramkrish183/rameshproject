package com.ge.trans.eoa.services.security.service.valueobjects;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class RoleManagementEOAVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    private String roleId;
    private String roleName;
    private String roleDescription;
    private String sortOrder;
    private List<PrivilegesEOAVO> privileges;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<PrivilegesEOAVO> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegesEOAVO> privileges) {
        this.privileges = privileges;
    }

}
