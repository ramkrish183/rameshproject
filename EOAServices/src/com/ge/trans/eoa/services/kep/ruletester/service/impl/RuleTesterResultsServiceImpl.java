package com.ge.trans.eoa.services.kep.ruletester.service.impl;

/**
 * ============================================================
 * File : RuleTesterResultsServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.ruletester.service.impl
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
import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.kep.common.constants.KEPCommonConstants;
import com.ge.trans.eoa.services.kep.ruletester.bo.intf.RuleTesterResultsBOIntf;
import com.ge.trans.eoa.services.kep.ruletester.service.intf.RuleTesterServiceIntf;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RTRequestVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RuleTesterVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterDetailsVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterResultVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the implementation class for RuleTesterResults
 *              Service layer
 * @History :
 ******************************************************************************/
public class RuleTesterResultsServiceImpl implements RuleTesterServiceIntf {

    RuleTesterResultsBOIntf objTesterResultsBOIntf;

    public RuleTesterResultsServiceImpl(final RuleTesterResultsBOIntf objTesterResultsBOIntf) {
        this.objTesterResultsBOIntf = objTesterResultsBOIntf;
    }

    /**
     * @param strTrackingId
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of TrackingIds for
     *               ajax populate
     */

    /**
     * @param RuleTesterVO
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the tracking Detail for
     *               values given in UI(filtered Options)
     */

    @Override
    public List<RuleTesterVO> getRuleTesterTrackings(final RuleTesterVO RuleTesterVO) throws RMDServiceException {
        List<RuleTesterVO> arlResults = null;
        try {
            arlResults = objTesterResultsBOIntf.getRuleTesterTrackings(RuleTesterVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }

        return arlResults;

    }

    /**
     * @param strRuleTitle
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of RuleTitle for
     *               ajax populate
     */

    @Override
    public List<RuleTesterVO> getRuleTitles(final String strRuleTitle) throws RMDServiceException {
        List<RuleTesterVO> arlRuleTitle = null;
        try {
            arlRuleTitle = objTesterResultsBOIntf.getRuleTitles(strRuleTitle);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlRuleTitle;

    }

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of ElementVO
     * @throws KEPServiceException
     */
    @Override
    public List<ElementVO> getLookUPDetails(final String strListName, final String strLanguage)
            throws RMDServiceException {
        // TODO Auto-generated method stub
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = objTesterResultsBOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlStatus;
    }

    /**
     * @param strCreatedBy
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of CreatedBy for
     *               ajax populate
     */

    @Override
    public List<RuleTesterVO> getCreators(final String strCreatedBy) throws RMDServiceException {
        List<RuleTesterVO> arlCreatedBy = null;
        try {
            arlCreatedBy = objTesterResultsBOIntf.getCreators(strCreatedBy);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlCreatedBy;

    }

    /**
     * @param strTrackingId
     * @return List<TesterDetailsVO>
     * @Description: This method is used for getting the tester criteria details
     *               for the particular TrackingId
     */

    @Override
    public List<TesterDetailsVO> getTrackingDetails(final String strTrackingId) throws RMDServiceException {
        List<TesterDetailsVO> arlSummaryDetails = null;
        try {
            arlSummaryDetails = objTesterResultsBOIntf.getTrackingDetails(strTrackingId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlSummaryDetails;

    }

    /**
     * @param strPattern
     *            ,strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the PatternDetails for
     *               particular TrackingId
     */

    @Override
    public List<TesterResultVO> getPatternDetails(final String strPattern, final String strTrackingId)
            throws RMDServiceException {
        List<TesterResultVO> arlPatternDetails = null;
        try {
            arlPatternDetails = objTesterResultsBOIntf.getPatternDetails(strPattern, strTrackingId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }
        return arlPatternDetails;

    }

    /**
     * @param rtRequestVO
     * @return Integer
     * @Description: This method is used to get the tracking id of the saved
     *               Rule tester record
     */
    @Override
    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDServiceException {
        Integer trackingId = 0;
        try {
            trackingId = objTesterResultsBOIntf.createRuleTesterRequest(rtRequestVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, KEPCommonConstants.ENGLISH_LANGUAGE);
        }
        return trackingId;
    }

    @Override
    public List<String> getRoadNumbers(final String model, final String customer) throws RMDServiceException {
        List<String> roadNumbers = null;
        try {
            roadNumbers = objTesterResultsBOIntf.getRoadNumbers(model, customer);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return roadNumbers;
    }

    @Override
    public List<String> getAssetId(final String model, final String customer, String roadNo)
            throws RMDServiceException {
        List<String> assetId = null;
        try {
            assetId = objTesterResultsBOIntf.getAssetId(model, customer, roadNo);
        } catch (RMDDAOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return assetId;
    }

    @Override
    public List<String> getClearingLogicId(String ruleId) throws RMDServiceException {
        List<String> clearingLogicIds = null;
        try {
            clearingLogicIds = objTesterResultsBOIntf.getClearingLogicId(ruleId);
        } catch (RMDDAOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return clearingLogicIds;
    }
}
