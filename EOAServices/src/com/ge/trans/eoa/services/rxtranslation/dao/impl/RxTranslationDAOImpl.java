package com.ge.trans.eoa.services.rxtranslation.dao.impl;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Value;

import com.ge.trans.eoa.common.util.AliasToEntityMapResultTransformerUtil;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.eoa.services.rxtranslation.dao.intf.RxTranslationDAOIntf;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualFilterVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.MultiLingualRxVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransDetailVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransHistVO;
import com.ge.trans.eoa.services.rxtranslation.service.valueobjects.RxTransTaskDetailVO;
import com.ge.trans.eoa.services.tools.rx.dao.impl.AddEditRxDAOImpl;
import com.ge.trans.eoa.services.tools.rx.service.valueobjects.AddEditRxTaskDocVO;
import com.ge.trans.rmd.caseapi.common.util.MultilingualUtil;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.esapi.util.EsapiUtil;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.tools.common.utils.RxPdfGenerator;
import com.ge.trans.rmd.tools.common.valueobjects.RxTaskVO;
import com.ge.trans.rmd.utilities.AppSecUtil;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.sun.jersey.core.util.Base64;

public class RxTranslationDAOImpl extends BaseDAO implements RxTranslationDAOIntf {


	private static final long serialVersionUID = 1L;
	public static final RMDLogger LOG = RMDLogger
			.getLogger(RxTranslationDAOImpl.class);
	@Value("${RX_DATA_UPLOAD_LOC}")
	private String rxDataUrl;

	@Value("${RX_DATA_DB_PATH}")
	private String rxDataDBURL;

