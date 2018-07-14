package com.ge.trans.eoa.services.kep.ruletester.bo.intf;

/**
 * ============================================================
 * File : RuleTesterResultsBOIntf.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.kep.services.ruletester.bo.intf
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
import com.ge.trans.rmd.exception.RMDBOException;
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
 * @Description :This class is the interface of Rule TesterResults BO layer
 * @History :
 ******************************************************************************/
public interface RuleTesterResultsBOIntf {

    /**
     * @param TesterVO
     * @return List<TesterVO>
     * @Description: This method is used for getting the tracking Detail for
     *               values given in UI(filtered Options)
     */
    List<RuleTesterVO> getRuleTesterTrackings(RuleTesterVO objRuleTesterVO) throws RMDBOException;

    /**
     * @param strRuleTitle
     * @return List<TesterVO>
     * @Description: This method is used for getting the list of RuleTitle for
     *               ajax populate
     */
    List<RuleTesterVO> getRuleTitles(String strRuleTitle) throws RMDBOException;

    /**
     * This method is used for loading the data details
     * 
     * @param strListName,strlanguage
     * @return list of BaseVO
     * @throws KEPBOException
     */

    public List<ElementVO> getLookUPDetails(String strListName, String strLanguage) throws RMDBOException;

    /**
     * @param strCreatedBy
     * @return List<TesterVO>
     * @Description: This method is used for getting the list of CreatedBy for
     *               ajax populate
     */
    List<RuleTesterVO> getCreators(String strCreatedBy) throws RMDBOException;

    /**
     * @param strTrackingId
     * @return List<TesterDetailsVO>
     * @Description: This method is used for getting the tester criteria details
     *               for the particular TrackingId
     */
    List<TesterDetailsVO> getTrackingDetails(String strTrackingId) throws RMDBOException;

    /**
     * @param strPattern
     *            ,strTrackingId
     * @return List<TesterResultVO>
     * @Description: This method is used for getting the PatternDetails for
     *               particular TrackingId
     */

    List<TesterResultVO> getPatternDetails(String strPattern, String strTrackingId) throws RMDBOException;

    /**
     * @param RTRequestVO
     * @return Integer
     * @Description: This method is used to get the tracking id of the saved
     *               Rule tester record
     */

    public Integer createRuleTesterRequest(RTRequestVO rtRequestVO) throws RMDBOException;

    List<String> getRoadNumbers(String model, String customer) throws RMDBOException;

    List<String> getAssetId(String model, String customer, String roadNo);

    List<String> getClearingLogicId(String ruleId);

}
