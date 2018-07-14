package com.ge.trans.eoa.services.common.util;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

public class MessageSender {
    private static final Logger LOGGER = Logger.getLogger(MessageSender.class);

    @Autowired
    protected JmsTemplate jmsTemplate;
    protected Destination destination;
    
    @Autowired
    @Qualifier("jmsTemplateForRCI")
    protected JmsTemplate jmsTemplateForRCI;

   

	/**
	 * @return the jmsTemplateForRCI
	 */
	public final JmsTemplate getJmsTemplateForRCI() {
		return jmsTemplateForRCI;
	}

	/**
	 * @param jmsTemplateForRCI the jmsTemplateForRCI to set
	 */
	public final void setJmsTemplateForRCI(JmsTemplate jmsTemplateForRCI) {
		this.jmsTemplateForRCI = jmsTemplateForRCI;
	}

    public void putQueueMessage(final String strInputMsg) throws RMDBOException {
        try {
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = null;
                    try {
                        message = session.createTextMessage();
                        message.setText(strInputMsg);

                    } catch (JMSException e) {
                        LOGGER.error("JMSException occured in putQueueMessage: " + e.getLinkedException(), e);
                        throw e;
                    }
                    return message;
                }
            });
        } catch (Exception ex) {
            LOGGER.error("An Exception occured when sending message to the queue: ", ex);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDBOException(errorCode);
        }

    }
    
    public void sendMessageToRCIMQ(final String strInputMsg) throws RMDBOException {
        try {
        	LOGGER.info("START sendMessageToRCIMQ() sending Message to MQ");
        	jmsTemplateForRCI.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage message = null;
                    try {
                        message = session.createTextMessage();
                        message.setText(strInputMsg);

                    } catch (JMSException e) {
                        LOGGER.error("JMSException occured in putQueueMessage: " + e.getLinkedException(), e);
                        throw e;
                    }
                    return message;
                }
            });
        } catch (Exception ex) {
            LOGGER.error("An Exception occured when sending message to the queue: ", ex);
            final String errorCode = RMDCommonUtility.getErrorCode(RMDCommonConstants.DB_CONNECTION_FAIL);
            throw new RMDBOException(errorCode);
        }
      
        LOGGER.info("MESSAGE SENT "+strInputMsg);
        LOGGER.info("End sendMessageToRCIMQ() sent Message to MQ");
    }
    

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

}
