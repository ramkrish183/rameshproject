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
package com.ge.trans.pp.services.common.constants;

/*******************************************************************************
 *
 * @Author      : iGATE
 * @Version     : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact     :
 * @Description : Constant file for service layer
 * @History     :
 *
 ******************************************************************************/
public final class RMDServiceConstants {

    /**  * Constant for hibernate configuration file     */
    public static final String HIBERNATE_CFG_FILE = "/com/ge/trans/rmd/services/hibernate/hibernate.cfg.xml";
    
    public static final String PROPERTY_PATH = ".properties";
    /**  * FaultCode search  */
    public static final String ENTER_STAR = "*";
    /** The Constant SEARCH_BEAN_NAME. */
    public static final String SEARCH_BEAN_NAME = "RuleBOImpl";
    /****START RULE MANAGEMENT ******/
    public static final String DAO_EXCEPTION_PINPOINT_STATUS = "DAO_EXCEPTION_PINPOINT_STATUS";
    public static final String DAO_EXCEPTION_PINPOINT_HISTORY = "DAO_EXCEPTION_PINPOINT_HISTORY";
    public static final String DAO_EXCEPTION_PINPOINT_STATES = "DAO_EXCEPTION_PINPOINT_STATES";
    public static final String DAO_EXCEPTION_PINPOINT_GET_ASSET = "DAO_EXCEPTION_PINPOINT_GET_ASSET";
    public static final String DAO_EXCEPTION_GET_RULESEARCH = "DAO_EXCEPTION_GET_RULESEARCH";
    public static final String DAO_EXCEPTION_GET_SEARCHRULERESULT = "DAO_EXCEPTION_GET_SEARCHRULERESULT";
    public static final String DAO_EXCEPTION_GET_RECOMMENDATION = "DAO_EXCEPTION_GET_RECOMMENDATION";
    public static final String DAO_EXCEPTION_GET_FAULTCODE = "DAO_EXCEPTION_GET_FAULTCODE";
    public static final String DAO_EXCEPTION_GET_ADDRULEINITIALLOAD = "DAO_EXCEPTION_GET_ADDRULEINITIALLOAD";
    public static final String DAO_EXCEPTION_GET_SUBIDLIST = "DAO_EXCEPTION_GET_SUBIDLIST";
    public static final String DAO_EXCEPTION_GET_FUNCTIONLIST = "DAO_EXCEPTION_GET_FUNCTIONLIST";
    public static final String DAO_EXCEPTION_GET_FLEET = "DAO_EXCEPTION_GET_FLEET";
    public static final String DAO_EXCEPTION_GET_RULETITLE = "DAO_EXCEPTION_GET_RULETITLE";
    public static final String DAO_EXCEPTION_GET_CONFIGURATION = "DAO_EXCEPTION_GET_CONFIGURATION";
    public static final String DAO_EXCEPTION_GET_FUNCTION = "DAO_EXCEPTION_GET_FUNCTION";
    public static final String DAO_EXCEPTION_GET_MODEL = "DAO_EXCEPTION_GET_MODEL";
    public static final String DAO_EXCEPTION_GET_RULEVIEW = "DAO_EXCEPTION_GET_RULEVIEW";
    public static final String DAO_EXCEPTION_INSERTDETAILS = "DAO_EXCEPTION_INSERTDETAILS";
    public static final String DAO_EXCEPTION_ACTIVATERULE = "DAO_EXCEPTION_ACTIVATERULE";
    public static final String DAO_EXCEPTION_GET_CONFIG_FN_VALUES = "DAO_EXCEPTION_GET_CONFIG_FN_VALUES";
    public static final String DAO_EXCEPTION_GET_CUSTOMER = "DAO_EXCEPTION_GET_CUSTOMER";
    public static final String ROR_FOR_OTHER_FINAL_RULES = "ROR_FOR_OTHER_FINAL_RULES";
    public static final String RECOMM_NOT_APPROVED_STATUS = "RECOMM_NOT_APPROVED_STATUS";
    public static final String DAO_EXCEP_LOCK_RELEASE_RULE = "DAO_EXCEP_LOCK_RELEASE_RULE";
    public static final String DAO_EXCEP_LOCK_RELEASE_RXEXEC = "DAO_EXCEP_LOCK_RELEASE_RXEXEC";
    public static final String DAO_EXCEPTION_GET_CASEIDS = "DAO_EXCEPTION_GET_CASEIDS";
    public static final String DAO_EXCEPTION_GET_RULES = "DAO_EXCEPTION_GET_RULES";
    /****END RULE MANAGEMENT ******/
    public static final String DAO_EXCEPTION_GET_RXTITLE = "DAO_EXCEPTION_GET_RXTITLE";
    public static final String DAO_EXCEPTION_GET_RXMETRICS = "DAO_EXCEPTION_GET_RXMETRICS";
    public static final String DAO_EXCEPTION_GET_RULEMEASURE = "DAO_EXCEPTION_GET_RULEMEASURE";
    public static final String DAO_EXCEPTION_GET_MY_CASES = "DAO_EXCEPTION_GET_MY_CASES";
    public static final String DAO_EXCEPTION_GET_DWQ_CASES = "DAO_EXCEPTION_GET_DWQ_CASES";
    public static final String DAO_EXCEPTION_GET_QUEUE = "DAO_EXCEPTION_GET_QUEUE";
    public static final String DAO_EXCEPTION_INVALID_ASSET = "DAO_EXCEPTION_INVALID_ASSET";
    public static final String DAO_EXCEP_INVALID_ASSET_NUM = "DAO_EXCEP_INVALID_ASSET_NUM";
    public static final String DAO_EXCEPTION_GET_ASSET_NOS = "DAO_EXCEPTION_GET_ASSET_NOS";
    public static final String DAO_EXCEPTION_GET_ALL_ROAD = "INVALID_ROAD_NUMBER_ALL";
    public static final String DAO_EXCEPTION_LOAD_CASE_DET = "DAO_EXCEPTION_LOAD_CASE_DET";
    public static final String DAO_EXCEPTION_GET_ASSET_INFO = "DAO_EXCEPTION_GET_ASSET_INFO";
    public static final String DAO_EXCEPTION_GET_CASE_HIST = "DAO_EXCEPTION_GET_CASE_HIST";
    public static final String DAO_EXCEPTION_GET_PREV_CASES = "DAO_EXCEPTION_GET_PREV_CASES";
    public static final String DAO_EXCEPTION_GET_ASSET_HDRS = "DAO_EXCEPTION_GET_ASSET_HDRS";
    public static final String DAO_EXCEPTION_GET_FIND_CASES = "DAO_EXCEPTION_GET_FIND_CASES";
    public static final String DAO_EXCEPTION_GET_FAULT_CLASS = "DAO_EXCEPTION_GET_FAULT_CLASS";
    public static final String DAO_EXCEPTION_SAVE_FSS = "DAO_EXCEPTION_SAVE_FSS";
    public static final String DAO_EXCEPTION_FIND_FAULT = "DAO_EXCEPTION_FIND_FAULT";
    public static final String DAO_EXCEPTION_GET_QUEUES = "DAO_EXCEPTION_GET_QUEUES";
    public static final String INSERT_ACTIVITY_ERROR_CODE = "INSERT_ACTIVITY_ERROR_CODE";
    public static final String DAO_EXCEPTION_REOPEN = "DAO_EXCEPTION_REOPEN";
    public static final String DAO_EXCEPTION_SAVE_CASE = "DAO_EXCEPTION_SAVE_CASE";
    public static final String DAO_EXCEPTION_GET_TOOL_OUT = "DAO_EXCEPTION_GET_TOOL_OUT";
    public static final String DAO_EXCEPTION_GET_RECOM_DETAILS = "DAO_EXCEPTION_GET_RECOM_DETAILS";
    public static final String RX_DELIVER_STATUS = "RECOMMENDATION_DELIVERED_CLOSED";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    public static final String GENERAL_BO_EXCEPTION = "GENERAL_BO_EXCEPTION";
    public static final String LOOK_STATE = "lookState";
    public static final String GENERAL_BO_VALIDATION_EXPCEPTION = "GENERAL_BO_VALIDATION_EXPCEPTION";
    public static final String DAO_EXCEPTION_GET_FAULT_DETAILS = "DAO_EXCEPTION_GET_FAULT_DETAILS";
    public static final String DAO_EXCEPTION_GET_FAULT_HEADERS = "DAO_EXCEPTION_GET_FAULT_HEADERS";
    public static final String DAO_EXCEPTION_GET_FAULT_GRP_HEADERS = "DAO_EXCEPTION_GET_FAULT_GRP_HEADERS";
    public static final String DAO_EXCEPTION_GET_FAULT_TOTAL_COUNT = "DAO_EXCEPTION_GET_FAULT_TOTAL_COUNT";
    public static final String FIELD_REQUEST = "FIELD_REQUEST";
    /******Start Advisory*********/
    public static final String DAO_EXCEPTION_GET_RXSTATUSLIST = "DAO_EXCEPTION_GET_RXSTATUSLIST";
    public static final String DAO_EXCEPTION_GET_RXTYPELIST = "DAO_EXCEPTION_GET_RXTYPELIST";
    public static final String DAO_EXCEPTION_GET_REPAIRCODE = "DAO_EXCEPTION_GET_REPAIRCODE";
    public static final String DAO_EXCEPTION_GET_REPAIRCODETASK = "DAO_EXCEPTION_GET_REPAIRCODETASK";
    public static final String DAO_EXCEPTION_GET_ASSETIMPLIST = "DAO_EXCEPTION_GET_ASSETIMPLIST";
    public static final String DAO_EXCEPTION_GET_URGREPAIRLIST = "DAO_EXCEPTION_GET_URGREPAIRLIST";
    public static final String DAO_EXCEPTION_GET_ESTMTIMEREPAIRLIST = "DAO_EXCEPTION_GET_ESTMTIMEREPAIRLIST";
    public static final String DAO_EXCEPTION_GET_SEARCHADVISORY = "DAO_EXCEPTION_GET_SEARCHADVISORY";
    public static final String DAO_EXCEPTION_GET_TASKADVISORY = "DAO_EXCEPTION_GET_TASKADVISORY";
    public static final String DAO_EXCEPTION_GET_ADVISORYDETAILS = "DAO_EXCEPTION_GET_ADVISORYDETAILS";
    public static final String DAO_EXCEPTION_SAVEADVISORY = "DAO_EXCEPTION_SAVEADVISORY";
    public static final String DAO_EXCEPTION_RX_STATUS_UPDATE = "DAO_EXCEPTION_RX_STATUS_UPDATE";
    public static final String DAO_EXCEPTION_RELEASE_RX_LOCK = "DAO_EXCEPTION_RELEASE_RX_LOCK";
    public static final String DAO_EXCEPTION_LOCK_RX = "DAO_EXCEPTION_LOCK_RX";
    public static final String DAO_EXCEPTION_GET_LOCKED_BY = "DAO_EXCEPTION_GET_LOCKED_BY";
    public static final String DAO_EXCEPTION_TITLECHECK = "DAO_EXCEPTION_TITLECHECK";
    public static final String DAO_EXCEPTION_RXDEACTIVATE = "DAO_EXCEPTION_RXDEACTIVATE";
    public static final String DAO_EXCEPTION_GET_MASSAPPLYADVISORY = "DAO_EXCEPTION_GET_MASSAPPLYADVISORY";
    /******End Advisory*********/
    /******Start Test Rule*********/
    public static final String DAO_EXCEPTION_SAVE_RUN_TEST = "DAO_EXCEPTION_SAVE_RUN_TEST";
    public static final String DAO_EXCEPTION_GET_ASSET_HEADER = "DAO_EXCEPTION_GET_ASSET_HEADER";
    public static final String DAO_EXCEPTION_GET_INITIAL_LOAD = "DAO_EXCEPTION_GET_INITIAL_LOAD";
    public static final String DAO_EXCEPTION_GET_RULE_ID = "DAO_EXCEPTION_GET_RULE_ID";
    public static final String DAO_EXCEPTION_GET_USER = "DAO_EXCEPTION_GET_USER";
    public static final String DAO_EXCEPTION_GET_TRACK_RULEID = "DAO_EXCEPTION_GET_TRACK_RULEID";
    public static final String DAO_EXCEPTION_GET_TRACKINGID = "DAO_EXCEPTION_GET_TRACKINGID";
    public static final String DAO_EXCEPTION_GET_SERCH_RESULT = "DAO_EXCEPTION_GET_SERCH_RESULT";
    /******End Test Rule*********/
    public static final String DAO_EXCEPTION_GET_ASST_LIST = "DAO_EXCEPTION_GET_ASST_LIST";
    public static final String DAO_EXCEPTION_RUN_JDPAD="DAO_EXCEPTION_RUN_JDPAD";
    public static final String DAO_EXCEPTION_GET_TRACK_ID="DAO_EXCEPTION_GET_TRACK_ID";
    public static final String DAO_EXCEPTION_SEARCH_JDPAD_LIST="DAO_EXCEPTION_SEARCH_JDPAD_LIST";

