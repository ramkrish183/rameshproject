/**
 * ============================================================
 * Classification: GE Confidential
 * File : WorkQueueServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.impl
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
package com.ge.trans.eoa.services.cases.service.impl;

import java.io.Serializable;
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.cases.bo.intf.WorkQueueBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.WorkQueueServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.OpenCaseServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
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
public class WorkQueueServiceImpl implements Serializable, WorkQueueServiceIntf {

    private static final long serialVersionUID = 154542L;
    private WorkQueueBOIntf objWorkQueueBOIntf;

    /**
     * @param objCaseBOIntf
     */
    public WorkQueueServiceImpl(WorkQueueBOIntf objWorkQueueBOIntf) {
        this.objWorkQueueBOIntf = objWorkQueueBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.CaseServiceIntf
     * #getDynamicWorkQueueCasesSummary()
     *//*
       * This Method is used for call the getDynamicWorkQueueCasesSummary method
       * in CaseBOImpl
       */
    @Override
    public List getDynamicWorkQueueCasesSummary(OpenCaseServiceVO objOpenCaseServiceVO) throws RMDServiceException {
        List arlWQCaseInfo = null;
        try {
            arlWQCaseInfo = objWorkQueueBOIntf.getDynamicWorkQueueCasesSummary(objOpenCaseServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objOpenCaseServiceVO.getStrLanguage());
        }
        return arlWQCaseInfo;
    }
}
