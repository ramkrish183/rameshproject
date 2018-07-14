/**
 * 
 */
package com.ge.trans.eoa.services.cases.dao.impl;

/*******************************************************************************
 * Title:		FaultLogDAO.java
 * 
 * Description:	Contains the queries to get all the details based on the 
 * 				search criteria.
 * 
 * Copyright:   Copyright (c) 2001
 * 
 * Company: 	General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author		:	iGate		
 * @Created Date:	18-Feb-08
 * @version		:	1.0
 * 				
 * Revision History		  :			
 * Last Modification Date : 21-July-2010
 * Last Modified By       : IGS
 * Last Modification      : Added a method to obtain the model id using the model name 
 * 
 ******************************************************************************/

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.cases.dao.intf.FaultLogDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.common.constants.FaultLogConstants;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultMobileServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetMobileToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

public class DPEABFaultLogDAO extends AbsFaultLogDAO implements FaultLogConstants, FaultLogDAOIntf {
    int intVehicleID = 0;
    public String strViewName = DPEAB_VIEW;
    int intCaseID = 0;
    String strCreationTime = null;
    String strMaxUpdatedDate = null;
    String strMinObjid = null;
    String strMaxObjid = null;
    String strCaseID = null;
    String strCtrlCfg = null;
    String strCountOccurDate = null;
    String strFaultClassified = null;
    private static final Codec ORACLE_CODEC = new OracleCodec();

    @Override
    public FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        // this gets all the data
        FaultLogHelper.debug("Start getFaultData :" + System.currentTimeMillis());
        Session session = null;
        Query pstmt = null;
        List rs = null;

        ArrayList arlDataDetails = new ArrayList();
        String strNotch = null;
        StringBuilder sbDataQuery = new StringBuilder();

