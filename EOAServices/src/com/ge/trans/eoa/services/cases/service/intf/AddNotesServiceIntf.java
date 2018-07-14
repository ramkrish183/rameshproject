/**
 * ============================================================
 * Classification: GE Confidential
 * File : AddNotesServiceIntf.java
 * Description : Service Interface for Add Notes
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
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
package com.ge.trans.eoa.services.cases.service.intf;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.cases.service.valueobjects.AddNotesServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Interface for Add Notes
 * @History :
 ******************************************************************************/
public interface AddNotesServiceIntf {

    /**
     * @param addnotesServiceVO
     * @throws RMDServiceException
     */
    void initalLoad(AddNotesServiceVO addnotesServiceVO) throws RMDServiceException;

    /**
     * @param notesServiceVO
     * @throws RMDServiceException
     */
    void saveNotes(AddNotesServiceVO notesServiceVO) throws RMDServiceException;

    /**
     * @param notesServiceVO
     * @throws RMDServiceException
     */
    void overwriteNotes(AddNotesServiceVO notesServiceVO) throws RMDServiceException;

    /**
     * @param notesServiceVO
     * @throws RMDServiceException
     */
    boolean deleteFleetRemark(AddNotesServiceVO notesServiceVO) throws RMDServiceException;

}
