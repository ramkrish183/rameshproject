/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
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
package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTypeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 10,2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface FindCaseDAOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param strCondition
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List findCases(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

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
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getLatestCases(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @Author:
     * @param findCaseSerVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Map getLatestCaseRules(FindCaseServiceVO findCaseSerVO) throws RMDBOException;

    /**
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     */
    List getRxTitles(FindCaseServiceVO findCaseSerVO) throws RMDDAOException;

    /**
     * @param findCaseSerVO
     * @return
     * @throws RMDDAOException
     */
    List<CaseTypeVO> getCaseType(CaseTypeVO caseTypeVO) throws RMDDAOException;

}
