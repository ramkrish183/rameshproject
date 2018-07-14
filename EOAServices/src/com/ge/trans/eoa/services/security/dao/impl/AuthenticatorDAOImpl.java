/**
 * ============================================================
 * Classification: GE Confidential
 * File : UserDAOImpl.java
 * Description :
 *
 * Package : com.ge.trans.rmd.services.security.dao.impl
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
package com.ge.trans.eoa.services.security.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.GetCmContactInfoVO;
import com.ge.trans.eoa.services.common.valueobjects.GetCmTimeZoneVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUsersVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetTimeZoneHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrRolesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserPreferenceHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserRolesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUsersHVO;
import com.ge.trans.eoa.services.security.dao.intf.AuthenticatorDAOIntf;
import com.ge.trans.eoa.services.security.service.valueobjects.LoginRequestServiceVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : Oct 30, 2009
 * @DateModified: May 11, 2010
 * @ModifiedBy : Sathya
 * @Contact :
 * @Description : Implementation class for AuthenticatorDAOIntf Interface
 * @History :
 ******************************************************************************/
public class AuthenticatorDAOImpl extends RMDCommonDAO implements AuthenticatorDAOIntf {

    private static final long serialVersionUID = 1L;

    /*
     * (non-Javadoc)
     * @com.ge.trans.rmd.services.security.dao.intf.AuthenticatorDAOIntf
     * #getUserDetails
     * (com.ge.trans.rmd.services.cases.service.valueobjects.UserVO) Method to
     * retrieve user details
     */
    @Override
    @SuppressWarnings("unchecked")
    public GetUsrUsersVO getUserDetails(LoginRequestServiceVO objLoginRequest)
            throws RMDDAOException, RMDDAOConnectionException {
        GetUsrUsersVO objUsersVO = null;
        Session hibernateSession = null;
        SimpleDateFormat sdf = new SimpleDateFormat(RMDCommonConstants.ddMMyyyyHHmmss);
        String lastVisited=null;
        List<GetUsrUsersHVO> userList = null;
        Query query = null;
        
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);

            // To check whether user id matches exactly
            String queryString = "FROM GetUsrUsersHVO USER LEFT JOIN FETCH USER.getUsrUserRoles LEFT JOIN FETCH USER.getTableBusOrgHVO WHERE USER.userId = :userId"; 
           
