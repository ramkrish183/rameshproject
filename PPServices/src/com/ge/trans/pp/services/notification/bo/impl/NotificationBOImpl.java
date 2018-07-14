package com.ge.trans.pp.services.notification.bo.impl;

import java.util.List;

import com.ge.trans.pp.services.notification.bo.intf.NotificationBOIntf;
import com.ge.trans.pp.services.notification.dao.intf.NotificationDAOIntf;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class NotificationBOImpl implements NotificationBOIntf {

    private NotificationDAOIntf objNotificationDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotificationBOImpl.class);

    public NotificationBOImpl(final NotificationDAOIntf objNotificationDAOIntf) {
        this.objNotificationDAOIntf = objNotificationDAOIntf;
    }

    /**
     * 
     */
    @Override
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDBOException {
        List<String> lstRegion;
        try {
            lstRegion = objNotificationDAOIntf.getPPCustomerRegions(customerId, strLanguage);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstRegion;
    }

    @Override
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDBOException {
        List<PPNotificationHistoryVO> lstNotificationHistory;
        try {
            lstNotificationHistory = objNotificationDAOIntf.getNotificationHistory(objPPNotHistoryReqVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstNotificationHistory;
    }

    @Override
    public List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDBOException {
        try {
            return objNotificationDAOIntf.getRoadInitialsForCustomer(customerId);
        } catch (RMDDAOException e) {
            throw e;
        }
    }
}
