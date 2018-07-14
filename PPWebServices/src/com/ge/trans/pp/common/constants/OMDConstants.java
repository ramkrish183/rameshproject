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
package com.ge.trans.pp.common.constants;

import java.util.HashMap;
import java.util.Map;

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
    
    public static final String PROPERTIES_FILE_NAME = "com/ge/trans/pp/common/ErrorCode.properties";
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
    public static final String USERLANGUAGE_NOT_PROVIDED = "USERLANGUAGE_NOT_PROVIDED";
    public static final String LANGUAGE_NOT_PROVIDED = "LANGUAGE_NOT_PROVIDED";
    public static final String CASEID_NOT_PROVIDED = "CASEID_NOT_PROVIDED";
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
    public static final String RULE_ID= "ruleId";
    public static final String RULE_TYPE= "ruleType";
    public static final String RULE_TITLE="ruleTitle";
    public static final String TO_DATE_FIELD="dtTo";
    public static final String DAYS="days";
    public static final String NO_OF_DAYS="days";

    //Added for sprint 8 websevices changes
    public static final String FAULT_CODE = "faultCode";
    public static final String SUB_ID = "subId";
    public static final String SERVICE_NAME = "serviceName";
    
    public static final String LAST_UPDATED_BY="lastUpdatedBy";
    public static final String SOLUTION_NOTES="solutionNotes";
    public static final String SOLUTION_TITLES="solutionTitles";
    
    public static final String FAMILY="family";
    public static final String CLONERULE="cloneRule";
    public static final String CLONE="N";
    public static final String RULE_LEVEL="level";
    public static final String RULE_SIMPLE="simple";
    public static final String RULE_COMPLEX="complex";
    public static final String OMD_RULE_TYPE = "OMD_RULE_TYPE" ;
    public static final String OMD_RULE_STATUS="OMD_RULE_STATUS";
    public static final String OMD_SUBSYSTEM = "SUBSYSTEM";
    public static final String RULE_VERSION="OMD_RULE_VERSION"; 
    public static final String NULL_SOLUTION_ID_STATUS="NULL_SOLUTION_ID_STATUS";
    public static final String PP_VEHICLE_NUMBER="vehicleNumber";
    public static final String PP_VEHICLE_HEADER="vehicleHeader";
    public static final String PP_BUS_ORG_ID="busOrgId";
    public static final String PP_NO_OF_DAYS="noOfDays";
    public static final String ASSET_SERVICE="/assetservice";
    public static final String GET_CURRENT_PPSTATUS ="/getCurrentPPStatus";
    public static final String GET_PPSTATES ="/getPPStates";
    public static final String GET_PPSTATUS_HISTORY="/getPPStatusHistory";
    public static final String PROXIMITY_SERVICE = "/proximityservice";
    public static final String IDLE_REPORT_SERVICE = "/idlereportservice";
    public static final String NOTIFICATION_SERVICE = "/notificationservice";
       //Added for Exception Handling --- Start 
        public static final String FATAL_ERROR = "FATAL_ERROR";
        public static final String MAJOR_ERROR = "MAJOR_ERROR";
        public static final String MINOR_ERROR = "MINOR_ERROR";
    public static final String PP_CUSTOMER_REGIONS = "/getPPCustomerRegions/customerId/{customerId}";
    public static final String PP_NOTIFICATION_HISTORY = "/getPPNotificationHistory";
    // added for proximity
    public static final String GET_COUNTRY_STATE_LIST = "/getAllPPCountryStates";
    public static final String GET_CITIES_FOR_GIVEN_STATE = "/getAllPPCityList/countryCode/{countryCode}/stateCode/{stateCode}";
    public static final String GET_CUST_ROAD_INITIALS = "/getRoadInitials/customerId/{customerId}";
    public static final String GET_ASSET_SERVICE_STATUS = "/getPPAssetServiceStatus";
    public static final String GET_PROXIMITY_DETAILS = "/getPPProximityDetails";
    public static final String COUNTRYCODE = "countryCode";
    public static final String GET_IDLE_REPORT_SUMMARY = "/getIdleReportSummary";
    public static final String GET_IDLE_REPORT_DETAILS = "/getIdleReportDetails";
    public static final String STATECODE = "stateCode";
    public static final String CUSTOMERID = "customerId";
    public static final String PP_NOTIFICATION_ALARM = "ALARM";
    public static final String PP_NOTIFICATION_ALERT = "ALERT";
    public static final String PP_NOTIFICATION_NORMAL = "NORMAL";
    public static final String GET_PP_ASSETS = "/getPPAssets";
    // added for geofenceReport
    public static final String GEOFENCE_REPORT_SERVICE = "/geofencereportservice";
    public static final String GEOFENCE_REPORT = "/getGeofenceReport";
    public static final String MANAGE_GEOFENCE_SERVICE = "/managegeofenceservice";
    public static final String MANAGE_GEOFENCE_DATA = "/getGeofenceProximityData";
    public static final String VALIDATE_GEOFENCE_DATA = "/validateGeofenceData";
    public static final String DELETE_GEOFENCE_DETAILS = "/deleteGeofenceDetails";
    public static final String SAVE_UPDATE_GEOFENCE_DETAILS = "/saveUpdateGeofenceDetails";
    
    
        public static final String USER_ID = "userId";
        public static final String SEQUENCE_ID = "SEQUENCE_ID";
        public static final String DELIVERY_FRMT = "DELIVERY_FRMT";
       
        public static final String GET_PPASSET_MODEL_VALUE="/getPPAssetModel";
        public static final String RNH_ID="rnhId";
        public static final String RN_ID="rnId";
        public static final String CUSTOMER_NAME="customerName";
        public static final String CHANGE_STATUS="/changeStatus";
        public static final String GET_ASSET_HISTORY="/getAssetHistory";
        public static final String RNHID_RNID_NOT_PROVIDED="RNHID_RNID_NOT_PROVIDED";
        
        public static final String GET_ALL_MODELS ="/getAllModels";
        public static final String GET_PP_SEARCH_ASSETS ="/getPPSearchAssets";
