package com.ge.trans.eoa.services.cases.service.impl;

import java.util.List;
import com.ge.trans.eoa.common.util.RMDServiceErrorHandler;
import com.ge.trans.eoa.services.cases.bo.intf.QueueCaseBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.QueueCasesServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class QueueCasesServiceImpl implements QueueCasesServiceIntf {
    private QueueCaseBOIntf objQueueCaseBOIntf;

    public QueueCasesServiceImpl(QueueCaseBOIntf objQueueCaseBOIntf) {
        this.objQueueCaseBOIntf = objQueueCaseBOIntf;
    }

    @Override
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDServiceException {
        List<QueueCaseVO> result = null;
        try {
            result = objQueueCaseBOIntf.getQueueList(roleId);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }

    @Override
    public List<QueueCaseVO> getQueueCases(String queueobjid, String strCustomer) throws RMDServiceException {
        List<QueueCaseVO> result = null;
        try {
            result = objQueueCaseBOIntf.getQueueCases(queueobjid, strCustomer);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return result;
    }
}