    /******Start Notes*********/
    public static final String DAO_EXCEPTION_EMAIL = "DAO_EXCEPTION_EMAIL";
    public static final String DAO_EXCEPTION_SAVE_NOTES = "DAO_EXCEPTION_SAVE_NOTES";
    public static final String DAO_EXCEPTION_OVERWRITE_NOTES = "DAO_EXCEPTION_OVERWRITE_NOTES";
    public static final String DAO_EXCEPTION_GET_SEARCH_NOTES = "DAO_EXCEPTION_GET_SEARCH_NOTES";
    public static final String DAO_EXCEPTION_RMV_STICKY_NOTES = "DAO_EXCEPTION_RMV_STICKY_NOTES";
    public static final String DAO_EXCEPTION_GET_INITALLOAD_NOTES = "DAO_EXCEPTION_GET_INITALLOAD_NOTES";
    public static final String DAO_EXCEPTION_REMOVE_FIND_NOTES = "DAO_EXCEPTION_REMOVE_FIND_NOTES";
    public static final String DAO_EXCEPTION_GET_FIND_NOTES = "DAO_EXCEPTION_GET_FIND_NOTES";
    public static final String DAO_EXCEPTION_FIND_NOTE_INIT = "DAO_EXCEPTION_FIND_NOTE_INIT";
    public static final String DAO_EXCEP_DEL_FLEET_REMARKS = "DAO_EXCEP_DEL_FLEET_REMARKS";
    public static final String NO_FLEET_SUMMARY_REMARK = "NO_FLEET_SUMMARY_REMARK";
    public static final String ENT_VALID_RN_NUM = "VALID_AST_NO";
    /******End Notes*********/
    public static final String LOOKUP_ERROR_CODE = "RMD_LOOKUP_ERROR_CODE";
    /***** Start Login module *********/
    public static final String DAO_EXCEPTION_USER_AUTHENTICATION = "DAO_EXCEPTION_USER_AUTHENTICATION";
    public static final String DAO_EXCEPTION_GET_TIME_ZONES = "DAO_EXCEPTION_GET_TIME_ZONES";
    /***** End Login module **********/
    public static final String BO_EXCEPTION_PDF_GENERATION = "BO_EXCEPTION_PDF_GENERATION";
    public static final String EXCEPTION_FILE_PATH = "EXCEPTION_FILE_PATH";

    public static final String DAO_EXCEPTION_GET_PDF_DETAILS = "DAO_EXCEPTION_GET_PDF_DETAILS";
    public static final String DAO_EXCEPTION_GET_RX_DELIVERED = "DAO_EXCEPTION_GET_RX_DELIVERED";
    public static final String DAO_EXCEPTION_RXDELIVERED_CASES = "DAO_EXCEPTION_RXDELIVERED_CASES";
    public static final String DAO_EXCEPTION_GET_RX_EXECDETAILS = "DAO_EXCEPTION_GET_RX_EXECDETAILS";
    public static final String DAO_EXCEPTION_CHECK_RXEXEC = "DAO_EXCEPTION_CHECK_RXEXEC";
    public static final String DAO_EXCEPTION_RCA_REPORT = "DAO_EXCEPTION_RCA_REPORT";

    //Health Check EJB JNDI Name
    public static final String HEALTH_CHECK_JNDI_NAME = "ejb/RMDOMI/RequestProcessorSLSB";


    /**
     * Constant for look value field in database
     */
    public static final String LOOK_VALUE = "lookValue";
    public static final String SYSLOOKUP_ID = "getSysLookupSeqId";
    /**
     * Case status lookup id for open case
     */
    public static final String CREATE_CASE_LOOKUP_STATUS = "1";
    public static final String CREATE_CASE_LOOKUP_STATE = "CASE_CONDITION";
    /**
     * Lookup value for open case
     */
    public static final String CREATE_CASE_LOOKUP_STATUS_VALUE = "Open";
    public static final String CASE_LOOKUP_ACTIVITY = "CASE_ACTIVITY";
    public static final String CASE_LOOKUP_REOPEN_VALUE = "Re Open";
    public static final String CASE_LOOKUP_MODIFY_VALUE = "Modify";
    public static final String CASE_TITLE_SET_TO = "CASE_TITLE_SET_TO";
    public static final String QUEUE_NAME = "name";
    public static final String CASE_REOPEN_MSG = "CASE_REOPEN_MSG";
    public static final String DS_DELIMITTER = "#$#";

