/**
 * ============================================================
 * File : CommonBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.bo.impl
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
package com.ge.trans.eoa.services.common.bo.impl;

import java.util.List;

import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.eoa.services.common.bo.intf.CommonBOIntf;
import com.ge.trans.eoa.services.common.dao.intf.CommonDAOIntf;
import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 11, 2012
 * @Date Modified :May 11, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CommonBOImpl implements CommonBOIntf {

    public static final RMDLogger LOG = RMDLogger.getLogger(CommonBOImpl.class);
    private CommonDAOIntf objCommonDAOIntf;

    public CommonBOImpl(final CommonDAOIntf objCommonDAOIntf) {
        super();
        this.objCommonDAOIntf = objCommonDAOIntf;

    }

    /**
     * @return the objCaseDAOIntf
     */
    public CommonDAOIntf getobjCommonDAOIntf() {
        return objCommonDAOIntf;
    }

    /**
     * @param objCaseDAOIntf
     *            the objCaseDAOIntf to set
     */
    public void setobjCommonDAOIntf(final CommonDAOIntf objCommonDAOIntf) {
        this.objCommonDAOIntf = objCommonDAOIntf;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.common.bo.intf#getSearchQueueMenu()
     *//*
       * This Method is used for call the getSearchQueueMenu method in
       * CommonDAOImpl
       */

    @Override
    public List getSearchQueueMenu(final String strLanguage, final String strUserLanguage) throws RMDBOException {
        List arlSrchQueues;
        try {
            arlSrchQueues = objCommonDAOIntf.getSearchQueueMenu(strLanguage, strUserLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlSrchQueues;
    }

    /**
     * @Author:
     * @return
     * @throws RMDBOException
     * @Description:
     */
    @Override
    public List<ElementVO> getFunctions(final String strLanguage, final String strUserLanguage) throws RMDBOException {
        List<ElementVO> arlFunctions;
        try {
            arlFunctions = objCommonDAOIntf.getFunctions(strLanguage, strUserLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlFunctions;
    }

    /**
     * @Description This method is to get the look up value from customer look
     *              up table
     * @return List<CustLookupVO>
     * @throws RMDBOException
     */
    @Override
    public List<CustLookupVO> getSDCustLookup() throws RMDBOException {
        List<CustLookupVO> lookupValueList = null;
        try {
            lookupValueList = objCommonDAOIntf.getSDCustLookup();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return lookupValueList;
    }

	@Override
	public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO) {
		objCommonDAOIntf.saveExceptionDetails(exceptionDetailsVO);
	}
	
	@Override
	public String getAssetPanelParameters() throws RMDBOException {
        String assetPanelParams = null;
        try {
        	assetPanelParams = objCommonDAOIntf.getAssetPanelParameters();
        } catch (RMDDAOException ex) {
            throw new RMDBOException(ex.getErrorDetail(), ex);
        }
        return assetPanelParams;
    }

}
