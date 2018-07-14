/**
 * ============================================================
 * Classification: GE Confidential
 * File : CommonDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.dao.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 11, 2012
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;
import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 11, 2012
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface CommonDAOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    @SuppressWarnings("rawtypes")
    List<ElementVO> getFunctions(String strLanguage, String strUserLanguage) throws RMDDAOException;

    /**
     * @Author:
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    public List<CustLookupVO> getSDCustLookup() throws RMDDAOException;
    
    /**
     * 
     * @param exceptionDetailsVO
     */
    public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO);
    
    public String getAssetPanelParameters();
}
