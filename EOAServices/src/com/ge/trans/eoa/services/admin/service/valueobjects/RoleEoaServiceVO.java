/**
 * ============================================================
 * Classification: GE Confidential
 * File : RoleServiceVO.java
 * Description : RoleServiceVO for RoleManagement
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

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

public class RoleEoaServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -8782066029117730357L;
    /** getUsrRolesSeqId of Type Long **/
    private Long getUsrRolesSeqId;
    /** roleName of Type String **/
    private String roleName;
    /** roleDesc of Type String **/
    private String roleDesc;
    /** status of Type Long **/
    private Long status;
    
    private String lastUpdatedBy;
    private Date lastUpdatedTime;
    

    public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime.toString();
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
     * @Author:
     * @return
     * @Description:
     */
    public Long getGetUsrRolesSeqId() {
        return getUsrRolesSeqId;
    }

    /**
     * @Author:
     * @param getUsrRolesSeqId
     * @Description:
     */
    public void setGetUsrRolesSeqId(Long getUsrRolesSeqId) {
        this.getUsrRolesSeqId = getUsrRolesSeqId;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @Author:
     * @param roleName
     * @Description:
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * @Author:
     * @param roleDesc
     * @Description:
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * @Author:
     * @return
     * @Description:
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @Author:
     * @param status
     * @Description:
     */
    public void setStatus(Long status) {
        this.status = status;
    }
}