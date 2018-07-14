package com.ge.trans.eoa.services.kep.ruletester.bo.impl;

/**
 * ============================================================
 * File : RuleTesterResultsBOImpl.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.ruletester.bo.impl
 * Author : iGATE Patni.
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

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.kep.ruletester.bo.intf.RuleTesterResultsBOIntf;
import com.ge.trans.eoa.services.kep.ruletester.dao.intf.RuleTesterResultsDAOIntf;
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
 * @Description :This class is the implementation class for RuleTesterResults BO
 *              layer
 * @History :
 ******************************************************************************/
public class RuleTesterResultsBOImpl implements RuleTesterResultsBOIntf {

    RuleTesterResultsDAOIntf objTesterResultsDAOIntf;

    public RuleTesterResultsBOImpl(RuleTesterResultsDAOIntf objTesterResultsDAOIntf) {
        this.objTesterResultsDAOIntf = objTesterResultsDAOIntf;
    }

    /**
     * @param strTrackingId
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of TrackingIds for
     *               ajax populate
     */

    @Override
    public List<RuleTesterVO> getRuleTesterTrackings(final RuleTesterVO objRuleTesterVO) throws RMDBOException {
        List<RuleTesterVO> arlResults = null;
        try {
            arlResults = objTesterResultsDAOIntf.getRuleTesterTrackings(objRuleTesterVO);
        } catch (RMDDAOException e) {
            throw e;
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
    public List<RuleTesterVO> getRuleTitles(final String strRuleTitle) throws RMDBOException {
        List<RuleTesterVO> arlRuleTitle = null;
        try {
            arlRuleTitle = objTesterResultsDAOIntf.getRuleTitles(strRuleTitle);
        } catch (RMDDAOException e) {
            throw e;

        }
        return arlRuleTitle;

    }

    /**
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of RuleType for
     *               ajax populate
     */

    @Override
    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDBOException {
        List<ElementVO> arlStatus = null;
        try {
            arlStatus = objTesterResultsDAOIntf.getLookUPDetails(strListName, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
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
    public List<RuleTesterVO> getCreators(final String strCreatedBy) throws RMDBOException {
        List<RuleTesterVO> arlCreatedBy = null;
        try {
            arlCreatedBy = objTesterResultsDAOIntf.getCreators(strCreatedBy);
        } catch (RMDDAOException e) {
            throw e;
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
    public List<TesterDetailsVO> getTrackingDetails(final String strTrackingId) throws RMDBOException {
        List<TesterDetailsVO> arlSummaryDetails = null;
        try {
            arlSummaryDetails = objTesterResultsDAOIntf.getTrackingDetails(strTrackingId);
        } catch (RMDDAOException e) {

            throw e;
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
            throws RMDBOException {
        List<TesterResultVO> arlPatternDetails = null;
        try {
            arlPatternDetails = objTesterResultsDAOIntf.getPatternDetails(strPattern, strTrackingId);
        } catch (RMDDAOException e) {
            throw e;
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
    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDBOException {
        Integer trackingId = 0;
        try {
            trackingId = objTesterResultsDAOIntf.createRuleTesterRequest(rtRequestVO);
        } catch (RMDDAOException e) {
            throw e;
        }

        return trackingId;
    }

    @Override
    public List<String> getRoadNumbers(final String model, final String customer) throws RMDBOException {
        List<String> roadNumbers = null;
        try {
            roadNumbers = objTesterResultsDAOIntf.getRoadNumbers(model, customer);
        } catch (RMDDAOException e) {
            throw e;
        }
        return roadNumbers;
    }

    @Override
    public List<String> getAssetId(final String model, final String customer, final String roadNo) {
        List<String> assetId = null;
        try {
            assetId = objTesterResultsDAOIntf.getAssetId(model, customer, roadNo);
        } catch (RMDDAOException e) {
            throw e;
        }
        return assetId;
    }

    @Override
    public List<String> getClearingLogicId(String ruleId) {
        List<String> clearingLogicIds = null;
        try {
            clearingLogicIds = objTesterResultsDAOIntf.getclearingLogicId(ruleId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return clearingLogicIds;
    }
}
