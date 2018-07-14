/**
 * ============================================================
 * File : OpenRxBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.bo.impl
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
package com.ge.trans.eoa.services.tools.rx.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.tools.rx.bo.intf.OpenRxBOIntf;
import com.ge.trans.eoa.services.tools.rx.dao.intf.OpenRxDAOIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;

/*******************************************************************************
 * @Author : Viji
 * @Version : 1.0
 * @Date Created: May 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class OpenRxBOImpl implements OpenRxBOIntf {

    private OpenRxDAOIntf objOpenRxDAOIntf;

    public OpenRxBOImpl(OpenRxDAOIntf objOpenRxDAOIntf) {
        super();
        this.objOpenRxDAOIntf = objOpenRxDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.tools.rx.bo.intf.OpenRxBOIntf#fetchOpenRxs(
     * com.ge.trans.rmd.services.tools.rx.service.valueobjects.OpenRxServiceVO)
     */
    @Override
    public List fetchOpenRxs(OpenRxServiceVO objOpenRxServiceVO) throws RMDBOException {
        List arlOpenRx;
        try {
            arlOpenRx = objOpenRxDAOIntf.fetchOpenRxs(objOpenRxServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlOpenRx;
    }
}
