package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

/**
 * ============================================================
 * File : ManualRuleDAOImpl.java
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinFragment;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstAssetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmDsParminfoHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmManFiredHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmManRunHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetSysLookupHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetToolSubrunHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUsersHVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ManualRunDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadResultServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadSearchVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ManualRunServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the DAO implementaion class to get the values
 *              from DB
 * @History :
 ******************************************************************************/
public class ManualRunDAOImpl extends BaseDAO implements ManualRunDAOIntf {

    /**
     * Default Serial version Id
     */
    private static final long serialVersionUID = 1L;

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(ManualRunDAOImpl.class);

    /**
     * @Author:
     * @param objManualRuleServiceVO
     * @return arlAsset
     * @Description:This method is used to get the list of Assets
     */
    @Override
    public List getAssetList(final ManualRunServiceVO objManualRuleServiceVO) throws RMDDAOException {
        List arlAsset = new ArrayList();

        Session manualRuleSession = null;
        try {
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASST_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objManualRuleServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASST_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objManualRuleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(manualRuleSession);
        }

        return arlAsset;
    }

    /**
     * @Author:
     * @param objManualJdpadServiceVO
     * @return iSeqID
     * @Description:This method is used for running the jdpad
     */

    @Override
    public int runJdpad(final ManualRunServiceVO objManualJdpadServiceVO) throws RMDDAOException {
        GetKmManRunHVO objGetKmManRunHVO;
        int iSeqID = RMDCommonConstants.INT_CONST;
        GetSysLookupHVO objGetSysLookupHVO = new GetSysLookupHVO();
        ArrayList arlAssetId = null;
        ArrayList arlLkUp = null;
        Session runJdpadSession = null;
        try {
            runJdpadSession = getHibernateSession("ManualJDPAD");
            Long lgassetNumber = objManualJdpadServiceVO.getLgAssetId();
            String strManRunStatus = "MANUAL_RUN_STATUS";
            String strLkValue = RMDCommonConstants.RUNNING;
            final Criteria criteria = runJdpadSession.createCriteria(GetAsstAssetHVO.class);
            criteria.add(Restrictions.eq("longAssetNumber", lgassetNumber));
            arlAssetId = (ArrayList) criteria.list();
            int arlAssetIdSize = arlAssetId.size();

            if (arlAssetIdSize > 0) {

                GetAsstAssetHVO objGetAsstAssetHVO = (GetAsstAssetHVO) arlAssetId.get(0);

                Criteria lkUpCriteria = runJdpadSession.createCriteria(GetSysLookupHVO.class);
                lkUpCriteria.add(Restrictions.eq(RMDCommonConstants.LIST_NAME, strManRunStatus));
                lkUpCriteria.add(Restrictions.eq(RMDCommonConstants.LOOKVALUE, strLkValue));
                arlLkUp = (ArrayList) lkUpCriteria.list();
                objGetSysLookupHVO = (GetSysLookupHVO) arlLkUp.get(0);
                objGetKmManRunHVO = new GetKmManRunHVO();
                objGetKmManRunHVO.setGetCmCustomer(objGetAsstAssetHVO.getGetCmCustomer());
                objGetKmManRunHVO.setGetAsstAsset(objGetAsstAssetHVO);
                objGetKmManRunHVO.setFromDate(objManualJdpadServiceVO.getDtStart());
                objGetKmManRunHVO.setToDate(objManualJdpadServiceVO.getDtEnd());
                objGetKmManRunHVO.setProcessMode(objManualJdpadServiceVO.getStrMode());
                objGetKmManRunHVO.setProcessStatus(objGetSysLookupHVO.getGetSysLookupSeqId());
                runJdpadSession.saveOrUpdate(objGetKmManRunHVO);
                runJdpadSession.flush();
                runJdpadSession.clear();
                iSeqID = objGetKmManRunHVO.getGetKmManRunSeqId().intValue();

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RUN_JDPAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objManualJdpadServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RUN_JDPAD);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objManualJdpadServiceVO.getStrLanguage()),
                    e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(runJdpadSession);
        }
        return iSeqID;

    }

    /**
     * @Author:
     * @param objJdpadResultServiceVO
     * @return
     * @Description:This method is used to get the search results from DB
     */

    @Override
    public List searchManualRun(JdpadResultServiceVO objJdpadResultServiceVO) throws RMDDAOException {
        Session searchJdpadSession = null;
        Long processId;
        Criteria criteriaFlag = null;
        ArrayList lstSearch = null;
        ArrayList flagDetails = null;

        try {
            lstSearch = new ArrayList();
            JdpadSearchVO objJdpadSearchVO = null;
            searchJdpadSession = getHibernateSession(RMDCommonConstants.ADMIN);
            Criteria criteria = searchJdpadSession.createCriteria(GetKmManRunHVO.class);

            if (!(RMDCommonConstants.EMPTY_STRING).equals(objJdpadResultServiceVO.getStrTrackingId())) {

                criteria.add(Restrictions.eq(RMDCommonConstants.GETKMMAN_RUNSEQID,
                        Long.valueOf(objJdpadResultServiceVO.getStrTrackingId())));
            }
            if (!(RMDCommonConstants.SELECT).equalsIgnoreCase(objJdpadResultServiceVO.getStrDay())
                    && !(RMDCommonConstants.EMPTY_STRING).equals(objJdpadResultServiceVO.getStrDay())) {
                int days = Integer.parseInt(objJdpadResultServiceVO.getStrDay());
                Date dest = RMDCommonUtility.getGMTDateTime(RMDCommonConstants.DateConstants.MMddyyyyHHmmssa);
                Date src = RMDCommonUtility.substractOrAddFromDate(dest,
                        TimeZone.getTimeZone(RMDCommonConstants.DateConstants.GMT), Calendar.DATE, -days);
                criteria.add(Restrictions.between(RMDCommonConstants.CREATION_DATE, src, dest));
            }

            if (!objJdpadResultServiceVO.getStrAssetId().equals(RMDCommonConstants.EMPTY_STRING)) {

                criteria.setFetchMode(RMDCommonConstants.GETASSTASSET, FetchMode.JOIN)
                        .createAlias(RMDCommonConstants.GETASSTASSET, RMDCommonConstants.ASSET);

                criteria.add(Restrictions.eq(RMDCommonConstants.ASSET_LONG_ASSETNUMBER,
                        Long.valueOf(objJdpadResultServiceVO.getStrAssetId())));
            }
            ArrayList arlSearch = (ArrayList) criteria.list();
            int arlSearchSize = arlSearch.size();
            if (arlSearchSize > 0) {

                for (int index = 0; index < arlSearchSize; index++) {

                    GetKmManRunHVO objGetKmManRunHVO = (GetKmManRunHVO) arlSearch.get(index);
                    objJdpadSearchVO = new JdpadSearchVO();
                    objJdpadSearchVO.setStrCreatedBy(objGetKmManRunHVO.getCreatedBy());
                    objJdpadSearchVO.setDtFinishTime(objGetKmManRunHVO.getLastUpdatedDate());
                    objJdpadSearchVO.setDtCreatedTime(objGetKmManRunHVO.getCreationDate());
                    objJdpadSearchVO.setStrTrackingId(objGetKmManRunHVO.getGetKmManRunSeqId().toString());

                    objJdpadSearchVO.setStrMode(objGetKmManRunHVO.getProcessMode());
                    if (objGetKmManRunHVO.getProcessMode().equals(RMDCommonConstants.DS)) {
                        objJdpadSearchVO.setBlMode(true);
                        criteriaFlag = searchJdpadSession.createCriteria(GetKmManFiredHVO.class);
                        criteriaFlag.setFetchMode(RMDCommonConstants.GET_KM_MAN_RUN, FetchMode.JOIN)
                                .createAlias(RMDCommonConstants.GET_KM_MAN_RUN, RMDCommonConstants.MAN_RUN);
                        criteriaFlag.add(Restrictions.eq(RMDCommonConstants.MANRUN_GET_KM_RUNSEQID,
                                Long.valueOf(objJdpadSearchVO.getStrTrackingId())));
                        flagDetails = (ArrayList) criteriaFlag.list();
                        boolean flagFilter = false;
                        boolean flagJDPAD = false;

                        for (Iterator iterator = flagDetails.iterator(); iterator.hasNext();) {
                            GetKmManFiredHVO object = (GetKmManFiredHVO) iterator.next();
                            if (object.getFilterFlag().equals(RMDCommonConstants.LETTER_Y)) {
                                flagFilter = true;
                            } else if (object.getFilterFlag().equals(RMDCommonConstants.LETTER_N)) {
                                flagJDPAD = true;
                            }
                            if (flagFilter && flagJDPAD) {
                                break;
                            }

                        }
                        if (flagFilter && flagJDPAD) {
                            objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.FAULTS_FILTERED);
                        } else if (flagFilter && !flagJDPAD) {
                            objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.ALL_FAULTS_FILTERED);
                        } else if (!flagFilter && flagJDPAD) {
                            objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.FEW_FAULTS_FILTERED);
                        } else {
                            objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.NO_FAULTS_NO_RULE_FIRED);
                        }

                    } else if (objGetKmManRunHVO.getProcessMode().equals(RMDCommonConstants.CC)) {
                        objJdpadSearchVO.setBlMode(false);

                        if (objGetKmManRunHVO.getGetToolRun() != null) {
                            criteriaFlag = searchJdpadSession.createCriteria(GetToolSubrunHVO.class);
                            criteriaFlag.setFetchMode(RMDCommonConstants.GET_TOOL_RUN, FetchMode.JOIN)
                                    .createAlias(RMDCommonConstants.GET_TOOL_RUN, RMDCommonConstants.RUN);
                            criteriaFlag.add(Restrictions.eq(RMDCommonConstants.RUN_GETTOOL_RUNSEQID,
                                    Long.valueOf(objGetKmManRunHVO.getGetToolRun().getGetToolRunSeqId())));
                            criteriaFlag.add(Restrictions.isNotNull(RMDCommonConstants.START_TIME));
                            criteriaFlag.add(Restrictions.isNotNull(RMDCommonConstants.CPT_TIME));
                            flagDetails = (ArrayList) criteriaFlag.list();
                            String toolIDFilter = null;
                            String toolIDJDPAD = null;
                            String toolIDCR = null;
                            for (Iterator iterator = flagDetails.iterator(); iterator.hasNext();) {
                                GetToolSubrunHVO object = (GetToolSubrunHVO) iterator.next();
                                if (object.getToolId().equals(RMDCommonConstants.FILTER)) {
                                    toolIDFilter = object.getToolId();
                                } else if (object.getToolId().equals(RMDCommonConstants.JDPAD)) {
                                    toolIDJDPAD = object.getToolId();
                                } else if (object.getToolId().equals(RMDCommonConstants.CR_TOOLID)) {
                                    toolIDCR = object.getToolId();
                                }
                            }
                            if (toolIDFilter != null && toolIDJDPAD != null && toolIDCR != null) {
                                objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.CASE_CREATED);
                            } else if (toolIDFilter == null && toolIDJDPAD != null && toolIDCR != null) {
                                objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.NO_FILTER_RULE_FIRED);
                            } else if (toolIDFilter == null && toolIDJDPAD == null && toolIDCR == null) {
                                objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.NO_NEW_FAULTS);
                            } else if (toolIDFilter != null && toolIDJDPAD == null && toolIDCR == null) {
                                objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.ALL_FAULTS_FILTERED);
                            } else if (toolIDFilter != null && toolIDJDPAD != null && toolIDCR == null) {
                                objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.NO_DIA_RULE_FIRED);
                            }
                        } else {
                            objJdpadSearchVO.setTrackingMsg(RMDCommonConstants.NO_NEW_FAULTS);
                        }
                    }

                    if (objGetKmManRunHVO.getGetToolRun() != null
                            && objGetKmManRunHVO.getGetToolRun().getGetCmCase() != null) {
                        objJdpadSearchVO.setStrCaseId(objGetKmManRunHVO.getGetToolRun().getGetCmCase().getCaseId());
                        objJdpadSearchVO.setStrStatus(RMDCommonConstants.COMPLETE);
                        objJdpadSearchVO.setBlStatus(true);
                    } else {

                        objJdpadSearchVO.setStrCaseId(RMDCommonConstants.BLANK_SPACE);

                        processId = objGetKmManRunHVO.getProcessStatus();
                        Criteria crit = searchJdpadSession.createCriteria(GetSysLookupHVO.class);
                        crit.add(Restrictions.eq(RMDCommonConstants.GETSYS_LOOKUP_SEQID, processId));
                        ArrayList arlLkUp = (ArrayList) crit.list();
                        if (!arlLkUp.isEmpty()) {
                            GetSysLookupHVO objGetSysLookupHVO = (GetSysLookupHVO) arlLkUp.get(0);
                            if (objGetSysLookupHVO.getLookValue().equals(RMDCommonConstants.RUNNING)) {
                                objJdpadSearchVO.setBlStatus(false);
                            } else {
                                objJdpadSearchVO.setBlStatus(true);

                            }

                            objJdpadSearchVO.setStrStatus(objGetSysLookupHVO.getLookValue());

                        }

                    }
                    lstSearch.add(objJdpadSearchVO);

                }

                objJdpadResultServiceVO.setArlJdpadSearch(lstSearch);

            } else {
                objJdpadResultServiceVO.setArlJdpadSearch(lstSearch);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_JDPAD_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objJdpadResultServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEARCH_JDPAD_LIST);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objJdpadResultServiceVO.getStrLanguage()),
                    e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(searchJdpadSession);
        }
        return lstSearch;

    }

    /**
     * @Author:
     * @param strLanguage
     *            ,strTrackingId
     * @return
     * @Description:This method is used to get the list of TrackIds from DB
     */
    @Override
    public List getTrackIdsList(String strTrackingId, String strLanguage) {

        List arlTrackId = new ArrayList();
        ArrayList lstTrackId = new ArrayList();
        Session getTrackIdSession = null;
        getTrackIdSession = getHibernateSession();
        Criteria criteria = getTrackIdSession.createCriteria(GetKmManRunHVO.class);
        try {
            if (strTrackingId != null && (!strTrackingId.equalsIgnoreCase(RMDCommonConstants.STAR_SYMBOL)
                    && !strTrackingId.trim().equalsIgnoreCase(RMDCommonConstants.EMPTY_STRING))) {
                criteria.add(
                        Restrictions.ilike(RMDCommonConstants.STRGETKMMANRUNSEQID, strTrackingId, MatchMode.ANYWHERE));
            }

            lstTrackId = (ArrayList) criteria.list();
            int lstTrackSize = lstTrackId.size();
            if (lstTrackSize > 0) {
                for (int index = 0; index < lstTrackSize; index++) {
                    GetKmManRunHVO objGetKmManRunHVO = (GetKmManRunHVO) lstTrackId.get(index);
                    arlTrackId.add(objGetKmManRunHVO.getGetKmManRunSeqId());
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TRACK_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TRACK_ID);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(getTrackIdSession);
        }

        return arlTrackId;

    }

    @Override
    public FaultDataDetailsServiceVO getFaultsForManualRun(
            RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) {

        LOG.debug("Enter into getFaultsForManualRun method in ManualRunDAOImpl");
        FaultDataDetailsServiceVO objFaultDataDetailsServiceVO = new FaultDataDetailsServiceVO();
        Session objHibernateSession = null;
        ArrayList arlHeader = new ArrayList();
        ArrayList arlFaultLog = new ArrayList();
        ArrayList arlDataDetails = new ArrayList();
        List arlGrpHeaderList = new ArrayList();
        try {
            String strRuleId = objRuleTesterSeachCriteriaServiceVO.getStrSelRuleID();
            String strLang = objRuleTesterSeachCriteriaServiceVO.getStrLanguage();
            objHibernateSession = getHibernateSession();
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
                LOG.debug("objParmServiceVO style :" + objParmServiceVO.getFormat());
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
                    " GET_DATA.GET_DATA_FAULT FAULT, GET_DATA.GET_DATA_MP MP, GET_KM.GET_KM_MAN_RUN MANRUN, GET_KM.GET_KM_MAN_FIRED  MANFIRED, GET_ASST.GET_ASST_ASSET ASSET, GET_ASST.GET_ASST_GROUP ASSET_GRP ");
            sbDataQuery.append(" WHERE ");
            sbDataQuery.append("MP.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID");
            sbDataQuery.append(" AND MANFIRED.LINK_FAULT = FAULT.GET_DATA_FAULT_SEQ_ID");
            sbDataQuery.append(" AND MANFIRED.LINK_MAN_RUN =GET_KM_MAN_RUN_SEQ_ID");
            sbDataQuery.append(" AND MANRUN.LINK_ASSET = ASSET.GET_ASST_ASSET_SEQ_ID");
            sbDataQuery.append(" AND FAULT.LINK_ASSET = ASSET.GET_ASST_ASSET_SEQ_ID");
            sbDataQuery.append(" AND ASSET.LINK_ASSET_GROUP = ASSET_GRP.GET_ASST_GROUP_SEQ_ID");
            sbDataQuery
                    .append(" AND GET_KM_MAN_RUN_SEQ_ID  = " + objRuleTesterSeachCriteriaServiceVO.getStrTrackingID());

            if (strRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strRuleId)) {
                sbDataQuery.append(" AND MANFIRED.LINK_RULE_DEF  = " + strRuleId);
            }
            sbDataQuery.append(" ORDER BY FAULT.OCCUR_TIME DESC ) FAULT)INN");
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
            arlHeader = null;
            arlFaultLog = null;
            arlDataDetails = null;
        }
        LOG.debug("End of getFaultsForManualRun method in ManualRunDAOImpl");
        return objFaultDataDetailsServiceVO;
    }

    public List getColToolTips(String strSelectedRuleId, String strSelectedRuleType, String strLanguage) {
        Session hibernateSession = getHibernateSession();
        List arlColToolTips = new ArrayList();
        try {
            // To get the Column name and the Tool Tip based on Rule
            StringBuilder sbColTooltipQuery = new StringBuilder();
            if (strSelectedRuleId != null && !RMDCommonConstants.EMPTY_STRING.equals(strSelectedRuleId)
                    && strSelectedRuleType != null && ("Simple").equals(strSelectedRuleType)) {
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
                    && strSelectedRuleType != null && ("Complex").equals(strSelectedRuleType)) {
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
            LOG.error("Unexpected Error occured in ManualRunDAOImpl getColToolTips()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return arlColToolTips;
    }

    public List getAssetList(String strUserId) throws RMDDAOException {
        List arlAsset = new ArrayList();
        ArrayList arlSearchRes = null;

        Session session = null;
        try {

            session = getHibernateSession(strUserId);

            final Criteria criteria = session.createCriteria(GetUsrUsersHVO.class)
                    .setFetchMode(RMDCommonConstants.GET_CMCONTACT_INFO, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GET_CMCONTACT_INFO, RMDCommonConstants.CONTACT_INFO)
                    .setFetchMode(RMDCommonConstants.CONTACT_INFO_GETASSTLOCATION, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.CONTACT_INFO_GETASSTLOCATION, RMDCommonConstants.ASSTLOC)
                    .createAlias(RMDCommonConstants.ASSTLOC_GETASSTASSETS, RMDCommonConstants.ASSTASSET,
                            JoinFragment.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.eq(RMDCommonConstants.USERID, strUserId));
            arlSearchRes = (ArrayList) criteria.list();
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASST_LIST);
            throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR, ex);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ASST_LIST);
            throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR, e);
        } finally {
            releaseSession(session);
        }

        return arlAsset;
    }

    public boolean isValidRuleID(String strRuleId, String strUserID) {
        Session objHibernateSession = null;
        String strValidRuleQuery = null;
        Query hibQuery = null;
        int iValidRuleIdList = RMDCommonConstants.INT_CONST;
        try {
            objHibernateSession = getHibernateSession(strUserID);
            strValidRuleQuery = "from GetKmDpdRulhisHVO ruleHist where ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId =:ruleId";
            hibQuery = objHibernateSession.createQuery(strValidRuleQuery);
            hibQuery.setString(RMDServiceConstants.RULE_ID, strRuleId);
            ArrayList arlRuleId = (ArrayList) hibQuery.list();
            iValidRuleIdList = arlRuleId.size();
            LOG.debug("Size of RuleId --------->" + iValidRuleIdList);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IS_VALID_RULEID);
            throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR, ex);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IS_VALID_RULEID);
            throw new RMDDAOException(errorCode, RMDCommonConstants.FATAL_ERROR, e);
        } finally {
            releaseSession(objHibernateSession);
        }
        if (iValidRuleIdList > 0) {
            return true;
        } else {
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#getArlHeaders()
     */
    private ArrayList getArlHeaders(final String strLanguage) {
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
            LOG.error("Unexpected Error occured in ManualRunDAOImpl getArlHeaders()", e);
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
    @SuppressWarnings("unchecked")
    public ArrayList getArlHeaders() {
        LOG.debug("Enter into getArlHeaders method in ManualRunDAOImpl");
        Session hibernateSession = getHibernateSession();
        ArrayList arlHeader = new ArrayList();
        try {
            StringBuilder sbHeaderQuery = new StringBuilder();
            sbHeaderQuery.append(
                    " SELECT GET_TOOL_DS_PARMINFO_SEQ_ID,LINK_PARMDEF,INFO,LOWER_BOUND,UPPER_BOUND,HEADER_HTML,SORT_ORDER,PARM_GROUP,HEADER_WIDTH,DATA_SUBSTRING,COLUMN_NAME,DATA_TOOLTIP_FLAG,STYLE,FORMAT,DS_COL_AVAIL,VVF_COL_AVAIL,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE ");
            sbHeaderQuery.append(" FROM GET_TOOL.GET_TOOL_DS_PARMINFO PARMINFO where RT_COL_AVAIL='Y'");
            sbHeaderQuery.append(" ORDER BY SORT_ORDER ");
            Query query = hibernateSession.createSQLQuery(sbHeaderQuery.toString()).addEntity("PARMINFO",
                    GetCmDsParminfoHVO.class);
            arlHeader = (ArrayList) query.list();
        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in getArlHeaders() in ManualRunDAOImpl", e);
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_DETAILS, e);
        } finally {
            releaseSession(hibernateSession);
        }
        LOG.debug("End of getArlHeaders method in ManualRunDAOImpl");
        return arlHeader;
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
            LOG.error("Unexpected Error occured in ManualRunDAOImpl getHeaderGroup()", e);
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