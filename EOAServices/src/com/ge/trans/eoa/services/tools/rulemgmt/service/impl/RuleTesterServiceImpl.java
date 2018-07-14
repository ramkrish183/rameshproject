/**
 * ============================================================
 * File : RuleTesterServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleTesterBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleTesterServiceIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterServiceVO;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call the RuleTesterBOImpl methods
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class RuleTesterServiceImpl implements RuleTesterServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTesterServiceImpl.class);
    private RuleTesterBOIntf objTestBOIntf;

    /**
     * @param objTestBOIntf
     */
    public RuleTesterServiceImpl(final RuleTesterBOIntf objTestBOIntf) {
        this.objTestBOIntf = objTestBOIntf;
    }

    /**
     * @return the objTestBOIntf
     */
    public RuleTesterBOIntf getObjTestBOIntf() {
        return objTestBOIntf;
    }

    /**
     * @param objTestBOIntf
     *            the objTestBOIntf to set
     */
    public void setObjTestBOIntf(final RuleTesterBOIntf objTestBOIntf) {
        this.objTestBOIntf = objTestBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #saveRunTest(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterServiceVO) This Method is used to call the
     * saveRunTest method in RuleTesterBOImpl
     */
    @Override
    public int saveRunTest(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException {
        int iSeqID = 0;
        try {
            iSeqID = objTestBOIntf.saveRunTest(objRuleTesterServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return iSeqID;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchAssetHeader(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterServiceVO) This Method is used to call the
     * fetchAssetHeader method in RuleTesterBOImpl
     */
    @Override
    public void fetchAssetHeader(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException {
        try {
            objTestBOIntf.fetchAssetHeader(objRuleTesterServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchAssetNumber(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterServiceVO) This Method is used to call the
     * fetchAssetNumber method in RuleTesterBOImpl
     */
    @Override
    public void fetchAssetNumber(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException {
        try {
            objTestBOIntf.fetchAssetNumber(objRuleTesterServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchUser(com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTesterSeachCriteriaServiceVO) This Method is used to call the
     * fetchUser method in RuleTesterBOImpl
     */
    @Override
    public void fetchUser(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objTestBOIntf.fetchUser(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchTrackingRuleId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterSeachCriteriaServiceVO) This Method is used to
     * call the fetchTrackingRuleId method in RuleTesterBOImpl
     */
    @Override
    public void getTrackingRules(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objTestBOIntf.getTrackingRules(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchTrackingId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterSeachCriteriaServiceVO) This Method is used to
     * call the fetchTrackingId method in RuleTesterBOImpl
     */
    @Override
    public void getTrackingDetails(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException {
        try {
            objTestBOIntf.getTrackingDetails(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #searchResult(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterSeachCriteriaServiceVO) This Method is used to
     * call the searchResult method in RuleTesterBOImpl
     */
    @Override
    public FaultDataDetailsServiceVO getFaults(
            final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDServiceException {
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = null;
        try {
            objFaultDataDetailsServiceVO = objTestBOIntf.getFaults(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return objFaultDataDetailsServiceVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTesterServiceIntf#searchTrackingResult(com.ge.trans.rmd.services.
     * tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO)
     */
    @Override
    public List searchTrackingResult(final RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDServiceException {
        List arlTrackingResults;
        try {
            arlTrackingResults = objTestBOIntf.searchTrackingResult(objRuleTesterSeachCriteriaServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlTrackingResults;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleTesterServiceIntf#getArlHeaders()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List getArlHeaders() throws RMDServiceException {
        List arlHeaders = new ArrayList();
        try {
            LOG.debug("In getArlHeaders - serv impl:");
            arlHeaders = objTestBOIntf.getArlHeaders();
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return arlHeaders;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.TestServiceIntf
     * #fetchRuleId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTesterServiceVO) This Method is used to call the
     * fetchRuleId method in RuleTesterBOImpl
     */
    @Override
    public void fetchRuleId(final RuleTesterServiceVO objRuleTesterServiceVO) throws RMDServiceException {
        try {
            objTestBOIntf.fetchRuleId(objRuleTesterServiceVO);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
    }
}
