/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindFindCaseDAOImpl.java
 * Description :
 *
 * Package :  com.ge.trans.rmd.services.cases.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 10, 2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinFragment;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.eoa.services.cases.dao.intf.FindCaseDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTypeVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseRxTitlesServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindCaseServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SelectCaseHomeVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmQueueHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmRecomDelvHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.SelectCaseHomeHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 10, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class FindCaseDAOImpl extends BaseCaseDAO implements FindCaseDAOIntf {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public FindCaseDAOImpl() {
        super();
    }

    public static final RMDLogger LOG = RMDLogger.getLogger(FindCaseDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.FindCaseDAOIntf#
     * getSearchQueueMenu ()
     */
    @Override
    public List getSearchQueueMenu(final String strLanguage, final String strUserLanguage) throws RMDDAOException {
        List arlSearchQueues;
        final List lstQueues = new ArrayList();
        Session session = null;
        ElementVO eVO;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            final Criteria criteria = session.createCriteria(GetCmQueueHVO.class);
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, strLanguage));
            criteria.addOrder(Order.asc(RMDServiceConstants.QUEUE_NAME));
            arlSearchQueues = criteria.list();
            GetCmQueueHVO vo;
            eVO = new ElementVO();
            eVO.setId(RMDServiceConstants.ALL);
            eVO.setName(RMDServiceConstants.ALL);
            lstQueues.add(eVO);
            int iSearchQueuesSize = arlSearchQueues.size();
            for (int i = 0; i < iSearchQueuesSize; i++) {
                vo = (GetCmQueueHVO) arlSearchQueues.get(i);
                eVO = new ElementVO();
                eVO.setId(vo.getName());
                eVO.setName(vo.getName());
                lstQueues.add(eVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getSearchQueueMenu()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlSearchQueues = null;
        }
        return lstQueues;
    }

    /***********************************************************************
     * This method is used to fetch the Case Status DropDown values from
     * database
     **********************************************************************/
    /**
     * @param strCondition
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    @Override
    public List loadCaseStatusDropDown(String strCondition, String strLanguage) throws RMDDAOException {
        List arlResults = null;
        Session objHibernateSession = null;
        try {
            arlResults = getLookupListValues(strCondition, strLanguage);
            Collections.sort(arlResults, OBJIDCOMPARATOR);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_STATUSLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl loadCaseStatusDropDown()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_STATUSLIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return arlResults;
    }

    /**
     *
     */
    public static final Comparator OBJIDCOMPARATOR = new Comparator() {

        /**
         * @Author:
         * @param objFirst
         * @param objSecond
         * @return
         * @Description:
         */
        @Override
        public int compare(Object objFirst, Object objSecond) {
            ElementVO firstObj = (ElementVO) objFirst;
            ElementVO secondObj = (ElementVO) objSecond;
            String firstObject = firstObj.getId();
            String secondObject = secondObj.getId();
            if (firstObject == null) {
                return -1;
            }
            if (secondObject == null) {
                return +1;
            }
            return firstObject.compareTo(secondObject);
        }
    };

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#findCases(com.ge
     * .trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings("deprecation")
    public List findCases(final FindCaseServiceVO findCaseSerVO) throws RMDDAOException {
        List arlSearchRes;
        List arlFinalResults;
        Session session = null;
        SelectCaseHomeVO objSelectCase;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            final Criteria criteria = session.createCriteria(SelectCaseHomeHVO.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .setFetchMode(RMDServiceConstants.ASSET_HOME_HVO, FetchMode.EAGER)
                    .setFetchMode(RMDServiceConstants.USER_HOME_HVO, FetchMode.EAGER)
                    .setFetchMode(RMDServiceConstants.STATUS_LOOKUP, FetchMode.JOIN)
                    .setFetchMode(RMDServiceConstants.QUEUE_LOOKUP, FetchMode.EAGER)
                    .createAlias(RMDServiceConstants.ASSET_HOME_HVO, RMDServiceConstants.ASSET_VO,
                            JoinFragment.LEFT_OUTER_JOIN)
                    .createAlias(RMDServiceConstants.STATUS_LOOKUP, RMDServiceConstants.STATUS_VO)
                    .createAlias(RMDCommonConstants.USER_HOMEVO, RMDCommonConstants.USERHOME,
                            JoinFragment.LEFT_OUTER_JOIN)
                    .createAlias(RMDServiceConstants.QUEUE_LOOKUP, RMDServiceConstants.QUEUE_VO,
                            JoinFragment.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq(RMDServiceConstants.LANGUAGE_SELECTION, findCaseSerVO.getStrLanguage()));
            criteria.addOrder(Order.desc(RMDServiceConstants.CREATION_DATE));
            if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrAssetNumber())) {
                criteria.add(Restrictions.ilike(RMDServiceConstants.ASST_RN, findCaseSerVO.getStrAssetNumber()));
            }
            if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && RMDServiceConstants.FIND_ID.equalsIgnoreCase(findCaseSerVO.getStrSearchWhat())) {
                criteria.add(Restrictions.like(RMDServiceConstants.CASEID, findCaseSerVO.getStrCaseLike().toUpperCase(),
                        MatchMode.ANYWHERE));
            } else if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && RMDServiceConstants.TITLE.equalsIgnoreCase(findCaseSerVO.getStrSearchWhat())) {
                criteria.add(Restrictions.ilike(RMDServiceConstants.COLUMN_TITLE, findCaseSerVO.getStrCaseLike(),
                        MatchMode.ANYWHERE));
            } else if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.FIND).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && !RMDCommonConstants.ALL.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && RMDServiceConstants.STATUS.equalsIgnoreCase(findCaseSerVO.getStrSearchWhat())) {
                Criteria statusCriteria = getHibernateSession().createCriteria(GetSysLookupHVO.class).createAlias(
                        RMDCommonConstants.GETSYS_LOOKUP_MULTILANG, RMDCommonConstants.MULTILANG,
                        JoinFragment.LEFT_OUTER_JOIN);
                statusCriteria.add(
                        Restrictions.eq(RMDServiceConstants.LIST_NAME, RMDServiceConstants.CREATE_CASE_LOOKUP_STATE));
                statusCriteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
                statusCriteria
                        .add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG, findCaseSerVO.getStrUserLanguage()));
                statusCriteria.add(
                        Restrictions.ilike(RMDCommonConstants.MULTILANG_DISPLAY_NAME, findCaseSerVO.getStrCaseLike()));
                statusCriteria.setFetchSize(1);
                List statusList = statusCriteria.list();
                if (RMDCommonUtility.isCollectionNotEmpty(statusList)) {
                    criteria.add(Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL,
                            ((GetSysLookupHVO) statusList.get(0)).getLookValue()));
                } else {
                    criteria.add(Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL, RMDCommonConstants.EMPTY_STRING));
                }
            }
            // strCaseLike - empty and strCaseWhat - OPEN - add code here
            else if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.DWQ).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && RMDServiceConstants.STATUS.equalsIgnoreCase(findCaseSerVO.getStrSearchWhat())) {
                Criteria statusCriteria = getHibernateSession().createCriteria(GetSysLookupHVO.class).createAlias(
                        RMDCommonConstants.GETSYS_LOOKUP_MULTILANG, RMDCommonConstants.MULTILANG,
                        JoinFragment.LEFT_OUTER_JOIN);
                statusCriteria.add(
                        Restrictions.eq(RMDServiceConstants.LIST_NAME, RMDServiceConstants.CREATE_CASE_LOOKUP_STATE));
                statusCriteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
                statusCriteria
                        .add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG, findCaseSerVO.getStrUserLanguage()));
                statusCriteria.add(
                        Restrictions.ilike(RMDCommonConstants.MULTILANG_DISPLAY_NAME, findCaseSerVO.getStrCaseLike()));
                statusCriteria.setFetchSize(1);
                List statusList = statusCriteria.list();
                if (RMDCommonUtility.isCollectionNotEmpty(statusList)) {
                    criteria.add(Restrictions.or(
                            Restrictions.and(
                                    Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL,
                                            ((GetSysLookupHVO) statusList.get(0)).getLookValue()),
                                    Restrictions.eq(RMDServiceConstants.QUEUE_VO_NAME, findCaseSerVO.getStrQueue())),
                            Restrictions.isNotNull(RMDCommonConstants.USERHOME_USERID)));
                } else {
                    criteria.add(Restrictions.or(
                            Restrictions.and(
                                    Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL,
                                            RMDCommonConstants.EMPTY_STRING),
                                    Restrictions.eq(RMDServiceConstants.QUEUE_VO_NAME, findCaseSerVO.getStrQueue())),
                            Restrictions.isNotNull(RMDCommonConstants.USERHOME_USERID)));
                }
            } else if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.HOME).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrCaseLike())
                    && RMDServiceConstants.STATUS.equalsIgnoreCase(findCaseSerVO.getStrSearchWhat())) {
                Criteria statusCriteria = getHibernateSession().createCriteria(GetSysLookupHVO.class).createAlias(
                        RMDCommonConstants.GETSYS_LOOKUP_MULTILANG, RMDCommonConstants.MULTILANG,
                        JoinFragment.LEFT_OUTER_JOIN);
                statusCriteria.add(
                        Restrictions.eq(RMDServiceConstants.LIST_NAME, RMDServiceConstants.CREATE_CASE_LOOKUP_STATE));
                statusCriteria.add(Restrictions.eq(RMDServiceConstants.LOOK_STATE, RMDServiceConstants.ACTIVE));
                statusCriteria
                        .add(Restrictions.eq(RMDCommonConstants.MULTILANG_LANG, findCaseSerVO.getStrUserLanguage()));
                statusCriteria.add(
                        Restrictions.ilike(RMDCommonConstants.MULTILANG_DISPLAY_NAME, findCaseSerVO.getStrCaseLike()));
                statusCriteria.setFetchSize(1);
                List statusList = statusCriteria.list();
                if (RMDCommonUtility.isCollectionNotEmpty(statusList)) {
                    criteria.add(Restrictions.and(
                            Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL,
                                    ((GetSysLookupHVO) statusList.get(0)).getLookValue()),
                            Restrictions.eq(RMDCommonConstants.USERHOME_USERID, findCaseSerVO.getStrUserId())));
                } else {
                    criteria.add(Restrictions.and(
                            Restrictions.eq(RMDServiceConstants.STATUS_LOOKVAL, RMDCommonConstants.EMPTY_STRING),
                            Restrictions.eq(RMDCommonConstants.USERHOME_USERID, findCaseSerVO.getStrUserId())));
                }
            }
            if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.FIND).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && findCaseSerVO.getStrQueue() != null
                    && !RMDServiceConstants.ALL.equalsIgnoreCase(findCaseSerVO.getStrQueue())) {
                criteria.add(Restrictions.eq(RMDServiceConstants.QUEUE_VO_NAME, findCaseSerVO.getStrQueue()));
            }
            if (null != findCaseSerVO.getSelectedFromDate() && null != findCaseSerVO.getSelectedToDate()) {
                criteria.add(Restrictions.between(RMDServiceConstants.CREATION_DATE,
                        findCaseSerVO.getSelectedFromDate(), findCaseSerVO.getSelectedToDate()));
            } else if (null != findCaseSerVO.getSelectedFromDate() && null == findCaseSerVO.getSelectedToDate()) {
                criteria.add(Restrictions.gt(RMDServiceConstants.CREATION_DATE, findCaseSerVO.getSelectedFromDate()));
            } else if (null == findCaseSerVO.getSelectedFromDate() && null != findCaseSerVO.getSelectedToDate()) {
                criteria.add(Restrictions.le(RMDServiceConstants.CREATION_DATE, findCaseSerVO.getSelectedToDate()));
            }
            if (null != findCaseSerVO.getStrPage()
                    && RMDCommonConstants.FIND.equalsIgnoreCase(findCaseSerVO.getStrPage())) {
                if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrRxTitle())
                        || !RMDCommonConstants.ALL.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())) {
                    criteria.createAlias(RMDServiceConstants.RECOM_DELV_HVO, RMDServiceConstants.RECOM_DELV,
                            JoinFragment.INNER_JOIN)
                            .createAlias(RMDCommonConstants.RECOMDELV_GETRXSTATUS, RMDServiceConstants.RX_STATUS,
                                    JoinFragment.LEFT_OUTER_JOIN)
                            .createAlias(RMDCommonConstants.RECOMDELV_GETCMRECOM_EXE, RMDServiceConstants.RX_EXECUTION,
                                    JoinFragment.INNER_JOIN)
                            .createAlias(RMDServiceConstants.RECOM_HVO, RMDServiceConstants.RECOM,
                                    JoinFragment.LEFT_OUTER_JOIN);
                }
            }
            if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.FIND).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrRxTitle())) {
                criteria.add(Restrictions.ilike(RMDServiceConstants.RECOM_TITLE, findCaseSerVO.getStrRxTitle(),
                        MatchMode.ANYWHERE));
            }
            if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.FIND).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())
                    && RMDServiceConstants.OPEN.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())) {
                criteria.add(Restrictions.isNull(RMDServiceConstants.RX_CLOSEDATE));

            } else if (null != findCaseSerVO.getStrPage()
                    && (RMDCommonConstants.FIND).equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())
                    && RMDServiceConstants.CLOSED.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())) {
                criteria.add(Restrictions.isNotNull(RMDServiceConstants.RX_CLOSEDATE));
            } else if (null != findCaseSerVO.getStrPage()
                    && RMDCommonConstants.FIND.equalsIgnoreCase(findCaseSerVO.getStrPage())
                    && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())
                    && RMDServiceConstants.EXECUTED.equalsIgnoreCase(findCaseSerVO.getStrRxStatus())) {
                criteria.add(Restrictions.isNotNull(RMDServiceConstants.RX_EXEC));
                criteria.add(Restrictions.isNull(RMDServiceConstants.RX_CLOSEDATE));
            }
            arlSearchRes = criteria.list();
            arlFinalResults = new ArrayList();
            int size = arlSearchRes.size();
            if (size > 0) {
                Map<Object, String> priorityMap = getDisplayNameMap(RMDServiceConstants.CASE_LOOKUP_PRIORITY,
                        findCaseSerVO.getStrUserLanguage(), null);
                Map<Object, String> caseReasonMap = getDisplayNameMap(RMDServiceConstants.CASE_LOOKUP_REASON,
                        findCaseSerVO.getStrUserLanguage(), null);
                Map<Object, String> statusMap = getDisplayNameMap(RMDServiceConstants.CASE_LOOKUP_STATUS,
                        findCaseSerVO.getStrUserLanguage(), null);

                for (int i = 0; i < size; i++) {
                    SelectCaseHomeHVO objSelectCaseHomeHVO = (SelectCaseHomeHVO) arlSearchRes.get(i);
                    objSelectCase = new SelectCaseHomeVO();
                    objSelectCase.setDtHistDate(objSelectCaseHomeHVO.getActivityDate());
                    objSelectCase.setLngCmCaseSeqId(objSelectCaseHomeHVO.getCmCaseSeqId());
                    objSelectCase.setStrCaseId(objSelectCaseHomeHVO.getCaseId());
                    objSelectCase.setStrTitle(objSelectCaseHomeHVO.getTitle());
                    objSelectCase.setDtCreationDate(objSelectCaseHomeHVO.getCreationDate());
                    // objSelectCase.setLongAssetNumber(objSelectCaseHomeHVO
                    // .getAssetHomeHVO().getLongAssetNumber());
                    /*
                     * objSelectCase.setStrAge(RMDCommonUtility
                     * .calculateAge(objSelectCaseHomeHVO.getActivityDate()));
                     */
                    if (objSelectCaseHomeHVO.getAssetHomeHVO() != null
                            && objSelectCaseHomeHVO.getAssetHomeHVO().getCustomerHomeHVO() != null
                            && objSelectCaseHomeHVO.getAssetHomeHVO().getCustomerHomeHVO().getName() != null) {
                        objSelectCase.setStrcustomerName(
                                objSelectCaseHomeHVO.getAssetHomeHVO().getCustomerHomeHVO().getName());
                    } else {
                        objSelectCase.setStrcustomerName(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (objSelectCaseHomeHVO.getAssetHomeHVO() != null
                            && objSelectCaseHomeHVO.getAssetHomeHVO().getAssetGroupHomeHVO() != null
                            && objSelectCaseHomeHVO.getAssetHomeHVO().getAssetGroupHomeHVO().getName() != null) {
                        objSelectCase.setStrAssetHeader(
                                objSelectCaseHomeHVO.getAssetHomeHVO().getAssetGroupHomeHVO().getName());
                    } else {
                        objSelectCase.setStrAssetHeader(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (objSelectCaseHomeHVO.getAssetHomeHVO() != null
                            && objSelectCaseHomeHVO.getAssetHomeHVO().getRoadNumber() != null) {
                        objSelectCase.setStrAssetNumber(objSelectCaseHomeHVO.getAssetHomeHVO().getRoadNumber());
                    } else {
                        objSelectCase.setStrAssetNumber(RMDCommonConstants.EMPTY_STRING);
                    }
                    objSelectCase.setStrPriority(priorityMap.get(objSelectCaseHomeHVO.getPriority()));
                    objSelectCase.setStrStatus(statusMap.get(objSelectCaseHomeHVO.getStatus()));
                    if (objSelectCaseHomeHVO.getUsersHomeCreatorVO() != null
                            && objSelectCaseHomeHVO.getUsersHomeCreatorVO().getUserId() != null) {
                        objSelectCase.setStrCreator(objSelectCaseHomeHVO.getUsersHomeCreatorVO().getUserId());
                    } else {
                        objSelectCase.setStrCreator(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (objSelectCaseHomeHVO.getUsersHomeVO() != null
                            && objSelectCaseHomeHVO.getUsersHomeVO().getUserId() != null) {
                        objSelectCase.setStrOwner(objSelectCaseHomeHVO.getUsersHomeVO().getUserId());
                    } else {
                        objSelectCase.setStrOwner(RMDCommonConstants.EMPTY_STRING);
                    }
                    if (objSelectCaseHomeHVO.getQueueLookup() != null
                            && objSelectCaseHomeHVO.getQueueLookup().getDisplayName() != null) {
                        objSelectCase.setStrQueue(objSelectCaseHomeHVO.getQueueLookup().getName());
                    } else {
                        objSelectCase.setStrQueue(RMDServiceConstants.EMPTY_TEXT);
                    }
                    objSelectCase.setStrReason(caseReasonMap.get(objSelectCaseHomeHVO.getCaseReason()));
                    arlFinalResults.add(objSelectCase);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl findCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
            arlSearchRes = null;
        }
        return arlFinalResults;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.FindCaseDAOIntf#getRxTitles(
     * com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    public List getRxTitles(FindCaseServiceVO objFindCaseServiceVO) throws RMDDAOException {
        Session session = null;
        List arlRxTitles = null;
        List arlResult = null;
        try {
            session = getHibernateSession(objFindCaseServiceVO.getStrUserName());
            session.flush();
            session.clear();
            // To get GetCmRecomDelvHVO Object
            Criteria caseCriteria = session.createCriteria(GetCmRecomDelvHVO.class)
                    .setResultTransformer(Criteria.ROOT_ENTITY)
                    .setFetchMode(RMDServiceConstants.GETCMCASE, FetchMode.JOIN)
                    .createAlias(RMDServiceConstants.GETCMCASE, RMDServiceConstants.CASE_ALIAS);
            caseCriteria.add(Restrictions.eq(RMDServiceConstants.CASE_SEQ_ID,
                    Long.valueOf(objFindCaseServiceVO.getStrCaseId())));
            arlRxTitles = caseCriteria.list();
            arlResult = new ArrayList();
            int iRxTitleSize = arlRxTitles.size();
            for (int i = 0; i < iRxTitleSize; i++) {
                GetCmRecomDelvHVO objCmRecomDelvHVO = (GetCmRecomDelvHVO) arlRxTitles.get(i);
                FindCaseRxTitlesServiceVO objServiceVO = new FindCaseRxTitlesServiceVO();
                // objServiceVO.setStrRxTitle(objCmRecomDelvHVO.getGetKmRecom()
                // .getTitle());
                arlResult.add(objServiceVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFindCaseServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getRxTitles()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RX_TITLE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFindCaseServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlRxTitles = null;
        }
        return arlResult;
    }

    /*
     * This method is fetching the list of cases based on queueName and
     * solutionStatus. The tables involved in fetching the data from database
     * are table_case, table_site_part, gets_sd_case_recom gets_sd_recom
     * gets_sd_recom_delv gets_sd_cust_fdbk GETS_RMD_VEHICLE GETS_RMD_VEH_HDR
     * table_bus_org table_user table_user table_act_entry table_gbst_elm
     */
    @Override
    @SuppressWarnings("deprecation")
    public List<SelectCaseHomeVO> getCases(final FindCaseServiceVO findCaseSerVO) throws RMDDAOException {

        List arlFinalResults = null;
        Session session = null;

        SelectCaseHomeVO objSelectCase, tmpobjSelectCase;
        RecomDelvServiceVO rxDelServiceVO;
        List<RecomDelvServiceVO> rxDelServiceVOLst = new ArrayList<RecomDelvServiceVO>();
        List<RecomDelvServiceVO> tmpDelServiceLst = null;
        List<SelectCaseHomeVO> selectCaseList = new ArrayList<SelectCaseHomeVO>();

        List<String> caseIdList = new ArrayList<String>();
        Map<String, SelectCaseHomeVO> selectcaseMap = new LinkedHashMap<String, SelectCaseHomeVO>();
        String language = findCaseSerVO.getStrUserLanguage();

        /*
         * if(null != language && language.equals(RMDCommonConstants.LANG_PT)){
         * language = RMDCommonConstants.LANG_PORTUGUESE; }
         */
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            long startTime = System.currentTimeMillis();
            // Sprint15 - changes start
            if (findCaseSerVO.getStrRxStatus().equalsIgnoreCase(RMDCommonConstants.OPEN)) {
                // Sprint15 - changes end
                StringBuilder Query = new StringBuilder();
                Query.append("SELECT c.id_number,");
                Query.append("c.title casetitle,");
                Query.append("TO_CHAR(c.creation_time,'mm/dd/yyyy hh24:mi:ss') case_time,");
                Query.append("recom.title rx_title,");
                Query.append("caserecom.urgency urgency,");
                Query.append("caserecom.est_repair_time repair_time ,");
                Query.append("delv.file_path || '/' ||delv.file_name file_name,");
                Query.append("recom.loco_impact loco_impact,");
                Query.append("delv.recom_notes,");
                Query.append("recom.objid recomid,");
                Query.append("fdbk.rx_case_id,");
                Query.append("tbo.org_id,");
                Query.append("tsp.x_veh_hdr,");
                Query.append("tsp.serial_no,");
                Query.append("TO_CHAR(delv.creation_date,'mm/dd/yyyy hh24:mi:ss') rxdevdate,");
                Query.append("TO_CHAR(fdbk.rx_close_date,'mm/dd/yyyy hh24:mi:ss') rxclosedate,");
                Query.append(" delv.objid rxdelvid,");
                Query.append(" usrowner.login_name owner,");
                Query.append(" usrcreator.login_name creator,");
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    Query.append(" g1.title reason,");
                }
                Query.append(" g2.title priority,");
                Query.append(" decode(fdbk.rx_close_date,null,'Open','Closed') rxstatus,");
                Query.append(" g3.title casestatus,");
                Query.append(" casetype.title casetype,");
                Query.append(" MDL.MODEL_NAME modelname,");
                Query.append(" grf.fleet_number fleetname");
                // for Multinlingual Rx title and LocoImpact
                Query.append(",(SELECT DISTINCT trans_recom_title_txt  FROM gets_sd.GETS_SD_RECOM_UT8 "
                        + "WHERE (LANGUAGE_CD=(select look_value  from gets_rmd_lookup where list_name =:language) "
                        + " OR  LANGUAGE_CD=:languageCode) AND LINK_RECOM   = RECOM.OBJID ) tansTtile,");
                Query.append(" (SELECT DISTINCT TRANS_LOCO_IMPACT_TXT FROM gets_sd.GETS_SD_MSTR_LOCO_IMPACT "
                        + "WHERE LOCO_IMPACT_TXt = RECOM.loco_impact AND UPPER(ACTIVE_FLG)='Y' "
                        + " AND (LANGUAGE_CD=(select look_value  from gets_rmd_lookup where list_name =:language)"
                        + " OR  LANGUAGE_CD=:languageCode) ) transLocoImpact ");

                Query.append(" FROM gets_rmd_fleet grf,GETS_RMD_MODEL MDL,");
                Query.append(" table_gbst_elm casetype, table_case c,");
                Query.append(" table_site_part tsp,");
                Query.append(" gets_sd_case_recom caserecom,");
                Query.append(" gets_sd_recom recom,");
                Query.append(" gets_sd_recom_delv delv,");
                Query.append(" gets_sd_cust_fdbk fdbk,");
                Query.append(" GETS_RMD_VEHICLE veh,");
                Query.append(" GETS_RMD_VEH_HDR vehhdr,");
                Query.append(" table_bus_org tbo,");
                Query.append(" table_user usrowner,");
                Query.append(" table_user usrcreator,");
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    Query.append("table_act_entry a,");
                    Query.append("table_gbst_elm g1,");
                }
                Query.append("table_gbst_elm g2,");
                Query.append("table_gbst_elm g3");
                Query.append(
                        " WHERE casetype.objid      = c.calltype2gbst_elm and c.case_prod2site_part      = tsp.objid");
                Query.append(" AND caserecom.case_recom2case (+)= c.objid");
                Query.append(" AND caserecom.case_recom2recom   = recom.objid(+)");
                Query.append(" AND delv.recom_delv2case(+)      = c.objid");
                Query.append(" AND fdbk.cust_fdbk2case          = c.objid");
                Query.append(" AND delv.recom_delv2cust_fdbk    = fdbk.objid");
                Query.append(" AND delv.recom_delv2recom        =recom.objid");
                Query.append(" AND tsp.serial_no NOT LIKE '%BAD%'");
                Query.append(" AND delv.creation_date           =");
                Query.append("(SELECT MAX(creation_date)");
                Query.append(" FROM gets_sd_recom_delv delv1");
                Query.append(" WHERE delv1.recom_delv2cust_fdbk = fdbk.objid)");
                Query.append(" AND fdbk.rx_close_date   IS NULL");
                Query.append(" AND tsp.objid             = veh.VEHICLE2SITE_PART");
                Query.append(" AND veh.vehicle2veh_hdr   = vehhdr.objid");
                Query.append(" AND vehhdr.veh_hdr2busorg = tbo.objid");
                Query.append(" AND usrowner.objid = c.case_owner2user");
                Query.append(" AND usrcreator.objid = c.case_originator2user");
                Query.append(" AND g3.objid    = c.casests2gbst_elm");
                Query.append(" AND c.respprty2gbst_elm = g2.objid");
                Query.append(" AND VEH.VEHICLE2MODEL = MDL.OBJID");
                Query.append(" AND veh.VEHICLE2FLEET     = grf.objid");
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    Query.append(" AND a.entry_name2gbst_elm = g1.objid");

                    Query.append(" AND a.act_entry2case = c.objid");
                    Query.append(" AND a.entry_time = (");
                    Query.append("SELECT MAX(entry_time)");
                    Query.append(" FROM table_act_entry,table_gbst_elm g WHERE act_entry2case = c.objid ");
                    Query.append(" AND entry_name2gbst_elm = g.objid");
                    Query.append(" AND g.title IN (");
                    Query.append(
                            " SELECT look_value FROM GETS_RMD_LOOKUP WHERE list_name = 'DynamicWorkQ_CaseReason_List'");
                    Query.append(" AND (LOOK_STATE = 'Active' OR LOOK_STATE = 'Default')))");
                    Query.append(" AND g1.title              IN" + "(SELECT LOOK_VALUE");
                    Query.append(" FROM gets_rmd_lookup");
                    Query.append(" WHERE LIST_NAME ='DynamicWorkQ_CaseReason_List'");
                    Query.append(" AND (LOOK_STATE = 'Active' OR LOOK_STATE = 'Default'))");
                }
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    Query.append(" AND tbo. org_id = :customerID ");
                }
                // Sprint15 - changes start
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    Query.append(" AND tsp.serial_no = :assetNumber ");
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    Query.append(" AND tsp.x_veh_hdr = :assetGrpName ");
                }
                /* Filter stories phase2 sprint 1 changes Starts */
                if (null != findCaseSerVO.getEstRepairTime()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getEstRepairTime())) {
                    Query.append(" AND caserecom.est_repair_time = :estRepairTime ");
                }
                if (null != findCaseSerVO.getUrgency() && !RMDCommonUtility.checkNull(findCaseSerVO.getUrgency())) {
                    Query.append(" AND caserecom.urgency in (:urgency) ");
                }
                /* Filter stories phase2 sprint 1 changes Ends */

                /* Filter stories phase2 sprint 2 changes Starts */
                if (null != findCaseSerVO.getRxIds() && !RMDCommonUtility.checkNull(findCaseSerVO.getRxIds())) {
                    Query.append(" AND recom.objid in (:rxTitleIds) ");
                }
                /* Filter stories phase2 sprint 2 changes Ends */
                /* For caseType filter */
                if (null != findCaseSerVO.getCaseType() && !RMDCommonUtility.checkNull(findCaseSerVO.getCaseType())) {
                    Query.append(" and casetype.objid in (:caseType) ");
                }
                /* For caseType filter */

                /* For model & fleets filter */
                if (null != findCaseSerVO.getModelId() && !RMDCommonUtility.checkNull(findCaseSerVO.getModelId())) {
                    Query.append(" AND MDL.OBJID in (:modelId) ");
                }

                if (null != findCaseSerVO.getFleetId() && !RMDCommonUtility.checkNull(findCaseSerVO.getFleetId())) {
                    Query.append("  AND grf.objid in (:fleetId) ");
                }
                /* For model & fleets filter */

                // Sprint15 - changes end
                /* Product asset Config Changes */
                // Adding assetId where clause to get only the product
                // configured assets
                // Adding assetId where clause to get only the product
                // configured assets
                if (null != findCaseSerVO.getProducts() && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                    if (!findCaseSerVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                        Query.append(" AND TSP.OBJID in (" + getProductQuery(findCaseSerVO.getStrCustomerId()) + ")");
                    }
                }

                /* Product asset Config Changes */
                Query.append(" ORDER BY case_time DESC");

                if (null != session) {
                    Query hibernateQuery = session.createSQLQuery(Query.toString());
                    if (null != findCaseSerVO.getStrCustomerId()
                            && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                        hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                        if (null != findCaseSerVO.getProducts()
                                && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                            if (!findCaseSerVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                                hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
                                        findCaseSerVO.getStrCustomerId());
                            }
                        }
                    }
                    // Sprint15 - Changes start
                    if (null != findCaseSerVO.getStrAssetNumber()
                            && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                        hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                    }
                    if (null != findCaseSerVO.getStrAssetGrpName()
                            && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                        hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
                                findCaseSerVO.getStrAssetGrpName());
                    }
                    /* Filter stories phase2 sprint 1 changes Starts */
                    if (null != findCaseSerVO.getEstRepairTime()
                            && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getEstRepairTime())) {
                        hibernateQuery.setParameter(RMDCommonConstants.EST_REP_TIME, findCaseSerVO.getEstRepairTime());
                    }
                    if (null != findCaseSerVO.getUrgency() && !RMDCommonUtility.checkNull(findCaseSerVO.getUrgency())) {
                        hibernateQuery.setParameterList(RMDCommonConstants.URGENCY_PARAM, findCaseSerVO.getUrgency());
                    }
                    /* Filter stories phase2 sprint 1 changes Ends */

                    /* Filter stories phase2 sprint 2 changes Starts */
                    if (null != findCaseSerVO.getRxIds() && !RMDCommonUtility.checkNull(findCaseSerVO.getRxIds())) {
                        hibernateQuery.setParameterList(RMDCommonConstants.RX_TITLE_IDS, findCaseSerVO.getRxIds());
                    }
                    /* Filter stories phase2 sprint 2 changes Ends */

                    /* For caseType filter */
                    if (null != findCaseSerVO.getCaseType()
                            && !RMDCommonUtility.checkNull(findCaseSerVO.getCaseType())) {
                        hibernateQuery.setParameterList(RMDCommonConstants.PARAM_CASE_TYPE,
                                findCaseSerVO.getCaseType());
                    }
                    /* For caseType filter */

                    /* For model & fleets filter */
                    if (null != findCaseSerVO.getModelId() && !RMDCommonUtility.checkNull(findCaseSerVO.getModelId())) {
                        hibernateQuery.setParameterList(RMDCommonConstants.MODELID, findCaseSerVO.getModelId());
                    }

                    if (null != findCaseSerVO.getFleetId() && !RMDCommonUtility.checkNull(findCaseSerVO.getFleetId())) {
                        hibernateQuery.setParameterList(RMDCommonConstants.FLEET_ID, findCaseSerVO.getFleetId());
                    }

                    /* For model & fleets filter */

                    /* Added for Product Asset Configuration */
                    if (null != findCaseSerVO.getProducts()
                            && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                        if (!findCaseSerVO.getProducts().contains(RMDCommonConstants.DEFAULT_STRING)) {
                            if (null != findCaseSerVO.getProducts()
                                    && !RMDCommonUtility.checkNull(findCaseSerVO.getProducts())) {
                                hibernateQuery.setParameterList(RMDCommonConstants.PRODUCT_CONF_NAME_LST,
                                        findCaseSerVO.getProducts());
                            }
                        }
                    }

                    // Multilingual RX Title and locoImpact
                    if (null != language) {
                        hibernateQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_" + language.toUpperCase());
                        hibernateQuery.setString(RMDCommonConstants.LANGUAGECODE, language);
                    } else {
                        hibernateQuery.setString(RMDCommonConstants.LANGUAGE, "LANG_EN");
                        hibernateQuery.setString(RMDCommonConstants.LANGUAGECODE, "en");
                    }

                    /* Added for Product Asset Configuration */
                    // Sprint15 - Changes end
                    arlFinalResults = hibernateQuery.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlFinalResults)) {
                        DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
                        /* Storing the data in VO */
                        for (int idx = 0; idx < arlFinalResults.size(); idx++) {
                            objSelectCase = new SelectCaseHomeVO();
                            Object caseData[] = (Object[]) arlFinalResults.get(idx);
                            // added for Rx Multilingual Rx and locoImpact
                            String transTitle = (String) caseData[caseData.length - 2];
                            String locoImpact = (String) caseData[caseData.length - 1];

                            objSelectCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[0]));
                            objSelectCase.setStrTitle(RMDCommonUtility.convertObjectToString(caseData[1]));
                            objSelectCase.setStrUrgency(RMDCommonUtility.convertObjectToString(caseData[4]));
                            objSelectCase.setStrAssetNumber(RMDCommonUtility.convertObjectToString(caseData[13]));
                            objSelectCase.setStrOwner(RMDCommonUtility.convertObjectToString(caseData[17]));
                            objSelectCase.setStrCreator(RMDCommonUtility.convertObjectToString(caseData[18]));

                            rxDelServiceVO = new RecomDelvServiceVO();

                            if (null != findCaseSerVO.getStrAssetGrpName()
                                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {

                                objSelectCase.setStrReason(RMDCommonUtility.convertObjectToString(caseData[19]));
                                objSelectCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[20]));
                                rxDelServiceVO.setStrRxStatus(RMDCommonUtility.convertObjectToString(caseData[21]));
                                objSelectCase.setStrStatus(RMDCommonUtility.convertObjectToString(caseData[22]));
                            } else {
                                /*
                                 * Commented to remove reason colom on
                                 * Technician case screen as a part of EOA Query
                                 * Fine tuning
                                 */
                                objSelectCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[19]));
                                rxDelServiceVO.setStrRxStatus(RMDCommonUtility.convertObjectToString(caseData[20]));
                                objSelectCase.setStrStatus(RMDCommonUtility.convertObjectToString(caseData[21]));
                            }

                            /* Checking for dates datatype */
                            if (null != caseData[2] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[2])) {
                                Date createdDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[2]));
                                objSelectCase.setDtCreationDate(createdDate);
                            }

                            objSelectCase.setStrcustomerName(RMDCommonUtility.convertObjectToString(caseData[11]));
                            // Sprint 22:EOA Fleet View Tooltip
                            objSelectCase.setStrGrpName(RMDCommonUtility.convertObjectToString(caseData[12]));

                            // rxDelServiceVO = new RecomDelvServiceVO();
                            rxDelServiceVO.setStrRxCaseId(RMDCommonUtility.convertObjectToString(caseData[10]));
                            rxDelServiceVO.setStrRxStatus(RMDCommonUtility.convertObjectToString(caseData[21]));
                            // added for Rx Multilingual Rx and
                            // locoImpact------start

                            objSelectCase.setFleetName(RMDCommonUtility.convertObjectToString(caseData[24]));
                            if (null != transTitle) {
                                rxDelServiceVO.setStrRxTitle(transTitle);
                            } else {
                                rxDelServiceVO.setStrRxTitle(RMDCommonUtility.convertObjectToString(caseData[3]));
                            }
                            if (null != locoImpact) {
                                rxDelServiceVO.setLocomotiveImpact(RMDCommonUtility.convertObjectToString(locoImpact));
                            } else {
                                rxDelServiceVO.setLocomotiveImpact(RMDCommonUtility.convertObjectToString(caseData[7]));
                            }
                            // added for Rx Multilingual Rx and
                            // locoImpact---------end
                            if (null != caseData[14] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[14])) {
                                Date delvDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[14]));
                                rxDelServiceVO.setDtRxDelvDate(delvDate);
                            }
                            if (null != caseData[15] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[15])) {
                                Date closeDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[15]));
                                rxDelServiceVO.setDtRxClosedDate(closeDate);
                            }
                            rxDelServiceVO.setStrRxObjid(RMDCommonUtility.convertObjectToString(caseData[9]));
                            rxDelServiceVO.setStrUrgRepair(RMDCommonUtility.convertObjectToString(caseData[4]));
                            rxDelServiceVO.setStrEstmRepTime(RMDCommonUtility.convertObjectToString(caseData[5]));

                            rxDelServiceVO.setSolutionNotes(RMDCommonUtility.convertObjectToString(caseData[8]));

                            /*
                             * Logic for getting multiple solutionInfo from a
                             * linear data
                             */
                            if (caseIdList.contains(caseData[0]
                                    .toString())) {/*
                                                    * Adding if multiple
                                                    * solution info in case
                                                    */
                                if (selectcaseMap.containsKey(caseData[0].toString())) {

                                    tmpobjSelectCase = new SelectCaseHomeVO();
                                    tmpDelServiceLst = new ArrayList<RecomDelvServiceVO>();
                                    tmpobjSelectCase = selectcaseMap.get(caseData[0].toString());
                                    tmpDelServiceLst = tmpobjSelectCase.getArlRecomDelv();
                                    tmpDelServiceLst.add(rxDelServiceVO);
                                    tmpobjSelectCase.setArlRecomDelv(tmpDelServiceLst);
                                    selectcaseMap.put(caseData[0].toString(), tmpobjSelectCase);
                                } else {
                                    selectcaseMap.put(caseData[0].toString(), objSelectCase);
                                }

                            } else {/* Adding if single solution info in case */
                                caseIdList.add(caseData[0].toString());
                                rxDelServiceVOLst = new ArrayList<RecomDelvServiceVO>();
                                rxDelServiceVOLst.add(rxDelServiceVO);
                                objSelectCase.setArlRecomDelv(rxDelServiceVOLst);

                                if (selectcaseMap.containsKey(caseData[0].toString())) {

                                    tmpobjSelectCase = selectcaseMap.get(caseData[0].toString());
                                    tmpDelServiceLst = tmpobjSelectCase.getArlRecomDelv();
                                    tmpDelServiceLst.add(rxDelServiceVO);
                                    tmpobjSelectCase.setArlRecomDelv(tmpDelServiceLst);
                                    selectcaseMap.put(caseData[0].toString(), tmpobjSelectCase);
                                } else {
                                    selectcaseMap.put(caseData[0].toString(), objSelectCase);
                                }

                            }

                        }

                        Collection collObject = selectcaseMap.values();

                        // obtain an Iterator for Collection
                        Iterator<SelectCaseHomeVO> itr = collObject.iterator();

                        // iterate through HashMap values iterator to add the
                        // values in list
                        while (itr.hasNext()) {
                            selectCaseList.add(itr.next());
                        }

                    }
                }
            }
            /* added for Sprint17_EOAAssetCases start */
            else if ((findCaseSerVO.getStrQueue()).equalsIgnoreCase(RMDCommonConstants.DELIVERED)) {
                selectCaseList = getDeliveredCases(selectCaseList, session, findCaseSerVO);
            } else if ((findCaseSerVO.getStrCaseStatus()).equalsIgnoreCase(RMDCommonConstants.CLOSED)) {
                selectCaseList = getClosedCase(selectCaseList, session, findCaseSerVO);
            }
            /* added for Sprint17_EOAAssetCases end */
            long endTime = System.currentTimeMillis();
            LOG.debug("Inside FindCaseDAO IMPL After Query Execution and setting parameters::" + startTime
                    + "::end time" + endTime + "Diff:" + (endTime - startTime));
            return selectCaseList;
        } catch (RMDDAOConnectionException ex) {

            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);

        }

    }

    /*
     * changed for to make query asset specific for Closed
     * Cases::Sprint17_EOAAssetCases start
     */
    private List<SelectCaseHomeVO> getClosedCase(List<SelectCaseHomeVO> selectCaseList, Session session,
            FindCaseServiceVO findCaseSerVO) {

        List arlFinalResults;
        SelectCaseHomeVO objSelectCaseHomeVO = null;
        SelectCaseHomeVO objSelectCase;
        try {
            final StringBuilder query = new StringBuilder();

            query.append(
                    " SELECT table_case.objid CASE_OBJ_ID,table_case.id_number CASE_ID,table_case.title CASE_TITLE,table_gse_status.title CASE_STATUS, ");
            query.append(
                    " table_gse_type.title CASE_TYPE,table_case.creation_time CASE_CREATE_TIME,table_close_case.close_date, ");
            query.append(
                    " DECODE(UPPER(table_gse_priority.TITLE),'6-CONDITIONAL',1,'5-ESTP',2,'2-HIGH',3,'3-MEDIUM',4,'4-LOW',5) CASE_PRIORITY, ");
            query.append(
                    "  DECODE(table_gse_reason.title ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review','6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed',table_gse_reason.title) casereason, ");
            query.append(
                    " table_queue.title CASE_QUEUE,table_user.login_name CASE_OWNER,table_condition.title CASE_CONDITION ");

            query.append(
                    " FROM table_gbst_elm table_gse_type,table_gbst_elm table_gse_severity,table_gbst_elm table_gse_status,table_gbst_elm table_gse_priority,table_condition table_condition,");
            query.append(
                    " table_gbst_elm table_gse_reason,table_case, table_queue,table_user,table_site,table_site_part,table_bus_org, table_close_case,table_act_entry aentry ");

            query.append(" WHERE table_gse_type.objid = table_case.calltype2gbst_elm ");
            query.append(" AND table_gse_severity.objid  = table_case.respsvrty2gbst_elm");
            query.append(" AND table_gse_priority.objid  = table_case.RESPPRTY2GBST_ELM");
            query.append(" AND table_condition.objid = table_case.CASE_STATE2CONDITION");
            query.append(" AND table_gse_status.objid = table_case.casests2gbst_elm");
            query.append(" AND table_user.objid  = table_case.case_owner2user");
            query.append(" AND table_queue.objid (+)  = table_case.case_currq2queue");
            query.append(" AND table_site.objid  = table_case.case_reporter2site");
            query.append(" AND table_site_part.serial_no NOT LIKE '%BAD%' ");
            query.append(" AND table_site_part.objid (+) = table_case.case_prod2site_part");
            query.append(" AND table_case.objid = table_close_case.last_close2case(+)");
            query.append(" AND table_bus_org.objid = table_site.primary2bus_org");
            query.append(" AND aentry.entry_name2gbst_elm = table_gse_reason.objid ");
            query.append(" AND table_case.objid = aentry.act_entry2case ");
            query.append(" AND table_gse_reason.title IN (SELECT look_value FROM GETS_RMD_LOOKUP WHERE list_name = ");
            query.append(
                    " 'DynamicWorkQ_CaseReason_List' AND (LOOK_STATE = 'Active' OR LOOK_STATE = 'Default') and look_value ");
            query.append(
                    " in ('Create','Append','MDSC Escalation','Red Rx Review','Yellow Rx Review','White Rx Review','Recommendation Closed')) ");
            query.append(
                    "  AND aentry.entry_time = (SELECT MAX(entry_time) FROM table_act_entry,table_gbst_elm g WHERE  ");
            query.append(
                    " act_entry2case = table_case.objid AND entry_name2gbst_elm = g.objid AND g.title IN (SELECT  ");
            query.append(
                    " look_value FROM GETS_RMD_LOOKUP WHERE list_name = 'DynamicWorkQ_CaseReason_List' AND (LOOK_STATE = 'Active' OR LOOK_STATE = 'Default'))) ");

            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                query.append(" AND table_bus_org.org_id= :customerID ");
            }

            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                query.append("  AND table_site_part.serial_no = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                query.append(" AND table_site_part.x_veh_hdr  = :assetGrpName ");
            }
            query.append(" AND table_condition.title = 'Closed'");
            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(query.toString());
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }

                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }

                arlFinalResults = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlFinalResults)) {
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.yyyyMMdd);
                    /* Storing the data in VO */
                    for (int idx = 0; idx < arlFinalResults.size(); idx++) {
                        objSelectCase = new SelectCaseHomeVO();
                        Object caseData[] = (Object[]) arlFinalResults.get(idx);

                        objSelectCase = new SelectCaseHomeVO();
                        objSelectCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[1]));
                        objSelectCase.setStrTitle(RMDCommonUtility.convertObjectToString(caseData[2]));
                        objSelectCase.setStrStatus(RMDCommonUtility.convertObjectToString(caseData[3]));
                        objSelectCase.setStrCaseType(RMDCommonUtility.convertObjectToString(caseData[4]));

                        if (null != caseData[5] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[5])) {
                            Date createDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[5]));
                            objSelectCase.setDtCreationDate(createDate);
                        }
                        if (null != caseData[6] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[6])) {
                            Date closeDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[6]));
                            objSelectCase.setDtLastUpdatedDate(closeDate);
                        }
                        objSelectCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[7]));
                        objSelectCase.setStrReason(RMDCommonUtility.convertObjectToString(caseData[8]));
                        objSelectCase.setStrQueue(RMDCommonUtility.convertObjectToString(caseData[9]));
                        objSelectCase.setStrOwner(RMDCommonUtility.convertObjectToString(caseData[10]));

                        selectCaseList.add(objSelectCase);
                    }
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getClosedCase()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }

        return selectCaseList;

    }

    /*
     * changed for to make query asset specific for Closed
     * Cases::Sprint17_EOAAssetCases end
     */

    /*
     * changed for to make query asset specific for Delivered
     * Cases::Sprint17_EOAAssetCases start
     */
    private List<SelectCaseHomeVO> getDeliveredCases(List<SelectCaseHomeVO> selectCaseList, Session session,
            FindCaseServiceVO findCaseSerVO) {

        List arlFinalResults;
        SelectCaseHomeVO objSelectCaseHomeVO = null;
        SelectCaseHomeVO objSelectCase;
        try {
            final StringBuilder queryDelivered = new StringBuilder();

            queryDelivered.append(
                    " SELECT table_case.objid CASE_OBJ_ID,table_case.id_number CASE_ID,table_case.title CASE_TITLE,table_gse_status.title CASE_STATUS, ");
            queryDelivered.append(
                    " table_gse_type.title CASE_TYPE,table_case.creation_time CASE_CREATE_TIME,table_close_case.close_date, ");
            queryDelivered.append(
                    " DECODE(UPPER(table_gse_priority.TITLE),'6-CONDITIONAL',1,'5-ESTP',2,'2-HIGH',3,'3-MEDIUM',4,'4-LOW',5) CASE_PRIORITY, ");
            queryDelivered.append(
                    " DECODE(table_gse_reason.title ,'Create','1-Create','Append','3-Append','MDSC Escalation','4-MDSC Escalation','Red Rx Review','5-Red Rx Review','White Rx Review','7-White Rx Review','Yellow Rx Review','6-Yellow Rx Review','Recommendation Closed','8-Recommendation Closed',table_gse_reason.title) casereason, ");
            queryDelivered.append(
                    " table_queue.title CASE_QUEUE,table_user.login_name CASE_OWNER,table_condition.title CASE_CONDITION ");

            queryDelivered.append(" FROM table_gbst_elm table_gse_type, ");
            queryDelivered.append(" table_gbst_elm table_gse_severity, ");
            queryDelivered.append(" table_gbst_elm table_gse_status, ");
            queryDelivered.append(" table_gbst_elm table_gse_priority, ");
            queryDelivered.append(" table_gbst_elm table_gse_reason, ");
            queryDelivered.append(" table_condition table_condition, ");
            queryDelivered.append(" table_case, ");
            queryDelivered.append(" table_queue, ");
            queryDelivered.append(" table_user, ");
            queryDelivered.append(" table_site, ");
            queryDelivered.append(" table_site_part, ");
            queryDelivered.append(" table_bus_org, ");
            queryDelivered.append(" table_close_case, ");
            queryDelivered.append(" gets_sd_case_recom caserecom, ");
            queryDelivered.append("  gets_sd_recom recom, ");
            queryDelivered.append(" gets_sd_recom_delv delv, ");
            queryDelivered.append(" gets_sd_cust_fdbk fdbk, ");
            queryDelivered.append(" table_act_entry aentry ");

            queryDelivered.append(" WHERE table_gse_type.objid      = table_case.calltype2gbst_elm ");
            queryDelivered.append(" AND table_gse_severity.objid  = table_case.respsvrty2gbst_elm ");
            queryDelivered.append(" AND table_gse_priority.objid  = table_case.RESPPRTY2GBST_ELM ");
            queryDelivered.append(" AND table_condition.objid = table_case.CASE_STATE2CONDITION ");
            queryDelivered.append(" AND aentry.entry_name2gbst_elm = table_gse_reason.objid ");
            queryDelivered.append(" AND table_case.objid = aentry.act_entry2case ");
            queryDelivered.append(" AND table_gse_reason.title IN (SELECT look_value FROM GETS_RMD_LOOKUP WHERE ");
            queryDelivered.append(" list_name = 'DynamicWorkQ_CaseReason_List' AND (LOOK_STATE = 'Active' OR ");
            queryDelivered.append(
                    " LOOK_STATE = 'Default') and look_value in ('Create','Append','MDSC Escalation','Red Rx Review','Yellow Rx Review','White Rx Review','Recommendation Closed')) ");
            queryDelivered
                    .append(" AND aentry.entry_time = (SELECT MAX(entry_time) FROM table_act_entry,table_gbst_elm g  ");

            queryDelivered.append(
                    " WHERE act_entry2case = table_case.objid AND entry_name2gbst_elm = g.objid AND g.title IN  ");
            queryDelivered.append(
                    " (SELECT look_value FROM GETS_RMD_LOOKUP WHERE list_name = 'DynamicWorkQ_CaseReason_List' AND (LOOK_STATE = 'Active' OR LOOK_STATE = 'Default'))) ");
            queryDelivered.append(" AND caserecom.case_recom2case (+)= table_case.objid ");
            queryDelivered.append(" AND caserecom.case_recom2recom   = recom.objid(+) ");
            queryDelivered.append(" AND delv.recom_delv2case(+)      = table_case.objid ");
            queryDelivered.append(" AND fdbk.cust_fdbk2case          = table_case.objid ");
            queryDelivered.append(" AND delv.recom_delv2cust_fdbk    = fdbk.objid ");
            queryDelivered.append(" AND delv.recom_delv2recom        = recom.objid ");
            queryDelivered.append(" AND delv.creation_date           =(SELECT MAX(creation_date) ");
            queryDelivered.append(" FROM gets_sd_recom_delv delv1 ");
            queryDelivered.append(" WHERE delv1.recom_delv2cust_fdbk = fdbk.objid ");
            queryDelivered.append(" ) ");
            queryDelivered.append(" AND fdbk.rx_close_date   IS NULL ");
            queryDelivered.append(" AND table_gse_status.objid    = table_case.casests2gbst_elm ");
            queryDelivered.append(" AND table_user.objid          = table_case.case_owner2user ");
            queryDelivered.append(" AND table_queue.objid (+)     = table_case.case_currq2queue ");
            queryDelivered.append(" AND table_site.objid          = table_case.case_reporter2site ");
            queryDelivered.append(" AND table_site_part.serial_no NOT LIKE '%BAD%' ");
            queryDelivered.append(" AND table_site_part.objid (+) = table_case.case_prod2site_part ");
            queryDelivered.append(" AND table_case.objid = table_close_case.last_close2case(+) ");
            queryDelivered.append(" AND table_bus_org.objid = table_site.primary2bus_org ");

            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                queryDelivered.append(" AND table_bus_org.org_id= :customerID ");
            }

            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                queryDelivered.append("  AND table_site_part.serial_no = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                queryDelivered.append(" AND table_site_part.x_veh_hdr  = :assetGrpName ");
            }

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(queryDelivered.toString());
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }

                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }

                arlFinalResults = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlFinalResults)) {
                    DateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.yyyyMMdd);
                    /* Storing the data in VO */
                    for (int idx = 0; idx < arlFinalResults.size(); idx++) {
                        objSelectCase = new SelectCaseHomeVO();
                        Object caseData[] = (Object[]) arlFinalResults.get(idx);

                        objSelectCase = new SelectCaseHomeVO();
                        objSelectCase.setStrCaseId(RMDCommonUtility.convertObjectToString(caseData[1]));
                        objSelectCase.setStrTitle(RMDCommonUtility.convertObjectToString(caseData[2]));
                        objSelectCase.setStrStatus(RMDCommonUtility.convertObjectToString(caseData[3]));
                        objSelectCase.setStrCaseType(RMDCommonUtility.convertObjectToString(caseData[4]));

                        if (null != caseData[5] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[5])) {
                            Date createDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[5]));
                            objSelectCase.setDtCreationDate(createDate);
                        }
                        if (null != caseData[6] && !RMDCommonConstants.EMPTY_STRING.equals(caseData[6])) {
                            Date closeDate = formatter.parse(RMDCommonUtility.convertObjectToString(caseData[6]));
                            objSelectCase.setDtLastUpdatedDate(closeDate);
                        }
                        objSelectCase.setStrPriority(RMDCommonUtility.convertObjectToString(caseData[7]));
                        objSelectCase.setStrReason(RMDCommonUtility.convertObjectToString(caseData[8]));
                        objSelectCase.setStrQueue(RMDCommonUtility.convertObjectToString(caseData[9]));
                        objSelectCase.setStrOwner(RMDCommonUtility.convertObjectToString(caseData[10]));

                        selectCaseList.add(objSelectCase);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getDeliveredCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }

        return selectCaseList;

    }

    /*
     * changed for to make query asset specific for Delivered
     * Cases::Sprint17_EOAAssetCases start
     */

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.FindCaseDAOIntf#getLatestCases(
     * com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public List getLatestCases(final FindCaseServiceVO findCaseSerVO) throws RMDDAOException {
        List arlSearchRes;
        List arlFinalResults = null;
        Session session = null;
        String assetGrpName = findCaseSerVO.getStrAssetGrpName();
        String assetNumber = findCaseSerVO.getStrAssetNumber();
        String customerId = findCaseSerVO.getStrCustomerId();
        String language = findCaseSerVO.getStrLanguage();
        String rowNum = RMDCommonConstants.EMPTY_STRING;
        int count = RMDServiceConstants.COUNT;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            long startTime = System.currentTimeMillis();
            final StringBuilder query = new StringBuilder();

            // Query for fetching latest 3 cases
            query.append(
                    "SELECT * FROM (SELECT ci.id_number FROM TABLE_CASE ci, TABLE_SITE_PART tsp,TABLE_BUS_ORG tbo ");
            query.append("WHERE ci.case_prod2site_part   = tsp.objid and tsp.serial_no NOT LIKE '%BAD%' ");
            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                query.append(" AND tbo. org_id= :customerID ");
            }

            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                query.append("  AND tsp.serial_no = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                query.append(" AND tsp.x_veh_hdr  = :assetGrpName ");
            }

            query.append("ORDER BY ci.creation_time DESC) " + "where ROWNUM  <= :count");

            long endTime = System.currentTimeMillis();
            LOG.debug("Inside FindCaseDAO IMPL Query Execution Time reqd start::" + startTime + "::end time" + endTime
                    + "Diff:" + (endTime - startTime));

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(query.toString());
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }
                // Sprint15 - Changes start
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }
                hibernateQuery.setParameter(RMDCommonConstants.COUNT, count);

                hibernateQuery.setFetchSize(300);
                arlSearchRes = hibernateQuery.list();
                arlFinalResults = new ArrayList();
                int size = arlSearchRes.size();

                SelectCaseHomeVO objSelectCaseHomeVO = null;
                for (int i = 0; i < size; i++) {
                    Object objDataArray[];
                    objDataArray = new Object[1];
                    objDataArray[0] = arlSearchRes.get(i);
                    String strCaseId = null;

                    for (int k = 0; k < objDataArray.length; k++) {
                        if (null != objDataArray[k]) {
                            strCaseId = objDataArray[k].toString();

                        } else {
                            strCaseId = RMDCommonConstants.EMPTY_STRING;
                        }
                        objSelectCaseHomeVO = new SelectCaseHomeVO();
                        objSelectCaseHomeVO.setStrCaseId(strCaseId);
                        arlFinalResults.add(objSelectCaseHomeVO);
                    }
                }
            }
            endTime = System.currentTimeMillis();
            LOG.debug("Inside FindCaseDAO IMPL After Query Execution and setting parameters::" + startTime
                    + "::end time" + endTime + "Diff:" + (endTime - startTime));
            return arlFinalResults;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl latestCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
            arlSearchRes = null;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.FindCaseDAOIntf#
     * getLatestCaseRules(
     * com.ge.trans.rmd.services.cases.service.valueobjects.FindCaseServiceVO)
     */
    @Override
    @SuppressWarnings({ "deprecation", "rawtypes" })
    public Map getLatestCaseRules(final FindCaseServiceVO findCaseSerVO) throws RMDDAOException {
        Map<String, Map<String, String>> caseRuleMap = new HashMap();
        List arlSearchRes;
        Session session = null;
        int count = RMDServiceConstants.COUNT;
        try {
            session = getHibernateSession();
            session.flush();
            session.clear();
            final StringBuilder query = new StringBuilder();
            query.append(" SELECT CASE_OBJID , ID_NUMBER , RD.OBJID RECOM_DEL_OBJID , RULEDEF2FINRUL ");
            query.append(" , TEMP.CREATION_TIME FROM ( SELECT ID_NUMBER , CASE_OBJID , CREATION_TIME ");
            query.append(" FROM ( SELECT CI.ID_NUMBER , CI.OBJID  CASE_OBJID , CI.CREATION_TIME ");
            query.append(" FROM TABLE_CASE CI , TABLE_SITE_PART TSP , TABLE_BUS_ORG TBO WHERE ");
            query.append(" CI.CASE_PROD2SITE_PART = TSP.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            if (null != findCaseSerVO.getStrCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                query.append(" AND TBO. ORG_ID = :customerID ");
            }
            if (null != findCaseSerVO.getStrAssetNumber()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                query.append(" AND TSP.SERIAL_NO = :assetNumber ");
            }
            if (null != findCaseSerVO.getStrAssetGrpName()
                    && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                query.append(" AND TSP.X_VEH_HDR = :assetGrpName ");
            }
            query.append(" ORDER BY CI.CREATION_TIME DESC ");
            query.append(" ) X WHERE ROWNUM <= :count ) TEMP , GETS_TOOL_AR_LIST ar , GETS_TOOL_RPRLDWN rp ");
            query.append(" , GETS_TOOL_DPD_RULEDEF rd , GETS_TOOL_DPD_FINRUL FR WHERE ");
            query.append(" RULEDEF2FINRUL = FR.OBJID (+) AND RP.RPRLDWN2AR_LIST (+) = AR.OBJID ");
            query.append(" AND RP.RPRLDWN2RULE_DEFN = RD.OBJID (+) AND AR.AR_LIST2CASE(+) = TEMP.CASE_OBJID ");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(query.toString());
                if (null != findCaseSerVO.getStrCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrCustomerId())) {
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMERID, findCaseSerVO.getStrCustomerId());
                }
                if (null != findCaseSerVO.getStrAssetNumber()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetNumber())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_NUMBER, findCaseSerVO.getStrAssetNumber());
                }
                if (null != findCaseSerVO.getStrAssetGrpName()
                        && !RMDCommonConstants.EMPTY_STRING.equals(findCaseSerVO.getStrAssetGrpName())) {
                    hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME, findCaseSerVO.getStrAssetGrpName());
                }
                hibernateQuery.setParameter(RMDCommonConstants.COUNT, count);

                hibernateQuery.setFetchSize(300);
                arlSearchRes = hibernateQuery.list();

                for (int i = 0; i < arlSearchRes.size(); i++) {
                    Object caseData[] = (Object[]) arlSearchRes.get(i);
                    String strCaseId = RMDCommonUtility.convertObjectToString(caseData[1]);
                    String strRuleDefId = RMDCommonUtility.convertObjectToString(caseData[2]);
                    String strRuleId = RMDCommonUtility.convertObjectToString(caseData[3]);
                    if (caseRuleMap.get(strCaseId) == null) {
                        Map<String, String> ruleMap = new HashMap();
                        caseRuleMap.put(strCaseId, ruleMap);
                    }
                    if (strRuleDefId != null && strRuleId != null) {
                        Map<String, String> ruleMap = caseRuleMap.get(strCaseId);
                        if (ruleMap.get(strRuleDefId) == null) {
                            ruleMap.put(strRuleDefId, strRuleId);
                            caseRuleMap.put(strCaseId, ruleMap);
                        }
                    }
                }
            }
            return caseRuleMap;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl latestCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FIND_CASES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, findCaseSerVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
            arlSearchRes = null;
        }
    }

    /* This method is used to fetch the available case type from the system */
    @Override
    public List<CaseTypeVO> getCaseType(CaseTypeVO objCaseTypeVO) throws RMDDAOException {

        List<Object[]> objcaseTypeLst;
        CaseTypeVO objCaseType = null;
        List<CaseTypeVO> caseTypeLst = new ArrayList<CaseTypeVO>();
        Session session = null;
        try {
            final StringBuilder queryCaseType = new StringBuilder();
            session = getHibernateSession();
            queryCaseType.append(
                    " SELECT OBJID, TITLE FROM SA.TABLE_GBST_ELM WHERE GBST_ELM2GBST_LST = (select objid from table_gbst_lst where title=:caseType) ");
            queryCaseType.append(" AND STATE != :state ORDER BY RANK");

            if (null != session) {
                Query hibernateQuery = session.createSQLQuery(queryCaseType.toString());
                hibernateQuery.setParameter(RMDCommonConstants.PARAM_CASE_TYPE, RMDCommonConstants.CASETYPE);
                hibernateQuery.setParameter(RMDCommonConstants.STATE_VAL, RMDCommonConstants.INSERTION_SUCCESS);
                objcaseTypeLst = hibernateQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(objcaseTypeLst)) {

                    for (int idx = 0; idx < objcaseTypeLst.size(); idx++) {
                        objCaseType = new CaseTypeVO();
                        Object caseData[] = objcaseTypeLst.get(idx);

                        objCaseType.setCaseTypeId(RMDCommonUtility.convertObjectToLong(caseData[0]));
                        objCaseType.setCaseTypeTitle(RMDCommonUtility.convertObjectToString(caseData[1]));

                        caseTypeLst.add(objCaseType);
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseTypeVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in FindCaseDAOImpl getCaseType()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CASE_TYPE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objCaseTypeVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }

        return caseTypeLst;

    }
}
