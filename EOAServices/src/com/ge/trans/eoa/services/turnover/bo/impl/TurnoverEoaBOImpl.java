package com.ge.trans.eoa.services.turnover.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.eoa.services.turnover.bo.intf.TurnoverEoaBOIntf;
import com.ge.trans.eoa.services.turnover.dao.intf.TurnoverEoaDAOIntf;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class TurnoverEoaBOImpl implements TurnoverEoaBOIntf {

	private TurnoverEoaDAOIntf turnoverEoaDAOIntf;

	/**
	 * @param rolesDAO
	 */
	public TurnoverEoaBOImpl(TurnoverEoaDAOIntf turnoverDAO) {
		this.turnoverEoaDAOIntf = turnoverDAO;
	}

	@Override
	public List<GetInboundTurnoverVO> getInboundReportData()
			throws RMDBOException {
		try {
			return turnoverEoaDAOIntf.getInboundReportData();
		} catch (RMDDAOException e) {
			throw e;
		}

	}
	
	@Override
	public List<CallLogVO> getCallCountByLocation() throws RMDBOException {
		List<CallLogVO> getCallLogReportVO = null;
		try {
			getCallLogReportVO = turnoverEoaDAOIntf.getCallCountByLocation();
		} catch (RMDDAOException e) {
		    throw new RMDBOException(e.getErrorDetail(), e);
		}
		return getCallLogReportVO;
	}
	@Override
    public List<CallLogVO> getCustBrkDownByMins() throws RMDBOException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverEoaDAOIntf.getCustBrkDownByMins();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getCallCntByBusnsArea() throws RMDBOException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverEoaDAOIntf.getCallCntByBusnsArea();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDBOException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverEoaDAOIntf.getWeeklyCallCntByBusnsArea();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public List<CallLogVO> getVehCallCntByBusnsArea() throws RMDBOException {
        List<CallLogVO> getCallLogReportVO = null;
        try {
            getCallLogReportVO = turnoverEoaDAOIntf.getVehCallCntByBusnsArea();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return getCallLogReportVO;
    }
	
	@Override
    public String getManualCallCount() throws RMDBOException {
        String manualCallCount = null;
        try {
            manualCallCount = turnoverEoaDAOIntf.getManualCallCount();
        } catch (RMDDAOException e) {
            throw new RMDBOException(e.getErrorDetail(), e);
        }
        return manualCallCount;
    }
}
