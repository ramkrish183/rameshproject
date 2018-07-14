/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.impl;
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
package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.asset.bo.intf.CallLogNotesBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.CallLogNotesServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jan 21, 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CallLogNotesServiceImpl implements CallLogNotesServiceIntf {

    private CallLogNotesBOIntf objCallLogNotesBOIntf;

    /**
     * @param objHealthCheckRequestBOIntf
     */
    public CallLogNotesServiceImpl(CallLogNotesBOIntf objCallLogNotesBOIntf) {
        this.objCallLogNotesBOIntf = objCallLogNotesBOIntf;
    }

    /**
     * @param CallLogNotesVO
     * @return List<CallLogNotesVO>
     * @throws RMDServiceException
     * @Description:This method returns the list of healthCheck request
     */
    @Override
    public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDServiceException {
        List<CallLogNotesVO> callLogNotesLst = null;
        try {
            callLogNotesLst = objCallLogNotesBOIntf.getCallLogNotes(callLogNotesVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return callLogNotesLst;
    }
}
