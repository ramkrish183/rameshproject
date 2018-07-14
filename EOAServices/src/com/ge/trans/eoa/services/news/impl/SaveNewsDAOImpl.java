package com.ge.trans.eoa.services.news.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Value;


import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.Base64;

@SuppressWarnings("unchecked")
public class SaveNewsDAOImpl extends BaseDAO implements NewsDAOIntf {

	public static final RMDLogger LOG = RMDLoggerHelper
			.getLogger(SaveNewsDAOImpl.class);
	private static final long serialVersionUID = 1L;
	
	@Value("${" + RMDCommonConstants.DELV_RX_PDF_PATH + "}")
    String delvRxPDFPath;
    @Value("${" + RMDCommonConstants.PDF_URL + "}")
    String pdfURL;

	@Override
	public String saveNews(SaveNewsVO saveNewsVO) throws RMDDAOException {
	
		Session session = null;
		String docPath =null;
		String docTitle=null;
		String imgPath=null;
		int recordCount=0;
		List<Object> resultList;
		Transaction transaction = null;
		String sequenceId = null;
		try {
			session = getHibernateSession();
			transaction=session.beginTransaction();
			StringBuilder newsQry = new StringBuilder();
			StringBuilder imgChkQry = new StringBuilder();
			StringBuilder imgInsertQry = new StringBuilder();
			StringBuilder imgUpdateQry = new StringBuilder();
			StringBuilder newsUpdateQry = new StringBuilder();
			StringBuilder newsUpdateQry2 = new StringBuilder();
			StringBuilder userNewsUpdateQry = new StringBuilder();
			StringBuilder userNewsUpdateQry2 = new StringBuilder();
			StringBuilder selectLinkUsersQry = new StringBuilder();
			StringBuilder sequenceQry = new StringBuilder();
			List<String> customers = new ArrayList<String>();
			customers = saveNewsVO.getCustomerarray();
 
			
			sequenceQry.append("select GETS_RMD.GETS_RMD_CUSTOMER_NEWS_SEQ.NEXTVAL from DUAL");
			newsQry.append("INSERT INTO GETS_RMD.GETS_RMD_CUSTOMER_NEWS (OBJID,CUSTOMER_ID,DESCRIPTION,ATTACHMENT_PATH,ATTACHMENT_NAME,STATUS,CREATED_BY,CREATION_DATE,EXPIRY_DATE,UPDATED_BY,UPDATED_DATE) VALUES (:objId,");
            newsQry.append("  :custName , :newsDesc , :attachment,:attachName,'Y',:userId, SYSDATE ,TO_DATE(:expiryDate,'DD/MM/YYYY HH24:MI:SS'),:userId,SYSDATE) "); 
            imgChkQry.append("SELECT COUNT(1) FROM GETS_RMD.CUSTOMER_NEWS_BG_IMG WHERE CUSTUMER_ID=:custId");
            imgUpdateQry.append("UPDATE GETS_RMD.CUSTOMER_NEWS_BG_IMG SET BG_IMG_PATH=:imgPath WHERE CUSTUMER_ID=:custId");
            imgInsertQry.append("INSERT INTO GETS_RMD.CUSTOMER_NEWS_BG_IMG (CUSTUMER_ID,BG_IMG_PATH) VALUES (:custId,:imgPath)");
            newsUpdateQry.append("UPDATE GETS_RMD.GETS_RMD_CUSTOMER_NEWS SET DESCRIPTION=:newsDesc,ATTACHMENT_PATH=:attachment,ATTACHMENT_NAME=:attachName,STATUS=:status,UPDATED_BY=:userId,EXPIRY_DATE=TO_DATE(:expiryDate,'DD/MM/YYYY HH24:MI:SS'),UPDATED_DATE=SYSDATE WHERE OBJID=:objId");
            newsUpdateQry2.append("UPDATE GETS_RMD.GETS_RMD_CUSTOMER_NEWS SET DESCRIPTION=:newsDesc,STATUS=:status,UPDATED_BY=:userId,EXPIRY_DATE=TO_DATE(:expiryDate,'DD/MM/YYYY HH24:MI:SS'),UPDATED_DATE=SYSDATE WHERE OBJID=:objId");
            userNewsUpdateQry.append("INSERT INTO GETS_RMD.GETS_RMD_USER_NEWS (LINK_USERS,CUSTOMER_NEWS_ID,READ_FLAG,ACTIVE_FLAG,CREATED_DATE,UPDATED_DATE) VALUES(:linkUsers,:objId,:readFlag,:activeFlag,SYSDATE,SYSDATE)");
            selectLinkUsersQry.append("SELECT cust.LINK_USERS FROM get_usr_user_customers cust,TABLE_BUS_ORG tbo where tbo.OBJID = cust.LINK_CUSTOMERS AND tbo.S_ORG_ID=:customerId");
            userNewsUpdateQry2.append("update GETS_RMD.GETS_RMD_USER_NEWS set  ACTIVE_FLAG=:Y,READ_FLAG=:N,UPDATED_DATE=SYSDATE where CUSTOMER_NEWS_ID=:objId ");
            if(null!=saveNewsVO.getObjDocDetails()){
                docPath = uploadNewsAttachment(saveNewsVO.getObjDocDetails().getDocData(), saveNewsVO.getObjDocDetails().getDocTitle(), "Docs");
                docTitle=saveNewsVO.getObjDocDetails().getDocTitle();
            }
            if(null!=saveNewsVO.getObjImgDetails()){
                imgPath = uploadNewsAttachment(saveNewsVO.getObjImgDetails().getDocData(), saveNewsVO.getObjImgDetails().getDocTitle(), "Imgs");
            }
            
            if(null==saveNewsVO.getObjId()){
			
    			for (String cust : customers) {
    			      
    				final Query sequenceHqry = session.createSQLQuery(sequenceQry.toString());
    				
    				sequenceId  = ((BigDecimal) sequenceHqry.uniqueResult()).toString();
    				final Query newsQuery = session
    						.createSQLQuery(newsQry.toString());
    				newsQuery.setParameter("custName",cust);
    				newsQuery.setParameter(RMDCommonConstants.OBJ_ID, sequenceId);
    				newsQuery.setParameter("newsDesc", EsapiUtil.escapeSpecialChars(saveNewsVO.getNewsDesc()));
    				newsQuery.setParameter("attachment",docPath);
    				newsQuery.setParameter("attachName",docTitle);
    				newsQuery.setParameter("userId",saveNewsVO.getUserId() );
    				newsQuery.setParameter("expiryDate",saveNewsVO.getExpiryDate());
    				newsQuery.executeUpdate();

    				if(null!=saveNewsVO.getObjImgDetails()){
    				    
    				    final Query imgChkHQry = session
                                .createSQLQuery(imgChkQry.toString());
    				    imgChkHQry.setParameter("custId",cust);
    				    resultList = imgChkHQry.list();
    		            
    		            if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
    		                recordCount = RMDCommonUtility.convertObjectToInt(resultList.get(0));
    		            }
    		            
    		            if(recordCount>0){
    		                final Query imgUpdateHQry = session
                                    .createSQLQuery(imgUpdateQry.toString());
    		                imgUpdateHQry.setParameter("custId",cust);
    		                imgUpdateHQry.setParameter("imgPath",imgPath);
    		                imgUpdateHQry.executeUpdate();
    		            }else{
    		                final Query imgInsertHQry = session
                                    .createSQLQuery(imgInsertQry.toString());
    		                imgInsertHQry.setParameter("custId",cust);
    		                imgInsertHQry.setParameter("imgPath",imgPath);
    		                imgInsertHQry.executeUpdate();
    		            }
    				}
    				
    				Query linkUsers = session.createSQLQuery(selectLinkUsersQry.toString());
                    linkUsers.setParameter(RMDCommonConstants.CUSTOMER_ID, cust);
                    List<BigDecimal> users = (ArrayList)linkUsers.list();
                    if (RMDCommonUtility.isCollectionNotEmpty(users)) {
                        final Query updateUserNewsHqry = session
                                .createSQLQuery(userNewsUpdateQry.toString());
                        for (BigDecimal obj : users){
                            if (RMDCommonUtility.convertObjectToString(obj) != null
                                    && !RMDCommonUtility.convertObjectToString(obj)
                                            .trim().equals(RMDCommonConstants.EMPTY_STRING)) {
                                updateUserNewsHqry.setParameter(RMDCommonConstants.LINK_USERS, RMDCommonUtility
                                        .convertObjectToString(obj));
                            }
                            updateUserNewsHqry.setParameter(RMDCommonConstants.OBJ_ID,sequenceId);
                            updateUserNewsHqry.setParameter(RMDCommonConstants.READ_NEWS_FLAG,RMDCommonConstants.N_LETTER_UPPER);
                            updateUserNewsHqry.setParameter(RMDCommonConstants.ACTIVE_NEWS_FLAG,RMDCommonConstants.Y_LETTER_UPPER);
                            updateUserNewsHqry.executeUpdate();
                        }
                    }
                            StringBuilder updateAllCustomerQry = new StringBuilder();
                            updateAllCustomerQry.append("select GET_USR_USERS_SEQ_ID,USER_ID from GET_USR.GET_USR_USERS where LINK_CUSTOMER IS NULL");
                            final Query updateAllUserNewsHqry = session
                                    .createSQLQuery(updateAllCustomerQry.toString());
                             List<Object[]>allUsers = (ArrayList) updateAllUserNewsHqry.list();
                             if (RMDCommonUtility.isCollectionNotEmpty(allUsers)) {
                                final Query updateUserNewsHqry = session
                                        .createSQLQuery(userNewsUpdateQry.toString());
                                for (Object[] obj : allUsers){
                                    if (RMDCommonUtility.convertObjectToString(obj[0]) != null
                                            && !RMDCommonUtility.convertObjectToString(obj[0])
                                                    .trim().equals(RMDCommonConstants.EMPTY_STRING)) {
                                        updateUserNewsHqry.setParameter(RMDCommonConstants.LINK_USERS, RMDCommonUtility
                                                .convertObjectToString(obj[0]));
                                    }
                                    updateUserNewsHqry.setParameter(RMDCommonConstants.OBJ_ID,sequenceId);
                                    updateUserNewsHqry.setParameter(RMDCommonConstants.READ_NEWS_FLAG,RMDCommonConstants.N_LETTER_UPPER);
                                    updateUserNewsHqry.setParameter(RMDCommonConstants.ACTIVE_NEWS_FLAG,RMDCommonConstants.Y_LETTER_UPPER);
                                    updateUserNewsHqry.executeUpdate();
                                }
                            }
    				
    			}
    			   
    	            
            }else{
                Query newsUpdateHQry =null;
                if(RMDCommonConstants.LETTER_Y.equalsIgnoreCase(saveNewsVO.getFileRemoved()) || null!=saveNewsVO.getObjDocDetails()){
                    newsUpdateHQry = session
                            .createSQLQuery(newsUpdateQry.toString());       
                    newsUpdateHQry.setParameter("attachment",docPath);
                    newsUpdateHQry.setParameter("attachName",docTitle);                  
                }else{
                    newsUpdateHQry = session
                            .createSQLQuery(newsUpdateQry2.toString());
                }
                newsUpdateHQry.setParameter("newsDesc", EsapiUtil.escapeSpecialChars(saveNewsVO.getNewsDesc()));
                newsUpdateHQry.setParameter("userId",saveNewsVO.getUserId() );
                newsUpdateHQry.setParameter("expiryDate",saveNewsVO.getExpiryDate());
                newsUpdateHQry.setParameter("status",saveNewsVO.getStatus());
                newsUpdateHQry.setParameter("objId",saveNewsVO.getObjId());
                
                newsUpdateHQry.executeUpdate();
               final Query userNewsUpdateHqry = session
                .createSQLQuery(userNewsUpdateQry2.toString());
               userNewsUpdateHqry.setParameter(RMDCommonConstants.Y_LETTER_UPPER, saveNewsVO.getStatus());
               userNewsUpdateHqry.setParameter(RMDCommonConstants.N_LETTER_UPPER, RMDCommonConstants.N_LETTER_UPPER);
               userNewsUpdateHqry.setParameter(RMDCommonConstants.OBJ_ID, saveNewsVO.getObjId());
               userNewsUpdateHqry.executeUpdate();
            }
         

            transaction.commit();
		}catch (RMDDAOConnectionException ex) {
		    if(null!=transaction){
                transaction.rollback(); 
            }   
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), ex,
						RMDCommonConstants.FATAL_ERROR);
			} catch (Exception e) {
			    if(null!=transaction){
	                transaction.rollback(); 
	            }   
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.MAJOR_ERROR);
			} finally {
				releaseSession(session);
			}
		LOG.debug("reached DAO @@@@@@@@@@@@@@@@@@@@@@@");
		return "SUCCESS";
	}

	@SuppressWarnings("null")
	@Override
	public List<AllNewsVO> getAllNews(String userId,String isAdminPage,String customerId) throws RMDDAOException {
		
		Session session = null;
		List<AllNewsVO> objVOlist = null;
		List<Object[]> resultList = null; 
		AllNewsVO singleVO = new AllNewsVO();
		StringBuilder queryStringBuilder = new StringBuilder();
		try{
			session = getHibernateSession(); 
			if(RMDCommonConstants.Y_LETTER_UPPER.equals(isAdminPage)){
				queryStringBuilder.append("select ab.objid,b.name,ab.description,ab.attachment_path,ab.status,ab.created_by,to_char(ab.Expiry_DATE, 'MM/DD/YYYY HH24:MI:SS') as expiry_time,to_char(ab.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS') as CREATION_DATE,ab.ATTACHMENT_NAME,ab.UPDATED_BY,to_char(ab.UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS') as update_date  from  GETS_RMD.GETS_RMD_CUSTOMER_NEWS  ab,TABLE_BUS_ORG  b where ab.Customer_ID = b.ORG_ID ");
				queryStringBuilder.append("order by UPDATED_DATE desc");
			}
			else
			{
			//String queryString = "select ab.objid,b.name,ab.description,ab.attachment_path,ab.status,ab.created_by,to_char(ab.Expiry_DATE, 'MM/DD/YYYY HH24:MI:SS') as expiry_time,to_char(ab.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS') as CREATION_DATE,ab.ATTACHMENT_NAME   from  GETS_RMD.GETS_RMD_CUSTOMER_NEWS  ab,TABLE_BUS_ORG  b where ab.Customer_ID = b.ORG_ID"; // where
			queryStringBuilder.append("SELECT ab.objid as customerNewsObjId,tbo.name,ab.description,ab.attachment_path,ab.status,ab.created_by,TO_CHAR(ab.Expiry_DATE, 'MM/DD/YYYY HH24:MI:SS')   AS expiry_time, ");
			queryStringBuilder.append("TO_CHAR(ab.CREATION_DATE, 'MM/DD/YYYY HH24:MI:SS') AS CREATION_DATE,ab.ATTACHMENT_NAME,news.READ_FLAG,news.OBJID as  newsObjId,to_char(news.UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS') as UPDATED_DATE,ab.ATTACHMENT_NAME,ab.UPDATED_BY ");
			queryStringBuilder.append("FROM GETS_RMD.GETS_RMD_CUSTOMER_NEWS ab,GETS_RMD.GETS_RMD_USER_NEWS news,TABLE_BUS_ORG tbo,GET_USR.GET_USR_USERS usr ");
			queryStringBuilder.append("where ab.OBJID = news.CUSTOMER_NEWS_ID AND ab.Customer_ID = tbo.ORG_ID AND usr.GET_USR_USERS_SEQ_ID = news.LINK_USERS AND news.ACTIVE_FLAG = 'Y' AND ab.STATUS='Y' ");
			if(!RMDCommonUtility.isNullOrEmpty(userId)){
				queryStringBuilder.append("AND usr.USER_ID=:userID");
			}
			queryStringBuilder.append(" AND ab.Customer_ID=:customerId order by news.UPDATED_DATE desc");
			}
			Query query = session.createSQLQuery(queryStringBuilder.toString());
            if (!RMDCommonConstants.Y_LETTER_UPPER.equals(isAdminPage)) {
                query.setParameter(RMDCommonConstants.USER_ID, userId);
                if (!RMDCommonUtility.isNullOrEmpty(customerId)) {
                    query.setParameter(RMDCommonConstants.CUSTOMER_ID,
                            customerId);
                } else {
                    query.setParameter(RMDCommonConstants.CUSTOMER_ID, "GETS");
                }
            }
			
			query.setFetchSize(1000);
			resultList = query.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				objVOlist = new ArrayList<AllNewsVO>(resultList.size());
				for (Object[] objects : resultList) {
					singleVO =  new AllNewsVO();
					singleVO.setObjid(RMDCommonUtility.convertObjectToString(objects[0]));
					singleVO.setCustomerId(RMDCommonUtility.convertObjectToString(objects[1]));
					singleVO.setDescription(ESAPI.encoder().encodeForXML(EsapiUtil.resumeSpecialChars(RMDCommonUtility.convertObjectToString(objects[2]))));
					singleVO.setAttachmentPath(RMDCommonUtility.convertObjectToString(objects[3]));
					if(RMDCommonUtility.convertObjectToString(objects[4]).equalsIgnoreCase(RMDCommonConstants.LETTER_Y))
					    singleVO.setStatus(RMDCommonConstants.LETTER_Y);
					else
					    singleVO.setStatus(RMDCommonConstants.LETTER_N);
					singleVO.setCreatedBy(RMDCommonUtility.convertObjectToString(objects[5]));
					singleVO.setExpiryDate(RMDCommonUtility.convertObjectToString(objects[6]));
					singleVO.setCreationDate(RMDCommonUtility.convertObjectToString(objects[7]));
					singleVO.setAttName(RMDCommonUtility.convertObjectToString(objects[8]));
					if(!RMDCommonConstants.Y_LETTER_UPPER.equals(isAdminPage)){
					singleVO.setReadNewsFlag(RMDCommonUtility.convertObjectToString(objects[9]));
					singleVO.setNewsObjId(RMDCommonUtility.convertObjectToString(objects[10]));
					singleVO.setLastUpdatedDate(RMDCommonUtility.convertObjectToString(objects[11]));
					}
					else
					{
					singleVO.setLastUpdatedBy(RMDCommonUtility.convertObjectToString(objects[9]));
					singleVO.setLastUpdatedDate(RMDCommonUtility.convertObjectToString(objects[10]));
					}
					
					objVOlist.add(singleVO);
				}
				
			}

		}catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		}catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		
		}
		 finally {
				releaseSession(session);
			}
		return objVOlist;
	}

	@Override
	public String deleteNews(String objId) throws RMDServiceException {
		Session session = null;
		String response = RMDCommonConstants.SUCCESS;
		Transaction transaction = null;
		try {
			session = getHibernateSession();
			transaction=session.beginTransaction();

				String queryString = "UPDATE GETS_RMD.GETS_RMD_USER_NEWS set ACTIVE_FLAG=:statusValue where objid=:objid";
				final Query newsQuery = session
						.createSQLQuery(queryString);
				newsQuery.setParameter("statusValue",'N');
				newsQuery.setParameter("objid",objId);
				newsQuery.executeUpdate();
				transaction.commit();

		}catch (RMDDAOConnectionException ex) {
			 if(null!=transaction){
	                transaction.rollback(); 
	            }
				response = RMDCommonConstants.FAILURE;
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), ex,
						RMDCommonConstants.FATAL_ERROR);
			} catch (Exception e) {
				 if(null!=transaction){
		                transaction.rollback(); 
		            }
				response = RMDCommonConstants.FAILURE;
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.MAJOR_ERROR);
			} finally {
				releaseSession(session);
			}
		
		return response;
	}
	
	/**
     * @Author:
     * @param:
     * @return: String
     * @throws:Exception
     * @Description: This method is used for uploading multiple attachments.
     */
    public String uploadNewsAttachment(String fileData, String fileName,String contentType)
            throws Exception {
        String filePath = null;
        File file = null;       
        try {   
            File dir = new File(delvRxPDFPath + File.separator
                    + "NewsAttachments" + File.separator + contentType);
        if (!dir.exists())
                dir.mkdirs();
            file = new File(dir.getAbsolutePath()
                    + File.separator + fileName);
            
            byte[] bDecodedArr = Base64.decode(fileData.getBytes());
            FileUtils.writeByteArrayToFile(file, bDecodedArr);
            filePath = pdfURL+File.separator+"NewsAttachments"+ File.separator + contentType+ File.separator + fileName;
            
        } catch (IOException ioexception) {
            LOG.error(
                    "Unable to write the file exception occured in uploadNewsAttachment() method - SaveNewsDAOImpl",
                    ioexception);
        }
        catch (Exception exception) {
            LOG.error(
                    "RMDWebException occurred while creating file uploadNewsAttachment() method - SaveNewsDAOImpl",
                    exception);
        }
        return filePath;
    }
    @Override
	public String getBackgroundImage(String customerId) throws RMDDAOException {
		
		Session session = null;
		List<AllNewsVO> objVOlist = null;
		String resultList = null;
		AllNewsVO singleVO = new AllNewsVO();
		try{
			session = getHibernateSession(); 
			String queryString = "select BG_IMG_PATH from GETS_RMD.CUSTOMER_NEWS_BG_IMG where CUSTUMER_ID=:customerId"; // where
			Query query = session.createSQLQuery(queryString);
			if(!RMDCommonUtility.isNullOrEmpty(customerId)){
				query.setParameter(RMDCommonConstants.CUSTOMER_ID, customerId);
			}
			query.setFetchSize(1000);
			resultList = RMDCommonUtility.convertObjectToString(query.uniqueResult());

		}catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BACKGROUND_IMAGE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		}catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_BACKGROUND_IMAGE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		
		}
		 finally {
				releaseSession(session);
			}
		return resultList;
	}
    @Override
	public String updateNewsFlag(String objId) throws RMDServiceException {
		Session session = null;
		String response = RMDCommonConstants.SUCCESS;
		Transaction transaction = null;
		try {
			session = getHibernateSession();
			transaction=session.beginTransaction();

				String queryString = "UPDATE GETS_RMD.GETS_RMD_USER_NEWS set READ_FLAG=:statusValue where objid=:objid";
				final Query newsQuery = session
						.createSQLQuery(queryString);
				newsQuery.setParameter("statusValue",'Y');
				newsQuery.setParameter("objid",objId);
				newsQuery.executeUpdate();
				transaction.commit();

		}catch (RMDDAOConnectionException ex) {
			 if(null!=transaction){
	                transaction.rollback(); 
	            }
				response = RMDCommonConstants.FAILURE;
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), ex,
						RMDCommonConstants.FATAL_ERROR);
			} catch (Exception e) {
				 if(null!=transaction){
		                transaction.rollback(); 
		            }
				response = RMDCommonConstants.FAILURE;
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_NEWS);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), e,
						RMDCommonConstants.MAJOR_ERROR);
			} finally {
				releaseSession(session);
			}
		
		return response;
	}
}
