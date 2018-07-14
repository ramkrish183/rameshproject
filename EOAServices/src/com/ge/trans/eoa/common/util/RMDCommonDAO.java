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
package com.ge.trans.eoa.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.TypedValue;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.ge.trans.eoa.cm.vo.User;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.valueobjects.UnitOfMeasureVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDCaseAcceptedException;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDAuditInterceptor;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * 
 * @Author :
 * @Version : 1.0
 * @Date Created: Jan 16, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
public class RMDCommonDAO implements Serializable {

    public static final RMDLogger LOG = RMDLogger.getLogger(RMDCommonDAO.class);
    public static final long serialVersionUID = 86249531L;
    private SessionFactory sessionFactory;
    private SessionFactory sessionFactoryDW;

    

    

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
    protected Session getHibernateSession(final String uName)
            throws RMDDAOConnectionException {
        Session hibernateSession = null;
        try {
            final RMDAuditInterceptor auditIntercepter = new RMDAuditInterceptor(
                    uName);
            hibernateSession = SessionFactoryUtils.getNewSession(
                    sessionFactory, auditIntercepter);
        }
        catch (Exception e) {
            LOG.error(e);
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[]{},
                    RMDCommonUtility.getMessage(errorCode, new String[]{},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.FATAL_ERROR);
        }
        return hibernateSession;
    }
    
