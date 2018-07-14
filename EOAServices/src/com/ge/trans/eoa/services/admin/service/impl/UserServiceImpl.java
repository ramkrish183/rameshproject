/**
 * ============================================================
 * Classification: GE Confidential
 * File : UserServiceImpl.java
 * Description : Service Impl for User Management
 *
 * Package : com.ge.trans.rmd.services.admin.service.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.admin.service.impl;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.bo.intf.UserBOIntf;
import com.ge.trans.eoa.services.admin.service.intf.UserServiceIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FetchFavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated: May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : Service Implementation for User Management
 * @History :
 ******************************************************************************/
public class UserServiceImpl implements UserServiceIntf {

    /** userBO of Type UserBOIntf **/
    private UserBOIntf userBO;

    /**
     * @param userBO
     */
    public UserServiceImpl(UserBOIntf userBO) {
        this.userBO = userBO;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#getUsers(com
     * .ge.trans.rmd.services.security.service.valueobjects.UserServiceVO)
     */
    @Override
    public List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDServiceException {
        List<UserServiceVO> users = null;
        try {
            users = userBO.getUsers(objUserServiceSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objUserServiceSearchVO.getStrLanguage());
        }
        return users;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#getUserInfo(
     * java.lang.Long)
     */
    @Override
    public UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDServiceException {
        UserServiceVO userInfo = null;
        try {
            userInfo = userBO.getUserInfo(userSeqId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return userInfo;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#
     * getUserRoleInfo(java.lang.Long)
     */
    @Override
    public List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDServiceException {
        List<RoleServiceVO> userRoles = null;
        try {
            userRoles = userBO.getUserRoleInfo(userSeqId, language);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, language);
        }
        return userRoles;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#saveUserInfo
     * (com.ge.trans.rmd.services.security.service.valueobjects.UserServiceVO,
     * java.util.List)
     */
    @Override
    public Long saveUserInfo(UserServiceVO userServiceVO) throws RMDServiceException {
        Long userSeqId = null;
        try {
            userSeqId = userBO.saveUserInfo(userServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, userServiceVO.getStrLanguage());
        }
        return userSeqId;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#
     * resetPassword(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO, java.util.List)
     */
    @Override
    public void resetPassword(UserServiceVO userServiceVO) throws RMDServiceException {
        try {
            userBO.resetPassword(userServiceVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, userServiceVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#
     * getMatchingUser(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO)
     */
    @Override
    public UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDServiceException {
        UserServiceVO matchingUser = null;
        try {
            matchingUser = userBO.getMatchingUser(objUserServiceSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objUserServiceSearchVO.getStrLanguage());
        }
        return matchingUser;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#
     * getMatchingUser(com.ge.trans.rmd.services.security.service.valueobjects.
     * GetUsrUserPreferenceVO)
     */
    @Override
    public int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList,
            GetUsrUserPreferenceVO userPreferenceVOobj) throws RMDServiceException {
        int flag = 0;
        try {
            flag = userBO.saveUserPreferences(userPreferenceList, userPreferenceVOobj);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, userPreferenceVOobj.getStrLanguage());
        }
        return flag;
    }

    @Override
    public void deleteFavoriteFilter(final FavFilterServiceVO objFavFilterVO) throws RMDServiceException {
        try {
            userBO.deleteFavoriteFilter(objFavFilterVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFavFilterVO.getStrLanguage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.service.intf.UserServiceIntf#
     * saveFilterData(com.ge.trans.rmd.services.security.service.valueobjects.
     * FavFilterServiceVO)
     */
    @Override
    public int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDServiceException {
        int flag = 0;
        try {
            flag = userBO.saveFilterData(objFavFilterVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFavFilterVO.getStrLanguage());
        }
        return flag;
    }

    /**
     * fetch favorite filter associated to a user
     */
    @Override
    public FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDServiceException {
        FetchFavFilterServiceVO fetchFilterVO = null;
        try {
            fetchFilterVO = userBO.fetchFavoriteFilter(objFavFilterVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objFavFilterVO.getStrLanguage());
        }
        return fetchFilterVO;
    }

    /**
     * Fetches enabled user who has privilege for alert subscription
     */
    @Override
    public List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDServiceException {
        List<UserServiceVO> users = null;
        try {
            users = userBO.getAlertUsersDetails(objUserServiceSearchVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objUserServiceSearchVO.getStrLanguage());
        }
        return users;
    }

    /**
     * fetch privilege associated to a user
     */
    @Override
    public List<String> getUserComponentList(String userId) throws RMDServiceException {
        List<String> userComponentList = null;
        try {
            userComponentList = userBO.getUserComponentList(userId);
        } catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail(), e);
        }
        return userComponentList;
    }

	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleId
	 * @return:UserEOADetailsVO
	 * @throws:RMDServiceException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */

	@Override
	public UserEOADetailsVO getCMorMLDetails(String roleId,String userId,String removedRoleId) throws RMDServiceException {
		UserEOADetailsVO objUserEOADetailsVO = null;
		try {
			objUserEOADetailsVO = userBO.getCMorMLDetails(roleId,userId,removedRoleId);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return objUserEOADetailsVO;
	}

	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	@Override
	public String checkEOAAliasExist(String aliasName)
			throws RMDServiceException {
		String result = null;
		try {
			result = userBO.checkEOAAliasExist(aliasName);
		} catch (RMDBOException e) {
			throw new RMDServiceException(e.getErrorDetail());
		}
		return result;
	}

	/**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    @Override
    public List<RolesVO> getNoCMRoles() throws RMDServiceException {
        List<RolesVO> arlRolesVO =null;
        try{
            arlRolesVO=userBO.getNoCMRoles();
        }catch (RMDBOException e) {
            throw new RMDServiceException(e.getErrorDetail());
        }

        return arlRolesVO;
    }

	@Override
	public String deleteUsers(List<String> userdetails) throws RMDServiceException {
		String str = null;
	    try{
	        str= userBO.deleteUsers(userdetails);
	       
	    }catch (RMDBOException e) {
	    	throw new RMDServiceException(e.getErrorDetail());

           
        }
        return str;
	}
	
}