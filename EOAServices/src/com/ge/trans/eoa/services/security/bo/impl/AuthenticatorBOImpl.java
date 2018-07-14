/**
 * ============================================================
 * Classification: GE Confidential
 * File : AuthenticatorBOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.security.bo.impl
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
package com.ge.trans.eoa.services.security.bo.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.security.bo.intf.AuthenticatorBOIntf;
import com.ge.trans.eoa.services.security.dao.intf.AuthenticatorDAOIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Nov 4, 2009
 * @DateModified : May 11, 2010
 * @ModifiedBy : Sathya
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class AuthenticatorBOImpl implements AuthenticatorBOIntf {

    /**
     * Declare Authenticator BO interface
     */
    private AuthenticatorDAOIntf objAuthenticatorDAO;

    /**
     * @param objAuthenticatorDAO
     */
    public AuthenticatorBOImpl(AuthenticatorDAOIntf objAuthenticatorDAO) {
        this.objAuthenticatorDAO = objAuthenticatorDAO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf#
     * authorizeUser(com.ge.trans.rmd.services.cases.service.valueobjects.
     * UserVO) Method to check is a user is valid
     */
    @Override
    public GetUsrUsersVO authorizeUser(LoginRequestServiceVO objLogin) throws RMDBOException {
        GetUsrUsersVO objUserVO = null;
        try {
            objUserVO = objAuthenticatorDAO.getUserDetails(objLogin);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objUserVO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf
     * #getTimeZones(com.ge.trans.rmd.services.cases.service.valueobjects.
     * UserVO) Method to get the available time zones for the system
     */
    @Override
    public List<GetCmTimeZoneVO> getTimeZones(final String language) throws RMDBOException {
        List<GetCmTimeZoneVO> timeZones = null;
        try {
            timeZones = objAuthenticatorDAO.getTimeZone(language);
        } catch (RMDDAOException e) {
            throw e;
        }
        return timeZones;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.cases.service.intf.AuthenticatorServiceIntf
     * #getTimeZones(com.ge.trans.rmd.services.common.valueobjects.
     * GetUsrUserPreferenceVO) Method to get the default user preference for the
     * system
     */
    @Override
    public List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLogin) throws RMDBOException {
        List<GetUsrUserPreferenceVO> objUserPreferenceVO = null;
        try {
            objUserPreferenceVO = objAuthenticatorDAO.getUserPreferences(objLogin);
        } catch (RMDDAOException e) {
            throw e;
        }
        return objUserPreferenceVO;
    }

	@Override
	public String updateLastLoginDetails(String userId, String lastLoginFrom) throws RMDBOException {
		String status= null;
        try {
        	status = objAuthenticatorDAO.updateLastLoginDetails(userId, lastLoginFrom);
        } catch (RMDDAOException e) {
            throw e;
        }
        return status;
	}
}