        String strIsJdpadCbrCrit = null;
        String strCaseFrom = null;
        /* For Pagination */
        FaultServiceVO objFaultServiceVO = new FaultServiceVO();
        int loopSize = arlHeaderDetails.size();
        try {
            strIsJdpadCbrCrit = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objFaultRequestVO.getJdpadCbrCrit());
            strCaseFrom = objFaultRequestVO.getCaseFrom();
            session = getHibernateSession();
            sbDataQuery = getFaultQuery(objFaultRequestVO, arlHeaderDetails,
                    (ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strControllerCfg)));
            pstmt = session.createSQLQuery(sbDataQuery.toString());
            String strNoOfDays = objFaultRequestVO.getNoOfDays();
            String strRuleDefID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objFaultRequestVO.getRuleDefID());
            FaultLogHelper.debug("strCaseID :" + strCaseID);
            FaultLogHelper.debug("intVehicleID :" + intVehicleID);
            FaultLogHelper.debug(" strMinObjid :" + strMinObjid);
            FaultLogHelper.debug(" strMaxObjid :" + strMaxObjid);
            FaultLogHelper.debug(" strRuleDefID :" + strRuleDefID);

            if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                if (STR_Y.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strNoOfDays);
                    pstmt.setParameter(1, intVehicleID);
                } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strCreationTime);
                    pstmt.setString(1, strNoOfDays);
                    pstmt.setString(2, strCreationTime);
                    pstmt.setParameter(3, intVehicleID);

                } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strNoOfDays);
                    pstmt.setParameter(1, intVehicleID);
                    if (null != strMaxUpdatedDate) {
                        pstmt.setString(2, strMaxUpdatedDate);
                        pstmt.setParameter(3, intCaseID);

                    } else {
                        pstmt.setParameter(2, intCaseID);
                    }
                } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intCaseID);
                    pstmt.setParameter(3, intVehicleID);
                }
            } else {
                if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                        pstmt.setString(0, strMinObjid);
                        pstmt.setString(1, strMaxObjid);
                        pstmt.setParameter(2, intVehicleID);
                        pstmt.setParameter(3, intCaseID);

                    } else {
                        FaultLogHelper.debug("strCaseID :" + strCaseID);
                        FaultLogHelper.debug("intVehicleID :" + intVehicleID);
                        FaultLogHelper.debug(" strMinObjid :" + strMinObjid);
                        FaultLogHelper.debug(" strMaxObjid :" + strMaxObjid);
                        FaultLogHelper.debug(" strRuleDefID :" + strRuleDefID);

                        pstmt.setString(0, strCaseID);
                        pstmt.setParameter(1, intVehicleID);
                        pstmt.setString(2, strMinObjid);
                        pstmt.setString(3, strMaxObjid);
                        if (null != strRuleDefID && !"".equals(strRuleDefID)) {
                            pstmt.setString(4, strRuleDefID);
                        }
                    }
                } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intVehicleID);
                    pstmt.setParameter(3, intCaseID);
                } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intVehicleID);
                    pstmt.setParameter(3, intCaseID);
                }
            }
            pstmt.setFetchSize(1000);
            List aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                ArrayList arlFaultLog = new ArrayList();
                FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
                arlDataDetails = new ArrayList();
                FaultDetailsServiceVO objFaultDetailsServiceVO = null;
                DateFormat zoneFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                // aliasToValueMapList.
                GetToolDsParminfoServiceVO objParminfoServiceVO;
                for (int i = 0; i < aliasToValueMapList.size(); i++) {

                    Object[] objMap = (Object[]) aliasToValueMapList.get(i);
                    objFaultLogServiceVO = new FaultLogServiceVO();
                    arlDataDetails = new ArrayList();
                    strNotch = null;
                    for (int j = 0; j < loopSize; j++) {
                        String strTmpDataHolder = null;
                        if (null != objMap[j]) {
                            if (objMap[j] instanceof java.util.Date) {
                                strTmpDataHolder = zoneFormater.format(objMap[j]);
                            } else if (objMap[j] instanceof java.math.BigDecimal) {
                                strTmpDataHolder = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[j]);
                            } else {
                                strTmpDataHolder = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                            if (j == 13) {// MP_0102 - Throttle Notch. Order is
                                          // important in the Query.
                                strNotch = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                        } else {
                            strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                        }
                        objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                        objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                        arlDataDetails.add(objFaultDetailsServiceVO);
                    }

                    objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                    objFaultLogServiceVO.setStrNotch(strNotch);
                    arlFaultLog.add(objFaultLogServiceVO);
                }
                /*
                 * modified added -1 since total no of records are also fetched
                 * as a part of the query
                 */
                /*
                 * added to set the total number of records available in DB on
                 * the whole
                 */
                objFaultServiceVO.setArlFaultData(arlFaultLog);

                /* Added */

            } else {
                objFaultServiceVO.setArlFaultData((ArrayList) aliasToValueMapList);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Fault Data Details : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing the ResultSet for Data: " + ex);
                throw ex;
            }
        }
        FaultLogHelper.debug("End getFaultData :" + System.currentTimeMillis());
        return objFaultServiceVO;
    }

    
    
    @Override
    public FaultMobileServiceVO getMobileFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        // this gets all the data
        FaultLogHelper.debug("Start getFaultData :" + System.currentTimeMillis());
        Session session = null;
        Query pstmt = null;
        List rs = null;

        ArrayList arlDataDetails = new ArrayList();
        String strNotch = null;
        StringBuilder sbDataQuery = new StringBuilder();

        String strIsJdpadCbrCrit = null;
        String strCaseFrom = null;
        /* For Pagination */
        FaultMobileServiceVO objFaultServiceVO = new FaultMobileServiceVO();
        int loopSize = arlHeaderDetails.size();
        try {
            strIsJdpadCbrCrit = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objFaultRequestVO.getJdpadCbrCrit());
            strCaseFrom = objFaultRequestVO.getCaseFrom();
            session = getHibernateSession();
            sbDataQuery = getMobileFaultQuery(objFaultRequestVO, arlHeaderDetails,
                    (ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strControllerCfg)));
            pstmt = session.createSQLQuery(sbDataQuery.toString());
            String strNoOfDays = objFaultRequestVO.getNoOfDays();
            String strRuleDefID = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, objFaultRequestVO.getRuleDefID());
            FaultLogHelper.debug("strCaseID :" + strCaseID);
            FaultLogHelper.debug("intVehicleID :" + intVehicleID);
            FaultLogHelper.debug(" strMinObjid :" + strMinObjid);
            FaultLogHelper.debug(" strMaxObjid :" + strMaxObjid);
            FaultLogHelper.debug(" strRuleDefID :" + strRuleDefID);

            if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                if (STR_Y.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strNoOfDays);
                    pstmt.setParameter(1, intVehicleID);
                } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strCreationTime);
                    pstmt.setString(1, strNoOfDays);
                    pstmt.setString(2, strCreationTime);
                    pstmt.setParameter(3, intVehicleID);

                } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strNoOfDays);
                    pstmt.setParameter(1, intVehicleID);
                    if (null != strMaxUpdatedDate) {
                        pstmt.setString(2, strMaxUpdatedDate);
                        pstmt.setParameter(3, intCaseID);

                    } else {
                        pstmt.setParameter(2, intCaseID);
                    }
                } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intCaseID);
                    pstmt.setParameter(3, intVehicleID);
                }
            } else {
                if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                        pstmt.setString(0, strMinObjid);
                        pstmt.setString(1, strMaxObjid);
                        pstmt.setParameter(2, intVehicleID);
                        pstmt.setParameter(3, intCaseID);

                    } else {
                        FaultLogHelper.debug("strCaseID :" + strCaseID);
                        FaultLogHelper.debug("intVehicleID :" + intVehicleID);
                        FaultLogHelper.debug(" strMinObjid :" + strMinObjid);
                        FaultLogHelper.debug(" strMaxObjid :" + strMaxObjid);
                        FaultLogHelper.debug(" strRuleDefID :" + strRuleDefID);

                        pstmt.setString(0, strCaseID);
                        pstmt.setParameter(1, intVehicleID);
                        pstmt.setString(2, strMinObjid);
                        pstmt.setString(3, strMaxObjid);
                        if (null != strRuleDefID && !"".equals(strRuleDefID)) {
                            pstmt.setString(4, strRuleDefID);
                        }
                    }
                } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intVehicleID);
                    pstmt.setParameter(3, intCaseID);
                } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    pstmt.setString(0, strMinObjid);
                    pstmt.setString(1, strMaxObjid);
                    pstmt.setParameter(2, intVehicleID);
                    pstmt.setParameter(3, intCaseID);
                }
            }
            pstmt.setFetchSize(1000);
            List aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                ArrayList arlFaultLog = new ArrayList();
                FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
                arlDataDetails = new ArrayList();
                FaultDetailsServiceVO objFaultDetailsServiceVO = null;
                DateFormat zoneFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                // aliasToValueMapList.
                GetToolDsParminfoServiceVO objParminfoServiceVO;
                for (int i = 0; i < aliasToValueMapList.size(); i++) {

                    Object[] objMap = (Object[]) aliasToValueMapList.get(i);
                    objFaultLogServiceVO = new FaultLogServiceVO();
                    arlDataDetails = new ArrayList();
                    strNotch = null;
                    for (int j = 0; j < loopSize; j++) {
                        String strTmpDataHolder = null;
                        if (null != objMap[j]) {
                            if (objMap[j] instanceof java.util.Date) {
                                strTmpDataHolder = zoneFormater.format(objMap[j]);
                            } else if (objMap[j] instanceof java.math.BigDecimal) {
                                strTmpDataHolder = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[j]);
                            } else {
                                strTmpDataHolder = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                            if (j == 13) {// MP_0102 - Throttle Notch. Order is
                                          // important in the Query.
                                strNotch = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                        } else {
                            strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                        }
                        objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                        objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                        arlDataDetails.add(objFaultDetailsServiceVO);
                    }

                    objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                    objFaultLogServiceVO.setStrNotch(strNotch);
                    arlFaultLog.add(objFaultLogServiceVO);
                }
                /*
                 * modified added -1 since total no of records are also fetched
                 * as a part of the query
                 */
                /*
                 * added to set the total number of records available in DB on
                 * the whole
                 */
                objFaultServiceVO.setArlFaultData(arlFaultLog);

                /* Added */

            } else {
                objFaultServiceVO.setArlFaultData((ArrayList) aliasToValueMapList);
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Fault Data Details : " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing the ResultSet for Data: " + ex);
                throw ex;
            }
        }
        FaultLogHelper.debug("End getFaultData :" + System.currentTimeMillis());
        return objFaultServiceVO;
    }
    
    
    
    public StringBuilder getFaultQuery(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbWhereQuery = new StringBuilder();
        StringBuilder sbOrderQuery = new StringBuilder();
        StringBuilder sbDataQuery = new StringBuilder();
        FaultLogHelper.debug("Inside the get Fault Query");
        FaultLogHelper.debug("Start getFaultQuery :" + System.currentTimeMillis());
        try {
            sbSelectQuery = selectClause(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            FaultLogHelper.debug("build the select clause");
            sbWhereQuery = whereClause(strViewName, objFaultRequestVO);
            FaultLogHelper.debug("build the where clause");
            sbOrderQuery = orderClause(strViewName, objFaultRequestVO);
            FaultLogHelper.debug("build the order clause");
            sbDataQuery.append(sbSelectQuery);
            sbDataQuery.append(sbWhereQuery);
            sbDataQuery.append(sbOrderQuery);

            String strViewAll = objFaultRequestVO.getViewAll();

            if (null == strViewAll || !STR_Y.equalsIgnoreCase(strViewAll)) {
                sbDataQuery = getPagination(objFaultRequestVO, sbDataQuery, sbOrderQuery);
            }

            FaultLogHelper.debug("Data Query : " + sbDataQuery);
        } catch (Exception e) {
            FaultLogHelper.error("Error in building FaultData Query : " + e);
            throw e;
        }
        FaultLogHelper.error("End getFaultQuery :" + System.currentTimeMillis());
        return sbDataQuery;
    }
    
    
    public StringBuilder getMobileFaultQuery(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbWhereQuery = new StringBuilder();
        StringBuilder sbOrderQuery = new StringBuilder();
        StringBuilder sbDataQuery = new StringBuilder();
        FaultLogHelper.debug("Inside the get Fault Query");
        FaultLogHelper.debug("Start getFaultQuery :" + System.currentTimeMillis());
        try {
            sbSelectQuery = selectClauseForMobile(objFaultRequestVO, arlHeaderDetails, strControllerCfg);
            FaultLogHelper.debug("build the select clause");
            sbWhereQuery = whereClause(strViewName, objFaultRequestVO);
            FaultLogHelper.debug("build the where clause");
            sbOrderQuery = orderClause(strViewName, objFaultRequestVO);
            FaultLogHelper.debug("build the order clause");
            sbDataQuery.append(sbSelectQuery);
            sbDataQuery.append(sbWhereQuery);
            sbDataQuery.append(sbOrderQuery);

            String strViewAll = objFaultRequestVO.getViewAll();

            if (null == strViewAll || !STR_Y.equalsIgnoreCase(strViewAll)) {
                sbDataQuery = getPagination(objFaultRequestVO, sbDataQuery, sbOrderQuery);
            }

            FaultLogHelper.debug("Data Query : " + sbDataQuery);
        } catch (Exception e) {
            FaultLogHelper.error("Error in building FaultData Query : " + e);
            throw e;
        }
        FaultLogHelper.error("End getFaultQuery :" + System.currentTimeMillis());
        return sbDataQuery;
    }

    private StringBuilder getPagination(FaultRequestVO objFaultRequestVO, StringBuilder sbDataQuery,
            StringBuilder sbOrderQuery) throws Exception {
        FaultLogHelper.debug("inside pagination");
        StringBuilder sbAllData = new StringBuilder();
        StringBuilder sbOrder = new StringBuilder();

        try {
            String strPaginationHit = objFaultRequestVO.getPaginationHit();
            String strPaginationCounter = objFaultRequestVO.getPaginationCounter();
            int intPaginationCounter = INT_DEF_PAGINATION_COUNTER;// Default
                                                                  // value
            if (strPaginationHit == null || "".equals(strPaginationHit)) {
                strPaginationHit = STR_0;
            }
            if (strPaginationCounter != null) {
                intPaginationCounter = RMDCommonUtility.convertObjectToInt(strPaginationCounter);

            }
            int intPaginationHit = RMDCommonUtility.convertObjectToInt(strPaginationHit);
            int intStartRowNum = (intPaginationHit * intPaginationCounter) + 1;
            int intFinalRowNum = ((intPaginationHit + 1) * intPaginationCounter) + 1;

            sbOrder = paginationOrderClause(objFaultRequestVO, sbOrderQuery);

            sbAllData.append(" SELECT * FROM (SELECT   DD.*, ");
            sbAllData.append(" DENSE_RANK () OVER ( ");
            sbAllData.append(sbOrder);
            sbAllData.append(" ) RANK , COUNT(*) OVER ( ) CNT ");
            sbAllData.append(" FROM ( ");
            sbAllData.append(sbDataQuery);
            sbAllData.append(" )dd  ) ");
            sbAllData.append(" WHERE RANK BETWEEN ");
            sbAllData.append(intStartRowNum);
            sbAllData.append(" AND ");
            sbAllData.append(intFinalRowNum);
            sbAllData.append(" ORDER BY RANK ASC ");

        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        }
        return sbAllData;

    }

    public StringBuilder paginationOrderClause(FaultRequestVO objFaultRequestVO, StringBuilder sbOrderQuery)
            throws Exception {
        FaultLogHelper.debug("Inside PaginationOrderClause ");
        StringBuilder sbPaginationOrder = new StringBuilder();
        String strCase = objFaultRequestVO.getCaseFrom();
        String strIsJdpadCbrCrit = objFaultRequestVO.getJdpadCbrCrit();
        try {

            if (STR_AC6000.equalsIgnoreCase(strCtrlCfg)) {
                if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit) && (STR_CASE_CREATION.equalsIgnoreCase(strCase)
                        || STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCase))) {
                    sbPaginationOrder.append(sbOrderQuery);
                    sbPaginationOrder.append(",fltindex desc, fault_objid ,v_sample_no DESC ");
                    FaultLogHelper.debug("inside the if loop " + sbPaginationOrder.toString());
                } else {
                    sbPaginationOrder.append(sbOrderQuery);
                    FaultLogHelper.debug("inside the inner else loop " + sbPaginationOrder.toString());
                }
            } else {
                sbPaginationOrder.append(sbOrderQuery);
                FaultLogHelper.debug("inside the outer else loop " + sbPaginationOrder.toString());
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in building order clause for pagination in FaultLogDAO : " + e);
        }
        return sbPaginationOrder;

    }

    public StringBuilder selectClause(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        FaultLogHelper.debug("Start selectClause :" + System.currentTimeMillis());
        FaultLogHelper.debug("Start of select clause");
        Session session = null;
        List rsCase = null;
        List rsCaseAppend = null;
        List rs = null;
        List rsFaultClassification = null;
        List rsOrder = null;
        Query pstmt = null;
        Query pstmtCase = null;
        Query pstmtCaseAppend = null;
        Query pstmtFaultClassification = null;
        Query pstmtOrder = null;
        String strCaseQuery = null;
        String strCustomer = null;
        String strHeaderNo = null;
        String strSerialNo = null;
        String strColumnName = null;
        String strAnomInd = null;
        String strDisplayName = null;

        String strSubstring = null;
        StringBuilder sbViewQuery = new StringBuilder();
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbBuildQuery = new StringBuilder();
        StringBuilder sbCaseAppendQuery = new StringBuilder();
        StringBuilder sbFaultClassification = new StringBuilder();
        String strUpperBound = null;
        String strLowerBound = null;
        String strDataToolTipFlag = null;
        String strCaseFrom = null;
        String strIsJdpadCbrCrit = null;
        String strFormatData = null;
        String strFormattedColumn = null;
        String strFaultWithNull = null;
        String strFaultWithoutNull = null;

        StringBuilder sbOrder = new StringBuilder();
        String strNoOfDays = null;
        String strHC = null;
        String strEGU = null;
        String strRuleDefID = null;

        String strSortColumn = "offboard_load_date";
        strSortColumn = objFaultRequestVO.getSortOrder();
        try {
            strCtrlCfg = strControllerCfg;

            strCustomer = objFaultRequestVO.getCustomer();
            strCustomer = strCustomer.trim();
            strHeaderNo = objFaultRequestVO.getVehicleHeader();
            strHeaderNo = strHeaderNo.trim();
            strSerialNo = objFaultRequestVO.getSerialNo();
            strSerialNo = strSerialNo.trim();
            strCaseID = objFaultRequestVO.getCaseId();
            strCaseFrom = objFaultRequestVO.getCaseFrom();
            strIsJdpadCbrCrit = objFaultRequestVO.getJdpadCbrCrit();
            strNoOfDays = objFaultRequestVO.getNoOfDays();
            strHC = objFaultRequestVO.getHC();
            strEGU = objFaultRequestVO.getEGU();
            strRuleDefID = objFaultRequestVO.getRuleDefID();

            session = getHibernateSession();

            sbFaultClassification.append(
                    " SELECT COUNT(GTAR.FC_FAULT) FAULT_FC_WITH_NULL,COUNT(NVL(GTAR.FC_FAULT,-1)) FAULT_FC_WITHOUT_NULL ");
            sbFaultClassification
                    .append(" FROM GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC  WHERE GTAR.AR_LIST2CASE = TC.OBJID ");
            sbFaultClassification.append(" AND  TC.ID_NUMBER = ");
            sbFaultClassification.append("?");

            pstmtFaultClassification = session.createSQLQuery(sbFaultClassification.toString());
            pstmtFaultClassification.setString(0, strCaseID);
            pstmtFaultClassification.setFetchSize(10);
            rsFaultClassification = pstmtFaultClassification.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rsFaultClassification)) {
                int index = 0;
                if (index < rsFaultClassification.size()) {
                    Object data[] = (Object[]) rsFaultClassification.get(index);
                    strFaultWithNull = RMDCommonUtility.convertObjectToString(data[0]);
                    strFaultWithoutNull = RMDCommonUtility.convertObjectToString(data[1]);
                }
                if ((strFaultWithNull.equalsIgnoreCase(strFaultWithoutNull))
                        && (!(strFaultWithoutNull.equalsIgnoreCase(STR_0)))) {

                    strFaultClassified = STR_YES;

                }
            }

            sbViewQuery.append(" SELECT GRCRRV.VEHICLE_OBJID ");
            sbViewQuery.append(" FROM GETS_RMD_CTL_CFG GRCC, ");
            sbViewQuery.append(" GETS_RMD_VEHICLE GRV, ");
            sbViewQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV ");
            sbViewQuery.append(" WHERE GRCC.OBJID = GRV.VEHICLE2CTL_CFG ");
            sbViewQuery.append(" AND GRV.OBJID = GRCRRV.VEHICLE_OBJID ");
            /*sbViewQuery.append(" AND GRCRRV.ORG_ID = '");
            sbViewQuery.append(strCustomer);
            sbViewQuery.append("' ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_HDR = '");
            sbViewQuery.append(strHeaderNo);
            sbViewQuery.append("' ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_NO = '");
            sbViewQuery.append(strSerialNo);
            sbViewQuery.append("' ");
			*/
            sbViewQuery.append(" AND GRCRRV.ORG_ID =:customer ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_HDR =:assetGrpName ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_NO =:assetNumber  ");
            pstmt = session.createSQLQuery(sbViewQuery.toString());
			pstmt.setString(RMDCommonConstants.CUSTOMER, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strCustomer));
			pstmt.setString(RMDCommonConstants.ASSET_GRP_NAME, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strHeaderNo));
			pstmt.setString(RMDCommonConstants.ASSET_NUMBER, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strSerialNo));
            pstmt.setFetchSize(10);
            rs = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rs)) {
                int index = 0;
                intVehicleID = RMDCommonUtility.convertObjectToInt(rs.get(index));
            }

            strCaseQuery = " SELECT OBJID,TO_CHAR(CREATION_TIME,'YYYYMMDDHH24MISS') CREATION_TIME FROM TABLE_CASE WHERE ID_NUMBER = ? ";
            pstmtCase = session.createSQLQuery(strCaseQuery.toString());// con.prepareStatement(strCaseQuery);
            pstmtCase.setString(0, strCaseID);
            pstmtCase.setFetchSize(10);
            rsCase = pstmtCase.list();
            if (RMDCommonUtility.isCollectionNotEmpty(rsCase)) {
                int index = 0;
                if (index < rsCase.size()) {
                    Object data[] = (Object[]) rsCase.get(index);
                    intCaseID = RMDCommonUtility.convertObjectToInt(data[0].toString());
                    strCreationTime = RMDCommonUtility.convertObjectToString(data[1]);

                }
            }
            if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit) || STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)
                    || STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)
                    || STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)
                    || STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {

                sbCaseAppendQuery.append(
                        " SELECT * FROM (SELECT TO_CHAR (MAX (LAST_UPDATED_DATE), 'YYYYMMDDHH24MISS') LAST_UPDATED_DATE ");
                sbCaseAppendQuery.append(" FROM GETS_TOOLS.GETS_TOOL_RUN ");
                sbCaseAppendQuery.append(" WHERE RUN2CASE =");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) UPD, ");
                sbCaseAppendQuery.append(" (SELECT MIN (RUN_PROCESSEDMIN_OBJID) MIN_OBJID ");
                sbCaseAppendQuery.append(" FROM GETS_TOOL_RUN ");
                sbCaseAppendQuery.append(" WHERE RUN2CASE =  ");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) MIN_OBJID, ");
                sbCaseAppendQuery.append(" (SELECT MAX (RUN_PROCESSEDMAX_OBJID) MAX_OBJID ");
                sbCaseAppendQuery.append(" FROM GETS_TOOL_RUN");
                sbCaseAppendQuery.append(" WHERE RUN2CASE = ");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) MAX_OBJID ");

                pstmtCaseAppend = session.createSQLQuery(sbCaseAppendQuery.toString());// con.prepareStatement(sbCaseAppendQuery.toString());
                pstmtCaseAppend.setParameter(0, intCaseID);
                pstmtCaseAppend.setParameter(1, intCaseID);
                pstmtCaseAppend.setParameter(2, intCaseID);
                pstmtCaseAppend.setFetchSize(10);
                rsCaseAppend = pstmtCaseAppend.list();
                if (RMDCommonUtility.isCollectionNotEmpty(rsCaseAppend)) {
                    int index = 0;
                    if (index < rsCaseAppend.size()) {
                        Object data[] = (Object[]) rsCaseAppend.get(index);
                        strMaxUpdatedDate = RMDCommonUtility.convertObjectToString(data[0]);
                        strMinObjid = RMDCommonUtility.convertObjectToString(data[1]);
                        strMaxObjid = RMDCommonUtility.convertObjectToString(data[2]);
                    }

                }
            }

            if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                if ("OFFBOARD_LOAD_DATE".equalsIgnoreCase(strSortColumn)) {
                    if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                        if (STR_Y.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" WHERE ");
                            sbOrder.append(" SYSDATE - ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE = ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".occur_date < to_date('1/1/2000','DD/MM/YYYY')");
                        } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" where ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= (TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') - ");
                            sbOrder.append("?");
                            sbOrder.append(") AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".fault2vehicle =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".occur_date < to_date('1/1/2000','DD/MM/YYYY') ");

                        } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= SYSDATE AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM ");
                            sbOrder.append(" WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= (SYSDATE -");
                            sbOrder.append("?");
                            sbOrder.append(") AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT AND MTM.LAST_UPDATED_DATE <= ");

                            if (null != strMaxUpdatedDate) {
                                sbOrder.append(" TO_DATE( ");
                                sbOrder.append("?");
                                sbOrder.append(",'YYYYMMDDHH24MISS') ");
                            } else {
                                sbOrder.append(" sysdate ");
                            }
                            sbOrder.append(" AND MTM.LAST_UPDATED_BY = 'Case Repetition_1' AND MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        }
                    } else {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                                sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                                sbOrder.append(strViewName);
                                sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID BETWEEN");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(" MTM.JDPAD_USED = 'Y' AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2VEHICLE =");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                                sbOrder.append("?");
                                sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");
                            } else {
                                sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                                sbOrder.append(strViewName);
                                sbOrder.append(
                                        " ,GETS_RMD.GETS_TOOL_AR_LIST GTAR,TABLE_CASE TC,GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF,GETS_RMD.GETS_TOOL_FLTDOWN GTFD,GETS_RMD.GETS_TOOL_FAULT GTFT ");
                                sbOrder.append(" WHERE TC.ID_NUMBER = ");
                                sbOrder.append("?");
                                sbOrder.append(" AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' ");
                                sbOrder.append(" AND GTRL.RPRLDWN2AR_LIST   =  GTAR.OBJID ");
                                sbOrder.append(
                                        " AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN AND GTDF.OBJID = GTDR.RULEDEF2FINRUL ");
                                sbOrder.append(
                                        " AND GTFD.FLTDOWN2RULE_DEFN = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID = GTFT.OBJID AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2VEHICLE =");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID BETWEEN ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                            }
                        } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID BETWEEN");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(" MTM.CBR_USED = 'Y' AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");
                        } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID BETWEEN ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append("?");
                            sbOrder.append(" AND FAULT_CLASSIFICATION='Critical Fault' AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        }
                    }
                    if (STR_HC.equalsIgnoreCase(strHC)) {

                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'HC') ");
                    } else if (STR_EGU.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'EGU') ");
                    } else if (STR_HC.equalsIgnoreCase(strHC) && STR_EGU.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'HC' OR ");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'EGU')");
                    }

                    pstmtOrder = session.createSQLQuery(sbOrder.toString());// con.prepareStatement(sbOrder.toString());

                    if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                        if (STR_Y.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strNoOfDays);
                            pstmtOrder.setParameter(1, intVehicleID);
                        } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strCreationTime);
                            pstmtOrder.setString(1, strNoOfDays);
                            pstmtOrder.setString(2, strCreationTime);
                            pstmtOrder.setParameter(3, intVehicleID);
                        } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strCreationTime);
                            pstmtOrder.setParameter(1, intVehicleID);
                        } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strNoOfDays);
                            pstmtOrder.setParameter(1, intVehicleID);
                            if (null != strMaxUpdatedDate) {
                                pstmtOrder.setString(2, strMaxUpdatedDate);
                                pstmtOrder.setParameter(3, intCaseID);
                            } else {
                                pstmtOrder.setParameter(2, intCaseID);
                            }
                        }
                    } else {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                                pstmtOrder.setString(0, strMinObjid);
                                pstmtOrder.setString(1, strMaxObjid);
                                pstmtOrder.setParameter(2, intVehicleID);
                                pstmtOrder.setParameter(3, intCaseID);
                            } else {
                                pstmtOrder.setString(0, strCaseID);
                                pstmtOrder.setParameter(1, intVehicleID);
                                pstmtOrder.setString(2, strMinObjid);
                                pstmtOrder.setString(3, strMaxObjid);
                            }
                        } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            pstmtOrder.setString(0, strMinObjid);
                            pstmtOrder.setString(1, strMaxObjid);
                            pstmtOrder.setParameter(2, intVehicleID);
                            pstmtOrder.setParameter(3, intCaseID);
                        } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            pstmtOrder.setString(0, strMinObjid);
                            pstmtOrder.setString(1, strMaxObjid);
                            pstmtOrder.setParameter(2, intVehicleID);
                            pstmtOrder.setParameter(3, intCaseID);
                        }
                    }

                    pstmtOrder.setFetchSize(10);
                    rsOrder = pstmtOrder.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(rsOrder)) {
                        int index = 0;
                        if (index < rsOrder.size()) {
                            Object data[];
                            if (rsOrder.get(index) instanceof BigDecimal) {
                                data = new Object[1];
                                data[0] = rsOrder.get(index);
                            } else {
                                data = (Object[]) rsOrder.get(index);
                            }
                            strCountOccurDate = RMDCommonUtility.convertObjectToString(data[0]);
                        }
                    }
                } else {
                    strCountOccurDate = STR_0;
                }
            }

            ArrayList arlSelectDetails = new ArrayList();
            int intHeaders = 0;

            GetToolDsParminfoServiceVO objFaultHeaderDetailVO = null;
            intHeaders = arlHeaderDetails.size();
            for (int j = 0; j < intHeaders; j++) {
                objFaultHeaderDetailVO = (GetToolDsParminfoServiceVO) arlHeaderDetails.get(j);
                strColumnName = objFaultHeaderDetailVO.getColumnName();
                strAnomInd = objFaultHeaderDetailVO.getAnomInd();
                strDisplayName = objFaultHeaderDetailVO.getParmName();
                strSubstring = objFaultHeaderDetailVO.getSubstring();
                strUpperBound = objFaultHeaderDetailVO.getUpperBound().toString();
                if (strUpperBound.equalsIgnoreCase("0.0")) {
                    strUpperBound = null;
                }
                strLowerBound = objFaultHeaderDetailVO.getLowerBound().toString();
                if (strLowerBound.equalsIgnoreCase("0.0")) {
                    strLowerBound = null;
                }
                strDataToolTipFlag = objFaultHeaderDetailVO.getDataTooltipFlag();
                strFormatData = objFaultHeaderDetailVO.getFormat();

                if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)) {
                    strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$")) + strViewName + "."
                            + strColumnName
                            + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                }

                if (STR_N.equalsIgnoreCase(strAnomInd)) {
                    if (null != strUpperBound && null != strLowerBound) {
                        if (STR_Y.equalsIgnoreCase(strDataToolTipFlag)) {

                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery
                                    .append("||'^'||(SELECT    RESIDUAL_CRITICALITY_FLAG||'^'|| (EXPECTED_VALUE + (");
                            sbSelectQuery.append(" HM.LOWER_BOUND))||'^'||(EXPECTED_VALUE + ");
                            sbSelectQuery.append(" HM.UPPER_BOUND");
                            sbSelectQuery.append(
                                    ") FROM GETS_TOOLS.GETS_TOOL_ANOM_OUT ANOM,GETS_TOOLS.GETS_TOOL_ANOM_ADMIN ADM,GETS_TOOLS.GETS_TOOL_HEATMAP_PARAM HM  WHERE ANOM_OUT2FAULT = ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(
                                    ".FAULT_OBJID AND ANOM.ANOM_OUT2ANOM_ADMIN = ADM.OBJID AND ADM.DISPLAY_PARM_NAME = '");
                            sbSelectQuery.append(strDisplayName);
                            sbSelectQuery.append("' AND ANOM_OUT2VEHICLE = ");
                            sbSelectQuery.append(intVehicleID);
                            sbSelectQuery.append(" AND HM.HEATMAP2ANOMADMIN = ADM.OBJID ");
                            sbSelectQuery.append(")),null) ");
                            sbSelectQuery.append(strDisplayName);
                            sbSelectQuery.append(" , ");

                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery.append("||'^'||(case when ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" < ");
                            sbSelectQuery.append(strLowerBound);
                            sbSelectQuery.append(" THEN 'L' WHEN ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" > ");
                            sbSelectQuery.append(strUpperBound);
                            sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL) ");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" , ");

                        }
                    } else {
                        sbSelectQuery.append("NVL2(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");
                        sbSelectQuery.append(strFormattedColumn);
                        sbSelectQuery.append(",NULL)  ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(" , ");

                    }
                } else // non anom columns
                {

                    if (STR_OCCUR_TIME.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_TIME.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {

                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(", 2),  'FM999999999999.00' ) ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");

                    } else if (STR_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                            || STR_EDP_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(", 1),  'FM99999990.0' ) ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");

                    }

                    else if (STR_FAULT_CFG.equalsIgnoreCase(strColumnName)
                            && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append("gets_sd_fltlog_samp_rt_pkg.get_sample_rt_fltlog_fn(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(") sample_rate,");

                    } else if (STR_A_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_B_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_L_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_M_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_O_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_S_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_V_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_W_STATUS.equalsIgnoreCase(strColumnName)) {
                        String strColumn = strColumnName.substring(0, 1);
                        strColumn = strColumn.toUpperCase();
                        sbSelectQuery.append("DECODE (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",1, '");
                        sbSelectQuery.append(strColumn);
                        sbSelectQuery.append("' , ' '),");

                    }

                    else if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_DATE.equalsIgnoreCase(strColumnName)
                            || STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)
                            || STR_CMU_TIME.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" TO_CHAR(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        // if (STR_AC.equalsIgnoreCase(strCtrlCfg)
                        // || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                        // sbSelectQuery.append(",'MM/DD/YYYY HH24:MI')");
                        // } else {
                        sbSelectQuery.append(",'MM/DD/YYYY HH24:MI:SS')");
                        // }
                        if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ocur_date");
                        } else if (STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ofboard_load_date");
                        } else {
                            sbSelectQuery.append(strColumnName);
                        }
                        sbSelectQuery.append(",");

                    } else if (STR_TOOL_RUN_IND.equalsIgnoreCase(strColumnName)) {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)
                                && !STR_YES.equalsIgnoreCase(strFaultClassified)) {
                            sbSelectQuery.append(" '*' toolrunind, ");

                        } else {
                            sbSelectQuery.append("	DECODE((SELECT NVL2 ( ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT2RULE_DEFN, ");
                            sbSelectQuery.append(" 'F', NULL ) FROM GETS_TOOL_CASE_MTM_FAULT MTM  ");
                            sbSelectQuery.append(" WHERE ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT_OBJID =  MTM.MTM2FAULT ");
                            sbSelectQuery.append(" AND ");
                            sbSelectQuery.append(" MTM.MTM2CASE = ");
                            sbSelectQuery.append(intCaseID);
                            sbSelectQuery.append(" ), ");
                            sbSelectQuery.append(" 'F','F', ");
                            sbSelectQuery.append(" (DECODE (( SELECT '*' ");
                            sbSelectQuery.append(" FROM GETS_TOOL_CASE_MTM_FAULT MTM ");
                            sbSelectQuery.append(" WHERE ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT_OBJID =  MTM.MTM2FAULT ");
                            sbSelectQuery.append(" AND ");
                            sbSelectQuery.append(" MTM.MTM2CASE = ");
                            sbSelectQuery.append(intCaseID);
                            sbSelectQuery.append(" ),'*','*',NULL)) ");
                            sbSelectQuery.append(" ) TOOLRUNIND, ");

                        }
                    } else if (STR_EX_SNAPSHOT_FLG.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" DECODE(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".EX_SNAPSHOT_FLG,1,'*'), ");

                    } else {
                        if (null != strUpperBound && null != strLowerBound) {
                            if (STR_Y.equalsIgnoreCase(strDataToolTipFlag)) {
                                // need to add the tooltip query
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(case when ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" then 'L' when ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL)  ");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" , ");

                            } else {
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(CASE WHEN ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" THEN 'L' WHEN ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL)  ");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" , ");

                            }
                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(", ");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery.append(",null)  ");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" , ");

                        }
                    }

                } // end non anom
            } // end of for loop of headers

            // end of group loop
            sbBuildQuery.append(" Select distinct ");
            sbBuildQuery.append(sbSelectQuery);
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".proximity_desc, ");

            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".OCCUR_DATE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".OFFBOARD_LOAD_DATE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".RECORD_TYPE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_STRATEGY_OBJID, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_ORIGIN, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_INDEX FLTINDEX, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".SAMPLE_NO V_SAMPLE_NO FROM  ");

            sbBuildQuery.append(strViewName);
            sbBuildQuery.append("  ");

        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
                throw e;
            }

        }
        FaultLogHelper.debug("End selectClause :" + System.currentTimeMillis());
        return sbBuildQuery;
    }

    public StringBuilder whereClause(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        StringBuilder sbWhereQuery = new StringBuilder();
        String strSortColumn = "offboard_load_date";
        String strDefaultRecType = null;
        String dataset = null;
        FaultLogHelper.debug("Start whereClause :" + System.currentTimeMillis());

        try {
            dataset = objFaultRequestVO.getVVfViewRadioSelected();
            strDefaultRecType = getFaultsforDataset(dataset, strViewName);
            String strCaseFrom = objFaultRequestVO.getCaseFrom();
            String strIsJdpadCbrCrit = objFaultRequestVO.getJdpadCbrCrit();
            String strHC = objFaultRequestVO.getHC();
            String strEGU = objFaultRequestVO.getEGU();
            String strNotch = objFaultRequestVO.getNotch();

            String strFilter = objFaultRequestVO.getFilter();

            strSortColumn = objFaultRequestVO.getSortOrder();

            String strCaseType = objFaultRequestVO.getCaseType();

            String strInitialLoad = objFaultRequestVO.getInitialLoad();

            String strRuleDefID = objFaultRequestVO.getRuleDefID();

            /* Added by IGS on 08/26/2009 for Faultcode search.. Start */
            String strFaultCode = objFaultRequestVO.getFaultCode();
            /* Added by IGS on 08/26/2009 for Faultcode search.. End */

            if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                if (STR_Y.equalsIgnoreCase(strCaseFrom)) {
                    sbWhereQuery.append(" WHERE SYSDATE+");
                    sbWhereQuery.append(STR_GMT);
                    sbWhereQuery.append("-");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append(strSortColumn);
                    sbWhereQuery.append("<= ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append("+");
                    sbWhereQuery.append(STR_GMT);
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2VEHICLE = ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault_code <> 'ANM' ");

                } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {
                    sbWhereQuery.append(" WHERE ( ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append(strSortColumn);
                    sbWhereQuery.append(" >= (TO_DATE (");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(", 'YYYYMMDDHH24MISS') - ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" ))");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append(strSortColumn);
                    sbWhereQuery.append(" <= TO_DATE (");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(", 'YYYYMMDDHH24MISS')");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault2vehicle = ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault_code <> 'ANM' ");

                } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                    sbWhereQuery.append(" WHERE ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append(strSortColumn);
                    sbWhereQuery.append(" >= (SYSDATE+ ");
                    sbWhereQuery.append(STR_GMT);
                    sbWhereQuery.append("- ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append("+ ");
                    sbWhereQuery.append(STR_GMT);
                    sbWhereQuery.append(") AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2VEHICLE = ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_OBJID = MTM.MTM2FAULT");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault_code <> 'ANM' ");

                    if (null != strMaxUpdatedDate) {
                        sbWhereQuery.append(" AND MTM.LAST_UPDATED_DATE <= TO_DATE ( ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(", 'YYYYMMDDHH24MISS') AND MTM.LAST_UPDATED_BY = 'CASE REPETITION_1'");

                    } else {
                        sbWhereQuery.append(" AND MTM.LAST_UPDATED_DATE <= SYSDATE+ ");
                        sbWhereQuery.append(STR_GMT);
                        sbWhereQuery.append(" AND MTM.LAST_UPDATED_BY = 'CASE REPETITION_1'");

                    }
                    sbWhereQuery.append(" AND MTM.MTM2CASE = ");
                    sbWhereQuery.append("?");

                } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {
                    sbWhereQuery.append(" WHERE ");

                    sbWhereQuery.append("(TO_CHAR ( ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append(strSortColumn);
                    sbWhereQuery.append(", 'YYYYMMDDHH24MISS') ");
                    sbWhereQuery.append(">=");
                    sbWhereQuery.append(strCreationTime);
                    sbWhereQuery.append(" or (");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append("FAULT_OBJID BETWEEN ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(" (MTM.MTM2CASE = ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" OR ");
                    sbWhereQuery.append(" MTM.MTM2CASE IS NULL) ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".");
                    sbWhereQuery.append("FAULT2RULE_DEFN IS NULL)) ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".OFFBOARD_LOAD_DATE <= SYSDATE+ ");
                    sbWhereQuery.append(STR_GMT);
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2VEHICLE = ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault_code <> 'ANM'");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".fault_objid = mtm.mtm2fault(+) ");

                }

                if (STR_Y.equalsIgnoreCase(strInitialLoad)) {
                    if (STR_ESTP_PROBLEM.equalsIgnoreCase(strCaseType)) {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(" to_char( ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".OFFBOARD_LOAD_DATE+1,'YYYYMMDDHH24MISS') >= ");
                        sbWhereQuery.append(strCreationTime);
                        sbWhereQuery.append(" ");

                    } else if (STR_EOA_PROBLEM.equalsIgnoreCase(strCaseType)) {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(" to_char( ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".OFFBOARD_LOAD_DATE+7,'YYYYMMDDHH24MISS') >= ");
                        sbWhereQuery.append(strCreationTime);
                        sbWhereQuery.append(" ");

                    }
                }

                if (!"".equals(strDefaultRecType)) {
                    sbWhereQuery.append(strDefaultRecType);
                }

                if (STR_HC.equalsIgnoreCase(strHC) && STR_EGU.equalsIgnoreCase(strEGU)) {
                    sbWhereQuery.append(" AND (");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".RECORD_TYPE = 'HC' OR ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".RECORD_TYPE = 'EGU')");

                } else if (STR_HC.equalsIgnoreCase(strHC)) {

                    sbWhereQuery.append(" AND (");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".record_type = 'HC') ");
                } else if (STR_EGU.equalsIgnoreCase(strEGU)) {
                    sbWhereQuery.append(" AND (");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".RECORD_TYPE = 'EGU') ");
                }

                if (STR_NOTCH8.equalsIgnoreCase(strNotch)) {
                    if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".notch = '8' ");
                    } else {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".BASIC_NOTCH = '8' ");
                    }
                }

                if (STR_HIDE_FILTER.equalsIgnoreCase(strFilter)) {
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2RULE_DEFN is null ");
                }
            } else {
                if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                        sbWhereQuery.append(" WHERE ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_OBJID BETWEEN ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND MTM.JDPAD_USED = 'Y' ");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT2VEHICLE =");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT2RULE_DEFN IS NULL  AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_CODE <> 'ANM' AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" OR MTM.MTM2CASE IS NULL) ");

                    } else {

                        sbWhereQuery.append(" WHERE ");
                        sbWhereQuery.append(" tc.id_number = ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND GTAR.AR_LIST2CASE = TC.OBJID ");
                        sbWhereQuery.append(" AND GTAR.TOOL_ID = 'JDPAD' ");
                        sbWhereQuery.append(" AND GTRL.RPRLDWN2AR_LIST = GTAR.OBJID ");
                        sbWhereQuery.append(" AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN ");
                        sbWhereQuery.append(" AND GTDF.OBJID = GTDR.RULEDEF2FINRUL ");
                        sbWhereQuery.append(" AND GTFD.FLTDOWN2RULE_DEFN = GTDR.OBJID ");
                        sbWhereQuery.append(" AND GTFT.OBJID = GTFD.FLTDOWN2FAULT ");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_OBJID = GTFT.OBJID ");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT2VEHICLE =");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_CODE <> 'ANM' ");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".FAULT_OBJID BETWEEN ");
                        sbWhereQuery.append("?");
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append("?");
                        if (null != strRuleDefID && !"".equals(strRuleDefID)) {
                            sbWhereQuery.append(" AND ");
                            sbWhereQuery.append(" FLTDOWN2RULE_DEFN = ");
                            sbWhereQuery.append("?");
                        }

                    }

                } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    sbWhereQuery.append(" WHERE ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_OBJID BETWEEN ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND MTM.CBR_USED = 'Y' AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2VEHICLE =");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(" .FAULT2RULE_DEFN IS NULL ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_OBJID = MTM.MTM2FAULT(+) ");
                    sbWhereQuery.append(" AND (MTM.MTM2CASE =");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" OR MTM.MTM2CASE IS NULL) ");

                } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                    sbWhereQuery.append(" WHERE ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_OBJID BETWEEN ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND FAULT_CLASSIFICATION = 'CRITICAL FAULT' ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2VEHICLE =");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT2RULE_DEFN IS NULL ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_CODE <> 'ANM' ");
                    sbWhereQuery.append(" AND ");
                    sbWhereQuery.append(strViewName);
                    sbWhereQuery.append(".FAULT_OBJID = MTM.MTM2FAULT(+) ");
                    sbWhereQuery.append(" AND (MTM.MTM2CASE =");
                    sbWhereQuery.append("?");
                    sbWhereQuery.append(" OR MTM.MTM2CASE IS NULL) ");

                }

                if (!"".equals(strDefaultRecType)) {
                    sbWhereQuery.append(strDefaultRecType);
                }

                if (STR_NOTCH8.equalsIgnoreCase(strNotch)) {
                    if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".NOTCH = '8' ");
                    } else {
                        sbWhereQuery.append(" AND ");
                        sbWhereQuery.append(strViewName);
                        sbWhereQuery.append(".BASIC_NOTCH = '8' ");
                    }
                }

            }

            /*
             * Added by IGS on 08/26/2009 for Faultcode search in DataScreen..
             * Start
             */
            if (null != strFaultCode && !"".equals(strFaultCode.trim())) {
                sbWhereQuery.append(" AND UPPER ( ");
                sbWhereQuery.append(strViewName);
                sbWhereQuery.append(".FAULT_CODE )  LIKE  ");
                sbWhereQuery.append(" UPPER ('%");
                sbWhereQuery.append(strFaultCode.trim());
                sbWhereQuery.append("%')");
            }
            /*
             * Added by IGS on 08/26/2009 for Faultcode search in DataScreen..
             * End
             */

        } catch (Exception e) {
            FaultLogHelper.error("Error in building the where clause : " + e);
            throw e;

        }
        FaultLogHelper.debug("End whereClause :" + System.currentTimeMillis());
        FaultLogHelper.debug("finshing the where cluase");
        return sbWhereQuery;
    }

    public StringBuilder orderClause(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        FaultLogHelper.debug("Start orderClause :" + System.currentTimeMillis());
        FaultLogHelper.debug("Starting the order clause");
        StringBuilder sbOrderQuery = new StringBuilder();
        String strCase = objFaultRequestVO.getCaseFrom();
        String strSortColumn = "offboard_load_date";
        String strJdpadCbrCrit = objFaultRequestVO.getJdpadCbrCrit();

        try {

            strSortColumn = objFaultRequestVO.getSortOrder();

            sbOrderQuery.append(" ORDER BY ");

            if (STR_AC6000.equalsIgnoreCase(strCtrlCfg)) {
                if (STR_Y.equalsIgnoreCase(strCase) || STR_CASE_APPENDED.equalsIgnoreCase(strCase)) {
                    if (STR_0.equalsIgnoreCase(strCountOccurDate)) {
                        sbOrderQuery.append(commonOrderClause(strSortColumn, strJdpadCbrCrit));
                        sbOrderQuery.append(" ,TOOLRUNIND ");

                    } else {

                        sbOrderQuery.append(commonOrderClause("offboard_load_date", strJdpadCbrCrit));
                        sbOrderQuery.append(",toolrunind ");

                    }

                } else if (STR_CASE_CREATION.equalsIgnoreCase(strCase)
                        || STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCase)) {
                    if (STR_0.equalsIgnoreCase(strCountOccurDate)) {
                        sbOrderQuery.append(strSortColumn);
                        sbOrderQuery.append(" DESC ");
                    } else {
                        sbOrderQuery.append(" OFFBOARD_LOAD_DATE DESC ");
                    }
                } else if (STR_JDPAD.equalsIgnoreCase(strJdpadCbrCrit) || STR_CBR.equalsIgnoreCase(strJdpadCbrCrit)
                        || STR_CRITICAL_FAULT.equalsIgnoreCase(strJdpadCbrCrit)) {
                    if (STR_0.equalsIgnoreCase(strCountOccurDate)) {
                        sbOrderQuery.append(commonOrderClause(strSortColumn, strJdpadCbrCrit));
                        sbOrderQuery.append(" ,TOOLRUNIND ");
                    } else {

                        sbOrderQuery.append(commonOrderClause("OFFBOARD_LOAD_DATE", strJdpadCbrCrit));
                        sbOrderQuery.append(" ,TOOLRUNIND ");

                    }

                }

            } else {
                if (STR_ACCCA.equalsIgnoreCase(strCtrlCfg) || STR_DCCCA.equalsIgnoreCase(strCtrlCfg)) {
                    sbOrderQuery.append(commonOrderClause(strSortColumn, strJdpadCbrCrit));
                    sbOrderQuery.append(",");
                    sbOrderQuery.append(" SDP_FLAG ASC ");

                } else if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                    sbOrderQuery.append(commonOrderClause(strSortColumn, strJdpadCbrCrit));
                    sbOrderQuery.append(" ,TOOLRUNIND ");
                }
                // Added by IGS on 04/13/2010 for KTZ
                else {
                    sbOrderQuery.append(strSortColumn);
                    sbOrderQuery.append(" DESC ");
                }
            }

        } catch (Exception e) {
            FaultLogHelper.error("Exception in building the order clause : " + e);
            throw e;
        }
        FaultLogHelper.debug("End orderClause :" + System.currentTimeMillis());
        FaultLogHelper.debug("order clause is finished");
        return sbOrderQuery;
    }

    public StringBuilder commonOrderClause(String strSortColumn, String strJdpadCbrCrit) throws Exception {
        StringBuilder sbCommomOrder = new StringBuilder();

        try {

            sbCommomOrder.append(strSortColumn);
            sbCommomOrder.append(" DESC, ");
            sbCommomOrder.append(" FLTINDEX DESC, ");
            sbCommomOrder.append(" FAULT_OBJID, ");
            sbCommomOrder.append(" V_SAMPLE_NO DESC ");

        } catch (Exception e) {
            FaultLogHelper.error("Exception in building Common Order Clause : " + e);
            throw e;
        }
        return sbCommomOrder;
    }

    public String getDefaultRecordTypes(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        Session session = null;
        Query pstmtDefRecType = null;
        List rsDefRecType = null;
        StringBuilder sbDefaultRecType = new StringBuilder();
        String strDefaultRecType = "";
        try {

            String strCaseType = objFaultRequestVO.getCaseType();

            session = getHibernateSession();
            sbDefaultRecType.append(" SELECT RECORD_TYPE ");
            sbDefaultRecType.append(" FROM GETS_SD.GETS_SD_CASETYPE_TO_FLT ");
            sbDefaultRecType.append(" WHERE CASE_PROBLEM_TYPE = ");
            sbDefaultRecType.append("?");
            pstmtDefRecType = session.createSQLQuery(sbDefaultRecType.toString());
            pstmtDefRecType.setString(0, strCaseType);
            rsDefRecType = pstmtDefRecType.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rsDefRecType)) {

                for (int index = 0; index < rsDefRecType.size(); index++) {
                    Object data[] = (Object[]) rsDefRecType.get(index);
                    strDefaultRecType += "'" + RMDCommonUtility.convertObjectToString(data[0]) + "',";

                }
            }
            if (!"".equals(strDefaultRecType)) {
                strDefaultRecType = strDefaultRecType.substring(0, strDefaultRecType.length() - 1);
                strDefaultRecType = "  AND " + strViewName + ".RECORD_TYPE IN(" + strDefaultRecType + ")";
            }

        } catch (Exception e) {
            FaultLogHelper.error("Error in getting Default Record Types in FaultLogDAO : " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for Default Record Types : " + ex);
                // throw ex;
            }

        }
        return strDefaultRecType;
    }
    
    
    public StringBuilder selectClauseForMobile(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        FaultLogHelper.debug("Start selectClause :" + System.currentTimeMillis());
        FaultLogHelper.debug("Start of select clause");
        Session session = null;
        List rsCase = null;
        List rsCaseAppend = null;
        List rs = null;
        List rsFaultClassification = null;
        List rsOrder = null;
        Query pstmt = null;
        Query pstmtCase = null;
        Query pstmtCaseAppend = null;
        Query pstmtFaultClassification = null;
        Query pstmtOrder = null;
        String strCaseQuery = null;
        String strCustomer = null;
        String strHeaderNo = null;
        String strSerialNo = null;
        String strColumnName = null;
        String strAnomInd = null;
        String strDisplayName = null;

        String strSubstring = null;
        StringBuilder sbViewQuery = new StringBuilder();
        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbBuildQuery = new StringBuilder();
        StringBuilder sbCaseAppendQuery = new StringBuilder();
        StringBuilder sbFaultClassification = new StringBuilder();
        String strUpperBound = null;
        String strLowerBound = null;
        String strDataToolTipFlag = null;
        String strCaseFrom = null;
        String strIsJdpadCbrCrit = null;
        String strFormatData = null;
        String strFormattedColumn = null;
        String strFaultWithNull = null;
        String strFaultWithoutNull = null;

        StringBuilder sbOrder = new StringBuilder();
        String strNoOfDays = null;
        String strHC = null;
        String strEGU = null;
        String strRuleDefID = null;

        String strSortColumn = "offboard_load_date";
        strSortColumn = objFaultRequestVO.getSortOrder();
        try {
            strCtrlCfg = strControllerCfg;

            strCustomer = objFaultRequestVO.getCustomer();
            strCustomer = strCustomer.trim();
            strHeaderNo = objFaultRequestVO.getVehicleHeader();
            strHeaderNo = strHeaderNo.trim();
            strSerialNo = objFaultRequestVO.getSerialNo();
            strSerialNo = strSerialNo.trim();
            strCaseID = objFaultRequestVO.getCaseId();
            strCaseFrom = objFaultRequestVO.getCaseFrom();
            strIsJdpadCbrCrit = objFaultRequestVO.getJdpadCbrCrit();
            strNoOfDays = objFaultRequestVO.getNoOfDays();
            strHC = objFaultRequestVO.getHC();
            strEGU = objFaultRequestVO.getEGU();
            strRuleDefID = objFaultRequestVO.getRuleDefID();

            session = getHibernateSession();

            sbFaultClassification.append(
                    " SELECT COUNT(GTAR.FC_FAULT) FAULT_FC_WITH_NULL,COUNT(NVL(GTAR.FC_FAULT,-1)) FAULT_FC_WITHOUT_NULL ");
            sbFaultClassification
                    .append(" FROM GETS_TOOL_AR_LIST GTAR ,TABLE_CASE TC  WHERE GTAR.AR_LIST2CASE = TC.OBJID ");
            sbFaultClassification.append(" AND  TC.ID_NUMBER = ");
            sbFaultClassification.append("?");

            pstmtFaultClassification = session.createSQLQuery(sbFaultClassification.toString());
            pstmtFaultClassification.setString(0, strCaseID);
            pstmtFaultClassification.setFetchSize(10);
            rsFaultClassification = pstmtFaultClassification.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rsFaultClassification)) {
                int index = 0;
                if (index < rsFaultClassification.size()) {
                    Object data[] = (Object[]) rsFaultClassification.get(index);
                    strFaultWithNull = RMDCommonUtility.convertObjectToString(data[0]);
                    strFaultWithoutNull = RMDCommonUtility.convertObjectToString(data[1]);
                }
                if ((strFaultWithNull.equalsIgnoreCase(strFaultWithoutNull))
                        && (!(strFaultWithoutNull.equalsIgnoreCase(STR_0)))) {

                    strFaultClassified = STR_YES;

                }
            }

            sbViewQuery.append(" SELECT GRCRRV.VEHICLE_OBJID ");
            sbViewQuery.append(" FROM GETS_RMD_CTL_CFG GRCC, ");
            sbViewQuery.append(" GETS_RMD_VEHICLE GRV, ");
            sbViewQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV ");
            sbViewQuery.append(" WHERE GRCC.OBJID = GRV.VEHICLE2CTL_CFG ");
            sbViewQuery.append(" AND GRV.OBJID = GRCRRV.VEHICLE_OBJID ");
            /*sbViewQuery.append(" AND GRCRRV.ORG_ID = '");
            sbViewQuery.append(strCustomer);
            sbViewQuery.append("' ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_HDR = '");
            sbViewQuery.append(strHeaderNo);
            sbViewQuery.append("' ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_NO = '");
            sbViewQuery.append(strSerialNo);
            sbViewQuery.append("' ");
			*/
            sbViewQuery.append(" AND GRCRRV.ORG_ID =:customer ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_HDR =:assetGrpName ");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_NO =:assetNumber  ");
            pstmt = session.createSQLQuery(sbViewQuery.toString());
			pstmt.setString(RMDCommonConstants.CUSTOMER, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strCustomer));
			pstmt.setString(RMDCommonConstants.ASSET_GRP_NAME, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strHeaderNo));
			pstmt.setString(RMDCommonConstants.ASSET_NUMBER, ESAPI.encoder()
					.encodeForSQL(ORACLE_CODEC, strSerialNo));
            pstmt.setFetchSize(10);
            rs = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rs)) {
                int index = 0;
                intVehicleID = RMDCommonUtility.convertObjectToInt(rs.get(index));
            }

            strCaseQuery = " SELECT OBJID,TO_CHAR(CREATION_TIME,'YYYYMMDDHH24MISS') CREATION_TIME FROM TABLE_CASE WHERE ID_NUMBER = ? ";
            pstmtCase = session.createSQLQuery(strCaseQuery.toString());// con.prepareStatement(strCaseQuery);
            pstmtCase.setString(0, strCaseID);
            pstmtCase.setFetchSize(10);
            rsCase = pstmtCase.list();
            if (RMDCommonUtility.isCollectionNotEmpty(rsCase)) {
                int index = 0;
                if (index < rsCase.size()) {
                    Object data[] = (Object[]) rsCase.get(index);
                    intCaseID = RMDCommonUtility.convertObjectToInt(data[0].toString());
                    strCreationTime = RMDCommonUtility.convertObjectToString(data[1]);

                }
            }
            if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit) || STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)
                    || STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)
                    || STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)
                    || STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {

                sbCaseAppendQuery.append(
                        " SELECT * FROM (SELECT TO_CHAR (MAX (LAST_UPDATED_DATE), 'YYYYMMDDHH24MISS') LAST_UPDATED_DATE ");
                sbCaseAppendQuery.append(" FROM GETS_TOOLS.GETS_TOOL_RUN ");
                sbCaseAppendQuery.append(" WHERE RUN2CASE =");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) UPD, ");
                sbCaseAppendQuery.append(" (SELECT MIN (RUN_PROCESSEDMIN_OBJID) MIN_OBJID ");
                sbCaseAppendQuery.append(" FROM GETS_TOOL_RUN ");
                sbCaseAppendQuery.append(" WHERE RUN2CASE =  ");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) MIN_OBJID, ");
                sbCaseAppendQuery.append(" (SELECT MAX (RUN_PROCESSEDMAX_OBJID) MAX_OBJID ");
                sbCaseAppendQuery.append(" FROM GETS_TOOL_RUN");
                sbCaseAppendQuery.append(" WHERE RUN2CASE = ");
                sbCaseAppendQuery.append("?");
                sbCaseAppendQuery.append(" AND RUN_CPT IS NOT NULL) MAX_OBJID ");

                pstmtCaseAppend = session.createSQLQuery(sbCaseAppendQuery.toString());// con.prepareStatement(sbCaseAppendQuery.toString());
                pstmtCaseAppend.setParameter(0, intCaseID);
                pstmtCaseAppend.setParameter(1, intCaseID);
                pstmtCaseAppend.setParameter(2, intCaseID);
                pstmtCaseAppend.setFetchSize(10);
                rsCaseAppend = pstmtCaseAppend.list();
                if (RMDCommonUtility.isCollectionNotEmpty(rsCaseAppend)) {
                    int index = 0;
                    if (index < rsCaseAppend.size()) {
                        Object data[] = (Object[]) rsCaseAppend.get(index);
                        strMaxUpdatedDate = RMDCommonUtility.convertObjectToString(data[0]);
                        strMinObjid = RMDCommonUtility.convertObjectToString(data[1]);
                        strMaxObjid = RMDCommonUtility.convertObjectToString(data[2]);
                    }

                }
            }

            if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                if ("OFFBOARD_LOAD_DATE".equalsIgnoreCase(strSortColumn)) {
                    if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                        if (STR_Y.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" WHERE ");
                            sbOrder.append(" SYSDATE - ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE = ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".occur_date < to_date('1/1/2000','DD/MM/YYYY')");
                        } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" where ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= (TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') - ");
                            sbOrder.append("?");
                            sbOrder.append(") AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".fault2vehicle =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".occur_date < to_date('1/1/2000','DD/MM/YYYY') ");

                        } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {

                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= TO_DATE(");
                            sbOrder.append("?");
                            sbOrder.append(",'YYYYMMDDHH24MISS') AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" <= SYSDATE AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM ");
                            sbOrder.append(" WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".");
                            sbOrder.append(strSortColumn);
                            sbOrder.append(" >= (SYSDATE -");
                            sbOrder.append("?");
                            sbOrder.append(") AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT AND MTM.LAST_UPDATED_DATE <= ");

                            if (null != strMaxUpdatedDate) {
                                sbOrder.append(" TO_DATE( ");
                                sbOrder.append("?");
                                sbOrder.append(",'YYYYMMDDHH24MISS') ");
                            } else {
                                sbOrder.append(" sysdate ");
                            }
                            sbOrder.append(" AND MTM.LAST_UPDATED_BY = 'Case Repetition_1' AND MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        }
                    } else {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                                sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                                sbOrder.append(strViewName);
                                sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID BETWEEN");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(" MTM.JDPAD_USED = 'Y' AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2VEHICLE =");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                                sbOrder.append("?");
                                sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");
                            } else {
                                sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                                sbOrder.append(strViewName);
                                sbOrder.append(
                                        " ,GETS_RMD.GETS_TOOL_AR_LIST GTAR,TABLE_CASE TC,GETS_RMD.GETS_TOOL_RPRLDWN GTRL,GETS_RMD.GETS_TOOL_DPD_RULEDEF GTDR,GETS_RMD.GETS_TOOL_DPD_FINRUL GTDF,GETS_RMD.GETS_TOOL_FLTDOWN GTFD,GETS_RMD.GETS_TOOL_FAULT GTFT ");
                                sbOrder.append(" WHERE TC.ID_NUMBER = ");
                                sbOrder.append("?");
                                sbOrder.append(" AND GTAR.AR_LIST2CASE = TC.OBJID AND gtar.TOOL_ID = 'JDPAD' ");
                                sbOrder.append(" AND GTRL.RPRLDWN2AR_LIST   =  GTAR.OBJID ");
                                sbOrder.append(
                                        " AND GTDR.OBJID = GTRL.RPRLDWN2RULE_DEFN AND GTDF.OBJID = GTDR.RULEDEF2FINRUL ");
                                sbOrder.append(
                                        " AND GTFD.FLTDOWN2RULE_DEFN = GTDR.OBJID AND GTFT.OBJID = GTFD.fltdown2fault AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID = GTFT.OBJID AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT2VEHICLE =");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".FAULT_OBJID BETWEEN ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append("?");
                                sbOrder.append(" AND ");
                                sbOrder.append(strViewName);
                                sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                            }
                        } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID BETWEEN");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(" MTM.CBR_USED = 'Y' AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");
                        } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            sbOrder.append(" SELECT COUNT(1) COUNT_OCCUR_DATE FROM ");
                            sbOrder.append(strViewName);
                            sbOrder.append(" ,GETS_TOOL_CASE_MTM_FAULT MTM WHERE ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID BETWEEN ");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append("?");
                            sbOrder.append(" AND FAULT_CLASSIFICATION='Critical Fault' AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2VEHICLE =");
                            sbOrder.append("?");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT2RULE_DEFN IS NULL AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".FAULT_OBJID = MTM.MTM2FAULT(+) AND (MTM.MTM2CASE = ");
                            sbOrder.append("?");
                            sbOrder.append(" OR MTM.MTM2CASE IS NULL) ");
                            sbOrder.append(" AND ");
                            sbOrder.append(strViewName);
                            sbOrder.append(".OCCUR_DATE < TO_DATE('1/1/2000','DD/MM/YYYY') ");

                        }
                    }
                    if (STR_HC.equalsIgnoreCase(strHC)) {

                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'HC') ");
                    } else if (STR_EGU.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'EGU') ");
                    } else if (STR_HC.equalsIgnoreCase(strHC) && STR_EGU.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'HC' OR ");
                        sbOrder.append(strViewName);
                        sbOrder.append(".RECORD_TYPE = 'EGU')");
                    }

                    pstmtOrder = session.createSQLQuery(sbOrder.toString());// con.prepareStatement(sbOrder.toString());

                    if (STR_DAYS.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                        if (STR_Y.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strNoOfDays);
                            pstmtOrder.setParameter(1, intVehicleID);
                        } else if (STR_CASE_CREATION.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strCreationTime);
                            pstmtOrder.setString(1, strNoOfDays);
                            pstmtOrder.setString(2, strCreationTime);
                            pstmtOrder.setParameter(3, intVehicleID);
                        } else if (STR_CASE_CREATION_TO_DATE.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strCreationTime);
                            pstmtOrder.setParameter(1, intVehicleID);
                        } else if (STR_CASE_APPENDED.equalsIgnoreCase(strCaseFrom)) {
                            pstmtOrder.setString(0, strNoOfDays);
                            pstmtOrder.setParameter(1, intVehicleID);
                            if (null != strMaxUpdatedDate) {
                                pstmtOrder.setString(2, strMaxUpdatedDate);
                                pstmtOrder.setParameter(3, intCaseID);
                            } else {
                                pstmtOrder.setParameter(2, intCaseID);
                            }
                        }
                    } else {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            if (STR_YES.equalsIgnoreCase(strFaultClassified)) {
                                pstmtOrder.setString(0, strMinObjid);
                                pstmtOrder.setString(1, strMaxObjid);
                                pstmtOrder.setParameter(2, intVehicleID);
                                pstmtOrder.setParameter(3, intCaseID);
                            } else {
                                pstmtOrder.setString(0, strCaseID);
                                pstmtOrder.setParameter(1, intVehicleID);
                                pstmtOrder.setString(2, strMinObjid);
                                pstmtOrder.setString(3, strMaxObjid);
                            }
                        } else if (STR_CBR.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            pstmtOrder.setString(0, strMinObjid);
                            pstmtOrder.setString(1, strMaxObjid);
                            pstmtOrder.setParameter(2, intVehicleID);
                            pstmtOrder.setParameter(3, intCaseID);
                        } else if (STR_CRITICAL_FAULT.equalsIgnoreCase(strIsJdpadCbrCrit)) {
                            pstmtOrder.setString(0, strMinObjid);
                            pstmtOrder.setString(1, strMaxObjid);
                            pstmtOrder.setParameter(2, intVehicleID);
                            pstmtOrder.setParameter(3, intCaseID);
                        }
                    }

                    pstmtOrder.setFetchSize(10);
                    rsOrder = pstmtOrder.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(rsOrder)) {
                        int index = 0;
                        if (index < rsOrder.size()) {
                            Object data[];
                            if (rsOrder.get(index) instanceof BigDecimal) {
                                data = new Object[1];
                                data[0] = rsOrder.get(index);
                            } else {
                                data = (Object[]) rsOrder.get(index);
                            }
                            strCountOccurDate = RMDCommonUtility.convertObjectToString(data[0]);
                        }
                    }
                } else {
                    strCountOccurDate = STR_0;
                }
            }

            ArrayList arlSelectDetails = new ArrayList();
            int intHeaders = 0;

            GetMobileToolDsParminfoServiceVO objFaultHeaderDetailVO = null;
            intHeaders = arlHeaderDetails.size();
            for (int j = 0; j < intHeaders; j++) {
                objFaultHeaderDetailVO = (GetMobileToolDsParminfoServiceVO) arlHeaderDetails.get(j);
                strColumnName = objFaultHeaderDetailVO.getColumnName();
                strUpperBound = objFaultHeaderDetailVO.getUpperBound().toString();
                if (strUpperBound.equalsIgnoreCase("0.0")) {
                    strUpperBound = null;
                }
                strLowerBound = objFaultHeaderDetailVO.getLowerBound().toString();
                if (strLowerBound.equalsIgnoreCase("0.0")) {
                    strLowerBound = null;
                }
                strDataToolTipFlag = objFaultHeaderDetailVO.getDataTooltipFlag();
                strFormatData = objFaultHeaderDetailVO.getFormat();

                if (null != strFormatData && !"".equalsIgnoreCase(strFormatData)) {
                    strFormattedColumn = strFormatData.substring(0, strFormatData.indexOf("$")) + strViewName + "."
                            + strColumnName
                            + strFormatData.substring(strFormatData.indexOf("$") + 1, strFormatData.length());
                }

                if (STR_N.equalsIgnoreCase(strAnomInd)) {
                    if (null != strUpperBound && null != strLowerBound) {
                        if (STR_Y.equalsIgnoreCase(strDataToolTipFlag)) {

                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery
                                    .append("||'^'||(SELECT    RESIDUAL_CRITICALITY_FLAG||'^'|| (EXPECTED_VALUE + (");
                            sbSelectQuery.append(" HM.LOWER_BOUND))||'^'||(EXPECTED_VALUE + ");
                            sbSelectQuery.append(" HM.UPPER_BOUND");
                            sbSelectQuery.append(
                                    ") FROM GETS_TOOLS.GETS_TOOL_ANOM_OUT ANOM,GETS_TOOLS.GETS_TOOL_ANOM_ADMIN ADM,GETS_TOOLS.GETS_TOOL_HEATMAP_PARAM HM  WHERE ANOM_OUT2FAULT = ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(
                                    ".FAULT_OBJID AND ANOM.ANOM_OUT2ANOM_ADMIN = ADM.OBJID AND ADM.DISPLAY_PARM_NAME = '");
                            sbSelectQuery.append(strDisplayName);
                            sbSelectQuery.append("' AND ANOM_OUT2VEHICLE = ");
                            sbSelectQuery.append(intVehicleID);
                            sbSelectQuery.append(" AND HM.HEATMAP2ANOMADMIN = ADM.OBJID ");
                            sbSelectQuery.append(")),null) ");
                            sbSelectQuery.append(strDisplayName);
                            sbSelectQuery.append(" , ");

                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery.append("||'^'||(case when ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" < ");
                            sbSelectQuery.append(strLowerBound);
                            sbSelectQuery.append(" THEN 'L' WHEN ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" > ");
                            sbSelectQuery.append(strUpperBound);
                            sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL) ");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" , ");

                        }
                    } else {
                        sbSelectQuery.append("NVL2(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");
                        sbSelectQuery.append(strFormattedColumn);
                        sbSelectQuery.append(",NULL)  ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(" , ");

                    }
                } else // non anom columns
                {

                    if (STR_OCCUR_TIME.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_TIME.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {

                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(", 2),  'FM999999999999.00' ) ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");

                    } else if (STR_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                            || STR_EDP_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(", 1),  'FM99999990.0' ) ");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",");

                    }

                    else if (STR_FAULT_CFG.equalsIgnoreCase(strColumnName)
                            && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append("gets_sd_fltlog_samp_rt_pkg.get_sample_rt_fltlog_fn(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(") sample_rate,");

                    } else if (STR_A_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_B_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_L_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_M_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_O_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_S_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_V_STATUS.equalsIgnoreCase(strColumnName)
                            || STR_W_STATUS.equalsIgnoreCase(strColumnName)) {
                        String strColumn = strColumnName.substring(0, 1);
                        strColumn = strColumn.toUpperCase();
                        sbSelectQuery.append("DECODE (");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        sbSelectQuery.append(",1, '");
                        sbSelectQuery.append(strColumn);
                        sbSelectQuery.append("' , ' '),");

                    }

                    else if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_DATE.equalsIgnoreCase(strColumnName)
                            || STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)
                            || STR_CMU_TIME.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" TO_CHAR(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(strColumnName);
                        // if (STR_AC.equalsIgnoreCase(strCtrlCfg)
                        // || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                        // sbSelectQuery.append(",'MM/DD/YYYY HH24:MI')");
                        // } else {
                        sbSelectQuery.append(",'MM/DD/YYYY HH24:MI:SS')");
                        // }
                        if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ocur_date");
                        } else if (STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ofboard_load_date");
                        } else {
                            sbSelectQuery.append(strColumnName);
                        }
                        sbSelectQuery.append(",");

                    } else if (STR_TOOL_RUN_IND.equalsIgnoreCase(strColumnName)) {
                        if (STR_JDPAD.equalsIgnoreCase(strIsJdpadCbrCrit)
                                && !STR_YES.equalsIgnoreCase(strFaultClassified)) {
                            sbSelectQuery.append(" '*' toolrunind, ");

                        } else {
                            sbSelectQuery.append("	DECODE((SELECT NVL2 ( ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT2RULE_DEFN, ");
                            sbSelectQuery.append(" 'F', NULL ) FROM GETS_TOOL_CASE_MTM_FAULT MTM  ");
                            sbSelectQuery.append(" WHERE ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT_OBJID =  MTM.MTM2FAULT ");
                            sbSelectQuery.append(" AND ");
                            sbSelectQuery.append(" MTM.MTM2CASE = ");
                            sbSelectQuery.append(intCaseID);
                            sbSelectQuery.append(" ), ");
                            sbSelectQuery.append(" 'F','F', ");
                            sbSelectQuery.append(" (DECODE (( SELECT '*' ");
                            sbSelectQuery.append(" FROM GETS_TOOL_CASE_MTM_FAULT MTM ");
                            sbSelectQuery.append(" WHERE ");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".FAULT_OBJID =  MTM.MTM2FAULT ");
                            sbSelectQuery.append(" AND ");
                            sbSelectQuery.append(" MTM.MTM2CASE = ");
                            sbSelectQuery.append(intCaseID);
                            sbSelectQuery.append(" ),'*','*',NULL)) ");
                            sbSelectQuery.append(" ) TOOLRUNIND, ");

                        }
                    } else if (STR_EX_SNAPSHOT_FLG.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" DECODE(");
                        sbSelectQuery.append(strViewName);
                        sbSelectQuery.append(".EX_SNAPSHOT_FLG,1,'*'), ");

                    } else {
                        if (null != strUpperBound && null != strLowerBound) {
                            if (STR_Y.equalsIgnoreCase(strDataToolTipFlag)) {
                                // need to add the tooltip query
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(case when ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" then 'L' when ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL)  ");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" , ");

                            } else {
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(CASE WHEN ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" THEN 'L' WHEN ");
                                sbSelectQuery.append(strViewName);
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" THEN 'H' ELSE 'N' END)),NULL)  ");
                                sbSelectQuery.append(strColumnName);
                                sbSelectQuery.append(" , ");

                            }
                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(strViewName);
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(", ");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery.append(",null)  ");
                            sbSelectQuery.append(strColumnName);
                            sbSelectQuery.append(" , ");

                        }
                    }

                } // end non anom
            } // end of for loop of headers

            // end of group loop
            sbBuildQuery.append(" Select distinct ");
            sbBuildQuery.append(sbSelectQuery);
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".proximity_desc, ");

            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".OCCUR_DATE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".OFFBOARD_LOAD_DATE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".RECORD_TYPE, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_STRATEGY_OBJID, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_ORIGIN, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".FAULT_INDEX FLTINDEX, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".SAMPLE_NO V_SAMPLE_NO FROM  ");

            sbBuildQuery.append(strViewName);
            sbBuildQuery.append("  ");

        } catch (Exception e) {
            FaultLogHelper.error("Error in building Select Clause : " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);
            } catch (Exception e) {
                FaultLogHelper.error("Error in Closing Connection for Order : " + e);
                throw e;
            }

        }
        FaultLogHelper.debug("End selectClause :" + System.currentTimeMillis());
        return sbBuildQuery;
    }
}
