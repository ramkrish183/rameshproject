package com.ge.trans.eoa.services.turnover.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDBOException;

public interface TurnoverEoaBOIntf {
	
	List<GetInboundTurnoverVO> getInboundReportData() throws RMDBOException;
	List<CallLogVO> getCallCountByLocation() throws RMDBOException;
    List<CallLogVO> getCustBrkDownByMins() throws RMDBOException;
    List<CallLogVO> getCallCntByBusnsArea() throws RMDBOException;
    List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDBOException;
    List<CallLogVO> getVehCallCntByBusnsArea() throws RMDBOException;
    String getManualCallCount() throws RMDBOException;

}
