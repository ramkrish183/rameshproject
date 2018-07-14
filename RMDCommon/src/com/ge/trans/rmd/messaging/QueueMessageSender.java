/**
 * ============================================================
 * File : QueueMessageSender.java
 * Description : 
 * 
 * Package : com.ge.trans.rmd.common.valueobjects
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : July 20, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.rmd.messaging;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ge.trans.rmd.exception.RMDRunTimeException;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: July 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : class that has code to open a queue connection, session and
 *              send message. it supports sending a text message
 * @History :
 ******************************************************************************/
public abstract class QueueMessageSender {

    private InitialContext iniCtx;
    private QueueConnectionFactory qcf;
    private QueueConnection conn;
    private QueueSession session;
    private Queue que;

    /**
     * Constructor
     * 
     * @param context
     * @param conFactory
     */
    public QueueMessageSender(final InitialContext context, final QueueConnectionFactory conFactory) {
        iniCtx = context;
        qcf = conFactory;
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendTextMessage(final String text) throws JMSException, NamingException {
        QueueSender sender = null;
        try {
            // Setup the PTP connection, session
            setupPTP();
            // Send a text msg
            sender = session.createSender(que);
            final TextMessage tm = session.createTextMessage(text);
            sender.send(tm);
            sender.close();
        } finally {
            // close sender
            if (sender != null) {
                sender.close();
            }
            // stop and close connection and session
            stop();
        }
    }

    /**
     * @Author:
     * @param text
     * @throws JMSException
     * @throws NamingException
     * @Description:Seding Text message
     */
    public void sendBinaryMessage(final byte[] bytes) throws JMSException, NamingException {
        QueueSender sender = null;
        try {
            byte[] byteMsg = null;
            if (bytes != null && bytes.length > 0) {
                byteMsg = new byte[bytes.length];
            }
            setupPTP();
            sender = session.createSender(que);
            final BytesMessage bytesMessage = session.createBytesMessage();
            for (int i = 0; i < bytes.length; i++) {
                byteMsg[i] = bytes[i];
            }
            bytesMessage.writeBytes(byteMsg);
            sender = session.createSender(que);
            sender.send(bytesMessage);
            sender.close();
        } catch (Exception objExp) {
            throw new RMDRunTimeException(objExp);
        } finally {
            // close sender
            if (sender != null) {
                sender.close();
            }
            // stop and close connection and session
            stop();
        }
    }

    /**
     * @Author:
     * @return
     * @Description:Get the Queue name
     */
    protected abstract String getQueueName();

    /**
     * @Author:
     * @throws JMSException
     * @throws NamingException
     * @Description:Create connection to queue
     */
    private void setupPTP() throws JMSException, NamingException {
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup(getQueueName());
        session = conn.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    /**
     * @Author:
     * @throws JMSException
     * @Description:Close the connection, session
     */
    private void stop() throws JMSException {
        if (conn != null) {
            conn.stop();
        }
        if (session != null) {
            session.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
