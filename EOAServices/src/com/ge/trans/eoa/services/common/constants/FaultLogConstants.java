/**
 * 
 */
package com.ge.trans.eoa.services.common.constants;

/*******************************************************************************
 * Title:		FaultLogConstants.java
 * 
 * Description:	
 * 
 * Copyright:   Copyright (c) 2001
 * 
 * Company: 	General Electric(Remote Monitoring and Diagnostics)
 * 
 * @author		:	iGate		
 * @Created Date:	1-Feb-08
 * @version		:	1.0
 * 				
 * Revision History		  :			
 * Last Modification Date :
 * Last Modified By       :
 * Last Modification      :
 * 
 ******************************************************************************/
import java.text.DecimalFormat;

public interface FaultLogConstants {

    public static final String STR_OCCUR_DATE = "OCCUR_DATE";
    public static final String STR_FAULT_RESET_DATE = "FAULT_RESET_DATE";
    public static final String STR_OFFBOARD_LOAD_DATE = "OFFBOARD_LOAD_DATE";
    public static final String STR_CMU_TIME = "CMU_TIME";
    public static final String STR_TOOL_RUN_IND = "ToolRunInd";
    public static final String STR_N = "N";
    public static final String STR_Y = "Y";
    public static final String STR_NOTCH_FLAG = "NOTCH_FLAG";// "notch_flag";
    public static final String STR_RECORD_TYPE = "RECORD_TYPE";// "record_type";
    public static final String STR_FAULT_STRATEGY_OBJID = "FAULT_STRATEGY_OBJID";// "fault_strategy_objid";
    public static final String STR_FAULT_ORIGIN = "FAULT_ORIGIN";// "fault_origin";
    public static final String STR_V_SAMPLE_NO = "V_SAMPLE_NO";// "v_sample_no";
    public static final String STR_PROXIMITY_DESC = "PROXIMITY_DESC";
    public static final String STR_FAULT_OBJID = "FAULT_OBJID";// "fault_objid";
    public static final String STR_FLT_OBJID = "flt_objid";
    public static final String STR_FAULT_LOG_VIEW_NAME = "fault_log_view_name";
    public static final String STR_VEHICLE_OBJID = "vehicle_objid";
    public static final String STR_OBJID = "OBJID";
    public static final String STR_V_PROXIMITY_DESC = "v_proximity_desc";
    public static final String STR_CONTROLLER_SOURCE_ID = "CONTROLLER_SOURCE_ID";// "controller_source_id";
    public static final String STR_PARM_GROUP = "PARM_GROUP";// "parm_group";
    public static final String STR_CNT = "CNT";
    public static final String STR_HEADER_WIDTH = "HEADER_WIDTH";// "header_width";
    public static final String STR_PX = "px";// "header_width";
    public static final String STR_HEADER_HTML = "HEADER_HTML";// "header_html";
    public static final String STR_INFO = "INFO";// "info";
    public static final String STR_PARM_LOAD_COLUMN = "PARM_LOAD_COLUMN";
    public static final String STR_COLUMN_NAME = "COLUMN_NAME";// "column_name";
    public static final String STR_PARM_NUMBER = "PARM_NUMBER";// "parm number";
    public static final String STR_UPPER_BOUND = "UPPER_BOUND";// "upper_bound";
    public static final String STR_LOWER_BOUND = "LOWER_BOUND";// "lower_bound";
    public static final String STR_ANOM_IND = "ANOM_IND";// "anom_ind";
    public static final String STR_ANOM_DISP_NAME = "ANOM_DISP_NAME";// "anom_disp_name";
    public static final String NULL = "null";// "anom_disp_name";
    // public static final String STR_DATA_SUBSTRING = "data_substring";
    public static final String STR_STYLE = "STYLE";
    public static final String STR_DATA_TOOLTIP_FLAG = "DATA_TOOLTIP_FLAG";// "data_tooltip_flag";
    public static final String STR_CASE_CREATION_TIME = "creation_time";
    public static final String STR_CASE_CREATION = "C";
    public static final String STR_CASE_APPENDED = "A";
    public static final String STR_CASE_CREATION_TO_DATE = "T";
    public static final String STR_MAX_LAST_UPDATED_DATE = "last_updated_date";
    public static final String STR_HC = "hc";
    public static final String STR_JDPAD = "jdpad";
    public static final String STR_CBR = "cbr";
    public static final String STR_CRITICAL_FAULT = "criticalfault";
    public static final String STR_MIN_OBJID = "min_objid";
    public static final String STR_MAX_OBJID = "max_objid";
    public static final String STR_DAYS = "days";
    public static final String STR_FAULT_CODE = "FAULT_CODE";
    public static final String STR_TOOLRUN_IND = "ToolRunInd";
    public static final String STR_EX_SNAPSHOT_FLG = "EX_SNAPSHOT_FLG";
    public static final String STR_PROXIMITY_LABEL = "PROXIMITY_LABEL";
    public static final String STR_CONTROLLER_CFG = "CONTROLLER_CFG";// "controller_cfg";
    public static final String STR_OCCUR_TIME = "OCCUR_TIME";
    public static final String STR_LOCO_SPEED = "LOCO_SPEED";
    public static final String STR_EDP_LOCO_SPEED = "STD_EDP_LOC_SPEED";
    public static final String STR_FAULT_RESET_TIME = "FAULT_RESET_TIME";
    public static final String STR_FAULT_CFG = "FAULT2CFG_DEF";
    public static final String STR_A_STATUS = "A_STATUS";
    public static final String STR_B_STATUS = "B_STATUS";
    public static final String STR_L_STATUS = "L_STATUS";
    public static final String STR_M_STATUS = "M_STATUS";
    public static final String STR_O_STATUS = "O_STATUS";
    public static final String STR_S_STATUS = "S_STATUS";
    public static final String STR_V_STATUS = "V_STATUS";
    public static final String STR_W_STATUS = "W_STATUS";
    public static final String STR_EGU = "egu";
    public static final String STR_FAULT_FC_WITH_NULL = "fault_fc_with_null";
    public static final String STR_FAULT_FC_WITHOUT_NULL = "fault_fc_without_null";
    public static final String STR_COUNT_OCCUR_DATE = "count_occur_date";
    public static final String STR_8 = "8";
    public static final String STR_L = "L";
    public static final String STR_H = "H";
    public static final String STR_R = "R";
    public static final String STR_0 = "0";
    public static final String STR_FIXED = "fixed";
    public static final String STR_NBSP = "&nbsp";
    public static final DecimalFormat DEC = new DecimalFormat(".##");
    public static final String STR_FORMAT = "FORMAT";// "format";
    public static final String STR_NOTCH8 = "notch8";
    public static final String STR_HIDE_FILTER = "hidefilters";
    public static final String STR_DEFAULT_LOOKBACK = "default_lookback";
    public static final String STR_DEFAULT_SORT_ORDER = "DEFAULT_SORT_ORDER";
    public static final String STR_PARM_OBJID = "OBJID";// "objid";
    public static final String DPEAB_VIEW = "GETS_MP_DP_EAB_V";// "DPEAB_VIEW";

