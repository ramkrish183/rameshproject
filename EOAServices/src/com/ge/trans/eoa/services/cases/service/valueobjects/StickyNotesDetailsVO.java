/**
 * ============================================================

 * Classification		: GE Confidential
 * File 				: NotesServiceVO.java
 * Description 			:
 * Package 				: com.ge.trans.rmd.services.cases.service.valueobjects
 * Author 				: iGATE Global Solutions Ltd.
 * Last Edited By 		:
 * Version 				: 1.0
 * Created on 			:
 * History
 * Modified By 			: Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.valueobjects;

import java.util.Date;
import java.util.List;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created :
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This is a POJO class which contains information about
 *              StickyNotes Details.
 * @History :
 ******************************************************************************/
public class StickyNotesDetailsVO {

    private String applyLevel;
    private String additionalInfo;
    private Date entryTime;
    private String createdBy;
    private String objId;
    private List<String> stickyAssets;

    public List<String> getStickyAssets() {
        return stickyAssets;
    }

    public void setStickyAssets(List<String> stickyAssets) {
        this.stickyAssets = stickyAssets;
    }

    public String getApplyLevel() {
        return applyLevel;
    }

    public void setApplyLevel(String applyLevel) {
        this.applyLevel = applyLevel;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

}
