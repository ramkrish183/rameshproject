/**
 * ============================================================
 * Classification: GE Confidential
 * File : KnowledgeSeekerDAOImpl.java
 * Description : 
 * Package : com.ge.trans.rmd.kep.services.knowledgeseeker.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : November 2 2011
 * History
 * Modified By : iGATEPatni
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.knowledgeseeker.dao.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.kep.hibernate.valueobjects.GetsRmdFaultCodesHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.KsResultDetailHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.KsResultSummaryHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.LookupLanguageHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.UserRequestClassificationHVO;
import com.ge.trans.rmd.kep.hibernate.valueobjects.UserRequestHVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.kep.common.constants.KEPCommonConstants;
import com.ge.trans.eoa.services.kep.common.constants.KEPServiceConstants;
import com.ge.trans.eoa.services.kep.knowledgeseeker.dao.intf.KnowledgeSeekerDAOIntf;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.AnalysisVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.BaseVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.KSRequestVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.SummaryVO;
import com.ge.trans.eoa.services.kep.knowledgeseeker.service.valueobjects.TrackingVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATEPatni
 * @Version : 1.0
 * @Date Created: November 2, 2011
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This Class act as DAO for the Knowledge seeker Screen
 * @History :
 ******************************************************************************/
public class KnowledgeSeekerDAOImpl extends RMDCommonDAO implements KnowledgeSeekerDAOIntf {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLogger.getLogger(KnowledgeSeekerDAOImpl.class);

