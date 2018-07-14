/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.impl
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
package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.eoa.services.cases.bo.intf.FindCaseBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FindCaseDAOIntf;
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
public class FindCaseBOImpl implements FindCaseBOIntf {

    public static final RMDLogger LOG = RMDLogger.getLogger(FindCaseBOImpl.class);
    private FindCaseDAOIntf objFindCaseDAOIntf;

    /**
     * @param objCaseDAOIntf
     */
    public FindCaseBOImpl(FindCaseDAOIntf objFindCaseDAOIntf) {
        this.objFindCaseDAOIntf = objFindCaseDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#getSearchQueueMenu
     * ()
     *//*
       * This Method is used for call the getSearchQueueMenu method in
       * FindCaseDAOImpl
       */
    @Override
    public List getSearchQueueMenu(final String strLanguage, final String strUserLanguage) throws RMDBOException {
        List arlSrchQueues;
        try {
            arlSrchQueues = objFindCaseDAOIntf.getSearchQueueMenu(strLanguage, strUserLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSrchQueues;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#
     * loadCaseStatusDropDown (java.lang.String, java.lang.String)
     */
    @Override
    public List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDBOException {
        LOG.debug("Begin FindCaseDAOImpl loadCaseStatusDropDown method");
        List arlStatus = null;
        try {
            arlStatus = objFindCaseDAOIntf.loadCaseStatusDropDown(strCondition, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        LOG.debug("End FindCaseDAOImpl loadCaseStatusDropDown method");
        return arlStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#findCases(com.
     * ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     *//* This Method is used for call the findCases method in FindCaseDAOImpl */
    @Override
    public List findCases(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List arlSearchRes;
        try {
            arlSearchRes = objFindCaseDAOIntf.findCases(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#getCases(com.ge
     * .trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    public List getCases(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List arlSearchRes;
        try {

            arlSearchRes = objFindCaseDAOIntf.getCases(findCaseSerVO);

        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    @Override
    public List getLatestCases(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List arlSearchRes;
        try {
            arlSearchRes = objFindCaseDAOIntf.getLatestCases(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    @Override
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        Map arlSearchRes;
        try {
            arlSearchRes = objFindCaseDAOIntf.getLatestCaseRules(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#getRxTitles(com
     * .ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    public List getRxTitles(final FindCaseServiceVO findCaseSerVO) throws RMDBOException {
        List arlSearchRes;
        try {
            arlSearchRes = objFindCaseDAOIntf.getRxTitles(findCaseSerVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearchRes;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.FindCaseBOIntf#getCaseType(com
     * .ge.trans.rmd.services.cases.service.valueobjects.CaseTypeVO)
     */
    @Override
    public List<CaseTypeVO> getCaseType(CaseTypeVO caseTypeVO) throws RMDBOException {
        List<CaseTypeVO> caseTypeLst;
        try {
            caseTypeLst = objFindCaseDAOIntf.getCaseType(caseTypeVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return caseTypeLst;
    }
}
