
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
package com.ge.trans.rmd.common.constants;


/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Constants class for common components
 * @History :
 * 
 ******************************************************************************/
public final class RMDCommonConstants {

    /** * Error codes properties file name */
    public static final String PROPERTIES_FILE_NAME = "com/ge/trans/rmd/exception/ErrorCode.properties";
    public static final String RMD_HOME = "RMD_HOME";
    public static final String DATA_FOLDER_PATH = "Data_Folder_Path";
    public static final String RMD_DATASOURCE_JNDI_NAME = "RMD_DATASOURCE_JNDI_NAME";
    public static final String PP_DATASOURCE_JNDI_NAME = "PP_DATASOURCE_JNDI_NAME";
    public static final String KEP_DATASOURCE_JNDI_NAME = "KEP_DATASOURCE_JNDI_NAME";
    public static final String EOADW_DATASOURCE_JNDI_NAME = "EOADW_DATASOURCE_JNDI_NAME";
    public static final String JAVA_NAMING_FACTORY_INITIAL = "java.naming.factory.initial";
    public static final String JAVA_NAMING_PROVIDER_URL = "java.naming.provider.url";
    public static final String QUEUE_JAVA_NAMING_FACTORY_INITIAL = "mq.java.naming.factory.initial";
    public static final String QUEUE_JAVA_NAMING_PROVIDER_URL = "mq.java.naming.provider.url";
    public static final String QUEUE_CONNECTION_FACTORY_PROVIDER = "mq.connection.factory.provider";
    public static final String QUEUE_CONNECTION_UNAME = "mq.connection.uname";
    public static final String QUEUE_CONNECTION_PWD = "mq.connection.pwd";
    public static final String COMMON_ERROR_QUEUENAME = "common.queue.errorproccessor";
    public static final String TRANCATION_INDICATOR = "800"; 
    public static final String DATA_FOLDER = "data";
    public static final String RMD_BASE_PROPERTY_FILE_NAME = "app/conf/qtrrmd.properties";
    public static final String DATA_LNAGUAGE = "rmd.data.language";
    public static final String CUSTOMERID = "customerID";
    /** The Constant AUTHORISED. */
    public static final boolean AUTHORISED = true;
    /** Constant for boolean false. */
    public static final boolean UNAUTHORISED = false;
    /** The Constant TRUE. */
    public static final boolean TRUE = true;
    /** The Constant FALSE. */
    public static final boolean FALSE = false;
    /** The Constant YES. */
    public static final String YES = "YES";
    /** The Constant Test. */
    public static final String TEST = "TEST";
    /** The Constant All. */
    public static final String ALL = "All";
    /** The Constant NO. */
    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String MPH = " mph";
    public static final String HOURS = " hours";
    public static final String DOT = ".";
    public static final String NBSP = "&nbsp;";
    public static final String DEG = " degrees";
    public static final String MILES = " miles ";
    public static final String OF = " of";
    public static final String ISOLATE = "Isolate";
    public static final String RUN = "Run";
    public static final String ARZN = "ARZN";
    public static final String SHORTHOOD_LEAD = "ShortHood Lead";
    public static final String SHORTHOOD_LEAD_ARZN = "Forward";
    public static final String LONGHOOD_LEAD = "LongHood Lead";
    public static final String LONGHOOD_LEAD_ARZN = "Reverse";
    public static final String NO = "NO";
    public static final String Location = "Location";
    /** The Constant YES. */
    public static final String LETTER_Y = "Y";
    public static final char CHAR_Y = 'Y';
    public static final char CHAR_N = 'N';
    public static final char CHAR_ZERO = '0';
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
    public static final String ONE_STRING = "1";
    /** The Constant ONE_STRING. */
    public static final String EITHER = "Either";
    /** The Constant ONE_STRING. */
    public static final String SELECT = "Select";
    public static final String ACTIVATE = "ACTIVE";
    /** The Constant ONE_STRING. */
    public static final String DEACTIVATE = "DEACTIVATE";
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
    public static final String NUMERIC_PATTERN_NEGATIVE_SIX_DECIMALS = "^-?[0-9]{1,4}+([.][0-9]{1,6})?$";
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
    public static final String NA_ARZN = "Center";
    /** The Constant ERROR_MESSAGE. */
    public static final String GALLONS = " gallons";
    public static final String ERROR_MESSAGE = "Error Message: ";
    /** The Constant ERROR_CODE. */
    public static final String ERROR_CODE = "Error Code: ";
    /** The Constant DOUBLE_QTE. */
    public static final char DOUBLE_QTE = '"';
    /** The Constant CHAR_SPACE. */
    public static final char CHAR_SPACE = ' ';
    /** The Constant MONTHS. */
    private static final String[] MONTHS = { " ", "Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    
	/** The Constant COLUMNASCENDING. */
    public static final String COLUMNASCENDING = "ASC";
    /** The Constant COLUMNDESCENDING. */
    public static final String COLUMNDESCENDING = "DESC";
    /** The Constant SUCCESS. */
    public static final String SUCCESS = "SUCCESS";
    public static final String LOWERCASE_SUCCESS = "success";
    /** The Constant FAIL. */
    public static final String FAIL = "FAIL";
    public static final String FAILURE = "failure";
    public static final String COMPLEX_RULE = "C";
    public static final String SIMPLE_RULE = "S";
    public static final String FINAL_RULE = "F";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String EQUAL_SYMBOL = "=";
    public static final String MODE_SYMBOL = "%";
    public static final String ANY = "Any";
    public static final String NO_OF_DAYS = "noDays";
	public static final String LONG_LOCO_L = "L";
	public static final String SHORT_LOCO_S = "S"; 
	public static final String VIEW_SHORT_LOCO="ShortHood Lead";
	public static final String VIEW_SHORT_LOCO_ARZN="Forward";
	public static final String VIEW_LONG_LOCO="LongHood Lead";
	public static final String VIEW_LONG_ARZN="Reverse";
	public static final String VIEW_NA="N/A";
	public static final String VIEW_NA_ARZN="Center";
    /** Get the MQ Queue Name */
    public static final String ERROR_QUEUE_JNDI_LOOKUP_KEY = "common.queue.errorproccessor.jndi";

    /*
     * Added constants for Dynamic work queue - (To display case list)
     */
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String CONDITIONAL = "Conditional";
    public static final String ESTP = "ESTP";
    public static final String MEDIUM = "Medium";
    
    //Added by Sri for Rule Hits Tab
	public static final String HITS_RULE_TYPE = "HITS_RULE_TYPE";
	public static final String NO_DATA = "NO_DATA";
	public static final String DATE_START = "dateStart";
	public static final String DATE_END = "dateEnd";
	public static final String CONTROLLER_ID="controllerId";
	public static final String VEHICLE="vehicle";
	public static final String MIN_LOOKBACK="minLookBack";
	public static final String MAX_LOOKBACK="maxLookBack";
	public static final String FIRING_ID="firingId";
	public static final String RUN_ID="runId";
	public static final String STRING_YES="Yes";
	public static final String STRING_NO = "No";
	public static final String PARM_VALUES = "parmValues";
	public static final String ALERT_SENT = "Sent";
	public static final String ALERT_SUPPRESSED = "Suppressed";
		

    /***************************************************************************
     * @Description : This class is to hold commonly used date constant
     *              variables
     * 
     **************************************************************************/
    public static class DateConstants {
        /** The Constant yyMMddhhmmss. */
        public static final String MMddyyhhmmss = "MM-dd-yy HH:mm:ss";

        /** The Constant yyMMddhhmmss. */
        public static final String yyMMddhhmmss = "yyMMddhhmmss";
        /** The Constant yyyyMMddHHmmss. */
        public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
        public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
        public static final String NOSPACE_MMddyyyyHHmmss = "MMddyyyyHHmmss";
        /** The Constant MMddyyyyHHmmss. */
        public static final String MMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
        /** The Constant yyyyHHddHHmmss. */
        public static final String mmddyyyyHHmmss = "mm/dd/yyyy HH:mm:ss";
        public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
        /** The Constant DEFAULT_DATE_FORMAT. */
        public static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy 24HH:mm:ss";
        /** The Constant DECODER_DATE_FORMAT. */
        public static final String DECODER_DATE_FORMAT = "MM/dd/yy HH:mm:ss";
        /** The Constant DECODER_DATE_FORMAT. */
        public static final String LAST_DOWNLOAD_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
        public static final String LAST_DOWNLOAD_DATE = "dd-MMM-yy";
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
        /** The Constant EST. */
        public static final String EST = "EST";
        /** The Constant EST.timzone */
        public static final String EST_US = "US/Eastern";
        /** The Constant GMT. */
        public static final String GMT = "GMT";

        public static final String d = "d";
        public static final String hrs = "hrs";
        public static final String min = "min";

        /** The Constant yyyy-MM-dd. */
        public static final String yyyyMMdd = "yyyy-MM-dd";

        /** The Constant MMddyyyyHHmm. */
        public static final String MMddyyyyHHmm = "MM/dd/yyyy HH:mm";
        public static final String MMddyyyyHHmmssSSS = "MM/dd/yyyy HH:mm:ss:SSS";
        public static final String MMddyyyyHHmmssSSS_C = "MM/dd/yyyy HH:mm:ss,SSS";
        public static final String ALERT_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.S";
        public static final String ALERT_FORENSICS_FORMAT = "dd-MMM-yy HH:mm:ss"; 
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
    public static final String SESSION_ID = "sessionId";
    public static final String USER_ID = "userID";
    public static final String LINK_RECOM_DELV = "LINK_RECOM_DELV";
    public static final String CAPSYMBOL = "^";
    public static final String NEWLINE = System.getProperty("line.separator");
    public static final String LOOKUP_ERROR_CODE = "LOOKUP_ERROR_CODE";
    public static final String DOUBLE_QUOTE_W_BSLASH = "\"";
    public static final String DOUBLE_QUOTE_W_EQ_BSLASH = "=\"";
    public static final String ORDER_ASCENDING = "ASC";
    public static final String ORDER_DESCENDING = "DESC";

    
    public static final String PP_MAP_ALARM = "ALARM";
    public static final String ATS_MESSAGE_REASON_CODE = "7";
    public static final String ATS_MESSAGE_REASON_YES="YES";
    public static final String ATS_MESSAGE_REASON_NO="NO";
    public static final String PP_MAP_ALERT = "ALERT";
    public static final String PP_MAP_NORMAL = "NORMAL";
    
    public static final String HIGH = "High";
    public static final String LOW = "Low";

    // Used in knowledgeSeeker--KM Team
    public static final char LF = '\n'; 
    public static final char CR = '\r';

    // Used in RuleDAOImpl for Configuration Symbols
    public static final String GREATER_THAN_SYMBOL = ">";
    public static final String GREATER_THAN_EQUAL_SYMBOL = ">=";
    public static final String LESS_THAN_SYMBOL = "<";
    public static final String LESS_THAN_EQUAL_SYMBOL = "<=";
    public static final String GREATER_THAN_AND_LESS_THAN_SYMBOL = "> AND <";
    public static final String GREATER_THAN_EQUAL_AND_LESS_THAN_SYMBOL = ">= AND <";
    public static final String GREATER_THAN_AND_LESS_THAN_EQUAL_SYMBOL = "> AND <=";
    public static final String GREATER_THAN_EQUAL_AND_LESS_THAN_EQUAL_SYMBOL = ">= AND <=";
    public static final String LESS_THAN_OR_GREATER_THAN_SYMBOL = "< OR >";
    public static final String LESS_THAN_EQUAL_OR_GREATER_THAN_SYMBOL = "<= OR >";
    public static final String LESS_THAN_OR_GREATER_THAN_EQUAL_SYMBOL = "< OR >=";
    public static final String LESS_THAN_EQUAL_OR_GREATER_THAN_EQUAL_SYMBOL = "<= OR >=";
    public static final String NOT_EQUAL_SYMBOL = "!="; 

    // used in CaseDAOImpl for dispatchCase method
    public static final String S_TITLE = "800. MDSC WORK QUEUE";

    // Added this variable used in RX search page
    public static final int Numeric_30_DAYS = 30;
    public static final int Numeric_7_DAYS = 7;

    public static final String KPI_RX_DELIVERED = "rxDelivered";
    public static final String KPI_RX_CLOSED_URGENCY = "rxClosedByUrgency";
    public static final String KPI_RX_CLOSED_TYPE = "rxClosedByType";

    public static final String SOLUTION_TITLE = "solutionTitle";
    public static final String SOLUTION_NOTES = "solutionNotes";
    public static final String SOLUTION_STATUS = "solutionStatus";
    public static final String SOLUTION_TYPE = "solutionType";
    public static final String SOLUTION_ID = "solutionId";
    
    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Deactive";
    public static final String COMPLETED = "Complete";
    public static final String DRAFT = "Draft";
    
    public static final String ACTIVE_STATUS = "1";
    public static final String INACTIVE_STATUS = "0";
    public static final String COMPLETE_STATUS = "1";
    public static final String DRAFT_STATUS = "0";
    public static final String PIPELINE_CHARACTER = "|";

    // ADDED FOR VALIDATION PURPOSE
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
    public static final int ISBOOLEAN = 12;
    public static final int CHECK_ENCODE = 13;
    public static final int SPECIAL_CHARACTER=14;

    public static final String INTNUMBER = "^[0-9]+$";
    public static final String INTNUMBER_COMMA = "^[0-9,]+$";
    public static final String ALPHANUMERIC = "^[A-Za-z0-9\\s]+$";
    public static final String ALPHANUMERIC_COMMA = "^[A-Za-z0-9,\\s]+$";
    public static final String ALPHNUMHYPHEN = "^(?!.*--)[A-Za-z0-9-\\s]+$";
    public static final String SEQVALPATTERN = "^(?!.*--)[A-Za-z0-9-+\\s]+$";
    public static final String ALPHNUMUNDERSCR = "^(?!.*--)[A-Za-z0-9-_\\s]+$";
    public static final String ALPHNUMBRACKETS =  "^(?!.*--)[A-Za-z0-9-'\\.()\\s]+$";
    public static final String CASEPATTERN = "^[A-Z]+$";
    public static final String EMAILPATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]+)*(\\.[A-Za-z]{2,})$";
    public static final String PDFTYPE = "pdf";
    public static final String TXTTYPE = "txt";
    public static final String FILEDELIMETER = "\\.";
    public static final String DBLQOUTE = "&quot;";
    public static final String LESSTHAN = "&lt;";
    public static final String GRTTHAN = "&gt;";
    public static final String AMPERSAND = "&amp;";
    public static final String FILENAME = "^(?!.*--)[A-Za-z0-9_-]+$";
    public static final String ALHA = "^[a-zA-Z\\s]+$";
    public static final String DBL_ZERO_STRING = "00";
    public static final String ADDCHARENTITY = "&#";
    public static final String RULE_TITLE_PATTERN = "^[0-9a-zA-Z_#()+--/.\\s]+$";
    public static final String ALPHAUNDERSCOREPAT = "^[A-Za-z_\\s]+$";
    public static final String NUMBERSHYPHEN = "^(?!.*--)[0-9-]+$";
    public static final String ALPH_NUM_UND = "^(?!.*--)[A-Za-z0-9_\\s]+$";

    public static final String ALPHA_BRACKET_PT = "^(?!.*--)[A-Za-z0-9()\\s_%-/:;]+$";
    public static final String ALPHA_AMPERSAND_PT = "^(?!.*--)[A-Za-z\\s&-]+$";
    public static final String ALPHA_FORWARD_SLASH_PT = "^(?!.*--)[A-Za-z0-9()/,''\\s-]+$";
    public static final String ALPHA_DECIMAL="^[0-9.-]+$";
    public static final String COLUMN_PATTERN="^[A-Za-z0-9+-,/#_%\\s]+$";
    public static final String ASSET_IMPACT="^(?!.*--)[A-Za-z0-9-;()&'%./:\\s]+$";
    public static final String TASKPATTERN="^(?!.*--)[A-Za-z0-9+-;()&'@~<#!%./_:\\s]+$";
    public static final String HISTORY_PATTERN="^[A-Za-z0-9,\\s]+$";
    public static final String DOC_PATTERN="^[A-Za-z0-9:/\\s]+$";
    public static final String FEEDBACK_PATTERN="^[A-Za-z/,\\s]+$";
    // CONFIG
    public static final String EQUALS = "=";
    public static final String GREATER = ">";
    public static final String GEQUALS = ">=";
    public static final String LESS="<";
    public static final String LEQULAS="<=";
    public static final String AND1= "> AND <";
    public static final String AND2= ">= AND <";
    public static final String AND3= "> AND <=";
    public static final String AND4= ">= AND <=";
    public static final String OR1 = "< OR >";
    public static final String OR2 = "<= OR >";
    public static final String OR3 = "< OR >=";
    public static final String OR4 = "<= OR >=";        
    public static final String NQ = "!=";
    public static final String MULTI = "MULTI_OR";
    public static final String AND5 = "AND";
    public static final String NOT = "NOT";
    public static final String OR5 = "OR"; 

    public static final String NOT_FORWARD="NOT_FRWD";
    public static final String NOT_BACKWARD="NOT_BKWD";
    public static final String CONFIG_PATTERN = "^(?!.*--)[A-Za-z0-9-_=><!\\s]+\\d+$";
    public static final String ALPHA_MINUS="^[0-9-.]+$";
            
