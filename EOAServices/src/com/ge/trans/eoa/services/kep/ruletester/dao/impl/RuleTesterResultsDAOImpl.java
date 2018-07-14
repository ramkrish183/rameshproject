package com.ge.trans.eoa.services.kep.ruletester.dao.impl;

/**
 * ============================================================
 * File : RuleTesterResultsDAOImpl.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.services.kep.ruletester.dao.impl
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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.kep.common.constants.KEPCommonConstants;
import com.ge.trans.eoa.services.kep.common.constants.KEPServiceConstants;
import com.ge.trans.eoa.services.kep.knowledgeseeker.dao.intf.KnowledgeSeekerDAOIntf;
import com.ge.trans.eoa.services.kep.ruletester.dao.intf.RuleTesterResultsDAOIntf;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RTRequestVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RuleTesterVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterDetailsVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterResultVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterSummaryVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.kep.hibernate.valueobjects.LookupLanguageHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.RtAssetResultHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.RtResultDetailHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.RtResultSummaryHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.UserRequestClassificationHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.UserRequestHVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: 16-march-2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the DAO implementation class for Rule Tester
 *              Results Screen
 * @History :
 ******************************************************************************/
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RuleTesterResultsDAOImpl extends RMDCommonDAO implements RuleTesterResultsDAOIntf {

    /**
     * serial version Id
     */
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTesterResultsDAOImpl.class);
    @Autowired
    KnowledgeSeekerDAOIntf knowledgeSeekerDAOIntf;

    /**
     * @param strRuleTitle
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of RuleTitle for
     *               ajax populate
     */

    @Override
    public List<RuleTesterVO> getRuleTitles(final String strRuleTitle) throws RMDDAOException {
        Session session = null;
        final List<RuleTesterVO> arlRuleTitle = new ArrayList<RuleTesterVO>();
        List lstRuleTitle;
        try {

            session = getHibernateSession();
            final Criteria criteria = session.createCriteria(UserRequestHVO.class);

            // Addded to get the distinct Created By values
            criteria.setProjection(Projections.distinct(Projections.property(KEPCommonConstants.RULE_TITLE)));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            if (!RMDCommonUtility.isNullOrEmpty(strRuleTitle)) {
                criteria.add(Restrictions.like(KEPCommonConstants.RULE_TITLE, strRuleTitle, MatchMode.ANYWHERE));

            }
            lstRuleTitle = criteria.list();
            final int arlSize = lstRuleTitle.size();
            RuleTesterVO objRuleTesterVO;

            if (arlSize > 0) {
                for (int i = 0; i < arlSize; i++) {

                    objRuleTesterVO = new RuleTesterVO();
                    objRuleTesterVO.setRuleTitle(lstRuleTitle.get(i).toString());
                    arlRuleTitle.add(objRuleTesterVO);

                }

            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_RULE_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_RULE_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);

        }

        return arlRuleTitle;

    }

    /**
     * This is a common Lookup Method used to load the frequently accessed
     * values
     * 
     * @param strListName
     * @return list of ElementVO
     * @throws KEPDAOException
     */
    @Override
    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDDAOException {
        Session hibernateSession = null;
        List<LookupLanguageHVO> arlResult = null;
        List<ElementVO> arlLookUpDetails = new ArrayList<ElementVO>();
        try {
            hibernateSession = getHibernateSession();

            Criteria criteria = hibernateSession.createCriteria(LookupLanguageHVO.class)
                    .setFetchMode("lookup", FetchMode.JOIN).createAlias("lookup", RMDCommonConstants.LOOKUP)
                    .add(Restrictions.eq(RMDCommonConstants.LANGUAGE, strLanguage))
                    .add(Restrictions.eq("lookup.lookupName", strListName));

            arlResult = criteria.list();
            for (Iterator iterator = arlResult.iterator(); iterator.hasNext();) {
                LookupLanguageHVO lookupLanguageObj = (LookupLanguageHVO) iterator.next();
                ElementVO objElementVO = new ElementVO();
                objElementVO.setId(Integer.toString(lookupLanguageObj.getLookup().getLookupSeqId()));
                objElementVO.setName(lookupLanguageObj.getLookup().getLookupValue());
                arlLookUpDetails.add(objElementVO);

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlLookUpDetails;
    }

    /**
     * @param strCreatedBy
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of CreatedBy for
     *               ajax populate
     */

    @Override
    public List<RuleTesterVO> getCreators(final String strCreatedBy) throws RMDDAOException {
        Session hibernateSession = null;
        List<RuleTesterVO> arlTrackingDetails = new ArrayList<RuleTesterVO>();
        RuleTesterVO ruleTesterVO = null;
        try {
            hibernateSession = getHibernateSession();
            Query displayNameQuery = hibernateSession.createSQLQuery(
                    "SELECT DISTINCT CREATED_BY FROM GETS_KEP.USER_REQUEST where RUN_NAME IS NULL ORDER BY CREATED_BY");
            List<Object> selectList = displayNameQuery.list();

            if (selectList != null && !selectList.isEmpty()) {

                for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter.hasNext();) {
                    String createdByObj = (String) lookValueIter.next();
                    ruleTesterVO = new RuleTesterVO();
                    ruleTesterVO.setCreatedBy(RMDCommonUtility.convertObjectToString(createdByObj));
                    arlTrackingDetails.add(ruleTesterVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            final String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);

        }

        return arlTrackingDetails;

    }

    /**
     * @param RuleTesterVO
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the tracking Detail for
     *               values given in UI(filtered Options)
     */

    @Override
    public List<RuleTesterVO> getRuleTesterTrackings(final RuleTesterVO ruleTesterVO) throws RMDDAOException {
        Session session = null;
        final List<RuleTesterVO> arlTrackingDetails = new ArrayList<RuleTesterVO>();
        List<UserRequestHVO> arlUserRequestHVO = null;
        Date dtFromDate = null;
        boolean blnDefaultLoad = false;
        Date dtToDate = null;
        try {
            blnDefaultLoad = ruleTesterVO.isBlnDefaultLoad();
            session = getHibernateSession();
            final Criteria criteria = session.createCriteria(UserRequestHVO.class);
            if (!RMDCommonUtility.isNull(ruleTesterVO.getTrackingId())
                    && !ruleTesterVO.getTrackingId().equalsIgnoreCase(KEPCommonConstants.EMPTY_STRING)) {
                criteria.add(Restrictions.like("userRequestSeqId", Integer.parseInt(ruleTesterVO.getTrackingId())));
            }

            if (!RMDCommonUtility.isNull(ruleTesterVO.getRuleId())
                    && !ruleTesterVO.getRuleId().equalsIgnoreCase(KEPCommonConstants.EMPTY_STRING)) {

                criteria.add(Restrictions.eq(KEPCommonConstants.RULE_ID, Integer.valueOf(ruleTesterVO.getRuleId())));
            }
            if (!RMDCommonUtility.isNull(ruleTesterVO.getRuleTitle())
                    && !ruleTesterVO.getRuleTitle().equalsIgnoreCase(KEPCommonConstants.EMPTY_STRING)) {

				criteria.add(new LikeExpressionWithEscapeCharacters(KEPCommonConstants.RULE_TITLE, ruleTesterVO
						.getRuleTitle(), MatchMode.ANYWHERE));

            }
            if (!RMDCommonUtility.isNull(ruleTesterVO.getRuleType())
                    && !ruleTesterVO.getRuleType().equalsIgnoreCase(KEPCommonConstants.SELECT)) {

                criteria.add(Restrictions.eq(KEPCommonConstants.RULE_TYPE, ruleTesterVO.getRuleType()));
            }
            if (!RMDCommonUtility.isNull(ruleTesterVO.getCreatedBy())
                    && !ruleTesterVO.getCreatedBy().equalsIgnoreCase(KEPCommonConstants.SELECT)) {
                criteria.add(Restrictions.eq(KEPCommonConstants.CREATED_BY, ruleTesterVO.getCreatedBy()));
            }

            if (null != ruleTesterVO.getFromDate() && null != ruleTesterVO.getDtTo()) {

                dtFromDate = ruleTesterVO.getFromDate();
                dtToDate = ruleTesterVO.getDtTo();
                LOG.debug("From Date:" + dtFromDate);
                LOG.debug("To Date:" + dtToDate);
                criteria.add(Restrictions.between(KEPCommonConstants.START_RUN_TSTAMP, dtFromDate, dtToDate));
            } else if (null != ruleTesterVO.getFromDate()) {

                Date destDate = new Date();
                try {
                    String strDate = RMDCommonUtility.formatDate(destDate,
                            KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                    destDate = RMDCommonUtility.stringToDate(strDate, KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                } catch (Exception e) {
                    LOG.error("Exception while parsing date: ", e);
                }
                criteria.add(Restrictions.between(KEPCommonConstants.START_RUN_TSTAMP, dtFromDate, destDate));
            }

            if (!RMDCommonUtility.isNull(ruleTesterVO.getStatus())
                    && !ruleTesterVO.getStatus().equalsIgnoreCase(KEPCommonConstants.SELECT)) {

                criteria.add(Restrictions.eq(RMDCommonConstants.STATUS, ruleTesterVO.getStatus()));
            }

            if (blnDefaultLoad) {

                Date dest = RMDCommonUtility.getGMTDateTime(KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);

                Date src = RMDCommonUtility.substractOrAddFromDate(dest, null, Calendar.DATE,
                        -KEPCommonConstants.Numeric_30_DAYS);
                criteria.add(Restrictions.between(KEPCommonConstants.CREATION_DATE, src, dest));

            }
            criteria.add(Restrictions.isNull(KEPCommonConstants.RUN_NAME));
            criteria.addOrder(Order.desc("userRequestSeqId"));
            arlUserRequestHVO = criteria.list();
            RuleTesterVO objRuleTesterVO;
            if (RMDCommonUtility.isCollectionNotEmpty(arlUserRequestHVO)) {
                for (UserRequestHVO userRequestHVO : arlUserRequestHVO) {
                    objRuleTesterVO = new RuleTesterVO();
                    objRuleTesterVO.setTrackingId(Integer.toString(userRequestHVO.getUserRequestSeqId()));
                    objRuleTesterVO.setRuleId(Integer.toString(userRequestHVO.getRuleId()));
                    objRuleTesterVO.setRuleTitle(AppSecUtil.htmlEscaping(userRequestHVO.getRuleTitle()));
                    objRuleTesterVO.setRuleType(userRequestHVO.getRuleType());
                    objRuleTesterVO.setStatus(userRequestHVO.getStatus());
                    objRuleTesterVO.setFromDate(userRequestHVO.getRunStartDate());
                    objRuleTesterVO.setDtTo(userRequestHVO.getRunEndDate());
                    objRuleTesterVO.setCreatedBy(userRequestHVO.getCreatedBy());
                    arlTrackingDetails.add(objRuleTesterVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKING_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKING_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);

        }

        return arlTrackingDetails;

    }

    /**
     * @param strTrackingId
     * @return List<TesterDetailsVO>
     * @Description: This method is used for getting the tester criteria details
     *               for the particular TrackingId
     */
    @Override
    public List<TesterDetailsVO> getTrackingDetails(final String strTrackingId) {
        List<TesterDetailsVO> lstTesterDetailsVO = null;
        Session hibernateSession = null;
        List<UserRequestHVO> arlTrackingResult = null;
        TesterDetailsVO objTesterDetailsVO = null;
        TesterResultVO objTesterResultVO = null;
        boolean boolCheckClassification = false;
        TesterSummaryVO objTesterSummaryVO = new TesterSummaryVO();
        Set setRtResultSummary = new HashSet();
        Set setUserRequestClassification = new HashSet();
        Set setRtAssetResult = new HashSet();
        Set setRtResultDetails = new HashSet();
        StringBuilder objStringBuilder = new StringBuilder();
        List<ElementVO> arlRx = new ArrayList<ElementVO>();
        try {
            hibernateSession = getHibernateSession();

            if (!RMDCommonUtility.isNullOrEmpty(strTrackingId)) {
                final Criteria criteria = hibernateSession.createCriteria(UserRequestHVO.class);
                criteria.add(Restrictions.eq("userRequestSeqId", Integer.valueOf(strTrackingId)));
                lstTesterDetailsVO = new ArrayList<TesterDetailsVO>();
                arlTrackingResult = criteria.list();
                final Map<String, TesterResultVO> mapPatternDetails = getCustrnhDetails(strTrackingId);
                objTesterDetailsVO = new TesterDetailsVO();
                if (RMDCommonUtility.isCollectionNotEmpty(arlTrackingResult)) {
                    for (UserRequestHVO objUserRequestHVO : arlTrackingResult) {
                        objTesterDetailsVO = new TesterDetailsVO();
                        objTesterDetailsVO.setCustomer(objUserRequestHVO.getCustomerId());
                        objTesterDetailsVO.setFleet(objUserRequestHVO.getFleetId());
                        objTesterDetailsVO.setAssetNumber(objUserRequestHVO.getAssetId());
                        objTesterDetailsVO.setModel(objUserRequestHVO.getModelId());
                        objTesterDetailsVO.setConfig(objUserRequestHVO.getConfigId());
                        objTesterDetailsVO.setRoadNumber(objUserRequestHVO.getRoadNumber());

                        if (null != objUserRequestHVO.getSegmentWindow()) {
                            objTesterDetailsVO.setSegmentWindow(String.valueOf(objUserRequestHVO.getSegmentWindow()));
                        }

                        if (null != objUserRequestHVO.getClassificationWindow()) {
                            objTesterDetailsVO.setClassificationWindow(
                                    String.valueOf(objUserRequestHVO.getClassificationWindow()));
                            boolCheckClassification = true;
                            objTesterDetailsVO.setHasClassification(boolCheckClassification);
                        }

                        String strStartDate = RMDCommonUtility.convertToString(objUserRequestHVO.getStartDate(),
                                KEPCommonConstants.DateConstants.MMddyyyyHHmmss);

                        String strEndDate = RMDCommonUtility.convertToString(objUserRequestHVO.getEndDate(),
                                KEPCommonConstants.DateConstants.MMddyyyyHHmmss);

                        objTesterDetailsVO.setRuleTesterStartDate(strStartDate);
                        objTesterDetailsVO.setRuleTesterEndDate(strEndDate);
                        objTesterDetailsVO.setOptions(String.valueOf(objUserRequestHVO.getDataWindow()));
                        objTesterDetailsVO.setRuleId(String.valueOf(objUserRequestHVO.getRuleId()));
                        // iterating the rt classification details
                        setUserRequestClassification = objUserRequestHVO.getUserRequestClassification();
                        if (RMDCommonUtility.isCollectionNotEmpty(setUserRequestClassification)) {
                            for (Iterator iterator4 = setUserRequestClassification.iterator(); iterator4.hasNext();) {
                                UserRequestClassificationHVO objUserRequestClassificationHVO = (UserRequestClassificationHVO) iterator4
                                        .next();
                                String strClassification = objUserRequestClassificationHVO.getName();
                                objTesterDetailsVO.setClassification(strClassification);
                                if (KEPCommonConstants.CCDB.equalsIgnoreCase(strClassification)) {
                                    objTesterDetailsVO.setRootCause(objUserRequestClassificationHVO.getRootCause());
                                    objTesterDetailsVO.setSymptom(objUserRequestClassificationHVO.getSymptom());
                                } else if (KEPCommonConstants.RXS.equalsIgnoreCase(strClassification)) {

                                    arlRx = knowledgeSeekerDAOIntf.getRx(objTesterDetailsVO.getModel(),
                                            objTesterDetailsVO.getCustomer(), "All", strStartDate, strEndDate,
                                            objUserRequestClassificationHVO.getRx());
                                    if (!RMDCommonUtility.checkNull(arlRx)) {
                                        for (ElementVO objElementVO : arlRx) {
                                            if (objStringBuilder != null && objStringBuilder.length() != 0) {
                                                objStringBuilder.append(RMDCommonConstants.COMMMA_SEPARATOR);
                                            }
                                            objStringBuilder.append(objElementVO.getName());
                                        }
                                    }
                                    objTesterDetailsVO.setRxDesc(objStringBuilder.toString());
                                }
                            }
                        }
                        // Iteration end
                        setRtResultSummary = objUserRequestHVO.getRtResultSummaries();
                        if (RMDCommonUtility.isCollectionNotEmpty(setRtResultSummary)) {
                            for (Iterator iterator = setRtResultSummary.iterator(); iterator.hasNext();) {
                                RtResultSummaryHVO objRtResultSummaryHVO = (RtResultSummaryHVO) iterator.next();
                                /*
                                 * rtResultSummarySeqId = objRtResultSummaryHVO
                                 * .getRtResultSummarySeqId();
                                 */
                                objTesterSummaryVO.setTotAssetsAnalysed(objRtResultSummaryHVO.getAssetsAnalyzed());
                                if (boolCheckClassification) {

                                    objTesterSummaryVO.setClassifications(objRtResultSummaryHVO.getClassification());
                                }
                                objTesterSummaryVO
                                        .setNormalDataSets(String.valueOf(objRtResultSummaryHVO.getNormalDataset()));
                                objTesterSummaryVO.setNoOfAssts(objRtResultSummaryHVO.getAssetsFound());
                                objTesterDetailsVO.getTesterSummary().add(objTesterSummaryVO);
                                setRtResultDetails = objRtResultSummaryHVO.getRtResultDetails();
                                for (Iterator iterator2 = setRtResultDetails.iterator(); iterator2.hasNext();) {
                                    RtResultDetailHVO rtResultDetailHVO = (RtResultDetailHVO) iterator2.next();
                                    objTesterDetailsVO.setCoverage(rtResultDetailHVO.getCoverageCnt());
                                    objTesterDetailsVO.setCoveragePercent(
                                            RMDCommonUtility.getRoundedString(rtResultDetailHVO.getCoveragePct()));
                                    objTesterDetailsVO.setFalseAlarmPercent(
                                            RMDCommonUtility.getRoundedString(rtResultDetailHVO.getFalseAlarmPct()));
                                    objTesterDetailsVO.setFalseAlarms(rtResultDetailHVO.getFalseAlarmCnt());
                                    setRtAssetResult = rtResultDetailHVO.getRtAssetResult();
                                    for (Iterator iterator3 = setRtAssetResult.iterator(); iterator3.hasNext();) {
                                        RtAssetResultHVO objRtAssetResultHVO = (RtAssetResultHVO) iterator3.next();
                                        objTesterResultVO = new TesterResultVO();
                                        objTesterResultVO.setAssetNo(String.valueOf(objRtAssetResultHVO.getAsset()));
                                        // Populate classification only when
                                        // classification window have values
                                        if (boolCheckClassification) {
                                            objTesterResultVO
                                                    .setClassification(objRtAssetResultHVO.getClassification());
                                        }
                                        if (mapPatternDetails != null && !mapPatternDetails.isEmpty()) {
                                            if (mapPatternDetails.containsKey(objTesterResultVO.getAssetNo())) {
                                                objTesterResultVO.setCustomer(mapPatternDetails
                                                        .get(objTesterResultVO.getAssetNo()).getCustomer());
                                                objTesterResultVO.setAssetHeader(mapPatternDetails
                                                        .get(objTesterResultVO.getAssetNo()).getAssetHeader());
                                                objTesterResultVO.setVehicleNo(mapPatternDetails
                                                        .get(objTesterResultVO.getAssetNo()).getVehicleNo());
                                            }
                                        }
                                        objTesterResultVO.setHitOrMiss(objRtAssetResultHVO.getHitormiss());
                                        objTesterResultVO.setNoOfFired(objRtAssetResultHVO.getNoOfFiring());
                                        objTesterResultVO.setSegmentId(objRtAssetResultHVO.getSegmentDate());
                                        objTesterDetailsVO.getTesterResult().add(objTesterResultVO);
                                    }
                                }

                            }
                        }
                    }
                }
                lstTesterDetailsVO.add(objTesterDetailsVO);
            }
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_SUMMARY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Exception in GetTrackingDetails RuletesterDAO " + e.getMessage());
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_SUMMARY_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);

        }
        return lstTesterDetailsVO;
    }

    /**
     * @param strPattern
     *            ,strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the PatternDetails for
     *               particular TrackingId
     */

    @Override
    public List<TesterResultVO> getPatternDetails(final String strPatternCategory, final String strTrackingId)
            throws RMDDAOException {
        final List<TesterResultVO> arlPatternDetails = new ArrayList<TesterResultVO>();
        TesterResultVO objTesterResultVO;
        Session session = null;
        StringBuilder objStringBuilder = new StringBuilder();
        try {
            objStringBuilder.append(
                    " SELECT DISTINCT USERREQUEST.USER_REQUEST_ID,ASSETRESULT.ASSET_ID,ASSETRESULT.CLASSIFICATION_TXT,CUSTRNHV.VEHICLE_HDR,ASSETRESULT.FIRING_CNT,ASSETRESULT.SEGMENT_DATE, ASSETRESULT.HIT_MISS_CD,CUSTRNHV.VEHICLE_NO,CUSTRNHV.VEHICLE_HDR FROM GETS_KEP.RT_ASSET_RESULT ASSETRESULT, GETS_KEP.RT_RESULT_DETAIL RESULTDETAIL,");
            objStringBuilder.append(
                    " GETS_KEP.RT_RESULT_SUMMARY RESULTSUMMARY,GETS_KEP.USER_REQUEST USERREQUEST, GETS_RMD_CUST_RNH_RN_V CUSTRNHV");
            objStringBuilder.append(" WHERE ASSETRESULT.RT_RESULT_DETAIL_ID=RESULTDETAIL.RT_RESULT_DETAIL_ID AND ");
            objStringBuilder.append(" RESULTDETAIL.RT_RESULT_SUMMARY_ID=RESULTSUMMARY.RT_RESULT_SUMMARY_ID AND");
            objStringBuilder.append(" RESULTSUMMARY.USER_REQUEST_ID=USERREQUEST.USER_REQUEST_ID AND ");
            objStringBuilder.append(" CUSTRNHV.VEHICLE_OBJID=ASSETRESULT.ASSET_ID AND");
            objStringBuilder.append(" USERREQUEST.USER_REQUEST_ID = :userRequestId  ");
            if (!RMDCommonUtility.isNullOrEmpty(strPatternCategory)) {
                if (!strPatternCategory.equalsIgnoreCase(KEPCommonConstants.ALL)) {
                    objStringBuilder.append(" AND ASSETRESULT.HIT_MISS_CD =:hitormiss ");
                }
            }
            // objStringBuilder
            session = getHibernateSession();
            Query patternDetailsQuery = session.createSQLQuery(objStringBuilder.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strPatternCategory)) {
                if (strPatternCategory.equalsIgnoreCase(KEPCommonConstants.HIT)) {
                    patternDetailsQuery.setParameter(KEPCommonConstants.HITTORMISS, KEPCommonConstants.H_CHAR);
                } else if (strPatternCategory.equalsIgnoreCase(KEPCommonConstants.MISS)) {
                    patternDetailsQuery.setParameter(KEPCommonConstants.HITTORMISS, KEPCommonConstants.M_CHAR);
                }
            }
            patternDetailsQuery.setParameter(KEPCommonConstants.USER_REQUEST_ID, Integer.parseInt(strTrackingId));
            List<Object> lstDetails = patternDetailsQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lstDetails)) {
                for (Iterator iterator = lstDetails.iterator(); iterator.hasNext();) {
                    Object[] objPatternDetails = (Object[]) iterator.next();
                    objTesterResultVO = new TesterResultVO();
                    objTesterResultVO.setAssetNo(objPatternDetails[1].toString());
                    objTesterResultVO.setClassification(objPatternDetails[2].toString());
                    objTesterResultVO.setCustomer(objPatternDetails[3].toString());
                    objTesterResultVO.setNoOfFired(RMDCommonUtility.convertObjectToInt(objPatternDetails[4]));
                    objTesterResultVO.setSegmentId(objPatternDetails[5].toString());
                    objTesterResultVO.setHitOrMiss(objPatternDetails[6].toString());
                    objTesterResultVO.setVehicleNo(objPatternDetails[7].toString());
                    objTesterResultVO.setAssetHeader(objPatternDetails[8].toString());
                    arlPatternDetails.add(objTesterResultVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_PATTERN_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_PATTERN_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlPatternDetails;
    }

    /**
     * @param strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the Customer details for
     *               particular TrackingId
     */

    public Map<String, TesterResultVO> getCustrnhDetails(final String strTrackingId) throws RMDDAOException {
        final Map<String, TesterResultVO> mapPatternDetails = new HashMap<String, TesterResultVO>();
        TesterResultVO objTesterResultVO;
        Session session = null;
        StringBuilder objStringBuilder = new StringBuilder();

        try {
            objStringBuilder.append(
                    " SELECT DISTINCT CUSTRNHV.VEHICLE_OBJID,CUSTRNHV.ORG_ID,CUSTRNHV.VEHICLE_HDR,CUSTRNHV.VEHICLE_NO FROM GETS_KEP.RT_ASSET_RESULT ASSETRESULT, GETS_KEP.RT_RESULT_DETAIL RESULTDETAIL,");
            objStringBuilder.append(
                    " GETS_KEP.RT_RESULT_SUMMARY RESULTSUMMARY,GETS_KEP.USER_REQUEST USERREQUEST, GETS_RMD_CUST_RNH_RN_V CUSTRNHV");
            objStringBuilder.append(" WHERE ASSETRESULT.RT_RESULT_DETAIL_ID=RESULTDETAIL.RT_RESULT_DETAIL_ID AND ");
            objStringBuilder.append(" RESULTDETAIL.RT_RESULT_SUMMARY_ID=RESULTSUMMARY.RT_RESULT_SUMMARY_ID AND");
            objStringBuilder.append(" RESULTSUMMARY.USER_REQUEST_ID=USERREQUEST.USER_REQUEST_ID AND ");
            objStringBuilder.append(" CUSTRNHV.VEHICLE_OBJID=ASSETRESULT.ASSET_ID AND");
            objStringBuilder.append(" USERREQUEST.USER_REQUEST_ID = :userRequestId  ");

            session = getHibernateSession();
            Query patternDetailsQuery = session.createSQLQuery(objStringBuilder.toString());
            patternDetailsQuery.setParameter(KEPCommonConstants.USER_REQUEST_ID, Integer.parseInt(strTrackingId));

            List<Object> lstDetails = patternDetailsQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lstDetails)) {
                for (Iterator iterator = lstDetails.iterator(); iterator.hasNext();) {
                    Object[] objPatternDetails = (Object[]) iterator.next();
                    objTesterResultVO = new TesterResultVO();
                    objTesterResultVO.setAssetNo(objPatternDetails[0].toString());
                    objTesterResultVO.setCustomer(objPatternDetails[1].toString());
                    objTesterResultVO.setAssetHeader(objPatternDetails[2].toString());
                    objTesterResultVO.setVehicleNo(objPatternDetails[3].toString());
                    mapPatternDetails.put(objPatternDetails[0].toString(), objTesterResultVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_PATTERN_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_PATTERN_DETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return mapPatternDetails;
    }

    /**
     * @param rtRequestVO
     * @return Integer
     * @Description: This method is used to get the tracking id of the saved
     *               Rule tester record
     */
    @Override
    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDDAOException {
        Session hibernateSession = null;
        int trackId = 0;
        UserRequestHVO objUserRequestHVO = null;
        UserRequestClassificationHVO objUserRequestClassificationHVO = null;
        try {
            hibernateSession = getHibernateSession(rtRequestVO.getUserName());
            objUserRequestHVO = new UserRequestHVO();
            objUserRequestClassificationHVO = new UserRequestClassificationHVO();
            objUserRequestHVO.setCustomerId(rtRequestVO.getCustomerIds());
            objUserRequestHVO.setFleetId(rtRequestVO.getFleets());
            objUserRequestHVO.setModelId(rtRequestVO.getModels());
            objUserRequestHVO.setConfigId(rtRequestVO.getConfigs());
            objUserRequestHVO.setAssetId(rtRequestVO.getUnitNumbers());
            objUserRequestHVO.setRoadNumber(rtRequestVO.getRoadNumber());
            objUserRequestHVO.setClearingLogic(rtRequestVO.getClearingLogic());
            objUserRequestHVO.setRuleId(Integer.parseInt(rtRequestVO.getRuleid()));
            objUserRequestHVO.setRuleTitle(rtRequestVO.getRuletitle());
            objUserRequestHVO.setRuleType(rtRequestVO.getRuletype());

            if (!RMDCommonUtility.isNullOrEmpty(rtRequestVO.getFromdate())) {
                objUserRequestHVO.setStartDate(RMDCommonUtility.stringToDate(rtRequestVO.getFromdate(),
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
            }
            if (!RMDCommonUtility.isNullOrEmpty(rtRequestVO.getTodate())) {
                objUserRequestHVO.setEndDate(RMDCommonUtility.stringToDate(rtRequestVO.getTodate(),
                        RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
            }
            objUserRequestHVO.setStatus(KEPCommonConstants.INPROGRESS);
            objUserRequestHVO.setSegmentWindow(Integer.parseInt(rtRequestVO.getSegmentWindow()));
            if (!(rtRequestVO.getClassificationwin().equals(KEPCommonConstants.EMPTY_STRING))) {

                objUserRequestHVO.setClassificationWindow(Integer.parseInt(rtRequestVO.getClassificationwin()));

            }

            objUserRequestHVO.setRuleDetails(rtRequestVO.getRuleDetails());
            objUserRequestClassificationHVO.setName(rtRequestVO.getClassification());
            if (!((RMDCommonConstants.SELECT).equalsIgnoreCase(rtRequestVO.getRootCause()))) {
                objUserRequestClassificationHVO.setRootCause(rtRequestVO.getRootCause());
            }
            if (!((RMDCommonConstants.SELECT).equalsIgnoreCase(rtRequestVO.getSymptom()))) {
                objUserRequestClassificationHVO.setSymptom(rtRequestVO.getSymptom());
            }
            if (!((RMDCommonConstants.SELECT).equalsIgnoreCase(rtRequestVO.getRx()))) {
                objUserRequestClassificationHVO.setRx(rtRequestVO.getRx());
            }

            objUserRequestClassificationHVO.setUserRequest(objUserRequestHVO);

            hibernateSession.saveOrUpdate(objUserRequestHVO);
            hibernateSession.saveOrUpdate(objUserRequestClassificationHVO);
            hibernateSession.flush();
            trackId = objUserRequestHVO.getUserRequestSeqId();

        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_RULETESTER_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (SQLException e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_RULETESTER_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_RULETESTER_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return trackId;
    }

    @Override
    public List<String> getRoadNumbers(String model, String customer) {
        Session hibernateSession = null;
        List<String> roadNumbers = null;
        try {
            hibernateSession = getHibernateSession();
            String strQuery = "select vehicle_no from gets_rmd_cust_rnh_rn_v where MODEL_NAME_V = :MODEL and ORG_ID = :CUSTOMER";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.MODEL, model);
            query.setParameter(RMDServiceConstants.CUSTOMER, customer);
            roadNumbers = query.list();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ROADNUMBER);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, "model:" + model + "::" + "customer:" + customer), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTesterDAOImpl getRoadNumbers()", e);
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_ROAD_NO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "roadNumbers"), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return roadNumbers;
    }

    @Override
    public List<String> getAssetId(String roadNo, String model, String customer) {
        Session hibernateSession = null;
        List<BigDecimal> assetIds = new ArrayList<BigDecimal>();
        List<String> strAssetIds = new ArrayList<String>();
        try {
            hibernateSession = getHibernateSession();
            String strQuery = "select vehicle_objid from gets_rmd_cust_rnh_rn_v where MODEL_NAME_V = :MODEL and ORG_ID = :CUSTOMER and vehicle_no = :ROADNO";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.MODEL, model);
            query.setParameter(RMDServiceConstants.CUSTOMER, customer);
            query.setParameter(RMDServiceConstants.ROADNO, roadNo);
            assetIds = query.list();
            for (BigDecimal assetId : assetIds) {
                String strAssetId = String.valueOf(assetId.intValue());
                strAssetIds.add(strAssetId);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ASSET_NUMBER);
            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                    new String[] {}, "model:" + model + "::" + "customer:" + customer), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTesterDAOImpl getRoadNumbers()", e);
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_INVALID_ASSET);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "AssetID"), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return strAssetIds;
    }

    @Override
    public List<String> getclearingLogicId(String ruleId) {
        Session hibernateSession = null;
        List<BigDecimal> clearingLogicIds = new ArrayList<BigDecimal>();
        List<String> strClearingLogicIds = new ArrayList<String>();
        try {
            hibernateSession = getHibernateSession();
            String strQuery = "select OBJID from GETS_TOOL_DPD_CL_RUL where CL_RUL2FINRUL = :RULEID";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.RULEID, ruleId);
            clearingLogicIds = query.list();
            for (BigDecimal clearingLogicId : clearingLogicIds) {
                String strClearingLogicId = String.valueOf(clearingLogicId.intValue());
                strClearingLogicIds.add(strClearingLogicId);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.RULEID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "ruleId:" + ruleId), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleTesterDAOImpl getclearingLogicId()", e);
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_CLEARING_LOGIC_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "clearingLogic"), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return strClearingLogicIds;
    }

}
