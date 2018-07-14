/**
 * ============================================================
 * File : TestDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
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
public interface RuleTesterDAOIntf {

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveRunTest(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    void fetchAssetHeader(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    void fetchAssetNumber(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    void fetchUser(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    void getTrackingDetails(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    FaultDataDetailsServiceVO getFaults(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List searchTrackingResult(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    @SuppressWarnings("unchecked")
    List getArlHeaders() throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    void getTrackingRules(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO);

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDDAOException
     * @Description:
     */
    void fetchRuleId(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException;

}
