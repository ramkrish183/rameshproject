/**
 * ============================================================
 * Classification: GE Confidential
 * File             : CaseActivityLogResponseType.java
 * Description      : Response Type for activity log  
 * Package          : com.ge.trans.rmd.services.cases.valueobjects
 * Author           : 
 * Last Edited By   :
 * Version          : 1.0
 * Created on       : 
 * History
 * Modified By      : Initial Release
 * Copyright (C) 2012 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.services.cases.valueobjects;
/*******************************************************************************
*
* @Author       :
* @Version      : 1.0
* @Date Created : 
* @Date Modified:
* @Modified By  :
* @Contact      :
* @Description  :
* @History      :
*
******************************************************************************/
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caseHistoryResponseType", propOrder = {
    "objId",
    "activity",
    "createDate",
    "user",
    "addInfo",
    "activityType",
    "description"
   
})
@XmlRootElement
public class CaseHistoryResponseType {
    @XmlElement(required = true)
    protected String objId;
    @XmlElement(required = true)
        protected String activity;
        @XmlSchemaType(name = "dateTime")
        //protected XMLGregorianCalendar createDate;
        protected String createDate;
        
        @XmlElement(required = true)
        protected String user;
        @XmlElement(required = true)
        protected String addInfo;
        @XmlElement(required = true)
        protected String activityType;
        @XmlElement(required = true)
        protected String description;
        
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
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
        
        
        public String getCreateDate() {
            return createDate;
        }
        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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
         public String getObjId() {
                return objId;
            }
            public void setObjId(String objId) {
                this.objId = objId;
            }
        
}