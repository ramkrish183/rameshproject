package com.ge.trans.eoa.services.cbr.dao.impl;

/**
 * ============================================================
 * File : CBRAdminDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.cbr.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :June 22,2016
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ge.trans.eoa.services.cbr.dao.intf.CBRAdminDAOIntf;
import com.ge.trans.eoa.services.cbr.service.valueobjects.CBRAdminVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 22,2016
 * @Date Modified : June 22,2016
 * @Modified By :
 * @Contact :
 * @Description :This is DAO implementation layer for CBR related
 *              functionalities
 * @History :
 * 
 ******************************************************************************/
public class CBRAdminDAOImpl extends BaseDAO implements CBRAdminDAOIntf {
	/**
	 * This method is used for fetching Rx Details for CBR Screen
	 * 
	 * @param objCBRCasesVO
	 * @return
	 * @throws RMDDAOException
	 */
	@Override
	public List<CBRAdminVO> getRxMaintenanceDetails(CBRAdminVO objCBRCasesVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		CBRAdminVO cbrCasesVO = null;
		List<CBRAdminVO> arlCBRCasesVO = new ArrayList<CBRAdminVO>();
		try {
			hibernateSession = getHibernateSession();
			StringBuilder strQry = new StringBuilder();
			strQry.append("SELECT DISTINCT L.LEARN2RECOM,R.TITLE,L.ACTIVE_FLAG,L.COMMENTS");
			strQry.append(" FROM GETS_RMD.GETS_TOOL_SIMON_LEARN L,GETS_RMD.GETS_SD_RECOM  R,GETS_RMD_LOOKUP LK");
			strQry.append(" WHERE L.LEARN2RECOM = R.OBJID");
			if (null != objCBRCasesVO.getRxTitle()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxTitle())) {
				strQry.append(" AND LOWER(R.TITLE) LIKE LOWER(:rxTitle) ").append(RMDCommonConstants.ESCAPE_LIKE);
			}
			if (null != objCBRCasesVO.getRxId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxId())) {
				strQry.append(" AND R.OBJID =:rxObjid");
			}
			if (null != objCBRCasesVO.getStatus()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getStatus())) {
				strQry.append(" AND ACTIVE_FLAG =:status");
			}
			if(RMDCommonConstants.STR_ZERO.equalsIgnoreCase(objCBRCasesVO.getStatus())){
				strQry.append(" AND  L.LEARN2RECOM IN (SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME = 'SimonRxExclude')");
			}else if(RMDCommonConstants.STR_ONE.equalsIgnoreCase(objCBRCasesVO.getStatus())){
				strQry.append(" AND  L.LEARN2RECOM NOT IN (SELECT LOOK_VALUE FROM GETS_RMD_LOOKUP WHERE LIST_NAME = 'SimonRxExclude')");
			}
			
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			if (null != objCBRCasesVO.getRxTitle()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxTitle())) {
				qry.setParameter(
						RMDCommonConstants.RX_TITLE,
						RMDServiceConstants.PERCENTAGE
								+ AppSecUtil.escapeLikeCharacters(objCBRCasesVO.getRxTitle())
								+ RMDServiceConstants.PERCENTAGE);
			}
			if (null != objCBRCasesVO.getRxId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxId())) {
				qry.setParameter(RMDCommonConstants.RX_OBJID,
						objCBRCasesVO.getRxId());
			}
			if (null != objCBRCasesVO.getStatus()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getStatus())) {
				qry.setParameter(RMDCommonConstants.STATUS,
						objCBRCasesVO.getStatus());
			}
			qry.setFetchSize(10);
			List<Object[]> arlCases = qry.list();
			if (null != arlCases && !arlCases.isEmpty()) {
				for (Object[] obj : arlCases) {
					cbrCasesVO = new CBRAdminVO();
					cbrCasesVO.setRxId(RMDCommonUtility
							.convertObjectToString(obj[0]));
					cbrCasesVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(obj[1]));
					cbrCasesVO.setStatus(RMDCommonUtility
							.convertObjectToString(obj[2]));
					cbrCasesVO.setComments(RMDCommonUtility
							.convertObjectToString(obj[3]));
					arlCBRCasesVO.add(cbrCasesVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_RX_MAINTENANCE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objCBRCasesVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_RX_MAINTENANCE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objCBRCasesVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return arlCBRCasesVO;

	}

	/**
	 * This method is used for fetching Case Details for CBR Screen
	 * 
	 * @param objCBRCasesVO
	 * @return
	 * @throws RMDDAOException
	 */
	@Override
	public List<CBRAdminVO> getCaseMaintenanceDetails(CBRAdminVO objCBRCasesVO)
			throws RMDDAOException {

		Session hibernateSession = null;
		CBRAdminVO cbrCasesVO = null;
		List<CBRAdminVO> arlCBRCasesVO = new ArrayList<CBRAdminVO>();
		try {
			hibernateSession = getHibernateSession();
			StringBuilder strQry = new StringBuilder();
			strQry.append("SELECT B.ID_NUMBER,RX.TITLE,ACTIVE_FLAG,SIMONLEARN.OBJID,A.VEHICLE_NO,A.VEHICLE_HDR,A.VEHICLE_HDR_CUST,A.CUST_NAME,V.BAD_ACTOR,'N',TC.TITLE");
			strQry.append(" FROM GETS_RMD_CUST_RNH_RN_V A,TABLE_CASE B,GETS_TOOL_SIMON_LEARN SIMONLEARN,GETS_SD_RECOM RX ,TABLE_CONDITION TC,GETS_RMD_VEHICLE V");
			strQry.append(" WHERE B.CASE_PROD2SITE_PART = A.SITE_PART_OBJID");
			strQry.append(" AND A.VEHICLE_OBJID =V.OBJID");
			strQry.append(" AND B.OBJID =SIMONLEARN.LEARN2CASE");
			strQry.append(" AND RX.OBJID=SIMONLEARN.LEARN2RECOM");
			strQry.append(" AND CASE_STATE2CONDITION = TC.OBJID");
			if (null != objCBRCasesVO.getRxTitle()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxTitle())) {
				strQry.append(" AND LOWER(RX.TITLE) LIKE LOWER(:rxTitle) ").append(RMDCommonConstants.ESCAPE_LIKE);
			}
			if (null != objCBRCasesVO.getCaseId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getCaseId())) {
				strQry.append(" AND ID_NUMBER LIKE :caseId");
			}
			if (null != objCBRCasesVO.getStatus()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getStatus())) {
				strQry.append(" AND ACTIVE_FLAG =:status");
			}
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			if (null != objCBRCasesVO.getRxTitle()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getRxTitle())) {
				qry.setParameter(
						RMDCommonConstants.RX_TITLE,
						RMDServiceConstants.PERCENTAGE
								+ AppSecUtil.escapeLikeCharacters(objCBRCasesVO.getRxTitle())
								+ RMDServiceConstants.PERCENTAGE);
			}
			if (null != objCBRCasesVO.getCaseId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getCaseId())) {
				qry.setParameter(RMDCommonConstants.CASE_ID,
						objCBRCasesVO.getCaseId());
			}
			if (null != objCBRCasesVO.getStatus()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objCBRCasesVO.getStatus())) {
				qry.setParameter(RMDCommonConstants.STATUS,
						objCBRCasesVO.getStatus());
			}
			qry.setFetchSize(10);
			List<Object[]> arlCases = qry.list();
			if (null != arlCases && !arlCases.isEmpty()) {
				for (Object[] obj : arlCases) {
					cbrCasesVO = new CBRAdminVO();
					cbrCasesVO.setCaseId(RMDCommonUtility
							.convertObjectToString(obj[0]));
					cbrCasesVO.setRxTitle(RMDCommonUtility
							.convertObjectToString(obj[1]));
					cbrCasesVO.setStatus(RMDCommonUtility
							.convertObjectToString(obj[2]));
					cbrCasesVO.setSimonObjid(RMDCommonUtility
							.convertObjectToString(obj[3]));
					arlCBRCasesVO.add(cbrCasesVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_CASE_MAINTENANCE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objCBRCasesVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_CASE_MAINTENANCE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objCBRCasesVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return arlCBRCasesVO;

	}

	/**
	 * This method is used for Activate/Deactivate Case
	 * 
	 * @param objid
	 * @param status
	 * @return
	 * @throws RMDDAOException
	 */
	@Override
	public String caseActivateDeactivate(String objid, String status)
			throws RMDDAOException {
		Session hibernateSession = null;
		String strActivateDeactivate = RMDCommonConstants.FAILURE;
		try {
			if (null != objid
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objid)
					&& null != status
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(status)) {
				hibernateSession = getHibernateSession();
				StringBuilder strQry = new StringBuilder();
				strQry.append("UPDATE GETS_TOOL_SIMON_LEARN SET ACTIVE_FLAG =:activeFlag WHERE OBJID =:objId");
				Query updQry = hibernateSession.createSQLQuery(strQry
						.toString());
				updQry.setParameter(RMDCommonConstants.OBJ_ID, objid);
				updQry.setParameter(RMDCommonConstants.CBR_ACTIVE_FLAG, status);
				updQry.executeUpdate();
				strActivateDeactivate = RMDCommonConstants.SUCCESS;
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_CASE_ACTIVATE_DEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_CASE_ACTIVATE_DEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return strActivateDeactivate;
	}

	/**
	 * This method is used for Activate/Deactivate Rx
	 * 
	 * @param objid
	 * @param status
	 * @param strUserName
	 * @return
	 * @throws RMDDAOException
	 */
	@Override
	public String rxActivateDeactivate(String objid, String status,
			String strUserName) throws RMDDAOException {
		Session hibernateSession = null;
		String strActivateDeactivate = RMDCommonConstants.FAILURE;
		try {
			if (null != objid
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objid)
					&& null != status
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(status)) {
				hibernateSession = getHibernateSession();
				StringBuilder strQry = new StringBuilder();
				strQry.append("UPDATE GETS_RMD.GETS_TOOL_SIMON_LEARN SET ACTIVE_FLAG=:activeFlag WHERE learn2recom =:objId");
				Query updQry = hibernateSession.createSQLQuery(strQry
						.toString());
				updQry.setParameter(RMDCommonConstants.OBJ_ID, objid);
				updQry.setParameter(RMDCommonConstants.CBR_ACTIVE_FLAG, status);
				updQry.executeUpdate();

				if (RMDCommonConstants.STR_ZERO.equalsIgnoreCase(status.trim())) {
					StringBuilder strSelectQry = new StringBuilder();
					strSelectQry
							.append("SELECT * FROM GETS_RMD_LOOKUP WHERE LIST_NAME=:listName and LOOK_VALUE=:look_value");
					Query qry = hibernateSession.createSQLQuery(strSelectQry
							.toString());
					qry.setParameter(RMDCommonConstants.LIST_NAME,
							RMDCommonConstants.SIMON_RX_EXCLUDE);
					qry.setParameter(RMDCommonConstants.LOOK_VALUE, objid);
					List<Object[]> arlList = qry.list();
					if (null != arlList && !arlList.isEmpty()) {
						return RMDCommonConstants.SUCCESS;
					}
					StringBuilder insertQuery = new StringBuilder();
					insertQuery
							.append("INSERT INTO GETS_RMD_LOOKUP (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, CREATED_BY, LIST_NAME, LIST_DESCRIPTION, LOOK_VALUE, LOOK_STATE, SORT_ORDER)");
					insertQuery
							.append(" VALUES (GETS_RMD_LOOKUP_SEQ.NEXTVAL, SYSDATE,:userName,SYSDATE,:userName,'SimonRxExclude', 'Exclude the RX from Hashload',:look_value,'Active', 1)");
					Query insQry = hibernateSession.createSQLQuery(insertQuery
							.toString());
					insQry.setParameter(RMDCommonConstants.USERNAME,
							strUserName);
					insQry.setParameter(RMDCommonConstants.LOOK_VALUE, objid);
					insQry.executeUpdate();
					strActivateDeactivate = RMDCommonConstants.SUCCESS;
				} else {
					StringBuilder deleteQuery = new StringBuilder();
					deleteQuery
							.append("DELETE FROM GETS_RMD_LOOKUP WHERE LIST_NAME='SimonRxExclude' and LOOK_VALUE=:look_value");
					Query delQry = hibernateSession.createSQLQuery(deleteQuery
							.toString());
					delQry.setParameter(RMDCommonConstants.LOOK_VALUE, objid);
					delQry.executeUpdate();
					strActivateDeactivate = RMDCommonConstants.SUCCESS;
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_RX_ACTIVATE_DEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CBR_RX_ACTIVATE_DEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return strActivateDeactivate;
	}

}
