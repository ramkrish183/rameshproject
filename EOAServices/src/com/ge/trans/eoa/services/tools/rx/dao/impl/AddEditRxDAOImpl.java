/**
 * ============================================================
 * File : AddEditRxDAOImpl.java
 * Description : DAO Implementation for Recommendation screen
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.impl
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
package com.ge.trans.eoa.services.tools.rx.dao.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.OracleCodec;
import org.springframework.beans.factory.annotation.Value;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.common.util.AliasToEntityMapResultTransformerUtil;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmRecomHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmRecomHistHVO;
import com.ge.trans.rmd.tools.common.utils.RxPdfGenerator;
import com.ge.trans.rmd.tools.common.valueobjects.RxTaskVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.AddEditRxDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxPlotVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskResultVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxConfigVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxHistServiceVO;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.RxTaskRepairCodeVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.Base64;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;

/*******************************************************************************
 * 
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Nov 16, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to perform database related manipulations
 * @History :
 * 
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class AddEditRxDAOImpl extends BaseDAO implements AddEditRxDAOIntf {

	private static final long serialVersionUID = 4419105547442705716L;
	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(AddEditRxDAOImpl.class);
	@Value("${RX_DATA_UPLOAD_LOC}")
	private String rxDataUrl;

	@Value("${RX_DATA_DB_PATH}")
	private String rxDataDBURL;

	@Value("${RX_DOMAIN_PATH}")
	private String domainPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.cases.dao.intf.NotesDAOIntf#fetchRecommDetails
	 * (com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
	 * This method is used to fetch the advisory details from database
	 */
	@Override
	public void fetchRecommDetails(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {
		List<Object[]> arlRecomDetails = null;
		SimpleDateFormat sdfDestination = new SimpleDateFormat(
				RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
		Session fetchRecommSession = null;
		String strRXId = null;
		String strFromPage = RMDCommonConstants.EMPTY_STRING;
		StringBuilder rxqueryBuilder = null;
		Query nativeQuery = null;
		List<String> lstModel;
		List<String> lstCustomer;
		List<String> lstFleet;
		List<String> lstSubsystem;
		ArrayList<RxConfigVO> arlRxConfigVO = null;
		RxConfigVO objRxConfigVO = null;
		RxHistServiceVO objRxHistServiceVO = null;
		ArrayList<RxHistServiceVO> arlRxHistory = null;
		String strRxType = null;
		String strRxStatus = null;
		String strCreateDate = null;
		String strLastUpdateDate = null;
		boolean previousQryhasData = false;
		String strLanguage = null;
		Codec ORACLE_CODEC = new OracleCodec();
		AddEditRxPlotVO addEditRxPlotVO = null;
		String recomToPlotObjid = null;
		try {
			LOG.debug("Start fetchRecommDetails");
			strRXId = addeditRxServiceVO.getStrRxId();
			strLanguage = addeditRxServiceVO.getStrLanguage();
			strFromPage = addeditRxServiceVO.getStrFromPage();
			fetchRecommSession = getHibernateSession();
			// Query to fetch the recommendation details
			rxqueryBuilder = new StringBuilder();
			rxqueryBuilder.append("SELECT SDREC.TITLE, ");
			rxqueryBuilder.append("  SDREC.LOCO_IMPACT, ");
			rxqueryBuilder.append("  SDREC.VERSION, ");
			rxqueryBuilder.append("  SDREC.RECOM_TYPE, ");
			rxqueryBuilder.append("  SDREC.RECOM_STATUS, ");
			rxqueryBuilder.append("  SDREC.URGENCY, ");
			rxqueryBuilder.append("  SDREC.EST_REPAIR_TIME, ");
			rxqueryBuilder
					.append("  TO_CHAR(SDREC.CREATION_DATE,'MM/DD/YYYY HH24:MI:SS') CREATION_DATE, ");
			rxqueryBuilder
					.append("  TO_CHAR(SDREC.LAST_UPDATED_DATE,'MM/DD/YYYY HH24:MI:SS') LAST_UPDATED_DATE, ");
			rxqueryBuilder.append("  SDREC.CREATED_BY, ");
			rxqueryBuilder.append("  SDREC.LAST_UPDATED_BY, ");
			rxqueryBuilder.append("  TQ.OBJID QUEUE_TITLE,");
			rxqueryBuilder.append("  SDREC.EXPORT_CONTROL, ");
			rxqueryBuilder.append("  SDREC.RECOM2PLOT, SDREC.DESCRIPTION ");
			rxqueryBuilder
					.append(" FROM gets_sd_recom sdrec, gets_sd_recom sdrec1,");
			rxqueryBuilder.append("  TABLE_QUEUE TQ ");
			rxqueryBuilder
					.append(" WHERE TQ.OBJID(+)   = SDREC.RECOM_MOVE2QUEUE ");
			rxqueryBuilder.append(" AND SDREC1.OBJID(+) = SDREC.RECOM2RECOM ");
			rxqueryBuilder.append(" AND SDREC.OBJID     =:recomId ");
			nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
					.toString());
			nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
					.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
			arlRecomDetails = nativeQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
				previousQryhasData = true;
				sdfDestination = new SimpleDateFormat(
						RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
				for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
					Object recomDetails[] = (Object[]) arlRecomDetails.get(idx);
					addeditRxServiceVO.setStrTitle(AppSecUtil
							.htmlEscaping(RMDCommonUtility
									.convertObjectToString(recomDetails[0])));
					addeditRxServiceVO.setStrSelAssetImp(RMDCommonUtility
							.convertObjectToString(recomDetails[1]));
					addeditRxServiceVO.setStrRevNo(RMDCommonUtility
							.convertObjectToString(recomDetails[2]));
					strRxStatus = RMDCommonUtility
							.convertObjectToString(recomDetails[4]);
					if (!RMDCommonUtility.isNullOrEmpty(strRxStatus)) {
						if (RMDServiceConstants.INPROCESS
								.equalsIgnoreCase(strRxStatus)) {
							addeditRxServiceVO
									.setStrRxStatus(RMDServiceConstants.DRAFT);
						} else {
							addeditRxServiceVO.setStrRxStatus(strRxStatus);
						}
					}
					strRxType = RMDCommonUtility
							.convertObjectToString(recomDetails[3]);
					if (!RMDCommonUtility.isNullOrEmpty(strRxType)) {
						if (RMDServiceConstants.NONE
								.equalsIgnoreCase(strRxType)) {
							addeditRxServiceVO
									.setStrRxType(RMDServiceConstants.CUSTOM);
						} else {
							addeditRxServiceVO.setStrRxType(strRxType);
						}
					}
					addeditRxServiceVO.setStrSelUrgRepair(RMDCommonUtility
							.convertObjectToString(recomDetails[5]));
					addeditRxServiceVO.setStrSelEstmTimeRepair(RMDCommonUtility
							.convertObjectToString(recomDetails[6]));
					strCreateDate = RMDCommonUtility
							.convertObjectToString(recomDetails[7]);
					if (!RMDCommonUtility.isNullOrEmpty(strCreateDate)) {
						addeditRxServiceVO.setCreationDate(sdfDestination
								.parse(strCreateDate));
					}
					strLastUpdateDate = RMDCommonUtility
							.convertObjectToString(recomDetails[8]);
					if (!RMDCommonUtility.isNullOrEmpty(strLastUpdateDate)) {
						addeditRxServiceVO.setLastUpdatedDate(sdfDestination
								.parse(strLastUpdateDate));
					}
					addeditRxServiceVO.setStrCreatedBy(RMDCommonUtility
							.convertObjectToString(recomDetails[9]));
					addeditRxServiceVO.setStrLastUpdatedBy((RMDCommonUtility
							.convertObjectToString(recomDetails[10])));
					addeditRxServiceVO.setStrQueue(RMDCommonUtility
							.convertObjectToString(recomDetails[11]));
					addeditRxServiceVO.setStrExportControl(RMDCommonUtility
							.convertObjectToString(recomDetails[12]));
					recomToPlotObjid = RMDCommonUtility
							.convertObjectToString(recomDetails[13]);
					addeditRxServiceVO.setStrDescription(RMDCommonUtility
							.convertObjectToString(recomDetails[14]));
				}
			}

			if (previousQryhasData) {
				arlRecomDetails.clear();
				rxqueryBuilder = new StringBuilder();
				nativeQuery = null;
				// Query to fetch recommendation rank,precision, #Deliveries

				rxqueryBuilder.append("SELECT RX_RANK, ");
				rxqueryBuilder.append("  RX_PRECISION,");
				rxqueryBuilder.append("  DELV_CNT_NOREISSUE");
				rxqueryBuilder.append(" FROM GETS_TOOL_RX_METRICS");
				rxqueryBuilder.append(" WHERE RX_METRICS2RECOM =:recomId");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, strRXId);
				arlRecomDetails = nativeQuery.list();
				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
						addeditRxServiceVO.setStrRank(RMDCommonUtility
								.convertObjectToString(recomDetails[0]));
						addeditRxServiceVO.setStrPrecision(RMDCommonUtility
								.convertObjectToString(recomDetails[1]));
						addeditRxServiceVO.setStrDeliveries(RMDCommonUtility
								.convertObjectToString(recomDetails[2]));
					}
				}

				arlRecomDetails.clear();
				nativeQuery = null;
				rxqueryBuilder = new StringBuilder();

				// Query to fetch model
				rxqueryBuilder
						.append("SELECT MTM.RECOM_MODEL2MODEL, GRM.MODEL_NAME ");
				rxqueryBuilder.append("FROM   GETS_SD_RECOM_MTM_MODEL MTM, ");
				rxqueryBuilder.append(" GETS_RMD_MODEL GRM ");
				rxqueryBuilder
						.append(" WHERE  MTM.RECOM_MODEL2MODEL = GRM.OBJID AND ");
				rxqueryBuilder.append(" MTM.RECOM_MODEL2RECOM =:recomId ");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
						.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
				arlRecomDetails = nativeQuery.list();
				lstModel = new ArrayList<String>();
				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
						lstModel.add(RMDCommonUtility
								.convertObjectToString(recomDetails[0])
								+ RMDCommonConstants.PIPELINE_CHARACTER
								+ RMDCommonUtility
										.convertObjectToString(recomDetails[1]));
					}
					addeditRxServiceVO.setModelList(lstModel);
				}

				arlRecomDetails.clear();
				
				//query to fetch customer
				
				nativeQuery = null;
				rxqueryBuilder = new StringBuilder();
				String exclude = null;
				// Query to fetch model
				rxqueryBuilder
						.append("SELECT MTM.CUST2BUSORG, ORG.ORG_ID, MTM.EXCLUDE AS EXCLUDE ");
				rxqueryBuilder.append("FROM   GETS_SD_RECOM_MTM_CUST MTM, ");
				rxqueryBuilder.append(" TABLE_BUS_ORG ORG ");
				rxqueryBuilder
						.append(" WHERE  MTM.CUST2BUSORG = ORG.OBJID AND ");
				rxqueryBuilder.append(" MTM.CUST2RECOM =:recomId ");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
						.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
				arlRecomDetails = nativeQuery.list();
				lstCustomer = new ArrayList<String>();
				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
						lstCustomer.add(RMDCommonUtility
								.convertObjectToString(recomDetails[0])
								+ RMDCommonConstants.PIPELINE_CHARACTER
								+ RMDCommonUtility
										.convertObjectToString(recomDetails[1]));
						if(RMDCommonUtility.convertObjectToString(recomDetails[2]) != null){
							exclude = RMDCommonUtility.convertObjectToString(recomDetails[2]);
						}
					}
					addeditRxServiceVO.setCustomerList(lstCustomer);
				}
				addeditRxServiceVO.setExclude(exclude);
				arlRecomDetails.clear();
				nativeQuery = null;
				rxqueryBuilder = new StringBuilder();
				rxqueryBuilder.append("SELECT FLEET.OBJID,FLEET.FLEET_NUMBER,CUSTOMER.OBJID as CUSTOMER, MTM.EXCLUDE AS EXCLUDE from GETS_SD_RECOM_MTM_FLEET MTM,GETS_RMD_FLEET FLEET,TABLE_BUS_ORG CUSTOMER,GETS_SD_RECOM_MTM_CUST MTMCUST ");
				rxqueryBuilder.append(" WHERE CUSTOMER.OBJID        =FLEET.FLEET2BUS_ORG");
				rxqueryBuilder.append(" AND MTM.FLEET2CUST=MTMCUST.OBJID AND MTM.FLEET2FLEET=FLEET.OBJID AND  MTMCUST.CUST2BUSORG = CUSTOMER.OBJID");
				rxqueryBuilder.append(" AND MTMCUST.CUST2RECOM =:recomId ");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
						.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
				arlRecomDetails = nativeQuery.list();
				lstFleet = new ArrayList<String>();

				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
						lstFleet
								.add(RMDCommonUtility
										.convertObjectToString(recomDetails[0])
										+ RMDCommonConstants.PIPELINE_CHARACTER
										+ RMDCommonUtility
												.convertObjectToString(recomDetails[1])+ RMDCommonConstants.PIPELINE_CHARACTER+RMDCommonUtility
												.convertObjectToString(recomDetails[2]));
					}
					addeditRxServiceVO.setFleetList(lstFleet);					
				}				
				arlRecomDetails.clear();

				// Query to fetch Subsystem values
				nativeQuery = null;

				rxqueryBuilder = new StringBuilder();
				rxqueryBuilder.append("SELECT LOOKUP.OBJID , ");
				rxqueryBuilder.append("LOOKUP.LOOK_VALUE, PRIMARY_SUBSYS_FLAG ");
				rxqueryBuilder.append("FROM GETS_SD_RECOM_SUBSYS SUBSYS, ");
				rxqueryBuilder.append("GETS_RMD_LOOKUP LOOKUP ");
				rxqueryBuilder
						.append("WHERE LOOKUP.OBJID    =SUBSYS.SUBSYSTEM ");
				rxqueryBuilder.append("AND LOOKUP.LIST_NAME  ='SUBSYSTEM' ");
				rxqueryBuilder.append("AND RECOM_SUBSYS2RECOM=:recomId ");
				rxqueryBuilder.append("ORDER BY LOOKUP.SORT_ORDER ASC ");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
						.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
				arlRecomDetails = nativeQuery.list();
				lstSubsystem = new ArrayList<String>();
				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
			
						String primaryFlag = null;
						if (null == recomDetails[2]) {
							primaryFlag = "N";
						} else {
							primaryFlag = RMDCommonUtility
									.convertObjectToString(recomDetails[2]);
						}
						lstSubsystem
								.add(RMDCommonUtility
										.convertObjectToString(recomDetails[0])
										+ RMDCommonConstants.PIPELINE_CHARACTER
										+ RMDCommonUtility
												.convertObjectToString(recomDetails[1]) 
										+ RMDCommonConstants.PIPELINE_CHARACTER
										+ RMDCommonUtility
												.convertObjectToString(primaryFlag));
					}
					addeditRxServiceVO.setSubSystemList(lstSubsystem);
				}

				arlRecomDetails.clear();

				// to populate the configuration list
				rxqueryBuilder = null;

				nativeQuery = null;
				rxqueryBuilder = new StringBuilder();
				rxqueryBuilder.append("SELECT CONFIG_FUNCTION, ");
				rxqueryBuilder.append("CONFIG_NAME, VALUE1, VALUE2 ");
				rxqueryBuilder.append("FROM GETS_SD.GETS_SD_RECOM_CONFIG ");
				rxqueryBuilder.append("WHERE RECOM_CONFIG2RECOM=:recomId ");
				nativeQuery = fetchRecommSession.createSQLQuery(rxqueryBuilder
						.toString());
				nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, ESAPI
						.encoder().encodeForSQL(ORACLE_CODEC, strRXId));
				arlRecomDetails = nativeQuery.list();
				arlRxConfigVO = new ArrayList<RxConfigVO>();
				if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
					for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
						Object recomDetails[] = (Object[]) arlRecomDetails
								.get(idx);
						objRxConfigVO = new RxConfigVO();
						objRxConfigVO.setConfigFunction(RMDCommonUtility
								.convertObjectToString(recomDetails[0]));
						objRxConfigVO.setConfigName(RMDCommonUtility
								.convertObjectToString(recomDetails[1]));
						objRxConfigVO.setConfigValue1(RMDCommonUtility
								.convertObjectToString(recomDetails[2]));
						objRxConfigVO.setConfigValue2(RMDCommonUtility
								.convertObjectToString(recomDetails[3]));
						arlRxConfigVO.add(objRxConfigVO);
					}
					addeditRxServiceVO.setArlRxConfig(arlRxConfigVO);
				}

				if (RMDServiceConstants.EDITRECOMM.equals(strFromPage)) {
					// Populating the recommendation history type
					rxqueryBuilder = null;
					nativeQuery = null;
					if (previousQryhasData) {
						arlRxHistory = new ArrayList<RxHistServiceVO>();
						rxqueryBuilder = new StringBuilder();
						rxqueryBuilder.append("SELECT RECOM_HIST2RECOM, ");
						rxqueryBuilder
								.append("  REVISION_NOTES, TO_CHAR(CREATION_DATE,'MM/DD/YYYY HH24:MI:SS') CREATION_DATE,CREATED_BY,CHANGE_DETAIL ");
						rxqueryBuilder.append("FROM GETS_SD_RECOM_HIST ");
						rxqueryBuilder
								.append("WHERE RECOM_HIST2RECOM=:recomId ORDER BY OBJID DESC ");
						nativeQuery = fetchRecommSession
								.createSQLQuery(rxqueryBuilder.toString());
						nativeQuery.setParameter(RMDServiceConstants.RECOM_ID,
								strRXId);
						arlRecomDetails = nativeQuery.list();
						arlRxConfigVO = new ArrayList<RxConfigVO>();
						if (RMDCommonUtility
								.isCollectionNotEmpty(arlRecomDetails)) {
							for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
								Object recomDetails[] = (Object[]) arlRecomDetails
										.get(idx);
								objRxHistServiceVO = new RxHistServiceVO();
								objRxHistServiceVO
										.setStrRxID(RMDCommonUtility
												.convertObjectToString(recomDetails[0]));
								objRxHistServiceVO
										.setStrRevisionHistory(AppSecUtil.htmlEscaping(RMDCommonUtility
												.convertObjectToString(recomDetails[1])));
								objRxHistServiceVO
										.setStrDateCreated(RMDCommonUtility
												.convertObjectToString(recomDetails[2]));
								objRxHistServiceVO
										.setStrCreatedBy(RMDCommonUtility
												.convertObjectToString(recomDetails[3]));
								objRxHistServiceVO
										.setChangeDetail(RMDCommonUtility
												.convertObjectToString(recomDetails[4]));
								arlRxHistory.add(objRxHistServiceVO);

								// To retrieve the latest revision notes and put
								// it in Recom
								// level
								if (idx == 0) {
									addeditRxServiceVO
											.setStrRevHist(AppSecUtil
													.htmlEscaping(RMDCommonUtility
															.convertObjectToString(recomDetails[1])));
								}

							}
							addeditRxServiceVO.setArlRxHistory(arlRxHistory);
						}
					}
				}
				//populate rx plot data
				if (null != recomToPlotObjid) {
					rxqueryBuilder = null;
					nativeQuery = null;
					rxqueryBuilder = new StringBuilder();
					rxqueryBuilder
							.append("SELECT OBJID,TITLE,INDEPENDENT_AXIS_LABEL,LEFT_DEPENDENT_AXIS_LABEL,RIGHT_DEPENDENT_AXIS_LABEL, ");
					rxqueryBuilder
							.append("DATASET_1_PARAMETER_NAME,DATASET_1_LABEL,DATASET_1_AXIS,DATASET_2_PARAMETER_NAME,DATASET_2_LABEL,DATASET_2_AXIS, ");
					rxqueryBuilder
							.append("DATASET_3_PARAMETER_NAME,DATASET_3_LABEL,DATASET_3_AXIS,DATASET_4_PARAMETER_NAME,DATASET_4_LABEL,DATASET_4_AXIS ");
					rxqueryBuilder
							.append("FROM GETS_SD_RECOM_PLOT WHERE OBJID=:recomToPlotObjid ");
					nativeQuery = fetchRecommSession
							.createSQLQuery(rxqueryBuilder.toString());
					nativeQuery.setParameter(
							"recomToPlotObjid",
							ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
									recomToPlotObjid));
					arlRecomDetails = nativeQuery.list();

					if (RMDCommonUtility.isCollectionNotEmpty(arlRecomDetails)) {
						for (int idx = 0; idx < arlRecomDetails.size(); idx++) {
							Object recomDetails[] = (Object[]) arlRecomDetails
									.get(idx);
							addEditRxPlotVO = new AddEditRxPlotVO();
							addEditRxPlotVO.setStrPlotObjid(RMDCommonUtility
									.convertObjectToString(recomDetails[0]));
							addEditRxPlotVO.setStrPlotTitle(RMDCommonUtility
									.convertObjectToString(recomDetails[1]));
							addEditRxPlotVO
									.setStrIndependentAxislabel(RMDCommonUtility
											.convertObjectToString(recomDetails[2]));
							addEditRxPlotVO
									.setStrLeftDependentAxislabel(RMDCommonUtility
											.convertObjectToString(recomDetails[3]));
							addEditRxPlotVO
									.setStrRightDependentAxislabel(RMDCommonUtility
											.convertObjectToString(recomDetails[4]));
							addEditRxPlotVO
									.setStrDataSetOneName(RMDCommonUtility
											.convertObjectToString(recomDetails[5]));
							addEditRxPlotVO
									.setStrDataSetOneLabel(RMDCommonUtility
											.convertObjectToString(recomDetails[6]));
							addEditRxPlotVO
									.setStrDataSetOneAxis(RMDCommonUtility
											.convertObjectToString(recomDetails[7]));
							addEditRxPlotVO
									.setStrDataSetTwoName(RMDCommonUtility
											.convertObjectToString(recomDetails[8]));
							addEditRxPlotVO
									.setStrDataSetTwoLabel(RMDCommonUtility
											.convertObjectToString(recomDetails[9]));
							addEditRxPlotVO
									.setStrDataSetTwoAxis(RMDCommonUtility
											.convertObjectToString(recomDetails[10]));
							addEditRxPlotVO
									.setStrDataSetThreeName(RMDCommonUtility
											.convertObjectToString(recomDetails[11]));
							addEditRxPlotVO
									.setStrDataSetThreeLabel(RMDCommonUtility
											.convertObjectToString(recomDetails[12]));
							addEditRxPlotVO
									.setStrDataSetThreeAxis(RMDCommonUtility
											.convertObjectToString(recomDetails[13]));
							addEditRxPlotVO
									.setStrDataSetFourName(RMDCommonUtility
											.convertObjectToString(recomDetails[14]));
							addEditRxPlotVO
									.setStrDataSetFourLabel(RMDCommonUtility
											.convertObjectToString(recomDetails[15]));
							addEditRxPlotVO
									.setStrDataSetFourAxis(RMDCommonUtility
											.convertObjectToString(recomDetails[16]));
							addeditRxServiceVO
									.setAddEditRxPlotVO(addEditRxPlotVO);
						}
					}
				}
			}
			LOG.debug("Start fetchRecommDetails");
		} catch (RMDDAOConnectionException ex) {
			ex.printStackTrace();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADVISORYDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADVISORYDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(fetchRecommSession);
			arlRecomDetails = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.cases.dao.intf.NotesDAOIntf#getTaskDetails(
	 * com.ge.trans.rmd.services.cases.service.valueobjects.AddEditRxServiceVO)
	 * This method is used to fetch the task details of an advisory from
	 * database
	 */
	@Override
	public void getTaskDetails(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {
		LOG.debug("AddEditRxDAOImpl.getTaskDetails() Started");
		List<Object[]> arlRecomTask;
		List<Object[]> arlRecomTaskDoc;
		ArrayList<AddEditRxTaskResultVO> lstRxTaskResult;
		Session getTaskSession = null;
		Query nativeQuery = null;
		Query nativeTaskDocQuery = null;

		AddEditRxTaskResultVO objAddEditRxTaskResultVO = null;
		String strDeliveries = null;
		float deliveries = 0;
		float closureCount = 0;
		float closurePercent = 0;
		String strClosureCount = null;
		StringBuilder rxTaskqueryBuilder = null;
		StringBuilder rxTaskDocqueryBuilder = null;

		AddEditRxTaskDocVO addEditRxTaskDocVO = null;
		String strTaskObjid = null;
		String strConditionalTask = null;
		String strConditionalTaskObjId = null;
		String strConditionalValues = null;
		String strTaskType = null;
		HashMap<String, String> condTaskObjIdMap = null;

		List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = null;
		try {
			getTaskSession = getHibernateSession();
			String strRxId = addeditRxServiceVO.getStrRxId();
			rxTaskqueryBuilder = new StringBuilder();
			// Query to fetch recommendation task information
			rxTaskqueryBuilder.append("SELECT GRT.MANDATORY_LEVEL, ");
			rxTaskqueryBuilder.append("  GRT.TASK_ID, ");
			rxTaskqueryBuilder.append("  GRT.TASK_DESC, ");
			rxTaskqueryBuilder
					.append("  DECODE(LTRIM(RTRIM(GRT.SUBTASK2TASK)),NULL,'N','Y') SUBTASK, ");
			rxTaskqueryBuilder.append("  GRT.UPPER_SPEC, ");
			rxTaskqueryBuilder.append("  GRT.LOWER_SPEC, ");
			rxTaskqueryBuilder.append("  GRT.TARGET, ");
			rxTaskqueryBuilder.append("  GRT.FEEDBACK, ");
			rxTaskqueryBuilder.append("  GRC.REPAIR_CODE, ");
			rxTaskqueryBuilder.append("  GRC.REPAIR_DESC, ");
			rxTaskqueryBuilder.append("  GRC.OBJID REPAIRCODEOBJID, ");
			rxTaskqueryBuilder.append("  GRT.CLOSURE_COUNT, ");
			rxTaskqueryBuilder.append("  GRT.OBJID, ");

			rxTaskqueryBuilder.append("  GRT.COND_TASK_ID, ");
			rxTaskqueryBuilder.append("  GRT.COND_TASK_VALUE, ");
			rxTaskqueryBuilder.append("  GRT.CONDITIONAL_TASK_TYPE ");

			rxTaskqueryBuilder
					.append("  FROM GETS_SD.GETS_SD_RECOM_TASK GRT, ");
			rxTaskqueryBuilder.append("  GETS_SD.GETS_SD_REPAIR_CODES GRC ");
			rxTaskqueryBuilder
					.append(" WHERE GRC.OBJID (+)  = GRT.RECOM_TASK2REP_CODE ");
			rxTaskqueryBuilder.append(" AND RECOM_TASK2RECOM =:recomId ");
			rxTaskqueryBuilder.append(" ORDER BY GRT.OBJID ");
			nativeQuery = getTaskSession.createSQLQuery(rxTaskqueryBuilder
					.toString());
			nativeQuery.setParameter(RMDServiceConstants.RECOM_ID, strRxId);
			strDeliveries = addeditRxServiceVO.getStrDeliveries();
			if (!RMDCommonUtility.isNullOrEmpty(strDeliveries)) {
				deliveries = Float.parseFloat(strDeliveries);
			}
			arlRecomTask = nativeQuery.list();

			if (RMDCommonUtility.isCollectionNotEmpty(arlRecomTask)) {
				lstRxTaskResult = new ArrayList<AddEditRxTaskResultVO>();

				condTaskObjIdMap = new HashMap<String, String>();
				for (int itask = 0; itask < arlRecomTask.size(); itask++) {
					Object objRxCondTask[] = (Object[]) arlRecomTask.get(itask);
					objAddEditRxTaskResultVO = new AddEditRxTaskResultVO();
					condTaskObjIdMap.put(RMDCommonUtility
							.convertObjectToString(objRxCondTask[12]),
							RMDCommonUtility
									.convertObjectToString(objRxCondTask[1]));
				}

				for (int idx = 0; idx < arlRecomTask.size(); idx++) {
					Object objRxTask[] = (Object[]) arlRecomTask.get(idx);
					objAddEditRxTaskResultVO = new AddEditRxTaskResultVO();
					objAddEditRxTaskResultVO.setStrRowId((idx + 1)
							+ RMDCommonConstants.EMPTY_STRING);
					objAddEditRxTaskResultVO.setStrMand(RMDCommonUtility
							.convertObjectToString(objRxTask[0]));
					objAddEditRxTaskResultVO.setStrNO(RMDCommonUtility
							.convertObjectToString(objRxTask[1]));
					objAddEditRxTaskResultVO.setStrTaskDeails(RMDCommonUtility
							.convertObjectToString(objRxTask[2]));
					objAddEditRxTaskResultVO.setStrSubTask(RMDCommonUtility
							.convertObjectToString(objRxTask[3]));
					objAddEditRxTaskResultVO.setStrUSL(RMDCommonUtility
							.convertObjectToString(objRxTask[4]));
					objAddEditRxTaskResultVO.setStrLSL(RMDCommonUtility
							.convertObjectToString(objRxTask[5]));
					objAddEditRxTaskResultVO.setStrTarget(RMDCommonUtility
							.convertObjectToString(objRxTask[6]));

					objAddEditRxTaskResultVO.setStrRepairCode(RMDCommonUtility
							.convertObjectToString(objRxTask[8]));
					objAddEditRxTaskResultVO
							.setStrRepairCodeDesc(RMDCommonUtility
									.convertObjectToString(objRxTask[9]));
					objAddEditRxTaskResultVO
							.setStrRepairCodeId(RMDCommonUtility
									.convertObjectToString(objRxTask[10]));
					strClosureCount = RMDCommonUtility
							.convertObjectToString(objRxTask[11]);
					if (!RMDCommonUtility.isNullOrEmpty(strClosureCount)
							&& !RMDCommonUtility.isNullOrEmpty(strDeliveries)) {
						closureCount = Float.parseFloat(strClosureCount);
						// Logic to calculate the closure percent
						closurePercent = (closureCount / deliveries) * 100;
						objAddEditRxTaskResultVO.setStrClosurePerc(Float
								.toString(closurePercent));

					}
					strTaskObjid = RMDCommonUtility
							.convertObjectToString(objRxTask[12]);
					/* Adding for Objid */
					objAddEditRxTaskResultVO.setStrTaskObjID(strTaskObjid);
					/* Adding for Objid End */

					/* Adding for Smart Reveal Start */

					strConditionalTaskObjId = RMDCommonUtility
							.convertObjectToString(objRxTask[13]);

					if (strConditionalTaskObjId == ""
							|| strConditionalTaskObjId == null
							|| strConditionalTaskObjId.equalsIgnoreCase("None")) {
						objAddEditRxTaskResultVO.setStrConditionalTask("None");
					} else {
						strConditionalTask = (String) condTaskObjIdMap
								.get(strConditionalTaskObjId);
						objAddEditRxTaskResultVO
								.setStrConditionalTask(strConditionalTask);
					}

					strConditionalValues = RMDCommonUtility
							.convertObjectToString(objRxTask[14]);
					if (strConditionalValues == ""
							|| strConditionalValues == null) {
						objAddEditRxTaskResultVO
								.setStrConditionalValues("Select");
					} else {
						objAddEditRxTaskResultVO
								.setStrConditionalValues(strConditionalValues);
					}

					strTaskType = RMDCommonUtility
							.convertObjectToString(objRxTask[15]);
					if (strTaskType == "" || strTaskType == null) {
						objAddEditRxTaskResultVO.setStrTaskType("None");
					} else {
						objAddEditRxTaskResultVO.setStrTaskType(strTaskType);
					}

					/* Adding for Smart Reveal End */

					// Query to fetch the Recommendation task doc details
					rxTaskDocqueryBuilder = new StringBuilder();
					rxTaskDocqueryBuilder
							.append(" SELECT OBJID,DOC_TITLE,DELETED,DOC_PATH FROM GETS_SD.GETS_SD_RECOM_TASK_DOC "
									+ "WHERE RECOM_DOC2RECOM_TASK=:recomTaskObjid AND DELETED = 0 AND LINK_LANG_TYPE is null");
					nativeTaskDocQuery = getTaskSession
							.createSQLQuery(rxTaskDocqueryBuilder.toString());
					nativeTaskDocQuery.setParameter(
							RMDServiceConstants.RECOM_TASK_OBJID, strTaskObjid);
					arlRecomTaskDoc = nativeTaskDocQuery.list();
					lstAddEditRxTaskDocVO = new ArrayList<AddEditRxTaskDocVO>();
					if (RMDCommonUtility.isCollectionNotEmpty(arlRecomTaskDoc)) {
						for (int icount = 0; icount < arlRecomTaskDoc.size(); icount++) {
							Object objRxTaskDoc[] = (Object[]) arlRecomTaskDoc
									.get(icount);
							addEditRxTaskDocVO = new AddEditRxTaskDocVO();
							addEditRxTaskDocVO
									.setStrTaskDocSeqId(RMDCommonUtility
											.convertObjectToString(objRxTaskDoc[0]));
							addEditRxTaskDocVO.setStrDocTitle(RMDCommonUtility
									.convertObjectToString(objRxTaskDoc[1]));
							addEditRxTaskDocVO.setStrDelete(RMDCommonUtility
									.convertObjectToString(objRxTaskDoc[2]));
							addEditRxTaskDocVO.setStrDocPath(RMDCommonUtility
									.convertObjectToString(objRxTaskDoc[3]));
							lstAddEditRxTaskDocVO.add(addEditRxTaskDocVO);
						}
						objAddEditRxTaskResultVO
								.setAddEditRxTaskDocVO(lstAddEditRxTaskDocVO);
					}
					objAddEditRxTaskResultVO.setRepairCodes(getRepairCodes(strTaskObjid));
					lstRxTaskResult.add(objAddEditRxTaskResultVO);
					
					
				}
				addeditRxServiceVO.setEditRxTaskResultList(lstRxTaskResult);
			}

		} catch (RMDDAOConnectionException ex) {
			LOG.error(ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TASKADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TASKADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(getTaskSession);
			arlRecomTask = null;
			lstRxTaskResult = null;
			nativeQuery = null;
		}
		LOG.debug("AddEditRxDAOImpl.getTaskDetails() Ended");
	}

	private List<RxTaskRepairCodeVO> getRepairCodes(String rxTaskObjId) {
		List<RxTaskRepairCodeVO> repairCodes = null;
		Session session = null;
		Query getRepairCodeQuery = null;
		String strGetRepairCodeQuery = "select TRC.RECOM_TASK_ID AS RECOM_TASK_ID, RC.REPAIR_CODE AS REPAIR_CODE, RC.REPAIR_DESC AS REPAIR_DESC, RC.OBJID AS REPAIRCODEOBJID from GETS_SD.GETS_SD_RECOM_TASK_REPAIR_CODE TRC JOIN GETS_SD.GETS_SD_REPAIR_CODES RC ON TRC.REPAIR_CODE_ID = RC.OBJID WHERE TRC.RECOM_TASK_ID = :recomTaskObjid";
		try{
			session = getHibernateSession();
			getRepairCodeQuery = session.createSQLQuery(strGetRepairCodeQuery);
			getRepairCodeQuery.setParameter(RMDServiceConstants.RECOM_TASK_OBJID, rxTaskObjId);
			getRepairCodeQuery.setResultTransformer(AliasToEntityMapResultTransformerUtil.getInstance());
			List<Map<String,Object>> repairCodeResult = getRepairCodeQuery.list();
			if(RMDCommonUtility.isCollectionNotEmpty(repairCodeResult)) {
				repairCodes = new ArrayList<RxTaskRepairCodeVO>();
				for(Map<String, Object> repairCode : repairCodeResult) {
					RxTaskRepairCodeVO taskRepairCode = new RxTaskRepairCodeVO(RMDCommonUtility.convertObjectToString(repairCode.get("REPAIR_CODE")),
							RMDCommonUtility.convertObjectToString(repairCode.get("REPAIR_DESC")), 
							RMDCommonUtility.convertObjectToString(repairCode.get("REPAIRCODEOBJID")));
					repairCodes.add(taskRepairCode);
				}
			}
		} catch (Exception e) {
			LOG.error("SQL Exception in getRepairCodes of AddEditRxDAOImpl", e);
		} finally {
			releaseSession(session);
		}
		return repairCodes;
	}
	
	/*
	 * This method is used to verify the Rx title if already exist or not in
	 * data base
	 * 
	 * @Param AddEditRxServiceVO
	 * 
	 * @returns AddEditRxServiceVO
	 * 
	 * @throws RMDDAOException
	 */
	@Override
	public String checkRxTitle(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {
		LOG.debug("AddEditRxDAOImpl.checkRxTitle() Started");
		Session hibernateSession = null;
		String strStatus = RMDCommonConstants.EMPTY_STRING;
		List arlRxTitle = null;
		try {
			hibernateSession = getHibernateSession();
			String strRXTitle = AppSecUtil
					.stripNonValidXMLCharactersForKM(AppSecUtil
							.decodeString(addeditRxServiceVO.getStrTitle()
									.trim()));
			Criteria titleCriteria = hibernateSession
					.createCriteria(GetKmRecomHVO.class);
			titleCriteria.add(Restrictions.eq(
					RMDServiceConstants.LANGUAGE_SELECTION,
					addeditRxServiceVO.getStrLanguage()));
			if (addeditRxServiceVO.getStrOriginalId() != null
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(addeditRxServiceVO
									.getStrOriginalId())) {
				titleCriteria.setFetchMode(
						RMDCommonConstants.GETKMRECOM_LINKORIGIONAL,
						FetchMode.JOIN).createAlias(
						RMDCommonConstants.GETKMRECOM_LINKORIGIONAL,
						RMDCommonConstants.RXHIS);
				Criterion objCriterion = Restrictions
						.eq(RMDCommonConstants.RXHIS_GETKMRECOMBYLINKORIGINALRECOM_GETKMRECOMSEQID,
								Long.valueOf(addeditRxServiceVO
										.getStrOriginalId()));
				titleCriteria.add(Restrictions.not(objCriterion));
				titleCriteria.add(Restrictions.eq(RMDCommonConstants.TITLE,
						strRXTitle));
			} else {
				titleCriteria.add(Restrictions.eq(RMDCommonConstants.TITLE,
						strRXTitle));
			}
			arlRxTitle = titleCriteria.list();
			if (null != arlRxTitle && !arlRxTitle.isEmpty()) {
				strStatus = RMDServiceConstants.ADVISORY_TITLE_EXIST;
			}
			LOG.debug("strStatus---" + strStatus);

		} catch (RMDDAOConnectionException ex) {
			LOG.error(ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TITLECHECK);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			strStatus = RMDCommonConstants.FAIL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TITLECHECK);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);

		} finally {
			releaseSession(hibernateSession);
			arlRxTitle = null;
		}
		LOG.debug("AddEditRxDAOImpl.checkRxTitle() Ended");
		return strStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.tools.rx.dao.intf.AddEditRxDAOIntf#deActivateRx
	 * (
	 * com.ge.trans.rmd.services.tools.rx.service.valueobjects.AddEditRxServiceVO
	 * ) Description: This method will deactivate all previous version of rx
	 * while saving the edited rx.
	 */
	@Override
	public void deActivateRx(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {
		LOG.debug("AddEditRxDAOImpl.deActivateRx() Started");
		Session hibernateSession = null;
		String strUserID = RMDCommonConstants.EMPTY_STRING;
		String strRxOrgId = RMDCommonConstants.EMPTY_STRING;
		StringBuilder prevRxDeActivate = null;
		Query prevRxDeActivateQuery = null;
		GetKmRecomHistHVO rxHistVO = null;
		List arlPrevHist;
		try {
			strUserID = addeditRxServiceVO.getStrUserName();
			hibernateSession = getHibernateSession(strUserID);
			strRxOrgId = addeditRxServiceVO.getStrOriginalId();
			// DeActivate All Previous Recomms
			if (strRxOrgId != null
					&& !RMDCommonConstants.EMPTY_STRING.equals(strRxOrgId)) {
				prevRxDeActivate = new StringBuilder();
				prevRxDeActivate
						.append("FROM GETKMRECOMHISTHVO RXHIS WHERE GETKMRECOMBYLINKORIGINALRECOM.GETKMRECOMSEQID =:orgRxID");
				prevRxDeActivateQuery = hibernateSession
						.createQuery(prevRxDeActivate.toString());
				prevRxDeActivateQuery.setLong(RMDCommonConstants.ORGRXID,
						RMDCommonUtility.getLongValue(strRxOrgId));
				arlPrevHist = prevRxDeActivateQuery.list();
				int iPrevHist = arlPrevHist.size();
				for (int count = 0; count < iPrevHist; count++) {
					rxHistVO = (GetKmRecomHistHVO) arlPrevHist.get(count);
					rxHistVO.setActive(RMDServiceConstants.INACTIVE_STATE);
					hibernateSession.saveOrUpdate(rxHistVO);
					hibernateSession.flush();
					hibernateSession.clear();
				}
			}
		} catch (RMDDAOConnectionException ex) {
			LOG.error(ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RXDEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RXDEACTIVATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		LOG.debug("AddEditRxDAOImpl.deActivateRx() Ended");
	}

	/*
	 * This method used to insert the new recommendation into the database
	 * 
	 * @Param AddEditRxServiceVO
	 * 
	 * @return String
	 * 
	 * @throws RMDDAOException, SQLException
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String saveNewRecomm(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException, SQLException {
		LOG.debug("AddEditRxDAOImpl.saveNewRecomm() Started");
		String strUserID = RMDCommonConstants.EMPTY_STRING;
		String strLanguage = RMDCommonConstants.EMPTY_STRING;
		String strFromPage = RMDCommonConstants.EMPTY_STRING;
		String strObjId = RMDCommonConstants.EMPTY_STRING;

		List<String> lstModels = null;
		String strModelId = "";
		String strModelArray[];

		long longTaskId = 0L;

		int iTask = RMDCommonConstants.INT_CONST;
		Integer recomId = RMDCommonConstants.INT_CONST;

		Connection conn = null;
		CallableStatement modelCallStmt = null;
		CallableStatement taskCallStmt = null;
		CallableStatement taskDocCallStmt = null;
		CallableStatement recomCommentCallStmt = null;
		CallableStatement copyCloneTasksCallStmt = null;
		
		PreparedStatement customerPrepStmt=null;
		PreparedStatement fleetPrepStmt=null;
		
		PreparedStatement taskIdStatement = null;
		PreparedStatement configPrepStmt = null;
		PreparedStatement newRecomPrepStmt = null;
		PreparedStatement histPrepStmt = null;
		PreparedStatement queueStatement = null;
		PreparedStatement orgHistPrepStmt = null;
		PreparedStatement repairCodeStmt = null;
		
		Session session = null;
		Statement recomIdStmt = null;

		PreparedStatement taskStatementObjNew = null;
		ResultSet taskResultSetObjNew = null;

		ResultSet recomIdResultSet = null;
		ResultSet taskResultSet = null;
		ResultSet queueResultSet = null;

		Object configObject = RMDCommonConstants.NULL_STRING;
		Object newRecomObject = RMDCommonConstants.NULL_STRING;
		Object histObject = RMDCommonConstants.NULL_STRING;
		List<String> filePathCreated = new ArrayList<String>();
		
		List<RxTaskRepairCodeVO> repairCodes = null;
		try {

			/***************** Added for task doc file movement. *************/
			LOG.debug("RX URL :" + rxDataUrl);

			String rxBasePath = RMDCommonConstants.EMPTY_STRING;

			rxBasePath = rxDataUrl + File.separator
					+ RMDServiceConstants.RX_AUTH;

			File task_attachment = new File(rxBasePath);
			if (!task_attachment.exists())
				task_attachment.mkdirs(); // Create the dir, if it doesn't exist

			/************ END- task doc file movement **********************************/

			session = getHibernateSession();

			strUserID = addeditRxServiceVO.getStrUserName();
			strLanguage = addeditRxServiceVO.getStrLanguage();
			strFromPage = addeditRxServiceVO.getStrFromPage();

			conn = session.connection();
			conn.setAutoCommit(false);
			
			String rxPlotObjid = null;
			
			if (null != addeditRxServiceVO.getAddEditRxPlotVO()) {
				rxPlotObjid = getRxPlotNextVal(conn);
				insertRxPlotData(conn, addeditRxServiceVO.getAddEditRxPlotVO(),
						Long.valueOf(rxPlotObjid), strUserID);
			}
			// Begin: insert the data in gets_sd_recom table
			// 1. call - SDRecomInsert_pr
			newRecomObject = "INSERT INTO gets_sd_recom(objid,last_updated_date,last_updated_by,creation_date,created_by,title,urgency,est_repair_time,recom_type,recom_status,version,loco_impact,recom_move2queue,recom2recom,SEND_TO_CUST,EXPORT_CONTROL,RECOM2PLOT,APPROVAL_DATE,APPROVED_BY, DESCRIPTION) "
					+ "VALUES (gets_sd_recom_seq.nextval,sysdate,?,sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			newRecomPrepStmt = conn.prepareStatement((String) newRecomObject);
			newRecomPrepStmt.setString(1, strUserID);
			newRecomPrepStmt.setString(2, strUserID);
			newRecomPrepStmt.setString(3, AppSecUtil
					.stripNonValidXMLCharactersForKM(AppSecUtil
							.decodeString(addeditRxServiceVO.getStrTitle())));
			newRecomPrepStmt.setString(4,
					addeditRxServiceVO.getStrSelUrgRepair());
			newRecomPrepStmt.setString(5,
					addeditRxServiceVO.getStrSelEstmTimeRepair());

			if (RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
					.getStrRxStatus())
					&& RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
							.getStrRxType())) {
				newRecomPrepStmt.setString(6, RMDServiceConstants.STANDARD);
			} else {
				newRecomPrepStmt
						.setString(6, addeditRxServiceVO.getStrRxType());
			}

			if (StringUtils.isNotBlank(addeditRxServiceVO.getStrRxStatus())
					&& RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
							.getStrRxStatus())) {
				newRecomPrepStmt.setString(7, RMDServiceConstants.APPROVED);
			} else {
				newRecomPrepStmt.setString(7, RMDServiceConstants.INPROCESS);
			}

			newRecomPrepStmt.setString(8, RMDCommonConstants.ZERO_STRING);
			newRecomPrepStmt.setString(9,
					addeditRxServiceVO.getStrSelAssetImp());

			newRecomPrepStmt.setString(10, addeditRxServiceVO.getStrQueue()); 
			if (null != addeditRxServiceVO.getOriginalId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(addeditRxServiceVO
									.getOriginalId())) {
			newRecomPrepStmt.setString(11, addeditRxServiceVO
					.getOriginalId());
			}else{
				newRecomPrepStmt.setString(11, null);
			}
			newRecomPrepStmt.setString(12, null);

			newRecomPrepStmt.setString(13,
					addeditRxServiceVO.getStrExportControl());
			newRecomPrepStmt.setString(14, rxPlotObjid);

			//OMDKM 2073 Populate new Fields on GETS_SD_RECOM when Activating or Deactivating

			if (RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
					.getStrRxStatus())) {
				newRecomPrepStmt.setDate(15, new Date(System.currentTimeMillis()));
				newRecomPrepStmt.setString(16, strUserID);

			} else {
				newRecomPrepStmt.setString(15, null);
				newRecomPrepStmt.setString(16, null);

			}
			newRecomPrepStmt.setString(17, AppSecUtil
					.stripNonValidXMLCharactersForKM(addeditRxServiceVO.getStrDescription()));

			newRecomPrepStmt.executeUpdate();

			recomIdStmt = conn.createStatement();
			recomIdResultSet = recomIdStmt
					.executeQuery("select gets_sd_recom_seq.CURRVAL from dual");
			while (recomIdResultSet.next()) {
				recomId = recomIdResultSet.getInt(1);
				addeditRxServiceVO.setStrRxId(recomId.toString());
			}
			// End: insert the data in gets_sd_recom table

			// Begin: inserting revision notes into GETS_SD_RECOM_HIST table

			histObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_HIST(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RECOM_HIST2RECOM,CHANGE_DETAIL,REVISION_NOTES,value) "
					+ "VALUES (gets_sd_recom_hist_seq.nextval, sysdate, ?, sysdate, ?, ?, ?, ?,?)";

			if (null != addeditRxServiceVO.getOriginalId()
					&& !RMDCommonConstants.EMPTY_STRING
							.equalsIgnoreCase(addeditRxServiceVO
									.getOriginalId())) {
				orgHistPrepStmt = conn.prepareStatement((String) histObject);
				orgHistPrepStmt.setString(1, strUserID);
				orgHistPrepStmt.setString(2, strUserID);
				orgHistPrepStmt.setInt(3,
						Integer.parseInt(addeditRxServiceVO.getOriginalId()));
				orgHistPrepStmt
						.setString(4,
								RMDServiceConstants.RECOMMENDATION_CUSTOMIZED
										+ recomId);
				orgHistPrepStmt.setString(5, AppSecUtil
						.stripNonValidXMLCharactersForKM(AppSecUtil
								.decodeString(addeditRxServiceVO
										.getStrRevHist())));
				orgHistPrepStmt.setString(6, addeditRxServiceVO.getStrRxId());
				orgHistPrepStmt.executeUpdate();
			}

			histPrepStmt = conn.prepareStatement((String) histObject);
			histPrepStmt.setString(1, strUserID);
			histPrepStmt.setString(2, strUserID);
			histPrepStmt.setInt(3, recomId);
			histPrepStmt.setString(4,
					RMDServiceConstants.RECOMMENDATION_CREATED);
			histPrepStmt.setString(5, AppSecUtil
					.stripNonValidXMLCharactersForKM(AppSecUtil
							.decodeString(addeditRxServiceVO.getStrRevHist())));
			histPrepStmt.setString(6, RMDCommonConstants.ZERO_STRING);

			histPrepStmt.executeUpdate();
			// End: inserting revision notes into GETS_SD_RECOM_HIST table

			// Begin: The Rx Models data inserting into GETS_SD_RECOM_MTM_MODEL
			// table
			lstModels = addeditRxServiceVO.getModelList();
			if (RMDCommonUtility.isCollectionNotEmpty(lstModels)) {
				for (String strModelDetails : lstModels) {
					strModelArray = RMDCommonUtility.splitString(
							strModelDetails,
							RMDCommonConstants.PIPELINE_CHARACTER);
					strModelId = strModelArray[RMDCommonConstants.INT_CONST_ZERO];

					modelCallStmt = conn
							.prepareCall("{ call GETS_SD_NEWRECOM_PKG.Model_Insert_pr(?,?,?) }");
					modelCallStmt.setInt(1, recomId);
					modelCallStmt.setString(2, strModelId);
					modelCallStmt.setString(3, strUserID);
					modelCallStmt.execute();
				}
			}
			/* RX Model Ends */

			/* RX Subsystem begins */
			// iterating the list of subsystem:
			// format of the subsystem string in the List :
			// <subsystemSeqId>|<subsystemName>
			insertRxSubSystem(conn,addeditRxServiceVO.getSubSystemList(),recomId.longValue(),strUserID,strFromPage);
			// End: The Rx subsystems data inserting into GETS_SD_RECOM_SUBSYS
			// table

			/*Edit For Customer*/			
			LOG.debug("Customer started -");			
				List<String> customerList = addeditRxServiceVO.getCustomerList();
				List<String> fleetList = addeditRxServiceVO.getFleetList();
				if (RMDCommonUtility.isCollectionNotEmpty(customerList)) {
					for (String strCustomerDetails : customerList) {
						String[] strCustomerArray = RMDCommonUtility.splitString(
								strCustomerDetails,
								RMDCommonConstants.PIPELINE_CHARACTER);								
									
					String customerObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_MTM_CUST(OBJID,CUST2BUSORG,CUST2RECOM,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATED_BY,CREATION_DATE) "
							+ "VALUES(GETS_SD.GETS_SD_RECOM_MTM_CUST_SEQ.NEXTVAL,?,?,sysdate,?,?,sysdate)";
					customerPrepStmt = conn
							.prepareStatement((String) customerObject);
					customerPrepStmt.setInt(1,Integer.parseInt(strCustomerArray[0]) );
					customerPrepStmt.setInt(2,
							recomId);
					customerPrepStmt.setString(3, strUserID);
					customerPrepStmt.setString(4,
							strUserID);
					
					customerPrepStmt.executeUpdate();
					LOG.debug("Customer completed -");
					

					if (RMDCommonUtility.isCollectionNotEmpty(fleetList)) {
						for (String strFleetDetails : fleetList) {
							String[] strFleetArray = RMDCommonUtility.splitString(
									strFleetDetails,
									RMDCommonConstants.PIPELINE_CHARACTER);								
										if(strCustomerArray[0].equalsIgnoreCase(strFleetArray[2])){
						String fleetObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_MTM_FLEET(OBJID,FLEET2FLEET,FLEET2CUST,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY) "
								+ "VALUES(GETS_SD.GETS_SD_RECOM_MTM_FLEET_SEQ.NEXTVAL,?,GETS_SD.GETS_SD_RECOM_MTM_CUST_SEQ.CURRVAL,sysdate,?,sysdate,?)";

						fleetPrepStmt = conn
								.prepareStatement((String) fleetObject);
						fleetPrepStmt.setInt(1,Integer.parseInt(strFleetArray[0]) );							
						fleetPrepStmt.setString(2, strUserID);						
						fleetPrepStmt.setString(3,
								strUserID);
						fleetPrepStmt.executeUpdate();
						LOG.debug("Customer completed -");
										}
					}
				}
				}
			}
			
			
			
			
			
			
			
			
			// Begin: The Rx config data inserting into GETS_SD_RECOM_CONFIG
			// table
			if (addeditRxServiceVO.getArlRxConfig() != null
					&& RMDCommonUtility.isCollectionNotEmpty(addeditRxServiceVO
							.getArlRxConfig())) {

				ArrayList<RxConfigVO> arlRxConfig = addeditRxServiceVO
						.getArlRxConfig();
				for (Iterator<RxConfigVO> iterator = arlRxConfig.iterator(); iterator
						.hasNext();) {
					RxConfigVO rxConfigVO = (RxConfigVO) iterator.next();

					configObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_CONFIG(OBJID,RECOM_CONFIG2RECOM,CONFIG_FUNCTION,CONFIG_NAME,VALUE1,VALUE2,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,LANGUAGE) "
							+ "VALUES(GETS_SD.GETS_SD_RECOM_CONFIG_SEQ.NEXTVAL,?,?,?,?,?,?,sysdate,?,sysdate,?)";

					configPrepStmt = conn
							.prepareStatement((String) configObject);
					configPrepStmt.setInt(1, recomId);
					configPrepStmt.setString(2, rxConfigVO.getConfigFunction());
					configPrepStmt.setString(3, rxConfigVO.getConfigName());
					configPrepStmt.setString(4, rxConfigVO.getConfigValue1());
					configPrepStmt.setString(5, rxConfigVO.getConfigValue2());
					configPrepStmt.setString(6, strUserID);
					configPrepStmt.setString(7, strUserID);
					configPrepStmt.setString(8, strLanguage);

					configPrepStmt.executeUpdate();
				}
			}
			// End: The Rx config data inserting into GETS_SD_RECOM_CONFIG table

			// commented to make the status of the edited version as same
			if (RMDServiceConstants.EDITRECOMM.equals(strFromPage)
					&& addeditRxServiceVO.isBlnFleetLeader()) {
				recommStatusUpdate(addeditRxServiceVO);
			}

			// Begin: The Rx Task data inserting into gets_sd_recom_task table
			// Prepare statements first to be used in loop

			iTask = addeditRxServiceVO.getLstEditTaskRxResultVO().size();

			AddEditRxTaskResultVO objAddEditRxTaskResultVO = null;
			String strTaskNo = null;
			String selectQuery = RMDCommonConstants.EMPTY_STRING;
			String strFinalTaskNo = RMDCommonConstants.EMPTY_STRING;
			String selectQueryObjidNew = RMDCommonConstants.EMPTY_STRING;
			String taskPath = null;
			String finTaskPath = null;

			/*
			 * This step is needed to handle the cloning of existing Rx. Cloning
			 * creates a new Rx, and the TASK_DOC should be populated from the
			 * cloned Rx appropriately
			 */
			for (int index = 0; index < iTask; index++) {
				objAddEditRxTaskResultVO = (AddEditRxTaskResultVO) addeditRxServiceVO
						.getLstEditTaskRxResultVO().get(index);

				// Task Doc details
				if (RMDCommonUtility
						.isCollectionNotEmpty(objAddEditRxTaskResultVO
								.getAddEditRxTaskDocVO())) {

					List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = objAddEditRxTaskResultVO
							.getAddEditRxTaskDocVO();
					for (AddEditRxTaskDocVO addEditRxTaskDocVO : lstAddEditRxTaskDocVO) {

						if (addEditRxTaskDocVO.getStrDocPath() != null
								&& !"".equals(addEditRxTaskDocVO
										.getStrDocPath())
								&& addEditRxTaskDocVO.getStrDocTitle() != null
								&& !"Y".equalsIgnoreCase(addEditRxTaskDocVO
										.getStrDelete())) {
							String sDocPath = addEditRxTaskDocVO
									.getStrDocPath().replace(this.domainPath,
											"");
							String sBase64Data = readBase64FileData(sDocPath,
									addEditRxTaskDocVO.getStrDocTitle());
							addEditRxTaskDocVO.setStrDocData(sBase64Data);
						} // For others
					}
				}
			} // End changes for Clone Rx

			String strRepairCodeQuery = "INSERT INTO GETS_SD_RECOM_TASK_REPAIR_CODE(OBJID, RECOM_TASK_ID, REPAIR_CODE_ID, CREATION_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY) "
					+"VALUES(GETS_SD_RECOM_TASK_RPR_CODE_SQ.nextVal, ?, ?, sysdate, ?, sysdate, ?)";
			for (int index = 0; index < iTask; index++) {

				objAddEditRxTaskResultVO = (AddEditRxTaskResultVO) addeditRxServiceVO
						.getLstEditTaskRxResultVO().get(index);
				strTaskNo = objAddEditRxTaskResultVO.getStrNO();
				LOG.debug("strTaskNo---" + strTaskNo);
				String strConditionalTask = objAddEditRxTaskResultVO
						.getStrConditionalTask();
				// comment here for differentiating task and subtask
				if (strTaskNo.lastIndexOf('.') != -1) {
					strFinalTaskNo = strTaskNo.substring(0,
							strTaskNo.lastIndexOf('.'));
					selectQuery = "select objid from gets_sd_recom_task where task_id=? and recom_task2recom=?";
					taskIdStatement = conn.prepareStatement(selectQuery);
					taskIdStatement.setString(1, strFinalTaskNo);
					taskIdStatement.setInt(2, recomId);
					taskResultSet = taskIdStatement.executeQuery();

					while (taskResultSet.next()) {
						strObjId = taskResultSet.getString(1);
						LOG.debug("strObjId---" + strObjId);
					}
				} else {
					if (strConditionalTask != null || strConditionalTask != ""
							|| !"None".equalsIgnoreCase(strConditionalTask)) {
						selectQueryObjidNew = "select objid from gets_sd_recom_task where task_id=? and recom_task2recom=?";
						taskStatementObjNew = conn
								.prepareStatement(selectQueryObjidNew);
						taskStatementObjNew.setString(1, strConditionalTask);
						taskStatementObjNew.setLong(2, Long.valueOf(recomId));
						taskResultSetObjNew = taskStatementObjNew
								.executeQuery();
						while (taskResultSetObjNew.next()) {
							strObjId = taskResultSetObjNew.getString(1);
							objAddEditRxTaskResultVO
									.setStrConditionalTask(strObjId);
						}
					}
				}

				taskCallStmt = conn
						.prepareCall("{ call GETS_SD_NEWRECOM_PKG.TaskInsert_pr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				taskCallStmt.setString(1, strUserID);
				taskCallStmt.setString(2, strTaskNo);
				taskCallStmt
						.setString(
								3,
								AppSecUtil
										.stripNonValidXMLCharactersForRx(objAddEditRxTaskResultVO
												.getStrTaskDeails()));
				if (objAddEditRxTaskResultVO.getStrLSL() != null
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO.getStrLSL())) {
					taskCallStmt.setString(4,
							objAddEditRxTaskResultVO.getStrLSL());
				} else {
					taskCallStmt.setString(4, "");
				}
				if (objAddEditRxTaskResultVO.getStrUSL() != null
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO.getStrUSL())) {
					taskCallStmt.setString(5,
							objAddEditRxTaskResultVO.getStrUSL());
				} else {
					taskCallStmt.setString(5, "");
				}
				if (objAddEditRxTaskResultVO.getStrTarget() != null
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO.getStrTarget())) {
					taskCallStmt.setString(6,
							objAddEditRxTaskResultVO.getStrTarget());
				} else {
					taskCallStmt.setString(6, "");
				}

				/*
				 * Setting default value null to Feedback Column as it is
				 * removed from UI , and will be set null until we remove
				 * feedback column from database
				 */
				taskCallStmt.setString(7, "");

				if (objAddEditRxTaskResultVO.getStrSubTask().equals(
						RMDCommonConstants.LETTER_Y)) {
					taskCallStmt.setString(8, strObjId);
				} else {
					taskCallStmt.setString(8, null);
				}
				taskCallStmt.setInt(9, recomId);
				if (objAddEditRxTaskResultVO.getStrRepairCodeId() != null
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO
										.getStrRepairCodeId())) {
					taskCallStmt.setString(10,
							objAddEditRxTaskResultVO.getStrRepairCodeId());
				} else {
					taskCallStmt.setLong(10, 0);
				}
				if (null != objAddEditRxTaskResultVO.getStrMand()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO.getStrMand())) {
					taskCallStmt.setString(11,
							objAddEditRxTaskResultVO.getStrMand());
				} else {
					taskCallStmt.setLong(11, 0);
				}
				if (null != objAddEditRxTaskResultVO.getStrClosurePerc()
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO
										.getStrClosurePerc())) {
					taskCallStmt.setString(12,
							objAddEditRxTaskResultVO.getStrClosurePerc());
				} else {
					taskCallStmt.setLong(12, 0);
				}
				/* Adding for SMART Reveal starts */
				if (objAddEditRxTaskResultVO.getStrConditionalTask() != null
						&& !"None".equalsIgnoreCase(objAddEditRxTaskResultVO
								.getStrConditionalTask())
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO
										.getStrConditionalTask())) {
					taskCallStmt.setString(13,
							objAddEditRxTaskResultVO.getStrConditionalTask());
				} else {
					taskCallStmt.setString(13, "");
				}

				if (objAddEditRxTaskResultVO.getStrConditionalValues() != null
						&& !"Select".equalsIgnoreCase(objAddEditRxTaskResultVO
								.getStrConditionalValues())
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO
										.getStrConditionalValues())) {
					taskCallStmt.setString(14,
							objAddEditRxTaskResultVO.getStrConditionalValues());
				} else {
					taskCallStmt.setString(14, "");
				}

				if (objAddEditRxTaskResultVO.getStrTaskType() != null
						&& !"Select".equalsIgnoreCase(objAddEditRxTaskResultVO
								.getStrTaskType())
						&& !RMDCommonConstants.EMPTY_STRING
								.equals(objAddEditRxTaskResultVO
										.getStrTaskType())) {
					taskCallStmt.setString(15,
							objAddEditRxTaskResultVO.getStrTaskType());
				} else {
					taskCallStmt.setString(15, "");
				}
				/* Adding for SMART Reveal Ends */

				taskCallStmt.registerOutParameter(16, java.sql.Types.NUMERIC);

				taskCallStmt.executeUpdate();

				longTaskId = taskCallStmt.getLong(16);
				// End: The Rx Task data inserting into gets_sd_recom_task table

				//Start - Insert Repair code into GETS_SD_RECOM_TASK_REPAIR_CODE table
				repairCodes = objAddEditRxTaskResultVO.getRepairCodes();
				if(repairCodes != null && !repairCodes.isEmpty()){
					repairCodeStmt = conn.prepareStatement(strRepairCodeQuery);
					for(RxTaskRepairCodeVO repairCode: repairCodes) {							
						repairCodeStmt.setLong(1, longTaskId);
						repairCodeStmt.setLong(2, Long.parseLong(repairCode.getStrRepairCodeId()));
						repairCodeStmt.setString(3, strUserID);
						repairCodeStmt.setString(4, strUserID);
						repairCodeStmt.addBatch();
					}
					repairCodeStmt.executeBatch();
				}					
				//End - Insert Repair code into GETS_SD_RECOM_TASK_REPAIR_CODE table
				
				// Begin: The Rx Task data inserting into GETS_SD_RECOM_TASK_DOC
				// table
				if (null == objAddEditRxTaskResultVO.getStrSubTask()
						|| "N".equalsIgnoreCase(objAddEditRxTaskResultVO
								.getStrSubTask())) {
					// Create task path
					taskPath = rxBasePath + File.separator + recomId.toString();
				}
				if (RMDCommonUtility
						.isCollectionNotEmpty(objAddEditRxTaskResultVO
								.getAddEditRxTaskDocVO())) {

					/******** Added for task doc file movement ***/
					// String
					// updated_path=rxBasePath+File.separator+recomId.toString()+File.separator+strTaskNo;//updated
					// file path
					if ("Y".equalsIgnoreCase(objAddEditRxTaskResultVO
							.getStrSubTask())) {
						finTaskPath = taskPath;
					} else {
						finTaskPath = taskPath;
					}
					String sDBDocPath = finTaskPath.replace(rxDataUrl,
							rxDataDBURL);

					task_attachment = new File(finTaskPath);
					if (!task_attachment.exists()) {
						task_attachment.mkdirs();
						filePathCreated.add(task_attachment.getAbsolutePath());
					}

					/****** End ---task doc file movement ****/

					List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = objAddEditRxTaskResultVO
							.getAddEditRxTaskDocVO();
					AddEditRxTaskDocVO addEditRxTaskDocVO = null;

					for (Iterator<AddEditRxTaskDocVO> iterator = lstAddEditRxTaskDocVO
							.iterator(); iterator.hasNext();) {

						addEditRxTaskDocVO = (AddEditRxTaskDocVO) iterator
								.next();

						// This check specifically for Clone Rx functionality
						if (addEditRxTaskDocVO.getStrDocData() != null
								&& !"".equals(addEditRxTaskDocVO
										.getStrDocData())
								&& addEditRxTaskDocVO.getStrDocData().length() > 0) {

							String finPath_title = finTaskPath + File.separator
									+ addEditRxTaskDocVO.getStrDocTitle();
							String sDBDomainPath = this.domainPath + sDBDocPath;

							// Insert details in Task Doc table
							taskDocCallStmt = conn
									.prepareCall("{ call GETS_SD_NEWRECOM_PKG.TaskDocInsert_pr(?,?,?,?,?) }");
							taskDocCallStmt.setString(1, strUserID);
							taskDocCallStmt.setLong(2, longTaskId);
							if (addEditRxTaskDocVO.getStrDocTitle() != null
									&& !RMDCommonConstants.EMPTY_STRING
											.equals(addEditRxTaskDocVO
													.getStrDocTitle())) {
								taskDocCallStmt.setString(3,
										addEditRxTaskDocVO.getStrDocTitle());
							}
							int iDelete = 0;

							taskDocCallStmt.setInt(4, iDelete);

							taskDocCallStmt.setString(5, sDBDomainPath);
							taskDocCallStmt.executeUpdate();

							/******** Added for task doc file movement *********************************/
							// Decode docData, create a file object and load it
							// in the final path
							// Insert the complete URL minus domain into the
							// RECOM_TASK table
							byte[] bArr = addEditRxTaskDocVO.getStrDocData()
									.getBytes();
							byte[] bDecodedArr = Base64.decode(bArr);
							File file = new File(finPath_title);
							FileUtils.writeByteArrayToFile(file, bDecodedArr);

						}// End changes for Clone Rx functionality

						/********** End --taskdoc file movement ******************************/

					}
				}
				// End: The Rx Task data inserting into GETS_SD_RECOM_TASK_DOC
				// table
			}

			fetchRecommDetails(addeditRxServiceVO);
			addeditRxServiceVO
					.setEditRxTaskResultList(new ArrayList<AddEditRxTaskResultVO>());

			/*
			 * Insert records into GETS_SD_ML_RECOM_COMMENT for multi-lingual
			 * support, by invoking SDRecom_Update_pr And this must be inserted
			 * only once - when Rx is saved as 'Approved' Note - This will
			 * insert an new entry in SD_RECOM_HIST table. Since this procedure
			 * is also used by EOA, hence this can be changed after
			 * de-commissioning
			 */

			if (RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
					.getStrRxStatus())
					&& RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
							.getStrRxType())) {
				newRecomPrepStmt.setString(6, RMDServiceConstants.STANDARD);
			} else {
				newRecomPrepStmt
						.setString(6, addeditRxServiceVO.getStrRxType());
			}

			if (StringUtils.isNotBlank(addeditRxServiceVO.getStrRxStatus())
					&& RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
							.getStrRxStatus())) {
				newRecomPrepStmt.setString(7, RMDServiceConstants.APPROVED);
			} else {
				newRecomPrepStmt.setString(7, RMDServiceConstants.INPROCESS);
			}
			if ((RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
					.getStrRxStatus()))
					|| (RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
							.getStrRxType()) && RMDServiceConstants.DRAFT
							.equals(addeditRxServiceVO.getStrRxStatus()))) {
				recomCommentCallStmt = conn
						.prepareCall("{ call GETS_SD_EDITRECOM_PKG.SDRECOM_UPDATE_PR(?,?,?,?,?,?,?,?,?,?,?) }");
				recomCommentCallStmt.setInt(1, recomId);
				recomCommentCallStmt.setString(2, strUserID);
				recomCommentCallStmt
						.setString(3, AppSecUtil
								.stripNonValidXMLCharactersForKM(AppSecUtil
										.decodeString(addeditRxServiceVO
												.getStrTitle())));
				recomCommentCallStmt.setString(4,
						addeditRxServiceVO.getStrSelAssetImp());
				recomCommentCallStmt.setString(5,
						addeditRxServiceVO.getStrSelUrgRepair());
				recomCommentCallStmt.setString(6,
						addeditRxServiceVO.getStrSelEstmTimeRepair());
				if (RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
						.getStrRxType())) {
					recomCommentCallStmt.setString(7,
							RMDServiceConstants.CUSTOM);
					recomCommentCallStmt.setString(8,
							RMDServiceConstants.INPROCESS);
				} else {
					recomCommentCallStmt.setString(7,
							RMDServiceConstants.STANDARD);
					recomCommentCallStmt.setString(8,
							RMDServiceConstants.APPROVED);
				}
				recomCommentCallStmt.setString(9,
						RMDCommonConstants.ZERO_STRING);
				recomCommentCallStmt.setString(10,
						addeditRxServiceVO.getStrQueue());
				if (null != addeditRxServiceVO.getOriginalId()
						&& !RMDCommonConstants.EMPTY_STRING
								.equalsIgnoreCase(addeditRxServiceVO
										.getOriginalId())) {
					recomCommentCallStmt.setString(11, addeditRxServiceVO
						.getOriginalId());
				}else{
					recomCommentCallStmt.setString(11, null);
				}
				
				recomCommentCallStmt.execute();
			}
			// End of inserting into GETS_SD_ML_RECOM_COMMENT

			//OMDKM 2284 -  Rx Clone - Copy Translated Tasks to Clone - Start
			//Added By Koushik
			if(addeditRxServiceVO != null && addeditRxServiceVO.isClone()){
				copyCloneTasksCallStmt = conn
						.prepareCall("call GETS_RMD.GETS_SD_DELV_RECOM_CLONE_PKG.CLONE_TRANSLATED_TASKS_PR(?,?,?)");
				copyCloneTasksCallStmt.setString(1, recomId.toString());
				copyCloneTasksCallStmt.setString(2, addeditRxServiceVO.getParentSolutionId());
				copyCloneTasksCallStmt.setString(3, strUserID);
				
				copyCloneTasksCallStmt.execute();
			}
			//OMDKM 2284 -  Rx Clone - Copy Translated Tasks to Clone - End
			if (conn != null) {
				conn.commit();
			}

		} catch (RMDDAOConnectionException ex) {
			if (filePathCreated.size() > 0) {// Delete the file paths created if
												// it is a rollback
				boolean bDelete = deleteDirs(filePathCreated);
				LOG.error("Directories cannot be deleted :" + bDelete);
			}

			conn.rollback();

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVEADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException rmdDAOException) {

			if (filePathCreated.size() > 0) {// Delete the file paths created if
												// it is a rollback
				boolean bDelete = deleteDirs(filePathCreated);
				LOG.error("Directories cannot be deleted :" + bDelete);
			}

			conn.rollback();

			LOG.error(rmdDAOException.getMessage());
			throw rmdDAOException;
		} catch (Exception e) {

			if (filePathCreated.size() > 0) {// Delete the file paths created if
												// it is a rollback
				boolean bDelete = deleteDirs(filePathCreated);
				LOG.error("Directories cannot be deleted :" + bDelete);
			}

			conn.rollback();

			LOG.error(e.getMessage());

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVEADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			try {

				if (modelCallStmt != null) {
					modelCallStmt.close();
				}
				if (taskCallStmt != null) {
					taskCallStmt.close();
				}
				if (taskDocCallStmt != null) {
					taskDocCallStmt.close();
				}
				if (configPrepStmt != null) {
					configPrepStmt.close();
				}
				if (newRecomPrepStmt != null) {
					newRecomPrepStmt.close();
				}
				if (histPrepStmt != null) {
					histPrepStmt.close();
				}
				if (queueStatement != null) {
					queueStatement.close();
				}
				if (recomIdStmt != null) {
					recomIdStmt.close();
				}
				if (recomIdResultSet != null) {
					recomIdResultSet.close();
				}
				if (taskResultSet != null) {
					taskResultSet.close();
				}
				if (recomCommentCallStmt != null) {
					recomCommentCallStmt.close();
				}
				if (queueResultSet != null) {
					queueResultSet.close();
				}
				if (conn != null) {
					conn.close();
				}
				releaseSession(session);
			} catch (SQLException e) {
				throw e;
			}
		}
		LOG.debug("AddEditRxDAOImpl.saveNewRecomm() Ended");
		return recomId.toString();
	}

	/**
	 * Method to delete the directories & files
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean deleteDirs(List<String> filePath) {

		boolean bDeleted = true;
		try {

			for (String path : filePath) {
				File file = new File(path);
				FileUtils.deleteDirectory(file);

				File parent = new File(file.getParent());
				if (parent != null && parent.isDirectory()
						&& parent.list().length == 0)
					parent.delete();
			}

		} catch (Exception e) {

			bDeleted = false;
		}
		return bDeleted;
	}

	/*
	 * This method used to update the existing recommendation in the database
	 * 
	 * @Param AddEditRxServiceVO
	 * 
	 * @return String
	 * 
	 * @throws RMDDAOException, SQLException
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String saveEditRecomm(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException, SQLException {
		LOG.debug("AddEditRxDAOImpl.saveEditRecomm() Started");

		String strRXID = RMDCommonConstants.EMPTY_STRING;
		String strUserID = RMDCommonConstants.EMPTY_STRING;
		String strLanguage = RMDCommonConstants.EMPTY_STRING;
		String strFromPage = RMDCommonConstants.EMPTY_STRING;
		String strObjId = RMDCommonConstants.EMPTY_STRING;
		List<String> lstModels = null;
		String strModelId = "";
		String strModelArray[];
		String strclosure = RMDCommonConstants.EMPTY_STRING;
		String strRxDelivered = RMDCommonConstants.EMPTY_STRING;
		String strTaskQuery = RMDCommonConstants.EMPTY_STRING;
		String strTaskDoc = RMDCommonConstants.EMPTY_STRING;
		String strTask = RMDCommonConstants.EMPTY_STRING;
		String strRepCode = RMDCommonConstants.EMPTY_STRING;
		String strConfig = RMDCommonConstants.EMPTY_STRING;

		String strRxMetricsQuery = RMDCommonConstants.EMPTY_STRING;
		String Str_Version = null;
		String Str_Creation_Dt = null;
		String Str_CreatedBy = null;
		String revision_Flag = "N";
		String recom_Flag = "N";
		HashMap<String, Long> objidcompMap = null;

		long longTaskId = 0L;
		long taskObjId = 0L;

		int iTask = RMDCommonConstants.INT_CONST;

		Connection conn = null;
		CallableStatement callableStatement = null;
		CallableStatement modelCallStmt = null;
		CallableStatement taskCallStmt = null;
		CallableStatement taskDocCallStmt = null;
		CallableStatement recomCommentCallStmt = null;

		PreparedStatement taskStatement = null;
		PreparedStatement taskStatementObj = null;
		PreparedStatement taskQueryStatement = null;

		PreparedStatement taskDocDelStmt = null;
		PreparedStatement taskDelStmt = null;
		PreparedStatement repCodeDelStmt = null;
		PreparedStatement taskDelStmtUTF = null;
		PreparedStatement configPrepStmt = null;
		PreparedStatement customerPrepStmt = null;
		PreparedStatement fleetPrepStmt = null;
		PreparedStatement editRecomPrepStmt = null;
		PreparedStatement editHistPrepStmt = null;
		PreparedStatement statusQryPrepStmt = null;
		PreparedStatement configDelStmt = null;
		PreparedStatement customerDelStmt = null;
		PreparedStatement fleetDelStmt = null;
		PreparedStatement queueStatement = null;
		PreparedStatement rxMetricsDetailsStatement = null;
		PreparedStatement utfStmt = null;
		PreparedStatement utfDocStmt = null;		
		PreparedStatement repairCodeStmt = null;
		
		ResultSet taskResultSet = null;
		ResultSet taskResultSetObj = null;
		ResultSet taskQueryResultSet = null;
		ResultSet taskQueryResultSetUTF = null;
		ResultSet queueResultSet = null;
		ResultSet rxMetricsResultSet = null;
		
		Session session = null;
		Object configObject = RMDCommonConstants.NULL_STRING;
		Object editRecomObject = RMDCommonConstants.NULL_STRING;
		Object editHistObject = RMDCommonConstants.NULL_STRING;

		/***************** Added for task doc file movement. *************/
		String userHome = System.getProperty(RMDServiceConstants.USER_HOME);
		String rxBasePath = RMDCommonConstants.EMPTY_STRING;
		// Stores the path created
		List<String> filePathCreated = new ArrayList<String>();
		String tempRxPath = null;
		String thisRxPath = null;
		/********************** END- task doc file movement ****************************/
		List<RxTaskRepairCodeVO> repairCodes = null;
		try {			
			session = getHibernateSession();

			strUserID = addeditRxServiceVO.getStrUserName();
			strRXID = addeditRxServiceVO.getStrRxId();
			strLanguage = addeditRxServiceVO.getStrLanguage();
			strFromPage = addeditRxServiceVO.getStrFromPage();
			/**
			 * if Rx status is Approved and Rx type is not Standard then throw
			 * exception
			 * 
			 * if Rx status is not Approved and Rx type is Standard then throw
			 * exception
			 * */

			if (userHome == null
					|| (userHome.indexOf(RMDServiceConstants.SYMBOL_FRONT_SLAH) < 0 && userHome
							.indexOf(RMDServiceConstants.SYMBOL_BACK_SLAH) < 0)) {
				rxBasePath = RMDServiceConstants.RX_AUTH;
			} else {
				rxBasePath = userHome + File.separator
						+ RMDServiceConstants.RX_AUTH;
			}

			conn = session.connection();
			conn.setAutoCommit(false);

			/* Edit data from tables */
			LOG.debug("strFromPage---" + strFromPage);
			LOG.debug("strRXID---" + strRXID);

			if (RMDServiceConstants.EDITRECOMM.equals(strFromPage)
					&& null != strRXID) {

				// Fetch Max Version, Created By and Creation Date

				callableStatement = conn
						.prepareCall("{ call GETS_SD_EDITRECOM_PKG.get_recom_values_pr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
				callableStatement.setInt(1, Integer.parseInt(strRXID));
				callableStatement.registerOutParameter(2,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(3,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(4,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(5,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(6,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(7,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(8,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(9,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(10,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(11,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(12,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(13,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(14,
						java.sql.Types.VARCHAR);
				callableStatement.registerOutParameter(15,
						java.sql.Types.VARCHAR);
				callableStatement.execute();
				Str_Version = callableStatement.getString(4);
				Str_Creation_Dt = callableStatement.getString(9);
				Str_CreatedBy = callableStatement.getString(11);
				
				String rxPlotObjid = null;
				String deprecatedBy = null;
				Date deprecatedOn = null;
				
				if (null != addeditRxServiceVO.getAddEditRxPlotVO()) {
					if (null == addeditRxServiceVO.getAddEditRxPlotVO()
							.getStrPlotObjid()) {
						rxPlotObjid = getRxPlotNextVal(conn);
						insertRxPlotData(conn,
								addeditRxServiceVO.getAddEditRxPlotVO(),
								Long.valueOf(rxPlotObjid), strUserID);
					} else {
						rxPlotObjid = addeditRxServiceVO.getAddEditRxPlotVO()
								.getStrPlotObjid();
						updateRxPlotData(conn,
								addeditRxServiceVO.getAddEditRxPlotVO(),
								Long.valueOf(rxPlotObjid), strUserID);
					}
				}

				/* Edit for Rx Details */
				//OMDKM 2073 : YJ Changes to save depricated date/depricated by when deactivating an Rx Start
				if (StringUtils.isNotBlank(addeditRxServiceVO.getStrRxStatus())	&& RMDServiceConstants.DEACTIVE.equals(addeditRxServiceVO.getStrRxStatus()))
				{
					editRecomObject = " UPDATE gets_sd_recom SET last_updated_by = ?,last_updated_date = sysdate,title = ?,loco_impact= ?,urgency = ?,"
							+ "est_repair_time =?,recom_type= ?,recom_status= ?,version= ?,recom_move2queue = ?,recom2recom = ?, EXPORT_CONTROL = ?, RECOM2PLOT = ? , DEPRECATED_ON = ?, DEPRECATED_BY = ?, DESCRIPTION = ? WHERE objid = ?";
				} else {
				editRecomObject = " UPDATE gets_sd_recom SET last_updated_by = ?,last_updated_date = sysdate,title = ?,loco_impact= ?,urgency = ?,"
						+ "est_repair_time =?,recom_type= ?,recom_status= ?,version= ?,recom_move2queue = ?,recom2recom = ?, EXPORT_CONTROL = ?, RECOM2PLOT = ?, DESCRIPTION = ? WHERE objid = ?";
				}
				//OMDKM 2073 : YJ Changes to save depricated date/depricated by when deactivating an Rx End

				editRecomPrepStmt = conn
						.prepareStatement((String) editRecomObject);
				editRecomPrepStmt.setString(1, strUserID);
				editRecomPrepStmt
						.setString(2, AppSecUtil
								.stripNonValidXMLCharactersForKM(AppSecUtil
										.decodeString(addeditRxServiceVO
												.getStrTitle())));
				editRecomPrepStmt.setString(3,
						addeditRxServiceVO.getStrSelAssetImp());
				editRecomPrepStmt.setString(4,
						addeditRxServiceVO.getStrSelUrgRepair());
				editRecomPrepStmt.setString(5,
						addeditRxServiceVO.getStrSelEstmTimeRepair());

				if (RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
						.getStrRxStatus())
						&& RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
								.getStrRxType())) {
					editRecomPrepStmt
							.setString(6, RMDServiceConstants.STANDARD);
				} else {
					editRecomPrepStmt.setString(6,
							addeditRxServiceVO.getStrRxType());
				}

				if (StringUtils.isNotBlank(addeditRxServiceVO.getStrRxStatus())
						&& RMDServiceConstants.APPROVED
								.equals(addeditRxServiceVO.getStrRxStatus())) {
					editRecomPrepStmt
							.setString(7, RMDServiceConstants.APPROVED);
				} else if (StringUtils.isNotBlank(addeditRxServiceVO
						.getStrRxStatus())
						&& RMDServiceConstants.DRAFT.equals(addeditRxServiceVO
								.getStrRxStatus())) {
					editRecomPrepStmt.setString(7,
							RMDServiceConstants.INPROCESS);
				} else if (StringUtils.isNotBlank(addeditRxServiceVO
						.getStrRxStatus())
						&& RMDServiceConstants.DEACTIVE
								.equals(addeditRxServiceVO.getStrRxStatus())) {
					editRecomPrepStmt
							.setString(7, RMDServiceConstants.DEACTIVE);
					deprecatedBy = strUserID;
					deprecatedOn = new Date(System.currentTimeMillis());
				}

				// Custom Rx Changes Started
				if (StringUtils.isBlank(Str_Version)) {
					Str_Version = RMDCommonConstants.ZERO_STRING;
					editRecomPrepStmt.setInt(8, Integer.parseInt(Str_Version)
							+ Integer.parseInt(RMDCommonConstants.ZERO_STRING));
					revision_Flag = "Y";
				} else if (null != Str_Version && Str_Version.length() != 0) {
					editRecomPrepStmt.setInt(8, Integer.parseInt(Str_Version)
							+ Integer.parseInt(RMDCommonConstants.ONE_STRING));
					revision_Flag = "Y";
				} else if (RMDServiceConstants.APPROVED
						.equals(addeditRxServiceVO.getStrRxStatus())) {
					editRecomPrepStmt.setInt(8,
							Integer.parseInt(RMDCommonConstants.ZERO_STRING));
					revision_Flag = "Y";
					recom_Flag = "Y";
				}
				// Custom Rx Changes Ended

				editRecomPrepStmt
						.setString(9, addeditRxServiceVO.getStrQueue());
				editRecomPrepStmt.setString(10, null);
				editRecomPrepStmt.setString(11,
						addeditRxServiceVO.getStrExportControl());
				editRecomPrepStmt.setString(12, rxPlotObjid);
				
				//OMDKM 2073 : YJ Changes to save deprecated date/deprecated by when deactivating an Rx Start
				if (StringUtils.isNotBlank(addeditRxServiceVO
						.getStrRxStatus())
						&& RMDServiceConstants.DEACTIVE
						.equals(addeditRxServiceVO.getStrRxStatus()))
				{
					editRecomPrepStmt.setDate(13, deprecatedOn);
					editRecomPrepStmt.setString(14, deprecatedBy);
					editRecomPrepStmt.setString(15, AppSecUtil
							.stripNonValidXMLCharactersForKM(addeditRxServiceVO.getStrDescription()));
					editRecomPrepStmt.setString(16, strRXID);
				}
				else
				{
					editRecomPrepStmt.setString(13, AppSecUtil
							.stripNonValidXMLCharactersForKM(addeditRxServiceVO.getStrDescription()));
					editRecomPrepStmt.setString(14, strRXID);
				}
				//OMDKM 2073 : YJ Changes to save deprecated date/deprecated by when deactivating an Rx End
				
				editRecomPrepStmt.executeUpdate();

				LOG.debug("GETS_SD_RECOM_HIST started-");
				/* Insert into History table */
				editHistObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_HIST(OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RECOM_HIST2RECOM,CHANGE_DETAIL,VALUE,REVISION_NOTES) "
						+ "VALUES (gets_sd_recom_hist_seq.nextval, sysdate, ?, sysdate, ?, ?, ?, ?, ?)";

				editHistPrepStmt = conn
						.prepareStatement((String) editHistObject);
				editHistPrepStmt.setString(1, strUserID);
				editHistPrepStmt.setString(2, strUserID);
				editHistPrepStmt.setLong(3, Long.valueOf(strRXID));
				if (StringUtils.isNotBlank(addeditRxServiceVO
						.getStrRxStatus())
						&& RMDServiceConstants.DEACTIVE
						.equals(addeditRxServiceVO.getStrRxStatus()))
				{
					
					editHistPrepStmt.setString(4,
							RMDServiceConstants.DEACTIVATED);
					
				}else if (revision_Flag == "Y" && recom_Flag == "Y") {
					editHistPrepStmt.setString(4,
							RMDServiceConstants.RECOMMENDATION_TYPE_CHANGED);
				} else {
					editHistPrepStmt.setString(4,
							RMDServiceConstants.REVISION_NUMBER_CHANGED);
				}
				editHistPrepStmt.setInt(5, Integer.parseInt(Str_Version)
						+ Integer.parseInt(RMDCommonConstants.ONE_STRING));
				editHistPrepStmt.setString(6, AppSecUtil
						.stripNonValidXMLCharactersForKM(AppSecUtil
								.decodeString(addeditRxServiceVO
										.getStrRevHist())));

				editHistPrepStmt.executeUpdate();
				LOG.debug("GETS_SD_RECOM_HIST Ended-");

				/*
				 * Insert records into GETS_SD_ML_RECOM_COMMENT for
				 * multi-lingual support, by invoking SDRecom_Update_pr But this
				 * must be inserted only once - hence check the previous status
				 * and if custom-draft, and new status is Approved, then only
				 * insert Also, if record already exists in
				 * GETS_SD_ML_RECOM_COMMENT table, do not insert again (not
				 * needed for now - will follow EOA approach) Note - This will
				 * insert an new entry in SD_RECOM_HIST table. Since this
				 * procedure is also used by EOA, hence this can be changed
				 * after de-commissioning
				 */
				if ((RMDServiceConstants.APPROVED.equals(addeditRxServiceVO
						.getStrRxStatus()))
						|| (RMDServiceConstants.CUSTOM
								.equals(addeditRxServiceVO.getStrRxType()) && RMDServiceConstants.DRAFT
								.equals(addeditRxServiceVO.getStrRxStatus()))) {

					recomCommentCallStmt = conn
							.prepareCall("{ call GETS_SD_EDITRECOM_PKG.SDRECOM_UPDATE_PR(?,?,?,?,?,?,?,?,?,?,?) }");
					recomCommentCallStmt.setString(1, strRXID);
					recomCommentCallStmt.setString(2, strUserID);
					recomCommentCallStmt.setString(3, AppSecUtil
							.stripNonValidXMLCharactersForKM(AppSecUtil
									.decodeString(addeditRxServiceVO
											.getStrTitle())));
					recomCommentCallStmt.setString(4,
							addeditRxServiceVO.getStrSelAssetImp());
					recomCommentCallStmt.setString(5,
							addeditRxServiceVO.getStrSelUrgRepair());
					recomCommentCallStmt.setString(6,
							addeditRxServiceVO.getStrSelEstmTimeRepair());
					if (RMDServiceConstants.CUSTOM.equals(addeditRxServiceVO
							.getStrRxType())) {
						recomCommentCallStmt.setString(7,
								RMDServiceConstants.CUSTOM);
						recomCommentCallStmt.setString(8,
								RMDServiceConstants.INPROCESS);
					} else {
						recomCommentCallStmt.setString(7,
								RMDServiceConstants.STANDARD);
						recomCommentCallStmt.setString(8,
								RMDServiceConstants.APPROVED);
					}
					recomCommentCallStmt.setString(9, Str_Version);
					recomCommentCallStmt.setString(10,
							addeditRxServiceVO.getStrQueue());
					recomCommentCallStmt.setString(11, null);

					recomCommentCallStmt.execute();
				}
				// End of inserting into GETS_SD_ML_RECOM_COMMENT

				/* Edit for Subsystem */
				insertRxSubSystem(conn,addeditRxServiceVO.getSubSystemList(),Long.parseLong(strRXID),strUserID,strFromPage);
				/* Edit for Subsystem END */

				/* Edit for Model */
				lstModels = addeditRxServiceVO.getModelList();

				if (RMDCommonUtility.isCollectionNotEmpty(lstModels)) {
					for (String strModelDetails : lstModels) {
						strModelArray = RMDCommonUtility.splitString(
								strModelDetails,
								RMDCommonConstants.PIPELINE_CHARACTER);
						strModelId = strModelId
								+ "~"
								+ strModelArray[RMDCommonConstants.INT_CONST_ZERO];
					}
					strModelId = strModelId.substring(1, strModelId.length());

					modelCallStmt = conn
							.prepareCall("{ call gets_rmd.gets_sd_editrecom_pkg.Model_Insert_pr(?,?,?,?,?) }");
					modelCallStmt.setInt(1, Integer.parseInt(strRXID));
					modelCallStmt.setString(2, strModelId);
					modelCallStmt.setString(3, strUserID);
					modelCallStmt.setString(4, Str_CreatedBy);
					modelCallStmt.setString(5, Str_Creation_Dt);
					modelCallStmt.execute();
				}
				/* Edit for Model END */

				/*Edit For Customer*/
				String strFleet = "delete from GETS_SD.GETS_SD_RECOM_MTM_FLEET where FLEET2CUST IN (SELECT OBJID FROM GETS_SD.GETS_SD_RECOM_MTM_CUST where CUST2RECOM=?)";
				fleetDelStmt = conn.prepareStatement(strFleet);
				fleetDelStmt.setLong(1, Long.valueOf(strRXID));
				fleetDelStmt.executeQuery();
				
				String strCustomer = "delete from GETS_SD.GETS_SD_RECOM_MTM_CUST where CUST2RECOM=?";
				customerDelStmt = conn.prepareStatement(strCustomer);
				customerDelStmt.setLong(1, Long.valueOf(strRXID));
				customerDelStmt.executeQuery();
				LOG.debug("Customer started -");
				
					List<String> customerList = addeditRxServiceVO.getCustomerList();
					List<String> fleetList = addeditRxServiceVO.getFleetList();
					if (RMDCommonUtility.isCollectionNotEmpty(customerList)) {
						for (String strCustomerDetails : customerList) {
							String[] strCustomerArray = RMDCommonUtility.splitString(
									strCustomerDetails,
									RMDCommonConstants.PIPELINE_CHARACTER);								
										
						String customerObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_MTM_CUST(OBJID,CUST2BUSORG,CUST2RECOM,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATED_BY,CREATION_DATE,EXCLUDE) "
								+ "VALUES(GETS_SD.GETS_SD_RECOM_MTM_CUST_SEQ.NEXTVAL,?,?,sysdate,?,?,to_date(?,'MM/DD/YY HH24:MI:SS'),?)";
						customerPrepStmt = conn
								.prepareStatement((String) customerObject);
						customerPrepStmt.setInt(1,Integer.parseInt(strCustomerArray[0]) );
						customerPrepStmt.setInt(2,
								Integer.parseInt(strRXID));
						customerPrepStmt.setString(3, strUserID);
						customerPrepStmt.setString(4,
								Str_CreatedBy);
						customerPrepStmt.setString(5,
								Str_Creation_Dt);
						customerPrepStmt.setString(6, addeditRxServiceVO.getExclude());
						customerPrepStmt.executeUpdate();
						LOG.debug("Customer completed -");
						

						if (RMDCommonUtility.isCollectionNotEmpty(fleetList)) {
							for (String strFleetDetails : fleetList) {
								String[] strFleetArray = RMDCommonUtility.splitString(
										strFleetDetails,
										RMDCommonConstants.PIPELINE_CHARACTER);								
											if(strCustomerArray[0].equalsIgnoreCase(strFleetArray[2])){
							String fleetObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_MTM_FLEET(OBJID,FLEET2FLEET,FLEET2CUST,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,EXCLUDE) "
									+ "VALUES(GETS_SD.GETS_SD_RECOM_MTM_FLEET_SEQ.NEXTVAL,?,GETS_SD.GETS_SD_RECOM_MTM_CUST_SEQ.CURRVAL,sysdate,?,to_date(?,'MM/DD/YY HH24:MI:SS'),?,?)";

							fleetPrepStmt = conn
									.prepareStatement((String) fleetObject);
							fleetPrepStmt.setInt(1,Integer.parseInt(strFleetArray[0]) );							
							fleetPrepStmt.setString(2, strUserID);
							fleetPrepStmt.setString(3,
									Str_Creation_Dt);
							fleetPrepStmt.setString(4,
									Str_CreatedBy);
							fleetPrepStmt.setString(5, addeditRxServiceVO.getExclude());
							fleetPrepStmt.executeUpdate();
							LOG.debug("Customer completed -");
											}
						}
					}
					}
				}
					
					
					
				
				
				
				/* Edit for Configuration */
				strConfig = "delete from GETS_SD.GETS_SD_RECOM_CONFIG where RECOM_CONFIG2RECOM=?";
				configDelStmt = conn.prepareStatement(strConfig);
				configDelStmt.setLong(1, Long.valueOf(strRXID));
				configDelStmt.executeQuery();

				LOG.debug("Config started -");
				if (null != addeditRxServiceVO.getArlRxConfig()
						&& RMDCommonUtility
								.isCollectionNotEmpty(addeditRxServiceVO
										.getArlRxConfig())) {

					ArrayList<RxConfigVO> arlRxConfig = addeditRxServiceVO
							.getArlRxConfig();
					for (Iterator<RxConfigVO> iterator = arlRxConfig.iterator(); iterator
							.hasNext();) {

						RxConfigVO rxConfigVO = (RxConfigVO) iterator.next();

						configObject = "INSERT INTO GETS_SD.GETS_SD_RECOM_CONFIG(OBJID,RECOM_CONFIG2RECOM,CONFIG_FUNCTION,CONFIG_NAME,VALUE1,VALUE2,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE,LANGUAGE) "
								+ "VALUES(GETS_SD.GETS_SD_RECOM_CONFIG_SEQ.NEXTVAL,?,?,?,?,?,?,sysdate,?,to_date(?,'MM/DD/YY HH24:MI:SS'),?)";

						configPrepStmt = conn
								.prepareStatement((String) configObject);
						configPrepStmt.setInt(1, Integer.parseInt(strRXID));
						configPrepStmt.setString(2,
								rxConfigVO.getConfigFunction());
						configPrepStmt.setString(3, rxConfigVO.getConfigName());
						configPrepStmt.setString(4,
								rxConfigVO.getConfigValue1());
						configPrepStmt.setString(5,
								rxConfigVO.getConfigValue2());
						configPrepStmt.setString(6, strUserID);
						configPrepStmt.setString(7, Str_CreatedBy);
						configPrepStmt.setString(8, Str_Creation_Dt);
						configPrepStmt.setString(9, strLanguage);

						configPrepStmt.executeUpdate();
						LOG.debug("Config completed -");
					}
				}
				/* Edit for Configuration END */

				/* Edit for Task */
				/*
				 * Loop through the incoming task details 1: If DocPath present
				 * and not deleted, load Base64 encoded data from file system
				 * and set in task doc object 2. If deleted true, then doc Data
				 * to be null when attachments removed and doc Data present when
				 * removed & new attachment added 3: Rename the existing folder
				 * on file system to <RX ID>_temp 4. Handle the task details as
				 * if they are new tasks 5. If transaction successful, delete
				 * the <RX ID>_temp folder. Else, rename 'temp' folder back to
				 * normal
				 */

				// Step 1 thru 3 explained above
				iTask = addeditRxServiceVO.getLstEditTaskRxResultVO().size();
				LOG.debug("iTask -" + iTask);
				AddEditRxTaskResultVO objAddEditRxTaskResultVO = null;

				rxBasePath = rxDataUrl + File.separator
						+ RMDServiceConstants.RX_AUTH;
				thisRxPath = rxBasePath + File.separator + strRXID;

				for (int index = 0; index < iTask; index++) {
					objAddEditRxTaskResultVO = (AddEditRxTaskResultVO) addeditRxServiceVO
							.getLstEditTaskRxResultVO().get(index);

					// Task Doc details
					if (RMDCommonUtility
							.isCollectionNotEmpty(objAddEditRxTaskResultVO
									.getAddEditRxTaskDocVO())) {

						List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = objAddEditRxTaskResultVO
								.getAddEditRxTaskDocVO();

						for (AddEditRxTaskDocVO addEditRxTaskDocVO : lstAddEditRxTaskDocVO) {

							if (addEditRxTaskDocVO.getStrDocPath() != null
									&& !"".equals(addEditRxTaskDocVO
											.getStrDocPath())
									&& !"Y".equalsIgnoreCase(addEditRxTaskDocVO
											.getStrDelete())) {
								String sDocPath = addEditRxTaskDocVO
										.getStrDocPath().replace(
												this.domainPath, "");
								String sBase64Data = readBase64FileData(
										sDocPath,
										addEditRxTaskDocVO.getStrDocTitle());
								addEditRxTaskDocVO.setStrDocData(sBase64Data);
							} // For others
						}
					}
				}
				// rename folder on filesystem
				File file = new File(thisRxPath).getAbsoluteFile();
				tempRxPath = rxBasePath + File.separator + strRXID + "_temp";
				File newFile = new File(tempRxPath).getAbsoluteFile();
				if (file.exists()) { // if directory exists rename to temp
					FileUtils.copyDirectory(file, newFile, true);
					// Delete directory and sub-directory
					File[] fileArr = file.listFiles();
					for (File file1 : fileArr) {
						if (file1.isDirectory()) {
							FileUtils.deleteDirectory(file1);
						} else {
							FileUtils.forceDelete(file1);
						}
					}
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e) {
						// In case FileUtils is not able to delete, try deleting
						// using java.io.File
						fileArr = file.listFiles();
						for (File file1 : fileArr) {
							file1.delete();
						}
						file.delete();
					}
				}

				// Now start deleting from DB
				LOG.debug("Task and doc deleting started-");
				strTaskQuery = "select OBJID from GETS_SD.GETS_SD_RECOM_TASK where RECOM_TASK2RECOM=?";
				taskQueryStatement = conn.prepareStatement(strTaskQuery);
				strTaskDoc = "delete from GETS_SD.GETS_SD_RECOM_TASK_DOC where RECOM_DOC2RECOM_TASK =? AND LINK_LANG_TYPE IS NULL";
				taskDocDelStmt = conn.prepareStatement(strTaskDoc);

				List<String> recomTaskObjIDlist = new ArrayList<String>();

				taskQueryStatement.setLong(1, Long.valueOf(strRXID));
				for (taskQueryResultSet = taskQueryStatement.executeQuery(); ((ResultSet) taskQueryResultSet)
						.next();) {
					taskObjId = taskQueryResultSet.getLong(1);
					recomTaskObjIDlist.add(Long.toString(taskObjId));
					taskDocDelStmt.setLong(1, taskObjId);
					taskDocDelStmt.executeQuery();
				}

				strRepCode = "delete from GETS_SD_RECOM_TASK_REPAIR_CODE where RECOM_TASK_ID IN( SELECT OBJID FROM GETS_SD_RECOM_TASK WHERE RECOM_TASK2RECOM =  ?)";
				repCodeDelStmt = conn.prepareStatement(strRepCode);
				repCodeDelStmt.setLong(1, Long.valueOf(strRXID));
				repCodeDelStmt.executeQuery();
				
				strTask = "delete from gets_sd_recom_task where recom_task2recom =?";
				taskDelStmt = conn.prepareStatement(strTask);
				taskDelStmt.setLong(1, Long.valueOf(strRXID));
				taskDelStmt.executeQuery();
				LOG.debug("Task and doc deleting ended-");

				/* Updating Task Details begins */
				iTask = addeditRxServiceVO.getLstEditTaskRxResultVO().size();
				LOG.debug("iTask -" + iTask);

				String strTaskNo = null;
				String selectQuery = RMDCommonConstants.EMPTY_STRING;
				String strFinalTaskNo = RMDCommonConstants.EMPTY_STRING;
				String selectQueryObjid = RMDCommonConstants.EMPTY_STRING;
				String taskPath = null;
				String finTaskPath = null;
				File task_attachment = null;
				objidcompMap = new HashMap<String, Long>();
				
				String strRepairCodeQuery = "INSERT INTO GETS_SD_RECOM_TASK_REPAIR_CODE(OBJID, RECOM_TASK_ID, REPAIR_CODE_ID, LAST_UPDATED_DATE, LAST_UPDATED_BY) "
						+"VALUES(GETS_SD_RECOM_TASK_RPR_CODE_SQ.nextVal, ?, ?, sysdate, ?)";
				
				for (int index = 0; index < iTask; index++) {

					objAddEditRxTaskResultVO = (AddEditRxTaskResultVO) addeditRxServiceVO
							.getLstEditTaskRxResultVO().get(index);

					String strOldTaskObjId = objAddEditRxTaskResultVO
							.getStrTaskObjID();
					String strConditionalTask = objAddEditRxTaskResultVO
							.getStrConditionalTask();
					strTaskNo = objAddEditRxTaskResultVO.getStrNO();

					if (strTaskNo.lastIndexOf('.') != -1) {
						strFinalTaskNo = strTaskNo.substring(0,
								strTaskNo.lastIndexOf('.'));
						selectQuery = "select objid from gets_sd_recom_task where task_id=? and recom_task2recom=?";
						taskStatement = conn.prepareStatement(selectQuery);
						taskStatement.setString(1, strFinalTaskNo);
						taskStatement.setLong(2, Long.valueOf(strRXID));
						taskResultSet = taskStatement.executeQuery();

						while (taskResultSet.next()) {
							strObjId = taskResultSet.getString(1);
						}
					} else {
						if (strConditionalTask != null
								|| strConditionalTask != ""
								|| !"None".equalsIgnoreCase(strConditionalTask)) {
							selectQueryObjid = "select objid from gets_sd_recom_task where task_id=? and recom_task2recom=?";
							taskStatementObj = conn
									.prepareStatement(selectQueryObjid);
							taskStatementObj.setString(1, strConditionalTask);
							taskStatementObj.setLong(2, Long.valueOf(strRXID));
							taskResultSetObj = taskStatementObj.executeQuery();
							while (taskResultSetObj.next()) {
								strObjId = taskResultSetObj.getString(1);
								objAddEditRxTaskResultVO
										.setStrConditionalTask(strObjId);
							}
						}
					}

					taskCallStmt = conn
							.prepareCall("{ call GETS_SD_NEWRECOM_PKG.TaskInsert_pr(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
					taskCallStmt.setString(1, strUserID);
					taskCallStmt.setString(2, strTaskNo);
					taskCallStmt
							.setString(
									3,
									AppSecUtil
											.stripNonValidXMLCharactersForRx(objAddEditRxTaskResultVO
													.getStrTaskDeails()));

					if (objAddEditRxTaskResultVO.getStrLSL() != null
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrLSL())) {
						taskCallStmt.setString(4,
								objAddEditRxTaskResultVO.getStrLSL());
					} else {
						taskCallStmt.setString(4, "");
					}
					if (objAddEditRxTaskResultVO.getStrUSL() != null
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrUSL())) {
						taskCallStmt.setString(5,
								objAddEditRxTaskResultVO.getStrUSL());
					} else {
						taskCallStmt.setString(5, "");
					}
					if (objAddEditRxTaskResultVO.getStrTarget() != null
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrTarget())) {
						taskCallStmt.setString(6,
								objAddEditRxTaskResultVO.getStrTarget());
					} else {
						taskCallStmt.setString(6, "");
					}

					/*
					 * Setting default value null to Feedback Column as it is
					 * removed from UI , and will be set null until we remove
					 * feedback column from database
					 */
					taskCallStmt.setString(7, "");

					if (objAddEditRxTaskResultVO.getStrSubTask().equals(
							RMDCommonConstants.LETTER_Y)) {
						taskCallStmt.setString(8, strObjId);
					} else {
						taskCallStmt.setString(8, null);
					}
					taskCallStmt.setInt(9, Integer.parseInt(strRXID));
					if (objAddEditRxTaskResultVO.getStrRepairCodeId() != null
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrRepairCodeId())) {
						taskCallStmt.setString(10,
								objAddEditRxTaskResultVO.getStrRepairCodeId());
					} else {
						taskCallStmt.setLong(10, 0);
					}
					if (null != objAddEditRxTaskResultVO.getStrMand()
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrMand())) {
						taskCallStmt.setString(11,
								objAddEditRxTaskResultVO.getStrMand());
					} else {
						taskCallStmt.setLong(11, 0);
					}
					strRxMetricsQuery = "SELECT RX_RANK,RX_PRECISION,DELV_CNT_NOREISSUE FROM GETS_TOOL_RX_METRICS WHERE RX_METRICS2RECOM =?";
					rxMetricsDetailsStatement = conn
							.prepareStatement(strRxMetricsQuery);
					rxMetricsDetailsStatement.setLong(1, Long.valueOf(strRXID));
					rxMetricsResultSet = rxMetricsDetailsStatement
							.executeQuery();
					while (rxMetricsResultSet.next()) {

						strRxDelivered = rxMetricsResultSet.getString(3);
					}
					if (null != objAddEditRxTaskResultVO.getStrClosurePerc()
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrClosurePerc())) {
						strclosure = objAddEditRxTaskResultVO
								.getStrClosurePerc();
					}
					float closurePercent = 0.0F;

					NumberFormat numberformat = NumberFormat.getInstance();
					numberformat.setMaximumFractionDigits(2);

					if (null != strclosure
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(strclosure)
							&& null != strRxDelivered
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(strRxDelivered)) {
						if ((double) Float.parseFloat(strclosure) > 0.0D
								&& (double) Float.parseFloat(strRxDelivered) > 0.0D) {
							closurePercent = (Float.parseFloat(strclosure) * Float
									.parseFloat(strRxDelivered)) / 100F;
						}
					}

					taskCallStmt.setString(12, Float.toString(closurePercent));

					/* Smart Reveal Changes Start */
					if (objAddEditRxTaskResultVO.getStrConditionalTask() != null
							&& !"None"
									.equalsIgnoreCase(objAddEditRxTaskResultVO
											.getStrConditionalTask())
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrConditionalTask())) {
						taskCallStmt.setString(13, objAddEditRxTaskResultVO
								.getStrConditionalTask());
					} else {
						taskCallStmt.setString(13, "");
					}

					if (objAddEditRxTaskResultVO.getStrConditionalValues() != null
							&& !"Select"
									.equalsIgnoreCase(objAddEditRxTaskResultVO
											.getStrConditionalValues())
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrConditionalValues())) {
						taskCallStmt.setString(14, objAddEditRxTaskResultVO
								.getStrConditionalValues());
					} else {
						taskCallStmt.setString(14, "");
					}

					if (objAddEditRxTaskResultVO.getStrTaskType() != null
							&& !"Select"
									.equalsIgnoreCase(objAddEditRxTaskResultVO
											.getStrTaskType())
							&& !RMDCommonConstants.EMPTY_STRING
									.equals(objAddEditRxTaskResultVO
											.getStrTaskType())) {
						taskCallStmt.setString(15,
								objAddEditRxTaskResultVO.getStrTaskType());
					} else {
						taskCallStmt.setString(15, "");
					}
					/* Smart Reveal Changes End */

					taskCallStmt.registerOutParameter(16,
							java.sql.Types.NUMERIC);
					taskCallStmt.executeUpdate();

					longTaskId = taskCallStmt.getLong(16);
					// End: The Rx Task data inserting into gets_sd_recom_task
					// table
					
					//Start - Insert Repair code into GETS_SD_RECOM_TASK_REPAIR_CODE table
					repairCodes = objAddEditRxTaskResultVO.getRepairCodes();
					if(repairCodes != null && !repairCodes.isEmpty()){
						repairCodeStmt = conn.prepareStatement(strRepairCodeQuery);
						for(RxTaskRepairCodeVO repairCode: repairCodes) {							
							repairCodeStmt.setLong(1, longTaskId);
							repairCodeStmt.setLong(2, Long.parseLong(repairCode.getStrRepairCodeId()));
							repairCodeStmt.setString(3, strUserID);
							repairCodeStmt.addBatch();
						}
						repairCodeStmt.executeBatch();
					}					
					//End - Insert Repair code into GETS_SD_RECOM_TASK_REPAIR_CODE table
					
					// Creating map for oldobjid and new objid and updating into
					// utf table with new objid
					objidcompMap.put(strOldTaskObjId, longTaskId);

					String utfUpdate = "update gets_sd_recom_task_utf8 set link_recom_task=? where link_recom_task=?";
					utfStmt = conn.prepareStatement(utfUpdate);
					utfStmt.setLong(1, longTaskId);
					utfStmt.setString(2, strOldTaskObjId);
					utfStmt.executeUpdate();

					recomTaskObjIDlist.remove(strOldTaskObjId);

					// Begin: The Rx Task data inserting into
					// GETS_SD_RECOM_TASK_DOC table
					if (null == objAddEditRxTaskResultVO.getStrSubTask()
							|| "N".equalsIgnoreCase(objAddEditRxTaskResultVO
									.getStrSubTask())) {
						// Create task path
						taskPath = rxBasePath + File.separator
								+ strRXID.toString();
					}

					if (RMDCommonUtility
							.isCollectionNotEmpty(objAddEditRxTaskResultVO
									.getAddEditRxTaskDocVO())) {

						/***************** Added for task doc file movement. *************/
						// String
						// updated_path=rxBasePath+File.separator+recomId.toString()+File.separator+strTaskNo;//updated
						// file path
						if ("Y".equalsIgnoreCase(objAddEditRxTaskResultVO
								.getStrSubTask())) {
							finTaskPath = taskPath;
						} else {
							finTaskPath = taskPath;
						}
						String sDBDocPath = finTaskPath.replace(rxDataUrl,
								rxDataDBURL);

						task_attachment = new File(finTaskPath);
						if (!task_attachment.exists()) {
							task_attachment.mkdirs();
							filePathCreated.add(task_attachment
									.getAbsolutePath());
						}

						/****** End ---task doc file movement ****/

						List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = objAddEditRxTaskResultVO
								.getAddEditRxTaskDocVO();
						AddEditRxTaskDocVO addEditRxTaskDocVO = null;
						if (null != finTaskPath && null != tempRxPath
								&& index == 0) {
							File file2 = new File(finTaskPath)
									.getAbsoluteFile();
							File newFile2 = new File(tempRxPath)
									.getAbsoluteFile();
							if (newFile2.exists()) {
								FileUtils.copyDirectory(newFile2, file2, true);
							}
						}

						for (Iterator<AddEditRxTaskDocVO> iterator = lstAddEditRxTaskDocVO
								.iterator(); iterator.hasNext();) {

							addEditRxTaskDocVO = (AddEditRxTaskDocVO) iterator
									.next();

							if (addEditRxTaskDocVO.getStrDocData() != null
									&& !"".equals(addEditRxTaskDocVO
											.getStrDocData())
									&& addEditRxTaskDocVO.getStrDocData()
											.length() > 0) {

								String finPath_title = finTaskPath
										+ File.separator
										+ addEditRxTaskDocVO.getStrDocTitle();
								String sDBDomainPath = this.domainPath
										+ sDBDocPath;

								taskDocCallStmt = conn
										.prepareCall("{ call GETS_SD_NEWRECOM_PKG.TaskDocInsert_pr(?,?,?,?,?) }");
								taskDocCallStmt.setString(1, strUserID);
								taskDocCallStmt.setLong(2, longTaskId);
								if (addEditRxTaskDocVO.getStrDocTitle() != null
										&& !RMDCommonConstants.EMPTY_STRING
												.equals(addEditRxTaskDocVO
														.getStrDocTitle())) {
									taskDocCallStmt
											.setString(3, addEditRxTaskDocVO
													.getStrDocTitle());
								}
								int iDelete = 0;
								taskDocCallStmt.setInt(4, iDelete);

								taskDocCallStmt.setString(5, sDBDomainPath);
								taskDocCallStmt.executeUpdate();

								/********************** Added for task doc file movement ************************************/
								// Decode docData, create a file object and load
								// it in the final path
								// Insert the complete URL minus domain into the
								// RECOM_TASK table
								byte[] bArr = addEditRxTaskDocVO
										.getStrDocData().getBytes();
								byte[] bDecodedArr = Base64.decode(bArr);
								file = new File(finPath_title);
								FileUtils.writeByteArrayToFile(file,
										bDecodedArr);

							}
						}
					}
					/* Updating Task Document Ends */
					
					/*Updating translated docs - start*/
					String utfDocUpdate = "update GETS_SD.GETS_SD_RECOM_TASK_DOC set RECOM_DOC2RECOM_TASK=? where RECOM_DOC2RECOM_TASK=? AND LINK_LANG_TYPE IS NOT NULL";
					utfDocStmt = conn.prepareStatement(utfDocUpdate);
					utfDocStmt.setLong(1, longTaskId);
					utfDocStmt.setString(2, strOldTaskObjId);
					utfDocStmt.executeUpdate();
					/*Updating translated docs - End*/
				}
				
				
				
				/* Updating Task Details end */
				// Deleting the Objids from UTF Table which are deleted from RX
				// Task Screen
				String delObjIDUTF = "delete from gets_sd_recom_task_utf8 where link_recom_task =?";
				taskDelStmtUTF = conn.prepareStatement(delObjIDUTF);
				if (recomTaskObjIDlist.size() > 0) {
					for (int i = 0; i < recomTaskObjIDlist.size(); i++) {
						taskDelStmtUTF.setString(1, recomTaskObjIDlist.get(i));
						taskDelStmtUTF.executeQuery();
					}
				}
			}

			if (conn != null) {
				conn.commit();
			}

			// Commit successful, remove the temp directory
			if (null != tempRxPath) {
				filePathCreated.clear();
				filePathCreated.add(tempRxPath);
				deleteDirs(filePathCreated);
			}
		} catch (RMDDAOConnectionException ex) {
			conn.rollback();
			if (filePathCreated.size() > 0) {// Delete the file paths created if
												// it is a rollback
				boolean bDelete = deleteDirs(filePathCreated);
				LOG.error("Directories cannot be deleted :" + bDelete);
			}
			// Rename temp directory back to normal
			if (tempRxPath != null) {
				File fileTemp = new File(tempRxPath);
				File file = new File(thisRxPath);
				try {
					FileUtils.copyDirectory(fileTemp, file, true);
					// Delete directory and sub-directory
					File[] fileArr = fileTemp.listFiles();
					for (File file1 : fileArr) {
						FileUtils.deleteDirectory(file1);
					}
					FileUtils.deleteDirectory(fileTemp);
					LOG.debug("Directory Rollback successful");
				} catch (IOException ioe) {
					LOG.error("Unable to rollback directory :" + ioe);
				}
			}

			LOG.error(ex.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVEADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			conn.rollback();
			if (filePathCreated.size() > 0) {// Delete the file paths created if
												// it is a rollback
				boolean bDelete = deleteDirs(filePathCreated);
				LOG.error("Directories cannot be deleted :" + bDelete);
			}
			// Rename temp directory back to normal
			if (tempRxPath != null) {
				File fileTemp = new File(tempRxPath);
				File file = new File(thisRxPath);
				try {
					FileUtils.copyDirectory(fileTemp, file, true);
					// Delete directory and sub-directory
					File[] fileArr = fileTemp.listFiles();
					for (File file1 : fileArr) {
						if (file1.isDirectory()) {
							FileUtils.deleteDirectory(file1);
						} else {
							FileUtils.forceDelete(file1);
						}
					}
					FileUtils.deleteDirectory(fileTemp);
					LOG.debug("Directory Rollback successful");
				} catch (IOException ioe) {
					LOG.error("Unable to rollback directory :" + ioe);
				}
			}

			LOG.error(e.getMessage());

			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVEADVISORY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			try {

				if (conn != null) {
					conn.commit();
				}
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (modelCallStmt != null) {
					modelCallStmt.close();
				}
				if (taskCallStmt != null) {
					taskCallStmt.close();
				}
				if (taskDocCallStmt != null) {
					taskDocCallStmt.close();
				}
				if (taskStatement != null) {
					taskStatement.close();
				}
				if (taskQueryStatement != null) {
					taskQueryStatement.close();
				}
				if (taskDocDelStmt != null) {
					taskDocDelStmt.close();
				}
				if (taskDelStmt != null) {
					taskDelStmt.close();
				}
				if(repCodeDelStmt != null) {
					repCodeDelStmt.close();
				}
				if (configPrepStmt != null) {
					configPrepStmt.close();
				}
				if (editRecomPrepStmt != null) {
					editRecomPrepStmt.close();
				}
				if (editHistPrepStmt != null) {
					editHistPrepStmt.close();
				}
				if (configDelStmt != null) {
					configDelStmt.close();
				}
				if (queueStatement != null) {
					queueStatement.close();
				}
				if (taskResultSet != null) {
					taskResultSet.close();
				}
				if (taskQueryResultSet != null) {
					taskQueryResultSet.close();
				}

				if (taskQueryResultSetUTF != null) {
					taskQueryResultSetUTF.close();
				}
				if (taskQueryStatement != null) {
					taskQueryStatement.close();
				}

				if (queueResultSet != null) {
					queueResultSet.close();
				}
				if (statusQryPrepStmt != null) {
					statusQryPrepStmt.close();
				}
				if (recomCommentCallStmt != null) {
					recomCommentCallStmt.close();
				}
				if(utfDocStmt != null){
					utfDocStmt.close();
				}
				if (conn != null) {
					conn.close();
				}
				releaseSession(session);
			} catch (SQLException e) {
				throw e;
			}
		}
		LOG.debug("AddEditRxDAOImpl.saveEditRecomm() Ended");
		return strRXID;
	}

	/**
	 * Reads the file data from FS and converts into Base64 encoding to be
	 * decoded later
	 * 
	 * @param docPath
	 * @return
	 * @throws Exception
	 */
	private String readBase64FileData(String docPath, String fileName)
			throws Exception {

		String sBase64Data = null;
		String sFSPath = null;
		try {

			// Replace docPath with correct file system path
			sFSPath = docPath.replace(this.rxDataDBURL, this.rxDataUrl);
			LOG.debug("File System Path: " + sFSPath);
			File file = new File(sFSPath, fileName).getAbsoluteFile();
			byte[] bArr = FileUtils.readFileToByteArray(file);
			sBase64Data = new String(Base64.encode(bArr));

		} catch (IOException ioe) {
			LOG.error(sFSPath, ioe);
			throw ioe;
		}
		return sBase64Data;
	}

	/*
	 * @seecom.ge.trans.rmd.services.tools.rx.dao.intf.AddEditRxDAOIntf#
	 * recommStatusUpdate
	 * (com.ge.trans.rmd.services.tools.rx.service.valueobjects
	 * .AddEditRxServiceVO) @ This method is used to update status of the
	 * recommendation
	 */
	@Override
	@SuppressWarnings("deprecation")
	public String recommStatusUpdate(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		Connection conn = null;
		CallableStatement callableStatement = null;
		int intRecomId = 0;
		String strRecomUpdateStatus = RMDCommonConstants.FAIL;
		String rxStatus = null;
		String strRecomType = RMDCommonConstants.EMPTY_STRING;
		String strRecomStatus = RMDCommonConstants.EMPTY_STRING;
		String strLanguage = RMDCommonConstants.EMPTY_STRING;
		try {
			/**
			 * if Rx status is Approved and Rx type is not Standard then throw
			 * exception
			 * 
			 * if Rx status is not Approved and Rx type is Standard then throw
			 * exception
			 * */
			strRecomType = addeditRxServiceVO.getStrRxType();
			strRecomStatus = addeditRxServiceVO.getStrRxStatus();
			strLanguage = addeditRxServiceVO.getStrLanguage();
			if (strRecomStatus != null && strRecomType != null) {
				if (strRecomStatus.equals(RMDServiceConstants.APPROVED)
						&& !strRecomType.equals(RMDServiceConstants.STANDARD)) {
					String errorCode = RMDCommonUtility
							.getErrorCode(RMDServiceConstants.RECOM_TYPE_NOT_VALID);
					throw new RMDDAOException(errorCode, new String[] {},
							RMDCommonUtility.getMessage(errorCode,
									new String[] {}, strLanguage), null,
							RMDCommonConstants.MINOR_ERROR);
				}
				if (!strRecomStatus.equals(RMDServiceConstants.APPROVED)
						&& strRecomType.equals(RMDServiceConstants.STANDARD)) {
					String errorCode = RMDCommonUtility
							.getErrorCode(RMDServiceConstants.RECOM_STATUS_NOT_VALID);
					throw new RMDDAOException(errorCode, new String[] {},
							RMDCommonUtility.getMessage(errorCode,
									new String[] {}, strLanguage), null,
							RMDCommonConstants.MINOR_ERROR);
				}
			}

			LOG.debug("Start recommStatusUpdate()");
			hibernateSession = getHibernateSession(addeditRxServiceVO
					.getStrUserName());
			intRecomId = Integer.parseInt(addeditRxServiceVO.getStrRxId());
			rxStatus = addeditRxServiceVO.getStrRxStatus();
			if (!RMDCommonUtility.isNullOrEmpty(rxStatus)) {
				if (rxStatus.equalsIgnoreCase(RMDServiceConstants.DRAFT)) {
					rxStatus = RMDServiceConstants.INPROCESS;
				}
			}
			conn = hibernateSession.connection();
			callableStatement = conn
					.prepareCall("{ CALL GETS_SD_EDITRECOM_PKG.SDUPDATERECOMSTATUS_PR(?,?,?,?) }");
			callableStatement.setString(1, addeditRxServiceVO.getStrUserName());
			callableStatement.setString(2, rxStatus);
			callableStatement.setInt(3, intRecomId);
			callableStatement.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStatement.executeUpdate();

			strRecomUpdateStatus = EsapiUtil
					.stripXSSCharacters((String) (callableStatement
							.getString(4)));
			LOG.debug("End recommStatusUpdate()");
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_STATUS_UPDATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException objRMDDAOException) {
			throw objRMDDAOException;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			strRecomUpdateStatus = RMDCommonConstants.FAIL;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_STATUS_UPDATE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							addeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
			try {
				if (callableStatement != null) {
					callableStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqlEx) {
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_STATUS_UPDATE);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								addeditRxServiceVO.getStrLanguage()), sqlEx,
						RMDCommonConstants.MAJOR_ERROR);
			}
		}

		return strRecomUpdateStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ge.trans.rmd.services.tools.rx.dao.intf.AddEditRxDAOIntf#
	 * lockRecommendation
	 * (com.ge.trans.rmd.services.tools.rx.service.valueobjects
	 * .AddEditRxServiceVO) @ This method is used to lock the recommendation
	 */
	@Override
	public int lockRecommendation(AddEditRxServiceVO objAddeditRxServiceVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		int updatedRows = 0;
		try {
			hibernateSession = getHibernateSession(objAddeditRxServiceVO
					.getStrUserName());
			Codec ORACLE_CODEC = new OracleCodec();
			StringBuilder queryString = new StringBuilder();
			queryString
					.append("update GetKmRecomHVO as r set r.owner = :owner where ");
			queryString
					.append(" not exists (from GetKmRecomHVO r1 where r1.getKmRecomSeqId in (select h1.getKmRecomByLinkCurrentRecom.getKmRecomSeqId from GetKmRecomHistHVO h1 where h1.getKmRecomByLinkOriginalRecom.getKmRecomSeqId = :orignalRecommId) and r1.owner is not null) ");
			queryString
					.append(" and r.getKmRecomSeqId in (select h.getKmRecomByLinkCurrentRecom.getKmRecomSeqId from GetKmRecomHistHVO as h where h.getKmRecomByLinkOriginalRecom.getKmRecomSeqId = :orignalRecommId) ");
			Query query = hibernateSession.createQuery(queryString.toString());
			query.setParameter(
					RMDCommonConstants.OWNER,
					ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
							objAddeditRxServiceVO.getStrUserName()));
			query.setParameter(
					RMDCommonConstants.ORIGIONAL_RECOMID,
					Long.valueOf(ESAPI.encoder().encodeForSQL(ORACLE_CODEC,
							objAddeditRxServiceVO.getStrOriginalId())));
			updatedRows = query.executeUpdate();
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_LOCK_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_LOCK_RX);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return updatedRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.tools.rx.dao.intf.AddEditRxDAOIntf#getLockedBy
	 * (com
	 * .ge.trans.rmd.services.tools.rx.service.valueobjects.AddEditRxServiceVO)
	 * 
	 * @ This method is used to lock the recommendation
	 */
	@Override
	public String getLockedBy(AddEditRxServiceVO objAddeditRxServiceVO)
			throws RMDDAOException {
		Session hibernateSession = null;
		String strLockedBy = null;
		try {
			hibernateSession = getHibernateSession();
			StringBuilder queryString = new StringBuilder();
			queryString
					.append("select distinct r.owner from GetKmRecomHVO r where r.getKmRecomSeqId in ");
			queryString
					.append("(select h.getKmRecomByLinkCurrentRecom.getKmRecomSeqId from GetKmRecomHistHVO as h where h.getKmRecomByLinkOriginalRecom.getKmRecomSeqId = :orignalRecommId)");
			queryString.append(" and r.owner is not null");
			Query query = hibernateSession.createQuery(queryString.toString());
			query.setParameter(RMDCommonConstants.ORIGIONAL_RECOMID,
					Long.valueOf(objAddeditRxServiceVO.getStrOriginalId()));
			strLockedBy = (String) query.uniqueResult();
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCKED_BY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_LOCKED_BY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
		return strLockedBy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.ge.trans.rmd.services.tools.rx.dao.intf.AddEditRxDAOIntf#
	 * releseLockOnRecommendation
	 * (com.ge.trans.rmd.services.tools.rx.service.valueobjects
	 * .AddEditRxServiceVO) @ This method is used to lock the recommendation
	 */
	@Override
	public void releseLockOnRecommendation(
			AddEditRxServiceVO objAddeditRxServiceVO) throws RMDDAOException {
		Session hibernateSession = null;
		try {
			hibernateSession = getHibernateSession(objAddeditRxServiceVO
					.getStrUserName());
			StringBuilder queryString = new StringBuilder();
			queryString
					.append("UPDATE GETKMRECOMHVO R SET R.OWNER = :owner where ");
			queryString
					.append(" R.GETKMRECOMSEQID IN (SELECT H.GETKMRECOMBYLINKCURRENTRECOM.GETKMRECOMSEQID FROM GETKMRECOMHISTHVO H WHERE H.GETKMRECOMBYLINKORIGINALRECOM.GETKMRECOMSEQID = :orignalRecommId)");
			queryString.append(" AND R.OWNER = :lockedBy");
			Query query = hibernateSession.createQuery(queryString.toString());
			query.setParameter(RMDCommonConstants.OWNER,
					RMDCommonConstants.EMPTY_STRING);
			query.setParameter(RMDCommonConstants.LOCKEDBY,
					objAddeditRxServiceVO.getStrLockedBy());
			query.setParameter(RMDCommonConstants.ORIGIONAL_RECOMID,
					Long.valueOf(objAddeditRxServiceVO.getStrOriginalId()));
			query.executeUpdate();
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RELEASE_RX_LOCK);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RELEASE_RX_LOCK);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objAddeditRxServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(hibernateSession);
		}
	}

	/*
	 * This method used to generate the PDF document for selected recommendation
	 * details
	 * 
	 * @param AddEditRxServiceVO
	 * 
	 * @returns String
	 * 
	 * @throws RMDDAOException
	 */
	@Override
	public String generateSolutionDetailsPDF(
			AddEditRxServiceVO addeditRxServiceVO) throws RMDDAOException {

		LOG.debug("AddEditRxDAOImpl.generateSolutionDetailsPDF() Started");
		List<AddEditRxTaskResultVO> taskList = null;
		int iTaskSize = RMDCommonConstants.INT_CONST;
		String strFilePath = RMDCommonConstants.EMPTY_STRING;
		String taskNo = RMDCommonConstants.EMPTY_STRING;
		ArrayList<RxTaskVO> taskDetails = new ArrayList<RxTaskVO>();
		File file = null;
		try {
			String lookup = RMDCommonConstants.EMPTY_STRING;
			String baseURL = null;
			File baseDir = null;
			baseURL = System.getProperty("user.home");
			baseDir = new File(baseURL);
			fetchRecommDetails(addeditRxServiceVO);
			com.ge.trans.rmd.tools.common.valueobjects.RxVO rxData1 = new com.ge.trans.rmd.tools.common.valueobjects.RxVO();
			rxData1.setLanguage(RMDServiceConstants.PDF_REGULAR);
			rxData1.setUTFLang(false);
			rxData1.setRecomObjID(addeditRxServiceVO.getStrRxId());
			rxData1.setRecomTitle(StringEscapeUtils
					.unescapeHtml(addeditRxServiceVO.getStrTitle()));
			rxData1.setUrgency(addeditRxServiceVO.getStrSelUrgRepair());
			rxData1.setEstRepairTime(addeditRxServiceVO
					.getStrSelEstmTimeRepair());
			rxData1.setLocoImpact(addeditRxServiceVO.getStrSelAssetImp());
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.setTitleMid(RMDServiceConstants.STR_RX);
			rxData1.setVersion(addeditRxServiceVO.getStrRevNo());
			rxData1.setLastModifiedOn(addeditRxServiceVO.getLastUpdatedDate());
			rxData1.setCreatedOn(addeditRxServiceVO.getCreationDate());
			rxData1.setRecomType(addeditRxServiceVO.getStrRxType());
			List<ElementVO> arlElementVO = getLookupListValues(
					RMDServiceConstants.RX_STATE_LOOKBACK_DAYS,
					addeditRxServiceVO.getStrLanguage());
			if (RMDCommonUtility.isCollectionNotEmpty(arlElementVO)) {
				lookup = arlElementVO.get(0).getName();
			}
			rxData1.setRecomState(rxData1.calculateStatus(Integer
					.parseInt(lookup)));
			addeditRxServiceVO
					.setEditRxTaskResultList(new ArrayList<AddEditRxTaskResultVO>());
			getTaskDetails(addeditRxServiceVO);
			if (addeditRxServiceVO.getStrRxId().toString().length() > 0) {
				taskList = addeditRxServiceVO.getEditRxTaskResultList();
				iTaskSize = taskList.size();
				for (int index = 0; index < iTaskSize; index++) {

					AddEditRxTaskResultVO taskVo = (AddEditRxTaskResultVO) taskList
							.get(index);
					if (!taskNo.trim().equalsIgnoreCase(
							taskVo.getStrNO().trim())) {

						RxTaskVO taskData = new RxTaskVO();
						taskData.setTaskID(taskVo.getStrNO());
						taskData.setDescription(taskVo.getStrTaskDeails());
						taskData.setLowerSpec(taskVo.getStrLSL());
						taskData.setUpperSpec(taskVo.getStrUSL());
						taskData.setTarget(taskVo.getStrTarget());
						taskDetails.add(taskData);

					}
				}
			}

			rxData1.setTaskDetails(taskDetails);
			RxPdfGenerator rxPdfGenerator;
			rxPdfGenerator = new RxPdfGenerator(
					Logger.getLogger(AddEditRxDAOImpl.class));
			String baseRxFilePath = baseDir.getAbsolutePath() + File.separator
					+ "report" + File.separator + "recommendations"
					+ File.separator;
			String baseRxFileName = addeditRxServiceVO.getStrRxId();
			file = rxPdfGenerator.createPdfFile(rxData1, baseRxFilePath,
					baseRxFileName);
			if (file.exists())
				strFilePath = file.getAbsolutePath();

		} catch (Exception rmdEx) {
			LOG.debug(rmdEx.getMessage());
		}
		return strFilePath;
	}

	/**
	 * This method used to fetch active rules associated with Rx from database
	 * 
	 * @param String
	 * @return List<ElementVO>
	 * @throws RMDDAOException
	 */
	@Override
	public List<ElementVO> fetchActiveRuleswithRX(String recomId) {
		LOG.debug("AddEditRxDAOImpl.fetchActiveRuleswithRX() Started");
		List<ElementVO> activeRuleList = null;
		Session rulesSession = getHibernateSession();
		StringBuilder ruleBuilder = new StringBuilder();
		ruleBuilder
				.append("SELECT finrule.objid,finrule.rule_title FROM gets_tool_dpd_ruledef ruledef, gets_tool_dpd_finrul finrule, GETS_TOOL_DPD_RULHIS rulehis ");
		ruleBuilder
				.append("where ruledef.RULEDEF2FINRUL = finrule.objid AND rulehis.ACTIVE = 1 AND rulehis.RULHIS2FINRUL = finrule.objid and ruledef.RULEDEF2RECOM=:recomId");
		try {

			Query ruleQuery = rulesSession.createSQLQuery(ruleBuilder
					.toString());
			ruleQuery.setParameter(RMDServiceConstants.RECOM_ID, recomId);
			List<Object> activeRulesList = ruleQuery.list();

			if (!activeRulesList.isEmpty()) {
				activeRuleList = new ArrayList<ElementVO>();
				for (final Iterator<Object> iter = activeRulesList.iterator(); iter
						.hasNext();) {
					final Object[] lookupRecord = (Object[]) iter.next();
					activeRuleList.add(new ElementVO(
							lookupRecord[0].toString(), lookupRecord[0]
									.toString()
									+ "===>>"
									+ lookupRecord[1].toString()));
				}
			}

		} catch (Exception e) {
			LOG.error("fetchActiveRuleswithRX() Error Occured---"
					+ e.getMessage());
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.BO_EXCEPTION_PDF_GENERATION);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							"EN"), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(rulesSession);
		}
		LOG.debug("AddEditRxDAOImpl.fetchActiveRuleswithRX() Ended");
		return activeRuleList;
	}

	/**
	 * This method used to fetch document
	 * 
	 * @param String
	 *            path
	 * @param String
	 *            filename
	 * @return ObjectOutputStream
	 */
	@Override
	public OutputStream downloadAttachment(
			AddEditRxServiceVO addeditRxServiceVO, String filepath,
			String fileName) throws RMDDAOException {
		LOG.debug("AddEditRxDAOImpl.fetchAttachment() Started");
		String strFilePath = RMDCommonConstants.EMPTY_STRING;
		OutputStream attachmentStream = null;
		InputStream is = null;

		strFilePath = rxDataUrl + File.separator + filepath + File.separator
				+ fileName;

		if (strFilePath != null && strFilePath.length() > 0) {
			File existingFile = null;
			existingFile = new File(strFilePath);
			try {
				is = new FileInputStream(existingFile);
				attachmentStream = new ByteArrayOutputStream();

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					attachmentStream.write(bytes, 0, read);
				}

				attachmentStream.flush();
			} catch (Exception e) {
				LOG.error(
						"Unexpected Error occured in downloadAttachment method in AddEditRxDAOImpl",
						e);
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.ERROR_LAST_DOWNLOAD_STATUS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								"EN"), e, RMDCommonConstants.MAJOR_ERROR);
			} finally {
				if (is != null) {
					try {
						is.close();
						is = null;
					} catch (IOException e) {
						is = null;
						LOG.debug(e.getMessage(), e);
					}

				}
			}
		}

		LOG.debug("AddEditRxDAOImpl.fetchAttachment() Ended");
		return attachmentStream;
	}

	/*
	 * This method used to generate the PDF document for selected recommendation
	 * details
	 * 
	 * @param AddEditRxServiceVO
	 * 
	 * @returns String
	 * 
	 * @throws RMDDAOException
	 */
	@Override
	public String previewRxPdf(AddEditRxServiceVO addeditRxServiceVO)
			throws RMDDAOException {

		LOG.debug("AddEditRxDAOImpl.previewRxPdf() Started");
		List<AddEditRxTaskResultVO> taskList = null;
		int iTaskSize = RMDCommonConstants.INT_CONST;
		String strFilePath = RMDCommonConstants.EMPTY_STRING;
		String taskNo = RMDCommonConstants.EMPTY_STRING;
		ArrayList<RxTaskVO> taskDetails = new ArrayList<RxTaskVO>();
		File file = null;
		try {
			String lookup = RMDCommonConstants.EMPTY_STRING;
			String baseURL = null;
			File baseDir = null;
			baseURL = System.getProperty("user.home");
			baseDir = new File(baseURL);
			com.ge.trans.rmd.tools.common.valueobjects.RxVO rxData1 = new com.ge.trans.rmd.tools.common.valueobjects.RxVO();
			rxData1.setLanguage(RMDServiceConstants.PDF_REGULAR);
			rxData1.setUTFLang(false);
			rxData1.setRecomObjID(addeditRxServiceVO.getStrRxId());
			rxData1.setRecomTitle(StringEscapeUtils
					.unescapeHtml(addeditRxServiceVO.getStrTitle()));
			rxData1.setUrgency(addeditRxServiceVO.getStrSelUrgRepair());
			rxData1.setEstRepairTime(addeditRxServiceVO
					.getStrSelEstmTimeRepair());
			rxData1.setLocoImpact(addeditRxServiceVO.getStrSelAssetImp());
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.addHeaderTask("  ");
			rxData1.setTitleMid(RMDServiceConstants.STR_RX);
			rxData1.setVersion(addeditRxServiceVO.getStrRevNo());
			rxData1.setLastModifiedOn(addeditRxServiceVO.getLastUpdatedDate());
			rxData1.setCreatedOn(addeditRxServiceVO.getCreationDate());
			rxData1.setRecomType(addeditRxServiceVO.getStrRxType());
			List<ElementVO> arlElementVO = getLookupListValues(
					RMDServiceConstants.RX_STATE_LOOKBACK_DAYS,
					addeditRxServiceVO.getStrLanguage());
			if (RMDCommonUtility.isCollectionNotEmpty(arlElementVO)) {
				lookup = arlElementVO.get(0).getName();
			}
			rxData1.setRecomState(rxData1.calculateStatus(Integer
					.parseInt(lookup)));

			taskList = addeditRxServiceVO.getLstEditTaskRxResultVO();
			iTaskSize = taskList.size();
			for (int index = 0; index < iTaskSize; index++) {

				AddEditRxTaskResultVO taskVo = (AddEditRxTaskResultVO) taskList
						.get(index);
				if (!taskNo.trim().equalsIgnoreCase(taskVo.getStrNO().trim())) {

					RxTaskVO taskData = new RxTaskVO();
					taskData.setTaskID(taskVo.getStrNO());
					taskData.setDescription(taskVo.getStrTaskDeails());
					taskData.setLowerSpec(taskVo.getStrLSL());
					taskData.setUpperSpec(taskVo.getStrUSL());
					taskData.setTarget(taskVo.getStrTarget());
					taskDetails.add(taskData);

				}
			}

			rxData1.setTaskDetails(taskDetails);
			RxPdfGenerator rxPdfGenerator;
			rxPdfGenerator = new RxPdfGenerator(
					Logger.getLogger(AddEditRxDAOImpl.class));
			String baseRxFilePath = baseDir.getAbsolutePath() + File.separator
					+ "report" + File.separator + "recommendations"
					+ File.separator;
			String baseRxFileName = addeditRxServiceVO.getStrRxId();
			file = rxPdfGenerator.createPdfFile(rxData1, baseRxFilePath,
					baseRxFileName);
			if (file.exists())
				strFilePath = file.getAbsolutePath();

		} catch (Exception rmdEx) {
			LOG.debug(rmdEx.getMessage());
		}
		return strFilePath;
	}

	private void insertRxPlotData(Connection conn,
			AddEditRxPlotVO addEditRxPlotVO, Long recomId, String userId) throws RMDDAOException {

		PreparedStatement prepStmt = null;

		String query = "INSERT INTO GETS_SD_RECOM_PLOT(OBJID,TITLE,INDEPENDENT_AXIS_LABEL,LEFT_DEPENDENT_AXIS_LABEL,RIGHT_DEPENDENT_AXIS_LABEL,DATASET_1_PARAMETER_NAME,DATASET_1_LABEL,DATASET_1_AXIS,DATASET_2_PARAMETER_NAME,DATASET_2_LABEL,DATASET_2_AXIS,DATASET_3_PARAMETER_NAME,DATASET_3_LABEL,DATASET_3_AXIS,DATASET_4_PARAMETER_NAME,DATASET_4_LABEL,DATASET_4_AXIS,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,?,SYSDATE,?)";
		try {

			prepStmt = conn.prepareStatement(query);
			prepStmt.setLong(1,recomId);
			prepStmt.setString(2, addEditRxPlotVO.getStrPlotTitle());
			prepStmt.setString(3, addEditRxPlotVO.getStrIndependentAxislabel());//X-Axis
			prepStmt.setString(4,
					addEditRxPlotVO.getStrLeftDependentAxislabel());//Y-Axis left
			prepStmt.setString(5,
					addEditRxPlotVO.getStrRightDependentAxislabel());//Y-Axis right
			
			String dataSetOneName = addEditRxPlotVO.getStrDataSetOneName();
			
			prepStmt.setString(6, dataSetOneName);
			String dataSetOneLabel = null;
			if(null == addEditRxPlotVO.getStrDataSetOneLabel()){
				dataSetOneLabel = dataSetOneName;
			}else{
				dataSetOneLabel = addEditRxPlotVO.getStrDataSetOneLabel();
			}
			prepStmt.setString(7, dataSetOneLabel);
			String axisValue = null;
			if (null == addEditRxPlotVO.getStrDataSetOneAxis()
					|| addEditRxPlotVO.getStrDataSetOneAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetOneAxis();
			}
			prepStmt.setString(8, axisValue);
			String dataSetTwoName = addEditRxPlotVO.getStrDataSetTwoName();
			prepStmt.setString(9, dataSetTwoName);
			String dataSetTwoLabel = null;
			if(null == addEditRxPlotVO.getStrDataSetTwoLabel()){
				dataSetTwoLabel = dataSetTwoName;
			}else{
				dataSetTwoLabel = addEditRxPlotVO.getStrDataSetTwoLabel();
			}
			prepStmt.setString(10, dataSetTwoLabel);
			if (null == addEditRxPlotVO.getStrDataSetTwoAxis()
					|| addEditRxPlotVO.getStrDataSetTwoAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetTwoAxis();
			}
			prepStmt.setString(11, axisValue);
			String dataSetThreeName = addEditRxPlotVO.getStrDataSetThreeName();
			prepStmt.setString(12, dataSetThreeName);
			String dataSetThreeLabel = null;
			if(null == addEditRxPlotVO.getStrDataSetThreeLabel()){
				dataSetThreeLabel = dataSetThreeName;
			}else{
				dataSetThreeLabel = addEditRxPlotVO.getStrDataSetThreeLabel();
			}
			prepStmt.setString(13, dataSetThreeLabel);
			if (null == addEditRxPlotVO.getStrDataSetThreeAxis()
					|| addEditRxPlotVO.getStrDataSetThreeAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetThreeAxis();
			}
			prepStmt.setString(14, axisValue); 
			
			String dataSetFourName = addEditRxPlotVO.getStrDataSetFourName();
			prepStmt.setString(15, dataSetFourName);
			String dataSetFourLabel = null;
			if(null == addEditRxPlotVO.getStrDataSetFourLabel()){
				dataSetFourLabel = dataSetFourName;
			}else{
				dataSetFourLabel = addEditRxPlotVO.getStrDataSetFourLabel();
			}
			prepStmt.setString(16, dataSetFourLabel);
			if (null == addEditRxPlotVO.getStrDataSetFourAxis()
					|| addEditRxPlotVO.getStrDataSetFourAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetFourAxis();
			}
			prepStmt.setString(17, axisValue);
			prepStmt.setString(18, userId);
			prepStmt.setString(19, userId);
			prepStmt.executeUpdate();

		} catch (Exception ex) {
			LOG.debug(ex.getMessage());
			throw new RMDDAOException();
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	private void updateRxPlotData(Connection conn,
			AddEditRxPlotVO addEditRxPlotVO, Long recomId, String userId) throws RMDDAOException{

		PreparedStatement prepStmt = null;

		String query = "UPDATE GETS_SD_RECOM_PLOT SET TITLE=?,INDEPENDENT_AXIS_LABEL=?,LEFT_DEPENDENT_AXIS_LABEL=?,RIGHT_DEPENDENT_AXIS_LABEL=?,DATASET_1_PARAMETER_NAME=?,DATASET_1_LABEL=?,DATASET_1_AXIS=?,"
				+ " DATASET_2_PARAMETER_NAME=?,DATASET_2_LABEL=?,DATASET_2_AXIS=?,DATASET_3_PARAMETER_NAME=?,DATASET_3_LABEL=?,DATASET_3_AXIS=?,DATASET_4_PARAMETER_NAME=?,DATASET_4_LABEL=?,DATASET_4_AXIS=?,LAST_UPDATED_DATE=SYSDATE,LAST_UPDATED_BY = ? WHERE OBJID = ?";
		try {

			prepStmt = conn.prepareStatement(query);

			prepStmt.setString(1, addEditRxPlotVO.getStrPlotTitle());
			prepStmt.setString(2, addEditRxPlotVO.getStrIndependentAxislabel());
			prepStmt.setString(3,
					addEditRxPlotVO.getStrLeftDependentAxislabel());
			prepStmt.setString(4,
					addEditRxPlotVO.getStrRightDependentAxislabel());
			String dataSetOneName = addEditRxPlotVO.getStrDataSetOneName();
			prepStmt.setString(5, dataSetOneName);
			String dataSetOneLabel = null;
			if (null == addEditRxPlotVO.getStrDataSetOneLabel()) {
				dataSetOneLabel = dataSetOneName;
			} else {
				dataSetOneLabel = addEditRxPlotVO.getStrDataSetOneLabel();
			}
			prepStmt.setString(6, dataSetOneLabel);
			String axisValue = null;
			if (null == addEditRxPlotVO.getStrDataSetOneAxis()
					|| addEditRxPlotVO.getStrDataSetOneAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetOneAxis();
			}
			prepStmt.setString(7, axisValue);
			String dataSetTwoName = addEditRxPlotVO.getStrDataSetTwoName();
			prepStmt.setString(8, dataSetTwoName);
			String dataSetTwoLabel = null;
			if (null == addEditRxPlotVO.getStrDataSetTwoLabel()) {
				dataSetTwoLabel = dataSetTwoName;
			} else {
				dataSetTwoLabel = addEditRxPlotVO.getStrDataSetTwoLabel();
			}
			prepStmt.setString(9, dataSetTwoLabel);
			if (null == addEditRxPlotVO.getStrDataSetTwoAxis()
					|| addEditRxPlotVO.getStrDataSetTwoAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetTwoAxis();
			}
			prepStmt.setString(10, axisValue);
			String dataSetThreeName = addEditRxPlotVO.getStrDataSetThreeName();
			prepStmt.setString(11, dataSetThreeName);
			String dataSetThreeLabel = null;
			if (null == addEditRxPlotVO.getStrDataSetThreeLabel()) {
				dataSetThreeLabel = dataSetThreeName;
			} else {
				dataSetThreeLabel = addEditRxPlotVO.getStrDataSetThreeLabel();
			}
			prepStmt.setString(12, dataSetThreeLabel);
			if (null == addEditRxPlotVO.getStrDataSetThreeAxis()
					|| addEditRxPlotVO.getStrDataSetThreeAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetThreeAxis();
			}
			prepStmt.setString(13, axisValue);
			
			String dataSetFourName = addEditRxPlotVO.getStrDataSetFourName();
			prepStmt.setString(14, dataSetFourName);
			String dataSetFourLabel = null;
			if (null == addEditRxPlotVO.getStrDataSetFourLabel()) {
				dataSetFourLabel = dataSetFourName;
			} else {
				dataSetFourLabel = addEditRxPlotVO.getStrDataSetFourLabel();
			}
			prepStmt.setString(15, dataSetFourLabel);
			if (null == addEditRxPlotVO.getStrDataSetFourAxis()
					|| addEditRxPlotVO.getStrDataSetFourAxis().isEmpty()) {
				axisValue = "L";
			} else {
				axisValue = addEditRxPlotVO.getStrDataSetFourAxis();
			}
			prepStmt.setString(16, axisValue);
			
			prepStmt.setString(17, userId);
			prepStmt.setLong(18, recomId); 
			prepStmt.executeUpdate();

		} catch (Exception ex) {
			LOG.debug(ex.getMessage());
			throw new RMDDAOException();
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	private String getRxPlotNextVal(Connection conn) throws RMDDAOException {
		String rxPlotObjid = null;
		ResultSet rxPlotResultSet = null;
		Statement rxPlotIdStmt = null;
		try {
			rxPlotIdStmt = conn.createStatement();
			rxPlotResultSet = rxPlotIdStmt
					.executeQuery("select GETS_SD.GETS_SD_RECOM_PLOT_SEQ.NEXTVAL from dual");
			if (rxPlotResultSet.next()) {
				rxPlotObjid = rxPlotResultSet.getString(1);
			}
		} catch (Exception ex) {
			LOG.debug(ex.getMessage());
			throw new RMDDAOException();
		} finally {
			try {
				if (rxPlotResultSet != null) {
					rxPlotResultSet.close();
				}
				if (rxPlotIdStmt != null) {
					rxPlotIdStmt.close();
				}
			} catch (SQLException e) {
			}
		}
		return rxPlotObjid;
	}
	
	private void insertRxSubSystem(Connection conn, List<String> subSystemList,
			Long recomId, String userId, String strFromPage) throws RMDDAOException {

		PreparedStatement subSystemPrepStmt = null;
		String strSubsystemArray[];
		String query = "INSERT INTO GETS_SD.GETS_SD_RECOM_SUBSYS (OBJID,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,RECOM_SUBSYS2RECOM,SUBSYSTEM,PRIMARY_SUBSYS_FLAG) VALUES(gets_sd_recom_subsys_seq.nextval,sysdate,?,sysdate,?,?,?,?)";
		String deleteRxSubSystem = "DELETE FROM gets_sd_recom_subsys WHERE recom_subsys2recom = ?";
		try {
	
			if (RMDCommonUtility.isCollectionNotEmpty(subSystemList)) {

				if (RMDServiceConstants.EDITRECOMM.equals(strFromPage)
						&& recomId > 0) {
					subSystemPrepStmt = conn
							.prepareStatement(deleteRxSubSystem);
					subSystemPrepStmt.setLong(1, recomId);
					subSystemPrepStmt.executeUpdate();
					subSystemPrepStmt = null;
				}
				for (String strSubsystems : subSystemList) {
					strSubsystemArray = RMDCommonUtility.splitString(
							strSubsystems,
							RMDCommonConstants.PIPELINE_CHARACTER);
					String linkSubsystem = strSubsystemArray[RMDCommonConstants.INT_CONST_ZERO];
					String linkSubsystemFlag = strSubsystemArray[RMDCommonConstants.INT_CONST_TWO];

					subSystemPrepStmt = conn.prepareStatement(query);
					subSystemPrepStmt.setString(1, userId);
					subSystemPrepStmt.setString(2, userId);
					subSystemPrepStmt.setLong(3, recomId);
					subSystemPrepStmt.setString(4, linkSubsystem);
					subSystemPrepStmt.setString(5, linkSubsystemFlag);
					subSystemPrepStmt.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.debug(ex.getMessage());
			throw new RMDDAOException();
		} finally {
			if (subSystemPrepStmt != null) {
				try {
					subSystemPrepStmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