    public static final String LOCALHOST = "localhost";
    public static final String ASSET_NUMBER = "assetNumber";
    public static final String CUSTOMER_ID = "customerId";
    public static final String ASSET_GRP_NAME = "assetGrpName";
    public static final String GROUP_NAME = "groupName";

    public static final String MODEL = "model";
    public static final String URGENCY = "urgency";
    public static final String REPAIR = "estRepair";
    public static final String LOCOMOTIVE = "locoImpact";
    public static final String SUB_SYSTEM = "subsystem";
    public static final String LAST_UPDATED = "lastUpdatedBy";
    public static final String EXPORT_CONTROL = "exportControl";


    public static final String CASEID="caseId";
    public static final String SEQUENCE_ID = "sequenceid";
    public static final String ASSET_NO="assetNo";
    public static final String CUST_ID="custId";
    public static final String TOOL_RUN_IND="ToolRunInd";
    public static final String FROM_DATE="fromDate";
    public static final String  TO_DATE="toDate";
    public static final String LANGUAGE="language";
    public static final String LANGUAGECODE="languageCode";
    public static final String ASSET_NUM="assetNum";
    public static final String  P_VEH_OBJID="P_VEH_OBJID";
    public static final String P_FROM_DATE="P_FROM_DATE";
    public static final String P_TO_DATE="P_TO_DATE";
    public static final String P_NO_OF_DAYS="P_NO_OF_DAYS";
    
    /**
     * 
     */
    public static final String INVALID_ROAD_NUMBER_ERROR = "Invalid Road Number";

    // constants added for Create case EOA
    public static final String SEQUENCE_NUM = "sequence_number";
    public static final String CONDITION = "condition";
    public static final String SEQUENCE = "sequence";
    public static final String TITLE = "title";
    public static final String USERID = "userId";
    public static final String ACT_CODE = "act_code";
    public static final String EST_REPAIR_TIME = "strEstTime";
    public static final String RECOM_ID = "strRecomId";
    public static final String EST_URGENCY = "strUrgency";
    public static final String TITLE_S = "strCondValue";
    public static final String MODEL_S = "strModelArray";
    public static final String STATUS_S = "strRxStatus";
    public static final String TYPE_S = "strType";
    public static final String LOCO_IMP_S = "strLocoImpArray";
    public static final String SUBSYSTEM_S = "strSubsystemArray";
    public static final String LAST_UPDATED_S = "strLastUpdated";
    public static final String NOTE_S = "strNote";
    public static final String EXP_CTRL_S = "strExpCtrl";
    public static final String FROM_DATE_S = "strFromDate";
    public static final String TO_DATE_S = "strToDate";
    public static final String CONTAINS_S = "Contains";
    public static final String STARTS_WITH_S = "Starts with";
    public static final String ENDS_WITH_S = "Ends with";
    public static final String ESTIMATED_TIME_TO_REPAIR = "ESTIMATED_TIME_TO_REPAIR";
    public static final String URGENCY_OF_REPAIR = "URGENCY_OF_REPAIR";
    public static final String LIST_NAME = "listName";
    public static final String EST_TIME_REPAIR = "Estimated Time To Repair";
    public static final String URG_REPAIR = "Urgency of Repair";
    public static final String RX_DELIVERY_STATUS = "RX_DELIVERY_STATUS";
    public static final String ALL_URG_ESTTIME = "All";
    public static final String ALL_URG_EST = "A";
    
    public static final String APPROVED = "Approved";
    public static final String CASE_OWNER2USER = "case_owner2user";
    public static final String CASE_ORIGINATOR2USER = "case_originator2user";
    public static final String ASSET_GROUP = "assetGroup";
    public static final String CASE_REPORTER2SITE = "case_reporter2site";
    public static final String CUSTOMER_OBJID = "customerObjectIdNo";
    public static final String SOURCE_INPUT="OMD";
    public static final String HC_ACTION_TYPE = "REQ";
    public static final String SDF_FORMAT_WITH_T = "yyyy-MM-dd'T'HH:mm:ss.S";


    /* Added for EOA_ Last download status story */
    public static final String ASSET_LAST_DOWNLOAD = "Last Download";
    public static final String ASSET_LAST_DOWNLOAD_STATE = "State";
    public static final String ASSET_CAB_CAX = "CAB/CAX";
    public static final String ASSET_EGU = "EGU";
    public static final String ASSET_SDIS_INCIDENT = "SDIS Incident";
    public static final String ASSET_SDIS_SNAPSHOT = "SDIS Snapshot";
/* Added for Secondary navigation Story */
    public static final String HELP="HELP";
    public static final String CWC="CWC";
    public static final String ESERVICE="ESERVICE";
    public static final String DUMMY_IMAGE="dummy.png";
    public static final String DUMMY2_IMAGE="dummy2.png";
    public static final String DUMMY3_IMAGE="dummy3.png";
    public static final String NEW_WINDOW_FLAG="new";
    public static final String SUBTAB_FLAG="blank";

    public static final String RECOM_STATUS_LISTNAME="Recommendation Status";
    public static final String RECOMMENDATION_STATUS_LISTNAME="RECOMMENDATION_STATUS";
    public static final String DISABLED="Disabled";
    public static final String MULTI_OR="MULTI_OR";
    public static final String  CUSTOMER="customer";
    public static final String  RN_SEARCH_STRING="rnSearchString";
    public static final String GET_FLEETS="/getFleets";
    public static final String EOA_APP="EOA";
    
    //Added for link customer to profile story
    public static final String USER_SEQ_ID = "userSeqId";
    public static final String OLD_USR_ID="oldUserId";
    public static final String CUS_ID = "CUSTID";
    //added for eoa user preference timezone story - Start
    public static final String PREFSEQID = "prefSeqId";
    //added for eoa user preference timezone story - end
    
