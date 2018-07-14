package com.ge.trans.pp.services.notification.service.impl;

import java.util.List;

import com.ge.trans.pp.common.util.RMDServiceErrorHandler;
import com.ge.trans.pp.services.notification.bo.intf.NotificationBOIntf;
import com.ge.trans.pp.services.notification.service.intf.NotificationServiceIntf;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryReqVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPNotificationHistoryVO;
import com.ge.trans.pp.services.notification.service.valueobjects.PPRoadInitialVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class NotificationServiceImpl implements NotificationServiceIntf {

    private NotificationBOIntf objNotificationBOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(NotificationServiceImpl.class);

    public NotificationServiceImpl(final NotificationBOIntf objNotificationBOIntf) {
        this.objNotificationBOIntf = objNotificationBOIntf;
    }

    /**
     * 
     */
    @Override
    public List<String> getPPCustomerRegions(String customerId, String strLanguage) throws RMDServiceException {
        List<String> ppCustRegionLst = null;
        try {

            ppCustRegionLst = objNotificationBOIntf.getPPCustomerRegions(customerId, strLanguage);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, strLanguage);
        }
        return ppCustRegionLst;
    }

    /**
     * 
     */
    @Override
    public List<PPNotificationHistoryVO> getNotificationHistory(PPNotificationHistoryReqVO objPPNotHistoryReqVO)
            throws RMDServiceException {
        List<PPNotificationHistoryVO> ppNotHistLst = null;
        try {

            ppNotHistLst = objNotificationBOIntf.getNotificationHistory(objPPNotHistoryReqVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, objPPNotHistoryReqVO.getStrLanguage());
        }
        return ppNotHistLst;
    }

    @Override
    public List<PPRoadInitialVO> getRoadInitialsForCustomer(String customerId) throws RMDServiceException {
        try {
            return objNotificationBOIntf.getRoadInitialsForCustomer(customerId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
    }
}
