/**
 * ============================================================
 * File : ActivateDeActivateRuleDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 7, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdCmpLinkHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdFinrulHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdRulhisHVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ActivateDeActivateRuleDAOIntf;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings({ "serial", "unchecked" })
public class ActivateDeActivateRuleDAOImpl extends RMDCommonDAO implements ActivateDeActivateRuleDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ActivateDeActivateRuleDAOImpl.class);

    /*
     * (non-Javadoc)
     * @param String strRuleId, String strLanguage,String strUser
     * @return List<String>
     * @throws RMDDAOException
     */
    @Override
    public List<String> activateRule(final String strFinalRuleID, final String strLanguage, final String strUser)
            throws RMDDAOException {

        Session objHibernateSession = null;
        List<String> deActiveRuleList = new ArrayList<String>();
        try {
            String strOriginalId = null;
            objHibernateSession = getHibernateSession();
            /****** Checking child rule is active or not *********/
            checkChildRuleIsActive(strFinalRuleID, deActiveRuleList);
            if (deActiveRuleList.isEmpty()) {

                StringBuilder strQry = new StringBuilder();
                // fetching the original Id for the final Rule Id
                strQry.append("SELECT ORIGINAL_ID FROM GETS_TOOL_DPD_RULHIS WHERE RULHIS2FINRUL =:ruleId");
                Query fetchOrgId = objHibernateSession.createSQLQuery(strQry.toString());
                fetchOrgId.setParameter(RMDServiceConstants.RULE_ID, strFinalRuleID);
                List<Object> arlOriginalRuleId = fetchOrgId.list();
                if (null != arlOriginalRuleId && !arlOriginalRuleId.isEmpty()) {
                    for (Iterator<Object> iterator = arlOriginalRuleId.iterator(); iterator.hasNext();) {
                        Object obj = iterator.next();
                        strOriginalId = RMDCommonUtility.convertObjectToString(obj);
                    }
                    // update all rules as deactive with same original ID
                    // (means making all other rules in the rule tree as
                    // deactive)
                    if (null != strOriginalId && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strOriginalId)) {
                        StringBuilder strUpdateQry = new StringBuilder();
                        strUpdateQry.append(
                                "UPDATE GETS_TOOL_DPD_RULHIS SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY=:userName,ACTIVE = 0  ,ACTIVATED_BY = :userName,ACTIVATION_DATE = SYSDATE WHERE ORIGINAL_ID = :originalId AND ACTIVE = 1");
                        Query qryUpdate = objHibernateSession.createSQLQuery(strUpdateQry.toString());
                        qryUpdate.setParameter(RMDServiceConstants.USERNAME, strUser);
                        qryUpdate.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        int updateActive = qryUpdate.executeUpdate();
                        if (updateActive >= 0&&null!=strFinalRuleID&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strFinalRuleID)) {

                            StringBuilder strUpdate = new StringBuilder();
                            // After making all other rules which are having
                            // same originalId as deactive,below
                            // update is to make a particular ruleId as active
                            // status
                            strUpdate.append(
                                    "UPDATE GETS_TOOL_DPD_RULHIS SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY=:userName,ACTIVE = 1,ACTIVATED_BY = :userName,ACTIVATION_DATE = SYSDATE WHERE RULHIS2FINRUL=:ruleId");
                            Query qry = objHibernateSession.createSQLQuery(strUpdate.toString());
                            qry.setParameter(RMDServiceConstants.USERNAME, strUser);
                            qry.setParameter(RMDServiceConstants.RULE_ID, strFinalRuleID);
                            qry.executeUpdate();

                            Query queryUpdateFinRule1 = null;
                            Query queryUpdateFinRule2 = null;
                            try {

                                queryUpdateFinRule1 = objHibernateSession.createSQLQuery(
                                        RMDServiceConstants.QueryConstants.UPDATE_TOP_LEVEL_FINAL_RULE1.toString());
                                queryUpdateFinRule1.setParameter(RMDServiceConstants.FINRULE_OBJID, strFinalRuleID);
                                queryUpdateFinRule1.setParameter(RMDServiceConstants.RULE_ORIGINAL_OBJID,
                                        strOriginalId);

                                queryUpdateFinRule2 = objHibernateSession.createSQLQuery(
                                        RMDServiceConstants.QueryConstants.UPDATE_TOP_LEVEL_FINAL_RULE2.toString());
                                queryUpdateFinRule2.setParameter(RMDServiceConstants.FINRULE_OBJID, strFinalRuleID);
                                queryUpdateFinRule2.setParameter(RMDServiceConstants.RULE_ORIGINAL_OBJID,
                                        strOriginalId);

                                queryUpdateFinRule1.executeUpdate();
                                queryUpdateFinRule2.executeUpdate();
                            } catch (RMDDAOConnectionException ex) {
                                LOG.error("ERROR in updateOldFinRule method of saveFinalRule in RuleDAOImpl"
                                        + ex.getMessage());
                                String errorCode = RMDCommonUtility
                                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_TOP_LEVEL_FINRULES);
                                throw new RMDDAOException(errorCode, new String[] {},
                                        RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                                        RMDCommonConstants.FATAL_ERROR);
                            } catch (Exception e) {
                                LOG.error("ERROR in updateOldFinRule method of saveFinalRule in RuleDAOImpl"
                                        + e.getMessage());
                                String errorCode = RMDCommonUtility
                                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_TOP_LEVEL_FINRULES);
                                throw new RMDDAOException(errorCode, new String[] {},
                                        RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                                        RMDCommonConstants.MAJOR_ERROR);
                            } finally {
                                queryUpdateFinRule1 = null;
                                queryUpdateFinRule2 = null;
                                // releaseSession(objHibernateSession);
                            }

                        }

                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {}, strLanguage, ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);

        }
        return deActiveRuleList;
    }

    public List<String> checkChildRuleIsActive(final String strFinalRuleID, List<String> deActiveRuleList)
            throws RMDDAOException {
        if (!deActiveRuleList.isEmpty()) {
            return deActiveRuleList;
        }
        Session objHibernateSession = null;
        try {
            objHibernateSession = getHibernateSession();
            // query to fetch the rule on rules for the particular ruleId
            StringBuilder sbfFinRulLst = new StringBuilder();
            sbfFinRulLst.append("SELECT RULE1_2FINRUL, RULE2_2FINRUL");
            sbfFinRulLst.append(" FROM GETS_TOOL_DPD_CMPRUL");
            sbfFinRulLst.append(" WHERE (RULE1_2FINRUL>0 OR RULE2_2FINRUL    >0) AND CMPRUL2FINRUL=:finRuleId");
            Query finRuleQry = objHibernateSession.createSQLQuery(sbfFinRulLst.toString());
            finRuleQry.setParameter(RMDServiceConstants.FINAL_RULE_ID, strFinalRuleID);
            List<Object> arlFinRul = finRuleQry.list();
            if (null != arlFinRul && !arlFinRul.isEmpty()) {
                for (Iterator<Object> iterator = arlFinRul.iterator(); iterator.hasNext();) {
                    Object objROR[] = (Object[]) iterator.next();
                    for (Object obj : objROR) {
                        String strRORRuleId = RMDCommonUtility.convertObjectToString(obj);
                        StringBuilder sbfROR = new StringBuilder();
                        // checking whether the rule on rule is active or not
                        if (Integer.valueOf(strRORRuleId) > 0) {
                            sbfROR.append(
                                    "SELECT ACTIVE,RULHIS2FINRUL FROM GETS_TOOL_DPD_RULHIS WHERE RULHIS2FINRUL=:finRuleId AND ACTIVE=1");
                            Query rorQry = objHibernateSession.createSQLQuery(sbfROR.toString());
                            rorQry.setParameter(RMDServiceConstants.FINAL_RULE_ID, strRORRuleId);
                            List<Object> arlROR = rorQry.list();
                            if (arlROR.isEmpty()) {
                                deActiveRuleList.add(strRORRuleId);
                            }
                            // calling once again the checkChildRuleIsActive to
                            // check for the rule on rule for the child rule
                            checkChildRuleIsActive(strRORRuleId, deActiveRuleList);
                        }
                    }

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, e.getMessage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return deActiveRuleList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * deActivateRule (java.lang.String)
     */
    /*
     * This Method is used for DeActivate the rule and update the database
     * column(Active) as zero
     */
    @Override
    public List<String> deActivateRule(final String strFinalRuleID, final String strLanguage, final String strUser)
            throws RMDDAOException {

        Session objHibernateSession = null;
        List<String> arlRORId = new ArrayList<String>();

        try {

            objHibernateSession = getHibernateSession();
            StringBuilder sbfQry = new StringBuilder();
            sbfQry.append("SELECT DISTINCT CMPRUL.CMPRUL2FINRUL");
            sbfQry.append(" FROM GETS_TOOL_DPD_CMPRUL CMPRUL, GETS_TOOL_DPD_RULHIS RULHIS");
            sbfQry.append(
                    " WHERE ( RULE1_2FINRUL = :finRuleId  OR RULE2_2FINRUL = :finRuleId  ) AND CMPRUL.CMPRUL2FINRUL = RULHIS.RULHIS2FINRUL AND RULHIS.ACTIVE = 1");
            Query qryCheckROR = objHibernateSession.createSQLQuery(sbfQry.toString());
            qryCheckROR.setParameter(RMDServiceConstants.FINAL_RULE_ID, strFinalRuleID);
            List<Object> arlRORCheck = qryCheckROR.list();
            arlRORCheck = qryCheckROR.list();

            if (arlRORCheck.isEmpty()) {
                StringBuilder strUpdate = new StringBuilder();
                strUpdate.append(
                        "UPDATE GETS_TOOL_DPD_RULHIS SET LAST_UPDATED_DATE = SYSDATE,LAST_UPDATED_BY=:userName,ACTIVE = 0,ACTIVATED_BY = :userName,ACTIVATION_DATE = SYSDATE WHERE RULHIS2FINRUL=:ruleId");
                Query qry = objHibernateSession.createSQLQuery(strUpdate.toString());
                qry.setParameter(RMDServiceConstants.USERNAME, strUser);
                qry.setParameter(RMDServiceConstants.RULE_ID, strFinalRuleID);
                qry.executeUpdate();

            } else {
                for (Object ruleIdObj : arlRORCheck) {
                    String strRORRuleId = RMDCommonUtility.convertObjectToString(ruleIdObj);
                    arlRORId.add(strRORRuleId);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);

        }
        return arlRORId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * deActiveRuleWithOrgId(java.lang.String)
     */
    /*
     * This Method is used for DeActivate the rule and update the database
     * column(Active) as zero
     */
    @Override
    public String deActiveRuleWithOrgId(final String strOriginalId, final String strFinalRuleID,
            final String strLanguage) throws RMDDAOException {
        String strViewID = null;
        GetKmDpdRulhisHVO objDpdRulhisHVO = null;
        List arlList = null;
        Session objHibernateSession = null;
        int listCount = 0;
        try {
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetKmDpdRulhisHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.ORIGINAL_ID, Long.valueOf(strOriginalId)));
            arlList = criteria.list();
            listCount = arlList.size();
            if (arlList != null && listCount > 0) {
                for (int index = 0; index < listCount; index++) {
                    objDpdRulhisHVO = (GetKmDpdRulhisHVO) arlList.get(index);
                    objDpdRulhisHVO.setActive(Long.valueOf(RMDServiceConstants.INACTIVE_STATE));
                    objHibernateSession.update(objDpdRulhisHVO);
                    objHibernateSession.flush();
                }
                strViewID = RMDServiceConstants.ACTIVATERULE;
            }
            /********/
            Long strRuleID;
            List arlCmpLink = null;
            List fetchList = null;
            int cmpLinkCount = 0;
            GetKmDpdCmpLinkHVO objGetKmDpdCmpLinkHVO = null;
            GetKmDpdFinrulHVO objFinrulHVO = null;
            for (int index = 0; index < listCount; index++) {
                objDpdRulhisHVO = (GetKmDpdRulhisHVO) arlList.get(index);
                strRuleID = objDpdRulhisHVO.getGetKmDpdFinrul().getGetKmDpdFinrulSeqId();
                if (null != strRuleID) {
                    Criteria updateCriteria = objHibernateSession.createCriteria(GetKmDpdCmpLinkHVO.class)
                            .setFetchMode(RMDCommonConstants.GETKMDPDFIN_RUL, FetchMode.JOIN)
                            .createAlias(RMDCommonConstants.GETKMDPDFIN_RUL, RMDCommonConstants.FINRUL);
                    updateCriteria
                            .add(Restrictions.eq(RMDCommonConstants.FINRUL_GETKMDPDFIN_SEQID, Long.valueOf(strRuleID)));
                    arlCmpLink = updateCriteria.list();
                    cmpLinkCount = arlCmpLink.size();
                    if (arlCmpLink != null && !arlCmpLink.isEmpty()) {
                        Criteria fetchCriteria = objHibernateSession.createCriteria(GetKmDpdFinrulHVO.class);
                        fetchCriteria.add(Restrictions.eq(RMDCommonConstants.GET_KMDP_FIN_RULE_SEQID,
                                Long.valueOf(strFinalRuleID)));
                        fetchList = fetchCriteria.list();
                        if (fetchList != null && !fetchList.isEmpty()) {
                            objFinrulHVO = (GetKmDpdFinrulHVO) fetchList.get(0);
                        }
                        for (int indexCmpLink = 0; indexCmpLink < cmpLinkCount; indexCmpLink++) {
                            objGetKmDpdCmpLinkHVO = (GetKmDpdCmpLinkHVO) arlCmpLink.get(indexCmpLink);
                            objGetKmDpdCmpLinkHVO.setGetKmDpdFinrul(objFinrulHVO);
                            objHibernateSession.update(objGetKmDpdCmpLinkHVO);
                            objHibernateSession.flush();
                        }
                    }
                }
            }
            /********/
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ex.getMessage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, e.getMessage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            arlList = null;
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.
     * ActivateDeActivateRuleDAOIntf#getParentRule(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List getParentRule(final String strRuleId, final String strLanguage) throws RMDDAOException {

        Session objHibernateSession = null;
        List parentRoRList = new ArrayList();
        List finalRuleList = null;
        Criteria criteria = null;
        Criteria finalRuleCriteria = null;
        GetKmDpdRulhisHVO objGetKmDpdRulhisHVO = null;
        Long lnOriginalID;
        int iFinalRuleSize = 0;
        String strFinalRuleID;
        try {
            objHibernateSession = getHibernateSession();

            criteria = objHibernateSession.createCriteria(GetKmDpdRulhisHVO.class)
                    .setProjection(
                            Projections.projectionList().add(Projections.property(RMDCommonConstants.ORIGINALID)))
                    .createAlias(RMDCommonConstants.GETKMDPDFIN_RUL, RMDCommonConstants.KMDPDFINRUL);
            criteria.add(
                    Restrictions.eq(RMDCommonConstants.KMDPDFINRUL_GET_KMDPDFINRUL_SEQID, Long.valueOf(strRuleId)));
            lnOriginalID = (Long) criteria.uniqueResult();

            finalRuleCriteria = objHibernateSession.createCriteria(GetKmDpdRulhisHVO.class)
                    .setFetchMode(RMDCommonConstants.GETKMDPDFIN_RUL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETKMDPDFIN_RUL, RMDCommonConstants.KMDPDFINRUL);
            finalRuleCriteria.add(Restrictions.eq(RMDCommonConstants.ORIGINALID, Long.valueOf(lnOriginalID)));
            finalRuleList = finalRuleCriteria.list();

            if (null != finalRuleList && !finalRuleList.isEmpty()) {
                iFinalRuleSize = finalRuleList.size();
                for (int i = 0; i < iFinalRuleSize; i++) {
                    objGetKmDpdRulhisHVO = (GetKmDpdRulhisHVO) finalRuleList.get(i);
                    strFinalRuleID = objGetKmDpdRulhisHVO.getGetKmDpdFinrul().getGetKmDpdFinrulSeqId().toString();
                    checkParentRule(strFinalRuleID, parentRoRList, strLanguage);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {}, strLanguage, ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return parentRoRList;
    }

    /**
     * @Author:
     * @param lnLinkID
     * @param parentRoRList
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    public List checkParentRule(final String strRuleId, final List parentRoRList, final String strLanguage)
            throws RMDDAOException {
        /*
         * if (!parentRoRList.isEmpty()) { return parentRoRList; }
         */
        Session objHibernateSession = null;

        GetKmDpdFinrulHVO objGetKmDpdFinrulHVO = null;
        GetKmDpdCmpLinkHVO objCmpLinkHVO = null;
        List finalRuleList = null;
        List linkIDList = null;
        String strFinalRuleID = null;
        Long lnLinkID;
        int linkIDSize = 0;
        try {
            objHibernateSession = getHibernateSession();

            Criteria criteria = objHibernateSession.createCriteria(GetKmDpdCmpLinkHVO.class)
                    .createAlias(RMDCommonConstants.GETKMDPDFIN_RUL, RMDCommonConstants.KMDPDFINRUL);
            criteria.add(
                    Restrictions.eq(RMDCommonConstants.KMDPDFINRUL_GET_KMDPDFINRUL_SEQID, Long.valueOf(strRuleId)));
            linkIDList = criteria.list();
            if (null != linkIDList && !linkIDList.isEmpty()) {
                linkIDSize = linkIDList.size();
                for (int i = 0; i < linkIDSize; i++) {
                    objCmpLinkHVO = (GetKmDpdCmpLinkHVO) linkIDList.get(i);
                    lnLinkID = objCmpLinkHVO.getLinkId();

                    Criteria parentFinalRule = objHibernateSession.createCriteria(GetKmDpdFinrulHVO.class);
                    parentFinalRule
                            .add(Restrictions.eq(RMDCommonConstants.GET_KMDPDFINRUL_GET_KMDPDFINRUL_SEQID, lnLinkID));
                    finalRuleList = parentFinalRule.list();
                    if (!finalRuleList.isEmpty()) {
                        objGetKmDpdFinrulHVO = (GetKmDpdFinrulHVO) finalRuleList.get(0);
                        parentRoRList.add(objGetKmDpdFinrulHVO.getGetKmDpdFinrulSeqId());
                        strFinalRuleID = objGetKmDpdFinrulHVO.getGetKmDpdFinrulSeqId().toString();

                    }
                    checkParentRule(strFinalRuleID, parentRoRList, strLanguage);
                }
            }
        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {}, ex.getMessage(), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_ACTIVATERULE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, e.getMessage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            objCmpLinkHVO = null;
        }
        return parentRoRList;
    }

}