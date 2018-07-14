/**
 * ============================================================
 * File : OpenRxServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.tools.rx.service.impl
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
package com.ge.trans.eoa.services.tools.rx.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.tools.rx.bo.intf.OpenRxBOIntf;
import com.ge.trans.eoa.services.tools.rx.service.intf.OpenRxServiceIntf;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.OpenRxServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

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
public class OpenRxServiceImpl implements OpenRxServiceIntf {

    private OpenRxBOIntf objOpenRxBOIntf;

    public OpenRxServiceImpl(OpenRxBOIntf objOpenRxBOIntf) {
        this.objOpenRxBOIntf = objOpenRxBOIntf;
    }

    /*
     * (non-Javadoc)
     * @seecom.ge.trans.rmd.services.tools.rx.service.intf.OpenRxServiceIntf#
     * fetchOpenRxs
     * (com.ge.trans.rmd.services.tools.rx.service.valueobjects.OpenRxServiceVO)
     */
    @Override
    public List fetchOpenRxs(final OpenRxServiceVO objOpenRxServiceVO) throws RMDServiceException {
        List arlOpenRx = null;
        try {
            arlOpenRx = objOpenRxBOIntf.fetchOpenRxs(objOpenRxServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objOpenRxServiceVO.getStrLanguage());
        }
        return arlOpenRx;
    }
}
