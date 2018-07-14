/**
 * ============================================================
 * File : CommonServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.common.service.impl
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
package com.ge.trans.eoa.services.common.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.valueobjects.CustLookupVO;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.common.bo.intf.CommonBOIntf;
import com.ge.trans.eoa.services.common.valueobjects.ExceptionDetailsVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: May 11, 2012
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class CommonServiceImpl
        implements Serializable, com.ge.trans.eoa.services.common.service.intf.CommonServiceIntf {

    private static final long serialVersionUID = 154542L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(CommonServiceImpl.class);
    private CommonBOIntf objCommonBOIntf;

    @SuppressWarnings("unused")
    /**
     * @param objCaseBOIntf
     */
    public CommonServiceImpl(final CommonBOIntf objCommonBOIntf) {
        this.objCommonBOIntf = objCommonBOIntf;
    }

    /**
     * @param objCaseBOIntf
     */
    public CommonServiceImpl(CommonBOIntf objCommonBOIntf,
            com.ge.trans.eoa.services.common.service.intf.CommonServiceIntf commonAPIServiceIntf) {
        this.objCommonBOIntf = objCommonBOIntf;

    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.common.service.intf.CommonServiceIntf #
     * getSearchQueueMenu()
     *//*
       * This Method is used for call the getSearchQueueMenu method in
       * FindCaseBOImpl
       */

    @Override
    @SuppressWarnings("rawtypes")
    public List getSearchQueueMenu(final String strLanguage, final String strUserLanguage) throws RMDServiceException {
        List arlSrchQueues = new ArrayList();
        try {
            arlSrchQueues = objCommonBOIntf.getSearchQueueMenu(strLanguage, strUserLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlSrchQueues;
    }

    /**
     * @Author:
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    @Override
    @SuppressWarnings("rawtypes")
    public List<ElementVO> getFunctions(final String strLanguage, final String strUserLanguage)
            throws RMDServiceException {
        List<ElementVO> arlFunctions = new ArrayList();
        try {
            arlFunctions = objCommonBOIntf.getFunctions(strLanguage, strUserLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return arlFunctions;
    }

    /**
     * @Description This method is to get the look up value from customer look
     *              up table
     * @return List<CustLookupVO>
     * @throws RMDServiceException
     */
    @Override
    public List<CustLookupVO> getSDCustLookup() throws RMDServiceException {
        List<CustLookupVO> lookupValueList = null;
        try {
            lookupValueList = objCommonBOIntf.getSDCustLookup();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, "Exception occured");
        }
        return lookupValueList;
    }

	@Override
	public void saveExceptionDetails(ExceptionDetailsVO exceptionDetailsVO) {
		objCommonBOIntf.saveExceptionDetails(exceptionDetailsVO);
	}
	
	@Override
    public String getAssetPanelParameters() throws RMDServiceException {
        String assetPanelParams = null;
        try {
        	assetPanelParams = objCommonBOIntf.getAssetPanelParameters();
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return assetPanelParams;
    }

}
