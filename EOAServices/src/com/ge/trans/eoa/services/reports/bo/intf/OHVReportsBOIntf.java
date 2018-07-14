package com.ge.trans.eoa.services.reports.bo.intf;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineTruckVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsGraphRequestVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO;
import com.ge.trans.rmd.common.valueobjects.ComStatusVO;
import com.ge.trans.rmd.common.valueobjects.MineVO;
import com.ge.trans.rmd.common.valueobjects.ReportsRxVO;
import com.ge.trans.rmd.common.valueobjects.ReportsTruckEventsVO;
import com.ge.trans.rmd.common.valueobjects.TruckGraphVO;
import com.ge.trans.rmd.common.valueobjects.TruckVO;

public interface OHVReportsBOIntf {
	
	/**
	 * @author Koushik B
	 * @param OHVMineRequestVO
	 * @return
	 */
	MineVO getMine(OHVMineRequestVO mineRequest);
	/**
	 * @author Koushik B
	 * @param rxRequestVO
	 * @return
	 */
	List<ReportsRxVO> getReportsRx(OHVReportsRxRequestVO rxRequestVO);
	
	/**
	 * @author Koushik B
	 * @param truckEventsRequestVO
	 * @return
	 */
	List<ReportsTruckEventsVO> getReportsTruckEvents(OHVReportsRxRequestVO truckEventsRequestVO);
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	
	List<ReportsTruckEventsVO> getReportsTopEvents(OHVMineRequestVO mineRequest);
	/**
	 * @Author Koushik B
	 * @param truckInfoVO
	 * @return
	 */
	TruckVO getTruckInfo(OHVReportsRxRequestVO truckInfoVO);
	
	/**
	 * @Author Koushik B
	 * @param comStatusVO
	 * @return
	 */
	ComStatusVO getComStatus(OHVReportsRxRequestVO comStatusVO);
	
	/**
	 * 
	 * @param graphDataRequest
	 * @return
	 */
	TruckGraphVO getTruckGraphData(OHVReportsGraphRequestVO graphDataRequest);
	
	/**
	 * 
	 * @param truckInfoVO
	 * @return
	 */
	List<String> getTruckParam(OHVReportsRxRequestVO truckInfoVO);
	
	Map<String,List<String>> getTruckParamList(OHVReportsRxRequestVO truckInfoVO);
	
	Map<String,List<String>> getTruckParamListScheduler(OHVReportsRxRequestVO truckInfoVO);
	
	List<OHVMineTruckVO> getListMineTruck();
	
	public String insertTruckParmList (Map<String,List<String>> truckParamListMap , OHVReportsRxRequestVO truckInfoVO);

}