    /******Advisory constants******/
    public static final String NEWRECOMM = "new";
    public static final String EDITRECOMM = "edit";
    public static final String CLONERECOMM = "clone";
    public static final String SELRECOMM = "SelectRx";
    public static final String SELECT = "Select";
    public static final String ALL = "All";
    public static final String SPECIFY = "Specify";
    public static final String APPROVED = "Approved";
    public static final String INPROCESS = "In Process";
    public static final String OBSOLETE = "Obsolete";
    public static final String STANDARD = "Standard";
    public static final String NONE = "None";
    public static final String INPROCESSLBL = "InProcess";
    /******Notes constants******/
    public static final String NOTESMENU = "menu";
    public static final String NOTESCASE = "case";
    public static final String NOTESLOCO = "loco";
    public static final String CASEAPPLYLEVEL = "Case";
    public static final String UNITAPPLYLEVEL = "Unit";
    public static final String NOTE_TYPE = "NOTES_TYPE";
    public static final String LANGUAGE_LIST_NAME = "QTR_RMD_LANGUAGES";
    public static final String LIST_NAME = "listName";
    public static final String SORT_ORDER = "sortOrder";
    public static final String IN_ACTIVE = "InActive";
    public static final String ACTIVE = "Active";
    public static final String FSS_SAVE_SUCCESS = "Fault Code changes saved successfully";
    public static final String CASE_SAVE_SUCCESS = "Case Title saved successfully";
    public static final String DISPATCH_SAVE_SUCCESS = "DISPATCH_SAVE_SUCCESS";
    /******Start Sticky Notes*********/
    public static final String CASE_STKY_NOTES = "Case Notes";
    public static final String ASSET_STKY_NOTES = "Asset Notes";
    /******End Sticky Notes*********/
    /**
     * caseId  field in database
     */
    public static final String CASEID = "caseId";
    public static final String GETCMCASE = "getCmCase";
    public static final String GETCMCASE_SEQ_ID = "getCmCaseSeqId";
    public static final String RXCASEID = "rxCaseId";
    public static final String GETASSTASSET = "getAsstAsset";
    public static final String ASSETNUMBER = "assetNumber";
    public static final String ROADNUMBER = "roadNumber";
    public static final String ASSETHEADER = "assetHeader";
    public static final String ASSET_GRP_HOME = "assetGroupHomeHVO";
    public static final String ASSETNUMBER_ALIAS = "assetNo";
    public static final String ASSET_GRP = "getAsstGroup";
    public static final String ASSET_GRP_ALIAS = "assetGroup";
    public static final String ASSET_GRP_SEQ_ID = "getAsstAssetSeqId";
    public static final String CREATION_DATE = "creationDate";
    public static final String CREATED_BY = "createdBy";
    public static final String ACTIVATED_BY = "activatedBy";
    public static final String DESCRIPTION = "description";
    public static final String CUSTNAME = "custName";
    public static final String STATUS_COLUMN = "status";
    public static final String SYSCONFIGLOOKUP = "getSysLookupByLinkStatusLookup";
    public static final String ADVISORY_DEL_SEQ_ID = "getCmRecomDelvSeqId";
    public static final String COLUMN_NAME = "columnName";
    public static final String GET_KM_DPD_COLUMN_NAME = "getKmDpdColname";
    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_HVO = "customerHVO";
    public static final String GET_KM_DPD_FINAL_RULE = "getKmDpdFinrul";
    public static final String GET_KM_DPD_FINAL_RULE_ALIAS = "finRulVO";
    public static final String ORIGINAL_ID = "originalId";
    /*****NOTES FIELD IN DATABASE******/
    public static final String LINK_ASSET = "getAsstAssetByLinkAsset";
    public static final String LINK_ASSET_ALIAS = "assetVO";
    public static final String STICKY = "sticky";
    
    /******COUNTRY STATE LIST*********/
    
    public static final String DAO_EXCEPTION_COUNTRY_STATE="DAO_EXCEPTION_COUNTRY_STATE";
    
    /*****CLOSE CASE******/
    public static final String RECOMMENDATION_DELIVERED_CLOSED = "RECOMMENDATION_DELIVERED_CLOSED";
    public static final String RECOMMENDATION_DELIVERED_NOT_CLOSED = "RECOMMENDATION_DELIVERED_NOT_CLOSED";
    public static final String NO_RECOMMENDATION_DELIVERED = "NO_RECOMMENDATION_DELIVERED";
    public static final String DAO_EXCEPTION_CHECK_RXDELIVER = "DAO_EXCEPTION_CHECK_RXDELIVER";
    public static final String DAO_EXCEPTION_RX_EXECUTION_INITIAL_RXSTATUS_DROPDOWN_LOAD = "DAO_EXCEPTION_RX_EXECUTION_INITIAL_RXSTATUS_DROPDOWN_LOAD";
    public static final String DAO_EXCEPTION_GET_RXLOAD = "DAO_EXCEPTION_GET_RXLOAD";
    public static final String DAO_EXCEPTION_SAVECUSTFDBK = "DAO_EXCEPTION_SAVECUSTFDBK";
    public static final String CLOSED = "Closed";
    public static final String OPEN = "Open";
    public static final String EXECUTED = "Executed";
    public static final String RX_STATUS_CLOSED = "closed";
    public static final String RX_STATUS_OPEN = "open";
    /*****NOTES******/
    public static final String NOTES_SEQ_ID = "getCmGenericNoteLogSeqId";
    public static final String STICKY_NOTE = "Sticky Note";
    public static final String GENERIC_NOTE = "Generic Note";
    public static final String LOCO_NOTES = "Locomotive Note";
    public static final String UNITSTICKYEXIST = "UNITSTICKYEXIST";
    public static final String CASESTICKYEXIST = "CASESTICKYEXIST";
    public static final String CASEASSETSTICKYEXIST = "CASEASSETSTICKYEXIST";
    public static final String CASE_OPEN = "Open";
    public static final String WORK_QUEUE = "Work";
    public static final String EMPTY_TEXT = "EMPTY_TEXT";
    public static final String CASE_NOTES = "CASE_NOTES";
    public static final String FIND_ID = "Id";
    public static final String TITLE = "Title";
    public static final String CONDITION = "Condition";
    public static final String STATUS = "Status";
    public static final String STARTS_WITH = "starts with";
    public static final String ENDS_WITH = "ends with";
    public static final String CONTAINS = "contains";
    public static final String EQUALS = "=";
    public static final String IS_EQUAL = "is equal to";
    public static final String IS_NOT_EQUAL = "is not equal to";