	@Value("${RX_DOMAIN_PATH}")
	private String domainPath;
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.rxtranslation.dao.intf.RxTranslationDAOIntf#getTranslationLanguage()
	 */
	@Override
	public List<String> getTranslationLanguage() throws RMDDAOException {
		Session objHibernateSession = null;
		List<String> languageList = new ArrayList<String>();
		try{
			objHibernateSession = getHibernateSession();
			String queryStr = "SELECT LANG_TYPE from GETS_SD_LANG_TYPE ORDER BY 1";
			Query languageQuery = objHibernateSession.createSQLQuery(queryStr);
			//languageQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			languageQuery.setResultTransformer(AliasToEntityMapResultTransformerUtil.getInstance());			
			List<Map<String, Object>> resultList = languageQuery.list();
			
			if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
				for(Map<String, Object> result : resultList){
					languageList.add(RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE")));
				}				
			}
			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_LANGUAGE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_LANGUAGE);
		} finally {
			releaseSession(objHibernateSession);
		}
		return languageList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.rxtranslation.dao.intf.RxTranslationDAOIntf#getTranslationLastModifiedBy()
	 */
	@Override
	public List<String> getTranslationLastModifiedBy() throws RMDDAOException {
		Session objHibernateSession = null;
		List<String> lastModifiedByList = new ArrayList<String>();
		try{
			objHibernateSession = getHibernateSession();
			String queryStr = "SELECT DISTINCT LAST_UPDATED_BY FROM GETS_SD_RECOM WHERE ((RECOM_STATUS = 'Approved' AND RECOM_TYPE = 'Standard') OR RECOM_TYPE = 'Custom') AND LAST_UPDATED_DATE BETWEEN SYSDATE-30 AND SYSDATE ORDER BY 1";
			Query lastModByQuery = objHibernateSession.createSQLQuery(queryStr);
			//lastModByQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			lastModByQuery.setResultTransformer(AliasToEntityMapResultTransformerUtil.getInstance());
			List<Map<String, Object>> resultList = lastModByQuery.list();
			if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
				for(Map<String, Object> result : resultList){
					lastModifiedByList.add(RMDCommonUtility.convertObjectToString(result.get("LAST_UPDATED_BY")));
				}				
			}			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY);
		} finally {
			releaseSession(objHibernateSession);
		}
		return lastModifiedByList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.rxtranslation.dao.intf.RxTranslationDAOIntf#getFilteredRx()
	 */
	@Override
	public List<MultiLingualRxVO> getTranslationRxList(MultiLingualFilterVO multiLingualFilter) throws RMDDAOException {
		Session objHibernateSession = null;
		List<MultiLingualRxVO> rxTranslationList = new ArrayList<MultiLingualRxVO>();
		SimpleDateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
		try{
			objHibernateSession = getHibernateSession(); 
			StringBuffer query = new StringBuffer();
			
			query.append("select  * from ");
			query.append("(SELECT R.OBJID,R.TITLE,R.RECOM_TYPE,R.RECOM_STATUS, C.LANG_TYPE, COMMENT_SEQUENCE_NBR,dense_rank() over (partition by RECOM_OBJID,LANG_TYPE order by COMMENT_SEQUENCE_NBR desc) as RANKNUM, ");
			query.append("  M.MODEL_NAME, C.TRANSLATION_STATUS, R.LAST_UPDATED_BY, TO_CHAR(R.LAST_UPDATED_DATE, 'MM/DD/YYYY HH24:MI:SS') AS LAST_UPDATED_DATE, R.APPROVED_BY, TO_CHAR(R.APPROVAL_DATE, 'MM/DD/YYYY HH24:MI:SS') AS APPROVAL_DATE ");
			query.append("FROM GETS_SD_RECOM R,GETS_SD_RECOM_MTM_MODEL MTM, GETS_RMD_MODEL M, ");
			if(!multiLingualFilter.getLanguage().isEmpty()){ //4. Lang type filter
				query.append("(select * from GETS_SD_ML_RECOM_COMMENT where LANG_TYPE in (:languages)) C ");
				query.append("WHERE R.OBJID =MTM.RECOM_MODEL2RECOM and MTM.RECOM_MODEL2MODEL=M.OBJID and C.RECOM_OBJID = R.OBJID ");
			} else {
				query.append("(select * from GETS_SD_ML_RECOM_COMMENT where LANG_TYPE in (select distinct LANG_TYPE from GETS_SD_LANG_TYPE)) C ");
				query.append("WHERE R.OBJID =MTM.RECOM_MODEL2RECOM and MTM.RECOM_MODEL2MODEL=M.OBJID and C.RECOM_OBJID(+) = R.OBJID ");
			}
			query.append(" AND R.RECOM_STATUS !='Deactive' AND (R.RECOM_STATUS !='In Process' OR R.RECOM_TYPE!='Standard') ");
			if(multiLingualFilter.getTitle() != null && !"".equalsIgnoreCase(multiLingualFilter.getTitle()))//1. Title filter
				query.append(" and title like :title ");
			if(multiLingualFilter.getModel() != null && !"".equalsIgnoreCase(multiLingualFilter.getModel()))//2. Model filter
				query.append("and model_name in (:model) ");
			if(multiLingualFilter.getType() != null && !"".equalsIgnoreCase(multiLingualFilter.getType())) //3. Rx Type filter
				query.append("and RECOM_TYPE  = :type ");
			
			if (multiLingualFilter.getLastModifiedBy() != null && !"".equalsIgnoreCase(multiLingualFilter.getLastModifiedBy())) //6. last updated by filter
				query.append(" and R.LAST_UPDATED_BY = :lastModifiedBy ");
			if (multiLingualFilter.getLastModifiedOn() != null
					&& !"".equalsIgnoreCase(multiLingualFilter
							.getLastModifiedOn().toString()) && multiLingualFilter.getLastModifiedOnTo() != null
									&& !"".equalsIgnoreCase(multiLingualFilter
											.getLastModifiedOnTo().toString())) {//7.Last updated on filter
				query.append("and R.LAST_UPDATED_DATE between To_date(:lastUpdatedDate, 'MM/DD/YYYY HH24:MI:SS')"
						+ " AND To_date(:lastUpdatedDateTo, 'MM/DD/YYYY HH24:MI:SS')");			
			} else {
				if(multiLingualFilter.isDefaultLoad())
					query.append(" and R.LAST_UPDATED_DATE > sysdate-30  ");
			}
			query.append(") where RANKNUM=1 ");
			if(multiLingualFilter.getTranslationStatus() != null && !"".equalsIgnoreCase(multiLingualFilter.getTranslationStatus())) //5. translation status filter
				query.append(" and TRANSLATION_STATUS in (:translationStatus) ");
			query.append(" order by LAST_UPDATED_DATE DESC ,OBJID,LANG_TYPE ");
			Query rxListQuery = objHibernateSession.createSQLQuery(query.toString());
			if(!multiLingualFilter.getLanguage().isEmpty()){
				rxListQuery.setParameterList("languages", multiLingualFilter.getLanguage());
			} 
			if(multiLingualFilter.getLastModifiedBy() != null && !"".equalsIgnoreCase(multiLingualFilter.getLastModifiedBy()))
				rxListQuery.setParameter("lastModifiedBy", multiLingualFilter.getLastModifiedBy());
			
			if(multiLingualFilter.getModel() != null && !"".equalsIgnoreCase(multiLingualFilter.getModel()))
				rxListQuery.setParameter("model", multiLingualFilter.getModel());
			if(multiLingualFilter.getTitle() != null && !"".equalsIgnoreCase(multiLingualFilter.getTitle()))
				rxListQuery.setParameter("title", "%"+multiLingualFilter.getTitle()+"%");
			if(multiLingualFilter.getType() != null && !"".equalsIgnoreCase(multiLingualFilter.getType()))
				rxListQuery.setParameter("type", multiLingualFilter.getType());
			if(multiLingualFilter.getLastModifiedOn() != null && !"".equalsIgnoreCase(multiLingualFilter.getLastModifiedOn().toString()) && multiLingualFilter.getLastModifiedOnTo() != null && !"".equalsIgnoreCase(multiLingualFilter.getLastModifiedOnTo().toString())){
				rxListQuery.setParameter("lastUpdatedDate", formatter.format(multiLingualFilter.getLastModifiedOn()));
				rxListQuery.setParameter("lastUpdatedDateTo", formatter.format(multiLingualFilter.getLastModifiedOnTo()));
			}
			if(multiLingualFilter.getTranslationStatus() != null && !"".equalsIgnoreCase(multiLingualFilter.getTranslationStatus().toString())){
				rxListQuery.setParameter("translationStatus", multiLingualFilter.getTranslationStatus());
			}
			rxListQuery.setResultTransformer(new AliasToEntityMapResultTransformer());
			List<Map<String, Object>> resultList = rxListQuery.list();
			MultiLingualRxVO rxTranslationDetail = null;
			if(RMDCommonUtility.isCollectionNotEmpty(resultList)){
				String prevObjId = null;
				String prevLanguage = null;	
				boolean isAdded = true;
				for(Map<String, Object> result : resultList){
					String currObjId = RMDCommonUtility.convertObjectToString(result.get("OBJID"));
					String currLanguage = RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE"));
					if(currObjId !=null && currObjId.equalsIgnoreCase(prevObjId)){
						if(currLanguage !=null && currLanguage.equalsIgnoreCase(prevLanguage)){
							rxTranslationDetail.loadModels(RMDCommonUtility.convertObjectToString(result.get("MODEL_NAME")));
						} else {
							if(RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE")) != null)
								rxTranslationDetail.loadTranslationStatus(RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE")), 
										RMDCommonUtility.convertObjectToString(result.get("TRANSLATION_STATUS")));
						}
					} else {
						if(rxTranslationDetail != null){
							rxTranslationList.add(rxTranslationDetail);
							isAdded = true;
						}
						rxTranslationDetail = new MultiLingualRxVO();
						isAdded = false;
						rxTranslationDetail.setRxObjid(Long.valueOf(currObjId));
						
						rxTranslationDetail.setTitle(AppSecUtil
								.stripNonValidXMLCharactersForKM(AppSecUtil
										.decodeString(RMDCommonUtility.convertObjectToString(result.get("TITLE")))));
						rxTranslationDetail.setType(RMDCommonUtility.convertObjectToString(result.get("RECOM_TYPE")));
						rxTranslationDetail.setRxStatus(RMDCommonUtility.convertObjectToString(result.get("RECOM_STATUS")));
						rxTranslationDetail.setLastModifiedBy(RMDCommonUtility.convertObjectToString(result.get("LAST_UPDATED_BY")));
						String modifiedDate = RMDCommonUtility.convertObjectToString(result.get("LAST_UPDATED_DATE"));
						Date modifiedOn = null;
						if(modifiedDate != null){
							modifiedOn = formatter.parse(modifiedDate);
						}
						rxTranslationDetail.setLastModifiedOn(modifiedOn);
						rxTranslationDetail.setApprovedBy(RMDCommonUtility.convertObjectToString(result.get("APPROVED_BY")));
						String approvedDate = RMDCommonUtility.convertObjectToString(result.get("APPROVAL_DATE"));
						Date approvedOn = null;
						if(approvedDate != null){
							approvedOn = formatter.parse(approvedDate);
						}
						rxTranslationDetail.setApprovedOn(approvedOn);
						rxTranslationDetail.loadModels(RMDCommonUtility.convertObjectToString(result.get("MODEL_NAME")));
						if(RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE")) != null)
							rxTranslationDetail.loadTranslationStatus(RMDCommonUtility.convertObjectToString(result.get("LANG_TYPE")),
									RMDCommonUtility.convertObjectToString(result.get("TRANSLATION_STATUS")));
					}
					prevObjId = currObjId;
					prevLanguage = currLanguage;
				}
				if(!isAdded){
					if(rxTranslationDetail != null)
						rxTranslationList.add(rxTranslationDetail);
				}
					
			}			
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RMDDAOException(
					RMDServiceConstants.DAO_EXCEPTION_GET_TRANSLATE_UPDATED_BY);
		} finally {
			releaseSession(objHibernateSession);
		}
		return rxTranslationList;
	}

	 @Override
	    public RxTransDetailVO getRxTransDetail(String rxObjid,String strLanguage) {
	        Session hibernateSession = null;
	        RxTransDetailVO objRxTransDetailVO=null;
	        Query strRxTransQuery = null;
	        List<RxTransHistVO> arlTransHistVO=null;
	        List<RxTransTaskDetailVO> arlRxTransTaskDetailVO=null;
	        MultilingualUtil objMultilingualUtil=new MultilingualUtil();
	        try {
	            hibernateSession = getHibernateSession();
	            StringBuilder strRxTrans = new StringBuilder();
	            strRxTrans.append("SELECT OBJID, TITLE,TRANSLATION_STATUS, RECOM_STATUS,LAST_UPDATE_DATE,LAST_UPDATED_BY,APPROVED_BY,APPROVED_DATE,TRANS_RECOM_TITLE_TXT,URGENCY, EST_REPAIR_TIME,LOCO_IMPACT,DESCRIPTION,TRANS_RECOM_DESCRIPTION  ");
	            strRxTrans.append("FROM (SELECT DISTINCT RECOM.OBJID AS OBJID,RECOM.TITLE AS TITLE,RECOMM_COMMENT.TRANSLATION_STATUS  AS TRANSLATION_STATUS,RECOM_STATUS AS RECOM_STATUS, TO_CHAR(RECOMM_COMMENT.LAST_UPDATE_DATE, 'MM/DD/YYYY HH24:MI:SS')  AS LAST_UPDATE_DATE,RECOMM_COMMENT.LAST_UPDATED_BY AS LAST_UPDATED_BY, RECOMM_COMMENT.APPROVED_BY AS APPROVED_BY, TO_CHAR(RECOMM_COMMENT.APPROVED_DATE, 'MM/DD/YYYY HH24:MI:SS') AS APPROVED_DATE, TRANS_RECOM_TITLE_TXT AS TRANS_RECOM_TITLE_TXT,RECOM.URGENCY AS URGENCY, RECOM.EST_REPAIR_TIME AS EST_REPAIR_TIME,RECOM.LOCO_IMPACT AS LOCO_IMPACT, COMMENT_SEQUENCE_NBR, RECOM.DESCRIPTION AS DESCRIPTION,RECOMUT8.TRANS_RECOM_DESCRIPTION AS TRANS_RECOM_DESCRIPTION ");
	            strRxTrans.append(" FROM GETS_SD.GETS_SD_RECOM RECOM,GETS_SD_ML_RECOM_COMMENT RECOMM_COMMENT,GETS_SD.GETS_SD_RECOM_UT8 RECOMUT8");
	            strRxTrans.append(" WHERE RECOMUT8.LINK_RECOM (+)= RECOM.OBJID AND RECOM.OBJID=RECOMM_COMMENT.RECOM_OBJID(+) AND  RECOM.OBJID=:objId AND LANGUAGE_CD (+)=:language AND upper(LANG_TYPE(+))=upper(:language) ORDER BY COMMENT_SEQUENCE_NBR desc ) where rownum = 1");
	            strRxTransQuery = hibernateSession.createSQLQuery(strRxTrans.toString());
	            strRxTransQuery.setParameter(RMDCommonConstants.OBJ_ID,rxObjid);
	            strRxTransQuery.setParameter(RMDCommonConstants.LANGUAGE,strLanguage);
	            List<Object[]> rxTransList = strRxTransQuery.list();
	            if (rxTransList != null && !rxTransList.isEmpty()) {
	                for (Iterator<Object[]> iter = rxTransList.iterator(); iter.hasNext();) {
	                	final Object[] rxTransDetails = (Object[]) iter.next();
	                	objRxTransDetailVO=new RxTransDetailVO();
	                	objRxTransDetailVO.setRxObjid(RMDCommonUtility.convertObjectToString(rxTransDetails[0]));
	                	objRxTransDetailVO.setRxTitle(AppSecUtil
								.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[1])));
	                	//objRxTransDetailVO.setRxTitle(EsapiUtil.stripXSSCharacters(EsapiUtil.escapeSpecialChars(RMDCommonUtility.convertObjectToString(rxTransDetails[1]))));

	                	objRxTransDetailVO.setTransStatus(RMDCommonUtility.convertObjectToString(rxTransDetails[2]));
	                	objRxTransDetailVO.setRxStatus(RMDCommonUtility.convertObjectToString(rxTransDetails[3]));   
						objRxTransDetailVO.setLastModifiedOn(RMDCommonUtility.convertObjectToString(rxTransDetails[4]));
	                	objRxTransDetailVO.setLastModifiedBy(RMDCommonUtility.convertObjectToString(rxTransDetails[5]));
	                	objRxTransDetailVO.setApprovedBy(RMDCommonUtility.convertObjectToString(rxTransDetails[6]));
						objRxTransDetailVO.setApprovedOn(RMDCommonUtility.convertObjectToString(rxTransDetails[7]));
						if(strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
    							|| strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
							objRxTransDetailVO.setRxTransTitle(objMultilingualUtil.convertToUTF8(AppSecUtil
									.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[8]))));
    					}else{
    						objRxTransDetailVO.setRxTransTitle(AppSecUtil
    								.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[8])));
    					}
	                	
	                	objRxTransDetailVO.setStrUrgRepair(RMDCommonUtility.convertObjectToString(rxTransDetails[9]));
	                	objRxTransDetailVO.setStrEstmTimeRepair(RMDCommonUtility.convertObjectToString(rxTransDetails[10]));
	                	objRxTransDetailVO.setStrSelAssetImp(RMDCommonUtility.convertObjectToString(rxTransDetails[11]));
	                	objRxTransDetailVO.setRxDescription(AppSecUtil
								.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[12])));
	                	if(strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
    							|| strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
							objRxTransDetailVO.setTransDescription(objMultilingualUtil.convertToUTF8(AppSecUtil
									.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[13]))));
    					}else{
    						objRxTransDetailVO.setTransDescription(AppSecUtil
    								.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[13])));
    					}
	                	objRxTransDetailVO.setLanguage(strLanguage);
	                }
	                
	                arlTransHistVO=getRxTransHistory(rxObjid,strLanguage);
	                objRxTransDetailVO.setArlRxTransHistVO(arlTransHistVO);
	                arlRxTransTaskDetailVO=getRxTransTasks(rxObjid, strLanguage);
	                objRxTransDetailVO.setArlRxTransTaskDetailVO(arlRxTransTaskDetailVO);
	                
	            }

	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_DETAILS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_DETAILS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        } finally {
	            releaseSession(hibernateSession);
	        }
	        return objRxTransDetailVO;
	    }
	    
	    
	    
	    
	    
	    private List<RxTransHistVO> getRxTransHistory(String rxObjid,String strLanguage) {
	        Session hibernateSession = null;
	        List<RxTransHistVO> arlRxTransHistVO=null;
	        Query strRxTransHisQuery = null;
	        try {
	            hibernateSession = getHibernateSession();
	            StringBuilder strRxTransHis = new StringBuilder();
	            strRxTransHis.append("SELECT RECOMM_COMMENT.RECOM_OBJID,RECOMM_COMMENT.TRANSLATION_STATUS,TO_CHAR(RECOMM_COMMENT.LAST_UPDATE_DATE, 'MM/DD/YYYY HH24:MI:SS'),RECOMM_COMMENT.LAST_UPDATED_BY,RECOMM_COMMENT.APPROVED_BY,RECOMM_COMMENT.APPROVED_DATE,REVISION_NOTES");
	            strRxTransHis.append(" FROM GETS_SD_ML_RECOM_COMMENT RECOMM_COMMENT");
	            strRxTransHis.append(" WHERE RECOMM_COMMENT.RECOM_OBJID=:objId AND upper(LANG_TYPE)=upper(:language) AND REVISION_NOTES IS NOT NULL ORDER BY RECOMM_COMMENT.LAST_UPDATE_DATE DESC");
	            strRxTransHisQuery = hibernateSession.createSQLQuery(strRxTransHis.toString());
	            strRxTransHisQuery.setParameter(RMDCommonConstants.OBJ_ID,rxObjid);
	            strRxTransHisQuery.setParameter(RMDCommonConstants.LANGUAGE,strLanguage);
	            List<Object[]> rxTransHisList = strRxTransHisQuery.list();

	            if (rxTransHisList != null && !rxTransHisList.isEmpty()) {
	            	arlRxTransHistVO=new ArrayList<RxTransHistVO>();
	                for (Iterator<Object[]> iter = rxTransHisList.iterator(); iter.hasNext();) {
	                	final Object[] rxTransDetails = (Object[]) iter.next();
	                	RxTransHistVO objHistVO=new RxTransHistVO(RMDCommonUtility.convertObjectToString(rxTransDetails[2]),RMDCommonUtility.convertObjectToString(rxTransDetails[3]),AppSecUtil.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransDetails[6])));
	                	arlRxTransHistVO.add(objHistVO);
	                	
	                }
	            }

	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_HIS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_HIS);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        } finally {
	            releaseSession(hibernateSession);
	        }
	        return arlRxTransHistVO;
	    }
	    
	    
	    private List<RxTransTaskDetailVO> getRxTransTasks(String rxObjid,String strLanguage) {
	        Session hibernateSession = null;
	        List<RxTransTaskDetailVO> arlRxTransTaskDetailVO=null;
	        Query strRxTransHisQuery = null;
	        String rxTransTaskDesc=RMDCommonConstants.EMPTY_STRING;
	        try {
	            hibernateSession = getHibernateSession();
	            StringBuilder strRxTransHis = new StringBuilder();
	            strRxTransHis.append("SELECT TASK.OBJID,TASK_ID,TASK_DESC,TRANS_TASK_DESC, LOWER_SPEC lower_spec, UPPER_SPEC upper_spec,TARGET target ");
	            strRxTransHis.append(" FROM GETS_SD_RECOM_TASK TASK, GETS_SD_RECOM RECOM, GETS_SD_RECOM_TASK_UTF8 UTF8,(SELECT");
	            strRxTransHis.append(" GSLT.OBJID FROM GETS_SD_LANG_TYPE GSLT WHERE GSLT.LANG_TYPE =:language) LANG_LOOKUP");
	            strRxTransHis.append(" WHERE RECOM.OBJID=:objId AND RECOM.OBJID  =TASK.RECOM_TASK2RECOM AND UTF8.LINK_RECOM_TASK(+) =TASK.OBJID AND UTF8.LINK_LANG_TYPE (+) =LANG_LOOKUP.OBJID ORDER BY TASK.objid ASC");
	            strRxTransHisQuery = hibernateSession.createSQLQuery(strRxTransHis.toString());
	            strRxTransHisQuery.setParameter(RMDCommonConstants.OBJ_ID,rxObjid);
	            strRxTransHisQuery.setParameter(RMDCommonConstants.LANGUAGE,strLanguage);
	            List<Object[]> rxTransTaskList = strRxTransHisQuery.list();
	            MultilingualUtil objMultilingualUtil=new MultilingualUtil();
	            if (rxTransTaskList != null && !rxTransTaskList.isEmpty()) {
	            	arlRxTransTaskDetailVO=new ArrayList<RxTransTaskDetailVO>();
	                for (Iterator<Object[]> iter = rxTransTaskList.iterator(); iter.hasNext();) {
	                	final Object[] rxTransTask = (Object[]) iter.next();
	                	
	                	if(strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
    							|| strLanguage.equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
	                		rxTransTaskDesc=objMultilingualUtil.convertToUTF8(AppSecUtil.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransTask[3])));
    					}else{
    						rxTransTaskDesc=AppSecUtil.stripNonValidXMLCharactersForKM(RMDCommonUtility.convertObjectToString(rxTransTask[3]));
    					}
					RxTransTaskDetailVO objRxTransTaskDetailVO = new RxTransTaskDetailVO(
							RMDCommonUtility
									.convertObjectToString(rxTransTask[0]),
							RMDCommonUtility
									.convertObjectToString(rxTransTask[1]),
							RMDCommonUtility
									.convertObjectToString(rxTransTask[2]),
									rxTransTaskDesc);
	                	objRxTransTaskDetailVO.setLsl(RMDCommonUtility.convertObjectToString(rxTransTask[4]));
	                	objRxTransTaskDetailVO.setUsl(RMDCommonUtility.convertObjectToString(rxTransTask[5]));
	                	objRxTransTaskDetailVO.setTarget(RMDCommonUtility.convertObjectToString(rxTransTask[6]));
	                	StringBuilder rxTaskDocqueryBuilder = new StringBuilder();
						rxTaskDocqueryBuilder
								.append(" SELECT OBJID,DOC_TITLE,DELETED,DOC_PATH FROM GETS_SD.GETS_SD_RECOM_TASK_DOC");
						rxTaskDocqueryBuilder
						.append(" WHERE RECOM_DOC2RECOM_TASK=:recomTaskObjid AND DELETED = 0 AND LINK_LANG_TYPE IS NULL");
						Query nativeTaskDocQuery = hibernateSession
								.createSQLQuery(rxTaskDocqueryBuilder.toString());
						nativeTaskDocQuery.setParameter(
								RMDServiceConstants.RECOM_TASK_OBJID, RMDCommonUtility.convertObjectToString(rxTransTask[0]));
						List<Object[]> arlRecomTaskDoc = nativeTaskDocQuery.list();
						List<AddEditRxTaskDocVO> lstAddEditRxTaskDocVO = new ArrayList<AddEditRxTaskDocVO>();
						if (RMDCommonUtility.isCollectionNotEmpty(arlRecomTaskDoc)) {
							for (int icount = 0; icount < arlRecomTaskDoc.size(); icount++) {
								Object objRxTaskDoc[] = (Object[]) arlRecomTaskDoc
										.get(icount);
								AddEditRxTaskDocVO addEditRxTaskDocVO = new AddEditRxTaskDocVO();
								addEditRxTaskDocVO
										.setStrTaskDocSeqId(RMDCommonUtility
												.convertObjectToString(objRxTaskDoc[0]));
								addEditRxTaskDocVO.setStrDocTitle(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[1]));
								addEditRxTaskDocVO.setStrDelete(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[2]));
								addEditRxTaskDocVO.setStrDocPath(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[3]));
								lstAddEditRxTaskDocVO.add(addEditRxTaskDocVO);
							}
							objRxTransTaskDetailVO
									.setAddEditRxTaskDocVO(lstAddEditRxTaskDocVO);
						}
						
						
						
						
						
						
						
						StringBuilder rxTransTaskDocqueryBuilder = new StringBuilder();
						rxTransTaskDocqueryBuilder
								.append(" SELECT OBJID,DOC_TITLE,DELETED,DOC_PATH FROM GETS_SD.GETS_SD_RECOM_TASK_DOC");
						rxTransTaskDocqueryBuilder
						.append(" WHERE RECOM_DOC2RECOM_TASK=:recomTaskObjid AND DELETED = 0 AND LINK_LANG_TYPE=(Select gslt.OBJID from GETS_SD_LANG_TYPE gslt where gslt.LANG_TYPE =:language)");
						Query nativeTransTaskDocQuery = hibernateSession
								.createSQLQuery(rxTransTaskDocqueryBuilder.toString());
						nativeTransTaskDocQuery.setParameter(
								RMDServiceConstants.RECOM_TASK_OBJID, RMDCommonUtility.convertObjectToString(rxTransTask[0]));
						nativeTransTaskDocQuery.setParameter(
								RMDCommonConstants.LANGUAGE, strLanguage);
						List<Object[]> arlTransTaskDoc = nativeTransTaskDocQuery.list();
						List<AddEditRxTaskDocVO> lstTransTaskDocVO = new ArrayList<AddEditRxTaskDocVO>();
						if (RMDCommonUtility.isCollectionNotEmpty(arlTransTaskDoc)) {
							for (int icount = 0; icount < arlTransTaskDoc.size(); icount++) {
								Object objRxTaskDoc[] = (Object[]) arlTransTaskDoc
										.get(icount);
								AddEditRxTaskDocVO addEditRxTaskDocVO = new AddEditRxTaskDocVO();
								addEditRxTaskDocVO
										.setStrTaskDocSeqId(RMDCommonUtility
												.convertObjectToString(objRxTaskDoc[0]));
								addEditRxTaskDocVO.setStrDocTitle(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[1]));
								addEditRxTaskDocVO.setStrDelete(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[2]));
								addEditRxTaskDocVO.setStrDocPath(RMDCommonUtility
										.convertObjectToString(objRxTaskDoc[3]));
								lstTransTaskDocVO.add(addEditRxTaskDocVO);
							}
							objRxTransTaskDetailVO
									.setRxTransTaskDocVO(lstTransTaskDocVO);
						}
					arlRxTransTaskDetailVO.add(objRxTransTaskDetailVO);
	                	
	                }
	            }

	        } catch (RMDDAOConnectionException ex) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_TASK);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	                    RMDCommonConstants.FATAL_ERROR);
	        } catch (Exception e) {
	            String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_RX_TRANS_TASK);
	            throw new RMDDAOException(errorCode, new String[] {},
	                    RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
	                    RMDCommonConstants.MAJOR_ERROR);
	        } finally {
	            releaseSession(hibernateSession);
	        }
	        return arlRxTransTaskDetailVO;
	    }
	    
	    @Override
	    public String saveRxTranslation(RxTransDetailVO rxTransDetailVO){
	    	Session hibernateSession = null;
	    	boolean isUpdated = false;
	    	Connection con = null;
	    	try {
	    		hibernateSession = getHibernateSession();
	    		con = hibernateSession.connection();
	    		StringBuilder checkTranslation = new StringBuilder();
	    		checkTranslation.append("SELECT COUNT(1) FROM GETS_SD.GETS_SD_RECOM_UT8 WHERE LINK_RECOM=:recomId AND LANGUAGE_CD=:language");
	    		Query queryCheckTranslation = hibernateSession.createSQLQuery(checkTranslation.toString());
	    		queryCheckTranslation.setParameter("recomId", rxTransDetailVO.getRxObjid());
	    		queryCheckTranslation.setParameter("language", rxTransDetailVO.getLanguage());
	    		List<Object> recomList = queryCheckTranslation.list();
	    		int countOfRxTrans = 0;
	    		if(RMDCommonUtility.isCollectionNotEmpty(recomList)){
	    			countOfRxTrans = RMDCommonUtility.convertObjectToInt(recomList.get(0));
	    		}
	    		MultilingualUtil mlUtil = new MultilingualUtil();
	    		int result1 = 0;
	    		
	    		String title = AppSecUtil
						.stripNonValidXMLCharactersForKM(rxTransDetailVO.getRxTransTitle());
	    		if(rxTransDetailVO.getLanguage().equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||rxTransDetailVO.getLanguage().equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
						|| rxTransDetailVO.getLanguage().equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
					title =  mlUtil.convertToHexaUnicode(title);
				}
	    		
	    		String lang = rxTransDetailVO.getLanguage();
	    		String transDescription = null;
	    		if (lang.equalsIgnoreCase(RMDServiceConstants.LANG_FRENCH)
						|| lang.equalsIgnoreCase(RMDServiceConstants.LANG_SPANISH)||lang.equalsIgnoreCase(RMDServiceConstants.LANG_PORTUGUESE) || lang.equalsIgnoreCase(RMDServiceConstants.LANG_INDONESIA)) {
	    			transDescription=  AppSecUtil
							.stripNonValidXMLCharactersForKM(rxTransDetailVO.getTransDescription());
				}
				else if(lang.equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||lang.equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
						|| lang.equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
					transDescription = mlUtil.convertToHexaUnicode(AppSecUtil
							.stripNonValidXMLCharactersForKM(rxTransDetailVO.getTransDescription()));
				}
	    		if(countOfRxTrans > 0){
	    			StringBuilder updateRxTrans = new StringBuilder();
	    			updateRxTrans.append("UPDATE GETS_SD.GETS_SD_RECOM_UT8 SET TRANS_RECOM_TITLE_TXT =:title,LAST_UPDATED_BY=:updatedBy, ");
	    			updateRxTrans.append("LAST_UPDATED_DATE=SYSDATE,TRANS_RECOM_DESCRIPTION=:transDescription WHERE LINK_RECOM=:recomId AND LANGUAGE_CD =:language");
	    			Query queryUpdateRxTrans = hibernateSession.createSQLQuery(updateRxTrans.toString());
	    			queryUpdateRxTrans.setParameter("title", title);
	    			queryUpdateRxTrans.setParameter("updatedBy", rxTransDetailVO.getLastModifiedBy());
	    			queryUpdateRxTrans.setParameter("recomId", rxTransDetailVO.getRxObjid());
	    			queryUpdateRxTrans.setParameter("language", rxTransDetailVO.getLanguage());
	    			queryUpdateRxTrans.setParameter("transDescription", transDescription);
	    			result1 = queryUpdateRxTrans.executeUpdate();
	    		} else if(null != rxTransDetailVO.getRxTransTitle() && !"".equalsIgnoreCase(rxTransDetailVO.getRxTransTitle())){
	    			StringBuilder insertRxTrans = new StringBuilder();
	    			insertRxTrans.append("insert into GETS_SD.GETS_SD_RECOM_UT8 (GETS_SD_RECOM_UT8_ID,LINK_RECOM," +
	    					"LANGUAGE_CD,TRANS_RECOM_TITLE_TXT,LAST_UPDATED_DATE,LAST_UPDATED_BY,CREATION_DATE,CREATED_BY,TRANS_RECOM_DESCRIPTION)" +
	    					"values(GETS_SD.GETS_SD_RECOM_UT8_SEQ.nextval,:recomId,:language,:title,sysdate,:updatedBy, sysdate,:createdBy,:transDescription)");
	    			Query queryUpdateRxTrans = hibernateSession.createSQLQuery(insertRxTrans.toString());
	    			queryUpdateRxTrans.setParameter("title", title);
	    			queryUpdateRxTrans.setParameter("updatedBy", rxTransDetailVO.getLastModifiedBy());
	    			queryUpdateRxTrans.setParameter("createdBy", rxTransDetailVO.getLastModifiedBy());
	    			queryUpdateRxTrans.setParameter("recomId", rxTransDetailVO.getRxObjid());
	    			queryUpdateRxTrans.setParameter("language", rxTransDetailVO.getLanguage());
	    			queryUpdateRxTrans.setParameter("transDescription", transDescription);
	    			result1 = queryUpdateRxTrans.executeUpdate();
	    		}
	    		
	    			Date date = new Date();
	    			SimpleDateFormat formatter = new SimpleDateFormat(RMDCommonConstants.DateConstants.MMddyyyyHHmmss);
	    			String sysdate = formatter.format(date);
	    			StringBuilder comment = new StringBuilder();
	    			comment.append("Old Status-" + rxTransDetailVO.getTransStatus()
	    					+ " : New Status-" + rxTransDetailVO.getTransNewStatus());

	    			comment.append(" : " + rxTransDetailVO.getLastModifiedBy() + " : " + sysdate + "/");

	    			
	    			String updateHist = "Call GETS_SD_MULTILINGUAL_PKG.GETS_SD_ML_HISTUPDATE_PR(?,?,?,?,?,?,?)";
	    			CallableStatement callableStatement = con.prepareCall(updateHist);
	    			callableStatement.setString(1, comment.toString());
	    			callableStatement.setString(2, AppSecUtil
							.stripNonValidXMLCharactersForKM(rxTransDetailVO.getRxTitle()));
	    			callableStatement.setString(3, rxTransDetailVO.getLanguage());
	    			callableStatement.setString(4, rxTransDetailVO.getLastModifiedBy());
	    			callableStatement.setString(5, rxTransDetailVO.getTransNewStatus());
	    			callableStatement.setString(6, AppSecUtil
							.stripNonValidXMLCharactersForKM(rxTransDetailVO.getRevisionNote()));
	    			callableStatement.setString(7, rxTransDetailVO.getRxObjid());
	    			callableStatement.execute();
	    			if(null != rxTransDetailVO) {
	    				for(RxTransTaskDetailVO transTask: rxTransDetailVO.getArlRxTransTaskDetailVO()){
	    					CallableStatement cst = con.prepareCall("Call GETS_SD_MULTILINGUAL_PKG.GETS_SD_ML_TASKUPDATE_PR(?,?,?,?,?,?,?)");
	    					cst.setString(1, rxTransDetailVO.getLanguage());
	    					
	    					if (lang.equalsIgnoreCase(RMDServiceConstants.LANG_FRENCH)
	    							|| lang.equalsIgnoreCase(RMDServiceConstants.LANG_SPANISH)||lang.equalsIgnoreCase(RMDServiceConstants.LANG_PORTUGUESE) || lang.equalsIgnoreCase(RMDServiceConstants.LANG_INDONESIA)) {
	    						cst.setString(2, AppSecUtil
	    								.stripNonValidXMLCharactersForKM(transTask.getStrTransTaskDesc()));
	    						cst.setString(3, RMDServiceConstants.UNICODE_STORE_NO);
	    					}
	    					else if(lang.equalsIgnoreCase(RMDServiceConstants.LANG_CHINESE)||lang.equalsIgnoreCase(RMDServiceConstants.LANG_RUSSIAN)
	    							|| lang.equalsIgnoreCase(RMDServiceConstants.LANG_HINDI)){
	    						cst.setString(2, mlUtil.convertToHexaUnicode(AppSecUtil
	    								.stripNonValidXMLCharactersForKM(transTask.getStrTransTaskDesc())));
	    						cst.setString(3, RMDServiceConstants.UNICODE_STORE_YES);
	    					}
	    					cst.setString(4, rxTransDetailVO.getRxTitle());
	    					cst.setString(5, transTask.getStrTaskId());
	    					cst.setString(6, rxTransDetailVO.getRxObjid());
	    					cst.registerOutParameter(7, Types.VARCHAR);
	    					cst.execute();

	    					String rxDocBasePath = rxDataUrl + File.separator
	    							+ RMDServiceConstants.RX_AUTH;
	    					String translatedRxPath = rxDocBasePath + File.separator + rxTransDetailVO.getRxObjid() +"_"+rxTransDetailVO.getLanguage();

	    					String finTaskPath = translatedRxPath;
	    					String sDBDocPath = finTaskPath.replace(rxDataUrl,
	    							rxDataDBURL);
	    					File taskAttachment = new File(finTaskPath);
	    					List<String> filePathCreated = new ArrayList<String>();
	    					if (!taskAttachment.exists()) {
	    						taskAttachment.mkdirs();
	    						filePathCreated.add(taskAttachment
	    								.getAbsolutePath());
	    					}
	    					String sDBDomainPath = this.domainPath
	    							+ sDBDocPath;
	    					
	    					List<AddEditRxTaskDocVO> documents = transTask.getRxTransTaskDocVO();
	    					if(null != documents) {
    							// rename folder on filesystem
    							File file = new File(translatedRxPath).getAbsoluteFile();
    							String tempRxPath = translatedRxPath + "_temp";
    							File newFile = new File(tempRxPath).getAbsoluteFile();
    							if (file.exists()) { // if directory exists rename to temp
    								FileUtils.copyDirectory(file, newFile, true);
    								// Delete directory and sub-directory
    								File[] fileArr = file.listFiles();
    								for (File file1 : fileArr) {
    									if (file1.isDirectory()) {
    										FileUtils.deleteDirectory(file1);
    									} else {
    										FileUtils.forceDelete(file1);
    									}
    								}
    								try {
    									FileUtils.deleteDirectory(file);
    								} catch (IOException e) {
    									// In case FileUtils is not able to delete, try deleting
    									// using java.io.File
    									fileArr = file.listFiles();
    									for (File file1 : fileArr) {
    										file1.delete();
    									}
    									file.delete();
    								}
    							}
    							
    							if (null != finTaskPath && null != tempRxPath) {
    								File file2 = new File(finTaskPath)
    								.getAbsoluteFile();
    								File newFile2 = new File(tempRxPath)
    								.getAbsoluteFile();
    								if (newFile2.exists()) {
    									FileUtils.copyDirectory(newFile2, file2, true);
    								}
    							}
    							
    							Transaction transaction = hibernateSession.beginTransaction();
    							try{
    								for(AddEditRxTaskDocVO document : documents){
    									if (document.getStrDocPath() != null
    											&& !"".equals(document
    													.getStrDocPath())
    													&& !"Y".equalsIgnoreCase(document
    															.getStrDelete())) {
    										String sDocPath = document
    												.getStrDocPath().replace(
    														this.domainPath, "");
    										String sBase64Data = readBase64FileData(
    												sDocPath,
    												document.getStrDocTitle());
    										document.setStrDocData(sBase64Data);
    									}
    									
    									if(("Y".equalsIgnoreCase(document.getStrDelete())) || (document.getStrDocData() != null && !"".equalsIgnoreCase(document.getStrDocData()) && document.getStrDocData().length() > 0)){
    										StringBuilder checkDoc = new StringBuilder();
    										checkDoc.append("SELECT COUNT(1) FROM GETS_SD.GETS_SD_RECOM_TASK_DOC WHERE RECOM_DOC2RECOM_TASK=:recomTaskId AND DOC_TITLE=:docTitle AND LINK_LANG_TYPE=(select OBJID from GETS_SD.GETS_SD_LANG_TYPE WHERE LANG_TYPE = :language) AND DELETED = 0");
    										Query checkDocQuery = hibernateSession.createSQLQuery(checkDoc.toString());
    										checkDocQuery.setParameter("recomTaskId", transTask.getStrTaskObjID());
    										checkDocQuery.setParameter("docTitle", document.getStrDocTitle());
    										checkDocQuery.setParameter("language", rxTransDetailVO.getLanguage());
    										List<Object> docList = checkDocQuery.list();
    										int countOfDoc = 0;
    										if(RMDCommonUtility.isCollectionNotEmpty(docList)){
    											countOfDoc = RMDCommonUtility.convertObjectToInt(docList.get(0));
    										}
    										if(countOfDoc > 0){
    											StringBuilder updateDoc = new StringBuilder();
    											if("Y".equalsIgnoreCase(document.getStrDelete())){
    												updateDoc.append("delete from GETS_SD.GETS_SD_RECOM_TASK_DOC WHERE RECOM_DOC2RECOM_TASK=:recomTaskId AND DOC_TITLE=:docTitle AND LINK_LANG_TYPE=(select OBJID from GETS_SD.GETS_SD_LANG_TYPE WHERE LANG_TYPE = :language) AND DELETED = 0");
    												Query updateDocQuery = hibernateSession.createSQLQuery(updateDoc.toString());
    												updateDocQuery.setParameter("recomTaskId", transTask.getStrTaskObjID());
    												updateDocQuery.setParameter("docTitle", document.getStrDocTitle());
    												updateDocQuery.setParameter("language", rxTransDetailVO.getLanguage());
    												updateDocQuery.executeUpdate();
    											} else {
    												updateDoc.append("UPDATE GETS_SD.GETS_SD_RECOM_TASK_DOC SET DOC_PATH = :docPath WHERE RECOM_DOC2RECOM_TASK=:recomTaskId AND DOC_TITLE=:docTitle AND LINK_LANG_TYPE=(select OBJID from GETS_SD.GETS_SD_LANG_TYPE WHERE LANG_TYPE = :language) AND DELETED = 0");
    												Query updateDocQuery = hibernateSession.createSQLQuery(updateDoc.toString());
    												updateDocQuery.setParameter("recomTaskId", transTask.getStrTaskObjID());
    												updateDocQuery.setParameter("docPath", sDBDomainPath);
    												updateDocQuery.setParameter("docTitle", document.getStrDocTitle());
    												updateDocQuery.setParameter("language", rxTransDetailVO.getLanguage());
    												updateDocQuery.executeUpdate();
    											}

    										} else {
    											StringBuilder insertDoc = new StringBuilder();
    											insertDoc.append("INSERT INTO GETS_SD.GETS_SD_RECOM_TASK_DOC(OBJID, RECOM_DOC2RECOM_TASK, DOC_TITLE, DELETED, DOC_PATH, LAST_UPDATED_BY, LAST_UPDATED_DATE, CREATED_BY, CREATION_DATE, LINK_LANG_TYPE) ");
    											insertDoc.append("VALUES(GETS_SD_RECOM_TASK_DOC_SEQ.NEXTVAL, :recomTaskId, :docTitle, 0, :docPath, :lastUpdatedBy, sysdate, :createdBy, sysdate, (select OBJID from GETS_SD.GETS_SD_LANG_TYPE WHERE LANG_TYPE = :language))");
    											Query insertDocQuery = hibernateSession.createSQLQuery(insertDoc.toString());
    											insertDocQuery.setParameter("recomTaskId", transTask.getStrTaskObjID());
    											insertDocQuery.setParameter("docPath", sDBDomainPath);
    											insertDocQuery.setParameter("docTitle", document.getStrDocTitle());
    											insertDocQuery.setParameter("lastUpdatedBy", rxTransDetailVO.getLastModifiedBy());
    											insertDocQuery.setParameter("createdBy", rxTransDetailVO.getLastModifiedBy());
    											insertDocQuery.setParameter("language", rxTransDetailVO.getLanguage());
    											insertDocQuery.executeUpdate();
    										}


    										String finalPathWithTitle = finTaskPath
    												+ File.separator
    												+ document.getStrDocTitle();

    										if(document
    												.getStrDocData() != null){
    											byte[] bArr = document
    													.getStrDocData().getBytes();
    											byte[] bDecodedArr = Base64.decode(bArr);
    											File newfile = new File(finalPathWithTitle);
    											FileUtils.writeByteArrayToFile(newfile,
    													bDecodedArr);
    										}

    										if (null != tempRxPath) {
    											filePathCreated.clear();
    											filePathCreated.add(tempRxPath);
    											deleteDirs(filePathCreated);
    										}
    									}
    								}
    								transaction.commit();
    							}catch (Exception e){
    								transaction.rollback();
    								
    								if (filePathCreated.size() > 0) {// Delete the file paths created if
    									// it is a rollback
    									boolean bDelete = deleteDirs(filePathCreated);
    									LOG.error("Directories cannot be deleted :" + bDelete);
    								}
    								// Rename temp directory back to normal
    								if (tempRxPath != null) {
    									File fileTemp = new File(tempRxPath);
    									File recreateFile = new File(translatedRxPath);
    									try {
    										FileUtils.copyDirectory(fileTemp, recreateFile, true);
    										// Delete directory and sub-directory
    										File[] fileArr = fileTemp.listFiles();
    										for (File file1 : fileArr) {
    											FileUtils.deleteDirectory(file1);
    										}
    										FileUtils.deleteDirectory(fileTemp);
    										LOG.debug("Directory Rollback successful");
    									} catch (IOException ioe) {
    										LOG.error("Unable to rollback directory :" + ioe);
    									}
    								}
    								
    								String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_CREATEDBY);
    					    		throw new RMDDAOException(errorCode, new String[] {},
    					    				RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
    					    				RMDCommonConstants.MAJOR_ERROR);
    							}
	    					}
	    				}
	    			}
	    		

	    	} catch (RMDDAOConnectionException ex) {
	    		ex.printStackTrace();
	    		String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_CREATEDBY);
	    		throw new RMDDAOException(errorCode, new String[] {},
	    				RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), ex,
	    				RMDCommonConstants.FATAL_ERROR);
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    		String errorCode = RMDCommonUtility.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_RECOM_CREATEDBY);
	    		throw new RMDDAOException(errorCode, new String[] {},
	    				RMDCommonUtility.getMessage(errorCode, new String[] {}, RMDCommonConstants.ENGLISH_LANGUAGE), e,
	    				RMDCommonConstants.MAJOR_ERROR);
	    	} finally {
	    		try {
	    			con.close();
	    		} catch (SQLException e) {
	    			e.printStackTrace();
	    		}
	    		releaseSession(hibernateSession);
	    	}
	    	return rxTransDetailVO.getRxObjid();
	    }

	    /**
	     * Reads the file data from FS and converts into Base64 encoding to be
	     * decoded later
	     * 
	     * @param docPath
	     * @return
	     * @throws Exception
	     */
	    private String readBase64FileData(String docPath, String fileName)
	    		throws Exception {

	    	String sBase64Data = null;
	    	String sFSPath = null;
	    	try {

	    		// Replace docPath with correct file system path
	    		sFSPath = docPath.replace(this.rxDataDBURL, this.rxDataUrl);
	    		LOG.debug("File System Path: " + sFSPath);
	    		File file = new File(sFSPath, fileName).getAbsoluteFile();
	    		if(file.exists() && !file.isDirectory()) { 
	    			byte[] bArr = FileUtils.readFileToByteArray(file);
		    		sBase64Data = new String(Base64.encode(bArr));
	    		}
	    	} catch (IOException ioe) {
	    		LOG.error(sFSPath, ioe);
	    		throw ioe;
	    	}
	    	return sBase64Data;
	    }
	    
	    /**
	     * Method to delete the directories & files
	     * 
	     * @param filePath
	     * @return
	     */
	    private boolean deleteDirs(List<String> filePath) {

	    	boolean bDeleted = true;
	    	try {

	    		for (String path : filePath) {
	    			File file = new File(path);
	    			FileUtils.deleteDirectory(file);

	    			File parent = new File(file.getParent());
	    			if (parent != null && parent.isDirectory()
	    					&& parent.list().length == 0)
	    				parent.delete();
	    		}

	    	} catch (Exception e) {

	    		bDeleted = false;
	    	}
	    	return bDeleted;
	    }
	    
	    /*
		 * This method used prevew the translated recommendation
		 * 
		 * @param AddEditRxServiceVO
		 * 
		 * @returns String
		 * 
		 * @throws RMDDAOException
		 */
		@Override
		public String previewRxPdf(RxTransDetailVO transRxDetail)
				throws RMDDAOException {

			LOG.debug("RxTranslationDAOImpl.previewRxPdf() Started");
			List<RxTransTaskDetailVO> taskList = null;
			int iTaskSize = RMDCommonConstants.INT_CONST;
			String strFilePath = RMDCommonConstants.EMPTY_STRING;
			String taskNo = RMDCommonConstants.EMPTY_STRING;
			ArrayList<RxTaskVO> taskDetails = new ArrayList<RxTaskVO>();
			final List<String> utfLangList = Arrays.asList("Chinese","Russian","Hindi");
			MultilingualUtil mlUtil = new MultilingualUtil();
			File file = null;
			try {
				String lookup = RMDCommonConstants.EMPTY_STRING;
				String baseURL = null;
				File baseDir = null;
				baseURL = System.getProperty("user.home");
				baseDir = new File(baseURL);
				com.ge.trans.rmd.tools.common.valueobjects.RxVO rxData1 = new com.ge.trans.rmd.tools.common.valueobjects.RxVO();
				rxData1.setLanguage(RMDServiceConstants.PDF_REGULAR);
				rxData1.setUTFLang(false);
				rxData1.setRecomObjID(transRxDetail.getRxObjid());
				if(utfLangList.contains(transRxDetail.getLanguage()))
					rxData1.setRecomTitle(mlUtil.convertToHexaUnicode(AppSecUtil
							.stripNonValidXMLCharactersForKM(transRxDetail.getRxTransTitle())));
				else
					rxData1.setRecomTitle(AppSecUtil
							.stripNonValidXMLCharactersForKM(transRxDetail.getRxTransTitle()));
				rxData1.setUrgency(transRxDetail.getStrUrgRepair());
				rxData1.setEstRepairTime(transRxDetail.getStrEstmTimeRepair());
				rxData1.setLocoImpact(transRxDetail.getStrSelAssetImp());
				rxData1.addHeaderTask("  ");
				rxData1.addHeaderTask("  ");
				rxData1.addHeaderTask("  ");
				rxData1.addHeaderTask("  ");
				rxData1.setTitleMid(RMDServiceConstants.STR_RX);
				rxData1.setVersion("");
				rxData1.setLastModifiedOn(new Date());
				rxData1.setCreatedOn(new Date());
				rxData1.setRecomType("");
				//OMDKM 1669 - for Bahasa Indonesia
				rxData1.setLanguage(transRxDetail.getLanguage()!=null?transRxDetail.getLanguage().replaceAll(" ", ""):null);
				
				if(utfLangList.contains(transRxDetail.getLanguage()))
				{
					rxData1.setUTFLang(true);
				}
				else
				{
					rxData1.setUTFLang(false);
				}
				
				List<ElementVO> arlElementVO = getLookupListValues(
						RMDServiceConstants.RX_STATE_LOOKBACK_DAYS,
						transRxDetail.getLanguage());
				if (RMDCommonUtility.isCollectionNotEmpty(arlElementVO)) {
					lookup = arlElementVO.get(0).getName();
				}
				rxData1.setRecomState(rxData1.calculateStatus(Integer
						.parseInt(lookup)));

				taskList = transRxDetail.getArlRxTransTaskDetailVO();
				iTaskSize = taskList.size();
				for (int index = 0; index < iTaskSize; index++) {

					RxTransTaskDetailVO taskVo = (RxTransTaskDetailVO) taskList
							.get(index);
					if (!taskNo.trim().equalsIgnoreCase(taskVo.getStrTaskId().trim())) {

						RxTaskVO taskData = new RxTaskVO();
						taskData.setTaskID(taskVo.getStrTaskId());
						if(utfLangList.contains(transRxDetail.getLanguage()))
							taskData.setDescription(mlUtil.convertToHexaUnicode(AppSecUtil
									.stripNonValidXMLCharactersForKM(taskVo.getStrTransTaskDesc())));
						else
							taskData.setDescription(AppSecUtil
									.stripNonValidXMLCharactersForKM(taskVo.getStrTransTaskDesc()));
						taskData.setLowerSpec(taskVo.getLsl());
						taskData.setUpperSpec(taskVo.getUsl());
						taskData.setTarget(taskVo.getTarget());
						taskDetails.add(taskData);
					}
				}

				rxData1.setTaskDetails(taskDetails);
				RxPdfGenerator rxPdfGenerator;
				rxPdfGenerator = new RxPdfGenerator(
						Logger.getLogger(AddEditRxDAOImpl.class));
				String baseRxFilePath = baseDir.getAbsolutePath() + File.separator
						+ "report" + File.separator + "recommendations"
						+ File.separator+transRxDetail.getRxObjid()+File.separator;
				String baseRxFileName = transRxDetail.getRxObjid()+"_"+transRxDetail.getLanguage();
				file = rxPdfGenerator.createPdfFile(rxData1, baseRxFilePath,
						baseRxFileName);
				if (file.exists())
					strFilePath = file.getAbsolutePath();

			} catch (Exception rmdEx) {
				LOG.debug(rmdEx.getMessage());
			}
			return strFilePath;
		}
}
