/**
 * ============================================================
 * Classification: GE Confidential
 * File : RepairCodeMaintDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.asset.bo.impl;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.common.util.RMDCommonDAO;
import com.ge.trans.eoa.services.asset.dao.intf.RepairCodeMaintDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RepairCodeEoaDetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class RepairCodeMaintDAOImpl extends RMDCommonDAO implements RepairCodeMaintDAOIntf {

	private static final long serialVersionUID = 1L;
	public static final RMDLogger LOGGER = RMDLoggerHelper
	.getLogger(RepairCodeMaintDAOImpl.class);

	/**
	 * @Author:
	 * @param:String selectBy, String condition, String status, String models,
	 *               String value
	 * @return:List<RepairCodeEoaDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching repair codes based on
	 *               search criteria.
	 */
	@Override
	public List<RepairCodeEoaDetailsVO> getRepairCodesList(String selectBy,
			String condition, String status, String models, String value)
			throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside getRepairCodesList() method");
		StringBuffer strQuery = null;
		Session session = null;
		List queryList = new ArrayList();
		String C_sql_value = "";
		List<RepairCodeEoaDetailsVO> objRepairCodeEoaDetailsVOList = null;
		List<String> repCodeList=new ArrayList<String>();
		RepairCodeEoaDetailsVO objRepairCodeEoaDetailsVO = null;
		Map<String, String> repCodeMap = new HashMap<String, String>();
		Map<String,String> modelsMap=null;
		value=ESAPI.encoder().decodeForHTML(value);
		try {
			session = getHibernateSession();
			modelsMap=getModelsForRepairCode(selectBy,condition,status,models,value);
			strQuery = new StringBuffer();
			if (condition.equals("CN")) {
				condition = "like ";
				if(value.length()>0)
				{
				C_sql_value = "%" + value + "%";
				}
			} else if (condition.equals("SW")) {
				condition = "like ";
				if(value.length()>0)
				{
				C_sql_value = value + "%";
				}
			} else if (condition.equals("EW")) {
				condition = "like ";
				if(value.length()>0)
				{
				C_sql_value = "%" + value;
				}
			} else {
				condition = "=";
				if(value.length()>0)
				{
				C_sql_value = value;
				}
			}
			if(selectBy.equals(RMDCommonConstants.DISCRIPTION))
			{
				selectBy="repair_desc";
			}
			else
			{
				selectBy="repair_code";
			}
			strQuery.append("Select repair_code,repair_desc,model_name,repair_status from ");
			strQuery.append("gets_sd_rep_mtm_mod rep_mod,gets_sd_repair_codes rep_mas,gets_rmd_model rmdmod  ");
			strQuery.append("where rep_mod.mtmmod2repair = rep_mas.objid and rep_mod.mtmmod2model =rmdmod.objid ");
			if (C_sql_value.trim().length() > 0)
			{
				strQuery.append(" and upper(");
				strQuery.append(selectBy);   
				strQuery.append(") "); 
				strQuery.append(condition); 
				strQuery.append(" upper(:value) ");
			}
			if (!(models.equals("ALL"))) {
				strQuery.append(" and model_name =:models ");
			}
			if (status.equals("ALL")) {
				strQuery.append(" order by repair_code,mtmmod2model");
			} else {
				strQuery.append(" and repair_status =:status ");
				strQuery.append(" order by repair_code,mtmmod2model");
			}
			Query query = session.createSQLQuery(strQuery.toString());
			if (C_sql_value.trim().length() > 0)
			{
			//query.setParameter(RMDCommonConstants.SELECT_BY, selectBy);
			//query.setParameter(RMDCommonConstants.CONDITION, condition);
			query.setParameter(RMDCommonConstants.VALUE, C_sql_value);
			}
			if (!(models.equals("ALL"))) 
			{
			query.setParameter(RMDCommonConstants.MODELS, models);
			}
			if(!status.equals("ALL"))
			{
			query.setParameter(RMDCommonConstants.STATUS, status);
			}
			queryList = query.list();
			if (queryList != null && queryList.size() > 0) {
				objRepairCodeEoaDetailsVOList = new ArrayList<RepairCodeEoaDetailsVO>(queryList.size());
				for (final Iterator<Object> iter = queryList.iterator(); iter
						.hasNext();) {
					objRepairCodeEoaDetailsVO = new RepairCodeEoaDetailsVO();
					final Object[] objRec = (Object[]) iter.next();
					
					if(repCodeList.isEmpty()){
						String repCode=RMDCommonUtility
						.convertObjectToString(objRec[0]);
								objRepairCodeEoaDetailsVO.setRepairCode(repCode);
								objRepairCodeEoaDetailsVO
										.setRepairCodeDesc(ESAPI.encoder().encodeForXML(RMDCommonUtility
												.convertObjectToString(objRec[1])));
								objRepairCodeEoaDetailsVO.setModels(modelsMap.get(repCode));
								objRepairCodeEoaDetailsVO.setStatus((RMDCommonUtility
										.convertObjectToString(objRec[3])));
								repCodeList.add(repCode);
								objRepairCodeEoaDetailsVOList
										.add(objRepairCodeEoaDetailsVO);
							}
					else
					{
						if(!repCodeList.contains(RMDCommonUtility
						.convertObjectToString(objRec[0])))
						{

							String repCode=RMDCommonUtility
							.convertObjectToString(objRec[0]);
									objRepairCodeEoaDetailsVO.setRepairCode(repCode);
									objRepairCodeEoaDetailsVO
											.setRepairCodeDesc(ESAPI.encoder().encodeForXML(RMDCommonUtility
													.convertObjectToString(objRec[1])));
									objRepairCodeEoaDetailsVO.setModels(modelsMap.get(repCode));
									objRepairCodeEoaDetailsVO.setStatus((RMDCommonUtility
											.convertObjectToString(objRec[3])));
									repCodeList.add(repCode);
									repCodeMap.put(repCode, RMDCommonUtility
											.convertObjectToString(objRec[2]));
									objRepairCodeEoaDetailsVOList
											.add(objRepairCodeEoaDetailsVO);
						
						}
					}
				}
			}
			queryList=null;
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception getRepairCodesList() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return objRepairCodeEoaDetailsVOList;

	}

	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@Override
	public String repairCodeValidations(String repairCode,
			String repairCodeDesc, String flag) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside repairCodeValidations() method");
		StringBuffer strQuery = null;
		Session session = null;
		List queryList = new ArrayList();
		String result = null;
		repairCodeDesc=ESAPI.encoder().encodeForXML(repairCodeDesc);
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			strQuery.append(" select count(1) from gets_sd_repair_codes where ");
			if (flag.equalsIgnoreCase(RMDCommonConstants.REPAIRCODE))
				strQuery.append(" repair_code =:repairCode");
			if (flag.equalsIgnoreCase(RMDCommonConstants.REPAIR_CODE_DESC))
				strQuery.append(" upper(repair_desc) = upper(:repairCodeDesc)");
			Query query = session.createSQLQuery(strQuery.toString());
			if (flag.equalsIgnoreCase(RMDCommonConstants.REPAIRCODE))
				query.setParameter(RMDCommonConstants.REPAIRCODE, repairCode);
			if (flag.equalsIgnoreCase(RMDCommonConstants.REPAIR_CODE_DESC))
				query.setParameter(RMDCommonConstants.REPAIR_CODE_DESC,
						repairCodeDesc);
			queryList = query.list();
			BigDecimal count = (BigDecimal) query.uniqueResult();
			int value = count.intValue();
			if (!RMDCommonConstants.ZERO_STRING.equals(String.valueOf(value))) {
				result = RMDCommonConstants.REPAIR_CODE_DETAILS_ALREADY_EXISTS;
			} 
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception repairCodeValidations() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return result;
	}
	
	
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String status,String models
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for adding repair codes
	 */
	@Override
	public String addRepairCodes(String repairCode,
			String repairCodeDesc, String status,String models) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside addRepairCodes() method");
		StringBuffer strQuery = null;
		Session session = null;
		String result = null;
		String repObjId=null;
		repairCodeDesc=ESAPI.encoder().encodeForXML(repairCodeDesc);
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			strQuery.append("insert into gets_sd_repair_codes(objid,creation_date,Last_updated_date, ");
			strQuery.append("Last_updated_by,CREATED_BY,repair_code,repair_desc,repair_status)  values ");
			strQuery.append("(gets_sd_repair_codes_seq.nextval,sysdate,sysdate,:createdBy,:createdBy,:repairCode,:repairCodeDesc,:status)");
			Query query = session.createSQLQuery(strQuery.toString());
			query.setParameter(RMDCommonConstants.CREATED_BY,
					RMDCommonConstants.REPAIR_CODE_MAINTENANCE);
			query.setParameter(RMDCommonConstants.REPAIRCODE,
					repairCode);
			query.setParameter(RMDCommonConstants.REPAIR_CODE_DESC,
					repairCodeDesc);
			query.setParameter(RMDCommonConstants.STATUS,
					status);
			query.executeUpdate();
			result = RMDCommonConstants.SUCCESS;
			if(result.equalsIgnoreCase(RMDCommonConstants.SUCCESS))
			{
				repObjId=getRepairCodeObjId(repairCode);
			}
			if(repObjId!=null)
			{
				result=addRepairCodesToMTMTable(repObjId, models);
			}
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception addRepairCodes() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return result;
	}
	
	
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	public String getRepairCodeObjId(String repairCode) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside getRepairCodeObjId() method");
		StringBuffer strQuery = null;
		Session session = null;
		String repObjId = null;
		List queryList = new ArrayList();
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			strQuery.append("select objid from gets_sd_repair_codes where repair_code =:repairCode");
			Query query = session.createSQLQuery(strQuery.toString());
			query.setParameter(RMDCommonConstants.REPAIRCODE,
					repairCode);
			queryList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(queryList)) {
				if (queryList.size() == 1) {
					repObjId = RMDCommonUtility
							.convertObjectToString(queryList.get(0));
				}
			}
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception getRepairCodeObjId() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return repObjId;
	}
	
	
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	public String addRepairCodesToMTMTable(String repObjId,String models) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside addRepairCodesToMTMTable() method");
		StringBuffer strQuery = null;
		Session session = null;
		String result = null;
		String modelObjIds=null;
		try {
			session = getHibernateSession();
			modelObjIds=getModelObjId(models);
			List<String> modelObjIdList = Arrays.asList(modelObjIds
					.split(RMDCommonConstants.COMMMA_SEPARATOR));
			
			for(int i=0;i<modelObjIdList.size();i++)
			{
				strQuery = new StringBuffer();
				strQuery.append("insert into gets_sd_rep_mtm_mod(objid,last_updated_date,last_updated_by,creation_date,created_by,mtmmod2repair,mtmmod2model)  ");
				strQuery.append("values (gets_sd_rep_mtm_mod_seq.nextval,SYSDATE,:updatedBy,SYSDATE,:createdBy,");
				strQuery.append(":repObjId,:models)");
				Query query = session.createSQLQuery(strQuery.toString());
				query.setParameter(RMDCommonConstants.UPDATED_BY,
						RMDCommonConstants.REPAIR_CODE_MAINTENANCE);
				query.setParameter(RMDCommonConstants.CREATED_BY,
						RMDCommonConstants.REPAIR_CODE_MAINTENANCE);
				query.setParameter(RMDCommonConstants.REP_OBJ_ID,
						repObjId);
				query.setParameter(RMDCommonConstants.MODELS,
						modelObjIdList.get(i));
				query.executeUpdate();
				result = RMDCommonConstants.SUCCESS;
			}
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception addRepairCodesToMTMTable() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return result;
	}
	
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	public String getModelObjId(String models) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside getModelObjId() method");
		StringBuffer strQuery = null;
		Session session = null;
		List<String> modelsList = Arrays.asList(models
				.split(RMDCommonConstants.COMMMA_SEPARATOR));
		String modelObjIdList="";
		List queryList = new ArrayList();
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			strQuery.append("select objid from gets_rmd_model where model_name in(:models)");
			Query query = session.createSQLQuery(strQuery.toString());
			query.setParameterList(RMDCommonConstants.MODELS,
					modelsList);
			queryList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(queryList)) {
				if (queryList.size() >0) {
					for(int i=0;i<queryList.size();i++)
					{
						modelObjIdList =modelObjIdList+RMDCommonUtility
						.convertObjectToString(queryList.get(i))+RMDCommonConstants.COMMMA_SEPARATOR;
					}
					
				}
			}
			modelObjIdList=modelObjIdList.substring(0,modelObjIdList.length()-1);
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception getModelObjId() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return modelObjIdList;
	}
	
	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	@Override
	public String updateRepairCodes(String repairCode,
			String repairCodeDesc, String status,String models) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside updateRepairCodes() method");
		StringBuffer strQuery = null;
		Session session = null;
		String result = null;
		String repObjId=null;
		repairCodeDesc=ESAPI.encoder().encodeForXML(repairCodeDesc);
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			repObjId=getRepairCodeObjId(repairCode);
			result=deleteRepairCodeMTMTable(repObjId);
			if(result.equalsIgnoreCase(RMDCommonConstants.SUCCESS))
			{
				strQuery.append("update gets_sd_repair_codes set repair_status =:status, REPAIR_DESC =:repairCodeDesc,repair_code =:repairCode where objid=:repObjId");
				Query query = session.createSQLQuery(strQuery.toString());
				query.setParameter(RMDCommonConstants.STATUS,
						status);
				query.setParameter(RMDCommonConstants.REPAIRCODE,
						repairCode);
				query.setParameter(RMDCommonConstants.REPAIR_CODE_DESC,
						repairCodeDesc);
				query.setParameter(RMDCommonConstants.REP_OBJ_ID,
						repObjId);
				query.executeUpdate();
				result = RMDCommonConstants.SUCCESS;
				if(result.equalsIgnoreCase(RMDCommonConstants.SUCCESS))
				{
				result=addRepairCodesToMTMTable(repObjId,models);
				}
			}
		}
		 catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception updateRepairCodes() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return result;
	}


	/**
	 * @Author:
	 * @param:String repairCode, String repairCodeDesc, String flag
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description:This method is used for validating the repair codes and
	 *                   repair code description.
	 */
	public String deleteRepairCodeMTMTable(String repObjId) throws RMDDAOException {
		LOGGER.debug("RepairCodeMaintDAOImpl: inside deleteRepairCodeMTMTable() method");
		StringBuffer strQuery = null;
		Session session = null;
		String result = null;
		try {
			session = getHibernateSession();
			strQuery = new StringBuffer();
			strQuery.append("delete from gets_sd_rep_mtm_mod where mtmmod2repair =:repObjId");
			Query query = session.createSQLQuery(strQuery.toString());
				query.setParameter(RMDCommonConstants.REP_OBJ_ID,
						repObjId);
				query.executeUpdate();
				result = RMDCommonConstants.SUCCESS;
		} catch (RMDDAOException e) {
			LOGGER.error("Exception occurred:", e);
			throw new RMDDAOException(e.getErrorDetail().getErrorCode(),
					new String[] {}, e.getErrorDetail().getErrorMessage(), e, e
							.getErrorDetail().getErrorType());
		} catch (Exception e) {
			LOGGER.error(
					"RepairCodeMaintDAOImpl : Exception deleteRepairCodeMTMTable() ",
					e);
			throw new RMDDAOException(RMDCommonConstants.COMMON_ERROR_MSG,
					e.getMessage());
		} finally {
			releaseSession(session);
		}
		return result;
	}
	
	/**
	 * @Author:
	 * @param :RxSearchCriteriaEoaServiceVO
	 * @return:Map<String,String>
	 * @throws:RMDDAOException
	 * @Description: This method is used to get models for the recommendations
	 *               which are listed.
	 */
	public Map<String, String> getModelsForRepairCode(
			String selectBy,
			String condition, String status, String models, String value)
			throws RMDDAOException {
		Session session = null;
		Map<String, String> modelsMap = new HashMap<String, String>();
		StringBuffer strQuery = null;
		String c_sql_value = "";
		try {
			session = getHibernateSession();
			strQuery=new  StringBuffer();
			if (condition.equals("CN")) {
				condition = "like ";
				if(value.length()>0)
				{
				c_sql_value = "%" + value + "%";
				}
			} else if (condition.equals("SW")) {
				condition = "like ";
				if(value.length()>0)
				{
				c_sql_value = value + "%";
				}
			} else if (condition.equals("EW")) {
				condition = "like ";
				if(value.length()>0)
				{
				c_sql_value = "%" + value;
				}
			} else {
				condition = "=";
				if(value.length()>0)
				{
				c_sql_value = value;
				}
			}
			if(selectBy.equals(RMDCommonConstants.DISCRIPTION))
			{
				selectBy="repair_desc";
			}
			else
			{
				selectBy="repair_code";
			}
			strQuery.append("Select distinct rep_mas.repair_code,rmdmod.model_name from gets_sd_rep_mtm_mod rep_mod, ");
			strQuery.append("gets_sd_repair_codes rep_mas,gets_rmd_model rmdmod ");
			strQuery.append("where rep_mod.mtmmod2repair = rep_mas.objid and rep_mod.mtmmod2model =rmdmod.objid ");
			if (c_sql_value.trim().length() > 0){
				strQuery.append(" and upper(");
				strQuery.append(selectBy);   
				strQuery.append(") ");
				strQuery.append(condition); 
				strQuery.append(" upper(:c_sql_value) ");
			}
			if (!(models.equals("ALL"))) {
				strQuery.append(" and model_name =:models ");
			}
			if (status.equals("ALL")) {
				strQuery.append(" order by rep_mas.repair_code,rmdmod.model_name");
			} else {
				strQuery.append(" and repair_status =:status ");
				strQuery.append(" order by rep_mas.repair_code,rmdmod.model_name");
			}
			
			Query query = session.createSQLQuery(strQuery.toString());
			if (c_sql_value.trim().length() > 0)
			{
			query.setParameter(RMDCommonConstants.C_SQL_VALUE, c_sql_value);
			}
			if (!(models.equals("ALL"))) 
			{
			query.setParameter(RMDCommonConstants.MODELS, models);
			}
			if(!status.equals("ALL"))
			{
			query.setParameter(RMDCommonConstants.STATUS, status);
			}
			List<Object[]>	queryList = query.list();		
			for (Object[] currentObject : queryList) {
				String repCode = RMDCommonUtility
						.convertObjectToString(currentObject[0]);
				String model = RMDCommonUtility
						.convertObjectToString(currentObject[1]);
				if (modelsMap.containsKey(repCode)) {
					modelsMap.put(repCode, modelsMap.get(repCode)
							+ RMDCommonConstants.COMMMA_SEPARATOR + model);
				} else {
					modelsMap.put(repCode, model);
				}
			}
		} catch (Exception e) {
			LOG.error(
					"Unexpected Error occured in FindCaseDAOImpl getModelsForRx()",
					e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODELS_FOR_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDServiceConstants.LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);

		} finally {
			releaseSession(session);
		}

		return modelsMap;
	}
}
