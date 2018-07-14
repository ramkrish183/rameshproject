package com.ge.trans.eoa.services.asset.bo.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface RxResearchBOIntf {
	
	public List<RxResearchEOAVO> getSearchSolution(RxResearchEOAVO objRxResearchEOAVO)  throws RMDBOException;
	public List<RxResearchEOAVO> getGraphicalData(RxResearchEOAVO objRxResearchEOAVO) throws RMDBOException;
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO) throws RMDBOException;

}
