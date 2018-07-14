/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthenticatorServiceIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.service.intf
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 30, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.service.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: Oct 30, 2009
 * @DateModified : May 11, 2010
 * @ModifiedBy : Sathya
 * @Contact :
 * @Description : Interface for Authenticator Service
 * @History :
 ******************************************************************************/
public interface AuthenticatorServiceIntf {

    /**
     * @Author:
     * @param uservo
     * @return boolean
     * @throws RMDServiceException
     * @Description: Method to check if the user is valid
     */
    GetUsrUsersVO authenticateUser(LoginRequestServiceVO objLogin) throws RMDServiceException;

    /**
     * @Author:
     * @param uservo
     * @return boolean
     * @throws RMDServiceException
     * @Description: Method to check if the user is valid
     */
    List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLogin) throws RMDServiceException;

    /**
     * @Author:
     * @return List
     * @throws RMDServiceException
     * @Description: Method to get the available time zones for the system
     */
    List<GetCmTimeZoneVO> getTimeZones(final String language) throws RMDServiceException;
    /**
     * @Author:
     * @return String
     * @throws RMDServiceException
     * @Description: Method to update the last logged in date and logged in from details
     */
    String updateLastLoginDetails(final String userId, final String lastLoginFrom) throws RMDServiceException;
}