    public static final String  GETASST_ASSETSEQID="getAsstAssetSeqId";
    public static final String RXCASEID="rxCaseID";
    public static final String  STITLE ="s_Title";
    public static final String SORT_ORDER="sortOrder";
    public static final String FLEETNUMBER="fleetNumber";
    public static final String MODEL_NAME="modelName";
    public static final String LOOKUPID="lookupId";
    public static final String LOOKUPSEQID="lookupSeqId";
    public static final String LAST_UPDATED_DATE="lastUpdatedDate";
    public static final String DESCRIPTIONNEW="descriptionNew";
    public static final String LISTNAMENNEW="listnameNew";
    public static final String LOOKVALUE="lookValue";
    public static final String LOOKSTATE="lookState";
    public static final String  ROLE_ID="roleId";
    public static final String ROLE_NAME="roleName";
    public static final String DELVDATE="delvDate";
    public static final String LETTER_G="G";
    public static final String LETTER_R="R";
    public static final String YELLOW_COLOR="yellow";
    public static final String GREEN_COLOR="green";
    public static final String RED_COLOR="red";
    public static final String FIND ="FIND";
    public static final String DELIVERED="DELIVERED";
    public static final String CLOSED="CLOSED";
    public static final String LASTNAME="lastName";
    public static final String NAME="name";
    public static final String LOCATIONID="locationId";
    public static final String IN_ACTIVE ="Inactive";
    public static final String STATUS ="status";
    public static final String FULLNAME="fullName";
    public static final String LOCATION ="LOCATION";
    public static final String FAULT_DESC ="FAULT_DESC";
    public static final String EPS="EPS";
    public static final String EX_SNAPSHOT_FLAG="EX_SNAPSHOT_FLAG";
    public static final String MSA ="MSA";
    public static final String ID="ID";
    public static final String CRITICAL="Critical";
    public static final String INCIDENT ="Incident";
    public static final String RN="RN";
    public static final String TRACKINGID="trackingId";
    public static final String STRRULEID="strRuleId";
    public static final String HITORMISS="hitormiss";
    public static final String NUMDAYS="numDays";
    public static final String PINPOINT="PinPoint";
    public static final String PROXIMITYlABEL="proximityLabel";
    public static final String PROXIMITYDESC="proximityDesc";
    public static final String ACTIVEFLAG="activeFlag";
    public static final String ADMIN="admin";
    public static final String DS="DS";
    public static final String FAMILY ="family";
    public static final String STAR_SYMBOL="*";
    public static final String STRLANG="strLang";
    public static final String STRFROMASST="strFromAsset";
    public static final String STRTOASSET="strToAsset";
    public static final String STRLANGUAGE="strLanguage";
    public static final String RX_CASE_ID="rx_Case_ID";
    public static final String RECOMEXECUTIONSEQID="recomExecutionSeqId";
    public static final String OWNER="owner";
    public static final String STRCUSTOMERNAME="strCustomerName";
    public static final String GET_ASST_MODEL ="getAsstModel";
    public static final String  GET_CM_CUSTOMER="getCmCustomer";
    public static final String FLEET="fleet";
    public static final String MODEL_MODEL_NAME="model.modelName";
    public static final String GET_ASSET_FLEET="getAsstFleet";
    public static final String CUSTOMER_CUSTOMERID="customer.customerId";
    public static final String FLEET_FLEET_NUMBER="fleet.fleetNumber";
    public static final String GET_CMDS_TEMPLATE="getCmDsTemplate";
    public static final String TEMPLATE="template";
    public static final String TEMPLATE_TEMPLATE_NAME="template.templateName";
    public static final String GET_CMDS_TEMPLATE_SEQID="getCmDsTemplate.getCmDsTemplateSeqId";
    public static final String GETCMDS_MASTER_PARAM="getCmDsMasterParam";
    public static final String MASTER="master";
    public static final String GETCMDSMASTER_PARAM="getCmDsMasterParam.getKmDpdColname";
    public static final String COLNAME="colname";
    public static final String GET_CMDS_MASTER_PARAM="getCmDsMasterParam.getKmDpdColname";
    public static final String TEMPMAP= "tempMap";
    public static final String TEMPMAPGETCMDS="tempMap.getCmDsTemplateSeqId";
    public static final String MASTER_SORT_ORDER="master.sortOrder";
    public static final String GET_KMDP_COLNAME="getKmDpdColname";
    public static final String GETKMDPCOLNAME_SEQID="getKmDpdColnameSeqId";
    public static final String COLNAME_GETKMDPCOLNAME_SEQID ="colName.getKmDpdColnameSeqId";
    public static final String GETCMDSMASTER_SEQID="getCmDsMasterParamSeqId";
    public static final String TEMPLATE_LINK="templateLink";
    public static final String  GETSYS_LOOKUP_SEQID="getSysLookupSeqId";
    public static final String GETCMDSTEMPLATE_SEQID="getCmDsTemplateSeqId";
    public static final String SERVICE_TYPE="SERVICE_TYPE";
    public static final String VIEW_TYPE="VIEW_TYPE";
    public static final String  DPCOLNAME_COLNAME="dpdcolname.columnName";
    public static final String DPCOLNAME="dpdcolname";
    public static final String GETCMDS_TEMPARE_PARAMSEQID="getCmDsTempParamSeqId";
    public static final String  GET_ASSTGROUP="getAsstGroup";
    public static final String GET_ASST_GROUP_NAME="getAsstGroup.name";
    public static final String WORK="Work";
    public static final String  GETSYS_LOOKUP_MULTILANG="getSysLookupMultilangs";
    public static final String MULTILANG="multiLang";
    public static final String MULTILANG_LANG="multiLang.language";
    public static final String GROUP="group";
    public static final String GROUPNAME="group.name";
    public static final String BEANPROPERTIES_PROPERTIES="BeanProperties.Properties";
    public static final String SYMBOL_ATRATE="@";
    public static final String INTERFACE_UTIL="interface java.util.List";
    public static final String FDBTXT="FDBTXT";
    public static final String RECOMMENDATION_CLOSED="RECOMMENDATION CLOSED";
    public static final String DEC_CASEID_FORMATTOR="-000000";
    public static final String  GETSYSLOOKUP_BYLINK_ERROR="getSysLookupByLinkErrorCategory";
    public static final String CATEGORY="category";
    public static final String  GETASSTASSET="getAsstAsset";
    public static final String  ASSET="asset";
    public static final String GETOMI_MESSAGE_OUT="getOmiMessageOut";
    public static final String OUT_MESSAGE="outmessage";
    public static final String GETDATA_ERROR_SEQID="getDataErrorLogSeqId";
    public static final String ASSET_ASSETNUMBER="asset.assetNumber";
    public static final String CATEGORY_GETSYSLOOKUP="category.getSysLookupSeqId";
    public static final String OUTMESSAGE_GETOMI_SEQID="outmessage.getOmiMessageOutSeqId";
    public static final String ERROR_TIMESTAMP="errorTimestamp";
    public static final String LIST_DESC="listDescription";
    public static final String GETSYSPURGE="getSysPurge";
    public static final String PURGE= "purge";
    public static final String CREATION_DATE="creationDate";
    public static final String PURGE_SEQUENCE="purge.sequence";
    public static final String  RESOURCE_TYPE="resourceType";
    public static final String SECNAV="SECNAV";
    public static final String CONTACT_INFOID="contactInfoSeqId";
    public static final String FIRSTNAME ="firstName";
    public static final String MIDDLENAME ="middleName";
    public static final String CUSTOMER_HOMEHVO="customerHomeHVO";
    public static final String ASSET_GROUP_HOMEHVO="assetGroupHomeHVO";
    public static final String ROAD_NUMBER="roadNumber";
    public static final String  LONG_ASSET_NUMBER="longAssetNumber";
    public static final String GOOD="Good";
    public static final String COMM="Comm";
    public static final String STRASSET_NUMBER="strAssetNumber";
    public static final String  MSGTYPE_DESC="msgTypeDescription";
    public static final String VEH_NUM="vehNum";
    public static final String GRPNAM="grpNam";
    public static final String CUSTID="custID";
    public static final String GET_SYS_CONTROLLER="getSysController";
    public static final String CTRLCNFG="ctrlCfg";
    public static final String ASSETVO="assetVO";
    public static final String ASSETVO_CUSTOMER="assetVO.getCmCustomer";
    public static final String ASSETVO_GETASSTGROUP="assetVO.getAsstGroup";
    public static final String ASST="asst";
    public static final String  GETASSTASSET_GETCM_CUSTOMER="getAsstAsset.getCmCustomer";
    public static final String GETASSTASSET_GETASSTGROUP="getAsstAsset.getAsstGroup";
    public static final String ASST_LANGUAGE="asst.language";
    public static final String ASST_ASSETNUMBER="asst.assetNumber";
    public static final String ASSETVO_LANGUAGE="assetVO.language";
    public static final String ASSETVO_ASSETNUMBER="assetVO.assetNumber";
    public static final String SYS_DASHSCREEN="sysDashGreen";
    public static final String  NO_COMM="No Comm";
    public static final String GETCMCASE="getCmCase";
    public static final String SYS_DASH_RED="sysDashRed";
    public static final String GETKMRECOM_SEQID="getKmRecomSeqId";
    public static final String GETKMRECOM_TASK_SEQID="getKmRecomTaskSeqId";
    public static final String GETUSRSUSERS_BYLINK_USERS="getUsrUsersByLinkUsers";
    public static final String  GETASST_ASSET_BYLINK_ASSET="getAsstAssetByLinkAsset";
    public static final String STICKY="sticky";
    public static final String GETSYS_LOOKUP_BY_STATUSLOOKUP="getSysLookupByLinkStatusLookup";
    public static final String GETCM_QUEUE="getCmQueue";
    public static final String GETSYS_LOOKUP_BY_LINKREASONLOOKUP="getSysLookupByLinkReasonLookup";
    public static final String ASSET_GETASSMODEL="asset.getAsstModel";
    public static final String  ASSET_GETASSTGROUP="asset.getAsstGroup";
    public static final String  ASSET_GETCM_CUSTOMER="asset.getCmCustomer";
    public static final String ASSET_GET_ASSTLOCATION="asset.getAsstLocation";
    public static final String SYMBOL_BACKSLASH= " \"";
    public static final String GETCMCASE_GETUSR_BY_LINK_USERS="getCmCase.getUsrUsersByLinkUsers";
    public static final String GETCMCASE_GETUSR_BY_LINK_STATUSLOOKUP="getCmCase.getSysLookupByLinkStatusLookup";
    public static final String GETCMCASE_GETCMQUEUE="getCmCase.getCmQueue";
    public static final String GETCMCASE_GETUSR_BY_LINK_REASONLOOKUP="getCmCase.getSysLookupByLinkReasonLookup";
    public static final String GETCMCASE_CASEID="getCmCase.caseId";
    public static final String GETOMISTATUSDTLS="getOmiMtsStatusDtls";
    public static final String STATUSDTL="statusDtl";
    public static final String STATUSDTL_COMPONENT_NAME="statusDtl.componentName";
    public static final String MTSRECEIVER="MTSReceiver";
    public static final String STATUS_TYPE="statusType";
    public static final String MTSSENDER="MTSSender";
    public static final String MTS_RUNNING_GOOD="mts.running.good";
    public static final String LAST_MESSAGE_INDATE="lastMessageInDate";
    public static final String  RUN_STATUS="runStatus";
    public static final String RUNCPT="runCpt";
    public static final String GETSYSLOOKUP ="getSysLookup";
    public static final String GETSYSLOOKUP_LISTNAME="getSysLookup.listName";
    public static final String DISPLAY_NAME="displayName";
    public static final String GET_USR_USER_BYLINK_CREATORUSERS="getUsrUsersByLinkCreatorUsers";
    public static final String URS_USERID="usr.userId";
    public static final String LOOKUPVO="lookupVO";
    public static final String GETCM_RECOM_DELVS="getCmRecomDelvs";
    public static final String LOOKUPVO_LOOKVALUE="lookupVO.lookValue";
    public static final String URS= "usr";
    public static final String CASESEQID="caseSeqId";
    public static final String  RULE_DEFID="ruleDefId";
    public static final String MIN_OBJID="minObjId";
    public static final String MAX_OBJID="maxObjId";
    public static final String CUR_TIME="curTime";
    public static final String CUR_TIME_MINUS_DAYS="curTimeMinusDays";
    public static final String MIN_OBJ_ID="minObjid";
    public static final String MAX_OBJ_ID="maxObjid";
    public static final String PX_COMMA="px,";
    public static final String CASE="case";
    public static final String  CASE_CASEID="case.caseId";
    public static final String GET_KM_RECOM="getKmRecom";
    public static final String TOOLID="toolId";
    public static final String GET_TOLL_OUT_LIST="getToolOutList";
    public static final String GET_KMDP_RULES_DEF_SEQID="getKmDpdRuledefSeqId";
    public static final String GET_KMDP_FIN_RULE_SEQID="getKmDpdFinrulSeqId";
    public static final String GET_KM_MAN_RUN="getKmManRun";
    public static final String MAN_RUN="manrun";
    public static final String MANRUN_GET_KM_RUNSEQID="manrun.getKmManRunSeqId";
    public static final String USER_HOMEVO="usersHomeVO";
    public static final String USERHOME="userHome";
    public static final String MULTILANG_DISPLAY_NAME="multiLang.displayName";
    public static final String DWQ="DWQ";
    public static final String  USERHOME_USERID="userHome.userId";
    public static final String HOME="HOME";
    public static final String RECOMDELV_GETRXSTATUS="recomDelv.getCmRxStatus";
    public static final String RECOMDELV_GETCMRECOM_EXE="recomDelv.getCmRecomExecution";
    public static final String SAVE="Save";
    public static final String MISS="Miss";
    public static final String MISS_CODE_VALUE="4C";
    public static final String RXSTATUS_GETCMRX_STATUS_SEQID="rxStatus.getCmRxStatusSeqId";
    public static final String RXSTATUS_MISSCODE="rxStatus.missCode";
    public static final String GET_ASSTLOCATION="getAsstLocation";
    public static final String ASSTLOCATION="asstLocation";
    public static final String ASSTLOCATION_NAME="asstLocation.name";
    public static final String GETCM_CONTACT_INFO_SEQID="getCmContactInfoSeqId";
    public static final String NOTETYPE="noteType";
    public static final String ASSET_LONG_ASSETNUMBER="asset.longAssetNumber";
    public static final String  SYSLOOKUP_LOOKVALUE="sysLookup.lookValue";
    public static final String ADDRESS_ADDRESS1="address.address1";
    public static final String ADDRESS_ADDRESS2="address.address2";
    public static final String  ADDRESS_CITY="address.city";
    public static final String ADDRESS_STATEPROVINCE="address.stateProvince";
    public static final String COUNTRY_NAME="country.name";
    public static final String ADDRESS_ZIP="address.zip";
    public static final String TIMEZONE_GETTIMEZONE_SEQID="timeZone.getCmTimeZoneSeqId";
    public static final String ASSTLOCATION_LOCATIONID="asstLocation.locationId";
    public static final String DATASET="dataset";
    public static final String  Y_LETTER="y";
    public static final String PARMINFO="PARMINFO";
    public static final String CASE_CONDITION="CASE_CONDITION";
    public static final String LANG="lang";
    public static final String GETSYSLOOKUP_GETSYSLOOKUPSEQID="getSysLookup.getSysLookupSeqId";
    public static final String  LOOKUP="lookup";
    public static final String  LOOKUP_LISTNAME="lookup.listName";
    public static final String  LOOKUP_SORTORDER="lookup.sortOrder";
    public static final String  PDFTASK="PDF_TASK";
    public static final String APP_CONF="app/conf/gbsn00lp.ttf";
    public static final String PDF_NOTE="PDF_NOTE";
    public static final String CP1252="Cp1252";
    public static final String  GETKMKEP_LOOKUP="getKmKepLookup";
    public static final String STRCUSTOMERS="strCustomers";
    public static final String GET_KM_KEPTRACKING_SEQID="getKmKepKsTrackingSeqId";
    public static final String GET_KM_KEPTRACKING="getKmKepKsTracking";
    public static final String TRACKING="tracking";
    public static final String PATTERN="pattern";
    public static final String TRACKING_GETKMKEP_SEQID="tracking.getKmKepKsTrackingSeqId";
    public static final String HIT_OR_MSGFLAG="hitOrMissFlag";
    public static final String GET_KM_KEPTRACKING_GET_KM_KEPTRACKING_SEQID="getKmKepKsTracking.getKmKepKsTrackingSeqId";
    public static final String SCORE="score";
    public static final String GET_KM_KEPRT_TRACKING_SEQID="getKmKepRtTrackingSeqId";
    public static final String GET_KM_KEPRULETESTER_RESULTS="getKmKepRuleTesterResults";
    public static final String RT_RESULTS= "rtResults";
    public static final String STR_TRUE = "true";
    public static final String GET_KM_KEPRUL_TRACKINGHVO="getKmKepRuleTestTrackingHVO.getKmKepRtTrackingSeqId";
    public static final String VERSION="version";
    public static final String INT_CREATOR_LINK="intCreatorLink";
    public static final String RXID="rxId";
    public static final String RULEID="ruleId";
    public static final String RULEDEFN="ruleDefn";
    public static final String PASSWORD="password";
    public static final String USERNAME="userName";
    public static final String  FAULTCODE1="faultCode1";
    public static final String  FAULTCODE2="faultCode1";
    public static final String GETKMFAULTCODE_SEQID="getKmFaultCodesSeqId";
    public static final String GETKM_PROXIMITY_DEF_SEQID="getKmProximityDefSeqId";
    public static final String FINRULVO_GETKMDPDFIN_SEQID="finRulVO.getKmDpdFinrulSeqId";
    public static final String GETKMDPDCMPRUL="getKmDpdCmprul";
    public static final String  KMDPD_CMPRUL="KmDpdCmprul";
    public static final String LINKID="linkId";
    public static final String  GETKMDPDFIN_RUL="getKmDpdFinrul";
    public static final String FINRUL="Finrul";
    public static final String FINRUL_GETKMDPDFIN_SEQID="Finrul.getKmDpdFinrulSeqId";
    public static final String ORIGINALID="originalId";
    public static final String GETASST_MODEL="getAsstModel";
    public static final String GETCMDS_TEMPLATE_GETCMDS_TEMPLATE_SEQID="getCmDsTemplate.getCmDsTemplateSeqId";
    public static final String ISEDITABLE="isEditable";
    public static final String MODELID="modelId";
    public static final String MODEL_MODEL="MODEL";
    public static final String FIXED="Fixed-";
    public static final String MP="MP_";
    public static final String EOA_USERID ="eoaUserId";
    public static final String NOTES="notes";
    public static final String VEHICLE_OBJID="vehicleObjid";
    public static final String SHOP_OBJID="shopObjid";
    public static final String VEHICLE_OBJID_LFS="vehicleObjId";
    public static final String TASKID_NUMBER="taskIdNumber";
    public static final String JDPAD="JDPAD";
    public static final String VEHICLE_HEADER="vehicleHeader";
    public static final String VEHICLE_NO="vehicleNo";
    public static final String GETKMRECOM_LINKORIGIONAL="getKmRecomHistsForLinkOriginalRecom";
    public static final String RMD_Equipment="RMD Equipment";
    public static final String LOCOCAM="LOCOCAM";
    public static final String SMPP ="SMPP";
    public static final String OIL="OIL";
    public static final String KMRECOM="KmRecom";
    public static final String GETCASE_GETASSTASSET="getCmCase.getAsstAsset";
    public static final String GETCM_RECOMEXECUTION="getCmRecomExecution";
    public static final String GETRX_STATUS="getCmRxStatus";
    public static final String GETASSTASSET_ASSETNUMBER="getAsstAsset.assetNumber";
    public static final String  KMRECOM_TITLE="KmRecom.title";
    public static final String GETCM_RXSTATUS_RXFDBX="getCmRxStatus.rxFdbk";
    public static final String GETASSTASSET_LONGASSETNUMBER="getAsstAsset.longAssetNumber";
    public static final String GETCMRECOMEXECUTION_CLOSEDATE="getCmRecomExecution.closeDate";
    public static final String GETCMREXSTATUS_RXCLOSEDATE="getCmRxStatus.rxCloseDate";
    public static final String LOOKUP_LOOKVALUE="lookup.lookValue";
    public static final String LOOK_VALUE="look_value";
    public static final String ASSTMODEL= "asstModel";
    public static final String GETKM_VIRTUALEWUATION="getKmVirtualEquation";
    public static final String EQUATION_FAMILY="equation.family";
    public static final String  KMDPDFINRUL="KmDpdFinrul";
    public static final String KMDPDFINRUL_GET_KMDPDFINRUL_SEQID ="KmDpdFinrul.getKmDpdFinrulSeqId";
    public static final String GET_KMDPDFINRUL_GET_KMDPDFINRUL_SEQID ="getKmDpdCmprul.getKmDpdCmprulSeqId";
    public static final String GET_CMCONTACT_INFO="getCmContactInfo";
    public static final String CONTACT_INFO="contactInfo";
    public static final String CONTACT_INFO_GETASSTLOCATION="contactInfo.getAsstLocation";
    public static final String ASSTLOC="asstLoc";
    public static final String ASSTLOC_GETASSTASSETS="asstLoc.getAsstAssets";
    public static final String ASSTASSET="asstAsset";
    public static final String STRGETKMMANRUNSEQID="strGetKmManRunSeqId";
    public static final String RUNNING="RUNNING";
    public static final String COMPLETE="COMPLETE";
    public static final String NO_NEW_FAULTS="No New Faults";
    public static final String NO_DIA_RULE_FIRED="No Diag Rule Fired";
    public static final String ALL_FAULTS_FILTERED="All Faults Filtered";
    public static final String NO_FILTER_RULE_FIRED="No Filter Rule Fired";
    public static final String  CASE_CREATED="Case Created";
    public static final String CR_TOOLID="CR";
    public static final String FILTER="Filter";
    public static final String FAVOURITE_FILTER="FILTER";
    public static final String GETKMMAN_RUNSEQID="getKmManRunSeqId";
    public static final String FAULTS_FILTERED ="Faults Filtered";
    public static final String FEW_FAULTS_FILTERED="Few Faults Filtered";
    public static final String NO_FAULTS_NO_RULE_FIRED="No Faults/No Rule Fired";
    public static final String CC="CC";
    public static final String GET_TOOL_RUN="getToolRun";
    public static final String RUN_GETTOOL_RUNSEQID="run.getToolRunSeqId";
    public static final String  START_TIME="startTime";
    public static final String CPT_TIME="cptTime";
    public static final String ACCCA="ACCCA";
    public static final String DCCCA="DCCCA";
    public static final String CCA="CCA";
    public static final String AC6000="AC6000";
    public static final String EDP="EDP";
    public static final String V="v_";
    public static final String VPN="VPN";
    public static final String ENG="ENG";
    public static final String ANM="ANM";
    public static final String  SIMPLE="Simple";
    public static final String COMPLEX="Complex";
    public static final String GETKMTRACER="getKmTracer";
    public static final String TRACER="tracer";
    public static final String GETKMTRACER_GETKMDPDFINRUL="getKmTracer.getKmDpdFinrul";
    public static final String TESTER_GETTOOLJDPAD_TRACKINGSEQID="tester.getToolJdpadTrackingSeqId";
    public static final String GETKM_RUNRECREATOR="getKmRunRecreator";
    public static final String RUNRECT="runrecrt";
    public static final String RUNRECT_GETKMRUNRECREATSEQID="runrecrt.getKmRunRecreatorSeqId";
    public static final String GETTOOLRUNSEQID="getToolRunSeqId";
    public static final String RUNID="runId";
    public static final String GETKMRECOMBY_LINKCURRENTRECOM="getKmRecomByLinkCurrentRecom.getKmRecomSeqId";
    public static final String STRCREATED_BY="strCreatedBy";
    public static final String STRLAST_UPDATEDBY="strLastUpdatedBy";
    public static final String STRACTIVITYBY="strActivateBy";
    public static final String STRISACTIVE ="strIsActive";
    public static final String STRISCOMPLETE="strIsComplete";
    public static final String STRRULETITLE="strRuleTitle";
    public static final String LOCKEDBY="lockedBy";
    public static final String ORIGIONAL_RECOMID="orignalRecommId";
    public static final String ASSTMODEL_MODELNAME="asstModel.modelName";
    public static final String COLUMNNAME="columnName";
    public static final String CONFIGITEM="configItem";
    public static final String FCN="fcn";
    public static final String PROCESS_TYPE="processType";
    public static final String EQUATION="equation";
    public static final String GETKMVIRTUAL_EQUATIONSEQID="getKmVirtualEquationSeqId";
    public static final String EXIST_VIRTUAL="EXIST_VIRTUAL";
    public static final String CONTAINS="Contains";
    public static final String STARTS_WITH="Starts with";
    public static final String ENDS_WITH="Ends with";
    public static final String STRMODELARAY="strModelArray";
    public static final String STRRXSTATUS="strRxStatus";
    public static final String STRTYPE="strType";
    public static final String STRLOCOIMPARRAY="strLocoImpArray";
    public static final String STRSUBSYSTEMARRAY="strSubsystemArray";
    public static final String STRLASTUPDATED="strLastUpdated";
    public static final String GETKMRECORDMTMMODELS="getKmRecomMtmModels.getAsstModel";
    public static final String GETKMRECORDMTMMODEL="getKmRecomMtmModels";
    public static final String REVHISTORY="revHistory";
    public static final String STRTODATE="strToDate";
    public static final String STRFROMDATE="strFromDate";
    public static final String STREXPCTRL="strExpCtrl";
    public static final String  STRNOTE="strNote";
    public static final String CFG="CFG";
    public static final String FLEET_FILTERID="FLEET";
    public static final String GETKMDPDFINRUL_GETKMDPDFINRULSEQID ="getKmDpdFinrul.getKmDpdFinrulSeqId";
    public static final String NOT_SYMBOL="!";
    public static final String AC4400="AC4400";
    public static final String SEVEN_NOT_EQUAL="7!";
    public static final String SEVEN_STRING="7";
    public static final String  SEVEN_EQUALS="7";
    public static final String SIM_SEQID="simSeqId";
    public static final String RULETITLE="ruleTitle";
    public static final String FINRUL_OBJID="finRul_objid";
    public static final String RULEDESC="ruleDescription";
    public static final String LOOKBACK="LOOKBACK";
    public static final String FINRULE2SIMRULE="finRule2SimRule";
    public static final String FINRULE2CPMRULE="finRule2CmpRule";
    public static final String RULEFAMILY="ruleFamily";
    public static final String FINRULEOBJID="finRuleObjid";
    public static final String FAULTSCODE="faultCode";
    public static final String SUBID="subId";
    public static final String FINAL_RULEID="final_rule_id";
    public static final String SIMFUNCTION="simFunction";
    public static final String NUMBER_NINE="9";
    public static final String SEVEN_STRING_NOT="!7";
    public static final String SYMBOL_PLUS="+";
    public static final String SHIFT="SHIFT";
    public static final String VALUE1="value1";
    public static final String VALUE2="value2";
    public static final String SEQVALUE="seqValue";
    public static final String SIMRULEOBJID ="simRuleObjid";
    public static final String COLNAME_OBJUD="colNameObjid";
    public static final String SIM_FCN="simFCN";
    public static final String EDFPARM="edfParm";
    public static final String ANOMOBJID="anomObjid";
    public static final String TECHNIQUE="Technique";
    public static final String COMPLEX_RULE_SEQID="complexRuleSeqId";
    public static final String TIME="time";
    public static final String FREQUENCY ="frequency";
    public static final String COMPLEX_FUNCTION="complexFunction";
    public static final String RULE1SIMRUL="rule1SimRul";
    public static final String RULE2SIMRUL="rule2SimRul";
    public static final String RULE1CMPMRUL="rule1CmpRul";
    public static final String RULE2CMPMRUL="rule2CmpRul";
    public static final String RULE1FINRULE="rule1FinRul";
    public static final String RULE2FINRUL="rule2FinRul";
    public static final String HIS_SEQ="his_seq";
    public static final String PREVIOUS_VERSION="previousVersion";
    public static final String ORIGIONAL_ID="original_id";
    public static final String REVISION_HISTORY="revision_history";
    public static final String RULEHIS2_FINRUL="ruleHis2FinRul";
    public static final String RULEDEF_OBJID="ruleDefObjid";
    public static final String MESSAGE="Message";
    public static final String ASSET_NUMBER_ROW_GET ="ASSET_NUMBER";
    public static final String CUSTOMER_ID_ROW_GET="CUSTOMER_ID";
    public static final String GET_ASST_ASSET_SEQ_ID="GET_ASST_ASSET_SEQ_ID";
    public static final String  GETASSTVEHCFS="getAsstVehcfgs";
    public static final String GETASSTVEHCFS_ASSTASSET="getAsstVehcfgs.getAsstAsset";
    public static final String FIXED_STYLE="fixed";
    public static final String  HEADERDETAIL="HeaderDetail";
    public static final String HEADERGRPDETAIL="HeaderGrpDetail";
    public static final String FINALHEADERWIDTH ="FinalHeaderWidth";
    public static final String FIXEDHEADERWIDTH="FixedHeaderWidth";
    public static final String  VARIABLEHEADERWIDTH="VariableHeaderWidth";
    public static final String FAULTHEADERLIST="faultHeaderList";
    public static final String GRPHEADERLIST ="grpHeaderList";
    public static final String CONTROLLERFLAG="controllerFlag";
    public static final String QNX_EQUIPMENT="QNX Equipment";
    public static final String OIL_ANOMALY ="Oil Anomaly";
    public static final String LOCO="Loco";
    public static final String RMD="RMD";
    public static final String QNX="QNX";
    public static final String DPEAB="DPEAB";
    public static final String DP="DP";
    public static final String EAB="EAB";
    public static final String CONTROLLER_CFG="controller_cfg";
    public static final String CONTROLLER_SOURCE_ID="controller_source_id";
    public static final String DS_COL_AVAIL="ds_col_avail";
    public static final String IN_COL_AVAIL ="in_col_avail";
    public static final String QNX_COL_AVAIL="qnx_col_avail";
    public static final String RR_COL_AVAIL="rr_col_avail";
    public static final String SORT_ORDER1="sort_order";
    public static final String VVF_COL_AVAIL ="vvf_col_avail";
    public static final String  MODEL_PARM2CE_PARM="model_parm2ce_parm";
    public static final String CUSTOBJID ="custObjId";
    public static final String  MINFAULTSEQID="minFaultSeqId";
    public static final String  MAXFAULTSEQID="maxFaultSeqId";
    public static final String  RULE_TYPE="Rule_type";
    public static final String  FINAL_ID ="Final_id";
    public static final String RECOM_OBJID  ="recom_objid";
    public static final String SERVICE_OBJID ="service_objid";
    public static final String CUSTOBJID1 ="custObjid";
    public static final String CUST2BUSORG="cust2busorg";
    public static final String  CUST2RULEDEF="cust2ruledef";
    public static final String EXCLUDE="exclude";
    public static final String FLEET2CUST="fleet2cust";
    public static final String  FLEET2FLEET="fleet2fleet";
    public static final String FUNCTIONID="functionID";
    public static final String CONFIGNAME="ConfigName";
    public static final String RULESEQID="ruleSeqId";
    public static final String GETKMRULEDEFFILTERS="getKmRuledefFilters";
    public static final String GETASSTVEHCFGS_GETASSTASSET_GETASSTMODEL="getAsstVehcfgs.getAsstAsset.getAsstModel";
    public static final String ASSTVEHCFGS="asstVehcfgs";
    public static final String ASSTMODEL_FAMILY="asstModel.family";
    public static final String RXHIS="rxHis";
    public static final String  RXHIS_GETKMRECOMBYLINKORIGINALRECOM_GETKMRECOMSEQID="rxHis.getKmRecomByLinkOriginalRecom.getKmRecomSeqId";
    public static final String ORGRXID ="orgRxID";
    public static final String CURRVAL="CURRVAL";
    public static final String STRTITLE ="strTitle";
    public static final String STRNOTES="strNotes";
    public static final String GETKMRECOMHISTSFORLINKCURRENTRECOM="getKmRecom.getKmRecomHistsForLinkCurrentRecom";
    public static final String GETCMCASE_TITLE="getCmCase.title";
    public static final String  GETSYSLOOKUPBYLINKSTATUSLOOKUP_LOOKVALUE="getSysLookupByLinkStatusLookup.lookValue";
    public static final String  GETCMCASE_CREATIONDATE="getCmCase.creationDate";
    public static final String GETKMRECOMHISTS_FORLINKCURRENTRECOM ="getKmRecomHistsForLinkCurrentRecom";
    public static final String GETKMRECOMHIST="getKmRecomHist";
    public static final String DEACTIVE="deactive";
    public static final String  ACTIVE_DEACTIVE="active,deactive";
    public static final String ACTIVE_DRAFT ="active,draft";
    public static final String DEACTIVE_DRAFT="deactive,draft";
    public static final String FINRULEID="finRuleId";
    public static final String ManualJDPAD="ManualJDPAD";
    public static final String MANUAL_RUN_STATUS ="MANUAL_RUN_STATUS";
    public static final String  CUSTOMER1="CUSTOMER";
    public static final String  GETKMDPDRULHISES="getKmDpdRulhises";
    public static final String CASE_OBJ_ID="caseObjId";
    public static final String ACTION_TYPE="C";     
    public static final String RULEHIS="rulehis";
    public static final String RULEHIS_ORIGINALID="rulehis.originalId";
    public static final String COMPLETTED ="completed";
    public static final String VERSIONNO="versionNo";
    public static final String FINRUL_SMALLCASE ="finrul";
    public static final String  FINRUL_GETKMDPDFINRULSEQID="finrul.getKmDpdFinrulSeqId";
    public static final String  GETTOOLJDPADTRACKING="getToolJdpadTracking";
    public static final String TESTER ="tester";
    public static final String ACTIVE_DEACTIVE_DRAFT="active,deactive,draft";
    public static final String RECENT="recent";
    public static final String  STRRULE_ID="strRuleID";
    public static final String GETKMDPDCMPLINKSEQID ="getKmDpdCmpLinkSeqId";
    public static final String GETKMRECOMBYLINKORIGINALRECOM ="getKmRecomByLinkOriginalRecom";
    public static final String GETKMRECOMBYLINKCURRENTRECOM="getKmRecomByLinkCurrentRecom";
    public static final String LINKORIGINALRECOM ="LinkOriginalRecom";
    public static final String  LINKCURRENTRECOM="LinkCurrentRecom";
    public static final String  LINKORIGINALRECOM_GETKMRECOMSEQID="LinkOriginalRecom.getKmRecomSeqId";
    public static final String LINKCURRENTRECOM_RECOMSTATUS="LinkCurrentRecom.recomStatus";
    public static final String  LINKCURRENTRECOM_VERSION="LinkCurrentRecom.version";
    public static final String DPDCOLNAME ="dpdColname";
    public static final String  DPDCOLNAME_COLUMNNAME="dpdColname.columnName";
    public static final String  RECOMMVO="recommVO";
    public static final String  RECOMTASKDOCVO="recomTaskDocVO";
    public static final String RECOMMVO_GETKMRECOMSEQID="recommVO.getKmRecomSeqId";
    public static final String QUEUENAME="queueName";
    public static final String TASKID ="taskID";
    public static final String RECOMMSEQID="recommSeqId";
    public static final String GETASSTMODELSEQID="getAsstModelSeqId";
    public static final String GETKMRECOMTASK="getKmRecomTask";
    public static final String GETCMRECOMEXECUTIONSEQID="getCmRecomExecutionSeqId";
    public static final String RXCASEID1="rxCaseId";
    public static final String GETKMRECOMSUBSYSTEMS="getKmRecomSubsystems";
    public static final String GETKMRECOMSUBSYSTEMS_GETSYSLOOKUP="getKmRecomSubsystems.getSysLookup";
    public static final String MANUAL_RUN="Manual_Run";
    public static final String  RULE_TESTER="Rule_Tester";
    public static final String RUN_RECREATOR ="Run_Recreator";
    public static final String ON_THE_FLY_DS="On_the_fly_DS";
    public static final String DEFQUEUE="DefQueue";
    public static final String EOAUSER_ID="eoaUserId";
    public static final String OPEN="OPEN";
    public static final String NOT_OPEN="NotOpen";
    public static final String FAMILY_INITIAL_LOADER="FAMILY";
    public static final String STRFLEET="strFleet";
    public static final String STRUNITNUMBER="strUnitNumber";
    public static final String  STRMODEL="strModel";
    public static final String CONFIGPARAM=":configParam";
    public static final String CONFIGPARAM_PARAM="configParam";
    public static final String CONFIGVALUE1=":configValue1";
    public static final String CONFIGVALUE1_PARAM="configValue1";
    public static final String STRCUSTOMER ="strCustomer";
    public static final String CONFIGVALUE2=":configValue2";
    public static final String CONFIGVALUE2_PARAM="configValue2";
    public static final String LOCATION_ID="locationId";
    public static final String CITY_NAME="CITY";
    public static final String STATE_NAME="STATE";
    public static final String DEFAULT_TIMEZONE="DEFAULT_TIMEZONE";
    public static final String RESOURCE_PATH="ps.application.appdata";
    public static final String RESOURCE_PROPERTY_FILE="rmd.properties";
    public static final String NO_DOWNLOADS="No Downloads";
    public static final String COUNT="count";
    public static final String TRACE_ID="strTraceID";
    public static final String PIPELINE_ESCAPE="\\|";
    public static final String GET_CM_ADDRESS="getCmAddress";
    public static final String ENV_RMDPROP_PATH_VAR="ps.application.appdata";
    public static final String ENV_OMD="GMT";
    public static final String DEFAULT_TIMEZONE_VALUE="DEFAULT_TIMEZONE";
    public static final int NUMERIC_4=4;
    public static final String CACHE_ASSET_LOCATION="locationCache";
    public static final String CACHE_DPEAB_DATASCREEN_HEADER="dpeab_dataScreen_headerCache";
    public static final String DEFAULT_PASSWORD_OMD = "Start64meup";
    public static final String ROLE = "role";
    public static final String PREFERENCE_ROLE = "Role";
    public static final String TIMEZONE = "timezone";
    public static final String PREFERENCE_TIMEZONE = "Timezone";
    public static final String PREFERENCE_LANGUAGE = "Language";
    public static final String TIMEZONE_ID = "timezoneId";
    public static final String LANGUAGE_ID = "languageId";
    public static final String PREF_TYPE = "prefType";
    public static final String PREF_VALUE = "prefValue";
    public static final String USER_STATUS = "userStatus";
    public static final String ROLE_SEQ_ID = "roleseqId";
    
