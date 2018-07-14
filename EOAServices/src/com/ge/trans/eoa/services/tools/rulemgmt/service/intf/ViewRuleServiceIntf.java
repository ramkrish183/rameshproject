/**
 * ============================================================
 * File : ViewRuleServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 23, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface ViewRuleServiceIntf {

    /**
     * @Author:
     * @param strRuleId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
	FinalRuleServiceVO getRuleDetails(String strRuleId,String strCloneRule,String strLanguage, String explode_ror,String defaultUom)
			throws RMDServiceException;

    /**
     * @Author:
     * @param isLock,strRuleId,strUserId,strLanguage
     * @throws RMDServiceException
     * @Description:
     */
    String lockEditRule(boolean isLockRule, String strRuleId, String strUserId, String strLanguage)
            throws RMDServiceException;

    /**
     * @Author:
     * @param strTrackingID,strCaseID,strTesterId
     * @throws RMDServiceException
     * @throws RMDServiceException
     * @Description:
     */
    List getFiredRuleList(String strTraceID, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strRuleId,language
     * @throws RMDServiceException
     * @throws RMDServiceException
     * @throws RMDServiceException
     * @Description:
     */
    List<DpdFinrulSearchVO> getFinalRuleList(String strRuleId, String strFamily, String strRuleType, String language,
            String strCustomer) throws RMDServiceException;

}
