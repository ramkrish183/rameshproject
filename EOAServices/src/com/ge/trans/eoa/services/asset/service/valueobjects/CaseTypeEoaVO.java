/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesServiceVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 7, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CaseTypeEoaVO extends BaseVO {
    private static final long serialVersionUID = 245245245;

    private Long caseTypeId;
    private String caseTypeTitle;

    public Long getCaseTypeId() {
        return caseTypeId;
    }

    public void setCaseTypeId(Long caseTypeId) {
        this.caseTypeId = caseTypeId;
    }

    public String getCaseTypeTitle() {
        return caseTypeTitle;
    }

    public void setCaseTypeTitle(String caseTypeTitle) {
        this.caseTypeTitle = caseTypeTitle;
    }

}