    public static final String EST_REP_TIME = "estRepairTime";
    public static final String URGENCY_PARAM = "urgency";
    public static final String RX_TITLE_IDS = "rxTitleIds";
    public static final String PARAM_CASE_TYPE = "caseType";
    
    public static final String RECOM_DELVS="recomDelvs";
    public static final String SCREENNAME="screenName";
    public static final String SHOW="Show";
    public static final String USR_ROLE_SEQ_ID="usrRoleSeqId";
    public static final String FILTER_ID="filterId";
    public static final String TEXT="text";
    public static final String NUMBER="number";
    public static final String DATE_TIME="datetime";
    public static final String DECIMAL="decimal";
    public static final String COLUMN_TYPE="columnType";
    public static final String CASE_TYPE = "CASE_TYPE";
    public static final String FLEET_ID = "fleetId";
    
    public static final String CACHE_ACTIONABLE_RX_TYPE = "actionableRxTypeCache";
    public static final String CACHE_NON_ACTIONABLE_RX_TITLES = "nonActionableRxTitlesCache";
    
    public static final String CASE_TYPE_CONTRAINT = "casetypeLookup.sysconfigLookupSeqId";
    public static final String CASETYPE="Case Type";
    public static final String STATE_VAL="state";
    public static final String RECOM_STATUS = "recomStatus";
    public static final String RECOM_GROUPID = "rxGrpId";
    public static final String FILTER_NAME = "filterName";
    public static final String MULTIFAULT="MULTIFAULT";
    public static final String BIG_O="O";
    public static final String RECOMMENDATION_LOG="/recommendations/log/";
    public static final String LOG_EXTN=".log";
    public static final String MINUS_ZERO_ONE="-01";
    public static final String MINUS_ZERO_STRING="-0";
    public static final String PDF_EXTN=".pdf";
    public static final String FIELD_REQUEST="Field Request";
    // Added for LastFaultStatus story -- 20-Dec-2012
    public static final String ASSET_LAST_EOA_FAULT = "Last EOA Fault";
    public static final String ASSET_LAST_ESTP_DOWNLOAD = "Last ESTP Download";
    public static final String ASSET_LAST_PP_ATS_MSG = "Last PP/ATS Msg";
    public static final String MAP_LAST_REFRESH_TIME = "MAP_LAST_REFRESH_TIME";
    
    public static final String GET_KM_MASTERVIRTUALSEQID="getKmMasterVirtualSeqId";
    public static final String GET_KM_VIRTUALTITLE="description";
    public static final String GET_KM_VIRTUAL_LASTUPDATEDBY="lastUpdatedBy";
    public static final String GET_KM_VIRTUAL_LASTUPDATEDDATE="lastUpdatedDate";
    public static final String GET_KM_CREATIONBY="createdBy";
    public static final String GET_KM_VIRTUAL_CREATIONDATE="creationDate";
    public static final String GET_KM_VIRTUAL_FAMILY="family";
    public static final String MAX_UPDATED_DATE = "maxUpdatedDate"; 
    
    public static final String FAV_FILTER_TEXT_CODE = "T";
    public static final String FAV_FILTER_DATE_CODE = "D";
    public static final String FAV_FILTER_NUMBER_CODE = "N";
    public static final String FAV_FILTER_DECIMAL_CODE = "L";
    public static final String PRODUCT_CONF_NAME_LST = "productNameLst";
    public static final String PRODUCT_CONF_CUSTOMER_ID = "prodCustomerId";
    public static final String GET_ELG_ASST_ID="getElgAsstId";
    public static final String GET_ELG_ASSTS="elgAssets";
    public static final String ELG_ASSTS_ROLE_SEQ_ID="elgAssets.roleSeqId";
    public static final String GET_USR_ROLE_NAME="roleId.roleName";
    public static final String ASSET_SEQ_ID="assetSeqId";
    
    public static final String KPI_RX_ACCURACY="rxAccuracy";
    public static final String KPI_NTF="noTroubleFound";
    public static final String KPI_RESPONSE_TIME="responseTime";
    public static final String MONTH_COUNT="monthCount";
    public static final String N_A = "NA";
    public static final String DEFAULT_STRING = "Default";
    public static final String QUERY_STRING = "query";
    public static final String PRD_ROLENAME="getProductRoleId";
    public static final String  ROLE_ID_NAME="roleId.roleName";
    public static final String COUNTRYCODE = "country";
    public static final String ELAPSED_TIME = "elapsedTime";
    public static final String REGION = "region";
    public static final String TIMER = "timer";
    public static final String STATECODE = "state";
    public static final String ORGID = "orgId";
    public static final String CAPS_ALL = "ALL";
    public static final String CITY = "city";
    public static final String LESSER_LONGITUDE_DIRECTION = "E";
    public static final String GREATER_LONGITUDE_DIRECTION = "W";
    public static final String LESSER_LATITUDE_DIRECTION = "N";
    public static final String GREATER_LATITUDE_DIRECTION = "S";
    public static final String NOT_MOVING_ALL = "Not Moving -All";
    public static final String DWELL_ONLY = "Dwell only";
    public static final String ENGINE_ON_NOT_MOVING = "Engine On & Not Moving";
    public static final String DWELL_NOT_MOVING = "Dwell (or) Not Moving";
    public static final String QNX_INSTALLED = "QNX INSTALLED";
    public static final String INSTALLED_GOOD = "INSTALLED/GOOD";
    public static final String NOTIFICATION_LOCATION = "location";
    public static final String NOTIFICATION_COMMENTS_COLON = "comments :";
    public static final String NOTIFICATION_COMMENTS = "comments";
    public static final String RECOM_TITLE = "recomTitle";
    public static final String REPAIR_CODE = "Repair Code";
    public static final Object LIKE = "LIKE";
    public static final String SUB_SYSTEMS = "Subsystems";
    public static final String MASS_APPLY_CASE_TITLE = "AutoCase for SpRX";
    public static final String MASS_APPLY_PHONE = "9999";
    public static final String MASS_APPLY_PRIORITY = "4-LOW";
    public static final String MASS_APPLY_NOTE = "This note is logged by createcase on case ";
    public static final String MASS_APPLY_CASE_NOTE = "Case created through MassApply";
    public static final String MASS_APPLY_ESCL = "!";
    public static final String REISSUE_FLAG = "reissueFlag";
    public static final String OB_MSG_ID = "obMsgId";
    public static final String CASE_RECOM_OBJID = "caseRecomObjid";
    public static final String RECOMMENDED_CASE_QUEUE = "820. RECOMMENDED CASES";
    public static final String DELIVERED_QUEUE_TYPE = "Delivered";
    public static final String LOOKUP_CACHE ="RMDService_lookupCache";
    public static final String USER_CACHE ="RMDService_userCache";
    public static final String QUEUE_CACHE ="queue_cache";
    public static final String ROLE_DESCRIPTION = "roleDesc";
    public static final String PRIVILEGE_NAME = "privilegeName";
    public static final String PRIVILEGE_ID = "privilegeId";
    public static final String OMD_SOA = "OMD SOA";
    public static final String GET_CM_CUSTOMER_HVO = "GetCmCustomerHVO";
    public static final String ASSETGRP_CUSTOMER = "ASSETGRP.getCmCustomer";
    public static final String GET_CM_CUSTOMER_HVO_CUSTOMER_ID = "GetCmCustomerHVO.customerId";
    public static final String PRODUCT_NAME = "productName";
    public static final String CUSTOMER_ID_LIST = "customerIdList";
    public static final String CUSTOMER_CHANGE ="defaultCustomer";
    public static final String NONE = "None";
    public static final String EXECUTED = "Executed";
    public static final String OPEN_CASE_RX_STATUS="Open";
    public static final String CLOSED_CASE_RX_STATUS="Closed";
    public static final String GET_USR_USERS ="getUsrUsersHVO";
    public static final String ASSET_GRP = "ASSETGRP";
    public static final String ASSET_FROM = "assetFrom";
    public static final String ASSET_TO = "assetTo";
    public static final Object ASSET_SOURCE = "ASSET_SOURCE";
    public static final String PRIMARY = "primary";
    public static final String Y_LETTER_UPPER = "Y";    
    public static final String CASE_CREATION_HISTORY_MESSAGE ="This note is logged by create case API appl on case ";
    public static final String CASE_DISPATCH_FROM = "From";
    public static final String CASE_DISPATCH =  "to Queue";
    public static final String DELV_RX_PDF_PATH = "DELV_RX_PDF_PATH";
    public static final String RECOMOBJID ="recomObjid";
    public static final String LINK_TYPE = "getSysLookupByLinkTypeLookup";
    public static final String ASSET_NUMBER_LIKE = "assetNumberLike";
    public static final String RX_OBJID = "rxObjid";
    public static final String GETCURRENT_CM_RECOMDELV = "getCurrentCmRecomDelv";
    public static final String LINK_CASE = "linkCase";
    public static final String PDF_URL = "DELV_PDF_URL";
    private static final String[] DEFAULT_CASE_OWNERS ={"AUTO_CASE_CLOSURE","AUTO_RX_DELIVER","AUTOGEN","CASE_CREATION"};
    

