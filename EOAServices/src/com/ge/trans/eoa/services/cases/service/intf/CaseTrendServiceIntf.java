package com.ge.trans.eoa.services.cases.service.intf;

import java.util.List;

import com.ge.trans.eoa.services.cases.service.valueobjects.CaseTrendVO;
import com.ge.trans.eoa.services.gpoc.service.valueobjects.GeneralNotesEoaServiceVO;
import com.ge.trans.rmd.exception.RMDServiceException;

public interface CaseTrendServiceIntf {
	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching delivered open rx count
	 *               for past 8 days.
	 */
	public List<CaseTrendVO> getOpenRXCount() throws RMDServiceException;

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 24 hours.
	 */
	public List<CaseTrendVO> getCaseTrend() throws RMDServiceException;

	/**
	 * @Author:
	 * @param:
	 * @return:List<CaseTrendVO>
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching created case type count
	 *               for past 7 days.
	 */
	public List<CaseTrendVO> getCaseTrendStatistics()
			throws RMDServiceException;
	/**
	 * @Author:
	 * @param:
	 * @return:String
	 * @throws:RMDServiceException
	 * @Description: This method is used for fetching general note info having flag as 'Y'.
	 */
	public  List<GeneralNotesEoaServiceVO> getGeneralNotesDetails() throws RMDServiceException;
}
