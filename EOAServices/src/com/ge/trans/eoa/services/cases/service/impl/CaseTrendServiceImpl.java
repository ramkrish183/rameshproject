package com.ge.trans.eoa.services.cases.service.impl;

import java.util.List;

import com.ge.trans.eoa.services.cases.bo.intf.CaseTrendBOIntf;
import com.ge.trans.eoa.services.cases.service.intf.CaseTrendServiceIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDServiceException;

public class CaseTrendServiceImpl implements CaseTrendServiceIntf {

	private CaseTrendBOIntf objCaseTrendBOIntf;

	public CaseTrendServiceImpl(CaseTrendBOIntf objCaseTrendBOIntf) {
		this.objCaseTrendBOIntf = objCaseTrendBOIntf;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching delivered open rx count
	 *               for past 8 days.
	 */
	@Override
	public List<CaseTrendVO> getOpenRXCount() throws RMDServiceException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendBOIntf.getOpenRXCount();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return caseTrendVO;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 24 hours.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrend() throws RMDServiceException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendBOIntf.getCaseTrend();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return caseTrendVO;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 7 days.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrendStatistics()
			throws RMDServiceException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendBOIntf.getCaseTrendStatistics();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return caseTrendVO;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getGeneralNotesDetails() throws RMDServiceException {
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
		try {
		    arlGeneralNotesEoaServiceVO = objCaseTrendBOIntf.getGeneralNotesDetails();
		} catch (RMDBOException ex) {
			throw new RMDServiceException(ex.getErrorDetail(), ex);
		}
		return arlGeneralNotesEoaServiceVO;
	}

}
