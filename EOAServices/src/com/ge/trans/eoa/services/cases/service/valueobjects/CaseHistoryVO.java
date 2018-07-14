/**
 * ============================================================
 * Classification: GE Confidential
 * File 			: CaseHistoryVO.java
 * Description 		: Value Object for Activity Log Display  
 * Package 			:  com.ge.trans.rmd.cm.valueobjects
 * Author 			: 
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		: 
 * History
 * Modified By 		: Initial Release
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CaseHistoryVO {
    private String objId;
    private String activity;
    private Date createDate;
    private String user;
    private String addInfo;
    private String activityType;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

}
