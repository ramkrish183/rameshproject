/**
 * =================================================================
 * Classification: GE Confidential
 * File : UserDAOImpl.java
 * Description : DAO Impl for User Management
 * 
 * Package : com.ge.trans.rmd.services.admin.dao.impl
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
package com.ge.trans.eoa.services.admin.dao.impl;

import static com.ge.trans.rmd.common.constants.RMDCommonConstants.EMPTY_STRING;
import static com.ge.trans.rmd.common.constants.RMDCommonConstants.UPDATE_SUCCESS;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.owasp.esapi.ESAPI;

import com.ge.trans.eoa.services.admin.dao.intf.UserDAOIntf;
import com.ge.trans.eoa.services.admin.service.valueobjects.RoleServiceVO;
import com.ge.trans.eoa.services.admin.service.valueobjects.UserEOADetailsVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.GetUsrUserPreferenceVO;
import com.ge.trans.eoa.services.common.valueobjects.RolesVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterDetailServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FetchFavFilterServiceVO;
import com.ge.trans.eoa.services.security.service.valueobjects.FilterDetailVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetColumnDetailOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetFilterDetailOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetResourceDetailOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetTableBusOrgHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrFilterDetailDatetimeOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrFilterDetailDecimalOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrFilterDetailNbrOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrFilterDetailTxtOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrFilterOMDHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrRolesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserCustomersHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserPreferenceHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUserRolesHVO;
import com.ge.trans.rmd.services.hibernate.valueobjects.GetUsrUsersHVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author : iGATE
 * @Version : 1.0
 * @DateCreated : May 13, 2010
 * @DateModified :
 * @ModifiedBy :
 * @Contact :
 * @Description : DAO Impl for User Management
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public class UserDAOImpl extends RMDCommonDAO implements UserDAOIntf {

    /** serialVersionUID of Type long **/
    private static final long serialVersionUID = -1434241541314481183L;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(UserDAOImpl.class);
    private final Object lock = new Object();

    /*
     * (non-Javadoc) This method will return the list of users present in the
     * system
     */
    @Override
    public List<UserServiceVO> getUsers(UserServiceVO objUserServiceSearchVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
            List<UserServiceVO> arlSearchResults = null;
            queryString.append("SELECT USR.GET_USR_USERS_SEQ_ID,USR.USER_ID,USR.STATUS, ");
            queryString.append(" USR.FIRST_NAME,USR.EMAIL_ID,USR.LAST_NAME, GETROLES.ROLE_NAME,");
            //Changed by Murali Medicherla for Rally Id : US226051
            queryString.append(
                    " CUST.ORG_ID DEFAULTCUSTOMER,PREF.USER_PREFERENCE_VALUE,ROL.ROLE_NAME PREFERED_ROLE, GETROLES.GET_USR_ROLES_SEQ_ID,  CUST1.ORG_ID multiCust,PREFLANG.USER_PREFERENCE_VALUE PREF_LANG,USR.USER_TYPE,PREFUOM.USER_PREFERENCE_VALUE PREFUOM,USR.LAST_UPDATED_BY,to_char(USR.LAST_UPDATED_DATE,'mm/dd/yyyy hh24:mi:ss') ,USR.MOBILE_ACCESS,USR.END_USER_SCORING ");
            queryString
                    .append(" FROM GET_USR.GET_USR_USERS USR,TABLE_BUS_ORG CUST,GET_USR.GET_USR_USER_ROLES USRROLE,");
            queryString.append(
                    " GET_USR.GET_USR_ROLES GETROLES,GET_USR.GET_USR_USER_PREFERENCE PREF,GET_USR.GET_USR_ROLES ROL, GET_USR.GET_USR_USER_CUSTOMERS USRCUST, TABLE_BUS_ORG CUST1, GET_USR.GET_USR_USER_PREFERENCE PREFLANG,GET_USR.GET_USR_USER_PREFERENCE PREFUOM ");
            queryString.append(" WHERE USR.LINK_CUSTOMER= CUST.OBJID(+)");
            queryString.append(
                    " AND USR.GET_USR_USERS_SEQ_ID=USRROLE.LINK_USERS(+) AND USRROLE.LINK_ROLES=GETROLES.GET_USR_ROLES_SEQ_ID(+) AND USR.GET_USR_USERS_SEQ_ID =PREF.LINK_USERS(+) AND PREF.USER_PREFERENCE_TYPE(+) = 'Role' AND PREF.USER_PREFERENCE_VALUE = ROL.GET_USR_ROLES_SEQ_ID(+)");
            queryString.append(
                    " AND USRCUST.LINK_CUSTOMERS=CUST1.OBJID(+) AND USR.GET_USR_USERS_SEQ_ID= USRCUST.LINK_USERS(+)   AND PREFLANG.USER_PREFERENCE_TYPE(+)  = 'Language'  AND PREFUOM.USER_PREFERENCE_TYPE(+) = 'Measurement System'  AND USR.GET_USR_USERS_SEQ_ID = PREFLANG.link_users(+) AND USR.GET_USR_USERS_SEQ_ID = PREFUOM.link_users(+)");
            if (null != objUserServiceSearchVO.getCustomerId()
                    && !RMDCommonConstants.EMPTY_STRING.equals(objUserServiceSearchVO.getCustomerId())) {
               queryString.append("AND CUST1.ORG_ID  =:customerId");
            }
            if (null != objUserServiceSearchVO.getListCustomer()
                    && !objUserServiceSearchVO.getListCustomer().isEmpty()) {
            	queryString.append("AND CUST1.ORG_ID in (:customerId)");
            }
            if (null != objUserServiceSearchVO.getUserType()
                    && !objUserServiceSearchVO.getUserType().equals(RMDCommonConstants.EMPTY_STRING)) {
            	queryString.append("AND UPPER(USR.USER_TYPE) = UPPER(:userType)");
            }
            queryString.append("ORDER BY USR.LAST_UPDATED_DATE DESC");
            if (null != session) {
                Query query = session.createSQLQuery(queryString.toString());

                // Get the list of users based on the search criteria
                if (null != objUserServiceSearchVO.getCustomerId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objUserServiceSearchVO.getCustomerId())) {
                    query.setParameterList(RMDCommonConstants.CUSTOMER_ID, objUserServiceSearchVO.getListCustomer());
                }
                if (null != objUserServiceSearchVO.getListCustomer()
                        && !objUserServiceSearchVO.getListCustomer().isEmpty()) {
                    query.setParameterList(RMDCommonConstants.CUSTOMER_ID, objUserServiceSearchVO.getListCustomer());
                }
                if (null != objUserServiceSearchVO.getUserType()
                        && !objUserServiceSearchVO.getUserType().equals(RMDCommonConstants.EMPTY_STRING)) {
                    query.setParameter(RMDCommonConstants.USERTYPE, objUserServiceSearchVO.getUserType());
                }
                query.setFetchSize(500);
                List<Object[]> arlUsers = query.list();
                arlSearchResults = populateUsers(arlUsers, RMDCommonConstants.USER_PAGE);
                arlUsers=null;
            }

            return arlSearchResults;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#getMatchingUser(
     * com.ge.trans.rmd.services.security.service.valueobjects.UserServiceVO)
     */
    @Override
    public UserServiceVO getMatchingUser(UserServiceVO objUserServiceSearchVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
            queryString.append("FROM GetUsrUsersHVO U WHERE 1 = 1");
            // Add the search criteria(if any)
            if (StringUtils.isNotBlank(objUserServiceSearchVO.getUserId())) {
                queryString.append(" AND U.userId = :userId");
            }
            Query query = session.createQuery(queryString.toString());
            if (StringUtils.isNotBlank(objUserServiceSearchVO.getUserId())) {
                query.setParameter(RMDCommonConstants.USERID, objUserServiceSearchVO.getUserId());
            }
            // Get the list based on the search criteria(if any)
            List<GetUsrUsersHVO> arlUsers = query.list();
            UserServiceVO mathchingUser = null;
            if (RMDCommonUtility.isCollectionNotEmpty(arlUsers)) {
                GetUsrUsersHVO objUserHVO = arlUsers.get(0);
                // Convert the HVO to service VO
                mathchingUser = new UserServiceVO();
                mathchingUser.setGetUsrUsersSeqId(objUserHVO.getGetUsrUsersSeqId());

                mathchingUser.setStatus(objUserHVO.getStatus());
                mathchingUser.setUserId(objUserHVO.getUserId());

                mathchingUser.setStrFirstName(objUserHVO.getFirstName());
                mathchingUser.setStrLastName(objUserHVO.getLastName());

            }
            return mathchingUser;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#getUserInfo(java
     * .lang.Long)
     */
    @Override
    public UserServiceVO getUserInfo(Long userSeqId, String language) throws RMDDAOException {
        UserServiceVO objUsersVO = null;
        Session hibernateSession = null;
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);
            String queryString = "FROM GetUsrUsersHVO USER WHERE USER.getUsrUsersSeqId =:userSeqId";
            Query query = hibernateSession.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USER_SEQ_ID, userSeqId);
            query.setFetchSize(1);
            List<GetUsrUsersHVO> userList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(userList)) {
                GetUsrUsersHVO objUserHVO = userList.get(0);
                objUsersVO = new UserServiceVO();
                objUsersVO.setGetUsrUsersSeqId(objUserHVO.getGetUsrUsersSeqId());
                objUsersVO.setStatus(objUserHVO.getStatus());
                objUsersVO.setUserId(objUserHVO.getUserId());
                objUsersVO.setStrFirstName(objUserHVO.getFirstName());
                objUsersVO.setStrLastName(objUserHVO.getLastName());
                objUsersVO.setEoaEmetricsVal(objUserHVO.getEmetricsAccess());

            }
            return objUsersVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#getUserRoleInfo(
     * java.lang.Long)
     */
    @Override
    public List<RoleServiceVO> getUserRoleInfo(Long userSeqId, String language) throws RMDDAOException {
        Session hibernateSession = null;
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);
            String queryString = "FROM GetUsrUserRolesHVO USERROLES LEFT JOIN FETCH USERROLES.getUsrRoles WHERE USERROLES.getUsrUsers.getUsrUsersSeqId = :userSeqId";
            Query query = hibernateSession.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USER_SEQ_ID, userSeqId);
            List<GetUsrUserRolesHVO> userRolesList = query.list();
            List<RoleServiceVO> arlUserRolesVO = null;
            if (RMDCommonUtility.isCollectionNotEmpty(userRolesList)) {
                arlUserRolesVO = new ArrayList<RoleServiceVO>();
                RoleServiceVO objUserRoleVO = null;
                for (GetUsrUserRolesHVO objUsrUsersRoleHVO : userRolesList) {
                    GetUsrRolesHVO objUserRoleHVO = objUsrUsersRoleHVO.getGetUsrRoles();
                    objUserRoleVO = new RoleServiceVO();
                    objUserRoleVO.setGetUsrRolesSeqId(objUserRoleHVO.getGetUsrRolesSeqId());
                    objUserRoleVO.setRoleName(objUserRoleHVO.getRoleName());
                    objUserRoleVO.setRoleDesc(objUserRoleHVO.getRoleName());
                    objUserRoleVO.setStatus(objUserRoleHVO.getStatus());
                    arlUserRolesVO.add(objUserRoleVO);
                }
            }
            return arlUserRolesVO;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, language), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#deleteUserRoles(
     * com.ge.trans.rmd.services.security.service.valueobjects.UserServiceVO)
     */
    @Override
    public int deleteUserRoles(Long userRoleSeqId) throws RMDDAOException {
        Session session = null;
        Transaction transaction = null;
        try {
            deleteFavFilterForUsrRole(userRoleSeqId);
            session = getHibernateSession();
            transaction = session.beginTransaction();

            String queryString = "DELETE FROM GetUsrUserRolesHVO UR WHERE UR.getUsrUserRolesSeqId = :userId";
            Query query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USERID, userRoleSeqId);
            int i = query.executeUpdate();
            transaction.commit();
            session.close();
            return i;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * This method is delete favorite filter for a given userroleseqid from the
     * system.
     * 
     * @param objFavFilterVO
     */
    public void deleteFavFilterForUsrRole(Long UserRoleSeqId) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();

            String queryStringUsrRole = "SELECT USRROLE.GET_USR_USER_ROLES_SEQ_ID FROM GET_USR.GET_USR_USER_ROLES USRROLE WHERE USRROLE.LINK_USERS=:userSeqId";
            Query queryUsrRole = session.createSQLQuery(queryStringUsrRole.toString());
            queryUsrRole.setParameter(RMDCommonConstants.USER_SEQ_ID, UserRoleSeqId);
            List<BigDecimal> usrRoleSeqIds = queryUsrRole.list();
            FavFilterServiceVO objFavFilterVO = new FavFilterServiceVO();
            if (RMDCommonUtility.isCollectionNotEmpty(usrRoleSeqIds)) {
                for (BigDecimal obj : usrRoleSeqIds) {
                    objFavFilterVO.setLinkUsrRoleSeqId(obj.longValue());
                    deleteFavoriteFilter(objFavFilterVO);

                    String queryString = "DELETE FROM GetFilterDetailOMDHVO FD WHERE FD.getUsrUserRolesSeqId.getUsrUserRolesSeqId = :usrRoleSeqId";
                    Query query = session.createQuery(queryString);
                    query.setParameter(RMDCommonConstants.USR_ROLE_SEQ_ID, obj.longValue());
                    query.executeUpdate();

                }
            }

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#saveUserInfo(com
	 * .ge.trans.rmd.services.security.service.valueobjects.UserServiceVO) *
	 * This method is used to save the user information in GET_USR.GET_USR_USERS
	 * table
	 */
	public UserServiceVO saveUserInfo(UserServiceVO objUserServiceVO)
			throws RMDDAOException {
		synchronized (lock){
		Session session = null;
		StringBuffer queryString = new StringBuffer();
		StringBuffer eoaQueryString = new StringBuffer();
		Set<GetUsrUserCustomersHVO> userCustTempSet = null;
		List<String> currAssociatedCustomerList = new ArrayList<String>();
		Transaction trans=null;
		String eoaUpdateStatus=RMDCommonConstants.SUCCESS;
		try {
			session = getHibernateSession(objUserServiceVO.getStrUserName());
			trans=session.beginTransaction();
			GetUsrUsersHVO userHVO = null;
			/* Getting the user object if userseqid is not null */
			if (null != objUserServiceVO
					&& (null != objUserServiceVO.getGetUsrUsersSeqId() && objUserServiceVO
							.getGetUsrUsersSeqId() != 0)) {
				userHVO = (GetUsrUsersHVO) session.get(GetUsrUsersHVO.class,
						objUserServiceVO.getGetUsrUsersSeqId());
			}
			if (null == userHVO) {
				userHVO = new GetUsrUsersHVO();
			} else{
				queryString = new StringBuffer();
				queryString
					.append("DELETE FROM GetUsrUserCustomersHVO userCustomer WHERE userCustomer.getUsrUsersHVO.getUsrUsersSeqId = :userID ");
				if (null != objUserServiceVO.getListCustomer()
						&& !objUserServiceVO.getListCustomer().isEmpty()) {
					queryString
						.append("and userCustomer.getTableBusOrgHVO.objid not in (select objid FROM GetTableBusOrgHVO WHERE orgId in (:customerId)) ");
				}
				Query query = session.createQuery(queryString.toString());
				if (null != userHVO.getGetUsrUsersSeqId()	&& userHVO
						.getGetUsrUsersSeqId() != 0	) {
					query.setLong(RMDCommonConstants.USER_ID,
							userHVO.getGetUsrUsersSeqId());
				}
				if (null != objUserServiceVO.getListCustomer()
						&& !objUserServiceVO.getListCustomer().isEmpty()) {
					query.setParameterList(RMDCommonConstants.CUSTOMER_ID,
							objUserServiceVO.getListCustomer());
				}
				query.executeUpdate();
				session.flush();
				
				
			}
			
			
			/*
			 * Save User Details and contact information in
			 * GET_USR.GET_USR_USERS table
			 */
			userHVO.setFirstName(ESAPI.encoder().decodeForHTML(objUserServiceVO.getStrFirstName()));
			userHVO.setLastName(ESAPI.encoder().decodeForHTML(objUserServiceVO.getStrLastName()));
			userHVO.setUserId(ESAPI.encoder().decodeForHTML(objUserServiceVO.getNewUserIdEntered()));
			userHVO.setUserType(objUserServiceVO.getUserType());
			userHVO.setStatus(objUserServiceVO.getStatus());
			userHVO.setStrLanguage(objUserServiceVO.getStrLanguage());
			//Added by Vamshi 
			userHVO.setEmailId(ESAPI.encoder().decodeForHTML(objUserServiceVO.getStrEmail()));
			GetTableBusOrgHVO customerHVO = null;
			// Update Customer details
			if (null == objUserServiceVO.getCustomerId()
					|| objUserServiceVO.getCustomerId().isEmpty()) {

				customerHVO = null;

			} /*
			 * Getting the customer seqid from Customer Id and then getting the
			 * values from existing HVO
			 */
			else {
				queryString = new StringBuffer();
				queryString
						.append("SELECT OBJID FROM TABLE_BUS_ORG WHERE ORG_ID=:customerId");
				Query query = session.createSQLQuery(queryString.toString());

				if (null != objUserServiceVO.getCustomerId()
						&& !objUserServiceVO.getCustomerId().isEmpty()) {
					query.setParameter(RMDCommonConstants.CUSTOMER_ID,
							objUserServiceVO.getCustomerId());
				}
				String sequence = query.uniqueResult().toString();
				customerHVO = (GetTableBusOrgHVO) session.get(
						GetTableBusOrgHVO.class, Long.valueOf(sequence));
			}
			userHVO.setGetTableBusOrgHVO(customerHVO);			
			userCustTempSet = userHVO.getGetUsrUserCustomers();
			currAssociatedCustomerList = new ArrayList<String>();
			for(GetUsrUserCustomersHVO tempVO : userCustTempSet){
				currAssociatedCustomerList.add(tempVO.getGetTableBusOrgHVO().getOrgId().toString());
			}
			
			if (null != objUserServiceVO.getListCustomer()
					&& !objUserServiceVO.getListCustomer().isEmpty()) {
			
				Iterator<String> custIt = objUserServiceVO.getListCustomer().iterator();
				while(custIt.hasNext()){
					if(currAssociatedCustomerList.contains(custIt.next())){
						custIt.remove();
					}
				}
			}
			if (null != objUserServiceVO.getListCustomer()
					&& !objUserServiceVO.getListCustomer().isEmpty()) {
				queryString = new StringBuffer();
				queryString
						.append("FROM GetTableBusOrgHVO WHERE orgId in (:customerId)");
				Query query = session.createQuery(queryString.toString());

				query.setParameterList(RMDCommonConstants.CUSTOMER_ID,
						objUserServiceVO.getListCustomer());

				List<GetTableBusOrgHVO> customerList = (List<GetTableBusOrgHVO>) query
						.list();
				if (null != customerList) {
					Iterator<GetTableBusOrgHVO> it = customerList.iterator();
					while (it.hasNext()) {
						GetUsrUserCustomersHVO getUsrUserCustomersHVO = new GetUsrUserCustomersHVO();
						getUsrUserCustomersHVO.setGetTableBusOrgHVO(it.next());
						getUsrUserCustomersHVO.setGetUsrUsersHVO(userHVO);
						userHVO.getGetUsrUserCustomers().add(
								getUsrUserCustomersHVO);
					}

				}
			}
			long start = System.currentTimeMillis();
	        Timestamp timestamp  = new Timestamp(start) ;
	        Date calculationDate = new java.util.Date(timestamp.getTime());			
			userHVO.setLastUpdatedBy(objUserServiceVO.getUserId());
			userHVO.setLastUpdatedDate(calculationDate);
			//Added by Murali Medicherla for Rally Id : US226051
			userHVO.setMobileAccess(objUserServiceVO.getMobileAccess());
			userHVO.setEndUserScoring(objUserServiceVO.getEndUserScoring());
			if(null != objUserServiceVO.getOmdEmetricsFlag()
                    && RMDCommonConstants.Y_LETTER_UPPER
                    .equalsIgnoreCase(objUserServiceVO.getOmdEmetricsFlag())){
            userHVO.setEmetricsAccess(objUserServiceVO.getEoaEmetricsVal());
            }else{
                userHVO.setEmetricsAccess("R");
            }
			
			session.saveOrUpdate(userHVO);	
			session.flush();
			/* Added for MDSC Admin changes */ 
			if (RMDCommonConstants.Y_LETTER_UPPER
							.equalsIgnoreCase(objUserServiceVO.getUpdateEOAUser()) &&
					(RMDCommonConstants.Y_LETTER_UPPER
							.equalsIgnoreCase(objUserServiceVO.getOmdCMFlag()) || RMDCommonConstants.Y_LETTER_UPPER
							.equalsIgnoreCase(objUserServiceVO.getOmdMLFlag()) || RMDCommonConstants.Y_LETTER_UPPER
							.equalsIgnoreCase(objUserServiceVO.getOmdCmMlPrevRemoved()))) { 
			    	eoaUpdateStatus=saveUserEOADetails(objUserServiceVO);
					
			}
			if(objUserServiceVO.getEoaOnsiteUser()!= null && objUserServiceVO.getEoaOnsiteUser().equals(RMDCommonConstants.Y_LETTER_UPPER)){
				String userId = null;
				eoaQueryString
				.append("SELECT objid from table_user where web_login=:oldUserId");
		Query query = session.createSQLQuery(eoaQueryString.toString());
		if (RMDCommonUtility.isNullOrEmpty(objUserServiceVO.getOldUserId())) {
			objUserServiceVO.setOldUserId(objUserServiceVO.getNewUserIdEntered());
		}
		if (!RMDCommonUtility.isNullOrEmpty(objUserServiceVO.getOldUserId())) {
			
			query.setParameter(RMDCommonConstants.OLD_USR_ID,
					objUserServiceVO.getOldUserId());
		}
	
		String objid = null;
		if(query.uniqueResult()!=null){
			objid = query.uniqueResult().toString();
		}
		StringBuilder tableEmpUpdateQuery = new StringBuilder();
		StringBuilder tableUserUpdateQuery = new StringBuilder();
		if(!RMDCommonUtility.isNullOrEmpty(objid)){
			tableUserUpdateQuery.append("UPDATE table_user set web_login=:userId where web_login=:oldUserId");
			final Query tableUSerHUpdateQuery = session.createSQLQuery(tableUserUpdateQuery
					.toString());
			tableUSerHUpdateQuery.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getNewUserIdEntered());
			tableUSerHUpdateQuery.setParameter(RMDCommonConstants.OLD_USR_ID, objUserServiceVO.getOldUserId());
			tableUSerHUpdateQuery.executeUpdate();
			tableEmpUpdateQuery.append("UPDATE TABLE_EMPLOYEE SET FIRST_NAME=:firstName,S_FIRST_NAME=:sFirstName,LAST_NAME=:lastName,S_LAST_NAME=:sLastName,");
			tableEmpUpdateQuery.append("E_MAIL=:emailId WHERE EMPLOYEE2USER=:objId");
			final Query tableEmpHUpdateQuery = session.createSQLQuery(tableEmpUpdateQuery
					.toString());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.FIRST_NAME, objUserServiceVO.getStrFirstName());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.S_FIRST_NAME, objUserServiceVO.getStrFirstName().toUpperCase());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.LAST_NAME, objUserServiceVO.getStrLastName());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.S_LAST_NAME, objUserServiceVO.getStrLastName().toUpperCase());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.EMAIL_ID, objUserServiceVO.getStrEmail());
			tableEmpHUpdateQuery.setParameter(RMDCommonConstants.OBJ_ID, objid);
			tableEmpHUpdateQuery.executeUpdate();
		}
			
			}
			
			/*End of MDSC Admin*/
			BeanUtilsBean.getInstance().getConvertUtils().register(false,false,0);
			UserServiceVO userServiceVOPersisted = new UserServiceVO();
			BeanUtils.copyProperties(userServiceVOPersisted, userHVO);
			userServiceVOPersisted
					.setUsrRoleId(objUserServiceVO.getUsrRoleId());
			if(RMDCommonConstants.SUCCESS.equalsIgnoreCase(eoaUpdateStatus)){ //modified for MDSC Admin changes
				userServiceVOPersisted.setEoaTableUpdated(RMDCommonConstants.TRUE);
				trans.commit();
			}
			else{
				userServiceVOPersisted.setEoaTableUpdated(RMDCommonConstants.FALSE);	
				trans.rollback();
			}
			
			return userServiceVOPersisted;
		} catch (ConstraintViolationException ex) {
			if(trans!=null)
			{
				trans.rollback();
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.USER_MANAGEMENT_CONCURENCY_ERROR_CODE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objUserServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.MINOR_ERROR);
		} catch (RMDDAOConnectionException ex) {
			if(trans!=null)
			{
				trans.rollback();
			}
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objUserServiceVO.getStrLanguage()), ex,
					RMDCommonConstants.FATAL_ERROR);
		}catch (Exception e) {
			String errorCode=RMDCommonConstants.EMPTY_STRING;
			if(trans!=null)
			{
				trans.rollback();
			}
			if(e.getMessage().contains(RMDServiceConstants.EOA_ALIAS_EXISTS)){
                errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.EOA_ALIAS_EXISTS);
            }else
			if(e.getMessage().contains(RMDServiceConstants.USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES)){
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES);
			}
			else if(e.getMessage().contains(RMDServiceConstants.EMETRICS_UPDATE_ERROR_MSG))
				{
				errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.EMETRICS_UPDATE_ERROR_MSG);
				}
			else{
				errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
			}
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							objUserServiceVO.getStrLanguage()), e,
					RMDCommonConstants.MAJOR_ERROR);
		}  finally {
			releaseSession(session);
		}
		}
	}

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#saveUserRoles(com
     * .ge.trans.rmd.services.security.service.valueobjects.UserServiceVO,
     * java.util.List)
     */
    @Override
    public int saveUserRoles(UserServiceVO userServiceVO) throws RMDDAOException {
        Session session = null;
        StringBuilder queryString = new StringBuilder();
       
        try {

            List<Long> userRoles = null;
            session = getHibernateSession(userServiceVO.getStrUserName());
            queryString.append(
                    "FROM GetUsrUserRolesHVO USERROLES LEFT JOIN FETCH USERROLES.getUsrRoles WHERE USERROLES.getUsrUsers.getUsrUsersSeqId = :userSeqId");
            if (RMDCommonUtility.isCollectionNotEmpty(userServiceVO.getRoleList())) {
                queryString.append(" and USERROLES.getUsrRoles.getUsrRolesSeqId not in (:roleId)");
                userRoles = userServiceVO.getRoleList();
            }
            Query query = session.createQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.USER_SEQ_ID, userServiceVO.getGetUsrUsersSeqId());
            if (RMDCommonUtility.isCollectionNotEmpty(userServiceVO.getRoleList())) {
                query.setParameterList(RMDCommonConstants.ROLE_ID, userRoles);
            }
            List<GetUsrUserRolesHVO> removableUserRolesList = query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(removableUserRolesList)) {

                for (GetUsrUserRolesHVO objUsrUsersRoleHVO : removableUserRolesList) {
                    if (null != objUsrUsersRoleHVO.getGetUsrRoles()) {
                        deleteUserRoles(
                                RMDCommonUtility.convertObjectToLong(objUsrUsersRoleHVO.getGetUsrUserRolesSeqId()));
                    }
                }
            }

            queryString.delete(0, queryString.length());

            queryString.append(
                    "FROM GetUsrUserRolesHVO USERROLES LEFT JOIN FETCH USERROLES.getUsrRoles WHERE USERROLES.getUsrUsers.getUsrUsersSeqId =:userSeqId");
            query = session.createQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.USER_SEQ_ID, userServiceVO.getGetUsrUsersSeqId());

            List<GetUsrUserRolesHVO> UserRolesList = query.list();
            queryString.delete(0, queryString.length());

            if (RMDCommonUtility.isCollectionNotEmpty(UserRolesList)) {
                for (GetUsrUserRolesHVO objUsrUsersRoleHVO : UserRolesList) {
                    if (null != objUsrUsersRoleHVO.getGetUsrRoles()) {
                        GetUsrRolesHVO objUserRoleHVO = objUsrUsersRoleHVO.getGetUsrRoles();
                        userRoles.remove(objUserRoleHVO.getGetUsrRolesSeqId());
                    }
                }
            }

            if (RMDCommonUtility.isCollectionNotEmpty(userRoles)) {
                GetUsrUserRolesHVO objUsrUserRolesHVO = null;
                for (Long roleIdToInsert : userRoles) {

                    objUsrUserRolesHVO = new GetUsrUserRolesHVO();
                    GetUsrRolesHVO userRolesHVO = (GetUsrRolesHVO) session.get(GetUsrRolesHVO.class, roleIdToInsert);
                    GetUsrUsersHVO userHVO = (GetUsrUsersHVO) session.get(GetUsrUsersHVO.class,
                            userServiceVO.getGetUsrUsersSeqId());
                    objUsrUserRolesHVO.setGetUsrRoles(userRolesHVO);
                    objUsrUserRolesHVO.setGetUsrUsers(userHVO);
                    objUsrUserRolesHVO.setStrLanguage(userServiceVO.getStrLanguage());
                    session.save(objUsrUserRolesHVO);
                    session.flush();

                }
            }
            
            long start = System.currentTimeMillis();
            Timestamp timestamp  = new Timestamp(start) ;
            Date calculationDate = new java.util.Date(timestamp.getTime());
            String queryStr = null;
            queryStr = "FROM GetUsrUsersHVO WHERE getUsrUsersSeqId =:userSeqId";
            query = session.createQuery(queryStr.toString());
            query.setParameter(RMDCommonConstants.USER_SEQ_ID, userServiceVO.getGetUsrUsersSeqId());
            GetUsrUsersHVO editedUser = (GetUsrUsersHVO) query.list().get(0);
            editedUser.setLastUpdatedBy(userServiceVO.getStrUserName());
            editedUser.setLastUpdatedDate(calculationDate);
            session.update(editedUser);
            session.flush();
            
            
            
            
            
            return UPDATE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * Method updates the user table with the customer selected by user at
     * Preference modal and the preferences
     */
    @Override
    @SuppressWarnings("rawtypes")
    public int saveUserPreferences(List<GetUsrUserPreferenceVO> userPreferenceList,
            GetUsrUserPreferenceVO userPreferenceVOobj) throws RMDDAOException {
        Session session = getHibernateSession(userPreferenceVOobj.getStrUserName());
        List lstResult;
        Criteria criteria = null;
        String custId = null;
        Long customerId = null;
        Long userId = null;
       
        StringBuilder queryString = new StringBuilder();
        GetTableBusOrgHVO customers = null;
        try {
            for (GetUsrUserPreferenceVO userPreferenceVO : userPreferenceList) {

                GetUsrUserPreferenceHVO getUsrUserPreferenceObj = (GetUsrUserPreferenceHVO) session
                        .get(GetUsrUserPreferenceHVO.class, userPreferenceVO.getGetUsrUserPreferenceSeqId());
                getUsrUserPreferenceObj.setUserPreferernceType(userPreferenceVO.getUserPreferernceType());
                getUsrUserPreferenceObj.setUserPreferenceValue(userPreferenceVO.getUserPreferenceValue());
                custId = userPreferenceVO.getCustomerId();
                userId = userPreferenceVO.getUserId();
                session.saveOrUpdate(getUsrUserPreferenceObj);
                session.flush();

            }

            if (custId != null && !custId.isEmpty()) {
                criteria = session.createCriteria(GetTableBusOrgHVO.class);
                criteria.add(Restrictions.eq(RMDCommonConstants.ORGID, custId));
                lstResult = criteria.list();
                if (!lstResult.isEmpty()) {
                    customers = (GetTableBusOrgHVO) lstResult.get(0);
                    customerId = customers.getObjid();
                }
            } else {
                customerId = null;
            }

            queryString = new StringBuilder();
            queryString.append(
                    "UPDATE get_usr.get_usr_users SET link_customer=:customerId WHERE get_usr_users_seq_id=:userSeqId");
            Query userQry = session.createSQLQuery(queryString.toString());
            userQry.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId, Hibernate.LONG);
            userQry.setParameter(RMDCommonConstants.USER_SEQ_ID, userId);

            userQry.executeUpdate();

            return UPDATE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_SAVE_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userPreferenceVOobj.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_SAVE_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, userPreferenceVOobj.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#createUserPreference
     * (com.ge.trans.rmd.services.security.service.valueobjects.UserServiceVO)
     * This method is used to create user preference when a new user will be
     * added
     */
    @Override
    public UserServiceVO createUserPreference(UserServiceVO objUserServiceVO) throws RMDDAOException {
        Session session = getHibernateSession(objUserServiceVO.getStrUserName());
        StringBuilder queryString = new StringBuilder();

        try {
            if (null == objUserServiceVO.getGetUsrUsersSeqId() || objUserServiceVO.getGetUsrUsersSeqId() == 0) {

                /* Getting the user sequence id for data processing */
                queryString.append("SELECT GET_USR_USERS_SEQ_ID FROM GET_USR.GET_USR_USERS WHERE USER_ID=:userId");
                Query query = session.createSQLQuery(queryString.toString());

                if (null != objUserServiceVO.getUserId() && !objUserServiceVO.getUserId().isEmpty()) {
                    query.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getUserId());
                }
                String sequence = query.uniqueResult().toString();
                objUserServiceVO.setGetUsrUsersSeqId(Long.valueOf(sequence));
            }
            if (null != objUserServiceVO.getGetUsrUsersSeqId() && objUserServiceVO.getGetUsrUsersSeqId() != 0) {
                queryString = new StringBuilder();
                /*
                 * Fetching the user preference if any associated with that user
                 */
                queryString.append(
                        "SELECT GET_USR_USER_PREFERENCE_SEQ_ID,USER_PREFERENCE_TYPE,USER_PREFERENCE_VALUE FROM GET_USR.GET_USR_USER_PREFERENCE WHERE LINK_USERS=:userId ");

                Query query = session.createSQLQuery(queryString.toString());

                if (null != objUserServiceVO.getGetUsrUsersSeqId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objUserServiceVO.getGetUsrUsersSeqId())) {
                    query.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getGetUsrUsersSeqId());
                }

                /*
                 * Checking if there is any existing user preferences associated
                 * with that user If no preferences is associated with user we
                 * are creating new preferences for that user
                 */
                List<Object[]> arlUserPreference = query.list();
				String[] preferenceType = {
						RMDCommonConstants.PREFERENCE_ROLE,
						RMDCommonConstants.PREFERENCE_TIMEZONE,
						RMDCommonConstants.PREFERENCE_LANGUAGE,
						RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM};
                if (!RMDCommonUtility.isCollectionNotEmpty(arlUserPreference)) {
                    
                    queryString = new StringBuilder();
                    for (int i = 0; i < preferenceType.length; i++) {

                        queryString = new StringBuilder();
                        queryString.append(" INSERT INTO GET_USR.GET_USR_USER_PREFERENCE ");
                        queryString.append(
                                " (GET_USR_USER_PREFERENCE_SEQ_ID,USER_PREFERENCE_TYPE,USER_PREFERENCE_VALUE,LINK_USERS, ROW_VERSION,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE) ");
                        queryString.append(
                                " VALUES(GET_USR.GET_USR_USER_PREFERENCE_SEQ.NEXTVAL,:prefType,:prefValue,:userId,0,:userName,SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

                        Query prefQuery = session.createSQLQuery(queryString.toString());

                        prefQuery.setParameter(RMDCommonConstants.PREF_TYPE, preferenceType[i]);
                        if (preferenceType[i].equals(RMDCommonConstants.PREFERENCE_ROLE)) {
                            if (null != objUserServiceVO.getUsrRoleId() && objUserServiceVO.getUsrRoleId() != 0) {
                                prefQuery.setParameter(RMDCommonConstants.PREF_VALUE, objUserServiceVO.getUsrRoleId(),
                                        Hibernate.LONG);
                            } else {
                                prefQuery.setParameter(RMDCommonConstants.PREF_VALUE, null);
                            }
                        } else if (preferenceType[i].equals(RMDCommonConstants.PREFERENCE_TIMEZONE)) {
                            prefQuery.setParameter(RMDCommonConstants.PREF_VALUE, RMDCommonConstants.DateConstants.GMT);
                        } else if (preferenceType[i].equals(RMDCommonConstants.PREFERENCE_LANGUAGE)) {
                            prefQuery.setParameter(RMDCommonConstants.PREF_VALUE, objUserServiceVO.getStrLanguage());
                        }else if (preferenceType[i]
								.equals(RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM)) {
							prefQuery.setParameter(
									RMDCommonConstants.PREF_VALUE,
									objUserServiceVO.getUom());
						}
                        prefQuery.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getGetUsrUsersSeqId(),
                                Hibernate.LONG);
                        prefQuery.setParameter(RMDCommonConstants.USERNAME, objUserServiceVO.getStrUserName());
                        prefQuery.executeUpdate();
                    }
                } else {
					boolean isUOMCreated=false;
                    for (int i = 0; i < arlUserPreference.size(); i++) {
                        Object prefObj[] = arlUserPreference.get(i);
						if(RMDCommonUtility.convertObjectToString(
								prefObj[1]).equalsIgnoreCase(
										RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM)){
							isUOMCreated=true;
						}
                        if (RMDCommonUtility.convertObjectToString(prefObj[1])
                                .equalsIgnoreCase(RMDCommonConstants.PREFERENCE_ROLE)
                                || RMDCommonUtility.convertObjectToString(prefObj[1])
                                        .equalsIgnoreCase(RMDCommonConstants.PREFERENCE_LANGUAGE)||RMDCommonUtility.convertObjectToString(
												prefObj[1]).equalsIgnoreCase(
														RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM)) {
                            if (null != prefObj[2] && !RMDCommonUtility.convertObjectToString(prefObj[2]).isEmpty()) {
                                queryString = new StringBuilder();
                                queryString.append(
                                        " UPDATE GET_USR.GET_USR_USER_PREFERENCE SET USER_PREFERENCE_VALUE=:prefValue,LAST_UPDATED_BY=:userName,LAST_UPDATED_DATE=SYSTIMESTAMP WHERE GET_USR_USER_PREFERENCE_SEQ_ID=:prefSeqId");

                                Query prefQuery = session.createSQLQuery(queryString.toString());
                                if (RMDCommonUtility.convertObjectToString(prefObj[1])
                                        .equalsIgnoreCase(RMDCommonConstants.PREFERENCE_ROLE)) {
                                    prefQuery.setParameter(RMDCommonConstants.PREF_VALUE,
                                            objUserServiceVO.getUsrRoleId());
                                } else if (RMDCommonUtility.convertObjectToString(prefObj[1])
                                        .equalsIgnoreCase(RMDCommonConstants.PREFERENCE_LANGUAGE)) {
                                    prefQuery.setParameter(RMDCommonConstants.PREF_VALUE,
                                            objUserServiceVO.getStrLanguage());
                                }else if (RMDCommonUtility
										.convertObjectToString(prefObj[1])
										.equalsIgnoreCase(
												RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM)) {
									prefQuery.setParameter(
											RMDCommonConstants.PREF_VALUE,
											objUserServiceVO.getUom());
								}

                                if (null != RMDCommonUtility.convertObjectToString(prefObj[0])
                                        && !RMDCommonUtility.convertObjectToString(prefObj[0]).isEmpty()) {
                                    prefQuery.setParameter(RMDCommonConstants.PREFSEQID,
                                            RMDCommonUtility.convertObjectToString(prefObj[0]));
                                }
                                prefQuery.setParameter(RMDCommonConstants.USERNAME, objUserServiceVO.getStrUserName());
                                prefQuery.executeUpdate();

                            }
                        }
                    }
					if(!isUOMCreated&&null!=objUserServiceVO.getUom()&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(objUserServiceVO.getUom())){
						queryString = new StringBuilder();
						queryString
								.append(" INSERT INTO GET_USR.GET_USR_USER_PREFERENCE ");
						queryString
								.append(" (GET_USR_USER_PREFERENCE_SEQ_ID,USER_PREFERENCE_TYPE,USER_PREFERENCE_VALUE,LINK_USERS, ROW_VERSION,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY,CREATION_DATE) ");
						queryString
								.append(" VALUES(GET_USR.GET_USR_USER_PREFERENCE_SEQ.NEXTVAL,:prefType,:prefValue,:userId,0,:userName,SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

						Query prefQuery = session.createSQLQuery(queryString
								.toString());
						prefQuery.setParameter(RMDCommonConstants.PREF_TYPE,
								RMDCommonConstants.PREFERENCE_MEASUREMENT_SYSTEM);
						prefQuery.setParameter(
								RMDCommonConstants.PREF_VALUE,
								objUserServiceVO.getUom());
						prefQuery.setParameter(RMDCommonConstants.USERID,
								objUserServiceVO.getGetUsrUsersSeqId(),
								Hibernate.LONG);
						prefQuery.setParameter(RMDCommonConstants.USERNAME,
								objUserServiceVO.getStrUserName());
						prefQuery.executeUpdate();
					}
                }
            }
            UserServiceVO userServiceVOPersisted = new UserServiceVO();
            userServiceVOPersisted.setGetUsrUsersSeqId(objUserServiceVO.getGetUsrUsersSeqId());
            userServiceVOPersisted.setUsrRoleId(objUserServiceVO.getUsrRoleId());
            return userServiceVOPersisted;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_SAVE_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.ERROR_SAVE_USER_PREFERENCES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#saveUserRole(com
     * .ge.trans.rmd.services.security.service.valueobjects.UserServiceVO) This
     * method is used to save a new or update an existing role when a user is
     * created or updated
     */
    @Override
    public UserServiceVO saveUserRole(UserServiceVO objUserServiceVO) throws RMDDAOException {
        Session session = getHibernateSession();
        StringBuilder queryString = new StringBuilder();
        try {

            if (null != objUserServiceVO.getUsrRoleId() && objUserServiceVO.getUsrRoleId() != 0) {
                queryString = new StringBuilder();
                /* Getting all the role sequence id associated with that user */
                queryString.append(
                        " SELECT GET_USR_USER_ROLES_SEQ_ID FROM GET_USR.GET_USR_USER_ROLES USRROLE WHERE USRROLE.LINK_USERS=:userId ");
                Query query = session.createSQLQuery(queryString.toString());
                if (null != objUserServiceVO.getGetUsrUsersSeqId()
                        && !RMDCommonConstants.EMPTY_STRING.equals(objUserServiceVO.getGetUsrUsersSeqId())) {
                    query.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getGetUsrUsersSeqId(),
                            Hibernate.LONG);
                }
                List<Object[]> usrRoleSeqIdLst = query.list();
                /*
                 * Checking whether any role is associated with that particular
                 * user if role is there we are updating the role otherwise
                 * inserting a new role for that user
                 */
                if (RMDCommonUtility.isCollectionNotEmpty(usrRoleSeqIdLst)) {
                    queryString = new StringBuilder();
                    queryString.append(
                            "  update GET_USR.GET_USR_USER_ROLES USRROLE set USRROLE.LINK_ROLES=:roleId where GET_USR_USER_ROLES_SEQ_ID=:roleseqId ");
                    Query roleQuery = session.createSQLQuery(queryString.toString());
                    roleQuery.setParameter(RMDCommonConstants.ROLE_ID, objUserServiceVO.getUsrRoleId(), Hibernate.LONG);
                    roleQuery.setParameter(RMDCommonConstants.ROLE_SEQ_ID,
                            RMDCommonUtility.convertObjectToString(usrRoleSeqIdLst.get(0)));
                    roleQuery.executeUpdate();
                } else {
                    queryString = new StringBuilder();
                    queryString.append(" INSERT INTO GET_USR.GET_USR_USER_ROLES ");
                    queryString.append(
                            " (GET_USR_USER_ROLES_SEQ_ID,LINK_USERS,LINK_ROLES,ROW_VERSION,LAST_UPDATED_BY,LAST_UPDATED_DATE,CREATED_BY, CREATION_DATE) ");
                    queryString.append(
                            " VALUES(GET_USR.GET_USR_USER_ROLES_SEQ.NEXTVAL,:userId,:roleId, 0,'ADMIN',SYSTIMESTAMP,'ADMIN',SYSTIMESTAMP) ");
                    Query roleQuery = session.createSQLQuery(queryString.toString());
                    roleQuery.setParameter(RMDCommonConstants.USERID, objUserServiceVO.getGetUsrUsersSeqId(),
                            Hibernate.LONG);
                    roleQuery.setParameter(RMDCommonConstants.ROLE_ID, objUserServiceVO.getUsrRoleId(), Hibernate.LONG);
                    roleQuery.executeUpdate();
                }

            }

            UserServiceVO userServiceVOPersisted = new UserServiceVO();
            userServiceVOPersisted.setGetUsrUsersSeqId(objUserServiceVO.getGetUsrUsersSeqId());
            userServiceVOPersisted.setUsrRoleId(objUserServiceVO.getUsrRoleId());
            return userServiceVOPersisted;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_ROLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SAVE_ROLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    /*
     * (non-Javadoc)
     * @see
     * com.ge.trans.rmd.services.admin.dao.intf.UserDAOIntf#saveFilterData(com
     * .ge.trans.rmd.services.security.service.valueobjects.FavFilterServiceVO)
     * This method is used to set the favourite filter for particular user for a
     * particular screen
     */
    @Override
    public int saveFilterData(FavFilterServiceVO objFavFilterVO) throws RMDDAOException {
        Session session = null;

        try {
            session = getHibernateSession(objFavFilterVO.getStrUserName());
            GetFilterDetailOMDHVO objFilterDetailHVO = null;
            if (null != objFavFilterVO.getFilterId() && objFavFilterVO.getFilterId() != 0) {
                deleteFavoriteFilter(objFavFilterVO);
                objFilterDetailHVO = (GetFilterDetailOMDHVO) session.get(GetFilterDetailOMDHVO.class,
                        objFavFilterVO.getFilterId());
            } else {
                /* For FilterDetailHVO */
                deleteFavoriteFilter(objFavFilterVO);
                objFilterDetailHVO = getFilterDetail(objFavFilterVO, session);
            }
            /* For FilterDetailHVO */

            /* For ColumnDetailHVO */

            List<GetColumnDetailOMDHVO> objColumnDetailHVOLst = null;
            objColumnDetailHVOLst = getColumnDetail(objFavFilterVO, session);

            /* For ColumnDetailHVO */

            /* For ResourceDetailHVO */
            GetResourceDetailOMDHVO objResourceDetailHVO = null;
            objResourceDetailHVO = getResourceDetail(objFavFilterVO, session);
            objFilterDetailHVO.setResourceDetail(objResourceDetailHVO);
            /* For ResourceDetailHVO */

            /* For UsrFilterHVO */
            GetUsrFilterOMDHVO objUsrFilterHVO = null;
            List<GetUsrFilterOMDHVO> objUsrFilterHVOLst = new ArrayList<GetUsrFilterOMDHVO>();
            for (int i = 0; i < objColumnDetailHVOLst.size(); i++) {
                objUsrFilterHVO = new GetUsrFilterOMDHVO();
                objUsrFilterHVO.setGetColumnDetailOMDHVO(objColumnDetailHVOLst.get(i));
                objUsrFilterHVO.setGetFilterDetailOMDHVO(objFilterDetailHVO);

                session.save(objUsrFilterHVO);
                session.flush();

                objUsrFilterHVOLst.add(objUsrFilterHVO);
            }
            /* For UsrFilterHVO */

            /* For Inserting in Individual Tables */
            GetUsrFilterDetailTxtOMDHVO objUsrFilterDetailTxtHVO = null;
            GetUsrFilterDetailDatetimeOMDHVO objUsrFilterDetailDatetimeHVO = null;
            GetUsrFilterDetailNbrOMDHVO objUsrFilterDetailNbrHVO = null;
            GetUsrFilterDetailDecimalOMDHVO objUsrFilterDetailDecimalHVO = null;
            if (null != objFavFilterVO.getDataLst() && !objFavFilterVO.getDataLst().isEmpty()) {
                for (int i = 0; i < objFavFilterVO.getDataLst().size(); i++) {
                    for (int j = 0; j < objFavFilterVO.getDataLst().get(i).length; j++) {

                        /* For GetUsrFilterDetailTxtOMDHVO */
                        if (objFavFilterVO.getColumnType().get(i).equalsIgnoreCase(RMDCommonConstants.TEXT)) {
                            objUsrFilterDetailTxtHVO = new GetUsrFilterDetailTxtOMDHVO();
                            objUsrFilterDetailTxtHVO.setGetUsrFilterOMDHVO(objUsrFilterHVOLst.get(i));
                            objUsrFilterDetailTxtHVO.setFromFilterValue(
                                    RMDCommonUtility.convertObjectToString(objFavFilterVO.getDataLst().get(i)[j]));
                            objUsrFilterDetailTxtHVO.setToFilterValue(null);

                            session.saveOrUpdate(objUsrFilterDetailTxtHVO);
                            session.flush();
                        }
                        /* For GetUsrFilterDetailTxtOMDHVO */
                        /* For GetUsrFilterDetailNbrOMDHVO */
                        if (objFavFilterVO.getColumnType().get(i).equalsIgnoreCase(RMDCommonConstants.NUMBER)) {
                            objUsrFilterDetailNbrHVO = new GetUsrFilterDetailNbrOMDHVO();
                            objUsrFilterDetailNbrHVO.setGetUsrFilterOMDHVO(objUsrFilterHVOLst.get(i));
                            objUsrFilterDetailNbrHVO.setFromFilterValue(
                                    RMDCommonUtility.convertObjectToLong(objFavFilterVO.getDataLst().get(i)[j]));
                            objUsrFilterDetailNbrHVO.setToFilterValue(null);

                            session.saveOrUpdate(objUsrFilterDetailNbrHVO);
                            session.flush();
                        }
                        /* For GetUsrFilterDetailNbrOMDHVO */
                        /* For GetUsrFilterDetailDatetimeOMDHVO */
                        if (objFavFilterVO.getColumnType().get(i).equalsIgnoreCase(RMDCommonConstants.DATE_TIME)) {
                            objUsrFilterDetailDatetimeHVO = new GetUsrFilterDetailDatetimeOMDHVO();
                            objUsrFilterDetailDatetimeHVO.setGetUsrFilterOMDHVO(objUsrFilterHVOLst.get(i));
                            try {
                                objUsrFilterDetailDatetimeHVO.setFromFilterValue(RMDCommonUtility.stringToDate(
                                        RMDCommonUtility.convertObjectToString(objFavFilterVO.getDataLst().get(i)[j]),
                                        RMDCommonConstants.DateConstants.DECODER_DATE_FORMAT));
                            } catch (Exception e) {
                                LOG.error("Exception while parsing date: ", e);
                            }
                            objUsrFilterDetailDatetimeHVO.setToFilterValue(null);

                            session.saveOrUpdate(objUsrFilterDetailDatetimeHVO);
                            session.flush();
                        }
                        /* For GetUsrFilterDetailDatetimeOMDHVO */
                        /* For GetUsrFilterDetailDecimalOMDHVO */
                        if (objFavFilterVO.getColumnType().get(i).equalsIgnoreCase(RMDCommonConstants.DECIMAL)) {
                            objUsrFilterDetailDecimalHVO = new GetUsrFilterDetailDecimalOMDHVO();
                            objUsrFilterDetailDecimalHVO.setGetUsrFilterOMDHVO(objUsrFilterHVOLst.get(i));
                            objUsrFilterDetailDecimalHVO.setFromFilterValue(
                                    RMDCommonUtility.convertObjtoBigDecimal(objFavFilterVO.getDataLst().get(i)[j]));
                            objUsrFilterDetailDecimalHVO.setToFilterValue(null);

                            session.saveOrUpdate(objUsrFilterDetailDecimalHVO);
                            session.flush();
                        }
                        /* For GetUsrFilterDetailDecimalOMDHVO */
                    }
                }
            }
            /* For Inserting in Individual Tables */

            return UPDATE_SUCCESS;
        } catch (RMDDAOConnectionException ex) {

            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FAVORITE_FITLER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);

        } catch (Exception e) {

            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FAVORITE_FITLER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);

        } finally {
            releaseSession(session);
        }
    }

    /**
     * this method will delete favorite filter from database
     */
    @Override
    public void deleteFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDDAOException {
        Session session = null;
        Transaction transaction = null;
        Iterator<GetUsrFilterOMDHVO> itr = null;
        Set<GetUsrFilterOMDHVO> getUsrFilterHVO = null;
        GetUsrFilterOMDHVO usrFilterHVO = null;
        String sequence = null;
        String filterId = RMDCommonUtility.convertObjectToString(objFavFilterVO.getFilterId());
        Long userRoleSeqId = objFavFilterVO.getLinkUsrRoleSeqId();
        String screenName = objFavFilterVO.getScreenName();
        try {
            session = getHibernateSession();
            transaction = session.beginTransaction();
            String queryString = null;
            Query query = null;

            if (null != screenName && userRoleSeqId != null && filterId == null) {
                sequence = getFilterIDByScreenName(objFavFilterVO.getScreenNameFilter(), session);
                queryString = "FROM GetFilterDetailOMDHVO FD WHERE FD.filterId = :filterId";

                query = session.createQuery(queryString);
                query.setParameter(RMDCommonConstants.FILTER_ID, RMDCommonUtility.convertObjectToLong(sequence));

            } else if (filterId != null) {
                queryString = "FROM GetFilterDetailOMDHVO FD WHERE FD.filterId = :filterId";

                query = session.createQuery(queryString);
                query.setParameter(RMDCommonConstants.FILTER_ID, Long.parseLong(filterId));

            } else if (null == screenName && userRoleSeqId != null && filterId == null) {
                queryString = "FROM GetFilterDetailOMDHVO FD WHERE FD.getUsrUserRolesSeqId.getUsrUserRolesSeqId= :roleSeqId";

                query = session.createQuery(queryString);
                query.setParameter("roleSeqId", userRoleSeqId);
            }

            List<GetFilterDetailOMDHVO> arlFilterDetail = query.list();

            /*
             * first we have to delete in GetUsrFilterOMDHVO and then we have to
             * delete from GetFilterDetailOMDHVO
             */

            if (RMDCommonUtility.isCollectionNotEmpty(arlFilterDetail)) {
                for (GetFilterDetailOMDHVO filterDetailHVO : arlFilterDetail) {

                    getUsrFilterHVO = filterDetailHVO.getGetUsrFilterOMDHVOs();

                    if (null != getUsrFilterHVO && false == getUsrFilterHVO.isEmpty()) {

                        itr = getUsrFilterHVO.iterator();

                        while (itr.hasNext()) {
                            usrFilterHVO = itr.next();
                            session.delete(usrFilterHVO);
                        }
                    }
                }
            }

            transaction.commit();
            session.close();

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELETE_FAVORITE_FILTER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_DELETE_FAVORITE_FILTER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }

    /**
     * fetch favorite filter associated to a user
     */
    @Override
    public FetchFavFilterServiceVO fetchFavoriteFilter(FavFilterServiceVO objFavFilterVO) throws RMDDAOException {

        Session session = null;
        Long filterId = null;
        Long usrRoleSeqId = null;
        String filterName = null;
        FetchFavFilterServiceVO fetchFilterVO = new FetchFavFilterServiceVO();
        Map<String, String> columnTypeDetails = new HashMap<String, String>();
        Set<GetUsrFilterOMDHVO> getUsrFilterHVO = null;
        Iterator<GetUsrFilterOMDHVO> itr = null;
        GetUsrFilterOMDHVO usrFilterHVO = null;

        List<FavFilterDetailServiceVO> favFilterDetailServiceVO = new ArrayList<FavFilterDetailServiceVO>();
        FavFilterDetailServiceVO eachFavFilterDetailVO = null;
        List<FilterDetailVO> filterDetailVOList = new ArrayList<FilterDetailVO>();
        try {
            session = getHibernateSession();
            String queryString = null;
            Query query = null;

            if (null != objFavFilterVO.getFilterId()) {
                queryString = "FROM GetFilterDetailOMDHVO FD WHERE FD.filterId = :filterId";

                query = session.createQuery(queryString);
                query.setParameter(RMDCommonConstants.FILTER_ID, objFavFilterVO.getFilterId());
            } else if (null != objFavFilterVO.getLinkUsrRoleSeqId()) {
                queryString = "FROM GetFilterDetailOMDHVO FD WHERE FD.getUsrUserRolesSeqId.getUsrUserRolesSeqId = :usrRoleSeqId";

                query = session.createQuery(queryString);
                query.setParameter(RMDCommonConstants.USR_ROLE_SEQ_ID, objFavFilterVO.getLinkUsrRoleSeqId());
            }

            List<GetFilterDetailOMDHVO> arlFilterDetails = query.list();

            if (RMDCommonUtility.isCollectionNotEmpty(arlFilterDetails)) {
                String columnType = null;
                String columnName = null;
                for (GetFilterDetailOMDHVO getFilterDetailHVO : arlFilterDetails) {

                    if (null == usrRoleSeqId) {
                        usrRoleSeqId = RMDCommonUtility.convertObjectToLong(
                                getFilterDetailHVO.getGetUsrUserRolesSeqId().getGetUsrUserRolesSeqId());
                    }
                    filterId = getFilterDetailHVO.getFilterId();
                    filterName = getFilterDetailHVO.getFilterName();

                    eachFavFilterDetailVO = new FavFilterDetailServiceVO();

                    eachFavFilterDetailVO.setFilterId(filterId);
                    eachFavFilterDetailVO.setFilterName(filterName);

                    filterDetailVOList = new ArrayList<FilterDetailVO>();

                    getUsrFilterHVO = getFilterDetailHVO.getGetUsrFilterOMDHVOs();

                    if (null != getUsrFilterHVO && false == getUsrFilterHVO.isEmpty()) {

                        itr = getUsrFilterHVO.iterator();

                        while (itr.hasNext()) {

                            usrFilterHVO = itr.next();

                            columnType = usrFilterHVO.getGetColumnDetailOMDHVO().getColumnTypeCd();
                            columnType = RMDCommonUtility.getFavFilterColumnTypeValue(columnType);
                            columnName = usrFilterHVO.getGetColumnDetailOMDHVO().getColumnName();
                            columnTypeDetails.put(columnName, columnType);

                            if (columnType.equalsIgnoreCase(RMDCommonConstants.TEXT)) {
                                filterDetailVOList
                                        .addAll(getFilterDetailTxtList(usrFilterHVO.getGetUsrFilterDetailTxtOMDHVOs()));
                            }

                            if (columnType.equalsIgnoreCase(RMDCommonConstants.NUMBER)) {
                                filterDetailVOList
                                        .addAll(getFilterDetailNbrList(usrFilterHVO.getGetUsrFilterDetailNbrOMDHVOs()));
                            }

                            if (columnType.equalsIgnoreCase(RMDCommonConstants.DATE_TIME)) {
                                filterDetailVOList.addAll(getFilterDetailDatetimeList(
                                        usrFilterHVO.getGetUsrFilterDetailDatetimeOMDHVOs()));
                            }

                            if (columnType.equalsIgnoreCase(RMDCommonConstants.DECIMAL)) {
                                filterDetailVOList.addAll(
                                        getFilterDetailDecimalList(usrFilterHVO.getGetUsrFilterDetailDecimalOMDHVOs()));
                            }

                        }
                    }
                    eachFavFilterDetailVO.setFilterDetail(filterDetailVOList);
                    favFilterDetailServiceVO.add(eachFavFilterDetailVO);

                }
            }

            fetchFilterVO.setFilterDetail(favFilterDetailServiceVO);
            fetchFilterVO.setUsrRoleSeqId(usrRoleSeqId);

        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_FAVORITE_FILTER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_FAVORITE_FILTER);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objFavFilterVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

        return fetchFilterVO;
    }

    /**
     * fetches FavFilterDetailServiceVO from GetUsrFilterDetailTxtHVO set
     */
    public List<FilterDetailVO> getFilterDetailTxtList(Set<GetUsrFilterDetailTxtOMDHVO> usrFilterDetailTxtSet) {
        List<FilterDetailVO> favFilterDetailLst = new ArrayList<FilterDetailVO>();
        GetUsrFilterDetailTxtOMDHVO eachDetailHVO = null;
        FilterDetailVO eachFilterDetailVO = null;

        if (null != usrFilterDetailTxtSet && false == usrFilterDetailTxtSet.isEmpty()) {
            Iterator<GetUsrFilterDetailTxtOMDHVO> itr = usrFilterDetailTxtSet.iterator();

            while (itr.hasNext()) {
                eachDetailHVO = itr.next();
                eachFilterDetailVO = new FilterDetailVO();
                eachFilterDetailVO.setUsrFilterDetailId(eachDetailHVO.getUsrFilterTxtId());
                eachFilterDetailVO.setColumnName(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnName());
                eachFilterDetailVO.setColumnType(RMDCommonUtility.getFavFilterColumnTypeValue(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnTypeCd()));
                eachFilterDetailVO.setResourceId(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceId());
                eachFilterDetailVO.setResourceName(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceName());
                eachFilterDetailVO.setUsrFilterId(eachDetailHVO.getGetUsrFilterOMDHVO().getUsrFilterId());
                eachFilterDetailVO.setFromFilterValue(eachDetailHVO.getFromFilterValue());
                eachFilterDetailVO.setToFilterValue(eachDetailHVO.getToFilterValue());
                eachFilterDetailVO.setCreatedBy(eachDetailHVO.getCreatedBy());
                eachFilterDetailVO.setCreatedDate(eachDetailHVO.getCreationDate());
                eachFilterDetailVO.setLastUpdatedBy(eachDetailHVO.getLastUpdatedBy());
                eachFilterDetailVO.setLastUpdatedDate(eachDetailHVO.getLastUpdatedDate());

                favFilterDetailLst.add(eachFilterDetailVO);
            }
        }
        return favFilterDetailLst;
    }

    /**
     * fetches FavFilterDetailServiceVO from GetUsrFilterDetailNbrHVO set
     */
    public List<FilterDetailVO> getFilterDetailNbrList(Set<GetUsrFilterDetailNbrOMDHVO> usrFilterDetailNbrSet) {
        List<FilterDetailVO> favFilterDetailLst = new ArrayList<FilterDetailVO>();
        GetUsrFilterDetailNbrOMDHVO eachDetailHVO = null;
        FilterDetailVO eachFilterDetailVO = null;

        if (null != usrFilterDetailNbrSet && false == usrFilterDetailNbrSet.isEmpty()) {
            Iterator<GetUsrFilterDetailNbrOMDHVO> itr = usrFilterDetailNbrSet.iterator();

            while (itr.hasNext()) {
                eachDetailHVO = itr.next();
                eachFilterDetailVO = new FilterDetailVO();
                eachFilterDetailVO.setUsrFilterDetailId(eachDetailHVO.getUsrFilterNbrId());
                eachFilterDetailVO.setColumnName(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnName());
                eachFilterDetailVO.setColumnType(RMDCommonUtility.getFavFilterColumnTypeValue(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnTypeCd()));
                eachFilterDetailVO.setResourceId(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceId());
                eachFilterDetailVO.setResourceName(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceName());
                eachFilterDetailVO.setUsrFilterId(eachDetailHVO.getGetUsrFilterOMDHVO().getUsrFilterId());
                eachFilterDetailVO.setFromFilterValue(eachDetailHVO.getFromFilterValue());
                eachFilterDetailVO.setToFilterValue(eachDetailHVO.getToFilterValue());
                eachFilterDetailVO.setCreatedBy(eachDetailHVO.getCreatedBy());
                eachFilterDetailVO.setCreatedDate(eachDetailHVO.getCreationDate());
                eachFilterDetailVO.setLastUpdatedBy(eachDetailHVO.getLastUpdatedBy());
                eachFilterDetailVO.setLastUpdatedDate(eachDetailHVO.getLastUpdatedDate());

                favFilterDetailLst.add(eachFilterDetailVO);
            }
        }
        return favFilterDetailLst;
    }

    /**
     * fetches FavFilterDetailServiceVO from GetUsrFilterDetailDatetimeHVO set
     */
    public List<FilterDetailVO> getFilterDetailDatetimeList(
            Set<GetUsrFilterDetailDatetimeOMDHVO> usrFilterDetailDatetimeSet) {
        List<FilterDetailVO> favFilterDetailLst = new ArrayList<FilterDetailVO>();
        GetUsrFilterDetailDatetimeOMDHVO eachDetailHVO = null;
        FilterDetailVO eachFilterDetailVO = null;

        if (null != usrFilterDetailDatetimeSet && false == usrFilterDetailDatetimeSet.isEmpty()) {
            Iterator<GetUsrFilterDetailDatetimeOMDHVO> itr = usrFilterDetailDatetimeSet.iterator();

            while (itr.hasNext()) {
                eachDetailHVO = itr.next();
                eachFilterDetailVO = new FilterDetailVO();
                eachFilterDetailVO.setUsrFilterDetailId(eachDetailHVO.getUsrFilterDatetimeId());
                eachFilterDetailVO.setColumnName(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnName());
                eachFilterDetailVO.setColumnType(RMDCommonUtility.getFavFilterColumnTypeValue(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnTypeCd()));
                eachFilterDetailVO.setResourceId(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceId());
                eachFilterDetailVO.setResourceName(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceName());
                eachFilterDetailVO.setUsrFilterId(eachDetailHVO.getGetUsrFilterOMDHVO().getUsrFilterId());
                eachFilterDetailVO.setFromFilterValue(eachDetailHVO.getFromFilterValue());
                eachFilterDetailVO.setToFilterValue(eachDetailHVO.getToFilterValue());
                eachFilterDetailVO.setCreatedBy(eachDetailHVO.getCreatedBy());
                eachFilterDetailVO.setCreatedDate(eachDetailHVO.getCreationDate());
                eachFilterDetailVO.setLastUpdatedBy(eachDetailHVO.getLastUpdatedBy());
                eachFilterDetailVO.setLastUpdatedDate(eachDetailHVO.getLastUpdatedDate());

                favFilterDetailLst.add(eachFilterDetailVO);
            }
        }
        return favFilterDetailLst;
    }

    /**
     * fetches FavFilterDetailServiceVO from GetUsrFilterDetailDecimalHVO set
     */
    public List<FilterDetailVO> getFilterDetailDecimalList(
            Set<GetUsrFilterDetailDecimalOMDHVO> usrFilterDetailDecimalSet) {
        List<FilterDetailVO> favFilterDetailLst = new ArrayList<FilterDetailVO>();
        GetUsrFilterDetailDecimalOMDHVO eachDetailHVO = null;
        FilterDetailVO eachFilterDetailVO = null;

        if (null != usrFilterDetailDecimalSet && false == usrFilterDetailDecimalSet.isEmpty()) {
            Iterator<GetUsrFilterDetailDecimalOMDHVO> itr = usrFilterDetailDecimalSet.iterator();

            while (itr.hasNext()) {
                eachDetailHVO = itr.next();
                eachFilterDetailVO = new FilterDetailVO();
                eachFilterDetailVO.setUsrFilterDetailId(eachDetailHVO.getUsrFilterDecimalId());
                eachFilterDetailVO.setColumnName(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnName());
                eachFilterDetailVO.setColumnType(RMDCommonUtility.getFavFilterColumnTypeValue(
                        eachDetailHVO.getGetUsrFilterOMDHVO().getGetColumnDetailOMDHVO().getColumnTypeCd()));
                eachFilterDetailVO.setResourceId(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceId());
                eachFilterDetailVO.setResourceName(eachDetailHVO.getGetUsrFilterOMDHVO().getGetFilterDetailOMDHVO()
                        .getResourceDetail().getResourceName());
                eachFilterDetailVO.setUsrFilterId(eachDetailHVO.getGetUsrFilterOMDHVO().getUsrFilterId());
                eachFilterDetailVO.setFromFilterValue(eachDetailHVO.getFromFilterValue());
                eachFilterDetailVO.setToFilterValue(eachDetailHVO.getToFilterValue());
                eachFilterDetailVO.setCreatedBy(eachDetailHVO.getCreatedBy());
                eachFilterDetailVO.setCreatedDate(eachDetailHVO.getCreationDate());
                eachFilterDetailVO.setLastUpdatedBy(eachDetailHVO.getLastUpdatedBy());
                eachFilterDetailVO.setLastUpdatedDate(eachDetailHVO.getLastUpdatedDate());
                favFilterDetailLst.add(eachFilterDetailVO);
            }
        }
        return favFilterDetailLst;
    }

    /**
     * @param objFavFilterVO
     * @param session
     * @return This method is used to get the filter Object from
     *         GetFilterDetailOMDHVO(Tablename:Filter_Detail)
     */
    public GetFilterDetailOMDHVO getFilterDetail(FavFilterServiceVO objFavFilterVO, Session session) {
        GetFilterDetailOMDHVO objFilterDetailHVO = null;

        String sequence = getFilterIDByScreenName(objFavFilterVO.getScreenNameFilter(), session);

        if (null != sequence && !sequence.isEmpty()) {
            objFilterDetailHVO = (GetFilterDetailOMDHVO) session.get(GetFilterDetailOMDHVO.class,
                    Long.valueOf(sequence));
        } else {
            /*
             * Checking if the fitler detail is existing otherwise saving the
             * new fitler detail
             */
            GetUsrUserRolesHVO objRmdOmdUserRolesHVO = null;
            /*
             * Getting the usr_role object from
             * GetUsrUserRolesHVO(get_usr.get_usr_user_roles)
             */
            if (null == objFavFilterVO.getLinkUsrRoleSeqId() || objFavFilterVO.getLinkUsrRoleSeqId() == 0) {

                objRmdOmdUserRolesHVO = null;

            } else {

                objRmdOmdUserRolesHVO = (GetUsrUserRolesHVO) session.get(GetUsrUserRolesHVO.class,
                        objFavFilterVO.getLinkUsrRoleSeqId());
            }

            GetResourceDetailOMDHVO objResourceDetailHVO = null;
            objResourceDetailHVO = getResourceDetail(objFavFilterVO, session);
            /*
             * Getting the usr_role object from
             * GetUsrUserRolesHVO(get_usr.get_usr_user_roles)
             */
            objFilterDetailHVO = new GetFilterDetailOMDHVO();
            objFilterDetailHVO.setGetUsrUserRolesSeqId(objRmdOmdUserRolesHVO);
            objFilterDetailHVO.setFilterName(objFavFilterVO.getScreenNameFilter());
            objFilterDetailHVO.setResourceDetail(objResourceDetailHVO);

            objFilterDetailHVO.setActivFlg(RMDCommonConstants.CHAR_Y);
            objFilterDetailHVO.setDefaultFlg(RMDCommonConstants.CHAR_Y);

            session.saveOrUpdate(objFilterDetailHVO);
            session.flush();
        }
        return objFilterDetailHVO;
    }

    /**
     * @param objFavFilterVO
     * @param session
     * @return This method is used to get the list of column Object from
     *         GetColumnDetailOMDHVO(Tablename:Column_Detail)
     */
    public List<GetColumnDetailOMDHVO> getColumnDetail(FavFilterServiceVO objFavFilterVO, Session session) {
        long sequenceId = 0;
        String queryString = null;
        Query query = null;
        List<GetColumnDetailOMDHVO> objColumnDetailHVOLst = new ArrayList<GetColumnDetailOMDHVO>();
        GetColumnDetailOMDHVO objColumnDetailHVO = null;
        if (null != objFavFilterVO.getColumnName()) {
            for (int i = 0; i < objFavFilterVO.getColumnName().size(); i++) {
                /* Checking for existing column object */
                sequenceId = 0;
                queryString = "SELECT COLUMDETAIL.columnId FROM GetColumnDetailOMDHVO COLUMDETAIL where COLUMDETAIL.columnName=:columnName and COLUMDETAIL.columnTypeCd=:columnType";
                query = session.createQuery(queryString);
                query.setParameter(RMDCommonConstants.COLUMNNAME, objFavFilterVO.getColumnName().get(i));
                query.setParameter(RMDCommonConstants.COLUMN_TYPE,
                        RMDCommonUtility.getFavFilterColumnTypeCode(objFavFilterVO.getColumnType().get(i)));
                if (null != RMDCommonUtility.convertObjectToString(query.uniqueResult())
                        && !RMDCommonUtility.convertObjectToString(query.uniqueResult()).isEmpty()
                        && !RMDCommonUtility.convertObjectToString(query.uniqueResult())
                                .equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)) {
                    sequenceId = RMDCommonUtility.convertObjectToLong(query.uniqueResult());
                }
                /*
                 * Checking if the column detail is existing otherwise saving
                 * the new column detail
                 */
                if (sequenceId != 0) {
                    objColumnDetailHVO = (GetColumnDetailOMDHVO) session.get(GetColumnDetailOMDHVO.class, sequenceId);
                    objColumnDetailHVOLst.add(objColumnDetailHVO);
                } else {

                    objColumnDetailHVO = new GetColumnDetailOMDHVO();
                    objColumnDetailHVO.setColumnName(objFavFilterVO.getColumnName().get(i));
                    objColumnDetailHVO.setColumnTypeCd(
                            RMDCommonUtility.getFavFilterColumnTypeCode(objFavFilterVO.getColumnType().get(i)));
                    session.saveOrUpdate(objColumnDetailHVO);
                    session.flush();

                    objColumnDetailHVOLst.add(objColumnDetailHVO);
                }
            }
        }
        return objColumnDetailHVOLst;
    }

    /**
     * @param objFavFilterVO
     * @param session
     * @return This method is used to get the resource Object from
     *         GetResourceDetailOMDHVO(Tablename:Resource_Detail)
     */
    public GetResourceDetailOMDHVO getResourceDetail(FavFilterServiceVO objFavFilterVO, Session session) {
        long sequenceId = 0;
        String queryString = null;
        Query query = null;
        GetResourceDetailOMDHVO objResourceDetailHVO = null;
        if (null != objFavFilterVO.getScreenName() && !objFavFilterVO.getScreenName().isEmpty()) {
            /* Checking for existing resource object */
            queryString = "SELECT RESOURCEDETAIL.resourceId FROM GetResourceDetailOMDHVO RESOURCEDETAIL where RESOURCEDETAIL.resourceName=:screenName";
            query = session.createQuery(queryString);
            query.setParameter(RMDCommonConstants.SCREENNAME, objFavFilterVO.getScreenName());

            if (null != RMDCommonUtility.convertObjectToString(query.uniqueResult())
                    && !RMDCommonUtility.convertObjectToString(query.uniqueResult()).isEmpty()
                    && !RMDCommonUtility.convertObjectToString(query.uniqueResult())
                            .equalsIgnoreCase(RMDCommonConstants.ZERO_STRING)) {
                sequenceId = RMDCommonUtility.convertObjectToLong(query.uniqueResult());
            }
            /*
             * Checking if the resource detail is existing otherwise saving the
             * new resource detail
             */
            if (sequenceId != 0) {
                objResourceDetailHVO = (GetResourceDetailOMDHVO) session.get(GetResourceDetailOMDHVO.class, sequenceId);
            } else {

                objResourceDetailHVO = new GetResourceDetailOMDHVO();
                objResourceDetailHVO.setResourceName(objFavFilterVO.getScreenName());
                objResourceDetailHVO.setAccessLevelCd(RMDCommonConstants.CHAR_Y);
                objResourceDetailHVO.setResourceTypeCd(RMDCommonConstants.CHAR_Y);

                session.saveOrUpdate(objResourceDetailHVO);
                session.flush();

            }
        }
        return objResourceDetailHVO;
    }

    public String getFilterIDByScreenName(String filterName, Session session) {
        String qry = " SELECT GET_USR_USER_FILTER_SEQ_ID FROM GET_USR.GET_USR_USER_FILTER WHERE FILTER_NAME=:filterName ";
        Query query = session.createSQLQuery(qry.toString());

        if (null != filterName && !filterName.isEmpty()) {
            query.setParameter(RMDCommonConstants.FILTER_NAME, filterName);
        }
        List arl = query.list();
        String sequence = null;
        if (RMDCommonUtility.isCollectionNotEmpty(arl)) {
            sequence = RMDCommonUtility.convertObjectToString(arl.get(0));
        }

        return sequence;
    }

    /*
     * (non-Javadoc) This method will return the list of enabled users who has
     * alert subscription privilege present in the system
     */
    @Override
    public List<UserServiceVO> getAlertUsersDetails(UserServiceVO objUserServiceSearchVO) throws RMDDAOException {
        Session session = null;
        try {
            session = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
            List<UserServiceVO> arlSearchResults = null;
            queryString.append("SELECT USR.GET_USR_USERS_SEQ_ID,USR.USER_ID,USR.STATUS, ");
            queryString.append(" USR.FIRST_NAME,USR.EMAIL_ID,USR.LAST_NAME,GETROLES.ROLE_NAME,");
            queryString.append(
                    " CUST.ORG_ID DEFAULTCUSTOMER,PREF.USER_PREFERENCE_VALUE,ROL.ROLE_NAME PREFERED_ROLE, GETROLES.GET_USR_ROLES_SEQ_ID,  CUST1.ORG_ID multiCust,PREFLANG.USER_PREFERENCE_VALUE PREF_LANG ");
            queryString
                    .append(" FROM GET_USR.GET_USR_USERS USR,TABLE_BUS_ORG CUST,GET_USR.GET_USR_USER_ROLES USRROLE,");
            queryString.append(
                    " GET_USR.GET_USR_ROLES GETROLES,GET_USR.GET_USR_USER_PREFERENCE PREF,GET_USR.GET_USR_ROLES ROL, GET_USR.GET_USR_USER_CUSTOMERS USRCUST, TABLE_BUS_ORG CUST1, GET_USR.GET_USR_USER_PREFERENCE PREFLANG, ");
            queryString.append("GET_USR_ROLE_PRIVILEGES privRole, GET_USR.GET_USR_PRIVILEGE privilage");
            queryString.append(" WHERE USR.LINK_CUSTOMER= CUST.OBJID(+)");
            queryString.append(
                    " AND USR.GET_USR_USERS_SEQ_ID=USRROLE.LINK_USERS(+) AND USRROLE.LINK_ROLES=GETROLES.GET_USR_ROLES_SEQ_ID(+) AND USR.GET_USR_USERS_SEQ_ID =PREF.LINK_USERS(+) AND PREF.USER_PREFERENCE_TYPE(+) = 'Role' AND PREF.USER_PREFERENCE_VALUE = ROL.GET_USR_ROLES_SEQ_ID(+)");
            queryString.append(
                    " AND USRCUST.LINK_CUSTOMERS=CUST1.OBJID(+) AND USR.GET_USR_USERS_SEQ_ID= USRCUST.LINK_USERS(+)   AND PREFLANG.USER_PREFERENCE_TYPE(+)  = 'Language'  AND USR.GET_USR_USERS_SEQ_ID = PREFLANG.link_users(+)");
            queryString.append(
                    " AND GETROLES.GET_USR_ROLES_SEQ_ID = privRole.LINK_ROLES AND privilage.GET_USR_PRIVILEGE_SEQ_ID =privRole.LINK_PRIVILEGES");
            queryString.append(
                    " AND privilage.PRIVILEGE_NAME='ALERT_SUBSCRIPTION' AND USR.STATUS =1 ORDER BY USR.USER_ID");
            if (null != session) {
                Query query = session.createSQLQuery(queryString.toString());

                query.setFetchSize(500);
                List<Object[]> arlUsers = query.list();
                arlSearchResults = populateUsers(arlUsers, RMDCommonConstants.SUBCRIPTION_PAGE);

            }

            return arlSearchResults;
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()),
                    ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, objUserServiceSearchVO.getStrLanguage()), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }

    }

    /*
     * (non-Javadoc) This method will return the list of privileges associated
     * with the user.
     */
    @Override
    public List<String> getUserComponentList(String userId) throws RMDDAOException {
        Session hibernateSession = null;
        StringBuilder queryString = new StringBuilder();
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);
            queryString.append("SELECT B.PRIVILEGE_NAME FROM (");
            queryString.append(
                    "SELECT * FROM GET_USR.GET_USR_USER_ROLES USRROL LEFT OUTER JOIN GET_USR.GET_USR_USERS USR");
            queryString
                    .append(" ON USRROL.LINK_USERS=USR.GET_USR_USERS_SEQ_ID LEFT OUTER JOIN GET_USR.GET_USR_ROLES ROL");
            queryString.append(
                    " ON USRROL.LINK_ROLES=ROL.GET_USR_ROLES_SEQ_ID LEFT OUTER JOIN GET_USR_USER_PREFERENCE PREF");
            queryString.append(
                    " ON PREF.LINK_USERS=USR.GET_USR_USERS_SEQ_ID WHERE USR.user_id =:userID AND PREF.USER_PREFERENCE_TYPE='Role' AND PREF.USER_PREFERENCE_VALUE = ROL.GET_USR_ROLES_SEQ_ID) A,");
            queryString.append(
                    " (SELECT * FROM GET_USR.GET_USR_ROLE_PRIVILEGES ROLPRIV LEFT OUTER JOIN GET_USR.GET_USR_ROLES ROL ON ROLPRIV.LINK_ROLES=ROL.GET_USR_ROLES_SEQ_ID");
            queryString.append(
                    " LEFT OUTER JOIN GET_USR.GET_USR_PRIVILEGE PRIV ON ROLPRIV.LINK_PRIVILEGES=PRIV.GET_USR_PRIVILEGE_SEQ_ID WHERE PRIV.RESOURCE_TYPE='utility') B");
            queryString.append(" WHERE A.GET_USR_ROLES_SEQ_ID=B.GET_USR_ROLES_SEQ_ID");
            Query query = hibernateSession.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.USER_ID, userId);
            query.setFetchSize(500);
            List<String> componentList = query.list();

            return componentList;
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }

    private List<UserServiceVO> populateUsers(List<Object[]> arlUsers, String flag) {
        List<UserServiceVO> arlSearchResults = null;
        List<String> userIdList = new ArrayList<String>();
        List<String> roleNameList = new ArrayList<String>();
        List<RolesVO> rolesLst;
        List<String> custList;
        UserServiceVO userTempVO;
        Map<String, UserServiceVO> userServiceMap = new LinkedHashMap<String, UserServiceVO>();
        if (RMDCommonUtility.isCollectionNotEmpty(arlUsers)) {

            UserServiceVO objUserServiceVO = null;
            arlSearchResults = new ArrayList<UserServiceVO>();
            for (Object[] obj : arlUsers) {
                objUserServiceVO = new UserServiceVO();

                /*
                 * If role exists already in the object We will be getting the
                 * the existing object from the map and adding the role in
                 * roleList
                 */
                if (userIdList.contains(obj[0].toString())) {
                    if (userServiceMap.containsKey(obj[0].toString())) {

                        userTempVO = null;
                        rolesLst = null;

                        userTempVO = userServiceMap.get(obj[0].toString());
                        List<String> customerList = userTempVO.getListCustomer();
                        rolesLst = userTempVO.getRoles();
                        if (!customerList.contains(RMDCommonUtility.convertObjectToString(obj[11]))
                                && !customerList.contains(RMDCommonConstants.ALL_CUSTOMER)) {
                            customerList.add(RMDCommonUtility.convertObjectToString(obj[11]));
                        }
                        if (!RMDCommonUtility.isCollectionNotEmpty(rolesLst)) {
                            rolesLst = new ArrayList<RolesVO>();
                        }
                        if (!roleNameList.contains(RMDCommonUtility.convertObjectToString(obj[6]))) {
                            RolesVO rolesVO = new RolesVO();
                            rolesVO.setGetUsrRolesSeqId(RMDCommonUtility.convertObjectToLong(obj[10]));
                            rolesVO.setRoleName(RMDCommonUtility.convertObjectToString(obj[6]));
                            rolesLst.add(rolesVO);
                        }
                        userTempVO.setRoles(rolesLst);
                        userTempVO.setListCustomer(customerList);
                        userServiceMap.put(obj[0].toString(), userTempVO);
                    }

                } /* Adding here for the first role */
                else {
                    userIdList.add(obj[0].toString());
                    rolesLst = new ArrayList<RolesVO>();
                    custList = new ArrayList<String>();
                    roleNameList = new ArrayList<String>();
                    if (null != RMDCommonUtility.convertObjectToString(obj[11])
                            && !RMDCommonUtility.convertObjectToString(obj[11]).isEmpty()
                            && !RMDCommonConstants.ZERO_STRING
                                    .equals(RMDCommonUtility.convertObjectToString(obj[11]))) {
                        custList.add(RMDCommonUtility.convertObjectToString(obj[11]));
                    } else {
                        custList.add(RMDCommonConstants.ALL_CUSTOMER);
                    }

                    RolesVO rolesVO = new RolesVO();
                    rolesVO.setGetUsrRolesSeqId(RMDCommonUtility.convertObjectToLong(obj[10]));
                    rolesVO.setRoleName(RMDCommonUtility.convertObjectToString(obj[6]));
                    rolesLst.add(rolesVO);
                    roleNameList.add(rolesVO.getRoleName());
                    objUserServiceVO.setRoles(rolesLst);
                    objUserServiceVO.setListCustomer(custList);
                    objUserServiceVO.setGetUsrUsersSeqId(RMDCommonUtility.convertObjectToLong(obj[0]));
                    objUserServiceVO.setStatus(RMDCommonUtility.convertObjectToLong(obj[2]));
                    objUserServiceVO.setUserId(RMDCommonUtility.convertObjectToString(obj[1]));
                    objUserServiceVO.setStrFirstName(RMDCommonUtility.convertObjectToString(obj[3]));
                    objUserServiceVO.setStrEmail(RMDCommonUtility.convertObjectToString(obj[4]));
                    objUserServiceVO.setStrLastName(RMDCommonUtility.convertObjectToString(obj[5]));
                    objUserServiceVO.setCustomerId(RMDCommonUtility.convertObjectToString(obj[7]));
                  
                    if (null != flag && flag.equalsIgnoreCase(RMDCommonConstants.USER_PAGE)) {
                        objUserServiceVO.setStrRole(RMDCommonUtility.convertObjectToString(obj[9]));
                        // Added for User Type
                        objUserServiceVO.setUserType(RMDCommonUtility.convertObjectToString(obj[13]));
                      //Added UOM
    					objUserServiceVO.setUom(RMDCommonUtility
    							.convertObjectToString(obj[14]));
    					//newly added code
                        objUserServiceVO.setLastUpdatedBy(RMDCommonUtility.convertObjectToString(obj[15]));
                        objUserServiceVO.setLastUpdatedTime(RMDCommonUtility.convertObjectToString(obj[16]));
                        //Added by Murali Medicherla for Rally Id : US226051
                        objUserServiceVO.setMobileAccess(RMDCommonUtility.convertObjectToString(obj[17]));
                        objUserServiceVO.setEndUserScoring(RMDCommonUtility.convertObjectToString(obj[18])==null?"N":RMDCommonUtility.convertObjectToString(obj[18]));
                    }
                    objUserServiceVO.setStrLanguage(RMDCommonUtility.convertObjectToString(obj[12]));
                    userServiceMap.put(obj[0].toString(), objUserServiceVO);

                }

            }
            Collection<UserServiceVO> collObject = userServiceMap.values();

            // obtain an Iterator for Collection
            Iterator<UserServiceVO> itr = collObject.iterator();
            /*
             * Iterating through the collection for getting the VO and adding
             * the VO in list
             */
            while (itr.hasNext()) {
                arlSearchResults.add(itr.next());
            }
        }
        return arlSearchResults;
 }
	/**
	 * @Author:
	 * @param : roleId,userId,removedRoleId
	 * @return:UserEOADetailsVO
	 * @throws:RMDDAOException
	 * @Description: This method is used to check whether the selected role is
	 *               having CM / Multilingual/emetrics privilege
	 */
	@Override
	public UserEOADetailsVO getCMorMLDetails(String roleId, String userId,String removedRoleId)
			throws RMDDAOException {
		Session session = null;
		StringBuilder priviligeQuery = new StringBuilder();
		StringBuilder aliasQuery = new StringBuilder();
		StringBuilder mlQuery = new StringBuilder();

		StringBuilder countUserRolesQuery = new StringBuilder();
		List<String> removedPrevList = null;
		List<String> addedPrevList = null;
		List<Object> userDetailsList = null;
		List<Object> mlDetails = null;
		UserEOADetailsVO objUserEOADetailsVO = new UserEOADetailsVO();;
		boolean alias=false;
		try {
			session = getHibernateSession();
			priviligeQuery
					.append("SELECT DISTINCT pr.PRIVILEGE_NAME FROM GET_USR_ROLE_PRIVILEGES ur,GET_USR.get_usr_privilege pr WHERE ur.LINK_PRIVILEGES=pr.GET_USR_PRIVILEGE_SEQ_ID ");
			priviligeQuery
					.append("AND pr.PRIVILEGE_NAME IN ('CASE_MANAGEMENT_PRIVILEGE','SUBMENU_MULTILINGUAL_RX','SUBMENU_EMETRICS') AND pr.RESOURCE_TYPE   NOT IN ('utility') ");
			priviligeQuery
					.append("AND ur.LINK_ROLES IN(:roleId) order by pr.PRIVILEGE_NAME");
			
			aliasQuery
				.append("SELECT TEMPLOYEE.FIRST_NAME,TEMPLOYEE.LAST_NAME,TEMPLOYEE.E_MAIL, TUSER.LOGIN_NAME,TUSER.WEB_LOGIN FROM ");
			aliasQuery
				.append("TABLE_EMPLOYEE TEMPLOYEE,TABLE_USER TUSER WHERE TEMPLOYEE.EMPLOYEE2USER=TUSER.OBJID  AND TUSER.WEB_LOGIN=:userID ");
			
			mlQuery.append("SELECT APPROVER FROM gets_sd_ml_user WHERE SSOID=:userID");
			
			
			countUserRolesQuery.append("SELECT COUNT(1) FROM SA.SA_SEC_USER_ROLE WHERE USER_NAME=(SELECT LOGIN_NAME FROM TABLE_USER WHERE WEB_LOGIN=:userID)");
			final Query priviligeHQuery = session.createSQLQuery(priviligeQuery
					.toString());
			List<String> roleIdList = Arrays.asList(roleId
					.split(RMDCommonConstants.COMMMA_SEPARATOR));
			priviligeHQuery
					.setParameterList(RMDCommonConstants.ROLE_ID, roleIdList);
			addedPrevList = priviligeHQuery.list();
			if (!addedPrevList.isEmpty()) {	//check whether the selected role is having CM and ML and emetrics or not
				final Query aliasHQuery = session.createSQLQuery(aliasQuery
						.toString());
				aliasHQuery.setParameter(RMDCommonConstants.USER_ID,
						userId);
				if (addedPrevList.contains("CASE_MANAGEMENT_PRIVILEGE")) {
						objUserEOADetailsVO.setOmdCMPrevFlag(RMDCommonConstants.TRUE);
						userDetailsList = aliasHQuery.list();	
						alias=true;
				}
				if (addedPrevList.contains("SUBMENU_MULTILINGUAL_RX")) {
					objUserEOADetailsVO.setOmdMLPrevFlag(RMDCommonConstants.TRUE);
					final Query mlHQuery = session.createSQLQuery(mlQuery
							.toString());
					mlHQuery.setParameter(RMDCommonConstants.USER_ID, userId);
					mlDetails = mlHQuery.list();
					if (!mlDetails.isEmpty()) {
						objUserEOADetailsVO.setEoaMLPrevFlag(RMDCommonConstants.TRUE);
						objUserEOADetailsVO.setEoaMLVal(mlDetails.get(0).toString());
					}
					if(!alias){
						userDetailsList = aliasHQuery.list();
					}
				}
				if (addedPrevList.contains("SUBMENU_EMETRICS")) {
				    
					objUserEOADetailsVO.setOmdEmetricsPrevFlag(RMDCommonConstants.TRUE);
					
					if(!alias){
                        userDetailsList = aliasHQuery.list();
                    }
					
					UserServiceVO userServiceVO = getUserInfo(userId);
					if(null != userServiceVO){
					objUserEOADetailsVO.setEndUserScoring(userServiceVO.getEndUserScoring());
					objUserEOADetailsVO.setEoaEmetricsPrevFlag(RMDCommonConstants.TRUE);
                    objUserEOADetailsVO.setEoaEmetricsVal(userServiceVO.getEmetricsAccess());
					}else{
					    objUserEOADetailsVO.setEoaEmetricsPrevFlag(RMDCommonConstants.FALSE);
	                    objUserEOADetailsVO.setEoaEmetricsVal("R");
					}
				}
			}
			if(!removedRoleId.isEmpty() && addedPrevList.size() !=3){
//check whether removed roles are having cm or ml prev or emetrics prev
				final Query removedprivHQuery = session.createSQLQuery(priviligeQuery
						.toString());
				List<String> removedRoleIdList = Arrays.asList(removedRoleId
						.split(RMDCommonConstants.COMMMA_SEPARATOR));
				removedprivHQuery
						.setParameterList(RMDCommonConstants.ROLE_ID, removedRoleIdList);
				removedPrevList = removedprivHQuery.list();
				if (!removedPrevList.isEmpty()  && addedPrevList.isEmpty()) { //if both/any cm or ml rolesr emetrics are removed and added roles are not having cm/ml/emetrics
					objUserEOADetailsVO.setOmdMLPrevFlag(RMDCommonConstants.FALSE);
					objUserEOADetailsVO.setOmdCMPrevFlag(RMDCommonConstants.FALSE);
					objUserEOADetailsVO.setOmdEmetricsPrevFlag(RMDCommonConstants.FALSE);
					objUserEOADetailsVO.setBlnOMDCmMlEmetricsPreRemoved(RMDCommonConstants.TRUE);
					final Query aliasHQuery = session.createSQLQuery(aliasQuery
							.toString());
					aliasHQuery.setParameter(RMDCommonConstants.USER_ID,
							userId);
					userDetailsList = aliasHQuery.list();
				}
				if(removedPrevList.contains("SUBMENU_MULTILINGUAL_RX") && !objUserEOADetailsVO.isOmdMLPrevFlag()){ //if ml alone removed
					objUserEOADetailsVO.setOmdMLPrevFlag(RMDCommonConstants.FALSE);
					objUserEOADetailsVO.setBlnOMDMlAloneRemoved(RMDCommonConstants.TRUE);
					final Query mlHQuery = session.createSQLQuery(mlQuery
							.toString());
					mlHQuery.setParameter(RMDCommonConstants.USER_ID, userId);
					mlDetails = mlHQuery.list();
					if (!mlDetails.isEmpty()) {
						objUserEOADetailsVO.setEoaMLPrevFlag(RMDCommonConstants.TRUE);
					}
				}
				if(removedPrevList.contains("SUBMENU_EMETRICS") && !objUserEOADetailsVO.isOmdEmetricsPrevFlag()){ //if emtrics alone removed
					objUserEOADetailsVO.setOmdEmetricsPrevFlag(RMDCommonConstants.FALSE);
					objUserEOADetailsVO.setBlnOMDEmetricsAloneRemoved(RMDCommonConstants.TRUE);
					
			}
			}
			if (RMDCommonUtility.isCollectionNotEmpty(userDetailsList)) {
				objUserEOADetailsVO.setEoaCMPrevFlag(RMDCommonConstants.TRUE);
				for (final Iterator<Object> obj = userDetailsList
						.iterator(); obj.hasNext();) {
					final Object[] userDetails = (Object[]) obj.next();

					objUserEOADetailsVO.setFirstName(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(RMDCommonUtility
							.convertObjectToString(userDetails[0]))));
					objUserEOADetailsVO.setLastName(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(RMDCommonUtility
							.convertObjectToString(userDetails[1]))));
					objUserEOADetailsVO.setEmailId(RMDCommonUtility
							.convertObjectToString(userDetails[2]));
					objUserEOADetailsVO.setAliasName(RMDCommonUtility
							.convertObjectToString(userDetails[3]));
					objUserEOADetailsVO.setUserId(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(RMDCommonUtility
							.convertObjectToString(userDetails[4]))));

				}
			}
			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_ML_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CM_ML_DETAILS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			priviligeQuery = null;
			aliasQuery = null;
			mlQuery = null;
			removedPrevList = null;
			userDetailsList = null;
			mlDetails = null;
			addedPrevList = null;

		}
		return objUserEOADetailsVO;
	}

	/**
	 * @Author:
	 * @param :userServiceVO
	 * @return:
	 * @throws:RMDDAOException
	 * @Description: This method is used to insert/update the user details into EOA tables
	 */
	public String saveUserEOADetails(UserServiceVO userServiceVO)
			throws RMDDAOException {
		
		Session session = null;
		StringBuilder tableWipBinQuery = new StringBuilder();
		StringBuilder tableUserQuery = new StringBuilder();
		StringBuilder tableEmployeeQuery = new StringBuilder();
		StringBuilder tableMLQuery = new StringBuilder();
		StringBuilder tableUserUpdateQuery = new StringBuilder();
		StringBuilder tableEmpUpdateQuery = new StringBuilder();
		StringBuilder tableMLUpdateQuery = new StringBuilder();
		StringBuilder tableMLDeleteQuery = new StringBuilder();
		StringBuilder deactiveUserQuery = new StringBuilder();
		StringBuilder tableRoleIdQuery = new StringBuilder();
		StringBuilder countUserRolesQuery = new StringBuilder();
		String result=RMDCommonConstants.SUCCESS;
		Transaction trans=null;
		StringBuilder oldSSOExistsQuery = new StringBuilder();
		StringBuilder oldSSOMlExistsQuery = new StringBuilder();
		boolean oldSSOExists = false;
		boolean oldSSOWithMLExists = false;
		
		try{
			session = getHibernateSession();
			trans=session.beginTransaction();
			//TABLE_WIPBIN insert query
			tableWipBinQuery.append("INSERT INTO SA.TABLE_WIPBIN (OBJID, TITLE, S_TITLE, ICON_ID, DIALOG_ID, WIPBIN_OWNER2USER) VALUES ((SELECT MAX(OBJID)+1 ");
			tableWipBinQuery.append("FROM SA.TABLE_WIPBIN),:title,:sTitle,:iconId,:dailogId,(SELECT MAX(OBJID)+1 FROM SA.TABLE_USER))");
			//table_user insert query
			tableUserQuery.append("INSERT INTO SA.TABLE_USER(OBJID, LOGIN_NAME, S_LOGIN_NAME, PASSWORD, STATUS, CS_LIC, CSDE_LIC, CQ_LIC, PASSWD_CHG, ");
			tableUserQuery.append("LAST_LOGIN, CLFO_LIC, CS_LIC_TYPE, CQ_LIC_TYPE, CSFTS_LIC, CSFTSDE_LIC, CQFTS_LIC, WEB_LOGIN, WEB_PASSWORD, SUBMITTER_IND, ");
			tableUserQuery.append("SFA_LIC, CCN_LIC, UNIV_LIC, LOCALE,  USER_ACCESS2PRIVCLASS, USER_DEFAULT2WIPBIN, OFFLINE2PRIVCLASS) VALUES ((SELECT MAX(OBJID)+1 FROM SA.TABLE_USER),");
			tableUserQuery.append(":loginName,:sLoginName,:password,:status,SYSDATE,SYSDATE,SYSDATE,SYSDATE,SYSDATE,SYSDATE,:csLICType,:cqLICType,SYSDATE,SYSDATE,SYSDATE,");
			tableUserQuery.append(":userId,:password,:submitterInd,SYSDATE,SYSDATE,SYSDATE,:locale,:userAcc2Priv,(SELECT MAX(OBJID) FROM SA.TABLE_WIPBIN),:offLine2PrivClass)");
			//table_employee insert query
			tableEmployeeQuery.append("INSERT INTO TABLE_EMPLOYEE (OBJID,FIRST_NAME,S_FIRST_NAME,LAST_NAME,S_LAST_NAME,E_MAIL,EMPLOYEE2USER) VALUES ((SELECT max(OBJID)+1 FROM TABLE_EMPLOYEE),");
			tableEmployeeQuery.append(":firstName,:sFirstName,:lastName,:sLastName,:emailId,(SELECT MAX(OBJID) FROM SA.TABLE_USER))");
			//gets_sd_ml_user insert query
			tableMLQuery.append("Insert into gets_sd_ml_user (OBJID,GETS_USER_NAME,SSOID,LAST_NAME,FIRST_NAME,EMAIL,APPROVAL_DATE,APPROVAL_FLAG,APPROVER,CREATED_DATE,");
			tableMLQuery.append("LAST_UPDATE_DATE,CREATION_BY,LAST_UPDATED_BY) values ((SELECT max(OBJID)+1 FROM gets_sd_ml_user),:loginName,:userId,:lastName,:firstName,");
			tableMLQuery.append(":emailId,SYSDATE,:flag,:approver,SYSDATE,SYSDATE,:createdBy,:updatedBy)");
			//Delete ML record Query
			tableMLDeleteQuery.append("DELETE FROM GETS_SD_ML_USER WHERE SSOID=:oldSSOId");
			//count no of roles in role table for a user
			countUserRolesQuery.append("SELECT COUNT(1) FROM SA.SA_SEC_USER_ROLE WHERE USER_NAME=:loginName");
			//to find role Id for selected privilege
			tableRoleIdQuery.append("SELECT ROLE_ID FROM SA.SA_SEC_ROLE WHERE ROLE=:selectedEmetricsPrev");
			oldSSOExistsQuery.append("SELECT COUNT(1) FROM TABLE_USER WHERE WEB_LOGIN=:oldSSOId");
			oldSSOMlExistsQuery.append("SELECT COUNT(1) FROM gets_sd_ml_user WHERE SSOID=:oldSSOId");
			

			
			if( RMDCommonConstants.LONG_CONST_ZERO != userServiceVO.getGetUsrUsersSeqId() && !userServiceVO.getOldUserId().equals(userServiceVO.getNewUserIdEntered())){
    			
    			final Query oldSSOExistsHQuery = session.createSQLQuery(oldSSOExistsQuery
                        .toString());
    			oldSSOExistsHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
    			 BigDecimal ssoCount=(BigDecimal) oldSSOExistsHQuery.uniqueResult();
                 int noOfSSO= ssoCount.intValue();
                 if (noOfSSO > 0) {
                     oldSSOExists =true;
                 }
                               
                 final Query oldSSOMlExistsHQuery = session.createSQLQuery(oldSSOMlExistsQuery
                         .toString());
                 oldSSOMlExistsHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
                  BigDecimal mlCount=(BigDecimal) oldSSOMlExistsHQuery.uniqueResult();
                  int noOfMLSSO= mlCount.intValue();
                  if (noOfMLSSO > 0) {
                      oldSSOWithMLExists =true;
                  }
			}else{
			    userServiceVO.setOldUserId(userServiceVO.getNewUserIdEntered());
			}
			
             if ( (null == userServiceVO.getEoaCMFlag()
					|| RMDCommonConstants.N_LETTER_UPPER
							.equalsIgnoreCase(userServiceVO.getEoaCMFlag())) && !oldSSOExists) { //if user is not exist in EOA
				
				final Query tableWipBinHQuery = session.createSQLQuery(tableWipBinQuery
						.toString());
				for(int i=0;i<3;i++){
					tableWipBinHQuery.setParameter(RMDCommonConstants.TITLE, RMDCommonConstants.getWipBinTitle()[i]);
					tableWipBinHQuery.setParameter(RMDCommonConstants.UPPER_TITLE, RMDCommonConstants.getWipBinTitle()[i].toUpperCase());
					tableWipBinHQuery.setParameter(RMDCommonConstants.ICON_ID, RMDCommonConstants.INT_CONST);
					tableWipBinHQuery.setParameter(RMDCommonConstants.DIALOG_ID, RMDCommonConstants.DIALOG_ID_VAL);
					tableWipBinHQuery.executeUpdate();
				}
				//tableUserQuery-needs to be executed
				final Query tableUserHQuery = session.createSQLQuery(tableUserQuery
						.toString());
				tableUserHQuery.setParameter(RMDCommonConstants.LOGIN_NAME, userServiceVO.getEoaAlias());
				tableUserHQuery.setParameter(RMDCommonConstants.S_LOGIN_NAME, userServiceVO.getEoaAlias().toUpperCase());
				tableUserHQuery.setParameter(RMDCommonConstants.PASSWORD, RMDCommonConstants.CONST_PASSWORD);
				tableUserHQuery.setParameter(RMDCommonConstants.STATUS, userServiceVO.getStatus());
				tableUserHQuery.setParameter(RMDCommonConstants.CS_LIC_TYPE, RMDCommonConstants.INT_CONST);
				tableUserHQuery.setParameter(RMDCommonConstants.CQ_LIC_TYPE, RMDCommonConstants.INT_CONST);
				tableUserHQuery.setParameter(RMDCommonConstants.USERID, userServiceVO.getNewUserIdEntered());
				tableUserHQuery.setParameter(RMDCommonConstants.SUBMITTER_IND, RMDCommonConstants.INT_CONST);
				tableUserHQuery.setParameter(RMDCommonConstants.LOCALE, RMDCommonConstants.INT_CONST);
				tableUserHQuery.setParameter(RMDCommonConstants.USER_ACCESS2PRIVCLASS, RMDCommonConstants.USER_ACCESS2PRIVCLASS_CONST);
				tableUserHQuery.setParameter(RMDCommonConstants.OFFLINE2PRIVCLASS, RMDCommonConstants.OFFLINE2PRIVCLASS_CONST);
				tableUserHQuery.executeUpdate();
				//tableEmployeeQuery-needs to be executed
				final Query tableEmpHQuery = session.createSQLQuery(tableEmployeeQuery
						.toString());
				tableEmpHQuery.setParameter(RMDCommonConstants.FIRST_NAME, userServiceVO.getStrFirstName());
				tableEmpHQuery.setParameter(RMDCommonConstants.S_FIRST_NAME, userServiceVO.getStrFirstName().toUpperCase());
				tableEmpHQuery.setParameter(RMDCommonConstants.LAST_NAME, userServiceVO.getStrLastName());
				tableEmpHQuery.setParameter(RMDCommonConstants.S_LAST_NAME, userServiceVO.getStrLastName().toUpperCase());
				tableEmpHQuery.setParameter(RMDCommonConstants.EMAIL_ID, userServiceVO.getStrEmail());
				tableEmpHQuery.executeUpdate();
			}else if(RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getEoaCMFlag()) || oldSSOExists){ //if user is already there in EOA				
				//update table employee	
				tableEmpUpdateQuery.append("UPDATE SA.TABLE_EMPLOYEE SET FIRST_NAME=:firstName,S_FIRST_NAME=:sFirstName,LAST_NAME=:lastName,S_LAST_NAME=:sLastName,");
				tableEmpUpdateQuery.append("E_MAIL=:emailId WHERE EMPLOYEE2USER=(SELECT OBJID FROM SA.TABLE_USER WHERE WEB_LOGIN=:userId)");
				final Query tableEmpHUpdateQuery = session.createSQLQuery(tableEmpUpdateQuery
						.toString());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.FIRST_NAME, userServiceVO.getStrFirstName());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.S_FIRST_NAME, userServiceVO.getStrFirstName().toUpperCase());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.LAST_NAME, userServiceVO.getStrLastName());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.S_LAST_NAME, userServiceVO.getStrLastName().toUpperCase());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.EMAIL_ID, userServiceVO.getStrEmail());
				tableEmpHUpdateQuery.setParameter(RMDCommonConstants.USERID, userServiceVO.getOldUserId());
				tableEmpHUpdateQuery.executeUpdate();
				
				//update table user
                tableUserUpdateQuery.append("UPDATE SA.TABLE_USER SET WEB_LOGIN=:userId,LOGIN_NAME=:loginName,S_LOGIN_NAME=:sLoginName,STATUS=:status  WHERE LOGIN_NAME=(SELECT LOGIN_NAME FROM TABLE_USER WHERE WEB_LOGIN =:oldSSOId)");
                final Query tableUserUpdateHQuery = session.createSQLQuery(tableUserUpdateQuery
                        .toString());
                tableUserUpdateHQuery.setParameter(RMDCommonConstants.USERID, userServiceVO.getNewUserIdEntered());
                tableUserUpdateHQuery.setParameter(RMDCommonConstants.LOGIN_NAME, userServiceVO.getEoaAlias());
                tableUserUpdateHQuery.setParameter(RMDCommonConstants.S_LOGIN_NAME, userServiceVO.getEoaAlias().toUpperCase());
                tableUserUpdateHQuery.setParameter(RMDCommonConstants.STATUS, userServiceVO.getStatus());
                tableUserUpdateHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
                tableUserUpdateHQuery.executeUpdate();
			}
			if(null != userServiceVO.getOmdMLFlag()
					&& RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getOmdMLFlag())){ //if omd ml pre is selected			   
				if((null != userServiceVO.getEoaMLFlag()
						&& RMDCommonConstants.Y_LETTER_UPPER
						.equalsIgnoreCase(userServiceVO.getEoaMLFlag())) || oldSSOWithMLExists){ //if eoa ml pre already exist
					//update ml table
					tableMLUpdateQuery.append("update gets_sd_ml_user set SSOID = :userId,GETS_USER_NAME=:loginName,LAST_NAME=:lastName,FIRST_NAME=:firstName,EMAIL = :emailId, APPROVAL_FLAG = :flag,APPROVER=:approver,last_update_date=SYSDATE ,"); 
					tableMLUpdateQuery.append("LAST_UPDATED_BY=(SELECT LOGIN_NAME FROM TABLE_USER WHERE WEB_LOGIN=:loginId) where SSOID=:oldSSOId");
					final Query tableMLUpdateHQuery = session.createSQLQuery(tableMLUpdateQuery
							.toString());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.USERID, userServiceVO.getNewUserIdEntered());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.LOGIN_NAME, userServiceVO.getEoaAlias());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.LAST_NAME, userServiceVO.getStrLastName());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.FIRST_NAME, userServiceVO.getStrFirstName());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.EMAIL_ID, userServiceVO.getStrEmail());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.FLAG, RMDCommonConstants.Y_LETTER_UPPER);
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.APPROVER, userServiceVO.getEoaMLVal());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.LOGIN_ID, userServiceVO.getUserId());
					tableMLUpdateHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
					tableMLUpdateHQuery.executeUpdate();
				}else if(null != userServiceVO.getEoaMLFlag() && userServiceVO.getStatus() ==1){ //if ml prev not exist and user status is active
					//insert into ML table
					final Query tableMLHQuery = session.createSQLQuery(tableMLQuery
							.toString());
					tableMLHQuery.setParameter(RMDCommonConstants.LOGIN_NAME, userServiceVO.getEoaAlias());
					tableMLHQuery.setParameter(RMDCommonConstants.USERID, userServiceVO.getNewUserIdEntered());
					tableMLHQuery.setParameter(RMDCommonConstants.LAST_NAME, userServiceVO.getStrLastName());
					tableMLHQuery.setParameter(RMDCommonConstants.FIRST_NAME, userServiceVO.getStrFirstName());
					tableMLHQuery.setParameter(RMDCommonConstants.EMAIL_ID, userServiceVO.getStrEmail());
					tableMLHQuery.setParameter(RMDCommonConstants.FLAG, RMDCommonConstants.Y_LETTER_UPPER);
					tableMLHQuery.setParameter(RMDCommonConstants.APPROVER, userServiceVO.getEoaMLVal());
					tableMLHQuery.setParameter(RMDCommonConstants.CREATED_BY, RMDCommonConstants.ADMIN);
					tableMLHQuery.setParameter(RMDCommonConstants.UPDATED_BY,RMDCommonConstants.ADMIN);
					tableMLHQuery.executeUpdate();
				}
					
			}
			if((RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getOmdCMFlag()) && RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getOmdMlAloneRemoved()) && RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getEoaMLFlag())) || (oldSSOWithMLExists && null != userServiceVO.getOmdMLFlag()
		                    && RMDCommonConstants.N_LETTER_UPPER
		                    .equalsIgnoreCase(userServiceVO.getOmdMLFlag()))){ //if omd ml alone removed and eoa ml exists								
					//delete record from ML table
					final Query tableMLDeleteHQuery = session.createSQLQuery(tableMLDeleteQuery
							.toString());
					tableMLDeleteHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
					tableMLDeleteHQuery.executeUpdate();
				
			} 
			
			
			if((null!=userServiceVO.getGetUsrUsersSeqId()&&RMDCommonConstants.Y_LETTER_UPPER
					.equalsIgnoreCase(userServiceVO.getOmdCmMlPrevRemoved()))||userServiceVO.getStatus()==0){//if Omd cm and ml both are removed
				if(RMDCommonConstants.Y_LETTER_UPPER
				.equalsIgnoreCase(userServiceVO.getEoaCMFlag()) || oldSSOExists){ //inactive the user
					//change user status to inactive
					deactiveUserQuery.append("UPDATE SA.TABLE_USER SET STATUS=0 WHERE LOGIN_NAME=:loginName");
					final Query deactiveUserHQuery = session.createSQLQuery(deactiveUserQuery
							.toString());
					deactiveUserHQuery.setParameter(RMDCommonConstants.LOGIN_NAME, userServiceVO.getEoaAlias());
					deactiveUserHQuery.executeUpdate();
				}
				if(RMDCommonConstants.Y_LETTER_UPPER
						.equalsIgnoreCase(userServiceVO.getEoaMLFlag()) || oldSSOWithMLExists){//delete record from ml table
					//delete the record from ML table
					final Query tableMLDeleteHQuery = session.createSQLQuery(tableMLDeleteQuery
							.toString());
					tableMLDeleteHQuery.setParameter(RMDCommonConstants.OLD_SSO_ID, userServiceVO.getOldUserId());
					tableMLDeleteHQuery.executeUpdate();
				}
				
				
			}
			trans.commit();
		} catch (ConstraintViolationException e) {
		    if(trans != null){
                trans.rollback();
            }
		    LOG.error("EOA Alias name already exists "+e);
		    if(e.getConstraintName().contains("SA.USER_LOGIN_INDEX") || e.getConstraintName().contains("SA.S_USER_LOGIN_INDEX"))
            {
                 throw new RMDDAOException(RMDServiceConstants.EOA_ALIAS_EXISTS);
            } else
            {
            result=RMDCommonConstants.FAILURE;
            LOG.error("Exception occured in saveUserEOADetails() method of UserDAOImpl " + e.getLocalizedMessage());
             throw new RMDDAOException(RMDServiceConstants.USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES);
            }                      
        }
		catch(Exception e){
			if(trans != null){
				trans.rollback();
			}
			if(e.getMessage().contains(RMDServiceConstants.EMETRICS_UPDATE_ERROR_MSG))
			{
				 throw new RMDDAOException(RMDServiceConstants.EMETRICS_UPDATE_ERROR_MSG);
			}
			else
			{
			result=RMDCommonConstants.FAILURE;
			LOG.error("Exception occured in saveUserEOADetails() method of UserDAOImpl " + e);
			 throw new RMDDAOException(RMDServiceConstants.USER_MANAGEMENT_FAILED_TO_UPDATE_EOA_TABLES);
			}
			
								
		}finally{
			trans=null;
			tableWipBinQuery =null;
			tableUserQuery = null;
			tableEmployeeQuery = null;
			tableMLQuery = null;
			tableUserUpdateQuery = null;
			tableEmpUpdateQuery = null;
			tableMLUpdateQuery = null;
			tableMLDeleteQuery = null;
			deactiveUserQuery = null;
			releaseSession(session);
		}
		return result;
	}

	/**
	 * @Author:
	 * @param :
	 * @param :aliasName
	 * @return:String
	 * @throws: RMDDAOException
	 * @Description:This method is used for checking whether the entered aliasName is exist or not
	 */
	@Override
	public String checkEOAAliasExist(String aliasName) throws RMDDAOException {
		Session session = null;
		StringBuilder eoaAliasQuery = new StringBuilder();
		String result = RMDCommonConstants.NO;
		List<Object[]> resultList =null;
		try {
			session = getHibernateSession();
			eoaAliasQuery
			.append("SELECT LOGIN_NAME FROM table_user WHERE LOGIN_NAME=:aliasName ");
			
			final Query eoaAliasHQuery = session.createSQLQuery(eoaAliasQuery
					.toString());
			eoaAliasHQuery.setParameter(RMDServiceConstants.EOA_ALIAS,
					aliasName);
			
			resultList = eoaAliasHQuery.list();
			if(!resultList.isEmpty()){
				result=RMDCommonConstants.YES;
			}
		}catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_EOA_ALIAS_EXISTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_CHECK_EOA_ALIAS_EXISTS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		}

		finally {
			releaseSession(session);
			eoaAliasQuery =null;
			resultList=null;
		}
		return result;
	}
	
	@Override
    public UserServiceVO getUserInfo(String userId) throws RMDDAOException {
        UserServiceVO objUsersVO = null;
        Session hibernateSession = null;
        try {
            hibernateSession = getHibernateSession(EMPTY_STRING);
            String queryString = "FROM GetUsrUsersHVO USER WHERE USER.userId =:userID";
            Query query = hibernateSession.createQuery(queryString);
            query.setParameter(RMDCommonConstants.USER_ID, userId);
            query.setFetchSize(1);
            
            List<GetUsrUsersHVO> userList = query.list();
            if (RMDCommonUtility.isCollectionNotEmpty(userList)) {
                GetUsrUsersHVO objUserHVO = userList.get(0);
                objUsersVO = new UserServiceVO();
                objUsersVO.setGetUsrUsersSeqId(objUserHVO.getGetUsrUsersSeqId());
                objUsersVO.setStatus(objUserHVO.getStatus());
                objUsersVO.setUserId(objUserHVO.getUserId());
                objUsersVO.setStrFirstName(objUserHVO.getFirstName());
                objUsersVO.setStrLastName(objUserHVO.getLastName());
                objUsersVO.setEmetricsAccess(objUserHVO.getEmetricsAccess());
                objUsersVO.setEndUserScoring(objUserHVO.getEndUserScoring()==null?"N":objUserHVO.getEndUserScoring());
                
            }
            return objUsersVO;
        } catch (RMDDAOConnectionException ex) {
            
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
    }

	/**
     * @Author:
     * @param :
     * @param :
     * @return:List<RolesVO>
     * @throws:RMDDAOException
     * @Description:This method is used to fetch the Roles which are not having the Case Management Privilege 
     */
    @Override
    public List<RolesVO> getNoCMRoles() throws RMDDAOException {
        List<RolesVO> arlRolesVO=null;
        RolesVO objRolesVO=null;
        StringBuilder rolesQuery=null;
        Session session=null;
        List<Object[]> result=null;
 
        try{            
            session=getHibernateSession();
            rolesQuery=new StringBuilder();
            rolesQuery.append("SELECT GET_USR_ROLES_SEQ_ID,ROLE_NAME FROM GET_USR.GET_USR_ROLES WHERE GET_USR_ROLES_SEQ_ID NOT IN (SELECT GET_USR_ROLES_SEQ_ID");
            rolesQuery.append(" FROM GET_USR_ROLES usr_roles,GET_USR_ROLE_PRIVILEGES role_prev,GET_USR_PRIVILEGE prev WHERE role_prev.LINK_ROLES = usr_roles.GET_USR_ROLES_SEQ_ID");
            rolesQuery.append(" AND role_prev.LINK_PRIVILEGES=prev.GET_USR_PRIVILEGE_SEQ_ID AND prev.PRIVILEGE_NAME =:privilegeName) ORDER BY ROLE_NAME");
            Query rolesHQuery=session.createSQLQuery(rolesQuery.toString());
            rolesHQuery.setParameter(RMDCommonConstants.PRIVILEGE_NAME, RMDCommonConstants.CASE_MANAGEMENT_PRIVILEGE);
            result=rolesHQuery.list();
            if(RMDCommonUtility.isCollectionNotEmpty(result)){
                arlRolesVO=new ArrayList<RolesVO>(result.size());
                for(Object[] rolesVO: result){
                    objRolesVO=new RolesVO();
                    objRolesVO.setGetUsrRolesSeqId(RMDCommonUtility.convertObjectToLong(rolesVO[0]));
                    objRolesVO.setRoleName(RMDCommonUtility.convertObjectToString(rolesVO[1]));
                    arlRolesVO.add(objRolesVO);
                }               
            }
            
        }catch (RMDDAOConnectionException ex) {       
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_NO_CM_ROLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {           
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_NO_CM_ROLES);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, null), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            rolesQuery=null;
            objRolesVO=null;
            result=null;
            releaseSession(session);
        }
        return arlRolesVO;
    }

	@Override
	public String deleteUsers(List<String> userdetails) throws RMDBOException {
		// TODO Auto-generated method stub
		List<Long> seqIds = null;
		 Session session = null;
	        Transaction transaction = null;
	        String responce = "success";
	        try {
	        	seqIds = getDeactiveUsers(userdetails);
	            session = getHibernateSession();
	            transaction = session.beginTransaction();
	            
               for(Long userId : seqIds){
            	   
            	   String alertQueryString = "select objid from GET_USR.GET_USR_NOTIFY_ALERT_CONF where notify_alert_conf2users_seqid =:userId";
            	   Query alertQuery = session.createSQLQuery(alertQueryString);
            	   alertQuery.setParameter(RMDCommonConstants.USERID, userId);
		            List list =alertQuery.list();
		            if(list.size() == 0){
            	   try{
			            //String deletePreferenceQueryString = "delete from GET_USR.GET_USR_USER_PREFERENCE where link_users = (select GET_USR_USERS_SEQ_ID from GET_USR_USERS where user_id =:userId))";
		            	   String deletePreferenceQueryString = "delete from GET_USR.GET_USR_USER_PREFERENCE where link_users =:userId";
			            Query preferenceQuery = session.createSQLQuery(deletePreferenceQueryString);
			            preferenceQuery.setParameter(RMDCommonConstants.USERID, userId);
			            preferenceQuery.executeUpdate();
			            String deleteRolequeryString = "delete from get_usr_user_roles where link_users =:userId";
			            Query roleQuery = session.createSQLQuery(deleteRolequeryString);
			            roleQuery.setParameter(RMDCommonConstants.USERID, userId);
			            roleQuery.executeUpdate();
			            String deleteUserQueryString = "DELETE FROM GET_USR_USERS where GET_USR_USERS_SEQ_ID =:userId";
			            Query userQuery = session.createSQLQuery(deleteUserQueryString);
			            userQuery.setParameter(RMDCommonConstants.USERID, userId);
			            userQuery.executeUpdate();
            	   }catch (Exception e) {
       	        	if(seqIds.size() > 0){
       	        		responce = "unsuccess";
       	        		continue;
       		        	}
       		        } 
		            }else{
		            	if(seqIds.size() > 0){
	       	        		responce = "unsuccess";
	       	        		continue;
	       		        	}
		            }
	            
               }
               transaction.commit();
	            session.close();
	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), ex, RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	        	if(seqIds.size() > 0){
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), e, RMDCommonConstants.MAJOR_ERROR);
	        	}
	        } finally {
	            releaseSession(session);
	        }
		
		LOG.info("DAO class UserIds: "+userdetails);
		return responce;
	}

	private  List<Long> getDeactiveUsers(List<String> userdetails) {
		// TODO Auto-generated method stub
		 Session session = null;
	        Transaction transaction = null;
	        List<Object[]> result=null;
	        List<UserServiceVO> usersVO=null;
	        UserServiceVO userServiceVO = null;
	        List<Long> userIds = null;
	        try {
	            session = getHibernateSession();
	            transaction = session.beginTransaction();
	            userIds = new ArrayList<Long>();
            for(String userId : userdetails){
	            String queryString = "from GetUsrUsersHVO where user_id=:userId";
	            Query  query = session.createQuery(queryString);
	            query.setParameter(RMDCommonConstants.USERID,userId);
	            query.setFetchSize(1);
	           
	            List<GetUsrUsersHVO> userList = query.list();
	            if (RMDCommonUtility.isCollectionNotEmpty(userList)) {
	            	  GetUsrUsersHVO objUserHVO = userList.get(0);
	            	Long id = objUserHVO.getGetUsrUsersSeqId();
                	userIds.add(id);
	            }
	           
	            /*if(RMDCommonUtility.isCollectionNotEmpty(result)){
	            	usersVO=new ArrayList<UserServiceVO>(result.size());
	            	userIds = new ArrayList<String>();
	                for(UserServiceVO userVO: usersVO){
	                	//userServiceVO=new UserServiceVO();
	                	String id = userVO.getUserId();
	                	userIds.add(id);
	                  
	                }               
	            }*/
	            
	           
	           // return userIds;
            }
            transaction.commit();
            session.close();
	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), ex, RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_USER_MANAGEMENT);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, "en"), e, RMDCommonConstants.MAJOR_ERROR);
	        } finally {
	            releaseSession(session);
	        }
		
		
	        return userIds;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("22222");
		list.add("1111111");
		UserDAOImpl dao = new UserDAOImpl();
		dao.getDeactiveUsers(list);
	}
	
}
