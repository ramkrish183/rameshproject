/**
 * ============================================================
 * Classification: GE Confidential
 * File : VehicleCfgDAOImpl.java
 * Description :
 *
 * Package :com.ge.trans.eoa.services.asset.dao.impl;
 * Author : iGATE Global Solutions Ltd.
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

package com.ge.trans.eoa.services.asset.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.asset.dao.intf.BOMMaintenanceDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.BOMMaintenanceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created : March,17 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class BOMMaintenanceDAOImpl extends BaseDAO implements
		BOMMaintenanceDAOIntf {

	private static final long serialVersionUID = 1L;

	public static final RMDLogger LOG = RMDLogger
			.getLogger(BOMMaintenanceDAOImpl.class);

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param :
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching config controller
	 *               list
	 * 
	 */

	@Override
	public List<BOMMaintenanceVO> getConfigList() throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		BOMMaintenanceVO objBOMMaintenanceVO = null;
		List<BOMMaintenanceVO> bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>();
		Object[] configDetails=null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" select objid,controller_cfg from gets_rmd_ctl_cfg ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = (Object[]) obj.next();
					objBOMMaintenanceVO = new BOMMaintenanceVO();

					objBOMMaintenanceVO.setObjid(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objBOMMaintenanceVO.setConfigList(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					bOMMaintenanceVOList.add(objBOMMaintenanceVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_BOM_CONFIG_LIST);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_BOM_CONFIG_LIST);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return bOMMaintenanceVOList;
	}

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param : configId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching config controller
	 *               details from database
	 * 
	 */
	@Override
	public List<BOMMaintenanceVO> populateConfigDetails(String configId)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		BOMMaintenanceVO objBOMMaintenanceVO = null;
		List<BOMMaintenanceVO> bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>();
		Object[] configDetails=null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT GRMB.CONFIG_ITEM CIT,GRMB.EXPECTED_VALUE VAL,GRMB.BOM_STATUS APP,GRMB.OBJID MOBJID,GRMB.MASTER_BOM2PARM_DEF MBPD,GRP.REQUEST_PARM_NUMBER AA, ");
			caseQry.append(" GRP.PARM_DESC BB,GRMB.NOTIFICATION FROM GETS_RMD_MASTER_BOM GRMB,GETS_RMD_PARMDEF GRP WHERE GRMB.MASTER_BOM2CTL_CFG = :configId ");
			caseQry.append(" AND GRMB.MASTER_BOM2PARM_DEF  = GRP.OBJID(+) ORDER BY GRMB.CONFIG_ITEM ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID, configId);
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = (Object[]) obj.next();
					objBOMMaintenanceVO = new BOMMaintenanceVO();
					objBOMMaintenanceVO.setConfigItem(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objBOMMaintenanceVO.setValue(ESAPI.encoder().encodeForXML(
							RMDCommonUtility
									.convertObjectToString(configDetails[1])));
					objBOMMaintenanceVO.setStatus(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					objBOMMaintenanceVO.setBomObjid(RMDCommonUtility
							.convertObjectToString(configDetails[3]));
					objBOMMaintenanceVO.setParameterObjid(RMDCommonUtility
							.convertObjectToString(configDetails[4]));
					objBOMMaintenanceVO.setParameterNumber(RMDCommonUtility
							.convertObjectToString(configDetails[5]));
					objBOMMaintenanceVO.setParameterDesc(RMDCommonUtility
							.convertObjectToString(configDetails[6]));
					objBOMMaintenanceVO.setNotificationFlag(RMDCommonUtility
							.convertObjectToString(configDetails[7]));
					bOMMaintenanceVOList.add(objBOMMaintenanceVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPULATE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPULATE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return bOMMaintenanceVOList;
	}

	/**
	 * @Author :
	 * @return :List<BOMMaintenanceVO>
	 * @param : configId
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for fetching parameter details
	 *               from database.
	 * 
	 */
	@Override
	public List<BOMMaintenanceVO> getParameterList(String configId)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		BOMMaintenanceVO objBOMMaintenanceVO = null;
		List<BOMMaintenanceVO> bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>();
		Object[] configDetails=null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT /*+  NO_PARALLEL  USE_NL (SH)*/ A.REQUEST_PARM_NUMBER, A.PARM_DESC,A.OBJID FROM  GETS_RMD.GETS_RMD_PARMDEF A  , GETS_RMD.GETS_RMD_CTL_CFG_SRC B WHERE  (A.CONTROLLER_SOURCE_ID = B.CONTROLLER_SOURCE_ID) AND (B.CTL_CFG_SRC2CTL_CFG = :configId) order by A.REQUEST_PARM_NUMBER ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID, configId);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				bOMMaintenanceVOList = new ArrayList<BOMMaintenanceVO>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = (Object[]) obj.next();
					objBOMMaintenanceVO = new BOMMaintenanceVO();

					objBOMMaintenanceVO.setParameterNumber(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objBOMMaintenanceVO.setParameterDesc(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objBOMMaintenanceVO.setParameterObjid(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					bOMMaintenanceVOList.add(objBOMMaintenanceVO);
				}
			}
			resultList = null;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPULATE_BOM_PARAMETER_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCPETION_POPULATE_BOM_PARAMETER_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return bOMMaintenanceVOList;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<BOMMaintenanceVO>
	 * @throws :RMDBOException
	 * @Description: This method is Responsible for saving config controller
	 *               details in database
	 * 
	 */
	@Override
	public String saveBOMDetails(List<BOMMaintenanceVO> bomMaintenanceVO)
			throws RMDDAOException {
		Session session = null;
		StringBuilder bomUpdateQuery = new StringBuilder();
		StringBuilder bomSelectQuery = new StringBuilder();
		StringBuilder bomInsertQuery = new StringBuilder();
		StringBuilder bomProcedureQuery = new StringBuilder();
		StringBuilder bomQuery = new StringBuilder();
		Query updatehibernateQuery = null, selectHibernateQuery = null, callHibernateQuery = null, insertHibernateQuery = null, bomCallQuery = null;
		Transaction transaction = null;
		List<Object[]> resultList = null;
		List<BigDecimal> queryList = null;
		int count = 0, bom = 0;
		String status = RMDServiceConstants.SUCCESS, objid = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			for (BOMMaintenanceVO objBOMMaintenanceVO : bomMaintenanceVO) {
				if (objBOMMaintenanceVO.getAction().equals(
						RMDServiceConstants.ADD)) {
					bom++;
				}
			}
			bomUpdateQuery
					.append(" UPDATE GETS_RMD.GETS_RMD_MASTER_BOM  A SET A.MASTER_BOM2PARM_DEF = :parameterObjid, A.BOM_STATUS =:status, A.EXPECTED_VALUE =:value, A.LAST_UPDATED_DATE = sysdate , A.LAST_UPDATED_BY = :userName,A.NOTIFICATION= :notifyFlagValue WHERE (A.objid = :strObjId) ");
			updatehibernateQuery = session.createSQLQuery(bomUpdateQuery
					.toString());
			bomSelectQuery
					.append(" select  config_item from gets_rmd_master_bom where master_bom2ctl_cfg= :configId and config_item = :configItem ");
			selectHibernateQuery = session.createSQLQuery(bomSelectQuery
					.toString());
			bomInsertQuery
					.append(" INSERT INTO GETS_RMD.GETS_RMD_MASTER_BOM (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, CREATED_BY, CONFIG_ITEM, ");
			bomInsertQuery
					.append(" MASTER_BOM2PARM_DEF, MASTER_BOM2CTL_CFG, BOM_STATUS, EXPECTED_VALUE, NOTIFICATION ) VALUES(gets_rmd_master_bom_seq.nextval,sysdate, :userName,sysdate,:userName,:configItem,:parameterObjid,:configId,:status,:value, :notifyFlagValue) ");
			insertHibernateQuery = session.createSQLQuery(bomInsertQuery
					.toString());
			bomProcedureQuery
					.append("call gets_sd_veh_cfg_pkg.create_veh_cfg_pr(null,:configId,:strObjId,:value,:userName,:noOfRecords)");
			callHibernateQuery = session.createSQLQuery(bomProcedureQuery
					.toString());
			bomQuery.append(" select objid from gets_rmd_master_bom where master_bom2ctl_cfg = :configId and config_item = :configItem ");
			bomCallQuery = session.createSQLQuery(bomQuery.toString());
			for (BOMMaintenanceVO objBOMMaintenanceVO : bomMaintenanceVO) {
				if (objBOMMaintenanceVO.getAction().equals(
						RMDServiceConstants.UPDATE)) {
					updatehibernateQuery.setParameter(
							RMDServiceConstants.PARAMETER_OBJID,
							objBOMMaintenanceVO.getParameterObjid());
					updatehibernateQuery.setParameter(
							RMDServiceConstants.STATUS_COLUMN,
							objBOMMaintenanceVO.getStatus());
					updatehibernateQuery.setParameter(
							RMDServiceConstants.VALUE,
							ESAPI.encoder().decodeForHTML(
									objBOMMaintenanceVO.getValue()));
					updatehibernateQuery.setParameter(
							RMDServiceConstants.USERNAME,
							objBOMMaintenanceVO.getUserName());
					updatehibernateQuery.setParameter(
							RMDServiceConstants.OBJID,
							objBOMMaintenanceVO.getObjid());
					updatehibernateQuery.setParameter(
							RMDCommonConstants.NOTIFY_FLAG_VALUE,
							objBOMMaintenanceVO.getNotificationFlag());
					updatehibernateQuery.executeUpdate();
				} else if (objBOMMaintenanceVO.getAction().equals(
						RMDServiceConstants.ADD)) {

					selectHibernateQuery.setParameter(
							RMDServiceConstants.CONFIG_ID,
							objBOMMaintenanceVO.getConfigId());
					selectHibernateQuery.setParameter(
							RMDServiceConstants.CONFIG_ITEM,
							objBOMMaintenanceVO.getConfigItem());
					resultList = selectHibernateQuery.list();
					if (resultList.size() == 0) {
						insertHibernateQuery.setParameter(
								RMDServiceConstants.USERNAME,
								objBOMMaintenanceVO.getUserName());
						insertHibernateQuery.setParameter(
								RMDServiceConstants.CONFIG_ITEM,
								objBOMMaintenanceVO.getConfigItem());
						insertHibernateQuery.setParameter(
								RMDServiceConstants.PARAMETER_OBJID,
								objBOMMaintenanceVO.getParameterObjid());
						insertHibernateQuery.setParameter(
								RMDServiceConstants.CONFIG_ID,
								objBOMMaintenanceVO.getConfigId());
						insertHibernateQuery.setParameter(
								RMDServiceConstants.STATUS_COLUMN,
								objBOMMaintenanceVO.getStatus());
						insertHibernateQuery.setParameter(
								RMDServiceConstants.VALUE,
								ESAPI.encoder().decodeForHTML(
										objBOMMaintenanceVO.getValue()));
						insertHibernateQuery.setParameter(
								RMDCommonConstants.NOTIFY_FLAG_VALUE,
								objBOMMaintenanceVO.getNotificationFlag());
						count = insertHibernateQuery.executeUpdate();
						if (count > 0) {
							bomCallQuery.setParameter(
									RMDServiceConstants.CONFIG_ID,
									objBOMMaintenanceVO.getConfigId());
							bomCallQuery.setParameter(
									RMDServiceConstants.CONFIG_ITEM,
									objBOMMaintenanceVO.getConfigItem());
							queryList = bomCallQuery.list();
							if (RMDCommonUtility
									.isCollectionNotEmpty(queryList)) {
								for (final Iterator<BigDecimal> obj = queryList
										.iterator(); obj.hasNext();) {
									final BigDecimal configDetails = obj.next();
									objid = RMDCommonUtility
											.convertObjectToString(configDetails);
									callHibernateQuery.setParameter(
											RMDServiceConstants.CONFIG_ID,
											objBOMMaintenanceVO.getConfigId());
									callHibernateQuery.setParameter(
											RMDServiceConstants.OBJID, objid);
									callHibernateQuery.setParameter(
											RMDServiceConstants.VALUE,
											ESAPI.encoder().decodeForHTML(
													objBOMMaintenanceVO
															.getValue()));
									callHibernateQuery.setParameter(
											RMDServiceConstants.USERNAME,
											objBOMMaintenanceVO.getUserName());
									callHibernateQuery.setParameter(
											RMDServiceConstants.NO_OF_RECORDS,
											bom);
									callHibernateQuery.executeUpdate();
								}
							}
							queryList = null;
						}
					} else {
						status = RMDCommonConstants.ALREADY_PRESENT;
					}
					resultList = null;
				}
			}
			transaction.commit();
			status = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			transaction.rollback();
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_BOM_CONFIG_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return status;
	}

}
