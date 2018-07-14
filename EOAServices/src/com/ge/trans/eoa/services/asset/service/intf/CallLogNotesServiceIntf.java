/**
 * ============================================================
 * Classification: GE Confidential
 * File : HealthCheckRequestServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.asset.service.intf;
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
package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.CallLogNotesVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jan,21 2016
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public interface CallLogNotesServiceIntf {

    /**
     * @Author:
     * @param
     * @return List<CallLogNotesVO>
     * @throws @Description:
     */
    public List<CallLogNotesVO> getCallLogNotes(CallLogNotesVO callLogNotesVO) throws RMDServiceException;

}
