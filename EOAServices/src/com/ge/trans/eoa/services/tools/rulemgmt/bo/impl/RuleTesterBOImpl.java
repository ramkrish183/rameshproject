/**
 * ============================================================
 * File : RuleTesterBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleTesterBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleTesterDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call RuleTesterDAOImpl methods
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RuleTesterBOImpl implements RuleTesterBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTesterBOImpl.class);
    private RuleTesterDAOIntf objTestDAOIntf;

    /**
     * @param objTestDAOIntf
     */
    public RuleTesterBOImpl(final RuleTesterDAOIntf objTestDAOIntf) {
        this.objTestDAOIntf = objTestDAOIntf;
    }

    /**
     * @return the objTestDAOIntf
     */
    public RuleTesterDAOIntf getObjTestDAOIntf() {
        return objTestDAOIntf;
    }

    /**
     * @param objTestDAOIntf
     *            the objTestDAOIntf to set
     */
    public void setObjTestDAOIntf(final RuleTesterDAOIntf objTestDAOIntf) {
        this.objTestDAOIntf = objTestDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #saveRunTest
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to call the saveRunTest method
     * in RuleTesterDAOImpl
     */
    @Override
    public int saveRunTest(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl saveRunTest method");
        int iSeqId = 0;
        try {

            iSeqId = objTestDAOIntf.saveRunTest(objRuleTesterServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl saveRunTest method");
        return iSeqId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchAssetHeader
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to call the fetchAssetHeader
     * method in RuleTesterDAOImpl
     */
    @Override
    public void fetchAssetHeader(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchAssetHeader method");
        try {
            objTestDAOIntf.fetchAssetHeader(objRuleTesterServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchAssetHeader method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchAssetNumber
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to call the fetchAssetNumber
     * method in RuleTesterDAOImpl
     */
    @Override
    public void fetchAssetNumber(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchAssetNumber method");
        try {
            objTestDAOIntf.fetchAssetNumber(objRuleTesterServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchAssetNumber method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchUser(com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTesterSeachCriteriaVO) This method is used to call the fetchUser
     * method in RuleTesterDAOImpl
     */
    @Override
    public void fetchUser(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchUser method");
        try {
            objTestDAOIntf.fetchUser(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchUser method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchTrackingRuleId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO) This method is used to call the
     * fetchTrackingRuleId method in RuleTesterDAOImpl
     */

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchTrackingId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO) This method is used to call the
     * fetchTrackingId method in RuleTesterDAOImpl
     */
    @Override
    public void getTrackingDetails(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchTrackingId method");
        try {
            objTestDAOIntf.getTrackingDetails(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchTrackingId method");
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTesterBOIntf#
     * searchTrackingResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO)
     */
    @Override
    public List searchTrackingResult(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl searchTrackingResult method");
        List arlTrackingResults;
        try {
            arlTrackingResults = objTestDAOIntf.searchTrackingResult(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl searchTrackingResult method");
        return arlTrackingResults;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleTesterBOIntf#
     * getArlHeaders()
     */
    @Override
    public List getArlHeaders() throws RMDBOException {
        List arlHeaders;
        LOG.debug("Entering into RuleTesterBOImpl getArlHeaders method");
        try {
            arlHeaders = objTestDAOIntf.getArlHeaders();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl getArlHeaders method");
        return arlHeaders;
    }

    @Override
    public FaultDataDetailsServiceVO getFaults(
            final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException {

        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = objTestDAOIntf.getFaults(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }

        return objFaultDataDetailsServiceVO;
    }

    @Override
    public void getTrackingRules(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchTrackingId method");
        try {
            objTestDAOIntf.getTrackingRules(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchTrackingId method");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.TestBOIntf
     * #fetchRuleId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterServiceVO) This method is used to call the
     * fetchRuleId method in RuleTesterDAOImpl
     */
    @Override
    public void fetchRuleId(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDBOException {
        LOG.debug("Begin RuleTesterBOImpl fetchRuleId method");
        try {
            objTestDAOIntf.fetchRuleId(objRuleTesterServiceVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleTesterBOImpl fetchRuleId method");
    }
}