	public static final String TO = "to";
    public static final String CASE_ACCEPT_START = "Ownership transferred from user ";
    public static final String CASE_ACCEPT_NO_QUEUE = "Ownership transferred to ";
    public static final String CASE_TRANSFER_BEGIN = "From Queue ";
    public static final String ASSET_LPAD_ASSETNUMBER="asset.lpadAssetNumber";
    public static final String LINK_RECOM_DELV_SEQ_ID = "getCmRecomDelv.getCmRecomDelvSeqId";
    public static final String N_LETTER_UPPER = "N";
    public static final String TRUE_STRING = "true";
    public static final String CLOSED_RX_LOOKBACK_DAYS = "lookBackDays";    
    
    /**
     * Added for Health Check
     */
    public static final String HC_ASSET_TYPE_LEGACY ="LEGACY";
    public static final String HC_ASSET_TYPE_EVO ="EVO";
    public static final String MCS_APPLICATION_ID ="89";
    public static final String MCS_MESSAGE_ID ="A3";
    public static final String HC__REQUEST_TYPE ="externalHC";
    public static final String WEBSERVICE_URL="MCS_WEBSERVICE_BASE_URL";
    public static final String MCS_USERNAME="MCS_WEBSERVICE_USERID";
    public static final String MCS_PASSWORD="MCS_WEBSERVICE_PASSWORD";
    //Utility method constants
    public static final String DAO_GET_CASE_EXCEPTION = "DAO_GET_CASE_EXCEPTION";
    public static final String DAO_GET_RECOM_DELVS_EXCEPTIONS = "DAO_GET_RECOM_DELVS_EXCEPTIONS";
    public static final String DAO_CONNECTION_EXCEPTION = "DAO_CONNECTION_EXCEPTION";
    
    public static final String  CM_GENERIC_NOTES = "getCmGenericNotesLogs";
    public static final String RX_CLOSE_DATE = "rxCloseDate";
    public static final String REPAIR_CODES = "repairCode";
    public static final String SERVICE_REQ_OPEN_MESSAGE_PART1 = "Cannot close this case. The service request id ";
    public static final String SERVICE_REQ_OPEN_MESSAGE = "Rx cannot be closed when service request id is Open";
    public static final String SERVICE_REQ_OPEN_MESSAGE_PART2 = " is open. Please contact field shop.";
    public static final String B2B_VALIDATION_MESSAGE = "Not all feedbacks for this case have been received from the customer.";
    public static final String NO_REPAIR_CODE_MESSAGE = "Repair codes for this case grid must have at least one Repair Code.";
    public static final String ADD_REPAIR_CODE_MESSAGE = "Please add additional repair code.";
    public static final String CASE_SUCCESS = "caseSuccess";
    public static final String MISS_CODE = "missCode";
    public static final String ACC_RECOM = "accRecom";
    public static final String GOOD_FDBK = "goodFdbk";
    public static final String CUST_FDBK_OBJID = "custFdbkObjid";
    public static final String RX_NOT_EXECUTED_MSG = "Rx has not been executed. Cannot be closed.";
    public static final String PINPOINT_PROBLEM = "PinPoint Problem";
    public static final String REPLACE_MODIFY_RX_210 = "REPLACE_MODIFY_RX_210";
    public static final String CUSTOMER_NAME = "customerName";
    // added for GeofenceReport
    public static final String CACHE_GEOFENCE_MASTER_PROXIMITY="geofence_master_proximityCache";
    public static final String FROM_ROAD_NUMBER="fromRoadNo";
    public static final String TO_ROAD_NUMBER="toRoadNo";
    public static final String GEOFENCE="GEOFENCE";
    public static final String D = "d";
    public static final String HRS = "hrs";
    public static final String MIN = "min"; 
    public static final String HC_EXTERNAL_CFG_DEF_DESC= "HC_EXTERNAL_CFG_DEF_DESC";
    public static final String HC_CFG_DEF_DESC="cfgDefDescription"; 
    public static final String STR_FALSE = "false";
    public static final String GEOFENCE_EDIT = "edit";
    public static final String GEOFENCE_ADD = "add";
    public static final String GEOFENCE_NAME="geofenceName";
    public static final String PROXIMITY_EVENT="proxEvent";
    public static final String UPPER_LEFT_LATITUDE="upLeftLat";
    public static final String UPPER_LEFT_LONGITUDE = "upLeftLong";
    public static final String LOWER_RIGHT_LATITUDE = "lowRightLat";
    public static final String LOWER_RIGHT_LONGITUDE = "lowRightLong";
    public static final String UPDATED_BY = "updatedBy";
    public static final String UPDATED_DATE = "updatedDate";
    public static final String CREATED_BY = "createdBy";
    public static final String GEOFENCE_SEQ_ID = "geofenceSeqId";
    public static final String GEOFENCE_PROXIMITY_CACHE = "geofence_master_proximityCache";
    
    /*Added for Visualization Changes*/ 
    public static final String SOURCE_TYPE="sourceType";
    public static final String ALL_UPPERCASE="ALL";
    public static final String HC_CFG_DEF_DESC_KEY = "HC_CFG_DEF_DESC_KEY"; 
    
    public static final String CASE_ID = "caseId";
    public static final String DISCRIPTION = "Description";
    public static final String SEARCH_VALUE = "searchValue";
    public static final String OMD_DATASCREEN_RECORD_LIMIT="OMD_DATASCREEN_RECORD_LIMIT";
    public static final String RESTRICTED_SPECIAL_CHAR_PATTERN ="[\\<\\>\\'\\/\\\\]";
    public static final String ENGLISH_UPPER = "EN";
    public static final String GET_CM_HISTORY_SEQ_ID = "getCmCaseHistorySeqId";
    public static final String FORMAT_DD_MM_YY = "yyyy-MM-dd";
    
    public static final String COMMON_ERROR_MSG="An Error occurred when processing the request. Please try again later.";
    /*Added for HealthCheck Changes*/
    public static final String ROAD_INTIAL="roadIntial";
    public static final String PLATFORM_EGA = "EGA";
    public static final String PLATFORM_QNX = "QNX";
    public static final String EGA_CODE ="HC_CUST";
    public static final String MSG_EGA_CODE ="MsgCode";
    public static final String REQUEST_PENDING="pendingRequest";
    public static final String SET_REQUEST_PENDING="setPendingRequest";
    public static final String ASSET_IP="assetIp";
    public static final String REQUEST_STATUS="reqStatus";
    public static final String OB_REQUEST_STATUS="obReqStatus";
    public static final String STATUS_FAILED="FAILED";
    public static final String VEHICLE_OBJ_ID="vehicleObjId";
    public static final String PASS_TO_QUEUE_EXCEPTION = "Exception while passing the message to EGA Queue.";
    public static final String REQUEST_NUMBER="requestNum";
    public static final String POST_SAMPLES="samples";
    public static final String MP_GROUP_NAME="mpGrpName";
    public static final String TEMPLATE_EDP = "EDP";
    public static final String TEMPLATE_SDP = "SDP";
    public static final String MESSAGE_ID = "messageId";
    public static final String OUT_VALUE = "OUT";
    public static final String CASE_ID_LIKE = "caseIdLike";
    public static final String MISS_4B = "Miss 4B";
    public static final String MISS_4F = "Miss 4F";
    public final static String SEQUENCE_COUNT = "sequenceCount";
    public final static String COUNT_RX_DELV = "countRxDelv";
    public static final String STORE_JAXB_XMLS="perf.store.client.unmaarshelled.xml";
    public static final String ALL_CUSTOMER="ALLCUSTOMER";
    public static final String ALL_MODEL="ALLMODEL";
    public static final String DAO_EXCEPTION_RX_HISTORY = "DAO_EXCEPTION_RX_HISTORY";
    public static final String RX_TITLE_CACHE = "RMD_rxTitleCache";
    public static final String RX_LOCO_IMPACT_CACHE = "RMD_locoImpactCache";
    public static final String GET_RECOM_FEEDBACK = "recommFdbk";
    public static final String GETCASE_CASEID = "case.caseId";
    
    public static final String MCS_ATS_APPLICATION_ID ="81";
    public static final String MCS_ATS_MESSAGE_ID ="A1";
    public static final String GEOFENCE_FLEETS = "fleets";
    public static final String GEOFENCE_NAMES = "geofences";
    public static final String LONG_RULE = "L";
    public static final String ACTIVE_FLAG = "Active";
    public static final String INACTIVE_FLAG = "Inactive";
    
    //Added by Vamsee for Dispatch Functionlaity
    public static final String GET_DISPATCH_USERID="userId";
    public static final String FAILURE_MSG="failure";
    public static final String SUCCESS_MSG="successful";

    //Added by Vamsee For AddNOtea Functionality
    public static final String GET_ADD_NOTES_USERID="userID";//Due to some conflicts while fetching
    public static final String GET_NOTES="notes";
    public static final String IS_STICKY="isSticky";
    public static final String GET_OBJID="objId";
    public static final String CASE_TITLE="caseTitle";
    public static final String CASE_TYPE_INFORMATIONAL="Informational";
    public static final String STRING_UNIT="unit";
    public static final String STRING_CASE="case";
    public static final String LETTER_U="U";
    public static final String LETTER_C="C";
    public static final String PREVIOUS_CASE_TYPES="PreviousCaseTypes";
    public static final String NOT_CURRENT_OWNER="NOT_CURRENT_OWNER";
    public static final String USER="user";
    public static final String PREVILIGE="previlige";
    public static final String CASE_MANAGEMENT_PRIVILEGE="CASE_MANAGEMENT_PRIVILEGE";
    public static final String NO_EOA_USER="NO_EOA_USER";
    public static final String CASE_ALREADY_OPENED="CASE_ALREADY_OPENED";
    public static final String STRING_M="M";
    public static final String STRING_DEL="DEL";
    public static final String LETTER_M="M";
    public static final int DIGIT_ONE=1;
    public static final String STRING_NULL="NULL";
    public static final String STRING_MISS="MISS";
    public static final String STRING_4C="4C";
    //#View Closure
    public static final String GET_CUST_FDBK_OBJID="custFdbkObjId";
    public static final String SERVICE_REQ_ID="serviceReqId";
    public static final String CASE_NOTES="Notes";
    public static final String CASE_ACTION_TYPE="type";
    public static final String RECOMM_MATCH_CONFIG="RECOMM_MATCH_CONFIG";
    public static final String REPLACE_REPAIR_CODE="9005";
    //Find Queue
    public static final String QUEUE_OBJID = "queueObjId";
    
    //Mass Apply
    public static final String CONTACT_AUTOGEN="AUTOGEN";
    public static final long STATUS_OPEN_GBSTELM_ID = 268435947;
    public static final long TABLE_ACT_ENTRY_CREATE_CASE = 600;
    public static final long TABLE_ACT_ENTRY_IN_PROCESS = 900;
    public static final String CREATE = "CREATE";
    public static final String STR_NOTES = "Notes";
    public static final String QUEUE_PINPOINT = "850. PinPoint";
    public static final String QUEUE_CASES_IN_PROCEESS = "810. Cases In Process";
    public static final String COMMISSION="Commission";
    public static final String MASSAPPLY_NOTES="This note is logged by createcase on case";
    public static final String FROM_WIP_DEFAULT_TO_QUEUE="from WIP default to Queue ";
    public static final String CONTACT="Contact = ";
    public static final String PRIORITY=" Priority=4-Low ,Status = Open";
    public static final String MASSAPPLY_RECOM_NOTES="Delivering Multiple rx's by using Mass Apply Rx";
    public static final String DAO_EXCEPTION_MASS_APPLY_RX_DELIVER_RECOMMENDATION="DAO_EXCEPTION_MASS_APPLY_RX_DELIVER_RECOMMENDATION";
    public static final String AUTO_CASE_FOR_SPRX="AutoCase for SpRX";
    public static final String MAX_MASS_APPLY_RX_UNITS="max_mass_apply_rx_units";
    public static final String DATE_FORMAT_24HRS = "MM/dd/yyyy HH:mm:ss";
    
    //Added for Append, Close, Split
    public static final String CASE_TYPE_APPEND = "Case Type for Append";
    public static final String FROM_CASE_OBJID = "fromCaseObjId";
    public static final String TO_CASE_OBJID = "toCaseObjId";
    public static final String AR_OBJID = "arListObjId";
    public static final String ADDNL_INFO = "CaseAppend caused OMD to append to this case.Case referred from:";
    public static final String ADDNL_INFO_MERGE = "CasMerge caused OMD to merge to this case.Case referred from:";
    //Toolouptut
    public static final String LMS_LOCO_ID = "lmsLocoId";
    public static final String FC_AR_PROBABILITY = ".2";
    public static final String STR_FAULT = "Fault";
    public static final String STR_RULE = "Rule";
    public static final String CBR = "CBR";
    public static final String RULE = "Rule";
    public static final String Fault = "Fault";
    
    public static final String FAULT_STRATEGY_OBJID="fsObjId";
    public static final String FAULT_CODE_ID="faultCode";
    public static final String FLTSTART2FLTCODE="fltStart2FltCode";
    public static final String DIAGNOSTIC_WEIGHT ="diagnosticWeight";
    public static final String SUBSYS_WEIGHT ="subSysWeight";
    public static final String MODE_RESTRICTION="modeRestriction";
    public static final String FAULT_CLASSIFICATION="faultClassification";
    public static final String CRITICAL_FLAG="criticalFlag";
    public static final String FAULTNOTES="fssrxnote";
    public static final String FAULTLAGTIME="lagTime";
    public static final String FLTDESCRIPTION="description";
    
    public static final String DIAG_SERVICE_ID ="diagServiceId";
    public static final int CASE_TITLE_MAX_LENGTH = 78;
    public static final String ALPHA_COMMA = "^[A-Za-z,\\s]+$";
    public static final String CUST_VERSION_NOT_EXIST="CUST_VERSION_NOT_EXIST";
    public static final String RECOMM_NOT_MATCH_CONFIG="RECOMM_NOT_MATCH_CONFIG";
    public static final String LOCO_ID="locoId";
    public static final String USER_PAGE ="users";
    public static final String SUBCRIPTION_PAGE ="subscription";

    public static final String AR_CURR_OBJID = "currarListObjId";       
    public static final String RNH_ID="rnhId";
    public static final String RN_ID="rnId";
    public static final String OBJ_ID="objId";
    public static final String VALID="VALID";
    public static final String DEGRADED="Degraded";
    public static final String NORMAL="Normal";
    public static final String MAINTENANCE="Maintenance";
    public static final String PP_DEGRADED="D";
    public static final String PP_NORMAL="Norm";
    public static final String PP_MAINTENANCE="Main";
    public static final String FR="F/R";
    public static final String PP_CURRENT_STATUS="allrnscurrentstatus";
    public static final String PP_SORT_BY="defaultlc";
    public static final String PP_MULTIPLE_ASSET="MULTIPLE ASSET";
    public static final String PP_SEARCH_CRITERIA=" and upper(org.name) = UPPER('";
    public static final String PP_SEARCH_CRITERIAEND="')";
    public static final String PP_SEARCH_CRITERIAALL="')   AND tsp.serial_no IN ('";
    public static final String PP_ENGINE_ONOFF = "engineonoff";     
    public static final String PP_STATE_CHANGE="statechange";
    public static final String PP_TL8="tl8";
    public static final String PP_TL9="tl9";
    public static final String PP_ECRUM="ecrun";
    public static final String PP_ECISO="eciso";
    public static final String PP_PROXLOADED="proxloaded";
    public static final String PP_SMS_BCCB="sms_bccb";
    public static final String PP_MOVED="moved";
    public static final String PP_RVR="rvr";
    public static final String PP_ACTUAL="actual";
    public static final String PP_IN="in";
    public static final String PP_CONSIST_CAN="Consist Can";
    public static final String PP_HEADING="heading";
    public static final String PP_GPS="nogpscomm";
    public static final String PP_MODEL="modelname";
    public static final String PP_CONDITION="v_condition";
    public static final String PP_CMUSTATUS="cmustatus";
    public static final String PP_BCCB_STATUS="BCCB status";
    
