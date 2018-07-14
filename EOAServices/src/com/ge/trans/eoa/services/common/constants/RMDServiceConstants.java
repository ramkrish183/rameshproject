/* * ============================================================
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
package com.ge.trans.eoa.services.common.constants;


/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Constant file for service layer
 * @History :
 * 
 ******************************************************************************/
public final class RMDServiceConstants {

	
    /** * Constant for hibernate configuration file */
    public static final String HIBERNATE_CFG_FILE = "/com/ge/trans/rmd/services/hibernate/hibernate.cfg.xml";

    public static final String PROPERTY_PATH = ".properties";
    /** * FaultCode search */
    public static final String ENTER_STAR = "*";
    /** The Constant SEARCH_BEAN_NAME. */
    public static final String SEARCH_BEAN_NAME = "RuleBOImpl";
    /**** START RULE MANAGEMENT ******/
    public static final String LOAD_FAULTS_ERROR_CODE = "LOAD_FAULTS_ERROR_CODE";
    public static final String STR_TRUE = "true";
    public static final String ERROR_USER_PREFERENCES = "ERROR_USER_PREFERENCES";
    public static final String DAO_EXCEPTION_GET_RULESEARCH = "DAO_EXCEPTION_GET_RULESEARCH";
    public static final String DAO_EXCEPTION_GET_SEARCHRULERESULT = "DAO_EXCEPTION_GET_SEARCHRULERESULT";
    public static final String ERROR_SAVE_USER_PREFERENCES = "ERROR_SAVE_USER_PREFERENCES";
    public static final String DAO_EXCEPTION_SAVE_ROLES = "DAO_EXCEPTION_SAVE_ROLES";
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
    public static final String DAO_EXCEPTION_CHECK_ROR_RULES = "DAO_EXCEPTION_CHECK_ROR_RULES";
    public static final String DAO_EXCEPTION_UPDATE_TOP_LEVEL_FINRULES = "DAO_EXCEPTION_UPDATE_TOP_LEVEL_FINRULES";
    /**** END RULE MANAGEMENT ******/
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
    public static final String DAO_EXCEPTION_GET_SUBID = "DAO_EXCEPTION_GET_SUBID";
    public static final String DAO_EXCEPTION_GET_CONTROLLERID = "DAO_EXCEPTION_GET_CONTROLLERID";
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
    /****** Start Advisory *********/
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
    /****** End Advisory *********/
    /****** Start Test Rule *********/
    public static final String DAO_EXCEPTION_SAVE_RUN_TEST = "DAO_EXCEPTION_SAVE_RUN_TEST";
    public static final String DAO_EXCEPTION_GET_ASSET_HEADER = "DAO_EXCEPTION_GET_ASSET_HEADER";
    public static final String DAO_EXCEPTION_GET_INITIAL_LOAD = "DAO_EXCEPTION_GET_INITIAL_LOAD";
    public static final String DAO_EXCEPTION_GET_RULE_ID = "DAO_EXCEPTION_GET_RULE_ID";
    public static final String DAO_EXCEPTION_GET_USER = "DAO_EXCEPTION_GET_USER";
    public static final String DAO_EXCEPTION_GET_TRACK_RULEID = "DAO_EXCEPTION_GET_TRACK_RULEID";
    public static final String DAO_EXCEPTION_GET_TRACKINGID = "DAO_EXCEPTION_GET_TRACKINGID";
    public static final String DAO_EXCEPTION_GET_SERCH_RESULT = "DAO_EXCEPTION_GET_SERCH_RESULT";
    /****** End Test Rule *********/
    public static final String DAO_EXCEPTION_GET_ASST_LIST = "DAO_EXCEPTION_GET_ASST_LIST";
    public static final String DAO_EXCEPTION_RUN_JDPAD = "DAO_EXCEPTION_RUN_JDPAD";
    public static final String DAO_EXCEPTION_GET_TRACK_ID = "DAO_EXCEPTION_GET_TRACK_ID";
    public static final String DAO_EXCEPTION_SEARCH_JDPAD_LIST = "DAO_EXCEPTION_SEARCH_JDPAD_LIST";

    /****** Start Notes *********/
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
    /****** End Notes *********/
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

    public static final String DAO_EXCEP_DEL_SAVE_DRAFT = "DAO_EXCEP_DEL_SAVE_DRAFT";
    // prod support Code change for exception
    public static final String DAO_EXCEPTION_GET_LATEST_CASES = "DAO_EXCEPTION_GET_LATEST_CASES";
    public static final String DAO_EXCEPTION_GET_DELIVERED_CASES = "DAO_EXCEPTION_GET_DELIVERED_CASES";
    public static final String DAO_EXCEPTION_GET_CLOSED_CASES = "DAO_EXCEPTION_GET_CLOSED_CASES";
    public static final String DAO_EXCEPTION_GET_RXTOTAL_COUNT = "DAO_EXCEPTION_GET_RXTOTAL_COUNT";
    public static final String DAO_EXCEPTION_GET_TIMEZONE = "DAO_EXCEPTION_GET_TIMEZONE";
    public static final String DAO_EXCEPTION_WORST_URGENCY = "DAO_EXCEPTION_WORST_URGENCY";
    public static final String DAO_EXCEPTION_API_APICOUNTS = "DAO_EXCEPTION_API_APICOUNTS";
    // Health Check EJB JNDI Name
    public static final String HEALTH_CHECK_JNDI_NAME = "ejb/RMDOMI/RequestProcessorSLSB";

    public static final String DAO_EXCEPTION_GET_RX_DETAILS = "DAO_EXCEPTION_GET_RX_DETAILS";
    public static final String DAO_EXCEPTION_GET_RX_TASK_DETAILS = "DAO_EXCEPTION_GET_RX_TASK_DETAILS";
    public static final String RX_NOT_FOUND_ERROR = "RX_NOT_FOUND_ERROR";
    public static final String DAO_EXCEPTION_SUBMIT_HC = "DAO_EXCEPTION_SUBMIT_HC";
    public static final String DAO_EXCEPTION_VIEW_HC = "DAO_EXCEPTION_VIEW_HC";

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
   
    /****** Advisory constants ******/
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
    /****** Notes constants ******/
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
    /****** Start Sticky Notes *********/
    public static final String CASE_STKY_NOTES = "Case Notes";
    public static final String ASSET_STKY_NOTES = "Asset Notes";
    /****** End Sticky Notes *********/
    /**
     * caseId field in database
     */
    public static final String CASEID = "caseId";
    public static final String GETCMCASE = "getCmCase";
    public static final String GETCMCASE_SEQ_ID = "getCmCaseSeqId";
    public static final String RXCASEID = "rxCaseId";
    public static final String GETASSTASSET = "getAsstAsset";
    public static final String ASSETNUMBER = "assetNumber";
    public static final String DEFAULT_NUMBER_OF_DAYS = "500";
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
    /***** NOTES FIELD IN DATABASE ******/
    public static final String LINK_ASSET = "getAsstAssetByLinkAsset";
    public static final String LINK_ASSET_ALIAS = "assetVO";
    public static final String STICKY = "sticky";
    /***** CLOSE CASE ******/
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
    /***** NOTES ******/
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
    public static final String CONTROLLER_ID = "controllerid";
    public static final String TITLE_RECOM = "title";
    /***** RULE TESTER ******/
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
    /***** ADVISORY ******/
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
    /***** FAULT SERVICE STGY CONSTANTS ******/
    public static final String GET_KM_FLT_STRATEGIES = "getKmFltStrategies";
    public static final String GET_KM_FLT_STRATEGIES_ALIAS = "faultStrategy";
    public static final String FLT_CLASSIFICATION = "FAULT_CLASSIFICATION";
       
     public static final String PDF_XSL_FILE_PATH = "/com/ge/trans/rmd/resources/Advisory_Report.xsl";
    public static final String CASE_PDF_XSL_FILE_PATH = "/com/ge/trans/rmd/resources/mdsc_recom_delv.xsl";
    public static final String PDF_FILE_NAME = "Recommendation_Report.pdf";
    public static final String EXCEL_FILE_NAME = "Export.xls";
    public static final String SHEET = "Sheet";
    public static final String XML_FILE_NAME = "/Advisory_Report.xml";
    public static final String FO_FILE_NAME = "/Advisory_Report.fo";
    public static final String REPORT_FILE_PATH = "REPORT_FILE_PATH";
    /*********** Rule default states ************/
    public static final Long DEFAULT_RULE_VERSION = 1L;
    public static final Long DEFAULT_RULE_COMPLETETION_STATUS = 1L;
    public static final Long INACTIVE_STATE = 0L;
    public static final Long ACTIVE_STATE = 1L;
    public static final Long UNUSED_STATE = -1L;
    public static final String DSDATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String DATE_FORMAT = "EEE MM/dd/yyyy hh:mm:ss";
    public static final String DATE_FORMAT2 = "MM/dd/yyyy hh:mm:ss";
    public static final String CAL_DATE_PATTERN = "M/d/yyyy HH:mm:ss";
    public static final String REASON_LOOK_VAL_APPEND = "Append";
    public static final String REASON_LIST_NAME = "Reason";
    /***** AddRxBOImpl ******/
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
    // new added
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
    /****** CaseBOImpl ******/
    public static final String HIGH = "High";
    public static final String JOY = "JOY";
    public static final String AUTOCASE_FOR_SPRX = "AUTOCASE_FOR_SPRX";
    public static final String PERCENTAGE = "%";
    public static final String TITLE_ALREADY_EXIST = "TitleExist";
    public static final String TITLE_ALREADY_EXIST_EXCEPTION = "TITLE_ALREADY_EXIST_EXCEPTION";
    public static final String RECOM_NOT_VALID = "RECOM_NOT_VALID";
    public static final String RECOM_TYPE_NOT_VALID = "RECOM_TYPE_NOT_VALID";
    public static final String RECOM_STATUS_NOT_VALID = "RECOM_STATUS_NOT_VALID";
    public static final String DAO_EXCEPTION_FETCH_SDP_VALUES = "DAO_EXCEPTION_FETCH_SDP_VALUES";
    public static final String RULE_TITLE = "ruleTitle";
    /****** AddRxDAoImpl ******/
    public static final String DAO_EXCEPTION_FETCHESTTIMEREPAIR = "DAO_EXCEPTION_FETCHESTTIMEREPAIR";
    public static final String DAO_EXCEPTION_FETCHURGREPAIR = "DAO_EXCEPTION_FETCHURGREPAIR";
    public static final String DAO_EXCEPTION_SAVERXDELVINFO = "DAO_EXCEPTION_SAVERXDELVINFO";
    public static final String DAO_EXCEPTION_REMOVESTICKY = "DAO_EXCEPTION_REMOVESTICKY";
    public static final String ORDERBY_DEL_DATE = "delvDate";
    /****** RecommDAoImpl ******/
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
    /****** Constants for column name in database ******/
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URGENCY = "urgency";
    public static final String COLUMN_ESTM_TIME_REPAIR = "estRepairTime";
    public static final String COLUMN_RECOMM_STATUS = "recomStatus";
    public static final String COLUMN_RECOMM_TYPE = "recomType";
    /****** Advisory History Constants ******/
    public static final String TITLE_CHANGED = "TITLE_CHANGED";
    public static final String RX_STATUS_CHANGED = "RX_STATUS_CHANGED";
    public static final String URG_REPAIR_CHANGED = "URG_REPAIR_CHANGED";
    public static final String ASSET_IMPACT_CHANGED = "ASSET_IMPACT_CHANGED";
    public static final String ESTM_TIME_REPAIR_CHANGED = "ESTM_TIME_REPAIR_CHANGED";
    public static final String REV_NUMBER_CHANGED = "REV_NUMBER_CHANGED";
    public static final String TASK_CHANGED = "TASK_CHANGED";
    public static final String RX_TYPE_CHANGED = "RX_TYPE_CHANGED";
    /****** Notes email format Constants ******/
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
    /**** Business validation *****/
    public static final String ATLEAST_ONE_RULE_DEFINITION = "ATLEAST_ONE_RULE_DEFINITION";
    public static final String ATLEAST_ONESIMPLE_RULE_NEEDED = "ATLEAST_ONESIMPLE_RULE_NEEDED";
    public static final String ALL_COMPLEXRULE_HAVE_ONESIMPLE_RULE = "ALL_COMPLEXRULE_HAVE_ONESIMPLE_RULE";
    public static final String LANGUAGE_SELECTION = "strLanguage";
    /*** Request History Page ******/
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
    /** Keep Alive Status */
    public static final String DAO_EXCEPTION_KEEP_ALIVE = "DAO_EXCEPTION_KEEP_ALIVE";
    public static final String CONTROLLER_NO_COMM = "CTRL_NO_COMM";
    public static final String TOTAL_NO_COMM = "TOTAL_NO_COMM";
    public static final String GOOD = "GOOD";
    /** Vehicle Comm Status */
    public static final String KEEP_ALIVE_DAYS = "10";
    /** Create/Find Location */
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
    /** Create/Find Contact ***/
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
    public static final String DAO_EXCEPTION_GET_LEGENDS = "DAO_EXCEPTION_GET_LEGENDS";
    public static final String BO_EXCEPTION_POPUP_ADMIN = "BO_EXCEPTION_POPUP_ADMIN";
    public static final String LIST_ALREADY_EXSIT = "LIST_ALREADY_EXSIT";
    // Role Management
    public static final String DAO_EXCEPTION_ROLE_MANAGEMENT = "DAO_EXCEPTION_ROLE_MANAGEMENT";
    public static final String BO_EXCEPTION_ROLE_MANAGEMENT = "BO_EXCEPTION_ROLE_MANAGEMENT";
    public static final String DAO_EXCEPTION_USER_MANAGEMENT = "DAO_EXCEPTION_USER_MANAGEMENT";
    public static final String DAO_EXCEPTION_FAVORITE_FITLER = "DAO_EXCEPTION_FAVORITE_FITLER";
    /**
     * Error code for duplicate user - concurrency error
     */
    public static final String USER_MANAGEMENT_CONCURENCY_ERROR_CODE = "USER_MANAGEMENT_CONCURENCY_ERROR_CODE";
    public static final String BO_EXCEPTION_USER_MANAGEMENT = "BO_EXCEPTION_USER_MANAGEMENT";

    public static final String PAGE_RESOURCE_TYPE = "page";
    // Purge
    public static final String DAO_EXCEPTION_PURGE = "DAO_EXCEPTION_PURGE";
    // For Find Case
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

    /******** Proximity Screen ********/
    public static final String EXCEPTION_PROXIMITY_LIST = "EXCEPTION_PROXIMITY_LIST";
    public static final String EXCEPTION_PROXIMITY_SAVE = "EXCEPTION_PROXIMITY_SAVE";
    public static final String DAO_EXCEPTION_GET_PROXY = "DAO_EXCEPTION_GET_PROXY";
    public static final String DAO_EXCEPTION_SAVE_PROXY = "DAO_EXCEPTION_SAVE_PROXY";
    /******** Proximity Screen ********/
    /******** Error Report Screen ********/
    public static final String REPORT_SEARCH_DAYS = "REQ_HISTORY_SEARCH_DAYS";
    public static final String ERR_REPORT_CATEGORY = "Error_Category";
    public static final String DAO_EXCEPTION_ERR_RPT_DET = "DAO_EXCEPTION_ERR_RPT_DET";
    public static final String DAO_EXCEPTION_ERR_RPT_INIT = "DAO_EXCEPTION_ERR_RPT_INIT";
    public static final String ERROR_CATEGORY_LIST_NAME = "Error_Category";
    /******** Error Report Screen ********/

    /****** Data Screen DAO Constants ******/
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
    /***** Rx Execution */
    public static final String ERROR_IN_SAVE_RX_TASKS = "ERROR_IN_SAVE_RX_TASKS";
    public static final String RX_LOCATION = "recommendations";
    /******** Locomotive Dashboard Screen starts ********/
    public static final String EXCEPTION_LOCODASH_RESULT = "EXCEPTION_LOCODASH_RESULT";
    public static final String EXCEPTION_OPEN_CASES = "EXCEPTION_OPEN_CASES";
    public static final String OK = "OK";
    public static final String MORE = "MORE";
    /******** Locomotive Dashboard Screen ends ********/
    /******** System Dashboard Screen starts ********/
    public static final String EXCEPTION_SYSDASH_RESULT = "EXCEPTION_SYSDASH_RESULT";
    public static final String RED_STATUS_STYLECLASS = "sysDashRed";
    public static final String GREEN_STATUS_STYLECLASS = "sysDashGreen";
    public static final String ERROR_RUN_STATUS = "Error";
    public static final String COMPLETE_RUN_STATUS = "Completed";
    public static final String MTS_MSG_OFFLINE_STATUS = "offline";
    public static final String TOOLS_CASE_CREATION = "TOOLS_CASECREATION";
    /******** System Dashboard Screen ends ********/
    /******** Dash board Screen Threshold Constants starts ********/
    public static final String KEEP_ALIVE = "KEEP_ALIVE_THRESHOLD";
    public static final String MTS_THRESHOLD = "MTS_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String MTS_RECEIVER = "MTS_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_MSG_RECEIVED = "OMI_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_SUCC_TOOL_RUN = "TOOL_SUCCESS_RUN_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_FAIL_TOOL_RUN = "TOOL_FAILED_RUN_DASHBOARD_ALERT_INTERVAL_MINUTES";
    public static final String LAST_CASE_CREATED = "TOOL_LAST_CASE_DASHBOARD_ALERT_INTERVAL_MINUTES";
    /******** Dash board Screen Threshold Constants ends ********/
    /******** Case History Constant ********/
    public static final String NOTES_ACTIVITY = "NOTES";
    public static final String QTR_CUSTOMER = "QTR";
    /******** Case History Constant ********/
    public static final String FLT = "FLT";
    public static final String MESSAGE_CATEGORY = "Message_Category";

    /************** ERROR MESSAGES *********/
    public static final String FIND_CASES_ERR_MSG = "FIND_CASES_ERR_MSG";

    public static final String DAO_EXCEPTION_GET_RX_TITLE = "DAO_EXCEPTION_GET_RX_TITLE";
    public static final String DAO_EXCEPTION_GET_OPEN_RXS = "DAO_EXCEPTION_GET_OPEN_RXS";
    public static final String DAO_EXCEPTION_SAVE_ATTACHMENTS = "DAO_EXCEPTION_SAVE_ATTACHMENTS";
    public static final String DAO_EXCEPTION_GET_ATTACHMENTS = "DAO_EXCEPTION_GET_ATTACHMENTS";
    public static final String DAO_EXCEPTION_REMOVE_STICKY = "DAO_EXCEPTION_REMOVE_STICKY";
    public static final String DAO_EXCEPTION_GET_OPEN_CASE_LIST = "DAO_EXCEPTION_GET_OPEN_CASE_LIST";

    public static final String NO_OF_DAYS_LOOKUP = "NO_OF_DAYS_LOOKUP";

    /******** Purge Table Constants ********/
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
    /******** Purge Table Constants ********/
    public static final String CAPSYMBOL = "^";
    public static final String CASE_REASON = "Recommendation Delivered";
    public static final String CASE_PRIORITY = "Low";

    public static final String DB_CONNECTION_FAILURE_MESSAGE = "DB_CONNECTION_FAILURE_MESSAGE";
    public static final String ERROR_LAST_DOWNLOAD_STATUS = "ERROR_LAST_DOWNLOAD_STATUS";
    public static final String ERROR_LAST_FAULT_STATUS = "ERROR_LAST_FAULT_STATUS";
    public static final String EXCEPTION_GENERAL = "EXCEPTION_GENERAL";
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
    public static final String CASE_LOOKUP_TYPE = "CASE_TYPE";
    public static final String CASE_LOOKUP_STATUS = "CASE_CONDITION";
    public static final String DAO_EXCEPTION_GET_SCORES = "DAO_EXCEPTION_GET_SCORES";
    public static final String RX_SCORING_CODES = "RX_SCORING_CODES";
    public static final String RX_STATUS_OPEN_ISSUE = "RX_STATUS_OPEN_ISSUE";
    public static final String DAO_EXCEPTION_CHK_RX_STATUS = "DAO_EXCEPTION_CHK_RX_STATUS";
    public static final String RX_MORE_THAN_ONE = "RX_MORE_THAN_ONE";
    public static final String DAO_EXCEPTION_GET_CASE_STATUSLIST = "DAO_EXCEPTION_GET_CASE_STATUSLIST";
    public static final String DAO_EXCEPTION_SAVE_RUN_TRACE = "DAO_EXCEPTION_SAVE_RUN_TRACE";

    /******** Virtual Screen ********/
    public static final String DAO_EXCEPTION_SAVE_VIRTUAL = "DAO_EXCEPTION_SAVE_VIRTUAL";
    public static final String DAO_EXCEPTION_SEARCH_VIRTUAL = "DAO_EXCEPTION_SEARCH_VIRTUAL";
    public static final String STR_ACCCA = "ACCCA";
    public static final String STR_TOOL_FAULT = "TOOL_FAULT";
    public static final String STR_TOOL_VIRTUAL = "Virtual";
    public static final String STR_TOOL_VIRTUAL_COLUMN = "virtualColumn";
    /******** Virtual Screen ********/

