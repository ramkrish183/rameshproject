package com.ge.trans.eoa.services.asset.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.ge.trans.eoa.services.asset.dao.intf.ConfigMaintenanceDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.AddEditEDPDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.ConfigTemplateDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.CtrlCfgVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPHeaderDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPParamDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EDPSearchParamVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.EFIDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultFilterDefVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultRangeDefVo;
import com.ge.trans.eoa.services.asset.service.valueobjects.FaultValueVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.TemplateSearchVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class ConfigMaintenanceDAOImpl extends BaseDAO implements
		ConfigMaintenanceDAOIntf {

	public static final RMDLogger LOG = RMDLogger
			.getLogger(ConfigMaintenanceDAOImpl.class);

	JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CtrlCfgVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the all controller configs
	 */
	@Override
	public List<CtrlCfgVO> getControllerConfigs() throws RMDDAOException {
		Session session = null;
		StringBuilder ctrlCfgQuery = new StringBuilder();
		List<CtrlCfgVO> ctrlCfgVOList = new ArrayList<CtrlCfgVO>();
		List<Object> resultList = null;
		CtrlCfgVO ctrlCfgVO = null;
		try {
			session = getHibernateSession();
			ctrlCfgQuery
					.append("SELECT DISTINCT CFG.OBJID,CONTROLLER_CFG FROM GETS_RMD_CTL_CFG CFG , GETS_RMD_CTL_CFG_SRC WHERE ");
			ctrlCfgQuery
					.append("CTL_CFG_SRC2CTL_CFG = CFG.OBJID ORDER BY CONTROLLER_CFG");
			final Query ctrlCfgHQuery = session.createSQLQuery(ctrlCfgQuery
					.toString());
			resultList = ctrlCfgHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				ctrlCfgVOList = new ArrayList<CtrlCfgVO>(resultList.size());
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] ctrlCfgDetails = (Object[]) obj.next();
					ctrlCfgVO = new CtrlCfgVO();
					ctrlCfgVO.setCtrlCfgObjId((RMDCommonUtility
							.convertObjectToString(ctrlCfgDetails[0])));
					ctrlCfgVO.setCtrlCfgName((RMDCommonUtility
							.convertObjectToString(ctrlCfgDetails[1])));
					ctrlCfgVOList.add(ctrlCfgVO);
					ctrlCfgVO = null;
				}

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLER_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLER_CONFIGS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			ctrlCfgQuery = null;
			resultList = null;
		}
		return ctrlCfgVOList;
	}

	/**
	 * @Author:
	 * @param:ctrlCfgObjId,cfgFileName
	 * @return:List<ConfigTemplateDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used to get the Templates for the selected
	 *               Controller Config and Config File.
	 */
	@Override
	public List<ConfigTemplateDetailsVO> getCtrlCfgTemplates(
			String ctrlCfgObjId, String cfgFileName) throws RMDDAOException {
		Session session = null;
		StringBuilder cfgTempQuery = new StringBuilder();
		List<ConfigTemplateDetailsVO> cfgTempVOList = new ArrayList<ConfigTemplateDetailsVO>();
		ConfigTemplateDetailsVO cfgTempVO = null;
		List<Object> resultList = null;
		String tempStatus = null;
		try {
			session = getHibernateSession();
			if (null != cfgFileName
					&& RMDCommonConstants.FFD_CFG_FILE
							.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("SELECT OBJID,FLT_FILTER_TEMPLATE,FLT_FILTER_VERSION,FLT_FILTER_DESC,STATUS  FROM GETS_OMI_FLT_FILTER_DEF ");
				cfgTempQuery
						.append("WHERE FLT_FILTER_DEF2CTL_CFG =:ctrlCfgObjId ORDER BY DECODE(STATUS,'Complete',1,'WIP',2,'Obsolete',3,4),");
				cfgTempQuery
						.append("TO_NUMBER(FLT_FILTER_TEMPLATE),TO_NUMBER(FLT_FILTER_VERSION) ASC");
			} else if (null != cfgFileName
					&& RMDCommonConstants.FRD_CFG_FILE
							.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("SELECT OBJID,FLT_RANGE_DEF_TEMPLATE,FLT_RANGE_DEF_VERSION,FLT_RANGE_DEF_DESC,STATUS  FROM GETS_OMI_FLT_RANGE_DEF ");
				cfgTempQuery
						.append("WHERE FLT_RANGE_DEF2CTL_CFG =:ctrlCfgObjId ORDER BY DECODE(STATUS,'Complete',1,'WIP',2,'Obsolete',3,4),");
				cfgTempQuery
						.append("TO_NUMBER(FLT_RANGE_DEF_TEMPLATE),TO_NUMBER(FLT_RANGE_DEF_VERSION) ASC");
			} else if (null != cfgFileName
					&& RMDCommonConstants.EDP_CFG_FILE
							.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("SELECT  OBJID,CFG_DEF_TEMPLATE,CFG_DEF_VERSION,CFG_DEF_DESC, STATUS FROM GETS_RMD.GETS_OMI_CFG_DEF WHERE ");
				cfgTempQuery
						.append("CFG_DEF2CTL_CFG=:ctrlCfgObjId AND CFG_TYPE=:cfgFileName ORDER BY DECODE(STATUS,'Complete',1,'WIP',2,'Obsolete',3,4),");
				cfgTempQuery
						.append("TO_NUMBER(CFG_DEF_TEMPLATE),TO_NUMBER(CFG_DEF_VERSION) ASC");
			} else if (null != cfgFileName
					&& RMDCommonConstants.AHC_CFG_FILE
							.equalsIgnoreCase(cfgFileName)) {
				cfgTempQuery
						.append("SELECT OBJID,AHC_TEMPLATE,AHC_VERSION,AHC_DESC,STATUS  FROM gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR ");
				cfgTempQuery
						.append("WHERE AHC_HDR2CTRCFG =:ctrlCfgObjId ORDER BY DECODE(STATUS,'Complete',1,'Work In Progress',2,'Obsolete',3,4),");
				cfgTempQuery
						.append("TO_NUMBER(AHC_TEMPLATE),TO_NUMBER(AHC_VERSION) ASC");
			}
			else if(null != cfgFileName
					&& RMDCommonConstants.RCI_CFG_FILE
							.equalsIgnoreCase(cfgFileName)){
				cfgTempQuery.append("SELECT GOCD.OBJID ,  GOCD.CFG_DEF_TEMPLATE,  GOCD.CFG_DEF_VERSION,  GOCD.CFG_DEF_DESC,GOCD.STATUS ");
				cfgTempQuery.append("FROM GETS_RMD_FAULT_CODES GRFC, GETS_OMI_CFG_DEF GOCD, GETS_RMD_CONTROLLER GRC ");
				cfgTempQuery.append("WHERE GOCD.RCI_FLT_CODE_ID =GRFC.OBJID AND GRC.CONTROLLER_SOURCE_ID = GRFC.CONTROLLER_SOURCE_ID AND ");
				cfgTempQuery.append("GOCD.CFG_DEF2CTL_CFG =:ctrlCfgObjId ");
				cfgTempQuery.append("ORDER BY decode(STATUS,'COMPLETE',1,'WIP',2,'OBSOLETE',3), TO_NUMBER(CFG_DEF_TEMPLATE), TO_NUMBER( CFG_DEF_VERSION) ");				
			}
			final Query cfgTempHQuery = session.createSQLQuery(cfgTempQuery
					.toString());
			cfgTempHQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);
			if (RMDCommonConstants.EDP_CFG_FILE.equalsIgnoreCase(cfgFileName)) {
				cfgTempHQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,
						cfgFileName);
			}
			resultList = cfgTempHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				cfgTempVOList = new ArrayList<ConfigTemplateDetailsVO>(
						resultList.size());
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] cfgTempDetails = (Object[]) obj.next();
					cfgTempVO = new ConfigTemplateDetailsVO();
					cfgTempVO.setTempObjId(RMDCommonUtility
							.convertObjectToString(cfgTempDetails[0]));
					cfgTempVO.setTemplateNo(RMDCommonUtility
							.convertObjectToString(cfgTempDetails[1]));
					cfgTempVO.setVersionNo(RMDCommonUtility
							.convertObjectToString(cfgTempDetails[2]));
					cfgTempVO
							.setTitle(ESAPI
									.encoder()
									.encodeForXML(
											EsapiUtil
													.escapeSpecialChars(RMDCommonUtility
															.convertObjectToString(cfgTempDetails[3]))));
					tempStatus = RMDCommonUtility
							.convertObjectToString(cfgTempDetails[4]);
					if(tempStatus.equalsIgnoreCase(RMDCommonConstants.WIP)){
						cfgTempVO
						.setStatus(RMDCommonConstants.WORK_IN_PROGRESS);
					}else if(tempStatus.equalsIgnoreCase(RMDCommonConstants.COMPLETE)){
						cfgTempVO
						.setStatus(RMDCommonConstants.COMPLETED);
					}else if(tempStatus.equalsIgnoreCase(RMDCommonConstants.OBSOLETE)){
                        cfgTempVO
                        .setStatus(RMDCommonConstants.OBSOLETE);
                    }
					else{
						cfgTempVO
						.setStatus(tempStatus);
					}
					
					cfgTempVOList.add(cfgTempVO);
					cfgTempVO = null;
				}

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLER_CONFIG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONTROLLER_CONFIG_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			cfgTempQuery = null;
			resultList = null;
		}
		return cfgTempVOList;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<EFIDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the existing EFI templates
	 */
	@Override
	public List<EFIDetailsVO> getEFIDetails() throws RMDDAOException {
		Session session = null;
		StringBuilder efiDetailsQuery = new StringBuilder();
		List<EFIDetailsVO> efiDetailsVOList = new ArrayList<EFIDetailsVO>();
		EFIDetailsVO efiDetailsVO = null;
		List<Object> resultList = null;
		try {
			session = getHibernateSession();
			efiDetailsQuery
					.append("SELECT OBJID,EFI_CFG_TEMPLATE,EFI_CFG_VERSION,ACTIVE_FLAG FROM GETS_OMI_EFI_CFG_DEF ORDER BY EFI_CFG_VERSION");
			final Query efiDetailsHQuery = session
					.createSQLQuery(efiDetailsQuery.toString());
			resultList = efiDetailsHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				efiDetailsVOList = new ArrayList<EFIDetailsVO>(
						resultList.size());
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] efiDetails = (Object[]) obj.next();
					efiDetailsVO = new EFIDetailsVO();
					efiDetailsVO.setTempObjId(RMDCommonUtility
							.convertObjectToString(efiDetails[0]));
					efiDetailsVO.setTemplateNo(RMDCommonUtility
							.convertObjectToString(efiDetails[1]));
					efiDetailsVO.setVersionNo(RMDCommonUtility
							.convertObjectToString(efiDetails[2]));
					efiDetailsVO.setIsActive(RMDCommonUtility
							.convertObjectToString(efiDetails[3]));
					efiDetailsVOList.add(efiDetailsVO);
					efiDetailsVO = null;
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EFI_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EFI_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			efiDetailsQuery = null;
			resultList = null;
		}
		return efiDetailsVOList;

	}

	/**
	 * @Author:
	 * @param userName
	 * @return String
	 * @throws RMDDAOException
	 * @Description This method is used to create new EFI template
	 * 
	 */
	@Override
	public String createNewEFI(String userName) throws RMDDAOException {
		String result = RMDCommonConstants.SUCCESS;
		String efiCfgDefDate = null;
		String efiSWVerDate = null;
		String efiMemoryDate = null;
		boolean createRecords = false;
		long efiCfgDate = 0;
		long efiSWdate = 0;
		long efiMemDate = 0;
		try {
			efiCfgDefDate = getEFICfgDefDate(userName);
			efiSWVerDate = getEFISWVerDate();
			if (null != efiCfgDefDate) {
				efiCfgDate = parseDate(efiCfgDefDate);
			}
			if (null != efiSWVerDate) {
				efiSWdate = parseDate(efiSWVerDate);
			}
			if (efiSWdate != 0 && efiCfgDate != 0 && efiCfgDate < efiSWdate) {
				createRecords = true;
			} else {
				efiMemoryDate = getEFIMemoryDate();
				if (null != efiMemoryDate) {
					efiMemDate = parseDate(efiMemoryDate);
				}
				if (efiMemDate != 0 && efiCfgDate != 0
						&& efiCfgDate < efiMemDate) {
					createRecords = true;
				}
			}
			if (createRecords) {
				result = createEFI(userName);
			} else {
				result = RMDServiceConstants.NO_NEW_EFI_DEF_FOUND;
			}

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_CREATE_NEW_EFI);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}
		return result;
	}

	/**
	 * @Author:
	 * @param userName
	 * @return String
	 * @throws RMDDAOException
	 *             ,SQLException
	 * @Description This method is used to create EFI template
	 * 
	 */
	public String createEFI(String userName) throws RMDDAOException,
			SQLException {
		SimpleJdbcCall jdbcCall = null;
		String result = RMDCommonConstants.SUCCESS;
		int returnCode = 0;
		try {
			jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
					.withCatalogName(RMDServiceConstants.GETS_RMD_APPLY_EFI_PKG)
					.withProcedureName("CREATE_EFIMTM_PR")
					.declareParameters(
							new SqlOutParameter("returnCode", Types.NUMERIC));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("p_userName", userName);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			if (null != simpleJdbcCallResult.get("returnCode")) {
				returnCode = Integer.parseInt(simpleJdbcCallResult.get(
						"returnCode").toString());
			}
			if (returnCode == -1) {
				result = RMDServiceConstants.NO_CFG_DEF_FOUND;
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_EFI_CFG_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			jdbcCall = null;
		}
		return result;
	}

	/**
	 * @Author:
	 * @param userName
	 * @return String
	 * @throws RMDDAOException
	 *             ,SQLException
	 * @Description This method is used to get the EFI Config Definition date
	 * 
	 */
	public String getEFICfgDefDate(String userName) throws RMDDAOException,
			SQLException {
		SimpleJdbcCall jdbcCall = null;
		String efiCfgDefDate = null;
		try {
			jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
					.withCatalogName(RMDServiceConstants.GETS_RMD_APPLY_EFI_PKG)
					.withProcedureName("EFI_CFG_DEF_DATE")
					.declareParameters(
							new SqlOutParameter("p_eficonfigDef", Types.VARCHAR),
							new SqlOutParameter("returnCode", Types.NUMERIC));
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("p_userName", userName);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
			if (null != simpleJdbcCallResult.get("p_eficonfigDef")) {
				efiCfgDefDate = simpleJdbcCallResult.get("p_eficonfigDef")
						.toString();
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_EFI_CFG_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			jdbcCall = null;
		}
		return efiCfgDefDate;
	}

	/**
	 * @Author:
	 * @param userName
	 * @return String
	 * @throws RMDDAOException
	 * @Description This method is used to get the EFI Software version date
	 * 
	 */
	public String getEFISWVerDate() throws RMDDAOException {
		SimpleJdbcCall jdbcCall = null;
		String efiSWVerDate = null;
		try {
			jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
					.withCatalogName(RMDServiceConstants.GETS_RMD_APPLY_EFI_PKG)
					.withProcedureName("SOFTWARE_VER_DATE")
					.declareParameters(
							new SqlOutParameter("p_SWdate", Types.VARCHAR),
							new SqlOutParameter("returnCode", Types.NUMERIC));
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute();
			if (null != simpleJdbcCallResult.get("p_SWdate")) {
				efiSWVerDate = simpleJdbcCallResult.get("p_SWdate").toString();
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_EFI_SW_VERSION_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			jdbcCall = null;
		}
		return efiSWVerDate;
	}

	/**
	 * @Author:
	 * @param userName
	 * @return String
	 * @throws RMDDAOException
	 * @Description This method is used to get the EFI Memory date
	 * 
	 */
	public String getEFIMemoryDate() throws RMDDAOException {
		SimpleJdbcCall jdbcCall = null;
		String efiMemDate = null;
		try {

			jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
					.withCatalogName(RMDServiceConstants.GETS_RMD_APPLY_EFI_PKG)
					.withProcedureName("SW_MEM_DATE")
					.declareParameters(
							new SqlOutParameter("p_SWMemdate", Types.VARCHAR),
							new SqlOutParameter("returnCode", Types.NUMERIC));
			Map<String, Object> simpleJdbcCallResult = jdbcCall.execute();
			if (null != simpleJdbcCallResult.get("p_SWMemdate")) {
				efiMemDate = simpleJdbcCallResult.get("p_SWMemdate").toString();
			}
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_IN_GET_EFI_MEMORY_DATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			jdbcCall = null;
		}
		return efiMemDate;
	}

	public long parseDate(String date) {
		long parsedDate = 0;
		SimpleDateFormat dfc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		parsedDate = dfc.parse(date, pos).getTime();
		return parsedDate;
	}

	/**
	 * @Author:
	 * @param ctrlCfgObjId
	 *            ,cfgFileName
	 * @return String
	 * @throws RMDDAOException
	 * @Description This method is used for fetching the latest template Number
	 * 
	 */
	@Override
	public String getMaxTemplateNumber(String ctrlCfgObjId, String cfgFileName)
			throws RMDDAOException {
		Session session = null;
		StringBuilder maxTempQuery = new StringBuilder();
		String maxTempNo = null;
		List<Object[]> maxTempList = null;
		try {
			session = getHibernateSession();
			maxTempQuery
					.append("SELECT DISTINCT (SELECT max(A.CFG_DEF_TEMPLATE) FROM GETS_RMD.GETS_OMI_CFG_DEF A WHERE A.CFG_type = :cfgFileName AND ");
			maxTempQuery
					.append("CFG_DEF2CTL_CFG = :ctrlCfgObjId) FROM GETS_RMD.GETS_OMI_CFG_DEF A, gets_RMD_CTL_CFG CC ");
			maxTempQuery
					.append("WHERE (A.CFG_TYPE = :cfgFileName and CFG_DEF2CTL_CFG = :ctrlCfgObjId) AND CC.OBJID = :ctrlCfgObjId");
			final Query maxTempHQuery = session.createSQLQuery(maxTempQuery
					.toString());
			maxTempHQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);
			maxTempHQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,
					cfgFileName);
			maxTempList = maxTempHQuery.list();
			if(maxTempList!=null && !maxTempList.isEmpty()){
				maxTempNo = String.valueOf(maxTempList.get(0));
				}
			if (null == maxTempNo) {
				maxTempNo = RMDCommonConstants.ZERO_STRING;
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_TEMPLATE_NUMBER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_TEMPLATE_NUMBER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			maxTempQuery = null;
			maxTempList = null;
		}
		return maxTempNo;
	}

	/**
	 * @Author:
	 * @param ctrlCfgObjId
	 *            ,cfgFileName,templateNo
	 * @return String
	 * @throws RMDDAOException
	 * @Description This method is used for fetching the latest version number
	 *              of selected template
	 * 
	 */
	@Override
	public String getTempMaxVerNumber(String ctrlCfgObjId, String cfgFileName,
			String templateNo) throws RMDDAOException {
		Session session = null;
		StringBuilder tempMaxVerQuery = new StringBuilder();
		String tempMaxVerNo = null;
		String ahc = new String("AHC");
		List<Object[]> maxTempVerList = null;
		try {
			session = getHibernateSession();
			if (cfgFileName.equals(ahc)) {
				tempMaxVerQuery
						.append("SELECT DISTINCT nvl(MAX(A.AHC_VERSION),0)  FROM gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR A WHERE ");
				tempMaxVerQuery
						.append(" A.AHC_HDR2CTRCFG=:ctrlCfgObjId AND A.AHC_TEMPLATE = :templateNo ");
			} else {
				tempMaxVerQuery
						.append("SELECT DISTINCT nvl(MAX(A.CFG_DEF_VERSION),0)  FROM GETS_RMD.GETS_OMI_CFG_DEF A WHERE A.CFG_TYPE = :cfgFileName ");
				tempMaxVerQuery
						.append("AND A.CFG_DEF2CTL_CFG=:ctrlCfgObjId AND A.CFG_DEF_TEMPLATE = :templateNo");
			}
			final Query tempMaxVerHQuery = session
					.createSQLQuery(tempMaxVerQuery.toString());
			if (!cfgFileName.equals(ahc)) {
				tempMaxVerHQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,
						cfgFileName);
			}
			tempMaxVerHQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);
			tempMaxVerHQuery.setParameter(RMDCommonConstants.TEMPLATE_NO,
					templateNo);
			maxTempVerList = tempMaxVerHQuery.list();
			tempMaxVerNo = String.valueOf(maxTempVerList.get(0));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_MAX_VERSION_NUMBER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_MAX_VERSION_NUMBER);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			tempMaxVerQuery = null;
			maxTempVerList = null;
		}
		return tempMaxVerNo;
	}

	/**
	 * @Author:
	 * @param objEDPSearchParamVo
	 * @return List<EDPParamDetailsVO>
	 * @throws RMDDAOException
	 * @Description This method is used to get the parameters that can be added
	 *              for EDP templates.
	 * 
	 */
	@Override
	public List<EDPParamDetailsVO> getEDPParameters(
			EDPSearchParamVO objEDPSearchParamVo) throws RMDDAOException {
		Session session = null;
		StringBuilder edpParamQuery = new StringBuilder();
		StringBuilder orderbyCol = new StringBuilder();
		List<EDPParamDetailsVO> edpParamVOList = new ArrayList<EDPParamDetailsVO>();
		EDPParamDetailsVO edpParamVO = null;
		String searchBy = objEDPSearchParamVo.getSearchBy();
		String condition = objEDPSearchParamVo.getCondition();
		String searchByVal = objEDPSearchParamVo.getSearchVal();
		String searchVal = "";
		List<Object> resultList = null;
		try {
			session = getHibernateSession();
			edpParamQuery
					.append("SELECT A.OBJID,A.REQUEST_PARM_NUMBER,B.CONTROLLER_NAME,A.PARM_DESC,A.PARM_UOM,A.USAGE_RATE,A.DEST_TYPE FROM GETS_RMD.GETS_RMD_PARMDEF A,");
			edpParamQuery
					.append("GETS_RMD.GETS_RMD_CONTROLLER B,GETS_RMD_CTL_CFG_SRC C WHERE a.controller_source_id = b.controller_source_id  ");
			edpParamQuery
					.append("AND C.controller_source_id = b.controller_source_id AND C.CTL_CFG_SRC2CTL_CFG  = :ctrlCfgObjId AND a.objid IN(    SELECT MIN(a.objid)");
			edpParamQuery
					.append(" FROM GETS_RMD.GETS_RMD_PARMDEF A,GETS_RMD.GETS_RMD_CONTROLLER B,GETS_RMD_CTL_CFG_SRC C WHERE a.controller_source_id = b.controller_source_id");
			edpParamQuery
					.append(" AND A.START_BIT = 0 AND A.ACTIVE_FLAG = 'Y' ");
			if (null != searchBy
					&& RMDCommonConstants.PARAMETER.equalsIgnoreCase(searchBy)) {
				edpParamQuery
						.append("AND UPPER(A.REQUEST_PARM_NUMBER)||UPPER(B.CONTROLLER_NAME) LIKE UPPER(:searchVal)");
				orderbyCol
						.append(" ORDER BY A.REQUEST_PARM_NUMBER,B.CONTROLLER_NAME");
			} else if (null != searchBy
					&& RMDCommonConstants.DESCRIPTION
							.equalsIgnoreCase(searchBy)) {
				edpParamQuery
						.append("AND UPPER(A.PARM_DESC) LIKE  UPPER(:searchVal)");
				orderbyCol.append(" ORDER BY A.PARM_DESC");
			}
			if (searchByVal.indexOf(RMDServiceConstants.PERCENTAGE) != -1) {
				edpParamQuery.append(" ESCAPE '/' ");
			}
			edpParamQuery
					.append(" AND C.controller_source_id = b.controller_source_id  AND C.CTL_CFG_SRC2CTL_CFG  = :ctrlCfgObjId GROUP BY A.REQUEST_PARM_NUMBER,");
			edpParamQuery
					.append("B.CONTROLLER_NAME,A.HEX,A.START_BIT,A.PARM_UOM,A.USAGE_RATE,A.DEST_TYPE) ");
			edpParamQuery.append(orderbyCol);

			final Query edpParamHQuery = session.createSQLQuery(edpParamQuery
					.toString());
			if (null != searchByVal
					&& searchByVal.indexOf(RMDServiceConstants.PERCENTAGE) != -1) {
				searchByVal = searchByVal.replace(
						RMDServiceConstants.PERCENTAGE,
						RMDServiceConstants.SYMBOL_FRONT_SLAH
								+ RMDServiceConstants.PERCENTAGE);
			}
			if (RMDCommonConstants.CONTAINS.equalsIgnoreCase(condition)) {
				searchVal = RMDServiceConstants.PERCENTAGE + searchByVal
						+ RMDServiceConstants.PERCENTAGE;
			} else if (RMDCommonConstants.ENDS_WITH.equalsIgnoreCase(condition)) {
				searchVal = RMDServiceConstants.PERCENTAGE + searchByVal;
			} else if (RMDCommonConstants.STARTS_WITH
					.equalsIgnoreCase(condition)) {
				searchVal = searchByVal + RMDServiceConstants.PERCENTAGE;
			} else
				searchVal = searchByVal;

			edpParamHQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					objEDPSearchParamVo.getCtrlCfgObjId());
			edpParamHQuery.setParameter(RMDCommonConstants.SEARCH_VAL,
					searchVal);

			resultList = edpParamHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				edpParamVOList = new ArrayList<EDPParamDetailsVO>(
						resultList.size());
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpParamDetails = (Object[]) obj.next();
					edpParamVO = new EDPParamDetailsVO();
					edpParamVO.setParamObjId(RMDCommonUtility
							.convertObjectToString(edpParamDetails[0]));
					edpParamVO.setParamNo(RMDCommonUtility
							.convertObjectToString(edpParamDetails[1]));
					edpParamVO.setCtrlName(RMDCommonUtility
							.convertObjectToString(edpParamDetails[2]));
					edpParamVO.setParamDesc(RMDCommonUtility
							.convertObjectToString(edpParamDetails[3]));
					edpParamVO.setUom(RMDCommonUtility
							.convertObjectToString(edpParamDetails[4]));
					edpParamVO.setUsageRate(RMDCommonUtility
							.convertObjectToString(edpParamDetails[5]));
					edpParamVO.setDestType(RMDCommonUtility
							.convertObjectToString(edpParamDetails[6]));
					edpParamVOList.add(edpParamVO);
					edpParamVO = null;
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			resultList = null;
			edpParamQuery = null;
			orderbyCol = null;
		}
		return edpParamVOList;
	}

	/**
	 * @Author:
	 * @param:tempObjId
	 * @return:List<EDPParamDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the opened template added
	 *               parameters
	 */
	@Override
	public List<EDPParamDetailsVO> getAddedEDPParameters(String tempObjId)
			throws RMDDAOException {
		Session session = null;
		StringBuilder addedEDPParamQuery = new StringBuilder();
		EDPParamDetailsVO edpParamVO = null;
		List<Object> resultList = null;
		List<EDPParamDetailsVO> addedEDPParamVOList = new ArrayList<EDPParamDetailsVO>();
		try {
			session = getHibernateSession();
			addedEDPParamQuery
					.append("SELECT A.OBJID, A.REQUEST_PARM_NUMBER,B.CONTROLLER_NAME,A.PARM_DESC,A.PARM_UOM,A.USAGE_RATE,A.DEST_TYPE FROM GETS_RMD.GETS_RMD_PARMDEF A,");
			addedEDPParamQuery
					.append("GETS_RMD.GETS_RMD_CONTROLLER B where a.objid in (select mtm2parm_def  from gets_omi_cfg_def_mtm_parm  where mtm2cfg_Def = :tempObjId) AND ");
			addedEDPParamQuery
					.append("a.controller_source_id = b.controller_source_id  AND A.ACTIVE_FLAG = ANY('y','Y') and A.START_BIT = 0 ");
			addedEDPParamQuery
					.append("ORDER BY A.USAGE_RATE,A.CONTROLLER_SOURCE_ID,A.REQUEST_PARM_NUMBER");

			final Query edpParamHQuery = session
					.createSQLQuery(addedEDPParamQuery.toString());
			edpParamHQuery.setParameter(RMDCommonConstants.TEMP_OBJ_ID,
					tempObjId);
			resultList = edpParamHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				addedEDPParamVOList = new ArrayList<EDPParamDetailsVO>(
						resultList.size());
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpParamDetails = (Object[]) obj.next();
					edpParamVO = new EDPParamDetailsVO();
					edpParamVO.setParamObjId(RMDCommonUtility
							.convertObjectToString(edpParamDetails[0]));
					edpParamVO.setParamNo(RMDCommonUtility
							.convertObjectToString(edpParamDetails[1]));
					edpParamVO.setCtrlName(RMDCommonUtility
							.convertObjectToString(edpParamDetails[2]));
					edpParamVO.setParamDesc(RMDCommonUtility
							.convertObjectToString(edpParamDetails[3]));
					edpParamVO.setUom(RMDCommonUtility
							.convertObjectToString(edpParamDetails[4]));
					edpParamVO.setUsageRate(RMDCommonUtility
							.convertObjectToString(edpParamDetails[5]));
					edpParamVO.setDestType(RMDCommonUtility
							.convertObjectToString(edpParamDetails[6]));
					addedEDPParamVOList.add(edpParamVO);
					edpParamVO = null;
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			addedEDPParamQuery = null;
			resultList = null;
		}
		return addedEDPParamVOList;
	}

	/**
	 * @Author:
	 * @param:objAddEditEDPDetailsVO
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for creating or updating EDP templates
	 */
	@Override
	public String saveEDPTemplate(AddEditEDPDetailsVO objAddEditEDPDetailsVO)
			throws RMDDAOException {
		String result = RMDCommonConstants.SUCCESS;
		SimpleJdbcCall jdbcCall = null;
		String canAdd = RMDCommonConstants.YES;
		Session session = null;
		StringBuilder addEDPParamQuery = new StringBuilder();
		StringBuilder delAllEDPParamQuery = new StringBuilder();
		StringBuilder delEDPParamQuery = new StringBuilder();
		String tempObjId = objAddEditEDPDetailsVO.getTempObjId();
		String newTempObjId = null;
		String addedParamObjId = null;
		try {
			session = getHibernateSession();
			if (null == tempObjId) {
				jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
						.withCatalogName("GETS_SD_WORKCFGFL_PKG")
						.withProcedureName("GETS_SD_SCFG_HDR_PR")
						.declareParameters(
								new SqlOutParameter("p_newOMIObjid",
										Types.NUMERIC),
								new SqlOutParameter("o_inserted", Types.VARCHAR));
				Map<String, Object> inParamMap = new HashMap<String, Object>();
				inParamMap.put("p_type", RMDCommonConstants.INSERT);
				inParamMap
						.put("p_whatnew", objAddEditEDPDetailsVO.getWhatNew());
				inParamMap.put("p_strWhoUser",
						objAddEditEDPDetailsVO.getUserName());
				inParamMap.put("p_objid",
						objAddEditEDPDetailsVO.getCtrlCfgObjId());
				inParamMap.put("p_cfgstatus",
						objAddEditEDPDetailsVO.getStatus());
				inParamMap.put("p_cfgtype",
						objAddEditEDPDetailsVO.getCfgFileName());
				inParamMap.put("p_temp",
						Long.parseLong(objAddEditEDPDetailsVO.getTemplateNo()));
				inParamMap.put("p_ver",
						Long.parseLong(objAddEditEDPDetailsVO.getVersionNo()));
				inParamMap.put(
						"p_description",
						ESAPI.encoder().decodeForHTML(
								objAddEditEDPDetailsVO.getTitle()));
				SqlParameterSource in = new MapSqlParameterSource(inParamMap);
				Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);
				newTempObjId = simpleJdbcCallResult.get("p_newOMIObjid")
						.toString();
				result = (String) simpleJdbcCallResult.get("o_inserted");
				if (RMDCommonConstants.NO.equalsIgnoreCase(result)) {
					canAdd = RMDCommonConstants.NO;
					result = RMDCommonConstants.RECORD_EXIST;
				} else {
					result = RMDCommonConstants.SUCCESS;
				}
			} else {
				jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						.withSchemaName(RMDServiceConstants.GETS_RMD_SCHEMA)
						.withCatalogName("GETS_SD_WORKCFGFL_PKG")
						.withProcedureName("GETS_SD_SCFG_HDR_PR")
						.declareParameters(
								new SqlOutParameter("p_newOMIObjid",
										Types.NUMERIC),
								new SqlOutParameter("o_inserted", Types.VARCHAR));

				Map<String, Object> inParamMap = new HashMap<String, Object>();
				inParamMap.put("p_type", RMDCommonConstants.UPDATE);
				inParamMap.put("p_whatnew", RMDCommonConstants.EMPTY_STRING);
				inParamMap.put("p_strWhoUser",
						objAddEditEDPDetailsVO.getUserName());
				inParamMap.put("p_objid", Long.parseLong(tempObjId));
				inParamMap.put("p_cfgstatus",
						objAddEditEDPDetailsVO.getStatus());
				inParamMap.put("p_cfgtype", RMDCommonConstants.EMPTY_STRING);
				inParamMap.put("p_temp", RMDCommonConstants.INT_CONST);
				inParamMap.put("p_ver", RMDCommonConstants.INT_CONST);
				inParamMap
						.put("p_description", RMDCommonConstants.EMPTY_STRING);
				SqlParameterSource in = new MapSqlParameterSource(inParamMap);
				Map<String, Object> simpleJdbcCallResult = jdbcCall.execute(in);

				if (null != objAddEditEDPDetailsVO.getRemovedParamObjId()) {
					if (RMDCommonConstants.REMOVE_ALL
							.equalsIgnoreCase(objAddEditEDPDetailsVO
									.getRemovedParamObjId())) {
						delAllEDPParamQuery
								.append("DELETE FROM  GETS_RMD.GETS_OMI_CFG_DEF_MTM_PARM  A WHERE  A.MTM2CFG_DEF = :tempObjId");
						final Query delEDPParamHQuery = session
								.createSQLQuery(delAllEDPParamQuery.toString());
						delEDPParamHQuery.setParameter(
								RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
						delEDPParamHQuery.executeUpdate();
					} else {
						delEDPParamQuery
								.append("DELETE FROM GETS_RMD.GETS_OMI_CFG_DEF_MTM_PARM  WHERE MTM2PARM_DEF=:paramObjId AND MTM2CFG_DEF = :tempObjId");
						final Query delEDPParamHQuery = session
								.createSQLQuery(delEDPParamQuery.toString());
						List<String> removedObjIdList = Arrays
								.asList(objAddEditEDPDetailsVO
										.getRemovedParamObjId()
										.split(RMDCommonConstants.COMMMA_SEPARATOR));
						for (String paramObjId : removedObjIdList) {
							delEDPParamHQuery
									.setParameter(
											RMDCommonConstants.PARAM_OBJ_ID,
											paramObjId);
							delEDPParamHQuery.setParameter(
									RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
							delEDPParamHQuery.executeUpdate();
						}

					}
				}
			}

			if (RMDCommonConstants.YES.equalsIgnoreCase(canAdd)) {
				if (null != tempObjId
						&& null != objAddEditEDPDetailsVO.getAddedParamObjId()) {
					addedParamObjId = objAddEditEDPDetailsVO
							.getAddedParamObjId();
				} else if (null != newTempObjId
						&& null != objAddEditEDPDetailsVO.getParamObjId()) {
					addedParamObjId = objAddEditEDPDetailsVO.getParamObjId();
					tempObjId = newTempObjId;
				}
				if (null != addedParamObjId && !addedParamObjId.isEmpty()) {
					int sequenceid = 1;
					addEDPParamQuery
							.append("INSERT INTO GETS_RMD.GETS_OMI_CFG_DEF_MTM_PARM (OBJID, LAST_UPDATED_DATE, LAST_UPDATED_BY, CREATION_DATE, CREATED_BY, MTM2CFG_DEF,");
					addEDPParamQuery
							.append("MTM2PARM_DEF, SEQUENCE_ID) VALUES(gets_omi_cfg_def_mtm_parm_seq.nextval, sysdate,:userName,sysdate,:userName,:tempObjId,:paramObjId,:sequenceid)");
					final Query addEDPParamHQuery = session
							.createSQLQuery(addEDPParamQuery.toString());
					List<String> parmObjIdList = Arrays.asList(addedParamObjId
							.split(RMDCommonConstants.COMMMA_SEPARATOR));

					for (String paramObjId : parmObjIdList) {

						addEDPParamHQuery.setParameter(
								RMDCommonConstants.USER_NAME,
								objAddEditEDPDetailsVO.getUserName());
						addEDPParamHQuery.setParameter(
								RMDCommonConstants.TEMP_OBJ_ID, tempObjId);
						addEDPParamHQuery.setParameter(
								RMDCommonConstants.PARAM_OBJ_ID, paramObjId);
						addEDPParamHQuery.setParameter(
								RMDCommonConstants.SEQUENCE_ID, sequenceid);
						addEDPParamHQuery.executeUpdate();
					}

				}
			}
			result = result + RMDCommonConstants.HYPHEN + tempObjId;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			addEDPParamQuery = null;
			delAllEDPParamQuery = null;
			delEDPParamQuery = null;
		}

		return result;
	}

	/**
	 * @Author:
	 * @param:String cfgFileName, String templateNo, String versionNo,String
	 *               ctrlCfgObjId
	 * @return:EDPHeaderDetailsVO
	 * @throws:RMDDAOException
	 * @Description: This method is used for getting the Next/Previous EDP
	 *               template templates
	 */
	@Override
	public EDPHeaderDetailsVO getPreNextEDPDetails(String cfgFileName,
			String templateNo, String versionNo, String ctrlCfgObjId)
			throws RMDDAOException {
		Session session = null;
		StringBuilder headerQuery = new StringBuilder();
		EDPHeaderDetailsVO objEDPHeaderDetailsVO = null;
		String ahc = new String("AHC");
		List<Object> resultList = null;
		try {
			session = getHibernateSession();
			if (cfgFileName.equals(ahc)) {
				headerQuery
						.append(" SELECT A.OBJID , A.AHC_HDR2CTRCFG,  A.AHC_TEMPLATE ,A.AHC_VERSION , A.AHC_DESC ,A.STATUS ");
				headerQuery
						.append("FROM gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR A  WHERE A.AHC_TEMPLATE = :templateNo AND A.AHC_VERSION =:versionNo AND A.AHC_HDR2CTRCFG=:ctrlCfgObjId");

			} else {
				headerQuery
						.append(" SELECT A.OBJID , A.CFG_DEF2CTL_CFG, A.CFG_TYPE , A.CFG_DEF_TEMPLATE ,A.CFG_DEF_VERSION , A.CFG_DEF_DESC ,  B.CONTROLLER_CFG,A.STATUS ");
				headerQuery
						.append("FROM GETS_RMD.GETS_OMI_CFG_DEF A , GETS_RMD.GETS_RMD_CTL_CFG B WHERE A.OBJID = (SELECT OBJID FROM  GETS_RMD.GETS_OMI_CFG_DEF WHERE ");
				headerQuery
						.append("CFG_TYPE = :cfgFileName AND CFG_DEF_TEMPLATE = :templateNo AND CFG_DEF_VERSION =:versionNo AND CFG_DEF2CTL_CFG=:ctrlCfgObjId) AND B.OBJID = A.CFG_DEF2CTL_CFG");
			}
			final Query headerHQuery = session.createSQLQuery(headerQuery
					.toString());
			if (!cfgFileName.equals(ahc)) {
				headerHQuery.setParameter(RMDCommonConstants.CFG_FILE_NAME,
						cfgFileName);
			}
			headerHQuery.setParameter(RMDCommonConstants.TEMPLATE_NO,
					templateNo);
			headerHQuery.setParameter(RMDCommonConstants.VERSION_NO, versionNo);
			headerHQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
					ctrlCfgObjId);
			resultList = headerHQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpTempDetails = (Object[]) obj.next();
					objEDPHeaderDetailsVO = new EDPHeaderDetailsVO();
					objEDPHeaderDetailsVO.setTempObjId(RMDCommonUtility
							.convertObjectToString(edpTempDetails[0]));
					objEDPHeaderDetailsVO.setCtrlCfgObjId(RMDCommonUtility
							.convertObjectToString(edpTempDetails[1]));
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setCfgFileName(cfgFileName);
					} else {
						objEDPHeaderDetailsVO.setCfgFileName(RMDCommonUtility
								.convertObjectToString(edpTempDetails[2]));
					}
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setTemplateNo(RMDCommonUtility
								.convertObjectToString(edpTempDetails[2]));
					} else {
						objEDPHeaderDetailsVO.setTemplateNo(RMDCommonUtility
								.convertObjectToString(edpTempDetails[3]));
					}
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setVesrionNo(RMDCommonUtility
								.convertObjectToString(edpTempDetails[3]));
					} else {
						objEDPHeaderDetailsVO.setVesrionNo(RMDCommonUtility
								.convertObjectToString(edpTempDetails[4]));
					}
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setTitle(RMDCommonUtility
								.convertObjectToString(edpTempDetails[4]));
					} else {
						objEDPHeaderDetailsVO.setTitle(RMDCommonUtility
								.convertObjectToString(edpTempDetails[4]));
					}
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setCtrlCfgName("");
					} else {
						objEDPHeaderDetailsVO.setCtrlCfgName(RMDCommonUtility
								.convertObjectToString(edpTempDetails[6]));
					}
					if (cfgFileName.equals(ahc)) {
						objEDPHeaderDetailsVO.setStatus(RMDCommonUtility
								.convertObjectToString(edpTempDetails[5]));
					} else {
						objEDPHeaderDetailsVO.setStatus(RMDCommonUtility
								.convertObjectToString(edpTempDetails[7]));
					}

				}

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDED_EDP_PARAMETERS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			resultList = null;
			headerQuery = null;
		}
		return objEDPHeaderDetailsVO;
	}

	/**
	 * @Author:
	 * @param:String configId
	 * @return:List<FaultValueVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the parameter title
	 */
	@Override
	public List<FaultValueVO> getParameterTitle(String configId)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		FaultValueVO objFaultValueVO = null;
		List<FaultValueVO> lstFaultValueVO = new ArrayList<FaultValueVO>();
		Object parameterTitle = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT pdef.objid,pdef.rule_parm_desc FROM gets_rmd_parmdef pdef,gets_rmd_ctl_cfg_src csrc,gets_rmd_ctl_cfg ccfg WHERE  ccfg.objid = csrc.ctl_cfg_src2ctl_cfg AND ");
			caseQry.append("csrc.controller_source_id = pdef.controller_source_id AND ccfg.controller_cfg_desc = :configId  AND ACTIVE_FLAG = ANY('y','Y') and START_BIT = 0 ORDER BY pdef.rule_parm_desc ASC ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID, configId);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				lstFaultValueVO = new ArrayList<FaultValueVO>(resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] efiDetails = (Object[]) obj.next();
					objFaultValueVO = new FaultValueVO();
					objFaultValueVO.setObjId((RMDCommonUtility
							.convertObjectToString(efiDetails[0])));
					objFaultValueVO.setParameterTitle((RMDCommonUtility
							.convertObjectToString(efiDetails[1])));
					lstFaultValueVO.add(objFaultValueVO);

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PARAMETER_TITLE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_PARAMETER_TITLE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultValueVO;
	}

	/**
	 * @Author:
	 * @param:String configId
	 * @return:List<FaultValueVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the EDP templates
	 */
	@Override
	public List<FaultValueVO> getEDPTemplate(String configId)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		FaultValueVO objFaultValueVO = null;
		List<FaultValueVO> lstFaultValueVO = new ArrayList<FaultValueVO>();
		Object parameterTitle = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT DISTINCT def.objid, def.cfg_def_template, def.cfg_def_version, def.cfg_def_desc FROM gets_omi_cfg_def def,gets_rmd_ctl_cfg cfg WHERE cfg.objid = def.cfg_def2ctl_cfg AND  UPPER(def.status) = 'COMPLETE' AND UPPER(def.cfg_type) = 'EDP' AND  cfg.objid =:configId");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID, configId);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				lstFaultValueVO = new ArrayList<FaultValueVO>(resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpDetails = (Object[]) obj.next();
					objFaultValueVO = new FaultValueVO();
					objFaultValueVO.setObjId((RMDCommonUtility
							.convertObjectToString(edpDetails[0])));
					objFaultValueVO.setCfgDetailDesc((RMDCommonUtility
							.convertObjectToString(edpDetails[1])));
					objFaultValueVO.setCfgVersion((RMDCommonUtility
							.convertObjectToString(edpDetails[2])));
					objFaultValueVO.setCfgDesc((RMDCommonUtility
							.convertObjectToString(edpDetails[3])));
					lstFaultValueVO.add(objFaultValueVO);

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultValueVO;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<FaultValueVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching existing pre,post,bias
	 *               values.
	 */
	@Override
	public List<FaultValueVO> getDefaultValuesRange() throws RMDDAOException {
		List<Object[]> resultList = null, preResultList = null, postResultList = null;
		Session session = null;
		FaultValueVO objFaultValueVO = null;
		List<FaultValueVO> lstFaultValueVO = new ArrayList<FaultValueVO>();
		Object preValue = null, postvalue = null;
		String preVal = null, postVal = null;
		try {
			session = getHibernateSession();
			StringBuilder biasQry = new StringBuilder();
			StringBuilder preQry = new StringBuilder();
			StringBuilder postQry = new StringBuilder();
			biasQry.append(" select value biasvalue from gets_rmd_sysparm  where upper(title) = 'BIAS VALUE' order by value asc");
			Query caseHqry = session.createSQLQuery(biasQry.toString());
			preQry.append("SELECT value prevalue FROM gets_rmd_sysparm where UPPER(title) = UPPER('Fault Pre Value') ");
			Query preHqry = session.createSQLQuery(preQry.toString());
			postQry.append("SELECT value postvalue FROM gets_rmd_sysparm where UPPER(title) = UPPER('Fault Post Value') ");
			Query postHqry = session.createSQLQuery(postQry.toString());
			preVal = preHqry.list().get(0).toString();
			postVal = postHqry.list().get(0).toString();
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object biasValue = (Object) obj.next();
					objFaultValueVO = new FaultValueVO();
					objFaultValueVO.setBiasValue((RMDCommonUtility
							.convertObjectToString(biasValue)));
					objFaultValueVO.setPreValue(preVal);
					objFaultValueVO.setPostValue(postVal);
					lstFaultValueVO.add(objFaultValueVO);

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_EDP_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultValueVO;
	}

	/**
	 * @Author:
	 * @param:FaultDetailsVO
	 * @return:List<FaultFilterDefVo>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the FFD Template details.
	 */
	@Override
	public List<FaultFilterDefVo> populateFFDDetails(
			FaultDetailsVO faultDetailsVO) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		FaultFilterDefVo objFaultFilterDefVo = null;
		List<FaultFilterDefVo> lstFaultFilterDefVo = new ArrayList<FaultFilterDefVo>();
		Object[] configDetails = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT DECODE(ffdtl.element_conjunction,0,'',1,'AND',2,'OR',3,'NOT AND',4,'NOT OR',5,'AND NOT',6,'OR NOT'),ffdtl.filter_value_1,DECODE(ffdtl.operator_1,0,'',1,'=',2,'<>',3,'>',4,'<',5,'>=',6,'<='), pdef.rule_parm_desc, ");
			caseQry.append(" DECODE(ffdtl.operator_2,0,'',1,'=',2,'<>',3,'>',4,'<',5,'>=',6,'<='),ffdtl.filter_value_2,pdef.objid, ffdtl.objid tempObjId FROM   gets_omi_flt_filter_def ffdef,  gets_omi_flt_filter_dtl ffdtl,gets_rmd_ctl_cfg ccfg, ");
			caseQry.append("gets_rmd_parmdef pdef WHERE  ffdef.objid = ffdtl.flt_filter_dtl2flt_filter_def AND ccfg.objid  = ffdef.flt_filter_def2ctl_cfg AND pdef.objid  = ffdtl.flt_filter_dtl2parmdef AND ");
			caseQry.append("ffdef.flt_filter_def2ctl_cfg = :configId AND ffdef.flt_filter_template = :template AND ffdef.flt_filter_version = :version ORDER BY ffdtl.objid");

			
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID,
					faultDetailsVO.getConfigId());
			caseHqry.setParameter(RMDCommonConstants.TEMPLATE,
					faultDetailsVO.getTemplateId());
			caseHqry.setParameter(RMDCommonConstants.VERSION,
					faultDetailsVO.getVersion());
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				lstFaultFilterDefVo = new ArrayList<FaultFilterDefVo>(
						resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					configDetails = (Object[]) obj.next();
					objFaultFilterDefVo = new FaultFilterDefVo();

					objFaultFilterDefVo.setConjunction(RMDCommonUtility
							.convertObjectToString(configDetails[0]));
					objFaultFilterDefVo.setValueFrom(RMDCommonUtility
							.convertObjectToString(configDetails[1]));
					objFaultFilterDefVo.setOperatorFrom(RMDCommonUtility
							.convertObjectToString(configDetails[2]));
					objFaultFilterDefVo.setParameterTitle(RMDCommonUtility
							.convertObjectToString(configDetails[3]));
					objFaultFilterDefVo.setOperatorTo(RMDCommonUtility
							.convertObjectToString(configDetails[4]));
					objFaultFilterDefVo.setValueTo(RMDCommonUtility
							.convertObjectToString(configDetails[5]));
					objFaultFilterDefVo.setParameterObjId(RMDCommonUtility
							.convertObjectToString(configDetails[6]));
					objFaultFilterDefVo.setObjId(RMDCommonUtility
							.convertObjectToString(configDetails[7]));
					lstFaultFilterDefVo.add(objFaultFilterDefVo);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultFilterDefVo;
	}

	/**
	 * @Author:
	 * @param:FaultDetailsVO
	 * @return:List<FaultRangeDefVo>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the FRD Template details.
	 */
	@Override
	public List<FaultRangeDefVo> populateFRDDetails(
			FaultDetailsVO faultDetailsVO) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		FaultRangeDefVo objFaultRangeDefVo = null;
		List<FaultRangeDefVo> lstFaultRangeDefVo = new ArrayList<FaultRangeDefVo>();
		Object[] configDetails = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			StringBuilder caseQrySecondCase = new StringBuilder();

			if ((faultDetailsVO.getConfigValue()
					.equals(RMDCommonConstants.AC6000))
					|| (faultDetailsVO.getConfigValue()
							.equals(RMDCommonConstants.ACCCA))
					|| (faultDetailsVO.getConfigValue()
							.equals(RMDCommonConstants.DCCCA))
					|| (faultDetailsVO.getConfigValue()
							.equals(RMDCommonConstants.CCA))
					|| (faultDetailsVO.getConfigValue()
							.equals(RMDCommonConstants.EC2))) {

				caseQry.append(" SELECT distinct dtl.objid, ctrl.controller_source_id,ctrl.controller_name, dtl.fault_code_start,dtl.sub_sys_start sub_id_start ,dtl.fault_code_end, ");
				caseQry.append(" dtl.sub_sys_end sub_id_end, dtl.edp_template,dtl.samples_pre,dtl.samples_post,dtl.sample_bias,dtl.fault_code_start f_start,dtl.fault_code_end f_end, dtl.edp_version ");
				caseQry.append(" FROM gets_omi_flt_range_dtl dtl,gets_omi_flt_range_def def,gets_rmd_ctl_cfg cfg,gets_rmd_ctl_cfg_src src,gets_rmd_controller ctrl WHERE cfg.objid = def.flt_range_def2ctl_cfg ");
				caseQry.append(" AND def.objid = dtl.flt_range_dtl2flt_range_def AND cfg.objid = src.ctl_cfg_src2ctl_cfg AND dtl.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND def.flt_range_def_template=:template ");
				caseQry.append(" AND def.flt_range_def_version =:version order by ctrl.controller_source_id,sub_id_start,f_start,sub_id_end,f_end  asc ");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						faultDetailsVO.getConfigId());
				caseHqry.setParameter(RMDCommonConstants.TEMPLATE,
						faultDetailsVO.getTemplateId());
				caseHqry.setParameter(RMDCommonConstants.VERSION,
						faultDetailsVO.getVersion());
				resultList = caseHqry.list();
				if (resultList != null && resultList.size() > 0) {
					lstFaultRangeDefVo = new ArrayList<FaultRangeDefVo>(
							resultList.size());
				}
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					for (final Iterator<Object[]> obj = resultList.iterator(); obj
							.hasNext();) {
						configDetails = (Object[]) obj.next();
						objFaultRangeDefVo = new FaultRangeDefVo();

						objFaultRangeDefVo.setObjId(RMDCommonUtility
								.convertObjectToString(configDetails[0]));
						objFaultRangeDefVo.setFaultSource(RMDCommonUtility
								.convertObjectToString(configDetails[1]));
						objFaultRangeDefVo.setControllerName(RMDCommonUtility
								.convertObjectToString(configDetails[2]));
						objFaultRangeDefVo.setFaultCodeFrom(RMDCommonUtility
								.convertObjectToString(configDetails[3]));
						objFaultRangeDefVo.setFaultStart(RMDCommonUtility
								.convertObjectToString(configDetails[4]));
						objFaultRangeDefVo.setFaultCodeTo(RMDCommonUtility
								.convertObjectToString(configDetails[5]));
						objFaultRangeDefVo.setFaultEnd(RMDCommonUtility
								.convertObjectToString(configDetails[6]));
						objFaultRangeDefVo.setEdpTemplate(RMDCommonUtility
								.convertObjectToString(configDetails[7]));
						objFaultRangeDefVo.setPreValue(RMDCommonUtility
								.convertObjectToString(configDetails[8]));
						objFaultRangeDefVo.setPostValue(RMDCommonUtility
								.convertObjectToString(configDetails[9]));
						objFaultRangeDefVo.setBiasValue(RMDCommonUtility
								.convertObjectToString(configDetails[10]));
						objFaultRangeDefVo.setVersion(RMDCommonUtility
								.convertObjectToString(configDetails[13]));
						lstFaultRangeDefVo.add(objFaultRangeDefVo);
					}
				}
			} else {
				caseQry.append(" SELECT distinct dtl.objid,ctrl.controller_source_id,ctrl.controller_name,dtl.fault_code_start,dtl.sub_id_start,dtl.fault_code_end,dtl.sub_id_end,dtl.edp_template,dtl.samples_pre,  ");
				caseQry.append(" dtl.samples_post,dtl.sample_bias,dtl.fault_code_start f_start,dtl.fault_code_end f_end,to_number(nvl(dtl.sub_id_start,'00'),'xx' ) s_subid,to_number(nvl(dtl.sub_id_end,'00'),'xx' ) e_subid, ");
				caseQry.append(" dtl.edp_version FROM gets_omi_flt_range_dtl dtl,gets_omi_flt_range_def def,gets_rmd_ctl_cfg cfg,gets_rmd_ctl_cfg_src src,gets_rmd_controller ctrl WHERE cfg.objid = def.flt_range_def2ctl_cfg ");
				caseQry.append(" AND def.objid = dtl.flt_range_dtl2flt_range_def AND cfg.objid = src.ctl_cfg_src2ctl_cfg AND dtl.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId ");
				caseQry.append(" AND def.flt_range_def_template=:template AND def.flt_range_def_version =:version order by ctrl.controller_source_id,f_start,s_subid,f_end,e_subid ");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						faultDetailsVO.getConfigId());
				caseHqry.setParameter(RMDCommonConstants.TEMPLATE,
						faultDetailsVO.getTemplateId());
				caseHqry.setParameter(RMDCommonConstants.VERSION,
						faultDetailsVO.getVersion());
				resultList = caseHqry.list();
				if (resultList != null && resultList.size() > 0) {
					lstFaultRangeDefVo = new ArrayList<FaultRangeDefVo>(
							resultList.size());
				}
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					for (final Iterator<Object[]> obj = resultList.iterator(); obj
							.hasNext();) {
						configDetails = (Object[]) obj.next();
						objFaultRangeDefVo = new FaultRangeDefVo();

						objFaultRangeDefVo.setObjId(RMDCommonUtility
								.convertObjectToString(configDetails[0]));
						objFaultRangeDefVo.setFaultSource(RMDCommonUtility
								.convertObjectToString(configDetails[1]));
						objFaultRangeDefVo.setControllerName(RMDCommonUtility
								.convertObjectToString(configDetails[2]));
						objFaultRangeDefVo.setFaultCodeFrom(RMDCommonUtility
								.convertObjectToString(configDetails[3]));
						objFaultRangeDefVo.setSubIdFrom(RMDCommonUtility
								.convertObjectToString(configDetails[4]));
						objFaultRangeDefVo.setFaultCodeTo(RMDCommonUtility
								.convertObjectToString(configDetails[5]));
						objFaultRangeDefVo.setSubIdTo(RMDCommonUtility
								.convertObjectToString(configDetails[6]));
						objFaultRangeDefVo.setEdpTemplate(RMDCommonUtility
								.convertObjectToString(configDetails[7]));
						objFaultRangeDefVo.setPreValue(RMDCommonUtility
								.convertObjectToString(configDetails[8]));
						objFaultRangeDefVo.setPostValue(RMDCommonUtility
								.convertObjectToString(configDetails[9]));
						objFaultRangeDefVo.setBiasValue(RMDCommonUtility
								.convertObjectToString(configDetails[10]));
						objFaultRangeDefVo.setVersion(RMDCommonUtility
								.convertObjectToString(configDetails[13]));
						lstFaultRangeDefVo.add(objFaultRangeDefVo);
					}
				}

			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPULATE_FFD_TEMPLATE_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultRangeDefVo;
	}

	/**
	 * @Author:
	 * @param:List<FaultFilterDefVo>
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for save the FFD Template details.
	 */
	@Override
	public String saveFFDTemplate(List<FaultFilterDefVo> lstFaultFilterDefVo)
			throws RMDDAOException {
		Session session = null;
		StringBuilder ffdUpdateQuery = new StringBuilder();
		StringBuilder ffdDeleteQuery = new StringBuilder();
		StringBuilder ffdInsertQuery = new StringBuilder();
		StringBuilder ffdProcedureQuery = new StringBuilder();
		StringBuilder newTemplateQuery = new StringBuilder();
		StringBuilder fetchObjQuery = new StringBuilder();
		StringBuilder upObjQuery = new StringBuilder();
		Query updatehibernateQuery = null, deleteHibernateQuery = null, insertHibernateQuery = null, selectHibernateQuery = null, newInsertQuery = null, hqrQuery = null, upQuery = null;
		Transaction transaction = null;
		List<Object[]> resultList = null, resultList1 = null;
		List<BigDecimal> queryList = null;
		int sequenceid = 0, sequenceID = 0, objId = 0;
		boolean flag = true;
		String status = RMDServiceConstants.SUCCESS, sourceId = null, templateId = null, templateNo = null, versionId = null, configId = null, configValue = null, userName = null;
		Object template = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			ffdUpdateQuery
					.append(" UPDATE gets_omi_flt_filter_def SET last_updated_by = :userName,last_updated_date = sysdate,flt_filter_desc   = :title,status= :status ");
			ffdUpdateQuery
					.append("WHERE flt_filter_def2ctl_cfg = :ctrlCfgObjId AND flt_filter_template =:template AND flt_filter_version =:version");
			updatehibernateQuery = session.createSQLQuery(ffdUpdateQuery
					.toString());
			upObjQuery
					.append("update gets_omi_flt_filter_dtl set last_updated_date = sysdate,last_updated_by= :userName,FILTER_VALUE_1=:value1,OPERATOR_1=:operatorFrom,FLT_FILTER_DTL2PARMDEF=:parameterObjId,OPERATOR_2=:operatorTo,FILTER_VALUE_2=:value2,ELEMENT_CONJUNCTION=:conjunction where OBJID=:defObjId");
			upQuery = session.createSQLQuery(upObjQuery.toString());

			ffdInsertQuery
					.append(" insert into gets_omi_flt_filter_dtl(objid,last_updated_date,last_updated_by, creation_date,created_by,controller_source_id,filter_value_1, operator_1,flt_filter_dtl2parmdef,operator_2,filter_value_2,element_conjunction,sequence_id,flt_filter_dtl2flt_filter_def) ");
			ffdInsertQuery
					.append("values (gets_omi.gets_omi_flt_filter_dtl_seq.nextval,sysdate,:userName,sysdate,:userName,:sourceId,:value1,:operatorFrom,:parameterObjId,:operatorTo,:value2,:conjunction,:sequenceid,:defObjId)");
			insertHibernateQuery = session.createSQLQuery(ffdInsertQuery
					.toString());
			ffdProcedureQuery
					.append("select controller_source_id from gets_rmd_controller where controller_name =:configValue");
			selectHibernateQuery = session.createSQLQuery(ffdProcedureQuery
					.toString());
			fetchObjQuery
					.append("select objid from gets_omi_flt_filter_def where flt_filter_template = :template and flt_filter_version =:version and flt_filter_def2ctl_cfg = :ctrlCfgObjId");
			hqrQuery = session.createSQLQuery(fetchObjQuery.toString());
			newTemplateQuery
					.append("INSERT INTO gets_omi_flt_filter_def(objid,last_updated_date,last_updated_by,creation_date,created_by,flt_filter_template,flt_filter_version,flt_filter_desc,flt_filter_def2ctl_cfg,status) ");
			newTemplateQuery
					.append("VALUES (gets_omi.gets_omi_flt_filter_def_seq.nextval,sysdate,:userName,sysdate,:userName,:template,:version,:title,:ctrlCfgObjId,:status)");
			newInsertQuery = session
					.createSQLQuery(newTemplateQuery.toString());
			for (FaultFilterDefVo objFaultFilterDefVo : lstFaultFilterDefVo) {
				if (objFaultFilterDefVo.getAction().equals(
						RMDServiceConstants.INSERT_HEADER)
						|| objFaultFilterDefVo.getAction().equals(
								RMDServiceConstants.UPDATE_HEADER)) {
					templateNo = objFaultFilterDefVo.getTemplate();
					versionId = objFaultFilterDefVo.getVersion();
					configId = objFaultFilterDefVo.getConfigId();
					configValue = objFaultFilterDefVo.getConfigValue();
					userName = objFaultFilterDefVo.getUserName();
					hqrQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
							configId);
					hqrQuery.setParameter(RMDCommonConstants.TEMPLATE,
							templateNo);
					hqrQuery.setParameter(RMDCommonConstants.VERSION, versionId);
					objId = hqrQuery.list().size();
					if (objId == 0) {
						newInsertQuery.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultFilterDefVo.getUserName());
						newInsertQuery.setParameter(
								RMDCommonConstants.TEMPLATE,
								objFaultFilterDefVo.getTemplate());
						newInsertQuery.setParameter(RMDCommonConstants.VERSION,
								objFaultFilterDefVo.getVersion());
						newInsertQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID,
								objFaultFilterDefVo.getConfigId());
						newInsertQuery.setParameter(
								RMDCommonConstants.TITLE,
								ESAPI.encoder().decodeForHTML(
										objFaultFilterDefVo.getTitle()));
						newInsertQuery.setParameter(RMDCommonConstants.STATUS,
								objFaultFilterDefVo.getStatus());
						newInsertQuery.executeUpdate();
					} else {
						updatehibernateQuery.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultFilterDefVo.getUserName());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.TITLE,
								ESAPI.encoder().decodeForHTML(
										objFaultFilterDefVo.getTitle()));
						updatehibernateQuery.setParameter(
								RMDCommonConstants.STATUS,
								objFaultFilterDefVo.getStatus());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.VERSION,
								objFaultFilterDefVo.getVersion());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.TEMPLATE,
								objFaultFilterDefVo.getTemplate());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID,
								objFaultFilterDefVo.getConfigId());
						updatehibernateQuery.executeUpdate();
					}

				} else if (objFaultFilterDefVo.getAction().equals(
						RMDServiceConstants.UPDATE)) {
					upQuery.setParameter(RMDCommonConstants.USERNAME, userName);
					upQuery.setParameter(RMDCommonConstants.VALUE1,
							objFaultFilterDefVo.getValueFrom());
					upQuery.setParameter(RMDCommonConstants.OPERATOR_FROM,
							objFaultFilterDefVo.getOperatorFrom());
					upQuery.setParameter(RMDCommonConstants.PARAMETER_OBJID,
							objFaultFilterDefVo.getParameterObjId());
					upQuery.setParameter(RMDCommonConstants.VALUE2,
							objFaultFilterDefVo.getValueTo());
					upQuery.setParameter(RMDCommonConstants.OPERATOR_TO,
							objFaultFilterDefVo.getOperatorTo());
					upQuery.setParameter(RMDCommonConstants.CONJUNCTION,
							objFaultFilterDefVo.getConjunction());
					upQuery.setParameter(RMDCommonConstants.DEF_OBJID,
							objFaultFilterDefVo.getDescObjId());
					upQuery.executeUpdate();
				} else if (objFaultFilterDefVo.getAction().equals(
						RMDServiceConstants.ADD)) {
					sequenceID++;
					if (Integer.parseInt(objFaultFilterDefVo.getDescObjId()) == 0) {
						hqrQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID, configId);
						hqrQuery.setParameter(RMDCommonConstants.TEMPLATE,
								templateNo);
						hqrQuery.setParameter(RMDCommonConstants.VERSION,
								versionId);
						templateId = hqrQuery.list().get(0).toString();

					} else {
						templateId = objFaultFilterDefVo.getDescObjId();
					}
					selectHibernateQuery.setParameter(
							RMDCommonConstants.CONFIG_VALUE, configValue);
					sourceId = selectHibernateQuery.list().get(0).toString();
					insertHibernateQuery.setParameter(
							RMDCommonConstants.USERNAME, userName);
					insertHibernateQuery.setParameter(
							RMDCommonConstants.VALUE1, Float
									.parseFloat(objFaultFilterDefVo
											.getValueFrom()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.VALUE2,
							Float.parseFloat(objFaultFilterDefVo.getValueTo()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.OPERATOR_FROM, Integer
									.parseInt(objFaultFilterDefVo
											.getOperatorFrom()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.OPERATOR_TO, Integer
									.parseInt(objFaultFilterDefVo
											.getOperatorTo()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.PARAMETER_OBJID, Integer
									.parseInt(objFaultFilterDefVo
											.getParameterObjId()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.CONJUNCTION, Integer
									.parseInt(objFaultFilterDefVo
											.getConjunction()));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.SEQUENCE_ID, sequenceID);
					insertHibernateQuery.setParameter(
							RMDCommonConstants.DEF_OBJID,
							Integer.parseInt(templateId));
					insertHibernateQuery.setParameter(
							RMDCommonConstants.SOURCE_ID,
							Integer.parseInt(sourceId));
					insertHibernateQuery.executeUpdate();
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

	/**
	 * @Author:
	 * @param:List<FaultRangeDefVo>
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for save the FRD Template details.
	 */
	@Override
	public String saveFRDTemplate(List<FaultRangeDefVo> lstFaultFilterDefVo)
			throws RMDDAOException {
		Session session = null;
		StringBuilder frdUpdateQuery = new StringBuilder();
		StringBuilder frdInsertQuery = new StringBuilder();
		StringBuilder frdInsertQuery1 = new StringBuilder();
		StringBuilder frdInsertQuery2 = new StringBuilder();
		StringBuilder faultDefInsertQuery = new StringBuilder();
		StringBuilder maxRangeQuery = new StringBuilder();
		StringBuilder fetchObjQuery = new StringBuilder();
		StringBuilder duplicateObjQuery = new StringBuilder();
		StringBuilder fetchCurrentCountQuery = new StringBuilder();
		Query updatehibernateQuery = null, maxRangeHibernateQuery = null, duplicateHibernateQuery = null, insertHibernateQuery = null, insertHibernateQuery1 = null, insertHibernateQuery2 = null, selectHibernateQuery = null, newInsertQuery = null, hqrQuery = null, upQuery = null;
		Transaction transaction = null;
		List<Object[]> duplicateResultList = null;
		boolean flag = true, flag2 = true;
		String status = RMDServiceConstants.SUCCESS, templateId = null, versionId = null, configId = null;
		Object template = null, count = null, range = null;

		int objId = 0, currentCount = 0, maxRange = 0, chkobjId = 0;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			frdUpdateQuery
					.append(" update gets_omi_flt_range_def set last_updated_date = sysdate,  last_updated_by = :userName, status = :status,flt_range_def_desc=:title ");
			frdUpdateQuery
					.append("where flt_range_def2ctl_cfg = :ctrlCfgObjId and flt_range_def_template=:template and flt_range_def_version=:version ");
			updatehibernateQuery = session.createSQLQuery(frdUpdateQuery
					.toString());

			fetchObjQuery
					.append("SELECT objid FROM gets_omi_flt_range_def WHERE flt_range_def2ctl_cfg =:ctrlCfgObjId AND flt_range_def_template =:template AND flt_range_def_version =:version ");
			hqrQuery = session.createSQLQuery(fetchObjQuery.toString());

			fetchCurrentCountQuery
					.append("SELECT COUNT(dtl.objid) FROM gets_omi_flt_range_def def, gets_omi_flt_range_dtl dtl WHERE def.objid = dtl.flt_range_dtl2flt_range_def ");
			fetchCurrentCountQuery
					.append("AND def.flt_range_def2ctl_cfg =:ctrlCfgObjId AND def.flt_range_def_template =:template AND def.flt_range_def_version =:version ");
			selectHibernateQuery = session
					.createSQLQuery(fetchCurrentCountQuery.toString());
			maxRangeQuery
					.append("SELECT value FROM gets_rmd_sysparm WHERE UPPER(title) = UPPER('Maximum Fault Ranges') ");
			maxRangeHibernateQuery = session.createSQLQuery(maxRangeQuery
					.toString());
			duplicateObjQuery
					.append("select distinct controller_source_id  from gets_omi_flt_range_dtl  where  controller_source_id = :ctrlSourceId and upper(fault_code_start) =upper(:faultCodeFrom) and upper(fault_code_end) =upper(:faultCodeTo) and flt_range_dtl2flt_range_def =:objId");
			duplicateHibernateQuery = session.createSQLQuery(duplicateObjQuery
					.toString());
			faultDefInsertQuery
					.append("insert into gets_omi_flt_range_def(objid, last_updated_date,last_updated_by, creation_date,  created_by, flt_range_def_template, flt_range_def_version, flt_range_def_desc, flt_range_def2ctl_cfg, status) ");
			faultDefInsertQuery
					.append("values(gets_omi_flt_range_def_seq.nextval,sysdate,:userName,sysdate,:userName,:template,:version,:title,:ctrlCfgObjId,:status)");
			newInsertQuery = session.createSQLQuery(faultDefInsertQuery
					.toString());
			frdInsertQuery
					.append("insert into gets_omi_flt_range_dtl(objid, last_updated_date,last_updated_by, creation_date,  created_by,controller_source_id, fault_code_start, sub_sys_start, fault_code_end, sub_sys_end,  edp_template, samples_pre, samples_post, sample_bias, flt_range_dtl2flt_range_def, edp_version) ");
			frdInsertQuery
					.append("values(gets_omi_flt_range_dtl_seq.nextval,sysdate,:userName,sysdate,:userName,:ctrlSourceId,:faultCodeFrom,:faultCodeStart,:faultCodeTo,:faultCodeEnd,:edpTemplate,:preValue,:postValue,:biasValue,:objId,:edpVersion)");
			insertHibernateQuery = session.createSQLQuery(frdInsertQuery
					.toString());
			frdInsertQuery1
					.append("insert into gets_omi_flt_range_dtl(objid, last_updated_date,last_updated_by, creation_date,  created_by,controller_source_id, fault_code_start, sub_id_start, fault_code_end, sub_id_end,  edp_template, samples_pre, samples_post, sample_bias, flt_range_dtl2flt_range_def, edp_version) ");
			frdInsertQuery1
					.append("values(gets_omi_flt_range_dtl_seq.nextval,sysdate,:userName,sysdate,:userName,:ctrlSourceId,:faultCodeFrom,:subIdFrom,:faultCodeTo,:subIdTo,:edpTemplate,:preValue,:postValue,:biasValue,:objId,:edpVersion)");
			insertHibernateQuery1 = session.createSQLQuery(frdInsertQuery1
					.toString());
			frdInsertQuery2
					.append("insert into gets_omi_flt_range_dtl(objid, last_updated_date,last_updated_by, creation_date,  created_by,controller_source_id, fault_code_start, fault_code_end, edp_template, samples_pre, samples_post, sample_bias, flt_range_dtl2flt_range_def, edp_version) ");
			frdInsertQuery2
					.append("values(gets_omi_flt_range_dtl_seq.nextval,sysdate,:userName,sysdate,:userName,:ctrlSourceId,:faultCodeFrom,:faultCodeTo,:edpTemplate,:preValue,:postValue,:biasValue,:objId,:edpVersion)");
			insertHibernateQuery2 = session.createSQLQuery(frdInsertQuery2
					.toString());
			outerloop: for (FaultRangeDefVo objFaultRangeDefVo : lstFaultFilterDefVo) {
				if (objFaultRangeDefVo.getAction().equals(
						RMDServiceConstants.UPDATE)
						|| objFaultRangeDefVo.getAction().equals(
								RMDServiceConstants.INSERT)) {
					templateId = objFaultRangeDefVo.getTemplate();
					versionId = objFaultRangeDefVo.getTempVersion();
					configId = objFaultRangeDefVo.getConfigId();
					hqrQuery.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
							configId);
					hqrQuery.setParameter(RMDCommonConstants.TEMPLATE,
							templateId);
					hqrQuery.setParameter(RMDCommonConstants.VERSION, versionId);
					chkobjId = hqrQuery.list().size();
					if (chkobjId == 0) {
						newInsertQuery.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultRangeDefVo.getUserName());
						newInsertQuery.setParameter(
								RMDCommonConstants.TEMPLATE,
								objFaultRangeDefVo.getTemplate());
						newInsertQuery.setParameter(RMDCommonConstants.VERSION,
								objFaultRangeDefVo.getTempVersion());
						newInsertQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID,
								objFaultRangeDefVo.getConfigId());
						newInsertQuery.setParameter(
								RMDCommonConstants.TITLE,
								ESAPI.encoder().decodeForHTML(
										objFaultRangeDefVo.getTitle()));
						newInsertQuery.setParameter(RMDCommonConstants.STATUS,
								objFaultRangeDefVo.getStatus());
						newInsertQuery.executeUpdate();
					} else {
						updatehibernateQuery.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultRangeDefVo.getUserName());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.TITLE,
								ESAPI.encoder().decodeForHTML(
										objFaultRangeDefVo.getTitle()));
						updatehibernateQuery.setParameter(
								RMDCommonConstants.STATUS,
								objFaultRangeDefVo.getStatus());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.VERSION,
								objFaultRangeDefVo.getTempVersion());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.TEMPLATE,
								objFaultRangeDefVo.getTemplate());
						updatehibernateQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID,
								objFaultRangeDefVo.getConfigId());
						updatehibernateQuery.executeUpdate();
					}
				} else if (objFaultRangeDefVo.getAction().equals(
						RMDServiceConstants.ADD)) {
					if (flag == true) {
						hqrQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID, configId);
						hqrQuery.setParameter(RMDCommonConstants.TEMPLATE,
								templateId);
						hqrQuery.setParameter(RMDCommonConstants.VERSION,
								versionId);
						objId = RMDCommonUtility.convertObjectToInt(hqrQuery
								.list().get(0));
						selectHibernateQuery.setParameter(
								RMDCommonConstants.CTRL_CFG_OBJID, configId);
						selectHibernateQuery.setParameter(
								RMDCommonConstants.TEMPLATE, templateId);
						selectHibernateQuery.setParameter(
								RMDCommonConstants.VERSION, versionId);
						currentCount = RMDCommonUtility
								.convertObjectToInt(selectHibernateQuery.list()
										.get(0));
						maxRange = RMDCommonUtility
								.convertObjectToInt(maxRangeHibernateQuery
										.list().get(0));
						flag = false;
					}
					duplicateHibernateQuery.setParameter(
							RMDCommonConstants.CTRL_SOURCE_ID,
							objFaultRangeDefVo.getCtrlSourceId());
					duplicateHibernateQuery.setParameter(
							RMDCommonConstants.FAULT_CODE_FROM,
							objFaultRangeDefVo.getFaultCodeFrom());
					duplicateHibernateQuery.setParameter(
							RMDCommonConstants.FAULT_CODE_TO,
							objFaultRangeDefVo.getFaultCodeTo());
					duplicateHibernateQuery.setParameter(
							RMDCommonConstants.OBJ_ID, objId);
					duplicateResultList = duplicateHibernateQuery.list();
					if (RMDCommonUtility
							.isCollectionNotEmpty(duplicateResultList)) {
						for (final Iterator<Object[]> obj = duplicateResultList
								.iterator(); obj.hasNext();) {
							status = "Fault Source "
									+ objFaultRangeDefVo.getFaultSource()
									+ ", from fault code "
									+ objFaultRangeDefVo.getFaultCodeFrom()
									+ " and to fault code "
									+ objFaultRangeDefVo.getFaultCodeTo()
									+ " are duplicate";
							break outerloop;
						}
					}

					if (objFaultRangeDefVo.getConfigValue().equals("AC6000")
							|| objFaultRangeDefVo.getConfigValue().equals(
									"ACCCA")
							|| objFaultRangeDefVo.getConfigValue().equals(
									"DCCCA")
							|| objFaultRangeDefVo.getConfigValue()
									.equals("CCA")
							|| objFaultRangeDefVo.getConfigValue()
									.equals("EC2")) {
						insertHibernateQuery.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultRangeDefVo.getUserName());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.CTRL_SOURCE_ID,
								objFaultRangeDefVo.getCtrlSourceId());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.FAULT_CODE_FROM,
								objFaultRangeDefVo.getFaultCodeFrom());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.FAULT_CODE_START,
								objFaultRangeDefVo.getFaultStart());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.FAULT_CODE_TO,
								objFaultRangeDefVo.getFaultCodeTo());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.FAULT_CODE_END,
								objFaultRangeDefVo.getFaultEnd());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.EDP_TEMPLATE,
								objFaultRangeDefVo.getEdpTemplate());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.PRE_VALUE,
								objFaultRangeDefVo.getPreValue());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.POST_VALUE,
								objFaultRangeDefVo.getPostValue());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.BIAS_VALUE,
								objFaultRangeDefVo.getBiasValue());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.EDP_VERSION,
								objFaultRangeDefVo.getVersion());
						insertHibernateQuery.setParameter(
								RMDCommonConstants.OBJ_ID, objId);
						if (currentCount < maxRange) {
							int i = insertHibernateQuery.executeUpdate();
							currentCount++;
						} else {
							status = RMDServiceConstants.MAXIMUM;
							break outerloop;
						}
					} else if ((objFaultRangeDefVo.getSubIdFrom() != null || objFaultRangeDefVo
							.getSubIdFrom() != "")
							&& (objFaultRangeDefVo.getSubIdTo() != null || objFaultRangeDefVo
									.getSubIdTo() != "")) {
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultRangeDefVo.getUserName());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.CTRL_SOURCE_ID,
								objFaultRangeDefVo.getCtrlSourceId());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.FAULT_CODE_FROM,
								objFaultRangeDefVo.getFaultCodeFrom());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.SUB_ID_FROM,
								objFaultRangeDefVo.getSubIdFrom());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.FAULT_CODE_TO,
								objFaultRangeDefVo.getFaultCodeTo());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.SUB_ID_TO,
								objFaultRangeDefVo.getSubIdTo());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.EDP_TEMPLATE,
								objFaultRangeDefVo.getEdpTemplate());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.PRE_VALUE,
								objFaultRangeDefVo.getPreValue());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.POST_VALUE,
								objFaultRangeDefVo.getPostValue());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.BIAS_VALUE,
								objFaultRangeDefVo.getBiasValue());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.EDP_VERSION,
								objFaultRangeDefVo.getVersion());
						insertHibernateQuery1.setParameter(
								RMDCommonConstants.OBJ_ID, objId);
						if (currentCount < maxRange) {
							int i = insertHibernateQuery1.executeUpdate();
							currentCount++;
						} else {
							status = RMDServiceConstants.MAXIMUM;
							break outerloop;
						}
					} else {
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.USERNAME,
								objFaultRangeDefVo.getUserName());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.CTRL_SOURCE_ID,
								objFaultRangeDefVo.getCtrlSourceId());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.FAULT_CODE_FROM,
								objFaultRangeDefVo.getFaultCodeFrom());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.FAULT_CODE_TO,
								objFaultRangeDefVo.getFaultCodeTo());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.EDP_TEMPLATE,
								objFaultRangeDefVo.getEdpTemplate());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.PRE_VALUE,
								objFaultRangeDefVo.getPreValue());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.POST_VALUE,
								objFaultRangeDefVo.getPostValue());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.BIAS_VALUE,
								objFaultRangeDefVo.getBiasValue());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.EDP_VERSION,
								objFaultRangeDefVo.getVersion());
						insertHibernateQuery2.setParameter(
								RMDCommonConstants.OBJ_ID, objId);
						if (currentCount < maxRange) {
							int i = insertHibernateQuery2.executeUpdate();
							currentCount++;
						} else {
							status = RMDServiceConstants.MAXIMUM;
							break outerloop;
						}

					}

				}
			}
			if(status.indexOf("duplicate") == -1){
				transaction.commit();
			}
		
			// status = RMDCommonConstants.SUCCESS;
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
			duplicateResultList = null;
			releaseSession(session);
		}
		return status;
	}

	/**
	 * @Author:
	 * @param:String
	 * @return:List<FaultDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for remove the FFD templates
	 */
	@Override
	public String removeFFDTemplate(List<FaultDetailsVO> lstFaultDetailsVO)
			throws RMDDAOException {
		Session session = null;
		StringBuilder removeQuery = new StringBuilder();
		Query removeHibernateQuery = null;
		Transaction transaction = null;
		List<Object[]> resultList = null;
		List<BigDecimal> queryList = null;
		int count = 0, bom = 0;
		String status = RMDServiceConstants.SUCCESS, objid = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			removeQuery
					.append("delete from gets_omi_flt_filter_dtl where objid=:strObjId");
			removeHibernateQuery = session.createSQLQuery(removeQuery
					.toString());
			for (FaultDetailsVO objFaultDetailsVO : lstFaultDetailsVO) {
				removeHibernateQuery.setParameter(RMDServiceConstants.OBJID,
						objFaultDetailsVO.getObjId());
				removeHibernateQuery.executeUpdate();

			}
			transaction.commit();
			status = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			transaction.rollback();
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_FFD_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return status;

	}

	/**
	 * @Author:
	 * @param:String
	 * @return:List<FaultDetailsVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for remove the FRD templates
	 */
	@Override
	public String removeFRDTemplate(List<FaultDetailsVO> lstFaultDetailsVO)
			throws RMDDAOException {
		Session session = null;
		StringBuilder removeQuery = new StringBuilder();
		Query removeHibernateQuery = null;
		Transaction transaction = null;
		List<Object[]> resultList = null;
		List<BigDecimal> queryList = null;
		int count = 0, bom = 0;
		String status = RMDServiceConstants.SUCCESS, objid = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			removeQuery
					.append("delete from gets_omi_flt_range_dtl where objid = :objId");
			removeHibernateQuery = session.createSQLQuery(removeQuery
					.toString());
			for (FaultDetailsVO objFaultDetailsVO : lstFaultDetailsVO) {
				removeHibernateQuery.setParameter(RMDCommonConstants.OBJ_ID,
						Integer.parseInt(objFaultDetailsVO.getObjId()));
				removeHibernateQuery.executeUpdate();

			}
			transaction.commit();
			status = RMDCommonConstants.SUCCESS;
		} catch (Exception e) {
			transaction.rollback();
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_FRD_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return status;

	}

	/**
	 * @Author:
	 * @param:String configId,String templateId,String configFile
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching maximum template version.
	 */
	@Override
	public String getMaximumVersion(String configId, String templateId,
			String configFile) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		Object parameterTitle = null;
		String maxVersion = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();

			if (configFile.equals(RMDCommonConstants.FFD_CFG_FILE)) {
				caseQry.append(" SELECT max(ffdef.flt_filter_version) FROM  gets_omi_flt_filter_def ffdef,gets_rmd_ctl_cfg ccfg WHERE  ffdef.flt_filter_def2ctl_cfg = ccfg.objid AND ccfg.objid = :configId AND ffdef.flt_filter_template = :template ");
			} else if (configFile.equals(RMDCommonConstants.FRD_CFG_FILE)) {
				caseQry.append(" select max(def.flt_range_def_version) \"version\"  from gets_omi_flt_range_def def, gets_rmd_ctl_cfg cfg  where def.flt_range_def2ctl_cfg = cfg.objid  and def.flt_range_def_template =:template and cfg.objid = :configId ");
			}

			caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CONFIG_ID, configId);
			caseHqry.setParameter(RMDCommonConstants.TEMPLATE, templateId);
			maxVersion = RMDCommonUtility.convertObjectToString(caseHqry.list()
					.get(0));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAXIMUM_VERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAXIMUM_VERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return maxVersion;
	}

	/**
	 * @Author:
	 * @param:String configId,String templateId
	 * @return:List<FaultValueVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching fault source
	 */
	@Override
	public List<FaultValueVO> getFaultSource(String configId, String configValue)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		Object parameterTitle[] = null;
		List<FaultValueVO> lstFaultValueVO = new ArrayList<FaultValueVO>();
		FaultValueVO faultValueVO = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			if (configValue.equals(RMDCommonConstants.DCCAB)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('DC_CAB','EFI','SMS','FLM','CMU')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.ACCABCE)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('AC_CAB_CE','PSC','EFI','SMS','FLM','CMU')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.ACCABCEA)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('AC_CAB_CEA','PSC','EFI','SMS','FLM','CMU')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.ACCAX)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('CAX','PSC','EFI','FLM','CMU','SMS')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.AC6000)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('AC6000','SMS','CMU','FLM')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.ACCCA)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('CMU','EFI','SMS','FLM','ACCCA')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configValue.equals(RMDCommonConstants.DCCCA)) {
				caseQry.append(" SELECT DISTINCT ctrl.objid, ctrl.controller_source_id, ctrl.controller_name FROM gets_rmd_ctl_cfg cfg, gets_rmd_ctl_cfg_src src, gets_rmd_controller ctrl ");
				caseQry.append(" WHERE cfg.objid = src.ctl_cfg_src2ctl_cfg AND src.controller_source_id = ctrl.controller_source_id AND cfg.objid = :ctrlCfgObjId AND UPPER(ctrl.controller_name) IN ('CMU','EFI','SMS','FLM','DCCCA')");
				caseHqry = session.createSQLQuery(caseQry.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			}
			if (caseHqry != null) {
				resultList = caseHqry.list();
			}

			if (resultList != null && resultList.size() > 0) {
				lstFaultValueVO = new ArrayList<FaultValueVO>(resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					parameterTitle = (Object[]) obj.next();
					faultValueVO = new FaultValueVO();

					faultValueVO.setObjId(RMDCommonUtility
							.convertObjectToString(parameterTitle[0]));
					faultValueVO.setFaultSource(RMDCommonUtility
							.convertObjectToString(parameterTitle[1]));
					faultValueVO.setCfgDetailDesc(RMDCommonUtility
							.convertObjectToString(parameterTitle[2]));

					lstFaultValueVO.add(faultValueVO);

				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAXIMUM_VERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAXIMUM_VERSION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultValueVO;
	}

	/**
	 * @Author:
	 * @param:String configId,String configFile
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the existing EFI templates
	 */
	@Override
	public String getCurrentTemplate(String configId, String configFile)
			throws RMDDAOException {
		// List<Object[]> resultList = null;
		Session session = null;
		// Object template = null;
		String currentTemplate = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFFDTemp = new StringBuilder();
			StringBuilder caseQryFRDTemp = new StringBuilder();
			StringBuilder caseQryAHCTemp = new StringBuilder();
			caseQryFFDTemp
					.append(" select nvl(max(flt_filter_template),0) from gets_omi_flt_filter_def where flt_filter_def2ctl_cfg = :ctrlCfgObjId ");
			caseQryFRDTemp
					.append(" select max(def.flt_range_def_template) \"template\"  from gets_omi_flt_range_def def, gets_rmd_ctl_cfg cfg  where def.flt_range_def2ctl_cfg = cfg.objid  and cfg.objid = :ctrlCfgObjId ");
			caseQryAHCTemp
					.append(" select nvl(max(AHC_TEMPLATE),0) from gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR where AHC_HDR2CTRCFG = :ctrlCfgObjId");
			if (configFile.equals(RMDCommonConstants.FFD_CFG_FILE)) {
				caseHqry = session.createSQLQuery(caseQryFFDTemp.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configFile.equals(RMDCommonConstants.FRD_CFG_FILE)) {
				caseHqry = session.createSQLQuery(caseQryFRDTemp.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			} else if (configFile.equals(RMDCommonConstants.AHC_CFG_FILE)) {
				caseHqry = session.createSQLQuery(caseQryAHCTemp.toString());
				caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID,
						configId);
			}

			currentTemplate = RMDCommonUtility.convertObjectToString(caseHqry
					.list().get(0));
		} catch (RMDDAOConnectionException ex) {
			//ex.printStackTrace();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			//e.printStackTrace();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return currentTemplate;
	}

	/**
	 * @Author:
	 * @param:String configId,String templateId,String versionId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the existing EFI templates
	 */
	@Override
	public String getCurrentStatus(String configId, String templateId,
			String versionId) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		Object status = null;
		String currentStatus = null, objid = null, statusText = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFFDTemp = new StringBuilder();
			StringBuilder caseQryFRDTemp = new StringBuilder();
			caseQryFFDTemp
					.append(" select def.status,def.objid  from gets_omi_flt_range_def def, gets_rmd_ctl_cfg cfg  where cfg.objid =  def.flt_range_def2ctl_cfg  and def.flt_range_def_template =:template and def.flt_range_def_version =:version and cfg.objid =:ctrlCfgObjId ");
			caseHqry = session.createSQLQuery(caseQryFFDTemp.toString());
			caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID, configId);
			caseHqry.setParameter(RMDCommonConstants.TEMPLATE, templateId);
			caseHqry.setParameter(RMDCommonConstants.VERSION, versionId);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpDetails = (Object[]) obj.next();
					statusText = RMDCommonUtility
							.convertObjectToString(edpDetails[0]);
					objid = RMDCommonUtility
							.convertObjectToString(edpDetails[1]);

				}
			}
			currentStatus = statusText + "~" + objid;
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_STATUS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_STATUS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return currentStatus;
	}

	/**
	 * @Author:
	 * @param:String configId,String templateId,String versionId
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the title value.
	 */
	@Override
	public String getTitle(String configId, String templateId, String versionId)
			throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		Object title = null;
		String currentTitle = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFFDTemp = new StringBuilder();
			StringBuilder caseQryFRDTemp = new StringBuilder();
			caseQryFFDTemp
					.append(" select flt_range_def_desc title from gets_omi_flt_range_def  where flt_range_def2ctl_cfg =:ctrlCfgObjId and flt_range_def_template =:template and flt_range_def_version =:version ");
			caseHqry = session.createSQLQuery(caseQryFFDTemp.toString());
			caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID, configId);
			caseHqry.setParameter(RMDCommonConstants.TEMPLATE, templateId);
			caseHqry.setParameter(RMDCommonConstants.VERSION, versionId);
			currentTitle = RMDCommonUtility.convertObjectToString(caseHqry
					.list().get(0));
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TITLE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TITLE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return currentTitle;
	}

	/**
	 * @Author:
	 * @param:String configId,String templateId,String versionId
	 * @return:List<FaultValueVO>
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the status
	 */
	@Override
	public List<FaultValueVO> getStatusDetails(String configId,
			String templateId, String versionId) throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		FaultValueVO objFaultValueVO = null;
		List<FaultValueVO> lstFaultValueVO = new ArrayList<FaultValueVO>();
		Object parameterTitle = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append(" SELECT ffdef.status, ffdef.objid, ffdef.flt_filter_desc FROM   gets_omi_flt_filter_def ffdef, gets_rmd_ctl_cfg ccfg ");
			caseQry.append(" WHERE  ccfg.objid = ffdef.flt_filter_def2ctl_cfg AND ffdef.flt_filter_template =:template AND ffdef.flt_filter_version = :version AND ccfg.objid = :ctrlCfgObjId ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.CTRL_CFG_OBJID, configId);
			caseHqry.setParameter(RMDCommonConstants.TEMPLATE, templateId);
			caseHqry.setParameter(RMDCommonConstants.VERSION, versionId);
			resultList = caseHqry.list();
			if (resultList != null && resultList.size() > 0) {
				lstFaultValueVO = new ArrayList<FaultValueVO>(resultList.size());
			}
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					final Object[] edpDetails = (Object[]) obj.next();
					objFaultValueVO = new FaultValueVO();
					objFaultValueVO.setStatus((RMDCommonUtility
							.convertObjectToString(edpDetails[0])));
					objFaultValueVO.setObjId((RMDCommonUtility
							.convertObjectToString(edpDetails[1])));
					objFaultValueVO.setCfgDesc((RMDCommonUtility
							.convertObjectToString(edpDetails[2])));
					lstFaultValueVO.add(objFaultValueVO);

				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATUS_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_STATUS_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return lstFaultValueVO;
	}

	/**
	 * @Author:
	 * @param:List<FaultRangeDefVo>
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for Check fault range details.
	 */
	@Override
	public String checkFaultRange(List<FaultRangeDefVo> lstFaultFilterDefVo)
			throws RMDDAOException {
		Session session = null;
		StringBuilder selectQuery = new StringBuilder();
		Query selectHibernateQuery = null;
		Transaction transaction = null;
		List<Object[]> resultList = null;
		String status = RMDCommonConstants.SUCCESS, objid = null, statusMessage = null, faultCode1 = null, faultCode2 = null;
		String s7 = "invalid", s8 = "invalid", s9 = "invalid", faultCodeFrom = null, faultCodeEnd = null;
		try {
			session = getHibernateSession();
			transaction = session.getTransaction();
			transaction.begin();
			selectQuery
					.append("select to_number(flt_range_min,'xxxxxx'), to_number(flt_range_max,'xxxxxx'),  to_number(:faultCodeFrom,'xxxxxx') , to_number(:faultCodeTo,'xxxxxx') from gets_rmd_ctl_flt_range where controller_source_id =:sourceId");
			selectHibernateQuery = session.createSQLQuery(selectQuery
					.toString());
			for (FaultRangeDefVo objFaultDetailsVO : lstFaultFilterDefVo) {
				faultCodeFrom = objFaultDetailsVO.getFaultCodeFrom();
				faultCodeEnd = objFaultDetailsVO.getFaultCodeTo();
				faultCode1 = objFaultDetailsVO.getFaultStart() + ""
						+ objFaultDetailsVO.getFaultCodeFrom();
				faultCode2 = objFaultDetailsVO.getFaultEnd() + ""
						+ objFaultDetailsVO.getFaultCodeTo();
				selectHibernateQuery.setParameter(
						RMDServiceConstants.FAULT_CODE_FROM, faultCode1);
				selectHibernateQuery.setParameter(
						RMDServiceConstants.FAULT_CODE_TO, faultCode2);
				selectHibernateQuery.setParameter(RMDCommonConstants.SOURCE_ID,
						objFaultDetailsVO.getFaultSource());
				resultList = selectHibernateQuery.list();
				if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
					for (final Iterator<Object[]> obj = resultList.iterator(); obj
							.hasNext();) {
						final Object[] edpDetails = (Object[]) obj.next();
						int value1 = 0, value2 = 0, value3 = 0, value4 = 0;
						value1 = RMDCommonUtility
								.convertObjectToInt(edpDetails[0]);
						value2 = RMDCommonUtility
								.convertObjectToInt(edpDetails[1]);
						value3 = RMDCommonUtility
								.convertObjectToInt(edpDetails[2]);
						value4 = RMDCommonUtility
								.convertObjectToInt(edpDetails[3]);
						if (s8.equals("valid") && s9.equals("invalid")) {
							s8 = "invalid";
						}
						if (s8.equals("invalid") && s9.equals("valid")) {
							s9 = "invalid";
						}
						if (value3 >= value1 && value3 <= value2
								&& s8.equals("invalid")) {
							s8 = "valid";
						}
						if (value4 <= value2 && value4 >= value1
								&& s9.equals("invalid")) {
							s9 = "valid";
						}
						if (objFaultDetailsVO.getFaultStart().compareTo(
								objFaultDetailsVO.getFaultEnd()) > 0) {
							s8 = "invalid";
						}
						if (objFaultDetailsVO.getFaultStart().equals(
								objFaultDetailsVO.getFaultEnd())
								&& value3 > value4) {
							s8 = "invalid";
						}

					}

				}
			}
			if (s8.equals("valid") && s9.equals("valid")) {
				s7 = "valid";
			} else {
				s7 = "invalid";
			}
			if (s8.equals("invalid")) {
				status = "The from Fault Code '" + faultCodeFrom
						+ "' is not valid";
			}
			if (s9.equals("invalid")) {
				status = "The to Fault Code '" + faultCodeEnd
						+ "' is not valid";
			}
			if (s8.equals("invalid") && s9.equals("invalid")) {
				status = "The from and/or to Fault Codes are not valid";
			}
			statusMessage = s7 + "~" + status;

		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_REMOVE_FFD_TEMPLATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			resultList = null;
			releaseSession(session);
		}
		return statusMessage;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching the maximum count of
	 *               parameter.
	 */
	@Override
	public String getMaxParameterCount() throws RMDDAOException {
		List<Object[]> resultList = null;
		Session session = null;
		Object count = null;
		String maxParameterCount = null;
		Query caseHqry = null;
		try {
			session = getHibernateSession();
			StringBuilder caseQryFFDTemp = new StringBuilder();
			StringBuilder caseQryFRDTemp = new StringBuilder();
			caseQryFFDTemp
					.append("select value from gets_rmd_sysparm where title ='maximum_fault_filter_entries'");
			caseHqry = session.createSQLQuery(caseQryFFDTemp.toString());
			maxParameterCount = caseHqry.list().get(0).toString();
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_PARAMETER_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MAX_PARAMETER_COUNT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return maxParameterCount;
	}
	
	/**
	 * @Author:
	 * @param:
	 * @return:String
	 * @throws:RMDDAOException
	 * @Description: This method is used for fetching Templates across all configs based on the selected search criteria.
	 *               
	 */
	@Override
	public List<TemplateInfoVO> getTemplateReport(final String templateReportQuery,final TemplateSearchVO templateSearchVO) throws RMDDAOException{
		Session session = null;
		Query templateQuery=null;
		List<Object[]> resultList = null;
		TemplateInfoVO templateInfoVO=null;
		List<TemplateInfoVO> lstTemplateDetails=null;
		try {
			session = getHibernateSession();
			templateQuery=session.createSQLQuery(templateReportQuery);
			if(null!= templateSearchVO.getCtrlCfgObjId()){
				templateQuery.setParameter(RMDCommonConstants.CONTROLLER_CONFIG_OBJID,templateSearchVO.getCtrlCfgObjId());
			}
			if(null!=templateSearchVO.getAssetNumber())
				templateQuery.setParameter(RMDCommonConstants.VEHICLE_NO, templateSearchVO.getAssetNumber());
				
			if(null!=templateSearchVO.getAssetGroupName())
				templateQuery.setParameter(RMDCommonConstants.VEHICLE_HEADER,templateSearchVO.getAssetGroupName());			
			
			if(null!=templateSearchVO.getCustomerId())
				templateQuery.setParameter(RMDCommonConstants.ORG_NAME, templateSearchVO.getCustomerId());
			
			if(null!=templateSearchVO.getTemplateName()){
				templateQuery.setParameter(RMDCommonConstants.TEMPLATE_NAME, RMDServiceConstants.PERCENTAGE+templateSearchVO.getTemplateName()+ RMDServiceConstants.PERCENTAGE);
			}
			if(null!=templateSearchVO.getTemplateNo()){
				templateQuery.setParameter(RMDCommonConstants.TEMPLATE_NO, templateSearchVO.getTemplateNo());
			}
			if(null!=templateSearchVO.getTemplateVersion()){
				templateQuery.setParameter(RMDCommonConstants.TEMPLATE_VERSION, templateSearchVO.getTemplateVersion());
			}
			if(null!=templateSearchVO.getTemplateStatus()){
				templateQuery.setParameter(RMDCommonConstants.TEMPLATE_STATUS, templateSearchVO.getTemplateStatus());
			}
			
			resultList = templateQuery.list();
			lstTemplateDetails=new ArrayList<TemplateInfoVO>(resultList.size());
			if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
				for (final Object[] objects : resultList) {
					templateInfoVO=new TemplateInfoVO();
					templateInfoVO.setCfgTemplateObjid(RMDCommonUtility.convertObjectToString(objects[0]));
					templateInfoVO.setCfgFile(RMDCommonUtility.convertObjectToString(objects[1]));
					templateInfoVO.setTemplate(RMDCommonUtility.convertObjectToString(objects[2]));
					templateInfoVO.setVersion(RMDCommonUtility.convertObjectToString(objects[3]));
					templateInfoVO.setControllerConfig(RMDCommonUtility.convertObjectToString(objects[4]));
					templateInfoVO.setTemplateStatus(RMDCommonUtility.convertObjectToString(objects[6]));
					templateInfoVO.setTemplateDescription(RMDCommonUtility.convertObjectToString(objects[7]));
					templateInfoVO.setOffboardStatus(RMDCommonUtility.convertObjectToString(objects[8]));
					templateInfoVO.setOnboardStatus(RMDCommonUtility.convertObjectToString(objects[9]));
					templateInfoVO.setRoadNumber(RMDCommonUtility.convertObjectToString(objects[10]));
					templateInfoVO.setRoadNumberHeader(RMDCommonUtility.convertObjectToString(objects[11]));
					templateInfoVO.setCustomerName(RMDCommonUtility.convertObjectToString(objects[12]));
					templateInfoVO.setCustomerID(RMDCommonUtility.convertObjectToString(objects[13]));
					templateInfoVO.setCtrlCfgObjId(RMDCommonUtility.convertObjectToString(objects[14]));
					templateInfoVO.setOnboardStatusDate(RMDCommonUtility.convertObjectToString(objects[15]));
					templateInfoVO.setOffboardStatusDate(RMDCommonUtility.convertObjectToString(objects[16]));
					lstTemplateDetails.add(templateInfoVO);
				}
			}
		} catch (RMDDAOConnectionException ex) {
			LOG.error("RMDDAOConnectionException in getTemplateReport() "+ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error("Exception in getTemplateReport() "+e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstTemplateDetails;
	}

	@Override
	public String checkIfRCITemplateExists(final TemplateInfoVO templateInfo) throws RMDDAOException {
		final String tempExistsQry="SELECT count(1) FROM GETS_OMI_CFG_DEF WHERE CFG_TYPE = 'RCI' AND CFG_DEF_TEMPLATE = :templateNo "
				+ " AND CFG_DEF_VERSION  = :templateVersion AND CFG_DEF2CTL_CFG in  (select objid from gets_rmd_ctl_cfg where controller_cfg in ('ACCCA','DCCCA')) ";
		final String fcExistsQry ="SELECT count(1) FROM GETS_RMD_FAULT_CODES WHERE FAULT_CODE=:faultCode AND FAULT_ORIGIN IN ('ACCCA','DCCCA')";
		Session session = null;
		int recordCount=0;
		List<Object> resultList;
		String status = RMDCommonConstants.SUCCESS;
		try {
			session=getHibernateSession();
			final Query fcExistsHQry = session.createSQLQuery(fcExistsQry);
            if(null!=templateInfo.getFaultCode()){
                fcExistsHQry.setParameter(RMDCommonConstants.FAULT_CODE_ID, templateInfo.getFaultCode());
            }
            resultList = fcExistsHQry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                recordCount = RMDCommonUtility.convertObjectToInt(resultList.get(0));
            }
            
            if(recordCount>0){           
    			final Query caseHqry = session.createSQLQuery(tempExistsQry);
    			if(null!=templateInfo.getTemplate()){
    				caseHqry.setParameter(RMDCommonConstants.TEMPLATE_NO, templateInfo.getTemplate());
    			}
    			if(null!=templateInfo.getVersion()){
    				caseHqry.setParameter(RMDCommonConstants.TEMPLATE_VERSION, templateInfo.getVersion());
    			}
    			resultList = caseHqry.list();
                if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
                    if(RMDCommonUtility.convertObjectToInt(resultList.get(0))>0){
                        status =RMDCommonConstants.TEMPLATE_EXISTS;
                    }
                }
    			
            }else{
                status =RMDCommonConstants.FAULT_CODE_DOES_NOT_EXISTS;
            }
		} catch (RMDDAOConnectionException ex) {
			LOG.error("RMDDAOConnectionException in checkIfRCITemplateExists() "+ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_IF_RCI_TEMPLATE_EXISTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error("Exception in checkIfRCITemplateExists() "+e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_IF_RCI_TEMPLATE_EXISTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return status;
	}

	@Override
	public String saveRCITemplate(TemplateInfoVO templateInfo)
			throws RMDDAOException {
		final StringBuilder rciTemplateInsert=new StringBuilder();
		Session session = null;
		String result=RMDCommonConstants.FAIL;
		Transaction transaction = null;
		try {
			rciTemplateInsert.append(" INSERT INTO GETS_OMI.GETS_OMI_CFG_DEF( OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,CFG_TYPE,CFG_DEF_TEMPLATE,CFG_DEF_VERSION,"
					+ " CFG_DEF_DESC,CFG_DEF2CTL_CFG,STATUS,MESSAGE_FILE_CREATED,MESSAGE_FILE_NAME,RCI_FLT_CODE_ID,MESSAGE_FILE_CONTENT) ");
			rciTemplateInsert.append(" VALUES (GETS_OMI.GETS_OMI_CFG_DEF_SEQ.NEXTVAL ,SYSDATE,:userName,SYSDATE,:userName,'RCI',:templateNo,:templateVersion,:DESCRIPTION,"
			        + "(select objid from gets_rmd_ctl_cfg where controller_cfg =:controller_cfg ) ,:status, 'Y',:templateName,(SELECT OBJID FROM GETS_RMD_FAULT_CODES WHERE FAULT_CODE = :faultCode AND FAULT_ORIGIN = :controller_cfg),:templateContent) ");
			session=getHibernateSession();
			transaction=session.beginTransaction();
			final Query rciInsertQuery = session.createSQLQuery(rciTemplateInsert.toString());
			rciInsertQuery.setParameter(RMDCommonConstants.USER_NAME, templateInfo.getUserName());
			rciInsertQuery.setParameter(RMDCommonConstants.TEMPLATE_NO, templateInfo.getTemplate());
			rciInsertQuery.setParameter(RMDCommonConstants.TEMPLATE_VERSION, templateInfo.getVersion());
			rciInsertQuery.setParameter(RMDCommonConstants.DESCRIPTION, templateInfo.getTemplateDescription());
			rciInsertQuery.setParameter(RMDCommonConstants.STATUS, templateInfo.getTemplateStatus());
			rciInsertQuery.setParameter(RMDCommonConstants.TEMPLATE_NAME, templateInfo.getTemplateFileName());	
			rciInsertQuery.setParameter(RMDCommonConstants.FAULT_CODE_ID, templateInfo.getFaultCode());
			rciInsertQuery.setParameter(RMDCommonConstants.TEMPLATE_CONTENT, templateInfo.getTemplateFileContent().getBytes());
			List<String> controllerConfig=Arrays.asList("ACCCA","DCCCA");
			
			for(String ctrlCfg : controllerConfig){
			    rciInsertQuery.setParameter(RMDCommonConstants.CONTROLLER_CFG, ctrlCfg);
			    rciInsertQuery.executeUpdate();
			}
			transaction.commit();
			result=RMDCommonConstants.SUCCESS;
		} catch (RMDDAOConnectionException ex) {
            if(null!=transaction){
                transaction.rollback(); 
            }   
            result=RMDCommonConstants.FAIL;
            LOG.error("Exception in saveRCITemplate() "+ex.getMessage());
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RCI_TEMPLATE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            if(null!=transaction){
                transaction.rollback(); 
            }
            result=RMDCommonConstants.FAIL;
            LOG.error("Exception in saveRCITemplate() "+e.getMessage());
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_RCI_TEMPLATE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
		return result;
	}
	
	/*public String saveRCITemplate(final TemplateInfoVO templateInfo)
			throws RMDDAOException {
		Session session = null;
		GetsOmiCfgHVO getsomiCfgHVO=null;
		Criteria criteria=null;
		Transaction transaction = null;
		String result=RMDCommonConstants.FAIL;
		try {
			session=getHibernateSession();
			transaction=session.beginTransaction();
			criteria=session.createCriteria(GetsRmdCtlCfgHVO.class);
			List<String> controllerConfig=Arrays.asList("ACCCA","DCCCA");
			criteria.add(Restrictions.in("controllerCfg", controllerConfig));
			List<GetsRmdCtlCfgHVO> objGetsRmdCtlCfgHVO=criteria.list();
			for (GetsRmdCtlCfgHVO getsRmdCtlCfgHVO : objGetsRmdCtlCfgHVO) {
				if(null!=templateInfo){
					getsomiCfgHVO=new GetsOmiCfgHVO();
					getsomiCfgHVO.setCreatedBy("OMD_UI");
					getsomiCfgHVO.setLastUpdatedBy("OMD_UI");
					getsomiCfgHVO.setConfigType(RMDCommonConstants.RCI_CFG_FILE);
					getsomiCfgHVO.setConfigDefDescription(templateInfo.getTemplateDescription());
					getsomiCfgHVO.setConfigType(templateInfo.getCfgFile());
					getsomiCfgHVO.setConfigDefVersion(Integer.parseInt(templateInfo.getVersion()));
					getsomiCfgHVO.setMessageFileCreated("Y");
					getsomiCfgHVO.setMessageFileContent(templateInfo.getTemplateFileContent().getBytes());
					getsomiCfgHVO.setMessageFileName(templateInfo.getTemplateFileName());
					getsomiCfgHVO.setConfigDefTemplate(Integer.parseInt(templateInfo.getTemplate()));
					getsomiCfgHVO.setLastUpdatedDate(new Date());
					getsomiCfgHVO.setCreationDate(new Date());	
					getsomiCfgHVO.setRciFaultCodeId(templateInfo.getFaultCodeVO().getFaultCodeObjid());
					getsomiCfgHVO.setGetsrmdCtlCfg(getsRmdCtlCfgHVO);
					session.save(getsomiCfgHVO);
					session.flush();
					result=RMDCommonConstants.SUCCESS;
				}
			}			
			transaction.commit();
			
		} catch (RMDDAOConnectionException ex) {
			if(null!=transaction){
				transaction.rollback();	
			}	
			result=RMDCommonConstants.FAIL;
			LOG.error("Exception in saveRCITemplate() "+ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			if(null!=transaction){
				transaction.rollback();	
			}
			result=RMDCommonConstants.FAIL;
			LOG.error("Exception in saveRCITemplate() "+e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TEMPLATE_REPORT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return result;
	}*/
	
	
}
