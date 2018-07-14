package com.ge.trans.eoa.services.security.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class PrivilegesVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    private String privilegeName;
    private long privilegeID;
    private String displayName;
    private long parentId;
    private boolean enabled;
    private String level;
    private String description;
    private String resourceType;
    private String sortOrder;

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(long privilegeID) {
        this.privilegeID = privilegeID;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

}