    /******** Added for Validation ********/
    public static final String DAO_EXCEPTION_IS_VALID_RULEID = "DAO_EXCEPTION_IS_VALID_RULEID";

    /******** Added for Validation ********/

    public static final String CUSTOMER = "CUSTOMER";
    public static final String FLEET = "FLEET";
    public static final String TEMPLATE = "TEMPLATE";
    public static final String MODEL = "MODEL";
    public static final String ROADNO = "ROADNO";

    /************ Constants added for Manual JDPAD *****************/
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

    /************ Constants added for DropDown DataScreen:Venky ************/
    public static final int INDEX = 0;
    public static int COUNT = 3;
    public static final String GROUP_CUSTOMER_ID = "customerId";
    public static final String ASSET_GROUP_NAME = "strAssetGrpName";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String NUMBER_OF_DAYS = "numberOfDays";

    /*********** Fields added for Format Latitude and Longitude ************/
    public static final String LATITUDE = "MP_0509";
    public static final String LONGITUDE = "MP_0514";

    /****** Constants for column name in RX GET_KM_RECOM TABLE ******/
    public static final String COLUMN_MODEL = "getAsstModel.modelName";
    public static final String COLUMN_LOCO_IMPACT = "assetImpact";
    public static final String COLUMN_SUBSYSTEM = "getKmRecomSubsystems.subsystem";
    public static final String COLUMN_EXPORT_CONTROL = "exportControl";
    public static final String COLUMN_REV_HISTORY = "getKmRecomHist.revHistory";
    /****** Constants for column name in RX GET_KM_RECOM TABLE ******/

    /****** Constants used in Select RX DAO impl ******/
    public static final String OMD_SUBSYSTEM_LIST_NAME = "SUBSYSTEM";
    public static final String OMD_EXPORT_CONTROL_LIST_NAME = "OMD_RX_EXPORT_CONTROL";
    public static final String OMD_RX_TYPE_LIST_NAME = "OMD_RX_TYPES";
    public static final String OMD_RX_STATUS_LIST_NAME = "OMD_RX_STATUSES";
    public static final String OMD_RX_URGENCY_LIST_NAME = "OMD_URGENCY_OF_REPAIR";
    public static final String OMD_RX_ESTREPAIR_LIST_NAME = "ESTIMATED_TIME_TO_REPAIR";
    public static final String OMD_RX_LOCOIMPACT_LIST_NAME = "LOCOMOTIVE_IMPACT";
    public static final String DAO_EXCEPTION_GET_RECOM_NOTES = "DAO_EXCEPTION_GET_RECOMNOTES";
    public static final String DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY = "DAO_EXCEPTION_GET_RECOM_LASTUPDATEDBY";
    public static final String DAO_EXCEPTION_GET_RECOM_TITLES = "DAO_EXCEPTION_GET_RECOM_TITLES";
    /****** Constants used in Select RX DAO impl ******/
    public static final String LOCOMOTIVE_NOTE = "Locomotive Note";
    public static final String NO_OF_DAYS = "days";

    // ********************KPI Services************//
    public static final String KPI_RX_DELIVERED = "rxDelivered";
    public static final String KPI_RX_CLOSED_URGENCY = "rxClosedByUrgency";
    public static final String KPI_RX_CLOSED_TYPE = "rxClosedByType";

    public static final String REV_HISTORY = "revHistory";
    public static final String RX_SEARCH_DEFAULT_NOOFDAYS = "RX_SEARCH_DEFAULT_NOOFDAYS";

    public static final String GET_KM_RECOM_MTM_MODELS = "getKmRecomMtmModels";
    public static final String RECOM_MTM_TO_ASSET_MODEL = "getKmRecomMtmModels.getAsstModel";
    public static final String GET_CM_QUEUE = "getCmQueue";
    public static final String GET_KM_RECOM_SUBSYSTEMS = "getKmRecomSubsystems";
    public static final String GET_ASST_MODEL = "getAsstModel";
    public static final String GET_KM_LINK_CURRENT_RECOM = "getKmRecomByLinkCurrentRecom";
    public static final String CURRENTRECOM = "currentRecom";
    public static final String LINK_ORIG_RECOM_SEQ_ID = "getKmRecomByLinkOriginalRecom.getKmRecomSeqId";
    public static final String GET_KM_RECOM_ORIG_RECOM = "getKmRecomByLinkOriginalRecom";
    public static final String LINK_ORIG_RECOM = "LinkOriginalRecom";
    public static final String LINK_CURRENT_RECOM = "LinkCurrentRecom";
    public static final String LINK_ORIGINAL_RECOM_SEQ_ID = "LinkOriginalRecom.getKmRecomSeqId";
    public static final String LINK_CURRENT_RECOM_STATUS = "LinkCurrentRecom.recomStatus";
    public static final String GET_KM_DPD_FIN_RUL = "getKmDpdFinrul";
    public static final String LINK_FIN_RULE = "linkfinRule";
    public static final String LINK_FIN_RULE_RULEDEFS = "linkfinRule.getKmDpdRuledefs";
    public static final String LINK_RULE_DEF = "linkRuleDef";
    public static final String LINK_RULE_DEF_LINK_ORIGRECOM = "linkRuleDef.linkOriginalRecom";
    public static final String CURRENTRECOM_VERSION = "currentRecom.version";
    public static final String RX_HIST_RECOM_SEQ_ID = "rxHis.getKmRecomSeqId";
    public static final String RX_HISTORY = "rxHis";
    public static final String GET_KM_RECOM_METRICS = "getKmRecomMetrics";
    public static final String DISABLED = "Disabled";
    public static final String DRAFT = "Draft";
    public static final String DRAFTSTATUS = "No";
    public static final String COMPLETESTATUS = "Yes";
    public static final String TESTSTATUS = "Test";
    public static final String GET_KM_RECOM_CONFIG = "getKmRecomConfigs";
    public static final String RECOM_ID = "recomId";
    public static final String RECOM_TASK_OBJID = "recomTaskObjid";
    public static final String CUSTOM = "Custom";
    public static final String RECOMMENDATION_CREATED = "Recommendation Created";
    public static final String REVISION_NUMBER_CHANGED = "Revision Number Changed";
    public static final String RECOMMENDATION_TYPE_CHANGED = "Recommendation Type Changed";
    public static final String EOA_FAMILY_LIST_NAME = "Rule Family";
    public static final String EOA_COMPLEX_FUNCTION_LIST_NAME = "jdpad_complex_fcns";

    // Added for getCustomer, getModel, getfleets, getAssets and getConfig in
    // RuleDAOImpl.java ---- Start
    public static final String FROM = " FROM ";
    public static final String AND = " AND ";
    public static final String WHERE = " WHERE ";
    public static final String COMMA = " , ";

    public static final String CUSTOMER_TABLE_NAME = " TABLE_BUS_ORG CUSTOMER ";
    public static final String FLEET_TABLE_NAME = " GETS_RMD_FLEET FLEET ";
    public static final String ASSET_TABLE_NAME = " GETS_RMD_VEHICLE ASSET ";
    public static final String MODEL_TABLE_NAME = " GETS_RMD_MODEL MODEL ";
    public static final String CTRL_CONFIG_TABLE_NAME = " GETS_RMD_CTL_CFG CFG ";
    public static final String MASTERBOM_TABLE_NAME = " GETS_RMD_MASTER_BOM MASTERBOM ";
    public static final String VEHCFG_TABLE_NAME = " GETS_RMD_VEHCFG VEHCFG ";
    public static final String SITE_PART_TABLE_NAME = "  TABLE_SITE_PART  TSP ";
    public static final String NOT_NULL_CUSTOMER_NAME = "  CUSTOMER.NAME IS NOT NULL AND ";
    public static final String CUSTOMER_TO_FLEET_JOIN = " CUSTOMER.OBJID =FLEET.FLEET2BUS_ORG ";
    public static final String ASSET_TO_FLEET_JOIN = " ASSET.VEHICLE2FLEET = FLEET.OBJID ";
    public static final String ASSET_TO_MODEL_JOIN = " ASSET.VEHICLE2MODEL = MODEL.OBJID ";
    public static final String CTRL_CONFIG_TO_MASTERBOM_JOIN = " MASTERBOM.MASTER_BOM2CTL_CFG = CFG.OBJID ";
    public static final String ASSET_TO_VEHCFG_JOIN = " ASSET.OBJID = VEHCFG.VEH_CFG2VEHICLE ";
    public static final String VEHCFG_TO_MASTERBOM_JOIN = " VEHCFG.VEHCFG2MASTER_BOM =MASTERBOM.OBJID ";
    public static final String ASSET_TO_SITE_PART_JOIN = " ASSET.VEHICLE2SITE_PART =TSP.OBJID ";

    public static final String FLEET_NUMBERS_IN = " FLEET.FLEET_NUMBER IN(:strFleet) ";
    public static final String ASSET_NUMBERS_IN = " ASSET.OBJID IN(:strUnitNumber) ";
    public static final String MODEL_NUMBERS_IN = " MODEL.MODEL_NAME IN(:strModel) ";
    public static final String CUSTOMER_NAMES_IN = " CUSTOMER.ORG_ID IN(:strCustomer) ";
    public static final String FAMILY_IN = " FAMILY IN (:strFamily) ";
    public static final String CONFIG_FAMILY_IN = " CFG.FAMILY IN (:strFamily) ";
    public static final String BOM_STATUS_Y = " MASTERBOM.BOM_STATUS='Y' ";
    public static final String STRFAMILY = "strFamily";
    public static final String ACCCA_DCCCA = "ACCCA,DCCCA";
    public static final String CUSTOMER_SELECT = " SELECT CUSTOMER.OBJID, CUSTOMER.ORG_ID, CUSTOMER.NAME ";
    public static final String MODEL_SELECT = " SELECT MODEL.OBJID, MODEL.MODEL_NAME, MODEL.FAMILY  ";
    public static final String FLEET_SELECT = " SELECT FLEET.OBJID, FLEET.FLEET_NUMBER  ";
    public static final String CONFIG_SELECT = " SELECT (SELECT OBJID FROM GETS_RMD_MASTER_BOM b where b.CONFIG_ITEM =mm.CONFIG_ITEM and rownum <= 1 ) objid , CONFIG_ITEM from (SELECT DISTINCT MASTERBOM.CONFIG_ITEM  ";
    public static final String ASSET_SELECT = "  SELECT ASSET.OBJID GET_ASST_ASSET_SEQ_ID, TSP.SERIAL_NO AS ASSET_NUMBER, CUSTOMER.ORG_ID AS CUSTOMER_ID  ";

    public static final String MOLDEL_FAMILY_NAME_EQUALS = "  MODEL.FAMILY=:family ";

    // Added for getCustomer, getModel, getfleets, getAssets and getConfig in
    // RuleDAOImpl.java ---- End

    // Added for getRuleDetails
    public static final String ANM = "ANM";
    public static final String ANOMOLY = "ANOM";
    public static final String EDP = "EDP";
    public static final String VPM = "VPM";
    public static final String VPN = "VPN";
    public static final String ENG = "ENG";
    public static final String V_ = "V_";
    public static final String STR_H = "H";
    public static final String DEACTIVE = "Deactive";
    public static final String STR_0 = "0";
    public static final String STR_1 = "1";
    public static final String AMPERSAND = "&";
    public static final String STR_S = "S";
    public static final String STR_C = "C";
    public static final String STR_F = "F";
    public static final String RMDEQUIPMENT = "RMD Equipment";
    public static final String OIL = "OIL";
    public static final String CCA = "CCA";
    public static final String QNX_EQUIPMENT = "QNX Equipment";
    public static final String HASH_SYMBOL = "#";
    public static final String SIMPLE_RULE_ID = "simRuleId";
    public static final String ANOMOLY_ID = "anomolyId";
    public static final String RULEDEF_ID = "ruleDefId";
    public static final String FAMILIES_WITH_MODELS = "Families with Models";
    public static final String STR_Y = "Y";
    public static final String STR_P = "P";
    public static final String FINAL_RULE_ID = "finRuleId";
    public static final String DEACTIVE_UPDATION_FAILURE = "DEACTIVE_UPDATION_FAILURE";
    public static final String DEACTIVE_UPDATION_SUCCESS = "DEACTIVE_UPDATION_SUCCESS";
    public static final String ZERO = "0";
    public static final String ONE = "1";
    /** ADDED CONSTANTS for EOA Rule authoring save rule ***/
    public static final String SIMRULE_OBJID = "simRuleObjid";
    public static final String FINRULE_OBJID = "finRuleObjid";
    public static final String CL_RULE_OBJID = "clRuleObjid";
    public static final String OLD_CL_RULE_OBJID = "oldClRuleObjid";
    public static final String OLD_FINRULE_OBJID = "oldFinRuleObjid";
    public static final String RULE_ORIGINAL_OBJID = "ruleOriginalObjid";
    public static final String CMPRULE_OBJID = "cmpRuleObjid";
    public static final String RULEDEF_OBJID = "ruleDefObjid";
    public static final String RULEHIS_OBJID = "rulHisObjid";
    public static final String SIMFEA_OBJID = "simfeaObjid";
    public static final String CUST_OBJID = "custObjid";
    public static final String FLEET_OBJID = "fleetObjid";
    public static final String CUST2BUS_OBJID = "cust2busorg";
    public static final String CUST2RULDEF_OBJID = "cust2ruledef";
    public static final String DIAGNOSTIC_RULE_TYPE = "Diagnostic";
    public static final String MODEL_OBJID = "ModelObjid";
    public static final String MODEL2RULEDEF_OBJID = "model2RuleDef";
    public static final String SERVICES_LIST_NAME = "JDPAD_Application";
    public static final String VALUE_1 = "value1";
    public static final String VALUE_2 = "value2";
    public static final String USERNAME = "userName";
    public static final String EXCLUDE = "exclude";
    public static final String MESSAGE = "Message";
    public static final String RULE_TYPE = "Rule_type";
    public static final String RECOM_OBJID = "recom_objid";
    public static final String SERVICE_OBJID = "service_objid";
    public static final String FAMILY = "Family";
    public static final String RULE_DESC = "ruleDescription";
    public static final String LOOKBACK = "LOOKBACK";
    public static final String FINRUL2SIMRULE = "finRule2SimRule";
    public static final String FINRUL2CMPRULE = "finRule2CmpRule";
    public static final String CL_RUL2FINRULE = "clRule2FinRule";
    public static final String CL_RUL2SIMRULE = "clRule2SimRule";
    public static final String CL_RUL2CMPRULE = "clRule2CmpRule";
    public static final String SIMFUNCTION = "simFunction";
    public static final String SIMRUL2FINRUL = "simRul2FinRul";
    public static final String COLUMN_OBJID = "colNameObjid";
    public static final String ANOM_OBJID = "anomObjid";
    public static final String TECHNIQUE = "Technique";
    public static final String EDP_PARM = "edpParm";
    public static final String SEQ_VALUE = "seqValue";
    public static final String SIM_FUNCTION = "simFCN";
    public static final String SUBSYSTEM = "SUBSYSTEM";
    public static final String TIMEFLAG_H = "H";
    public static final String TIMEFLAG_P = "P";
    public static final String TIME_WINDOW = "timeWindow";
    public static final String FREQUENCY = "frequency";
    public static final String CMPLX_FUNCTION = "complexFunction";
    public static final String RULE1SIMRUL = "rule1SimRul";
    public static final String RULE2SIMRUL = "rule2SimRul";
    public static final String RULE1CMPRUL = "rule1CmpRul";
    public static final String RULE2CMPRUL = "rule2CmpRul";
    public static final String RULE1FINRUL = "rule1FinRul";
    public static final String RULE2FINRUL = "rule2FinRul";
    public static final String SUBTIMEWINDOW = "subtimewindow";
    public static final String SUBWINDOWGOAL = "subwindowgoal";
    public static final String AC4400 = "AC4400";
    public static final String STR_CCA = "CCA";
    public static final String PREVIOUS_VERSION = "previousVersion";
    public static final String VERSION = "version";
    public static final String REVISION_HISTORY = "revision_history";
    public static final String SIMPLE_RULE_TYPE = "S";
    public static final String COMPLEX_RULE_TYPE = "C";
    public static final String OLD_RULEHIS2FINRUL = "oldRulhis2Finrul";
    public static final String RULHIS2FINRUL = "rulhis2finrul";
    public static final String NEGATIVE_NINE = "-9";
    public static final String NEGATIVE_ONE = "-1";
    public static final String POSITIVE_NINE = "9";
    public static final String PLUS_SYMBOL = "+";
    public static final String SHIFT = "SHIFT";
    public static final String FUNCTION_ID = "functionID";
    public static final String CONFIGNAME = "ConfigName";
    public static final String CFGFEA2RULDEF = "configFea2RuleDef";
    public static final String MODELMAPPEDFAMILY_LISTNAME = "Families with Models";

    public static final String MORE_THAN_ONE_SIMPLE_RULE_WITHOUT_COMPLERULE = "MORE_THAN_ONE_SIMPLE_RULE_WITHOUT_COMPLERULE";

    public static final String FAMILY_ACCCA = "ACCCA";
    public static final String FAMILY_DCCCA = "DCCCA";
    public static final String FAMILY_CCA = "CCA";
    public static final String FAMILY_AC6000 = "AC6000";
    public static final String PINPOINT = "PinPoint";
    public static final String QNXEQUIPMENT = "QNX Equipment";
    public static final String DHMS = "DHMS";
    public static final String RMDEQUIMENT = "RMD Equipment";
    public static final String LOCOCAM = "LOCOCAM";
    public static final String SMPP = "SMPP";
    public static final String RMDOIL = "OIL";
    public static final String v_ = "v_";
    public static final String CAPS_V_ = "V_";
    public static final String SDP = "SDP";
    public static final String COLUMN_PIXELS = "pixels";
    public static final String COLUMN_RULE1PIXELS = "rule1pixels";
    public static final String COLUMN_RULE2PIXELS = "rule2pixels";
    public static final String ORDER_BY_DESC = "ORDER BY 1 DESC";
    public static final String ORDER_BY_MODEL_NAME_ASC = " ORDER BY MODEL_NAME ASC";
    public static final String ORDER_BY_CONFIG_ITEMS_ASC = " ORDER BY CONFIG_ITEM  ASC";
    public static final String ORDER_BY_ORG_ID_ASC = " ORDER BY ORG_ID ASC";
    public static final String RULETYPE = "RULETYPE";
    public static final String RULETITLE = "RULETITLE";
    public static final String RULEID = "RULEID";
    public static final String ISACTIVE = "ISACTIVE";
    public static final String FAULTCODE = "FAULTCODE";
    public static final String CREATEDBY = "CREATEDBY";
    public static final String LastUpdateBy = "LastUpdateBy";
    public static final String RXTitle = "rxTitle";
    public static final String LastUpdatedFromDate = "lastUpdatedFromDate";
    public static final String LastUpdateToDate = "lastUpdatedToDate";
    public static final String createdFromDate = "createdFromDate";
    public static final String createdToDate = "createdToDate";
    public static final String MULTIFAULT_SEQ_ID = "mtmMultiFaultId";
    // Added for DataScreen changes for Diesel Doctor
    public static final String DATA_SCREEN_NO_PARAMS = "DATA_SCREEN_NO_PARAMS";
    public static final String DATA_SCREEN_ERROR = "DATA_SCREEN_ERROR";
    public static final String DATA_SCREEN_NOT_SUPPORTED = "DATA_SCREEN_NOT_SUPPORTED";
    public static final String MULTIFAULT_PIXELS = "multiFaultPixels";

    public static final String PERF_CHECK_OBJID = "perfCheckObjid";
    public static final String HEALTH_FACTOR = "healthFactor";
    public static final String PERF_CHECK_ID = "perfCheckID";
    public static final String HEALTH_RULE_TYPE = "Health";

