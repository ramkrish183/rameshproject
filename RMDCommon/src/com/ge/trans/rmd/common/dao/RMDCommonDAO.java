/**
 * ============================================================
 * Classification: GE Confidential
 * File : RMDCommonDAO.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.dao
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 16, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.common.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDAuditInterceptor;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Jan 16, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDCommonDAO implements Serializable {

	private static final RMDLogger LOG = RMDLogger.getLogger(RMDCommonDAO.class);
	private static final long serialVersionUID = 86249531L;
    private SessionFactory sessionFactory;
    public static final String DAO_EXCEPTION_GET_LOOKUP_VALUE = "DAO_EXCEPTION_GET_LOOKUP_VALUE";

    /**
     * 
     */
    public RMDCommonDAO() {
        super();
    }

    /**
     * @Author:
     * @param uName
     * @return
     * @Description:This method is used for get the hibernate session.
     */
    protected Session getHibernateSession(final String uName) throws RMDDAOConnectionException {
        Session hibernateSession = null;
        try {
            final RMDAuditInterceptor auditIntercepter = new RMDAuditInterceptor(uName);
            hibernateSession = SessionFactoryUtils.getNewSession(sessionFactory, auditIntercepter);
        } catch (Exception e) {
            LOG.error(e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return hibernateSession;
    }

    /**
     * @Author:
     * @param uName
     * @return
     * @Description:This method is used for get the hibernate session.
     */
    protected Session getHibernateSession() throws RMDDAOConnectionException {
        Session hibernateSession = null;
        try {
            hibernateSession = SessionFactoryUtils.getNewSession(sessionFactory);
        } catch (Exception e) {
            LOG.error(e);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return hibernateSession;
    }

    /**
     * @Author:
     * @param uName
     * @return
     * @Description:This method is used for release the hibernate session.
     */
    protected void releaseSession(final Session session) {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    /**
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Generating common product asset query
    protected String getProductQuery(String customerId) throws RMDDAOException {
        StringBuilder query = new StringBuilder();
        query.append("select TSP.objid from GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST,TABLE_SITE_PART TSP ");
        query.append(
                " ,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
        query.append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
        query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and ELGASST.PRODUCT_CD IN(:productNameLst) ");
        if (null != customerId && !RMDCommonConstants.EMPTY_STRING.equals(customerId)) {
            query.append("  and TBO.org_id IN ( :customerId )");
        }

        return query.toString();
    }

    // Generating common product asset query for DWQ
    protected String getProductQueryDWQ(String customerId) throws RMDDAOException {
        StringBuilder query = new StringBuilder();
        query.append(", TABLE_SITE_PART TSP");
        query.append(
                ",GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO,GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST");
        query.append(" where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
        query.append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
        query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and v1.serial_no=TSP.serial_no ");
        query.append(" and ELGASST.PRODUCT_CD IN(:productNameLst) ");
        if (null != customerId && !RMDCommonConstants.EMPTY_STRING.equals(customerId)) {
            query.append(" and TBO.org_id=:customerId");
        }

        return query.toString();
    }

    // Generating common product asset query For OMD
    protected String getOMDProductQuery() throws RMDDAOException {
        StringBuilder query = new StringBuilder();
        query.append(
                " SELECT GET_ASST_ASSET_SEQ_ID FROM GET_USR.GET_USR_USER_PRODUCT_ASST WHERE GET_USR_USER_PRODUCT_SEQ_ID IN ");
        query.append(
                "(SELECT GET_USR_USER_PRODUCT_SEQ_ID FROM GET_USR.GET_USR_USER_PRODUCT WHERE PRODUCT_DESC_TXT IN (:productNameLst)) ");
        return query.toString();
    }
    
    /**
	 * Method to return list of values based on description
	 * @param listDescription
	 * @return
	 * @throws RMDDAOException
	 */
    protected Map<String, String> getLookUpValues(String listDescription) throws RMDDAOException {
		Session objHibernateSession = null;
		Map<String, String> lookUpMap = new HashMap<String, String>();
		try {
			objHibernateSession = getHibernateSession();
			StringBuilder lookupbuffer = new StringBuilder();
			lookupbuffer.append("SELECT LIST_NAME, LOOK_VALUE FROM GETS_RMD.GETS_RMD_LOOKUP WHERE LIST_DESCRIPTION = :listDescription");
			Query lookupQuery = objHibernateSession.createSQLQuery(lookupbuffer.toString());
			lookupQuery.setParameter(RMDCommonConstants.LIST_DESC, listDescription);
			List<Object[]> lookupList = lookupQuery.list();
			if (lookupList != null && !lookupList.isEmpty()) {
				for(Object[] lookupArray : lookupList){
					lookUpMap.put(lookupArray[0].toString(), lookupArray[1].toString());
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(DAO_EXCEPTION_GET_LOOKUP_VALUE);
		} finally {
			releaseSession(objHibernateSession);
		}
		return lookUpMap;
	}
    
    protected String getProductQueryForRole(String roleId) throws RMDDAOException {
        StringBuilder query = new StringBuilder();
        query.append("SELECT GRV.OBJID FROM GETS_RMD_VEH_HDR GRHV,TABLE_BUS_ORG TBO,GETS_RMD_VEHICLE GRV,TABLE_SITE_PART TSP,GETS_RMD_OMD_PRODUCT_ASST PRODUCT, ");
        query.append("GET_USR_USER_PRODUCT_ROLE ROL WHERE GRHV.VEH_HDR2BUSORG = TBO.OBJID AND GRHV.objid = GRV.VEHICLE2VEH_HDR AND GRV.VEHICLE2SITE_PART = TSP.OBJID ");
        query.append("AND GRV.OBJID = PRODUCT.ASSET_ID AND PRODUCT.GET_RMD_OMD_PRODUCT_SEQ_ID=ROL.LINK_PRODUCT AND ROL.LINK_ROLES="+roleId);
        return query.toString();
    }

}
