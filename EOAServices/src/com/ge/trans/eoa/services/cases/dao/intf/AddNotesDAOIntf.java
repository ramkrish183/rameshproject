/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesDAOIntf.java
 * Description : DAO interface for Add Notes
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
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
package com.ge.trans.eoa.services.cases.dao.intf;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : DAO interface for Add Notes
 * @History :
 ******************************************************************************/
public interface AddNotesDAOIntf {

    /**
     * @param addnotesServiceVO
     * @throws RMDDAOException
     */
    void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDDAOException;

    /**
     * @param notesServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String saveNotes(AddNotesServiceVO notesServiceVO) throws RMDDAOException;

    /**
     * @param notesServiceVO
     * @return String
     * @throws RMDDAOException
     */
    String overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDDAOException;

    /**
     * @param notesServiceVO
     * @throws RMDDAOException
     */
    boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDDAOException;
}
