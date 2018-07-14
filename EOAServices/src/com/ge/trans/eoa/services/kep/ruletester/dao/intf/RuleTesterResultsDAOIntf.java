package com.ge.trans.eoa.services.kep.ruletester.dao.intf;

/**
 * ============================================================
 * File : RuleTesterResultsDAOIntf.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.tester.dao.intf
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

import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;
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
 * @Description :This class is the interface of Rule TesterResults DAO layer
 * @History :
 ******************************************************************************/
public interface RuleTesterResultsDAOIntf {

    /*	*//**
           * @param strTrackingId
           * @return List<RuleTesterVO>
           * @Description: This method is used for getting the list of
           *               TrackingIds for ajax populate
           */
    /*
     * List<RuleTesterVO> getTrackingIDs(String strTrackingId) throws
     * RMDDAOException;
     *//**
       * @param strRuleId
       * @return List<RuleTesterVO>
       * @Description: This method is used for getting the list of ruleIds for
       *               ajax populate
       *//*
         * List<RuleTesterVO> getRuleIds(String strRuleId) throws
         * RMDDAOException;
         */

    /**
     * @param strRuleTitle
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of RuleTitle for
     *               ajax populate
     */
    List<RuleTesterVO> getRuleTitles(String strRuleTitle) throws RMDDAOException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strlanguage
     * @return list of BaseVO
     * @throws RMDDAOException
     */

    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDDAOException;

    /**
     * @param strCreatedBy
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of CreatedBy for
     *               ajax populate
     */
    List<RuleTesterVO> getCreators(String strCreatedBy) throws RMDDAOException;

    /**
     * @param RuleTesterVO
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the tracking Detail for
     *               values given in UI(filtered Options)
     */
    List<RuleTesterVO> getRuleTesterTrackings(RuleTesterVO objTesterVO) throws RMDDAOException;

    /**
     * @param strTrackingId
     * @return List<TesterDetailsVO>
     * @Description: This method is used for getting the tester criteria details
     *               for the particular TrackingId
     */
    List<TesterDetailsVO> getTrackingDetails(String strTrackingId) throws RMDDAOException;

    /**
     * @param strPattern
     *            ,strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the PatternDetails for
     *               particular TrackingId
     */
    List<TesterResultVO> getPatternDetails(String strPattern, String strTrackingId) throws RMDDAOException;

    /**
     * @param rtRequestVO
     * @return Integer
     * @Description: This method is used to get the tracking id of the saved
     *               Rule tester record
     */

    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDDAOException;

    List<String> getRoadNumbers(String model, String customer);

    List<String> getAssetId(String model, String customer, String roadNo);

    List<String> getclearingLogicId(String ruleId);

}
