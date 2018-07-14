/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 31, 2009
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.cases.bo.intf.WorkQueueBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.WorkQueueDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;

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
public class WorkQueueBOImpl implements WorkQueueBOIntf {

    public static final RMDLogger LOG = RMDLogger.getLogger(WorkQueueBOImpl.class);
    private WorkQueueDAOIntf objWorkQueueDAOIntf;

    /**
     * @param objCaseDAOIntf
     */
    public WorkQueueBOImpl(WorkQueueDAOIntf objWorkQueueDAOIntf) {
        this.objWorkQueueDAOIntf = objWorkQueueDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.cases.bo.intf.CaseBOIntf#
     * getDynamicWorkQueueCasesSummary() This Method is used for call the
     * getDynamicWorkQueueCasesSummary method in CaseDAOImpl
     */
    @Override
    public List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDBOException {
        List arlWQCases;
        try {
            arlWQCases = objWorkQueueDAOIntf.getDynamicWorkQueueCasesSummary(objOpenCaseServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlWQCases;
    }
}
