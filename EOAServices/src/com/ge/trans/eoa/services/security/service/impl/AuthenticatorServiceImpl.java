/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthenticatorServiceImpl.java
 * Description : Service for Authentication process
 *
 * Package : com.ge.trans.rmd.services.security.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Nov 4, 2009
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.security.bo.intf.AuthenticatorBOIntf;
import com.ge.trans.eoa.services.security.service.intf.AuthenticatorServiceIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @DateCreated: Nov 4, 2009
 * @DateModified : May 11, 2010
 * @ModifiedBy : Sathya
 * @Contact :
 * @Description : Service for Authentication process
 * @History : Added method to get time zone (Sathya)
 ******************************************************************************/
public class AuthenticatorServiceImpl implements AuthenticatorServiceIntf {
    /**
     * Declare Authenticator BO interface
     */
    private AuthenticatorBOIntf objauthenticatorBOIntf;

    /**
     * @param objauthenticatorBOIntf
     */
    public AuthenticatorServiceImpl(AuthenticatorBOIntf objauthenticatorBOIntf) {
        this.objauthenticatorBOIntf = objauthenticatorBOIntf;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf
     * #isUserValid(com.ge.trans.rmd.services.cases.service.valueobjects.UserVO)
     * Method to check is a user is valid
     */
    @Override
    public GetUsrUsersVO authenticateUser(LoginRequestServiceVO objLogin) throws RMDServiceException {
        GetUsrUsersVO objUser = null;
        try {
            objUser = objauthenticatorBOIntf.authorizeUser(objLogin);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objLogin.getStrLanguage());
        }
        return objUser;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf
     * Method to fetch user preferences
     */
    @Override
    public List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLogin) throws RMDServiceException {
        List<GetUsrUserPreferenceVO> objUserPreference = null;
        try {
            objUserPreference = objauthenticatorBOIntf.getUserPreferences(objLogin);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objLogin.getStrLanguage());
        }
        return objUserPreference;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf
     * Method to get the available time zones for the system
     */
    @Override
    public List<GetCmTimeZoneVO> getTimeZones(final String language) throws RMDServiceException {
        List<GetCmTimeZoneVO> timeZones = null;
        try {
            timeZones = objauthenticatorBOIntf.getTimeZones(language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return timeZones;
    }

	@Override
	public String updateLastLoginDetails(String userId, String lastLoginFrom)
			throws RMDServiceException {
		String status = null;
		 try {
			 status = objauthenticatorBOIntf.updateLastLoginDetails(userId, lastLoginFrom);
	        } catch (RMDDAOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        } catch (RMDBOException ex) {
	            throw new RMDServiceException(ex.getErrorDetail(), ex);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        return status;
	}
}
