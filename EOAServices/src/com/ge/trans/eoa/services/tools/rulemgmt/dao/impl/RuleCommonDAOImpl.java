/**
 * ============================================================
 * File : RuleCommonDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstAssetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstModelHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmRecomHVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
@SuppressWarnings({ "serial", "unchecked", "unused" })
public class RuleCommonDAOImpl extends RMDCommonDAO implements RuleCommonDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleCommonDAOImpl.class);

    public RuleCommonDAOImpl() {
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getFaultCode(java.lang.String)
     */
    /* This Method is used for fetch the fault code list from database */
    @Override
    public List<String> getFaultCode(String strFaultCode, String strLanguage) throws RMDDAOException {
        Session session = null;

        List<String> arlFaultCode = new ArrayList<String>();

        StringBuilder queryString = new StringBuilder("");
        Query query = null;

        try {
            session = getHibernateSession();
            if (!RMDServiceConstants.ENTER_STAR.equalsIgnoreCase(strFaultCode.trim())) {

                queryString.append(" SELECT DISTINCT FAULT_CODE");
                queryString.append(" FROM GETS_RMD.GETS_RMD_FAULT_CODES ");

                queryString.append(" WHERE LOWER(FAULT_CODE) LIKE LOWER(:FAULTCODE)");
                query = session.createSQLQuery(queryString.toString());

                query.setParameter("FAULTCODE", "%" + strFaultCode + "%");

                final List<Object> lookupList = query.list();

                if (lookupList != null && !lookupList.isEmpty()) {
                    arlFaultCode = new ArrayList();
                    for (int i = 0; i < lookupList.size(); i++) {

                        arlFaultCode.add(lookupList.get(i).toString());

                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlFaultCode;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getSubID (java.lang.String, java.lang.String)
     */
    /* This Method is used for fetch the SubID list from database */
    @Override
    @SuppressWarnings("rawtypes")
    public List getSubID(String strFaultCode, String strSubId, String strLanguage, String family) {
        List subIDList = null;
        Session session = null;
        ElementVO objElementVO = null;
        List<String> lookupList = null;
        Query query = null;
        LOG.debug("Enter into getSubID in RuleCommonDAOImpl method");
        StringBuilder queryString = new StringBuilder();
        StringBuilder CCAQueryString = new StringBuilder();
        try {
            session = getHibernateSession();
            if (!RMDCommonUtility.isNullOrEmpty(family)) {
                if (RMDServiceConstants.FAMILY_CCA.equalsIgnoreCase(family)) {
                    // Query to retrieve Sub id for CCA family
                    CCAQueryString = RMDServiceConstants.QueryConstants.FETCH_SUB_ID_FOR_CCA_FAMILY;
                    if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
                        CCAQueryString.append(" AND CODE.FAULT_CODE = :faultCode");
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(strSubId)) {
                        CCAQueryString.append(" AND LOWER(CODE.SUB_ID) LIKE LOWER( :subId) ");
                    }
                    query = session.createSQLQuery(CCAQueryString.toString());

                } else {
                    // Query to retrieve Sub id for other than CCA family
                    queryString = RMDServiceConstants.QueryConstants.FETCH_SUB_ID_DATA;
                    if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
                        queryString.append(" AND FAULT_CODE = :faultCode ");
                    }
                    if (!RMDCommonUtility.isNullOrEmpty(strSubId)) {
                        queryString.append(" AND LOWER(SUB_ID) LIKE LOWER ( :subId) ");
                    }
                    query = session.createSQLQuery(queryString.toString());

                }

            } else {
                // query to retrieve the default sub id irrespective of family
                query = session.createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_SUB_ID_DATA.toString());
            }

            if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
                query.setParameter(RMDServiceConstants.FAULT_CODE, strFaultCode);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strSubId)) {
                query.setParameter(RMDServiceConstants.SUB_ID,
                        RMDServiceConstants.PERCENTAGE + strSubId + RMDServiceConstants.PERCENTAGE);
            }
            if (!RMDCommonUtility.isNullOrEmpty(family)) {
                if (!RMDServiceConstants.FAMILY_CCA.equalsIgnoreCase(family)) {
                    query.setParameter(RMDServiceConstants.FAMILY, family);
                }
            }
            lookupList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
                subIDList = new ArrayList();
                for (String strSubIdValue : lookupList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(strSubIdValue);
                    subIDList.add(objElementVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SUBID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End of getSubID in RuleCommonDAOImpl method");

        return subIDList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getControllerID
     */
    /* This Method is used for fetch the SubID list from database */
    @Override
    @SuppressWarnings("rawtypes")
    public List getControllerID(String strLanguage) {
        List<ElementVO> controlIDList = null;
        Session session = null;
        ElementVO objElementVO = null;
        Query query = null;
        LOG.debug("Enter into getControllerID in RuleCommonDAOImpl method");
        StringBuilder queryString = new StringBuilder();
        StringBuilder CCAQueryString = new StringBuilder();
        List rows = null;
        try {
            session = getHibernateSession();
            queryString = RMDServiceConstants.QueryConstants.FETCH_ALL_CONTROLLER_ID_DATA;
            query = session.createSQLQuery(queryString.toString());
            rows = query.list();

            if (rows != null && !rows.isEmpty()) {
                controlIDList = new ArrayList();
                for (int i = 0; i < rows.size(); i++) {
                    objElementVO = new ElementVO();
                    Object[] row = (Object[]) rows.get(i);
                    objElementVO.setId(row[0].toString());
                    objElementVO.setName(row[1].toString());
                    controlIDList.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLERID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLERID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End of getControllerID in RuleCommonDAOImpl method");

        return controlIDList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * searchRxTitles(java.lang.String)
     */
    /* This Method is used for fetch the AdvisoryVO list from database */
    @Override
    public List searchRxTitles(String strTitle, String strLanguage) throws RMDDAOException {

        List arlRxTitles = null;
        List arlAdvisory = null;
        GetKmRecomHVO objGetKmRecom = null;
        ElementVO objElementVO = null;
        Session session = null;
        try {
            arlAdvisory = new ArrayList();
            session = getHibernateSession();
            Criteria criteria = session.createCriteria(GetKmRecomHVO.class);
            criteria.addOrder(Order.asc(RMDCommonConstants.TITLE));
            if (strTitle != null && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strTitle)) {
                criteria.add(Restrictions.like(RMDServiceConstants.TITLE_RECOM,
                        RMDCommonConstants.MODE_SYMBOL + strTitle + RMDCommonConstants.MODE_SYMBOL));
            }
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            arlRxTitles = criteria.list();
            if (null != arlRxTitles && !arlRxTitles.isEmpty()) {
                int intRxTitlesize = arlRxTitles.size();
                for (int i = 0; i < intRxTitlesize; i++) {
                    objGetKmRecom = (GetKmRecomHVO) arlRxTitles.get(i);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objGetKmRecom.getTitle());
                    objElementVO.setName(objGetKmRecom.getTitle());
                    arlAdvisory.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RXTITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            arlRxTitles = null;
        }

        return arlAdvisory;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getFamily() This method calls the general getLookupValues method to get
     * the family names
     */

    @Override
    public List getFamily(String strLanguage) throws RMDDAOException {

        List<String> lookupList = getLookupValues(RMDServiceConstants.EOA_FAMILY_LIST_NAME);

        return lookupList;

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getLookupValues(strListName) This is the general method which returns the
     * lookup values based on the LIST_NAME passed
     */
    @SuppressWarnings("rawtypes")
    public List getLookupValues(String strListName) throws RMDDAOException {

        List arlFunctions = null;
        Session session = null;
        ElementVO objElementVO = null;
        try {
            session = getHibernateSession();

            String queryString = "SELECT LOOK_VALUE FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LOOK_STATE='Active' AND LIST_NAME=:listName";

            Query query = session.createSQLQuery(queryString);
            query.setParameter("listName", strListName);

            List<String> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlFunctions = new ArrayList();

                for (int i = 0; i < lookupList.size(); i++) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(lookupList.get(i));

                    objElementVO.setName(lookupList.get(i));
                    arlFunctions.add(objElementVO);

                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);

        }
        return arlFunctions;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getModel ()
     */
    /* This Method is used for fetch the model list from database */
    @Override
    @SuppressWarnings("rawtypes")
    public List getModel(String strLanguage) throws RMDDAOException {
        ArrayList arlModels = null;
        Session session = null;

        ElementVO objElementVO = null;
        LOG.debug("Enter into getModel in RuleCommonDAOImpl method");

        try {
            session = getHibernateSession();
            String queryString = "SELECT objid,model_name FROM gets_rmd_model";
            Query query = session.createSQLQuery(queryString);
            List<Object> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlModels = new ArrayList();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {

                    final Object[] lookupRecord = (Object[]) iter.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(lookupRecord[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(lookupRecord[1]));
                    arlModels.add(objElementVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        LOG.debug("End of getModel in RuleCommonDAOImpl method" + arlModels.size());

        return arlModels;
    }

    @Override
    public List<ElementVO> getModel(String strLanguage, String strCustomerId) throws RMDDAOException {
        Session session = null;
        List modelList;
        List arlmodel = null;
        GetAsstAssetHVO objGetAsstAssetHVO;
        ElementVO elementVO;
        Set<String> hsModel = new HashSet<String>();
        String strModel = null;
        try {

            session = getHibernateSession();

            if (null != strCustomerId && !RMDCommonConstants.EMPTY_STRING.equals(strCustomerId)) {
                Criteria criteria = session.createCriteria(GetAsstAssetHVO.class);
                criteria.createAlias(RMDCommonConstants.GETASST_MODEL, RMDCommonConstants.MODEL)
                        .setFetchMode(RMDCommonConstants.GETASST_MODEL, FetchMode.JOIN);
                criteria.createAlias(RMDCommonConstants.GET_CM_CUSTOMER, RMDCommonConstants.CUSTOMER)
                        .setFetchMode(RMDCommonConstants.GET_CM_CUSTOMER, FetchMode.JOIN);

                criteria.add(Restrictions.eq(RMDCommonConstants.CUSTOMER_CUSTOMERID, strCustomerId));
                modelList = criteria.list();
                int modelLstSize = modelList.size();
                if (modelLstSize > 0) {
                    arlmodel = new ArrayList<ElementVO>();
                    for (int i = 0; i < modelLstSize; i++) {
                        objGetAsstAssetHVO = (GetAsstAssetHVO) modelList.get(i);
                        strModel = objGetAsstAssetHVO.getGetAsstModel().getModelName();
                        if (!hsModel.contains(strModel)) {
                            elementVO = new ElementVO();
                            elementVO.setId(strModel);
                            elementVO.setName(strModel);
                            arlmodel.add(elementVO);
                            hsModel.add(strModel);
                        }
                    }

                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            modelList = null;
        }
        return arlmodel;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf#
     * getFamilies ()
     */
    /* This Method is used for fetch the model list from database */
    @Override
    public List getFamilies(String strLanguage, String familyName) throws RMDDAOException {
        List arlFamily = null;
        GetAsstModelHVO objGetAsstModel = null;
        ElementVO objElementVO = null;
        List familyList = null;

        Session session = null;
        try {
            arlFamily = new ArrayList();
            session = getHibernateSession();
            Criteria criteria = session.createCriteria(GetAsstModelHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));

            criteria.add(Restrictions.like(RMDCommonConstants.FAMILY,
                    RMDCommonConstants.MODE_SYMBOL + familyName + RMDCommonConstants.MODE_SYMBOL));

            familyList = criteria.list();
            if (null != familyList && !familyList.isEmpty()) {
                int intFamilyListSize = familyList.size();
                for (int index = 0; index < intFamilyListSize; index++) {
                    objGetAsstModel = (GetAsstModelHVO) familyList.get(index);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objGetAsstModel.getFamily());
                    objElementVO.setName(objGetAsstModel.getFamily());
                    arlFamily.add(objElementVO);
                }
            }
        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            familyList = null;
        }

        return arlFamily;
    }

    /**
     * This is the method used for fetching the families based on external
     * customer for Rule Search
     * 
     * @param String
     *            strLanguage,String strCustomer
     * @return List<ElementVO>
     * @throws RMDDAOException
     */
    @Override
    public List<ElementVO> getFamiliesForExternalRuleSearch(String strLanguage, String strCustomer)
            throws RMDDAOException {
        List<ElementVO> arlModels = null;
        Session session = null;
        ElementVO objElementVO = null;
        StringBuilder queryString = new StringBuilder();
        try {
            session = getHibernateSession();
            queryString.append(
                    "SELECT DISTINCT FAMILY FROM GETS_TOOL_DPD_FINRUL F,GETS_TOOL_DPD_RULEDEF D,GETS_TOOL_DPD_CUST C,SA.TABLE_BUS_ORG O");
            queryString.append(" WHERE D.RULEDEF2FINRUL  = F.OBJID");
            queryString.append(" AND D.RULE_TYPE       = 'Alert'");
            queryString.append(" AND C.CUST2RULEDEF    = D.OBJID");
            queryString.append(" AND C.EXCLUDE         = 'N'");
            queryString.append(" AND C.CUST2BUSORG     = O.OBJID");
            queryString.append(" AND O.ORG_ID            =:customerId");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, strCustomer);
            List<Object> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlModels = new ArrayList<ElementVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    final String lookupRecord = (String) iter.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(lookupRecord);
                    objElementVO.setName(lookupRecord);
                    arlModels.add(objElementVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_SEARCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_SEARCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

        return arlModels;
    }

    /**
     * This is the method used for fetching the families based on external
     * customer for Rule Authoring
     * 
     * @param String
     *            strLanguage,String strCustomer
     * @return List<ElementVO>
     * @throws RMDDAOException
     */
    @Override
    public List<ElementVO> getFamiliesForAlertsRuleAuthor(String strLanguage, String strCustomer)
            throws RMDDAOException {
        List<ElementVO> arlModels = null;
        Session session = null;
        ElementVO objElementVO = null;
        StringBuilder queryString = new StringBuilder();
        try {
            session = getHibernateSession();
            queryString.append(
                    "SELECT DISTINCT DECODE(FAMILY,'ACCCA','CCA','DCCCA','CCA',FAMILY) FAMILY FROM GETS_RMD_VEHICLE V,GETS_RMD_MODEL M,GETS_RMD_FLEET F,SA.TABLE_BUS_ORG O");
            queryString.append(" WHERE V.VEHICLE2MODEL=M.OBJID");
            queryString.append(" AND V.VEHICLE2FLEET  =F.OBJID");
            queryString.append(" AND F.FLEET2BUS_ORG  = O.OBJID");
			queryString.append(" AND O.ORG_ID            =:customerId ORDER BY FAMILY");
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, strCustomer);
            List<Object> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlModels = new ArrayList<ElementVO>();
                for (final Iterator<Object> iter = lookupList.iterator(); iter.hasNext();) {
                    final String lookupRecord = (String) iter.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(lookupRecord);
                    objElementVO.setName(lookupRecord);
                    arlModels.add(objElementVO);

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_AUTHOR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_AUTHOR);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlModels;
    }

}