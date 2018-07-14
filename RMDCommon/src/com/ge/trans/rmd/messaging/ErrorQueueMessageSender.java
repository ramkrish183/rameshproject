/**
 * ============================================================
 * File : ErrorMessageVO.java
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

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.valueobjects.ErrorMessageVO;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: July 20, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : It creates error message text string from this VO and uses the
 *              sendTextMessage to send the message to error queue.
 * @History :
 ******************************************************************************/
public class ErrorQueueMessageSender extends QueueMessageSender {

    public ErrorQueueMessageSender(final InitialContext context, final QueueConnectionFactory conFactory) {
        super(context, conFactory);
    }

    /**
     * @Author:
     * @param errorMsg
     * @Description:Send message to queue
     */
    public void sendMessage(final ErrorMessageVO errorMsg) throws Exception {
        final String msgText = RMDCommonErrorMessageHelper.buildMqRequest(errorMsg);
        try {
            sendBinaryMessage(msgText.getBytes());
        } catch (JMSException e) {
            throw e;
        } catch (NamingException e) {
            throw e;
        }
    }

    @Override
    protected String getQueueName() {
        String strQueueName = null;
        try {
            strQueueName = RMDCommonUtility
                    .getApplicationConfigEntriesFromPropertyFile(RMDCommonConstants.ERROR_QUEUE_JNDI_LOOKUP_KEY);
        } catch (Exception e) {

        }
        return strQueueName;
    }
}
