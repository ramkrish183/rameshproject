/**
 * ============================================================
 * File : RuleTracerServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Mar 16, 2011
 * History
 * Modified By : Initial Release
 * Classification : iGATE Sensitive
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.valueobjects.ParicipatedRuleResultVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RunRecreatorBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RunRecreatorServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceCriteriaVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Mar 16, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RunRecreatorServiceImpl implements RunRecreatorServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RunRecreatorServiceImpl.class);
    private RunRecreatorBOIntf objRuleTracerBOIntf;

    /**
     * @param objRuleCommonBOIntf
     */
    public RunRecreatorServiceImpl(final RunRecreatorBOIntf objRuleTracerBOIntf) {
        this.objRuleTracerBOIntf = objRuleTracerBOIntf;
    }

    @Override
    public List<String> getCaseIds(final String strCaseId, final String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleTracerServiceImpl getCaseIds method");
        List<String> arlCaseId = null;
        try {
            arlCaseId = objRuleTracerBOIntf.getCaseIds(strCaseId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleTracerServiceImpl getCaseIds method");
        return arlCaseId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf #getRunIds(java.lang.String, java.lang.String)
     */
    @Override
    public List<String> getRunIds(final String strCaseId, final String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleTracerServiceImpl getRunIds method");
        List<String> arlRunId = null;
        try {
            arlRunId = objRuleTracerBOIntf.getRunIds(strCaseId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleTracerServiceImpl getRunIds method");
        return arlRunId;
    }

    @Override
    public List<ParicipatedRuleResultVO> getParticipatedRules(final RuleTracerServiceCriteriaVO objServiceCriteriaVO)
            throws RMDServiceException {
        List<ParicipatedRuleResultVO> arlRuleResultVO = null;
        try {
            arlRuleResultVO = objRuleTracerBOIntf.getParticipatedRules(objServiceCriteriaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);

        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objServiceCriteriaVO.getStrLanguage());
        }
        return arlRuleResultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf #
     * saveRunTrace(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects .RuleTracerServiceCriteriaVO)
     */
    @Override
    public int saveRunTrace(final RuleTracerServiceCriteriaVO objServiceCriteriaVO) throws RMDServiceException {
        int iSeqID = 0;
        try {
            iSeqID = objRuleTracerBOIntf.saveRunTrace(objServiceCriteriaVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return iSeqID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf
     * #fetchUser(com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTracerSeachCriteriaServiceVO) This Method is used to call the
     * fetchUser method in RuleTracerBOImpl
     */
    @Override
    public void fetchUser(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objRuleTracerBOIntf.fetchUser(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf
     * #fetchTrackingRuleId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTracerSeachCriteriaServiceVO) This Method is used to
     * call the fetchTrackingRuleId method in RuleTracerBOImpl
     */
    @Override
    public void fetchTrackingRuleId(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objRuleTracerBOIntf.fetchTrackingRuleId(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf #
     * fetchTrackingId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects .RuleTracerSeachCriteriaServiceVO) This Method is used to
     * call the fetchTrackingId method in RuleTracerBOImpl
     */
    @Override
    public void fetchTrackingId(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objRuleTracerBOIntf.fetchTrackingId(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTracerServiceIntf
     * #searchTrackingResult(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTracerSeachCriteriaServiceVO)
     */
    @Override
    public List searchTrackingResult(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDServiceException {
        List arlTrackingDataResult = null;
        try {
            arlTrackingDataResult = objRuleTracerBOIntf.searchTrackingResult(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlTrackingDataResult;
    }

}
