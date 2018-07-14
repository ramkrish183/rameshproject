package com.ge.trans.eoa.services.cases.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface QueueCaseDAOIntf {
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDDAOException;

    public List<QueueCaseVO> getQueueCases(String queueobjid, String strCustomer) throws RMDDAOException;
}
