package com.ge.trans.eoa.services.tools.lhr.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.lhr.dao.intf.LhrDAOIntf;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRuleFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthScoreRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesFiringVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.HealthRulesVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LhrResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesCommResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesRequestVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.LocomotivesResponseVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.PerformanceHealthDataVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.RxVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.VehicleHealthDataVO;
import com.ge.trans.eoa.services.tools.lhr.service.valueobjects.VehicleVO;

public class LhrDAOImpl extends BaseDAO implements LhrDAOIntf {  
	
	private static final long serialVersionUID = -757052913816732274L;
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(LhrDAOImpl.class);
	
	private String rxHealthDataQuery = "SELECT recom.OBJID AS objid,recom.URGENCY AS urgency, recom.TITLE AS title,fdbk.rx_open_date AS rxOpenDate, fdbk.rx_close_date AS rxCloseDate,rd.delv_date AS rxDeliveryDate,  recom.RECOM_TYPE AS type,  recom.RECOM_STATUS AS status,  recom.EST_REPAIR_TIME AS estimatedRepairTime,  recom.LOCO_IMPACT AS impactDescription FROM gets_sd.gets_sd_recom recom,gets_sd.gets_sd_recom_delv rd,gets_sd.gets_sd_cust_fdbk fdbk,table_case c,table_gbst_elm ge WHERE recom.objid = recom_delv2recom AND recom_delv2cust_fdbk = fdbk.objid AND fdbk.cust_fdbk2case  = c.objid AND recom_delv2case = c.objid AND calltype2gbst_elm = ge.objid AND ge.title IN ('EOA Problem','ESTP Problem') and recom.objid = :rxId AND fdbk.cust_fdbk2vehicle  = :vehicleId ORDER BY 1 DESC";
	private String vehicleHealthScoreQuery = "SELECT vhealth.objid health_Id,vhealth.HEALTH_SCORE HEALTH_SCORE,vhealth.DEFECT_LEVEL_CODE,vhealth.CONFIDENCE HEALTH_CONFIDENCE,vhealth.CALCULATION_DATE HEALTH_CALCULATION_DATE,phealth.PERFCHECK_NAME,phealth.PERFCHECK_CODE,phealth.perf_health2recom,phealth.objid PERFCHECK_ID,phealth.phealth_Objid,phealth.HEALTH_SCORE PERF_HEALTH_SCORE,phealth.CONFIDENCE PERF_CONFIDENCE,phealth.CALCULATION_DATE PERF_CALCULATION_DATE FROM GETS_TOOLS.GETS_TOOL_IPA_VEHICLE_HEALTH vhealth,GETS_TOOLS.GETS_TOOL_IPA_CURR_VEH_HEALTH currVeh,(SELECT  phealth.perf_health2vehicle_health,pchck.objid,phealth.objid phealth_Objid,pchck.PERFCHECK_NAME,pchck.PERFCHECK_CODE,phealth.perf_health2recom,phealth.HEALTH_SCORE,phealth.CONFIDENCE,phealth.CALCULATION_DATE FROM GETS_TOOLS.GETS_TOOL_IPA_PERF_HEALTH phealth, GETS_TOOLS.GETS_TOOL_LHR_PERF_check pchck WHERE phealth.perf_health2perf_check  = pchck.objid ) phealth WHERE currVeh.curr_veh_health = vhealth.objid AND vhealth.objid = phealth.perf_health2vehicle_health(+) AND currVeh.curr_veh_health2vehicle = :vehicleId";
	private String healthRulesQuery = "select distinct original_id RuleId,rule_title,Rule_type,family from gets_tool_dpd_finrul a ,gets_tool_dpd_ruledef,gets_tool_dpd_rulhis where rule_type in ('Health','Diagnostic') and a.objid = ruledef2finrul and a.objid = rulhis2finrul and ruledef2finrul = rulhis2finrul and active = 1 order by family";
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
	