    public static final String CONTACT = "Contact";
    public static final String CREATION_TIME = "Creation Time";
    public static final String QUEUE = "Queue";
    public static final String FAULT_CODE = "faultCode";
    public static final String SUB_ID = "subId";
    public static final String TITLE_RECOM = "title";
    /*****RULE TESTER******/
    public static final String RULE_ID_KEY = "ruleIdKey";
    public static final String RULE_ID = "ruleId";
    public static final String TRACKLING_ID_KEY = "trackingIdKey";
    public static final String ACTIVE_FLAG = "activeFlag";
    public static final String JDPAD_TESTER_STATUS = "JDPAD_TESTER_STATUS";
    public static final String JDPAD_TRACER_STATUS = "JDPAD_TRACER_STATUS";
    public static final String NEW = "NEW";
    public static final String OCCUR_DATE = "OCCUR_TIME";
    public static final String FAULT_RESET_DATE = "RESET_TIME";
    public static final String OFFBOARD_CREATION_DATE = "CREATION_DATE";
    public static final String LINK_FAULT_CODES = "FAULT_CODE";
    public static final String ASSET_NUM_COL = "asset.ASSET_NUMBER";
    public static final String ASSET_HDR_COL = "asset_grp.NAME";
    public static final String ASSET_NUM_COL_INFO = " <BR>Asset<BR>Number";
    public static final String ASSET_NUM_COL_WIDTH = "80";
    public static final String ASSET_NUM_COL_TOOL_TIP = "Asset Number";
    public static final String ASSET_HDR_COL_INFO = " <BR>Asset<BR>Header";
    public static final String ASSET_HDR_COL_WIDTH = "80";
    public static final String ASSET_HDR_COL_TOOL_TIP = "Asset Header";
    public static final String RUNNING = "RUNNING";
    public static final String COMPLETE = "COMPLETE";
    /*****ADVISORY******/
    public static final String ADVISORY_TITLE_EXIST = "RxTitle Already Exist";
    public static final String ADVISORY_ADD_SUCC_MSG = "ADVISORY_ADD_SUCC_MSG";
    public static final String ADVISORY_UPDATE_SUCC_MSG = "ADVISORY_UPDATE_SUCC_MSG";
    public static final String ADVISORY_CLONE_SUCC_MSG = "ADVISORY_CLONE_SUCC_MSG";
    public static final String ADVISORY_ADD_FAIL_MSG = "ADVISORY_ADD_FAIL_MSG";
    public static final String ADVISORY_CLONE_FAIL_MSG = "ADVISORY_CLONE_FAIL_MSG";
    public static final String ADVISORY_UPDATE_FAIL_MSG = "ADVISORY_UPDATE_FAIL_MSG";
    public static final String ADVISORY_TITLE_EXIST_MSG = "ADVISORY_TITLE_EXIST_MSG";
    public static final String ADVISORY_REPORT = "ADVISORY_REPORT";
    public static final String CASEADVISORY_REPORT = "CASEADVISORY_REPORT";
    public static final String ASSOCIATED_TASK = "Associated Tasks :";
    /*****FAULT SERVICE STGY CONSTANTS******/
    public static final String GET_KM_FLT_STRATEGIES = "getKmFltStrategies";
    public static final String GET_KM_FLT_STRATEGIES_ALIAS = "faultStrategy";
    public static final String FLT_CLASSIFICATION = "FAULT_CLASSIFICATION";
    //public static final String PDF_FOOTER = "2009 � General Electric Company. All Rights Reserved";
    public static final String PDF_XSL_FILE_PATH = "/com/ge/trans/rmd/resources/Advisory_Report.xsl";
    public static final String CASE_PDF_XSL_FILE_PATH = "/com/ge/trans/rmd/resources/mdsc_recom_delv.xsl";
    public static final String PDF_FILE_NAME = "Recommendation_Report.pdf";
    public static final String EXCEL_FILE_NAME = "Export.xls";
    public static final String SHEET = "Sheet";
    public static final String XML_FILE_NAME = "/Advisory_Report.xml";
    public static final String FO_FILE_NAME = "/Advisory_Report.fo";
    public static final String REPORT_FILE_PATH = "REPORT_FILE_PATH";
    /***********Rule default states************/
    public static final Long DEFAULT_RULE_VERSION = 1L;
    public static final Long DEFAULT_RULE_COMPLETETION_STATUS = 1L;
    public static final Long INACTIVE_STATE = 0L;
    public static final Long ACTIVE_STATE = 1L;
    public static final String DATE_FORMAT = "EEE MM/dd/yyyy hh:mm:ss";
    public static final String DATE_FORMAT2 = "MM/dd/yyyy hh:mm:ss";
    public static final String CAL_DATE_PATTERN = "M/d/yyyy HH:mm:ss";
    public static final String REASON_LOOK_VAL_APPEND = "Append";
    public static final String REASON_LIST_NAME = "Reason";
    /*****AddRxBOImpl******/
    public static final String RECOMMENDATIONS = "recommendations";
    public static final String TASK_ID_VALUE = "TASK_ID_VALUE";
    public static final String TASK_DESC_VALUE = "TASK_DESC_VALUE";
    public static final String UPPER_SPEC_VALUE = "UPPER_SPEC_VALUE";
    public static final String LOWER_SPEC_VALUE = "LOWER_SPEC_VALUE";
    public static final String TARGET_VALUE = "TARGET_VALUE";
    public static final String PDF = ".pdf";
    public static final String ADVISORY_FILE_PATH = "ADVISORY_FILE_PATH";
    public static final String XML = ".xml";
    public static final String FO = ".fo";
    public static final String DELIVERED = "Delivered";
    public static final String URGENCY_OF_REPAIR = "URGENCY_OF_REPAIR";
    public static final String ESTIMATEDTIMETOREPAIR = "ESTIMATED_TIME_TO_REPAIR";
    public static final String ADVISORY_DESCRIPTION = "ADVISORY_DESCRIPTION";
    public static final String ACTIVITY = "CASE_ACTIVITY";
    public static final String ADVISORY_ACTIVITY = "Advisory Delivered";
    public static final String SYMBOL = "#";
    public static final String GETKMRECOMSEQID = "getKmRecomSeqId";
    public static final String GETKMRECOM = "getKmRecom";
    public static final String GETKMRECOMTASKDOCS = "getKmRecomTaskDocs";
    public static final String COLUMN_TASK_ID = "taskId";
    public static final String RMD_PROBLEM = "- RMD Problem";
    public static final String RMD_EQUIPMENT = "- RMD Equipment";
    public static final String INFORMATIONAL = "- Informational";
    public static final String LETTER_R = "R";
    public static final String LETTER_W = "W";
    public static final String LETTER_B = "B";
    public static final String LETTER_G = "G";
    public static final String LETTER_O = "O";
    //new added
    public static final String LESSTHAN_TWOHRS = "< 2hrs";
    public static final String BETWEENTWO_FOUR = "2-4hrs";
    public static final String BETWEENFOUR_SIX = "4-6hrs";
    public static final String BETWEENSIX_EIGHT = "6-8hrs";
    public static final String GREATERTHAN_ONEDAY = "> 1 day";
    public static final String LESSTHAN_EIGHTHRS = "< 8hrs";
    public static final String BETWEENEIGHT_SIXTEEN = "8-16hrs";
    public static final String BETWEENSIXTEEN_TWENTYFOUR = "16-24hrs";
    public static final String FILTER = "Filter";
    public static final String MONTH_DATE_YEAR = "MM/dd/yyyy hh:mm:ss a";
    public static final String SINGLEQUOTE = "'";
    public static final String SINGLEQUOTE_PERCENTAGE = "'%";
    public static final String PERCENTAGE_SINGLEQUOTE = "%'";
    public static final String DEFAULT_CASE_TPYE = "Diagnostics";
    public static final String ADVISORY_CLOSED = "ADVISORY_CLOSED";
    public static final String ADVISORY_EXEC_CLOSED = "ADVISORY_EXEC_CLOSED";
    public static final String ADVISORY = "Advisory";
    public static final String ADVISORY_EXECUTION = "Advisory Execution";
    public static final String TRUE = "true";
    public static final String ACTIVATERULE = "ActivateRule";
    /******CaseBOImpl******/
    public static final String HIGH = "High";
    public static final String JOY = "JOY";
    public static final String AUTOCASE_FOR_SPRX = "AUTOCASE_FOR_SPRX";
    public static final String PERCENTAGE = "%";
    public static final String TITLE_ALREADY_EXIST = "TitleExist";
    public static final String RULE_TITLE = "ruleTitle";
    /******AddRxDAoImpl******/
    public static final String DAO_EXCEPTION_FETCHESTTIMEREPAIR = "DAO_EXCEPTION_FETCHESTTIMEREPAIR";
    public static final String DAO_EXCEPTION_FETCHURGREPAIR = "DAO_EXCEPTION_FETCHURGREPAIR";
    public static final String DAO_EXCEPTION_SAVERXDELVINFO = "DAO_EXCEPTION_SAVERXDELVINFO";
    public static final String DAO_EXCEPTION_REMOVESTICKY = "DAO_EXCEPTION_REMOVESTICKY";
    public static final String ORDERBY_DEL_DATE = "delvDate";
    /******RecommDAoImpl******/
    public static final String ALPHABET_W = "W";
    public static final String ALPHABET_Y = "Y";
    public static final String ALPHABET_N = "N";
    public static final String ALPHABET_R = "R";
    public static final String ALPHABET_B = "B";
    public static final String ALPHABET_G = "G";
    public static final String ALPHABET_O = "O";
    public static final String NUMBER_1 = "1";
    public static final String NUMBER_2 = "2";
    public static final String NUMBER_3 = "3";
    public static final String ESTIMATED_LIST_NAME = "ESTIMATED_TIME_TO_REPAIR";
    public static final String URGENCY_LIST_NAME = "URGENCY_OF_REPAIR";
    public static final String ASSET_IMPACT_LIST_NAME = "LOCOMOTIVE_IMPACT";
    public static final String RX_TYPE_LIST_NAME = "RX_TYPES";
    public static final String RECOMM_STATUS_LIST_NAME = "RX_STATUSES";
    public static final String RX_DELIVERY_STATUS = "RX_DELIVERY_STATUS";
    public static final String LESS_THAN_24_HRS = "- <24 hrs";
    public static final String BETWEEN_24TO48_HRS = "- 24-48 hrs";
    public static final String GREATER_THAN_48_HRS = "- >48 hrs";
    /******Constants for column name in database******/
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URGENCY = "urgency";
    public static final String COLUMN_ESTM_TIME_REPAIR = "estRepairTime";
    public static final String COLUMN_RECOMM_STATUS = "recomStatus";
    public static final String COLUMN_RECOMM_TYPE = "recomType";
    /******Advisory History Constants******/
    public static final String TITLE_CHANGED = "TITLE_CHANGED";
    public static final String RX_STATUS_CHANGED = "RX_STATUS_CHANGED";
    public static final String URG_REPAIR_CHANGED = "URG_REPAIR_CHANGED";
    public static final String ASSET_IMPACT_CHANGED = "ASSET_IMPACT_CHANGED";
    public static final String ESTM_TIME_REPAIR_CHANGED = "ESTM_TIME_REPAIR_CHANGED";
    public static final String REV_NUMBER_CHANGED = "REV_NUMBER_CHANGED";
    public static final String TASK_CHANGED = "TASK_CHANGED";
    public static final String RX_TYPE_CHANGED = "RX_TYPE_CHANGED";
    /******Notes email format Constants******/
    public static final String PROTOCOL = "mail.smtp.host";
    public static final String NOTES_FROM_MENU = "Notes Added From Menu";
    public static final String NOTES_FROM_CASE = "Notes Added From Case";
    public static final String CUST = "Customer : ";
    public static final String CASE_ID_NA = "Case ID : N/A";
    public static final String CASE_ID = "Case ID : ";
    public static final String NOTES = "Notes : ";
    public static final String ASSET_FROM = "ASSET From : ";
    public static final String ASSET_TO = "ASSET To : ";
    public static final String ASSET_NUMBER = "Asset Number : ";
    public static final String HTML_BR = "</br>";
    public static final String MAIL_FROM = "EmailFrom";
    public static final String MAIL_TO = "EmailTo";
    public static final String PROB_IDENTIF = " Problem Identification";
    public static final String DAO_EXCEPTION_HEALTHASSETINFO = "DAO_EXCEPTION_HEALTHASSETINFO";
    /****Business validation*****/
    public static final String ATLEAST_ONE_RULE_DEFINITION = "ATLEAST_ONE_RULE_DEFINITION";
    public static final String ATLEAST_ONESIMPLE_RULE_NEEDED = "ATLEAST_ONESIMPLE_RULE_NEEDED";
    public static final String ALL_COMPLEXRULE_HAVE_ONESIMPLE_RULE = "ALL_COMPLEXRULE_HAVE_ONESIMPLE_RULE";
    public static final String LANGUAGE_SELECTION = "strLanguage";
    /***Request History Page******/
    public static final String DAO_EXCEPTION_REQUESTHISROTY_INITIALLOAD = "DAO_EXCEPTION_REQUESTHISROTY_INITIALLOAD";
    public static final String DAO_EXCEPTION_REQHIS_CONDITIONLOAD = "DAO_EXCEPTION_REQHIS_CONDITIONLOAD";
    public static final String DAO_EXCEPTION_REQHIS_SEARCH_RESULT = "DAO_EXCEPTION_REQHIS_SEARCH_RESULT";
    public static final String DAO_EXCEPTION_VEHICLECOMM_SEARCH_RESULT = "DAO_EXCEPTION_VEHICLECOMM_SEARCH_RESULT";
    public static final String ROAD_NUMBER = "Road Number";
    public static final String MESSAGE_ID = "Message ID";
    public static final String REQUEST_TYPE = "Request Type";
    public static final String MSG_TYPE_DESCRIPTION = "Msg_Type_Description";