            query = hibernateSession.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USERID, objLoginRequest.getStrUserName());            
            query.setFetchSize(1);
            userList = query.list();
            
            if (!RMDCommonUtility.isCollectionNotEmpty(userList)) {
                // Upper added to make the username case insensitive
                String queryStringUppr = "FROM GetUsrUsersHVO USER LEFT JOIN FETCH USER.getUsrUserRoles LEFT JOIN FETCH USER.getTableBusOrgHVO WHERE UPPER(USER.userId) = :userId"; // and                                                                                                                                                                     // :language
                query = hibernateSession.createQuery(queryStringUppr);
                query.setParameter(RMDCommonConstants.USERID, objLoginRequest.getStrUserName().toUpperCase());
                query.setFetchSize(1);
                userList = query.list();
            }
            if (RMDCommonUtility.isCollectionNotEmpty(userList)) {
                GetUsrUsersHVO objUserHVO = userList.get(0);
                objUsersVO = new GetUsrUsersVO();
                objUsersVO.setGetUsrUsersSeqId(objUserHVO.getGetUsrUsersSeqId());
                objUsersVO.setRowVersion(objUserHVO.getRowVersion());
                objUsersVO.setStatus(objUserHVO.getStatus());
                objUsersVO.setUserId(objUserHVO.getUserId());
                objUsersVO.setEndUserScoring(objUserHVO.getEndUserScoring() == null?"N":objUserHVO.getEndUserScoring());
                GetCmContactInfoVO contactInfoVO = new GetCmContactInfoVO();
                contactInfoVO.setFirstName(objUserHVO.getFirstName());
                contactInfoVO.setLastName(objUserHVO.getLastName());
                objUsersVO.setGetCmContactInfo(contactInfoVO);
                objUsersVO.setUserType(objUserHVO.getUserType());
                if(objUserHVO.getLastLoginDateTime()!=null && !objUserHVO.getLastLoginDateTime().equals(RMDCommonConstants.EMPTY_STRING)){
	                lastVisited=sdf.format(objUserHVO.getLastLoginDateTime());
	                objUsersVO.setLastLoginDateTime(lastVisited);
                }else{
                	sdf.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
                	Date currentDate=new Date();
                	lastVisited=sdf.format(currentDate);
                	objUsersVO.setLastLoginDateTime(lastVisited);
                }
                objUsersVO.setLastLoginFrom(objUserHVO.getLastLoginFrom());
                //Added by Murali Medicherla for Rally Id : US226051
                objUsersVO.setMobileAccess(objUserHVO.getMobileAccess());
                // RMD Changes - START
                List<RolesVO> rolesVOLst = new ArrayList<RolesVO>();
                List<GetUsrUserRolesHVO> getUsrUserRolesHVOLst = new ArrayList<GetUsrUserRolesHVO>(
                        objUserHVO.getGetUsrUserRoles());
                RolesVO rolesVO = null;
                for (GetUsrUserRolesHVO fetUsrUserRolesHVO : getUsrUserRolesHVOLst) {

                    GetUsrRolesHVO getUsrRolesHVO = fetUsrUserRolesHVO.getGetUsrRoles();

                    if (null != getUsrRolesHVO) {
                        rolesVO = new RolesVO();
                        /* User Roles seq id */
                        rolesVO.setLinkUsrRoleSeqId(fetUsrUserRolesHVO.getGetUsrUserRolesSeqId());
                        /* User Roles seq id */
                        rolesVO.setGetUsrRolesSeqId(getUsrRolesHVO.getGetUsrRolesSeqId());
                        rolesVO.setRoleName(getUsrRolesHVO.getRoleName());
                        rolesVO.setRoleDesc(getUsrRolesHVO.getRoleDesc());
                        rolesVO.setStatus(getUsrRolesHVO.getStatus());
                        rolesVOLst.add(rolesVO);
                    }
                }
                objUsersVO.setGetUsrUserRoles(rolesVOLst);

                // Customer Filter story - sets the customer associated with the
                // user and added CustomerName for Miscellaneous changes across
                // the screens story
                if (null != objUserHVO.getGetTableBusOrgHVO()) {
                    objUsersVO.setCustomerId(objUserHVO.getGetTableBusOrgHVO().getOrgId());
                    if (null != objUserHVO.getGetTableBusOrgHVO().getName()) {
                        objUsersVO.setCustomerName(objUserHVO.getGetTableBusOrgHVO().getName());
                    } else {
                        objUsersVO.setCustomerName(RMDCommonConstants.EMPTY_STRING);
                    }
                }
                // RMD Changes - END
            }
        } catch (RMDDAOConnectionException e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_AUTHENTICATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLoginRequest.getStrLanguage()), e,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_AUTHENTICATION);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLoginRequest.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return objUsersVO;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.security.dao.intf.AuthenticatorDAOIntf#
     * getUserPreferences
     * (com.ge.trans.rmd.services.security.service.valueobjects
     * .LoginRequestServiceVO)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GetUsrUserPreferenceVO> getUserPreferences(LoginRequestServiceVO objLoginRequest)

            throws RMDDAOException, RMDDAOConnectionException {
        GetUsrUserPreferenceVO objUserPreferenceVO = null;
        Session hibernateSession = null;
        List<GetUsrUserPreferenceVO> objList = new ArrayList<GetUsrUserPreferenceVO>();
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);

            String queryString = "SELECT PREFERENCE FROM GetUsrUserPreferenceHVO PREFERENCE, GetUsrUsersHVO USERTABLE WHERE USERTABLE.getUsrUsersSeqId=PREFERENCE.userId AND  UPPER(USERTABLE.userId) = :userName";
            Query query = hibernateSession.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USERNAME, objLoginRequest.getStrUserName().toUpperCase());
            List<GetUsrUserPreferenceHVO> userList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(userList)) {

                for (GetUsrUserPreferenceHVO objUserPreferenceHVO : userList) {
                    objUserPreferenceVO = new GetUsrUserPreferenceVO();
                    objUserPreferenceVO
                            .setGetUsrUserPreferenceSeqId(objUserPreferenceHVO.getGetUsrUserPreferenceSeqId());
                    objUserPreferenceVO.setUserPreferernceType(objUserPreferenceHVO.getUserPreferernceType());
                    objUserPreferenceVO.setUserPreferenceValue(objUserPreferenceHVO.getUserPreferenceValue());
                    GetUsrUsersHVO objUserHVO = objUserPreferenceHVO.getUserId();

                    GetUsrUsersVO objUserVO = new GetUsrUsersVO();
                    objUserVO.setGetUsrUsersSeqId(objUserHVO.getGetUsrUsersSeqId());
                    objUserPreferenceVO.setGetUsrUsersVO(objUserVO);
                    objList.add(objUserPreferenceVO);

                }
                return objList;

            }
        } catch (RMDDAOConnectionException e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLoginRequest.getStrLanguage()), e,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objLoginRequest.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return objList;
    }

    /*
     * (non-Javadoc)
     * @see com.ge.trans.rmd.services.security.dao.intf.AuthenticatorDAOIntf
     * Method to get the available time zones for the system
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GetCmTimeZoneVO> getTimeZone(final String language) {
        Session hibernateSession = null;
        GetCmTimeZoneVO timeZoneVO = null;
        List<GetTimeZoneHVO> allTimeZones = null;
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);
            Criteria criteria = hibernateSession.createCriteria(GetTimeZoneHVO.class);
            criteria.add(Restrictions.ne(RMDCommonConstants.NAME,RMDCommonConstants.NONE));
            criteria.addOrder(Order.asc(RMDCommonConstants.FULLNAME));
            criteria.setCacheable(true);
            allTimeZones = criteria.list();
            if (RMDCommonUtility.isCollectionNotEmpty(allTimeZones)) {
                List<GetCmTimeZoneVO> timeZones = new ArrayList<GetCmTimeZoneVO>();
                for (GetTimeZoneHVO timeZone : allTimeZones) {
                	
                    timeZoneVO = new GetCmTimeZoneVO();
                    BeanUtils.copyProperties(timeZoneVO, timeZone);
                    timeZoneVO.setName(timeZone.getName());
                    timeZoneVO.setDisplayName(timeZone.getName());
                    timeZones.add(timeZoneVO);
                    
                }
                return timeZones;
            }
            return null;
        } catch (RMDDAOConnectionException e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TIMEZONE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TIMEZONE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }

	@Override
	public String updateLastLoginDetails(String userId, String lastLoginFrom) throws RMDDAOException {
		Session session = null;
		Query caseHqry = null;
		String status = RMDServiceConstants.SUCCESS;
		try {
			
			session = getHibernateSession();
			StringBuilder updateQuery = new StringBuilder();
			updateQuery
					.append("update GET_USR.GET_USR_USERS set LAST_LOGIN_DATE = sysdate, LAST_LOGIN_FROM =:lastLoginFrom where GET_USR_USERS_SEQ_ID =:userID");
			caseHqry = session.createSQLQuery(updateQuery.toString());
			caseHqry.setParameter(RMDCommonConstants.USER_ID, userId);
			caseHqry.setParameter(RMDCommonConstants.LAST_LOGIN_FROM, lastLoginFrom);
			caseHqry.executeUpdate();
		} catch (RMDDAOConnectionException ex) {
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_LAST_LOGIN_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);

		} catch (Exception e) {
			status = RMDServiceConstants.FAILURE;
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_UPDATE_LAST_LOGIN_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);

		} finally {
			releaseSession(session);
		}
		return status;
	}
}
