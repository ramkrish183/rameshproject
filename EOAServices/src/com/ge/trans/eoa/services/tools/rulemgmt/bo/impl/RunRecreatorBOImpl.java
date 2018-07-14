/**
 * ============================================================
 * File : RuleTracerBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
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

package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.valueobjects.ParicipatedRuleResultVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RunRecreatorBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RunRecreatorDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceCriteriaVO;

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
public class RunRecreatorBOImpl implements RunRecreatorBOIntf {

    private RunRecreatorDAOIntf objRuleTracerDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RunRecreatorBOImpl.class);

    /**
     * @param objRuleCommonDAO
     */
    public RunRecreatorBOImpl(final RunRecreatorDAOIntf objRuleTracerDAOIntf) {
        this.objRuleTracerDAOIntf = objRuleTracerDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf#
     * getCaseIds (java.lang.String, java.lang.String)
     */
    @Override
    public List<String> getCaseIds(final String strCaseId, final String strLanguage) {
        List<String> arlCaseId = null;
        try {
            arlCaseId = objRuleTracerDAOIntf.getCaseIds(strCaseId, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlCaseId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf#
     * getRunIds (java.lang.String, java.lang.String)
     */
    @Override
    public List<String> getRunIds(final String strCaseId, final String strLanguage) {
        List<String> arlRunId = null;
        try {
            arlRunId = objRuleTracerDAOIntf.getRunIds(strCaseId, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlRunId;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf#
     * getParticipatedRules
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTracerServiceCriteriaVO)
     */
    @Override
    public List<ParicipatedRuleResultVO> getParticipatedRules(final RuleTracerServiceCriteriaVO objServiceCriteriaVO) {
        List<ParicipatedRuleResultVO> arlRuleResultVO = null;
        try {
            arlRuleResultVO = objRuleTracerDAOIntf.getParticipatedRules(objServiceCriteriaVO);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlRuleResultVO;
    }

    /**
     * @param objServiceCriteriaVO
     * @return
     * @throws RMDBOException
     */
    @Override
    public int saveRunTrace(final RuleTracerServiceCriteriaVO objServiceCriteriaVO) throws RMDBOException {
        LOG.debug("Begin RuleTracerBOImpl saveRunTrace method");
        int iSeqId = 0;
        try {

            iSeqId = objRuleTracerDAOIntf.saveRunTrace(objServiceCriteriaVO);

        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTracerBOImpl saveRunTrace method");
        return iSeqId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf
     * #fetchUser(com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTracerSeachCriteriaServiceVO) This method is used to call the
     * fetchUser method in RuleTracerDAOImpl
     */
    @Override
    public void fetchUser(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTracerBOImpl fetchUser method");
        try {
            objRuleTracerDAOIntf.fetchUser(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTracerBOImpl fetchUser method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf
     * #fetchTrackingRuleId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTracerSeachCriteriaServiceVO) This method is used to call the
     * fetchTrackingRuleId method in RuleTracerDAOImpl
     */
    @Override
    public void fetchTrackingRuleId(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTracerBOImpl fetchTrackingRuleId method");
        try {
            objRuleTracerDAOIntf.fetchTrackingRuleId(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTracerBOImpl fetchTrackingRuleId method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf
     * #fetchTrackingId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTracerSeachCriteriaServiceVO) This method is used to call the
     * fetchTrackingId method in RuleTracerDAOImpl
     */
    @Override
    public void fetchTrackingId(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTracerBOImpl fetchTrackingId method");
        try {
            objRuleTracerDAOIntf.fetchTrackingId(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTracerBOImpl fetchTrackingId method");
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTracerBOIntf#
     * searchTrackingResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTracerSeachCriteriaServiceVO)
     */
    @Override
    public List searchTrackingResult(final RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTracerBOImpl searchTrackingResult method");
        List arlTrackingDataResult = null;
        try {
            arlTrackingDataResult = objRuleTracerDAOIntf.searchTrackingResult(objRuleTracerSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTracerBOImpl searchTrackingResult method");
        return arlTrackingDataResult;
    }

}
