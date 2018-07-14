/**
 * ============================================================
 * File : RuleTracerServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.intf
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

package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
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
public interface RunRecreatorServiceIntf {

    /**
     * @Author: iGATE
     * @param strCaseId
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<String> getCaseIds(String strCaseId, String strLanguage) throws RMDServiceException;

    /**
     * @Author: iGATE
     * @param strCaseId
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<String> getRunIds(String strCaseId, String strLanguage) throws RMDServiceException;

    /**
     * @Author: iGATE
     * @param objServiceCriteriaVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ParicipatedRuleResultVO> getParticipatedRules(RuleTracerServiceCriteriaVO objServiceCriteriaVO)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objServiceCriteriaVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveRunTrace(RuleTracerServiceCriteriaVO objServiceCriteriaVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objTestSeachCritServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchUser(RuleTracerSeachCriteriaServiceVO objTestSeachCritServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objTestSeachCritServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchTrackingRuleId(RuleTracerSeachCriteriaServiceVO objTestSeachCritServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objTestSeachCritServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchTrackingId(RuleTracerSeachCriteriaServiceVO objTestSeachCritServiceVO) throws RMDServiceException;

    /**
     * @Author: O
     * @param objTestSeachCritServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List searchTrackingResult(RuleTracerSeachCriteriaServiceVO objTestSeachCritServiceVO) throws RMDServiceException;

}
