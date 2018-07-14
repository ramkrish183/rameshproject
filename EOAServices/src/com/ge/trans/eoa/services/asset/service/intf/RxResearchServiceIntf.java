package com.ge.trans.eoa.services.asset.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RxResearchServiceIntf {
	
	public List<RxResearchEOAVO> getSearchSolution(RxResearchEOAVO objRxResearchEOAVO) throws RMDServiceException;
	public List<RxResearchEOAVO> getGraphicalData(RxResearchEOAVO objRxResearchEOAVO) throws RMDServiceException;
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO) throws RMDServiceException;

}
