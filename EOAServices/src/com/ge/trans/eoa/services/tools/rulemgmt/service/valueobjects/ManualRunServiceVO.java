package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

/**
 * ============================================================
 * File : ManualJdpadServiceVO.java
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
import java.util.Date;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 6, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class contains the value objects of ManualJdpadServiceVO
 * @History :
 ******************************************************************************/
public class ManualRunServiceVO extends BaseVO {

    /**
     * Default serial version Id
     */
    private static final long serialVersionUID = 1168661415686388555L;
    private String strMode;
    private long lgAssetId;
    private Date dtStart;
    private Date dtEnd;
    private String strSeqId;
    private String user;
    private String strAssetNo;

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getStrSeqId() {
        return strSeqId;
    }

    public void setStrSeqId(final String strSeqId) {
        this.strSeqId = strSeqId;
    }

    public String getStrMode() {
        return strMode;
    }

    public void setStrMode(final String strMode) {
        this.strMode = strMode;
    }

    public long getLgAssetId() {
        return lgAssetId;
    }

    public void setLgAssetId(final long lgAssetId) {
        this.lgAssetId = lgAssetId;
    }

    public Date getDtStart() {
        return dtStart;
    }

    public void setDtStart(final Date dtStart) {
        this.dtStart = dtStart;
    }

    public Date getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(final Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getStrAssetNo() {
        return strAssetNo;
    }

    public void setStrAssetNo(final String strAssetNo) {
        this.strAssetNo = strAssetNo;
    }

}
