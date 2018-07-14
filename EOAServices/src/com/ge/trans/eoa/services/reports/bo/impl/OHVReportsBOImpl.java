package com.ge.trans.eoa.services.reports.bo.impl;

import java.util.List;
import java.util.Map;

import com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf;
import com.ge.trans.eoa.services.reports.dao.intf.OHVReportsDAOIntf;
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

public class OHVReportsBOImpl implements OHVReportsBOIntf {
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(OHVReportsBOImpl.class);
	OHVReportsDAOIntf ohvReportsDAOIntf;
	OHVReportsBOImpl(OHVReportsDAOIntf ohvReportsDAOIntf){
		this.ohvReportsDAOIntf = ohvReportsDAOIntf;
	}
	public MineVO getMine(OHVMineRequestVO mineRequest) {
		LOG.info("BO is invoked");
		return ohvReportsDAOIntf.getMine(mineRequest);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getReportsRx(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public List<ReportsRxVO> getReportsRx(OHVReportsRxRequestVO rxRequestVO) {
		return ohvReportsDAOIntf.getReportsRx(rxRequestVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getReportsTruckEvents(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public List<ReportsTruckEventsVO> getReportsTruckEvents(
			OHVReportsRxRequestVO truckEventsRequestVO) {
		return ohvReportsDAOIntf.getReportsTruckEvents(truckEventsRequestVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getReportsTopEvents(java.lang.String)
	 */
	public List<ReportsTruckEventsVO> getReportsTopEvents(OHVMineRequestVO mineRequest){
		return ohvReportsDAOIntf.getReportsTopEvents(mineRequest);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getTruckInfo(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public TruckVO getTruckInfo(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsDAOIntf.getTruckInfo(truckInfoVO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getComStatus(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public ComStatusVO getComStatus(OHVReportsRxRequestVO comStatusVO) {
		return ohvReportsDAOIntf.getComStatus(comStatusVO);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getTruckGraphData(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	@Override
	public TruckGraphVO getTruckGraphData(OHVReportsGraphRequestVO graphDataRequest) {
		return ohvReportsDAOIntf.getTruckGraphData(graphDataRequest);
	}

	/*
	 * (non-Javadoc)
	 * @see com.ge.trans.eoa.services.reports.bo.intf.OHVReportsBOIntf#getTruckParam(com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO)
	 */
	public List<String> getTruckParam(OHVReportsRxRequestVO truckInfoVO) {
		if (truckInfoVO.isAvgCalc()) {
			return ohvReportsDAOIntf.getTruckParamAvg(truckInfoVO); }
		else {
		return ohvReportsDAOIntf.getTruckParam(truckInfoVO); }
	}
	
	public Map<String,List<String>> getTruckParamList(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsDAOIntf.getTruckParamList(truckInfoVO);
	}
	
	public List<OHVMineTruckVO> getListMineTruck() {
		return ohvReportsDAOIntf.getListMineTruck();
	}
	
	public String insertTruckParmList (Map<String,List<String>> truckParamListMap , OHVReportsRxRequestVO truckInfoVO){
		return ohvReportsDAOIntf.insertTruckParmList (truckParamListMap ,  truckInfoVO);
	}
	
	public Map<String,List<String>> getTruckParamListScheduler(OHVReportsRxRequestVO truckInfoVO) {
		return ohvReportsDAOIntf.getTruckParamListFromScheduler(truckInfoVO);
	}
}
