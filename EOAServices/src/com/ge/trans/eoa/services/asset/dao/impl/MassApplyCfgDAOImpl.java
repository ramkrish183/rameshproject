/**
 * ============================================================
 * Classification: GE Confidential
 * File : MassApplyCfgServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.assets.resources;
 * Author : Capgemini India
 * Last Edited By :
 * Version : 1.0
 * Created on : August 2, 2011
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2011 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.asset.dao.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.AsyncResult;

import com.ge.trans.eoa.services.asset.dao.intf.CMUPassthroughDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.MassApplyCfgDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.MessageDefinitionDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ApplyEFICfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CfgAssetSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigSearchVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MassApplyDeleteVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MessageDefVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MessageQueuesVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RCIMessageRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleCfgTemplateVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VerifyCfgTemplateVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.util.CMUPassthroughMessageSender;
import com.ge.trans.eoa.services.common.util.ConfigMaintenanceUtility;
import com.ge.trans.eoa.services.common.util.MessageSender;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.Base64;

/*******************************************************************************
 * 
 * @Author : Capgemini
 * @Version : 1.0
 * @Date Created: Sep 29, 2016
 * @Date Modified : Feb 6, 2017
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/

public class MassApplyCfgDAOImpl extends BaseDAO implements MassApplyCfgDAOIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final RMDLogger massApplyCfgLogger = RMDLogger
			.getLogger(MassApplyCfgDAOImpl.class);

	private JdbcTemplate jdbcTemplate;

	private MessageDefinitionDAOIntf messageDefinitionDAOIntf;

	public void setMessageDefinitionDAOIntf(
			MessageDefinitionDAOIntf messageDefinitionDAOIntf) {
		this.messageDefinitionDAOIntf = messageDefinitionDAOIntf;
	}
	
	@Autowired
	CMUPassthroughDAOIntf objCmuPassthroughDAOIntf;
	
	@Autowired
	CMUPassthroughMessageSender objCmuPassthroughMessageSender;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
    @Autowired @Qualifier("rciConfigMessageSender")
    private MessageSender mqSender;

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type EDP.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MassApplyCfgVO> getEDPConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException {
		List<MassApplyCfgVO> arlEDPConfigs = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("gets_rmd")
					.withCatalogName("GETS_RMD_MASS_APPLY_CFG_PKG")
					.withFunctionName("GET_CFG_DETAILS_FN")
					.declareParameters(
							new SqlOutParameter("cr_CFG", OracleTypes.CURSOR,
									ParameterizedBeanPropertyRowMapper
											.newInstance(MassApplyCfgVO.class)),
							new SqlOutParameter("p_errorcode", Types.INTEGER),
							new SqlOutParameter("p_errorstring", Types.VARCHAR));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap
					.put("p_ctlcfgobjid", objConfigSearchVO.getCtrlcfgObjId());
			if (!objConfigSearchVO.isCaseMassApplyCFG()) {
				inParamMap.put("p_param", RMDCommonConstants.STRING_MENU);
				inParamMap.put("p_vehobjid", RMDCommonConstants.EMPTY_STRING);
			} else {
				inParamMap.put("p_param", RMDCommonConstants.EMPTY_STRING);
				inParamMap.put("p_vehobjid",
						objConfigSearchVO.getVehicleObjId());
			}
			inParamMap.put("p_cfgtype", RMDCommonConstants.EDP);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			arlEDPConfigs = (List<MassApplyCfgVO>) simpleJdbcCallResult
					.get("cr_CFG");
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return arlEDPConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FFD.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MassApplyCfgVO> getFFDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException {
		List<MassApplyCfgVO> arlFFDConfigs = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("gets_rmd")
					.withCatalogName("GETS_RMD_MASS_APPLY_CFG_PKG")
					.withFunctionName("GET_FFD_DETAILS_FN")
					.declareParameters(
							new SqlOutParameter("cr_FFD", OracleTypes.CURSOR,
									ParameterizedBeanPropertyRowMapper
											.newInstance(MassApplyCfgVO.class)),
							new SqlOutParameter("p_errorcode", Types.INTEGER),
							new SqlOutParameter("p_errorstring", Types.VARCHAR));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap
					.put("p_ctlcfgobjid", objConfigSearchVO.getCtrlcfgObjId());
			if (!objConfigSearchVO.isCaseMassApplyCFG()) {
				inParamMap.put("p_param", RMDCommonConstants.STRING_MENU);
				inParamMap.put("p_vehobjid", RMDCommonConstants.EMPTY_STRING);
			} else {
				inParamMap.put("p_param", RMDCommonConstants.EMPTY_STRING);
				inParamMap.put("p_vehobjid",
						objConfigSearchVO.getVehicleObjId());
			}
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			arlFFDConfigs = (List<MassApplyCfgVO>) simpleJdbcCallResult
					.get("cr_FFD");

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FFD_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return arlFFDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<MassApplyCfgVO>
	 * @param :String ctrlCfgObjId,String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Configurations of
	 *               Type FRD.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MassApplyCfgVO> getFRDConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException {
		List<MassApplyCfgVO> arlFRDConfigs = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("gets_rmd")
					.withCatalogName("GETS_RMD_MASS_APPLY_CFG_PKG")
					.withFunctionName("GET_FRD_DETAILS_FN")
					.declareParameters(
							new SqlOutParameter("cr_FRD", OracleTypes.CURSOR,
									ParameterizedBeanPropertyRowMapper
											.newInstance(MassApplyCfgVO.class)),
							new SqlOutParameter("p_errorcode", Types.INTEGER),
							new SqlOutParameter("p_errorstring", Types.VARCHAR));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap
					.put("p_ctlcfgobjid", objConfigSearchVO.getCtrlcfgObjId());
			if (!objConfigSearchVO.isCaseMassApplyCFG()) {
				inParamMap.put("p_param", RMDCommonConstants.STRING_MENU);
				inParamMap.put("p_vehobjid", RMDCommonConstants.EMPTY_STRING);
			} else {
				inParamMap.put("p_param", RMDCommonConstants.EMPTY_STRING);
				inParamMap.put("p_vehobjid",
						objConfigSearchVO.getVehicleObjId());
			}
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			arlFRDConfigs = (List<MassApplyCfgVO>) simpleJdbcCallResult
					.get("cr_FRD");
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FFD_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return arlFRDConfigs;
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTemplateVO>
	 * @param :List<String> edpObjIdList
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations of type EDP.
	 * 
	 */
	@Override
	public Future<List<VerifyCfgTemplateVO>> getEDPTemplates(
			List<String> edpObjIdList) throws RMDDAOException {

		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder edpTemplateQuery = new StringBuilder();
		List<VerifyCfgTemplateVO> arlVerifyCfgTemplateVOs = null;
		VerifyCfgTemplateVO objCfgTempaltesVO = null;
		try {
			objSession = getHibernateSession();
			edpTemplateQuery
					.append("SELECT CFG.CFG_TYPE,CFG.OBJID,CFG.CFG_DEF_TEMPLATE,CFG.CFG_DEF_VERSION,CFG.CFG_DEF_DESC,CFG.STATUS FROM ");
			edpTemplateQuery
					.append("GETS_OMI.GETS_OMI_CFG_DEF CFG WHERE CFG.OBJID IN(:cfgObjId) AND CFG.CFG_TYPE = 'EDP' ");
			hibernateQuery = objSession.createSQLQuery(edpTemplateQuery
					.toString());
			hibernateQuery.setParameterList(RMDCommonConstants.CFG_OBJID,
					edpObjIdList);
			List<Object[]> arlTemplates = hibernateQuery.list();
			if (null != arlTemplates && !arlTemplates.isEmpty()) {
				arlVerifyCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
						arlTemplates.size());
				for (Object[] currentTemplate : arlTemplates) {
					objCfgTempaltesVO = new VerifyCfgTemplateVO();
					objCfgTempaltesVO.setCfgFile(RMDCommonUtility
							.convertObjectToString(currentTemplate[0]));
					objCfgTempaltesVO.setObjid(RMDCommonUtility
							.convertObjectToString(currentTemplate[1]));
					objCfgTempaltesVO.setTemplate(RMDCommonUtility
							.convertObjectToString(currentTemplate[2]));
					objCfgTempaltesVO.setVersion(RMDCommonUtility
							.convertObjectToString(currentTemplate[3]));
					objCfgTempaltesVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
							.convertObjectToString(currentTemplate[4])));
					objCfgTempaltesVO.setStatus(RMDCommonUtility
							.convertObjectToString(currentTemplate[5]));
					arlVerifyCfgTemplateVOs.add(objCfgTempaltesVO);
				}
			}
			arlTemplates = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_TEMPLATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}

		return new AsyncResult<List<VerifyCfgTemplateVO>>(
				arlVerifyCfgTemplateVOs);
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTemplateVO>
	 * @param :List<String> ffdObjIdList
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations of type FFD.
	 * 
	 */
	@Override
	public Future<List<VerifyCfgTemplateVO>> getFFDTemplates(
			List<String> ffdObjIdList) throws RMDDAOException {

		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder ffdTemplateQuery = new StringBuilder();
		List<VerifyCfgTemplateVO> arlFFDVerifyCfgTemplateVOs = null;
		VerifyCfgTemplateVO objCfgTempaltesVO = null;
		try {
			objSession = getHibernateSession();
			ffdTemplateQuery
					.append(" SELECT cast('FFD' as varchar(3)),CFG.OBJID,CFG.FLT_FILTER_TEMPLATE,CFG.FLT_FILTER_VERSION,CFG.FLT_FILTER_DESC ");
			ffdTemplateQuery
					.append(" ,CFG.STATUS FROM GETS_OMI.GETS_OMI_FLT_FILTER_DEF CFG WHERE CFG.OBJID IN(:cfgObjId) ");
			hibernateQuery = objSession.createSQLQuery(ffdTemplateQuery
					.toString());
			hibernateQuery.setParameterList(RMDCommonConstants.CFG_OBJID,
					ffdObjIdList);
			List<Object[]> arlTemplates = hibernateQuery.list();
			if (null != arlTemplates && !arlTemplates.isEmpty()) {
				arlFFDVerifyCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
						arlTemplates.size());
				for (Object[] currentTemplate : arlTemplates) {
					objCfgTempaltesVO = new VerifyCfgTemplateVO();
					objCfgTempaltesVO.setCfgFile(RMDCommonUtility
							.convertObjectToString(currentTemplate[0]));
					objCfgTempaltesVO.setObjid(RMDCommonUtility
							.convertObjectToString(currentTemplate[1]));
					objCfgTempaltesVO.setTemplate(RMDCommonUtility
							.convertObjectToString(currentTemplate[2]));
					objCfgTempaltesVO.setVersion(RMDCommonUtility
							.convertObjectToString(currentTemplate[3]));
					objCfgTempaltesVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
							.convertObjectToString(currentTemplate[4])));
					objCfgTempaltesVO.setStatus(RMDCommonUtility
							.convertObjectToString(currentTemplate[5]));
					arlFFDVerifyCfgTemplateVOs.add(objCfgTempaltesVO);
				}
			}
			arlTemplates = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FFD_TEMPLATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}

		return new AsyncResult<List<VerifyCfgTemplateVO>>(
				arlFFDVerifyCfgTemplateVOs);
	}

	/**
	 * @Author :
	 * @return :List<VerifyCfgTemplateVO>
	 * @param :String frdObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations of type FRD.
	 * 
	 */
	@Override
	public Future<List<VerifyCfgTemplateVO>> getFRDTemplates(
			List<String> frdObjId) throws RMDDAOException {
		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder frdTemplateQuery = new StringBuilder();
		List<VerifyCfgTemplateVO> arlCfgTemplateVOs = null;
		VerifyCfgTemplateVO objCfgTempaltesVO = null;
		try {
			if (null != frdObjId) {
				objSession = getHibernateSession();
				frdTemplateQuery
						.append(" SELECT cast('FRD' as varchar(3)),CFG.OBJID,CFG.FLT_RANGE_DEF_TEMPLATE,CFG.FLT_RANGE_DEF_VERSION, ");
				frdTemplateQuery
						.append("CFG.FLT_RANGE_DEF_DESC,CFG.STATUS FROM GETS_OMI.GETS_OMI_FLT_RANGE_DEF CFG WHERE CFG.OBJID IN(:cfgObjId) ");
				hibernateQuery = objSession.createSQLQuery(frdTemplateQuery
						.toString());
				hibernateQuery.setParameterList(RMDCommonConstants.CFG_OBJID,
						frdObjId);
				List<Object[]> arlTemplates = hibernateQuery.list();
				if (null != arlTemplates && !arlTemplates.isEmpty()) {
					arlCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
							arlTemplates.size());
					for (Object[] currentTemplate : arlTemplates) {
						objCfgTempaltesVO = new VerifyCfgTemplateVO();
						objCfgTempaltesVO.setCfgFile(RMDCommonUtility
								.convertObjectToString(currentTemplate[0]));
						objCfgTempaltesVO.setObjid(RMDCommonUtility
								.convertObjectToString(currentTemplate[1]));
						objCfgTempaltesVO.setTemplate(RMDCommonUtility
								.convertObjectToString(currentTemplate[2]));
						objCfgTempaltesVO.setVersion(RMDCommonUtility
								.convertObjectToString(currentTemplate[3]));
						objCfgTempaltesVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
								.convertObjectToString(currentTemplate[4])));
						objCfgTempaltesVO.setStatus(RMDCommonUtility
								.convertObjectToString(currentTemplate[5]));
						arlCfgTemplateVOs.add(objCfgTempaltesVO);
					}
				}
				arlTemplates = null;
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FRD_TEMPLATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return new AsyncResult<List<VerifyCfgTemplateVO>>(arlCfgTemplateVOs);
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@Override
	public List<String> getOnboardSoftwareVersion(String ctrlCfgObjId)
			throws RMDDAOException {

		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder softwareVersionQuery = new StringBuilder();
		List<String> softwareVersionList = null;
		try {
			objSession = getHibernateSession();
			softwareVersionQuery
					.append(" SELECT DISTINCT VEHCFG.CURRENT_VERSION FROM GETS_RMD_VEHCFG VEHCFG,GETS_RMD_MASTER_BOM BOM WHERE BOM.OBJID = VEHCFG.VEHCFG2MASTER_BOM ");
			softwareVersionQuery
					.append(" AND  BOM.CONFIG_ITEM = 'ONBOARD SW VERSION' AND VEHCFG.CURRENT_VERSION IS NOT NULL AND BOM.MASTER_BOM2CTL_CFG =:ctrlCfgObjId");
			hibernateQuery = objSession.createSQLQuery(softwareVersionQuery
					.toString());
			hibernateQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);
			List<Object> arlSoftWareVersion = hibernateQuery.list();
			if (null != arlSoftWareVersion && !arlSoftWareVersion.isEmpty()) {
				softwareVersionList = new ArrayList<String>(
						arlSoftWareVersion.size());
				for (Object object : arlSoftWareVersion) {
					softwareVersionList.add(RMDCommonUtility
							.convertObjectToString(object));
				}
			}
			arlSoftWareVersion = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ONBOARD_SOFTWARE_VERSIONS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return softwareVersionList;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :String cfgType, String templateObjId, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected Template
	 *               Versions.
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCfgTemplateVersions(String cfgType, String template,
			String ctrlCfgObjId) throws RMDDAOException {
		List<String> cfgTempalteVersionList = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("gets_rmd")
					.withCatalogName("gets_sd_veh_get_pkg")
					.withFunctionName("GET_VER_VEH_CTL_FN")
					.declareParameters(
							new SqlOutParameter("VersionCur",
									OracleTypes.CURSOR,
									new SingleColumnRowMapper<String>(
											String.class)),
							new SqlOutParameter("returnCode", Types.INTEGER));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("cfgType", cfgType);
			inParamMap.put("cfgDefTemplate", template);
			inParamMap.put("cfgDefCtlCfg", ctrlCfgObjId);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			cfgTempalteVersionList = (List<String>) simpleJdbcCallResult
					.get("VersionCur");
			int returnCode = RMDCommonUtility
					.convertObjectToInt(simpleJdbcCallResult.get("returnCode"));
			if (returnCode == -1) {
				massApplyCfgLogger.info("No Data Found for cfgType : "
						+ cfgType + " template " + template);
			}

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CFG_TEMPLATE_VERSIONS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return cfgTempalteVersionList;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :AssetSearchVO objAssetSearchVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching selected
	 *               Configurations for applying.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSpecificAssetNumbers(AssetSearchVO objAssetSearchVO)
			throws RMDDAOException {
		List<String> arlAssetNumbers = null;
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("gets_rmd")
					.withCatalogName("GETS_SD_VEH_GET_PKG")
					.withProcedureName("VEH_CTLCFG_NOS_PR")
					.declareParameters(
							new SqlOutParameter("cr_vehicleNos",
									OracleTypes.CURSOR,
									new SingleColumnRowMapper<String>(
											String.class)),
							new SqlOutParameter("p_returnCode", Types.INTEGER));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("p_defaultCustomer",
					objAssetSearchVO.getCustomerId());
			inParamMap.put("p_defaultRoadNumberHdr",
					objAssetSearchVO.getAssetGroupName());
			inParamMap.put("p_defaultRoadNFrom",
					objAssetSearchVO.getAssetFrom());
			inParamMap.put("p_defaultRoadNTo", objAssetSearchVO.getAssetTo());
			inParamMap.put("p_defaultctlcfg",
					Long.parseLong(objAssetSearchVO.getCtrlCfgObjId()));
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			arlAssetNumbers = (List<String>) simpleJdbcCallResult
					.get("cr_vehicleNos");
			int returnCode = RMDCommonUtility
					.convertObjectToInt(simpleJdbcCallResult
							.get("p_returnCode"));
			if (returnCode == 0 && null != arlAssetNumbers
					&& arlAssetNumbers.isEmpty()) {
				arlAssetNumbers.add(RMDCommonConstants.NO_DATA_FOUND);
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SPECIFIC_ASSET_NUMBERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return arlAssetNumbers;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */

	@Override
	public List<String> applyCfgTemplates(
			ApplyCfgTemplateVO objApplyCfgTemplateVO) throws RMDDAOException {

		List<VehicleDetailsVO> arlVehicleDetailsVOs = new ArrayList<VehicleDetailsVO>();
		List<VehicleDetailsVO> arlTempVehicleDetailsVOs = null;
		List<String> arlLogMessages = new ArrayList<String>();
		List<String> arlStrings = null;
		String message = RMDCommonConstants.EMPTY_STRING;
		MassApplyDeleteVO objMassApplyDeleteVo =null;
		VerifyCfgTemplateVO objTemplateVO = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		try {

			/*************************************************
			 * if user selected all then call the getVehicleDetails method by
			 * passing empty object
			 *****************************************************/

			if (null != objApplyCfgTemplateVO.getSearchType()
					&& RMDCommonConstants.ALL
							.equalsIgnoreCase(objApplyCfgTemplateVO
									.getSearchType())) {
				arlTempVehicleDetailsVOs = getVehicleDetails(null,
						objApplyCfgTemplateVO.getCtrlCfgObjId());

				if (null != arlTempVehicleDetailsVOs) {
					arlVehicleDetailsVOs.addAll(arlTempVehicleDetailsVOs);
				} else {
					arlLogMessages
							.add(sdf.format(new Date())
									+ ":: There exists no units under this Controller Configuration : "
									+ objApplyCfgTemplateVO.getCtrlCfgName());
				}

			}
			/*
			 * Else Call the methods based upon Selection
			 */

			else if (null != objApplyCfgTemplateVO.getArlCfgAssetSearchVOs()
					&& !objApplyCfgTemplateVO.getArlCfgAssetSearchVOs()
							.isEmpty()) {
				for (CfgAssetSearchVO objCfgAssetSearchVO : objApplyCfgTemplateVO
						.getArlCfgAssetSearchVOs()) {

					/*
					 * If Upgrade Check Box is Checked then we will call
					 * getVehicleDetailsUpgrade() method.
					 */
					if (objCfgAssetSearchVO.getSearchType().contains(
							RMDCommonConstants.UPGRADE_TO_VERSION)) {

						if (null != objApplyCfgTemplateVO.getCfgTemplateList()
								&& !objApplyCfgTemplateVO.getCfgTemplateList()
										.isEmpty()) {
							objTemplateVO = objApplyCfgTemplateVO
									.getCfgTemplateList().get(0);
						}

						arlTempVehicleDetailsVOs = getVehicleDetailsUpgrade(
								objCfgAssetSearchVO, objTemplateVO,
								objApplyCfgTemplateVO.getCtrlCfgObjId());

					} else {

						arlTempVehicleDetailsVOs = getVehicleDetails(
								objCfgAssetSearchVO,
								objApplyCfgTemplateVO.getCtrlCfgObjId());
					}

					if (null == arlTempVehicleDetailsVOs) {

						if (RMDCommonConstants.RNH
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: Controller Config mismatch for the selected Road Numbers:"
									+ objCfgAssetSearchVO.getAssetNumbers();
						} else if (RMDCommonConstants.FLEET
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units in the fleet for the item Selected:"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getFleet();
						} else if (RMDCommonConstants.MODEL
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units in the model for the item Selected:"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getModel();
						} else if (RMDCommonConstants.FLEET_PLUS_SW_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units for the item Selected :"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getFleet()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getOnboardSWVersion();

						} else if (RMDCommonConstants.MODEL_PLUS_SW_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units for the item Selected :"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getModel()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getOnboardSWVersion();
						} else if (RMDCommonConstants.FLEET_PLUS_UPGRADE_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units for the item Selected :"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getFleet()
									+ RMDCommonConstants.HYPHEN
									+ "V"
									+ objCfgAssetSearchVO.getFromVersion()
									+ RMDCommonConstants.HYPHEN
									+ "V"
									+ objTemplateVO.getVersion();
						} else if (RMDCommonConstants.MODEL_PLUS_UPGRADE_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							message = ":: There exists no units for the item Selected :"
									+ objCfgAssetSearchVO.getCustomer()
									+ RMDCommonConstants.HYPHEN
									+ objCfgAssetSearchVO.getModel()
									+ RMDCommonConstants.HYPHEN
									+ "V"
									+ objCfgAssetSearchVO.getFromVersion()
									+ RMDCommonConstants.HYPHEN
									+ "V"
									+ objTemplateVO.getVersion();
						}
						if (!RMDCommonUtility.isNullOrEmpty(message)) {
							arlLogMessages
									.add(sdf.format(new Date()) + message);
						} else if (RMDCommonConstants.RNH_PLUS_SW_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							for (String assetNumber : objCfgAssetSearchVO
									.getAssetNumbers()) {
								arlLogMessages
										.add(sdf.format(new Date())
												+ " :: The Unit "
												+ objCfgAssetSearchVO
														.getAssetGrpName()
												+ RMDCommonConstants.HYPHEN
												+ assetNumber
												+ " does not have the Software version - "
												+ objCfgAssetSearchVO
														.getOnboardSWVersion());
							}
						} else if (RMDCommonConstants.RNH_PLUS_UPGRADE_VERSION
								.equalsIgnoreCase(objCfgAssetSearchVO
										.getSearchType())) {
							for (String assetNumber : objCfgAssetSearchVO
									.getAssetNumbers()) {
								arlLogMessages
										.add(sdf.format(new Date())
												+ "  :: The Unit  "
												+ objCfgAssetSearchVO
														.getAssetGrpName()
												+ RMDCommonConstants.HYPHEN
												+ assetNumber
												+ " does not have the appropriate template version for an upgrade "
												+ RMDCommonConstants.FORWARD_SLASH_T_COMMA
												+ objTemplateVO.getTemplate()
												+ RMDCommonConstants.EMPTY_STRING
												+ RMDCommonConstants.FORWARD_SLASH_V_COMMA
												+ objCfgAssetSearchVO
														.getFromVersion()

										);
							}
						}

					} else {
						arlVehicleDetailsVOs.addAll(arlTempVehicleDetailsVOs);
					}

				}
			}
			if (null != arlVehicleDetailsVOs && !arlVehicleDetailsVOs.isEmpty()) {

				/******************************************************
				 * Delete Logic goes here
				 * ****************************************************/

				if (objApplyCfgTemplateVO.isDeleteConfig()) {

					objMassApplyDeleteVo = new MassApplyDeleteVO();
					VerifyCfgTemplateVO verifyCfgTemplateVO = objApplyCfgTemplateVO
							.getCfgTemplateList().get(0);
					objMassApplyDeleteVo.setTempObjId(verifyCfgTemplateVO
							.getObjid());
					objMassApplyDeleteVo.setTemplateNo(verifyCfgTemplateVO
							.getTemplate());
					objMassApplyDeleteVo.setTempVer(verifyCfgTemplateVO
							.getVersion());
					objMassApplyDeleteVo.setCfgType(verifyCfgTemplateVO
							.getCfgFile());
					objMassApplyDeleteVo
							.setArlVehicleDetailsVO(arlVehicleDetailsVOs);
					objMassApplyDeleteVo.setUserName(objApplyCfgTemplateVO
							.getUserName());
					objMassApplyDeleteVo.setCtrlCfgName(objApplyCfgTemplateVO
							.getCtrlCfgName());
					arlLogMessages = massApplyDelete(objMassApplyDeleteVo);

				}
				/*************************************************************************************************
				 * Apply Logic goes here for EDP,FFD,FRD based upon Screen
				 * MassApply and Cfg Maintenance Screens.
				 * **********************************************************************************************/
				else {
					arlStrings = processContentsForApply(objApplyCfgTemplateVO,
							arlVehicleDetailsVOs);
					arlLogMessages.addAll(arlStrings);
				}
			}

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPLY_CFG_TEMPLATES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arlTempVehicleDetailsVOs = null;
			arlVehicleDetailsVOs = null;
			objMassApplyDeleteVo=null;		
		}
		return arlLogMessages;
	}

	

	/**
	 * @Author :
	 * @return :List<Strings>
	 * @param : ApplyEFICfgVO objApplyEFICfgVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying EFI Configuration.
	 * 
	 */
	@Override
	public List<String> applyEFIConfig(ApplyEFICfgVO objApplyEFICfgVO)
			throws RMDDAOException {
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		List<String> arlLogMessages = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		try {
			if (objApplyEFICfgVO.isChangeVersion()) {

				arlVehicleDetailsVOs = getVehicleDetailsEFIUpdateVersion(
						objApplyEFICfgVO.getCustomer(),// CustomerName
						objApplyEFICfgVO.getAssetGrpName(),
						objApplyEFICfgVO.getFromAssetNumber(),
						objApplyEFICfgVO.getToAssetNumber(),
						objApplyEFICfgVO.getFromVersion());

				if (null == arlVehicleDetailsVOs) {
					arlLogMessages.add(sdf.format(new Date())
							+ " :: No Units found for RoadNumber Range:"
							+ objApplyEFICfgVO.getFromAssetNumber()
							+ RMDCommonConstants.HYPHEN
							+ objApplyEFICfgVO.getToAssetNumber());
				}
			} else {
				arlVehicleDetailsVOs = getVehicleDetailsEFIFromRn(
						objApplyEFICfgVO.getCustomer(),
						objApplyEFICfgVO.getAssetGrpName(),
						objApplyEFICfgVO.getFromAssetNumber(),
						objApplyEFICfgVO.getToAssetNumber());

				if (null == arlVehicleDetailsVOs) {
					arlLogMessages.add(sdf.format(new Date())
							+ " :: No Units found for RoadNumber Range:"
							+ objApplyEFICfgVO.getFromAssetNumber() + " to "
							+ objApplyEFICfgVO.getToAssetNumber());
				}
			}
			/******************************************************
			 * Apply Logic goes here for EFI
			 * ****************************************************/
			if (null != arlVehicleDetailsVOs && !arlVehicleDetailsVOs.isEmpty()) {
				List<String> arlStrings = processContentsForEFIApply(
						arlVehicleDetailsVOs,
						objApplyEFICfgVO.getObjVerifyCfgTemplateVO(),
						objApplyEFICfgVO.getUserName());
				arlLogMessages.addAll(arlStrings);
				arlStrings = null;
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_APPLY_EFI_CONFIG);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arlVehicleDetailsVOs = null;
		}
		return arlLogMessages;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param :ApplyCfgTemplateVO objApplyCfgTemplateVO,List<VehicleDetailsVO>
	 *        arlVehicleDetailsVOs
	 * @throws SQLException
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for applying the config
	 *               templates to the selected assets.
	 * 
	 */
	@Override
	public List<String> processContentsForApply(
			ApplyCfgTemplateVO objApplyCfgTemplateVO,
			List<VehicleDetailsVO> arlVehicleDetailsVOs)
			throws RMDDAOException, SQLException {
		List<String> logMessages = new ArrayList<String>();
		String userName = objApplyCfgTemplateVO.getUserName();
		String msg = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		String cfgType = null;
		List<Integer> arlEDPCfgDefObjId = null;
		List<Integer> arlVehicleObjId = null;
		List<Integer> arlFFDCfgDefObjId = null;
		List<Integer> arlFRDCfgDefObjId = null;
		List<Integer> arlAHCCfgDefObjId = null;
		List<Integer> arlRCICfgDefObjId = null;
		List<String> arlEDPMsgs = null;
		List<String> arlFFDMsgs = null;
		List<String> arlFRDMsgs = null;
		Future<List<String>> arlAHCMsgs = null;
		Future<List<String>> arlRCIMsgs = null;
		Future<String> edpStatus = null;
		Future<String> frdStatus = null;
		Future<String> ffdStatus = null;
		List<String> cmuCntrlCfgList = null;
		List<String> vehicleObjIdListForCmu = new ArrayList<String>();
		boolean isReApply =RMDCommonConstants.FALSE;

		try {
			arlEDPCfgDefObjId = new ArrayList<Integer>();
			arlFFDCfgDefObjId = new ArrayList<Integer>();
			arlFRDCfgDefObjId = new ArrayList<Integer>();
			arlAHCCfgDefObjId = new ArrayList<Integer>();
			arlRCICfgDefObjId = new ArrayList<Integer>();
			arlVehicleObjId = new ArrayList<Integer>();
			arlEDPMsgs = new ArrayList<String>();
			arlFFDMsgs = new ArrayList<String>();
			arlFRDMsgs = new ArrayList<String>();

			cmuCntrlCfgList = objCmuPassthroughDAOIntf.getCmuCtrlCfgList();
			userName = objApplyCfgTemplateVO.getUserName();
			for (VerifyCfgTemplateVO objVerifyCfgTemplateVO : objApplyCfgTemplateVO
					.getCfgTemplateList()) {
			    
				cfgType = objVerifyCfgTemplateVO.getCfgFile();

				if (RMDCommonConstants.EDP.equalsIgnoreCase(cfgType)) {
					arlEDPCfgDefObjId.add(Integer
							.parseInt(objVerifyCfgTemplateVO.getObjid()));
				} else if (RMDCommonConstants.FFD_CFG_FILE
						.equalsIgnoreCase(cfgType)) {
					arlFFDCfgDefObjId.add(Integer
							.parseInt(objVerifyCfgTemplateVO.getObjid()));

				} else if (RMDCommonConstants.FRD_CFG_FILE
						.equalsIgnoreCase(cfgType)) {
					arlFRDCfgDefObjId.add(Integer
							.parseInt(objVerifyCfgTemplateVO.getObjid()));
				}else if (RMDCommonConstants.AHC
                        .equalsIgnoreCase(cfgType)) {
                    arlAHCCfgDefObjId.add(Integer
                            .parseInt(objVerifyCfgTemplateVO.getObjid()));
                }else if (RMDCommonConstants.RCI_CFG_FILE
                        .equalsIgnoreCase(cfgType)) {
                    arlRCICfgDefObjId.add(Integer
                            .parseInt(objVerifyCfgTemplateVO.getObjid()));
                }

				for (VehicleDetailsVO objVehicleDetailsVO : arlVehicleDetailsVOs) {

					if (!arlFRDCfgDefObjId.isEmpty()
							&& (cmuCntrlCfgList.contains(objApplyCfgTemplateVO
									.getCtrlCfgName()))
							&& !vehicleObjIdListForCmu
									.contains(objVehicleDetailsVO
											.getVehicleObjid())) {
						vehicleObjIdListForCmu.add(objVehicleDetailsVO
								.getVehicleObjid());
					}
				    
					if (!arlVehicleObjId.contains(Integer
							.parseInt(objVehicleDetailsVO.getVehicleObjid()))) {
						arlVehicleObjId.add(RMDCommonUtility
								.convertStringToInt(objVehicleDetailsVO
										.getVehicleObjid()));
					}
					msg = "New MTM Record inserted for Configuration Type "
							+ cfgType + " : vehicle Objid: "
							+ objVehicleDetailsVO.getVehicleObjid()
							+ " vehicle hdr: "
							+ objVehicleDetailsVO.getVehicleHdr()
							+ " vehicle No: "
							+ objVehicleDetailsVO.getVehicleNo()
							+ " action Type :" + cfgType
							+ RMDCommonConstants.UNDERSCORE_APPLY;
					massApplyCfgLogger.debug(msg);
					if (RMDCommonConstants.EDP.equalsIgnoreCase(cfgType)) {
						arlEDPMsgs.add(sdf.format(new Date()) + " :: " + msg);
					} else if (RMDCommonConstants.FFD_CFG_FILE
							.equalsIgnoreCase(cfgType)) {
						arlFFDMsgs.add(sdf.format(new Date()) + " :: " + msg);

					} else if (RMDCommonConstants.FRD_CFG_FILE
							.equalsIgnoreCase(cfgType)) {
						arlFRDMsgs.add(sdf.format(new Date()) + " :: " + msg);
					}

				}
				
				
			}

			if (!arlFFDCfgDefObjId.isEmpty()) {

				ffdStatus = createNewMTMVehRecordForFFD(arlVehicleObjId,
						userName, arlFFDCfgDefObjId);
				if (null != ffdStatus
						&& RMDCommonConstants.FAILURE
								.equalsIgnoreCase(ffdStatus.get())) {
					logMessages.add(sdf.format(new Date())
							+ " :: Failed to apply FFD template(s).");
				} else {
					logMessages.addAll(arlFFDMsgs);
				}

			}

			if (!arlEDPCfgDefObjId.isEmpty()) {

				edpStatus = createNewMTMVehRecordForEdp(arlVehicleObjId,
						userName, arlEDPCfgDefObjId);
				if (null != edpStatus
						&& RMDCommonConstants.FAILURE
								.equalsIgnoreCase(edpStatus.get())) {
					logMessages.add(sdf.format(new Date())
							+ " :: Failed to apply EDP template(s).");
				} else {
					logMessages.addAll(arlEDPMsgs);
				}
			}

			if (!arlFRDCfgDefObjId.isEmpty()) {

				frdStatus = createNewMTMVehRecordForFRD(arlVehicleObjId,
						userName, arlFRDCfgDefObjId, vehicleObjIdListForCmu);
				if (null != frdStatus
						&& RMDCommonConstants.FAILURE
								.equalsIgnoreCase(frdStatus.get())) {
					logMessages.add(sdf.format(new Date())
							+ " :: Failed to apply FRD template(s).");
				} else {
					logMessages.addAll(arlFRDMsgs);
				}
			}
			
			if (!arlAHCCfgDefObjId.isEmpty()) {
			    arlAHCMsgs = createNewMTMVehRecordForAHC(arlVehicleDetailsVOs,
                         arlAHCCfgDefObjId,userName); 
			    
                logMessages.addAll(arlAHCMsgs.get());
            }
			
			if (!arlRCICfgDefObjId.isEmpty()) {
                arlRCIMsgs = associateRCITemplate(arlVehicleDetailsVOs,
                         arlRCICfgDefObjId,userName,isReApply); 
                
                logMessages.addAll(arlRCIMsgs.get());
            }

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PROCESS_CONTENTS_FOR_APPLY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arlEDPCfgDefObjId = null;
			arlFFDCfgDefObjId = null;
			arlVehicleObjId = null;
			arlEDPMsgs = null;
			arlFFDMsgs = null;
			arlFRDMsgs = null;
			cmuCntrlCfgList=null;
		}
		return logMessages;

	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<Integer> vehicleObjId, String userName, List<Integer>
	 *        cfgDefObjId
	 * @throws SQLException
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for creating new MTM Record for
	 *               Config Type EDP/SDP.
	 * 
	 */
	@Override
	public Future<String> createNewMTMVehRecordForEdp(
			List<Integer> vehicleObjId, String userName,
			List<Integer> cfgDefObjId) throws RMDDAOException, SQLException {
		Session session = null;
		Connection conn = null;
		OracleConnection oracleConnection = null;
		ArrayDescriptor vehObjIdArrDescriptor = null;
		ArrayDescriptor cfgDefDescriptor = null;
		ARRAY vehicleObjArr = null;
		ARRAY cfgObjArr = null;
		Integer[] arrIntegerVehObjId = null;
		Integer[] arrIntegerCfgObjId = null;
		CallableStatement callstmt = null;
		String status = RMDCommonConstants.SUCCESS;
		try {
			session = getHibernateSession();
			conn = getConnection(session);
			conn.setAutoCommit(false);
			if (conn.isWrapperFor(OracleConnection.class)) {
				oracleConnection = conn.unwrap(OracleConnection.class);
			}
			massApplyCfgLogger.info("No of Templates for EDP Apply :"
					+ cfgDefObjId.size());
			massApplyCfgLogger.info("No of Assets for EDP Apply :"
					+ vehicleObjId.size());
			massApplyCfgLogger
					.info("Before fetching Array Descriptors for GETS_RMD. VEH_OBJ_ARR,GETS_RMD.CFG_DEF_ARRAY  ");
			vehObjIdArrDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.VEH_OBJ_ARR, conn.getMetaData()
							.getConnection());
			cfgDefDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.CFG_DEF_ARRAY, conn.getMetaData()
							.getConnection());
			arrIntegerVehObjId = vehicleObjId.toArray(new Integer[vehicleObjId
					.size()]);
			arrIntegerCfgObjId = cfgDefObjId.toArray(new Integer[cfgDefObjId
					.size()]);
			int[] arrVehPrimitives = ArrayUtils.toPrimitive(arrIntegerVehObjId);
			int[] arrCfgPrimitives = ArrayUtils.toPrimitive(arrIntegerCfgObjId);

			massApplyCfgLogger
					.info("Before Creating Array of Inputs to procedure ");
			vehicleObjArr = new ARRAY(vehObjIdArrDescriptor, oracleConnection,
					arrVehPrimitives);
			cfgObjArr = new ARRAY(cfgDefDescriptor, oracleConnection,
					arrCfgPrimitives);

			callstmt = conn
					.prepareCall("call GETS_RMD_APPLYTOMULTI_PKG.GETS_RMD_INSERT_MTM_EDP(?,?,?,?,?,?)");
			callstmt.setArray(1, vehicleObjArr);
			callstmt.setArray(2, cfgObjArr);
			callstmt.setString(3, userName);
			callstmt.setString(4, RMDCommonConstants.EDP);
			callstmt.registerOutParameter(5, Types.NUMERIC);
			callstmt.registerOutParameter(6, Types.NUMERIC);
			callstmt.execute();
			int returnCode = callstmt.getInt(6);
			massApplyCfgLogger.info("Return Code :" + returnCode);
			if (1 != returnCode) {
				status = RMDCommonConstants.FAILURE;
			}
			conn.commit();
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			if (null != conn) {
				conn.rollback();
			}
			status = RMDCommonConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arrIntegerVehObjId = null;
			arrIntegerCfgObjId = null;
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				callstmt = null;
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				conn = null;
			}
			oracleConnection = null;
			releaseSession(session);
		}
		return new AsyncResult<String>(status);
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<Integer> vehicleObjId, String userName, List<Integer>
	 *        cfgDefObjId
	 * @throws SQLException
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for creating new MTM Record for
	 *               Config Type EDP/SDP.
	 * 
	 */
	public Future<String> createNewMTMVehRecordForFFD(
			List<Integer> vehicleObjId, String userName,
			List<Integer> cfgDefObjId) throws RMDDAOException, SQLException {
		Session session = null;
		Connection conn = null;
		OracleConnection oracleConnection = null;
		ArrayDescriptor vehObjIdArrDescriptor = null;
		ArrayDescriptor cfgDefDescriptor = null;
		ARRAY vehicleObjArr = null;
		ARRAY cfgObjArr = null;
		Integer[] arrIntegerVehObjId = null;
		Integer[] arrIntegerCfgObjId = null;
		CallableStatement callstmt = null;
		String status = RMDCommonConstants.SUCCESS;
		try {
			session = getHibernateSession();
			conn = getConnection(session);
			conn.setAutoCommit(false);
			if (conn.isWrapperFor(OracleConnection.class)) {
				oracleConnection = conn.unwrap(OracleConnection.class);
			}
			massApplyCfgLogger.info("No of Templates for FFD Apply :"
					+ cfgDefObjId.size());
			massApplyCfgLogger.info("No of Assets for FFD Apply :"
					+ vehicleObjId.size());
			massApplyCfgLogger
					.info("Before fetching Array Descriptors for GETS_RMD.VEH_OBJ_ARR,GETS_RMD.CFG_DEF_ARRAY  ");
			vehObjIdArrDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.VEH_OBJ_ARR, conn.getMetaData()
							.getConnection());
			cfgDefDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.CFG_DEF_ARRAY, conn.getMetaData()
							.getConnection());
			arrIntegerVehObjId = vehicleObjId.toArray(new Integer[vehicleObjId
					.size()]);
			arrIntegerCfgObjId = cfgDefObjId.toArray(new Integer[cfgDefObjId
					.size()]);
			int[] arrVehPrimitives = ArrayUtils.toPrimitive(arrIntegerVehObjId);
			int[] arrCfgPrimitives = ArrayUtils.toPrimitive(arrIntegerCfgObjId);

			massApplyCfgLogger
					.info("Before Creating Array of Inputs to procedure");
			vehicleObjArr = new ARRAY(vehObjIdArrDescriptor, oracleConnection,
					arrVehPrimitives);
			cfgObjArr = new ARRAY(cfgDefDescriptor, oracleConnection,
					arrCfgPrimitives);

			callstmt = conn
					.prepareCall("call GETS_RMD_APPLYTOMULTI_PKG.GETS_RMD_INSERT_MTM_FFD(?,?,?,?,?)");
			callstmt.setArray(1, vehicleObjArr);
			callstmt.setArray(2, cfgObjArr);
			callstmt.setString(3, userName);
			callstmt.setString(4, RMDCommonConstants.FFD_CFG_FILE);
			callstmt.registerOutParameter(5, Types.NUMERIC);
			callstmt.execute();
			int returnCode = callstmt.getInt(5);
			massApplyCfgLogger.info("Return Code :" + returnCode);
			if (1 != returnCode) {
				status = RMDCommonConstants.FAILURE;
			}
			conn.commit();
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			if (null != conn) {
				conn.rollback();
			}
			status = RMDCommonConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arrIntegerVehObjId = null;
			arrIntegerCfgObjId = null;
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				callstmt = null;
			}
			try {
				if (null != callstmt) {
					conn.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				conn = null;
			}
			oracleConnection = null;
			releaseSession(session);
		}
		return new AsyncResult<String>(status);
	}

	/**
	 * @Author :
	 * @return :String
	 * @param : List<Integer> vehicleObjId, String userName, List<Integer>
	 *        cfgDefObjId
	 * @throws SQLException
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for creating new MTM Record for
	 *               Config Type EDP/SDP.
	 * 
	 */
	public Future<String> createNewMTMVehRecordForFRD(
			List<Integer> vehicleObjId, String userName,
			List<Integer> cfgDefObjId, List<String> vehicleObjIdListForCmu) throws RMDDAOException, SQLException {
		Session session = null;
		Connection conn = null;
		OracleConnection oracleConnection = null;
		ArrayDescriptor vehObjIdArrDescriptor = null;
		ArrayDescriptor cfgDefDescriptor = null;
		ARRAY vehicleObjArr = null;
		ARRAY cfgObjArr = null;
		Integer[] arrIntegerVehObjId = null;
		Integer[] arrIntegerCfgObjId = null;
		CallableStatement callstmt = null;
		String status = RMDCommonConstants.SUCCESS;
		try {
			session = getHibernateSession();
			conn = getConnection(session);
			conn.setAutoCommit(false);
			if (conn.isWrapperFor(OracleConnection.class)) {
				oracleConnection = conn.unwrap(OracleConnection.class);
			}
			massApplyCfgLogger.info("No of Templates for FRD Apply :"
					+ cfgDefObjId.size());
			massApplyCfgLogger.info("No of Assets for FRD Apply :"
					+ vehicleObjId.size());
			massApplyCfgLogger
					.info("Before fetching Array Descriptors for GETS_RMD.VEH_OBJ_ARR,GETS_RMD.CFG_DEF_ARRAY  ");
			vehObjIdArrDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.VEH_OBJ_ARR, conn.getMetaData()
							.getConnection());
			cfgDefDescriptor = ArrayDescriptor.createDescriptor(
					RMDCommonConstants.CFG_DEF_ARRAY, conn.getMetaData()
							.getConnection());
			arrIntegerVehObjId = vehicleObjId.toArray(new Integer[vehicleObjId
					.size()]);
			arrIntegerCfgObjId = cfgDefObjId.toArray(new Integer[cfgDefObjId
					.size()]);
			int[] arrVehPrimitives = ArrayUtils.toPrimitive(arrIntegerVehObjId);
			int[] arrCfgPrimitives = ArrayUtils.toPrimitive(arrIntegerCfgObjId);

			massApplyCfgLogger
					.info("Before Creating Array of Inputs to procedure");
			vehicleObjArr = new ARRAY(vehObjIdArrDescriptor, oracleConnection,
					arrVehPrimitives);
			cfgObjArr = new ARRAY(cfgDefDescriptor, oracleConnection,
					arrCfgPrimitives);

			callstmt = conn
					.prepareCall("call GETS_RMD_APPLYTOMULTI_PKG.GETS_RMD_INSERT_MTM_FRD(?,?,?,?,?)");
			callstmt.setArray(1, vehicleObjArr);
			callstmt.setArray(2, cfgObjArr);
			callstmt.setString(3, userName);
			callstmt.setString(4, RMDCommonConstants.FRD_CFG_FILE);
			callstmt.registerOutParameter(5, Types.NUMERIC);
			callstmt.execute();
			int returnCode = callstmt.getInt(5);
			massApplyCfgLogger.info("Return Code :" + returnCode);
			if (1 != returnCode) {
				status = RMDCommonConstants.FAILURE;
			} 
			
			conn.commit();
			/*Added for CMU Pass Through Changes*/
			if (returnCode == 1 && !vehicleObjIdListForCmu.isEmpty()) {
				objCmuPassthroughMessageSender.sendCmuMQMessages(
						vehicleObjIdListForCmu, userName,
						RMDCommonConstants.FRD_CFG_FILE);
			}
			/*End of Changes*/
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			if (null != conn) {
				conn.rollback();
			}
			status = RMDCommonConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			arrIntegerVehObjId = null;
			arrIntegerCfgObjId = null;
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				callstmt = null;
			}
			try {
				if (null != conn) {
					conn.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				conn = null;
			}
			oracleConnection = null;
			releaseSession(session);
		}
		return new AsyncResult<String>(status);
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Templates of Type
	 *               EFI Config.
	 * 
	 */
	@Override
	public Future<VerifyCfgTemplateVO> getEFIConfigTempaltes(String efiObjId)
			throws RMDDAOException {
		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder efiCfgQuery = new StringBuilder();
		VerifyCfgTemplateVO objVerifyCfgTemplateVO = null;
		try {
			objSession = getHibernateSession();
			efiCfgQuery
					.append(" SELECT cast('EFI' as varchar(3)),OBJID, EFI_CFG_TEMPLATE, EFI_CFG_VERSION, ACTIVE_FLAG FROM GETS_OMI_EFI_CFG_DEF WHERE OBJID=:objId ");
			hibernateQuery = objSession.createSQLQuery(efiCfgQuery.toString());
			hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID, efiObjId);
			List<Object[]> arlTemplates = hibernateQuery.list();
			if (null != arlTemplates && !arlTemplates.isEmpty()) {
				Object[] currentTemplate = arlTemplates.get(0);
				objVerifyCfgTemplateVO = new VerifyCfgTemplateVO();
				objVerifyCfgTemplateVO.setCfgFile(RMDCommonUtility
						.convertObjectToString(currentTemplate[0]));
				objVerifyCfgTemplateVO.setObjid(RMDCommonUtility
						.convertObjectToString(currentTemplate[1]));
				objVerifyCfgTemplateVO.setTemplate(RMDCommonUtility
						.convertObjectToString(currentTemplate[2]));
				objVerifyCfgTemplateVO.setVersion(RMDCommonUtility
						.convertObjectToString(currentTemplate[3]));
				objVerifyCfgTemplateVO.setStatus(RMDCommonUtility
						.convertObjectToString(currentTemplate[4]));
			}
			arlTemplates = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EFI_CONFIG_TEMPALTES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return new AsyncResult<VerifyCfgTemplateVO>(objVerifyCfgTemplateVO);
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Applying Cfg Template of
	 *               Type EFI
	 */
	@Override
	public List<String> processContentsForEFIApply(
			List<VehicleDetailsVO> arlVehicleDetailsVOs,
			VerifyCfgTemplateVO objVerifyCfgTemplateVO, String userName)
			throws RMDDAOException {
		List<String> logMessages = new ArrayList<String>();
		List<TemplateInfoVO> arlTemplateInfoVOs = new ArrayList<TemplateInfoVO>();
		boolean isPinpointEnabled = false;
		TemplateInfoVO objTemplateInfoVO = null;
		String msg = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		MessageDefVO objMessageDefVO = null;
		try {
			objMessageDefVO = messageDefinitionDAOIntf.getEFIMsgDefObjid();
			for (VehicleDetailsVO objVehicleDetailsVO : arlVehicleDetailsVOs) {

				isPinpointEnabled = objVehicleDetailsVO.isServicesEnabled();
				isPinpointEnabled = true;
				if (!isPinpointEnabled) {
					msg = "MDSC startup file not available or Unit does not have Pinpoint service enabled Road Number: "
							+ objVehicleDetailsVO.getVehicleNo();
					logMessages.add(msg);
					massApplyCfgLogger.debug(sdf.format(new Date()) + " :: "
							+ msg);
				} else {
					objTemplateInfoVO = new TemplateInfoVO();
					objTemplateInfoVO.setVehicleObjId(objVehicleDetailsVO
							.getVehicleObjid());
					objTemplateInfoVO.setRoadNumber(objVehicleDetailsVO
							.getVehicleNo());
					objTemplateInfoVO.setRoadNumberHeader(objVehicleDetailsVO
							.getVehicleHdr());
					objTemplateInfoVO.setMsgDefObjidApply(String
							.valueOf(objMessageDefVO.getMsgDefObjidApply()));
					objTemplateInfoVO.setTemplate(objVerifyCfgTemplateVO
							.getTemplate());
					objTemplateInfoVO.setVersion(objVerifyCfgTemplateVO
							.getVersion());
					objTemplateInfoVO
							.setCfgTemplateObjid(objVerifyCfgTemplateVO
									.getObjid());
					objTemplateInfoVO.setUserName(userName);
					objTemplateInfoVO.setServices(objVehicleDetailsVO
							.getServices());
					objTemplateInfoVO.setMsgPriority(objMessageDefVO
							.getMessagePriority());
					objTemplateInfoVO.setPartStatus(objVehicleDetailsVO
							.getPartStatus());
					arlTemplateInfoVOs.add(objTemplateInfoVO);
				}
			}
			if (!arlTemplateInfoVOs.isEmpty()) {
				List<String> arlMesssages = createNewMtmVehRecordEFI(arlTemplateInfoVOs);
				logMessages.addAll(arlMesssages);
				arlMesssages = null;
			}

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_PROCESS_CONTENTS_FOR_EFI_APPLY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		}
		return logMessages;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Units For Applying
	 *               EFI Config
	 * 
	 */
	@Override
	public List<VehicleDetailsVO> getVehicleDetailsEFIUpdateVersion(
			String customer, String roadNumberHdr, String roadFrom,
			String roadTo, String version) {
		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder vehicleDetailsQuery = new StringBuilder();
		List<VehicleDetailsVO> arlobjVehicleDetailsVOs = null;
		try {
			objSession = getHibernateSession();
			vehicleDetailsQuery.append("SELECT C.vehicle_objid, ");
			vehicleDetailsQuery.append("       C.vehicle_no, ");
			vehicleDetailsQuery.append("       mbom.config_item, ");
			vehicleDetailsQuery
					.append("       vehcfg.current_version ,GETS_RMD_APPLYTOMULTI_PKG.GETS_SERVICES_FN(C.vehicle_objid) ");
			vehicleDetailsQuery.append("FROM   gets_omi_efi_cfg_mtm_veh A, ");
			vehicleDetailsQuery.append("       gets_omi_efi_cfg_def B, ");
			vehicleDetailsQuery.append("       gets_rmd_cust_rnh_rn_v C, ");
			vehicleDetailsQuery.append("       gets_rmd_vehcfg vehcfg, ");
			vehicleDetailsQuery.append("       gets_rmd_master_bom mbom, ");
			vehicleDetailsQuery.append("       gets_rmd_vehicle veh ");
			vehicleDetailsQuery.append("WHERE  A.status = 'ACTIVE' ");
			vehicleDetailsQuery.append("       AND C.cust_name = :customer ");
			vehicleDetailsQuery.append("       AND C.vehicle_hdr = :rnh ");
			vehicleDetailsQuery.append("       AND mtm2efi_cfg = B.objid ");
			vehicleDetailsQuery
					.append("       AND mtm2vehicle = C.vehicle_objid ");
			vehicleDetailsQuery
					.append("       AND C.vehicle_objid = vehcfg.veh_cfg2vehicle ");
			vehicleDetailsQuery
					.append("       AND vehcfg.vehcfg2master_bom = mbom.objid ");
			vehicleDetailsQuery
					.append("       AND vehcfg.veh_cfg2vehicle = veh.objid ");
			vehicleDetailsQuery
					.append("       AND veh.vehicle2ctl_cfg = mbom.master_bom2ctl_cfg ");
			vehicleDetailsQuery
					.append("       AND C.vehicle_no NOT LIKE '%BAD%' ");
			vehicleDetailsQuery
					.append("       AND Lpad(C.vehicle_no, 30, '0') BETWEEN ");
			vehicleDetailsQuery
					.append("       Lpad(:fromRoadNo, 30, '0') AND Lpad( ");
			vehicleDetailsQuery.append("           :toRoadNo, 30, '0') ");
			vehicleDetailsQuery
					.append("       AND B.efi_cfg_version = :version ");
			vehicleDetailsQuery.append("       AND mbom.bom_status = 'Y' ");
			vehicleDetailsQuery
					.append("       AND mbom.config_item IN ( 'CMU', 'HPEAP', 'LCV', 'LIG', 'WTMC' ) ");
			vehicleDetailsQuery.append("ORDER  BY vehicle_objid");

			hibernateQuery = objSession.createSQLQuery(vehicleDetailsQuery
					.toString());
			hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER, customer);
			hibernateQuery.setParameter(RMDCommonConstants.RNH, roadNumberHdr);
			hibernateQuery.setParameter(RMDCommonConstants.FROM_ROAD_NUMBER,
					roadFrom);
			hibernateQuery.setParameter(RMDCommonConstants.TO_ROAD_NUMBER,
					roadTo);
			hibernateQuery.setParameter(RMDCommonConstants.VERSION, version);
			List<Object[]> arlDetails = hibernateQuery.list();
			arlobjVehicleDetailsVOs = populateEFIConfigDataToVO(arlDetails);
			arlDetails = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_UPDATEVERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return arlobjVehicleDetailsVOs;
	}

	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Units For Applying
	 *               EFI Config.
	 * 
	 */
	@Override
	public List<VehicleDetailsVO> getVehicleDetailsEFIFromRn(String customer,
			String roadNumberHdr, String roadNumberFrom, String roadNumberTo) {
		Session objSession = null;
		Query hibernateQuery = null;
		StringBuilder vehicleDetailsQuery = new StringBuilder();
		List<VehicleDetailsVO> arlobjVehicleDetailsVOs = null;
		try {
			objSession = getHibernateSession();
			vehicleDetailsQuery
					.append(" SELECT V.VEHICLE_OBJID, V.VEHICLE_NO,MBOM.CONFIG_ITEM, ");
			vehicleDetailsQuery
					.append(" VEHCFG.CURRENT_VERSION,GETS_RMD_APPLYTOMULTI_PKG.GETS_SERVICES_FN(V.VEHICLE_OBJID)  ");
			vehicleDetailsQuery
					.append(" FROM GETS_RMD_CUST_RNH_RN_V V,GETS_RMD_MASTER_BOM MBOM, GETS_RMD_VEHICLE VEH, ");
			vehicleDetailsQuery
					.append(" GETS_RMD_VEHCFG VEHCFG WHERE V.VEHICLE_OBJID = VEHCFG.VEH_CFG2VEHICLE  ");
			vehicleDetailsQuery
					.append(" AND VEHCFG.VEHCFG2MASTER_BOM = MBOM.OBJID ");
			vehicleDetailsQuery
					.append(" AND VEHCFG.VEH_CFG2VEHICLE = VEH.OBJID ");
			vehicleDetailsQuery
					.append(" AND VEH.VEHICLE2CTL_CFG = MBOM.MASTER_BOM2CTL_CFG  ");
			vehicleDetailsQuery.append(" AND V.VEHICLE_NO NOT LIKE '%BAD%'  ");
			vehicleDetailsQuery
					.append(" AND V.CUST_NAME =:customer  AND V.VEHICLE_HDR =:rnh   ");
			vehicleDetailsQuery
					.append(" AND LPAD(V.VEHICLE_NO, 30, '0') BETWEEN LPAD(:fromRoadNo, 30, '0') AND LPAD(:toRoadNo, 30, '0') ");
			vehicleDetailsQuery
					.append(" AND MBOM.BOM_STATUS = 'Y' AND MBOM.CONFIG_ITEM IN ( 'CMU', 'HPEAP', 'LCV', 'LIG', 'WTMC' )  ");
			vehicleDetailsQuery
					.append(" ORDER BY LPAD_FUNCTION(V.VEHICLE_NO) ");
			hibernateQuery = objSession.createSQLQuery(vehicleDetailsQuery
					.toString());
			hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER, customer);
			hibernateQuery.setParameter(RMDCommonConstants.RNH, roadNumberHdr);
			hibernateQuery.setParameter(RMDCommonConstants.FROM_ROAD_NUMBER,
					roadNumberFrom);
			hibernateQuery.setParameter(RMDCommonConstants.TO_ROAD_NUMBER,
					roadNumberTo);
			List<Object[]> arlDetails = hibernateQuery.list();
			arlobjVehicleDetailsVOs = populateEFIConfigDataToVO(arlDetails);
			arlDetails = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_DETAILS_EFI_UPDATEVERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return arlobjVehicleDetailsVOs;
	}
	

	/**
	 * @Author :
	 * @return :String
	 * @param :String efiObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Creating a new MTM Vehicle
	 *               Record.
	 * 
	 */
	@Override
	public List<String> createNewMtmVehRecordEFI(
			List<TemplateInfoVO> arlTemplateInfoVOs) throws RMDDAOException {
		Session session = null;
		Connection conn = null;
		OracleConnection oracleConnection = null;
		Object[] templateDetails = null;
		List<String> arlLogMessages = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		int index = 0;
		STRUCT objStruct = null;
		MessageQueuesVO objMessageQueuesVO = null;
		Map<String, Object> resultSetMap = null;
		final List<MessageQueuesVO> arlMessageQueues = new ArrayList<MessageQueuesVO>();
		StructDescriptor requestDescriptor = null;
		StructDescriptor responseDescriptor = null;
		ArrayDescriptor requestArrayDescriptor = null;
		ResultSetMetaData metaData = null;
		STRUCT[] arrStruct = null;
		ARRAY arrTempaltes = null;
		Struct currentRow = null;
		CallableStatement callstmt = null;
		try {
			arlLogMessages = new ArrayList<String>(arlTemplateInfoVOs.size());
			session = getHibernateSession();
			conn = getConnection(session);
			conn.setAutoCommit(false);
			if (conn.isWrapperFor(OracleConnection.class)) {
				oracleConnection = conn.unwrap(OracleConnection.class);
			}

			massApplyCfgLogger.info("No of Records for EFI APPLY :"
					+ arlTemplateInfoVOs.size());
			massApplyCfgLogger
					.info("Before getting Struct Descriptors for GETS_RMD.CFG_TEMPLATE_DTLS, GETS_RMD.CFG_RESPONSE_DTLS");

			requestDescriptor = StructDescriptor.createDescriptor(
					"GETS_RMD.EFI_TEMPLATE_DTLS", oracleConnection);
			responseDescriptor = StructDescriptor.createDescriptor(
					"GETS_RMD.CFG_RESPONSE_DTLS", oracleConnection);
			metaData = responseDescriptor.getMetaData();

			arrStruct = new STRUCT[arlTemplateInfoVOs.size()];
			massApplyCfgLogger.info("Before Creating Array of Structs");
			for (TemplateInfoVO objTemplateInfoVO : arlTemplateInfoVOs) {
				templateDetails = new Object[] {
						objTemplateInfoVO.getVehicleObjId(),
						objTemplateInfoVO.getRoadNumber(),
						objTemplateInfoVO.getRoadNumberHeader(),
						objTemplateInfoVO.getMsgDefObjidApply(),
						objTemplateInfoVO.getTemplate(),
						objTemplateInfoVO.getVersion(),
						objTemplateInfoVO.getUserName(),
						objTemplateInfoVO.getCfgTemplateObjid(),
						objTemplateInfoVO.getServices(),
						objTemplateInfoVO.getMsgPriority(),
						objTemplateInfoVO.getPartStatus() };
				objStruct = new STRUCT(requestDescriptor, oracleConnection,
						templateDetails);
				arrStruct[index] = objStruct;
				index++;
			}
			massApplyCfgLogger
					.info("Before Getting Array Descriptor for GETS_RMD.CFG_TEMPLATE_ARRY");
			requestArrayDescriptor = ArrayDescriptor.createDescriptor(
					"GETS_RMD.EFI_TEMPLATE_ARRY", oracleConnection);
			arrTempaltes = new ARRAY(requestArrayDescriptor, oracleConnection,
					arrStruct);
			callstmt = conn
					.prepareCall("call GETS_RMD_APPLY_EFI_PKG.GETS_RMD_INSERT_EFI_PR(?,?)");
			callstmt.setArray(1, arrTempaltes);
			callstmt.registerOutParameter(2, Types.ARRAY,
					"GETS_RMD.CFG_RESPONSE_ARRY");
			callstmt.execute();
			Object[] data = (Object[]) ((Array) callstmt.getObject(2))
					.getArray();
			massApplyCfgLogger
					.info("After Fetching Response Array from procedure");
			for (Object temp : data) {
				currentRow = (Struct) temp;
				index = 1;
				resultSetMap = new HashMap<String, Object>();
				/*
				 * Mapping data from result set to columns names using Result
				 * Set MetaData
				 */
				for (Object attribute : currentRow.getAttributes()) {
					resultSetMap.put(metaData.getColumnLabel(index), attribute);
					index++;
				}
				/* Checking if Send Message to Mqueues Flag is Y or not */
				if (RMDCommonConstants.LETTER_Y
						.equalsIgnoreCase(RMDCommonUtility
								.convertObjectToString(resultSetMap
										.get("MQUEUEMESSAGEFLAG")))) {
					objMessageQueuesVO = new MessageQueuesVO();
					objMessageQueuesVO.setVehicleObjId(RMDCommonUtility
							.convertObjectToInt(resultSetMap
									.get("VEHICLEOBJID")));
					objMessageQueuesVO.setMessageId(RMDCommonUtility
							.convertObjectToInt(resultSetMap.get("MESSAGEID")));
					objMessageQueuesVO
							.setServices(RMDCommonUtility
									.convertObjectToString(resultSetMap
											.get("SERVICES")));
					objMessageQueuesVO.setMessagePriority(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("MSGPRIORITY")));
					objMessageQueuesVO.setRoadNumber(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("VEHICLENO")));
					objMessageQueuesVO.setVehicleHdrNo(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("VEHICLEHDRNO")));
					objMessageQueuesVO.setRoadNumberHeader(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("VEHICLEHDR")));
					arlMessageQueues.add(objMessageQueuesVO);
					arlLogMessages.add(sdf.format(new Date())
							+ " :: "
							+ RMDCommonUtility
									.convertObjectToString(resultSetMap
											.get("RETURNMESSAGE")));
					massApplyCfgLogger.debug(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("RETURNMESSAGE")));
				} else {
					arlLogMessages.add(sdf.format(new Date())
							+ " :: "
							+ RMDCommonUtility
									.convertObjectToString(resultSetMap
											.get("RETURNMESSAGE")));
					massApplyCfgLogger.debug(RMDCommonUtility
							.convertObjectToString(resultSetMap
									.get("RETURNMESSAGE")));
				}
				resultSetMap = null;
				objMessageQueuesVO = null;
			}
			data = null;
			/* Calling Message Queues using Threads */
			if (null != arlMessageQueues && !arlMessageQueues.isEmpty()) {
				massApplyCfgLogger
						.info("Calling the Thread to send Messages to MQueues");
				new Thread(new Runnable() {
					@Override
					public void run() {
						messageDefinitionDAOIntf
								.sendMessageToQueue(arlMessageQueues);
					}
				}).start();
			}
			conn.commit();
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				massApplyCfgLogger.info("Roll back failed :" + e1);
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CREATE_NEW_MTMVEH_RECORD_FOR_EDP);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				callstmt = null;
			}
			try {
				conn.close();
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				conn = null;
			}
			releaseSession(session);
		}
		return arlLogMessages;
	}

	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param :List<Object[]> arlVehicleDetails
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for mapping result Set data to
	 *               List of VO's
	 */
	@Override
	public List<VehicleDetailsVO> populateDataToVO(
			List<Object[]> arlVehicleDetails) {
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		VehicleDetailsVO objVehicleDetailsVO = null;

		try {
			if (null != arlVehicleDetails && !arlVehicleDetails.isEmpty()) {
				arlVehicleDetailsVOs = new ArrayList<VehicleDetailsVO>(
						arlVehicleDetails.size());
				for (Object[] currentAsset : arlVehicleDetails) {
					objVehicleDetailsVO = new VehicleDetailsVO();
					objVehicleDetailsVO.setVehicleObjid(RMDCommonUtility
							.convertObjectToString(currentAsset[0]));
					objVehicleDetailsVO.setOrgId(RMDCommonUtility
							.convertObjectToString(currentAsset[1]));
					objVehicleDetailsVO.setCustName(RMDCommonUtility
							.convertObjectToString(currentAsset[2]));
					objVehicleDetailsVO.setVehicleNo(RMDCommonUtility
							.convertObjectToString(currentAsset[3]));
					objVehicleDetailsVO.setVehicleHdr(RMDCommonUtility
							.convertObjectToString(currentAsset[4]));
					arlVehicleDetailsVOs.add(objVehicleDetailsVO);
				}
			}
			arlVehicleDetails = null;
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_COPY_DETAILS_TO_VO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			objVehicleDetailsVO = null;
		}
		return arlVehicleDetailsVOs;

	}

	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param :List<Object[]> arlVehicleDetails
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for mapping result Set data to
	 *               List of VO's
	 */
	@Override
	public List<VehicleDetailsVO> populateEFIConfigDataToVO(
			List<Object[]> arlDetails) {
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		VehicleDetailsVO objVehicleDetailsVO = null;
		boolean servicesFlag = false;
		List<String> arlEnabledServices = null;
		List<String> arlAssetServices = null;
		String assetServices = null;
		Map<String, VehicleDetailsVO> objidPartStatusMap = new HashMap<String, VehicleDetailsVO>();
		try {
			arlEnabledServices = messageDefinitionDAOIntf
					.getEnabledServicesEFI();
			if (null != arlDetails && !arlDetails.isEmpty()) {
				arlVehicleDetailsVOs = new ArrayList<VehicleDetailsVO>(
						arlDetails.size());
				for (Object[] currentObject : arlDetails) {
					objVehicleDetailsVO = new VehicleDetailsVO();
					objVehicleDetailsVO.setVehicleObjid(RMDCommonUtility
							.convertObjectToString(currentObject[0]));
					objVehicleDetailsVO.setVehicleNo(RMDCommonUtility
							.convertObjectToString(currentObject[1]));
					assetServices = RMDCommonUtility
							.convertObjectToString(currentObject[4]);
					if (null != assetServices) {
						assetServices = assetServices.replaceAll(
								RMDCommonConstants.REGEX_REMOVE_NULL,
								RMDCommonConstants.EMPTY_STRING);

						arlAssetServices = Arrays.asList(assetServices
								.split(RMDCommonConstants.COMMMA_SEPARATOR));
						for (String service : arlEnabledServices) {
							if (arlAssetServices.contains(service)) {
								servicesFlag = true;
							}
						}
						arlAssetServices = null;
						objVehicleDetailsVO.setServicesEnabled(servicesFlag);
						objVehicleDetailsVO.setServices(assetServices);
					}
					if (!objidPartStatusMap.containsKey(objVehicleDetailsVO
							.getVehicleObjid())
							|| RMDCommonConstants.TO_BE_INSTALLED
									.equalsIgnoreCase(objidPartStatusMap.get(
											objVehicleDetailsVO
													.getVehicleObjid())
											.getPartStatus())) {
						if (RMDCommonConstants.CMU
								.equalsIgnoreCase(RMDCommonUtility
										.convertObjectToString(currentObject[2]))
								&& null != RMDCommonUtility
										.convertObjectToString(currentObject[3])
								&& RMDCommonConstants.ONE_STRING
										.equals(RMDCommonUtility
												.convertObjectToString(currentObject[3]))) {
							objVehicleDetailsVO
									.setPartStatus(RMDCommonConstants.INSTALLED_GOOD);
							objidPartStatusMap.put(
									objVehicleDetailsVO.getVehicleObjid(),
									objVehicleDetailsVO);
							continue;
						} else if (RMDCommonConstants.CMU
								.equalsIgnoreCase(RMDCommonUtility
										.convertObjectToString(currentObject[2]))
								&& null != RMDCommonUtility
										.convertObjectToString(currentObject[3])
								&& RMDCommonConstants.TWO
										.equals(RMDCommonUtility
												.convertObjectToString(currentObject[3]))
								|| RMDCommonConstants.HPEAP
										.equalsIgnoreCase(RMDCommonUtility
												.convertObjectToString(currentObject[2]))
								&& null != RMDCommonUtility
										.convertObjectToString(currentObject[3])
								&& RMDCommonConstants.ONE_STRING
										.equals(RMDCommonUtility
												.convertObjectToString(currentObject[3]))
								|| RMDCommonConstants.LCV
										.equalsIgnoreCase(RMDCommonUtility
												.convertObjectToString(currentObject[2]))
								&& null != RMDCommonUtility
										.convertObjectToString(currentObject[3])
								&& RMDCommonConstants.ONE_STRING
										.equals(RMDCommonUtility
												.convertObjectToString(currentObject[3]))
								|| RMDCommonConstants.LIG
										.equalsIgnoreCase(RMDCommonUtility
												.convertObjectToString(currentObject[2]))
								&& null != RMDCommonUtility
										.convertObjectToString(currentObject[3])
								&& RMDCommonConstants.ONE_STRING
										.equals(RMDCommonUtility
												.convertObjectToString(currentObject[3]))) {
							objVehicleDetailsVO
									.setPartStatus(RMDCommonConstants.QNX_INSTALLED);
							objidPartStatusMap.put(
									objVehicleDetailsVO.getVehicleObjid(),
									objVehicleDetailsVO);
							continue;
						} else {
							objVehicleDetailsVO
									.setPartStatus(RMDCommonConstants.TO_BE_INSTALLED);
							objidPartStatusMap.put(
									objVehicleDetailsVO.getVehicleObjid(),
									objVehicleDetailsVO);
						}
					}
				}
				arlVehicleDetailsVOs.addAll(objidPartStatusMap.values());
			}
			arlDetails = null;
			objidPartStatusMap.clear();
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_COPY_DETAILS_TO_VO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {

			objVehicleDetailsVO = null;
		}
		return arlVehicleDetailsVOs;
	}

	
	
	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param  :CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Vehicle Details for the Below Combinations
	 * 				1.Fleet
	 * 				2.Model	
	 * 				3.RNH
	 * 				4.Fleet + Software Version
	 * 				5.Model + Software Version
	 * 				6.RNH   + Software Version
	 */
	@Override
	public List<VehicleDetailsVO> getVehicleDetails(
			CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId) {

		StringBuilder vehicleQuery = new StringBuilder();
		Session objSession = null;
		Query hQuery = null;
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		List<Object[]> arlVehObjects=null;
		 String assetNoQry=RMDCommonConstants.EMPTY_STRING;
		try {
			
			arlVehObjects=new ArrayList<Object[]>();
			objSession = getHibernateSession();
			vehicleQuery
					.append(" SELECT V.VEHICLE_OBJID,V.ORG_ID,V.CUST_NAME,V.VEHICLE_NO,V.VEHICLE_HDR ");
			vehicleQuery
					.append(" FROM GETS_RMD_CUST_RNH_RN_V V,GETS_RMD_CTL_CFG CFG,GETS_RMD_VEHICLE GRV");
			if (null != objCfgAssetSearchVO && !RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
                    .getOnboardSWVersion())) {
                vehicleQuery
                        .append(" ,GETS_RMD_VEHCFG VEHCFG,GETS_RMD_MASTER_BOM BOM ");
            }
			vehicleQuery
					.append(" WHERE GRV.OBJID = V.VEHICLE_OBJID AND GRV.VEHICLE2CTL_CFG  = CFG.OBJID  AND CFG.OBJID =:ctrlCfgObjId");
			
			if (null != objCfgAssetSearchVO) {
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getOnboardSWVersion())) {
					vehicleQuery
							.append(" AND BOM.OBJID = VEHCFG.VEHCFG2MASTER_BOM AND VEHCFG.VEH_CFG2VEHICLE = GRV.OBJID AND BOM.CONFIG_ITEM = 'ONBOARD SW VERSION' AND VEHCFG.CURRENT_VERSION IN (:swVersion) ");
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getCustomer())) {
					vehicleQuery
							.append(" AND UPPER(V.ORG_ID) = UPPER(:customerId) ");
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getModel())) {
					vehicleQuery
							.append(" AND GRV.VEHICLE2MODEL IN (SELECT OBJID FROM GETS_RMD_MODEL WHERE  UPPER(MODEL_NAME) = UPPER(:model)) ");
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getFleet())) {
					vehicleQuery
							.append(" AND GRV.VEHICLE2FLEET IN (SELECT OBJID FROM GETS_RMD_FLEET WHERE UPPER(FLEET_NUMBER) = UPPER(:fleet)) ");
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getAssetGrpName())) {
					vehicleQuery
							.append(" AND  V.VEHICLE_HDR  IN(:assetGrpName) ");
				}
				if (null != objCfgAssetSearchVO.getAssetNumbers()
						&& !objCfgAssetSearchVO.getAssetNumbers().isEmpty()) {
					/* Logic to handle when more than 1000 assets are selected */
					
					if (objCfgAssetSearchVO.getAssetNumbers().size() < 1000) {
						vehicleQuery
								.append(" AND V.VEHICLE_NO IN (:assetNumber) ");
					} else {

						for (String assetNumber : objCfgAssetSearchVO
								.getAssetNumbers()) {
							assetNoQry += " SELECT '"+ assetNumber + "' FROM DUAL UNION ";
						}
						assetNoQry = assetNoQry.substring(0,
								assetNoQry.lastIndexOf("UNION"));

						vehicleQuery.append(" AND V.VEHICLE_NO IN(" + assetNoQry+ ") ");
					}
				}
			}
			vehicleQuery.append(" ORDER BY LPAD_FUNCTION (V.VEHICLE_NO ) ");

			hQuery = objSession.createSQLQuery(vehicleQuery.toString());
			hQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID, ctrlCfgObjId);
			if (null != objCfgAssetSearchVO) {
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getOnboardSWVersion())) {
					hQuery.setParameter(RMDCommonConstants.SOFTWARE_VERSION,
							objCfgAssetSearchVO.getOnboardSWVersion());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getCustomer())) {
					hQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
							objCfgAssetSearchVO.getCustomer());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getModel())) {
					hQuery.setParameter(RMDCommonConstants.MODEL,
							objCfgAssetSearchVO.getModel());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getFleet())) {
					hQuery.setParameter(RMDCommonConstants.FLEET,
							objCfgAssetSearchVO.getFleet());
				}
				if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
						.getAssetGrpName())) {
					hQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
							objCfgAssetSearchVO.getAssetGrpName());
				}
				if (null != objCfgAssetSearchVO.getAssetNumbers()
						&& !objCfgAssetSearchVO.getAssetNumbers().isEmpty()) {
					
					if (objCfgAssetSearchVO.getAssetNumbers().size() < 1000) {
						hQuery.setParameterList(
								RMDCommonConstants.ASSET_NUMBER,
								objCfgAssetSearchVO.getAssetNumbers());
					} 
				}
			}
			arlVehObjects = hQuery.list();
			arlVehicleDetailsVOs = populateDataToVO(arlVehObjects);
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_DETAILS_FILTER_BY_RNH);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return arlVehicleDetailsVOs;

	}

	/**
	 * @Author :
	 * @return :List<VehicleDetailsVO>
	 * @param  :CfgAssetSearchVO objCfgAssetSearchVO, String ctrlCfgObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Vehicle Details for the Below Combinations
	 * 				1.Fleet + Upgrade Version
	 * 				2.Model + Upgrade Version
	 * 				3.RNH   + Upgrade Version
	 */
	@Override
	public List<VehicleDetailsVO> getVehicleDetailsUpgrade(
			CfgAssetSearchVO objCfgAssetSearchVO,
			VerifyCfgTemplateVO objVerifyCfgTemplateVO, String ctrlCfgObjId) {

		StringBuilder mainQuery = new StringBuilder();
		StringBuilder subQuery = new StringBuilder();
		Session objSession = null;
		Query hibernateQuery = null;
		List<VehicleDetailsVO> arlVehicleDetailsVOs = null;
		long fromVer = 0;
		long verLst = 0;
		try {
			objSession = getHibernateSession();
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
					.getFromVersion())) {
				fromVer = Long.parseLong(objCfgAssetSearchVO.getFromVersion());
			}
			if (fromVer >= 100) {
				verLst = fromVer / 100;
			}

			mainQuery
			.append("SELECT V.VEHICLE_OBJID,V.ORG_ID,V.CUST_NAME,V.VEHICLE_NO,V.VEHICLE_HDR FROM  GETS_RMD_CUST_RNH_RN_V V  WHERE V.VEHICLE_OBJID IN( ");

			if (RMDCommonConstants.EDP_CFG_FILE
					.equalsIgnoreCase(objVerifyCfgTemplateVO.getCfgFile())) {

				subQuery.append(" SELECT OBJID FROM GETS_RMD_VEHICLE WHERE VEHICLE2CTL_CFG =:ctrlCfgObjId AND OBJID IN (SELECT MTM.MTM2VEH FROM GETS_OMI_CFG_DEF_MTM_VEH MTM ");
				subQuery.append(" WHERE UPPER(MTM.STATUS) = 'ACTIVE' AND MTM2CFG_DEF IN (SELECT A.OBJID FROM  GETS_RMD.GETS_OMI_CFG_DEF A ");
				subQuery.append(" WHERE A.CFG_DEF_TEMPLATE =:templateNo AND A.CFG_TYPE =:cfgFileName    ");

				if (verLst == 0) {
					subQuery.append(" AND A.CFG_DEF_VERSION =:fromVersion ))) ");
				} else {
					subQuery.append("AND (A.CFG_DEF_VERSION BETWEEN 1 AND :maxVersion ) and A.CFG_DEF_VERSION != :cfgVersion ))) ");
				}

			}

			else if (RMDCommonConstants.FRD_CFG_FILE
					.equalsIgnoreCase(objVerifyCfgTemplateVO.getCfgFile())) {

				subQuery.append(" SELECT OBJID FROM GETS_RMD_VEHICLE WHERE VEHICLE2CTL_CFG =:ctrlCfgObjId AND OBJID IN (SELECT MTM.MTM2VEHICLE FROM GETS_OMI_FLTRNGDEF_MTM_VEH MTM ");
				subQuery.append(" WHERE UPPER(MTM.STATUS) = 'ACTIVE' AND MTM2FLT_RNG_DEF IN (SELECT A.OBJID FROM  GETS_RMD.GETS_OMI_FLT_RANGE_DEF A  ");
				subQuery.append(" WHERE  A.FLT_RANGE_DEF_TEMPLATE =:templateNo   ");

				if (verLst == 0) {
					subQuery.append(" AND A.FLT_RANGE_DEF_VERSION =:fromVersion ))) ");
				} else {
					subQuery.append(" AND (A.FLT_RANGE_DEF_VERSION BETWEEN 1 AND :maxVersion ) AND A.FLT_RANGE_DEF_VERSION != :cfgVersion )))");
				}

			}

			else if (RMDCommonConstants.FFD_CFG_FILE
					.equalsIgnoreCase(objVerifyCfgTemplateVO.getCfgFile())) {

				subQuery.append(" SELECT OBJID FROM GETS_RMD_VEHICLE WHERE VEHICLE2CTL_CFG = :ctrlCfgObjId AND OBJID IN (SELECT MTM.MTM2VEHICLE FROM GETS_OMI_FLT_FLTR_MTM_VEH MTM ");
				subQuery.append(" WHERE UPPER(MTM.STATUS) = 'ACTIVE' AND MTM2FLT_FILTER_DEF IN (SELECT A.OBJID FROM  GETS_RMD.GETS_OMI_FLT_FILTER_DEF A");
				subQuery.append(" WHERE A.FLT_FILTER_TEMPLATE = :templateNo   ");

				if (verLst == 0) {
					subQuery.append(" AND A.FLT_FILTER_VERSION =:fromVersion ))) ");
				} else {
					subQuery.append(" AND (A.FLT_FILTER_VERSION BETWEEN 1 AND :maxVersion ) AND A.FLT_FILTER_VERSION != :cfgVersion ))) ");
				}
			}

			mainQuery.append(subQuery); // Merging SubQuery to Main Query

			// Adding Filters to Fetch data based upon user selection

			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
					.getCustomer())) {
				mainQuery.append(" AND UPPER(V.ORG_ID) = UPPER(:customerId) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO.getModel())) {
				mainQuery.append(" AND UPPER(V.MODEL_NAME_V) = UPPER(:model) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO.getFleet())) {
				mainQuery
				.append(" AND UPPER(V.FLEET_NUMBER_V) = UPPER(:fleet) ");
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
					.getAssetGrpName())) {
				mainQuery.append(" AND  V.VEHICLE_HDR  IN(:assetGrpName) ");
			}
			if (null != objCfgAssetSearchVO.getAssetNumbers()
					&& !objCfgAssetSearchVO.getAssetNumbers().isEmpty()) {
				mainQuery.append(" AND V.VEHICLE_NO IN (:assetNumber) ");
			}

			mainQuery.append(" ORDER BY LPAD_FUNCTION (V.VEHICLE_NO ) ");

			/* End of Query */

			hibernateQuery = objSession.createSQLQuery(mainQuery.toString());

			hibernateQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);

			hibernateQuery.setParameter(RMDCommonConstants.TEMPLATE_NO,
					objVerifyCfgTemplateVO.getTemplate());

			if (RMDCommonConstants.EDP_CFG_FILE
					.equalsIgnoreCase(objVerifyCfgTemplateVO.getCfgFile())) {
				hibernateQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,
						objVerifyCfgTemplateVO.getCfgFile());
			}

			if (verLst == 0) {
				hibernateQuery.setParameter(RMDCommonConstants.FROM_VERSION,
						objCfgAssetSearchVO.getFromVersion());
			} else {
				hibernateQuery.setParameter(RMDCommonConstants.MAX_VERSION,
						verLst);
				hibernateQuery.setParameter(RMDCommonConstants.CFG_VERSION,
						objVerifyCfgTemplateVO.getVersion());
			}

			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
					.getCustomer())) {
				hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,
						objCfgAssetSearchVO.getCustomer());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO.getModel())) {
				hibernateQuery.setParameter(RMDCommonConstants.MODEL,
						objCfgAssetSearchVO.getModel());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO.getFleet())) {
				hibernateQuery.setParameter(RMDCommonConstants.FLEET,
						objCfgAssetSearchVO.getFleet());
			}
			if (!RMDCommonUtility.isNullOrEmpty(objCfgAssetSearchVO
					.getAssetGrpName())) {
				hibernateQuery.setParameter(RMDCommonConstants.ASSET_GRP_NAME,
						objCfgAssetSearchVO.getAssetGrpName());
			}
			if (null != objCfgAssetSearchVO.getAssetNumbers()
					&& !objCfgAssetSearchVO.getAssetNumbers().isEmpty()) {
				hibernateQuery.setParameterList(
						RMDCommonConstants.ASSET_NUMBER,
						objCfgAssetSearchVO.getAssetNumbers());
			}

			List<Object[]> arrObjects = hibernateQuery.list();
			arlVehicleDetailsVOs = populateDataToVO(arrObjects);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VEHICLE_DETAILS_UPGRADE_FROM_TO);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
							RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return arlVehicleDetailsVOs;
	}

	
	
	/**
	 * @Author :
	 * @return :List<String>
	 * @param :MassApplyDeleteVO
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for Creating a new MTM Vehicle
	 *               Record.
	 * 
	 */
	public List<String> massApplyDelete(MassApplyDeleteVO objMassApplyDeleteVo) throws RMDDAOException {
		Session session = null;
		Connection conn = null;
		OracleConnection oracleConnection = null;
		String result = RMDCommonConstants.EMPTY_STRING;
		Object[] templateDetails = null;
		List<String> arlLogMessages = null;
		List<String> arlVehicleObjId= null;
		List<String> cmuCntrlCfgList = null;
		SimpleDateFormat sdf = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
		int index = 0;
		STRUCT objStruct = null;
		StructDescriptor requestDescriptor = null;
		ArrayDescriptor requestArrayDescriptor = null;
		STRUCT[] arrStruct = null;
		ARRAY arrTempaltes = null;
		Array arr =null;
		Object[] arrObjects=null;
		CallableStatement callstmt = null;
		List<String> arlRciXmllog = null;
		try {
			massApplyCfgLogger
					.info("No of units selected for deleting the template "
							+ objMassApplyDeleteVo.getArlVehicleDetailsVO()
									.size());
			cmuCntrlCfgList=objCmuPassthroughDAOIntf.getCmuCtrlCfgList();
			arlLogMessages = new ArrayList<String>(objMassApplyDeleteVo
					.getArlVehicleDetailsVO().size());
			arlVehicleObjId= new  ArrayList<String>(objMassApplyDeleteVo
					.getArlVehicleDetailsVO().size());
			session = getHibernateSession();
			conn = getConnection(session);
			conn.setAutoCommit(false);
			if (conn.isWrapperFor(OracleConnection.class)) {
				oracleConnection = conn.unwrap(OracleConnection.class);
			}
			massApplyCfgLogger.info("Setting values into arrays of structs");
			requestDescriptor = StructDescriptor.createDescriptor(
					"GETS_RMD.DELETE_CFG_TEMPLATE", oracleConnection);
			arrStruct = new STRUCT[objMassApplyDeleteVo
					.getArlVehicleDetailsVO().size()];
			for (VehicleDetailsVO objTemplateInfoVO : objMassApplyDeleteVo
					.getArlVehicleDetailsVO()) {
				templateDetails = new Object[] {
						objTemplateInfoVO.getVehicleObjid(),
						objTemplateInfoVO.getVehicleNo(),
						objTemplateInfoVO.getVehicleHdr() };
				objStruct = new STRUCT(requestDescriptor, oracleConnection,
						templateDetails);
				arrStruct[index] = objStruct;
				index++;
			}
			requestArrayDescriptor = ArrayDescriptor.createDescriptor(
					"GETS_RMD.DELETECFG_REQUEST_ARRY", oracleConnection);
			arrTempaltes = new ARRAY(requestArrayDescriptor, oracleConnection,
					arrStruct);
			callstmt = conn
					.prepareCall("call GETS_SD_DELETE_CFG_PKG.GETS_RMD__DEL_CFG_PR(?,?,?,?,?,?,?,?,?)");
			callstmt.setString(1, objMassApplyDeleteVo.getCfgType());
			callstmt.setArray(2, arrTempaltes);
			callstmt.setString(3, objMassApplyDeleteVo.getTempObjId());
			callstmt.setString(4, objMassApplyDeleteVo.getTemplateNo());
			callstmt.setString(5, objMassApplyDeleteVo.getTempVer());
			callstmt.registerOutParameter(6, Types.NUMERIC);
			callstmt.registerOutParameter(7, Types.ARRAY,
					RMDCommonConstants.LOG_MESSAGES_ARR);
			callstmt.registerOutParameter(8, Types.ARRAY,
					RMDCommonConstants.VEH_OBJ_ARR);
			callstmt.registerOutParameter(9, Types.ARRAY,
                    RMDCommonConstants.LOG_MESSAGES_ARR);
			massApplyCfgLogger
					.info("Before executing the Mass apply delete procedure "
							+ sdf.format(new Date()));
			callstmt.execute();
			massApplyCfgLogger
					.info("After executing the Mass apply delete procedure "
							+ sdf.format(new Date()));
			int returnCod = callstmt.getInt(6);
			if (returnCod == -1) {
				arlLogMessages.add(RMDCommonConstants.FAILURE);
			} else if (returnCod == 1) {
				arr = callstmt.getArray(7);
				if (arr != null) {
					arrObjects = (Object[]) arr.getArray();
					for (Object msg : arrObjects) {
						arlLogMessages.add(sdf.format(new Date()) + result
								+ msg.toString());
					}
					arrObjects=null;
					arr=null;
				}	
				arr=callstmt.getArray(8);
					if (arr != null) {
						 arrObjects = (Object[]) arr.getArray();
						for (Object vehicleObjId : arrObjects) {
							arlVehicleObjId.add(RMDCommonUtility
									.convertObjectToString(vehicleObjId));
						}
						arrObjects=null;
						arr=null;
					}
					if (RMDCommonConstants.RCI_CFG_FILE.equalsIgnoreCase(objMassApplyDeleteVo.getCfgType())){
					    arr=callstmt.getArray(9);
	                    if (arr != null) {   
	                         arrObjects = (Object[]) arr.getArray();
	                         arlRciXmllog = new ArrayList<String>(arrObjects.length);
	                        for (Object xmlMsg : arrObjects) {
	                            arlRciXmllog.add(RMDCommonUtility
	                                    .convertObjectToString(xmlMsg));
	                        }
	                        arrObjects=null;
	                        arr=null;
	                    }
                    }
				}
			
				/*Added for CMU Pass Through Changes*/
				if (RMDCommonConstants.FRD_CFG_FILE
						.equalsIgnoreCase(objMassApplyDeleteVo.getCfgType())
						&& RMDCommonUtility
								.isCollectionNotEmpty(arlVehicleObjId)
						&& RMDCommonUtility
								.isCollectionNotEmpty(cmuCntrlCfgList)
						&& cmuCntrlCfgList.contains(objMassApplyDeleteVo
								.getCtrlCfgName())) {
					objCmuPassthroughMessageSender.sendCmuMQMessages(
							arlVehicleObjId,
							objMassApplyDeleteVo.getUserName(),
							RMDCommonConstants.FRD_CFG_FILE);
				}
				/*End of Changes*/
			
			
			  //Sending RCI messages to EGA Agent Input MQ
			if(RMDCommonUtility.isCollectionNotEmpty(arlRciXmllog)){
				for (String rciInputMessage : arlRciXmllog) {
					sendRCIMessageToMQ(rciInputMessage,objMassApplyDeleteVo.getUserName());
				}
			}
			conn.commit();
            //End
		} catch (Exception e) {
			massApplyCfgLogger.info(e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				massApplyCfgLogger.info("Roll back failed :" + e1);
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MASS_APPLY_DELETE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
				arlVehicleObjId= null;
				cmuCntrlCfgList = null;
			try {
				if (null != callstmt) {
					callstmt.close();
				}
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				callstmt = null;
			}
			try {
				conn.close();
			} catch (Exception e) {
				massApplyCfgLogger.error(e);
				conn = null;
			}
			releaseSession(session);
		}
		return arlLogMessages;
	}
	
	/**
     * @Author :
     * @return :List<MassApplyCfgVO>
     * @param :ConfigSearchVO objConfigSearchVO
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching AHC config templates
     * 
     */
	/**
     * @Author:
     * @param:
     * @return:List<CtrlCfgVO>
     * @throws:RMDDAOException
     * @Description: This method is used for fetching the all controller configs
     */
    @Override
    public List<MassApplyCfgVO> getAHCConfigs(ConfigSearchVO objConfigSearchVO) throws RMDDAOException {
        Session session = null;
        StringBuilder ahcCfgQry = new StringBuilder();
        List<MassApplyCfgVO>  arlAHCConfigs = null;
        List<Object[]> resultList=null;
        MassApplyCfgVO ahcTempVO = null;
        try {
            session = getHibernateSession();
            if(objConfigSearchVO.isCaseMassApplyCFG()){
                ahcCfgQry
                        .append("SELECT CFG_DEF.OBJID,CFG_DEF.AHC_TEMPLATE,CFG_DEF.AHC_VERSION,CFG_DEF.AHC_DESC,CFG_DEF.STATUS,");
                ahcCfgQry
                        .append("DECODE(UPPER(MTM_VEH.STATUS),'FAILED',' ','CANCELLED',' ','OBSOLETE',' ',NULL,' ',UPPER(MTM_VEH.STATUS))  veh_status,");
                ahcCfgQry
                        .append("DECODE(UPPER(MTM_VEH.STATUS),'FAILED',' ','CANCELLED',' ','OBSOLETE',' ',NULL,' ',TO_CHAR(MTM_VEH.LAST_UPDATED_DATE,'mm/dd/yyyy hh24:mi:ss')) veh_status_date ");
                ahcCfgQry
                        .append("FROM GETS_OMI.GETS_OMI_AUTO_HC_CONFIG MTM_VEH,GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR CFG_DEF WHERE CFG_DEF.AHC_HDR2CTRCFG = :ctrlCfgObjId AND ( UPPER(CFG_DEF.STATUS) = 'COMPLETE' ");
                ahcCfgQry
                        .append("OR   ( UPPER(CFG_DEF.STATUS) = 'OBSOLETE' AND UPPER(MTM_VEH.STATUS) IN ('ACTIVE','PENDING','HOLD'))) ");
                ahcCfgQry
                        .append("AND MTM_VEH.AUTO_HC_CONFIG2VEHICLE(+) = :vehObjId AND MTM_VEH.AUTO_HC_CONFIG2CONFIG_HDR(+) = CFG_DEF.OBJID ORDER BY TO_NUMBER(CFG_DEF.AHC_TEMPLATE), TO_NUMBER(CFG_DEF.AHC_VERSION) ASC");
            }else{
                ahcCfgQry
                        .append("SELECT OBJID,AHC_TEMPLATE,AHC_VERSION,AHC_DESC FROM GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR WHERE ");
                ahcCfgQry
                        .append("AHC_HDR2CTRCFG =:ctrlCfgObjId AND UPPER(STATUS) = 'COMPLETE' ORDER BY  TO_NUMBER(AHC_TEMPLATE), TO_NUMBER(AHC_VERSION) ASC");
            }
            final Query ahcConfigQry = session.createSQLQuery(ahcCfgQry
                    .toString());
            if(objConfigSearchVO.isCaseMassApplyCFG()){
                ahcConfigQry.setParameter(RMDCommonConstants.VEH_OBJ_ID, objConfigSearchVO.getVehicleObjId());
            }
            ahcConfigQry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID, objConfigSearchVO.getCtrlcfgObjId());
            resultList = ahcConfigQry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                arlAHCConfigs = new ArrayList<MassApplyCfgVO>(resultList.size());
                 for(Object[] obj : resultList) {  
                    ahcTempVO = new MassApplyCfgVO();
                    ahcTempVO.setOmiObjid(RMDCommonUtility
                            .convertObjectToString(obj[0]));
                    ahcTempVO.setTemplate(RMDCommonUtility
                            .convertObjectToString(obj[1]));
                    ahcTempVO.setVersion(RMDCommonUtility
                            .convertObjectToString(obj[2]));
                    ahcTempVO.setTitle(RMDCommonUtility
                            .convertObjectToString(obj[3]));
                    if(objConfigSearchVO.isCaseMassApplyCFG()){
                        ahcTempVO.setTempStatus(RMDCommonUtility
                                .convertObjectToString(obj[4]));
                        ahcTempVO.setVehStatus(RMDCommonUtility
                                .convertObjectToString(obj[5]));
                        ahcTempVO.setVehStatusDate(RMDCommonUtility
                                .convertObjectToString(obj[6]));
                    }
                    arlAHCConfigs.add(ahcTempVO);
                    ahcTempVO=null;
                }
                
            }
            
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_AHC_CONFIGS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_AHC_CONFIGS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }

        finally {
            releaseSession(session);
            ahcCfgQry=null;
            resultList = null;
        }
        return arlAHCConfigs;
    }
    
    /**
     * @Author :
     * @return :List<VerifyCfgTemplateVO>
     * @param :List<String> edpObjIdList
     * @throws :RMDDAOException
     * @Description: This method is Responsible for fetching selected
     *               Configurations of type EDP.
     * 
     */
    @Override
    public Future<List<VerifyCfgTemplateVO>> getAHCTemplates(
            List<String> ahcObjIdList) throws RMDDAOException {

        Session objSession = null;
        Query hibernateQuery = null;
        StringBuilder ahcTemplateQuery = new StringBuilder();
        List<VerifyCfgTemplateVO> arlVerifyCfgTemplateVOs = null;
        VerifyCfgTemplateVO objCfgTempaltesVO = null;
        try {
            objSession = getHibernateSession();
            ahcTemplateQuery
                    .append("SELECT cast('AHC' as varchar(3)),CFG.OBJID,CFG.AHC_TEMPLATE,CFG.AHC_VERSION,CFG.AHC_DESC,CFG.STATUS FROM ");
            ahcTemplateQuery
                    .append("GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_HDR CFG WHERE CFG.OBJID IN(:cfgObjId) ");
            hibernateQuery = objSession.createSQLQuery(ahcTemplateQuery
                    .toString());
            hibernateQuery.setParameterList(RMDCommonConstants.CFG_OBJID,
                    ahcObjIdList);
            List<Object[]> arlTemplates = hibernateQuery.list();
            if (null != arlTemplates && !arlTemplates.isEmpty()) {
                arlVerifyCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
                        arlTemplates.size());
                for (Object[] currentTemplate : arlTemplates) {
                    objCfgTempaltesVO = new VerifyCfgTemplateVO();
                    objCfgTempaltesVO.setCfgFile(RMDCommonUtility
                            .convertObjectToString(currentTemplate[0]));
                    objCfgTempaltesVO.setObjid(RMDCommonUtility
                            .convertObjectToString(currentTemplate[1]));
                    objCfgTempaltesVO.setTemplate(RMDCommonUtility
                            .convertObjectToString(currentTemplate[2]));
                    objCfgTempaltesVO.setVersion(RMDCommonUtility
                            .convertObjectToString(currentTemplate[3]));
                    objCfgTempaltesVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(currentTemplate[4])));
                    objCfgTempaltesVO.setStatus(RMDCommonUtility
                            .convertObjectToString(currentTemplate[5]));
                    arlVerifyCfgTemplateVOs.add(objCfgTempaltesVO);
                }
            }
            arlTemplates = null;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_AHC_TEMPLATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(objSession);
        }

        return new AsyncResult<List<VerifyCfgTemplateVO>>(
                arlVerifyCfgTemplateVOs);
    }
    
    /**
     * @Author :
     * @return :String
     * @param : List<Integer> vehicleObjId, String userName, List<Integer>
     *        cfgDefObjId
     * @throws SQLException
     * @throws :RMDDAOException
     * @Description: This method is Responsible for creating new MTM Record for
     *               Config Type AHC.
     * 
     */
    public Future<List<String>> createNewMTMVehRecordForAHC(
            List<VehicleDetailsVO> arlVehicleDetailsVOs,
            List<Integer> cfgDefObjId, String userName)
            throws RMDDAOException, SQLException {
        Session session = null;
        Connection conn = null;
        OracleConnection oracleConnection = null;
        CallableStatement callstmt = null;
        Array arr =null;
        Object[] arrObjects=null;
        Integer[] arrIntegerCfgObjId = null;
        STRUCT objStruct = null;
        StructDescriptor requestDescriptor = null;
        ArrayDescriptor requestArrayDescriptor = null;
        ArrayDescriptor cfgDefDescriptor = null;
        ARRAY cfgObjArr = null;
        STRUCT[] arrStruct = null;
        Object[] templateDetails = null;
        ARRAY arrUnits = null;
        int index = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
        List<String> arlMsgs = new ArrayList<String>();
        try {
            session = getHibernateSession();
            conn = getConnection(session);
            conn.setAutoCommit(false);
            if (conn.isWrapperFor(OracleConnection.class)) {
                oracleConnection = conn.unwrap(OracleConnection.class);
            }
            massApplyCfgLogger.info("No of assets selected : "+arlVehicleDetailsVOs.size());
            massApplyCfgLogger.info("Setting values into arrays of structs");
            requestDescriptor = StructDescriptor.createDescriptor(
                    "GETS_RMD.DELETE_CFG_TEMPLATE", oracleConnection);
            arrStruct = new STRUCT[arlVehicleDetailsVOs.size()];
            for (VehicleDetailsVO objTemplateInfoVO : arlVehicleDetailsVOs) {
                templateDetails = new Object[] {
                        objTemplateInfoVO.getVehicleObjid(),
                        objTemplateInfoVO.getVehicleNo(),
                        objTemplateInfoVO.getVehicleHdr() };
                objStruct = new STRUCT(requestDescriptor, oracleConnection,
                        templateDetails);
                arrStruct[index] = objStruct;
                index++;
            }
            requestArrayDescriptor = ArrayDescriptor.createDescriptor(
                    "GETS_RMD.DELETECFG_REQUEST_ARRY", oracleConnection);
            arrUnits = new ARRAY(requestArrayDescriptor, oracleConnection,
                    arrStruct);
            
            cfgDefDescriptor = ArrayDescriptor.createDescriptor(
                    RMDCommonConstants.CFG_DEF_ARRAY, conn.getMetaData()
                            .getConnection());
            arrIntegerCfgObjId = cfgDefObjId.toArray(new Integer[cfgDefObjId
                                                                 .size()]);
            int[] arrCfgPrimitives = ArrayUtils.toPrimitive(arrIntegerCfgObjId);
            cfgObjArr = new ARRAY(cfgDefDescriptor, oracleConnection,
                    arrCfgPrimitives);
                            
            callstmt = conn
                    .prepareCall("call GETS_RMD_APPLYTOMULTI_PKG.GETS_RMD_INSERT_MTM_AHC(?,?,?,?,?,?)");
            callstmt.setArray(1, arrUnits);
            callstmt.setArray(2, cfgObjArr);
            callstmt.setString(3, userName);
            callstmt.setString(4, RMDCommonConstants.AHC);
            callstmt.registerOutParameter(5, Types.NUMERIC);
            callstmt.registerOutParameter(6, Types.ARRAY,
                    RMDCommonConstants.LOG_MESSAGES_ARR);
            massApplyCfgLogger.info("Before executing the Mass apply AHC procedure "
                    + sdf.format(new Date()));
             callstmt.execute();
            massApplyCfgLogger.info("After executing the Mass apply AHC procedure "
                    + sdf.format(new Date()));
            int returnCode = callstmt.getInt(5);
            massApplyCfgLogger.info("Return Code :" + returnCode);
            if (1 == returnCode) {
                arr = callstmt.getArray(6);
                if (arr != null) {
                    arrObjects = (Object[]) arr.getArray();
                    for (Object msg : arrObjects) {
                        arlMsgs.add(sdf.format(new Date()) +" :: " +msg.toString());
                    }
                    arrObjects=null;
                    arr=null;
                }
                
            }else{
                arlMsgs.add(sdf.format(new Date())
                        + " :: Failed to apply AHC template(s).");
            }
            conn.commit();
        } catch (Exception e) {
            massApplyCfgLogger.info(e);
            if (null != conn) {
                conn.rollback();
            }
            arlMsgs.add(sdf.format(new Date())
                    + " :: Failed to apply AHC template(s) :: "+e);
        } finally {
            try {
                if (null != callstmt) {
                    callstmt.close();
                }
            } catch (Exception e) {
                massApplyCfgLogger.error(e);
                callstmt = null;
            }
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
                massApplyCfgLogger.error(e);
                conn = null;
            }
            oracleConnection = null;
            releaseSession(session);
        }
        return new AsyncResult<List<String>>(arlMsgs);
    }

	@Override
	public List<MassApplyCfgVO> getRCIConfigs(ConfigSearchVO objConfigSearchVO)
			throws RMDDAOException {
		List<MassApplyCfgVO> arlRCItemplateList = null;
		Session objSession = null;
		StringBuilder rciTemplateQuery = new StringBuilder();
		MassApplyCfgVO massApplyCfgVO = null;
		Query hibernateQuery = null;
		try {
			objSession = getHibernateSession();
			if(objConfigSearchVO.isCaseMassApplyCFG()){
			    rciTemplateQuery
                    .append("SELECT cfgdef.objid,cfgdef.cfg_def_template,cfgdef.cfg_def_desc,cfgdef.CFG_DEF_VERSION,cfgdef.status,GOCVS.OFFBOARD_STATUS,GOCVS.ONBOARD_STATUS, ");
			    rciTemplateQuery
                    .append("TO_CHAR(GOCVS.ONBOARD_STATUS_DATE,'mm/dd/yyyy hh24:mi:ss') ONBOARD_STATUS_DATE FROM GETS_OMI_CFG_DEF cfgdef,GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS WHERE cfgdef.RCI_FLT_CODE_ID IS NOT NULL AND ");
			    rciTemplateQuery
                    .append("cfgdef.cfg_def2ctl_cfg =:cfgObjId AND (cfgdef.status = 'COMPLETE' OR (cfgdef.status = 'COMPLETE' AND UPPER(GOCVS.ONBOARD_STATUS) NOT IN ('OBSOLETE') )) ");
			    rciTemplateQuery
                    .append("AND cfgdef.status = 'COMPLETE' AND cfgdef.cfg_type = 'RCI' AND GOCVS.status2vehicle (+) = :vehObjId AND GOCVS.status2cfg_def (+) = cfgdef.objid ");
			    rciTemplateQuery
                    .append("ORDER BY TO_NUMBER(cfgdef.cfg_def_template), TO_NUMBER(cfgdef.cfg_def_version) ASC");
			}else{
    			rciTemplateQuery
    					.append("SELECT CFG.OBJID,CFG.CFG_DEF_TEMPLATE,CFG.CFG_DEF_DESC,CFG.CFG_DEF_VERSION FROM GETS_OMI.GETS_OMI_CFG_DEF CFG ");
    			rciTemplateQuery
    					.append("WHERE CFG.cfg_def2ctl_cfg IN(:cfgObjId) AND CFG.STATUS='COMPLETE' AND CFG.CFG_TYPE = 'RCI' ORDER BY TO_NUMBER(CFG.CFG_DEF_TEMPLATE),TO_NUMBER(CFG.CFG_DEF_VERSION) ");   			
			}
			
			hibernateQuery = objSession.createSQLQuery(rciTemplateQuery
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.CFG_OBJID,
                    objConfigSearchVO.getCtrlcfgObjId());
            if(objConfigSearchVO.isCaseMassApplyCFG()){
                hibernateQuery.setParameter(RMDCommonConstants.VEH_OBJ_ID,
                        objConfigSearchVO.getVehicleObjId());
            }

			List<Object[]> arlTemplates = hibernateQuery.list();
			arlRCItemplateList = new ArrayList<MassApplyCfgVO>();
			for (Object[] currentRecord : arlTemplates) {
				massApplyCfgVO = new MassApplyCfgVO();
				massApplyCfgVO.setOmiObjid(RMDCommonUtility
						.convertObjectToString(currentRecord[0]));
				massApplyCfgVO.setTemplate(RMDCommonUtility
						.convertObjectToString(currentRecord[1]));
				massApplyCfgVO.setTitle(RMDCommonUtility
						.convertObjectToString(currentRecord[2]));
				massApplyCfgVO.setVersion(RMDCommonUtility
						.convertObjectToString(currentRecord[3]));
				if(objConfigSearchVO.isCaseMassApplyCFG()){
				    massApplyCfgVO.setTempStatus(RMDCommonUtility
                        .convertObjectToString(currentRecord[4]));
				    massApplyCfgVO.setOffboardStatus(RMDCommonUtility
	                        .convertObjectToString(currentRecord[5]));
				    massApplyCfgVO.setOnBoardStatus(RMDCommonUtility
	                        .convertObjectToString(currentRecord[6]));
				    massApplyCfgVO.setOnBoardStatusDate(RMDCommonUtility
                            .convertObjectToString(currentRecord[7]));
				}
				arlRCItemplateList.add(massApplyCfgVO);
			}

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RCI_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(objSession);
		}
		return arlRCItemplateList;
	}

    @Override
    public Future<List<VerifyCfgTemplateVO>> getRCITemplates(
            List<String> rciObjIdList) throws RMDDAOException {
        Session objSession = null;
        Query hibernateQuery = null;
        StringBuilder rciTemplateQuery = new StringBuilder();
        List<VerifyCfgTemplateVO> arlVerifyCfgTemplateVOs = null;
        VerifyCfgTemplateVO objCfgTempaltesVO = null;
        try {
            objSession = getHibernateSession();
            rciTemplateQuery
                    .append("SELECT CFG.CFG_TYPE,CFG.OBJID,CFG.CFG_DEF_TEMPLATE,CFG.CFG_DEF_VERSION,CFG.CFG_DEF_DESC,CFG.STATUS,grfc.FAULT_CODE, ");
            rciTemplateQuery
                    .append("CFG.MESSAGE_FILE_NAME,CFG.MESSAGE_FILE_CONTENT FROM GETS_OMI.GETS_OMI_CFG_DEF CFG,gets_rmd_fault_codes grfc WHERE ");
             rciTemplateQuery
                            .append("grfc.objid = CFG.RCI_FLT_CODE_ID AND CFG.OBJID IN(:cfgObjId) AND CFG.CFG_TYPE = 'RCI'");
            hibernateQuery = objSession.createSQLQuery(rciTemplateQuery
                    .toString());
            hibernateQuery.setParameterList(RMDCommonConstants.CFG_OBJID,
                    rciObjIdList);
            List<Object[]> arlTemplates = hibernateQuery.list();
            if (null != arlTemplates && !arlTemplates.isEmpty()) {
                arlVerifyCfgTemplateVOs = new ArrayList<VerifyCfgTemplateVO>(
                        arlTemplates.size());
                for (Object[] currentTemplate : arlTemplates) {
                    objCfgTempaltesVO = new VerifyCfgTemplateVO();
                    objCfgTempaltesVO.setCfgFile(RMDCommonUtility
                            .convertObjectToString(currentTemplate[0]));
                    objCfgTempaltesVO.setObjid(RMDCommonUtility
                            .convertObjectToString(currentTemplate[1]));
                    objCfgTempaltesVO.setTemplate(RMDCommonUtility
                            .convertObjectToString(currentTemplate[2]));
                    objCfgTempaltesVO.setVersion(RMDCommonUtility
                            .convertObjectToString(currentTemplate[3]));
                    objCfgTempaltesVO.setTitle(ESAPI.encoder().encodeForXML(RMDCommonUtility
                            .convertObjectToString(currentTemplate[4])));
                    objCfgTempaltesVO.setStatus(RMDCommonUtility
                            .convertObjectToString(currentTemplate[5]));
                    objCfgTempaltesVO.setFaultCode(RMDCommonUtility
                            .convertObjectToString(currentTemplate[6]));
                    objCfgTempaltesVO.setFileName(RMDCommonUtility
                            .convertObjectToString(currentTemplate[7]));
                    byte[] buff = new byte[1024];
                    Blob blob = (Blob) currentTemplate[8];
                    if(null!=blob){
                        buff = blob.getBytes(1, (int)blob.length());
                        objCfgTempaltesVO.setFileContent(new String(Base64.encode(buff)));
                    }
                    arlVerifyCfgTemplateVOs.add(objCfgTempaltesVO);
                }
            }
            arlTemplates = null;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RCI_TEMPLATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            rciTemplateQuery=null;
            releaseSession(objSession);
        }

        return new AsyncResult<List<VerifyCfgTemplateVO>>(
                arlVerifyCfgTemplateVOs);
    }
    
    /**
     * @Author :
     * @return :String
     * @param : List<Integer> vehicleObjId, String userName, List<Integer>
     *        cfgDefObjId
     * @throws SQLException
     * @throws :RMDDAOException
     * @Description: This method is Responsible for creating new MTM Record for
     *               Config Type AHC.
     * 
     */
    public Future<List<String>> associateRCITemplate(
            List<VehicleDetailsVO> arlVehicleDetailsVOs,
            List<Integer> cfgDefObjId, String userName,boolean isReApply)
            throws RMDDAOException, SQLException {
        Session session = null;
        Connection conn = null;
        OracleConnection oracleConnection = null;
        CallableStatement callstmt = null;
        Array logMsgArr =null;
        Object[] arrObjects=null;
        Integer[] arrIntegerCfgObjId = null;
        STRUCT objStruct = null;
        StructDescriptor requestDescriptor = null;
        ArrayDescriptor requestArrayDescriptor = null;
        ArrayDescriptor cfgDefDescriptor = null;
        ARRAY cfgObjArr = null;
        STRUCT[] arrStruct = null;
        Object[] templateDetails = null;
        ARRAY arrUnits = null;
        int index = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(
                RMDCommonConstants.DateConstants.MMddyyyyHHmmssSSS);
        List<String> arlMsgs = new ArrayList<String>();
        List<String> arlXMLMsgs = new ArrayList<String>();
        try {
            session = getHibernateSession();
            conn = getConnection(session);
            conn.setAutoCommit(false);
            if (conn.isWrapperFor(OracleConnection.class)) {
                oracleConnection = conn.unwrap(OracleConnection.class);
            }
            massApplyCfgLogger.info("No of assets selected : "+arlVehicleDetailsVOs.size());
            massApplyCfgLogger.info("Setting values into arrays of structs");
            requestDescriptor = StructDescriptor.createDescriptor(
                    "GETS_RMD.DELETE_CFG_TEMPLATE", oracleConnection);
            arrStruct = new STRUCT[arlVehicleDetailsVOs.size()];
            for (VehicleDetailsVO objTemplateInfoVO : arlVehicleDetailsVOs) {
                templateDetails = new Object[] {
                        objTemplateInfoVO.getVehicleObjid(),
                        objTemplateInfoVO.getVehicleNo(),
                        objTemplateInfoVO.getVehicleHdr() };
                objStruct = new STRUCT(requestDescriptor, oracleConnection,
                        templateDetails);
                arrStruct[index] = objStruct;
                index++;
            }
            requestArrayDescriptor = ArrayDescriptor.createDescriptor(
                    "GETS_RMD.DELETECFG_REQUEST_ARRY", oracleConnection);
            arrUnits = new ARRAY(requestArrayDescriptor, oracleConnection,
                    arrStruct);
            
            cfgDefDescriptor = ArrayDescriptor.createDescriptor(
                    RMDCommonConstants.CFG_DEF_ARRAY, conn.getMetaData()
                            .getConnection());
            arrIntegerCfgObjId = cfgDefObjId.toArray(new Integer[cfgDefObjId
                                                                 .size()]);
            int[] arrCfgPrimitives = ArrayUtils.toPrimitive(arrIntegerCfgObjId);
            cfgObjArr = new ARRAY(cfgDefDescriptor, oracleConnection,
                    arrCfgPrimitives);
                            
            callstmt = conn
                    .prepareCall("call GETS_RMD_APPLYTOMULTI_PKG.GETS_RMD_INSERT_MTM_RCI(?,?,?,?,?,?,?)");
            callstmt.setArray(1, arrUnits);
            callstmt.setArray(2, cfgObjArr);
            callstmt.setString(3, "2500");
            callstmt.setString(4, userName);
            callstmt.registerOutParameter(5, Types.NUMERIC);
            callstmt.registerOutParameter(6, Types.ARRAY,
                    RMDCommonConstants.LOG_MESSAGES_ARR);
            callstmt.registerOutParameter(7, Types.ARRAY,
                    RMDCommonConstants.LOG_MESSAGES_ARR);
            massApplyCfgLogger.info("Before executing the Mass apply RCI procedure "
                    + sdf.format(new Date()));
             callstmt.execute();
            massApplyCfgLogger.info("After executing the Mass apply RCI procedure "
                    + sdf.format(new Date()));
            int returnCode = callstmt.getInt(5);
            massApplyCfgLogger.info("Return Code :" + returnCode);
            if (1 == returnCode) {
                if(isReApply){
                    arlMsgs.add(RMDCommonConstants.SUCCESS);
                    logMsgArr = callstmt.getArray(7);
                    if (logMsgArr != null) {
                        arrObjects = (Object[]) logMsgArr.getArray();
                        for (Object msg : arrObjects) {
                            arlXMLMsgs.add(msg.toString());
                        }
                        arrObjects=null;
                        logMsgArr=null;
                    }
                }else{
                    logMsgArr = callstmt.getArray(6);
                    if (logMsgArr != null) {
                        arrObjects = (Object[]) logMsgArr.getArray();
                        for (Object msg : arrObjects) {
                            arlMsgs.add(sdf.format(new Date()) +" :: " +msg.toString());
                        }
                        arrObjects=null;
                        logMsgArr=null;
                    }
                    logMsgArr = callstmt.getArray(7);
                    if (logMsgArr != null) {
                        arrObjects = (Object[]) logMsgArr.getArray();
                        for (Object msg : arrObjects) {
                            arlXMLMsgs.add(msg.toString());
                        }
                        arrObjects=null;
                        logMsgArr=null;
                    }
                }
            }else{
                if(isReApply){
                    arlMsgs.add(RMDCommonConstants.FAILURE);
                }else{
                    arlMsgs.add(sdf.format(new Date())
                        + " :: Failed to apply RCI template(s).");
                }
            }
            //Sending RCI messages to EGA Agent Input MQ
            if(RMDCommonUtility.isCollectionNotEmpty(arlXMLMsgs)){
            	  for (String rciInputMessage : arlXMLMsgs) {
      				sendRCIMessageToMQ(rciInputMessage,userName);
      			}
            }
            conn.commit();

          
        } catch (Exception e) {
            massApplyCfgLogger.error(e);
            if (null != conn) {
                conn.rollback();
            }
            arlMsgs.add(sdf.format(new Date())
                    + " :: Failed to apply RCI template(s) :: "+e);
        } finally {
            try {
                if (null != callstmt) {
                    callstmt.close();
                }
            } catch (Exception e) {
                massApplyCfgLogger.error(e);
                callstmt = null;
            }
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (Exception e) {
                massApplyCfgLogger.error(e);
                conn = null;
            }
            oracleConnection = null;
            releaseSession(session);
        }
        return new AsyncResult<List<String>>(arlMsgs);
    }

    /**
     * @Author :
     * @return :String
     * @param : VehicleCfgTemplateVO
     * @throws :RMDDAOException
     * @Description: This method is Responsible to re-apply vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reApplyTemplate(VehicleCfgTemplateVO objCfgTemplateVO)
            throws RMDDAOException {
        String result=null;
        Session objSession = null;
        Query hibernateQuery = null;
        StringBuilder standardQry = new StringBuilder();
        StringBuilder tempStatusQry = new StringBuilder();
        List<VehicleDetailsVO> arlVehicleDetailsVOs=null;
        List<Integer> cfgObjIdList=null;
        VehicleDetailsVO objVehicleDetailsVO= null;
        Future<List<String>> reApplyStatus=null;
        boolean isReAppy= RMDCommonConstants.TRUE;
        try {
            objSession = getHibernateSession();
            standardQry
                    .append("SELECT GOCD.STATUS,NVL(OFFBOARD_STATUS,' ') OFFBOARD_STATUS,GRCV.VEHICLE_OBJID FROM GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS,GETS_RMD_CUST_RNH_RN_V GRCV, ");
            standardQry
                    .append("GETS_OMI_CFG_DEF GOCD,GETS_RMD_FAULT_CODES GRFC WHERE GOCD.OBJID = GOCVS.STATUS2CFG_DEF AND GOCD.RCI_FLT_CODE_ID = GRFC.OBJID ");
            standardQry
                    .append("AND GRCV.VEHICLE_OBJID= STATUS2VEHICLE AND GRCV.ORG_ID= :customerId AND GRCV.VEHICLE_HDR = :vehicleHeader AND ");
            standardQry
                    .append("GRCV.VEHICLE_NO = :roadNumber AND GOCD.CFG_TYPE = :cfgFileName AND GOCVS.STATUS2CFG_DEF = :cfgObjId");
            tempStatusQry.append(standardQry).append(" AND GOCVS.OBJID=:objId");
            hibernateQuery = objSession.createSQLQuery(tempStatusQry
                    .toString());
            hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,objCfgTemplateVO.getCustomerId());
            hibernateQuery.setParameter(RMDCommonConstants.VEHICLE_HEADER,objCfgTemplateVO.getAssetGrpName());
            hibernateQuery.setParameter(RMDCommonConstants.ROAD_NUMBER,objCfgTemplateVO.getAssetNumber());
            hibernateQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,objCfgTemplateVO.getConfigFile());
            hibernateQuery.setParameter(RMDCommonConstants.CFG_OBJID,objCfgTemplateVO.getObjId());
            hibernateQuery.setParameter(RMDCommonConstants.OBJ_ID,objCfgTemplateVO.getVehStatusObjId());
            List<Object[]> arlTemplates = hibernateQuery.list();
            if (null != arlTemplates && !arlTemplates.isEmpty()) {
                Object[] currentTemplate =arlTemplates.get(0);
                
                if(RMDCommonConstants.FAILED_INSTALLATION.equalsIgnoreCase(RMDCommonUtility
                        .convertObjectToString(currentTemplate[1])) && RMDCommonConstants.COMPLETE.equalsIgnoreCase(RMDCommonUtility
                                .convertObjectToString(currentTemplate[0]))){
                    tempStatusQry = new StringBuilder();
                    tempStatusQry.append(standardQry).append(" AND (GOCVS.OFFBOARD_STATUS IN ('PENDING REAPPLY') OR GOCVS.OFFBOARD_STATUS IS NULL)");
                    hibernateQuery = objSession.createSQLQuery(tempStatusQry
                            .toString());
                    hibernateQuery.setParameter(RMDCommonConstants.CUSTOMER_ID,objCfgTemplateVO.getCustomerId());
                    hibernateQuery.setParameter(RMDCommonConstants.VEHICLE_HEADER,objCfgTemplateVO.getAssetGrpName());
                    hibernateQuery.setParameter(RMDCommonConstants.ROAD_NUMBER,objCfgTemplateVO.getAssetNumber());
                    hibernateQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,objCfgTemplateVO.getConfigFile());
                    hibernateQuery.setParameter(RMDCommonConstants.CFG_OBJID,objCfgTemplateVO.getObjId());
                    List<Object[]> resulList = hibernateQuery.list();
                    if(RMDCommonUtility.isCollectionNotEmpty(resulList))
                        result=RMDCommonConstants.PENDING_REAPPLY;
                    else{
                        objVehicleDetailsVO = new VehicleDetailsVO();
                        arlVehicleDetailsVOs = new ArrayList<VehicleDetailsVO>();
                        cfgObjIdList = new ArrayList<Integer>();
                        cfgObjIdList.add(Integer.parseInt(objCfgTemplateVO.getObjId()));
                        objVehicleDetailsVO.setVehicleHdr(objCfgTemplateVO.getAssetGrpName());
                        objVehicleDetailsVO.setVehicleNo(objCfgTemplateVO.getAssetNumber());
                        objVehicleDetailsVO.setVehicleObjid(RMDCommonUtility
                                .convertObjectToString(currentTemplate[2]));
                        arlVehicleDetailsVOs.add(objVehicleDetailsVO);
                        reApplyStatus = associateRCITemplate(arlVehicleDetailsVOs, cfgObjIdList, objCfgTemplateVO.getUserName(),isReAppy);
                        result=reApplyStatus.get().get(0);
                    }
                        
                }else{
                    if(!RMDCommonConstants.FAILED_INSTALLATION.equalsIgnoreCase(RMDCommonUtility
                            .convertObjectToString(currentTemplate[1]))){
                        result=RMDCommonConstants.NOT_FAILED_INSTALLATION;
                    }else if(!RMDCommonConstants.COMPLETE.equalsIgnoreCase(RMDCommonUtility
                            .convertObjectToString(currentTemplate[0]))){
                        result=RMDCommonConstants.OBSOLETE;
                    }
                }
            }else{
                result=RMDCommonConstants.FAILURE;
            }
            arlTemplates = null;
        } catch (Exception e) {
            result=RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RCI_TEMPLATES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            standardQry=null;
            tempStatusQry=null;
            arlVehicleDetailsVOs=null;
            cfgObjIdList=null;
            objVehicleDetailsVO= null;
            reApplyStatus=null;
            releaseSession(objSession);
        }
        
        return result;

    }
    /**
     * @Author :
     * @return :
     * @param : String input, String userName
     * @throws :RMDDAOException
     * @Description:This method is used to send RCI messages (Apply,ReApply activities) to TRANS.EOA.EGA.ADMIN.ALIAS
     * 
     */
    private void sendRCIMessageToMQ(String input, String userName)throws RMDDAOException {
    	RCIMessageRequestVO rciMessageRequestVO=null;
    	String xmlOutput=null;
    	try {
    		massApplyCfgLogger.info("Start - Sending RCI message to EGA Agent");
    		 rciMessageRequestVO=ConfigMaintenanceUtility.transformStringToRCIObject(input,userName);
    		 xmlOutput=ConfigMaintenanceUtility.generateRCIXMLOutput(rciMessageRequestVO);
    		 mqSender.sendMessageToRCIMQ(xmlOutput);
    		 massApplyCfgLogger.info("End - Sent RCI message to EGA Agent");
		} catch (Exception e) {
			massApplyCfgLogger.error(
	                    "Exception while passing the message to MQ "
	                            + e.getMessage(), e);
	            throw new RMDDAOException(
	                    RMDCommonConstants.PASS_TO_QUEUE_EXCEPTION, e.getMessage());
		}
    	
    }

    /**
     * @Author :
     * @return :String
     * @param : String vehStausObjId,String userId
     * @throws :RMDDAOException
     * @Description: This method is Responsible to re-notify vehicle
     *               Configuration Template.
     * 
     */
    @Override
    public String reNotifyTemplateAssociation(String vehStausObjId,
            String userId) throws RMDDAOException {
        String result=RMDCommonConstants.EMPTY_STRING;
        Session objSession = null;
        Query reNotifyHQry = null;
        StringBuilder reNotifyQry = null;
        boolean isReNotifyAllowed = RMDCommonConstants.FALSE;
        StringBuilder xmlMsg = null;
        String action = RMDCommonConstants.EMPTY_STRING;
        try {
            objSession = getHibernateSession();
            reNotifyQry = new StringBuilder();
            reNotifyQry.append("SELECT GOCVS.STATUS2CFG_DEF,GOCVS.STATUS2VEHICLE,NVL(GOCVS.OFFBOARD_STATUS,' ')OFFBOARD_STATUS,NVL(GOCVS.ONBOARD_STATUS,' ')ONBOARD_STATUS, ");
            reNotifyQry.append("COMM_ID, grfc.fault_code, GOCD.CFG_DEF_TEMPLATE, GOCD.CFG_DEF_VERSION,GRCRRV.ORG_ID, GRCRRV.VEHICLE_HDR, GRCRRV.VEHICLE_NO, GOCVS.OB_MSG_ID ");
            reNotifyQry.append("FROM GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS,GETS_OMI_EGA_CFG GOEC, GETS_OMI_CFG_DEF GOCD,GETS_RMD_CUST_RNH_RN_V GRCRRV,gets_rmd_fault_codes grfc ");
            reNotifyQry.append("WHERE GOCVS.STATUS2VEHICLE=GOEC.EGA_CFG2VEHICLE(+) AND GOCD.OBJID=GOCVS.STATUS2CFG_DEF AND VEHICLE_OBJID=GOCVS.STATUS2VEHICLE AND grfc.objid= GOCD.RCI_FLT_CODE_ID AND GOCVS.OBJID=:objId");
            reNotifyHQry = objSession.createSQLQuery(reNotifyQry.toString());
            reNotifyHQry.setParameter(RMDCommonConstants.OBJ_ID, vehStausObjId);
            List<Object[]> arlTemplateDetails = reNotifyHQry.list();
            if (null != arlTemplateDetails && !arlTemplateDetails.isEmpty()) {
                Object[] currentTemplate =arlTemplateDetails.get(0);
                String offBoardStatus = RMDCommonUtility.convertObjectToString(currentTemplate[2]);
                String onBoardStatus = RMDCommonUtility.convertObjectToString(currentTemplate[3]);
                
                if((RMDCommonConstants.INSTALLED.equalsIgnoreCase(onBoardStatus) || RMDCommonConstants.OBSOLETE.equalsIgnoreCase(onBoardStatus)) && RMDCommonConstants.BLANK_SPACE.equals(offBoardStatus)){
                    result =  RMDCommonConstants.RENOTIFY_NOT_NEEDED;
                }else if(RMDCommonConstants.UPGRADED.equalsIgnoreCase(offBoardStatus) || RMDCommonConstants.FAILED_DELETION.equalsIgnoreCase(offBoardStatus) 
                        || RMDCommonConstants.FAILED_INSTALLATION.equalsIgnoreCase(offBoardStatus)){
                    result =  RMDCommonConstants.RENOTIFY_NOT_NEEDED;;
                }else if(RMDCommonConstants.PENDING_DELETION.equalsIgnoreCase(offBoardStatus) && RMDCommonConstants.INSTALLED.equalsIgnoreCase(onBoardStatus)){
                    isReNotifyAllowed = RMDCommonConstants.TRUE;
                    action= RMDCommonConstants.DELETE;
                }else if((RMDCommonConstants.PENDING_INSTALLATION.equalsIgnoreCase(offBoardStatus) || RMDCommonConstants.PENDING_REAPPLY.equalsIgnoreCase(offBoardStatus)) && RMDCommonConstants.NOT_INSTALLED.equalsIgnoreCase(onBoardStatus)){
                    isReNotifyAllowed = RMDCommonConstants.TRUE;
                    action= RMDCommonConstants.APPLY_UPPER;
                }else{
                    result =  RMDCommonConstants.RENOTIFY_NOT_NEEDED;
                }
                if(isReNotifyAllowed){
                    xmlMsg = new StringBuilder();
                    xmlMsg.append(RMDCommonUtility.convertObjectToString(currentTemplate[11])+RMDCommonConstants.PIPELINE_CHARACTER+RMDCommonUtility.convertObjectToString(currentTemplate[4])+RMDCommonConstants.PIPELINE_CHARACTER);
                    xmlMsg.append(RMDCommonUtility.convertObjectToString(currentTemplate[8])+RMDCommonConstants.PIPELINE_CHARACTER+RMDCommonUtility.convertObjectToString(currentTemplate[9])+RMDCommonConstants.PIPELINE_CHARACTER);
                    xmlMsg.append(RMDCommonUtility.convertObjectToString(currentTemplate[10])+RMDCommonConstants.PIPELINE_CHARACTER+RMDCommonUtility.convertObjectToString(currentTemplate[5])+RMDCommonConstants.PIPELINE_CHARACTER);
                    xmlMsg.append(RMDCommonUtility.convertObjectToString(currentTemplate[6])+RMDCommonConstants.PIPELINE_CHARACTER+RMDCommonUtility.convertObjectToString(currentTemplate[7])+RMDCommonConstants.PIPELINE_CHARACTER+action);
                    sendRCIMessageToMQ(xmlMsg.toString(),userId);
                    result=RMDCommonConstants.SUCCESS;
                }
            }
        }catch (Exception e) {
            result=RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RE_NOTIFY_ASSOCIATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            reNotifyQry=null;
            reNotifyHQry=null;
            releaseSession(objSession);
        }
            
        return result;
    }

    /**
     * @Author :
     * @return :String
     * @param : String tempObjId, String templateNo,String versionNo, String userId,String status
     * @throws :RMDDAOException
     * @Description: This method is Responsible to update the RCI Template
     * 
     */
    @Override
    public String updateRCITemplate(String tempObjId,String templateNo,String versionNo, String userId,String status)
            throws RMDDAOException {
        String result=RMDCommonConstants.SUCCESS;
        Session objSession = null;
        Transaction transaction=null;
        Query deleteRCIHQry = null;
        StringBuilder deleteRCIQry = null;
        StringBuilder updateOutBndQry = null;
        Query updateOutHBndQry = null;
        StringBuilder vehDetailsQry = null;
        Query vehDetailsHQry = null;
        List<Object[]> resultList=null;
        VehicleDetailsVO objVehicleDetailsVO=null;
        List<VehicleDetailsVO> arListVehList=null;
        MassApplyDeleteVO objMassApplyDeleteVO=null;
        try{
            deleteRCIQry = new StringBuilder();
            updateOutBndQry = new StringBuilder();
            objSession = getHibernateSession();
            transaction = objSession.getTransaction();
            transaction.begin();
            if(RMDCommonConstants.OBSOLETE.equalsIgnoreCase(status)){
                deleteRCIQry.append("UPDATE GETS_OMI_CFG_DEF SET STATUS='OBSOLETE',LAST_UPDATED_BY=:userName,LAST_UPDATED_DATE=SYSDATE WHERE OBJID=:tempObjId");
                deleteRCIHQry= objSession.createSQLQuery(deleteRCIQry.toString());
                deleteRCIHQry.setParameter(RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
                deleteRCIHQry.setParameter(RMDCommonConstants.USER_NAME, userId);
                deleteRCIHQry.executeUpdate();
                updateOutBndQry.append("UPDATE GETS_OMI.GETS_OMI_OUTBNDMSG_HIST SET OB_REQ_STATUS  = 'CANCELLED',LAST_UPDATED_BY  ='RCI_UI',LAST_UPDATED_DATE=SYSDATE WHERE ");
                updateOutBndQry.append("OB_MSG_ID In (SELECT OB_MSG_ID FROM GETS_OMI.GETS_OMI_CFG_VEH_STATUS WHERE status2cfg_def = :tempObjId AND ((ONBOARD_STATUS = 'INSTALLED' AND ");
                updateOutBndQry.append("OFFBOARD_STATUS IS NULL) OR OFFBOARD_STATUS ='PENDING INSTALLATION' OR OFFBOARD_STATUS ='PENDING REAPPLY' OR OFFBOARD_STATUS   = 'FAILED DELETION')) ");
                updateOutBndQry.append("AND OB_MSG_HIST2MSG_DEF IN (SELECT OBJID FROM GETS_OMI_MESSAGE_DEF WHERE MSG_NUM_CODE IN ('RCI_DEL','RCI_APP')) AND OB_REQ_STATUS='OPEN'");
                updateOutHBndQry= objSession.createSQLQuery(updateOutBndQry.toString());
                updateOutHBndQry.setParameter(RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
                updateOutHBndQry.executeUpdate();
                vehDetailsQry = new StringBuilder();
                vehDetailsQry.append("SELECT GRCRRV.VEHICLE_HDR,GRCRRV.VEHICLE_NO,GRCRRV.VEHICLE_OBJID FROM GETS_OMI.GETS_OMI_CFG_VEH_STATUS GOCVS,GETS_OMI_EGA_CFG GOEC,");
                vehDetailsQry.append("GETS_OMI_CFG_DEF GOCD,GETS_RMD_CUST_RNH_RN_V GRCRRV,gets_rmd_fault_codes grfc WHERE GOCVS.STATUS2VEHICLE=GOEC.EGA_CFG2VEHICLE(+) AND ");
                vehDetailsQry.append("GOCD.OBJID  = GOCVS.STATUS2CFG_DEF AND GRCRRV.VEHICLE_OBJID  = GOCVS.STATUS2VEHICLE AND grfc.objid = GOCD.RCI_FLT_CODE_ID AND ");
                vehDetailsQry.append("((GOCVS.ONBOARD_STATUS = 'INSTALLED' AND GOCVS.OFFBOARD_STATUS IS NULL) OR GOCVS.OFFBOARD_STATUS = 'PENDING INSTALLATION'  OR  ");
                vehDetailsQry.append("GOCVS.OFFBOARD_STATUS = 'PENDING REAPPLY' OR GOCVS.OFFBOARD_STATUS  = 'FAILED DELETION') AND GOCVS.status2cfg_def = :tempObjId");
                vehDetailsHQry= objSession.createSQLQuery(vehDetailsQry.toString());
                vehDetailsHQry.setParameter(RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
                resultList=vehDetailsHQry.list();
                if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
                    arListVehList = new ArrayList<VehicleDetailsVO>(resultList.size());
                    objMassApplyDeleteVO = new MassApplyDeleteVO();
                    for(Object[] obj : resultList){
                        objVehicleDetailsVO = new VehicleDetailsVO();
                        objVehicleDetailsVO.setVehicleHdr(RMDCommonUtility.convertObjectToString(obj[0]));
                        objVehicleDetailsVO.setVehicleNo(RMDCommonUtility.convertObjectToString(obj[1]));
                        objVehicleDetailsVO.setVehicleObjid(RMDCommonUtility.convertObjectToString(obj[2]));
                        arListVehList.add(objVehicleDetailsVO);
                    }
                    objMassApplyDeleteVO.setArlVehicleDetailsVO(arListVehList);
                    objMassApplyDeleteVO.setCfgType(RMDCommonConstants.RCI_CFG_FILE);
                    objMassApplyDeleteVO.setTemplateNo(templateNo);
                    objMassApplyDeleteVO.setTempVer(versionNo);
                    objMassApplyDeleteVO.setTempObjId(tempObjId);
                    objMassApplyDeleteVO.setUserName(userId);
                    List<String> arlDelResult=massApplyDelete(objMassApplyDeleteVO);
                    if(RMDCommonUtility.isCollectionNotEmpty(arlDelResult) && arlDelResult.get(0)==RMDCommonConstants.FAILURE){
                        result=RMDCommonConstants.FAILURE;
                        transaction.rollback();
                    }else{
                        transaction.commit();
                    }
                }else{
                    transaction.commit();
                }
            }else{
                deleteRCIQry.append("UPDATE GETS_OMI_CFG_DEF SET STATUS=:status,LAST_UPDATED_BY=:userName,LAST_UPDATED_DATE=SYSDATE WHERE OBJID=:tempObjId");
                deleteRCIHQry= objSession.createSQLQuery(deleteRCIQry.toString());
                deleteRCIHQry.setParameter(RMDCommonConstants.STATUS, status);
                deleteRCIHQry.setParameter(RMDCommonConstants.USER_NAME, userId);
                deleteRCIHQry.setParameter(RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
                deleteRCIHQry.executeUpdate();
                transaction.commit();
            }
        }catch (Exception e) {
            result=RMDCommonConstants.FAILURE;
            if(null!=transaction){
                transaction.rollback();
            }   
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_RCI_TEMPLATE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        }finally{
            releaseSession(objSession);
            if(null!=transaction){
                transaction=null;
            }
            deleteRCIHQry = null;
            deleteRCIQry = null;
            updateOutBndQry = null;
            updateOutHBndQry = null;
            vehDetailsQry = null;
            vehDetailsHQry = null;
            resultList=null;
            objVehicleDetailsVO=null;
            arListVehList=null;
            objMassApplyDeleteVO=null;
            
        }
        return result;
    }
	
}
