package com.ge.trans.eoa.services.rxchange.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Value;

import com.ge.trans.eoa.services.alert.service.valueobjects.ModelVO;
import com.ge.trans.eoa.services.asset.dao.intf.HealthCheckDAOIntf;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.common.util.EmailContentGenerator;
import com.ge.trans.eoa.services.common.util.RxchangePDFGenerator;
import com.ge.trans.eoa.services.rxchange.dao.intf.RxChangeDAOIntf;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeAdminVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeSearchVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeVO;
import com.ge.trans.eoa.services.rxchange.service.valueobjects.RxChangeWhitePaperPDFVO;
import com.ge.trans.eoa.services.security.service.valueobjects.UserServiceVO;
import com.ge.trans.eoa.services.tools.rx.dao.intf.SelectRxEoaDAOIntf;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.RecommDelvDocVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.omi.exception.FileNotFoundException;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.Base64;

public class RxChangeDAOImpl extends BaseDAO implements RxChangeDAOIntf {

    /**
     * 
     */
	HealthCheckDAOIntf objHealthCheckDAOIntf;
    
	/**
     * @param objSelectRxDAOIntf the objSelectRxDAOIntf to set
     */
    public void setObjHealthCheckDAOIntf(HealthCheckDAOIntf objHealthCheckDAOIntf) {
        this.objHealthCheckDAOIntf = objHealthCheckDAOIntf;
    }
    private static final long serialVersionUID = -4217239969290256530L;
    private static final RMDLogger rxChangeDaoLog = RMDLoggerHelper
            .getLogger(RxChangeDAOImpl.class);
    
