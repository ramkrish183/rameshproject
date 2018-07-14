/**
 * ============================================================
 * File : OpenRxDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */

package com.ge.trans.eoa.services.tools.rx.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface OpenRxDAOIntf {

    /**
     * @param objOpenRxServiceVO
     * @return
     * @throws RMDDAOException
     */
    List fetchOpenRxs(OpenRxServiceVO objOpenRxServiceVO) throws RMDDAOException;

}
