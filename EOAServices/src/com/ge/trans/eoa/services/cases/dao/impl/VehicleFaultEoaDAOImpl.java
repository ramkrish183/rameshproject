/**
 * =================================================================
 * Classification: GE Confidential
 * File : RolesDAOIntf.java
 * Description : DAO Impl for Vehicle Faults
 *
 * Package : com.ge.trans.rmd.services.cases.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.cases.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.eoa.services.cases.dao.intf.VehicleFaultEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultDetailsTO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderGroupVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultLogTO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultTO;
import com.ge.trans.eoa.services.cases.service.valueobjects.VehicleFaultEoaServiceVO;
import com.ge.trans.eoa.services.common.constants.FaultLogConstants;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDetailsServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultLogServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Impl for Vehicle Faults
 * @History :
 ******************************************************************************/
public class VehicleFaultEoaDAOImpl extends AbsFaultLogDAO implements VehicleFaultEoaDAOIntf, FaultLogConstants {

    private static final long serialVersionUID = -2116196822240466537L;
    Codec ORACLE_CODEC = new OracleCodec();

    private static final RMDLogger LOG = RMDLogger.getLogger(VehicleFaultEoaDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.VehicleFaultDAOIntf
     * #getFaultDetails (com.ge.trans.rmd.services.cases.service.valueobjects.
     * VehicleFaultServiceVO )
     */
    @Override
    public FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception {
        FaultServiceVO objFaultServiceVO = new FaultServiceVO();
        Session session = null;

        // this gets all the data
        FaultLogHelper.log("Start getFaultData :" + System.currentTimeMillis());
        String strDataQuery = null;
        Query faultQuery = null;
        FaultDetailsTO objFaultDetailsTO = new FaultDetailsTO();
        FaultLogTO objFaultLogTO = new FaultLogTO();
        FaultTO objFaultTO = new FaultTO();
        ArrayList arlDataDetails = new ArrayList();
        ArrayList arlData = new ArrayList();
        String strProximityDesc = null;
        String strNotch = null;
        ArrayList arlFaultDetail = new ArrayList();
        StringBuilder sbDataQuery = new StringBuilder();
        String strRecordType = null;
        String strFaultStrategyID = null;
        String strFaultOrigin = null;
        String strSampleNo = null;
        String strFaultObjid = null;
        int intFaultCnt = 1;
        HashMap hmSample = new HashMap();
        FaultHeaderGroupVO objFaultHeader = null;
        String strData = null;
        Integer FltCnt = null;
        String strIsJdpadCbrCrit = null;
        String strCaseFrom = null;
        /* For Pagination */
        String strTotalRecordCnt = null;

        StringBuilder sbViewQuery = new StringBuilder();
        String strViewName = null;
        Integer intVehicleID = 0;
        Query viewQuery = null;

        try {
            session = getHibernateSession();

            String strCustomer = objFaultRequestVO.getCustomer();
            String strHeaderNo = objFaultRequestVO.getVehicleHeader();
            String strSerialNo = objFaultRequestVO.getSerialNo();

            sbViewQuery.append(" SELECT GRCC.FAULT_LOG_VIEW_NAME, GRCRRV.VEHICLE_OBJID ");
            sbViewQuery.append(" FROM GETS_RMD_CTL_CFG GRCC, ");
            sbViewQuery.append(" GETS_RMD_VEHICLE GRV, ");
            sbViewQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV ");
            sbViewQuery.append(" WHERE GRCC.OBJID = GRV.VEHICLE2CTL_CFG ");
            sbViewQuery.append(" AND GRV.OBJID = GRCRRV.VEHICLE_OBJID ");
            sbViewQuery.append(" AND GRCRRV.ORG_ID =:customer");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_HDR =:vehicleHeader");
            sbViewQuery.append(" AND GRCRRV.VEHICLE_NO =:roadNumber");

            viewQuery = session.createSQLQuery(sbViewQuery.toString());
            viewQuery.setString("customer", ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strCustomer));
            viewQuery.setString("vehicleHeader", ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strHeaderNo));
            viewQuery.setString("roadNumber", ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strSerialNo));

            List result = viewQuery.list();

            if (result != null && !result.isEmpty()) {
                Object data[] = (Object[]) result.get(0);
                strViewName = RMDCommonUtility.convertObjectToString(data[0]);
                intVehicleID = RMDCommonUtility.convertObjectToInt(data[1]);
            }

            sbDataQuery = getFaultQuery(objFaultRequestVO, arlHeaderDetails, strViewName, intVehicleID,
                    strControllerCfg);

            faultQuery = session.createSQLQuery(sbDataQuery.toString());

            String strRadioSelected = objFaultRequestVO.getRadioSelected();

            String strNoOfDays = objFaultRequestVO.getNoOfDays();

            String strStartDate = objFaultRequestVO.getVvfStartDate();

            String strEndDate = objFaultRequestVO.getVvfEndDate();

            faultQuery.setInteger(0, intVehicleID);

            if (STR_NO_OF_DAYS.equalsIgnoreCase(strRadioSelected)) {
                faultQuery.setString(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strNoOfDays));
            } else if (STR_DAYS_BETWEEN.equalsIgnoreCase(strRadioSelected)) {
                faultQuery.setString(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strStartDate));
                faultQuery.setString(2, ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strEndDate));
            }

            int headerSize = arlHeaderDetails.size();
            int revisedHeaderSize = headerSize;
            ;

            if (headerSize >= 10) {
                revisedHeaderSize = revisedHeaderSize = 10;
            }

            List faultList = faultQuery.list();

            // ResultSetMetaData rsmd = rs.getMetaData();
            // intNoOfCols = rsmd.getColumnCount();

            if (RMDCommonUtility.isCollectionNotEmpty(faultList) && !faultList.isEmpty()) {

                ArrayList arlFaultLog = new ArrayList();
                FaultLogServiceVO objFaultLogServiceVO = new FaultLogServiceVO();
                arlDataDetails = new ArrayList();
                FaultDetailsServiceVO objFaultDetailsServiceVO = null;
                DateFormat zoneFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                for (int i = 0; i < faultList.size(); i++) {

                    Object[] objMap = (Object[]) faultList.get(i);
                    objFaultLogServiceVO = new FaultLogServiceVO();
                    arlDataDetails = new ArrayList();
                    strNotch = null;

                    objFaultLogTO = new FaultLogTO();

                    int headerCounter = 0;

                    for (int j = 0; j < headerSize; j++) {
                        String strTmpDataHolder = null;
                        if (null != objMap[j]) {
                            if (objMap[j] instanceof java.util.Date) {
                                strTmpDataHolder = zoneFormater.format(objMap[j]);
                            } else if (objMap[j] instanceof java.math.BigDecimal) {
                                strTmpDataHolder = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[j]);
                            } else {
                                strTmpDataHolder = RMDCommonUtility.convertObjectToString(objMap[j]);
                            }
                        } else {
                            strTmpDataHolder = RMDCommonConstants.EMPTY_STRING;
                        }

                        objFaultDetailsServiceVO = new FaultDetailsServiceVO();
                        objFaultDetailsServiceVO.setStrData(strTmpDataHolder);
                        arlDataDetails.add(objFaultDetailsServiceVO);
                    }

                    /*
                     * //strProximityDesc = rs.getString(STR_PROXIMITY_DESC);
                     * strProximityDesc = objMap[headerSize - 10] != null ?
                     * (String)objMap[headerSize - 10] : null;
                     * objFaultDetailsServiceVO.setStrProximityDesc(
                     * strProximityDesc); //strNotch =
                     * rs.getString(STR_NOTCH_FLAG) strNotch =
                     * RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[
                     * headerSize - 9]);
                     * objFaultDetailsServiceVO.setStrNotch(strNotch);
                     * //strFaultObjid = rs.getString(STR_FAULT_OBJID);
                     * strFaultObjid =
                     * RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[
                     * headerSize - 8]); //TODO Future: Set in appropriate
                     * object //objFaultLogTO.setFaultObjid(strFaultObjid);
                     * //strRecordType = rs.getString(STR_RECORD_TYPE);
                     * strRecordType = objMap[headerSize - 3] != null ?
                     * (String)objMap[headerSize - 5] : null; //TODO Future: Set
                     * in appropriate object
                     * //objFaultLogTO.setRecordType(strRecordType);
                     * //strFaultStrategyID =
                     * rs.getString(STR_FAULT_STRATEGY_OBJID);
                     * strFaultStrategyID =
                     * RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[
                     * headerSize - 4]);
                     * //objFaultLogTO.setFaultStrategyID(strFaultStrategyID);
                     * //strFaultOrigin = rs.getString(STR_FAULT_ORIGIN);
                     * strRecordType = objMap[headerSize - 3] != null ?
                     * (String)objMap[headerSize - 3] : null; //TODO Future: Set
                     * in appropriate object
                     * //objFaultLogTO.setFaultOrigin(strFaultOrigin);
                     * //strSampleNo = rs.getString(STR_V_SAMPLE_NO);
                     * strSampleNo =
                     * RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap[
                     * headerSize - 1]);
                     */

                    // TODO check if required
                    /*
                     * strNotch = rs.getString(STR_NOTCH_FLAG);
                     * objFaultLogTO.setNotch(strNotch); strRecordType =
                     * rs.getString(STR_RECORD_TYPE);
                     * objFaultLogTO.setRecordType(strRecordType);
                     * strFaultStrategyID =
                     * rs.getString(STR_FAULT_STRATEGY_OBJID);
                     * objFaultLogTO.setFaultStrategyID(strFaultStrategyID);
                     * strFaultOrigin = rs.getString(STR_FAULT_ORIGIN);
                     * objFaultLogTO.setFaultOrigin(strFaultOrigin); strSampleNo
                     * = rs.getString(STR_V_SAMPLE_NO); strProximityDesc =
                     * rs.getString(STR_PROXIMITY_DESC); strFaultObjid =
                     * rs.getString(STR_FAULT_OBJID);
                     * objFaultLogTO.setFaultObjid(strFaultObjid); //For
                     * pagination, this will get the total count of records
                     * available String strViewAll =
                     * objFaultRequestVO.getViewAll(); if(null == strViewAll ||
                     * !STR_Y.equalsIgnoreCase(strViewAll)) { strTotalRecordCnt
                     * = rs.getString(STR_COUNT); }
                     * if(!hmSample.containsKey(strFaultObjid)) { intFaultCnt =
                     * 1; FltCnt = new Integer(intFaultCnt);
                     * hmSample.put(strFaultObjid,FltCnt); } else {
                     * intFaultCnt++; FltCnt = new Integer(intFaultCnt);
                     * hmSample.put(strFaultObjid,FltCnt); }
                     * objFaultLogTO.setNonDisplayableCols(STR_NOTCH_FLAG,
                     * strNotch);
                     * objFaultLogTO.setNonDisplayableCols(STR_RECORD_TYPE,
                     * strRecordType); objFaultLogTO.setNonDisplayableCols(
                     * STR_FAULT_STRATEGY_OBJID,strFaultStrategyID);
                     * objFaultLogTO.setNonDisplayableCols(STR_FAULT_ORIGIN,
                     * strFaultOrigin);
                     * objFaultLogTO.setNonDisplayableCols(STR_V_SAMPLE_NO,
                     * strSampleNo);
                     * objFaultLogTO.setNonDisplayableCols(STR_V_PROXIMITY_DESC,
                     * strProximityDesc); if(null == strViewAll ||
                     * !STR_Y.equalsIgnoreCase(strViewAll)) {
                     * objFaultLogTO.setNonDisplayableCols(STR_TOTAL_RECORD_CNT,
                     * strTotalRecordCnt); }
                     * objFaultLogTO.setFaultData(arlDataDetails);
                     * arlData.add(objFaultLogTO);
                     */

                    objFaultLogServiceVO.setArlFaultDataDetails(arlDataDetails);
                    objFaultLogServiceVO.setStrNotch(strNotch);
                    arlFaultLog.add(objFaultLogServiceVO);

                }

                objFaultTO.setSampleDetails(hmSample);
                objFaultTO.setFaultData(arlData);

                // Setting Data to VO
                objFaultServiceVO.setArlFaultData(arlFaultLog);

            } else {
                objFaultServiceVO.setArlFaultData((ArrayList) faultList);
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("RMDDAOConnectionException occured in VehicleFaultDAOImpl FaultDataDetailsServiceVO()", ex);
        } catch (Exception ex) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl FaultDataDetailsServiceVO()", ex);
        } finally {
            releaseSession(session);
        }
        return objFaultServiceVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#getTotalCount(com
     * .ge.trans.rmd.services.cases.service.valueobjects.VehicleFaultServiceVO)
     */
    @Override
    public void getTotalCount(VehicleFaultEoaServiceVO objVehicleFaultServiceVO) {
        try {
            // TODO Add Method logic in the future as per requirement
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_TOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objVehicleFaultServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception ex) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getTotalCount()", ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_TOTAL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objVehicleFaultServiceVO.getStrLanguage()),
                    ex, RMDCommonConstants.MAJOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#getFaultCode(java
     * .lang.String)
     */
    @Override
    public List getFaultCode(String strFaultCode, String strLanguage) throws RMDDAOException {
        List faultCodeSet = null;
        Session session = null;
        try {
            session = getHibernateSession();
            // TODO Add Method logic in the future as per requirement

        } catch (RMDDAOConnectionException ex) {
            LOG.error(ex.getMessage());
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FAULTCODE);
        } finally {
            releaseSession(session);
        }
        return faultCodeSet;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.CaseDAOIntf#getArlHeaders()
     */
    public ArrayList getArlHeaders(String strLanguage) {
        Session hibernateSession = getHibernateSession();
        ArrayList arlHeader = new ArrayList();
        ArrayList arlHeaderList = new ArrayList();
        String strHeaderWidth = RMDCommonConstants.EMPTY_STRING;
        String strWidth = RMDCommonConstants.EMPTY_STRING;
        try {
            // TODO Add Method logic in the future as per requirement
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getArlHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return arlHeaderList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.dao.intf.VehicleFaultDAOIntf#
     * getHeaderGroup (java.lang.String)
     */
    @Override
    public List getHeaderGroup(String strLanguage) {
        Session hibernateSession = getHibernateSession();
        List arlHeaderGroup = new ArrayList();
        try {
            // TODO Add Method logic in the future as per requirement
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getArlGrpHeaders()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FAULT_GRP_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return arlHeaderGroup;
    }

    // Added method to get customer level header details for DD VVF view
    /**
     * @param strCustomer
     * @param strModelId
     * @return
     */
    @Override
    public ArrayList<String> getCustLevelDetails(String strCustomer, String strModelId) {
        Session session = null;
        String strDataQuery = null;
        Query query = null;
        ArrayList<String> arlParamInfoObjidList = null;
        strDataQuery = "select extapp2ce_parm_info from gets_tools.gets_tool_ce_parm_info_extapp app";
        strDataQuery += ", gets_tool_ce_parm_info_new new,table_bus_org bus ";
        strDataQuery += " where extapp2bus_org = bus.objid and bus.s_org_id =:customerId ";
        strDataQuery += " and new.ce_param2model =:modelId and app.extapp2ce_parm_info = new.objid";
        try {
            session = getHibernateSession();
            query = session.createSQLQuery(strDataQuery.toString());
            query.setString("customerId", strCustomer);
            query.setString("modelId", strModelId);
            List arlParamInfoList = query.list();
            arlParamInfoObjidList = new ArrayList<String>();

            if (arlParamInfoList != null && !arlParamInfoList.isEmpty()) {
                for (Object objid : arlParamInfoList) {
                    arlParamInfoObjidList.add(RMDCommonUtility.cnvrtBigDecimalObjectToString(objid));
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getCustLevelDetails()", ex);

        } catch (Exception e) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getCustLevelDetails()", e);
        } finally {
            releaseSession(session);
        }
        return arlParamInfoObjidList;
    }

    @Override
    public ArrayList<String> getCustLevelDPEABDetails(String strCustomer, String strModelId) {
        Session session = null;
        String strDataQuery = null;
        Query query = null;
        ArrayList<String> arlParamInfoObjidList = null;
        strDataQuery = "select extapp2ce_parm_info from gets_tools.gets_tool_ce_parm_info_extapp app";
        strDataQuery += ", gets_tools.gets_tool_ce_parm_dpeab dpeab,table_bus_org bus ";
        strDataQuery += " where extapp2bus_org = bus.objid and bus.s_org_id =:customerId ";
        strDataQuery += " and dpeab.ce_param2model =:modelId and app.extapp2ce_parm_info = dpeab.objid";
        try {
            session = getHibernateSession();
            query = session.createSQLQuery(strDataQuery.toString());
            query.setString("customerId", strCustomer);
            query.setString("modelId", strModelId);
            List arlParamInfoList = query.list();
            arlParamInfoObjidList = new ArrayList<String>();

            if (arlParamInfoList != null && !arlParamInfoList.isEmpty()) {
                for (Object objid : arlParamInfoList) {
                    arlParamInfoObjidList.add(RMDCommonUtility.cnvrtBigDecimalObjectToString(objid));
                }
            }

        } catch (RMDDAOConnectionException ex) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getCustLevelDPEABDetails()", ex);

        } catch (Exception e) {
            LOG.error("Unexpected Error occured in VehicleFaultDAOImpl getCustLevelDPEABDetails()", e);
        } finally {
            releaseSession(session);
        }
        return arlParamInfoObjidList;
    }

    /**
     * @param objFaultRequestVO
     * @param arlHeaderDetails
     * @param strViewName
     * @param intVehicleID
     * @param strControllerCfg
     * @return
     * @throws Exception
     */
    public StringBuilder getFaultQuery(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails, String strViewName,
            Integer intVehicleID, String strControllerCfg) throws Exception {

        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbWhereQuery = new StringBuilder();
        StringBuilder sbOrderQuery = new StringBuilder();
        StringBuilder sbDataQuery = new StringBuilder();

        FaultLogHelper.log("Start getFaultQuery :" + System.currentTimeMillis());
        try {

            // ......
            // TODO check and change...for now hardcoding strCountOccurDate to
            // null
            String strCountOccurDate = null;

            sbSelectQuery = selectClause(objFaultRequestVO, arlHeaderDetails, strViewName, strControllerCfg,
                    intVehicleID);
            sbWhereQuery = whereClause(strViewName, objFaultRequestVO);
            sbOrderQuery = orderClause(strViewName, objFaultRequestVO, strControllerCfg, strCountOccurDate);
            sbDataQuery.append(sbSelectQuery);
            sbDataQuery.append(sbWhereQuery);
            sbDataQuery.append(sbOrderQuery);

            String strViewAll = objFaultRequestVO.getViewAll();

            if (null == strViewAll || !STR_Y.equalsIgnoreCase(strViewAll)) {
                sbDataQuery = getPagination(objFaultRequestVO, sbDataQuery, sbOrderQuery, strControllerCfg,
                        strCountOccurDate);
            }
            FaultLogHelper.log("VVF Data Query : " + sbDataQuery);
        } catch (Exception e) {
            FaultLogHelper.log("Error in building FaultData Query : " + e);
            throw e;
        }
        FaultLogHelper.log("End getFaultQuery :" + System.currentTimeMillis());
        return sbDataQuery;
    }

    /**
     * @param objFaultRequestVO
     * @param arlHeaderDetails
     * @param strViewName
     * @param strControllerCfg
     * @param intVehicleID
     * @return
     * @throws Exception
     */
    public StringBuilder selectClause(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails, String strViewName,
            String strControllerCfg, Integer intVehicleID) throws Exception {
        FaultLogHelper.log("Start selectClause :" + System.currentTimeMillis());

        Session session = null;
        List rsOrder = null;
        Query queryOrder = null;
        String strColumnName = null;
        String strAnomInd = null;
        String strDisplayName = null;

        StringBuilder sbSelectQuery = new StringBuilder();
        StringBuilder sbBuildQuery = new StringBuilder();
        String strUpperBound = null;
        String strLowerBound = null;
        String strDataToolTipFlag = null;
        String strFormatData = null;
        String strFormattedColumn = null;

        StringBuilder sbOrder = new StringBuilder();
        String strNoOfDays = null;
        String strHC = null;
        String strEGU = null;
        String strStartDate = null;
        String strEndDate = null;
        String strRadioSelected = null;

        String strCtrlCfg = null;
        String strCountOccurDate = null;

        try {
            session = getHibernateSession();

            strCtrlCfg = strControllerCfg;

            strNoOfDays = objFaultRequestVO.getNoOfDays();
            strHC = objFaultRequestVO.getHC();
            strEGU = objFaultRequestVO.getEGU();
            strStartDate = objFaultRequestVO.getVvfStartDate();
            strEndDate = objFaultRequestVO.getVvfEndDate();
            strRadioSelected = objFaultRequestVO.getRadioSelected();

            // FaultLogHelper.log("intVehicleID :"+intVehicleID);

            String strSortOrder = objFaultRequestVO.getSortOrder();

            // FaultLogHelper.log("strSortOrder :"+strSortOrder);

            if (STR_AC6000.equalsIgnoreCase(strControllerCfg)) {
                if ("OFFBOARD_LOAD_DATE".equalsIgnoreCase(strSortOrder)) {
                    sbOrder.append(" select COUNT(1) count_occur_date from ");
                    sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                    sbOrder.append(" where ");
                    if (STR_NO_OF_DAYS.equalsIgnoreCase(strRadioSelected)) {
                        sbOrder.append(" ABS(SYSDATE+6/24 -");
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".");
                        sbOrder.append("offboard_load_date) <= ");
                        sbOrder.append("?");
                    } else if (STR_DAYS_BETWEEN.equalsIgnoreCase(strRadioSelected)) {
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".");
                        sbOrder.append("offboard_load_date between ");
                        sbOrder.append(" to_date(  ");
                        sbOrder.append("?");
                        sbOrder.append(",'MM/DD/YYYY') ");
                        sbOrder.append(" AND ");
                        sbOrder.append("to_date( ");
                        sbOrder.append("?");
                        sbOrder.append(",'MM/DD/YYYY')");

                    }
                    sbOrder.append(" AND ");
                    sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                    sbOrder.append(".");
                    sbOrder.append("fault2vehicle = ");
                    sbOrder.append("?");
                    sbOrder.append(" AND ");
                    sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                    sbOrder.append(".occur_date < to_date('1/1/1999','DD/MM/YYYY') ");

                    if (STR_ON.equalsIgnoreCase(strHC)) {

                        sbOrder.append(" AND (");
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".record_type = 'HC') ");
                    } else if (STR_ON.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".record_type = 'EGU') ");
                    } else if (STR_HC.equalsIgnoreCase(strHC) && STR_EGU.equalsIgnoreCase(strEGU)) {
                        sbOrder.append(" AND (");
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".record_type = 'HC' or ");
                        sbOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbOrder.append(".record_type = 'EGU')");
                    }
                    queryOrder = session.createSQLQuery(sbOrder.toString());
                    if (STR_NO_OF_DAYS.equalsIgnoreCase(strRadioSelected)) {
                        queryOrder.setString(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strNoOfDays));
                        queryOrder.setInteger(1, intVehicleID);
                    } else if (STR_DAYS_BETWEEN.equalsIgnoreCase(strRadioSelected)) {
                        queryOrder.setString(0, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strStartDate));
                        queryOrder.setString(1, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strEndDate));
                        queryOrder.setInteger(2, intVehicleID);
                    }

                    rsOrder = queryOrder.list();

                    if (rsOrder != null && !rsOrder.isEmpty()) {
                        strCountOccurDate = (String) rsOrder.get(0);
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
                strUpperBound = RMDCommonUtility.cnvrtDoubleObjectToString(objFaultHeaderDetailVO.getUpperBound());
                strLowerBound = RMDCommonUtility.cnvrtDoubleObjectToString(objFaultHeaderDetailVO.getLowerBound());

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
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strFormattedColumn));
                            sbSelectQuery
                                    .append("||'^'||(SELECT    residual_criticality_flag||'^'|| (expected_value + (");
                            sbSelectQuery.append(" hm.LOWER_BOUND))||'^'||(expected_value + ");
                            sbSelectQuery.append(" hm.UPPER_BOUND");
                            sbSelectQuery.append(
                                    ") FROM gets_tools.gets_tool_anom_out anom,gets_tools.gets_tool_anom_admin adm,gets_tools.gets_tool_heatmap_param hm WHERE anom_out2fault = ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(
                                    ".fault_objid and anom.anom_out2anom_admin = adm.objid and adm.display_parm_name = '");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strDisplayName));
                            sbSelectQuery.append("' and anom_out2vehicle = ");
                            sbSelectQuery.append(intVehicleID);
                            sbSelectQuery.append(" and hm.HEATMAP2ANOMADMIN = adm.objid ");
                            sbSelectQuery.append(")),null) ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strDisplayName));
                            sbSelectQuery.append(" , ");
                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(",(");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strFormattedColumn));
                            sbSelectQuery.append("||'^'||(case when ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(" < ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strLowerBound));
                            sbSelectQuery.append(" then 'L' when ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(" > ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strUpperBound));
                            sbSelectQuery.append(" then 'H' else 'N' end)),null) ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(" , ");
                        }
                    } else {
                        sbSelectQuery.append("NVL2(");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(",");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strFormattedColumn));
                        sbSelectQuery.append(",null)  ");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(" , ");
                    }
                } else // non anom columns
                {

                    if (STR_OCCUR_TIME.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_TIME.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {

                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(", 2),  'FM999999999999.00' ) ");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(",");

                    } else if (STR_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                            || STR_EDP_LOCO_SPEED.equalsIgnoreCase(strColumnName)
                                    && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append(" TO_CHAR(TRUNC (");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(", 1),  'FM99999990.0' ) ");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(",");
                    }

                    else if (STR_FAULT_CFG.equalsIgnoreCase(strColumnName)
                            && (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg))) {
                        sbSelectQuery.append("gets_sd_fltlog_samp_rt_pkg.get_sample_rt_fltlog_fn(");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
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
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        sbSelectQuery.append(",1, '");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumn));
                        sbSelectQuery.append("' , ' '),");

                    }

                    else if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)
                            || STR_FAULT_RESET_DATE.equalsIgnoreCase(strColumnName)
                            || STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)
                            || STR_CMU_TIME.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" TO_CHAR(");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                            if (STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)
                                    || STR_CMU_TIME.equalsIgnoreCase(strColumnName)) {
                                sbSelectQuery.append(",'MM/DD/YYYY HH24:MI:SS')");
                            } else {
                                sbSelectQuery.append(",'MM/DD/YYYY HH24:MI')");
                            }
                        } else {
                            sbSelectQuery.append(",'MM/DD/YY HH24:MI:SS')");
                        }
                        if (STR_OCCUR_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ocur_date");
                        } else if (STR_OFFBOARD_LOAD_DATE.equalsIgnoreCase(strColumnName)) {
                            sbSelectQuery.append("ofboard_load_date");
                        } else {
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                        }
                        sbSelectQuery.append(",");
                    } else if (STR_TOOL_RUN_IND.equalsIgnoreCase(strColumnName)) {

                        sbSelectQuery.append(" NVL2( ");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".");
                        sbSelectQuery.append("fault2rule_defn,");
                        sbSelectQuery.append(" 'F',null ");
                        sbSelectQuery.append(" ) toolrunind, ");

                    } else if (STR_EX_SNAPSHOT_FLG.equalsIgnoreCase(strColumnName)) {
                        sbSelectQuery.append(" DECODE(");
                        sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                        sbSelectQuery.append(".ex_snapshot_flg,1,'*'), ");
                    } else {
                        if (null != strUpperBound && null != strLowerBound) {
                            if (STR_Y.equalsIgnoreCase(strDataToolTipFlag)) {
                                // need to add the tooltip query
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(case when ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" then 'L' when ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" then 'H' else 'N' end)),null)  ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" , ");
                            } else {
                                sbSelectQuery.append("NVL2(");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(",(");
                                sbSelectQuery.append(strFormattedColumn);
                                sbSelectQuery.append("||'^'||(case when ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" < ");
                                sbSelectQuery.append(strLowerBound);
                                sbSelectQuery.append(" then 'L' when ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                                sbSelectQuery.append(".");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" > ");
                                sbSelectQuery.append(strUpperBound);
                                sbSelectQuery.append(" then 'H' else 'N' end)),null)  ");
                                sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                                sbSelectQuery.append(" , ");
                            }
                        } else {
                            sbSelectQuery.append("NVL2(");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                            sbSelectQuery.append(".");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(", ");
                            sbSelectQuery.append(strFormattedColumn);
                            sbSelectQuery.append(",null)  ");
                            sbSelectQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strColumnName));
                            sbSelectQuery.append(" , ");
                        }
                    }

                } // end non anom
            } // end of for loop of headers

            // //end of group loop
            sbBuildQuery.append(" Select distinct ");
            sbBuildQuery.append(sbSelectQuery);
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".proximity_desc, ");
            sbBuildQuery.append(strViewName);
            if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                sbBuildQuery.append(".notch notch_flag, ");
            } else {
                sbBuildQuery.append(".basic_notch notch_flag, ");
            }
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".fault_objid fault_objid, ");
            if (STR_ACCCA.equalsIgnoreCase(strCtrlCfg) || STR_DCCCA.equalsIgnoreCase(strCtrlCfg)) {
                sbBuildQuery.append(strViewName);
                sbBuildQuery.append(".sdp_flag, ");
            }

            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".occur_date, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".offboard_load_date, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".record_type, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".fault_strategy_objid, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".fault_origin, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".fault_index fltindex, ");
            sbBuildQuery.append(strViewName);
            sbBuildQuery.append(".sample_no v_sample_no from  ");
            sbBuildQuery.append(strViewName);

        } catch (Exception e) {
            FaultLogHelper.log("Error in building Select Clause : " + e);
            //
            throw e;
        } finally {
            releaseSession(session);
        }
        FaultLogHelper.log("End selectClause :" + System.currentTimeMillis());
        return sbBuildQuery;
    }

    /**
     * @param strViewName
     * @param objFaultRequestVO
     * @return
     * @throws Exception
     */
    public StringBuilder whereClause(String strViewName, FaultRequestVO objFaultRequestVO) throws Exception {
        StringBuilder sbWhereQuery = new StringBuilder();
        FaultLogHelper.log("Start whereClause :" + System.currentTimeMillis());
        try {

            String strHC = objFaultRequestVO.getHC();
            String strEGU = objFaultRequestVO.getEGU();

            String strSortColumn = objFaultRequestVO.getSortOrder();

            String strRadioSelected = objFaultRequestVO.getRadioSelected();

            String strStartDate = objFaultRequestVO.getVvfStartDate();

            String strEndDate = objFaultRequestVO.getVvfEndDate();

            String strVvfRadioSelected = objFaultRequestVO.getVVfViewRadioSelected();

            /* Added by IGS on 08/26/2009 for Faultcode search.. Start */
            String strFaultCode = objFaultRequestVO.getFaultCode();
            /* Added by IGS on 08/26/2009 for Faultcode search.. End */

            sbWhereQuery.append(" WHERE ");
            sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strViewName));
            sbWhereQuery.append(".");
            sbWhereQuery.append("fault2vehicle = ");
            sbWhereQuery.append("?");
            sbWhereQuery.append(" AND ");

            if (STR_NO_OF_DAYS.equalsIgnoreCase(strRadioSelected)) {
                sbWhereQuery.append(" ABS(SYSDATE+ ");
                sbWhereQuery.append(STR_GMT);
                sbWhereQuery.append("- ");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strSortColumn));
                sbWhereQuery.append(" ) <= ");
                // sbWhereQuery.append("offboard_load_date) <= ");
                sbWhereQuery.append("?");
                sbWhereQuery.append("+");
                sbWhereQuery.append(STR_GMT);

            } else if (STR_DAYS_BETWEEN.equalsIgnoreCase(strRadioSelected)) {
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strSortColumn));
                sbWhereQuery.append(" BETWEEN ");
                // sbWhereQuery.append("offboard_load_date BETWEEN ");
                sbWhereQuery.append(" to_date( ");
                sbWhereQuery.append("?");
                sbWhereQuery.append(" ,'MM/DD/YYYY') ");
                sbWhereQuery.append(" AND ");
                sbWhereQuery.append(" to_date( ");
                sbWhereQuery.append("?");
                sbWhereQuery.append(" ,'MM/DD/YYYY') ");

            }

            String strTitle = null;
            if (STR_LOCO.equalsIgnoreCase(strVvfRadioSelected)) {
                strTitle = STR_VVF_FAULTS_FOR_LOCO;
            } else if (STR_RMD.equalsIgnoreCase(strVvfRadioSelected)) {
                strTitle = STR_VVF_FAULTS_FOR_RMD;

            }

            FaultLogHelper.log("strTitle :" + strTitle);
            String strRecortTypes = getRecordTypes(strTitle);
            if (null != strRecortTypes && !"".equalsIgnoreCase(strRecortTypes)) {
                sbWhereQuery.append(" AND ");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".record_type in ( ");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strRecortTypes));
                sbWhereQuery.append(") ");
                sbWhereQuery.append(" ");

            }

            if (STR_ON.equalsIgnoreCase(strHC) && STR_ON.equalsIgnoreCase(strEGU)) {
                sbWhereQuery.append(" AND (");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".record_type = 'HC' or ");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".record_type = 'EGU')");
            } else if (STR_ON.equalsIgnoreCase(strHC)) {

                sbWhereQuery.append(" AND (");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".record_type = 'HC') ");
            } else if (STR_ON.equalsIgnoreCase(strEGU)) {
                sbWhereQuery.append(" AND (");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".record_type = 'EGU') ");
            }

            /*
             * Added by IGS on 08/26/2009 for Faultcode search in DataScreen..
             * Start
             */
            if (null != strFaultCode && !"".equals(strFaultCode.trim())) {
                sbWhereQuery.append(" AND UPPER ( ");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strViewName));
                sbWhereQuery.append(".fault_code )  like  ");
                sbWhereQuery.append(" UPPER ('%");
                sbWhereQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strFaultCode.trim()));
                sbWhereQuery.append("%')");
            }
            /*
             * Added by IGS on 08/26/2009 for Faultcode search in DataScreen..
             * End
             */

        } catch (Exception e) {
            FaultLogHelper.log("Error in building the where clause : " + e);
            throw e;

        }
        FaultLogHelper.log("End whereClause :" + System.currentTimeMillis());
        return sbWhereQuery;
    }

    /**
     * @param strViewName
     * @param objFaultRequestVO
     * @param strCtrlCfg
     * @param strCountOccurDate
     * @return
     * @throws Exception
     */
    public StringBuilder orderClause(String strViewName, FaultRequestVO objFaultRequestVO, String strCtrlCfg,
            String strCountOccurDate) throws Exception {
        FaultLogHelper.log("Start orderClause :" + System.currentTimeMillis());

        StringBuilder sbOrderQuery = new StringBuilder();

        String strSortOrder = objFaultRequestVO.getSortOrder();

        try {

            sbOrderQuery.append(" ORDER BY ");

            if (STR_AC.equalsIgnoreCase(strCtrlCfg) || STR_DC.equalsIgnoreCase(strCtrlCfg)) {
                sbOrderQuery.append(commonOrderClause(strSortOrder));
            } else if (STR_ACCCA.equalsIgnoreCase(strCtrlCfg) || STR_DCCCA.equalsIgnoreCase(strCtrlCfg)) {
                sbOrderQuery.append(commonOrderClause(strSortOrder));
                sbOrderQuery.append(" , sdp_flag ASC ");
            } else if (STR_AC6000.equalsIgnoreCase(strCtrlCfg)) {
                if (STR_0.equalsIgnoreCase(strCountOccurDate)) {
                    // sbOrderQuery.append(" occur_date DESC, offboard_load_date
                    // DESC ");
                    sbOrderQuery.append(commonOrderClause(strSortOrder));
                } else {
                    sbOrderQuery.append(" offboard_load_date DESC ");
                }
            }

            // Added by IGS on 04/13/2010 for KTZ
            else {
                sbOrderQuery.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strSortOrder));
                sbOrderQuery.append(" DESC ");
            }

        } catch (Exception e) {

            FaultLogHelper.log("Exception in building the order clause : " + e);
            throw e;
        }
        FaultLogHelper.log("End orderClause :" + System.currentTimeMillis());
        return sbOrderQuery;
    }

    /**
     * @param strSortOrder
     * @return
     * @throws Exception
     */
    public StringBuilder commonOrderClause(String strSortOrder) throws Exception {
        StringBuilder sbCommomOrder = new StringBuilder();

        try {
            sbCommomOrder.append(" ");
            sbCommomOrder.append(ESAPI.encoder().encodeForSQL(ORACLE_CODEC, strSortOrder));
            sbCommomOrder.append(" DESC, ");
            sbCommomOrder.append(" fltindex DESC, ");
            sbCommomOrder.append(" fault_objid, ");
            sbCommomOrder.append(" v_sample_no DESC ");

        } catch (Exception e) {
            FaultLogHelper.log("Exception in building Common Order Clause : " + e);
            throw e;
        }
        return sbCommomOrder;
    }

    /**
     * @param objFaultRequestVO
     * @param sbDataQuery
     * @param sbOrderQuery
     * @param strCtrlCfg
     * @param strCountOccurDate
     * @return
     * @throws Exception
     */
    private StringBuilder getPagination(FaultRequestVO objFaultRequestVO, StringBuilder sbDataQuery,
            StringBuilder sbOrderQuery, String strCtrlCfg, String strCountOccurDate) throws Exception {
        StringBuilder sbAllData = new StringBuilder();
        try {
            String strPaginationHit = objFaultRequestVO.getPaginationHit();
            String strPaginationCounter = objFaultRequestVO.getPaginationCounter();
            int intPaginationCounter = INT_DEF_PAGINATION_COUNTER;// Default
                                                                  // value
            if (strPaginationHit == null || "".equals(strPaginationHit)) {
                strPaginationHit = STR_0;
            }
            if (strPaginationCounter != null) {
                intPaginationCounter = Integer.parseInt(strPaginationCounter);

            }
            int intPaginationHit = Integer.parseInt(strPaginationHit);
            int intStartRowNum = (intPaginationHit * intPaginationCounter) + 1;
            int intFinalRowNum = ((intPaginationHit + 1) * intPaginationCounter) + 1;

            sbAllData.append(" select * from (SELECT   dd.*, ");
            sbAllData.append(" DENSE_RANK () OVER ( ");
            // sbAllData.append(sbOrderQuery);
            if (STR_AC6000.equalsIgnoreCase(strCtrlCfg)) {
                if (STR_0.equalsIgnoreCase(strCountOccurDate)) {
                    // FaultLogHelper.log("Inside AC6000 if :
                    // "+strCountOccurDate);
                    /*
                     * sbAllData.append(" ,fault_index DESC, ");
                     * sbAllData.append(" fault_objid, "); sbAllData.append(
                     * " v_sample_no DESC ");
                     */
                    sbAllData.append(sbOrderQuery);
                } else {
                    // FaultLogHelper.log("Inside AC6000 else : "+sbOrderQuery);
                    sbAllData.append(sbOrderQuery);
                    sbAllData.append(" ,fltindex DESC, ");
                    sbAllData.append(" fault_objid, ");
                    sbAllData.append(" v_sample_no DESC ");
                }
            } else {
                sbAllData.append(sbOrderQuery);
            }
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
            FaultLogHelper.log("Error in getPagination in ViewVehicleFaultDAO : " + e);
            throw e;
        }
        return sbAllData;

    }

    /**
     * @param strTitle
     * @return
     * @throws Exception
     */
    public String getRecordTypes(String strTitle) throws Exception {
        String strRecordType = "";
        Query recordTypeQuery = null;
        List rsRecordType = null;
        String strRecTypes = null;
        Session session = null;
        try {

            session = getHibernateSession();
            
            recordTypeQuery = session.createSQLQuery(" Select value from gets_rmd.gets_rmd_sysparm where title = :strTitle ");

            recordTypeQuery.setString("strTitle", ESAPI.encoder().encodeForSQL(ORACLE_CODEC,strTitle));

            rsRecordType = recordTypeQuery.list();

            if (rsRecordType != null && !rsRecordType.isEmpty()) {
                strRecTypes = (String) rsRecordType.get(0);
            }

            StringTokenizer st = new StringTokenizer(strRecTypes, ",");
            String strData = null;
            while (st.hasMoreTokens()) {
                if (st.hasMoreTokens()) {
                    strData = st.nextToken();
                    strRecordType += "'" + strData + "',";
                }
            }
            // FaultLogHelper.log("strRecType before :"+strRecordType);
            strRecordType = strRecordType.substring(0, strRecordType.length() - 1);
            FaultLogHelper.log("strRecType after :" + strRecordType);

        } catch (Exception e) {
            FaultLogHelper.log("Error in getting Record Types :" + e);
            throw e;
        } finally {
            releaseSession(session);
        }
        return strRecordType;
    }

}