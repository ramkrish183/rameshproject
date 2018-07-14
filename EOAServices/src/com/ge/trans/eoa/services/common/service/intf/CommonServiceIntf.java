/**
 * ============================================================
 * File : CommonServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.service.intf
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
package com.ge.trans.eoa.services.common.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;
import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created:May 11, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public interface CommonServiceIntf {

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    @SuppressWarnings("rawtypes")
    List getFunctions(String strLanguage, String strUserLanguage) throws RMDServiceException;

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    public List<CustLookupVO> getSDCustLookup() throws RMDServiceException;
    
    /**
     * 
     */
    public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO );
    
    public String getAssetPanelParameters() throws RMDServiceException;
}