    /**
     * This method is used for searching Tracking Details data based on the
     * input TrackingVO
     * 
     * @param TrackingVO
     * @return list of TrackingVO
     * @throws RMDServiceException
     */
    @Override
    public List<TrackingVO> getKnowledgeSeekerTrackings(TrackingVO objTrackingVOParam) throws RMDDAOException {
        Session hibernateSession = null;
        List<UserRequestHVO> arlUserRequestHVO = null;
        List<TrackingVO> arlTrackingDetails = new ArrayList<TrackingVO>();
        TrackingVO objTrackingVO = null;
        String strCreatedBy = null;
        String strRunName = null;
        String strStatus = null;
        String strTrackingID = null;
        Date dtFromDate = null;
        Date dtToDate = null;
        Date dtCreateDate = null;
        try {
            hibernateSession = getHibernateSession();
            Criteria criteria = hibernateSession.createCriteria(UserRequestHVO.class);
            strRunName = objTrackingVOParam.getRunName();
            strTrackingID = objTrackingVOParam.getTrackID();
            dtFromDate = objTrackingVOParam.getDtStartDate();
            dtToDate = objTrackingVOParam.getDtEndDate();
            strCreatedBy = objTrackingVOParam.getCreatedBy();
            strStatus = objTrackingVOParam.getStatus();
            dtCreateDate = objTrackingVOParam.getDtCreateDate();
            if (strCreatedBy != null && strCreatedBy.trim().length() != 0) {
                // criteria.add(Restrictions.like(KEPCommonConstants.CREATED_BY,strCreatedBy,
                // MatchMode.ANYWHERE));
                criteria.add(Restrictions.eq(KEPCommonConstants.CREATED_BY, strCreatedBy));
            }
            if (strTrackingID != null && strTrackingID.trim().length() != 0) {
                criteria.add(Restrictions.like("userRequestSeqId", Integer.parseInt(strTrackingID)));
            }
            if (strStatus != null && strStatus.trim().length() != 0) {
                criteria.add(Restrictions.like(KEPCommonConstants.STATUS, strStatus, MatchMode.ANYWHERE));
            }
            if (strRunName != null && strRunName.trim().length() != 0) {
                criteria.add(Restrictions.like(KEPCommonConstants.RUN_NAME, strRunName, MatchMode.ANYWHERE));
            }
            if (dtFromDate != null && null != dtToDate) {

                /*
                 * dtFromDate = RMDCommonUtility.updateAsGMT(dtFromDate);
                 * dtToDate = RMDCommonUtility.updateAsGMT(dtToDate);
                 */
                criteria.add(Restrictions.between(KEPCommonConstants.CREATION_DATE, dtFromDate, dtToDate));
            } else if (dtFromDate != null) {
                // dtFromDate = RMDCommonUtility.updateAsGMT(dtFromDate);
                /*
                 * Date currentGMTDate = RMDCommonUtility
                 * .getGMTDateTime(KEPCommonConstants.DateConstants.
                 * MMddyyyyHHmmssa);
                 */
                Date destDate = new Date();
                try {
                    String strDate = RMDCommonUtility.formatDate(destDate,
                            KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                    destDate = RMDCommonUtility.stringToDate(strDate, KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                } catch (Exception e) {
                    LOG.error("Exception while parsing date: ", e);
                }
                criteria.add(Restrictions.between(KEPCommonConstants.CREATION_DATE, dtFromDate, destDate));
            }
            // this part is to bring the last 30 days record from the DB
            // this will be used for onload of the screen
            if (dtCreateDate != null) {
                // Removed the dependency in calculating date in DAO
                /*
                 * Date destDate=new Date(); String
                 * strDate=RMDCommonUtility.formatDate(destDate,
                 * KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                 * destDate=RMDCommonUtility.stringToDate(strDate,
                 * KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                 */
                Date dest = RMDCommonUtility.getGMTDateTime(KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
                Date src = RMDCommonUtility.substractOrAddFromDate(dest, null, Calendar.DATE,
                        -KEPCommonConstants.Numeric_30_DAYS);
                criteria.add(Restrictions.between(KEPCommonConstants.CREATION_DATE, src, dest));
            }
            // Order by condition is common for all.
            criteria.add(Restrictions.isNotNull(KEPCommonConstants.RUN_NAME));
            criteria.addOrder(Order.desc(KEPCommonConstants.CREATION_DATE));
            arlUserRequestHVO = criteria.list();
            for (UserRequestHVO objUserRequestHVO : arlUserRequestHVO) {
                objTrackingVO = new TrackingVO();
                objTrackingVO.setRunName(objUserRequestHVO.getRunName());
                objTrackingVO.setCreatedBy(objUserRequestHVO.getCreatedBy());
                objTrackingVO.setStatus(objUserRequestHVO.getStatus());
                objTrackingVO.setTrackID(String.valueOf(objUserRequestHVO.getUserRequestSeqId()));
                objTrackingVO.setDtStartDate(objUserRequestHVO.getCreationDate());
                objTrackingVO.setDtEndDate(objUserRequestHVO.getRunEndDate());
                objTrackingVO.setDtCreateDate(objUserRequestHVO.getCreationDate());
                arlTrackingDetails.add(objTrackingVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SEARCHTRACKINGDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SEARCHTRACKINGDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlTrackingDetails;
    }

    /**
     * This method is used for loading the Pattern Summary Details
     * 
     * @param strTrackingId
     *            ,strPattern,strPatternCategory
     * @return list of BaseVO
     * @throws RMDDAOException
     */
    @Override
    public List<BaseVO> getPatternDetails(String strPatternSeqId, String strPatternCategory) throws RMDDAOException {
        Session hibernateSession = null;
        // List<KsPatternCategoryHVO> arlResult = null;
        List<BaseVO> arlDataDetails = new ArrayList<BaseVO>();
        int intPatternSeqId = 0;
        BaseVO objBaseVO = null;
        Query patternDetailsQuery = null;
        StringBuilder objStringBuilder = new StringBuilder();
        try {
            if (strPatternSeqId != null && strPatternSeqId.length() != 0) {
                intPatternSeqId = Integer.parseInt(strPatternSeqId);
            }
            hibernateSession = getHibernateSession();
            if (RMDCommonUtility.isNullOrEmpty(strPatternCategory)) {
                patternDetailsQuery = hibernateSession
                        .createSQLQuery(KEPCommonConstants.FETCH_PATTERN_DETAILS.toString());
                patternDetailsQuery.setParameter(KEPCommonConstants.KSRESULTDETAILID, intPatternSeqId);
            } else {
                objStringBuilder.append(KEPCommonConstants.FETCH_PATTERN_DETAILS.toString());
                objStringBuilder.append(" AND PATTERNCATEGORY.HIT_MISS_CD = :hitormiss");
                patternDetailsQuery = hibernateSession.createSQLQuery(objStringBuilder.toString());
                patternDetailsQuery.setParameter(KEPCommonConstants.KSRESULTDETAILID, intPatternSeqId);
                if (strPatternCategory.equalsIgnoreCase(KEPCommonConstants.H_CHAR)) {
                    patternDetailsQuery.setParameter(KEPCommonConstants.HITTORMISS, KEPCommonConstants.H_CHAR);
                } else if (strPatternCategory.equalsIgnoreCase(KEPCommonConstants.M_CHAR)) {
                    patternDetailsQuery.setParameter(KEPCommonConstants.HITTORMISS, KEPCommonConstants.M_CHAR);
                }
            }
            List<Object> arlPatternDetails = patternDetailsQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlPatternDetails)) {
                for (Iterator iterator = arlPatternDetails.iterator(); iterator.hasNext();) {
                    Object[] objPatternDetails = (Object[]) iterator.next();
                    objBaseVO = new BaseVO();
                    objBaseVO.setSegmentID(objPatternDetails[0].toString());
                    objBaseVO.setAssetName(objPatternDetails[1].toString());
                    objBaseVO.setCustomer(objPatternDetails[2].toString());
                    objBaseVO.setPatternHitorMiss(objPatternDetails[3].toString());
                    objBaseVO.setCreatedBy(objPatternDetails[4].toString());
                    objBaseVO.setAssetHeader(objPatternDetails[5].toString());
                    arlDataDetails.add(objBaseVO);
                }
            }
            /*
             * Criteria criteria = hibernateSession.createCriteria(
             * KsPatternCategoryHVO.class);
             * criteria.setFetchMode("ksResultDetail", FetchMode.JOIN);
             * criteria.createAlias("ksResultDetail", "ksResultDetail");
             * criteria.setFetchMode("ksResultDetail.ksResultSummary",
             * FetchMode.JOIN);
             * criteria.createAlias("ksResultDetail.ksResultSummary",
             * "ksResultSummary");
             * criteria.setFetchMode("ksResultSummary.userRequest",
             * FetchMode.JOIN);
             * criteria.createAlias("ksResultSummary.userRequest",
             * "userRequest");
             * criteria.add(Restrictions.eq("ksResultDetail.pattern",
             * strPattern));
             * criteria.add(Restrictions.eq("userRequest.userRequestSeqId",
             * intTrackingID)); if (strPatternCategory != null &&
             * strPatternCategory.length() != 0) { if (!strPatternCategory
             * .equalsIgnoreCase(KEPCommonConstants.ALL)) {
             * if(strPatternCategory.equalsIgnoreCase(KEPCommonConstants.HIT)){
             * strPatternCategory=KEPCommonConstants.H_CHAR; } else
             * if(strPatternCategory.equalsIgnoreCase(KEPCommonConstants.MISS)){
             * strPatternCategory=KEPCommonConstants.M_CHAR; }
             * criteria.add(Restrictions.eq(RMDCommonConstants.HIT_OR_MSGFLAG,
             * strPatternCategory)); } } arlResult =
             * (ArrayList<KsPatternCategoryHVO>) criteria .list();
             * if(RMDCommonUtility.isCollectionNotEmpty(arlResult)){ for
             * (KsPatternCategoryHVO objCategoryHVO : arlResult) { objBaseVO =
             * new BaseVO(); //strAssetName=objCategoryHVO.getAssetId();
             * strCustomerName=objCategoryHVO.getCustomer();
             * if(!RMDCommonUtility.checkNull(strAssetName)){
             * objBaseVO.setAssetName(strAssetName); }
             * if(!RMDCommonUtility.isNull(strCustomerName)){
             * objBaseVO.setCustomer(strCustomerName); }
             * objBaseVO.setAssetName(String.valueOf(objCategoryHVO.getAssetId()
             * )); objBaseVO.setCreatedBy(objCategoryHVO.getCreatedBy());
             * objBaseVO.setSegmentID(objCategoryHVO.getSegmentId()); objBaseVO
             * .setPatternHitorMiss(objCategoryHVO.getHitOrMissFlag());
             * //objBaseVO.setAssetHeader(objCategoryHVO.getAssetHeader());
             * arlDataDetails.add(objBaseVO); } }
             */
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_DATADETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlDataDetails;
    }

    /*	*//**
           * This method is used for loading the tracking ID
           * 
           * @param strTrackingId
           * @return list of TrackingVO
           * @throws RMDDAOException
           *//*
             * public List<TrackingVO> getTrackingIDs(String strTrackingId)
             * throws RMDDAOException { Session session = null; List
             * lstTrackingId; Query hibQuery = null; GetKmKepKsTrackingHVO
             * objGetKmKepKsTrackingHVO; TrackingVO objTrackingVO = null;
             * List<TrackingVO> arlTrackingDetails = new
             * ArrayList<TrackingVO>(); try { session = getHibernateSession();
             * String Qry =
             * "FROM GetKmKepKsTrackingHVO WHERE getKmKepKsTrackingSeqId LIKE:trackingId"
             * ; if (!RMDCommonUtility.isNull(strTrackingId)) { hibQuery =
             * session.createQuery(Qry); if (null != strTrackingId &&
             * !strTrackingId
             * .equalsIgnoreCase(KEPCommonConstants.EMPTY_STRING)) {
             * hibQuery.setString(KEPCommonConstants.TRACKING_ID, "%" +
             * strTrackingId + "%"); } lstTrackingId = (ArrayList)
             * hibQuery.list(); int arlSize = lstTrackingId.size(); if (arlSize
             * > 0) { for (int i = 0; i < arlSize; i++) {
             * objGetKmKepKsTrackingHVO = (GetKmKepKsTrackingHVO) lstTrackingId
             * .get(i); objTrackingVO = new TrackingVO();
             * objTrackingVO.setTrackID(String .valueOf(objGetKmKepKsTrackingHVO
             * .getGetKmKepKsTrackingSeqId()));
             * objTrackingVO.setDtStartDate(objGetKmKepKsTrackingHVO
             * .getStartDate());
             * objTrackingVO.setDtEndDate(objGetKmKepKsTrackingHVO
             * .getEndDate());
             * objTrackingVO.setDtCreateDate(objGetKmKepKsTrackingHVO
             * .getCreationDate()); arlTrackingDetails.add(objTrackingVO); } } }
             * } catch (RMDDAOConnectionException ex) { String errorCode =
             * RMDCommonUtility
             * .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
             * throw new RMDDAOException(errorCode, new String[] {},
             * RMDCommonUtility.getMessage(errorCode, new String[] {},
             * KEPCommonConstants.ENGLISH_LANGUAGE), ex,
             * KEPCommonConstants.FATAL_ERROR); } catch (Exception e) { String
             * errorCode = RMDCommonUtility
             * .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
             * throw new RMDDAOException(errorCode, new String[] {},
             * RMDCommonUtility.getMessage(errorCode, new String[] {},
             * KEPCommonConstants.ENGLISH_LANGUAGE), e,
             * KEPCommonConstants.MAJOR_ERROR); } finally {
             * releaseSession(session); } return arlTrackingDetails; }
             */

    /**
     * This method is used for getting the CreatedBy
     * 
     * @param strCreatedBy
     * @return list of TrackingVO
     * @throws RMDDAOException
     */
    @Override
    public List<TrackingVO> getCreators(String strCreatedBy) throws RMDDAOException {
        Session hibernateSession = null;
        List<UserRequestHVO> arlUserRequestHVO = null;
        List<TrackingVO> arlTrackingDetails = new ArrayList<TrackingVO>();
        TrackingVO objTrackingVO = null;
        try {
            hibernateSession = getHibernateSession();
            Criteria criteria = hibernateSession.createCriteria(UserRequestHVO.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.add(Restrictions.like(KEPCommonConstants.CREATED_BY, strCreatedBy, MatchMode.ANYWHERE));
            criteria.add(Restrictions.isNotNull(KEPCommonConstants.RUN_NAME));
            arlUserRequestHVO = criteria.list();
            for (UserRequestHVO objUserRequest : arlUserRequestHVO) {
                objTrackingVO = new TrackingVO();
                objTrackingVO.setCreatedBy(objUserRequest.getCreatedBy());
                arlTrackingDetails.add(objTrackingVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlTrackingDetails;
    }

    /**
     * This method is used for getting the Run Name
     * 
     * @param strRunName
     * @return list of TrackingVO
     * @throws RMDDAOException
     */
    @Override
    public List<TrackingVO> getRunNames(String strRunName) throws RMDDAOException {
        Session hibernateSession = null;
        List<UserRequestHVO> arlUserRequestHVO = null;
        List<TrackingVO> arlTrackingDetails = new ArrayList<TrackingVO>();
        TrackingVO objTrackingVO = null;
        try {
            hibernateSession = getHibernateSession();
            Criteria criteria = hibernateSession.createCriteria(UserRequestHVO.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.add(Restrictions.ilike(KEPCommonConstants.RUN_NAME, strRunName, MatchMode.ANYWHERE));
            arlUserRequestHVO = criteria.list();
            for (UserRequestHVO userRequestObj : arlUserRequestHVO) {
                objTrackingVO = new TrackingVO();
                objTrackingVO.setRunName(userRequestObj.getRunName());
                arlTrackingDetails.add(objTrackingVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_RUNNAME);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_RUNNAME);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlTrackingDetails;
    }

    /**
     * This method is used for getting the Tracking details
     * 
     * @param strRunName
     * @return list of TrackingVO
     * @throws RMDDAOException
     */

    @Override
    public List getTrackingDetails(String strTrackingID) {
        Session hibernateSession = null;
        List<KsResultDetailHVO> arlKsResultDetailHVO = null;
        List<AnalysisVO> arlAnalysisList = null;
        AnalysisVO objAnalysisVO;
        int intTrackingID = 0;
        String strFaultcode;
        StringTokenizer stringTokenizer = null;
        List<GetsRmdFaultCodesHVO> arlFaultdesc = null;
        // List arlFaultdesc = null;
        String strFaultCodeDesc = KEPCommonConstants.EMPTY_STRING;
        String strCoveragePercent = null;
        String strScore = null;
        String strFault = null;
        String strPattern = KEPCommonConstants.EMPTY_STRING;
        // Added to collect HashMap
        Map<String, String> mapFaultCodeDesc = null;
        // For getting summary
        List<UserRequestHVO> arlUserRequestHVO = null;
        List<SummaryVO> lstSummaryVO = null;
        SummaryVO objSummaryVO = null;
        Set setGetKsResultSummaries = null;
        String strStartDate;
        String strEndDate;
        Date startDate = null;
        Date endDate = null;
        List lstTrackingSummary = new ArrayList();
        String strAssetId = null;
        String[] tempStrAssetId = null;
        Set setUserRequestClassification = null;
        Criteria criteriaFaultDesc = null;
        String dataFilterValue = RMDCommonConstants.EMPTY_STRING;
        List<ElementVO> arlSegmentWindow = null;
        String strSegmentWindow = null;
        String strClassficationStepSize = null;
        List<ElementVO> arlClassficationStepSize = null;
        List<ElementVO> arlDataFilter = null;
        String strFaultCodeQuery = null;
        Query faultQuery = null;
        int resultSummarySeqId = 0;
        StringBuilder objStringBuilder = new StringBuilder();
        List<ElementVO> arlRx = new ArrayList<ElementVO>();

        try {
            hibernateSession = getHibernateSession();
            arlAnalysisList = new ArrayList<AnalysisVO>();
            lstSummaryVO = new ArrayList<SummaryVO>();
            mapFaultCodeDesc = new HashMap<String, String>();
            if (!RMDCommonUtility.isNull(strTrackingID)) {
                intTrackingID = Integer.parseInt(strTrackingID);
                // For getting the Tracking summary
                Criteria critTrackingSummary = hibernateSession.createCriteria(UserRequestHVO.class);
                critTrackingSummary.add(Restrictions.eq("userRequestSeqId", intTrackingID));
                arlUserRequestHVO = critTrackingSummary.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlUserRequestHVO)) {
                    for (UserRequestHVO objUserRequest : arlUserRequestHVO) {
                        objSummaryVO = new SummaryVO();
                        if (!RMDCommonUtility.checkNull(objUserRequest.getClassificationWindow())) {
                            objSummaryVO.setClassificationWindow(objUserRequest.getClassificationWindow());
                        }
                        if (!RMDCommonUtility.checkNull(objUserRequest.getProgExclude())) {
                            objSummaryVO.setProgExclude(objUserRequest.getProgExclude());
                        }
                        objSummaryVO.setNormalCondition(objUserRequest.getNormalCondition());
                        objSummaryVO.setSupportLevel(objUserRequest.getSupportLevel());
                        objSummaryVO.setCustomer(objUserRequest.getCustomerId());
                        objSummaryVO.setFleetName(objUserRequest.getFleetId());
                        objSummaryVO.setModelName(objUserRequest.getModelId());
                        objSummaryVO.setConfigName(objUserRequest.getConfigId());
                        if (null != objUserRequest.getDaysAfterFailure()) {
                            objSummaryVO.setDaysAfterEvent(objUserRequest.getDaysAfterFailure());
                        }
                        if (null != objUserRequest.getAlgorithm()) {
                            objSummaryVO.setAlgorithmSelection(objUserRequest.getAlgorithm());
                        }
                        if (null != objUserRequest.getCoverage()) {
                            objSummaryVO.setCoverageAnalysis(objUserRequest.getCoverage());
                        }
                        startDate = objUserRequest.getStartDate();
                        endDate = objUserRequest.getEndDate();
                        strStartDate = RMDCommonUtility.convertToString(startDate,
                                KEPCommonConstants.DateConstants.MMddyyyyHHmmss);
                        strEndDate = RMDCommonUtility.convertToString(endDate,
                                KEPCommonConstants.DateConstants.MMddyyyyHHmmss);
                        objSummaryVO.setRuleMiningStartDate(strStartDate);
                        objSummaryVO.setRuleMiningEndDate(strEndDate);
                        objSummaryVO.setFilterFlag(objUserRequest.getFilterFlag());
                        // putting null check for the asset id
                        // bringing asset name instead of assetid
                        if (null != objUserRequest.getAssetId()) {
                            strAssetId = objUserRequest.getAssetId();
                            objSummaryVO.setAssetID(strAssetId);
                        }

                        if (null != objUserRequest.getAssetId()) {
                            strAssetId = objUserRequest.getAssetId();
                            tempStrAssetId = strAssetId.split(RMDCommonConstants.COMMMA_SEPARATOR);
                            objSummaryVO.setTotAssetsAnalysed(tempStrAssetId.length);
                        }
                        setGetKsResultSummaries = objUserRequest.getKsResultSummaries();
                        for (Iterator iterator = setGetKsResultSummaries.iterator(); iterator.hasNext();) {
                            KsResultSummaryHVO objKsResultSummaryHVO = (KsResultSummaryHVO) iterator.next();
                            resultSummarySeqId = objKsResultSummaryHVO.getKsResultSummarySeqId();
                            objSummaryVO.setAnalysisFailureCases(objKsResultSummaryHVO.getAnalysisFailureCases());
                            objSummaryVO.setAnalysisNormalCases(objKsResultSummaryHVO.getAnalysisNormalCases());
                            objSummaryVO.setFeAssets(objKsResultSummaryHVO.getFeAssets());
                            objSummaryVO.setSeAssets(objKsResultSummaryHVO.getSeAssets());
                            objSummaryVO.setNormalSegments(objKsResultSummaryHVO.getNormalSegments());
                            objSummaryVO.setFailureSegments(objKsResultSummaryHVO.getFailureSegments());
                        }
                        setUserRequestClassification = objUserRequest.getUserRequestClassification();
                        for (Iterator iterator = setUserRequestClassification.iterator(); iterator.hasNext();) {
                            UserRequestClassificationHVO objUserRequestClassificationHVO = (UserRequestClassificationHVO) iterator
                                    .next();
                            if (objUserRequestClassificationHVO.getName() != null) {
                                if (objUserRequestClassificationHVO.getFaultCode() != null) {
                                    objSummaryVO.setFaultCode(objUserRequestClassificationHVO.getFaultCode());
                                    objSummaryVO.setFailureType(objUserRequestClassificationHVO.getName());
                                } else {
                                    objSummaryVO.setFailureType(objUserRequestClassificationHVO.getName());

                                    if (KEPCommonConstants.CCDB
                                            .equalsIgnoreCase(objUserRequestClassificationHVO.getName())) {
                                        objSummaryVO.setRootCause(objUserRequestClassificationHVO.getRootCause());
                                        objSummaryVO.setSymptoms(objUserRequestClassificationHVO.getSymptom());
                                    } else {
                                        arlRx = getRx(objSummaryVO.getModelName(), objSummaryVO.getCustomer(), "All",
                                                strStartDate, strEndDate, objUserRequestClassificationHVO.getRx());
                                        if (!RMDCommonUtility.checkNull(arlRx)) {
                                            for (ElementVO objElementVO : arlRx) {
                                                if (objStringBuilder != null && objStringBuilder.length() != 0) {
                                                    objStringBuilder.append(RMDCommonConstants.COMMMA_SEPARATOR);
                                                }
                                                objStringBuilder.append(objElementVO.getName());
                                            }
                                        }
                                        objSummaryVO.setRxDesc(objStringBuilder.toString());
                                    }
                                }
                            }
                        }
                        arlDataFilter = getLookUPDetails(KEPCommonConstants.DEFAULT_DATA_FILTER,
                                RMDCommonConstants.ENGLISH_LANGUAGE);
                        if (!RMDCommonUtility.checkNull(arlDataFilter)) {
                            for (ElementVO objElementVO : arlDataFilter) {
                                if (!RMDCommonUtility.isNullOrEmpty(objElementVO.getName())) {
                                    dataFilterValue += objElementVO.getName() + RMDCommonConstants.COMMMA_SEPARATOR;
                                }
                            } // Check to remove comma at the end of string
                            if (!RMDCommonUtility.isNullOrEmpty(dataFilterValue)) {
                                dataFilterValue = dataFilterValue.trim().substring(RMDCommonConstants.INT_CONST_ZERO,
                                        dataFilterValue.trim().length() - 1);
                                objSummaryVO.setDataFilter(dataFilterValue);
                            }
                        }

                        arlSegmentWindow = getLookUPDetails(KEPCommonConstants.RM_DEFAULT_CONFIG_SEGMENT,
                                RMDCommonConstants.ENGLISH_LANGUAGE);
                        if (!RMDCommonUtility.checkNull(arlSegmentWindow)) {
                            for (ElementVO objElementVO : arlSegmentWindow) {
                                strSegmentWindow = objElementVO.getName();
                                if (!RMDCommonUtility.isNullOrEmpty(strSegmentWindow)) {
                                    objSummaryVO.setSegmentWindow(Integer.parseInt(strSegmentWindow));
                                }
                            }
                        }
                        arlClassficationStepSize = getLookUPDetails(KEPCommonConstants.RM_DEFAULT_CONFIG_STEP,
                                RMDCommonConstants.ENGLISH_LANGUAGE);
                        if (!RMDCommonUtility.checkNull(arlClassficationStepSize)) {
                            for (ElementVO objElementVO : arlClassficationStepSize) {
                                strClassficationStepSize = objElementVO.getName();
                                if (!RMDCommonUtility.isNullOrEmpty(strClassficationStepSize)) {
                                    objSummaryVO.setClassificationStepSize(Integer.parseInt(strClassficationStepSize));
                                }
                            }
                        }
                        lstSummaryVO.add(objSummaryVO);
                    }
                }
                // Loading the faultcodes and description to a Hashmap
                criteriaFaultDesc = hibernateSession.createCriteria(GetsRmdFaultCodesHVO.class);
                arlFaultdesc = criteriaFaultDesc.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlFaultdesc)) {
                    for (GetsRmdFaultCodesHVO objGetsRmdFaultCodesHVO : arlFaultdesc) {
                        mapFaultCodeDesc.put(objGetsRmdFaultCodesHVO.getFaultCode(),
                                objGetsRmdFaultCodesHVO.getFaultDesc());
                    }
                }
                // Looping Result details
                final Criteria criteria = hibernateSession.createCriteria(KsResultDetailHVO.class);
                criteria.add(Restrictions.eq("ksResultSummary.ksResultSummarySeqId", resultSummarySeqId));
                criteria.addOrder(Order.desc(RMDCommonConstants.SCORE));
                arlKsResultDetailHVO = criteria.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlKsResultDetailHVO)) {
                    for (KsResultDetailHVO ksResultDetailHVO : arlKsResultDetailHVO) {
                        objAnalysisVO = new AnalysisVO();
                        objAnalysisVO.setTrackID(Integer.toString(resultSummarySeqId));
                        strFaultcode = ksResultDetailHVO.getPattern();
                        objAnalysisVO.setCoverages(ksResultDetailHVO.getCoverageCnt());
                        strCoveragePercent = RMDCommonUtility.getRoundedString(ksResultDetailHVO.getCoveragePct());
                        strScore = RMDCommonUtility.getRoundedString(ksResultDetailHVO.getScore());
                        // as per the comments removed false alarm percent
                        objAnalysisVO.setCoveragesPercent(strCoveragePercent);
                        objAnalysisVO.setFalseAlarm(ksResultDetailHVO.getFalseAlarmCnt());
                        objAnalysisVO.setFalseAlarmPercent(ksResultDetailHVO.getFalseAlarmPct());
                        objAnalysisVO.setScore(strScore);
                        objAnalysisVO.setPatternSeqID(ksResultDetailHVO.getKsResultDetailSeqId());
                        objAnalysisVO.setFaultCodes(ksResultDetailHVO.getPattern());
                        stringTokenizer = new StringTokenizer(strFaultcode, KEPCommonConstants.COMMMA_SEPARATOR);
                        while (stringTokenizer.hasMoreTokens()) {
                            strFault = stringTokenizer.nextToken();
                            strFaultCodeDesc = mapFaultCodeDesc.get(strFault);
                            strPattern += strFault + KEPCommonConstants.HYPHEN + KEPCommonConstants.START_FONT_TAG
                                    + strFaultCodeDesc + KEPCommonConstants.END_FONT_TAG;
                        }
                        strPattern = strPattern.substring(0, strPattern.length() - 1);
                        objAnalysisVO.setPattern(strPattern);
                        strPattern = KEPCommonConstants.EMPTY_STRING;
                        arlAnalysisList.add(objAnalysisVO);
                    }
                }
            }
            // adding the Tracking Summary list and Tracking Details list to the
            // common list
            lstTrackingSummary.add(arlAnalysisList);
            lstTrackingSummary.add(lstSummaryVO);
        } catch (RMDDAOConnectionException ex) {

            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SEARCHANALYSISDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SEARCHANALYSISDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);

        }
        return lstTrackingSummary;
    }

    @Override
    public Integer createKnowledgeSeekerRequest(KSRequestVO ksRequestVO) throws RMDDAOException {
        Session hibernateSession = null;
        int trackId = 0;
        UserRequestHVO objUserRequestHVO = null;
        UserRequestClassificationHVO objUserRequestClassificationHVO = null;
        try {
            hibernateSession = getHibernateSession(ksRequestVO.getUserName());
            objUserRequestHVO = new UserRequestHVO();
            objUserRequestClassificationHVO = new UserRequestClassificationHVO();
            objUserRequestHVO.setCustomerId(ksRequestVO.getCustomers());
            objUserRequestHVO.setAssetId(ksRequestVO.getUnitNumbers());
            objUserRequestHVO.setFleetId(ksRequestVO.getFleets());
            objUserRequestHVO.setModelId(ksRequestVO.getModels());
            objUserRequestHVO.setConfigId(ksRequestVO.getConfigs());
            /*
             * getKmKepKsTrackingHVO.setCustomerName(ksRequestVO.getCustomers())
             * ; getKmKepKsTrackingHVO.setFleetName(ksRequestVO.getFleets());
             * getKmKepKsTrackingHVO.setModelName(ksRequestVO.getModels());
             * getKmKepKsTrackingHVO.setConfigName(ksRequestVO.getConfigs());
             * getKmKepKsTrackingHVO.setAssetId(ksRequestVO.getUnitNumbers());
             * getKmKepKsTrackingHVO.setAssetName(ksRequestVO.getRoadNumber());
             */
            /*
             * getKmKepKsTrackingHVO.setStartDate(RMDCommonUtility.updateAsGMT((
             * Date ) dateFormat .parse(ksRequestVO.getFromdate())));
             * getKmKepKsTrackingHVO
             * .setEndDate(RMDCommonUtility.updateAsGMT((Date) dateFormat
             * .parse(ksRequestVO.getTodate())));
             */
            if (!RMDCommonUtility.isNullOrEmpty(ksRequestVO.getFromdate())) {
                try {
                    objUserRequestHVO.setStartDate(RMDCommonUtility.stringToDate(ksRequestVO.getFromdate(),
                            RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
                } catch (ParseException e) {
                    LOG.error("Exception while parsing date: ", e);
                }
            }
            if (!RMDCommonUtility.isNullOrEmpty(ksRequestVO.getTodate())) {
                try {
                    objUserRequestHVO.setEndDate(RMDCommonUtility.stringToDate(ksRequestVO.getTodate(),
                            RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
                } catch (ParseException e) {
                    LOG.error("Exception while parsing date: ", e);
                }
            }
            objUserRequestHVO.setRunName(ksRequestVO.getRunname());

            objUserRequestHVO.setStatus(KEPCommonConstants.INPROGRESS);

            // Setting Current date as creation date
            Date creationDate = new Date();
            String strDate = RMDCommonUtility.formatDate(creationDate,
                    KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
            creationDate = RMDCommonUtility.stringToDate(strDate, KEPCommonConstants.DateConstants.MMddyyyyHHmmssa);
            objUserRequestHVO.setCreationDate(creationDate);

            objUserRequestHVO.setAlgorithm(ksRequestVO.getAlgorithm());

            objUserRequestHVO.setFilterFlag(ksRequestVO.getFilterFlag());
            // addded for new changes

            objUserRequestHVO.setClassificationWindow(ksRequestVO.getClassificationWindow() != null
                    ? Integer.parseInt(ksRequestVO.getClassificationWindow()) : 0);

            objUserRequestHVO.setProgExclude(
                    ksRequestVO.getProgWindow() != null ? Integer.parseInt(ksRequestVO.getProgWindow()) : 0);

            objUserRequestHVO.setNormalCondition(ksRequestVO.getNormalData());

            objUserRequestHVO.setCoverage((Integer.parseInt(ksRequestVO.getCoverage())));
            objUserRequestHVO.setDaysAfterFailure((Integer.parseInt(ksRequestVO.getDaysAfterEvent())));
            // Added temporily, once identified new columns for faultcodes,
            // runAgainstFields need to change accordinly
            // if the request has Faultcode, then it won't have root cause and
            // symptoms so don't save them.
            // Else save Root cause and symptoms values and save them properly.
            if (ksRequestVO.getFaultCode() != null) {
                objUserRequestClassificationHVO.setFaultCode(ksRequestVO.getFaultCode());
                objUserRequestClassificationHVO.setName(ksRequestVO.getClassification());
            } else {
                objUserRequestClassificationHVO.setName(ksRequestVO.getClassification());
                objUserRequestClassificationHVO.setRootCause(ksRequestVO.getRootCause());
                objUserRequestClassificationHVO.setSymptom(ksRequestVO.getSymptom());
                objUserRequestClassificationHVO.setRx(ksRequestVO.getRx());
            }
            objUserRequestClassificationHVO.setUserRequest(objUserRequestHVO);
            hibernateSession.saveOrUpdate(objUserRequestHVO);
            hibernateSession.saveOrUpdate(objUserRequestClassificationHVO);
            hibernateSession.flush();
            trackId = objUserRequestHVO.getUserRequestSeqId();
        } catch (RMDDAOConnectionException ex) {
            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_IMINE_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), ex,
                    KEPCommonConstants.FATAL_ERROR);
        } catch (SQLException e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_IMINE_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } catch (Exception e) {

            final String errorCode = RMDCommonUtility
                    .getErrorCode(KEPServiceConstants.DAO_EXCEPTION_SAVE_IMINE_REQUEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, KEPCommonConstants.ENGLISH_LANGUAGE), e,
                    KEPCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }

        return trackId;
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
                    .add(Restrictions.eq("lookup.lookupName", strListName))
                    .add(Restrictions.eq("lookup.lookupState", RMDCommonConstants.ACTIVATE));
            arlResult = criteria.list();
            for (Iterator iterator = arlResult.iterator(); iterator.hasNext();) {
                LookupLanguageHVO lookupLanguageObj = (LookupLanguageHVO) iterator.next();
                ElementVO objElementVO = new ElementVO();
                objElementVO.setId(Integer.toString(lookupLanguageObj.getLookup().getLookupSeqId()));
                objElementVO.setName(lookupLanguageObj.getLookup().getLookupValue());
                arlLookUpDetails.add(objElementVO);

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
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
     * This method is used for fetching the symptom from DB
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    @Override
    public List getSymptom(final String strCustomer, final String strModel, final String strFleet,
            final String strFromDate, final String strToDate) throws RMDDAOException {

        List<ElementVO> arlSymptom = new ArrayList<ElementVO>();
        Session hibernateSession = null;
        Query hibQuery = null;
        StringBuilder strSymptomQuery = new StringBuilder();
        try {

            hibernateSession = getDWHibernateSession();
            strSymptomQuery.append(KEPServiceConstants.FETCH_SYMPTOM_VALUES.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strSymptomQuery.append(" AND RC.CUSTOMER_ID IN(:customer)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strSymptomQuery.append(" AND M.MODEL_CD IN(:model)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strSymptomQuery.append(" AND V.FLEET_CD  IN (:fleet)");

            }
            strSymptomQuery.append(" AND RC.EVDT BETWEEN :start AND :end");

            strSymptomQuery.append(" GROUP BY RC.SYMPTOM_CD");

            hibQuery = hibernateSession.createSQLQuery(strSymptomQuery.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("customer", strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("model", strModel.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("fleet", strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            hibQuery.setParameter("start",
                    RMDCommonUtility.stringToDate(strFromDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
            hibQuery.setParameter("end",
                    RMDCommonUtility.stringToDate(strToDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));

            List<Object> result = hibQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(result)) {
                for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                    Object[] objSymptomDetails = (Object[]) iterator.next();
                    if (objSymptomDetails != null && objSymptomDetails.length > 0) {
                        ElementVO objElementVO = new ElementVO();
                        // strSymptom=(String) iterator.next();
                        objElementVO.setId(objSymptomDetails[0].toString());
                        objElementVO.setName(objSymptomDetails[0].toString() + RMDCommonConstants.BLANK_SPACE + "["
                                + String.valueOf(objSymptomDetails[1]) + "]");
                        arlSymptom.add(objElementVO);
                    }
                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
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
            strSymptomQuery = null;
        }

        return arlSymptom;

    }

    /**
     * This method is used for fetching the RootCause from DB
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    @Override
    public List getRootCause(final String strCustomer, final String strModel, final String strFleet,
            final String strFromDate, final String strToDate) throws RMDDAOException {

        List<ElementVO> arlRootCause = new ArrayList<ElementVO>();
        Session hibernateSession = null;
        Query hibQuery = null;
        StringBuilder strRootCauseQry = new StringBuilder();

        try {

            hibernateSession = getDWHibernateSession();
            strRootCauseQry.append(KEPServiceConstants.FETCH_ROOT_CAUSE_VALUES.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRootCauseQry.append(" AND RC.CUSTOMER_ID IN(:customer)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRootCauseQry.append(" AND M.MODEL_CD IN(:model)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRootCauseQry.append(" AND V.FLEET_CD  IN (:fleet)");
            }
            strRootCauseQry.append(" AND RC.EVDT BETWEEN :start AND :end");

            strRootCauseQry.append(" GROUP BY RC.ROOT_CAUSE_CODE");
            hibQuery = hibernateSession.createSQLQuery(strRootCauseQry.toString());
            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("customer", strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("model", strModel.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("fleet", strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }

            hibQuery.setParameter("start",
                    RMDCommonUtility.stringToDate(strFromDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
            hibQuery.setParameter("end",
                    RMDCommonUtility.stringToDate(strToDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));

            List<Object> result = hibQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(result)) {
                for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                    Object[] objRootCauseDetails = (Object[]) iterator.next();
                    if (objRootCauseDetails != null && objRootCauseDetails.length > 0) {
                        ElementVO objElementVO = new ElementVO();
                        // strRootCause=(String) iterator.next();
                        objElementVO.setId(objRootCauseDetails[0].toString());
                        objElementVO.setName(objRootCauseDetails[0].toString() + RMDCommonConstants.BLANK_SPACE + "["
                                + String.valueOf(objRootCauseDetails[1]) + "]");
                        arlRootCause.add(objElementVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
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
            strRootCauseQry = null;
        }
        return arlRootCause;

    }

    /**
     * This method is used for fetching the Rx from DB
     * 
     * @param
     * @return list
     * @throws RMDDAOException
     */
    @Override
    public List getRx(final String strModel, final String strCustomer, final String strFleet, final String strFromDate,
            final String strToDate, String rxId) throws RMDDAOException {
        List<ElementVO> arlRx = new ArrayList<ElementVO>();
        Session hibernateSession = null;
        Query hibQuery = null;
        StringBuilder strRxQry = new StringBuilder();
        try {
            hibernateSession = getDWHibernateSession();
            strRxQry.append(KEPServiceConstants.FETCH_RX_VALUES.toString());

            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRxQry.append(" AND M.MODEL_CD IN (:model)");
            }

            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRxQry.append(" AND RD.CUSTOMER_ID IN (:customer)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(rxId) && !rxId.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRxQry.append(" AND RD.RECOM_OBJID IN (:rxId)");
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                strRxQry.append(" AND V.FLEET_CD IN (:fleet)");
            }
            strRxQry.append(" GROUP BY RD.RECOM_OBJID, RD.RECOMM_TITLE");

            hibQuery = hibernateSession.createSQLQuery(strRxQry.toString());

            if (!RMDCommonUtility.isNullOrEmpty(strModel) && !strModel.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("model", strModel.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strCustomer) && !strCustomer.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("customer", strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(rxId) && !rxId.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("rxId", rxId.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFleet) && !strFleet.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                hibQuery.setParameterList("fleet", strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR));
            }
            hibQuery.setParameter("start",
                    RMDCommonUtility.stringToDate(strFromDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));
            hibQuery.setParameter("end",
                    RMDCommonUtility.stringToDate(strToDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmss));

            List<Object> result = hibQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(result)) {
                for (Iterator iterator = result.iterator(); iterator.hasNext();) {
                    Object[] objRxDetails = (Object[]) iterator.next();
                    if (objRxDetails != null && objRxDetails.length > 0) {
                        ElementVO objElementVO = new ElementVO();
                        // strRootCause=(String) iterator.next();
                        objElementVO.setId(objRxDetails[0].toString());
                        // Added as part to display Rx count
                        if (!RMDCommonUtility.isNullOrEmpty(rxId) && !rxId.equalsIgnoreCase(RMDCommonConstants.ALL)) {
                            objElementVO.setName(objRxDetails[1].toString());
                        } else {
                            objElementVO.setName(objRxDetails[1].toString() + RMDCommonConstants.BLANK_SPACE + "["
                                    + String.valueOf(objRxDetails[2]) + "]");
                        }
                        arlRx.add(objElementVO);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(KEPServiceConstants.DAO_EXCEPTION_GET_TRACKINGID);
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
            strRxQry = null;
        }
        return arlRx;

    }
}
