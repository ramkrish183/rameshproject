/**
 * ============================================================
 * Classification: GE Confidential
 * File : OMDConstants.java
 * Description :
 * Package : com.ge.trans.rmd.services.common
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 * Copyright (C) 2011 General Electric Company. All rights reserved
 * ============================================================
 */
package com.ge.trans.rmd.common.constants;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class OMDConstants {

    public static final String PROPERTIES_FILE_NAME = "com/ge/trans/rmd/common/ErrorCode.properties";
    public static final String EMPTY_STRING = "";

    public static final String NORECORDFOUNDEXCEPTION = "NO_RECORD_FOUND_EXCEPTION";
    public static final String INTEGER_PARSE_EXCEPTION = "INTEGER_PARSE_EXCEPTION";
    public static final String LONG_PARSE_EXCEPTION = "LONG_PARSE_EXCEPTION";
    public static final String FLOAT_PARSE_EXCEPTION = "FLOAT_PARSE_EXCEPTION";
    public static final String DATE_FORMAT_NOT_CORRECT = "IMPROPER_DATE_FORMAT";
    public static final String NO_PARAMETERS_FOUND = "NO_PARAMETERS_FOUND";
    public static final String INVALID_SEARCH_CRITERIA = "INVALID_SEARCH_CRITERIA";
    public static final String CHINESE_LANGUAGE = "zh";
    public static final String GENERALEXCEPTION = "GENERAL_EXCEPTION";
    public static final String USERID_NOT_PROVIDED = "USERID_NOT_PROVIDED";
    public static final String CASETITLE_NOT_PROVIDED = "CASETITLE_NOT_PROVIDED";
    public static final String USERLANGUAGE_NOT_PROVIDED = "USERLANGUAGE_NOT_PROVIDED";
    public static final String LANGUAGE_NOT_PROVIDED = "LANGUAGE_NOT_PROVIDED";
    public static final String CASEID_NOT_PROVIDED = "CASEID_NOT_PROVIDED";
    public static final String CASETYPE_NOT_PROVIDED = "CASETYPE_NOT_PROVIDED";
    public static final String CASE_ID_NOT_PROVIDED = "CASE_ID_NOT_PROVIDED";
    public static final String QUERY_PARAMETERS_NOT_PASSED = "QUERY_PARAMETERS_NOT_PASSED";
    public static final String SOLUTION_ID_NOT_PROVIDED = "SOLUTION_ID_NOT_PROVIDED";
    public static final String GETTING_NULL_REQUEST_OBJECT = "GETTING_NULL_REQUEST_OBJECT";
    public static final String FAULT_CODE_NOT_PROVIDED = "FAULT_CODE_NOT_PROVIDED";
    public static final String EMPTY_FAULT_CLASSIFICATION_DETAILS = "EMPTY_FAULT_CLASSIFICATION_DETAILS";
    public static final String RXDELVID_NOT_PROVIDED = "RXDELVID_NOT_PROVIDED";
    public static final String LISTNAME_NOT_PROVIDED = "LISTNAME_NOT_PROVIDED";
    public static final String SOLUTION_STATUS_NOT_PROVIDED = "SOLUTION_STATUS_NOT_PROVIDED";
    public static final String URGENCY_VALUE_NOT_PROVIDED = "URGENCY_VALUE_NOT_PROVIDED";
    public static final String ESTREPAIR_VALUE_NOT_PROVIDED = "ESTREPAIR_VALUE_NOT_PROVIDED";
    public static final String USERID_PASSWORD_NOT_PROVIDED = "USERID_PASSWORD_NOT_PROVIDED";
    public static final String TRACKINGID_NOT_PROVIDED = "TRACKINGID_NOT_PROVIDED";
    public static final String RULEID_NOT_PROVIDED = "RULEID_NOT_PROVIDED";

    public static final String INVALID_ASSET_NUMBER = "INVALID_ASSET_NUMBER";
    public static final String INVALID_LANGUAGE = "INVALID_LANGUAGE";
    public static final String INVALID_VALUE = "INVALID_VALUE";

    public static final String INVALID_DAYS = "INVALID_DAYS";
    public static final String INVALID_MESSAGE_STATUS = "INVALID_MESSAGE_STATUS";
    public static final String INVALID_MESSAGE_TYPE = "INVALID_MESSAGE_TYPE";
    public static final String INVALID_MODE = "INVALID_MODEL";
    public static final String INVALID_USER_LANGUAGE = "INVALID_USER_LANGUAGE";

    public static final String USERID = "userID";
    public static final String CM_ALIAS_NAME = "cmAliasName";
    public static final String LANGUAGE = "language";
    public static final String USERLANGUAGE = "userLanguage";
    public static final String ALL = "All";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    public static final String PROXIMITY_STATUS_A = "A";

    public static final String DEFAULT_LANGUAGE = "en";
    public static final String DEFAULT_USERNAME = "admin";

    public static final String NULL_HEADER_LANGUAGE = "NULL_HEADER_LANGUAGE";
    public static final String NULL_HEADER_USERID = "NULL_HEADER_USERID";
    public static final String NULL_HEADER_USERLANGUAGE = "NULL_HEADER_USERLANGUAGE";

    // kep entries

    public static final String DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";
    public static final String TRACKING_ID = "trackingId";
    public static final String PATTERN_SEQ_ID = "patternSeqId";
    public static final String CREATED_BY = "createdBy";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String RUN_NAME = "runName";
    public static final String STATUS = "status";
    public static final String PATTERN_NAME = "patternName";
    public static final String PATTERN_CATEGORY = "patternCategory";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String CREATE_DATE = "createDate";
    public static final String BASEDATARESPONSETYPE = "baseDataResponseType";
    public static final String LIST_NAME = "listName";
    public static final String CUSTOMER_ID = "customerId";
    public static final String ASSET_NUMBER = "assetNumber";
    public static final String ASSET_GROUP_NAME = "assetGrpName";
    public static final String RULE_ID = "ruleID";
    public static final String RS_RULE_ID = "ruleID";
    public static final String RULE_TYPE = "ruleType";
    public static final String RULE_TITLE = "ruleTitle";
    public static final String TO_DATE_FIELD = "dtTo";
    public static final String DAYS = "days";
    public static final String NO_OF_DAYS = "days";
    public static final String PATTERN = "pattern";
    public static final String KPI_NAME = "kpiName";
    // Added for sprint 8 websevices changes
    public static final String FAULT_CODE = "faultCode";
    public static final String SUB_ID = "subId";
    public static final String SERVICE_NAME = "serviceName";

    public static final String LAST_UPDATED_BY = "lastUpdatedBy";
    public static final String RX_TITLE = "rxTitle";
    public static final String SOLUTION_NOTES = "solutionNotes";
    public static final String SOLUTION_TITLES = "solutionTitles";

    public static final String FAMILY = "family";
    public static final String CLONERULE = "cloneRule";
    public static final String CLONE = "N";
    public static final String RULE_LEVEL = "level";
    public static final String RULE_SIMPLE = "simple";
    public static final String RULE_COMPLEX = "complex";
    public static final String OMD_RULE_TYPE = "OMD_RULE_TYPE";
    public static final String OMD_RULE_STATUS = "OMD_RULE_STATUS";
    public static final String OMD_SUBSYSTEM = "SUBSYSTEM";
    public static final String RULE_VERSION = "OMD_RULE_VERSION";
    public static final String NULL_SOLUTION_ID_STATUS = "NULL_SOLUTION_ID_STATUS";
    public static final String EXPLODE_ROR = "explodeROR";
    // Added while code cleanup changes
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String LOCATION_NAME = "locationName";
    public static final String NUMBER_OF_DAYS = "noOfDays";
    public static final String LIST_DESCPT = "listDescription";
    public static final String LOCATION_ID = "locationId";
    public static final String SITE_ADDRESS1 = "siteAddress1";
    public static final String SITE_ADDRESS2 = "siteAddress2";
    public static final String CITY = "city";
    public static final String STATE = "state";
    public static final String COUNTRY = "country";
    public static final String ZIPCODE = "zipCode";
    public static final String TIMEZONE = "timeZone";
    public static final String INCLUDE_INACTIVE = "includeInactive";
    public static final String LOCATION_TYPE = "locationType";
    public static final String CUSTOMER = "customer";
    public static final String FLEET = "fleet";
    public static final String MODEL = "model";
    public static final String TEMPLATE = "template";
    public static final String PROXYMITY_DESC = "proximityDesc";
    public static final String PROXIMITY_LABEL = "proximityLabel";
    public static final String CONFIG = "config";
    public static final String UNIT_NUMBER = "unitNumber";
    public static final String GROUP_NAME = "groupName";
    public static final String STARTROW = "startrow";
    public static final String ENDROW = "endrow";
    public static final String SEARCH_BY = "searchBy";
    public static final String MESSAGE_TYPE = "messageType";
    public static final String MESSAGE_STATUS = "messageStatus";
    public static final String IS_HEALTH_CHECK = "isHealthCheck";
    public static final String SORT_OPTIONS = "sortOptions";
    public static final String SAMPLE_RATE = "sampleRate";
    public static final String NO_OF_POST_SAMPLES = "noOfPostSamples";
    public static final String MP_NUMBERS = "mpNumbers";
    public static final String DAYS_SELECTED = "daysSelected";
    public static final String MIN_FAULT_ID = "minFaultId";
    public static final String MAX_FAULT_ID = "maxFaultId";
    public static final String CALENDAR = "calendar";
    public static final String MINUTES = "minutes";
    public static final String SORT_SELECTION = "sortSelection";
    public static final String CASE_SELECTION = "caseSelection";
    public static final String RULE_DEFINITION_ID = "ruleDefinitionId";
    public static final String JDPADRADIO = "JDPADRadio";
    public static final String CASE_ID = "caseid";
    public static final String TRACE_ID = "traceID";
    public static final String TESTER_ID = "testerID";
    public static final String MANUAL_RUN_ID = "manualRunID";
    public static final String RUN_RECREATOR_ID = "runrecreatorID";
    public static final String TEMPLATE_ID = "templateId";
    public static final String LOOKUP_NAME = "lookupName";
    public static final String DWQ = "DWQ";
    public static final String CLOSED = "Closed";
    public static final String SOLUTION_ID = "solutionID";
    public static final String ASSET_NUMBER_FROM = "assetNumberFrom";
    public static final String ASSET_NUMBER_TO = "assetNumberTo";
    public static final String SELECT = "Select";
    public static final String NOTE_TYPE = "noteType";
    public static final String CREATION_DATE_FROM = "creationDateFrom";
    public static final String CREATION_DATE_TO = "creationDateTo";
    public static final String KEYWORD = "keyword";
    public static final String SOLUTION_FEEDBACK = "solutionFeedback";
    public static final String SOLUTION_TITLE = "solutionTitle";
    public static final String CASE_CREATION_FROM_DATE = "caseCreationFromDate";
    public static final String CASE_CREATION_TO_DATE = "caseCreationToDate";
    public static final String SOLUTION_STATUS = "solutionStatus";
    public static final String ERROR_CATEGORY = "errorCategory";
    public static final String NO_OF_DAYS_LOOKBACK = "noOfDaysLookback";
    public static final String OUT_BND_MSG_ID = "outbndMsgId";
    public static final String IS_RULE_FIRED = "isRuleFired";
    public static final String IS_ACTIVE_RULES = "isActiveRules";
    public static final String SOLUTION_VERSION = "solutionVersion";
    public static final String RULES = "rules";
    public static final String VIRTUAL_TYPE = "virtualType";
    public static final String VIRTUAL_NAME = "virtualName";
    public static final String N = "N";
    public static final String TESTER_TRACE_ID = "testerTraceID";
    public static final String RULE_TESTER = "Rule_Tester";
    public static final String RUN_CREATE_TRACK_ID = "runReCreateTrackID";
    public static final String RUN_RECREATOR = "Run_Recreator";
    public static final String MANUAL_RUN = "manualRun";
    public static final String ON_THE_FLY_DS = "On_the_fly_DS";
    public static final String LOOK_BACK_WEEKS = "lookBackWeeks";
    public static final String TYPE = "type";
    public static final String ROR = "ROR";
    public static final String CREATED_SINCE = "createdSince";
    public static final String RECOMMENDATION = "recommendation";
    public static final String ACTIVATE_BY = "activateBy";
    public static final String ACTIVATE_SINCE = "activateSince";
    public static final String IS_ACTIVE = "isActive";
    public static final String VERSION = "version";
    public static final String LAST_UPDATED_FROM_DATE = "lastUpdatedFromDate";
    public static final String LAST_UPDATED_TO_DATE = "lastUpdatedToDate";
    public static final String CREATED_BY_FROM_DATE = "createdByFromDate";
    public static final String CREATED_BY_TO_DATE = "createdByToDate";
    public static final String SUB_SYSTEM = "subSystem";
    public static final String BLN_DEFAULT_LOAD = "blnDefaultLoad";
    public static final String NUMBER = "1";
    public static final String LKBK_WEEKS = "lkbkweeks";
    public static final String METRICS_DAYS = "metricsDays";
    public static final String ASSET_SERVICE = "/assetservice";
    public static final String GET_MODELS = "/getModels";
    public static final String GET_VEHICLE_COMMON_STATUS = "/getVehicleCommStatus";
    public static final String GET_REQUEST_HISTORY = "/getRequestHistory";
    public static final String GET_FAULTS = "/getFaults/assetNumber/{assetNumber}/assetGrpName/{assetGrpName}/customerId/{customerId}";
    public static final String REQUEST_HEALTH_CHECK = "/requestHealthCheck";
    /**
     * Added for Health Check
     */
    public static final String VIEW_HC_REQUEST_HISTORY = "/viewRequestHistory";
    public static final String GET_ASSETHCMPGROUPS = "/getAssetHCMPGroups";
    public static final String IS_HC_AVAILABLE = "/IsHCAvailable";
    public static final String GET_HC_ASSET_TYPE = "/getHCAssetType";
    public static final String SUBMIT_HC_REQUEST = "/submitHealthCheckRequest";
    public static final String GET_MCS_WS_ATTRIBUTE = "/getSendMessageAttributes";
    public static final String GET_HC_CUST_RNH_DETAILS = "/getCustrnhDetails";

    public static final String GET_FAULTS_CASEID = "/getFaults/caseid/{caseid}";
    public static final String GET_FAULTS_TRACEID = "/getFaults/traceID/{traceID}";
    public static final String GET_FAULTS_TESTERID = "/getFaults/testerID/{testerID}";
    public static final String GET_FAULTS_MANUAL_RUNID = "/getFaults/manualRunID/{manualRunID}";
    public static final String GET_FAULTS_RUN_RECREATOR_ID = "/getFaults/runrecreatorID/{runrecreatorID}";
    public static final String GET_FLEETS = "/getFleets";
    public static final String GET_ASSETS = "getAssets";
    public static final String GET_SERVICES = "/getServices";
    public static final String GET_USER_CUSTOMERS = "/getUserCustomers/userId/{userId}";
    public static final String GET_ALL_CUSTOMERS = "/getAllCustomers";
    public static final String GET_CONFIGURATIONS = "/getConfigurations";
    public static final String GET_PARAMETERS = "/getParameters";
    public static final String GET_SDPVALUES = "/getSDPValues";
    public static final String GET_ASSET_LOCATION = "/getAssetLocation";
    public static final String GET_ASSET_LOCATION_LITE = "/getAssetLocationLite";
    public static final String GET_ASSETS_FOR_ROLE = "/getAssetsForRole";
    public static final String GET_FAMILIES = "/getFamilies";
    public static final String USERId = "userId";
    public static final String LOOKUP_SEQ_ID = "lookupSeqId";
    public static final String ADMIN_SERVICE = "/adminservice";
    public static final String GET_PROXIMITY_DETAILS = "/getProximityDetails";
    public static final String GET_SYSTEM_TIMEZONE = "/getSystemTimeZones";
    public static final String GET_LOCATIONS_LOCATION_ID = "/getLocations/locationId/{locationId}";
    public static final String GET_CONTACTS = "/getContacts";
    public static final String GET_PURGE_HISTORY = "/getPurgeHistory";
    public static final String GET_APLLI_PARAMETER = "/getApplicationParameters";
    public static final String GET_USER_DETAILS_USERId = "/getUsersDetails/userId/{userId}";
    public static final String GET_USERS_DETAILS = "/getUsersDetails";
    public static final String GET_CASE_TYPE = "/getCaseType";
    public static final String GET_EOA_CASE_TYPE = "/getEoaCaseType";
    public static final String SAVE_USER_DETAILS = "/saveUserDetails";
    public static final String SAVE_PROXIMITY_DATAILS = "/saveProximityDetails";
    public static final String GET_LOCATIONS = "/getLocations";
    public static final String CREATE_LOCATION = "/createLocation";
    public static final String CREATE_CONTACT = "/createContact";
    public static final String RESET_PASSWORD = "/resetPassword";
    public static final String SAVE_USER = "/saveUser";
    public static final String ADD_FILTER = "/addFilter";
    public static final String SAVE_APPLICATION_PARAMETER = "/saveApplicationParameters";
    public static final String GET_TEMPLATE_DETAILS = "/getTemplateDetails/templateId/{templateId}";
    public static final String GET_TEMPLATES = "/getTemplates";
    public static final String GET_MASTER_PARAM = "/getMasterParam";
    public static final String GET_VIEWS = "/getViews";
    public static final String GET_NEW_MASTER_PARAM = "/getNewMasterParam";
    public static final String APPLY_TEMPLATE = "/applyTemplate";
    public static final String SAVE_MASTER_TEMPLATE = "/saveMasterTemplate";
    public static final String SAVE_TEMPLATE = "/saveTemplate";
    public static final String DELETE_APPL_PARAM = "/deleteApplicationParameter/lookupSeqId/{lookupSeqId}";
    public static final String DELETE_APPL_PARAM_LOOKNAME = "/deleteApplicationParameter/lookupName/{lookupName}";
    public static final String SAVE_USER_PREFERENCE = "/saveUserPreferences";
    public static final String GET_LOOKUP_VALUES = "/getLookupValues/listName/{lookupName}";
    public static final String GET_LOOKUP_VALUES_LIST = "/getLookupValues";

    public static final String GET_INSTALLED_PRODUCT = "/getInstalledProduct";
    public static final String ASSET_SERVICE_GET_RDR_NOTIFICATIONS = "/getRDRNotifications";
    public static final String ASSET_SERVICE_UPDATE_RDR_NOTIFICATION = "/updateRDRNotification";
    public static final String ASSET_SERVICE_INSERT_RDR_NOTIFICATION = "/insertRDRNotification";
    public static final String GET_RDR_NOTIFICATIONS_USER_ID = "userId";
    
    /* CONSTANTS ADDED FOR SOLUTION RESOURCE */
    public static final String SOLUTION_SERVICE = "/solutionservice";
    public static final String GET_SOLUTION_PARAMETERS = "/getSolutionParameters";
    public static final String GET_SOLUTION_EXE_DETAILS = "/getSolutionExecutionDetails/rxCaseId/{rxCaseId}";
    public static final String RX_CASE_ID = "rxCaseId";
    public static final String RX_SCORE_CODE = "rxScoreCode";
    public static final String RX_FEEDBACK = "rxFeedback";
    public static final String GET_SOLUTIONS = "/getSolutions";
    public static final String URGENCY = "urgency";
    public static final String EST_REPAIR = "estRepair";
    public static final String SOLUTION_TYPE = "solutionType";
    public static final String LOCO_IMPACT = "locoImpact";
    public static final String EXPORT_CONTROL = "exportControl";
    public static final String IS_DEFAULT_LOAD = "isDefaultLoad";
    public static final String GET_WORST_URGENCY = "/getWorstUrgency";
    public static final String ADD_SOLUTION = "/addSolution";
    public static final String UPDATE_SOLUTION = "/updateSolution";
    public static final String SAVE_SOLUTION_EXE_DETAILS = "/saveSolutionExecutionDetails";
    public static final String SAVE_SOLUTION_FEEDBACK = "/saveSolutionFeedback";
    public static final String GET_SOLUTION_INFORM = "/getSolutionsInformation";
    public static final String GET_SOLUTION_LASTUPDATE_BY = "/getSolutionsLastUpdatedBy";
    public static final String GET_TASK_DETAILS = "/getTaskDetails";
    public static final String RX_ID = "rxId";
    // for merge
    public static final String MERGE_TO_ID = "mergedTo";
    public static final String MERGED_TO = "/mergeRx/customerId/{customerId}/rxId/{rxId}/caseId/{caseId}/assetNumber/{assetNumber}/assetGrpName/{assetGrpName}/mergedTo/{mergedTo}/userId/{userId}/toolId/{toolId}/toolObjId/{toolObjId}/ruleDefinitionId/{ruleDefinitionId}/toCaseId/{toCaseId}";
    public static final String GET_SOLUTION_DTLS_SOLUTION_ID = "/getSolutionDetails/solutionId/{solutionId}";
    public static final String GET_RX_DETAILS = "/getRxDetails/solutionId/{solutionId}";
    public static final String GET_RX_TASK_DETAILS = "/getRxTaskDetails/solutionId/{solutionId}";
    public static final String SOLUTION_Id = "solutionId";
    public static final String FROM_PAGE = "fromPage";
    public static final String UPDATE_SOLUTION_STATUS = "/updateSolutionStatus";
    public static final String RX_SUB_SYSTEM = "subsystem";
    public static final String KEP_MODEL_LIST = "KEP_MODEL_LIST";
    public static final String KEP_MODELS = "KEP_MODELS";
    public static final String GET_RX_ACTIVE_RULES_DETAILS = "/getActiveRulesInformation/solutionId/{solutionId}";

    public static final int NUMERIC = 1;
    public static final int AlPHA_NUMERIC = 2;
    public static final int ALPHABETS = 3;
    public static final int VALID_DATE = 4;
    public static final int ALPHA_NUMERIC_HYPEN = 5;
    public static final int ALPHA_UNDERSCORE = 6;
    public static final int ALPHA_NUMERIC_UNDERSCORE = 7;
    public static final int NUMERIC_HYPHEN = 8;
    public static final int ALPHA_BRACKET = 9;
    public static final int ALPHA_AMPERSAND = 10;
    public static final int DOUBLE_HYPHEN = 11;
    public static final int SPECIAL_CHARACTER = 14;
    public static final String COMMA = ",";
    public static final String CAP = "//^";

    public static final String all = "ALL";
    public static final String REPAIR = "estRepair";
    public static final String LOCOMOTIVE = "locoImpact";
    public static final String LAST_UPDATED = "lastUpdatedBy";
    public static final String DEFAULT_LOAD = "isDefaultLoad";

    public static final String GETSUBSYSTEMVALUES = "/getSubsystemValues";
    public static final String GETMODELVALUES = "/getModelValues";
    public static final String GETSOLUTIONVALUES = "/getSolutionStatus";
    public static final String GET_SOLUTION_LOCOIMPACT = "/getSolutionLocoImpact";

    public static final String REPAIRCODE = "repairCode";
    public static final String OBJECT_ID = "objID";
    public static final String GET_SOLUTION_CONFIGURATIONS = "/getSolutionConfigurations";
    public static final String GET_FUNCTIONS = "/getFunctions";

    // Added for Exception Handling --- Start
    public static final String FATAL_ERROR = "FATAL_ERROR";
    public static final String MAJOR_ERROR = "MAJOR_ERROR";
    public static final String MINOR_ERROR = "MINOR_ERROR";
    // Added for Exception Handling --- End

    public static final String EOA_FAMILY_LIST_NAME = "Rule Family";
    public static final String EOA_SERVICES_LIST_NAME = "Rule_Services";
    public static final String USER_ID = "userId";
    public static final String USER_TYPE = "userType";
    public static final String YES = "Y";
    public static final String GET_USER_DETAILS_USER_ID = "/getUsersDetails/userId/{userId}";
    public static final String SOLUTIONID = "solutionId";
    public static final String GET_ASSET_DELIVERED_CASES = "/getDeliveredCasesAndLocation";
    /**
     * tool resources
     */
    public static final String CASE_Id = "caseId";
    public static final String TOOL_SERVICE = "toolservice";
    public static final String GET_RUNS = "/getRuns";
    public static final String CASEID = "caseId";
    public static final String REFER_CASE = "/referCase";
    public static final String SCHEDULE_TOOLS = "/scheduleTools";
    public static final String STR_FROM_DATE = "08/21/2011 12:22:34";
    public static final String STR_END_DATE = "08/24/2011 12:22:34";
    public static final String USER_LANGUAGE = "userlanguage";
    public static final String ASSET_HEADER = "assetHeader";
    public static final String QUEUE_NAME = "queueName";
    public static final String CASE_TITLE = "caseTitle";
    public static final String CASE_STATUS = "caseStatus";

    /**
     * CasesResources
     */

    public static final String GET_CASES = "/getCases";
    public static final String GET_USER_CASES = "/getUserCases";
    public static final String GET_DELIVERED_CASES_LITE = "/getDeliveredCases";
    public static final String GET_ASSET_CASES_LITE = "/getAssetCases";
    public static final String CASE_SERVICE = "/caseservice";
    public static final String CASE_SERVICE_LITE = "/caseservicelite";
    public static final String CASE_ID1 = "caseID";
    public static final String USER_ID1 = "userid";
    public static final String STARTDATE = "startdate";
    public static final String ENDDATE = "enddate";
    public static final String GET_DELIVERED_SOLN_DETAILS = "/getDeliveredSolutionDetails";
    public static final String GET_MISSED_CASES = "/getMissedCases";
    public static final String GET_CASEDETAILS = "/getCaseDetails";
    public static final String DISPATCH_TO_QUEUE = "/dispatchToQueue";
    public static final String TAKE_OWNERSHIP = "/takeOwnerShip";
    public static final String CREATE_CASE = "/createCase";
    public static final String GET_TOOL_OUTPUT_DETAILS = "/getToolOutputDetails";
    public static final String SAVE_CASE_DETAILS = "/saveCaseDetails";
    public static final String REOPEN_CASE = "/reOpenCase";
    public static final String GET_OPEN_RX_DETAILS = "/getOpenRXDetails";
    public static final String DELIVER_SOLUTION = "/deliverSolution";
    public static final String SCORERX_AND_CLOSECASE = "/scoreCloseCase";
    public static final String GET_LATEST_CASES = "/getLatestCases";
    public static final String GET_LATEST_CASERULES = "/getLatestCaseRules";
    public static final String START_ROW = "startRow";
    public static final String END_ROW = "endRow";
    public static final String RULEID = "ruleID";
    public static final String CL = "clearingLogic";
    public static final String CUSTOMER_ID_FOR_ASSET = "customerID";
    public static final String GET_ASSET_GROUPS = "getAssetGroups/customerId/{customerId}";
    public static final String GET_LASTDOWNLOAD_STATUS = "/getLastDowloadStatus";
    public static final String AUTHORIZATION_SERVICE = "/authorizationservice";
    public static final String GET_ROLESDETAILS = "/getRoleDetails";
    public static final String GET_ROLESDETAILS_ROLENAME = "/getRoleDetails/roleName/{roleNameValue}";
    public static final String SAVEROLESDETAILS = "/saveRoleDetails";
    public static final String GETROLEPRIVILEGE_ROLEID = "/getRolePrivileges/roleId/{roleIdValue}";
    public static final String GETROLEsECNAVIGATION = "/getRoleSecNavPrivileges/roleId/{roleIdValue}";
    public static final String NOTESSERVICE = "/notesservice";
    public static final String GETNOTES = "/getNotes";
    public static final String ADDNOTES = "/addNotes";
    public static final String EDITNOTES = "/editNotes";
    public static final String DELETENOTES = "/deletenote";
    public static final String COMMONSERVICE = "/commonservice";
    public static final String GETQUEUES = "/getQueues";
    public static final String FAULTSTRATEGYSERVICE = "/faultstrategyservice";
    public static final String GETFAULTCODES = "/getFaultCodes";
    public static final String GETSUBID = "/getSubID";
    public static final String GETCOUNTROLLERIDS = "/getControlIDs";
    public static final String GETFAULTCODES_FAULTVALUES = "/getFaultCodes/faultCode/{faultCodeValue}";
    public static final String FAULTCODEVALUE = "faultCodeValue";
    public static final String SAVEFAULTCODEDETAILS = "/saveFaultCodeDetails";
    public static final String KEPSERVICE = "/kepservice";
    public static final String GETSTATUS = "/getStatus";
    public static final String GETCLASSIFICATION = "/getClassification";
    public static final String GETCLASSIFICATIONWINDOW = "/getClassificationWindow";
    public static final String GETDAYSAFTEREVENT = "/getDaysAfterEvent";
    public static final String GETALGORITHM = "/getAlgorithm";
    public static final String GETROOTCAUSE = "/getRootCause";
    public static final String GETSYMPTOM = "/getSymptom";
    public static final String GETRX = "/getRx";
    public static final String GETPROGNOSTICWINDOW = "/getPrognosticWindow";
    public static final String GETCONFIGFUNCTION = "/getConfigFunction";
    public static final String KEPSERVICE_KNOWLEDGESEEKER = "/kepservice/knowledgeSeeker";
    public static final String GETPATTERNSUMMARYDETAILS = "/getPatternSummaryDetails";
    public static final String GETTRACKINGIDS_TRACKINGID = "/getTrackingIds/trackingId/{trackingId}";
    public static final String GETCREATORS_CREATBY = "/getCreators/createdBy/{createdBy}";
    public static final String GETRUNNAMES = "/getRunNames/runName/{runName}";
    public static final String GETKNOWLEDGESEEKERSTRACKINGID = "/getKnowledgeSeekerTrackings";
    public static final String GETTRACKINGDEATILS_TRACKINGID = "/getTrackingDetails/trackingId/{trackingId}";
    public static final String CREATEREQUEST = "/createRequest";
    public static final String GETFILTERFLAG = "/getFilterFlag";
    public static final String RULESERVICE = "/ruleservice/ruleTester";
    public static final String GETRULEIDS = "/getRuleIds/ruleId/{ruleId}";
    public static final String RULEIDD = "ruleId";
    public static final String GETRULESTITLES = "/getRuleTitles";
    public static final String GETRULETYPE = "/getRuleType";
    public static final String GETPATTERNDETAILS = "/getPatternDetails/pattern/{pattern}/trackingId/{trackingId}";
    public static final String GETTESTERTRACKINGS = "/getTesterTrackings";
    public static final String KPISERVICE = "/kpiservice";
    public static final String GETASSETKPICOUNTS = "/getAssetKPICounts/kpiname/{kpiname}";
    public static final String KPINAME = "kpiname";
    public static final String GETTOTALCOUNTS = "/getTotalCounts";
    public static final String MONITORINGSERVICE = "/monitoringservice";
    public static final String GETLOCAL_DASHBAORD_DETAILS = "/getLocoDashboardDetails";
    public static final String GETSYSTEM_DASHBOARD_DETAILS = "/getSystemDashboardDetails";
    public static final String GETKEEPALIVE_STATUS = "/getKeepAliveStatus";
    public static final String REPORTSERVICE = "/reportsservice";
    public static final String GETRCAREPORT = "/getRCAReport";
    public static final String GETERRORREPORT = "/getErrorReport";
    public static final String GETRULEMEASUREREPORT = "/getRuleMeasureReport";
    public static final String GETSOLUTIONREPORT = "/getSolutionReport";
    public static final String GETRULEMEASURE = "/getRuleMeasure";
    public static final String GETERRORCATEGORIES = "/getErrorCategories";
    public static final String RULE_SERVICE = "ruleservice";
    public static final String GETMANUALRUN_DETAILS = "/getManualRunDetails";
    public static final String SECURITYSERVICE = "/securityservice";
    public static final String AUTHENTICATEUSER_USERID = "/authenticateUser/userId/{userIdValue}";
    public static final String AUTHENTICATEUSER_USERID_MOB = "/authenticateUserMob/userId/{userIdValue}/lastLoginFrom/{lastLoginFrom}";
    public static final String USERIDVALUE = "userIdValue";
    public static final String PASSWORD = "password";
    public static final String LASTLOGINFROM = "lastLoginFrom";
    public static final String GETSOLUTIONREPAIRCODE = "/getSolutionRepairCodes/objID/{objID}";
    public static final String GETSOLUTIONTASKDESC = "/getSolutionTaskDesc/repairCode/{repairCode}";
    public static final String TRACKINGID = "trackingID";
    public static final String NOOFDAYS = "NoOfDays";
    public static final String GETMANUALRUN_DETAILS_TRACKINGID = "/getManualRunDetails/trackingID/{trackingID}";
    public static final String GETRULEDETAILS = "/getRuleDetails/ruleID/{ruleID}";
    public static final String GETRULEDETAILSCL = "/getRuleDetailsCL/ruleID/{ruleID}/clearingLogic/{clearingLogic}";
    public static final String GETTRACEDETAILS = "/getTraceDetails";
    public static final String MANUALRUN = "Manual_Run";
    public static final String GETRULETESTER_DETAILS = "/getRuleTestsDetails";
    public static final String GETRULES = "/getRules";
    public static final String GETRUNRECREATE_DETAILS = "/getRunRecreateDetails";
    public static final String REQUESTRULETEST = "/requestRuleTest";
    public static final String SAVERULE = "/saveRule";
    public static final String ACTIVEDEACTIVATIVATE_RULE = "/activateDeactivateRule";
    public static final String GETRULEFUNCTIONS = "/getRuleFunctions";
    public static final String REQUESTRUNRECREATE = "/requestRunrecreate";
    public static final String SAVEVIRTUAL = "/saveVirtual";
    public static final String GETVIRTUALS = "/getVirtuals";
    public static final String REQUEST_MANUALRUN = "/requestManualRun";
    public static final String GETRULETYPES = "/getRuleTypes";
    public static final String GETRULE_STATUS = "/getRuleStatus";
    public static final String GETSUBSYSTEM = "/getSubSystem";
    public static final String GETVERSION = "/getVersion";
    public static final String GETFAMILY = "/getFamily";
    public static final String GETFAULTCODE = "/getFaultCode";
    public static final String GETRULECREATEDBY = "/getRuleCreatedBy";
    public static final String GETRULESLASTUPDATESBY = "/getRuleLastUpdatedBy";
    public static final String GETMETRICSDAYS = "/getMetricsDays/metricsDays/{metricsDays}";
    public static final String RULEDEFID = "ruleDefID";
    public static final String STRING_EMPTY = " ";
    public static final String GET_LAST_FAULT_STATUS = "/getLastFaultStatus";

    public static final String DATA_SET = "DATA_SET";
    // Added as part of Misc changes Sprint 21
    public static final String USER_FNAME = "userFirstName";
    public static final String USER_LNAME = "userLastName";
    public static final String ROLE_ID_VALUE = "roleIdValue";
    public static final String SYMBOL_NEGETION = "~";
    public static final String SYMBOL_DOUBLESLASH = "\\";
    public static final String CUSTOMERS = "customers";
    public static final String ROLENAMEVALUE = "roleNameValue";
    public static final String GETDATA_FILTER = "/getDataFilter";
    public static final String BEAN_PROPERTIES = "BeanProperties.Properties";
    public static final String CLOSED_TO_LOAD = ".classtoload";
    public static final String SYMBOL_ATRATE = "@";
    public static final String INTERFACE_JAVAUTIL = "interface java.util.List";
    public static final String CLASS_JAVASTRING = "class java.lang.String";
    public static final String DOT = ".";
    public static final String SYMBOL_OR = "|";
    public static final String SINGLEQUOTE = "'";
    public static final String DEACTIVE_UPDATION_FAILURE = "DEACTIVE_UPDATION_FAILURE";
    public static final String DEACTIVE_UPDATION_SUCCESS = "DEACTIVE_UPDATION_SUCCESS";
    public static final String ACTIVE_UPDATION_FAILURE = "ACTIVE_UPDATION_FAILURE";
    public static final String INVALID_INPUTS = "INVALID_INPUTS";
    public static final String INVALID_CHARACTER = "INVALID_CHARACTER";
    public static final String INPUTS_NOT_GIVEN = "INPUTS_NOT_GIVEN";
    public static final String ANOM_TECHNIQUE_LISTNAME = "Anomaly Techniques";
    public static final String EDP_TECHNIQUE_LISTNAME = "EDP_Techniques";
    public static final String GET_EDP_TECHNIQUE = "/getEDPTechniques";
    public static final String GET_ANOM_TECHNIQUE = "/getAnomTechniques";
    public static final String GET_SDP_FUNCTIONS = "/getSDPFunctions";
    public static final String SDP_FUNCTIONS_LISTNAME = "SDP_Functions";
    public static final String UTF_8 = "UTF-8";
    public static final String DEFAULT_TIMEZONE = "defaultTimezone";
    public static final String DEFAULT_TIMEZONE_VALUE = "DEFAULT_TIMEZONE";
    public static final String EST_REP_TIME = "estRepairTime";
    public static final String RXTASK_UPLOAD_ATTACHMENT = "/uploadattachment";
    public static final String RXTASK_DOWNLOAD_ATTACHMENT = "/downloadattachment";
    public static final String DOWNLOAD_STREAM_ATTACHMENT = "/downloadstreamattachment";
    public static final String RXTASK_DELETE_ATTACHMENT = "/deleteattachment";
    public static final String RX_DOWNLOAD_FILE_NAME = "downloadfilename";
    public static final String RX_FILE_PATH = "filepath";
    public static final String RX_DELETE_FILE_NAME = "filename";
    public static final String RX_SOLUTION_ID = "solutionid";
    public static final String RX_LOEGGED_USER_ID = "loggedinuser";
    public static final String RX_RX_TASKEID = "taskId";
    public static final String NEW_RX = "newrx";
    public static final String SYMBOLE_ROOT = "/";
    public static final String USER_HOME = "user.home";
    public static final String RX_UPLOAD_ERR = "Error encountered while uploading file";
    public static final String TEMP = "TEMP";
    public static final String ATTACHMENT = "ATTACHMENT";
    public static final String RXAUTH = "RXAUTH";
    public static final String DIRECTORYCREATIONEXCEPTION = "DIRECTORY_CREATION_FAILED";
    public static final String FILENOTFOUNDEXCEPTION = "FILE_NOT_FOUND";
    public static final String INVALID_FILE_NAME = "INAVLID_FILE_NAME";
    public static final String FILE_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String FILE_ATTACHMENT_NAME = "attachment; filename=";
    public static final String GET_ASSET_SERVICES = "/getAssetServices";
    public static final String RX_IDS = "rxIds";
    public static final String CASE_TYPE = "caseType";
    public static final String DATA_SCREEN = "DATA_SCREEN";
    public static final String ALL_RECORDS = "ALL_RECORDS";
    public static final String NOTCH_8 = "notch8";
    // Added for Asset Overview Services stroy - Start
    public static final String ASSET_NUMBER_NOT_PROVIDED = "ASSET_NUMBER_NOT_PROVIDED";
    public static final String ASSET_GROUP_NAME_NOT_PROVIDED = "ASSET_GROUP_NAME_NOT_PROVIDED";
    public static final String CUSTOMERID_NOT_PROVIDED = "CUSTOMERID_NOT_PROVIDED";
    // Added for Asset Overview Services stroy - End
    public static final String RX_DOWNLOAD_PDF = "/downloadpdf";
    public static final String RX_SOLUTION_ID_PDF = "solutionidpdf";
    public static final String DELETE_FAVORITE_FILTER = "/deleteFilter";
    public static final String FILTER_ID = "filterId";
    public static final String FILTERID_NOT_PROVIDED = "FILTER_ID_NOT_PROVIDED";
    public static final String FETCH_FAVORITE_FILTER = "/fetchFavoriteFilter";
    public static final String USR_ROLE_SEQ_ID = "usrRoleSeqId";
    public static final String ROLE_SEQ_ID = "roleSeqId";
    public static final String USR_ROLE_SEQID_NOT_PROVIDED = "USR_ROLE_SEQID_NOT_PROVIDED";
    // Added for Favourite Filter Story - Start

    // Added for Favourite Filter Story - End
    public static final String GET_MAP_LEGENDS = "/getMapLegends";
    public static final String GET_MAP_LAST_REFRESH_TIME = "/getMapLastRefreshTime";
    /*
     * Mandatory check for User_role_seq_id not found added in admin resource for Favorite filter
     * sotry
     */
    public static final String USR_ROLE_ID_NOT_FOUND = "USR_ROLE_ID_NOT_FOUND"; // JaxB
                                                                                // File::
                                                                                // SaveFavFilterType
                                                                                // fieldname::linkUsrRoleSeqId
    /*
     * Mandatory check for User_role_seq_id not found added in admin resource for Favorite filter
     * sotry
     */
    // Added for sprint 4 Actionable Rx Story
    public static final String GET_ACTIONABLE_RXTYPES = "/getActionableRxTypes";
    public static final String GET_NON_ACTIONABLE_RXTITLES = "/getNonActionableRxTitles";
    public static final String FLEET_ID = "fleetId";
    public static final String ROAD_NO = "roadNo";
    public static final String MODELID = "modelId";
    public static final String RX_GROUP_ID = "rxGroupId";
    public static final String GET_IMINE_RUNAGAINST = "/getiMineRunAgainstValues";
    public static final String GET_ROAD_NUMBERS = "/getRoadNos";
    public static final String GET_ASSET_ID = "/getAssetId";
    public static final String GET_CLEARING_LOGIC_ID = "/getClearingLogicId";

    /** Added constants for Virtual Webservices **/
    public static final String GET_VIRUTALS_CREATEDBY = "/getVirtualCreatedBy";
    public static final String GET_VIRUTALS_LASTUPDATEDBY = "/getVirtualLastUpdatedBy";
    public static final String GET_VIRUTALS_TITLES = "/getVirtualTitles";
    public static final String GET_VIRUTALS_STATUS = "/getVirtualStatus";
    public static final String GET_VIRUTALS_FAMILY = "/getVirtualFamily";
    public static final String GET_VIRUTALS_ID = "/getVirtualIds";
    public static final String GETVIRTUAL_DETAILS = "/getVirtualDetails/virtualId/{virtualId}";
    public static final String VIRUTALS_ID = "virtualId";
    public static final String VIRUTALS_TITLE = "virtualTitle";
    public static final String VIRUTALS_LAST_UPDATED_BY = "virtualLastUpdatedBy";
    public static final String VIRUTALS_LAST_UPDATED_DATE = "virtualLastUpdatedDate";
    public static final String VIRUTALS_CREATED_BY = "virtualCreatedBy";
    public static final String VIRUTALS_CREATED_DATE = "virtualCreatedDate";
    public static final String VIRUTALS_CREATED_FROM_DATE = "virtualCreatedFromDate";
    public static final String VIRUTALS_CREATED_TO_DATE = "virtualCreatedToDate";
    public static final String VIRUTALS_LAST_UPDATED_FROM_DATE = "virtualLastUpdatedFromDate";
    public static final String VIRUTALS_LAST_UPDATED_TO_DATE = "virtualLastUpdatedToDate";
    public static final String VIRUTALS_FAMILY = "virtualFamily";
    public static final String VIRUTALS_STATUS = "virtualStatus";
    public static final String CASE_FROM = "caseFrom";
    public static final String INVALID_VIRTUAL_ID = "INVALID_VIRTUAL_ID";
    public static final String VIRTUAL_STATUS = "VIRTUAL_STATUS";
    public static final String ROLE_NAME = "roleName";
    public static final String GET_VIRUTALS_COLUMN_STATUS = "/getVirtualColumnStatus";
    public static final String GET_VIRUTAL_ACTIVE_RULES = "/getVirtualActiveRules";
    /** added for proximity **/
    public static final String GET_SYSTEM_PARAM_VALUES = "/getAllSystemParamValues";
    // added for select rx
    public static final String CREATCASE_CONDITION = "condition";
    public static final String CREATCASE_SELECTBY = "selectBy";
    public static final String CREATCASE_MODELTYPE = "modelType";
    public static final String CREATCASE_VALUE = "solutionValue";
    public static final String HAS_CM_PRIVILEGE = "/hasCMPrivilege/userId/{userId}";
    public static final String MASS_APPLY_RX = "/massApplyRX";
    public static final String HYPHEN = "-";

    public static final String GET_VISUALIZATION_DETAILS = "/getVisualizationDetails";
    public static final String GRAPH_PARMS = "graphParmVal";
    public static final String IS_DATE_DIFF = "dateDiff";
    public static final String CONTROLLER_CONFIG = "controllerConfig";
    public static final String ADD_RX = "/addRX";
    public static final String GET_CASE_RX_DETAILS = "/getCaseRXDetails/caseId/{caseId}";
    public static final String LAST_FAULT_FLAG = "isLastFault";
    public static final String EDIT_ROLE = "/editRole";
    public static final String ADD_ROLE = "/addRole";
    public static final String ROLE_ID = "roleId";
    public static final String GET_ALL_PRIVILEGES = "/getAllPrivileges";
    public static final String ROLE_NAME_REQUIRED = "ROLE_NAME_REQUIRED";
    public static final String IS_LIMITED_PARAM = "isLimitedParam";
    public static final String IS_HIDE_L3 = "IS_HIDE_L3";
    public static final String INIT_LOAD = "INIT_LOAD";
    public static final String CUSTOMER_CHANGE = "defaultCustomer";
    public static final String DEFAULT_CUSTOMER = "defaultCustomer";
    public static final String EOA_SOURCE = "EOA";
    public static final String MCS_SOURCE = "MCS";
    public static final String CXS_MCS_SOURCE = "CXS_MCS_SOURCE";
    public static final String CASE_CREATION_FAILURE = "CASE_CREATION_FAILURE";
    public static final String RX_DELIVERED_SUCCESS = "RX_DELIVERED_SUCCESS";
    public static final String RX_DELIVERED_FAILURE = "RX_DELIVERED_FAILURE";
    public static final String CASE_PRIORITY = "High";
    public static final String DEFAULT_CASE_TITLE = "Field Request";
    public static final String GET_CASEHISTORY_DETAILS = "/getCaseHistoryDetails";
    public static final String MODIFY_RX_SOLUTION = "/modifyRx";
    public static final String ADD_RX_TO_CASE = "addRXToCase";
    public static final String REPLACE_RX = "replaceRx";
    public static final String FETCH_CURRENT_OWNERSHIP = "/fetchCurrentOwnership/caseId/{caseId}";
    public static final String RMD_203 = "RMD_203";
    public static final String RMD_204 = "RMD_204";

    public static final String RX_SCORE_CODE_DEL = "Miss-4C";
    public static final String RX_FDBK = "Auto Scored";

    // added for scoreRx
    public static final String SCORE_RX = "/scoreRx";
    public static final String RX_CASEID_NOT_PROVIDED = "RX_CASEID_NOT_PROVIDED";
    public static final String SCORE_CODE_NOT_PROVIDED = "SCORE_CODE_NOT_PROVIDED";
    public static final String RX_FEEDBACK_NOT_PROVIDED = "RX_FEEDBACK_NOT_PROVIDED";
    public static final String Y = "Y";

    // added for closeCase
    public static final String CLOSE_CASE = "/closeCase";

    // invalid character set
    public static final String NOT = "!";
    public static final String AT = "@";
    public static final String HASH = "#";
    public static final String DOLLAR = "$";
    public static final String PERCENTAGE = "%";
    public static final String CARROT = "^";
    public static final String AMPERSAND = "&";
    public static final String STAR = "*";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSED_BRACKET = ")";
    public static final String PLUS = "+";
    public static final String SLASH = "/";
    public static final String GET_CURRENT_OWNERSHIP = "/getCurrentOwnership/caseId/{caseId}";

    public static final String MCS_ASSETLIST_URL = "MCS_ASSETLIST_URL";
    public static final String MCS_USERNAME = "MCS_USERNAME";
    public static final String MCS_PASSWORD = "MCS_PASSWORD";
    public static final String NAMESPACE_URI_MCS = "http://www.getransporatation.com/railsolutions/mcs";
    public static final String LOCAL_PART_ASSET_SERVICE = "AssetsListServiceInterface";
    public static final String SAVE_MANUAL_FEEDBACK = "/saveSolutionFeedback";
    public static final String RX_SCORED_205 = "RX_SCORED";
    public static final String RX_SCORED_206 = "CLOSED_CASE";
    public static final String SERVICE_STATUS_OPEN = "OPEN";
    public static final String REPLACE_MODIFY_RX_210 = "REPLACE_MODIFY_RX_210";
    public static final String SAVE_MANUAL_FEEDBACK_ERROR = "SAVE_MANUAL_FEEDBACK";
    public static final String SAVE_MANUAL_FEEDBACK_NO_RECOMMENDATION = "FEEDBACK_NO_RECOMMENDATION";
    // Reopen Case RTC
    public static final String REOPEN_CASE_RTC_FAILED = "REOPEN_CASE_RTC_FAILED";
    public static final String RECOM_ID = "recomId";
    public static final String GET_CLOSE_OUT_REPAIR_CODES = "/getCloseOutRepairCodes/recomId/{recomId}";
    public static final String GET_CASE_REPAIR_CODE = "/getCaseRepairCodes/caseId/{caseId}";
    public static final String GET_REPAIR_CODE = "/getRepairCodes";
    public static final String VALUE = "value";
    public static final String ADD_REPAIRCODE_TO_CASE = "/addRepairCodes";
    public static final String REMOVE_REPAIRCODE_TO_CASE = "/removeRepairCodes";
    /* Added For Visualization changes */
    public static final String VISUALIZATION_SERVICE = "visualizationservice";
    public static final String GET_VISUALIZATION_GRAPH_NAMES = "getVisualizationGraphNames";
    public static final String SOURCE_TYPE = "sourceType";
    public static final String DATASOURCE = "dataSource";
    public static final String CONTROLLER_CONFIG_NOT_PROVIDED = "CONTROLLER_CONFIG_NOT_PROVIDED";
    public static final String CUSTOMER_ID_NOT_PROVIDED = "CUSTOMER_ID_NOT_PROVIDED";
    public static final String SOURCE_NOT_PROVIDED = "SOURCE_NOT_PROVIDED";
    public static final String SOURCE_TYPE_NOT_PROVIDED = "SOURCE_TYPE_NOT_PROVIDED";
    public static final String MP_NUMBERS_NOT_PROVIDED = "MP_NUMBERS_NOT_PROVIDED";
    public static final String GET_VISUALIZATION_PARM_NUMBERS = "/getMPParmNumbers";
    public static final String GET_FAULT_CODE = "/getFaultCode";
    public static final String REQUEST_TYPE = "requestType";
    public static final String REQUEST_TYPE_HC = "requestTypeHC";
    public static final String REQ_PARAM_ASSET_TYPE = "assetType";
    public static final String REQ_PARAM_DEVICE_NAME = "deviceName";
    public static final String UNDERSCORE = "_";
    public static final String GET_VISUALIZATION_EVENT_DATA = "/getAssetVisualizationEventData";
    public static final String GET_VIZ_VIRTUAL_PARM_NUMBERS = "/getVizVirtualParameters";
    public static final String GET_VIZ_VIRTUAL_ASSET_FAMILY = "/getVizVirtualAssetFamily/model/{model}";
    public static final String DATABASE = "database";
    public static final String SEARCH_CASES = "/searchCases";
    public static final String LIFE_STATISTICS_DATA = "/getLifeStatisticsData";
    public static final String GET_RX_TITLES_LITE = "getRxTitlesLite";
    public static final String GET_CASE_RX_HISTORY = "/getCaseRxHistory/caseId/{caseId}";
    public static final String LANGUAGE_EN = "EN";
    public static final String ALL_CUSTOMER = "ALLCUSTOMER";
    public static final String GET_ALL_FLEETS = "/getAllFleets";
    public static final String GET_ALL_REGIONS = "/getAllRegions";
    public static final String GET_SUB_DIVISION = "/getAllSubDivisions";
    /* Added by vamsi for fetching queue Names for Dispatch */
    public static final String GET_QUEUE_NAMES = "/getQueueNames";

    public static final String DISPATCH_CASE_TO_QUEUE = "/dispatchCaseToQueue";
    public static final String QUEUE_ID = "queueId";

    /* Added by vamsi for AddNotes */

    public static final String ADD_NOTES_TO_CASE = "/addNotesToCase";
    public static final String FETCH_UNIT_STICKY_DETAILS = "/fetchUnitStickyDetails";
    public static final String FETCH_CASE_STICKY_DETAILS = "/fetchCaseStickyDetails";
    /* added for usernames */
    public static final String GET_CASE_MGMT_USER_NAMES = "/getCaseMgmtUsersDetails";
    public static final String userId = "userId";
    public static final String firstName = "firstName";
    public static final String lastName = "lastName";
    public static final String ASSIGN_TO_USER = "/assignCaseToUser";
    public static final String GET_OWNER_FOR_CASE = "/getCaseCurrentOwnerDetails";
    public static final String CASE_HISTORY = "/getCaseHistory";
    public static final String GET_ASSET_CASES = "/getAssetCases";
    public static final String GET_VIEW_CASES = "/getViewCases";
    public static final String ASSETNUMBER_NOT_PROVIDED = "ASSETNUMBER_NOT_PROVIDED";
    public static final String ASSETGRPNAME_NOT_PROVIDED = "ASSETGRPNAME_NOT_PROVIDED";
    public static final String NOOFDAYS_NOT_PROVIDED = "NOOFDAYS_NOT_PROVIDED";
    public static final String CUSTOMERNAME_NOT_PROVIDED = "CUSTOMERNAME_NOT_PROVIDED";

    // public static final String CASE_ID1="caseId";
    public static final String REMOVE_STICKY_NOTES = "/removeStickyNotes";
    public static final String APPLY_LEVEL = "applyLevel";
    public static final String CASE_OBJID = "caseObjId";
    public static final String GET_CASE_HISTORY = "/getCaseHistory";
    public static final String UPDATE_CASE_DETAILS = "/updateCaseDetails";
    public static final String GET_CASE_TYPES = "/getCaseTypes";
    public static final String QUEUEID_NOT_PROVIDED = "QUEUEID_NOT_PROVIDED";
    public static final String GETTING_NULL_RESPONSE_OBJECT = "GETTING_NULL_RESPONSE_OBJECT";
    public static final String CASE_OBJID_NOT_PROVIDED = "CASE_OBJID_NOT_PROVIDED";
    public static final String APPLY_LEVEL_NOT_PROVIDED = "APPLY_LEVEL_NOT_PROVIDED";
    public static final String FAILURE = "failure";
    public static final String NOTE_DESCRIPTION_NOT_PROVIDED = "NOTE_DESCRIPTION_NOT_PROVIDED";
    public static final String GET_EOA_LOOKUP_VALUES = "/getEoaLookupValues/listName/{lookupName}";
    public static final String INVALID_CASEID = "INVALID_CASEID";
    public static final String INVALID_USERID = "INVALID_USERID";
    public static final String YANK_CASE = "/yankCase";
    public static final String GET_CM_PRIVILEGE_ROLES = "/getCaseMgmtRoles";
    public static final String GET_EOA_USERID = "/getEoaUserName";
    public static final String GET_SUBSYSTEM = "/getSubSystemDetails";
    public static final String GET_SOLUTIONS_FOR_CASE = "/getSolutionsForCase";
    public static final String INVALID_CASEOBJID = "INVALID_CASEOBJID";
    public static final String ADD_RX_FOR_CASE = "/addRxForCase";
    public static final String DELETE_RX_FOR_CASE = "/deleteRxForCase";
    public static final String INVALID_RX_OBJID = "INVALID_RX_OBJID";
    public static final String INVALID_REVISION_NUMBER = "INVALID_REVISION_NUMBER";
    public static final String INVALID_ESTIMATED_REPAIR_TIME = "INVALID_ESTIMATED_REPAIR_TIME";
    public static final String GET_CASE_INFO = "/getCaseInfo";
    public static final String RE_OPEN_CASE = "/reopenCase";
    public static final String CASEOBJID_NOT_PROVIDED = "CASEOBJID_NOT_PROVIDED";
    public static final String USERNAME_NOT_PROVIDED = "USERNAME_NOT_PROVIDED";
    public static final String RECOMMENDATION_NOTES_NOT_PROVIDED = "RECOMMENDATION_NOTES_NOT_PROVIDED";
    public static final String DELIVER_RX = "/deliverRx";
    public static final String MODIFY_RX_TO_CASE = "/modifiyRxToCase";
    public static final String REPLACE_RX_TO_CASE = "/replaceRxToCase";
    public static final String GET_RX_SUBSYSTEM = "/getSubSystem";
    public static final String GET_CASE_SCORE = "/getCaseScore";
    public static final String GET_READY_TO_DELIVER = "/getReadyTodeliver";
    public static final String GET_UNIT_SHIP_DETAILS = "/getUnitShipDetails";
    public static final String PENDING_FDBK_SERIVICE_STATUS = "/pendingFdbkServiceStatus";
    public static final String GET_SERVICE_REQUESTID_STATUS = "/getServiceRequestIdStatus";
    public static final String GET_DELV_DATE_FOR_RX = "/getDeliveryDateForRx";
    public static final String GET_T2_REQUEST = "/getT2request";
    public static final String GET_CUSTOMER_FDBK_OBJID = "/getCustomerFdbkObjid";
    public static final String FDBK_OBJID = "fdbkObjId";
    public static final String FDBK_OBJID_NOT_PROVIDED = "FDBK_OBJID_NOT_PROVIDED";
    public static final String INVALID_FDBK_OBJID = "INVALID_FDBK_OBJID";
    public static final String RX_OBJID = "rxObjId";
    public static final String RX_OBJID_NOT_PROVIDED = "RX_OBJID_NOT_PROVIDED";
    public static final String CHECK_FOR_DELV_MECHANISM = "/checkForDelvMechanism";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String GET_MSDC_NOTES = "/getMsdcNotes";
    public static final String DELIVERED_RX_OBJID_NOT_PROVIDED = "DELVERD_RX_OBJID_NOT_PROVIDED";
    public static final String INVALID_DELIVERED_RX_OBJID = "INVALID_DELVERD_RX_OBJID";
    public static final String GET_PENDING_RECOMMENDATIONS = "/getPendingRecommendations";
    public static final String INVALID_URGENCY = "INVALID_URGENCY";
    public static final String VERSION_NOT_PROVIDED = "VERSION_NOT_PROVIDED";
    public static final String CHECK_FOR_CONTOLLER_CONFIG = "/checkForContollerConfig";
    // View Closure
    public static final String VIEW_CLOSURE_CASE_ID = "caseId";
    public static final String GET_RX_STATUS_HISTORY = "/getRxstatusHistory";
    public static final String GET_CLOSEOUT_REPAIR_CODE = "/getCloseOutRepairCode";
    public static final String GET_RX_HISTORY = "/getRxHistory";
    public static final String GET_ATTACHED_DETAILS = "/getAttachedDetails";
    public static final String GET_RX_NOTE = "/getRxNote";
    public static final String GET_SERVICE_REQ_ID = "/getServiceReqId";
    public static final String GET_CLOSURE_FDBK = "/getClosureFdbk";
    public static final String SERVICE_REQ_ID = "serviceReqId";
    public static final String CUST_FDBK_OBJ_ID = "custFdbkObjId";
    public static final String GET_CUST_FDBK_OBJID = "getCustFdbkObjId";
    public static final String SERVICERQID_NOT_PROVIDED = "SERVICERQID_NOT_PROVIDED";
    public static final boolean TRUE = true;
    // Closure
    public static final String GET_CLOSURE_DETAILS = "/getClosureDetails";
    // manual feedback
    public static final String RX_NOTES_NOT_PROVIDED = "RX_NOTES_NOT_PROVIDED";
    public static final String REISSUE_NOT_PROVIDED = "REISSUE_NOT_PROVIDED";
    public static final String DO_ESERVICE_VALIDATION = "/doEserviceValidation";
    public static final String RX_CASE_ID_NOT_PROVIDED = "RX_CASE_ID_NOT_PROVIDED";
    public static final String USER_ID_NOT_PROVIDED = "USER_ID_NOT_PROVIDED";
    // Find Queue
    public static final String GET_QUEUE_LIST = "/getQueueList";
    public static final String GET_QUEUE_CASES = "getQueueCases";
    public static final String QUEUE_OBJ_ID = "queueObjId";

    // MASSAPPLYRX
    public static final String GET_ROAD_NUMBER_HEADERS = "/getRoadNumberHeaders";
    public static final String GET_MAX_MASS_APPLY_UNITS = "/getMaxMassApplyUnits";
    public static final String GET_ASSET_LIST = "/getAssetList";
    public static final String ASSET_LIST_NOT_PROVIDED = "ASSET_LIST_NOT_PROVIDED";
    public static final String MASS_APPY_RX = "/massApplyRx";
    public static final String PRIORITY_LOW_GBSTELM2GBSTLST = "4-LOW";
    public static final String SEVERITY_MEDIUM_GBSTELM2GBSTLST = "MEDIUM";
    public static final String MASS_APPLY_CASE_TITLE = "AutoCase for SpRX";
    public static final int OPEN_DISPATCH_CONDITION = 10;
    public static final String OPEN_DISPATCH = "Open-Dispatch";

    // Added for Append/Split/Close

    public static final String ENABLE_APPEND = "/enableAppend/caseId/{caseId}/userId/{userId}";
    public static final String APPEND_RX_TO_CASE = "/appendRx/customerId/{customerId}/rxId/{rxId}/caseId/{caseId}/assetNumber/{assetNumber}/assetGrpName/{assetGrpName}/userId/{userId}/toolId/{toolId}/toolObjId/{toolObjId}/ruleDefinitionId/{ruleDefinitionId}/toCaseId/{toCaseId}";
    public static final String GET_PREV_CASES = "/getPrevCases";
    public static final String GET_TOOL_OUTPUT = "/getToolOutput";
    public static final String TO_CASE_ID = "toCaseId";
    public static final String VEHICLE_ID = "vehicleId";
    public static final String TOOL_ID = "toolId";

    // Tooloutput
    public static final String GET_OPEN_FL_COUNT = "/getOpenFLCount";
    public static final String GET_LMS_LOCO_ID = "/getLmsLocoID";
    public static final String Lms_Id = "lmsId";
    public static final String GET_CASE_TITLE = "/getCaseTitle/caseId/{caseId}";
    public static final String MOVE_TOOL_OUTPUT = "/moveToolOutput/customerId/{customerId}/rxId/{rxId}/caseId/{caseId}/assetNumber/{assetNumber}/assetGrpName/{assetGrpName}/userId/{userId}/rxDelv/{rxDelv}/toolId/{toolId}/toolObjId/{toolObjId}/skipCount/{skipCount}/ruleDefinitionId/{ruleDefinitionId}/toCaseId/{toCaseId}";

    public static final String RX_DELV = "rxDelv";
    public static final String TOOL_OUTPUT_CREATECASE = "/toolOutputCreateCase";
    public static final String SKIP_COUNT_CLOSE = "skipCount";
    public static final String SAVE_TOOL_OUTPUT_ACT_ENTRY = "/saveToolOutputActEntry";

    public static final String GET_DIAGNOSTIC_WEIGHT = "/getDiagnosticWeight";
    public static final String GET_SUBSYS_WEIGHT = "/getSubSysWeight";
    public static final String GET_MODE_RESTRICTION = "/getModeRestriction";
    public static final String GET_FAULT_CLASSIFICATION = "/getFaultClassification";
    public static final String GET_CRITICAL_FLAG = "/getCriticalFlag";
    public static final String GET_FAULT_STRATEGY_DETAILS = "/getFaultStrategyDetails";
    public static final String FAULT_STRATEGY_OBJID = "fsObjId";
    public static final String FAULT_CODE_ID = "faultCode";
    public static final String GET_RULE_DESC = "/getRuleDesc";
    public static final String UPDATE_FAULT_SERVICE_STRATEGY = "/updateFaultServiceStrategy";

    public static final String MOVE_DELIVER_TOOL_OUTPUT = "/moveDeliverToolOutput";
    public static final String GET_TO_REPAIR_CODE = "/getTORepairCodes";
    public static final String ACTIVE_RX_EXISTS = "/activeRxExists/caseId/{caseId}";
    public static final String GET_ENABLED_UNIT_RXS_DELIVER = "/getEnabledUnitRxsDeliver";
    public static final String UPDATE_CASE_TITLE = "/updateCaseTitle";
    public static final String GET_HEADER_SEARCH_CASES = "/getHeaderSearchCases";
    public static final String IS_MASS_APPLY_RX = "isMassApplyRx";
    public static final String ADD_RX_APPLY = "addRxApply";
    public static final String GET_RECOMM_DETAILS = "/getRecommDetails";
    public static final String VEHICLE_OBJID = "vehicleObjId";
    public static final String VEHICLE_OBJID_NOT_PROVIDED = "VEHICLE_OBJID_NOT_PROVIDED";
    public static final String INVALID_VEHICLE_OBJID = "INVALID_VEHICLE_OBJID";
    public static final String MODEL_NOT_PROVIDED = "MODEL_NOT_PROVIDED";
    public static final String INVALID_MODEL = "INVALID_MODEL";
    public static final String UNIT_CONFIG = "/getUnitConfigDetails";
    public static final String LOCO_ID = "locoId";
    public static final String GET_SD_CUST_LOOKUP = "/getSDCustLookup";

    public static final String GET_ALERT_SUB_USERS_DETAILS = "/getAlertUsersDetails";
    public static final String GET_USER_COMPONENT_LIST = "/getUserComponentList";

    public static final String TOOL_OBJID = "toolObjId";
    public static final String FALSE_ALARM_DETAILS = "/getFalseAlarmDetails/rxObjId/{rxObjId}";
    public static final String RX_OBJ_ID = "rxObjId";
    public static final String RX_FALSE_ALARM_DETAILS = "/getRXFalseAlarmDetails/rxObjId/{rxObjId}";
    public static final String MDSC_ACCURATE_DETAILS = "/getMDSCAccurateDetails/rxObjId/{rxObjId}";
    public static final String MDSC_ACCURATE_GET_CASE_DETAILS = "/getCaseDetails";
    public static final String REP_OBJ_ID = "repObjId";
    public static final String MDSC_ACCURATE_GET_RX_DETAILS = "/getMDSCRxDetails/repObjId/{repObjId}";
    public static final String RX_OBJID_NUMBER_FORMAT_EXCEPTION = "Rx objId is not in number format";
    public static final String REP_OBJID_NUMBER_FORMAT_EXCEPTION = "Rx objId is not in number format";

    public static final String GET_CALL_LOG_NOTES = "/getCallLogNotes";
    public static final String REGION = "region";
    public static final String GET_FAULT_ORIGIN = "/getFaultOrigin";
    public static final String GET_FAULT_CODE_SUBID = "/getFaultCodeSubId";
    public static final String GET_VIEW_FSS_FAULT_CODE = "/getViewFSSFaultCode";
    public static final String GET_FAULT_STRATEGY_OBJID = "/getFaultStrategyObjId";
    public static final String FAULT_CODE_SUBID = "faultCodeSubId";
    public static final String FAULT_ORIGIN = "faultOrigin";
    public static final String FAULT_CODE_SUBID_NOT_PROVIDED = "FAULT_CODE_SUBID_NOT_PROVIDED";
    public static final String FAULT_ORIGIN_NOT_PROVIDED = "FAULT_ORIGIN_NOT_PROVIDED";
    public static final String INVALID_FAULT_ID = "INVALID_FAULT_ID";
    public static final String INVALID_FAULT_CODE_SUB_ID = "INVALID_FAULT_CODE_SUB_ID";
    public static final String INVALID_FAULT_ORIGIN = "INVALID_FAULT_ORIGIN";
    public static final String CHECK_FOR_UNIT_SHIP_DETAILS = "/checkForUnitShipDetails";
    public static final String GET_DEVICE_INFO = "/getDeviceInfo";
    public static final String TYPE_OF_USER = "typeOfUser";
    public static final String DEVICE = "device";
    public static final String SAVE_HC_DETAILS = "/saveHCDetails";
    public static final String MESSAGE_ID = "messageId";
    public static final String GET_FIND_CASES = "/getFindCases";
    public static final String GET_UNITS_TO_BE_SHIPPED = "/getUnitsToBeShipped";
    public static final String GET_LAST_SHIPPED_UNITS = "/getLastShippedUnits";
    public static final String UPDATE_UNITS_TO_BE_SHIPPED = "/updateUnitsToBeShipped";
    public static final String REQ_URI_UNIT_SHIPPER_SERVICE = "/unitShipperService";
    public static final String CUSTOMER_LIST = "customerList";
    public static final String OBJID_NOT_PROVIDED = "OBJID_NOT_PROVIDED";
    public static final String IS_NON_GPOC_USER = "isNonGPOCUser";
    public static final String GET_PP_ASSET_HST_BTN_DETAILS = "/getPPAssetHstBtnDetails";
    public static final String GET_CUSTOMER_RNH = "/getAllCustRNH";
    public static final String GET_ROAD_NUM = "/getRoadNumber";
    public static final String GET_ROAD_NUM_WITH_FILTER = "/getRoadNumberWithFilter";
    public static final String RNH_ID = "rnhId";
    public static final String RN_SEARCH_STRING = "rnSearchString";
    public static final String RN_FILTER = "rnFilter";

    // Material Usage - Added by Mohamed
    public static final String MATERIAL_USAGE_LOOKUP_DAYS = "lookUpDays";
    public static final String MATERIAL_USAGE_LOOKUP_DAYS_NOT_PROVIDED = "MATERIAL_USAGE_LOOKUP_DAYS_NOT_PROVIDED";
    public static final String GET_MATERIAL_USAGE = "/getMaterialUsage";

    public static final String VALIDATE_EGA_HC = "/validateEGAHC";
    public static final String VALIDATE_NT_HC = "/validateNTHC";

    // Common section data for asset case - Added by mohamed
    public static final String GET_ASSET_CASE_COMMON_SECTION = "/getAssetCaseCommSection";

    // sHIP uNITS - aDDED BY mOHAMED
    public static final String GET_ASSET_NUMBERS_FOR_SHIP_UNITS = "/getAssetNumbersForShipUnits";

    // Added for Add Notes Screen
    public static final String GET_ALL_CONTROLLERS = "/getAllControllers";
    public static final String GET_ALL_CREATORS = "/getNotesCreatersList";
    public static final String ADD_NOTES_TO_VEHICLE = "/addNotesToVehicle";
    public static final String FETCH_VEHICLE_STICKY_DETAILS = "/fetchVehicleSticyDetails";
    public static final String FROM_RN = "fromRN";
    public static final String TO_RN = "toRN";
    public static final String GET_FIND_NOTES = "/getFindNotes";
    public static final String FIND_NOTES_REMOVE_STICKY = "/removeSticky";

    // Vehicle Config
    public static final String REQ_URI_VEHICLE_CONFIG = "/vehicleCfgService";
    public static final String DELETE_VEHICLE_CFG_TEMPLATE = "/deleteVehicleCfgTemplate";
    public static final String CONFIG_FILE_NOT_PROVIDED = "CONFIG_FILE_NOT_PROVIDED";
    public static final String TEMPLATE_NOT_PROVIDED = "TEMPLATE_NOT_PROVIDED";
    public static final String TITLE_NOT_PROVIDED = "TITLE_NOT_PROVIDED";
    public static final String STATUS_NOT_PROVIDED = "STATUS_NOT_PROVIDED";
    public static final String RNH_NOT_PROVIDED = "RNH_NOT_PROVIDED";
    public static final String GET_VEHICLE_BOM_CONFIGS = "/getVehicleBOMConfigs";
    public static final String ROAD_NUMBER_NOT_PROVIDED = "ROAD_NUMBER_NOT_PROVIDED";
    public static final String GET_MDSC_CONTROLLERS_INFO = "/getMDSCStartUpControllersInfo";
    public static final String GET_VEHICLE_CFG_TEMPLATES = "/getVehicleCfgTemplates";
    public static final String UPDATE_VEHICLE_CFG_ITEMS = "/saveVehicleBOMConfigs";
    public static final String SERIALNO_NOT_PROVIDED = "SERIALNO_NOT_PROVIDED";
    public static final String CONFIG_OBJ_ID_NOT_PROVIDED = "CONFIG_OBJ_ID_NOT_PROVIDED";
    public static final String RNH = "rnh";
    public static final String ROAD_NUMBER = "roadNumber";
    // message history
    public static final String FILTER_RECORDS = "/filterRecords";
    public static final String MESSAGE_ID_LIST = "messageIdList";
    public static final String MSG_TITLE = "msgTitle";
    // Reclose
    public static final String GET_RX_DETAILS_FOR_RECLOSE = "/getRxDetailsForReclose";
    public static final String CLOSE_CASE_RESULT = "/getCloseCaseResult";
    public static final String RECLOSE_RESET_FAULTS = "/reCloseResetFaults";
    public static final String GET_CUSTOMER_BASED_URGENCY = "/getCustomerBasedLookupValues";
    // POP UP LIST ADMIN
    public static final String POPUP_LISTADMIN = "/popuplistadmin";
    public static final String GET_SHOW_ALL = "/getLookupValuesShowAll";
    public static final String GET_LOOKUP_LIST = "/getList";
    public static final String POUP_SEARCHBY = "searchBy";
    public static final String POUP_CONDITION = "Condition";
    public static final String POUP_VALUE = "value";
    public static final String ADD_POPUP_LIST_VALUES = "/savePopupList";
    public static final String GET_POPUP_LIST_VALUES = "/getPopupListValues";
    public static final String ADD_POPUP_LOOK_VALUES = "/savePopupListlookvalue";
    public static final String UPDATE_POPUP_LIST = "/updatePopupList";
    public static final String DELETE_POPUP_LIST = "/deletePopUpList";
    public static final String REMOVE_POPUP_LOOK_VALUES = "/removePopupListlookvalue";
    public static final String UPDATE_POPUP_LIST_VALUE = "/updatePopupListValues";
    public static final String POUP_LIST_NAME = "listName";
    public static final String POUP_LIST_DESCRIPTION = "listDescription";
    // Find/Add notes changes
    public static final String ADD_NOTES_TO_UNIT = "/addNotesToUnit";
    public static final String FETCH_UNIT_LEVEL_STICKY_DETAILS = "/fetchStickyUnitLevelNotes";
    public static final String NO_OF_UNITS = "noOfUnits";

    // Added for CM KM merge
    public static final String LHRSERVICE = "lhrservice";
    public static final String GET_LHR_HEALTH_FOR_VEHICLES = "/getVehicleHealthScore";
    public static final String VEHICLE_ROAD_NUMBER_NOT_PROVIDED = "VEHICLE_ROAD_NUMBER_NOT_PROVIDED";
    public static final String LOCO_ID_NOT_PROVIDED = "LOCO_ID_NOT_PROVIDED";
    public static final String GET_PERFORMANCE_CHECK_VALUES = "/getPerformanceCheckValues";
    public static final String HEALTH_RULE_TYPE = "Health";

    /* Added by Amuthan for CBR changes */

    public static final String CBR_SERVICE = "cbrservice";
    public static final String GET_CBR_RX_DETAILS = "getRxMaintenanceDetails";
    public static final String GET_CBR_CASE_DETAILS = "getCaseMaintenanceDetails";
    public static final String CBR_CASE_ACTIVATE_DEACTIVATE = "cbrCaseActivateDeactivate";
    public static final String CBR_RX_ACTIVATE_DEACTIVATE = "cbrRxActivateDeactivate";
    public static final String GET_FAMILIES_EXTERNAL_RULE_SEARCH = "/getFamiliesForExternalRuleSearch";
    public static final String GET_FAMILIES_RULE_AUTHOR = "/getFamiliesForRuleAuthor";
    public static final String GETRULECREATEDBYEXTERNAL = "/getRuleCreatedByExternal";
    public static final String GETRULESLASTUPDATESBYEXTERNAL = "/getRuleLastUpdatedByExternal";
    public static final String IS_KM = "isKM";

    // GPOC Smart Shops
    public static final String GET_GPOC_SMART_SHOP_VOTES = "/getGPOCSmartShopVotes";
    public static final String CAST_GPOC_VOTE = "/castGPOCVote";
    public static final String GET_PREVIOUS_VOTE = "/getPreviousVote";
    public static final String REPAIRCODE_NOT_PROVIDED = "REPAIRCODE_NOT_PROVIDED";

    // Added for Repair Maintenance screen
    public static final String REQ_URI_REPAIR_MAINTENANCE_SERVICE = "/repairMaintenanceService";
    public static final String GET_CUST_SITES = "/getCustomerSites";
    public static final String GET_REPAIR_FACILITY_DETAILS = "/getRepairFacilityDetails";
    public static final String INSERT_REPAIR_SITE_LOCATION = "/insertRepairSiteLoc";
    public static final String UPDATE_REPAIR_LOCATION_DETAILS = "/updateRepairSiteLoc";
    public static final String SITE = "site";
    public static final String SITE_NOT_PROVIDED = "SITE_NOT_PROVIDED";
    public static final String RAILWAY_DESIG_CODE_VALUE_NOT_PROVIDED = "RAILWAY_DESIG_CODE_VALUE_NOT_PROVIDED";
    public static final String REPAIR_LOCATION_VALUE_NOT_PROVIDED = "RAILWAY_DESIG_CODE_VALUE_NOT_PROVIDED";
    public static final String LOCATION_CODE_VALUE_NOT_PROVIDED = "LOCATION_CODE_VALUE_NOT_PROVIDED";
    public static final String STATUS_VALUE_NOT_PROVIDED = "STATUS_VALUE_NOT_PROVIDED";
    public static final String USER_NAME_VALUE_NOT_PROVIDED = "USER_NAME_VALUE_NOT_PROVIDED";
    public static final String CUSTOMER_ID_VALUE_NOT_PROVIDED = "CUSTOMER_ID_VALUE_NOT_PROVIDED";
    public static final String SITE_VALUE_NOT_PROVIDED = "SITE_VALUE_NOT_PROVIDED";
    public static final String SITE_OBJID_NOT_PROVIDED = "SITE_OBJ_ID_NOT_PROVIDED";

    // Added For Customer Id Changes
    public static final String GET_CUSTOMER_ID = "/getCustomerId";
    public static final String INVALID_ASSET_GRP_NAME = "INVALID_ASSET_GRP_NAME";

    // repair code maintenance
    public static final String REQ_URI_REPAIRCODE_MAINTENANCE = "/repairCodesMaintenanceService";
    public static final String GET_REPAIRCODE_LIST = "/getRepairCodesList";
    public static final String REPAIRCODE_VALIDATIONS = "/repairCodeValidations";
    public static final String REPAIR_CODE = "repairCode";
    public static final String REPAIR_CODE_DESC = "repairCodeDesc";
    public static final String FLAG = "flag";
    public static final String ADD_REPAIR_CODES = "/addRepairCodes";
    public static final String UPDATE_REPAIR_CODES = "/updateRepairCodes";
    // BOM Maintenance
    public static final String GET_BOM_CONFIG_LIST = "/getConfigList";
    public static final String REQ_URI_BOM_CONFIG = "/bomservice";
    public static final String OPEN_CASES = "/openCases";
    public static final String POPULATE_CONFIG_DETAILS = "/populateConfigDetails";
    public static final String CONFIG_ID = "configId";
    public static final String GET_PARAMETER_LIST = "/getParameterList";
    public static final String UPDATE_BOM_CFG_ITEMS = "/saveBOMDetails";
    public static final String STR_USERNAME = "strUserName";

    // MassApply Config
    public static final String REQ_URI_MASS_APPLY_CFG = "/massapplycfgservice";
    public static final String GET_CTRL_CONFIGS = "/getControllerConfigs";
    public static final String GET_FFD_CONFIGS = "/getFFDConfigs";
    public static final String GET_FRD_CONFIGS = "/getFRDConfigs";
    public static final String GET_EDP_CONFIGS = "/getEDPConfigs";
    public static final String GET_RCI_CONFIGS = "/getRCIConfigs";
    public static final String CTRL_CFG_OBJID_NOT_PROVIDED = "CTRL_CFG_OBJID_NOT_PROVIDED";
    public static final String CTRL_CFG_OBJID = "ctrlCfgObjId";
    public static final String IS_CASE_MASS_APPLY_CFG = "isCaseMassApplyCfg";
    public static final String INVALID_CTRL_CFG_OBJID = "INVALID_CTRL_CFG_OBJID";
    public static final String VERIFY_CONFIG_TEMPLATES = "/verifyConfigTemplates";
    public static final String EDP_OBJID = "edpObjId";
    public static final String FFD_OBJID = "ffdObjId";
    public static final String FRD_OBJID = "frdObjId";
    public static final String GET_ASSETS_HEADER = "getAssetsData";
    public static final String HEADER_SEARCH_CASES = "/getHeaderSearchCasesData";
    public static final String REQ_URI_CONFIG_MAINTENANCE_SERVICE = "/configMaintenanceService";
    public static final String GET_CTRL_CFGS = "/getControllerConfigs";
    public static final String CTRL_CFG_OBJ_ID = "ctrlCfgObjId";
    public static final String CFG_FILE_NAME = "cfgFileName";
    public static final String GET_CONFIG_TEMPLATE_DETAILS = "/getCtrlCfgTemplates";
    public static final String CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED = "CONTROLLER_CONFIG_VALUE_IS_NOT_PROVIDED";
    public static final String CONFIG_FILE_VALUE_IS_NOT_PROVIDED = "CONFIG_FILE_VALUE_IS_NOT_PROVIDED";
    public static final String GET_EFI_DETAILS = "/getEFIDetails";
    public static final String GET_PARAMETER_TITLE = "/getParameterTitle";
    public static final String POPULATE_FFD_DETAILS = "/populateFFDDetails";
    public static final String POPULATE_FRD_DETAILS = "/populateFRDDetails";
    public static final String SAVE_FFD_TEMPLATE = "/saveFFDTemplate";
    public static final String SAVE_FRD_TEMPLATE = "/saveFRDTemplate";
    public static final String REMOVE_FFD_TEMPLATE = "/removeFFDTemplate";
    public static final String REMOVE_FRD_TEMPLATE = "/removeFRDTemplate";
    public static final String GET_FAULT_SOURCE = "/getFaultSource";
    public static final String GET_EDP_TEMPLATE = "/getEDPTemplate";
    public static final String GET_DEFAULT_VALUES_RANGE = "/getDefaultValuesRange";
    public static final String GET_MAXIMUM_VERSION = "/getMaximumVersion";
    public static final String VERSION_ID = "versionId";
    public static final String CONFIG_VALUE = "configValue";
    public static final String GET_CURRENT_TEMPLATE = "/getCurrentTemplate";
    public static final String CONFIG_FILE = "configFile";
    public static final String GET_CURRENT_STATUS = "/getCurrentStatus";
    public static final String GET_TITLE = "/getTitle";
    public static final String GET_STATUS_DETAILS = "/getStatusDetails";
    public static final String CHECK_FAULT_RANGE = "/checkFaultRange";
    public static final String GET_MAX_PARAMETER_COUNT = "/getMaxParameterCount";
    public static final String CREATE_NEW_EFI = "/createNewEFI";
    public static final String USER_NAME = "userName";
    public static final String GET_MAX_TEMPLATE_NUMBER = "/getMaxTemplateNo";
    public static final String GET_TEMPLATE_MAX_VER_NUMBER = "/getTempMaxVerNo";
    public static final String TEMPLATE_NO = "templateNo";
    public static final String TEMPLATE_NUMBER_IS_NOT_PROVIDED = "TEMPLATE_NUMBER_IS_NOT_PROVIDED";
    public static final String GET_EDP_PARAMETRES = "/getEDPParameters";
    public static final String CONDITION = "condition";
    public static final String SEARCH_VAL = "searchVal";
    public static final String SEARCH_BY_VALUE_IS_NOT_PROVIDED = "SEARCH_BY_VALUE_IS_NOT_PROVIDED";
    public static final String SEARCH_CONDITION_IS_NOT_PROVIDED = "SEARCH_CONDITION_IS_NOT_PROVIDED";
    public static final String SEARCH_VAL_IS_NOT_PROVIDED = "SEARCH_VAL_IS_NOT_PROVIDED";
    public static final String GET_ADDED_EDP_PARAMETRES = "/getAddedEDPParameters";
    public static final String TEMP_OBJ_ID = "tempObjId";
    public static final String TEMPLATE_OBJ_ID_VALUE_IS_NOT_PROVIDED = "TEMPLATE_OBJ_ID_VALUE_IS_NOT_PROVIDED";
    public static final String SAVE_EDP_TEMPLATE = "/saveEDPTemplate";
    public static final String GET_PRE_NEXT_EDP_DETAILS = "/getPreNextEDPDetails";
    public static final String VERSION_NO = "versionNo";
    public static final String VERSION_NUMBER_IS_NOT_PROVIDED = "VERSION_NUMBER_IS_NOT_PROVIDED";
    // delete configuration
    public static final String GET_CONTROLLER_CONFIGURATIONS = "/getControllerConfigs";
    public static final String GET_CONTROLLER_CONFIGURATION_TEMPLATES = "/getControllerConfigTemplates";
    public static final String CONTROLLER_CONFIGURATION = "cntrlCnfg";
    public static final String CONFIGURATION_FILE = "cnfgFile";
    public static final String DELETE_VEHICLE_CFG = "/deleteVehicleCfg";
    public static final String CNTRLCNFG_NOT_PROVIDED = "CNTRLCNFG_NOT_PROVIDED";
    public static final String CNFGFILE_NOT_PROVIDED = "CNFGFILE_NOT_PROVIDED";

    // Apply Config
    public static final String GET_ON_BOARD_SOFTWARE_VERSION = "/getOnboardSoftwareVersion";
    public static final String GET_CGF_TEMPLATE_VERSIONS = "/getCfgTemplateVersions";
    public static final String CFG_FILE = "cfgFile";
    public static final String TEMPLATE_OBJID = "templateObjId";
    public static final String TEMPLATE_OBJID_NOT_PROVIDED = "TEMPLATE_OBJID_NOT_PROVIDED";
    public static final String INVALID_TEMPLATE_OBJID = "INVALID_TEMPLATE_OBJID";
    public static final String CFG_FILE_NOT_PROVIDED = "CFG_FILE_NOT_PROVIDED";
    public static final String GET_SPECIFIC_ASSET_NUMBERS = "/getSpecificAssetNumbers";
    public static final String APPLY_CFG_TEMPLATES = "/applyCfgTemplates";
    public static final String ITEMS_ARE_NOT_SELECTED_FOR_APPLYING = "ITEMS_ARE_NOT_SELECTED_FOR_APPLYING";
    public static final String CFG_TEMPALTES_NOT_PROVIDED = "CFG_TEMPALTES_NOT_PROVIDED";
    public static final String EFI_OBJID = "efiObjId";

    // EFI Apply
    public static final String APPLY_EFI_CFG = "/applyEFICfg";

    public static final String GET_GEO_LOCATION_VALUES = "/getGeoLocationValues";
    public static final String GET_ATS_PARAMETERS = "/getATSParameters";
    public static final String IS_GE_RULE_AUTHOR = "isGERuleAuthor";
    public static final String MAXIMUM_10_COMPLEX = "MAXIMUM_10_COMPLEX";
    public static final String NO_RULE_PERMISSION_TO_VIEW = "NO_RULE_PERMISSION_TO_VIEW";
    public static final String NO_RULE_PERMISSION_TO_CREATE = "NO_RULE_PERMISSION_TO_CREATE";
    public static final String NO_RULE_PERMISSION_TO_MODIFY = "NO_RULE_PERMISSION_TO_MODIFY";
    public static final String ALERTS = "Alert";
    public static final String RULE_INVALID_VALUE = "RULE_INVALID_VALUE";
    public static final String GET_COLVALUES = "/getColValues";

    public static final String SUBMIT_HC_MOBILE_REQUEST = "/submitHealthCheckMobileRequest";
    public static final String NO_OF_SAMPLES = "noOfSamples";
    public static final String STR_NO = "N";
    public static final String STR_YES = "Y";
    public static final String USER_TYPE_INTERNAL = "internal";
    public static final String USER_TYPE_EXTERNAL = "external";
    public static final String HC_MCS_SENDMESSAGE_URL = "HC_MCS_SENDMESSAGE_URL";
    public static final String HC_MCS_SENDMESSAGE_USERNAME = "HC_MCS_SENDMESSAGE_USERNAME";
    public static final String HC_MCS_SENDMESSAGE_PASSWORD = "HC_MCS_SENDMESSAGE_PASSWORD";
    public static final String HEALH_CHECK_SAMPLE_RATE_LOOKUP = "OMD_HC_REQUEST_EXT_SAMPLERATE";
    public static final String HEALH_CHECK_NO_OF_SAMPLES_LOOKUP = "OMD_HC_REQUEST_EXT_POSTSAMPLES";
    // Added for MDSC Admin
    public static final String GET_CM_ML_DETAILS = "/getCMorMLDetails";
    public static final String ROLE_ID_NOT_PROVIDED = "ROLE ID NOT PROVIDED";
    public static final String REMOVED_ROLE_ID = "removedRoleId";
    public static final String EOA_ALIAS = "eoaAlias";
    public static final String EOA_ALIAS_VALUE_NOT_PROVIDED = "EOA_ALIAS_VALUE_NOT_PROVIDED";
    public static final String CHECK_EOA_ALIAS_EXIST = "/checkEOAAliasExist";

    public static final String GET_DELIVER_RX_URL = "/getDeliverRxURL";
    public static final String GET_MODEL_BASED_FILTER = "/getModelByFilter";
    public static final String ASSET_LIST = "assetList";

    // added for delete button in roles tab
    public static final String DELETE_ROLE = "/deleteRole";
    public static final String DEVICE_EGA = "EGA";
    public static final String EXCEPTION_EOA_3032 = "EOA_3032";
    public static final String GET_HEALTH_RULES_FIRING = "/getHealthRulesFiring";
    public static final String GET_HEALTH_RULES = "/getHealthRules";
    public static final String HEALTH_RULES_LIST_ID = "INVALID_HEALTH_RULES_LIST";
    public static final String ASSETTYPE_LEGACY = "LEGACY";
    public static final String HC_REQ_TYPE_MOBILITY = "Mobility-HC";
    public static final String UPDATE_LAST_LOGIN_DETAILS = "/updateLastLoginDetails";
    public static final String UPDATE_LAST_LOGIN_AND_FROM_DETAILS = "/updateLastLoginAndFromDetails/userId/{userIdValue}/userSeqId/{userSeqIdValue}/lastLoginFrom/{lastLoginFrom}";
    // added for Contact screen
    public static final String REQ_URI_CONTACT_SITE_SERVICE = "/contactSiteService";
    public static final String PH_NO = "phNo";
    public static final String SITE_ID = "siteId";
    public static final String SITE_NAME = "siteName";
    public static final String CONTACT_STATUS = "contactStatus";
    public static final String VIEW_CONTACT_DETAILS = "/viewContactDetails";
    public static final String CONTACT_OBJID = "contactObjId";
    public static final String GET_CONTACT_SECONDARY_SITES = "/getContactSecondarySites";
    public static final String ADD_CONTACT_SECONDARY_SITE = "/addContactSecondarySite";
    public static final String REMOVE_CONTACT_SECONDARY_SITE = "/removeContactSecondarySite";
    public static final String UPDATE_CONTACT = "/updateContact";

    public static final String CONTACT_SITE_SERVICE = "/contactsiteservice";
    public static final String GET_SITES = "/getSites";
    public static final String VIEW_SITE_DETAILS = "/viewSiteDetails";
    public static final String SITE_OBJID = "siteObjId";
    public static final String UPDATE_SITE = "/updateSite";
    public static final String CREATE_SITE = "/createSite";
    public static final String GET_ADDRESS = "/getAddress";
    public static final String ADDRESS = "address";
    public static final String ZIP_CODE = "zipCode";
    public static final String ADDRESS_FILTER = "addrFilter";
    public static final String CITY_FILTER = "cityFilter";
    public static final String ZIPCODE_FILTER = "zipCodeFilter";
    public static final String STATE_FILTER = "stateFilter";
    public static final String VIEW_ADDRESS_DETAILS = "/viewAddressDetails";
    public static final String ADDRESS_OBJID = "addrObjId";
    public static final String GET_COUNTRY_LIST = "/getCountryList";
    public static final String GET_COUNTRY_TIMEZONES = "getCountryTimeZones";
    public static final String GET_COUNTRY_STATES = "/getCountryStates";
    public static final String CONTACT_OBJID_NOT_PROVIDED = "CONTACT_OBJID_NOT_PROVIDED";
    public static final String COUNTRY_NOT_PROVIDED = "COUNTRY_NOT_PROVIDED";
    public static final String ADDRESS_OBJID_NOT_PROVIDED = "ADDRESS_OBJID_NOT_PROVIDED";
    public static final String ADD_OR_UPDATE_ADDRESS = "/addOrUpdateAddress";
    public static final String ONLY_SIMRULES_ALLOWED = "ONLY_SIMRULES_ALLOWED";
    public static final String ONLY_SIMRULES_ALLOWED_CL = "ONLY_SIMRULES_ALLOWED_CL";

    public static final String DELETE_ROLE_LIST = "/getDeleteRoleList";
    public static final String DELETE_ROLE_UPDATE_LIST = "/deleteRoleUpdateList";
    public static final String CURRENT_ROLE_NOT_PROVIDED = "CURRENT_ROLE_NOT_PROVIDED";
    public static final String CHANGED_ROLE_NOT_PROVIDED = "CHANGED_ROLE_NOT_PROVIDED";
    public static final String TURNOVER_SERVICE = "/turnoverservice";
    public static final String GET_INBOUND_REPORT = "/getInboundReportData";
    public static final String GET_ASSETS_LOCATION_FROM_SHOP = "/getAssetsLocationFromShop";
    // added for GPOC turnover (general and comm notes)
    public static final String GPOC_SERVICE = "/gpocservice";
    public static final String ADD_GENERAL_NOTES = "/addGeneralNotes";
    public static final String GENERALnOTESDESC = "genralNotesDesc";
    public static final String GET_All_GENERAL_NOTES = "/showAllGeneralNotes";
    public static final String REMOVE_GENERAL_NOTES = "/removeGeneralNotes";
    public static final String ADD_COMM_NOTES = "/addCommNotes";
    public static final String GET_All_COMM_NOTES = "/showAllcommnotes";
    public static final String REMOVE_COMM_NOTES = "/removeCommNotes";

    // added for alertSubscription
    public static final String GET_ATS_CUSTOMERS = "/getATSCustomers";
    public static final String ACT_DEACT_ALERT = "/activateOrDeactivateAlert";
    public static final String EDIT_DLVRY_FRMT = "/editAlert";
    public static final String DELETE_ALERT = "/deleteAlert";
    public static final String GET_ASSET_FOR_CUSTOMER = "/getAssetForCustomer";
    public static final String GET_ALERT_FOR_CUSTOMER = "/getAlertForCustomer";
    public static final String ADD_ALERT_SUBSCRIPTION_DATA = "/addAlertSubscriptionData";
    public static final String GET_ALERT_SUBSCRIPTION_DATA = "/getAlertSubscriptionData";
    public static final String REGION_TYPE = "REGION_TYPE";
    public static final String FLEET_TYPE = "FLEET_TYPE";
    public static final String ASSET_TYPE = "ASSET_TYPE";
    public static final String GET_ALERT_SUB_ASSET_COUNT = "/getAlertSubAssetCount";
    public static final String GET_ALL_SHOPS = "/getAllShops";
    public static final String GET_ALL_SHOP_CHECK = "/getRestrictedAlertShop";

    public static final String GET_ALL_MODELS = "/getAllModels";
    public static final String ALERT_SERVICE = "/alertservice";
    public static final String ALERTHISTORYDETAILS = "/getAlertHistoryDetails";
    public static final String STR_MODEL = "strModel";
    public static final String GET_MULTI_USERS = "/getMultiUsers";
    public static final String MODEL_VAL = "modelVal";
    public static final String GET_ALERT_RULES = "/getConfigAlertForCustomer";
    public static final String USERTYPE = "userType";
    public static final String GET_USER_EMAIL_ID = "/getUserEmailId";
    public static final String GET_HEALTH_RULES_FIRING_DETAILS = "/getHealthRulesFiringDetails";
    public static final String GET_DIAGNOSTIC_RULES_FIRING_DETAILS = "/getDiagnosticRulesFiringDetails";
    public static final String REQ_URI_CASE_TREND_SERVICE = "/casetrendservice";
    public static final String GET_OPEN_RX_COUNT = "/getOpenRXCount";
    public static final String GET_CASE_TREND_COUNT = "/getCaseTrend";
    public static final String GET_CASE_TREND_STAT_COUNT = "/getCaseTrendStatistics";
    public static final String GET_OPEN_COMM_RX_COUNT = "/getOpenCommRxCount";
    public static final String GET_CASE_CONVERSION_DETAILS = "/getCaseConversionDetails";
    public static final String GET_CASE_CONVERSION_PERCENTAGE = "/getCaseConversionPercentage";
    public static final String GET_TOP_NO_ACTION_RX_DETAILS = "/getTopNoActionRXDetails";
    public static final String GET_COMM_NOTES_DETAILS = "/getCommNotesDetails";
    public static final String GET_SHIP_UNIT_DETAILS = "/getShipUnitDetails";
    public static final String GET_INFANCY_FAILURE_UNITS = "/getInfancyFailureDetails";
    public static final String GET_GENERAL_NOTES_DETAILS = "/getGeneralNotesDetails";
    public static final String GET_REPEATERS_RX_LIST = "/getRepeaterRxsList";
    public static final String GET_ALERT_HST_POPULATE_DATA = "/getAlertHisotryPopulateData";
    public static final String GET_SUBMENU_LIST = "/getSubMenuList";

    // Added by Sri for Hits- for Alert Forensics
    public static final String FETCH_RECENT_ALERT_FIRING = "/getRecentAlertFiring";
    public static final String FETCH_ALERT_RUNS = "/getAlertRuns";
    public static final String FETCH_RULES_FOR_FAMILY = "/getAlertFamilyRules";
    public static final String FETCH_RULE_PARM_DATA = "/getRuleParmData";
    public static final String FETCH_FIRING_DETAILS = "/getFiringDetails";
    public static final String FETCH_RULE_MISS_DETAILS = "/getRuleMissDetails";

    public static final String GET_RULE_BY_FAMILY = "/getRuleByFamily";

    public static final String DATE_RANGE_START = "dateStart";
    public static final String DATE_RANGE_END = "dateEnd";
    public static final String RUN_OBJ_ID = "runObjId";
    public static final String ALERT_FIRING_ID = "alertFiringID";
    public static final String RULEE_TYPE = "ruleType";
    public static final String RULE_FAMILY = "ruleFamily";
    public static final String DIAG_SERVICE_ID = "diagServiceId";
    public static final String VEH_HDR = "vehHdr";
    public static final String HIT_ROAD_NO = "roadNo";
    public static final String HIT_RULE_TITLE = "ruleTitle";

    public static final String HIT_RULE_TYPE = "ruleType";
    public static final String HIT_RULE_FAMILY = "family";
    public static final String HIT_RULE_ID = "ruleID";

    public static final String IS_GE_USER = "isGEUser";
    public static final String IS_AURIZON_ALERT_USER = "isArznAlertUser";
    public static final String CUST_UOM = "custUOM";
    public static final String GET_ROADNUM_HEADERS = "/getRoadNoHdrs";
    // Save Rx
    public static final String GET_RX_EXECUTION_DETAILS = "/getRxExecutionDetails/rxCaseId/{rxCaseId}";
    public static final String GET_REP_CODE_DETAILS = "/getRepCodeDetails";
    public static final String GET_LOOKUP_REP_CODE_DETAILS = "/getLookUpRepCodeDetails";
    public static final String REPAIR_CODE_LIST = "repaircodeList";
    
    /* Unit Renumbering */
    public static final String GET_ASSETS_UNIT_RENUM = "getAssetsForUnitRenum";
    public static final String UPDATE_UNIT_NUMBER = "/updateUnitNumber";
    public static final String UPDATE_UNIT_HEADER = "/updateUnitHeader";
    
    //Added as part of Phone field validation in Contact screen
    public static final String GET_ISD_CODE = "/getISDCode";
    public static final String VALIDATE_URL="/validateURL"; 
    public static final String FILENAME = "filename";  
    public static final String LASTLOGINFROMMOB = "Mob";
    public static final String ROLE = "Role";
    public static final String MOBILE_APP_CONFIGURATION = "MOBILE APP CONFIGURATION";
    public static final String TASK_ID = "taskId";
    
    // Added by Sriram.B(212601214) for SMS feature
    public static final String UPDATE_PHONE = "/updatephone";
    public static final String GET_USER_PHONE_NO ="/getUserPhoneNo";
    public static final String GET_USER_PHONE_COUNTRY_CODE ="/getUserPhoneCountryCode";
    public static final String GET_OTP_PARAMETERS ="/getOTPParameters";
    
    public static final String GET_ASSET_PANEL_PARAMETERS="/getAssetPanelParameters";
    
    public static final String TOOLS_SERVICE = "toolsservice";
    public static final String GET_NEXT_SCHEDULE_RUN = "/getNextScheduleRun/vehicleObjId/{vehicleObjId}";
    public static final String GET_LAST_DIAGNOSTIC_RUN_DETAILS = "/getLastDiagnosticRunDetails/vehicleObjId/{vehicleObjId}";
    public static final String RUN_TOOLS = "/runTools";
    public static final String GET_DIAGNOSTIC_SERVICES="/getDiagnosticServices/vehicleObjId/{vehicleObjId}";
    public static final String INVALID_DATE_FORMAT = "INVALID_DATE_FORMAT";
    public static final String REQUIRED_INPUT = "REQUIRED_INPUT";
    public static final String GET_CASESCORE_REPAIR_CODE="/getCaseScoreRepairCodes";
    
    // Added for US225214: Heat Map Feature
    public static final String HEATMAP_SERVICE = "/heatmapservice";
    public static final String HEATMAP_MODELS = "/getHeatMapModels";
    public static final String HEATMAP_ASSET_HEADERS = "/getHeatMapAssetHeaders";
    public static final String HEATMAP_ASSET_NUMBERS = "/getHeatMapAssetNumbers";
    public static final String HEATMAP_FAULTS = "/getHeatMapFaults";
    public static final String HEATMAP_FILTERS_RESULT = "/getHeatMapFilterFaults";
 public static final String RUN_TOOLS_NOW = "/runToolsNow";
 //Added by Murali Ch
    public static final String UPDATE_GEN_OR_COMM_NOTES = "/updateGenOrCommNotes";
    public static final String END_SCORING_FLAG="endScoringFlag";
   
    //Added by Koushik for OHV Reports
    public static final String REQ_URI_OHV_REPORTS = "ohvReportsService";
    public static final String REPORTS_GET_RX = "getRx";
    public static final String REPORTS_GET_TRUCK_EVENTS = "getTruckEvents";
    public static final String REPORTS_GET_TRUCK_INFO = "getTruckInfo";
    public static final String REPORTS_GET_COM_STATUS = "getComStatus";
    public static final String REPORTS_GET_MINE = "getMine";
    public static final String REPORTS_GET_TOP_EVENTS = "getTopEvents";
    public static final String REPORTS_GET_TRUCK_GRAPH_DATA = "getTruckGraphData";
    public static final String REPORTS_FROM_DATE = "fromDate";
    public static final String REPORTS_TO_DATE = "toDate";
    public static final String REPORTS_GET_TRUCK_PARAM = "getTruckVariablesParam";
    public static final String REPORTS_GET_TRUCK_PARAM_LIST = "getTruckVariablesParamList";
    public static final String REPORTS_GET_TRUCK_PARAM_LIST_SCHEDULER = "getTruckVariablesParamListScheduler";
    
    //Added by Murali Ch for US117241
    public static final String FROM_SCREEN="fromScreen";
    public static final String CUST_FDBK_OBJ_ID_NOT_PROVIDED = "CUST_FDBK_OBJ_ID_NOT_PROVIDED";
    public static final String USER_SEQ_ID_VALUE = "userSeqIdValue";
	public static final String PREVIEW_PDF = "/previewPdf";
	public static final String RUN_ON_PAST_DATA = "/runToolsOnPastData";
	public static final String GET_LOCOMOTIVE_COMMUNICATION_DETAILS = "/getLocomotiveCommunicationDetails";
	public static final String GET_RULE_CACHE_REFRESH = "/getNextRuleCacheRefresh/ruleType/{ruleType}";
	public static final String GET_SOLUTIONS_PARAMETERS = "/getSolutionParameters";
	//Added by Shikha
    public static final String GET_SUBSCRIBERS = "/getSubscribers";
    
    //Added for turnover report changes    
    public static final String GET_CALL_COUNT_BY_LOCATION = "/getCallCountByLocation";
    public static final String GET_CUST_BREAK_DOWN_MINS = "/getCustBrkDownByMins";
    public static final String GET_CALL_COUNT_BY_BUSS_AREA="/getCallCntByBusnsArea";
    public static final String GET_WEEKLY_CALL_COUNT_BY_BUSS_AREA="/getWeeklyCallCntByBusnsArea";
    public static final String GET_VEH_CALL_COUNT_BY_BUSS_AREA="/getVehCallCntByBusnsArea";
    public static final String GET_MANUAL_CALL_COUNT="/getManualCallCount";
	 public static final String GET_RX_VISUALIZATION_DETAILS = "/getRxVisualizationDetails";
	public static final String GET_RX_VISUALIZATION_PLOT_INFO = "/getVizPlotInformations/rxObjId/{rxObjId}";
	//Added for AHC template changes
    public static final String GET_AHC_CONFIGS="/getAHCConfigs";
    public static final String AHC_OBJID = "ahcObjId";
    
	/*added by venkatesh for Auto HC template*/
	public static final String REQ_URI_AUTO_HC_TEMPLATE = "autoHCTemplateService";
	public static final String AUTO_HC_TEMPLATE_GET = "/getTemplateDropDwn";
	public static final String AUTO_HC_FAULT_CODE_REC_TYPE_GET = "/getFaultCodeRecTypeDropDwn";
	public static final String MESSAGE_DEF_ID_DATA_GET = "/getMessageDefIdDropDwn";
	public static final String CTRL_CONFIG_NAMEVALUE = "ctrlConfig";
	public static final String CONFIG_FILE_NAMEVALUE = "configFile";
	public static final String POST_NEW_TEMPLATE_DATA = "/postAutoHCNewTemplate";
	public static final String AH_TEMPLATE_OBJ_ID = "tempObjId";
    public static final String AH_SAMPLE_RATE = "sampleRate";
    public static final String AH_POST_SAMPLES = "postSamples";
    public static final String AH_FREQUENCY = "frequency";
    public static final String AH_FCRT_OBJID = "fcrtObjid";
    public static final String AH_TEMPLATE_NO = "templateNo";
    public static final String AH_VERSION_NO = "versionNo";
    public static final String AH_TITLE = "title";
    public static final String AH_STATUS = "status";
    public static final String AH_CTRL_CONFIG_OBJID = "configCtrlObjid";
    public static final String GET_AUTO_HC_COMPLETE_DETAILS = "/getComAHCDetails";
    public static final String VALIDATE_VEH_BOMS="/validateVehBoms";
    
  //Added for US251801: Rx Update Workflow - Migrate this process within EoA/OMD - Ryan & Todd
    public static final String RXCHANGE_SERVICE = "/rxchangeservice";
    public static final String RXCHANGE_OVERVIEW_DATA = "/getRxChangeOverviewData";
    public static final String SAVE_RXCHANGE_DETAILS = "/saveRxChangeDetails";
    public static final String GET_RXCHANGE_USER_CASES = "/getUserCases";
    public static final String GET_RXCHANGE_RX_TITLE = "/getRxTitle";
    public static final String GET_RXCHANGE_REQUESTORS = "/getRxChangeRequestor";
    public static final String GET_RXCHANGE_LOOK_UP = "/getRxChangeLookUp";
	public static final String GET_RX_DELIVERY_ATTACHMENTS = "/getRxDeliveryAttachments";
    public static final String GET_SOLUTION_CREATED_BY = "/getSolutionsCreatedBy";
    public static final String RX_CREATED= "createdBy";//Created By
	public static final String RX_LST_FROM_DATE= "lastUpdatedFromDate";
	public static final String RX_LST_TO_DATE= "lastUpdatedToDate";
	public static final String RX_FRM_DATE= "createdByFromDate";
	public static final String RX_TO_DATE= "createdByToDate";
	public static final String RXCHANGE_MODEL_FOR_RX_TITLE = "/getModelForRxTitle";
	public static final String RXCHANGE_SAVE_UPDATE_ADMIN_DETAILS = "/saveUpdateRxChangeAdminDetails";
	public static final String RXCHANGE_ADMIN_DATA = "/getRxChangeAdminData";
	public static final String GET_RXCHANGE_ADMIN_USERS = "/getRxChangeAdminUsers";
	
	public static final String RX_TRANSLATION_SERVICE = "/rxTranslationService";
	public static final String GET_RX_TRANSLATION_LANGUAGE = "/getRxTranslationLanguage";
	public static final String GET_RX_TRANSLATION_LAST_MODIFIED_BY = "/getRxTranslationLastModifiedByList";
	public static final String GET_RX_TRANSLATION_LIST = "/getFilteredRxTranslation";
	public static final String GET_RX_TRANS_DETAIL = "/getRxTransDetail";
	public static final String SAVE_RX_TRANS_DETAIL = "/saveRxTranslation";
	public static final String GET_EOA_ONSITE_ROLES = "/getEoaOnsiteRoles";
	public static final String GET_ROR_RULES_DETAILS = "/getRoRRuleInformation";
	public static final String ROR_RULES="rorRules";
	public static final String NON_ROR_RULES_ERROR="NON_ROR_RULES_ERROR";
	public static final String NON_ROR_RULES_ERROR_MESSAGE="There are circular references back to the current rule from the following rule(s):";
	public static final String GET_CASE_RX_DELV_DETAILS = "/getCaseRXDelvDetails/caseId/{caseId}";
    public static final String GET_NO_CM_ROLES = "/getNoCMRoles";
    
    public static final String USER_CUSTOMER = "userCustomer";
	public static final String USER_TIMEZONE = "userTimeZone";
	public static final String USER_TIMEZONE_CODE = "userTimeZoneCode";
	public static final String GET_LOOKUP_VALUE_TOOLTIP = "/getLookupValueTooltip";
	//Added for RCI Apply and Delete
    public static final String RCI_OBJID = "rciObjId";
    public static final String RE_APPLY_TEMPLATE = "/reApplyTemplate";
    public static final String GET_RXCHANGE_AUDIT_TRIAL_INFO = "/getRxChangeAuditTrailInfo";
    public static final String RX_CHANGE_REQ_OBJ_ID="rxChangeReqObjId";
    public static final String SEND_RX_CHANGE_ESCALATION="sendRxChangeEscalation";
    public static final String SAVE_NEWS = "/saveNews";
	public static final String NEWS_SERVICE = "/newsResource";
	public static final String GET_TEMPLATE_REPORT = "/getTemplateReport";
	
	public static final String ENABLE_CUSTOM_COLUMNS = "enableCustomColumns";
	public static final String GET_ALL_NEWS = "/getAllNews";
	public static final String DELETE_NEWS = "/deleteNews";
	
	
	 //Added by Murali D for SoftwareHistory details
    public static final String SOFTWARE_HISTORY_DETAILS = "softwareHistoryDetails";
    public static final String GET_SEARCH_SOLUTION = "/getSearchSolution";
	public static final String RX_RESEARCH_SERVICE ="/rxResearchService";
	public static final String SELECT_BY_OPTIONS ="SELECT_BY_OPTIONS";
	public static final String RX_TYPE = "rxType";
	public static final String SELECTBY_NOT_PROVIDED = "SELECTBY_NOT_PROVIDED";
	public static final String CONDITION_NOT_PROVIDED = "CONDITION_NOT_PROVIDED";
	public static final String SEARCH_VAL_NOT_PROVIDED = "SEARCH_VAL_NOT_PROVIDED";
	public static final String RXTYPE_NOT_PROVIDED = "RXTYPE_NOT_PROVIDED";
	public static final String GET_SEARCH_GRAPH_DATA = "/getGraphicalData";
	public static final String POPULATE_REPAIRCODE_DETAILS = "/populateRepairCodeDetails";
	public static final String ISGOODFDBK = "isGoodFdbk";
	public static final String GET_LOCO_ID ="/getLocoId";
	
	public static final String RE_NOTIFY_TEMPLATE_ASSOCIATION="/reNotifyTemplateAssociation";
	public static final String UPDATE_RCI_TEMPLATE="/updateRCITemplate";
	public static final String SAVE_RCI_TEMPLATE = "/saveRCITemplate";
	public static final String ADMIN_DELETE_USER_DETAILS= "/deleteUsers";
	public static final String GET_BACKGROUND_IMAGE= "/getBackgroundImage";
	public static final String IS_ADMIN_PAGE= "isAdminPage";
	public static final String ADMIN_UPDATE_READ_FLAG= "/updateReadFlag";

	
}