    /***************************************************************************
     * @Description : This class is to hold commonly used native SQL queries and
     *              its constant variables
     * 
     **************************************************************************/
    public static class QueryConstants {
        /** NATIVE SQL for the save Rule ***/
        public static final StringBuilder INSERT_DPD_CUST_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_CUST(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,CUST2BUSORG,CUST2RULEDEF,EXCLUDE) ")
                .append(" VALUES(:custObjid,SYSDATE,:userName,SYSDATE,:userName,:cust2busorg,:cust2ruledef,:exclude)");
        public static final StringBuilder INSERT_DPD_FLEET_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_FLEET(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,FLEET2FLEET,FLEET2CUST,EXCLUDE)")
                .append(" VALUES(GETS_TOOL_DPD_FLEET_SEQ.nextval,SYSDATE,:userName,SYSDATE,:userName,:fleetObjid,:custObjid,:exclude)");
        // Added Notes column by Amuthan
        public static final StringBuilder INSERT_DPD_SIMRUL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_SIMRUL(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,FAULT,SUB_ID,SIMRUL2FINRUL,PIXELS,CONTROLLER_ID,NOTES) ")
                .append(" VALUES(:simRuleObjid,SYSDATE,:userName,SYSDATE,:userName,:faultCode,:subId,:simRul2FinRul,:pixels,:controllerid,:notes) ");
        public static final StringBuilder INSERT_DPD_SIMFEA_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_SIMFEA(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY, VALUE1,VALUE2,SEQ_VALUE,SIMFEA2SIMRUL,SIMFEA2COLNAME,SIMFEA2SIMFCN,EDP_PARMDEF,SIMFEA2ANOM_ADMIN,ANOM_TECHNIQUE,PIXELS,NOTES)")
                .append(" values (GETS_TOOL_DPD_SIMFEA_seq.nextval,sysdate,:userName,sysdate,:userName,:value1,:value2,:seqValue,:simRuleObjid,:colNameObjid,:simFCN,:edpParm,:anomObjid,:Technique,:pixels,:notes) ");
        public static final StringBuilder INSERT_DPD_CMPRUL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_CMPRUL(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,TIME_WINDOW,FREQUENCY, COMPLEX_FUNCTION,RULE1_2SIMRUL,RULE1_2CMPRUL,RULE2_2SIMRUL,RULE2_2CMPRUL,CMPRUL2FINRUL,RULE1_2FINRUL,RULE2_2FINRUL,PIXELS,RULE1_PIXELS,RULE2_PIXELS,SUB_TIME_WINDOW,SUB_WINDOW_GOAL,NOTES) ")
                .append("VALUES(:cmpRuleObjid,SYSDATE,:userName,SYSDATE,:userName,:timeWindow,:frequency,:complexFunction,:rule1SimRul, :rule1CmpRul,:rule2SimRul,:rule2CmpRul,:finRuleObjid,:rule1FinRul,:rule2FinRul,:pixels,:rule1pixels,:rule2pixels,:subtimewindow,:subwindowgoal,:notes)");
        // Ended Notes column by Amuthan
        public static final StringBuilder INSERT_DPD_FINRUL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_FINRUL(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,FAMILY,SUBSYSTEM,LOOKBACK,FINRUL2SIMRUL,FINRUL2CMPRUL,RULE_DESCRIPTION,RULE_TITLE,PIXELS)")
                .append("  VALUES(:finRuleObjid,SYSDATE,:userName,SYSDATE,:userName,:Family,:SUBSYSTEM,:LOOKBACK,:finRule2SimRule,:finRule2CmpRule,:ruleDescription,:ruleTitle,:pixels)");
        public static final StringBuilder INSERT_DPD_CL_RUL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_CL_RUL(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,CL_RUL2FINRUL,CL_RUL2SIMRUL,CL_RUL2CMPRUL,PIXELS)")
                .append("  VALUES(:clRuleObjid,SYSDATE,:userName,SYSDATE,:userName,:clRule2FinRule,:clRule2SimRule,:clRule2CmpRule,:pixels)");
        public static final StringBuilder INSERT_DPD_RULHIS_BUILDER = new StringBuilder()
                .append(" INSERT INTO GETS_TOOL_DPD_RULHIS(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,VERSION_NO,ACTIVE,PREVIOUS_VERSION_ID,COMPLETED,RULHIS2FINRUL,ORIGINAL_ID ,REV_HISTORY)")
                .append(" VALUES(:rulHisObjid,SYSDATE,:userName,SYSDATE,:userName,:version,:Active,:previousVersion, :COMPLETE,:rulhis2finrul, :originalId, :revision_history)");

        public static final StringBuilder INSERT_DPD_RULDEF_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_RULEDEF (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,MESSAGE,RULE_TYPE,RULEDEF2FINRUL,RULEDEF2RECOM,RULEDEF2APP) ")
                .append(" VALUES(:ruleDefObjid,SYSDATE,:userName,SYSDATE,:userName,:Message,:Rule_type,:finRuleObjid,:recom_objid,:service_objid)");
        public static final StringBuilder INSERT_DPD_MODEL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_MODEL (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,MODEL2MODEL,MODEL2RULEDEF)")
                .append(" VALUES(GETS_TOOLS.GETS_TOOL_DPD_MODEL_SEQ.nextval,sysdate,:userName,sysdate,:userName,:ModelObjid,:model2RuleDef)");

        public static final StringBuilder INSERT_DPD_CFGFEA_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOL_DPD_CFGFEA(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,VALUE1,VALUE2,CONFIG_ITEM,CFGFEA2SIMFCN,CFGFEA2RULDEF) ")
                .append("  VALUES(GETS_TOOL_DPD_CFGFEA_SEQ.NEXTVAL,SYSDATE,:userName,SYSDATE,:userName,:value1,:value2,:ConfigName,:functionID,:configFea2RuleDef)");

        public static final StringBuilder UPDATE_DPD_RULHIS_BUILDER = new StringBuilder()
                .append(" UPDATE GETS_TOOL_DPD_RULHIS SET OBJID=:rulHisObjid,LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY=:userName,CREATION_DATE=SYSDATE,CREATED_BY=:userName,PREVIOUS_VERSION_ID =:previousVersion, VERSION_NO=:version, COMPLETED= :COMPLETE, RULHIS2FINRUL=:rulhis2finrul,REV_HISTORY= :revision_history,ORIGINAL_ID= :originalId ")
                .append(" WHERE RULHIS2FINRUL=:oldRulhis2Finrul");

        public static final StringBuilder FETCH_SUB_ID_FOR_CCA_FAMILY = new StringBuilder()
                .append(" SELECT DISTINCT CODE.SUB_ID FROM ")
                .append(" (SELECT DISTINCT SUB_ID FROM GETS_RMD.GETS_RMD_FAULT_CODES WHERE CONTROLLER_SOURCE_ID = 20 UNION ")
                .append(" SELECT DISTINCT SUB_ID  FROM GETS_RMD.GETS_RMD_FAULT_CODES WHERE CONTROLLER_SOURCE_ID = 21) INNER1,  ")
                .append("  GETS_RMD_FAULT_CODES CODE  ")
                .append(" WHERE INNER1.SUB_ID = CODE.SUB_ID ");
        public static final StringBuilder FETCH_SUB_ID_DATA = new StringBuilder()
                .append(" SELECT  DISTINCT SUB_ID FROM GETS_RMD.GETS_RMD_FAULT_CODES FC,GETS_RMD.GETS_RMD_CONTROLLER RC, ")
                .append(" GETS_RMD.GETS_RMD_CTL_CFG CC,GETS_RMD.GETS_RMD_CTL_CFG_SRC CCS ")
                .append(" WHERE FC.CONTROLLER_SOURCE_ID = RC.CONTROLLER_SOURCE_ID AND ")
                .append("  RC.CONTROLLER_SOURCE_ID = CCS.CONTROLLER_SOURCE_ID AND   ")
                .append(" CCS.CTL_CFG_SRC2CTL_CFG = CC.OBJID  ");
        public static final StringBuilder FETCH_ALL_CONTROLLER_ID_DATA = new StringBuilder()
                .append(" SELECT CONTROLLER_SOURCE_ID, CONTROLLER_NAME FROM GETS_RMD.GETS_RMD_CONTROLLER");
        public static final StringBuilder RULE_RECORDCOUNT = new StringBuilder()
                .append("SELECT COUNT(RULHIS.OBJID) ")
                .append("FROM GETS_TOOL_DPD_FINRUL FR,GETS_TOOL_DPD_RULHIS RULHIS ")
                .append("WHERE   RULHIS.RULHIS2FINRUL = FR.OBJID ")
                .append(" AND RULHIS.ORIGINAL_ID=:originalId ");
        public static final String SELECT_SIMFEA_SEQ = "SELECT GETS_TOOL_DPD_SIMFEA_seq.nextval from dual";
        public static final String FLEET_SQL_SQL = "SELECT GETS_TOOL_DPD_FLEET_SEQ.NEXTVAL FROM DUAL";
        public static final String CUSTOMER_SEQ_SQL = "SELECT GETS_TOOLS.GETS_TOOL_DPD_CUST_SEQ.NEXTVAL FROM DUAL";
        public static final String SIMFCN_SQL = "SELECT OBJID FROM GETS_TOOL_DPD_SIMFCN WHERE FCN = :simFunction";
        public static final String RULEDEF_SEQ_SQL = "SELECT GETS_TOOL_DPD_RULEDEF_SEQ.NEXTVAL FROM dual ";
        public static final String RECOM_OBJID_SQL = "SELECT OBJID FROM GETS_SD_RECOM WHERE TITLE=:Title";
        public static final String RULEHIS_SEQ_SQL = " SELECT GETS_TOOL_DPD_RULHIS_SEQ.NEXTVAL  FROM dual";
        public static final String SIMRUL_SEQ_SQL = "SELECT GETS_TOOL_DPD_SIMRUL_SEQ.NEXTVAL  FROM dual ";
        public static final String FINRUL_SEQ_SQL = "SELECT GETS_TOOL_DPD_FINRUL_SEQ.nextval FROM dual  ";
        public static final String SELECT_RULHIS_SQL = "SELECT PREVIOUS_VERSION_ID,VERSION_NO,COMPLETED,ORIGINAL_ID FROM GETS_TOOL_DPD_RULHIS WHERE RULHIS2FINRUL=:rulHisObjid ORDER BY OBJID DESC";
        public static final String TOTAL_MODELS_SQL = "SELECT COUNT(1) FROM GETS_RMD_MODEL";
        public static final String ACCCA_DCCCA_MODELS_SQL = "SELECT COUNT(1) FROM GETS_RMD_MODEL WHERE FAMILY IN ('ACCCA', 'DCCCA')";
        
        public static final StringBuilder SELECT_FINAL_ACTIVE_RULES_SQL = new StringBuilder()
                .append("SELECT FINRUL.OBJID,FINRUL.RULE_TITLE FROM GETS_TOOL_DPD_FINRUL FINRUL,GETS_TOOL_DPD_RULHIS RULHIS,GETS_TOOL_DPD_RULEDEF RULEDEF ")
                .append(" WHERE FINRUL.OBJID=RULHIS.RULHIS2FINRUL AND RULHIS.ACTIVE =1 AND FINRUL.FAMILY =:Family AND FINRUL.OBJID <> :finRuleObjid AND RULEDEF.RULEDEF2FINRUL=FINRUL.OBJID AND RULEDEF.RULE_TYPE =:Rule_type ")
                .append(" GROUP BY RULEDEF.RULEDEF2FINRUL, FINRUL.OBJID, FINRUL.RULE_TITLE HAVING COUNT(DISTINCT RULEDEF.RULEDEF2FINRUL) = 1");
        public static final String GET_CMPRULE_RULES_SQL = "SELECT FINRUL2CMPRUL FROM GETS_TOOL_DPD_FINRUL WHERE OBJID =:finRuleObjid ";
        public static final StringBuilder CHECK_ROR_RULE_SQL = new StringBuilder()
                .append("SELECT RULE1_2FINRUL,RULE2_2FINRUL FROM  GETS_TOOL_DPD_CMPRUL CONNECT BY PRIOR RULE1_2CMPRUL = OBJID OR PRIOR RULE2_2CMPRUL = OBJID START WITH OBJID = :cmpRuleObjid UNION ")
                .append("SELECT RULE1_2FINRUL,RULE2_2FINRUL FROM  GETS_TOOL_DPD_CMPRUL CONNECT BY PRIOR RULE2_2CMPRUL = OBJID OR PRIOR RULE1_2CMPRUL = OBJID START WITH OBJID = :cmpRuleObjid ");

        public static final StringBuilder FETCH_SDP_PARAMS = new StringBuilder()
                .append("  SELECT DISTINCT CLMNAM.OBJID,CLMNAM.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,CLMNAM.PARM_TYPE,max(SOURCE_UOM_ID) ")
                .append("  FROM GETS_TOOL_DPD_COLNAME CLMNAM,GETS_RMD_PARMDEF PARMDEF ")
                .append("  WHERE  PARMDEF.RULE_PARM_DESC(+)=CLMNAM.COLUMN_NAME AND LOWER(CLMNAM.FAMILY)=LOWER( :FAMILY) AND CLMNAM.PARM_TYPE IN ('MP','EDP') ")
                .append("  group by CLMNAM.OBJID,CLMNAM.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME),CLMNAM.PARM_TYPE ")
                .append("  UNION ")
                .append("  SELECT DISTINCT CLMNAM.OBJID,  CLMNAM.COLUMN_NAME,DB_COLUMN_NAME paramDBName,CLMNAM.PARM_TYPE,null ")
                .append("  FROM GETS_TOOL_DPD_COLNAME CLMNAM ")
                .append("  WHERE LOWER(CLMNAM.FAMILY)     = LOWER(:FAMILY) AND CLMNAM.PARM_TYPE IN ('Standard') ORDER BY COLUMN_NAME");

        public static final StringBuilder FETCH_SDP_VALUES = new StringBuilder()
                .append("  SELECT CLMNAM.OBJID,CLMNAM.COLUMN_NAME,CLMVAL.VAL")
                .append("  FROM GETS_TOOL_DPD_COLVAL CLMVAL,  GETS_TOOL_DPD_COLNAME CLMNAM ")
                .append("  WHERE CLMVAL.COLVAL2COLNAME=CLMNAM.OBJID ");
        // Added Notes column by Amuthan
        public static final StringBuilder INSERT_SIMMTM_MULTIFAULT_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_DPD_SIM_MTM_FAULT(GETS_TOOL_DPD_SIM_MTM_FAULT_ID,FAULT2SIMRUL,FAULT_CODE,MULTIFAULT_PIXELS,SUB_ID,LAST_UPDATED_TSTAMP,LAST_UPDATED_BY,CREATION_TSTAMP,CREATED_BY,CONTROLLER_ID,NOTES) ")
                .append(" VALUES(:mtmMultiFaultId,:simRuleObjid,:faultCode,:multiFaultPixels,:subId,SYSTIMESTAMP,:userName,SYSTIMESTAMP,:userName,:controllerid,:notes) ");
        // Ended Notes column by Amuthan
        public static final String SELECT_MULTIFAULT_SEQ = "SELECT GETS_TOOLS.GETS_TOOL_DPD_SIM_MTM_FLT_SEQ.nextval from dual";
        public static final StringBuilder SELECT_MULTIFAULT_DETAILS = new StringBuilder()
                .append("SELECT DISTINCT MUL.FAULT_CODE,  MULTIFAULT_PIXELS,  (SELECT FAULT_DESC  FROM GETS_RMD_FAULT_CODES FAULTCODE  WHERE FAULTCODE.FAULT_CODE=MUL.FAULT_CODE AND ROWNUM  < 2  ) FAULT_DESC, GETS_TOOL_DPD_SIM_MTM_FAULT_ID,  MUL.SUB_ID, MUL.CONTROLLER_ID,MUL.NOTES  ")
                .append("FROM GETS_TOOLS.GETS_TOOL_DPD_SIM_MTM_FAULT MUL WHERE FAULT2SIMRUL = :simRuleObjid  ORDER BY GETS_TOOL_DPD_SIM_MTM_FAULT_ID ASC");
        /** Start Query for virtuals */
        public static final StringBuilder FETCH_VIRTUALS_ID = new StringBuilder()
                .append("SELECT DISTINCT GETS_TOOL_VIRTUAL_SEQ_ID FROM GETS_TOOLS.GETS_TOOL_VIRTUAL ")
                .append("WHERE GETS_TOOL_VIRTUAL_SEQ_ID LIKE (:virtualId)");
        public static final StringBuilder FETCH_VIRTUALS_CREATED_BY = new StringBuilder()
                .append("SELECT DISTINCT CREATED_BY FROM GETS_TOOLS.GETS_TOOL_VIRTUAL  ");
        public static final StringBuilder FETCH_VIRTUALS_LASTUPDATEDBY = new StringBuilder()
                .append("SELECT DISTINCT LAST_UPDATED_BY FROM GETS_TOOLS.GETS_TOOL_VIRTUAL  ");
        public static final StringBuilder FETCH_VIRTUALS_FAMILY = new StringBuilder()
                .append("SELECT DISTINCT FAMILY_CD FROM GETS_TOOLS.GETS_TOOL_VIRTUAL  ");
        public static final StringBuilder FETCH_VIRTUALS_TITLES = new StringBuilder()
                .append("SELECT DISTINCT DPDCOLNAME.COLUMN_NAME FROM  GETS_TOOLS.GETS_TOOL_VIRTUAL VIRTUAL, GETS_TOOLS.GETS_TOOL_DPD_COLNAME DPDCOLNAME ")
                .append("WHERE VIRTUAL.LINK_DPD_COLUMN_NAME=DPDCOLNAME.OBJID AND DPDCOLNAME.COLUMN_NAME LIKE (:columnName)");


        public static final StringBuilder SEARCH_VIRTUALS = new StringBuilder()
                .append(" SELECT * FROM (SELECT DISTINCT VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID,COLNAME.COLUMN_NAME, VIRTUAL.LAST_UPDATED_BY,TO_CHAR(VIRTUAL.LAST_UPDATED_DATE,'YYYY-MM-DD HH24:MI:SS') LAST_UPDATED_DATE, ")
                .append(" VIRTUAL.CREATED_BY,TO_CHAR(VIRTUAL.CREATION_DATE,'YYYY-MM-DD HH24:MI:SS'),VIRTUAL.FAMILY_CD, ")
                .append(" NVL(( SELECT DISTINCT RULHIST.ACTIVE  FROM GETS_TOOLS.GETS_TOOL_DPD_SIMFEA SIMFEA, GETS_TOOLS.GETS_TOOL_DPD_SIMRUL SIMRUL,   GETS_TOOLS.GETS_TOOL_DPD_RULHIS RULHIST ")
                .append(" WHERE COLNAME.OBJID = SIMFEA.SIMFEA2COLNAME AND SIMFEA.SIMFEA2SIMRUL = SIMRUL.OBJID AND SIMRUL.SIMRUL2FINRUL =RULHIST.RULHIS2FINRUL AND RULHIST.ACTIVE =1 AND RULHIST.COMPLETED  =1 ),0) status ")
                .append("FROM    GETS_TOOLS.GETS_TOOL_VIRTUAL VIRTUAL, GETS_TOOLS.GETS_TOOL_EQUATION EQUATION,GETS_TOOLS.GETS_TOOL_VIRTUAL_EQUATION VIRTUALEQUATION,  GETS_TOOLS.GETS_TOOL_DPD_COLNAME COLNAME  ")
                .append("WHERE  VIRTUALEQUATION.GETS_TOOL_EQUATION_SEQ_ID=EQUATION.GETS_TOOL_EQUATION_SEQ_ID AND  ")
                .append("VIRTUALEQUATION.GETS_TOOL_VIRTUAL_SEQ_ID=VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID AND VIRTUAL.LINK_DPD_COLUMN_NAME  =COLNAME.OBJID ");
        public static final StringBuilder FIND_VIRTUAL_STATUS_QUERY = new StringBuilder()
                .append("SELECT RULHIST.ACTIVE,RULHIST.COMPLETED  ")
                .append("FROM GETS_TOOLS.GETS_TOOL_DPD_SIMFEA SIMFEA,GETS_TOOLS.GETS_TOOL_DPD_COLNAME COLNAME, ")
                .append("GETS_TOOLS.GETS_TOOL_DPD_SIMRUL SIMRUL, GETS_TOOLS.GETS_TOOL_DPD_RULHIS RULHIST ")
                .append("WHERE ")
                .append("COLNAME.OBJID= SIMFEA.SIMFEA2COLNAME AND SIMFEA.SIMFEA2SIMRUL  = SIMRUL.OBJID AND ")
                .append("SIMRUL.SIMRUL2FINRUL   =RULHIST.RULHIS2FINRUL ");

