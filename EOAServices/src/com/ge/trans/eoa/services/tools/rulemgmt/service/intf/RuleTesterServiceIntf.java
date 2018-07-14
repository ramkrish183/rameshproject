/**
 * ============================================================
 * File : RuleTesterServiceIntf.java
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
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RuleTesterServiceIntf {

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveRunTest(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchAssetHeader(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchAssetNumber(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchUser(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objTestSeachCriteria
     * @throws RMDServiceException
     * @Description:
     */
    void getTrackingRules(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objTestSeachCriteria
     * @throws RMDServiceException
     * @Description:
     */
    void getTrackingDetails(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    FaultDataDetailsServiceVO getFaults(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return objRuleTesterResultServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    List searchTrackingResult(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    @SuppressWarnings("unchecked")
    List getArlHeaders() throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDServiceException
     * @Description:
     */
    void fetchRuleId(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException;

}
