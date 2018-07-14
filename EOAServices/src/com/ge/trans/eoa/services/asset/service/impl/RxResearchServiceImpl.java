package com.ge.trans.eoa.services.asset.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RxResearchBOIntf;
import com.ge.trans.eoa.services.asset.service.intf.RxResearchServiceIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class RxResearchServiceImpl implements RxResearchServiceIntf {
	
	private RxResearchBOIntf objRxResearchBOIntf;
	public RxResearchServiceImpl(RxResearchBOIntf objRxResearchBOIntf) {
		this.objRxResearchBOIntf = objRxResearchBOIntf;
	}
	@Override
	public List<RxResearchEOAVO> getSearchSolution(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDServiceException {
		
		List<RxResearchEOAVO> rxResearchEOAVO = null;
		try {
			rxResearchEOAVO = objRxResearchBOIntf.getSearchSolution(objRxResearchEOAVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return rxResearchEOAVO;
	}
	@Override
	public List<RxResearchEOAVO> getGraphicalData(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDServiceException {
		List<RxResearchEOAVO> rxResearchGraphEOAVO = null;
		try {
			rxResearchGraphEOAVO = objRxResearchBOIntf.getGraphicalData(objRxResearchEOAVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return rxResearchGraphEOAVO;
	}
	@Override
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO)
			throws RMDServiceException {
		List<RxRepairDetailsVO> rxRepairDetailsVO = null;
		try {
			rxRepairDetailsVO = objRxResearchBOIntf.populateRepairCodeDetails(objRxResearchEOAVO);
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return rxRepairDetailsVO;
	}

}
