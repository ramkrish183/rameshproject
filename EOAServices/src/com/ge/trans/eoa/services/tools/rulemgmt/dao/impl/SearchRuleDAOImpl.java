/**
 * ============================================================
 * File : SearchRuleDAOImpl.java
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.DpdFinrulSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRulMetricsSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRuldefSearchVO;
import com.ge.trans.eoa.services.common.valueobjects.DpdRulhisSearchVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdFinrulHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdRulhisHVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.SearchRuleIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceDefaultVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
@SuppressWarnings({ "serial", "unchecked" })
public class SearchRuleDAOImpl extends RMDCommonDAO implements SearchRuleIntf {

    public static final RMDLogger LOG = RMDLoggerHelper
            .getLogger(SearchRuleDAOImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOInf#
     * getSearchRuleCriteria()
     */
    /*
     * This Method is used for load the search rule dropdown values from
     * database
     */
    @Override
	public SearchRuleServiceDefaultVO getSearchRuleCriteria(String strLanguage)
            throws RMDDAOException {
        SearchRuleServiceDefaultVO objSearchRuleVO = null;
        List arlCreatedBy = null;
        List arlActivatedBy = null;
        Session objHibernateSession = null;
        try {
            objSearchRuleVO = new SearchRuleServiceDefaultVO();
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(
                    GetKmDpdFinrulHVO.class).setProjection(
                    Projections.distinct(Projections.projectionList().add(
                            Projections
                                    .property(RMDServiceConstants.CREATED_BY),
                            RMDServiceConstants.CREATED_BY)));
            criteria.add(Restrictions.eq(
                    RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            arlCreatedBy = criteria.list();
            objSearchRuleVO.setArlCreatedByList((ArrayList) arlCreatedBy);

            Criteria criteria1 = objHibernateSession
                    .createCriteria(GetKmDpdRulhisHVO.class);
            ProjectionList projList = Projections.projectionList();
            projList.add(Projections
                    .groupProperty(RMDServiceConstants.ACTIVATED_BY));
            criteria1.setProjection(projList);
            criteria1.add(Restrictions
                    .isNotNull(RMDServiceConstants.ACTIVATED_BY));
            criteria1.add(Restrictions.eq(
                    RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            arlActivatedBy = (ArrayList) criteria1.list();
            objSearchRuleVO.setArlActivatedByList((ArrayList) arlActivatedBy);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULESEARCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RULESEARCH);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return objSearchRuleVO;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * getSearchRuleResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .SearchRuleServiceCriteriaVO)
     */
    /*
     * This Method is used for fetch(search) the rule details for corresponding
     * input's from database
     */
    @Override
	public List getSearchRuleResult(
            SearchRuleServiceCriteriaVO objSearchCriteriaVO) {
        List arlSearchResults = null;
        Session session = null;
        boolean blnDefaultLoad = false;
        StringBuilder sqlQry = new StringBuilder(
                RMDCommonConstants.EMPTY_STRING);
        DpdRulhisSearchVO dpdRulhisSearchVO = null;
        DpdFinrulSearchVO dpdFinrulSearchVO = null;
        DpdRuldefSearchVO dpdRuldefSearchVO = null;
        DpdRulMetricsSearchVO dpdRulMetricsSearchVO = null;
        Query query = null;
        try {
            blnDefaultLoad = objSearchCriteriaVO.isBlnDefaultLoad();
            session = getHibernateSession();
            // Condition to bring records for default load or through Search.
            if (blnDefaultLoad) {
                sqlQry.append("SELECT DISTINCT FR.OBJID,RULHIS.ORIGINAL_ID,FR.FAMILY,FR.SUBSYSTEM,"
                        + "FR.RULE_TITLE,RULHIS.VERSION_NO,RULHIS.ACTIVE,RULHIS.LAST_UPDATED_BY,"
                        + " TO_CHAR(RULHIS.LAST_UPDATED_DATE,'MM/DD/YYYY hh24:mi:ss') lastupdateddate,RULHIS.COMPLETED,FR.FINRUL2SIMRUL,TO_CHAR(RULHIS.CREATION_DATE,'MM/DD/YYYY hh24:mi:ss'),RULHIS.CREATED_BY,RULDEF.RULE_TYPE,RULEMETRICS.FIRED_RULES_CNT,RULEMETRICS.SUCCESS_RULES_CNT,RULEMETRICS.ACCURATE_RULES_PCT,RULEMETRICS.MISS_RULES_CNT,RULHIS.LAST_UPDATED_DATE ");
                sqlQry.append(" FROM GETS_TOOL_DPD_FINRUL FR,GETS_TOOL_DPD_RULHIS RULHIS,GETS_TOOL_DPD_RULEDEF RULDEF, GETS_TOOLS.GETS_TOOL_RULE_METRIC RULEMETRICS");

                if (objSearchCriteriaVO.getStrCustomer() != null
                        && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                                .getStrCustomer())
                        && !RMDCommonConstants.SELECT
                                .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {
                    
                    sqlQry.append(",GETS_TOOL_DPD_CUST CUST,SA.TABLE_BUS_ORG TABLEBUS");

                }
                sqlQry.append(" WHERE RULHIS.RULHIS2FINRUL = FR.OBJID AND  RULDEF.RULEDEF2FINRUL=FR.OBJID ");
                
                if (objSearchCriteriaVO.getStrRuleType() != null
                        && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                                .getStrRuleType())
                        && !RMDCommonConstants.SELECT
                                .equalsIgnoreCase(objSearchCriteriaVO.getStrRuleType())) {

                    sqlQry.append(" AND RULDEF.RULE_TYPE IN (:RULETYPE)");

                }
                
                if (objSearchCriteriaVO.getStrCustomer() != null
                        && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                                .getStrCustomer())
                        && !RMDCommonConstants.SELECT
                                .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {

                    sqlQry.append(" AND CUST.CUST2RULEDEF    = RULDEF.OBJID AND CUST.CUST2BUSORG     = TABLEBUS.OBJID AND TABLEBUS.ORG_ID = :CUSTOMER");

                }
                //sqlQry.append(" AND RULHIS.ACTIVE = 1");
                sqlQry.append(" AND FR.OBJID=RULEMETRICS.FINRULE_ID(+) ");
                sqlQry.append(" AND RULHIS.LAST_UPDATED_DATE BETWEEN sysdate-"
                        + RMDCommonConstants.Numeric_30_DAYS
                        + " AND sysdate ORDER BY RULHIS.LAST_UPDATED_DATE DESC,RULHIS.ACTIVE DESC");

                query = session.createSQLQuery(sqlQry.toString());
                if (objSearchCriteriaVO.getStrRuleType() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrRuleType())) {
                    String[] tmpStrRuleType = objSearchCriteriaVO
                            .getStrRuleType().split(
                                    RMDCommonConstants.COMMMA_SEPARATOR);
                    query.setParameterList(RMDServiceConstants.RULETYPE,
                            tmpStrRuleType);

                }
                
                if (objSearchCriteriaVO.getStrCustomer() != null
                        && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                                .getStrCustomer())
                        && !RMDCommonConstants.SELECT
                                .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {

                    query.setParameter(RMDServiceConstants.CUSTOMER,
                            objSearchCriteriaVO.getStrCustomer());

                }
            } else {
                sqlQry = buildSqlQuery(objSearchCriteriaVO); // return sql query
                                                                // based on
                                                                // search
                                                                // criteria
                query = session.createSQLQuery(sqlQry.toString());

                if (objSearchCriteriaVO.getStrFamily() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrFamily())) {

                    String[] tmpStrFamily = objSearchCriteriaVO.getStrFamily()
                            .split(RMDCommonConstants.COMMMA_SEPARATOR);
                    query.setParameterList(
                            RMDCommonConstants.FAMILY_INITIAL_LOADER,
                            tmpStrFamily);

                }
                
                if (objSearchCriteriaVO.getStrCustomer() != null
                        && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                                .getStrCustomer())
                        && !RMDCommonConstants.SELECT
                                .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {

                    query.setParameter(RMDServiceConstants.CUSTOMER,
                            objSearchCriteriaVO.getStrCustomer());

                }
                
                if (objSearchCriteriaVO.getStrRuleType() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrRuleType())) {
                    String[] tmpStrRuleType = objSearchCriteriaVO
                            .getStrRuleType().split(
                                    RMDCommonConstants.COMMMA_SEPARATOR);
                    query.setParameterList(RMDServiceConstants.RULETYPE,
                            tmpStrRuleType);

                }
                if (objSearchCriteriaVO.getStrRuleTitle() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrRuleTitle())) {
                    query.setParameter(RMDServiceConstants.RULETITLE,
                            RMDServiceConstants.PERCENTAGE
									+ AppSecUtil.escapeLikeCharacters(AppSecUtil.stripNonValidXMLCharactersForKM(objSearchCriteriaVO.getStrRuleTitle()))
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (objSearchCriteriaVO.getStrRuleID() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrRuleID())) {
                    query.setParameter(RMDServiceConstants.RULEID,
                            objSearchCriteriaVO.getStrRuleID());
                }
                if (objSearchCriteriaVO.getStrSubSystem() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrSubSystem())) {
                    String[] tmpStrSubSystem = objSearchCriteriaVO
                            .getStrSubSystem().split(
                                    RMDCommonConstants.COMMMA_SEPARATOR);
                    query.setParameterList(RMDServiceConstants.SUBSYSTEM,
                            tmpStrSubSystem);

                } else if (objSearchCriteriaVO.getStrIsActive() != null
                        && !RMDCommonConstants.EITHER
                                .equalsIgnoreCase(objSearchCriteriaVO
                                        .getStrIsActive())
                        && !RMDCommonConstants.EMPTY_STRING
                                .equalsIgnoreCase(objSearchCriteriaVO
                                        .getStrIsActive())) {
                    query.setParameter(RMDServiceConstants.ISACTIVE,
                            objSearchCriteriaVO.getStrIsActive());
                }
                if (objSearchCriteriaVO.getStrFault() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrFault())) {
                    query.setParameter(RMDServiceConstants.FAULTCODE,
                            RMDServiceConstants.PERCENTAGE
                                    + objSearchCriteriaVO.getStrFault()
                                    + RMDServiceConstants.PERCENTAGE);
                }
                if (objSearchCriteriaVO.getStrCreatedBy() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO.getStrCreatedBy())) {
                    query.setParameter(RMDServiceConstants.CREATEDBY,
                            objSearchCriteriaVO.getStrCreatedBy());

                }

                if (objSearchCriteriaVO.getStrLastUpdatedBy() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO
                                        .getStrLastUpdatedBy())) {
                    query.setParameter(RMDServiceConstants.LastUpdateBy,
                            objSearchCriteriaVO.getStrLastUpdatedBy());
                }
                if (!RMDCommonUtility.isNullOrEmpty(objSearchCriteriaVO.getStrLastUpdatedFromDate())
                        && !RMDCommonUtility.isNullOrEmpty(objSearchCriteriaVO
                                .getStrLastUpdatedToDate())) {
                    query.setParameter(RMDServiceConstants.LastUpdatedFromDate,
                            objSearchCriteriaVO.getStrLastUpdatedFromDate());
                    query.setParameter(RMDServiceConstants.LastUpdateToDate,
                            objSearchCriteriaVO.getStrLastUpdatedToDate());
                }
                
                if (!RMDCommonUtility.isNullOrEmpty(objSearchCriteriaVO
                        .getStrCreatedByFromDate())
                        && !RMDCommonUtility.isNullOrEmpty(objSearchCriteriaVO
                                .getStrCreatedByToDate())) {
                    query.setParameter(RMDServiceConstants.createdFromDate,
                            objSearchCriteriaVO.getStrCreatedByFromDate());
                    query.setParameter(RMDServiceConstants.createdToDate,
                            objSearchCriteriaVO.getStrCreatedByToDate());
                }
                //Add Rx
                if (objSearchCriteriaVO.getStrRxTitle() != null
                        && !RMDCommonConstants.EMPTY_STRING
                                .equals(objSearchCriteriaVO
                                        .getStrRxTitle())) {
                    query.setParameter(RMDServiceConstants.RXTitle,RMDServiceConstants.PERCENTAGE
							+ AppSecUtil.escapeLikeCharacters(objSearchCriteriaVO.getStrRxTitle())
                            + RMDServiceConstants.PERCENTAGE);
                }

            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
            List<DpdRulhisSearchVO> arlChangedResults = new ArrayList<DpdRulhisSearchVO>();
            List<Object> arlRuleDetails = query.list();
            if (null != arlRuleDetails && !arlRuleDetails.isEmpty()) {

                for (Iterator<Object> iterator = arlRuleDetails.iterator(); iterator
                        .hasNext();) {

                    Object obj[] = (Object[]) iterator.next();

                    {
                        dpdFinrulSearchVO = new DpdFinrulSearchVO();
                        dpdRuldefSearchVO = new DpdRuldefSearchVO();
                        dpdRulMetricsSearchVO = new DpdRulMetricsSearchVO();
                        dpdRulhisSearchVO = new DpdRulhisSearchVO();

                        dpdRulhisSearchVO.setOriginalId(Long.parseLong(obj[1]
                                .toString()));
                        dpdRulhisSearchVO.setVersionNo(Long.parseLong(obj[5]
                                .toString()));

                        dpdRulhisSearchVO.setActive(Long.parseLong(obj[6]
                                .toString()));
                        if (obj[7] != null)
                            dpdRulhisSearchVO.setLastUpdatedBy(obj[7]
                                    .toString());
                        if (obj[8] != null) {
                            Date lastUpdateDate = dateFormat.parse(obj[8].toString());
                            dpdRulhisSearchVO.setLastUpdatedDate(lastUpdateDate);
                        }

						if (obj[9] != null)
							dpdRulhisSearchVO.setCompleted(Long
									.parseLong(obj[9].toString()));
						if (obj[8] != null) {							
							dpdFinrulSearchVO
									.setLastUpdatedDate(obj[8]
											.toString());

                        }
                        if (obj[1] != null)
                            dpdRulhisSearchVO.setOriginalId(Long
                                    .parseLong(obj[1].toString()));
                        if (obj[0] != null) {
                            dpdFinrulSearchVO.setKmDpdFinrulSeqId(Long
                                    .parseLong(obj[0].toString()));
                            dpdRulhisSearchVO.setLinkFinalRule(Long
                                    .parseLong(obj[0].toString()));
                        }
                        if (obj[8] != null) {
                            Date lastUpdateDate = dateFormat.parse(obj[8]
                                    .toString());
                            dpdRuldefSearchVO
                                    .setLastUpdatedDate(lastUpdateDate);

                        }
                        if (obj[2] != null)
                            dpdFinrulSearchVO.setFamily(obj[2].toString());
                        if (obj[3] != null)
                            dpdFinrulSearchVO.setSubsystem(obj[3].toString());

						if (obj[4] != null)
								dpdFinrulSearchVO.setRuleTitle(AppSecUtil.htmlEscaping(AppSecUtil.stripNonValidXMLCharactersForKM(obj[4].toString())));
						if (obj[11] != null) {
							Date createDate = dateFormat.parse(obj[11]
									.toString());
							dpdFinrulSearchVO
									.setCreationDate(obj[11]
											.toString());
							dpdRulhisSearchVO.setCreationDate(createDate);

                        }
                        //dpdFinrulSearchVO.setCreationDate((Date) obj[11]);
                        dpdFinrulSearchVO.setCreatedBy(obj[12].toString());

                        
                        dpdRulhisSearchVO.setCreatedBy(obj[12].toString());

                        dpdRulhisSearchVO
                                .setDpdRuldefSearchVO(dpdRuldefSearchVO);
                        if (obj[13] != null)
                            dpdRuldefSearchVO.setRuleType(obj[13].toString());
                        if(!RMDCommonUtility.checkNull(obj[14])){
                            dpdRulMetricsSearchVO
                            .setFiredRules(RMDCommonUtility.convertObjectToString(obj[14]));    
                        }
                        if(!RMDCommonUtility.checkNull(obj[15])){
                            dpdRulMetricsSearchVO
                            .setSuccessRules(RMDCommonUtility.convertObjectToString(obj[15]));  
                        }
                        if(!RMDCommonUtility.checkNull(obj[16])){
                            dpdRulMetricsSearchVO
                            .setAccuratePercent(RMDCommonUtility.convertObjectToString(obj[16]));   
                        }
                        if(!RMDCommonUtility.checkNull(obj[17])){
                            dpdRulMetricsSearchVO
                            .setMissRules(RMDCommonUtility.convertObjectToString(obj[17])); 
                        }                       

                        dpdRulhisSearchVO
                                .setDpdRulMetricsSearchVO(dpdRulMetricsSearchVO);
                        // Set final rule details into ruleHistory ....

                        dpdRulhisSearchVO.setDpdFinrulHVO(dpdFinrulSearchVO);

                        arlChangedResults.add(dpdRulhisSearchVO);

                    }

                }
            }
            Set<DpdRulhisSearchVO> allSet = new HashSet<DpdRulhisSearchVO>();
            allSet.addAll(arlChangedResults);
            arlSearchResults = (ArrayList) arlChangedResults;

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHRULERESULT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objSearchCriteriaVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHRULERESULT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            objSearchCriteriaVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {

            releaseSession(session);

        }
        return arlSearchResults;

    }

    // Build Sql query based on search criteria
    private StringBuilder buildSqlQuery(
            SearchRuleServiceCriteriaVO objSearchCriteriaVO) {

        StringBuilder sqlQry = new StringBuilder(
                RMDCommonConstants.EMPTY_STRING);
        sqlQry.append(" SELECT DISTINCT FR.OBJID,RULHIS.ORIGINAL_ID,FR.FAMILY,FR.SUBSYSTEM,"
                + " FR.RULE_TITLE,RULHIS.VERSION_NO,RULHIS.ACTIVE,RULHIS.LAST_UPDATED_BY,"
                + " TO_CHAR(RULHIS.LAST_UPDATED_DATE,'MM/DD/YYYY hh24:mi:ss') lastUpdatedDate,RULHIS.COMPLETED,FR.FINRUL2SIMRUL,TO_CHAR(RULHIS.CREATION_DATE,'MM/DD/YYYY hh24:mi:ss') ,RULHIS.CREATED_BY,RULDEF.RULE_TYPE,RULEMETRICS.FIRED_RULES_CNT,RULEMETRICS.SUCCESS_RULES_CNT,RULEMETRICS.ACCURATE_RULES_PCT,RULEMETRICS.MISS_RULES_CNT,RULHIS.LAST_UPDATED_DATE ");

        sqlQry.append(" FROM GETS_TOOL_DPD_FINRUL FR,GETS_TOOL_DPD_RULHIS RULHIS,GETS_TOOL_DPD_RULEDEF RULDEF, GETS_TOOLS.GETS_TOOL_RULE_METRIC RULEMETRICS");
        if(objSearchCriteriaVO.getStrRxTitle() != null &&
                !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO.getStrRxTitle())){
            sqlQry.append(",GETS_SD_RECOM RECOM");  
        }
        if (objSearchCriteriaVO.getStrCustomer() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrCustomer())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {
            
            sqlQry.append(",GETS_TOOL_DPD_CUST CUST,SA.TABLE_BUS_ORG TABLEBUS");

        }
        sqlQry.append(" WHERE RULHIS.RULHIS2FINRUL = FR.OBJID AND RULDEF.RULEDEF2FINRUL(+)=FR.OBJID");
        sqlQry.append(" AND FR.OBJID=RULEMETRICS.FINRULE_ID(+) ");
        if (objSearchCriteriaVO.getStrCustomer() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrCustomer())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrCustomer())) {

            sqlQry.append(" AND CUST.CUST2RULEDEF    = RULDEF.OBJID AND CUST.CUST2BUSORG     = TABLEBUS.OBJID AND TABLEBUS.ORG_ID = :CUSTOMER");

        }
        
        // For Title Search
        if (objSearchCriteriaVO.getStrRuleTitle() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrRuleTitle())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrRuleTitle())) {

			sqlQry.append(" AND TRIM(LOWER(FR.RULE_TITLE)) LIKE LOWER(:RULETITLE) ").append(RMDCommonConstants.ESCAPE_LIKE);

        }
        // For RuleId Search
        if (objSearchCriteriaVO.getStrRuleID() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrRuleID())) {

            sqlQry.append(" AND FR.OBJID =:RULEID ");

        }
        // For family Search
        if (objSearchCriteriaVO.getStrFamily() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrFamily())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrFamily())) {

            sqlQry.append(" AND FR.FAMILY IN(:FAMILY)");

        }
        // For subsystem Search
        if (objSearchCriteriaVO.getStrSubSystem() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrSubSystem())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrSubSystem())) {
            sqlQry.append(" AND TRIM(FR.SUBSYSTEM) IN(:SUBSYSTEM)");

        }
        
        // For fault code Search
        if (objSearchCriteriaVO.getStrFault() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrFault())
                && !RMDServiceConstants.ENTER_STAR
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrFault())) {

            sqlQry.append(" AND FR.OBJID IN ( SELECT  SIMRUL2FINRUL FROM GETS_TOOL_DPD_SIMRUL ");
            sqlQry.append(" WHERE LOWER(FAULT) LIKE LOWER (:FAULTCODE) ");
            sqlQry.append(" UNION ALL SELECT SIMRUL2FINRUL FROM GETS_TOOL_DPD_SIMRUL A, GETS_TOOLS.GETS_TOOL_DPD_SIM_MTM_FAULT B ");
            sqlQry.append(" WHERE LOWER(B.FAULT_CODE) LIKE LOWER (:FAULTCODE) AND A.OBJID = B.FAULT2SIMRUL)");
        } else if (objSearchCriteriaVO.getStrIsActive() != null
                && !RMDCommonConstants.EITHER
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrIsActive())
                && !RMDCommonConstants.EMPTY_STRING
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrIsActive())) {
            sqlQry.append(" AND RULHIS.ACTIVE = :ISACTIVE");
            if (objSearchCriteriaVO.getStrFault() != null
                    && !RMDCommonConstants.EMPTY_STRING
                            .equalsIgnoreCase(objSearchCriteriaVO.getStrFault())
                    && !RMDServiceConstants.ENTER_STAR
                            .equalsIgnoreCase(objSearchCriteriaVO.getStrFault())) {
                sqlQry.append(" AND RULHIS.ACTIVE = :ISACTIVE");
            }
        }
        // For RuleType Search
        if (objSearchCriteriaVO.getStrRuleType() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrRuleType())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrRuleType())) {

            sqlQry.append(" AND RULDEF.RULE_TYPE IN (:RULETYPE)");

        }

        // For CreatedBy Search
        if (objSearchCriteriaVO.getStrCreatedBy() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrCreatedBy())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrCreatedBy())) {

            sqlQry.append(" AND RULHIS.CREATED_BY=:CREATEDBY ");

        }

        // For Status Search
        if (objSearchCriteriaVO.getStrStatus() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrStatus())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrStatus())) {

            if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.ACTIVE)) {
                sqlQry.append(" AND RULHIS.ACTIVE = 1");
            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.DEACTIVE)) {
                sqlQry.append(" AND RULHIS.ACTIVE = 0 AND RULHIS.COMPLETED = 1");
            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.DRAFT)) {
                sqlQry.append(" AND RULHIS.ACTIVE = 0 AND RULHIS.COMPLETED = 0");

            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.ACTIVE_DEACTIVE)) {
                sqlQry.append(" AND (RULHIS.ACTIVE = 0 OR RULHIS.ACTIVE = 1) AND RULHIS.COMPLETED = 1");
            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.ACTIVE_DRAFT)) {
                sqlQry.append(" AND ((RULHIS.ACTIVE          = 0 AND RULHIS.COMPLETED        = 0) OR (RULHIS.ACTIVE           = 1 AND RULHIS.COMPLETED        = 1)) ");
            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.DEACTIVE_DRAFT)) {
                sqlQry.append(" AND RULHIS.ACTIVE = 0  AND (RULHIS.COMPLETED = 0 OR RULHIS.COMPLETED = 1) ");
            } else if (objSearchCriteriaVO.getStrStatus().equalsIgnoreCase(
                    RMDCommonConstants.ACTIVE_DEACTIVE_DRAFT)) {
                sqlQry.append(" AND (RULHIS.ACTIVE = 0 OR RULHIS.ACTIVE = 1) AND (RULHIS.COMPLETED = 0 OR RULHIS.COMPLETED = 1) ");
            }

        }
        // For Version Search

        if (objSearchCriteriaVO.getStrVersion() != null
                && !RMDCommonConstants.ALL.equals(objSearchCriteriaVO
                        .getStrVersion())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO.getStrVersion())) {
            if (objSearchCriteriaVO.getStrVersion().equalsIgnoreCase(
                    RMDCommonConstants.RECENT)) {

                sqlQry.append(" AND VERSION_NO =(SELECT MAX(VER_RUL_HIS.VERSION_NO) FROM ");
                sqlQry.append(" GETS_TOOL_DPD_RULHIS VER_RUL_HIS WHERE RULHIS.ORIGINAL_ID = VER_RUL_HIS.ORIGINAL_ID) ");

            }
        }

        // For LastUpdateBy Search
        if (objSearchCriteriaVO.getStrLastUpdatedBy() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrLastUpdatedBy())
                && !RMDCommonConstants.SELECT
                        .equalsIgnoreCase(objSearchCriteriaVO
                                .getStrLastUpdatedBy())) {

            sqlQry.append(" AND RULHIS.LAST_UPDATED_BY=:LastUpdateBy ");

        }
        // For LastUpdate From Date
        if (objSearchCriteriaVO.getStrLastUpdatedFromDate() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrLastUpdatedFromDate())
                && objSearchCriteriaVO.getStrLastUpdatedToDate() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrLastUpdatedToDate())) {

            sqlQry.append(" AND RULHIS.LAST_UPDATED_DATE between TO_DATE("
                    + " :lastUpdatedFromDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') AND  TO_DATE("
                    +" :lastUpdatedToDate "
                    + ",'MM/DD/YYYY hh24:mi:ss')");
            
        }

        // For Creation From Date
        if (objSearchCriteriaVO.getStrCreatedByFromDate() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrCreatedByFromDate())
                && objSearchCriteriaVO.getStrCreatedByToDate() != null
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO
                        .getStrCreatedByToDate())) {
            
        
            sqlQry.append(" AND RULHIS.CREATION_DATE BETWEEN TO_DATE("
                    +" :createdFromDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') AND  TO_DATE("
                    +" :createdToDate "
                    + ",'MM/DD/YYYY hh24:mi:ss') ");
        }

        // For Rx search
        if (null != objSearchCriteriaVO.getStrRxTitle()
                && !RMDCommonConstants.EMPTY_STRING.equals(objSearchCriteriaVO.
                        getStrRxTitle())) {
            
            sqlQry.append(" AND RULDEF.RULEDEF2RECOM=RECOM.OBJID " +
					"AND TRIM(LOWER(RECOM.TITLE)) LIKE LOWER(:rxTitle)").append(RMDCommonConstants.ESCAPE_LIKE);
        }
        sqlQry.append(" ORDER BY RULHIS.LAST_UPDATED_DATE DESC,RULHIS.ACTIVE DESC");
        return sqlQry;

    }
}
