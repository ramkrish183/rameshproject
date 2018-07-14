/**
 * ============================================================
 * Classification: GE Confidential
 * File : CommonDAOImpl.java
 * Description :
 *
 * Package :  com.ge.trans.rmd.services.common.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 11, 2012
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.intf.CommonDAOIntf;
import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: May 11, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CommonDAOImpl extends BaseDAO implements CommonDAOIntf {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final RMDLogger LOG = RMDLogger
			.getLogger(CommonDAOImpl.class);
	Codec ORACLE_CODEC = new OracleCodec();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.common.dao.intf.CommonDAOIntf#getSearchQueueMenu
	 * ()
	 */

	@SuppressWarnings("rawtypes")
	public List getSearchQueueMenu(String strLanguage, String strUserLanguage)
			throws RMDDAOException {
		ArrayList lstQueues = null;
		Session session = null;

		ElementVO objElementVO = null;
		LOG.debug("Enter into getSearchQueueMenu in CommonDAOImpl method");

		try {
			session = getHibernateSession();
			String queryString = "SELECT OBJID A,TITLE B,'Z' FROM TABLE_QUEUE UNION SELECT 0, 'SELECT', ' ' FROM DUAL ORDER BY 3, 2";
			Query query = session.createSQLQuery(queryString);
			List<Object> lookupList = query.list();

			if (lookupList != null && !lookupList.isEmpty()) {
				lstQueues = new ArrayList();
				for (final Iterator<Object> iter = lookupList.iterator(); iter
						.hasNext();) {

					final Object[] lookupRecord = (Object[]) iter.next();
					objElementVO = new ElementVO();
					objElementVO.setId(RMDCommonUtility
							.convertObjectToString(lookupRecord[0]));
					objElementVO.setName(RMDCommonUtility
							.convertObjectToString(lookupRecord[1]));
					lstQueues.add(objElementVO);

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_QUEUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		LOG.debug("End of getSearchQueueMenu in CommonDAOImpl method"
				+ lstQueues.size());
		return lstQueues;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ge.trans.rmd.services.common.dao.intf.CommonDAOIntf#
	 * getSolutionTypes ()
	 */
	/* This Method is used for fetch the function list from database */
	public List<ElementVO> getFunctions(final String strLanguage,
			String strUserLanguage) throws RMDDAOException {
		// TODO Auto-generated method stub
		ElementVO objElementVO = null;
		Session session = null;
		String queryString = null;
		Query nativeQuery = null;
		List lookupList = null;
		List arlFunctions = null;
		try {
			LOG.debug("Start getFunctions() in CommonDaoImpl");
			session = getHibernateSession();
			queryString = "SELECT  FUNCTIONS FROM GETS_RMD_FUNCTIONS  WHERE LANGUAGE=:language";
			nativeQuery = session.createSQLQuery(queryString);
			nativeQuery.setParameter(RMDServiceConstants.LANGUAGE, strLanguage);
			lookupList = nativeQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
				arlFunctions = new ArrayList<ElementVO>();
				for (int icount = 0; icount < lookupList.size(); icount++) {
					Object strFunction = (Object) lookupList.get(icount);
					objElementVO = new ElementVO();
					objElementVO.setId((strFunction.toString()));
					objElementVO.setName((strFunction.toString()));
					arlFunctions.add(objElementVO);
				}
			}
			LOG.debug("End getFunctions() in CommonDaoImpl");
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return arlFunctions;
	}
	
	/**
	 * @Description This method is to get the look up value from customer look
	 *              up table
	 * @return List<CustLookupVO>
	 * @throws RMDDAOException
	 */
	public List<CustLookupVO> getSDCustLookup()
			throws RMDDAOException {
		Session objSession = getHibernateSession();
		StringBuffer custLookUpQuery = new StringBuffer();
		List lookupList = null;
		Query lookUpQuery = null;
		List<CustLookupVO> arlFunctions = null;
		CustLookupVO custLookupVO = null;
		try {
			custLookUpQuery
					.append("SELECT CUSTLKP.SHOW_FAM,CUSTLKP.MATRIX_CONV,TBLBUS.ORG_ID,CUSTLKP.RX_EMAIL_CHK,DATE_FORMAT,CUSTLKP.CONFIG_ALERT_FLAG,CUSTLKP.FLAG_MPDATA ");					
			custLookUpQuery.append("FROM GETS_SD.GETS_SD_CUST_LKP CUSTLKP, ");
			custLookUpQuery.append("SA.TABLE_BUS_ORG TBLBUS ");
			custLookUpQuery
					.append("WHERE TBLBUS.NAME = CUSTLKP.CUSTOMER_NAME ");
			lookUpQuery = objSession.createSQLQuery(custLookUpQuery.toString());

			lookupList = lookUpQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
				arlFunctions = new ArrayList<CustLookupVO>();
				for (int i = 0; i < lookupList.size(); i++) {
					Object rxTitleMLData[] = (Object[]) lookupList.get(i);
					custLookupVO = new CustLookupVO();
					custLookupVO.setFamPrivilege(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[0]));
					custLookupVO.setMetricsPrivilege(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[1]));
					custLookupVO.setCustomerId(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[2]));
					custLookupVO.setRxEmailCheck(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[3]));
					custLookupVO.setDateFormat(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[4]));
					custLookupVO.setConfigAlertFlg(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[5]));
					custLookupVO.setFlagMPData(RMDCommonUtility
							.convertObjectToString(rxTitleMLData[6]));
					arlFunctions.add(custLookupVO);
				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RMDDAOException(new String[] {},
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			if (null != objSession) {
				releaseSession(objSession);
			}
		}
		return arlFunctions;

	}

	@Override
	public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO) {
		Session objSession = getHibernateSession();
		try {
			StringBuilder insertExceptionLog = new StringBuilder();
			String traceLog = exceptionDetailsVO.getTraceLog();
			if(traceLog != null && traceLog.length() >4000){
				traceLog = traceLog.substring(0, 4000);
			}
			insertExceptionLog
			        .append("insert into GETS_RMD.OMD_EXCEPTION_LOG(OBJID, USER_ID, SCREEN_NAME, BUSINESS_KEYS, EXCEPTION_TYPE, "
			                + " EXCEPTION_DESC, EXCEPTION_TIME, TRACE_LOG ) "
			                + "VALUES(GETS_RMD.OMD_EXCEPTION_LOG_SEQ.NEXTVAL, :userId, :screenName, :businessKeys, :exceptionType, :exceptionDesc, sysdate, :traceLog)");

			Query exceptionLogInsertHQry = objSession.createSQLQuery(insertExceptionLog.toString());
			exceptionLogInsertHQry.setParameter("userId", exceptionDetailsVO.getUserId());
			exceptionLogInsertHQry.setParameter("screenName", exceptionDetailsVO.getScreenName());
			exceptionLogInsertHQry.setParameter("businessKeys", exceptionDetailsVO.getBusinessKeys());
			exceptionLogInsertHQry.setParameter("exceptionType", exceptionDetailsVO.getExceptionType());
			exceptionLogInsertHQry.setParameter("exceptionDesc", exceptionDetailsVO.getExceptionDesc());
			exceptionLogInsertHQry.setParameter("traceLog", traceLog);
			exceptionLogInsertHQry.executeUpdate();
		} catch (HibernateException e) {
			LOG.error(e, e);
		}finally{
			if (null != objSession) {
				releaseSession(objSession);
			}
		}
	}
	
	/**
	 * @param userId
	 * @return String
	 * @Description: method to get OTP parameters
	 */
	@Override
	public String getAssetPanelParameters() throws RMDDAOException {
		Session session = null;
		String assetPanelParams =  "";
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("SELECT TITLE,VALUE FROM GETS_RMD_SYSPARM WHERE TITLE IN (:asset_panel_param_1,:asset_panel_param_2,:asset_panel_param_3)");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.ASSET_PANEL_PARAM_1,RMDCommonConstants.ASSET_PANEL_IMAGE_REFRESH_RATE_IN_MILLIS);
			caseHqry.setParameter(RMDCommonConstants.ASSET_PANEL_PARAM_2,RMDCommonConstants.ASSET_PANEL_GREEN_TO_YELLOW_THRESHOLD_IN_MILLIS);
			caseHqry.setParameter(RMDCommonConstants.ASSET_PANEL_PARAM_3,RMDCommonConstants.ASSET_PANEL_YELLOW_TO_RED_THRESHOLD_IN_MILLIS);
			if (null != caseHqry.list()) {
				List result = caseHqry.list();
				String[] a = new String[3];
				for(Object i: result)
				{
					Object temp[] = (Object[]) i;
					if(temp[0].equals(RMDCommonConstants.ASSET_PANEL_IMAGE_REFRESH_RATE_IN_MILLIS))
						a[0] = temp[1].toString(); 
					if(temp[0].equals(RMDCommonConstants.ASSET_PANEL_GREEN_TO_YELLOW_THRESHOLD_IN_MILLIS))
						a[1] = temp[1].toString();
					if(temp[0].equals(RMDCommonConstants.ASSET_PANEL_YELLOW_TO_RED_THRESHOLD_IN_MILLIS))
						a[2] = temp[1].toString();
				}
				assetPanelParams = a[0] + "~" + a[1] + "~" + a[2];
			} else {
				assetPanelParams = RMDCommonConstants.STRING_NULL;
			}
		} catch (Exception e) {
			assetPanelParams = RMDCommonConstants.STRING_NULL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_USER_PHONE_NO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return assetPanelParams;
	}

}