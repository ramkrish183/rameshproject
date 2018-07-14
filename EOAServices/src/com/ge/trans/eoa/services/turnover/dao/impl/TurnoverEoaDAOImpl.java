package com.ge.trans.eoa.services.turnover.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.turnover.dao.intf.TurnoverEoaDAOIntf;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class TurnoverEoaDAOImpl extends BaseDAO implements TurnoverEoaDAOIntf {

	private static final long serialVersionUID = -5259728318291027569L;

	@SuppressWarnings("unchecked")
	@Override
	public List<GetInboundTurnoverVO> getInboundReportData()
			throws RMDDAOException {

		Session session = null;
		List<GetInboundTurnoverVO> objVOlist = null;
		try {
			session = getHibernateSession();

			StringBuilder varname1 = new StringBuilder();
			
			varname1.append("SELECT cas.x_veh_hdr,a.repair_desc, COUNT(1) FROM table_case_display cas,GETS_SD_REPAIR_CODES a,GETS_SD_FINAL_REPCODE rep");
			varname1.append(" WHERE cas.objid= rep.final_repcode2case AND rep.final_repcode2repcode = a.objid AND title LIKE '%Inbound Review%' AND creation_time >= TO_DATE( sysdate) -1 AND creation_time < TO_DATE(sysdate)  GROUP BY cas.x_veh_hdr,a.repair_desc ");
			Query inboundRepHqry = session.createSQLQuery(varname1.toString());
			inboundRepHqry.setFetchSize(500);
			List<Object[]> resultList = null;
			resultList = inboundRepHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				objVOlist = new ArrayList<GetInboundTurnoverVO>();
				for (final Object[] inboundTurnoverVO : resultList) {
					GetInboundTurnoverVO itVO = new GetInboundTurnoverVO();
					itVO.setRoadNumberHeader(RMDCommonUtility.convertObjectToString(inboundTurnoverVO[0]));
					itVO.setGpocComments(RMDCommonUtility.convertObjectToString(inboundTurnoverVO[1]));
					itVO.setCount(RMDCommonUtility.convertObjectToString(inboundTurnoverVO[2]));
					objVOlist.add(itVO);
				}
			}
		} catch (Exception ex) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPUP_LISTADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} finally {
			releaseSession(session);
		}

		return objVOlist;

	}

	@Override
	public List<CallLogVO> getCallCountByLocation() throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		CallLogVO objCallLogVO = null;
		List<CallLogVO> arlCallCountByLoc = null;
		StringBuilder callCountQuery =null;
		try {
			session = getHibernateSession();
			callCountQuery = new StringBuilder();
			callCountQuery.append("select DECODE(location,'null','No Location',location) location,count(objid) from GETS_RMD.GETS_SFDC_CALL_LOGS where ISSUE_TYPE in (SELECT LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_ISSUE_TYPE') ");
			callCountQuery.append("AND CREATION_DATE >=  TO_DATE(TO_CHAR(SYSDATE-(select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_DEFAULT_DAYS'), 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) AND ");
			callCountQuery.append("CREATION_DATE <=  TO_DATE(TO_CHAR(SYSDATE, 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) group by LOCATION order by 2 desc");

			Query callCountHQuery = session.createSQLQuery(callCountQuery.toString());
			callCountHQuery.setFetchSize(1000);
			resultList = callCountHQuery.list();
			
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
			    arlCallCountByLoc = new ArrayList<CallLogVO>(resultList.size());
				 for(Object[] countByLocObj : resultList){   
					objCallLogVO = new CallLogVO();
					objCallLogVO.setLocation(RMDCommonUtility
								.convertObjectToString(countByLocObj[0]));
					objCallLogVO.setCount(RMDCommonUtility
							.convertObjectToString(countByLocObj[1]));
					arlCallCountByLoc.add(objCallLogVO);
				}
			}
			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CALL_COUNT_BY_LOCATION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CALL_COUNT_BY_LOCATION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
		    callCountQuery = null;
		    resultList = null;
			releaseSession(session);
			
		}
		return arlCallCountByLoc;
	}
	
	@Override
    public List<CallLogVO> getCustBrkDownByMins() throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        CallLogVO objCallLogVO = null;
        List<CallLogVO> arlCustBrkDwnByMins = null;
        StringBuilder custBrkDwnByMinsQry =null;
        try {
            session = getHibernateSession();
            custBrkDwnByMinsQry = new StringBuilder();
            custBrkDwnByMinsQry.append("SELECT DECODE(OMD_CUST_ID,'null','No Customer',OMD_CUST_ID) OMD_CUST_ID,sum(CALL_DURATION_MINUTES) from GETS_RMD.GETS_SFDC_CALL_LOGS where CALL_DURATION_MINUTES is not null ");
            custBrkDwnByMinsQry.append("AND CREATION_DATE >= TO_DATE(TO_CHAR(SYSDATE-(select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_DEFAULT_DAYS'), 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
            custBrkDwnByMinsQry.append("AND CREATION_DATE <= TO_DATE(TO_CHAR(SYSDATE, 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) group by OMD_CUST_ID HAVING sum(CALL_DURATION_MINUTES)>0 ORDER by 2 desc");

            Query custBrkDwnByMinsHQry = session.createSQLQuery(custBrkDwnByMinsQry.toString());
            custBrkDwnByMinsHQry.setFetchSize(1000);
            resultList = custBrkDwnByMinsHQry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                arlCustBrkDwnByMins = new ArrayList<CallLogVO>(resultList.size());
                for(Object[] custBrkDwnMinsObj : resultList){
                    objCallLogVO = new CallLogVO();
                    objCallLogVO.setCustomer(RMDCommonUtility
                                .convertObjectToString(custBrkDwnMinsObj[0]));
                    objCallLogVO.setCount(RMDCommonUtility
                            .convertObjectToString(custBrkDwnMinsObj[1]));
                    arlCustBrkDwnByMins.add(objCallLogVO);
                }
            }
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_GET_CUST_BREAK_DOWN_BY_MINS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_GET_CUST_BREAK_DOWN_BY_MINS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            custBrkDwnByMinsQry = null;
            resultList = null;
            releaseSession(session);           
        }
        return arlCustBrkDwnByMins;
    }
	
	@Override
    public List<CallLogVO> getCallCntByBusnsArea() throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        CallLogVO objCallLogVO = null;
        List<CallLogVO> callCntByBsnsArea = null;
        StringBuilder callCntByBsnsAreaQry =null;
        try {
            session = getHibernateSession();
            callCntByBsnsAreaQry = new StringBuilder();
            callCntByBsnsAreaQry.append("SELECT OMD_CUST_ID,BUSNSS_AREA,CNT,SUM(CNT) OVER (PARTITION BY OMD_CUST_ID) RANK FROM (");
            callCntByBsnsAreaQry.append("SELECT OMD_CUST_ID,BUSNSS_AREA,SUM(CNT) CNT FROM ( SELECT DECODE(OMD_CUST_ID,'null','No Customer',OMD_CUST_ID) OMD_CUST_ID, ");
            callCntByBsnsAreaQry.append(RMDServiceConstants.CALL_REPORT_BUSS_DECODE_QUERY+RMDCommonConstants.COMMMA_SEPARATOR);
            callCntByBsnsAreaQry.append("COUNT(*) CNT FROM GETS_RMD.GETS_SFDC_CALL_LOGS WHERE CREATION_DATE >= TO_DATE(TO_CHAR(SYSDATE-(select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_DEFAULT_DAYS'), 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
            callCntByBsnsAreaQry.append("AND CREATION_DATE <= TO_DATE(TO_CHAR(SYSDATE, 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) GROUP BY OMD_CUST_ID,BUSNSS_AREA ORDER BY OMD_CUST_ID) GROUP BY OMD_CUST_ID,BUSNSS_AREA) ORDER BY RANK DESC");
         
            Query callCntByBsnsAreaHQry = session.createSQLQuery(callCntByBsnsAreaQry.toString());
            callCntByBsnsAreaHQry.setFetchSize(1000);
            resultList = callCntByBsnsAreaHQry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                callCntByBsnsArea = new ArrayList<CallLogVO>(resultList.size());
                for(Object[] callCntByBussObj : resultList){
                    objCallLogVO = new CallLogVO();
                    objCallLogVO.setCustomer(RMDCommonUtility
                                .convertObjectToString(callCntByBussObj[0]));
                    objCallLogVO.setBussinessType(RMDCommonUtility
                            .convertObjectToString(callCntByBussObj[1]));
                    objCallLogVO.setCount(RMDCommonUtility
                            .convertObjectToString(callCntByBussObj[2]));
                    callCntByBsnsArea.add(objCallLogVO);
                }
            }
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            callCntByBsnsAreaQry = null;
            resultList = null;
            releaseSession(session);           
        }
        return callCntByBsnsArea;
    }
	
	@Override
    public List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        CallLogVO objCallLogVO = null;
        List<CallLogVO> weeklyCallCntByBsnsArea = null;
        DateFormat zoneFormat = null;
        DateFormat zoneFormatDate = null;
        StringBuilder callCntByBsnsAreaQry =null;
        try {
            zoneFormatDate = new SimpleDateFormat("yyyy-mm-dd",
                    Locale.ENGLISH);
            zoneFormat = new SimpleDateFormat("mm/dd/yyyy",
                    Locale.ENGLISH);
            session = getHibernateSession();
            callCntByBsnsAreaQry = new StringBuilder();
            callCntByBsnsAreaQry.append("SELECT CREATION_DATE,BUSNSS_AREA,SUM(CALL_COUNT) FROM (SELECT TRUNC(CREATION_DATE) CREATION_DATE, ");
            callCntByBsnsAreaQry.append(RMDServiceConstants.CALL_REPORT_BUSS_DECODE_QUERY+RMDCommonConstants.COMMMA_SEPARATOR);
            callCntByBsnsAreaQry.append("COUNT(*) CALL_COUNT FROM GETS_RMD.GETS_SFDC_CALL_LOGS WHERE ");
            callCntByBsnsAreaQry.append("CREATION_DATE >= TO_DATE(SYSDATE-(SELECT LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_REPORT_BY_BUSSINESS_AREA_LOAD_DAYS')) ");
            callCntByBsnsAreaQry.append("AND CREATION_DATE < TRUNC(TO_DATE(SYSDATE)) GROUP BY TRUNC(CREATION_DATE),BUSNSS_AREA) GROUP BY CREATION_DATE,BUSNSS_AREA ORDER BY CREATION_DATE ASC");

            Query callCntByBsnsAreaHQry = session.createSQLQuery(callCntByBsnsAreaQry.toString());
            callCntByBsnsAreaHQry.setFetchSize(1000);
            resultList = callCntByBsnsAreaHQry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                weeklyCallCntByBsnsArea = new ArrayList<CallLogVO>(resultList.size());
                for(Object[] weeklyCallCntObj : resultList){
                    objCallLogVO = new CallLogVO();
                    Date creationDate = zoneFormatDate.parse(RMDCommonUtility
                            .convertObjectToString(weeklyCallCntObj[0]));
                    String formatDate = zoneFormat.format(creationDate);
                    objCallLogVO.setDate(formatDate);
                    objCallLogVO.setBussinessType(RMDCommonUtility
                            .convertObjectToString(weeklyCallCntObj[1]));
                    objCallLogVO.setCount(RMDCommonUtility
                            .convertObjectToString(weeklyCallCntObj[2]));
                    weeklyCallCntByBsnsArea.add(objCallLogVO);
                }
            }
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_WEEKLY_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_WEEKLY_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            callCntByBsnsAreaQry = null;
            resultList = null;
            releaseSession(session);           
        }
        return weeklyCallCntByBsnsArea;
    }
	
	@Override
    public List<CallLogVO> getVehCallCntByBusnsArea() throws RMDDAOException {
        List<Object[]> resultList = null;
        Session session = null;
        CallLogVO objCallLogVO = null;
        List<CallLogVO> vehCallCntByBsnsArea = null;
        StringBuilder callCntByBsnsAreaQry =null;
        try {
            session = getHibernateSession();
            callCntByBsnsAreaQry = new StringBuilder();
            callCntByBsnsAreaQry.append("SELECT ASSET,BUSNSS_AREA,CALL_COUNT,SUM(CALL_COUNT) OVER (PARTITION BY ASSET) RANK FROM (");
            callCntByBsnsAreaQry.append("SELECT ASSET,BUSNSS_AREA,SUM(CALL_COUNT) CALL_COUNT FROM (SELECT CUSTRNH.VEHICLE_HDR || ' ' || CUSTRNH.VEHICLE_NO ASSET, ");
            callCntByBsnsAreaQry.append(RMDServiceConstants.CALL_REPORT_BUSS_DECODE_QUERY+RMDCommonConstants.COMMMA_SEPARATOR);
            callCntByBsnsAreaQry.append("COUNT(*) CALL_COUNT FROM GETS_RMD.GETS_SFDC_CALL_LOGS CALLLOG,GETS_RMD.GETS_SFDC_CALL_LOGS_MTM_VEH CALLLOGVEH,GETS_RMD_CUST_RNH_RN_V CUSTRNH ");
            callCntByBsnsAreaQry.append("WHERE CALLLOG.OBJID = CALLLOGVEH.MTM2SFDC_CALL_LOGS AND CALLLOGVEH.MTM2VEHICLE =CUSTRNH.VEHICLE_OBJID AND ");
            callCntByBsnsAreaQry.append("CALLLOG.CREATION_DATE >= TO_DATE(TO_CHAR(SYSDATE-(SELECT LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_DEFAULT_DAYS'), 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
            callCntByBsnsAreaQry.append("AND CALLLOG.CREATION_DATE <= TO_DATE(TO_CHAR(SYSDATE, 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
            callCntByBsnsAreaQry.append("GROUP BY CUSTRNH.VEHICLE_HDR,CUSTRNH.VEHICLE_NO,CALLLOG.BUSNSS_AREA ORDER BY CALL_COUNT) GROUP BY ASSET,BUSNSS_AREA) ORDER BY RANK DESC");
            Query callCntByBsnsAreaHQry = session.createSQLQuery(callCntByBsnsAreaQry.toString());      
            callCntByBsnsAreaHQry.setFetchSize(1000);
            resultList = callCntByBsnsAreaHQry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                vehCallCntByBsnsArea = new ArrayList<CallLogVO>(resultList.size());
                for(Object[] vehCallCntObj : resultList){
                    objCallLogVO = new CallLogVO();
                    objCallLogVO.setAsset(RMDCommonUtility
                                .convertObjectToString(vehCallCntObj[0]));
                    objCallLogVO.setBussinessType(RMDCommonUtility
                            .convertObjectToString(vehCallCntObj[1]));
                    objCallLogVO.setCount(RMDCommonUtility
                            .convertObjectToString(vehCallCntObj[2]));
                    vehCallCntByBsnsArea.add(objCallLogVO);
                }
            }
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEH_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEH_CALL_COUNT_BY_BUSSINESS_AREA);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            callCntByBsnsAreaQry = null;
            resultList = null;
            releaseSession(session);           
        }
        return vehCallCntByBsnsArea;
    }
	
	@Override
    public String getManualCallCount() throws RMDDAOException {
        Session session = null;
        String manualCallCount = null;
        StringBuilder manualCallCountQuery = null;

        try {
            session = getHibernateSession();
            manualCallCountQuery = new StringBuilder();
            manualCallCountQuery.append("SELECT COUNT(1) FROM GETS_RMD.GETS_SFDC_CALL_LOGS WHERE CALL_STARTED_ON is null AND CALL_ENDED_ON is null ");
            manualCallCountQuery.append("AND CREATION_DATE >= TO_DATE(TO_CHAR(SYSDATE-(SELECT LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CALL_LOG_REPORT_DEFAULT_DAYS'), 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
            manualCallCountQuery.append("AND CREATION_DATE <= TO_DATE(TO_CHAR(SYSDATE, 'DD-MM-YYYY'),'DD-MM-YYYY hh24:MI:SS')+(1/24*7) ");
 
            Query manualCallCountHQuery = session.createSQLQuery(manualCallCountQuery.toString());
            manualCallCount = RMDCommonUtility.convertObjectToString(manualCallCountHQuery
                    .uniqueResult());
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MANUAL_CALL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MANUAL_CALL_COUNT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            manualCallCountQuery=null;
            releaseSession(session);
        }
        return manualCallCount;
    }
	
	

}
