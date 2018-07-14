/**
 * 
 */
package com.ge.trans.eoa.services.reports.service.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf;
import com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf;
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
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

/**
 * @author
 *
 */
public class OHVReportsServiceImpl implements OHVReportsServiceIntf{

	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(OHVReportsServiceImpl.class);
	OHVReportsBOIntf ohvReportsBOIntf;
	OHVReportsServiceImpl(OHVReportsBOIntf ohvReportsBOIntf){
		this.ohvReportsBOIntf = ohvReportsBOIntf;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getMine(java.lang.String)
	 */
	public MineVO getMine(OHVMineRequestVO mineRequest){
		LOG.info("Service is invoked");
		return ohvReportsBOIntf.getMine(mineRequest);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getReportsRx(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public List<ReportsRxVO> getReportsRx(OHVReportsRxRequestVO rxRequestVO) {
		return ohvReportsBOIntf.getReportsRx(rxRequestVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getReportsTruckEvents(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public List<ReportsTruckEventsVO> getReportsTruckEvents(
			OHVReportsRxRequestVO truckEventRequestVO) {
		return ohvReportsBOIntf.getReportsTruckEvents(truckEventRequestVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getReportsTopEvents(java.lang.String)
	 */
	public List<ReportsTruckEventsVO> getReportsTopEvents(OHVMineRequestVO mineRequest){
		return ohvReportsBOIntf.getReportsTopEvents(mineRequest);
	}
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getTruckInfo(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public TruckVO getTruckInfo(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsBOIntf.getTruckInfo(truckInfoVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getComStatus(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public ComStatusVO getComStatus(OHVReportsRxRequestVO comStatusVO) {
		return ohvReportsBOIntf.getComStatus(comStatusVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getTruckGraphData(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	public TruckGraphVO getTruckGraphData(OHVReportsGraphRequestVO graphDataRequest) {
		return ohvReportsBOIntf.getTruckGraphData(graphDataRequest);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf#getTruckParm(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	public List<String> getTruckParm(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsBOIntf.getTruckParam(truckInfoVO);
	}
	
	public Map<String,List<String>> getTruckParmList(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsBOIntf.getTruckParamList(truckInfoVO);
	}
	
	public List<OHVMineTruckVO> getListMineTruck() {
		return ohvReportsBOIntf.getListMineTruck();
	}
	
	public String insertTruckParmList (Map<String,List<String>> truckParamListMap , OHVReportsRxRequestVO truckInfoVO){
		return ohvReportsBOIntf.insertTruckParmList (truckParamListMap , truckInfoVO);
	}
	
	public Map<String,List<String>> getTruckParmListScheduler(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsBOIntf.getTruckParamListScheduler(truckInfoVO);
	}
}
