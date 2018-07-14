package com.ge.trans.eoa.services.tools.rulemgmt.service.intf;

/**
 * ============================================================
 * File : ManualRuleServiceIntf.java
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
import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.fault.service.valueobjects.FaultDataDetailsServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.JdpadResultServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.ManualRunServiceVO;
import com.ge.trans.eoa.services.tools.rulemgmt.service.valueobjects.RuleTesterSeachCriteriaServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :This is the interface for manualruleserviceImpl class
 ******************************************************************************/
public interface ManualRunServiceIntf {

    /**
     * @Author:
     * @param objManualRuleServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used to get the list of assets
     */

    List getAssetList(ManualRunServiceVO objManualRuleServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objManualJdpadServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used for running the jdpad
     */

    int runJdpad(ManualRunServiceVO objManualJdpadServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objJdpadResultServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used to get the search results of manual
     *                   Jdpad
     */

    List searchManualRun(JdpadResultServiceVO objJdpadResultServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param strTrackingId,strLanguage
     * @return
     * @throws RMDServiceException
     * @Description:This method is used to get the list of TrackIds
     */

    List getTrackIdsList(String strTrackingId, String strLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:This method is used to get the Faults
     */
    FaultDataDetailsServiceVO getFaultsForManualRun(
            RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDServiceException;

}
