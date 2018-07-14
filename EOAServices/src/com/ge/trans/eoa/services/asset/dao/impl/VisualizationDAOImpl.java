package com.ge.trans.eoa.services.asset.dao.impl;

/**
 * ============================================================
 * File : VisualizationDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.visualization.dao.impl
 * Author : iGATE Global Solutions Ltd.
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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.admin.dao.intf.PopupListAdminDAOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.VisualizationDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxVisualizationPlotInfoVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VehicleDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VirtualParametersVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsRequestVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationEventDataVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationGraphDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationMPParmNumVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.VisualizationParmDetailsVO;
import com.ge.trans.eoa.services.common.constants.FaultLogHelper;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.valueobjects.UnitOfMeasureVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants.DateConstants;
import com.ge.trans.rmd.common.valueobjects.GetSysLookupVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;


/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Sep 17, 2013
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class DAO implementation layer is used to fetch the
 *              details for visualization screen
 * @History :
 * 
 ******************************************************************************/
public class VisualizationDAOImpl extends BaseDAO implements VisualizationDAOIntf {

    private PopupListAdminDAOIntf popupListAdminDAO;
    Codec ORACLE_CODEC = new OracleCodec();

	public void setPopupListAdminDAO(PopupListAdminDAOIntf popupListAdminDAO) {
		this.popupListAdminDAO = popupListAdminDAO;
	}

	/**
	 * This method is used for fetching graphDetails of visualization screen
	 * 
	 * @param VisualizationGraphDetailsVO
	 * @return List<VisualizationGraphDetailsVO>
	 * @throws RMDDAOException
	 */
	@Override
	public List<VisualizationGraphDetailsVO> getGraphDetails(
			VisualizationGraphDetailsVO objDetailsVO) throws RMDDAOException {
		Session hibernateSession = null;
		VisualizationGraphDetailsVO objGraphDetailsVO = null;
		List<VisualizationGraphDetailsVO> arlGraphDetailsVO = new ArrayList<VisualizationGraphDetailsVO>();
		try {
			hibernateSession = getHibernateSession();
			StringBuilder strQry = new StringBuilder();
			strQry.append("SELECT GRAPH_NAME,SOURCE_TYPE,PARM,PARM_DESC,GRAPHS.SORT_ORDER,GRAPHS.CONTROLLER_CFG,GRAPHS_DTL.STACK_ORDER FROM GETS_RMD.GETS_RMD_OMD_VIZ_GRAPH_HDR GRAPHS,GETS_RMD.GETS_RMD_OMD_VIZ_GRAPH_DTL GRAPHS_DTL");
			strQry.append(" WHERE");
			strQry.append(" GRAPHS.GETS_RMD_OMD_VIZ_GRAPH_HDR_ID=GRAPHS_DTL.LINK_GRAPH AND GRAPHS.STATUS_CD=1 order by GRAPHS.SORT_ORDER,GRAPHS_DTL.SORT_ORDER ASC");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());

			qry.setFetchSize(10);
			List<Object[]> arlGraphDetails = qry.list();
			if (null != arlGraphDetails && !arlGraphDetails.isEmpty()) {
				for (Object[] obj : arlGraphDetails) {
					objGraphDetailsVO = new VisualizationGraphDetailsVO();
					objGraphDetailsVO.setGraphName(RMDCommonUtility.convertObjectToString(obj[0]));
					objGraphDetailsVO.setSourceType(RMDCommonUtility.convertObjectToString(obj[1]));
					objGraphDetailsVO.setGraphMPNum(RMDCommonUtility.convertObjectToString(obj[2]));
					objGraphDetailsVO.setGraphMPDesc(RMDCommonUtility.convertObjectToString(obj[3]));
					objGraphDetailsVO.setSortOrder(RMDCommonUtility.convertObjectToString(obj[4]));
					objGraphDetailsVO.setControllercfg(RMDCommonUtility.convertObjectToString(obj[5]));
					objGraphDetailsVO.setStackOrder(RMDCommonUtility.convertObjectToString(obj[6]));
					arlGraphDetailsVO.add(objGraphDetailsVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							objDetailsVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							objDetailsVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return arlGraphDetailsVO;
	}

	/**
	 * This method is used for fetching Visualization fault details based on
	 * source,source,assetNumer etc
	 * 
	 * @param VisualizationDetailsRequestVO
	 * @return List<VisualizationDetailsVO>
	 * @throws RMDDAOException
	 */
	@Override
	public List<VisualizationDetailsVO> getVisualizationDetails(
			VisualizationDetailsRequestVO objVisualizationDetailsRequestVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		List<VisualizationDetailsVO> arlDetailsVO = new ArrayList<VisualizationDetailsVO>();
		Map<String, Map<String, VisualizationDetailsVO>> parentParmDetailsMap = new HashMap<String, Map<String, VisualizationDetailsVO>>();
		Map<String, VisualizationDetailsVO> parmDetailsMap = new HashMap<String, VisualizationDetailsVO>();
		Map<String, String> filterLookupValues = new LinkedHashMap<String, String>();
		Map<String, List<String>> logicalOperators = new LinkedHashMap<String, List<String>>();
		List<String> simpleOperators = new ArrayList<String>();
		List<String> complexOperators = new ArrayList<String>();
		StringBuilder filterWhereQuery = new StringBuilder();
		String filterFromQuery = RMDCommonConstants.EMPTY_STRING;
		List<String> parmDisplayNames = new ArrayList<String>();
		List<String> lstStackOrder = new ArrayList<String>();
		Map<String, VisualizationDetailsVO> multiAssetVizDetails = new LinkedHashMap<String, VisualizationDetailsVO>();
		VisualizationParmDetailsVO objVisualizationParmDetailsVO = null;
		List<Long> assetObjidList = new ArrayList<Long>();
		Long assetObjid = 0L;
		boolean isMultiAsset = false;
		Map<String, String> assetObjidMp = null;
		try {
			hibernateSession = getHibernateSession();
			List<String> arlMpNumbers = objVisualizationDetailsRequestVO.getMpNumbers();
			String fromQuery = RMDCommonConstants.EMPTY_STRING;
			String whereQuery = RMDCommonConstants.EMPTY_STRING;
			String selectQuery = RMDCommonConstants.EMPTY_STRING;

			String controllerSrc = objVisualizationDetailsRequestVO.getControllerSrc();
			filterLookupValues = getVizFilterLookUpValues(RMDServiceConstants.VISUALIZATION_FILTER_PARM_COLUMN_NAMES);
			logicalOperators = getLogicalOperators(RMDServiceConstants.OMD_LOGICAL_OPERATORS);
			simpleOperators = logicalOperators.get(RMDServiceConstants.SIMPLE_OPERATORS);
			complexOperators = logicalOperators.get(RMDServiceConstants.COMPLEX_OPERATORS);

			parentParmDetailsMap = getERParmDetails();
			if (null != objVisualizationDetailsRequestVO.getAssetObjid()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAssetObjid())) {
				isMultiAsset = true;
				String[] strMultiAsset = objVisualizationDetailsRequestVO.getAssetObjid().split(",");
				assetObjidMp = new HashMap<String, String>();
				for (int i = 0; i < strMultiAsset.length; i++) {
					String[] assetDetails = strMultiAsset[i].split("~");
					assetObjidMp.put(assetDetails[1], assetDetails[0]);
					assetObjidList.add(Long.valueOf(assetDetails[1]));
				}
			} else {
				assetObjid = getAssetObjid(
						objVisualizationDetailsRequestVO.getCustomerId(),
						objVisualizationDetailsRequestVO.getAssetNumber(),
						objVisualizationDetailsRequestVO.getAssetGroupName(),
						objVisualizationDetailsRequestVO.getControllerCfg());
				assetObjidList.add(assetObjid);
			}
			if (null != assetObjidList && !assetObjidList.isEmpty()) {
				parmDetailsMap = parentParmDetailsMap.get(controllerSrc);
				for (Iterator iterator = arlMpNumbers.iterator(); iterator.hasNext();) {
					String strMpNumberWithStackOrder = (String) iterator.next();
					String[] arrMpNumberWithStackOrder = strMpNumberWithStackOrder.split("~");
					String mpNumber = arrMpNumberWithStackOrder[0];
					String stackOrder = arrMpNumberWithStackOrder[1];

					if (null != parmDetailsMap && !parmDetailsMap.isEmpty()) {
						VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(mpNumber);
						if (null != objVisualizationDetailsVO) {
							String columnName = objVisualizationDetailsVO.getColumnName();
							String tableName = objVisualizationDetailsVO.getTableName();

							parmDisplayNames.add(objVisualizationDetailsVO.getDisplayName());
							lstStackOrder.add(stackOrder);
							selectQuery = selectQuery + RMDCommonConstants.COMMMA_SEPARATOR + tableName + "." + columnName;

							if (RMDServiceConstants.CCA.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())
									|| RMDServiceConstants.ECU.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())) {

								if (!fromQuery.contains("GETS_TOOLS."+ tableName)) {
									fromQuery = fromQuery + RMDCommonConstants.COMMMA_SEPARATOR
											+ "GETS_TOOLS." + tableName + " " + tableName;
								}

								if (!whereQuery.contains(tableName + ".")) {
									whereQuery = whereQuery
											+ "REC.SOURCE_TYPE_CD=" + tableName
											+ ".SOURCE_TYPE_CD AND "
											+ "REC.PARM2VEHICLE=" + tableName
											+ ".PARM2VEHICLE AND "
											+ "REC.INCIDENT_TIMESTAMP="
											+ tableName
											+ ".INCIDENT_TIMESTAMP AND ";
								}
							}

						}
					}

				}

				filterFromQuery = filterERQuery(
						objVisualizationDetailsRequestVO, filterLookupValues,
						simpleOperators, complexOperators, parmDetailsMap,
						controllerSrc, fromQuery, whereQuery, filterWhereQuery);

				if (!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(selectQuery)
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(filterFromQuery)
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(whereQuery)) {

					StringBuilder strBufQry = new StringBuilder();
					strBufQry.append("SELECT REC.PARM2VEHICLE,TO_CHAR(REC.INCIDENT_TIMESTAMP,'MM/DD/YYYY HH24:MI:SS:FF3')" + selectQuery);

					if (RMDServiceConstants.CCA.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())
							|| RMDServiceConstants.ECU.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())) {
						strBufQry.append(" FROM GETS_TOOLS.GETS_TOOL_ENGINE_REC_HDR REC" + filterFromQuery);
						strBufQry.append(" WHERE " + whereQuery);
						strBufQry.append(" REC.PARM2VEHICLE IN(:assetNumber) ");
						strBufQry.append(filterWhereQuery.toString());
					}

					if (RMDServiceConstants.CCA.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())
							|| RMDServiceConstants.ECU.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())) {
						strBufQry.append(" AND REC.SOURCE_TYPE_CD IN(:sourceTypeCode)");
					}

