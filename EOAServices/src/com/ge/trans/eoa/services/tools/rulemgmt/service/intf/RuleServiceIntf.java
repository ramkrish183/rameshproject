/**
 * ============================================================
 * File : RuleServiceInf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceDefaultVO;

/*******************************************************************************
 * @param <ManualJdpadVO>
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified : February 14, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :New methods added getCustomerList, getModelList, getFleetList,
 *          getConfigurationList, getAssetList
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RuleServiceIntf {

	/**
	 * @Author:
	 * @param searchRuleServiceCriteriaVO
	 * @return
	 * @Description:
	 */
	List getSearchRuleResult(
			SearchRuleServiceCriteriaVO searchRuleServiceCriteriaVO)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @return
	 * @Description:
	 */
	SearchRuleServiceDefaultVO getSearchRuleCriteria(String strLanguage)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @return
	 * @Description:
	 */
	List getInitialLoadForAddRule(
			ParameterRequestServiceVO objParameterRequestServiceVO)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	List getArlFunctionList(String strLanguage) throws RMDServiceException;

	/**
	 * @Author:
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	Map getInitialRuleDefValues(String strModel, Long lngUserId,
			String strLanguage) throws RMDServiceException;

	/**
	 * @Author:
	 * @param finalRuleServiceVO
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	String saveFinalRule(FinalRuleServiceVO finalRuleServiceVO)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @param strFinalRuleID
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	List activateRule(String strFinalRuleID, String strLanguage, String strUser)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @param strFinalRuleID
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	List<String> deActivateRule(String strFinalRuleID, String strLanguage,
			String strUser) throws RMDServiceException;

	/**
	 * @Author:
	 * @param strOriginalId
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	String deActiveRuleWithOrgId(String strOriginalId, String strFinalRuleID,
			String strLanguage) throws RMDServiceException;

	/**
	 * @Author:
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	List getconfigFcnValues(String strLanguage) throws RMDServiceException;

	/**
	 * @Author:
	 * @param strRuleId
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 * @Description:
	 */
	List getParentRule(String strRuleId, String strLanguage)
			throws RMDServiceException;

	List getFleetsList(String strCustomerId, String strLanguage)
			throws RMDServiceException;

	List getConfigurationList(String[] strModel, String strLanguage)
			throws RMDServiceException;

	List getExcludedCustomerList(String[] strCustomerId, String strLanguage)
			throws RMDServiceException;

	List getAllConfigList(String strFamily, String strLanguage)
			throws RMDServiceException;

	List getCustomerList(String strLanguage, String userId)
			throws RMDServiceException;

	List getCustomerList(String strModel, String strFleet, String strConfig[],
			String strUnitNumber, String strLanguage)
			throws RMDServiceException;

	List getModelList(String strFamily, String strLanguage, String strKEP)
			throws RMDServiceException;

	/**
	 * @Author:
	 * @param: strRuleId
	 * @return lstRuleId
	 * @throws RMDServiceException
	 * @Description:This method is the Service Interface for getting the list of
	 *                   RuleIds
	 */
	List getRules(String strRuleId) throws RMDServiceException;

	List<ElementVO> getModelList(String strCustomer, String strFleet,
			String strConfig[], String strUnitNumber, String strFamily,
			String strLanguage, String strKEP) throws RMDServiceException,
			RMDBOException;

	List getFleetList(String strCustomer, String strModel, String strConfig[],
			String strUnitNumber, String strLanguage)
			throws RMDServiceException, RMDBOException;

	List<AssetServiceVO> getAssetList(String strCustomer, String strModel,
			String strConfig[], String strFleet, String strLanguage)
			throws RMDServiceException, RMDBOException;

	List getConfigurationList(String strCustomer, String strModel,
			String strFleet, String strUnitNumber, String strFamily,
			String strLanguage) throws RMDServiceException, RMDBOException;

	/**
	 * @param strListName
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 */
	/*
	 * List getRuleTypeLookupValues(String strListName, String strLanguage)
	 * throws RMDServiceException;
	 *//**
	 * @param strListName
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 */

	/*
	 * List getStatusLookupValues(String strListName, String strLanguage) throws
	 * RMDServiceException;
	 *//**
	 * @param strListName
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 */
	/*
	 * List getSubSystemLookupValues(String strListName, String strLanguage)
	 * throws RMDServiceException; /**
	 * 
	 * @param strListName
	 * 
	 * @param strLanguage
	 * 
	 * @return
	 * 
	 * @throws RMDServiceException List getVersionLookupValues(String
	 * strListName, String strLanguage) throws RMDServiceException;
	 */

	List<ElementVO> getRuleCreatedBy(String strLanguage)
			throws RMDServiceException;

	List<ElementVO> getRuleLastUpdatedBy(String strLanguage)
			throws RMDServiceException;

	List<ElementVO> getRuleLookupValues(String strListName, String strLanguage)
			throws RMDServiceException;

	String getRuleLookupValues(String strListName) throws RMDServiceException;

	/**
	 * @param strLanguage
	 *            ,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	 */
	List<ParameterServiceVO> getSDPValues(String family, String strLanguage)
			throws RMDServiceException;

	/**
	 * @param strLanguage
	 * @return
	 * @throws RMDServiceException
	 * @throws RMDBOException
	 */
	List<ElementVO> getPerformanceCheckValues(final String strLanguage)
			throws RMDServiceException, RMDBOException;

	List<ElementVO> getRuleCreatedByExternal(String strLanguage,
			String strCustomer) throws RMDServiceException;

	List<ElementVO> getRuleLastUpdatedByExternal(String strLanguage,
			String strCustomer) throws RMDServiceException;

	/**
	 * @param strLanguage
	 *            ,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return GEO location params and its values.
	 */
	List<ParameterServiceVO> getGeoLocationValues(String customer,
			String family, String strLanguage) throws RMDServiceException;

	/**
	 * @param strLanguage
	 *            ,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return GEO location params and its values.
	 */
	List<ParameterServiceVO> getATSParameters(String family,
			String strLanguage, String uom, String customer)
			throws RMDServiceException;

	List<ElementVO> getCustomerForRule(String ruleId, String strLanguage)
			throws RMDServiceException;

	/**
	 * @param strLanguage
	 *            ,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	 */
	List<ParameterServiceVO> getColValues(String family, String strLanguage)
			throws RMDServiceException;

	/**
	 * @param strLanguage
	 * @author 212556286
	 * @return List of AlertRuleVO
	 * @throws RMDServiceException
	 * @Description:This method will return all alerts fired for last 24 hrs.
	 */
	List<AlertRuleVO> getRecentAurizonFiring(final String strLanguage,
			final String isGECustomer, final String ruleType,
			final String family, final String ruleId, final String ruleTitle,
			final String roadHdr, final String roadNo, final String vehicleId,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDServiceException;

	/**
	 * 
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param vehicleId
	 * @param dateStart
	 * @param dateEnd
	 * @return List of AlertRunsVO
	 * @throws RMDServiceException
	 * @Description:This method will return all runs for Alerts
	 * @author 212556286
	 */
	List<AlertRunsVO> getAlertRuns(final String strLanguage,
			final String isGECustomer, final String vehHdr,
			final String roadNo, final String diagService,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDServiceException;

	/**
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param family
	 * @return List of AlertRuleVO
	 * @throws RMDServiceException
	 * @Description: This method will return all rules for a family.
	 * @author 212556286
	 */
	List<AlertRuleVO> getAlertRulesForFamily(final String strLanguage,
			final String isGECustomer, final String customerId,
			final String family)
			throws RMDServiceException;

	/**
	 * Used to return firing details for an Alert
	 * 
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param alertFiringId
	 * @return AlertRunsVO
	 * @throws RMDServiceException
	 * @author 212556286
	 */
	AlertRunsVO getAlertFiringDetails(final String strLanguage,
			final String isGECustomer, final String alertFiringId,
			final String custUOM, final String strCustomer)
			throws RMDServiceException;

	/**
	 * Used to fetch Alert Rule Miss Details
	 * 
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param ruleId
	 * @param runId
	 * @return AlertRunsVO
	 * @throws RMDServiceException
	 * @author 212556286
	 */
	AlertRunsVO getAlertMissingDetails(final String strLanguage,
			final String isGECustomer, final String ruleId, final String runId,
			final String custUOM, final String strCustomer)
			throws RMDServiceException;

	/**
	 * Used to fetch rules based on family.
	 * 
	 * @param strLanguage
	 * @param family
	 * @return
	 * @throws RMDServiceException
	 */
	List<RuleByFamilyVO> getRuleByFamily(final String strLanguage,
			final String family) throws RMDServiceException;

	List<ElementVO> getRoadNumberHeaders(String customerId)
			throws RMDServiceException;
	
	String getNextRuleCacheRefresh(String ruleType) throws RMDServiceException;
	List<ParameterServiceVO> getSolutionParameters(List<String> modelList) throws RMDServiceException;
	List<String> getRoRRuleInformation(List<String> ruleIdList,
			String strFinalRule) throws RMDServiceException;
}
