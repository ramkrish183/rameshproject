/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesServiceImpl.java
 * Description : Service Implementation for Add Notes
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.bo.intf.AddNotesBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.AddNotesServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author : iGATE Global Solutions
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call the AddNotesBOImpl methods
 * @History :
 ******************************************************************************/
public class AddNotesServiceImpl implements Serializable, AddNotesServiceIntf {

    private static final long serialVersionUID = 1L;
    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(AddNotesServiceImpl.class);
    private AddNotesBOIntf objAddNotesBOIF;

    /**
     * @param objAddNotesBOIF
     */
    public AddNotesServiceImpl(AddNotesBOIntf objAddNotesBOIF) {
        this.objAddNotesBOIF = objAddNotesBOIF;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.NotesServiceIntf
     * #initalLoad(com.ge.trans.rmd.services.cases.service.valueobjects.
     * AddNotesServiceVO) This method is used to call the initalLoad method in
     * AddNotesBOImpl
     */
    @Override
    public void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDServiceException {
        try {
            objAddNotesBOIF.initalLoad(addnotesServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method initalLoad()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, addnotesServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.NotesServiceIntf
     * #saveNotes(com.ge.trans.rmd.services.cases.service.valueobjects.
     * AddNotesServiceVO) This method is used to call the saveNotes method in
     * AddNotesBOImpl
     */
    @Override
    public void saveNotes(AddNotesServiceVO notesServiceVO) throws RMDServiceException {
        try {
            objAddNotesBOIF.saveNotes(notesServiceVO);
        } catch (RMDDAOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method saveNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, notesServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.NotesServiceIntf
     * #overwriteNotes(com.ge.trans.rmd.services.cases.service.valueobjects.
     * AddNotesServiceVO) This method is used to call the overwrieNotes method
     * in AddNotesBOImpl
     */
    @Override
    public void overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDServiceException {
        try {
            objAddNotesBOIF.overwriteNotes(notesServiceVO);
        } catch (RMDDAOException ex) {
            LOG.error("Unexceptected Error in Service method overwriteNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method overwriteNotes()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, notesServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.service.intf.NotesServiceIntf
     * #deleteFleetRemark(com.ge.trans.rmd.services.cases.service.valueobjects.
     * AddNotesServiceVO) This method is used to call the deleteFleetRemark
     * method in AddNotesBOImpl
     */
    @Override
    public boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDServiceException {
        boolean isRemoved = false;
        try {
            isRemoved = objAddNotesBOIF.deleteFleetRemark(notesServiceVO);
        } catch (RMDDAOException ex) {
            LOG.error("Unexceptected Error in Service method deleteFleetRemark()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            LOG.error("Unexceptected Error in Service method deleteFleetRemark()", ex);
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, notesServiceVO.getStrLanguage());
        }
        return isRemoved;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

}
