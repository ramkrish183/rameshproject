/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesBOIntf.java
 * Description : BO Interface for Add Notes
 *
 * Package : com.ge.trans.rmd.services.cases.bo.intf
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
package com.ge.trans.eoa.services.cases.bo.intf;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Interface for Add Notes
 * @History :
 ******************************************************************************/
public interface AddNotesBOIntf {

    /**
     * @param addnotesServiceVO
     * @throws RMDBOException
     */
    void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDBOException;

    /**
     * @param notesServiceVO
     * @throws RMDBOException
     */
    void saveNotes(AddNotesServiceVO notesServiceVO) throws RMDBOException;

    /**
     * @param notesServiceVO
     * @throws RMDBOException
     */
    void overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDBOException;

    /**
     * @param notesServiceVO
     * @throws RMDBOException
     */
    boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDBOException;
}
