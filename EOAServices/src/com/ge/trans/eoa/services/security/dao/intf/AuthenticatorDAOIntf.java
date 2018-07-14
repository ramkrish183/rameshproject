/**
 * ============================================================
 * Classification: GE Confidential
 * File : UserDAOIntf.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.cases.dao.intf
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
package com.ge.trans.eoa.services.security.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Oct 30, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : Interface for UserDAO
 * @History :
 ******************************************************************************/
public interface AuthenticatorDAOIntf {

    /**
     * @Author:
     * @param userVO
     * @return UserVO
     * @Description: Method for retrieving user all user information
     */
    GetUsrUsersVO getUserDetails(LoginRequestServiceVO objLogin);

    /**
     * @Author:
     * @param userVO
     * @return UserVO
     * @Description: Method for retrieving user all user information
     */
    List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLogin);

    /**
     * @Author:
     * @return Map
     * @throws RMDDAOException
     * @Description: Method to get the available time zones for the system
     */
    List<GetCmTimeZoneVO> getTimeZone(final String language);
    /**
     * @Author:
     * @return String
     * @throws RMDServiceException
     * @Description: Method to get the available time zones for the system
     */
   String updateLastLoginDetails(final String userId, final String lastLoginFrom);
}
