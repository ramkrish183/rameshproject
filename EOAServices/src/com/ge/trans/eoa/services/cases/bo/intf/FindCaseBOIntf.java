/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 10,2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
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
public interface FindCaseBOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDBOException;

    /**
     * @param strCondition
     * @param strLanguage
     * @throws RMDBOException
     */
    List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List findCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     */
    List getRxTitles(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param FindCaseServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */

    List getLatestCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param FindCaseServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */

    Map getLatestCaseRules(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param caseTypeVO
     * @return
     * @throws RMDBOException
     * @Description:
     */

    List<CaseTypeVO> getCaseType(CaseTypeVO caseTypeVO) throws RMDBOException;

}
