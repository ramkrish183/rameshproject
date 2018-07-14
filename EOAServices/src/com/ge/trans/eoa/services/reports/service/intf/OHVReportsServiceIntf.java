package com.ge.trans.eoa.services.reports.service.intf;

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

/**
 * @author
 *
 */
public interface OHVReportsServiceIntf {
	/**
	 * @author Koushik B
	 * @param customerId
	 * @return
	 */
	MineVO getMine(OHVMineRequestVO mineRequest);
	
	/**
	 * @Author Koushik B
	 * @param rxRequestVO
	 * @return
	 */
	List<ReportsRxVO> getReportsRx(OHVReportsRxRequestVO rxRequestVO);
	
	/**
	 * @Author Koushik B
	 * @param rxRequestVO
	 * @return
	 */
	List<ReportsTruckEventsVO> getReportsTruckEvents(OHVReportsRxRequestVO truckEventRequestVO);
	
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
	List<String> getTruckParm(OHVReportsRxRequestVO truckInfoVO);
	
	Map<String,List<String>> getTruckParmList(OHVReportsRxRequestVO truckInfoVO);
	
	public List<OHVMineTruckVO> getListMineTruck();
	
	public String insertTruckParmList(Map<String,List<String>> truckParamListMap , OHVReportsRxRequestVO truckInfoVO);
	
	Map<String,List<String>> getTruckParmListScheduler(OHVReportsRxRequestVO truckInfoVO);
	
}