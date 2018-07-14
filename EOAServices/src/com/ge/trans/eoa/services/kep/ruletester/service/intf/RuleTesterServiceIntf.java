package com.ge.trans.eoa.services.kep.ruletester.service.intf;

/**
 * ============================================================
 * File : RuleTesterServiceIntf.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.ruletester.service.intf
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
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RTRequestVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterDetailsVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.TesterResultVO;
import com.ge.trans.eoa.services.kep.ruletester.service.valueobjects.RuleTesterVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :This class is the interface of Rule TesterResults service layer
 * @History :
 ******************************************************************************/
public interface RuleTesterServiceIntf {

    /**
     * @param RuleTesterVO
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the tracking Detail for
     *               values given in UI(filtered Options)
     */
    List<RuleTesterVO> getRuleTesterTrackings(RuleTesterVO tester) throws RMDServiceException;

    /**
     * @param strRuleTitle
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of RuleTitle for
     *               ajax populate
     */
    List<RuleTesterVO> getRuleTitles(String strRuleTitle) throws RMDServiceException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strLanguage
     * @return list of BaseVO
     * @throws RMDServiceException
     */
    public List<ElementVO> getLookUPDetails(String listName, String strLanguage) throws RMDServiceException;

    /**
     * @param strCreatedBy
     * @return List<RuleTesterVO>
     * @Description: This method is used for getting the list of CreatedBy for
     *               ajax populate
     */
    List<RuleTesterVO> getCreators(String strCreatedBy) throws RMDServiceException;

    /**
     * @param strTrackingId
     * @return List<TesterDetailsVO>
     * @Description: This method is used for getting the tester criteria details
     *               for the particular TrackingId
     */
    List<TesterDetailsVO> getTrackingDetails(String strTrackingId) throws RMDServiceException;

    /**
     * @param strPattern
     *            ,strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the PatternDetails for
     *               particular TrackingId
     */
    List<TesterResultVO> getPatternDetails(String strPattern, String strTrackingId) throws RMDServiceException;

    /**
     * @param rtRequestVO
     * @return Integer
     * @Description: This method is used to get the tracking id of the saved
     *               Rule tester record
     */
    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDServiceException;

    List<String> getRoadNumbers(String model, String customer) throws RMDServiceException;

    List<String> getAssetId(String model, String customer, String roadNo) throws RMDServiceException;

    List<String> getClearingLogicId(String ruleId) throws RMDServiceException;

}
