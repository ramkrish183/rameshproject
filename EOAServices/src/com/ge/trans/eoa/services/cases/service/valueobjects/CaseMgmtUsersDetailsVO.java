/**
 * ============================================================
 * Classification: GE Confidential
 * File 			: CaseMgmtUsersDetailsVO.java
 * Description 		: Value Object for User names Display  
 * Package 			: com.ge.trans.rmd.cm.valueobjects
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

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified:
 * @Modified By :
 * @Contact :
 * @Description :this file contains user id,first name,last name details
 * @History :
 ******************************************************************************/
public class CaseMgmtUsersDetailsVO {
    private String userId;
    private String firstName;
    private String lastName;
    private String sso;

    public String getSso() {
        return sso;
    }

    public void setSso(String sso) {
        this.sso = sso;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
