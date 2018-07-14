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

import java.util.List;
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
public class FavFilterServiceVO extends BaseVO {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = 6245186534L;

    /** getUsrUsersSeqId of Type Long **/
    private Long filterId;
    /** userId of Type String **/
    private String screenName;
    private String screenNameFilter;
    private Long linkUsrRoleSeqId;
    private List<String> columnName;
    private List<String> columnType;
    private List<String> filterData;
    private List<Object[]> dataLst;

    public List<Object[]> getDataLst() {
        return dataLst;
    }

    public void setDataLst(List<Object[]> dataLst) {
        this.dataLst = dataLst;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public List<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(List<String> columnName) {
        this.columnName = columnName;
    }

    public List<String> getFilterData() {
        return filterData;
    }

    public void setFilterData(List<String> filterData) {
        this.filterData = filterData;
    }

    public String getScreenNameFilter() {
        return screenNameFilter;
    }

    public void setScreenNameFilter(String screenNameFilter) {
        this.screenNameFilter = screenNameFilter;
    }

    public Long getLinkUsrRoleSeqId() {
        return linkUsrRoleSeqId;
    }

    public void setLinkUsrRoleSeqId(Long linkUsrRoleSeqId) {
        this.linkUsrRoleSeqId = linkUsrRoleSeqId;
    }

    public List<String> getColumnType() {
        return columnType;
    }

    public void setColumnType(List<String> columnType) {
        this.columnType = columnType;
    }
}