/**
 * ============================================================
 * Classification: GE Confidential
 * File : CaseRxMgmtServiceImpl.java
 * Description :
 *
 * Package :  com.ge.trans.rmd.services.tools.rx.service.impl
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
package com.ge.trans.eoa.services.cases.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.bo.intf.CaseRxMgmtBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.CaseRxMgmtServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.RecomDelvInfoServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

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
public class CaseRxMgmtServiceImpl implements CaseRxMgmtServiceIntf {

    private CaseRxMgmtBOIntf objCaseRxMgmtBOIntf;

    public CaseRxMgmtServiceImpl(CaseRxMgmtBOIntf objCaseRxMgmtBOIntf) {
        super();
        this.objCaseRxMgmtBOIntf = objCaseRxMgmtBOIntf;
    }

    /**
     * @return the objCaseRxMgmtBOIntf
     */
    public CaseRxMgmtBOIntf getObjCaseRxMgmtBOIntf() {
        return objCaseRxMgmtBOIntf;
    }

    /**
     * @param objCaseRxMgmtBOIntf
     *            the objCaseRxMgmtBOIntf to set
     */
    public void setObjCaseRxMgmtBOIntf(CaseRxMgmtBOIntf objCaseRxMgmtBOIntf) {
        this.objCaseRxMgmtBOIntf = objCaseRxMgmtBOIntf;
    }

    /**
     * This method is used to add an RX for a particular case
     * 
     * @param RecomDelvInfoServiceVO
     * @return void
     * @throws RMDServiceException
     */
    @Override
    public void addRXToCase(RecomDelvInfoServiceVO caseRXVO) throws RMDServiceException {
        // TODO Auto-generated method stub
        try {
            objCaseRxMgmtBOIntf.addRXToCase(caseRXVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
    }

    /**
     * This method is used to get the RXs which are added for a case
     * 
     * @param String
     *            caseId
     * @return void
     * @throws RMDServiceException
     */
    @Override
    public List<RecomDelvInfoServiceVO> getCaseRXDetails(String caseId) throws RMDServiceException {
        // TODO Auto-generated method stub
        List<RecomDelvInfoServiceVO> recomList = null;
        try {
            recomList = objCaseRxMgmtBOIntf.getCaseRXDetails(caseId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return recomList;
    }

}
