/**
 * ============================================================
 * Classification: GE Confidential
 * File 			: CaseMgmtUsersResponseType.java
 * Description 		: Response Type for user names  
 * Package 			: com.ge.trans.rmd.services.cases.valueobjects
 * Author 			: 
 * Last Edited By 	:
 * Version 			: 1.0
 * Created on 		: 
 * History
 * Modified By 		: Initial Release
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.cases.valueobjects;

/*******************************************************************************
*
* @Author 		:
* @Version 	    : 1.0
* @Date Created : 
* @Date Modified:
* @Modified By  :
* @Contact 	    :
* @Description  :
* @History		:
*
******************************************************************************/
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caseMgmtUsersResponseType", propOrder = { "userId", "firstName", "lastName", "sso"

})
@XmlRootElement
public class CaseMgmtUsersResponseType {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String firstName;
    @XmlElement(required = true)
    protected String lastName;
    @XmlElement(required = true)
    protected String sso;

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
