package com.ge.trans.pp.services.notification.bo.intf;

import java.util.List;

import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface NotificationBOIntf {

    /**
     * @param customerId
     * @param strLanguage
     * @return
     * @throws RMDBOException
     */
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDBOException;

    /**
     * @param objPPNotHistoryReqVO
     * @return
     * @throws RMDBOException
     */
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDBOException;

    /**
     * @param customerId
     * @return PPRoadInitialVO
     * @throws RMDBOException
     * @Description:this is method declaration for fetching the road initial
     *                   list for given customer
     */
    List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDBOException;
}