//Added for PP Noticiation Config
    public static final String REQ_URI_CONFIG_GEOFENCE_SERVICE="/cfgGeofenceService";
    public static final String GET_NOTIFY_CONFIGS = "/getNotifyConfigs";
    public static final String ADD_NOTIFY_CONFIG = "/addNotifyConfig";
    public static final String UPDATE_NOTIFY_CONFIG = "/updateNotifyConfig";
    public static final String GET_THRESHOLDS = "/getThresholds";
    public static final String GET_NOTIFY_FLAGS = "/getNotifyFlags";
    public static final String GET_CUSTOMER_MODELS = "/getCustomerModels";
    public static final String CUSTOMER="customer";
    public static final String MODEL_OBJID_NOT_PROVIDED="MODEL_OBJID_NOT_PROVIDED";
    public static final String ORG_OBJID_NOT_PROVIDED="ORG_OBJID_NOT_PROVIDED";
    public static final String FLAG_NAME_NOT_PROVIDED="FLAG_NAME_NOT_PROVIDED";
    public static final String FLAG_VALUE_NOT_PROVIDED="FLAG_VALUE_NOT_PROVIDED";
    public static final String THRESHOLD_NAME_NOT_PROVIDED="THRESHOLD_NAME_NOT_PROVIDED";
    public static final String THRESHOLD_VALUE_NOT_PROVIDED="THRESHOLD_VALUE_NOT_PROVIDED";
    public static final String USERNAME_VALUE_NOT_PROVIDED="USERNAME_VALUE_NOT_PROVIDED";
    public static final String MODEL_NOT_PROVIDED="MODEL_NOT_PROVIDED";
    public static final String CFGOBJID_NOT_PROVIDED="CFGOBJID_NOT_PROVIDED";
    public static final String PARAM_NAME_NOT_PROVIDED="PARAM_NAME_NOT_PROVIDED";
    public static final String PARAM_VALUE_NOT_PROVIDED="PARAM_NAME_NOT_PROVIDED";
    
    //Config Geofence Murali Changes
            
    public static final String GET_GEO_PROXIMITY_DETAILS = "/getProximityDetails";
    public static final String PROXMITY_STATUS = "proxStatus";
    public static final String ADD_NEW_PROXIMITY = "/addNewProximity";
    public static final String FAILURE = "failure";
    public static final String UPDATE_PROXIMITY_DETAILS = "/updateProximity";
    public static final String DELETE_PROXIMITY = "/deleteProximity";
    public static final String GET_GEO_MILEPOST_DETAILS = "/getMilePostDetails";
    public static final String MILEPOST_STATUS = "milePostStatus";
    public static final String UPDATE_MILEPOST_DETAILS = "/updateMilePost";
    public static final String ADD_NEW_MILEPOST = "/addNewMilePost";
    public static final String DELETE_MILEPOST = "/deleteMilePost";
    public static final String GET_STATE_PROVINCE = "/getStateProvince";
    public static final String GET_PROXIMITY_PARENT = "/getProximityParent";
    public static final String CUSTOMER_ID_NOT_PROVIDED = "CUSTOMER ID IS NOT PROVIDED";
    public static final String ADD_REGION_SUB_REGION = "/addRegionSubRegion";
    public static final String REGION = "region";
    public static final String SUB_REGION = "subRegion";
    public static final String REGION_NOT_PROVIDED = "REGION NOT PROVIDED";
    public static final String USER_ID_NOT_PROVIDED = "USER ID NOT PROVIDED";
    public static final String GET_REGION = "/getRegion";
    public static final String GET_SUB_REGION = "/getSubRegion";
    
    //metrics conversion units
    public static final String GET_CONVERSION_UNITS="/getMetricsConversion";
    public static final String COLUMN_NAME="columnName";
    public static final String SCREEN_NAME="screenName";
    
    public static final String GET_STATUS_VALUE="/getStatusValue";
    public static final String FLAG_MP_DATA="flagMPData";
    
    public static final String CONV_PREFERENCE = "conversionPreference";
	public static final String SORT_BY="sortBy";
	public static final String PAGE_NUMBER = "pagenum";
	public static final String PAGE_SIZE = "pagesize";
	public static final String RECORDSTARTINDEX = "recordstartindex";
	public static final String START_ROW = "startRow";
	 public static final String GET_PP_HEADERS="/getPPAssetHistoryHeaders";
	 public static final String PAGEFLAG = "pageflag";
	 public static final String ERC65528 = "FLM Conversion error"; 
	 public static final String ERC65529 = "ATS Code 127 = FLM interface is not configured."; 
	 public static final String ERC65531 = "ATS Code 123 = FLM interface is comm?s up but the engine is off."; 
	 public static final String ERC65530 = "ATS Code 127 = FLM interface is not configured."; 
	 public static final String ERC65532 = "ATS Code 122 = Waiting 2 minutes after comm.is established"; 
	 public static final String ERC65533 = "ATS Code 121 = FLM interface is comm?s up but the engine status has not been in the desired state for x seconds."; 
	 public static final String ERC65534 = "ATS Code 120 = Invalid fuel value received (>6249)."; 
	 public static final String ERC65535 = "ATS Code 63 = Reported by FLM when the fuel sensor has failed.";
    public static final String ROLE_ID = "roleId";
    public static final String GET_UNIT_CONVERSION_DATA="/getUnitConversionData";
    public static final String MEASUREMENT_SYSTEM="measurementSystem";
    public static final String UNIT_VALUE="value";
    public static final String PARAM_ID="paramId";


}