    public static final String DIRECTION_IN = "IN";
    public static final String DIRECTION_OUT = "OUT";
    /** Keep Alive Status*/
    public static final String DAO_EXCEPTION_KEEP_ALIVE = "DAO_EXCEPTION_KEEP_ALIVE";
    public static final String CONTROLLER_NO_COMM = "CTRL_NO_COMM";
    public static final String TOTAL_NO_COMM = "TOTAL_NO_COMM";
    public static final String GOOD = "GOOD";
    /** Vehicle Comm Status*/
    public static final String KEEP_ALIVE_DAYS = "10";
    /** Create/Find Location*/
    public static final String TIME_ZONE_SEQUENCEID = "getCmTimeZoneSeqId";
    public static final String CONTACT_SEQUENCEID = "getCmContactInfoSeqId";
    public static final String LOCATION_SEQUENCEID = "getAsstLocationSeqId";
    public static final String FIRST_NAME = "firstName";
    public static final String INVALID_LOCATION_ID = "INVALID_LOCATION_ID";
    public static final String TYPE_STATUS = "LOCATION_STATUS";
    public static final String CONTACT_STATUS = "CONTACT_STATUS";
    public static final String MESSAGE_DELIVERY_STATUS = "Message_Status";

    public static final String LOCATION_TYPE = "LOCATION_TYPE";
    public static final String GET_SYSLOOKUP = "getSysLookup";
    public static final String ALIAS_SYS_LOOKUP = "sysLookup";
    public static final String GET_CONTACT_INFO = "getCmContactInfo";
    public static final String ALIAS_COUNTRY = "country";
    public static final String ALIAS_TIMEZONE = "timeZone";
    public static final String ADDRESS = "getCmAddress";
    public static final String ALIAS_ADDRESS = "address";
    public static final String ADDRESS_COUNTRY = "address.getCmCountry";
    public static final String ADDRESS_TIMEZONE = "address.getCmTimeZone";
    public static final String ALIAS_CONTACT_INFO = "contactInfo";
    public static final String CONTACT_COUNTRY = "contactInfo.getCmCountry";
    public static final String CONTACT_TIMEZONE = "contactInfo.getCmTimeZone";
    public static final String DAO_EXCEPTION_FIND_LOCATION = "DAO_EXCEPTION_FIND_LOCATION";
    public static final String DAO_EXCEPTION_CREATE_LOCATION = "DAO_EXCEPTION_CREATE_LOCATION";
    public static final String DAO_EXCEPTION_FIND_LOCATION_INITIAL_STATUS_DROPDOWN_LOAD = "DAO_EXCEPTION_FIND_LOCATION_INITIAL_STATUS_DROPDOWN_LOAD";
    public static final String DAO_EXCEPTION_FIND_LOCATION_INITIAL_TYPE_DROPDOWN_LOAD = "DAO_EXCEPTION_FIND_LOCATION_INITIAL_TYPE_DROPDOWN_LOAD";
    public static final String DAO_EXCEPTION_FIND_LOCATION_TIMEZONE_DROPDOWN_LOAD = "DAO_EXCEPTION_FIND_LOCATION_TIMEZONE_DROPDOWN_LOAD";
    public static final String DAO_EXCEPTION_EDIT_LOCATION = "DAO_EXCEPTION_EDIT_LOCATION";
    public static final String DAO_EXCEPTION_FETCH_CONTACT_FOR_LOCATION = "DAO_EXCEPTION_FETCH_CONTACT_FOR_LOCATION";
    public static final String DAO_EXCEPTION_UPDATE_LOCATION = "DAO_EXCEPTION_UPDATE_LOCATION";
    /** Create/Find Contact***/
    public static final String DAO_EXCEPTION_GET_FIRSTNAME = "DAO_EXCEPTION_GET_FIRSTNAME";
    public static final String DAO_EXCEPTION_GET_LASTNAME = "DAO_EXCEPTION_GET_LASTNAME";
    public static final String DAO_EXCEPTION_GET_LOCATION = "DAO_EXCEPTION_GET_LOCATION";
    public static final String DAO_EXCEPTION_CREATE_CONTACT = "DAO_EXCEPTION_CREATE_CONTACT";
    public static final String DAO_EXCEPTION_FIND_CONTACT = "DAO_EXCEPTION_FIND_CONTACT";
    public static final String DAO_EXCEPTION_EDIT_CONTACT = "DAO_EXCEPTION_EDIT_CONTACT";
    public static final String DAO_EXCEPTION_UPDATE_CONTACT = "DAO_EXCEPTION_UPDATE_CONTACT";
    public static final String LOCATIONID_ALREADY_EXIST = "LocationIDExist";
    public static final String REQ_HISTORY_SEARCH_OPT = "REQ_HISTORY_SEARCH_OPT";
    public static final String REQ_HISTORY_SEARCH_DAYS = "REQ_HISTORY_SEARCH_DAYS";
    public static final String REQ_HISTORY_SEARCH_ROW_PER_PAGE = "REQ_HISTORY_SEARCH_ROW_PER_PAGE";
    public static final String CREATE_LOCATION = "CreateLocation";
    public static final String SITE_DETAILS = "siteDetails";
    public static final String SHOW_NEWCONTACT_PAGE = "showNewContactPage";
    public static final String LOCATION_ID = "locationId";
    // Pop Up List Admin
    public static final String RMD_SCREEN_POPERTIES_LIST_NAME = "RMD_SCREEN";
    public static final String DAO_EXCEPTION_POPUP_ADMIN = "DAO_EXCEPTION_POPUP_ADMIN";
    public static final String BO_EXCEPTION_POPUP_ADMIN = "BO_EXCEPTION_POPUP_ADMIN";
    // Role Management
    public static final String DAO_EXCEPTION_ROLE_MANAGEMENT = "DAO_EXCEPTION_ROLE_MANAGEMENT";
    public static final String BO_EXCEPTION_ROLE_MANAGEMENT = "BO_EXCEPTION_ROLE_MANAGEMENT";
    public static final String DAO_EXCEPTION_USER_MANAGEMENT = "DAO_EXCEPTION_USER_MANAGEMENT";
    public static final String BO_EXCEPTION_USER_MANAGEMENT = "BO_EXCEPTION_USER_MANAGEMENT";
    public static final String PAGE_RESOURCE_TYPE = "page";
    // Purge
    public static final String DAO_EXCEPTION_PURGE = "DAO_EXCEPTION_PURGE";
    //For Find Case
    public static final String ASSET_HOME_HVO = "assetHomeHVO";
    public static final String ASSET_GRP_HOME_HVO = "assetHomeHVO.assetGroupHomeHVO";
    public static final String USER_HOME_HVO = "usersHomeVO";
    public static final String PRIORITY_LOOKUP = "priorityLookup";
    public static final String REASON_LOOKUP = "casereasonLookup";
    public static final String STATUS_LOOKUP = "casestatusLookup";
    public static final String QUEUE_LOOKUP = "queueLookup";
    public static final String ASSET_VO = "assetVO";
    public static final String STATUS_VO = "statusVO";
    public static final String ASSET_GROUP_VO = "assetGroupVO";
    public static final String CUST_FDBK_HVO = "getCmRxStatus";
    public static final String RX_STATUS = "rxStatus";
    public static final String RX_EXECUTION = "rxExecution";
    public static final String RECOM_DELV_HVO = "getCmRecomDelvs";
    public static final String RECOM_DELV_VO = "getCmRecomDelv";
    public static final String RECOM_DELV = "recomDelv";
    public static final String RECOM_HVO = "recomDelv.getKmRecom";
    public static final String RECOM = "recom";
    public static final String QUEUE_VO = "queueVO";
    public static final String ASST_GRP_NAME = "assetGroupVO.name";
    public static final String ASST_RN = "assetVO.roadNumber";
    public static final String STATUS_LOOKVAL = "statusVO.lookValue";
    public static final String QUEUE_VO_NAME = "queueVO.name";
    public static final String RECOM_TITLE = "recom.title";
    public static final String RX_CLOSEDATE = "rxStatus.rxCloseDate";
    public static final String RX_EXEC = "rxExecution.closeDate";
    public static final String SEMI_COLON = ":";
    public static final String CASE_ALIAS = "case";
    public static final String CASE_SEQ_ID = "case.getCmCaseSeqId";
    public static final String ASSET_CUSTOMER_HVO = "assetVO.customerHomeHVO";
    public static final String CUSTOMER_VO = "customerVO";
    public static final String ASSTCUSTOMER_ID = "customerVO.customerId";
    public static final String ASSETGRP_NAME = "assetGroup.name";
        
