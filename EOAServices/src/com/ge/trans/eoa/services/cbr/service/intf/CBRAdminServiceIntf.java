package com.ge.trans.eoa.services.cbr.service.intf;

/**
 * ============================================================
 * File : CBRAdminServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.eoa.services.cbr.service.intf
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

import com.ge.trans.eoa.services.cbr.service.valueobjects.CBRAdminVO;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: June 22,2016
 * @Date Modified : June 22,2016
 * @Modified By :
 * @Contact :
 * @Description :This is service interface layer for CBR related functionalities
 * @History :
 ******************************************************************************/
public interface CBRAdminServiceIntf {

    /**
     * This method is used for fetching Rx Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDServiceException
     */
    public List<CBRAdminVO> getRxMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDServiceException;

    /**
     * This method is used for fetching Case Details for CBR Screen
     * 
     * @param CBRAdminVO
     * @return List<CBRAdminVO>
     * @throws RMDServiceException
     */

    public List<CBRAdminVO> getCaseMaintenanceDetails(CBRAdminVO objCBRCasesVO) throws RMDServiceException;

    /**
     * This method is used for Activate/Deactivate cases
     * 
     * @param objid
     * @param status
     * @return
     * @throws RMDServiceException
     */
    public String caseActivateDeactivate(String objid, String status) throws RMDServiceException;

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
    public String rxActivateDeactivate(String objid, String status, String strUserName) throws RMDServiceException;
}
