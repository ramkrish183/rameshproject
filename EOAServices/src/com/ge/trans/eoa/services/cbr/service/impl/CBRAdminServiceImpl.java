package com.ge.trans.eoa.services.cbr.service.impl;

/**
 * ============================================================
 * File : CBRAdminServiceImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.cbr.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on :June 22,2016
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 * Classification: GE Confidential
 * ============================================================
 */
import java.util.List;

import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.cbr.bo.intf.CBRAdminBOIntf;
import com.ge.trans.eoa.services.cbr.service.intf.CBRAdminServiceIntf;
import com.ge.trans.eoa.services.cbr.service.valueobjects.CBRAdminVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 22,2016
 * @Date Modified : June 22,2016
 * @Modified By :
 * @Contact :
 * @Description :This is service implementation layer for CBR related
 *              functionalities
 * @History :
 ******************************************************************************/
public class CBRAdminServiceImpl implements CBRAdminServiceIntf {

    private CBRAdminBOIntf objCBRBOIntf;

    public CBRAdminServiceImpl(CBRAdminBOIntf objCBRBOIntf) {
        this.objCBRBOIntf = objCBRBOIntf;
    }

    /**
     * This method is used for fetching Rx Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDServiceException
     */
    @Override
    public List<CBRAdminVO> getRxMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDServiceException {

        List<CBRAdminVO> arlCBRCasesVO = null;
        try {
            arlCBRCasesVO = objCBRBOIntf.getRxMaintenanceDetails(objCBRCasesVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objCBRCasesVO.getStrLanguage());
        }
        return arlCBRCasesVO;

    }

    /**
     * This method is used for fetching Case Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDServiceException
     */
    @Override
    public List<CBRAdminVO> getCaseMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDServiceException {

        List<CBRAdminVO> arlCBRCasesVO = null;
        try {
            arlCBRCasesVO = objCBRBOIntf.getCaseMaintenanceDetails(objCBRCasesVO);

        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objCBRCasesVO.getStrLanguage());
        }
        return arlCBRCasesVO;

    }

    /**
     * This method is used for Activate/Deactivate cases
     * 
     * @param objid
     * @param status
     * @return
     * @throws RMDServiceException
     */
    @Override
    public String caseActivateDeactivate(String objid, String status) throws RMDServiceException {
        String strActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            strActivateDeactivate = objCBRBOIntf.caseActivateDeactivate(objid, status);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return strActivateDeactivate;
    }

    /**
     * This method is used for Activate/Deactivate Rx
     * 
     * @param objid
     * @param status
     * @param rxComment
     * @param strUserName
     * @return
     * @throws RMDServiceException
     */
    @Override
    public String rxActivateDeactivate(String objid, String status, String strUserName) throws RMDServiceException {
        String strActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            strActivateDeactivate = objCBRBOIntf.rxActivateDeactivate(objid, status, strUserName);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return strActivateDeactivate;
    }

}
