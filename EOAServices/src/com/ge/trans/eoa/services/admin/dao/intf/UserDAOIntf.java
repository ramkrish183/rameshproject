/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserDAOIntf.java
 * Description : DAO Interface for User Management
 *
 * Package : com.ge.trans.rmd.services.admin.dao.intf
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
package com.ge.trans.eoa.services.admin.dao.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FetchFavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Interface for User Management
 * @History :
 ******************************************************************************/
public interface UserDAOIntf {

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDDAOException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDDAOException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    UserServiceVO saveUserInfo(UserServiceVO userServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    UserServiceVO createUserPreference(UserServiceVO userServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    UserServiceVO saveUserRole(UserServiceVO userServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int deleteUserRoles(Long userRoleSeqId) throws RMDDAOException;

    /**
     * @Author:
     * @param userServiceVO
     * @param userRoles
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveUserRoles(UserServiceVO userServiceVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userPreferenceList
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList, GetUsrUserPreferenceVO userPreferenceVOobj)
            throws RMDDAOException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    void deleteFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDDAOException;

    /**
     * @param objFavFilterVO
     * @return
     * @throws RMDDAOException
     */
    FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDDAOException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDDAOException;

    /**
     * @Author:
     * @param userId
     * @return
     * @throws RMDDAOException
     * @Description:
     */
    List<String> getUserComponentList(String userId) throws RMDDAOException;

	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleID
	 * @return:UserEOADetailsVO
	 * @throws:RMDDAOException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	UserEOADetailsVO getCMorMLDetails(String roleId,String userId, String removedRoleID) throws RMDDAOException;
	
	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws: RMDDAOException
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	String checkEOAAliasExist(String aliasName) throws RMDDAOException;

    UserServiceVO getUserInfo(String userId) throws RMDDAOException;

    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public List<RolesVO> getNoCMRoles() throws RMDDAOException;
    
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public String deleteUsers(List<String> userdetails) throws RMDBOException;
}