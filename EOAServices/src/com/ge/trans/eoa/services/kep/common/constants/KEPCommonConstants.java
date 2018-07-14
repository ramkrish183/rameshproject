/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDCommonConstants.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.constants
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 30, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
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
 * @Description : Constants class for common components
 * @History :
 ******************************************************************************/
public final class KEPCommonConstants {

    /** * Error codes properties file name */
    public static final String PERCENTAGE_SYMBOL = "%";
    public static final String PROPERTIES_FILE_NAME = "com/ge/trans/rmd/exception/ErrorCode.properties";
    /** The Constant TRUE. */
    public static final boolean TRUE = true;
    /** The Constant FALSE. */
    public static final boolean FALSE = false;
    /** The Constant YES. */
    public static final String YES = "YES";
    /** The Constant All. */
    public static final String ALL = "All";
    /** The Constant NO. */
    public static final String NO = "NO";
    /** The Constant YES. */
    public static final String LETTER_Y = "Y";
    /** The Constant NO. */
    public static final String LETTER_N = "N";
    /** The Constant EMPTY_STRING. */
    public static final String EMPTY_STRING = "";
    /** The Constant INT_CONST. */
    public static final int INT_CONST = 0;
    /** The Constant INT_CONST. */
    public static final int INT_CONST_ZERO = 0;
    /** The Constant LONG_CONST. */
    public static final long LONG_CONST_ZERO = 0;
    /** The Constant BLANK_SPACE. */
    public static final String BLANK_SPACE = " ";
    /** The Constant ZERO_STRING. */
    public static final String ZERO_STRING = "0";

    /** The Constant ONE_STRING. */
    public static final String SELECT = "Select";

    /** The Constant COMMMA_SEPARATOR. */
    public static final String COMMMA_SEPARATOR = ",";
    /** The Constant COMMMA_SEPARATOR_ZH. */
    public static final String COMMMA_SEPARATOR_ZH = "\uff0c";
    /** The Constant CARRIAGE_RETURN. */
    public static final String CARRIAGE_RETURN = "\n";
    /** The Constant TAB_SPACE. */
    public static final String TAB_SPACE = "\t";
    /** The Constant HYPHEN. */
    public static final String HYPHEN = "-";
    /** The Constant HYPHEN_WITHSPACE. */
    public static final String HYPHEN_WITHSPACE = " - ";
    /** The Constant UNDERSCORE. */
    public static final String UNDERSCORE = "_";
    /** The Constant SINGLE_QTE. */
    public static final String SINGLE_QTE = "'";
    /** The Constant FOWARD_SLASH. */
    public static final String FOWARD_SLASH = "/";
    /** The Constant BACK_SLASH. */
    public static final String BACK_SLASH = "\\";
    /** The Constant FULL_COLON. */
    public static final String FULL_COLON = ":";
    /** The Constant SEMI_COLON. */
    public static final String SEMI_COLON = ";";
    /** The Constant NULL_STRING. */
    public static final String NULL_STRING = "null";
    /** The Constant MINUS_ONE. */
    public static final String MINUS_ONE = "-1";
    // Rule constants
    public static final String RULE_DEFAULT_TIME = "24";
    public static final String RULE_DEFAULT_SEC = "60";
    public static final String RULE_DEFAULT_FREQUENCY = "1";
    public static final String RULE_DEFAULT_DROPDOWN_VALUE = "Pick";
    /** The Constant NUMERIC_PATTERN. */
    public static final String NUMERIC_PATTERN = "^[0-9.]*$";
    /** The Constant NUM_PATRN_WITHOUT_DOT. */
    public static final String NUM_PATRN_WITHOUT_DOT = "^[0-9]*$";
    public static final String NUMERIC_PATTERN_NEGATIVE = "^-{0,1}\\d*\\.{0,1}\\d+$";
    /**
     * Password pattern# 1 - Should not begin and with a number; Must be a
     * minimum of 8 & maximum of 15 characters.
     */
    public static final String PASSWORD_PATTERN1 = "^[^0-9].{6,13}[^0-9]$";
    /**
     * Password pattern# 2 - Must contain atleast 2 embedded numbers;
     */
    public static final String PASSWORD_PATTERN2 = "^.*(?=.*\\d{2,}).*$";
    /** The Constant NA. */
    public static final String NA = "N/A";
    /** The Constant ERROR_MESSAGE. */
    public static final String ERROR_MESSAGE = "Error Message: ";
    /** The Constant ERROR_CODE. */
    public static final String ERROR_CODE = "Error Code: ";
    /** The Constant DOUBLE_QTE. */
    public static final char DOUBLE_QTE = '"';
    /** The Constant CHAR_SPACE. */
    public static final char CHAR_SPACE = ' ';
    /** The Constant COLUMNASCENDING. */
    public static final String COLUMNASCENDING = "ASC";
    /** The Constant COLUMNDESCENDING. */
    public static final String COLUMNDESCENDING = "DESC";
    /** The Constant SUCCESS. */
    public static final String SUCCESS = "SUCCESS";
    /** The Constant FAIL. */
    public static final String FAIL = "FAIL";
    public static final String FAILURE = "failure";
    public static final String SIMPLE_RULE = "S";
    public static final String COMPLEX_RULE = "C";
    public static final String FINAL_RULE = "F";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String EQUAL_SYMBOL = "=";
    public static final String MODE_SYMBOL = "%";
    public static final String ANY = "Any";
    /** Get the MQ Queue Name */
    public static final String ERROR_QUEUE_JNDI_LOOKUP_KEY = "common.queue.errorproccessor.jndi";

