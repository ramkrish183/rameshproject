package com.ge.trans.pp.services.idlereport.bo.intf;

import java.util.List;

import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface IdleReportBOIntf {

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDBOException
     */
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDBOException;

    /**
     * @param objIdleReportSummaryVO
     * @return
     * @throws RMDBOException
     */
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDBOException;
}
