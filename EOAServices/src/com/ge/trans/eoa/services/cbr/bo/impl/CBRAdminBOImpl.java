package com.ge.trans.eoa.services.cbr.bo.impl;

/**
 * ============================================================
 * File : CBRAdminBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.cbr.bo.impl
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

import com.ge.trans.eoa.services.cbr.bo.intf.CBRAdminBOIntf;
import com.ge.trans.eoa.services.cbr.dao.intf.CBRAdminDAOIntf;
import com.ge.trans.eoa.services.cbr.service.valueobjects.CBRAdminVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 22,2016
 * @Date Modified : June 22,2016
 * @Modified By :
 * @Contact :
 * @Description :This is BO implementation layer for CBR related functionalities
 * @History :
 ******************************************************************************/
public class CBRAdminBOImpl implements CBRAdminBOIntf {
    private CBRAdminDAOIntf objCBRDAOIntf;

    public CBRAdminBOImpl(CBRAdminDAOIntf objCBRDAOIntf) {
        this.objCBRDAOIntf = objCBRDAOIntf;
    }

    /**
     * This method is used for fetching Rx Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDBOException
     */
    @Override
    public List<CBRAdminVO> getRxMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDBOException {

        List<CBRAdminVO> arlCBRCasesVO = null;
        try {
            arlCBRCasesVO = objCBRDAOIntf.getRxMaintenanceDetails(objCBRCasesVO);

        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCBRCasesVO;

    }

    /**
     * This method is used for fetching Case Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDBOException
     */
    @Override
    public List<CBRAdminVO> getCaseMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDBOException {

        List<CBRAdminVO> arlCBRCasesVO = null;
        try {
            arlCBRCasesVO = objCBRDAOIntf.getCaseMaintenanceDetails(objCBRCasesVO);

        } catch (RMDDAOException e) {
            throw e;
        }
        return arlCBRCasesVO;

    }

    /**
     * This method is used for Activate/Deactivate cases
     * 
     * @param objid
     * @param status
     * @return
     * @throws RMDBOException
     */
    @Override
    public String caseActivateDeactivate(String objid, String status) throws RMDBOException {
        String strActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            strActivateDeactivate = objCBRDAOIntf.caseActivateDeactivate(objid, status);

        } catch (RMDDAOException e) {
            throw e;
        }
        return strActivateDeactivate;
    }

    /**
     * This method is used for Activate/Deactivate Rx
     * 
     * @param objid
     * @param status
     * @param strUserName
     * @return
     * @throws RMDBOException
     */
    @Override
    public String rxActivateDeactivate(String objid, String status, String strUserName) throws RMDBOException {
        String strActivateDeactivate = RMDCommonConstants.FAILURE;
        try {
            strActivateDeactivate = objCBRDAOIntf.rxActivateDeactivate(objid, status, strUserName);

        } catch (RMDDAOException e) {
            throw e;
        }
        return strActivateDeactivate;
    }

}