    /***************************************************************************
     * @Description : This class is to hold commonly used date constant
     *              variables
     **************************************************************************/
    public static class DateConstants {
        public static final String ddMMMYYYYhhmmss = "dd-MMM-yyyy hh:mm:ss";

        /** The Constant yyMMddhhmmss. */
        public static final String yyMMddhhmmss = "yyMMddhhmmss";
        /** The Constant yyyyMMddHHmmss. */
        public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
        public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

        /** The Constant MMddyyyyHHmmss. */
        public static final String MMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
        /** The Constant yyyyHHddHHmmss. */
        public static final String yyyyHHddHHmmss = "yyyy-MM-dd HH:mm:ss";
        /** The Constant DEFAULT_DATE_FORMAT. */
        public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy 24HH:mm:ss";
        /** The Constant DECODER_DATE_FORMAT. */
        public static final String DECODER_DATE_FORMAT = "MM/dd/yy HH:mm:ss";
        /** The Constant MMddyyyyHHmmssa */
        public static final String MMddyyyyHHmmssa = "MM/dd/yyyy hh:mm:ss a";
        public static final String MMddyyyyHHmissAM = "MM/dd/yyyy hh:mi:ss AM";
        /** The Constant yyMMdd. */
        public static final String yyMMdd = "yyMMdd";
        /** The Constant MMddyyyy. */
        public static final String MMddyyyy = "MM/dd/yyyy";
        /** The Constant EMMMddHHmmsszyyyy. */
        public static final String EMMMddHHmmsszyyyy = "E MMM dd HH:mm:ss z yyyy";
        /** The Constant MMddyyHHmm. */
        public static final String MMddyyHHmm = "MM/dd/yy HH:mm";
        /** The Constant yyyy. */
        public static final String yyyy = "yyyy";
        /** The Constant MMMM_DD_YYYY. */
        public static final String MMMM_DD_YYYY = "MMM dd yyyy";
        /** The Constant MM. */
        public static final String MM = "MM";
        /** The Constant dd. */
        public static final String dd = "dd";
        /** The Constant HH. */
        public static final String HH = "HH";
        /** The Constant mm. */
        public static final String mm = "mm";
        /** The Constant ss. */
        public static final String ss = "ss";
        /** The Constant HHmmss. */
        public static final String HHmmss = "HH:mm:ss";
        /** The Constant GMT. */
        public static final String GMT = "GMT";
    }

