/**
 * ============================================================
 * File : ParicipatedRuleResultVO.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 17, 2011
 * History
 * Modified By : Initial Release
 * Classification : iGATE Sensitive
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.common.valueobjects;

import com.ge.trans.rmd.common.valueobjects.BaseVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Mar 17, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class ParicipatedRuleResultVO extends BaseVO {

    /**
     *
     */
    private static final long serialVersionUID = -3041637936918987607L;
    private String strRuleId;
    private String strRuleTitle;
    private String strActive;
    private String strFired;
    private String strRuleType;

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrRuleId() {
        return strRuleId;
    }

    /**
     * @Author: iGATE
     * @param strRuleId
     * @Description:
     */
    public void setStrRuleId(final String strRuleId) {
        this.strRuleId = strRuleId;
    }

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrRuleTitle() {
        return strRuleTitle;
    }

    /**
     * @Author: iGATE
     * @param ruleTitle
     * @Description:
     */
    public void setStrRuleTitle(final String strRuleTitle) {
        this.strRuleTitle = strRuleTitle;
    }

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrActive() {
        return strActive;
    }

    /**
     * @Author: iGATE
     * @param strActive
     * @Description:
     */
    public void setStrActive(final String strActive) {
        this.strActive = strActive;
    }

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrFired() {
        return strFired;
    }

    /**
     * @Author: iGATE
     * @param strFired
     * @Description:
     */
    public void setStrFired(final String strFired) {
        this.strFired = strFired;
    }

    /**
     * @Author: iGATE
     * @return
     * @Description:
     */
    public String getStrRuleType() {
        return strRuleType;
    }

    /**
     * @Author: iGATE
     * @param strRuleType
     * @Description:
     */
    public void setStrRuleType(final String strRuleType) {
        this.strRuleType = strRuleType;
    }

}
