/**
 * ============================================================
 * File : RMDQueueUtility.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.messaging
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Oct 11, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.messaging;

import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDRunTimeException;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Oct 11, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
public class RMDQueueUtility {

    private static QueueConnectionFactory queueConnectionFactory;
    private Queue queue;
    private TextMessage message;
    private QueueConnection queueConnection;
    private QueueSession queueSession;
    private static Properties props;
    private static Context ctx;

    /**
     * Opens JMS objects.
     * 
     * @param queueName
     *            the destination Queue
     * @exception JMSException
     *                if JMS fails to close objects due to internal error
     * @exception NamingException
     *                If the look up fails.
     */
    private void open(final String queueName) throws JMSException, NamingException {
        try {
            if (ctx == null) {
                ctx = getInitialContext();
            }
            final String queueFactoryProvider = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.QUEUE_CONNECTION_FACTORY_PROVIDER);
            if (queueConnectionFactory == null) {
                queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(queueFactoryProvider);
            }
            queue = (Queue) ctx.lookup(queueName);
            queueConnection = queueConnectionFactory.createQueueConnection(
                    props.getProperty(Context.SECURITY_PRINCIPAL), props.getProperty(Context.SECURITY_CREDENTIALS));
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (NamingException e) {
            throw e;
        }
    }

    /**
     * Initializes the Context.
     * 
     * @exception NamingException
     *                If the look up fails.
     */
    private Context getInitialContext() throws NamingException {
        Context jndiContext = null;
        try {
            final String queueFactoryInitial = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.QUEUE_JAVA_NAMING_FACTORY_INITIAL);
            final String queueProviderURL = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.QUEUE_JAVA_NAMING_PROVIDER_URL);
            final String strUname = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.QUEUE_CONNECTION_UNAME);
            final String strPwd = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.QUEUE_CONNECTION_PWD);
            if (props == null) {
                props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY, queueFactoryInitial);
                props.put(Context.PROVIDER_URL, queueProviderURL);
                // QTR DEV
                props.put(Context.SECURITY_AUTHENTICATION, "simple");
                props.put(Context.SECURITY_PRINCIPAL, strUname);
                props.put(Context.SECURITY_CREDENTIALS, strPwd);
            }
            jndiContext = new InitialContext(props);
        } catch (NamingException e) {
            throw e;
        }
        return jndiContext;
    }

    /**
     * Closes JMS objects.
     */
    private void close() {
        try {
            if (queueSession != null) {
                queueSession.close();
            }
            if (queueConnection != null) {
                queueConnection.close();
            }
        } catch (JMSException jmsException) {
        }
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendErrorMessageAsText(final String text) throws JMSException, NamingException {
        String strErrorQueueName = RMDCommonUtility
                .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.COMMON_ERROR_QUEUENAME);
        sendTextMessage(strErrorQueueName, text);
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendMessageAsText(final String queuName, final String text) throws JMSException, NamingException {
        sendTextMessage(queuName, text);
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    private void sendTextMessage(final String queuName, final String text) throws JMSException, NamingException {
        QueueSender queueSender;
        try {
            // Setup the PTP connection, session
            open(queuName);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            message.setText(text);
            queueSender.send(message);
        } finally {
            // stop and close connection and session
            close();
        }
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendErrorMessageAsBinary(final byte[] bytes) throws JMSException, NamingException {
        final String strErrorQueueName = RMDCommonUtility
                .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.COMMON_ERROR_QUEUENAME);
        sendBinaryMessage(strErrorQueueName, bytes);
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendMessageAsBinary(final String queueName, final byte[] bytes) throws JMSException, NamingException {
        sendBinaryMessage(queueName, bytes);
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    private void sendBinaryMessage(final String queueName, final byte[] bytes) throws JMSException, NamingException {
        QueueSender queueSender;
        try {
            byte[] byteMsg = null;
            if (bytes != null && bytes.length > 0) {
                byteMsg = new byte[bytes.length];
            }
            open(queueName);
            final BytesMessage bytesMessage = queueSession.createBytesMessage();
            for (int i = 0; i < bytes.length; i++) {
                byteMsg[i] = bytes[i];
            }
            bytesMessage.writeBytes(byteMsg);
            queueSender = queueSession.createSender(queue);
            queueSender.send(bytesMessage);
        } catch (Exception objExp) {
            throw new RMDRunTimeException(objExp);
        } finally {
            // stop and close connection and session
            close();
        }
    }
}
