package com.ge.trans.eoa.services.reports.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.reports.dao.intf.OHVReportsDAOIntf;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineTruckVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsGraphRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ComStatusVO;
import com.ge.trans.rmd.common.valueobjects.MineVO;
import com.ge.trans.rmd.common.valueobjects.ReportsRxVO;
import com.ge.trans.rmd.common.valueobjects.ReportsTimeSeriesVO;
import com.ge.trans.rmd.common.valueobjects.ReportsTruckEventsVO;
import com.ge.trans.rmd.common.valueobjects.TruckGraphVO;
import com.ge.trans.rmd.common.valueobjects.TruckVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class OHVReportsDAOImpl extends RMDCommonDAO implements OHVReportsDAOIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1182646615817121029L;
	
	private static final String ZERO_STRING = "0";

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.dao.intf.OHVReportsDAOIntf#getMine(java.lang.String)
	 */
	public MineVO getMine(OHVMineRequestVO mineRequest) {
		MineVO mine = null;
		Session session = null;
        String defaultUrgency = "B"; //Default urgency is Blue
		try {
            session = getHibernateSession();
            StringBuilder vehicleQuery = new StringBuilder();            
            vehicleQuery.append("select CUST_NAME, VEHICLE_NO, VEHICLE_OBJID from gets_rmd_cust_rnh_rn_v rnv where ORG_ID = :customerId AND rnv.model_family_v = 'OHV' ORDER BY VEHICLE_NO ASC");
            
            Query vehQuery = session.createSQLQuery(vehicleQuery.toString());
            vehQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
            vehQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineRequest.getMineId());
            List<Map<String,Object>> resultList = null;
			resultList = vehQuery.list();
			int redCount = 0;
			int yellowCount = 0;
			int blueCount = 0;
			List<TruckVO> trucks = new ArrayList<TruckVO>();
			List<String> loads = new ArrayList<String>();
			List<String> engineHrs = new ArrayList<String>();
			List<String> idleTime = new ArrayList<String>();
			List<String> truckParked = new ArrayList<String>();
			List<String> overfloadFaults = new ArrayList<String>();
			List<String> averageHP = new ArrayList<String>();
			List<String> rxOpenCount = new ArrayList<String>();
			List<String> rxCloseCount = new ArrayList<String>();
			List<String> redRx = new ArrayList<String>();
			List<String> yellowRx = new ArrayList<String>();
			List<String> blueRx = new ArrayList<String>();
			List<String> greenRx = new ArrayList<String>();
			Map<String, List<String>> mineParamList = new LinkedHashMap<String, List<String>>();
			Map<String, List<String>> vehFaultObjIdMap = new HashMap<String, List<String>>();
			LOG.info("Number of vehicles:"+resultList.size());
			
			
			
			
			StringBuilder faultQuery = new StringBuilder();
            faultQuery.append("SELECT Min(fault.objid)    AS leastobjid, ");
            faultQuery.append("       Max(fault.objid)    AS latestobjid, ");
            faultQuery.append("       fault.fault2vehicle AS vehicleobjid ");
            faultQuery.append("FROM   gets_tool_fault fault ");
            faultQuery.append("       JOIN gets_tool_mp_mining mining ");
            faultQuery.append("         ON fault.objid = mining.parm2fault ");
            faultQuery.append("       JOIN gets_rmd_cust_rnh_rn_v rnv ");
            faultQuery.append("         ON rnv.vehicle_objid = fault.fault2vehicle ");
            faultQuery.append("WHERE  fault.record_type = 'OHVFLT' ");
            faultQuery.append("       AND rnv.org_id = :customerId ");
            faultQuery.append("       AND rnv.model_family_v = 'OHV' ");
            faultQuery.append("       AND fault.offboard_load_date > sysdate - 1 ");
            faultQuery.append("       AND fault.created_by = 'MINING_PROCESS' ");
            faultQuery.append("       AND ( mp_2489_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2470_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2471_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2473_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2535_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2480_n_0_32 IS NOT NULL ");
            faultQuery.append("              OR mp_2481_n_0_32 IS NOT NULL ) ");
            faultQuery.append("GROUP  BY fault.fault2vehicle ");
            Query fltQuery = session.createSQLQuery(faultQuery.toString());
			fltQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			fltQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineRequest.getMineId());
            List<Map<String,Object>> faultResultList = null;
            faultResultList = fltQuery.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(faultResultList)) {
             for (final Map<String, Object> faultResultListVO : faultResultList) {
            	 List<String> faultObjIdList = new ArrayList<String>();
            	 faultObjIdList.add(RMDCommonUtility.convertObjectToString(faultResultListVO.get("LEASTOBJID")));
            	 faultObjIdList.add(RMDCommonUtility.convertObjectToString(faultResultListVO.get("LATESTOBJID")));
            	 vehFaultObjIdMap.put(RMDCommonUtility.convertObjectToString(faultResultListVO.get("VEHICLEOBJID")), faultObjIdList);
             }
            }
            
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					TruckVO truck = new TruckVO();
					String vehicleObjId = RMDCommonUtility.convertObjectToString(reportsVO.get("VEHICLE_OBJID"));
					String vehicleId = RMDCommonUtility.convertObjectToString(reportsVO.get("VEHICLE_NO"));
					String custID = RMDCommonUtility.convertObjectToString(mineRequest.getMineId());
					truck.setTruck(vehicleId);
					truck.setUrgency(defaultUrgency);
					boolean isTruckComm = this.isTruckCommunication(custID, vehicleId);
					StringBuilder openRxurgencyQuery = new StringBuilder();
				    openRxurgencyQuery.append("SELECT Rtrim(Regexp_replace(( Listagg(CASERECOM.urgency, '-')  within GROUP (ORDER BY caserecom.urgency) ), '([^-]*)(-\\1)+($|-)', '\\1\\3'), '-') AS URGENCY, "
								+ "       veh.model_name_v AS Truck_Model, "
								+ "       Count (CASERECOM.urgency) AS NoOfOpenRX "
								+ "FROM   table_case C, "
								+ "       gets_sd_cust_fdbk fdbk, "
								+ "       gets_sd_recom_delv delv, "
								+ "       gets_sd_recom recom, "
								+ "       gets_sd_case_recom CASERECOM, "
								+ "       gets_rmd_cust_rnh_rn_v veh "
								+ "WHERE  fdbk.objid = delv.recom_delv2cust_fdbk "
								+ "       AND delv.recom_delv2recom = recom.objid "
								+ "       AND CASERECOM.case_recom2case = C.objid "
								+ "       AND CASERECOM.case_recom2recom = RECOM.objid "
								+ "       AND fdbk.cust_fdbk2vehicle = veh.vehicle_objid "
								+ "       AND veh.vehicle_objid = :vehicleObjId"
								+ "       AND fdbk.rx_close_date IS NULL "
								+ "GROUP  BY veh.vehicle_hdr, "
								+ "          veh.vehicle_objid, "
								+ "          veh.vehicle_no, "
								+ "          veh.model_name_v "
								+ "ORDER  BY vehicle_no");
					
					Query urgQuery = session.createSQLQuery(openRxurgencyQuery.toString());
					urgQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
					urgQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_OBJID, vehicleObjId);
		            List<Map<String,Object>> urgResultList = null;
		            urgResultList = urgQuery.list();
		            if (RMDCommonUtility.isCollectionNotEmpty(urgResultList)) {
		            	for( Map<String, Object> urgResult : urgResultList) {
		            		String allUrgency = RMDCommonUtility.convertObjectToString(urgResult.get("URGENCY"));
		            		if(allUrgency != null) {						
								if(allUrgency.contains("R")){
									truck.setUrgency("R");
									redCount ++;
								}
								else if(allUrgency.contains("W")){
									truck.setUrgency("Y");
									yellowCount++;
								}
								else if(allUrgency.contains("G"))
									truck.setUrgency("G");
								else if(allUrgency.contains("B")) {
									truck.setUrgency("B");
									blueCount++;
								}
							}
		            	}
		            	
		            } else {
		            	StringBuilder closedRxurgencyQuery = new StringBuilder();
		            	
		            	closedRxurgencyQuery.append("SELECT Rtrim(Regexp_replace(( Listagg(CASERECOM.urgency, '-')  within GROUP (ORDER BY caserecom.urgency) ), '([^-]*)(-\\1)+($|-)', '\\1\\3'), '-') AS URGENCY, "
		    					+ "       veh.model_name_v AS Truck_Model, "
		    					+ "       Count (CASERECOM.urgency) AS NoOfOpenRX "
		    					+ "FROM   table_case C, "
		    					+ "       gets_sd_cust_fdbk fdbk, "
		    					+ "       gets_sd_recom_delv delv, "
		    					+ "       gets_sd_recom recom, "
		    					+ "       gets_sd_case_recom CASERECOM, "
		    					+ "       gets_rmd_cust_rnh_rn_v veh "
		    					+ "WHERE  fdbk.objid = delv.recom_delv2cust_fdbk "
		    					+ "       AND delv.recom_delv2recom = recom.objid "
		    					+ "       AND CASERECOM.case_recom2case = C.objid "
		    					+ "       AND CASERECOM.case_recom2recom = RECOM.objid "
		    					+ "       AND fdbk.cust_fdbk2vehicle = veh.vehicle_objid "
		    					+ "       AND veh.vehicle_objid = :vehicleObjId "
		    					+ "       AND fdbk.rx_close_date is not null "
		    					+ "GROUP  BY veh.vehicle_hdr, "
		    					+ "          veh.vehicle_objid, "
		    					+ "          veh.vehicle_no, "
		    					+ "          veh.model_name_v "
		    					+ "ORDER  BY vehicle_no");
						Query closedUrgQuery = session.createSQLQuery(closedRxurgencyQuery.toString());
						closedUrgQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
						closedUrgQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_OBJID, vehicleObjId);
			            List<Map<String,Object>> closedUrgResultList = null;
			            closedUrgResultList = closedUrgQuery.list();
			            if (RMDCommonUtility.isCollectionNotEmpty(urgResultList)) {
			            	for( Map<String, Object> urgResult : closedUrgResultList) {
			            		String allUrgency = RMDCommonUtility.convertObjectToString(urgResult.get("URGENCY"));
			            		if(allUrgency != null) {						
			            			if(allUrgency.contains("B")) {
										truck.setUrgency("B");
										blueCount++;
			            			}
			            			else {
			            				if(isTruckComm)
			            					truck.setUrgency("BL");
			            			}
			            				
								}
			            	}
			            	
			            } else {
			            	if(isTruckComm)
            					truck.setUrgency("BL");
			            	else 
			            		blueCount++;
			            }
		            }
					
		            String leastObjid = null;
		            String latestObjid = null;
					if (!vehFaultObjIdMap.isEmpty()) {
						if(vehFaultObjIdMap.get(vehicleObjId) != null){
						leastObjid = RMDCommonUtility.convertObjectToString(vehFaultObjIdMap.get(vehicleObjId).get(0));
						latestObjid = RMDCommonUtility.convertObjectToString(vehFaultObjIdMap.get(vehicleObjId).get(1));
					    }
					if(leastObjid != null && latestObjid != null) {                        
						String valueQuery = "select MP_2489_N_0_32, MP_2470_N_0_32,  MP_2471_N_0_32,  MP_2473_N_0_32,  MP_2535_N_0_32, MP_2480_N_0_32,  MP_2481_N_0_32 "
	                            + "from  "
	                            + "(SELECT MP_2489_N_0_32, MP_2470_N_0_32, MP_2471_N_0_32, MP_2473_N_0_32, MP_2535_N_0_32, MP_2480_N_0_32,  MP_2481_N_0_32 "
	                            + "FROM gets_tool_mp_mining where parm2fault = :paramNo AND created_by  ='MINING_PROCESS' order by objid )"
	                            + " where rownum =1";
						Query valQuery = session.createSQLQuery(valueQuery);
						valQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
						valQuery.setParameter(RMDCommonConstants.OHV_PARAM_ID, leastObjid);
						List<Map<String,Object>> leastValueMapList = null;
						leastValueMapList = valQuery.list();
						valQuery.setParameter(RMDCommonConstants.OHV_PARAM_ID, latestObjid);
						List<Map<String,Object>> latestValueMapList = null;
						latestValueMapList = valQuery.list();
						
						double propelTime;
						if(latestValueMapList.get(0).get("MP_2480_N_0_32") == null){
							propelTime = 0;
						} else if(latestValueMapList.get(0).get("MP_2480_N_0_32") == leastValueMapList.get(0).get("MP_2480_N_0_32") || leastValueMapList.get(0).get("MP_2480_N_0_32") == null ) 
                            propelTime = Double.parseDouble(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2480_N_0_32").toString().trim()) / 3600)));
						else
							propelTime =  Double.parseDouble(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2480_N_0_32").toString().trim()) - Double.parseDouble(leastValueMapList.get(0).get("MP_2480_N_0_32").toString().trim())) / 3600));
            
						double retardTime;
						if(latestValueMapList.get(0).get("MP_2481_N_0_32") == null){
							retardTime = 0;
						} else if(latestValueMapList.get(0).get("MP_2481_N_0_32") == leastValueMapList.get(0).get("MP_2481_N_0_32") || leastValueMapList.get(0).get("MP_2481_N_0_32") == null ) 
							retardTime = Double.parseDouble(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2481_N_0_32").toString().trim()) / 3600)));
						else
							retardTime =  Double.parseDouble(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2481_N_0_32").toString().trim()) - Double.parseDouble(leastValueMapList.get(0).get("MP_2481_N_0_32").toString().trim())) / 3600));

						truck.setActiveTime(String.format("%.2f",propelTime + retardTime));
						
						long load = 0;
						if(latestValueMapList.get(0).get("MP_2489_N_0_32") == null){
							load = 0;
						} else if(latestValueMapList.get(0).get("MP_2489_N_0_32") == leastValueMapList.get(0).get("MP_2489_N_0_32") || leastValueMapList.get(0).get("MP_2489_N_0_32") == null ) {
							load = (Long.parseLong(latestValueMapList.get(0).get("MP_2489_N_0_32").toString().trim()));
						    loads.add(latestValueMapList.get(0).get("MP_2489_N_0_32").toString().trim());
						}
						else {
							load =  (Long.parseLong(latestValueMapList.get(0).get("MP_2489_N_0_32").toString().trim()) - Long.parseLong(leastValueMapList.get(0).get("MP_2489_N_0_32").toString().trim()));
							loads.add(String.valueOf((Long.parseLong(latestValueMapList.get(0).get("MP_2489_N_0_32").toString().trim()) - Long.parseLong(leastValueMapList.get(0).get("MP_2489_N_0_32").toString().trim()))));
						
						}
						truck.setLoads(Long.toString(load));
						
						if(latestValueMapList.get(0).get("MP_2470_N_0_32") != null){
						  if(latestValueMapList.get(0).get("MP_2470_N_0_32") == leastValueMapList.get(0).get("MP_2470_N_0_32") || leastValueMapList.get(0).get("MP_2470_N_0_32") == null ) 
							  engineHrs.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2470_N_0_32").toString().trim()) / 3600)));
						else
							engineHrs.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2470_N_0_32").toString().trim()) - Double.parseDouble(leastValueMapList.get(0).get("MP_2470_N_0_32").toString().trim())) / 3600));
            
						}
						
						
						if(latestValueMapList.get(0).get("MP_2471_N_0_32") != null){
							if(latestValueMapList.get(0).get("MP_2471_N_0_32") == leastValueMapList.get(0).get("MP_2471_N_0_32") || leastValueMapList.get(0).get("MP_2471_N_0_32") == null ) 
								idleTime.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2471_N_0_32").toString().trim()) / 3600)));
							else
								idleTime.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2471_N_0_32").toString().trim()) - Double.parseDouble(leastValueMapList.get(0).get("MP_2471_N_0_32").toString().trim())) / 3600));
						}
						
						if(latestValueMapList.get(0).get("MP_2473_N_0_32") != null){
							if(latestValueMapList.get(0).get("MP_2473_N_0_32") == leastValueMapList.get(0).get("MP_2473_N_0_32") || leastValueMapList.get(0).get("MP_2473_N_0_32") == null ) 
								truckParked.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2473_N_0_32").toString().trim()) / 3600)));
							else
								truckParked.add(String.format("%.2f",(Double.parseDouble(latestValueMapList.get(0).get("MP_2473_N_0_32").toString().trim()) - Double.parseDouble(leastValueMapList.get(0).get("MP_2473_N_0_32").toString().trim())) / 3600));
						}
						
						if(latestValueMapList.get(0).get("MP_2535_N_0_32") != null){
							if(latestValueMapList.get(0).get("MP_2535_N_0_32") == leastValueMapList.get(0).get("MP_2535_N_0_32") || leastValueMapList.get(0).get("MP_2535_N_0_32") == null ) 
								overfloadFaults.add(String.valueOf((Long.parseLong(latestValueMapList.get(0).get("MP_2535_N_0_32").toString().trim()))));
							else
								overfloadFaults.add(String.valueOf((Long.parseLong(latestValueMapList.get(0).get("MP_2535_N_0_32").toString().trim()) - Long.parseLong(leastValueMapList.get(0).get("MP_2535_N_0_32").toString().trim()))));
						}
						
					}
						String averageHpQuery = "select CAST(ROUND(avg(HP_FEEDBACK_ALTERNATOR_INPUT),2) AS VARCHAR2(30)) AS AVGHP "
								+ "FROM gets_tool_mp_mining mining, "
								+ "gets_tool_fault fault, "
								+ "gets_rmd_cust_rnh_rn_v grcrrv  "
								+ "where grcrrv.vehicle_objid = fault.fault2vehicle  "
								+ "and fault.objid = mining.parm2fault "
								+ "and fault.offboard_load_date > sysdate - 1 "
								+ "and grcrrv.vehicle_hdr = :customerId  "
								+ "and grcrrv.vehicle_no = :vehicleNo "
								+ "and mining.created_by  ='MINING_PROCESS' ";
						Query avgHpQuery = session.createSQLQuery(averageHpQuery);
						avgHpQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
						avgHpQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineRequest.getMineId());
						avgHpQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, vehicleId);
						List<Map<String,Object>> avgHpList = null;
						avgHpList = avgHpQuery.list();
						if (RMDCommonUtility.isCollectionNotEmpty(avgHpList)) {
							for (final Map<String, Object> avgHp : avgHpList) {
								String avgVal = RMDCommonUtility.convertObjectToString(avgHp.get("AVGHP"));
								 if (avgVal != null && !ZERO_STRING.equalsIgnoreCase(avgVal))
                                     averageHP.add(avgVal);
							}
						}
					}
					
					String chartQuery = "SELECT fdbk.rx_close_date AS CLOSED_DATE, count(fdbk.rx_open_date) as OPEN_COUNT, "
							+ "count(fdbk.rx_close_date) as CLOSE_COUNT, "
							+ "count(decode(CASERECOM.urgency,'R','RED')) as RED_RX, "
							+ "count(decode(CASERECOM.urgency,'W','WHITE')) as WHITE_RX, "
							+ "count(decode(CASERECOM.urgency,'Y','YELLOW')) as YELLOW_RX, "
							+ "count(decode(CASERECOM.urgency,'B','BLUE')) as BLUE_RX "
							+ "FROM table_case C, "
							+ "gets_sd_cust_fdbk fdbk, "
							+ "gets_sd_recom_delv delv, "
							+ "gets_sd_recom recom, "
							+ "gets_sd_case_recom CASERECOM, "
							+ "gets_rmd_cust_rnh_rn_v veh "
							+ "WHERE fdbk.objid = delv.RECOM_DELV2CUST_FDBK "
							+ "AND delv.recom_delv2case = C.objid "
							+ "AND delv.RECOM_DELV2RECOM  = recom.objid "
							+ "AND fdbk.CUST_FDBK2VEHICLE = veh.vehicle_objid "
							+ "AND CASERECOM.case_recom2case = C.objid "
							+ "AND CASERECOM.case_recom2recom = RECOM.objid "
							+ "AND veh.model_family_v     = 'OHV' "
							+ "and veh.vehicle_objid= :vehicleObjId "
							+ "AND (fdbk.rx_close_date >= fdbk.creation_date OR fdbk.rx_close_date IS NULL) "
							+ "GROUP BY fdbk.rx_close_date ";
					
					Query chartQ = session.createSQLQuery(chartQuery);
					chartQ.setResultTransformer(new AliasToEntityMapResultTransformer());
					chartQ.setParameter(RMDCommonConstants.OHV_VEHICLE_OBJID, vehicleObjId);
					List<Map<String,Object>> chartValueMapList = null;
					chartValueMapList = chartQ.list();
					int localGreenCount = 0;
					int localRedCount = 0;
					int localYellowCount = 0;
					int localBlueCount = 0;
					int openCount = 0;
					int closedCount = 0;
					if (RMDCommonUtility.isCollectionNotEmpty(chartValueMapList)) {
						for (final Map<String, Object> chartResult : chartValueMapList) {
							if(chartResult.get("CLOSED_DATE") != null) {
								localGreenCount += RMDCommonUtility.convertObjectToInt(chartResult.get("RED_RX"));
								localGreenCount += RMDCommonUtility.convertObjectToInt(chartResult.get("WHITE_RX"));
								localGreenCount += RMDCommonUtility.convertObjectToInt(chartResult.get("YELLOW_RX"));
								localGreenCount += RMDCommonUtility.convertObjectToInt(chartResult.get("BLUE_RX"));
								closedCount += RMDCommonUtility.convertObjectToInt(chartResult.get("CLOSE_COUNT"));
							} else {
								localRedCount += RMDCommonUtility.convertObjectToInt(chartResult.get("RED_RX"));
								localYellowCount += RMDCommonUtility.convertObjectToInt(chartResult.get("YELLOW_RX"));
								localYellowCount += RMDCommonUtility.convertObjectToInt(chartResult.get("WHITE_RX"));
								localBlueCount += RMDCommonUtility.convertObjectToInt(chartResult.get("BLUE_RX"));
							}
						}
					}
					openCount = localRedCount +	localYellowCount + localBlueCount;
					truck.setOpenRx(String.valueOf(openCount));
					if(openCount != 0)
						rxOpenCount.add(String.valueOf(openCount));
					if(closedCount != 0)
						rxCloseCount.add(String.valueOf(closedCount));
					if(localRedCount != 0)
						redRx.add(String.valueOf(localRedCount));
					if(localYellowCount != 0)
						yellowRx.add(String.valueOf(localYellowCount));
					if(localBlueCount != 0)
						blueRx.add(String.valueOf(localBlueCount));
					if(localGreenCount != 0)
						greenRx.add(String.valueOf(localGreenCount));
					
					Map<String, String> bomElements = getBOMElements(mineRequest.getMineId(), vehicleId);
					truck.setCustomerModel(bomElements.get(RMDCommonConstants.OHV_BOM_CUST_MODEL));
					trucks.add(truck);					
				}
				
				mineParamList.put(RMDCommonConstants.OHV_MINE_LOADS, loads);
				mineParamList.put(RMDCommonConstants.OHV_MINE_ENGINE_IDLE, idleTime);
				mineParamList.put(RMDCommonConstants.OHV_MINE_ENGINE_OP, engineHrs);
				mineParamList.put(RMDCommonConstants.OHV_MINE_AVG_HP, averageHP);
				mineParamList.put(RMDCommonConstants.OHV_MINE_TRUCK_PARKED, truckParked);
				mineParamList.put(RMDCommonConstants.OHV_MINE_OPEN_RX, rxOpenCount);
				mineParamList.put(RMDCommonConstants.OHV_MINE_CLOSED_RX, rxCloseCount);
				mineParamList.put(RMDCommonConstants.OHV_MINE_RED, redRx);
				mineParamList.put(RMDCommonConstants.OHV_MINE_YELLOW, yellowRx);
				mineParamList.put(RMDCommonConstants.OHV_MINE_BLUE, blueRx);
				mineParamList.put(RMDCommonConstants.OHV_MINE_RX_CLOSURE_TIME, this.getAvgRxClosureTime(mineRequest.getMineId()));  
				
				
				mine = new MineVO();
				mine.setTrucks(trucks);
				mine.setHeader(mineRequest.getMineId());
				mine.setMineStatus(getMineStatus(redCount, yellowCount, trucks.size(), mineRequest.getMineId()));
				mine.setCommStatus(getCommunicationStatus(blueCount, trucks.size()));
				mine.setMineParam(mineParamList);
				mine.setHealthReport(String.valueOf(getHealthCheckCount(mineRequest.getMineId(), null)));
				mine.setMessageReceived(String.valueOf(getIncidentCount(mineRequest.getMineId(), null)));
			}
			
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return mine;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.dao.intf.OHVReportsDAOIntf#getReportsRx(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportsRxVO> getReportsRx(OHVReportsRxRequestVO rxRequestVO) {
		Session session = null;
		List<ReportsRxVO> reportsRxList = new ArrayList<ReportsRxVO>();
		ReportsRxVO reportsRx = null;
		
        try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
			String rxType = rxRequestVO.getRxType();
			queryString.append(" SELECT TSP.SERIAL_NO AS TRUCK_ID, FDBK.RX_CASE_ID RX_ID ,C.ID_NUMBER CASE_ID, RECOM.TITLE RX_TITLE , RECOM.CREATION_DATE CREATION_DT, "
					+ "RECOM.LAST_UPDATED_DATE LAST_UPDATED_DT,  CASERECOM.URGENCY URGENCY , "
					+ "CASERECOM.EST_REPAIR_TIME REPAIR_TIME  , RECOM.LOCO_IMPACT LOCO_IMPACT,  TO_CHAR(DELV.DELV_DATE, 'MM/DD/YYYY HH24:MI:SS') RX_DELIVER_DATE  , "
					+ "DELV.RECOM_NOTES , VEH.VEHICLE2MODEL MODELOBJID , VEH.VEHICLE2FLEET FLEETOBJID  , RECOM.OBJID RECOMOBJID  ,"
					+ "TO_CHAR(FDBK.RX_CLOSE_DATE, 'MM/DD/YYYY HH24:MI:SS') RX_CLOSE_DATE,RECOM_TYPE  "
					+ "FROM GETS_SD_RECOM RECOM ,GETS_SD_CASE_RECOM CASERECOM  ,GETS_SD_RECOM_DELV DELV ,"
					+ "GETS_SD_CUST_FDBK FDBK  ,GETS_RMD_VEHICLE VEH,  GETS_RMD_VEH_HDR VEHHDR, TABLE_SITE_PART TSP,  "
					+ "gets_rmd_model VEHMOD,TABLE_BUS_ORG TBO, TABLE_CASE C "
					+ "WHERE DELV.RECOM_DELV2RECOM = RECOM.OBJID "
					+ "AND CASERECOM.CASE_RECOM2RECOM = RECOM.OBJID  "
					+ "AND fdbk.CUST_FDBK2VEHICLE = veh.OBJID "
					+ "AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID "
					+ "AND VEH.VEHICLE2SITE_PART = TSP.OBJID "
					+ "AND VEHMOD.objid  = VEH.VEHICLE2MODEL "
					+ "AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID "
					+ "AND FDBK.OBJID = DELV.RECOM_DELV2CUST_FDBK "	
					+ "AND FDBK.CUST_FDBK2CASE=CASERECOM.CASE_RECOM2CASE "
					+ "AND DELV.RECOM_DELV2CASE = C.OBJID "
					+ "AND vehmod.model_name = 'OHV' AND INSTR((tsp.serial_no),'BAD') = 0 ");
			if(rxRequestVO.getTruckId() != null && (!"".equalsIgnoreCase(rxRequestVO.getTruckId().trim())))
				queryString.append("AND TSP.SERIAL_NO = :vehicleNo ");
			
			queryString.append("AND TBO.S_ORG_ID = :customerId ");			
			if(RMDCommonConstants.OHV_RX_OPEN.equalsIgnoreCase(rxType)) {
				queryString.append("AND FDBK.RX_CLOSE_DATE IS NULL ");
			} else if(RMDCommonConstants.OHV_RX_CLOSED.equalsIgnoreCase(rxType)) {
				queryString.append("AND  FDBK.RX_CLOSE_DATE BETWEEN to_date(:fromDate,'MM/DD/YYYY HH24:MI:SS') AND to_date(:toDate,'MM/DD/YYYY HH24:MI:SS') ");
			} else if(RMDCommonConstants.OHV_RX_ALL.equalsIgnoreCase(rxType)) {
				queryString.append("AND (FDBK.RX_CLOSE_DATE IS NULL OR FDBK.RX_CLOSE_DATE >= SYSDATE -  365*3)");
			}
			if(RMDCommonConstants.OHV_RX_RED_URGENCY.equalsIgnoreCase(rxRequestVO.getUrgency())){
				queryString.append("AND CASERECOM.URGENCY = 'R' ");
			}
			else if(RMDCommonConstants.OHV_RX_WHITE_URGENCY.equalsIgnoreCase(rxRequestVO.getUrgency())){
				queryString.append("AND CASERECOM.URGENCY = 'W' ");
			}
			Query rxQuery = session.createSQLQuery(queryString.toString());
			if(rxRequestVO.getTruckId() != null && (!"".equalsIgnoreCase(rxRequestVO.getTruckId().trim())))
				rxQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, rxRequestVO.getTruckId());
			rxQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, rxRequestVO.getMineId());
			if(RMDCommonConstants.OHV_RX_CLOSED.equalsIgnoreCase(rxType)) {
				rxQuery.setParameter(RMDCommonConstants.OHV_FROM_DATE, rxRequestVO.getFromDate());
				rxQuery.setParameter(RMDCommonConstants.OHV_TO_DATE, rxRequestVO.getToDate());
			}
			rxQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			List<Map<String,Object>> resultList = null;
			resultList = rxQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					reportsRx = new ReportsRxVO();
					reportsRx.setRxObjid(RMDCommonUtility.convertObjectToString(reportsVO.get("RX_ID")));
					reportsRx.setRxTitle(RMDCommonUtility.convertObjectToString(reportsVO.get("RX_TITLE")));
					reportsRx.setTruckId(RMDCommonUtility.convertObjectToString(reportsVO.get("TRUCK_ID")));
					if(reportsVO.get("RX_DELIVER_DATE") != null) {
						reportsRx.setRxDeliverDate(reportsVO.get("RX_DELIVER_DATE").toString());
					}
					reportsRx.setUrgency(RMDCommonUtility.convertObjectToString(reportsVO.get("URGENCY")));					
					if(reportsVO.get("RX_CLOSE_DATE") != null) {
						reportsRx.setRxClosedDate(reportsVO.get("RX_CLOSE_DATE").toString());
					}
					reportsRx.setCaseId(reportsVO.get("CASE_ID").toString());
					reportsRx.setEstmRepairTime(reportsVO.get("REPAIR_TIME").toString());
					reportsRx.setLocoImpact(reportsVO.get("LOCO_IMPACT").toString());
					reportsRxList.add(reportsRx);
				}
			}
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_RX);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return reportsRxList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportsTruckEventsVO> getReportsTruckEvents(
			OHVReportsRxRequestVO truckEventsRequestVO) {
		Session session = null;
		List<ReportsTruckEventsVO> reportsTruckEventsList = new ArrayList<ReportsTruckEventsVO>();
		ReportsTruckEventsVO reportsTruckEvents = null;
		try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT F.fault_code                                         AS EVENT_NUMBER, ");
			queryString.append("       FC.sub_id                                            AS SUB_ID, ");
			queryString.append("       FC.fault_desc                                        AS DESCRIPTION, ");
			queryString.append("       To_char(F.occur_date, 'MM/DD/YYYY HH24:MI:SS')       AS OCCUR_TIME, ");
			queryString.append("       To_char(F.fault_reset_date, 'MM/DD/YYYY HH24:MI:SS') AS RESET_TIME ");
			queryString.append("FROM   gets_tool_fault F ");
			queryString.append("       JOIN gets_rmd_fault_codes FC ");
			queryString.append("         ON FC.objid = F.fault2fault_code ");
			queryString.append("       JOIN gets_rmd_cust_rnh_rn_v VEH ");
			queryString.append("         ON VEH.vehicle_objid = F.fault2vehicle ");
			queryString.append("WHERE  F.offboard_load_date > sysdate - 1 ");
			queryString.append("       AND VEH.org_id = :customerId ");
			queryString.append("       AND veh.vehicle_no = :vehicleNo ");
			queryString.append("       AND F.fault_code NOT IN ( '645', '650', '651', '700', ");
			queryString.append("                                 '701', '702', '703', '704', ");
			queryString.append("                                 '705', '706', '707', '708', ");
			queryString.append("                                 '709', '710', '711', '714', ");
			queryString.append("                                 '715', '716', '717', '718', ");
			queryString.append("                                 '720', '729', '730', '731', ");
			queryString.append("                                 '732', '734', '735', '736', ");
			queryString.append("                                 '737', '738', '739', '740', ");
			queryString.append("                                 '741', '742', '743', '744', ");
			queryString.append("                                 '748', '749', '750', '752', ");
			queryString.append("                                 '754', '756', '759', '765', ");
			queryString.append("                                 '766', '770', '775', '790', ");
			queryString.append("                                 '791', '792', '793', 'H00000', ");
			queryString.append("                                 'H00001', 'H00100', 'HC1' )");
			queryString.append("                                 ORDER  BY OCCUR_TIME DESC");
			
			
			
			
			Query truckEventsQuery = session.createSQLQuery(queryString.toString());
			truckEventsQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckEventsRequestVO.getMineId());
			truckEventsQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckEventsRequestVO.getTruckId());
			truckEventsQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			List<Map<String,Object>> resultList = null;
			resultList = truckEventsQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					reportsTruckEvents = new ReportsTruckEventsVO();
					reportsTruckEvents.setEventNumber(RMDCommonUtility.convertObjectToString(reportsVO.get("EVENT_NUMBER")));
					reportsTruckEvents.setSubId(RMDCommonUtility.convertObjectToString(reportsVO.get("SUB_ID")));
					reportsTruckEvents.setDescription(RMDCommonUtility.convertObjectToString(reportsVO.get("DESCRIPTION")));
					if(reportsVO.get("OCCUR_TIME") != null){
						reportsTruckEvents.setOccurTime(reportsVO.get("OCCUR_TIME").toString());
					}					
					if(reportsVO.get("RESET_TIME") != null){
						reportsTruckEvents.setResetTime(reportsVO.get("RESET_TIME").toString());
					}
					reportsTruckEventsList.add(reportsTruckEvents);
				}
			}
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return reportsTruckEventsList;
	}
	
	@Override
	public List<ReportsTruckEventsVO> getReportsTopEvents(OHVMineRequestVO mineRequest){
		Session session = null;
		List<ReportsTruckEventsVO> reportsTruckEventsList = new ArrayList<ReportsTruckEventsVO>();
		ReportsTruckEventsVO reportsTruckEvents = null;
		try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT event_number, ");
			queryString.append("       sub_id, ");
			queryString.append("       description, ");
			queryString.append("       occurance, ");
			queryString.append("       trucksperday ");
			queryString.append("FROM  (SELECT event_number, ");
			queryString.append("              sub_id, ");
			queryString.append("              description, ");
			queryString.append("              Sum(occurance)      AS OCCURANCE, ");
			queryString.append("              Count(event_number) AS TRUCKSPERDAY ");
			queryString.append("       FROM  (SELECT F.fault_code        AS EVENT_NUMBER, ");
			queryString.append("                     FC.sub_id           AS SUB_ID, ");
			queryString.append("                     FC.fault_desc       AS DESCRIPTION, ");
			queryString.append("                     Count(F.fault_code) AS OCCURANCE ");
			queryString.append("              FROM   gets_tool_fault F ");
			queryString.append("                     JOIN gets_rmd_fault_codes FC ");
			queryString.append("                       ON FC.objid = F.fault2fault_code ");
			queryString.append("                     JOIN gets_rmd_vehicle VEH ");
			queryString.append("                       ON VEH.objid = F.fault2vehicle ");
			queryString.append("                     JOIN gets_rmd_veh_hdr VEHHDR ");
			queryString.append("                       ON VEH.vehicle2veh_hdr = VEHHDR.objid ");
			queryString.append("                     JOIN table_bus_org TBO ");
			queryString.append("                       ON VEHHDR.veh_hdr2busorg = TBO.objid ");
			queryString.append("              WHERE  F.offboard_load_date > sysdate - 1 ");
			queryString.append("                     AND TBO.s_org_id = :customerId ");
			if(mineRequest.isGeLevelReport()){
			queryString.append("                       AND ( F.fault_code NOT IN ( '645', '650', '651', '700', ");
			queryString.append("                                                   '701', '702', '703', '704', ");
			queryString.append("                                                   '705', '706', '707', '708', ");
			queryString.append("                                                   '709', '710', '711', '714', ");
			queryString.append("                                                   '715', '716', '717', '718', ");
			queryString.append("                                                   '720', '729', '730', '731', ");
			queryString.append("                                                   '732', '734', '735', '736', ");
			queryString.append("                                                   '737', '738', '739', '740', ");
			queryString.append("                                                   '741', '742', '743', '744', ");
			queryString.append("                                                   '748', '749', '750', '752', ");
			queryString.append("                                                   '754', '756', '759', '765', ");
			queryString.append("                                                   '766', '790', '791', '792', ");
			queryString.append("                                                   '793', 'H00000', 'H00001', ");
			queryString.append("                                                   'H00100', ");
			queryString.append("                                                   'HC1', '770' ) ");
			queryString.append("                              OR ( F.fault_code = '770' ");
			queryString.append("                                   AND FC.sub_id = '3' ) ) ");
			}else {
			queryString.append("                     AND F.fault_code NOT IN ( '645', '650', '651', '700', ");
			queryString.append("                                               '701', '702', '703', '704', ");
			queryString.append("                                               '705', '706', '707', '708', ");
			queryString.append("                                               '709', '710', '711', '714', ");
			queryString.append("                                               '715', '716', '717', '718', ");
			queryString.append("                                               '720', '729', '730', '731', ");
			queryString.append("                                               '732', '734', '735', '736', ");
			queryString.append("                                               '737', '738', '739', '740', ");
			queryString.append("                                               '741', '742', '743', '744', ");
			queryString.append("                                               '748', '749', '750', '752', ");
			queryString.append("                                               '754', '756', '759', '765', ");
			queryString.append("                                               '766', '770', '790', ");
			queryString.append("                                               '791', '792', '793', 'H00000', ");
			queryString.append("                                               'H00001', 'H00100', 'HC1' ) ");	
			}
			queryString.append("              GROUP  BY F.fault_code, ");
			queryString.append("                        FC.sub_id, ");
			queryString.append("                        FC.fault_desc) ");
			queryString.append("              GROUP  BY event_number, ");
			queryString.append("                 sub_id, ");
			queryString.append("                 description ");
			queryString.append("       ORDER  BY occurance DESC) ");
			queryString.append("WHERE  rownum <= 10 ");
			
			Query truckEventsQuery = session.createSQLQuery(queryString.toString());
			truckEventsQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineRequest.getMineId());
			truckEventsQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			List<Map<String,Object>> resultList = null;
			resultList = truckEventsQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					reportsTruckEvents = new ReportsTruckEventsVO();
					reportsTruckEvents.setEventNumber(RMDCommonUtility.convertObjectToString(reportsVO.get("EVENT_NUMBER")));
					reportsTruckEvents.setSubId(RMDCommonUtility.convertObjectToString(reportsVO.get("SUB_ID")));
					reportsTruckEvents.setDescription(RMDCommonUtility.convertObjectToString(reportsVO.get("DESCRIPTION")));
					reportsTruckEvents.setNumberOfOccurance(RMDCommonUtility.convertObjectToString(reportsVO.get("OCCURANCE")));
					reportsTruckEvents.setTrucksPerDay(RMDCommonUtility.convertObjectToString(reportsVO.get("TRUCKSPERDAY")));
					reportsTruckEventsList.add(reportsTruckEvents);
				}
			}
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return reportsTruckEventsList;
	}

	@Override
	public TruckVO getTruckInfo(OHVReportsRxRequestVO truckInfoVO) {
		TruckVO truckInfo = new TruckVO();
		Session session = null;
		String mineHeader = truckInfoVO.getMineId();
		try {
			Map<String, String> bomElements = getBOMElements(truckInfoVO.getMineId(), truckInfoVO.getTruckId());
			truckInfo.setCustomerModel(bomElements.get(RMDCommonConstants.OHV_BOM_CUST_MODEL) == null ? "" : bomElements.get(RMDCommonConstants.OHV_BOM_CUST_MODEL));
			truckInfo.setControllerConfig(bomElements.get(RMDCommonConstants.OHV_BOM_CONTROLLER) == null ? "" : bomElements.get(RMDCommonConstants.OHV_BOM_CONTROLLER));
			truckInfo.setHeader(mineHeader);
			StringBuilder queryString = new StringBuilder();
			queryString.append("Select GRCRRV.cust_name as MINE, "
					+ "GRCRRV.vehicle_no as TRUCK, "
					+ "GRCRRV.fleet_number_v as FLEET, "
					+ "GRCRRV.vehicle_hdr as HEADER, "
					+ "TSP.INSTANCE_NAME as GE_MODEL "
					+ "FROM GETS_RMD_CUST_RNH_RN_V GRCRRV "
					+ "INNER JOIN  table_site_part tsp "
					+ "ON GRCRRV.SITE_PART_OBJID=tsp.objid "
					+ "where vehicle_hdr=:customerId and vehicle_no=:vehicleNo ");

			session = getHibernateSession();			
			
			Query query = session.createSQLQuery(queryString.toString());
			query.setResultTransformer(new AliasToEntityMapResultTransformer());
			query.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
			query.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
			List<Map<String, Object>> resultList = null;
			resultList = query.list();
			if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
				for (final Map<String, Object> reportsVO : resultList) {
					String mine = RMDCommonUtility.convertObjectToString(reportsVO.get("MINE"));
					String fleet = RMDCommonUtility.convertObjectToString(reportsVO.get("FLEET"));
					String geModel = RMDCommonUtility.convertObjectToString(reportsVO.get("GE_MODEL"));
					
					truckInfo.setMine(mine == null ? "" : mine);
					truckInfo.setTruck(truckInfoVO.getTruckId());
					truckInfo.setFleet(fleet == null ? "" : fleet);
					truckInfo.setGeModel(geModel == null ? "" : geModel);
				}
			}
			
			
			StringBuilder queryStringVersion = new StringBuilder();
			queryStringVersion.append("SELECT Round(mining.mp_2608_n_0_32, 2) AS INVERTER_1, "
					+ "       Round(mining.mp_2609_n_0_32, 2) AS INVERTER_2, "
					+ "       mining.app_version AS CPU_VER "
					+ "FROM   gets_tool_mp_mining mining, "
					+ "       gets_tool_fault fault, "
					+ "       gets_rmd_cust_rnh_rn_v grcrrv "
					+ "WHERE  grcrrv.vehicle_objid = fault.fault2vehicle "
					+ "       AND fault.objid = mining.parm2fault "
					+ "       AND grcrrv.vehicle_hdr = :customerId "
					+ "       AND grcrrv.vehicle_no = :vehicleNo "
					+ "       AND fault.created_by = 'MINING_PROCESS' "
					+ "       AND rownum = 1 "
					+ "ORDER  BY mining.creation_date DESC " ) ;
			
			
			Query queryVersion = session.createSQLQuery(queryStringVersion.toString());
			queryVersion.setResultTransformer(new AliasToEntityMapResultTransformer());
			queryVersion.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
			queryVersion.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
			List<Map<String, Object>> resultVersion = null;
			resultVersion = queryVersion.list();
			Map<String,String> softwareVer = new LinkedHashMap<String, String>();
			if(RMDCommonUtility.isCollectionNotEmpty(resultVersion)){
				for (final Map<String, Object> reportsVersion : resultVersion) {
					if(RMDCommonUtility.convertObjectToString(reportsVersion.get("CPU_VER")) != null){
						String ver = this.cpuSoftwareVer(RMDCommonUtility.convertObjectToString(reportsVersion.get("CPU_VER")));
					    softwareVer.put("CPU",ver); 
					}
					if(RMDCommonUtility.convertObjectToString(reportsVersion.get("INVERTER_1")) != null)
						softwareVer.put("Inverter 1",RMDCommonUtility.convertObjectToString(reportsVersion.get("INVERTER_1")));
					if(RMDCommonUtility.convertObjectToString(reportsVersion.get("INVERTER_2")) != null)
						softwareVer.put("Inverter 2",RMDCommonUtility.convertObjectToString(reportsVersion.get("INVERTER_2")));
				}
			} 
			truckInfo.setSoftware(softwareVer);
			
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return truckInfo;
	}

	@Override
	public ComStatusVO getComStatus(OHVReportsRxRequestVO comStatusRequestVO) {
		ComStatusVO commStatus = new ComStatusVO();
		Session session = null;
		String vehicleObjId = null;
		try {
            session = getHibernateSession();
            StringBuilder vehicleQuery = new StringBuilder();            
            vehicleQuery.append("select CUST_NAME, VEHICLE_NO, VEHICLE_OBJID from gets_rmd_cust_rnh_rn_v rnv "
            		+ " where ORG_ID = :customerId AND VEHICLE_NO =:vehicleNo AND rnv.model_family_v = 'OHV'");
            
            Query vehQuery = session.createSQLQuery(vehicleQuery.toString());
            vehQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
            vehQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, comStatusRequestVO.getMineId());
            vehQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, comStatusRequestVO.getTruckId());
            List<Map<String,Object>> resultList = vehQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					vehicleObjId = RMDCommonUtility.convertObjectToString(reportsVO.get("VEHICLE_OBJID"));
				}
            }
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_INFO);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		Map<String, String> bomElements = getBOMElements(comStatusRequestVO.getMineId(), comStatusRequestVO.getTruckId());
		commStatus.setEoaEquip(bomElements.get(RMDCommonConstants.OHV_BOM_COM_TYPE)== null? "" : bomElements.get(RMDCommonConstants.OHV_BOM_COM_TYPE));
		int hcCount = getHealthCheckCount(comStatusRequestVO.getMineId(), comStatusRequestVO.getTruckId());
		int incCount = getIncidentCount(comStatusRequestVO.getMineId(), comStatusRequestVO.getTruckId());
		commStatus.setHealthReports(String.valueOf(hcCount));
		commStatus.setMessageReceived(String.valueOf(incCount));
		return commStatus;
	}
	
	public Map<String, String> getBOMElements(String mineId, String truckId){
		Session session = null;
		Map<String, String> bomElements = new HashMap<String, String>();
		try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
			queryString.append("SELECT mbom.config_item AS CONFIG_ITEM, vehcfg.current_version AS VALUE "
					+ "FROM   gets_rmd_vehcfg vehcfg,  "
					+ "gets_rmd_cust_rnh_rn_v loco, "
					+ "gets_rmd_master_bom mbom, "
					+ "gets_rmd_vehicle veh "
					+ "WHERE  vehcfg2master_bom = mbom.objid "
					+ "AND loco.vehicle_objid = veh_cfg2vehicle "
					+ "AND mbom.bom_status = 'Y' "
					+ "AND veh.objid = loco.vehicle_objid "
					+ "AND loco.org_id = :customerId "
					+ "AND loco.vehicle_no = :vehicleNo "
					+ "AND veh_cfg2vehicle = veh.objid "
					+ "AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg "
					+ "AND mbom.config_item IN ('Customer Model','Controller','Com type' )");
			
			Query query = session.createSQLQuery(queryString.toString());
			query.setResultTransformer(new AliasToEntityMapResultTransformer());
			query.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			query.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckId);
			List<Map<String,Object>> resultList = null;
			resultList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Map<String, Object> reportsVO : resultList) {
					bomElements.put(RMDCommonUtility.convertObjectToString(reportsVO.get("CONFIG_ITEM")), RMDCommonUtility.convertObjectToString(reportsVO.get("VALUE")));
				}
			}
        } catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return bomElements;		
	}
	
	private String getMineStatus(int currentRedCount, int currentYellowCount, int totalCount, String mineId) {
		String status = "G";
		if(totalCount > 0){
			double redPercent = ((double)currentRedCount/totalCount)*100;
			if(redPercent > 10) {//>10% of trucks are RED -> R
				status = "R";
			} else if ( redPercent > 5 || (currentYellowCount/totalCount)*100 > 10) { //>5% of trucks are RED -> Y OR  >10% of trucks are yellow -> Y
				status = "Y";
			}
		}
		if(! status.equalsIgnoreCase("R")) {
		Session session = null;
		try {
			session = getHibernateSession();
			StringBuilder mineStatusQuery = new StringBuilder();
			mineStatusQuery.append("SELECT TBO.s_org_id, "
					+ "       fdbk.cust_fdbk2vehicle, "
					+ "       CASERECOM.urgency, "
					+ "       Count (CASERECOM.urgency) "
					+ "FROM   table_case C, "
					+ "       gets_sd_recom RECOM, "
					+ "       gets_sd_case_recom CASERECOM, "
					+ "       gets_sd_recom_delv DELV, "
					+ "       table_site_part TSP, "
					+ "       gets_sd_cust_fdbk FDBK, "
					+ "       gets_rmd_vehicle VEH, "
					+ "       gets_rmd_veh_hdr VEHHDR, "
					+ "       table_bus_org TBO "
					+ "WHERE  DELV.recom_delv2case = C.objid "
					+ "       AND DELV.recom_delv2recom = RECOM.objid "
					+ "       AND CASERECOM.case_recom2case = C.objid "
					+ "       AND CASERECOM.case_recom2recom = RECOM.objid "
					+ "       AND TBO.s_org_id = :customerId "
					+ "       AND case_prod2site_part = TSP.objid "
					+ "       AND TSP.objid = VEH.vehicle2site_part "
					+ "       AND VEH.vehicle2veh_hdr = VEHHDR.objid "
					+ "       AND VEHHDR.veh_hdr2busorg = TBO.objid "
					+ "       AND FDBK.cust_fdbk2case = C.objid "
					+ "       AND FDBK.rx_close_date IS NULL "
					+ "       AND FDBK.objid = DELV.recom_delv2cust_fdbk "
					+ "       AND ( delv.creation_date > sysdate - 7 "
					+ "              OR ( ( delv.delv_date > sysdate - 7 ) "
					+ "                   AND ( caserecom.reissue_flag = 'Y' ) ) ) "
					+ "       AND CASERECOM.urgency = 'R' "
					+ "GROUP  BY fdbk.cust_fdbk2vehicle, "
					+ "          CASERECOM.urgency, "
					+ "          TBO.s_org_id "
					+ "HAVING Count(CASERECOM.urgency) >= 3");
			
			Query mineStatQuery = session.createSQLQuery(mineStatusQuery.toString());
			mineStatQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			mineStatQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			List<Map<String,Object>> resultList = null;
			resultList = mineStatQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				if(resultList.size() >= 2){
					status = "R";
				} else {
					status = "Y";
				}
			}
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
         }
		}
		return status;
	}
	
	private String getCommunicationStatus(int blueCount, int totalCount){
		String status = "B";
		if(totalCount > 0) {
			double commPercentage = ((double)blueCount/totalCount) *100;
			if(commPercentage >= 25){
				status = "R";
			} else if(commPercentage >= 10) {
				status = "Y";
			}
		}
		return status;
	}
	
	private int getHealthCheckCount(String mineId, String vehicleNo) {
		int hcCount = 0;
		Session session = null;
		try {
			session = getDWHibernateSession();
			StringBuilder healthCheckQuery = new StringBuilder();
			if(vehicleNo != null && vehicleNo != "") {
				healthCheckQuery.append("SELECT Count(*) AS HC_COUNT ");
				healthCheckQuery.append("FROM   gets_dw_eoa_loader_log ");
				healthCheckQuery.append("WHERE  cust_id = :customerId ");
				healthCheckQuery.append("       AND vehicle_number = :vehicleNo ");
				healthCheckQuery.append("       AND message_type = 'OHV_HC' ");
				healthCheckQuery.append("       AND log_time > sysdate - 1 ");
			}
			else {
				healthCheckQuery.append("SELECT Count(*) AS HC_COUNT ");
				healthCheckQuery.append("FROM   gets_dw_eoa_loader_log ");
				healthCheckQuery.append("WHERE  cust_id = :customerId ");
				healthCheckQuery.append("       AND message_type = 'OHV_HC' ");
				healthCheckQuery.append("       AND log_time > sysdate - 1 ");
			}
			Query hcQuery = session.createSQLQuery(healthCheckQuery.toString());
			hcQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			if(vehicleNo != null && vehicleNo != "") {
				hcQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
				hcQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, vehicleNo);
			}
			else
				hcQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			
			List<Map<String,Object>> resultList = null;
			resultList = hcQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for(Map<String, Object> result : resultList){
					hcCount = RMDCommonUtility.convertObjectToInt(result.get("HC_COUNT"));
				}
			}			
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return hcCount;
	}
	
	private int getIncidentCount (String mineId, String vehicleNo) {
		int incCount = 0;
		Session session = null;
		try {
			session = getDWHibernateSession();
			StringBuilder incidentQuery = new StringBuilder();
			if(vehicleNo != null && vehicleNo != "") {
				incidentQuery.append("SELECT Count(*) AS INC_COUNT ");
				incidentQuery.append("FROM   gets_dw_eoa_loader_log ");
				incidentQuery.append("WHERE  cust_id = :customerId ");
				incidentQuery.append("       AND vehicle_number = :vehicleNo ");
				incidentQuery.append("       AND message_type = 'OHV_INC' ");
				incidentQuery.append("       AND log_time > sysdate - 1 ");
			}
			else {
				incidentQuery.append("SELECT Count(*) AS INC_COUNT ");
				incidentQuery.append("FROM   gets_dw_eoa_loader_log ");
				incidentQuery.append("WHERE  cust_id = :customerId ");
				incidentQuery.append("       AND message_type = 'OHV_INC' ");
				incidentQuery.append("       AND log_time > sysdate - 1 ");
			}
			Query incQuery = session.createSQLQuery(incidentQuery.toString());
			incQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			if(vehicleNo != null && vehicleNo != "") {
				incQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
				incQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, vehicleNo);
			}
			else
				incQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			
			List<Map<String,Object>> resultList = null;
			resultList = incQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for(Map<String, Object> result : resultList){
					incCount = RMDCommonUtility.convertObjectToInt(result.get("INC_COUNT"));
				}
			}			
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return incCount;
	}

	@Override
	public TruckGraphVO getTruckGraphData(OHVReportsGraphRequestVO graphDataRequest) {
		TruckGraphVO truckGraphData = new TruckGraphVO();
		Session session = null;
		CallableStatement stmt = null;
		Connection con = null;
		ResultSet ohvParamResult = null;
		try {
			session = getHibernateSession();
			con = session.connection();
			con.setAutoCommit(false);
			String sql = "call GETS_RMD.GETS_RMD_OHV_REPORTS_PKG.GETS_RMD_GET_OHV_PARAMETER(?,?,?,?) ";
			stmt = con.prepareCall(sql);			
			stmt.setString(1, graphDataRequest.getMineId());
			stmt.setString(2, graphDataRequest.getTruckId());
			stmt.setInt(3, graphDataRequest.getPeriod());
			stmt.registerOutParameter(4, OracleTypes.CURSOR);
			stmt.executeQuery();
			ohvParamResult = (ResultSet)stmt.getObject(4);
			final int resultSize = ohvParamResult.getFetchSize();
			List<ReportsTimeSeriesVO> loadList = new ArrayList<ReportsTimeSeriesVO>();
			List<ReportsTimeSeriesVO> engineOpList = new ArrayList<ReportsTimeSeriesVO>();
			List<ReportsTimeSeriesVO> engineIdleList = new ArrayList<ReportsTimeSeriesVO>();
			List<ReportsTimeSeriesVO> overloadList = new ArrayList<ReportsTimeSeriesVO>();
			List<ReportsTimeSeriesVO> avgHpList = new ArrayList<ReportsTimeSeriesVO>();			
			if(resultSize > 0){
				
				while(ohvParamResult.next()){
					String time = RMDCommonUtility.checkData(ohvParamResult.getString(6));
					String load = ohvParamResult.getString(1);
					String engineOpTime = ohvParamResult.getString(2);
					String engineIdleTime = ohvParamResult.getString(3);
					String overload = ohvParamResult.getString(4);
					String avgHp = ohvParamResult.getString(5);
					ReportsTimeSeriesVO loadsData = new ReportsTimeSeriesVO();
					loadsData.setTimestamp(time);
					loadsData.setValue(Double.valueOf(load));
					loadList.add(loadsData);
					
					ReportsTimeSeriesVO engineOpData = new ReportsTimeSeriesVO();
					engineOpData.setTimestamp(time);
					engineOpData.setValue(Double.valueOf(engineOpTime) / 3600);
					engineOpList.add(engineOpData);
					
					ReportsTimeSeriesVO engineIdleTimeData = new ReportsTimeSeriesVO();
					engineIdleTimeData.setTimestamp(time);
					engineIdleTimeData.setValue(Double.valueOf(engineIdleTime) / 3600);
					engineIdleList.add(engineIdleTimeData);
					
					ReportsTimeSeriesVO overloadData = new ReportsTimeSeriesVO();
					overloadData.setTimestamp(time);
					overloadData.setValue(Double.valueOf(overload));
					overloadList.add(overloadData);
					
					ReportsTimeSeriesVO avgHPData = new ReportsTimeSeriesVO();
					avgHPData.setTimestamp(time);
					avgHPData.setValue(Double.valueOf(avgHp));
					avgHpList.add(avgHPData);					
				}
			}
			truckGraphData.setLoads(loadList);
			truckGraphData.setEngineOpHrs(engineOpList);
			truckGraphData.setEngineIdleTime(engineIdleList);
			truckGraphData.setOverloads(overloadList);
			truckGraphData.setAverageHP(avgHpList);
			con.commit();
		} catch (RMDDAOConnectionException ex) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	try {
				con.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
        	try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
            releaseSession(session);
        }
		return truckGraphData;
	}
	
	public List<String> getTruckParam(OHVReportsRxRequestVO truckInfoVO) {
		Session session = null;
		List<Map<String,Object>> hcObjectList = null;
		List<Map<String,Object>> fuelObjectList = null;
		String paramName = RMDCommonConstants.PARAM_NAME;
		String unitName = RMDCommonConstants.UNIT_NAME;
		List<String> paramNameList = Arrays.asList(paramName.split(","));
		List<String> unitNameList = Arrays.asList(unitName.split(","));
		List<String> contentList = new ArrayList<String>();

		try {
			session = getHibernateSession();
			StringBuilder faultQuery = new StringBuilder();
			faultQuery.append("SELECT Min(fault.objid) AS leastObjId, "
					+ "       Max(fault.objid) AS latestObjId "
					+ "FROM   gets_tool_fault fault "
					+ "       JOIN gets_rmd_cust_rnh_rn_v grcrrv "
					+ "         ON grcrrv.vehicle_objid = fault.fault2vehicle "
					+ "       JOIN gets_tool_mp_mining mining "
					+ "         ON fault.objid = mining.parm2fault "
					+ "WHERE  fault.record_type = 'OHVFLT' "
					+ "       AND grcrrv.vehicle_hdr = :customerId "
					+ "       AND grcrrv.vehicle_no = :vehicleNo "
					+ "       AND fault.offboard_load_date BETWEEN "
					+ "           To_date(:fromDate, 'MM/DD/YYYY HH24:MI:SS') AND "
					+ "               To_date(:toDate, 'MM/DD/YYYY HH24:MI:SS') "
					+ "       AND fault.created_by = 'MINING_PROCESS' "
					+ "       AND ( mp_2469_n_0_32 IS NOT NULL "
					+ "              OR mp_2470_n_0_32 IS NOT NULL "
					+ "              OR mp_2471_n_0_32 IS NOT NULL "
					+ "              OR MP_2554_N_0_32 IS NOT NULL "
					+ "              OR MP_2555_N_0_32 IS NOT NULL "
					+ "              OR mp_2483_n_0_32 IS NOT NULL "
					+ "              OR mp_2498_n_0_32 IS NOT NULL "
					+ "              OR mp_2572_n_0_32 IS NOT NULL ) ");
			
			Query fltQuery = session.createSQLQuery(faultQuery.toString());
			fltQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			fltQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
			fltQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
			fltQuery.setParameter(RMDCommonConstants.OHV_FROM_DATE, truckInfoVO.getFromDate());
			fltQuery.setParameter(RMDCommonConstants.OHV_TO_DATE, truckInfoVO.getToDate());
            List<Map<String,Object>> faultResultList = null;
            faultResultList = fltQuery.list();
            String leastObjid = null;
            String latestObjid = null;
            StringBuilder mpQueryString = new StringBuilder();
			mpQueryString.append(" SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, preShift ");
			mpQueryString.append(" FROM (SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, ");
			mpQueryString.append(" ( mp_2537_n_0_32 + mp_2538_n_0_32 + mp_2539_n_0_32 )  AS preShift ");
			mpQueryString.append(" FROM   gets_tool_mp_mining mine ");
			mpQueryString.append(" WHERE	parm2fault = :paramNo ");
			mpQueryString.append(" AND mine.created_by = 'MINING_PROCESS' ");
			mpQueryString.append(" AND ( mp_2469_n_0_32 IS NOT NULL OR mp_2470_n_0_32 IS NOT NULL  OR mp_2471_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR MP_2554_N_0_32 IS NOT NULL OR MP_2555_N_0_32 IS NOT NULL OR mp_2483_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR mp_2498_n_0_32 IS NOT NULL OR mp_2572_n_0_32 IS NOT NULL ) ");
			if (RMDCommonUtility.isCollectionNotEmpty(faultResultList)) {
				leastObjid = RMDCommonUtility.convertObjectToString(faultResultList.get(0).get("LEASTOBJID"));
				latestObjid = RMDCommonUtility.convertObjectToString(faultResultList.get(0).get("LATESTOBJID"));
			if(leastObjid != null && latestObjid != null) {			
				Query faultObjList = session.createSQLQuery(mpQueryString.toString() + " order by objid) where rownum =1 " );
				faultObjList.setParameter(RMDCommonConstants.OHV_PARAM_ID, leastObjid);
				List<Object[]> leastValueParamList = faultObjList.list();
				
				Query latestQuery = session.createSQLQuery(mpQueryString.toString() + " order by objid desc) where rownum =1 " );
				latestQuery.setParameter(RMDCommonConstants.OHV_PARAM_ID, latestObjid);
				List<Object[]> latestValueParamList = latestQuery.list();
			
			if (RMDCommonUtility.isCollectionNotEmpty(leastValueParamList) && RMDCommonUtility.isCollectionNotEmpty(latestValueParamList)) {
				List<String> leastValueList = new ArrayList<String>();
				List<String> latestValueList = new ArrayList<String>();
				Object[] leastResultRow = leastValueParamList.get(0);
				Object[] latestResultRow = latestValueParamList.get(0);
				for(int i = 0; i< leastResultRow.length; i++){
					if(leastResultRow[i] !=null){
						leastValueList.add(leastResultRow[i] .toString());
					}else{ 
						leastValueList.add(ZERO_STRING);
					}
					
					if(latestResultRow[i] !=null){
						latestValueList.add(latestResultRow[i].toString());
					}else{
						latestValueList.add(ZERO_STRING);
					}
				}

				List calculatedValues = this.paramCalculation(unitNameList, leastValueList, latestValueList);

				if(!calculatedValues.isEmpty()) {
					for(int cnt=0; cnt < calculatedValues.size(); cnt++) {
						String calcValue = calculatedValues.get(cnt).toString().trim();
						if(ZERO_STRING.equals(calcValue))
							contentList.add(ZERO_STRING);
						else 
							contentList.add(String.format("%.2f",Double.parseDouble(calcValue)));
					}
				}
			}
				StringBuilder queryHCString = new StringBuilder();
				queryHCString.append("SELECT ob_req_status AS STATUS, "
						+ "       Count(1) AS HC_COUNT "
						+ "FROM   gets_omi_outbndmsg_hist hst "
						+ "       JOIN gets_rmd_cust_rnh_rn_v grcrrv "
						+ "         ON grcrrv.vehicle_objid = hst.ob_msg_hist2rmd_veh "
						+ "WHERE  grcrrv.vehicle_hdr = :customerId "
						+ "       AND grcrrv.vehicle_no = :vehicleNo "
						+ "       AND hst.ob_req_status IN ( 'FAILED', 'LOADED' ) "
						+ "       AND hst.ob_req_status_date > sysdate -1 "
						+ "GROUP  BY hst.ob_req_status "
						+ "ORDER  BY hst.ob_req_status");

				Query hcObjList = session.createSQLQuery(queryHCString.toString());
				hcObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
				hcObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
				hcObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
				hcObjectList = hcObjList.list();
				String status;
				int loadedCount = 0;
				int failedCount = 0;
				int totalCount = 0;
				if (RMDCommonUtility.isCollectionNotEmpty(hcObjectList)) {
					for ( Map<String, Object> hcObj : hcObjectList) {
						status = RMDCommonUtility.convertObjectToString(hcObj.get("STATUS"));
						if("LOADED".equalsIgnoreCase(status)){
							loadedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
						} else {
							failedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
						}                                        
					}
				}
				totalCount = loadedCount+failedCount;

				int fuelSaver =0;
				int contRetard=0;
				StringBuilder queryFuelString = new StringBuilder();
				queryFuelString.append("SELECT Sum(mp_2552_n_0_32) AS FUEL_COUNT, Sum(mp_2393_n_0_32) AS CONTINUOUS_RETARD "
						+ "FROM   gets_tool_mp_mining mining, "
						+ "       gets_tool_fault fault, "
						+ "       gets_rmd_cust_rnh_rn_v grcrrv "
						+ "WHERE  grcrrv.vehicle_objid = fault.fault2vehicle "
						+ "       AND fault.objid = mining.parm2fault "
						+ "       AND grcrrv.vehicle_hdr = :customerId "
						+ "       AND grcrrv.vehicle_no = :vehicleNo "
						+ "       AND fault.created_by = 'MINING_PROCESS' "
						+ "       AND fault.offboard_load_date > sysdate -1 ");

				Query fuelObjList = session.createSQLQuery(queryFuelString.toString());
				fuelObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
				fuelObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
				fuelObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
				fuelObjectList = fuelObjList.list();
				if (RMDCommonUtility.isCollectionNotEmpty(fuelObjectList)) {
					for (Map<String, Object> fuelObj : fuelObjectList) {
						if (null !=fuelObj && fuelObj.size() > 0) {
							fuelSaver = RMDCommonUtility.convertObjectToInt(fuelObj.get("FUEL_COUNT"));
							contRetard = RMDCommonUtility.convertObjectToInt(fuelObj.get("CONTINUOUS_RETARD"));
						}
					}
				}
				
					contentList.add(Integer.toString(failedCount));
					contentList.add(Integer.toString(loadedCount));
					contentList.add(ZERO_STRING);
					contentList.add(ZERO_STRING);
					contentList.add(Integer.toString(failedCount+loadedCount));
					contentList.add(Integer.toString(fuelSaver));
					contentList.add(Integer.toString(contRetard));

			} else {
				for(int i=0; i<paramNameList.size();i++){
					contentList.add(ZERO_STRING);
				}
			}
			
			} 
			
			else {
				for(int i=0; i<paramNameList.size();i++){
					contentList.add(ZERO_STRING);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			LOG.error(ex, ex);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e, e);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return contentList;
	}



	private List<Double> paramCalculation(List<String> unitNameList, List<String> leastList, List<String> latestList) {
		List<Double> finalList = new ArrayList<Double>();
		for(int i=0; i < unitNameList.size(); i++){
			String unitName = unitNameList.get(i).trim();
			String latest = latestList.get(i).trim();
			String least  = leastList.get(i).trim();
			if("null".equalsIgnoreCase(latest) || "null".equalsIgnoreCase(least) || ZERO_STRING.equals(latest) || ZERO_STRING.equals(least)){
				finalList.add((double)0);
			} else { 
				NumberFormat formatter = new DecimalFormat("#.00");
				if("Hrs".equals(unitName)) {					
					String calcValue = formatter.format((Double.parseDouble(latest) - Double.parseDouble(least)) / 3600);
					if(ZERO_STRING.equals(calcValue)){
						finalList.add((double) 0); 
					}else {
						finalList.add(Double.parseDouble(calcValue));
					}
				}else {
					String calcValue = formatter.format(Double.parseDouble(latest) - Double.parseDouble(least));
					if(ZERO_STRING.equals(calcValue)){
						finalList.add((double) 0); 
					}else {
						finalList.add(Double.parseDouble(calcValue));
					}
				}
			}
		}
		return finalList;
	}
	
	
	public String cpuSoftwareVer(String softwareVer) {
		int verNumber;
		int asciBase = 96;
		StringBuilder ver = new StringBuilder();
		if(! softwareVer.isEmpty() && softwareVer.length() >=4){
		ver.append(softwareVer.charAt(0)).append(".").append(softwareVer.substring(1, 3));
		if (softwareVer.length() == 4 && softwareVer.charAt(3) != ' ' ){
		verNumber = Integer.valueOf(softwareVer.substring(3));
		ver.append(Character.toString((char) (asciBase + verNumber)));
		  }  else if (softwareVer.length() >=4 && softwareVer.charAt(3) != ' ' && softwareVer.charAt(4) != ' ' && Integer.valueOf(softwareVer.substring(3,5)) <= 26 ){
			 verNumber = Integer.valueOf(softwareVer.substring(3,5));
				ver.append(Character.toString((char) (asciBase + verNumber)));
		  }
		}
		return ver.toString() ;
	}
	
	
	public Map<String,List<String>> getTruckParamList(OHVReportsRxRequestVO truckInfoVO) {
		Session session = null;
		String paramName = RMDCommonConstants.PARAM_NAME;
		String unitName = RMDCommonConstants.UNIT_NAME;
		String paramNameMP = RMDCommonConstants.PARAM_NAME_MP;
		List<String> paramNameMPList = Arrays.asList(paramNameMP.split(","));
		List<String> paramNameList = Arrays.asList(paramName.split(","));
		List<String> unitNameList = Arrays.asList(unitName.split(","));
		Map<String, List<String>> contentListMap = new HashMap<String, List<String>>();

		try {
			session = getHibernateSession();
			StringBuilder faultQuery = new StringBuilder();
			faultQuery.append(" SELECT MIN_FAULT_OBJID, MAX_FAULT_OBJID, DURATION, TO_CHAR( START_DATE, 'MM/DD/YYYY HH24:MI:SS') AS START_DATE , ");
			faultQuery.append(" TO_CHAR( END_DATE, 'MM/DD/YYYY HH24:MI:SS') AS END_DATE FROM   gets_rmd.gets_rmd_get_fault_objid ");
			faultQuery.append(" WHERE  vehicle_hdr = :customerId AND vehicle_no = :vehicleNo AND duration <> 'FULL' ");
			
			Query fltQuery = session.createSQLQuery(faultQuery.toString());
			fltQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			fltQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
			fltQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
            List<Map<String,Object>> faultResult = fltQuery.list();
            String leastObjid = null;
            String latestObjid = null;
            String fromDate = null;
            String toDate = null;
            String duration = null;
            List<String> contentList = null;
            StringBuilder mpQueryString = new StringBuilder();
			mpQueryString.append(" SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, preShift ");
			mpQueryString.append(" FROM (SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, ");
			mpQueryString.append(" ( mp_2537_n_0_32 + mp_2538_n_0_32 + mp_2539_n_0_32 )  AS preShift ");
			mpQueryString.append(" FROM   gets_tool_mp_mining mine ");
			mpQueryString.append(" WHERE	parm2fault = :paramNo ");
			mpQueryString.append(" AND mine.created_by = 'MINING_PROCESS' ");
			mpQueryString.append(" AND ( mp_2469_n_0_32 IS NOT NULL OR mp_2470_n_0_32 IS NOT NULL  OR mp_2471_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR MP_2554_N_0_32 IS NOT NULL OR MP_2555_N_0_32 IS NOT NULL OR mp_2483_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR mp_2498_n_0_32 IS NOT NULL OR mp_2572_n_0_32 IS NOT NULL ) ");
			
			StringBuilder queryHCString = new StringBuilder();
			queryHCString.append("SELECT ob_req_status AS STATUS, Count(1) AS HC_COUNT FROM gets_omi_outbndmsg_hist hst JOIN gets_rmd_cust_rnh_rn_v grcrrv ");
			queryHCString.append(" ON grcrrv.vehicle_objid = hst.ob_msg_hist2rmd_veh WHERE  grcrrv.vehicle_hdr = :customerId AND grcrrv.vehicle_no = :vehicleNo ");
			queryHCString.append(" AND hst.ob_req_status IN ( 'FAILED', 'LOADED' ) AND hst.ob_req_status_date BETWEEN to_date(:fromDate,'MM/DD/YYYY HH24:MI:SS') ");
			queryHCString.append(" AND to_date(:toDate,'MM/DD/YYYY HH24:MI:SS') GROUP  BY hst.ob_req_status ORDER  BY hst.ob_req_status");

			StringBuilder queryFuelString = new StringBuilder();
			queryFuelString.append("SELECT Sum(mp_2552_n_0_32) AS FUEL_COUNT, Sum(mp_2393_n_0_32) AS CONTINUOUS_RETARD FROM   gets_tool_mp_mining mining, ");
			queryFuelString.append(" gets_tool_fault fault, gets_rmd_cust_rnh_rn_v grcrrv WHERE  grcrrv.vehicle_objid = fault.fault2vehicle ");
			queryFuelString.append(" AND fault.objid = mining.parm2fault AND grcrrv.vehicle_hdr = :customerId AND grcrrv.vehicle_no = :vehicleNo ");
			queryFuelString.append(" AND fault.created_by = 'MINING_PROCESS' AND fault.offboard_load_date BETWEEN to_date(:fromDate,'MM/DD/YYYY HH24:MI:SS') ");
			queryFuelString.append(" AND to_date(:toDate,'MM/DD/YYYY HH24:MI:SS')");
			
			if (RMDCommonUtility.isCollectionNotEmpty(faultResult)) {
				for (final Map<String,Object> faultResultObj : faultResult) {
					contentList = new ArrayList<String>();
					leastObjid = RMDCommonUtility.convertObjectToString(faultResultObj.get("MIN_FAULT_OBJID"));
					latestObjid = RMDCommonUtility.convertObjectToString(faultResultObj.get("MAX_FAULT_OBJID"));
				    fromDate = RMDCommonUtility.convertObjectToString(faultResultObj.get("START_DATE"));
				    toDate = RMDCommonUtility.convertObjectToString(faultResultObj.get("END_DATE"));
				    duration = RMDCommonUtility.convertObjectToString(faultResultObj.get("DURATION"));
					if(leastObjid == null || latestObjid == null) {
						for(int i=0; i<paramNameList.size();i++){
							contentList.add(ZERO_STRING);
						}
					} else {
						Query faultObjList = session.createSQLQuery(mpQueryString.toString() + " order by objid) where rownum =1 " );
						faultObjList.setParameter(RMDCommonConstants.OHV_PARAM_ID, leastObjid);
						List<Object[]> leastValueParamList = faultObjList.list();
						
						Query latestQuery = session.createSQLQuery(mpQueryString.toString() + " order by objid desc) where rownum =1 " );
						latestQuery.setParameter(RMDCommonConstants.OHV_PARAM_ID, latestObjid);
						List<Object[]> latestValueParamList = latestQuery.list();
					
						if (RMDCommonUtility.isCollectionNotEmpty(leastValueParamList) && RMDCommonUtility.isCollectionNotEmpty(latestValueParamList)) {
							List<String> leastValueList = new ArrayList<String>();
							List<String> latestValueList = new ArrayList<String>();
							Object[] leastResultRow = leastValueParamList.get(0);
							Object[] latestResultRow = latestValueParamList.get(0);
							for(int i = 0; i< leastResultRow.length; i++){
								if(leastResultRow[i] == null){
									leastValueList.add(ZERO_STRING);
								}else{ 
									leastValueList.add(leastResultRow[i] .toString());
								}
								
								if(latestResultRow[i] == null){
									latestValueList.add(ZERO_STRING);
								}else{
									latestValueList.add(latestResultRow[i].toString());
								}
							}
			
							List<Double> calculatedValues = this.paramCalculation(unitNameList, leastValueList, latestValueList);
			
							for(Double calcValue : calculatedValues) {
								if(ZERO_STRING.equals(calcValue.toString())){
									contentList.add(ZERO_STRING);
								}else {
									contentList.add(String.format("%.2f", calcValue));
								}
							}
							
						  } else {
							  for(int i=0; i<paramNameMPList.size();i++){
								  contentList.add(ZERO_STRING);
							  }
						  }
						
						Query hcObjList = session.createSQLQuery(queryHCString.toString());
						hcObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
						hcObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
						hcObjList.setParameter(RMDCommonConstants.OHV_FROM_DATE, fromDate);
						hcObjList.setParameter(RMDCommonConstants.OHV_TO_DATE, toDate);
						hcObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
						List<Map<String,Object>> hcObjectList  = hcObjList.list();
						String status;
						int loadedCount = 0;
						int failedCount = 0;
						if (RMDCommonUtility.isCollectionNotEmpty(hcObjectList)) {
							for ( Map<String, Object> hcObj : hcObjectList) {
								status = RMDCommonUtility.convertObjectToString(hcObj.get("STATUS"));
								if("LOADED".equalsIgnoreCase(status)){
									loadedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
								} else {
									failedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
								}                                        
							}
						}
		
						String fuelSaver = ZERO_STRING;
						String contRetard = ZERO_STRING;
						Query fuelObjList = session.createSQLQuery(queryFuelString.toString());
						fuelObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
						fuelObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
						fuelObjList.setParameter(RMDCommonConstants.OHV_FROM_DATE, fromDate);
						fuelObjList.setParameter(RMDCommonConstants.OHV_TO_DATE, toDate);
						fuelObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
						List<Map<String,Object>> fuelObjectList = fuelObjList.list();
						if (RMDCommonUtility.isCollectionNotEmpty(fuelObjectList)) {
							Map<String, Object> fuelObj = fuelObjectList.get(0);
							if(fuelObj != null){
								fuelSaver = RMDCommonUtility.convertObjectToString(fuelObj.get("FUEL_COUNT"));
								contRetard = RMDCommonUtility.convertObjectToString(fuelObj.get("CONTINUOUS_RETARD"));
							}
						}
						contentList.add(Integer.toString(failedCount));
						contentList.add(Integer.toString(loadedCount));
						contentList.add(ZERO_STRING);
						contentList.add(ZERO_STRING);
						contentList.add(Integer.toString(failedCount+loadedCount));
						if(fuelSaver == null || fuelSaver.trim().equals("")){
							contentList.add(ZERO_STRING);
						}else{
							contentList.add(fuelSaver);
						}
						if(contRetard == null || contRetard.trim().equals("")){
							contentList.add(ZERO_STRING);
						}else{
							contentList.add(contRetard);
						}
						
					}
					contentListMap.put(duration, contentList);
			   }
				
			 }else {
				contentList = new ArrayList<String>();
				for(int i=0; i<paramNameList.size();i++){
					contentList.add(ZERO_STRING);
				}
				contentListMap.put("LAST_MONTH", contentList);
				contentListMap.put("LAST_QUARTER", contentList);	
			}
		  }  catch (RMDDAOConnectionException ex) {
			LOG.error(ex, ex);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e, e);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return contentListMap;
	}
	
	
	
	public List<String> getTruckParamAvg (OHVReportsRxRequestVO truckInfoVO)  {
		Session session = null;
		List<Map<String,Object>> hcObjectList = null;
		List<Map<String,Object>> fuelObjectList = null;
		String paramName = RMDCommonConstants.PARAM_NAME;
		String paramNameMP = RMDCommonConstants.PARAM_NAME_MP;
		String unitName = RMDCommonConstants.UNIT_NAME;
		List<String> paramNameList = Arrays.asList(paramName.split(","));
		List<String> paramNameMPList = Arrays.asList(paramNameMP.split(","));
		List<String> unitNameList = Arrays.asList(unitName.split(","));
		List<Double> totalContentList = null;
		List<Double> totalContentSumList = new ArrayList<Double>(30);
		List<String> totalContentAvgList = new ArrayList<String>();
		try {
			session = getHibernateSession();
			Query fltQuery = session.createSQLQuery("SELECT MIN_FAULT_OBJID, MAX_FAULT_OBJID from gets_rmd.gets_rmd_get_fault_objid_mnth WHERE vehicle_hdr = :mineId AND vehicle_no = :vehicleNo ");
			fltQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			fltQuery.setParameter("mineId", truckInfoVO.getMineId());
			fltQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
            List<Map<String, Object>> faultResultList = fltQuery.list();
            String leastObjid = null;
            String latestObjid = null;
            StringBuilder mpQueryString = new StringBuilder();
			mpQueryString.append(" SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, preShift ");
			mpQueryString.append(" FROM (SELECT mp_2469_n_0_32, total_engine_running_hours, mp_2470_n_0_32, mp_2471_n_0_32, mp_2473_n_0_32, ");
			mpQueryString.append(" mp_2480_n_0_32, mp_2481_n_0_32, mp_2540_n_0_32, MP_2554_N_0_32, MP_2555_N_0_32, MP_2561_N_0_32, ");
			mpQueryString.append(" mp_2577_n_0_32, MP_2578_N_0_32, mp_2546_n_0_32, mp_2547_n_0_32, mp_2474_n_0_32, mp_2488_n_0_32, ");
			mpQueryString.append(" mp_2489_n_0_32, mp_2582_n_0_32, mp_2485_n_0_32, mp_2486_n_0_32, mp_2487_n_0_32, mp_2483_n_0_32, ");
			mpQueryString.append(" mp_2498_n_0_32, mp_2572_n_0_32, mp_2545_n_0_32, mp_2535_n_0_32, ");
			mpQueryString.append(" ( mp_2537_n_0_32 + mp_2538_n_0_32 + mp_2539_n_0_32 )  AS preShift ");
			mpQueryString.append(" FROM   gets_tool_mp_mining mine ");
			mpQueryString.append(" WHERE	parm2fault = :paramNo ");
			mpQueryString.append(" AND mine.created_by = 'MINING_PROCESS' ");
			mpQueryString.append(" AND ( mp_2469_n_0_32 IS NOT NULL OR mp_2470_n_0_32 IS NOT NULL  OR mp_2471_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR MP_2554_N_0_32 IS NOT NULL OR MP_2555_N_0_32 IS NOT NULL OR mp_2483_n_0_32 IS NOT NULL ");
			mpQueryString.append(" OR mp_2498_n_0_32 IS NOT NULL OR mp_2572_n_0_32 IS NOT NULL ) ");
			
			if (RMDCommonUtility.isCollectionNotEmpty(faultResultList)) {
				for (final Map<String, Object> faultResultObj : faultResultList) {
					leastObjid = RMDCommonUtility.convertObjectToString(faultResultObj.get("MIN_FAULT_OBJID"));
					latestObjid = RMDCommonUtility.convertObjectToString(faultResultObj.get("MAX_FAULT_OBJID"));
					if(leastObjid != null && latestObjid != null) {	
						
						Query faultObjList = session.createSQLQuery(mpQueryString.toString() + " order by objid) where rownum =1 " );
						faultObjList.setParameter(RMDCommonConstants.OHV_PARAM_ID, leastObjid);
						List<Object[]> leastValueParamList = faultObjList.list();
						
						Query latestQuery = session.createSQLQuery(mpQueryString.toString() + " order by objid desc) where rownum =1 " );
						latestQuery.setParameter(RMDCommonConstants.OHV_PARAM_ID, latestObjid);
						List<Object[]> latestValueParamList = latestQuery.list();
						
						if (RMDCommonUtility.isCollectionNotEmpty(leastValueParamList) && RMDCommonUtility.isCollectionNotEmpty(latestValueParamList)) {
							List<String> leastValueList = new ArrayList<String>();
							List<String> latestValueList = new ArrayList<String>();
							Object[] leastResultRow = leastValueParamList.get(0);
							Object[] latestResultRow = latestValueParamList.get(0);
							for(int i = 0; i< leastResultRow.length; i++){
								if(leastResultRow[i] == null){
									leastValueList.add(ZERO_STRING);
								}else{ 
									leastValueList.add(leastResultRow[i] .toString());
								}
								
								if(latestResultRow[i] == null){
									latestValueList.add(ZERO_STRING);
								}else{
									latestValueList.add(latestResultRow[i].toString());
								}
							}
							
							List<Double> calculatedValues = this.paramCalculation(unitNameList, leastValueList, latestValueList);
							
							if(totalContentList == null){
								totalContentList = calculatedValues;
							} else {
								for(int k=0; k<totalContentList.size(); k++){
									totalContentSumList.add(k, totalContentList.get(k) + calculatedValues.get(k));
								}
								totalContentList = totalContentSumList;
								totalContentSumList = new ArrayList<Double>();
							}
						}
					} else {
						for(int i=0; i<paramNameMPList.size();i++){
							totalContentAvgList.add(ZERO_STRING);
						}
					}
					
				}
				if (totalContentList != null) {
					for(int k=0; k<totalContentList.size(); k++){
						totalContentAvgList.add(k, String.format("%.2f",totalContentList.get(k) / 30));
					}
				}
				StringBuilder queryHCString = new StringBuilder();
				queryHCString.append("SELECT ob_req_status AS STATUS, Count(1) AS HC_COUNT FROM gets_omi_outbndmsg_hist hst JOIN gets_rmd_cust_rnh_rn_v grcrrv ");
				queryHCString.append(" ON grcrrv.vehicle_objid = hst.ob_msg_hist2rmd_veh WHERE  grcrrv.vehicle_hdr = :customerId AND grcrrv.vehicle_no = :vehicleNo ");
				queryHCString.append(" AND hst.ob_req_status IN ( 'FAILED', 'LOADED' ) AND hst.ob_req_status_date > sysdate -30 GROUP  BY hst.ob_req_status ORDER  BY hst.ob_req_status ");

				Query hcObjList = session.createSQLQuery(queryHCString.toString());
				hcObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
				hcObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
				hcObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
				hcObjectList = hcObjList.list();
				String status;
				int loadedCount = 0;
				int failedCount = 0;
				if (RMDCommonUtility.isCollectionNotEmpty(hcObjectList)) {
					for ( Map<String, Object> hcObj : hcObjectList) {
						status = RMDCommonUtility.convertObjectToString(hcObj.get("STATUS"));
						if("LOADED".equalsIgnoreCase(status)){
							loadedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
						} else {
							failedCount = RMDCommonUtility.convertObjectToInt(hcObj.get("HC_COUNT"));
						}                                        
					}
				}

				int fuelSaver =0;
				int contRetard=0;
				StringBuilder queryFuelString = new StringBuilder();
				queryFuelString.append("SELECT Sum(mp_2552_n_0_32) AS FUEL_COUNT, Sum(mp_2393_n_0_32) AS CONTINUOUS_RETARD FROM gets_tool_mp_mining mining, ");
				queryFuelString.append(" gets_tool_fault fault, gets_rmd_cust_rnh_rn_v grcrrv WHERE  grcrrv.vehicle_objid = fault.fault2vehicle ");
				queryFuelString.append(" AND fault.objid = mining.parm2fault AND grcrrv.vehicle_hdr = :customerId AND grcrrv.vehicle_no = :vehicleNo ");
				queryFuelString.append(" AND fault.created_by = 'MINING_PROCESS' AND fault.offboard_load_date > sysdate -30 ");

				Query fuelObjList = session.createSQLQuery(queryFuelString.toString());
				fuelObjList.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
				fuelObjList.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
				fuelObjList.setResultTransformer(new AliasToEntityMapResultTransformer());
				fuelObjectList = fuelObjList.list();
				if (RMDCommonUtility.isCollectionNotEmpty(fuelObjectList)) {
					for (Map<String, Object> fuelObj : fuelObjectList) {
						if (null !=fuelObj && fuelObj.size() > 0) {
							fuelSaver = RMDCommonUtility.convertObjectToInt(fuelObj.get("FUEL_COUNT"));
							contRetard = RMDCommonUtility.convertObjectToInt(fuelObj.get("CONTINUOUS_RETARD"));
						}
					}
				}

				totalContentAvgList.add(Integer.toString(failedCount / 30));
				totalContentAvgList.add(Integer.toString(loadedCount / 30));
				totalContentAvgList.add(ZERO_STRING);
				totalContentAvgList.add(ZERO_STRING);
				totalContentAvgList.add(Integer.toString((failedCount+loadedCount) / 30));
				totalContentAvgList.add(Integer.toString(fuelSaver / 30));
				totalContentAvgList.add(Integer.toString(contRetard / 30));
			 } else {
					for(int i=0; i<paramNameList.size();i++){
						totalContentAvgList.add(ZERO_STRING);
					}	
				}
			} catch (RMDDAOConnectionException ex) {
	
			LOG.error(ex, ex);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e, e);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return totalContentAvgList;
	}
	
	
	private boolean isTruckCommunication  (String mineId, String vehicleNo) {
		int count = 0;
		boolean isTruckCommunication = false;
		Session session = null;
		try {
			session = getDWHibernateSession();
			StringBuilder communicationQuery = new StringBuilder();
				communicationQuery.append("SELECT Count(*) AS INC_COUNT ");
				communicationQuery.append("FROM   gets_dw_eoa_loader_log ");
				communicationQuery.append("WHERE  cust_id = :customerId ");
				communicationQuery.append("       AND vehicle_number = :vehicleNo ");
				communicationQuery.append("       AND message_type in ('OHV_INC', 'OHV_HC', 'OHV_DP' ) ");
				communicationQuery.append("       AND log_time > sysdate - 1 ");
			
			Query truckCommunicationQuery = session.createSQLQuery(communicationQuery.toString());
			truckCommunicationQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			truckCommunicationQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			truckCommunicationQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, vehicleNo);
			List<Map<String,Object>> resultList = null;
			resultList = truckCommunicationQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for(Map<String, Object> result : resultList){
					 count = RMDCommonUtility.convertObjectToInt(result.get("INC_COUNT"));
				}
				if (count > 0)
					isTruckCommunication = true;
			}			
		} catch (RMDDAOConnectionException ex) {
        	LOG.error(ex, ex);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return isTruckCommunication;
	}
	
	
	
	public List<String> getAvgRxClosureTime(String mineId) {
		List<String> rxClousureTimeList = new ArrayList<String>();
		Session session = null;
		try {
			session = getHibernateSession();
			StringBuilder rxClousureTimeQuery = new StringBuilder();
			rxClousureTimeQuery.append("SELECT FDBK.rx_case_id                                       AS RX_ID, ");
			rxClousureTimeQuery.append("       To_char(DELV.delv_date, 'MM/DD/YYYY HH24:MI:SS')      AS RX_DELIVER_DATE, ");
			rxClousureTimeQuery.append("       To_char(FDBK.rx_close_date, 'MM/DD/YYYY HH24:MI:SS')  AS RX_CLOSE_DATE, ");
			rxClousureTimeQuery.append("       round(abs(( DELV.DELV_DATE-FDBK.RX_CLOSE_DATE)*24),2) AS HOUR_DIFF ");
			rxClousureTimeQuery.append("FROM   gets_sd_recom RECOM, ");
			rxClousureTimeQuery.append("       gets_sd_case_recom CASERECOM, ");
			rxClousureTimeQuery.append("       gets_sd_recom_delv DELV, ");
			rxClousureTimeQuery.append("       gets_sd_cust_fdbk FDBK, ");
			rxClousureTimeQuery.append("       gets_rmd_vehicle VEH, ");
			rxClousureTimeQuery.append("       gets_rmd_veh_hdr VEHHDR, ");
			rxClousureTimeQuery.append("       table_site_part TSP, ");
			rxClousureTimeQuery.append("       gets_rmd_model VEHMOD, ");
			rxClousureTimeQuery.append("       table_bus_org TBO, ");
			rxClousureTimeQuery.append("       table_case C ");
			rxClousureTimeQuery.append("WHERE  DELV.recom_delv2recom = RECOM.objid ");
			rxClousureTimeQuery.append("       AND CASERECOM.case_recom2recom = RECOM.objid ");
			rxClousureTimeQuery.append("       AND fdbk.cust_fdbk2vehicle = veh.objid ");
			rxClousureTimeQuery.append("       AND VEH.vehicle2veh_hdr = VEHHDR.objid ");
			rxClousureTimeQuery.append("       AND VEH.vehicle2site_part = TSP.objid ");
			rxClousureTimeQuery.append("       AND VEHMOD.objid = VEH.vehicle2model ");
			rxClousureTimeQuery.append("       AND VEHHDR.veh_hdr2busorg = TBO.objid ");
			rxClousureTimeQuery.append("       AND FDBK.objid = DELV.recom_delv2cust_fdbk ");
			rxClousureTimeQuery.append("       AND FDBK.cust_fdbk2case = CASERECOM.case_recom2case ");
			rxClousureTimeQuery.append("       AND DELV.recom_delv2case = C.objid ");
			rxClousureTimeQuery.append("       AND vehmod.model_name = 'OHV' ");
			rxClousureTimeQuery.append("       AND Instr(( tsp.serial_no ), 'BAD') = 0 ");
			rxClousureTimeQuery.append("       AND FDBK.rx_close_date IS NOT NULL ");
			rxClousureTimeQuery.append("       AND TBO.s_org_id = :customerId ");
			
			Query rxClousureTimeGraphQuery = session.createSQLQuery(rxClousureTimeQuery.toString());
			rxClousureTimeGraphQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			rxClousureTimeGraphQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, mineId);
			List<Map<String,Object>> resultList = null;
			resultList = rxClousureTimeGraphQuery.list();
			
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (Map<String, Object> rxTimeObj : resultList) {
					String hourDiff = rxTimeObj.get("HOUR_DIFF").toString();
					rxClousureTimeList.add(hourDiff);
					
				}
			}
			
		}catch (RMDDAOConnectionException ex) {
	
			LOG.error(ex, ex);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e, e);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		
		return rxClousureTimeList;
		
	}
	
	
	public List<OHVMineTruckVO> getListMineTruck() {
		Session session = null;
		List<OHVMineTruckVO> listMineTruck = new ArrayList<OHVMineTruckVO>();
		
		try {
            session = getHibernateSession();
            StringBuilder vehicleQuery = new StringBuilder();            
            vehicleQuery.append("select ORG_ID, VEHICLE_NO, VEHICLE_OBJID from gets_rmd_cust_rnh_rn_v rnv where rnv.model_family_v = 'OHV' ORDER BY VEHICLE_NO ASC");
            Query vehQuery = session.createSQLQuery(vehicleQuery.toString());
            vehQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
            List<Map<String,Object>> resultList = null;
            resultList = vehQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
            	for (final Map<String,Object> mineTruckList : resultList) {
            		OHVMineTruckVO mineTruck = new OHVMineTruckVO();
					String mineId = RMDCommonUtility.convertObjectToString(mineTruckList.get("ORG_ID"));
					String truckId = RMDCommonUtility.convertObjectToString(mineTruckList.get("VEHICLE_NO"));
				    String vehObjId = RMDCommonUtility.convertObjectToString(mineTruckList.get("VEHICLE_OBJID"));
				    mineTruck.setMineId(mineId);
				    mineTruck.setTruckId(truckId);
				    mineTruck.setVehObjId(vehObjId);
				    
				    listMineTruck.add(mineTruck);
            	}
            	
            }
            
		}catch (RMDDAOConnectionException ex) {
			LOG.error(ex, ex);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e, e);
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return listMineTruck;
	
	}
	
	
	 public String insertTruckParmList(Map<String,List<String>> truckParamListMap , OHVReportsRxRequestVO truckInfoVO) {
	        Session session = null;
	        Transaction transaction = null;
	        String result = RMDCommonConstants.SUCCESS;
	        StringBuilder insertQuery = new StringBuilder();
	        StringBuilder deleteQuery = new StringBuilder();

	        
	        try {
	            session = getHibernateSession();
	            transaction = session.beginTransaction();
	            	deleteQuery.append("DELETE FROM gets_ohv_truck_param_info WHERE MINE_ID = :mineId AND TRUCK_ID = :truckId AND DURATION = :duration ");
	            	insertQuery.append(" INSERT INTO gets_ohv_truck_param_info (MINE_ID,TRUCK_ID,DURATION,CONTROL_POWER_ON,ENGINE_HOURS_METER,ENGINE_OPERATING_HOURS,ENGINE_IDLE_TIME,TRUCK_PARKED,PROPEL_TIME,RETARD_TIME,WARM_UP_MODE,NO_RETARD,NO_PROPEL,SPEED_LIMIT,INVERTER_1_DISABLE,INVERTER_2_DISABLE,RETARD_ENGINE_SPEED_INCR_1,RETARD_ENGINE_SPEED_INCR_2,DISTANCE_TRAVELED,");
	                insertQuery.append( " BODY_UP,LOADS,DISPATCH_MESSAGES,RP1_PICKUP,RP2_PICKUP,RP3_PICKUP,GF_CONTACTOR,PARKING_BRAKE,ENG_ALTERNATOR_REVOLUTIONS,ALTERNATOR_MW_HRS,OVERLOADS,PRE_SHIFT_BRAKE_TESTS,HEALTHCHECK_FAILED,HEALTHCHECK_SUCCESS,FAULTCHECK_SUCCESS,FAULTCHECK_FAILED,HC_FILES_RECEIVED,FUEL_SAVER_2_ENABLED,CONTINUOUS_RETARD ) ");
	                insertQuery.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
	                
	                Query insertHQuery = session.createSQLQuery(insertQuery.toString());
	                Query query = session.createSQLQuery(deleteQuery.toString());
	                for (Map.Entry<String, List<String>> pair : truckParamListMap.entrySet()) {
	                	String duration = pair.getKey();
	                	List<String> listValues = pair.getValue();
	                	query.setParameter(RMDCommonConstants.OHV_MINE_ID, truckInfoVO.getMineId());
	                	query.setParameter(RMDCommonConstants.OHV_TRUCK_ID,truckInfoVO.getTruckId());
	                	query.setParameter(RMDCommonConstants.OHV_TRUCK_PARAM_DURATION,duration);
	                	query.executeUpdate();

	                	insertHQuery.setString(0, truckInfoVO.getMineId());
	                	insertHQuery.setInteger(1, Integer.parseInt(truckInfoVO.getTruckId()));
	                	insertHQuery.setString(2, duration);
	                	insertHQuery.setDouble(3, Double.parseDouble(listValues.get(0)));
	                	insertHQuery.setDouble(4, Double.parseDouble(listValues.get(1)));
	                	insertHQuery.setDouble(5, Double.parseDouble(listValues.get(2)));
	                	insertHQuery.setDouble(6, Double.parseDouble(listValues.get(3)));
	                	insertHQuery.setDouble(7, Double.parseDouble(listValues.get(4)));
	                	insertHQuery.setDouble(8, Double.parseDouble(listValues.get(5)));
	                	insertHQuery.setDouble(9, Double.parseDouble(listValues.get(6)));
	                	insertHQuery.setDouble(10, Double.parseDouble(listValues.get(7)));
	                	insertHQuery.setDouble(11, Double.parseDouble(listValues.get(8)));
	                	insertHQuery.setDouble(12, Double.parseDouble(listValues.get(9)));
	                	insertHQuery.setDouble(13, Double.parseDouble(listValues.get(10)));
	                	insertHQuery.setDouble(14, Double.parseDouble(listValues.get(11)));
	                	insertHQuery.setDouble(15, Double.parseDouble(listValues.get(12)));
	                	insertHQuery.setDouble(16, Double.parseDouble(listValues.get(13)));
	                	insertHQuery.setDouble(17, Double.parseDouble(listValues.get(14)));
	                	insertHQuery.setDouble(18, Double.parseDouble(listValues.get(15)));
	                	insertHQuery.setDouble(19, Double.parseDouble(listValues.get(16)));
	                	insertHQuery.setDouble(20, Double.parseDouble(listValues.get(17)));
	                	insertHQuery.setDouble(21, Double.parseDouble(listValues.get(18)));
	                	insertHQuery.setDouble(22, Double.parseDouble(listValues.get(19)));
	                	insertHQuery.setDouble(23, Double.parseDouble(listValues.get(20)));
	                	insertHQuery.setDouble(24, Double.parseDouble(listValues.get(21)));
	                	insertHQuery.setDouble(25, Double.parseDouble(listValues.get(22)));
	                	insertHQuery.setDouble(26, Double.parseDouble(listValues.get(23)));
	                	insertHQuery.setDouble(27, Double.parseDouble(listValues.get(24)));
	                	insertHQuery.setDouble(28, Double.parseDouble(listValues.get(25)));
	                	insertHQuery.setDouble(29, Double.parseDouble(listValues.get(26)));
	                	insertHQuery.setDouble(30, Double.parseDouble(listValues.get(27)));
	                	insertHQuery.setDouble(31, Double.parseDouble(listValues.get(28)));
	                	insertHQuery.setDouble(32, Double.parseDouble(listValues.get(29)));
	                	insertHQuery.setDouble(33, Double.parseDouble(listValues.get(30)));
	                	insertHQuery.setDouble(34, Double.parseDouble(listValues.get(31)));
	                	insertHQuery.setDouble(35, Double.parseDouble(listValues.get(32)));
	                	insertHQuery.setDouble(36, Double.parseDouble(listValues.get(33)));
	                	insertHQuery.setDouble(37, Double.parseDouble(listValues.get(34)));
	                	insertHQuery.executeUpdate();
	                }
	                transaction.commit();
	               
	        } catch (Exception e) {
	        	transaction.rollback();
	            LOG.error("Exception occurred IN OHV Scheduler Insert", e);
	            result = RMDCommonConstants.FAILURE;
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_INSERT_REPAIR_LOCATION_DETAILS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        } finally {

	            releaseSession(session);
	        }
	        return result;
	    }
	 
	 
	 
	 
	 
	 
	 public Map<String,List<String>> getTruckParamListFromScheduler (OHVReportsRxRequestVO truckInfoVO) {
			Session session = null;
			Map<String, List<String>> contentListMap = new HashMap<String, List<String>>();

			try {
				session = getHibernateSession();
				StringBuilder faultQuery = new StringBuilder();
				faultQuery.append("SELECT DURATION, ");
				faultQuery.append("       control_power_on, ");
				faultQuery.append("       engine_hours_meter, ");
				faultQuery.append("       engine_operating_hours, ");
				faultQuery.append("       engine_idle_time, ");
				faultQuery.append("       truck_parked, ");
				faultQuery.append("       propel_time, ");
				faultQuery.append("       retard_time, ");
				faultQuery.append("       warm_up_mode, ");
				faultQuery.append("       no_retard, ");
				faultQuery.append("       no_propel, ");
				faultQuery.append("       speed_limit, ");
				faultQuery.append("       inverter_1_disable, ");
				faultQuery.append("       inverter_2_disable, ");
				faultQuery.append("       retard_engine_speed_incr_1, ");
				faultQuery.append("       retard_engine_speed_incr_2, ");
				faultQuery.append("       distance_traveled, ");
				faultQuery.append("       body_up, ");
				faultQuery.append("       loads, ");
				faultQuery.append("       dispatch_messages, ");
				faultQuery.append("       rp1_pickup, ");
				faultQuery.append("       rp2_pickup, ");
				faultQuery.append("       rp3_pickup, ");
				faultQuery.append("       gf_contactor, ");
				faultQuery.append("       parking_brake, ");
				faultQuery.append("       eng_alternator_revolutions, ");
				faultQuery.append("       alternator_mw_hrs, ");
				faultQuery.append("       overloads, ");
				faultQuery.append("       pre_shift_brake_tests, ");
				faultQuery.append("       healthcheck_failed, ");
				faultQuery.append("       healthcheck_success, ");
				faultQuery.append("       faultcheck_success, ");
				faultQuery.append("       faultcheck_failed, ");
				faultQuery.append("       hc_files_received, ");
				faultQuery.append("       fuel_saver_2_enabled, ");
				faultQuery.append("       continuous_retard ");
				faultQuery.append("FROM   gets_ohv_truck_param_info ");
				faultQuery.append("WHERE  mine_id =  :customerId ");
				faultQuery.append("       AND truck_id = :vehicleNo ");

				Query fltQuery = session.createSQLQuery(faultQuery.toString());
				fltQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
				fltQuery.setParameter(RMDCommonConstants.OHV_CUSTOMER_ID, truckInfoVO.getMineId());
				fltQuery.setParameter(RMDCommonConstants.OHV_VEHICLE_NO, truckInfoVO.getTruckId());
	            List<Map<String,Object>> faultResult = fltQuery.list();
	            if (RMDCommonUtility.isCollectionNotEmpty(faultResult)) {
					for (final Map<String,Object> faultResultObj : faultResult) {
						List<String> paramList = new ArrayList<String>();
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("CONTROL_POWER_ON")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("ENGINE_HOURS_METER")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("ENGINE_OPERATING_HOURS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("ENGINE_IDLE_TIME")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("TRUCK_PARKED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("PROPEL_TIME")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RETARD_TIME")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("WARM_UP_MODE")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("NO_RETARD")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("NO_PROPEL")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("SPEED_LIMIT")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("INVERTER_1_DISABLE")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("INVERTER_2_DISABLE")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RETARD_ENGINE_SPEED_INCR_1")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RETARD_ENGINE_SPEED_INCR_2")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("DISTANCE_TRAVELED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("BODY_UP")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("LOADS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("DISPATCH_MESSAGES")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RP1_PICKUP")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RP2_PICKUP")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("RP3_PICKUP")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("GF_CONTACTOR")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("PARKING_BRAKE")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("ENG_ALTERNATOR_REVOLUTIONS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("ALTERNATOR_MW_HRS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("OVERLOADS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("PRE_SHIFT_BRAKE_TESTS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("HEALTHCHECK_FAILED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("HEALTHCHECK_SUCCESS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("FAULTCHECK_SUCCESS")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("FAULTCHECK_FAILED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("HC_FILES_RECEIVED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("FUEL_SAVER_2_ENABLED")));
						paramList.add(RMDCommonUtility.convertObjectToString(faultResultObj.get("CONTINUOUS_RETARD")));
						contentListMap.put(RMDCommonUtility.convertObjectToString(faultResultObj.get("DURATION")), paramList);
					}
				}
			  }  catch (RMDDAOConnectionException ex) {
				LOG.error(ex, ex);
				String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
						ex, RMDCommonConstants.FATAL_ERROR);
			} catch (Exception e) {
				LOG.error(e, e);
				String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OHV_TRUCK_EVENTS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
						RMDCommonConstants.MAJOR_ERROR);
			} finally {
				releaseSession(session);
			}
			return contentListMap;
		}
	 
}
