package com.ge.trans.eoa.services.tools.rulemgmt.dao.intf;

/**
 * ============================================================
 * File : ManualRuleDAOIntf.java
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
import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
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
 * @Description :This method is the interface for ManualRuleDaoImpl
 * @History :
 ******************************************************************************/

public interface ManualRunDAOIntf {
    /**
     * @Author:
     * @param objManualRuleServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getAssetList(ManualRunServiceVO objManualRuleServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objManualJdpadServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int runJdpad(ManualRunServiceVO objManualJdpadServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objJdpadResultServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */

    List searchManualRun(JdpadResultServiceVO objJdpadResultServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param strLanguage,strTrackingId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getTrackIdsList(String strTrackingId, String strLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    FaultDataDetailsServiceVO getFaultsForManualRun(
            RuleTesterSeachCriteriaServiceVO objRuleTesterSeachCriteriaServiceVO);
}
