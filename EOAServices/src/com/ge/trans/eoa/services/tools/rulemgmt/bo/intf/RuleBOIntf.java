/**
 * ============================================================
 * File : RuleBOInf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceDefaultVO;

/*******************************************************************************
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
public interface RuleBOIntf {

    /**
     * @param strLanguage
     * @Author:
     * @return
     * @Description:
     */
    SearchRuleServiceDefaultVO getSearchRuleCriteria(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param searchRuleServiceCriteriaVO
     * @return
     * @Description:
     */
    List getSearchRuleResult(SearchRuleServiceCriteriaVO searchRuleServiceCriteriaVO) throws RMDBOException;

    /**
     * @param strLanguage
     * @Author:
     * @return
     * @Description:
     */
	List getInitialLoadForAddRule(ParameterRequestServiceVO objParameterRequestServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param strcustomer
     * @return
     * @Description:
     */
    // ArrayList getFleets(String strcustomer) throws RMDBOException(For
    // Future);
    /**
     * @param strLanguage
     * @Author:
     * @param strdatapackElement
     * @return
     * @Description:
     */
    List getArlFunctionList(String strLanguage) throws RMDBOException;

    /*
     * ArrayList getArlFunctionList(String strdatapackElement) throws
     * RMDBOException;(For Future)
     */
    /**
     * @param strModel
     * @param strLanguage
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Map getInitialRuleDefValues(String strModel, Long lngUserId, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param finalRuleServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String saveFinalRule(FinalRuleServiceVO finalRuleServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List activateRule(String strFinalRuleID, String strLanguage, String strUser) throws RMDBOException;

    /**
     * @Author:
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<String> deActivateRule(String strFinalRuleID, String strLanguage, String strUser) throws RMDBOException;

    /**
     * @param strLanguage
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getconfigFcnValues(String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    // ArrayList getCustomer() throws RMDBOException(For Future);
    /**
     * @Author:
     * @param strOriginalId
     * @param strFinalRuleID
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    String deActiveRuleWithOrgId(String strOriginalId, String strFinalRuleID, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param strRuleId
     * @param strLanguage
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getParentRule(String strRuleId, String strLanguage) throws RMDBOException;

    List getFleetsList(String strCustomerId, String strLanguage) throws RMDBOException;

    List getConfigurationList(String[] strModel, String strLanguage) throws RMDBOException;

    List getExcludedCustomerList(String[] strCustomerId, String strLanguage) throws RMDBOException;

    List getAllConfigList(String strFamily, String strLanguage) throws RMDBOException;

    List getCustomerList(String strLanguage, String userId) throws RMDBOException;

    List getCustomerList(String strModel, String strFleet, String strConfig[], String strUnitNumber, String strLanguage)
            throws RMDBOException;

    List getModelList(String strfamily, String strLanguage, String strKEP) throws RMDBOException;

    /**
     * @Author:
     * @param: strRuleId
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is the BO Interface for getting the list of
     *                   RuleIds
     */
    List getRules(String strRuleId) throws RMDBOException;

    List<ElementVO> getModelList(String strCustomer, String strFleet, String strConfig[], String strUnitNumber,
            String strFamily, String strLanguage, String strKEP) throws RMDDAOException;

    List getFleetList(String strCustomer, String strModel, String strConfig[], String strUnitNumber, String strLanguage)
            throws RMDDAOException;

    List<AssetServiceVO> getAssetList(String strCustomer, String strModel, String strConfig[], String strFleet,
            String strLanguage) throws RMDDAOException;

    List getConfigurationList(String strCustomer, String strModel, String strFleet, String strUnitNumber,
            String strFamily, String strLanguage) throws RMDDAOException;

    /*
     * List getRuleTypeLookupValues(String strListName, String strLanguage)
     * throws RMDBOException; List getStatusLookupValues(String strListName,
     * String strLanguage) throws RMDBOException; List
     * getSubSystemLookupValues(String strListName, String strLanguage) throws
     * RMDBOException; List getVersionLookupValues(String strListName, String
     * strLanguage) throws RMDBOException;
     */

    List<ElementVO> getRuleCreatedBy(String strLanguage) throws RMDBOException;

    List<ElementVO> getRuleLastUpdatedBy(String strLanguage) throws RMDBOException;

    List<ElementVO> getRuleLookupValues(String strListName, String strLanguage) throws RMDBOException;

    String getRuleLookupValues(String strListName) throws RMDBOException;

    /**
     * @param strLanguage,strFamily
     * @Author:iGATE
     * @return list of ParameterServiceVO
     * @throws RMDBOException
     * @Description:This method will return SDP params and its values.
     */
    List<ParameterServiceVO> getSDPValues(String family, String strLanguage) throws RMDBOException;

    /**
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    List<ElementVO> getPerformanceCheckValues(String strLanguage) throws RMDBOException;

    List<ElementVO> getRuleCreatedByExternal(String strLanguage, String strCustomer) throws RMDBOException;

    List<ElementVO> getRuleLastUpdatedByExternal(String strLanguage, String strCustomer) throws RMDBOException;
	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return GEO Location params and its values.
	*/
	List<ParameterServiceVO> getGeoLocationValues(String customer, String strLanguage,String family)throws RMDBOException;
	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return GEO Location params and its values.
	*/
	List<ParameterServiceVO> getATSParameters(String family, String strLanguage,String uom,String customer)throws RMDBOException;
	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return GEO Location params and its values.
	*/
	List<ElementVO> getCustomerForRule(String ruleId, String strLanguage)throws RMDBOException;
	List<ParameterServiceVO> getColValues(String family, String strLanguage)throws RMDBOException;
	
	/**
	 * @param strLanguage
	 * @author 212556286
	 * @return List of AlertRuleVO
	 * @throws RMDBOException
	 * @Description:This method will return all alerts fired for last 24 hrs.
	 */
	List<AlertRuleVO> getRecentAurizonFiring(final String strLanguage,
			final String isGECustomer, final String ruleType,
			final String family, final String ruleId, final String ruleTitle,
			final String roadHdr, final String roadNo, final String vehicleId,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDBOException;
	
	/**
	 * 
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param vehicleId
	 * @param dateStart
	 * @param dateEnd
	 * @author 212556286
	 * @return List of AlertRunsVO
	 * @throws RMDBOException
	 * @Description: This method will return all alert runs for Search
	 */
	List<AlertRunsVO> getAlertRuns(final String strLanguage,
			final String isGECustomer, final String vehHdr,
			final String roadNo, final String diagService,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDBOException;

	/**
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param family
	 * @return List of AlertRuleVO
	 * @throws RMDBOException
	 * @author 212556286
	 * @Desccription: This method will return all rules for a family
	 */
	List<AlertRuleVO> getAlertRulesForFamily(final String strLanguage,
			final String isGECustomer, final String customerId,
			final String family)
			throws RMDBOException;	
	
	/**
	 * Used to fetch firing details for Alert
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param alertFiringId
	 * @return AlertRunsVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	AlertRunsVO getAlertFiringDetails(final String strLanguage,
			final String isGECustomer, final String alertFiringId,
			final String custUOM, final String strCustomer)
			throws RMDBOException;
	
	/**
	 * Used to fetch Alert Rule Miss Details
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param ruleId
	 * @param runId
	 * @return AlertRunsVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	AlertRunsVO getAlertMissingDetails(final String strLanguage,
			final String isGECustomer, final String ruleId, final String runId,
			final String custUOM, final String strCustomer)
			throws RMDBOException;
	
	/**
	 * Used to fetch rules based on family
	 * @param strLanguage
	 * @param family
	 * @return List  of RuleByFamilyVO
	 * @throws RMDBOException
	 */
	List<RuleByFamilyVO> getRuleByFamily(final String strLanguage, final String family) throws RMDBOException;
	
	List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDBOException;
	
	String getNextRuleCacheRefresh(String ruleType) throws RMDBOException;
	List<ParameterServiceVO> getSolutionParameters(List<String> modelList)throws RMDBOException;

	List<String> getRoRRuleInformation(List<String> ruleIdList,
			String strFinalRule) throws RMDBOException;
}