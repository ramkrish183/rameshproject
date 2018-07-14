/**
 * ============================================================
 * File : RuleTracerBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.intf
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

package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.common.valueobjects.ParicipatedRuleResultVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceCriteriaVO;

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
@SuppressWarnings("unchecked")
public interface RunRecreatorBOIntf {

    /**
     * @Author: iGATE
     * @param strCaseId
     * @param strLanguage
     * @return
     * @Description:
     */
    List<String> getCaseIds(String strCaseId, String strLanguage);

    /**
     * @Author: iGATE
     * @param strCaseId
     * @param strLanguage
     * @return
     * @Description:
     */
    List<String> getRunIds(String strCaseId, String strLanguage);

    /**
     * @Author: iGATE
     * @param objServiceCriteriaVO
     * @return
     * @Description:
     */
    List<ParicipatedRuleResultVO> getParticipatedRules(RuleTracerServiceCriteriaVO objServiceCriteriaVO);

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int saveRunTrace(RuleTracerServiceCriteriaVO objServiceCriteriaVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchUser(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchTrackingRuleId(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchTrackingId(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTracerSeachCriteriaServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List searchTrackingResult(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException;

}