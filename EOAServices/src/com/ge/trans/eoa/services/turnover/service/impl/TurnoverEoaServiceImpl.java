package com.ge.trans.eoa.services.turnover.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.eoa.services.turnover.bo.intf.TurnoverEoaBOIntf;
import com.ge.trans.eoa.services.turnover.service.intf.TurnoverEoaServiceIntf;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class TurnoverEoaServiceImpl implements TurnoverEoaServiceIntf {

	private TurnoverEoaBOIntf turnoverBOIntf;

	/**
	 * @param rolesBO
	 */
	public TurnoverEoaServiceImpl(TurnoverEoaBOIntf turnoverBO) {
		this.turnoverBOIntf = turnoverBO;
	}

	@Override
	public List<GetInboundTurnoverVO> getInboundReportData()
			throws RMDServiceException {

		List<GetInboundTurnoverVO> responseData = new ArrayList<GetInboundTurnoverVO>();
		try {
			responseData = turnoverBOIntf.getInboundReportData();
		} catch (RMDDAOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseData;

	}
	@Override
	public List<CallLogVO> getCallCountByLocation() throws RMDServiceException {
		List<CallLogVO> getCallLogReportVO = null;
		try {
			getCallLogReportVO = turnoverBOIntf.getCallCountByLocation();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return getCallLogReportVO;
	}
	
	@Override
    public List<CallLogVO> getCustBrkDownByMins() throws RMDServiceException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverBOIntf.getCustBrkDownByMins();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getCallCntByBusnsArea() throws RMDServiceException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverBOIntf.getCallCntByBusnsArea();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDServiceException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverBOIntf.getWeeklyCallCntByBusnsArea();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getVehCallCntByBusnsArea() throws RMDServiceException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverBOIntf.getVehCallCntByBusnsArea();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return getCallLogReportVO;
    }

    @Override
    public String getManualCallCount() throws RMDServiceException {
        String manualCallCount = null;
        try {
            manualCallCount = turnoverBOIntf.getManualCallCount();
        } catch (RMDBOException ex) {
            throw new RMDServiceException(ex.getErrorDetail(), ex);
        }
        return manualCallCount;
    }
	
}
