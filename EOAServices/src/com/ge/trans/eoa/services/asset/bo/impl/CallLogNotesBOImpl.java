/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthChechRequestBOImpl.java
 * Description :
 * Package : com.ge.trans.rmd.services.asset.bo.impl
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
package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.CallLogNotesBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.CallLogNotesDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jan 21,2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class CallLogNotesBOImpl implements CallLogNotesBOIntf {

    private CallLogNotesDAOIntf objCallLogDAOIntf;

    /**
     * @param objCallLogDAOIntf
     */
    public CallLogNotesBOImpl(final CallLogNotesDAOIntf objCallLogDAOIntf) {
        super();
        this.objCallLogDAOIntf = objCallLogDAOIntf;
    }

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CallLogNotesBOImpl.class);

    /**
     * @param healthCheckSubmitEoaVO
     * @return List<ViewRespHistoryEoaVo>
     * @throws RMDBOException
     * @Description:This method returns the list of Request History
     */
    @Override
    public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDBOException {
        try {
            List<CallLogNotesVO> callLogNotesList = objCallLogDAOIntf.getCallLogNotes(callLogNotesVO);
            return callLogNotesList;
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in CallLogNotesBOImpl getCallLogNotes()", e);
            throw new RMDBOException(e.getErrorDetail(), e);
        } catch (Exception e) {
            LOG.error("CommonUtil : Exception in generateXML :", e);
            throw new RMDBOException(e.getMessage());
        }
    }

}
