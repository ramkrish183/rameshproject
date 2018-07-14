/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseRxMgmtBOImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.tools.rx.bo.impl
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
package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.bo.intf.CaseRxMgmtBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.CaseRxMgmtDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 9, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CaseRxMgmtBOImpl implements CaseRxMgmtBOIntf {

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CaseRxMgmtBOImpl.class);
    private CaseRxMgmtDAOIntf objCaseRxMgmtDAOIntf;

    /**
     * @param objCaseRxMgmtDAOIntf
     * @param objCaseDAOIntf
     */
    public CaseRxMgmtBOImpl(CaseRxMgmtDAOIntf objCaseRxMgmtDAOIntf) {
        super();
        this.objCaseRxMgmtDAOIntf = objCaseRxMgmtDAOIntf;
    }

    /**
     * @return the objCaseRxMgmtDAOIntf
     */
    public CaseRxMgmtDAOIntf getObjCaseRxMgmtDAOIntf() {
        return objCaseRxMgmtDAOIntf;
    }

    /**
     * @param objCaseRxMgmtDAOIntf
     *            the objCaseRxMgmtDAOIntf to set
     */
    public void setObjCaseRxMgmtDAOIntf(CaseRxMgmtDAOIntf objCaseRxMgmtDAOIntf) {
        this.objCaseRxMgmtDAOIntf = objCaseRxMgmtDAOIntf;
    }

    /**
     * This method is used to add an RX for a particular case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDBOException
     */
    @Override
    public void addRXToCase(RecomDelvInfoServiceVO caseRXVO) throws RMDBOException {
        // TODO Auto-generated method stub
        try {
            objCaseRxMgmtDAOIntf.addRXTocase(caseRXVO);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
    }

    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDBOException
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDBOException {
        // TODO Auto-generated method stub
        try {
            return objCaseRxMgmtDAOIntf.getCaseRXDetails(caseId);
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
    }
}
