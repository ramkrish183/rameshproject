/**
 * ============================================================
 * File : TestDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleTesterDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTrackingServiceResultVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.AssetHomeHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstAssetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstGroupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmDsParminfoHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdRulhisHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmTrackingAssetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetToolJdpadTrackingHVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("serial")
public class RuleTesterDAOImpl extends BaseDAO implements RuleTesterDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleTesterDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #saveRunTest
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to create a new tracking record
     * for the rule id provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int saveRunTest(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException {
        LOG.debug("Enter into saveRunTest method in RuleTesterDAOImpl");
        int iSeqID = RMDCommonConstants.INT_CONST;
        String strUserId = RMDCommonConstants.EMPTY_STRING;
        String strRuleId = RMDCommonConstants.EMPTY_STRING;
        String strLookBack = RMDCommonConstants.EMPTY_STRING;
        String strAssetHeader = RMDCommonConstants.EMPTY_STRING;
        String[] strAssetNumber;
        Session objHibernateSession = null;
        String strValidRuleQuery = RMDCommonConstants.EMPTY_STRING;
        int iValidRuleIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;

        try {
            strUserId = objRuleTesterServiceVO.getStrUserName();
            LOG.debug("*******Inside TestDAOImpl saveRunTest method**********" + strUserId);
            strRuleId = objRuleTesterServiceVO.getStrRuleID();
            strLookBack = objRuleTesterServiceVO.getStrDay();
            strAssetHeader = objRuleTesterServiceVO.getStrAssetHeader();
            strAssetNumber = objRuleTesterServiceVO.getStrAssetNumber();
            LOG.debug("*******strAssetHeader**********" + strAssetHeader);
            LOG.debug("*******strAssetNumber**********" + strAssetNumber);
            objHibernateSession = getHibernateSession(strUserId);
            /* Check whether the rule id is valid Active Rule Id */
            strValidRuleQuery = "from GetKmDpdRulhisHVO ruleHist where ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId =:ruleId";
            hibQuery = objHibernateSession.createQuery(strValidRuleQuery);
            hibQuery.setString(RMDServiceConstants.RULE_ID, strRuleId);
            // hibQuery.setInteger(RMDServiceConstants.ACTIVE_FLAG, 1); // 1
            // denotes active rule
            ArrayList arlRuleId = (ArrayList) hibQuery.list();
            iValidRuleIdList = arlRuleId.size();
            LOG.debug("Size of RuleId --------->" + iValidRuleIdList);
            if (iValidRuleIdList > 0) {
                GetSysLookupHVO objGetSysLookup = null;
                objGetSysLookup = getParentLookupHVO(RMDServiceConstants.JDPAD_TESTER_STATUS,
                        RMDServiceConstants.RUNNING, objRuleTesterServiceVO.getStrLanguage());
                LOG.debug("********objGetSysLookup status***************" + objGetSysLookup.getGetSysLookupSeqId());
                GetToolJdpadTrackingHVO objToolsJdpadTrackingHVO = new GetToolJdpadTrackingHVO();
                objToolsJdpadTrackingHVO.setRuleId(Long.valueOf(strRuleId));
                objToolsJdpadTrackingHVO.setLookback(Long.valueOf(strLookBack));
                objToolsJdpadTrackingHVO.setStatus(objGetSysLookup.getGetSysLookupSeqId());
                objHibernateSession.save(objToolsJdpadTrackingHVO);
                objHibernateSession.flush();
                if (strAssetNumber != null) {
                    for (int index = 0; index < strAssetNumber.length; index++) {
                        Criteria assetCriteria = objHibernateSession.createCriteria(GetAsstAssetHVO.class);
                        assetCriteria.add(Restrictions.eq(RMDServiceConstants.ASSETNUMBER, strAssetNumber[index]));
                        ArrayList arlAssetNumber = (ArrayList) assetCriteria.list();
                        GetAsstAssetHVO objGetAsstAsset = null;
                        if (null != arlAssetNumber && !arlAssetNumber.isEmpty()) {
                            objGetAsstAsset = (GetAsstAssetHVO) arlAssetNumber.get(0);
                        }
                        LOG.debug("********arlAssetNumber.size()***************" + arlAssetNumber.size());
                        GetKmTrackingAssetHVO objGetKmTrackingAsset = new GetKmTrackingAssetHVO();
                        objGetKmTrackingAsset.setGetToolJdpadTracking(objToolsJdpadTrackingHVO);
                        objGetKmTrackingAsset.setGetAsstAsset(objGetAsstAsset);
                        objHibernateSession.save(objGetKmTrackingAsset);
                        objHibernateSession.flush();
                    }
                }
                iSeqID = objToolsJdpadTrackingHVO.getGetToolJdpadTrackingSeqId().intValue();
                LOG.debug("********JDPAD Tracking id in DAO***************" + iSeqID);
            } else {
                LOG.debug("InValid RuleId --------->");
                iSeqID = RMDCommonConstants.INT_CONST;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RUN_TEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRuleTesterServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RUN_TEST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objRuleTesterServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End into saveRunTest method in RuleTesterDAOImpl");
        return iSeqID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #fetchAssetHeader
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to fetch the asset header
     * suggestion list from database based on keyword provided
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fetchAssetHeader(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException {
        LOG.debug("Enter into fetchAssetHeader method in RuleTesterDAOImpl");
        ArrayList arlAssetHeader;
        ArrayList lstAssetHeader;
        int iAssetHeaderList = RMDCommonConstants.INT_CONST;
        Session objHibernateSession = null;
        GetAsstGroupHVO objGetAsstGroup = null;
        try {
            objHibernateSession = getHibernateSession();
            lstAssetHeader = new ArrayList();
            LOG.debug("*******Inside TestDAOImpl fetchAssetHeader**************");
            Criteria criteria = objHibernateSession.createCriteria(GetAsstGroupHVO.class);
            arlAssetHeader = (ArrayList) criteria.list();
            iAssetHeaderList = arlAssetHeader.size();
            LOG.debug("Size of AssetHeader --------->" + iAssetHeaderList);
            ElementVO objElementVO = null;
            objElementVO = new ElementVO();
            for (int index = 0; index < iAssetHeaderList; index++) {
                objGetAsstGroup = (GetAsstGroupHVO) arlAssetHeader.get(index);
                objElementVO = new ElementVO();
                objElementVO.setId(objGetAsstGroup.getGetAsstGroupSeqId().toString());
                objElementVO.setName(objGetAsstGroup.getName());
                lstAssetHeader.add(objElementVO);
            }
            objRuleTesterServiceVO.setAlAssetHeaderList(lstAssetHeader);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_HEADER, e);
        } finally {
            releaseSession(objHibernateSession);
            objGetAsstGroup = null;
            lstAssetHeader = null;
            arlAssetHeader = null;
        }
        LOG.debug("End into fetchAssetHeader method in RuleTesterDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #fetchAssetNumber
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterServiceVO) This method is used to fetch the asset number
     * suggestion list from database based on keyword provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fetchAssetNumber(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException {
        LOG.debug("Enter into fetchAssetNumber method in RuleTesterDAOImpl");
        ArrayList arlAssetNumber;
        ArrayList lstAssetNumber;
        int iAssetNumberList = RMDCommonConstants.INT_CONST;
        Session objHibernateSession = null;
        AssetHomeHVO objAssetHomeVO = null;
        try {
            objHibernateSession = getHibernateSession();
            lstAssetNumber = new ArrayList();
            ElementVO objElementVO = null;
            String strSelAssetHeader = RMDServiceConstants.QTR_CUSTOMER;
            LOG.debug("*******Inside TestDAOImpl fetchAssetNumber strSelAssetHeader**************" + strSelAssetHeader);
            if (RMDCommonConstants.SELECT.equalsIgnoreCase(strSelAssetHeader)) {
                objRuleTesterServiceVO.getAlAssetNumberList().clear();
                objElementVO = new ElementVO();
                objElementVO.setId(RMDCommonConstants.SELECT);
                objElementVO.setName(RMDCommonConstants.SELECT);
                lstAssetNumber.add(objElementVO);
                objRuleTesterServiceVO.setAlAssetNumberList(lstAssetNumber);
            } else {
                objRuleTesterServiceVO.getAlAssetNumberList().clear();
                Criteria criteria = objHibernateSession.createCriteria(AssetHomeHVO.class);
                criteria.setFetchMode(RMDServiceConstants.ASSET_GRP_HOME, FetchMode.JOIN);
                criteria.createAlias(RMDServiceConstants.ASSET_GRP_HOME, RMDServiceConstants.ASSET_GRP_ALIAS);
                criteria.add(Restrictions.eq("assetGroup.assetGroupSeqId", Long.valueOf(strSelAssetHeader)));
                criteria.addOrder(Order.asc(RMDServiceConstants.ROADNUMBER));
                arlAssetNumber = (ArrayList) criteria.list();
                iAssetNumberList = arlAssetNumber.size();
                LOG.debug("Size of AssetNumber --------->" + iAssetNumberList);
                for (int index = 0; index < iAssetNumberList; index++) {
                    objAssetHomeVO = (AssetHomeHVO) arlAssetNumber.get(index);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objAssetHomeVO.getAssetSeqId().toString());
                    objElementVO.setName(objAssetHomeVO.getRoadNumber());
                    lstAssetNumber.add(objElementVO);
                }
                objRuleTesterServiceVO.setAlAssetNumberList(lstAssetNumber);
            }
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_ASSET_NOS, e);
        } finally {
            releaseSession(objHibernateSession);
            objAssetHomeVO = null;
            arlAssetNumber = null;
            lstAssetNumber = null;
        }
        LOG.debug("End into fetchAssetNumber method in RuleTesterDAOImpl");
    }

    // This is used to sort the values of asset number by converting it into
    // integer
    /**
     *
     */
    @SuppressWarnings("unchecked")
    public static final Comparator ASSETNUMBERCAMPARATOR = new Comparator() {
        /**
         * @Author:
         * @param objFirst
         * @param objSecond
         * @return
         * @Description:
         */
        @Override
        public int compare(Object objFirst, Object objSecond) {
            Integer sortOrderFirst = 0;
            Integer sortOrderSecond = 0;
            ElementVO objElementVOFirst = (ElementVO) objFirst;
            ElementVO objElementVOSecond = (ElementVO) objSecond;
            String elementVOFirst = objElementVOFirst.getName();
            sortOrderFirst = Integer.valueOf(elementVOFirst);
            String elementVOSecond = objElementVOSecond.getName();
            sortOrderSecond = Integer.valueOf(elementVOSecond);
            return sortOrderFirst.compareTo(sortOrderSecond);
        }
    };

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf#
     * fetchRuleId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTesterServiceVO) This method is used to fetch the rule id suggestion
     * list from database based on keyword provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fetchRuleId(RuleTesterServiceVO objRuleTesterServiceVO) throws RMDDAOException {
        LOG.debug("Enter into fetchRuleId method in RuleTesterDAOImpl");
        ArrayList arlRuleId;
        ArrayList lstRuleId;
        String strRuleIdKey = RMDCommonConstants.EMPTY_STRING;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iRuleIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        Session objHibernateSession = null;
        GetKmDpdRulhisHVO objGetKmDpdRulhis = null;
        try {
            objHibernateSession = getHibernateSession();
            lstRuleId = new ArrayList();
            strRuleIdKey = objRuleTesterServiceVO.getStrRuleID();
            LOG.debug("*******Inside TestDAOImpl fetchRuleId**************" + strRuleIdKey);
            strQuery = "from GetKmDpdRulhisHVO ruleHist where ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId like :ruleIdKey";
            strQuery = strQuery + " order by ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId asc";
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
            // hibQuery.setInteger(RMDServiceConstants.ACTIVE_FLAG, 1); // 1
            // denotes active rule
            arlRuleId = (ArrayList) hibQuery.list();
            iRuleIdList = arlRuleId.size();
            LOG.debug("Size of RuleId --------->" + iRuleIdList);
            for (int index = 0; index < iRuleIdList; index++) {
                objGetKmDpdRulhis = (GetKmDpdRulhisHVO) arlRuleId.get(index);
                lstRuleId.add(objGetKmDpdRulhis.getGetKmDpdFinrul().getGetKmDpdFinrulSeqId());
            }
            objRuleTesterServiceVO.setAlRuleIdList(lstRuleId);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_RULE_ID, e);
        } finally {
            releaseSession(objHibernateSession);
            objGetKmDpdRulhis = null;
            arlRuleId = null;
            lstRuleId = null;
        }
        LOG.debug("End of fetchRuleId method in RuleTesterDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf#fetchUser
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects.
     * RuleTesterSeachCriteriaServiceVO) This method is used to load the
     * distinct user drop down values from database
     */
    @Override
    @SuppressWarnings("unchecked")
    public void fetchUser(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDDAOException {
        LOG.debug("Enter into fetchUser method in RuleTesterDAOImpl");
        ArrayList arlUserName;
        ArrayList lstUserName;
        int iUserNameList = RMDCommonConstants.INT_CONST;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        Query hibQuery = null;
        Session objHibernateSession = null;
        try {
            objHibernateSession = getHibernateSession();
            lstUserName = new ArrayList();
            strQuery = "select distinct createdBy from GetToolJdpadTrackingHVO";
            hibQuery = objHibernateSession.createQuery(strQuery);
            arlUserName = (ArrayList) hibQuery.list();
            iUserNameList = arlUserName.size();
            LOG.debug("Size of User  Name--------->" + iUserNameList);
            for (int index = 0; index < iUserNameList; index++) {
                lstUserName.add(arlUserName.get(index));
            }
            objRuleTesterSeachCriteriaServiceVO.setAlUserName(lstUserName);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_USER, e);
        } finally {
            releaseSession(objHibernateSession);
            arlUserName = null;
            lstUserName = null;
        }
        LOG.debug("End of fetchUser method in RuleTesterDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #fetchTrackingRuleId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO) This method is used to fetch the rule
     * id suggestion list from database based on keyword provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void getTrackingRules(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into fetchTrackingRuleId method in RuleTesterDAOImpl");
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
            strRuleIdKey = objRuleTesterSeachCriteriaServiceVO.getStrRuleID();
            LOG.debug("*******Inside TestDAOImpl fetchRuleId**************" + strRuleIdKey);
            strQuery = "select distinct ruleId from GetToolJdpadTrackingHVO jdpadTracking where jdpadTracking.ruleId like :ruleIdKey";
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
            objRuleTesterSeachCriteriaServiceVO.setAlRuleID(lstTrackingRuleId);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_TRACK_RULEID, e);
        } finally {
            releaseSession(objHibernateSession);
            arlTrackingRuleId = null;
            lstTrackingRuleId = null;
        }
        LOG.debug("End of fetchTrackingRuleId method in RuleTesterDAOImpl");
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #fetchTrackingId
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO) This method is used to fetch the
     * tracking id suggestion list from database based on keyword provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void getTrackingDetails(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into fetchTrackingId method in RuleTesterDAOImpl");
        ArrayList arlTrackingId;
        ArrayList lstTrackingId;
        String strRuleId = RMDCommonConstants.EMPTY_STRING;
        String strTrackingIdKey = RMDCommonConstants.EMPTY_STRING;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iTrackingIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        Session objHibernateSession = null;
        GetToolJdpadTrackingHVO objGetToolJdpadTracking = null;
        try {
            objHibernateSession = getHibernateSession();
            lstTrackingId = new ArrayList();
            strRuleId = objRuleTesterSeachCriteriaServiceVO.getStrRuleID();
            strTrackingIdKey = objRuleTesterSeachCriteriaServiceVO.getStrTrackingID();
            LOG.debug("*******Inside TestDAOImpl fetchRuleId strRuleId**************" + strRuleId);
            LOG.debug("*******Inside TestDAOImpl fetchRuleId strTrackingIdKey**************" + strTrackingIdKey);
            strQuery = "from GetToolJdpadTrackingHVO jdpadTracking where jdpadTracking.getToolJdpadTrackingSeqId like :trackingIdKey";
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                strQuery = strQuery + " and jdpadTracking.ruleId =:ruleId";
            }
            strQuery = strQuery + " order by jdpadTracking.getToolJdpadTrackingSeqId asc";
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
                objGetToolJdpadTracking = (GetToolJdpadTrackingHVO) arlTrackingId.get(index);
                lstTrackingId.add(objGetToolJdpadTracking.getGetToolJdpadTrackingSeqId());
            }
            objRuleTesterSeachCriteriaServiceVO.setAlTrackingID(lstTrackingId);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_TRACKINGID, e);
        } finally {
            releaseSession(objHibernateSession);
            objGetToolJdpadTracking = null;
            arlTrackingId = null;
            lstTrackingId = null;
        }
        LOG.debug("Enter of fetchTrackingId method in RuleTesterDAOImpl");
    }

    private ArrayList getArlHeaders(String strLanguage) {
        Session hibernateSession = getHibernateSession();
        ArrayList arlHeader = new ArrayList();
        ArrayList arlHeaderList = new ArrayList();
        String strHeaderWidth = RMDCommonConstants.EMPTY_STRING;
        String strWidth = RMDCommonConstants.EMPTY_STRING;
        try {
            StringBuilder sbHeaderQuery = new StringBuilder();
            sbHeaderQuery.append(
                    " SELECT GET_CM_DS_PARMINFO_SEQ_ID,LINK_PARMDEF,INFO,LOWER_BOUND,UPPER_BOUND,HEADER_HTML,SORT_ORDER,PARM_GROUP,HEADER_WIDTH,DATA_SUBSTRING,COLUMN_NAME,DATA_TOOLTIP_FLAG,STYLE,FORMAT,DS_COL_AVAIL,VVF_COL_AVAIL,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,LANGUAGE ");
            sbHeaderQuery.append(" FROM GET_CM.GET_CM_DS_PARMINFO PARMINFO WHERE RT_COL_AVAIL='Y' AND LANGUAGE = '"
                    + strLanguage + "'");
            sbHeaderQuery.append(" ORDER BY SORT_ORDER ");
            Query query = hibernateSession.createSQLQuery(sbHeaderQuery.toString())
                    .addEntity(RMDCommonConstants.PARMINFO, GetCmDsParminfoHVO.class);
            arlHeader = (ArrayList) query.list();
            GetToolDsParminfoServiceVO objParminfoServiceVO;
            int intArlHeaderSize = arlHeader.size();
            int fixedHeaderWidthTotal = 0;
            int variableHeaderWidthTotal = 0;
            int intWidthLen = 0;
            StringBuilder sbfDummyScrollHeader;
            for (int i = 0; i < intArlHeaderSize; i++) {
                sbfDummyScrollHeader = new StringBuilder();
                GetCmDsParminfoHVO objGetCmDsParminfoHVO = (GetCmDsParminfoHVO) arlHeader.get(i);
                objParminfoServiceVO = new GetToolDsParminfoServiceVO();
                objParminfoServiceVO.setColumnName(objGetCmDsParminfoHVO.getColumnName());
                objParminfoServiceVO.setHeaderHtml(objGetCmDsParminfoHVO.getHeaderHtml());
                objParminfoServiceVO.setStyle(objGetCmDsParminfoHVO.getStyle());
                objParminfoServiceVO.setInfo(objGetCmDsParminfoHVO.getInfo());
                objParminfoServiceVO.setLowerBound(objGetCmDsParminfoHVO.getLowerBound());
                objParminfoServiceVO.setUpperBound(objGetCmDsParminfoHVO.getUpperBound());
                objParminfoServiceVO.setDataTooltipFlag(objGetCmDsParminfoHVO.getDataTooltipFlag());
                strWidth = objGetCmDsParminfoHVO.getHeaderWidth().toString();
                intWidthLen = Integer.valueOf(strWidth);
                if (i < 4) {
                    fixedHeaderWidthTotal += objGetCmDsParminfoHVO.getHeaderWidth();
                    if (i == 0) {
                        intWidthLen = intWidthLen - 20;
                    }
                    for (int index = 0; index < intWidthLen; index++) {
                        sbfDummyScrollHeader.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                } else {
                    variableHeaderWidthTotal += objGetCmDsParminfoHVO.getHeaderWidth();
                    for (int index = 0; index < intWidthLen; index++) {
                        sbfDummyScrollHeader.append(RMDServiceConstants.DUMMY_HDR_LITERAL);
                    }
                }
                String temp = objGetCmDsParminfoHVO.getHeaderWidth() + "px,";
                strHeaderWidth = strHeaderWidth + temp;
                objParminfoServiceVO.setHeaderWidth(strWidth + RMDServiceConstants.PX);
                objParminfoServiceVO.setDummyHeaderHtml(sbfDummyScrollHeader.toString());
                objParminfoServiceVO.setFormat(objGetCmDsParminfoHVO.getFormat());
                objParminfoServiceVO.setLowerBound(objGetCmDsParminfoHVO.getLowerBound());
                objParminfoServiceVO.setUpperBound(objGetCmDsParminfoHVO.getUpperBound());
                objParminfoServiceVO.setFixedHeaderWidthTotal(String.valueOf(fixedHeaderWidthTotal));
                objParminfoServiceVO.setVariableHeaderWidthTotal(String.valueOf(variableHeaderWidthTotal));
                objParminfoServiceVO.setStrHeaderWidth(strHeaderWidth);
                arlHeaderList.add(objParminfoServiceVO);
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in CaseDAOImpl getArlHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlHeaderList;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTesterDAOIntf#
     * getArlHeaders()
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList getArlHeaders() {
        LOG.debug("Enter into getArlHeaders method in RuleTesterDAOImpl");
        Session hibernateSession = getHibernateSession();
        ArrayList arlHeader = new ArrayList();
        try {
            StringBuilder sbHeaderQuery = new StringBuilder();
            sbHeaderQuery.append(
                    " SELECT GET_TOOL_DS_PARMINFO_SEQ_ID,LINK_PARMDEF,INFO,LOWER_BOUND,UPPER_BOUND,HEADER_HTML,SORT_ORDER,PARM_GROUP,HEADER_WIDTH,DATA_SUBSTRING,COLUMN_NAME,DATA_TOOLTIP_FLAG,STYLE,FORMAT,DS_COL_AVAIL,VVF_COL_AVAIL,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE ");
            sbHeaderQuery.append(" FROM GET_TOOL.GET_TOOL_DS_PARMINFO PARMINFO WHERE RT_COL_AVAIL='Y'");
            sbHeaderQuery.append(" ORDER BY SORT_ORDER ");
            Query query = hibernateSession.createSQLQuery(sbHeaderQuery.toString()).addEntity("PARMINFO",
                    GetCmDsParminfoHVO.class);
            arlHeader = (ArrayList) query.list();
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in CaseDAOImpl getArlHeaders()", e);
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DETAILS, e);
        } finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("End of getArlHeaders method in RuleTesterDAOImpl");
        return arlHeader;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.TestDAOIntf
     * #searchResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO) This method is used to fetch the
     * search result(Fault data) from database based on the search criteria
     * provided.
     */
    @Override
    @SuppressWarnings("unchecked")
    public FaultDataDetailsServiceVO getFaults(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into searchResult method in RuleTesterDAOImpl");
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();
        Session objHibernateSession = null;
        ArrayList arlFaultData = new ArrayList();
        ArrayList arlHeader = new ArrayList();
        ArrayList arlFaultLog = new ArrayList();
        ArrayList arlDataDetails = new ArrayList();
        List arlGrpHeaderList = new ArrayList();
        try {
            String strRuleId = objRuleTesterSeachCriteriaServiceVO.getStrSelRuleID();
            String strTrackingId = objRuleTesterSeachCriteriaServiceVO.getStrSelTrackingID();
            String strUser = objRuleTesterSeachCriteriaServiceVO.getStrSelUserName();
            String strLang = objRuleTesterSeachCriteriaServiceVO.getStrLanguage();
            objHibernateSession = getHibernateSession();
            StringBuilder sbFaultDetailQuery = new StringBuilder();
            sbFaultDetailQuery
                    .append(" SELECT DISTINCT TRACKING.RULE_ID,RULE.RULE_TITLE,TRACKING.FAULT_COUNT,TRACKING.LOOKBACK");
            sbFaultDetailQuery.append(" FROM ");
            sbFaultDetailQuery.append(" GET_TOOL.GET_TOOL_JDPAD_TRACKING TRACKING,GET_KM.GET_KM_DPD_FINRUL RULE");
            sbFaultDetailQuery.append(" WHERE ");
            sbFaultDetailQuery.append(" TRACKING.RULE_ID = RULE.GET_KM_DPD_FINRUL_SEQ_ID ");
            sbFaultDetailQuery.append(" AND TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID = ");
            sbFaultDetailQuery.append(strTrackingId);
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                sbFaultDetailQuery.append(" AND TRACKING.RULE_ID = ");
                sbFaultDetailQuery.append(strRuleId);
            }
            LOG.debug("******sbFaultDetailQuery**********" + sbFaultDetailQuery);
            Query dataQuery = objHibernateSession.createSQLQuery(sbFaultDetailQuery.toString());
            arlFaultData = (ArrayList) dataQuery.list();
            LOG.debug("******arlFaultData size**************" + arlFaultData.size());
            if (!arlFaultData.isEmpty()) {
                Object[] objFaultDataArray = (Object[]) arlFaultData.get(0);
                LOG.debug("objDataArray :" + objFaultDataArray);
                LOG.debug("objDataArray.length :" + objFaultDataArray.length);
                String[] strFaultData = new String[objFaultDataArray.length];
                for (int k = 0; k < strFaultData.length; k++) {
                    if (null == objFaultDataArray[k] || RMDCommonConstants.EMPTY_STRING.equals(objFaultDataArray[k])) {
                        strFaultData[k] = null;
                    } else {
                        strFaultData[k] = objFaultDataArray[k].toString();
                    }
                    LOG.debug("strFaultData[k] :" + strFaultData[k]);
                }
                String strFaultRuleId = strFaultData[0];
                String strRuleTitle = strFaultData[1];
                String strFaultCount = strFaultData[2];
                String strLookBack = strFaultData[3];
                LOG.debug("******strFaultRuleId**************" + strFaultRuleId);
                LOG.debug("******strRuleTitle**************" + strRuleTitle);
                LOG.debug("******strFaultCount**************" + strFaultCount);
                objRuleTesterSeachCriteriaServiceVO.setStrFaultRuleID(strFaultRuleId);
                objRuleTesterSeachCriteriaServiceVO.setStrRuleTitle(strRuleTitle);
                objRuleTesterSeachCriteriaServiceVO.setStrFaultCount(strFaultCount);
                objRuleTesterSeachCriteriaServiceVO.setStrLookBack(strLookBack);
            }
            LOG.debug("******strLang**********" + strLang);
            arlHeader = getArlHeaders(strLang);
            arlGrpHeaderList = getHeaderGroup(strLang);// For getting the
            // ArlGrpHeaders

            int intArlHeaderSize = 0;
            intArlHeaderSize = arlHeader.size();
            GetToolDsParminfoServiceVO objParminfoServiceVO = new GetToolDsParminfoServiceVO();
            for (int i = 0; i < intArlHeaderSize; i++) {
                objParminfoServiceVO = (GetToolDsParminfoServiceVO) arlHeader.get(i);
            }

            String strFinalHeaderWidth = objParminfoServiceVO.getStrHeaderWidth().substring(0,
                    objParminfoServiceVO.getStrHeaderWidth().length() - 1);
            objFaultDataDetailsServiceVO
                    .setFixedHeaderWidthTotal(objParminfoServiceVO.getFixedHeaderWidthTotal() + RMDServiceConstants.PX);
            objFaultDataDetailsServiceVO.setVariableHeaderWidthTotal(
                    objParminfoServiceVO.getVariableHeaderWidthTotal() + RMDServiceConstants.PX);
            objFaultDataDetailsServiceVO.setStrHeaderWidth(strFinalHeaderWidth);
            objFaultDataDetailsServiceVO.setStrFixedHeaderWidth(objParminfoServiceVO.getFixedHeaderWidthTotal());
            objFaultDataDetailsServiceVO.setArlHeader(arlHeader);
            objFaultDataDetailsServiceVO.setArlGrpHeader(arlGrpHeaderList);

            String strColumnName = RMDCommonConstants.EMPTY_STRING;
            String strDataColumns = RMDCommonConstants.EMPTY_STRING;
            String strAlName = RMDCommonConstants.EMPTY_STRING;
            int intArlHeaderListSize = arlHeader.size();
            for (int i = 0; i < intArlHeaderListSize; i++) {
                GetToolDsParminfoServiceVO objParmServiceVO = (GetToolDsParminfoServiceVO) arlHeader.get(i);

                Double lngUpperBound = objParmServiceVO.getUpperBound();
                Double lngLowerBound = objParmServiceVO.getLowerBound();
                String strFormat = objParmServiceVO.getFormat();
                strColumnName = objParmServiceVO.getColumnName();

                if (RMDServiceConstants.OCCUR_DATE.equals(strColumnName)
                        || RMDServiceConstants.FAULT_RESET_DATE.equals(strColumnName)) {
                    strAlName = strColumnName;
                    if (RMDServiceConstants.OCCUR_DATE.equals(strColumnName)) {
                        strColumnName = " FAULT." + strColumnName;
                    }
                    strDataColumns += "NVL2(" + strColumnName + ",to_char(" + strColumnName
                            + ",'MM/dd/yyyy hh:mi:SS AM') || '^' || 'date','')" + strAlName + " ,";
                } else if ("CREATION_DATE".equals(strColumnName)) {
                    strDataColumns += " FAULT." + strColumnName + ",";
                } else if (RMDServiceConstants.LOCATION.equals(strColumnName)) {
                    strDataColumns += " (SELECT DISTINCT PD.PROXIMITY_LABEL || '" + RMDServiceConstants.DS_DELIMITTER
                            + "' || PD.PROXIMITY_DESC ";
                    strDataColumns += " FROM GET_KM.GET_KM_PROXIMITY_DEF PD,";
                    strDataColumns += "	GET_TOOL.GET_TOOL_FLT_PROX_FILTER PROX";
                    strDataColumns += "	WHERE  FAULT.GET_DATA_FAULT_SEQ_ID = PROX.LINK_FAULT";
                    strDataColumns += "	AND PROX.LINK_PROX_DEF = PD.GET_KM_PROXIMITY_DEF_SEQ_ID) " + strColumnName
                            + ",";
                } else if (RMDServiceConstants.LINK_FAULT_CODES.equals(strColumnName)) {
                    strDataColumns += " NVL(( SELECT FAULT_CODE FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                            + strColumnName + " ),FAULT.FAULT_CODE) " + strColumnName + " , ";
                } else if (RMDServiceConstants.FAULT_DESC.equalsIgnoreCase(strColumnName)) {
                    if (null != strFormat) {
                        strDataColumns += " ( SELECT NVL2(FAULT_DESC," + strFormat.substring(0, strFormat.indexOf('$'))
                                + strColumnName + strFormat.substring(strFormat.indexOf('$') + 1, strFormat.length())
                                + ",' ') FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                                + RMDServiceConstants.LINK_FAULT_CODES + " ) " + strColumnName + " , ";
                    } else {
                        strDataColumns += " ( SELECT NVL2(FAULT_DESC,FAULT_DESC,' ') FROM GET_KM.GET_KM_FAULT_CODES INN WHERE FAULT.FAULT_CODE = "
                                + RMDServiceConstants.LINK_FAULT_CODES + " ) " + strColumnName + " , ";
                    }
                } else {
                    if (null != lngUpperBound && null != lngLowerBound) {
                        if (null != strFormat) {
                            strDataColumns += "NVL2(" + strColumnName + ",("
                                    + strFormat.substring(0, strFormat.indexOf('$')) + strColumnName
                                    + strFormat.substring(strFormat.indexOf('$') + 1, strFormat.length())
                                    + "||'^'|| (case when " + strColumnName + " < " + lngLowerBound + " then 'L' when "
                                    + strColumnName + " > " + lngUpperBound + " then 'H' else 'N' end)), null)"
                                    + strColumnName + ",";
                        } else {
                            strDataColumns += strColumnName + "||'^'|| (case when " + strColumnName + " < "
                                    + lngLowerBound + " then 'L' when " + strColumnName + " > " + lngUpperBound
                                    + " then 'H' else 'N' end), null)" + strColumnName + ",";
                        }
                    } else {
                        if (null != strFormat) {
                            strDataColumns += strFormat.substring(0, strFormat.indexOf('$')) + strColumnName
                                    + strFormat.substring(strFormat.indexOf('$') + 1, strFormat.length())
                                    + strColumnName + ",";
                        } else {
                            strDataColumns += strColumnName + ",";
                        }
                    }
                }
            }
            strDataColumns = strDataColumns.substring(0, strDataColumns.length() - 1);
            LOG.debug("strDataColumns :" + strDataColumns);
            StringBuilder sbDataQuery = new StringBuilder();
            sbDataQuery.append(" SELECT INN.*,COUNT(*) OVER (ORDER BY RNUM) CNT FROM "
                    + "(SELECT FAULT.*, 1 RNUM FROM  (SELECT " + strDataColumns);
            sbDataQuery.append(" FROM ");
            sbDataQuery.append(
                    " GET_DATA.GET_DATA_FAULT FAULT,GET_DATA.GET_DATA_MP MP, GET_TOOL.GET_TOOL_JDPAD_TESTER TESTER,GET_TOOL.GET_TOOL_JDPAD_TRACKING TRACKING,GET_KM.GET_KM_TRACKING_ASSET TRACK_ASSET,GET_ASST.GET_ASST_ASSET ASSET,GET_ASST.GET_ASST_GROUP ASSET_GRP");
            sbDataQuery.append(" WHERE ");
            sbDataQuery.append(" MP.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID  ");
            sbDataQuery.append(" AND TESTER.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID ");
            sbDataQuery.append(" AND TESTER.LINK_TRACKING = TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID ");
            sbDataQuery.append(" AND TRACKING.RULE_ID = TESTER.RULE_ID");
            sbDataQuery.append(" AND TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID = TRACK_ASSET.LINK_TRACKING");
            sbDataQuery.append(" AND TRACK_ASSET.LINK_ASSET = ASSET.GET_ASST_ASSET_SEQ_ID");
            sbDataQuery.append(" AND FAULT.LINK_ASSET = ASSET.GET_ASST_ASSET_SEQ_ID");
            sbDataQuery.append(" AND ASSET.LINK_ASSET_GROUP = ASSET_GRP.GET_ASST_GROUP_SEQ_ID");
            sbDataQuery.append(" AND TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID = ");
            sbDataQuery.append(strTrackingId);
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                sbDataQuery.append(" AND TRACKING.RULE_ID = ");
                sbDataQuery.append(strRuleId);
            }
            if (strUser != null && !RMDCommonConstants.SELECT.equalsIgnoreCase(strUser)) {
                strUser = strUser.toLowerCase();
                sbDataQuery.append(" AND LOWER(TRACKING.CREATED_BY) = '" + strUser + "'");
            }
            sbDataQuery.append(" order by fault.occur_time desc ) fault)inn");
            LOG.debug("sbDataQuery :" + sbDataQuery.toString());
            Query dataFaultQuery = objHibernateSession.createSQLQuery(sbDataQuery.toString());
            if (objRuleTesterSeachCriteriaServiceVO.getIntNoOfRecs() >= 0) {
                dataFaultQuery.setFirstResult(objRuleTesterSeachCriteriaServiceVO.getIntStartPos());
                dataFaultQuery.setMaxResults(objRuleTesterSeachCriteriaServiceVO.getIntNoOfRecs());
            }
            List arlData = dataFaultQuery.list();
            int intArlDataSize = arlData.size();
            FaultServiceVO objFaultServiceVO = new FaultServiceVO();
            arlFaultLog = new ArrayList();
            FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
            arlDataDetails = new ArrayList();
            String strTotCnt = null;
            FaultDetailsServiceVO objFaultDetailsServiceVO = null;
            for (int i = 0; i < intArlDataSize; i++) {
                objFaultLogServiceVO = new FaultLogServiceVO();
                arlDataDetails = new ArrayList();
                Object[] objDataArray = (Object[]) arlData.get(i);
                String strTmpDataHolder = null;
                for (int k = 0; k < objDataArray.length - 1; k++) {
                    if (null != objDataArray[k]) {
                        strTmpDataHolder = objDataArray[k].toString();
                    } else {
                        strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                    }
                    objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                    objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                    arlDataDetails.add(objFaultDetailsServiceVO);
                }
                strTotCnt = objDataArray[intArlHeaderListSize + 1].toString();
                objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                arlFaultLog.add(objFaultLogServiceVO);
            }
            /*
             * modified added -1 since total no of records are also fetched as a
             * part of the query
             */
            /*
             * added to set the total number of records available in DB on the
             * whole
             */
            if (intArlDataSize > 0) {
                objFaultDataDetailsServiceVO.setIntTotalRecsAvail(RMDCommonUtility.getIntValue(strTotCnt));
            }
            objFaultServiceVO.setArlFaultData(arlFaultLog);
            objFaultDataDetailsServiceVO.setObjFaultServiceVO(objFaultServiceVO);
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_SERCH_RESULT, e);
        } finally {
            releaseSession(objHibernateSession);
            arlFaultData = null;
            arlHeader = null;
            arlFaultLog = null;
            arlDataDetails = null;
        }
        LOG.debug("End of searchResult method in RuleTesterDAOImpl");
        return objFaultDataDetailsServiceVO;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleTesterDAOIntf#
     * searchTrackingResult
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .RuleTesterSeachCriteriaServiceVO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List searchTrackingResult(RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO)
            throws RMDDAOException {
        LOG.debug("Enter into searchTrackingResult method in RuleTesterDAOImpl");
        int iTrackingData = RMDCommonConstants.INT_CONST;
        Session objHibernateSession = null;
        ArrayList arlTrackingData = new ArrayList();
        ArrayList arlTrackingDataResult = new ArrayList();
        Codec ORACLE_CODEC = new OracleCodec();
        try {
            String strRuleId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTesterSeachCriteriaServiceVO.getStrRuleID());
            String strTrackingId = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTesterSeachCriteriaServiceVO.getStrTrackingID());
            String strUser = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTesterSeachCriteriaServiceVO.getStrUserName());
            String strLkbWeeks = ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
                    objRuleTesterSeachCriteriaServiceVO.getStrLookBackWeeks());
            objHibernateSession = getHibernateSession();
            StringBuilder sbTrackingDetailQuery = new StringBuilder();
            sbTrackingDetailQuery.append(
                    " SELECT DISTINCT TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID,TRACKING.RULE_ID,RULE.RULE_TITLE,RULEDEF.RULE_TYPE,TRACKING.CREATED_BY,TO_CHAR(TRACKING.CREATION_DATE,'MM/DD/YYYY HH:MI:SS AM'),TO_CHAR(TRACKING.LAST_UPDATED_DATE,'MM/DD/YYYY HH:MI:SS AM'),LOOKUP.LOOKUP_VALUE");
            sbTrackingDetailQuery.append(" FROM ");
            sbTrackingDetailQuery.append(
                    " GET_TOOL.GET_TOOL_JDPAD_TRACKING TRACKING,GET_KM.GET_KM_DPD_RULEDEF RULEDEF,GET_KM.GET_KM_DPD_FINRUL RULE,GET_SYS.GET_SYS_LOOKUP LOOKUP");
            sbTrackingDetailQuery.append(" WHERE ");
            sbTrackingDetailQuery.append(" TRACKING.STATUS = LOOKUP.GET_SYS_LOOKUP_SEQ_ID ");
            sbTrackingDetailQuery.append("AND TRACKING.RULE_ID = RULE.GET_KM_DPD_FINRUL_SEQ_ID ");
            sbTrackingDetailQuery.append("AND RULE.GET_KM_DPD_FINRUL_SEQ_ID = RULEDEF.LINK_FINRUL ");
            if (strTrackingId != null && !RMDCommonConstants.EMPTY_STRING.equals(strTrackingId)) {
                sbTrackingDetailQuery.append(" AND TRACKING.GET_TOOL_JDPAD_TRACKING_SEQ_ID = ");
                sbTrackingDetailQuery.append(strTrackingId);
            }
            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                sbTrackingDetailQuery.append(" AND TRACKING.RULE_ID = ");
                sbTrackingDetailQuery.append(strRuleId);
            }
            if (strUser != null && !RMDCommonConstants.SELECT.equalsIgnoreCase(strUser)) {
                sbTrackingDetailQuery.append(" AND UPPER(TRACKING.CREATED_BY) = '" + strUser.toUpperCase() + "'");
            }
            if (strLkbWeeks != null && !RMDCommonConstants.SELECT.equalsIgnoreCase(strLkbWeeks)) {
                sbTrackingDetailQuery
                        .append(" AND TRACKING.CREATION_DATE >= CAST((FROM_TZ(CAST(SYSDATE AS    TIMESTAMP),   'ASIA/CALCUTTA') AT TIME ZONE 'GMT') AS    DATE) - ("
                                + strLkbWeeks + "*7)");
            }
            sbTrackingDetailQuery.append("ORDER BY tracking.GET_TOOL_JDPAD_TRACKING_SEQ_ID asc ");
            LOG.debug("******sbTrackingDetailQuery**********" + sbTrackingDetailQuery);
            Query dataQuery = objHibernateSession.createSQLQuery(sbTrackingDetailQuery.toString());
            arlTrackingData = (ArrayList) dataQuery.list();
            iTrackingData = arlTrackingData.size();
            LOG.debug("******arlFaultData size**************" + iTrackingData);
            RuleTrackingServiceResultVO objRuleTrackingServiceResultVO = null;
            if (iTrackingData > 0) {
                for (int index = 0; index < iTrackingData; index++) {
                    Object[] objTrackingDataArray = (Object[]) arlTrackingData.get(index);
                    String[] strTrackingData = new String[objTrackingDataArray.length];
                    for (int k = 0; k < strTrackingData.length; k++) {
                        if (null == objTrackingDataArray[k]
                                || RMDCommonConstants.EMPTY_STRING.equals(objTrackingDataArray[k])) {
                            strTrackingData[k] = null;
                        } else {
                            strTrackingData[k] = objTrackingDataArray[k].toString();
                        }
                    }
                    objRuleTrackingServiceResultVO = new RuleTrackingServiceResultVO();
                    String strTrackingSeqId = strTrackingData[0];
                    String strFaultRuleId = strTrackingData[1];
                    String strRuleTitle = strTrackingData[2];
                    String strRuleType = strTrackingData[3];
                    String strCreatedBy = strTrackingData[4];
                    String strCreationDate = strTrackingData[5];
                    String strLastUpdatedDate = strTrackingData[6];
                    String strStatus = strTrackingData[7];
                    if (RMDServiceConstants.RUNNING.equalsIgnoreCase(strStatus)) {
                        objRuleTrackingServiceResultVO.setBTrackingText(RMDCommonConstants.TRUE);
                        objRuleTrackingServiceResultVO.setBTrackingLink(RMDCommonConstants.FALSE);
                    }
                    if (RMDServiceConstants.COMPLETE.equalsIgnoreCase(strStatus)) {
                        objRuleTrackingServiceResultVO.setBTrackingText(RMDCommonConstants.FALSE);
                        objRuleTrackingServiceResultVO.setBTrackingLink(RMDCommonConstants.TRUE);
                    }
                    objRuleTrackingServiceResultVO.setStrTrackingSeqId(strTrackingSeqId);
                    objRuleTrackingServiceResultVO.setStrFaultRuleId(strFaultRuleId);
                    objRuleTrackingServiceResultVO.setStrRuleTitle(strRuleTitle);
                    objRuleTrackingServiceResultVO.setStrRuleType(strRuleType);
                    objRuleTrackingServiceResultVO.setStrCreatedBy(strCreatedBy);
                    try {
                        objRuleTrackingServiceResultVO.setStrCreationDate(RMDCommonUtility.updateAsGMT(RMDCommonUtility
                                .stringToDate(strCreationDate, RMDCommonConstants.DateConstants.MMddyyyyHHmmssa)));
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    try {
                        if (RMDServiceConstants.COMPLETE.equalsIgnoreCase(strStatus)) {
                            objRuleTrackingServiceResultVO.setStrLastUpdatedDate(
                                    RMDCommonUtility.updateAsGMT(RMDCommonUtility.stringToDate(strLastUpdatedDate,
                                            RMDCommonConstants.DateConstants.MMddyyyyHHmmssa)));
                        } else {
                            objRuleTrackingServiceResultVO.setStrLastUpdatedDate(null);
                        }
                    } catch (Exception e) {
                        LOG.error("Exception while parsing date: ", e);
                    }
                    objRuleTrackingServiceResultVO.setStrStatus(strStatus);
                    arlTrackingDataResult.add(objRuleTrackingServiceResultVO);
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
        LOG.debug("End of searchTrackingResult method in RuleTesterDAOImpl");
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
            String strQuery = "select PARM_GROUP, STYLE, SUM(HEADER_WIDTH) headerWidth,count(*) no_columns , MIN(SORT_ORDER) sortOrder from GET_CM.GET_CM_DS_PARMINFO where RT_COL_AVAIL='Y' and language = :language group by PARM_GROUP, STYLE order by sortOrder";
            Query query = hibernateSession.createSQLQuery(strQuery);
            query.setParameter(RMDServiceConstants.LANGUAGE, strLanguage);
            List headerGroupResult = query.list();
            int iheaderGroupResult = headerGroupResult.size();
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
            LOG.error("Unexpected Error occured in RuleTesterDAOImpl getHeaderGroup()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlHeaderGroup;
    }

}
