/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDServiceConstants.java
 * Description : 
 * 
 * Package :com.ge.trans.rmd.services.common.constants.RMDServiceConstants
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2008 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.kep.common.constants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Constant file for service layer
 * @History :
 ******************************************************************************/
public final class KEPServiceConstants {

    /**
     * private constructor
     */
    private KEPServiceConstants() {
    }

    public static final String DAO_EXCEPTION_GET_TESTER_RESULTS = "Exception in loading the tester details";
    // public static final String DAO_EXCEPTION_GET_TRACKINGID="";
    public static final String DAO_EXCEPTION_GET_RULEID = "KEP_SERVICE_2005";
    public static final String DAO_EXCEPTION_GET_RULE_TITLE = "KEP_SERVICE_2004";
    public static final String DAO_EXCEPTION_GET_CREATEDBY = "KEP_SERVICE_2003";
    public static final String DAO_EXCEPTION_GET_TRACKING_DETAILS = "KEP_SERVICE_2002";
    public static final String DAO_EXCEPTION_GET_SUMMARY_DETAILS = "KEP_SERVICE_2001";
    public static final String DAO_EXCEPTION_GET_PATTERN_DETAILS = "KEP_SERVICE_2007";
    public static final String DAO_EXCEPTION_SAVE_RULETESTER_REQUEST = "DAO_EXCEPTION_SAVE_RULETESTER_REQUEST";
    public static final String DAO_EXCEPTION_SAVE_IMINE_REQUEST = "DAO_EXCEPTION_SAVE_IMINE_REQUEST";
    public static final String DB_CONNECTION_FAILURE_MESSAGE = "";
    public static final String DAO_EXCEPTION_GET_RULETESTERTRACKING = "KEP_SERVICE_2006";
    public static final String EXCEPTION_GENERAL = "";
    public static final String DAO_EXCEPTION_GET_ANALYSISDETAILS = "Exception in GETTING the ANALYSIS details";
    public static final String DAO_EXCEPTION_SEARCHANALYSISDETAILS = "Exception in SEARCHING the ANALYSIS details";
    public static final String DAO_EXCEPTION_SEARCHTRACKINGDETAILS = "Exception in SEARCHING the TRACKING details";
    public static final String DAO_EXCEPTION_GET_TRACKINGDETAILS = "Exception in GETTING the TRACKING details";
    public static final String DAO_EXCEPTION_GET_DATADETAILS = "Exception in GETTING the DATA details";
    public static final String DAO_EXCEPTION_GET_TRACKINGID = "Exception while retrieving iMiner Tracking ID";
    public static final String DAO_EXCEPTION_GET_RULETESTERTRACKINGID = "Exception while retrieving Rule Tester Tracking ID";
    public static final String DAO_EXCEPTION_GET_STATUS = "";
    public static final String DAO_EXCEPTION_GET_RUNNAME = "Exception in GETTING the RUNNAME ID";
    public static final String DAO_EXCEPTION_GET_SUMMARYDETAILS = "Exception in GETTING the SUMMARY DETAILS";
    public static final String DAO_EXCEPTION_INVALID_ASSET = "DAO_EXCEPTION_INVALID_ASSET";
    public static final String DAO_EXCEPTION_GET_ROAD_NO = "DAO_EXCEPTION_GET_ROAD_NO";
    public static final String DAO_EXCEPTION_GET_CLEARING_LOGIC_ID = "DAO_EXCEPTION_GET_CLEARING_LOGIC_ID";

    public static final StringBuilder FETCH_ROOT_CAUSE_VALUES = new StringBuilder().append(
            "SELECT UPPER(ROOT_CAUSE_CODE),COUNT(EVDT) FROM GETS_DW_EOA_RC_DTL RC,GETS_DW_EOA_VEHICLE V,GETS_DW_EOA_MODEL M,GETS_DW_EOA_CUSTOMER C WHERE  V.MODEL_KEY = M.MODEL_KEY AND V.CUSTOMER_KEY = C.CUSTOMER_KEY AND V.VEHICLE_nbr = RC.ASSET_ID AND RC.CUSTOMER_ID = C.CUSTOMER_ID AND ROOT_CAUSE_CODE IS NOT NULL");
    public static final StringBuilder FETCH_SYMPTOM_VALUES = new StringBuilder().append(
            "SELECT UPPER(SYMPTOM_CD),COUNT(EVDT) FROM GETS_DW_EOA_RC_DTL RC,GETS_DW_EOA_VEHICLE V,GETS_DW_EOA_MODEL M,GETS_DW_EOA_CUSTOMER C WHERE  V.MODEL_KEY = M.MODEL_KEY AND V.CUSTOMER_KEY = C.CUSTOMER_KEY AND V.VEHICLE_nbr = RC.ASSET_ID AND RC.CUSTOMER_ID = C.CUSTOMER_ID AND SYMPTOM_CD IS NOT NULL");
    public static final StringBuilder FETCH_RX_VALUES = new StringBuilder().append(
            "SELECT RD.RECOM_OBJID,RD.RECOMM_TITLE,COUNT(CASE_ID) AS CASE_CNT FROM GETS_DW_EOA_RECOM_DEL RD,GETS_DW_EOA_VEHICLE V,GETS_DW_EOA_MODEL M,GETS_DW_EOA_CUSTOMER C WHERE RD.DELIVERY_DATE BETWEEN :start AND :end AND V.MODEL_KEY = M.MODEL_KEY AND V.CUSTOMER_KEY = C.CUSTOMER_KEY AND V.ROAD_NBR = RD.VEHICLE_NO AND RD.CUSTOMER_ID = C.CUSTOMER_ID");

}
