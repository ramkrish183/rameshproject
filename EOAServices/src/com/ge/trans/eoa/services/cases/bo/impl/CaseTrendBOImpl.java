package com.ge.trans.eoa.services.cases.bo.impl;

import java.util.List;

import com.ge.trans.eoa.services.cases.bo.intf.CaseTrendBOIntf;
import com.ge.trans.eoa.services.cases.dao.intf.CaseTrendDAOIntf;
import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDBOException;
import com.ge.trans.rmd.exception.RMDDAOException;
import com.ge.trans.rmd.logging.RMDLogger;

public class CaseTrendBOImpl implements CaseTrendBOIntf {

	private CaseTrendDAOIntf objCaseTrendDAOIntf;
	public static final RMDLogger LOG = RMDLogger
			.getLogger(CaseTrendBOImpl.class);

	public CaseTrendBOImpl(CaseTrendDAOIntf objCaseTrendDAOIntf) {
		this.objCaseTrendDAOIntf = objCaseTrendDAOIntf;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDBOException
	 * @Description: This method is used for fetching delivered open rx count
	 *               for past 8 days.
	 */
	@Override
	public List<CaseTrendVO> getOpenRXCount() throws RMDBOException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendDAOIntf.getOpenRXCount();
		} catch (RMDDAOException e) {
			throw e;
		}
		return caseTrendVO;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDBOException
	 * @Description: This method is used for fetching created case type count
	 *               for past 24 hours.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrend() throws RMDBOException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendDAOIntf.getCaseTrend();
		} catch (RMDDAOException e) {
			throw e;
		}
		return caseTrendVO;
	}

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDBOException
	 * @Description: This method is used for fetching created case type count
	 *               for past 7 days.
	 */
	@Override
	public List<CaseTrendVO> getCaseTrendStatistics() throws RMDBOException {
		List<CaseTrendVO> caseTrendVO = null;
		try {
			caseTrendVO = objCaseTrendDAOIntf.getCaseTrendStatistics();
		} catch (RMDDAOException e) {
			throw e;
		}
		return caseTrendVO;
	}

	@Override
	public List<GeneralNotesEoaServiceVO> getGeneralNotesDetails() throws RMDBOException {
		List<GeneralNotesEoaServiceVO> arlGeneralNotesEoaServiceVO = null;
		try {
		    arlGeneralNotesEoaServiceVO = objCaseTrendDAOIntf.getGeneralNotesDetails();
		} catch (RMDDAOException ex) {
			throw ex;
		}
		return arlGeneralNotesEoaServiceVO;
	}

}
