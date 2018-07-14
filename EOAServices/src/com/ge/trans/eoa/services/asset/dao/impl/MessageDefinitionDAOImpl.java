package com.ge.trans.eoa.services.asset.dao.impl;

import getsrmd.mq.MQConnection;
import getsrmd.mq.PutMQMsgParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.cache.annotation.Cacheable;

import com.ge.trans.eoa.services.asset.dao.intf.MessageDefinitionDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.MessageDefVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.MessageQueuesVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.eoa.services.common.dao.impl.BaseDAO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.ibm.mq.MQException;

public class MessageDefinitionDAOImpl extends BaseDAO implements
		MessageDefinitionDAOIntf {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final RMDLogger msgDefLogger = RMDLogger
			.getLogger(MessageDefinitionDAOImpl.class);

	/**
	 * @Author :
	 * @return :MessageDefVO
	 * @param : String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Message Def ObjId
	 *               Details.
	 * 
	 */
	@Override
	@Cacheable(value = "GetEFIMsgDefinition")
	public MessageDefVO getEFIMsgDefObjid() throws RMDDAOException {
		MessageDefVO objMessageDefVO = null;
		Query hibernateQuery = null;
		Session session = null;
		StringBuilder edpMsgDefObjIdQuery = new StringBuilder();
		try {
			session = getHibernateSession();
			edpMsgDefObjIdQuery
					.append(" SELECT OBJID, MSG_NUM_CODE,MSG_PRIORITY FROM GETS_OMI_MESSAGE_DEF WHERE  ");
			edpMsgDefObjIdQuery
					.append(" MSG_NUM_TITLE IN ('EFI PARAMETER MAP DEFINITION UPLOADED')");
			hibernateQuery = session.createSQLQuery(edpMsgDefObjIdQuery
					.toString());
			List<Object[]> arlMsgDefs = hibernateQuery.list();
			if (null != arlMsgDefs && !arlMsgDefs.isEmpty()) {
				Object[] objApply = arlMsgDefs.get(0);
				objMessageDefVO = new MessageDefVO();
				objMessageDefVO.setMsgDefObjidApply(RMDCommonUtility
						.convertObjectToInt(objApply[0]));
				objMessageDefVO.setMsgNumCode(RMDCommonUtility
						.convertObjectToString(objApply[1]));
				objMessageDefVO.setMessagePriority(RMDCommonUtility
						.convertObjectToString(objApply[2]));
				objApply = null;
			}
			arlMsgDefs = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FFD_MSGDEF_OBJID);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return objMessageDefVO;
	}

	/**
	 * @Author :
	 * @return :List<String>
	 * @param : String vehicleObjId
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for fetching Services Types
	 *               which will allow EDP,FFD,FRD Config Tempaltes.
	 * 
	 */
	@Override
	@Cacheable(value = "GetEnabledServicesCacheEFI")
	public List<String> getEnabledServicesEFI() throws RMDDAOException {
		List<String> arlEnabledservices = null;
		Query hibernateQuery = null;
		Session session = null;
		StringBuilder edpMsgDefObjIdQuery = new StringBuilder();
		try {
			session = getHibernateSession();
			edpMsgDefObjIdQuery
					.append(" SELECT DISTINCT SERVICE_TYPE FROM GETS_RMD_SERVICE_CFG_DEF WHERE CFG_TYPE IN('EFI')");
			edpMsgDefObjIdQuery.append("ORDER BY SERVICE_TYPE");
			hibernateQuery = session.createSQLQuery(edpMsgDefObjIdQuery
					.toString());
			List<Object> arlServices = hibernateQuery.list();
			if (null != arlServices && !arlServices.isEmpty()) {
				arlEnabledservices = new ArrayList<String>(arlServices.size());
				for (Object object : arlServices) {
					arlEnabledservices.add(RMDCommonUtility
							.convertObjectToString(object));
				}
			}
			arlServices = null;
		} catch (Exception e) {
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_GET_FFD_MSGDEF_OBJID);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
		}
		return arlEnabledservices;

	}

	/**
	 * @Author :
	 * @return :
	 * @param : List<MessageQueuesVO> arlMessageQueuesVOs
	 * @throws :RMDDAOException
	 * @Description: This method is Responsible for sending list of Messages to
	 *               Mqueues.
	 * 
	 */
	@Override
	public void sendMessageToQueue(List<MessageQueuesVO> arlMessageQueuesVOs)
			throws RMDDAOException {
		Session session = null;
		Query hibernateQuery = null;
		StringBuilder mqDetailsQuery = new StringBuilder();
		String queueName = null;
		String queueMgr = null;
		MQConnection mqConn = null;
		PutMQMsgParam putmqmsgparam = null;
		int index = 0;
		try {
			session = getHibernateSession();
			mqDetailsQuery
					.append("SELECT DEFAULT_OUTPUT_QUEUE_NAME,QUEUE_MGR FROM GETS_RMD_PROGRAM_DEF WHERE PROGRAM_ID = :programId AND  INSTANCE_ID = :instanceId ");
			hibernateQuery = session.createSQLQuery(mqDetailsQuery.toString());
			hibernateQuery.setParameter(RMDCommonConstants.PROGRAM_ID,
					RMDCommonConstants.NUMBER_THREE_THOUSAND_TWENTY);
			hibernateQuery.setParameter(RMDCommonConstants.INSTANCE_ID,
					RMDCommonConstants.NUMBER_ONE);
			List<Object[]> queueDetailsList = hibernateQuery.list();
			if (RMDCommonUtility.isCollectionNotEmpty(queueDetailsList)) {
				Object[] mqObject = queueDetailsList.get(0);
				queueName = RMDCommonUtility.convertObjectToString(mqObject[0]);
				queueMgr = RMDCommonUtility.convertObjectToString(mqObject[1]);
			}
			try {
				mqConn = new MQConnection(queueMgr);
				msgDefLogger.info("After Connecting to MQ TAB File");
				msgDefLogger.info("No of Messages to send :"
						+ arlMessageQueuesVOs.size());
				for (MessageQueuesVO objMessageQueuesVO : arlMessageQueuesVOs) {
					putmqmsgparam = new PutMQMsgParam();
					putmqmsgparam.setQueueManagerName(queueMgr);
					putmqmsgparam.setOutputQueueName(queueName);
					Long longVehicleObjId = new Long(
							objMessageQueuesVO.getVehicleObjId());
					String strVehObjId = longVehicleObjId.toString();
					byte[] abyte0 = strVehObjId.getBytes();
					putmqmsgparam.setCorrelationId(abyte0);
					Long longMsgId = new Long(objMessageQueuesVO.getMessageId());
					String strMsgId = longMsgId.toString();
					byte[] abyte1 = strMsgId.getBytes();
					putmqmsgparam.setMsgId(abyte1);
					putmqmsgparam.setSrvcType(objMessageQueuesVO.getServices());
					SimpleDateFormat simpledateformat = new SimpleDateFormat(
							RMDCommonConstants.DateConstants.yyyyMMddHHmmss);
					Date date = new Date();
					String generationTime = simpledateformat.format(date);
					putmqmsgparam.setMsgGenTime(generationTime);
					putmqmsgparam.setVehicleHdr(objMessageQueuesVO
							.getRoadNumberHeader());
					putmqmsgparam.setVehicleNum(objMessageQueuesVO
							.getRoadNumber());
					putmqmsgparam.setVehicleHdrNum(objMessageQueuesVO
							.getVehicleHdrNo());
					putmqmsgparam.setPriority(Integer
							.parseInt(objMessageQueuesVO.getMessagePriority()));
					putmqmsgparam.setObMsgRef(longMsgId.intValue());
					putmqmsgparam.setMsgBody(putmqmsgparam.setGetsHdr());
					mqConn.sendRequest(putmqmsgparam);
					mqConn.commitRequest();
					msgDefLogger.info("MQ request was Sent Success fully for : "
							+ objMessageQueuesVO.getRoadNumberHeader() + " - "
							+ objMessageQueuesVO.getRoadNumber()
							+ "with Messsage Id : "
							+ objMessageQueuesVO.getMessageId());
					index++;
				}
				msgDefLogger.info("Total No of Messages sent :" + index);
			} catch (MQException mqexception) {
				if (null != mqConn) {
					mqConn.backoutRequest();
				}
				throw new MQException(mqexception.completionCode,
						mqexception.reasonCode, mqexception);
			}
		} catch (Exception e) {
			msgDefLogger.info("Total No of Messages sent :" + index);
			String errorCode = RMDCommonUtility
					.getErrorCode(RMDServiceConstants.DAO_EXCEPTION_SEND_MESSAGE_TO_QUEUE);
			throw new RMDDAOException(errorCode, new String[] {},
					RMDCommonUtility.getMessage(errorCode, new String[] {},
							RMDCommonConstants.ENGLISH_LANGUAGE), e,
					RMDCommonConstants.MINOR_ERROR);
		} finally {
			releaseSession(session);
			if (mqConn != null) {
				try {
					mqConn.closeOutputQueue(queueName);
					mqConn.disconnect();
				} catch (MQException e) {
					msgDefLogger.error(e);
					mqConn = null;
				}
			}
			
		}
	}
}
