/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthenticatorBOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.security.bo.intf;
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
package com.ge.trans.eoa.services.security.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @DateModified : May 11, 2010
 * @ModifiedBy : Sathya
 * @Contact :
 * @Description : Interface for authenticatorBO
 * @History :
 ******************************************************************************/
public interface AuthenticatorBOIntf {

    /**
     * @Author:
     * @param userVO
     * @return
     * @throws RMDBOException
     * @Description: Method to check if a user is valid
     */
    GetUsrUsersVO authorizeUser(LoginRequestServiceVO objLogin) throws RMDBOException;

    /**
     * @Author:
     * @param userVO
     * @return
     * @throws RMDBOException
     * @Description: Method to check if a user is valid
     */
    List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLogin) throws RMDBOException;

    /**
     * @Author:
     * @return List
     * @throws RMDBOException
     * @Description: Method to get the available time zones for the system
     */
    List<GetCmTimeZoneVO> getTimeZones(final String language) throws RMDBOException;
    /**
     * @Author:
     * @return String
     * @throws RMDServiceException
     * @Description: Method to get the available time zones for the system
     */
    String updateLastLoginDetails(final String userId, final String lastLoginFrom) throws RMDBOException;
}
