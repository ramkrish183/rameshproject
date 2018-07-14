package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.RMDAuditInfoVO;

public class RolesVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long getUsrRolesSeqId;
    /*
     * Sequence Id from get_usr.get_usr_user_roles table which is a mapping
     * table for users and roles
     */
    private Long linkUsrRoleSeqId;
    /*
     * Sequence Id from get_usr.get_usr_user_roles table which is a mapping
     * table for users and roles
     */
    private String roleName;
    private String roleDesc;
    private Long status;
    private String strLanguage;
    private String lastUpdatedBy;
    private String lastUpdatedTime;

    public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public Long getGetUsrRolesSeqId() {
        return this.getUsrRolesSeqId;
    }

    public void setGetUsrRolesSeqId(Long getUsrRolesSeqId) {
        this.getUsrRolesSeqId = getUsrRolesSeqId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public RMDAuditInfoVO getAuditInfo() {
        return null;
    }

    @Override
    public String getStrLanguage() {
        return strLanguage;
    }

    @Override
    public void setStrLanguage(String strLanguage) {
        this.strLanguage = strLanguage;
    }

    public Long getLinkUsrRoleSeqId() {
        return linkUsrRoleSeqId;
    }

    public void setLinkUsrRoleSeqId(Long linkUsrRoleSeqId) {
        this.linkUsrRoleSeqId = linkUsrRoleSeqId;
    }
    @Override
	public String toString() {
		return "RoleManagementVo [getUsrRolesSeqId=" + getUsrRolesSeqId + ", linkUsrRoleSeqId="
				+ linkUsrRoleSeqId + ", roleName=" + roleName + ", roleDesc="
				+ roleDesc + ", status=" + status + ", strLanguage="
				+ strLanguage + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedTime="
				+ lastUpdatedTime + "]";
	}

}