        public static final StringBuilder FETCH_VIRTUAL_DETAILS = new StringBuilder()
                .append(" SELECT VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID, COLNAME.COLUMN_NAME,EQUATION.GETS_TOOL_EQUATION_SEQ_ID, EQUATION.EQUATION_TXT,")
                .append(" EQUATION.CUSTOM_PROCESS_FILE_NAME, EQUATION.EQUATION_TYPE_CD,VIRTUAL.FAMILY_CD,VIRTUAL.ACTIVE_FLG,")
                .append(" VIRTUAL.EQUATION_DESC_TXT,EQUATION.LOOK_BACK_FAULT_CNT,EQUATION.LOOK_BACK_TIME,VIRTUAL.LAST_UPDATED_BY, ")
                .append(" TO_CHAR(VIRTUAL.LAST_UPDATED_DATE,'YYYY-MM-DD HH:MI:SS'),VIRTUAL.CREATED_BY,TO_CHAR(VIRTUAL.CREATION_DATE,'YYYY-MM-DD HH:MI:SS'),  ")
                .append(" EQUATION.EQUATION_DESC_TXT, EQUATION.EQUATION_JSON_TXT ")
                .append(" FROM  GETS_TOOLS.GETS_TOOL_VIRTUAL VIRTUAL, GETS_TOOLS.GETS_TOOL_EQUATION EQUATION,GETS_TOOLS.GETS_TOOL_VIRTUAL_EQUATION VIRTUALEQUATION, GETS_TOOLS.GETS_TOOL_DPD_COLNAME COLNAME")
                .append(" WHERE VIRTUALEQUATION.GETS_TOOL_EQUATION_SEQ_ID=EQUATION.GETS_TOOL_EQUATION_SEQ_ID")
                .append(" AND VIRTUALEQUATION.GETS_TOOL_VIRTUAL_SEQ_ID=VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID  ")
                .append(" AND VIRTUAL.LINK_DPD_COLUMN_NAME = COLNAME.OBJID AND VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID = (:virtualId)")
                .append(" ORDER BY  VIRTUAL.FAMILY_CD ASC");

        
        public static final StringBuilder FETCH_METRIC_DETAILS = new StringBuilder()
                .append(" (SELECT RD.RECOMM_TITLE RXTITLE, ROUND((SUM(DECODE(RX_SUCCESS,'Save',DECODE(METRIC_REISSUE,'N',1,0),0))) / DECODE((SUM(DECODE(MISS_CODE,'4A',1,0))+SUM(DECODE(RX_SUCCESS,'Save',1,0))),0,0.0001,(SUM(DECODE(MISS_CODE,'4A',1,0))+SUM(DECODE(RX_SUCCESS,'Save',1,0)))),3) accuracyPercent,")
                .append(" ROUND(SUM(DECODE(METRIC_REISSUE,'Y',1,0))                                     /DECODE((COUNT(1)-SUM(DECODE(MISS_CODE,'8',1,0))-SUM(DECODE(MISS_CODE,'4C',1,0))-SUM(DECODE(MISS_CODE,'4F',1,0))),0,0.0001,COUNT(1)-SUM(DECODE(MISS_CODE,'8',1,0))-SUM(DECODE(MISS_CODE,'4C',1,0))-SUM(DECODE(MISS_CODE,'4F',1,0))),3) ReIssuePercent,")
                .append(" ROUND(SUM(DECODE(MISS_CODE,'4B',1,0))                                         /DECODE(COUNT(1)-(SUM(DECODE(MISS_CODE,'8',1,0))-SUM(DECODE(MISS_CODE,'4C',1,0))-SUM(DECODE(MISS_CODE,'4F',1,0))),0,0.0001,COUNT(1)-(SUM(DECODE(MISS_CODE,'8',1,0))-SUM(DECODE(MISS_CODE,'4C',1,0))-SUM(DECODE(MISS_CODE,'4F',1,0)))),3) NTFPercent ")
                .append(" FROM rmd_metrics.rmd_mtx_rx_effective_fact@RMD_EOA_DW.WORLD rmdmetrics,")
                .append(" GETS_DW_EOA_RECOM_DEL@RMD_EOA_DW.WORLD rd")
                .append(" WHERE rmdmetrics.rx_delv_objid = rd.recomm_del_objid")
                .append(" AND rd.delivery_date           > sysdate - 180")
                .append(" GROUP BY RD.RECOMM_TITLE ").append(" ) MET ");
        public static final StringBuilder INSERT_DPD_COLNAME_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_DPD_COLNAME(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,FAMILY,COLUMN_NAME,DB_COLUMN_NAME,TABLE_NAME,TABLE_NAME_DISP,PARM_TYPE)")
                .append(" VALUES (:colNameObjid,SYSDATE,:lastUpdatedBy,SYSDATE,:createdBy,:Family,:columnName,:dbColumnName,:tableName,:tableNameDisp,:parmType)");
        public static final StringBuilder INSERT_TOOLS_VIRTUAL_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_VIRTUAL(GETS_TOOL_VIRTUAL_SEQ_ID,LINK_DPD_COLUMN_NAME,EQUATION_DESC_TXT,FAMILY_CD,ACTIVE_FLG,ROW_VERSION,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY, VIRTUAL_COLUMN_NAME) ")
                .append("VALUES (:virtualObjid,:colNameObjid,:equationDescTxt,:Family,:activeFlag,:rowVersion,SYSDATE,:lastUpdatedBy,SYSDATE,:createdBy,:virtualColumn)");
        public static final StringBuilder INSERT_TOOLS_EQUATION_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_EQUATION(GETS_TOOL_EQUATION_SEQ_ID,EQUATION_TXT,EQUATION_DESC_TXT,CUSTOM_PROCESS_FILE_NAME,LOOK_BACK_TIME,LOOK_BACK_FAULT_CNT,EQUATION_TYPE_CD,EQUATION_JSON_TXT,PARENT_EQUATION_SEQ_ID,ROW_VERSION,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY) ")
                .append("VALUES (:equationObjid,:equationTxt,:equationDescTxt,:customProcessFile,:lookBackTime,:lookBackFaultCount,:equationType,:equationJSONTxt,:parentEquationSeqId,:rowVersion,SYSDATE,:lastUpdatedBy,SYSDATE,:createdBy)");
        public static final StringBuilder INSERT_TOOLS_VIRTUAL_EQUATION_BUILDER = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_VIRTUAL_EQUATION (GETS_TOOL_EQUATION_SEQ_ID,GETS_TOOL_VIRTUAL_SEQ_ID) ")
                .append("VALUES (:equationObjid,:virtualObjid)");
        public static final String SELECT_DPD_COLNAME_SEQ = "SELECT GETS_TOOLS.GETS_TOOL_DPD_COLNAME_SEQ.NEXTVAL from dual";
        public static final String SELECT_VIRTUAL_SEQ = "SELECT GETS_TOOLS.GETS_TOOL_VIRTUAL_SEQ.NEXTVAL from dual";
        public static final String SELECT_EQUATION_SEQ = "SELECT GETS_TOOLS.GETS_TOOL_EQUATION_SEQ.NEXTVAL from dual";

        public static final StringBuilder UPDATE_TOOLS_VIRTUAL_BUILDER = new StringBuilder()
                .append("UPDATE GETS_TOOLS.GETS_TOOL_VIRTUAL SET EQUATION_DESC_TXT=:equationDescTxt,ACTIVE_FLG=:activeFlag,ROW_VERSION=:rowVersion,LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY=:lastUpdatedBy  ")
                .append(" WHERE GETS_TOOL_VIRTUAL_SEQ_ID = :virtualObjid ");

        public static final StringBuilder UPDATE_TOOLS_EQUATION_BUILDER = new StringBuilder()
                .append("UPDATE GETS_TOOLS.GETS_TOOL_EQUATION SET EQUATION_TXT=:equationTxt,EQUATION_DESC_TXT=:equationDescTxt,CUSTOM_PROCESS_FILE_NAME=:customProcessFile,LOOK_BACK_TIME=:lookBackTime,LOOK_BACK_FAULT_CNT=:lookBackFaultCount,EQUATION_TYPE_CD=:equationType, ")
                .append(" EQUATION_JSON_TXT=:equationJSONTxt,PARENT_EQUATION_SEQ_ID=:parentEquationSeqId,ROW_VERSION=:rowVersion,LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY=:lastUpdatedBy  ")
                .append(" WHERE GETS_TOOL_EQUATION_SEQ_ID = :equationObjid ");

        public static final StringBuilder SELECT_VIRTUAL_COLUMN_NAME = new StringBuilder()
                .append("select column_name from all_tab_columns where owner = 'GETS_TOOLS' and table_name='GETS_TOOL_VIRTUAL_OUT' and column_name not in ('FAULT_OBJID','TOOL_RUN_OBJID')")
                .append(" and column_name not in (select 'V'|| to_number(substr(virtual_column_name,2,length(virtual_column_name)-4)) || '_ID' from gets_tools.gets_tool_virtual) order by column_id");

        /** End Query for virtual's */

        public static final StringBuilder FETCH_VIRTUAL_PARAMS = new StringBuilder()
                .append("SELECT COLNAME.OBJID,COLNAME.COLUMN_NAME,VIRTUAL.GETS_TOOL_VIRTUAL_SEQ_ID	")
                .append("FROM GETS_TOOLS.GETS_TOOL_VIRTUAL VIRTUAL, GETS_TOOL_DPD_COLNAME COLNAME	")
                .append("WHERE COLNAME.OBJID=VIRTUAL.LINK_DPD_COLUMN_NAME	");

        public static final StringBuilder FETCH_PARAMETERS = new StringBuilder()
                .append("SELECT DISTINCT COLNAME.objid,COLNAME.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,COLNAME.PARM_TYPE,max(SOURCE_UOM_ID) ")
                .append("FROM GETS_TOOL_DPD_COLNAME COLNAME,GETS_RMD_PARMDEF PARMDEF	")
                .append("WHERE PARMDEF.RULE_PARM_DESC=COLNAME.COLUMN_NAME AND COLNAME.PARM_TYPE        = 'MP' ");
        public static final StringBuilder FETCH_SDP_PARMATERS_NONLEGACY = new StringBuilder()
                .append("  UNION ")
                .append("  SELECT DISTINCT CLMNAM.OBJID,  CLMNAM.COLUMN_NAME,DB_COLUMN_NAME paramDBName,CLMNAM.PARM_TYPE,null ")
                .append("  FROM GETS_TOOL_DPD_COLNAME CLMNAM ")
                .append("  WHERE LOWER(CLMNAM.FAMILY)     = LOWER(:FAMILY) AND CLMNAM.PARM_TYPE IN ('Standard')");
        public static final StringBuilder FETCH_MP_PARAM = new StringBuilder()
                .append("SELECT DISTINCT nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,nvl2(SOURCE_UOM_ID,SOURCE_UOM_ID,SDP_SOURCE_UOM_ID) sourceUom	")
                .append("FROM GETS_TOOL_DPD_COLNAME COLNAME,GETS_RMD_PARMDEF PARMDEF	")
                .append("WHERE PARMDEF.RULE_PARM_DESC(+)=COLNAME.COLUMN_NAME AND  LOWER(COLNAME.FAMILY)IN LOWER(:Family) 	")
                .append(" AND   COLNAME.COLUMN_NAME= :columnName ORDER BY 2");
        public static final StringBuilder FETCH_SOURCE_UOM = new StringBuilder()
                .append("SELECT DISTINCT SOURCE_UOM_ID	")
                .append("FROM GETS_TOOL_DPD_COLNAME COLNAME,GETS_RMD_PARMDEF PARMDEF	")
                .append("WHERE PARMDEF.RULE_PARM_DESC(+)=COLNAME.COLUMN_NAME AND  LOWER(COLNAME.FAMILY)IN LOWER(:Family) AND SOURCE_UOM_ID is not null	")
                .append(" AND   COLNAME.OBJID= :colNameObjid ")
                .append(" UNION	")
                .append(" SELECT DISTINCT SDP_SOURCE_UOM_ID	FROM GETS_TOOL_DPD_COLNAME COLNAME ")
                .append(" WHERE  LOWER(COLNAME.FAMILY)IN LOWER(:Family) AND SDP_SOURCE_UOM_ID is not null	")
                .append(" AND   COLNAME.OBJID= :colNameObjid ");

        public static final StringBuilder SELECT_TOP_LEVEL_COMPLEX_RULEID = new StringBuilder()
                .append("SELECT FINRUL2CMPRUL FROM GETS_TOOLS.GETS_TOOL_DPD_FINRUL WHERE OBJID=:finRuleObjid");

        public static final StringBuilder UPDATE_TOP_LEVEL_FINAL_RULE1 = new StringBuilder()
                .append("UPDATE  GETS_TOOL_DPD_CMPRUL SET RULE1_2FINRUL = :finRuleObjid  WHERE RULE1_2FINRUL IN (SELECT RULE1_2FINRUL FROM GETS_TOOL_DPD_CMPRUL WHERE RULE1_2FINRUL in (SELECT RULHIS2FINRUL FROM GETS_TOOL_DPD_RULHIS WHERE ORIGINAL_ID=:ruleOriginalObjid))");
        public static final StringBuilder UPDATE_TOP_LEVEL_FINAL_RULE2 = new StringBuilder()
                .append("UPDATE  GETS_TOOL_DPD_CMPRUL SET RULE2_2FINRUL = :finRuleObjid  WHERE RULE2_2FINRUL IN (SELECT RULE2_2FINRUL FROM GETS_TOOL_DPD_CMPRUL WHERE RULE2_2FINRUL in (SELECT RULHIS2FINRUL FROM GETS_TOOL_DPD_RULHIS WHERE ORIGINAL_ID=:ruleOriginalObjid))");

        public static final StringBuilder INSERT_LHR_PERFCHK2RULEDEF = new StringBuilder()
                .append("INSERT INTO GETS_TOOLS.GETS_TOOL_LHR_PERFCHK2RULEDEF (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,HEALTH_FACTOR,PERF_CHECK_ID,RULE_DEF_ID) ")
                .append(" VALUES(GETS_TOOLS.GETS_TOOL_PERFCHK2RULEDEF_SEQ.NEXTVAL,SYSDATE,:userName,SYSDATE,:userName,:healthFactor,:perfCheckID,:ruleDefObjid)");

        public static final StringBuilder FETCH_GEO_LOCATION_VALUES = new StringBuilder()
                .append("  SELECT GEOFENCE_ID,PROXIMITY_DESC")
                .append("  FROM GETS_MCS_PP_CALC_GEOFENCE_DEF GD,GETS_RMD_PP_PROXIMITY_DEF PD,TABLE_BUS_ORG TB")
                .append("  WHERE PPCALC_GEO_DEF2PROX_DEF = PD.OBJID AND PD.PROXIMITY2BUS_ORG = TB.OBJID");

        public static final StringBuilder FETCH_ATS_PARAMETERS = new StringBuilder()
                .append("  SELECT DISTINCT COLNAME.objid,COLNAME.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName")
                .append("  FROM GETS_TOOL_DPD_COLNAME COLNAME");
        public static final StringBuilder FETCH_COL_VALUES = new StringBuilder()
                .append("  SELECT CLMNAM.OBJID,CLMNAM.COLUMN_NAME,CLMVAL.VAL,CLMVAL.MENU_ITEM")
                .append("  FROM GETS_TOOL_DPD_COLVAL CLMVAL,  GETS_TOOL_DPD_COLNAME CLMNAM ")
                .append("  WHERE CLMVAL.COLVAL2COLNAME=CLMNAM.OBJID AND CLMNAM.PARM_TYPE='ATS' ");

        public static final StringBuilder FETCH_ALERT_PARAMETERS = new StringBuilder()
                .append("SELECT DISTINCT COLNAME.objid,COLNAME.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,COLNAME.PARM_TYPE,max(SOURCE_UOM_ID) ")
                .append("FROM GETS_TOOL_DPD_COLNAME COLNAME, GETS_RMD_PARMDEF PARMDEF, GETS_TOOL_CE_PARM_INFO_NEW INFO,TABLE_BUS_ORG ORG	");
        

        

        public static final StringBuilder FETCH_EDP_PARAMETERS = new StringBuilder()
                .append("SELECT DISTINCT COLNAME.objid,COLNAME.COLUMN_NAME,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,COLNAME.PARM_TYPE,max(SOURCE_UOM_ID) ")
                .append("FROM GETS_TOOL_DPD_COLNAME COLNAME,GETS_RMD_PARMDEF PARMDEF	")
                .append("WHERE PARMDEF.RULE_PARM_DESC=COLNAME.COLUMN_NAME AND COLNAME.PARM_TYPE        = 'EDP' ");
        
    }
    


    /** NATIVE SQL for the save Rule ***/
    public static final String USER_HOME = "user.home";
    public static final String RX_AUTH = "RXAUTH";
    public static final String SYMBOL_FRONT_SLAH = "/";
    public static final String SYMBOL_BACK_SLAH = "\\";
    /** NATIVE SQL for the save Rule ***/
    // Added for Asset Overview Services stroy - Start
    public static final String DAO_EXCEPTION_GET_ASSET_SERVICES = "DAO_EXCEPTION_GET_ASSET_SERVICES";
    // Added for Asset Overview Services stroy - End

    public static final String DAO_EXCEPTION_FETCH_FAVORITE_FILTER = "DAO_EXCEPTION_FETCH_FAVORITE_FILTER";
    public static final String DAO_EXCEPTION_DELETE_FAVORITE_FILTER = "DAO_EXCEPTION_DELETE_FAVORITE_FILTER";
    public static final String DAO_EXCEPTION_GET_CASE_TYPE = "DAO_EXCEPTION_GET_CASE_TYPE";
    // Added for Actionable Rx Type stroy - Start
    public static final String DAO_EXCEPTION_ACTIONABLE_RX_TYPE = "DAO_EXCEPTION_ACTIONABLE_RX_TYPE";
    // Added for Actionable Rx Type stroy - End
    public static final String OBJID = "strObjId";

    /** Constants for Virtuals **/
    public static final String DAO_EXCEPTION_GET_VIRTUAL_ID = "DAO_EXCEPTION_GET_VIRTUAL_ID";
    public static final String DAO_EXCEPTION_GET_VIRTUAL_TITLE = "DAO_EXCEPTION_GET_VIRTUAL_TITLE";
    public static final String DAO_EXCEPTION_GET_VIRTUAL_FAMILY = "DAO_EXCEPTION_GET_VIRTUAL_FAMILY";
    public static final String DAO_EXCEPTION_GET_VIRTUAL_STATUS = "DAO_EXCEPTION_GET_VIRTUAL_STATUS";
    public static final String DAO_EXCEPTION_GET_VIRTUAL_LASTUPDATEDBY = "DAO_EXCEPTION_GET_VIRTUAL_LASTUPDATEDBY";
    public static final String DAO_EXCEPTION_GET_VIRTUAL_CREATEDBY = "DAO_EXCEPTION_GET_VIRTUAL_CREATEDBY";
    public static final String GET_KM_MASTER_VIRTUAL_SEQID = "getKmMasterVirtualSeqId";
    public static final String VIRTUAL_ID = "virtualId";
    public static final String VIRTUAL_TITLE = "virtualTitle";

    public static final String TABLE_NAME = "tableName";
    public static final String TABLE_NAME_DISC = "tableNameDisp";
    public static final String PARM_TYPE = "parmType";
    public static final String VIRTUAL_OBJID = "virtualObjid";
    public static final String EQUATION_DESC_TXT = "equationDescTxt";
    public static final String EQUATION_OBJID = "equationObjid";
    public static final String EQUATION_TXT = "equationTxt";
    public static final String CUSTOM_PROCESS_FILE = "customProcessFile";
    public static final String LOOKBACKTIME = "lookBackTime";
    public static final String LOOK_BACK_FAULT_COUNT = "lookBackFaultCount";
    public static final String EQUATION_TYPE = "equationType";
    public static final String EQUATION_JSON_TXT = "equationJSONTxt";
    public static final String PARENT_EQUATION_SEQ_ID = "parentEquationSeqId";
    public static final String DB_COLUMN_NAME = "dbColumnName";
    public static final String RX_TITLE = "rxTitle";
    public static final String VIRTUAL_HIST_UPDATED_BY = "lastUpdatedBy";
    public static final String VIRTUAL_HIST_CREATED_BY = "createdBy";
    public static final String VIRTUAL_HIST_NOTE = "note";
    /** Constants for Virtuals **/

    /**
     * Error code for fetching case management privilege details
     */
    public static final String FETCH_CM_PRIVILEGE_ERROR_CODE = "FETCH_CM_PRIVILEGE_ERROR_CODE";

    public static final String TCS_VERSION_ERROR_CODE = "TCS_VERSION_ERROR_CODE";
    public static final String NO_URGENCY_RX_ERROR_CODE = "NO_URGENCY_RX_ERROR_CODE";
    public static final String SITE_ID_UNAVAILABLE_ERROR_CODE = "SITE_ID_UNAVAILABLE_ERROR_CODE";
    public static final String INSERT_CASE_RECOM_ERROR_CODE = "INSERT_CASE_RECOM_ERROR_CODE";
    public static final String DAO_EXCEPTION_MASS_APPLY = "DAO_EXCEPTION_MASS_APPLY";

    public static final String VISUALIZATION_OCCUR_DATE = "OCCUR_DATE";
    public static final String PARM_TABLE_NAMES = "PARM_TABLE_NAMES";
    public static final String CONTROLLER_CONFIG = "controllerCfg";
    public static final String NEGOTION_SYMBOL = "~";
    public static final String STR_PARMS = "strParms";

    public static final String ROLE_NAME_ALREADY_PRESENT = "ROLE_NAME_ALREADY_PRESENT";
    public static final String SHOW = "Show";

