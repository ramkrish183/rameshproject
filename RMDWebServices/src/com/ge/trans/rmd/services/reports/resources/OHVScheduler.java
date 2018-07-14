package com.ge.trans.rmd.services.reports.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ge.trans.eoa.services.reports.service.intf.OHVReportsServiceIntf;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVMineTruckVO;
import com.ge.trans.eoa.services.reports.service.valueobjects.OHVReportsRxRequestVO;
import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.intf.OMDResourceMessagesIntf;
import com.ge.trans.rmd.common.util.RMDWebServiceErrorHandler;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;

//@Component
public class OHVScheduler {
	
	public static final RMDLogger LOG = RMDLoggerHelper.getLogger(OHVScheduler.class);
	
	@Autowired
    private OHVReportsServiceIntf ohvReportsServiceIntf;
	@Autowired
    private OMDResourceMessagesIntf omdResourceMessagesIntf;

	
	public List<List<String>> listAllMineTruck(){
		OHVReportsRxRequestVO truckInfoVO = null;
		LOG.info("getMine service triggered");
		try {
				LOG.info("Calling service from resource");
				List<OHVMineTruckVO> mineTruckList  = ohvReportsServiceIntf.getListMineTruck();		
				for( OHVMineTruckVO mineTruck : mineTruckList){
					truckInfoVO = new OHVReportsRxRequestVO();
					truckInfoVO.setMineId(mineTruck.getMineId());
					truckInfoVO.setTruckId(mineTruck.getTruckId());
					Map<String,List<String>> truckParamListMap  = ohvReportsServiceIntf.getTruckParmList(truckInfoVO);
					truckInfoVO.setAvgCalc(true);
					List<String> getTruckParm = ohvReportsServiceIntf.getTruckParm(truckInfoVO);
					truckParamListMap.put(RMDCommonConstants.THIRTY_DAY_AVG , getTruckParm);
					String status = ohvReportsServiceIntf.insertTruckParmList(truckParamListMap ,  truckInfoVO);
				}
			     
					
		} catch (Exception ex) {
			LOG.info("Exception occured in getMine of OHVReportsResource "+ex.getMessage());
			LOG.error(ex,ex);
            RMDWebServiceErrorHandler.handleException(ex,omdResourceMessagesIntf);
        }
		return null;
		
	}
	
	
	
}
