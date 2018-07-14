/**
 * ============================================================
 * File : NotificationServiceIntf.java
 * Description : 
 * 
 * Package :  com.ge.trans.pp.services.notification.service.intf
 * Author : iGATE 
 * Last Edited By :
 * Version : 1.0
 * Created on : Jun 7, 2013
 * History
 * Modified By : Initial Release
 * Classification : iGATE Sensitive
 * Copyright (C) 2013 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.pp.services.notification.service.intf;

import java.util.List;

import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface NotificationServiceIntf {

    /**
     * @param customerId
     * @param strLanguage
     * @return
     * @throws RMDServiceException
     */
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDServiceException;

    /**
     * @param objPPNotHistoryReqVO
     * @return
     * @throws RMDServiceException
     */
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDServiceException;

    /**
     * @param customerId
     * @return PPRoadInitialVO
     * @throws RMDServiceException
     * @Description:this is method declaration for fetching the road initial
     *                   list for given customer
     */
    List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDServiceException;
}
