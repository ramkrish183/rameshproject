/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
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
package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;

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
public interface WorkQueueBOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description: This method is used to get the list of cases which are Open
     */
    List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDBOException;

}
