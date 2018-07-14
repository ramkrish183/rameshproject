package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

/**
 * ============================================================
 * File : JdpadResultServiceVO.java
 * Description : 
 * 
 * package com.ge.trans.rmd.web.tools.rule.valueobjects;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2008 General Electric Company. All rights reserved
 *
 * ============================================================
 */
import java.util.ArrayList;
import java.util.Date;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class contains the value objects of JdpadResultServiceVO
 * @History :
 ******************************************************************************/
public class JdpadResultServiceVO extends BaseVO {

    private String strTrackingId;
    private String strAssetId;
    private String strDay = RMDCommonConstants.EMPTY_STRING;
    private String strCaseId;
    private String strCreatedBy;
    private Date dtCreatedTime;
    private Date dtFinishTime;
    private String strStatus;
    private String strMode;
    private boolean blStatus;
    private boolean blMode;
    private ArrayList arlJdpadSearch = new ArrayList();
    private ArrayList alDayList;

    public String getStrDay() {
        return strDay;
    }

    public void setStrDay(final String strDay) {
        this.strDay = strDay;
    }

    public ArrayList getAlDayList() {
        return alDayList;
    }

    public void setAlDayList(final ArrayList alDayList) {
        this.alDayList = alDayList;
    }

    public ArrayList getArlJdpadSearch() {
        return arlJdpadSearch;
    }

    public void setArlJdpadSearch(final ArrayList arlJdpadSearch) {
        this.arlJdpadSearch = arlJdpadSearch;
    }

    public String getStrMode() {
        return strMode;
    }

    public void setStrMode(final String strMode) {
        this.strMode = strMode;
    }

    public String getStrCaseId() {
        return strCaseId;
    }

    public void setStrCaseId(final String strCaseId) {
        this.strCaseId = strCaseId;
    }

    public String getStrCreatedBy() {
        return strCreatedBy;
    }

    public void setStrCreatedBy(final String strCreatedBy) {
        this.strCreatedBy = strCreatedBy;
    }

    public Date getDtCreatedTime() {
        return dtCreatedTime;
    }

    public void setDtCreatedTime(final Date dtCreatedTime) {
        this.dtCreatedTime = dtCreatedTime;
    }

    public Date getDtFinishTime() {
        return dtFinishTime;
    }

    public void setDtFinishTime(final Date dtFinishTime) {
        this.dtFinishTime = dtFinishTime;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(final String strStatus) {
        this.strStatus = strStatus;
    }

    public boolean getBlStatus() {
        return blStatus;
    }

    public void setBlStatus(final boolean blStatus) {
        this.blStatus = blStatus;
    }

    public boolean getBlMode() {
        return blMode;
    }

    public void setBlMode(final boolean blMode) {
        this.blMode = blMode;
    }

    public String getStrTrackingId() {
        return strTrackingId;
    }

    public void setStrTrackingId(final String strTrackingId) {
        this.strTrackingId = strTrackingId;
    }

    public String getStrAssetId() {
        return strAssetId;
    }

    public void setStrAssetId(final String strAssetId) {
        this.strAssetId = strAssetId;
    }

}
