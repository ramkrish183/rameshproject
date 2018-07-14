package com.ge.trans.eoa.services.asset.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.asset.bo.intf.RxResearchBOIntf;
import com.ge.trans.eoa.services.asset.dao.intf.RxResearchDAOIntf;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxRepairDetailsVO;
import com.ge.trans.eoa.services.asset.service.valueobjects.RxResearchEOAVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;

public class RxResearchBOImpl implements RxResearchBOIntf {
	
	private RxResearchDAOIntf objRxResearchDAOIntf;
	public RxResearchBOImpl(RxResearchDAOIntf objRxResearchDAOIntf) {
		this.objRxResearchDAOIntf = objRxResearchDAOIntf;
	}

	@Override
	public List<RxResearchEOAVO> getSearchSolution(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDBOException {
		List<RxResearchEOAVO> rxResearchEOAVO = null;
		try {
			rxResearchEOAVO = objRxResearchDAOIntf.getSearchSolution(objRxResearchEOAVO);
		} catch (RMDDAOException e) {
			throw e;
		}
		return rxResearchEOAVO;
	}

	@Override
	public List<RxResearchEOAVO> getGraphicalData(
			RxResearchEOAVO objRxResearchEOAVO) throws RMDBOException {
		List<RxResearchEOAVO> rxResearchEOAVO = null;
		try {
			rxResearchEOAVO = objRxResearchDAOIntf.getGraphicalData(objRxResearchEOAVO);
		} catch (RMDDAOException e) {
			throw e;
		}
		return rxResearchEOAVO;
	}

	@Override
	public List<RxRepairDetailsVO> populateRepairCodeDetails(RxResearchEOAVO objRxResearchEOAVO)
			throws RMDBOException {
		List<RxRepairDetailsVO> rxRepairDetailsVO = null;
		try {
			rxRepairDetailsVO = objRxResearchDAOIntf.populateRepairCodeDetails(objRxResearchEOAVO);
		} catch (RMDDAOException e) {
			throw e;
		}
		return rxRepairDetailsVO;
	}

}
