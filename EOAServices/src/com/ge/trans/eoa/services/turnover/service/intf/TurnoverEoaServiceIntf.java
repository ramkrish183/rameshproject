package com.ge.trans.eoa.services.turnover.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CallLogVO;
import com.ge.trans.rmd.common.valueobjects.GetInboundTurnoverVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface TurnoverEoaServiceIntf {

	List<GetInboundTurnoverVO> getInboundReportData() throws RMDServiceException;
	List<CallLogVO> getCallCountByLocation() throws RMDServiceException;
    List<CallLogVO> getCustBrkDownByMins() throws RMDServiceException;
    List<CallLogVO> getCallCntByBusnsArea() throws RMDServiceException;
    List<CallLogVO> getWeeklyCallCntByBusnsArea() throws RMDServiceException;
    List<CallLogVO> getVehCallCntByBusnsArea() throws RMDServiceException;
    String getManualCallCount() throws RMDServiceException;
	
}
