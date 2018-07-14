package com.ge.trans.eoa.services.cases.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.cases.dao.intf.CaseTrendDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class CaseTrendDAOImpl extends BaseDAO implements CaseTrendDAOIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final RMDLogger LOGGER = RMDLogger
			.getLogger(CaseTrendDAOImpl.class);

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching delivered open rx count
	 *               for past 8 days.
	 */
	@Override
	public List<CaseTrendVO> getOpenRXCount() throws RMDDAOException {
		int openingCount = 0;
		Session session = null;
		CaseTrendVO objCaseTrendVO = null;
		List<CaseTrendVO> caseTrendVOList = new ArrayList<CaseTrendVO>();
		Object[] configDetails = null;
		String defaultCommDays = null;
		String defaultCommDaysQry = null;
		String defaultDaysQry = null;
		String defaultDays= null;
		List<Object[]> resultList = null;
		int count  = 0;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			StringBuilder caseQryComm = new StringBuilder();
			caseQryComm.append("SELECT COUNT(fdbk.rx_case_id) FROM gets_sd_cust_fdbk fdbk WHERE fdbk.CREATION_DATE <= TO_DATE( sysdate)- :days ");
			caseQryComm.append("AND (fdbk.RX_CLOSE_DATE  IS NULL OR fdbk.RX_CLOSE_DATE > sysdate- :days)");
			defaultCommDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'OPEN_RX_GRAPH_DATA_LOAD_DAYS'";
			Query daysCommHqry = session.createSQLQuery(defaultCommDaysQry);
			defaultCommDays = RMDCommonUtility
					.convertObjectToString(daysCommHqry.uniqueResult());
			Query caseCommHqry = session.createSQLQuery(caseQryComm.toString());
			caseCommHqry.setParameter(RMDServiceConstants.NO_OF_DAYS,
					RMDCommonUtility.convertStringToInt(defaultCommDays));
			caseCommHqry.setFetchSize(1000);
			openingCount = Integer.parseInt(caseCommHqry.list().get(0).toString());
			caseQry.append("SELECT CASE WHEN open_date IS NOT NULL AND closed_date IS NULL THEN open_date WHEN open_date IS NULL AND closed_date IS NOT NULL ");
			caseQry.append("THEN closed_date ELSE open_date END AS rxdate,CASE WHEN open_day IS NOT NULL AND closed_day IS NULL THEN open_day WHEN open_day IS NULL ");
			caseQry.append("AND closed_day IS NOT NULL THEN closed_day ELSE open_day END AS rxday,COALESCE(open_count, 0 )   AS open_rx_count ,COALESCE(closed_rx, 0 ) AS closed_rx_count ");
			caseQry.append("FROM(SELECT TRUNC(fdbk.CREATION_DATE) AS open_date,TO_CHAR(to_date(fdbk.CREATION_DATE,'dd/mm/yy'), 'DAY') AS open_day,COUNT(DISTINCT fdbk.rx_case_id) AS open_count ");
			caseQry.append("FROM gets_sd_cust_fdbk fdbk WHERE fdbk.CREATION_DATE >= TO_DATE( sysdate) -:days AND fdbk.CREATION_DATE < TO_DATE(sysdate) GROUP BY TRUNC(fdbk.CREATION_DATE), ");
			caseQry.append("TO_CHAR(to_date(fdbk.CREATION_DATE,'dd/mm/yy'), 'DAY') ORDER BY 1 ASC) Open_RX FULL OUTER JOIN(SELECT TRUNC(RX_CLOSE_DATE ) AS closed_date, ");
			caseQry.append("TO_CHAR(to_date(RX_CLOSE_DATE,'dd/mm/yy'), 'DAY') AS closed_day,COUNT(DISTINCT fdbk.rx_case_id) AS closed_rx FROM gets_sd_cust_fdbk fdbk ");
			caseQry.append("WHERE fdbk.RX_CLOSE_DATE >= TO_DATE( sysdate) -:days AND fdbk.RX_CLOSE_DATE < TO_DATE( sysdate) GROUP BY TRUNC(RX_CLOSE_DATE ),TO_CHAR(to_date(RX_CLOSE_DATE,'dd/mm/yy'), 'DAY') ");
			caseQry.append("ORDER BY 1 ASC) close_rx ON Open_RX.open_date = close_rx.closed_date ORDER BY 1");
			defaultDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'OPEN_RX_GRAPH_DATA_LOAD_DAYS'";
			Query daysHqry = session.createSQLQuery(defaultDaysQry);
			defaultDays = RMDCommonUtility.convertObjectToString(daysHqry
					.uniqueResult());
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDServiceConstants.NO_OF_DAYS,
					RMDCommonUtility.convertStringToInt(defaultDays));
			caseHqry.setFetchSize(1000);
			resultList = caseHqry.list();
			if (resultList != null && !resultList.isEmpty()) {
				caseTrendVOList = new ArrayList<CaseTrendVO>(resultList.size());
			}
			if (resultList != null
					&& RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objCaseTrendVO = new CaseTrendVO();

					objCaseTrendVO.setCreationRXDate(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objCaseTrendVO.setDay(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					count = openingCount+RMDCommonUtility
							.convertObjectToInt(configDetails[2]);
					count = count - RMDCommonUtility
							.convertObjectToInt(configDetails[3]);
					objCaseTrendVO.setCount(String.valueOf(count));
					caseTrendVOList.add(objCaseTrendVO);
					openingCount = count;
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_OPEN_RX_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return caseTrendVOList;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching created case type count
	 *               for past 24 hours.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrend() throws RMDDAOException {
		List<Object[]> caseResultList = null;
		Session session = null;
		CaseTrendVO objCaseTrendVO = null;
		List<CaseTrendVO> caseTrendVOList = new ArrayList<CaseTrendVO>();
		Object[] configDetails = null;
		String defaultDays = null;
		String defaultDaysQry = null;
		String caseTypeQry = null;
		List<String> strModelArray = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT to_char(tc.creation_time,'DD-MM-YYYY') CREATED_DATE, tp.title CASE_TITLE, count(*) FAULT_COUNT  FROM table_case tc, table_gbst_elm tp ");
			caseQry.append("WHERE  tc.creation_time >= TO_DATE( sysdate) -:days AND tc.creation_time < TO_DATE(sysdate) AND tc.calltype2gbst_elm = tp.objid AND tp.title IN (:strModelArray) group by to_char(tc.creation_time,'DD-MM-YYYY'),tp.title order by 2 ");
			defaultDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CASE_TREND_GRAPH_DATA_LOAD_DAYS'";
			caseTypeQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CASE_TREND_BAR_GRAPH_CASE_TYPES'";
			Query caseTypeHqry = session.createSQLQuery(caseTypeQry);
			strModelArray = caseTypeHqry.list();
			Query daysHqry = session.createSQLQuery(defaultDaysQry);
			defaultDays = RMDCommonUtility.convertObjectToString(daysHqry
					.uniqueResult());
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDServiceConstants.NO_OF_DAYS,
					RMDCommonUtility.convertStringToInt(defaultDays));
			caseHqry.setParameterList(RMDCommonConstants.MODEL_S, strModelArray);
			caseHqry.setFetchSize(1000);
			caseResultList = caseHqry.list();
			if (caseResultList != null && !caseResultList.isEmpty()) {
				caseTrendVOList = new ArrayList<CaseTrendVO>(
						caseResultList.size());
			}
			if (caseResultList != null
					&& RMDCommonUtility.isCollectionNotEmpty(caseResultList)) {
				for (final Iterator<Object[]> obj = caseResultList.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objCaseTrendVO = new CaseTrendVO();

					objCaseTrendVO.setCreationRXDate(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objCaseTrendVO.setProblemDesc(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objCaseTrendVO.setCount(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					caseTrendVOList.add(objCaseTrendVO);
				}
			}
			caseResultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return caseTrendVOList;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDWebException
	 * @Description: This method is used for fetching created case type count
	 *               for past 7 days.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrendStatistics() throws RMDDAOException {
		List<Object[]> caseResultList = null;
		Session session = null;
		CaseTrendVO objCaseTrendVO = null;
		List<CaseTrendVO> caseTrendVOList = new ArrayList<CaseTrendVO>();
		Object[] configDetails = null;
		DateFormat zoneFormat = null;
		DateFormat zoneFormatDate = null;
		int defaultDays = 0;
		String defaultDaysQry = null;
		String caseTypeQry = null;
		List<String> strModelArray = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT trunc(tc.creation_time) CREATED_DATE, tp.title CASE_TITLE,count(*) FAULTCOUNT FROM table_case tc, table_gbst_elm tp ");
			caseQry.append("WHERE tc.creation_time >= TO_DATE( sysdate) -:days AND tc.creation_time < TO_DATE(sysdate) and tc.calltype2gbst_elm = tp.objid AND tp.title IN (:strModelArray) ");
			caseQry.append("group by trunc(tc.creation_time),tp.title order by trunc(tc.creation_time) asc");
			defaultDaysQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CASE_TREND_STATISTICS_GRAPH_DATA_LOAD_DAYS'";
			caseTypeQry = "select LOOK_VALUE from GETS_RMD.GETS_RMD_LOOKUP where LIST_NAME = 'CASE_TREND_GRAPH_CASE_TYPES'";
			Query daysHqry = session.createSQLQuery(defaultDaysQry);
			defaultDays = RMDCommonUtility.convertObjectToInt(daysHqry.uniqueResult());
			Query caseTypeHqry = session.createSQLQuery(caseTypeQry);
			strModelArray = caseTypeHqry.list();
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameterList(RMDCommonConstants.MODEL_S, strModelArray);
			caseHqry.setParameter(RMDServiceConstants.NO_OF_DAYS, defaultDays);
			caseHqry.setFetchSize(1000);
			caseResultList = caseHqry.list();
			if (caseResultList != null && caseResultList.isEmpty()) {
				caseTrendVOList = new ArrayList<CaseTrendVO>(
						caseResultList.size());
			}
			if (caseResultList != null
					&& RMDCommonUtility.isCollectionNotEmpty(caseResultList)) {
				for (final Iterator<Object[]> obj = caseResultList.iterator(); obj
						.hasNext();) {
					configDetails = obj.next();
					objCaseTrendVO = new CaseTrendVO();
					String date = RMDCommonUtility
							.convertObjectToString(configDetails[0]);
					zoneFormatDate = new SimpleDateFormat("yyyy-mm-dd",
							Locale.ENGLISH);
					zoneFormat = new SimpleDateFormat("mm/dd/yyyy",
							Locale.ENGLISH);
					Date creationDate = zoneFormatDate.parse(date);
					String formatDate = zoneFormat.format(creationDate);
					objCaseTrendVO.setCreationRXDate(formatDate);
					objCaseTrendVO.setProblemDesc(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objCaseTrendVO.setCount(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					caseTrendVOList.add(objCaseTrendVO);
				}
			}
			caseResultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CASE_TREND_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return caseTrendVOList;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getGeneralNotesDetails() throws RMDDAOException {
		Session session = null;
		GeneralNotesEoaServiceVO objGeneralNotesEoaServiceVO=null;
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO =null;
		List<Object[]> resultList = null;
		StringBuilder caseQryFrom=null;
		try {
			session = getHibernateSession();
			caseQryFrom = new StringBuilder();
			caseQryFrom.append("SELECT NOTES_DESCRIPTION,CREATED_BY FROM GETS_RMD.GENERAL_NOTES WHERE FLAG = 'Y' ORDER BY LAST_UPDATED_DATE DESC");
			Query caseHqryFrom = session.createSQLQuery(caseQryFrom.toString());
			caseHqryFrom.setFetchSize(1000);
			resultList = caseHqryFrom.list();
			 if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
			     arlGeneralNotesEoaServiceVO = new ArrayList<GeneralNotesEoaServiceVO>(
	                        resultList.size());
	               for(Object[] objCommNotes : resultList){
	                    objGeneralNotesEoaServiceVO = new GeneralNotesEoaServiceVO();
	                    objGeneralNotesEoaServiceVO.setNotesdesc(RMDCommonUtility
	                            .convertObjectToString(objCommNotes[0]));
	                    objGeneralNotesEoaServiceVO.setEnteredby(RMDCommonUtility
	                            .convertObjectToString(objCommNotes[1]));                    
	                    arlGeneralNotesEoaServiceVO.add(objGeneralNotesEoaServiceVO);

	                }
	            }
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GENERAL_NOTES_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GENERAL_NOTES_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
			resultList=null;
			caseQryFrom=null;
		}
		return arlGeneralNotesEoaServiceVO;
	}

}