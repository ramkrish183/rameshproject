/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindCaseServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.impl
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
package com.ge.trans.eoa.services.cases.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.bo.intf.FindCaseBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindCaseServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTypeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

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
public class FindCaseServiceImpl implements Serializable, FindCaseServiceIntf {

    private static final long serialVersionUID = 154542L;
    private FindCaseBOIntf objFindCaseBOIntf;

    /**
     * @param objCaseBOIntf
     */
    public FindCaseServiceImpl(FindCaseBOIntf objFindCaseBOIntf) {
        this.objFindCaseBOIntf = objFindCaseBOIntf;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getSearchQueueMenu()
     *//*
       * This Method is used for call the getSearchQueueMenu method in
       * FindCaseBOImpl
       */
    @Override
    public List getSearchQueueMenu(final String strLanguage, final String strUserLanguage) throws RMDServiceException {
        List arlSrchQueues = null;
        try {
            arlSrchQueues = objFindCaseBOIntf.getSearchQueueMenu(strLanguage, strUserLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlSrchQueues;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * loadCaseStatusDropDown(java.lang.String, java.lang.String)
     */
    @Override
    public List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDServiceException {
        List arlStatus = null;
        try {
            arlStatus = objFindCaseBOIntf.loadCaseStatusDropDown(strCondition, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf
     * #findCases
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     *//* This Method is used for call the findCases method in FindCaseBOImpl */
    @Override
    public List findCases(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseBOIntf.findCases(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#getCases
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    public List getCases(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseBOIntf.getCases(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getLatestCases
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getLatestCases(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseBOIntf.getLatestCases(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getLatestCaseRules
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        Map arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseBOIntf.getLatestCaseRules(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getRxTitles
     * (com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    public List getRxTitles(final FindCaseServiceVO findCaseServiceVO) throws RMDServiceException {
        List arlSearchResults = null;
        try {
            arlSearchResults = objFindCaseBOIntf.getRxTitles(findCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findCaseServiceVO.getStrLanguage());
        }
        return arlSearchResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindCaseServiceIntf#
     * getCaseType
     * (com.ge.trans.rmd.services.cases.service.valueobjects.CaseTypeVO)
     */
    @Override
    public List<CaseTypeVO> getCaseType(CaseTypeVO caseTypeVO) throws RMDServiceException {
        List<CaseTypeVO> caseTypeLst = null;
        try {
            caseTypeLst = objFindCaseBOIntf.getCaseType(caseTypeVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, caseTypeVO.getStrLanguage());
        }
        return caseTypeLst;
    }
}
