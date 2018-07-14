/**
 * ============================================================
 * File : ViewRuleBOIntf.java
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
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.IDListServiceVO;

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
public interface ViewRuleBOIntf {

    /**
     * @Author:
     * @param strRuleId
     * @param strCloneRule
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
	FinalRuleServiceVO getRuleDetails(String strRuleId, String strCloneRule, String strLanguage, String explode_ror,String defaultUom) throws RMDBOException;
           

    /**
     * @Author:
     * @param isLock,strRuleId,strUserId,strLanguage
     * @throws RMDBOException
     * @Description:
     */
    String lockEditRule(boolean isLockRule, String strRuleId, String strUserId, String strLanguage)
            throws RMDBOException;

    /**
     * @Author:
     * @param listServiceVO
     * @throws RMDBOException
     * @Description:
     */
    List getFiredRuleList(IDListServiceVO listServiceVO);

    /**
     * @Author:
     * @param strRuleId,language
     * @throws RMDBOException
     * @Description:
     */
    List<DpdFinrulSearchVO> getFinalRuleList(String strRuleId, String strFamily, String strRuleType, String language,
            String strCustomer);

}