    public static final String BOEXCEPTION = "BOEXCEPTION";
    /** * Constant for SERVICEEXCEPTION */
    public static final String SERVICEEXCEPTION = "SERVICEEXCEPTION";
    /** * Constant for DAOEXCEPTION */
    public static final String DAOEXCEPTION = "DAOEXCEPTION";
    /** * Constant for DAOCONNECTIONEXCEPTION */
    public static final String DAOCONNECTIONEXCEPTION = "DAOCONNECTIONEXCEPTION";
    /** * Constant for BEANFACTORYEXCEPTION */
    public static final String BEANFACTORYEXCEPTION = "BEANFACTORYEXCEPTION";
    /**
     * Constant for null object exception
     */
    public static final String NULLOBJECTEXCEPTION = "NULLOBJECTEXCEPTION";
    /**
     * Constant for Case Accepted exception
     */
    public static final String CASEACCEPTEDEXCEPTION = "CASEACCEPTEDEXCEPTION";
    /**
     * Constant for messages informing recommendation created
     */
    public static final String RECOMMCREATED = "recomCreated";
    public static final String REPORT_FILE_PATH = "REPORT_FILE_PATH";
    public static final String ADVISORY_FILE_PATH = "ADVISORY_FILE_PATH";
    public static final String SMTP_SERVER_NAME = "SMTP_SERVER_NAME";
    public static final String PROTOCOL = "PROTOCOL";
    public static final String SUBJECT = "SUBJECT";
    public static final String MESSAGE_BODY = "MESSAGE_BODY";
    public static final String CHARTFOLER_NAME = "charts";
    public static final String ENGLISH_LANGUAGE = "en";
    public static final String CHINESE_LANGUAGE = "zh";
    // Constants for return values on Update/Add
    public static final int UPDATE_SUCCESS = 1;
    public static final int UPDATE_FAILURE = 0;
    public static final int INSERTION_SUCCESS = 1;
    public static final int INSERTION_FAILURE = 0;
    public static final String STRING_TRUE = "TRUE";
    public static final String STRING_FALSE = "FALSE";
    public static final String DB_CONNECTION_FAIL = "DB_CONNECTION_FAIL";
    public static final String FATAL_ERROR = "FATAL_ERROR";
    public static final String MAJOR_ERROR = "MAJOR_ERROR";
    public static final String MINOR_ERROR = "MINOR_ERROR";
    public static final String UTF_8 = "UTF-8";
    public static final String SQUARE_BRAKCET_OPEN = "[";
    public static final String SQUARE_BRAKCET_CLOSE = "]";
    public static final String USER_SESSION_ID = "userSessionID";
    public static final String USER_ID = "userID";
    public static final String CAPSYMBOL = "^";
    public static final String NEWLINE = System.getProperty("line.separator");
    public static final String LOOKUP_ERROR_CODE = "LOOKUP_ERROR_CODE";
    public static final String DOUBLE_QUOTE_W_BSLASH = "\"";
    public static final String DOUBLE_QUOTE_W_EQ_BSLASH = "=\"";
    public static final String ORDER_ASCENDING = "ASC";
    public static final String ORDER_DESCENDING = "DESC";
    public static final String START_FONT_TAG = "<font style=color:#949494;>";
    public static final String END_FONT_TAG = "</font><br>";
    public static final String BREAK_TAG = "<BR>";