    /********Proximity Screen********/
    public static final String EXCEPTION_PROXIMITY_LIST = "EXCEPTION_PROXIMITY_LIST";
    public static final String EXCEPTION_PROXIMITY_SAVE = "EXCEPTION_PROXIMITY_SAVE";
    public static final String DAO_EXCEPTION_GET_PROXY = "DAO_EXCEPTION_GET_PROXY";
    public static final String DAO_EXCEPTION_SAVE_PROXY = "DAO_EXCEPTION_SAVE_PROXY";
    /********Proximity Screen********/
    /********Error Report Screen********/
    public static final String REPORT_SEARCH_DAYS = "REQ_HISTORY_SEARCH_DAYS";
    public static final String ERR_REPORT_CATEGORY = "Error_Category";
    public static final String DAO_EXCEPTION_ERR_RPT_DET = "DAO_EXCEPTION_ERR_RPT_DET";
    public static final String DAO_EXCEPTION_ERR_RPT_INIT = "DAO_EXCEPTION_ERR_RPT_INIT";
    public static final String ERROR_CATEGORY_LIST_NAME = "Error_Category";
    /********Error Report Screen********/

    /******Data Screen DAO Constants******/
    public static final String TOOL_RUNID = "ToolRunInd";
    public static final String JDPAD = "JDPAD";
    public static final String FAULTS = "Faults";
    public static final String CRITICAL_FAULT = "Critical Fault";
    public static final String NOW = "now";
    public static final String APPENDED = "Appended";
    public static final String CASE_CREATION_TODATE = "CaseCreaion To Date";
    public static final String CASE_CREATION = "CaseCreation";
    public static final String DUMMY_HDR_LITERAL = "&nbsp;";
    public static final String LANGUAGE = "language";
    public static final String PX = "px";
    public static final String RULE_DEFAULT_DROPDOWN_VALUE = "Pick";
    public static final String COMPLEXRULE_NOTBUILD_PROPERLY = "COMPLEXRULE_NOTBUILD_PROPERLY";
    public static final String LOCATION = "LOCATION";
    public static final String FAULT_DESC = "FAULT_DESC";

    /********** Asset Fault Data ********/
    public static final String VEHICLE_FAULT_DAYS_SELECTION = "VEHICLE_FAULT_DAYS_SELECTION";
    /***** Rx Execution*/
    public static final String ERROR_IN_SAVE_RX_TASKS = "ERROR_IN_SAVE_RX_TASKS";
    public static final String RX_LOCATION = "recommendations";
    /********Locomotive Dashboard Screen starts********/
    public static final String EXCEPTION_LOCODASH_RESULT = "EXCEPTION_LOCODASH_RESULT";
    public static final String EXCEPTION_OPEN_CASES = "EXCEPTION_OPEN_CASES";
    public static final String OK = "OK";
    public static final String MORE = "MORE";
    /********Locomotive Dashboard Screen ends********/
    /********System Dashboard Screen starts********/
    public static final String EXCEPTION_SYSDASH_RESULT = "EXCEPTION_SYSDASH_RESULT";
    public static final String RED_STATUS_STYLECLASS = "sysDashRed";
    public static final String GREEN_STATUS_STYLECLASS = "sysDashGreen";
    public static final String ERROR_RUN_STATUS = "Error";
    public static final String COMPLETE_RUN_STATUS = "Completed";
    public static final String MTS_MSG_OFFLINE_STATUS = "offline";
    public static final String TOOLS_CASE_CREATION = "TOOLS_CASECREATION";
    /********System Dashboard Screen ends********/
    /********Dash board Screen Threshold Constants starts********/
    public static final String KEEP_ALIVE = "KEEP_ALIVE_THRESHOLD";
    public static final String MTS_THRESHOLD = "MTS_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String MTS_RECEIVER = "MTS_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_MSG_RECEIVED = "OMI_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_SUCC_TOOL_RUN = "TOOL_SUCCESS_RUN_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_FAIL_TOOL_RUN = "TOOL_FAILED_RUN_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_CASE_CREATED = "TOOL_LAST_CASE_DASHBOARD_ALERT_INTERVAL_MINUTES";
    /********Dash board Screen Threshold Constants ends********/
    /********Case History Constant********/
    public static final String NOTES_ACTIVITY = "NOTES";
    public static final String QTR_CUSTOMER = "QTR";
    /********Case History Constant********/
    public static final String FLT = "FLT";
    public static final String MESSAGE_CATEGORY = "Message_Category";

    /**************ERROR MESSAGES*********/
    public static final String FIND_CASES_ERR_MSG = "FIND_CASES_ERR_MSG";


    public static final String DAO_EXCEPTION_GET_RX_TITLE = "DAO_EXCEPTION_GET_RX_TITLE";
    public static final String DAO_EXCEPTION_GET_OPEN_RXS = "DAO_EXCEPTION_GET_OPEN_RXS";
    public static final String DAO_EXCEPTION_SAVE_ATTACHMENTS = "DAO_EXCEPTION_SAVE_ATTACHMENTS";
    public static final String DAO_EXCEPTION_GET_ATTACHMENTS = "DAO_EXCEPTION_GET_ATTACHMENTS";
    public static final String DAO_EXCEPTION_REMOVE_STICKY = "DAO_EXCEPTION_REMOVE_STICKY";
    public static final String DAO_EXCEPTION_GET_OPEN_CASE_LIST = "DAO_EXCEPTION_GET_OPEN_CASE_LIST";

    public static final String NO_OF_DAYS_LOOKUP = "NO_OF_DAYS_LOOKUP";

