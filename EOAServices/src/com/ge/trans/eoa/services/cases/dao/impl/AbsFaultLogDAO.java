package com.ge.trans.eoa.services.cases.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.cases.service.valueobjects.ControllerModelVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderDetailVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderGroupVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultHeaderVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.FaultRequestVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.SortOrderLkBackDaysVO;
import com.ge.trans.eoa.services.common.constants.FaultLogConstants;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.GetToolDsParminfoServiceVO;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.ToolDsParmGroupServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public abstract class AbsFaultLogDAO extends BaseDAO implements FaultLogConstants {

    private static final long serialVersionUID = 1L;
    private static final String PIXEL = "px";
    private static final String SPACE = "&nbsp;";
    private static final String COMMA = ",";
    Codec ORACLE_CODEC = new OracleCodec();

    public abstract FaultServiceVO getFaultData(FaultRequestVO objFaultRequestVO, ArrayList arlHeaderDetails,
            String strControllerCfg) throws Exception;

    public ControllerModelVO getControllerSrcAndModel(String custID, String rnh, String rn) throws Exception {
    	ControllerModelVO cmVO = null;
        StringBuilder ctrlMdlQuery = new StringBuilder();
        StringBuilder ctrlMdlQuery1 = new StringBuilder();
        Session session = null;
        Query qry = null;
        Query qry1 = null;
        List ctrlMdlList = null;
        List <Object[]> ctrlMdlList1 = null;
        ctrlMdlQuery.append(" SELECT ctrlsrc.CONTROLLER_SOURCE_ID, grcrrv.MODEL_NAME_V, mod.objid, ");
        ctrlMdlQuery.append(" grcrrv.org_id,  grcrrv.vehicle_hdr,  GRCRRV.VEHICLE_NO, GRCC.FAULT_LOG_VIEW_NAME, GRCRRV.vehicle_objid, GRCC.CONTROLLER_CFG ");
        ctrlMdlQuery.append(" FROM gets_rmd_ctl_cfg grcc,  gets_rmd_vehicle grv, ");
        ctrlMdlQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV,  GETS_SD.GETS_SD_CTRLCFG_TO_CTRLSRC CTRLSRC, ");
        ctrlMdlQuery.append(" gets_rmd_model mod WHERE grcc.objid = grv.vehicle2ctl_cfg AND GRV.OBJID = ");
        ctrlMdlQuery.append(" GRCRRV.VEHICLE_OBJID AND GRCC.CONTROLLER_CFG = CTRLSRC.CONTROLLER_CFG AND ");
        ctrlMdlQuery.append(" grcrrv.MODEL_NAME_V = mod.MODEL_NAME");
        ctrlMdlQuery.append(" AND grcrrv.org_id = ? and grcrrv.vehicle_hdr = ? and GRCRRV.VEHICLE_NO = ? ");
        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(ctrlMdlQuery.toString());
            qry.setParameter(0, custID);
            qry.setParameter(1, rnh);
            qry.setParameter(2, rn);
            qry.setFetchSize(5);
            ctrlMdlList = qry.list();
            if (ctrlMdlList != null && !ctrlMdlList.isEmpty()) {
                Object controllerSrcIdModelData[] = (Object[]) ctrlMdlList.get(0);
                cmVO = new ControllerModelVO(controllerSrcIdModelData[0].toString(),
                        controllerSrcIdModelData[1].toString(), controllerSrcIdModelData[2].toString(),
                        controllerSrcIdModelData[6].toString(), controllerSrcIdModelData[7].toString(),
                        controllerSrcIdModelData[8].toString());
            }
            ctrlMdlList = null;
            
            ctrlMdlQuery1.append("SELECT mbom.config_item, ");
            ctrlMdlQuery1.append("       vehcfg.current_version ");
            ctrlMdlQuery1.append("FROM   gets_rmd_vehcfg vehcfg, ");
            ctrlMdlQuery1.append("       gets_rmd_cust_rnh_rn_v loco, ");
            ctrlMdlQuery1.append("       gets_rmd_master_bom mbom, ");
            ctrlMdlQuery1.append("       gets_rmd_vehicle veh ");
            ctrlMdlQuery1.append("WHERE  vehcfg2master_bom = mbom.objid ");
            ctrlMdlQuery1.append("       AND loco.vehicle_objid = veh_cfg2vehicle ");
            ctrlMdlQuery1.append("       AND mbom.bom_status = 'Y' ");
            ctrlMdlQuery1.append("       AND veh.objid = loco.vehicle_objid ");
            ctrlMdlQuery1.append("       AND loco.org_id = ? ");
            ctrlMdlQuery1.append("       AND loco.vehicle_no = ? ");
            ctrlMdlQuery1.append("       AND veh_cfg2vehicle = veh.objid ");
            ctrlMdlQuery1.append("       AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg ");
            ctrlMdlQuery1.append("       AND mbom.config_item IN ( 'CMU', 'HPEAP', 'LCV', 'LIG', 'WTMC' )");
      
                //session = getHibernateSession();
                qry1 = session.createSQLQuery(ctrlMdlQuery1.toString());
                qry1.setParameter(0, custID);
                qry1.setParameter(1, rn);
                qry1.setFetchSize(5);
                ctrlMdlList1 = qry1.list();
                if (ctrlMdlList1 != null && !ctrlMdlList1.isEmpty() && cmVO != null) {
                for (Object[] obj : ctrlMdlList1) {
                   if (RMDCommonUtility.convertObjectToString(obj[0]).equalsIgnoreCase("CMU") && RMDCommonUtility.convertObjectToString(obj[1]) != null && RMDCommonUtility.convertObjectToString(obj[1]).equals("1")){
                	   	cmVO.setPartStatus("INSTALLED/GOOD");
                	} else if (RMDCommonUtility.convertObjectToString(obj[0]).equalsIgnoreCase("CMU") && RMDCommonUtility.convertObjectToString(obj[1]) != null && RMDCommonUtility.convertObjectToString(obj[1]).equals("2") || 
                			   RMDCommonUtility.convertObjectToString(obj[0]).equalsIgnoreCase("HPEAP") && RMDCommonUtility.convertObjectToString(obj[1]) != null && RMDCommonUtility.convertObjectToString(obj[1]).equals("1") || 
                			   RMDCommonUtility.convertObjectToString(obj[0]).equalsIgnoreCase("LCV") && RMDCommonUtility.convertObjectToString(obj[1]) != null && RMDCommonUtility.convertObjectToString(obj[1]).equals("1") || 
                			   RMDCommonUtility.convertObjectToString(obj[0]).equalsIgnoreCase("LIG") && RMDCommonUtility.convertObjectToString(obj[1]) != null && RMDCommonUtility.convertObjectToString(obj[1]).equals("1"))  {
                		cmVO.setPartStatus("QNX INSTALLED");
                	} 
                   } if (cmVO.getPartStatus() == null ){
                	   cmVO.setPartStatus("TO BE INSTALLED");
                   }
                }
                
        } catch (Exception e) {

            FaultLogHelper.error("the exception in the method getControllerSrcAndModel" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getControllerSrcAndModel : " + ex);
            }
        }
        return cmVO;
    }

    // @Cacheable(value = "controllerSrcIdModelAll_cache")
    public HashMap<String, ControllerModelVO> getControllerSrcAndModel() throws Exception {
        StringBuilder ctrlMdlQuery = new StringBuilder();
        HashMap<String, ControllerModelVO> ctrlSrcAndModelDataMap = new HashMap<String, ControllerModelVO>();
        Session session = null;
        Query qry = null;
        List ctrlMdlList = null;
        ctrlMdlQuery.append(" SELECT ctrlsrc.CONTROLLER_SOURCE_ID, grcrrv.MODEL_NAME_V, mod.objid, ");
        ctrlMdlQuery.append(
                " grcrrv.org_id,  grcrrv.vehicle_hdr,  GRCRRV.VEHICLE_NO, GRCC.FAULT_LOG_VIEW_NAME, GRCRRV.vehicle_objid, GRCRRV.part_status, GRCC.CONTROLLER_CFG ");
        ctrlMdlQuery.append(" FROM gets_rmd_ctl_cfg grcc,  gets_rmd_vehicle grv, ");
        ctrlMdlQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV,  GETS_SD.GETS_SD_CTRLCFG_TO_CTRLSRC CTRLSRC, ");
        ctrlMdlQuery.append(" gets_rmd_model mod WHERE grcc.objid = grv.vehicle2ctl_cfg AND GRV.OBJID = ");
        ctrlMdlQuery.append(" GRCRRV.VEHICLE_OBJID AND GRCC.CONTROLLER_CFG = CTRLSRC.CONTROLLER_CFG AND ");
        ctrlMdlQuery.append(" grcrrv.MODEL_NAME_V = mod.MODEL_NAME");
        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(ctrlMdlQuery.toString());
            qry.setFetchSize(1000);
            ctrlMdlList = qry.list();
            ControllerModelVO ControllerSrcModelVO = null;
            if (ctrlMdlList != null && !ctrlMdlList.isEmpty()) {
                for (int i = 0; i < ctrlMdlList.size(); i++) {
                    Object controllerSrcIdModelData[] = (Object[]) ctrlMdlList.get(i);
                    ControllerSrcModelVO = new ControllerModelVO(controllerSrcIdModelData[0].toString(),
                            controllerSrcIdModelData[1].toString(), controllerSrcIdModelData[2].toString(),
                            controllerSrcIdModelData[6].toString(), controllerSrcIdModelData[7].toString(),
                            controllerSrcIdModelData[8].toString(), controllerSrcIdModelData[9].toString());
                    ctrlSrcAndModelDataMap.put(controllerSrcIdModelData[3].toString() + "_"
                            + controllerSrcIdModelData[4].toString() + "_" + controllerSrcIdModelData[5].toString(),
                            ControllerSrcModelVO);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getControllerSrcAndModel" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getControllerSrcAndModel : " + ex);
                throw ex;
            }
        }
        return ctrlSrcAndModelDataMap;
    }

    @Cacheable(value = "DataScreenFiltersAll_cache")
    public HashMap<String, String> getDataScreenFilters() throws Exception {
        StringBuilder filterQuery = new StringBuilder();
        HashMap<String, String> filtersMap = new HashMap<String, String>();
        Session session = null;
        Query qry = null;
        List filterList = null;
        filterQuery.append(" SELECT COLUMN_NAME, DATE_FILTER, NUMERAL_FILTER, STRING_FILTER ");
        filterQuery.append(" FROM GET_CM.GET_CM_DATASCREEN_FILTERCOL ");
        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(filterQuery.toString());
            qry.setFetchSize(50);
            filterList = qry.list();
            if (filterList != null && !filterList.isEmpty()) {
                for (int i = 0; i < filterList.size(); i++) {
                    Object filterData[] = (Object[]) filterList.get(i);
                    String filterType = "";
                    if (filterData[1] != null) {
                        filterType = "Date";
                    } else if (filterData[2] != null) {
                        filterType = "Numeral";
                    } else if (filterData[3] != null) {
                        filterType = "String";
                    }
                    filtersMap.put(filterData[0].toString(), filterType);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getDataScreenFilters" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getDataScreenFilters : " + ex);
                throw ex;
            }
        }
        return filtersMap;
    }

    @Cacheable(value = "custLevelDetailsAll_cache")
    public Map<String,HashMap<String, HashMap<String,String>>> getCustLevelDetails() throws Exception {
        StringBuilder custLvlQuery = new StringBuilder();
        HashMap<String, HashMap<String,String>> custLeveltoolTipMap = new HashMap<String,HashMap<String,String>>();
        HashMap<String, HashMap<String,String>> custLevelHdrMap = new HashMap<String,HashMap<String,String>>();
        HashMap<String, HashMap<String,String>> custLevelFiterMap = new HashMap<String,HashMap<String,String>>();
        Session session = null;
        Query qry = null;
        List custLevelList = null;
        Map<String,HashMap<String, HashMap<String,String>>> hdrToolTipMap= new HashMap<String,HashMap<String, HashMap<String,String>>>();
        custLvlQuery.append(" SELECT bus.org_id, new.ce_param2model, extapp2ce_parm_info, app.cust_tooltip_info, app.cust_header_html, app.format ");
        custLvlQuery.append(" FROM gets_tools.gets_tool_ce_parm_info_extapp app, gets_tool_ce_parm_info_new NEW, ");
        custLvlQuery
                .append(" table_bus_org bus WHERE extapp2bus_org = bus.objid AND app.extapp2ce_parm_info = new.objid ");
        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(custLvlQuery.toString());
            qry.setFetchSize(500);
            custLevelList = qry.list();
            HashMap<String,String> toolTipsMap =null;
            HashMap<String,String> headersMap =null;
            HashMap<String,String> filterMap =null;
            if (custLevelList != null && !custLevelList.isEmpty()) {
                for (int i = 0; i < custLevelList.size(); i++) {
                    Object custLevelDetailsData[] = (Object[]) custLevelList.get(i);
                    String key = custLevelDetailsData[0].toString() + "_" + custLevelDetailsData[1].toString();
                    if (custLeveltoolTipMap.get(key) == null) {                        
                        toolTipsMap = new  HashMap<String,String>();
                        headersMap = new  HashMap<String,String>();
                        filterMap = new  HashMap<String,String>();
                    } else {                      
                        toolTipsMap = custLeveltoolTipMap.get(key);
                        headersMap = custLevelHdrMap.get(key);
                        filterMap = custLevelFiterMap.get(key);
                    }                   
                    
                    toolTipsMap.put(custLevelDetailsData[2].toString(),RMDCommonUtility.convertObjectToString(custLevelDetailsData[3]));                  
                    custLeveltoolTipMap.put(key, toolTipsMap);
                    
                    headersMap.put(custLevelDetailsData[2].toString(),RMDCommonUtility.convertObjectToString(custLevelDetailsData[4]));
                    custLevelHdrMap.put(key, headersMap);
                    
                    filterMap.put(custLevelDetailsData[2].toString(),RMDCommonUtility.convertObjectToString(custLevelDetailsData[5]));
                    custLevelFiterMap.put(key, filterMap);

                }
                
                hdrToolTipMap.put(RMDCommonConstants.CUST_TOOLTIP_MAP, custLeveltoolTipMap);
                hdrToolTipMap.put(RMDCommonConstants.CUST_HEADER_MAP, custLevelHdrMap);
                hdrToolTipMap.put(RMDCommonConstants.CUST_FILTER_MAP, custLevelFiterMap);
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getCustLevelDetails" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getCustLevelDetails : " + ex);
                throw ex;
            }
        }
        return hdrToolTipMap;
    }

    
    @Cacheable(value = "custLevelMobileDetailsAll_cache")
    public HashMap<String, List<String>> getCustLevelMobileDetails() throws Exception {
        StringBuilder custLvlQuery = new StringBuilder();
        HashMap<String, List<String>> custLevelMap = new HashMap<String, List<String>>();
        Session session = null;
        Query qry = null;
        List custLevelList = null;
        custLvlQuery.append(" SELECT bus.org_id,   new.ce_param2model,   extapp2ce_parm_info ");
        custLvlQuery.append(" FROM gets_tools.gets_tool_ce_parm_info_extapp app, gets_tool_ce_parm_info_new NEW, ");
        custLvlQuery
                .append(" table_bus_org bus WHERE extapp2bus_org = bus.objid AND app.extapp2ce_parm_info = new.objid AND app.MOBILE_REQ_FLAG = 'Y' ");
        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(custLvlQuery.toString());
            qry.setFetchSize(500);
            custLevelList = qry.list();
            List<String> paramInfoList = null;
            if (custLevelList != null && !custLevelList.isEmpty()) {
                for (int i = 0; i < custLevelList.size(); i++) {
                    Object custLevelDetailsData[] = (Object[]) custLevelList.get(i);
                    if (custLevelMap.get(
                            custLevelDetailsData[0].toString() + "_" + custLevelDetailsData[1].toString()) == null) {
                        paramInfoList = new ArrayList<String>();
                    } else {
                        paramInfoList = custLevelMap
                                .get(custLevelDetailsData[0].toString() + "_" + custLevelDetailsData[1].toString());
                    }
                    paramInfoList.add(custLevelDetailsData[2].toString());
                    custLevelMap.put(custLevelDetailsData[0].toString() + "_" + custLevelDetailsData[1].toString(),
                            paramInfoList);

                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getCustLevelDetails" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getCustLevelDetails : " + ex);
                throw ex;
            }
        }
        return custLevelMap;
    }
    
    @Cacheable(value = "sortOrderLookbackDaysAll_cache")
    public HashMap<String, SortOrderLkBackDaysVO> getSortOrderLookbackDays() throws Exception {
        StringBuilder srtOrderLkBackQry = new StringBuilder();
        HashMap<String, SortOrderLkBackDaysVO> sortOrderLookbackDaysMap = new HashMap<String, SortOrderLkBackDaysVO>();
        Session session = null;
        Query qry = null;
        List srtLbkList = null;
        srtOrderLkBackQry.append(" SELECT CASE_PROBLEM_TYPE, CONTROLLER_SOURCE_ID, MODEL, DEFAULT_SORT_ORDER, ");
        srtOrderLkBackQry.append(" default_lookback from gets_sd.gets_sd_casetype_to_deflkb ");

        try {
            session = getHibernateSession();
            qry = session.createSQLQuery(srtOrderLkBackQry.toString());
            qry.setFetchSize(100);
            srtLbkList = qry.list();
            SortOrderLkBackDaysVO sortOrderAndLBDaysVO = null;
            if (srtLbkList != null && !srtLbkList.isEmpty()) {
                for (int i = 0; i < srtLbkList.size(); i++) {
                    Object sortOrderLookbackDaysData[] = (Object[]) srtLbkList.get(i);
                    sortOrderAndLBDaysVO = new SortOrderLkBackDaysVO(sortOrderLookbackDaysData[3].toString(),
                            sortOrderLookbackDaysData[4].toString());
                    sortOrderLookbackDaysMap.put(sortOrderLookbackDaysData[0].toString() + "_"
                            + sortOrderLookbackDaysData[1].toString() + "_" + sortOrderLookbackDaysData[2].toString(),
                            sortOrderAndLBDaysVO);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getSortOrderLookbackDays" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for getSortOrderLookbackDays : " + ex);
                throw ex;
            }
        }
        return sortOrderLookbackDaysMap;
    }

    @Cacheable(value = "DataScreenAllHeaders_cache")
    public Map<String, FaultHeaderVO> getHeaderDetails(boolean isCustColEnabled) throws Exception {
        Map<String, FaultHeaderVO> headerMap = new HashMap<String, FaultHeaderVO>();
        StringBuilder sbHeaderQuery = new StringBuilder();

        Session session = null;
        Query qry = null;
        List headerList = null;

        ArrayList<FaultHeaderDetailVO> arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
        ArrayList<FaultHeaderGroupVO> arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();
        HashMap<String, FaultHeaderVO> hmHeader = new HashMap<String, FaultHeaderVO>();
        HashSet<String> hsControllerSourceId = new HashSet<String>();
        HashSet<String> hsGroupName = new HashSet<String>();

        FaultHeaderVO objFaultHeaderVO = new FaultHeaderVO();
        FaultHeaderGroupVO objFaultHeaderGroupVO = new FaultHeaderGroupVO();
        FaultHeaderDetailVO objFaultHeaderDetail = new FaultHeaderDetailVO();

        int intcounter = 0;
        int controllerId = 0;
        int newLineFeed = 10;
        char newLineFeedChar = (char) newLineFeed;
        String strGroupName = null;
        String strControllerId = null;
        String strModel = null;
        String strControllerCfg = null;
        String strModelID = null;
        boolean fixedHeaders = false;

        try {
            sbHeaderQuery.append(" SELECT DISTINCT c.objid,a.controller_source_id, a.parm_group, header_html, info, ");
            sbHeaderQuery.append(" sort_order, column_name, a.cnt, parm_load_column,a.ce_param2model model, ");
            sbHeaderQuery
                    .append(" header_width, upper_bound, lower_bound, data_tooltip_flag, controller_cfg, format, ");
            sbHeaderQuery.append(
                    " style,ds_col_avail, vvf_col_avail, rr_col_avail, in_col_avail, qnx_col_avail, dhms_col_avail, ");
            sbHeaderQuery.append(" (SELECT DISTINCT anomaly_indicator ");
            sbHeaderQuery.append(" FROM gets_tool_anom_admin adm ");
            sbHeaderQuery.append(" WHERE adm.parm_number = def.parm_number ");
            sbHeaderQuery.append(" AND adm.anomaly_indicator = 'N' ");
            sbHeaderQuery.append(" AND adm.axis = 'Y') anom_ind, ");
            sbHeaderQuery.append(" (SELECT DISTINCT display_parm_name ");
            sbHeaderQuery.append(" FROM gets_tool_anom_admin adm ");
            sbHeaderQuery.append(" WHERE adm.parm_number = def.parm_number ");
            sbHeaderQuery.append(" AND adm.anomaly_indicator = 'N' ");
            sbHeaderQuery.append(" AND adm.axis = 'Y') anom_disp_name ");
            sbHeaderQuery.append(" FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW c, ");
            sbHeaderQuery.append(" gets_rmd_parmdef def, ");
            sbHeaderQuery.append(" (SELECT   parm_group, controller_source_id, COUNT (*) cnt,ce_param2model ");
            sbHeaderQuery.append(" FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW a ");
            sbHeaderQuery.append(" GROUP BY parm_group, controller_source_id,ce_param2model) a ");
            sbHeaderQuery.append(" WHERE a.parm_group = c.parm_group ");
            sbHeaderQuery.append(" AND a.controller_source_id = c.controller_source_id ");
            sbHeaderQuery.append(" AND a.ce_param2model = c.ce_param2model ");
            sbHeaderQuery.append(" AND def.objid(+) = parm_info2parmdef ");
            sbHeaderQuery.append(" AND def.controller_source_id(+) = c.controller_source_id ");
            if(!isCustColEnabled){
            	sbHeaderQuery.append(" AND c.COLUMN_NAME NOT IN ('OCCUR_DATE_CUSTOM', 'FAULT_RESET_DATE_CUSTOM') ");
            }
            sbHeaderQuery.append(" ORDER BY a.controller_source_id,model,sort_order ");

            session = getHibernateSession();
            qry = session.createSQLQuery(sbHeaderQuery.toString());
            qry.setFetchSize(1000);
            headerList = qry.list();

            if (headerList != null && !headerList.isEmpty()) {
                for (int i = 0; i < headerList.size(); i++) {
                    Object headerData[] = (Object[]) headerList.get(i);

                    intcounter++;

                    controllerId = RMDCommonUtility.convertObjectToInt(headerData[1]);
                    strGroupName = RMDCommonUtility.convertObjectToString(headerData[2]);
                    strControllerCfg = RMDCommonUtility.convertObjectToString(headerData[14]);
                    strModel = RMDCommonUtility.convertObjectToString(headerData[9]);
                    strControllerId = String.valueOf(controllerId);
                    fixedHeaders = false;

                    if (strControllerId.startsWith("-")) {
                        strModel = "Fixed-" + strModel;
                        fixedHeaders = true;
                    }
                    if (!hsControllerSourceId.contains(strModel)) {
                        if (intcounter > 1) {
                            objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);
                            arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                            arlHeaderGroup.add(objFaultHeaderGroupVO);
                            objFaultHeaderVO.setFaultGroups(arlHeaderGroup);
                            arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();

                            headerMap.put(strModelID, objFaultHeaderVO);
                            hsGroupName = new HashSet<String>();
                            intcounter = 1;
                        }
                        objFaultHeaderVO = new FaultHeaderVO();
                        objFaultHeaderVO.setControllerId(controllerId);
                        objFaultHeaderVO.setControllerDesc(strControllerCfg);

                        objFaultHeaderVO.setModel(strModel);
                        hsControllerSourceId.add(strModel);
                    }
                    if (!hsGroupName.contains(strGroupName)) {
                        if (intcounter > 1) {
                            objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);
                            arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                            arlHeaderGroup.add(objFaultHeaderGroupVO);
                        }
                        objFaultHeaderGroupVO = new FaultHeaderGroupVO();
                        objFaultHeaderGroupVO.setGroupName(strGroupName);
                        objFaultHeaderGroupVO.setNoOfHeaders(RMDCommonUtility.convertObjectToInt(headerData[7]));
                        hsGroupName.add(strGroupName);
                    }
                    objFaultHeaderDetail = new FaultHeaderDetailVO();

                    objFaultHeaderDetail.setParmObjid(RMDCommonUtility.convertObjectToString(headerData[0]));
                    objFaultHeaderDetail.setHeaderDesc(RMDCommonUtility.convertObjectToString(headerData[3])
                            .replaceAll(String.valueOf(newLineFeedChar), "<BR>"));
                    objFaultHeaderDetail.setToolTip(RMDCommonUtility.convertObjectToString(headerData[4]));
                    objFaultHeaderDetail.setColumnName(RMDCommonUtility.convertObjectToString(headerData[6]));
                    objFaultHeaderDetail.setParamNumber(RMDCommonUtility.convertObjectToString(headerData[8]));
                    if (objFaultHeaderDetail.getColumnName().trim().equals("FAULT_RESET_MODE")
                            || objFaultHeaderDetail.getColumnName().trim().equals("EX_SNAPSHOT_FLG")) {
                        objFaultHeaderDetail.setHeaderWidth(RMDCommonUtility
                                .convertObjectToString((((BigDecimal) headerData[10]).intValue() + 1) * 6.0));
                        objFaultHeaderDetail.setCharLength(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue() + 1));
                    } else if (objFaultHeaderDetail.getColumnName().trim().equals("event_data")) {
                        objFaultHeaderDetail.setHeaderWidth(RMDCommonUtility
                                .convertObjectToString((((BigDecimal) headerData[10]).intValue() + 3) * 6.0));
                        objFaultHeaderDetail.setCharLength(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue()));
                    } else if (objFaultHeaderDetail.getColumnName().trim().equals("fault_desc")) {
                        objFaultHeaderDetail.setHeaderWidth(RMDCommonUtility
                                .convertObjectToString((((BigDecimal) headerData[10]).intValue() + 4) * 6.0));
                        objFaultHeaderDetail.setCharLength(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue()));
                    } else {
                        objFaultHeaderDetail.setHeaderWidth(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue() * 6.0));
                        objFaultHeaderDetail.setCharLength(RMDCommonUtility.convertObjectToString(headerData[10]));
                    }
                    if (objFaultHeaderDetail.getCharLength().trim().equals("1")) {
                        objFaultHeaderDetail.setHeaderWidth("10.0");
                    }
                    objFaultHeaderDetail.setUpperBound(RMDCommonUtility.convertObjectToString(headerData[11]));
                    objFaultHeaderDetail.setLowerBound(RMDCommonUtility.convertObjectToString(headerData[12]));
                    objFaultHeaderDetail.setDataToolTipFlag(RMDCommonUtility.convertObjectToString(headerData[13]));
                    objFaultHeaderDetail.setFormat(RMDCommonUtility.convertObjectToString(headerData[15]));
                    if (fixedHeaders) {
                        objFaultHeaderDetail.setStyle("fixedHeader");
                    } else if (strGroupName != null && strGroupName.trim().equals("standard/EDP")) {
                        objFaultHeaderDetail
                                .setStyle(RMDCommonUtility.convertObjectToString("e" + headerData[16]).trim());
                    } else {
                        objFaultHeaderDetail.setStyle(RMDCommonUtility.convertObjectToString(headerData[16]).trim());
                    }
                    objFaultHeaderDetail.setDataScreenFlag(RMDCommonUtility.convertObjectToString(headerData[17]));
                    objFaultHeaderDetail.setVvfFlag(RMDCommonUtility.convertObjectToString(headerData[18]));
                    objFaultHeaderDetail.setRapidResponseFlag(RMDCommonUtility.convertObjectToString(headerData[19]));
                    objFaultHeaderDetail.setIncidentFlag(RMDCommonUtility.convertObjectToString(headerData[20]));
                    objFaultHeaderDetail.setQnxColAvail(RMDCommonUtility.convertObjectToString(headerData[21]));
                    objFaultHeaderDetail.setStrDhmsColAvail(RMDCommonUtility.convertObjectToString(headerData[22]));
                    objFaultHeaderDetail.setAnomInd(RMDCommonUtility.convertObjectToString(headerData[23]));
                    objFaultHeaderDetail.setParmName(RMDCommonUtility.convertObjectToString(headerData[24]));

                    arlHeaderDetails.add(objFaultHeaderDetail);
                    strModelID = strModel;
                }
            }
            if (!hmHeader.containsKey(strModelID)) {
                objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);
                arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                arlHeaderGroup.add(objFaultHeaderGroupVO);
                objFaultHeaderVO.setFaultGroups(arlHeaderGroup);
                arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();
                hmHeader.put(strModelID, objFaultHeaderVO);
                hsGroupName = new HashSet<String>();
                intcounter = 1;
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Header Details: " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        return headerMap;
    }

    @Cacheable(value = "DataScreenOilHeaders_cache")
    public Map<String, FaultHeaderVO> getOilHeaderDetails(boolean isCustColEnabled) throws Exception {

        Map<String, FaultHeaderVO> oilHeaderHashMap = new HashMap<String, FaultHeaderVO>();
        StringBuilder sbHeaderQuery = new StringBuilder();

        Session session = null;
        Query qry = null;
        List headerList = null;

        int newLineFeed = 10;
        char newLineFeedChar = (char) newLineFeed;

        FaultHeaderVO objFaultHeaderVO = null;
        FaultHeaderGroupVO objFaultHeaderGroupVO = null;
        FaultHeaderDetailVO objFaultHeaderDetail = null;

        ArrayList<FaultHeaderGroupVO> arlHeaderGroup = null;

        ArrayList<FaultHeaderDetailVO> arlHeaderDetails = null;
        HashSet<String> keySet = new HashSet<String>();

        int intcounter = 0;
        String strKey = null;
        try {
            sbHeaderQuery.append(
                    " SELECT info.header_info, NULL component_name, NULL customer, parm_group, sort_order, PARM_INFO2CUSTOMER, ");
            sbHeaderQuery.append(
                    " header_width, db_column_name db_column, external_col_avail, internal_col_avail, NULL format, ");
            sbHeaderQuery.append(
                    " NULL header_tool_tip, REPLACE(info.header_info,CHR(13),'\n') FROM gets_tools.gets_tool_oil_parm_info info WHERE parm_group = 'FixedParameters' ");
            sbHeaderQuery.append(" and parm_info2customer != '-1' UNION ");
            // sbHeaderQuery.append(" SELECT info.header_info, NULL
            // component_name, NULL customer, parm_group, sort_order,
            // parm_info2customer, ");
            // sbHeaderQuery.append(" header_width, db_column_name db_column,
            // external_col_avail, internal_col_avail, format, ");
            // sbHeaderQuery.append(" header_tool_tip,
            // REPLACE(info.header_info,CHR(13),'\n') FROM
            // gets_tools.gets_tool_oil_parm_info info WHERE parm_group <>
            // 'FixedParameters' ");
            // sbHeaderQuery.append(" AND info.parm_info2component IS NULL UNION
            // ");
            sbHeaderQuery.append(
                    " SELECT info.header_info, comp.component_name, bus.ORG_ID, parm_group, sort_order, PARM_INFO2CUSTOMER, ");
            sbHeaderQuery.append(
                    " header_width, db_column, external_col_avail, internal_col_avail, format, header_tool_tip, REPLACE(info.header_info,CHR(13),'\n') ");
            sbHeaderQuery.append(
                    " FROM gets_tools.gets_tool_oil_parm_info info,  gets_tools.gets_tool_oil_component comp, table_bus_org bus ");
            sbHeaderQuery
                    .append(" WHERE info.parm_info2component = comp.objid AND bus.OBJID = info.PARM_INFO2CUSTOMER ");
            sbHeaderQuery.append(
                    " GROUP BY PARM_INFO2CUSTOMER, info.header_info, comp.component_name, bus.org_id, sort_order, ");
            sbHeaderQuery
                    .append(" parm_group, header_width, db_column, external_col_avail, internal_col_avail, format, ");
            sbHeaderQuery.append(" header_tool_tip ORDER BY parm_info2customer, sort_order ");

            session = getHibernateSession();
            qry = session.createSQLQuery(sbHeaderQuery.toString());
            qry.setFetchSize(500);
            headerList = qry.list();

            if (headerList != null && !headerList.isEmpty()) {
                for (int i = 0; i < headerList.size(); i++) {
                    Object headerData[] = (Object[]) headerList.get(i);

                    objFaultHeaderDetail = new FaultHeaderDetailVO();
                    objFaultHeaderDetail.setHeaderDesc(RMDCommonUtility.convertObjectToString(headerData[12])
                            .replaceAll(String.valueOf(newLineFeedChar), "<BR>"));
                    String parmGroup = RMDCommonUtility.convertObjectToString(headerData[3]);
                    if (parmGroup != null && parmGroup.trim().equals("FixedParameters")) {
                        strKey = parmGroup;
                        objFaultHeaderDetail.setColumnName(RMDCommonUtility.convertObjectToString(headerData[0]));
                        objFaultHeaderDetail.setStyle("fixedHeader");
                    } else {
                        objFaultHeaderDetail.setColumnName(RMDCommonUtility.convertObjectToString(headerData[1]));
                        objFaultHeaderDetail.setStrCustID(RMDCommonUtility.convertObjectToString(headerData[2]));
                        objFaultHeaderDetail.setStyle("Oil");
                        strKey = RMDCommonUtility.convertObjectToString(headerData[2]);
                    }
                    objFaultHeaderDetail.setCharLength(RMDCommonUtility.convertObjectToString(headerData[6]));
                    if (objFaultHeaderDetail.getCharLength().trim().equals("1")) {
                        objFaultHeaderDetail.setHeaderWidth("10.0");
                    } else {
                        objFaultHeaderDetail.setHeaderWidth(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[6]).intValue() * 6.0));
                    }
                    objFaultHeaderDetail.setDBColumnName(RMDCommonUtility.convertObjectToString(headerData[7]));
                    objFaultHeaderDetail.setExternalColAvail(RMDCommonUtility.convertObjectToString(headerData[8]));
                    objFaultHeaderDetail.setInternalColAvail(RMDCommonUtility.convertObjectToString(headerData[9]));
                    objFaultHeaderDetail.setFormat(RMDCommonUtility.convertObjectToString(headerData[10]));
                    objFaultHeaderDetail.setToolTip(RMDCommonUtility.convertObjectToString(headerData[11]));

                    if (!keySet.contains(strKey)) {
                        keySet.add(strKey);

                        arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                        objFaultHeaderGroupVO = new FaultHeaderGroupVO();
                        objFaultHeaderGroupVO.setGroupName(parmGroup);
                        objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);

                        arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();
                        arlHeaderGroup.add(objFaultHeaderGroupVO);
                        objFaultHeaderVO = new FaultHeaderVO();
                        objFaultHeaderVO.setFaultGroups(arlHeaderGroup);

                        oilHeaderHashMap.put(strKey, objFaultHeaderVO);
                    }
                    intcounter++;
                    arlHeaderDetails.add(objFaultHeaderDetail);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Header Details: " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        return oilHeaderHashMap;
    }

    @Cacheable(value = "DataScreenDHMSHeaders_cache")
    public FaultHeaderVO getDHMSHeaderDetails(boolean isCustColEnabled) throws Exception {
        StringBuilder sbHeaderQuery = new StringBuilder();

        Session session = null;
        Query qry = null;
        List headerList = null;

        FaultHeaderVO objFaultHeaderVO = new FaultHeaderVO();
        FaultHeaderGroupVO objFaultHeaderGroupVO = new FaultHeaderGroupVO();
        FaultHeaderDetailVO objFaultHeaderDetail = new FaultHeaderDetailVO();

        ArrayList<FaultHeaderGroupVO> arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();
        objFaultHeaderVO.setFaultGroups(arlHeaderGroup);

        ArrayList<FaultHeaderDetailVO> arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
        HashSet<String> hsGroupName = new HashSet<String>();

        int controllerId = 0;
        int newLineFeed = 10;

        char newLineFeedChar = (char) newLineFeed;
        String strGroupName = null;
        String strControllerCfg = null;
        try {
            sbHeaderQuery.append(" SELECT DISTINCT c.objid,a.controller_source_id, a.parm_group, header_html, info, ");
            sbHeaderQuery.append(" sort_order, column_name, a.cnt, parm_load_column,a.ce_param2model model, ");
            sbHeaderQuery
                    .append(" header_width, upper_bound, lower_bound, data_tooltip_flag, controller_cfg, format, ");
            sbHeaderQuery.append(
                    " style,ds_col_avail, vvf_col_avail, rr_col_avail, in_col_avail, qnx_col_avail, dhms_col_avail, ");
            sbHeaderQuery.append(" (SELECT DISTINCT anomaly_indicator ");
            sbHeaderQuery.append(" FROM gets_tool_anom_admin adm ");
            sbHeaderQuery.append(" WHERE adm.parm_number = def.parm_number ");
            sbHeaderQuery.append(" AND adm.anomaly_indicator = 'N' ");
            sbHeaderQuery.append(" AND adm.axis = 'Y') anom_ind, ");
            sbHeaderQuery.append(" (SELECT DISTINCT display_parm_name ");
            sbHeaderQuery.append(" FROM gets_tool_anom_admin adm ");
            sbHeaderQuery.append(" WHERE adm.parm_number = def.parm_number ");
            sbHeaderQuery.append(" AND adm.anomaly_indicator = 'N' ");
            sbHeaderQuery.append(" AND adm.axis = 'Y') anom_disp_name ");
            sbHeaderQuery.append(" FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW c, ");
            sbHeaderQuery.append(" gets_rmd_parmdef def, ");
            sbHeaderQuery.append(" (SELECT   parm_group, controller_source_id, COUNT (*) cnt,ce_param2model ");
            sbHeaderQuery.append(" FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW a ");
            sbHeaderQuery.append(" GROUP BY parm_group, controller_source_id,ce_param2model) a ");
            sbHeaderQuery.append(" WHERE a.parm_group = c.parm_group ");
            sbHeaderQuery.append(" AND a.controller_source_id = c.controller_source_id ");
            sbHeaderQuery.append(" AND a.ce_param2model = c.ce_param2model ");
            sbHeaderQuery.append(" AND def.objid(+) = parm_info2parmdef ");
            sbHeaderQuery.append(" AND def.controller_source_id(+) = c.controller_source_id ");
            sbHeaderQuery.append(" AND a.controller_source_id in ('-34', '34') ");
            if(!isCustColEnabled){
            	sbHeaderQuery.append(" AND c.COLUMN_NAME NOT IN ('OCCUR_DATE_CUSTOM', 'FAULT_RESET_DATE_CUSTOM') ");
            }
            sbHeaderQuery.append(" ORDER BY a.controller_source_id,model,sort_order ");

            session = getHibernateSession();
            qry = session.createSQLQuery(sbHeaderQuery.toString());
            qry.setFetchSize(1000);
            headerList = qry.list();

            if (headerList != null && !headerList.isEmpty()) {
                for (int i = 0; i < headerList.size(); i++) {
                    Object headerData[] = (Object[]) headerList.get(i);

                    objFaultHeaderDetail = new FaultHeaderDetailVO();
                    objFaultHeaderDetail.setParmObjid(RMDCommonUtility.convertObjectToString(headerData[0]));
                    objFaultHeaderDetail.setHeaderDesc(RMDCommonUtility.convertObjectToString(headerData[3])
                            .replaceAll(String.valueOf(newLineFeedChar), "<BR>"));
                    objFaultHeaderDetail.setToolTip(RMDCommonUtility.convertObjectToString(headerData[4]));
                    objFaultHeaderDetail.setColumnName(RMDCommonUtility.convertObjectToString(headerData[6]));
                    objFaultHeaderDetail.setParamNumber(RMDCommonUtility.convertObjectToString(headerData[8]));
                    objFaultHeaderDetail.setCharLength(RMDCommonUtility.convertObjectToString(headerData[10]));
                    if (objFaultHeaderDetail.getCharLength().trim().equals("1")) {
                        objFaultHeaderDetail.setHeaderWidth("10.0");
                    } else {
                        objFaultHeaderDetail.setHeaderWidth(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue() * 6.0));
                    }
                    objFaultHeaderDetail.setUpperBound(RMDCommonUtility.convertObjectToString(headerData[11]));
                    objFaultHeaderDetail.setLowerBound(RMDCommonUtility.convertObjectToString(headerData[12]));
                    objFaultHeaderDetail.setDataToolTipFlag(RMDCommonUtility.convertObjectToString(headerData[13]));
                    objFaultHeaderDetail.setFormat(RMDCommonUtility.convertObjectToString(headerData[15]));
                    objFaultHeaderDetail.setDataScreenFlag(RMDCommonUtility.convertObjectToString(headerData[17]));
                    objFaultHeaderDetail.setVvfFlag(RMDCommonUtility.convertObjectToString(headerData[18]));
                    objFaultHeaderDetail.setRapidResponseFlag(RMDCommonUtility.convertObjectToString(headerData[19]));
                    objFaultHeaderDetail.setIncidentFlag(RMDCommonUtility.convertObjectToString(headerData[20]));
                    objFaultHeaderDetail.setQnxColAvail(RMDCommonUtility.convertObjectToString(headerData[21]));
                    objFaultHeaderDetail.setStrDhmsColAvail(RMDCommonUtility.convertObjectToString(headerData[22]));
                    objFaultHeaderDetail.setAnomInd(RMDCommonUtility.convertObjectToString(headerData[23]));
                    objFaultHeaderDetail.setParmName(RMDCommonUtility.convertObjectToString(headerData[24]));

                    strGroupName = RMDCommonUtility.convertObjectToString(headerData[2]);
                    if (strGroupName != null && strGroupName.trim().equals("FixedParameters")) {
                        objFaultHeaderDetail.setStyle("fixedHeader");
                    } else {
                        objFaultHeaderDetail.setStyle(RMDCommonUtility.convertObjectToString(headerData[16]));
                    }
                    if (!hsGroupName.contains(strGroupName)) {
                        hsGroupName.add(strGroupName);
                        objFaultHeaderGroupVO = new FaultHeaderGroupVO();
                        objFaultHeaderGroupVO.setGroupName(strGroupName);
                        objFaultHeaderGroupVO.setNoOfHeaders(RMDCommonUtility.convertObjectToInt(headerData[7]));
                        arlHeaderGroup.add(objFaultHeaderGroupVO);
                        arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                        objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);
                    }
                    arlHeaderDetails.add(objFaultHeaderDetail);
                    objFaultHeaderVO.setControllerId(controllerId);
                    objFaultHeaderVO.setControllerDesc(strControllerCfg);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Header Details: " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        return objFaultHeaderVO;
    }

    @Cacheable(value = RMDCommonConstants.CACHE_DPEAB_DATASCREEN_HEADER)
    public FaultHeaderVO getDPEABHeaders(boolean isCustColEnabled) throws Exception {
        StringBuilder sbHeaderQuery = new StringBuilder();

        Session session = null;
        Query qry = null;
        List headerList = null;

        FaultHeaderVO objFaultHeaderVO = new FaultHeaderVO();
        FaultHeaderGroupVO objFaultHeaderGroupVO = new FaultHeaderGroupVO();
        FaultHeaderDetailVO objFaultHeaderDetail = new FaultHeaderDetailVO();

        ArrayList<FaultHeaderGroupVO> arlHeaderGroup = new ArrayList<FaultHeaderGroupVO>();
        objFaultHeaderVO.setFaultGroups(arlHeaderGroup);

        ArrayList<FaultHeaderDetailVO> arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
        HashSet<String> hsGroupName = new HashSet<String>();

        int controllerId = 0;
        int newLineFeed = 10;

        char newLineFeedChar = (char) newLineFeed;
        String strGroupName = null;
        String strControllerCfg = null;
        try {

            sbHeaderQuery.append(" SELECT DISTINCT C.OBJID,A.CONTROLLER_SOURCE_ID, A.PARM_GROUP, HEADER_HTML, INFO, ");
            sbHeaderQuery.append("  SORT_ORDER, COLUMN_NAME, A.CNT, DEF.PARM_LOAD_COLUMN,A.CE_PARAM2MODEL MODEL, ");
            sbHeaderQuery.append(
                    "  HEADER_WIDTH, UPPER_BOUND, LOWER_BOUND, DATA_TOOLTIP_FLAG, CONTROLLER_CFG, FORMAT, STYLE,DS_COL_AVAIL, VVF_COL_AVAIL, RR_COL_AVAIL, IN_COL_AVAIL, QNX_COL_AVAIL, ");
            sbHeaderQuery.append("  (SELECT DISTINCT ANOMALY_INDICATOR ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_IND, ");
            sbHeaderQuery.append("  (SELECT DISTINCT DISPLAY_PARM_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = ");
            sbHeaderQuery.append("  DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_DISP_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_DPEAB C, ");
            sbHeaderQuery.append("  GETS_RMD_PARMDEF DEF, ");
            sbHeaderQuery.append("  (SELECT   PARM_GROUP, CONTROLLER_SOURCE_ID, COUNT (*) CNT,CE_PARAM2MODEL ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_DPEAB A ");
            sbHeaderQuery.append("  GROUP BY PARM_GROUP, CONTROLLER_SOURCE_ID,CE_PARAM2MODEL) A ");
            sbHeaderQuery.append("  WHERE A.PARM_GROUP = C.PARM_GROUP ");
            sbHeaderQuery.append("  AND A.CONTROLLER_SOURCE_ID = C.CONTROLLER_SOURCE_ID ");
            sbHeaderQuery.append("  AND A.CE_PARAM2MODEL = C.CE_PARAM2MODEL ");
            sbHeaderQuery.append("  AND DEF.OBJID(+) = PARM_INFO2PARMDEF ");
            sbHeaderQuery.append("  AND DEF.CONTROLLER_SOURCE_ID(+) = C.CONTROLLER_SOURCE_ID ");
            if(!isCustColEnabled){
            	sbHeaderQuery.append(" AND C.COLUMN_NAME NOT IN ('OCCUR_DATE_CUSTOM', 'FAULT_RESET_DATE_CUSTOM') ");
            }
            sbHeaderQuery.append("  ORDER BY A.CONTROLLER_SOURCE_ID, SORT_ORDER ");

            session = getHibernateSession();
            qry = session.createSQLQuery(sbHeaderQuery.toString());
            qry.setFetchSize(1000);
            headerList = qry.list();

            if (headerList != null && !headerList.isEmpty()) {
                for (int i = 0; i < headerList.size(); i++) {
                    Object headerData[] = (Object[]) headerList.get(i);

                    objFaultHeaderDetail = new FaultHeaderDetailVO();
                    objFaultHeaderDetail.setParmObjid(RMDCommonUtility.convertObjectToString(headerData[0]));
                    objFaultHeaderDetail.setHeaderDesc(RMDCommonUtility.convertObjectToString(headerData[3])
                            .replaceAll(String.valueOf(newLineFeedChar), "<BR>"));
                    objFaultHeaderDetail.setToolTip(RMDCommonUtility.convertObjectToString(headerData[4]));
                    objFaultHeaderDetail.setColumnName(RMDCommonUtility.convertObjectToString(headerData[6]));
                    objFaultHeaderDetail.setParamNumber(RMDCommonUtility.convertObjectToString(headerData[8]));
                    objFaultHeaderDetail.setCharLength(RMDCommonUtility.convertObjectToString(headerData[10]));
                    if (objFaultHeaderDetail.getCharLength().trim().equals("1")) {
                        objFaultHeaderDetail.setHeaderWidth("10.0");
                    } else {
                        objFaultHeaderDetail.setHeaderWidth(
                                RMDCommonUtility.convertObjectToString(((BigDecimal) headerData[10]).intValue() * 6.0));
                    }
                    objFaultHeaderDetail.setUpperBound(RMDCommonUtility.convertObjectToString(headerData[11]));
                    objFaultHeaderDetail.setLowerBound(RMDCommonUtility.convertObjectToString(headerData[12]));
                    objFaultHeaderDetail.setDataToolTipFlag(RMDCommonUtility.convertObjectToString(headerData[13]));
                    objFaultHeaderDetail.setFormat(RMDCommonUtility.convertObjectToString(headerData[15]));
                    objFaultHeaderDetail.setDataScreenFlag(RMDCommonUtility.convertObjectToString(headerData[17]));
                    objFaultHeaderDetail.setVvfFlag(RMDCommonUtility.convertObjectToString(headerData[18]));
                    objFaultHeaderDetail.setRapidResponseFlag(RMDCommonUtility.convertObjectToString(headerData[19]));
                    objFaultHeaderDetail.setIncidentFlag(RMDCommonUtility.convertObjectToString(headerData[20]));
                    objFaultHeaderDetail.setQnxColAvail(RMDCommonUtility.convertObjectToString(headerData[21]));
                    objFaultHeaderDetail.setAnomInd(RMDCommonUtility.convertObjectToString(headerData[22]));
                    objFaultHeaderDetail.setParmName(RMDCommonUtility.convertObjectToString(headerData[23]));

                    strGroupName = RMDCommonUtility.convertObjectToString(headerData[2]);
                    if (strGroupName != null && strGroupName.trim().equals("FixedParameters")) {
                        objFaultHeaderDetail.setStyle("fixedHeader");
                    } else {
                        objFaultHeaderDetail.setStyle(RMDCommonUtility.convertObjectToString(headerData[16]));
                    }
                    if (!hsGroupName.contains(strGroupName)) {
                        hsGroupName.add(strGroupName);
                        objFaultHeaderGroupVO = new FaultHeaderGroupVO();
                        objFaultHeaderGroupVO.setGroupName(strGroupName);
                        objFaultHeaderGroupVO.setNoOfHeaders(RMDCommonUtility.convertObjectToInt(headerData[7]));
                        arlHeaderGroup.add(objFaultHeaderGroupVO);
                        arlHeaderDetails = new ArrayList<FaultHeaderDetailVO>();
                        objFaultHeaderGroupVO.setFaultHeaderDetails(arlHeaderDetails);
                    }
                    arlHeaderDetails.add(objFaultHeaderDetail);
                    objFaultHeaderVO.setControllerId(controllerId);
                    objFaultHeaderVO.setControllerDesc(strControllerCfg);
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Header Details: " + e);
            throw e;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        return objFaultHeaderVO;
    }

    public void getControllerSrcId(FaultRequestVO objFaultRequestVO) throws Exception {
        StringBuilder sbCtrlQuery = new StringBuilder();
        Session session = null;
        Query pstmtCntrl = null;
        List rsCntrl = null;
        String strSerialNo = null;
        String strCustomer = null;
        String strVehicleHeader = null;
        String strCtrlSourceID = null;
        String strModel = null;
        try {
            strSerialNo = objFaultRequestVO.getSerialNo();
            FaultLogHelper.debug("strSerialNo :" + strSerialNo);
            strSerialNo = strSerialNo.trim();
            FaultLogHelper.debug("strSerialNo after trim :" + strSerialNo);

            strCustomer = objFaultRequestVO.getCustomer();
            FaultLogHelper.debug("strCustomer :" + strCustomer);
            strCustomer = strCustomer.trim();
            FaultLogHelper.debug("strCustomer after trim :" + strCustomer);

            strVehicleHeader = objFaultRequestVO.getVehicleHeader();
            FaultLogHelper.debug("strVehicleHeader :" + strVehicleHeader);
            strVehicleHeader = strVehicleHeader.trim();
            FaultLogHelper.debug("strVehicleHeader after trim :" + strVehicleHeader);

            session = getHibernateSession();
            sbCtrlQuery.append(" SELECT CTRLSRC.CONTROLLER_SOURCE_ID,GRCRRV.MODEL_NAME_V ");
            sbCtrlQuery.append(" FROM GETS_RMD_CTL_CFG GRCC, ");
            sbCtrlQuery.append(" GETS_RMD_VEHICLE GRV,");
            sbCtrlQuery.append(" GETS_RMD_CUST_RNH_RN_V GRCRRV, ");
            sbCtrlQuery.append(" GETS_SD.GETS_SD_CTRLCFG_TO_CTRLSRC CTRLSRC ");
            sbCtrlQuery.append(" WHERE GRCC.OBJID = GRV.VEHICLE2CTL_CFG");
            sbCtrlQuery.append(" AND GRV.OBJID = GRCRRV.VEHICLE_OBJID");
            sbCtrlQuery.append(" AND GRCC.CONTROLLER_CFG = CTRLSRC.CONTROLLER_CFG ");
            sbCtrlQuery.append(" AND GRCRRV.ORG_ID =:customer ");
            sbCtrlQuery.append(" AND GRCRRV.VEHICLE_HDR =:vehicleHeader ");
            sbCtrlQuery.append(" AND GRCRRV.VEHICLE_NO =:vehicleNo ");

            pstmtCntrl = session.createSQLQuery(sbCtrlQuery.toString());
            pstmtCntrl.setString(RMDCommonConstants.CUSTOMER, strCustomer);
            pstmtCntrl.setString(RMDCommonConstants.VEHICLE_HEADER, strVehicleHeader.trim());
            pstmtCntrl.setString(RMDCommonConstants.VEHICLE_NO, strSerialNo);

            pstmtCntrl.setFetchSize(10);
            rsCntrl = pstmtCntrl.list();
            FaultLogHelper.debug("get ctrlsrc " + strCustomer + "  " + strVehicleHeader + "  " + strSerialNo);

            if (RMDCommonUtility.isCollectionNotEmpty(rsCntrl)) {
                int index = 0;
                if (index < rsCntrl.size()) {
                    Object data[] = (Object[]) rsCntrl.get(index);
                    strCtrlSourceID = RMDCommonUtility.convertObjectToString(data[0]);
                    strModel = RMDCommonUtility.convertObjectToString(data[1]);
                }
            }
            objFaultRequestVO.setControllerSrcID(strCtrlSourceID);
            FaultLogHelper.debug("strModel" + strModel + " ctrl src " + strCtrlSourceID);
            objFaultRequestVO.setModel(strModel);
        } catch (Exception e) {
            FaultLogHelper.error("Exception in getting the controller source id : " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);

            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing the Connection for Cntrl: " + ex);
                throw ex;
            }
        }

    }

    @Cacheable(value = "dataScreen_headerCache", key = "#assetModelId")
    public HashMap getHeaderDetails(String assetModelId) throws Exception {
        ArrayList arlHeaderGroup = new ArrayList();
        FaultHeaderVO objFaultHeaderVO = new FaultHeaderVO();

        /* Changed for EOA Datascreen story */
        ArrayList arlHeaderList = new ArrayList();
        ToolDsParmGroupServiceVO parmGroupInfo = null;
        HashMap hmHeader = new HashMap();
        Query pstmt = null;
        List rs = null;
        Session session = null;
        HashSet hsGroupName = new HashSet();
        StringBuilder sbHeaderQuery = new StringBuilder();
        int intControllerId = 0;
        String strGroupName = null;
        int intNoOfHeaders = 0;
        String strControllerId = null;

        String strHeaderWidth = null;
        String strToolTip = null;
        String strParmNumber = null;
        String strAnomInd = null;
        String strParmName = null;
        int intModel = 0;
        String strModel = null;
        String strControllerCfg = null;
        String strFormat = null;
        String strDataScreenColAvail = null;
        String strVvfColAvail = null;
        String strRapidRespColAvail = null;
        String strIncidentColAvail = null;
        String strQnxColAvail = null;
        String strParamObjid = null;

        try {
            session = getHibernateSession();
            sbHeaderQuery.append(" SELECT DISTINCT C.OBJID,A.CONTROLLER_SOURCE_ID, A.PARM_GROUP, HEADER_HTML, INFO, ");
            sbHeaderQuery.append(
                    "  SORT_ORDER, COLUMN_NAME, A.CNT, DEF.PARM_LOAD_COLUMN,A.CE_PARAM2MODEL MODEL,DEF.PARM_NUMBER, ");
            sbHeaderQuery.append(
                    "  HEADER_WIDTH, UPPER_BOUND, LOWER_BOUND, DATA_TOOLTIP_FLAG, CONTROLLER_CFG, FORMAT, STYLE,DS_COL_AVAIL, VVF_COL_AVAIL, RR_COL_AVAIL, IN_COL_AVAIL, QNX_COL_AVAIL, ");
            sbHeaderQuery.append("  (SELECT DISTINCT ANOMALY_INDICATOR ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_IND, ");
            sbHeaderQuery.append("  (SELECT DISTINCT DISPLAY_PARM_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = ");
            sbHeaderQuery.append("  DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_DISP_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW C, ");
            sbHeaderQuery.append("  GETS_RMD_PARMDEF DEF, ");
            sbHeaderQuery.append("  (SELECT   PARM_GROUP, CONTROLLER_SOURCE_ID, COUNT (*) CNT,CE_PARAM2MODEL ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_INFO_NEW A ");
            sbHeaderQuery.append("  GROUP BY PARM_GROUP, CONTROLLER_SOURCE_ID,CE_PARAM2MODEL) A ");
            sbHeaderQuery.append("  WHERE A.PARM_GROUP = C.PARM_GROUP ");
            sbHeaderQuery.append("  AND A.CONTROLLER_SOURCE_ID = C.CONTROLLER_SOURCE_ID ");
            sbHeaderQuery.append("  AND A.CE_PARAM2MODEL = C.CE_PARAM2MODEL ");
            sbHeaderQuery.append("  AND DEF.OBJID(+) = PARM_INFO2PARMDEF ");
            sbHeaderQuery.append("  AND DEF.CONTROLLER_SOURCE_ID(+) = C.CONTROLLER_SOURCE_ID ");
            sbHeaderQuery.append(" AND A.CE_PARAM2MODEL =:modelId");
            sbHeaderQuery.append("  ORDER BY   A.CONTROLLER_SOURCE_ID,MODEL,SORT_ORDER ");

            pstmt = session.createSQLQuery(sbHeaderQuery.toString());
            pstmt.setString(RMDCommonConstants.MODELID, ESAPI.encoder().encodeForSQL(ORACLE_CODEC, assetModelId));

            pstmt.setResultTransformer(new AliasToEntityMapResultTransformer());
            pstmt.setFetchSize(300);
            List<Map<String, String>> aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                GetToolDsParminfoServiceVO objParminfoServiceVO;
                int fixedHeaderWidthTotal = 0;
                int variableHeaderWidthTotal = 0;
                int intWidthLen = 0;
                StringBuilder sbfDummyScrollHeader;
                int i = 0;
                for (Map<String, String> objMap : aliasToValueMapList) {

                    sbfDummyScrollHeader = new StringBuilder();
                    intControllerId = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(STR_CONTROLLER_SOURCE_ID));
                    strGroupName = objMap.get(STR_PARM_GROUP);
                    intNoOfHeaders = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(STR_CNT));

                    strControllerCfg = objMap.get(STR_CONTROLLER_CFG);
                    intModel = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(RMDCommonConstants.MODEL_MODEL));
                    strModel = intModel + RMDCommonConstants.EMPTY_STRING;

                    strControllerId = intControllerId + RMDCommonConstants.EMPTY_STRING;

                    if (strControllerId.startsWith(RMDCommonConstants.HYPHEN)) {
                        strModel = RMDCommonConstants.FIXED + strModel;
                    }

                    objParminfoServiceVO = new GetToolDsParminfoServiceVO();

                    objParminfoServiceVO.setNoOfHeaders(String.valueOf(intNoOfHeaders));
                    objParminfoServiceVO.setGroupName(strGroupName);
                    strToolTip = objMap.get(STR_INFO);
                    objParminfoServiceVO.setToolTip(strToolTip);

                    strParmNumber = objMap.get(RMDCommonUtility.convertObjectToString(STR_PARM_LOAD_COLUMN));
                    objParminfoServiceVO.setParamNumber(strParmNumber);

                    strParamObjid = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap.get(STR_PARM_OBJID));
                    objParminfoServiceVO.setParmObjid(strParamObjid);

                    strAnomInd = objMap.get(RMDCommonUtility.convertObjectToString(STR_ANOM_IND));
                    objParminfoServiceVO.setAnomInd(strAnomInd);

                    strParmName = objMap.get(RMDCommonUtility.convertObjectToString(STR_ANOM_DISP_NAME));
                    objParminfoServiceVO.setParmName(strParmName);

                    strDataScreenColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_DS_COL_AVAIL));
                    objParminfoServiceVO.setDataScreenFlag(strDataScreenColAvail);

                    strVvfColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_VVF_COL_AVAIL));
                    objParminfoServiceVO.setVvfFlag(strVvfColAvail);

                    strRapidRespColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_RR_COL_AVAIL));
                    objParminfoServiceVO.setRapidResponseFlag(strRapidRespColAvail);

                    strIncidentColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_IN_COL_AVAIL));
                    objParminfoServiceVO.setIncidentFlag(strIncidentColAvail);

                    strQnxColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_QNX_COL_AVAIL));
                    objParminfoServiceVO.setQnxColAvail(strQnxColAvail);

                    if (null != objMap.get(STR_PARM_NUMBER)) {
                        objParminfoServiceVO.setParmNumber(RMDCommonConstants.MP + objMap.get(STR_PARM_NUMBER));
                    } else {
                        objParminfoServiceVO.setParmNumber(objMap.get(STR_COLUMN_NAME));
                    }
                    objParminfoServiceVO.setColumnName(objMap.get(STR_COLUMN_NAME));
                    objParminfoServiceVO.setHeaderHtml(objMap.get(STR_HEADER_HTML));
                    if (null == objMap.get(STR_STYLE)) {
                        objParminfoServiceVO.setStyle(RMDCommonConstants.FIXED_STYLE);
                    } else {
                        objParminfoServiceVO.setStyle(objMap.get(STR_STYLE));
                    }
                    objParminfoServiceVO.setInfo(objMap.get(STR_INFO));
                    objParminfoServiceVO
                            .setLowerBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_LOWER_BOUND)));
                    objParminfoServiceVO
                            .setUpperBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_UPPER_BOUND)));
                    objParminfoServiceVO.setDataTooltipFlag(objMap.get(STR_DATA_TOOLTIP_FLAG));

                    String strWidth = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap.get(STR_HEADER_WIDTH));
                    intWidthLen = Integer.parseInt(strWidth);
                    int width = 5 * intWidthLen;
                    if (i < 4) {
                        fixedHeaderWidthTotal += intWidthLen;
                        if (i == 1) {
                            intWidthLen = intWidthLen - 20;
                        }
                        for (int index = 0; index < intWidthLen; index++) {
                            sbfDummyScrollHeader.append(SPACE);
                        }
                        fixedHeaderWidthTotal += 20;
                    } else {
                        variableHeaderWidthTotal += intWidthLen;
                        for (int index = 0; index < intWidthLen; index++) {
                            sbfDummyScrollHeader.append(SPACE);
                        }
                    }
                    strFormat = objMap.get(STR_FORMAT);
                    String temp = intWidthLen + PIXEL + COMMA;
                    strHeaderWidth = strHeaderWidth + temp;
                    strWidth = String.valueOf(width);
                    objParminfoServiceVO.setHeaderWidth(strWidth + PIXEL);
                    objParminfoServiceVO.setDummyHeaderHtml(sbfDummyScrollHeader.toString());
                    objParminfoServiceVO.setFormat(strFormat);
                    objParminfoServiceVO
                            .setLowerBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_LOWER_BOUND)));
                    objParminfoServiceVO
                            .setUpperBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_UPPER_BOUND)));
                    objParminfoServiceVO.setFixedHeaderWidthTotal(String.valueOf(fixedHeaderWidthTotal));
                    objParminfoServiceVO.setVariableHeaderWidthTotal(String.valueOf(variableHeaderWidthTotal));
                    objParminfoServiceVO.setStrHeaderWidth(strHeaderWidth);

                    arlHeaderList.add(objParminfoServiceVO);

                    if (!hsGroupName.contains(strGroupName)) {
                        parmGroupInfo = new ToolDsParmGroupServiceVO();
                        parmGroupInfo.setParamGroup(strGroupName);
                        parmGroupInfo.setStyle(objMap.get(STR_STYLE));
                        parmGroupInfo.setColspan(String.valueOf(intNoOfHeaders));
                        arlHeaderGroup.add(parmGroupInfo);
                        hsGroupName.add(strGroupName);
                    }
                    i++;
                }

                objParminfoServiceVO = new GetToolDsParminfoServiceVO();
                for (int k = 0; k < arlHeaderList.size(); k++) {
                    objParminfoServiceVO = (GetToolDsParminfoServiceVO) arlHeaderList.get(k);
                }
                String strFinalHeaderWidth = objParminfoServiceVO.getStrHeaderWidth().substring(0,
                        objParminfoServiceVO.getStrHeaderWidth().length() - 1);

                hmHeader.put(RMDCommonConstants.HEADERDETAIL, arlHeaderList);
                hmHeader.put(RMDCommonConstants.HEADERGRPDETAIL, arlHeaderGroup);
                hmHeader.put(RMDCommonConstants.FINALHEADERWIDTH, strFinalHeaderWidth);
                hmHeader.put(RMDCommonConstants.FIXEDHEADERWIDTH,
                        objParminfoServiceVO.getFixedHeaderWidthTotal() + PIXEL);
                hmHeader.put(RMDCommonConstants.VARIABLEHEADERWIDTH,
                        objParminfoServiceVO.getVariableHeaderWidthTotal() + PIXEL);
                hmHeader.put(RMDCommonConstants.CONTROLLERFLAG, strControllerCfg);
            }

        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the Header Details: " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        FaultLogHelper.debug("End getHeaderDetails :" + System.currentTimeMillis());
        return hmHeader;
    }

    @Cacheable(value = RMDCommonConstants.CACHE_DPEAB_DATASCREEN_HEADER)
    public HashMap getDPEABHeaderDetails() throws Exception {
        ArrayList arlHeaderGroup = new ArrayList();

        /* Changed for EOA Datascreen story */
        ArrayList arlHeaderList = new ArrayList();
        ToolDsParmGroupServiceVO parmGroupInfo = null;
        HashMap hmHeader = new HashMap();
        Query pstmt = null;
        List rs = null;
        Session session = null;
        HashSet hsGroupName = new HashSet();
        StringBuilder sbHeaderQuery = new StringBuilder();
        int intControllerId = 0;
        String strGroupName = null;
        int intNoOfHeaders = 0;
        String strControllerId = null;

        String strHeaderWidth = null;
        String strToolTip = null;
        String strParmNumber = null;
        String strAnomInd = null;
        String strParmName = null;
        int intModel = 0;
        String strModel = null;
        String strControllerCfg = null;
        String strFormat = null;
        String strDataScreenColAvail = null;
        String strVvfColAvail = null;
        String strRapidRespColAvail = null;
        String strIncidentColAvail = null;
        String strQnxColAvail = null;
        String strParamObjid = null;

        try {
            session = getHibernateSession();
            // changed by IGS on 22-july-2010 for model change in the data
            // screen
            sbHeaderQuery.append(" SELECT DISTINCT C.OBJID,A.CONTROLLER_SOURCE_ID, A.PARM_GROUP, HEADER_HTML, INFO, ");
            sbHeaderQuery.append(
                    "  SORT_ORDER, COLUMN_NAME, A.CNT, DEF.PARM_LOAD_COLUMN,A.CE_PARAM2MODEL MODEL,DEF.PARM_NUMBER, ");
            sbHeaderQuery.append(
                    "  HEADER_WIDTH, UPPER_BOUND, LOWER_BOUND, DATA_TOOLTIP_FLAG, CONTROLLER_CFG, FORMAT, STYLE,DS_COL_AVAIL, VVF_COL_AVAIL, RR_COL_AVAIL, IN_COL_AVAIL, QNX_COL_AVAIL, ");
            sbHeaderQuery.append("  (SELECT DISTINCT ANOMALY_INDICATOR ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_IND, ");
            sbHeaderQuery.append("  (SELECT DISTINCT DISPLAY_PARM_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOL_ANOM_ADMIN ADM ");
            sbHeaderQuery.append("  WHERE ADM.PARM_NUMBER = ");
            sbHeaderQuery.append("  DEF.PARM_NUMBER ");
            sbHeaderQuery.append("  AND ADM.ANOMALY_INDICATOR = 'N' ");
            sbHeaderQuery.append("  AND ADM.AXIS = 'Y') ANOM_DISP_NAME ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_DPEAB C, ");
            sbHeaderQuery.append("  GETS_RMD_PARMDEF DEF, ");
            sbHeaderQuery.append("  (SELECT   PARM_GROUP, CONTROLLER_SOURCE_ID, COUNT (*) CNT,CE_PARAM2MODEL ");
            sbHeaderQuery.append("  FROM GETS_TOOLS.GETS_TOOL_CE_PARM_DPEAB A ");
            sbHeaderQuery.append("  GROUP BY PARM_GROUP, CONTROLLER_SOURCE_ID,CE_PARAM2MODEL) A ");
            sbHeaderQuery.append("  WHERE A.PARM_GROUP = C.PARM_GROUP ");
            sbHeaderQuery.append("  AND A.CONTROLLER_SOURCE_ID = C.CONTROLLER_SOURCE_ID ");
            sbHeaderQuery.append("  AND A.CE_PARAM2MODEL = C.CE_PARAM2MODEL ");
            sbHeaderQuery.append("  AND DEF.OBJID(+) = PARM_INFO2PARMDEF ");
            sbHeaderQuery.append("  AND DEF.CONTROLLER_SOURCE_ID(+) = C.CONTROLLER_SOURCE_ID ");
            /* Added for EOA Datascreen story */
            // sbHeaderQuery.append(" AND A.CE_PARAM2MODEL =:modelId");
            sbHeaderQuery.append("  ORDER BY   A.CONTROLLER_SOURCE_ID, SORT_ORDER ");

            FaultLogHelper.debug("---DPEAB Header query in AbsFaultLogDAO::: " + sbHeaderQuery + "-----");

            pstmt = session.createSQLQuery(sbHeaderQuery.toString());

            // pstmt.setString(RMDCommonConstants.MODELID, dpeabAssetModelId);

            pstmt.setResultTransformer(new AliasToEntityMapResultTransformer());
            pstmt.setFetchSize(300);
            List<Map<String, String>> aliasToValueMapList = pstmt.list();

            if (RMDCommonUtility.isCollectionNotEmpty(aliasToValueMapList) && !aliasToValueMapList.isEmpty()) {
                GetToolDsParminfoServiceVO objParminfoServiceVO;
                int fixedHeaderWidthTotal = 0;
                int variableHeaderWidthTotal = 0;
                int intWidthLen = 0;
                StringBuilder sbfDummyScrollHeader;
                int i = 0;
                for (Map<String, String> objMap : aliasToValueMapList) {
                    sbfDummyScrollHeader = new StringBuilder();
                    intControllerId = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(STR_CONTROLLER_SOURCE_ID));
                    strGroupName = objMap.get(STR_PARM_GROUP);
                    intNoOfHeaders = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(STR_CNT));

                    strControllerCfg = objMap.get(STR_CONTROLLER_CFG);
                    intModel = RMDCommonUtility.cnvrtBigDecimalObjectToInt(objMap.get(RMDCommonConstants.MODEL_MODEL));
                    strModel = intModel + RMDCommonConstants.EMPTY_STRING;

                    strControllerId = intControllerId + RMDCommonConstants.EMPTY_STRING;

                    if (strControllerId.startsWith(RMDCommonConstants.HYPHEN)) {
                        strModel = RMDCommonConstants.FIXED + strModel;

                    }

                    /* Pasted EOA code */

                    objParminfoServiceVO = new GetToolDsParminfoServiceVO();

                    objParminfoServiceVO.setNoOfHeaders(String.valueOf(intNoOfHeaders));
                    objParminfoServiceVO.setGroupName(strGroupName);
                    strToolTip = objMap.get(STR_INFO);
                    objParminfoServiceVO.setToolTip(strToolTip);

                    strParmNumber = objMap.get(RMDCommonUtility.convertObjectToString(STR_PARM_LOAD_COLUMN));
                    if (strParmNumber != null) {
                        objParminfoServiceVO.setParamNumber(strParmNumber);
                    } else {
                        objParminfoServiceVO.setParamNumber(objMap.get(STR_COLUMN_NAME));
                    }

                    // YJ Change Start: Added for VVF Changes
                    strParamObjid = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap.get(STR_PARM_OBJID));
                    objParminfoServiceVO.setParmObjid(strParamObjid);
                    // YJ Change end

                    strAnomInd = objMap.get(RMDCommonUtility.convertObjectToString(STR_ANOM_IND));
                    objParminfoServiceVO.setAnomInd(strAnomInd);

                    strParmName = objMap.get(RMDCommonUtility.convertObjectToString(STR_ANOM_DISP_NAME));
                    objParminfoServiceVO.setParmName(strParmName);

                    strDataScreenColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_DS_COL_AVAIL));
                    objParminfoServiceVO.setDataScreenFlag(strDataScreenColAvail);

                    strVvfColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_VVF_COL_AVAIL));
                    objParminfoServiceVO.setVvfFlag(strVvfColAvail);

                    strRapidRespColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_RR_COL_AVAIL));
                    objParminfoServiceVO.setRapidResponseFlag(strRapidRespColAvail);

                    strIncidentColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_IN_COL_AVAIL));
                    objParminfoServiceVO.setIncidentFlag(strIncidentColAvail);

                    strQnxColAvail = objMap.get(RMDCommonUtility.convertObjectToString(STR_QNX_COL_AVAIL));
                    objParminfoServiceVO.setQnxColAvail(strQnxColAvail);

                    if (null != objMap.get(STR_PARM_NUMBER)) {
                        objParminfoServiceVO.setParmNumber(RMDCommonConstants.MP + objMap.get(STR_PARM_NUMBER));
                    } else {
                        objParminfoServiceVO.setParmNumber(objMap.get(STR_COLUMN_NAME));
                    }
                    objParminfoServiceVO.setColumnName(objMap.get(STR_COLUMN_NAME));
                    objParminfoServiceVO.setHeaderHtml(objMap.get(STR_HEADER_HTML));
                    if (null == objMap.get(STR_STYLE)) {
                        objParminfoServiceVO.setStyle(RMDCommonConstants.FIXED_STYLE);
                    } else {
                        objParminfoServiceVO.setStyle(objMap.get(STR_STYLE));
                    }
                    objParminfoServiceVO.setInfo(objMap.get(STR_INFO));
                    objParminfoServiceVO
                            .setLowerBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_LOWER_BOUND)));
                    objParminfoServiceVO
                            .setUpperBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_UPPER_BOUND)));
                    objParminfoServiceVO.setDataTooltipFlag(objMap.get(STR_DATA_TOOLTIP_FLAG));

                    String strWidth = RMDCommonUtility.cnvrtBigDecimalObjectToString(objMap.get(STR_HEADER_WIDTH));
                    intWidthLen = Integer.parseInt(strWidth);
                    /* Added Multiplying factor */
                    int width = 5 * intWidthLen;
                    /* Added Multiplying factor */
                    if (i < 4) {
                        fixedHeaderWidthTotal += intWidthLen;
                        if (i == 1) {
                            intWidthLen = intWidthLen - 20;
                        }
                        for (int index = 0; index < intWidthLen; index++) {
                            sbfDummyScrollHeader.append(SPACE);
                        }
                        fixedHeaderWidthTotal += 20;
                    } else {
                        variableHeaderWidthTotal += intWidthLen;
                        for (int index = 0; index < intWidthLen; index++) {
                            sbfDummyScrollHeader.append(SPACE);
                        }
                    }
                    strFormat = objMap.get(STR_FORMAT);
                    String temp = intWidthLen + PIXEL + COMMA;
                    strHeaderWidth = strHeaderWidth + temp;
                    strWidth = String.valueOf(width);
                    objParminfoServiceVO.setHeaderWidth(strWidth + PIXEL);
                    objParminfoServiceVO.setDummyHeaderHtml(sbfDummyScrollHeader.toString());
                    objParminfoServiceVO.setFormat(strFormat);
                    objParminfoServiceVO
                            .setLowerBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_LOWER_BOUND)));
                    objParminfoServiceVO
                            .setUpperBound(RMDCommonUtility.cnvrtStringObjectToDouble(objMap.get(STR_UPPER_BOUND)));
                    objParminfoServiceVO.setFixedHeaderWidthTotal(String.valueOf(fixedHeaderWidthTotal));
                    objParminfoServiceVO.setVariableHeaderWidthTotal(String.valueOf(variableHeaderWidthTotal));
                    objParminfoServiceVO.setStrHeaderWidth(strHeaderWidth);

                    arlHeaderList.add(objParminfoServiceVO);

                    if (!hsGroupName.contains(strGroupName)) {

                        parmGroupInfo = new ToolDsParmGroupServiceVO();
                        parmGroupInfo.setParamGroup(strGroupName);
                        parmGroupInfo.setStyle(objMap.get(STR_STYLE));
                        parmGroupInfo.setColspan(String.valueOf(intNoOfHeaders));

                        arlHeaderGroup.add(parmGroupInfo);
                        hsGroupName.add(strGroupName);
                    }

                    i++;
                }

                objParminfoServiceVO = new GetToolDsParminfoServiceVO();
                for (int k = 0; k < arlHeaderList.size(); k++) {
                    objParminfoServiceVO = (GetToolDsParminfoServiceVO) arlHeaderList.get(k);
                }
                String strFinalHeaderWidth = objParminfoServiceVO.getStrHeaderWidth().substring(0,
                        objParminfoServiceVO.getStrHeaderWidth().length() - 1);

                hmHeader.put(RMDCommonConstants.HEADERDETAIL, arlHeaderList);
                hmHeader.put(RMDCommonConstants.HEADERGRPDETAIL, arlHeaderGroup);
                hmHeader.put(RMDCommonConstants.FINALHEADERWIDTH, strFinalHeaderWidth);
                hmHeader.put(RMDCommonConstants.FIXEDHEADERWIDTH,
                        objParminfoServiceVO.getFixedHeaderWidthTotal() + PIXEL);
                hmHeader.put(RMDCommonConstants.VARIABLEHEADERWIDTH,
                        objParminfoServiceVO.getVariableHeaderWidthTotal() + PIXEL);
                hmHeader.put(RMDCommonConstants.CONTROLLERFLAG, strControllerCfg);
            }

        } catch (Exception e) {
            FaultLogHelper.error("Error in getting the getDPEABHeaderDetails : " + e);
            throw e;
        } finally {

            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }
        FaultLogHelper.debug("End getDPEABHeaderDetails :" + System.currentTimeMillis());
        return hmHeader;
    }

    public HashMap getPaginationDetails() throws Exception {
        List rsPagination = null;
        Query pstmtPagination = null;
        Session session = null;
        String strDBHit = null;
        String strPaginationPerPage = null;
        HashMap hmPagination = new HashMap();
        try {
            session = getHibernateSession();

            String strQuery = "  SELECT * FROM (SELECT VALUE " + STR_DB_HIT + " FROM GETS_RMD_SYSPARM WHERE TITLE  = '"
                    + STR_DB_HIT + "'),(SELECT VALUE " + STR_PER_PAGE + " FROM GETS_RMD_SYSPARM WHERE TITLE  = '"
                    + STR_PER_PAGE + "') ";
            pstmtPagination = session.createSQLQuery(strQuery);
            rsPagination = pstmtPagination.list();

            if (RMDCommonUtility.isCollectionNotEmpty(rsPagination)) {
                int index = 0;
                if (index < rsPagination.size()) {
                    Object data[] = (Object[]) rsPagination.get(index);

                    strDBHit = RMDCommonUtility.convertObjectToString(data[0]);
                    strPaginationPerPage = RMDCommonUtility.convertObjectToString(data[1]);
                }
            }
            hmPagination.put(STR_DB_HIT, strDBHit);
            hmPagination.put(STR_PER_PAGE, strPaginationPerPage);
        } catch (Exception ex) {
            FaultLogHelper.error("Error in getPaginationDetails : " + ex);
            throw ex;
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in Closing Connection: " + ex);
            }
        }

        return hmPagination;

    }// End of method

    public void closeDB(Connection con) throws Exception {
        Connection localcon = con;
        if (localcon != null) {
            localcon.close();
            localcon = null;
        }
    }

    public String getFaultsforDataset(String dataset, String strViewName) {
        StringBuilder whereClauseSB = new StringBuilder();
        if (RMDCommonConstants.DPEAB.equalsIgnoreCase(dataset)) {
            whereClauseSB.append("  AND ").append(strViewName).append(".RECORD_TYPE IN ('")
                    .append(RMDCommonConstants.DP).append("','").append(RMDCommonConstants.EAB).append("') ");

        } else {
            whereClauseSB.append("  AND ").append(strViewName).append(".RECORD_TYPE NOT IN ('")
                    .append(RMDCommonConstants.DP).append("','").append(RMDCommonConstants.EAB).append("') ");
        }
        return whereClauseSB.toString();
    }

    @Cacheable(value = "dataScreen_lookbackCache")
    public HashMap<String, String> getLookbackDays() throws Exception {
        HashMap<String, String> lookbackMap = new HashMap();
        Session session = null;
        Query stmt = null;
        List rs = null;
        String sql = " select CASE_PROBLEM_TYPE, CONTROLLER_SOURCE_ID, MODEL, DEFAULT_LOOKBACK from GETS_SD.GETS_SD_CASETYPE_TO_DEFLKB ";
        try {
            session = getHibernateSession();
            stmt = session.createSQLQuery(sql);
            stmt.setFetchSize(100);
            rs = stmt.list();
            if (rs != null && !rs.isEmpty()) {
                for (int indx = 0; indx < rs.size(); indx++) {
                    Object sortOrderData[] = (Object[]) rs.get(indx);
                    lookbackMap.put(sortOrderData[0].toString() + "_" + sortOrderData[1].toString() + "_"
                            + sortOrderData[2].toString(), sortOrderData[3].toString());
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getModelId" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for Default Sort Order : " + ex);
                throw ex;
            }
        }
        return lookbackMap;
    }

    @Cacheable(value = "dataScreen_sortOrderCache")
    public HashMap<String, String> getSortOrders() throws Exception {
        HashMap<String, String> sortOrderMap = new HashMap();
        Session session = null;
        Query stmt = null;
        List rs = null;
        String sql = " select CASE_PROBLEM_TYPE, CONTROLLER_SOURCE_ID, MODEL, DEFAULT_SORT_ORDER from GETS_SD.GETS_SD_CASETYPE_TO_DEFLKB ";
        try {
            session = getHibernateSession();
            stmt = session.createSQLQuery(sql);
            stmt.setFetchSize(100);
            rs = stmt.list();
            if (rs != null && !rs.isEmpty()) {
                for (int indx = 0; indx < rs.size(); indx++) {
                    Object sortOrderData[] = (Object[]) rs.get(indx);
                    sortOrderMap.put(sortOrderData[0].toString() + "_" + sortOrderData[1].toString() + "_"
                            + sortOrderData[2].toString(), sortOrderData[3].toString());
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getModelId" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for Default Sort Order : " + ex);
                throw ex;
            }
        }
        return sortOrderMap;
    }

    @Cacheable(value = "dataScreen_modelCache")
    public HashMap<String, String> getAllModels() throws Exception {
        HashMap<String, String> modelMap = new HashMap();
        Session session = null;
        Query stmt = null;
        List rs = null;
        String sql = " select MODEL_NAME, objid  from gets_rmd_model";
        try {
            session = getHibernateSession();
            stmt = session.createSQLQuery(sql);
            stmt.setFetchSize(100);
            rs = stmt.list();
            if (rs != null && !rs.isEmpty()) {
                for (int indx = 0; indx < rs.size(); indx++) {
                    Object modelData[] = (Object[]) rs.get(indx);
                    modelMap.put(modelData[0].toString(), modelData[1].toString());
                }
            }
        } catch (Exception e) {
            FaultLogHelper.error("the exception in the method getModelId" + e);
        } finally {
            try {
                releaseSession(session);
            } catch (Exception ex) {
                FaultLogHelper.error("Error in closing Connection for Default Sort Order : " + ex);
                throw ex;
            }
        }
        return modelMap;
    }
}
