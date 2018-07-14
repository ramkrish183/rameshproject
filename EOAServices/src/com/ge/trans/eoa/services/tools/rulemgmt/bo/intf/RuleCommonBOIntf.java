/**
 * ============================================================
 * File : RuleCommonBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;

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
public interface RuleCommonBOIntf {

    /**
     * @Author:
     * @param strFaultCode
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getFaultCode(String strFaultCode, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strTitle
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List searchRxTitles(String strTitle, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strFaultCode
     * @param strSubId
     * @param strLanguage
     * @param family
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getSubID(String strFaultCode, String strSubId, String strLanguage, String family) throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage
     * @return list of controller IDs
     * @throws RMDBOException
     * @Description:
     */
    List getControllerID(String strLanguage) throws RMDBOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getFamily(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getModel(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<ElementVO> getModel(String strLanguage, String strCustomerId) throws RMDBOException;

    /**
     * @param strlanguage,familyName
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getFamilies(String strLanguage, String familyName) throws RMDBOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer) throws RMDBOException;

    /**
     * @param strlanguage
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer) throws RMDBOException;

    List getLookupValues(String strListName) throws RMDBOException;

}
