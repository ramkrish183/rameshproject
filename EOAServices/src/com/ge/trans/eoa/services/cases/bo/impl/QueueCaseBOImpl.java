package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.cases.bo.intf.QueueCaseBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.QueueCaseDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class QueueCaseBOImpl implements QueueCaseBOIntf {
    private QueueCaseDAOIntf objQueueCaseDAOIntf;

    /**
     * @param objHealthCheckRequestDAOIntf
     */
    public QueueCaseBOImpl(final QueueCaseDAOIntf objQueueCaseDAOIntf) {
        super();
        this.objQueueCaseDAOIntf = objQueueCaseDAOIntf;
    }

    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(QueueCaseBOImpl.class);

    @Override
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDBOException {
        List<QueueCaseVO> arlQueueNames = null;
        try {
            arlQueueNames = objQueueCaseDAOIntf.getQueueList(roleId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlQueueNames;
    }

    @Override
    public List<QueueCaseVO> getQueueCases(String queueobjid, String customerId) throws RMDBOException {
        List<QueueCaseVO> arlQueueCases = null;
        try {
            arlQueueCases = objQueueCaseDAOIntf.getQueueCases(queueobjid, customerId);
        } catch (RMDDAOException e) {
            throw e;
        }
        return arlQueueCases;
    }

}