    public static final String RX_EXIST_ERROR_CODE = "RX_EXIST_ERROR_CODE";
    public static final String DAO_EXCEPTION_ADD_RX = "DAO_EXCEPTION_ADD_RX";
    public static final String DAO_EXCEPTION_GET_CASE_RX = "DAO_EXCEPTION_GET_CASE_RX";
    public static final String DAO_EXCEPTION_DELIVER_RX = "DAO_EXCEPTION_DELIVER_RX";
    public static final String DAO_EXCEPTION_DELIVER_RX_API = "DAO_EXCEPTION_DELIVER_RX_API";

    public static final String DAO_EXCEPTION_GET_CURRENT_OWNER_EOA = "DAO_EXCEPTION_GET_CURRENT_OWNER_EOA";
    public static final String DAO_EXCEPTION_TAKE_OWNERSHIP_RTU = "DAO_EXCEPTION_TAKE_OWNERSHIP_RTU";

    public static final String DAO_EXCEPTION_RX_EXISTS = "DAO_EXCEPTION_RX_EXISTS";
    public static final String DAO_EXCEPTION_GET_RX_CLOSE_DATE = "DAO_EXCEPTION_GET_RX_CLOSE_DATE";
    public static final String DISPATCH_CASE_ERROR_CODE = "DISPATCH_CASE_ERROR_CODE";
    public static final String DISPATCH_CASE_CONCURENCY_ERROR_CODE = "DISPATCH_CASE_CONCURENCY_ERROR_CODE";
    public static final String SUMMARY = "summary";
    public static final String CLOSE_CASE_EXCEPTION = "CLOSE_CASE_EXCEPTION";
    public static final String DAO_CONNECTION_EXCEPTION = "DAO_CONNECTION_EXCEPTION";
    public static final String SCORE_RX_RTU_ERROR = "SCORE_RX_RTU_ERROR";
    public static final String SAVE_MANUAL_FEEDBACK = "SAVE_MANUAL_FEEDBACK";
    public static final String DAO_EXCEPTION_DO_ESERVICEVALIDATION = "EXCEPTION IN DO ESERVICEVALIDATION";
    // Reopen RTC/RTU
    public static final String REOPEN_CASE_RTU_ERROR = "REOPEN_CASE_RTU_ERROR";
    public static final String DAO_EXCEPTION_FETCH_REPAIR_CODE = "DAO_EXCEPTION_FETCH_REPAIR_CODE";
    public static final String ADD_CASE_REPAIR_CODE_EXCEPTION = "ADD_CASE_REPAIR_CODE_EXCEPTION";
    public static final String DAO_EXCEPTION_ADD_CASE_REPAIR_CODES = "DAO_EXCEPTION_ADD_CASE_REPAIR_CODES";
    public static final String DAO_EXCEPTION_REMOVE_CASE_REPAIR_CODES = "DAO_EXCEPTION_REMOVE_CASE_REPAIR_CODES";
    public static final String REMOVE_CASE_REPAIR_CODE_EXCEPTION = "REMOVE_CASE_REPAIR_CODE_EXCEPTION";

    /* Added for Visualization changes */
    public static final String DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS = "DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS";
    public static final String PARM_NUMBER = "parmNumber";
    public static final String OMD_VISUALIZATION_LOOKBACK_DAYS = "OMD_VISUALIZATION_LOOKBACK_DAYS";
    public static final String SNAPSHOT = "SNAPSHOT";
    public static final String ENGINERECORDER = "ENGINE RECORDER";
    public static final String ECU = "ECU";
    public static final String CONTROLLER_SOURCE = "controllerSrc";
    public static final String ENGINE_RECORDER = "ENGINE_RECORDER";

    public static final String VISUALIZATION_FILTER_PARM_COLUMN_NAMES = "VISUALIZATION_FILTER_PARM_COLUMN_NAMES";
    public static final String OMD_LOGICAL_OPERATORS = "OMD_LOGICAL_OPERATORS";
    public static final String SIMPLE_OPERATORS = "SIMPLE OPERATORS";
    public static final String COMPLEX_OPERATORS = "COMPLEX OPERATORS";

    public static final String LOCO_STATE = "LOCO_STATE";
    public static final String NOTCH = "NOTCH";
    public static final String AMBIENT_TEMP = "AMBIENT_TEMP";
    public static final String ENGINE_GHP = "ENGINE_GHP";
    public static final String ENGINE_SPEED = "ENGINE_SPEED";
    public static final String LOCO_STATE_INPUT = "locoState";
    public static final String FAULT_CODE_INPUT = "faultCode";
    public static final String AMBIENT_TEMP_INPUT1 = "ambTempVal1";
    public static final String AMBIENT_TEMP_INPUT2 = "ambTempVal2";
    public static final String ENGINE_GHP_INPUT1 = "engGHP1";
    public static final String ENGINE_GHP_INPUT2 = "engGHP2";
    public static final String ENGINE_SPEED_INPUT1 = "engSpeed1";
    public static final String ENGINE_SPEED_INPUT2 = "engSpeed2";
    public static final String NOTCH_INPUT = "notch";
    public static final String ENGINE_RECORDER_ = "ENGINE_RECORDER_";
    public static final String SNAPSHOT_ = "SNAPSHOT_";
    public static final String RECORD_TYPE = "recordType";
    public static final String DAO_EXCEPTION_VISUALIZATION = "DAO_EXCEPTION_VISUALIZATION";
    public static final String DAO_EXCEPTION_GET_VISUALIZATION_DETAILS = "DAO_EXCEPTION_GET_VISUALIZATION_DETAILS";
    public static final String DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS_INVALID_COLUMN = "visualization.external.error";
    public static final String ORA_00904 = "ORA-00904";
    public static final String SCHEDULED = "SCHEDULED";
    public static final String SOURCE_TYPE_CODE = "sourceTypeCode";

    public static final String DAO_EXCEPTION_ASSET_LIFE_STAT = "DAO_EXCEPTION_ASSET_LIFE_STAT";
    public static final String ALL_CUSTOMER = "ALLCUSTOMER";
    public static final String MULTI_LANG_CHACHE_ERROR = "MULTI_LANG_CHACHE_ERROR";
    public static final String USER_ID = "userId";

    public static final String DATE_FORMAT_MCS = "yyyy-MM-dd hh:mm:ss";
    public static final String VIZ_LOOKBACK_HOURS_FROM_DWH = "VISUALIZATION_INCLUDE_RMD_DATA_LOOKBACK_DAYS";
    public static final String ERROR_MODEL_CUSTOMER = "ERROR_MODEL_CUSTOMER";
    public static final String DAO_EXCEPTION_GET_ALL_FLEETS = "DAO_EXCEPTION_GET_ALL_FLEETS";
    public static final String DAO_EXCEPTION_GET_ALL_REGIONS = "DAO_EXCEPTION_GET_ALL_REGIONS";
    public static final String DAO_EXCEPTION_GET_USER_NAMES = "EXCEPTION IN GET CASE MANAGEMENT USERS DETAILS";
    public static final String DAO_EXCEPTION_ASSIGN_CASE_TO_USER = "EXCEPTION IN ASSIGN CASE TO USER";
    public static final String DAO_EXCEPTION_GET_OWNER_FOR_CASE = "EXCEPTION IN GET OWNER FOR CASE";
    public static final String DAO_EXCEPTION_GET_ACTIVITY_LOG = "EXCEPTION IN GET ACTIVITY LOG";
    public static final String DAO_EXCEPTION_GET_ASSET_CASES = "EXCEPTION IN GET ASSET CASES";
    public static final String STR_CUSTOMER = "customer";
    public static final String STR_ASSET_GROUP_NAME = "assetGroupName";
    public static final String LAST_NAME = "lastName";
    public static final String STR_NO_OF_DAYS = "noOfDays";
    public static final String STR_NO_OF_DAYS_VALUE = "180";

    // Added by Vamshi for Dispatch Case Functionality
    public static final String DAO_EXCEPTION_GET_QUEUE_NAMES = "DAO_EXCEPTION_GET_QUEUE_NAMES";
    public static final String DAO_EXCEPTION_DiSPTACH_TO_QUEUE = "DAO_EXCEPTION_DiSPTACH_TO_QUEUE ";
    // Added by Vamshi for Dispatch Case Functionality
    public static final String DAO_EXCEPTION_ADD_NOTES_TO_CASE = "DAO_EXCEPTION_ADD_NOTES_TO_CASE";
    public static final String DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES = "DAO_EXCEPTION_FETCH_UNIT_STCKY_NOTES";
    public static final String DAO_EXCEPTION_FETCH_CASE_STCKY_NOTES = "DAO_EXCEPTION_FETCH_CASE_STCKY_NOTES";
    public static final String DAO_EXCEPTION_REMOVE_STICKY_NOTE = "DAO_EXCEPTION_REMOVE_STICKY_NOTE";
    public static final String DAO_EXCEPTION_GET_CASE_HUSTORY = "DAO_EXCEPTION_GET_CASE_HUSTORY";
    public static final String DAO_EXCEPTION_UPDATE_CASE_DETIALS = "DAO_EXCEPTION_UPDATE_CASE_DETIALS";
    public static final String DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES = "DAO_EXCEPTION_GET_CM_PREVILIGE_ROLES";
    public static final String DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE = "DAO_EXCEPTION_GET_SOLUTIONS_FOR_CASE";
    public static final String DAO_EXCEPTION_GET_CASE_INFO = "DAO_EXCEPTION_GET_CASE_INFO";
    public static final String DAO_EXCEPTION_GET_REC_PENDING = "DAO_EXCEPTION_GET_REC_PENDING";
    public static final String DAO_EXCEPTION_GET_SERVICES = "DAO_EXCEPTION_GET_SERVICES";
    public static final String DAO_EXCEPTION_GET_PENDING_FAULTS = "DAO_EXCEPTION_GET_PENDING_FAULTS";
    public static final String DAO_EXCEPTION_GET_SUBSYSTEM = "DAO_EXCEPTION_GET_SUBSYSTEM";
    public static final String DAO_EXCEPTION_PENDING_FDBK_SERVICE_STATUS = "DAO_EXCEPTION_PENDING_FDBK_SERVICE_STATUS";
    public static final String DAO_EXCEPTION_GET_SERVICE_REQID_STATUS = "DAO_EXCEPTION_GET_SERVICE_REQID_STATUS";
    public static final String DAO_EXCEPTION_GET_DELV_DATE_FOR_RX = "DAO_EXCEPTION_GET_DELV_DATE_FOR_RX";
    public static final String DAO_EXCEPTION_GET_T2_REQ = "DAO_EXCEPTION_GET_T2_REQ";
    public static final String DAO_EXCEPTION_GET_UNIT_SHIP_DETAILS = "DAO_EXCEPTION_GET_UNIT_SHIP_DETAILS";
    public static final String DAO_EXCEPTION_GET_CASE_SCORE = "DAO_EXCEPTION_GET_CASE_SCORE";
    public static final String DAO_EXCEPTION_GET_READY_TO_DELV = "DAO_EXCEPTION_GET_READY_TO_DELV";
    public static final String DAO_EXCEPTION_GET_CUSTOMER_FDBK_OBJID = "DAO_EXCEPTION_GET_CUSTOMER_FDBK_OBJID";
    public static final String DAO_EXCEPTION_CHECK_FOR_DELV_MECHANISM = "DAO_EXCEPTION_CHECK_FOR_DELV_MECHANISM";
    public static final String FDBK_OBJID = "fdbkObjid";
    public static final String CASE_OBJID = "caseObjId";
    public static final String RX_OBJID = "rxObjId";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String DAO_EXCEPTION_GET_MSDC_NOTES = "DAO_EXCEPTION_GET_MSDC_NOTES";
    public static final String DAO_EXCEPTION_MODIFY_RX = "DAO_EXCEPTION_MODIFY_RX";
    public static final String DAO_EXCEPTION_REPLACE_RX = "DAO_EXCEPTION_REPLACE_RX";
    public static final String DAO_EXCEPTION_IN_CLOSING_CONNECTION = "DAO_EXCEPTION_IN_CLOSING_CONNECTION";

    public static final String DAO_EXCEPTION_GET_RX_HISTORY = "EXCEPTION IN GET RX HISTORY";
    public static final String DAO_EXCEPTION_GET_RX_STATUS_HISTORY = "EXCEPTION IN GET RX STATUS HISTORY";
    public static final String DAO_EXCEPTION_GET_SERVICE_RQ_ID = "EXCEPTION IN GET SERVICE RQ ID";
    public static final String DAO_EXCEPTION_GET_CUST_FDBK_OBJID = "EXCEPTION IN GET CUST FDBK OBJID";
    public static final String DAO_EXCEPTION_GET_CLOSEOUT_REPAIR_CODE = "EXCEPTION IN GET CLOSEOUT REPAIR CODE";
    public static final String DAO_EXCEPTION_GET_ATTACHED_DETAILS = "EXCEPTION IN GET ATTACHED DETAILS";
    public static final String DAO_EXCEPTION_GET_RX_NOTE = "EXCEPTION IN GET RX NOTE";
    public static final String DAO_EXCEPTION_GET_GOOD_FDBK = "EXCEPTION IN GET GOOD FDBK";
    public static final String NORECORDFOUNDEXCEPTION = "NO_RECORD_FOUND_EXCEPTION";
    public static final String EMPTY_STRING = "";
    public static final String DAO_EXCEPTION_GET_CLOSURE = "EXCEPTION IN GET CLOSURE";
    public static final String DAO_EXCEPTION_CHECK_FOR_CONTOLLER_CONFIG = "EXCEPTION IN CHECK FOR CONTOLLER CONFIG";
    // Mass Apply
    public static final String DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS = "DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS";
    public static final String DAO_EXCEPTION_GET_MAX_MASS_APPPLY_RX = "DAO_EXCEPTION_GET_MAX_MASS_APPPLY_RX";
    public static final String DAO_EXCPETION_GET_USER_DETAILS = "DAO_EXCPETION_GET_USER_DETAILS";

    // Append
    public static final String DAO_EXCEPTION_APPEND_CASE = "DAO_EXCEPTION_APPEND_CASE";

    public static final String DAO_EXCEPTION_GET_DIAGNOSTIC_WEIGHT = "EXCEPTION IN GET DIAGNOSTIC WEIGHT";
    public static final String DAO_EXCEPTION_GET_SUBSYS_WEIGHT = "EXCEPTION IN GET SUBSYS WEIGHT";
    public static final String DAO_EXCEPTION_GET_MODE_RESTRICTION = "EXCEPTION IN GET MODE RESTRICTION";
    public static final String DAO_EXCEPTION_GET_FAULT_CLASSIFICATION = "EXCEPTION IN GET FAULT CLASSIFICATION";
    public static final String DAO_EXCEPTION_GET_CRITICAL_FLAG = "EXCEPTION IN GET FAULT CLASSIFICATION";
    public static final String DAO_EXCEPTION_GET_FAULT_STRATEGY_DETAILS = "EXCEPTION IN GET FAULT STRATEGY DETAILS";
    public static final String DAO_EXCEPTION_GET_FAULT_RULE_DESC = "EXCEPTION IN GET FAULT RULE DESC";
    public static final String DAO_EXCEPTION_UPDATE_FAULT_SERVICE_STRATEGY = "EXCEPTION IN UPDATE FAULT SERVICE STRATEGY";
    public static final String DAO_EXCEPTION_GETRX_DETAILS = "DAO_EXCEPTION_GET_RX_DETAILS";
    public static final String DAO_EXCEPTION_GET_MODELS_FOR_RX = "DAO_EXCEPTION_GET_MODELS_FOR_RX";
    public static final String DAO_EXCEPTION_GET_VEHCONFIG_GROUP = "DAO_EXCEPTION_GET_VEHCONFIG_GROUP";
    public static final String DAO_EXCEPTION_GET_VEHCONFIG_GROUP_FOR_RX = "DAO_EXCEPTION_GET_VEHCONFIG_GROUP_FOR_RX";
    public static final String DAO_EXCEPTION_GET_MODEL_MATCH = "DAO_EXCEPTION_GET_MODEL_MATCH";
    public static final String DAO_EXCEPTION_UNIT_CONFIG = "EXCEPTION IN UNIT CONFIGURATION";
    public static final String DAO_EXCEPTION_FALSE_ALARM_DETAILS = "EXCEPTION IN FALSE ALARM DETAILS METHOD";
    public static final String DAO_EXCEPTION_FALSE_ALARM_RX_DETAILS = "EXCEPTION IN FALSE ALARM RX DETAILS METHOD";
    public static final String DAO_EXCEPTION_MDSC_ACCURATE_DETAILS = "EXCEPTION IN MDSC ACCURATE DETAILS METHOD";
    public static final String DAO_EXCEPTION_MDSC_ACCURATE_CASE_DETAILS = "EXCEPTION IN MDSC ACCURATE CASE DETAILS METHOD";
    public static final String DAO_EXCEPTION_MDSC_ACCURATE_RX_DETAILS = "EXCEPTION IN MDSC ACCURATE RX DETAILS METHOD";
    public static final String DATA_SCREEN_MISSING_CONFIGURATION = "DATA_SCREEN_MISSING_CONFIGURATION";
    public static final String DAO_EXCEPTION_GET_FAULT_ORIGIN = "DAO_EXCEPTION_GET_FAULT_ORIGIN";
    public static final String DAO_EXCEPTION_FAULT_CODE_SUBID = "DAO_EXCEPTION_FAULT_CODE_SUBID";
    public static final String DAO_EXCEPTION_GET_VIEW_FSS_FAULT_CODE = "DAO_EXCEPTION_GET_VIEW_FSS_FAULT_CODE";
    public static final String DAO_EXCEPTION_GET_FAULT_STRATEGY_OBJID = "DAO_EXCEPTION_GET_FAULT_STRATEGY_OBJID";
    public static final String DAO_EXCEPTION_CHECK_UNIT_SHIP_DETAILS = "DAO_EXCEPTION_CHECK_UNIT_SHIP_DETAILS";
    // added for Anomaly parms in visualization
    public static final String VISUALIZATION_ANOMALY_PARAMETERS = "VISUALIZATION_ANOMALY_PARAMETERS";
    public static final String OIL_INLET = "OIL_INLET";
    public static final String BAROMETRIC_PRESS = "BAROMETRIC_PRESS";
    public static final String HP_AVAILABLE = "HP_AVAILABLE";
    public static final String OIL_INLET_INPUT1 = "oilInlet1";
    public static final String OIL_INLET_INPUT2 = "oilInlet2";
    public static final String HP_AVAILABLE_INPUT1 = "hpAvailable1";
    public static final String HP_AVAILABLE_INPUT2 = "hpAvailable2";
    public static final String BAROMETRIC_PRESS_INPUT1 = "barometricPress1";
    public static final String BAROMETRIC_PRESS_INPUT2 = "barometricPress2";
    public static final String DAO_EXCEPTION_DEVICE_INFORMATION = "EXCEPTION IN DEVICE INFORMATION METHOD";
    public static final String INVALID_DEVICE_CONFIGURATION = "Asset is having 0 or more devices ";
    public static final String DAO_EXCEPTION_SAVE_HC_DETAILS = "EXCEPTION IN SAVING HC DETAILS";
    public static final String DAO_EXCPETION_FIND_CASES = "EXCEPTION IN FIND CASES";
    // added for Unit Shippers
    public static final String DAO_EXCEPTION_GET_UNIT_TO_BE_SHIPPED = "DAO_EXCEPTION_GET_UNIT_TO_BE_SHIPPED";
    public static final String DAO_EXCEPTION_GET_LAST_SHIPPED_UNITS = "DAO_EXCEPTION_GET_LAST_SHIPPED_UNITS";
    public static final String DAO_EXCEPTION_UPDATE_UNITS_TO_BE_SHIPPED = "DAO_EXCEPTION_UPDATE_UNITS_TO_BE_SHIPPED";
    public static final String SHIP_DATE = "shipDate";
    public static final String TEST_TRACK_DATE = "testTrackDate";
    public static final String DEFAULT_NO_OF_DAYS_UNIT_SHIPPERS = "DEFAULT_NO_OF_DAYS_UNIT_SHIPPERS";
    public static final String DAO_EXCEPTION_CONVERT_DATE_TO_EST = "DAO_EXCEPTION_CONVERT_DATE_TO_EST";
    public static final String VEHICLE_OBJID = "vehicleObjId";
    public static final String DAO_EXCEPTION_DISPLAY_PPASSETHST_BTN = "DAO_EXCEPTION_DISPLAY_PPASSETHST_BTN";
    public static final String SUM_OF_RESIDUAL_VALUES = "SUM_OF_RESIDUAL_VALUES";
    public static final String DAO_EXCEPTION_ROAD_NUMBERS = "DAO_EXCEPTION_ROAD_NUMBERS";
    public static final String DAO_EXCEPTION_CUTSNAME_RNH = "DAO_EXCEPTION_CUTSNAME_RNH";

    // Material Usage - Added by Mohamed
    public static final String DAO_EXCEPTION_GET_MATERIAL_USAGE = "EXCEPTION IN GET MATERILA USAGE";