	@SuppressWarnings("unchecked")
	public List<VehicleVO> getVehicle(String customerId, List<String> roadNumber,Session hibernateSession) throws RMDDAOException {
		List<VehicleVO> vehicleList = null;
		Query displayNameQuery;
		
		try {
			
			VehicleVO vehicle = null;
			String strQry = "select grv.OBJID AS objid,tsp.X_VEH_HDR_CUST AS customerId,tsp.SERIAL_NO AS roadNumber,F.FLEET_NUMBER AS fleetId, M.MODEL_NAME AS modelId from gets_rmd_veh_hdr grvh,table_bus_org tbo, table_site_part tsp, gets_rmd_vehicle grv,GETS_RMD_FLEET F, GETS_RMD_MODEL M where grvh.veh_hdr2busorg = tbo.objid and grvh.objid = grv.vehicle2veh_hdr AND grv.vehicle2site_part = tsp.objid AND grv.VEHICLE2FLEET = F.OBJID AND grv.VEHICLE2MODEL = M.OBJID and org_id = :customerId and tsp.SERIAL_NO in (?)";
			strQry=strQry.replace("(?)",createSQLStringArray(roadNumber));
			
			displayNameQuery = hibernateSession.createSQLQuery(strQry.toString());
			displayNameQuery.setParameter("customerId",customerId);
			List<Object> selectList = (List<Object>)displayNameQuery.list();
			
			if (selectList != null && !selectList.isEmpty()) {
				 vehicleList = new ArrayList<VehicleVO>();
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] vehicleObject = (Object[]) lookValueIter.next();
					
					vehicle = new VehicleVO(
							Long.valueOf(vehicleObject[0].toString()),
							String.valueOf(vehicleObject[1]),
							String.valueOf(vehicleObject[2]),
							String.valueOf(vehicleObject[3]),
							String.valueOf(vehicleObject[4]));
					vehicleList.add(vehicle);
				}
			}
		}catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getVehicle() "+e.getMessage(),e);
			throw e;
		}
		return vehicleList;
	}
	private static String createSQLStringArray(List<String> vehileList) {
		String result = null;

		if (vehileList != null && !vehileList.isEmpty()) {
			StringBuffer resultBuffer = new StringBuffer();
			int count = 0;
			resultBuffer.append("('");
			for (String id : vehileList) {
				if (count++ > 0) {
					resultBuffer.append("','");
				}
				resultBuffer.append(id);			
			}
			resultBuffer.append("')");
			result=resultBuffer.toString();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public RxVO getHelathDataRX(String vehicleId,long recomId, Session hibernateSession) {
		RxVO recommendation = null;
        Query queryObj;
       
        try {
        	
			queryObj = hibernateSession.createSQLQuery(rxHealthDataQuery.toString());
			queryObj.setParameter("rxId",recomId);
			queryObj.setParameter("vehicleId", vehicleId);
			
			List<Object> selectList = (List<Object>)queryObj.list();
			
			if (selectList != null && !selectList.isEmpty()) {
				
				Object[] rxObject = (Object[])selectList.get(0);
				Date rxOpenDate = null;
				Date rxCloseDate = null;
				Date rxDeliveryDate = null;
				
				if (null != rxObject[3]) {
					rxOpenDate = (Date) rxObject[3];
				}
				
				if (rxObject[5] != null) {
					rxDeliveryDate = (Date) rxObject[5];
				}
				
				if (rxObject[4] != null) {
					rxCloseDate = (Date) rxObject[4];
				}
				recommendation = new RxVO(Long.valueOf(rxObject[0].toString()), 
						String.valueOf(rxObject[1]),
						String.valueOf(rxObject[2]), 
						String.valueOf(rxObject[6]), 
						String.valueOf(rxObject[7]),
						String.valueOf(rxObject[8]),
						String.valueOf(rxObject[9]),
						rxOpenDate, rxCloseDate, rxDeliveryDate);
			}
		}  catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getHelathDataRX() "+e.getMessage(),e);
			throw e;
		}
		return recommendation;
	}
	
	@SuppressWarnings("unchecked")
	public VehicleHealthDataVO getVehicleHealthData(VehicleVO vehicle, Session hibernateSession) throws RMDDAOException {
		VehicleHealthDataVO healthData = null;
        Query queryObj;
		try {
			
			queryObj = hibernateSession.createSQLQuery(vehicleHealthScoreQuery);
			queryObj.setParameter("vehicleId", vehicle.getObjidAsString());
			
			List<Object> selectList = (List<Object>)queryObj.list();
			
	        if (selectList != null && !selectList.isEmpty()) {
	        	int flag = 0;
		        healthData = new VehicleHealthDataVO(vehicle);
	        	for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
	        		Object[] healthyObject = (Object[]) lookValueIter.next();
					PerformanceHealthDataVO  performanceData = null;
					//check PERFCHECK_NAME or 	PERFCHECK_ID is not null 			
					if(null != healthyObject[5] || null != healthyObject[6]){
						performanceData = new PerformanceHealthDataVO();
						//phealth_Objid
						performanceData.setObjid(Long.valueOf(healthyObject[9].toString()));
						//PERFCHECK_ID
						performanceData.setPerfCheckId(Integer.valueOf(healthyObject[8].toString()));
						//PERF_CONFIDENCE
						performanceData.setConfidence(Double.valueOf(healthyObject[11].toString()));
						//PERF_HEALTH_SCORE
						performanceData.setHealthScore(Float.valueOf(healthyObject[10].toString()));
						//PERF_CALCULATION_DATE
						Timestamp timestamp = (Timestamp) healthyObject[12];
						if (timestamp != null) {
							Date calculationDate = new java.util.Date(timestamp.getTime());
							performanceData.setCalculationDate(calculationDate);
						}
						performanceData.setPerformanceCheckName(String.valueOf(healthyObject[5]));
						performanceData.setPerformanceCheckCode(String.valueOf(healthyObject[6]));
						//perf_health2recom
						if(null != healthyObject[7] && Long.valueOf(healthyObject[7].toString()) > 0){
							RxVO rxInfo = getHelathDataRX(healthData.getVehicle().getObjidAsString(), Long.valueOf(healthyObject[7].toString()),hibernateSession);
							performanceData.setMostCriticalRx(rxInfo);
						}
					}
					
					if(flag == 0){
						//health_Id
						healthData.setObjid(Long.valueOf(healthyObject[0].toString()));
						//HEALTH_SCORE
						healthData.setHealthFactor(Float.valueOf(healthyObject[1].toString()));
						//DEFECT_LEVEL_CODE
						healthData.setDefectLevelCode(String.valueOf(healthyObject[2]));
						//HEALTH_CONFIDENCE
						healthData.setConfidence(Long.valueOf(healthyObject[3].toString()));
						//HEALTH_CALCULATION_DATE
						Timestamp timestamp = (Timestamp) healthyObject[4];
						if (timestamp != null) {
							Date calculationDate = new java.util.Date(timestamp.getTime());
							healthData.setCalculationDate(calculationDate);
						}
					}
					healthData.addComponentHealth(performanceData);
					flag++;
	        	}
	        }
		}  catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getVehicleHealthData() "+e.getMessage(),e);
			throw e;
		}
		return healthData;
	}
	
	@Override
	public List<HealthRuleFiringVO> getHealthRulesData(String lmsLocoId,
			List<Long> ruleList) throws RMDDAOException {

		List<HealthRuleFiringVO> healthRuleFiringVOList = new ArrayList<HealthRuleFiringVO>();
		Session hibernateSession = null;
		try {
			hibernateSession = getHibernateSession();
			String vehicleId = validateLocoId(lmsLocoId,hibernateSession);
			Map<String, List<Long>> ruleLists = getRuleTypes(hibernateSession,
					ruleList);

			for (Map.Entry<String, List<Long>> entryList : ruleLists.entrySet()) {

				List<Long> ruleEntryList = entryList.getValue();
				if (ruleEntryList.size() > 0) {
					if (entryList.getKey().equalsIgnoreCase("Health")) {
						healthRuleFiringVOList.addAll(fetchHealthRules(
								hibernateSession, lmsLocoId, ruleEntryList));
					} else {
						healthRuleFiringVOList.addAll(fetchDiagnosticRules(
								hibernateSession, vehicleId, ruleEntryList));
					}
				}
			}
		} finally {
			releaseSession(hibernateSession);
		}
		return healthRuleFiringVOList;
	}
	private List<HealthRuleFiringVO> fetchHealthRules(Session hibernateSession,
			String lmsLocoId, List<Long> ruleList) throws RMDDAOException {

		List<HealthRuleFiringVO> healthRuleFiringVOList = new ArrayList<HealthRuleFiringVO>();
		StringBuffer strQuery = new StringBuffer();

		strQuery.append(
				"select ORIGINAL_ID ,nvl(LAST_UPDATED_DATE,(SELECT CVH.LAST_UPDATED_DATE FROM GETS_TOOL_IPA_CURR_VEH_HEALTH CVH,GETS_RMD_VEHICLE V ")
				.append("WHERE CVH.CURR_VEH_HEALTH2VEHICLE = V.OBJID AND V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and rownum=1)) LAST_UPDATED_DATE, ")
				.append("firingCount from ( SELECT ORIGINAL_ID ,LAST_UPDATED_DATE ,COUNT(ORG) firingCount, rank() over(partition by original_id order by last_updated_date desc nulls last) rn ")
				.append("FROM ( SELECT ORG.ORIGINAL_ID,NXT.ORIGINAL_ID ORG, NXT.LAST_UPDATED_DATE FROM ( SELECT  R.OBJID,H.ORIGINAL_ID FROM GETS_TOOL_DPD_RULEDEF R,  GETS_TOOL_DPD_RULHIS H WHERE  R.RULEDEF2FINRUL = H.RULHIS2FINRUL  AND H.ORIGINAL_ID in (%s)) ORG, ")
				.append("(SELECT CVH.LAST_UPDATED_DATE, CVH.CURR_VEH_HEALTH,ORIGINAL_ID FROM GETS_TOOL_IPA_CURR_VEH_HEALTH CVH,GETS_RMD_VEHICLE V,")
				.append("(select VH.objid, HF.FIRING2RULE_DEFN ORIGINAL_ID,hf.creation_date from GETS_TOOL_HEALTH_FIRING HF,GETS_RMD_VEHICLE V, GETS_TOOL_IPA_PERF_HEALTH PH, GETS_TOOL_IPA_VEHICLE_HEALTH VH ")
				.append("WHERE VH.OBJID = PH.PERF_HEALTH2VEHICLE_HEALTH AND HF.OBJID = PH.PERF_HEALTH2FIRING and V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and HF.creation_date between sysdate-1 and sysdate) t ")
				.append("WHERE CVH.CURR_VEH_HEALTH2VEHICLE = V.OBJID AND V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and CVH.CURR_VEH_HEALTH(+) = t.objid ")
				.append(") NXT WHERE ORG.OBJID= NXT.ORIGINAL_ID(+)) GROUP BY ORIGINAL_ID,LAST_UPDATED_DATE ) where rn=1");
		String anomIds = getStringList(ruleList);
		String strQry = String.format(strQuery.toString(), anomIds);

		Query queryOne = hibernateSession.createSQLQuery(strQry);
		queryOne.setFetchSize(700);
		List<Object> selectList = (List<Object>) queryOne.list();
		HealthRuleFiringVO healthRuleFiringVO=null;
		if (selectList != null && !selectList.isEmpty()) {
			for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
					.hasNext();) {
				Object[] healthyObject = (Object[]) lookValueIter.next();
				BigDecimal ruleId = (BigDecimal) healthyObject[0];
				Timestamp timestamp = (Timestamp) healthyObject[1];
				boolean firingStatus = Long
						.valueOf(healthyObject[2].toString()) > 0;
						
				if (null != timestamp) {
					healthRuleFiringVO = new HealthRuleFiringVO(
							ruleId.longValue(), firingStatus,
							new java.util.Date(timestamp.getTime()));
				} else {
					healthRuleFiringVO = new HealthRuleFiringVO(
							ruleId.longValue(), firingStatus, null);
				}
				healthRuleFiringVOList.add(healthRuleFiringVO);
			}
		}else{
			for (Long ruleId : ruleList) {
				healthRuleFiringVO = new HealthRuleFiringVO(ruleId, false, null);
				healthRuleFiringVOList.add(healthRuleFiringVO);
			}
		}
		return healthRuleFiringVOList;
	}
	
	private String getStringList(List<Long> idList) {
		StringBuilder buff = new StringBuilder();
		String sep = "";

		for (Long id : idList) {
			buff.append(sep);
			buff.append(id);
			sep = ",";
		}
		return buff.toString();
	}
	
	public List<HealthRulesVO> getHealthRules() throws RMDDAOException {
		List<HealthRulesVO> healthRulesList = new ArrayList<HealthRulesVO>();
		Session hibernateSession = null;
		try {
			
			hibernateSession = getHibernateSession();
			Query queryOne = hibernateSession.createSQLQuery(healthRulesQuery);
			List<Object> selectList = (List<Object>) queryOne.list();

			if (selectList != null && !selectList.isEmpty()) {
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] healthyObject = (Object[]) lookValueIter.next();
					BigDecimal ruleId = (BigDecimal) healthyObject[0];
					String ruleTitle = (String) healthyObject[1];
					String ruleType = (String) healthyObject[2];
					String family = (String) healthyObject[3];
					HealthRulesVO healthRulesVO = new HealthRulesVO(ruleId.longValue(),
							ruleTitle, ruleType, family);
					healthRulesList.add(healthRulesVO);
				}
			}
		} finally {
			releaseSession(hibernateSession);
		}
		return healthRulesList;
	}

	@Override
	public List<LocomotivesResponseVO> getHealthFiringDetails(List<LocomotivesRequestVO> locoList,String ruleType)
			throws RMDDAOException {
		
		List<LocomotivesResponseVO> locomotivesResponseVOList = new ArrayList<LocomotivesResponseVO>();
		Session hibernateSession = null;
		String vehicleId = null;
		try {
			
			hibernateSession = getHibernateSession();
			if (ruleType.equalsIgnoreCase("Diagnostic") && !locoList.isEmpty()) { 
				LocomotivesRequestVO locomotivesRequestVO = locoList.get(0);
				String locoId = String.valueOf(locomotivesRequestVO.getLocomotiveId());
			    vehicleId = validateLocoId(locoId,hibernateSession);
			}
			
			for(LocomotivesRequestVO locomotivesRequestVO : locoList){
				
				LocomotivesResponseVO locomotivesResponseVO = new LocomotivesResponseVO();
				
				ArrayList<HealthRulesFiringVO> healthRulesFiringVOList = new ArrayList<HealthRulesFiringVO>();
				
				String lmsLocoId = String.valueOf(locomotivesRequestVO.getLocomotiveId());
				
				List<Long> ruleList = locomotivesRequestVO.getHealthRules();
				
				
				int fromIndex = 0;
				int processingSize = 700;
				int toIndex = 700;

				int listSize = ruleList.size();

				Long lgItrCount = (long) Math.round(listSize / toIndex);

				if (lgItrCount * toIndex < listSize && lgItrCount > 0) {
					lgItrCount++;
				}
				if (toIndex > listSize) {
					toIndex = listSize;
				}

				while (lgItrCount >= 0) {
					
					if (fromIndex > toIndex || fromIndex == toIndex)
						break;
					if (ruleType.equalsIgnoreCase("Diagnostic")) { 
						if(null != vehicleId){
						healthRulesFiringVOList.addAll(new ArrayList<HealthRulesFiringVO>(fetchDiagnosticRulesFiringDetails(
								hibernateSession, vehicleId, ruleList.subList(fromIndex, toIndex))));
						}
					}else{
						healthRulesFiringVOList.addAll(new ArrayList<HealthRulesFiringVO>(fetchHealthRulesFiringDetails(
								hibernateSession, lmsLocoId, ruleList.subList(fromIndex, toIndex))));
					}
					lgItrCount--;
					fromIndex = toIndex;
					toIndex = toIndex + processingSize;
					if (lgItrCount == 0 || toIndex > listSize) {
						toIndex = listSize;
					}
				}
				
				locomotivesResponseVO.setLocomotiveId(locomotivesRequestVO.getLocomotiveId());
				locomotivesResponseVO.setHealthRules(healthRulesFiringVOList);				
				locomotivesResponseVOList.add(locomotivesResponseVO);
				
			}
		} finally {
			releaseSession(hibernateSession);
		}
		return locomotivesResponseVOList;
	}
	
	private List<HealthRulesFiringVO> fetchHealthRulesFiringDetails(Session hibernateSession,
			String lmsLocoId, List<Long> ruleList) throws RMDDAOException {

		List<HealthRulesFiringVO> healthRulesFiringVOList = new ArrayList<HealthRulesFiringVO>();
		StringBuffer strQuery = new StringBuffer();

		strQuery.append(
				"select ORIGINAL_ID ,nvl(LAST_UPDATED_DATE,(SELECT CVH.LAST_UPDATED_DATE FROM GETS_TOOL_IPA_CURR_VEH_HEALTH CVH,GETS_RMD_VEHICLE V ")
				.append("WHERE CVH.CURR_VEH_HEALTH2VEHICLE = V.OBJID AND V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and rownum=1)) LAST_UPDATED_DATE, ")
				.append("firingCount from ( SELECT ORIGINAL_ID ,LAST_UPDATED_DATE ,COUNT(ORG) firingCount, rank() over(partition by original_id order by last_updated_date desc nulls last) rn ")
				.append("FROM ( SELECT ORG.ORIGINAL_ID,NXT.ORIGINAL_ID ORG, NXT.LAST_UPDATED_DATE FROM ( SELECT  R.OBJID,H.ORIGINAL_ID FROM GETS_TOOL_DPD_RULEDEF R,  GETS_TOOL_DPD_RULHIS H WHERE  R.RULEDEF2FINRUL = H.RULHIS2FINRUL  AND H.ORIGINAL_ID in (%s)) ORG, ")
				.append("(SELECT CVH.LAST_UPDATED_DATE, CVH.CURR_VEH_HEALTH,ORIGINAL_ID FROM GETS_TOOL_IPA_CURR_VEH_HEALTH CVH,GETS_RMD_VEHICLE V,")
				.append("(select VH.objid, HF.FIRING2RULE_DEFN ORIGINAL_ID,hf.creation_date from GETS_TOOL_HEALTH_FIRING HF,GETS_RMD_VEHICLE V, GETS_TOOL_IPA_PERF_HEALTH PH, GETS_TOOL_IPA_VEHICLE_HEALTH VH ")
				.append("WHERE VH.OBJID = PH.PERF_HEALTH2VEHICLE_HEALTH AND HF.OBJID = PH.PERF_HEALTH2FIRING and V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and HF.creation_date between sysdate-1 and sysdate) t ")
				.append("WHERE CVH.CURR_VEH_HEALTH2VEHICLE = V.OBJID AND V.LMS_LOCO_ID = ")
				.append(lmsLocoId)
				.append(" and CVH.CURR_VEH_HEALTH(+) = t.objid ")
				.append(") NXT WHERE ORG.OBJID= NXT.ORIGINAL_ID(+)) GROUP BY ORIGINAL_ID,LAST_UPDATED_DATE ) where rn=1");
		
		String ruleIds = getStringList(ruleList);
		String strQry = String.format(strQuery.toString(), ruleIds);

		Query queryOne = hibernateSession.createSQLQuery(strQry);
		queryOne.setFetchSize(700);
		List<Object> selectList = (List<Object>) queryOne.list();
		HealthRulesFiringVO healthRulesFiringVO=null;
		if (selectList != null && !selectList.isEmpty()) {
			for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
					.hasNext();) {
				Object[] healthyObject = (Object[]) lookValueIter.next();
				BigDecimal ruleId = (BigDecimal) healthyObject[0];
				Timestamp timestamp = (Timestamp) healthyObject[1];
				boolean firingStatus = Long
						.valueOf(healthyObject[2].toString()) > 0;
									
				if (null != timestamp) {
					healthRulesFiringVO = new HealthRulesFiringVO(
							ruleId.longValue(), firingStatus,
							new java.util.Date(timestamp.getTime()));
				} else {
					healthRulesFiringVO = new HealthRulesFiringVO(
							ruleId.longValue(), firingStatus, null);
				}
				healthRulesFiringVOList.add(healthRulesFiringVO);
			}
		}else{
			for (Long ruleId : ruleList) {
				healthRulesFiringVO = new HealthRulesFiringVO(
						ruleId.longValue(), false, null);
				healthRulesFiringVOList.add(healthRulesFiringVO);
			}
		}
		return healthRulesFiringVOList;
	}
	
	@Override
	public LhrResponseVO getVehicleHealthData(
			List<HealthScoreRequestVO> assetList) throws RMDDAOException {
		Session hibernateSession = null;
		LhrResponseVO lhrResponseVO = new LhrResponseVO();
		List<VehicleHealthDataVO> vehicleHealthDataVOList = new ArrayList<VehicleHealthDataVO>();
		try {
			hibernateSession = getHibernateSession();
			// segment the customer and list of road numbers
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			for (HealthScoreRequestVO requestVo : assetList) {
				// customer, road number
				if (map.containsKey(requestVo.getRoadNumberHeader())) {
					map.get(requestVo.getRoadNumberHeader()).add(
							requestVo.getRoadNumber());
				} else {
					List<String> roadnumber = new ArrayList<String>();
					roadnumber.add(requestVo.getRoadNumber());
					map.put(requestVo.getRoadNumberHeader(), roadnumber);
				}
			}
			// get vehicle details to get health score and make response
			List<VehicleVO> vehList = new ArrayList<VehicleVO>();
			for (Entry<String, List<String>> ss : map.entrySet()) {
				List<VehicleVO> returnValues = getVehicle(ss.getKey(),
						ss.getValue(),hibernateSession);
				if (null != returnValues) {
					vehList.addAll(returnValues);
				}
			}
			// get health score values
			if (null != vehList && !vehList.isEmpty()) {
				for (VehicleVO vehicleObj : vehList) {
					VehicleHealthDataVO vehicleData = getVehicleHealthData(
							vehicleObj,hibernateSession);
					if (null == vehicleData) {
						vehicleData = new VehicleHealthDataVO(vehicleObj, 0,
								"NA");
					}
					vehicleHealthDataVOList.add(vehicleData);
				}
			}
			lhrResponseVO.setVehicleHealthDataVOList(vehicleHealthDataVOList);
		} catch (RMDDAOException e) {
			LOG.error(
					"Error occured in LhrDAOImpl.getVehicleHealthData() "
							+ e.getMessage(), e);
			throw e;
		} finally {
			releaseSession(hibernateSession);
		}
		return lhrResponseVO;
	}
	
	private List<HealthRulesFiringVO> fetchDiagnosticRulesFiringDetails(Session hibernateSession,
			String vehicleId, List<Long> ruleList) throws RMDDAOException {

		List<HealthRulesFiringVO> healthRulesFiringVOList = new ArrayList<HealthRulesFiringVO>();
		//StringBuffer strQuery = new StringBuffer();		
		/*strQuery.append(
				"with main as ( select /*+ parallel(8)  temp.OBJID,temp.ORIGINAL_ID, max(detect.creation_date) creation_dt, count(*) over (partition by original_id) cnt ")
				.append("from ( SELECT distinct R.OBJID,H.ORIGINAL_ID , r.ruledef2recom FROM GETS_TOOL_DPD_RULEDEF R,GETS_TOOL_DPD_RULHIS H ")
				.append("WHERE R.RULEDEF2FINRUL = H.RULHIS2FINRUL and rule_type in ('Diagnostic') and active = 1 AND H.ORIGINAL_ID IN (%s) ) temp, ")
				.append("GETS_TOOL_AR_DETECT detect where  ar_detect2recom(+) = ruledef2recom group by temp.OBJID,temp.ORIGINAL_ID ")
				.append("), ")
				.append("SS_Data as ( select /*+ parallel(8)  distinct fltdown2rule_defn from gets_tool_fltdown d, gets_tool_fault f ")
				.append("where fault2vehicle in (select objid from gets_rmd_vehicle where lms_loco_id = :lmsLocoId ) and d.fltdown2fault = f.objid ")
				.append("and d.creation_date   BETWEEN sysdate-30 AND sysdate ) ")
				.append("select ORIGINAL_ID,creation_dt,count from ( select distinct a.ORIGINAL_ID,decode(fltdown2rule_defn,null,null,a.creation_dt) creation_dt, ")
				.append("decode(fltdown2rule_defn,null,0,a.cnt) count , rank () over(partition by original_id order by original_id, decode(fltdown2rule_defn,null,null,a.creation_dt) ")
				.append("desc nulls last ) rn from main A, SS_Data B where A.objid = B.fltdown2rule_defn(+) ) where rn=1 ");
		*/
		StringBuffer  strQuery = new StringBuffer();
		strQuery.append("WITH rx ");
		strQuery.append("     AS (SELECT DISTINCT H.original_id, ");
		strQuery.append("                         r.ruledef2recom ");
		strQuery.append("         FROM   gets_tool_dpd_ruledef R, ");
		strQuery.append("                gets_tool_dpd_rulhis H ");
		strQuery.append("         WHERE  R.ruledef2finrul = H.rulhis2finrul ");
		strQuery.append("                AND rule_type IN ( 'Diagnostic' ) ");
		strQuery.append("                AND H.original_id IN ( %s )), ");
		strQuery.append("     rec ");
		strQuery.append("     AS (SELECT /*+ parallel(8) */  ");
		strQuery.append("                                   ar_detect2recom, ");
		strQuery.append("                                   ar.creation_date ");
		strQuery.append("         FROM   gets_tool_run r, ");
		strQuery.append("                gets_tool_subrun sr, ");
		strQuery.append("                gets_tool_ar_detect ar ");
		strQuery.append("         WHERE  subrun2run = r.objid ");
		strQuery.append("                AND tool_id = 'JDPAD' ");
		strQuery.append("                AND sr.objid = ar_detect2tol_subrun ");
		strQuery.append("                AND ar_detect2recom IS NOT NULL ");
		strQuery.append("                AND AR.creation_date BETWEEN SYSDATE - 30 AND SYSDATE ");
		strQuery.append("                AND R.diag_service_id NOT IN ( 'Alert', 'IPA' ) ");
		strQuery.append("                AND R.run2vehicle = :vehicleId ) ");
		strQuery.append("SELECT original_id, TO_CHAR(Max(creation_date)), Count(distinct AR_DETECT2RECOM) ");
		strQuery.append("FROM   rx, rec WHERE  rx.ruledef2recom = rec.ar_detect2recom(+) ");
		strQuery.append("GROUP  BY original_id");
		String ruleIds = getStringList(ruleList);
		String strQry = String.format(strQuery.toString(), ruleIds);

		Query queryOne = hibernateSession.createSQLQuery(strQry);
		queryOne.setFetchSize(700);
		queryOne.setParameter("vehicleId",vehicleId);
		List<Object> selectList = (List<Object>) queryOne.list();
		HealthRulesFiringVO healthRulesFiringVO=null;

		if (selectList != null && !selectList.isEmpty()) {
			for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
					.hasNext();) {
				Object[] healthyObject = (Object[]) lookValueIter.next();
				BigDecimal ruleId = (BigDecimal) healthyObject[0];
				boolean firingStatus = Long
						.valueOf(healthyObject[2].toString()) > 0;
									
				if (null != healthyObject[1]) {
					Timestamp timestamp;
					try {
						timestamp = new Timestamp(formatter.parse((String) healthyObject[1]).getTime());
					} catch (ParseException e) {
						timestamp = new Timestamp(0);
					}
					healthRulesFiringVO = new HealthRulesFiringVO(ruleId.longValue(), firingStatus,
							new java.util.Date(timestamp.getTime()));
				} else {
					healthRulesFiringVO = new HealthRulesFiringVO(ruleId.longValue(), firingStatus, new java.util.Date());
				}
				healthRulesFiringVOList.add(healthRulesFiringVO);
			}
		}else{
			for (Long ruleId : ruleList) {
				healthRulesFiringVO = new HealthRulesFiringVO(
						ruleId.longValue(), false, null);
				healthRulesFiringVOList.add(healthRulesFiringVO);
			}
		}
		return healthRulesFiringVOList;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, List<Long>> getRuleTypes(Session hibernateSession,List<Long> ruleList) {
		Map<String, List<Long>> ruleMap = null;
		Query displayNameQuery;		
		try {

			String strQuery = "SELECT DISTINCT RULE_TYPE, ORIGINAL_ID FROM GETS_TOOL_DPD_RULEDEF R, GETS_TOOL_DPD_RULHIS H WHERE R.RULEDEF2FINRUL = H.RULHIS2FINRUL AND rule_type in ('Diagnostic','Health') AND H.ORIGINAL_ID IN (%s)";
			String ruleIds = getStringList(ruleList);
			String strQry = String.format(strQuery, ruleIds);
			
			displayNameQuery = hibernateSession.createSQLQuery(strQry.toString());
			List<Object> selectList = (List<Object>)displayNameQuery.list();
			ruleMap = new HashMap<String, List<Long>>();
			if (selectList != null && !selectList.isEmpty()) {				
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] ruleObject = (Object[]) lookValueIter.next();
					
					String rulType = ruleObject[0].toString();
					Long ruleId = Long.valueOf(ruleObject[1].toString());
					if(ruleMap.containsKey(rulType)){
						//get list and add new value, put
						ruleMap.get(rulType).add(Long.valueOf(ruleId));
					}else{
						List<Long> rulList = new ArrayList<Long>();
						rulList.add(Long.valueOf(ruleId));
						ruleMap.put(rulType, rulList);
					}
				}
			}
		}catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getRuleTypes() "+e.getMessage(),e);
			throw e;
		}
		return ruleMap;
	}
	
	private List<HealthRuleFiringVO> fetchDiagnosticRules(Session hibernateSession,
			String vehicleId, List<Long> ruleList) throws RMDDAOException {

		List<HealthRuleFiringVO> healthRuleFiringVOList = new ArrayList<HealthRuleFiringVO>();
		//StringBuffer strQuery = new StringBuffer();		
		/*strQuery.append(
				"with main as ( select /*+ parallel(8)  temp.OBJID,temp.ORIGINAL_ID, max(detect.creation_date) creation_dt, count(*) over (partition by original_id) cnt ")
				.append("from ( SELECT distinct R.OBJID,H.ORIGINAL_ID , r.ruledef2recom FROM GETS_TOOL_DPD_RULEDEF R,GETS_TOOL_DPD_RULHIS H ")
				.append("WHERE R.RULEDEF2FINRUL = H.RULHIS2FINRUL and rule_type in ('Diagnostic') and active = 1 AND H.ORIGINAL_ID IN (%s) ) temp, ")
				.append("GETS_TOOL_AR_DETECT detect where  ar_detect2recom(+) = ruledef2recom group by temp.OBJID,temp.ORIGINAL_ID ")
				.append("), ")
				.append("SS_Data as ( select /*+ parallel(8)  distinct fltdown2rule_defn from gets_tool_fltdown d, gets_tool_fault f ")
				.append("where fault2vehicle in (select objid from gets_rmd_vehicle where lms_loco_id = :lmsLocoId ) and d.fltdown2fault = f.objid ")
				.append("and d.creation_date   BETWEEN sysdate-30 AND sysdate ) ")
				.append("select ORIGINAL_ID,creation_dt,count from ( select distinct a.ORIGINAL_ID,decode(fltdown2rule_defn,null,null,a.creation_dt) creation_dt, ")
				.append("decode(fltdown2rule_defn,null,0,a.cnt) count , rank () over(partition by original_id order by original_id, decode(fltdown2rule_defn,null,null,a.creation_dt) ")
				.append("desc nulls last ) rn from main A, SS_Data B where A.objid = B.fltdown2rule_defn(+) ) where rn=1 ");
		*/
		StringBuffer  strQuery = new StringBuffer();
		strQuery.append("WITH rx ");
		strQuery.append("     AS (SELECT DISTINCT H.original_id, ");
		strQuery.append("                         r.ruledef2recom ");
		strQuery.append("         FROM   gets_tool_dpd_ruledef R, ");
		strQuery.append("                gets_tool_dpd_rulhis H ");
		strQuery.append("         WHERE  R.ruledef2finrul = H.rulhis2finrul ");
		strQuery.append("                AND rule_type IN ( 'Diagnostic' ) ");
		strQuery.append("                AND H.original_id IN ( %s )), ");
		strQuery.append("     rec ");
		strQuery.append("     AS (SELECT /*+ parallel(8) */ ");
		strQuery.append("                                   ar_detect2recom, ");
		strQuery.append("                                   ar.creation_date ");
		strQuery.append("         FROM   gets_tool_run r, ");
		strQuery.append("                gets_tool_subrun sr, ");
		strQuery.append("                gets_tool_ar_detect ar ");
		strQuery.append("         WHERE  subrun2run = r.objid ");
		strQuery.append("                AND tool_id = 'JDPAD' ");
		strQuery.append("                AND sr.objid = ar_detect2tol_subrun ");
		strQuery.append("                AND ar_detect2recom IS NOT NULL ");
		strQuery.append("                AND AR.creation_date BETWEEN SYSDATE - 30 AND SYSDATE ");
		strQuery.append("                AND R.diag_service_id NOT IN ( 'Alert', 'IPA' ) ");
		strQuery.append("                AND R.run2vehicle = :vehicleId ) ");
		strQuery.append("SELECT original_id, TO_CHAR(Max(creation_date)), Count(distinct AR_DETECT2RECOM) ");
		strQuery.append("FROM   rx, rec WHERE  rx.ruledef2recom = rec.ar_detect2recom(+) ");
		strQuery.append("GROUP  BY original_id");
		String ruleIds = getStringList(ruleList);
		String strQry = String.format(strQuery.toString(), ruleIds);

		Query queryOne = hibernateSession.createSQLQuery(strQry);
		queryOne.setParameter("vehicleId", vehicleId);
		queryOne.setFetchSize(700);
		List<Object> selectList = (List<Object>) queryOne.list();
		HealthRuleFiringVO healthRuleFiringVO=null;
		if (selectList != null && !selectList.isEmpty()) {
			for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
					.hasNext();) {
				Object[] healthyObject = (Object[]) lookValueIter.next();
				BigDecimal ruleId = (BigDecimal) healthyObject[0];
				boolean firingStatus = Long
						.valueOf(healthyObject[2].toString()) > 0;
						
				if (null != healthyObject[1]) {
					Timestamp timestamp;
					try {
						timestamp = new Timestamp(formatter.parse((String) healthyObject[1]).getTime());
					} catch (ParseException e) {
						timestamp = new Timestamp(0);
					}
					healthRuleFiringVO = new HealthRuleFiringVO(ruleId.longValue(), firingStatus,
							new java.util.Date(timestamp.getTime()));
				} else {
					healthRuleFiringVO = new HealthRuleFiringVO(ruleId.longValue(), firingStatus, new java.util.Date());
				}
				healthRuleFiringVOList.add(healthRuleFiringVO);
			}
		}else{
			for (Long ruleId : ruleList) {
				healthRuleFiringVO = new HealthRuleFiringVO(ruleId, false, null);
				healthRuleFiringVOList.add(healthRuleFiringVO);
			}
		}
		return healthRuleFiringVOList;
	}
	@Override
	public List<LocomotivesCommResponseVO> getLocoCommunicationDetails(
			List<Long> locoList) throws RMDDAOException {
		List<LocomotivesCommResponseVO> locoCommResponseVOList = null;
		Query displayNameQuery;
		Session hibernateSession = null;
		
		try {
			hibernateSession = getHibernateSession();
			StringBuffer queryString = new StringBuffer();
			queryString
					.append("With Next_Maint_Date as ( select inbound2vehicle vehicleid ,next_maint_type ,next_maint_date from gets_b2b_csx_inbound where last_updated_date > sysdate - 7 ), ");
			queryString
					.append("EOA_Data as ( select v.vehicle_objid ,v.lms_loco_id ,v.org_id cust ,v.vehicle_hdr ,v.vehicle_no ,v.vehicle_hdr_no ,m.next_maint_type, ");
			queryString
					.append("to_char(m.next_maint_date, 'DD-MON-YYYY') next_maint_date ,CAST((FROM_TZ(CAST(Greatest(nvl(s.last_fault_date_flt, to_date('01-01-1900', 'MM-DD-YYYY')), ");
			queryString
					.append("nvl(s.last_fault_date_cel, to_date('01-01-1900', 'MM-DD-YYYY')), nvl(s.last_fault_date_stp, ");
			queryString
					.append("to_date('01-01-1900', 'MM-DD-YYYY')))  AS TIMESTAMP),'US/Eastern')) AS DATE) Last_Fault_Msg_Date ,s.last_fault_date_flt ,s.last_fault_date_cel, ");
			queryString
					.append("s.last_fault_date_stp ,CAST((FROM_TZ(CAST(s.lastppats_date AS TIMESTAMP),'US/Eastern')) AS DATE) Last_ATS_Msg_Date ");
			queryString
					.append("FROM gets_rmd_cust_rnh_rn_v v ,gets_rmd_asset_snapshot s ,Next_Maint_Date m where v.vehicle_objid = m.vehicleid(+) ");
			queryString
					.append("and v.vehicle_objid = s.vehicle_objid and v.lms_loco_id in (%s) ) ");
			queryString
					.append("Select d.vehicle_objid ,d.lms_loco_id ,d.cust ,d.vehicle_hdr ,d.vehicle_no ,d.next_maint_type,d.next_maint_date, ");
			queryString
					.append("case when d.Last_Fault_Msg_Date > to_date('01-01-2000', 'MM-DD-YYYY') then d.Last_Fault_Msg_Date else null end Last_Fault_Msg_Date, ");
			queryString
					.append("d.Last_ATS_Msg_Date,CAST((FROM_TZ(CAST(max(t.date_timestamp) AS TIMESTAMP),'US/Eastern')) AS DATE) Last_TO_Status_Msg_Date ");
			queryString
					.append("from EOA_Data d ,todw_locostatus@emetrics_rwdb t where d.vehicle_no = t.road_number(+) and d.vehicle_hdr_no = t.vehicle_header_number(+) ");
			queryString
					.append("group by d.vehicle_objid ,d.lms_loco_id,d.cust,d.vehicle_hdr,d.vehicle_no ,d.vehicle_hdr_no ,d.next_maint_type , ");
			queryString
					.append("d.next_maint_date ,d.Last_Fault_Msg_Date ,d.Last_ATS_Msg_Date");
			
			String locoIds = getStringList(locoList);
			String strQry = String.format(queryString.toString(), locoIds);
		
			displayNameQuery = hibernateSession.createSQLQuery(strQry);
			List<Object> selectList = (List<Object>)displayNameQuery.list();
			
			List<Long> dataExistLocoList = new ArrayList<Long>();
			locoCommResponseVOList = new ArrayList<LocomotivesCommResponseVO>();
			if (selectList != null && !selectList.isEmpty()) {			
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] commObject = (Object[]) lookValueIter.next();
					
					LocomotivesCommResponseVO commResponseVO = new LocomotivesCommResponseVO();
					commResponseVO.setLocomotiveId(Long.valueOf(commObject[1].toString()));
					commResponseVO.setRoadNumber(commObject[3].toString());
					commResponseVO.setVehicleHeader(commObject[4].toString());
					
					if (null != commObject[5]) {
						commResponseVO.setNextMaintenanceType(Long
								.valueOf(commObject[5].toString()));
					} else {
						commResponseVO.setNextMaintenanceType(-1);
					}

					if (null != commObject[6]) {
						commResponseVO.setNextMaintenanceDate(commObject[6]
								.toString());
					}
					if (null != commObject[7]) {
						commResponseVO.setLastFaultMsgDate(commObject[7]
								.toString());
					}
					if (null != commObject[8]) {
						commResponseVO.setLastATSMsgDate(commObject[8]
								.toString());
					}
					if (null != commObject[9]) {
						commResponseVO.setLastTOStatusMsgDate(commObject[9]
								.toString());
					}
					locoCommResponseVOList.add(commResponseVO);
					dataExistLocoList.add(commResponseVO.getLocomotiveId());
				}
			}
			
			List<Long> dataNotExistLocoList = new ArrayList<Long>();
			for(Long locoId : locoList){
				if(!dataExistLocoList.contains(locoId)){
					dataNotExistLocoList.add(locoId);
				}
			}
			
			if(dataNotExistLocoList.size() > 0){
				getLocoDetails(hibernateSession,dataNotExistLocoList,locoCommResponseVOList);
			}
			
		}catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getLocoCommunicationDetails() "+e.getMessage(),e);
			throw e;
		}finally {
			releaseSession(hibernateSession);
		}
		return locoCommResponseVOList;
	}
	
	public void  getLocoDetails(Session hibernateSession,
			List<Long> locoList,List<LocomotivesCommResponseVO> locoCommResponseVOList) throws RMDDAOException {
		Query displayNameQuery;
		
		try {
			
			StringBuffer queryString = new StringBuffer();
			queryString.append("SELECT lms_loco_id,tsp.serial_no, vehhdr.veh_hdr ");
			queryString
					.append("FROM table_site_part tsp, gets_rmd_vehicle veh, gets_rmd_veh_hdr vehhdr,table_bus_org tbo ");
			queryString
					.append(" WHERE tsp.objid = veh.vehicle2site_part AND veh.vehicle2veh_hdr = vehhdr.objid AND vehhdr.veh_hdr2busorg = tbo.objid and INSTR((tsp.serial_no),'BAD') = 0 and  lms_loco_id in (%s)");
			
			String locoIds = getStringList(locoList);
			String strQry = String.format(queryString.toString(), locoIds);
		
			displayNameQuery = hibernateSession.createSQLQuery(strQry);
			List<Object> selectList = (List<Object>)displayNameQuery.list();
			
			if (selectList != null && !selectList.isEmpty()) {
				for (Iterator<Object> lookValueIter = selectList.iterator(); lookValueIter
						.hasNext();) {
					Object[] commObject = (Object[]) lookValueIter.next();

					LocomotivesCommResponseVO LocoCommResponseVO = new LocomotivesCommResponseVO(
							Long.valueOf(commObject[0].toString()),
							commObject[1].toString(), commObject[2].toString(),
							-1, null, null, null, null);
					locoCommResponseVOList.add(LocoCommResponseVO);
				}
			}
		}catch (RMDDAOException e) {
			LOG.error("Error occured in LhrDAOImpl.getLocoDetails() "+e.getMessage(),e);
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	private String validateLocoId(String locoId, Session hibernateSession)
			throws RMDDAOException {
		String vehicleId = "0";
		String strQry = "SELECT objid FROM GETS_RMD_VEHICLE WHERE LMS_LOCO_ID = :locoId";
		Query query = hibernateSession.createSQLQuery(strQry);
		query.setParameter("locoId", locoId);
		List<BigDecimal> resultList = query.list();
		 if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
             vehicleId = resultList.get(0).toString();
		 }
	return vehicleId;
	}
}
