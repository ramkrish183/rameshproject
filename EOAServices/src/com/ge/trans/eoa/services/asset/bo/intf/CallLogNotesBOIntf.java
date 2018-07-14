/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.bo.intf
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
package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.rmd.exception.RMDBOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Apr 7, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface CallLogNotesBOIntf {
    /**
     * @Author:
     * @param
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDBOException;

}
