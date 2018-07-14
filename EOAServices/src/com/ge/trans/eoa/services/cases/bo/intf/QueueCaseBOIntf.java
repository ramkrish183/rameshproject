package com.ge.trans.eoa.services.cases.bo.intf;

import java.util.List;
import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface QueueCaseBOIntf {
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDBOException;

    public List<QueueCaseVO> getQueueCases(String queueobjid, String strCustomer) throws RMDBOException;
}
