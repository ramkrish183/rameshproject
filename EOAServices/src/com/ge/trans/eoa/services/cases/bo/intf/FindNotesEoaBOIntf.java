/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesBOIntf.java
 * Description : BO Interface for Find Notes
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

import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : BO Interface for Find Notes
 * @History :
 ******************************************************************************/
public interface FindNotesEoaBOIntf {

    /**
     * @param findNotesServiceVO
     * @throws RMDBOException
     */
    void getNotes(FindNotesServiceVO findNotesServiceVO) throws RMDBOException;

}