    public static final String NS_FIELD = "NS_FIELD";
    public static final String EXT_RX_CLOSE = "Ext_Rx_Close";
    public static final String EXT_RX_SCORE = "Ext_Rx_Score";
    public static final String SCORE_YES = "Y";
    public static final String SCORE_NO = "N";
    public static final String RX_OBJ_ID="rxObjId";
    public static final String REP_OBJ_ID="repObjId";       
    public static final String FAULT_ORIGIN="faultOrigin";
    public static final String FAULT_SUBID="subId";
    
    public static final String VEH_OBJ_ID="vehObjId";
    public static final String HEALTHCHECK_DEVICE_CUSTOMER="HEALTHCHECK_DEVICE_CUSTOMER";
    public static final String HEALTHCHECK_DEVICE_MDSC="HEALTHCHECK_DEVICE_MDSC";
    public static final String EXTERNAL_HC_REQUEST_TYPE="externalRequestType";
    public static final String INTERNAL_HC_REQUEST_TYPE="internalRequestType";
    public static final String OMD_HC_REQUEST="OMD_HEALTHCHECK_REQUEST";
    public static final String HC_REQUEST="hcRequest";
    public static final String STR_YES="Y";
    public static final String STR_NO="N";
    public static final String MSG_DEF="msgDef";
    public static final String MSG_CODE="msgCode";
    public static final String HC_INTERNAL_MSG_CODE="HC_GE";
    public static final String HC_CUSTOMER_MSG_CODE="HC_CUST";
    public static final String HC_CUSTOMER_AUTO_MSG_CODE="HC_AUTO_GE";
    public static final String OB_REQ_STATUS="reqStatus";
    public static final String OB_REQ_STATUS_OPEN="OPEN";
    public static final String MSG_CODE_EGA_HC_INTERNAL="HC_GE";
    public static final String MSG_CODE_EGA_HC_CUSTOMER="HC_CUST";
    public static final String CASE_FILED_VALUE="caseFieldValue";
    public static final String FIND_CASE_TYPE="caseType";
    public static final String ROAD_HEADER="roadHeader";
    public static final String FIND_CASE_ENDDATE="endDate";
    public static final String FIND_CASE_STARTDATE="startDate";
    public static final String FIND_CASE_ID="ID";
    public static final String FIND_CASE_ID_NUMBER=" ID_NUMBER ";
    public static final String FIND_CASE_Title="Title";
    public static final String FIND_CASE_TITLE="TITLE ";
    public static final String FIND_CASE_Condition="Condition";
    public static final String FIND_CASE_CONDITION="CONDITION ";
    public static final String FIND_CASE_Status="Status";
    public static final String FIND_CASE_STATUS="STATUS ";
    public static final String FIND_CASE_ENDS_WITH="Ends With";
    public static final String FIND_CASE_CONTAINS="Contains";
    public static final String FIND_CASE_IS_EQUAL_TO="Is Equal to";
    public static final String FIND_CASE_IS_NOT_EQUAL_TO="Is Not Equal to";
    public static final String FIND_CASE_STARTS_WITH="Starts With";
    public static final String FIND_CASE_UPPER="UPPER(";
    public static final String RN_STARTS_WITH="Starts With";
    public static final String RN_IS_EQUAL_TO="Is Equal to";
    public static final String FIND_CASE_TABLE_ALIAS="TCD.";
    
    public static final String PINPOINTFLG="PINPOINT";
    public static final String OMD_FINDCASES_RECORD_LIMIT="OMD_FINDCASES_RECORD_LIMIT";
    public static final String HIDE_NOTES_ROLES = "hideNotesRoles";
    public static final String HIDE_ASSETOVERVIEW_NOTES_CREATED_BY_ROLES = "HIDE_ASSETOVERVIEW_NOTES_CREATED_BY_ROLES";
    // Material Usage
    public static final String MATERILA_USAGE_LOOKUP_DAYS ="lookUpDays";
    
    public static final String HEALTHCHECK_DISABLED="Health Check is disabled";
    public static final String EGA_HEALTHCHECK_DISABLED="EGA Health Check is disabled";
    public static final String HEALTHCHECK_CANNOT_PERFORMED="Health Check cannot be performed";
    public static final String EGA_HEALTHCHECK_CANNOT_PERFORMED="Unit is not configured for EGA. Health Check cannot be performed"; 
    
    public static final String MODEL_OBJID="modelObjId";
    public static final String ALL_SUBSCRIPTION= "ALL_SUBSCRIPTION";
    //Add notes and Find Notes Screen
    public static final String FROM_RN = "fromRN";
    public static final String TO_RN = "toRN";
    public static final String UNIT_STICKY_OBJID = "unitStickyObjId";
    public static final String CASE_STICKY_OBJID = "caseStickyObjId";
    public static final String FIND_NOTES_PHONE_NOTE = "Phone Note";
    
    //Vehicle Config
    public static final String MESSAGE_DEF_OBJID ="msgDefObjid";
    public static final String CONFIG_DEF="configDef";
    public static final String STR_QNX="QNX";
    public static final String STR_NT="NT";
    public static final String PROGRAM_ID="programId";
    public static final String INSTANCE_ID="instanceId";
    public static final String NUMBER_THREE_THOUSAND_TWENTY="3020";
    public static final String NUMBER_ONE="1";
    public static final String CONFIG_TEMPLATE_EDP="EDP";
    public static final String CONFIG_TEMPLATE_FFD="FFD";
    public static final String CONFIG_TEMPLATE_SDP="SDP";
    public static final String CONTROLLER_CFG_ACCABCEA="ACCABCEA";
    public static final String CONTROLLER_CFG_ACCABCE="ACCABCE";
    public static final String CONTROLLER_CFG_ACCAX="ACCAX";
    public static final String CONTROLLER_CFG_DCCAB="DCCAB";
    public static final String CONTROLLER_CFG_AC6000="AC6000";
    public static final String CONTROLLER_CFG_ACCCA="ACCCA";
    public static final String CONTROLLER_CFG_DCCCA="DCCCA";
    public static final String CTRL_CONFIG="ctrlConfig";
    public static final String REPAIR_CODE_9001="9001";
    public static final String CASE_TYPE_SOFTWARE_VIGILANCE="Software Vigilance";
    public static final String STR_CLOSED="Closed";
    public static final String CASE_IS_CLOSED="CASE_IS_CLOSED";
    public static final String NOT_CASE_CURRENT_OWNER="NOT_CASE_CURRENT_OWNER";
    public static final String DEVICE_NAME="deviceName";
    //message history
    public static final String MESSAGE_ID_CUSTOMER="messageIdCustomer";
    public static final String MESSAGE_ID_AUTO="messageIdAuto";
    public static final String MSG_CODE_EGA_HC_AUTO="HC_AUTO_GE";
    public static final String MCS_MESSAGE_ID_GE="A3_GE";
    public static final String INTERNAL_EXTERNAL_USERS="Internal and External";
    public static final String QNX_EXTERNAL="qnxExternal";
    public static final String QNX_INTERNAL="qnxInternal";
    public static final String QNX_HC_EXTERNAL="CUSTOMER MANUAL HEALTH CHECK REQUEST";
    public static final String QNX_HC_INTERNAL="GE MANUAL HEALTH CHECK REQUEST";
    public static final String QNX_HC_AUTO="GE AUTO HEALTH CHECK REQUEST";
    public static final String EXTERNAL_USERS="External";
    public static final String MESSAGE_ID_LIST="messageIdList";
    public static final String REQUEST_PENDING_FOR_TEMPLATE="REQUEST_PENDING_FOR_TEMPLATE";
    public static final String REQUEST_PENDING_IS_NULL="REQUEST_PENDING_IS_NULL";
    public static final String OTHERS_STRING="OTHERS";
    //ip address validation
    public static final String IP_ADDRESS_VALIDATION_REGEX="^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    //Added by Mohamed - Dispatch queue
    public static final String DISPATCH_IT_QUEUE="IT Queue";
    public static final String TILDA="~";
    
    //message history changes
    public static final String INTERNAL_AUTO_REQUESTS="Internal and Auto";
    public static final String INTERNAL_REQUESTS="Internal";
    public static final String EXTERNAL_AUTO_REQUESTS="External and Auto";
    public static final String QNX_AUTO="qnxAuto";

    //Added by Vamshi for Notification Screen
    public static final String ORG_OBJID="orgObjId";
    public static final String CFG_PARAM_VALUE="cfgParamValue";
    public static final String NOTIFY_FLAG_VALUE="notifyFlagValue";
    public static final String THRESHOLD_VALUE="thresholdValue";
    public static final String COLUMN_OBJID="OBJID";
    public static final String COLUMN_NOTIFY_CFG2BUS_ORG="NOTIFY_CFG2BUS_ORG";
    public static final String COLUMN_NOTIFY_CFG2MODEL="NOTIFY_CFG2MODEL";
    public static final String COLUMN_NAME="NAME";
    public static final String COLUMN_MODEL_NAME="MODEL_NAME";
    
    // Config Geofence screen by Murali
    public static final String STATUS_ACTIVE = "Active";
    public static final String STATUS_INACTIVE = "InActive";
    public static final String STATUS_BOTH = "Both";
    public static final String PROXIMITY_PARENT = "proxParent";
    public static final String PROXIMITY_ID = "proxId";
    public static final String PROXIMITY_DESC = "proxDesc";
    public static final String MILEPOST_ID = "milePostId";
    public static final String MILEPOST_DESC = "milePostDesc";
    public static final String SUB_REGION = "subRegion";
    
    //POP UP LIST ADMIN 
    public static final String POUP_SEARCHBY = "searchBy";
    public static final String POUP_CONDITION = "Condition";
    public static final String POUP_VALUE = "value";
    public static final String POPUP_LIST_ADMIN_STARTS_WITH="Starts With";
    public static final String POPUP_LIST_ADMIN_ENDS_WITH="Ends With";
    public static final String POPUP_LIST_ADMIN_CONTAINS="Contains";
    public static final String POPUP_LIST_ADMIN_IS_EQUAL_TO="Is Equal to";
    public static final String POPUP_LIST_ADMIN_UPPER="UPPER(";
    public static final String POPUP_LISTNAME="List Name";
    public static final String POPUP_LIST_NAME="LIST_NAME";
    public static final String POPUP_FILED_VALUE="popupFieldValue";
    public static final String POPUP_DESCRIPTION="Description";
    public static final String POP_UP_DESCRIPTION="LIST_DESCRIPTION ";
    public static final String UNIQUE_RECORD = "uniquerecord";
    public static final String OLD_LIST_NAME = "oldListName";
    
    //Find/Add notes changes
    public static final String NOTES_DESC="notesDesc";
    public static final String BUS_ORG_ID="busOrgId";
    public static final String OMD_PPASSETHISTORY_RECORD_LIMIT="OMD_PPASSETHISTORY_RECORD_LIMIT";
    public static final String UN_DEFINED = "undefined";
    
    /*Added By Amuthan For CBR*/        
    public static final String RX_TITLE="rxTitle";
    public static final String CBR_ACTIVE_FLAG = "activeFlag";
    public static final String RX_COMMENT = "rxComment";
    public static final String SIMON_RX_EXCLUDE = "SimonRxExclude";
    public static final String CBR_STATUS="CBR_STATUS";
    public static final String CBR_DEACTIVE="Inactive";
    public static final String STR_ZERO="0";
    public static final String STR_ONE="1";
    
    /*Added By Amuthan for And Forward*/
    public static final String AND_FORWARD="AND_FRWD";
    
    //GPOC VOTES
    public static final String NOT_YET_VOTED="NOT_YET_VOTED";
    public static final String CASE_CLOSED="CASE_CLOSED";
    public static final String INBOUND_REVIEW="Inbound Review";
    
    //Repair Facility Maintenance
    public static final String ALL_SITES = "All Sites";
    public static final String SITE = "site";
    public static final String USER_NAME = "userName";
    public static final String RAILWAY_DESIG_CODE = "railWayDesigCode";
    public static final String LOCATION_CODE = "locationCode";
    public static final String REPAIR_LOCATION = "repairLocation";
    public static final String SITE_OBJ_ID = "siteObjId";
    public static final String ACTIVE_REPAIR_RAILWAY_CODE="0";
    public static final String RAILWAY_CODE_ACTIVE = "Active";
    public static final String RAILWAY_CODE_INACTIVE = "InActive";

    //Smart Shop
    public static final String FINAL_REPCODE_OBJID_LIST="finalRepCodeObjIdList";
    //Repair code maintenance
    public static final String SELECT_BY="selectBy";
    public static final String MODELS="models";
    public static final String VALUE="value";
    public static final String REPAIRCODE="repairCode";
    public static final String REPAIR_CODE_DESC="repairCodeDesc";
    public static final String REPAIR_CODE_DETAILS_ALREADY_EXISTS="REPAIR CODE/DESC ALREADY EXISTS";
    public static final String REPAIR_CODE_MAINTENANCE="REPAIR_CODE_MAINTENANCE";
    public static final String CONFIG_ID="configId";
    public static final String ALREADY_PRESENT="duplicate";
    public static final String C_SQL_VALUE="c_sql_value";
    
    //Added for Config Maintenance
    public static final String FFD_CFG_FILE = "FFD";
    public static final String FRD_CFG_FILE = "FRD";
    public static final String EDP_CFG_FILE = "EDP";
    public static final String CTRL_CFG_OBJID = "ctrlCfgObjId";
    public static final String CFG_FILE_NAME = "cfgFileName";
    public static final String DCCAB = "DCCAB";
    public static final String ACCABCE="ACCABCE";
    public static final String ACCABCEA="ACCABCEA";
    public static final String ACCAX="ACCAX";
    public static final String OPERATOR_FROM="operatorFrom";
    public static final String OPERATOR_TO="operatorTo";
    public static final String PARAMETER_OBJID="parameterObjId";
    public static final String CONJUNCTION="conjunction";
    public static final String DEF_OBJID="defObjId";
    public static final String CONFIG_VALUE="configValue";
    public static final String SOURCE_ID="sourceId";
    public static final String CTRL_SOURCE_ID="ctrlSourceId";
    public static final String FAULT_CODE_FROM="faultCodeFrom";
    public static final String FAULT_CODE_START="faultCodeStart";
    public static final String FAULT_CODE_TO="faultCodeTo";
    public static final String FAULT_CODE_END="faultCodeEnd";
    public static final String EDP_TEMPLATE="edpTemplate";
    public static final String EDP_VERSION="edpVersion";
    public static final String PRE_VALUE="preValue";
    public static final String POST_VALUE="postValue";
    public static final String BIAS_VALUE="biasValue";
    public static final String SUB_ID_FROM="subIdFrom";
    public static final String SUB_ID_TO="subIdTo";
    public static final String TEMPLATE_NO = "templateNo";
    public static final String PARAMETER = "PARAMETER";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String SEARCH_VAL = "searchVal";
    public static final String TEMP_OBJ_ID = "tempObjId";
    public static final String PARAM_OBJ_ID = "paramObjId";
    public static final String RECORD_EXIST = "RECORD EXISTS";
    public static final String WIP = "WIP";
    public static final String WORK_IN_PROGRESS = "Work In Progress";
    public static final String VERSION_NO = "versionNo";
    public static final String REMOVE_ALL = "REMOVE ALL";
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String EC2 = "EC2";
    
    //delete configuration
    public static final String CNFG_FRD="FRD";
    public static final String CNFG_FFD="FFD";
    public static final String CNFG_EDP="EDP";
    public static final String CNFG_AHC="AHC";
    public static final String CNTRL_CNFG_OBJID="cntrlCnfgObjId";
    public static final String CONFIG_FILE="cnfgFile";
    
    //Locovision Application ID
    public static final String LDVR_MCS_APPLICATION_ID ="83";

    
    
    public static final String  CUSTOMERSTR="Customer";
    
    public static final String DOLLAR_SEPARATOR="$";

    //MassApply Config
    public static final String STRING_MENU="MENU";
    public static final String CFG_OBJID="cfgObjId";
    public static final String SELECTED_LIST="selectedList";
    public static final String MESSAGE_DEF_EXCEPTION="Exception while retriveing MessageDef Objid";
    public static final String APPLY="Apply";
    public static final String FAULT_EXTENDED_DATAPACK_DEFINITON="FAULT EXTENDED DATAPACK DEFINITON";
    public static final String EXTENDED_DATAPACK_FAULT_RANGE_DEFINITION_UPLOADED="EXTENDED DATAPACK FAULT RANGE DEFINITION UPLOADED";
    public static final String FAULT_FILTER_DEFINITION_UPLOADED="FAULT FILTER DEFINITION UPLOADED"; 
    public static final String CONFIG_TEMPLATE_FRD="FRD";
    public static final String RNH="rnh";
    public static final String SOFTWARE_VERSION="swVersion";
    public static final String MDSCSUP="MDSCSUP";
    public static final String FORWARD_SLASH_T_COMMA="/T:";
    public static final String FORWARD_SLASH_V_COMMA="/V:";
    public static final String UPGRADE_TO_VERSION="upGradeToVersion";
    public static final String NO_DATA_FOUND="NO_DATA_FOUND";
    public static final String EFI_APPLY_BY_RNH="efiApplyByRnh";
    public static final String EFI_APPLY_UPDATE_VERSION="efiApplyUpdateVersion";
    
