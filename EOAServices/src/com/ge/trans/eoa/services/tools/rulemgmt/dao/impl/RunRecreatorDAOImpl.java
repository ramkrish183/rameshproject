/**
 * ============================================================
 * File : RuleTracerDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
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

package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.valueobjects.ParicipatedRuleResultVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RunRecreatorDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTracerServiceResultVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCaseHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdFinrulHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmRunRecreatorHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetToolRunHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

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
public class RunRecreatorDAOImpl extends BaseDAO implements RunRecreatorDAOIntf {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RunRecreatorDAOImpl.class);

    @Override
    public List<String> getCaseIds(String strCaseId, String strLanguage) {

        List<String> arlCaseId = null;
        List<GetCmCaseHVO> arlGetCmCaseHVO;
        Session session = null;
        try {
            if (arlCaseId == null) {
                arlCaseId = new ArrayList<String>();
            }
            session = getHibernateSession();
            Criteria criteria = session.createCriteria(GetCmCaseHVO.class);
            criteria.addOrder(Order.asc(RMDCommonConstants.CASEID));
            if (strCaseId != null && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strCaseId)) {
                if (RMDServiceConstants.ENTER_STAR.equalsIgnoreCase(strCaseId.trim())) {

                } else {
                    criteria.add(Restrictions.ilike(RMDCommonConstants.CASEID, strCaseId, MatchMode.ANYWHERE));
                }
            }
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            arlGetCmCaseHVO = criteria.list();

            if (RMDCommonUtility.isCollectionNotEmpty(arlGetCmCaseHVO)) {

                for (GetCmCaseHVO objGetCmCaseHVO : arlGetCmCaseHVO) {

                    if (!RMDCommonUtility.isNullOrEmpty(objGetCmCaseHVO.getCaseId())) {
                        arlCaseId.add(objGetCmCaseHVO.getCaseId());

                    }

                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASEIDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASEIDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlGetCmCaseHVO = null;
        }
        return arlCaseId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf#
     * getRunIds (java.lang.String, java.lang.String)
     */
    @Override
    public List<String> getRunIds(String strCaseId, String strLanguage) {

        List<String> arlRunId = null;
        List<GetToolRunHVO> arlGetToolRunHVO = null;
        Session session = null;
        try {
            if (arlRunId == null) {
                arlRunId = new ArrayList<String>();
            }
            session = getHibernateSession();

            Criteria objCriteria = session.createCriteria(GetToolRunHVO.class)
                    .setResultTransformer(Criteria.ROOT_ENTITY)
                    .setFetchMode(RMDCommonConstants.GETCMCASE, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETCMCASE, RMDCommonConstants.CASE);
            objCriteria.addOrder(Order.asc(RMDCommonConstants.GETTOOLRUNSEQID));
            objCriteria.add(Restrictions.eq(RMDCommonConstants.CASE_CASEID, strCaseId));

            arlGetToolRunHVO = objCriteria.list();

            for (GetToolRunHVO objGetToolRunHVO : arlGetToolRunHVO) {
                if (objGetToolRunHVO.getGetToolRunSeqId() > 0) {
                    arlRunId.add(String.valueOf(objGetToolRunHVO.getGetToolRunSeqId()));
                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASEIDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASEIDS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRunId;
    }

    @Override
    public List<ParicipatedRuleResultVO> getParticipatedRules(RuleTracerServiceCriteriaVO objServiceCriteriaVO) {
        List<ParicipatedRuleResultVO> arlRuleResultVO = null;
        Session session = null;
        StringBuilder sbQry = new StringBuilder();
        String strRunId;
        List<Object[]> lsResult;
        int intColumns;
        int intCounter;
        ParicipatedRuleResultVO objResultVO;

        try {
            strRunId = objServiceCriteriaVO.getStrRunId();
            if (arlRuleResultVO == null) {
                arlRuleResultVO = new ArrayList<ParicipatedRuleResultVO>();
            }
            session = getHibernateSession();
            sbQry.append("  SELECT DISTINCT FR.GET_KM_DPD_FINRUL_SEQ_ID FINRULEID, ");
            sbQry.append("    RULE_TITLE, ");
            sbQry.append("    RULEDEF.RULE_TYPE, ");
            sbQry.append("    ACTIVE, ");
            sbQry.append("    DECODE(FINRULE,   NULL,   'N',   'Y') FIREDSTATUS ");
            sbQry.append("  FROM GET_KM.GET_KM_DPD_FINRUL FR, ");
            sbQry.append("    GET_ASST.GET_ASST_ASSET AST, ");
            sbQry.append("    GET_KM.GET_KM_DPD_RULEDEF RULEDEF, ");
            sbQry.append("    GET_KM.GET_KM_DPD_RULHIS RULHIS, ");
            sbQry.append("    GET_ASST.GET_ASST_MODEL MODEL, ");
            sbQry.append("    GET_TOOL.GET_TOOL_RUN RUN, ");
            sbQry.append("      (SELECT DISTINCT LINK_FINRUL FINRULE ");
            sbQry.append("     FROM GET_TOOL.GET_TOOL_TOOL_OUT TOOLOUT, ");
            sbQry.append("       GET_TOOL.GET_TOOL_RULE_FIRED RULEFIRED, ");
            sbQry.append("       GET_TOOL.GET_TOOL_RUN RUN, ");
            sbQry.append("       GET_TOOL.GET_TOOL_SUBRUN SUBRUN, ");
            sbQry.append("       GET_KM.GET_KM_DPD_RULEDEF RULEDEF ");
            sbQry.append("     WHERE GET_KM_DPD_RULEDEF_SEQ_ID = RULEFIRED.LINK_RULE_DEFN ");
            sbQry.append("     AND SUBRUN.LINK_RUN = GET_TOOL_RUN_SEQ_ID ");
            sbQry.append("     AND TOOLOUT.LINK_SUBRUN = GET_TOOL_SUBRUN_SEQ_ID ");
            sbQry.append("     AND RULEFIRED.LINK_TOOL_OUT = GET_TOOL_TOOL_OUT_SEQ_ID ");
            sbQry.append("     AND GET_TOOL_RUN_SEQ_ID = :runId ");
            sbQry.append("     UNION ");
            sbQry.append("     SELECT DISTINCT LINK_FINRUL ");
            sbQry.append("     FROM GET_TOOL.GET_TOOL_FAULT_FILTERED FILTERFIRED, ");
            sbQry.append("       GET_TOOL.GET_TOOL_RUN RUN, ");
            sbQry.append("       GET_TOOL.GET_TOOL_SUBRUN SUBRUN, ");
            sbQry.append("       GET_KM.GET_KM_DPD_RULEDEF RULEDEF ");
            sbQry.append("     WHERE FILTERFIRED.LINK_FAULT BETWEEN RUN_PROCESSEDMIN_OBJID ");
            sbQry.append("     AND RUN_PROCESSEDMAX_OBJID ");
            sbQry.append("     AND GET_KM_DPD_RULEDEF_SEQ_ID = LINK_RULE_DEFN ");
            sbQry.append("     AND SUBRUN.LINK_RUN = GET_TOOL_RUN_SEQ_ID ");
            sbQry.append("     AND FILTERFIRED.LINK_SUBRUN = GET_TOOL_SUBRUN_SEQ_ID ");
            sbQry.append("     AND GET_TOOL_RUN_SEQ_ID = :runId) ");
            sbQry.append("  FIREDRULES ");
            sbQry.append("  WHERE GET_TOOL_RUN_SEQ_ID = :runId ");
            sbQry.append("   AND AST.GET_ASST_ASSET_SEQ_ID = RUN.LINK_ASSET ");
            sbQry.append("   AND RULEDEF.LINK_FINRUL = FR.GET_KM_DPD_FINRUL_SEQ_ID ");
            sbQry.append("   AND RULHIS.LINK_FINRUL = FR.GET_KM_DPD_FINRUL_SEQ_ID ");
            sbQry.append("   AND RULHIS.ACTIVE = 1 ");
            sbQry.append("   AND AST.LINK_MODEL = GET_ASST_MODEL_SEQ_ID ");
            sbQry.append("   AND MODEL.FAMILY = FR.FAMILY ");
            sbQry.append("   AND FIREDRULES.FINRULE(+) = GET_KM_DPD_FINRUL_SEQ_ID ");

            Query ruleResultQry = session.createSQLQuery(sbQry.toString());
            ruleResultQry.setParameter(RMDCommonConstants.RUNID, strRunId);
            lsResult = ruleResultQry.list();

            for (Object[] objects : lsResult) {
                intColumns = objects.length;
                objResultVO = new ParicipatedRuleResultVO();
                intCounter = 0;
                while (intCounter < intColumns) {

                    if (intCounter == 0 && !RMDCommonUtility.checkNull(objects[intCounter])) {
                        objResultVO.setStrRuleId(String.valueOf(objects[intCounter]));
                    }

                    if (intCounter == 1 && !RMDCommonUtility.checkNull(objects[intCounter])) {
                        objResultVO.setStrRuleTitle(String.valueOf(objects[intCounter]));
                    }

                    if (intCounter == 2 && !RMDCommonUtility.checkNull(objects[intCounter])) {
                        objResultVO.setStrRuleType(String.valueOf(objects[intCounter]));
                    }
                    if (intCounter == 3 && !RMDCommonUtility.checkNull(objects[intCounter])) {
                        objResultVO.setStrActive(String.valueOf(objects[intCounter]));
                    }

                    if (intCounter == 4 && !RMDCommonUtility.checkNull(objects[intCounter])) {
                        objResultVO.setStrFired(String.valueOf(objects[intCounter]));
                    }
                    intCounter++;
                }
                arlRuleResultVO.add(objResultVO);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objServiceCriteriaVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objServiceCriteriaVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRuleResultVO;
    }

    @Override
    public int saveRunTrace(RuleTracerServiceCriteriaVO objRCriteriaVO) throws RMDDAOException {
        LOG.debug("Enter into saveRunTrace method in RunRecreatorDAOImpl");
        int iSeqID = RMDCommonConstants.INT_CONST;
        String strUserId = RMDCommonConstants.EMPTY_STRING;
        String strFinRuleId = RMDCommonConstants.EMPTY_STRING;
        String strRunId = RMDCommonConstants.EMPTY_STRING;
        String strCaseId = RMDCommonConstants.EMPTY_STRING;
        Session objHibernateSession = null;
        String strValidRuleQuery = RMDCommonConstants.EMPTY_STRING;
        int iValidRuleIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        try {
            strUserId = objRCriteriaVO.getStrUserName();
            LOG.debug("*******Inside RuleTracerDAOImpl saveRunTrace method**********" + strUserId);

            strFinRuleId = objRCriteriaVO.getStrFinRulId();
            strRunId = objRCriteriaVO.getStrRunId();
            strCaseId = objRCriteriaVO.getStrCaseId();

            objHibernateSession = getHibernateSession(strUserId);

            /* Check whether the rule id is valid Active Rule Id */
            strValidRuleQuery = "from GetKmDpdRulhisHVO ruleHist where ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId =:ruleId";
            hibQuery = objHibernateSession.createQuery(strValidRuleQuery);
            hibQuery.setString(RMDServiceConstants.RULE_ID, strFinRuleId);
            // denotes active rule
            ArrayList arlRuleId = (ArrayList) hibQuery.list();
            iValidRuleIdList = arlRuleId.size();
            LOG.debug("Size of RuleId --------->" + iValidRuleIdList);
            if (iValidRuleIdList > 0) {

                GetSysLookupHVO objGetSysLookup = null;
                objGetSysLookup = getParentLookupHVO(RMDServiceConstants.JDPAD_TRACER_STATUS,
                        RMDServiceConstants.RUNNING, objRCriteriaVO.getStrLanguage());
                LOG.debug("********objGetSysLookup status***************" + objGetSysLookup.getGetSysLookupSeqId());

                GetKmRunRecreatorHVO objGetKmRunRecreatorHVO = new GetKmRunRecreatorHVO();

                GetKmDpdFinrulHVO objFinrulHVO = new GetKmDpdFinrulHVO();
                Criteria criteria = objHibernateSession.createCriteria(GetKmDpdFinrulHVO.class);
                criteria.add(Restrictions.eq(RMDCommonConstants.GET_KMDP_FIN_RULE_SEQID, Long.valueOf(strFinRuleId)));
                objFinrulHVO = (GetKmDpdFinrulHVO) criteria.list().get(0);

                objGetKmRunRecreatorHVO.setGetKmDpdFinrul(objFinrulHVO);

                GetCmCaseHVO objGetCmCaseHVO = new GetCmCaseHVO();
                criteria = objHibernateSession.createCriteria(GetCmCaseHVO.class);
                criteria.add(Restrictions.eq(RMDServiceConstants.CASEID, strCaseId));
                objGetCmCaseHVO = (GetCmCaseHVO) criteria.list().get(0);
                objGetKmRunRecreatorHVO.setGetCmCase(objGetCmCaseHVO);
                GetToolRunHVO objGetRunHVO = new GetToolRunHVO();
                criteria = objHibernateSession.createCriteria(GetToolRunHVO.class);
                criteria.add(Restrictions.eq(RMDCommonConstants.GETTOOLRUNSEQID, Long.valueOf(strRunId)));
                objGetRunHVO = (GetToolRunHVO) criteria.list().get(0);
                objGetKmRunRecreatorHVO.setGetToolRun(objGetRunHVO);
                objGetKmRunRecreatorHVO.setStatus(objGetSysLookup.getGetSysLookupSeqId());
                objHibernateSession.save(objGetKmRunRecreatorHVO);
                objHibernateSession.flush();

                iSeqID = objGetKmRunRecreatorHVO.getGetKmRunRecreatorSeqId().intValue();
                LOG.debug("********JDPAD Tracer id in DAO***************" + iSeqID);
            } else {
                LOG.debug("InValid RuleId --------->");
                iSeqID = RMDCommonConstants.INT_CONST;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RUN_TRACE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRCriteriaVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RUN_TRACE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRCriteriaVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End into saveRunTrace method in RunRecreatorDAOImpl");
        return iSeqID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf#
     * fetchUser (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTracerSeachCriteriaServiceVO) This method is used to load the
     * distinct user drop down values from database
     */
    @Override
    public void fetchUser(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO) throws RMDDAOException {
        LOG.debug("Enter into fetchUser method in RunRecreatorDAOImpl");
        ArrayList arlUserName;
        ArrayList lstUserName;
        int iUserNameList = RMDCommonConstants.INT_CONST;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        Query hibQuery = null;
        Session objHibernateSession = null;
        try {
            objHibernateSession = getHibernateSession();
            lstUserName = new ArrayList();
            strQuery = "select distinct createdBy from GetKmTracerHVO";
            hibQuery = objHibernateSession.createQuery(strQuery);
            arlUserName = (ArrayList) hibQuery.list();
            iUserNameList = arlUserName.size();
            LOG.debug("Size of User  Name--------->" + iUserNameList);
            for (int index = 0; index < iUserNameList; index++) {
                lstUserName.add(arlUserName.get(index));
            }
            objRuleTracerSeachCriteriaServiceVO.setAlUserName(lstUserName);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_USER, e);
        } finally {
            releaseSession(objHibernateSession);
            arlUserName = null;
            lstUserName = null;
        }
        LOG.debug("End of fetchUser method in RunRecreatorDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf
     * #fetchTrackingRuleId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.RuleTracerSeachCriteriaServiceVO) This method is used to
     * fetch the rule id suggestion list from database based on keyword
     * provided.
     */
    @Override
    public void fetchTrackingRuleId(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into fetchTrackingRuleId method in RunRecreatorDAOImpl");
        ArrayList arlTrackingRuleId;
        ArrayList lstTrackingRuleId;
        String strRuleIdKey = RMDCommonConstants.EMPTY_STRING;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iRuleIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        Session objHibernateSession = null;
        try {
            objHibernateSession = getHibernateSession();
            lstTrackingRuleId = new ArrayList();
            strRuleIdKey = objRuleTracerSeachCriteriaServiceVO.getStrRuleID();
            LOG.debug("*******Inside RunRecreatorDAOImpl fetchTrackingRuleId**************" + strRuleIdKey);
            strQuery = "select distinct KmRunRecreator.getKmDpdFinrul.getKmDpdFinrulSeqId from GetKmRunRecreatorHVO KmRunRecreator where KmRunRecreator.getKmDpdFinrul.getKmDpdFinrulSeqId like :ruleIdKey";
            hibQuery = objHibernateSession.createQuery(strQuery);
            if ((RMDCommonConstants.STAR_SYMBOL).equalsIgnoreCase(strRuleIdKey.trim())) {
                strRuleIdKey = RMDCommonConstants.EMPTY_STRING;
                hibQuery.setString(RMDServiceConstants.RULE_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strRuleIdKey + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            } else {
                hibQuery.setString(RMDServiceConstants.RULE_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strRuleIdKey + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            }
            arlTrackingRuleId = (ArrayList) hibQuery.list();
            iRuleIdList = arlTrackingRuleId.size();
            LOG.debug("Size of RuleId --------->" + iRuleIdList);
            for (int index = 0; index < iRuleIdList; index++) {
                lstTrackingRuleId.add(arlTrackingRuleId.get(index));
            }
            objRuleTracerSeachCriteriaServiceVO.setAlRuleID(lstTrackingRuleId);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_TRACK_RULEID, e);
        } finally {
            releaseSession(objHibernateSession);
            arlTrackingRuleId = null;
            lstTrackingRuleId = null;
        }
        LOG.debug("End of fetchTrackingRuleId method in RunRecreatorDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf
     * # fetchTrackingId(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects .RuleTracerSeachCriteriaServiceVO) This method is used to
     * fetch the tracking id suggestion list from database based on keyword
     * provided.
     */
    @Override
    public void fetchTrackingId(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into fetchTrackingId method in RunRecreatorDAOImpl");
        ArrayList arlTrackingId;
        ArrayList lstTrackingId;
        String strRuleId = RMDCommonConstants.EMPTY_STRING;
        String strTrackingIdKey = RMDCommonConstants.EMPTY_STRING;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iTrackingIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        Session objHibernateSession = null;
        GetKmRunRecreatorHVO objGetKmRunRecreatorHVO = null;
        try {
            objHibernateSession = getHibernateSession();
            lstTrackingId = new ArrayList();
            strRuleId = objRuleTracerSeachCriteriaServiceVO.getStrRuleID();
            strTrackingIdKey = objRuleTracerSeachCriteriaServiceVO.getStrTraceID();
            LOG.debug("*******Inside RunRecreatorDAOImpl fetchRuleId strRuleId**************" + strRuleId);
            LOG.debug(
                    "*******Inside RunRecreatorDAOImpl fetchRuleId strTrackingIdKey**************" + strTrackingIdKey);
            strQuery = "from GetKmRunRecreatorHVO kmTracer where kmTracer.getKmRunRecreatorSeqId like :trackingIdKey";
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                strQuery = strQuery + " and kmTracer.getKmDpdFinrul.getKmDpdFinrulSeqId =:ruleId";
            }
            strQuery = strQuery + " order by kmTracer.getKmRunRecreatorSeqId asc";
            hibQuery = objHibernateSession.createQuery(strQuery);
            if ((RMDCommonConstants.STAR_SYMBOL).equalsIgnoreCase(strTrackingIdKey.trim())) {
                strTrackingIdKey = RMDCommonConstants.EMPTY_STRING;
                hibQuery.setString(RMDServiceConstants.TRACKLING_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strTrackingIdKey + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            } else {
                hibQuery.setString(RMDServiceConstants.TRACKLING_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strTrackingIdKey + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            }
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                hibQuery.setInteger(RMDServiceConstants.RULE_ID, RMDCommonUtility.getIntValue(strRuleId));
            }
            arlTrackingId = (ArrayList) hibQuery.list();
            iTrackingIdList = arlTrackingId.size();
            LOG.debug("Size of iTrackingIdList --------->" + iTrackingIdList);
            for (int index = 0; index < iTrackingIdList; index++) {
                objGetKmRunRecreatorHVO = (GetKmRunRecreatorHVO) arlTrackingId.get(index);

                lstTrackingId.add(objGetKmRunRecreatorHVO.getGetKmRunRecreatorSeqId());
            }
            objRuleTracerSeachCriteriaServiceVO.setAlTrackingID(lstTrackingId);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_TRACKINGID, e);
        } finally {
            releaseSession(objHibernateSession);
            objGetKmRunRecreatorHVO = null;
            arlTrackingId = null;
            lstTrackingId = null;
        }
        LOG.debug("Enter of fetchTrackingId method in RunRecreatorDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTracerDAOIntf#
     * searchTrackingResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTracerSeachCriteriaServiceVO)
     */
    @Override
    public List searchTrackingResult(RuleTracerSeachCriteriaServiceVO objRuleTracerSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into searchTrackingResult method in RunRecreatorDAOImpl");
        int iTrackingData = RMDCommonConstants.INT_CONST;
        Session objHibernateSession = null;
        ArrayList arlTrackingData = new ArrayList();
        List arlTrackingDataResult = new ArrayList();
        Codec ORACLE_CODEC = new OracleCodec();
        try {
            String strRuleId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTracerSeachCriteriaServiceVO.getStrRuleID());
            String strTrackingId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTracerSeachCriteriaServiceVO.getStrTraceID());
            String strUser = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTracerSeachCriteriaServiceVO.getStrUserName());
            String strLkbWeeks = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTracerSeachCriteriaServiceVO.getStrLookBackWeeks());
            String strCaseId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTracerSeachCriteriaServiceVO.getStrCaseID());
            objHibernateSession = getHibernateSession();
            StringBuilder sbTrackingDetailQuery = new StringBuilder();
            sbTrackingDetailQuery.append(
                    " SELECT DISTINCT TRACER.GET_KM_RUN_RECREATOR_SEQ_ID, TRACER.LINK_FINRUL, CASE.CASE_ID, CASE.TITLE, TRACER.CREATED_BY, ");
            sbTrackingDetailQuery.append(
                    " TO_CHAR(TRACER.CREATION_DATE,   'MM/DD/YYYY HH:MI:SS AM'),  TO_CHAR(TRACER.LAST_UPDATED_DATE,   'MM/DD/YYYY HH:MI:SS AM'), ");
            sbTrackingDetailQuery.append("LOOKUP.LOOKUP_VALUE ");
            sbTrackingDetailQuery.append(" FROM ");
            sbTrackingDetailQuery.append(
                    " GET_KM.GET_KM_RUN_RECREATOR TRACER, GET_CM.GET_CM_CASE CASE, GET_SYS.GET_SYS_LOOKUP LOOKUP, GET_KM.GET_KM_DPD_FINRUL FINRUL ");
            sbTrackingDetailQuery.append(" WHERE ");
            sbTrackingDetailQuery.append(" TRACER.STATUS = LOOKUP.GET_SYS_LOOKUP_SEQ_ID ");
            sbTrackingDetailQuery.append(" AND TRACER.LINK_CASE = CASE.GET_CM_CASE_SEQ_ID ");
            sbTrackingDetailQuery.append(" AND TRACER.LINK_FINRUL = FINRUL.GET_KM_DPD_FINRUL_SEQ_ID ");
            if (strTrackingId != null && !RMDCommonConstants.EMPTY_STRING.equals(strTrackingId)) {
                sbTrackingDetailQuery.append(" AND TRACER.GET_KM_RUN_RECREATOR_SEQ_ID = ");
                sbTrackingDetailQuery.append(strTrackingId);
            }
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                sbTrackingDetailQuery.append(" AND TRACER.LINK_FINRUL = ");
                sbTrackingDetailQuery.append(strRuleId);
            }
            if (strUser != null && !RMDCommonConstants.SELECT.equalsIgnoreCase(strUser)) {
                sbTrackingDetailQuery.append(" AND UPPER(TRACER.CREATED_BY) = '" + strUser.toUpperCase() + "'");
            }
            if (strCaseId != null && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strCaseId)) {
                sbTrackingDetailQuery.append(" AND CASE.CASE_ID = '" + strCaseId + "'");
            }
            if (strLkbWeeks != null && !RMDCommonConstants.SELECT.equalsIgnoreCase(strLkbWeeks)) {
                sbTrackingDetailQuery
                        .append(" AND TRACER.CREATION_DATE >= CAST((FROM_TZ(CAST(SYSDATE AS    TIMESTAMP),   'ASIA/CALCUTTA') AT TIME ZONE 'GMT') AS    DATE) - ("
                                + strLkbWeeks + "*7)");
            }
            sbTrackingDetailQuery.append(" ORDER BY tracer.get_km_run_recreator_seq_id ASC ");
            LOG.debug("******sbTrackingDetailQuery**********" + sbTrackingDetailQuery);
            Query dataQuery = objHibernateSession.createSQLQuery(sbTrackingDetailQuery.toString());
            arlTrackingData = (ArrayList) dataQuery.list();
            iTrackingData = arlTrackingData.size();
            LOG.debug("******arlFaultData size**************" + iTrackingData);
            RuleTracerServiceResultVO objRuleTracerServiceResultVO = null;
            if (iTrackingData > 0) {
                for (int index = 0; index < iTrackingData; index++) {
                    Object objTrackingDataArray[] = (Object[]) arlTrackingData.get(index);
                    String strTrackingData[] = new String[objTrackingDataArray.length];
                    for (int k = 0; k < strTrackingData.length; k++) {
                        if (null == objTrackingDataArray[k]
                                || RMDCommonConstants.EMPTY_STRING.equals(objTrackingDataArray[k])) {
                            strTrackingData[k] = null;
                        } else {
                            strTrackingData[k] = objTrackingDataArray[k].toString();
                        }
                    }
                    objRuleTracerServiceResultVO = new RuleTracerServiceResultVO();
                    String strTrackingSeqId = strTrackingData[0];
                    String strFaultRuleId = strTrackingData[1];
                    String strCaseID = strTrackingData[2];
                    String strCaseTitle = strTrackingData[3];
                    String strCreatedBy = strTrackingData[4];
                    String strCreationDate = strTrackingData[5];
                    String strLastUpdatedDate = strTrackingData[6];
                    String strStatus = strTrackingData[7];

                    if (RMDServiceConstants.RUNNING.equalsIgnoreCase(strStatus)) {
                        objRuleTracerServiceResultVO.setBTrackingText(RMDCommonConstants.TRUE);
                        objRuleTracerServiceResultVO.setBTrackingLink(RMDCommonConstants.FALSE);
                    }
                    if (RMDServiceConstants.COMPLETE.equalsIgnoreCase(strStatus)) {
                        objRuleTracerServiceResultVO.setBTrackingText(RMDCommonConstants.FALSE);
                        objRuleTracerServiceResultVO.setBTrackingLink(RMDCommonConstants.TRUE);
                    }
                    objRuleTracerServiceResultVO.setStrTrackingSeqId(strTrackingSeqId);
                    objRuleTracerServiceResultVO.setStrFaultRuleId(strFaultRuleId);
                    objRuleTracerServiceResultVO.setStrCaseID(strCaseID);
                    objRuleTracerServiceResultVO.setStrCaseTitle(strCaseTitle);
                    objRuleTracerServiceResultVO.setStrCreatedBy(strCreatedBy);
                    try {
                        objRuleTracerServiceResultVO.setStrCreationDate(RMDCommonUtility.updateAsGMT(RMDCommonUtility
                                .stringToDate(strCreationDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmssa)));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    try {
                        if (RMDServiceConstants.COMPLETE.equalsIgnoreCase(strStatus)) {
                            objRuleTracerServiceResultVO.setStrLastUpdatedDate(
                                    RMDCommonUtility.updateAsGMT(RMDCommonUtility.stringToDate(strLastUpdatedDate,
                                            RMDCommonConstants.DateConstants.MMddyyyyHHmmssa)));
                        } else {
                            objRuleTracerServiceResultVO.setStrLastUpdatedDate(null);
                        }
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objRuleTracerServiceResultVO.setStrStatus(strStatus);
                    arlTrackingDataResult.add(objRuleTracerServiceResultVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_SERCH_RESULT, e);
        } finally {
            releaseSession(objHibernateSession);
            arlTrackingData = null;
        }
        LOG.debug("End of searchTrackingResult method in RunRecreatorDAOImpl");
        return arlTrackingDataResult;
    }

    /**
     * @Author:
     * @param strLanguage
     * @return
     * @Description:
     */
    public List getHeaderGroup(String strLanguage) {
        Session hibernateSession = null;
        List arlHeaderGroup = new ArrayList();
        try {
            hibernateSession = getHibernateSession();
            String strQuery = "SELECT PARM_GROUP, STYLE, SUM(HEADER_WIDTH) HEADERWIDTH,COUNT(*) NO_COLUMNS , MIN(SORT_ORDER) SORTORDER FROM GET_CM.GET_CM_DS_PARMINFO WHERE RT_COL_AVAIL='Y' AND LANGUAGE = :LANGUAGE GROUP BY PARM_GROUP, STYLE ORDER BY SORTORDER";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.LANGUAGE, strLanguage);
            List headerGroupResult = query.list();
            int iheaderGroupResult = headerGroupResult.size();
            long headerWidthAbs = 0;
            if (RMDCommonUtility.isCollectionNotEmpty(headerGroupResult)) {
                for (int row = 0; row < iheaderGroupResult; row++) {
                    Object[] resultRow = (Object[]) headerGroupResult.get(row);
                    ToolDsParmGroupServiceVO parmGroupInfo = new ToolDsParmGroupServiceVO();
                    parmGroupInfo.setParamGroup((String) resultRow[0]);
                    parmGroupInfo.setStyle((String) resultRow[1]);
                    long groupHeaderWidth = ((BigDecimal) resultRow[2]).longValue();
                    long no_of_cols = ((BigDecimal) resultRow[3]).longValue();
                    parmGroupInfo.setColspan(no_of_cols + RMDCommonConstants.EMPTY_STRING);
                    parmGroupInfo.setGroupHeaderWidth(Long.valueOf(groupHeaderWidth) + RMDServiceConstants.PX);
                    if (row > 0) {
                        headerWidthAbs += groupHeaderWidth;
                    }
                    StringBuilder sbfDummyHeaderGroup = new StringBuilder();
                    for (int index = 0; index < groupHeaderWidth; index++) {
                        sbfDummyHeaderGroup.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                    parmGroupInfo.setDummyParamGroup(sbfDummyHeaderGroup.toString());
                    arlHeaderGroup.add(parmGroupInfo);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTracerDAOImpl getHeaderGroup()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlHeaderGroup;
    }

    public List getColToolTips(String strSelectedRuleId, String strSelectedRuleType, String strLanguage) {
        Session hibernateSession = getHibernateSession();
        List arlColToolTips = new ArrayList();
        try {
            // To get the Column name and the Tool Tip based on Rule
            StringBuilder sbColTooltipQuery = new StringBuilder();
            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals("Simple")) {
                sbColTooltipQuery.append(
                        " SELECT DISTINCT PROCESSEDFLT.LINK_FAULT || COL.DB_COLUMN_NAME || '^' ||  NVL2(SIMFEA.VALUE2,    COL.DB_COLUMN_NAME ||  SIMFN.FCN ||  SIMFEA.VALUE1 || ',' || SIMFEA.VALUE2,    COL.COLUMN_NAME ||  SIMFN.FCN ||  SIMFEA.VALUE1) TOOLTIP ");
                sbColTooltipQuery.append(
                        " FROM GET_KM.GET_KM_DPD_SIMRUL SIM,GET_KM.GET_KM_DPD_SIMFEA SIMFEA, GET_KM.GET_KM_DPD_SIMFCN SIMFN, GET_KM.GET_KM_DPD_COLNAME COL, ");
                sbColTooltipQuery.append(
                        " GET_KM.GET_KM_PROCESSED_SIMRUL PROCESSEDSIMRUL, GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT ");
                sbColTooltipQuery.append(" WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append(
                        " AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbColTooltipQuery.append(" AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");

                sbColTooltipQuery.append(" AND GET_KM_DPD_SIMRUL_SEQ_ID = ");
                sbColTooltipQuery.append(strSelectedRuleId);
            } else if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && strSelectedRuleType.equals(RMDCommonConstants.COMPLEX)) {
                // Complex Query here
                sbColTooltipQuery.append(" SELECT DISTINCT PROCESSEDFLT.LINK_FAULT || COL.DB_COLUMN_NAME || '^' || ");
                sbColTooltipQuery.append(
                        " NVL2(SIMFEA.VALUE2,    COL.DB_COLUMN_NAME || SIMFN.FCN ||  SIMFEA.VALUE1 || ',' || SIMFEA.VALUE2,    COL.COLUMN_NAME ||  SIMFN.FCN ||  SIMFEA.VALUE1) TOOLTIP ");
                sbColTooltipQuery.append(
                        " FROM GET_KM.GET_KM_DPD_SIMRUL SIM, GET_KM.GET_KM_DPD_SIMFEA SIMFEA, GET_KM.GET_KM_DPD_SIMFCN SIMFN, ");
                sbColTooltipQuery.append(
                        " GET_KM.GET_KM_DPD_COLNAME COL, GET_KM.GET_KM_PROCESSED_SIMRUL PROCESSEDSIMRUL, GET_KM.GET_KM_PROCESSED_FLT PROCESSEDFLT, ");
                sbColTooltipQuery.append("(SELECT DISTINCT REF_LINK_SIMRUL SIMRUL_ID ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbColTooltipQuery.append(" WHERE LINK_CMPRUL IN ");
                sbColTooltipQuery.append(" (SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = "
                        + strSelectedRuleId + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbColTooltipQuery.append(" UNION ");
                sbColTooltipQuery.append(" SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbColTooltipQuery.append(" (SELECT FINRUL.LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM ");
                sbColTooltipQuery.append(" (SELECT DISTINCT REF_LINK_FINRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL = "
                        + strSelectedRuleId + " CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL) ");
                sbColTooltipQuery.append(" REFFINRUL, ");
                sbColTooltipQuery.append(" GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbColTooltipQuery.append(" WHERE REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID) ");
                sbColTooltipQuery.append(" CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbColTooltipQuery.append(" UNION ");
                sbColTooltipQuery.append(" SELECT DISTINCT LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK START WITH LINK_CMPRUL IN ");
                sbColTooltipQuery.append(" (SELECT FINRUL.LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK CMPLINK, ");
                sbColTooltipQuery.append(" GET_KM.GET_KM_DPD_FINRUL FINRUL ");
                sbColTooltipQuery.append(" WHERE LINK_ID IN ");
                sbColTooltipQuery.append(" (SELECT LINK_ID ");
                sbColTooltipQuery.append(" FROM ");
                sbColTooltipQuery.append(" (SELECT DISTINCT LINK_ID, ");
                sbColTooltipQuery.append(" (SELECT LINK_CMPRUL ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_FINRUL ");
                sbColTooltipQuery.append(" WHERE GET_KM_DPD_FINRUL_SEQ_ID = REF_LINK_FINRUL) ");
                sbColTooltipQuery.append(" CHILDCMP ");
                sbColTooltipQuery.append(" FROM GET_KM.GET_KM_DPD_CMP_LINK ");
                sbColTooltipQuery.append(" WHERE REF_LINK_FINRUL IS NOT NULL) ");
                sbColTooltipQuery.append(
                        " START WITH LINK_ID =  " + strSelectedRuleId + " CONNECT BY PRIOR CHILDCMP = LINK_ID) ");
                sbColTooltipQuery.append(" AND REF_LINK_FINRUL = GET_KM_DPD_FINRUL_SEQ_ID ");
                sbColTooltipQuery.append(" AND REF_LINK_FINRUL IS NOT NULL) ");
                sbColTooltipQuery.append(" CONNECT BY PRIOR REF_LINK_CMPRUL = LINK_CMPRUL ");
                sbColTooltipQuery.append(" ) ");
                sbColTooltipQuery.append(" AND REF_LINK_SIMRUL IS NOT NULL) CHILDSIMPLE ");
                sbColTooltipQuery.append(" WHERE SIMFEA.LINK_SIMRUL = SIM.GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append(" AND SIMFEA.LINK_SIMFCN = SIMFN.GET_KM_DPD_SIMFCN_SEQ_ID ");
                sbColTooltipQuery.append(" AND COL.GET_KM_DPD_COLNAME_SEQ_ID = SIMFEA.LINK_COLNAME ");
                sbColTooltipQuery.append(" AND PROCESSEDSIMRUL.LINK_SIMRUL = GET_KM_DPD_SIMRUL_SEQ_ID ");
                sbColTooltipQuery.append(" AND PROCESSEDFLT.GET_KM_PROCESSED_FLT_SEQ_ID = LINK_PROCESSED_FLT ");
                sbColTooltipQuery.append(" AND  GET_KM_DPD_SIMRUL_SEQ_ID = CHILDSIMPLE.SIMRUL_ID ");
            }
            Query query = hibernateSession.createSQLQuery(sbColTooltipQuery.toString());
            arlColToolTips = query.list();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTracerDAOImpl getColToolTips()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlColToolTips;
    }

}