					if (null != objVisualizationDetailsRequestVO.getFromDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getFromDate())
							&& null != objVisualizationDetailsRequestVO.getToDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getToDate())) {
						strBufQry.append(" AND REC.INCIDENT_TIMESTAMP BETWEEN TO_DATE(:fromDate, 'MM/DD/YYYY HH24.MI.SS') AND TO_DATE(:toDate, 'MM/DD/YYYY HH24.MI.SS') ORDER BY REC.INCIDENT_TIMESTAMP ASC");
					}
					Query parmdetailsQry = hibernateSession.createSQLQuery(strBufQry.toString());
					parmdetailsQry.setParameterList(RMDServiceConstants.ASSETNUMBER, assetObjidList);

					if (null != objVisualizationDetailsRequestVO.getFromDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getFromDate())
							&& null != objVisualizationDetailsRequestVO.getToDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getToDate())) {
						parmdetailsQry.setParameter(RMDServiceConstants.FROM_DATE, objVisualizationDetailsRequestVO.getFromDate());
						parmdetailsQry.setParameter(RMDServiceConstants.TO_DATE, objVisualizationDetailsRequestVO.getToDate());
					}
					if (RMDServiceConstants.CCA.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())
							|| RMDServiceConstants.ECU.equalsIgnoreCase(objVisualizationDetailsRequestVO.getControllerSrc())) {
						List<String> arlSourceTypeCode = Arrays.asList(objVisualizationDetailsRequestVO.getSourceTypeCd().split(","));
						parmdetailsQry.setParameterList(RMDServiceConstants.SOURCE_TYPE_CODE, arlSourceTypeCode);
					}

					if (null != objVisualizationDetailsRequestVO.getLocoState()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getLocoState())
							&& filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.LOCO_STATE)) {
						
						List<String> arlLocoState = Arrays.asList(objVisualizationDetailsRequestVO.getLocoState().split(","));
						parmdetailsQry.setParameterList(RMDServiceConstants.LOCO_STATE_INPUT, arlLocoState);
					}

					if (null != objVisualizationDetailsRequestVO.getNotchValue()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getNotchValue())
							&& filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_	+ RMDServiceConstants.NOTCH)) {

						List<String> arlNotch = Arrays.asList(objVisualizationDetailsRequestVO.getNotchValue().split(","));
						parmdetailsQry.setParameterList(RMDServiceConstants.NOTCH_INPUT, arlNotch);
					}

					if (null != objVisualizationDetailsRequestVO.getAmbientTempOperator()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempOperator())
							&& null != objVisualizationDetailsRequestVO.getAmbientTempValue1()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempValue1())
							&& filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_	+ RMDServiceConstants.AMBIENT_TEMP)) {

						parmdetailsQry.setParameter(RMDServiceConstants.AMBIENT_TEMP_INPUT1,
								objVisualizationDetailsRequestVO.getAmbientTempValue1());
						if (complexOperators.contains(objVisualizationDetailsRequestVO.getAmbientTempOperator())) {
							parmdetailsQry.setParameter(RMDServiceConstants.AMBIENT_TEMP_INPUT2,
											objVisualizationDetailsRequestVO.getAmbientTempValue2());
						}
					}

					if (null != objVisualizationDetailsRequestVO.getEngineGHPOperator()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPOperator())
							&& null != objVisualizationDetailsRequestVO.getEngineGHPValue1()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPValue1())) {
						if (filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_	+ RMDServiceConstants.ENGINE_GHP)) {

							parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_GHP_INPUT1, objVisualizationDetailsRequestVO.getEngineGHPValue1());
							if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {
								parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_GHP_INPUT2, objVisualizationDetailsRequestVO.getEngineGHPValue2());
							}
						}
					}

					if (null != objVisualizationDetailsRequestVO.getEngineSpeedOperator()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedOperator())
							&& null != objVisualizationDetailsRequestVO.getEngineSpeedValue1()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedValue1())) {
						if (filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_+  RMDServiceConstants.ENGINE_SPEED)) {

							parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_SPEED_INPUT1,
									objVisualizationDetailsRequestVO.getEngineSpeedValue1());
							if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineSpeedOperator())) {
								parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_SPEED_INPUT2,
												objVisualizationDetailsRequestVO.getEngineSpeedValue2());
							}

						}
					}

					parmdetailsQry.setFetchSize(500);
					List<Object[]> arlParmDetails = parmdetailsQry.list();

					if (null != arlParmDetails && !arlParmDetails.isEmpty()) {

						for (Object[] object : arlParmDetails) {
							String strAssetObjid = RMDCommonUtility.convertObjectToString(object[0]);
							String assetNumberWithGrpName = RMDCommonConstants.EMPTY_STRING;
							DateFormat zoneFormater = new SimpleDateFormat(DateConstants.MMddyyyyHHmmss);
							String incidentTime = RMDCommonUtility.convertObjectToString(object[1]);
							GregorianCalendar objGregorianCalendar = new GregorianCalendar();
							objGregorianCalendar.setTime(zoneFormater.parse(incidentTime));
							RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, RMDCommonConstants.DateConstants.GMT);
							for (int i = 2; i < object.length; i++) {
								String displayName = RMDCommonConstants.EMPTY_STRING;
								if (isMultiAsset) {
									assetNumberWithGrpName = assetObjidMp.get(strAssetObjid);
									displayName = assetNumberWithGrpName + RMDCommonConstants.UNDERSCORE + parmDisplayNames.get(i - 2);
								} else {
									displayName = parmDisplayNames.get(i - 2);
								}
								String stackOrderNum = lstStackOrder.get(i - 2);
								if (multiAssetVizDetails.containsKey(displayName)) {
									VisualizationDetailsVO objDetailsVO = multiAssetVizDetails.get(displayName);
									objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
									objVisualizationParmDetailsVO.setOccurTime(objGregorianCalendar.getTimeInMillis());
									objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									objDetailsVO.getArlParmdetails().add(objVisualizationParmDetailsVO);
								} else {
									VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
									objDetailsVO.setDisplayName(displayName);
									objDetailsVO.setStackOrder(stackOrderNum);
									objDetailsVO.setAsset(assetNumberWithGrpName);
									objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
									objVisualizationParmDetailsVO.setOccurTime(objGregorianCalendar.getTimeInMillis());
									objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									objDetailsVO.getArlParmdetails().add(objVisualizationParmDetailsVO);
									multiAssetVizDetails.put(displayName, objDetailsVO);

								}
							}
						}

					} 
					
						for (int i = 0; i < parmDisplayNames.size(); i++) {
							for (int j = 0; j < assetObjidList.size(); j++) {

								String strAssetObjid = String.valueOf(assetObjidList.get(j));
								String assetNumberWithGrpName = RMDCommonConstants.EMPTY_STRING;
								String displayName = RMDCommonConstants.EMPTY_STRING;
								if (isMultiAsset) {
									assetNumberWithGrpName = assetObjidMp.get(strAssetObjid);
									displayName = assetNumberWithGrpName
											+ RMDCommonConstants.UNDERSCORE
											+ parmDisplayNames.get(i);
								} else {
									displayName = parmDisplayNames.get(i);
								}
								String stackOrderNum = lstStackOrder.get(i);
								if (!multiAssetVizDetails.containsKey(displayName)) {

									VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
									objDetailsVO.setDisplayName(displayName);
									objDetailsVO.setStackOrder(stackOrderNum);
									objDetailsVO.setAsset(assetNumberWithGrpName);
									multiAssetVizDetails.put(displayName, objDetailsVO);

								}
							}

						}

					arlDetailsVO = new ArrayList<VisualizationDetailsVO>(multiAssetVizDetails.values());
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							objVisualizationDetailsRequestVO.getUserLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							objVisualizationDetailsRequestVO.getUserLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return arlDetailsVO;

	}
	
	/**
	 * This method is used for forming the Query for Engine Recorder based on filter
	 * 
	 * @param 
	 * @return String
	 * @throws RMDDAOException
	 */
	public String filterERQuery(VisualizationDetailsRequestVO objVisualizationDetailsRequestVO,
			Map<String, String> filterLookupValues,
			List<String> simpleOperators, List<String> complexOperators,
			Map<String, VisualizationDetailsVO> parmDetailsMap,
			String controllerSrc, String fromQuery, String whereQuery,
			StringBuilder filterWhereQuery) throws Exception {
		try {
			String filterColumnName = RMDCommonConstants.EMPTY_STRING;

			if (null != objVisualizationDetailsRequestVO.getLocoState()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getLocoState())
					&& filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.LOCO_STATE)) {
				
					filterColumnName = filterLookupValues.get(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.LOCO_STATE);
					fromQuery = filterERFromQuery(filterColumnName, parmDetailsMap, controllerSrc, fromQuery, whereQuery, filterWhereQuery);
					filterWhereQuery.append(" AND " + filterColumnName + " IN(:locoState)");
			}

			if (null != objVisualizationDetailsRequestVO.getNotchValue()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getNotchValue())
					&& filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.NOTCH)) {
				
					filterColumnName = filterLookupValues.get(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.NOTCH);
					fromQuery = filterERFromQuery(filterColumnName, parmDetailsMap, controllerSrc, fromQuery, whereQuery, filterWhereQuery);
					filterWhereQuery.append(" AND " + filterColumnName + " IN(:notch)");
			}

			if (null != objVisualizationDetailsRequestVO.getAmbientTempOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempOperator())
					&& null != objVisualizationDetailsRequestVO.getAmbientTempValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.AMBIENT_TEMP)) {
					filterColumnName = filterLookupValues.get(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.AMBIENT_TEMP);
					fromQuery = filterERFromQuery(filterColumnName, parmDetailsMap, controllerSrc, fromQuery,
							whereQuery, filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getAmbientTempOperator())) {
						filterWhereQuery.append(" AND " + filterColumnName
								+ objVisualizationDetailsRequestVO.getAmbientTempOperator() + ":ambTempVal1");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getAmbientTempOperator())) {

						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getAmbientTempOperator(),
								filterColumnName, RMDServiceConstants.AMBIENT_TEMP_INPUT1, RMDServiceConstants.AMBIENT_TEMP_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}

			if (null != objVisualizationDetailsRequestVO.getEngineGHPOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPOperator())
					&& null != objVisualizationDetailsRequestVO.getEngineGHPValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_+ RMDServiceConstants.ENGINE_GHP)) {
					filterColumnName = filterLookupValues.get(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.ENGINE_GHP);
					fromQuery = filterERFromQuery(filterColumnName,
							parmDetailsMap, controllerSrc, fromQuery,
							whereQuery, filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {
						filterWhereQuery.append(" AND "+ filterColumnName
								+ objVisualizationDetailsRequestVO.getEngineGHPOperator() + ":engGHP1");
					} else if (complexOperators
							.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {

						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getEngineGHPOperator(),
								filterColumnName, RMDServiceConstants.ENGINE_GHP_INPUT1, RMDServiceConstants.ENGINE_GHP_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}

			if (null != objVisualizationDetailsRequestVO.getEngineSpeedOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedOperator())
					&& null != objVisualizationDetailsRequestVO.getEngineSpeedValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.ENGINE_SPEED)) {
					filterColumnName = filterLookupValues.get(RMDServiceConstants.ENGINE_RECORDER_ + RMDServiceConstants.ENGINE_SPEED);
					fromQuery = filterERFromQuery(filterColumnName, parmDetailsMap, controllerSrc, fromQuery,
							whereQuery, filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getEngineSpeedOperator())) {
						filterWhereQuery.append(" AND " + filterColumnName
								+ objVisualizationDetailsRequestVO.getEngineSpeedOperator() + ":engSpeed1");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineSpeedOperator())) {
						String filterWhereQuery2 = filterWhereQuery(
								objVisualizationDetailsRequestVO.getEngineSpeedOperator(), filterColumnName,
								RMDServiceConstants.ENGINE_SPEED_INPUT1, RMDServiceConstants.ENGINE_SPEED_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}

		} catch (Exception e) {
			throw e;

		}
		return fromQuery;

	}
	/**
	 * This method is used for forming the Query for Snapshot based on filter
	 * 
	 * @param 
	 * @return String
	 * @throws RMDDAOException
	 */
	public String filterSnapshotQuery(
			VisualizationDetailsRequestVO objVisualizationDetailsRequestVO,
			Map<String, String> filterLookupValues,
			List<String> simpleOperators, List<String> complexOperators,
			Map<String, VisualizationDetailsVO> parmDetailsMap,
			String controllerSrc, String fromQuery,
			StringBuilder filterWhereQuery) throws Exception {
		try {
			String filterColumnName = RMDCommonConstants.EMPTY_STRING;

			if (null != objVisualizationDetailsRequestVO.getFaultCode()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getFaultCode())) {
				filterWhereQuery.append("FAULT_CODE IN (:faultCode) AND ");

			}

			if (null != objVisualizationDetailsRequestVO.getLocoState()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getLocoState())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.LOCO_STATE)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.LOCO_STATE);
					VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
					filterColumnName=objVisualizationDetailsVO.getColumnName();
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					filterWhereQuery.append(filterColumnName + " IN(:locoState) AND ");
				}
			}

			if (null != objVisualizationDetailsRequestVO.getNotchValue()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getNotchValue())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.NOTCH)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.NOTCH);
					VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
					filterColumnName=objVisualizationDetailsVO.getColumnName();
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					filterWhereQuery.append(filterColumnName + " IN(:notch) AND ");
				}
			}

			if (null != objVisualizationDetailsRequestVO.getAmbientTempOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempOperator())
					&& null != objVisualizationDetailsRequestVO.getAmbientTempValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getAmbientTempValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.AMBIENT_TEMP)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.AMBIENT_TEMP);
			VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
			filterColumnName=objVisualizationDetailsVO.getColumnName();
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getAmbientTempOperator())) {
						filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getAmbientTempOperator()+ ":ambTempVal1 AND ");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getAmbientTempOperator())) {

						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getAmbientTempOperator(),
								filterColumnName, RMDServiceConstants.AMBIENT_TEMP_INPUT1, RMDServiceConstants.AMBIENT_TEMP_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}

			if (null != objVisualizationDetailsRequestVO.getEngineGHPOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPOperator())
					&& null != objVisualizationDetailsRequestVO.getEngineGHPValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.ENGINE_GHP)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.ENGINE_GHP);
					VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
					filterColumnName=objVisualizationDetailsVO.getColumnName();
					
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {
						filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getEngineGHPOperator() + ":engGHP1 AND ");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {

						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getEngineGHPOperator(),
								filterColumnName, RMDServiceConstants.ENGINE_GHP_INPUT1, RMDServiceConstants.ENGINE_GHP_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}

			if (null != objVisualizationDetailsRequestVO.getEngineSpeedOperator()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedOperator())
					&& null != objVisualizationDetailsRequestVO.getEngineSpeedValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineSpeedValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.ENGINE_SPEED)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.ENGINE_SPEED);
					VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
					filterColumnName=objVisualizationDetailsVO.getColumnName();
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getEngineSpeedOperator())) {
						filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getEngineSpeedOperator() + ":engSpeed1 AND ");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineSpeedOperator())) {
						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getEngineSpeedOperator(),
								filterColumnName, RMDServiceConstants.ENGINE_SPEED_INPUT1, RMDServiceConstants.ENGINE_SPEED_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
			}
			
			
			if (null != objVisualizationDetailsRequestVO.getOilInletOp()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getOilInletOp())
					&& null != objVisualizationDetailsRequestVO.getOilInletValue1()
					&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getOilInletValue1())) {
				if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.OIL_INLET)) {
					String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.OIL_INLET);
					VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
					filterColumnName=objVisualizationDetailsVO.getColumnName();
					
					fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
					if (simpleOperators.contains(objVisualizationDetailsRequestVO.getOilInletOp())) {
						filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getOilInletOp() + ":oilInlet1 AND ");
					} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getOilInletOp())) {
						String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getOilInletOp(),
								filterColumnName, RMDServiceConstants.OIL_INLET_INPUT1, RMDServiceConstants.OIL_INLET_INPUT2);
						filterWhereQuery.append(filterWhereQuery2);
					}
				}
				
			}
				
				
				if (null != objVisualizationDetailsRequestVO.getHpAvailableOp()
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getHpAvailableOp())
						&& null != objVisualizationDetailsRequestVO.getHpAvailableValue1()
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getHpAvailableValue1())) {
					if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.HP_AVAILABLE)) {
						String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.HP_AVAILABLE);
						VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
						filterColumnName=objVisualizationDetailsVO.getColumnName();
						
						fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
						if (simpleOperators.contains(objVisualizationDetailsRequestVO.getHpAvailableOp())) {
							filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getHpAvailableOp() + ":hpAvailable1 AND ");
						} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getHpAvailableOp())) {

							String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getHpAvailableOp(),
									filterColumnName, RMDServiceConstants.HP_AVAILABLE_INPUT1, RMDServiceConstants.HP_AVAILABLE_INPUT2);
							filterWhereQuery.append(filterWhereQuery2);
						}
					}
				}
				
				if (null != objVisualizationDetailsRequestVO.getBarometricPressOp()
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getBarometricPressOp())
						&& null != objVisualizationDetailsRequestVO.getBarometricPressValue1()
						&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getBarometricPressValue1())) {
					if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.BAROMETRIC_PRESS)) {
						String parmNumber = filterLookupValues.get(RMDServiceConstants.SNAPSHOT_ + RMDServiceConstants.BAROMETRIC_PRESS);
						VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
						filterColumnName=objVisualizationDetailsVO.getColumnName();
						
						fromQuery = filterSnapshotFromQuery(parmNumber, parmDetailsMap, controllerSrc, fromQuery,filterWhereQuery);
						if (simpleOperators.contains(objVisualizationDetailsRequestVO.getBarometricPressOp())) {
							filterWhereQuery.append(filterColumnName + objVisualizationDetailsRequestVO.getBarometricPressOp() + ":barometricPress1 AND ");
						} else if (complexOperators.contains(objVisualizationDetailsRequestVO.getBarometricPressOp())) {

							String filterWhereQuery2 = filterWhereQuery(objVisualizationDetailsRequestVO.getBarometricPressOp(),
									filterColumnName, RMDServiceConstants.BAROMETRIC_PRESS_INPUT1, RMDServiceConstants.BAROMETRIC_PRESS_INPUT2);
							filterWhereQuery.append(filterWhereQuery2);
						}
					}
				}
		} catch (Exception e) {
			throw e;
		}
		return fromQuery;
	}
	/**
	 * This method is used for forming the from Query for Engine Recorder based on filter
	 * 
	 * @param 
	 * @return String
	 * @throws RMDDAOException
	 */
	public String filterERFromQuery(String filterColumnName,
			Map<String, VisualizationDetailsVO> parmDetailsMap,
			String controllerSrc, String fromQuery, String whereQuery,
			StringBuilder sbWhereQuery) throws Exception {
		try {
			VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(filterColumnName);
			String tableName = RMDCommonConstants.EMPTY_STRING;
			if (RMDServiceConstants.CCA.equalsIgnoreCase(controllerSrc) || RMDServiceConstants.ECU.equalsIgnoreCase(controllerSrc)) {
				tableName = objVisualizationDetailsVO.getTableName();
			} 
		
			if (!fromQuery.contains("GETS_TOOLS." + tableName)) {
				fromQuery = fromQuery + RMDCommonConstants.COMMMA_SEPARATOR + "GETS_TOOLS." + tableName + " " + tableName;
			}
			if (RMDServiceConstants.CCA.equalsIgnoreCase(controllerSrc) || RMDServiceConstants.ECU.equalsIgnoreCase(controllerSrc)) {
				if (!whereQuery.contains(tableName + ".") && !sbWhereQuery.toString().contains(tableName + ".")) {				
					sbWhereQuery.append(" AND REC.PARM2VEHICLE="
							+ tableName + ".PARM2VEHICLE  AND REC.SOURCE_TYPE_CD="+tableName+".SOURCE_TYPE_CD AND REC.INCIDENT_TIMESTAMP="+tableName+".INCIDENT_TIMESTAMP");
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return fromQuery;

	}
	/**
	 * This method is used for forming the from Query for Snapshot based on filter
	 * 
	 * @param 
	 * @return String
	 * @throws RMDDAOException
	 */
	public String filterSnapshotFromQuery(String parmNumber,
			Map<String, VisualizationDetailsVO> parmDetailsMap,
			String controllerSrc, String fromQuery,StringBuilder whereQuery) {
		try {
			VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap.get(parmNumber);
			String tableName = objVisualizationDetailsVO.getTableName();

			if (!fromQuery.contains("GETS_TOOLS." + tableName+" "+tableName)) {
				fromQuery = fromQuery + RMDCommonConstants.COMMMA_SEPARATOR + "GETS_TOOLS." + tableName + " " + tableName;
			}
			
			if (!whereQuery.toString().contains(tableName + ".")) {
				whereQuery.append("FAULT.OBJID="+ tableName + ".PARM2FAULT(+) AND ");
			}

		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VISUALIZATION);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		}
		return fromQuery;

	}

	/**
	 * This method is used for forming the where Query for complex operators based on filter
	 * 
	 * @param 
	 * @return String
	 * @throws RMDDAOException
	 */
	public String filterWhereQuery(String logicalOperator, String columnName, String value1Name, String value2Name) {
			String query = RMDCommonConstants.EMPTY_STRING;

			if (logicalOperator.equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_SYMBOL)) {
				query = columnName + " >:" + value1Name + " AND " + columnName + " <:" + value2Name+ " AND ";
			}

			if (logicalOperator.equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_SYMBOL)) {
				query = columnName + " >=:" + value1Name + " AND " + columnName + "<:" + value2Name+ " AND ";
			}

			if (logicalOperator.equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_EQUAL_SYMBOL)) {
				query = columnName + " >:" + value1Name + " AND " + columnName + "<=:" + value2Name+ " AND ";
			}

			if (logicalOperator.equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_EQUAL_SYMBOL)) {
				query = columnName + " >=:" + value1Name + " AND " + columnName + "<=:" + value2Name+ " AND ";
			}

			if (logicalOperator.equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_SYMBOL)) {
				query = columnName + " <:" + value1Name + " OR " + columnName + ">:" + value2Name+ " AND ";
			}

			if (logicalOperator.equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_SYMBOL)) {
				query = columnName + " <=:" + value1Name + " OR " + columnName + ">:" + value2Name+ " AND ";
			}
			if (logicalOperator.equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_EQUAL_SYMBOL)) {
				query = columnName + " <:" + value1Name + " OR " + columnName + ">=:" + value2Name+ " AND ";
			}
			if (logicalOperator.equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_EQUAL_SYMBOL)) {
				query = columnName + " <=:" + value1Name + " OR " + columnName + ">=:" + value2Name+ " AND ";
			}

			return query;
	}

	/**
	 * This method is used for Mp Parm Details from Parmdef table
	 * 
	 * @param VisualizationDetailsRequestVO
	 * @return List<VisualizationDetailsVO>
	 * @throws RMDDAOException
	 */
	@Cacheable(value = "parmDetailsCache")
	public Map<String, Map<String, VisualizationDetailsVO>> getERParmDetails() {
		Session hibernateSession = null;
		List<Object[]> arlParmDetails = null;
		VisualizationDetailsVO objVisualizationDetailsVO = null;
		Map<String, Map<String, VisualizationDetailsVO>> parentParmDetailsMap = new HashMap<String, Map<String, VisualizationDetailsVO>>();
		Map<String, VisualizationDetailsVO> parmDetailsMap = new HashMap<String, VisualizationDetailsVO>();
		try {
			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT PARMDEF.PARM_NUMBER,PARM_LOAD_COLUMN,PARM_LOAD_TABLE,PARM_DESC,CONTROLLER_SOURCE_ID,SOURCE_DATA_FLOW_CD,SUMMARY_TBL_NAME,RULE_PARM_DESC FROM GETS_RMD.GETS_RMD_PARMDEF_NEW PARMDEF");
			Query qry = hibernateSession.createSQLQuery("SELECT PARMDEF.PARM_NUMBER,PARM_LOAD_COLUMN,PARM_LOAD_TABLE,PARM_DESC,CONTROLLER_SOURCE_ID,SOURCE_DATA_FLOW_CD,SUMMARY_TBL_NAME,RULE_PARM_DESC FROM GETS_RMD.GETS_RMD_PARMDEF_NEW PARMDEF");
			qry.setFetchSize(500);
			arlParmDetails = qry.list();
			if (null != arlParmDetails && arlParmDetails.size() > 0) {
				for (Object[] obj : arlParmDetails) {
					objVisualizationDetailsVO = new VisualizationDetailsVO();
					String parmNo = RMDCommonUtility.convertObjectToString(obj[0]);
					String columnName = RMDCommonUtility.convertObjectToString(obj[1]);
					String tableName = RMDCommonUtility.convertObjectToString(obj[2]);
					String parmDesc = RMDCommonUtility.convertObjectToString(obj[3]);
					String ctrlCfg = RMDCommonUtility.convertObjectToString(obj[4]);
					String ctrlSrc = RMDCommonUtility.convertObjectToString(obj[5]);
					String summaryTable = RMDCommonUtility.convertObjectToString(obj[6]);
					objVisualizationDetailsVO.setParmDescription(parmDesc);
					objVisualizationDetailsVO.setColumnName(columnName);
					objVisualizationDetailsVO.setTableName(tableName);
					objVisualizationDetailsVO.setParmNumber(parmNo);
					objVisualizationDetailsVO.setControllerCfg(ctrlCfg);
					objVisualizationDetailsVO.setControllerSource(ctrlSrc);
					objVisualizationDetailsVO.setSummaryTableName(summaryTable);
					objVisualizationDetailsVO.setDisplayName(columnName);
					objVisualizationDetailsVO.setParmDescriptionName(columnName);
					if (parentParmDetailsMap.containsKey(ctrlSrc)) {
						parmDetailsMap = parentParmDetailsMap.get(ctrlSrc);
						parmDetailsMap.put(columnName, objVisualizationDetailsVO);
						parentParmDetailsMap.put(ctrlSrc, parmDetailsMap);
						parentParmDetailsMap.put(ctrlCfg, parmDetailsMap);
					} else {
						parmDetailsMap = new HashMap<String, VisualizationDetailsVO>();
						parmDetailsMap.put(columnName, objVisualizationDetailsVO);
						parentParmDetailsMap.put(ctrlSrc, parmDetailsMap);
						parentParmDetailsMap.put(ctrlCfg, parmDetailsMap);
					}
				}
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VISUALIZATION);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_VISUALIZATION);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return parentParmDetailsMap;
	}

	
	/**
	 * This method is used for fetching the logical operators
	 * 
	 * @param listName
	 * @return Map<String, List<String>>
	 * @throws RMDDAOException
	 */
	
	@Cacheable(value = "logicalOperatorsCache")
	public Map<String, List<String>> getLogicalOperators(String listName) {

		Map<String, List<String>> resultMap = new LinkedHashMap<String, List<String>>();
		List<String> logicalOperatorsLst = null;
		try {
			List<GetSysLookupVO> arlSysLookUp = popupListAdminDAO.getPopupListValues(listName);
			if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
				for (GetSysLookupVO objSysLookupVO : arlSysLookUp) {
					String lookValue = ESAPI.encoder().decodeForHTML(objSysLookupVO.getLookValue());
					String listDescription = ESAPI.encoder().decodeForHTML(objSysLookupVO.getListDescription());
					if (resultMap.containsKey(listDescription)) {
						List<String> logicalOperatorsType = resultMap.get(listDescription);
						logicalOperatorsType.add(lookValue);
						resultMap.put(listDescription, logicalOperatorsType);
					} else {
						logicalOperatorsLst = new ArrayList<String>();
						logicalOperatorsLst.add(lookValue);
						resultMap.put(listDescription, logicalOperatorsLst);
					}
				}
			}
		} catch (RMDDAOException e) {
			throw e;
		}
		return resultMap;

	}
	/**
	 * This method is used for fetching the vizualization lookup values
	 * 
	 * @param listName
	 * @return Map<String, String>
	 * @throws RMDDAOException
	 */
	@Cacheable(value = "vizFilterLookUpValuesCache")
	public Map<String, String> getVizFilterLookUpValues(String listName) {

		Map<String, String> lookupValues = new LinkedHashMap<String, String>();
		try {
			List<GetSysLookupVO> arlSysLookUp = popupListAdminDAO.getPopupListValues(listName);
			if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
				for (GetSysLookupVO objSysLookupVO : arlSysLookUp) {
					lookupValues.put(ESAPI.encoder().decodeForHTML(objSysLookupVO.getListDescription()), ESAPI.encoder().decodeForHTML(objSysLookupVO.getLookValue()));
				}
			}
		} catch (RMDDAOException e) {
			throw e;
		}
		return lookupValues;
	}
	
	/**
	 * This method is used for fetching the parm Details
	 * 
	 * @param 
	 * @return Map<String, Map<String, VisualizationDetailsVO>>
	 * @throws RMDDAOException
	 */
	@Cacheable(value = "snapshotParmDetailsCache")
	public Map<String, Map<String, VisualizationDetailsVO>> getSnapshotParmDetails() {
		Session hibernateSession = null;
		List<Object[]> arlParmDetails = null;
		VisualizationDetailsVO objVisualizationDetailsVO = null;
		Map<String, Map<String, VisualizationDetailsVO>> parentParmDetailsMap = new HashMap<String, Map<String, VisualizationDetailsVO>>();
		Map<String, VisualizationDetailsVO> parmDetailsMap = new HashMap<String, VisualizationDetailsVO>();
		try {
			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT PARMDEF.PARM_NUMBER,PARM_LOAD_COLUMN,PARM_LOAD_TABLE,PARM_DESC,C.CONTROLLER_CFG,RULE_PARM_DESC from GETS_RMD.GETS_RMD_PARMDEF PARMDEF,GETS_RMD.GETS_RMD_CTL_CFG C where PARMDEF.CONTROLLER_SOURCE_ID=C.VEHICLE_NO_MP_SOURCE");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			qry.setFetchSize(500);
			arlParmDetails = qry.list();
			if (null != arlParmDetails && arlParmDetails.size() > 0) {
				for (Object[] obj : arlParmDetails) {
					objVisualizationDetailsVO = new VisualizationDetailsVO();
					String parmNo = RMDCommonUtility.convertObjectToString(obj[0]);
					String columnName = RMDCommonUtility.convertObjectToString(obj[1]);
					String tableName = RMDCommonUtility.convertObjectToString(obj[2]);
					String parmDesc = RMDCommonUtility.convertObjectToString(obj[3]);
					String ctrlCfg = RMDCommonUtility.convertObjectToString(obj[4]);
					String parmDescName = RMDCommonUtility.convertObjectToString(obj[5]);
					objVisualizationDetailsVO.setParmDescription(parmDesc);
					objVisualizationDetailsVO.setColumnName(columnName);
					objVisualizationDetailsVO.setTableName(tableName);
					objVisualizationDetailsVO.setParmNumber(parmNo);
					objVisualizationDetailsVO.setControllerCfg(ctrlCfg);
					objVisualizationDetailsVO.setDisplayName(parmDesc);
					objVisualizationDetailsVO.setParmDescriptionName(parmDescName);
					if (parentParmDetailsMap.containsKey(ctrlCfg)) {
						parmDetailsMap = parentParmDetailsMap.get(ctrlCfg);
						parmDetailsMap.put(parmNo, objVisualizationDetailsVO);
						parentParmDetailsMap.put(ctrlCfg, parmDetailsMap);
					} else {
						parmDetailsMap = new HashMap<String, VisualizationDetailsVO>();
						parmDetailsMap.put(parmNo, objVisualizationDetailsVO);
						parentParmDetailsMap.put(ctrlCfg, parmDetailsMap);
					}
				}
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return parentParmDetailsMap;
	}

	/**
	 * This method is used for fetching the Asset objids
	 * 
	 * @param 
	 * @return Long
	 * @throws RMDDAOException
	 */
	public Long getAssetObjid(
			String customerId,String assetNumber,String assetHeader,String controllerCfg) {
		Session hibernateSession = null;
		Long longVehicleID = null;
		try {

			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT GRCRRV.VEHICLE_OBJID");
			strQry.append(" FROM GETS_RMD_CTL_CFG GRCC,");
			strQry.append("GETS_RMD_VEHICLE GRV,");
			strQry.append("GETS_RMD_CUST_RNH_RN_V GRCRRV");
			strQry.append(" WHERE GRCC.OBJID = GRV.VEHICLE2CTL_CFG");
			strQry.append(" AND GRV.OBJID = GRCRRV.VEHICLE_OBJID");
			strQry.append(" AND GRCRRV.ORG_ID =:customer");
			strQry.append(" AND GRCRRV.VEHICLE_HDR =:vehicleHeader");
			strQry.append(" AND GRCRRV.VEHICLE_NO =:roadNumber");
			strQry.append(" AND GRCC.CONTROLLER_CFG=:controllerCfg");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			qry.setParameter("customer", customerId);
			qry.setParameter("vehicleHeader", assetHeader);
			qry.setParameter("roadNumber", assetNumber);
			qry.setParameter("controllerCfg", controllerCfg);
			qry.setFetchSize(1);
			List<BigDecimal> lstAsset = qry.list();
			if (null != lstAsset && !lstAsset.isEmpty()) {
				longVehicleID = lstAsset.get(0).longValue();
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {}, 
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return longVehicleID;
	}
	/**
	 * This method is used for fetching the Snapshot ParmNumbers
	 * 
	 * @param String source,String sourceType, String language
	 * @return List<VisualizationMPParmNumVO>
	 * @throws RMDDAOException
	 */
	@Override
	public List<VisualizationMPParmNumVO> getSnapshotParmNumbers(String source,
			String sourceType, String language) throws RMDDAOException {
		List<VisualizationMPParmNumVO> parmDetailsLst = new ArrayList<VisualizationMPParmNumVO>();
		VisualizationMPParmNumVO objVisualizationMPParmNumVO = null;
		try {

			Map<String, Map<String, VisualizationDetailsVO>> parentParmDetails = getSnapshotParmDetails();
			for (Map.Entry entry : parentParmDetails.entrySet()) {
				Map<String, VisualizationDetailsVO> parmDetailsMap = (Map<String, VisualizationDetailsVO>) entry.getValue();
				for (Map.Entry parmentrySet : parmDetailsMap.entrySet()) {
					VisualizationDetailsVO objVisualizationDetailsVO = (VisualizationDetailsVO) parmentrySet.getValue();
					objVisualizationMPParmNumVO = new VisualizationMPParmNumVO();
					objVisualizationMPParmNumVO.setParmDescription(objVisualizationDetailsVO.getParmDescription());
					objVisualizationMPParmNumVO.setColumnName(objVisualizationDetailsVO.getColumnName());
					objVisualizationMPParmNumVO.setTableName(objVisualizationDetailsVO.getTableName());
					objVisualizationMPParmNumVO.setParmNumber(objVisualizationDetailsVO.getParmNumber());
					objVisualizationMPParmNumVO.setControllerCfg(objVisualizationDetailsVO.getControllerCfg());
					objVisualizationMPParmNumVO.setParmDescriptionName(objVisualizationDetailsVO.getParmDescriptionName());
					objVisualizationMPParmNumVO.setParmNumColName(objVisualizationDetailsVO.getParmNumber());
					parmDetailsLst.add(objVisualizationMPParmNumVO);
				}

			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} 
		return parmDetailsLst;
	}
	
	/**
	 * This method is used for fetching the Engine Recorder ParmNumbers
	 * 
	 * @param String source,String sourceType, String language
	 * @return List<VisualizationMPParmNumVO>
	 * @throws RMDDAOException
	 */

	@Override
	public List<VisualizationMPParmNumVO> getERParmNumbers(String source,
			String sourceType, String language) throws RMDDAOException {
		List<VisualizationMPParmNumVO> parmDetailsLst = new ArrayList<VisualizationMPParmNumVO>();
		VisualizationMPParmNumVO objVisualizationMPParmNumVO = null;
		try {

			Map<String, Map<String, VisualizationDetailsVO>> parentParmDetails = getERParmDetails();
			for (Map.Entry entry : parentParmDetails.entrySet()) {
				Map<String, VisualizationDetailsVO> parmDetailsMap = (Map<String, VisualizationDetailsVO>) entry.getValue();
				for (Map.Entry parmentrySet : parmDetailsMap.entrySet()) {
					VisualizationDetailsVO objVisualizationDetailsVO = (VisualizationDetailsVO) parmentrySet.getValue();
					objVisualizationMPParmNumVO = new VisualizationMPParmNumVO();
					objVisualizationMPParmNumVO.setParmDescription(objVisualizationDetailsVO.getParmDescription());
					objVisualizationMPParmNumVO.setColumnName(objVisualizationDetailsVO.getColumnName());
					objVisualizationMPParmNumVO.setTableName(objVisualizationDetailsVO.getTableName());
					objVisualizationMPParmNumVO.setParmNumber(objVisualizationDetailsVO.getParmNumber());
					objVisualizationMPParmNumVO.setControllerCfg(objVisualizationDetailsVO.getControllerCfg());
					objVisualizationMPParmNumVO.setParmDescriptionName(objVisualizationDetailsVO.getParmDescriptionName());
					objVisualizationMPParmNumVO.setParmNumColName(objVisualizationDetailsVO.getParmNumber());
					objVisualizationMPParmNumVO.setControllerType(objVisualizationDetailsVO.getControllerSource());
					parmDetailsLst.add(objVisualizationMPParmNumVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} 
		return parmDetailsLst;

	}
	/**
	 * This method is used for fetching getRecordTypes
	 * 
	 * @param 
	 * @return String
	 * @throws 
	 */
	public String getRecordTypes() throws Exception {
		String strRecordType = RMDCommonConstants.EMPTY_STRING;
		String sbRecordTypeQuery = null;
		String strRecTypes = null;
		Session session = null;
		Query hibernateQuery = null;
		List recordTypeList = null;
		StringTokenizer st = null;
		String strData = null;
		try {
			sbRecordTypeQuery = " Select value from gets_rmd.gets_rmd_sysparm"
					+ " where title = 'vvf_faults_for_loco'";
			session = getHibernateSession();
			hibernateQuery = session.createSQLQuery(sbRecordTypeQuery);
			hibernateQuery.setFetchSize(10);
			recordTypeList = hibernateQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(recordTypeList)) {
				final Iterator<Object> iter = recordTypeList.iterator();
				if (iter.hasNext()) {
					final Object recordTypeRow = iter.next();
					strRecTypes = RMDCommonUtility
							.convertObjectToString(recordTypeRow);
				}
			}

			st = new StringTokenizer(strRecTypes, RMDCommonConstants.COMMMA_SEPARATOR);
			while (st.hasMoreTokens()) {
				if (st.hasMoreTokens()) {
					strData = st.nextToken();
					strRecordType += RMDCommonConstants.SINGLE_QTE + strData
							+ RMDCommonConstants.SINGLE_QTE
							+ RMDCommonConstants.COMMMA_SEPARATOR;
				}
			}

			strRecordType = strRecordType.substring(0, strRecordType.length() - 1);
			FaultLogHelper.log("strRecType after :" + strRecordType);
		} catch (Exception e) {
			FaultLogHelper.log("Error in getting Record Types :" + e);
			throw e;
		} finally {
			releaseSession(session);
		}
		return strRecordType;
	}
	
	
	/**
	 * This method is used for fetching AssetVisualizationData
	 * 
	 * @param 
	 * @return List<VisualizationEventDataVO>
	 * @throws 
	 */
	@Override
	public List<VisualizationEventDataVO> getAssetVisualizationEventData(VisualizationEventDataVO objVisualizationEventDataVO) throws RMDDAOException {
		List<VisualizationEventDataVO> arlVisualizationEventDataVO = new ArrayList<VisualizationEventDataVO>();
		VisualizationEventDataVO objVisualizationEventDetails=null;
		Session hibernateSession = null;
		try {
			Long assetObjid=getAssetObjid(objVisualizationEventDataVO.getCustomerId(), objVisualizationEventDataVO.getAssetNumber(), objVisualizationEventDataVO.getAssetGroupName(), objVisualizationEventDataVO.getControllerCfg());
			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT S.VEHICLE_OBJID AS ASSET_ID,TO_CHAR(WO.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS') EVENT_OCCUR_DATE,TO_CHAR(WO.LAST_UPDATE_DATE,'MM/DD/YYYY HH24:MI:SS') EVENT_END_DATE,");
			strQry.append("'SCHEDULED' EVENT_TYPE,SUBSTR(WO.INSHOP_REASON_CODE||'-'||WO.WORKORDER_COMMENTS,0,25) EVENT_SUMMARY,WO.INSHOP_REASON_CODE||'-'||WO.WORKORDER_COMMENTS EVENT_DETAILS");
			strQry.append(" FROM GETS_DW_EOA_SITE_PART@RMD_EOA_DW.WORLD S,");
			strQry.append("GETS_DW_EOA_VEHICLE@RMD_EOA_DW.WORLD V,GETS_LMS.GETS_LMS_SERVICE_WORKORDER@ESERVICES.WORLD WO");
			strQry.append(" WHERE S.VEHICLE_OBJID   = V.VEHICLE_NBR AND V.LMS_LOCOMOTIVE_ID = WO.LOCOMOTIVE_ID");
			if(objVisualizationEventDataVO.getNoOfDays() != null && !"".equals(objVisualizationEventDataVO.getNoOfDays())){
				strQry.append(" AND WO.CREATION_DATE > SYSDATE - :noOfDays");
			}else{
				strQry.append(" AND WO.CREATION_DATE BETWEEN TO_DATE(:fromDate,'MM/DD/YYYY HH24:MI:SS') AND TO_DATE(:toDate,'MM/DD/YYYY HH24:MI:SS')");
			}
			strQry.append(" AND S.VEHICLE_OBJID     =:roadNumber");
			strQry.append(" UNION");
			strQry.append(" SELECT CUST_FDBK2VEHICLE,  TO_CHAR(RX_OPEN_DATE,'MM/DD/YYYY HH24:MI:SS'),TO_CHAR(RX_CLOSE_DATE,'MM/DD/YYYY HH24:MI:SS'),'RX',");
			strQry.append("RX_CASE_ID|| '-'  || RECOM_TITLE, RX_FEEDBACK");
			strQry.append(" FROM GETS_DW_EOA_CUST_FDBK@RMD_EOA_DW.WORLD F");
			strQry.append(" WHERE CUST_FDBK2VEHICLE =:roadNumber");
			if(objVisualizationEventDataVO.getNoOfDays() != null && !"".equals(objVisualizationEventDataVO.getNoOfDays())){
				strQry.append(" AND CASE_CREATION_DATE > SYSDATE - :noOfDays");
			}else{
				strQry.append(" AND CASE_CREATION_DATE BETWEEN TO_DATE(:fromDate,'MM/DD/YYYY HH24:MI:SS') AND TO_DATE(:toDate,'MM/DD/YYYY HH24:MI:SS')");
			}
			strQry.append(" ORDER BY 1,2");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			
			qry.setParameter(RMDServiceConstants.ROADNUMBER, assetObjid);
			if(objVisualizationEventDataVO.getNoOfDays() != null && !"".equals(objVisualizationEventDataVO.getNoOfDays())){
				qry.setParameter("noOfDays", objVisualizationEventDataVO.getNoOfDays());
			}else{
				qry.setParameter(RMDServiceConstants.FROM_DATE, objVisualizationEventDataVO.getFromDate());
				qry.setParameter(RMDServiceConstants.TO_DATE, objVisualizationEventDataVO.getToDate());
			}
			qry.setFetchSize(1);
			
			List<Object[]> arlEventDetails=qry.list();
			if (null != arlEventDetails && arlEventDetails.size() > 0) {
				for (Object[] obj : arlEventDetails) {
					objVisualizationEventDetails =new VisualizationEventDataVO();
					objVisualizationEventDetails.setAssetNumber(RMDCommonUtility.convertObjectToString(obj[0]));
					String occurDate = RMDCommonUtility.convertObjectToString(obj[1]);	
					String eventType=RMDCommonUtility.convertObjectToString(obj[3]);
					String eventSummary=ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(obj[4]));
					String eventDetails=RMDCommonUtility.convertObjectToString(obj[5]);
					DateFormat zoneFormater = new SimpleDateFormat(DateConstants.MMddyyyyHHmmss);
					if(null!=occurDate&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(occurDate)){
						objVisualizationEventDetails.setStrEvntOccurDt(occurDate);					
						GregorianCalendar objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(zoneFormater.parse(occurDate));
						RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, RMDCommonConstants.DateConstants.GMT);
						objVisualizationEventDetails.setEventOccurDate(objGregorianCalendar.getTimeInMillis());
					}
					String endDate = RMDCommonUtility.convertObjectToString(obj[2]);	
					if(null!=endDate&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(endDate)){
						objVisualizationEventDetails.setStrEvntEndDt(endDate);
						GregorianCalendar objGregorianCalendarEnd = new GregorianCalendar();
						objGregorianCalendarEnd.setTime(zoneFormater.parse(endDate));
						RMDCommonUtility.setZoneOffsetTime(objGregorianCalendarEnd, RMDCommonConstants.DateConstants.GMT);
						objVisualizationEventDetails.setEventEndDate(objGregorianCalendarEnd.getTimeInMillis());
					}
					objVisualizationEventDetails.setEventType(eventType);
					if(null!=eventType&&eventType.equalsIgnoreCase(RMDServiceConstants.SCHEDULED)){
						if(null!=eventDetails&&eventDetails.length()>25){
							objVisualizationEventDetails.setEventSummary(eventSummary+"...");
						}else{
							objVisualizationEventDetails.setEventSummary(eventSummary);
						}
					}else{
						objVisualizationEventDetails.setEventSummary(eventSummary);
					}
					
					objVisualizationEventDetails.setEventDetails(ESAPI.encoder().encodeForXML(RMDCommonUtility.convertObjectToString(obj[5])));
					arlVisualizationEventDataVO.add(objVisualizationEventDetails);
				}
			}
					
		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return arlVisualizationEventDataVO;

	}
	
	/**
	 * This method is used for fetching virtual parameters for visualization screen and this method is cached
	 * 
	 * 
	 * @param 
	 * @return Map<String, Map<String, VirtualParametersVO>>
	 * @throws RMDDAOException
	 */
	@Cacheable(value = "virtualParametersCache")
	public Map<String, Map<String, VirtualParametersVO>> getCachedVirtualParameters()
			throws RMDDAOException {
		Session hibernateSession = null;
		List<Object[]> arlVirtualParmDetails = null;
		VirtualParametersVO objVirtualParametersVO=null;
		Map<String, Map<String, VirtualParametersVO>> virtualParmDetailsMap = new LinkedHashMap<String, Map<String, VirtualParametersVO>>();
		Map<String, VirtualParametersVO> virtualParmDetails;
		try {
			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT DISTINCT C.COLUMN_NAME VIRTUALNAME,V.VIRTUAL_COLUMN_NAME VIRTUALID,C.FAMILY FROM GETS_TOOLS.GETS_TOOL_VIRTUAL V, GETS_TOOL_DPD_COLNAME C, GETS_TOOL_DPD_SIMFEA SF, GETS_TOOL_DPD_SIMRUL SR, GETS_TOOL_DPD_RULHIS RH");
			strQry.append(" WHERE LINK_DPD_COLUMN_NAME=C.OBJID AND SIMFEA2SIMRUL = SR.OBJID AND SF.SIMFEA2COLNAME = C.OBJID AND SIMRUL2FINRUL = RH.RULHIS2FINRUL AND RH.ACTIVE  = 1 ORDER BY C.COLUMN_NAME ASC");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			qry.setFetchSize(500);
			arlVirtualParmDetails = qry.list();
			if (null != arlVirtualParmDetails && arlVirtualParmDetails.size() > 0) {
				for (Object[] obj : arlVirtualParmDetails) {
					objVirtualParametersVO = new VirtualParametersVO();
					String virtualName = RMDCommonUtility.convertObjectToString(obj[0]);
					String virtualId = RMDCommonUtility.convertObjectToString(obj[1]);
					String family = RMDCommonUtility.convertObjectToString(obj[2]);
					objVirtualParametersVO.setVirtualName(virtualName);
					objVirtualParametersVO.setVirtualId(virtualId);
					objVirtualParametersVO.setFamily(family);
					if (virtualParmDetailsMap.containsKey(family)) {
						virtualParmDetails = virtualParmDetailsMap.get(family);
						virtualParmDetails.put(virtualId, objVirtualParametersVO);
						virtualParmDetailsMap.put(family, virtualParmDetails);
					} else {
						virtualParmDetails = new LinkedHashMap<String, VirtualParametersVO>();
						virtualParmDetails.put(virtualId, objVirtualParametersVO);
						virtualParmDetailsMap.put(family, virtualParmDetails);
					}
				}
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return virtualParmDetailsMap;
	}
	
	
	/**
	 * This method is used for fetching virtual parameters for visualization screen
	 * 
	 * 
	 * @param String database,String language
	 * @return List<VirtualParametersVO>
	 * @throws RMDDAOException
	 */
	
	@Override
	public List<VirtualParametersVO> getVizVirtualParameters() throws RMDDAOException {
		Session hibernateSession = null;
		List<VirtualParametersVO> arlVirtualParametersVO = new ArrayList<VirtualParametersVO>();
		try {

			Map<String, Map<String, VirtualParametersVO>> parentVirtualParmDetails = getCachedVirtualParameters();
			for (Map.Entry entry : parentVirtualParmDetails.entrySet()) {
				Map<String, VirtualParametersVO> virtualParmDetailsMap = (Map<String, VirtualParametersVO>) entry.getValue();
				for (Map.Entry parmentrySet : virtualParmDetailsMap.entrySet()) {
					VirtualParametersVO objVirtualParametersVO = (VirtualParametersVO) parmentrySet.getValue();
					arlVirtualParametersVO.add(objVirtualParametersVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return arlVirtualParametersVO;
	}

	/**
	 * This method is used for fetching asset family for visualization screen
	 * 
	 * 
	 * @param String model
	 * @return String
	 * @throws RMDDAOException
	 */
	@Override
	public String getVizVirtualAssetFamily(String model) throws RMDDAOException {
		Session hibernateSession = null;
		List<String> arlFamily = null;
		String family=RMDCommonConstants.EMPTY_STRING;
		try {
			StringBuilder strQry = new StringBuilder();
			hibernateSession = getHibernateSession();
			strQry.append("SELECT DECODE(FAMILY,'ACCCA','CCA','DCCCA','CCA',FAMILY) MODELFAMILY FROM GETS_RMD_MODEL WHERE MODEL_NAME =:model");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			qry.setParameter(RMDCommonConstants.MODEL, model);
			qry.setFetchSize(1);
			arlFamily = qry.list();
			if (null != arlFamily && arlFamily.size() > 0) {
				family=arlFamily.get(0);
			}

		} catch (RMDDAOConnectionException ex) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			final String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_POPUP_ADMIN);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}

		return family;
	}
	
	
	/**
	 * This method is used for fetching virtual parameter records for drawing graph in visualization screen
	 *  
	 * @param VisualizationDetailsRequestVO
	 * @return List<VisualizationDetailsVO>
	 * @throws RMDDAOException
	 */		
	
	@Override
	public List<VisualizationDetailsVO> getSnapshotVisualizationDetails(
			VisualizationDetailsRequestVO objVisualizationDetailsRequestVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		List<VisualizationDetailsVO> arlDetailsVO = null;
		List<String> parmDisplayNames = new ArrayList<String>();
		List<String> lstStackOrder = new ArrayList<String>();
		
		Map<String, String> filterLookupValues = new LinkedHashMap<String, String>();
		Map<String, List<String>> logicalOperators = new LinkedHashMap<String, List<String>>();
		List<String> simpleOperators = new ArrayList<String>();
		List<String> complexOperators = new ArrayList<String>();
		StringBuilder filterWhereQuery = new StringBuilder();
		String filterFromQuery = RMDCommonConstants.EMPTY_STRING;
		Map<String, VisualizationDetailsVO> multiAssetVizDetails = new LinkedHashMap<String, VisualizationDetailsVO>();
		VisualizationParmDetailsVO objVisualizationParmDetailsVO = null;
		List<Long> assetObjidList = new ArrayList<Long>();
		Long assetObjid = 0L;
		boolean isMultiAsset = false;
		Map<String, String> assetObjidMp = null;
		try {
			hibernateSession = getHibernateSession();
			List<String> arlMpNumbers = objVisualizationDetailsRequestVO
					.getMpNumbers();
			List<String> arlVirtualParms = objVisualizationDetailsRequestVO
					.getVirtualParameters();
			List<String> arlAnomParms = objVisualizationDetailsRequestVO
			.getAnomParameters();
			String fromQuery = RMDCommonConstants.EMPTY_STRING;
			String whereQuery = RMDCommonConstants.EMPTY_STRING;
			String selectQuery = RMDCommonConstants.EMPTY_STRING;
			String controllerCfg = objVisualizationDetailsRequestVO
					.getControllerCfg();

			filterLookupValues = getVizFilterLookUpValues(RMDServiceConstants.VISUALIZATION_FILTER_PARM_COLUMN_NAMES);
			logicalOperators = getLogicalOperators(RMDServiceConstants.OMD_LOGICAL_OPERATORS);
			simpleOperators = logicalOperators
					.get(RMDServiceConstants.SIMPLE_OPERATORS);
			complexOperators = logicalOperators
					.get(RMDServiceConstants.COMPLEX_OPERATORS);

			if (null != objVisualizationDetailsRequestVO.getAssetObjid()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(objVisualizationDetailsRequestVO
									.getAssetObjid())) {
				isMultiAsset = true;
				String[] strMultiAsset = objVisualizationDetailsRequestVO
						.getAssetObjid().split(",");
				assetObjidMp = new HashMap<String, String>();
				for (int i = 0; i < strMultiAsset.length; i++) {
					String[] assetDetails = strMultiAsset[i].split("~");
					assetObjidMp.put(assetDetails[1], assetDetails[0]);
					assetObjidList.add(Long.valueOf(assetDetails[1]));
				}
			} else {
				assetObjid = getAssetObjid(
						objVisualizationDetailsRequestVO.getCustomerId(),
						objVisualizationDetailsRequestVO.getAssetNumber(),
						objVisualizationDetailsRequestVO.getAssetGroupName(),
						objVisualizationDetailsRequestVO.getControllerCfg());
				assetObjidList.add(assetObjid);
			}
			if (null != assetObjidList && !assetObjidList.isEmpty()) {
				Map<String, Map<String, VisualizationDetailsVO>> parentParmDetailsMap = getSnapshotParmDetails();
				Map<String, VisualizationDetailsVO> parmDetailsMap = parentParmDetailsMap
						.get(controllerCfg);
				if (null != arlMpNumbers && arlMpNumbers.size() > 0) {
					String recordType = getRecordTypes();
					for (Iterator iterator = arlMpNumbers.iterator(); iterator
							.hasNext();) {
						String strMpNumberWithStackOrder = (String) iterator
								.next();
						String[] arrMpNumberWithStackOrder = strMpNumberWithStackOrder
								.split("~");
						String mpNumber = arrMpNumberWithStackOrder[0];
						String stackOrder = arrMpNumberWithStackOrder[1];

						if (null != parmDetailsMap && !parmDetailsMap.isEmpty()) {
							VisualizationDetailsVO objVisualizationDetailsVO = parmDetailsMap
									.get(mpNumber);
							if (null != objVisualizationDetailsVO) {
								String columnName = objVisualizationDetailsVO
										.getColumnName();
								String tableName = objVisualizationDetailsVO
										.getTableName();
								parmDisplayNames.add(objVisualizationDetailsVO
										.getDisplayName());
								lstStackOrder.add(stackOrder);
								selectQuery = selectQuery
										+ RMDCommonConstants.COMMMA_SEPARATOR
										+ tableName + "." + columnName;
								if (!fromQuery.contains("GETS_TOOLS."
										+ tableName + " " + tableName)) {
									fromQuery = fromQuery
											+ RMDCommonConstants.COMMMA_SEPARATOR
											+ "GETS_TOOLS." + tableName + " "
											+ tableName;
								}
								if (!whereQuery.contains(tableName + ".")) {
									whereQuery = whereQuery + "FAULT.OBJID="
											+ tableName + ".PARM2FAULT(+) AND ";
								}
							}
						}

					}

					whereQuery = whereQuery
							+ "FAULT2VEHICLE IN(:assetNumber) AND RECORD_TYPE IN ("
							+ recordType + ") AND ";

				}

				if (null != arlVirtualParms && arlVirtualParms.size() > 0) {
					Map<String, Map<String, VirtualParametersVO>> virtualParameterDetails = getCachedVirtualParameters();
					Map<String, VirtualParametersVO> virtualParametersForFamily = virtualParameterDetails
							.get(objVisualizationDetailsRequestVO.getFamily());

					for (Iterator iterator = arlVirtualParms.iterator(); iterator
							.hasNext();) {

						String strMpNumberWithStackOrder = (String) iterator
								.next();
						String[] arrMpNumberWithStackOrder = strMpNumberWithStackOrder
								.split("~");
						String columnName = arrMpNumberWithStackOrder[0];
						String stackOrder = arrMpNumberWithStackOrder[1];

						selectQuery = selectQuery
								+ RMDCommonConstants.COMMMA_SEPARATOR
								+ columnName;

						if (virtualParametersForFamily.containsKey(columnName)) {
							VirtualParametersVO objVirtualParametersVO = virtualParametersForFamily
									.get(columnName);
							parmDisplayNames.add(objVirtualParametersVO
									.getVirtualName());
							lstStackOrder.add(stackOrder);
						}

					}

					fromQuery = fromQuery
							+ ",GETS_TOOLS.GETS_TOOL_VIRTUAL_OUT VO,GETS_RMD_VEHICLE V";
					whereQuery = whereQuery
							+ "VO.FAULT_OBJID(+) = FAULT.OBJID AND FAULT2VEHICLE = V.OBJID AND V.OBJID IN(:assetNumber) AND ";
				}
				
				if (null != arlAnomParms && arlAnomParms.size() > 0) {
					Map<String,String> anomParms = getAnomParameters();
					for (Iterator iterator = arlAnomParms.iterator(); iterator
							.hasNext();) {

						String strMpNumberWithStackOrder = (String) iterator
								.next();
						String[] arrMpNumberWithStackOrder = strMpNumberWithStackOrder
								.split("~");
						String columnName = arrMpNumberWithStackOrder[0];
						String stackOrder = arrMpNumberWithStackOrder[1];

						selectQuery = selectQuery
								+ RMDCommonConstants.COMMMA_SEPARATOR
								+ columnName;

						if (anomParms.containsKey(columnName)) {							
							parmDisplayNames.add(anomParms.get(columnName));
							lstStackOrder.add(stackOrder);
						}

					}

					fromQuery = fromQuery
							+ ",GETS_TOOLS.GETS_TOOL_ANOM_OUT ANOM";
					whereQuery = whereQuery
							+ "ANOM.ANOM_OUT2FAULT(+) = FAULT.OBJID AND FAULT2VEHICLE = ANOM.ANOM_OUT2VEHICLE AND ANOM_OUT2VEHICLE IN(:assetNumber) AND ";
					if(selectQuery.contains(RMDServiceConstants.SUM_OF_RESIDUAL_VALUES)){						
						whereQuery = whereQuery
								+ "ANOM.SUM_OF_RESIDUAL_VALUES!=0 AND ";
					}
				}
				
				filterWhereQuery.append(whereQuery);
				filterFromQuery = filterSnapshotQuery(
						objVisualizationDetailsRequestVO, filterLookupValues,
						simpleOperators, complexOperators, parmDetailsMap,
						controllerCfg, fromQuery, filterWhereQuery);

				if (!RMDCommonConstants.EMPTY_STRING
						.equalsIgnoreCase(selectQuery)
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(fromQuery)
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(filterWhereQuery.toString())) {
					StringBuilder strBufQry = new StringBuilder();
					strBufQry
							.append("SELECT FAULT2VEHICLE,TO_CHAR(OCCUR_DATE,'MM/DD/YYYY HH24:MI:SS')"
									+ selectQuery
									+ " FROM GETS_TOOLS.GETS_TOOL_FAULT FAULT"
									+ filterFromQuery);
					strBufQry.append(" WHERE ");
					strBufQry.append(filterWhereQuery.toString());
					if(objVisualizationDetailsRequestVO.getNoOfDays() != null && !"".equals(objVisualizationDetailsRequestVO.getNoOfDays().trim())){
						strBufQry.append(" OCCUR_DATE > SYSDATE - :noOfDays ORDER BY OCCUR_DATE ASC");
					}else if (null != objVisualizationDetailsRequestVO.getFromDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getFromDate())
							&& null != objVisualizationDetailsRequestVO.getToDate()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getToDate())) {
						strBufQry.append(" OCCUR_DATE BETWEEN TO_DATE(:fromDate, 'MM/DD/YYYY HH24.MI.SS') AND TO_DATE(:toDate, 'MM/DD/YYYY HH24.MI.SS') ORDER BY OCCUR_DATE ASC");
					}
					Query parmdetailsQry = hibernateSession
							.createSQLQuery(strBufQry.toString());
					parmdetailsQry.setParameterList(
							RMDServiceConstants.ASSETNUMBER, assetObjidList);
					if(objVisualizationDetailsRequestVO.getNoOfDays() != null && !"".equals(objVisualizationDetailsRequestVO.getNoOfDays().trim())){
						parmdetailsQry.setParameter("noOfDays", objVisualizationDetailsRequestVO.getNoOfDays());
					}else if (null != objVisualizationDetailsRequestVO.getFromDate()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO.getFromDate())
							&& null != objVisualizationDetailsRequestVO.getToDate()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getToDate())) {
						parmdetailsQry.setParameter(
								RMDServiceConstants.FROM_DATE,
								objVisualizationDetailsRequestVO.getFromDate());
						parmdetailsQry.setParameter(
								RMDServiceConstants.TO_DATE,
								objVisualizationDetailsRequestVO.getToDate());
					}

					if (null != objVisualizationDetailsRequestVO.getFaultCode()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getFaultCode())) {
						List<String> arlFaultCode = Arrays
								.asList(objVisualizationDetailsRequestVO
										.getFaultCode().split(","));

						parmdetailsQry.setParameterList(
								RMDServiceConstants.FAULT_CODE, arlFaultCode);

					}

					if (null != objVisualizationDetailsRequestVO.getLocoState()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getLocoState())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.LOCO_STATE)) {
							List<String> arlLocoState = Arrays
									.asList(objVisualizationDetailsRequestVO
											.getLocoState().split(","));

							parmdetailsQry.setParameterList(
									RMDServiceConstants.LOCO_STATE_INPUT,
									arlLocoState);
						}
					}

					if (null != objVisualizationDetailsRequestVO
							.getNotchValue()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getNotchValue())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.NOTCH)) {

							List<String> arlnotchValue = Arrays
									.asList(objVisualizationDetailsRequestVO
											.getNotchValue().split(","));
							parmdetailsQry.setParameterList(
									RMDServiceConstants.NOTCH_INPUT,
									arlnotchValue);
						}
					}

					if (null != objVisualizationDetailsRequestVO
							.getAmbientTempOperator()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getAmbientTempOperator())
							&& null != objVisualizationDetailsRequestVO
									.getAmbientTempValue1()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getAmbientTempValue1())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.AMBIENT_TEMP)) {

							parmdetailsQry.setParameter(
									RMDServiceConstants.AMBIENT_TEMP_INPUT1,
									objVisualizationDetailsRequestVO
											.getAmbientTempValue1());
							if (complexOperators
									.contains(objVisualizationDetailsRequestVO
											.getAmbientTempOperator())) {
								parmdetailsQry
										.setParameter(
												RMDServiceConstants.AMBIENT_TEMP_INPUT2,
												objVisualizationDetailsRequestVO
														.getAmbientTempValue2());
							}
						}
					}

					if (null != objVisualizationDetailsRequestVO.getEngineGHPOperator()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPOperator())
							&& null != objVisualizationDetailsRequestVO.getEngineGHPValue1()
							&& !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objVisualizationDetailsRequestVO.getEngineGHPValue1())) {
						if (filterLookupValues.containsKey(RMDServiceConstants.SNAPSHOT_+ RMDServiceConstants.ENGINE_GHP)) {
								parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_GHP_INPUT1, 
										objVisualizationDetailsRequestVO.getEngineGHPValue1());
							if (complexOperators.contains(objVisualizationDetailsRequestVO.getEngineGHPOperator())) {
									parmdetailsQry.setParameter(RMDServiceConstants.ENGINE_GHP_INPUT2,
										objVisualizationDetailsRequestVO.getEngineGHPValue2());
							}

						}
					}

					if (null != objVisualizationDetailsRequestVO
							.getEngineSpeedOperator()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getEngineSpeedOperator())
							&& null != objVisualizationDetailsRequestVO
									.getEngineSpeedValue1()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getEngineSpeedValue1())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.ENGINE_SPEED)) {

							parmdetailsQry.setParameter(
									RMDServiceConstants.ENGINE_SPEED_INPUT1,
									objVisualizationDetailsRequestVO
											.getEngineSpeedValue1());
							if (complexOperators
									.contains(objVisualizationDetailsRequestVO
											.getEngineSpeedOperator())) {
								parmdetailsQry
										.setParameter(
												RMDServiceConstants.ENGINE_SPEED_INPUT2,
												objVisualizationDetailsRequestVO
														.getEngineSpeedValue2());
							}

						}
					}
					
					//added for Visuaization Anomaly
					
					
					if (null != objVisualizationDetailsRequestVO
							.getOilInletOp()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getOilInletOp())
							&& null != objVisualizationDetailsRequestVO
									.getOilInletValue1()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getOilInletValue1())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.OIL_INLET)) {

							parmdetailsQry.setParameter(
									RMDServiceConstants.OIL_INLET_INPUT1,
									objVisualizationDetailsRequestVO
											.getOilInletValue1());
							if (complexOperators
									.contains(objVisualizationDetailsRequestVO
											.getOilInletOp())) {
								parmdetailsQry.setParameter(
										RMDServiceConstants.OIL_INLET_INPUT2,
										objVisualizationDetailsRequestVO
												.getOilInletValue2());
							}

						}
					}
					
					
					if (null != objVisualizationDetailsRequestVO
							.getBarometricPressOp()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getBarometricPressOp())
							&& null != objVisualizationDetailsRequestVO
									.getBarometricPressValue1()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getBarometricPressValue1())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.BAROMETRIC_PRESS)) {

							parmdetailsQry.setParameter(
									RMDServiceConstants.BAROMETRIC_PRESS_INPUT1,
									objVisualizationDetailsRequestVO
											.getBarometricPressValue1());
							if (complexOperators
									.contains(objVisualizationDetailsRequestVO
											.getBarometricPressOp())) {
								parmdetailsQry.setParameter(
										RMDServiceConstants.BAROMETRIC_PRESS_INPUT2,
										objVisualizationDetailsRequestVO
												.getBarometricPressValue2());
							}

						}
					}
					
					
					if (null != objVisualizationDetailsRequestVO
							.getHpAvailableOp()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getHpAvailableOp())
							&& null != objVisualizationDetailsRequestVO
									.getHpAvailableValue1()
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(objVisualizationDetailsRequestVO
											.getHpAvailableValue1())) {
						if (filterLookupValues
								.containsKey(RMDServiceConstants.SNAPSHOT_
										+ RMDServiceConstants.HP_AVAILABLE)) {

							parmdetailsQry.setParameter(
									"hpAvailable1",
									objVisualizationDetailsRequestVO
											.getHpAvailableValue1());
							if (complexOperators
									.contains(objVisualizationDetailsRequestVO
											.getHpAvailableOp())) {
								parmdetailsQry.setParameter(
										RMDServiceConstants.HP_AVAILABLE_INPUT2,
										objVisualizationDetailsRequestVO
												.getHpAvailableValue2());
							}

						}
					}

					parmdetailsQry.setFetchSize(500);
					List<Object[]> arlParmDetails = parmdetailsQry.list();

					if (null != arlParmDetails && !arlParmDetails.isEmpty()) {

						for (Object[] object : arlParmDetails) {
							String strAssetObjid = RMDCommonUtility
									.convertObjectToString(object[0]);
							String assetNumberWithGrpName = RMDCommonConstants.EMPTY_STRING;
							DateFormat zoneFormater = new SimpleDateFormat(
									DateConstants.MMddyyyyHHmmss);
							String incidentTime = RMDCommonUtility
									.convertObjectToString(object[1]);
							GregorianCalendar objGregorianCalendar = new GregorianCalendar();
							objGregorianCalendar.setTime(zoneFormater
									.parse(incidentTime));
							RMDCommonUtility.setZoneOffsetTime(
									objGregorianCalendar,
									RMDCommonConstants.DateConstants.GMT);
							for (int i = 2; i < object.length; i++) {
								String displayName = RMDCommonConstants.EMPTY_STRING;
								if (isMultiAsset) {
									assetNumberWithGrpName = assetObjidMp
											.get(strAssetObjid);
									displayName = assetNumberWithGrpName
											+ RMDCommonConstants.UNDERSCORE
											+ parmDisplayNames.get(i - 2);
								} else {
									displayName = parmDisplayNames.get(i - 2);
								}
								String stackOrderNum = lstStackOrder.get(i - 2);
								if (multiAssetVizDetails
										.containsKey(displayName)) {
									VisualizationDetailsVO objDetailsVO = multiAssetVizDetails
											.get(displayName);
									objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
									objVisualizationParmDetailsVO
											.setOccurTime(objGregorianCalendar
													.getTimeInMillis());
									objVisualizationParmDetailsVO
											.setParmValue(RMDCommonUtility
													.convertObjectToString(object[i]));
									objDetailsVO.getArlParmdetails().add(
											objVisualizationParmDetailsVO);
								} else {

									VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
									objDetailsVO.setDisplayName(displayName);
									objDetailsVO.setStackOrder(stackOrderNum);
									objDetailsVO
											.setAsset(assetNumberWithGrpName);
									objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
									objVisualizationParmDetailsVO
											.setOccurTime(objGregorianCalendar
													.getTimeInMillis());
									objVisualizationParmDetailsVO
											.setParmValue(RMDCommonUtility
													.convertObjectToString(object[i]));
									objDetailsVO.getArlParmdetails().add(
											objVisualizationParmDetailsVO);
									multiAssetVizDetails.put(displayName,
											objDetailsVO);

								}
							}
						}

					} 
						for (int i = 0; i < parmDisplayNames.size(); i++) {
							for (int j = 0; j < assetObjidList.size(); j++) {

								String strAssetObjid = String
										.valueOf(assetObjidList.get(j));
								String assetNumberWithGrpName = RMDCommonConstants.EMPTY_STRING;
								String displayName = RMDCommonConstants.EMPTY_STRING;
								if (isMultiAsset) {
									assetNumberWithGrpName = assetObjidMp
											.get(strAssetObjid);
									displayName = assetNumberWithGrpName
											+ RMDCommonConstants.UNDERSCORE
											+ parmDisplayNames.get(i);
								} else {
									displayName = parmDisplayNames.get(i);
								}
								String stackOrderNum = lstStackOrder.get(i);
								if (!multiAssetVizDetails
										.containsKey(displayName)) {

									VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
									objDetailsVO.setDisplayName(displayName);
									objDetailsVO.setStackOrder(stackOrderNum);
									objDetailsVO
											.setAsset(assetNumberWithGrpName);
									multiAssetVizDetails.put(displayName,
											objDetailsVO);

								}
							}

						}

					

					arlDetailsVO = new ArrayList<VisualizationDetailsVO>(
							multiAssetVizDetails.values());
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(
					errorCode,
					new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objVisualizationDetailsRequestVO.getUserLanguage()),
					ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			if (e instanceof SQLGrammarException) {
				if (((SQLGrammarException) e).getSQLException().getMessage()
						.contains(RMDServiceConstants.ORA_00904)) {
					String errorCode = RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS_INVALID_COLUMN;
					throw new RMDDAOException(errorCode, new String[] {},
							RMDCommonUtility.getMessage(errorCode,
									new String[] {},
									objVisualizationDetailsRequestVO
											.getUserLanguage()), e,
							RMDCommonConstants.MINOR_ERROR);

				}

			} else {
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								objVisualizationDetailsRequestVO
										.getUserLanguage()), e,
						RMDCommonConstants.MAJOR_ERROR);
			}
		} finally {
			releaseSession(hibernateSession);
		}

		return arlDetailsVO;

	}
	
	/**
	 * This method is used for fetching virtual parameter records for drawing graph in visualization screen
	 *  
	 * @param VisualizationDetailsRequestVO
	 * @return List<VisualizationDetailsVO>
	 * @throws RMDDAOException
	 */	
	public Map<String,String> getAnomParameters() {
		Map<String,String> hmAnomParms = new HashMap<String, String>();
		try {
			List<GetSysLookupVO> arlSysLookUp = popupListAdminDAO.getPopupListValues(RMDServiceConstants.VISUALIZATION_ANOMALY_PARAMETERS);
			if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
				for (GetSysLookupVO objSysLookupVO : arlSysLookUp) {
					hmAnomParms.put(ESAPI.encoder().decodeForHTML(objSysLookupVO.getLookValue()), 
							ESAPI.encoder().decodeForHTML(objSysLookupVO.getListDescription()));
				}
			}

		} catch (RMDDAOException e) {
			throw e;
		}
		return hmAnomParms;

	}
	
	
	
	
	
	
	/**
	 * This method is used for fetching graphDetails of visualization screen
	 * 
	 * @param VisualizationGraphDetailsVO
	 * @return List<VisualizationGraphDetailsVO>
	 * @throws RMDDAOException
	 */
	@Override
	public RxVisualizationPlotInfoVO getVizPlotInformations(
			String rxObjid) throws RMDDAOException {
		Session hibernateSession = null;
		RxVisualizationPlotInfoVO objRxVisualizationPlotInfoVO = null;		
		try {
			hibernateSession = getHibernateSession();
			StringBuilder strQry = new StringBuilder();
			strQry.append("SELECT RP.TITLE,INDEPENDENT_AXIS_LABEL,LEFT_DEPENDENT_AXIS_LABEL,RIGHT_DEPENDENT_AXIS_LABEL,DATASET_1_PARAMETER_NAME,DATASET_1_LABEL,DATASET_1_AXIS,DATASET_2_PARAMETER_NAME,DATASET_2_LABEL,");
			strQry.append(" DATASET_2_AXIS,DATASET_3_PARAMETER_NAME,DATASET_3_LABEL,DATASET_3_AXIS,DATASET_4_PARAMETER_NAME,DATASET_4_LABEL,DATASET_4_AXIS FROM GETS_SD_RECOM_PLOT RP,GETS_SD_RECOM R");
			strQry.append(" WHERE RP.OBJID = R.RECOM2PLOT");
			strQry.append(" AND R.OBJID = :rxObjid");
			Query qry = hibernateSession.createSQLQuery(strQry.toString());
			qry.setParameter(RMDCommonConstants.RX_OBJID, rxObjid);
			qry.setFetchSize(1);
			List<Object[]> arlGraphDetails = qry.list();
			if (null != arlGraphDetails && !arlGraphDetails.isEmpty()) {
				for (Object[] obj : arlGraphDetails) {
					objRxVisualizationPlotInfoVO = new RxVisualizationPlotInfoVO();
					objRxVisualizationPlotInfoVO.setTitle(RMDCommonUtility.convertObjectToString(obj[0]));
					objRxVisualizationPlotInfoVO.setIndependentAxisLabel(RMDCommonUtility.convertObjectToString(obj[1]));
					objRxVisualizationPlotInfoVO.setLeftDependentAxisLabel(RMDCommonUtility.convertObjectToString(obj[2]));
					objRxVisualizationPlotInfoVO.setRightDependentAxisLabel(RMDCommonUtility.convertObjectToString(obj[3]));
					objRxVisualizationPlotInfoVO.setDataset1ParameterName(RMDCommonUtility.convertObjectToString(obj[4]));
					objRxVisualizationPlotInfoVO.setDataset1Label(RMDCommonUtility.convertObjectToString(obj[5]));
					objRxVisualizationPlotInfoVO.setDataset1Axis(RMDCommonUtility.convertObjectToString(obj[6]));
					objRxVisualizationPlotInfoVO.setDataset2ParameterName(RMDCommonUtility.convertObjectToString(obj[7]));
					objRxVisualizationPlotInfoVO.setDataset2Label(RMDCommonUtility.convertObjectToString(obj[8]));
					objRxVisualizationPlotInfoVO.setDataset2Axis(RMDCommonUtility.convertObjectToString(obj[9]));
					objRxVisualizationPlotInfoVO.setDataset3ParameterName(RMDCommonUtility.convertObjectToString(obj[10]));
					objRxVisualizationPlotInfoVO.setDataset3Label(RMDCommonUtility.convertObjectToString(obj[11]));
					objRxVisualizationPlotInfoVO.setDataset3Axis(RMDCommonUtility.convertObjectToString(obj[12]));
					objRxVisualizationPlotInfoVO.setDataset4ParameterName(RMDCommonUtility.convertObjectToString(obj[13]));
					objRxVisualizationPlotInfoVO.setDataset4Label(RMDCommonUtility.convertObjectToString(obj[14]));
					objRxVisualizationPlotInfoVO.setDataset4Axis(RMDCommonUtility.convertObjectToString(obj[15]));
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
					RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return objRxVisualizationPlotInfoVO;
	}
	
	public VehicleDetailsVO getVehicleInfo(String assetObjid) throws Exception {
		Session hibernateSession = null;
		VehicleDetailsVO objDetailsVO = null;
		try {
			hibernateSession = getHibernateSession();
			Query qry = hibernateSession
					.createSQLQuery(RMDServiceConstants.SQL_VEHICLE_INFO_QUERY);
			qry.setParameter(RMDCommonConstants.OBJ_ID, assetObjid);
			qry.setFetchSize(1);
			List<Object[]> arlVehicleInfo = qry.list();
			if (null != arlVehicleInfo && !arlVehicleInfo.isEmpty()) {
				for (Object[] obj : arlVehicleInfo) {
					objDetailsVO = new VehicleDetailsVO();
					objDetailsVO.setFamily(RMDCommonUtility
							.convertObjectToString(obj[2]));
					objDetailsVO.setControllerId(RMDCommonUtility
							.convertObjectToInt(obj[1]));
					objDetailsVO.setMeasurementSystemId(RMDCommonUtility
							.convertObjectToInt(obj[3]));
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return objDetailsVO;
	}
	
	private List<VisualizationDetailsVO> getParameterDefinitionsQuery(String vehicleFamily,
			int controllerSourceId, String parameterNamesList) throws Exception {
		Session hibernateSession = null;
		StringBuffer parameterDefinitionsQueryBuffer = new StringBuffer();
		String familyName = null;	
		List<VisualizationDetailsVO> arlDetailsVO=new ArrayList<VisualizationDetailsVO>();
		VisualizationDetailsVO objDetailsVO=null;
		try{
		hibernateSession = getHibernateSession();
		parameterDefinitionsQueryBuffer.append(RMDServiceConstants.SQL_PARAMETER_DEFINITIONS_QUERY_BASE);			
		parameterDefinitionsQueryBuffer.append(parameterNamesList).append(")");		
		if (vehicleFamily != null && !vehicleFamily.isEmpty()) {
			vehicleFamily = vehicleFamily.toUpperCase();
			if (vehicleFamily.equals(RMDServiceConstants.STR_ACCCA) || vehicleFamily.equals(RMDServiceConstants.STR_DCCCA)) {
				familyName = RMDServiceConstants.STR_CCA;
			}
			else {
				familyName = vehicleFamily;
			}
			parameterDefinitionsQueryBuffer.append(" AND cn.family = '").append(familyName).append("'");
			
			if (!familyName.equalsIgnoreCase(RMDServiceConstants.STR_BSS) && !familyName.equalsIgnoreCase(RMDServiceConstants.STR_AC6000) && !familyName.equalsIgnoreCase(RMDServiceConstants.STR_MNS)) {
				parameterDefinitionsQueryBuffer.append(" AND pd.controller_source_id (+) = ")
				.append(controllerSourceId);
			}
		}
		parameterDefinitionsQueryBuffer.append(RMDServiceConstants.SQL_PARAMETER_DEFINITIONS_QUERY_UNION);
		parameterDefinitionsQueryBuffer.append(parameterNamesList).append(")");		
		if (familyName != null && !familyName.isEmpty()) {
			parameterDefinitionsQueryBuffer.append(" AND cn.family = '")
			.append(familyName).append("'");
		}
		
		Query qry = hibernateSession
				.createSQLQuery(parameterDefinitionsQueryBuffer.toString());
		qry.setFetchSize(10);
		List<Object[]> arlParametersList = qry.list();
		if (null != arlParametersList && !arlParametersList.isEmpty()) {
				for (Object[] obj : arlParametersList) {
					objDetailsVO = new VisualizationDetailsVO();
					objDetailsVO.setParmNumber(RMDCommonUtility
							.convertObjectToString(obj[0]));
					
					objDetailsVO.setParmType(RMDCommonUtility
							.convertObjectToString(obj[1]));
					
					objDetailsVO.setColumnName(RMDCommonUtility
							.convertObjectToString(obj[2]));
					
					objDetailsVO.setTableName(RMDCommonUtility
							.convertObjectToString(obj[3]));
					objDetailsVO.setParmLoadColumn(RMDCommonUtility
							.convertObjectToString(obj[4]));
					
					objDetailsVO.setSourceUom(RMDCommonUtility
							.convertObjectToString(obj[5]));
					arlDetailsVO.add(objDetailsVO);
				}
		}
		}catch(Exception e){
			throw e;
		}finally{
			releaseSession(hibernateSession);
		}
		return arlDetailsVO;
	}
	
	
	public List<VisualizationDetailsVO> getRxVisualizationDetails(VisualizationDetailsRequestVO objVisualizationDetailsRequestVO) {
		Session hibernateSession = null;
		String queryString = null;
		Map<String,Long> paramUnitOfMeasureMap = new HashMap<String, Long>();
		StringBuffer dataFetcQueryBuffer = new StringBuffer(RMDServiceConstants.SQL_DATA_FETCH_QUERY_BASE);
		int tableCounter = 0;
		Set<String> tableNamesSet = new HashSet<String>();
		StringBuffer mpTableList = new StringBuffer();
		StringBuffer mpColumnNamesList = new StringBuffer();
		StringBuffer recordFilterClauses = new StringBuffer();
		StringBuffer mpTableJoinClauses  = new StringBuffer();
		String tableName = null;
		String tableAlias = null;
		VehicleDetailsVO objVehDetailsVO=null;
		String vehicleFamily=null;
		String assetObjid=null;
		RxVisualizationPlotInfoVO objRxVisualizationPlotInfoVO=null;
		String parametersList=RMDCommonConstants.EMPTY_STRING;
		Map<String,List<String>> parmColumnNames=new HashMap<String,List<String>>();
		List<String> parmDetails = null;
		List<String> arlColumns=new ArrayList<String>();
		Map<String, VisualizationDetailsVO> multiAssetVizDetails = new LinkedHashMap<String, VisualizationDetailsVO>();
		List<VisualizationDetailsVO> arlDetailsVO=null;
		Map<String,String> measurementLookup= new HashMap<String, String>();
		String uom=RMDCommonConstants.EMPTY_STRING;
		Map<String,UnitOfMeasureVO> mpUom = null;
		try {
			hibernateSession = getHibernateSession();
		assetObjid=objVisualizationDetailsRequestVO.getAssetObjid();
		if(null!=objVisualizationDetailsRequestVO.getAssetObjid()){
		objVehDetailsVO=getVehicleInfo(assetObjid);
		}
		objRxVisualizationPlotInfoVO=getVizPlotInformations(objVisualizationDetailsRequestVO.getRxObjid());
		if(null!=objRxVisualizationPlotInfoVO){
		if(null!=objRxVisualizationPlotInfoVO.getDataset1ParameterName()){
			parametersList+="'"+objRxVisualizationPlotInfoVO.getDataset1ParameterName()+"'";
			parmDetails=new ArrayList<String>();
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset1Label());
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset1Axis());
			parmColumnNames.put(objRxVisualizationPlotInfoVO.getDataset1ParameterName(),parmDetails);
			
		}
		if(null!=objRxVisualizationPlotInfoVO.getDataset2ParameterName()){
			parametersList+=",'"+objRxVisualizationPlotInfoVO.getDataset2ParameterName()+"'";
			parmDetails=new ArrayList<String>();
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset2Label());
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset2Axis());
			parmColumnNames.put(objRxVisualizationPlotInfoVO.getDataset2ParameterName(),parmDetails);
		}
		if(null!=objRxVisualizationPlotInfoVO.getDataset3ParameterName()){
			parametersList+=",'"+objRxVisualizationPlotInfoVO.getDataset3ParameterName()+"'";
			parmDetails=new ArrayList<String>();
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset3Label());
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset3Axis());
			parmColumnNames.put(objRxVisualizationPlotInfoVO.getDataset3ParameterName(),parmDetails);
		}
		
		if(null!=objRxVisualizationPlotInfoVO.getDataset4ParameterName()){
			parametersList+=",'"+objRxVisualizationPlotInfoVO.getDataset4ParameterName()+"'";
			parmDetails=new ArrayList<String>();
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset4Label());
			parmDetails.add(objRxVisualizationPlotInfoVO.getDataset4Axis());
			parmColumnNames.put(objRxVisualizationPlotInfoVO.getDataset4ParameterName(),parmDetails);
		}
		
		vehicleFamily=objVehDetailsVO.getFamily();
		measurementLookup=getMeasurementLookUpValues(RMDServiceConstants.MEASUREMENT_SYSYTEM);
		if(null!=measurementLookup){
		uom=measurementLookup.get(objVehDetailsVO.getMeasurementSystemId());
		}
		if(null!=uom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(uom)){
		mpUom=convertSourceToTarget(uom);
		}
		if (vehicleFamily != null && !vehicleFamily.isEmpty()) {
			vehicleFamily = vehicleFamily.toUpperCase();
			if (vehicleFamily.equals(RMDServiceConstants.STR_ACCCA) || vehicleFamily.equals(RMDServiceConstants.STR_DCCCA)) {
				if (vehicleFamily.equals(RMDServiceConstants.STR_ACCCA)) {
					tableName =RMDServiceConstants.MP_AC_TABLE_NAME;
				}
				else {
					tableName = RMDServiceConstants.MP_DC_TABLE_NAME;
				}
				vehicleFamily = RMDServiceConstants.STR_CCA;
				recordFilterClauses.append(" WHERE fault_code = '10-0305' AND ")
				.append(RMDServiceConstants.MP_HP_AVAILABLE_PARAM_FIELD_NAME)
				.append(" >= 4000 AND ")
				.append(RMDServiceConstants.MP_ACTUAL_RPM_PARAM_FIELD_NAME)
				.append(" > 990 AND ABS(")
				.append(RMDServiceConstants.MP_ACTUAL_RPM_PARAM_FIELD_NAME)
				.append(" - ")
				.append(RMDServiceConstants.MP_DESIRED_RPM_PARAM_FIELD_NAME)
				.append(") < 5");
				tableNamesSet.add(tableName.toUpperCase());
				tableCounter++;
				tableAlias = "mp"+tableCounter;
				mpTableList.append(", ")
				.append(tableName)
				.append(" ")
				.append(tableAlias);
				mpTableJoinClauses.append(" AND ")
				.append(tableAlias)
				.append(".parm2fault (+) = t27.objid");
			}
		}
		
		List<VisualizationDetailsVO> arlDetailsVOs=getParameterDefinitionsQuery(vehicleFamily,objVehDetailsVO.getControllerId(),parametersList);
			
			
			if (null!=arlDetailsVOs) {
				
				String parameterType;
				String columnName;
				String parameterLoadTable;
				String parameterLoadColumn;
				String sourceUOMId;
				for (Iterator iterator = arlDetailsVOs.iterator(); iterator
						.hasNext();) {
					VisualizationDetailsVO visualizationDetailsVO = (VisualizationDetailsVO) iterator
							.next();						
					parameterType = visualizationDetailsVO.getParmType();
					columnName =visualizationDetailsVO.getColumnName();
					arlColumns.add(columnName);
					parameterLoadTable =  visualizationDetailsVO.getTableName();
					parameterLoadColumn =  visualizationDetailsVO.getParmLoadColumn();
					sourceUOMId = visualizationDetailsVO.getSourceUom();
					
					if (!paramUnitOfMeasureMap.containsKey(columnName)) {
						if(null!=sourceUOMId&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(sourceUOMId)){
						paramUnitOfMeasureMap.put(columnName, new Long(sourceUOMId));
						}else{
							paramUnitOfMeasureMap.put(columnName,null);
						}
						mpColumnNamesList.append(", ").append(parameterLoadColumn);
						
						if (parameterType != null && !parameterType.equals("Standard")) {
							if (parameterType.equals("Virtual")) {
								tableName = RMDServiceConstants.VP_TABLE_NAME;
							}
							else {
								tableName = parameterLoadTable;
							}
							if (!tableNamesSet.contains(tableName)) {
								tableNamesSet.add(tableName.toUpperCase());
								tableCounter++;
								tableAlias = "mp"+tableCounter;
								mpTableList.append(", ")
								.append(tableName)
								.append(" ")
								.append(tableAlias);

								if (tableCounter == 1 && recordFilterClauses.length() == 0) {
									mpTableJoinClauses.append(" WHERE ");
								}
								else {
									mpTableJoinClauses.append(" AND ");
								}
								if (parameterType.equalsIgnoreCase("Virtual")) {
									mpTableJoinClauses.append(tableAlias)
									.append(".fault_objid (+) = t27.objid");
								}
								else {
									mpTableJoinClauses.append(tableAlias)
									.append(".parm2fault (+) = t27.objid");
									if (vehicleFamily.equalsIgnoreCase(RMDServiceConstants.STR_BSS)) {
										mpTableJoinClauses.append(" AND ")
										.append(tableAlias)
										.append(".sample_no (+) = 0");
									}
								}
							}
						}
					}
				}
								
				dataFetcQueryBuffer.append(mpColumnNamesList)
				.append(" FROM t27")
				.append(mpTableList)
				.append(recordFilterClauses)
				.append(mpTableJoinClauses)
				.append(" ORDER BY t27.occur_date ASC");
				
				Query qry = hibernateSession
						.createSQLQuery(dataFetcQueryBuffer.toString());
				qry.setParameter(RMDServiceConstants.OBJID, objVisualizationDetailsRequestVO.getAssetObjid());
				qry.setFetchSize(10);
				List<Object[]> arlParmDetails = qry.list();
				if (null != arlParmDetails && !arlParmDetails.isEmpty()) {
					VisualizationParmDetailsVO objVisualizationParmDetailsVO=null;
					for (Object[] object : arlParmDetails) {
						DateFormat zoneFormater = new SimpleDateFormat(DateConstants.MMddyyyyHHmmss);
						String incidentTime = RMDCommonUtility.convertObjectToString(object[1]);
						GregorianCalendar objGregorianCalendar = new GregorianCalendar();
						objGregorianCalendar.setTime(zoneFormater.parse(incidentTime));
						RMDCommonUtility.setZoneOffsetTime(objGregorianCalendar, RMDCommonConstants.DateConstants.GMT);
						for (int i = 2; i < object.length; i++) {
							String displayName = RMDCommonConstants.EMPTY_STRING;
							String strColumnName=RMDCommonConstants.EMPTY_STRING;
							String axis=RMDCommonConstants.EMPTY_STRING;							
							strColumnName=arlColumns.get(i - 2);
							List<String> arlDet=parmColumnNames.get(strColumnName);
							if(null!=arlDet&&arlDet.size()>=2){
							displayName = arlDet.get(0);
							axis=arlDet.get(1);
							}
							if (multiAssetVizDetails.containsKey(displayName)) {
								VisualizationDetailsVO objDetailsVO = multiAssetVizDetails.get(displayName);
								objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
								objVisualizationParmDetailsVO.setOccurTime(objGregorianCalendar.getTimeInMillis());
								if(null!=uom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(uom)&&!RMDCommonConstants.US.equalsIgnoreCase(uom)){
									if(null!=strColumnName){
										UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(paramUnitOfMeasureMap.get(strColumnName));
										if (null != objUnitOfMeasureVO&&RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objUnitOfMeasureVO.getIsConversionRequired())) {
											String conversionFormula=objUnitOfMeasureVO.getConversionExp();
											objVisualizationParmDetailsVO.setParmValue(AppSecUtil.convertMeasurementSystem(conversionFormula, RMDCommonUtility.convertObjectToString(object[i])));
									   }else{
										   objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									   }
									 }else{
										 objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									 }
								  }else{
									  objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
								  }
								objDetailsVO.getArlParmdetails().add(objVisualizationParmDetailsVO);
							} else {
								VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
								objDetailsVO.setDisplayName(displayName);
								objDetailsVO.setAxisSide(axis);
								objDetailsVO.setLeftDependentAxis(objRxVisualizationPlotInfoVO.getLeftDependentAxisLabel());
								objDetailsVO.setRightDependentAxis(objRxVisualizationPlotInfoVO.getRightDependentAxisLabel());
								objDetailsVO.setIndependentAxis(objRxVisualizationPlotInfoVO.getIndependentAxisLabel());
								objDetailsVO.setTitle(objRxVisualizationPlotInfoVO.getTitle());
								objVisualizationParmDetailsVO = new VisualizationParmDetailsVO();
								objVisualizationParmDetailsVO.setOccurTime(objGregorianCalendar.getTimeInMillis());
								if(null!=uom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(uom)&&!RMDCommonConstants.US.equalsIgnoreCase(uom)){
									if(null!=strColumnName){
										UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(paramUnitOfMeasureMap.get(strColumnName));
										if (null != objUnitOfMeasureVO&&RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objUnitOfMeasureVO.getIsConversionRequired())) {
											String conversionFormula=objUnitOfMeasureVO.getConversionExp();
											objVisualizationParmDetailsVO.setParmValue(AppSecUtil.convertMeasurementSystem(conversionFormula, RMDCommonUtility.convertObjectToString(object[i])));
									   }else{
										   objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									   }
									 }else{
										 objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
									 }
								  }else{
									  objVisualizationParmDetailsVO.setParmValue(RMDCommonUtility.convertObjectToString(object[i]));
								  }
								objDetailsVO.getArlParmdetails().add(objVisualizationParmDetailsVO);
								multiAssetVizDetails.put(displayName, objDetailsVO);

							}
						}
					}

				} else{					
					for (int i = 0; i < arlColumns.size(); i++) {
						String displayName = RMDCommonConstants.EMPTY_STRING;
						String axis=RMDCommonConstants.EMPTY_STRING;
						String strColumnName=arlColumns.get(i);
						List<String> arlDet=parmColumnNames.get(strColumnName);
						if(null!=arlDet&&arlDet.size()>=2){
						displayName = arlDet.get(0);
						axis=arlDet.get(1);
						}
					VisualizationDetailsVO objDetailsVO = new VisualizationDetailsVO();
					objDetailsVO.setDisplayName(displayName);
					objDetailsVO.setAxisSide(axis);
					objDetailsVO.setLeftDependentAxis(objRxVisualizationPlotInfoVO.getLeftDependentAxisLabel());
					objDetailsVO.setRightDependentAxis(objRxVisualizationPlotInfoVO.getRightDependentAxisLabel());
					objDetailsVO.setIndependentAxis(objRxVisualizationPlotInfoVO.getIndependentAxisLabel());	
					objDetailsVO.setTitle(objRxVisualizationPlotInfoVO.getTitle());
					multiAssetVizDetails.put(displayName, objDetailsVO);
				}
				}
				arlDetailsVO = new ArrayList<VisualizationDetailsVO>(multiAssetVizDetails.values());
			}
			}
		}
		catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_VISUALIZATION_GRAPHS);
			throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode, new String[] {},
					RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		}
		finally { //  Close the results and statement
			releaseSession(hibernateSession);
		}
		return arlDetailsVO;
	}
	
	
	public Map<String, String> getMeasurementLookUpValues(String listName) {

		Map<String, String> lookupValues = new LinkedHashMap<String, String>();
		try {
			List<GetSysLookupVO> arlSysLookUp = popupListAdminDAO.getPopupListValues(listName);
			if (null != arlSysLookUp && !arlSysLookUp.isEmpty()) {
				for (GetSysLookupVO objSysLookupVO : arlSysLookUp) {
					lookupValues.put(String.valueOf(objSysLookupVO.getGetSysLookupSeqId()), objSysLookupVO.getLookValue());
				}
			}
		} catch (RMDDAOException e) {
			throw e;
		}
		return lookupValues;
	}

}
