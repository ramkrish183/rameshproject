/**
 * ============================================================
 * Classification: GE Confidential
 * File : QueryConstants.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.common.constants;
 * Author : Capgemini
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.constants;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;

public final class QueryConstants {

	public static final String EDP_SELECTQUERY_TEMPLATE_REPORT="SELECT cfgdef.objid configObjid,  cfgdef.cfg_type configType,  cfgdef.cfg_def_template configDefTemplate,  cfgdef.cfg_def_version configDefVersion,ctlcfg.CONTROLLER_CFG controllerConfig,  cfgdef.cfg_def2ctl_cfg configDef2ControllerConfig,"
			+ " cfgdef.status templateStatus,  cfgdef.CFG_DEF_DESC configDefDescription,  NULL AS offboardStatus,  NULL AS onboardStatus,  rnhview.vehicle_No,  rnhview.vehicle_Hdr,  rnhview.cust_Name, rnhview.ORG_ID, ctlcfg.objid controllerCfgObjid, NULL AS onboardStatusDate, NULL AS offboardStatusDate "
			+ " FROM GETS_OMI.GETS_OMI_CFG_DEF cfgdef,  GETS_RMD.GETS_RMD_CUST_RNH_RN_V rnhview,  GETS_OMI.gets_omi_cfg_def_mtm_veh mtmdef, GETS_RMD.GETS_RMD_CTL_CFG ctlcfg "
			+ " WHERE mtmdef.mtm2veh    =rnhview.vehicle_objid AND mtmdef.Status       ='ACTIVE' AND cfgdef.objid        =mtmdef.mtm2cfg_def"
			+ " AND cfg_type            ='EDP' AND ctlcfg.OBJID=cfgdef.CFG_DEF2CTL_CFG ";
	
	public static final String RCI_SELECTQUERY_TEMPLATE_REPORT="SELECT cfgdef.objid configObjid,    cfgdef.cfg_type configType ,    cfgdef.cfg_def_template configDefTemplate,    cfgdef.CFG_DEF_VERSION configDefVersion,ctlcfg.CONTROLLER_CFG controllerConfig,    cfgdef.cfg_def2ctl_cfg configDef2ControllerConfig, "
			+ "cfgdef.status templateStatus,    cfgdef.cfg_def_desc configDefDescription,    GOCVS.OFFBOARD_STATUS offboardStatus,    GOCVS.ONBOARD_STATUS onboardStatus,   rnhview.vehicle_No,rnhview.vehicle_Hdr,  rnhview.cust_Name, rnhview.ORG_ID, ctlcfg.objid controllerCfgObjid, NVL2(GOCVS.ONBOARD_STATUS_DATE,to_char(GOCVS.ONBOARD_STATUS_DATE,'MM/DD/YYYY HH24:MI:SS'),GOCVS.ONBOARD_STATUS_DATE) onboardStatusDate, GOCVS.OFFBOARD_STATUS_DATE offboardStatusDate "
			+ "FROM GETS_OMI.GETS_OMI_CFG_DEF cfgdef,    GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS,    gets_rmd_cust_rnh_rn_v rnhview , GETS_RMD.GETS_RMD_CTL_CFG ctlcfg "
			+ "WHERE cfgdef.RCI_FLT_CODE_ID  IS NOT NULL  AND GOCVS.status2cfg_def  =cfgdef.objid  AND cfgdef.cfg_type ='RCI'  AND GOCVS.ONBOARD_STATUS NOT IN ('OBSOLETE') "
			+ "AND rnhview.vehicle_objid  =GOCVS.status2vehicle AND ctlcfg.OBJID        =cfgdef.CFG_DEF2CTL_CFG ";
	
	public static final String AHC_SELECTQUERY_TEMPLATE_REPORT="SELECT cfgdef.objid configObjid, CAST('AHC' AS VARCHAR2(5)) configType, cfgdef.AHC_TEMPLATE configDefTemplate, cfgdef.AHC_VERSION configDefVersion, ctlcfg.CONTROLLER_CFG controllerConfig, cfgdef.AHC_HDR2CTRCFG configDef2ControllerConfig, "
			+ " cfgdef.status templateStatus, cfgdef.AHC_DESC configDefDescription, NULL AS offboardStatus, NULL AS onboardStatus, rnhview.vehicle_No, rnhview.vehicle_Hdr, rnhview.cust_Name, rnhview.ORG_ID, ctlcfg.objid controllerCfgObjid, NULL AS onboardStatusDate, NULL AS offboardStatusDate  "
			+ " FROM GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR cfgdef,  GETS_OMI.GETS_OMI_AUTO_HC_CONFIG ahcconfig,  GETS_RMD.GETS_RMD_CUST_RNH_RN_V rnhview,   GETS_RMD.GETS_RMD_CTL_CFG ctlcfg "
			+ " WHERE ahcconfig.status =('ACTIVE') AND ahcconfig.AUTO_HC_CONFIG2VEHICLE   = rnhview.vehicle_objid AND ahcconfig.AUTO_HC_CONFIG2CONFIG_HDR=cfgdef.objid AND ctlcfg.OBJID        =cfgdef.AHC_HDR2CTRCFG(+) ";
	
	public static final String FFD_SELECTQUERY_TEMPLATE_REPORT=" SELECT cfgdef.objid configObjid,  CAST('FFD' AS VARCHAR2(5)) configType ,  cfgdef.FLT_FILTER_TEMPLATE configDefTemplate,  cfgdef.FLT_FILTER_VERSION configDefVersion,  ctlcfg.CONTROLLER_CFG controllerConfig,  cfgdef.FLT_FILTER_DEF2CTL_CFG configDef2ControllerConfig, "
			+ " cfgdef.status templateStatus,  cfgdef.FLT_FILTER_DESC configDefDescription,  NULL AS offboardStatus, NULL AS onboardStatus,  rnhview.vehicle_No,  rnhview.vehicle_Hdr,  rnhview.cust_Name, rnhview.ORG_ID, ctlcfg.objid controllerCfgObjid, NULL AS onboardStatusDate , NULL AS offboardStatusDate "
			+ " FROM GETS_OMI.GETS_OMI_FLT_FILTER_DEF cfgdef,  GETS_OMI.GETS_OMI_FLT_FLTR_MTM_VEH mtmvehicle,  gets_rmd_cust_rnh_rn_v rnhview,  GETS_RMD.GETS_RMD_CTL_CFG ctlcfg "
			+ " WHERE mtmvehicle.mtm2vehicle=rnhview.vehicle_objid AND cfgdef.objid               =mtmvehicle.MTM2FLT_FILTER_DEF AND mtmvehicle.status              ='ACTIVE' AND ctlcfg.OBJID        =cfgdef.FLT_FILTER_DEF2CTL_CFG ";
	
	public static final String FRD_SELECTQUERY_TEMPLATE_REPORT=" SELECT cfgdef.objid configObjid,  CAST('FRD' AS VARCHAR2(5)) configType ,  cfgdef.FLT_RANGE_DEF_template configDefTemplate,  cfgdef.FLT_RANGE_DEF_version configDefVersion, ctlcfg.CONTROLLER_CFG controllerConfig, cfgdef.flt_range_def2ctl_cfg configDef2ControllerConfig,  "
			+ " cfgdef.status templateStatus, cfgdef.FLT_RANGE_DEF_DESC configDefDescription, NULL AS offboardStatus, NULL AS onboardStatus,  rnhview.vehicle_No,rnhview.vehicle_Hdr,  rnhview.cust_Name, rnhview.ORG_ID, ctlcfg.objid controllerCfgObjid, NULL AS onboardStatusDate, NULL AS offboardStatusDate  "
			+ " FROM GETS_OMI.GETS_OMI_FLT_RANGE_DEF cfgdef,  GETS_OMI.GETS_OMI_FLTRNGDEF_MTM_VEH mtmvehicle,  GETS_RMD.GETS_RMD_CUST_RNH_RN_V rnhview , GETS_RMD.GETS_RMD_CTL_CFG ctlcfg "
			+ " WHERE cfgdef.objid         =mtmvehicle.mtm2flt_rng_def and rnhview.vehicle_objid=mtmvehicle.MTM2VEHICLE AND mtmvehicle.status   ='ACTIVE'  and cfgdef.FLT_RANGE_DEF2CTL_CFG=ctlcfg.OBJID ";
	
	public static final String VEHICLE_BASED_SEARCH=" rnhview.ORG_ID = :orgName and rnhview.VEHICLE_HDR = :vehicleHeader and rnhview.VEHICLE_NO  = :vehicleNo ";
	
	public static final String CONTROLLER_CONFIG_SEARCH=" ctlcfg.OBJID = :controllerConfigObjid ";
	
	public static final String VEHICLE_NO_SEARCH=" rnhview.VEHICLE_NO  = :vehicleNo ";
	public static final String ORG_ID_SEARCH=" rnhview.ORG_ID = :orgName ";	
	public static final String VEHICLE_HEADER_SEARCH=" rnhview.VEHICLE_HDR = :vehicleHeader ";
	
	public static final String TEMPLATE_ID_SEARCH=" configDefTemplate = :templateNo ";	
	public static final String TEMPLATE_NAME_SEARCH=" configDefDescription LIKE ( :templateName ) ";
	public static final String TEMPLATE_VERSION_SEARCH=" configDefVersion = (:templateVersion)	";
	public static final String TEMPLATE_STATUS_SEARCH=" UPPER(templateStatus) = UPPER(:templateStatus) ";
	
	public static final String TEMPLATE_REPORT_SELECT_QUERY=" SELECT configObjid, configType, configDefTemplate, configDefVersion, controllerConfig, configDef2ControllerConfig, "
			+ "  templateStatus, configDefDescription, offboardStatus, onboardStatus, VEHICLE_NO, VEHICLE_HDR, CUST_NAME, ORG_ID, controllerCfgObjid, onboardStatusDate, offboardStatusDate FROM ( ";
	
	public static final String TEMPLATE_REPORT_ORDER_BY_CLAUSE=" ORDER BY to_date(onboardStatusDate,'MM/DD/YYYY HH24:MI:SS') desc NULLS last, configType , configDefTemplate , configDefVersion ";
	
	public static final String WHERE_CLAUSE =" 	WHERE ";
	
	public static final List<String> LIST_CONFIG_FILES = Arrays.asList(
			RMDCommonConstants.RCI_CFG_FILE, RMDCommonConstants.FRD_CFG_FILE,
			RMDCommonConstants.FFD_CFG_FILE, RMDCommonConstants.AHC_CFG_FILE,
			RMDCommonConstants.EDP_CFG_FILE);
	
	public static final Map<String,String> TEMPLATE_QUERY_MAP=updateMap();	
	public static final Map<String,String> updateMap(){
		Map<String,String> TEMPLATE_QUERY_MAP=new HashMap<String, String>();
		TEMPLATE_QUERY_MAP.put(RMDCommonConstants.RCI_CFG_FILE, QueryConstants.RCI_SELECTQUERY_TEMPLATE_REPORT);
		TEMPLATE_QUERY_MAP.put(RMDCommonConstants.EDP_CFG_FILE, QueryConstants.EDP_SELECTQUERY_TEMPLATE_REPORT);
		TEMPLATE_QUERY_MAP.put(RMDCommonConstants.AHC_CFG_FILE, QueryConstants.AHC_SELECTQUERY_TEMPLATE_REPORT);
		TEMPLATE_QUERY_MAP.put(RMDCommonConstants.FFD_CFG_FILE, QueryConstants.FFD_SELECTQUERY_TEMPLATE_REPORT);
		TEMPLATE_QUERY_MAP.put(RMDCommonConstants.FRD_CFG_FILE, QueryConstants.FRD_SELECTQUERY_TEMPLATE_REPORT);
		return TEMPLATE_QUERY_MAP;
	};
	
	
}
