/**
 * ============================================================
 * File : RuleCommonServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleCommonServiceIntf;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

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
public class RuleCommonServiceImpl implements RuleCommonServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleCommonServiceImpl.class);
    private RuleCommonBOIntf objRuleCommonBOIntf;

    /**
     * @param objRuleCommonBOIntf
     */
    public RuleCommonServiceImpl(final RuleCommonBOIntf objRuleCommonBOIntf) {
        this.objRuleCommonBOIntf = objRuleCommonBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceInf
     * #getFaultCode(java.lang.String)
     */
    /*
     * This Method is used for call the getFaultCode method in
     * RuleCommonServiceImpl
     */
    @Override
    public List getFaultCode(final String strFaultCode, final String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getFaultCode method");
        List faultCodeList = null;
        try {
            faultCodeList = objRuleCommonBOIntf.getFaultCode(strFaultCode, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("Begin RuleCommonServiceImpl getFaultCode method");
        return faultCodeList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf #getSubID(java.lang.String, java.lang.String)
     */
    /*
     * This Method is used for call the getSubID method in
     * RuleCommonServiceImpl(For Future)
     */
    @Override
    public List getSubID(final String strFaultCode, final String strSubId, String strLanguage, String family)
            throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getSubID method");
        List list = null;
        try {
            list = objRuleCommonBOIntf.getSubID(strFaultCode, strSubId, strLanguage, family);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getSubID method");
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf #getControllerID(java.lang.String)
     */
    /*
     * This Method is used for call the getControllerID method in
     * RuleCommonServiceImpl(For Future)
     */
    @Override
    public List getControllerID(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getControllerID method");
        List list = null;
        try {
            list = objRuleCommonBOIntf.getControllerID(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getControllerID method");
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf #searchRxTitles(java.lang.String)
     */
    /*
     * This Method is used for call the searchRxTitles method in
     * RuleCommonServiceImpl
     */
    @Override
    public List searchRxTitles(final String strTitle, final String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl searchRxTitles method");
        List arlRxTitles = null;
        try {
            arlRxTitles = objRuleCommonBOIntf.searchRxTitles(strTitle, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getSearchRuleResult method");
        return arlRxTitles;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf#getFamily()
     */
    /*
     * This Method is used for call the getFamily method in
     * RuleCommonServiceImpl
     */
    @Override
    public List getFamily(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getFamily method");
        List arlFamily = null;
        try {
            arlFamily = objRuleCommonBOIntf.getFamily(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getFamily method");
        return arlFamily;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf#getFamily()
     */
    /*
     * This Method is used for call the getFamilies method in
     * RuleCommonServiceImpl
     */
    @Override
    public List getFamilies(String strLanguage, String familyName) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getFamilies method");
        List arlFamilies = null;
        try {
            arlFamilies = objRuleCommonBOIntf.getFamilies(strLanguage, familyName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getFamilies method");
        return arlFamilies;
    }

    /**
     * @param objRuleCommonBOIntf
     *            the objRuleCommonBOIntf to set
     */
    public void setObjRuleCommonBOIntf(RuleCommonBOIntf objRuleCommonBOIntf) {
        this.objRuleCommonBOIntf = objRuleCommonBOIntf;
    }

    /* This Method is used for call the getModel method in RuleCommonBOIntf */
    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.
     * RuleCommonServiceIntf#getModel()
     */
    @Override
    public List getModel(String strLanguage) throws RMDServiceException {
        LOG.debug("Begin RuleCommonServiceImpl getModel method");
        List arlFamily = null;
        try {
            arlFamily = objRuleCommonBOIntf.getModel(strLanguage);
        } catch (RMDDAOException ex) {
        	LOG.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
        	LOG.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
        	LOG.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getModel method");
        return arlFamily;
    }

    @Override
    public List<ElementVO> getModel(String strLanguage, String strCustomerId) throws RMDServiceException {
        List<ElementVO> arlModel = null;
        try {
            arlModel = objRuleCommonBOIntf.getModel(strLanguage, strCustomerId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        LOG.debug("End RuleCommonServiceImpl getModel method");
        return arlModel;
    }

    /**
     * @return the objRuleCommonBOIntf
     */
    public RuleCommonBOIntf getObjRuleCommonBOIntf() {
        return objRuleCommonBOIntf;
    }

    @Override
    public List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer)
            throws RMDServiceException {
        List<ElementVO> arlFamily = null;
        try {
            arlFamily = objRuleCommonBOIntf.getFamiliesForExternalRuleSearch(strLanguage, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFamily;
    }

    @Override
    public List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer)
            throws RMDServiceException {
        List<ElementVO> arlFamily = null;
        try {
            arlFamily = objRuleCommonBOIntf.getFamiliesForAlertsRuleAuthor(strLanguage, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFamily;
    }
    
    
    
    @Override
    public List<ElementVO> getLookupValues(String strListName) throws RMDServiceException {
        List<ElementVO> arlFamily = null;
        try {
            arlFamily = objRuleCommonBOIntf.getLookupValues(strListName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, null);
        }
        return arlFamily;
    }

}
