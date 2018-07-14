/**
 * ============================================================
 * File : RuleDAOInf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.dao.intf
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
package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified : February 14, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History : New methods added getCustomerList, getModelList, getFleetList,
 *          getConfigurationList, getAssetList
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface RuleDAOIntf {
    /**
     * @param strLanguage
     * @Author:
     * @return
     * @Description:
     */
	List getInitialLoadForAddRule(ParameterRequestServiceVO objParameterRequestServiceVO) throws RMDDAOException;

    /**
     * @param strModel
     * @param strLanguage
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    Map getInitialRuleDefValues(String strModel, Long lngUserId, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param finalRuleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String saveFinalRule(FinalRuleServiceVO finalRuleServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param finalRuleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    String ruleTitleCheck(FinalRuleServiceVO finalRuleServiceVO) throws RMDDAOException;

    /**
     * @param strLanguage
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getconfigFcnValues(String strLanguage) throws RMDDAOException;

    List getFleetsList(String strCustomerId, String strLanguage) throws RMDDAOException;

    List getConfigurationList(String[] strModel, String strLanguage) throws RMDDAOException;

    List getExcludedCustomerList(String[] strCustomerId, String strLanguage) throws RMDDAOException;

    List getAllConfigList(String strFamily, String strLanguage) throws RMDDAOException;

    List getCustomerList(String strLanguage, String userId) throws RMDDAOException;

    List getCustomerList(String strModel, String strFleet, String strConfig[], String strUnitNumber, String strLanguage)
            throws RMDDAOException;

    List getModelList(String strfamily, String strLanguage, String strKEP) throws RMDDAOException;

    String getCustomerId(Long lngUserId, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @param: strRuleId
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is the DAO Interface for getting the list of
     *                   RuleIds
     */

    List getRules(String strRuleId) throws RMDDAOException;

    List<ElementVO> getModelList(String strCustomer, String strFleet, String strConfig[], String strUnitNumber,
            String strFamily, String strLanguage, String strKEP) throws RMDDAOException;

    List getFleetList(String strCustomer, String strModel, String strConfig[], String strUnitNumber, String strLanguage)
            throws RMDDAOException;

    List<AssetServiceVO> getAssetList(String strCustomer, String strModel, String strConfig[], String strFleet,
            String strLanguage) throws RMDDAOException;

    List getConfigurationList(String strCustomer, String strModel, String strFleet, String strUnitNumber,
            String strFamily, String strLanguage) throws RMDDAOException;
    /**
     * @param strListName
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    /*
     * List getRuleTypeLookupValues(String strListName, String strLanguage)
     * throws RMDDAOException;
     *//**
       * @param strListName
       * @param strLanguage
       * @return
       * @throws RMDBOException
       */
    /*
     * List getStatusLookupValues(String strListName, String strLanguage) throws
     * RMDDAOException;
     *//**
       * @param strListName
       * @param strLanguage
       * @return
       * @throws RMDBOException
       */

    /*
     * List getSubSystemLookupValues(String strListName, String strLanguage)
     * throws RMDDAOException;
     *//**
       * @param strListName
       * @param strLanguage
       * @return
       * @throws RMDBOException
       *//*
         * List getVersionLookupValues(String strListName, String strLanguage)
         * throws RMDDAOException;
         */

    List<ElementVO> getRuleCreatedBy(String strLanguage) throws RMDDAOException;

    List<ElementVO> getRuleLastUpdatedBy(String strLanguage) throws RMDDAOException;

    List<ElementVO> getRuleLookupValues(String strListName, String strLanguage) throws RMDDAOException;

    String getRuleLookupValues(String strListName) throws RMDDAOException;

    /**
     * @param strLanguage,strFamily
     * @Author:iGATE
     * @return list of ParameterServiceVO
     * @throws RMDBOException
     * @Description:This method will return SDP params and its values.
     */
    List<ParameterServiceVO> getSDPValues(String family, String strLanguage) throws RMDDAOException;

    /**
     * This method is used to get performance check values
     * 
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    List<ElementVO> getPerformanceCheckValues(String strLanguage) throws RMDDAOException;

    List<ElementVO> getRuleCreatedByExternal(String strLanguage, String strCustomer) throws RMDDAOException;

    List<ElementVO> getRuleLastUpdatedByExternal(String strLanguage, String strCustomer) throws RMDDAOException;

	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	List<ParameterServiceVO> getGeoLocationValues(String customer,String family, String strLanguage)throws RMDDAOException;
	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	List<ParameterServiceVO> getATSParameters(String family, String strLanguage,String uom,String customer)throws RMDDAOException;
	/**
	 * @param strLanguage,customer
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	List<ElementVO> getCustomerForRule(String ruleId, String strLanguage)throws RMDDAOException;
	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	List<ParameterServiceVO> getColValues(String family, String strLanguage)throws RMDDAOException;
	
	/**
	 * @param
	 * @author 212556286
	 * @return List of AlertRuleVO
	 * @throws RMDDAOException
	 * @Description:This method will return all alerts fired for last 24 hrs.
	 */
	List<AlertRuleVO> getRecentAurizonFiring(String strLanguage,
			String isGECustomer, String ruleType, String family, String ruleId,
			String ruleTitle, String roadHdr, String roadNo, String vehicleId,
			String dateStart, String dateEnd, String strCustomer)
			throws RMDDAOException;
	
	/**
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param vehicleId
	 * @param dateStart
	 * @param dateEnd
	 * @author 212556286
	 * @return List of AlertRunsVO
	 * @throws RMDDAOException
	 * @Description: This method will return all alert runs for Search
	 */
	List<AlertRunsVO> getAlertRuns(String strLanguage, String isGECustomer,
			String vehHdr, String roadNo, String diagService, String dateStart,
			String dateEnd, String strCustomer) throws RMDDAOException;
	
	/**
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param family
	 * @return List of AlertRuleVO
	 * @throws RMDDAOException
	 * @Description: This method will return all rules for a family.
	 * @author 212556286
	 */
	List<AlertRuleVO> getAlertRulesForFamily(String strLanguage,
			String isGECustomer, String customerId, String family)
			throws RMDDAOException;
	
	/**
	 * Used to fetch Alert Rule Miss details
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param ruleId
	 * @param runId
	 * @return
	 * @throws RMDDAOException
	 */
	AlertRunsVO getAlertMissingDetails(String strLanguage, String isGECustomer,
			String ruleId, String runId, String custUOM, String strCustomer)
			throws RMDDAOException;
	
	/**
	 * Used to fetch firing details for an alert.
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param alertFiringId
	 * @return AlertRunVO
	 * @throws RMDDAOException
	 * @author 212556286
	 */
	AlertRunsVO getAlertFiringDetails(String strLanguage, String isGECustomer,
			String alertFiringId, String custUOM, String strCustomer)
			throws RMDDAOException;

	/**
	 * Used to fetch rules based on family
	 * @param strLanguage
	 * @param family
	 * @return
	 * @throws RMDDAOException
	 */
	List<RuleByFamilyVO> getRuleByFamily(String strLanguage, String family) throws RMDDAOException;
	
	List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDDAOException;
	
	String getNextRuleCacheRefresh(String ruleType) throws RMDDAOException;
	List<ParameterServiceVO> getSolutionParameters(List<String> modelList)throws RMDDAOException;
	List<String> getRoRRuleInformation(List<String> ruleIdList,String strFinalRule)throws RMDDAOException;
}