    //Added for LocoVision
    public static final String ddMMyyyyHHmmss="dd/MM/yyyy HH:mm:ss";
    public static final String MMddyyyyhhmma="MM-dd-yyyy hh:mm a";
    public static final String yyyyMMddHHmmssSSS="yyyy-MM-dd HH:mm:ss.SSS";
    public static final String MMddyyyyhhmmssa = "MM-dd-yyyy hh:mm:ss a";
    public static final String ddMMyyyyhhmma="dd-MM-yyyy hh:mm a";
    


	public static final String PREFERENCE_MEASUREMENT_SYSTEM = "Measurement System";
	public static final String US = "US";
	public static final String DCLOCO = "DCLOCO";
	public static final String ESCAPE_LIKE = "ESCAPE '/' ";
	
	
	 //Added for MDSC Admin changes
	private static final String[] WIP_BIN_TITLE ={ "Accept", "Working", "Dispatch" };
	

	public static final String UPPER_TITLE = "sTitle";
	public static final String ICON_ID = "iconId";
	public static final String DIALOG_ID = "dailogId";
	public static final String DIALOG_ID_VAL = "375";
	public static final String WIPBIN2USER = "wipBin2User";
	public static final String LOGIN_NAME = "loginName";
	public static final String S_LOGIN_NAME = "sLoginName";
	public static final String CONST_PASSWORD = "1nRuLnTXw+dVbke89d111g==";
	public static final String CS_LIC_TYPE = "csLICType";
	public static final String CQ_LIC_TYPE = "cqLICType";
	public static final String SUBMITTER_IND = "submitterInd";
	public static final String LOCALE = "locale";
	public static final String USER_ACCESS2PRIVCLASS = "userAcc2Priv";
	public static final String USER_ACCESS2PRIVCLASS_CONST = "268435457";		
	public static final String OFFLINE2PRIVCLASS = "offLine2PrivClass";
	public static final String OFFLINE2PRIVCLASS_CONST = "268435482";
	public static final String FIRST_NAME = "firstName";		
	public static final String S_FIRST_NAME = "sFirstName";
	public static final String LAST_NAME = "lastName";
	public static final String S_LAST_NAME = "sLastName";
	public static final String EMAIL_ID = "emailId";
	public static final String FLAG = "flag";
	public static final String LOGIN_ID = "loginId";
	public static final String APPROVER = "approver";
	public static final String PREV_SUBMENU_MULTILINGUAL_RX = "SUBMENU_MULTILINGUAL_RX";
	public static final String T_LETTER_UPPER = "T";
	public static final String A_LETTER_UPPER = "A";
	public static final String ALL_OMD = "ALL_OMD";
	public static final String STR_CASE_ID="caseID";
	//Added for DE49509
	public static final String OPEN_DISPATCH = "Open-Dispatch";
	public static final String PREV_SUBMENU_EMETRICS = "SUBMENU_EMETRICS";
	public static final String EMETRICS_REQUESTOR="emetricsRequestor";
	public static final String ADMIN_USER="Admin";
	public static final String SELECTED_EMETRICS_PREV="selectedEmetricsPrev";
	public static final String EMETRICS_READ_ONLY="EMETRICS_READONLY";
	public static final String EMETRICS_UPDATE_ERROR_MSG="User has multiple roles configured.Please try again!";
	public static final String EXISTING_ROLE_ID="roleID";
	
	
	//MassApply Cfg 
	public static final String REGEX_REMOVE_NULL="(,NULL)|(NULL,)";
	public static final String UNDERSCORE_APPLY="_Apply";
	public static final String TO_BE_INSTALLED="TO BE INSTALLED";
	public static final String CMU="CMU";
	public static final String HPEAP="HPEAP";
	public static final String LCV="LCV";
	public static final String LIG="LIG";
	public static final String EST_TIMEZONE = "Canada/Eastern";
	
	
	// New Enhancements MassApply
	public static final String FROM_VERSION = "fromVersion";
	public static final String MAX_VERSION = "maxVersion";
	public static final String CFG_VERSION = "cfgVersion";
	public static final String FLEET_PLUS_SW_VERSION = "Fleet+swVersion";
	public static final String MODEL_PLUS_SW_VERSION = "Model+swVersion";
	public static final String RNH_PLUS_SW_VERSION = "rnh+swVersion";
	public static final String FLEET_PLUS_UPGRADE_VERSION = "Fleet+upGradeToVersion";
	public static final String MODEL_PLUS_UPGRADE_VERSION = "Model+upGradeToVersion";
	public static final String RNH_PLUS_UPGRADE_VERSION = "rnh+upGradeToVersion";
	public static final String VEH_OBJ_ARR = "GETS_RMD.VEH_OBJ_ARR";
	public static final String CFG_DEF_ARRAY = "GETS_RMD.CFG_DEF_ARRAY";
	
	//Added for Contact Screen
	public static final String SITE_NAME = "siteName";
	public static final String SITE_ID = "siteId";
	public static final String PH_NO = "phNo";
	public static final String CONTACT_OBJID = "contactObjId";
	public static final String SITE_OBJID = "siteObjId";
	public static final String CONTACT_ROLE = "contactRole";
	public static final String SITE_OR_ROLE_EXISTS = "SITE_OR_ROLE_EXISTS";
	public static final String SECONDARY_SITE_ID = "secondarySiteId";
	public static final String ROLE2GBSTELM_VAL = "268435632";
	public static final String ROLE2GBSTELM = "role2GBSTELM";
	public static final String FAX_NO = "faxNo";
	public static final String SALUTATION = "salutation";
	public static final String CELL_PH_NO = "cellPhNo";
	public static final String DAIL_COMM = "dailComm";
	public static final String HOME_PH_NO = "homePhNo";
	public static final String VOICE_MAIL = "voiceMail";
	public static final String ADD_CONTACT = "ADD_CONTACT";
	public static final String CONTACT_EXIST = "CONTACT_EXIST";
	public static final String UPDATE_CONTACT = "UPDATE_CONTACT";
	public static final String PRIMARY_SITE_ID = "primarySiteId";		
	public static final String SITEID = "siteId";
	public static final String ADDRESS = "address";
	public static final String ORG_NAME = "orgName";
	public static final String CUST = "CUST";
	public static final String INTR = "INTR";
	public static final String RSEL = "RSEL";
	public static final String INDV = "INDV";
	public static final String VEND = "VEND";
	public static final String OBSOLETE = "Obsolete";
	public static final String SITE_INACTIVE = "INACTIVE";
	public static final String SITE_TYPE_ID = "siteTypeId";
	public static final String SITE_TYPE = "siteType";
	public static final String SITE_STATUS = "siteStatus";
	public static final String SITE_PHONE = "sitePhone";
	public static final String BUS_ORG = "busOrg";
	public static final String SITE_FAX = "siteFax";
	public static final String SITE_SHIPPING = "siteShipping";
	public static final String SITE_PRIM_ADD_OBJID = "primAddObjId";
	public static final String SITE_BILL_ADD_OBJID = "billAddObjId";
	public static final String SITE_SHIP_ADD_OBJID = "shipAddObjId";		
	public static final String INTERNAL = "Internal";
	public static final String RESELLER = "Reseller";
	public static final String INDIVIDUAL = "Individual";
	public static final String VENDOR = "Vendor";
	public static final String ERROR_INSERT = "failed while inserting customer to the site";
	public static final String ERROR_UPDATE = "failed while updating customer to the site";
	public static final String SITE_EXISTS = "SITE_EXISTS";
	public static final String SITE_AND_ROLE_EXIST = "SITE_AND_ROLE_EXISTS";
	public static final String ROLE_EXISTS = "ROLE_EXISTS";
	public static final String NO_DUPLICATES = "NO_DUPLICATES";
	public static final String SEC_SITE_SCREEN = "SEC_SITE_SCREEN";
	public static final String OLD_SITE_OBJID = "oldSiteObjId";
	public static final String STATE = "state";
	public static final String ZIP_CODE = "zipCode";
	public static final String ADDRESS_OBJID = "addrObjId";
	public static final String COUNTRY = "country";
	public static final String STATE_COUNTRY = "stateCountry";
	public static final String ADD_ADDRESS = "ADD_ADDRESS";
	public static final String UPDATE_ADDRESS = "UPDATE_ADDRESS";
	public static final String ADDRESS_1 = "address1";
	public static final String ADDRESS_2 = "address2";
	public static final String TIME_ZONE = "timeZone";
	public static final int INT_CONST_ONE = 1;
    public static final int INT_CONST_TWO = 2;
	public static final int INT_CONST_THREE = 3;
	public static final int INT_CONST_FOUR = 4;
	public static final int INT_CONST_FIVE = 5;
	public static final String ALPHA_DECIMAL_WITHOUT_MINUS="^[0-9.]+$";
	public static final String ANOM_OBJID = "anomObjid";
	public static final String ESERVICE_ORGID = "eServiceOrgId";
	
	//General Notes and comm notes under turnover
	public static final String GENERAL_NOTES_DESC = "generalnotesdesc";
	public static final String GENERAL_NOTES_SEQID = "generalnotesId";
	public static final String GN_LAST_UPDATED_DATE = "lastUpdatedDate";
	public static final String ENTERED_BY = "enteredBy";
	public static final String GN_ENTERED_BY = "gnenteredBy";
	public static final String ENTERED_DATE = "enteredDate";
	public static final String COMM_NOTES_SEQID = "commnotesId";
	public static final String LOG_MESSAGES_ARR = "GETS_RMD.LOG_MESSAGES_ARR";
	public static final String DELETE_CFG_ERR_MSG1 =" :: Error :: Template ";
	public static final String DELETE_CFG_ERR_MSG2 =" and Version ";
	public static final String DELETE_CFG_ERR_MSG3 =" of config type ";
	public static final String DELETE_CFG_ERR_MSG4 = " of config type ";
	public static final String LAST_LOGIN_FROM = "lastLoginFrom";
	
	public static final String DELETE_ROLE_USER_ID = "userId";
	public static final String DELETE_ROLE_CHANGED_ROLE_ID = "changedRoleId";
	public static final String DELETE_NO_VAL = new String("noVal");
	
	//Added as part of Unit Renumbering
	public static final String UNIT_SERIAL_NUMBER = "serialNo";
	public static final String UNIT_S_SERIAL_NUMBER = "sserialNo";
	public static final String UNIT_OLD_SERIAL_NUMBER = "oldSerialNo";
	public static final String UNIT_VEHICLE_HEADER = "vehicleHeader";
	public static final String UNIT_VEHICLE_NUMBER = "vehicleNo";
	public static final String UNIT_OLD_VEHICLE_NUMBER = "oldVehicleNo";
	public static final String UNIT_ROAD_NUMBER = "roadNo";
	public static final String UNIT_OLD_ROAD_NUMBER = "oldRoadNo";
	public static final String UNIT_CUSTOMER_ID = "customerId";
	public static final String UNIT_CURRENT_VEHICLE_HEADER = "currentVehicleHeader";
	public static final String UNIT_NEW_VEHICLE_HEADER1 = "newVehicleHeader1";
	public static final String UNIT_NEW_VEHICLE_HEADER_CUST = "newVehicleHeaderCust";
	public static final String UNIT_NEW_VEHICLE_HEADER = "newVehicleHeader";
	public static final String UNIT_VEHICLE_NUMBERS = "vehicleNumbers";
	
	//CMU
	public static final String BOM_ACTION="BOM";    
	public static final String CMU_CTRL_CFG_LIST="CMU_CTRL_CFG_LIST";
	
	//Added by Murali C
	public static final String OLD_SSO_ID = "oldSSOId";	
	public static final int STATUS_FLAG = 1;
	public static final String MOBILE = "Mobile";
	
	// Added by Arun to avoid sonar issues
    public static final String SMS_ENG_CTRL_ISOLATED = "SMS_ENG_CTRL_ISOLATED";
    public static final String SMS_ENG_CTRL_RUN = "SMS_ENG_CTRL_RUN";
    public static final String NOT_MOVE_ALARM2NOTIFY_HIST = "NOT_MOVE_ALARM2NOTIFY_HIST";
    public static final String FUEL_ALARM2NOTIFY_HIST = "FUEL_ALARM2NOTIFY_HIST";
    public static final String NOT_MOVE_ALERT2NOTIFY_HIST = "NOT_MOVE_ALERT2NOTIFY_HIST";
    public static final String FUEL_ALERT2NOTIFY_HIST = "FUEL_ALERT2NOTIFY_HIST";
    public static final String DWELL2NOTIFY_HIST = "DWELL2NOTIFY_HIST";
    public static final String GPS_LAT_DISPLAY = "GPS_LAT_DISPLAY";
    public static final String GPS_LON_DISPLAY = "GPS_LON_DISPLAY";
    public static final String GPS_HEADING = "GPS_HEADING";
    public static final String LEAD_TRAIL = "LEAD_TRAIL";
    public static final String SMS_FUEL_PUMP_RELAY = "SMS_FUEL_PUMP_RELAY";
    public static final String VEHICLE_ORIENTATION = "VEHICLE_ORIENTATION";
    public static final String ATS_MESSAGE_REASON = "ATS_MESSAGE_REASON";
    public static final String DIRECTION_FROM_LOCATION = "DIRECTION_FROM_LOCATION";
    public static final String METRIC = "Metric";
    public static final String FUEL_LEVEL = "FUEL_LEVEL";
    public static final String FUEL_ADDED = "FUEL_ADDED";
    public static final String GPS_VELOCITY = "GPS_VELOCITY";
    public static final String NEAREST_LOCATION_MILES = "NEAREST_LOCATION_MILES";
    
    //Added by Murali C
    public static final String RECORD_EXIST_WITH_CURRENT_VALUES = "A record already Exist with current values";
    public static final String EXCEPTION_OCCURED = "Exception occurred:";
    public static final String CUST_TOOLTIP_MAP = "CUST_TOOLTIP_MAP";
    public static final String CUST_HEADER_MAP = "CUST_HEADER_MAP";
    public static final String AURIZON_DD_ROLE ="Aurizon_DD";
    public static final String LIMITED_PARAMS_PRIVILEGE ="DATASCREEN_VVF_LIMITED_CUST_MP";
    public static final String AURIZON_QRNI_HDR ="QRNI";
    public static final String AURIZON_QRNI_UNIT ="6010";
    public static final String DE_ROLE ="Diagnostic Engineer";
    public static final String BASIC_CCOP = "BASIC_CCOP";
    public static final String BASIC_CCOP_ARZN_HDR = "CCOP<BR>(mmH2O)";
    public static final String CCOP = "CCOP";
    public static final String PREV_SUBMENU_FIND_NOTES = "SUBMENU_VIEW_FIND_NOTES";
    public static final String ERC = "ERC";
	public static final String DIAGNOSTIC_SERVICE_ID = "diagServiceId";
    public static final String SCHEDULED_DATE = "scheduledDate";
    
    //Added by Sriram.B for SMS feature
    public static final String OTP_PARAM_1 = "otp_param1";
    public static final String OTP_PARAM_2 = "otp_param2";
    public static final String OTP_PARAM_3 = "otp_param3";
    public static final String AUTO_RETRY_ATTEMPTS = "auto_retry_attempts_param";
    public static final String ATTEMPTS_LEFT = "attempts_left_param";
    public static final String TIME_REMAINING = "time_remaining_in_mins_param";
    
    //Added by Sriram.B for Asset Panel feature
    public static final String ASSET_PANEL_PARAM_1 = "asset_panel_param_1";
    public static final String ASSET_PANEL_PARAM_2 = "asset_panel_param_2";
    public static final String ASSET_PANEL_PARAM_3 = "asset_panel_param_3";
    public static final String ASSET_PANEL_IMAGE_REFRESH_RATE_IN_MILLIS = "ASSET_PANEL_IMAGE_REFRESH_RATE_IN_MILLIS";
    public static final String ASSET_PANEL_GREEN_TO_YELLOW_THRESHOLD_IN_MILLIS = "ASSET_PANEL_GREEN_TO_YELLOW_THRESHOLD_IN_MILLIS";
    public static final String ASSET_PANEL_YELLOW_TO_RED_THRESHOLD_IN_MILLIS = "ASSET_PANEL_YELLOW_TO_RED_THRESHOLD_IN_MILLIS";
    
    //Added by Murali Ch
    public static final String GENERAL_NOTES = "GENERAL_NOTES";
    public static final String COMM_NOTES = "COMM_NOTES";
    public static final String NO_INPUT_SELECTED = "NO_INPUT_SELECTED";
    public static final String VEHICLE_UPPER="VEHICLE";
    public static final String EQUIP_NT="EQUIP-NT";
    
