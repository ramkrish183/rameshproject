/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserServiceIntf.java
 * Description : Service Interface for User Management
 *
 * Package : com.ge.trans.rmd.services.admin.service.intf
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
package com.ge.trans.eoa.services.admin.service.intf;

import java.util.List;

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
 * @Description : Service Interface for User Management
 * @History :
 ******************************************************************************/
public interface UserServiceIntf {

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDServiceException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDServiceException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDServiceException;

    /**
     * @Author:
     * @param userServiceVO
     * @param userRoles
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    Long saveUserInfo(UserServiceVO userServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    void resetPassword(UserServiceVO userServiceVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDServiceException;

    /**
     * @Author:
     * @param userPreferenceList
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList, GetUsrUserPreferenceVO userPreferenceVOobj)
            throws RMDServiceException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    void deleteFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDServiceException;

    /**
     * @param objFavFilterVO
     * @return
     * @throws RMDServiceException
     */
    FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDServiceException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDServiceException;

    /**
     * @Author:
     * @param userId
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    List<String> getUserComponentList(String userId) throws RMDServiceException;
	
	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleId
	 * @return:UserEOADetailsVO
	 * @throws:RMDServiceException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	UserEOADetailsVO getCMorMLDetails(String roleId, String userId, String removedRoleId) throws RMDServiceException;

	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	String checkEOAAliasExist(String aliasName) throws RMDServiceException;

	/**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public List<RolesVO> getNoCMRoles() throws RMDServiceException;;
    
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDServiceException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public String deleteUsers(List<String> userdetails) throws RMDServiceException;;
}