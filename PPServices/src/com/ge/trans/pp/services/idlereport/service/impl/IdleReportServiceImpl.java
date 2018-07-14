package com.ge.trans.pp.services.idlereport.service.impl;

import java.util.List;

import com.ge.trans.pp.common.util.RMDServiceErrorHandler;
import com.ge.trans.pp.services.idlereport.bo.intf.IdleReportBOIntf;
import com.ge.trans.pp.services.idlereport.service.intf.IdleReportServiceIntf;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportDetailsResponseVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryReqVO;
import com.ge.trans.pp.services.idlereport.service.valueobjects.IdleReportSummaryResponseVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

public class IdleReportServiceImpl implements IdleReportServiceIntf {

    private IdleReportBOIntf objIdleReportBOIntf;
    public static final RMDLogger LOG = RMDLoggerHelper.getLogger(IdleReportServiceImpl.class);

    public IdleReportServiceImpl(final IdleReportBOIntf objIdleReportBOIntf) {
        this.objIdleReportBOIntf = objIdleReportBOIntf;
    }

    /**
     * 
     */
    @Override
    public List<IdleReportSummaryResponseVO> getIdleReportSummary(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDServiceException {
        List<IdleReportSummaryResponseVO> ppIdlerepSummaryLst = null;
        try {

            ppIdlerepSummaryLst = objIdleReportBOIntf.getIdleReportSummary(objIdleReportSummaryVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return ppIdlerepSummaryLst;
    }

    @Override
    public List<IdleReportDetailsResponseVO> getIdleReportDetails(IdleReportSummaryReqVO objIdleReportSummaryVO)
            throws RMDServiceException {
        List<IdleReportDetailsResponseVO> ppIdlerepDetailLst = null;
        try {

            ppIdlerepDetailLst = objIdleReportBOIntf.getIdleReportDetails(objIdleReportSummaryVO);
        } catch (RMDDAOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        } catch (Exception ex) {
            RMDServiceErrorHandler.handleGeneralException(ex, RMDCommonConstants.ENGLISH_LANGUAGE);
        }
        return ppIdlerepDetailLst;
    }
}