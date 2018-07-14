/**
 * ============================================================
 * Classification: GE Confidential
 * File : FindNotesServiceIntf.java
 * Description : Service Interface for Find Notes
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

import com.ge.trans.eoa.services.cases.service.valueobjects.FindNotesServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 31, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Service Interface for Find Notes
 * @History :
 ******************************************************************************/
public interface FindNotesEoaServiceIntf {

    /**
     * @param findNotesServiceVO
     * @throws RMDServiceException
     */
    void getNotes(FindNotesServiceVO findNotesServiceVO) throws RMDServiceException;

}
