/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesServiceImpl.java
 * Description : Service Implementation for Find Notes
 *
 * Package : com.ge.trans.rmd.services.cases.service.impl
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

import java.io.Serializable;

import com.ge.trans.eoa.services.cases.bo.intf.FindNotesEoaBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.FindNotesEoaServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call the FindNotesBOImpl methods
 * @History :
 ******************************************************************************/
public class FindNotesEoaServiceImpl implements Serializable, FindNotesEoaServiceIntf {

    private static final long serialVersionUID = 1L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(FindNotesEoaServiceImpl.class);
    private FindNotesEoaBOIntf objFindNotesBOIF;

    /**
     * @param objNotesBOIF
     */
    public FindNotesEoaServiceImpl(FindNotesEoaBOIntf objFindNotesBOIF) {
        this.objFindNotesBOIF = objFindNotesBOIF;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.FindNotesServiceIntf#
     * getNotes(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FindNotesServiceVO) This method is used to call the getNotes method in
     * FindNotesServiceImpl
     */
    @Override
    public void getNotes(final FindNotesServiceVO findNotesServiceVO) throws RMDServiceException {
        LOG.debug("Begin getNotes method of FindNotesServiceImpl");
        try {
            objFindNotesBOIF.getNotes(findNotesServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, findNotesServiceVO.getStrLanguage());
        }
        LOG.debug("End getNotes method of FindNotesServiceImpl");
    }

    /**
     * @return the objFindNotesBOIF
     */
    public FindNotesEoaBOIntf getObjFindNotesBOIF() {
        return objFindNotesBOIF;
    }

    /**
     * @param objFindNotesBOIF
     *            the objFindNotesBOIF to set
     */
    public void setObjFindNotesBOIF(final FindNotesEoaBOIntf objFindNotesBOIF) {
        this.objFindNotesBOIF = objFindNotesBOIF;
    }

}
