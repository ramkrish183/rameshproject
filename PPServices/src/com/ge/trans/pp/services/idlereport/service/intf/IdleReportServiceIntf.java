package com.ge.trans.pp.services.idlereport.service.intf;

import java.util.List;

import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface IdleReportServiceIntf {

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDServiceException
     */
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDServiceException;

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDServiceException
     */
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDServiceException;
}
