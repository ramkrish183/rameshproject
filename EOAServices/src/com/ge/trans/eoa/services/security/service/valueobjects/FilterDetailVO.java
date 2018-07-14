/**
 * ============================================================
 * Classification: GE Confidential
 * File : UsersVO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.web.security.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Contains user details
 * @History :
 ******************************************************************************/
public class FilterDetailVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 6245186534L;

    private Long usrFilterDetailId;
    private Long usrFilterId;
    private Object fromFilterValue;
    private Object toFilterValue;
    private Date createdDate;
    private String createdBy;
    private Date lastUpdatedDate;
    private String lastUpdatedBy;
    private String resourceName;
    private Long resourceId;
    private String columnName;
    private String columnType;

    public FilterDetailVO() {
    }

    public FilterDetailVO(Long usrFilterDetailId, Long usrFilterId, Object fromFilterValue, Object toFilterValue,
            Date createdDate, String createdBy, Date lastUpdatedDate, String lastUpdatedBy, String resourceName,
            Long resourceId, String columnName, String columnType) {
        super();
        this.usrFilterDetailId = usrFilterDetailId;
        this.usrFilterId = usrFilterId;
        this.fromFilterValue = fromFilterValue;
        this.toFilterValue = toFilterValue;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdatedDate = lastUpdatedDate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.resourceName = resourceName;
        this.resourceId = resourceId;
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public Long getUsrFilterDetailId() {
        return usrFilterDetailId;
    }

    public void setUsrFilterDetailId(Long usrFilterDetailId) {
        this.usrFilterDetailId = usrFilterDetailId;
    }

    public Long getUsrFilterId() {
        return usrFilterId;
    }

    public void setUsrFilterId(Long usrFilterId) {
        this.usrFilterId = usrFilterId;
    }

    public Object getFromFilterValue() {
        return fromFilterValue;
    }

    public void setFromFilterValue(Object fromFilterValue) {
        this.fromFilterValue = fromFilterValue;
    }

    public Object getToFilterValue() {
        return toFilterValue;
    }

    public void setToFilterValue(Object toFilterValue) {
        this.toFilterValue = toFilterValue;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

}