    protected Session getDWHibernateSession()
            throws RMDDAOConnectionException {
        Session hibernateSession = null;
        try {
            hibernateSession = SessionFactoryUtils.getNewSession(sessionFactoryDW);
        }
        catch (Exception e) {
            LOG.error(e);
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[]{},
                    RMDCommonUtility.getMessage(errorCode, new String[]{},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
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
            hibernateSession = SessionFactoryUtils
                    .getNewSession(sessionFactory);
        }
        catch (Exception e) {
            LOG.error(e);
            final String errorCode = RMDCommonUtility
                    .getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDDAOConnectionException(errorCode, new String[]{},
                    RMDCommonUtility.getMessage(errorCode, new String[]{},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
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
    
    protected Connection getConnection(Session session) throws SQLException{
        Connection connection = null;
        if (session != null && session.isOpen()) {
            connection =((SessionFactoryImplementor)session.getSessionFactory()).getConnectionProvider().getConnection(); 
        }
        return connection;
    }
    public void closeConnection(Connection conn) throws RMDDAOException
    {
              try
              {
                  if(null!=conn){
                      conn.close();   
                  }
              }
              catch (Exception excp)
              {
                  String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), excp,
                        RMDCommonConstants.MAJOR_ERROR);
              }
    }
    /**
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void setSessionFactoryDW(SessionFactory sessionFactoryDW) {
        this.sessionFactoryDW = sessionFactoryDW;
    }
    //Generating common product asset query 
    protected String getProductQuery(String customerId)throws RMDDAOException{
        StringBuilder query=new StringBuilder();
        query.append("select TSP.objid from GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST,TABLE_SITE_PART TSP ");  
        query.append(" ,GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART "); 
        query.append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID "); 
        query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and ELGASST.PRODUCT_CD in(:productNameLst) ");
        if (null != customerId
                && !RMDCommonConstants.EMPTY_STRING
                        .equals(customerId)) {
            query.append(" and TBO.org_id IN ( :customerId )");
        }
        
        
        return query.toString();
    }
    //Generating common product asset query for DWQ
        protected String getProductQueryDWQ(String customerId)throws RMDDAOException{
            StringBuilder query=new StringBuilder();
            query.append(", TABLE_SITE_PART TSP");
            query.append(",GETS_RMD_VEHICLE VEH,GETS_RMD_VEH_HDR VEHHDR,TABLE_BUS_ORG TBO,GETS_RMD.GETS_RMD_OMD_PRODUCT_ASST ELGASST");
            query.append(" where ELGASST.ASSET_ID=VEH.OBJID AND TSP.OBJID = VEH.VEHICLE2SITE_PART ");
            query.append(" AND VEH.VEHICLE2VEH_HDR = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID ");
            query.append(" AND TSP.SERIAL_NO NOT LIKE '%BAD%' and v1.serial_no=TSP.serial_no ");
            query.append(" and ELGASST.PRODUCT_CD IN(:productNameLst) ");
            if (null != customerId
                    && !RMDCommonConstants.EMPTY_STRING
                            .equals(customerId)) {
                query.append(" and TBO.org_id=:customerId");
            }
            
            return query.toString();
        }
    
        //Generating common product asset query For OMD 
        protected String getOMDProductQuery()throws RMDDAOException{
            StringBuilder query=new StringBuilder();
            query.append(" SELECT GET_ASST_ASSET_SEQ_ID FROM GET_USR.GET_USR_USER_PRODUCT_ASST WHERE GET_USR_USER_PRODUCT_SEQ_ID IN ");
            query.append("(SELECT GET_USR_USER_PRODUCT_SEQ_ID FROM GET_USR.GET_USR_USER_PRODUCT WHERE PRODUCT_DESC_TXT IN (:productNameLst)) ");
            return query.toString();
        }
        /**
         * @Author:
         * @return String
         * @param caseId
         * @Description: To fetch the customer name for a case
         */
        public String getCustomerNameForCase(String caseId) {
            Session session = null;
            try {
                session = getHibernateSession();
                final String strCustomerNameQuery = "SELECT asset.CUST_NAME FROM TABLE_CASE tc, GETS_RMD_CUST_RNH_RN_V asset "
                        + "WHERE tc.CASE_PROD2SITE_PART = asset.SITE_PART_OBJID AND tc.ID_NUMBER = :caseId ";
                final Query queryCustomerName = session
                        .createSQLQuery(strCustomerNameQuery.toString());
                queryCustomerName.setParameter(RMDCommonConstants.CASEID, caseId);
                final String custName = queryCustomerName.uniqueResult().toString()
                        .trim();
                return custName;
            } catch (RMDDAOConnectionException ex) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                        RMDCommonConstants.FATAL_ERROR);
            } catch (Exception e) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.INSERT_ACTIVITY_ERROR_CODE);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } finally {
                releaseSession(session);
            }
        }
        /*
         * @param String
         * This method is used to retrieve a user
         */
        public User getUser(String username){
            User user = new User();
            Session objSession = getHibernateSession();
            Long userId = Long.parseLong(getUserSequenceID(objSession,username));
            String userName = getEoaUserName(objSession, username);
            user.setUserId(userId);
            user.setLoginName(userName);
            releaseSession(objSession);
            return user;
        }
        /**
         * 
         * This method is added for getting EOA get UserSequenceID by passing
         * Session and userId
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public String getUserSequenceID(Session hibernateSession, String userId) {
            final String strUserSeqIDQuery = "select OBJID FROM TABLE_USER WHERE WEB_LOGIN = :userId";
            final Query queryUserSeqID = hibernateSession
                    .createSQLQuery(strUserSeqIDQuery.toString());
            queryUserSeqID.setParameter(RMDCommonConstants.USERID, userId);
            final String userSeqID = queryUserSeqID.uniqueResult().toString();
            return userSeqID;
        }

        /**
         * @throws RMDCaseAcceptedException
         *             This method is added for getting EOA user First Name for
         *             passing Session and userId
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public String getEoaUserName(Session hibernateSession, String userId) {
            String strEoaUserName = null;
            Query hibernateQuery = hibernateSession.createSQLQuery("SELECT A.LOGIN_NAME FROM TABLE_USER A WHERE UPPER(A.WEB_LOGIN) =UPPER(:userId)");
            hibernateQuery.setParameter(RMDCommonConstants.USERID, userId);
            Object result =hibernateQuery.uniqueResult();
            if(null != result){
                strEoaUserName = result.toString();
            }
            return strEoaUserName;
        }
        /*
         * @param String
         * This method is to return the vehicleobj id.
         */
        public List getVehicleObjId(String strVehNo,String strCusID,String strAstGrp){
            Session objHibernateSession = getHibernateSession();
            StringBuilder getVehicleObjid = new StringBuilder();
            getVehicleObjid
                    .append("SELECT VEH.OBJID FROM  TABLE_SITE_PART TSP, GETS_RMD_VEHICLE VEH, GETS_RMD_VEH_HDR VEHHDR, TABLE_BUS_ORG TBO ");
            getVehicleObjid
                    .append("WHERE TSP.OBJID = VEH.VEHICLE2SITE_PART AND VEH.VEHICLE2VEH_HDR   = VEHHDR.OBJID AND VEHHDR.VEH_HDR2BUSORG = TBO.OBJID AND TSP.SERIAL_NO NOT LIKE '%BAD%' ");
            if (null != strVehNo) {
                getVehicleObjid
                        .append("AND TSP.SERIAL_NO = :vehNum AND TSP.X_VEH_HDR=:grpNam ");
            }
            if (null != strCusID) {
                getVehicleObjid.append("AND TBO.ORG_ID =:custID ");
            }
            Query qGetVehicleObjidQry = objHibernateSession
                    .createSQLQuery(getVehicleObjid.toString());
            if (null != strVehNo) {
                qGetVehicleObjidQry.setParameter(RMDCommonConstants.VEH_NUM,
                        strVehNo);
                qGetVehicleObjidQry.setParameter(RMDCommonConstants.GRPNAM,
                        strAstGrp);
            }
            if (null != strCusID) {
                qGetVehicleObjidQry.setParameter(RMDCommonConstants.CUSTID,
                        strCusID);
            }
            List arlGetVehObjId = (ArrayList) qGetVehicleObjidQry.list();
            releaseSession(objHibernateSession);
            return arlGetVehObjId;
        }
        /**
         * 
         * This method is to retrieve the cust fdbk table objid for a
         * caseId and recomObjid
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public int getCustFdbkObjid(Session hibernateSession, String rxCaseId) {
            final String strCustFdbkObjidQuery = "SELECT CUST_FDBK_OBJID FROM GETS_SD_FDBKPNDG_V WHERE RX_CASE_ID = :caseId";
            String strCustFdbkObjid = null;
            final Query queryCustFdbkObjid = hibernateSession
                    .createSQLQuery(strCustFdbkObjidQuery.toString());
            queryCustFdbkObjid.setParameter(RMDCommonConstants.CASEID, rxCaseId);
            Object result = queryCustFdbkObjid.uniqueResult();
            if(null != result){
                strCustFdbkObjid = result.toString();
            }
            if(null != strCustFdbkObjid && !strCustFdbkObjid.isEmpty()){
                return Integer.parseInt(strCustFdbkObjid);
            }
            return 0;
        }   
        /**
         * 
         * This method is to retrieve the cust fdbk table objid for a
         * caseId and recomObjid
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public String getCurrentRecomObjid(Session hibernateSession, String rxCaseId) {
            final String strCurrentRecomObjidQuery = "SELECT RECOM_OBJID FROM GETS_SD_FDBKPNDG_V  WHERE RX_CASE_ID = :caseId  ";
            String strCurrentRecomObjid = null;
            final Query queryCurrentRecomObjid = hibernateSession
                    .createSQLQuery(strCurrentRecomObjidQuery.toString());
            queryCurrentRecomObjid.setParameter(RMDCommonConstants.CASEID, rxCaseId);
            Object result = queryCurrentRecomObjid.uniqueResult();
            if(null != result){
                strCurrentRecomObjid = result.toString();
            }
            return strCurrentRecomObjid;
        }   
        /**
         * 
         * This method is to retrieve the cust fdbk table objid for a
         * caseId and recomObjid
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public String getCaseObjid(Session hibernateSession, String caseId) {
            final String strCaseObjidQuery = "SELECT OBJID FROM TABLE_CASE WHERE ID_NUMBER = :caseId";
            String strCaseObjid = null;
            final Query queryCaseObjid = hibernateSession.createSQLQuery(strCaseObjidQuery.toString());
            queryCaseObjid.setParameter(RMDCommonConstants.CASEID, caseId);
            Object result = queryCaseObjid.uniqueResult();
            if(null != result){
                strCaseObjid = result.toString();
            }
            return strCaseObjid;
        }   
        
        /**
         * 
         * This method is to retrieve the cust fdbk table objid for a
         * caseId and recomObjid
         * 
         * @param hibernateSession
         *            ,userId
         * @return String
         * 
         */
        public String getCaseType(Session hibernateSession, String caseId) {
            final String strCaseTypeQuery = " SELECT tcasetype.TITLE FROM   TABLE_CASE tc, TABLE_GBST_ELM tcasetype "
                    +"WHERE  tc.ID_NUMBER =:caseId AND  tc.CALLTYPE2GBST_ELM = tcasetype.OBJID ";
            String strCaseType = null;
            final Query queryCaseType = hibernateSession
                    .createSQLQuery(strCaseTypeQuery.toString());
            queryCaseType.setParameter(RMDCommonConstants.CASEID, caseId);
            Object result = queryCaseType.uniqueResult();
            if(null != result){
                strCaseType = result.toString();
            }
            return strCaseType;
        }
        /**
         * @param caseId
         * @throws RMDDAOException
         */
        public boolean isValidEOACase(String caseId) throws RMDDAOException {
            Session objSession = null;
            String caseIdEoa = null;
            boolean isValid = true;
            try {
                LOG.debug("Begin isValidEOACase method of CaseEoaDAOImpl");
                objSession = getHibernateSession();
                StringBuilder caseQry = new StringBuilder();
                caseQry.append("SELECT ID_NUMBER FROM TABLE_CASE WHERE ID_NUMBER =:caseId");
                Query caseHqry = objSession.createSQLQuery(caseQry.toString())
                        .setParameter(RMDCommonConstants.CASEID, caseId);
                caseIdEoa = (String) caseHqry.uniqueResult();
                if (null == caseIdEoa || caseIdEoa.isEmpty()) {
                    isValid = false;
                }
            } catch (RMDDAOConnectionException ex) {
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_CURRENT_OWNER_EOA);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                        RMDCommonConstants.FATAL_ERROR);
            } catch (Exception e) {
            	LOG.error(e);
                throw new RMDDAOException();
            } finally {
                releaseSession(objSession);
            }
            LOG.debug("Ends isValidEOACase method of CaseEoaDAOImpl");
            return isValid;
        }
		public String getConversionFormula(String sourceUom,boolean targetToSource) throws RMDDAOConnectionException,RMDDAOException,Exception {
			Session hibernateSession = null;
			Query queryMPName = null;
			String strMpName = null;
			List<String> lstMPName = null;			
			try {
				hibernateSession = getHibernateSession();
				StringBuilder strBuffer=new StringBuilder();
				if (!RMDCommonUtility.checkNull(hibernateSession)
						&& !RMDCommonUtility.isNullOrEmpty(sourceUom)) {
					strBuffer.append("SELECT UOM.CONVERSION_EXP FROM GETS_RMD_UNIT_OF_MEASURE M1,GETS_RMD_UNIT_OF_MEASURE M2,GETS_RMD_UOM_CONVERSION UOM");
					strBuffer.append(" WHERE M1.UOM_TYPE=M2.UOM_TYPE");
					if (targetToSource) {
						strBuffer.append(" AND M1.OBJID=TARGETUOM");
						strBuffer.append(" AND M2.OBJID=SOURCEUOM");
					} else {
						strBuffer.append(" AND M1.OBJID=SOURCEUOM");
						strBuffer.append(" AND M2.OBJID=TARGETUOM");
					}
					strBuffer.append(" AND M1.CONVERSION_REQUIRED=1");
					strBuffer.append(" AND M2.CONVERSION_REQUIRED=1 AND M1.OBJID=:strObjId");
					queryMPName = hibernateSession.createSQLQuery(strBuffer.toString());
					queryMPName.setParameter(RMDServiceConstants.OBJID, sourceUom);
					lstMPName = queryMPName.list();
					if (RMDCommonUtility.isCollectionNotEmpty(lstMPName)) {						
						strMpName=lstMPName.get(0);						
					}
				}
			} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
				throw objRMDDAOConnectionException;
			} catch (RMDDAOException objRMDDAOException) {
				throw objRMDDAOException;
			} catch (Exception objException) {
				throw objException;
			}
			finally{
				queryMPName=null;
				releaseSession(hibernateSession);
			}
			return strMpName;
		}
		
		/**
		 * @Author:iGATE
		 * @return
		 * @throws Exception 
		 * @Description: This method will return the MP name for the column passed
		*/
		public String getSourceUom(Session hibernateSession, String columnObjid,
				String strFamily) throws RMDDAOConnectionException,RMDDAOException,Exception {
			Query queryMPName = null;
			String sourceUom = null;
			List<BigDecimal> lstMPName = null;
			String[] strFamilyArray=null;
			try {
				if (!RMDCommonUtility.checkNull(hibernateSession)
						&& !RMDCommonUtility.isNullOrEmpty(columnObjid)
						&& !RMDCommonUtility.isNullOrEmpty(strFamily)) {
					queryMPName = hibernateSession.createSQLQuery(RMDServiceConstants.QueryConstants.FETCH_SOURCE_UOM
									.toString());

					strFamilyArray=strFamily.split(RMDCommonConstants.COMMMA_SEPARATOR);				
					queryMPName.setParameterList(RMDServiceConstants.FAMILY, strFamilyArray);		
					queryMPName.setParameter(RMDServiceConstants.COLUMN_OBJID, columnObjid);
					lstMPName = queryMPName.list();
					if (RMDCommonUtility.isCollectionNotEmpty(lstMPName)) {
						BigDecimal objBigDecimal = (BigDecimal) lstMPName.get(0);		
						sourceUom=objBigDecimal.toString();
					}
				}
			} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
				throw objRMDDAOConnectionException;
			} catch (RMDDAOException objRMDDAOException) {
				throw objRMDDAOException;
			} catch (Exception objException) {
				throw objException;
			}
			finally{
				queryMPName=null;
			}
			return sourceUom;
		}
		
		/**
		 * @Author:iGATE
		 * @return
		 * @throws Exception 
		 * @Description: This method will return the MP name for the column passed
		*/
		public Map<String,UnitOfMeasureVO> convertSourceToTarget(String defaultUom) throws RMDDAOConnectionException,RMDDAOException,Exception {
			Session hibernateSession = null;
			Query queryUomDetails = null;
			List<Object[]> lstUomDetails = null;
			List<Object[]> lstUomNoConvDetails = null;
			Map<String,UnitOfMeasureVO> mpUom=new HashMap<String, UnitOfMeasureVO>();
			UnitOfMeasureVO objMeasureVO=null;					
			try {
				hibernateSession = getHibernateSession();
				StringBuilder strBuffer=new StringBuilder();
				strBuffer.append("SELECT M1.OBJID SOURCE,M1.ABBREVIATION SOURCEABBR,M2.OBJID TARGET,M2.ABBREVIATION TARGETABBR,UOM.CONVERSION_EXP");
				strBuffer.append(" FROM GETS_RMD_UNIT_OF_MEASURE M1,GETS_RMD_UNIT_OF_MEASURE M2,GETS_RMD_UOM_CONVERSION UOM");	
				strBuffer.append(" WHERE M1.UOM_TYPE=M2.UOM_TYPE");				
				strBuffer.append(" AND M1.OBJID=SOURCEUOM");
				strBuffer.append(" AND M2.OBJID=TARGETUOM");
				strBuffer.append(" AND M1.CONVERSION_REQUIRED=1");
				strBuffer.append(" AND M2.CONVERSION_REQUIRED=1");
				if(null!=defaultUom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(defaultUom)&&!RMDCommonConstants.US.equalsIgnoreCase(defaultUom)){
				strBuffer.append(" AND M2.MEAS_SYS_ID=(SELECT OBJID FROM GETS_RMD_LOOKUP WHERE LOOK_VALUE IN (:uom))");
				}
				queryUomDetails = hibernateSession.createSQLQuery(strBuffer.toString());	
				if(null!=defaultUom&&!RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(defaultUom)&&!RMDCommonConstants.US.equalsIgnoreCase(defaultUom)){
				queryUomDetails.setParameter(RMDServiceConstants.UOM, defaultUom);
				}
				lstUomDetails = queryUomDetails.list();
					if (RMDCommonUtility.isCollectionNotEmpty(lstUomDetails)) {						
						for (Object obj[] : lstUomDetails) {
							objMeasureVO=new UnitOfMeasureVO();
							BigDecimal sourceUom = (BigDecimal) obj[0];	
							BigDecimal tragetUom = (BigDecimal) obj[2];	
							objMeasureVO.setSourceUom(sourceUom.toString());
							objMeasureVO.setTargetUom(tragetUom.toString());
							objMeasureVO.setSourceAbbr(RMDCommonUtility.convertObjectToString(obj[1]));
							objMeasureVO.setTargetAbbr(RMDCommonUtility.convertObjectToString(obj[3]));
							objMeasureVO.setConversionExp(RMDCommonUtility.convertObjectToString(obj[4]));
							objMeasureVO.setIsConversionRequired(RMDCommonConstants.ONE_STRING);
							mpUom.put(sourceUom.toString(),objMeasureVO);
							objMeasureVO=null;
						  }				
					}
					
					
					strBuffer=new StringBuilder();
					strBuffer.append("SELECT OBJID,ABBREVIATION FROM GETS_RMD_UNIT_OF_MEASURE");
					strBuffer.append(" WHERE CONVERSION_REQUIRED=0");
					Query queryUomNoConvDetails = hibernateSession.createSQLQuery(strBuffer.toString());				
					lstUomNoConvDetails = queryUomNoConvDetails.list();
						if (RMDCommonUtility.isCollectionNotEmpty(lstUomNoConvDetails)) {						
							for (Object obj[] : lstUomNoConvDetails) {
								objMeasureVO=new UnitOfMeasureVO();
								BigDecimal sourceUom = (BigDecimal) obj[0];	
								objMeasureVO.setSourceUom(sourceUom.toString());
								objMeasureVO.setSourceAbbr(RMDCommonUtility.convertObjectToString(obj[1]));
								objMeasureVO.setIsConversionRequired(RMDCommonConstants.ZERO_STRING);
								mpUom.put(sourceUom.toString(),objMeasureVO);
								objMeasureVO=null;
							  }				
						}
				
			} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
				throw objRMDDAOConnectionException;
			} catch (RMDDAOException objRMDDAOException) {
				throw objRMDDAOException;
			} catch (Exception objException) {
				throw objException;
			}
			finally{
				queryUomDetails=null;
				releaseSession(hibernateSession);
			}
			return mpUom;
		
		}
		
		
		/**
		 * @Author:iGATE
		 * @return
		 * @throws Exception 
		 * @Description: This method will return the MP name for the column passed
		*/
		public Map<String,UnitOfMeasureVO> convertTargetTOSource(String defaultUom) throws RMDDAOConnectionException,RMDDAOException,Exception {
			Session hibernateSession = null;
			Query queryUomDetails = null;
			List<Object[]> lstUomDetails = null;
			Map<String,UnitOfMeasureVO> mpUom=new HashMap<String, UnitOfMeasureVO>();
			UnitOfMeasureVO objMeasureVO=null;
					
			try {
				hibernateSession = getHibernateSession();
				StringBuilder strBuffer=new StringBuilder();
				strBuffer.append("SELECT M1.OBJID SOURCE,M1.ABBREVIATION SOURCEABBR,M2.OBJID TARGET,M2.ABBREVIATION TARGETABBR,UOM.CONVERSION_EXP");
				strBuffer.append(" FROM GETS_RMD_UNIT_OF_MEASURE M1,GETS_RMD_UNIT_OF_MEASURE M2,GETS_RMD_UOM_CONVERSION UOM");	
				strBuffer.append(" WHERE M1.UOM_TYPE=M2.UOM_TYPE");				
				strBuffer.append(" AND M1.OBJID=TARGETUOM");
				strBuffer.append(" AND M2.OBJID=SOURCEUOM");
				strBuffer.append(" AND M1.CONVERSION_REQUIRED=1");
				strBuffer.append(" AND M2.CONVERSION_REQUIRED=1");
				strBuffer.append(" AND M2.MEAS_SYS_ID=(SELECT OBJID FROM GETS_RMD_LOOKUP WHERE LOOK_VALUE IN (:uom))");
				queryUomDetails = hibernateSession.createSQLQuery(strBuffer.toString());	
				queryUomDetails.setParameter(RMDServiceConstants.UOM, defaultUom);				
				lstUomDetails = queryUomDetails.list();
					if (RMDCommonUtility.isCollectionNotEmpty(lstUomDetails)) {						
						for (Object obj[] : lstUomDetails) {
							objMeasureVO=new UnitOfMeasureVO();
							BigDecimal sourceUom = (BigDecimal) obj[0];	
							BigDecimal targetUom = (BigDecimal) obj[2];	
							String strSourceUom=sourceUom.toString();
							String strTargetUom=targetUom.toString();
							objMeasureVO.setSourceUom(strSourceUom);
							objMeasureVO.setTargetUom(strTargetUom);
							objMeasureVO.setSourceAbbr(RMDCommonUtility.convertObjectToString(obj[1]));
							objMeasureVO.setTargetAbbr(RMDCommonUtility.convertObjectToString(obj[3]));
							objMeasureVO.setConversionExp(RMDCommonUtility.convertObjectToString(obj[4]));
							objMeasureVO.setIsConversionRequired(RMDCommonConstants.ONE_STRING);
							mpUom.put(strSourceUom,objMeasureVO);
							objMeasureVO=null;
						  }				
					}
				
			} catch (RMDDAOConnectionException objRMDDAOConnectionException) {
				throw objRMDDAOConnectionException;
			} catch (RMDDAOException objRMDDAOException) {
				throw objRMDDAOException;
			} catch (Exception objException) {
				throw objException;
			}
			finally{
				queryUomDetails=null;
				releaseSession(hibernateSession);
			}
			return mpUom;
		
		}
		
		
		// This class is used to override the escape characters in the value being
		// substituted in where clause
		public class LikeExpressionWithEscapeCharacters implements Criterion {

			private static final long serialVersionUID = 1L;

			private final String propertyName;
			private final String value;
			private final Character escapeChar;

			public LikeExpressionWithEscapeCharacters(String propertyName,
					Object value) {
				this(propertyName, value.toString(), (Character) null);
			}

			public LikeExpressionWithEscapeCharacters(String propertyName,
					String value, MatchMode matchMode) {
				this(propertyName, matchMode.toMatchString(value.toString()
						.replaceAll("!", "!!").replaceAll("%", "!%")
						.replaceAll("_", "!_")), '!');
			}

			public LikeExpressionWithEscapeCharacters(String propertyName,
					String value, Character escapeChar) {
				this.propertyName = propertyName;
				this.value = value;
				this.escapeChar = escapeChar;
			}

			public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
					throws HibernateException {
				Dialect dialect = criteriaQuery.getFactory().getDialect();
				String[] columns = criteriaQuery.getColumnsUsingProjection(
						criteria, propertyName);
				if (columns.length != 1) {
					throw new HibernateException(
							"Like may only be used with single-column properties");
				}
				String lhs = lhs(dialect, columns[0]);
				return lhs
						+ " like ?"
						+ (escapeChar == null ? RMDCommonConstants.EMPTY_STRING
								: " escape ?");

			}

			public TypedValue[] getTypedValues(Criteria criteria,
					CriteriaQuery criteriaQuery) throws HibernateException {
				return new TypedValue[] {
						criteriaQuery.getTypedValue(criteria, propertyName,
								typedValue(value)),
						criteriaQuery.getTypedValue(criteria, propertyName,
								escapeChar.toString()) };
			}

			protected String lhs(Dialect dialect, String column) {
				return dialect.getLowercaseFunction() + '(' + column + ')';
			}

			protected String typedValue(String value) {
				return value.toLowerCase();
			}

		}
}
