package com.ge.trans.eoa.services.asset.dao.intf;

import java.util.List;

import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public interface RxResearchDAOIntf {
	public List<RxResearchEOAVO> getSearchSolution(RxResearchEOAVO objRxResearchEOAVO)  throws RMDDAOException;
	public List<RxResearchEOAVO> getGraphicalData(RxResearchEOAVO objRxResearchEOAVO) throws RMDDAOException;
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO) throws RMDDAOException;

}
