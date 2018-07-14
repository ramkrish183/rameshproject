package com.ge.trans.eoa.services.cases.service.valueobjects;

public class CMPrivilegeVO {

    private boolean isCMPrivilege;
    private String cmAliasName;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isCMPrivilege() {
        return isCMPrivilege;
    }

    public void setCMPrivilege(boolean isCMPrivilege) {
        this.isCMPrivilege = isCMPrivilege;
    }

    public String getCmAliasName() {
        return cmAliasName;
    }

    public void setCmAliasName(String cmAliasName) {
        this.cmAliasName = cmAliasName;
    }

}