    //
    public static final String DAO_EXCEPTION_VALIDATE_EGA_HC = "EXCEPTION IN VALIDATE EGA HC METHOD";
    public static final String DAO_EXCEPTION_VALIDATE_NT_HC = "EXCEPTION IN VALIDATE NT HC METHOD";

    // Common section data for asset case - Added by mohamed
    public static final String DAO_EXCEPTION_GET_VEHICLE_OBJECT = "EXCEPTION IN GET VEHICLE OBJECT";
    public static final String DAO_EXCEPTION_GET_LAST_EOA_FAULT_HEADER = "EXCEPTION IN GET LAST EOA FAULT HEADER";
    public static final String DAO_EXCEPTION_GET_LAST_PPATS_MSG_HEADER = "EXCEPTION IN GET PP/ATS MSG HEADER";
    public static final String DAO_EXCEPTION_GET_ESTP_DOWNLOAD_HEADER = "EXCEPTION IN GET ESTP DOWNLOAD HEADER";
    public static final String DAO_EXCEPTION_GET_FAULT_DATE_CELL = "EXCEPTION IN GET FAULT CELL DATE";
    // added for Add Notes and Find Notes
    public static final String DAO_EXCEPTION_ADD_NOTES_TO_VEHICLE = "DAO_EXCEPTION_ADD_NOTES_TO_VEHICLE";
    public static final String DAO_EXCEPTION_GET_ALL_CONTROLLERS = "DAO_EXCEPTION_GET_ALL_CONTROLLERS";
    public static final String DAO_EXCEPTION_GET_ALL_CREATORS = "DAO_EXCEPTION_GET_ALL_CREATORS";
    public static final String DAO_EXCEPTION_FIND_NOTES_TO_VEHICLE = "DAO_EXCEPTION_FIND_NOTES_TO_VEHICLE";
    public static final String FIND_NOTES_ORDER_BY = "CREATION_TIME";
    public static final String DAO_EXCEPTION_FIND_NOTES_REMOVE_STICKY_NOTE = "DAO_EXCEPTION_FIND_NOTES_REMOVE_STICKY_NOTE";

    // Vehicle Config
    public static final String DAO_EXCEPTION_DELETE_VEH_CFG_TEMPLATE = "DAO_EXCEPTION_DELETE_VEH_CFG_TEMPLATE";
    public static final String DAO_EXCEPTION_UPDATE_MTM_VEHICLE = "DAO_EXCEPTION_UPDATE_MTM_VEHICLE";
    public static final String DAO_EXCEPTION_UPDATE_EDP_SDP_CFG_MTM_VEH_OBSOLOTE = "DAO_EXCEPTION_UPDATE_EDP_SDP_CFG_MTM_VEH_OBSOLOTE";
    public static final String DAO_EXCEPTION_CHECK_CFG_TEMPLATE_APPLIED = "DAO_EXCEPTION_CHECK_CFG_TEMPLATE_APPLIED";
    public static final String DAO_EXCEPTION_GET_VEHICLE_HEADER_NUMBER = "DAO_EXCEPTION_GET_VEHICLE_HEADER_NUMBER";
    public static final String DAO_EXCEPTION_GET_MSG_PRIORITY = "DAO_EXCEPTION_GET_MSG_PRIORITY";
    public static final String DAO_EXCEPTION_CHECK_REQUEST_PENDING = "DAO_EXCEPTION_CHECK_REQUEST_PENDING";
    public static final String DAO_EXCEPTION_SEND_MESSAGE_TO_QUEUE = "DAO_EXCEPTION_SEND_MESSAGE_TO_QUEUE";
    public static final String DAO_EXCEPTION_GET_UNIT_TYPE = "DAO_EXCEPTION_GET_UNIT_TYPE";
    public static final String DAO_EXCEPTION_GET_SERVICE_TYPE = "DAO_EXCEPTION_GET_SERVICE_TYPE";
    public static final String DAO_EXCEPTION_GET_VEHICLE_BOM_CONFIG_DETAILS = "EXCEPTION IN GET VEHICLE BOM CONFIGURATION DETAILS";
    public static final String DAO_EXCEPTION_GET_MDSC_START_UP_CONTROLLERS_INFO = "EXCEPTION IN GET MDSC START UP CONTROLLERS INFORMATION";
    public static final String DAO_EXCEPTION_GET_VEHICLE_CFG_TEMPLATE = "EXCEPTION IN GET VEHICLE CONFIGURATION TEMPLATE";
    public static final String DAO_EXCEPTION_SAVE_VEHICLE_BOM_CONFIG_DETAILS = "EXCEPTION IN SAVE VEHICLE CONFIGURATION DETAILS";
    public static final String CURRENT_VERSION = "currentVersion";
    public static final String EXPECTED_VERSION = "expectedVersion";
    public static final String SERIAL_NO = "serialNumber";
    public static final String CFG_OBJ_ID = "cgfObjId";
    public static final String DAO_EXCEPTION_FILTER_RECORDS = "EXCEPTION IN FILTER RECORDS";
    public static final String DAO_EXCEPTION_CHECK_CONTROLLER_CONFIG = "DAO_EXCEPTION_CHECK_CONTROLLER_CONFIG";
    // Reclose - Added by Mohamed
    public static final String DAO_EXCEPTION_GET_RX_DEATAILS_FOR_RE_CLOSE = "EXCEPTION IN GET_RX_DEATAILS_FOR_RE_CLOSE";
    public static final String DAO_EXCEPTION_CLOSE_RESULT = "EXCEPTION IN CLOSE RESULT";
    public static final String DAO_EXCEPTION_RECLOSE_RESET_FAULTS = "EXCEPTION IN RECLOSE_RESET_FAULTS";
    public static final String DAO_EXCEPTION_CUSTOMER_POPUP_ADMIN = "DAO_EXCEPTION_CUSTOMER_URGENCY_POPUP_ADMIN";
    public static final String BO_EXCEPTION_CUSTOMER_POPUP_ADMIN = "BO_EXCEPTION_CUSTOMER_URGENCY_POPUP_ADMIN";
    // add for pop up list admin
    public static final String DAO_EXCPETION_POPUP_LISTADMIN = "EXCEPTION IN POPUP_LISTADMIN";
    public static final String KEP_FAMILY_LIST = "KEP_FAMILY_LIST";
    /***** SYSTEM PARAM TITLE AND VALUE ******/
    public static final String DAO_EXCEPTION_SYSPARAM_FETCH = "DAO_EXCEPTION_SYSPARAM_FETCH";

    /* Added By Amuthan for CBR */
    public static final String DAO_EXCEPTION_GET_CBR_RX_MAINTENANCE = "DAO_EXCEPTION_GET_CBR_RX_MAINTENANCE";
    public static final String DAO_EXCEPTION_GET_CBR_CASE_MAINTENANCE = "DAO_EXCEPTION_GET_CBR_CASE_MAINTENANCE";
    public static final String DAO_EXCEPTION_GET_CBR_CASE_ACTIVATE_DEACTIVATE = "DAO_EXCEPTION_GET_CBR_CASE_ACTIVATE_DEACTIVATE";
    public static final String DAO_EXCEPTION_GET_CBR_RX_ACTIVATE_DEACTIVATE = "DAO_EXCEPTION_GET_CBR_RX_ACTIVATE_DEACTIVATE";

    public static final String DAO_EXCEPTION_GET_LMS_LOCO_ID = "DAO_EXCEPTION_GET_LMS_LOCO_ID";
    public static final String DAO_EXCEPTION_GET_LMS_RULE_ID = "DAO_EXCEPTION_GET_LMS_RULE_ID";
    // Added By Amuthan for Adding Notes section to Rule Module
    public static final String STR_NOTES = "notes";
    public static final String DAO_EXCEPTION_EXTERNAL_CREATEDBY = "DAO_EXCEPTION_EXTERNAL_CREATEDBY";
    public static final String DAO_EXCEPTION_EXTERNAL_LAST_UPDATED_BY = "DAO_EXCEPTION_EXTERNAL_LAST_UPDATED_BY";
    public static final String DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_SEARCH = "DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_SEARCH";
    public static final String DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_AUTHOR = "DAO_EXCEPTION_EXTERNAL_FAMILY_RULE_AUTHOR";
    // Added for Repair Facility Maintenance
    public static final String DAO_EXCEPTION_IN_GET_REPAIR_FACILITY_DETAILS = "DAO EXCEPTION IN GET REPAIR FACILITY DETAILS";
    public static final String DAO_EXCEPTION_IN_UPDATE_REPAIR_LOCATION_DETAILS = "DAO EXCEPTION IN UPDATE REPAIR LOCATION DETAILS";
    public static final String DAO_EXCEPTION_IN_INSERT_REPAIR_LOCATION_DETAILS = "DAO EXCEPTION IN INSERT REPAIR LOCATION DETAILS";
    public static final String ACTIVE_RAILWAY_DESIG_CODE_EXISTS = "ACTIVE RAILWAY DESIG CODE EXISTS";
    public static final String NO_ACTIVE_RAILWAY_DESIG_CODE_EXISTS = "NO ACTIVE RAILWAY DESIG CODE EXISTS";
    public static final String DAO_EXCEPTION_IN_CHECK_ACTIVE_DUP_RAILWAY_CODE = "DAO EXCEPTION IN CHECK ACTIVE DUP RAILWAY CODE METHOD";
    public static final String DAO_EXCEPTION_GET_CUSTOMER_SITES = "DAO EXCEPTION IN GET CUSTOMER SITES";
    // Added for Customer ID
    public static final String DAO_EXCEPTION_GET_CUSTOMER_ID = "DAO_EXCEPTION_GET_CUSTOMER_ID";
    public static final String INVALID_ASSET = "INVALID_ASSET";
    // Added By Arun for BOM
    public static final String DAO_EXCPETION_BOM_CONFIG_LIST = "DAO_EXCPETION_BOM_CONFIG_LIST";
    public static final String DAO_EXCPETION_POPULATE_BOM_CONFIG_DETAILS = "DAO_EXCPETION_POPULATE_BOM_CONFIG_DETAILS";
    public static final String DAO_EXCPETION_POPULATE_BOM_PARAMETER_DETAILS = "DAO_EXCPETION_POPULATE_BOM_PARAMETER_DETAILS";
    public static final String PARAMETER_OBJID = "parameterObjid";
    public static final String VALUE = "value";
    public static final String UPDATE = "update";
    public static final String ADD = "add";
    public static final String CLONE = "clone";
    public static final String CONFIG_ID = "configId";
    public static final String CONFIG_ITEM = "configItem";
    public static final String NO_OF_RECORDS = "noOfRecords";
    public static final String DAO_EXCEPTION_SAVE_BOM_CONFIG_DETAILS = "EXCEPTION IN SAVE BOM CONFIGURATION DETAILS";
    // Added for Config Maintenance
    public static final String DAO_EXCEPTION_GET_CONTROLLER_CONFIGS = "DAO_EXCEPTION_GET_CONTROLLER_CONFIGS";
    public static final String DAO_EXCEPTION_GET_CONTROLLER_CONFIG_TEMPLATE = "DAO_EXCEPTION_GET_CONTROLLER_CONFIG_TEMPLATE";
    public static final String DAO_EXCEPTION_GET_EFI_DETAILS = "DAO_EXCEPTION_GET_EFI_DETAILS";
    public static final String DAO_EXCEPTION_GET_PARAMETER_TITLE = "DAO_EXCEPTION_GET_PARAMETER_TITLE";
    public static final String DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS = "DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS";
    public static final String DAO_EXCEPTION_GET_MAXIMUM_VERSION = "DAO_EXCEPTION_GET_MAXIMUM_VERSION";
    public static final String TEMPLATE_NO = "templateNo";
    public static final String VERSION_NO = "versionNo";
    public static final String CTRL_CONFIG = "ctrlCfgObjId";
    public static final String DAO_EXCEPTION_REMOVE_FFD_TEMPLATE = "DAO_EXCEPTION_REMOVE_FFD_TEMPLATE";
    public static final String DAO_EXCEPTION_REMOVE_FRD_TEMPLATE = "DAO_EXCEPTION_REMOVE_FRD_TEMPLATE";
    public static final String DAO_EXCEPTION_GET_CURRENT_TEMPLATE = "DAO_EXCEPTION_GET_CURRENT_TEMPLATE";
    public static final String DAO_EXCEPTION_GET_EDP_TEMPLATE = "DAO_EXCEPTION_GET_EDP_TEMPLATE";
    public static final String DAO_EXCEPTION_GET_CURRENT_STATUS = "DAO_EXCEPTION_GET_CURRENT_STATUS";
    public static final String DAO_EXCEPTION_GET_TITLE = "DAO_EXCEPTION_GET_TITLE";
    public static final String DAO_EXCEPTION_GET_STATUS_DETAILS = "DAO_EXCEPTION_GET_STATUS_DETAILS";
    public static final String FAULT_CODE_FROM = "faultCodeFrom";
    public static final String FAULT_START_ID = "faultStartId";
    public static final String FAULT_CODE_TO = "faultCodeTo";
    public static final String FAULT_TO_ID = "faultToId";
    public static final String NEW_VERSION = "newversion";
    public static final String DELETE = "delete";
    public static final String UPDATE_HEADER = "updateheader";
    public static final String INSERT_HEADER = "insertheader";
    public static final String INSERT = "insert";
    public static final String DAO_EXCEPTION_GET_MAX_PARAMETER_COUNT = "DAO_EXCEPTION_GET_MAX_PARAMETER_COUNT";
    public static final String MAXIMUM = "MAXIMUM";
    // Added for Config Maintenance
    public static final String DAO_EXCEPTION_IN_GET_EFI_CFG_DATE = "DAO_EXCEPTION_IN_GET_EFI_CFG_DATE";
    public static final String DAO_EXCEPTION_IN_GET_EFI_SW_VERSION_DATE = "DAO_EXCEPTION_IN_GET_EFI_SW_VERSION_DATE";
    public static final String DAO_EXCEPTION_IN_GET_EFI_MEMORY_DATE = "DAO_EXCEPTION_IN_GET_EFI_MEMORY_DATE";
    public static final String NO_CFG_DEF_FOUND = "No Config Definitions found";
    public static final String DAO_EXCEPTION_IN_CREATE_NEW_EFI = "DAO_EXCEPTION_IN_CREATE_NEW_EFI";
    public static final String NO_NEW_EFI_DEF_FOUND = "No New EFI Definitions to Create";
    public static final String DAO_EXCEPTION_GET_MAX_TEMPLATE_NUMBER = "DAO_EXCEPTION_GET_MAX_TEMPLATE_NUMBER";
    public static final String DAO_EXCEPTION_GET_TEMPLATE_MAX_VERSION_NUMBER = "DAO_EXCEPTION_GET_TEMPLATE_MAX_VERSION_NUMBER";
    public static final String DAO_EXCEPTION_GET_EDP_PARAMETERS = "DAO_EXCEPTION_GET_EDP_PARAMETERS";
    public static final String DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS = "DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS";
    public static final String DAO_EXCEPTION_SAVE_EDP_PARAMETERS = "DAO_EXCEPTION_SAVE_EDP_PARAMETERS";
    public static final String GETS_RMD_SCHEMA = "gets_rmd";
    public static final String GETS_RMD_APPLY_EFI_PKG = "GETS_RMD_APPLY_EFI_PKG";

    // Mass Apply Config
    public static final String DAO_EXCEPTION_GET_CTRL_CONFIGS = "DAO_EXCEPTION_GET_CTRL_CONFIGS";
    public static final String DAO_EXCEPTION_GET_FFD_CONFIGS = "DAO_EXCEPTION_GET_FFD_CONFIGS";
    public static final String DAO_EXCEPTION_GET_FRD_CONFIGS = "DAO_EXCEPTION_GET_FRD_CONFIGS";
    public static final String DAO_EXCEPTION_GET_EDP_CONFIGS = "DAO_EXCEPTION_GET_EDP_CONFIGS";
    public static final String DAO_EXCEPTION_GET_RCI_CONFIGS = "DAO_EXCEPTION_GET_RCI_CONFIGS";
    public static final String DAO_EXCEPTION_GET_EDP_TEMPLATES = "DAO_EXCEPTION_GET_EDP_TEMPLATES";
    public static final String DAO_EXCEPTION_GET_FFD_TEMPLATES = "DAO_EXCEPTION_GET_FFD_TEMPLATES";
    public static final String DAO_EXCEPTION_GET_FRD_TEMPLATES = "DAO_EXCEPTION_GET_FRD_TEMPLATES";
    public static final String DAO_EXCEPTION_GET_SPECIFIC_ASSET_NUMBERS = "DAO_EXCEPTION_GET_SPECIFIC_ASSET_NUMBERS";
    public static final String DAO_EXCEPTION_GET_CFG_TEMPLATE_VERSIONS = "DAO_EXCEPTION_GET_CFG_TEMPLATE_VERSIONS";
    public static final String DAO_EXCEPTION_GET_ONBOARD_SOFTWARE_VERSIONS = "DAO_EXCEPTION_GET_ONBOARD_SOFTWARE_VERSIONS";
    public static final String DAO_EXCEPTION_APPLY_CFG_TEMPLATES = "DAO_EXCEPTION_APPLY_CFG_TEMPLATES";
    public static final String DAO_EXCEPTION_PROCESS_CONTENTS_FOR_APPLY = "DAO_EXCEPTION_PROCESS_CONTENTS_FOR_APPLY";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_FILTER_BY_RNH = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_FILTER_BY_RNH";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_FLTR_BY_MODEL_FLEET = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_FLTR_BY_MODEL_FLEET";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_FLTR_BY_SWVERSION = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_FLTR_BY_SWVERSION";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_UPGRADE_FROM_TO = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_UPGRADE_FROM_TO";
    public static final String DAO_EXCEPTION_GET_CELL_TYPE = "DAO_EXCEPTION_GET_CELL_TYPE";
    public static final String DAO_EXCEPTION_CHECK_SERVICE_ENABLED = "DAO_EXCEPTION_CHECK_SERVICE_ENABLED";
    public static final String DAO_EXCEPTION_GET_FFD_MSGDEF_OBJID = "DAO_EXCEPTION_GET_FFD_MSGDEF_OBJID";
    public static final String DAO_EXCEPTION_GET_FRD_MSGDEF_OBJID = "DAO_EXCEPTION_GET_FRD_MSGDEF_OBJID";
    public static final String DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP = "DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP";
    public static final String DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD = "DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD";
    public static final String DAO_EXCEPTION_CREATE_NEW_OUT_MSGHIST_RECORD = "DAO_EXCEPTION_CREATE_NEW_OUT_MSGHIST_RECORD";

    // EFI APPLY
    public static final String DAO_EXCEPTION_APPLY_EFI_CONFIG = "DAO_EXCEPTION_APPLY_EFI_CONFIG";
    public static final String DAO_EXCEPTION_GET_EFI_CONFIG_TEMPALTES = "DAO_EXCEPTION_GET_EFI_CONFIG_TEMPALTES";
    public static final String DAO_EXCEPTION_PROCESS_CONTENTS_FOR_EFI_APPLY = "DAO_EXCEPTION_PROCESS_CONTENTS_FOR_EFI_APPLY";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_UPDATEVERSION = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_UPDATEVERSION";
    public static final String DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_FROMRN = "DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_FROMRN";
    public static final String DAO_EXCEPTION_GET_EFI_MSGDEFOBJID = "DAO_EXCEPTION_GET_EFI_MSGDEFOBJID";
    public static final String DAO_EXCEPTION_CREATE_NEW_OUT_MSG_HIST_RECORD_EFI = "DAO_EXCEPTION_CREATE_NEW_OUT_MSG_HIST_RECORD_EFI";
    public static final String DAO_EXCEPTION_CREATE_NEW_MTM_VEH_RECORD_EFI = "DAO_EXCEPTION_CREATE_NEW_MTM_VEH_RECORD_EFI";