    /* For Pagination */
    public static final String STR_COUNT = "CNT";
    public static final String STR_TOTAL_RECORD_CNT = "STR_TOTAL_RECORD_CNT";
    public static final String STR_DB_HIT = "Ds_Pagination_DB_Hit";
    public static final String STR_PER_PAGE = "Ds_Pagination_Rec_Per_Page";

    /* End Pagination */

    public static final String STR_YES = "yes";
    public static final String STR_AC6000 = "AC6000";
    public static final String STR_AC = "AC";
    public static final String STR_ACCCA = "ACCCA";
    public static final String STR_DCCCA = "DCCCA";
    public static final String STR_DC = "DC";
    public static final int INT_DEF_PAGINATION_COUNTER = 100;
    public static final String STR_PREVIOUS_BUTTON = "PreviousButton";
    public static final String STR_NEXT_BUTTON = "NextButton";
    public static final String STR_DESTINATION_URL = "/eServiceSupportJSP/EOA/DataScreen/FaultDataDetails.jsp";
    public static final String STR_EXPORT_FILENAME = "dataset.xls";
    public static final String STR_OIL_EXPORT_FILENAME = "OilDataSet.xls";
    public static final String STR_PAGINATION_COUNTER = "200";
    public static final String STR_LOGFILE_PATH = "/DataScreen/performance.log";
    public static final String STR_MODEL = "MODEL_NAME_V";
    public static final String STR_DS_COL_AVAIL = "DS_COL_AVAIL";
    public static final String STR_VVF_COL_AVAIL = "VVF_COL_AVAIL";
    public static final String STR_RR_COL_AVAIL = "RR_COL_AVAIL";
    public static final String STR_IN_COL_AVAIL = "IN_COL_AVAIL";
    public static final String STR_ON = "on";
    public static final String STR_NO_OF_DAYS = "daysselon";
    public static final String STR_DAYS_BETWEEN = "dayscalon";
    public static final String STR_NO_OF_DAYS_TXT = "daystxton";
    public static final String STR_CASE = "case";
    public static final String STR_VVF = "vvf";
    public static final String STR_RAPIDRESPONSE = "rapidresponse";
    public static final String STR_INCIDENT = "incident";
    public static final String STR_GMT = "(6/24)";
    public static final String STR_EOA_PROBLEM = "EOA Problem";
    public static final String STR_ESTP_PROBLEM = "ESTP Problem";
    public static final String STR_SEARCH = "Search";
    public static final String DROPDOWN_SELECT_VALUE = "Select";
    public static final String DROPDOWN_ALL_ID = "All";
    public static final String DROPDOWN_ALL_VALUE = "All";
    public static final String DROPDOWN_SELECT_ID = "Select";
    public static final String DROPDOWN_ALL_AND_SELECT = "ALLandSELECT";

