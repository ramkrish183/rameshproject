/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesBOImpl.java
 * Description : BO Implementation for Find Notes
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

import com.ge.trans.eoa.services.cases.bo.intf.FindNotesEoaBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.FindNotesEoaDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class is used to call FindNotesBOImpl methods
 * @History :
 ******************************************************************************/
public class FindNotesEoaBOImpl implements FindNotesEoaBOIntf {

    private FindNotesEoaDAOIntf objFindNotesDAOIntf;

    /**
     * @param objNotesDAOIntf
     */
    public FindNotesEoaBOImpl(final FindNotesEoaDAOIntf objFindNotesDAOIntf) {
        this.objFindNotesDAOIntf = objFindNotesDAOIntf;
    }

    /**
     * @return the objFindNotesDAOIntf
     */
    public FindNotesEoaDAOIntf getObjFindNotesDAOIntf() {
        return objFindNotesDAOIntf;
    }

    /**
     * @param objFindNotesDAOIntf
     *            the objFindNotesDAOIntf to set
     */
    public void setObjFindNotesDAOIntf(final FindNotesEoaDAOIntf objFindNotesDAOIntf) {
        this.objFindNotesDAOIntf = objFindNotesDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.cases.bo.intf.FindNotesBOIntf#
     * getNotes(com.ge.trans.rmd.services.cases.service.valueobjects.
     * FindNotesServiceVO) This method is used to call the getNotes method in
     * FindNotesBOImpl
     */
    @Override
    public void getNotes(final FindNotesServiceVO findNotesServiceVO) throws RMDBOException {
        try {
            objFindNotesDAOIntf.getNotes(findNotesServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

}
