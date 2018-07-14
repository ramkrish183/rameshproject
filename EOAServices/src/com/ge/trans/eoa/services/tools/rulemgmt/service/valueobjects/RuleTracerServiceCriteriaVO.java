/**
 * ============================================================
 * File : RuleTracerServiceCriteriaVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 16, 2011
 * History
 * Modified By : Initial Release
 * Classification : iGATE Sensitive
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Mar 16, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RuleTracerServiceCriteriaVO extends BaseVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String strCaseId;
    private String strRunId;
    private String strTraceId;
    private String strFinRulId;

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrCaseId() {
        return strCaseId;
    }

    /**
     * @Author: iGATE
     * @param strCaseId
     * @Description:
     */
    public void setStrCaseId(String strCaseId) {
        this.strCaseId = strCaseId;
    }

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrRunId() {
        return strRunId;
    }

    /**
     * @Author: iGATE
     * @param strRunId
     * @Description:
     */
    public void setStrRunId(String strRunId) {
        this.strRunId = strRunId;
    }

    public String getStrTraceId() {
        return strTraceId;
    }

    public void setStrTraceId(String strTraceId) {
        this.strTraceId = strTraceId;
    }

    public String getStrFinRulId() {
        return strFinRulId;
    }

    public void setStrFinRulId(String strFinRulId) {
        this.strFinRulId = strFinRulId;
    }
}
