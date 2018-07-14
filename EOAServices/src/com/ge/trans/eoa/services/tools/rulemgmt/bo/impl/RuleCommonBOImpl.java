/**
 * ============================================================
 * File : RuleCommonBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf;

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
public class RuleCommonBOImpl implements RuleCommonBOIntf {

    private RuleCommonDAOIntf objRuleCommonDAO;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleCommonBOImpl.class);

    /**
     * @param objRuleCommonDAO
     */
    public RuleCommonBOImpl(final RuleCommonDAOIntf objRuleCommonDAO) {
        this.objRuleCommonDAO = objRuleCommonDAO;
    }

    /**
     * @return the objRuleCommonDAO
     */
    public RuleCommonDAOIntf getObjRuleCommonDAO() {
        return objRuleCommonDAO;
    }

    /**
     * @param objRuleCommonDAO
     *            the objRuleCommonDAO to set
     */
    public void setObjRuleCommonDAO(final RuleCommonDAOIntf objRuleCommonDAO) {
        this.objRuleCommonDAO = objRuleCommonDAO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf
     * #getFaultCode(java.lang.String)
     */
    /*
     * This Method is used for call the getFaultCode method in RuleCommonDAOImpl
     */
    @Override
    public List getFaultCode(final String strFaultCode, final String strLanguage) throws RMDBOException {
        List faultCodeList = null;
        try {
            faultCodeList = objRuleCommonDAO.getFaultCode(strFaultCode, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return faultCodeList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf
     * #searchRxTitles(java.lang.String)
     */
    /*
     * This Method is used for call the searchRxTitles method in
     * RuleCommonDAOImpl
     */
    @Override
    public List searchRxTitles(final String strTitle, final String strLanguage) throws RMDBOException {
        List arlRxTitles = null;
        try {
            arlRxTitles = objRuleCommonDAO.searchRxTitles(strTitle, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRxTitles;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf#
     * getFamily ()
     */
    /* This Method is used for call the getFamily method in RuleCommonDAOImpl */
    @Override
    public List getFamily(final String strLanguage) throws RMDBOException {
        List arlFamily = null;
        try {
            arlFamily = objRuleCommonDAO.getFamily(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFamily;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf#
     * getFamily ()
     */
    /* This Method is used for call the getFamily method in RuleCommonDAOImpl */
    @Override
    public List getFamilies(final String strLanguage, final String familyName) throws RMDBOException {
        List arlFamilies = null;
        try {
            arlFamilies = objRuleCommonDAO.getFamilies(strLanguage, familyName);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFamilies;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf
     * #getSubID(java.lang.String, java.lang.String)
     */
    /*
     * This Method is used for call the getSubID method in RuleCommonDAOImpl(For
     * Future)
     */
    @Override
    public List getSubID(final String strFaultCode, final String strSubId, final String strLanguage,
            final String family) throws RMDBOException {
        List list = null;
        try {
            list = objRuleCommonDAO.getSubID(strFaultCode, strSubId, strLanguage, family);
        } catch (RMDDAOException e) {
            throw e;
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf
     * #getSubID(java.lang.String, java.lang.String)
     */
    /*
     * This Method is used for call the getSubID method in RuleCommonDAOImpl(For
     * Future)
     */
    @Override
    public List getControllerID(final String strLanguage) throws RMDBOException {
        List list = null;
        try {
            list = objRuleCommonDAO.getControllerID(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleCommonBOIntf#
     * getModel ()
     */
    /* This Method is used for call the getModel method in RuleCommonDAOImpl */
    @Override
    public List getModel(final String strLanguage) throws RMDBOException {
        List arlFamily = null;
        try {
            arlFamily = objRuleCommonDAO.getModel(strLanguage);
        } catch (RMDDAOException e) {
        	LOG.error(e, e);
            throw e;
        } catch (Exception e) {
        	LOG.error(e, e);
        }
        return arlFamily;
    }

    @Override
    public List<ElementVO> getModel(final String strLanguage, final String strCustomerId) throws RMDBOException {
        List<ElementVO> arlModel = null;
        try {
            arlModel = objRuleCommonDAO.getModel(strLanguage, strCustomerId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlModel;
    }

    @Override
    public List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer)
            throws RMDBOException {
        List<ElementVO> arlFamily = null;
        try {
            arlFamily = objRuleCommonDAO.getFamiliesForExternalRuleSearch(strLanguage, strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFamily;
    }

    @Override
    public List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer)
            throws RMDBOException {
        List<ElementVO> arlFamily = null;
        try {
            arlFamily = objRuleCommonDAO.getFamiliesForAlertsRuleAuthor(strLanguage, strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFamily;
    }
    
        
        @Override
        public List getLookupValues(String strListName) throws RMDBOException {
            List arlFamily = null;
            try {
                arlFamily = objRuleCommonDAO.getLookupValues(strListName);
            } catch (RMDDAOException e) {
                throw e;
            }
            return arlFamily;
        }
    

}