    public static final String GEO = "GEO";
    public static final String ATS = "ATS";
    public static final String DAO_EXCEPTION_FETCH_GEO_VALUES = "DAO_EXCEPTION_FETCH_GEO_VALUES";
    public static final String DAO_EXCEPTION_FETCH_ATS_VALUES = "DAO_EXCEPTION_FETCH_ATS_VALUES";
    public static final String GEOFENCE_ID = "geoFenceId";
    public static final String DAO_EXCEPTION_CUSTOMER_FOR_RULE = "DAO_EXCEPTION_CUSTOMER_FOR_RULE";
    public static final String GEOLOCATION = "Geolocation";
    public static final String UOM = "uom";
    public static final String ALERT = "ALERT";
    public static final String DAO_EXCEPTION_OPEN_RX_COUNT = "DAO_EXCEPTION_OPEN_RX_COUNT";
    public static final String DAO_EXCEPTION_CASE_TREND_COUNT = "DAO_EXCEPTION_CASE_TREND_COUNT";
    public static final String DAO_EXCEPTION_CASE_CONVERSION_PERCENTAGE = "DAO_EXCEPTION_CASE_CONVERSION_PERCENTAGE";
    public static final String DAO_EXCEPTION_TOP_NO_ACTION_RX = "DAO_EXCEPTION_TOP_NO_ACTION_RX";
    public static final String DAO_EXCEPTION_COMM_NOTES_DETAILS = "DAO_EXCEPTION_COMM_NOTES_DETAILS";
    public static final String DAO_EXCEPTION_SHIP_UNIT_REPORT = "DAO_EXCEPTION_SHIP_UNIT_REPORT";
    public static final String DAO_EXCEPTION_INFANCY_FAILURE_UNIT = "DAO_EXCEPTION_INFANCY_FAILURE_UNIT";
    public static final String DAO_EXCEPTION_GENERAL_NOTES_DETAILS = "DAO_EXCEPTION_GENERAL_NOTES_DETAILS";
    // Added for MDSC Admin
    public static final String DAO_EXCEPTION_GET_CM_ML_DETAILS = "DAO_EXCEPTION_GET_CM_ML_DETAILS";
    public static final String EOA_ALIAS = "aliasName";
    public static final String DAO_EXCEPTION_CHECK_EOA_ALIAS_EXISTS = "DAO_EXCEPTION_CHECK_EOA_ALIAS_EXISTS";
    public static final String USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES = "USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES";
    public static final String PDF_REGULAR = "Regular";
    public static final String RX_STATE_LOOKBACK_DAYS = "RX_STATE_LOOKBACK_DAYS";
    public static final String STR_RX = "Rx";
    public static final String DAO_EXCEPTION_GET_MODEL_VALUES = "DAO_EXCEPTION_GET_MODEL_VALUES";

    // Added For Case Screen Changes
    public static final String DAO_EXCEPTION_NEXT_SCHEDULED_RUN = "DAO_EXCEPTION_NEXT_SCHEDULED_RUN";

    public static final String EMETRICS_UPDATE_ERROR_MSG = "EMETRICS_UPDATE_ERROR_MSG";

    // Added For MassApply Revamp
    public static final String DAO_EXCEPTION_COPY_DETAILS_TO_VO = "DAO_EXCEPTION_COPY_DETAILS_TO_VO";
    public static final String ORDER_BY_FLEET_NUMBER_ASC = " ORDER BY FLEET.FLEET_NUMBER ASC";
    public static final String DAO_EXCEPTION_UPDATE_LAST_LOGIN_DETAILS = "DAO_EXCEPTION_UPDATE_LAST_LOGIN_DETAILS";
    // Added for Contact Screen
    public static final String DAO_EXCEPTION_GET_CONTACTS = "DAO_EXCEPTION_GET_CONTACTS";
    public static final String DAO_EXCEPTION_VIEW_CONTACT_DETAILS = "DAO_EXCEPTION_VIEW_CONTACT_DETAILS";
    public static final String DAO_EXCEPTION_GET_CONTACT_SECONDARY_SITE_DETAILS = "DAO_EXCEPTION_GET_CONTACT_SECONDARY_SITE_DETAILS";
    public static final String DAO_EXCEPTION_ADD_CONTACT_SECONDARY_SITE = "DAO_EXCEPTION_ADD_CONTACT_SECONDARY_SITE";
    public static final String DAO_EXCEPTION_REMOVE_CONTACT_SECONDARY_SITE = "DAO_EXCEPTION_REMOVE_CONTACT_SECONDARY_SITE";
    public static final String DAO_EXCEPTION_IN_IS_DUPLICATE_CONTACT_METHOD = "DAO_EXCEPTION_IN_IS_DUPLICATE_CONTACT_METHOD";
    public static final String DAO_EXCEPTION_IN_GET_CONTACT_OBJID_METHOD = "DAO_EXCEPTION_IN_GET_CONTACT_OBJID_METHOD";
    public static final String DAO_EXCEPTION_ADD_OR_UPDATE_CONTACT = "DAO_EXCEPTION_ADD_OR_UPDATE_CONTACT";
    public static final String DAO_EXCEPTION_IN_IS_DUPLICATE_SITE_OR_ROLE_METHOD = "DAO_EXCEPTION_IN_IS_DUPLICATE_SITE_OR_ROLE_METHOD";
    public static final String DAO_EXCEPTION_GET_SITES = "DAO_EXCEPTION_GET_SITES";
    public static final String DAO_EXCEPTION_VIEW_SITE_DETAILS = "DAO_EXCEPTION_VIEW_SITE_DETAILS";
    public static final String DAO_EXCEPTION_UPDATE_SITE = "DAO_EXCEPTION_UPDATE_SITE";
    public static final String DAO_EXCEPTION_CREATE_SITE = "DAO_EXCEPTION_CREATE_SITE";
    public static final String DAO_EXCEPTION_GET_ADDRESS = "DAO_EXCEPTION_GET_ADDRESS";
    public static final String DAO_EXCEPTION_VIEW_ADDRESS_DETAILS = "DAO_EXCEPTION_VIEW_ADDRESS_DETAILS";
    public static final String DAO_EXCEPTION_GET_COUNTRY_LIST = "DAO_EXCEPTION_GET_COUNTRY_LIST";
    public static final String DAO_EXCEPTION_GET_COUNTRY_STATES = "DAO_EXCEPTION_GET_COUNTRY_STATES";
    public static final String DAO_EXCEPTION_GET_COUNTRY_TIMEZONES = "DAO_EXCEPTION_GET_COUNTRY_TIMEZONES";
    public static final String DAO_EXCEPTION_ADD_OR_UPDATE_ADDRESS = "DAO_EXCEPTION_ADD_OR_UPDATE_ADDRESS";
    public static final String DAO_EXCEPTION_GET_ASSET_DETAILS = "DAO_EXCEPTION_GET_ASSET_DETAILS";
    public static final String DAO_EXCEPTION_SHOPNOTAVAILABLE = "DAO_EXCEPTION_SHOPNOTAVAILABLE";
    // GPOC Turnover general and comm notes
    public static final String DAO_EXCPETION_GENERAL_NOTES = "EXCEPTION IN GENERAL NOTES";
    public static final String DAO_EXCPETION_COMM_NOTES = "EXCEPTION IN COMM NOTES";

    public static final String DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER = "DAO_EXCEPTION_GET_ALERT_FOR_CUSTOMER";
    public static final String DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA = "DAO_EXCEPTION_GET_ALERT_SUBSCRIPTION_DATA";

    public static final String DAO_EXCEPTION_GET_MODEL_DETAILS = "DAO_EXCEPTION_GET_MODEL_DETAILS";
    public static final String DAO_EXCEPTION_PINPOINT_GET_ASSET = "DAO_EXCEPTION_PINPOINT_GET_ASSET";
    public static final String DAO_EXCEPTION_GET_LOOKUP_VALUE = "DAO_EXCEPTION_GET_LOOKUP_VALUE";
    public static final String DAO_EXCEPTION_GET_CONFIG_ALERTS = "DAO EXCEPTION IN GET CONFIG ALERTS";
    public static final String DAO_EXCEPTION_GET_MULTI_USERS = "DAO EXCEPTION IN GET MULTI USERS";
    public static final String DAO_EXCEPTION_GET_USER_EMAIL_ID = "DAO EXCEPTION GET USER EMAIL ID";
    public static final String DAO_EXCEPTION_MASS_APPLY_DELETE = "DAO_EXCEPTION_MASS_APPLY_DELETE";
    public static final String DAO_EXCEPTION_ALERT_HISTORY_POPULATE_DATA = "DAO EXCEPTION GET ALERT HISTORY POPULATE DATA";
    public static final String DAO_EXCEPTION_GET_ALERT_HISTORY_DATA = "DAO EXCEPTION GET ALERT HISTORY DATA";
	public static final String DAO_EXCEPTION_GET_SUBMENU_LIST = "DAO_EXCEPTION_GET_SUBMENU_LIST";

    // Properties for CMU Pass through queue
    public static final String EGA_MQ_HOST_NAME = "EGA_MQ_HOST_NAME";
    public static final String EGA_MQ_PORT = "EGA_MQ_PORT";
    public static final String EGA_MQ_CHANNEL = "EGA_MQ_CHANNEL";
    public static final String EGA_MQ_ADMIN_INPUT_QUEUE = "EGA_MQ_ADMIN_INPUT_QUEUE";
    public static final String EGA_MQ_QUEUE_MANAGER = "EGA_MQ_QUEUE_MANAGER";
    public static final String RECOMM_ID = "recommId";
    public static final String EOA_ALIAS_EXISTS = "EOA_ALIAS_EXISTS";
    
    // Added by Sriram.B(212601214) for SMS feature
    public static final String DAO_EXCEPTION_GET_USER_PHONE_NO = "DAO EXCEPTION GET USER PHONE NO";
    public static final String DAO_EXCEPTION_GET_USER_PHONE_COUNTRY_CODE = "DAO EXCEPTION GET USER PHONE COUNTRY CODE";

	public static final String DAO_EXCEPTION_UPDATE_TOOL_RUN_NEXT = "DAO_EXCEPTION_UPDATE_TOOL_RUN_NEXT";
    public static final String DAO_EXCEPTION_RUN_TOOLS = "DAO_EXCEPTION_RUN_TOOLS";
    
    //Added by Murali Ch
    public static final String DAO_EXCPETION_UPDATE_GEN_OR_COMM_NOTES = "DAO_EXCPETION_UPDATE_GEN_OR_COMM_NOTES";
    
    //Added by Koushik for OHV reports
    public static final String DAO_EXCEPTION_OHV_TRUCK_RX = "DAO_EXCEPTION_OHV_TRUCK_RX";
    public static final String DAO_EXCEPTION_OHV_TRUCK_EVENTS = "DAO_EXCEPTION_OHV_TRUCK_EVENTS";
    public static final String DAO_EXCEPTION_OHV_TRUCK_INFO = "DAO_EXCEPTION_OHV_TRUCK_INFO";
    public static final String DAO_EXCEPTION_OHV_COMM_STATUS = "DAO_EXCEPTION_OHV_COMM_STATUS";
    
    public static final String CUST_FDBK_OBJID = "custFdbkObjId";
	public static final String RECOMMENDATION_CUSTOMIZED = "Rx Customized:";
	
	//Added by Murali C for Turnover report changes
    public static final String CALL_REPORT_BUSS_DECODE_QUERY ="DECODE(BUSNSS_AREA,'Locomotive','Locomotive','Communications (ITS)','Communications','Communications (ITS);Locomotive','Both','Locomotive;Communications (ITS)','Both','Miscellaneous') BUSNSS_AREA";
    public static final String DAO_EXCEPTION_GET_CALL_COUNT_BY_LOCATION = "DAO_EXCEPTION_GET_CALL_COUNT_BY_LOCATION";
    public static final String DAO_EXCEPTION_GET_GET_CUST_BREAK_DOWN_BY_MINS ="DAO_EXCEPTION_GET_GET_CUST_BREAK_DOWN_BY_MINS";
    public static final String DAO_EXCEPTION_GET_CALL_COUNT_BY_BUSSINESS_AREA = "DAO_EXCEPTION_GET_CALL_COUNT_BY_BUSSINESS_AREA";
    public static final String DAO_EXCEPTION_GET_WEEKLY_CALL_COUNT_BY_BUSSINESS_AREA = "DAO_EXCEPTION_GET_WEEKLY_CALL_COUNT_BY_BUSSINESS_AREA";
    public static final String DAO_EXCEPTION_GET_VEH_CALL_COUNT_BY_BUSSINESS_AREA = "DAO_EXCEPTION_GET_WEEKLY_CALL_COUNT_BY_BUSSINESS_AREA";
    public static final String DAO_EXCEPTION_GET_MANUAL_CALL_COUNT = "DAO_EXCEPTION_GET_MANUAL_CALL_COUNT";
    public static final String VP_TABLE_NAME = "GETS_TOOL_VIRTUAL_OUT";
    public static final String MP_AC_TABLE_NAME = "GETS_TOOL_MP_AC_CCA";
    public static final String MP_DC_TABLE_NAME = "GETS_TOOL_MP_DC_CCA";
	public static final String MP_ACTUAL_RPM_PARAM_FIELD_NAME = "mp_1005_n_0_0";
	public static final String MP_DESIRED_RPM_PARAM_FIELD_NAME = "mp_1006_n_0_0";
    public static final String MP_HP_AVAILABLE_PARAM_FIELD_NAME = "MP_1025_N_0_0";
	public static final String STR_DCCCA = "DCCCA";
	public static final String STR_BSS = "BSS";
	public static final String STR_AC6000 = "AC6000";
	public static final String STR_MNS = "MnS";	
	public static final String SQL_VEHICLE_INFO_QUERY = "SELECT veh.objid, ctlcfg.cust_id_mp_source AS controllerid, rmdmodel.family AS family, busorg.meas_sys_id FROM gets_rmd_vehicle veh, gets_rmd_ctl_cfg ctlcfg, gets_rmd_model rmdmodel, gets_rmd_fleet fleet, sa.table_bus_org busorg WHERE veh.vehicle2ctl_cfg = ctlcfg.objid AND veh.vehicle2model = rmdmodel.objid AND ctlcfg.family = rmdmodel.family AND veh.vehicle2fleet = fleet.objid AND busorg.objid = fleet.fleet2bus_org AND veh.objid = :objId";
	public static final String SQL_DATA_FETCH_QUERY_BASE = "WITH t27 AS (SELECT /*+ use_nl(f) leading(f) */ f.* FROM gets_tool_fault f WHERE f.fault2vehicle = :strObjId AND f.offboard_load_date >= SYSDATE - 30) SELECT t27.objid,  TO_CHAR(t27.occur_date,'MM/DD/YYYY HH24:MI:SS')";
	public static final String SQL_PARAMETER_DEFINITIONS_QUERY_BASE = "SELECT cn.objid, cn.parm_type, cn.column_name, NVL(pd.parm_load_table, cn.table_name) as parm_load_table, NVL(pd.parm_load_column, cn.db_column_name) as parm_load_column, NVL(pd.source_uom_id, cn.sdp_source_uom_id) as source_uom_id FROM gets_rmd_parmdef pd, gets_tool_dpd_colname cn WHERE pd.rule_parm_desc (+) = cn.column_name AND cn.parm_type NOT IN ('Geolocation','ATS','Virtual') AND cn.column_name in (";
	public static final String SQL_PARAMETER_DEFINITIONS_QUERY_UNION = " UNION SELECT cn.objid, cn.parm_type, cn.column_name, 'GETS_TOOL_VIRTUAL_OUT' AS parm_load_table, NVL(v.virtual_column_name, cn.db_column_name) AS parm_load_column, cn.sdp_source_uom_id AS source_uom_id FROM gets_tool_dpd_colname cn, gets_tool_virtual v WHERE v.link_dpd_column_name = cn.objid AND v.family_cd = cn.family AND cn.parm_type IN ('Virtual') AND cn.column_name IN (";
	public static final String SQL_UOM_CONVERSION_BASE = "SELECT sourceuom, conversion_exp, tgt.unit_of_measure FROM gets_rmd_unit_of_measure tgt, gets_rmd_uom_conversion uomc, (SELECT objid, uom_type FROM gets_rmd_unit_of_measure WHERE objid in (";
	public static final String SQL_UOM_CONVERSION_END = " ) src WHERE tgt.meas_sys_id = ? AND tgt.uom_type = src.uom_type AND uomc.sourceuom = src.objid AND uomc.targetuom = tgt.objid";
	public static final String MEASUREMENT_SYSYTEM = "Measurement System";
	
	//Added for AHC template apply 
    public static final String DAO_EXCEPTION_GET_AHC_TEMPLATES = "DAO_EXCEPTION_GET_AHC_TEMPLATES";
    public static final String DAO_EXCEPTION_GET_AHC_CONFIGS = "DAO_EXCEPTION_GET_AHC_CONFIGS";
    public static final String DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_AHC = "DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_AHC";
    
    public static final String DAO_EXCEPTION_MSG_ID = "EXCEPTION IN AUTO HC MESSAGE_ID";
	public static final String DAO_EXCEPTION_FCRT = "EXCEPTION IN AUTO HC FAULT CODE RECORD TYPE";
	public static final String DAO_EXCEPTION_TEMPLATE = "EXCEPTION IN AUTO HC TEMPLATES DROPDOWN";
	public static final String DAO_EXCEPTION_TEMPLATE_AHC = "EXCEPTION IN SAVE NEW TEMPLATE DAO LAYER DETAILS";

    public static final String DAO_EXCEPTION_VALIDATE_VEH_BOMS = "DAO_EXCEPTION_VALIDATE_VEH_BOMS";

    public static final String DAO_EXCEPTION_GET_VEH_BOM_DETAILS = "DAO_EXCEPTION_GET_VEH_BOM_DETAILS";
    
