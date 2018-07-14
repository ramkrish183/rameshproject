/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesBOImpl.java
 * Description : BO Implementation for Add Notes
 *
 * Package : com.ge.trans.rmd.services.cases.bo.impl
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

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.services.cases.bo.intf.AddNotesBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.AddNotesDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.RxExecutionEoaDAOIntf;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call AddNotesDAOImpl methods
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class AddNotesBOImpl implements AddNotesBOIntf {

    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(AddNotesBOImpl.class);
    private AddNotesDAOIntf objAddNotesDAOIntf;
    RxExecutionEoaDAOIntf rxExecutionEoaDAOIntf;

    /**
     * @param objNotesDAOIntf
     */
    public AddNotesBOImpl(AddNotesDAOIntf objAddNotesDAOIntf, RxExecutionEoaDAOIntf rxExecutionEoaDAOIntf) {
        this.objAddNotesDAOIntf = objAddNotesDAOIntf;
        this.rxExecutionEoaDAOIntf = rxExecutionEoaDAOIntf;
    }

    /**
     * @return the objAddNotesDAOIntf
     */
    public AddNotesDAOIntf getObjAddNotesDAOIntf() {
        return objAddNotesDAOIntf;
    }

    /**
     * @param objAddNotesDAOIntf
     *            the objAddNotesDAOIntf to set
     */
    public void setObjAddNotesDAOIntf(AddNotesDAOIntf objAddNotesDAOIntf) {
        this.objAddNotesDAOIntf = objAddNotesDAOIntf;
    }

    /**
     * @return the objAddNotesDAOIntf
     */
    public RxExecutionEoaDAOIntf getRxExecutionEoaDAOIntf() {
        return rxExecutionEoaDAOIntf;
    }

    /**
     * @param objAddNotesDAOIntf
     *            the objAddNotesDAOIntf to set
     */
    public void setRxExecutionEoaDAOIntf(RxExecutionEoaDAOIntf rxExecutionEoaDAOIntf) {
        this.rxExecutionEoaDAOIntf = rxExecutionEoaDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#initalLoad(com.ge.
     * trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to call the initalLoad method in AddNotesDAOImpl
     */
    @Override
    public void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDBOException {
        try {
            objAddNotesDAOIntf.initalLoad(addnotesServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in AddNotesBOImpl initalLoad method", e);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#saveNotes(com.ge.
     * trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to call the saveNotes method in AddNotesDAOImpl
     */
    @Override
    public void saveNotes(AddNotesServiceVO notesServiceVO) throws RMDBOException {
        try {
            // Added

            String eoaUserId = rxExecutionEoaDAOIntf.getEOAUserId(notesServiceVO.getStrUserName(),
                    notesServiceVO.getStrLanguage());
            notesServiceVO.setEoaUserId(eoaUserId);
            // Added
            objAddNotesDAOIntf.saveNotes(notesServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in AddNotesBOImpl saveNotes method", e);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#overwriteNotes(com.ge
     * .trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to call the overwrieNotes method in AddNotesDAOImpl
     */
    @Override
    public void overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDBOException {
        try {
            objAddNotesDAOIntf.overwriteNotes(notesServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in AddNotesBOImpl overwriteNotes method", e);
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.bo.intf.NotesBOIntf#overwriteNotes(com.ge
     * .trans.rmd.services.cases.service.valueobjects.AddNotesServiceVO) This
     * method is used to call the deleteFleetRemark method in AddNotesDAOImpl
     */
    @Override
    public boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDBOException {
        try {
            return objAddNotesDAOIntf.deleteFleetRemark(notesServiceVO);
        } catch (RMDDAOException e) {
            LOG.error("Unexpected Error occured in AddNotesBOImpl deleteFleetRemark method", e);
            throw e;
        }
    }
}
