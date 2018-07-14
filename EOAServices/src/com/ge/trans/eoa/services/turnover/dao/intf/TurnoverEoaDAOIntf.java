package com.ge.trans.eoa.services.turnover.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface TurnoverEoaDAOIntf {

	List<GetInboundTurnoverVO> getInboundReportData() throws RMDDAOException;
	List<CallLogVO> getCallCountByLocation() throws RMDDAOException;
    List<CallLogVO> getCustBrkDownByMins() throws RMDDAOException;
    List<CallLogVO> getCallCntByBusnsArea() throws RMDDAOException;
    List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDDAOException;
    List<CallLogVO> getVehCallCntByBusnsArea() throws RMDDAOException;
    String getManualCallCount() throws RMDDAOException;
}
