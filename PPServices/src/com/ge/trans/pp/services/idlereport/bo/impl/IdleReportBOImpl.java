package com.ge.trans.pp.services.idlereport.bo.impl;

import java.util.List;

import com.ge.trans.pp.services.idlereport.bo.intf.IdleReportBOIntf;
import com.ge.trans.pp.services.idlereport.dao.intf.IdleReportDAOIntf;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class IdleReportBOImpl implements IdleReportBOIntf {

    private IdleReportDAOIntf objIdleReportDAOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(IdleReportBOImpl.class);

    public IdleReportBOImpl(final IdleReportDAOIntf objIdleReportDAOIntf) {
        this.objIdleReportDAOIntf = objIdleReportDAOIntf;
    }

    @Override
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDBOException {
        List<IdleReportSummaryResponseVO> lstIdleReportSummary;
        try {
            lstIdleReportSummary = objIdleReportDAOIntf.getIdleReportSummary(objIdleReportSummaryVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstIdleReportSummary;
    }

    @Override
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDBOException {
        List<IdleReportDetailsResponseVO> lstIdleReportDetails;
        try {
            lstIdleReportDetails = objIdleReportDAOIntf.getIdleReportDetails(objIdleReportSummaryVO);
        } catch (RMDDAOException e) {
            throw e;
        }
        return lstIdleReportDetails;
    }
}