    /********Purge Table Constants********/
    public static final String GET_SYS_PURGE_SEQ_ID = "getSysPurgeSeqId";
    public static final String PURGE_NAME = "purgeName";
    public static final String PURGE_OBJECTS = "purgeObjects";
    public static final String DAYS_TO_KEEP = "daysToKeep";
    public static final String MIN_RECORDS_TO_KEEP = "minRecordsToKeep";
    public static final String GROUP_TYPE = "groupType";
    public static final String ROW_VERSION = "rowVersion";
    public static final String LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String LAST_UPDATED_DATE = "lastUpdatedDate";
    public static final String GET_SYS_PURGE_HIST_SEQ_ID = "getSysPurgeHistSeqId";
    public static final String OBJECTS_DELETED = "objectsDeleted";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String EXCEPTION_DESC = "exceptionDesc";
    public static final String LINK_PURGE = "getSysPurge";
    /********Purge Table Constants********/
    public static final String CAPSYMBOL = "^";
    public static final String CASE_REASON = "Recommendation Delivered";
    public static final String CASE_PRIORITY = "Low";

    public static final String DB_CONNECTION_FAILURE_MESSAGE = "DB_CONNECTION_FAILURE_MESSAGE";
    public static final String EXCEPTION_GENERAL ="EXCEPTION_GENERAL";
    public static final String TOOLMDSCPERFOMANCEGREEN = "RMD_PERFORMANCE_GREEN";
    public static final String TOOLMDSCPERFOMANCEYELLOW = "RMD_PERFORMANCE_YELLOW";
    public static final String TOOLFALSEALARMYELLOW = "TOOLS_FALSE_ALARM_YELLOW";
    public static final String TOOLFALSEALARMGREEN = "TOOLS_FALSE_ALARM_GREEN";
    public static final String TOOLRXACCURACYGREEN = "TOOLS_RX_ACCURACY_GREEN";
    public static final String TOOLRXACCURACYYELLOW = "TOOLS_RX_ACCURACY_YELLOW";

    public static final String HC_REQUEST_EXIST = "HC_REQUEST_EXIST";
    public static final String DAO_EXCEPTION_INVALID_ROAD_NUMBER = "INVALID_ROAD_NUMBER";

    public static final String CASE_LOOKUP_PRIORITY = "CASE_PRIORITY";
    public static final String CASE_LOOKUP_REASON = "CASE_REASON";
    public static final String CASE_LOOKUP_STATUS = "CASE_CONDITION";
    public static final String DAO_EXCEPTION_GET_SCORES = "DAO_EXCEPTION_GET_SCORES";
    public static final String RX_SCORING_CODES = "RX_SCORING_CODES";
    public static final String RX_STATUS_OPEN_ISSUE = "RX_STATUS_OPEN_ISSUE";
    public static final String DAO_EXCEPTION_CHK_RX_STATUS = "DAO_EXCEPTION_CHK_RX_STATUS";
    public static final String RX_MORE_THAN_ONE = "RX_MORE_THAN_ONE";
    public static final String DAO_EXCEPTION_GET_CASE_STATUSLIST = "DAO_EXCEPTION_GET_CASE_STATUSLIST";
    public static final String DAO_EXCEPTION_SAVE_RUN_TRACE = "DAO_EXCEPTION_SAVE_RUN_TRACE";

    /******** Virtual Screen********/
    public static final String DAO_EXCEPTION_SAVE_VIRTUAL = "DAO_EXCEPTION_SAVE_VIRTUAL";
    public static final String DAO_EXCEPTION_SEARCH_VIRTUAL = "DAO_EXCEPTION_SEARCH_VIRTUAL";
    public static final String STR_ACCCA = "ACCCA";
    public static final String STR_TOOL_FAULT = "TOOL_FAULT";
    /******** Virtual Screen********/

    /******** Added for Validation********/
    public static final String DAO_EXCEPTION_IS_VALID_RULEID = "DAO_EXCEPTION_IS_VALID_RULEID";

    /******** Added for Validation********/

    public static final String CUSTOMER = "CUSTOMER";
    public static final String FLEET = "FLEET";
    public static final String TEMPLATE = "TEMPLATE";
    public static final String MODEL = "MODEL";

    /************Constants added for Manual JDPAD*****************/
    public static final String DS = "DS";
    public static final String CC = "CC";
    public static final String CR = "CR";
    public static final String FAULTS_FILTERED = "Faults Filtered";
    public static final String ALL_FAULTS_FILTERED = "All Faults Filtered";
    public static final String FEW_FAULTS_FILTERED = "Few Faults Filtered";
    public static final String NO_FAULTS = "No Faults/No Rule Fired";
    public static final String CASE_CREATED = "Case Created";
    public static final String NO_FILTER_RULE = "No Filter Rule Fired";
    public static final String NO_NEW_FAULTS = "No New Faults";
    public static final String NO_DIAG_RULE_FIRED = "No Diag Rule Fired";


    /************ Constants added for DropDown DataScreen:Venky************/
    static  int INDEX = 0;
    static  int COUNT = 3;
    public static final String GROUP_CUSTOMER_ID = "customerId";
    public static final String ASSET_GROUP_NAME = "strAssetGrpName";
    
    /*********** Fields added for Format Latitude and Longitude ************/
    public static final String LATITUDE = "MP_0509";
    public static final String LONGITUDE = "MP_0514";
    
    /******Constants for column name in RX GET_KM_RECOM TABLE******/
    public static final String COLUMN_MODEL = "getAsstModel.modelName";
    public static final String COLUMN_LOCO_IMPACT = "assetImpact";
    public static final String COLUMN_SUBSYSTEM = "getKmRecomSubsystems.subsystem";
    public static final String COLUMN_EXPORT_CONTROL = "exportControl";
    public static final String COLUMN_REV_HISTORY = "getKmRecomHist.revHistory";
    /******Constants for column name in RX GET_KM_RECOM TABLE******/
    
    /******Constants used in Select RX DAO impl******/
    public static final String OMD_SUBSYSTEM_LIST_NAME = "SUBSYSTEM";
    public static final String OMD_EXPORT_CONTROL_LIST_NAME = "OMD_RX_EXPORT_CONTROL";
    public static final String OMD_RX_TYPE_LIST_NAME = "OMD_RX_TYPES";
    public static final String OMD_RX_STATUS_LIST_NAME = "OMD_RX_STATUSES";
    public static final String OMD_RX_URGENCY_LIST_NAME = "OMD_URGENCY_OF_REPAIR";
    public static final String OMD_RX_ESTREPAIR_LIST_NAME = "ESTIMATED_TIME_TO_REPAIR";
    public static final String OMD_RX_LOCOIMPACT_LIST_NAME = "LOCOMOTIVE_IMPACT";
    public static final String DAO_EXCEPTION_GET_RECOM_NOTES="DAO_EXCEPTION_GET_RECOMNOTES";
    public static final String DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY="DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY";
    public static final String DAO_EXCEPTION_GET_RECOM_TITLES="DAO_EXCEPTION_GET_RECOM_TITLES";
    /******Constants used in Select RX DAO impl******/
    public static final String LOCOMOTIVE_NOTE = "Locomotive Note";
    public static final String NO_OF_DAYS = "days";
    
    //********************KPI Services************//
    public static final String KPI_RX_DELIVERED = "rxDelivered";
    public static final String KPI_RX_CLOSED_URGENCY = "rxClosedByUrgency";
    public static final String KPI_RX_CLOSED_TYPE = "rxClosedByType";

    public static final String REV_HISTORY= "revHistory";
    public static final String RX_SEARCH_DEFAULT_NOOFDAYS = "RX_SEARCH_DEFAULT_NOOFDAYS";
    
    public static final String GET_KM_RECOM_MTM_MODELS="getKmRecomMtmModels";
    public static final String RECOM_MTM_TO_ASSET_MODEL="getKmRecomMtmModels.getAsstModel";
    public static final String GET_CM_QUEUE="getCmQueue";
    public static final String GET_KM_RECOM_SUBSYSTEMS="getKmRecomSubsystems";
    public static final String GET_ASST_MODEL="getAsstModel";
    public static final String GET_KM_LINK_CURRENT_RECOM="getKmRecomByLinkCurrentRecom";
    public static final String CURRENTRECOM="currentRecom";
    public static final String LINK_ORIG_RECOM_SEQ_ID="getKmRecomByLinkOriginalRecom.getKmRecomSeqId";
    public static final String GET_KM_RECOM_ORIG_RECOM="getKmRecomByLinkOriginalRecom";
    public static final String LINK_ORIG_RECOM="LinkOriginalRecom";
    public static final String LINK_CURRENT_RECOM="LinkCurrentRecom";
    public static final String LINK_ORIGINAL_RECOM_SEQ_ID="LinkOriginalRecom.getKmRecomSeqId";
    public static final String LINK_CURRENT_RECOM_STATUS="LinkCurrentRecom.recomStatus";
    public static final String GET_KM_DPD_FIN_RUL="getKmDpdFinrul";
    public static final String LINK_FIN_RULE="linkfinRule";
    public static final String LINK_FIN_RULE_RULEDEFS="linkfinRule.getKmDpdRuledefs";
    public static final String LINK_RULE_DEF="linkRuleDef";
    public static final String LINK_RULE_DEF_LINK_ORIGRECOM="linkRuleDef.linkOriginalRecom";
    public static final String CURRENTRECOM_VERSION="currentRecom.version";
    public static final String RX_HIST_RECOM_SEQ_ID="rxHis.getKmRecomSeqId";
    public static final String RX_HISTORY="rxHis";
    public static final String GET_KM_RECOM_METRICS="getKmRecomMetrics";
    
    
    // proximity
    