    public static final String DAO_EXCEPTION_RX_CHANGE_UPDATE = "DAO_EXCEPTION_RX_CHANGE_UPDATE";
    public static final String DAO_EXCEPTION_RX_CHANGE = "DAO_EXCEPTION_RX_CHANGE";
    public static final String DAO_EXCEPTION_GET_RX_ATTACHMENTS = "DAO_EXCEPTION_GET_RX_ATTACHMENTS";
    public static final String DAO_EXCEPTION_GET_RECOM_CREATEDBY = "DAO_EXCEPTION_GET_RECOM_CREATEDBY";
    public static final String SQL_RX_CHANGE_OVERVIEW_QUERY = "SELECT A.RX_CHANGE_REQ_SEQ_ID, A.USER_ID, A.LOOK_VALUE, A.RX_TITLE_GENERAL, A.SERIAL_NO, A.CASE_ID, A.TITLE, (SELECT CHANGES_SUGGESTED FROM GETS_RMD_RX_CHNG_REQ_DETAILS WHERE RX_CHANGE_REQ_SEQ_ID=a.RX_CHANGE_REQ_SEQ_ID ) CHANGES_SUGGESTED, (SELECT LOOK_VALUE FROM GETS_RMD_RX_CHNG_REQ_DETAILS, GETS_RMD_LOOKUP LOOKUP WHERE RX_CHANGE_REQ_SEQ_ID=a.RX_CHANGE_REQ_SEQ_ID AND RX_REQUEST_STATUS =LOOKUP.OBJID ) REQUEST_STATUS, (SELECT REQUESTOR_NOTES FROM GETS_RMD_RX_CHNG_REQ_DETAILS WHERE RX_CHANGE_REQ_SEQ_ID=a.RX_CHANGE_REQ_SEQ_ID ) REQUESTOR_NOTES, TO_CHAR(A.CREATED_DATE,'MM/DD/YYYY HH24:MI:SS') CREATED_DATEF, A.created_date, LISTAGG(a.model, ',') within GROUP ( ORDER BY A.RX_CHANGE_REQ_SEQ_ID) MODEL, customer, TYPECHANGE, ATTACHMENTS, (SELECT USR.LAST_NAME ||',' ||USR.FIRST_NAME ||'|' ||USR.USER_ID FROM GETS_RMD_RX_CHNG_REQ_DETAILS, GET_USR_USERS USR WHERE RX_CHANGE_REQ_SEQ_ID =a.RX_CHANGE_REQ_SEQ_ID AND TO_CHAR(REQUEST_OWNER) =USR.USER_ID ) REQUEST_OWNER, A.FIRST_NAME, A.LAST_NAME, A.EMAIL_ID, A.VERSION_DRAFT, A.RX_CHANGE_PROCESSING_SEQ_ID, (SELECT REVIEWER_NOTES FROM GETS_RMD_RX_CHNG_PROC_DETAILS WHERE RX_CHANGE_REQ_OBJID = a.RX_CHANGE_REQ_SEQ_ID ) REVIEWER_NOTES, a.WHITE_PAPER_PDF_FILE_NAME, A.WHITE_PAPER_PDF_FILE_PATH, A.OBJID FROM (SELECT DISTINCT RXREQ.RX_CHANGE_REQ_SEQ_ID, USERS.USER_ID, LOOKUP.LOOK_VALUE, RXREQ.RX_TITLE_GENERAL, TSP.SERIAL_NO, RXREQ.CASE_ID, RECOM.TITLE, RXREQ.CREATED_DATE, RMDMODEL.MODEL_GENERAL MODEL, (SELECT customer FROM (SELECT DISTINCT A.RX_CHANGE_REQ_SEQ_ID, LISTAGG(a.CUSTOMER, ',') WITHIN GROUP( ORDER BY a.RX_CHANGE_REQ_SEQ_ID) CUSTOMER FROM (SELECT DISTINCT RXREQA.RX_CHANGE_REQ_SEQ_ID, BUSORG.NAME CUSTOMER FROM GETS_RMD_RX_CHNG_REQ_DETAILS RXREQA, GETS_RMD_VEHICLE VEH, GETS_RMD_RX_CHNG_CUSTOMERS CUST, TABLE_BUS_ORG BUSORG, gets_rmd_model RMDMODEL, GETS_RMD_RX_CHNG_MODELS RXMODEL, TABLE_SITE_PART TSP, GETS_SD_RECOM RECOM, GETS_RMD_LOOKUP LOOKUP, GET_USR_USERS USERS, GETS_RMD_RX_CHNG_PROC_DETAILS RXCHGPROC, GETS_RMD_RX_CHNG_TYPE type, GETS_RMD_RX_CHNG_ATTACHMENTS attachments WHERE RXREQa.RX_CHANGE_REQ_SEQ_ID=CUST.RX_CHANGE_REQ_OBJID AND RXREQa.RX_CHANGE_REQ_SEQ_ID =RXMODEL.RX_CHANGE_REQ_OBJID(+) AND RXREQa.ROAD_NUMBER = VEH.OBJID(+) AND CUST.LINK_CUSTOMER = BUSORG.OBJID AND RXMODEL.LINK_MODEL = RMDMODEL.OBJID(+) AND TSP.OBJID (+) = VEH.VEHICLE2SITE_PART AND RXREQA.RX_TITLE_SPECIFIC = RECOM.OBJID(+) AND RXREQA.REVISION_TYPE = LOOKUP.OBJID AND RXREQA.REQUESTED_BY = USERS.GET_USR_USERS_SEQ_ID AND RXREQa.RX_CHANGE_REQ_SEQ_ID =RXREQ.RX_CHANGE_REQ_SEQ_ID AND RXREQA.RX_CHANGE_REQ_SEQ_ID = RXCHGPROC.RX_CHANGE_REQ_OBJID(+) AND type.RX_CHANGE_REQ_OBJID = RXREQA.RX_CHANGE_REQ_SEQ_ID AND RXREQa.RX_CHANGE_REQ_SEQ_ID = attachments.RX_CHANGE_REQ_OBJID(+) AND RXMODEL.DATA_SOURCE(+) = 'RxChangeReq' AND CUST.DATA_SOURCE = 'RxChangeReq' AND TYPE.DATA_SOURCE = 'RxChangeReq' ";
    public static final String SQL_RX_CHANGE_OVERVIEW_FIRST_INNER_QUERY_END= " )a GROUP BY A.RX_CHANGE_REQ_SEQ_ID ) ) CUSTOMER , (SELECT TYPECHANGE FROM (SELECT DISTINCT RX_CHANGE_REQ_SEQ_ID, LISTAGG(look_value, ',') within GROUP ( ORDER BY RX_CHANGE_REQ_SEQ_ID) TYPECHANGE FROM (SELECT RX_CHANGE_REQ_SEQ_ID, look_value FROM GETS_RMD_RX_CHNG_TYPE type, GETS_RMD_RX_CHNG_REQ_DETAILS req, gets_rmd_lookup lookup WHERE REQ.RX_CHANGE_REQ_SEQ_ID = type.RX_CHANGE_REQ_OBJID AND LOOKUP.OBJID = type.LINK_TYPEOFCHANGE AND REQ.RX_CHANGE_REQ_SEQ_ID =RXREQ.RX_CHANGE_REQ_SEQ_ID AND TYPE.DATA_SOURCE = 'RxChangeReq' )LOOK GROUP BY RX_CHANGE_REQ_SEQ_ID ) ) TYPECHANGE, (SELECT ATTACHMENTS FROM (SELECT DISTINCT RX_CHANGE_REQ_SEQ_ID, LISTAGG(FILE_DET, ',') within GROUP ( ORDER BY RX_CHANGE_REQ_SEQ_ID) ATTACHMENTS FROM (SELECT req.RX_CHANGE_REQ_SEQ_ID, attachments.FILE_NAME ||'~'|| attachments.FILE_PATH as FILE_DET FROM GETS_RMD_RX_CHNG_ATTACHMENTS attachments, GETS_RMD_RX_CHNG_REQ_DETAILS req WHERE REQ.RX_CHANGE_REQ_SEQ_ID = attachments.RX_CHANGE_REQ_OBJID(+) AND REQ.RX_CHANGE_REQ_SEQ_ID =RXREQ.RX_CHANGE_REQ_SEQ_ID AND attachments.FILE_PATH IS NOT NULL AND attachments.FILE_NAME IS NOT NULL )attach GROUP BY RX_CHANGE_REQ_SEQ_ID ) ) ATTACHMENTS, USERS.FIRST_NAME, USERS.LAST_NAME, USERS.EMAIL_ID, RXCHGPROC.VERSION_DRAFT, RXCHGPROC.RX_CHANGE_PROCESSING_SEQ_ID, RXREQ.WHITE_PAPER_PDF_FILE_NAME, RXREQ.WHITE_PAPER_PDF_FILE_PATH,RECOM.OBJID FROM GETS_RMD_RX_CHNG_REQ_DETAILS RXREQ, GETS_RMD_VEHICLE VEH, GETS_RMD_RX_CHNG_CUSTOMERS CUST, TABLE_BUS_ORG BUSORG, gets_rmd_model RMDMODEL, GETS_RMD_RX_CHNG_MODELS RXMODEL, TABLE_SITE_PART TSP, GETS_SD_RECOM RECOM, GETS_RMD_LOOKUP LOOKUP, GET_USR_USERS USERS, GETS_RMD_RX_CHNG_PROC_DETAILS RXCHGPROC, GETS_RMD_RX_CHNG_TYPE type, GETS_RMD_RX_CHNG_ATTACHMENTS attachments WHERE RXREQ.RX_CHANGE_REQ_SEQ_ID=CUST.RX_CHANGE_REQ_OBJID AND RXREQ.RX_CHANGE_REQ_SEQ_ID =RXMODEL.RX_CHANGE_REQ_OBJID(+) AND RXREQ.ROAD_NUMBER = VEH.OBJID(+) AND CUST.LINK_CUSTOMER = BUSORG.OBJID AND RXMODEL.LINK_MODEL = RMDMODEL.OBJID(+) AND TSP.OBJID (+) = VEH.VEHICLE2SITE_PART AND RXREQ.RX_TITLE_SPECIFIC = RECOM.OBJID(+) AND RXREQ.REVISION_TYPE = LOOKUP.OBJID AND RXREQ.REQUESTED_BY = USERS.GET_USR_USERS_SEQ_ID AND RXREQ.RX_CHANGE_REQ_SEQ_ID =RXREQ.RX_CHANGE_REQ_SEQ_ID AND RXREQ.RX_CHANGE_REQ_SEQ_ID = RXCHGPROC.RX_CHANGE_REQ_OBJID(+) AND type.RX_CHANGE_REQ_OBJID = RXREQ.RX_CHANGE_REQ_SEQ_ID AND RXREQ.RX_CHANGE_REQ_SEQ_ID = attachments.RX_CHANGE_REQ_OBJID(+) AND RXMODEL.DATA_SOURCE(+) = 'RxChangeReq' AND CUST.DATA_SOURCE = 'RxChangeReq' AND TYPE.DATA_SOURCE = 'RxChangeReq' ";
    public static final String SQL_RX_CHANGE_OVERVIEW_GROUP_BY_CONDITION=" )A GROUP BY A.RX_CHANGE_REQ_SEQ_ID, A.USER_ID, A.LOOK_VALUE, A.RX_TITLE_GENERAL, A.SERIAL_NO, A.CASE_ID, A.TITLE, A.CREATED_DATE, CUSTOMER, typechange, ATTACHMENTS, A.FIRST_NAME, A.LAST_NAME, A.EMAIL_ID, A.VERSION_DRAFT, a.RX_CHANGE_PROCESSING_SEQ_ID, a.WHITE_PAPER_PDF_FILE_NAME, A.WHITE_PAPER_PDF_FILE_PATH,A.OBJID ORDER BY A.CREATED_DATE DESC";
    public static final String RX_CHANGE_ADMIN_PRIVILEGE = "RX_CHANGE_ADMIN_PRIVILEGE";
    //Changed as part of DE81941: IT Task: Found below issues in White Paper PDF document generated on Approving RxChangeRequest
    public static final String SQL_RX_CHANGE_ADMIN_QUERY= "WITH temp_mdl_nm AS ( SELECT RX_CHANGE_PROCESSING_SEQ_ID, SUBSTR(XMLAGG(xmlelement(name \"E\", TRIM(model.objid||'|'||model.model_name||','))).extract('//text()').GETCLOBVAL(),1) mdl_name FROM GETS_RMD_RX_CHNG_PROC_DETAILS p, GETS_RMD_MODEL model, GETS_RMD_RX_CHNG_MODELS m WHERE p.RX_CHANGE_REQ_OBJID =m.RX_CHANGE_REQ_OBJID AND p.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId AND m.DATA_SOURCE ='RxChangeAdminRequest' AND model.objid =m.link_model GROUP BY RX_CHANGE_PROCESSING_SEQ_ID ORDER BY 1 ), temp_cust_nm AS (SELECT RX_CHANGE_PROCESSING_SEQ_ID, SUBSTR(XMLAGG(xmlelement(name \"E\", TRIM(customer.objid||'~'||customer.NAME||'|'))).extract('//text()').GETCLOBVAL(),1) cust_name FROM GETS_RMD_RX_CHNG_PROC_DETAILS p, GETS_RMD_RX_CHNG_CUSTOMERS cust, TABLE_BUS_ORG customer WHERE p.RX_CHANGE_REQ_OBJID = cust.RX_CHANGE_REQ_OBJID AND p.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId AND cust.DATA_SOURCE ='RxChangeAdminRequest' AND customer.objid = cust.link_customer GROUP BY RX_CHANGE_PROCESSING_SEQ_ID ORDER BY 1 ), TEMP_TITLE as ( select RX_CHANGE_PROCESSING_SEQ_ID , SUBSTR(XMLAGG(xmlelement(name \"E\", TRIM(RECOM.OBJID||'~'||RECOM.TITLE||'|'))).extract('//text()').GETCLOBVAL(),1) title FROM GETS_RMD_RX_CHNG_PROC_DETAILS p, GETS_RMD_RX_CHNG_RX_TITLE rx, GETS_SD.GETS_SD_RECOM recom WHERE p.RX_CHANGE_PROCESSING_SEQ_ID = rx.RX_CHANGE_PROCESSING_DET_OBJID AND P.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId AND RECOM.OBJID =RX.LINK_RX_TITLE GROUP BY RX_CHANGE_PROCESSING_SEQ_ID order by 1 ), temp_additional_reviewer AS (SELECT DISTINCT RX_CHANGE_PROCESSING_SEQ_ID , objid, LISTAGG(reviewer, ',') WITHIN GROUP( ORDER BY reviewer) over(partition BY RX_CHANGE_PROCESSING_SEQ_ID ) reviewer FROM ( SELECT DISTINCT RX_CHANGE_PROCESSING_SEQ_ID , p.RX_CHANGE_REQ_OBJID objid, trim(USERS.GET_USR_USERS_SEQ_ID) reviewer FROM GETS_RMD_RX_CHNG_PROC_DETAILS p, GETS_RMD_RX_CHNG_REVIEWERS rev, GET_USR_USERS USERS WHERE p.RX_CHANGE_PROCESSING_SEQ_ID = rev.RX_CHANGE_PRCSNG_RVWRS_OBJID AND p.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId AND users.GET_USR_USERS_SEQ_ID =rev.LINK_REVIEWER ORDER BY 2 ) ), temp_type_change AS (SELECT DISTINCT RX_CHANGE_PROCESSING_SEQ_ID , objid, LISTAGG(typechange, ',') WITHIN GROUP( ORDER BY typechange) over(partition BY RX_CHANGE_PROCESSING_SEQ_ID ) typechange FROM (SELECT DISTINCT RX_CHANGE_PROCESSING_SEQ_ID , p.RX_CHANGE_REQ_OBJID objid, trim(LOOKUP.OBJID ||'~' ||LOOKUP.look_value) typechange FROM GETS_RMD_RX_CHNG_PROC_DETAILS p, GETS_RMD_RX_CHNG_TYPE TYPE, GETS_RMD_LOOKUP LOOKUP WHERE p.RX_CHANGE_REQ_OBJID = TYPE.RX_CHANGE_REQ_OBJID AND p.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId AND TYPE.DATA_SOURCE ='RxChangeAdminRequest' AND LOOKUP.OBJID =TYPE.LINK_TYPEOFCHANGE ORDER BY 2 ) ) SELECT main.RX_CHANGE_PROCESSING_SEQ_ID, main.RX_CHANGE_REQ_OBJID, main.NEW_RX_REQUIRED, main.MATERIAL_CHANGE_REQUIRED, main.TRIGGER_LOGIC_CHANGED, main.UNFAMILIAR_SYSTEM_CHANGE, main.NUMBER_OF_RXS_ATTACHMENT_ADDED , main.CHANGES_SUMMARY, main.REVIEWED_AND_APPROVED_BY, main.NOTES_INTERNAL, main.REVIEWER_NOTES , TO_CHAR(main.TARGET_IMPLEMENTATION_DATE,'MM/DD/YYYY'), mdl_name, cust_name, title, reviewer, typechange FROM GETS_RMD_RX_CHNG_PROC_DETAILS main, temp_mdl_nm, temp_cust_nm, temp_title, temp_additional_reviewer, temp_type_change WHERE main.RX_CHANGE_PROCESSING_SEQ_ID = temp_mdl_nm.RX_CHANGE_PROCESSING_SEQ_ID(+) AND main.RX_CHANGE_PROCESSING_SEQ_ID = temp_cust_nm.RX_CHANGE_PROCESSING_SEQ_ID(+) AND main.RX_CHANGE_PROCESSING_SEQ_ID = temp_title.RX_CHANGE_PROCESSING_SEQ_ID(+) AND main.RX_CHANGE_PROCESSING_SEQ_ID = temp_additional_reviewer.RX_CHANGE_PROCESSING_SEQ_ID(+) AND main.RX_CHANGE_PROCESSING_SEQ_ID = temp_type_change.RX_CHANGE_PROCESSING_SEQ_ID(+) AND main.RX_CHANGE_PROCESSING_SEQ_ID = :rxChangeProcObjId AND main.RX_CHANGE_REQ_OBJID = :rxChangeReqObjId";
//OMDKM-2016 - Added by Koushik for RX Translation
    public static final String DAO_EXCEPTION_GET_TRANSLATE_LANGUAGE = "DAO_EXCEPTION_GET_TRANSLATE_LANGUAGE";
    public static final String DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY = "DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY";
    public static final String LANG_CHINESE = "Chinese";
	public static final String LANG_FRENCH = "French";
	public static final String LANG_SPANISH = "Spanish";
	public static final String LANG_RUSSIAN = "Russian";
	public static final String LANG_INDONESIA = "Bahasa Indonesian";
	public static final String UNICODE_STORE_YES = "Y";
	public static final String UNICODE_STORE_NO = "N"; 
	public static final String LANG_PORTUGUESE = "Portuguese";
	public static final String LANG_HINDI= "Hindi";
	public static final String DAO_EXCEPTION_GET_EOA_ONSITE_PREVILIGE_ROLES = "DAO_EXCEPTION_GET_EOA_ONSITE_PREVILIGE_ROLES";
	
	
	public static final String RXCHANGE_NEW_RX = "New RX";
	public static final String RXCHANGE_EXISTING_RX = "Existing RX";
	public static final String RXCHANGE_RX_TITLE_NOTE1 = "NOTE: There are a total of ";
	public static final String RXCHANGE_RX_TITLE_NOTE2 =" Rx Titles impacted with this change.  Please see the full list in the Rx Titles Impacted section below.";
	public static final String STRING_YES = "Yes";
	public static final String DAO_EXCEPTION_RX_TRANS_TASK = "DAO_EXCEPTION_RX_TRANS_TASK";
	public static final String DAO_EXCEPTION_RX_TRANS_HIS = "DAO_EXCEPTION_RX_TRANS_HIS";
	public static final String DAO_EXCEPTION_RX_TRANS_DETAILS = "DAO_EXCEPTION_RX_TRANS_DETAILS";
	public static final String GE_LOGO="img/app/ge_logo.png";
	 public static final String DEACTIVATED = "Deactivated";

    public static final String DAO_EXCEPTION_GET_NO_CM_ROLES = "DAO_EXCEPTION_GET_NO_CM_ROLES";
    public static final String DAO_EXCEPTION_GET_LOOKUP_VALUE_TOOLTIP = "DAO_EXCEPTION_GET_LOOKUP_VALUE_TOOLTIP";
    
    //Added fro RCI Apply and Delete

    public static final String DAO_EXCEPTION_GET_RCI_TEMPLATES = "DAO_EXCEPTION_GET_RCI_TEMPLATES";
    public static final String DAO_EXCEPTION_VALIDATE_VEHICLE_TEMPLATE_STATUS = "DAO_EXCEPTION_VALIDATE_VEHICLE_TEMPLATE_STATUS";
    public static final String SQL_RX_CHNG_AUDIT_TRAIL_QUERY = "SELECT ACTIVITY, PERFORMED_BY, TO_CHAR(PERFORMED_ON,'MM/DD/YYYY HH24:MI:SS') PERFORMED_ON, USR.LAST_NAME||','||USR.FIRST_NAME, L.LOOK_VALUE, COMMENTS FROM GETS_RMD.RX_CHNG_AUDIT_TRAIL_INFO RXAUDIT, GET_USR_USERS USR, GETS_RMD_LOOKUP L WHERE RXAUDIT.ACTIVITY=L.OBJID AND RXAUDIT.PERFORMED_BY = USR.USER_ID(+) AND RXAUDIT.RX_CHANGE_REQ_OBJID = :objId ORDER BY PERFORMED_ON DESC";
    public static final String SQL_RX_CHNG_ESCALATION_QUERY = "SELECT  RX_CHANGE_REQ_SEQ_ID, LISTAGG(CUSTOMER, ',') WITHIN GROUP (ORDER BY RX_CHANGE_REQ_SEQ_ID) CUSTOMER, ROAD_NUMBER, TITLE, REQUESTOR, CREATED_DATE, EMAIL_ID from (SELECT DISTINCT  RX_CHANGE_REQ_SEQ_ID, BUSORG.NAME CUSTOMER, ROAD_NUMBER, RECOM.TITLE, REQUESTED_BY, USR.LAST_NAME||','||USR.FIRST_NAME||'|'||USR.USER_ID REQUESTOR, TO_CHAR(rx.CREATED_DATE,'MM/DD/YYYY HH24:MI:SS') CREATED_DATE, USR.EMAIL_ID FROM GETS_RMD_RX_CHNG_REQ_DETAILS RX, GETS_RMD_RX_CHNG_CUSTOMERS CUST, TABLE_BUS_ORG BUSORG, GETS_RMD_LOOKUP L, GETS_SD_RECOM RECOM, GET_USR_USERS USR WHERE RX.RX_CHANGE_REQ_SEQ_ID=CUST.RX_CHANGE_REQ_OBJID AND CUST.LINK_CUSTOMER           = BUSORG.OBJID(+) AND RX.RX_REQUEST_STATUS=L.OBJID AND RX.RX_TITLE_SPECIFIC     = RECOM.OBJID(+) AND RX.REQUESTED_BY =USR.GET_USR_USERS_SEQ_ID(+) AND LOOK_VALUE='Submitted' AND RX.LAST_UPDATED_DATE < SYSDATE-30) group by   RX_CHANGE_REQ_SEQ_ID, ROAD_NUMBER, TITLE, REQUESTOR, CREATED_DATE, EMAIL_ID ORDER BY CREATED_DATE DESC";
    public static final String DAO_EXCEPTION_NEWS = "EXCEPTION IN SAVE NEWS DAO LAYER DETAILS";
	public static final String DAO_EXCEPTION_GET_TEMPLATE_REPORT="DAO_EXCEPTION_GET_TEMPLATE_REPORT";
	public static final String DAO_EXCPETION_RX_RESEARCH_GET_SOLUTION = "DAO_EXCPETION_RX_RESEARCH_GET_SOLUTION";
	public static final String DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA = "DAO_EXCPETION_RX_RESEARCH_GET_GRAPH_DATA";
	public static final String DAO_EXCEPTION_GET_LOCO_ID= "EXCEPTION IN GET LOCO ID Method";
	public static final String DAO_EXCEPTION_RE_NOTIFY_ASSOCIATION = "DAO_EXCEPTION_RE_NOTIFY_ASSOCIATION";
    public static final String DAO_EXCEPTION_UPDATE_RCI_TEMPLATE = "DAO_EXCEPTION_UPDATE_RCI_TEMPLATE";
    public static final String DAO_EXCEPTION_CHECK_IF_RCI_TEMPLATE_EXISTS = "DAO_EXCEPTION_CHECK_IF_RCI_TEMPLATE_EXISTS";
    public static final String DAO_EXCEPTION_SAVE_RCI_TEMPLATE = "DAO_EXCEPTION_SAVE_RCI_TEMPLATE"; 
    public static final String DUMMY = "DUMMY"; 
    public static final String DAO_EXCEPTION_GET_BACKGROUND_IMAGE = "EXCEPTION IN GET BACKGROUND IMAGE URL";
    
	
    /** private constructor
     */
    private RMDServiceConstants() {
    }

}