    public static final String STR_VALUE = "value";
    public static final String STR_RR_FAULTS_FOR_LOCO = "rapidresponse_faults_for_loco";
    public static final String STR_RR_RMD_EQUIPMENT = "rapidresponse_faults_for_rmd";
    public static final String STR_LOCO = "Loco";
    public static final String STR_RMD = "RMD";
    public static final String STR_DPEAB = "DPEAB";

    public static final String STR_VVF_FAULTS_FOR_LOCO = "vvf_faults_for_loco";
    public static final String STR_VVF_FAULTS_FOR_RMD = "vvf_faults_for_rmd";
    public static final String STR_VVF_FAULTS_FOR_QNX = "vvf_faults_to_exclude";
    public static final String STR_FAULTS_DHMS = "vvf_faults_for_DHMS";

    // For Oil
    public static final String CUSTOMER_LIST = "customer";
    public static final String STR_FLEET = "Fleet";
    public static final String STR_ROAD_NO = "VEHICLE_NO";
    public static final String STR_ALL = "All";
    public static final String STR_CRITICAL = "Critical";
    public static final String STR_VEHICLE_HEADER = "x_veh_hdr";
    public static final String STR_SERIAL_NO = "serial_no";
    public static final String STR_FLEET_NUMBER = "fleet_number";
    public static final String STR_FLEET_DESC = "fleet_desc";
    // public static final String STR_OCCUR_DATE = "occur_date";
    public static final String STR_TYPE = "TYPE";
    public static final String STR_MODEL_NAME = "MODEL_NAME";
    public static final String STR_HEADER_INFO = "header_info";
    public static final String STR_COMPONENT_NAME = "component_name";
    public static final String STR_OIL_DESTINATION_URL = "/eServiceSupportJSP/EOA/DataScreen/OilDataScreen.jsp";
    public static final String STR_OIL_RULEDETAILS_DESTINATION_URL = "/eServiceSupportJSP/EOA/DataScreen/RuleDetails.jsp";
    public static final String STR_OIL_CMPRULEDETAILS_DESTINATION_URL = "/eServiceSupportJSP/EOA/DataScreen/ComplexRuleDetails.jsp";
    public static final String STR_OIL_CUSTOMER = "customer";
    public static final String STR_RULE_DETAILS = "rule_details";
    public static final String STR_OIL_DEFAULT_LOOKBACK = "lookback";
    public static final String STR_EOA = "EOA";
    public static final String STR_ESERVICES = "ESERVICES";
    public static final String STR_RULE_ID = "ruleid";
    public static final String STR_RULE_TITLE = "title";
    public static final String STR_TOOL = "tool";
    public static final String STR_FAULT_OJID = "flt_objid";
    public static final String STR_FAULT_CNT = "fltcnt";
    public static final String STR_RULE_COLUMN_NAME = "DB_COLUMN_NAME";
    public static final String STR_RULE_FUNCTION = "FCN";
    public static final String STR_RULE_VALUE1 = "VALUE1";
    public static final String STR_RULE_VALUE2 = "VALUE2";
    public static final String STR_CUSTOMER_ID = "PARM_INFO2CUSTOMER";
    public static final String STR_OIL = "Oil";
    public static final String STR_LMLOA_EQ_CAL = "LMOAEQ_CALC";
    public static final String STR_SOOTMETER_CAL = "SOOTMETER_CALC";
    public static final String STR_DB_COLUMN_NAME = "db_column";
    public static final String STR_EXTERNAL_COL_AVAIL = "external_col_avail";
    public static final String STR_INTERNAL_COL_AVAIL = "internal_col_avail";
    public static final String STR_CUSTID_CASE_FIXEDPARAM = "-4";
    public static final String STR_CUSTID_VIEW_FIXEDPARAM = "-1";
    public static final String STR_FUTURE_DATE_1 = "future_oil_date1";
    public static final String STR_FUTURE_DATE_2 = "future_oil_date2";
    public static final String STR_OIL_CHANGE_DATE = "oil_change_date";
    public static final String STR_OIL_CHANGE_RX_DATE = "oil_change_date_rx";
    public static final String STR_HEADER_TOOL_TIP = "header_tool_tip";
    public static final String STR_LMS_LOCO_ID = "lms_loco_id";
    public static final String STR_RULE_DESCRIPTION = "rule_description";
    public static final String STR_TASK_DESCRIPTION = "task_desc";
    public static final String STR_ORG_ID = "org";
    public static final String STR_FLEET_NO = "fleetno";
    public static final String STR_TYPECHECK = "TYPECHECK";

    // For QNX
    public static final String STR_QNX_COL_AVAIL = "QNX_COL_AVAIL";
    // OIL Rxs
    public static final String STR_OIL_RX_LIST_URL = "/eServiceSupportJSP/EOA/DataScreen/OilRxsList.jsp";
    public static final String STR_OIL_CHART_URL = "/eServiceSupportJSP/EOA/DataScreen/OilChart.jsp";

    // For Authentication
    public static final String STR_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String STR_DHMS_CTRL_SRC_ID = "34";

}