  //Added by Koushik for OHV Reports
    public static final String OHV_VEHICLE_NO = "vehicleNo";
    public static final String OHV_VEHICLE_OBJID = "vehicleObjId";
    public static final String OHV_CUSTOMER_ID = "customerId";
    public static final String OHV_REPORT_DURATION = "duration";
    public static final String OHV_PARAM_ID = "paramNo";
    public static final String OHV_FROM_DATE = "fromDate";
    public static final String OHV_TO_DATE = "toDate";
    public static final String OHV_RX_OPEN = "Open";
    public static final String OHV_RX_RED_URGENCY = "Red";
    public static final String OHV_RX_WHITE_URGENCY = "White";
    public static final String OHV_RX_CLOSED = "Closed";
    public static final String OHV_RX_ALL = "All";
    public static final String OHV_BOM_CUST_MODEL = "Customer Model";
    public static final String OHV_BOM_CONTROLLER = "Controller";
    public static final String OHV_BOM_COM_TYPE = "Com type";
    public static final String PARAM_NAME = "Control Power On,Engine Hours - (Meter),Engine Operating Hours,Engine Idle Time,Truck Parked,Propel Time,Retard Time,Warm Up Mode,No Retard,No Propel,Speed Limit,Inverter 1 Disable,Inverter 2 Disable,Retard Engine Speed Incr 1,Retard Engine Speed Incr 2,Distance Traveled,Body Up,Loads,Dispatch Messages,RP1 Pickup,RP2 Pickup,RP3 Pickup,GF Contactor,Parking Brake,Eng/Alternator Revolutions,Alternator MW-Hrs,Overloads,Pre-Shift Brake Tests, Healthcheck Failed, Healthcheck Success, Faultcheck Success, Faultcheck Failed, HC Files Received, Fuel Saver 2 Enabled, Continuous Retard  ";
    public static final String PARAM_NAME_MP = "Control Power On,Engine Hours - (Meter),Engine Operating Hours,Engine Idle Time,Truck Parked,Propel Time,Retard Time,Warm Up Mode,No Retard,No Propel,Speed Limit,Inverter 1 Disable,Inverter 2 Disable,Retard Engine Speed Incr 1,Retard Engine Speed Incr 2,Distance Traveled,Body Up,Loads,Dispatch Messages,RP1 Pickup,RP2 Pickup,RP3 Pickup,GF Contactor,Parking Brake,Eng/Alternator Revolutions,Alternator MW-Hrs,Overloads,Pre-Shift Brake Tests ";
    public static final String UNIT_NAME = "Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Hrs,Times,Times,Miles,Miles,Number,Number,Times,Times,Times,Times,Times,Number,MWh,Number,Hours ";
    public static final String HC_UNIT_NAME = "Number,Number,Number,Number,Number,Number,Hrs";
    public static final String OHV_FAULT_ID = "faultId";
    public static final String OHV_MINE_LOADS = "Loads";
    public static final String OHV_MINE_OPEN_RX = "RXs Open";
    public static final String OHV_MINE_CLOSED_RX = "RXs Closed";
    public static final String OHV_MINE_ENGINE_OP = "Engine Operating Hrs";
    public static final String OHV_MINE_ENGINE_IDLE = "Engine Idle Time";
    public static final String OHV_MINE_TRUCK_PARKED = "Truck Parked";
    public static final String OHV_MINE_OVERLOAD_FAULTS = "Overload Faults";
    public static final String OHV_MINE_AVG_HP = "Average HP";
    public static final String OHV_MINE_RED = "Red Status Trucks";
    public static final String OHV_MINE_YELLOW = "Yellow Status Trucks";
    public static final String OHV_MINE_RX_CLOSURE_TIME = "Rx Closure Time";
    public static final String OHV_MINE_BLUE = "Blue Status Trucks";
    public static final String OHV_MINE_ID = "mineId";
    public static final String OHV_TRUCK_ID = "truckId";
    public static final String OHV_TRUCK_PARAM_DURATION = "duration";
    
 // Added by Murali Ch for US117241
    public static final String CLOSURE_SCREEN = "CASE_CLOSURE";
    public static final String USERTYPE = "userType";
    
	private RMDCommonConstants() {}
	
 	public static String[] getMonths() {
		return MONTHS;
	}
    
    public static String[] getDefaultCaseOwners() {
		return DEFAULT_CASE_OWNERS;
	}
    
    public static String[] getWipBinTitle() {
		return WIP_BIN_TITLE;
	}
	    
    //Added by Shikha for Alerts
    public static final String ALERT_ID = "alertId";  
    public static final String LATTITUDE = "latitude";
    public static final String LONGITUDE = "longitude"; 
	public static final String ORG_ID = "orgId";
	public static final String ORIGINAL_ID = "originalId";
	public static final String NOTIFICATION_ID = "notificationId"; 
	public static final String CONFIGURABLE = "CONFIGURABLE"; 
	public static final String ATS = "ATS"; 
	public static final String RX = "RX";
	//Added for AHC template Appy
	public static final String AHC = "AHC"; 
	
	  //added for auto hc template user-story
    public static final String AHC_CFG_FILE = "AHC";
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
   
   public static final String GENERAL_RX = "GENERAL_RX";
   
   public static final String MAIL_SERVER_HOST="MAIL_SERVER_HOST";
   public static final String SMTP_SERVER="SMTP_SERVER";
   public static final String RX_CHANGE_SUBMIT_MAIL_SUBJECT="RxCHANGE REQUEST : ";
   public static final String RX_CHANGE_SUBMIT_MAIL_SUBJECT_PENDING="NEW RxCHANGE REQUEST : ";
   public static final String RX_CHANGE_SUBMIT_MAIL_SUBJECT_NOTIFICATION=" NOTIFICATION";
   public static final String RX_CHANGE_STATUS_EMAIL_UPDATE="Updated";	
   public static final String RX_CHANGE_STATUS_PENDING = "Submitted";
   public static final String RX_CHANGE_STATUS_OPEN = "Under Review";
   public static final String RX_CHANGE_STATUS_APPROVED = "Approved/Implemented";
   public static final String RX_CHANGE_STATUS_REJECTED = "Rejected";
   public static final String RX_CHANGE_STATUS_PENDING_REQUESTOR = "Pending Requestor";
   public static final String FROM_DL = "RX_CHANGE_EMAIL_FROM_DL";
   public static final String TO_DL = "RX_CHANGE_EMAIL_TO_DL";
   public static final String RX_CHANGE_SUBMIT_MAIL_SUBJECT_NEW="For more details navigate to Rx->RxChangeRequest page in OMD Portal :- ";   
   public static final String   REQUESTEDBY = "requestedBy";
   public static final String  RIVISIONTYPE = "rivisionType";
   public static final String  ROADNUMBER = "roadNumber";
   public static final String  RXTITLESPECIFIC = "rxTitleSpecific";
   public static final String  RXTITLEGENERAL  = "rxTitleGeneral";
   public static final String  CHANGESSUGGESTED  = "changesSuggested";
   public static final String  RXREQUESTSTATUS = "rxRequestStatus";
   public static final String  REQUESTORNOTES  = "requestorNotes";
   public static final String  FILEDATA = "filedata";
   public static final String  DATASOURCE = "dataSource";
   public static final String MODELOBJID = "modelObjId";
   public static final String RX_CHANGE_FILENAME = "fileName";
   public static final String FILEPATH = "filePath";
   public static final String RX_REQUEST_OWNER = "rxRequestOwner";
   public static final String REQUESTOR_NOTES = "requestorNotes";
   public static final String WHITE_PAPER_PDF_FILE_NAME = "whitePaperPdffileName";
   public static final String WHITE_PAPER_PDF_FILE_PATH = "whitePaperPdffilePath";
   public static final String RX_CHANGE_ADMIN_REQUEST_DATASOURCE = "RxChangeAdminRequest";
   public static final String RX_CHANGE_NEW_RX_REQUIRED ="newRxRequired";
   public static final String RX_CHANGE_MATERIAL_CHANGE_REQUIRED ="materialChangeRequired";
   public static final String RX_CHANGE_TRIGGER_LOGIC_CHANGED="triggerLogicChanged";
   public static final String RX_CHANGE_UNFAMILIAR_SYSTEM_CHANGE="unFamiliarSysChange";
   public static final String RX_CHANGE_NUMBER_OF_RXS_ATTACHMENT_ADDED="noOfRxAttachment";
   public static final String RX_CHANGES_SUMMARY="summaryChanges";
   public static final String RX_CHANGE_REVIEWED_AND_APPROVED_BY ="reviewedApproveFlag";
   public static final String RX_CHANGE_INTERNAL_NOTES="internalNotes";
   public static final String RX_CHANGE_REVIEWER_NOTES="reviewerNotes";
   public static final String RX_CHANGE_TARGET_IMP_DATE="targetImplDate";
   public static final String RX_CHANGE_TYPE_OF_CHANGE="typeOfChange";
   public static final String RX_CHANGE_PROC_OBJ_ID="rxChangeProcObjId";
   public static final String RX_CHANGE_REQ_OBJ_ID="rxChangeReqObjId";
   public static final String RX_CHANGE_SUMMARY = "summaryChanges";
   public static final String RX_CHANGE_ADMIN_PRIVILEGE = "RX_CHANGE_ADMIN_PRIVILEGE";
   public static final String RXCHANGEADMINPRIV= "rxChangeAdminPriv";
   public static final String RX_CHANGE_DATA_SOURCE = "RxChangeReq";
   public static final String RX_CHANGE_REVIEWER_ID = "reviewerId";
   public static final String TYPE_OF_RX_CHANGE = "typeOfRxChange";
   public static final String STATUS_CHANGE = "is changed to";
   public static final String RX_CHANGE_STATUS_PENDING_REQUESTOR_MSG = "Pending with Requestor";
   public static final String ADDITIONAL_REVIEWER = "additionalReviewer";
   public static final String HORN_BITS_0052 = "HORN_BITS_0052";
   public static final String HORN_PRESSURE_SWITCH_0143 = "HORN_PRESSURE_SWITCH_0143";
   public static final String RX_CHANGE_SUBMIT_MAIL_NAIVGATION="Under Rx menu -> RxChangeRequest tab";
   public static final String CONNECTION_URL="CONNECTION_URL";
   //Added by Murali C for Bom changes
   public static final String TOOL_OUT_PUT="TOOLOUTPUT";
   public static final String NOT_A_CM_USER = "NOT_A_CM_USER";
   public static final String CASE_TYPE_RF_TRIGGER = "RF Trigger";
   public static final String DISABLED_RX_LIST="DISABLED_RX_LIST";
   public static final String BOM_MISMATCH_RX_LIST="BOM_MISMATCH_RX_LIST";
   public static final String APPEND_CASE_TYPES="_APPEND_CASE_TYPES";
   
   //Added for RCI Template creation User story
   public static final String RCI_CFG_FILE = "RCI";
   public static final String SUBMENU_ADMINISTRATION_EOAONSITE="SUBMENU_ADMINISTRATION_EOAONSITE";
   
   public static final String CUST_FILTER_MAP = "CUST_FILTER_MAP";
   public static final String TECHNICIAN_CASES = "TECHNICIAN_CASES";
   public static final String RX_CHANGE_PDF_DISTRIBUTION_LIST = "RX_CHANGE_PDF_SENDING_DISTRIBUTION_LIST_ID";
   public static final String RX_CHANGE_SEQ_PREFIX = "RxReq";
   public static final String RX_CHANGE_TRIGGER_LOGIC_NOTE = "RX_CHANGE_TRIGGER_LOGIC_NOTE";
   public static final String DOC_LANGUAGE="docLanguage";   
   public static final String CUSTOMER_OBJ_ID = "custObjId";
   public static final String PDF_RESOURCES_FILE_PATH = "com/ge/trans/eoa/services/common/resources/";
   public static final String PDF_RESOURCES_FILE_PATH_DOT = "./com/ge/trans/eoa/services/common/resources/";
   public static final String LOOKVALUEDESC="lookValueDesc";
   public static final String ALPHA_NUMBER_HYPHON="ALPHA_NUMBER_HYPHON";
   //Added for RCI Apply and Delete
   public static final String FAILED_INSTALLATION = "FAILED INSTALLATION";
   public static final String PENDING_REAPPLY = "PENDING REAPPLY";
   public static final String NOT_FAILED_INSTALLATION = "NOT_FAILED_INSTALLATION";
   public static final String UPGRADED = "UPGRADED";
   public static final String PENDING_DELETION = "PENDING DELETION";
   public static final String CONTROLLER_CONFIG_OBJID="controllerConfigObjid";
   public static final String CONFIG_TYPE="configType";
   public static final String SPACE_AND_SPACE=" AND ";
   public static final String SPACE_UNION_SPACE=" UNION ";
   public static final String TEMPLATE_REPORT_FORMDATA="templateReportSearchData";
   public static final String TEMPLATE_STATUS="templateStatus";
   public static final String TEMPLATE_NAME="templateName";   
   public static final String TEMPLATE_VERSION="templateVersion";
   public static final String RCI_TRANSACTION_INDICATOR="800";
   public static final String RCI_ACTION_TYPE="RCI";
   public static final String RX_CHANGE_STATUS_ID = "rxChangeStatusId";
   public static final String RX_CHANGE_COMMENTS = "comments";
   public static final String RX_CHANGE_STATUS_ESCALATION = "Escalation";
   public static final String RX_CHANGE_REQUEST_ESCALATED = "Request Escalated - Rx Change - ";
   public static final String RX_CHANGE_MAIL_SEND_IN_BCC = "sendMailIdInBCC-";
   public static final String RX_TYPE = "rxType";
   public static final String TITLE_INIT = "Title";
   public static final String REPAIRCODE_INIT="Repair Code";
   public static final String SUB_SYSTEM_INIT = "Subsystems";
   
   //Added for AGT 
   public static final String AGT_GROUP = "AGT: Asset Group Template";
   public static final String AGT_TEMPLATE = "AGT";
   public static final String FAILED_TO_APPLY_AGT_TEMPLATE = "Error :: Failed to Apply selected AGT template";
   public static final String NO_DATA_FOUND_TO_APPLY_AGT = "Error :: No Data Found to Apply AGT";
   public static final String LDVR_PQ = "LDVR PQ";
   
   public static final String THIRTY_DAY_AVG = "THIRTY_DAY_AVG";   
   public static final String RX_TASK_REPAIR_CODE_USER = "user";
   public static final String RX_TASK_REPAIR_CODE_TASK_OBJID = "taskObjId";
   public static final String RX_TASK_REPAIR_CODE_ID = "repairCodeId";
   public static final String ISGOODFDBK = "isGoodFdbk";
   
    public static final String VEH_STATUS_OBJID = "vehStatusObjId";
   public static final String INSTALLED = "INSTALLED";
   public static final String RENOTIFY_NOT_NEEDED = "RENOTIFY_NOT_NEEDED";
   public static final String FAILED_DELETION = "FAILED DELETION";
   public static final String PENDING_INSTALLATION = "PENDING INSTALLATION";
   public static final String NOT_INSTALLED = "NOT INSTALLED";
   public static final String DELETE = "DELETE";
   public static final String APPLY_UPPER = "APPLY";
public static final String TEMPLATE_CONTENT = "templateContent";
public static final String FAULT_CODE_DOES_NOT_EXISTS = "FAULT_CODE_DOES_NOT_EXISTS";
public static final String TEMPLATE_EXISTS = "TEMPLATE_EXISTS";
public static final String LINK_USERS = "linkUsers";
public static final String CUSTOMER_NEWS_ID="customerNewsId";
public static final String READ_NEWS_FLAG="readFlag";
public static final String ACTIVE_NEWS_FLAG="activeFlag";

public static final StringBuilder FETCH_ALERT_SDP_PARAMS1_SELECT = new StringBuilder()
.append("  SELECT DISTINCT CLMNAM.OBJID,CLMNAM.COLUMN_NAME, nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME) paramDBName,  CLMNAM.PARM_TYPE,max(SOURCE_UOM_ID)  ");
public static final StringBuilder FETCH_ALERT_SDP_PARAMS1_FROM = new StringBuilder()
.append("  FROM GETS_TOOL_DPD_COLNAME CLMNAM, GETS_RMD_PARMDEF PARMDEF, GETS_TOOL_CE_PARM_INFO_NEW INFO,TABLE_BUS_ORG ORG");
public static final StringBuilder FETCH_ALERT_SDP_PARAMS1_WHERE = new StringBuilder()
.append("  WHERE PARMDEF.RULE_PARM_DESC(+) = CLMNAM.COLUMN_NAME AND LOWER(CLMNAM.FAMILY)     = LOWER(:FAMILY) AND CLMNAM.PARM_TYPE = 'MP' AND INFO.PARM_INFO2PARMDEF   = PARMDEF.OBJID AND INFO.DS_COL_AVAIL='Y' ");

public static final StringBuilder FETCH_ALERT_SDP_PARAMS2_SELECT = new StringBuilder()
.append("  SELECT DISTINCT COLNAME.OBJID,COLNAME.COLUMN_NAME,COLNAME.DB_COLUMN_NAME PARAMDBNAME,COLNAME.PARM_TYPE,SDP_SOURCE_UOM_ID ");
public static final StringBuilder FETCH_ALERT_SDP_PARAMS2_FROM = new StringBuilder()
.append("  FROM GETS_TOOL_DPD_COLNAME COLNAME, GETS_TOOL_CE_PARM_INFO_NEW INFO, TABLE_BUS_ORG ORG ");
public static final StringBuilder FETCH_ALERT_SDP_PARAMS2_WHERE = new StringBuilder()
.append("  WHERE UPPER(INFO.COLUMN_NAME) = UPPER(COLNAME.COLUMN_NAME)  AND LOWER(COLNAME.FAMILY)     = LOWER(:FAMILY) ")
.append("  AND COLNAME.PARM_TYPE         = 'Standard' AND INFO.DS_COL_AVAIL='Y' ");
public static final StringBuilder FETCH_ALERT_PARAMETERS_WHERE = new StringBuilder()
.append("WHERE PARMDEF.RULE_PARM_DESC(+)=COLNAME.COLUMN_NAME AND COLNAME.PARM_TYPE        = 'MP' AND INFO.PARM_INFO2PARMDEF   = PARMDEF.OBJID AND INFO.DS_COL_AVAIL='Y' ");

}
    
