/**
 * ============================================================
 * File : SearchRuleIntf.java
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
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceDefaultVO;

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
public interface SearchRuleIntf {

    /**
     * @Author:
     * @param searchRuleServiceCriteriaVO
     * @return
     * @Description:
     */

    List getSearchRuleResult(SearchRuleServiceCriteriaVO searchRuleServiceCriteriaVO) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @Description:
     */
    SearchRuleServiceDefaultVO getSearchRuleCriteria(String strLanguage) throws RMDDAOException;
}