    public static final String DAO_EXCEPTION_GET_LEGENDS = "DAO_EXCEPTION_GET_LEGENDS";
    public static final String DAO_EXCEPTION_CITYLIST = "DAO_EXCEPTION_CITYLIST";
    public static final String DAO_EXCEPTION_ASSET_SERVICE_STATUS = "DAO_EXCEPTION_ASSET_SERVICE_STATUS";
    public static final String PP_EXCEPTION_CUSTOMER_REGION = "PP_EXCEPTION_CUSTOMER_REGION";
    public static final String PP_EXCEPTION_NOTIFICATION_HISTORY = "PP_EXCEPTION_NOTIFICATION_HISTORY";
    public static final String PP_EXCEPTION_IDLE_SUMMARY="PP_EXCEPTION_IDLE_SUMMARY";
    public static final String PP_EXCEPTION_IDLE_DETAILS="PP_EXCEPTION_IDLE_DETAILS";
    public static final String DAO_EXCEPTION_PROXIMITY_DETAILS = "DAO_EXCEPTION_PROXIMITY_DETAILS";
    public static final String DAO_EXCEPTION_CUST_ROAD_INITIALS = "DAO_EXCEPTION_CUST_ROAD_INITIALS";
    public static final String DAO_SQL_EXCEPTION = "DAO_SQL_EXCEPTION";
    public static final String DAO_EXCEPTION = "DAO_EXCEPTION";
    public static final String DB_CONNECTION_FAIL="DB_CONNECTION_FAIL";
    
    public static final String DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER = "DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER";
    public static final String DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA = "DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA";
    public static final String ALERT_TYPE_CUSTOMER = "ALERT_TYPE_CUSTOMER";
    public static final String ALERT_TYPE_REGION = "ALERT_TYPE_REGION";
    public static final String ALERT_TYPE_SUB_DIVISION = "ALERT_TYPE_SUBDIVISION";
    public static final String ALERT_TYPE_FLEET = "ALERT_TYPE_FLEET";
    public static final String ALERT_TYPE_ASSET = "ALERT_TYPE_ASSET";
    public static final String ALERT_TYPE_SHOP = "ALERT_TYPE_SHOP";
    public static final String COMMA = ",";
    
    public static final String DAO_EXCEPTION_GET_PPHISTORY_HEADERS = "DAO_EXCEPTION_GET_PPHISTORY_HEADERS";
    public static final String DAO_EXCEPTION_GET_ASSET_HISTORY = "DAO_EXCEPTION_GET_ASSET_HISTORY";
    public static final String DAO_EXCEPTION_CHANGE_STATUS = "DAO_EXCEPTION_CHANGE_STATUS";
    public static final String DAO_EXCEPTION_GET_STATUS_VALUE = "DAO_EXCEPTION_GET_STATUS_VALUE";
    
    public static final String DAO_EXCEPTION_GET_MODEL_DETAILS = "DAO_EXCEPTION_GET_MODEL_DETAILS";
    public static final String ALERT_TYPE_MODEL = "ALERT_TYPE_MODEL";
    
    // Config Geofence Screen Murali Changes
    public static final String DAO_EXCEPTION_GET_PROXIMITY_DETAILS = "DAO EXCEPTION IN GET PROXIMITY DETAILS";
    public static final String PROXIMITY_ID = "proxId";
    public static final String UPPPER_LEFT_LAT = "upperLeftLat";
    public static final String UPPPER_LEFT_LON = "upperLeftLon";
    public static final String LOWER_RIGHT_LAT = "lowerRightLat";
    public static final String LOWER_RIGHT_LON = "lowerRightLon";
    public static final String PROX_DESC = "proxDesc";
    public static final String PROX_TYPE = "proxType";
    public static final String PROX_PARENT = "proxParent";
    public static final String ARRIVAL_FLAG = "arrivalFlag";
    public static final String DEPARTURE_FLAG = "departureFlag";
    public static final String CONSIST_FLAG = "consistFlag";
    public static final String DWEL_FLAG = "dwelFlag";
    public static final String INTERCHANGE_FLAG = "interChangeFlag";
    public static final String SMPP_PROX_ID = "smppProxId";
    public static final String PROX_OBJ_ID = "proxObjId";
    public static final String USER_ID = "userId";
    public static final String DAO_EXCEPTION_UPDATE_PROXIMITY_DETAILS = "DAO EXCEPTION IN UPDATE PROXIMITY";
    public static final String PROXIMITY_EXIST = "PROXIMITY_ALREADY_EXIST";
    public static final String BUS_ORG_ID = "busOrgId";
    public static final String DAO_EXCEPTION_ADD_PROXIMITY = "DAO EXCEPTION IN ADD PROXIMITY";
    public static final String EMPTY_STRING = "";
    public static final String DAO_EXCEPTION_DELETE_PROXIMITY = "DAO EXCEPTION IN DELETE PROXIMITY";
    public static final String DEL_PROX_OBJ_ID = "delProxObjId";
    public static final String DAO_EXCEPTION_GET_MILEPOST_DETAILS = "DAO EXCEPTION IN GET MILEPOST DETAILS";
    public static final String MILEPOST_ID = "milePostId";
    public static final String MILEPOST_DESC = "milePostDesc";
    public static final String GPS_LAT = "gpsLat";
    public static final String GPS_LON = "gpsLon";
    public static final String REGION = "region";
    public static final String SUB_REGION = "subRegion";
    public static final String STATE_PROVINCE = "stateProvince";
    public static final String MILEPOST_OBJID = "milePostObjId";
    public static final String DEL_MILEPOST_OBJ_ID = "delMPObjId";
    public static final String DAO_EXCEPTION_UPDATE_MILEPOST_DETAILS = "DAO EXCEPTION IN UPDATE MILEPOST";
    public static final String DAO_EXCEPTION_ADD_MILEPOST = "DAO EXCEPTION IN ADD MILEPOST";
    public static final String DAO_EXCEPTION_DELETE_MILEPOST = "DAO EXCEPTION IN DELETE MILEPOST";
    public static final String DAO_EXCEPTION_GET_STATE_PROVINCE = "DAO EXCEPTION IN GET STATE PROVINCE";
    public static final String DAO_EXCEPTION_GET_PROXIMITY_PARENT = "DAO EXCEPTION IN GET PROXIMITY PARENT";
    public static final int ROUND_TO_DECIMALS = 8;
    public static final String DAO_EXCEPTION_ADD_REGION_SUB_REGION = "DAO EXCEPTION IN ADD REGION SUB REGION";
    public static final String PROXIMITY_PARENT = "proxParent";
    public static final String DAO_EXCEPTION_GET_REGION = "DAO EXCEPTION IN GET REGION";
    public static final String DAO_EXCEPTION_GET_SUB_REGION = "DAO EXCEPTION IN GET SUB REGION";
    public static final String ID_EXIST = "ID EXISTS";
    public static final String VALUE_EXIST = "VALUE EXISTS";
    public static final String COMBINATION_EXISTS = "COMBINATION EXISTS";
    public static final String DAO_EXCEPTION_GET_LOOKUP_VALUE = "DAO_EXCEPTION_GET_LOOKUP_VALUE";
    
    public static final String DAO_EXCEPTION_GET_CONFIG_ALERTS = "DAO EXCEPTION IN GET CONFIG ALERTS";
    public static final String DAO_EXCEPTION_GET_MULTI_USERS = "DAO EXCEPTION IN GET MULTI USERS";
    
    public static final String DAO_EXCEPTION_GET_USER_EMAIL_ID = "DAO EXCEPTION GET USER EMAIL ID";
    public static final String DAO_EXCEPTION_PP_HISTORY_HEADERS = "DAO_EXCEPTION_PP_HISTORY_HEADERS";

    public static final String DAO_EXCEPTION_GET_BUS_ORG_ID = "DAO_EXCEPTION_GET_BUS_ORG_ID";
    
    public static final String FUEL_ERROR_CODES = "Fuel error codes";
    /**
     * private constructor
     */
    private RMDServiceConstants() {
    }
}

