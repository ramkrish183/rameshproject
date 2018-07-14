/**
 * ============================================================
 * File : CommonBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.bo.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :May 11, 2012
 * History
 * Modified By : iGATE
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;
import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.exception.RMDBOException;

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
public interface CommonBOIntf {

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List getSearchQueueMenu(String strLanguage, String strUserLanguage) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    @SuppressWarnings("rawtypes")
    List getFunctions(String strLanguage, String strUserLanguage) throws RMDBOException;

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    public List<CustLookupVO> getSDCustLookup() throws RMDBOException;
    
    /**
     * 
     * @param exceptionDetailsVO
     */
    public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO);
    
    public String getAssetPanelParameters() throws RMDBOException;
}