    @Value("${" + RMDCommonConstants.MAIL_SERVER_HOST + "}")
    String mailServerHost;
    @Value("${" + RMDCommonConstants.SMTP_SERVER + "}")
    String smtpServerName;
    @Value("${" + RMDCommonConstants.FROM_DL + "}")
    String fromDL;
    @Value("${" + RMDCommonConstants.TO_DL + "}")
    String toDL;    
	@Value("${" + RMDCommonConstants.DELV_RX_PDF_PATH + "}")
	String delvRxPDFPath;
	@Value("${" + RMDCommonConstants.PDF_URL + "}")
	String pdfURL;
//    @Value("${" + RMDCommonConstants.CONNECTION_URL + "}")
//    String connectionURL;    

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ge.trans.eoa.services.rxchange.dao.intf.RxChangeDAOIntff#getRxChangeOverviewData
     * ()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RxChangeVO> getRxChangeOverviewData(RxChangeSearchVO rxChangeSearchVO)
            throws RMDDAOException {
        Session objSession = null;
        List<RxChangeVO> rxChangeVOLst = new ArrayList<RxChangeVO>();
        RxChangeVO rxChangeVO = null;
               
        try { 
            
            objSession = getHibernateSession();
            StringBuilder rxChangeQuery = new StringBuilder();
            
            rxChangeQuery.append(RMDServiceConstants.SQL_RX_CHANGE_OVERVIEW_QUERY);
            
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxChangeReqObjId())){                   
                rxChangeQuery.append(" AND RXREQ.RX_CHANGE_REQ_SEQ_ID  = :rxChangeReqObjId");
            }
            
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getUserId())){                 
                rxChangeQuery.append(" AND USERS.USER_ID = :userID");
            }
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getCustomerIdLst()))
                rxChangeQuery.append(" AND CUST.LINK_CUSTOMER IN(:customer)");
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getModelLst())){
                rxChangeQuery.append(" AND RXMODEL.LINK_MODEL IN(:model)");             
            }

            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getStatus())){
                rxChangeQuery.append(" AND RXREQ.RX_REQUEST_STATUS IN(:status)");               
            }
                
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO
                    .getRxTitle())){
                rxChangeQuery.append(" AND (UPPER(RX_TITLE_GENERAL) LIKE '%"
                        + rxChangeSearchVO.getRxTitle().toUpperCase()
                        + "%' OR  UPPER(RECOM.TITLE) like '%"
                        + rxChangeSearchVO.getRxTitle().toUpperCase() + "%')");           
            }
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getTypeOfChangeLst())){
                rxChangeQuery.append(" AND TYPE.LINK_TYPEOFCHANGE IN(:typeOfRxChange)");                
            }           
            rxChangeQuery.append(RMDServiceConstants.SQL_RX_CHANGE_OVERVIEW_FIRST_INNER_QUERY_END);
            
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxChangeReqObjId())){                   
                rxChangeQuery.append(" AND RXREQ.RX_CHANGE_REQ_SEQ_ID  = :rxChangeReqObjId");
            }                       
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getUserId())){                 
                rxChangeQuery.append(" AND USERS.USER_ID = :userID");
            }
        
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getCustomerIdLst()))
                rxChangeQuery.append(" AND CUST.LINK_CUSTOMER IN(:customer)");
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getModelLst())){
                rxChangeQuery.append(" AND RXMODEL.LINK_MODEL IN(:model)");             
            }

            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getStatus())){
                rxChangeQuery.append(" AND RXREQ.RX_REQUEST_STATUS IN(:status)");               
            }
            if (!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxTitle())) {
                rxChangeQuery.append(" AND (UPPER(RX_TITLE_GENERAL) LIKE '%"
                        + rxChangeSearchVO.getRxTitle().toUpperCase()
                        + "%' OR  UPPER(RECOM.TITLE) like '%"
                        + rxChangeSearchVO.getRxTitle().toUpperCase() + "%')");
            }
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getTypeOfChangeLst())){
                rxChangeQuery.append(" AND TYPE.LINK_TYPEOFCHANGE IN(:typeOfRxChange)");                
            }
            
            rxChangeQuery.append(RMDServiceConstants.SQL_RX_CHANGE_OVERVIEW_GROUP_BY_CONDITION);
        
            Query rxChangeHqry = objSession.createSQLQuery(rxChangeQuery.toString());

            if(!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxChangeReqObjId())){
                rxChangeHqry.setParameter(RMDCommonConstants.RX_CHANGE_REQ_OBJ_ID,
                        rxChangeSearchVO.getRxChangeReqObjId());
            }       
            
            if(!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getUserId())){
                rxChangeHqry.setParameter(RMDCommonConstants.USER_ID,
                        rxChangeSearchVO.getUserId());
            }               
            if(RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO.getCustomerIdLst())){
                rxChangeHqry.setParameterList(RMDCommonConstants.CUSTOMER,
                        rxChangeSearchVO.getCustomerIdLst());
            }               
            if(RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO.getModelLst()))
                rxChangeHqry.setParameterList(RMDCommonConstants.MODEL,
                    rxChangeSearchVO.getModelLst());
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO
                    .getStatus()))
                rxChangeHqry.setParameterList(RMDCommonConstants.STATUS,
                        rxChangeSearchVO.getStatus());
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO.getTypeOfChangeLst())) {
                rxChangeHqry.setParameterList(RMDCommonConstants.TYPE_OF_RX_CHANGE,
                        rxChangeSearchVO.getTypeOfChangeLst());
            }
                                
            List<Object[]> arlResults = rxChangeHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                for (Object[] rxChangeObj : arlResults) {
                    rxChangeVO = new RxChangeVO();
                    rxChangeVO.setObjid(RMDCommonUtility
                            .convertObjectToLong(rxChangeObj[0]));
                    String rxRequestId = "RxReq"+RMDCommonUtility
                            .convertObjectToString(rxChangeObj[0]);
                    rxChangeVO.setRequestId(rxRequestId);
                    rxChangeVO.setRequestor(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[1]));
                    if(!RMDCommonUtility.isNullOrEmpty(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[2]))){
                        rxChangeVO.setRevisionType(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[2]));
                    }else
                        rxChangeVO.setRevisionType(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[3]));
                    rxChangeVO.setRoadNumber(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[4]));
                    rxChangeVO.setCaseId(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[5]));
                    if(null!=rxChangeObj[6]){
                        rxChangeVO.setRxTitle(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[6]));
                    }else
                        rxChangeVO.setRxTitle(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[3]));
                    if (null != rxChangeObj[7]) {
                          Clob cb = (Clob) rxChangeObj[7];
                          if(cb != null && cb.length() > 0){
                              rxChangeVO.setChangesSuggested(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }                       
                    }
                    rxChangeVO.setStatus(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[8]));
                    if (null != rxChangeObj[9]) { 
                          Clob cb = (Clob) rxChangeObj[9];
                          if(cb != null && cb.length() > 0){
                              rxChangeVO.setNotes(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }             
                    }
                    rxChangeVO.setRequestLoggedDate(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[10]));
                    
                    rxChangeVO.setModel(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[12]));
                    rxChangeVO.setCustomer(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[13]));
                    rxChangeVO.setTypeOfRxChange(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[14]));
                    
                    
                    if(null!=rxChangeObj[15])
                    rxChangeVO.setFileName(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[15]));
                    
                    if(null!=rxChangeObj[16]){
                        String[] requestOwner = RMDCommonUtility
                                .convertObjectToString(rxChangeObj[16]).split(
                                        Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER));
                        if (null != requestOwner && requestOwner.length > 0) {
                            rxChangeVO.setRequestOwner(requestOwner[0]);    
                            rxChangeVO.setRequestOwnerSSO(requestOwner[1]);    
                        }
                    }
                
                    rxChangeVO.setUserEmail(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[19]));
                 /*   rxChangeVO.setFileName(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[20]));
                    rxChangeVO.setFilePath(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[19]));*/
                    if(rxChangeObj[18]!=null && rxChangeObj[17]!=null){
                        String userName = RMDCommonUtility
                                .convertObjectToString(rxChangeObj[18])+", "+RMDCommonUtility
                                .convertObjectToString(rxChangeObj[17]);
                        rxChangeVO.setUserName(userName);
                    }   
                    if(rxChangeObj[20]!=null){
                        rxChangeVO.setSaveAsDraftFlag(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[20]));   
                    }
                    if(rxChangeObj[21]!=null){
                        rxChangeVO.setRxChangeProcObjId(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[21]));
                    }       
                    if(rxChangeObj[22]!=null){
                          Clob cb = (Clob) rxChangeObj[22];
                          if(cb != null && cb.length() > 0){
                              rxChangeVO.setNotestoRequestor(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }    
                    }   
                    if(rxChangeObj[23]!=null){
                          rxChangeVO.setWhitePaperPdffileName(RMDCommonUtility
                                    .convertObjectToString(rxChangeObj[23]));
                            
                        }
                    if(rxChangeObj[24]!=null){
                          rxChangeVO.setWhitePaperPdffilePath(RMDCommonUtility
                                    .convertObjectToString(rxChangeObj[24]));
                         
                        }
                    if(rxChangeObj[25]!=null){
                        rxChangeVO.setRecomObjId(RMDCommonUtility
                                  .convertObjectToString(rxChangeObj[25]));
                       
                      }
                    //rxChangeVO.setRxchangeAuditInfoLst(getRxChangeAuditTrailInfo(String.valueOf(rxChangeVO.getObjid())));
                    
                    rxChangeVOLst.add(rxChangeVO);  
                    
                }                 
            }            
        }catch (RMDDAOConnectionException ex) {
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {
           rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getRxChangeOverviewData()", e);
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), e, RMDCommonConstants.MAJOR_ERROR);
       } finally {
           releaseSession(objSession);
       }
        return rxChangeVOLst;
    }
 
    public boolean getUserCases(String userId, String caseId) throws RMDDAOException {        
        Session session = null;
        final StringBuilder strQuery = new StringBuilder();
       
        try {

            session = getHibernateSession();
           
            strQuery.append("SELECT COUNT(1) FROM GETS_SD_CUST_FDBK CF WHERE CF.RX_CASE_ID =:caseId");
            Query query = session.createSQLQuery(strQuery.toString());
 
            if (!RMDCommonUtility.isNullOrEmpty(caseId)){
                query.setParameter(RMDCommonConstants.CASE_ID, caseId);
            }
            BigDecimal count = (BigDecimal) query
                    .uniqueResult();

            if (count.doubleValue() == 1) {
                return RMDCommonConstants.TRUE;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getUserCases()", e);
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        return RMDCommonConstants.FALSE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> getRxTitles(String strTitle, String strObjIdLst) throws RMDDAOException {
        
        Session hibernateSession = null;
        List<Object[]> rxTitleList = new ArrayList<Object[]>();
        Map<String, String> rxTitleMap = new HashMap<String, String>();

        StringBuilder searchRecommQry = new StringBuilder();
        Query displayNameQuery = null;
        try {

            hibernateSession = getHibernateSession();
            // Query will get the distinct of RX Titles values from table
            if (null != strTitle && !RMDCommonConstants.EMPTY_STRING.equalsIgnoreCase(strTitle)) {
                
                if(!RMDCommonUtility.isNullOrEmpty(strObjIdLst)){
                    searchRecommQry.append("SELECT recom.oBJID, recom.TITLE "); 
                    searchRecommQry.append("FROM GETS_SD_RECOM_MTM_MODEL MTMMODEL, GETS_RMD_MODEL RMDMODEL, GETS_SD_RECOM RECOM "); 
                    searchRecommQry.append("WHERE RECOM.RECOM_STATUS = :solutionStatus AND MTMMODEL.RECOM_MODEL2MODEL = RMDMODEL.OBJID AND MTMMODEL.RECOM_MODEL2RECOM = recom.objid ");
                    
                    if(!RMDCommonUtility.isNullOrEmpty(strTitle)){
                        searchRecommQry.append(" AND LOWER(TITLE) LIKE LOWER( :title) ");
                    }
                        
                    if(!RMDCommonUtility.isNullOrEmpty(strObjIdLst)){
                    searchRecommQry.append("AND RMDMODEL.objid in (select objid from GETS_RMD_MODEL where MODEL_GENERAL in (select MODEL_GENERAL from GETS_RMD_MODEL  where objid in (:objId))) ");
                    }
                    searchRecommQry.append("ORDER BY RECOM.TITLE ASC"); 
                }                
                else{
                searchRecommQry.append("SELECT OBJID, TITLE FROM GETS_SD.GETS_SD_RECOM WHERE RECOM_STATUS = :solutionStatus ");
                if(!RMDCommonUtility.isNullOrEmpty(strTitle)){
                searchRecommQry.append("AND LOWER(TITLE) LIKE LOWER( :title) ");
                }
                if(!RMDCommonUtility.isNullOrEmpty(strObjIdLst)){
                    searchRecommQry.append("AND OBJID IN (:objId) ");
                }
                searchRecommQry.append("ORDER BY TITLE ASC");
                }
               
                System.out.println("searchRecommQry::"+searchRecommQry+" :: "+strObjIdLst);
                displayNameQuery = hibernateSession.createSQLQuery(searchRecommQry.toString());        
                
                displayNameQuery.setParameter(RMDCommonConstants.SOLUTION_STATUS, RMDCommonConstants.APPROVED);
                
                if(!RMDCommonUtility.isNullOrEmpty(strTitle)){
                    displayNameQuery.setParameter(RMDCommonConstants.TITLE, RMDServiceConstants.PERCENTAGE + strTitle
                            + RMDServiceConstants.PERCENTAGE);
                }
                if(!RMDCommonUtility.isNullOrEmpty(strObjIdLst)){
                    if(strObjIdLst.contains(",")){
                        String[] strArr = strObjIdLst.split(",");
                        List<String> arrStrLst = Arrays.asList(strArr);
                        List<Integer> intArrStrLst = new ArrayList<Integer>();
                        for(String str: arrStrLst){
                            intArrStrLst.add(Integer.valueOf(str));
                        }
                       System.out.println("intArrStrLst "+intArrStrLst); 
                    displayNameQuery.setParameterList(RMDCommonConstants.OBJ_ID, intArrStrLst);
                    }
                    else{
                        displayNameQuery.setParameter(RMDCommonConstants.OBJ_ID, strObjIdLst); 
                    }
                }   
                
                rxTitleList = (ArrayList<Object[]>) displayNameQuery.list();

                if (RMDCommonUtility.isCollectionNotEmpty(rxTitleList)) {

                    for (final Iterator<Object[]> obj = rxTitleList.iterator(); obj.hasNext();) {

                        final Object[] rxTitleObj = obj.next();

                        rxTitleMap.put(RMDCommonUtility.convertObjectToString(rxTitleObj[0]),
                                EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(rxTitleObj[1]))));
                    }
                }
            }
        } catch (RMDDAOConnectionException ex) {
            ex.printStackTrace();
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return rxTitleMap;
    }

    

    @Override
    public Map<String, String> getOmdUsers(String fName, String lName, String userId) throws RMDDAOException {
        Session hibernateSession = null;
        Map<String, String> userDetailsMap = new HashMap<String, String>();
        boolean fnameExist = RMDCommonConstants.FALSE;
        boolean lnameExist = RMDCommonConstants.FALSE;

        try {
            hibernateSession = getHibernateSession();
            StringBuilder queryString = new StringBuilder();
            queryString.append("SELECT USR.GET_USR_USERS_SEQ_ID,USR.USER_ID, ");
            queryString.append(" USR.FIRST_NAME,USR.EMAIL_ID,USR.LAST_NAME FROM GET_USR.GET_USR_USERS USR WHERE ");

            if (null != fName && !RMDCommonConstants.EMPTY_STRING.equals(fName)) {
                fnameExist= RMDCommonConstants.TRUE;
                queryString.append(" UPPER (USR.FIRST_NAME) LIKE UPPER('%"+fName+"%')");
            }
            if (null != lName && !RMDCommonConstants.EMPTY_STRING.equals(lName)) {
                lnameExist= RMDCommonConstants.TRUE;
                if (fnameExist) {
                queryString.append("AND UPPER (USR.LAST_NAME) LIKE UPPER('%"+lName+"%')");
                }else{
                    queryString.append(" UPPER (USR.LAST_NAME) LIKE UPPER('%"+lName+"%')");
                }
            }
            if (null != userId && !RMDCommonConstants.EMPTY_STRING.equals(userId)) {
                if (fnameExist || lnameExist){
                queryString.append("AND USR.USER_ID LIKE UPPER('%"+userId+"%')");
                }else{
                    queryString.append(" USR.USER_ID LIKE UPPER('%"+userId+"%')");
                }
            }
            queryString.append("ORDER BY USR.LAST_NAME DESC");

            if (null != hibernateSession) {
                Query query = hibernateSession.createSQLQuery(queryString.toString());

                query.setFetchSize(500);
                List<Object[]> arlUsers = query.list();
                
                if (RMDCommonUtility.isCollectionNotEmpty(arlUsers)) {
                    for (Object[] obj : arlUsers) {
                        userDetailsMap.put(obj[0].toString()+"_"+obj[1].toString(), obj[2].toString() + " " + obj[4].toString()+" ("+obj[1].toString()+")");
                    }
                }

                arlUsers = null;
            }
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_TITLES);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(hibernateSession);
        }
        return userDetailsMap;
    }
    
    public String saveRxChangeInfo(RxChangeVO rxChangeVO) throws RMDDAOException {

        Session session = null;
        StringBuilder queryString = new StringBuilder();
        Transaction trans = null;
        String result=RMDCommonConstants.SUCCESS;
        StringBuilder seqQuery = new StringBuilder();
        BigDecimal rxRequestIdSeq = null;
        boolean whitePaperRelatedUpdate = false;
        
        long start = System.currentTimeMillis();
        Timestamp timestamp  = new Timestamp(start) ;
        Date calculationDate = new java.util.Date(timestamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH24:mm:ss");
        
        rxChangeVO.setLastUpdatedDate(calculationDate);
        rxChangeVO.setCreationDate(calculationDate);

        try {
            session = getHibernateSession();
            
            
           
            if(null != rxChangeVO.getObjid() && !RMDCommonConstants.EMPTY_STRING.equals(rxChangeVO.getObjid())){
                queryString.append(" UPDATE GETS_RMD_RX_CHNG_REQ_DETAILS SET LAST_UPDATED_DATE=systimestamp ");
                
                if(rxChangeVO.getStatusObjId()!= 0){
                    queryString.append(", RX_REQUEST_STATUS=:rxRequestStatus");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRequestOwner())){
                    queryString.append(", REQUEST_OWNER=:rxRequestOwner ");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getStrUserName())){
                    queryString.append(", LAST_UPDATED_BY=:userName ");
                }
                           
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getNotes())){
                    queryString.append(", REQUESTOR_NOTES= :requestorNotes ");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getWhitePaperPdffileName())){
                    queryString.append(", WHITE_PAPER_PDF_FILE_NAME= :whitePaperPdffileName ");
                    whitePaperRelatedUpdate = true;
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getWhitePaperPdffilePath())){
                    queryString.append(", WHITE_PAPER_PDF_FILE_PATH= :whitePaperPdffilePath ");
                    whitePaperRelatedUpdate = true;
                }
                
                queryString.append(" WHERE RX_CHANGE_REQ_SEQ_ID =:objId");
                
                final Query rxUpdateQuery = session.createSQLQuery(queryString.toString());
                
                if(rxChangeVO.getStatusObjId()!= 0){
                    rxUpdateQuery.setParameter(RMDCommonConstants.RXREQUESTSTATUS, rxChangeVO.getStatusObjId());
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRequestOwner())){
                    rxUpdateQuery.setParameter(RMDCommonConstants.RX_REQUEST_OWNER, rxChangeVO.getRequestOwner());
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getStrUserName())){
                    rxUpdateQuery.setParameter(RMDCommonConstants.USERNAME, rxChangeVO.getStrUserName());
                }
                String notes = EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeVO.getNotes())));
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getNotes())){
                    rxUpdateQuery.setParameter(RMDCommonConstants.REQUESTOR_NOTES, notes);
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getWhitePaperPdffileName())){
                    rxUpdateQuery.setParameter(RMDCommonConstants.WHITE_PAPER_PDF_FILE_NAME, rxChangeVO.getWhitePaperPdffileName());
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getWhitePaperPdffilePath())){
                    rxUpdateQuery.setParameter(RMDCommonConstants.WHITE_PAPER_PDF_FILE_PATH, rxChangeVO.getWhitePaperPdffilePath());
                }
                rxUpdateQuery.setParameter(RMDCommonConstants.OBJ_ID, rxChangeVO.getObjid());
                
                int updateResult = rxUpdateQuery.executeUpdate();
            
                if(updateResult == 0)
                    result=RMDCommonConstants.FAILURE;
                else{
                    if(RMDCommonUtility.isNullOrEmpty(rxChangeVO.getWhitePaperPdffileName())){
                    rxChangeVO.setRequestId(String.valueOf(rxChangeVO.getObjid()));
                    if(RMDCommonUtility.isNullOrEmpty(rxChangeVO.getNotes())){
                        rxChangeVO.setNotes(rxChangeVO.getChangesSuggested());
                    }
                    rxChangeVO.setStatus(null);
                    saveAuditTrailInfo(rxChangeVO);
                    }
                }
            }
            else{
            	
            	if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getScreenName())
						&& !RMDCommonUtility.isNullOrEmpty(rxChangeVO
								.getRoadNumber())) {
            		String assetGroupName = rxChangeVO
							.getRoadNumber().split(RMDCommonConstants.UNDERSCORE)[0];
            		String roadNumber = rxChangeVO
						.getRoadNumber().split(RMDCommonConstants.UNDERSCORE)[1];
            		
					if (RMDCommonUtility.isCollectionNotEmpty(rxChangeVO.getListCustomer())) {
						Map<String, String> vehicleObjidMap = objHealthCheckDAOIntf
								.getCustrnhDetails(rxChangeVO.getListCustomer()
										.get(0), roadNumber, assetGroupName,
										RMDCommonConstants.ENG);
						if (vehicleObjidMap != null
								&& vehicleObjidMap.size() > 0) {
							for (Map.Entry<String, String> entry : vehicleObjidMap
									.entrySet()) {
								rxChangeVO.setRoadNumber(entry.getKey());
							}
						}
					}

				}
                 trans = session.beginTransaction();
                seqQuery.append("SELECT GETS_RMD_RX_CHNG_REQ_DET_SEQ.NEXTVAL from DUAL");
                
                final Query rxChangeSeqQuery = session
                        .createSQLQuery(seqQuery.toString());
                rxRequestIdSeq = (BigDecimal) rxChangeSeqQuery.uniqueResult();
                
            rxChangeVO.setFilePath(rxChangeVO.getFilePath());
            
            queryString.append(" INSERT INTO GETS_RMD_RX_CHNG_REQ_DETAILS ");
            queryString.append(
                    " (RX_CHANGE_REQ_SEQ_ID,REQUESTED_BY,REVISION_TYPE,ROAD_NUMBER,CASE_ID,RX_TITLE_SPECIFIC,RX_TITLE_GENERAL,CHANGES_SUGGESTED,RX_REQUEST_STATUS,REQUESTOR_NOTES, CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) ");
            queryString.append(
                    " VALUES(:objId,:requestedBy,:rivisionType,:roadNumber,:caseId,:rxTitleSpecific,:rxTitleGeneral,:changesSuggested,:rxRequestStatus,:requestorNotes, :userName, systimestamp,:userName,systimestamp) ");
           
            final Query rxChangeQuery = session.createSQLQuery(queryString.toString());
            rxChangeQuery.setParameter(RMDCommonConstants.OBJ_ID, rxRequestIdSeq);            
            rxChangeQuery.setParameter(RMDCommonConstants.REQUESTEDBY, rxChangeVO.getRequestor());
            rxChangeQuery.setParameter(RMDCommonConstants.RIVISIONTYPE, rxChangeVO.getRevisionType());
            rxChangeQuery.setParameter(RMDCommonConstants.ROADNUMBER, rxChangeVO.getRoadNumber());
            rxChangeQuery.setParameter(RMDCommonConstants.CASE_ID, rxChangeVO.getCaseId());
            
            rxChangeQuery.setParameter(RMDCommonConstants.RXTITLESPECIFIC, rxChangeVO.getSpecificRxTitle());
            rxChangeQuery.setParameter(RMDCommonConstants.RXTITLEGENERAL, rxChangeVO.getGeneralRxTitle());
      
            rxChangeQuery.setParameter(RMDCommonConstants.CHANGESSUGGESTED, EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeVO.getChangesSuggested()))));
            rxChangeQuery.setParameter(RMDCommonConstants.RXREQUESTSTATUS, rxChangeVO.getStatusObjId());
            rxChangeQuery.setParameter(RMDCommonConstants.REQUESTORNOTES, EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeVO.getNotes()))));
            //rxChangeQuery.setParameter(RMDCommonConstants.FILEDATA, rxChangeVO.getFileData());
          //  rxChangeQuery.setParameter(RMDCommonConstants.RX_CHANGE_FILENAME, rxChangeVO.getFileName());
           // rxChangeQuery.setParameter(RMDCommonConstants.FILEPATH, rxChangeVO.getFilePath());
            rxChangeQuery.setParameter(RMDCommonConstants.USERNAME, rxChangeVO.getStrUserName());
            
                       
            int updateResult = rxChangeQuery.executeUpdate();
           
            if(updateResult == 0)
                result=RMDCommonConstants.FAILURE;          
        	/**
    		 * Added for Rx update  US297469: Customers: Rx Update Workflow - Link to Add Rx change request from Technician cases view tab Start
    		 */
				if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getScreenName())
						&& RMDCommonUtility.isCollectionNotEmpty(rxChangeVO
								.getListCustomer())) {
            	queryString = new StringBuilder();
				queryString
						.append(" Insert into GETS_RMD_RX_CHNG_CUSTOMERS ");
				queryString
						.append(" (RX_CHANGE_REQ_CUSTOMERS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_CUSTOMER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
				queryString
						.append(" (GETS_RMD.GETS_RMD_RX_CHNG_CUSTOMERS_SQ.NEXTVAL,:objId,(select objid from table_bus_org where org_id=:customerId), :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

				final Query rxChangeCustQuery = session
						.createSQLQuery(queryString.toString());

						rxChangeCustQuery.setParameter(
								RMDCommonConstants.OBJ_ID, rxRequestIdSeq);
						rxChangeCustQuery.setParameter(
								RMDCommonConstants.CUSTOMER_ID, rxChangeVO
								.getListCustomer().get(0));
						rxChangeCustQuery.setParameter(
								RMDCommonConstants.DATASOURCE,
								RMDCommonConstants.RX_CHANGE_DATA_SOURCE);
						rxChangeCustQuery.setParameter(
								RMDCommonConstants.USERNAME,
								rxChangeVO.getStrUserName());
						updateResult = rxChangeCustQuery.executeUpdate();

						if (updateResult == 0)
							result = RMDCommonConstants.FAILURE;
					
            	
				}
				/**
				 * Added for Rx update  US297469: Customers: Rx Update Workflow - Link to Add Rx change request from Technician cases view tab End
				 */
				else {

					queryString = new StringBuilder();
					queryString
							.append(" Insert into GETS_RMD_RX_CHNG_CUSTOMERS ");
					queryString
							.append(" (RX_CHANGE_REQ_CUSTOMERS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_CUSTOMER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
					queryString
							.append(" (GETS_RMD.GETS_RMD_RX_CHNG_CUSTOMERS_SQ.NEXTVAL,:objId,:custObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

					final Query rxChangeCustQuery = session
							.createSQLQuery(queryString.toString());

					if (null != rxChangeVO.getListCustomer()
							&& !rxChangeVO.getListCustomer().isEmpty()) {
						for (String custId : rxChangeVO.getListCustomer()) {
							rxChangeCustQuery.setParameter(
									RMDCommonConstants.OBJ_ID, rxRequestIdSeq);
							rxChangeCustQuery.setParameter(
									RMDCommonConstants.CUSTOMER_OBJ_ID, custId);
							rxChangeCustQuery.setParameter(
									RMDCommonConstants.DATASOURCE,
									RMDCommonConstants.RX_CHANGE_DATA_SOURCE);
							rxChangeCustQuery.setParameter(
									RMDCommonConstants.USERNAME,
									rxChangeVO.getStrUserName());
							updateResult = rxChangeCustQuery.executeUpdate();

							if (updateResult == 0)
								result = RMDCommonConstants.FAILURE;
						}
					}
				}
				/**
				 * Added for Rx update  US297469: Customers: Rx Update Workflow - Link to Add Rx change request from Technician cases view tab Start
				 */
			if (!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getScreenName())
						&& RMDCommonUtility.isCollectionNotEmpty(rxChangeVO
								.getListModel())) {
			for(String modelName : rxChangeVO.getListModel()){					
            	queryString = new StringBuilder();
            	queryString.append("Insert into GETS_RMD_RX_CHNG_MODELS ");
	            queryString.append(
	                    " (RX_CHANGE_REQ_MODELS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_MODEL,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
	            queryString.append(
	                    " (GETS_RMD.GETS_RMD_RX_CHNG_MODELS_SEQ.NEXTVAL,:objId, (select objid from gets_rmd_model where model_name=:modelName), :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");
	            
	            Query rxChangeModelQuery = session.createSQLQuery(queryString.toString());
                  rxChangeModelQuery.setParameter(RMDCommonConstants.OBJ_ID, rxRequestIdSeq);
	                   rxChangeModelQuery.setParameter(RMDCommonConstants.MODEL_NAME, modelName);
	                   rxChangeModelQuery.setParameter(RMDCommonConstants.USERNAME, rxChangeVO.getStrUserName());
	                   rxChangeModelQuery.setParameter(RMDCommonConstants.DATASOURCE,
	                           RMDCommonConstants.RX_CHANGE_DATA_SOURCE); 
	                   updateResult = rxChangeModelQuery.executeUpdate();

						if (updateResult == 0)
							result = RMDCommonConstants.FAILURE;					
	              
	            }
			}
				/**
				 * Added for Rx update  US297469: Customers: Rx Update Workflow - Link to Add Rx change request from Technician cases view tab End
				 */	
			else{
					queryString = new StringBuilder();
			           
		            queryString.append("Insert into GETS_RMD_RX_CHNG_MODELS ");
		            queryString.append(
		                    " (RX_CHANGE_REQ_MODELS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_MODEL,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
		            queryString.append(
		                    " (GETS_RMD.GETS_RMD_RX_CHNG_MODELS_SEQ.NEXTVAL,:objId,:modelObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");
		            
		            Query rxChangeModelQuery = session.createSQLQuery(queryString.toString());
		            
		            if (null != rxChangeVO.getListModel() && !rxChangeVO.getListModel().isEmpty()) {
		               for(String modelObjId : rxChangeVO.getListModel()){
		                   rxChangeModelQuery.setParameter(RMDCommonConstants.OBJ_ID, rxRequestIdSeq);
		                   rxChangeModelQuery.setParameter(RMDCommonConstants.MODEL_OBJID, modelObjId);
		                   rxChangeModelQuery.setParameter(RMDCommonConstants.USERNAME, rxChangeVO.getStrUserName());
		                   rxChangeModelQuery.setParameter(RMDCommonConstants.DATASOURCE,
		                           RMDCommonConstants.RX_CHANGE_DATA_SOURCE); 
		                   updateResult = rxChangeModelQuery.executeUpdate();
		                   if(updateResult == 0)
		                       result=RMDCommonConstants.FAILURE;
		               }
		            }   
				}
               
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeVO.getTypeOfRxChangeLst())){  
            for (String typeofChangeId : rxChangeVO.getTypeOfRxChangeLst()) {
                 StringBuilder rxChangeTypeQuery = new StringBuilder();
                rxChangeTypeQuery
                       .append("INSERT INTO GETS_RMD_RX_CHNG_TYPE ");
                rxChangeTypeQuery
                       .append(" (RX_CHANGE_REQ_TYPE_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_TYPEOFCHANGE,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                rxChangeTypeQuery
                       .append(" (GETS_RMD_RX_CHNG_TYPE_SQ.NEXTVAL,:objId,:typeOfRxChange, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP,:dataSource) ");
            
               final Query rxChangeTypeHQuery = session
                       .createSQLQuery(rxChangeTypeQuery
                               .toString());
               rxChangeTypeHQuery.setParameter(
                       RMDCommonConstants.OBJ_ID,
                       rxRequestIdSeq);
               rxChangeTypeHQuery.setParameter(RMDCommonConstants.DATASOURCE,
                       RMDCommonConstants.RX_CHANGE_DATA_SOURCE);           
               rxChangeTypeHQuery.setParameter(
                       RMDCommonConstants.TYPE_OF_RX_CHANGE, typeofChangeId);
               rxChangeTypeHQuery.setParameter(
                       RMDCommonConstants.USERNAME,
                       rxChangeVO.getStrUserName());
               rxChangeTypeHQuery.executeUpdate();    
            }
        }
          
            /**
			 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page Start
			 */
            
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeVO.getLstAttachment())) {
               	for (Iterator iterator = rxChangeVO.getLstAttachment().iterator(); iterator
						.hasNext();) {
               		RecommDelvDocVO recommDelvDocVO = (RecommDelvDocVO) iterator.next();
               		String filePath = uploadRxAttachment(recommDelvDocVO.getDocData(), recommDelvDocVO.getDocTitle(),rxRequestIdSeq);					
               		recommDelvDocVO.setDocPath(filePath);
               		
               	 StringBuilder rxChangeTypeQuery = new StringBuilder();
                 rxChangeTypeQuery
                        .append("INSERT INTO GETS_RMD_RX_CHNG_ATTACHMENTS ");
                 rxChangeTypeQuery
                        .append(" (RX_CHANGE_REQ_ATT_SEQ_ID ,RX_CHANGE_REQ_OBJID,FILE_PATH,FILE_NAME,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES  ");
                 rxChangeTypeQuery
                        .append(" ( GETS_RMD_RX_CHNG_REQ_ATT_SEQ.NEXTVAL,:objId,:filePath,:fileName, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");
             
                final Query rxChangeTypeHQuery = session
                        .createSQLQuery(rxChangeTypeQuery
                                .toString());
                rxChangeTypeHQuery.setParameter(
                        RMDCommonConstants.OBJ_ID,
                        rxRequestIdSeq);
                    
                rxChangeTypeHQuery.setParameter(
                        RMDCommonConstants.RX_CHANGE_FILENAME, recommDelvDocVO.getDocTitle());
                rxChangeTypeHQuery.setParameter(
                        RMDCommonConstants.FILEPATH, recommDelvDocVO.getDocPath());
                rxChangeTypeHQuery.setParameter(
                        RMDCommonConstants.USERNAME,
                        rxChangeVO.getStrUserName());
                rxChangeTypeHQuery.executeUpdate();    
				}
             }
            /**
			 * US293725	Customers : Rx Update Workflow - Save multiple attachments in Add Rx Change Request Page End
			 */
            trans.commit();
            if(result.equals(RMDCommonConstants.SUCCESS)){
                rxChangeVO.setRequestId(String.valueOf(rxRequestIdSeq));
                rxChangeVO.setStatus(null);
                saveAuditTrailInfo(rxChangeVO);
                result = result+"_"+String.valueOf(rxRequestIdSeq.longValue());     
     
            }
          }          
           
        } catch (ConstraintViolationException ex) {
            if (trans != null) {
                trans.rollback();
            }
            result=RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.USER_MANAGEMENT_CONCURENCY_ERROR_CODE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    rxChangeVO.getStrLanguage()), ex, RMDCommonConstants.MINOR_ERROR);
        } catch (RMDDAOConnectionException ex) {
            
            if (trans != null) {
                trans.rollback();
            }
            result=RMDCommonConstants.FAILURE;
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE_UPDATE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    rxChangeVO.getStrLanguage()), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) { 
           
            String errorCode = RMDCommonConstants.EMPTY_STRING;
            if (trans != null) {
                trans.rollback();
            }
            result=RMDCommonConstants.FAILURE;
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    rxChangeVO.getStrLanguage()), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
        if (result.contains(RMDCommonConstants.SUCCESS)) {
            RxChangeSearchVO rxChangeSearchVO = new RxChangeSearchVO();
            if (null != rxChangeVO.getObjid()
                    && !RMDCommonConstants.EMPTY_STRING.equals(rxChangeVO
                            .getObjid())) {
                rxChangeSearchVO.setRxChangeReqObjId(String.valueOf(rxChangeVO
                        .getObjid()));
            } else
                rxChangeSearchVO.setRxChangeReqObjId(String
                        .valueOf(rxRequestIdSeq));
            List<RxChangeVO> rxChangeOverviewVOLst = getRxChangeOverviewData(rxChangeSearchVO);
           
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeOverviewVOLst)) {
            	 rxChangeOverviewVOLst.get(0).setTriggerLogicNote(rxChangeVO.getTriggerLogicNote());
                if(!whitePaperRelatedUpdate){
                sendRxChangeEmailNotification(rxChangeOverviewVOLst.get(0),rxChangeVO.getAdminEmailIdLst(), rxChangeVO.getPdfEmailIdList());
                }                
            }
        }
        return result;
    }

   
    @Override
    public List<ModelVO> getModelForRxTitle(RxChangeSearchVO rxChangeSearchVO)
            throws RMDDAOException {
            StringBuilder modelQuery = new StringBuilder();
            Session session = null;
            Query hibernateQuery = null;
            List<ModelVO> modelLst = new ArrayList<ModelVO>();;
            ModelVO modelVO =null;
            try {
                session = getHibernateSession();
                modelQuery
                        .append("SELECT DISTINCT RMDMODEL.OBJID,RMDMODEL.MODEL_NAME,RMDMODEL.FAMILY,RMDMODEL.MODEL_GENERAL ");
                modelQuery
                        .append("FROM GETS_SD_RECOM_MTM_MODEL MTMMODEL,GETS_RMD_MODEL RMDMODEL WHERE MTMMODEL.RECOM_MODEL2MODEL = RMDMODEL.OBJID ");
                modelQuery
                .append("AND RECOM_MODEL2RECOM IN (:rxObjid)");
                modelQuery
                .append(" ORDER BY RMDMODEL.MODEL_NAME ASC");
                    hibernateQuery = session.createSQLQuery(modelQuery
                            .toString());
                    if(RMDCommonUtility.isCollectionNotEmpty(rxChangeSearchVO.getRecomObjIdLst())){
                         hibernateQuery.setParameterList(RMDCommonConstants.RX_OBJID,
                                rxChangeSearchVO.getRecomObjIdLst());
                    }
                    List<Object[]> arlResults = hibernateQuery.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                        for (Object[] rxChangeObj : arlResults) {
                            modelVO = new ModelVO();
                            modelVO.setModelObjId(RMDCommonUtility
                                    .convertObjectToString(rxChangeObj[0]));
                            modelVO.setModelName(RMDCommonUtility
                                        .convertObjectToString(rxChangeObj[1]));
                            modelVO.setModelFamily(RMDCommonUtility
                                    .convertObjectToString(rxChangeObj[2]));
                            modelVO.setModelGeneral(RMDCommonUtility
                                    .convertObjectToString(rxChangeObj[3]));
                            modelLst.add(modelVO);
                        }
                    }
            } catch (Exception e) {
                rxChangeDaoLog.error(
                        "Unexpected Error occured in RxChangeDAOImpl getModelForRxTitle()",
                        e);
                String errorCode = RMDCommonUtility
                        .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MODEL_MATCH);
                throw new RMDDAOException(errorCode, new String[] {},
                        RMDCommonUtility.getMessage(errorCode, new String[] {},
                                RMDCommonConstants.ENGLISH_LANGUAGE), e,
                        RMDCommonConstants.MAJOR_ERROR);
            } finally {
                releaseSession(session);
            }
            return modelLst;        
    }

    @Override
    public String saveUpdateRxChangeAdminDetails(RxChangeAdminVO rxChangeAdminVO)
            throws RMDDAOException {
        
        String result = RMDCommonConstants.FAILURE; 
        Session objSession = null;
        StringBuilder rxChangeAdminQuery = null;
        StringBuilder rxChangeModelQuery = null;
        StringBuilder rxChangeCustomerQuery = null;
        StringBuilder rxChangeRxTitleQuery = null;
        StringBuilder rxChangeSequenceQuery = null;
        BigDecimal rxChangeProcObjId = null;
        Transaction transaction = null;
        Query rxChangeAdminHQuery=null;
        
        long start = System.currentTimeMillis();
        Timestamp timestamp  = new Timestamp(start) ;
        Date calculationDate = new java.util.Date(timestamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH24:mm:ss");
        
        try {
            objSession = getHibernateSession();
            rxChangeAdminQuery = new StringBuilder();
            
            if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getObjId())){
            //check if record already exists 
            rxChangeAdminQuery.append("SELECT COUNT(1) FROM GETS_RMD_RX_CHNG_PROC_DETAILS WHERE RX_CHANGE_PROCESSING_SEQ_ID= :strObjId");
            Query rxAdminHQuery = objSession.createSQLQuery(rxChangeAdminQuery
                    .toString());
            rxAdminHQuery.setParameter(
                    RMDServiceConstants.OBJID,
                    rxChangeAdminVO.getObjId());
            BigDecimal count = (BigDecimal) rxAdminHQuery
                    .uniqueResult();
            
            if (count.intValue() != 0) {           
                transaction = objSession.getTransaction();
                transaction.begin();
                rxChangeAdminQuery = new StringBuilder();
                rxChangeAdminQuery.append("UPDATE GETS_RMD_RX_CHNG_PROC_DETAILS SET NEW_RX_REQUIRED=:newRxRequired,MATERIAL_CHANGE_REQUIRED=:materialChangeRequired,TRIGGER_LOGIC_CHANGED=:triggerLogicChanged,UNFAMILIAR_SYSTEM_CHANGE=:unFamiliarSysChange,");
                rxChangeAdminQuery.append("CHANGES_SUMMARY=:summaryChanges,REVIEWED_AND_APPROVED_BY =:reviewedApproveFlag,NOTES_INTERNAL=:internalNotes,");
                rxChangeAdminQuery.append(" REVIEWER_NOTES=:reviewerNotes,LAST_UPDATED_BY=:userID, LAST_UPDATED_DATE=systimestamp,VERSION_DRAFT=:Draft");
               if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getTargetImplementationDate())){
                      rxChangeAdminQuery.append(" ,TARGET_IMPLEMENTATION_DATE=to_date(:targetImplDate,'MM/DD/YYYY HH24:MI:SS')");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getNoOfRxAttachment())){
                     rxChangeAdminQuery.append(",NUMBER_OF_RXs_ATTACHMENT_ADDED=:noOfRxAttachment");
                }                
                
                rxChangeAdminQuery.append(" WHERE RX_CHANGE_PROCESSING_SEQ_ID = :rxChangeProcObjId and RX_CHANGE_REQ_OBJID = :rxChangeReqObjId");
                
                rxChangeAdminHQuery = objSession.createSQLQuery(rxChangeAdminQuery
                        .toString());   
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_PROC_OBJ_ID,
                        rxChangeAdminVO.getObjId());        
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_REQ_OBJ_ID,
                        rxChangeAdminVO.getRxChangeRequestId());                
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_NEW_RX_REQUIRED,
                        rxChangeAdminVO.getNewRxCreated());     
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_MATERIAL_CHANGE_REQUIRED,
                        rxChangeAdminVO.getAnyChangeinMaterial());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_TRIGGER_LOGIC_CHANGED,
                        rxChangeAdminVO.getTriggerLogicChange());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_UNFAMILIAR_SYSTEM_CHANGE,
                        rxChangeAdminVO.getUnfamiliarSystemChange());
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getNoOfRxAttachment())){
                     rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_NUMBER_OF_RXS_ATTACHMENT_ADDED,
                             rxChangeAdminVO.getNoOfRxAttachment());
                }               
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_SUMMARY,
                        EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeAdminVO.getSummaryOfChanges()))));
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_REVIEWED_AND_APPROVED_BY,
                        rxChangeAdminVO.getAcceptanceFlag());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_INTERNAL_NOTES,
                        EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeAdminVO.getInternalNotes()))));          
                
                String reviewerNotes = EsapiUtil
                            .stripXSSCharacters(EsapiUtil
                                    .escapeSpecialChars(AppSecUtil
                                            .decodeString(rxChangeAdminVO
                                                    .getReviewerNotes())));
                    if (!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO
                            .getReviewerNotes())) {
                        rxChangeAdminHQuery.setParameter(
                                RMDCommonConstants.RX_CHANGE_REVIEWER_NOTES,
                                reviewerNotes);
                    } else
                        rxChangeAdminHQuery.setParameter(
                                RMDCommonConstants.RX_CHANGE_REVIEWER_NOTES,
                                rxChangeAdminVO.getReviewerNotes());
              if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getTargetImplementationDate())){
                    rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_TARGET_IMP_DATE,
                            rxChangeAdminVO.getTargetImplementationDate());        
                }                 
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.USER_ID,
                        rxChangeAdminVO.getUserId());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.DRAFT,
                        rxChangeAdminVO.getSaveAsDraft());
                rxChangeAdminHQuery.executeUpdate();
                
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                            .getAdditionalReviewerLst())) {
                        StringBuilder rxChangeDeleteQuery = new StringBuilder();
                        rxChangeDeleteQuery
                                .append("DELETE FROM GETS_RMD_RX_CHNG_REVIEWERS WHERE RX_CHANGE_PRCSNG_RVWRS_OBJID = :objId");
                        Query hibernateRxChangeDelQuery = objSession
                                .createSQLQuery(rxChangeDeleteQuery.toString());
                        hibernateRxChangeDelQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getObjId());
                        hibernateRxChangeDelQuery.executeUpdate();
                        
                        for (String reviewerId : rxChangeAdminVO
                                .getAdditionalReviewerLst()) {
                            StringBuilder rxChangeAdditionalQuery = new StringBuilder();
                            rxChangeAdditionalQuery
                                    .append("INSERT INTO GETS_RMD_RX_CHNG_REVIEWERS ");
                            rxChangeAdditionalQuery
                                    .append(" (RX_CHANGE_PRCSNG_RVWRS_SEQ_ID,RX_CHANGE_PRCSNG_RVWRS_OBJID,LINK_REVIEWER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES  ");
                            rxChangeAdditionalQuery
                                    .append(" (GETS_RMD_RX_CHNG_REVIEWERS_SEQ.NEXTVAL,:objId,:additionalReviewer, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

                            final Query rxChangeAdditionalHQuery = objSession
                                    .createSQLQuery(rxChangeAdditionalQuery
                                            .toString());
                            rxChangeAdditionalHQuery.setParameter(
                                    RMDCommonConstants.OBJ_ID,
                                    rxChangeAdminVO.getObjId());
                            rxChangeAdditionalHQuery.setParameter(
                                    RMDCommonConstants.ADDITIONAL_REVIEWER,
                                    reviewerId);
                            rxChangeAdditionalHQuery.setParameter(
                                    RMDCommonConstants.USERNAME,
                                    rxChangeAdminVO.getUserId());
                            rxChangeAdditionalHQuery.executeUpdate();
                        }
                    } else{
                        StringBuilder rxChangeDeleteQuery = new StringBuilder();
                        rxChangeDeleteQuery
                                .append("DELETE FROM GETS_RMD_RX_CHNG_REVIEWERS WHERE RX_CHANGE_PRCSNG_RVWRS_OBJID = :objId");
                        Query hibernateRxChangeDelQuery = objSession
                                .createSQLQuery(rxChangeDeleteQuery
                                        .toString());
                        hibernateRxChangeDelQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getObjId());
                        hibernateRxChangeDelQuery.executeUpdate();
                    }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                            .getRxChangeReasonsLst())) {
                      StringBuilder rxChangeDeleteQuery = new StringBuilder();
                      rxChangeDeleteQuery.append("DELETE FROM GETS_RMD_RX_CHNG_TYPE WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                      Query hibernateRxChangeDelQuery = objSession.createSQLQuery(rxChangeDeleteQuery
                              .toString());   
                      
                    hibernateRxChangeDelQuery.setParameter(
                            RMDCommonConstants.OBJ_ID,
                            rxChangeAdminVO.getRxChangeRequestId());
                    hibernateRxChangeDelQuery.setParameter(RMDCommonConstants.DATASOURCE,
                               RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);      
                    hibernateRxChangeDelQuery.executeUpdate();                                     
                    for (String typeofChangeId : rxChangeAdminVO
                            .getRxChangeReasonsLst()) {
                        StringBuilder rxChangeTypeQuery = new StringBuilder();
                        rxChangeTypeQuery
                               .append("INSERT INTO GETS_RMD_RX_CHNG_TYPE ");
                        rxChangeTypeQuery
                               .append(" (RX_CHANGE_REQ_TYPE_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_TYPEOFCHANGE,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                        rxChangeTypeQuery
                               .append(" (GETS_RMD_RX_CHNG_TYPE_SQ.NEXTVAL,:objId,:typeOfRxChange, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP,:dataSource) ");
                    
                       final Query rxChangeTypeHQuery = objSession
                               .createSQLQuery(rxChangeTypeQuery
                                       .toString());
                       rxChangeTypeHQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getRxChangeRequestId());
                       rxChangeTypeHQuery.setParameter(RMDCommonConstants.DATASOURCE,
                               RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);          
                       rxChangeTypeHQuery.setParameter(
                               RMDCommonConstants.TYPE_OF_RX_CHANGE, typeofChangeId);
                       rxChangeTypeHQuery.setParameter(
                               RMDCommonConstants.USERNAME,
                               rxChangeAdminVO.getUserId());
                       rxChangeTypeHQuery.executeUpdate();    
                    }
                }else{
                      StringBuilder rxChangeDeleteQuery = new StringBuilder();
                      rxChangeDeleteQuery.append("DELETE FROM GETS_RMD_RX_CHNG_TYPE WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                      Query hibernateRxChangeDelQuery = objSession.createSQLQuery(rxChangeDeleteQuery
                              .toString());      
                      hibernateRxChangeDelQuery.setParameter(
                            RMDCommonConstants.OBJ_ID,
                            rxChangeAdminVO.getObjId());
                      hibernateRxChangeDelQuery.setParameter(RMDCommonConstants.DATASOURCE,
                           RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);      
                    hibernateRxChangeDelQuery.executeUpdate();                   
                }
                  
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getCustomers())) {
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                    rxChangeDeleteQuery
                            .append("DELETE FROM GETS_RMD_RX_CHNG_CUSTOMERS WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                    Query hibernateRxChangeDelQuery = objSession
                            .createSQLQuery(rxChangeDeleteQuery
                                    .toString());
                    hibernateRxChangeDelQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getRxChangeRequestId());
                    hibernateRxChangeDelQuery.setParameter(
                            RMDCommonConstants.DATASOURCE,
                            RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);             
                    hibernateRxChangeDelQuery.executeUpdate();
                    
                    for (String customerObjId : rxChangeAdminVO.getCustomers()) {
                        rxChangeCustomerQuery = new StringBuilder();
                        rxChangeCustomerQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_CUSTOMERS ");
                        rxChangeCustomerQuery
                                .append(" (RX_CHANGE_REQ_CUSTOMERS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_CUSTOMER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                        rxChangeCustomerQuery
                                .append(" (GETS_RMD_RX_CHNG_CUSTOMERS_SQ.NEXTVAL,:objId,:custObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

                        final Query rxChangeCustQuery = objSession
                                .createSQLQuery(rxChangeCustomerQuery
                                        .toString());
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getRxChangeRequestId());
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.CUSTOBJID, customerObjId);
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.DATASOURCE,
                                RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeCustQuery.executeUpdate();
                        
                    }
                }else{
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                    rxChangeDeleteQuery
                            .append("DELETE FROM GETS_RMD_RX_CHNG_CUSTOMERS WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                    Query hibernateRxChangeDelQuery = objSession
                            .createSQLQuery(rxChangeDeleteQuery
                                    .toString());
                    hibernateRxChangeDelQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getRxChangeRequestId());
                    hibernateRxChangeDelQuery.setParameter(
                            RMDCommonConstants.DATASOURCE,
                            RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);             
                    hibernateRxChangeDelQuery.executeUpdate();
                }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getModelsImpacted())) {
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                    rxChangeDeleteQuery
                            .append("DELETE FROM GETS_RMD_RX_CHNG_MODELS WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                    Query hibernateRxChangeDelQuery = objSession
                            .createSQLQuery(rxChangeDeleteQuery
                                    .toString());
                    hibernateRxChangeDelQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getRxChangeRequestId());
                    hibernateRxChangeDelQuery.setParameter(
                            RMDCommonConstants.DATASOURCE,
                            RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);             
                    hibernateRxChangeDelQuery.executeUpdate();
                    for (String modelObjId : rxChangeAdminVO
                            .getModelsImpacted()) {
                        rxChangeModelQuery = new StringBuilder();
                        rxChangeModelQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_MODELS ");
                        rxChangeModelQuery
                                .append(" (RX_CHANGE_REQ_MODELS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_MODEL,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                        rxChangeModelQuery
                                .append(" (GETS_RMD_RX_CHNG_MODELS_SEQ.NEXTVAL,:objId,:modelObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

                        Query rxChangeModelHQuery = objSession
                                .createSQLQuery(rxChangeModelQuery.toString());

                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getRxChangeRequestId());
                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.MODEL_OBJID,modelObjId);
                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeModelHQuery
                                .setParameter(
                                        RMDCommonConstants.DATASOURCE,
                                        RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);
                        rxChangeModelHQuery.executeUpdate();

                    }
                }else{
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                                rxChangeDeleteQuery
                                        .append("DELETE FROM GETS_RMD_RX_CHNG_MODELS WHERE RX_CHANGE_REQ_OBJID = :objId AND DATA_SOURCE=:dataSource");
                                Query hibernateRxChangeDelQuery = objSession
                                        .createSQLQuery(rxChangeDeleteQuery
                                                .toString());
                                hibernateRxChangeDelQuery.setParameter(
                                           RMDCommonConstants.OBJ_ID,
                                           rxChangeAdminVO.getRxChangeRequestId());
                                hibernateRxChangeDelQuery.setParameter(
                                        RMDCommonConstants.DATASOURCE,
                                        RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);             
                                hibernateRxChangeDelQuery.executeUpdate();
                }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getRxsImpacted())) {
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                    rxChangeDeleteQuery
                            .append("DELETE FROM GETS_RMD_RX_CHNG_RX_TITLE WHERE RX_CHANGE_PROCESSING_DET_OBJID = :objId");
                    Query hibernateRxChangeDelQuery = objSession
                            .createSQLQuery(rxChangeDeleteQuery
                                    .toString());
                    hibernateRxChangeDelQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getObjId());                 
                    hibernateRxChangeDelQuery.executeUpdate();
                    for (String rxObjId : rxChangeAdminVO.getRxsImpacted()) {
                        rxChangeRxTitleQuery = new StringBuilder();
                        rxChangeRxTitleQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_RX_TITLE ");
                        rxChangeRxTitleQuery
                                .append(" (RX_CHANGE_PRCSNG_RXTITLE_SQ_ID,RX_CHANGE_PROCESSING_DET_OBJID,LINK_RX_TITLE,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES  ");
                        rxChangeRxTitleQuery
                                .append(" (GETS_RMD_RX_CHNG_RX_TITLE_SEQ.NEXTVAL,:objId,:rxObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

                        Query rxChangeRxTitleHQuery = objSession
                                .createSQLQuery(rxChangeRxTitleQuery.toString());
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.OBJ_ID, rxChangeAdminVO.getObjId());
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.RX_OBJ_ID, rxObjId);
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeRxTitleHQuery.executeUpdate();

                    }
                }else{
                    StringBuilder rxChangeDeleteQuery = new StringBuilder();
                    rxChangeDeleteQuery
                            .append("DELETE FROM GETS_RMD_RX_CHNG_RX_TITLE WHERE RX_CHANGE_PROCESSING_DET_OBJID = :objId");
                    Query hibernateRxChangeDelQuery = objSession
                            .createSQLQuery(rxChangeDeleteQuery
                                    .toString());
                    hibernateRxChangeDelQuery.setParameter(
                               RMDCommonConstants.OBJ_ID,
                               rxChangeAdminVO.getObjId());                     
                    hibernateRxChangeDelQuery.executeUpdate();
                }

                transaction.commit();
              
                if (transaction.wasCommitted()) {
                    result = RMDCommonConstants.SUCCESS; 
                }
            } 
            } else {
                transaction = objSession.getTransaction();
                transaction.begin();
                rxChangeSequenceQuery = new StringBuilder();
                rxChangeAdminQuery = new StringBuilder();
                rxChangeSequenceQuery.append("SELECT GETS_RMD_RX_CHNG_PROC_DET_SEQ.NEXTVAL FROM DUAL");
                Query hibernateRxChangeSeqQuery = objSession.createSQLQuery(rxChangeSequenceQuery
                        .toString());               
                rxChangeProcObjId = (BigDecimal) hibernateRxChangeSeqQuery
                        .uniqueResult();
                
                rxChangeAdminQuery.append("INSERT INTO GETS_RMD_RX_CHNG_PROC_DETAILS ");
                rxChangeAdminQuery.append("(RX_CHANGE_PROCESSING_SEQ_ID, RX_CHANGE_REQ_OBJID, NEW_RX_REQUIRED,MATERIAL_CHANGE_REQUIRED,TRIGGER_LOGIC_CHANGED,UNFAMILIAR_SYSTEM_CHANGE,");
                rxChangeAdminQuery.append("CHANGES_SUMMARY,REVIEWED_AND_APPROVED_BY ,NOTES_INTERNAL,");
                rxChangeAdminQuery.append("REVIEWER_NOTES,CREATED_BY,CREATED_DATE,VERSION_DRAFT");
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getTargetImplementationDate())){
                     rxChangeAdminQuery.append(",TARGET_IMPLEMENTATION_DATE");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getNoOfRxAttachment())){
                     rxChangeAdminQuery.append(",NUMBER_OF_RXs_ATTACHMENT_ADDED");
                }               
                rxChangeAdminQuery.append(")");
                rxChangeAdminQuery.append(" VALUES(:strObjId,:rxChangeProcObjId,:newRxRequired,:materialChangeRequired,:triggerLogicChanged,:unFamiliarSysChange,:summaryChanges, :reviewedApproveFlag,:internalNotes,:reviewerNotes,:userID,systimestamp,:Draft");
                
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getTargetImplementationDate())){
                    rxChangeAdminQuery.append(",to_date(:targetImplDate,'MM/DD/YYYY HH24:MI:SS')");
                }
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getNoOfRxAttachment())){
                    rxChangeAdminQuery.append(",:noOfRxAttachment");
                }
                rxChangeAdminQuery.append(")");
                    
                rxChangeAdminHQuery = objSession.createSQLQuery(rxChangeAdminQuery
                        .toString());       
                rxChangeAdminHQuery.setParameter(RMDServiceConstants.OBJID,
                        rxChangeProcObjId); 
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_PROC_OBJ_ID,
                        rxChangeAdminVO.getRxChangeRequestId());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_NEW_RX_REQUIRED,
                        rxChangeAdminVO.getNewRxCreated()); 
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_MATERIAL_CHANGE_REQUIRED,
                        rxChangeAdminVO.getAnyChangeinMaterial());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_TRIGGER_LOGIC_CHANGED,
                        rxChangeAdminVO.getTriggerLogicChange());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_UNFAMILIAR_SYSTEM_CHANGE,
                        rxChangeAdminVO.getUnfamiliarSystemChange());
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getNoOfRxAttachment())){
                      rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_NUMBER_OF_RXS_ATTACHMENT_ADDED,
                              rxChangeAdminVO.getNoOfRxAttachment());
                }              
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_SUMMARY,
                        EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeAdminVO.getSummaryOfChanges()))));
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_REVIEWED_AND_APPROVED_BY,
                        rxChangeAdminVO.getAcceptanceFlag());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_INTERNAL_NOTES,
                        EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeAdminVO.getInternalNotes()))));                
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getReviewerNotes())){
                    rxChangeAdminHQuery.setParameter(
                            RMDCommonConstants.RX_CHANGE_REVIEWER_NOTES,
                            (EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeAdminVO.getReviewerNotes())))));
               
                }else
                    rxChangeAdminHQuery.setParameter(
                            RMDCommonConstants.RX_CHANGE_REVIEWER_NOTES,rxChangeAdminVO
                            .getReviewerNotes());
                if(!RMDCommonUtility.isNullOrEmpty(rxChangeAdminVO.getTargetImplementationDate())){
                      rxChangeAdminHQuery.setParameter(RMDCommonConstants.RX_CHANGE_TARGET_IMP_DATE,
                              rxChangeAdminVO.getTargetImplementationDate());      
                }                     
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.USER_ID,
                        rxChangeAdminVO.getUserId());
                rxChangeAdminHQuery.setParameter(RMDCommonConstants.DRAFT,
                        rxChangeAdminVO.getSaveAsDraft());
                rxChangeAdminHQuery.executeUpdate();
                
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getCustomers())) {
                    for (String customerObjId : rxChangeAdminVO.getCustomers()) {
                        rxChangeCustomerQuery = new StringBuilder();
                       
                        rxChangeCustomerQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_CUSTOMERS ");
                        rxChangeCustomerQuery
                                .append(" (RX_CHANGE_REQ_CUSTOMERS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_CUSTOMER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                        rxChangeCustomerQuery
                                .append(" (GETS_RMD_RX_CHNG_CUSTOMERS_SQ.NEXTVAL,:objId,:custObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

                        final Query rxChangeCustQuery = objSession
                                .createSQLQuery(rxChangeCustomerQuery
                                        .toString());
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getRxChangeRequestId());
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.CUSTOBJID, customerObjId);
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.DATASOURCE,
                                RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);
                        rxChangeCustQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeCustQuery.executeUpdate();
                        
                    }
                }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getModelsImpacted())) {
                    for (String modelObjId : rxChangeAdminVO
                            .getModelsImpacted()) {
                        rxChangeModelQuery = new StringBuilder();
                        rxChangeModelQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_MODELS ");
                        rxChangeModelQuery
                                .append(" (RX_CHANGE_REQ_MODELS_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_MODEL,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                        rxChangeModelQuery
                                .append(" (GETS_RMD_RX_CHNG_MODELS_SEQ.NEXTVAL,:objId,:modelObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP, :dataSource) ");

                        Query rxChangeModelHQuery = objSession
                                .createSQLQuery(rxChangeModelQuery.toString());

                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeAdminVO.getRxChangeRequestId());
                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.MODEL_OBJID,modelObjId);
                        rxChangeModelHQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeModelHQuery
                                .setParameter(
                                        RMDCommonConstants.DATASOURCE,
                                        RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);
                        rxChangeModelHQuery.executeUpdate();

                    }
                }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getRxsImpacted())) {
                    for (String rxObjId : rxChangeAdminVO.getRxsImpacted()) {
                        rxChangeRxTitleQuery = new StringBuilder();
                        rxChangeRxTitleQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_RX_TITLE ");
                        rxChangeRxTitleQuery
                                .append(" (RX_CHANGE_PRCSNG_RXTITLE_SQ_ID,RX_CHANGE_PROCESSING_DET_OBJID,LINK_RX_TITLE,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES  ");
                        rxChangeRxTitleQuery
                                .append(" (GETS_RMD_RX_CHNG_RX_TITLE_SEQ.NEXTVAL,:objId,:rxObjId, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

                        Query rxChangeRxTitleHQuery = objSession
                                .createSQLQuery(rxChangeRxTitleQuery.toString());
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.OBJ_ID, rxChangeProcObjId);
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.RX_OBJ_ID, rxObjId);
                        rxChangeRxTitleHQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeRxTitleHQuery.executeUpdate();

                    }
                }
                if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getAdditionalReviewerLst())) {                             
                    for (String reviewerId : rxChangeAdminVO
                            .getAdditionalReviewerLst()) {
                        StringBuilder rxChangeAdditionalQuery = new StringBuilder();        
                        rxChangeAdditionalQuery
                                .append("INSERT INTO GETS_RMD_RX_CHNG_REVIEWERS ");
                        rxChangeAdditionalQuery
                                .append(" (RX_CHANGE_PRCSNG_RVWRS_SEQ_ID,RX_CHANGE_PRCSNG_RVWRS_OBJID,LINK_REVIEWER,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES  ");
                        rxChangeAdditionalQuery
                                .append(" (GETS_RMD_RX_CHNG_REVIEWERS_SEQ.NEXTVAL,:objId,:additionalReviewer, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP) ");

                        final Query rxChangeAdditionalHQuery = objSession
                                .createSQLQuery(rxChangeAdditionalQuery
                                        .toString());
                        rxChangeAdditionalHQuery.setParameter(
                                RMDCommonConstants.OBJ_ID,
                                rxChangeProcObjId);
                        rxChangeAdditionalHQuery.setParameter(
                                RMDCommonConstants.ADDITIONAL_REVIEWER,
                                reviewerId);
                        rxChangeAdditionalHQuery.setParameter(
                                RMDCommonConstants.USERNAME,
                                rxChangeAdminVO.getUserId());
                        rxChangeAdditionalHQuery.executeUpdate();
                    }
                }     
            if (RMDCommonUtility.isCollectionNotEmpty(rxChangeAdminVO
                        .getRxChangeReasonsLst())) {
                for (String typeofChangeId : rxChangeAdminVO
                        .getRxChangeReasonsLst()) {
                     StringBuilder rxChangeTypeQuery = new StringBuilder();
                    rxChangeTypeQuery
                           .append("INSERT INTO GETS_RMD_RX_CHNG_TYPE ");
                    rxChangeTypeQuery
                           .append(" (RX_CHANGE_REQ_TYPE_SEQ_ID,RX_CHANGE_REQ_OBJID,LINK_TYPEOFCHANGE,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE,DATA_SOURCE) VALUES  ");
                    rxChangeTypeQuery
                           .append(" (GETS_RMD_RX_CHNG_TYPE_SQ.NEXTVAL,:objId,:typeOfRxChange, :userName, SYSTIMESTAMP,:userName,SYSTIMESTAMP,:dataSource) ");
                
                   final Query rxChangeTypeHQuery = objSession
                           .createSQLQuery(rxChangeTypeQuery
                                   .toString());
                   rxChangeTypeHQuery.setParameter(
                           RMDCommonConstants.OBJ_ID,
                           rxChangeAdminVO.getRxChangeRequestId());
                   rxChangeTypeHQuery.setParameter(RMDCommonConstants.DATASOURCE,
                           RMDCommonConstants.RX_CHANGE_ADMIN_REQUEST_DATASOURCE);          
                   rxChangeTypeHQuery.setParameter(
                           RMDCommonConstants.TYPE_OF_RX_CHANGE, typeofChangeId);
                   rxChangeTypeHQuery.setParameter(
                           RMDCommonConstants.USERNAME,
                           rxChangeAdminVO.getUserId());
                   rxChangeTypeHQuery.executeUpdate();    
                }  
                
            }
                transaction.commit();
                if (transaction.wasCommitted()) {
                    result = RMDCommonConstants.SUCCESS + "_"
                            + rxChangeProcObjId;
                }
            }          
        } catch (RMDDAOConnectionException ex) {
            rxChangeDaoLog.error(ex, ex);
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_CONNECTION_EXCEPTION);
            result = RMDCommonConstants.FAILURE;
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), ex,
                    RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            rxChangeDaoLog.error(
                    "Exception occurred in saveUpdateRxChangeAdminDetails method:", e);
            result = RMDCommonConstants.FAILURE;
            transaction.rollback();
            String errorCode = RMDCommonUtility
                    .getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FETCH_REPAIR_CODE);
            throw new RMDDAOException(errorCode, new String[] {},
                    RMDCommonUtility.getMessage(errorCode, new String[] {},
                            RMDCommonConstants.ENGLISH_LANGUAGE), e,
                    RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(objSession);
        }
        return result;
    }
    
    @Override
    public RxChangeAdminVO getRxChangeAdminData(RxChangeSearchVO rxChangeSearchVO)
            throws RMDDAOException {
        
        Session objSession = null;
        String strCustomer=null;                
        RxChangeAdminVO rxChangeAdminVO = null;
        try {
            objSession = getHibernateSession();
            StringBuilder rxChangeQuery = new StringBuilder();
            
            rxChangeQuery.append(RMDServiceConstants.SQL_RX_CHANGE_ADMIN_QUERY);
                    
            Query rxChangeHqry = objSession.createSQLQuery(rxChangeQuery.toString());

            if(!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxChangeReqObjId())){
                rxChangeHqry.setParameter(RMDCommonConstants.RX_CHANGE_REQ_OBJ_ID,
                        rxChangeSearchVO.getRxChangeReqObjId());
            }               
            if(!RMDCommonUtility.isNullOrEmpty(rxChangeSearchVO.getRxChangeProcObjId())){
                rxChangeHqry.setParameter(RMDCommonConstants.RX_CHANGE_PROC_OBJ_ID,
                        rxChangeSearchVO.getRxChangeProcObjId());
            }                           
            List<Object[]> arlResults = rxChangeHqry.list();
            if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                for (Object[] rxChangeObj : arlResults) {
                    rxChangeAdminVO = new RxChangeAdminVO();
                    rxChangeAdminVO.setObjId(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[0]));
                    rxChangeAdminVO.setRxChangeRequestId(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[1]));
                    rxChangeAdminVO.setNewRxCreated(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[2]));
                    rxChangeAdminVO.setAnyChangeinMaterial(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[3]));
                    rxChangeAdminVO.setTriggerLogicChange(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[4]));
                    rxChangeAdminVO.setUnfamiliarSystemChange(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[5]));
                    rxChangeAdminVO.setNoOfRxAttachment(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[6]));
                    if (null != rxChangeObj[7]) {
                        Clob cb = (Clob) rxChangeObj[7];
                        if(cb != null && cb.length() > 0){
                            rxChangeAdminVO.setSummaryOfChanges(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }
                    }                
                    rxChangeAdminVO.setAcceptanceFlag(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[8]));
                    if (null != rxChangeObj[9]) {
                        Clob cb = (Clob) rxChangeObj[9];
                        if(cb != null && cb.length() > 0){
                            rxChangeAdminVO.setInternalNotes(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }
                    }
                    if (null != rxChangeObj[10]) {
                        Clob cb = (Clob) rxChangeObj[10];
                        if(cb != null && cb.length() > 0){
                            rxChangeAdminVO.setReviewerNotes(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }
                    }                   
                            
                    rxChangeAdminVO.setTargetImplementationDate(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[11]));           
                    if(null!=rxChangeObj[12]){                      
                        Clob cb = (Clob) rxChangeObj[12];
						if (cb != null && cb.length() > 0) {
							rxChangeAdminVO.setModel(cb.getSubString(1,
									(int) cb.length()));
						}
                    }
                    if (null != rxChangeObj[13]) {
                    	 Clob cb = (Clob) rxChangeObj[13];
                    	 String customer = cb.getSubString(1,
									(int) cb.length());
                        for (String objid : RMDCommonUtility
                                .convertObjectToString(customer)
                                .split(Pattern
                                        .quote(RMDCommonConstants.PIPELINE_CHARACTER))) {
                            if (RMDCommonUtility.isNullOrEmpty(strCustomer))
                                strCustomer = objid
                                        .split(RMDCommonConstants.TILDA)[0];
                            else
                                strCustomer += RMDCommonConstants.COMMMA_SEPARATOR
                                        + objid.split(RMDCommonConstants.TILDA)[0];
                        }
                        rxChangeAdminVO.setCustomer(strCustomer);
                    }
                    if (null != rxChangeObj[14]) {    
                    	Clob cb = (Clob) rxChangeObj[14];
                       rxChangeAdminVO.setRxList(cb.getSubString(1,
    									(int) cb.length()));
                    }
                    if (null != rxChangeObj[15]) {                      
                        rxChangeAdminVO.setAdditionalReviewer(RMDCommonUtility
                                .convertObjectToString(rxChangeObj[15]));
                    }
                    rxChangeAdminVO.setRxChangeReasons(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[16]));
                    rxChangeAdminVO.setRxchangeAuditInfoLst(getRxChangeAuditTrailInfo(rxChangeSearchVO.getRxChangeReqObjId()));
                }                
            }
        }catch (RMDDAOConnectionException ex) {
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {
           rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getRxChangeOverviewData()", e);
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), e, RMDCommonConstants.MAJOR_ERROR);
       } finally {
           releaseSession(objSession);
       }
        return rxChangeAdminVO; 
        
    }
    
    @Override
    public List<UserServiceVO> getRxChangeAdminUsers() throws RMDDAOException {
        Session session = null;
        try{
        session = getHibernateSession();
        StringBuilder queryString = new StringBuilder();
        List<UserServiceVO> arlSearchResults = null;
        
        queryString.append("SELECT DISTINCT UU.GET_USR_USERS_SEQ_ID, UU.USER_ID, UU.FIRST_NAME, UU.LAST_NAME, UU.EMAIL_ID ");
        queryString.append("FROM GET_USR_PRIVILEGE UP, GET_USR_ROLE_PRIVILEGES URP, GET_USR_USER_ROLES UUR, GET_USR.GET_USR_USERS UU "); 

        queryString.append("WHERE UP.GET_USR_PRIVILEGE_SEQ_ID = URP.LINK_PRIVILEGES ");
        queryString.append("AND URP.LINK_ROLES              = UUR.LINK_ROLES ");
        queryString.append("AND UUR.LINK_USERS              = UU.GET_USR_USERS_SEQ_ID ");
        queryString.append("AND PRIVILEGE_NAME            =:rxChangeAdminPriv ");
        queryString.append("AND UU.STATUS = 1");
        
        if (null != session) {
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.RXCHANGEADMINPRIV, RMDCommonConstants.RX_CHANGE_ADMIN_PRIVILEGE);
            query.setFetchSize(500);
            List<Object[]> arlUsers = query.list();
            UserServiceVO objUserServiceVO = null;
            arlSearchResults = new ArrayList<UserServiceVO>();
            for (Object[] obj : arlUsers) {
                objUserServiceVO = new UserServiceVO();
                objUserServiceVO.setGetUsrUsersSeqId(RMDCommonUtility.convertObjectToLong(obj[0]));
                objUserServiceVO.setUserId(RMDCommonUtility.convertObjectToString(obj[1]));
                objUserServiceVO.setStrFirstName(RMDCommonUtility.convertObjectToString(obj[2]));
                objUserServiceVO.setStrLastName(RMDCommonUtility.convertObjectToString(obj[3]));
                objUserServiceVO.setStrEmail(RMDCommonUtility.convertObjectToString(obj[4]));
                arlSearchResults.add(objUserServiceVO);
            }
            arlUsers=null;
        }
        return arlSearchResults;
        } catch (RMDDAOConnectionException ex) {
        String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
        throw new RMDDAOException(errorCode, new String[] {},
                RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {          
        String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
        throw new RMDDAOException(errorCode, new String[] {},
                RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                RMDCommonConstants.MAJOR_ERROR);
       } finally {
        releaseSession(session);
      }
    }
    
    /**
     * This method will send email notification to Requestor and Admin Group
     * @param rxChangeVO
     */
    public void sendRxChangeEmailNotification(RxChangeVO rxChangeVO, String userEmail, String pdfEmailIdstList) {
        rxChangeDaoLog.debug("RxChangeDAOImpl :: sendRxChangeEmailNotification");
        try {
            EmailContentGenerator emailContentGenerator = new EmailContentGenerator();

            StringBuilder emailSubject = new StringBuilder();
            String strEmailBody = null;
            File whitePaperPdfFile = null;
            RxChangeAdminVO rxChangeAdminVO = null;

            File fromFile;
            FileInputStream fis = null;
            String delvRxPDFPath = null;
            String pdfURL = null;
            String url = null;
            String geLogoPath = null;
            String custEmailId = "";

            Properties mProp = new Properties();

            fromFile = new File(System.getProperty("ps.application.appdata") + File.separator + "rmd.properties");
            fis = new FileInputStream(fromFile);
            mProp.load(fis);

            String referredEnvStr = mProp.getProperty("CONNECTION_URL");
            delvRxPDFPath = mProp.getProperty("DELV_PDF_URL");
            pdfURL = mProp.getProperty("DELV_RX_PDF_PATH");
            geLogoPath = "file:" + mProp.getProperty("OHV_IMAGES_PATH") + RMDServiceConstants.GE_LOGO;

            if (referredEnvStr.contains("dev")) {
                emailSubject.append("DEV - ");
                url = "https://dev-omd.getransportation.com/RMDWeb/login";
            }
            if (referredEnvStr.contains("qa")) {
                emailSubject.append("QA - ");
                url = "https://qa-omd.getransportation.com/RMDWeb/login";
            } else {
                url = referredEnvStr;
            }
            
            if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getUserEmail()) && !(rxChangeVO.getUserEmail().equals(userEmail)))
                toDL = rxChangeVO.getUserEmail()+", "+userEmail;
                else
                    toDL = userEmail;

            if (RMDCommonConstants.RX_CHANGE_STATUS_APPROVED.equalsIgnoreCase(rxChangeVO.getStatus())) {

                RxChangeSearchVO rxChangeSearchVO = new RxChangeSearchVO();
                rxChangeSearchVO.setRxChangeReqObjId(String.valueOf(rxChangeVO.getObjid()));
                rxChangeSearchVO.setRxChangeProcObjId(rxChangeVO.getRxChangeProcObjId());
                rxChangeAdminVO = getRxChangeAdminData(rxChangeSearchVO);                
                
                RxChangeWhitePaperPDFVO rxChangeWhitePaperPDFVO = setRxChangeWhitePaperPDFVO(rxChangeAdminVO,
                        rxChangeVO, geLogoPath);
                
                whitePaperPdfFile = generateWhitePaperPdf(rxChangeWhitePaperPDFVO, delvRxPDFPath, pdfURL,
                        rxChangeAdminVO.getRxChangeRequestId());
                
                if (!RMDCommonUtility.isNullOrEmpty(pdfEmailIdstList))
                    toDL = toDL+", "+pdfEmailIdstList;
                
                custEmailId = getCustomerSubscribedMailList(getRxTitleObjIdString(rxChangeAdminVO), rxChangeAdminVO.getCustomer(), getModelIdString(rxChangeAdminVO));
            }

            System.out.println("Forming Email Content");
            strEmailBody = emailContentGenerator.getEmailContent(rxChangeAdminVO, rxChangeVO, url);

            System.out.println("Forming Email subject");
            if (RMDCommonConstants.RX_CHANGE_STATUS_PENDING.equalsIgnoreCase(rxChangeVO.getStatus())) {
                emailSubject.append(RMDCommonConstants.RX_CHANGE_SUBMIT_MAIL_SUBJECT).append(rxChangeVO.getRequestId());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getRxTitle());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getTypeOfRxChange());
                emailSubject.append(" - ");
                emailSubject.append(RMDCommonConstants.RX_CHANGE_STATUS_PENDING);               
            }
            if (RMDCommonConstants.RX_CHANGE_STATUS_APPROVED.equalsIgnoreCase(rxChangeVO.getStatus())) {
                emailSubject.append(RMDCommonConstants.RX_CHANGE_SUBMIT_MAIL_SUBJECT);
                emailSubject.append(rxChangeVO.getRequestId());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getRxTitle());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getTypeOfRxChange());
                emailSubject.append(" - ");
                emailSubject.append(RMDCommonConstants.RX_CHANGE_STATUS_APPROVED);
            }
            if (RMDCommonConstants.RX_CHANGE_STATUS_PENDING_REQUESTOR.equalsIgnoreCase(rxChangeVO.getStatus())) {

                emailSubject.append(RMDCommonConstants.RX_CHANGE_SUBMIT_MAIL_SUBJECT);
                emailSubject.append(rxChangeVO.getRequestId());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getRxTitle());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getTypeOfRxChange());
                emailSubject.append(" - ");
                emailSubject.append(RMDCommonConstants.RX_CHANGE_STATUS_PENDING_REQUESTOR_MSG);
            }
            if (RMDCommonConstants.RX_CHANGE_STATUS_REJECTED.equalsIgnoreCase(rxChangeVO.getStatus())) {
                emailSubject.append(RMDCommonConstants.RX_CHANGE_SUBMIT_MAIL_SUBJECT);
                emailSubject.append(rxChangeVO.getRequestId());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getRxTitle());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getTypeOfRxChange());
                emailSubject.append(" - ");
                emailSubject.append(RMDCommonConstants.RX_CHANGE_STATUS_REJECTED);
            }
            if (RMDCommonConstants.RX_CHANGE_STATUS_OPEN.equalsIgnoreCase(rxChangeVO.getStatus())) {
                emailSubject.append(RMDCommonConstants.RX_CHANGE_SUBMIT_MAIL_SUBJECT);
                emailSubject.append(rxChangeVO.getRequestId());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getRxTitle());
                emailSubject.append(" - ");
                emailSubject.append(rxChangeVO.getTypeOfRxChange());
                emailSubject.append(" - ");
                emailSubject.append(RMDCommonConstants.RX_CHANGE_STATUS_OPEN);
            }
            if (RMDCommonConstants.RX_CHANGE_STATUS_ESCALATION.equalsIgnoreCase(rxChangeVO.getStatus())) {
                emailSubject.append(RMDCommonConstants.RX_CHANGE_REQUEST_ESCALATED).append(rxChangeVO.getRequestId());
                rxChangeVO.setStatus(RMDCommonConstants.RX_CHANGE_STATUS_PENDING);
                if (!RMDCommonUtility.isNullOrEmpty(pdfEmailIdstList))
                    toDL = toDL+", "+pdfEmailIdstList;
                
            }
            
            
                                 
            System.out.println("toDL :: "+toDL);
            System.out.println("emailSubject :: "+emailSubject);
            
            RMDCommonUtility.SendEmail(mailServerHost, smtpServerName, emailSubject.toString(),
                    strEmailBody, fromDL, toDL, whitePaperPdfFile);
            
            if (RMDCommonConstants.RX_CHANGE_STATUS_APPROVED.equalsIgnoreCase(rxChangeVO.getStatus())) {
                
                if(!RMDCommonUtility.isNullOrEmpty(custEmailId)){
                    System.out.println("Sending EMAIL to subscribed Customers :: "+custEmailId);
                emailSubject = new StringBuilder();
                emailSubject.append("GE Global Peformance Optimization Center (GPOC) Rx Change Notice");
                System.out.println("Sending email to the customer subscribed");
                String emailBody = RMDCommonConstants.RX_CHANGE_MAIL_SEND_IN_BCC+"This is an automatically generated email, please do not reply to this message. If you have questions or concerns about the information provided in this email, please contact the GPOC.";
                RMDCommonUtility.SendEmail(mailServerHost, smtpServerName, emailSubject.toString(),
                        emailBody, fromDL, custEmailId, whitePaperPdfFile);
                }else{
                    System.out.println("No Customers are subscribed");
                }
            }           
             
            System.out.println("Email Sent successfully");
            
        } catch (Exception e) {
            e.printStackTrace();
            rxChangeDaoLog.error("****** Rx Change Process SendEmail method failed ******" + e.toString());
        }
    }

    public RxChangeWhitePaperPDFVO setRxChangeWhitePaperPDFVO(RxChangeAdminVO rxChangeAdminVO, RxChangeVO rxChangeVO,
            String geLogoPath) {
        rxChangeDaoLog.debug("RxChangeDAOImpl :: setRxChangeWhitePaperPDFVO");
        
        RxChangeWhitePaperPDFVO rxChangeWhitePaperPDFVO = new RxChangeWhitePaperPDFVO();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        long start = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(start);
        Date calculationDate = new java.util.Date(timestamp.getTime());
        try {
            rxChangeWhitePaperPDFVO.setIssueDate(sdf.format(calculationDate));

            if ((RMDServiceConstants.STRING_YES).equalsIgnoreCase(rxChangeAdminVO.getNewRxCreated())) {
                rxChangeWhitePaperPDFVO.setNewRxCreated(RMDServiceConstants.RXCHANGE_NEW_RX);
            } else {
                rxChangeWhitePaperPDFVO.setNewRxCreated(RMDServiceConstants.RXCHANGE_EXISTING_RX);
            }
           
            List<String> rxChangeReasonList = new ArrayList<String>();
            if (null != rxChangeAdminVO.getRxChangeReasons()) {
                for (String objid : rxChangeAdminVO.getRxChangeReasons().split(
                        Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR))) {
                    rxChangeReasonList.add(objid.split(RMDCommonConstants.TILDA)[1]);
                }
            }
            
            List<String> rxTitleList = new ArrayList<String>();
            if (null != rxChangeAdminVO.getRxList()) {
                for (String objid : rxChangeAdminVO.getRxList().split(
                        Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))) {                    
                    rxTitleList.add(objid.split(Pattern.quote(RMDCommonConstants.TILDA))[1]);                    
                }
            }

            StringBuilder modelString = new StringBuilder();

            if (null != rxChangeAdminVO.getModel()) {
                if (rxChangeAdminVO.getModel().contains(RMDCommonConstants.COMMMA_SEPARATOR)) {

                    String[] modelStrArray = rxChangeAdminVO.getModel().split(
                            Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR));

                    int i = 1;
                    int j = modelStrArray.length;

                    for (String objid : modelStrArray) {
                        if (!RMDCommonUtility.isNullOrEmpty(objid)) {
                            modelString.append(objid.split(Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))[1]);
                            if (i < j) {
                                modelString.append(RMDCommonConstants.COMMMA_SEPARATOR);
                            }
                            i++;
                        }
                    }
                } else {
                    modelString.append(rxChangeAdminVO.getModel().split(
                            Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))[1]);
                }
            }
            
            
            String modelStr = modelString.toString().replace(RMDCommonConstants.COMMMA_SEPARATOR, ", ");
            
            if(rxTitleList != null){
            rxChangeWhitePaperPDFVO.setRxsImpacted(rxTitleList);
            rxChangeWhitePaperPDFVO.setRxNote(RMDServiceConstants.RXCHANGE_RX_TITLE_NOTE1
                    + rxTitleList.size() + RMDServiceConstants.RXCHANGE_RX_TITLE_NOTE2);
            }
            
            rxChangeWhitePaperPDFVO.setRxChangeReasons(rxChangeReasonList);
            rxChangeWhitePaperPDFVO.setModelsImpacted(modelStr);
            rxChangeWhitePaperPDFVO.setRxChangeRequestId(RMDCommonConstants.RX_CHANGE_SEQ_PREFIX
                    + rxChangeAdminVO.getRxChangeRequestId());
            rxChangeWhitePaperPDFVO.setRequestedBy(rxChangeVO.getUserName());
            rxChangeWhitePaperPDFVO.setSummaryOfChanges(EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(
                    rxChangeAdminVO.getSummaryOfChanges())));
            if ((RMDServiceConstants.STRING_YES).equalsIgnoreCase(rxChangeAdminVO.getTriggerLogicChange()))
                rxChangeWhitePaperPDFVO.setTriggerLogicText(rxChangeVO.getTriggerLogicNote());
            rxChangeWhitePaperPDFVO.setRequestedChanges(EsapiUtil.resumeSpecialChars(ESAPI.encoder().decodeForHTML(
                    rxChangeVO.getChangesSuggested())));
            rxChangeWhitePaperPDFVO.setGeLogoPath(geLogoPath);
            rxChangeDaoLog.debug("rxChangeWhitePaperPDFVO :: " + rxChangeWhitePaperPDFVO);

        } catch (Exception e) {           
            rxChangeDaoLog.error("****** Rx Change Process creation of setRxChangeWhitePaperPDFVO bean failed ******"
                    + e.toString());
        }
        return rxChangeWhitePaperPDFVO;
    }

    public File generateWhitePaperPdf(RxChangeWhitePaperPDFVO rxChangeWhitePaperPDFVO, String delvRxPDFPath,
            String pdfURL, String rxChangeRequestId) {
        rxChangeDaoLog.debug("In generateWhitePaperPdf");

        String pdfPath = null;
        File file = null;
        final String FILE_SEPERATOR_FWD_SLASH = "/";

        try {

            String baseRxFilePath = pdfURL + FILE_SEPERATOR_FWD_SLASH + "RxChangeAttachments"
                    + FILE_SEPERATOR_FWD_SLASH + rxChangeWhitePaperPDFVO.getRxChangeRequestId();
            String baseRxFileName = rxChangeWhitePaperPDFVO.getRxChangeRequestId();

            File dir = new File(baseRxFilePath);
            if (!dir.exists())
                dir.mkdirs();

            rxChangeDaoLog.debug("In generateWhitePaperPdf : baseRxFilePath:" + baseRxFilePath + " :::  baseRxFileName:"
                    + baseRxFileName);

            RxchangePDFGenerator generateRxPDF = new RxchangePDFGenerator(Logger.getLogger(RxchangePDFGenerator.class));
            file = generateRxPDF.createPdfFile(rxChangeWhitePaperPDFVO, baseRxFilePath, baseRxFileName);

            if (file != null) {
                            
                pdfPath = delvRxPDFPath + File.separator + "RxChangeAttachments" + File.separator
                        + rxChangeWhitePaperPDFVO.getRxChangeRequestId() + File.separator + file.getName();                
                RxChangeVO rxChangeVO = new RxChangeVO();
                rxChangeVO.setWhitePaperPdffileName(file.getName());
                rxChangeVO.setWhitePaperPdffilePath(pdfPath);
                rxChangeVO.setObjid(Long.valueOf(rxChangeRequestId));
                saveRxChangeInfo(rxChangeVO);
            }
        } catch (FileNotFoundException filenotfoundexception) {
            rxChangeDaoLog.error("file not found " + filenotfoundexception.toString(), filenotfoundexception);
        } catch (IOException ioexception) {           
            rxChangeDaoLog.error("falied while writing into the file  " + ioexception.toString(), ioexception);
        } catch (SQLException sqlexception) {            
            rxChangeDaoLog.error("falied while communicating to the datbase   " + sqlexception.toString(), sqlexception);
        } catch (Exception exception) {
            rxChangeDaoLog.error("General Exception creating PDF  " + exception.toString(), exception);
        }
        return file;
    }
    
    @Override
    public void saveAuditTrailInfo(RxChangeVO rxChangeVO) throws RMDDAOException {
        Session session = null;
        StringBuilder queryString = null;

        try {
            session = getHibernateSession();
            queryString = new StringBuilder();
            
            if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getStatus())){
            queryString.append(" Insert into GETS_RMD.RX_CHNG_AUDIT_TRAIL_INFO (OBJID,RX_CHANGE_REQ_OBJID,ACTIVITY,PERFORMED_BY,PERFORMED_ON,COMMENTS) values (RX_CHNG_AUDIT_TRAIL_INFO_SEQ.NEXTVAL, ");
            queryString.append(":rxChangeReqObjId, (select objid from gets_rmd_lookup where look_value = 'Escalation'), :userName,SYSTIMESTAMP, :comments) ");
            }else{
            queryString.append(" Insert into GETS_RMD.RX_CHNG_AUDIT_TRAIL_INFO (OBJID,RX_CHANGE_REQ_OBJID,ACTIVITY,PERFORMED_BY,PERFORMED_ON,COMMENTS) values (RX_CHNG_AUDIT_TRAIL_INFO_SEQ.NEXTVAL, ");
            queryString.append(":rxChangeReqObjId, :rxChangeStatusId, :userName,SYSTIMESTAMP, :comments) ");
            }
            
            final Query auditTrailInfoQuery = session.createSQLQuery(queryString.toString());

            if (null != rxChangeVO) {                
                    auditTrailInfoQuery
                            .setParameter(RMDCommonConstants.RX_CHANGE_REQ_OBJ_ID, rxChangeVO.getRequestId());
                    if(RMDCommonUtility.isNullOrEmpty(rxChangeVO.getStatus())){                        
                    auditTrailInfoQuery.setParameter(RMDCommonConstants.RX_CHANGE_STATUS_ID, rxChangeVO.getStatusObjId());
                    }
                    auditTrailInfoQuery.setParameter(RMDCommonConstants.USERNAME, rxChangeVO.getStrUserName());
                    auditTrailInfoQuery.setParameter(RMDCommonConstants.RX_CHANGE_COMMENTS, EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(AppSecUtil.decodeString(rxChangeVO.getNotes()))));
                    auditTrailInfoQuery.executeUpdate();                
            } 
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }
    }
    
    @Override
    public List<RxChangeVO> getRxChangeAuditTrailInfo(String rxChngObjid)
            throws RMDDAOException {
        
        Session objSession = null;              
        RxChangeVO rxChangeVO = null;
        List<RxChangeVO> rxChangeVoLst = new ArrayList<RxChangeVO>();
        //SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH24:MI:ss");
        try {
            objSession = getHibernateSession();
            StringBuilder rxChangeQuery = new StringBuilder();
            
            rxChangeQuery.append(RMDServiceConstants.SQL_RX_CHNG_AUDIT_TRAIL_QUERY);
                    
            Query rxChangeHqry = objSession.createSQLQuery(rxChangeQuery.toString());

            if(!RMDCommonUtility.isNullOrEmpty(rxChngObjid)){
                rxChangeHqry.setParameter(RMDCommonConstants.OBJ_ID, rxChngObjid);
            }               
                                       
            List<Object[]> arlResults = rxChangeHqry.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                for (Object[] rxChangeObj : arlResults) {
                    rxChangeVO = new RxChangeVO();
                    
                    rxChangeVO.setStatus(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[4]));
                    rxChangeVO.setRequestLoggedDate(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[2]));
                    rxChangeVO.setUserName(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[3]));
                  
                  
                    if (null != rxChangeObj[5]) {
                        Clob cb = (Clob) rxChangeObj[5];
                        if(cb != null && cb.length() > 0){
                            rxChangeVO.setNotes(ESAPI.encoder().encodeForXML(cb.getSubString(1, (int)cb.length())));
                            }
                    }                 
                            
                    rxChangeVoLst.add(rxChangeVO); 
                }                
            }
        }catch (RMDDAOConnectionException ex) {
            
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {
           
           rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getRxChangeOverviewData()", e);
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), e, RMDCommonConstants.MAJOR_ERROR);
       } finally {
           releaseSession(objSession);
       }
        return rxChangeVoLst;         
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void sendRxChangeEscalation(String escalationData)
            throws RMDDAOException {
        
        Session objSession = null;              
        RxChangeVO rxChangeVO = null;
        List<RxChangeVO> rxChangeVoLst = new ArrayList<RxChangeVO>();
        
        try {
            objSession = getHibernateSession();
            StringBuilder rxChangeEscalationQuery = new StringBuilder();
            
            rxChangeEscalationQuery.append(RMDServiceConstants.SQL_RX_CHNG_ESCALATION_QUERY);
                    
            Query rxChangeEscQry = objSession.createSQLQuery(rxChangeEscalationQuery.toString());
            List<Object[]> arlResults = rxChangeEscQry.list();
            
            
            if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                for (Object[] rxChangeObj : arlResults) {
                    System.out.println("rxChangeObj :: "+rxChangeObj);
                    rxChangeVO = new RxChangeVO();
                    
                    rxChangeVO.setObjid(Long.parseLong(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[0])));
                    rxChangeVO.setRequestId(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[0]));
                    rxChangeVO.setCustomer(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[1]));
                    rxChangeVO.setRoadNumber(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[2]));
                    rxChangeVO.setRxTitle(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[3]));
                    rxChangeVO.setRequestor(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[4]));
                    rxChangeVO.setRequestLoggedDate(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[5]));
                    rxChangeVO.setUserEmail(RMDCommonUtility
                            .convertObjectToString(rxChangeObj[6]));
                    rxChangeVO.setStatus(RMDCommonConstants.RX_CHANGE_STATUS_ESCALATION);
                    
                    if(!RMDCommonUtility.isNullOrEmpty(rxChangeVO.getRequestor())){
                        rxChangeVO.setUserName(rxChangeVO.getRequestor().split((Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER)))[0]);
                    }
                    
                    sendRxChangeEmailNotification(rxChangeVO, rxChangeVO.getUserEmail(), getAdminEmailIdStr());
                    
                    //rxChangeVO.setUserName("SYSTEM");
                    rxChangeVO.setStatus("(select objid from gets_rmd_lookup where look_value = 'Escalation')");
                    saveAuditTrailInfo(rxChangeVO);
                    updateLastUpdatedBy(rxChangeVO.getRequestId());
                    rxChangeVoLst.add(rxChangeVO); 
                }                
            }
        }catch (RMDDAOConnectionException ex) {
            
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {
           
           rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getRxChangeOverviewData()", e);
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), e, RMDCommonConstants.MAJOR_ERROR);
       } finally {
           releaseSession(objSession);
       }        
    }
    
    public String getAdminEmailIdStr() throws  RMDDAOException {
        StringBuilder emailIdStr = null;
        String strEmailIdResult = "";
        try {
            List<UserServiceVO> lstUserVo = getRxChangeAdminUsers();

            if (null != lstUserVo && !lstUserVo.isEmpty()) {
                emailIdStr = new StringBuilder();

                for (UserServiceVO userServiceVO : lstUserVo) {
                    if (emailIdStr.length() > 0) {
                        emailIdStr.append(", ");
                    }
                    emailIdStr.append(userServiceVO.getStrEmail());
                }
                strEmailIdResult = emailIdStr.toString();
            }
        } catch (RMDDAOConnectionException ex) {
            
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {
           
           rxChangeDaoLog.error("Unexpected Error occured in RxChangeDAOImpl getRxChangeOverviewData()", e);
           String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_MY_CASES);
           throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                   ""), e, RMDCommonConstants.MAJOR_ERROR);
       }

        return strEmailIdResult;
    }
    
    public void updateLastUpdatedBy(String objid){
        Session session = null;
        StringBuilder queryString = null;
        

        try {
            session = getHibernateSession();
            queryString = new StringBuilder();
            queryString
                    .append(" UPDATE GETS_RMD_RX_CHNG_REQ_DETAILS SET LAST_UPDATED_DATE = systimestamp where RX_CHANGE_REQ_SEQ_ID = :rxChangeReqObjId ");
            
            final Query rxchngupdateQuery = session.createSQLQuery(queryString.toString());

            if (null != objid) {                
                rxchngupdateQuery
                            .setParameter(RMDCommonConstants.RX_CHANGE_REQ_OBJ_ID, objid);
                rxchngupdateQuery.executeUpdate();                
            } 
        } catch (RMDDAOConnectionException ex) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }        
    }
    
    public String getCustomerSubscribedMailList(String rxObjId, String custObjId, String modelObjId){
        System.out.println("rxObjId :: "+rxObjId);
        System.out.println("custObjId :: "+custObjId);
        System.out.println("modelObjId :: "+modelObjId);
        
        StringBuilder mailIdStr = new StringBuilder();
        StringBuilder alertSubMailid = new StringBuilder();
        Session session = null;
        try{
            session = getHibernateSession();
            
            
            alertSubMailid.append("SELECT DISTINCT USER_EMAIL FROM GETS_RMD.GETS_RMD_SUBSCRIPTION_ALERT_V V, GET_USR.GET_USR_CUSTOMER_ALERT C, ");
            alertSubMailid.append("GET_USR.GET_USR_MODEL_ALERT M, TABLE_BUS_ORG CC, gets_rmd_model mm ");
            alertSubMailid.append("WHERE V.CUSTOMER_OBJID = C.OBJID(+) AND V.MODEL_OBJID = M.OBJID(+) AND C.cust_id2bus_org = CC.OBJID(+) ");
            alertSubMailid.append("AND m.model_objid2rmd_model = mm.objid(+) ");
            alertSubMailid.append("AND (RXALERT_VALUE IN (SELECT urgency FROM gets_rmd.gets_sd_recom WHERE objid IN (:rxObjId)) OR ");
            alertSubMailid.append("RXALERT_VALUE IN (SELECT LOCO_IMPACT FROM GETS_RMD.GETS_SD_RECOM WHERE OBJID IN (:rxObjId))) ");    
            alertSubMailid.append("AND CC.OBJID in (:custObjId) AND (mm.objid is null OR mm.objid IN (:modelObjId)) ");
            alertSubMailid.append("AND NOTIFY_EVENT_TYPE = 'Rx Change' AND SERVICE = 'EOA' AND V.ACTIVE_FLAG = 'Y'");
            
            System.out.println("alertSubMailid :: "+alertSubMailid);
            
            final Query alertSubMailidQuery = session.createSQLQuery(alertSubMailid.toString());
            
            alertSubMailidQuery.setParameterList("rxObjId", rxObjId.split(
                        Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR)));
            alertSubMailidQuery.setParameterList("custObjId", custObjId.split(
                        Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR)));
            
            alertSubMailidQuery.setParameterList("modelObjId", modelObjId.split(
                        Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR)));
            
            System.out.println("alertSubMailidQuery :: "+alertSubMailidQuery.getQueryString());
            
            List<String> arlResults = alertSubMailidQuery.list();
            
            if (RMDCommonUtility.isCollectionNotEmpty(arlResults)) {                
                for (String rxChangeObj : arlResults) {
                    if(mailIdStr.length() > 0){
                        mailIdStr.append(", ");
                    }
                    mailIdStr.append(RMDCommonUtility
                            .convertObjectToString(rxChangeObj));
                }
                System.out.println("Subscribed Mail Id's for the  "+mailIdStr);
                return mailIdStr.toString();
                
            }else{
                System.out.println("No DATA found for the Alerts subscription");
            }
           
            
        }catch (RMDDAOConnectionException ex) {
            ex.printStackTrace();
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), ex, RMDCommonConstants.FATAL_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
            throw new RMDDAOException(errorCode, new String[]{}, RMDCommonUtility.getMessage(errorCode, new String[]{},
                    ""), e, RMDCommonConstants.MAJOR_ERROR);
        } finally {
            releaseSession(session);
        }       
        return null;
    }
    
    public String getRxTitleObjIdString(RxChangeAdminVO rxChangeAdminVO) {
        
        StringBuilder rxTitleObjIdStr = new StringBuilder();
        if (null != rxChangeAdminVO.getRxList()) {
            for (String objid : rxChangeAdminVO.getRxList().split(
                    Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))) {
                if(rxTitleObjIdStr.length() > 0){
                    rxTitleObjIdStr.append(", ");
                }
                
                rxTitleObjIdStr.append(objid.split(Pattern.quote(RMDCommonConstants.TILDA))[0]);
            }
        }
        return rxTitleObjIdStr.toString();
    }
    
    public String getModelIdString(RxChangeAdminVO rxChangeAdminVO){        

        StringBuilder modelForMailStr = new StringBuilder();
        
  
        if (null != rxChangeAdminVO.getModel()) {
            if (rxChangeAdminVO.getModel().contains(RMDCommonConstants.COMMMA_SEPARATOR)) {

                String[] modelStrArray = rxChangeAdminVO.getModel().split(
                        Pattern.quote(RMDCommonConstants.COMMMA_SEPARATOR));

                int i = 1;
                int j = modelStrArray.length;

                for (String objid : modelStrArray) {
                    if (!RMDCommonUtility.isNullOrEmpty(objid)) {
                        
                        modelForMailStr.append(objid.split(Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))[0]);
                        if (i < j) {                           
                            modelForMailStr.append(RMDCommonConstants.COMMMA_SEPARATOR);
                        }
                        i++;
                    }
                }
            } else {                
                modelForMailStr.append(rxChangeAdminVO.getModel().split(
                        Pattern.quote(RMDCommonConstants.PIPELINE_CHARACTER))[0]);
            }
        }
        
        
        String modelStr = modelForMailStr.toString().replace(RMDCommonConstants.COMMMA_SEPARATOR, ", ");
        return modelStr;
    }
    /**
     * @Author:
     * @param:
     * @return: String
     * @throws:Exception
     * @Description: This method is used for uploading multiple attachments.
     */
	public String uploadRxAttachment(String fileData, String fileName,BigDecimal rxRequestIdSeq)
			throws Exception {
		String filePath = null;
		File file = null;		
		try {	
			File dir = new File(delvRxPDFPath + File.separator
					+ "RxChangeAttachments" + File.separator + "RxReq"
					+ String.valueOf(rxRequestIdSeq));
		if (!dir.exists())
				dir.mkdirs();
			file = new File(dir.getAbsolutePath()
					+ File.separator + fileName);
			
			byte[] bDecodedArr = Base64.decode(fileData.getBytes());
			FileUtils.writeByteArrayToFile(file, bDecodedArr);
			filePath = pdfURL+File.separator+"RxChangeAttachments"+ File.separator + "RxReq"+String.valueOf(rxRequestIdSeq) +File.separator + fileName;
			
		} catch (IOException ioexception) {
			rxChangeDaoLog.error(
					"Unable to write the file exception occured in uploadRxAttachment() method - RxChangeController",
					ioexception);
		}
		catch (Exception exception) {
			rxChangeDaoLog.error(
					"RMDWebException occurred while creating file uploadRxAttachment() method - RxChangeController",
					exception);
		}
		return filePath;
	}
	
	@Override
    public String getRxChangeReqId(String scId) throws RMDDAOException {
        Session session = null;
        try{
        session = getHibernateSession();
        StringBuilder queryString = new StringBuilder();
        
        queryString.append("select RX_CHANGE_REQ_SEQ_ID from GETS_RMD_RX_CHNG_REQ_DETAILS where SUPPORT_CENTRAL_REQ_ID = :rxChangeAdminPriv ");
        queryString.append("AND UU.STATUS = 1");
        
        if (null != session) {
            Query query = session.createSQLQuery(queryString.toString());
            query.setParameter(RMDCommonConstants.RXCHANGEADMINPRIV, scId);
            query.setFetchSize(500);
            List<Object[]> arlUsers = query.list();
            for (Object[] obj : arlUsers) {
               return RMDCommonUtility.convertObjectToString(obj[0]);
            }
            
        }
        
        } catch (RMDDAOConnectionException ex) {
        String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
        throw new RMDDAOException(errorCode, new String[] {},
                RMDCommonUtility.getMessage(errorCode, new String[] {}, ""),
                ex, RMDCommonConstants.FATAL_ERROR);
       } catch (Exception e) {          
        String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_CHANGE);
        throw new RMDDAOException(errorCode, new String[] {},
                RMDCommonUtility.getMessage(errorCode, new String[] {}, ""), e,
                RMDCommonConstants.MAJOR_ERROR);
       } finally {
        releaseSession(session);
      }
        return null;
    }

}
