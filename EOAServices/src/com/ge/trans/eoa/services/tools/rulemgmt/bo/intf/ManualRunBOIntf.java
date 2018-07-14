package com.ge.trans.eoa.services.tools.rulemgmt.bo.intf;

/**
 * ============================================================
 * File : ManualRuleBOIntf.java
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
import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
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
 * @Description : This class is the interface of ManualRuleBOImpl class
 * @History :
 ******************************************************************************/
public interface ManualRunBOIntf {
    /**
     * @Author:
     * @param objManualRuleServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getAssetList(ManualRunServiceVO objManualRuleServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objManualJdpadServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int runJdpad(ManualRunServiceVO objManualJdpadServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objJdpadResultServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List searchManualRun(JdpadResultServiceVO objJdpadResultServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param strLanguage,strTrackingId
     * @return
     * @throws RMDBOException
     * @Description:
     */

    List getTrackIdsList(String strTrackingId, String strLanguage) throws RMDBOException;

    /**
     * @Author:
     * @param objRuleTesterSeachCriteriaServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    FaultDataDetailsServiceVO getFaultsForManualRun(
            RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO) throws RMDBOException;
}
