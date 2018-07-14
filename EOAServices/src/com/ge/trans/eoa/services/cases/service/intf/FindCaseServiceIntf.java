/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseServiceIntf
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 10, 2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTypeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 10, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface FindCaseServiceIntf {

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param condition
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method fetches the list of cases for the given Search
     *               criteria
     */
    List findCases(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     */
    List getRxTitles(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the list of cases for the given
     *               search criteria
     */
    List getCases(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the list of cases for the given
     *               search criteria
     */
    List getLatestCases(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param findCaseServiceVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the list of cases and rules for
     *               the given search criteria
     */
    Map getLatestCaseRules(FindCaseServiceVO findCaseServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param caseTypeVO
     * @return
     * @throws RMDServiceException
     * @Description: This method is used to get the list of Case Type in the
     *               application
     */
    List<CaseTypeVO> getCaseType(CaseTypeVO caseTypeVO) throws RMDServiceException;
}