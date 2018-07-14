/**
 * ============================================================
 * File : ActivateDeActivateRuleDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 7, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface ActivateDeActivateRuleDAOIntf {

    /**
     * @Author:
     * @param strOriginalId
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String deActiveRuleWithOrgId(String strOriginalId, String strFinalRuleID, String strLanguage)
            throws RMDDAOException;

    /**
     * @Author:
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List activateRule(String strFinalRuleID, String strLanguage, String strUser) throws RMDDAOException;

    /**
     * @Author:
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<String> deActivateRule(String strFinalRuleID, String strLanguage, String strUser) throws RMDDAOException;

    /**
     * @Author:
     * @param strRuleId
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getParentRule(String strRuleId, String strLanguage) throws RMDDAOException;
}
