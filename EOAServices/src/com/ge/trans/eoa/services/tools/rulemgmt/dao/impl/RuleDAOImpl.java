/**
 * ============================================================
 * File : RuleDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * Modified on : February 14, 2012
 * History : New methods added
 * getCustomerList, getModelList, getFleetList, getConfigurationList, getAssetList
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rulemgmt.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.valueobjects.UnitOfMeasureVO;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ClearingLogicRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ComplexRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefConfigServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefCustomerServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefModelServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleDefinitionServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleHistoryServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleClauseServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SimpleRuleServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.AlertRuleParmVO;
import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.ParmMetricVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.common.valueobjects.RuleParmVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstFleetHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetAsstMasterBomHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetCmCustomerHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmDpdRulhisHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetKmFiltersLinkHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings({ "serial", "unchecked" })
public class RuleDAOImpl extends BaseDAO implements RuleDAOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleDAOImpl.class);

    /*
     * This Method is used for fetch the Data Pack element details
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * getInitialLoadForAddRule(java.lang.String, java.lang.String)
     */
    @Override
    @SuppressWarnings("rawtypes")
    // @Cacheable(value = "parameterCache", key = "#family")
	public List getInitialLoadForAddRule(ParameterRequestServiceVO objParameterRequestServiceVO)
			throws RMDDAOException {

		List arlResults = new ArrayList();

		Session session = null;
		ParameterServiceVO parameterServiceVO = null;
		List<Object> lookupListEDP = null;
		List<Object> lookupListType = null;
		LOG.debug("Enter into getInitialLoadForAddRule in RuleDAOImpl method");
		StringBuilder queryString = new StringBuilder(
				RMDCommonConstants.EMPTY_STRING);
		StringBuilder sqlQuery = new StringBuilder(
				RMDCommonConstants.EMPTY_STRING);
		StringBuilder sqlQueryANM = new StringBuilder(
				RMDCommonConstants.EMPTY_STRING);
		Query queryParameters = null;
		Map<String,UnitOfMeasureVO> mpUom = null;
		Query queryAnomParameters = null;
		Query queryEDPParameters = null;
		String strParam = null;		
		List<Object> lookupList = null;
		StringBuilder sqlQueryVPM = new StringBuilder();
		Query queryVPMParameters = null;
		List<Object> lstVirtualResults = null;
		try {
            String family=objParameterRequestServiceVO.getFamily();
            String uom=objParameterRequestServiceVO.getUom();
            String isGERuleAUthor=objParameterRequestServiceVO.getIsGERuleAUthor();
            String customer=objParameterRequestServiceVO.getCustomer();
            String ruleType=objParameterRequestServiceVO.getRuleType();
			session = getHibernateSession();
			mpUom=convertSourceToTarget(uom);			
			// For fetching the Normal & Virtual Parameter
			
			if (!RMDCommonUtility.isNullOrEmpty(family)) {
				
					
					if (RMDServiceConstants.ALERT.equalsIgnoreCase(ruleType)) {
						queryString=new StringBuilder(
								RMDCommonConstants.EMPTY_STRING);
						queryString
								.append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS1_SELECT).append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS1_FROM);
						
						if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
							queryString
							.append(",GETS_TOOL_CE_PARM_INFO_EXTAPP EXT");
						}
						queryString
						.append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS1_WHERE);
						if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
							queryString
							.append(" AND INFO.OBJID               = EXT.EXTAPP2CE_PARM_INFO AND EXT.EXTAPP2BUS_ORG  = ORG.OBJID ");
						}
						if (!RMDCommonUtility.isNullOrEmpty(customer)
								&& RMDServiceConstants.ALERT
										.equalsIgnoreCase(ruleType)) {
							queryString
									.append(" AND ORG.ORG_ID     in (:customer)");
						} 
						queryString
								.append(" group by CLMNAM.OBJID,nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME),CLMNAM.COLUMN_NAME,CLMNAM.PARM_TYPE UNION ");
						queryString
								.append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS2_SELECT)
								.append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS2_FROM);
						if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
							queryString
							.append(",GETS_TOOL_CE_PARM_INFO_EXTAPP EXT");
						}
						
						queryString
						.append(RMDCommonConstants.FETCH_ALERT_SDP_PARAMS2_WHERE);
						if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
							queryString
							.append(" AND INFO.OBJID               = EXT.EXTAPP2CE_PARM_INFO AND EXT.EXTAPP2BUS_ORG  = ORG.OBJID ");
						}
						
						if (!RMDCommonUtility.isNullOrEmpty(customer)
								&& RMDServiceConstants.ALERT
										.equalsIgnoreCase(ruleType)) {
							queryString
									.append(" AND ORG.ORG_ID     in (:customer)");
						}
						queryString.append(" ORDER BY COLUMN_NAME ");
						queryParameters = session.createSQLQuery(queryString
								.toString());
						if (!RMDCommonUtility.isNullOrEmpty(customer)
								&& RMDServiceConstants.ALERT
										.equalsIgnoreCase(ruleType)) {
							String[] tmpStrCustomer = customer
									.split(RMDCommonConstants.COMMMA_SEPARATOR);
							queryParameters.setParameterList(
									RMDCommonConstants.CUSTOMER, tmpStrCustomer);
						}
					}else{
					 queryParameters = session
								.createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_SDP_PARAMS
										.toString());
					}
					queryParameters.setParameter(
							RMDCommonConstants.FAMILY_INITIAL_LOADER, family);
				
			} else {
				
				if(RMDServiceConstants.ALERT.equalsIgnoreCase(ruleType)){
					queryString
					.append(RMDServiceConstants.QueryConstants.FETCH_ALERT_PARAMETERS);
					if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
						queryString
						.append(",GETS_TOOL_CE_PARM_INFO_EXTAPP EXT");
					}
					queryString
					.append(RMDCommonConstants.FETCH_ALERT_PARAMETERS_WHERE);
					if(!RMDCommonUtility.isNullOrEmpty(customer)||RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)){							
						queryString
						.append(" AND INFO.OBJID               = EXT.EXTAPP2CE_PARM_INFO AND EXT.EXTAPP2BUS_ORG  = ORG.OBJID ");
					}
				}else{
				queryString
						.append(RMDServiceConstants.QueryConstants.FETCH_PARAMETERS);
				}
				queryString.append(" ORDER BY COLNAME.COLUMN_NAME");
				queryParameters = session
						.createSQLQuery(queryString.toString());
			}
			queryParameters.setFetchSize(500);
			lookupList = queryParameters.list();
			// Iterating General Param list
			if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {
				arlResults = new ArrayList();

				for (final Iterator<Object> iter = lookupList.iterator(); iter
						.hasNext();) {
					parameterServiceVO = new ParameterServiceVO();
					final Object[] lookupRecord = (Object[]) iter.next();

					strParam = lookupRecord[1].toString();
					String parmType=RMDCommonConstants.EMPTY_STRING;
					parmType=RMDCommonUtility.convertObjectToString(lookupRecord[3]);
					String sourceUom=RMDCommonUtility.convertObjectToString(lookupRecord[4]);
					// if (isAC4400) {
					// if the Param starts with SDP then the param will be SDP
					// and its type will be marked as SDP
					if (!RMDCommonUtility.isNullOrEmpty(strParam)) {
						if (strParam.startsWith(RMDServiceConstants.SDP)) {
							parameterServiceVO
									.setColumnType(RMDServiceConstants.SDP);
							String strSDPParam[] = strParam
									.split(RMDServiceConstants.SDP);
							parameterServiceVO.setParameterName(strSDPParam[1]);
							parameterServiceVO
									.setParameterNameID(lookupRecord[0]
											.toString());
							parameterServiceVO.setParamDBName(RMDCommonUtility
									.convertObjectToString(lookupRecord[2]));
						} else {
							
							
							if(!parmType.equalsIgnoreCase(RMDServiceConstants.ATS)&&!parmType.equalsIgnoreCase(RMDServiceConstants.GEOLOCATION)){
								parameterServiceVO
									.setParameterNameID(lookupRecord[0]
											.toString());
								parameterServiceVO.setParamDBName(RMDCommonUtility
									.convertObjectToString(lookupRecord[2]));
								
								if (parmType.equalsIgnoreCase("Standard")) {
										parameterServiceVO
												.setColumnType(RMDServiceConstants.SDP);
								} else {
										parameterServiceVO
												.setColumnType(RMDServiceConstants.ENG);
								}

								
							
							if (null != mpUom
										&& !mpUom.isEmpty()
										&& null != sourceUom
										&& !RMDCommonConstants.EMPTY_STRING
												.equalsIgnoreCase(sourceUom)) {
									UnitOfMeasureVO objMeasureVO = mpUom
											.get(sourceUom);
									if(null!=objMeasureVO){
									if (null != uom
											&& !RMDCommonConstants.EMPTY_STRING
													.equalsIgnoreCase(uom)
											&& !RMDCommonConstants.US
													.equalsIgnoreCase(uom)&&RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objMeasureVO.getIsConversionRequired())) {
										parameterServiceVO
												.setUomAbbr(objMeasureVO
														.getTargetAbbr());
									} else {
										parameterServiceVO
												.setUomAbbr(objMeasureVO
														.getSourceAbbr());
									}
									}
								}
							parameterServiceVO.setParameterName(strParam);
							}
						}
					}
					/*
					 * } else { if (!RMDCommonUtility.isNullOrEmpty(strParam)) {
					 * parameterServiceVO.setParameterName(strParam); if
					 * (strParam .startsWith(RMDServiceConstants.v_)||strParam
					 * .startsWith(RMDServiceConstants.CAPS_V_)) {
					 * parameterServiceVO
					 * .setColumnType(RMDServiceConstants.VPM); } else {
					 * parameterServiceVO
					 * .setColumnType(RMDServiceConstants.ENG); } } }
					 */
					arlResults.add(parameterServiceVO);
				}
			}
				// Querying to collect Virtual params
				if(!RMDCommonConstants.N_LETTER_UPPER.equalsIgnoreCase(isGERuleAUthor)&&!RMDServiceConstants.ALERT.equalsIgnoreCase(ruleType)){
				sqlQueryVPM
						.append(RMDServiceConstants.QueryConstants.FETCH_VIRTUAL_PARAMS);
				if (!RMDCommonUtility.isNullOrEmpty(family)) {
					sqlQueryVPM
							.append(" AND LOWER(VIRTUAL.FAMILY_CD)=LOWER( :Family)");
					queryVPMParameters = session.createSQLQuery(sqlQueryVPM
							.toString());
					queryVPMParameters.setParameter(RMDServiceConstants.FAMILY,
							family);
				} else {
					queryVPMParameters = session.createSQLQuery(sqlQueryVPM
							.toString());
				}
				lstVirtualResults = queryVPMParameters.list();
				if (RMDCommonUtility.isCollectionNotEmpty(lstVirtualResults)) {
					for (final Iterator<Object> virtualFields = lstVirtualResults
							.iterator(); virtualFields.hasNext();) {
						parameterServiceVO = new ParameterServiceVO();
						final Object[] virtualRecords = (Object[]) virtualFields
								.next();
						parameterServiceVO.setParameterNameID(virtualRecords[0]
								.toString());
						parameterServiceVO.setParameterName(virtualRecords[1]
								.toString());
						parameterServiceVO
								.setColumnType(RMDServiceConstants.VPM);
						parameterServiceVO.setVirtualId(virtualRecords[2]
								.toString());
						arlResults.add(parameterServiceVO);
					}
				}

				// for fetching the anomalies
				
				// Query to Fetch Anomaly Parameters
				sqlQueryANM.append("SELECT OBJID, DISPLAY_PARM_NAME || ");
				sqlQueryANM
						.append("DECODE(NVL(PARM_NUMBER,'XX'),'XX',' ',' - '||PARM_NUMBER) PARM");
				sqlQueryANM
						.append(" FROM GETS_TOOL_ANOM_ADMIN WHERE AXIS = 'Y' AND ACTIVE = 1 AND ");
				sqlQueryANM
						.append("ANOMALY_INDICATOR= 'Y' ORDER BY ANOMALY_TITLE");
				if (RMDCommonUtility.isNullOrEmpty(family)) {
					queryAnomParameters = session.createSQLQuery(sqlQueryANM
							.toString());
					lookupListType = queryAnomParameters.list();
					} else if ((RMDServiceConstants.FAMILY_ACCCA
							.equalsIgnoreCase(family))
							|| (RMDServiceConstants.FAMILY_DCCCA
									.equalsIgnoreCase(family))
							|| (RMDServiceConstants.FAMILY_CCA
									.equalsIgnoreCase(family))
							|| (RMDCommonConstants.AC4400
									.equalsIgnoreCase(family))
							|| (RMDCommonConstants.DCLOCO
									.equalsIgnoreCase(family))
									|| (RMDCommonConstants.OIL
											.equalsIgnoreCase(family))) {
					// For Anomalies
					queryAnomParameters = session.createSQLQuery(sqlQueryANM
							.toString());
					lookupListType = queryAnomParameters.list();

				}
				// Iterating Anomaly Param list
				if (RMDCommonUtility.isCollectionNotEmpty(lookupListType)) {
					for (final Iterator<Object> iter1 = lookupListType
							.iterator(); iter1.hasNext();) {
						parameterServiceVO = new ParameterServiceVO();
						final Object[] lookupRecord1 = (Object[]) iter1.next();

						parameterServiceVO.setParameterNameID(lookupRecord1[0]
								.toString());
						parameterServiceVO.setParameterName(lookupRecord1[1]
								.toString());
						parameterServiceVO
								.setColumnType(RMDServiceConstants.ANM);
						arlResults.add(parameterServiceVO);
					}
				}

				// for fetching the EDP Parameter
				sqlQuery.append(RMDServiceConstants.QueryConstants.FETCH_EDP_PARAMETERS);				
				if (RMDCommonUtility.isNullOrEmpty(family)) {
					sqlQuery.append(" ORDER BY 1 ");
					queryEDPParameters = session.createSQLQuery(sqlQuery
							.toString());
					lookupListEDP = queryEDPParameters.list();
				} else if (!(RMDServiceConstants.FAMILY_AC6000
						.equalsIgnoreCase(family))
						&& !(RMDServiceConstants.FAMILY_ACCCA
								.equalsIgnoreCase(family))
						&& !(RMDServiceConstants.FAMILY_DCCCA
								.equalsIgnoreCase(family))
						&& !(RMDServiceConstants.FAMILY_CCA)
								.equalsIgnoreCase(family))

				{
					
					sqlQuery
					.append(" AND  LOWER(COLNAME.FAMILY)=LOWER( :FAMILY)");
					sqlQuery
					.append(" GROUP BY COLNAME.objid, COLNAME.COLUMN_NAME, nvl2(PARM_LOAD_COLUMN,PARM_LOAD_COLUMN,DB_COLUMN_NAME), COLNAME.PARM_TYPE ORDER BY COLUMN_NAME");
					// For EDP
					queryEDPParameters = session.createSQLQuery(sqlQuery
							.toString());
					queryEDPParameters.setParameter(
							RMDCommonConstants.FAMILY_INITIAL_LOADER, family);
					lookupListEDP = queryEDPParameters.list();
				}

				// Iterating EDP Param list
				if (RMDCommonUtility.isCollectionNotEmpty(lookupListEDP)) {
					for (final Iterator<Object> iter1 = lookupListEDP
							.iterator(); iter1.hasNext();) {
						parameterServiceVO = new ParameterServiceVO();
						final Object[] lookupRecord1 = (Object[]) iter1.next();

						parameterServiceVO.setParameterNameID(lookupRecord1[0]
								.toString());
						parameterServiceVO.setParameterName(lookupRecord1[1]
								.toString());
						parameterServiceVO
								.setColumnType(RMDServiceConstants.EDP);
						arlResults.add(parameterServiceVO);
					}
				}
			}

			

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRULEINITIALLOAD);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objParameterRequestServiceVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRULEINITIALLOAD);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objParameterRequestServiceVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		LOG.debug("End of getInitialLoadForAddRule in RuleDAOImpl method");

		return arlResults;
	}

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * getInitialRuleDefValues()
     */
    /*
     * This Method is used for fetch the details from database for loading the
     * configuration dropdown values in the AddDefintion Pages
     */
    @Override
    public Map getInitialRuleDefValues(String strFamily, Long lngUserId, String strLanguage) throws RMDDAOException {
        List arlFilterList = null;

        Session objHibernateSession = null;
        String strCustomerId = null;
        HashMap<String, BigDecimal> filterMap = null;
        ElementVO objElementVO = null;
        Map filterListMap = null;
        try {
            filterListMap = new HashMap();
            objHibernateSession = getHibernateSession();
            if (strFamily != null && strFamily != RMDCommonConstants.EMPTY_STRING) {
                strCustomerId = getCustomerId(lngUserId, strLanguage);
                if (strCustomerId != null && !strCustomerId.equals(RMDCommonConstants.EMPTY_STRING)) {
                    filterMap = getFilterMap(strCustomerId, strLanguage);
                    if (filterMap != null) {
                        if (!filterMap.isEmpty() && filterMap.size() > 0) {
                            Set filterSet = new HashSet(filterMap.keySet());
                            Iterator filterIterator = filterSet.iterator();
                            while (filterIterator.hasNext()) {
                                String filterId = (String) filterIterator.next();
                                BigDecimal filterOrder = filterMap.get(filterId);
                                if (filterId != null && !filterId.equals(RMDCommonConstants.EMPTY_STRING)) {
                                    objElementVO = new ElementVO();
                                    objElementVO.setId(String.valueOf(filterOrder));
                                    objElementVO.setName(filterId);
                                    if (filterId.equals(RMDCommonConstants.CUSTOMER1)) {
                                        arlFilterList = getCustomerList(strLanguage, null);
                                    } else if (filterId.equals(RMDCommonConstants.MODEL_MODEL)) {
                                        arlFilterList = getModelList(strFamily, strLanguage, null);
                                    } else if (filterId.equals(RMDCommonConstants.CFG)) {
                                        arlFilterList = getAllConfigList(strFamily, strLanguage);

                                    } else if (filterId.equals(RMDCommonConstants.FLEET_FILTERID)) {
                                        if (filterSet.contains(RMDCommonConstants.CUSTOMER1)) {
                                            arlFilterList = new ArrayList();
                                        } else {
                                            arlFilterList = getFleetsList(strCustomerId, strLanguage);
                                        }
                                    }
                                    filterListMap.put(objElementVO, arlFilterList);
                                    arlFilterList = null;
                                }
                            }
                        }
                    }

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONFIGURATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CONFIGURATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return filterListMap;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOIntf#
     * ruleTitleCheck
     * (com.ge.trans.rmd.services.tools.rulemgmt.service.valueobjects
     * .FinalRuleServiceVO)
     */
    /*
     * This Method is used for check the rule title is already exist in database
     * for any other rule's
     */
    @Override
    public String ruleTitleCheck(FinalRuleServiceVO finalRuleServiceVO) throws RMDDAOException {
        String strViewId = RMDServiceConstants.FAILURE;
        List arlRuleTitle = null;
        Session objHibernateSession = null;
        StringBuilder finRuleBuilder = null;
        Query ruleTileQuery = null;
        try {
            objHibernateSession = getHibernateSession(finalRuleServiceVO.getStrUserName());
            // Condition to check the rule id needs to be created newly or
            // already there.

            finRuleBuilder = new StringBuilder();
            finRuleBuilder.append("SELECT A.OBJID FROM GETS_TOOL_DPD_FINRUL A,GETS_TOOL_DPD_RULHIS B ");
            finRuleBuilder.append(
                    " WHERE B.RULHIS2FINRUL = A.OBJID AND UPPER( A.RULE_TITLE) = UPPER (:ruleTitle)AND A.FAMILY=:Family  ");
            // checking rule history table if the rule is edit
            if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO.getArlRuleHistoryVO())) {
                if (finalRuleServiceVO.getArlRuleHistoryVO().get(0).getStrOriginalID() != null) {
                    finRuleBuilder.append(
                            "AND B.ORIGINAL_ID != ( SELECT RULHIS.ORIGINAL_ID FROM GETS_TOOL_DPD_RULHIS RULHIS WHERE RULHIS.RULHIS2FINRUL = :finRuleObjid)");
                }
            }

            ruleTileQuery = objHibernateSession.createSQLQuery(finRuleBuilder.toString());
            ruleTileQuery.setParameter(RMDServiceConstants.RULE_TITLE, AppSecUtil
                    .stripNonValidXMLCharactersForKM(AppSecUtil.decodeString(finalRuleServiceVO.getStrRuleTitle())));
            ruleTileQuery.setParameter(RMDServiceConstants.FAMILY, finalRuleServiceVO.getStrFamily());
            if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO.getArlRuleHistoryVO())) {
                if (finalRuleServiceVO.getArlRuleHistoryVO().get(0).getStrOriginalID() != null) {
                    ruleTileQuery.setParameter(RMDServiceConstants.FINRULE_OBJID,
                            finalRuleServiceVO.getArlRuleHistoryVO().get(0).getStrOriginalID());
                }
            }
            arlRuleTitle = ruleTileQuery.list();
            // checking if Rule title is already exist or not.
            if (null != arlRuleTitle && !arlRuleTitle.isEmpty()) {
                strViewId = RMDServiceConstants.TITLE_ALREADY_EXIST;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, finalRuleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, finalRuleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
            arlRuleTitle = null;
        }
        return strViewId;
    }

    /*
     * This Method is used for delete the save rule as draft details from
     * database
     */
    public void deleteSaveDfaft(FinalRuleServiceVO finalRuleServiceVO) {
        Session objHibernateSession = null;
        try {
            GetKmDpdRulhisHVO objDpdRulhisHVO = null;

            objHibernateSession = getHibernateSession(finalRuleServiceVO.getStrUserName());
            RuleHistoryServiceVO serviceHistory = finalRuleServiceVO.getArlRuleHistoryVO().get(0);
            Criteria critsaveDraft = objHibernateSession.createCriteria(GetKmDpdRulhisHVO.class);
            critsaveDraft.add(Restrictions.eq(RMDCommonConstants.GETKMDPDFINRUL_GETKMDPDFINRULSEQID,
                    Long.valueOf(serviceHistory.getStrRuleID())));

            List critsaveDraftList = critsaveDraft.list();

            if (null != critsaveDraftList && !critsaveDraftList.isEmpty()) {

                objDpdRulhisHVO = (GetKmDpdRulhisHVO) critsaveDraftList.get(0);

                // check save as draft condition (active 0 && complete 0)
                if (RMDServiceConstants.INACTIVE_STATE.equals(Long.valueOf(objDpdRulhisHVO.getActive()))
                        && RMDServiceConstants.INACTIVE_STATE.equals(objDpdRulhisHVO.getCompleted())) {

                    // Delete From final Rule Table
                    String queryString = "delete from GetKmDpdFinrulHVO his where his.getKmDpdFinrulSeqId = :getKmDpdFinrulSeqId";

                    Query query = objHibernateSession.createQuery(queryString);

                    query.setParameter(RMDCommonConstants.GET_KMDP_FIN_RULE_SEQID,
                            Long.valueOf(serviceHistory.getStrRuleID()));

                    query.executeUpdate();

                    // delete from GET_KM_DPD_SIMRUL and
                    // GET_KM.GET_KM_DPD_SIMFEA
                    String sqlsimrule = "delete from GetKmDpdSimrulHVO simp where "
                            + "simp.getKmDpdSimrulSeqId in (select fin.getKmDpdSimrul.getKmDpdSimrulSeqId from  "
                            + "GetKmDpdFinrulHVO fin where fin.getKmDpdFinrulSeqId=:getKmDpdFinrulSeqId)";

                    Query queryRule = objHibernateSession.createQuery(sqlsimrule);

                    queryRule.setParameter(RMDCommonConstants.GET_KMDP_FIN_RULE_SEQID,
                            Long.valueOf(finalRuleServiceVO.getStrFinalRuleID()));

                    queryRule.executeUpdate();

                    // delete from complex rule details
                    String sqlcomprule = "delete from GetKmDpdCmprulHVO comp where "
                            + "comp.getKmDpdCmprulSeqId in (select fin.getKmDpdCmprul.getKmDpdCmprulSeqId from  "
                            + "GetKmDpdFinrulHVO fin where fin.getKmDpdFinrulSeqId=:getKmDpdFinrulSeqId)";

                    Query queryComp = objHibernateSession.createQuery(sqlcomprule);

                    queryComp.setParameter(RMDCommonConstants.GET_KMDP_FIN_RULE_SEQID,
                            Long.valueOf(finalRuleServiceVO.getStrFinalRuleID()));

                    queryComp.executeUpdate();

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_DEL_SAVE_DRAFT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, finalRuleServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEP_DEL_SAVE_DRAFT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, finalRuleServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOInf#
     * getRuleSummaryDetails (com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.FinalRuleServiceVO)
     */
    /* This Method is used for save the rule details in database */
    @Override
	public String saveFinalRule(FinalRuleServiceVO finalRuleServiceVO)
			throws RMDDAOException {
		Session objHibernateSession = null;
		String strFamily = RMDCommonConstants.EMPTY_STRING;
		String strUserName = RMDCommonConstants.EMPTY_STRING;
		String strFinRulType = null;
		Map<String, String> hmSimRule = null;
		String strFinRulObjid = null;
		// default time window value
		float flTimeWindow = 24;
		String strRule1SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule2SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule1CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule2CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
		// Variables used Complex Rule
		String strRul1FinRul = RMDServiceConstants.NEGATIVE_ONE;
		String strRul2FinRul = RMDServiceConstants.NEGATIVE_ONE;
		int intCplxRulLstSize = -1;

		Map<String, String> hmCmpRules = new HashMap<String, String>();
		String strRule1 = null;
		String strRule2 = null;
		String strRule1Type = RMDCommonConstants.EMPTY_STRING;
		String strRule2Type = null;
		String strCmpSeqId = null;
		int complexIndex = 0;
		try {
			objHibernateSession = getHibernateSession(finalRuleServiceVO
					.getStrUserName());
			objHibernateSession.beginTransaction();
			// Collect the new final rule seq id
			strFinRulObjid = getSequenceId(
					RMDServiceConstants.QueryConstants.FINRUL_SEQ_SQL
							.toString(),
					objHibernateSession);
			// if the rule is edited then store the existing(old) rule id into
			// strOldFinalRuleID.
			if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO
					.getArlRuleHistoryVO())) {
				finalRuleServiceVO.setStrOldFinalRuleID(finalRuleServiceVO
						.getStrFinalRuleID());
			} else {
				finalRuleServiceVO
						.setStrOldFinalRuleID(RMDCommonConstants.EMPTY_STRING);
			}
			// Store the new rule id to the StrFinalRuleID, which is created
			// above.
			finalRuleServiceVO.setStrFinalRuleID(strFinRulObjid);

			strFamily = finalRuleServiceVO.getStrFamily();
			strUserName = finalRuleServiceVO.getStrUserName();
			if (finalRuleServiceVO.getClearingLogicRule() != null
					&& (finalRuleServiceVO.getClearingLogicRule()
							.getArlComplexRule().size() > 0 || finalRuleServiceVO
							.getClearingLogicRule().getArlSimpleRule().size() > 0)) {
				finalRuleServiceVO.getClearingLogicRule().setStrUserName(
						strUserName);
				finalRuleServiceVO.getClearingLogicRule().setStrFinalRuleID(
						strFinRulObjid);
			}

			/** ******* SimpleRule start here ********************** */
			// This map will hold the list of simple rule ids
			hmSimRule = saveSimpleRule(finalRuleServiceVO, objHibernateSession);

			/** *******End SimpleRule********************** */

			/** *******Start Complex Rule********************** */
			/*
			 * This below lines are used for iterate finalRuleServiceVO and get
			 * the complex rule details
			 */

			if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO
					.getArlComplexRule())) {
				strFinRulType = RMDServiceConstants.COMPLEX_RULE_TYPE;
				if (null != finalRuleServiceVO.getArlComplexRule()) {
					intCplxRulLstSize = finalRuleServiceVO.getArlComplexRule()
							.size();
				}
				List<String> complexRulesList = new ArrayList<String>();
				// Collect the complex rules in the Set: Set is to avoid
				// duplicate values
				// we can remove as comp id should not ve duplicaton id we can
				// remove
				for (ComplexRuleServiceVO objComplexRuleServiceVO : finalRuleServiceVO
						.getArlComplexRule()) {

					complexRulesList.add(objComplexRuleServiceVO
							.getStrComplexRuleId());
				}
				// sort the list : this is to retrieve the
				// values in the sequence

				Collections.sort(complexRulesList);
				Collections.reverse(complexRulesList);

				for (String strComplexRuleId : complexRulesList) {
					strCmpSeqId = getSequenceId(
							RMDServiceConstants.QueryConstants.SIMRUL_SEQ_SQL,
							objHibernateSession);
					hmCmpRules.put(strComplexRuleId, strCmpSeqId);
					finalRuleServiceVO.getArlComplexRule().get(complexIndex)
							.setCsequenceID(Long.parseLong(strCmpSeqId));
					complexIndex++;
				}

				// iterate the list of complex rules and insert those values to
				// the GETS_TOOL_DPD_CMPRUL table
				for (ComplexRuleServiceVO objComplexRuleServiceVO : finalRuleServiceVO
						.getArlComplexRule()) {
					// make it default after inserting the record
					strRule1SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule2SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule1CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule2CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRul1FinRul = RMDServiceConstants.NEGATIVE_ONE;
					strRul2FinRul = RMDServiceConstants.NEGATIVE_ONE;
					strRule1Type = objComplexRuleServiceVO.getStrRule1Type();
					strRule2Type = objComplexRuleServiceVO.getStrRule2Type();
					strRule1 = objComplexRuleServiceVO.getStrRule1();
					strRule2 = objComplexRuleServiceVO.getStrRule2();
					/*
					 * int intCmpxsize = finalRuleServiceVO.getArlComplexRule()
					 * .size();
					 */
					// To identify the Rule 1 is Simple or Complex and to find
					// the seqId
					// To find the sequence id
					if (!RMDCommonUtility.isNullOrEmpty(strRule1Type)) {
						if (strRule1Type
								.equalsIgnoreCase(RMDServiceConstants.COMPLEX_RULE_TYPE)) {
							strRule1CmpSeqId = hmCmpRules.get(strRule1);
						} else if (strRule1Type
								.equalsIgnoreCase(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
							strRule1SimpleSeqId = hmSimRule.get(strRule1);
						} else {
							strRul1FinRul = strRule1;
						}
					}
					if (!RMDCommonUtility.isNullOrEmpty(strRule2Type)) {
						if (strRule2Type
								.equalsIgnoreCase(RMDServiceConstants.COMPLEX_RULE_TYPE)) {
							strRule2CmpSeqId = hmCmpRules.get(strRule2);
						} else if (strRule2Type
								.equalsIgnoreCase(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
							strRule2SimpleSeqId = hmSimRule.get(strRule2);
						} else {
							strRul2FinRul = strRule2;
						}
					}

					// Logic to calculate time Window
					// If timeFlag is P store value as – ve of time window
					if (!RMDCommonUtility.isNullOrEmpty(objComplexRuleServiceVO
							.getStrTimeFlag())) {
						flTimeWindow = Float.parseFloat(objComplexRuleServiceVO
								.getStrTimeWindow());
						if (RMDServiceConstants.TIMEFLAG_P
								.equalsIgnoreCase(objComplexRuleServiceVO
										.getStrTimeFlag())) {
							flTimeWindow = Math.abs(flTimeWindow) * (-1);
						}
					}

					Double subTime = ((null == objComplexRuleServiceVO.getStrSubTimeWindow()) || objComplexRuleServiceVO.getStrSubTimeWindow().isEmpty()) ? -1 : Double.valueOf(objComplexRuleServiceVO.getStrSubTimeWindow());
					Integer subGoal = (null == objComplexRuleServiceVO.getStrSubWindowGoal() || objComplexRuleServiceVO.getStrSubWindowGoal().isEmpty()) ? -1 : Integer.valueOf(objComplexRuleServiceVO.getStrSubWindowGoal());
					
					// After receiving the rule1 seq id and rule2 seq id
					// Insert them in the table GETS_TOOL_DPD_CMPRUL
					Query cmpQuery = objHibernateSession
							.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_CMPRUL_BUILDER
									.toString());
					cmpQuery.setParameter(RMDServiceConstants.CMPRULE_OBJID,
							hmCmpRules.get(objComplexRuleServiceVO
									.getStrComplexRuleId()));
					cmpQuery.setParameter(RMDServiceConstants.USERNAME,
							finalRuleServiceVO.getStrUserName());
					cmpQuery.setParameter(RMDServiceConstants.TIME_WINDOW,
							flTimeWindow);
					cmpQuery.setParameter(RMDServiceConstants.FREQUENCY,
							objComplexRuleServiceVO.getStrFrequency());
					cmpQuery.setParameter(RMDServiceConstants.CMPLX_FUNCTION,
							objComplexRuleServiceVO.getStrFunction());
					cmpQuery.setParameter(RMDServiceConstants.RULE1SIMRUL,
							strRule1SimpleSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE2SIMRUL,
							strRule2SimpleSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE1CMPRUL,
							strRule1CmpSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE2CMPRUL,
							strRule2CmpSeqId);
					cmpQuery.setParameter(RMDServiceConstants.FINRULE_OBJID,
							strFinRulObjid);
					cmpQuery.setParameter(RMDServiceConstants.RULE1FINRUL,
							strRul1FinRul);
					cmpQuery.setParameter(RMDServiceConstants.RULE2FINRUL,
							strRul2FinRul);
					cmpQuery.setParameter(RMDServiceConstants.COLUMN_PIXELS,
							objComplexRuleServiceVO.getStrPixels());
					cmpQuery.setParameter(
							RMDServiceConstants.COLUMN_RULE1PIXELS,
							objComplexRuleServiceVO.getStrRule1Pixels());
					cmpQuery.setParameter(
							RMDServiceConstants.COLUMN_RULE2PIXELS,
							objComplexRuleServiceVO.getStrRule2Pixels());
					cmpQuery.setParameter(
							RMDServiceConstants.SUBTIMEWINDOW,
							subTime);
					cmpQuery.setParameter(
							RMDServiceConstants.SUBWINDOWGOAL,
							subGoal);
					cmpQuery.setParameter(
							RMDServiceConstants.STR_NOTES,
							AppSecUtil.stripNonValidXMLCharactersForKM(objComplexRuleServiceVO.getNotes()));
					cmpQuery.executeUpdate();

				}
			} else {
				// else if complex rule does not exist
				strFinRulType = RMDServiceConstants.SIMPLE_RULE_TYPE;
			}

			/** SAVING FINAL RULE STARTS */
			// inserting the rule details to the table GETS_TOOL_DPD_FINRUL

			Query finalRuleQuery = objHibernateSession
					.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_FINRUL_BUILDER
							.toString());
			finalRuleQuery.setParameter(RMDServiceConstants.FINRULE_OBJID,
					strFinRulObjid);
			finalRuleQuery.setParameter(RMDServiceConstants.FAMILY, strFamily);
			finalRuleQuery.setParameter(RMDServiceConstants.USERNAME,
					strUserName);
			/*
			 * finalRuleQuery.setParameter(RMDServiceConstants.RULE_TITLE,
			 * finalRuleServiceVO.getStrRuleTitle());
			 */
			finalRuleQuery.setParameter(RMDServiceConstants.RULE_TITLE,
					AppSecUtil.stripNonValidXMLCharactersForKM(AppSecUtil.decodeString(finalRuleServiceVO
							.getStrRuleTitle())));
			finalRuleQuery.setParameter(RMDServiceConstants.RULE_DESC,
					AppSecUtil.stripNonValidXMLCharactersForKM(AppSecUtil.decodeString(finalRuleServiceVO
							.getStrRuleDescription())));
			/*
			 * finalRuleQuery.setParameter(RMDServiceConstants.RULE_DESC,
			 * finalRuleServiceVO.getStrRuleDescription());
			 */
			finalRuleQuery.setParameter(RMDServiceConstants.LOOKBACK, 1);
			finalRuleQuery.setParameter(RMDServiceConstants.SUBSYSTEM,
					finalRuleServiceVO.getStrSubsystem());
			finalRuleQuery.setParameter(RMDServiceConstants.COLUMN_PIXELS,
					finalRuleServiceVO.getStrPixels());
			if (strFinRulType.equals(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
				finalRuleQuery.setParameter(RMDServiceConstants.FINRUL2SIMRULE,
						finalRuleServiceVO.getArlSimpleRule().get(0)
								.getSimpleSequenceId());
				finalRuleQuery.setParameter(RMDServiceConstants.FINRUL2CMPRULE,
						RMDServiceConstants.NEGATIVE_ONE);
			} else {
				finalRuleQuery.setParameter(RMDServiceConstants.FINRUL2SIMRULE,
						RMDServiceConstants.NEGATIVE_ONE);
				finalRuleQuery.setParameter(
						RMDServiceConstants.FINRUL2CMPRULE,
						finalRuleServiceVO.getArlComplexRule()
								.get(intCplxRulLstSize - 1).getCsequenceID());
			}
			finalRuleQuery.executeUpdate();
			/** SAVING FINAL RULE ENDS */

			/** SAVING RULE HISTORY STARTS */
			saveRuleHistory(finalRuleServiceVO, objHibernateSession);

			/** SAVING RULE HISTORY STARTS ENDS */

			/** ****************Start RuleDefinition ********************** */
			saveRuleDefinition(finalRuleServiceVO, objHibernateSession);
			/** ****************End RuleDefinition ********************** */
			/******** Update the old final rule reference to new final rule ***********/
//			if(finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase("yes")) {
//				updateOldFinRule(finalRuleServiceVO, objHibernateSession);				
//			}
			if (finalRuleServiceVO.getClearingLogicRule() != null
					&& (finalRuleServiceVO.getClearingLogicRule()
							.getArlComplexRule().size() > 0 || finalRuleServiceVO
							.getClearingLogicRule().getArlSimpleRule().size() > 0)) {
				saveClearingLogicRule(finalRuleServiceVO, objHibernateSession);
			}
			/************** End ********************/
			if(!objHibernateSession.getTransaction().wasCommitted()){
				objHibernateSession.getTransaction().commit();
			}
		} catch (RMDDAOConnectionException ex) {
			
			objHibernateSession.getTransaction().rollback();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							finalRuleServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException objRMDDAOException) {
			
			objHibernateSession.getTransaction().rollback();
			throw objRMDDAOException;
		} catch (Exception e) {
			objHibernateSession.getTransaction().rollback();
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							finalRuleServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(objHibernateSession);
		}
		LOG.debug("End of saveFinalRule method in RuleDAOImpl" + strFinRulObjid);
		return strFinRulObjid + RMDCommonConstants.EMPTY_STRING;
	}

	private String saveClearingLogicRule(FinalRuleServiceVO finalRuleServiceVO, Session objHibernateSession) {
		//Session objHibernateSession = null;
		String strUserName = RMDCommonConstants.EMPTY_STRING;
		String strClearingLogicRulType = null;
		Map<String, String> hmSimRule = null;
		String strClearingLogicRulObjid = null;
		// default time window value
		float flTimeWindow = 24;
		String strRule1SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule2SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule1CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
		String strRule2CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
		// Variables used Complex Rule
		String strRul1FinRul = RMDServiceConstants.NEGATIVE_ONE;
		String strRul2FinRul = RMDServiceConstants.NEGATIVE_ONE;
		int intCplxRulLstSize = -1;

		Map<String, String> hmCmpRules = new HashMap<String, String>();
		String strRule1 = null;
		String strRule2 = null;
		String strRule1Type = RMDCommonConstants.EMPTY_STRING;
		String strRule2Type = null;
		String strCmpSeqId = null;
		int complexIndex = 0;
		ClearingLogicRuleServiceVO clearingLogicRuleServiceVO = null;
		try {
			clearingLogicRuleServiceVO = finalRuleServiceVO
					.getClearingLogicRule();
			strUserName = clearingLogicRuleServiceVO.getStrUserName();
			//objHibernateSession = getHibernateSession(strUserName);
			// Collect the new final rule seq id
			strClearingLogicRulObjid = getSequenceId(
					RMDServiceConstants.QueryConstants.FINRUL_SEQ_SQL
							.toString(),
					objHibernateSession);
			if (!RMDCommonUtility.isNullOrEmpty(clearingLogicRuleServiceVO
					.getStrOldClearingLogicRuleID())) {
				clearingLogicRuleServiceVO
						.setStrOldClearingLogicRuleID(clearingLogicRuleServiceVO
								.getStrClearingLogicRuleID());
			} else {
				clearingLogicRuleServiceVO
						.setStrOldClearingLogicRuleID(RMDCommonConstants.EMPTY_STRING);
			}
			// Store the new rule id to the StrFinalRuleID, which is created
			// above.
			clearingLogicRuleServiceVO
					.setStrClearingLogicRuleID(strClearingLogicRulObjid);

			/** ******* SimpleRule start here ********************** */
			// This map will hold the list of simple rule ids
			hmSimRule = saveClearingLogicSimpleRule(finalRuleServiceVO,
					objHibernateSession);

			/** *******End SimpleRule********************** */

			/** *******Start Complex Rule********************** */
			/*
			 * This below lines are used for iterate finalRuleServiceVO and get
			 * the complex rule details
			 */

			if (RMDCommonUtility
					.isCollectionNotEmpty(clearingLogicRuleServiceVO
							.getArlComplexRule())) {
				strClearingLogicRulType = RMDServiceConstants.COMPLEX_RULE_TYPE;
				if (null != clearingLogicRuleServiceVO.getArlComplexRule()) {
					intCplxRulLstSize = clearingLogicRuleServiceVO
							.getArlComplexRule().size();
				}
				List<String> complexRulesList = new ArrayList<String>();
				// Collect the complex rules in the Set: Set is to avoid
				// duplicate values
				// we can remove as comp id should not ve duplicaton id we can
				// remove
				for (ComplexRuleServiceVO objComplexRuleServiceVO : clearingLogicRuleServiceVO
						.getArlComplexRule()) {

					complexRulesList.add(objComplexRuleServiceVO
							.getStrComplexRuleId());
				}
				// sort the list : this is to retrieve the
				// values in the sequence

				Collections.sort(complexRulesList);
				Collections.reverse(complexRulesList);

				for (String strComplexRuleId : complexRulesList) {
					strCmpSeqId = getSequenceId(
							RMDServiceConstants.QueryConstants.SIMRUL_SEQ_SQL,
							objHibernateSession);
					hmCmpRules.put(strComplexRuleId, strCmpSeqId);
					clearingLogicRuleServiceVO.getArlComplexRule()
							.get(complexIndex)
							.setCsequenceID(Long.parseLong(strCmpSeqId));
					complexIndex++;
				}

				// iterate the list of complex rules and insert those values to
				// the GETS_TOOL_DPD_CMPRUL table
				for (ComplexRuleServiceVO objComplexRuleServiceVO : clearingLogicRuleServiceVO
						.getArlComplexRule()) {
					// make it default after inserting the record
					strRule1SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule2SimpleSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule1CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRule2CmpSeqId = RMDServiceConstants.NEGATIVE_ONE;
					strRul1FinRul = RMDServiceConstants.NEGATIVE_ONE;
					strRul2FinRul = RMDServiceConstants.NEGATIVE_ONE;
					strRule1Type = objComplexRuleServiceVO.getStrRule1Type();
					strRule2Type = objComplexRuleServiceVO.getStrRule2Type();
					strRule1 = objComplexRuleServiceVO.getStrRule1();
					strRule2 = objComplexRuleServiceVO.getStrRule2();
					/*
					 * int intCmpxsize = finalRuleServiceVO.getArlComplexRule()
					 * .size();
					 */
					// To identify the Rule 1 is Simple or Complex and to find
					// the seqId
					// To find the sequence id
					if (!RMDCommonUtility.isNullOrEmpty(strRule1Type)) {
						if (strRule1Type
								.equalsIgnoreCase(RMDServiceConstants.COMPLEX_RULE_TYPE)) {
							strRule1CmpSeqId = hmCmpRules.get(strRule1);
						} else if (strRule1Type
								.equalsIgnoreCase(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
							strRule1SimpleSeqId = hmSimRule.get(strRule1);
						} else {
							strRul1FinRul = strRule1;
						}
					}
					if (!RMDCommonUtility.isNullOrEmpty(strRule2Type)) {
						if (strRule2Type
								.equalsIgnoreCase(RMDServiceConstants.COMPLEX_RULE_TYPE)) {
							strRule2CmpSeqId = hmCmpRules.get(strRule2);
						} else if (strRule2Type
								.equalsIgnoreCase(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
							strRule2SimpleSeqId = hmSimRule.get(strRule2);
						} else {
							strRul2FinRul = strRule2;
						}
					}

					// Logic to calculate time Window
					// If timeFlag is P store value as � ve of time window
					if (!RMDCommonUtility.isNullOrEmpty(objComplexRuleServiceVO
							.getStrTimeFlag())) {
						flTimeWindow = Float.parseFloat(objComplexRuleServiceVO
								.getStrTimeWindow());
						if (RMDServiceConstants.TIMEFLAG_P
								.equalsIgnoreCase(objComplexRuleServiceVO
										.getStrTimeFlag())) {
							flTimeWindow = Math.abs(flTimeWindow) * (-1);
						}
					}
					Double subTime = ((null == objComplexRuleServiceVO.getStrSubTimeWindow()) || objComplexRuleServiceVO.getStrSubTimeWindow().isEmpty()) ? -1 : Double.valueOf(objComplexRuleServiceVO.getStrSubTimeWindow());
					Integer subGoal = (null == objComplexRuleServiceVO.getStrSubWindowGoal() || objComplexRuleServiceVO.getStrSubWindowGoal().isEmpty()) ? -1 : Integer.valueOf(objComplexRuleServiceVO.getStrSubWindowGoal());

					// After receiving the rule1 seq id and rule2 seq id
					// Insert them in the table GETS_TOOL_DPD_CMPRUL
					Query cmpQuery = objHibernateSession
							.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_CMPRUL_BUILDER
									.toString());
					cmpQuery.setParameter(RMDServiceConstants.CMPRULE_OBJID,
							hmCmpRules.get(objComplexRuleServiceVO
									.getStrComplexRuleId()));
					cmpQuery.setParameter(RMDServiceConstants.USERNAME,
							strUserName);
					cmpQuery.setParameter(RMDServiceConstants.TIME_WINDOW,
							flTimeWindow);
					cmpQuery.setParameter(RMDServiceConstants.FREQUENCY,
							objComplexRuleServiceVO.getStrFrequency());
					cmpQuery.setParameter(RMDServiceConstants.CMPLX_FUNCTION,
							objComplexRuleServiceVO.getStrFunction());
					cmpQuery.setParameter(RMDServiceConstants.RULE1SIMRUL,
							strRule1SimpleSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE2SIMRUL,
							strRule2SimpleSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE1CMPRUL,
							strRule1CmpSeqId);
					cmpQuery.setParameter(RMDServiceConstants.RULE2CMPRUL,
							strRule2CmpSeqId);
					cmpQuery.setParameter(RMDServiceConstants.FINRULE_OBJID,
							strClearingLogicRulObjid);
					cmpQuery.setParameter(RMDServiceConstants.RULE1FINRUL,
							strRul1FinRul);
					cmpQuery.setParameter(RMDServiceConstants.RULE2FINRUL,
							strRul2FinRul);
					cmpQuery.setParameter(RMDServiceConstants.COLUMN_PIXELS,
							objComplexRuleServiceVO.getStrPixels());
					cmpQuery.setParameter(
							RMDServiceConstants.COLUMN_RULE1PIXELS,
							objComplexRuleServiceVO.getStrRule1Pixels());
					cmpQuery.setParameter(
							RMDServiceConstants.COLUMN_RULE2PIXELS,
							objComplexRuleServiceVO.getStrRule2Pixels());
					cmpQuery.setParameter(
							RMDServiceConstants.SUBTIMEWINDOW,
							subTime);
					cmpQuery.setParameter(
							RMDServiceConstants.SUBWINDOWGOAL,
							subGoal);
					cmpQuery.setParameter(
							RMDServiceConstants.STR_NOTES,
							AppSecUtil.stripNonValidXMLCharactersForKM(objComplexRuleServiceVO.getNotes()));
					cmpQuery.executeUpdate();

				}
			} else {
				// else if complex rule does not exist
				strClearingLogicRulType = RMDServiceConstants.SIMPLE_RULE_TYPE;
			}

			/** SAVING CLEARING LOGIC RULE STARTS */

			Query clearingLogicRuleQuery = objHibernateSession
					.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_CL_RUL_BUILDER
							.toString());
			clearingLogicRuleQuery
					.setParameter(RMDServiceConstants.CL_RULE_OBJID,
							strClearingLogicRulObjid);
			clearingLogicRuleQuery.setParameter(
					RMDServiceConstants.CL_RUL2FINRULE,
					clearingLogicRuleServiceVO.getStrFinalRuleID());
			clearingLogicRuleQuery.setParameter(RMDServiceConstants.USERNAME,
					strUserName);
			clearingLogicRuleQuery.setParameter(
					RMDServiceConstants.COLUMN_PIXELS,
					clearingLogicRuleServiceVO.getStrPixels());
			if (strClearingLogicRulType
					.equals(RMDServiceConstants.SIMPLE_RULE_TYPE)) {
				clearingLogicRuleQuery.setParameter(
						RMDServiceConstants.CL_RUL2SIMRULE,
						clearingLogicRuleServiceVO.getArlSimpleRule().get(0)
								.getSimpleSequenceId());
				clearingLogicRuleQuery.setParameter(
						RMDServiceConstants.CL_RUL2CMPRULE,
						RMDServiceConstants.NEGATIVE_ONE);
			} else {
				clearingLogicRuleQuery.setParameter(
						RMDServiceConstants.CL_RUL2SIMRULE,
						RMDServiceConstants.NEGATIVE_ONE);
				clearingLogicRuleQuery.setParameter(
						RMDServiceConstants.CL_RUL2CMPRULE,
						clearingLogicRuleServiceVO.getArlComplexRule()
								.get(intCplxRulLstSize - 1).getCsequenceID());
			}
			clearingLogicRuleQuery.executeUpdate();
			/** SAVING FINAL RULE ENDS */

			/************** End ********************/
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							clearingLogicRuleServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (RMDDAOException objRMDDAOException) {
			throw objRMDDAOException;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_INSERTDETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							clearingLogicRuleServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			//releaseSession(objHibernateSession);
		}
		LOG.debug("End of saveClearingLogicRule method in RuleDAOImpl"
				+ strClearingLogicRulObjid);
		return strClearingLogicRulObjid + RMDCommonConstants.EMPTY_STRING;
	}

    /*
     * This Method is used for fetch the details from database for loading the
     * configuration Function dropdown values in the AddDefintion Pages
     */
    @Override
    public List getconfigFcnValues(String strLanguage) throws RMDDAOException {
        List arlFunctions = null;
        Session session = null;
        ElementVO objElementVO = null;
        try {
            session = getHibernateSession();

            String queryString = "Select Fcn From Gets_Tool_Dpd_Simfcn";

            Query query = session.createSQLQuery(queryString);
            List<String> lookupList = query.list();

            if (lookupList != null && !lookupList.isEmpty()) {
                arlFunctions = new ArrayList();

                for (int i = 0; i < lookupList.size(); i++) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(lookupList.get(i));

                    objElementVO.setName(lookupList.get(i));
                    arlFunctions.add(objElementVO);

                }

            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);

        }
        return arlFunctions;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOInf#getCustomer
     * ()
     */
    /*
     * This Method is used for fetch the details from database for loading the
     * Customer dropdown values
     */
    @Override
    public List getCustomerList(String strLanguage, String userId) throws RMDDAOException {
        List arlCustomers = null;
        StringBuilder defaultCustomerValue = new StringBuilder();
        String defaultCustomer = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        Query custHqry = null;
        boolean allCustomer = true;
        List<Object> customerList = null;
        LOG.debug("Enter into getCustomer in RuleDAOImpl method");
        try {
            arlCustomers = new ArrayList();
            objHibernateSession = getHibernateSession();
            allCustomer = isuserHavingCustomerList(userId, defaultCustomerValue);
            if (null != defaultCustomerValue && defaultCustomerValue.length() > 0) {
                defaultCustomer = defaultCustomerValue.toString();
            }
            // if user id is present
            if (null != userId && !userId.isEmpty() && allCustomer) {
                String query = "SELECT BUSORG.ORG_ID,BUSORG.OBJID,BUSORG.NAME FROM GET_USR.GET_USR_USERS USR, GET_USR.GET_USR_USER_CUSTOMERS CUST,TABLE_BUS_ORG BUSORG  WHERE CUST.LINK_USERS=USR.GET_USR_USERS_SEQ_ID AND BUSORG.OBJID= CUST.LINK_CUSTOMERS  AND  USR.USER_ID = :userID ORDER BY LOWER(BUSORG.NAME)";
                if (objHibernateSession != null) {
                    custHqry = objHibernateSession.createSQLQuery(query.toString())
                            .setParameter(RMDCommonConstants.USER_ID, userId);
                }
                customerList = custHqry.list();
                for (final Iterator<Object> customerIter = customerList.iterator(); customerIter.hasNext();) {
                    final Object[] customerRec = (Object[]) customerIter.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(customerRec[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(customerRec[2]));
                    objElementVO.setCustomerSeqId(RMDCommonUtility.convertObjectToString(customerRec[1]));
                    if (null != defaultCustomer && !defaultCustomer.isEmpty()
                            && objElementVO.getCustomerSeqId().equals(defaultCustomer)) {
                        objElementVO.setIsdefault(true);
                    } else {
                        objElementVO.setIsdefault(false);
                    }
                    objElementVO.setAllCustomer(false);
                    arlCustomers.add(objElementVO);
                }
            } else {
                String query = "SELECT ORG_ID,OBJID,NAME FROM TABLE_BUS_ORG WHERE NAME IS NOT NULL ORDER BY LOWER(NAME)";
                if (objHibernateSession != null) {
                    custHqry = objHibernateSession.createSQLQuery(query.toString());
                }
                custHqry.setFetchSize(50);
                customerList = custHqry.list();
                for (final Iterator<Object> customerIter = customerList.iterator(); customerIter.hasNext();) {
                    final Object[] customerRec = (Object[]) customerIter.next();
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(customerRec[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(customerRec[2]));
                    objElementVO.setCustomerSeqId(RMDCommonUtility.convertObjectToString(customerRec[1]));
                    if (null != defaultCustomer && !defaultCustomer.isEmpty()
                            && objElementVO.getCustomerSeqId().equals(defaultCustomer)) {
                        objElementVO.setAllCustomerdefault(true);
                    } else {
                        objElementVO.setAllCustomerdefault(false);
                    }
                    objElementVO.setAllCustomer(true);
                    arlCustomers.add(objElementVO);
                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getCustomer in RuleDAOImpl method" + arlCustomers.size());
        return arlCustomers;
    }

    public boolean isuserHavingCustomerList(String userId, StringBuilder defaultCustomerValue) {
        Session objHibernateSession = getHibernateSession();
        try {

            String query = "SELECT usr.user_id FROM GET_USR.GET_USR_USERS USR, GET_USR_USER_CUSTOMERS CUST  WHERE CUST.LINK_USERS=USR.GET_USR_USERS_SEQ_ID AND USR.USER_ID = :userID ";
            Query defaultCust = objHibernateSession.createSQLQuery(query.toString())
                    .setParameter(RMDCommonConstants.USER_ID, userId);
            List<Object> customerList = defaultCust.list();
            query = "SELECT LINK_CUSTOMER FROM GET_USR.GET_USR_USERS WHERE USER_ID =:userID";
            Query defaultCustvalue = objHibernateSession.createSQLQuery(query.toString())
                    .setParameter(RMDCommonConstants.USER_ID, userId);
            BigDecimal defaultCustomerStr = (BigDecimal) defaultCustvalue.uniqueResult();
            if (null != defaultCustomerStr) {
                defaultCustomerValue.append(defaultCustomerStr.toString());
            }

            if (null != customerList && !customerList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOInf#getCustomer
     * ()
     */
    /*
     * This Method is used for fetch the details from database for loading the
     * Customer dropdown values
     */
    @Override
    public List getExcludedCustomerList(String[] strCustomerId, String strLanguage) throws RMDDAOException {
        List arlCustomers = null;
        Session objHibernateSession = null;
        GetCmCustomerHVO objGetCmCustomerHVO = null;
        ElementVO objElementVO = null;

        LOG.debug("Enter into getCustomer in RuleDAOImpl method");
        try {
            arlCustomers = new ArrayList();
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetCmCustomerHVO.class);
            Criterion objCriterion = Restrictions.in(RMDCommonConstants.CUSTOMER_ID, strCustomerId);
            criteria.add(Restrictions.not(objCriterion));
            List customerList = criteria.list();
            if (null != customerList && !customerList.isEmpty()) {
                int customerListSize = customerList.size();
                for (int i = 0; i < customerListSize; i++) {
                    objGetCmCustomerHVO = (GetCmCustomerHVO) customerList.get(i);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objGetCmCustomerHVO.getCustomerId());
                    objElementVO.setName(objGetCmCustomerHVO.getCustomerId());
                    arlCustomers.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getCustomer in RuleDAOImpl method" + arlCustomers.size());
        return arlCustomers;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.dao.intf.RuleDAOInf#getFleets
     * (java.lang.String)
     */
    /* This Method is used for fetch the fleets details from database */
    @Override
    public List getFleetsList(String strCustomerId, String strLanguage) {
        List arlFleet = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        GetAsstFleetHVO objGetAsstFleetHVO = null;
        LOG.debug("Enter into getFleets method in RuleDAOImpl");
        try {
            arlFleet = new ArrayList();
            objHibernateSession = getHibernateSession();
            Criteria fleetCriteria = objHibernateSession.createCriteria(GetAsstFleetHVO.class);
            fleetCriteria.setFetchMode(RMDCommonConstants.GET_CM_CUSTOMER, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GET_CM_CUSTOMER, RMDCommonConstants.CUSTOMER);
            fleetCriteria.add(Restrictions.eq(RMDCommonConstants.CUSTOMER_CUSTOMERID, strCustomerId));
            fleetCriteria.setFetchSize(100);
            List lstFleets = fleetCriteria.list();
            if (null != lstFleets && !lstFleets.isEmpty()) {
                int fleetCount = lstFleets.size();
                for (int i = 0; i < fleetCount; i++) {
                    objGetAsstFleetHVO = (GetAsstFleetHVO) lstFleets.get(i);
                    objElementVO = new ElementVO();
                    objElementVO.setId(objGetAsstFleetHVO.getFleetNumber());
                    objElementVO.setName(objGetAsstFleetHVO.getFleetNumber());
                    arlFleet.add(objElementVO);

                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FLEET, e);
        }

        finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getFleets method in RuleDAOImpl");
        return arlFleet;
    }

    public HashMap<String, BigDecimal> getFilterMap(String strCustomerId, String strLanguage) {
        HashMap<String, BigDecimal> filterMap = new HashMap<String, BigDecimal>();
        List arlList = null;
        GetKmFiltersLinkHVO objGetKmFiltersLinkHVO = null;
        Session objHibernateSession = null;
        LOG.debug("Enter into getFilterMap method in RuleDAOImpl");
        try {
            objHibernateSession = getHibernateSession();
            Criteria filterCriteria = objHibernateSession.createCriteria(GetKmFiltersLinkHVO.class)
                    .setFetchMode(RMDCommonConstants.GETKMRULEDEFFILTERS, FetchMode.JOIN);
            filterCriteria.setFetchMode(RMDCommonConstants.GET_CM_CUSTOMER, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GET_CM_CUSTOMER, RMDCommonConstants.CUSTOMER);
            filterCriteria.add(Restrictions.eq(RMDCommonConstants.CUSTOMER_CUSTOMERID, strCustomerId));
            arlList = filterCriteria.list();
            if (arlList != null && !arlList.isEmpty()) {
                int listCount = arlList.size();
                for (int index = 0; index < listCount; index++) {
                    objGetKmFiltersLinkHVO = (GetKmFiltersLinkHVO) arlList.get(index);
                    filterMap.put(objGetKmFiltersLinkHVO.getGetKmRuledefFilters().getFilterId(),
                            objGetKmFiltersLinkHVO.getFilterOrder());
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST, e);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getFilterMap method in RuleDAOImpl");
        return filterMap;
    }

    @Override
    public String getCustomerId(Long lngUserId, String strLanguage) {

        String strCustomerId = null;
        Session objHibernateSession = null;
        List arlCustomerResult;
        LOG.debug("Enter into getCustomerId method in RuleDAOImpl");
        try {
            objHibernateSession = getHibernateSession();
            Query customerQuer = objHibernateSession.createSQLQuery(" SELECT DISTINCT cust.customer_id "
                    + " FROM GET_CM.GET_CM_CUSTOMER cust, GET_CM.GET_CM_CONTACT_INFO contact, GET_USR.GET_USR_USERS usr, GET_ASST.GET_ASST_LOCATION location "
                    + " WHERE usr.LINK_CONTACT_INFO = contact.GET_CM_CONTACT_INFO_SEQ_ID "
                    + " AND contact.LINK_LOCATION = location.GET_ASST_LOCATION_SEQ_ID "
                    + " AND location.LINK_CUSTOMER = cust.GET_CM_CUSTOMER_SEQ_ID " + " AND usr.GET_USR_USERS_SEQ_ID = "
                    + lngUserId + " ");
            arlCustomerResult = customerQuer.list();
            if (arlCustomerResult != null && !arlCustomerResult.isEmpty()) {
                strCustomerId = (String) arlCustomerResult.get(0);
            } else {
                strCustomerId = RMDCommonConstants.EMPTY_STRING;
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }

        catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTIONLIST, e);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getCustomerId method in RuleDAOImpl");
        return strCustomerId;
    }

    @Override
    public ArrayList getModelList(String strfamily, String strLanguage, String strKEP) throws RMDDAOException {
        ArrayList arlModels = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        LOG.debug("Enter into getModel in RuleDAOImpl method");
        try {
            arlModels = new ArrayList();
            arlModels = new ArrayList();
            String strModelQry = null;
            if (null != strKEP && !("".equals(strKEP)))
                strModelQry = getKEPModelsQuery(strLanguage);
            objHibernateSession = getHibernateSession();
            StringBuilder strQuery = new StringBuilder();
            strQuery = strQuery.append(RMDServiceConstants.MODEL_SELECT).append(RMDServiceConstants.FROM)
                    .append(RMDServiceConstants.MODEL_TABLE_NAME).append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.MOLDEL_FAMILY_NAME_EQUALS);

            if (null != strKEP && "Y".equals(strKEP)) {
                strQuery.append("AND MODEL.FAMILY  in (");
                strQuery.append(strModelQry);
                strQuery.append(")");
            }
            LOG.debug("model list query" + strQuery);
            Query qryQuery = objHibernateSession.createSQLQuery(strQuery.toString());

            if (null != strfamily) {

                qryQuery.setParameter(RMDCommonConstants.FAMILY, strfamily);
            }

            List<Object[]> modelList = qryQuery.list();

            // Setting model seq. id and model Name
            if (RMDCommonUtility.isCollectionNotEmpty(modelList)) {
                for (Object[] modelInfo : modelList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(modelInfo[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(modelInfo[1]));
                    arlModels.add(objElementVO);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getModel in RuleDAOImpl method" + arlModels.size());
        return arlModels;
    }

    @Override
    public List getConfigurationList(String[] strModel, String strLanguage) throws RMDDAOException {
        List configurationList = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        List arlConfiguration = null;
        LOG.debug("Enter into getConfigurationList in RuleDAOImpl method");
        try {
            arlConfiguration = new ArrayList();
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetAsstMasterBomHVO.class)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFS, FetchMode.JOIN)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFS_ASSTASSET, FetchMode.JOIN)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFGS_GETASSTASSET_GETASSTMODEL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFS, RMDCommonConstants.ASSTVEHCFGS)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFS_ASSTASSET, RMDCommonConstants.ASSTASSET)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFGS_GETASSTASSET_GETASSTMODEL,
                            RMDCommonConstants.ASSTMODEL)
                    .setProjection(Projections.distinct(Projections.projectionList()
                            .add(Projections.property(RMDCommonConstants.CONFIGITEM), RMDCommonConstants.CONFIGITEM)));
            criteria.add(Restrictions.in(RMDCommonConstants.ASSTMODEL_MODELNAME, strModel));
            configurationList = criteria.list();
            if (null != configurationList && !configurationList.isEmpty()) {
                int configurationListSize = configurationList.size();
                for (int i = 0; i < configurationListSize; i++) {
                    objElementVO = new ElementVO();
                    objElementVO.setId((String) configurationList.get(i));
                    objElementVO.setName((String) configurationList.get(i));
                    arlConfiguration.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getConfigurationList in RuleDAOImpl method" + arlConfiguration.size());
        return arlConfiguration;
    }

    @Override
    public List getAllConfigList(String strFamily, String strLanguage) throws RMDDAOException {
        List configurationList = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        List arlConfiguration = null;
        LOG.debug("Enter into getConfigurationList in RuleDAOImpl method");
        try {
            arlConfiguration = new ArrayList();
            objHibernateSession = getHibernateSession();
            Criteria criteria = objHibernateSession.createCriteria(GetAsstMasterBomHVO.class)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFS, FetchMode.JOIN)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFS_ASSTASSET, FetchMode.JOIN)
                    .setFetchMode(RMDCommonConstants.GETASSTVEHCFGS_GETASSTASSET_GETASSTMODEL, FetchMode.JOIN)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFS, RMDCommonConstants.ASSTVEHCFGS)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFS_ASSTASSET, RMDCommonConstants.ASSTASSET)
                    .createAlias(RMDCommonConstants.GETASSTVEHCFGS_GETASSTASSET_GETASSTMODEL,
                            RMDCommonConstants.ASSTMODEL)
                    .setProjection(Projections.distinct(Projections.projectionList()
                            .add(Projections.property(RMDCommonConstants.CONFIGITEM), RMDCommonConstants.CONFIGITEM)));
            criteria.add(Restrictions.eq(RMDCommonConstants.ASSTMODEL_FAMILY, strFamily));
            configurationList = criteria.list();
            if (null != configurationList && !configurationList.isEmpty()) {
                int configurationListSize = configurationList.size();
                for (int i = 0; i < configurationListSize; i++) {
                    objElementVO = new ElementVO();
                    objElementVO.setId((String) configurationList.get(i));
                    objElementVO.setName((String) configurationList.get(i));
                    arlConfiguration.add(objElementVO);
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End of getConfigurationList in RuleDAOImpl method" + arlConfiguration.size());
        return arlConfiguration;
    }

    /**
     * @Author:
     * @param: strRuleId
     * @return lstRuleId
     * @throws RMDDAOException
     * @Description:This method is the DAO Implementation for getting the list
     *                   of RuleIds from DataBase
     */

    @Override
    public List getRules(String strRuleId) throws RMDDAOException {
        LOG.debug("Enter into getRules method in RuleDAOImpl");
        ArrayList arlRuleId;
        List lstRuleId;
        String strQuery = RMDCommonConstants.EMPTY_STRING;
        int iRuleIdList = RMDCommonConstants.INT_CONST;
        Query hibQuery = null;
        Session objHibernateSession = null;
        GetKmDpdRulhisHVO objGetKmDpdRulhis = null;
        try {
            objHibernateSession = getHibernateSession();
            lstRuleId = new ArrayList();

            LOG.debug("*******Inside RuleDAOImpl getRules**************" + strRuleId);
            strQuery = "from GetKmDpdRulhisHVO ruleHist where ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId like :ruleIdKey";
            strQuery = strQuery + " order by ruleHist.getKmDpdFinrul.getKmDpdFinrulSeqId asc";
            hibQuery = objHibernateSession.createQuery(strQuery);
            if ("*".equalsIgnoreCase(strRuleId.trim())
                    || RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strRuleId.trim())) {
                strRuleId = RMDCommonConstants.EMPTY_STRING;
                hibQuery.setString(RMDServiceConstants.RULE_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strRuleId + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            } else {
                hibQuery.setString(RMDServiceConstants.RULE_ID_KEY,
                        RMDServiceConstants.PERCENTAGE + strRuleId + RMDServiceConstants.PERCENTAGE); // For
                // Starts
                // with
            }
            // hibQuery.setInteger(RMDServiceConstants.ACTIVE_FLAG, 1); // 1
            // denotes active rule
            arlRuleId = (ArrayList) hibQuery.list();
            iRuleIdList = arlRuleId.size();
            LOG.debug("Size of RuleId --------->" + iRuleIdList);
            for (int index = 0; index < iRuleIdList; index++) {
                objGetKmDpdRulhis = (GetKmDpdRulhisHVO) arlRuleId.get(index);
                LOG.debug("******rule id---------->" + objGetKmDpdRulhis.getGetKmDpdFinrul().getGetKmDpdFinrulSeqId());
                lstRuleId.add(objGetKmDpdRulhis.getGetKmDpdFinrul().getGetKmDpdFinrulSeqId());
            }

        } catch (RMDDAOConnectionException ex) {
            throw new RMDRunTimeException(ex, ex.getErrorDetail().getErrorCode());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RMDDAOException(RMDServiceConstants.DAO_EXCEPTION_GET_RULES, e);
        } finally {
            releaseSession(objHibernateSession);
            objGetKmDpdRulhis = null;
            arlRuleId = null;
            lstRuleId = null;
        }
        LOG.debug("End of getRules method in RuleDAOImpl");
        return lstRuleId;
    }

    /**
     * This method is used to get Customer details of rule for the Parameter
     * passed
     * 
     * @param strModel
     *            , strFleet, strConfig, strUnitNumber, strLanguage
     * @return List of Customer
     * @throws RMDDAOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getCustomerList(String strModel, String strFleet, String[] strConfig, String strUnitNumber,
            String strLanguage) throws RMDDAOException {
        List arlCustomers = null;
        Session objHibernateSession = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;

        ElementVO objElementVO = null;

        try {
            arlCustomers = new ArrayList();
            objHibernateSession = getHibernateSession();

            String strQry;
            strQry = buildCustomerQuery(strModel, strFleet, strConfig, strUnitNumber, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Fleet values to the Query Parameters, if Fleet value
            // is not equal to ALL
            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {

                String[] tmpStrFleet = strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRFLEET, tmpStrFleet);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER, tmpStrUnitNumber);
            }

            // Setting the Model values to the Query Parameters, if Model value
            // is not equal to ALL
            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {

                String[] tmpStrModel = strModel.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRMODEL, tmpStrModel);
            }

            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGPARAM_PARAM + i, configParam);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE1_PARAM + i, configValue1);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE2_PARAM + i, configValue2);
                    }

                    i = i + 3;
                }

            }
            // Setting customer seq. id and customer Name
            List<Object[]> customerList = qryQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(customerList)) {
                for (Object[] customerDetails : customerList) {
                    // customer org id or customer name should not be null.
                    if (null != customerDetails[1] || null != customerDetails[2]) {
                        objElementVO = new ElementVO();
                        objElementVO.setId(RMDCommonUtility.convertObjectToString(customerDetails[0]));
                        objElementVO.setName(RMDCommonUtility.convertObjectToString(customerDetails[1]));
                        objElementVO.setCustomerSeqId(RMDCommonUtility.convertObjectToString(customerDetails[0]));
                        objElementVO.setCustomerFullName(RMDCommonUtility.convertObjectToString(customerDetails[2]));
                        arlCustomers.add(objElementVO);
                    }
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("Size of Customer Array List : " + arlCustomers.size());
        return arlCustomers;
    }

    /**
     * This method is used to get Model details of rule for the Parameter passed
     * 
     * @param strCustomer
     *            , strFleet, strConfig, strUnitNumber, strLanguage
     * @return List of Model
     * @throws RMDDAOException
     */
    @Override
    public List<ElementVO> getModelList(String strCustomer, String strFleet, String strConfig[], String strUnitNumber,
            String strFamily, String strLanguage, String strKEP) throws RMDDAOException {
        List<ElementVO> arlModels = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;
        LOG.debug("Start model list");
        try {
            arlModels = new ArrayList<ElementVO>();
            String strModelQry = null;
            if (null != strKEP && !("".equals(strKEP)))
                strModelQry = getKEPModelsQuery(strLanguage);
            objHibernateSession = getHibernateSession();

            String strQry;
            strQry = buildModelQuery(strCustomer, strFleet, strConfig, strUnitNumber, strFamily, strLanguage,
                    strModelQry);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer) && !(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER, tmpStrCustomer);
            }

            // Setting the Fleet values to the Query Parameters, if Fleet value
            // is not equal to ALL
            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {

                String[] tmpStrFleet = strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRFLEET, tmpStrFleet);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER, tmpStrUnitNumber);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
                if (strFamily.equalsIgnoreCase(RMDServiceConstants.CCA)) {
                    String[] tmpFamily = RMDServiceConstants.ACCCA_DCCCA.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    qryQuery.setParameterList(RMDServiceConstants.STRFAMILY, tmpFamily);
                } else {
                    qryQuery.setParameter(RMDServiceConstants.STRFAMILY, strFamily);
                }
            }
            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGPARAM_PARAM + i, configParam);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE1_PARAM + i, configValue1);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE2_PARAM + i, configValue2);
                    }

                    i = i + 3;
                }

            }

            qryQuery.setFetchSize(100);
            List<Object[]> modelList = qryQuery.list();

            // Setting model seq. id and model Name
            if (RMDCommonUtility.isCollectionNotEmpty(modelList)) {
                for (Object[] modelInfo : modelList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(modelInfo[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(modelInfo[1]));
                    if (null != strKEP && !("".equals(strKEP))) {
                        objElementVO.setFamily(RMDCommonUtility.convertObjectToString(modelInfo[2]));
                    } else {
                        objElementVO.setFamily("NA");
                    }
                    arlModels.add(objElementVO);
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
        	LOG.error(e, e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("End Model list");
        return arlModels;

    }

    /**
     * This method is used to get Fleet details of rule for the Parameter passed
     * 
     * @param strCustomer
     *            , strModel, strConfig, strUnitNumber, strLanguage
     * @return List of Fleet
     * @throws RMDDAOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getFleetList(String strCustomer, String strModel, String[] strConfig, String strUnitNumber,
            String strLanguage) throws RMDDAOException {
        List arlFleets = null;
        Session objHibernateSession = null;

        ElementVO objElementVO = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;
        LOG.debug("Start getFleetList() RuleDAOImpl");
        try {
            arlFleets = new ArrayList();
            objHibernateSession = getHibernateSession();

            String strQry;
            strQry = buildFleetQuery(strCustomer, strModel, strConfig, strUnitNumber, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer) && !(RMDServiceConstants.ALL.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER, tmpStrCustomer);
            }

            // Setting the Model values to the Query Parameters, if Model value
            // is not equal to ALL
            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {

                String[] tmpStrModel = strModel.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRMODEL, tmpStrModel);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER, tmpStrUnitNumber);
            }

            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGPARAM_PARAM + i, configParam);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE1_PARAM + i, configValue1);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE2_PARAM + i, configValue2);
                    }

                    i = i + 3;
                }

            }

            List<Object[]> fleetList = qryQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(fleetList)) {
                for (Object objFleet[] : fleetList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(objFleet[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(objFleet[1]));
                    arlFleets.add(objElementVO);
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("Size of Fleet Array List : " + arlFleets.size());
        LOG.debug("End getFleetList() RuleDAOImpl");
        return arlFleets;

    }

    /**
     * This method is used to get Asset details of rule for the Parameter passed
     * 
     * @param strCustomer
     *            , strFleet, strConfig, strModel, strLanguage
     * @return List of Assets
     * @throws RMDDAOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<AssetServiceVO> getAssetList(String strCustomer, String strModel, String[] strConfig, String strFleet,
            String strLanguage) throws RMDDAOException {
        List<AssetServiceVO> arlAsset = null;
        Session objHibernateSession = null;
        AssetServiceVO objAssetServiceVO = null;
        String configParam = null;
        String configValue1 = null;
        String configValue2 = null;

        try {
            LOG.debug("Inside try of getAssetList");
            arlAsset = new ArrayList<AssetServiceVO>();
            objHibernateSession = getHibernateSession();

            String strQry;
            strQry = buildAssetQuery(strCustomer, strModel, strConfig, strFleet, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer) && !(RMDServiceConstants.ALL.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER, tmpStrCustomer);
            }

            // Setting the Model values to the Query Parameters, if Model value
            // is not equal to ALL
            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {

                String[] tmpStrModel = strModel.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRMODEL, tmpStrModel);
            }

            // Setting the Fleet values to the Query Parameters, if Fleet value
            // is not equal to ALL
            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {

                String[] tmpStrFleet = strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRFLEET, tmpStrFleet);
            }

            // Setting the Config Param , values to the Query Parameters, if
            // Config function value is not equal to ALL
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                int i = 0;
                for (i = 0; i < strConfig.length; i++) {
                    configParam = strConfig[i];
                    configValue1 = strConfig[i + 2];
                    configValue2 = strConfig[i + 3];

                    if (strQry.toString().contains(RMDCommonConstants.CONFIGPARAM + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGPARAM_PARAM + i, configParam);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE1 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE1_PARAM + i, configValue1);
                    }
                    if (strQry.toString().contains(RMDCommonConstants.CONFIGVALUE2 + i)) {
                        qryQuery.setParameter(RMDCommonConstants.CONFIGVALUE2_PARAM + i, configValue2);
                    }

                    i = i + 3;
                }

            }

            List assetList = qryQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();

            if (null != assetList) {

                for (Object object : assetList) {
                    Map row = (Map) object;
                    objAssetServiceVO = new AssetServiceVO();
                    LOG.debug("Sequence id is ::::::::::" + row.get("GET_ASST_ASSET_SEQ_ID"));
                    String str = row.get("GET_ASST_ASSET_SEQ_ID").toString();
                    Long ln = Long.valueOf(str);
                    objAssetServiceVO.setAssetId(ln.longValue());
                    objAssetServiceVO.setStrAssetNumber(row.get("ASSET_NUMBER").toString());
                    objAssetServiceVO.setCustomerName(row.get("CUSTOMER_ID").toString());
                    arlAsset.add(objAssetServiceVO);
                }
            }
        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        LOG.debug("Size of Asset Array List : " + arlAsset.size());
        return arlAsset;

    }

    /**
     * This method is used to get Configuration details of rule for the
     * Parameter passed
     * 
     * @param strCustomer
     *            , strModel, strFleet, strUnitNumber, strLanguage
     * @return List of Configuration
     * @throws RMDDAOException
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List getConfigurationList(String strCustomer, String strModel, String strFleet, String strUnitNumber,
            String strFamily, String strLanguage) throws RMDDAOException {
        List arlConfig = null;
        Session objHibernateSession = null;
        ElementVO objElementVO = null;

        try {
            arlConfig = new ArrayList();
            objHibernateSession = getHibernateSession();

            String strQry;
            strQry = buildConfigurationQuery(strCustomer, strModel, strFleet, strUnitNumber, strFamily, strLanguage);

            Query qryQuery = objHibernateSession.createSQLQuery(strQry);

            // Setting the Customer values to the Query Parameters, if Customer
            // value is not equal to ALL
            if ((null != strCustomer) && !(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                String[] tmpStrCustomer = strCustomer.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRCUSTOMER, tmpStrCustomer);
            }

            // Setting the Model values to the Query Parameters, if Model value
            // is not equal to ALL
            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {

                String[] tmpStrModel = strModel.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRMODEL, tmpStrModel);
            }

            // Setting the Fleet values to the Query Parameters, if Fleet value
            // is not equal to ALL
            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {

                String[] tmpStrFleet = strFleet.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRFLEET, tmpStrFleet);
            }

            // Setting the Asset / UnitNumber values to the Query Parameters, if
            // Asset / UnitNumber value is not equal to ALL
            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {

                String[] tmpStrUnitNumber = strUnitNumber.split(RMDCommonConstants.COMMMA_SEPARATOR);
                qryQuery.setParameterList(RMDCommonConstants.STRUNITNUMBER, tmpStrUnitNumber);
            }
            if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
                if (strFamily.equalsIgnoreCase(RMDServiceConstants.CCA)) {
                    String[] tmpFamily = RMDServiceConstants.ACCCA_DCCCA.split(RMDCommonConstants.COMMMA_SEPARATOR);
                    qryQuery.setParameterList(RMDServiceConstants.STRFAMILY, tmpFamily);
                } else {
                    qryQuery.setParameter(RMDServiceConstants.STRFAMILY, strFamily);
                }
            }
            List<Object[]> configList = qryQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(configList)) {
                for (Object objConfig[] : configList) {
                    objElementVO = new ElementVO();
                    objElementVO.setId(RMDCommonUtility.convertObjectToString(objConfig[0]));
                    objElementVO.setName(RMDCommonUtility.convertObjectToString(objConfig[1]));
                    arlConfig.add(objElementVO);
                }
            }

        }

        catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FUNCTION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objHibernateSession);
        }
        return arlConfig;

    }

    public void configCriteraNative(String[] strConfig, StringBuilder strQry) {

        String configFunction = null;
        int i = 0;
        for (i = 0; i < strConfig.length; i++) {
            configFunction = strConfig[i + 1];
            if (i > 0) {
                strQry.append(" AND EXISTS (SELECT 'X' FROM GETS_RMD_MASTER_BOM WHERE");
                strQry.append(" CONFIG_ITEM=:configParam" + i);
            } else {
                strQry.append(" AND (CONFIG_ITEM=:configParam" + i);
            }
            if (configFunction.equals(RMDCommonConstants.EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION =:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >=:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION <=:configValue1" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION <:configValue1" + i + " AND CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION >=:configValue1" + i + " AND CURRENT_VERSION<:configValue2" + i + ")");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_AND_LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >:configValue1" + i + " AND CURRENT_VERSION<=:configValue2" + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.GREATER_THAN_EQUAL_AND_LESS_THAN_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION >=configValue1" + i + " AND CURRENT_VERSION<=:configValue2" + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION <:configValue1" + i + " OR CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION <=:configValue1" + i + " OR CURRENT_VERSION>:configValue2" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_OR_GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION <:configValue1" + i + " OR CURRENT_VERSION>=:configValue2" + i + ")");
            }
            if (configFunction.equals(RMDCommonConstants.LESS_THAN_EQUAL_OR_GREATER_THAN_EQUAL_SYMBOL)) {
                strQry.append(
                        " AND CURRENT_VERSION <=:configValue1" + i + " OR CURRENT_VERSION>=:configValue2" + i + ") ");
            }
            if (configFunction.equals(RMDCommonConstants.NOT_EQUAL_SYMBOL)) {
                strQry.append(" AND CURRENT_VERSION !=:configValue1" + i + ")");
            }
            if (configFunction.equals(RMDCommonConstants.MULTI_OR)) {
                strQry.append(
                        " AND (CURRENT_VERSION =:configValue1" + i + " OR CURRENT_VERSION =:configValue2" + i + "))");
            }
            i = i + 3;
        }

    }

    /**
     * The method is used to get rule parameters like ruletype, status etc..
     * through LOOKUP table
     * 
     * @param strListname
     *            ,Language
     * @return List of ElementVO
     * @throws RMDServiceException
     */
    @Override
    public List<ElementVO> getRuleLookupValues(String strListName, String strLanguage) throws RMDDAOException {
        ArrayList<ElementVO> lstLookupValues = null;
        try {
            lstLookupValues = (ArrayList) getLookupListValues(strListName, strLanguage);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleDAOImpl getVersionLookupValues()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return lstLookupValues;
    }

    /**
     * The method is used to get rule parameters like ruletype, status etc..
     * through LOOKUP table
     * 
     * @param strListname
     * @return List of ElementVO
     * @throws RMDServiceException
     */
    @Override
    public String getRuleLookupValues(String strListName) throws RMDDAOException {
        String strLookupValues = null;
        try {
            strLookupValues = getLookupValue(strListName);
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strListName), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            LOG.error("Unexpected Error occured in RuleDAOImpl getVersionLookupValues()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strListName), e,
                    RMDCommonConstants.MAJOR_ERROR);
        }
        return strLookupValues;
    }

    /**
     * The method is used to get all the createdBy values from DataBase using
     * the Input given Input parameters
     * 
     * @param uriParam
     * @return List of ElementVO
     * @throws RMDServiceException
     */
    @Override
    public List<ElementVO> getRuleCreatedBy(String strLanguage) throws RMDDAOException {

        List<ElementVO> arlRuleCreatedByList = new ArrayList<ElementVO>();
        ElementVO objRuleCreatedByList = null;
        Session session = null;

        Query query = null;
        StringBuilder stringBuilder = null;

        try {

            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append(" SELECT DISTINCT CREATED_BY FROM GETS_TOOLS.GETS_TOOL_DPD_FINRUL");
            stringBuilder.append(" ORDER BY CREATED_BY ASC");
            query = session.createSQLQuery(stringBuilder.toString());
            List<Object> arlCreatedList = query.list();
            for (Iterator iterator = arlCreatedList.iterator(); iterator.hasNext();) {
                String creator = (String) iterator.next();
                objRuleCreatedByList = new ElementVO();
                objRuleCreatedByList.setId(creator);
                objRuleCreatedByList.setName(creator);
                arlRuleCreatedByList.add(objRuleCreatedByList);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRuleCreatedByList;
    }

    /**
     * The method is used to get all the LastUpdatedBy values from DataBase
     * using the Input given Input parameters
     * 
     * @param uriParam
     * @return List of ElementVO
     * @throws RMDServiceException
     */
    @Override
    public List<ElementVO> getRuleLastUpdatedBy(String strLanguage) throws RMDDAOException {

        List<ElementVO> arlRuleLastUpdatedByList = new ArrayList<ElementVO>();
        ElementVO objRuleLastUpdatedByList = null;
        Session session = null;
        StringBuilder stringBuilder = null;
        Query query = null;
        try {

            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append(" SELECT DISTINCT LAST_UPDATED_BY FROM GETS_TOOLS.GETS_TOOL_DPD_FINRUL");
            stringBuilder.append(" ORDER BY LAST_UPDATED_BY ASC");
            query = session.createSQLQuery(stringBuilder.toString());
            List<Object> arlLastUpdated = query.list();

            if (arlLastUpdated != null && !arlLastUpdated.isEmpty()) {

                for (Iterator iterator = arlLastUpdated.iterator(); iterator.hasNext();) {
                    String updator = (String) iterator.next();
                    objRuleLastUpdatedByList = new ElementVO();
                    objRuleLastUpdatedByList.setId(updator);
                    objRuleLastUpdatedByList.setName(updator);
                    arlRuleLastUpdatedByList.add(objRuleLastUpdatedByList);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRuleLastUpdatedByList;
    }

    /**
     * This method will return the dynamic SQL built based on inputs, which will
     * get the customers. In the SQL the table will be joined, only if
     * corresponding input is provided. i.e It will join model table only if
     * models are provided in the input parameters.
     * 
     * @param String
     *            strModel, String strFleet, String[] strConfig, String
     *            strUnitNumber, String strLanguage
     * @return String
     */
    private String buildCustomerQuery(String strModel, String strFleet, String[] strConfig, String strUnitNumber,
            String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();

        // Basic query to get the customers, when the values of
        // model,fleet,config,unitnumber are All
        // i.e Will return all the customers
        strQuery = strQuery.append(RMDServiceConstants.CUSTOMER_SELECT).append(RMDServiceConstants.FROM)
                .append(RMDServiceConstants.CUSTOMER_TABLE_NAME);

        if (((null != strModel) && (!(RMDServiceConstants.ALL.equals(strModel))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL.equals(strConfig[0]))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL.equals(strUnitNumber))))
                || ((null != strFleet) && (!(RMDServiceConstants.ALL.equals(strFleet))))) {
            // Need fleet table to get the customers , so by default fleet table
            // and join condition is added
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);

            // Since fleet table is added by default, adding only IN operator
            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.FLEET_NUMBERS_IN);

            }

            // If Asset are provided, we are adding the Asset table and Asset to
            // Fleet join condition
            // Fleet table is added by default, so we are not adding here in
            // this block
            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);

            }

            // If Models are provided, we are adding the Model table and Asset
            // to Model join condition
            // Fleet table is added by default, so we are not adding here in
            // this block
            if ((null != strModel) && (!RMDServiceConstants.ALL.equals(strModel))) {

                // This block is to check, whether the Asset Table is added in
                // the previous step or not.
                // It will add Asset Table only if no Asset numbers is ALL
                if ((null == strUnitNumber) || (RMDServiceConstants.ALL.equals(strUnitNumber))) {
                    strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME);
                }
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MODEL_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_MODEL_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.MODEL_NUMBERS_IN);

            }

            // If Config Parameters are provided, we are adding the Master_BOM
            // table, Vehicle_Config table, Asset to Vehicle join condition
            // and Vehicle config to Master BOM table.
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                // This block is to check, whether the Asset Table is added in
                // the previous step or not.
                // It will add Asset Table only if no Asset numbers is ALL
                if (((null == strUnitNumber) || (RMDServiceConstants.ALL.equals(strUnitNumber)))
                        && ((null == strModel) || (RMDServiceConstants.ALL.equals(strModel)))) {
                    strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME);
                }
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuilder buffer = new StringBuilder();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }

            // Constructing the query
			strQuery = strQuery.append(strTable).append(strTableCondition)
					.append(strWherClause);
        }

		strQuery = strQuery.append(RMDServiceConstants.ORDER_BY_ORG_ID_ASC);
        return strQuery.toString();
    }

    /**
     * This method will return the dynamic SQL built based on inputs, which will
     * get the Configurations. In the SQL the table will be joined, only if
     * corresponding input is provided. i.e It will join model table only if
     * models are provided in the input parameters.
     * 
     * @param String
     *            strModel, String strFleet, String[] strConfig, String
     *            strUnitNumber, String strLanguage
     * @return String
     */
    private String buildConfigurationQuery(String strCustomer, String strModel, String strFleet, String strUnitNumber,
            String strFamily, String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();
        boolean hasOtherParams = false;
        strQuery = strQuery.append(RMDServiceConstants.CONFIG_SELECT).append(RMDServiceConstants.FROM)
                .append(RMDServiceConstants.MASTERBOM_TABLE_NAME);
        if (((null != strCustomer) && (!(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))))
                || ((null != strFleet) && (!(RMDServiceConstants.ALL.equals(strFleet))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL.equals(strUnitNumber))))
                || ((null != strModel) && (!(RMDServiceConstants.ALL.equals(strModel))))) {
            hasOtherParams = true;
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME)
                    .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.VEHCFG_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN).append(RMDServiceConstants.AND)
                    .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN);

            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MODEL_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_MODEL_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.MODEL_NUMBERS_IN);
            }

            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.FLEET_NUMBERS_IN);
            }

            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);
            }

            if ((null != strCustomer) && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                if ((null == strFleet) || (RMDServiceConstants.ALL.equals(strFleet))) {
                    strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME);
                }
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CUSTOMER_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }
        }
        // building query if family passed as one of the parameter.
        if (!hasOtherParams && !RMDCommonUtility.isNullOrEmpty(strFamily)) {
            strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CTRL_CONFIG_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.CTRL_CONFIG_TO_MASTERBOM_JOIN);
            strWherClause = strWherClause.append(RMDServiceConstants.AND).append(RMDServiceConstants.CONFIG_FAMILY_IN);
        } else if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CTRL_CONFIG_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                    .append(RMDServiceConstants.CTRL_CONFIG_TO_MASTERBOM_JOIN);
            strWherClause = strWherClause.append(RMDServiceConstants.AND).append(RMDServiceConstants.CONFIG_FAMILY_IN);
        }
        if (RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strWherClause.toString())) {
            strQuery = strQuery.append(strTable).append(strTableCondition).append(RMDServiceConstants.WHERE);
            strQuery = strQuery.append(RMDServiceConstants.BOM_STATUS_Y);
            strQuery = strQuery.append(") mm ");
            strQuery = strQuery.append(RMDServiceConstants.ORDER_BY_CONFIG_ITEMS_ASC);
        } else {
            strQuery = strQuery.append(strTable).append(strTableCondition).append(strWherClause);
            strQuery = strQuery.append(RMDServiceConstants.AND).append(RMDServiceConstants.BOM_STATUS_Y);
            strQuery = strQuery.append(") mm ");
            strQuery = strQuery.append(RMDServiceConstants.ORDER_BY_CONFIG_ITEMS_ASC);
        }

        return strQuery.toString();
    }

    /**
     * This method will return the dynamic SQL built based on inputs, which will
     * get the Asset / Unit Numbers. In the SQL the table will be joined, only
     * if corresponding input is provided. i.e It will join model table only if
     * models are provided in the input parameters.
     * 
     * @param String
     *            strModel, String strFleet, String[] strConfig, String
     *            strUnitNumber, String strLanguage
     * @return String
     */
    private String buildAssetQuery(String strCustomer, String strModel, String[] strConfig, String strFleet,
            String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();

        if (((null != strCustomer) && (!(RMDServiceConstants.ALL.equals(strCustomer))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL.equals(strConfig[0]))))
                || ((null != strFleet) && (!(RMDServiceConstants.ALL.equals(strFleet))))
                || ((null != strModel) && (!(RMDServiceConstants.ALL.equals(strModel))))) {

            strQuery = strQuery.append(RMDServiceConstants.ASSET_SELECT).append(RMDServiceConstants.FROM)
                    .append(RMDServiceConstants.ASSET_TABLE_NAME);
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CUSTOMER_TABLE_NAME)
                    .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME)
                    .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.SITE_PART_TABLE_NAME);

            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);
            strTableCondition.append(RMDServiceConstants.AND).append(RMDServiceConstants.ASSET_TO_FLEET_JOIN)
                    .append(RMDServiceConstants.AND).append(RMDServiceConstants.ASSET_TO_SITE_PART_JOIN);

            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {

                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.FLEET_NUMBERS_IN);
            }

            if ((null != strCustomer) && (!RMDServiceConstants.ALL.equals(strCustomer))) {

                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }

            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MODEL_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_MODEL_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.MODEL_NUMBERS_IN);
            }

            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuilder buffer = new StringBuilder();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }
            strQuery = strQuery.append(strTable).append(strTableCondition).append(strWherClause);
        } else {

            strQuery = strQuery.append(RMDServiceConstants.ASSET_SELECT).append(RMDServiceConstants.FROM)
                    .append(RMDServiceConstants.CUSTOMER_TABLE_NAME).append(RMDServiceConstants.COMMA)
                    .append(RMDServiceConstants.FLEET_TABLE_NAME).append(RMDServiceConstants.COMMA)
                    .append(RMDServiceConstants.ASSET_TABLE_NAME).append(RMDServiceConstants.COMMA)
                    .append(RMDServiceConstants.SITE_PART_TABLE_NAME).append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN).append(RMDServiceConstants.AND)
                    .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN).append(RMDServiceConstants.AND)
                    .append(RMDServiceConstants.ASSET_TO_SITE_PART_JOIN);

        }

        return strQuery.toString();
    }

    /**
     * This method will return the dynamic SQL built based on inputs, which will
     * get the Fleet. In the SQL the table will be joined, only if corresponding
     * input is provided. i.e It will join model table only if models are
     * provided in the input parameters.
     * 
     * @param String
     *            strModel, String strFleet, String[] strConfig, String
     *            strUnitNumber, String strLanguage
     * @return String
     */
    private String buildFleetQuery(String strCustomer, String strModel, String[] strConfig, String strUnitNumber,
            String strLanguage) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();

        strQuery = strQuery.append(RMDServiceConstants.FLEET_SELECT).append(RMDServiceConstants.FROM)
                .append(RMDServiceConstants.FLEET_TABLE_NAME);

        if (((null != strCustomer) && (!(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL.equals(strConfig[0]))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL.equals(strUnitNumber))))
                || ((null != strModel) && (!(RMDServiceConstants.ALL.equals(strModel))))) {
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN);

            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {

                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);
            }

            if ((null != strCustomer) && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CUSTOMER_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }

            if ((null != strModel) && !(RMDServiceConstants.ALL.equals(strModel))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MODEL_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_MODEL_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.MODEL_NUMBERS_IN);
            }

            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuilder buffer = new StringBuilder();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }
            strQuery = strQuery.append(strTable).append(strTableCondition).append(strWherClause);
        }

        return strQuery.toString();
    }

    private String buildModelQuery(String strCustomer, String strFleet, String[] strConfig, String strUnitNumber,
            String strFamily, String strLanguage, String strKEPModelQuery) {

        StringBuilder strWherClause = new StringBuilder();
        StringBuilder strTableCondition = new StringBuilder();
        StringBuilder strTable = new StringBuilder();
        StringBuilder strQuery = new StringBuilder();
        boolean hasOtherParams = false;
        strQuery = strQuery.append(RMDServiceConstants.MODEL_SELECT).append(RMDServiceConstants.FROM)
                .append(RMDServiceConstants.MODEL_TABLE_NAME);

        if (((null != strCustomer) && (!(RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))))
                || ((null != strConfig) && (!(RMDServiceConstants.ALL.equals(strConfig[0]))))
                || ((null != strUnitNumber) && (!(RMDServiceConstants.ALL.equals(strUnitNumber))))
                || ((null != strFleet) && (!(RMDServiceConstants.ALL.equals(strFleet)))))

        {
            hasOtherParams = true;
            strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.ASSET_TABLE_NAME);
            strTableCondition = strTableCondition.append(RMDServiceConstants.WHERE)
                    .append(RMDServiceConstants.ASSET_TO_MODEL_JOIN);

            if ((null != strUnitNumber) && !(RMDServiceConstants.ALL.equals(strUnitNumber))) {
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_NUMBERS_IN);
            }

            if ((null != strFleet) && !(RMDServiceConstants.ALL.equals(strFleet))) {
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.FLEET_NUMBERS_IN);
            }

            if ((null != strCustomer) && (!RMDServiceConstants.ALL_CUSTOMER.equals(strCustomer))) {
                if ((null == strFleet) || (RMDServiceConstants.ALL.equals(strFleet))) {
                    strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.FLEET_TABLE_NAME);
                }
                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.CUSTOMER_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_FLEET_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_TO_FLEET_JOIN);
                strWherClause = strWherClause.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.CUSTOMER_NAMES_IN);
            }
            if ((null != strConfig) && !(RMDServiceConstants.ALL.equals(strConfig[0]))) {

                strTable = strTable.append(RMDServiceConstants.COMMA).append(RMDServiceConstants.MASTERBOM_TABLE_NAME)
                        .append(RMDServiceConstants.COMMA).append(RMDServiceConstants.VEHCFG_TABLE_NAME);
                strTableCondition = strTableCondition.append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.VEHCFG_TO_MASTERBOM_JOIN).append(RMDServiceConstants.AND)
                        .append(RMDServiceConstants.ASSET_TO_VEHCFG_JOIN);
                StringBuilder buffer = new StringBuilder();
                configCriteraNative(strConfig, buffer);
                strWherClause = strWherClause.append(buffer);
            }
            strQuery = strQuery.append(strTable).append(strTableCondition).append(strWherClause);
        }
        if (hasOtherParams && !RMDCommonUtility.isNullOrEmpty(strFamily)) {
            if (strQuery.toString().contains(RMDServiceConstants.WHERE)) {
                strQuery = strQuery.append(RMDServiceConstants.AND).append(RMDServiceConstants.FAMILY_IN);
            }
        } else if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
            strQuery = strQuery.append(RMDServiceConstants.WHERE).append(RMDServiceConstants.FAMILY_IN);
        }

        if (null != strKEPModelQuery && !("").equals(strKEPModelQuery)) {
            if (strQuery.toString().contains(RMDServiceConstants.WHERE)) {
                strQuery.append(" AND ");
            } else {
                strQuery.append(" 	WHERE ");
            }
            strQuery.append("  FAMILY in (").append(strKEPModelQuery).append(")");
        }
        strQuery = strQuery.append(RMDServiceConstants.ORDER_BY_MODEL_NAME_ASC);

        return strQuery.toString();
    }

    /**
     * @Author:iGATE
     * @param: strQuery,hibernateSession
     * @return seqID
     * @throws @Description:This
     *             method is used to generate the Sequence Id
     */

    private String getSequenceId(String strQuery, Session hibernateSession) {
        String strSeqId = null;
        Query simRuleQuery;
        BigDecimal bdSeqId;
        try {

            if (!RMDCommonUtility.isNullOrEmpty(strQuery) && !RMDCommonUtility.checkNull(hibernateSession)) {
                simRuleQuery = hibernateSession.createSQLQuery(strQuery);
                List<BigDecimal> arlSimplRuleSeq = simRuleQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(arlSimplRuleSeq)) {
                    bdSeqId = arlSimplRuleSeq.get(0);
                    strSeqId = bdSeqId.toString();
                }
            }
            return strSeqId;
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        }
    }

    /**
     * @Author:iGATE
     * @param: strQuery,hibernateSession
     * @return seqID
     * @throws @Description:This
     *             method is used to save the Rule Definition
     */
    private void saveRuleDefinition(FinalRuleServiceVO finalRuleServiceVO, Session objHibernateSession) {
        String strFinRulObjid = RMDCommonConstants.EMPTY_STRING;
        String strFamily = RMDCommonConstants.EMPTY_STRING;
        String strUserName = RMDCommonConstants.EMPTY_STRING;
        String strRuleDefObjid = null;
        String strServiceId = RMDCommonConstants.EMPTY_STRING;
        String countQuery;
        String strModelId;
        String strValue2 = RMDCommonConstants.EMPTY_STRING;
        Query queryRuleDef = null;
        int intRecomObjid = 0;
        int totalModels = 0;
        int customerObjid = 0;
        List arlModelCount = null;
        try {
            /** ****************Start RuleDefinition ********************** */
            LOG.debug("START RULE DEFINITION #saveFinalRule()");
            strFinRulObjid = finalRuleServiceVO.getStrFinalRuleID();
            strFamily = finalRuleServiceVO.getStrFamily();
            strUserName = finalRuleServiceVO.getStrUserName();
            if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO.getArlRuleDefinition())) {
                /*
                 * This below lines are used for iterate finalRuleServiceVO and
                 * get the rule definition details
                 */
                for (RuleDefinitionServiceVO objServiceDefinition : finalRuleServiceVO.getArlRuleDefinition()) {

                    /** only Diagnostic rule will have RX */
                    if (!RMDCommonUtility.isNullOrEmpty(finalRuleServiceVO.getStrRuleType())
                            && RMDServiceConstants.DIAGNOSTIC_RULE_TYPE
                                    .equalsIgnoreCase(finalRuleServiceVO.getStrRuleType())) {
                        // to retrieve the RX OBJID: and insert them in the
                        // table GETS_TOOL_DPD_RULEDEF
                        if (!RMDCommonUtility.isNullOrEmpty(objServiceDefinition.getStrRecommendation())
                                && !RMDServiceConstants.DRAFTSTATUS
                                        .equalsIgnoreCase(finalRuleServiceVO.getStrCompleteStatus())) {

                            queryRuleDef = objHibernateSession
                                    .createSQLQuery(RMDServiceConstants.QueryConstants.RECOM_OBJID_SQL);
                            queryRuleDef.setParameter(RMDServiceConstants.TITLE,
                                    objServiceDefinition.getStrRecommendation());
                            List arlRecomObjid = queryRuleDef.list();
                            if (RMDCommonUtility.isCollectionNotEmpty(arlRecomObjid)) {
                                BigDecimal objBigDecimal = (BigDecimal) arlRecomObjid.get(0);
                                intRecomObjid = objBigDecimal.intValue();
                            } else {
                                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.RECOM_NOT_VALID);
                                throw new RMDDAOException(errorCode, new String[] {},
                                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                                finalRuleServiceVO.getStrLanguage()),
                                        null, RMDCommonConstants.MINOR_ERROR);
                            }
                        } else if (!RMDCommonUtility.isNullOrEmpty(objServiceDefinition.getStrRecommendation())
                                && RMDServiceConstants.DRAFTSTATUS
                                        .equalsIgnoreCase(finalRuleServiceVO.getStrCompleteStatus())) {

                            queryRuleDef = objHibernateSession
                                    .createSQLQuery(RMDServiceConstants.QueryConstants.RECOM_OBJID_SQL);
                            queryRuleDef.setParameter(RMDServiceConstants.TITLE,
                                    objServiceDefinition.getStrRecommendation());
                            List arlRecomObjid = queryRuleDef.list();
                            if (RMDCommonUtility.isCollectionNotEmpty(arlRecomObjid)) {
                                BigDecimal objBigDecimal = (BigDecimal) arlRecomObjid.get(0);
                                intRecomObjid = objBigDecimal.intValue();
                            } else {
                                String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.RECOM_NOT_VALID);
                                throw new RMDDAOException(errorCode, new String[] {},
                                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                                finalRuleServiceVO.getStrLanguage()),
                                        null, RMDCommonConstants.MINOR_ERROR);
                            }
                        }

                    }
                    // to retrieve the service id call lookup table, by default
                    // the service is "EOA"
                    List<ElementVO> lstLookupServiceId = getLookupList(RMDServiceConstants.SERVICES_LIST_NAME,
                            finalRuleServiceVO.getStrLanguage(), RMDCommonConstants.EOA_APP);
                    if (RMDCommonUtility.isCollectionNotEmpty(lstLookupServiceId)) {
                        ElementVO objElementVO = lstLookupServiceId.get(0);
                        strServiceId = objElementVO.getId();
                    }

                    // to get the unique key of GETS_TOOL_DPD_RULEDEF table
                    strRuleDefObjid = getSequenceId(RMDServiceConstants.QueryConstants.RULEDEF_SEQ_SQL,
                            objHibernateSession);

                    // inserting rule definition details to the table
                    // GETS_TOOL_DPD_RULEDEF
                    queryRuleDef = objHibernateSession
                            .createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_RULDEF_BUILDER.toString());
                    queryRuleDef.setParameter(RMDServiceConstants.RULEDEF_OBJID, strRuleDefObjid);
                    queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                    queryRuleDef.setParameter(RMDServiceConstants.MESSAGE, objServiceDefinition.getStrMessage());
                    queryRuleDef.setParameter(RMDServiceConstants.RULE_TYPE, finalRuleServiceVO.getStrRuleType());
                    queryRuleDef.setParameter(RMDServiceConstants.FINRULE_OBJID, strFinRulObjid);
                    queryRuleDef.setParameter(RMDServiceConstants.RECOM_OBJID, intRecomObjid);
                    queryRuleDef.setParameter(RMDServiceConstants.SERVICE_OBJID, strServiceId);
                    queryRuleDef.executeUpdate();
                    // in the existing EOA code saving Model is done only only
                    // if
                    List<RuleDefModelServiceVO> arlRuleDefModel = objServiceDefinition.getArlRuleDefModel();
                    // Need to insert models only if family is either of these
                    // RMD Equipment,OIL,CCA,NX Equipment. These values are
                    // retrieved from
                    // the lookup table
                    if (strFamily.equalsIgnoreCase(RMDServiceConstants.STR_CCA)) {
                        countQuery = RMDServiceConstants.QueryConstants.ACCCA_DCCCA_MODELS_SQL;
                    } else {
                        countQuery = RMDServiceConstants.QueryConstants.TOTAL_MODELS_SQL;
                    }
                    queryRuleDef = objHibernateSession.createSQLQuery(countQuery);
                    arlModelCount = queryRuleDef.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlModelCount)) {
                        BigDecimal objBigDecimal = (BigDecimal) arlModelCount.get(0);
                        totalModels = objBigDecimal.intValue();
                    }
                    // totalModels--> total available models
                    // if the all models are selected, then rules wont
                    // be mapped to the models
                    if (!arlRuleDefModel.isEmpty() && totalModels != arlRuleDefModel.size()) {
                        for (RuleDefModelServiceVO objRuleDefModelServiceVO : arlRuleDefModel) {
                            strModelId = objRuleDefModelServiceVO.getModelId();
                            queryRuleDef = objHibernateSession.createSQLQuery(
                                    RMDServiceConstants.QueryConstants.INSERT_DPD_MODEL_BUILDER.toString());
                            queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                            queryRuleDef.setParameter(RMDServiceConstants.MODEL_OBJID, strModelId);
                            queryRuleDef.setParameter(RMDServiceConstants.MODEL2RULEDEF_OBJID, strRuleDefObjid);
                            queryRuleDef.executeUpdate();
                        }
                    }
                    // data save for customer table
                    ArrayList<RuleDefCustomerServiceVO> arlRuleDefCustomer = objServiceDefinition
                            .getArlRuleDefCustomer();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlRuleDefCustomer)) {
                        for (RuleDefCustomerServiceVO ruleDefCustomerServiceVO : arlRuleDefCustomer) {
                            queryRuleDef = objHibernateSession
                                    .createSQLQuery(RMDServiceConstants.QueryConstants.CUSTOMER_SEQ_SQL);
                            BigDecimal objBigDecimal = (BigDecimal) queryRuleDef.list().get(0);
                            customerObjid = objBigDecimal.intValue();
                            // inserting customer details to the table
                            // GETS_TOOL_DPD_CUST
                            queryRuleDef = objHibernateSession.createSQLQuery(
                                    RMDServiceConstants.QueryConstants.INSERT_DPD_CUST_BUILDER.toString());
                            queryRuleDef.setParameter(RMDServiceConstants.CUST_OBJID, customerObjid);
                            queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                            queryRuleDef.setParameter(RMDServiceConstants.CUST2BUS_OBJID,
                                    ruleDefCustomerServiceVO.getStrCustomerID());
                            queryRuleDef.setParameter(RMDServiceConstants.CUST2RULDEF_OBJID, strRuleDefObjid);
                            queryRuleDef.setParameter(RMDServiceConstants.EXCLUDE,
                                    objServiceDefinition.getStrExclude());
                            List<ElementVO> arlRuleDefFleet = ruleDefCustomerServiceVO.getArlRuleDefFleet();
                            queryRuleDef.executeUpdate();

                            if (RMDCommonUtility.isCollectionNotEmpty(arlRuleDefFleet)) {
                                for (ElementVO objElementVO : arlRuleDefFleet) {
                                    // inserting fleet details to the table
                                    // GETS_TOOL_DPD_FLEET
                                    queryRuleDef = objHibernateSession.createSQLQuery(
                                            RMDServiceConstants.QueryConstants.INSERT_DPD_FLEET_BUILDER.toString());
                                    queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                                    queryRuleDef.setParameter(RMDServiceConstants.CUST_OBJID, customerObjid);
                                    queryRuleDef.setParameter(RMDServiceConstants.FLEET_OBJID, objElementVO.getId());
                                    queryRuleDef.setParameter(RMDServiceConstants.EXCLUDE,
                                            objServiceDefinition.getStrExclude());
                                    queryRuleDef.executeUpdate();
                                }
                            }
                        }
                    }

                    // Saving the configuration informations
                    List<RuleDefConfigServiceVO> arlRuleDefConfigs = objServiceDefinition.getArlRuleDefConfig();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlRuleDefConfigs)) {
                        for (RuleDefConfigServiceVO ruleDefConfigServiceVO : arlRuleDefConfigs) {
                            // getting Function Id from the table
                            // GETS_TOOL_DPD_SIMFCN
                            queryRuleDef = objHibernateSession
                                    .createSQLQuery(RMDServiceConstants.QueryConstants.SIMFCN_SQL);
                            queryRuleDef.setParameter(RMDServiceConstants.SIMFUNCTION,
                                    ruleDefConfigServiceVO.getStrFunction());
                            BigDecimal objBigDecimal = (BigDecimal) queryRuleDef.list().get(0);

                            // inserting configuration details to the table
                            // GETS_TOOL_DPD_CFGFEA
                            queryRuleDef = objHibernateSession.createSQLQuery(
                                    RMDServiceConstants.QueryConstants.INSERT_DPD_CFGFEA_BUILDER.toString());
                            queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                            queryRuleDef.setParameter(RMDServiceConstants.FUNCTION_ID, objBigDecimal.toString());
                            queryRuleDef.setParameter(RMDServiceConstants.CONFIGNAME,
                                    ruleDefConfigServiceVO.getStrConfiguration());
                            queryRuleDef.setParameter(RMDServiceConstants.CFGFEA2RULDEF, strRuleDefObjid);
                            queryRuleDef.setParameter(RMDServiceConstants.VALUE_1,
                                    ruleDefConfigServiceVO.getStrValue1());
                            if (!RMDCommonUtility.isNullOrEmpty(ruleDefConfigServiceVO.getStrValue2())) {
                                strValue2 = ruleDefConfigServiceVO.getStrValue2();
                            }
                            queryRuleDef.setParameter(RMDServiceConstants.VALUE_2, strValue2);
                            queryRuleDef.executeUpdate();
                            strValue2 = RMDCommonConstants.EMPTY_STRING;
                        }
                    }

                    // To insert health factor and perfcheckid in to
                    // GETS_TOOL_LHR_PERFCHK2RULEDEF
                    if (null != finalRuleServiceVO.getStrRuleType() && finalRuleServiceVO.getStrRuleType()
                            .equalsIgnoreCase(RMDServiceConstants.HEALTH_RULE_TYPE)) {

                        queryRuleDef = objHibernateSession.createSQLQuery(
                                RMDServiceConstants.QueryConstants.INSERT_LHR_PERFCHK2RULEDEF.toString());
                        queryRuleDef.setParameter(RMDServiceConstants.USERNAME, strUserName);
                        queryRuleDef.setParameter(RMDServiceConstants.HEALTH_FACTOR,
                                objServiceDefinition.getHealthFactor());
                        queryRuleDef.setParameter(RMDServiceConstants.PERF_CHECK_ID,
                                objServiceDefinition.getPerfHealthCheckID());
                        queryRuleDef.setParameter(RMDServiceConstants.RULEDEF_OBJID, strRuleDefObjid);
                        queryRuleDef.executeUpdate();

                    }
                }
            }
            LOG.debug("END RULE DEFINITION #saveFinalRule()");
            /** ****************End RuleDefinition ********************** */
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        } catch (RMDDAOException objRMDDAOException) {
            throw objRMDDAOException;
        } finally {
            queryRuleDef = null;
        }
    }

    /**
     * @Author:iGATE
     * @param: FinalRuleServiceVO,hibernateSession
     * @return seqID
     * @throws @Description:This
     *             method is used to save the Rule History
     */
    private void saveRuleHistory(FinalRuleServiceVO finalRuleServiceVO, Session objHibernateSession) {
        String strCompletionStatus = RMDCommonConstants.EMPTY_STRING;
        Query ruleHistQuery = null;
        Query ruleCountQuery = null;
        String strOldFinalRule = null;
        int intVersion = 0;
        int intPreviousVersion = 0;
        int intPreviousCompleted = -1;
        String strOriginalId = RMDCommonConstants.EMPTY_STRING;
        String strFinRulObjid = RMDCommonConstants.EMPTY_STRING;
        String strUserName = RMDCommonConstants.EMPTY_STRING;
        String strRuleHistSeq = null;
        String strRevisionHistory = RMDCommonConstants.EMPTY_STRING;
        List<Object> lstRuleHist = null;
        try {
            /** SAVING RULE HISTORY STARTS */
            /** Logic to save the rulehistory details to EOA DB **/
            /**
             * if the completion status is "draft" or "No" then we need to
             * update the existing rule history record details such as
             * version,previous_version id, orginal_id, rulhis_seq id will
             * remains same, only rulhis2finrul column will be updated. if the
             * completion status is "Complete" or "Yes" then we need to insert a
             * new record to the history table. if the draft rule is edited and
             * saved with the completion status "Complete" or "Yes", then we
             * need to update record to the history table Details such as
             * version, previous_version id, orginal_id, rulhis_seq id will
             * remains same, only rulhis2finrul,complete column will be updated.
             * if a complete rule is edited and saved with the completion status
             * "Complete" or "Yes", then we need to insert a new record to the
             * history table Details such as version, previous_version id will
             * be incremented ,orginal_id, rulhis_seq id,rulhis2finrul,complete
             * column will be inserted with new value. * if the draft rule is
             * edited and saved with the completion status "Draft" , then we
             * need to update record to the history table Details such that
             * version, previous_version id, orginal_id, rulhis_seq id will
             * remains same, only rulhis2finrul,complete column will be updated.
             * * if a complete rule is edited and saved with the as Draft then
             * we need to insert a new record to the history table Details such
             * as version, previous_version id will be incremented ,orginal_id,
             * rulhis_seq id,rulhis2finrul,complete column will be inserted with
             * new value. complete would be 0.
             **/

            strFinRulObjid = finalRuleServiceVO.getStrFinalRuleID();
            strUserName = finalRuleServiceVO.getStrUserName();

            // Collect the oldfinalrule id:which is the id of Rule which is
            // being edited.
            strOldFinalRule = finalRuleServiceVO.getStrOldFinalRuleID();
            strRuleHistSeq = getSequenceId(RMDServiceConstants.QueryConstants.RULEHIS_SEQ_SQL, objHibernateSession);
            strCompletionStatus = finalRuleServiceVO.getStrCompleteStatus();
            strRevisionHistory = finalRuleServiceVO.getStrRevisionNotes();
            strRevisionHistory = AppSecUtil.decodeString(strRevisionHistory);
            // If the existing rule is edited, then collect the
            // previous_version,version, complete values of the existing record.
            if (!RMDCommonUtility.isNullOrEmpty(strOldFinalRule)) {
                ruleHistQuery = objHibernateSession
                        .createSQLQuery(RMDServiceConstants.QueryConstants.SELECT_RULHIS_SQL);
                ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strOldFinalRule);
                lstRuleHist = ruleHistQuery.list();
                if (RMDCommonUtility.isCollectionNotEmpty(lstRuleHist)) {
                    Object objRulHis[] = (Object[]) lstRuleHist.get(0);
                    intPreviousVersion = RMDCommonUtility.convertObjectToInt(objRulHis[0]);
                    intVersion = RMDCommonUtility.convertObjectToInt(objRulHis[1]);
                    intPreviousCompleted = RMDCommonUtility.convertObjectToInt(objRulHis[2]);
                    strOriginalId = RMDCommonUtility.convertObjectToString(objRulHis[3]);
                }
            }
            // Based on Rule completion status, rule history table would be
            // either updated or inserted with new record.
            if (!RMDCommonUtility.isNullOrEmpty(strCompletionStatus)
                    && (RMDServiceConstants.DRAFTSTATUS.equalsIgnoreCase(strCompletionStatus)
                            || RMDServiceConstants.TESTSTATUS.equalsIgnoreCase(strCompletionStatus))) {

                if (!RMDCommonUtility.isNullOrEmpty(strOldFinalRule)) {
                    // Saved as Draft rule::: existing Rule
                    // So update the existing record using the Old Fin rule Id
                    // with
                    // version,previous_version remains same
                    switch (intPreviousCompleted) {
                    case 0:
                        // If previous is in- complete and current request is
                        // draft.
                        // So updating the rule history with column
                        // rulehis2finrul with new ruleId
                        // But version, previous_version,original_id would
                        // remains same.
                        ruleHistQuery = objHibernateSession.createSQLQuery(
                                RMDServiceConstants.QueryConstants.UPDATE_DPD_RULHIS_BUILDER.toString());
                        ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                        ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, intPreviousVersion);
                        ruleHistQuery.setParameter(RMDServiceConstants.VERSION, intVersion);
                        ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.INT_CONST_ZERO);
                        if (intVersion == 1) {
                            ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strFinRulObjid);
                        } else {
                            ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        }
                        ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                        ruleHistQuery.setParameter(RMDServiceConstants.OLD_RULEHIS2FINRUL, strOldFinalRule);
                        ruleHistQuery.executeUpdate();
                        break;
                    case 1:
                        // If previous is complete and current request is draft.
                        // So Insert a new record in the rule history with
                        // column rulehis2finrul with new ruleId
                        // version and previous_version would be
                        // incremented,original_id would remains same.
                        ruleHistQuery = objHibernateSession.createSQLQuery(
                                RMDServiceConstants.QueryConstants.INSERT_DPD_RULHIS_BUILDER.toString());
                        ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                        ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                        ruleHistQuery.setParameter(RMDServiceConstants.ACTIVE, RMDServiceConstants.INACTIVE_STATE);
                        ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, intPreviousVersion + 1);
                        ruleHistQuery.setParameter(RMDServiceConstants.VERSION, intVersion + 1);
                        ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                        ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.INT_CONST_ZERO);
                        ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                        ruleHistQuery.executeUpdate();
                        break;
                    default:
                        break;
                    }
                } else {
                    // Saved as Draft rule::: New Rule
                    // So insert the new record with the
                    // version=1,previous_version_id=0,active=0,completed=0
                    ruleHistQuery = objHibernateSession
                            .createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_RULHIS_BUILDER.toString());
                    ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                    ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                    ruleHistQuery.setParameter(RMDServiceConstants.ACTIVE, RMDServiceConstants.INACTIVE_STATE);
                    ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, RMDCommonConstants.INT_CONST_ZERO);
                    ruleHistQuery.setParameter(RMDServiceConstants.VERSION, RMDCommonConstants.NUMERIC);
                    ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strFinRulObjid);
                    ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                    ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.INT_CONST_ZERO);
                    ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                    ruleHistQuery.executeUpdate();
                }
            } else if (!RMDCommonUtility.isNullOrEmpty(strCompletionStatus)
                    && RMDServiceConstants.COMPLETESTATUS.equalsIgnoreCase(strCompletionStatus)) {

                if (!RMDCommonUtility.isNullOrEmpty(strOldFinalRule)) {
                    // Saved as complete rule::: existing Rule
                    switch (intPreviousCompleted) {
                    case 0:
                        // previous version is in-complete and current is
                        // complete
                        // since previous version is draft so update the record
                        // with the same version and update completed as 1

                        ruleCountQuery = objHibernateSession
                                .createSQLQuery(RMDServiceConstants.QueryConstants.RULE_RECORDCOUNT.toString());
                        ruleCountQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        List<BigDecimal> countRowsList = ruleCountQuery.list();
                        if (RMDCommonUtility.isCollectionNotEmpty(countRowsList) && intVersion == 1) {
                            strOriginalId = strFinRulObjid;

                        }

                        ruleHistQuery = objHibernateSession.createSQLQuery(
                                RMDServiceConstants.QueryConstants.UPDATE_DPD_RULHIS_BUILDER.toString());
                        ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                        ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, intPreviousVersion);
                        ruleHistQuery.setParameter(RMDServiceConstants.VERSION, intVersion);
                        ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.NUMERIC);

                        ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                        ruleHistQuery.setParameter(RMDServiceConstants.OLD_RULEHIS2FINRUL, strOldFinalRule);
                        ruleHistQuery.executeUpdate();
                        break;
                    case 1:
                        // If previous version is complete and current is
                        // complete
                        // So insert the new record with the
                        // version=version,previous_version_id=previousversion+1,active=0,completed=0
                        ruleHistQuery = objHibernateSession.createSQLQuery(
                                RMDServiceConstants.QueryConstants.INSERT_DPD_RULHIS_BUILDER.toString());
                        ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                        ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                        ruleHistQuery.setParameter(RMDServiceConstants.ACTIVE, RMDServiceConstants.INACTIVE_STATE);
                        ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, intPreviousVersion + 1);
                        ruleHistQuery.setParameter(RMDServiceConstants.VERSION, intVersion + 1);
                        ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strOriginalId);
                        ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.NUMERIC);
                        ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                        ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                        ruleHistQuery.executeUpdate();
                        break;
                    default:
                        break;
                    }
                    // So update the existing record using the OldFinrul Id with
                    // version,previous_version remains same
                } else {
                    // Saved as complete rule::: New Rule
                    // So insert the brand new record with the
                    // version=1,previous_version_id=0,active=0,completed=1
                    ruleHistQuery = objHibernateSession
                            .createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_RULHIS_BUILDER.toString());
                    ruleHistQuery.setParameter(RMDServiceConstants.RULEHIS_OBJID, strRuleHistSeq);
                    ruleHistQuery.setParameter(RMDServiceConstants.USERNAME, strUserName);
                    ruleHistQuery.setParameter(RMDServiceConstants.ACTIVE, RMDServiceConstants.INACTIVE_STATE);
                    ruleHistQuery.setParameter(RMDServiceConstants.PREVIOUS_VERSION, RMDCommonConstants.INT_CONST_ZERO);
                    ruleHistQuery.setParameter(RMDServiceConstants.VERSION, RMDCommonConstants.NUMERIC);
                    ruleHistQuery.setParameter(RMDServiceConstants.ORIGINAL_ID, strFinRulObjid);
                    ruleHistQuery.setParameter(RMDServiceConstants.COMPLETE, RMDCommonConstants.NUMERIC);
                    ruleHistQuery.setParameter(RMDServiceConstants.RULHIS2FINRUL, strFinRulObjid);
                    ruleHistQuery.setParameter(RMDServiceConstants.REVISION_HISTORY, strRevisionHistory);
                    ruleHistQuery.executeUpdate();
                }

            }
        } catch (RMDDAOConnectionException objRMDDAOConnectionException) {
            throw objRMDDAOConnectionException;
        } finally {
            ruleHistQuery = null;
        }
    }

    /**
     * @Author:iGATE
     * @param: FinalRuleServiceVO,hibernateSession
     * @return seqID
     * @throws Exception
     * @throws @Description:This
     *             method is used to save the Simple Rule
     */
	private Map<String, String> saveSimpleRule(
			FinalRuleServiceVO finalRuleServiceVO, Session objHibernateSession) throws Exception {
		int beginIndex = 0;
		int endIndex = 1;
		ArrayList<SimpleRuleClauseServiceVO> arlSimpleRuleClauseVO = null;
		String strSimRulevalue1;
		String strSimRulevalue2 = RMDCommonConstants.EMPTY_STRING;
		String strColumnId = RMDCommonConstants.EMPTY_STRING;
		String strAnomTechnique = RMDCommonConstants.EMPTY_STRING;
		String strAnomId = RMDCommonConstants.EMPTY_STRING;
		String strSubFaultCode = null;
		String strSubId = null;
		String strControllerid = null;
		String strEDPParam = null;
		String strSimpRuleObjid = null;
		String strFaultCode;
		//String strFamily = RMDCommonConstants.EMPTY_STRING;
		Query simRuleQuery;
		Query simFeaQuery;
		String strUserName = RMDCommonConstants.EMPTY_STRING;
		Map<String, String> hmSimRule = new HashMap<String, String>();
		String strFinRulObjid = null;
		String strColumnType = RMDCommonConstants.EMPTY_STRING;
		String strSplId = RMDServiceConstants.NEGATIVE_NINE;
		String strFunction = RMDCommonConstants.EMPTY_STRING;
		String strFunctionObjid = null;
		int simRuleIndex = 0;
		boolean isMultifault = false;
		Query simMultifaultQuery;
		String multiFaultArray[];
		String multiSubIdArray[];
		String multiControlleridArray[];
		String strMultiFaultObjid;
		List<String> lstPixelsMultifaults = null;
		List<String> lstNotesMultifaults = null;
		Map<String,UnitOfMeasureVO> mpUom = null;
		try {
			LOG.debug("SIMPLE RULE STARTS from saveFinalRule()");
			if (RMDCommonUtility.isCollectionNotEmpty(finalRuleServiceVO
					.getArlSimpleRule())) {
				strFinRulObjid = finalRuleServiceVO.getStrFinalRuleID();
				strUserName = finalRuleServiceVO.getStrUserName();
				String defaultUom=finalRuleServiceVO.getDefaultUOM();
				if(null!=defaultUom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(defaultUom)&&!RMDCommonConstants.US.equalsIgnoreCase(defaultUom)){
					mpUom=convertTargetTOSource(defaultUom);
				}
				// iterating the Simple Rule VO and store the values SimRul
				// table.
				for (SimpleRuleServiceVO objSimpleRuleServiceVO : finalRuleServiceVO
						.getArlSimpleRule()) {
					strSimpRuleObjid = getSequenceId(
							RMDServiceConstants.QueryConstants.SIMRUL_SEQ_SQL,
							objHibernateSession);

					// Retrieve the simple rule id and keep it in the hashMap
					// and sequenceId object
					// 101-12345
					if (objSimpleRuleServiceVO.getStrSimpleRuleId() != null) {
						hmSimRule.put(
								objSimpleRuleServiceVO.getStrSimpleRuleId(),
								strSimpRuleObjid);
						finalRuleServiceVO
								.getArlSimpleRule()
								.get(simRuleIndex)
								.setSimpleSequenceId(
										Long.parseLong(strSimpRuleObjid));
					}
					strSubId = objSimpleRuleServiceVO.getStrSubID();
					strFaultCode = objSimpleRuleServiceVO.getStrFaultCode();
					strControllerid = objSimpleRuleServiceVO.getStrControllerid();
					if (null == strControllerid || strControllerid.trim().equals("")) {
						strControllerid = "-1";
					}
					lstPixelsMultifaults = objSimpleRuleServiceVO
							.getPixelsMultifaults();
					lstNotesMultifaults=objSimpleRuleServiceVO.getNotesMultifaults();

					// Existing logic in EOA to build the fault code
					// if the fault code starts with '!' collect the indexes
					// !CANDE
					if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
						if (strFaultCode
								.contains(RMDCommonConstants.COMMMA_SEPARATOR)) {
							isMultifault = true;
						} else {
							isMultifault = false;
							if (strFaultCode
									.startsWith(RMDCommonConstants.NOT_SYMBOL)) {
								beginIndex = 1;
								endIndex = 2;
							}
							strSubFaultCode = strFaultCode.substring(
									beginIndex, endIndex);
							// Check to set the fault code if the family is
							// AC4400
							if (!RMDCommonUtility
									.isNullOrEmpty(finalRuleServiceVO
											.getStrFamily())
									&& RMDServiceConstants.AC4400
											.equalsIgnoreCase(finalRuleServiceVO
													.getStrFamily())
									&& strSubFaultCode
											.equals(RMDCommonConstants.COMPLEX_RULE)) {
								// if the fault code starts with '!' append the
								// fault code with "7!" else "7"
								// !7CANDE
								if (strFaultCode
										.startsWith(RMDCommonConstants.NOT_SYMBOL))
									strFaultCode = RMDCommonConstants.SEVEN_STRING_NOT
											+ strFaultCode.substring(endIndex,
													strFaultCode.trim()
															.length());
								// 7CANDE
								else
									strFaultCode = RMDCommonConstants.SEVEN_STRING
											+ strFaultCode.substring(endIndex,
													strFaultCode.trim()
															.length());
							}
						}
					} else {
						isMultifault = false;
					}
					// inserting simple rule details info to the table
					// GETS_TOOL_DPD_SIMRUL
					simRuleQuery = objHibernateSession
							.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_SIMRUL_BUILDER
									.toString());
					simRuleQuery
							.setParameter(RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
					simRuleQuery.setParameter(RMDServiceConstants.USERNAME,
							strUserName);
					if (isMultifault) {
						simRuleQuery.setParameter(
								RMDServiceConstants.FAULT_CODE,
								RMDCommonConstants.EMPTY_STRING);
						simRuleQuery.setParameter(RMDServiceConstants.SUB_ID,
								RMDCommonConstants.EMPTY_STRING);
						simRuleQuery.setParameter(RMDServiceConstants.CONTROLLER_ID,
								RMDCommonConstants.MINUS_ONE);
					} else {
						simRuleQuery.setParameter(
								RMDServiceConstants.FAULT_CODE, strFaultCode);

						simRuleQuery.setParameter(RMDServiceConstants.SUB_ID,
								strSubId);
						simRuleQuery.setParameter(RMDServiceConstants.CONTROLLER_ID,
								strControllerid);
					}
					simRuleQuery.setParameter(
							RMDServiceConstants.SIMRUL2FINRUL, strFinRulObjid);
					simRuleQuery.setParameter(
							RMDServiceConstants.COLUMN_PIXELS,
							objSimpleRuleServiceVO.getStrPixels());
					simRuleQuery.setParameter(
							RMDServiceConstants.STR_NOTES,
							AppSecUtil.stripNonValidXMLCharactersForKM(objSimpleRuleServiceVO.getNotes()));
					simRuleQuery.executeUpdate();

					// Start--Inserting the simple rule Multi-fault details to
					// the table MTM_MULTIFAULT
					if (isMultifault) {
						strFaultCode = strFaultCode.substring(0,
								strFaultCode.length() - 1);
						strSubId = strSubId.substring(0, strSubId.length() - 1);
						strControllerid = strControllerid.substring(0, strControllerid.length() - 1);
						multiSubIdArray = strSubId
								.split(RMDCommonConstants.COMMMA_SEPARATOR);
						multiFaultArray = strFaultCode
								.split(RMDCommonConstants.COMMMA_SEPARATOR);
						multiControlleridArray = strControllerid.split(RMDCommonConstants.COMMMA_SEPARATOR);
						for (int i = 0; i < multiFaultArray.length; i++) {
							String strMultiFault = multiFaultArray[i];
							
							/*Added By Amuthan For converting the fault code from C to 7 for AC4400 family if starting with C*/
							if (!RMDCommonUtility
									.isNullOrEmpty(finalRuleServiceVO
											.getStrFamily())
									&& RMDServiceConstants.AC4400
											.equalsIgnoreCase(finalRuleServiceVO
													.getStrFamily())) {							
							strMultiFault=changeMultiFaultCodeForAC440(strMultiFault);							
							}						
							/*End of code changes For converting the fault code from C to 7 for AC4400 family if starting with C*/
							strMultiFaultObjid = getSequenceId(
									RMDServiceConstants.QueryConstants.SELECT_MULTIFAULT_SEQ,
									objHibernateSession);
							simMultifaultQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_SIMMTM_MULTIFAULT_BUILDER
											.toString());
							simMultifaultQuery.setParameter(
									RMDServiceConstants.USERNAME, strUserName);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.FAULT_CODE,
									strMultiFault);
							if (multiSubIdArray.length >= (i + 1)
									&& null != multiSubIdArray[i]
									&& !"".equals(multiSubIdArray[i].trim())
									&& !RMDCommonConstants.UN_DEFINED
											.equalsIgnoreCase(multiSubIdArray[i]
													.trim()))
								simMultifaultQuery.setParameter(
										RMDServiceConstants.SUB_ID,
										multiSubIdArray[i].trim());
							else
								simMultifaultQuery.setParameter(
										RMDServiceConstants.SUB_ID, null,
										Hibernate.STRING);
							if (multiControlleridArray.length >= (i + 1)
									&& null != multiControlleridArray[i]
									&& !"".equals(multiControlleridArray[i].trim())
									&& !RMDCommonConstants.UN_DEFINED
											.equalsIgnoreCase(multiControlleridArray[i]
													.trim()))
								simMultifaultQuery.setParameter(
										RMDServiceConstants.CONTROLLER_ID,
										multiControlleridArray[i].trim());
							else
							simMultifaultQuery.setParameter(
									RMDServiceConstants.CONTROLLER_ID, Integer.valueOf(RMDCommonConstants.MINUS_ONE),
									Hibernate.INTEGER);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.MULTIFAULT_SEQ_ID,
									strMultiFaultObjid);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
							if (RMDCommonUtility
									.isCollectionNotEmpty(lstPixelsMultifaults)) {
								simMultifaultQuery.setParameter(
										RMDServiceConstants.MULTIFAULT_PIXELS,
										lstPixelsMultifaults.get(i));
							}
							
							simMultifaultQuery.setParameter(
									RMDServiceConstants.STR_NOTES,
									AppSecUtil.stripNonValidXMLCharactersForKM(lstNotesMultifaults.get(i)));
							simMultifaultQuery.executeUpdate();
						}
					}
					// End--Inserting the simple rule Multi-fault details to the
					// table MTM_MULTIFAULT
					arlSimpleRuleClauseVO = objSimpleRuleServiceVO
							.getArlSimpleRuleClauseVO();

					// Iterating the simple rule clause VO and retrieve the
					// objects and store them in GETS_TOOL_DPD_SimFea table
					if (RMDCommonUtility
							.isCollectionNotEmpty(arlSimpleRuleClauseVO)) {
						for (SimpleRuleClauseServiceVO simpleRuleClauseServiceVO : arlSimpleRuleClauseVO) {
							// reinitializing the temporary values
							strColumnType = simpleRuleClauseServiceVO
									.getStrColumnType();

							strFunction = simpleRuleClauseServiceVO
									.getStrFunction();
							strAnomId = RMDCommonConstants.EMPTY_STRING;
							strAnomTechnique = RMDCommonConstants.EMPTY_STRING;
							strEDPParam = RMDCommonConstants.EMPTY_STRING;
							strSimRulevalue2 = RMDCommonConstants.EMPTY_STRING;
							strSplId = RMDServiceConstants.NEGATIVE_NINE;
							// Calculating the Function id
							simRuleQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.SIMFCN_SQL);
							simRuleQuery.setParameter(
									RMDServiceConstants.SIMFUNCTION,
									strFunction);
							List lstFunctionId = simRuleQuery.list();
							if (RMDCommonUtility
									.isCollectionNotEmpty(lstFunctionId)) {
								BigDecimal objBigDecimal = (BigDecimal) lstFunctionId
										.get(0);
								strFunctionObjid = objBigDecimal.toString();
							}
							// Calculating the SIMFEA objid

							strSimRulevalue1 = simpleRuleClauseServiceVO
									.getStrValue1();

							if (!RMDCommonUtility
									.isNullOrEmpty(simpleRuleClauseServiceVO
											.getStrValue2())
									&& !simpleRuleClauseServiceVO
											.getStrValue2()
											.equalsIgnoreCase(
													RMDServiceConstants.RULE_DEFAULT_DROPDOWN_VALUE)) {
								strSimRulevalue2 = simpleRuleClauseServiceVO
										.getStrValue2();
							}
							// Column name and seq_value will be calculated
							// based on the column Types i.e., EDP or ANM or
							// general param
							// We can remove this logic once integrated from UI
							// as these values will be passed from the UI itself
							if (!RMDCommonUtility.isNullOrEmpty(strColumnType)) {
								if (strColumnType
										.equals(RMDServiceConstants.EDP)) {
									
									
									strSplId = simpleRuleClauseServiceVO
											.getStrSeqVal();
									
									if (!strSplId
											.equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)){
										strColumnId = RMDServiceConstants.NEGATIVE_ONE;
										strEDPParam = simpleRuleClauseServiceVO
												.getStrColumnName();
									}else{
										strColumnId=simpleRuleClauseServiceVO
										.getStrColumnId();
									}
									if (!RMDCommonUtility
											.isNullOrEmpty(strSplId)) {
										if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.ANY)) {
											strSplId = RMDServiceConstants.POSITIVE_NINE;
										} else if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)) {
											strSplId = RMDCommonConstants.ZERO_STRING;
										} else if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.HYPHEN)) {
											strSplId = RMDServiceConstants.NEGATIVE_ONE;
										} else if (strSplId
												.equalsIgnoreCase(RMDServiceConstants.PLUS_SYMBOL)) {
											strSplId = RMDServiceConstants.NUMBER_1;
										} else {
											strSplId = RMDServiceConstants.NEGATIVE_NINE;
										}
									} else {
										strSplId = RMDServiceConstants.NEGATIVE_NINE;
									}
								} else if (strColumnType
										.equals(RMDServiceConstants.ANM)) {
									strSplId = RMDServiceConstants.NEGATIVE_NINE;
									strColumnId = RMDServiceConstants.NEGATIVE_ONE;
									strAnomId = simpleRuleClauseServiceVO
											.getStrColumnId();
									if (!RMDCommonUtility
											.isNullOrEmpty(simpleRuleClauseServiceVO
													.getAnomTechniqueId())) {
										strAnomTechnique = simpleRuleClauseServiceVO
												.getAnomTechniqueId();
										if (strAnomTechnique
												.equalsIgnoreCase(RMDServiceConstants.SHIFT)) {
											strAnomTechnique = RMDCommonConstants.TWO;
										} else {
											strAnomTechnique = RMDCommonConstants.ONE_STRING;
										}
									}
								} else {
									strSplId = RMDServiceConstants.NEGATIVE_NINE;
									strColumnId = simpleRuleClauseServiceVO
											.getStrColumnId();
								}
							} else {
								strColumnId = simpleRuleClauseServiceVO
										.getStrColumnId();
							}
							// inserting simple rule features info to the table
							// GETS_TOOL_DPD_SIMFEA
														
							if (null != defaultUom
									&& !RMDCommonConstants.EMPTY_STRING
											.equalsIgnoreCase(defaultUom)
									&& !RMDCommonConstants.US
											.equalsIgnoreCase(defaultUom)) {
								String sourceUom = getSourceUom(
										objHibernateSession, strColumnId,
										finalRuleServiceVO.getStrFamily());
								if (null != sourceUom) {
									UnitOfMeasureVO objUnitOfMeasureVO = mpUom
											.get(sourceUom);
									if (null != objUnitOfMeasureVO) {										
												String conversionFormula = objUnitOfMeasureVO
														.getConversionExp();
												if (null != conversionFormula) {
													strSimRulevalue1 = AppSecUtil
															.convertMeasurementSystem(
																	conversionFormula,
																	strSimRulevalue1);
													strSimRulevalue2 = AppSecUtil
															.convertMeasurementSystem(
																	conversionFormula,
																	strSimRulevalue2);
												}
									}
								}
							}
							simFeaQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_SIMFEA_BUILDER
											.toString());
							simFeaQuery.setParameter(
									RMDServiceConstants.USERNAME, strUserName);
							simFeaQuery.setParameter(
									RMDServiceConstants.VALUE_1,
									strSimRulevalue1);
							simFeaQuery.setParameter(
									RMDServiceConstants.VALUE_2,
									strSimRulevalue2);
							simFeaQuery.setParameter(
									RMDServiceConstants.SEQ_VALUE, strSplId);
							simFeaQuery.setParameter(
									RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
							simFeaQuery.setParameter(
									RMDServiceConstants.COLUMN_OBJID,
									strColumnId);
							simFeaQuery.setParameter(
									RMDServiceConstants.SIM_FUNCTION,
									strFunctionObjid);
							simFeaQuery.setParameter(
									RMDServiceConstants.EDP_PARM, strEDPParam);
							simFeaQuery.setParameter(
									RMDServiceConstants.ANOM_OBJID, strAnomId);
							simFeaQuery.setParameter(
									RMDServiceConstants.TECHNIQUE,
									strAnomTechnique);
							simFeaQuery.setParameter(
									RMDServiceConstants.COLUMN_PIXELS,
									simpleRuleClauseServiceVO.getStrPixels());
							simFeaQuery.setParameter(
									RMDServiceConstants.STR_NOTES,
									AppSecUtil.stripNonValidXMLCharactersForKM(simpleRuleClauseServiceVO.getNotes()));
							simFeaQuery.executeUpdate();

						}
					}
					simRuleIndex++;
				}
			}
			LOG.debug("SAVE SIMPLE RULE ENDS");
			return hmSimRule;
		} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
			throw objRMDDAOConnectionException;
		}
		
		finally {
			simFeaQuery = null;
			simRuleQuery = null;
		}
	}

	/**
	 * @Author:iGATE
	 * @param: FinalRuleServiceVO,hibernateSession
	 * @return seqID
	 * @throws Exception 
	 * @throws
	 * @Description:This method is used to save the Simple Rule
	 * 
	 */
	private Map<String, String> saveClearingLogicSimpleRule(
			FinalRuleServiceVO finalRuleServiceVO, Session objHibernateSession) throws Exception {
		int beginIndex = 0;
		int endIndex = 1;
		ArrayList<SimpleRuleClauseServiceVO> arlSimpleRuleClauseVO = null;
		String strSimRulevalue1;
		String strSimRulevalue2 = RMDCommonConstants.EMPTY_STRING;
		String strColumnId = RMDCommonConstants.EMPTY_STRING;
		String strAnomTechnique = RMDCommonConstants.EMPTY_STRING;
		String strAnomId = RMDCommonConstants.EMPTY_STRING;
		String strSubFaultCode = null;
		String strSubId = null;
		String strControllerid = null;
		String strEDPParam = null;
		String strSimpRuleObjid = null;
		String strFaultCode;
		String strFamily = RMDCommonConstants.EMPTY_STRING;
		Query simRuleQuery;
		Query simFeaQuery;
		String strUserName = RMDCommonConstants.EMPTY_STRING;
		Map<String, String> hmSimRule = new HashMap<String, String>();
		String strFinRulObjid = null;
		String strColumnType = RMDCommonConstants.EMPTY_STRING;
		String strSplId = RMDServiceConstants.NEGATIVE_NINE;
		String strFunction = RMDCommonConstants.EMPTY_STRING;
		String strFunctionObjid = null;
		int simRuleIndex = 0;
		boolean isMultifault = false;
		Query simMultifaultQuery;
		String multiFaultArray[];
		String multiSubIdArray[];
		String multiControlleridArray[];
		String strMultiFaultObjid;
		List<String> lstPixelsMultifaults = null;
		List<String> lstNotesMultifaults = null;
		ClearingLogicRuleServiceVO clearingLogicRuleServiceVO = null;
		Map<String,UnitOfMeasureVO> mpUom = null;
		try {
			LOG.debug("SIMPLE RULE STARTS from saveFinalRule()");
			clearingLogicRuleServiceVO = finalRuleServiceVO
					.getClearingLogicRule();
			if (RMDCommonUtility
					.isCollectionNotEmpty(clearingLogicRuleServiceVO
							.getArlSimpleRule())) {
				strFinRulObjid = clearingLogicRuleServiceVO
						.getStrClearingLogicRuleID();
				strUserName = clearingLogicRuleServiceVO.getStrUserName();
				String defaultUom=finalRuleServiceVO.getDefaultUOM();
				if(null!=defaultUom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(defaultUom)&&!RMDCommonConstants.US.equalsIgnoreCase(defaultUom)){
					mpUom=convertTargetTOSource(defaultUom);
				}
				// iterating the Simple Rule VO and store the values SimRul
				// table.
				for (SimpleRuleServiceVO objSimpleRuleServiceVO : clearingLogicRuleServiceVO
						.getArlSimpleRule()) {
					strSimpRuleObjid = getSequenceId(
							RMDServiceConstants.QueryConstants.SIMRUL_SEQ_SQL,
							objHibernateSession);

					// Retrieve the simple rule id and keep it in the hashMap
					// and sequenceId object
					// 101-12345
					if (objSimpleRuleServiceVO.getStrSimpleRuleId() != null) {
						hmSimRule.put(
								objSimpleRuleServiceVO.getStrSimpleRuleId(),
								strSimpRuleObjid);
						clearingLogicRuleServiceVO
								.getArlSimpleRule()
								.get(simRuleIndex)
								.setSimpleSequenceId(
										Long.parseLong(strSimpRuleObjid));
					}
					strSubId = objSimpleRuleServiceVO.getStrSubID();
					strFaultCode = objSimpleRuleServiceVO.getStrFaultCode();
					strControllerid = objSimpleRuleServiceVO.getStrControllerid();
					if (null == strControllerid || strControllerid.trim().equals("")) {
						strControllerid = "-1";
					}
					lstPixelsMultifaults = objSimpleRuleServiceVO
							.getPixelsMultifaults();
					lstNotesMultifaults=objSimpleRuleServiceVO.getNotesMultifaults();

					// Existing logic in EOA to build the fault code
					// if the fault code starts with '!' collect the indexes
					// !CANDE
					if (!RMDCommonUtility.isNullOrEmpty(strFaultCode)) {
						if (strFaultCode
								.contains(RMDCommonConstants.COMMMA_SEPARATOR)) {
							isMultifault = true;
						} else {
							isMultifault = false;
							if (strFaultCode
									.startsWith(RMDCommonConstants.NOT_SYMBOL)) {
								beginIndex = 1;
								endIndex = 2;
							}
							strSubFaultCode = strFaultCode.substring(
									beginIndex, endIndex);
							// Check to set the fault code if the family is
							// AC4400
							if (!RMDCommonUtility.isNullOrEmpty(strFamily)
									&& RMDServiceConstants.AC4400
											.equalsIgnoreCase(strFamily)
									&& strSubFaultCode
											.equals(RMDCommonConstants.COMPLEX_RULE)) {
								// if the fault code starts with '!' append the
								// fault code with "7!" else "7"
								// !7CANDE
								if (strFaultCode
										.startsWith(RMDCommonConstants.NOT_SYMBOL))
									strFaultCode = RMDCommonConstants.SEVEN_STRING_NOT
											+ strFaultCode.substring(endIndex,
													strFaultCode.trim()
															.length());
								// 7CANDE
								else
									strFaultCode = RMDCommonConstants.SEVEN_STRING
											+ strFaultCode.substring(endIndex,
													strFaultCode.trim()
															.length());
							}
						}
					} else {
						isMultifault = false;
					}
					// inserting simple rule details info to the table
					// GETS_TOOL_DPD_SIMRUL
					simRuleQuery = objHibernateSession
							.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_SIMRUL_BUILDER
									.toString());
					simRuleQuery
							.setParameter(RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
					simRuleQuery.setParameter(RMDServiceConstants.USERNAME,
							strUserName);
					if (isMultifault) {
						simRuleQuery.setParameter(
								RMDServiceConstants.FAULT_CODE,
								RMDCommonConstants.EMPTY_STRING);
						simRuleQuery.setParameter(RMDServiceConstants.SUB_ID,
								RMDCommonConstants.EMPTY_STRING);
						simRuleQuery.setParameter(RMDServiceConstants.CONTROLLER_ID,
								RMDCommonConstants.EMPTY_STRING);
					} else {
						simRuleQuery.setParameter(
								RMDServiceConstants.FAULT_CODE, strFaultCode);

						simRuleQuery.setParameter(RMDServiceConstants.SUB_ID,
								strSubId);
						simRuleQuery.setParameter(RMDServiceConstants.CONTROLLER_ID,
								strControllerid);
					}
					simRuleQuery.setParameter(
							RMDServiceConstants.SIMRUL2FINRUL, strFinRulObjid);
					simRuleQuery.setParameter(
							RMDServiceConstants.COLUMN_PIXELS,
							objSimpleRuleServiceVO.getStrPixels());
					simRuleQuery.setParameter(
							RMDServiceConstants.STR_NOTES,
							AppSecUtil.stripNonValidXMLCharactersForKM(objSimpleRuleServiceVO.getNotes()));
					simRuleQuery.executeUpdate();

					// Start--Inserting the simple rule Multi-fault details to
					// the table MTM_MULTIFAULT
					if (isMultifault) {
						strFaultCode = strFaultCode.substring(0,
								strFaultCode.length() - 1);
						strSubId = strSubId.substring(0, strSubId.length() - 1);
						multiSubIdArray = strSubId
								.split(RMDCommonConstants.COMMMA_SEPARATOR);
						multiFaultArray = strFaultCode
								.split(RMDCommonConstants.COMMMA_SEPARATOR);
						multiControlleridArray = strControllerid
								.split(RMDCommonConstants.COMMMA_SEPARATOR);
						for (int i = 0; i < multiFaultArray.length; i++) {
							String strMultiFault = multiFaultArray[i];
							if (!RMDCommonUtility
									.isNullOrEmpty(finalRuleServiceVO
											.getStrFamily())
									&& RMDServiceConstants.AC4400
											.equalsIgnoreCase(finalRuleServiceVO
													.getStrFamily())) {							
							strMultiFault=changeMultiFaultCodeForAC440(strMultiFault);							
							}	
							strMultiFaultObjid = getSequenceId(
									RMDServiceConstants.QueryConstants.SELECT_MULTIFAULT_SEQ,
									objHibernateSession);
							simMultifaultQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_SIMMTM_MULTIFAULT_BUILDER
											.toString());
							simMultifaultQuery.setParameter(
									RMDServiceConstants.USERNAME, strUserName);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.FAULT_CODE,
									strMultiFault);
							if (multiSubIdArray.length >= (i + 1)
									&& null != multiSubIdArray[i]
									&& !"".equals(multiSubIdArray[i].trim())
									&& !RMDCommonConstants.UN_DEFINED
											.equalsIgnoreCase(multiSubIdArray[i]
													.trim()))
								simMultifaultQuery.setParameter(
										RMDServiceConstants.SUB_ID,
										multiSubIdArray[i].trim());
							else
								simMultifaultQuery.setParameter(
										RMDServiceConstants.SUB_ID, null,
										Hibernate.STRING);
							if (multiControlleridArray.length >= (i + 1)
									&& null != multiControlleridArray[i]
									&& !"".equals(multiControlleridArray[i]
											.trim())
									&& !RMDCommonConstants.UN_DEFINED
											.equalsIgnoreCase(multiControlleridArray[i]
													.trim()))
								simMultifaultQuery.setParameter(
										RMDServiceConstants.CONTROLLER_ID,
										multiControlleridArray[i].trim());
							else
								simMultifaultQuery
										.setParameter(
												RMDServiceConstants.CONTROLLER_ID,
												Integer.valueOf(RMDCommonConstants.MINUS_ONE),
												Hibernate.INTEGER);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.MULTIFAULT_SEQ_ID,
									strMultiFaultObjid);
							simMultifaultQuery.setParameter(
									RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
							if (RMDCommonUtility
									.isCollectionNotEmpty(lstPixelsMultifaults)) {
								simMultifaultQuery.setParameter(
										RMDServiceConstants.MULTIFAULT_PIXELS,
										lstPixelsMultifaults.get(i));
							}
							simMultifaultQuery.setParameter(
									RMDServiceConstants.STR_NOTES,
									AppSecUtil.stripNonValidXMLCharactersForKM(lstNotesMultifaults.get(i)));
							simMultifaultQuery.executeUpdate();
						}
					}
					// End--Inserting the simple rule Multi-fault details to the
					// table MTM_MULTIFAULT
					arlSimpleRuleClauseVO = objSimpleRuleServiceVO
							.getArlSimpleRuleClauseVO();

					// Iterating the simple rule clause VO and retrieve the
					// objects and store them in GETS_TOOL_DPD_SimFea table
					if (RMDCommonUtility
							.isCollectionNotEmpty(arlSimpleRuleClauseVO)) {
						for (SimpleRuleClauseServiceVO simpleRuleClauseServiceVO : arlSimpleRuleClauseVO) {
							// reinitializing the temporary values
							strColumnType = simpleRuleClauseServiceVO
									.getStrColumnType();

							strFunction = simpleRuleClauseServiceVO
									.getStrFunction();
							strAnomId = RMDCommonConstants.EMPTY_STRING;
							strAnomTechnique = RMDCommonConstants.EMPTY_STRING;
							strEDPParam = RMDCommonConstants.EMPTY_STRING;
							strSimRulevalue2 = RMDCommonConstants.EMPTY_STRING;
							strSplId = RMDServiceConstants.NEGATIVE_NINE;
							// Calculating the Function id
							simRuleQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.SIMFCN_SQL);
							simRuleQuery.setParameter(
									RMDServiceConstants.SIMFUNCTION,
									strFunction);
							List lstFunctionId = simRuleQuery.list();
							if (RMDCommonUtility
									.isCollectionNotEmpty(lstFunctionId)) {
								BigDecimal objBigDecimal = (BigDecimal) lstFunctionId
										.get(0);
								strFunctionObjid = objBigDecimal.toString();
							}
							// Calculating the SIMFEA objid

							strSimRulevalue1 = simpleRuleClauseServiceVO
									.getStrValue1();

							if (!RMDCommonUtility
									.isNullOrEmpty(simpleRuleClauseServiceVO
											.getStrValue2())
									&& !simpleRuleClauseServiceVO
											.getStrValue2()
											.equalsIgnoreCase(
													RMDServiceConstants.RULE_DEFAULT_DROPDOWN_VALUE)) {
								strSimRulevalue2 = simpleRuleClauseServiceVO
										.getStrValue2();
							}
							// Column name and seq_value will be calculated
							// based on the column Types i.e., EDP or ANM or
							// general param
							// We can remove this logic once integrated from UI
							// as these values will be passed from the UI itself
							if (!RMDCommonUtility.isNullOrEmpty(strColumnType)) {
								if (strColumnType
										.equals(RMDServiceConstants.EDP)) {
									strSplId = simpleRuleClauseServiceVO
											.getStrSeqVal();
									
									if (!strSplId
											.equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)){
										strColumnId = RMDServiceConstants.NEGATIVE_ONE;
										strEDPParam = simpleRuleClauseServiceVO
												.getStrColumnName();
									}else{
										strColumnId=simpleRuleClauseServiceVO
										.getStrColumnId();
									}
									if (!RMDCommonUtility
											.isNullOrEmpty(strSplId)) {
										if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.ANY)) {
											strSplId = RMDServiceConstants.POSITIVE_NINE;
										} else if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)) {
											strSplId = RMDCommonConstants.ZERO_STRING;
										} else if (strSplId
												.equalsIgnoreCase(RMDCommonConstants.HYPHEN)) {
											strSplId = RMDServiceConstants.NEGATIVE_ONE;
										} else if (strSplId
												.equalsIgnoreCase(RMDServiceConstants.PLUS_SYMBOL)) {
											strSplId = RMDServiceConstants.NUMBER_1;
										} else {
											strSplId = RMDServiceConstants.NEGATIVE_NINE;
										}
									} else {
										strSplId = RMDServiceConstants.NEGATIVE_NINE;
									}
								} else if (strColumnType
										.equals(RMDServiceConstants.ANM)) {
									strSplId = RMDServiceConstants.NEGATIVE_NINE;
									strColumnId = RMDServiceConstants.NEGATIVE_ONE;
									strAnomId = simpleRuleClauseServiceVO
											.getStrColumnId();
									if (!RMDCommonUtility
											.isNullOrEmpty(simpleRuleClauseServiceVO
													.getAnomTechniqueId())) {
										strAnomTechnique = simpleRuleClauseServiceVO
												.getAnomTechniqueId();
										if (strAnomTechnique
												.equalsIgnoreCase(RMDServiceConstants.SHIFT)) {
											strAnomTechnique = RMDCommonConstants.TWO;
										} else {
											strAnomTechnique = RMDCommonConstants.ONE_STRING;
										}
									}
								} else {
									strSplId = RMDServiceConstants.NEGATIVE_NINE;
									strColumnId = simpleRuleClauseServiceVO
											.getStrColumnId();
								}
							} else {
								strColumnId = simpleRuleClauseServiceVO
										.getStrColumnId();
							}
							// inserting simple rule features info to the table
							// GETS_TOOL_DPD_SIMFEA
							if (null != defaultUom
									&& !RMDCommonConstants.EMPTY_STRING
											.equalsIgnoreCase(defaultUom)
									&& !RMDCommonConstants.US
											.equalsIgnoreCase(defaultUom)) {
								String sourceUom = getSourceUom(
										objHibernateSession, strColumnId,
										finalRuleServiceVO.getStrFamily());
								if (null != sourceUom) {
									UnitOfMeasureVO objUnitOfMeasureVO = mpUom
											.get(sourceUom);
									if (null != objUnitOfMeasureVO) {										
												String conversionFormula = objUnitOfMeasureVO
														.getConversionExp();
												if (null != conversionFormula) {
													strSimRulevalue1 = AppSecUtil
															.convertMeasurementSystem(
																	conversionFormula,
																	strSimRulevalue1);
													strSimRulevalue2 = AppSecUtil
															.convertMeasurementSystem(
																	conversionFormula,
																	strSimRulevalue2);
												}
									}
								}
							}
							simFeaQuery = objHibernateSession
									.createSQLQuery(RMDServiceConstants.QueryConstants.INSERT_DPD_SIMFEA_BUILDER
											.toString());
							simFeaQuery.setParameter(
									RMDServiceConstants.USERNAME, strUserName);
							simFeaQuery.setParameter(
									RMDServiceConstants.VALUE_1,
									strSimRulevalue1);
							simFeaQuery.setParameter(
									RMDServiceConstants.VALUE_2,
									strSimRulevalue2);
							simFeaQuery.setParameter(
									RMDServiceConstants.SEQ_VALUE, strSplId);
							simFeaQuery.setParameter(
									RMDServiceConstants.SIMRULE_OBJID,
									strSimpRuleObjid);
							simFeaQuery.setParameter(
									RMDServiceConstants.COLUMN_OBJID,
									strColumnId);
							simFeaQuery.setParameter(
									RMDServiceConstants.SIM_FUNCTION,
									strFunctionObjid);
							simFeaQuery.setParameter(
									RMDServiceConstants.EDP_PARM, strEDPParam);
							simFeaQuery.setParameter(
									RMDServiceConstants.ANOM_OBJID, strAnomId);
							simFeaQuery.setParameter(
									RMDServiceConstants.TECHNIQUE,
									strAnomTechnique);
							simFeaQuery.setParameter(
									RMDServiceConstants.COLUMN_PIXELS,
									simpleRuleClauseServiceVO.getStrPixels());
							simFeaQuery.setParameter(
									RMDServiceConstants.STR_NOTES,
									AppSecUtil.stripNonValidXMLCharactersForKM(simpleRuleClauseServiceVO.getNotes()));
							simFeaQuery.executeUpdate();

						}
					}
					simRuleIndex++;
				}
			}
			LOG.debug("SAVE SIMPLE RULE ENDS");
			return hmSimRule;
		} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
			throw objRMDDAOConnectionException;
		} finally {
			simFeaQuery = null;
			simRuleQuery = null;
		}
	}

	/**
	 * @Author:iGATE
	 * @return
	 * @Description: This method will return the SDP parameters and its values
	 */
	public List<ParameterServiceVO> getSDPValues(String strFamily,
			String strLanguage) {
		List<ParameterServiceVO> lstParameterServiceVO = null;
		Session session = null;
		StringBuilder queryString = new StringBuilder();
		List<Object[]> listSDPValues = null;
		Query sdpQuery = null;
		ParameterServiceVO objParameterServiceVO = null;

		try {
			session = getHibernateSession();
			queryString = RMDServiceConstants.QueryConstants.FETCH_SDP_VALUES;
			if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
				queryString
						.append("  AND LOWER(CLMNAM.FAMILY)=LOWER(:FAMILY) ");
				sdpQuery = session.createSQLQuery(queryString.toString());
				sdpQuery.setParameter(RMDCommonConstants.FAMILY_INITIAL_LOADER,
						strFamily);
			} else {
				sdpQuery = session.createSQLQuery(queryString.toString());
			}
			listSDPValues = sdpQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listSDPValues)) {
				lstParameterServiceVO = new ArrayList<ParameterServiceVO>();
				for (Object[] objValues : listSDPValues) {
					objParameterServiceVO = new ParameterServiceVO();
					objParameterServiceVO.setParameterNameID(objValues[0]
							.toString());
					objParameterServiceVO.setParameterName(objValues[1]
							.toString());
					objParameterServiceVO.setValue(objValues[2].toString());
					objParameterServiceVO
							.setColumnType(RMDServiceConstants.SDP);
					lstParameterServiceVO.add(objParameterServiceVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_SDP_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_SDP_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstParameterServiceVO;
	}

    private String getKEPModelsQuery(String strLanguage) {
        String strModelQry = "";
        try {
            List<ElementVO> arlKepModels = getLookupList(RMDServiceConstants.KEP_FAMILY_LIST, strLanguage);
            String strKEPModels = arlKepModels.get(0).getName();
            StringTokenizer stk = new StringTokenizer(strKEPModels, ",");
            StringBuilder sbf = new StringBuilder();
            while (stk.hasMoreElements()) {
                sbf.append("'");
                sbf.append(stk.nextToken());
                sbf.append("',");
            }
            strModelQry = sbf.substring(0, sbf.length() - 1);
        } catch (Exception e) {
            LOG.debug("", e);
        }
        return strModelQry;
    }

    /**
     * The method is used to get performance check values of health rules
     * 
     * @param strLanguage
     *            *
     * @return List of ElementVO
     * @throws RMDServiceException
     */
    @Override
    public List<ElementVO> getPerformanceCheckValues(final String strLanguage) throws RMDDAOException {
        Session session = null;
        ArrayList<ElementVO> lstLookupValues = null;
        Query sqlQuery = null;
        StringBuilder stringBuilder = null;
        try {
            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append(
                    "SELECT OBJID,PERFCHECK_NAME FROM GETS_TOOL_LHR_PERF_CHECK WHERE PERFCHECK_CODE!= 'RX' ORDER BY PERFCHECK_NAME");
            sqlQuery = session.createSQLQuery(stringBuilder.toString());

            List<Object> lookList = sqlQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(lookList)) {
                lstLookupValues = new ArrayList<ElementVO>();

                for (final Iterator<Object> iter = lookList.iterator(); iter.hasNext();) {
                    ElementVO elementVO = new ElementVO();
                    final Object[] lookupRecord = (Object[]) iter.next();

                    elementVO.setId(lookupRecord[0].toString());
                    elementVO.setName(lookupRecord[1].toString());
                    lstLookupValues.add(elementVO);
                }

            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.LOOKUP_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, strLanguage), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return lstLookupValues;
    }

    public String changeMultiFaultCodeForAC440(String strFaultCode) throws Exception {
        String strSubFaultCode = null;
        int beginIndex = 0;
        int endIndex = 1;
        try {
            if (strFaultCode.startsWith(RMDCommonConstants.NOT_SYMBOL)) {
                beginIndex = 1;
                endIndex = 2;
            }
            strSubFaultCode = strFaultCode.substring(beginIndex, endIndex);
            // Check to set the fault code if the family is
            // AC4400
            if (strSubFaultCode.equals(RMDCommonConstants.COMPLEX_RULE)) {
                // if the fault code starts with '!' append the
                // fault code with "7!" else "7"
                // !7CANDE
                if (strFaultCode.startsWith(RMDCommonConstants.NOT_SYMBOL))
                    strFaultCode = RMDCommonConstants.SEVEN_STRING_NOT
                            + strFaultCode.substring(endIndex, strFaultCode.trim().length());
                // 7CANDE
                else
                    strFaultCode = RMDCommonConstants.SEVEN_STRING
                            + strFaultCode.substring(endIndex, strFaultCode.trim().length());
            }
        } catch (Exception e) {
            throw e;
        }
        return strFaultCode;
    }

    /**
     * @param String
     *            strLanguage,String strCustomer
     * @return list of ElementVO
     * @throws RMDServiceException
     * @Description:This method will FETCH Created by based on external customer
     */
    @Override
    public List<ElementVO> getRuleCreatedByExternal(String strLanguage, String strCustomer) throws RMDDAOException {

        List<ElementVO> arlRuleCreatedByList = new ArrayList<ElementVO>();
        ElementVO objRuleCreatedByList = null;
        Session session = null;

        Query query = null;
        StringBuilder stringBuilder = null;

        try {

            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append(
                    " SELECT DISTINCT F.CREATED_BY FROM GETS_TOOL_DPD_FINRUL F,GETS_TOOL_DPD_RULEDEF D,GETS_TOOL_DPD_CUST C,SA.TABLE_BUS_ORG O");
            stringBuilder.append(" WHERE D.RULEDEF2FINRUL  = F.OBJID");
            stringBuilder.append(" AND D.RULE_TYPE       = 'Alert'");
            stringBuilder.append(" AND C.CUST2RULEDEF    = D.OBJID");
            stringBuilder.append(" AND C.EXCLUDE         = 'N'");
            stringBuilder.append(" AND C.CUST2BUSORG     = O.OBJID");
            stringBuilder.append(" AND O.ORG_ID   =:customerId");
            query = session.createSQLQuery(stringBuilder.toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, strCustomer);
            List<Object> arlCreatedList = query.list();
            for (Iterator iterator = arlCreatedList.iterator(); iterator.hasNext();) {
                String creator = (String) iterator.next();
                objRuleCreatedByList = new ElementVO();
                objRuleCreatedByList.setId(creator);
                objRuleCreatedByList.setName(creator);
                arlRuleCreatedByList.add(objRuleCreatedByList);
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_CREATEDBY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRuleCreatedByList;
    }

    /**
     * @param String
     *            strLanguage,String strCustomer
     * @return list of ElementVO
     * @throws RMDServiceException
     * @Description:This method will FETCH Created by based on external customer
     */
    @Override
    public List<ElementVO> getRuleLastUpdatedByExternal(String strLanguage, String strCustomer) throws RMDDAOException {

        List<ElementVO> arlRuleLastUpdatedByList = new ArrayList<ElementVO>();
        ElementVO objRuleLastUpdatedByList = null;
        Session session = null;
        StringBuilder stringBuilder = null;
        Query query = null;
        try {

            session = getHibernateSession();
            stringBuilder = new StringBuilder();
            stringBuilder.append(
                    " SELECT DISTINCT F.LAST_UPDATED_BY FROM GETS_TOOL_DPD_FINRUL F,GETS_TOOL_DPD_RULEDEF D,GETS_TOOL_DPD_CUST C,SA.TABLE_BUS_ORG O");
            stringBuilder.append(" WHERE D.RULEDEF2FINRUL  = F.OBJID");
            stringBuilder.append(" AND D.RULE_TYPE       = 'Alert'");
            stringBuilder.append(" AND C.CUST2RULEDEF    = D.OBJID");
            stringBuilder.append(" AND C.EXCLUDE         = 'N'");
            stringBuilder.append(" AND C.CUST2BUSORG     = O.OBJID");
            stringBuilder.append(" AND O.ORG_ID   =:customerId");
            query = session.createSQLQuery(stringBuilder.toString());
            query.setParameter(RMDCommonConstants.CUSTOMER_ID, strCustomer);
            List<Object> arlLastUpdated = query.list();
            if (arlLastUpdated != null && !arlLastUpdated.isEmpty()) {
                for (Iterator iterator = arlLastUpdated.iterator(); iterator.hasNext();) {
                    String updator = (String) iterator.next();
                    objRuleLastUpdatedByList = new ElementVO();
                    objRuleLastUpdatedByList.setId(updator);
                    objRuleLastUpdatedByList.setName(updator);
                    arlRuleLastUpdatedByList.add(objRuleLastUpdatedByList);
                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_LAST_UPDATED_BY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_EXTERNAL_LAST_UPDATED_BY);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return arlRuleLastUpdatedByList;
    }
	/**
	 * @Author:iGATE
	 * @return
	 * @Description: This method will return the SDP parameters and its values
	 */
	public List<ParameterServiceVO> getGeoLocationValues(String customer,String family,
			String strLanguage) {
		List<ParameterServiceVO> lstParameterServiceVO = null;
		Session session = null;
		StringBuilder queryString = new StringBuilder();
		List<Object[]> listGeoLocationValues = null;
		List<BigDecimal> listGeoLocationParameter = null;
		Query geoQuery = null;
		Query geoLocQuery = null;
		ParameterServiceVO objParameterServiceVO = null;
		String geoLocationObjid=RMDCommonConstants.EMPTY_STRING;
		try {
			session = getHibernateSession();
			queryString
					.append("  SELECT GEOFENCE_ID,PROXIMITY_DESC")
					.append("  FROM GETS_MCS_PP_CALC_GEOFENCE_DEF GD,GETS_RMD_PP_PROXIMITY_DEF PD,TABLE_BUS_ORG TB")
					.append("  WHERE PPCALC_GEO_DEF2PROX_DEF = PD.OBJID AND PD.PROXIMITY2BUS_ORG = TB.OBJID AND PD.ACTIVE_FLAG='Y' AND GD.ACTIVE_FLAG='Y'");
			if (!RMDCommonUtility.isNullOrEmpty(customer)) {
				queryString
						.append(" AND TB.ORG_ID IN (:customer) ");
				geoQuery = session.createSQLQuery(queryString.toString());
				List<String> customerLst = Arrays
						.asList(customer.split(","));
				geoQuery.setParameterList(RMDCommonConstants.CUSTOMER,customerLst);
			} else {
				geoQuery = session.createSQLQuery(queryString.toString());
			}
			StringBuilder geoQueryString = new StringBuilder();
			geoQueryString
			.append("  SELECT DISTINCT COLNAME.objid FROM GETS_TOOL_DPD_COLNAME COLNAME")
			.append("  WHERE COLNAME.PARM_TYPE='Geolocation'");
			
			if("ACCCA".equalsIgnoreCase(family)||"DCCCA".equalsIgnoreCase(family)){		
				
				family="CCA";
			}
			
			if (!RMDCommonUtility.isNullOrEmpty(family)) {
				geoQueryString
						.append(" AND FAMILY=:family ");
				geoLocQuery = session.createSQLQuery(geoQueryString.toString());
				geoLocQuery.setParameter(RMDCommonConstants.FAMILY,
						family);
			} else {
				geoLocQuery = session.createSQLQuery(geoQueryString.toString());
			}
			listGeoLocationParameter=geoLocQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listGeoLocationParameter)) {				
				
					geoLocationObjid=String.valueOf(listGeoLocationParameter.get(0));
			}
			listGeoLocationValues = geoQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listGeoLocationValues)) {
				lstParameterServiceVO = new ArrayList<ParameterServiceVO>();
				for (Object[] objValues : listGeoLocationValues) {
					objParameterServiceVO = new ParameterServiceVO();
					objParameterServiceVO.setParameterNameID(geoLocationObjid);
					objParameterServiceVO.setParameterName(objValues[1]
							.toString());
					objParameterServiceVO.setValue(objValues[0].toString());
					objParameterServiceVO
							.setColumnType(RMDServiceConstants.GEO);
					lstParameterServiceVO.add(objParameterServiceVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_GEO_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_GEO_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstParameterServiceVO;
	}
	
	/**
	 * @Author:iGATE
	 * @return
	 * @Description: This method will return the SDP parameters and its values
	 */
	public List<ParameterServiceVO> getATSParameters(String family,
			String strLanguage,String uom,String customer) {
		List<ParameterServiceVO> lstParameterServiceVO = null;
		Session session = null;
		StringBuilder atsQueryString = new StringBuilder();
		List<Object[]> listGeoLocationValues = null;
		Query atsQuery = null;
		ParameterServiceVO objParameterServiceVO = null;
		Map<String,UnitOfMeasureVO> mpUom = null;
		try {
			session = getHibernateSession();
			mpUom=convertSourceToTarget(uom);	
			atsQueryString
			.append("  SELECT DISTINCT COLNAME.OBJID,COLNAME.COLUMN_NAME,SOURCE_UOM_ID")
			.append("  FROM GETS_TOOL_DPD_COLNAME COLNAME,GETS_RMD_PARMDEF DEF,GETS_TOOL_CE_PARM_INFO_NEW INFO,GETS_TOOL_CE_PARM_INFO_EXTAPP EXT,TABLE_BUS_ORG ORG")
			.append("  WHERE DEF.RULE_PARM_DESC        =COLNAME.COLUMN_NAME")
			.append("  AND INFO.PARM_INFO2PARMDEF     =DEF.OBJID")
			.append("  AND INFO.OBJID                 =EXT.EXTAPP2CE_PARM_INFO AND ext.extapp2bus_org       =ORG.OBJID")
			.append("  AND INFO.ATS_COL_AVAIL='Y'")
			.append("  AND COLNAME.PARM_TYPE = 'ATS'");
			if (!RMDCommonUtility.isNullOrEmpty(family)) {
				atsQueryString
						.append(" AND COLNAME.FAMILY=:family ");				
			} 
			
			if (!RMDCommonUtility.isNullOrEmpty(customer)) {
				atsQueryString
						.append(" AND ORG.ORG_ID     IN (:customer) ");
			} 	
			
			atsQueryString
			.append(" ORDER BY COLUMN_NAME");
			atsQuery = session.createSQLQuery(atsQueryString.toString());
			if (!RMDCommonUtility.isNullOrEmpty(family)) {								
				atsQuery.setParameter(RMDCommonConstants.FAMILY,
						family);
			} 
			
			if (!RMDCommonUtility.isNullOrEmpty(customer)) {	
				String[] tmpStrCustomer = customer
						.split(RMDCommonConstants.COMMMA_SEPARATOR);
				atsQuery.setParameterList(
						RMDCommonConstants.CUSTOMER, tmpStrCustomer);
			} 
			listGeoLocationValues = atsQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listGeoLocationValues)) {
				lstParameterServiceVO = new ArrayList<ParameterServiceVO>();
				for (Object[] objValues : listGeoLocationValues) {
					objParameterServiceVO = new ParameterServiceVO();
					objParameterServiceVO.setParameterNameID(objValues[0]
							.toString());
					objParameterServiceVO.setParameterName(objValues[1]
							.toString());					
					objParameterServiceVO
							.setColumnType(RMDServiceConstants.ATS);
					String sourceUom=RMDCommonUtility.convertObjectToString(objValues[2]);
					if (null != mpUom
							&& !mpUom.isEmpty()
							&& null != sourceUom
							&& !RMDCommonConstants.EMPTY_STRING
									.equalsIgnoreCase(sourceUom)) {
						UnitOfMeasureVO objMeasureVO = mpUom
								.get(sourceUom);
						if(null!=objMeasureVO){
						if (null != uom
								&& !RMDCommonConstants.EMPTY_STRING
										.equalsIgnoreCase(uom)
								&& !RMDCommonConstants.US
										.equalsIgnoreCase(uom)&&RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objMeasureVO.getIsConversionRequired())) {
							objParameterServiceVO
									.setUomAbbr(objMeasureVO
											.getTargetAbbr());
						} else {
							objParameterServiceVO
									.setUomAbbr(objMeasureVO
											.getSourceAbbr());
						}
						}
					}
					lstParameterServiceVO.add(objParameterServiceVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_ATS_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_ATS_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstParameterServiceVO;
	}
	
	
	/**
	 * @Author:iGATE
	 * @return
	 * @Description: This method will return the SDP parameters and its values
	 */
	public List<ElementVO> getCustomerForRule(String ruleId,
			String strLanguage) {
		List<ElementVO> lstElementVO = null;
		Session session = null;
		StringBuilder qryString = new StringBuilder();		
		Query query = null;
		ElementVO objElementVO = null;
		List<Object[]> listCustomer = null;

		try {
			session = getHibernateSession();
			qryString
			.append("  SELECT DISTINCT CUST.OBJID, CUST.CUST2BUSORG,CUST.EXCLUDE,CUST.CUST2RULEDEF,BUSORG.S_ORG_ID FROM GETS_TOOL_DPD_CUST CUST,TABLE_BUS_ORG BUSORG,GETS_TOOL_DPD_RULEDEF RULEDEF")
			.append("  WHERE CUST.CUST2BUSORG=BUSORG.OBJID AND CUST.CUST2RULEDEF=RULEDEF.OBJID");
			if (!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				qryString
						.append(" AND RULEDEF.RULEDEF2FINRUL=:ruleId");
				query = session.createSQLQuery(qryString.toString());
				query.setParameter(RMDCommonConstants.RULEID,
						ruleId);
			} else {
				query = session.createSQLQuery(qryString.toString());
			}
			listCustomer = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listCustomer)) {
				lstElementVO = new ArrayList<ElementVO>();
				for (Object[] objValues : listCustomer) {
					objElementVO = new ElementVO();
					objElementVO.setCustomerSeqId(objValues[1]
							.toString());
					objElementVO.setName(objValues[4]
							.toString());					
					
					lstElementVO.add(objElementVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstElementVO;
	}

	/**
	 * @Author:iGATE
	 * @return
	 * @Description: This method will return the SDP parameters and its values
	 */
	public List<ParameterServiceVO> getColValues(String strFamily,
			String strLanguage) {
		List<ParameterServiceVO> lstParameterServiceVO = null;
		Session session = null;
		StringBuilder queryString = new StringBuilder();
		List<Object[]> listSDPValues = null;
		Query sdpQuery = null;
		ParameterServiceVO objParameterServiceVO = null;

		try {
			session = getHibernateSession();
			queryString = RMDServiceConstants.QueryConstants.FETCH_COL_VALUES;
			if (!RMDCommonUtility.isNullOrEmpty(strFamily)) {
				queryString
						.append("  AND LOWER(CLMNAM.FAMILY)=LOWER(:FAMILY) ");
				sdpQuery = session.createSQLQuery(queryString.toString());
				sdpQuery.setParameter(RMDCommonConstants.FAMILY_INITIAL_LOADER,
						strFamily);
			} else {
				sdpQuery = session.createSQLQuery(queryString.toString());
			}
			listSDPValues = sdpQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(listSDPValues)) {
				lstParameterServiceVO = new ArrayList<ParameterServiceVO>();
				for (Object[] objValues : listSDPValues) {
					objParameterServiceVO = new ParameterServiceVO();
					objParameterServiceVO.setParameterNameID(objValues[0]
							.toString());
					objParameterServiceVO.setParameterName(objValues[1]
							.toString());
					objParameterServiceVO.setValue(objValues[2].toString());
					objParameterServiceVO.setDisplayName(objValues[3].toString());
					objParameterServiceVO
							.setColumnType(RMDServiceConstants.ENG);
					lstParameterServiceVO.add(objParameterServiceVO);
				}
			}

		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_SDP_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_SDP_VALUES);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return lstParameterServiceVO;
	}
	
	/**
	  * This method will return the list of alert firing for last 24 hrs.
	  * @param strLanguage
	  * @return List<AlertRuleVO>
	  * @author 212556286
	  */
	public List<AlertRuleVO> getRecentAurizonFiring(String strLanguage,
			String isGECustomer, String ruleType, String family, String ruleId,
			String ruleTitle, String roadHdr, String roadNo, String vehicleId,
			String dateStart, String dateEnd, String strCustomer) {
		
		 List<AlertRuleVO> alertRuleData = null;
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 List<Object[]> listAlerts = null;
		 AlertRuleVO alertRuleVO = null;
		 String[] vehicle = null;
		 LOG.debug("in getRecentAurizonFiring, isGECustomer "+isGECustomer+" , ruleId "+ruleId+" , strCustomer "+strCustomer);
		 try {
			 if((roadHdr!=null && roadHdr!="") || (roadNo!=null && roadNo!=""))
				 vehicle = getVehicle(strLanguage, roadHdr, roadNo);

			session = getHibernateSession();
			queryString = queryString
						 .append("SELECT DISTINCT AF.OBJID,  FR.OBJID AS RULEID, FR.RULE_TITLE, FR.FAMILY, RH.VERSION_NO, V.OBJID, SP.X_VEH_HDR_CUST, SP.SERIAL_NO, TO_CHAR(AF.CREATION_DATE,'DD-MON-YY hh24:mi:ss') CREATION_DATE, R.DIAG_SERVICE_ID, AH.ALERT_SENT_TIME,TBO.ORG_ID ")
						 .append("FROM GETS_TOOL_ALERT_FIRING AF, GETS_TOOL_DPD_FINRUL FR, GETS_TOOL_DPD_RULEDEF RD, GETS_TOOL_DPD_RULHIS RH, GETS_RMD_VEHICLE V, SA.TABLE_SITE_PART SP, GETS_RMD_VEH_HDR VH, GETS_TOOL_SUBRUN SR, GETS_TOOL_RUN R, GETS_TOOL_ALERT_HISTORY AH,TABLE_BUS_ORG TBO ");
			
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append(",GETS_TOOL_DPD_CUST C ");
			}
			queryString = queryString
					.append("WHERE RD.OBJID = AF.FIRING2RULE_DEFN AND RD.RULEDEF2FINRUL = FR.OBJID AND RH.RULHIS2FINRUL = FR.OBJID AND V.OBJID = AF.FIRING2VEHICLE  AND RH.ACTIVE=1 ")
					.append("AND SP.OBJID = V.VEHICLE2SITE_PART AND VH.VEH_HDR2BUSORG = TBO.OBJID  AND VH.OBJID = V.VEHICLE2VEH_HDR ")
					.append("AND SR.OBJID = AF.FIRING2SUBRUN AND R.OBJID=SR.SUBRUN2RUN AND SR.SUBRUN2RUN=AH.ALERTHISTORY2RUN(+) AND RH.ORIGINAL_ID =AH.ORIGINAL_ID(+) ");
	
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				 queryString = queryString
						 .append(" AND C.CUST2RULEDEF = RD.OBJID AND C.CUST2BUSORG = VH.VEH_HDR2BUSORG AND TBO.ORG_ID = :CUSTOMER ");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(family))
			 {
				 queryString = queryString.append(" AND FR.FAMILY IN (:family)");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(ruleId))
			 {
				 queryString = queryString.append(" AND FR.OBJID = :ruleId ");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(ruleTitle)) {
				 queryString = queryString.append(" AND LOWER(FR.RULE_TITLE) LIKE LOWER(:ruleTitle) ");
			 }

			 if(null!=vehicle && vehicle.length>0) {
					 queryString = queryString.append(" AND AF.FIRING2VEHICLE IN (:vehNum) ");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(dateStart) && !RMDCommonUtility.isNullOrEmpty(dateEnd)) {
				 queryString = queryString.append(" AND AF.CREATION_DATE >= TO_DATE(:dateStart,'MM/DD/YYYY HH24:MI:SS') ")
						 .append("AND AF.CREATION_DATE <= TO_DATE(:dateEnd,'MM/DD/YYYY HH24:MI:SS')");
			 } else
				 queryString = queryString.append(" AND AF.CREATION_DATE > SYSDATE-1 ");

			 queryString = queryString.append(" ORDER BY CREATION_DATE DESC");
			 query = session.createSQLQuery(queryString.toString());
			 if(!RMDCommonUtility.isNullOrEmpty(family)) {
				 query.setParameterList(RMDCommonConstants.FAMILY, family.split(","));
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				 query.setParameter(RMDCommonConstants.RULEID, ruleId);
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(ruleTitle)) {
				 query.setParameter(RMDCommonConstants.RULETITLE, RMDServiceConstants.PERCENTAGE+ruleTitle+RMDServiceConstants.PERCENTAGE);
			 }

			 if(null!=vehicle && vehicle.length>0) {
				 query.setParameterList(RMDCommonConstants.VEH_NUM, vehicle);
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(dateStart) && !RMDCommonUtility.isNullOrEmpty(dateEnd)) {
				 query.setParameter(RMDCommonConstants.DATE_START, dateStart);
				 query.setParameter(RMDCommonConstants.DATE_END, dateEnd);
			 }
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				query.setParameter(RMDServiceConstants.CUSTOMER, strCustomer);
			}
			 listAlerts = query.list();

			 if(RMDCommonUtility.isCollectionNotEmpty(listAlerts)) {
				 alertRuleData = new ArrayList<AlertRuleVO>();
				 for(Object[] object:listAlerts) {
					 alertRuleVO = new AlertRuleVO();
					 alertRuleVO.setFireId(RMDCommonUtility.convertObjectToString(object[0]));
					 alertRuleVO.setRuleId(RMDCommonUtility.convertObjectToString(object[1]));
					 alertRuleVO.setTitle(RMDCommonUtility.convertObjectToString(object[2]));
					 alertRuleVO.setFamily(RMDCommonUtility.convertObjectToString(object[3]));
					 alertRuleVO.setVersion(RMDCommonUtility.convertObjectToString(object[4]));
					 alertRuleVO.setVehObjId(RMDCommonUtility.convertObjectToString(object[5]));
					 alertRuleVO.setVehHdr(RMDCommonUtility.convertObjectToString(object[6]));
					 alertRuleVO.setVehSerialNo(RMDCommonUtility.convertObjectToString(object[7]));
					 alertRuleVO.setCreatedOn(RMDCommonUtility.convertObjectToString(object[8]));
					 alertRuleVO.setDiagService(RMDCommonUtility.convertObjectToString(object[9]));					 
					 String alertStatus = RMDCommonUtility.convertObjectToString(object[10]);
					 alertRuleVO.setStrCustomer(RMDCommonUtility.convertObjectToString(object[11]));
					 if(!RMDCommonUtility.isNullOrEmpty(alertStatus))
						 alertRuleVO.setAlertStatus(RMDCommonConstants.STRING_NO);
					 else
						 alertRuleVO.setAlertStatus(RMDCommonConstants.STRING_YES);
					 alertRuleData.add(alertRuleVO);					
				 }
			 }


		 }catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRuleData;
	 }


	 /**
	  * Used to get vehicle objid based on vehHdr and RoadNo
	  * @param strLanguage
	  * @param vehHdr
	  * @param roadNo
	  * @return String[]
	  */
	 public String[] getVehicle(String strLanguage, String vehHdr, String roadNo) {
		 Session session = null;
		 String vehicle = "";
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 List<?> dataList = null;
		 String[] result = null;

		 try {			
			 session = getHibernateSession();
			 if(!RMDCommonUtility.isNullOrEmpty(vehHdr)) {
				 if(!RMDCommonUtility.isNullOrEmpty(roadNo)) {
					 queryString = queryString.append("select vehicle_objid from gets_rmd_cust_rnh_rn_v where vehicle_hdr=:vehicleHeader and vehicle_no=:roadNumber ");
				 } else {
					 queryString = queryString.append("select vehicle_objid from gets_rmd_cust_rnh_rn_v where vehicle_hdr=:vehicleHeader");
				 }
			 } else {
				 if(!RMDCommonUtility.isNullOrEmpty(roadNo)) {
					 queryString = queryString.append("select vehicle_objid from gets_rmd_cust_rnh_rn_v where vehicle_no=:roadNumber");
				 }
			 }

			 if(null!=queryString && queryString.length()>0) {				
				 query = session.createSQLQuery(queryString.toString());	
				 if(!RMDCommonUtility.isNullOrEmpty(vehHdr)) {
					 if(!RMDCommonUtility.isNullOrEmpty(roadNo)) {
						 query.setParameter(RMDCommonConstants.VEHICLE_HEADER, vehHdr);
						 query.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNo);
					 } else {
						 query.setParameter(RMDCommonConstants.VEHICLE_HEADER, vehHdr);
					 }
				 } else {
					 if(!RMDCommonUtility.isNullOrEmpty(roadNo)) {
						 query.setParameter(RMDCommonConstants.ROAD_NUMBER, roadNo);
					 }
				 }

				 dataList= query.list();
				 if(!dataList.isEmpty()) {
					 result = new String[dataList.size()];
					 vehicle = "";
					 for(int index=0; index<dataList.size(); index ++) {
						 String veh = ((BigDecimal) dataList.get(index)).toString();
						 vehicle = vehicle.concat("'").concat(veh).concat("',");	

						 result[index] = veh;
					 }
				 } else {
					 vehicle = RMDCommonConstants.NO_DATA;
				 }

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle)) {
					 vehicle = vehicle.substring(0, vehicle.length()-1);
				 }
			 }

		 } catch (RMDDAOConnectionException ex) {			 
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }

		 return result;
	 }
	 /**
	  * This method will return the list of alert runs
	  * @param strLanguage
	  * @return List<AlertRunsVO>
	  * @author 212556286
	  */
	public List<AlertRunsVO> getAlertRuns(String strLanguage,
			String isGECustomer, String vehHdr, String roadNo,
			String diagService, String dateStart, String dateEnd,
			String strCustomer) {

		 List<AlertRunsVO> alertRunData = new ArrayList<AlertRunsVO>();
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 List<Object[]> listAlertRuns = null;
		 AlertRunsVO alertRunsVO = null;
		 LOG.debug("in getAlertRuns, isGECustomer "+isGECustomer+" , strCustomer "+strCustomer);
		 try {

			 String[] vehicleId = null;
			 if((vehHdr!=null && vehHdr!="") || (roadNo!=null && roadNo!=""))
				 vehicleId = getVehicle(strLanguage, vehHdr, roadNo);

			 session = getHibernateSession();

			queryString = queryString
					.append("SELECT R.OBJID, TO_CHAR(R.CREATION_DATE,'DD-MON-YY hh24:mi:ss') CREATION_DATE, R.RUN2VEHICLE, SP.X_VEH_HDR_CUST, SP.SERIAL_NO, M.FAMILY, null, null,TBO.ORG_ID ")
					.append("FROM GETS_TOOL_RUN R, GETS_TOOL_SUBRUN SR, GETS_RMD_VEHICLE V, SA.TABLE_SITE_PART SP, GETS_RMD_MODEL M, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ")
					.append("WHERE UPPER(R.DIAG_SERVICE_ID) = 'ALERT' AND R.RUN_CPT IS NOT NULL AND SR.SUBRUN2RUN = R.OBJID AND V.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ")
					.append("AND V.OBJID = R.RUN2VEHICLE AND SP.OBJID = V.VEHICLE2SITE_PART AND M.OBJID = V.VEHICLE2MODEL AND SR.TOOL_ID = 'JDPAD' ");

			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append(" AND TBO.ORG_ID = :CUSTOMER ");
			}

			 if(null!=vehicleId  && vehicleId.length>0) {
					 queryString = queryString.append(" AND R.RUN2VEHICLE IN ( :vehNum ) ");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(dateStart) && !RMDCommonUtility.isNullOrEmpty(dateEnd)) {
				 if(dateStart.length()>0 && dateEnd.length()>0) {
					 queryString = queryString.append(" AND R.CREATION_DATE >= TO_DATE(:dateStart,'MM/DD/YYYY HH24:MI:SS') ")
							 .append(" AND R.CREATION_DATE <= TO_DATE(:dateEnd,'MM/DD/YYYY HH24:MI:SS')");
				 }
			 } else {
				 queryString = queryString.append(" AND R.CREATION_DATE > SYSDATE-0.042 ");
			 }

			 queryString = queryString.append(" ORDER BY CREATION_DATE DESC");
			 query = session.createSQLQuery(queryString.toString());

			 if(null!=vehicleId && vehicleId.length>0) {
					 query.setParameterList(RMDCommonConstants.VEH_NUM, vehicleId);
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(dateStart) && !RMDCommonUtility.isNullOrEmpty(dateEnd)) {
				 query.setParameter(RMDCommonConstants.DATE_START, dateStart);
				 query.setParameter(RMDCommonConstants.DATE_END, dateEnd);
			 }
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				query.setParameter(RMDServiceConstants.CUSTOMER, strCustomer);
			}
			 listAlertRuns = query.list();
			 if(RMDCommonUtility.isCollectionNotEmpty(listAlertRuns)) {
				 for(Object[] object:listAlertRuns) {
					 alertRunsVO = new AlertRunsVO();
					 alertRunsVO.setRunObjId(RMDCommonUtility.convertObjectToString(object[0]));
					 alertRunsVO.setRunDate(RMDCommonUtility.convertObjectToString(object[1]));
					 alertRunsVO.setRun2Vehicle(RMDCommonUtility.convertObjectToString(object[2]));
					 alertRunsVO.setVehHdrCust(RMDCommonUtility.convertObjectToString(object[3]));
					 alertRunsVO.setSerialNo(RMDCommonUtility.convertObjectToString(object[4]));
					 String family=RMDCommonUtility.convertObjectToString(object[5]);
					 if(RMDCommonConstants.ACCCA.equalsIgnoreCase(family)||RMDCommonConstants.DCCCA.equalsIgnoreCase(family)){
						 family="CCA";
					 }
					 alertRunsVO.setFamily(family);
					 alertRunsVO.setRuleId(RMDCommonUtility.convertObjectToString(object[6]));
					 alertRunsVO.setRuleTitle(RMDCommonUtility.convertObjectToString(object[7]));
					 alertRunsVO.setStrCustomer(RMDCommonUtility.convertObjectToString(object[8]));
					 alertRunData.add(alertRunsVO);					
				 }
			 }
		 }catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRunData;
	 }

	 /**
	  * @Purpose Used to fetch list of rules for a family.
	  * @param strLanguage
	  * @param isGECustomer
	  * @param customerId
	  * @param family
	  * @return List of AlertRuleVO
	  * @author 212556286
	  */
	public List<AlertRuleVO> getAlertRulesForFamily(String strLanguage,
			String isGECustomer, String customerId, String family) {

		 List<AlertRuleVO> alertRuleData = null;
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 List<Object[]> listAlertRules = null;
		 AlertRuleVO alertRuleVO = null;
		 LOG.debug("in getAlertRulesForFamily, isGECustomer "+isGECustomer+" , customerId "+customerId);
		 try {
			 session = getHibernateSession();
			 
			queryString = queryString
					.append("SELECT FR.OBJID,FR.RULE_TITLE FROM GETS_TOOL_DPD_FINRUL FR,GETS_TOOL_DPD_RULEDEF RD,GETS_TOOL_DPD_RULHIS RH ");
			if (null != customerId
					&& !customerId.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append(",GETS_TOOL_DPD_CUST C,SA.TABLE_BUS_ORG TBO ");
			}
			queryString = queryString
					.append("WHERE RD.RULEDEF2FINRUL = FR.OBJID AND RH.RULHIS2FINRUL = FR.OBJID AND UPPER(RD.RULE_TYPE) = 'ALERT' AND RH.ACTIVE = 1 ");

			if (null != customerId
					&& !customerId.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append("AND C.CUST2RULEDEF = RD.OBJID AND C.CUST2BUSORG = TBO.OBJID AND TBO.ORG_ID = :CUSTOMER ");
			}
			 if(!RMDCommonUtility.isNullOrEmpty(family)) {
				 queryString = queryString.append("AND FR.FAMILY IN (:family)");
			 }
			 query = session.createSQLQuery(queryString.toString());

			 if(!RMDCommonUtility.isNullOrEmpty(family)) {
				 query.setParameterList(RMDCommonConstants.FAMILY, family.split(","));
			 }
			if (null != customerId
					&& !customerId.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				query.setParameter(RMDServiceConstants.CUSTOMER, customerId);
			}
			 listAlertRules = query.list();
			 alertRuleData = new ArrayList<AlertRuleVO>();
			 if(RMDCommonUtility.isCollectionNotEmpty(listAlertRules)) {				
				 for(Object[] object:listAlertRules) {
					 alertRuleVO = new AlertRuleVO();
					 alertRuleVO.setRuleId(RMDCommonUtility.convertObjectToString(object[0]));
					 alertRuleVO.setTitle(RMDCommonUtility.convertObjectToString(object[1]));
					 alertRuleData.add(alertRuleVO);					
				 }
			 }

		 }catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRuleData;
	 }

	 /**
	  * Used to get parm details for a rule selected for a run.
	  * @param strLanguage
	  * @param isGECustomer
	  * @param customerId
	  * @param runObjId
	  * @param ruleId
	  * @param family
	  * @return List of AlertRuleParmVO
	  * @author 212556286
	  */
	 private List<AlertRuleParmVO> getRuleParmData(String strLanguage, String ruleId, 
			 String family, String vehicle, String controllerId, String eoaMinLkBk, String eoaMaxLkBk, String atsMinLkBk, String atsMaxLkbK, String custUOM) {
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 Query query1 = null;
		 List<Object[]> listAlertParmData = null;
		 List<AlertRuleParmVO> alertRuleParmData = new ArrayList<AlertRuleParmVO>();
		 List<Object[]> listParmData = null;
		 AlertRuleParmVO alertParmVO = null;
		 List<String> parmNames = new ArrayList<String>();
		 List<String> fltParmNames = new ArrayList<String>();
		 List<String> parmDisplayNames = new ArrayList<String>();
		 List<String> parmDisplayNamesNew = new ArrayList<String>();
		 List<ParmMetricVO> parmDetailLst = new ArrayList<ParmMetricVO>();
		 List<String> parmAbbrLst = new ArrayList<String>();

		 Map<String,UnitOfMeasureVO> mpUom = null;
		 try {
			 if(custUOM!=null && custUOM!="") {
				 mpUom=convertSourceToTarget(custUOM);
			 }


			 // Fetches Run details..
			 session = getHibernateSession();
			 //Get ATS PARM DATA FOR RULE.
			 queryString = queryString
					 .append("SELECT DISTINCT C.OBJID, PLI.PARM_LOAD_COLUMN, PLI.PARM_LOAD_TABLE, C.COLUMN_NAME, C.PARM_TYPE, P.SOURCE_UOM_ID FROM GETS_RMD_PARMDEF P,GETS_RMD_PARMDEF_LOAD_INFO PLI,GETS_TOOL_DPD_COLNAME C ")
					 .append("WHERE C.COLUMN_NAME = P.RULE_PARM_DESC AND C.PARM_TYPE IN ('ATS','Geolocation') AND PLI.SOURCE_TYPE = 'ATS' AND PLI.PARM_OBJID = P.OBJID AND C.OBJID IN ")
					 .append("(SELECT DISTINCT SF.SIMFEA2COLNAME FROM GETS_TOOLS.GETS_TOOL_DPD_SIMFEA SF, GETS_TOOLS.GETS_TOOL_DPD_SIMRUL SR WHERE SF.SIMFEA2SIMRUL = SR.OBJID ");

			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				 queryString = queryString.append("AND SR.SIMRUL2FINRUL = :ruleId )");
			 }

			 query = session.createSQLQuery(queryString.toString());
			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				 query.setParameter(RMDCommonConstants.RULEID, ruleId);
			 }
			 listParmData = query.list();
			 StringBuilder atsFromCaluse = new StringBuilder();
			 StringBuilder atsWhereClause = new StringBuilder();
			 StringBuilder atsColNames = new StringBuilder();
			 List<String> atsParamNames = new ArrayList<String>();
			 if(RMDCommonUtility.isCollectionNotEmpty(listParmData)) {
				 for(int i=0 ; i<listParmData.size(); i++) {
					 Object[] obj =  listParmData.get(i);
					 atsFromCaluse = atsFromCaluse.append(",").append(obj[2].toString()).append(" ").append(" T").append(i);
					 if(obj[2].toString().indexOf("GETS_TOOL_PP_STATUS_HIST")<0)
					     atsWhereClause = atsWhereClause.append(" AND T").append(i).append(".GETS_TOOL_DP_HDR_ID = DPH.GETS_TOOL_DP_HDR_ID ");
					 else
					     atsWhereClause = atsWhereClause.append(" AND T").append(i).append(".OBJID = SH.OBJID ");
					 atsColNames = atsColNames.append(",T").append(i).append(".").append(obj[1].toString());
					 atsParamNames.add(RMDCommonUtility.convertObjectToString(obj[1]));
					 parmNames.add(RMDCommonUtility.convertObjectToString(obj[1]));
					 parmDisplayNames.add(RMDCommonUtility.convertObjectToString(obj[3]));

					 ParmMetricVO metricVO = new ParmMetricVO();
					 metricVO.setParamDBName(RMDCommonUtility.convertObjectToString(obj[1]));
					 metricVO.setParamDisplayName(RMDCommonUtility.convertObjectToString(obj[3]));
					 metricVO.setParamType(RMDCommonUtility.convertObjectToString(obj[4]));
					 BigDecimal objBigDecimal = (BigDecimal) obj[5];
					 if(objBigDecimal!=null)
						 metricVO.setSourceUOM(objBigDecimal.toString());
					 else
						 metricVO.setSourceUOM("");		

					 parmDetailLst.add(metricVO);
				 }
			 }

			 //GET EOA PARM DATA FOR RULE.
			 queryString = new StringBuilder();
			 queryString = queryString
					 .append("SELECT DISTINCT C.OBJID, P.PARM_LOAD_COLUMN, P.PARM_LOAD_TABLE, C.COLUMN_NAME, C.PARM_TYPE, P.SOURCE_UOM_ID FROM GETS_RMD_PARMDEF P,GETS_TOOL_DPD_COLNAME C ")
					 .append("WHERE C.COLUMN_NAME = P.RULE_PARM_DESC AND C.PARM_TYPE IN ('MP') ");

			 if(!RMDCommonUtility.isNullOrEmpty(controllerId)) {
				 queryString = queryString
						 .append("AND P.CONTROLLER_SOURCE_ID = :controllerId ");
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(family)) {
				 queryString = queryString.append(" AND C.FAMILY = :family ");
			 }
			 queryString = queryString.append(" AND C.OBJID IN (SELECT DISTINCT SF.SIMFEA2COLNAME FROM GETS_TOOLS.GETS_TOOL_DPD_SIMFEA SF,GETS_TOOLS.GETS_TOOL_DPD_SIMRUL SR WHERE SF.SIMFEA2SIMRUL = SR.OBJID ");

			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				 queryString=  queryString.append(" AND SR.SIMRUL2FINRUL = :ruleId )");
			 }

			 query = session.createSQLQuery(queryString.toString());
			 if(!RMDCommonUtility.isNullOrEmpty(controllerId)){
				 query.setParameter(RMDCommonConstants.CONTROLLER_ID, controllerId);
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(family)) {
				 query.setParameter(RMDCommonConstants.FAMILY, family); 
			 }

			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				 query.setParameter(RMDCommonConstants.RULEID, ruleId);
			 }
			 listParmData = query.list();

			 StringBuilder eoaFromCaluse = new StringBuilder();
			 StringBuilder eoaWhereClause = new StringBuilder();
			 StringBuilder eoaColNames = new StringBuilder();
			 List<String> eoaParmNames = new ArrayList<String>();
			 if(RMDCommonUtility.isCollectionNotEmpty(listParmData)) {
				 for(int i=0 ; i<listParmData.size(); i++) {
					 Object[] obj =  listParmData.get(i);
					 eoaFromCaluse = eoaFromCaluse.append(",").append(obj[2].toString()).append(" ").append(" T").append(i);
					 if(eoaWhereClause.length() > 0)
						 eoaWhereClause = eoaWhereClause.append(" AND T").append(i).append(".PARM2FAULT = T27.OBJID ");
					 else
						 eoaWhereClause = eoaWhereClause.append(" T").append(i).append(".PARM2FAULT = T27.OBJID ");
					 eoaColNames = eoaColNames.append(",T").append(i).append(".").append(obj[1].toString());
					 eoaParmNames.add(RMDCommonUtility.convertObjectToString(obj[1]));
					 parmNames.add(RMDCommonUtility.convertObjectToString(obj[1]));
					 fltParmNames.add(RMDCommonUtility.convertObjectToString(obj[1]));
					 parmDisplayNames.add(RMDCommonUtility.convertObjectToString(obj[3]));

					 ParmMetricVO metricVO = new ParmMetricVO();
					 metricVO.setParamDBName(RMDCommonUtility.convertObjectToString(obj[1]));					
					 metricVO.setParamDisplayName(RMDCommonUtility.convertObjectToString(obj[3]));
					 metricVO.setParamType(RMDCommonUtility.convertObjectToString(obj[4]));
					 BigDecimal objBigDecimal = (BigDecimal) obj[5];
					 if(objBigDecimal!=null)
						 metricVO.setSourceUOM(objBigDecimal.toString());
					 else
						 metricVO.setSourceUOM("");		

					 parmDetailLst.add(metricVO);
				 }
			 }
			 int isAtsFaultDataExist = 0;
			 //GET ATS DATA RECORD FETCH..
			 if(!RMDCommonUtility.isNullOrEmpty(atsColNames.toString())
					 && !RMDCommonUtility.isNullOrEmpty(atsFromCaluse.toString())
					 && !RMDCommonUtility.isNullOrEmpty(atsWhereClause.toString())) {

				 queryString = new StringBuilder();
				 queryString = queryString
						 .append("SELECT HM.GETS_TOOL_DP_PP_HIST_MAP_ID, 'ATS' AS RECORD_TYPE, TO_CHAR(SH.CMU_TIME,'DD-MON-YY hh24:mi:ss') CMU_TIME, TO_CHAR(DPH.INCIDENT_TIMESTAMP,'DD-MON-YY hh24:mi:ss') INCIDENT_TIMESTAMP, TO_CHAR(SH.CREATION_DATE,'DD-MON-YY hh24:mi:ss') CREATION_DATE ")
						 .append(atsColNames)
						 .append(" FROM GETS_TOOL_DP_PP_HIST_MAP HM,  GETS_TOOL_PP_STATUS_HIST SH,  GETS_TOOL_DP_HDR DPH ")
						 .append(atsFromCaluse)
						 .append(" WHERE HM.DPMAPPING2PPHIST  = SH.OBJID  AND HM.DPMAPPING2DATAPACK  = DPH.GETS_TOOL_DP_HDR_ID")
						 .append(atsWhereClause);

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 queryString = queryString.append(" AND DPH.PARM2VEHICLE = :vehicle ");

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 queryString =  queryString.append(" AND SH.STATUS_HIST2VEHICLE = :vehicle");

				 if(!RMDCommonUtility.isNullOrEmpty(atsMinLkBk))
					 queryString =  queryString.append(" AND GETS_TOOL_DP_PP_HIST_MAP_ID >= :minLookBack");

				 if(!RMDCommonUtility.isNullOrEmpty(atsMaxLkbK))
					 queryString = queryString.append(" AND GETS_TOOL_DP_PP_HIST_MAP_ID <= :maxLookBack");

				 queryString = queryString.append(" ORDER BY CMU_TIME DESC ");


				 query1 = session.createSQLQuery(queryString.toString());

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 query1.setParameter(RMDCommonConstants.VEHICLE, vehicle);

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 query1.setParameter(RMDCommonConstants.VEHICLE, vehicle);

				 if(!RMDCommonUtility.isNullOrEmpty(atsMinLkBk))
					 query1.setParameter(RMDCommonConstants.MIN_LOOKBACK, atsMinLkBk);					 

				 if(!RMDCommonUtility.isNullOrEmpty(atsMaxLkbK))
					 query1.setParameter(RMDCommonConstants.MAX_LOOKBACK, atsMaxLkbK);					

				 listAlertParmData = query1.list();
				 
				 if(RMDCommonUtility.isCollectionNotEmpty(listAlertParmData)) {
					 isAtsFaultDataExist = 1;
					 for(Object[] object:listAlertParmData) {
						 alertParmVO = new AlertRuleParmVO();
						 alertParmVO.setFaultObjId(object[0].toString());
						 String recType = "";
						 if(object[1]!=null) {
							 if("A".equalsIgnoreCase(object[1].toString()))
								 recType = "ATS";
							 else
								 recType = object[1].toString();
						 }

						 alertParmVO.setRecordType(recType);
						 alertParmVO.setCmuTime(RMDCommonUtility.convertObjectToString(object[2]));
						 alertParmVO.setIncidentTime(RMDCommonUtility.convertObjectToString(object[3]));
						 alertParmVO.setParmNames(parmNames);
						 List<RuleParmVO> parmData = new ArrayList<RuleParmVO>();
						 parmDisplayNamesNew = new ArrayList<String>();

						 //Loop ATS Parm Names..
						 for(int i=0;i<atsParamNames.size();i++) {
							 int index = 5+i;
							 String parmName = atsParamNames.get(i).toString();
							 //Loop thru parmDetails list..
							 for(ParmMetricVO parmMetricVO:parmDetailLst) {
								 ParmMetricVO metricObj = parmMetricVO;
								 if(parmName.equalsIgnoreCase(metricObj.getParamDBName())) {
									 String parmAbbr = "";
									 RuleParmVO parmvo = new RuleParmVO();
									 parmvo.setParmName(parmName);
									 if(object[index]!=null) {
										 parmvo.setParmValue(RMDCommonUtility.convertObjectToString(object[index]));
									 } else
										 parmvo.setParmValue("");
									 String srcUOM = metricObj.getSourceUOM();
									 if(null!=custUOM && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(custUOM)
											 && !RMDCommonConstants.US.equalsIgnoreCase(custUOM) && alertParmVO.getRecordType().equalsIgnoreCase(metricObj.getParamType())){
										 if(null!=srcUOM && srcUOM!=""){
											 UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(srcUOM);
											 if (null != objUnitOfMeasureVO && RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objUnitOfMeasureVO.getIsConversionRequired())
													 && parmvo.getParmValue()!=null && parmvo.getParmValue()!="") {
												 String conversionFormula=objUnitOfMeasureVO.getConversionExp();
												 parmvo.setParmValue(AppSecUtil.convertMeasurementSystem(conversionFormula,parmvo.getParmValue()));
												 parmAbbr = objUnitOfMeasureVO.getTargetAbbr();

											 }else{
												 parmAbbr = objUnitOfMeasureVO.getTargetAbbr();
											 }
										 }	
									 } else {
										 if(null!=srcUOM) {
											 UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(srcUOM);
											 if (null != objUnitOfMeasureVO) {	
												 parmAbbr = objUnitOfMeasureVO.getSourceAbbr();
											 }
										 }	
									 }


									 parmAbbrLst.add(parmAbbr);
									 parmvo.setUnitAbbr(parmAbbr);
									 if(!RMDCommonUtility.isNullOrEmpty(parmAbbr)
											 && null!=custUOM && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(custUOM)
											 && alertParmVO.getRecordType().equalsIgnoreCase(metricObj.getParamType()))
										 parmDisplayNamesNew.add(parmDisplayNames.get(i).concat(" (").concat(parmAbbr).concat(")"));
									 else {
										 parmDisplayNamesNew.add(parmDisplayNames.get(i));
									 }

									 parmData.add(parmvo);
								 }
							 }
						 }

						 alertParmVO.setParmDisplayNames(parmDisplayNamesNew);
						 alertParmVO.setParmSet(parmData);
						 alertRuleParmData.add(alertParmVO);
					 }				
				 }
			 }

			 //GET EOA DATA RECORD FETCH..
			 int parmDisplaycount = 0;
			 if(!RMDCommonUtility.isNullOrEmpty(eoaColNames.toString())
					 && !RMDCommonUtility.isNullOrEmpty(eoaFromCaluse.toString())
					 && !RMDCommonUtility.isNullOrEmpty(eoaWhereClause.toString())) {
				 queryString = new StringBuilder();
				 queryString = queryString
						 .append(" WITH T27 AS ")
						 .append(" (SELECT ")
						 .append(" /*+ use_nl(f) */ ");

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 queryString = queryString.append(" F.* FROM GETS_TOOL_FAULT F WHERE FAULT2VEHICLE = :vehicle");

				 if(!RMDCommonUtility.isNullOrEmpty(eoaMinLkBk))
					 queryString = queryString.append(" AND F.OBJID >= :minLookBack");

				 if(!RMDCommonUtility.isNullOrEmpty(eoaMaxLkBk))
					 queryString = queryString.append(" AND F.OBJID <= :maxLookBack");

				 queryString = queryString.append(" AND RECORD_TYPE IN (SELECT FAULT_RECORD_TYPE FROM GETS_RMD_SVC_TO_FAULT WHERE UPPER(DIAG_SERVICE_ID)='ALERT' )) ")
						 .append(" SELECT /*+ USE_NL(t27 mp1 mp2 mp3) */ ")
						 .append(" T27.OBJID, T27.RECORD_TYPE, T27.FAULT_CODE, TO_CHAR(T27.OFFBOARD_LOAD_DATE,'DD-MON-YY hh24:mi:ss') OFFBOARD_LOAD_DATE, TO_CHAR(T27.CMU_TIME,'DD-MON-YY hh24:mi:ss') CMU_TIME ")
						 .append(eoaColNames)
						 .append(" FROM T27 ").append(eoaFromCaluse)
						 .append(" WHERE ").append(eoaWhereClause);
				 queryString.append(" ORDER BY CMU_TIME DESC");

				 query1 = session.createSQLQuery(queryString.toString());

				 if(!RMDCommonUtility.isNullOrEmpty(vehicle))
					 query1.setParameter(RMDCommonConstants.VEHICLE, vehicle);

				 if(!RMDCommonUtility.isNullOrEmpty(eoaMinLkBk))
					 query1.setParameter(RMDCommonConstants.MIN_LOOKBACK, eoaMinLkBk);

				 if(!RMDCommonUtility.isNullOrEmpty(eoaMaxLkBk))
					 query1.setParameter(RMDCommonConstants.MAX_LOOKBACK, eoaMaxLkBk);

				 listAlertParmData = query1.list();
				 if(RMDCommonUtility.isCollectionNotEmpty(listAlertParmData)) {
						
					 for(Object[] object:listAlertParmData) {
						 alertParmVO = new AlertRuleParmVO();
						 alertParmVO.setFaultObjId(RMDCommonUtility.convertObjectToString(object[0]));
						 alertParmVO.setRecordType(RMDCommonUtility.convertObjectToString(object[1]));
						 alertParmVO.setCmuTime(RMDCommonUtility.convertObjectToString(object[4]));
						 alertParmVO.setIncidentTime(RMDCommonUtility.convertObjectToString(object[3]));
						 alertParmVO.setFaultCode(RMDCommonUtility.convertObjectToString(object[2]));
						 if(isAtsFaultDataExist == 1)
							 alertParmVO.setParmNames(parmNames);
						 else
							 alertParmVO.setParmNames(fltParmNames);
						 List<RuleParmVO> parmData = new ArrayList<RuleParmVO>();
						 //Loop EOA Parm Names..
						 for(int i=0;i<eoaParmNames.size();i++) {
							 int index = 5+i;
							 String parmName = eoaParmNames.get(i).toString();
							 //Loop thru parmDetails list..
							 for(ParmMetricVO parmMetricVO:parmDetailLst) {
								 ParmMetricVO metricObj = parmMetricVO;
								 if(parmName.equalsIgnoreCase(metricObj.getParamDBName())) {
									 String parmAbbr = "";
									 RuleParmVO parmvo = new RuleParmVO();
									 parmvo.setParmName(parmName);
									 if(object[index]!=null) {
										 parmvo.setParmValue(RMDCommonUtility.convertObjectToString(object[index]));
									 } else
										 parmvo.setParmValue("");
									 String srcUOM = metricObj.getSourceUOM();
									 if(null!=custUOM && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(custUOM)
											 && !RMDCommonConstants.US.equalsIgnoreCase(custUOM) && alertParmVO.getRecordType().equalsIgnoreCase(metricObj.getParamType())){
										 if(null!=srcUOM && srcUOM!=""){
											 UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(srcUOM);
											 if (null != objUnitOfMeasureVO && RMDCommonConstants.ONE_STRING.equalsIgnoreCase(objUnitOfMeasureVO.getIsConversionRequired())
													 && parmvo.getParmValue()!=null && parmvo.getParmValue()!="") {
												 String conversionFormula=objUnitOfMeasureVO.getConversionExp();

												 parmvo.setParmValue(AppSecUtil.convertMeasurementSystem(conversionFormula,parmvo.getParmValue()));
												 parmAbbr = objUnitOfMeasureVO.getTargetAbbr();

											 }else{
												 parmAbbr = objUnitOfMeasureVO.getTargetAbbr();
											 }
										 }	
									 } else {
										 if(null!=srcUOM) {
											 UnitOfMeasureVO objUnitOfMeasureVO = mpUom.get(srcUOM);
											 if (null != objUnitOfMeasureVO) {	
												 parmAbbr = objUnitOfMeasureVO.getSourceAbbr();
											 }
										 }	
									 }


									 parmAbbrLst.add(parmAbbr);
									 parmvo.setUnitAbbr(parmAbbr);
									 if(parmDisplaycount<eoaParmNames.size()) {
									 if(!RMDCommonUtility.isNullOrEmpty(parmAbbr) &&
											 null!=custUOM && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(custUOM)
											 && alertParmVO.getRecordType().equalsIgnoreCase(metricObj.getParamType())
											 ) {
										 parmDisplayNamesNew.add(parmDisplayNames.get(atsParamNames.size()+i).concat(" (").concat(parmAbbr).concat(") "));
										 parmDisplaycount++;
									 }
									 else {
										 parmDisplayNamesNew.add(parmDisplayNames.get(atsParamNames.size()+i));
										 parmDisplaycount++;
									 }
									 }
									 parmData.add(parmvo);
								 }
								 
							 }
							 
						 }					 
						 alertParmVO.setParmSet(parmData);
						 alertRuleParmData.add(alertParmVO);
					 }		

					 for(AlertRuleParmVO parmVO:alertRuleParmData) {
						 parmVO.setParmDisplayNames(parmDisplayNamesNew);
						 if(listAlertParmData.isEmpty())  {
							 parmVO.setParmNames(atsParamNames);
						 }
					 }
				 } else {
					 for(AlertRuleParmVO parmVO:alertRuleParmData) {
						 parmVO.setParmNames(atsParamNames);
					 }
				 }

			 }

			 //Replace GEO ID Parm Values..
			 String parmValues = "";
			 for(AlertRuleParmVO parmVO:alertRuleParmData) {
				 for(RuleParmVO parmData:parmVO.getParmSet()) {
					 if(parmData.getParmName().indexOf("GEO_ZONE_")>=0 && !RMDCommonUtility.isNullOrEmpty(parmData.getParmValue())) {						
						parmValues = parmValues + parmData.getParmValue() + ",";							
					 } 
				 }
			 }

			 if(parmValues.length()>0) {
				 Map<String, String> parmValueMap = new HashMap<String, String>();
				 parmValues = parmValues.substring(0, parmValues.length()-1);
				 queryString = new StringBuilder();
				 queryString.append("SELECT GEOFENCE_ID, PROXIMITY_DESC FROM GETS_MCS_PP_CALC_GEOFENCE_DEF, GETS_RMD_PP_PROXIMITY_DEF PD WHERE PD.OBJID = PPCALC_GEO_DEF2PROX_DEF ")
				 .append("AND GEOFENCE_ID IN (:parmValues)");

				 query = session.createSQLQuery(queryString.toString());				 
				 query.setParameterList(RMDCommonConstants.PARM_VALUES, parmValues.split(","));
				 listAlertParmData = query.list();
				 if(RMDCommonUtility.isCollectionNotEmpty(listAlertParmData)) {
					 for(Object[] object:listAlertParmData) {
						 parmValueMap.put(RMDCommonUtility.convertObjectToString(object[0]), RMDCommonUtility.convertObjectToString(object[1]));
					 }
				 }

				 for(AlertRuleParmVO parmVO:alertRuleParmData) {
					 for(RuleParmVO parmData:parmVO.getParmSet()) {
						 if(parmData.getParmName().indexOf("GEO_ZONE_")>=0 && parmValueMap.containsKey(parmData.getParmValue())) {							
								 parmData.setParmValue(parmValueMap.get(parmData.getParmValue()));
						 } 
					 }
				 }				 
			 }

		 } catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRuleParmData;
	 }

	 /**
	  * Used to get detailed info on an alert firing
	  * @param strLanguage
	  * @param isGECustomer
	  * @param customerId
	  * @param alertFiringId
	  * @return AlertRunsVO
	  * @author 212556286
	  */
	 public AlertRunsVO getAlertFiringDetails(String strLanguage, String isGECustomer,String alertFiringId, String custUOM,String strCustomer) {
		 AlertRunsVO alertRunVO = null;
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();
		 Query query = null;
		 List<Object[]> listAlertFiringData = null;
		 List<AlertRuleParmVO> ruleParmData = new ArrayList<AlertRuleParmVO>();
		 LOG.debug("in getAlertFiringDetails, isGECustomer "+isGECustomer+" , alertFiringId "+alertFiringId+" , strCustomer "+strCustomer);
		 try {
			 session = getHibernateSession();
			queryString = queryString
					.append("SELECT FR.OBJID AS RULEID,FR.RULE_TITLE,FR.FAMILY,V.OBJID,SP.X_VEH_HDR_CUST,SP.SERIAL_NO,CC.CUST_ID_MP_SOURCE AS CONTROLLER_SOURCE_ID, ")
					.append("TO_CHAR(R.RUN_START,'DD-MON-YY hh24:mi:ss') AS RAN_ON,TO_CHAR(AF.CREATION_DATE,'DD-MON-YY hh24:mi:ss') AS FIRED_ON,AF.FIRING2LASTDATARECORD,AF.DATARECORDTYPE, ")
					.append("SR.LOOKBKMIN_OBJID AS EOAMINLOOKBACK,SR.LOOKBKMAX_OBJID AS EOAMAXLOOKBACK,AR.LOOKBKMIN_OBJID AS ATSMINLOOKBACK,AR.LOOKBKMAX_OBJID AS ATSMAXLOOKBACK, R.OBJID, AF.OBJID, AH.ALERT_SENT_TIME,TBO.ORG_ID  ")
					.append("FROM GETS_TOOL_ALERT_FIRING AF,GETS_TOOL_DPD_FINRUL FR,GETS_TOOL_DPD_RULEDEF RD,GETS_TOOL_DPD_RULHIS RH,GETS_RMD_VEHICLE V,SA.TABLE_SITE_PART SP,GETS_RMD_CTL_CFG CC, ")
					.append("GETS_TOOL_SUBRUN SR,GETS_TOOL_RUN R,GETS_TOOL_ATS_RUN AR, GETS_TOOL_ALERT_HISTORY AH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO ");
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString.append(",GETS_TOOL_DPD_CUST C ");
			}
			queryString = queryString
					.append("WHERE RD.OBJID = AF.FIRING2RULE_DEFN AND RD.RULEDEF2FINRUL = FR.OBJID AND V.OBJID = AF.FIRING2VEHICLE AND SP.OBJID = V.VEHICLE2SITE_PART AND V.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ")
					.append("AND CC.OBJID = V.VEHICLE2CTL_CFG AND SR.OBJID = AF.FIRING2SUBRUN AND R.OBJID = SR.SUBRUN2RUN AND R.RUN2VEHICLE = V.OBJID AND AR.ATS_RUN2RUN = R.OBJID AND RH.RULHIS2FINRUL=FR.OBJID AND RH.ACTIVE=1 ")
					.append("AND SR.SUBRUN2RUN=AH.ALERTHISTORY2RUN(+) AND RH.ORIGINAL_ID=AH.ORIGINAL_ID(+) ");


			 if(!RMDCommonUtility.isNullOrEmpty(alertFiringId)) {
				 queryString = queryString.append("AND AF.OBJID = :firingId");
			 }
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append(" AND C.CUST2RULEDEF = RD.OBJID AND C.CUST2BUSORG = VEHHDR.VEH_HDR2BUSORG AND TBO.ORG_ID = :CUSTOMER ");
			}
			 query = session.createSQLQuery(queryString.toString());
			 if(!RMDCommonUtility.isNullOrEmpty(alertFiringId)) {
				 query.setParameter(RMDCommonConstants.FIRING_ID, alertFiringId);
			 }
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				query.setParameter(RMDServiceConstants.CUSTOMER, strCustomer);
			}
			 listAlertFiringData = query.list();

			 if(RMDCommonUtility.isCollectionNotEmpty(listAlertFiringData)) { 
				 alertRunVO =  new AlertRunsVO();
				 for(Object[] object:listAlertFiringData) {
					 alertRunVO.setRuleId(RMDCommonUtility.convertObjectToString(object[0]));
					 alertRunVO.setRuleTitle(RMDCommonUtility.convertObjectToString(object[1]));
					 alertRunVO.setFamily(RMDCommonUtility.convertObjectToString(object[2]));
					 alertRunVO.setRun2Vehicle(RMDCommonUtility.convertObjectToString(object[3]));
					 alertRunVO.setVehHdrCust(RMDCommonUtility.convertObjectToString(object[4]));
					 alertRunVO.setSerialNo(RMDCommonUtility.convertObjectToString(object[5]));
					 alertRunVO.setControllerSrcId(RMDCommonUtility.convertObjectToString(object[6]));
					 alertRunVO.setRunDate(RMDCommonUtility.convertObjectToString(object[7]));
					 alertRunVO.setRuleFiredDate(RMDCommonUtility.convertObjectToString(object[8]));
					 alertRunVO.setFiredDataRecord(RMDCommonUtility.convertObjectToString(object[9]));

					 alertRunVO.setDataRecordType(RMDCommonUtility.convertObjectToString(object[10]));
					 alertRunVO.setEoaMinLookBack(RMDCommonUtility.convertObjectToString(object[11]));
					 alertRunVO.setEoaMaxLookBack(RMDCommonUtility.convertObjectToString(object[12]));
					 alertRunVO.setAtsMinLookBack(RMDCommonUtility.convertObjectToString(object[13]));
					 alertRunVO.setAtsMaxLookBack(RMDCommonUtility.convertObjectToString(object[14]));
					 alertRunVO.setRunObjId(RMDCommonUtility.convertObjectToString(object[15]));
					 alertRunVO.setFiringId(RMDCommonUtility.convertObjectToString(object[16]));
					 alertRunVO.setStrCustomer(RMDCommonUtility.convertObjectToString(object[18]));
					 if(!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility.convertObjectToString(object[17])))
						 alertRunVO.setAlertSent(true);
					 else
						 alertRunVO.setAlertSent(false);
					 ruleParmData = getRuleParmData(strLanguage, alertRunVO.getRuleId(), alertRunVO.getFamily(), alertRunVO.getRun2Vehicle(), alertRunVO.getControllerSrcId(), alertRunVO.getEoaMinLookBack(), alertRunVO.getEoaMaxLookBack(), alertRunVO.getAtsMinLookBack(), alertRunVO.getAtsMaxLookBack(), custUOM);
					 alertRunVO.setRuleParmData(ruleParmData);
				 }				
			 }

		 } catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRunVO;
	 }

	 /**
	  * Used to fetch alert missed details
	  * @param strLanguage
	  * @param isGECustomer
	  * @param customerId
	  * @param ruleId
	  * @param runId
	  * @return AlertRunsVO
	  * @author 212556286
	  */
	 public AlertRunsVO getAlertMissingDetails(String strLanguage, String isGECustomer,String ruleId, String runId, String custUOM,String strCustomer) {
		 AlertRunsVO alertRunVO = null;
		 Session session = null;
		 StringBuilder queryString = new StringBuilder();

		 Query query = null;
		 List<Object[]> listAlertFiringData = null;
		 List<Object[]> ruleData = null;
		 List<AlertRuleParmVO> ruleParmData = new ArrayList<AlertRuleParmVO>();
		 LOG.debug("in getAlertMissingDetails, isGECustomer "+isGECustomer+" , ruleId "+ruleId+" ,runId "+runId+" , strCustomer "+strCustomer);
		 try {
			 session = getHibernateSession();
			queryString = queryString
					.append("SELECT R.OBJID, TO_CHAR(R.CREATION_DATE,'DD-MON-YY hh24:mi:ss') CREATION_DATE, R.RUN2VEHICLE,SP.X_VEH_HDR_CUST,SP.SERIAL_NO,CC.CUST_ID_MP_SOURCE AS CONTROLLER_SOURCE_ID,")
					.append("SR.LOOKBKMIN_OBJID AS EOAMINLOOKBACK,SR.LOOKBKMAX_OBJID AS EOAMAXLOOKBACK,AR.LOOKBKMIN_OBJID AS ATSMINLOOKBACK,AR.LOOKBKMAX_OBJID AS ATSMAXLOOKBACK, null,TBO.ORG_ID ")
					.append("FROM GETS_TOOL_RUN R,GETS_TOOL_SUBRUN SR,GETS_TOOL_ATS_RUN AR,GETS_RMD_VEHICLE V,SA.TABLE_SITE_PART SP,GETS_RMD_CTL_CFG CC, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ")
					.append("WHERE SR.SUBRUN2RUN = R.OBJID AND SR.TOOL_ID = 'JDPAD' AND AR.ATS_RUN2RUN = R.OBJID AND V.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ")
					.append("AND V.OBJID = R.RUN2VEHICLE AND SP.OBJID = V.VEHICLE2SITE_PART AND CC.OBJID = V.VEHICLE2CTL_CFG ");
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				queryString = queryString
						.append(" AND TBO.ORG_ID = :CUSTOMER ");
			}	
			 if(!RMDCommonUtility.isNullOrEmpty(runId)) {
				 queryString = queryString.append("AND R.OBJID = :runId");
			 }
			 
			 query = session.createSQLQuery(queryString.toString());
			 if(!RMDCommonUtility.isNullOrEmpty(runId)) {
				 query.setParameter(RMDCommonConstants.RUN_ID, runId);
			 }
			if (null != strCustomer
					&& !strCustomer.isEmpty()
					&& !isGECustomer
							.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
				query.setParameter(RMDServiceConstants.CUSTOMER, strCustomer);
			}
			 listAlertFiringData = query.list();
			 if(RMDCommonUtility.isCollectionNotEmpty(listAlertFiringData)) { 
				 alertRunVO =  new AlertRunsVO();
				 for(Object[] object:listAlertFiringData) {
					 alertRunVO.setRunObjId(RMDCommonUtility.convertObjectToString(object[0]));
					 alertRunVO.setRunDate(RMDCommonUtility.convertObjectToString(object[1]));
					 alertRunVO.setRun2Vehicle(RMDCommonUtility.convertObjectToString(object[2]));
					 alertRunVO.setVehHdrCust(RMDCommonUtility.convertObjectToString(object[3]));
					 alertRunVO.setSerialNo(RMDCommonUtility.convertObjectToString(object[4]));
					 alertRunVO.setControllerSrcId(RMDCommonUtility.convertObjectToString(object[5]));
					 alertRunVO.setEoaMinLookBack(RMDCommonUtility.convertObjectToString(object[6]));
					 alertRunVO.setEoaMaxLookBack(RMDCommonUtility.convertObjectToString(object[7]));
					 alertRunVO.setAtsMinLookBack(RMDCommonUtility.convertObjectToString(object[8]));
					 alertRunVO.setAtsMaxLookBack(RMDCommonUtility.convertObjectToString(object[9]));
					 alertRunVO.setFiredDataRecord(RMDCommonUtility.convertObjectToString(object[10]));
					 alertRunVO.setStrCustomer(RMDCommonUtility.convertObjectToString(object[11]));
				 }				
			 }
			 if(!RMDCommonUtility.isNullOrEmpty(ruleId)) {
				queryString = new StringBuilder();
				queryString = queryString
						.append("SELECT FR.OBJID,FR.RULE_TITLE,FR.FAMILY FROM GETS_TOOL_DPD_FINRUL FR ");
				
				if (null != strCustomer
						&& !strCustomer.isEmpty()
						&& !isGECustomer
								.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
					queryString = queryString
							.append(",GETS_TOOL_DPD_RULEDEF RD,GETS_TOOL_DPD_CUST C, TABLE_BUS_ORG TBO ");
				}
				queryString = queryString.append("WHERE FR.OBJID = :ruleId ");
				if (null != strCustomer
						&& !strCustomer.isEmpty()
						&& !isGECustomer
								.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
					queryString = queryString
							.append(" AND RD.RULEDEF2FINRUL = FR.OBJID AND C.CUST2RULEDEF = RD.OBJID AND C.CUST2BUSORG = TBO.OBJID AND TBO.ORG_ID = :CUSTOMER ");
				}

				 Query query1 = session.createSQLQuery(queryString.toString());
				 query1.setParameter(RMDCommonConstants.RULEID, ruleId);
				if (null != strCustomer
						&& !RMDCommonConstants.EMPTY_STRING.equals(strCustomer)
						&& !isGECustomer
								.equalsIgnoreCase(RMDCommonConstants.Y_LETTER_UPPER)) {
					query1.setParameter(RMDServiceConstants.CUSTOMER,
							strCustomer);
				}
				 ruleData = query1.list();
				 if(RMDCommonUtility.isCollectionNotEmpty(ruleData)) {
					 for(Object[] data:ruleData) {
						 if(!RMDCommonUtility.isNull(data[0].toString()))
							 alertRunVO.setRuleId(RMDCommonUtility.convertObjectToString(data[0]));
						 if(!RMDCommonUtility.isNull(data[1].toString()))
							 alertRunVO.setRuleTitle(RMDCommonUtility.convertObjectToString(data[1]));
						 if(!RMDCommonUtility.isNull(data[2].toString()))
							 alertRunVO.setFamily(RMDCommonUtility.convertObjectToString(data[2]));
					 }
				 }

				 if(!RMDCommonUtility.isNullOrEmpty(alertRunVO.getRuleId()))
					 ruleParmData = getRuleParmData(strLanguage, alertRunVO.getRuleId(), alertRunVO.getFamily(), alertRunVO.getRun2Vehicle(), alertRunVO.getControllerSrcId(), alertRunVO.getEoaMinLookBack(), alertRunVO.getEoaMaxLookBack(), alertRunVO.getAtsMinLookBack(), alertRunVO.getAtsMaxLookBack(), custUOM);
				 alertRunVO.setRuleParmData(ruleParmData);
			 }

		 } catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), ex, RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility
					 .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CUSTOMER_FOR_RULE);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {},
							 strLanguage), e, RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return alertRunVO;
	 }

	 /**
	  * Used to fetch rules based on family
	  * @param strLanguage
	  * @param family
	  * @return List of RuleByFamilyVO
	  * @author 212556286
	  */
	 @Override
	 public List<RuleByFamilyVO> getRuleByFamily(String strLanguage, String family) throws RMDDAOException {

		 List<RuleByFamilyVO> arlRuleByFamily = new ArrayList<RuleByFamilyVO>();
		 RuleByFamilyVO objRuleByFamily = null;
		 Session session = null;

		 Query query = null;
		 StringBuilder stringBuilder = null;

		 try {
			 session = getHibernateSession();
			 stringBuilder = new StringBuilder();
			 stringBuilder.append("SELECT FR.OBJID, FR.RULE_TITLE, FR.FAMILY from GETS_TOOLS.GETS_TOOL_DPD_FINRUL FR, GETS_TOOL_DPD_RULEDEF RD ")
			 .append("WHERE FR.OBJID=RD.RULEDEF2FINRUL AND RULE_TYPE='Alert' ")
			 .append("AND FR.FAMILY = :family");
			 query = session.createSQLQuery(stringBuilder.toString());
			 query.setParameter(RMDCommonConstants.FAMILY, family);
			 List<Object[]> arlCreatedList = query.list();
			 for (Object[] obj:arlCreatedList) {
				 objRuleByFamily = new RuleByFamilyVO();
				 objRuleByFamily.setRuleId(RMDCommonUtility.convertObjectToString(obj[0]));
				 objRuleByFamily.setTitle(RMDCommonUtility.convertObjectToString(obj[1]));
				 objRuleByFamily.setFamily(RMDCommonUtility.convertObjectToString(obj[2]));
				 arlRuleByFamily.add(objRuleByFamily);
			 }
		 } catch (RMDDAOConnectionException ex) {
			 String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					 RMDCommonConstants.FATAL_ERROR);
		 } catch (Exception e) {
			 String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_SEARCHADVISORY);
			 throw new RMDDAOException(errorCode, new String[] {},
					 RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
					 RMDCommonConstants.MAJOR_ERROR);
		 } finally {
			 releaseSession(session);
		 }
		 return arlRuleByFamily;
	 }
	 
	  /**
	  * Used to fetch roadnumbers
	  * @author 212556286
	  * @param CustomerId
	  */
	 @Override
	    public List<ElementVO> getRoadNumberHeaders(String customerId)
	            throws RMDDAOException {
	        List<ElementVO> arlElementVOs = new ArrayList<ElementVO>();
	        Session session = null;
	        Query hibernateQuery = null;
	        StringBuilder roadIntialQuery = new StringBuilder();
	        try {
	            session = getHibernateSession();
	            roadIntialQuery
	                    .append("SELECT DISTINCT VEHICLE_HDR,VEH_HDR_ORDER FROM GETS_RMD_CUST_RNH_RN_V ");
	            
	            if(!RMDCommonUtility.isNullOrEmpty(customerId)) {
	            	roadIntialQuery = roadIntialQuery.append("WHERE ORG_ID=:customerId  ORDER BY VEH_HDR_ORDER");
					 }
	            else {
	            	roadIntialQuery = roadIntialQuery.append("ORDER BY VEHICLE_HDR");
	            }
	            	
	            hibernateQuery = session.createSQLQuery(roadIntialQuery.toString());
	            if(!RMDCommonUtility.isNullOrEmpty(customerId)) {
	            	hibernateQuery.setParameter(RMDServiceConstants.CUSTOMER_ID, customerId);
	            }
	            hibernateQuery.setFetchSize(100);
	            List<Object[]> roadIntialsList = hibernateQuery.list();
	            for (Object[] currentRoadIntial : roadIntialsList) {
	                ElementVO objElementVO = new ElementVO();
	                objElementVO.setId(RMDCommonUtility
	                        .convertObjectToString(currentRoadIntial[1]));
	                objElementVO.setName(RMDCommonUtility
	                        .convertObjectToString(currentRoadIntial[0]));
	                arlElementVOs.add(objElementVO);
	            }
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility
	                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {},
	                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MINOR_ERROR);
	        } finally {
	            releaseSession(session);
	        }
	        return arlElementVOs;
	    }

	@Override
	public String getNextRuleCacheRefresh(String ruleType)
			throws RMDDAOException {
		String nextRuleCacheRefreshTime = null;
        Session session = null;
        Query hibernateQuery = null;
        StringBuilder roadIntialQuery = new StringBuilder();
        try {
        	
            session = getHibernateSession();
            
        	String instanceId = null;
            if(ruleType.equalsIgnoreCase("Alert")){
            	instanceId = " AND INSTANCE_ID IN (9) ";
            }else if(ruleType.equalsIgnoreCase("Filter")){
            	instanceId = " AND INSTANCE_ID IN (1,6) ";
            }else{
            	instanceId = " AND INSTANCE_ID IN (2,5,7)";
            }

			roadIntialQuery
					.append("SELECT TO_CHAR((SELECT TO_DATE(TO_CHAR((MIN(LAST_CACHE_REFRESH_TIME)),'DD-MM-YYYY HH24:MI:SS'),'DD-MM-YYYY HH24:MI:SS') ");
			roadIntialQuery
					.append(" from GETS_RMD_PROGRAM_DEF where LAST_CACHE_REFRESH_TIME is not null AND PROGRAM_ID = 1020 ")
					.append(instanceId).append(" ) + ");
			roadIntialQuery
					.append("((SELECT value FROM gets_rmd_sysparm  WHERE title = 'AFFECTED_VEHRULE_PERIOD')+1200000)/1000/60/60/24 , 'YYYY-MM-DD HH24:MI:SS') datestr from dual ");       
			
            hibernateQuery = session.createSQLQuery(roadIntialQuery.toString());
            List<String> selectList = (List<String>)hibernateQuery.list();
            
			if (selectList != null && !selectList.isEmpty()) {
				for (String strValue : selectList) {
					nextRuleCacheRefreshTime = strValue;
				}
			}

        } catch (Exception e) {
        	e.printStackTrace();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ROAD_INITIAL_HEADERS);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MINOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return nextRuleCacheRefreshTime;
	}

	@Override
	public List<ParameterServiceVO> getSolutionParameters(List<String> modelList)
			throws RMDDAOException {
		LOG.debug("Start of RuleDAOImpl.getSolutionParameters()");
		List<ParameterServiceVO> arlResults = new ArrayList<ParameterServiceVO>();
		Session session = null;
		ParameterServiceVO parameterServiceVO = null;
		StringBuilder queryString = new StringBuilder(
				RMDCommonConstants.EMPTY_STRING);
		Query queryParameters = null;
		List<Object> lookupList = null;
		
		try {

			session = getHibernateSession();
			queryString
					.append("SELECT DISTINCT c.objid, c.parm_type,c.column_name FROM gets_tool_dpd_colname c WHERE c.parm_type NOT IN ('Geolocation','ATS') ");
			if (null != modelList && !modelList.isEmpty()) {
				String modelString = getStringList(modelList);
				queryString
						.append("AND family IN ( SELECT DISTINCT DECODE(m.family,'ACCCA','CCA','DCCCA','CCA',m.family ) AS family");
				queryString
						.append(" FROM gets_rmd_model m WHERE m.model_name IN (")
						.append(modelString).append("))");
			}
			queryString.append("ORDER BY c.column_name ");
			queryParameters = session
					.createSQLQuery(queryString.toString());
			queryParameters.setFetchSize(2000);
			lookupList = queryParameters.list();
			
			// Iterating General Param list
			if (RMDCommonUtility.isCollectionNotEmpty(lookupList)) {

				for (final Iterator<Object> iter = lookupList.iterator(); iter
						.hasNext();) {
					parameterServiceVO = new ParameterServiceVO();
					final Object[] lookupRecord = (Object[]) iter.next();
					parameterServiceVO.setParameterNameID(RMDCommonUtility
							.convertObjectToString(lookupRecord[0]));
					parameterServiceVO.setColumnType(RMDCommonUtility
							.convertObjectToString(lookupRecord[1]));
					parameterServiceVO.setParameterName(RMDCommonUtility
							.convertObjectToString(lookupRecord[2]));
					parameterServiceVO.setDisplayName(RMDCommonUtility
							.convertObjectToString(lookupRecord[2]));
					parameterServiceVO.setVirtualId(null);
					arlResults.add(parameterServiceVO);
				}
			}			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRULEINITIALLOAD);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex, RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_ADDRULEINITIALLOAD);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e, RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		LOG.debug("End of RuleDAOImpl.getSolutionParameters()");
		return arlResults;
	}
	private String getStringList(List<String> idList) {
		StringBuilder buff = new StringBuilder();
		String sep = "";

		for (String id : idList) {
			buff.append(sep);
			buff.append("'");
			buff.append(id);
			buff.append("'");
			sep = ",";
		}
		return buff.toString();
	}
	
	
	 /**
     * @Author:iGATE
     * @param: Session,strFinalRule,
     *             strChoosedRuleOnRule
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is used for checking the ROR rule is having
     *                   circular reference with the edit final rule if it has
     *                   circular reference then it will return yes else it will
     *                   return no. This method will recursively called again to
     *                   check all the inner Rules are having circular reference
     *                   with final rules.
     */
    public List<String> getRoRRuleInformation(List<String> ruleIdList,String strFinalRule) {
    	Session hibernateSession = null;
    	List<String> notRoR=null;
        try {
        	hibernateSession=getHibernateSession();
        	if(null!=ruleIdList&&!ruleIdList.isEmpty()){
        		notRoR=new ArrayList<String>();
        	for(String strChoosedRuleOnRule:ruleIdList){
        		if(checkFinalRuleLinked(hibernateSession,strFinalRule,strChoosedRuleOnRule)){
        			notRoR.add(strChoosedRuleOnRule);
        		}
        	}
        	}
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }finally {
            releaseSession(hibernateSession);
        }
        return notRoR;
    }
    
    
    /**
     * @Author:iGATE
     * @param: Session,strFinalRule,
     *             strChoosedRuleOnRule
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is used for checking the ROR rule is having
     *                   circular reference with the edit final rule if it has
     *                   circular reference then it will return yes else it will
     *                   return no. This method will recursively called again to
     *                   check all the inner Rules are having circular reference
     *                   with final rules.
     */
    private boolean checkFinalRuleLinked(Session hibernateSession, String strFinalRule, String strChoosedRuleOnRule) {
        Query cmpRuleQuery = null;
        Query checkFinalRule = null;
        List<BigDecimal> arlCmpRules = new ArrayList<BigDecimal>();
        List<Object[]> arlFinalRule = new ArrayList<Object[]>();
        String strRule1 = null;
        String strRule2 = null;
        String strCmpRuleId = null;
        try {
            cmpRuleQuery = hibernateSession.createSQLQuery(RMDServiceConstants.QueryConstants.GET_CMPRULE_RULES_SQL);
            cmpRuleQuery.setParameter(RMDServiceConstants.FINRULE_OBJID, strChoosedRuleOnRule);
            arlCmpRules = cmpRuleQuery.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlCmpRules)) {
                checkFinalRule = hibernateSession
                        .createSQLQuery(RMDServiceConstants.QueryConstants.CHECK_ROR_RULE_SQL.toString());
                for (BigDecimal bigDecCmpRuleId : arlCmpRules) {
                    strCmpRuleId = bigDecCmpRuleId.toString();
                    checkFinalRule.setParameter(RMDServiceConstants.CMPRULE_OBJID, strCmpRuleId);
                    arlFinalRule = checkFinalRule.list();
                    for (Object[] objFinalRule : arlFinalRule) {
                        strRule1 = RMDCommonUtility.convertObjectToString(objFinalRule[0]);
                        strRule2 = RMDCommonUtility.convertObjectToString(objFinalRule[1]);
                        if (null != strRule1 && !RMDServiceConstants.NEGATIVE_ONE.equals(strRule1)
                                && strRule1.equalsIgnoreCase(strFinalRule)) {
                            return true;
                        } else if (null != strRule2 && !RMDServiceConstants.NEGATIVE_ONE.equals(strRule2)
                                && strRule2.equalsIgnoreCase(strFinalRule)) {
                            return true;
                        } else {
                            if (!RMDServiceConstants.NEGATIVE_ONE.equals(strRule1)
                                    && checkFinalRuleLinked(hibernateSession, strFinalRule, strRule1)) {
                                return true;
                            } else if (!RMDServiceConstants.NEGATIVE_ONE.equals(strRule2)
                                    && checkFinalRuleLinked(hibernateSession, strFinalRule, strRule2)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_ROR_RULES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return false;

    }
}
