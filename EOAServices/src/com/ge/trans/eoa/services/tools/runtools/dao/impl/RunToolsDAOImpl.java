package com.ge.trans.eoa.services.tools.runtools.dao.impl;

/**
 * ============================================================
 * File : RunToolsDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.tools.runtools.dao.impl
 * Author : 
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import getsrmd.sql.GetsRmdProgramDef;
import getstool.mq.MQQueueOps;
import getstool.sql.GetsToolRun;
import getstool.sql.GetsToolSubrun;
import getstool.sql.GetsToolSubrunObject;
import getstool.sql.TableSitePart;
import getstool.tools.job.Tuple;
import getstool.toolscheduler.ThreadStatusEnum;
import getstool.toolscheduler.ToolScheduler;
import getstool.toolscheduler.ToolSchedulerLogging;
import getstool.toolscheduler.ToolSchedulerThread;
import getstool.toolscheduler.ToolSchedulerThreadDBGetUtils;
import getstool.toolscheduler.ToolSchedulerThreadDBUpdateUtils;
import getstool.toolscheduler.ToolSchedulerThreadData;
import getstool.toolscheduler.ToolSchedulerThreadMQUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.cases.dao.impl.CaseEoaDAOImpl;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.tools.runtools.dao.intf.RunToolsDAOIntf;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.DaignosticServiceVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.FaultDetailsVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolDiagnosticRunDetailsVO;
import com.ge.trans.eoa.services.tools.runtools.service.valueobjects.ToolRunRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

@SuppressWarnings("serial")
public class RunToolsDAOImpl extends BaseDAO implements RunToolsDAOIntf {

	private PopupListAdminDAOIntf popupListAdminDAO;
	
	public void setPopupListAdminDAO(PopupListAdminDAOIntf popupListAdminDAO) {
		this.popupListAdminDAO = popupListAdminDAO;
	}
	
	public static final RMDLogger LOG = RMDLogger
			.getLogger(CaseEoaDAOImpl.class);

	@Override
	public String getNextScheduleRun(String vehicleObjid)
			throws RMDDAOException {

		Session objHibernateSession = null;
		String nextToolRun = RMDCommonConstants.EMPTY_STRING;

		final StringBuilder toolNextRun = new StringBuilder();
		try {
			objHibernateSession = getHibernateSession();
			toolNextRun
					.append("SELECT TOOL_RUN_NEXT FROM GETS_RMD_VEHICLE WHERE OBJID =:objId");
			Query queryToolNextRun = objHibernateSession
					.createSQLQuery(toolNextRun.toString());
			queryToolNextRun.setParameter(RMDCommonConstants.OBJ_ID,
					vehicleObjid);
			nextToolRun = (String) queryToolNextRun.uniqueResult();

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return nextToolRun;

	}

	public ToolDiagnosticRunDetailsVO getLastDiagnosticRunDetails(
			String vehicleObjid) throws RMDDAOException {

		Session objHibernateSession = null;
		final StringBuilder toolNextRun = new StringBuilder();
		ToolDiagnosticRunDetailsVO objToolDiagnosticRunDetailsVO = null;
		List<Object[]> objList = null;
		try {
			objHibernateSession = getHibernateSession();
			toolNextRun
					.append("SELECT RUN_CPT,  DIAG_SERVICE_ID FROM GETS_TOOL_RUN WHERE OBJID IN  (SELECT MAX(OBJID)  FROM GETS_TOOL_RUN")
					.append("WHERE RUN2VEHICLE    = :objId  AND CREATION_DATE    > SYSDATE - 7  AND DIAG_SERVICE_ID IN ('DHMS', 'EOA', 'ESTP', 'MNS', 'OHV', 'OIL', 'QNX Equipment')")
					.append("AND RUN_CPT         IS NOT NULL)");
			Query queryToolNextRun = objHibernateSession
					.createSQLQuery(toolNextRun.toString());
			queryToolNextRun.setParameter(RMDCommonConstants.OBJ_ID,
					vehicleObjid);
			objList = queryToolNextRun.list();
			if (RMDCommonUtility.isCollectionNotEmpty(objList)) {
				objToolDiagnosticRunDetailsVO = new ToolDiagnosticRunDetailsVO();
				for (Object[] obj : objList) {
					objToolDiagnosticRunDetailsVO
							.setLastToolRun(RMDCommonUtility
									.convertObjectToString(obj[0]));
					objToolDiagnosticRunDetailsVO
							.setDiagnosticService(RMDCommonUtility
									.convertObjectToString(obj[1]));

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return objToolDiagnosticRunDetailsVO;

	}

	public String runTools(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDDAOException {
		Session objSession = null;
		String result = RMDCommonConstants.FAILURE;
		
		try {

			if ((null != ObjToolRunRequestVO.getScheduleRunDate() && !RMDCommonConstants.EMPTY_STRING
					.equalsIgnoreCase(ObjToolRunRequestVO.getScheduleRunDate()))
					|| ObjToolRunRequestVO.isCurrentDateRun()) {
				updateToolRunNext(ObjToolRunRequestVO);
				result = RMDCommonConstants.SUCCESS;
			} else {
				
				if(ObjToolRunRequestVO.isRunOnPastData()){
					ObjToolRunRequestVO.setLookbackRange(getLookUpValue("TOOL_RUN_PAST_DATA_LOOKUP"));
					ObjToolRunRequestVO.setDiagnosticService(getLookUpValue("DEFAULT_TOOL_RUN_DIAGNOSTIC_SERVICES"));
					getVehicleDetails(ObjToolRunRequestVO);
				}
				runToolsOnPastDate(ObjToolRunRequestVO);
				
				result = RMDCommonConstants.SUCCESS;
			}

		} catch (Exception e) {
			LOG.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RUN_TOOLS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {

			releaseSession(objSession);
		}

		return result;
	}

	public List<ElementVO> getDiagnosticServices(String vehicleObjid)
			throws RMDDAOException {

		Session objHibernateSession = null;
		List<String> objList = null;
		List<ElementVO> lstElementVO = new ArrayList<ElementVO>();
		final StringBuilder diagnosticServices = new StringBuilder();
		ElementVO objElementVO = null;
		try {
			objHibernateSession = getHibernateSession();
			diagnosticServices
					.append("SELECT DISTINCT DECODE(MASTERBOM.CONFIG_ITEM,'Service_oil','OIL','Service_offboard_eoa','EOA','Service_offboard_estp','ESTP','Service_ohv','OHV','Service_mns','MNS','Service_dhms','DHMS','Service_fault_gen','QNX Equipment','CMU','QNX Equipment',MASTERBOM.CONFIG_ITEM) SERVICE")
					.append(" FROM GETS_RMD_VEHICLE RMDVEHICLE,  TABLE_SITE_PART SITEPART,  GETS_RMD_VEHCFG VEHICLECONFIG,  GETS_RMD_MASTER_BOM MASTERBOM")
					.append(" WHERE VEHICLE2SITE_PART           = SITEPART.OBJID AND VEH_CFG2VEHICLE               = RMDVEHICLE.OBJID AND VEHCFG2MASTER_BOM             = MASTERBOM.OBJID AND MASTERBOM.BOM_STATUS          = 'Y'")
					.append(" AND ((CONFIG_ITEM                IN ('Service_oil','Service_mns','Service_fault_gen','Service_dhms','Service_ohv','Service_offboard_eoa','Service_offboard_estp')")
					.append(" AND VEHICLECONFIG.CURRENT_VERSION = '1')")
					.append(" OR (CONFIG_ITEM                  IN ('CMU')")
					.append(" AND VEHICLECONFIG.CURRENT_VERSION = '2'))")
					.append(" AND RMDVEHICLE.OBJID              =:objId");
			Query diagnosticServicesQry = objHibernateSession
					.createSQLQuery(diagnosticServices.toString());
			diagnosticServicesQry.setParameter(RMDCommonConstants.OBJ_ID,
					vehicleObjid);
			objList = diagnosticServicesQry.list();
			for (String obj : objList) {
				objElementVO = new ElementVO();
				objElementVO.setId(obj);
				objElementVO.setName(obj);
				lstElementVO.add(objElementVO);

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return lstElementVO;

	}

	public List<DaignosticServiceVO> getToolIds(String diagServiceId)
			throws RMDDAOException {
		Session objHibernateSession = null;
		List<Object[]> objList = null;
		List<DaignosticServiceVO> lstDaignosticServiceVO = new ArrayList<DaignosticServiceVO>();
		final StringBuilder diagnosticServices = new StringBuilder();
		DaignosticServiceVO objDaignosticServiceVO = null;
		try {
			objHibernateSession = getHibernateSession();
			diagnosticServices
					.append("SELECT DISTINCT TOOL_ID,  LOOK_BACK,  CHK_LOOK_BACK,  PORT_NO")
					.append(" FROM GETS_TOOL_EXEC_CFG")
					.append(" WHERE DIAG_SERVICE_ID = :diagServiceId");
			Query diagnosticServicesQry = objHibernateSession
					.createSQLQuery(diagnosticServices.toString());
			diagnosticServicesQry.setParameter(
					RMDCommonConstants.DIAGNOSTIC_SERVICE_ID, diagServiceId);
			objList = diagnosticServicesQry.list();
			for (Object[] obj : objList) {
				objDaignosticServiceVO = new DaignosticServiceVO();
				objDaignosticServiceVO.setToolId(RMDCommonUtility
						.convertObjectToString(obj[0]));
				objDaignosticServiceVO.setLookBack(RMDCommonUtility
						.convertObjectToString(obj[1]));
				objDaignosticServiceVO.setChkLookBack(RMDCommonUtility
						.convertObjectToString(obj[2]));
				objDaignosticServiceVO.setPortNo(RMDCommonUtility
						.convertObjectToString(obj[3]));
				lstDaignosticServiceVO.add(objDaignosticServiceVO);

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return lstDaignosticServiceVO;

	}

	public String insertToolSubRun(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDDAOException {
		Session objSession = null;
		String result = RMDCommonConstants.FAILURE_MSG;

		try {

			objSession = getHibernateSession();
			StringBuilder insertSubRun = new StringBuilder();
			insertSubRun
					.append("INSERT INTO GETS_TOOL_SUBRUN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY, LOOKBACK_USED,LOOKBKMIN_OBJID,LOOKBKMAX_OBJID,TOOL_ID,SUBRUN2RUN) VALUES( GETS_TOOL_SUBRUN_SEQ.NEXTVAL, sysdate, :lastUpdatedBy, sysdate, :createdBy, :lookBackUsed, :lookbackMinObjid, :lookbackMaxObjid, :toolId, :subRun2Run )");
			Query insertSubRunQry = objSession.createSQLQuery(insertSubRun
					.toString());
			insertSubRunQry.executeUpdate();
			result = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			LOG.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_CASE_DETIALS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {

			releaseSession(objSession);
		}

		return result;
	}

	public String insertToolRun(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDDAOException {
		Session objSession = null;
		String result = RMDCommonConstants.FAILURE_MSG;

		try {
			objSession = getHibernateSession();
			StringBuilder insertToolRun = new StringBuilder();
			insertToolRun
					.append("INSERT INTO GETS_TOOL_RUN (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RUN_START,RUN_PRIORITY, RUN_PROCESSEDMIN_OBJID,RUN_PROCESSEDMAX_OBJID, RUN2VEHICLE, DIAG_SERVICE_ID) VALUES( :objid,sysdate,:lastUpdatedBy,sysdate,:createdBy,sysdate,:runPriority,:lookbackMinObjid,:lookbackMaxObjid,:vehicleObjid,:diagServiceId)");
			Query insertRunQry = objSession.createSQLQuery(insertToolRun
					.toString());
			insertRunQry.executeUpdate();
			result = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			LOG.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_CASE_DETIALS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {

			releaseSession(objSession);
		}

		return result;
	}

	public FaultDetailsVO getFaultMinMaxObjids(
			ToolRunRequestVO ObjToolRunRequestVO) throws RMDDAOException {
		Session objHibernateSession = null;
		List<Object[]> objList = null;

		final StringBuilder strFaultDetails = new StringBuilder();
		FaultDetailsVO objFaultDetailsVO = null;
		try {
			objHibernateSession = getHibernateSession();
			strFaultDetails
					.append("SELECT MIN(OBJID), MAX(OBJID) FROM GETS_TOOL_FAULT WHERE FAULT2VEHICLE = :vehicleObjid AND RECORD_TYPE IN (SELECT FAULT_RECORD_TYPE FROM GETS_RMD.GETS_RMD_SVC_TO_FAULT WHERE DIAG_SERVICE_ID =:diagServiceId)")
					.append(" AND OFFBOARD_LOAD_DATE >= SYSDATE - :LOOKBACK")
					.append(" AND OFFBOARD_LOAD_DATE <= SYSDATE");
			Query faultDetailsQry = objHibernateSession
					.createSQLQuery(strFaultDetails.toString());
			faultDetailsQry.setParameter(
					RMDCommonConstants.DIAGNOSTIC_SERVICE_ID,
					ObjToolRunRequestVO.getDiagnosticService());
			faultDetailsQry.setParameter(RMDCommonConstants.VEHICLE_OBJID,
					ObjToolRunRequestVO.getVehicleObjid());
			faultDetailsQry.setParameter(RMDCommonConstants.LOOKBACK,
					ObjToolRunRequestVO.getLookbackRange());
			objList = faultDetailsQry.list();
			for (Object[] obj : objList) {
				objFaultDetailsVO = new FaultDetailsVO();
				objFaultDetailsVO.setMinFaultObjid(RMDCommonUtility
						.convertObjectToString(obj[0]));
				objFaultDetailsVO.setMaxFaultObjid(RMDCommonUtility
						.convertObjectToString(obj[1]));
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST);
		} finally {
			releaseSession(objHibernateSession);
		}
		return objFaultDetailsVO;

	}

	public String updateToolRunNext(ToolRunRequestVO ObjToolRunRequestVO)
			throws RMDDAOException {
		Session objSession = null;
		String result = RMDCommonConstants.FAILURE_MSG;
		try {
			objSession = getHibernateSession();
			StringBuilder updateToolRunNext = new StringBuilder();

			if (ObjToolRunRequestVO.isCurrentDateRun()) {
				updateToolRunNext
						.append("UPDATE GETS_RMD_VEHICLE SET TOOL_RUN_NEXT=SYSDATE,LAST_UPDATED_BY=:userId");
			} else if (null != ObjToolRunRequestVO.getScheduleRunDate()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(ObjToolRunRequestVO
									.getScheduleRunDate())) {
				updateToolRunNext
						.append("UPDATE GETS_RMD_VEHICLE SET TOOL_RUN_NEXT=to_date(:scheduledDate,'dd-mon-yyyy hh24:mi:ss'),LAST_UPDATED_BY=:userId");
			}
			
			if (ObjToolRunRequestVO.isIncludeShopData()) {
				updateToolRunNext
						.append(",PROCESS_SHOP_FAULTS=1");
			} else{
				updateToolRunNext
						.append(",PROCESS_SHOP_FAULTS=0");
			}
			updateToolRunNext
			.append(" WHERE OBJID=:objId");			

			Query updateToolRunNextQry = objSession
					.createSQLQuery(updateToolRunNext.toString());
			if (null != ObjToolRunRequestVO.getScheduleRunDate()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(ObjToolRunRequestVO
									.getScheduleRunDate())
					&& !ObjToolRunRequestVO.isCurrentDateRun()) {
				updateToolRunNextQry.setParameter(
						RMDCommonConstants.SCHEDULED_DATE,
						ObjToolRunRequestVO.getScheduleRunDate());
			}

			if (null != ObjToolRunRequestVO.getUserName()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(ObjToolRunRequestVO.getUserName())) {
				updateToolRunNextQry.setParameter(RMDCommonConstants.USERID,
						ObjToolRunRequestVO.getUserName());
			}
			updateToolRunNextQry.setParameter(RMDCommonConstants.OBJ_ID,
					Long.valueOf(ObjToolRunRequestVO.getVehicleObjid()));
			updateToolRunNextQry.executeUpdate();
			result = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			LOG.error("Exception occurred:", e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_TOOL_RUN_NEXT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {

			releaseSession(objSession);
		}

		return result;
	}

	public String runToolsOnPastDate(ToolRunRequestVO ObjToolRunRequestVO)
			throws NumberFormatException, SQLException, Exception {
		Session objSession = null;
		String result = RMDCommonConstants.SUCCESS;
		List<DaignosticServiceVO> lstDaignosticService = null;
		ToolSchedulerThreadData data = new ToolSchedulerThreadData(0,
				ToolSchedulerThread.class.getSimpleName());
		ToolSchedulerThreadDBGetUtils dbGetUtils;
		ToolSchedulerThreadMQUtils mqUtils;
		String strPriority = null;
		Tuple<Long, Long> faultDataRanges = null;
		Connection connection = null;
		String userName = ObjToolRunRequestVO.getUserName();
		GetsToolSubrunObject objectCbr = new GetsToolSubrunObject();
		GetsToolSubrunObject objectJdpad = new GetsToolSubrunObject();
		GetsToolSubrunObject objectFilter = new GetsToolSubrunObject();
		GetsToolSubrunObject objectCr = new GetsToolSubrunObject();
		GetsToolSubrunObject objectVirtual = new GetsToolSubrunObject();
		GetsToolSubrunObject objectAnomaly = new GetsToolSubrunObject();
		try {
			if (null != ObjToolRunRequestVO.getDiagnosticService()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(ObjToolRunRequestVO
									.getDiagnosticService())) {
				objSession = getHibernateSession();
				connection = getConnection(objSession);
				MQQueueOps mqQueueOps = null;				
				data.setTableSitePart(new TableSitePart(
						ToolSchedulerLogging.writeLog, ToolScheduler.getDoLog()));
				dbGetUtils = new ToolSchedulerThreadDBGetUtils(data);
				int lookbackUsed = Integer.parseInt(ObjToolRunRequestVO
						.getLookbackRange()) / 24;

				lstDaignosticService = getToolIds(ObjToolRunRequestVO
						.getDiagnosticService());
				faultDataRanges = dbGetUtils.getMinMaxObjids(connection,
						lookbackUsed,
						Long.valueOf(ObjToolRunRequestVO.getVehicleObjid()),
						ObjToolRunRequestVO.getDiagnosticService());
				if (faultDataRanges.getFirst() != 0 && faultDataRanges.getSecond() != 0) {
				mqUtils = new ToolSchedulerThreadMQUtils(data);
				strPriority = "3";
				int priority = 0;
				if (strPriority != null) {
					priority = Integer.parseInt(strPriority);
				}

				TableSitePart objTableSitePart = new TableSitePart(
						ToolSchedulerLogging.writeLog, ToolScheduler.getDoLog());
				objTableSitePart.setSerialNo(ObjToolRunRequestVO
						.getAssetNumber());
				objTableSitePart.setXVehHdr(ObjToolRunRequestVO
						.getAssetHeader());
				data.setInsertedToolRunObjid(GetsToolRun
						.selectNextval(connection));
				data.setCurrentVehicleObjid(Integer
						.parseInt(ObjToolRunRequestVO.getVehicleObjid()));
				data.setDiagServType(ObjToolRunRequestVO.getDiagnosticService());

				data.setTableSitePart(objTableSitePart);
				int result1 = GetsToolRun
						.insert(connection, userName, data
								.getInsertedToolRunObjid(),
								Integer.parseInt(ObjToolRunRequestVO
										.getVehicleObjid()), priority,
								faultDataRanges.getFirst(), faultDataRanges
										.getSecond(), ObjToolRunRequestVO
										.getDiagnosticService());
				if (result1 != 1) {
					return "Failure";
				}
				GetsToolSubrun subrun = new GetsToolSubrun(
						ToolSchedulerLogging.writeLog, ToolScheduler.getDoLog());

				for (DaignosticServiceVO diagnosticServiceVo : lstDaignosticService) {
					int subRunresult = 0;
					if (("CBR").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectCbr.setLookbackUsed(lookbackUsed);
						objectCbr
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectCbr.setLookbkminObjid(faultDataRanges.getFirst());
						objectCbr.setToolId(diagnosticServiceVo.getToolId());
						objectCbr.setCreateSubrun(true);
					} else if (("CR").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectCr.setLookbackUsed(lookbackUsed);
						objectCr
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectCr.setLookbkminObjid(faultDataRanges.getFirst());
						objectCr.setToolId(diagnosticServiceVo.getToolId());
						objectCr.setCreateSubrun(true);
					} else if (("JDPAD").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectJdpad.setLookbackUsed(lookbackUsed);
						objectJdpad
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectJdpad.setLookbkminObjid(faultDataRanges.getFirst());
						objectJdpad.setToolId(diagnosticServiceVo.getToolId());
						objectJdpad.setCreateSubrun(true);
					} else if (("VIRTUAL").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectVirtual.setLookbackUsed(lookbackUsed);
						objectVirtual
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectVirtual.setLookbkminObjid(faultDataRanges.getFirst());
						objectVirtual
								.setToolId(diagnosticServiceVo.getToolId());
						objectVirtual.setCreateSubrun(true);
					} else if (("ANOMALY").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectAnomaly.setLookbackUsed(lookbackUsed);
						objectAnomaly
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectAnomaly.setLookbkminObjid(faultDataRanges.getFirst());
						objectAnomaly
								.setToolId(diagnosticServiceVo.getToolId());
						objectAnomaly.setCreateSubrun(true);
					} else if (("FILTER").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) {
						objectFilter.setLookbackUsed(lookbackUsed);
						objectFilter
								.setLookbkmaxObjid(faultDataRanges.getSecond());
						objectFilter.setLookbkminObjid(faultDataRanges.getFirst());
						objectFilter.setToolId(diagnosticServiceVo.getToolId());
						objectFilter.setCreateSubrun(true);
					}
					if (("FILTER").equalsIgnoreCase(diagnosticServiceVo
							.getToolId())) { 
					int filterObjid = GetsToolSubrun.selectNextval(connection);
					if (filterObjid < ThreadStatusEnum.STATUS_SUCCESS.getValue()) {
						return "Failure";
						}
					data.setInsertedToolSubrunFilterObjid(filterObjid);
					subRunresult = GetsToolSubrun.insert(connection, userName, data.getInsertedToolRunObjid(),
							objectFilter.getToolId(), objectFilter.getLookbkminObjid(),
							objectFilter.getLookbkmaxObjid(), objectFilter.getLookbackUsed(),
							data.getInsertedToolSubrunFilterObjid());
					}else{
					subRunresult = subrun.insert(connection, userName,
							data.getInsertedToolRunObjid(),
							diagnosticServiceVo.getToolId(),
							faultDataRanges.getFirst(),
							faultDataRanges.getSecond(), lookbackUsed);
					}
					if (subRunresult != ThreadStatusEnum.STATUS_SUCCESS.getValue()) {
						 return "Failure";
						}
				}

				GetsRmdProgramDef cfg = new GetsRmdProgramDef(
						ToolSchedulerLogging.writeLog, true);
				int returnValue = cfg.selectByProgramIdAndInstanceId(
						connection, ToolSchedulerLogging.programId, 1);
				if (returnValue == 1) {
					mqQueueOps = MQQueueOps.getConnectionMgrInstance(0);
					ToolScheduler.setRmdProgramDef(new GetsRmdProgramDef(
							ToolSchedulerLogging.writeLog, ToolScheduler
									.getDoLog()));
					ToolScheduler.getRmdProgramDef()
							.setSecondaryOutputQueueName(
									cfg.getSecondaryOutputQueueName());
					ToolScheduler.getRmdProgramDef().setDefaultOutputQueueName(
							cfg.getDefaultOutputQueueName());
					ToolScheduler.getRmdProgramDef().setQueueMgrName(
							cfg.getQueueMgrName());

					mqUtils.outputQueue(priority, objectCbr, objectJdpad,
							objectFilter, objectCr, objectVirtual,
							objectAnomaly, connection);
					if (mqQueueOps.isConnected()) {
						mqQueueOps.commitMQ();
					}
				}
				}
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			connection.close();
			releaseSession(objSession);
		}

		return result;

	}
	
	
	
	
	public String getLookUpValue(String listName) {

		String lookupValue = null;
		try {
			List<GetSysLookupVO> arlSysLookUp = popupListAdminDAO.getPopupListValues(listName);
			if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
				for (GetSysLookupVO objSysLookupVO : arlSysLookUp) {
					lookupValue=objSysLookupVO.getLookValue();
				}
			}
		} catch (RMDDAOException e) {
			throw e;
		}
		return lookupValue;
	}

	
	 private void getVehicleDetails(ToolRunRequestVO objToolRunRequestVO)
	            throws RMDDAOException {
	        List<ElementVO> arlElementVOs = new ArrayList<ElementVO>();
	        Session session = null;
	        Query hibernateQuery = null;
	        StringBuilder roadIntialQuery = new StringBuilder();
	        try {
	            session = getHibernateSession();
	            roadIntialQuery
	                    .append("SELECT DISTINCT VEHICLE_HDR,VEHICLE_NO FROM GETS_RMD_CUST_RNH_RN_V ");
	            
	            if(!RMDCommonUtility.isNullOrEmpty(objToolRunRequestVO.getVehicleObjid())) {
	            	roadIntialQuery = roadIntialQuery.append("WHERE VEHICLE_OBJID=:strObjId");
				}
	            
	            	
	            hibernateQuery = session.createSQLQuery(roadIntialQuery.toString());
	            if(!RMDCommonUtility.isNullOrEmpty(objToolRunRequestVO.getVehicleObjid())) {
	            	hibernateQuery.setParameter(RMDServiceConstants.OBJID, objToolRunRequestVO.getVehicleObjid());
	            }
	            hibernateQuery.setFetchSize(100);
	            List<Object[]> roadIntialsList = hibernateQuery.list();
	            for (Object[] currentRoadIntial : roadIntialsList) {
	            	objToolRunRequestVO.setAssetNumber(RMDCommonUtility
	                        .convertObjectToString(currentRoadIntial[1]));
	            	objToolRunRequestVO.setAssetHeader(RMDCommonUtility
	                        .convertObjectToString(currentRoadIntial[0]));	                
	            }
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MINOR_ERROR);
	        } finally {
	            releaseSession(session);
	        }
	       
	    }
}
