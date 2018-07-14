package com.ge.trans.eoa.services.autohc.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ge.trans.eoa.services.asset.service.valueobjects.ControllerConfigVO;
import com.ge.trans.eoa.services.autohc.dao.intf.AutoHCTemplateDaoIntf;
import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCFaultCodeServiceVO;
import com.ge.trans.eoa.services.autohc.service.valueobjects.AutoHCTemplateSaveVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.dao.RMDCommonDAO;
import com.ge.trans.rmd.exception.RMDDAOConnectionException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class AutoHCTemplateDaoImpl extends RMDCommonDAO implements
		AutoHCTemplateDaoIntf {

	private static final long serialVersionUID = 1L;

	@Override
	public ArrayList<String> getTemplateDropDwn(String ctrlConfig,
			String configFile) throws RMDDAOException {

		Session objSession = getHibernateSession();
		ArrayList<String> data = new ArrayList<String>();
		data.add("test data");
		data.add("test data");
		releaseSession(objSession);
		return data;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getFaultCodeRecTypeDropDwn(String ctrlConfig,
			String configFile) throws RMDDAOException {

		Session session = null;
		Query faultHqry = null;
		List<Object[]> resultList = null;
		List<AutoHCFaultCodeServiceVO> objVOlist = null;
		AutoHCFaultCodeServiceVO singleVO = null;
		ArrayList<String> data = new ArrayList<String>();

		try {
			session = getHibernateSession();
			StringBuilder faultCodeQry = new StringBuilder();
//			faultCodeQry
//					.append("select hc_flt_cfg2flt_code,hc_record_type,msg_def_id,objid from gets_omi.gets_omi_hc_flt_cfg order by last_updated_date desc");
//			faultCodeQry
//			.append("select c.fault_code,c.fault_desc, b.msg_num_code,a.objid from gets_omi.gets_omi_hc_flt_cfg a , gets_omi.gets_omi_message_def b ,gets_rmd.gets_rmd_fault_codes c where b.objid = a.msg_def_id and ");
//			faultCodeQry
//			.append(" c.objid = a.hc_flt_cfg2flt_code and c.fault_origin in (select controller_cfg from GETS_RMD_CTL_CFG where objid =:configCtrlObjid)  order by a.last_updated_date desc");

			
			faultCodeQry.append("SELECT c.fault_code , c.fault_desc, a.objid FROM gets_omi.gets_omi_hc_flt_cfg a , gets_omi.gets_omi_message_def b , ");
			faultCodeQry.append(" gets_rmd.gets_rmd_fault_codes c, GETS_RMD_CTL_CFG ctl_cfg, GETS_RMD_CTL_CFG_SRC ctl_cfg_src WHERE b.objid  = a.msg_def_id");
			faultCodeQry.append(" AND c.objid = a.hc_flt_cfg2flt_code AND c.controller_source_id = ctl_cfg_src.CONTROLLER_SOURCE_ID AND ctl_cfg_src.CTL_CFG_SRC2CTL_CFG = ctl_cfg.OBJID ");
			faultCodeQry.append(" AND ctl_cfg.objid =:configCtrlObjid AND b.msg_def_type = 'HC' ORDER BY c.fault_desc DESC");
			faultHqry = session.createSQLQuery(faultCodeQry.toString());
			faultHqry.setParameter(RMDCommonConstants.AH_CTRL_CONFIG_OBJID,ctrlConfig);
			faultHqry.setFetchSize(1000);
			resultList = faultHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				objVOlist = new ArrayList<AutoHCFaultCodeServiceVO>(resultList.size());
				for (Object[] objFaultC : resultList) {
					singleVO = new AutoHCFaultCodeServiceVO();
					singleVO.setFault_code(RMDCommonUtility.convertObjectToString(objFaultC[0]));
					singleVO.setRecord_type(RMDCommonUtility.convertObjectToString(objFaultC[1]));
					//singleVO.setMessage_def_id(RMDCommonUtility.convertObjectToString(objFaultC[2]));

					objVOlist.add(singleVO);
					data.add(singleVO.getFault_code() + "-"
							+ singleVO.getRecord_type()  + "~" + RMDCommonUtility
							.convertObjectToString(objFaultC[2]));
				}
			}

		}

		catch (Exception ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_FCRT);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} finally {
			releaseSession(session);
		}
		return data;

	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getMessageDefIdDropDwn() throws RMDDAOException {
		Session session = null;
		Query msgDefHqry = null;
		List<Object[]> resultList = null;
		ArrayList<String> data = new ArrayList<String>();

		try {
			session = getHibernateSession();
			StringBuilder magDefQry = new StringBuilder();
			magDefQry
					.append("select msg_num_code,msg_num_title from gets_omi_message_def where msg_def_type='HC'");

			msgDefHqry = session.createSQLQuery(magDefQry.toString());
			msgDefHqry.setFetchSize(1000);
			resultList = msgDefHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {

				for (Object[] objMsgIds : resultList) {

					data.add(RMDCommonUtility
							.convertObjectToString(objMsgIds[0])
							+ " - "
							+ RMDCommonUtility
							.convertObjectToString(objMsgIds[1]));
				}
			}

		}

		catch (Exception ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_MSG_ID);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} finally {
			releaseSession(session);
		}
		return data;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String postAutoHCNewTemplate(
			AutoHCTemplateSaveVO autoHCTemplateSaveVO) throws RMDDAOException {
		Session session = null;
		Session session1 = null;
		StringBuilder ahcTempQuery = new StringBuilder();
		StringBuilder ahcNextvalQuery = new StringBuilder();
		String result = new String();
		Transaction transaction = null;
		Query msgDefHqry = null;
		List<Object[]> resultList = null;
		List<Object> resultList1 = null;
		String nextVal =  new String();
		Boolean updVal = false;
		try {
			session1 = getHibernateSession();
			StringBuilder magDefQry = new StringBuilder();
			magDefQry
					.append("select * from gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR where AHC_TEMPLATE=:templateNo and AHC_VERSION=:versionNo and AHC_HDR2CTRCFG=:configCtrlObjid ");

			msgDefHqry = session1.createSQLQuery(magDefQry.toString());
			msgDefHqry.setParameter(RMDCommonConstants.AH_TEMPLATE_NO,autoHCTemplateSaveVO.getTemplateNo());
			msgDefHqry.setParameter(RMDCommonConstants.AH_VERSION_NO,autoHCTemplateSaveVO.getVersionNo());
			msgDefHqry.setParameter(RMDCommonConstants.AH_CTRL_CONFIG_OBJID,autoHCTemplateSaveVO.getConfigCtrlObjid());
			msgDefHqry.setFetchSize(10);
			resultList = msgDefHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
				updVal = true;
			}else{
				updVal = false;
			}
			session1.close();
			session = getHibernateSession();
			transaction = session.beginTransaction();
			if(!updVal){
				ahcNextvalQuery.append("select GETS_OMI.GETS_OMI_AUTO_HC_CONFIG_SEQ.NEXTVAL from DUAL");
				final Query ahcNextvalHQuery = session
						.createSQLQuery(ahcNextvalQuery.toString());
				ahcNextvalHQuery.setFetchSize(1);
				resultList1 = ahcNextvalHQuery.list();
				
//				Object[] objArray = resultList1.get(0);
				System.out.println(RMDCommonUtility
						.convertObjectToString(resultList1.get(0)));
				nextVal = RMDCommonUtility
						.convertObjectToString(resultList1.get(0));
			}
			
			System.out.println(autoHCTemplateSaveVO.getStatus());
			if(!updVal){
				ahcTempQuery.append("INSERT INTO gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR  (OBJID,AUTO_HC_CONFIG_HDR2CFG_DEF,SAMPLE_RATE,NO_OF_POST_SAMPLES,");
				ahcTempQuery.append("FREQUENCY_OF_HC_MIN, CREATION_DATE, CREATED_BY, LAST_UPDATED_DATE, LAST_UPDATED_BY,");
				ahcTempQuery.append(" AUTO_HC_CFG_HDR2HC_FLT_CFG, AHC_TEMPLATE, AHC_VERSION, AHC_DESC, STATUS, AHC_HDR2CTRCFG");
				ahcTempQuery.append(" ) VALUES( :nextval,:tempObjId,:sampleRate,");
				ahcTempQuery.append(":postSamples, :frequency,sysdate, 'ADMIN',sysdate, 'ADMIN',");
				ahcTempQuery.append(":fcrtObjid,:templateNo,:versionNo,:title,:status,:configCtrlObjid)");
				
			}else{
				ahcTempQuery.append("UPDATE gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR SET AUTO_HC_CONFIG_HDR2CFG_DEF =:tempObjId, SAMPLE_RATE =:sampleRate, NO_OF_POST_SAMPLES=:postSamples, ");
				ahcTempQuery.append(" FREQUENCY_OF_HC_MIN =:frequency, AUTO_HC_CFG_HDR2HC_FLT_CFG=:fcrtObjid, AHC_DESC=:title,  STATUS=:status ");
				ahcTempQuery.append("  WHERE AHC_TEMPLATE=:templateNo and AHC_VERSION=:versionNo and AHC_HDR2CTRCFG=:configCtrlObjid");
			}
			final Query addAHCTempHQuery = session
					.createSQLQuery(ahcTempQuery.toString());
			if(!updVal){
				addAHCTempHQuery.setParameter("nextval",nextVal);
			}
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_TEMPLATE_OBJ_ID,autoHCTemplateSaveVO.getTempObjId());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_SAMPLE_RATE,autoHCTemplateSaveVO.getSampleRate());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_POST_SAMPLES,autoHCTemplateSaveVO.getPostSamples());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_FREQUENCY,autoHCTemplateSaveVO.getFrequency());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_FCRT_OBJID,autoHCTemplateSaveVO.getFcrtObjid());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_TEMPLATE_NO,autoHCTemplateSaveVO.getTemplateNo());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_VERSION_NO,autoHCTemplateSaveVO.getVersionNo());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_TITLE,autoHCTemplateSaveVO.getTitle());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_STATUS,autoHCTemplateSaveVO.getStatus());
			addAHCTempHQuery.setParameter(RMDCommonConstants.AH_CTRL_CONFIG_OBJID,autoHCTemplateSaveVO.getConfigCtrlObjid());
			
			addAHCTempHQuery.executeUpdate();
			transaction.commit();
			session.close();
			result = RMDCommonConstants.SUCCESS;
		}catch (Exception ex) {
				transaction.rollback();
				result = RMDCommonConstants.FAILURE;
				String errorCode = RMDCommonUtility
						.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TEMPLATE_AHC);
				throw new RMDDAOException(errorCode, new String[] {},
						RMDCommonUtility.getMessage(errorCode, new String[] {},
								RMDCommonConstants.ENGLISH_LANGUAGE), ex,
						RMDCommonConstants.FATAL_ERROR);
			} finally {
				result =  nextVal +"~"+result;
				releaseSession(session);
				releaseSession(session1);
			}
			
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getComAHCDetails(String templateNo,
			String versionNo, String ctrlCfgObjId) throws RMDDAOException {
		
		List<Object[]> resultList = null;
		Session session = null;
		Map<String,String> result = new HashMap<String, String>();
		try {
			session = getHibernateSession();
			StringBuilder caseQry = new StringBuilder();
			caseQry.append("select sample_rate,no_of_post_samples,frequency_of_hc_min,auto_hc_cfg_hdr2hc_flt_cfg,auto_hc_config_hdr2cfg_def from gets_omi.GETS_OMI_AUTO_HC_CONFIG_HDR where AHC_TEMPLATE=:templateNo and AHC_VERSION=:versionNo and AHC_HDR2CTRCFG=:configCtrlObjid ");
			Query caseHqry = session.createSQLQuery(caseQry.toString());
			caseHqry.setParameter(RMDCommonConstants.AH_TEMPLATE_NO,templateNo);
			caseHqry.setParameter(RMDCommonConstants.AH_VERSION_NO,versionNo);
			caseHqry.setParameter(RMDCommonConstants.AH_CTRL_CONFIG_OBJID,ctrlCfgObjId);
			
			caseHqry.setFetchSize(10);
			resultList = caseHqry.list();
			if (RMDCommonUtility.isCollectionNotEmpty(resultList)) {
			
				for (final Iterator<Object[]> obj = resultList.iterator(); obj
						.hasNext();) {
					
					final Object[] cnfgDetails =  obj.next();
					result.put(RMDCommonConstants.AH_SAMPLE_RATE,(RMDCommonUtility
							.convertObjectToString(cnfgDetails[0])));
					result.put(RMDCommonConstants.AH_POST_SAMPLES,(RMDCommonUtility
							.convertObjectToString(cnfgDetails[1])));
					result.put(RMDCommonConstants.AH_FREQUENCY,(RMDCommonUtility
							.convertObjectToString(cnfgDetails[2])));
					result.put(RMDCommonConstants.AH_FCRT_OBJID,(RMDCommonUtility
							.convertObjectToString(cnfgDetails[3])));
					result.put(RMDCommonConstants.AH_TEMPLATE_OBJ_ID,(RMDCommonUtility
							.convertObjectToString(cnfgDetails[4])));
					
				}
			}
		} catch (RMDDAOConnectionException ex) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TEMPLATE_AHC);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), ex,
					RMDCommonConstants.FATAL_ERROR);
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_TEMPLATE_AHC);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MAJOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return result;
	}

}
