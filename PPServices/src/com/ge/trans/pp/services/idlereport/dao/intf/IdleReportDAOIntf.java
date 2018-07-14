package com.ge.trans.pp.services.idlereport.dao.intf;

import java.util.List;

import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface IdleReportDAOIntf {

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDDAOException
     */
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDDAOException;

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDDAOException
     */
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDDAOException;
}
