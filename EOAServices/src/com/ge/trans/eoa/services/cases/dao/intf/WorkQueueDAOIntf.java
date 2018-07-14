/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May7,2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 7,2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface WorkQueueDAOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description: This method fetches the contents for the dynamic work queue
     *               page
     */
    List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDDAOException;

}
