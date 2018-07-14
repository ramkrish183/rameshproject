/**
 * ============================================================
 * File : RuleBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.bo.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.bo.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.ActivateDeActivateRuleDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleCommonDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.RuleDAOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.dao.intf.SearchRuleIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ComplexRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.FinalRuleServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ParameterRequestServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceCriteriaVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.SearchRuleServiceDefaultVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 *
 * @Author 		:
 * @Version 	: 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified : February 14, 2012
 * @Modified By :
 * @Contact 	:
 * @Description :
 * @History		:New methods added
 * getCustomerList, getModelList, getFleetList, getConfigurationList, getAssetList
 *
 ******************************************************************************/
/**
 * @author krishbal
 *
 */
/**
 * @author krishbal
 */
@SuppressWarnings("unchecked")
public class RuleBOImpl implements RuleBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleBOImpl.class);
    private RuleDAOIntf objRuleDAOInf;
    private RuleCommonDAOIntf objRuleCommonDAOIntf;
    private ActivateDeActivateRuleDAOIntf objActivateDeActivateRuleDAOIntf;
    private SearchRuleIntf objSearchRuleIntf;

    public RuleCommonDAOIntf getObjRuleCommonDAOIntf() {
        return objRuleCommonDAOIntf;
    }

    public void setObjRuleCommonDAOIntf(final RuleCommonDAOIntf objRuleCommonDAOIntf) {
        this.objRuleCommonDAOIntf = objRuleCommonDAOIntf;
    }

    /**
     * @param objRuleDAOInf
     */
    public RuleBOImpl(RuleDAOIntf objRuleDAOInf) {
        this.objRuleDAOInf = objRuleDAOInf;
        this.objRuleCommonDAOIntf = objRuleCommonDAOIntf;
    }

    /**
     * @param objActivateDeActivateRuleDAOIntf
     */
    public RuleBOImpl(final ActivateDeActivateRuleDAOIntf objActivateDeActivateRuleDAOIntf) {
        this.objActivateDeActivateRuleDAOIntf = objActivateDeActivateRuleDAOIntf;
    }

    /**
     * @param objSearchRuleIntf
     */
    public RuleBOImpl(final SearchRuleIntf objSearchRuleIntf) {
        this.objSearchRuleIntf = objSearchRuleIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#
     * getSearchRuleResult(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.SearchRuleServiceCriteriaVO)
     */
    /*
     * This Method is used for call the getSearchRuleResult method in
     * RuleDAOImpl
     */
    @Override
    public List getSearchRuleResult(final SearchRuleServiceCriteriaVO objSearchCriteriaVO) throws RMDBOException {
        List arlSearch = null;
        try {
            arlSearch = objSearchRuleIntf.getSearchRuleResult(objSearchCriteriaVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSearch;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#
     * getSearchRuleCriteria()
     */
    /*
     * This Method is used for call the getSearchRuleCriteria method in
     * RuleDAOImpl
     */
    @Override
    public SearchRuleServiceDefaultVO getSearchRuleCriteria(final String strLanguage) throws RMDBOException {
        SearchRuleServiceDefaultVO objSearchDefaultVO = null;
        try {
            objSearchDefaultVO = objSearchRuleIntf.getSearchRuleCriteria(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objSearchDefaultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#
     * getInitialLoadForAddRule()
     */
    /*
     * This Method is used for call the getInitialLoadForAddRule method in
     * RuleDAOImpl
     */
    @Override
	public List getInitialLoadForAddRule(ParameterRequestServiceVO objParameterRequestServiceVO)
			throws RMDBOException {
		List arlDataPack = null;
		try {
			arlDataPack = objRuleDAOInf.getInitialLoadForAddRule(objParameterRequestServiceVO);
		}
		catch (RMDDAOException e) {
			throw e;
		}
		return arlDataPack;
	}

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getArlFunctionList()
     */
    /*
     * This Method is used for call the getArlFunctionList method in RuleDAOImpl
     */
    @Override
    public List getArlFunctionList(final String strLanguage) throws RMDBOException {
        List arlFunction = null;
        try {
            arlFunction = objRuleDAOInf.getconfigFcnValues(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFunction;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#
     * getInitialRuleDefValues()
     */
    /*
     * This Method is used for call the getInitialRuleDefValues method in
     * RuleDAOImpl
     */
    @Override
    public Map getInitialRuleDefValues(final String strModel, final Long lngUserId, final String strLanguage)
            throws RMDBOException {
        Map filterListMap = null;
        try {
            filterListMap = objRuleDAOInf.getInitialRuleDefValues(strModel, lngUserId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return filterListMap;
    }

    /**
     * @param FinalRuleServiceVO
     * @Author:iGATE
     * @return final rule Id
     * @throws RMDBOException
     * @Description:This method is used to call saveFinalRule(FinalRuleServiceVO
     *                   objFinalRuleServiceVO)
     */
    @Override
    public String saveFinalRule(final FinalRuleServiceVO finalRuleServiceVO) throws RMDBOException {
        String strFinalRuleID = null;
        boolean blnCanBeAdded = false;
        try {
            if (finalRuleServiceVO.getArlComplexRule() != null && !finalRuleServiceVO.getArlComplexRule().isEmpty()) {
                final int iComplexRuleSize = finalRuleServiceVO.getArlComplexRule().size();
                for (int i = 0; i < iComplexRuleSize; i++) {
                    ComplexRuleServiceVO objComplexRuleServiceVO = finalRuleServiceVO.getArlComplexRule().get(i);
                    // If complex Rule validate them before use.
                    blnCanBeAdded = validateComplexRule(objComplexRuleServiceVO);
                    if (blnCanBeAdded == false) {
                        break;
                    }
                }
                /**
                 * If the rule saved as Draft, then the Complete Status will be
                 * No. If the rule is saved as Active, then the complete status
                 * will be Yes. For Draft rule, we can able to save rule with or
                 * without Rule definition For Active rule, we can able to save
                 * rule with Rule definition
                 **/
                if (blnCanBeAdded) {
                    if (finalRuleServiceVO.getArlComplexRule() != null
                            && !finalRuleServiceVO.getArlComplexRule().isEmpty()
                            && (finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.NO)
                                    || ((finalRuleServiceVO.getStrCompleteStatus()
                                            .equalsIgnoreCase(RMDCommonConstants.YES)
                                            && finalRuleServiceVO.getArlRuleDefinition() != null)
                                            && (finalRuleServiceVO.getStrCompleteStatus()
                                                    .equalsIgnoreCase(RMDCommonConstants.YES)
                                                    && !finalRuleServiceVO.getArlRuleDefinition().isEmpty()))
                                    || ((finalRuleServiceVO.getStrCompleteStatus()
                                            .equalsIgnoreCase(RMDCommonConstants.TEST)
                                            && finalRuleServiceVO.getArlRuleDefinition() != null)
                                            && (finalRuleServiceVO.getStrCompleteStatus()
                                                    .equalsIgnoreCase(RMDCommonConstants.TEST)
                                                    && !finalRuleServiceVO.getArlRuleDefinition().isEmpty())))) {

                        if (finalRuleServiceVO.getArlRuleHistoryVO().isEmpty()) {
                            strFinalRuleID = objRuleDAOInf.ruleTitleCheck(finalRuleServiceVO);
                        }
                        // if the rule title already exists throw exception
                        if (strFinalRuleID != null && strFinalRuleID.equals(RMDServiceConstants.TITLE_ALREADY_EXIST)) {
                            String errorCode = RMDCommonUtility
                                    .getErrorCode(RMDServiceConstants.TITLE_ALREADY_EXIST_EXCEPTION);
                            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                    new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                    RMDCommonConstants.MINOR_ERROR);
                        } else {
                            strFinalRuleID = objRuleDAOInf.saveFinalRule(finalRuleServiceVO);
                        }
                    } else {
                        /**
                         * For Active rule, if Rule definition does not exist,
                         * throw exception
                         */
                        if ((finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.YES)
                                || finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.TEST))
                                && (finalRuleServiceVO.getArlRuleDefinition() == null
                                        || finalRuleServiceVO.getArlRuleDefinition().isEmpty())) {
                            String errorCode = RMDCommonUtility
                                    .getErrorCode(RMDServiceConstants.ATLEAST_ONE_RULE_DEFINITION);
                            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                    new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                    RMDCommonConstants.MINOR_ERROR);
                        }

                    }
                } else {
                    String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.COMPLEXRULE_NOTBUILD_PROPERLY);
                    throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                            new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                            RMDCommonConstants.MINOR_ERROR);

                }
            } else {
                // Logic for Saving Simple rule
                if ((finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.NO)
                        || ((finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.YES)
                                || finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.TEST))
                                && finalRuleServiceVO.getArlRuleDefinition() != null
                                && !finalRuleServiceVO.getArlRuleDefinition().isEmpty()))
                        && finalRuleServiceVO.getArlSimpleRule() != null
                        && !finalRuleServiceVO.getArlSimpleRule().isEmpty()) {
                    // Condition for simple rule, if the rule has more than 1
                    // simple rule and don't not have complex rule
                    if (finalRuleServiceVO.getArlSimpleRule().size() > 1) {
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDServiceConstants.MORE_THAN_ONE_SIMPLE_RULE_WITHOUT_COMPLERULE);
                        throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                RMDCommonConstants.MINOR_ERROR);
                    } else {

                        if (finalRuleServiceVO.getArlRuleHistoryVO().isEmpty()) {
                            strFinalRuleID = objRuleDAOInf.ruleTitleCheck(finalRuleServiceVO);
                        }
                        // if the rule title already exists throw exception
                        if (strFinalRuleID != null && strFinalRuleID.equals(RMDServiceConstants.TITLE_ALREADY_EXIST)) {
                            String errorCode = RMDCommonUtility
                                    .getErrorCode(RMDServiceConstants.TITLE_ALREADY_EXIST_EXCEPTION);
                            throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                    new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                    RMDCommonConstants.MINOR_ERROR);
                        } else {
                            strFinalRuleID = objRuleDAOInf.saveFinalRule(finalRuleServiceVO);
                        }
                    }
                } else {
                    // Active simple rules should have Rule definition
                    if ((finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.YES)
                            || finalRuleServiceVO.getStrCompleteStatus().equalsIgnoreCase(RMDCommonConstants.TEST))
                            && (finalRuleServiceVO.getArlRuleDefinition() == null
                                    || finalRuleServiceVO.getArlRuleDefinition().isEmpty())) {
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDServiceConstants.ATLEAST_ONE_RULE_DEFINITION);
                        throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                RMDCommonConstants.MINOR_ERROR);

                    }
                    // Atleast one simple rule should be present, else throw
                    // exception
                    else {
                        String errorCode = RMDCommonUtility
                                .getErrorCode(RMDServiceConstants.ATLEAST_ONESIMPLE_RULE_NEEDED);
                        throw new RMDDAOException(errorCode, new String[] {}, RMDCommonUtility.getMessage(errorCode,
                                new String[] {}, finalRuleServiceVO.getStrLanguage()), null,
                                RMDCommonConstants.MINOR_ERROR);

                    }
                }
            }
        } catch (RMDDAOException e) {
            throw e;
        }
        return strFinalRuleID;
    }

    /**
     * @Author:
     * @param objComplexRuleVO
     * @return
     * @Description:This Method is used for
     *                   validateComplexRule's(ComplexRuleVO's) the complex rule
     *                   is validated for 1. if the rule1 is equal to rule2. 2.
     *                   Check if the rule1 is equal to Default dropdown value
     *                   Pick 3. Check if the rule2 is equal to Default dropdown
     *                   value Pick 4. Check if the Function is equal to Default
     *                   dropdown value Pick
     */
    private boolean validateComplexRule(final ComplexRuleServiceVO objComplexRuleServiceVO) {
        boolean blnStatus = true;
        try {
            if (!RMDServiceConstants.RULE_DEFAULT_DROPDOWN_VALUE.equals(objComplexRuleServiceVO.getStrRule1())) {
                if (!RMDServiceConstants.RULE_DEFAULT_DROPDOWN_VALUE.equals(objComplexRuleServiceVO.getStrFunction())) {
                    if (RMDServiceConstants.RULE_DEFAULT_DROPDOWN_VALUE.equals(objComplexRuleServiceVO.getStrRule2())) {
                        blnStatus = false;
                    } else {
                        if (objComplexRuleServiceVO.getStrRule2().equals(objComplexRuleServiceVO.getStrRule1())) {
                            blnStatus = false;
                        }
                    }
                } else {
                    objComplexRuleServiceVO.setStrFunction(RMDCommonConstants.EMPTY_STRING);
                    objComplexRuleServiceVO.setStrRule2(RMDCommonConstants.EMPTY_STRING);
                }
            } else {
                blnStatus = false;
            }
        } catch (Exception e) {
            LOG.error("Unexpected error occurred in AddComplexRuleBean validateComplexRule Method", e);
        }
        return blnStatus;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#activateRule(
     * java.lang.String)
     */
    /* This Method is used for call the activateRule method in RuleDAOImpl */
    @Override
    public List activateRule(final String strFinalRuleID, final String strLanguage, final String strUser)
            throws RMDBOException {
        List deActiveList = null;
        try {
            deActiveList = objActivateDeActivateRuleDAOIntf.activateRule(strFinalRuleID, strLanguage, strUser);
        } catch (RMDDAOException e) {
            throw e;
        }
        return deActiveList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * deActivateRule(java.lang.String)
     */
    /* This Method is used for call the deActivateRule method in RuleDAOImpl */
    @Override
    public List<String> deActivateRule(String strFinalRuleID, String strLanguage, String strUser)
            throws RMDBOException {
        List<String> strViewID = null;
        try {
            strViewID = objActivateDeActivateRuleDAOIntf.deActivateRule(strFinalRuleID, strLanguage, strUser);
        } catch (RMDDAOException e) {
            throw e;
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOIntf
     * #deActivateRule(java.lang.String)
     */
    /*
     * This Method is used for call the deActiveRuleWithOrgId method in
     * RuleDAOImpl
     */
    @Override
    public String deActiveRuleWithOrgId(final String strOriginalId, final String strFinalRuleID,
            final String strLanguage) throws RMDBOException {
        String strViewID = null;
        try {
            strViewID = objActivateDeActivateRuleDAOIntf.deActiveRuleWithOrgId(strOriginalId, strFinalRuleID,
                    strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.bo.intf.RuleBOInf#
     * getconfigFcnValues()
     */
    /*
     * This Method is used for call the getconfigFcnValues method in RuleDAOImpl
     */
    @Override
    public List getconfigFcnValues(final String strLanguage) throws RMDBOException {
        List arlConfigFcn = null;
        try {
            arlConfigFcn = objRuleDAOInf.getconfigFcnValues(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlConfigFcn;
    }

    /*
     * This Method is used for call the getconfigFcnValues method in RuleDAOImpl
     */
    @Override
    public List getParentRule(final String strRuleId, final String strLanguage) throws RMDBOException {
        List arlParentRuleList = null;
        try {
            arlParentRuleList = objActivateDeActivateRuleDAOIntf.getParentRule(strRuleId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlParentRuleList;
    }

    @Override
    public List getFleetsList(final String strCustomerId, final String strLanguage) {
        List arlFleetList = null;
        try {
            arlFleetList = objRuleDAOInf.getFleetsList(strCustomerId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFleetList;
    }

    @Override
    public List getConfigurationList(final String[] strModel, final String strLanguage) {
        List arlConfigurationList = null;
        try {
            arlConfigurationList = objRuleDAOInf.getConfigurationList(strModel, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlConfigurationList;
    }

    @Override
    public List getExcludedCustomerList(final String[] strCustomerId, final String strLanguage) {
        List arlCustomerList = null;
        try {
            arlCustomerList = objRuleDAOInf.getExcludedCustomerList(strCustomerId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCustomerList;
    }

    @Override
    public List getAllConfigList(final String family, final String strLanguage) {
        List arlConfigList = null;
        try {
            arlConfigList = objRuleDAOInf.getAllConfigList(family, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlConfigList;
    }

    @Override
    public List getCustomerList(final String strLanguage, String userId) {
        List arlCustomerList = null;
        try {
            arlCustomerList = objRuleDAOInf.getCustomerList(strLanguage, userId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCustomerList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getCustomerList(java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String[], java.lang.String) Method to
     * retrieve Customer List based on Model, Fleet, Unit No and Configuration
     */
    @Override
    public List getCustomerList(final String strModel, final String strFleet, final String strConfig[],
            final String strUnitNumber, final String strLanguage) {
        List arlCustomerList = null;
        try {
            arlCustomerList = objRuleDAOInf.getCustomerList(strModel, strFleet, strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCustomerList;
    }

    @Override
    public List getModelList(final String strfamily, final String strLanguage, String strKEP) {
        List arlModelList = null;
        try {
            arlModelList = objRuleDAOInf.getModelList(strfamily, strLanguage, strKEP);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlModelList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getModelList (java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Model List based
     * on Customer, Fleet, Unit No and Configuration
     */
    @Override
    public List<ElementVO> getModelList(final String strCustomer, final String strFleet, final String strConfig[],
            final String strUnitNumber, final String strFamily, final String strLanguage, String strKEP) {
        List<ElementVO> arlModelList = null;
        try {
            arlModelList = objRuleDAOInf.getModelList(strCustomer, strFleet, strConfig, strUnitNumber, strFamily,
                    strLanguage, strKEP);
        } catch (RMDDAOException ex) {
        	LOG.error(ex, ex);
            throw ex;
        } catch (Exception ex) {
        	LOG.error(ex, ex);
        }
        
        return arlModelList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getFleetList (java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Fleet List based
     * on Customer, Configuration, Unit No and Model
     */
    @Override
    public List getFleetList(final String strCustomer, final String strModel, final String strConfig[],
            final String strUnitNumber, final String strLanguage) {
        List arlFleetList = null;
        try {
            arlFleetList = objRuleDAOInf.getFleetList(strCustomer, strModel, strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFleetList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getAssetList (java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Asset List based
     * on Customer, Fleet, Configuration and Model
     */
    @Override
    public List<AssetServiceVO> getAssetList(final String strCustomer, final String strModel, final String strConfig[],
            final String strFleet, final String strLanguage) {
        List<AssetServiceVO> arlAssetList = null;
        try {
            arlAssetList = objRuleDAOInf.getAssetList(strCustomer, strModel, strConfig, strFleet, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlAssetList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.cm.services.tools.rulemgmt.bo.intf.RuleBOIntf#
     * getConfigurationList(java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String[], java.lang.String) Method to
     * retrieve Configuration List based on Customer, Fleet, Unit No and Model
     */
    @Override
    public List getConfigurationList(final String strCustomer, final String strModel, final String strFleet,
            final String strUnitNumber, final String strFamily, final String strLanguage) {
        List arlConfigList = null;
        try {
            arlConfigList = objRuleDAOInf.getConfigurationList(strCustomer, strModel, strFleet, strUnitNumber,
                    strFamily, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlConfigList;
    }

    /**
     * @return the objRuleDAOInf
     */
    public RuleDAOIntf getObjRuleDAOInf() {
        return objRuleDAOInf;
    }

    /**
     * @param objRuleDAOInf
     *            the objRuleDAOInf to set
     */
    public void setObjRuleDAOInf(final RuleDAOIntf objRuleDAOInf) {
        this.objRuleDAOInf = objRuleDAOInf;
    }

    /**
     * @return the objActivateDeActivateRuleDAOIntf
     */
    public ActivateDeActivateRuleDAOIntf getObjActivateDeActivateRuleDAOIntf() {
        return objActivateDeActivateRuleDAOIntf;
    }

    /**
     * @param objActivateDeActivateRuleDAOIntf
     *            the objActivateDeActivateRuleDAOIntf to set
     */
    public void setObjActivateDeActivateRuleDAOIntf(
            final ActivateDeActivateRuleDAOIntf objActivateDeActivateRuleDAOIntf) {
        this.objActivateDeActivateRuleDAOIntf = objActivateDeActivateRuleDAOIntf;
    }

    /**
     * @return the objSearchRuleIntf
     */
    public SearchRuleIntf getObjSearchRuleIntf() {
        return objSearchRuleIntf;
    }

    /**
     * @param objSearchRuleIntf
     *            the objSearchRuleIntf to set
     */
    public void setObjSearchRuleIntf(final SearchRuleIntf objSearchRuleIntf) {
        this.objSearchRuleIntf = objSearchRuleIntf;
    }

    /**
     * @Author:
     * @param: strRuleId
     * @return lstRuleId
     * @throws RMDBOException
     * @Description:This method is the BO Implementation for getting the list of
     *                   RuleIds
     */

    @Override
    public List getRules(final String strRuleId) throws RMDBOException {
        LOG.debug("Begin RuleBOImpl getRules method");
        List lstRuleId = null;
        try {
            lstRuleId = objRuleDAOInf.getRules(strRuleId);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        LOG.debug("End RuleBOImpl getRules method");
        return lstRuleId;
    }

    @Override
    public List<ElementVO> getRuleCreatedBy(final String strLanguage) throws RMDBOException {
        List<ElementVO> arlRuleCreatedByList = null;
        try {
            arlRuleCreatedByList = objRuleDAOInf.getRuleCreatedBy(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRuleCreatedByList;
    }

    @Override
    public List<ElementVO> getRuleLastUpdatedBy(final String strLanguage) throws RMDBOException {
        List<ElementVO> arlRuleLastUpdatedByList = null;
        try {
            arlRuleLastUpdatedByList = objRuleDAOInf.getRuleLastUpdatedBy(strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRuleLastUpdatedByList;
    }

    @Override
    public List<ElementVO> getRuleLookupValues(final String strListName, final String strLanguage) {
        List arlRxLookupValues = null;
        try {
            arlRxLookupValues = objRuleDAOInf.getRuleLookupValues(strListName, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return arlRxLookupValues;
    }

    @Override
    public String getRuleLookupValues(final String strListName) {
        String strRxLookupValues = null;
        try {
            strRxLookupValues = objRuleDAOInf.getRuleLookupValues(strListName);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return strRxLookupValues;
    }

    /**
     * @param strLanguage,strFamily
     * @Author:iGATE
     * @return list of ParameterServiceVO
     * @throws RMDBOException
     * @Description:This method will return SDP params and its values.
     */
    @Override
    public List<ParameterServiceVO> getSDPValues(final String family, final String strLanguage) throws RMDBOException {
        List<ParameterServiceVO> listParameterServiceVO = null;
        try {
            listParameterServiceVO = objRuleDAOInf.getSDPValues(family, strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return listParameterServiceVO;
    }

    /**
     * @param strLanguage
     * @return list of ElementVO
     * @throws RMDBOException
     * @Description:This method will return performance values.
     */
    @Override
    public List<ElementVO> getPerformanceCheckValues(final String strLanguage) throws RMDBOException {
        List<ElementVO> performanceCheckValues = null;
        try {
            performanceCheckValues = objRuleDAOInf.getPerformanceCheckValues(strLanguage);
        } catch (RMDDAOException ex) {
            throw ex;
        }
        return performanceCheckValues;
    }

    /**
     * @param String
     *            strLanguage,String strCustomer
     * @return list of ElementVO
     * @throws RMDBOException
     * @Description:This method will FETCH Created by based on external customer
     */
    @Override
    public List<ElementVO> getRuleCreatedByExternal(final String strLanguage, final String strCustomer)
            throws RMDBOException {
        List<ElementVO> arlRuleCreatedByList = null;
        try {
            arlRuleCreatedByList = objRuleDAOInf.getRuleCreatedByExternal(strLanguage, strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRuleCreatedByList;
    }

    /**
     * @param String
     *            strLanguage,String strCustomer
     * @return list of ElementVO
     * @throws RMDBOException
     * @Description:This method will FETCH Last Updated by based on external
     *                   customer
     */
    @Override
    public List<ElementVO> getRuleLastUpdatedByExternal(final String strLanguage, final String strCustomer)
            throws RMDBOException {
        List<ElementVO> arlRuleLastUpdatedByList = null;
        try {
            arlRuleLastUpdatedByList = objRuleDAOInf.getRuleLastUpdatedByExternal(strLanguage, strCustomer);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlRuleLastUpdatedByList;
    }

	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	public List<ParameterServiceVO> getGeoLocationValues(final String customer,final String family,
			final String strLanguage) throws RMDBOException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleDAOInf.getGeoLocationValues(customer,family,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listParameterServiceVO;
	}
	
	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	public List<ParameterServiceVO> getATSParameters(final String family,
			final String strLanguage,String uom,final String customer) throws RMDBOException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleDAOInf.getATSParameters(family,
					strLanguage,uom,customer);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listParameterServiceVO;
	}
	
	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	public List<ElementVO> getCustomerForRule(final String ruleId,
			final String strLanguage) throws RMDBOException {
		List<ElementVO> listElementVO = null;
		try {
			listElementVO = objRuleDAOInf.getCustomerForRule(ruleId,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listElementVO;
	}
	
	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	public List<ParameterServiceVO> getColValues(final String family,
			final String strLanguage) throws RMDBOException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleDAOInf.getColValues(family,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listParameterServiceVO;
	}
	
	/**
	 * @param strLanguage
	 * @author 212556286
	 * @return List of AlertRuleVO
	 * @throws RMDBOException
	 * @Description:This method will return all alerts fired for last 24 hrs.
	 */
	public List<AlertRuleVO> getRecentAurizonFiring(final String strLanguage,
			final String isGECustomer, final String ruleType,
			final String family, final String ruleId, final String ruleTitle,
			final String roadHdr, final String roadNo, final String vehicleId,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDBOException {
		List<AlertRuleVO> listAlertRuleVO = null;
		try {
			listAlertRuleVO = objRuleDAOInf.getRecentAurizonFiring(strLanguage,
					isGECustomer, ruleType, family, ruleId, ruleTitle, roadHdr,
					roadNo, vehicleId, dateStart, dateEnd, strCustomer);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listAlertRuleVO;
	}
	
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
	public List<AlertRunsVO> getAlertRuns(final String strLanguage,
			final String isGECustomer, final String vehHdr,
			final String roadNo, final String diagService,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDBOException {
		List<AlertRunsVO> listAlertRunsVO = null;
		try {
			listAlertRunsVO = objRuleDAOInf.getAlertRuns(strLanguage,
					isGECustomer, vehHdr, roadNo, diagService, dateStart,
					dateEnd, strCustomer);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listAlertRunsVO;
	} 
	
	/**
	 * 
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param family
	 * @return List of AlertRuleVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	public List<AlertRuleVO> getAlertRulesForFamily(final String strLanguage,
			final String isGECustomer, final String customerId,
			final String family)
			throws RMDBOException {
		List<AlertRuleVO> listAlertRuleVO = null;
		try {
			listAlertRuleVO = objRuleDAOInf.getAlertRulesForFamily(strLanguage,
					isGECustomer,customerId, family);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return listAlertRuleVO;
	}
	
	
	/**
	 * Returns firing details of an Alert.
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param alertFiringId
	 * @return AlertRunsVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	public AlertRunsVO getAlertFiringDetails(final String strLanguage,
			final String isGECustomer, final String alertFiringId,
			final String custUOM, final String strCustomer)
			throws RMDBOException {
		AlertRunsVO firingDetails = null;
		try {
			firingDetails = objRuleDAOInf.getAlertFiringDetails(strLanguage,
					isGECustomer,alertFiringId, custUOM,strCustomer);
		} catch(RMDDAOException ex) {
			throw ex;
		}
		return firingDetails;
	}
	
	/**
	 * Used to fetch Alery Rule Miss Details
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param ruleId
	 * @param runId
	 * @return AlertRunsVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	public AlertRunsVO getAlertMissingDetails(final String strLanguage,
			final String isGECustomer, final String ruleId, final String runId,
			final String custUOM, final String strCustomer)
			throws RMDBOException {
		AlertRunsVO fireMissDetails = null;
		try {
			fireMissDetails = objRuleDAOInf.getAlertMissingDetails(strLanguage,
					isGECustomer, ruleId, runId, custUOM, strCustomer);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return fireMissDetails;
	}
	
	/**
	 * Used to fetch Rules based on family
	 * @param strLanguage
	 * @param family
	 * @return List of RuleByFamilyVO
	 * @throws RMDBOException
	 * @author 212556286
	 */
	public List<RuleByFamilyVO> getRuleByFamily(final String strLanguage, final String family) throws RMDBOException {
		List<RuleByFamilyVO> result = null;
		try {
			result = objRuleDAOInf.getRuleByFamily(strLanguage, family);
		} catch(RMDDAOException ex) {
			throw ex;
		}
		return result;
	}
	
	/**
	 * Used to Fetch RoadNumbres for Customers.
	 * @author 212556286
	 */
	public List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDBOException {
		List<ElementVO> result = null;
		try {
		result = objRuleDAOInf.getRoadNumberHeaders(customerId);
		} catch(RMDDAOException ex) {
			throw ex;
		}
		
		return result;
	}

	@Override
	public String getNextRuleCacheRefresh(String ruleType)
			throws RMDBOException {
		String result = null;
		try {
		result = objRuleDAOInf.getNextRuleCacheRefresh(ruleType);
		} catch(RMDDAOException ex) {
			throw ex;
		}
		return result;
	}

	@Override
	public List<ParameterServiceVO> getSolutionParameters(List<String> modelList)
			throws RMDBOException {
		List<ParameterServiceVO> result = null;
		try {
			result = objRuleDAOInf.getSolutionParameters(modelList);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return result;
	}
	
	@Override
	public List<String> getRoRRuleInformation(List<String> ruleIdList,String strFinalRule)
			throws RMDBOException {
		List<String> notRor = null;
		try {
			notRor = objRuleDAOInf.getRoRRuleInformation(ruleIdList,strFinalRule);
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return notRor;
	}
}