/**
 * ============================================================
 * File : OpenRxBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 20, 2010
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.tools.rx.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
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
public interface OpenRxBOIntf {
    /**
     * @param objOpenRxServiceVO
     * @return
     * @throws RMDBOException
     */
    List fetchOpenRxs(OpenRxServiceVO objOpenRxServiceVO) throws RMDBOException;
}
