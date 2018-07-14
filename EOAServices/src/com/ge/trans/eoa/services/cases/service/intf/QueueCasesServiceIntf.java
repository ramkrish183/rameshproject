package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.QueueCaseVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface QueueCasesServiceIntf {
    public List<QueueCaseVO> getQueueList(String roleId) throws RMDServiceException;

    public List<QueueCaseVO> getQueueCases(String queueobjid, String strCustomer) throws RMDServiceException;
}
