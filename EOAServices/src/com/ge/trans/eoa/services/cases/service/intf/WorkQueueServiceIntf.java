/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 7, 2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface WorkQueueServiceIntf {

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description: This method fetches the contents for the dynamic work queue
     *               page
     */
    List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDServiceException;

}