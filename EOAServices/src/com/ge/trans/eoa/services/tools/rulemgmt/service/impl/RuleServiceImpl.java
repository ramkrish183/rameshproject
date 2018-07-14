/**
 * ============================================================
 * File : RuleServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rulemgmt.service.impl
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
package com.ge.trans.eoa.services.tools.rulemgmt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.AlertRuleVO;
import com.ge.trans.rmd.common.valueobjects.AlertRunsVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.RuleByFamilyVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.asset.service.valueobjects.AssetServiceVO;
import com.ge.trans.eoa.services.cases.service.valueobjects.ParameterServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.bo.intf.RuleBOIntf;
import com.ge.trans.eoa.services.tools.rulemgmt.service.intf.RuleServiceIntf;
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
public class RuleServiceImpl implements RuleServiceIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(RuleServiceImpl.class);
    private RuleBOIntf objRuleBOInf;

    /**
     *
     */
    public RuleServiceImpl() {
    }

    /**
     * @param objRuleBOInf
     */
    public RuleServiceImpl(RuleBOIntf objRuleBOInf) {
        super();
        this.objRuleBOInf = objRuleBOInf;
    }

    /**
     * @return the objRuleBOInf
     */
    public RuleBOIntf getObjRuleBOInf() {
        return objRuleBOInf;
    }

    /**
     * @param objRuleBOInf
     *            the objRuleBOInf to set
     */
    public void setObjRuleBOInf(RuleBOIntf objRuleBOInf) {
        this.objRuleBOInf = objRuleBOInf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceInf
     * #getSearchRuleResult(com.ge.trans.rmd.services.tools.rulemgmt.
     * service.valueobjects.SearchRuleServiceCriteriaVO)
     */
    /*
     * This Method is used for call the getSearchRuleResult method in RuleBOImpl
     */
    @Override
    public List getSearchRuleResult(SearchRuleServiceCriteriaVO searchRuleServiceCriteriaVO)
            throws RMDServiceException {
        List arlSearch = null;
        try {
            arlSearch = objRuleBOInf.getSearchRuleResult(searchRuleServiceCriteriaVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, searchRuleServiceCriteriaVO.getStrLanguage());
        }
        return arlSearch;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceInf
     * #getSearchRuleCriteria()
     */
    /*
     * This Method is used for call the getSearchRuleCriteria method in
     * RuleBOImpl
     */
    @Override
    public SearchRuleServiceDefaultVO getSearchRuleCriteria(String strLanguage) throws RMDServiceException {
        SearchRuleServiceDefaultVO objSearchRuleServiceDefaultVO = null;
        try {
            objSearchRuleServiceDefaultVO = objRuleBOInf.getSearchRuleCriteria(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return objSearchRuleServiceDefaultVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceInf
     * #getInitialLoadForAddRule()
     */
    /*
     * This Method is used for call the getInitialLoadForAddRule method in
     * RuleBOImpl
     */
    /*
     * This Method is used for call the getInitialLoadForAddRule method in
     * RuleBOImpl
     */
    public List getInitialLoadForAddRule(ParameterRequestServiceVO objParameterRequestServiceVO)
	   throws RMDServiceException {
	  List arlDataPack = null;
	  try {
	   arlDataPack = objRuleBOInf.getInitialLoadForAddRule(objParameterRequestServiceVO);
	  }
	  catch (RMDDAOException ex) {
	   throw new RMDServiceException(ex.getErrorDetail());
	  }
	  catch (RMDBOException ex) {
	   throw new RMDServiceException(ex.getErrorDetail());
	  }
	  catch (Exception ex) {
	   RMDServiceErrorHandler.handleGeneralException(ex,objParameterRequestServiceVO.getStrLanguage());
	  }
	  return arlDataPack;
	 }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf#
     * getArlFunctionList()
     */
    /*
     * This Method is used for call the getArlFunctionList method in RuleBOImpl
     */
    @Override
    public List getArlFunctionList(String strLanguage) throws RMDServiceException {
        List arlFunctionList = null;
        try {
            arlFunctionList = objRuleBOInf.getArlFunctionList(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFunctionList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #getInitialRuleDefValues()
     */
    /*
     * This Method is used for call the getInitialRuleDefValues method in
     * RuleBOImpl
     */
    @Override
    public Map getInitialRuleDefValues(String strModel, Long lngUserId, String strLanguage) throws RMDServiceException {
        Map filterListMap = null;
        try {
            filterListMap = objRuleBOInf.getInitialRuleDefValues(strModel, lngUserId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return filterListMap;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #saveFinalRule(com.ge.trans.rmd.services.tools.rulemgmt.service.
     * valueobjects.FinalRuleServiceVO)
     */
    /* This Method is used for call the saveFinalRule method in RuleBOImpl */
    @Override
    public String saveFinalRule(FinalRuleServiceVO finalRuleServiceVO) throws RMDServiceException {
        String strFinalRuleID = null;
        try {
            strFinalRuleID = objRuleBOInf.saveFinalRule(finalRuleServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, finalRuleServiceVO.getStrLanguage());
        }
        return strFinalRuleID;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #activateRule(java.lang.String)
     */
    /* This Method is used for call the activateRule method in RuleBOImpl */
    @Override
    public List activateRule(String strFinalRuleID, String strLanguage, String strUser) throws RMDServiceException {
        List deActiveList = null;

        try {
            deActiveList = objRuleBOInf.activateRule(strFinalRuleID, strLanguage, strUser);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return deActiveList;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #deActivateRule(java.lang.String)
     */
    /* This Method is used for call the deActivateRule in RuleBOImpl */
    @Override
    public List<String> deActivateRule(String strFinalRuleID, String strLanguage, String strUser)
            throws RMDServiceException {
        List<String> strViewID = null;
        try {
            strViewID = objRuleBOInf.deActivateRule(strFinalRuleID, strLanguage, strUser);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf#
     * deActiveRuleWithOrgId(java.lang.String)
     */
    /* This Method is used for call the deActiveRuleWithOrgId in RuleBOImpl */
    @Override
    public String deActiveRuleWithOrgId(String strOriginalId, String strFinalRuleID, String strLanguage)
            throws RMDServiceException {
        String strViewID = null;
        try {
            strViewID = objRuleBOInf.deActiveRuleWithOrgId(strOriginalId, strFinalRuleID, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return strViewID;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf#
     * getconfigFcnValues()
     */
    /*
     * This Method is used for call the getconfigFcnValues method in RuleBOImpl
     */
    @Override
    public List getconfigFcnValues(String strLanguage) throws RMDServiceException {
        List arlConfigFcn = null;
        try {
            arlConfigFcn = objRuleBOInf.getconfigFcnValues(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlConfigFcn;
    }

    /*
     * This Method is used for call the getSearchRuleResult method in RuleBOImpl
     */
    @Override
    public List getParentRule(String strRuleId, String strLanguage) throws RMDServiceException {
        List arlSearch = null;
        try {
            arlSearch = objRuleBOInf.getParentRule(strRuleId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlSearch;
    }

    @Override
    public List getFleetsList(String strCustomerId, String strLanguage) throws RMDServiceException {
        List arlFleet = null;
        try {
            arlFleet = objRuleBOInf.getFleetsList(strCustomerId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFleet;
    }

    @Override
    public List getConfigurationList(String[] strModel, String strLanguage) throws RMDServiceException {
        List arlConfiguration = null;
        try {
            arlConfiguration = objRuleBOInf.getConfigurationList(strModel, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlConfiguration;
    }

    @Override
    public List getExcludedCustomerList(String[] strCustomer, String strLanguage) throws RMDServiceException {
        List arlCustomer = null;
        try {
            arlCustomer = objRuleBOInf.getExcludedCustomerList(strCustomer, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlCustomer;
    }

    @Override
    public List getAllConfigList(String strFamily, String strLanguage) throws RMDServiceException {
        List arlConfig = null;
        try {
            arlConfig = objRuleBOInf.getAllConfigList(strFamily, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlConfig;
    }

    @Override
    public List getCustomerList(String strLanguage, String userId) throws RMDServiceException {
        List arlCustomer = null;
        try {
            arlCustomer = objRuleBOInf.getCustomerList(strLanguage, userId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlCustomer;
    }

    @Override
    public List getModelList(String strFamily, String strLanguage, String strKEP) throws RMDServiceException {
        List arlModel = null;
        try {
            arlModel = objRuleBOInf.getModelList(strFamily, strLanguage, strKEP);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlModel;
    }

    /**
     * @Author:
     * @param: strRuleId
     * @return lstRuleId
     * @throws RMDServiceException
     * @Description:This method is the Service Implementation for getting the
     *                   list of RuleIds
     */
    @Override
    public List getRules(String strRuleId) throws RMDServiceException {
        List lstRuleId = null;
        try {
            lstRuleId = objRuleBOInf.getRules(strRuleId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strRuleId);
        }
        return lstRuleId;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.cm.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #getCustomerList(java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String[], java.lang.String) Method to
     * retrieve Customer List based on Model, Fleet, Unit No and Configuration
     */
    @Override
    public List getCustomerList(String strModel, String strFleet, String[] strConfig, String strUnitNumber,
            String strLanguage) throws RMDServiceException {
        List arlCustomer = null;
        try {
            arlCustomer = objRuleBOInf.getCustomerList(strModel, strFleet, strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlCustomer;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.cm.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #getModelList(java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Model List based
     * on Customer, Fleet, Unit No and Configuration
     */
    @Override
    public List<ElementVO> getModelList(String strCustomer, String strFleet, String[] strConfig, String strUnitNumber,
            String strFamily, String strLanguage, String strKEP) throws RMDServiceException, RMDBOException {
        List<ElementVO> arlModel = null;
        try {
            arlModel = objRuleBOInf.getModelList(strCustomer, strFleet, strConfig, strUnitNumber, strFamily,
                    strLanguage, strKEP);
        } catch (RMDDAOException ex) {
        	LOG.error(ex, ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
        	LOG.error(ex, ex);
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlModel;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.cm.services.tools.rulemgmt.service.intf.RuleServiceIntf
     * #getFleetList(java.lang.String[], java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String) Method to retrieve Fleet List based
     * on Customer, Model, Unit No and Configuration
     */
    @Override
    public List getFleetList(String strCustomer, String strModel, String[] strConfig, String strUnitNumber,
            String strLanguage) throws RMDServiceException, RMDBOException {
        List arlFleet = null;
        try {
            arlFleet = objRuleBOInf.getFleetList(strCustomer, strModel, strConfig, strUnitNumber, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFleet;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceInf
     * #getAssetList()
     */
    /*
     * This Method is used for call the getAssetList method in RuleBOImpl
     */

    @Override
    public List<AssetServiceVO> getAssetList(String strCustomer, String strModel, String[] strConfig, String strFleet,
            String strLanguage) throws RMDServiceException, RMDBOException {
        List<AssetServiceVO> arlAsset = null;
        try {
            arlAsset = objRuleBOInf.getAssetList(strCustomer, strModel, strConfig, strFleet, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlAsset;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rulemgmt.service.intf.RuleServiceIntf#
     * getConfigurationList(java.lang.String[], java.lang.String[],
     * java.lang.String[], java.lang.String[], java.lang.String) Method to
     * retrieve Configuration List based on Customer, Model, Unit No and Fleet
     */
    @Override
    public List getConfigurationList(String strCustomer, String strModel, String strFleet, String strUnitNumber,
            String strFamily, String strLanguage) throws RMDServiceException, RMDBOException {
        List arlConfig = null;
        try {
            arlConfig = objRuleBOInf.getConfigurationList(strCustomer, strModel, strFleet, strUnitNumber, strFamily,
                    strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlConfig;
    }

    @Override
    public List<ElementVO> getRuleCreatedBy(String strLanguage) throws RMDServiceException {
        List<ElementVO> arlRuleCreatedByList = null;
        try {
            arlRuleCreatedByList = objRuleBOInf.getRuleCreatedBy(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlRuleCreatedByList;
    }

    @Override
    public List<ElementVO> getRuleLastUpdatedBy(String strLanguage) throws RMDServiceException {
        List<ElementVO> arlRuleLastUpdatedByList = null;
        try {
            arlRuleLastUpdatedByList = objRuleBOInf.getRuleLastUpdatedBy(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlRuleLastUpdatedByList;
    }

    @Override
    public List getRuleLookupValues(String listName, String strLanguage) throws RMDServiceException {
        List arlRxLookupValues = new ArrayList();
        try {
            arlRxLookupValues = objRuleBOInf.getRuleLookupValues(listName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlRxLookupValues;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rx.service.intf.RecommServiceIntf#
     * getRxLookupValues(java.lang.String)
     */
    @Override
    public String getRuleLookupValues(String listName) throws RMDServiceException {
        String strRxLookupValues = null;
        try {
            strRxLookupValues = objRuleBOInf.getRuleLookupValues(listName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, listName);
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
    public List<ParameterServiceVO> getSDPValues(final String family, final String strLanguage)
            throws RMDServiceException {
        List<ParameterServiceVO> listParameterServiceVO = null;
        try {
            listParameterServiceVO = objRuleBOInf.getSDPValues(family, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return listParameterServiceVO;
    }

    /**
     * @param strLanguage
     * @return list of ElementVO
     * @throws RMDServiceException
     * @Description:This method will return Performance check values.
     */
    @Override
    public List<ElementVO> getPerformanceCheckValues(final String strLanguage) throws RMDServiceException {
        List<ElementVO> performanceCheckList = null;
        try {
            performanceCheckList = objRuleBOInf.getPerformanceCheckValues(strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return performanceCheckList;
    }

    /**
     * @param String
     *            strLanguage,String strCustomer
     * @return list of ElementVO
     * @throws RMDServiceException
     * @Description:This method will FETCH Created by based on external customer
     */
    @Override
    public List<ElementVO> getRuleCreatedByExternal(String strLanguage, String strCustomer) throws RMDServiceException {
        List<ElementVO> arlRuleCreatedByList = null;
        try {
            arlRuleCreatedByList = objRuleBOInf.getRuleCreatedByExternal(strLanguage, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
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
    public List<ElementVO> getRuleLastUpdatedByExternal(String strLanguage, String strCustomer)
            throws RMDServiceException {
        List<ElementVO> arlRuleLastUpdatedByList = null;
        try {
            arlRuleLastUpdatedByList = objRuleBOInf.getRuleLastUpdatedByExternal(strLanguage, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
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
			final String strLanguage)throws RMDServiceException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleBOInf.getGeoLocationValues(customer,family,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
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
			final String strLanguage,String uom,final String customer)throws RMDServiceException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleBOInf.getATSParameters(family,
					strLanguage,uom,customer);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return listParameterServiceVO;
	}
	
	/**
	 * @param strLanguage,ruleId
	 * @Author:iGATE
	 * @return list of ElementVO
	 * @throws RMDServiceException
	 * @Description:This method will return customerId and CustomerName for a Rule
	*/
	public List<ElementVO> getCustomerForRule(final String ruleId,
			final String strLanguage)throws RMDServiceException {
		List<ElementVO> lstElementVO = null;
		try {
			lstElementVO = objRuleBOInf.getCustomerForRule(ruleId,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return lstElementVO;
	}
	
	/**
	 * @param strLanguage,strFamily
	 * @Author:iGATE
	 * @return list of ParameterServiceVO
	 * @throws RMDBOException
	 * @Description:This method will return SDP params and its values.
	*/
	public List<ParameterServiceVO> getColValues(final String family,
			final String strLanguage)throws RMDServiceException {
		List<ParameterServiceVO> listParameterServiceVO = null;
		try {
			listParameterServiceVO = objRuleBOInf.getColValues(family,
					strLanguage);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return listParameterServiceVO;
	}
	
	/**
	 * @param strLanguage
	 * @author 212556286
	 * @return List of AlertRuleVO
	 * @throws RMDServiceException
	 * @Description:This method will return all alerts fired for last 24 hrs.
	 */
	public List<AlertRuleVO> getRecentAurizonFiring(final String strLanguage,
			final String isGECustomer, final String ruleType,
			final String family, final String ruleId, final String ruleTitle,
			final String roadHdr, final String roadNo, final String vehicleId,
			final String dateStart, final String dateEnd,
			final String strCustomer) throws RMDServiceException {
		List<AlertRuleVO> listAlertRuleVO  = null;
		try {
			listAlertRuleVO = objRuleBOInf.getRecentAurizonFiring(strLanguage,
					isGECustomer, ruleType, family, ruleId, ruleTitle, roadHdr,
					roadNo, vehicleId, dateStart, dateEnd, strCustomer);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
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
			final String strCustomer) throws RMDServiceException {
		List<AlertRunsVO> listAlertRunsVO =null;
		try {
			listAlertRunsVO = objRuleBOInf.getAlertRuns(strLanguage, isGECustomer,vehHdr, roadNo, diagService, dateStart, dateEnd,strCustomer);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return listAlertRunsVO;
	}
	
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
	public List<AlertRuleVO> getAlertRulesForFamily(final String strLanguage,
			final String isGECustomer, final String customerId,
			final String family)
			throws RMDServiceException {
		List<AlertRuleVO> listAlertRulesVO = null;
		try {
			listAlertRulesVO = objRuleBOInf.getAlertRulesForFamily(strLanguage,
					isGECustomer, customerId, family);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return listAlertRulesVO;
	}
	
	/**
	 * Used to fetch firing details for Alert
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param alertFiringId
	 * @return AlertRunsVO
	 * @throws RMDServiceException
	 */
	public AlertRunsVO getAlertFiringDetails(final String strLanguage,
			final String isGECustomer, final String alertFiringId,
			final String custUOM, final String strCustomer)
			throws RMDServiceException {
		AlertRunsVO firingDetails = null;
		try {
			firingDetails = objRuleBOInf.getAlertFiringDetails(strLanguage,
					isGECustomer, alertFiringId, custUOM, strCustomer);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return firingDetails;
	}
	
	/**
	 * Used to fetch Alert Rule Miss Details
	 * @param strLanguage
	 * @param isGECustomer
	 * @param customerId
	 * @param ruleId
	 * @param runId
	 * @return AlertRunsVO
	 * @throws RMDServiceException
	 * @author 212556286
	 */
	public AlertRunsVO getAlertMissingDetails(final String strLanguage,
			final String isGECustomer, final String ruleId, final String runId,
			final String custUOM, final String strCustomer)
			throws RMDServiceException {
		
		AlertRunsVO fireMissDetails = null;
		try {
			fireMissDetails = objRuleBOInf.getAlertMissingDetails(strLanguage, isGECustomer, ruleId, runId, custUOM,strCustomer);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return fireMissDetails;
	}
	
	/**
	 * Used to fetch rules based on family
	 * @param strLanguage
	 * @param family
	 * @return List of RuleByFamilyVO
	 * @throws RMDServiceException
	 * @author 212556286
	 */
	public List<RuleByFamilyVO> getRuleByFamily(final String strLanguage, final String family) throws RMDServiceException {
		List<RuleByFamilyVO> result = null;
		try {
			result = objRuleBOInf.getRuleByFamily(strLanguage, family);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (Exception ex) {
			RMDServiceErrorHandler.handleGeneralException(ex,
					strLanguage);
		}
		return result;
	}
	
	public List<ElementVO> getRoadNumberHeaders(String customerId) throws RMDServiceException {
		List<ElementVO> result = null;
		try {
			result = objRuleBOInf.getRoadNumberHeaders(customerId);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} 
		return result;
	}

	@Override
	public String getNextRuleCacheRefresh(String ruleType)
			throws RMDServiceException {
		String refreshTime;
		try {
			refreshTime = objRuleBOInf.getNextRuleCacheRefresh(ruleType);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} 
		return refreshTime;
	}

	@Override
	public List<ParameterServiceVO> getSolutionParameters(List<String> modelList)
			throws RMDServiceException {
		List<ParameterServiceVO> paramrServiceVOList = null;
		try {
			paramrServiceVOList = objRuleBOInf.getSolutionParameters(modelList);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		}
		return paramrServiceVOList;
	}
	
	@Override
	public List<String> getRoRRuleInformation(List<String> ruleIdList,
			String strFinalRule) throws RMDServiceException {
		List<String> notRor = null;
		try {
			notRor = objRuleBOInf.getRoRRuleInformation(ruleIdList,strFinalRule);
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail());
		}
		return notRor;
	}
	

}