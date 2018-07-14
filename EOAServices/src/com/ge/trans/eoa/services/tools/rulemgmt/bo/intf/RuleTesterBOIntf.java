/**
 * ============================================================
 * File : TestBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
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
public interface RuleTesterBOIntf {

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int saveRunTest(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchAssetHeader(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchAssetNumber(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchUser(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void getTrackingDetails(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    FaultDataDetailsServiceVO getFaults(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List searchTrackingResult(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */

    List getArlHeaders() throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    void getTrackingRules(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterServiceVO
     * @throws RMDBOException
     * @Description:
     */
    void fetchRuleId(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException;

}
