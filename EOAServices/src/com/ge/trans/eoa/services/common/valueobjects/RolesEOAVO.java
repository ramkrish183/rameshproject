package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;
import com.ge.trans.rmd.common.valueobjects.RMDAuditInfoVO;

public class RolesEOAVO extends BaseVO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long getUsrRolesSeqId;
    /*
     * Sequence Id from GETS_RMD.gets_rmd_omd_user_roles table which is a
     * mapping table for users and roles
     */
    private Long linkUsrRoleSeqId;
    /*
     * Sequence Id from GETS_RMD.gets_rmd_omd_user_roles table which is a
     * mapping table for users and roles
     */
    private String roleName;

    private String roleDesc;
    private Long status;
    private String strLanguage;

    public Long getGetUsrRolesSeqId() {
        return this.getUsrRolesSeqId;
    }

    public void setGetUsrRolesSeqId(final Long getUsrRolesSeqId) {
        this.getUsrRolesSeqId = getUsrRolesSeqId;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return this.roleDesc;
    }

    public void setRoleDesc(final String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(final Long status) {
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
    public void setStrLanguage(final String strLanguage) {
        this.strLanguage = strLanguage;
    }

    public Long getLinkUsrRoleSeqId() {
        return linkUsrRoleSeqId;
    }

    public void setLinkUsrRoleSeqId(Long linkUsrRoleSeqId) {
        this.linkUsrRoleSeqId = linkUsrRoleSeqId;
    }
}
