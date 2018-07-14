/**
 * ============================================================
 * Classification: GE Confidential
 * File : VirtualServiceVO.java
 * Description : Service VO for Virtual
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Mar 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service VO for Virtual
 * @History :
 ******************************************************************************/
public class VirtualServiceVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    private String strObjId;
    private String strVirtualFun;
    private String strDisplayName;
    private String strDescription;
    private String strModel;
    private String family;
    private String status;
    private String lastUpdatedBy;
    private Date lastUpdatedDate;
    private Date lastUpdatedFromDate;
    private Date lastUpdatedToDate;
    private Date creationFromDate;
    private Date creationToDate;
    private String createdBy;
    private Date creationDate;
    private boolean isDefaultLoad = false;

    /**
     * @return the strVirtualFun
     */
    public String getStrVirtualFun() {
        return strVirtualFun;
    }

    /**
     * @param strVirtualFun
     *            the strVirtualFun to set
     */
    public void setStrVirtualFun(String strVirtualFun) {
        this.strVirtualFun = strVirtualFun;
    }

    /**
     * @return the strDisplayName
     */
    public String getStrDisplayName() {
        return strDisplayName;
    }

    /**
     * @param strDisplayName
     *            the strDisplayName to set
     */
    public void setStrDisplayName(String strDisplayName) {
        this.strDisplayName = strDisplayName;
    }

    /**
     * @return the strDescription
     */
    public String getStrDescription() {
        return strDescription;
    }

    /**
     * @param strDescription
     *            the strDescription to set
     */
    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    /**
     * @return the lngObjId
     */
    public String getStrObjId() {
        return strObjId;
    }

    /**
     * @param lngObjId
     *            the lngObjId to set
     */
    public void setStrObjId(String strObjId) {
        this.strObjId = strObjId;
    }

    /**
     * @return the strModel
     */
    public String getStrModel() {
        return strModel;
    }

    /**
     * @param strModel
     *            the strModel to set
     */
    public void setStrModel(String strModel) {
        this.strModel = strModel;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDefaultLoad() {
        return isDefaultLoad;
    }

    public void setDefaultLoad(boolean isDefaultLoad) {
        this.isDefaultLoad = isDefaultLoad;
    }

    public Date getLastUpdatedFromDate() {
        return lastUpdatedFromDate;
    }

    public void setLastUpdatedFromDate(Date lastUpdatedFromDate) {
        this.lastUpdatedFromDate = lastUpdatedFromDate;
    }

    public Date getLastUpdatedToDate() {
        return lastUpdatedToDate;
    }

    public void setLastUpdatedToDate(Date lastUpdatedToDate) {
        this.lastUpdatedToDate = lastUpdatedToDate;
    }

    public Date getCreationFromDate() {
        return creationFromDate;
    }

    public void setCreationFromDate(Date creationFromDate) {
        this.creationFromDate = creationFromDate;
    }

    public Date getCreationToDate() {
        return creationToDate;
    }

    public void setCreationToDate(Date creationToDate) {
        this.creationToDate = creationToDate;
    }

}
