/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserBOImpl.java
 * Description : BO Impl for User Management
 *
 * Package : com.ge.trans.rmd.services.admin.bo.impl
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : May 13, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * =================================================================
 */
package com.ge.trans.eoa.services.admin.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.bo.intf.UserBOIntf;
import com.ge.trans.eoa.services.admin.dao.intf.UserDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FetchFavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : BO Impl for User Management
 * @History :
 ******************************************************************************/
public class UserBOImpl implements UserBOIntf {

    /** userDAO of Type UserDAOIntf **/
    private UserDAOIntf userDAO;

    /**
     * @param userDAO
     */
    public UserBOImpl(UserDAOIntf userDAO) {
        this.userDAO = userDAO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf
     * #getUsers(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO)
     */
    @Override
    public List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDBOException {
        try {
            return userDAO.getUsers(objUserServiceSearchVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf#getUserInfo(java.lang.
     * Long)
     */
    @Override
    public UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDBOException {
        try {
            return userDAO.getUserInfo(userSeqId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf#getUserRoleInfo(java.
     * lang.Long)
     */
    @Override
    public List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDBOException {
        try {
            return userDAO.getUserRoleInfo(userSeqId, language);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf
     * #saveUserInfo(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO, java.util.List)
     */
    @Override
    public Long saveUserInfo(UserServiceVO userServiceVO) throws RMDBOException {
        try {

            UserServiceVO userServiceVOPersisted = userDAO.saveUserInfo(userServiceVO);
            userServiceVO.setGetUsrUsersSeqId(userServiceVOPersisted.getGetUsrUsersSeqId());
            userServiceVO.setUserId(userServiceVOPersisted.getUserId());
            userServiceVO.setUsrRoleId(userServiceVOPersisted.getUsrRoleId());
			if(userServiceVOPersisted.isEoaTableUpdated()){//added for MDSC Admin changes
				if (Boolean.valueOf(userServiceVO.getRolesChangeFlag())) {
					userDAO.saveUserRoles(userServiceVO);
				}
				
				if(null!=userServiceVO.getUsrRoleId()&& userServiceVO.getUsrRoleId()!=0){
					userServiceVOPersisted = userDAO.createUserPreference(userServiceVO);
				}
			}
            userServiceVO.setUsrRoleId(userServiceVOPersisted.getUsrRoleId());
            userServiceVO.setGetUsrUsersSeqId(userServiceVOPersisted.getGetUsrUsersSeqId());
            return userServiceVOPersisted.getGetUsrUsersSeqId();

        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
			String errorCode= RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_USER_MANAGEMENT);

            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf
     * #resetPassword(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO, java.util.List)
     */
    @Override
    public void resetPassword(UserServiceVO userServiceVO) throws RMDBOException {
        try {
            userDAO.saveUserInfo(userServiceVO);
        } catch (RMDDAOException e) {
            throw e;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.BO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MINOR_ERROR);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf
     * #getMatchingUser(com.ge.trans.rmd.services.security.service.valueobjects.
     * UserServiceVO)
     */
    @Override
    public UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDBOException {
        try {
            return userDAO.getMatchingUser(objUserServiceSearchVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf
     * #saveUserPreference(com.ge.trans.rmd.services.security.service.
     * valueobjects.GetUsrUserPreferenceVO)
     */
    @Override
    public int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList,
            GetUsrUserPreferenceVO userPreferenceVOobj) throws RMDBOException {
        int flag = 0;
        try {
            flag = userDAO.saveUserPreferences(userPreferenceList, userPreferenceVOobj);
        } catch (RMDDAOException e) {
            throw e;
        }
        return flag;
    }

    @Override
    public void deleteFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDBOException {
        try {
            userDAO.deleteFavoriteFilter(objFavFilterVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.bo.intf.UserBOIntf#saveFilterData(com.ge.
     * trans.rmd.services.security.service.valueobjects.FavFilterServiceVO)
     */
    @Override
    public int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDBOException {
        int flag = 0;
        Object[] strDataArray;
        List<Object[]> dataLst = new ArrayList<Object[]>();
        try {
            if (RMDCommonUtility.isCollectionNotEmpty(objFavFilterVO.getFilterData())) {
                // Converting the comma String to String Array
                for (int i = 0; i < objFavFilterVO.getFilterData().size(); i++) {
                    strDataArray = RMDCommonUtility.splitString(objFavFilterVO.getFilterData().get(i),
                            RMDCommonConstants.COMMMA_SEPARATOR);
                    dataLst.add(strDataArray);

                }
                objFavFilterVO.setDataLst(dataLst);
            }
            flag = userDAO.saveFilterData(objFavFilterVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return flag;
    }

    /**
     * fetch favorite filter associated to a user
     */
    @Override
    public FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDBOException {
        try {
            return userDAO.fetchFavoriteFilter(objFavFilterVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * fetch enabled user who has privilege for alert subscription
     */
    @Override
    public List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDBOException {
        try {
            return userDAO.getAlertUsersDetails(objUserServiceSearchVO);
        } catch (RMDDAOException e) {
            throw e;
        }
    }

    /**
     * fetch privilege related to the user
     */
    @Override
    public List<String> getUserComponentList(String userId) throws RMDBOException {
        List<String> userComponentList = null;
        try {
            userComponentList = userDAO.getUserComponentList(userId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return userComponentList;
    }
	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleID
	 * @return:UserEOADetailsVO
	 * @throws:RMDBOException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	@Override
	public UserEOADetailsVO getCMorMLDetails(String roleId,String userId,String removedRoleID) throws RMDBOException {
		UserEOADetailsVO objUserEOADetailsVO = null;
		try {
			objUserEOADetailsVO = userDAO.getCMorMLDetails(roleId,userId,removedRoleID);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail());
		}
		return objUserEOADetailsVO;
	}

	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws: RMDBOException;
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	@Override
	public String checkEOAAliasExist(String aliasName) throws RMDBOException {
		String result = null;
		try {
			result = userDAO.checkEOAAliasExist(aliasName);
		} catch (RMDDAOException e) {
			throw new RMDBOException(e.getErrorDetail());
		}
		return result;
	}


	/**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    @Override
    public List<RolesVO> getNoCMRoles() throws RMDBOException {
        List<RolesVO> arlRolesVO = null;
        try{
            arlRolesVO=userDAO.getNoCMRoles();
        }catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail());
        }  
        return arlRolesVO;
    }

	@Override
	public String deleteUsers(List<String> userdetails) throws RMDBOException {
		// TODO Auto-generated method stub
		String str = null;
	    try{
	        str= userDAO.deleteUsers(userdetails);
	       
	    }catch (RMDDAOException e) {
	    	throw new RMDBOException(e.getErrorDetail());

           
        }
        return str;
	}
}