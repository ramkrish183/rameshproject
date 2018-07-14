/**
 * ============================================================
 * File : OpenRxServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 2, 2009
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 2, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface OpenRxServiceIntf {

    /**
     * @param objOpenRxServiceVO
     * @return
     * @throws RMDServiceException
     */
    List fetchOpenRxs(OpenRxServiceVO objOpenRxServiceVO) throws RMDServiceException;
}
