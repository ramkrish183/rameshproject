/**
 * ============================================================
 * File : RuleCommonServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;

import com.ge.trans.rmd.exception.RMDServiceException;

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
public interface RuleCommonServiceIntf {

    /**
     * @Author:
     * @param strFaultCode
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getFaultCode(String strFaultCode, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strFaultCode
     * @param strSubId
     * @param family
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getSubID(String strFaultCode, String strSubId, String strLanguage, String family) throws RMDServiceException;

    /**
     * @Author:
     * @param strLanguage
     * @return list of controller ids
     * @throws RMDServiceException
     * @Description:
     */
    List getControllerID(String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param strTitle
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List searchRxTitles(String strTitle, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getFamily(String strlanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getModel(String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ElementVO> getModel(String strLanguage, String strCustomerId) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getFamilies(String strlanguage, String familyName) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer) throws RMDServiceException;
    List getLookupValues(String strListName) throws RMDServiceException;
}
