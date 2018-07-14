package com.ge.trans.pp.services.notification.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface NotificationDAOIntf {

    /**
     * @param customerId
     * @param strLanguage
     * @return
     * @throws RMDDAOException
     */
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDDAOException;

    /**
     * @param objPPNotHistoryReqVO
     * @return
     * @throws RMDDAOException
     */
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDDAOException;

    /**
     * @param customerId
     * @return PPRoadInitialVO
     * @throws RMDDAOException
     * @Description:this is method declaration for fetching the road initial
     *                   list for given customer
     */
    List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDDAOException;
}
