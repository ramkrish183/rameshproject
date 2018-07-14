/**
 * ============================================================
 * File : RuleCommonAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 4, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RuleCommonDAOIntf {

    /**
     * @Author:
     * @param strFaultCode
     * @return
     * @Description:
     */
    List getFaultCode(String strFaultCode, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strTitle
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List searchRxTitles(String strTitle, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param simpleRuleServiceVO
     * @return
     * @Description:
     */
    List getSubID(String strFaultCode, String strSubId, String strLanguage, String family) throws RMDDAOException;

    /**
     * @Author:
     * @param language
     * @return list of controller IDs
     * @Description:
     */
    List getControllerID(String strLanguage) throws RMDDAOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getFamily(String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getModel(String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<ElementVO> getModel(String strLanguage, String strCustomerId) throws RMDDAOException;

    /**
     * @param strlanguage,familyName
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getFamilies(String strLanguage, String familyName) throws RMDDAOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer) throws RMDDAOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer) throws RMDDAOException;
    
    List getLookupValues(String strListName) throws RMDDAOException ;
}