    // Used in KnowledgeSeeker Results screen
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
    public static final String CREATION_DATE = "creationDate";
    public static final String PATTERN = "creationDate";
    public static final String CUSTOMER = "customer";
    public static final String KSREQUEST = "ksRequest";
    public static final String KNOWLEDGE_SEEKER_REQUEST = "/knowledgeSeekerRequest";
    public static final String KNOWLEDGE_SEEKER_RESULT = "/knowledgeSeekerResult";
    public static final String KNOWLEDGESEEKER_REQUEST = "knowledgeSeekerRequest";
    public static final String GET_CUSTOMERS = "/getCustomers";
    public static final String GET_MODELS = "/getModels";
    public static final String GET_FLEETS = "/getFleets";
    public static final String GET_CONFIGS = "/getConfigurations";
    public static final String GET_ASSETS = "/getAssets";
    public static final String GET_ENTITES = "/getEntites";
    public static final String GET_FAILURE_TYPE = "/getFailureType";
    public static final String GET_DATA_FILTER_PARAM = "/getDatafilterParam";
    public static final String GET_DATA_FILTER_FUNCTION = "/getDatafilterFunction";
    public static final String GET_CONFIG_FILTER_FUNCTION = "/getConfigfilterFunction";
    public static final String GET_NORMAL_WINDOW_LIMIT = "/getNormwindowlimit";
    public static final String GET_PROG_EXCLUDE = "/getProgexclude";
    public static final String GET_NORMAL_CONDITION_PERC = "/getNormconditionperc";
    public static final String GET_SUPPORT_LEVEL_PERC = "/getSupportlevelperc";
    public static final String GET_UNIT_NUMBER = "/getUnitNumber";
    public static final String FLEET = "fleet";
    public static final String CUST = "cust";
    public static final String MODEL = "model";
    public static final String MODELS = "models";
    public static final String CUSTOMERID = "customerID";
    public static final String USERNAME = "RMREQUESTOR";
    public static final String CONFIG = "config";
    public static final String UNITNUMBER = "unitNumber";

    public static final char LF = '\n'; // TODO move to utils
    public static final char CR = '\r';

    public static final String INPROGRESS = "In Progress";
    public static final int Numeric_30_DAYS = 30;
    public static final String RM_DEFAULT_CONFIG_SEGMENT = "RM_DEFAULT_CONFIG_SEGMENT";
    public static final String RM_DEFAULT_CONFIG_STEP = "RM_DEFAULT_CONFIG_STEP";
    public static final String DEFAULT_DATA_FILTER = "DEFAULT_DATA_FILTER";
    public static final String RULE_ID = "ruleId";
    public static final String RULE_TYPE = "ruleType";
    public static final String RULE_TITLE = "ruleTitle";
    public static final String STRRULE_ID = "strRuleId";
    public static final String HITTORMISS = "hitormiss";
    public static final String CCDB = "CCDB";
    public static final String RXS = "Rxs";
    public static final String FAULTCODES = "FaultCodes";
    public static final String HIT = "HIT";
    public static final String MISS = "MISS";
    public static final String H_CHAR = "H";
    public static final String M_CHAR = "M";
    public static final String KSRESULTDETAILID = "ksResultDetailId";
    public static final String USER_REQUEST_ID = "userRequestId";
    public static final StringBuilder FETCH_PATTERN_DETAILS = new StringBuilder()
            .append(" SELECT PATTERNCATEGORY.SEGMENT_ID,CUSTRNHV.VEHICLE_NO,CUSTRNHV.ORG_ID,patterncategory.HIT_MISS_CD,PATTERNCATEGORY.CREATED_BY,CUSTRNHV.VEHICLE_HDR ")
            .append(" FROM GETS_KEP.KS_PATTERN_CATEGORY PATTERNCATEGORY,  GETS_RMD_CUST_RNH_RN_V CUSTRNHV ")
            .append(" WHERE KS_RESULT_DETAIL_ID=:ksResultDetailId AND ")
            .append(" CUSTRNHV.VEHICLE_OBJID=PATTERNCATEGORY.ASSET_ID ");
    public static final String START_RUN_TSTAMP = "runStartDate";
    

    private KEPCommonConstants() {
    }
}
