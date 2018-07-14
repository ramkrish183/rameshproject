/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseRxMgmtServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.intf;
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 4, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface CaseRxMgmtServiceIntf {

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description: Function to add RX to the case
     */
    void addRXToCase(RecomDelvInfoServiceVO caseRXVO) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description: Function to add RX to the case
     */
    List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDServiceException;
}