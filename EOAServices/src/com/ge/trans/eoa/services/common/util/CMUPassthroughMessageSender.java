package com.ge.trans.eoa.services.common.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.asset.dao.intf.CMUPassthroughDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.CMUPassthroughVO;
import com.ge.trans.eoa.services.common.constants.RMDServiceConstants;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class CMUPassthroughMessageSender {

	@Autowired
	CMUPassthroughDAOIntf objCmuPassthroughDAOIntf;

	public static final RMDLogger cmuPassMessageSenderLogger = RMDLogger
			.getLogger(CMUPassthroughMessageSender.class);

	public void sendCmuMQMessages(final List<String> vehicleOjectIdList,
			final String requestorId, final String actionType) {

		CmuPassthroughThread cmuPassthroughThread = new CmuPassthroughThread(
				vehicleOjectIdList, requestorId, actionType);
		cmuPassthroughThread.start();

	}

	private class CmuPassthroughThread extends Thread {

		List<String> vehicleOjectIdList;
		String requestorId;
		String actionType;

		public CmuPassthroughThread(List<String> vehicleOjectIdList,
				String requestorId, String actionType) {
			this.vehicleOjectIdList = vehicleOjectIdList;
			this.requestorId = requestorId;
			this.actionType = actionType;
		}

		@Override
		public void run() {

			Map<String, String> cmuMQDetailsMap = objCmuPassthroughDAOIntf
					.getCmuMQDetails();

			List<CMUPassthroughVO> cmuPassthroughVOList = objCmuPassthroughDAOIntf
					.getCmuMQMessages(vehicleOjectIdList);

			cmuPassMessageSenderLogger
					.info("Number of XML messages to be sent to CMU queue is :"
							+ cmuPassthroughVOList.size());

			MQEnvironment.hostname = cmuMQDetailsMap
					.get(RMDServiceConstants.EGA_MQ_HOST_NAME);
			MQEnvironment.port = Integer.valueOf(cmuMQDetailsMap
					.get(RMDServiceConstants.EGA_MQ_PORT));
			MQEnvironment.channel = cmuMQDetailsMap
					.get(RMDServiceConstants.EGA_MQ_CHANNEL);
			String qName = cmuMQDetailsMap
					.get(RMDServiceConstants.EGA_MQ_ADMIN_INPUT_QUEUE);
			String qManager = cmuMQDetailsMap
					.get(RMDServiceConstants.EGA_MQ_QUEUE_MANAGER);

			MQQueue queue = null;
			MQQueueManager qMgr = null;
			try {
				qMgr = new MQQueueManager(qManager);
				int openOptions = 16;
				queue = qMgr.accessQueue(qName, openOptions);
				cmuPassMessageSenderLogger
						.info("Established connection with CMU queue " + qName);
				int messageSentCount = 0;
				MQMessage msg;
				MQPutMessageOptions pmo;

				cmuPassMessageSenderLogger
						.info("Started the process of pushing XML messages to CMU Passthrough MQ");
				for (CMUPassthroughVO cmuPassthroughVO : cmuPassthroughVOList) {
					msg = new MQMessage();
					pmo = new MQPutMessageOptions();
					msg.writeString(prepareXmlMessageBody(cmuPassthroughVO,
							requestorId, actionType));
					msg.messageId = String.valueOf(
							cmuPassthroughVO.getRequestNo()).getBytes();
					msg.priority = 5;
					pmo.options = 2;
					queue.put(msg, pmo);
					messageSentCount++;

					cmuPassMessageSenderLogger.debug(messageSentCount
							+ " - Put xml message in queue for "
							+ cmuPassthroughVO.getVehicleNo());

					if (messageSentCount % 100 == 0) {
						qMgr.commit();
					}
				}

				qMgr.commit();
				cmuPassMessageSenderLogger
						.info("The total number of messages sent to CMU Queue is :"
								+ messageSentCount);

			} catch (MQException ex) {
				cmuPassMessageSenderLogger
						.error("CMU MQ Error occured : Completion Code "
								+ ex.completionCode + " Reason Code "
								+ ex.reasonCode + " : " + ex);
			} catch (Exception e) {
				cmuPassMessageSenderLogger
						.error("Exception occured in CmuPassthroughThread while sending messages to CMU queue as "
								+ e);
			} finally {
				if (queue != null && queue.isOpen()) {
					try {
						queue.close();
						cmuPassMessageSenderLogger
								.info("Closed Queue connection gracefully.");
					} catch (Exception ex) {
						queue = null;
						cmuPassMessageSenderLogger
								.error("Exception occured in CmuPassthroughThread while closing CMU queue connection "
										+ ex);
					}
				}

				if (qMgr != null && qMgr.isOpen()) {
					try {
						qMgr.close();
						cmuPassMessageSenderLogger
								.info("Closed Queue manager connection gracefully.");
					} catch (Exception e) {
						qMgr = null;
						cmuPassMessageSenderLogger
								.error("Exception occured in CmuPassthroughThread closing CMU queue connection "
										+ e);
					}
				}

				if (qMgr != null && qMgr.isConnected()) {
					try {
						qMgr.disconnect();
						cmuPassMessageSenderLogger
								.info("Disconnected from Queue manager gracefully.");
					} catch (Exception e) {
						qMgr = null;
						cmuPassMessageSenderLogger
								.error("Exception occured in CmuPassthroughThread while closing CMU queue manager connection "
										+ e);
					}
				}
			}

			cmuPassMessageSenderLogger
					.info("Finished the process of pushing XML messages to CMU Passthrough MQ");

		}

		private String prepareXmlMessageBody(CMUPassthroughVO cmuPassthroughVO,
				String requestorId, String actionType) {

			StringBuilder xmlMessage = new StringBuilder();

			xmlMessage.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

			xmlMessage
					.append("<ROOT>\n");
			xmlMessage.append("<HEADER>\n");

			xmlMessage
					.append("<TRANSACTION_INDICATOR></TRANSACTION_INDICATOR>\n");

			xmlMessage.append("<REQUEST_NO>");
			xmlMessage.append(cmuPassthroughVO.getRequestNo());
			xmlMessage.append("</REQUEST_NO>\n");

			xmlMessage.append("<HEADER_DATE>");
			xmlMessage.append(cmuPassthroughVO.getHeaderDate());
			xmlMessage.append("</HEADER_DATE>\n");

			xmlMessage.append("<ACTION_TYPE>");
			xmlMessage.append(actionType);
			xmlMessage.append("</ACTION_TYPE>\n");

			xmlMessage.append("<PHYS_RES_ID>");
			xmlMessage.append((cmuPassthroughVO.getPhyResId() == null) ? ""
					: cmuPassthroughVO.getPhyResId());
			xmlMessage.append("</PHYS_RES_ID>\n");

			xmlMessage.append("<CUST_ID>");
			xmlMessage.append(cmuPassthroughVO.getCustId());
			xmlMessage.append("</CUST_ID>\n");

			xmlMessage.append("<VEHICLE_INITIAL>");
			xmlMessage.append(cmuPassthroughVO.getVehicleInitial());
			xmlMessage.append("</VEHICLE_INITIAL>\n");

			xmlMessage.append("<VEHICLE_NO>");
			xmlMessage.append(cmuPassthroughVO.getVehicleNo());
			xmlMessage.append("</VEHICLE_NO>\n");

			xmlMessage.append("<SOURCE>OMD</SOURCE>\n");

			xmlMessage.append("<REQUESTER>");
			xmlMessage.append(requestorId);
			xmlMessage.append("</REQUESTER>\n");
			xmlMessage.append("</HEADER>\n");
			xmlMessage.append("</ROOT>\n");
				cmuPassMessageSenderLogger.debug(xmlMessage);
			return xmlMessage.toString();
		}
	}
}
