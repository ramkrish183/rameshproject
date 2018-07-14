/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserDAOImpl.java
 * Description : BO Intf for User Management
 *
 * Package : com.ge.trans.rmd.services.admin.bo.intf
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
package com.ge.trans.eoa.services.admin.bo.intf;

import java.util.List;

import com.ge.trans.rmd.exception.RMDBOException;
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
 * @Description : BO Intf for User Management
 * @History :
 ******************************************************************************/
public interface UserBOIntf {

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDBOException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDBOException;

    /**
     * @Author:
     * @param userSeqId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDBOException;

    /**
     * @Author:
     * @param userServiceVO
     * @param userRoles
     * @return
     * @throws RMDBOException
     * @Description:
     */
    Long saveUserInfo(UserServiceVO userServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param userServiceVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    void resetPassword(UserServiceVO userServiceVO) throws RMDBOException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDBOException;

    /**
     * @Author:
     * @param userPreferenceList
     * @return
     * @throws RMDBOException
     * @Description:
     */
    int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList, GetUsrUserPreferenceVO userPreferenceVOobj)
            throws RMDBOException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    void deleteFavoriteFilter(final FavFilterServiceVO objFavFilterVO) throws RMDBOException;

    /**
     * @Author:
     * @param objFavFilterVO
     * @return
     * @throws RMDServiceException
     * @Description:
     */
    int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDBOException;

    /**
     * @param objFavFilterVO
     * @return
     * @throws RMDBOException
     */
    FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDBOException;

    /**
     * @Author:
     * @param objUserServiceSearchVO
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDBOException;

    /**
     * @Author:
     * @param userId
     * @return
     * @throws RMDBOException
     * @Description:
     */
    List<String> getUserComponentList(String userId) throws RMDBOException;

	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleId 
	 * @return:UserEOADetailsVO
	 * @throws:RMDBOException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	UserEOADetailsVO getCMorMLDetails(String roleId,String userId, String removedRoleId) throws RMDBOException;
	
	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws: RMDBOException;
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	String checkEOAAliasExist(String aliasName) throws RMDBOException;

	/**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public List<RolesVO> getNoCMRoles() throws RMDBOException;
    
    /**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDBOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    public String deleteUsers(List<String> userdetails) throws RMDBOException;

}
